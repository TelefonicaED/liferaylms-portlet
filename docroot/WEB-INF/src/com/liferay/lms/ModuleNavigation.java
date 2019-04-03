package com.liferay.lms;

import java.io.IOException;
import java.util.List;

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
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class ModuleNavigation
 */
public class ModuleNavigation extends MVCPortlet {
 
	private static Log log = LogFactoryUtil.getLog(ModuleNavigation.class);
	
	public void goToModule(ActionRequest actionRequest, ActionResponse actionResponse)throws Exception {
		ThemeIdEvent themeIdEvent = new ThemeIdEvent();
		themeIdEvent.setModuleId(ParamUtil.getLong(actionRequest, "moduleId",0));
		themeIdEvent.setThemeId(ParamUtil.getLong(actionRequest, "themeId",1));	
		themeIdEvent.setActId(ParamUtil.getLong(actionRequest, "actId",0));
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
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		boolean actionEditingDetails = ParamUtil.getBoolean(renderRequest, "actionEditingDetails", false);
		boolean actionEditingActivity = ParamUtil.getBoolean(renderRequest, "actionEditingActivity", false);
		boolean actionEditingModule = ParamUtil.getBoolean(renderRequest, "actionEditingModule", false);
		boolean actionCalifications = ParamUtil.getBoolean(renderRequest, "actionCalifications", false);
		
		log.debug("actionEditingDetails:"+actionEditingDetails);
		log.debug("actionEditingActivity:"+actionEditingActivity);
		log.debug("actionEditingModule:"+actionEditingModule);
		log.debug("actionCalifications:"+actionCalifications);
		
		//Cuando no tenemos actividad ni modulo, ocultamos el portlet.
		if( (ParamUtil.getLong(renderRequest, "actId", 0) == 0 && ParamUtil.getLong(renderRequest, "moduleId", 0) == 0)
				|| actionEditingDetails || actionEditingActivity || actionEditingModule || actionCalifications){
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
				
		if(ParamUtil.getBoolean(renderRequest, WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,true)) {
		
		
			long moduleId = ParamUtil.getLong(renderRequest,"moduleId",0);
			long currentActId = ParamUtil.getLong(renderRequest,"actId",0);
			log.debug("******doView - currentActId: " + currentActId);	
			
			//ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
	
			//obtener la primera y la ultima actividad del modulo, si es la primera y hay un modulo anterior, mostrar el enlace del modulo anterior,
			//si es la ultima actividad y hay siguiente modulo, mostrar el enlace del siguiente modulo.
			long firstActId = 0L;
			long lastActId = 0L;	
			if (moduleId > 0){
				try{
					DynamicQuery q = DynamicQueryFactoryUtil.forClass(LearningActivity.class);
					q.add(RestrictionsFactoryUtil.eq("moduleId", moduleId));
					q.addOrder(OrderFactoryUtil.asc("priority"));				
					List<LearningActivity> lActivitiesByModuleId  = LearningActivityLocalServiceUtil.dynamicQuery(q);
					if (lActivitiesByModuleId!= null && lActivitiesByModuleId.size()>0){
						
						for (int i=0;i<lActivitiesByModuleId.size();i++){
							log.debug("1-for: " + i + " - themeDisplay.getUserId(): " + themeDisplay.getUserId());
							if (firstActId == 0L && themeDisplay.getPermissionChecker().hasPermission(lActivitiesByModuleId.get(i).getGroupId(),LearningActivity.class.getName(),lActivitiesByModuleId.get(i).getActId(), ActionKeys.VIEW)){							
								log.debug("if canBeView");
								firstActId = lActivitiesByModuleId.get(i).getActId();
								break;
							}
						}
						
						for (int z=lActivitiesByModuleId.size()-1;z>=0;z--){
							log.debug("2-for: " + z+ " - themeDisplay.getUserId(): " + themeDisplay.getUserId());
							if (lastActId == 0L && themeDisplay.getPermissionChecker().hasPermission(lActivitiesByModuleId.get(z).getGroupId(),LearningActivity.class.getName(),lActivitiesByModuleId.get(z).getActId(), ActionKeys.VIEW)){
								log.debug("if canBeView");
								lastActId = lActivitiesByModuleId.get(z).getActId();
								break;
							}
						}
						
					}
					log.debug("****** doView - first actId: " + firstActId + " -- lastActId: " + lastActId);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			
			renderRequest.setAttribute("firstActId", Long.toString(firstActId));
			renderRequest.setAttribute("lastActId", Long.toString(lastActId));
	
	
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
}
