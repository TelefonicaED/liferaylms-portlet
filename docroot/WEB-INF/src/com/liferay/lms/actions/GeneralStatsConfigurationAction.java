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
				
		portletPreferences.setValue("categories",		Boolean.toString(ParamUtil.getBoolean(actionRequest, "showSearchTags", false)));
		portletPreferences.setValue("showFailed",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showFailed", false)));
		portletPreferences.setValue("showFinished",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showFinished", true)));
		portletPreferences.setValue("showAvgResult",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showAvgResult", true)));
		portletPreferences.setValue("showModules",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showModules", true)));
		portletPreferences.setValue("showActivities",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showActivities", true)));
		portletPreferences.setValue("showPassed",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showPassed", false)));
		portletPreferences.setValue("showInit",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showInit", true)));
		portletPreferences.setValue("showCourseClosed",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showCourseClosed", true)));
		portletPreferences.setValue("showRegistered",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showRegistered", true)));
		portletPreferences.store();
		
		SessionMessages.add(actionRequest, portletConfig.getPortletName() + ".doConfigure"); 
	} 
}
