package com.liferay.lms;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.ProcessEvent;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.namespace.QName;

import com.liferay.lms.events.ThemeIdEvent;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class ModuleNavigation
 */
public class ModuleNavigation extends MVCPortlet {
 
	public void goToModule(ActionRequest actionRequest, ActionResponse actionResponse)throws Exception {
		ThemeIdEvent themeIdEvent = new ThemeIdEvent();
		themeIdEvent.setModuleId(ParamUtil.getLong(actionRequest, "moduleId",0));
		themeIdEvent.setThemeId(ParamUtil.getLong(actionRequest, "themeId",1));		
		actionResponse.setEvent(new QName("http://www.wemooc.com/" , "themeId"), themeIdEvent);
	}
	
    @ProcessEvent(qname = "{http://www.wemooc.com/}themeId")
    public void handlethemeEvent(EventRequest eventRequest, EventResponse eventResponse) {
       if (eventRequest.getEvent().getValue() instanceof ThemeIdEvent){
    	   ThemeIdEvent themeIdEvent = (ThemeIdEvent) eventRequest.getEvent().getValue();
    	   long moduleId=ParamUtil.getLong(eventRequest, "moduleId",0L);
    	   if(moduleId==themeIdEvent.getModuleId()){
    		   eventResponse.setRenderParameter("themeId",Long.toString(themeIdEvent.getThemeId()));
    		   eventResponse.setRenderParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.TRUE);
    	   }    	
    	   else if((moduleId==0)&&(themeIdEvent.getModuleId()==ThemeIdEvent.EVALUATION_THEME_ID)){
    		   eventResponse.setRenderParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.FALSE);
    	   }
       }
    }
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		
		if(ParamUtil.getLong(renderRequest, "moduleId", 0) == 0){
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
				
		if(ParamUtil.getBoolean(renderRequest, WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,true)) {
			super.doView(renderRequest, renderResponse);
		}
		else {
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
	}
}
