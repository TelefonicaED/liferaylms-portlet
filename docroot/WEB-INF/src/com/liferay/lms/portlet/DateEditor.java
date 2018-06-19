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
	public void submit(ActionRequest request, ActionResponse response) {
		log.debug("Action::submit");		
		
		Enumeration<String> parNames = request.getParameterNames();
		while (parNames.hasMoreElements()) {			
			String name = parNames.nextElement();
			log.debug("ForParam::"+name);
			
			if(name.matches("(\\D{4})\\d+")){
				log.debug("ForParam::ok!");
				
				long id =  0;
				try{
					id = Long.parseLong(name.substring(4,name.length()));
					
					String key = name.substring(0,4);
					
					switch (key) {
					case "fmin":
						log.debug("ForParam::fecha min");	
						setModuleDate(name, id, request, true);	
						break;
					case "fmou":
						log.debug("ForParam::fecha mou");
						setModuleDate(name, id, request, false);
						break;
					case "tmin":
						log.debug("ForParam::tiempo min");
						setModuleTime(name, id, request, true);
						break;
					case "tmou":
						log.debug("ForParam::tiempo mou");
						setModuleTime(name, id, request, false);
						break;
					case "fact":
						log.debug("ForParam::fecha act");
						setP2PUploadDate(name, id, request);			
						break;
					case "tact":
						log.debug("ForParam::tiempo act");
						setP2PUploadTime(name, id, request);			
						break;
					case "fain":
						log.debug("ForParam::fain");
						setActivityDate(name, id, request, true);
						break;
					case "faou":
						log.debug("ForParam::faou");
						setActivityDate(name, id, request, false);
						break;
					case "tain":
						log.debug("ForParam::tain");
						setActivityTime(name, id, request, true);
						break;
					case "taou":
						log.debug("ForParam::taou");
						setActivityTime(name, id, request, false);
						break;
					case "fcin":
						log.debug("ForParam::fecha cin");	
						setCourseDate(name, request, true);	
						break;
					case "fcou":
						log.debug("ForParam::fecha cou");
						setCourseDate(name, request, false);
						break;
					case "tcin":
						log.debug("ForParam::tiempo cin");
						setCourseTime(name, request, true);
						break;
					case "tcou":
						log.debug("ForParam::tiempo cou");
						setCourseTime(name, request, false);
						break;
					default:
						break;
					}
					
				}catch(NumberFormatException nfe){
					return;
				}
				
			}
		}
	}
	
	private void setCourseDate(String name, ActionRequest request, boolean isStartDate){
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		try {
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());	
			Date date = (isStartDate) ? course.getExecutionStartDate() : course.getExecutionEndDate();			
			String sDate = request.getParameter(name);		
			Date bDate = sdf.parse(sDate);
			
			Calendar gc = Calendar.getInstance(themeDisplay.getTimeZone());
			Calendar gcBase = Calendar.getInstance(themeDisplay.getTimeZone());
			gc.setTime(date);
			gcBase.setTime(bDate);
			gc.set(Calendar.DAY_OF_MONTH, gcBase.get(Calendar.DAY_OF_MONTH));
			gc.set(Calendar.MONTH, gcBase.get(Calendar.MONTH));
			gc.set(Calendar.YEAR, gcBase.get(Calendar.YEAR));
			
			if (isStartDate){
				course.setExecutionStartDate(gc.getTime());
			}else{
				course.setExecutionEndDate(gc.getTime());
			}
			CourseLocalServiceUtil.updateCourse(course);
		} catch (SystemException | ParseException e) {
			log.error(e);
		}
	}
	
	private void setCourseTime(String name, ActionRequest request, boolean isStartTime){		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		try {
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());	
			Date date = (isStartTime) ? course.getExecutionStartDate() : course.getExecutionEndDate();
			String sHour = request.getParameter(name);
			
			Calendar gc = Calendar.getInstance(themeDisplay.getTimeZone());
			Calendar gcBase = Calendar.getInstance(themeDisplay.getTimeZone());
			gc.setTime(date);
			
			String[] data = sHour.split(":");
			if(data.length==2){
				gcBase.set(Calendar.HOUR_OF_DAY,Integer.valueOf(data[0]));
				gcBase.set(Calendar.MINUTE,Integer.valueOf(data[1]));
			}
			
			gc.set(Calendar.HOUR_OF_DAY, gcBase.get(Calendar.HOUR_OF_DAY));
			gc.set(Calendar.MINUTE, gcBase.get(Calendar.MINUTE));
			
					
			if (isStartTime){
				course.setExecutionStartDate(gc.getTime());
			}else{
				course.setExecutionEndDate(gc.getTime());
			}
			CourseLocalServiceUtil.updateCourse(course);
								
		} catch (SystemException e) {
			log.error(e);
		}
	}
		
	private void setModuleDate(String name, long id, ActionRequest request, boolean isStartDate){		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			
		try {
			Module module = ModuleLocalServiceUtil.getModule(id);
			Date date = (isStartDate) ? module.getStartDate() : module.getEndDate();			
			String sDate = request.getParameter(name);		
			Date bDate = sdf.parse(sDate);
			
			Calendar gc = Calendar.getInstance(themeDisplay.getTimeZone());
			Calendar gcBase = Calendar.getInstance(themeDisplay.getTimeZone());
			gc.setTime(date);
			gcBase.setTime(bDate);
			gc.set(Calendar.DAY_OF_MONTH, gcBase.get(Calendar.DAY_OF_MONTH));
			gc.set(Calendar.MONTH, gcBase.get(Calendar.MONTH));
			gc.set(Calendar.YEAR, gcBase.get(Calendar.YEAR));
			
			if (isStartDate){
				module.setStartDate(gc.getTime());
			}else{
				module.setEndDate(gc.getTime());
			}
			ModuleLocalServiceUtil.updateModule(module);
		} catch (PortalException | SystemException | ParseException e) {
			log.error(e);
		}
	}
		
	private void setModuleTime(String name, long id, ActionRequest request, boolean isStartTime){		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		try {
			Module module = ModuleLocalServiceUtil.getModule(id);
			Date date = (isStartTime) ? module.getStartDate() : module.getEndDate();
			String sHour = request.getParameter(name);
			
			Calendar gc = Calendar.getInstance(themeDisplay.getTimeZone());
			Calendar gcBase = Calendar.getInstance(themeDisplay.getTimeZone());
			gc.setTime(date);
			
			String[] data = sHour.split(":");
			if(data.length==2){
				gcBase.set(Calendar.HOUR_OF_DAY,Integer.valueOf(data[0]));
				gcBase.set(Calendar.MINUTE,Integer.valueOf(data[1]));
			}
			
			gc.set(Calendar.HOUR_OF_DAY, gcBase.get(Calendar.HOUR_OF_DAY));
			gc.set(Calendar.MINUTE, gcBase.get(Calendar.MINUTE));
			
			if (isStartTime){
				module.setStartDate(gc.getTime());
			}else{
				module.setEndDate(gc.getTime());
			}
			ModuleLocalServiceUtil.updateModule(module);
								
		} catch (PortalException | SystemException e) {
			log.error(e);
		}
	}
	
	private void setP2PUploadDate(String name, long id, ActionRequest request){
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		try{
			String suDate = LearningActivityLocalServiceUtil.getExtraContentValue(id, P2P_DATEUPLOAD);
			Date date = null;
			Date bDate = null;
			if(suDate!=null){
				try{
					date = sdfP2p.parse(suDate);
				}catch(ParseException e){
					date = new Date();
				}
			}
			String sDate = request.getParameter(name);
			bDate = sdf.parse(sDate);
	
			Calendar gc = Calendar.getInstance(themeDisplay.getTimeZone());
			Calendar gcBase = Calendar.getInstance(themeDisplay.getTimeZone());
			
			gc.setTime(date);
			gcBase.setTime(bDate);
					
			gc.set(Calendar.DAY_OF_MONTH, gcBase.get(Calendar.DAY_OF_MONTH));
			gc.set(Calendar.MONTH, gcBase.get(Calendar.MONTH));
			gc.set(Calendar.YEAR, gcBase.get(Calendar.YEAR));			
			
			LearningActivityLocalServiceUtil.setExtraContentValue(id, P2P_DATEUPLOAD, sdfP2p.format(gc.getTime()));		
		}catch(SystemException | ParseException e){
			log.error(e);
		}
	}
	
	private void setP2PUploadTime(String name, long id, ActionRequest request){
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		try {
			String suDate = LearningActivityLocalServiceUtil.getExtraContentValue(id, P2P_DATEUPLOAD);
			Date date = null;			
			if(suDate!=null){
				try{
					date = sdfP2p.parse(suDate);
				}catch(ParseException e){
					date = new Date();
				}
			}
	
			Calendar gc = Calendar.getInstance(themeDisplay.getTimeZone());
			
			gc.setTime(date);
	
			String sHour = request.getParameter(name);
			
			String[] data = sHour.split(":");
			if(data.length==2){
				gc.set(Calendar.HOUR_OF_DAY,Integer.valueOf(data[0]));
				gc.set(Calendar.MINUTE,Integer.valueOf(data[1]));
			}
			
			LearningActivityLocalServiceUtil.setExtraContentValue(id, P2P_DATEUPLOAD, sdfP2p.format(gc.getTime()));
		} catch (SystemException e) {
			log.error(e);
		}
	}
	
	
	private void setActivityDate(String name, long id, ActionRequest request, boolean isStartDate){	
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		try {
			LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(id);
			Date date = (isStartDate) ? activity.getStartdate() : activity.getEnddate();
			String sDate = request.getParameter(name);
			log.debug("sDate::"+ sDate);
			Date bDate = sdf.parse(sDate);
			log.debug("bDate::"+ bDate.toString());
	
			Calendar gc = Calendar.getInstance(themeDisplay.getTimeZone());
			Calendar gcBase = Calendar.getInstance(themeDisplay.getTimeZone());
			if(date!=null){
				gc.setTime(date);
			}
			
			gcBase.setTime(bDate);
			gc.set(Calendar.DAY_OF_MONTH, gcBase.get(Calendar.DAY_OF_MONTH));
			gc.set(Calendar.MONTH, gcBase.get(Calendar.MONTH));
			gc.set(Calendar.YEAR, gcBase.get(Calendar.YEAR));
			
			log.debug("DAY_OF_MONTH::"+ gcBase.get(Calendar.DAY_OF_MONTH));
			log.debug("MONTH::"+ gcBase.get(Calendar.MONTH));
			log.debug("YEAR::"+ gcBase.get(Calendar.YEAR));
			
			if(isStartDate){
				activity.setStartdate(gc.getTime());
			}else{
				activity.setEnddate(gc.getTime());
			}
			LearningActivityLocalServiceUtil.updateLearningActivity(activity);
									
		} catch (SystemException | PortalException | ParseException e) {
			log.error(e);
		} 
	}
	
	
	private void setActivityTime(String name, long id, ActionRequest request, boolean isStartTime){	
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		try {
			LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(id);
			Date date = (isStartTime) ? activity.getStartdate() : activity.getEnddate();
			String sHour = request.getParameter(name);			
	
			Calendar gc = Calendar.getInstance(themeDisplay.getTimeZone());
			Calendar gcBase = Calendar.getInstance(themeDisplay.getTimeZone());
			if(date!=null){
				gc.setTime(date);
			}
			
			String[] data = sHour.split(":");
			if(data.length==2){
				gcBase.set(Calendar.HOUR_OF_DAY,Integer.valueOf(data[0]));
				gcBase.set(Calendar.MINUTE,Integer.valueOf(data[1]));
			}
			
			gc.set(Calendar.HOUR_OF_DAY, gcBase.get(Calendar.HOUR_OF_DAY));
			gc.set(Calendar.MINUTE, gcBase.get(Calendar.MINUTE));
			
			if(isStartTime){
				activity.setStartdate(gc.getTime());
			}else{
				activity.setEnddate(gc.getTime());
			}
			
			LearningActivityLocalServiceUtil.updateLearningActivity(activity);
								
		} catch (PortalException | SystemException e) {
			log.error(e);
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
