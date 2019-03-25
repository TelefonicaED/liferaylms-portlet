package com.liferay.lms.actions;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class AsynchronousConfigurationAction implements ConfigurationAction {
	
	public static final String JSP = "/html/asynchronousprocessdashboard/config/config.jsp";
	
	private static Log log = LogFactoryUtil.getLog(AsynchronousConfigurationAction.class);
	
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
		
		
		
		String refreshPageEachXSeg = ParamUtil.getString(actionRequest,"refreshPageEachXSeg", "");
		portletPreferences.setValue("refreshPageEachXSeg", refreshPageEachXSeg);
		
		String showAllClassName = ParamUtil.getString(actionRequest,"preferences--showAllClassName--", "");
		portletPreferences.setValue("preferences--showAllClassName--", showAllClassName);
		
		String classNameValue ="";
		if(showAllClassName.equalsIgnoreCase("true")){ 
			classNameValue = "todos"; 
		}else{
			classNameValue = StringUtil.merge(actionRequest.getParameterMap().get( "className"));
		}
		portletPreferences.setValue("className",classNameValue);
			
		String onlyForUserOwner = ParamUtil.getString(actionRequest,"preferences--onlyForUserOwner--", "");
		portletPreferences.setValue("preferences--onlyForUserOwner--", onlyForUserOwner);
		
		String showExtraContent = ParamUtil.getString(actionRequest,"preferences--showExtraContent--", "");
		portletPreferences.setValue("preferences--showExtraContent--", showExtraContent);

		portletPreferences.store();
		
		SessionMessages.add( 
				actionRequest, portletConfig.getPortletName() + ".doConfigure"); 

		
	} 
}
