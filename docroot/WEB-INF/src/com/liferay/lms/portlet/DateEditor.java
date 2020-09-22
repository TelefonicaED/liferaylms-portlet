package com.liferay.lms.portlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.learningactivity.TaskP2PLearningActivityType;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class DateEditor
 */
public class DateEditor extends MVCPortlet {
	private static Log log = LogFactoryUtil.getLog(DateEditor.class);
	
	private SimpleDateFormat sdfP2p =  new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private static final String P2P_DATEUPLOAD = "dateupload";
	
	private String viewJSP = null;
	
	@Override
	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
		
	}
	
	@Override
	public void doView(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException {
		log.debug("doView");
	
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		try {			
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());	
			
			if(course != null){
				List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId());
				
				renderRequest.setAttribute("course", course);
				renderRequest.setAttribute("listModules", modules);
			}
			
						
		} catch (SystemException e) {
			log.error(e);
		}		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",themeDisplay.getLocale());
		dateFormat.setTimeZone(themeDisplay.getTimeZone());
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", themeDisplay.getLocale());
		timeFormat.setTimeZone(themeDisplay.getTimeZone());
		
		renderRequest.setAttribute("dateFormat", dateFormat);
		renderRequest.setAttribute("timeFormat", timeFormat);
		
		SimpleDateFormat p2pFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",themeDisplay.getLocale());
		renderRequest.setAttribute("p2pFormat", p2pFormat);
		
		renderRequest.setAttribute("typeP2P", TaskP2PLearningActivityType.TYPE_ID);
	
		include(viewJSP, renderRequest, renderResponse);
	}
	
		
	
	@ProcessAction(name = "submit")
	public void submit(ActionRequest request, ActionResponse response) throws SystemException {
		log.debug("Action::submit");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
		
		Calendar calendar = Calendar.getInstance(themeDisplay.getTimeZone(), themeDisplay.getLocale());
		
		//FECHA DE INICIO DE EJECUCIÓN DE CURSO
		Date date = ParamUtil.getDate(request, "fcin" + course.getCourseId(), sdf);
		String time = ParamUtil.getString(request, "tcin" + course.getCourseId());
		calendar.setTime(date);
		String[] hours = time.split(":");
		if(hours.length==2){
			calendar.set(Calendar.HOUR_OF_DAY,Integer.valueOf(hours[0]));
			calendar.set(Calendar.MINUTE,Integer.valueOf(hours[1]));
		}
		course.setExecutionStartDate(calendar.getTime());
		
		//FECHA DE FIN DE EJECUCIÓN DE CURSO
		date = ParamUtil.getDate(request, "fcou" + course.getCourseId(), sdf);
		time = ParamUtil.getString(request, "tcou" + course.getCourseId());
		calendar.setTime(date);
		hours = time.split(":");
		if(hours.length==2){
			calendar.set(Calendar.HOUR_OF_DAY,Integer.valueOf(hours[0]));
			calendar.set(Calendar.MINUTE,Integer.valueOf(hours[1]));
		}
		course.setExecutionEndDate(calendar.getTime());
		
		CourseLocalServiceUtil.updateCourse(course);
		
		List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
		
		for(Module module: modules){
			
			//Primero miramos si está el check
			if(ParamUtil.getBoolean(request, "startDateEnabled" + module.getModuleId() + "_")){
				date = ParamUtil.getDate(request, "fmin" + module.getModuleId(), sdf);
				time = ParamUtil.getString(request, "tmin" + module.getModuleId());
				calendar.setTime(date);
				hours = time.split(":");
				if(hours.length==2){
					calendar.set(Calendar.HOUR_OF_DAY,Integer.valueOf(hours[0]));
					calendar.set(Calendar.MINUTE,Integer.valueOf(hours[1]));
				}
				
				if(calendar.getTime().compareTo(course.getExecutionStartDate()) == 0){
					module.setStartDate(null);
				}else{
					module.setStartDate(calendar.getTime());
				}
			}else{
				module.setStartDate(null);
			}
			
			//Primero miramos si está el check
			if(ParamUtil.getBoolean(request, "endDateEnabled" + module.getModuleId() + "_")){
				date = ParamUtil.getDate(request, "fmou" + module.getModuleId(), sdf);
				time = ParamUtil.getString(request, "tmou" + module.getModuleId());
				calendar.setTime(date);
				hours = time.split(":");
				if(hours.length==2){
					calendar.set(Calendar.HOUR_OF_DAY,Integer.valueOf(hours[0]));
					calendar.set(Calendar.MINUTE,Integer.valueOf(hours[1]));
				}
				
				if(calendar.getTime().compareTo(course.getExecutionEndDate()) == 0){
					module.setEndDate(null);
				}else{
					module.setEndDate(calendar.getTime());
				}
			}else{
				module.setEndDate(null);
			}
			
			ModuleLocalServiceUtil.updateModule(module);
		}
		
		List<LearningActivity> activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfGroup(themeDisplay.getScopeGroupId());
		
		for(LearningActivity activity: activities){
			//Primero miramos si está el check
			if(ParamUtil.getBoolean(request, "startDateActivityEnabled_" + activity.getModuleId() + "_" + activity.getActId() + "_")){
				date = ParamUtil.getDate(request, "fain" + activity.getActId(), sdf);
				time = ParamUtil.getString(request, "tain" + activity.getActId());
				calendar.setTime(date);
				hours = time.split(":");
				if(hours.length==2){
					calendar.set(Calendar.HOUR_OF_DAY,Integer.valueOf(hours[0]));
					calendar.set(Calendar.MINUTE,Integer.valueOf(hours[1]));
				}
				
				if(calendar.getTime().compareTo(course.getExecutionStartDate()) == 0){
					activity.setStartdate(null);
				}else{
					activity.setStartdate(calendar.getTime());
				}
			}else{
				activity.setStartdate(null);
			}
			
			//Primero miramos si está el check
			if(ParamUtil.getBoolean(request, "endDateActivityEnabled_" + activity.getModuleId() + "_" + activity.getActId() + "_")){
				date = ParamUtil.getDate(request, "faou" + activity.getActId(), sdf);
				time = ParamUtil.getString(request, "taou" + activity.getActId());
				calendar.setTime(date);
				hours = time.split(":");
				if(hours.length==2){
					calendar.set(Calendar.HOUR_OF_DAY,Integer.valueOf(hours[0]));
					calendar.set(Calendar.MINUTE,Integer.valueOf(hours[1]));
				}
				
				if(calendar.getTime().compareTo(course.getExecutionEndDate()) == 0){
					activity.setEnddate(null);
				}else{
					activity.setEnddate(calendar.getTime());
				}
			}else{
				activity.setEnddate(null);
			}
			
			LearningActivityLocalServiceUtil.updateLearningActivity(activity);
			
			if(activity.getTypeId() == TaskP2PLearningActivityType.TYPE_ID){
				
				date = ParamUtil.getDate(request, "fact" + activity.getActId(), sdf);
				time = ParamUtil.getString(request, "tact" + activity.getActId());
				calendar.setTime(date);
				hours = time.split(":");
				if(hours.length==2){
					calendar.set(Calendar.HOUR_OF_DAY,Integer.valueOf(hours[0]));
					calendar.set(Calendar.MINUTE,Integer.valueOf(hours[1]));
				}

				LearningActivityLocalServiceUtil.setExtraContentValue(activity.getActId(), P2P_DATEUPLOAD, sdfP2p.format(calendar.getTime()));	
			}
		}
	}
	
	@Override
	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
	
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(path);
	
		if (portletRequestDispatcher == null) {
			log.error(path + " is not a valid include");
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}
}
