package com.liferay.lms;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;


import com.liferay.lms.model.Course;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.util.searchcontainer.UserSearchContainer;
import com.liferay.lms.util.searchterms.UserSearchTerms;
import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.comparator.UserLastNameComparator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;


/**
 * Portlet implementation class StudentManage
 */
public class StudentManage extends MVCPortlet {
	private static Log log = LogFactoryUtil.getLog(StudentManage.class);
	protected String viewJSP;
	public void init() throws PortletException {
		// View Mode Pages
		viewJSP = getInitParameter("view-jsp");
		
	}
	protected void include(String path, RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);
		if (portletRequestDispatcher != null) {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}	
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		String jsp = renderRequest.getParameter("view");		
		log.debug("::DOVIEW::"+jsp);
		
		try{
			if (jsp == null || jsp.equals("")) {
				showViewDefault(renderRequest, renderResponse);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void showViewDefault(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException, SystemException, PortalException{		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);	
		log.debug(":::show view default:::");		

		UserSearchContainer searchContainer = new UserSearchContainer(renderRequest, renderResponse.createRenderURL());		
		UserSearchTerms searchTerms = (UserSearchTerms) searchContainer.getSearchTerms();
		
		long teamId = searchTerms.getTeamId();
		log.debug("---teamId:"+teamId);
		
		Course course=CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
		renderRequest.setAttribute("course", course);
		
		List<Team> userTeams=TeamLocalServiceUtil.getUserTeams(themeDisplay.getUserId(), themeDisplay.getScopeGroupId());
		
		Team theTeam=null;
		boolean hasNullTeam=false;
		if(teamId>0 && (TeamLocalServiceUtil.hasUserTeam(themeDisplay.getUserId(), teamId)||userTeams.size()==0)){		
			theTeam=TeamLocalServiceUtil.fetchTeam(teamId);	
		}else{
			if(userTeams!=null&& userTeams.size()>0){
				theTeam=userTeams.get(0);	
				teamId=theTeam.getTeamId();
			}
		}
		
		if(userTeams.size()==0){
			userTeams=TeamLocalServiceUtil.getGroupTeams(themeDisplay.getScopeGroupId());
			hasNullTeam=true;
		}
		
		renderRequest.setAttribute("hasNullTeam", hasNullTeam);
		renderRequest.setAttribute("theTeam", theTeam);
		renderRequest.setAttribute("userTeams", userTeams);	
		
		LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());		
		
		OrderByComparator obc = new   UserLastNameComparator(true);			
		LinkedHashMap userParams = new LinkedHashMap();

		userParams.put("notInCourseRoleTeach", new CustomSQLParam("WHERE User_.userId NOT IN "
	              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
	              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
	            	themeDisplay.getScopeGroupId(),
	              RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId() }));
	           


	   userParams.put("notInCourseRoleEdit", new CustomSQLParam("WHERE User_.userId NOT IN "
	              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
	              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
	            	themeDisplay.getScopeGroupId(),
	              RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId() }));
	           
		
	    userParams.put("usersGroups", new Long(themeDisplay.getScopeGroupId()));
	    
		if(theTeam!=null) userParams.put("usersTeams", theTeam.getTeamId());
		
				
		List<User> users = null; 
		int total = 0;		

		if(searchTerms.isAdvancedSearch()){			
			log.debug("firstName:"+searchTerms.getFirstName());
			log.debug("lastName:"+searchTerms.getLastName());
			log.debug("screenName:"+searchTerms.getScreenName());
			log.debug("emailAddress:"+searchTerms.getEmailAddress());
			
			users= UserLocalServiceUtil.search(themeDisplay.getCompanyId(), searchTerms.getFirstName(), StringPool.BLANK, 
					searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), 0, userParams, searchTerms.isAndOperator(), 
					searchContainer.getStart(), searchContainer.getEnd(), obc);

			total= UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), searchTerms.getFirstName(), StringPool.BLANK,
					searchTerms.getLastName(), searchTerms.getScreenName(), searchTerms.getEmailAddress(), 0, userParams, searchTerms.isAndOperator());
			
		}else{
			log.debug("Keywords:"+searchTerms.getKeywords());
			
			users= UserLocalServiceUtil.search(themeDisplay.getCompanyId(), searchTerms.getKeywords(), 0, userParams, 
					searchContainer.getStart(), searchContainer.getEnd(), obc);
			total= UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), searchTerms.getKeywords(), WorkflowConstants.STATUS_APPROVED, userParams);
		}	
		
		searchContainer.setResults(users);
		searchContainer.setTotal(total);
		
		searchContainer.getIteratorURL().setParameter("view", "");
		searchContainer.getIteratorURL().setParameter("teamId", Long.toString(teamId));
		renderRequest.setAttribute("searchContainer", searchContainer);
		
		PortletURL searchURL = renderResponse.createRenderURL();
		searchURL.setParameter("view", "");
		searchURL.setParameter("teamId", Long.toString(teamId));
		renderRequest.setAttribute("searchURL", searchURL.toString());
		
		PortletURL returnURL = renderResponse.createRenderURL();
		searchURL.setParameter("view", "");
		searchURL.setParameter("teamId", Long.toString(teamId));
		renderRequest.setAttribute("returnURL", returnURL.toString());
		
		log.debug("Total:"+searchContainer.getTotal());
		log.debug("usersInPage:"+users.size());
		
		include(viewJSP, renderRequest, renderResponse);
	}
}
