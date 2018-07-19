package com.liferay.lms;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.util.displayterms.UserDisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.comparator.UserFirstNameComparator;
import com.liferay.portal.util.comparator.UserLastNameComparator;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class StudentSearch
 */
public class StudentSearch extends MVCPortlet {
 
	private String viewJSP = null;

	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
	}

	private static Log log = LogFactoryUtil.getLog(StudentSearch.class);
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		log.debug(":: VIEW STUDENT SEARCH "+this.viewJSP);
		PortletPreferences preferences = renderRequest.getPreferences();
		boolean showSearcher = GetterUtil.getBoolean(preferences.getValue("showSearcher", StringPool.TRUE));
			
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		UserDisplayTerms userDisplayTerms = new UserDisplayTerms(renderRequest);
				
		//Buscamos los usuario
		PortletURL iteratorURL = renderResponse.createRenderURL();
		iteratorURL.setParameter("teamId", Long.toString(userDisplayTerms.getTeamId()));
		iteratorURL.setParameter("firstName", userDisplayTerms.getFirstName());
		iteratorURL.setParameter("lastName", userDisplayTerms.getLastName());
		iteratorURL.setParameter("screenName", userDisplayTerms.getScreenName());
		iteratorURL.setParameter("emailAddress", userDisplayTerms.getEmailAddress());
		iteratorURL.setParameter("showSearcher" ,  String.valueOf(showSearcher));
		SearchContainer<User> searchContainer = new SearchContainer<User>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
				10, iteratorURL, 
				null, "there-are-no-users");
		
		List<User> results = null;
		int total = 0;
		try {
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
			OrderByComparator obc = null;
			PortletPreferences portalPreferences = PortalPreferencesLocalServiceUtil.getPreferences(themeDisplay.getCompanyId(), themeDisplay.getCompanyId(), 1);
			if(Boolean.parseBoolean(portalPreferences.getValue("users.first.last.name", "false"))){
				obc = new UserLastNameComparator(true);
			}else{
				obc = new UserFirstNameComparator(true);
			}
			results = CourseLocalServiceUtil.getStudents(course.getCourseId(), themeDisplay.getCompanyId(), userDisplayTerms.getScreenName(), userDisplayTerms.getFirstName(), 
					userDisplayTerms.getLastName(), userDisplayTerms.getEmailAddress(), WorkflowConstants.STATUS_APPROVED, userDisplayTerms.getTeamId(), true, searchContainer.getStart(),
					searchContainer.getEnd(), obc);

			total = CourseLocalServiceUtil.countStudents(course.getCourseId(), themeDisplay.getCompanyId(), userDisplayTerms.getScreenName(), userDisplayTerms.getFirstName(), 
					userDisplayTerms.getLastName(), userDisplayTerms.getEmailAddress(), WorkflowConstants.STATUS_APPROVED, userDisplayTerms.getTeamId(), true);
			
			searchContainer.setResults(results);
			searchContainer.setTotal(total);
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PortletURL renderURL = renderResponse.createRenderURL();
		renderRequest.setAttribute("renderURL", renderURL.toString());
		renderRequest.setAttribute("displayTerms", userDisplayTerms);
		renderRequest.setAttribute("searchContainer", searchContainer);
		renderRequest.setAttribute("showSearcher", showSearcher);
		this.include(this.viewJSP, renderRequest, renderResponse);
		
	}
	
	protected void include(String path, RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);
	
		if (portletRequestDispatcher == null) {
			
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}


}
