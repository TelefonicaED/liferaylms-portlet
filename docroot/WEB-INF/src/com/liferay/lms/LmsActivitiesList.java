
package com.liferay.lms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.ProcessEvent;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;

import com.liferay.lms.asset.LearningActivityAssetRendererFactory;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.events.ThemeIdEvent;
import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.learningactivity.ResourceExternalLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.P2pActivityCorrections;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletQNameUtil;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class LmsActivitiesList
 */

public class LmsActivitiesList extends MVCPortlet {
	private Log log = LogFactoryUtil.getLog(LmsActivitiesList.class);
	
    @ProcessEvent(qname = "{http://www.wemooc.com/}themeId")
    public void handlethemeEvent(EventRequest eventRequest, EventResponse eventResponse) {
    	
        if (eventRequest.getEvent().getValue() instanceof ThemeIdEvent){
     	   ThemeIdEvent themeIdEvent = (ThemeIdEvent) eventRequest.getEvent().getValue();
     	   long moduleId=ParamUtil.getLong(eventRequest, "moduleId",0L);
     	   if(moduleId==themeIdEvent.getModuleId()){
     		   eventResponse.setRenderParameter("viewCurrentModule",StringPool.TRUE);
     	   }    	
     	   else if((moduleId==0)&&(themeIdEvent.getModuleId()==ThemeIdEvent.EVALUATION_THEME_ID)){
     		   eventResponse.setRenderParameter("viewCurrentModule",StringPool.FALSE);
     	   }
        }
    }

	public void deleteMyTries(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {
		long actId = ParamUtil.getLong(actionRequest, "resId", 0);
		String redirect = ParamUtil.getString(actionRequest, "redirect");
		if(actId>0)
		{
		LearningActivity larn=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		actionRequest.setAttribute("editing", "true");
		LearningActivityTryLocalServiceUtil.deleteUserTries(actId, themeDisplay.getUserId());
		if(P2pActivityLocalServiceUtil.existP2pAct(actId, themeDisplay.getUserId()))
		{
			P2pActivity p2pact=P2pActivityLocalServiceUtil.findByActIdAndUserId(actId, themeDisplay.getUserId());
			P2pActivityLocalServiceUtil.deleteP2pActivity(p2pact.getP2pActivityId());
			
			java.util.List<P2pActivityCorrections> p2pactcorrcs=P2pActivityCorrectionsLocalServiceUtil.findByP2pActivityId(p2pact.getP2pActivityId());
			for(P2pActivityCorrections p2pactcorr:p2pactcorrcs)
			{
				P2pActivityCorrectionsLocalServiceUtil.deleteP2pActivityCorrections(p2pactcorr);
			}
			
		}
		
		actionRequest.setAttribute("activity", larn);
		}
		WindowState windowState = actionRequest.getWindowState();
		if (redirect != null && !"".equals(redirect)) {
			if (!windowState.equals(LiferayWindowState.POP_UP)) {
				actionResponse.sendRedirect(redirect);
			}
			else {
				redirect = PortalUtil.escapeRedirect(redirect);

				if (Validator.isNotNull(redirect)) {
					actionResponse.sendRedirect(redirect);
				}
			}
		}
		
		
	}
	public void saveActivity(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		UploadRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);

		if(log.isDebugEnabled()){
			Enumeration<String> parNames= uploadRequest.getParameterNames();
			while(parNames.hasMoreElements()){
				String paramName=parNames.nextElement();
				log.debug(paramName+"::"+uploadRequest.getParameter(paramName));
			}
		}
		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivity.class.getName(), actionRequest);
		
		List<Long> assetCategoryIdsList = new ArrayList<Long>();
		boolean updateAssetCategoryIds = false;
		
		for (String name:Collections.list((Enumeration<String>)uploadRequest.getParameterNames())){
			if (name.startsWith("assetCategoryIds")) {
				updateAssetCategoryIds = true;
				for (long assetCategoryId : StringUtil.split(
						ParamUtil.getString(uploadRequest, name), 0L)) {
					assetCategoryIdsList.add(assetCategoryId);
				}
			}
		}
		
		if (updateAssetCategoryIds) {
			serviceContext.setAssetCategoryIds(ArrayUtil.toArray(
					assetCategoryIdsList.toArray(
							new Long[assetCategoryIdsList.size()])));
		}
	
		String assetTagNames = uploadRequest.getParameter("assetTagNames");

		if (assetTagNames != null) {
			serviceContext.setAssetTagNames(StringUtil.split(assetTagNames));
		}
		
