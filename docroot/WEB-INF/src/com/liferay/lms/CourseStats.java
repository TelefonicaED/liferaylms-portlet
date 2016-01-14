package com.liferay.lms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.comparator.UserLastNameComparator;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CourseStats
 */
public class CourseStats extends MVCPortlet {
 
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
			Group group = GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
			List<User> usersList = new ArrayList<User>();
			
			long registered	 = 0;
			long finalizados = 0;
			long iniciados	 = 0;
			
			if (teamId != 0){
				
				//Usuarios del equipo
				usersList = getUsersOfTeam(courseId, teamId, themeDisplay);
				
				//Colección con los id's de Usuario
				Collection<Object> usersCollection = new ArrayList<Object>(usersList.size());
				
				//Usuarios registrados en el curso
				List<User> registeredUsers = CourseLocalServiceUtil
												.getStudentsFromCourse (course);
				
				for (User usuario: usersList){
					//Añadimos el id a la colección de usuarios
					usersCollection.add(usuario.getUserId());
					
					//Comprobamos si está registrado en el curso
					if (registeredUsers.contains(usuario))
						registered += 1;
				}
				DynamicQuery courseResultsQueryF = DynamicQueryFactoryUtil.forClass(CourseResult.class)
													.add(PropertyFactoryUtil.forName("courseId").eq(new Long(course.getCourseId())))
													.add(PropertyFactoryUtil.forName("passed").eq(new Boolean(true)))
													.add(PropertyFactoryUtil.forName("userId")
														.in(usersCollection));

				DynamicQuery courseResultsQueryI = DynamicQueryFactoryUtil.forClass(CourseResult.class)
													.add(PropertyFactoryUtil.forName("courseId").eq(new Long(course.getCourseId())))
													.add(PropertyFactoryUtil.forName("passed").eq(new Boolean(false)))
													.add(PropertyFactoryUtil.forName("userId")
														.in(usersCollection));

				finalizados  = CourseResultLocalServiceUtil.dynamicQuery(courseResultsQueryF).size();
				iniciados 	 = CourseResultLocalServiceUtil.dynamicQuery(courseResultsQueryI).size() + finalizados;
	
			}else{
				registered=CourseLocalServiceUtil.getStudentsFromCourse(themeDisplay.getCompanyId(), course.getGroupCreatedId()).size();
				finalizados = CourseResultLocalServiceUtil.countStudentsByCourseId(course, true);
				iniciados = CourseResultLocalServiceUtil.countStudentsByCourseId(course, false) + finalizados;
			}
			
			List<Module> tempResults = ModuleLocalServiceUtil.findAllInGroup(group.getGroupId());
			CSVWriter writer = initCsv(resourceResponse);
		    ResourceBundle rb =  ResourceBundle.getBundle("content.Language");
		    rb = ResourceBundle.getBundle("content.Language", themeDisplay.getLocale());
		    
		    //Curso
		    writer.writeNext(new String[]{course.getTitle(themeDisplay.getLocale())});
		    
		    //Inscritos
		    Object args[] = {registered};
		    writer.writeNext(new String[]{LanguageUtil.format(themeDisplay.getLocale(), "coursestats.modulestats.inscritos", args)});
		    
		    //Iniciaron/finalizaron
		    writer.writeNext(new String[]{LanguageUtil.get(themeDisplay.getLocale(), "coursestats.start.course") +" "+ iniciados + LanguageUtil.get(themeDisplay.getLocale(),"coursestats.end.course") +" "+ finalizados});
		    
		    int numCols = 7;
		    String[] cabeceras = new String[numCols];
		    
		    cabeceras[0]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.module");
		    cabeceras[1]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.stardate");
		    cabeceras[2]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.enddate");
		    cabeceras[3]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.start.student");
		    cabeceras[4]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.end.student");
		    cabeceras[5]=LanguageUtil.get(themeDisplay.getLocale(),"total.activity");
		    cabeceras[6]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.dependencies");
		    
		    writer.writeNext(cabeceras);

	    	java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
	    	sdf.setTimeZone(themeDisplay.getTimeZone());
		    
