package com.liferay.lms.portlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.views.ModuleView;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class DateEditor
 */
public class DateEditor extends MVCPortlet {
private static Log log = LogFactoryUtil.getLog(DateEditor.class);
	
	protected String viewJSP;

	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");

	}
	
	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);

		if (portletRequestDispatcher != null) {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}

	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getScopeGroupId();
		List<ModuleView> listModules = new ArrayList<ModuleView>();
		
		log.debug("DO VIEW "+themeDisplay.getScopeGroupId());
		
		try {
			List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(groupId);
			if (modules!=null && modules.size()>0) {
				if (log.isDebugEnabled())log.debug("Modules Size::" + modules.size());
				for (Module module : modules) {
					try {						
						List<LearningActivity> activities = LearningActivityLocalServiceUtil
								.getLearningActivitiesOfModule(module.getModuleId());
						
						if (activities != null) {
							listModules.add(new ModuleView(
									module, activities,themeDisplay.getLocale(),themeDisplay.getTimeZone()));
						}
						
						if (log.isDebugEnabled())log.debug("Size::" + activities.size());
						
					} catch (SystemException e) {
						e.printStackTrace();
					}
				}
			}
			
			log.debug("List modules size "+listModules.size());
		} catch (SystemException e) {
			e.printStackTrace();
		}
		renderRequest.setAttribute("listModules", listModules);
		include(viewJSP, renderRequest, renderResponse);
	}

	
	
	
	@ProcessAction(name = "submitChangeDate")
	public void submitChangeDate(ActionRequest request, ActionResponse response) {
		if (log.isDebugEnabled())log.debug("Action::submitChangeDate");
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getScopeGroupId();
		boolean isCorrect = true;
		try{		
			List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(groupId);
			List<LearningActivity> activities;
			if (modules!=null && modules.size()>0) {
				for (Module module : modules) {
					if(checkModuleDates(module.getModuleId(), request)){
						module=updateModuleDates(module, request, true);
						module=updateModuleDates(module, request, false);
						ModuleLocalServiceUtil.updateModule(module);
						
					}else{
						isCorrect=false;
					}
					activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(module.getModuleId());
					
					for(LearningActivity activity : activities){
						if(checkActivityDates(activity.getActId(), request)){
							activity = updateActivityDates(activity, request, 0);
							activity = updateActivityDates(activity, request, 1);
							LearningActivityLocalServiceUtil.updateLearningActivity(activity);
						}else{
							isCorrect = false;
						}						
						if(activity.getTypeId() == 3){
							if(checkUpdateActivityDates(activity.getActId(), request)){
								updateActivityDates(activity, request, 2);
							}else{
								isCorrect = false;
							}
							
						}		
					}
					
				}
			}
		
			if(isCorrect){
				SessionMessages.add(request, "dates-updated-correctly");
			}else{
				SessionErrors.add(request, "error-updating-dates");
			}
		}catch(SystemException e){
			e.printStackTrace();
		}
	}

	
	private Module updateModuleDates(Module module, ActionRequest request, boolean startDate){
		String paramValue;
		if(startDate){
			paramValue="modInit";
		}else{
			paramValue="modEnd";
		}		
		Calendar date = getDateFromRequest(request, paramValue, module.getModuleId());
					
		if(startDate){
			module.setStartDate(date.getTime());
		}else{
			module.setEndDate(date.getTime());
		}
		
		return module;
	}
	
	
	
	private LearningActivity updateActivityDates(LearningActivity activity, ActionRequest request, int dateType) throws SystemException{
		String paramValue;
		if(dateType == 0){
			paramValue="actInit";
		}else if(dateType == 1){
			paramValue="actEnd";
		}else if(dateType == 2){
			paramValue = "actUpdate";
		}else{
			return activity;
		}		
		
		Calendar date = getDateFromRequest(request, paramValue, activity.getActId());
		
		if(dateType == 0){
			activity.setStartdate(date.getTime());
		}else if(dateType == 1){
			activity.setEnddate(date.getTime());
		}else if(dateType == 2){
			SimpleDateFormat sdfP2p = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			LearningActivityLocalServiceUtil.setExtraContentValue(activity.getActId(), "dateupload",sdfP2p.format(date.getTime()));
		}
		
		return activity;
	}
	
	
	private boolean checkModuleDates(long moduleId, ActionRequest request){
			
		Calendar startDate = getDateFromRequest(request, "modInit", moduleId);
		Calendar endDate = getDateFromRequest(request, "modEnd", moduleId);
		if(endDate.after(startDate)){
			return true;
		}else{
			return false;
		}		
	}
	
	private boolean checkActivityDates(long moduleId, ActionRequest request){
		
		Calendar startDate = getDateFromRequest(request, "actInit", moduleId);
		Calendar endDate = getDateFromRequest(request, "actEnd", moduleId);
		if(endDate.after(startDate)){
			return true;
		}else{
			return false;
		}	
	}
	
	private boolean checkUpdateActivityDates(long moduleId, ActionRequest request){
		
		Calendar startDate = getDateFromRequest(request, "actInit", moduleId);
		Calendar updateDate = getDateFromRequest(request, "actUpdate", moduleId);
		Calendar endDate = getDateFromRequest(request, "actUpdate", moduleId);
		if(updateDate.after(startDate)){
			if(endDate.after(updateDate))
			return true;
		}
		return false;
	}
	
	
	private Calendar getDateFromRequest(ActionRequest request, String paramValue, long id){
		int year,month,day,hour,minute;
		
		year=ParamUtil.getInteger(request, paramValue+"Year_"+id);
		month=ParamUtil.getInteger(request, paramValue+"Month_"+id);
		day=ParamUtil.getInteger(request, paramValue+"Day_"+id);
		hour=ParamUtil.getInteger(request, paramValue+"Hour_"+id);
		minute=ParamUtil.getInteger(request, paramValue+"Minute_"+id);
		
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, month);
		date.set(Calendar.DAY_OF_MONTH, day);
		date.set(Calendar.HOUR, hour);
		date.set(Calendar.MINUTE, minute);
		
		return date;
	}

}
