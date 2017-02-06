package com.liferay.lms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
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
import com.liferay.lms.service.impl.CourseServiceImpl;
import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
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
	
	private static Log log = LogFactoryUtil.getLog(GradeBook.class);
	
	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		String action = ParamUtil.getString(resourceRequest, "action");
		long moduleId = ParamUtil.getLong(resourceRequest, "moduleId",0);
		long teamId=ParamUtil.getLong(resourceRequest, "teamId",0);
		ThemeDisplay themeDisplay  =(ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		if(action.equals("export")){
			
			try {
				Team theTeam=null;
				java.util.List<Team> userTeams=TeamLocalServiceUtil.getUserTeams(themeDisplay.getUserId(), themeDisplay.getScopeGroupId());
				if(teamId>0 && (TeamLocalServiceUtil.hasUserTeam(themeDisplay.getUserId(), teamId)||userTeams.size()==0))
				{		
					theTeam=TeamLocalServiceUtil.fetchTeam(teamId);	
				}
				Module module = ModuleLocalServiceUtil.getModule(moduleId);
				List<LearningActivity> learningActivities = LearningActivityServiceUtil
						.getLearningActivitiesOfModule(moduleId);

				//Necesario para crear el fichero csv.
				resourceResponse.setCharacterEncoding(StringPool.UTF8);
				resourceResponse.setContentType(ContentTypes.TEXT_CSV_UTF8);
				resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=data_"+Long.toString(System.currentTimeMillis())+".csv");
		        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
		        
		        resourceResponse.getPortletOutputStream().write(b);
		        
		        CSVWriter writer = new CSVWriter(new OutputStreamWriter(resourceResponse.getPortletOutputStream(),StringPool.UTF8),CharPool.SEMICOLON);
	
		        //Comunidad
		        writer.writeNext(new String[]{themeDisplay.getScopeGroupName()});
		        
		        //M�dulo
		        writer.writeNext(new String[]{module.getTitle(themeDisplay.getLocale())});
		        
		        String[] cabeceras = new String[learningActivities.size()+4];
		        
		        int column=4;
		        cabeceras[0]=LanguageUtil.get(themeDisplay.getLocale(),"user-name");
		        cabeceras[1]=LanguageUtil.get(themeDisplay.getLocale(),"last-name");
		        cabeceras[2]=LanguageUtil.get(themeDisplay.getLocale(),"user-id");
		        cabeceras[3]=LanguageUtil.get(themeDisplay.getLocale(),"email");
		        
		        for(LearningActivity learningActivity:learningActivities){
		        	cabeceras[column++]=learningActivity.getTitle(themeDisplay.getLocale());
		        }
		        		    
		        writer.writeNext(cabeceras);
		        List<User> usus=null;
				LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());

				LinkedHashMap userParams = new LinkedHashMap();

				
				Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(module.getGroupId());
				
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
					usus  = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), "", 0, userParams, 0, 5000, obc);	
				
		        for(User usuario:usus){
		        	String[] resultados = new String[learningActivities.size()+4];
		        	
		        	column=4;
		        	resultados[0]=usuario.getFirstName();
		        	resultados[1]=usuario.getLastName();
		        	
		        	resultados[2]=String.valueOf(usuario.getUserId());
		        	resultados[3]=usuario.getEmailAddress();
		        	

			        for(LearningActivity learningActivity:learningActivities){
						LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), usuario.getUserId());
						
						if(learningActivityResult==null) {
							resultados[column++]=StringPool.BLANK;
						}
						else {
							resultados[column++]=Long.toString(learningActivityResult.getResult());
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
			if(result<ct.getMinValue() || result>ct.getMaxValue()){
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
				learningActivityTry.setResult(ct.toBase100(result));
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
		double result=ParamUtil.getDouble(renderRequest,"result");
		try {
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());			
			ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());			
			log.debug("result: "+result);
			if(result<ct.getMinValue() || result>ct.getMaxValue()){
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
				learningActivityTry.setResult(ct.toBase100(result));
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
			LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(learningActivityTry.getActId());
			learningActivityResult.setResult(learningActivityTry.getResult());
			learningActivityResult.setPassed(learningActivityTry.getResult()>=learningActivity.getPasspuntuation());
			LearningActivityResultLocalServiceUtil.updateLearningActivityResult(learningActivityResult);
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

}
