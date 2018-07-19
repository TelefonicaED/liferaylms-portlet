package com.liferay.lms;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

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
	
	
	public void changeSettings(ActionRequest request , ActionResponse response) throws Exception
	{
		
		String redirect = ParamUtil.get(request, "redirect", "");
		
		String sitetemplates=StringUtil.merge(request.getParameterMap().get( "lmsTemplatesCheckbox"));
		String activitytypes=StringUtil.merge(request.getParameterMap().get( "activitiesCheckbox"));
		String calificationTypes=StringUtil.merge(request.getParameterMap().get( "calificationTypesCheckbox"));
		String courseEvalsTypes=StringUtil.merge(request.getParameterMap().get( "courseEvalsCheckbox"));
		ThemeDisplay themeDisplay  =(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean hasAPILicence = ParamUtil.getBoolean(request, "hasAPILicence");
	
		boolean showHideActivity = ParamUtil.getBoolean(request, "showHideActivity", true);
		boolean viewCoursesFinished = ParamUtil.getBoolean(request, "viewCoursesFinished", false);
		boolean linkResources = ParamUtil.getBoolean(request,"linkResources");
		
		LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId());
		prefs.setLmsTemplates(sitetemplates);
		prefs.setActivities(activitytypes);
		prefs.setCourseevals(courseEvalsTypes);
		prefs.setScoretranslators(calificationTypes);
		prefs.setHasAPILicence(hasAPILicence);
		prefs.setShowHideActivity(showHideActivity);
		prefs.setViewCoursesFinished(viewCoursesFinished);
		LmsPrefsLocalServiceUtil.updateLmsPrefs(prefs);
		savePreference(LmsConstant.RESOURCE_INTERNAL_DOCUMENT_LINKED ,String.valueOf(linkResources) , themeDisplay.getCompanyId());
		
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
