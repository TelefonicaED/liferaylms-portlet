package com.liferay.lms.actions;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class FinalCourseResultConfigurationAction  implements ConfigurationAction {
	public static final String JSP = "/html/finalcourseresult/config/edit.jsp";

	public String render(PortletConfig config, RenderRequest renderRequest, RenderResponse renderResponse) throws Exception 
	{
		return JSP; 
	}
	
	public void processAction( 
			PortletConfig portletConfig, ActionRequest actionRequest, 
			ActionResponse actionResponse) 
		throws Exception { 
		
		PortletPreferences portletPreferences =
		PortletPreferencesFactoryUtil.getPortletSetup( 
				actionRequest, ParamUtil.getString(actionRequest, "portletResource")); 
		
		/**
		 * boolean showAllways = 		preferences.getValue("showAllways", "false").equals("true");
	boolean showOnlyWhenFinishDate = 	preferences.getValue("showOnlyWhenFinishDate", "true").equals("true");
		 */
		

		
		portletPreferences.setValue("showCalificationMode",ParamUtil.getString(actionRequest, "showCalificationMode"));

		portletPreferences.store();
		SessionMessages.add( 
				actionRequest, portletConfig.getPortletName() + ".doConfigure"); 
		//SessionMessages.add(actionRequest,"success");
	} 
}