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

import com.liferay.lms.asset.LearningActivityAssetRendererFactory;
import com.liferay.lms.events.ThemeIdEvent;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class ActivityDatesPortlet extends MVCPortlet {
	
    @ProcessEvent(qname = "{http://www.wemooc.com/}themeId")
    public void handlethemeEvent(EventRequest eventRequest, EventResponse eventResponse) {
    	
        if (eventRequest.getEvent().getValue() instanceof ThemeIdEvent){
     	   ThemeIdEvent themeIdEvent = (ThemeIdEvent) eventRequest.getEvent().getValue();
     	   long moduleId=ParamUtil.getLong(eventRequest, "moduleId",0L);
     	   if(moduleId==themeIdEvent.getModuleId()){
     		   eventResponse.setRenderParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.TRUE);
     	   }    	
     	   else if((moduleId==0)&&(themeIdEvent.getModuleId()==ThemeIdEvent.EVALUATION_THEME_ID)){
     		   eventResponse.setRenderParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.FALSE);
     	   }
        }
    }

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		
		//Cuando no tenemos actividad, ocultamos el portlet.
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
	
	public void viewactivity(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortalException, SystemException, Exception {

			long actId = ParamUtil.getInteger(actionRequest, "actId");
			LearningActivity learnact = com.liferay.lms.service.LearningActivityServiceUtil.getLearningActivity(actId);
			LearningActivityAssetRendererFactory laf = new LearningActivityAssetRendererFactory();
			if (laf != null) {
				AssetRenderer assetRenderer = laf.getAssetRenderer(actId, 0);
				String urlEdit = assetRenderer.getURLViewInContext((LiferayPortletRequest) actionRequest, (LiferayPortletResponse) actionResponse, "").toString();
				actionResponse.sendRedirect(urlEdit);
			}
			SessionMessages.add(actionRequest, "asset-renderer-not-defined");

		}

}