		    for(Module modulo:tempResults){
		    	String[] resultados = new String[numCols];
		    	long started = 0;
		    	long finished = 0;
		    	
		    	int totalActivity=0;
				
				List<LearningActivity> actividades = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(modulo.getModuleId());
				totalActivity = actividades.size();
		    	
		    	if (teamId == 0) {
		    		started=ModuleResultLocalServiceUtil.countByModuleOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), modulo.getModuleId());
		    		finished=ModuleResultLocalServiceUtil.countByModulePassedOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),modulo.getModuleId(),true);
		    	}else{
		    		started=ModuleResultLocalServiceUtil.countByModuleOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), modulo.getModuleId(), usersList);
		    		finished=ModuleResultLocalServiceUtil.countByModulePassedOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),modulo.getModuleId(),true, usersList);
		    	}
		    	String moduloBloqueo = "";
		    	if(modulo.getPrecedence() != 0) {
		    		Module modulePredence = ModuleLocalServiceUtil.getModule(modulo.getPrecedence());
		    		moduloBloqueo=modulePredence.getTitle(themeDisplay.getLocale());
		    	}else{
		    		moduloBloqueo=rb.getString("dependencies.false");
		    	}
		    	resultados[0]=modulo.getTitle(themeDisplay.getLocale());
		    	resultados[1]=sdf.format(modulo.getStartDate());
		    	resultados[2]=sdf.format(modulo.getEndDate());
		    	resultados[3]=Long.toString(started);
		    	resultados[4]=Long.toString(finished);
		    	resultados[5]=Long.toString(totalActivity);
		    	resultados[6]=moduloBloqueo;
		        writer.writeNext(resultados);
		    }
		    endCsv(resourceResponse, writer);
		} catch (NestableException e) {
		}finally{
			resourceResponse.getPortletOutputStream().flush();
			resourceResponse.getPortletOutputStream().close();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<User> getUsersOfTeam (	long courseId, 
										long teamId, 
										ThemeDisplay themeDisplay) 
			throws PortalException, SystemException {
		
		OrderByComparator obc	 = new UserLastNameComparator(true);
		LinkedHashMap userParams = new LinkedHashMap();
		LmsPrefs prefs			 = LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
		
		Course curso = CourseLocalServiceUtil.getCourse(courseId);
		
		userParams.put( "notInCourseRoleTeach", new CustomSQLParam("WHERE User_.userId NOT IN "
				        + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
				        + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
				        curso.getGroupCreatedId(),
				        RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId() }));

		userParams.put( "notInCourseRoleEdit", new CustomSQLParam("WHERE User_.userId NOT IN "
				        + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
				        + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
				        curso.getGroupCreatedId(),
				        RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId() }));
		
		userParams.put("usersGroups", new Long(themeDisplay.getScopeGroupId()));
		userParams.put("usersTeams", teamId);
		
		return UserLocalServiceUtil.search(themeDisplay.getCompanyId(), StringPool.BLANK, 0, 
										   userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);
	}

	private void exportModule(ResourceResponse resourceResponse, long moduleId,
							  long teamId, ThemeDisplay themeDisplay, ResourceRequest resourceRequest) 
									  throws IOException, UnsupportedEncodingException {
		try {
			
			java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
			List<User> usersList = new ArrayList<User>();
			Course curso = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());

			Module module = ModuleLocalServiceUtil.getModule(moduleId);
			Group group = GroupLocalServiceUtil.getGroup(module.getGroupId());
			long registered=CourseLocalServiceUtil.getStudentsFromCourse(themeDisplay.getCompanyId(), module.getGroupId()).size();
			//long registered=UserLocalServiceUtil.getGroupUsersCount(group.getGroupId(),0);
			long iniciados = 0;
	    	long finalizados = 0;
	    	
			if (teamId != 0){
				//Usuarios registrados en el curso
				List<User> registeredUsers = CourseLocalServiceUtil.getStudentsFromCourse(	themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId());
				
				usersList = getUsersOfTeam(curso.getCourseId(), teamId, themeDisplay);
				iniciados=ModuleResultLocalServiceUtil.countByModuleOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), module.getModuleId(), usersList);
		    	finalizados=ModuleResultLocalServiceUtil.countByModulePassedOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),module.getModuleId(),true, usersList);
		    	
		    	registered = 0;
		    	for (User usuario: usersList){	
					//Comprobamos si está registrado en el curso
		    		if (registeredUsers.contains(usuario))
		    			registered += 1;
		    	}
			}else{
				iniciados=ModuleResultLocalServiceUtil.countByModuleOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), module.getModuleId());
		    	finalizados=ModuleResultLocalServiceUtil.countByModulePassedOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),module.getModuleId(),true);
			}
			
			String mStartDate = module.getStartDate() == null? "":sdf.format(module.getStartDate());
			String mEndDate = module.getEndDate() == null? "":sdf.format(module.getEndDate());
			
			List<LearningActivity> tempResults = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(moduleId);
			CSVWriter writer = initCsv(resourceResponse);
			
			ResourceBundle rb =  ResourceBundle.getBundle("content.Language");
		    rb = ResourceBundle.getBundle("content.Language", themeDisplay.getLocale());
		    
		    //Curso
		    writer.writeNext(new String[]{group.getName()});
		    
		    //Modulo
		    writer.writeNext(new String[]{module.getTitle(themeDisplay.getLocale())});
		    
		    //Inscritos
		    Object args[] = {registered};
		    writer.writeNext(new String[]{MessageFormat.format(rb.getString("coursestats.modulestats.inscritos"),registered)});
		    
		    //Iniciaron/finalizaron
		    writer.writeNext(new String[]{rb.getString("coursestats.start.course") + iniciados});// + rb.getString("coursestats.end.course") + finalizados});
		    
		    //Fechas del mï¿½dulo
		    writer.writeNext(new String[]{mStartDate + " " + mEndDate});
		    
		    int numCols = 14;
		    String[] cabeceras = new String[numCols];
		    cabeceras[0]=rb.getString("coursestats.modulestats.activity");
		    cabeceras[1]=rb.getString("coursestats.modulestats.activity.start");
		    cabeceras[2]=rb.getString("coursestats.modulestats.activity.end");
		    cabeceras[3]=rb.getString("coursestats.modulestats.init");
		    cabeceras[4]=rb.getString("coursestats.modulestats.passed");
		    cabeceras[5]=rb.getString("coursestats.modulestats.failed");
		    cabeceras[6]=rb.getString("coursestats.modulestats.trials.average");
		    cabeceras[7]=rb.getString("coursestats.modulestats.marks.average");
		    cabeceras[8]=rb.getString("coursestats.modulestats.pass.mark");
		    cabeceras[9]=rb.getString("coursestats.modulestats.trials.numbers");
		    cabeceras[10]=rb.getString("coursestats.modulestats.dependencies");
		    cabeceras[11]=rb.getString("coursestats.modulestats.type");
		    cabeceras[12]=rb.getString("coursestats.modulestats.obligatory");
		    cabeceras[13]="";
		    		    
		    writer.writeNext(cabeceras);
		   
		    for(LearningActivity activity:tempResults){
		    	String[] resultados = new String[numCols];
		    	
		    	long started = 0;
		    	long finished = 0;
		    	long notpassed = 0;
		    	double avgResult = 0;
		    	double triesPerUser = 0;
		    	boolean hasPrecedence = false;
		    	if (teamId != 0){
			    	started=LearningActivityResultLocalServiceUtil.countStartedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), usersList);
			    	finished=LearningActivityResultLocalServiceUtil.countPassedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(),true, usersList);
			    	notpassed=LearningActivityResultLocalServiceUtil.countNotPassedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), usersList);
		    	
			    	if(finished+notpassed>0)
			    		avgResult=LearningActivityResultLocalServiceUtil.avgResultOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), usersList);
			    	triesPerUser=LearningActivityResultLocalServiceUtil.triesPerUserOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), usersList);
			    	
			    	if(activity.getPrecedence() > 0)
			    		hasPrecedence = true;
		    	}else{
		    		started=LearningActivityResultLocalServiceUtil.countStartedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId());
			    	finished=LearningActivityResultLocalServiceUtil.countPassedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(),true);
			    	notpassed=LearningActivityResultLocalServiceUtil.countNotPassedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId());
		    	
			    	if(finished+notpassed>0)
			    		avgResult=LearningActivityResultLocalServiceUtil.avgResultOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId());
			    	triesPerUser=LearningActivityResultLocalServiceUtil.triesPerUserOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId());
			   
			    	if(activity.getPrecedence() > 0)
			    		hasPrecedence = true;
		    	}
		    	NumberFormat numberFormat = NumberFormat.getNumberInstance(themeDisplay.getLocale());
		    	
		    	resultados[0]=activity.getTitle(themeDisplay.getLocale());
		    	if(activity.getStartdate()!=null) resultados[1]=sdf.format(activity.getStartdate());
		    	else resultados[1]="-";
		    	if(activity.getStartdate()!=null) resultados[2]=sdf.format(activity.getEnddate());
		    	else resultados[2]="-";
		    	resultados[3]=Long.toString(started);
		    	resultados[4]=Long.toString(finished);
		    	resultados[5]=Long.toString(notpassed);
		    	resultados[6]=numberFormat.format(triesPerUser);
		    	resultados[7]=numberFormat.format(avgResult);
		    	resultados[8]=numberFormat.format(activity.getPasspuntuation());
		    	resultados[9]=numberFormat.format(activity.getTries());
		    	resultados[10]=String.valueOf(hasPrecedence);
		    	resultados[11]=Integer.toString(activity.getTypeId());
		    	resultados[12]=activity.getWeightinmodule() == 1 ? "Si":"No";
		    	resultados[13]="";//getExtraData(themeDisplay, activity, resourceRequest);
		    	
		        writer.writeNext(resultados);
		    }
			
		    endCsv(resourceResponse, writer);
		
		} catch (NestableException e) {
		}finally{
			resourceResponse.getPortletOutputStream().flush();
			resourceResponse.getPortletOutputStream().close();
		}
	}

	private String getExtraData(ThemeDisplay themeDisplay, LearningActivity activity,
			ResourceRequest resourceRequest) {
		
		PortletConfig portletConfig = (PortletConfig)resourceRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		
		String typeId = StringPool.BLANK;
		try {
			AssetRendererFactory arf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
			Map<Long, String> classTypes = arf.getClassTypes(new long[0], themeDisplay.getLocale());
			typeId = classTypes.get((long)activity.getTypeId());
		} catch (Exception e) {
		}
		
		String result = "", head="", val="";
		LearningActivity precedenceActivity=null;
		if(activity.getPrecedence()!=0) {
			try{
				precedenceActivity=LearningActivityLocalServiceUtil.getLearningActivity(activity.getPrecedence());
				String title=precedenceActivity.getTitle(themeDisplay.getLocale());
				int maxNameLength=GetterUtil.getInteger(LanguageUtil.get(portletConfig,resourceRequest.getLocale(), "coursestats.modulestats.large.name.length"),20);
				
				if(title.length()>maxNameLength) 
					title=MessageFormat.format(LanguageUtil.get(portletConfig,resourceRequest.getLocale(), "coursestats.modulestats.large.name"),title.subSequence(0, maxNameLength+1));
				
				head=replaceAcutes(LanguageUtil.get(portletConfig,resourceRequest.getLocale(), "coursestats.modulestats.dependencies"));
				val=replaceAcutes(title);
				result = result == "" ? head + " = " + val:result + "; "+head + " = " + val;
				head=""; val="";
			}
			catch(NestableException e){}
		}

		if((activity.getExtracontent()!=null)&&(activity.getExtracontent().length()!=0)) {
			try{
				Iterator<Element> nodes= SAXReaderUtil.read(activity.getExtracontent()).getRootElement().elementIterator();
				while(nodes.hasNext()){
					Element node =(Element)nodes.next();
					if(node.getText().length()!=0) {
						String extraFieldType = "";
						try{
							String headerKey="coursestats.modulestats."+typeId+"."+node.getName();
							head=replaceAcutes(LanguageUtil.get(portletConfig,resourceRequest.getLocale(), headerKey));
							extraFieldType=LanguageUtil.get(portletConfig,resourceRequest.getLocale(), headerKey + ".type");
						}catch(Exception e){
							e.printStackTrace();
						}
						String value=null;
						if("seconds".equals(extraFieldType)) {
							try{
								long seconds = Long.parseLong(node.getTextTrim());
								NumberFormat timeNumberFormat = NumberFormat.getInstance(themeDisplay.getLocale());
								timeNumberFormat.setMinimumIntegerDigits(2);
								value=timeNumberFormat.format(seconds / 3600)+StringPool.COLON+
									  timeNumberFormat.format((seconds % 3600) / 60)+StringPool.COLON+
									  timeNumberFormat.format(seconds % 60);
							}
							catch(NumberFormatException e) {
								value=LanguageUtil.get(portletConfig,resourceRequest.getLocale(), node.getTextTrim());
							}
						}else {
							value=LanguageUtil.get(portletConfig,resourceRequest.getLocale(), node.getTextTrim());
						}
						
						val=replaceAcutes(value);
						result = result == "" ? head + " = " + val:result + "; "+head + " = " + val;
		    			head=""; val="";
					}
				}
			}
			catch(DocumentException e){}
		}
		return result;
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
		resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=data.csv");
		byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
		
		resourceResponse.getPortletOutputStream().write(b);
		
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(resourceResponse.getPortletOutputStream(),StringPool.UTF8),CharPool.SEMICOLON);
		return writer;
	}
	
	private String replaceAcutes(String text){
		String result = text;
		result = result.replaceAll("&aacute;", "ï¿½");
		result = result.replaceAll("&eacute;", "ï¿½");
		result = result.replaceAll("&iacute;", "ï¿½");
		result = result.replaceAll("&oacute;", "ï¿½");
		result = result.replaceAll("&uacute;", "ï¿½");
		result = result.replaceAll("&ntilde;", "ï¿½");
		return result;
	}

}
