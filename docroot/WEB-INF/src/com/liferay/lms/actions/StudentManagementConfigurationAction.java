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

public class StudentManagementConfigurationAction implements ConfigurationAction {
	public static final String JSP = "/html/studentmanage/config/config.jsp";

	public String render(PortletConfig config, RenderRequest renderRequest, RenderResponse renderResponse) throws Exception 
	{
		return JSP; 
	}
	
	public void processAction( 
			PortletConfig portletConfig, ActionRequest actionRequest, 
			ActionResponse actionResponse) 
		throws Exception { 
		
		if (!Constants.UPDATE.equals(actionRequest.getParameter(Constants.CMD))){ 
			return;
		}
		
		PortletPreferences prefs;
		String portletResource = ParamUtil.getString(actionRequest, "portletResource");	
		if (Validator.isNotNull(portletResource)){
			prefs = PortletPreferencesFactoryUtil.getPortletSetup(actionRequest, portletResource);
		} else {
			prefs = actionRequest.getPreferences();
		}
		
		prefs.setValue("showActionSocial", actionRequest.getParameter("showActionSocial"));
		prefs.setValue("showActionAudit", actionRequest.getParameter("showActionAudit"));
		prefs.setValue("showEmailAddress", actionRequest.getParameter("showEmailAddress"));
		
		
		prefs.store();
		SessionMessages.add( 
				actionRequest, portletConfig.getPortletName() + ".doConfigure"); 
		//SessionMessages.add(actionRequest,"success");
	} 
}
