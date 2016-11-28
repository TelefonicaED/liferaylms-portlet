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
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.util.searchcontainer.UserSearchContainer;
import com.liferay.lms.util.displayterms.UserDisplayTerms;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
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
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			
			log.debug(":: VIEW STUDENT SEARCH "+this.viewJSP);
			
			PortletPreferences preferences = renderRequest.getPreferences();
			boolean showSearcher = GetterUtil.getBoolean(preferences.getValue("showSearcher", StringPool.TRUE));
			boolean showScreenName = GetterUtil.getBoolean(preferences.getValue("showScreenName", StringPool.TRUE));
			boolean showEmail = GetterUtil.getBoolean(preferences.getValue("showEmail", StringPool.FALSE));
			
			
			long teamId = ParamUtil.getLong(renderRequest, "team",0);
			
			List<Team> teams = TeamLocalServiceUtil.getGroupTeams(themeDisplay.getScopeGroupId());
			
			
			renderRequest.setAttribute("showSearcher", showSearcher);
			renderRequest.setAttribute("showScreenName", showScreenName);
			renderRequest.setAttribute("showEmail", showEmail);

			PortletURL iteratorURL = renderResponse.createRenderURL();
			iteratorURL.setParameter("team" ,  String.valueOf(teamId));
			
			/*iteratorURL.setParameter("showSearcher" ,  String.valueOf(showSearcher));
			iteratorURL.setParameter("showScreenName" , String.valueOf(showScreenName));
			iteratorURL.setParameter("showEmail" ,  String.valueOf(showEmail));*/
			
			
			UserSearchContainer userSearchContainer = new UserSearchContainer(renderRequest, iteratorURL);
			UserDisplayTerms displayTerms  = (UserDisplayTerms)userSearchContainer.getDisplayTerms();
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("usersGroups", new Long(themeDisplay.getScopeGroupId())); 
			
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		
			if(log.isDebugEnabled()){
				log.debug("NAME "+displayTerms.getFirstName());
				log.debug("SURNAME "+displayTerms.getLastName());
				log.debug("SCREEN NAME "+displayTerms.getScreenName());
				log.debug("EMAIL ADDRESS "+displayTerms.getEmailAddress());
				log.debug("KEYWORDS "+displayTerms.getKeywords());
				log.debug("START "+userSearchContainer.getStart());
				log.debug("END  "+userSearchContainer.getEnd());
				
				log.debug("ADVANCED SEARCH "+displayTerms.isAdvancedSearch());
				log.debug("AND OPERATOR "+displayTerms.isAndOperator());
				log.debug("TEAM "+teamId);
				
			}
			
			
			
			if(log.isDebugEnabled()){
				log.debug("NAME "+ParamUtil.getString(renderRequest, "firstName"));
				
			}
			
			if(course!=null){
				try {
					if(displayTerms.isAdvancedSearch()){		
						userSearchContainer.setResults(CourseLocalServiceUtil.getStudentsFromCourse(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), 
										userSearchContainer.getStart(), userSearchContainer.getEnd(), teamId, displayTerms.getFirstName(),  
										displayTerms.getLastName(), displayTerms.getScreenName(), displayTerms.getEmailAddress(), displayTerms.isAndOperator()));
						
						userSearchContainer.setTotal(CourseLocalServiceUtil.getStudentsFromCourseCount(course.getCourseId(), teamId, displayTerms.getFirstName(), 
													displayTerms.getLastName(), displayTerms.getScreenName(), 
													displayTerms.getEmailAddress(), displayTerms.isAndOperator()));
						
					}else{
						userSearchContainer.setResults(CourseLocalServiceUtil.getStudentsFromCourse(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), 
								userSearchContainer.getStart(), userSearchContainer.getEnd(), teamId, displayTerms.getKeywords(),  
								displayTerms.getKeywords(), displayTerms.getKeywords(), displayTerms.getKeywords(), true));
				
						userSearchContainer.setTotal(CourseLocalServiceUtil.getStudentsFromCourseCount(course.getCourseId(), teamId, 
											displayTerms.getKeywords(), displayTerms.getKeywords(), displayTerms.getKeywords(), 
											displayTerms.getKeywords(), true));
					}

				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(log.isDebugEnabled()){
				log.debug(" -- TOTAL: "+userSearchContainer.getTotal());
				log.debug(" -- SIZE: "+userSearchContainer.getResults().size());
				
			}
			
			renderRequest.setAttribute("searchContainer", userSearchContainer);
			renderRequest.setAttribute("team", teamId);
			renderRequest.setAttribute("teams", teams);
			
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

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
