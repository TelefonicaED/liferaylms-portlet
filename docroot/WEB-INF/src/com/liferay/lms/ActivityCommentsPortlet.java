package com.liferay.lms;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.struts.PortletActionInvoker;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class ActivityCommentsPortlet extends MVCPortlet {

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		if(ParamUtil.getLong(renderRequest, "actId", 0) == 0){
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
				
		if(ParamUtil.getBoolean(renderRequest, WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,true)) {
			super.doView(renderRequest, renderResponse);
		}
		else {
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
	}
	

	public void addDiscussion(ActionRequest actionRequest,ActionResponse actionResponse){  
		try {  
			PortletActionInvoker.processAction("com.liferay.portlet.messageboards.action.EditDiscussionAction", null, actionRequest, actionResponse);
		} catch (Exception e) {  
			e.printStackTrace();
		}  
	}		
}

