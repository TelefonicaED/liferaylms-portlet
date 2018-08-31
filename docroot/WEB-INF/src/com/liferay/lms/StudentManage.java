package com.liferay.lms;

import java.io.IOException;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.util.displayterms.UserDisplayTerms;
import com.liferay.lms.util.searchcontainer.UserSearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
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
		UserDisplayTerms displayTerms = (UserDisplayTerms) searchContainer.getDisplayTerms();
		
		Course course=CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
		renderRequest.setAttribute("course", course);
					
		log.debug("firstName: "+displayTerms.getFirstName());
		log.debug("lastName: "+displayTerms.getLastName());
		log.debug("screenName: "+displayTerms.getScreenName());
		log.debug("emailAddress: "+displayTerms.getEmailAddress());
		
		searchContainer.setResults(displayTerms.getStudents(course.getCourseId(), searchContainer.getStart(), searchContainer.getEnd()));
		searchContainer.setTotal(displayTerms.countStudents(course.getCourseId()));
		
		searchContainer.getIteratorURL().setParameter("view", "");
		renderRequest.setAttribute("searchContainer", searchContainer);
		
		PortletURL searchURL = renderResponse.createRenderURL();
		searchURL.setParameter("view", "");
		renderRequest.setAttribute("searchURL", searchURL.toString());
		
		PortletURL returnURL = renderResponse.createRenderURL();
		searchURL.setParameter("view", "");
		renderRequest.setAttribute("returnURL", returnURL.toString());
		
		renderRequest.setAttribute("displayTerms", displayTerms);
		
		log.debug("Total: "+searchContainer.getTotal());
		log.debug("usersInPage: "+searchContainer.getResults().size());
		
		include(viewJSP, renderRequest, renderResponse);
	}
}