		ThemeDisplay themeDisplay = (ThemeDisplay) uploadRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
		String redirect = ParamUtil.getString(uploadRequest, "redirect");
		if(Validator.isNull(redirect)) {
			for(Map.Entry<String, String[]> parameter: (Set<Map.Entry<String,  String[]>>) uploadRequest.getParameterMap().entrySet()){
				if(parameter.getValue()!=null) {
					actionResponse.setRenderParameter(parameter.getKey(), parameter.getValue());
				}
			}
		}
		uploadRequest.setAttribute("editing", "true");
		User user = themeDisplay.getUser();
		long actId = ParamUtil.getLong(uploadRequest, "resId", 0);
		long moduleId = ParamUtil.getLong(uploadRequest, "resModuleId", 0);
		long weightinmodule=ParamUtil.getLong(uploadRequest, "weightinmodule", 0);
		long precedence=ParamUtil.getLong(uploadRequest, "precedence", 0);
		//String title = actionRequest.getParameter("title");
		
		String description = uploadRequest.getParameter("description");
		int type = ParamUtil.getInteger(uploadRequest, "type", -1);
		
		String feedbackCorrect = ParamUtil.getString(uploadRequest, "feedbackCorrect", "");
		String feedbackNoCorrect = ParamUtil.getString(uploadRequest, "feedbackNoCorrect", "");
		
		Date startDate = null;
		boolean start =  ParamUtil.getBoolean(uploadRequest, "startdate-enabled", false);
		
		if(start){
			int startMonth = ParamUtil.getInteger(uploadRequest, "startMon");
			int startYear = ParamUtil.getInteger(uploadRequest, "startYear");
			int startDay = ParamUtil.getInteger(uploadRequest, "startDay");
			int startHour = ParamUtil.getInteger(uploadRequest, "startHour");
			int startMinute = ParamUtil.getInteger(uploadRequest, "startMin");
			int startAMPM = ParamUtil.getInteger(uploadRequest, "startAMPM");
			
			if (startAMPM > 0) {
				startHour += 12;
			}
			
			startDate = PortalUtil.getDate(startMonth, startDay, startYear, startHour, startMinute, user.getTimeZone(), new EntryDisplayDateException());
		}

		Date stopDate = null;
		boolean stop =  ParamUtil.getBoolean(uploadRequest, "stopdate-enabled", false);
		
		if(stop){
			int stopMonth = ParamUtil.getInteger(uploadRequest, "stopMon");
			int stopYear = ParamUtil.getInteger(uploadRequest, "stopYear");
			int stopDay = ParamUtil.getInteger(uploadRequest, "stopDay");
			int stopHour = ParamUtil.getInteger(uploadRequest, "stopHour");
			int stopMinute = ParamUtil.getInteger(uploadRequest, "stopMin");
			int stopAMPM = ParamUtil.getInteger(uploadRequest, "stopAMPM");
			
			if (stopAMPM > 0) {
				stopHour += 12;
			}
			
			stopDate = PortalUtil.getDate(stopMonth, stopDay, stopYear, stopHour, stopMinute, user.getTimeZone(), new EntryDisplayDateException());
		}
				
		long tries = ParamUtil.getLong(uploadRequest, "tries", 0);
		int passpuntuation = ParamUtil.getInteger(uploadRequest, "passpuntuation", 0);
		java.util.Date ahora = new java.util.Date(System.currentTimeMillis());
		
		//validating
		Enumeration<String> parNams= uploadRequest.getParameterNames();
		boolean hasTitle=false;
		String title="";
		while(parNams.hasMoreElements())
		{
		  String paramName=parNams.nextElement();
		  if(paramName.startsWith("title_")&&paramName.length()>6)
		  {
			  if(uploadRequest.getParameter(paramName)!=null && uploadRequest.getParameter(paramName).length()>0){
				  title = uploadRequest.getParameter(paramName);
				  StringTokenizer tokens = new StringTokenizer(title);
				  if(tokens.countTokens() > 0 ){
					  hasTitle=true;
				  }
			  }
		  }
		}
		if(!hasTitle)
		{
			SessionErrors.add(actionRequest, "activity-title-required");
			return;
		}
		/* La descripcion es opcional */
		/*
		if (Validator.isNull(HtmlUtil.extractText(description))) {
			SessionErrors.add(actionRequest, "description-required");
		}
		*/
		if(Validator.equals(moduleId, 0))
		{
			SessionErrors.add(actionRequest, "module-required");
		}
		
		//Date validation
		if (start && stop && startDate.after(stopDate)){
			SessionErrors.add(actionRequest, "activity-startDate-before-endDate");
		}
		
