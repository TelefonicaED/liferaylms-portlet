package com.liferay.lms;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.views.CourseResultView;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class MyCourses
 */
public class MyCourses extends MVCPortlet {
 
	private static Log log = LogFactoryUtil.getLog(MyCourses.class); 

	private String viewJSP = null;
	
	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
	}
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException{

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		int courseOrder = Integer.parseInt(renderRequest.getPreferences().getValue("myCoursesOrder","0"));
		String orderByColumn = null;
		String orderByType = null;
		
		//ordenamos la lista
		if (courseOrder == 0){
			orderByColumn = "Lms_Course.courseId";
			orderByType = "ASC";
		}else if (courseOrder == 1){
			orderByColumn = "courseTitle";
			orderByType = "ASC";
		}else if (courseOrder == 2){
			orderByColumn = "courseTitle";
			orderByType = "DESC";
		}else if (courseOrder == 3){
			orderByColumn = "Lms_Course.executionStartDate";
			orderByType = "ASC";
		}else if (courseOrder == 4){
			orderByColumn = " Lms_Course.executionEndDate";
			orderByType = "ASC";
		}
		
		List<CourseResultView> listMyCourses = CourseLocalServiceUtil.getMyCourses(themeDisplay.getScopeGroupId(), themeDisplay.getUserId(), themeDisplay, orderByColumn, orderByType, -1, -1);	
		renderRequest.setAttribute("listMyCourses", listMyCourses);
		
		
		include(this.viewJSP, renderRequest, renderResponse);		
	}
	
	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(path);

		if (portletRequestDispatcher != null) {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}	
	}
}
