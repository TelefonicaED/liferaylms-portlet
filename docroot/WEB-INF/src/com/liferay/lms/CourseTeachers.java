package com.liferay.lms;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CourseTeachers
 */
public class CourseTeachers extends MVCPortlet {
 
	private static Log log = LogFactoryUtil.getLog(CourseTeachers.class);
	protected String viewJSP;
	
	public void init() throws PortletException {
		// View Mode Pages
		viewJSP = getInitParameter("view-template");
	}
	
	protected void include(String path, RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		LmsPrefs lmsprefs = null;
		Course course = null;
		try {
			lmsprefs = LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
			course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(lmsprefs != null && course!=null && themeDisplay.getPermissionChecker().hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW)){
			
			log.debug("CourseTeachers::permiso para ver el curso");
			
			long teacherRoleId=lmsprefs.getTeacherRole();
			
			List<User> listTeachers = CourseLocalServiceUtil.getTeachersFromCourseTeams(course, teacherRoleId, themeDisplay.getUserId());
			
			if(listTeachers == null || listTeachers.size() == 0){
				renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
			}else{
				renderRequest.setAttribute("listTeachers", listTeachers);
			}
			
			
			
		}else{
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
		
		include(this.viewJSP, renderRequest, renderResponse);
	}
}
