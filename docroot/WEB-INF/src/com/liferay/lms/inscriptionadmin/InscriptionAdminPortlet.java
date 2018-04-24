package com.liferay.lms.inscriptionadmin;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class InscriptionAdminPortlet
 */
public class InscriptionAdminPortlet extends MVCPortlet {
	private static Log log = LogFactoryUtil.getLog(InscriptionAdminPortlet.class);
 
	private String viewJSP; 
	
	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}
	
	public void doView(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException {
		if(log.isDebugEnabled())log.debug(this.getClass().getName());
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		Course course = null;
		int numberUsers = 0;
		long maxUsers = 0;
		
		try {
			course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
			
			if(course==null){
				renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
				return;
			}else{
				maxUsers = course.getMaxusers();
				numberUsers = CourseLocalServiceUtil.countStudents(course.getCourseId(), course.getCompanyId(), null, null, null, null, false);
			}
		} catch (SystemException e) {
			if(log.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		
		SearchContainer<MembershipRequest> searchContainer = new SearchContainer<MembershipRequest>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
				SearchContainer.DEFAULT_DELTA, renderResponse.createRenderURL(), 
				null, "no-results");
		
		PermissionChecker permissionChecker = PermissionThreadLocal.getPermissionChecker();
		
		if(!permissionChecker.hasPermission(course.getGroupCreatedId(),  Course.class.getName(),course.getCourseId(),ActionKeys.ASSIGN_MEMBERS)){
			if(log.isDebugEnabled())log.debug(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY+"-"+Boolean.FALSE);
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
				
		try {
			searchContainer.setResults(MembershipRequestLocalServiceUtil.search(themeDisplay.getScopeGroupId(), MembershipRequestConstants.STATUS_PENDING, searchContainer.getStart(), searchContainer.getEnd()));
			searchContainer.setTotal(MembershipRequestLocalServiceUtil.searchCount(themeDisplay.getScopeGroupId(), MembershipRequestConstants.STATUS_PENDING));
		} catch (SystemException e) {
			if(log.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		
		if(maxUsers>0){
			renderRequest.setAttribute("maxUsers", "/"+maxUsers); 
		}
		
		renderRequest.setAttribute("searchContainer", searchContainer);
		renderRequest.setAttribute("numberUsers", numberUsers);
		
		include(viewJSP, renderRequest, renderResponse);
	}

	@ProcessAction(name = "enroll") 
	public void enroll(ActionRequest request, ActionResponse response) throws SystemException{
		long msrId = ParamUtil.getLong(request, "msrId");
		try {
			MembershipRequest msr = MembershipRequestLocalServiceUtil.getMembershipRequest(msrId);

	    	Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(msr.getGroupId());
	    	int numberUsers = UserLocalServiceUtil.getGroupUsersCount(msr.getGroupId());
	    	
	    	if(course.getMaxusers()>0&&numberUsers>=course.getMaxusers()){
	    		SessionErrors.add(request, "course.full"); 
	    		return;
	    	}else{
	    		long[] groupIds = new long[1];
	        	groupIds[0] = msr.getGroupId();
	    		GroupLocalServiceUtil.addUserGroups(msr.getUserId(),groupIds);
	    		
				msr.setStatusId(MembershipRequestConstants.STATUS_APPROVED);
				msr.setReplyDate(new Date());
				MembershipRequestLocalServiceUtil.updateMembershipRequest(msr);
				
				//auditing -> GroupListener
				//ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
				//AuditingLogFactory.audit(themeDisplay.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
				//		course.getCourseId(), themeDisplay.getUserId(), AuditConstants.REGISTER, null);
	    	}
		} catch (NumberFormatException e) {
			if(log.isDebugEnabled()){
				e.printStackTrace();
			}
		} catch (PortalException e) {
			if(log.isDebugEnabled()){
				e.printStackTrace();
			}
		}
	}

	@ProcessAction(name = "denied")
	public void denied(ActionRequest request, ActionResponse response) throws SystemException{
		long msrId = ParamUtil.getLong(request, "msrId");
		try {
			MembershipRequest msr = MembershipRequestLocalServiceUtil.getMembershipRequest(msrId);
			List<Team> teams =  TeamLocalServiceUtil.getUserTeams(msr.getUserId(), msr.getGroupId());
			if(teams!=null && teams.size()>0){
				long[] userIds = new long[1];
    			userIds[0] = msr.getUserId();
    			for(Team team : teams){
					UserLocalServiceUtil.unsetTeamUsers(team.getTeamId(), userIds);	
				}
			}			
			
			msr.setStatusId(MembershipRequestConstants.STATUS_DENIED);
			msr.setReplyDate(new Date());
			MembershipRequestLocalServiceUtil.updateMembershipRequest(msr);
			
			
		} catch (NumberFormatException e) {
			if(log.isDebugEnabled()){
				e.printStackTrace();
			}
		} catch (PortalException e) {
			if(log.isDebugEnabled()){
				e.printStackTrace();
			}
		}
	}
	

	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}
}
