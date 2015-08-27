package com.liferay.lms.actions;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.portlet.BaseConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class CourseExpandoValueConfigurationAction extends BaseConfigurationAction 
{
	public static final String JSP = "/html/courseexpandovalue/config/edit.jsp";

	public static final String PROPERTY_TYPE_IS_SHOWN_PREFIX = "typeIsShow_"; 

	public static String getTypeIsShownPropertyName(String type)
	{
		return PROPERTY_TYPE_IS_SHOWN_PREFIX + type;	
	}
	
	public String render(PortletConfig config, RenderRequest renderRequest, RenderResponse renderResponse) throws Exception 
	{
		return JSP; 
	}
	public void processAction(PortletConfig portletConfig, 
			ActionRequest actionRequest,ActionResponse actionResponse) throws Exception 
    {	
		PortletPreferences prefs = actionRequest.getPreferences();
		String portletResource = ParamUtil.getString(actionRequest, "portletResource");	
		if (Validator.isNotNull(portletResource))
		{
			prefs = PortletPreferencesFactoryUtil.getPortletSetup(actionRequest, portletResource);
		}
		prefs.setValue("expandoColumn", actionRequest.getParameter("expandoColumn"));
		
		prefs.store();
		SessionMessages.add(actionRequest,"success");
    }
}
