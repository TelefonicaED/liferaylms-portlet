package com.liferay.lms;

import java.io.IOException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.ProcessEvent;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.asset.LearningActivityAssetRendererFactory;
import com.liferay.lms.events.ThemeIdEvent;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class ActivityNavigatorPortlet extends MVCPortlet {
	
	private static Log log = LogFactoryUtil.getLog(ActivityNavigatorPortlet.class);
	private String viewJSP = null;
	
	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
	}
	
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
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		
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
					
			//Comprobamos si tenemos acceso a los bloqueados
			boolean accessLock = CourseLocalServiceUtil.canAccessLock(themeDisplay.getScopeGroupId(), themeDisplay.getUser());
			 
			long moduleId = ParamUtil.getLong(renderRequest,"moduleId",0);
			long actId = ParamUtil.getLong(renderRequest,"actId",0);
			
			//Comprobamos la primera opción: seleccionado un módulo
			//Debemos tener en cuenta también los bloqueos
			if(moduleId != 0 && actId == 0){
				
				boolean foundActivity = false;
				int position = 0;
				LearningActivity learningActivity = LearningActivityLocalServiceUtil.getByPriority(position, moduleId, themeDisplay.getCompanyId());
				while(!foundActivity && learningActivity != null && !accessLock){
					//Comprobamos los permisos
					foundActivity = !learningActivity.isLocked(themeDisplay.getUser(), themeDisplay.getPermissionChecker());
					if(!foundActivity){
						position++;
						learningActivity = LearningActivityLocalServiceUtil.getByPriority(position, moduleId, themeDisplay.getCompanyId());
					}
				}
				if(learningActivity != null){
					PortletURL startURL = renderResponse.createActionURL();
					startURL.setParameter("javax.portlet.action", "viewactivity");
					startURL.setParameter("actId", String.valueOf(learningActivity.getActId()));
					renderRequest.setAttribute("startURL", startURL.toString());
				}
				
			}else if(actId != 0){
				
				//Pedimos las actividades anteriores a la nuestra
				
				LearningActivity learningActivity = null;
				try {
					learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				List<LearningActivity> listPreviusLearningActivities = LearningActivityLocalServiceUtil.getPreviusLearningActivites(learningActivity);
				
				if(listPreviusLearningActivities != null && listPreviusLearningActivities.size() > 0){
					boolean foundActivity = accessLock;
					int position = listPreviusLearningActivities.size() - 1;
					
					LearningActivity previusLearningActivity = listPreviusLearningActivities.get(position);
					
					while(!foundActivity && position >= 0 && !accessLock){
						foundActivity = !previusLearningActivity.isLocked(themeDisplay.getUser(), themeDisplay.getPermissionChecker());
						if(!foundActivity){
							position--;
							if(position >= 0){
								previusLearningActivity = listPreviusLearningActivities.get(position);
							}
						}
					}
					
					if(foundActivity && previusLearningActivity != null){
						PortletURL previusURL = renderResponse.createActionURL();
						previusURL.setParameter("javax.portlet.action", "viewactivity");
						previusURL.setParameter("actId", String.valueOf(previusLearningActivity.getActId()));
						renderRequest.setAttribute("previusURL", previusURL.toString());
					}
				}
				
				//Ahora hacemos los mismo con las siguientes
				
				List<LearningActivity> listNextLearningActivities = LearningActivityLocalServiceUtil.getNextLearningActivites(learningActivity);
				
				if(listNextLearningActivities != null && listNextLearningActivities.size() > 0){
					boolean foundActivity = accessLock;
					int position = 0;
					
					LearningActivity nextLearningActivity = listNextLearningActivities.get(position);
					
					while(!foundActivity && position < listNextLearningActivities.size() && !accessLock){
						foundActivity = !nextLearningActivity.isLocked(themeDisplay.getUser(), themeDisplay.getPermissionChecker());
						if(!foundActivity){
							position++;
							if(position < listNextLearningActivities.size()){
								nextLearningActivity = listNextLearningActivities.get(position);
							}
						}
					}
					
					if(foundActivity && nextLearningActivity != null){
						PortletURL nextURL = renderResponse.createActionURL();
						nextURL.setParameter("javax.portlet.action", "viewactivity");
						nextURL.setParameter("actId", String.valueOf(nextLearningActivity.getActId()));
						renderRequest.setAttribute("nextURL", nextURL.toString());
					}
				}
			}
			
			include(this.viewJSP, renderRequest, renderResponse);
			
		} else {
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
	
	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			// do nothing
			// _log.error(path + " is not a valid include");
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}

}

