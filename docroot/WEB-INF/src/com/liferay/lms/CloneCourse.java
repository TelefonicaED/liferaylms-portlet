package com.liferay.lms;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.IOUtils;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.model.AnnouncementsFlagConstants;
import com.liferay.portlet.announcements.service.AnnouncementsEntryServiceUtil;
import com.liferay.portlet.announcements.service.AnnouncementsFlagLocalServiceUtil;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

public class CloneCourse implements MessageListener {
	private static Log log = LogFactoryUtil.getLog(CloneCourse.class);

	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads";

	long groupId;
	
	String newCourseName;
	
	ThemeDisplay themeDisplay;
	ServiceContext serviceContext;
	
	Date startDate;
	Date endDate;
	
	boolean visible;
	boolean includeTeacher;
	
	private String cloneTraceStr = "--------------- Clone course trace ----------------"; 
		
	public CloneCourse(long groupId, String newCourseName, ThemeDisplay themeDisplay, Date startDate, Date endDate, ServiceContext serviceContext) {
		super();
		this.groupId = groupId;
		this.newCourseName = newCourseName;
		this.themeDisplay = themeDisplay;
		this.startDate = startDate;
		this.endDate = endDate;
		this.serviceContext = serviceContext;
	}
	

	public CloneCourse() {
	}
	
	
	
	@Override
	public void receive(Message message) throws MessageListenerException {
		
		try {
			
			this.groupId	= message.getLong("groupId");
			this.newCourseName = message.getString("newCourseName");
			
			this.startDate 	= (Date)message.get("startDate");
			this.endDate 	= (Date)message.get("endDate");
			
			this.serviceContext = (ServiceContext)message.get("serviceContext");
			this.themeDisplay = (ThemeDisplay)message.get("themeDisplay");
			
			this.visible = message.getBoolean("visible");
			this.includeTeacher = message.getBoolean("includeTeacher");
			Role adminRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(),"Administrator");
			List<User> adminUsers = UserLocalServiceUtil.getRoleUsers(adminRole.getRoleId());
			 
			PrincipalThreadLocal.setName(adminUsers.get(0).getUserId());
			PermissionChecker permissionChecker =PermissionCheckerFactoryUtil.create(adminUsers.get(0), true);
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		
			doCloneCourse();
			
			log.debug("Clone Stack Trace: "+cloneTraceStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void doCloneCourse() throws Exception {
		cloneTraceStr += " Course to clone\n........................." + groupId;
		
		log.debug("  + groupId: "+groupId);
		
		Group group = GroupLocalServiceUtil.getGroup(groupId);
		Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(groupId);
				
		log.debug("  + course: "+course.getTitle(themeDisplay.getLocale()));
		cloneTraceStr += " course:" + course.getTitle(themeDisplay.getLocale()); 
		cloneTraceStr += " groupId:" + groupId;
		
		Date today=new Date(System.currentTimeMillis());

		String courseTemplate = this.serviceContext.getRequest().getParameter("courseTemplate");
		long layoutSetPrototypeId = 0;
		if(courseTemplate.indexOf("&")>-1){
			layoutSetPrototypeId = Long.parseLong(courseTemplate.split("&")[1]);
		}else{
			layoutSetPrototypeId = Long.parseLong(courseTemplate);
		}		
		
		/*LmsPrefs lmsPrefs=LmsPrefsLocalServiceUtil.getLmsPrefsIni(serviceContext.getCompanyId());
		
		System.out.println("  + getLmsTemplates: "+lmsPrefs.getLmsTemplates());
		
		if(lmsPrefs.getLmsTemplates().contains(",")){
			String []ids = lmsPrefs.getLmsTemplates().split(",");
			
			for(String id:ids){
				LayoutSetPrototype layout = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.valueOf(id));
				layoutSetPrototypeId=Long.valueOf(id);
				System.out.println("  + layout: "+layout.getDescription());
				if(layout.getDescription().equals("course")){
					break;
				}
			}
		}else if(!"".equals(lmsPrefs.getLmsTemplates())){
			LayoutSetPrototype layout = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.valueOf(lmsPrefs.getLmsTemplates()));
			layoutSetPrototypeId=Long.valueOf(lmsPrefs.getLmsTemplates());
		}else{
			layoutSetPrototypeId = LayoutSetLocalServiceUtil.getLayoutSet(groupId, true).getLayoutSetPrototypeId();
		}*/
		log.debug("  + layoutSetPrototypeId: "+layoutSetPrototypeId);
		cloneTraceStr += " layoutSetPrototypeId:" + layoutSetPrototypeId;
		
		try{
			AssetEntryLocalServiceUtil.validate(course.getGroupCreatedId(), Course.class.getName(), serviceContext.getAssetCategoryIds(), serviceContext.getAssetTagNames());
			serviceContext.setAssetCategoryIds(AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId()).getCategoryIds());
			log.debug("  + AssetCategoryIds: "+AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId()).getCategoryIds().toString());
		}catch(Exception e){
			serviceContext.setAssetCategoryIds(new long[]{});
			//serviceContext.setAssetTagNames(AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId()).getTags());
		}
		
		//Course newCourse = CourseLocalServiceUtil.addCourse(newCourseName, course.getDescription(), "", themeDisplay.getLocale() , today, startDate, endDate, serviceContext, course.getCalificationType());
		
		//when lmsprefs has more than one lmstemplate selected the addcourse above throws an error.
		
		
		int typeSite = GroupLocalServiceUtil.getGroup(course.getGroupCreatedId()).getType();
		Course newCourse = null;  
		String summary = new String();
		try{
			summary = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),course.getCourseId()).getSummary(themeDisplay.getLocale());
			newCourse = CourseLocalServiceUtil.addCourse(newCourseName, course.getDescription(themeDisplay.getLocale()),summary
					, "", themeDisplay.getLocale(), today, startDate, endDate, layoutSetPrototypeId, typeSite, serviceContext, course.getCalificationType(), (int)course.getMaxusers(),true);
			
