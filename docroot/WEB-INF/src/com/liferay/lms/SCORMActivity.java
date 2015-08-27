package com.liferay.lms;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
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

/**
 * Portlet implementation class SCORMActivity
 */
public class SCORMActivity extends MVCPortlet {
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
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
	throws PortletException, IOException {

if(ParamUtil.getLong(renderRequest, "actId", 0)==0)// TODO Auto-generated method stub
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{
		LearningActivity activity;
		try {
			activity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(renderRequest, "actId", 0));
			
			//auditing
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
			long typeId=activity.getTypeId();
			
			if(typeId==9)
			{
				super.render(renderRequest, renderResponse);
			}
			else
			{
				renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
			}
		} catch (PortalException e) {
			// TODO Auto-generated catch block
		} catch (SystemException e) {
			// TODO Auto-generated catch block
		}			
}
}

}
