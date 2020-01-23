package com.liferay.lms;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CourseRatings
 */
public class CourseRatings extends MVCPortlet {

	private String viewJSP = null;

	public void init() {
		viewJSP = getInitParameter("view-template");
	}

	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException{
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		PortletPreferences prefs = null;
		String portletResource = ParamUtil.getString(renderRequest, "portletResource");	
		if (Validator.isNotNull(portletResource)){
			try {
				prefs = PortletPreferencesFactoryUtil.getPortletSetup(renderRequest, portletResource);
			} catch (PortalException | SystemException e) {
				log.error(e.getLocalizedMessage());
			}
		}
		if(Validator.isNull(prefs))
			prefs = renderRequest.getPreferences();
		
		boolean rateParentCourse = Validator.isNotNull(prefs) && Boolean.valueOf(prefs.getValue("rateParentCourse", "false"));
		
		log.debug("::::rateParentCourse::: " + rateParentCourse);
		
		Course course = null;
		try {
			course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		} catch (SystemException e) {
			log.error(e.getLocalizedMessage());
		}
		
		if(Validator.isNotNull(course)){
			
			long courseId = rateParentCourse ? course.getParentCourseId() : course.getCourseId();
			
			String courseClassName = Course.class.getName();
			
			PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();
			
			boolean hasPermission = Validator.isNotNull(permissionChecker) && permissionChecker.hasPermission(course.getGroupId(), courseClassName, courseId, ActionKeys.VIEW);
			
			log.debug("::::hasPermission::: " + hasPermission);
			
			if(hasPermission){
				
				renderRequest.setAttribute("courseId", courseId);
				renderRequest.setAttribute("courseClassName", courseClassName);
			
			}
		}
		
		include(this.viewJSP, renderRequest, renderResponse);
	}

	protected void include(String path, RenderRequest request, RenderResponse response) throws IOException, PortletException {
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);
		if (Validator.isNotNull(portletRequestDispatcher)) {
			portletRequestDispatcher.include(request, response);
		}
	}

	private static Log log = LogFactoryUtil.getLog(CourseRatings.class.getName());

}