			newCourse.setWelcome(course.getWelcome());
			newCourse.setWelcomeMsg(course.getWelcomeMsg());
			newCourse.setWelcomeSubject(course.getWelcomeSubject());
			newCourse.setGoodbye(course.getGoodbye());
			newCourse.setGoodbyeMsg(course.getGoodbyeMsg());
			newCourse.setGoodbyeSubject(course.getGoodbyeSubject());
			newCourse.setCourseEvalId(course.getCourseEvalId());
			
		} catch(DuplicateGroupException e){
			if(log.isDebugEnabled())e.printStackTrace();
			throw new DuplicateGroupException();
		}
	
		newCourse.setExpandoBridgeAttributes(serviceContext);
		
		newCourse.getExpandoBridge().setAttributes(course.getExpandoBridge().getAttributes());
		//Course newCourse = CourseLocalServiceUtil.addCourse(newCourseName, course.getDescription(), "", "", themeDisplay.getLocale(), today, today, today, layoutSetPrototypeId, serviceContext);
		Group newGroup = GroupLocalServiceUtil.getGroup(newCourse.getGroupCreatedId());
		serviceContext.setScopeGroupId(newCourse.getGroupCreatedId());
		
		Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
		
		
		newCourse.setIcon(course.getIcon());
		
		try{
			newCourse = CourseLocalServiceUtil.modCourse(newCourse, serviceContext);
			
			AssetEntry entry = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),newCourse.getCourseId());
			entry.setVisible(visible);
			entry.setSummary(summary);
			AssetEntryLocalServiceUtil.updateAssetEntry(entry);
			newGroup.setName(newCourse.getTitle(themeDisplay.getLocale(), true));
			newGroup.setDescription(summary);
			GroupLocalServiceUtil.updateGroup(newGroup);
			
		}catch(Exception e){
			if(log.isDebugEnabled())e.printStackTrace();
		}
		
		newCourse.setUserId(themeDisplay.getUserId());

		log.debug("-----------------------\n  From course: "+  group.getName());
		log.debug("  + to course: "+  newCourse.getTitle(Locale.getDefault()) +", GroupCreatedId: "+newCourse.getGroupCreatedId()+", GroupId: "+newCourse.getGroupId());
		cloneTraceStr += "\n New course\n........................." + groupId;
		cloneTraceStr += " Course: "+  newCourse.getTitle(Locale.getDefault()) +"\n GroupCreatedId: "+newCourse.getGroupCreatedId()+"\n GroupId: "+newCourse.getGroupId();
		cloneTraceStr += "\n.........................";
		
		/**
		 * METO AL USUARIO CREADOR DEL CURSO COMO PROFESOR
		 */
		if(includeTeacher){
			log.debug(includeTeacher);
			if (!GroupLocalServiceUtil.hasUserGroup(themeDisplay.getUserId(), newCourse.getGroupCreatedId())) {
					GroupLocalServiceUtil.addUserGroups(themeDisplay.getUserId(),	new long[] { newCourse.getGroupCreatedId() });
				//The application only send one mail at listener
				//User user = UserLocalServiceUtil.getUser(userId);
				//sendEmail(user, course);
				}
			LmsPrefs lmsPrefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());

			long teacherRoleId=RoleLocalServiceUtil.getRole(lmsPrefs.getEditorRole()).getRoleId();
				UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { themeDisplay.getUserId() }, newCourse.getGroupCreatedId(), teacherRoleId);
				
		}
		
		
		
		/*********************************************************/
		
		
		/*long days = 0;
		boolean isFirstModule = true;*/
		
		LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
		List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(groupId);
		HashMap<Long,Long> correlationActivities = new HashMap<Long, Long>();
		HashMap<Long,Long> correlationModules = new HashMap<Long, Long>();
		HashMap<Long,Long> modulesDependencesList = new  HashMap<Long, Long>();
		for(Module module:modules){
			/*
			if(isFirstModule){
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(startDate.getTime() - module.getStartDate().getTime());
				days = c.get(Calendar.DAY_OF_YEAR);
				
				if(c.get(Calendar.YEAR) - 1970 > 0){
					days += 365 * (c.get(Calendar.YEAR) - 1970);
				}
				isFirstModule = false;
				
				System.out.println(" Days to add: "+ days +" "+startDate+" "+module.getStartDate()+" "+c.getTime());
				cloneTraceStr += "\n Days to add: "+ days +" "+startDate+" "+module.getStartDate()+" "+c.getTime();
				cloneTraceStr += "\n\n";
			}
			*/
			Module newModule;

			try {
				newModule = ModuleLocalServiceUtil.createModule(CounterLocalServiceUtil.increment(Module.class.getName()));
				correlationModules.put(module.getModuleId(), newModule.getModuleId());
				
				if(module.getPrecedence()!=0){
					modulesDependencesList.put(module.getModuleId(),module.getPrecedence());
				}

				newModule.setTitle(module.getTitle());
				newModule.setDescription(module.getDescription());
				newModule.setGroupId(newCourse.getGroupId());
				
				newModule.setCompanyId(newCourse.getCompanyId());
				newModule.setGroupId(newCourse.getGroupCreatedId());
				newModule.setUserId(newCourse.getUserId());
				newModule.setOrdern(newModule.getModuleId());
				
				//Icono
				newModule.setIcon(module.getIcon());
				
				/*
				Calendar start = Calendar.getInstance();
				start.setTimeInMillis(module.getStartDate().getTime() + TimeUnit.MILLISECONDS.convert(days, TimeUnit.DAYS));
				Calendar stop = Calendar.getInstance();
				stop.setTimeInMillis(module.getEndDate().getTime() + TimeUnit.MILLISECONDS.convert(days, TimeUnit.DAYS));
				*/
				
				//System.out.println(" startDate: "+ start.getTime() +"   -> "+module.getStartDate());
				//System.out.println(" stopDate : "+ stop.getTime()  +"   -> "+module.getEndDate());
				
				newModule.setStartDate(startDate);
				newModule.setEndDate(endDate);
				
				newModule.setDescription(descriptionFilesClone(module.getDescription(),newCourse.getGroupCreatedId(), newModule.getModuleId(),themeDisplay.getUserId()));
				
				ModuleLocalServiceUtil.addModule(newModule);
				
				log.debug("\n    Module : " + module.getTitle(Locale.getDefault()) +"("+module.getModuleId()+")");
				log.debug("    + Module : " + newModule.getTitle(Locale.getDefault()) +"("+newModule.getModuleId()+")" );
				cloneTraceStr += "  Module: " + newModule.getTitle(Locale.getDefault()) +"("+newModule.getModuleId()+")";
				
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			List<LearningActivity> activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(module.getModuleId());
			HashMap<Long, Long> pending = new HashMap<Long, Long>();
			List<Long> evaluations = new ArrayList<Long>(); 
			for(LearningActivity activity:activities){
				
				LearningActivity newLearnActivity;
				LearningActivity nuevaLarn = null;
				try {
					newLearnActivity = LearningActivityLocalServiceUtil.createLearningActivity(CounterLocalServiceUtil.increment(LearningActivity.class.getName()));
					
					newLearnActivity.setTitle(activity.getTitle());
					newLearnActivity.setDescription(activity.getDescription());
					newLearnActivity.setTypeId(activity.getTypeId());
					//Cuando es tipo Evaluación no hay que llevarse el extracontent
					newLearnActivity.setExtracontent(activity.getExtracontent());
					
					
					newLearnActivity.setTries(activity.getTries());
					newLearnActivity.setPasspuntuation(activity.getPasspuntuation());
					newLearnActivity.setPriority(newLearnActivity.getActId());
					
					boolean actPending = false;
					if(activity.getPrecedence()>0){
						if(correlationActivities.get(activity.getPrecedence())==null){
							actPending = true;
						}else{
							newLearnActivity.setPrecedence(correlationActivities.get(activity.getPrecedence()));
						}
					}
					
					newLearnActivity.setWeightinmodule(activity.getWeightinmodule());
					
					newLearnActivity.setGroupId(newModule.getGroupId());
					newLearnActivity.setModuleId(newModule.getModuleId());
					
					newLearnActivity.setStartdate(startDate);
					newLearnActivity.setEnddate(endDate);
					
					/*
					if(activity.getStartdate() != null){
						Calendar start = Calendar.getInstance();
						start.setTimeInMillis(activity.getStartdate().getTime() + TimeUnit.MILLISECONDS.convert(days, TimeUnit.DAYS));
						newLearnActivity.setStartdate(start.getTime());
						//System.out.println(" startDate: "+ start.getTime() +"   -> "+activity.getStartdate());
					}
					
					if(activity.getEnddate() != null){
						Calendar stop = Calendar.getInstance();
						stop.setTimeInMillis(activity.getEnddate().getTime() + TimeUnit.MILLISECONDS.convert(days, TimeUnit.DAYS));
						newLearnActivity.setEnddate(stop.getTime());
						//System.out.println(" stopDate : "+ stop.getTime()  +"   -> "+activity.getEnddate());
					}
					*/

					newLearnActivity.setDescription(descriptionFilesClone(activity.getDescription(),newModule.getGroupId(), newLearnActivity.getActId(),themeDisplay.getUserId()));
		
					nuevaLarn=LearningActivityLocalServiceUtil.addLearningActivity(newLearnActivity,serviceContext);

					log.debug("      Learning Activity : " + activity.getTitle(Locale.getDefault())+ " ("+activity.getActId()+", " + LanguageUtil.get(Locale.getDefault(),learningActivityTypeRegistry.getLearningActivityType(activity.getTypeId()).getName())+")");
					log.debug("      + Learning Activity : " + nuevaLarn.getTitle(Locale.getDefault())+ " ("+nuevaLarn.getActId()+", " + LanguageUtil.get(Locale.getDefault(),learningActivityTypeRegistry.getLearningActivityType(nuevaLarn.getTypeId()).getName())+")");
					cloneTraceStr += "   Learning Activity: " + nuevaLarn.getTitle(Locale.getDefault())+ " ("+nuevaLarn.getActId()+", " + LanguageUtil.get(Locale.getDefault(),learningActivityTypeRegistry.getLearningActivityType(nuevaLarn.getTypeId()).getName())+")";
					
					cloneActivityFile(activity, nuevaLarn, themeDisplay.getUserId(), serviceContext);
					
					
					long actId = nuevaLarn.getActId();
					correlationActivities.put(activity.getActId(), actId);
					
					boolean visible = ResourcePermissionLocalServiceUtil.hasResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
							ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),siteMemberRole.getRoleId(), ActionKeys.VIEW);
					
					if(!visible) {
						ResourcePermissionLocalServiceUtil.setResourcePermissions(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
								ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),siteMemberRole.getRoleId(), new String[] {ActionKeys.VIEW});
					}
					
					if(nuevaLarn.getTypeId() == 8){
						evaluations.add(nuevaLarn.getActId());
					}
					
					
					if(actPending){
						pending.put(actId, activity.getPrecedence());
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

				List<TestQuestion> questions = TestQuestionLocalServiceUtil.getQuestions(activity.getActId());
				for(TestQuestion question:questions)
				{
					TestQuestion newTestQuestion;
					try {
						newTestQuestion = TestQuestionLocalServiceUtil.addQuestion(nuevaLarn.getActId(), question.getText(), question.getQuestionType());
						
						String newTestDescription = descriptionFilesClone(question.getText(),newModule.getGroupId(), newTestQuestion.getActId(),themeDisplay.getUserId());
						
						newTestQuestion.setText(newTestDescription);
						TestQuestionLocalServiceUtil.updateTestQuestion(newTestQuestion, true);
						
						log.debug("      Test question : " + question.getQuestionId() );
						log.debug("      + Test question : " + newTestQuestion.getQuestionId() );
						log.debug("      + Test question TEXT : " + newTestDescription );
						cloneTraceStr += "\n   Test question: " + newTestQuestion.getQuestionId();
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
					
					List<TestAnswer> answers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
					for(TestAnswer answer:answers){
						
						try {
							TestAnswer newTestAnswer = TestAnswerLocalServiceUtil.addTestAnswer(question.getQuestionId(), answer.getAnswer(), answer.getFeedbackCorrect(), answer.getFeedbacknocorrect(), answer.isIsCorrect());
							newTestAnswer.setActId(nuevaLarn.getActId());
							newTestAnswer.setQuestionId(newTestQuestion.getQuestionId());
							newTestAnswer.setAnswer(descriptionFilesClone(answer.getAnswer(),newModule.getGroupId(), newTestAnswer.getActId(),themeDisplay.getUserId()));
							
							TestAnswerLocalServiceUtil.updateTestAnswer(newTestAnswer, true);
							
							log.debug("        Test answer : " + answer.getAnswerId());
							log.debug("        + Test answer : " + newTestAnswer.getAnswerId());
							cloneTraceStr += "\n     Test answer: " + newTestAnswer.getAnswerId();
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}

				}
			}
			
			if(pending.size()>0){
				for(Long id : pending.keySet()){
					LearningActivity la = LearningActivityLocalServiceUtil.getLearningActivity(id);
					
					if(log.isDebugEnabled())log.debug(la);
					if(la!=null){
						Long idAsig = pending.get(id);

						if(log.isDebugEnabled())log.debug(idAsig);
						if(idAsig!=null){
							Long other = correlationActivities.get(idAsig);
							if(log.isDebugEnabled())log.debug(other);
							la.setPrecedence(other);
							
							LearningActivityLocalServiceUtil.updateLearningActivity(la);
						}
					}
				}
			}
			
			
			//Extra Content de las evaluaciones
			LearningActivity evaluationActivity;
			for(Long evaluation : evaluations){
				evaluationActivity = LearningActivityLocalServiceUtil.getLearningActivity(evaluation);
				try{
				if((evaluationActivity.getExtracontent()!=null)&&(evaluationActivity.getExtracontent().length()!=0)) {	
					//Element activitiesElement = SAXReaderUtil.read(evaluationActivity.getExtracontent()).getRootElement().element("activities");
					Document document =SAXReaderUtil.read(evaluationActivity.getExtracontent());
					if(log.isDebugEnabled())log.debug(" --- OLD EXTRA CONTENT "+document.formattedString());
					
					Element evaluationXML = document.getRootElement();
									
					if(log.isDebugEnabled())log.debug("--- OLD Evaluation Element "+evaluationXML.asXML());
					Element activitiesElement = evaluationXML.element("activities");
					if(log.isDebugEnabled())log.debug("--- OLD Activities Element "+activitiesElement.asXML());
					if(activitiesElement!=null){
						Iterator<Element> activitiesElementItr = activitiesElement.elementIterator();
						while(activitiesElementItr.hasNext()) {
							Element activity =activitiesElementItr.next();
							if(log.isDebugEnabled())log.debug("-- Activity "+ activity);
							if(("activity".equals(activity.getName()))&&(activity.attribute("id")!=null)&&(activity.attribute("id").getValue().length()!=0)){
								try{
									if(log.isDebugEnabled())log.debug("Old Value "+Long.parseLong(activity.attribute("id").getValue()));
									Long newValue = correlationActivities.get(Long.parseLong(activity.attribute("id").getValue()));
									if(log.isDebugEnabled())log.debug("New Value "+ newValue);
									activity.attribute("id").setValue(String.valueOf(newValue));
								}
								catch(NumberFormatException e){}
							}
							
							if(log.isDebugEnabled())log.debug("-- Activity Changed "+ activity.asXML());
						}		
						
						if(log.isDebugEnabled())log.debug("--- NEW Activities Element "+activitiesElement.asXML());
					}

					if(log.isDebugEnabled()){
						log.debug("--- NEW Evaluation Element "+evaluationXML.asXML());
						log.debug(" --- NEW EXTRA CONTENT "+document.formattedString());
					}
					evaluationActivity.setExtracontent(document.formattedString());
					LearningActivityLocalServiceUtil.updateLearningActivity(evaluationActivity);
				}
				}catch(Exception e){e.printStackTrace();}
			
			}
			
		}	
		
		
		//Dependencias de modulos
		log.debug("modulesDependencesList "+modulesDependencesList.keySet());
		for(Long id : modulesDependencesList.keySet()){
			//id del modulo actual
			Long moduleToBePrecededNew = correlationModules.get(id);
			Long modulePredecesorIdOld =  modulesDependencesList.get(id);
			Long modulePredecesorIdNew = correlationModules.get(modulePredecesorIdOld);
			Module moduleNew = ModuleLocalServiceUtil.getModule(moduleToBePrecededNew);
			moduleNew.setPrecedence(modulePredecesorIdNew);
			ModuleLocalServiceUtil.updateModule(moduleNew);
		}
		
		
		log.debug(" ENDS!");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		dateFormat.setTimeZone(themeDisplay.getTimeZone());
		
		String[] args = {newCourse.getTitle(themeDisplay.getLocale()), dateFormat.format(startDate), dateFormat.format(endDate)};
		
		sendNotification(LanguageUtil.get(themeDisplay.getLocale(),"courseadmin.clone.confirmation.title"), LanguageUtil.format(themeDisplay.getLocale(),"courseadmin.clone.confirmation.message", args), themeDisplay.getPortalURL()+"/web/"+newGroup.getFriendlyURL(), "Avisos", 1);
	
	}
	
	public String descriptionFilesClone(String description, long groupId, long actId, long userId){

		String newDescription = description;
		
		try {
			
			Document document = SAXReaderUtil.read(description.replace("&lt;","<").replace("&nbsp;",""));
			
			Element rootElement = document.getRootElement();
			
			if(rootElement.elements("Description").size()!=0){
				for (Element entryElement : rootElement.elements("Description")) {
					for (Element entryElementP : entryElement.elements("p")) {
						
						//Para las imagenes
						for (Element entryElementImg : entryElementP.elements("img")) {
							
							String src = entryElementImg.attributeValue("src");
							
							String []srcInfo = src.split("/");
							String fileUuid = "", fileGroupId ="";
							
							if(srcInfo.length >= 6  && srcInfo[1].compareTo("documents") == 0){
								fileUuid = srcInfo[srcInfo.length-1];
								fileGroupId = srcInfo[2];
								
								String []uuidInfo = fileUuid.split("\\?");
								String uuid="";
								if(srcInfo.length > 0){
									uuid=uuidInfo[0];
								}
								
								FileEntry file;
								try {
									file = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, Long.parseLong(fileGroupId));
									
									ServiceContext serviceContext = new ServiceContext();
									serviceContext.setScopeGroupId(groupId);
									serviceContext.setUserId(userId);
									serviceContext.setCompanyId(file.getCompanyId());
									serviceContext.setAddGroupPermissions(true);
									
									FileEntry newFile = cloneFileDescription(file, actId, file.getUserId(), serviceContext);
									
									newDescription = descriptionCloneFile(newDescription, file, newFile);
									
									log.debug("     + Description file image : " + file.getTitle() +" ("+file.getMimeType()+")");
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									//e.printStackTrace();
									log.error("* ERROR! Description file image : " + e.getMessage());
								}
							}
						}
						
						//Para los enlaces
						for (Element entryElementLink : entryElementP.elements("a")) {
							
							String href = entryElementLink.attributeValue("href");
							
							String []hrefInfo = href.split("/");
							String fileUuid = "", fileGroupId ="";
							
							if(hrefInfo.length >= 6 && hrefInfo[1].compareTo("documents") == 0){
								fileUuid = hrefInfo[hrefInfo.length-1];
								fileGroupId = hrefInfo[2];
								
								String []uuidInfo = fileUuid.split("\\?");
								String uuid="";
								if(hrefInfo.length > 0){
									uuid=uuidInfo[0];
								}
								
								FileEntry file;
								try {
									file = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, Long.parseLong(fileGroupId));
																			
									ServiceContext serviceContext = new ServiceContext();
									serviceContext.setScopeGroupId(groupId);
									serviceContext.setUserId(userId);
									serviceContext.setCompanyId(file.getCompanyId());
									serviceContext.setAddGroupPermissions(true);
									
									FileEntry newFile = cloneFileDescription(file, actId, file.getUserId(), serviceContext);
									
									newDescription = descriptionCloneFile(newDescription, file, newFile);
									
									log.debug("   + Description file pdf : " + file.getTitle() +" "+file.getFileEntryId() );
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									//e.printStackTrace();
									log.error("* ERROR! Description file pdf : " + e.getMessage());
								}
							}
							
							//Si en los enlaces tienen una imagen para hacer click.
							for (Element entryElementLinkImage : entryElementLink.elements("img")) {
								;//parseImage(entryElementLinkImage, element, context, moduleId);
							}
							
						}
					}
				}
			}else{
				if (rootElement.getQName().getName().equals("p")) {
					
					//Para las imagenes
					for (Element entryElementImg : rootElement.elements("img")) {
						
						String src = entryElementImg.attributeValue("src");
						
						String []srcInfo = src.split("/");
						String fileUuid = "", fileGroupId ="";
						
						if(srcInfo.length >= 6  && srcInfo[1].compareTo("documents") == 0){
							fileUuid = srcInfo[srcInfo.length-1];
							fileGroupId = srcInfo[2];
							
							String []uuidInfo = fileUuid.split("\\?");
							String uuid="";
							if(srcInfo.length > 0){
								uuid=uuidInfo[0];
							}
							
							FileEntry file;
							try {
								file = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, Long.parseLong(fileGroupId));
								
								ServiceContext serviceContext = new ServiceContext();
								serviceContext.setScopeGroupId(groupId);
								serviceContext.setUserId(userId);
								serviceContext.setCompanyId(file.getCompanyId());
								serviceContext.setAddGroupPermissions(true);
								
								FileEntry newFile = cloneFileDescription(file, actId, file.getUserId(), serviceContext);
								
								newDescription = descriptionCloneFile(newDescription, file, newFile);
								
								log.debug("     + Description file image : " + file.getTitle() +" ("+file.getMimeType()+")");
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
								log.error("* ERROR! Description file image : " + e.getMessage());
							}
						}
					}
					
					//Para los enlaces
					for (Element entryElementLink : rootElement.elements("a")) {
						
						String href = entryElementLink.attributeValue("href");
						
						String []hrefInfo = href.split("/");
						String fileUuid = "", fileGroupId ="";
						
						if(hrefInfo.length >= 6 && hrefInfo[1].compareTo("documents") == 0){
							fileUuid = hrefInfo[hrefInfo.length-1];
							fileGroupId = hrefInfo[2];
							
							String []uuidInfo = fileUuid.split("\\?");
							String uuid="";
							if(hrefInfo.length > 0){
								uuid=uuidInfo[0];
							}
							
							FileEntry file;
							try {
								file = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, Long.parseLong(fileGroupId));
																		
								ServiceContext serviceContext = new ServiceContext();
								serviceContext.setScopeGroupId(groupId);
								serviceContext.setUserId(userId);
								serviceContext.setCompanyId(file.getCompanyId());
								serviceContext.setAddGroupPermissions(true);
								
								FileEntry newFile = cloneFileDescription(file, actId, file.getUserId(), serviceContext);
								
								newDescription = descriptionCloneFile(newDescription, file, newFile);
								
								log.debug("   + Description file pdf : " + file.getTitle() +" "+file.getFileEntryId() );
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
								log.error("* ERROR! Description file pdf : " + e.getMessage());
							}
						}
						
						//Si en los enlaces tienen una imagen para hacer click.
						for (Element entryElementLinkImage : entryElementLink.elements("img")) {
							;//parseImage(entryElementLinkImage, element, context, moduleId);
						}
						
					}
				}
			}
			
			
			
		} catch (DocumentException de) {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("* ERROR! Document Exception : " + e.getMessage());
		}

		return newDescription;
		
	}
	
	private String descriptionCloneFile(String description, FileEntry oldFile, FileEntry newFile){
		String res = description;
		
		//Precondicion
		if(oldFile == null || newFile == null){
			return res;
		}
		
		//<img src="/documents/10808/0/GibbonIndexer.jpg/b24c4a8f-e65c-434a-ba36-3b3e10b21a8d?t=1376472516221"
		//<a  href="/documents/10808/10884/documento.pdf/32c193ed-16b3-4a83-93da-630501b72ee4">Documento</a></p>
		
		String target 		= "/documents/"+oldFile.getRepositoryId()+"/"+oldFile.getFolderId()+"/"+URLEncoder.encode(oldFile.getTitle())+"/"+oldFile.getUuid();
		String replacement 	= "/documents/"+newFile.getRepositoryId()+"/"+newFile.getFolderId()+"/"+URLEncoder.encode(newFile.getTitle())+"/"+newFile.getUuid();

		res = description.replace(target, replacement);
		
		//System.out.println("   res         : " + res );
		if(log.isDebugEnabled()){
			if(res.equals(description)){
				log.debug("   :: description         : " + description );
				log.debug("   :: target      : " + target );	
				log.debug("   :: replacement : " + replacement );
			}
		}
		
				
		String changed = (!res.equals(description))?" changed":" not changed";
		
		log.debug("   + Description file : " + newFile.getTitle() +" (" + newFile.getMimeType() + ")" + changed);
		
		return res;
	}
	
	private void cloneActivityFile(LearningActivity actOld, LearningActivity actNew, long userId, ServiceContext serviceContext){
					
		try {
			log.error("cloneActivityFile");
			String entryIdStr = "";
			if(actOld.getTypeId() == 2){
				entryIdStr = LearningActivityLocalServiceUtil.getExtraContentValue(actOld.getActId(), "document");
			}else if(actOld.getTypeId() == 7 || actOld.getTypeId() == 9){
				entryIdStr = LearningActivityLocalServiceUtil.getExtraContentValue(actOld.getActId(), "assetEntry");
			}
			
			
			
			if(!entryIdStr.equals("")){
				
				AssetEntry docAsset = AssetEntryLocalServiceUtil.getAssetEntry(Long.valueOf(entryIdStr));
				long entryId = 0;
				if(docAsset.getUrl()!=null && docAsset.getUrl().trim().length()>0){
					entryId = Long.valueOf(entryIdStr);
				}else{
					
					if(actNew.getTypeId() == 2){
						try{
							
							HashMap<String, String> map = LearningActivityLocalServiceUtil.convertXMLExtraContentToHashMap(actNew.getActId());
							Iterator <String> keysString =  map.keySet().iterator();
							//int index = 0;
							while (keysString.hasNext()){
								String key = keysString.next();
								
								if(!key.equals("video") && key.indexOf("document")!=-1){
									
									//index++;
									long assetEntryIdOld =  Long.parseLong(map.get(key));
									
									AssetEntry docAssetOLD= AssetEntryLocalServiceUtil.getAssetEntry(assetEntryIdOld);
									DLFileEntry oldFile=DLFileEntryLocalServiceUtil.getDLFileEntry(docAssetOLD.getClassPK());
									
									InputStream inputStream = DLFileEntryLocalServiceUtil.getFileAsStream(userId, oldFile.getFileVersion().getFileEntryId(), oldFile.getFileVersion().getVersion());
									byte[] byteArray = null;
									try {
										byteArray = IOUtils.toByteArray(inputStream);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
									
										long repositoryId = DLFolderConstants.getDataRepositoryId(actNew.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
										
										long dlMainFolderId = 0;
										 boolean dlMainFolderFound = false;
									        //Get main folder
									        try {
									        	//Get main folder
									        	Folder dlFolderMain = DLAppLocalServiceUtil.getFolder(repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,DOCUMENTLIBRARY_MAINFOLDER+actNew.getActId());
									        	dlMainFolderId = dlFolderMain.getFolderId();
									        	dlMainFolderFound = true;
									        	//Get portlet folder
									        } catch (Exception ex){
									        }
									        
											//Damos permisos al archivo para usuarios de comunidad.
											serviceContext.setAddGroupPermissions(true);
									        
									        //Create main folder if not exist
									        if(!dlMainFolderFound){
									        	Folder newDocumentMainFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, DOCUMENTLIBRARY_MAINFOLDER+actNew.getActId(), DOCUMENTLIBRARY_MAINFOLDER+actNew.getActId(), serviceContext);
									        	dlMainFolderFound = true;
									        	dlMainFolderId = newDocumentMainFolder.getFolderId();
									        }
										


										
										String ficheroExtStr = "";
										String extension[] = oldFile.getTitle().split("\\.");
										if(extension.length > 0){
											ficheroExtStr = "."+extension[extension.length-1];
										}
									
										FileEntry newFile = DLAppLocalServiceUtil.addFileEntry(
												userId, repositoryId , dlMainFolderId , oldFile.getTitle()+ficheroExtStr, oldFile.getMimeType(), 
											oldFile.getTitle(), StringPool.BLANK, StringPool.BLANK, byteArray , serviceContext ) ;

									
										//AssetEntry asset  = AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), newFile.getPrimaryKey());
										//System.out.println("      DLFileEntry newFile: "+newFile.getTitle()+", newFile PrimaryKey: "+newFile.getPrimaryKey()+", EntryId: "+asset.getEntryId());
										
										map.put(key, String.valueOf(AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), newFile.getPrimaryKey()).getEntryId()));
										
									
										
										Role siteMemberRole = RoleLocalServiceUtil.getRole(serviceContext.getCompanyId(), RoleConstants.SITE_MEMBER);
										ResourcePermissionLocalServiceUtil.setResourcePermissions(serviceContext.getCompanyId(), LearningActivity.class.getName(), 
												ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actNew.getActId()),siteMemberRole.getRoleId(), new String[] {ActionKeys.VIEW});

								}

										LearningActivityLocalServiceUtil.saveHashMapToXMLExtraContent(actNew.getActId(), map);
							
						}
						
						
					} catch (NoSuchEntryException nsee) {
						log.error(" asset not exits ");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						serviceContext.setAddGroupPermissions(serviceContext.isAddGroupPermissions());
					}
				}else{
					entryId = cloneFile(Long.valueOf(entryIdStr), actNew, userId, serviceContext);
				}
					
					

				}
				
				if(actNew.getTypeId() == 7){
					LearningActivityLocalServiceUtil.setExtraContentValue(actNew.getActId(), "assetEntry", String.valueOf(entryId));
				}else if(actNew.getTypeId() == 9){
					AssetEntry entry =  AssetEntryLocalServiceUtil.getAssetEntry(entryId);
					AssetEntry newEntry = AssetEntryLocalServiceUtil.createAssetEntry(CounterLocalServiceUtil.increment(AssetEntry.class.getName()));
					long newEntryId = newEntry.getEntryId();
					newEntry = (AssetEntry)entry.clone();
					newEntry.setEntryId(newEntryId);
					newEntry.setGroupId(actNew.getGroupId());
					AssetEntryLocalServiceUtil.updateAssetEntry(newEntry);
					log.error("NEW ENTRY ID "+ newEntryId);
					LearningActivityLocalServiceUtil.setExtraContentValue(actNew.getActId(), "assetEntry", String.valueOf(newEntryId));
					
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private long cloneFile(long entryId, LearningActivity actNew, long userId, ServiceContext serviceContext){
		
		long assetEntryId = 0;
		boolean addGroupPermissions = serviceContext.isAddGroupPermissions();
		
		try {
			log.debug("EntryId: "+entryId);
			AssetEntry docAsset = AssetEntryLocalServiceUtil.getAssetEntry(entryId);
			//docAsset.getUrl()!=""
			//DLFileEntryLocalServiceUtil.getDLFileEntry(fileEntryId)
			log.debug(docAsset.getClassPK());
			DLFileEntry docfile = DLFileEntryLocalServiceUtil.getDLFileEntry(docAsset.getClassPK());
			InputStream is = DLFileEntryLocalServiceUtil.getFileAsStream(userId, docfile.getFileEntryId(), docfile.getVersion());
			
			//Crear el folder
			DLFolder dlFolder = createDLFoldersForLearningActivity(userId, serviceContext.getScopeGroupId(), actNew.getActId(), actNew.getTitle(Locale.getDefault()), serviceContext);

			long repositoryId = DLFolderConstants.getDataRepositoryId(actNew.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			
			String ficheroStr = docfile.getTitle();	
			if(!docfile.getTitle().endsWith(docfile.getExtension())){
				ficheroStr = ficheroStr +"."+ docfile.getExtension();
			}
			
			serviceContext.setAddGroupPermissions(true);
			FileEntry newFile = DLAppLocalServiceUtil.addFileEntry(
					serviceContext.getUserId(), repositoryId , dlFolder.getFolderId() , ficheroStr, docfile.getMimeType(), 
					docfile.getTitle(), StringPool.BLANK, StringPool.BLANK, is, docfile.getSize() , serviceContext ) ;
			
			
			AssetEntry asset = AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), newFile.getPrimaryKey());
			
			log.debug(" asset : " + asset.getEntryId());
			
			assetEntryId = asset.getEntryId();
			
		} catch (NoSuchEntryException nsee) {
			log.error(" asset not exits ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			serviceContext.setAddGroupPermissions(addGroupPermissions);
		}
		
		return assetEntryId;
	}
	
	private FileEntry cloneFileDescription(FileEntry file, long actId, long userId, ServiceContext serviceContext){
		
		long folderId = 0;
		
		try {
			
			InputStream is = DLFileEntryLocalServiceUtil.getFileAsStream(userId, file.getFileEntryId(), file.getVersion());
			
			//Crear el folder
			DLFolder dlFolder = createDLFoldersForLearningActivity(userId, serviceContext.getScopeGroupId(), actId, String.valueOf(actId), serviceContext);
			folderId = dlFolder.getFolderId();
			
			//long repId = DLFolderConstants.getDataRepositoryId(file.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			long repositoryId = DLFolderConstants.getDataRepositoryId(serviceContext.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			
			String ficheroStr = file.getTitle();	
			if(!file.getTitle().endsWith(file.getExtension())){
				ficheroStr = ficheroStr +"."+ file.getExtension();
			}
			return  DLAppLocalServiceUtil.addFileEntry(
					serviceContext.getUserId(), repositoryId , folderId , ficheroStr, file.getMimeType(), 
					file.getTitle(), StringPool.BLANK, StringPool.BLANK, is, file.getSize() , serviceContext ) ;
			
			
		}catch(DuplicateFileException dfl){
			
			try{
				
				return DLAppLocalServiceUtil.getFileEntry(serviceContext.getScopeGroupId(), folderId, file.getTitle());
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*private long createDLFolders(Long userId,Long repositoryId,PortletRequest portletRequest) throws PortalException, SystemException{
		//Variables for folder ids
		Long dlMainFolderId = 0L;
		//Search for folder in Document Library
        boolean dlMainFolderFound = false;
        //Get main folder
        try {
        	//Get main folder
        	Folder dlFolderMain = DLAppLocalServiceUtil.getFolder(repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,DOCUMENTLIBRARY_MAINFOLDER);
        	dlMainFolderId = dlFolderMain.getFolderId();
        	dlMainFolderFound = true;
        	//Get portlet folder
        } catch (Exception ex){
        }
        
		ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFolder.class.getName(), portletRequest);
		//Damos permisos al archivo para usuarios de comunidad.
		serviceContext.setAddGroupPermissions(true);
        
        //Create main folder if not exist
        if(!dlMainFolderFound){
        	Folder newDocumentMainFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, DOCUMENTLIBRARY_MAINFOLDER, DOCUMENTLIBRARY_MAINFOLDER, serviceContext);
        	dlMainFolderId = newDocumentMainFolder.getFolderId();
        }//Create portlet folder if not exist
     
        return dlMainFolderId;
	}*/
	
	private DLFolder getMainDLFolder(Long userId, Long groupId, ServiceContext serviceContext) throws PortalException, SystemException{

		DLFolder mainFolder = null;
		boolean addGroupPermissions = serviceContext.isAddGroupPermissions();
		try {

			mainFolder = DLFolderLocalServiceUtil.getFolder(groupId,0,"ResourceUploads");

        } catch (Exception ex){

        	long repositoryId = DLFolderConstants.getDataRepositoryId(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
        	//mountPoint -> Si es carpeta raiz.
        	serviceContext.setAddGroupPermissions(true);
        	mainFolder = DLFolderLocalServiceUtil.addFolder(userId, groupId, repositoryId, false, 0, "ResourceUploads", "ResourceUploads", serviceContext);
        } finally {
        	serviceContext.setAddGroupPermissions(addGroupPermissions);
        }
  
        return mainFolder;
	}
	
	private DLFolder createDLFoldersForLearningActivity(Long userId, Long groupId, Long actId, String title, ServiceContext serviceContext) throws PortalException, SystemException{
		
		DLFolder newDLFolder = null;
		
		try {

			DLFolder dlMainFolder = getMainDLFolder(userId, groupId, serviceContext);
			
			//A partir de ahora, guardamos los ficheros en el "Resource Uploads".
			return dlMainFolder;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("* ERROR! createDLFoldersForLearningActivity: " + e.getMessage());
		}
		
    	return newDLFolder;
	}
	
	private void sendNotification(String title, String content, String url, String type, int priority){
		
		//ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);	
		SimpleDateFormat formatDay = new SimpleDateFormat("dd");
		formatDay.setTimeZone(themeDisplay.getTimeZone());
		SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
		formatMonth.setTimeZone(themeDisplay.getTimeZone());
		SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
		formatYear.setTimeZone(themeDisplay.getTimeZone());
		SimpleDateFormat formatHour = new SimpleDateFormat("HH");
		formatHour.setTimeZone(themeDisplay.getTimeZone());
		SimpleDateFormat formatMin = new SimpleDateFormat("mm");
		formatMin.setTimeZone(themeDisplay.getTimeZone());
		
		Date today=new Date(System.currentTimeMillis());
		
		int displayDateDay=Integer.parseInt(formatDay.format(today));
		int displayDateMonth=Integer.parseInt(formatMonth.format(today))-1;
		int displayDateYear=Integer.parseInt(formatYear.format(today));
		int displayDateHour=Integer.parseInt(formatHour.format(today));
		int displayDateMinute=Integer.parseInt(formatMin.format(today));
		
		int expirationDateDay=Integer.parseInt(formatDay.format(today));
		int expirationDateMonth=Integer.parseInt(formatMonth.format(today))-1;
		int expirationDateYear=Integer.parseInt(formatYear.format(today))+1;
		int expirationDateHour=Integer.parseInt(formatHour.format(today));
		int expirationDateMinute=Integer.parseInt(formatMin.format(today));

		long classNameId=PortalUtil.getClassNameId(User.class.getName());
		long classPK=themeDisplay.getUserId();

		AnnouncementsEntry ae;
		try {
			ae = AnnouncementsEntryServiceUtil.addEntry(
			                            themeDisplay.getPlid(), classNameId, classPK, title, content, url, type, 
			                            displayDateMonth, displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			                            expirationDateMonth, expirationDateDay, expirationDateYear, expirationDateHour, expirationDateMinute,
			                            priority, false);
			
			AnnouncementsFlagLocalServiceUtil.addFlag(classPK,ae.getEntryId(),AnnouncementsFlagConstants.UNREAD);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		                            
	}

}
