package com.liferay.lms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class SCORMActivity
 */
public class SCORMActivity extends MVCPortlet {
	
	private static Log log = LogFactoryUtil.getLog(SCORMActivity.class);
	
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

		if(ParamUtil.getLong(renderRequest, "actId", 0)==0){
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}else{
			LearningActivity activity;
			try {
				activity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(renderRequest, "actId", 0));

				//auditing
				ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

				long typeId=activity.getTypeId();

				if(typeId==9){

					int sessionTimeout = GetterUtil.getInteger(PropsUtil.get(PropsKeys.SESSION_TIMEOUT));
					int sessionTimeoutMinute = sessionTimeout * (int)Time.MINUTE;
					int sessionTimeoutWarning = GetterUtil.getInteger(PropsUtil.get(PropsKeys.SESSION_TIMEOUT_WARNING));

					boolean sessionRedirectOnExpire = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_TIMEOUT_REDIRECT_ON_EXPIRE));
					String sessionRedirectUrl = themeDisplay.getURLHome();

					long companyId = themeDisplay.getCompanyId();

					if (PrefsPropsUtil.getBoolean(companyId, PropsKeys.CAS_AUTH_ENABLED, GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CAS_AUTH_ENABLED))) && GetterUtil.getBoolean(PropsUtil.get(PropsKeys.CAS_LOGOUT_ON_SESSION_EXPIRATION))) {
						sessionRedirectOnExpire = true;
						sessionRedirectUrl = PrefsPropsUtil.getString(companyId, PropsKeys.CAS_LOGOUT_URL, PropsUtil.get(PropsKeys.CAS_LOGOUT_URL));
					}else if (PrefsPropsUtil.getBoolean(companyId, PropsKeys.OPEN_SSO_AUTH_ENABLED, GetterUtil.getBoolean(PropsUtil.get(PropsKeys.OPEN_SSO_AUTH_ENABLED))) && GetterUtil.getBoolean(PropsUtil.get(PropsKeys.OPEN_SSO_LOGOUT_ON_SESSION_EXPIRATION))) {
						sessionRedirectOnExpire = true;
						sessionRedirectUrl = PrefsPropsUtil.getString(companyId, PropsKeys.OPEN_SSO_LOGOUT_URL, PropsUtil.get(PropsKeys.OPEN_SSO_LOGOUT_URL));
					}

					Calendar sessionTimeoutCal = CalendarFactoryUtil.getCalendar(themeDisplay.getTimeZone());

					sessionTimeoutCal.add(Calendar.MILLISECOND, sessionTimeoutMinute);

					boolean autoExtend = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.SESSION_TIMEOUT_AUTO_EXTEND));

					renderRequest.setAttribute("sessionTimeout", sessionTimeout);
					renderRequest.setAttribute("sessionRedirectOnExpire", sessionRedirectOnExpire);
					renderRequest.setAttribute("sessionRedirectUrl", sessionRedirectUrl);
					renderRequest.setAttribute("sessionTimeoutWarning", sessionTimeoutWarning);
					renderRequest.setAttribute("autoExtend", autoExtend);
					renderRequest.setAttribute("sessionIntervale", sessionTimeoutMinute - 1000);

					log.debug("sessionTimeout: " + sessionTimeout);
					log.debug("sessionRedirectOnExpire: " + sessionRedirectOnExpire);
					log.debug("sessionRedirectUrl: " + sessionRedirectUrl);
					log.debug("sessionTimeoutWarning: " + sessionTimeoutWarning);
					log.debug("autoExtend: " + autoExtend);
					log.debug("sessionIntervale: " + (sessionTimeoutMinute - 1000));
					
					super.render(renderRequest, renderResponse);
				}else{
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
