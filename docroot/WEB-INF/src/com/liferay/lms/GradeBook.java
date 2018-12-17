package com.liferay.lms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
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
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.util.displayterms.UserDisplayTerms;
import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.comparator.UserFirstNameComparator;
import com.liferay.portal.util.comparator.UserLastNameComparator;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class GradeBook
 */
public class GradeBook extends MVCPortlet {
	
	private String viewJSP = null;
	private String userDetailsJSP = null;
	
	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
		userDetailsJSP = getInitParameter("user-details-template");
	}
	
	private static Log log = LogFactoryUtil.getLog(GradeBook.class);
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		String jsp = renderRequest.getParameter("view");
		log.debug("view:"+jsp);

		PortletURL renderURL = renderResponse.createRenderURL();
		renderRequest.setAttribute("renderURL", renderURL.toString());
		
		super.doView(renderRequest, renderResponse);
		
		if (jsp == null || "".equals(jsp) || "view-template".equals(jsp)) {
			showViewDefault(renderRequest, renderResponse);
		}else if ("user-details".equals(jsp)) {
			showViewUserDetails(renderRequest, renderResponse);
		}
	}
	
	private void showViewDefault(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		UserDisplayTerms userDisplayTerms = new UserDisplayTerms(renderRequest);
			
		//Buscamos los usuario
		
		PortletURL iteratorURL = renderResponse.createRenderURL();
		iteratorURL.setParameter("team", Long.toString(userDisplayTerms.getTeamId()));
		iteratorURL.setParameter("firstName", userDisplayTerms.getFirstName());
		iteratorURL.setParameter("lastName", userDisplayTerms.getLastName());
		iteratorURL.setParameter("screenName", userDisplayTerms.getScreenName());
		iteratorURL.setParameter("emailAddress", userDisplayTerms.getEmailAddress());
		
		SearchContainer<User> searchContainer = new SearchContainer<User>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
				10, iteratorURL, 
				null, "there-are-no-users");
		
		List<User> results = null;
		int total = 0;
		try {
			OrderByComparator obc = null;
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
			PortletPreferences portalPreferences = PortalPreferencesLocalServiceUtil.getPreferences(themeDisplay.getCompanyId(), themeDisplay.getCompanyId(), 1);
			if(Boolean.parseBoolean(portalPreferences.getValue("users.first.last.name", "false"))){
				obc = new UserLastNameComparator(true);
			}else{
				obc = new UserFirstNameComparator(true);
			}
			results = CourseLocalServiceUtil.getStudents(course.getCourseId(), themeDisplay.getCompanyId(), userDisplayTerms.getScreenName(), userDisplayTerms.getFirstName(), 
					userDisplayTerms.getLastName(), userDisplayTerms.getEmailAddress(), WorkflowConstants.STATUS_APPROVED, userDisplayTerms.getTeamId(), true, searchContainer.getStart(),
					searchContainer.getEnd(), obc);

			total = CourseLocalServiceUtil.countStudents(course.getCourseId(), themeDisplay.getCompanyId(), userDisplayTerms.getScreenName(), userDisplayTerms.getFirstName(), 
					userDisplayTerms.getLastName(), userDisplayTerms.getEmailAddress(), WorkflowConstants.STATUS_APPROVED, userDisplayTerms.getTeamId(), true);
			
			searchContainer.setResults(results);
			searchContainer.setTotal(total);
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<SearchContainer<User>> searchContainers = new ArrayList<SearchContainer<User>>();
		
		try {
			List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
			
			SearchContainer<User> searchContainerList = null;
			
			for(Module module: modules){
				if(searchContainerList != null){
					searchContainerList = new SearchContainer<User>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
							10, iteratorURL, 
							null, "there-are-no-users");
					searchContainerList.setResults(results);
					searchContainerList.setTotal(total);
				}else{
					searchContainerList = searchContainer;
				}
				searchContainerList.setId("search_"+module.getModuleId());
				searchContainers.add(searchContainerList);
			}
			renderRequest.setAttribute("modules", modules);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 
		renderRequest.setAttribute("displayTerms", userDisplayTerms);
		renderRequest.setAttribute("searchContainers", searchContainers);
		
		try {
			CalificationType ct = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId()).getCalificationType());
			renderRequest.setAttribute("ct", ct);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		include(this.viewJSP, renderRequest, renderResponse);
	}	
	
	private void showViewUserDetails(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		
		
		include(this.userDetailsJSP, renderRequest, renderResponse);
	}	
	
	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		ThemeDisplay themeDisplay  =(ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String action = ParamUtil.getString(resourceRequest, "action");
		long groupId = ParamUtil.getLong(resourceRequest, "groupId", 0);
		long moduleId = ParamUtil.getLong(resourceRequest, "moduleId",0);
		long teamId=ParamUtil.getLong(resourceRequest, "team",0);
		
		if(log.isDebugEnabled()){
			log.debug("Resource:: action :: " + action);
			log.debug("Resource:: groupId :: " + groupId);
			log.debug("Resource:: moduleId :: " + moduleId);
			log.debug("Resource:: teamId :: " + teamId);
		}
		
		if(action.equals("export")){
			try {
				Team theTeam=null;
				List<Team> userTeams=TeamLocalServiceUtil.getUserTeams(themeDisplay.getUserId(), themeDisplay.getScopeGroupId());
				if(teamId>0 && (TeamLocalServiceUtil.hasUserTeam(themeDisplay.getUserId(), teamId)||userTeams.size()==0))
					theTeam=TeamLocalServiceUtil.fetchTeam(teamId);	
				Module module = ModuleLocalServiceUtil.getModule(moduleId);
				List<LearningActivity> learningActivities = LearningActivityServiceUtil.getLearningActivitiesOfModule(moduleId);

				//Necesario para crear el fichero csv.
				resourceResponse.setCharacterEncoding(StringPool.UTF8);
				resourceResponse.setContentType(ContentTypes.TEXT_CSV_UTF8);
				resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=data_"+Long.toString(System.currentTimeMillis())+".csv");
		        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
		        
		        resourceResponse.getPortletOutputStream().write(b);
		        
		        CSVWriter writer = new CSVWriter(new OutputStreamWriter(resourceResponse.getPortletOutputStream(),StringPool.UTF8),CharPool.SEMICOLON);
	
		        Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(module.getGroupId());
		        
		        //Curso
		        writer.writeNext(new String[]{course.getTitle(themeDisplay.getLocale())});
		        
		        //Módulo
		        writer.writeNext(new String[]{module.getTitle(themeDisplay.getLocale())});
		        
		        String[] cabeceras = new String[learningActivities.size()+4];
		        
		        int column=4;
		        cabeceras[0]=LanguageUtil.get(themeDisplay.getLocale(),"user-name");
		        cabeceras[1]=LanguageUtil.get(themeDisplay.getLocale(),"last-name");
		        cabeceras[2]=LanguageUtil.get(themeDisplay.getLocale(),"screen-name");
		        cabeceras[3]=LanguageUtil.get(themeDisplay.getLocale(),"email");
		        
		        for(LearningActivity learningActivity:learningActivities){
		        	cabeceras[column++]=learningActivity.getTitle(themeDisplay.getLocale());
		        }
		        		    
		        writer.writeNext(cabeceras);
		        List<User> usus=null;
				LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());

				LinkedHashMap userParams = new LinkedHashMap();
				
				CalificationType ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());
				
				if(theTeam!=null)
				{
					userParams.put("usersGroups", theTeam.getGroupId());
					userParams.put("usersTeams", theTeam.getTeamId());
				}
				
				userParams.put("usersGroups", course.getGroupCreatedId());
					
					userParams.put("notInCourseRoleTeach", new CustomSQLParam("WHERE User_.userId NOT IN "
				              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
				              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
				            		  course.getGroupCreatedId(),
				              RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId() }));
				           


				       	userParams.put("notInCourseRoleEdit", new CustomSQLParam("WHERE User_.userId NOT IN "
				              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
				              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
				              course.getGroupCreatedId(),
				              RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId() }));

					OrderByComparator obc = new UserFirstNameComparator(true);
					usus  = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), "", 0, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);	
				
		        for(User usuario:usus){
		        	String[] resultados = new String[learningActivities.size()+4];
		        	
		        	column=4;
		        	resultados[0]=usuario.getFirstName();
		        	resultados[1]=usuario.getLastName();		        	
		        	resultados[2]=usuario.getScreenName();
		        	resultados[3]=usuario.getEmailAddress();
		        	

			        for(LearningActivity learningActivity:learningActivities){
						LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), usuario.getUserId());
						
						if(learningActivityResult==null) {
							resultados[column++]=StringPool.BLANK;
						}
						else {
							resultados[column++]=ct.translate(themeDisplay.getLocale(), course.getGroupCreatedId(),learningActivityResult.getResult());
						}

			        }

			        writer.writeNext(resultados);
		        }
				
		        writer.flush();
				writer.close();
				resourceResponse.getPortletOutputStream().flush();
				resourceResponse.getPortletOutputStream().close();
			
			} catch (NestableException e) {
			}finally{
				resourceResponse.getPortletOutputStream().flush();
				resourceResponse.getPortletOutputStream().close();
			}
		} else if (action.equals("exportAll")){
			
			try {
				
				Team theTeam = null;
				List<Team> userTeams=TeamLocalServiceUtil.getUserTeams(themeDisplay.getUserId(), themeDisplay.getScopeGroupId());
				if(teamId>0 && (TeamLocalServiceUtil.hasUserTeam(themeDisplay.getUserId(), teamId)||userTeams.size()==0))
					theTeam=TeamLocalServiceUtil.fetchTeam(teamId);	
				
				//Necesario para crear el fichero csv.
				resourceResponse.setCharacterEncoding(StringPool.UTF8);
				resourceResponse.setContentType(ContentTypes.TEXT_CSV_UTF8);
				resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=data_"+Long.toString(System.currentTimeMillis())+".csv");
		        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
		        
		        resourceResponse.getPortletOutputStream().write(b);
		        
		        CSVWriter writer = new CSVWriter(new OutputStreamWriter(resourceResponse.getPortletOutputStream(),StringPool.UTF8),CharPool.SEMICOLON);
	
		        //Curso
		        Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		        writer.writeNext(new String[]{course.getTitle(themeDisplay.getLocale())});
		        
		        //Número de columnas del informe
		        int headerSize = (int)LearningActivityLocalServiceUtil.countLearningActivitiesOfGroup(themeDisplay.getScopeGroupId())+4;
		        
		        //Lista de módulos
		        List<Module> listModules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
		        List<LearningActivity> listLearningActivities = new ArrayList<LearningActivity>();
		        
		        //Títulos de los módulos
		        String[] moduleTitles = new String[headerSize];
		        List<LearningActivity> listLearningActivitiesOfModule = new ArrayList<LearningActivity>();
		        int column = 3;
		        for(Module module:listModules){
		        	listLearningActivitiesOfModule = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(module.getModuleId());
		        	if(Validator.isNotNull(listLearningActivitiesOfModule) && listLearningActivitiesOfModule.size()>0){
			        	moduleTitles[column]= module.getTitle(themeDisplay.getLocale());
			        	column = column + listLearningActivitiesOfModule.size();
			        	listLearningActivities.addAll(listLearningActivitiesOfModule);
		        	}
		        }
		        writer.writeNext(moduleTitles);
		        
		        //Cabeceras
		        String[] header = new String[headerSize];
		        column = 0;
		        //Usuario
		        header[column]=LanguageUtil.get(themeDisplay.getLocale(),"user-name");
		        header[column++]=LanguageUtil.get(themeDisplay.getLocale(),"last-name");
		        header[column++]=LanguageUtil.get(themeDisplay.getLocale(),"screen-name");
		        header[column++]=LanguageUtil.get(themeDisplay.getLocale(),"email");
		        //Título de la actividad
				for(LearningActivity learningActivity:listLearningActivities)
					header[column++] = learningActivity.getTitle(themeDisplay.getLocale());
		        writer.writeNext(header);
				
		        //Datos usuarios
				LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
				LinkedHashMap userParams = new LinkedHashMap();
				CalificationType ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());
				if(theTeam!=null){
					userParams.put("usersGroups", theTeam.getGroupId());
					userParams.put("usersTeams", theTeam.getTeamId());
				}
				userParams.put("usersGroups", course.getGroupCreatedId());
				userParams.put("notInCourseRoleTeach", new CustomSQLParam("WHERE User_.userId NOT IN "
			              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
			              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
			            		  course.getGroupCreatedId(),
			              RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId() }));

			    userParams.put("notInCourseRoleEdit", new CustomSQLParam("WHERE User_.userId NOT IN "
			              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
			              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
			              course.getGroupCreatedId(),
			              RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId() }));

				OrderByComparator obc = new UserFirstNameComparator(true);
				List<User> listUsers = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), "", 0, userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);	
				
				String[] results;
		        for(User user:listUsers){
		        	results = new String[headerSize];
		        	
		        	column=0;
		        	results[column]=user.getFirstName();
		        	results[column++]=user.getLastName();		        	
		        	results[column++]=user.getScreenName();
		        	results[column++]=user.getEmailAddress();

		        	//Resultados del usuario
			        for(LearningActivity learningActivity:listLearningActivities){
						LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), user.getUserId());
						if(learningActivityResult==null) 
							results[column++]=StringPool.BLANK;
						else 
							results[column++]=ct.translate(themeDisplay.getLocale(), course.getGroupCreatedId(),learningActivityResult.getResult());
			        }

			        writer.writeNext(results);
		        }
				
		        writer.flush();
				writer.close();
				resourceResponse.getPortletOutputStream().flush();
				resourceResponse.getPortletOutputStream().close();
			
			} catch (NestableException e) {
				e.printStackTrace();
			}finally{
				resourceResponse.getPortletOutputStream().flush();
				resourceResponse.getPortletOutputStream().close();
			}
		}
	}
 
	
	public void setGrades(ActionRequest request,	ActionResponse response){
		
		ThemeDisplay themeDisplay  =(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean correct=true;
		long actId = ParamUtil.getLong(request,"actId"); 
		long studentId = ParamUtil.getLong(request,"studentId");		
		String comments = ParamUtil.getString(request,"comments");
		
		log.debug("actId: "+actId);
		log.debug("studentId: "+studentId);
		log.debug("comments: "+comments);		
		

		CalificationType ct = null;
		double result=0;
		try {
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());			
			ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());			
			result= Double.valueOf(ParamUtil.getString(request,"result").replace(",", "."));
			log.debug("result: "+result);
			if(result<ct.getMinValue(course.getGroupCreatedId()) || result>ct.getMaxValue(course.getGroupCreatedId())){
				correct=false;
				log.error("Result fuera de rango");
				SessionErrors.add(request, "result-bad-format");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			correct=false;
			SessionErrors.add(request, "result-bad-format");
		} catch (Exception e) {
			e.printStackTrace();
			correct=false;
			SessionErrors.add(request, "grades.bad-updating");
		}
		
		if(correct) {
			try {
				LearningActivityTry  learningActivityTry =  LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, studentId);
				if(learningActivityTry==null){
					ServiceContext serviceContext = new ServiceContext();
					serviceContext.setUserId(studentId);
					learningActivityTry =  LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
				}
				learningActivityTry.setEndDate(new Date());
				learningActivityTry.setResult(ct.toBase100(themeDisplay.getScopeGroupId(),result));
				learningActivityTry.setComments(comments);
				updateLearningActivityTryAndResult(learningActivityTry);
				
				SessionMessages.add(request, "grades.updating");
			} catch (NestableException e) {
				SessionErrors.add(request, "grades.bad-updating");
			}
		}
	}
	
	private void setGrades(RenderRequest renderRequest,	RenderResponse renderResponse) throws IOException {
		
		ThemeDisplay themeDisplay  =(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean correct=true;
		long actId = ParamUtil.getLong(renderRequest,"actId"); 
		long studentId = ParamUtil.getLong(renderRequest,"studentId");		
		String comments = renderRequest.getParameter("comments");
		
		log.debug("actId: "+actId);
		log.debug("studentId: "+studentId);
		log.debug("comments: "+comments);		
		

		CalificationType ct = null;
		double result=0;
		try {
			result=ParamUtil.getDouble(renderRequest,"result");
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());			
			ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());			
			log.debug("result: "+result);
			if(result<ct.getMinValue(course.getGroupCreatedId()) || result>ct.getMaxValue(course.getGroupCreatedId())){
				correct=false;
				log.error("Result fuera de rango");
				SessionErrors.add(renderRequest, "offlinetaskactivity.grades.result-bad-format");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			correct=false;
			SessionErrors.add(renderRequest, "offlinetaskactivity.grades.result-bad-format");
		} catch (Exception e) {
			e.printStackTrace();
			correct=false;
			SessionErrors.add(renderRequest, "offlinetaskactivity.grades.bad-updating");
		}
		
		if(correct) {
			try {
				LearningActivityTry  learningActivityTry =  LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, studentId);
				if(learningActivityTry==null){
					ServiceContext serviceContext = new ServiceContext();
					serviceContext.setUserId(studentId);
					learningActivityTry =  LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
				}
				learningActivityTry.setEndDate(new Date());
				learningActivityTry.setResult(ct.toBase100(themeDisplay.getScopeGroupId(),result));
				learningActivityTry.setComments(comments);
				updateLearningActivityTryAndResult(learningActivityTry);
				
				SessionMessages.add(renderRequest, "offlinetaskactivity.grades.updating");
			} catch (NestableException e) {
				SessionErrors.add(renderRequest, "offlinetaskactivity.grades.bad-updating");
			}
		}
	}

	private void updateLearningActivityTryAndResult(
			LearningActivityTry learningActivityTry) throws PortalException,
			SystemException {
		LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningActivityTry);
		
		LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivityTry.getActId(), learningActivityTry.getUserId());
		if(learningActivityResult.getResult() != learningActivityTry.getResult()) {
			LearningActivityResultLocalServiceUtil.update(learningActivityTry);
		}
	}
	
	@Override
	protected void doDispatch(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		String ajaxAction = renderRequest.getParameter("ajaxAction");
		if(ajaxAction!=null) {
			if("setGrades".equals(ajaxAction)) {
				setGrades(renderRequest, renderResponse);
			} 
		}
		
		
		super.doDispatch(renderRequest, renderResponse);
	}
	
	protected void include(String path, RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}

}