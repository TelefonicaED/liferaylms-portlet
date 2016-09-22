package com.liferay.lms.portlet;

import java.io.IOException;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.CourseHistory;
import com.liferay.lms.model.ActivityTriesDeleted;
import com.liferay.lms.service.ActivityTriesDeletedLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class ViewActivityTriesDeleted
 */
public class ViewActivityTriesDeleted extends MVCPortlet {
 
	private static Log log = LogFactoryUtil.getLog(CourseHistory.class); 

	private String viewJSP = null;
	
	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
	}
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		SearchContainer<ActivityTriesDeleted> searchContainer = new SearchContainer<ActivityTriesDeleted>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
				10, renderResponse.createRenderURL(), null, "there-are-no-activity-tries-deleted");
		
		searchContainer.setResults(ActivityTriesDeletedLocalServiceUtil.getByGroupId(themeDisplay.getScopeGroupId(), searchContainer.getStart(), searchContainer.getEnd()));
		searchContainer.setTotal(ActivityTriesDeletedLocalServiceUtil.countByGroupId(themeDisplay.getScopeGroupId()));
		
		renderRequest.setAttribute("searchContainer", searchContainer);
		
		
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
