package com.liferay.lms.actions;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.Course;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;

public class CourseAdminConfigurationAction implements ConfigurationAction {
	public static final String JSP = "/html/courseadmin/config/edit.jsp";
	private static Log log = LogFactoryUtil.getLog(CourseAdminConfigurationAction.class);
	public String render(PortletConfig config, RenderRequest renderRequest, RenderResponse renderResponse) throws Exception 
	{
		return JSP; 
	}
	
	public void processAction( 
			PortletConfig portletConfig, ActionRequest actionRequest, 
			ActionResponse actionResponse) 
		throws Exception { 
		
		if (!Constants.UPDATE.equals(actionRequest.getParameter(Constants.CMD))) {
			return;
		} 
		
		PortletPreferences portletPreferences =
		PortletPreferencesFactoryUtil.getPortletSetup( 
				actionRequest, ParamUtil.getString(actionRequest, "portletResource")); 
		
		portletPreferences.setValue("showInscriptionDate",Boolean.toString(ParamUtil.getBoolean(actionRequest, "inscriptionDate",true)));
		portletPreferences.setValue("showExecutionDate",Boolean.toString(ParamUtil.getBoolean(actionRequest, "executionDate",true)));
		portletPreferences.setValue("categories",Boolean.toString(ParamUtil.getBoolean(actionRequest, "categories",true)));
		portletPreferences.setValue("showcatalog",Boolean.toString(ParamUtil.getBoolean(actionRequest, "showcatalog",true)));
		portletPreferences.setValue("courseTemplates",	StringUtil.merge(actionRequest.getParameterMap().get( "courseTemplates")));
		portletPreferences.setValue("showCourseCatalogForEditions",Boolean.toString(ParamUtil.getBoolean(actionRequest, "showCourseCatalogForEditions",	false)));

		portletPreferences.setValue("showClose",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showClose",	true)));
		portletPreferences.setValue("showDelete",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showDelete",	true)));
		portletPreferences.setValue("showMembers",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showMembers",	true)));
		portletPreferences.setValue("showExport",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showExport",	true)));
		portletPreferences.setValue("showImport",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showImport",	true)));
		portletPreferences.setValue("showGroupFilter",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showGroupFilter",	false)));
		portletPreferences.setValue("showClone",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showClone",	true)));
		portletPreferences.setValue("showGo",		Boolean.toString(ParamUtil.getBoolean(actionRequest, "showGo",		true)));
		portletPreferences.setValue("showPermission",Boolean.toString(ParamUtil.getBoolean(actionRequest, "showPermission",	true)));
		portletPreferences.setValue("showRegistrationType", Boolean.toString(ParamUtil.getBoolean(actionRequest, "showRegistrationType",	true)));
		portletPreferences.setValue("showMaxUsers", Boolean.toString(ParamUtil.getBoolean(actionRequest, "showMaxUsers",	true)));
		
		portletPreferences.setValue("showResume",Boolean.toString(ParamUtil.getBoolean(actionRequest, "showResume",	false)));
		portletPreferences.setValue("showDescription",Boolean.toString(ParamUtil.getBoolean(actionRequest, "showDescription",	false)));

		portletPreferences.setValue("showSearchTags",Boolean.toString(ParamUtil.getBoolean(actionRequest, "showSearchTags",	false)));
		portletPreferences.setValue("showWelcomeMsg",Boolean.toString(ParamUtil.getBoolean(actionRequest, "showWelcomeMsg",	true)));
		portletPreferences.setValue("showGoodbyeMsg",Boolean.toString(ParamUtil.getBoolean(actionRequest, "showGoodbyeMsg",	true)));

		
		portletPreferences.setValue("showOnlyOrganizationUsers",Boolean.toString(ParamUtil.getBoolean(actionRequest, "showOnlyOrganizationUsers",	true)));
		portletPreferences.setValue("showCalendar",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "showCalendar",	false)));

		portletPreferences.setValue("inscriptionDateColumn",Boolean.toString(ParamUtil.getBoolean(actionRequest, "inscriptionDateColumn",	true)));
		portletPreferences.setValue("executionDateColumn",Boolean.toString(ParamUtil.getBoolean(actionRequest, "executionDateColumn",	true)));
		portletPreferences.setValue("createDateColumn",Boolean.toString(ParamUtil.getBoolean(actionRequest, "createDateColumn",	false)));
		portletPreferences.setValue("allowDuplicateName",Boolean.toString(ParamUtil.getBoolean(actionRequest, "allowDuplicateName",	false)));
		
		portletPreferences.setValue("showIconCourse",Boolean.toString(ParamUtil.getBoolean(actionRequest, "showIconCourse",	true)));
		portletPreferences.setValue("showCoursePermission",Boolean.toString(ParamUtil.getBoolean(actionRequest, "showCoursePermission",	true)));
		portletPreferences.setValue("showEditionsWithoutRestrictions",Boolean.toString(ParamUtil.getBoolean(actionRequest, "showEditionsWithoutRestrictions",	false)));

		portletPreferences.setValue("filterByTemplates",	Boolean.toString(ParamUtil.getBoolean(actionRequest, "filterByTemplates",	false)));
		
		portletPreferences.setValue("showExpandos", Boolean.toString(ParamUtil.getBoolean(actionRequest, "showExpandos", false)));
		portletPreferences.setValue("showExpandosEdition", Boolean.toString(ParamUtil.getBoolean(actionRequest, "showExpandosEdition", false)));
		
		//Campos personalizados a mostrar en la tabla
		// Expandos dinamicos
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<ExpandoColumn> expandosColumnCourse = ExpandoColumnLocalServiceUtil.getDefaultTableColumns(themeDisplay.getCompanyId(), ClassNameLocalServiceUtil.getClassNameId(Course.class));
		if(Validator.isNotNull(expandosColumnCourse) && expandosColumnCourse.size()>0) {
			String expandoName="";
			String nameExpando = "";
			String nameExpandoEdition = "";
			for (ExpandoColumn expandoCourse : expandosColumnCourse) {
				expandoName = StringUtil.upperCaseFirstLetter(expandoCourse.getName());
				nameExpando = "showExpando_" + expandoCourse.getColumnId();
				nameExpandoEdition = "showExpandoEdition_" + expandoCourse.getColumnId();
				log.debug("SAVE: show" + expandoName+ "   VALUE:  "+actionRequest.getParameter("show" + expandoName));
				portletPreferences.setValue("show" + expandoName, actionRequest.getParameter("show" + expandoName));
				portletPreferences.setValue(nameExpando, actionRequest.getParameter(nameExpando));
				portletPreferences.setValue(nameExpandoEdition, actionRequest.getParameter(nameExpandoEdition));
			}	
		}
		
		
		portletPreferences.store();
		SessionMessages.add( 
				actionRequest, portletConfig.getPortletName() + ".doConfigure"); 

		
	} 
}
