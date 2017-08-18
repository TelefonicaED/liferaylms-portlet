package com.liferay.lms;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.UpgradeUtil;
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
		
		LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId());
		prefs.setLmsTemplates(sitetemplates);
		prefs.setActivities(activitytypes);
		prefs.setCourseevals(courseEvalsTypes);
		prefs.setScoretranslators(calificationTypes);
		prefs.setHasAPILicence(hasAPILicence);
		prefs.setShowHideActivity(showHideActivity);
		prefs.setViewCoursesFinished(viewCoursesFinished);
		LmsPrefsLocalServiceUtil.updateLmsPrefs(prefs);
		
		if (Validator.isNotNull(redirect)) {
			response.sendRedirect(redirect);
		}

	}
	
	public void upgradeVersion(ActionRequest request , ActionResponse response) throws Exception{
		
		try{
			UpgradeUtil.upgrade();
			SessionMessages.add(request, "upgrade-ok");
		}catch(Exception e){
			e.printStackTrace();
			SessionErrors.add(request, e.getMessage());
		}
		
		
	
	}
}
