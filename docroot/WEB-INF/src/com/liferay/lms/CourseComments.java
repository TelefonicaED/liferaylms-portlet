package com.liferay.lms;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.util.liferay.patch.PortalClassInvokerPatched;

/**
 * Portlet implementation class CourseComments
 */
public class CourseComments extends MVCPortlet {
 
	@Override
	public void invokeTaglibDiscussion(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
		
		//Se parchea porque da error al hacer comentarios con la clase por defecto del portal.
		
		PortletConfig portletConfig = getPortletConfig();
		
		PortalClassInvokerPatched.invoke(  // Notar el "Patched"
            true,
            "com.liferay.portlet.messageboards.action.EditDiscussionAction",
            "processAction",
            new String[] {
                    "org.apache.struts.action.ActionMapping",
                    "org.apache.struts.action.ActionForm",
                    PortletConfig.class.getName(), ActionRequest.class.getName(),
                    ActionResponse.class.getName()
            },
            null, null, portletConfig, actionRequest, actionResponse);
	}
}
