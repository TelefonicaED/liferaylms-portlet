package com.liferay.lms;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.views.CourseResultView;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CourseHistory
 */
public class CourseHistory extends MVCPortlet 
{
	private static Log log = LogFactoryUtil.getLog(CourseHistory.class); 

	private String viewJSP = null;
	
	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
	}
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		int countCourses = CourseLocalServiceUtil.countFinishedCoursesOfUser(0, themeDisplay.getUserId());
		
		if(countCourses>0){
			
			SearchContainer<CourseResultView> searchContainer = new SearchContainer<CourseResultView>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
					10, renderResponse.createRenderURL(), null, "there-are-no-courses");
			
			List<CourseResultView> courses = CourseLocalServiceUtil.getFinishedCoursesOfUser(0, themeDisplay.getUserId(), themeDisplay, null, null, searchContainer.getStart(), searchContainer.getEnd());
			
			log.debug("CourseHistory::total de cursos: " + courses.size());
			
			searchContainer.setResults(courses);
			searchContainer.setTotal(countCourses);
			
			renderRequest.setAttribute("searchContainer", searchContainer);
			
			renderRequest.setAttribute("now", new Date());
			
			//Mandamos las preferencias para saber si podemos enlazar al curso
			try {
				LmsPrefs prefs = LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
				renderRequest.setAttribute("prefs", prefs);
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			log.debug("CourseHistory::no hay cursos");
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
		
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
