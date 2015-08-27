package com.liferay.lms.actions;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class GeneralStatsConfigurationAction implements ConfigurationAction {
	public static final String JSP = "/html/generalstats/config/config.jsp";

	public String render(PortletConfig config, RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {
		return JSP; 
	}
	
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception { 
		
		if (!Constants.UPDATE.equals(actionRequest.getParameter(Constants.CMD))) {
			return;
		} 
		
		PortletPreferences portletPreferences =	PortletPreferencesFactoryUtil.getPortletSetup(actionRequest, ParamUtil.getString(actionRequest, "portletResource")); 
				
		portletPreferences.setValue("showSearchTagsGeneralStats",		Boolean.toString(ParamUtil.getBoolean(actionRequest, "showSearchTagsGeneralStats", false)));
		portletPreferences.setValue("showSearchCategoriesGeneralStats",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showSearchCategoriesGeneralStats", true)));
		
		portletPreferences.store();
		
		SessionMessages.add(actionRequest, portletConfig.getPortletName() + ".doConfigure"); 
	} 
}