		LearningActivity larn=null;
		LearningActivityType learningActivityType=null;
		boolean isnew=false;
		if(actId==0){
			isnew=true;
			//Type validation
			if (type==-1){
				SessionErrors.add(actionRequest, "activity-type-not-selected");
				return;
			}
						
			learningActivityType=new LearningActivityTypeRegistry().getLearningActivityType(type);
		}
		else
		{		
			learningActivityType=new LearningActivityTypeRegistry().
					getLearningActivityType(LearningActivityLocalServiceUtil.getLearningActivity(actId).getTypeId());
		}
			
		//Type especific validations
		if(!learningActivityType.especificValidations(uploadRequest, actionResponse)){
			actionResponse.setRenderParameters(uploadRequest.getParameterMap());
			return;
		}
		
		
		if (actId == 0) 
		{

			if(permissionChecker.hasPermission(
					themeDisplay.getScopeGroupId(),
					Module.class.getName(), moduleId,
					"ADD_LACT"))
			{
			larn =LearningActivityLocalServiceUtil.addLearningActivity(
				"", "", ahora, startDate, stopDate, type, tries, passpuntuation, moduleId, "", feedbackCorrect, feedbackNoCorrect, serviceContext);
			}
			long teamId =ParamUtil.get(uploadRequest, "team", 0);
			//Leemos del portal.properties el estado del permiso VIEW por defecto para siteMember en las actividades nuevas (si no existe, por defecto ser�n visibles)
			boolean hideStr = Boolean.parseBoolean(PrefsPropsUtil.getString("learningactivity.default.hidenewactivity", "false"));
			Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
			if(teamId==0){
				if((moduleId!=0)&&(hideStr))
				{
						ResourcePermissionLocalServiceUtil.removeResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(larn.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW);	
				}
				else ResourcePermissionLocalServiceUtil.setResourcePermissions(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
							ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(larn.getActId()),siteMemberRole.getRoleId(), new String[] {ActionKeys.VIEW});
			}
			else{
				Team t = TeamLocalServiceUtil.getTeam(teamId);
				Role teamMemberRole = RoleLocalServiceUtil.getTeamRole(t.getCompanyId(), t.getTeamId());
				if((moduleId!=0)&&(hideStr))
				{
					ResourcePermissionLocalServiceUtil.removeResourcePermission(t.getCompanyId(), LearningActivity.class.getName(), 
							ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),teamMemberRole.getRoleId(), ActionKeys.VIEW);	
				}else {
					String[] actIds = {ActionKeys.VIEW};
					ResourcePermissionLocalServiceUtil.setResourcePermissions(t.getCompanyId(), LearningActivity.class.getName(), 
							ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),teamMemberRole.getRoleId(), actIds);
				}
			}
				
		}
		else 
		{
			LearningActivity tmp =	LearningActivityLocalServiceUtil.getLearningActivity(actId);
			if(permissionChecker.hasPermission(
					themeDisplay.getScopeGroupId(),
					LearningActivity.class.getName(), actId,
					ActionKeys.UPDATE)||permissionChecker.hasOwnerPermission(
							themeDisplay.getCompanyId(),
							LearningActivity.class.getName(), actId,tmp.getUserId(),
							ActionKeys.UPDATE))
			{
				
			String extraContentTmp = "";	
			
			
			extraContentTmp = tmp.getExtracontent();
			String teamIdStr = StringUtils.substringAfter(extraContentTmp, "<team>");
			teamIdStr = StringUtils.substringBefore(teamIdStr, "</team>");
			if(StringPool.BLANK.equals(teamIdStr)){
				teamIdStr="0";
			}
			long teamId = Long.parseLong(teamIdStr);
			Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
			if(teamId==0){
				ResourcePermissionLocalServiceUtil.removeResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
				ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(tmp.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW);	
			}
			else{
				Team t = TeamLocalServiceUtil.getTeam(teamId);
				Role teamMemberRole = RoleLocalServiceUtil.getTeamRole(t.getCompanyId(), t.getTeamId());
				ResourcePermissionLocalServiceUtil.removeResourcePermission(t.getCompanyId(), LearningActivity.class.getName(), 
				ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(tmp.getActId()),teamMemberRole.getRoleId(), ActionKeys.VIEW);	
			}
				
			larn=LearningActivityLocalServiceUtil.modLearningActivity(
				actId, "", "", ahora, startDate, stopDate, type, tries, passpuntuation, moduleId,  extraContentTmp, feedbackCorrect, feedbackNoCorrect, serviceContext);
			}
			
		}
		
		larn.setDescription( description,themeDisplay.getLocale());
		larn.setWeightinmodule(weightinmodule);
		larn.setPrecedence(precedence);
		Enumeration<String> parNames= uploadRequest.getParameterNames();
		
		while(parNames.hasMoreElements())
		{
		  String paramName=parNames.nextElement();
		  if(paramName.startsWith("title_")&&paramName.length()>6)
		  { 
			  String language=paramName.substring(6);
			  Locale locale=LocaleUtil.fromLanguageId(language);
			  larn.setTitle( uploadRequest.getParameter(paramName),locale);
		  }
		}
		
		//descomentar si se permiten llamadas por webservice, ademas a�adir booleano editionBlocked en los metodos setExtraContent de las actividades
		//para poder diferenciar las partes bloqueadas de las que no lo estan a la hora de crear el extraContent.
		/*boolean setExtraContent = false;
		if(actId == 0) setExtraContent = true;
		else setExtraContent = LearningActivityLocalServiceUtil.canBeEdited(larn, user.getUserId());
		if(setExtraContent)*/
		learningActivityType.setExtraContent(uploadRequest,actionResponse,larn);
		//Seteamos permiso de view a quien corresponda.
		long teamId =ParamUtil.get(uploadRequest, "team", 0);
		//Leemos del portal.properties el estado del permiso VIEW por defecto para siteMember en las actividades nuevas (si no existe, por defecto ser�n visibles)
		boolean hideStr = Boolean.parseBoolean(PrefsPropsUtil.getString("learningactivity.default.hidenewactivity", "false"));
		Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
		if(teamId==0){
			if(hideStr)
			{
					ResourcePermissionLocalServiceUtil.removeResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
					ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(larn.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW);	
			}
			else ResourcePermissionLocalServiceUtil.setResourcePermissions(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(larn.getActId()),siteMemberRole.getRoleId(), new String[] {ActionKeys.VIEW});
		}
		else{
			Team t = TeamLocalServiceUtil.getTeam(teamId);
			Role teamMemberRole = RoleLocalServiceUtil.getTeamRole(t.getCompanyId(), t.getTeamId());
			if(hideStr)
			{
				ResourcePermissionLocalServiceUtil.removeResourcePermission(t.getCompanyId(), LearningActivity.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),teamMemberRole.getRoleId(), ActionKeys.VIEW);	
			}else {
				String[] actIds = {ActionKeys.VIEW};
				ResourcePermissionLocalServiceUtil.setResourcePermissions(t.getCompanyId(), LearningActivity.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),teamMemberRole.getRoleId(), actIds);
			}
		}
		if(permissionChecker.hasPermission(
				themeDisplay.getScopeGroupId(),
				LearningActivity.class.getName(), larn.getActId(),
				ActionKeys.UPDATE)||permissionChecker.hasOwnerPermission(
						themeDisplay.getCompanyId(),
						LearningActivity.class.getName(), larn.getActId(),larn.getUserId(),
						ActionKeys.UPDATE)||isnew)
		{
			LearningActivityLocalServiceUtil.updateLearningActivity(larn);
			learningActivityType.afterInsertOrUpdate(uploadRequest,actionResponse,larn);
		}
		
		if(actionRequest.getPortletSession().getAttribute("error")!=null){
			actionRequest.getPortletSession().removeAttribute("error");
		}else{
			SessionMessages.add(actionRequest, "activity-saved-successfully");
		}
		
		WindowState windowState = actionRequest.getWindowState();
		if (Validator.isNotNull(redirect)) {
			if (!windowState.equals(LiferayWindowState.POP_UP)) {
				actionResponse.sendRedirect(redirect);
			}
			else {
				redirect = PortalUtil.escapeRedirect(redirect);

				if (Validator.isNotNull(redirect)) {
					actionResponse.sendRedirect(redirect);
				}
			}
		}
		uploadRequest.setAttribute("activity", larn);

	}
	
	public void goToModule(ActionRequest actionRequest, ActionResponse actionResponse)throws Exception {
		ThemeIdEvent themeIdEvent = new ThemeIdEvent();
		themeIdEvent.setModuleId(ParamUtil.getLong(actionRequest, "moduleId",0));
		themeIdEvent.setThemeId(ParamUtil.getLong(actionRequest, "themeId",1));		
		actionResponse.setEvent(new QName("http://www.wemooc.com/" , "themeId"), themeIdEvent);
	}
	
	public void deletemodule(ActionRequest actionRequest, ActionResponse actionResponse)throws Exception 
	{

		long moduleId = ParamUtil.getLong(actionRequest, "resId",0);
		long renderModule = ParamUtil.getLong(actionRequest, "moduleId",0);
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
	
		if(moduleId>0)
		{
			if(permissionChecker.hasPermission(
					themeDisplay.getScopeGroupId(),
					Module.class.getName(), moduleId,
					ActionKeys.DELETE))
			{
				if(LearningActivityLocalServiceUtil.dynamicQueryCount(DynamicQueryFactoryUtil.forClass(LearningActivity.class).
				add(PropertyFactoryUtil.forName("moduleId").eq(moduleId)))==0)
				{
					ModuleLocalServiceUtil.deleteModule(moduleId);
					if(moduleId==renderModule)
					{
						actionResponse.removePublicRenderParameter("moduleId");
						actionResponse.removePublicRenderParameter("actId");	
	
					}
				}
				else
				{
				    SessionErrors.add(actionRequest, "activities-in-module");
			
				}			
			}
		}
	}
	
	public void upmodule(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {

		long moduleId = ParamUtil.getLong(actionRequest, "resId",0);
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
	
		if(moduleId>0)
		{
			if(permissionChecker.hasPermission(
		
				themeDisplay.getScopeGroupId(),
				Module.class.getName(), moduleId,
				ActionKeys.UPDATE))
			{
				ModuleLocalServiceUtil.goUpModule(moduleId);
			}
		}
		
	}
	
	public void downmodule(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
	
		long moduleId = ParamUtil.getLong(actionRequest, "resId",0);
		
		if(moduleId>0)
		{
			if(permissionChecker.hasPermission(
				themeDisplay.getScopeGroupId(),
				Module.class.getName(), moduleId,
				ActionKeys.UPDATE))
			{
			ModuleLocalServiceUtil.goDownModule(moduleId);
			}
		}
		
	}
	
	public void moveModule(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
		
		long moduleId = ParamUtil.getLong(actionRequest, "pageId"),
		     prevModId = ParamUtil.getLong(actionRequest, "prevPageId"),
		     nextModId = ParamUtil.getLong(actionRequest, "nextPageId");
		
		if(moduleId>0){
			if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Module.class.getName(), moduleId, ActionKeys.UPDATE)){
				ModuleLocalServiceUtil.moveModule(moduleId, prevModId, nextModId);
			}
		}
		
		actionResponse.setRenderParameter("jsp", "/html/lmsactivitieslist/view.jsp");
	}
	
	public void deleteactivity(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long actId = ParamUtil.getLong(actionRequest, "resId");
		long renderActId = ParamUtil.getLong(actionRequest, "actId",0); 
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
		
		if(actId>0)
		{
			LearningActivity larn = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			if(permissionChecker.hasPermission(larn.getGroupId(), LearningActivity.class.getName(), larn.getActId(),
					ActionKeys.DELETE)|| permissionChecker.hasOwnerPermission(larn.getCompanyId(), LearningActivity.class.getName(), larn.getActId(),larn.getUserId(),
							ActionKeys.DELETE))
			{
				LearningActivityType learningActivityType=new LearningActivityTypeRegistry().
						getLearningActivityType(larn.getTypeId());
				learningActivityType.deleteResources(actionRequest, actionResponse, larn);
				
				LearningActivityServiceUtil.deleteLearningactivity(actId);
	
				//auditing
				AuditingLogFactory.audit(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), LearningActivity.class.getName(), actId, themeDisplay.getUserId(), AuditConstants.DELETE, null);
				
				if(actId==renderActId) {
					actionResponse.removePublicRenderParameter("actId");				
				}
				
				if(!LiferayWindowState.EXCLUSIVE.equals(actionRequest.getWindowState())){
					actionResponse.sendRedirect(
							((LiferayPortletResponse)actionResponse).createRenderURL().toString());
					
				}
			}
		}
	}
	
	public void upactivity(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {
	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
		
		long actId = ParamUtil.getLong(actionRequest, "resId",0);
		
		if(actId>0)
		{	
			LearningActivity larn = LearningActivityLocalServiceUtil.getLearningActivity(actId);
		
			if(permissionChecker.hasPermission(larn.getGroupId(), LearningActivity.class.getName(), larn.getActId(),
					ActionKeys.UPDATE)|| permissionChecker.hasOwnerPermission(larn.getCompanyId(), LearningActivity.class.getName(), larn.getActId(),larn.getUserId(),
							ActionKeys.UPDATE))
			{
			LearningActivityLocalServiceUtil.goUpLearningActivity(actId);
			}
		}
	}
	
	public void downactivity(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {
	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
		
		long actId = ParamUtil.getLong(actionRequest, "resId",0);
	
		if(actId>0)
		{
			LearningActivity larn = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			
			if(permissionChecker.hasPermission(larn.getGroupId(), LearningActivity.class.getName(), larn.getActId(),
					ActionKeys.UPDATE)|| permissionChecker.hasOwnerPermission(larn.getCompanyId(), LearningActivity.class.getName(), larn.getActId(),larn.getUserId(),
							ActionKeys.UPDATE))
			{
				LearningActivityLocalServiceUtil.goDownLearningActivity(actId);
			}
		}
	}
	
	public void moveActivity(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
		
		long actId = ParamUtil.getLong(actionRequest, "pageId"),
		     prevActId = ParamUtil.getLong(actionRequest, "prevPageId"),
		     nextActId = ParamUtil.getLong(actionRequest, "nextPageId");
		
		if(actId>0){
			LearningActivity larn = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			
			if(permissionChecker.hasPermission(larn.getGroupId(), LearningActivity.class.getName(), larn.getActId(), ActionKeys.UPDATE)|| 
					permissionChecker.hasOwnerPermission(larn.getCompanyId(), LearningActivity.class.getName(), larn.getActId(),larn.getUserId(), ActionKeys.UPDATE)){
				LearningActivityLocalServiceUtil.moveActivity(actId, prevActId, nextActId);
			}
		}
		
		actionResponse.setRenderParameter("jsp", "/html/lmsactivitieslist/view.jsp");
	}
	
	public void editactivity(ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException, SystemException, Exception {
		long actId = ParamUtil.getInteger(actionRequest, "resId");
	
		// LearningActivity learnact =
		// com.liferay.lms.service.LearningActivityServiceUtil.getLearningActivity(actId);
		LearningActivityAssetRendererFactory laf = new LearningActivityAssetRendererFactory();
		if (laf != null) {
			AssetRenderer assetRenderer = laf.getAssetRenderer(actId, 0);
			String urlEdit = assetRenderer.getURLEdit((LiferayPortletRequest) actionRequest, (LiferayPortletResponse) actionResponse).toString();			
			Portlet urlEditPortlet =PortletLocalServiceUtil.getPortletById(HttpUtil.getParameter(urlEdit, "p_p_id",false));
			
			if(urlEditPortlet!=null) {
				PublicRenderParameter actIdPublicParameter = urlEditPortlet.getPublicRenderParameter("actId");
				if(actIdPublicParameter!=null) {
					urlEdit=HttpUtil.removeParameter(urlEdit,PortletQNameUtil.getPublicRenderParameterName(actIdPublicParameter.getQName()));
				}
				urlEdit=HttpUtil.addParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resId", actId);
				urlEdit=HttpUtil.removeParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"actionEditingDetails");
				urlEdit=HttpUtil.addParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"actionEditingDetails", true);
			}
		
			actionResponse.sendRedirect(urlEdit);
		}
		SessionMessages.add(actionRequest, "asset-renderer-not-defined");
	}
	
	public void changeVisibility(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long actId=ParamUtil.getLong(actionRequest, "resId", 0);
		LearningActivity larn = LearningActivityLocalServiceUtil.getLearningActivity(actId);		
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
	
		if(permissionChecker.hasPermission(larn.getGroupId(), LearningActivity.class.getName(), larn.getActId(), ActionKeys.PERMISSIONS)){
		String team = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"team");
		long teamId=0;
		if(StringPool.BLANK.equals(team)){
			
			Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
		
			boolean visible = ResourcePermissionLocalServiceUtil.hasResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
					ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),siteMemberRole.getRoleId(), ActionKeys.VIEW);
			
			if(visible) {
				ResourcePermissionLocalServiceUtil.removeResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),siteMemberRole.getRoleId(), ActionKeys.VIEW);	
			}else {
				String[] actIds = {ActionKeys.VIEW};
				ResourcePermissionLocalServiceUtil.setResourcePermissions(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),siteMemberRole.getRoleId(), actIds);
			}
		}
		else{
			teamId = Long.parseLong(team);
			Team t = TeamLocalServiceUtil.getTeam(teamId);
			Role teamMemberRole = RoleLocalServiceUtil.getTeamRole(t.getCompanyId(), t.getTeamId());
			boolean visible = ResourcePermissionLocalServiceUtil.hasResourcePermission(t.getCompanyId(), LearningActivity.class.getName(), 
					ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),teamMemberRole.getRoleId(), ActionKeys.VIEW);
			
			if(visible) {
				ResourcePermissionLocalServiceUtil.removeResourcePermission(t.getCompanyId(), LearningActivity.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),teamMemberRole.getRoleId(), ActionKeys.VIEW);	
			}else {
				String[] actIds = {ActionKeys.VIEW};
				ResourcePermissionLocalServiceUtil.setResourcePermissions(t.getCompanyId(), LearningActivity.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),teamMemberRole.getRoleId(), actIds);
				}
		}
		}
	}
	
	public void changeAllVisibility(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
				ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
				long actId=ParamUtil.getLong(actionRequest, "resId", 0);
				LearningActivity larn = LearningActivityLocalServiceUtil.getLearningActivity(actId);
				List<LearningActivity> lacts =LearningActivityLocalServiceUtil.getLearningActivitiesOfGroup(larn.getGroupId());
				PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
				for(LearningActivity lact : lacts){
					if(lact.getActId() == actId){
					
						if(permissionChecker.hasPermission(lact.getGroupId(), LearningActivity.class.getName(), lact.getActId(), ActionKeys.PERMISSIONS)){
						
							String team = LearningActivityLocalServiceUtil.getExtraContentValue(lact.getActId(),"team");
							long teamId=0;
							if(StringPool.BLANK.equals(team)){
								Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
								boolean visible = ResourcePermissionLocalServiceUtil.hasResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
										ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(lact.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW);
								if(!visible) {	
									String[] actIds = {ActionKeys.VIEW};
									ResourcePermissionLocalServiceUtil.setResourcePermissions(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
											ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(lact.getActId()),siteMemberRole.getRoleId(), actIds);
								}
							}
							else{
								teamId = Long.parseLong(team);
								Team t = TeamLocalServiceUtil.getTeam(teamId);
								Role teamMemberRole = RoleLocalServiceUtil.getTeamRole(t.getCompanyId(), t.getTeamId());
								boolean visible = ResourcePermissionLocalServiceUtil.hasResourcePermission(t.getCompanyId(), LearningActivity.class.getName(), 
										ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(lact.getActId()),teamMemberRole.getRoleId(), ActionKeys.VIEW);
								
								if(!visible) {
									String[] actIds = {ActionKeys.VIEW};
									ResourcePermissionLocalServiceUtil.setResourcePermissions(t.getCompanyId(), LearningActivity.class.getName(), 
											ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(lact.getActId()),teamMemberRole.getRoleId(), actIds);
									}
							}
						}
					}else{
						if(permissionChecker.hasPermission(lact.getGroupId(), LearningActivity.class.getName(), lact.getActId(), ActionKeys.PERMISSIONS)){
							
							String team = LearningActivityLocalServiceUtil.getExtraContentValue(lact.getActId(),"team");
							long teamId=0;
							if(StringPool.BLANK.equals(team)){
								Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
								boolean visible = ResourcePermissionLocalServiceUtil.hasResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
										ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(lact.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW);
								if(visible){
									ResourcePermissionLocalServiceUtil.removeResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
										ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(lact.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW);
								}
							}
							else{
								teamId = Long.parseLong(team);
								Team t = TeamLocalServiceUtil.getTeam(teamId);
								Role teamMemberRole = RoleLocalServiceUtil.getTeamRole(t.getCompanyId(), t.getTeamId());
								boolean visible = ResourcePermissionLocalServiceUtil.hasResourcePermission(t.getCompanyId(), LearningActivity.class.getName(), 
										ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(lact.getActId()),teamMemberRole.getRoleId(), ActionKeys.VIEW);							
								if(visible){
									ResourcePermissionLocalServiceUtil.removeResourcePermission(teamMemberRole.getCompanyId(), LearningActivity.class.getName(), 
										ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(lact.getActId()),teamMemberRole.getRoleId(), ActionKeys.VIEW);	
								}
							}
						}
					}
				}
			}


	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

        /*
			ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
					WebKeys.THEME_DISPLAY);
	
			if((themeDisplay!=null)&&(themeDisplay.isWidget())) {
				include("/html/lmsactivitieslist/widget/view.jsp",renderRequest,renderResponse);
			}
			else
		*/
		
		if(ParamUtil.getBoolean(renderRequest,"editing"))
		{
			
			Integer maxfile = ResourceExternalLearningActivityType.DEFAULT_FILENUMBER;
			try{
				maxfile = Integer.valueOf(PropsUtil.get("lms.learningactivity.maxfile"));
			}catch(NumberFormatException nfe){
			}
			
			for(int i=0;i<=maxfile;i++){
				String paramExt = "extensionfile";
				String paramSize = "sizefile";
				if(i > 0){
					paramExt = paramExt + (i-1);
					paramSize = paramSize + (i-1);
				}
				
				if(renderRequest.getPortletSession().getAttribute(paramExt)!=null){
					renderRequest.setAttribute(paramExt,renderRequest.getPortletSession().getAttribute(paramExt));
					renderRequest.getPortletSession().removeAttribute(paramExt);
				}
				if(renderRequest.getPortletSession().getAttribute(paramSize)!=null){ 
					renderRequest.setAttribute(paramSize,renderRequest.getPortletSession().getAttribute(paramSize));
					renderRequest.getPortletSession().removeAttribute(paramSize);
				}
			}
			
			if(renderRequest.getPortletSession().getAttribute("preferencesOpen")!=null){
				renderRequest.setAttribute("preferencesOpen",renderRequest.getPortletSession().getAttribute("preferencesOpen"));
				renderRequest.getPortletSession().removeAttribute("preferencesOpen");
			}
			
			renderRequest.setAttribute("showcategorization", ("false".equals(PropsUtil.get("activity.show.categorization")))?false:true);
			include("/html/editactivity/editactivity.jsp",renderRequest,renderResponse);
		}
		else if(ParamUtil.getBoolean(renderRequest,"califications"))
		{
			include("/html/lmsactivitieslist/califications.jsp",renderRequest,renderResponse);
		}
		else
		{
			super.doView(renderRequest, renderResponse);
		}
	}

	public void viewactivity(ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException, SystemException, Exception {

		long actId = ParamUtil.getInteger(actionRequest, "actId");
		AssetRendererFactory laf = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
		
		//auditing
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		AuditingLogFactory.audit(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), LearningActivity.class.getName(), 
				actId, themeDisplay.getUserId(), AuditConstants.GET, null);
		
		if (laf != null) {
			AssetRenderer assetRenderer = laf.getAssetRenderer(actId, 0);
			String urlEdit = assetRenderer.getURLViewInContext((LiferayPortletRequest) actionRequest, (LiferayPortletResponse) actionResponse, "").toString();
			actionResponse.sendRedirect(urlEdit);
		}
		SessionMessages.add(actionRequest, "asset-renderer-not-defined");

	}
	
	public void deleteAllURL(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		long actId = ParamUtil.getInteger(actionRequest, "resId");
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);    
		
		if(log.isDebugEnabled())log.debug(actId); 

		LearningActivity la = LearningActivityLocalServiceUtil.getLearningActivity(actId);
		
		if(la!=null){
			Message message=new Message();
			message.put("learningActivity",la);
			message.put("userc",themeDisplay.getUser());
			MessageBusUtil.sendMessage("liferay/lms/cleanTriesNotPassed", message);
		}

		actionResponse.setRenderParameter("resId", String.valueOf(actId));
		actionResponse.setRenderParameter("califications", String.valueOf(true));
	}
	
	public void deleteURL(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		long actId = ParamUtil.getInteger(actionRequest, "resId");
		long userId = ParamUtil.getInteger(actionRequest, "userId");
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);    
		
		if(log.isDebugEnabled()){
			log.debug(actId);
			log.debug(userId);
		}

		LearningActivity la = LearningActivityLocalServiceUtil.getLearningActivity(actId);
		User user = UserLocalServiceUtil.getUser(userId);
		
		if(la!=null&&user!=null){
			Message message=new Message();
			message.put("learningActivity",la);
			message.put("user",user);
			message.put("userc",themeDisplay.getUser());
			MessageBusUtil.sendMessage("liferay/lms/cleanTriesUser", message);
		}
		
		actionResponse.setRenderParameter("resId", String.valueOf(actId));
		actionResponse.setRenderParameter("userId", String.valueOf(userId));
		actionResponse.setRenderParameter("califications", String.valueOf(true));
	}

	public void modactivity(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		String portletId = PortalUtil.getPortletId(actionRequest);

		String title = actionRequest.getParameter("title");
		String description = actionRequest.getParameter("description");
		int typeId = ParamUtil.getInteger(actionRequest, "type", 0);
		java.util.Date ahora = new java.util.Date(System.currentTimeMillis());
		LearningActivity learningActivity = LearningActivityServiceUtil.getLearningActivity(ParamUtil.getLong(actionRequest, "actId"));
		learningActivity.setTitle(title);
		learningActivity.setDescription(description);
		learningActivity.setTypeId(typeId);
		LearningActivityLocalServiceUtil.updateLearningActivity(learningActivity, false);
		editactivity(actionRequest, actionResponse);
		SessionMessages.add(actionRequest, "activity-modified-successfully");
	}
	
}
