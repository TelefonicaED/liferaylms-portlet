package com.liferay.lms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;


/**
 * Portlet implementation class OfflineActivity
 */
public class OfflineActivity extends MVCPortlet {
	
	private static Log log = LogFactoryUtil.getLog(OfflineActivity.class);
	
	private static DateFormat _dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"dd/MM/yyyy",Locale.US);
	
	public static final String NOT_TEACHER_SQL = "WHERE User_.userId NOT IN "+
			 "( SELECT Usergrouprole.userId "+
			 "    FROM Usergrouprole "+ 
			 "   INNER JOIN Resourcepermission ON Usergrouprole.roleId = Resourcepermission.roleId "+
			 "   INNER JOIN Resourceaction ON Resourcepermission.name = Resourceaction.name "+
			 "	   					      AND (BITAND(CAST_LONG(ResourcePermission.actionIds), CAST_LONG(ResourceAction.bitwiseValue)) != 0)"+
			 "   WHERE Resourcepermission.scope="+ResourceConstants.SCOPE_GROUP_TEMPLATE+
			 "     AND Resourceaction.actionId = 'VIEW_RESULTS' "+
			 "     AND Resourceaction.name='com.liferay.lms.model' "+
			 "     AND Usergrouprole.groupid=? ) ";
	
	public static final String ACTIVITY_RESULT_PASSED_SQL = "WHERE (EXISTS (SELECT 1 FROM lms_learningactivityresult " +
			"WHERE User_.userId = lms_learningactivityresult.userId" +
			" AND lms_learningactivityresult.passed > 0 AND lms_learningactivityresult.actId = ? ))"; 
	
	public static final String ACTIVITY_RESULT_FAIL_SQL = "WHERE (EXISTS (SELECT 1 FROM lms_learningactivityresult " +
			"WHERE User_.userId = lms_learningactivityresult.userId" +
			" AND lms_learningactivityresult.passed = 0 AND lms_learningactivityresult.actId = ? ))"; 
	
	public static final String ACTIVITY_RESULT_NO_CALIFICATION_SQL = "WHERE (NOT EXISTS (SELECT 1 FROM lms_learningactivityresult " +
			"WHERE User_.userId = lms_learningactivityresult.userId AND lms_learningactivityresult.actId = ? ))"; 
	
	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
    	ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String action = ParamUtil.getString(resourceRequest, "action");
		long actId = ParamUtil.getLong(resourceRequest, "actId",0);
		if(action.equals("export")){
			
			try {
				//Necesario para crear el fichero csv.
				resourceResponse.setCharacterEncoding(StringPool.UTF8);
				resourceResponse.setContentType(ContentTypes.TEXT_CSV_UTF8);
				resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=data.csv");
		        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
		        
		        resourceResponse.getPortletOutputStream().write(b);
		        
		        CSVWriter writer = new CSVWriter(new OutputStreamWriter(resourceResponse.getPortletOutputStream(),StringPool.UTF8),CharPool.SEMICOLON);
		        String[] cabeceras = new String[4];
		        
		        
		        //En esta columna vamos a tener el nombre del usuario.
		        cabeceras[0]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "onlinetaskactivity.export.user");
		        cabeceras[1]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "onlinetaskactivity.export.date");
		        cabeceras[2]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "onlinetaskactivity.export.result");
		        cabeceras[3]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "onlinetaskactivity.export.comment");
		        		    
		        writer.writeNext(cabeceras);
		        DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivityResult.class);
		      	Criterion criterion=PropertyFactoryUtil.forName("actId").eq(actId);
				dq.add(criterion);
				
				//Partiremos del usuario para crear el csv para que sea más facil ver los intentos.
		        List<LearningActivityResult> listresult = LearningActivityResultLocalServiceUtil.dynamicQuery(dq);
		        for(LearningActivityResult learningActivityResult:listresult){
		        			//Array con los resultados de los intentos.
			        		String[] resultados = new String[4];
			        		//En la primera columna del csv introducidos el nombre del estudiante.
			        		resultados[0] = UserLocalServiceUtil.getUser(learningActivityResult.getUserId()).getScreenName();
			        		resultados[1] = _dateFormat.format(learningActivityResult.getEndDate());
			        		resultados[2] = String.valueOf(learningActivityResult.getResult());
			        		resultados[3] = String.valueOf(learningActivityResult.getComments());
			        		
			        		//Escribimos las respuestas obtenidas para el intento en el csv.
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
		
	private void importGrades(RenderRequest renderRequest,
			RenderResponse renderResponse) throws PortletException, IOException {
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(renderRequest);
		
		List<String> errors= new ArrayList<String>();
		renderRequest.setAttribute("errorsInCSV", errors);
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Locale locale = themeDisplay.getLocale();
		
		long actId = ParamUtil.getLong(renderRequest,"actId");
		long groupId = themeDisplay.getScopeGroupId();
		InputStream csvFile = uploadRequest.getFileAsStream("fileName");
		
		if(csvFile==null){
			errors.add(LanguageUtil.get(getPortletConfig(),locale,"offlinetaskactivity.csvError.empty-file"));
		}else{
			CSVReader reader=null;
			try {
				reader = new CSVReader(new InputStreamReader(csvFile, StringPool.UTF8),CharPool.SEMICOLON);
				int line=0;
				String[] currLine;
				while ((currLine = reader.readNext()) != null) {
					boolean correct=true;
					if(line++!=0){			
						if(currLine.length>=4){
							
							User user=null;
							String userFullName=StringPool.BLANK;
							Date endDate = null;
							long result=0;
							
							try {
								user=UserLocalServiceUtil.getUserByScreenName(themeDisplay.getCompanyId(), currLine[0].trim());
							} catch (NestableException e) {
								correct=false;
								errors.add(LanguageUtil.format(getPortletConfig(),locale,"offlinetaskactivity.csvError.user-id-bad-format",new Object[]{line},false));
							}
							
							if(correct) {
								try {
									userFullName=user.getFullName();
									if(!ArrayUtil.contains(user.getGroupIds(),groupId)){
										correct=false;
										errors.add(LanguageUtil.format(getPortletConfig(),locale,"offlinetaskactivity.csvError.user-id-not-in-course",new Object[]{line},false));
									}
								} catch (PortalException e) {
									correct=false;
									errors.add(LanguageUtil.format(getPortletConfig(),locale,"offlinetaskactivity.csvError.user-id-not-exists",new Object[]{line},false));
								} catch (SystemException e) {
									correct=false;
									errors.add(LanguageUtil.format(getPortletConfig(),locale,"offlinetaskactivity.csvError.user-id-system-error",new Object[]{line},false));
								}
							}
							
							try {
								endDate=_dateFormat.parse(currLine[1]);
							} catch (ParseException e) {
								correct=false;
								errors.add(LanguageUtil.format(getPortletConfig(),locale,"offlinetaskactivity.csvError.date-bad-format",new Object[]{line},false));
							}
														
							try {
								result=Long.parseLong(currLine[2]);
								if(result<0 || result>100){
									correct=false;
									errors.add(LanguageUtil.format(getPortletConfig(),locale,"offlinetaskactivity.csvError.result-bad-format",new Object[]{line},false));
								}
							} catch (NumberFormatException e) {
								correct=false;
								errors.add(LanguageUtil.format(getPortletConfig(),locale,"offlinetaskactivity.csvError.result-bad-format",new Object[]{line},false));
							}
												
							if(correct){
								try {
									LearningActivityTry  learningActivityTry =  LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, user.getUserId());
									if(learningActivityTry==null){
										ServiceContext serviceContext = new ServiceContext();
										serviceContext.setUserId(user.getUserId());
										learningActivityTry =  LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
									}
									learningActivityTry.setEndDate(endDate);
									learningActivityTry.setResult(result);
									learningActivityTry.setComments(currLine[3]);
									updateLearningActivityTryAndResult(learningActivityTry);
		
								} catch (NestableException e) {
									correct=false;
									errors.add(LanguageUtil.format(getPortletConfig(),locale,"offlinetaskactivity.csvError.bad-updating",new Object[]{line,userFullName},false));
								}
							}	
						}
						else {
							if(currLine.length!=0) {
								correct=false;
								errors.add(LanguageUtil.format(getPortletConfig(),locale,"offlinetaskactivity.csvError.bad-format",new Object[]{line},false));
							}
						}
					}
				}
	
			} catch(FileNotFoundException e) {
				errors.add(LanguageUtil.get(getPortletConfig(),locale,"offlinetaskactivity.csvError.empty-file"));
			} finally {
				if(reader!=null) {
					reader.close();
				}
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
			if("importGrades".equals(ajaxAction)) {
				importGrades(renderRequest, renderResponse);
			}
			else if("setGrades".equals(ajaxAction)) {
				setGrades(renderRequest, renderResponse);
			} 
		}
		
		
		super.doDispatch(renderRequest, renderResponse);
	}
	
	public void edit(ActionRequest actionRequest,ActionResponse actionResponse)throws Exception {

		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		if(ParamUtil.getLong(actionRequest, "actId", 0)==0)
		{
			actionResponse.setRenderParameter("jspPage", "/html/offlinetaskactivity/admin/edit.jsp");
		}
	}
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {
		long actId=0;
		
		if(ParamUtil.getBoolean(renderRequest, "actionEditingDetails", false)){
			
			actId=ParamUtil.getLong(renderRequest, "resId", 0);
			renderResponse.setProperty("clear-request-parameters",Boolean.TRUE.toString());
		}
		else{
			actId=ParamUtil.getLong(renderRequest, "actId", 0);
		}
					
		if(actId==0)// TODO Auto-generated method stub
		{
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
		else
		{
				LearningActivity activity;
				try {

					//auditing
					ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
					
					activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
					long typeId=activity.getTypeId();
					
					if(typeId==5)
					{
						super.render(renderRequest, renderResponse);
					}
					else
					{
						renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
					}
				} catch (PortalException e) {
				} catch (SystemException e) {
				}			
		}
	}
	
			
}
