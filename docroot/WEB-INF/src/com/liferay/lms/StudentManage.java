package com.liferay.lms;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletException;
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.comparator.UserLastNameComparator;
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
		log.debug("::DOVIEW:: "+jsp);
		
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
		log.debug(":::show view default::: ");		

		UserSearchContainer searchContainer = new UserSearchContainer(renderRequest, renderResponse.createRenderURL());		
		UserSearchTerms searchTerms = (UserSearchTerms) searchContainer.getSearchTerms();
		
		long teamId = searchTerms.getTeamId();
		log.debug("---teamId: "+teamId);
		
		Course course=CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
		renderRequest.setAttribute("course", course);
		
		List<Team> teams=TeamLocalServiceUtil.getGroupTeams(themeDisplay.getScopeGroupId());
		renderRequest.setAttribute("teams", teams);	
			
		if(searchTerms.isAdvancedSearch()){			
			log.debug("firstName: "+searchTerms.getFirstName());
			log.debug("lastName: "+searchTerms.getLastName());
			log.debug("screenName: "+searchTerms.getScreenName());
			log.debug("emailAddress: "+searchTerms.getEmailAddress());
			
			searchContainer.setResults(CourseLocalServiceUtil.getStudentsFromCourse(themeDisplay.getCompanyId(), course.getGroupCreatedId(),  
					searchContainer.getStart(), searchContainer.getEnd(), teamId, searchTerms.getFirstName(), searchTerms.getLastName(), 
					searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.isAndOperator()));
			searchContainer.setTotal(CourseLocalServiceUtil.getStudentsFromCourseCount(course.getCourseId(), teamId, 
					searchTerms.getFirstName(), searchTerms.getLastName(), 
					searchTerms.getScreenName(), searchTerms.getEmailAddress(), searchTerms.isAndOperator()));
		}else{
			log.debug("Keywords: "+searchTerms.getKeywords());
			searchContainer.setResults(CourseLocalServiceUtil.getStudentsFromCourse(themeDisplay.getCompanyId(), course.getGroupCreatedId(),  
					searchContainer.getStart(), searchContainer.getEnd(), teamId, searchTerms.getKeywords(), searchTerms.getKeywords(), 
					searchTerms.getKeywords(), searchTerms.getKeywords(), searchTerms.isAndOperator()));
			searchContainer.setTotal(CourseLocalServiceUtil.getStudentsFromCourseCount(course.getCourseId(), teamId, 
					 searchTerms.getKeywords(), searchTerms.getKeywords(), 
						searchTerms.getKeywords(), searchTerms.getKeywords(), searchTerms.isAndOperator()));
		}
		
		searchContainer.getIteratorURL().setParameter("view", "");
		searchContainer.getIteratorURL().setParameter("team", String.valueOf(teamId));
		renderRequest.setAttribute("searchContainer", searchContainer);
		
		PortletURL searchURL = renderResponse.createRenderURL();
		searchURL.setParameter("view", "");
		renderRequest.setAttribute("searchURL", searchURL.toString());
		
		PortletURL returnURL = renderResponse.createRenderURL();
		searchURL.setParameter("view", "");
		renderRequest.setAttribute("returnURL", returnURL.toString());
		renderRequest.setAttribute("showEmail", true);
		renderRequest.setAttribute("showScreenName", true);
		
		log.debug("Total: "+searchContainer.getTotal());
		log.debug("usersInPage: "+searchContainer.getResults().size());
		
		include(viewJSP, renderRequest, renderResponse);
	}
}
