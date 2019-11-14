package com.liferay.lms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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

import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.comparator.UserFirstNameComparator;
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
			String gradeFilter = ParamUtil.getString(resourceRequest, "gradeFilter");
			String criteria = ParamUtil.getString(resourceRequest, "criteria");
			
			try {

				CalificationType ct = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId()).getCalificationType());

				//Necesario para crear el fichero csv.
				resourceResponse.setCharacterEncoding(StringPool.UTF8);
				resourceResponse.setContentType(ContentTypes.TEXT_CSV_UTF8);
				resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=data.csv");
				byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};

				resourceResponse.getPortletOutputStream().write(b);

				CSVWriter writer = new CSVWriter(new OutputStreamWriter(resourceResponse.getPortletOutputStream(),StringPool.UTF8),CharPool.SEMICOLON);
				String[] cabeceras = new String[6];

				String authType = themeDisplay.getCompany().getAuthType();

				//En esta columna vamos a tener el nombre del usuario.
				if (CompanyConstants.AUTH_TYPE_EA.compareToIgnoreCase(authType) == 0) {
					cabeceras[0]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "email");
				} else if (CompanyConstants.AUTH_TYPE_SN.compareToIgnoreCase(authType) == 0) {
					cabeceras[0]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "screen-name");
				} else {
					cabeceras[0]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "user-id");
				}
				
				cabeceras[1]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "name");
				cabeceras[2]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "last-name");
				cabeceras[3]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "onlinetaskactivity.export.date");
				cabeceras[4]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "onlinetaskactivity.export.result");
				cabeceras[5]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "onlinetaskactivity.export.comment");

				writer.writeNext(cabeceras);
				
				List<User> userListsOfCourse = OfflineActivity.getCalificationUsers(themeDisplay, actId, criteria, gradeFilter);
				
				for(User user : userListsOfCourse){
					//Array con los resultados de los intentos.
					String[] resultados = new String[6];
					//En la primera columna del csv introducidos el nombre del estudiante.
					if(user!=null){
						try{
							//Obtenemos el método de login para exportar el usuario.
													
							if (CompanyConstants.AUTH_TYPE_EA.compareToIgnoreCase(authType) == 0) {
								//Caso para el emailAddress
								if(log.isDebugEnabled()){log.debug("Caso para el EmailAddress");}
								resultados[0] = user.getEmailAddress();
								
							} else if (CompanyConstants.AUTH_TYPE_SN.compareToIgnoreCase(authType) == 0) {
								//Caso para el screenName
								if(log.isDebugEnabled()){log.debug("Caso para el ScreenName");}
								resultados[0] = user.getScreenName();
								
							} else {
								//Caso para el userId
								if(log.isDebugEnabled()){log.debug("Caso para el userId");}
								resultados[0] = String.valueOf(user.getUserId());						
							}								
							LearningActivityResult lar = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, user.getUserId());
							resultados[1] = user.getFirstName();
							resultados[2] = user.getLastName();
	
							if(lar!=null){
								resultados[3] = _dateFormat.format(lar.getEndDate());
								resultados[4] = ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), lar.getResult());
								resultados[5] = lar.getComments()!=null?lar.getComments():"";
							}else{
								resultados[3] = "-";
								resultados[4] = "-";
								resultados[5] = LanguageUtil.get(themeDisplay.getLocale(), "offlinetaskactivity.student.without.qualification");
							}
	
							//Escribimos las respuestas obtenidas para el intento en el csv.
							writer.writeNext(resultados);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				writer.flush();
				writer.close();
				resourceResponse.getPortletOutputStream().flush();
				resourceResponse.getPortletOutputStream().close();

			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				resourceResponse.getPortletOutputStream().flush();
				resourceResponse.getPortletOutputStream().close();
			}
		} else if (action.equals("importGradesExample")) {
			try {
				CalificationType ct = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId()).getCalificationType());
	
				//Necesario para crear el fichero csv.
				resourceResponse.setCharacterEncoding(StringPool.UTF8);
				resourceResponse.setContentType(ContentTypes.TEXT_CSV_UTF8);
				resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=data.csv");
				byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
	
				resourceResponse.getPortletOutputStream().write(b);
	
				CSVWriter writer = new CSVWriter(new OutputStreamWriter(resourceResponse.getPortletOutputStream(),StringPool.UTF8),CharPool.SEMICOLON);
				String[] cabeceras = new String[6];
	
	
				//En esta columna vamos a tener el nombre del usuario.
				String authType = themeDisplay.getCompany().getAuthType();

				//En esta columna vamos a tener el nombre del usuario.
				if (CompanyConstants.AUTH_TYPE_EA.compareToIgnoreCase(authType) == 0) {
					cabeceras[0]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "email");
				} else if (CompanyConstants.AUTH_TYPE_SN.compareToIgnoreCase(authType) == 0) {
					cabeceras[0]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "screen-name");
				} else {
					cabeceras[0]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "user-id");
				}
				cabeceras[1]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "name");
				cabeceras[2]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "last-name");
				cabeceras[3]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "onlinetaskactivity.export.date");
				cabeceras[4]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "onlinetaskactivity.export.result");
				cabeceras[5]= LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "onlinetaskactivity.export.comment");
	
				writer.writeNext(cabeceras);
				
				//Array con los resultados de los intentos.
				String[] resultados = new String[6];
				//En la primera columna del csv introducidos el nombre del estudiante.
				User user = UserLocalServiceUtil.fetchUser(themeDisplay.getUserId());
				if(user!=null){
					try{
						//Obtenemos el método de login para exportar el usuario.
														
						if (CompanyConstants.AUTH_TYPE_EA.compareToIgnoreCase(authType) == 0) {
							//Caso para el emailAddress
							if(log.isDebugEnabled()){log.debug("Caso para el EmailAddress");}
							resultados[0] = user.getEmailAddress();
							
						} else if (CompanyConstants.AUTH_TYPE_SN.compareToIgnoreCase(authType) == 0) {
							//Caso para el screenName
							if(log.isDebugEnabled()){log.debug("Caso para el ScreenName");}
							resultados[0] = user.getScreenName();
							
						} else {
							//Caso para el userId
							if(log.isDebugEnabled()){log.debug("Caso para el userId");}
							resultados[0] = String.valueOf(user.getUserId());						
						}								
								
						resultados[1] = user.getFirstName();
						resultados[2] = user.getLastName();
						resultados[3] = _dateFormat.format(new Date());
						resultados[4] = String.valueOf(ct.getMaxValue(themeDisplay.getScopeGroupId()));
						resultados[5] = LanguageUtil.get(themeDisplay.getLocale(), "comments");
		
						//Escribimos las respuestas obtenidas para el intento en el csv.
						writer.writeNext(resultados);
					}catch(Exception e){
						e.printStackTrace();
					}
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

				CalificationType ct = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId()).getCalificationType());

				reader = new CSVReader(new InputStreamReader(csvFile, StringPool.UTF8),CharPool.SEMICOLON);
				int line=0;
				String[] currLine;
				while ((currLine = reader.readNext()) != null) {
					boolean correct=true;
					if(line++!=0){			
						if(currLine.length>=5){

							User user=null;
							String userFullName=StringPool.BLANK;
							Date endDate = null;
							double result=0;
							if(log.isDebugEnabled()){log.debug("Current Line length: "+currLine.length);}
							try {
								//Obtenemos el método de login para importar el usuario.
								String userLogin = null;
								String authType = null;
								Company company = CompanyLocalServiceUtil.getCompany(themeDisplay.getCompanyId());
								if (Validator.isNotNull(company)) {
									authType = company.getAuthType();
								}
								if (CompanyConstants.AUTH_TYPE_EA.compareToIgnoreCase(authType) == 0) {
									//Caso para el emailAddress
									if(log.isDebugEnabled()){log.debug("Caso para el EmailAddress");}
									userLogin = currLine[0].trim();
									if (Validator.isNull(userLogin) || userLogin.isEmpty() || !Validator.isEmailAddress(userLogin)) {
										correct = Boolean.FALSE;
										log.error(LanguageUtil.format(getPortletConfig(), themeDisplay.getLocale(), "offlinetaskactivity.csvError.user-id-bad-format",
												new Object[] { line }, false));
									}else{
										user=UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), userLogin);
									}
								} else if (CompanyConstants.AUTH_TYPE_SN.compareToIgnoreCase(authType) == 0) {
									//Caso para el screenName
									if(log.isDebugEnabled()){log.debug("Caso para el ScreenName");}
									userLogin = currLine[0].trim();
									if (Validator.isNull(userLogin) || userLogin.isEmpty()) {
										correct = Boolean.FALSE;
										log.error(LanguageUtil.format(getPortletConfig(), themeDisplay.getLocale(), "offlinetaskactivity.csvError.user-id-bad-format",
												new Object[] { line }, false));
									}else{
										user=UserLocalServiceUtil.getUserByScreenName(themeDisplay.getCompanyId(), userLogin);
									}
								} else {
									//Caso para el userId
									if(log.isDebugEnabled()){log.debug("Caso para el userId");}
									try {
										userLogin = currLine[0].trim();
										user= UserLocalServiceUtil.fetchUser(Long.parseLong(userLogin));
									} catch (Exception e) {
										correct = false;
										errors.add(LanguageUtil.format(getPortletConfig(), themeDisplay.getLocale(), "offlinetaskactivity.csvError.user-id-bad-format",
												new Object[] { line }, false));
										log.error(e.toString());
									}
								}								
							} catch (Exception e) {
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
								endDate=_dateFormat.parse(currLine[3]);
							} catch (ParseException e) {
								correct=false;
								errors.add(LanguageUtil.format(getPortletConfig(),locale,"offlinetaskactivity.csvError.date-bad-format",new Object[]{line},false));
							}

							try {
								result=Double.parseDouble(currLine[4]);
								if(result<ct.getMinValue(groupId) || result>ct.getMaxValue(groupId)){	
									log.error("***Result fuera de rango***");
									correct=false;
									errors.add(LanguageUtil.format(getPortletConfig(),locale,"offlinetaskactivity.csvError.result-bad-format",new Object[]{line,ct.getMinValue(groupId),ct.getMaxValue(groupId)},false));
								}
							} catch (NumberFormatException e) {
								e.printStackTrace();
								correct=false;
								errors.add(LanguageUtil.format(getPortletConfig(),locale,"offlinetaskactivity.csvError.result-bad-format",new Object[]{line,ct.getMinValue(groupId),ct.getMaxValue(groupId)},false));
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
									learningActivityTry.setResult(ct.toBase100(themeDisplay.getScopeGroupId(),result));
									if(currLine.length>5){
										learningActivityTry.setComments(currLine[5]);
									}
									updateLearningActivityTryAndResult(learningActivityTry);

								} catch (Exception e) {
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
				e.printStackTrace();
				errors.add(LanguageUtil.get(getPortletConfig(),locale,"offlinetaskactivity.csvError.empty-file"));
			} catch(Exception e){
				e.printStackTrace();
				errors.add(LanguageUtil.get(getPortletConfig(),locale,"offlinetaskactivity.csvError.bad-updating"));
			}

			finally {
				if(reader!=null) {
					reader.close();
				}
			}
		}
	}



	public void setGrades(ActionRequest request,	ActionResponse response){

		ThemeDisplay themeDisplay  =(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		int curValue = ParamUtil.getInteger(request, "curValue");
		boolean correct=true;
		long actId = ParamUtil.getLong(request,"actId"); 
		long studentId = ParamUtil.getLong(request,"studentId");		
		String comments = ParamUtil.getString(request,"comments");

		log.debug("actId: "+actId);
		log.debug("studentId: "+studentId);
		log.debug("comments: "+comments);		

		String gradeFilter = ParamUtil.getString(request, "gradeFilter");
		String criteria = ParamUtil.getString(request, "criteria");

		log.debug("gradeFilter: "+gradeFilter);
		log.debug("criteria: "+criteria);

		response.setRenderParameter("gradeFilter", gradeFilter);
		response.setRenderParameter("criteria", criteria);		
		response.setRenderParameter("curValue", String.valueOf(curValue));
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
			LearningActivityTypeRegistry registry = new LearningActivityTypeRegistry();
			LearningActivityType learningActivityType = registry.getLearningActivityType(learningActivity.getTypeId());
			learningActivityResult.setPassed(learningActivityType.isPassed(learningActivity, learningActivityTry));
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

	
	public static List<User> getCalificationUsers(ThemeDisplay themeDisplay, long actId, String criteria, String gradeFilter) throws Exception{
		LinkedHashMap<String,Object> params = new LinkedHashMap<String,Object>();
		LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
		params.put("usersGroups", new Long(themeDisplay.getScopeGroupId()));
		 params.put("notInCourseRoleTeach", new CustomSQLParam("WHERE User_.userId NOT IN "
	              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
	              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
	              themeDisplay.getScopeGroupId(),
	              RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId() }));
		 
		 params.put("notInCourseRoleEdit", new CustomSQLParam("WHERE User_.userId NOT IN "
	              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
	              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
	            		  themeDisplay.getScopeGroupId(),
	              RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId() }));
		if(gradeFilter.equals("passed")) {
			params.put("passed",new CustomSQLParam(OfflineActivity.ACTIVITY_RESULT_PASSED_SQL,actId));
		}
		else {
			if(gradeFilter.equals("failed")) {
				params.put("failed",new CustomSQLParam(OfflineActivity.ACTIVITY_RESULT_FAIL_SQL,actId));
			} else {
				if (gradeFilter.equals("nocalification")) {
					params.put("nocalification",new CustomSQLParam(OfflineActivity.ACTIVITY_RESULT_NO_CALIFICATION_SQL,actId));
				}
			}
		}
		if(!StringPool.BLANK.equals(LearningActivityLocalServiceUtil.getExtraContentValue(actId,"team"))){
			String teamId = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"team");
			Team team = TeamLocalServiceUtil.getTeam(Long.parseLong(teamId));
			params.put("usersTeams", team.getTeamId());
		}
	
		OrderByComparator obc = new UserFirstNameComparator(true);
	
		return UserLocalServiceUtil.search(themeDisplay.getCompanyId(), criteria, WorkflowConstants.STATUS_ANY, params, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);
			
		
		
	}

}
