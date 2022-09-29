package com.liferay.lms;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class LmsConfig
 */
public class LmsConfig extends MVCPortlet {
	
	public void changeSettings(ActionRequest request , ActionResponse response) throws Exception{		
		
		String redirect = ParamUtil.get(request, "redirect", "");
		
		String sitetemplates=StringUtil.merge(request.getParameterMap().get( "lmsTemplatesCheckbox"));
		String activitytypes=StringUtil.merge(request.getParameterMap().get( "activitiesCheckbox"));
		String calificationTypes=StringUtil.merge(request.getParameterMap().get( "calificationTypesCheckbox"));
		String courseEvalsTypes=StringUtil.merge(request.getParameterMap().get( "courseEvalsCheckbox"));
		String inscriptionTypes=StringUtil.merge(request.getParameterMap().get( "inscriptionTypesCheckbox"));
		boolean checkExecutionDate = ParamUtil.getBoolean(request,"checkExecutionDate");
		ThemeDisplay themeDisplay  =(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean hasAPILicence = ParamUtil.getBoolean(request, "hasAPILicence");
	
		boolean showHideActivity = ParamUtil.getBoolean(request, "showHideActivity", true);
		boolean showModuleClassification = ParamUtil.getBoolean(request, "showModuleClassification", false);
		boolean showActivityClassification = ParamUtil.getBoolean(request, "showActivityClassification", true);
		boolean viewCoursesFinished = ParamUtil.getBoolean(request, "viewCoursesFinished", false);
		int viewCourseFinishedType = ParamUtil.getInteger(request, "viewCourseFinishedType", LmsConstant.VIEW_COURSE_FINISHED_TYPE_DEFAULT);
		
		boolean showCompletedOpenCoursesInProgress = ParamUtil.getBoolean(request,"showCompletedOpenCoursesInProgress");
		boolean allowWeightlessMandatoryActivities = ParamUtil.getBoolean(request,"allowWeightlessMandatoryActivities");
		boolean linkResources = ParamUtil.getBoolean(request,"linkResources");
		boolean sendMailToEditors = ParamUtil.getBoolean(request, "sendMailToEditors");
		boolean sendMailToTutors = ParamUtil.getBoolean(request, "sendMailToTutors");
		boolean showButtonInscriptionAll = ParamUtil.getBoolean(request, "showButtonInscriptionAll", true);
		boolean showButtonUnsubscribeAll = ParamUtil.getBoolean(request, "showButtonUnsubscribeAll", true);
		boolean showOptionTest = ParamUtil.getBoolean(request, "showOptionTest", false);
		boolean accessCoursesExecutionDate = ParamUtil.getBoolean(request, "accessCoursesExecutionDate", true);
		boolean accessQualitySurveyExecutionDate = ParamUtil.getBoolean(request, "accessQualitySurveyExecutionDate", true);
		boolean showInspectorRole = ParamUtil.getBoolean(request, "showInspectorRole", true);
		
		
		LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId());
		prefs.setLmsTemplates(sitetemplates);
		prefs.setActivities(activitytypes);
		prefs.setCourseevals(courseEvalsTypes);
		prefs.setScoretranslators(calificationTypes);
		prefs.setInscriptionTypes(inscriptionTypes);
		prefs.setHasAPILicence(hasAPILicence);
		prefs.setShowHideActivity(showHideActivity);
		prefs.setViewCoursesFinished(viewCoursesFinished);
		LmsPrefsLocalServiceUtil.updateLmsPrefs(prefs);
		savePreference(LmsConstant.CHECK_EXECUTION_DATE ,String.valueOf(checkExecutionDate) , themeDisplay.getCompanyId());
		savePreference(LmsConstant.SHOW_COMPLETED_OPEN_COURSES_INPROGRESS ,String.valueOf(showCompletedOpenCoursesInProgress) , themeDisplay.getCompanyId());
		savePreference(LmsConstant.ALLOW_WEIGHTLESS_MANDATORY_ACTIVITIES ,String.valueOf(allowWeightlessMandatoryActivities) , themeDisplay.getCompanyId());
		savePreference(LmsConstant.RESOURCE_INTERNAL_DOCUMENT_LINKED ,String.valueOf(linkResources) , themeDisplay.getCompanyId());
		savePreference(LmsConstant.SHOW_MODULE_CLASSIFICATION, String.valueOf(showModuleClassification), themeDisplay.getCompanyId());
		savePreference(LmsConstant.SHOW_ACTIVITY_CLASSIFICATION, String.valueOf(showActivityClassification), themeDisplay.getCompanyId());
		savePreference(LmsConstant.SEND_MAIL_TO_EDITORS, String.valueOf(sendMailToEditors), themeDisplay.getCompanyId());
		savePreference(LmsConstant.SEND_MAIL_TO_TUTORS, String.valueOf(sendMailToTutors), themeDisplay.getCompanyId());
		savePreference(LmsConstant.SHOW_BUTTON_INSCRIPTION_ALL, String.valueOf(showButtonInscriptionAll), themeDisplay.getCompanyId());
		savePreference(LmsConstant.SHOW_BUTTON_UNSUBSCRIBE_ALL, String.valueOf(showButtonUnsubscribeAll), themeDisplay.getCompanyId());
		savePreference(LmsConstant.PREFS_VIEW_COURSE_FINISHED_TYPE, String.valueOf(viewCourseFinishedType), themeDisplay.getCompanyId());
		savePreference(LmsConstant.PREFS_SHOW_OPTION_TEST, String.valueOf(showOptionTest), themeDisplay.getCompanyId());
		savePreference(LmsConstant.PREFS_ACCESS_COURSE_EXECUTION_DATES, String.valueOf(accessCoursesExecutionDate), themeDisplay.getCompanyId());
		savePreference(LmsConstant.PREFS_ACCESS_QUALITY_SURVEY_EXECUTION_DATES, String.valueOf(accessQualitySurveyExecutionDate), themeDisplay.getCompanyId());
		savePreference(LmsConstant.PREFS_SHOW_INSPECTOR_ROLE, String.valueOf(showInspectorRole), themeDisplay.getCompanyId());
		
		LearningActivityTypeRegistry.resetLearningActivityTypes();
		
		if (Validator.isNotNull(redirect)) {
			response.sendRedirect(redirect);
		}
	}
	
	
	private boolean savePreference(String key,String value, long companyId) throws SystemException {
		
		PortletPreferences prefs= PortalPreferencesLocalServiceUtil.getPreferences(companyId, companyId, 1);
		boolean error = false;
		if(!"".equals(key)&&!prefs.isReadOnly(key))
		{
			try {
				prefs.setValue(key, value);
			} catch (ReadOnlyException e) {
				e.printStackTrace();
				error=true;
			}
			try {
				prefs.store();
			} catch (ValidatorException e) {
				e.printStackTrace();
				error=true;
			} catch (IOException e) {
				e.printStackTrace();
				error=true;
			}
		}
		else
		{
			error=true;
		}
		return error;
	}
}
