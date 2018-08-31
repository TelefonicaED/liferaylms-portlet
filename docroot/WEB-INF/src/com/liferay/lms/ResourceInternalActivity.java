package com.liferay.lms;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.learningactivity.ResourceInternalLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.util.liferay.patch.PortalClassInvokerPatched;

/**
 * Portlet implementation class ResourceActivity
 */
public class ResourceInternalActivity extends MVCPortlet {
	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads";

	public void selectResource(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	
		String jspPage = ParamUtil.getString(actionRequest, "jspPage");
		long actId = ParamUtil.getLong(actionRequest, "actId", 0);
		long entryId = ParamUtil.getLong(actionRequest, "entryId", 0);
		ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivity.class.getName(), actionRequest);
	
		LearningActivity larn = LearningActivityServiceUtil.getLearningActivity(actId);
		larn.setExtracontent(Long.toString(entryId));
		LearningActivityServiceUtil.modLearningActivity(larn, serviceContext);
		SessionMessages.add(actionRequest, "activity-saved-successfully");
		actionResponse.setRenderParameter("jspPage", jspPage);
		actionResponse.setRenderParameter("actId", Long.toString(actId));
	}
	


	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException {

		if(ParamUtil.getLong(renderRequest, "actId", 0)==0){
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}else{
			LearningActivity activity;
			try {
				activity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(renderRequest, "actId", 0));
				long typeId=activity.getTypeId();
				if(typeId==ResourceInternalLearningActivityType.TYPE_ID){
					super.render(renderRequest, renderResponse);
				}else {
					renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
				}
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}	
		}
	}
	
	
	
	public void invokeTaglibDiscussion(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
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
