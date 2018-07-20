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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class ModuleListAction implements ConfigurationAction {

	public static final String JSP = "/html/modulelist/config/moduleListConfig.jsp";

	public String render(PortletConfig config, RenderRequest renderRequest, RenderResponse renderResponse) throws Exception 
	{
		return JSP; 
	}
	
	

	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse arg2) throws Exception {
		if (!Constants.UPDATE.equals(actionRequest.getParameter(Constants.CMD))) 
			return;
		
		PortletPreferences prefs;
		String portletResource = ParamUtil.getString(actionRequest, "portletResource");	
		if (Validator.isNotNull(portletResource)){
			prefs = PortletPreferencesFactoryUtil.getPortletSetup(actionRequest, portletResource);
		} else {
			prefs = actionRequest.getPreferences();
		}
		prefs.setValue("showLockedModulesIcon", actionRequest.getParameter("showLockedModulesIcon"));
		prefs.setValue("showModuleIcon", actionRequest.getParameter("showModuleIcon"));
		prefs.setValue("numerateModules", actionRequest.getParameter("numerateModules"));
		prefs.setValue("moduleTitleLinkable", actionRequest.getParameter("moduleTitleLinkable"));
		prefs.setValue("showPercentDone", actionRequest.getParameter("showPercentDone"));
		prefs.setValue("showModuleStartDate", actionRequest.getParameter("showModuleStartDate"));
		prefs.setValue("showModuleEndDate", actionRequest.getParameter("showModuleEndDate"));
		prefs.setValue("allowEditionMode", actionRequest.getParameter("allowEditionMode"));
		prefs.setValue("allowAccessWhenFinishedButNotClosed", actionRequest.getParameter("allowAccessWhenFinishedButNotClosed"));
		prefs.setValue("dragAndDrop", actionRequest.getParameter("dragAndDrop"));
		prefs.setValue("showActivities", actionRequest.getParameter("showActivities"));
		
		prefs.store();
		SessionMessages.add(actionRequest,portletConfig.getPortletName() + ".doConfigure");
		
	} 
}