package com.liferay.lms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.views.CourseResultView;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
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
		
		List<CourseResultView> courses = new ArrayList<CourseResultView>();
		try {
			List<Group> groups = GroupLocalServiceUtil.getUserGroups(themeDisplay.getUserId());
			
			Course course = null;
			Group groupsel = null;
			CourseResult courseResult = null;
			Date finishDate=null;
			Date lastModuleDate=null;
			Date now = new Date();
			
			for(Group groupCourse:groups){
				
				course = CourseLocalServiceUtil.fetchByGroupCreatedId(groupCourse.getGroupId());
				
				if(course!=null && (course.isClosed() || now.after(course.getExecutionEndDate()))){
					courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), themeDisplay.getUserId());
					courses.add(new CourseResultView(course, courseResult, themeDisplay));
				} else if (course!= null){
					groupsel= GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());	
			     	courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), themeDisplay.getUserId());

					finishDate=null;
					if(courseResult!=null && courseResult.getAllowFinishDate()!=null){
						finishDate=courseResult.getAllowFinishDate();
					}
					
					lastModuleDate=null;
					for(Module module:ModuleLocalServiceUtil.findAllInGroup(groupsel.getGroupId())){
						if(lastModuleDate==null){
							lastModuleDate=module.getEndDate();
						} else if(module.getEndDate()!=null && lastModuleDate.before(module.getEndDate())){
							lastModuleDate=module.getEndDate();
						}
					}
					if(finishDate==null){
						finishDate=lastModuleDate;
					} else {
						if(lastModuleDate!=null && lastModuleDate.before(finishDate)){
							finishDate=lastModuleDate;
						}
					}
					
					if(finishDate!=null && finishDate.before(new Date())){				
						courses.add(new CourseResultView(course, courseResult, themeDisplay));
					}
				}
				
			}
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(courses.size()>0){
			
			log.debug("CourseHistory::total de cursos: " + courses.size());
			
			SearchContainer<CourseResultView> searchContainer = new SearchContainer<CourseResultView>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
					10, renderResponse.createRenderURL(), null, "there-are-no-courses");
			
			searchContainer.setResults(ListUtil.subList(courses, searchContainer.getStart(), searchContainer.getEnd()));
			searchContainer.setTotal(courses.size());
			
			renderRequest.setAttribute("searchContainer", searchContainer);
			
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
