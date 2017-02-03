package com.liferay.lms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.Schedule;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.ScheduleLocalServiceUtil;
import com.liferay.lms.views.ActivityStatsView;
import com.liferay.lms.views.CourseStatsView;
import com.liferay.lms.views.ModuleStatsView;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CourseStats
 */
public class CourseStats extends MVCPortlet {
 
	
	
	private String viewJSP = null;
	private String viewModuleJSP = null;

	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-jsp");
		viewModuleJSP = getInitParameter("view-module-jsp");
	}

	private static Log log = LogFactoryUtil.getLog(StudentSearch.class);
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String jsp = ParamUtil.get(renderRequest, "view", "");
		if(jsp!=null && jsp.equals("viewModule")){
			showViewModule(themeDisplay, renderRequest, renderResponse);
		}else{
			showViewDefault(themeDisplay, renderRequest, renderResponse);
		}
		
	}
	
	private void showViewDefault(ThemeDisplay themeDisplay, RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException{
		try {
			
			log.debug(":: VIEW COURSE STATS "+this.viewJSP);
			
			long teamId = ParamUtil.getLong(renderRequest, "teamId",0);
			List<Team> teams = TeamLocalServiceUtil.getGroupTeams(themeDisplay.getScopeGroupId());
			Course course 	 = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
			
						
			renderRequest.setAttribute("teamId", teamId);
			renderRequest.setAttribute("teams",teams);	
			renderRequest.setAttribute("course", course);
	
			if(teams!=null && teams.size()>0){
				renderRequest.setAttribute("existTeams", true);
			}else{
				renderRequest.setAttribute("existTeams", false);	
			}
			
			PortletURL iteratorURL = renderResponse.createRenderURL();
			iteratorURL.setParameter("teamId", String.valueOf(teamId));
			
			
			CourseStatsView courseStats = getCourseStats(course, teamId, themeDisplay.getLocale());
			
			List<CourseStatsView> courseStatsViews = new ArrayList<CourseStatsView>();
			courseStatsViews.add(courseStats);
			
			SearchContainer<CourseStatsView> searchContainerCourses = new SearchContainer<CourseStatsView>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
					SearchContainer.DEFAULT_DELTA, iteratorURL, 
					null, "no-results");
			searchContainerCourses.setIteratorURL(renderResponse.createRenderURL());
			searchContainerCourses.setResults(courseStatsViews);
			searchContainerCourses.setTotal(courseStatsViews.size());
			
			//Se construye la vista de las estadisticas de los m�dulos
			List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId(), searchContainerCourses.getStart(), searchContainerCourses.getEnd());
			List<ModuleStatsView> moduleStatsViews = new ArrayList<ModuleStatsView>();
			
			Schedule sch = ScheduleLocalServiceUtil.getScheduleByTeamId(teamId);
			for(Module module: modules){
				moduleStatsViews.add(getModuleStats(module, teamId, themeDisplay.getLocale(), themeDisplay.getTimeZone(), sch));
			}
			
			SearchContainer<ModuleStatsView> searchContainerModules = new SearchContainer<ModuleStatsView>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
					SearchContainer.DEFAULT_DELTA, iteratorURL, 
					null, "module-empty-results-message");
			searchContainerModules.setIteratorURL(renderResponse.createRenderURL());
			searchContainerModules.setResults(moduleStatsViews);
			searchContainerModules.setTotal(moduleStatsViews.size());
			
			
			renderRequest.setAttribute("searchContainer", searchContainerCourses);
			renderRequest.setAttribute("moduleSearchContainer", searchContainerModules);
			
				
		} catch (Exception e) {
			e.printStackTrace();
		} 

		this.include(this.viewJSP, renderRequest, renderResponse);
		
	}
	
	
	private void showViewModule(ThemeDisplay themeDisplay, RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException{
		try {
			AssetRendererFactory arf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
			Map<Long, String> classTypes;
			classTypes = arf.getClassTypes(new long[]{themeDisplay.getScopeGroupId()}, themeDisplay.getLocale());
			renderRequest.setAttribute("types", classTypes);
			long moduleId = ParamUtil.getLong(renderRequest, "moduleId", 0);
			long teamId = ParamUtil.getLong(renderRequest, "teamId", 0);
			
			renderRequest.setAttribute("moduleId", moduleId);
			renderRequest.setAttribute("teamId", teamId);
			
			PortletURL iteratorURL = renderResponse.createRenderURL();
			iteratorURL.setParameter("teamId", String.valueOf(teamId));
			iteratorURL.setParameter("moduleId", String.valueOf(moduleId));
			iteratorURL.setParameter("view", "viewModule");
			
			
			SearchContainer<ActivityStatsView> searchContainerActivities = new SearchContainer<ActivityStatsView>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
					SearchContainer.DEFAULT_DELTA, iteratorURL, 
					null, "module-empty-results-message");
			//Se construye la vista de las estadisticas de los m�dulos
			List<LearningActivity> activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(moduleId,searchContainerActivities.getStart(), searchContainerActivities.getEnd() );
			Long total = LearningActivityLocalServiceUtil.countLearningActivitiesOfModule(moduleId);
			
			List<ActivityStatsView> activityStatsViews = new ArrayList<ActivityStatsView>();
			Schedule sch = ScheduleLocalServiceUtil.getScheduleByTeamId(teamId);
			
			CalificationType ct = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId()).getCalificationType());
			
			for(LearningActivity activity: activities){
				activityStatsViews.add(getActivityStats(activity, themeDisplay.getLocale(), teamId, classTypes, sch, themeDisplay.getTimeZone(),ct));
			}
			
			searchContainerActivities.setResults(activityStatsViews);
			searchContainerActivities.setTotal(total.intValue());
			renderRequest.setAttribute("searchContainer", searchContainerActivities);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.include(this.viewModuleJSP, renderRequest, renderResponse);
	}
	
	protected void include(String path, RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);
		if (portletRequestDispatcher == null) {
			
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}
	
	
	
	
	private CourseStatsView getCourseStats (Course course, long teamId, Locale locale) throws PortalException, SystemException{
		
		//Se construye la vista de las estadisticas del curso
		CourseStatsView courseStats = new CourseStatsView(course.getCourseId(), course.getTitle(locale));				
		courseStats.setRegistered(CourseLocalServiceUtil.getStudentsFromCourseCount(course.getCourseId(), teamId));
		courseStats.setStarted(CourseResultLocalServiceUtil.countStudentsStartedByCourseId(course, null, teamId));
		courseStats.setFinished(CourseResultLocalServiceUtil.countStudentsFinishedByCourseId(course, null, teamId));
		courseStats.setPassed(CourseResultLocalServiceUtil.countStudentsPassedByCourseId(course, null, teamId));
		courseStats.setFailed(CourseResultLocalServiceUtil.countStudentsFailedByCourseId(course, null, teamId));
		
		return courseStats;
		
	}
	
	
	private ModuleStatsView getModuleStats(Module module, long teamId, Locale locale, TimeZone timeZone, Schedule sch) throws SystemException{
		ModuleStatsView moduleStats = new ModuleStatsView(module.getModuleId(), module.getTitle(locale), timeZone);
		moduleStats.setStarted(ModuleResultLocalServiceUtil.countStudentsStartedByModuleId(module, null, teamId));
		moduleStats.setFinished(ModuleResultLocalServiceUtil.countStudentsFinishedByModuleId(module, null, teamId));
		if(sch!=null){
			moduleStats.setStartDate(sch.getStartDate());
			moduleStats.setEndDate(sch.getEndDate());
		}else{
			moduleStats.setStartDate(module.getStartDate());
			moduleStats.setEndDate(module.getEndDate());
		}
		moduleStats.setActivityNumber(LearningActivityLocalServiceUtil.countLearningActivitiesOfModule(module.getModuleId()));
		if(module.getPrecedence()!=0){
			Module precedence = ModuleLocalServiceUtil.fetchModule(module.getPrecedence());
			if(precedence!=null){
				moduleStats.setPrecedence(precedence.getTitle(locale));
			}					
		}
		
		return moduleStats;
	}
	
	
	private ActivityStatsView getActivityStats(LearningActivity activity, Locale locale, long teamId, Map<Long, String> classTypes, Schedule sch, TimeZone timeZone, CalificationType ct) throws SystemException{
		ActivityStatsView activityStats = new ActivityStatsView(activity.getActId(), activity.getTitle(locale), timeZone);
		
		activityStats.setStarted(LearningActivityResultLocalServiceUtil.
				countStartedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), null, teamId));
		activityStats.setFinished(LearningActivityResultLocalServiceUtil.
				countFinishedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), null, teamId));
		activityStats.setPassed(LearningActivityResultLocalServiceUtil.
				countPassedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), true, null, teamId));
		activityStats.setFailed(LearningActivityResultLocalServiceUtil.
				countNotPassedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), null, teamId));
		activityStats.setTriesPerUser(LearningActivityResultLocalServiceUtil.
				triesPerUserOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), null, teamId));
		
		String avgResult = ct.translate(locale, activity.getCompanyId(), LearningActivityResultLocalServiceUtil.
				avgResultOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), null, teamId));		
		activityStats.setAvgResult(avgResult);
		activityStats.setAvgResultWithSuffix(avgResult+ct.getSuffix());
		
		String passPuntuation = ct.translate(locale, activity.getCompanyId(), activity.getPasspuntuation());		
		activityStats.setPassPuntuation(passPuntuation);
		activityStats.setPassPuntuationWithSuffix(passPuntuation+ct.getSuffix());
		
		activityStats.setTries(activity.getTries());
		
		boolean hasPrecedence = false;
		if(activity.getPrecedence() > 0){
			hasPrecedence = true;
		}
		activityStats.setPrecedence(LanguageUtil.get(locale,"dependencies."+String.valueOf(hasPrecedence)));
		activityStats.setType(LanguageUtil.get(locale,classTypes.get((long)activity.getTypeId())));
		if(activity.getWeightinmodule() == 1){
			activityStats.setMandatory(LanguageUtil.get(locale, "yes"));
		}else{
			activityStats.setMandatory(LanguageUtil.get(locale, "no"));
		}
		
		if(sch!=null){
			activityStats.setStartDate(sch.getStartDate());
			activityStats.setEndDate(sch.getEndDate());
		}else{
			activityStats.setStartDate(activity.getStartdate());
			activityStats.setEndDate(activity.getEnddate());
		}
		
		return activityStats;
	}
			
	@Override
	public void serveResource(ResourceRequest resourceRequest,
							  ResourceResponse resourceResponse) 
					throws IOException, PortletException {
		String action = ParamUtil.getString(resourceRequest, "action");
		ThemeDisplay themeDisplay  =(ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long teamId   = ParamUtil.getLong(resourceRequest, "teamId", 0);
		
		if("export".equals(action)){
			long courseId = ParamUtil.getLong(resourceRequest, "courseId",0);
			exportCourse(resourceResponse, courseId, teamId, themeDisplay);
		} else if("exportModule".equals(action)){
			long moduleId = ParamUtil.getLong(resourceRequest, "moduleId",0);
			exportModule(resourceResponse, moduleId, teamId, themeDisplay, resourceRequest);
		}
	}
	
	
	private void exportCourse(ResourceResponse resourceResponse, long courseId,
							  long teamId, ThemeDisplay themeDisplay) 
									  throws IOException, UnsupportedEncodingException {
		try {
			Course course = CourseLocalServiceUtil.getCourse(courseId);
			CourseStatsView courseView  = getCourseStats(course, teamId, themeDisplay.getLocale());
			List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId());
			CSVWriter writer = initCsv(resourceResponse);
			 
			//Cabecera del curso
			int numCols = 6;  
			String[] cabeceras = new String[numCols];
			cabeceras[0]=LanguageUtil.get(themeDisplay.getLocale(),"title");
			cabeceras[1]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.registered");
			cabeceras[2]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.start.student");
			cabeceras[3]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.end.student");
			cabeceras[4]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.passed");
			cabeceras[5]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.failed");
			writer.writeNext(cabeceras);
			
			//Resultados del curso
			String[] resultados = new String[numCols];
			resultados[0]=courseView.getCourseTitle();
	    	resultados[1]=Long.toString(courseView.getRegistered());
	    	resultados[2]=Long.toString(courseView.getStarted());
	    	resultados[3]=Long.toString(courseView.getFinished());
	    	resultados[4]=Long.toString(courseView.getPassed());
	    	resultados[5]=Long.toString(courseView.getFailed());	
	      	writer.writeNext(resultados);
			
	      	//Cabecera del modulo
		    numCols = 7;
		    cabeceras = new String[numCols];
		    cabeceras[0]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.module");
		    cabeceras[1]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.stardate");
		    cabeceras[2]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.enddate");
		    cabeceras[3]=LanguageUtil.get(themeDisplay.getLocale(),"total.activity");
		    cabeceras[4]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.start.student");
		    cabeceras[5]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.end.student");
		    cabeceras[6]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.dependencies");
		    writer.writeNext(cabeceras);
    	
		    
		    //Resultados de los modulos
	    	Schedule sch = ScheduleLocalServiceUtil.getScheduleByTeamId(teamId);
	    	ModuleStatsView moduleStats;
		    for(Module module:  modules){
		    	resultados = new String[numCols];
		    	moduleStats = getModuleStats(module, teamId, themeDisplay.getLocale(), themeDisplay.getTimeZone(), sch);
		    	resultados[0]=moduleStats.getModuleTitle();
		    	resultados[1]=moduleStats.getStartDateString();
		    	resultados[2]=moduleStats.getEndDateString();
		    	resultados[3]=Long.toString(moduleStats.getActivityNumber());
		    	resultados[4]=Long.toString(moduleStats.getStarted());
		    	resultados[5]=Long.toString(moduleStats.getFinished());		    	
		    	resultados[6]=moduleStats.getPrecedence();
		        writer.writeNext(resultados);
		    }
		    endCsv(resourceResponse, writer);
		    
		} catch (NestableException e) {
			e.printStackTrace();
		}finally{
			resourceResponse.getPortletOutputStream().flush();
			resourceResponse.getPortletOutputStream().close();
		}
	}

	
	private void exportModule(ResourceResponse resourceResponse, long moduleId,
							  long teamId, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) 
									  throws IOException, UnsupportedEncodingException {
		try {
			Module module = ModuleLocalServiceUtil.getModule(moduleId);		
			Schedule sch = ScheduleLocalServiceUtil.getScheduleByTeamId(teamId);
			ModuleStatsView moduleStats = getModuleStats(module, teamId, themeDisplay.getLocale(), themeDisplay.getTimeZone(), sch);
			AssetRendererFactory arf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
			Map<Long, String> classTypes;
			classTypes = arf.getClassTypes(new long[]{themeDisplay.getScopeGroupId()}, themeDisplay.getLocale());						
			
			CSVWriter writer = initCsv(resourceResponse);
			
	
			//Cabecera del modulo
		    int numCols = 7;
		    String[] cabeceras = new String[numCols];
		    cabeceras[0]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.module");
		    cabeceras[1]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.stardate");
		    cabeceras[2]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.enddate");
		    cabeceras[3]=LanguageUtil.get(themeDisplay.getLocale(),"total.activity");
		    cabeceras[4]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.start.student");
		    cabeceras[5]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.end.student"); 
		    cabeceras[6]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.dependencies");
		    
		    //Resultados del modulo
		    String[] resultados = new String[numCols];
	    	moduleStats = getModuleStats(module, teamId, themeDisplay.getLocale(), themeDisplay.getTimeZone(), sch);
	    	resultados[0]=moduleStats.getModuleTitle();
	    	resultados[1]=moduleStats.getStartDateString();
	    	resultados[2]=moduleStats.getEndDateString();
	    	resultados[3]=Long.toString(moduleStats.getActivityNumber());
	    	resultados[4]=Long.toString(moduleStats.getStarted());
	    	resultados[5]=Long.toString(moduleStats.getFinished());	    	
	    	resultados[6]=moduleStats.getPrecedence();
	        writer.writeNext(resultados);
		   
	        //Cabecera de la actividad
	        numCols = 14;
		    cabeceras = new String[numCols];
		    cabeceras[0]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.activity");
		    cabeceras[1]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.activity.start");
		    cabeceras[2]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.activity.end");
		    cabeceras[3]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.init");
		    cabeceras[4]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.finished");
		    cabeceras[5]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.passed");
		    cabeceras[6]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.failed");
		    cabeceras[7]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.trials.average");
		    cabeceras[8]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.marks.average");
		    cabeceras[9]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.pass.mark");
		    cabeceras[10]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.trials.numbers");
		    cabeceras[11]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.act-precedence");
		    cabeceras[12]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.type");
		    cabeceras[13]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.obligatory");    
		    writer.writeNext(cabeceras);
		    
		    //Resultados de las actividades
		    List<LearningActivity> activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(moduleId);
		    ActivityStatsView activityView;
		    
			CalificationType ct = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.getCourseByGroupCreatedId(module.getGroupId()).getCalificationType());
			
		    
		    for(LearningActivity activity : activities){
		    	activityView = getActivityStats(activity, themeDisplay.getLocale(), teamId, classTypes, sch, themeDisplay.getTimeZone(),ct);
		    	resultados = new String[numCols];
		 		
		    	resultados[0]=activityView.getActTitle();
		    	resultados[1]=activityView.getStartDateString();
		    	resultados[2]=activityView.getEndDateString();
		    	resultados[3]=Long.toString(activityView.getStarted());
		    	resultados[4]=Long.toString(activityView.getFinished());
		    	resultados[5]=Long.toString(activityView.getPassed());
		    	resultados[6]=Long.toString(activityView.getFailed());
		    	resultados[7]=activityView.getTriesPerUserString();
		    	resultados[8]=activityView.getAvgResult();
		    	resultados[9]=activityView.getPassPuntuation();
		    	resultados[10]=Long.toString(activityView.getTries());
		    	resultados[11]=activityView.getPrecedence();
		    	resultados[12]=activityView.getType();
		    	resultados[13]=activityView.getMandatory();
		    			    	
		        writer.writeNext(resultados);
		    }
			
		    endCsv(resourceResponse, writer);
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			resourceResponse.getPortletOutputStream().flush();
			resourceResponse.getPortletOutputStream().close();
		}
	}

	private void endCsv(ResourceResponse resourceResponse, CSVWriter writer)
			throws IOException {
		writer.flush();
		writer.close();
		resourceResponse.getPortletOutputStream().flush();
		resourceResponse.getPortletOutputStream().close();
	}

	private CSVWriter initCsv(ResourceResponse resourceResponse)
			throws IOException, UnsupportedEncodingException {
		//Necesario para crear el fichero csv.
		resourceResponse.setCharacterEncoding(StringPool.UTF8);
		resourceResponse.setContentType(ContentTypes.TEXT_CSV_UTF8);
		resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=Statistics.csv");
		byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
		
		resourceResponse.getPortletOutputStream().write(b);
		
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(resourceResponse.getPortletOutputStream(),StringPool.UTF8),CharPool.SEMICOLON);
		return writer;
	}

}
