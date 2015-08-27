package com.liferay.lms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
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
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class GradeBook
 */
public class GradeBook extends MVCPortlet {
	
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
		        
		        String[] cabeceras = new String[learningActivities.size()+2];
		        
		        int column=2;
		        cabeceras[0]="User";
		        cabeceras[1]="Email";
		        for(LearningActivity learningActivity:learningActivities){
		        	cabeceras[column++]=learningActivity.getTitle(themeDisplay.getLocale());
		        }
		        		    
		        writer.writeNext(cabeceras);
		        List<User> usus=null;
				if(theTeam==null)
				{
					usus = UserLocalServiceUtil.getGroupUsers(themeDisplay.getScopeGroupId());
				}
				else
				{
					LinkedHashMap userParams = new LinkedHashMap();
					userParams.put("usersGroups", theTeam.getGroupId());
					userParams.put("usersTeams", theTeam.getTeamId());
					OrderByComparator obc = null;
					usus  = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), "", 0, userParams, 0, 1000, obc);	
				}
		        for(User usuario:usus){
		        	String[] resultados = new String[learningActivities.size()+2];
		        	
		        	column=2;
		        	resultados[0]=usuario.getFullName()+"("+usuario.getUserId()+")";
		        	resultados[1]=usuario.getEmailAddress();
		        	

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
 
	private void setGrades(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		
		boolean correct=true;
		long actId = ParamUtil.getLong(renderRequest,"actId"); 
		long studentId = ParamUtil.getLong(renderRequest,"studentId");
				
		String comments = renderRequest.getParameter("comments");
		long result=0;
		try {
			result=Long.parseLong(renderRequest.getParameter("result"));
			if(result<0 || result>100){
				correct=false;
				SessionErrors.add(renderRequest, "offlinetaskactivity.grades.result-bad-format");
			}
		} catch (NumberFormatException e) {
			correct=false;
			SessionErrors.add(renderRequest, "offlinetaskactivity.grades.result-bad-format");
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
				learningActivityTry.setResult(result);
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
