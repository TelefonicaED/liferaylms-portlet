package com.liferay.lms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.AsynchronousProcessAudit;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseCompetence;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.impl.ModuleImpl;
import com.liferay.lms.service.AsynchronousProcessAuditLocalServiceUtil;
import com.liferay.lms.service.CourseCompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
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
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
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
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.util.CourseCopyUtil;

public class CloneCourse extends CourseCopyUtil implements MessageListener {
	private static Log log = LogFactoryUtil.getLog(CloneCourse.class);


	long groupId;
	
	String newCourseName;
	
	ThemeDisplay themeDisplay;
	ServiceContext serviceContext;
	
	Date startDate;
	Date endDate;
	
	boolean visible;
	boolean includeTeacher;
	AsynchronousProcessAudit process = null;
	String statusMessage ="";
	boolean error= false;
	
	boolean cloneForum;
	
	public CloneCourse(long groupId, String newCourseName, ThemeDisplay themeDisplay, Date startDate, Date endDate, boolean cloneForum, ServiceContext serviceContext) {
		super();
		this.groupId = groupId;
		this.newCourseName = newCourseName;
		this.themeDisplay = themeDisplay;
		this.startDate = startDate;
		this.endDate = endDate;
		this.cloneForum = cloneForum;
		this.serviceContext = serviceContext;
	}
	

	public CloneCourse() {
	}
	
	
	
	@Override
	public void receive(Message message) throws MessageListenerException {
		
		try {
			
			long processId = message.getLong("asynchronousProcessAuditId");
			
			process = AsynchronousProcessAuditLocalServiceUtil.fetchAsynchronousProcessAudit(processId);
			process = AsynchronousProcessAuditLocalServiceUtil.updateProcessStatus(process, null, LmsConstant.STATUS_IN_PROGRESS, "");
			statusMessage ="";
			error = false;
			
			this.groupId	= message.getLong("groupId");
			this.newCourseName = message.getString("newCourseName");
			this.startDate 	= (Date)message.get("startDate");
			this.endDate 	= (Date)message.get("endDate");
			this.serviceContext = (ServiceContext)message.get("serviceContext");
			this.themeDisplay = (ThemeDisplay)message.get("themeDisplay");
			this.visible = message.getBoolean("visible");
			this.includeTeacher = message.getBoolean("includeTeacher");
			this.cloneForum = message.getBoolean("cloneForum");
			Role adminRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(),"Administrator");
			List<User> adminUsers = UserLocalServiceUtil.getRoleUsers(adminRole.getRoleId());
			 
			PrincipalThreadLocal.setName(adminUsers.get(0).getUserId());
			PermissionChecker permissionChecker =PermissionCheckerFactoryUtil.create(adminUsers.get(0), true);
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		
			doCloneCourse();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void doCloneCourse() throws Exception {
		log.debug("Course to clone\n........................." + groupId);
		Group group = GroupLocalServiceUtil.fetchGroup(groupId);
		Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(groupId);
		if(log.isDebugEnabled()){
			log.debug("Course to clone\n.........................");
			log.debug("  + groupId: "+groupId);
			log.debug("  + course: "+course.getTitle(themeDisplay.getLocale()));
		}
		
		Date today=new Date(System.currentTimeMillis());
		String courseTemplate = this.serviceContext.getRequest().getParameter("courseTemplate");
		long layoutSetPrototypeId = 0;
		if(courseTemplate.indexOf("&")>-1){
			layoutSetPrototypeId = Long.parseLong(courseTemplate.split("&")[1]);
		}else{
			layoutSetPrototypeId = Long.parseLong(courseTemplate);
		}		
		
		if(log.isDebugEnabled()){
			log.debug("  + layoutSetPrototypeId: "+layoutSetPrototypeId);	
		}
		
		
		try{
			log.debug("  + AssetCategoryIds: "+AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId()).getCategoryIds().toString());
			log.debug("  + AssetCategoryIds Service Context: "+serviceContext.getAssetCategoryIds());
			serviceContext.setAssetCategoryIds(AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId()).getCategoryIds());
			AssetEntryLocalServiceUtil.validate(course.getGroupCreatedId(), Course.class.getName(), serviceContext.getAssetCategoryIds(), serviceContext.getAssetTagNames());
		}catch(Exception e){
			serviceContext.setAssetCategoryIds(new long[]{});
			//serviceContext.setAssetTagNames(AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId()).getTags());
		}
		
		//Course newCourse = CourseLocalServiceUtil.addCourse(newCourseName, course.getDescription(), "", themeDisplay.getLocale() , today, startDate, endDate, serviceContext, course.getCalificationType());
		
		//when lmsprefs has more than one lmstemplate selected the addcourse above throws an error.
		
		
		int typeSite = GroupLocalServiceUtil.getGroup(course.getGroupCreatedId()).getType();
		Course newCourse = null;  
		String summary = null;
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
			newCourse.setExecutionStartDate(startDate);
			newCourse.setExecutionEndDate(endDate);
			
			process.setClassPK(newCourse.getCourseId());
			process = AsynchronousProcessAuditLocalServiceUtil.updateAsynchronousProcessAudit(process);
		} catch(DuplicateGroupException e){
			if(log.isDebugEnabled())e.printStackTrace();
			process = AsynchronousProcessAuditLocalServiceUtil.updateProcessStatus(process, new Date(), LmsConstant.STATUS_ERROR, e.getMessage());
			throw new DuplicateGroupException();
		}
	
		newCourse.setExpandoBridgeAttributes(serviceContext);
		
		newCourse.getExpandoBridge().setAttributes(course.getExpandoBridge().getAttributes());
	
		List<CourseCompetence> courseCompetences= CourseCompetenceLocalServiceUtil.findBycourseId(course.getCourseId(), false);
		for(CourseCompetence courseCompetence:courseCompetences)
		{
			long courseCompetenceId = CounterLocalServiceUtil.increment(CourseCompetence.class.getName());
			CourseCompetence cc = CourseCompetenceLocalServiceUtil.createCourseCompetence(courseCompetenceId);
			cc.setCourseId(newCourse.getCourseId());
			cc.setCompetenceId(courseCompetence.getCompetenceId());
			cc.setCachedModel(courseCompetence.getCondition());
			cc.setCondition(courseCompetence.getCondition());
			CourseCompetenceLocalServiceUtil.updateCourseCompetence(cc, true);
		}
		courseCompetences= CourseCompetenceLocalServiceUtil.findBycourseId(course.getCourseId(), true);
		CourseCompetence cc=null;
		for(CourseCompetence courseCompetence:courseCompetences){
			
			long courseCompetenceId = CounterLocalServiceUtil.increment(CourseCompetence.class.getName());
			cc = CourseCompetenceLocalServiceUtil.createCourseCompetence(courseCompetenceId);
			cc.setCourseId(newCourse.getCourseId());
			cc.setCompetenceId(courseCompetence.getCompetenceId());
			cc.setCachedModel(courseCompetence.getCondition());
			cc.setCondition(courseCompetence.getCondition());
			CourseCompetenceLocalServiceUtil.updateCourseCompetence(cc, true);
		}
	
		Group newGroup = GroupLocalServiceUtil.getGroup(newCourse.getGroupCreatedId());
		serviceContext.setScopeGroupId(newCourse.getGroupCreatedId());
		
		newCourse.setParentCourseId(course.getParentCourseId());
		
		
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
			error=true;
			statusMessage += e.getMessage() + "\n";
		}
		
		newCourse.setUserId(themeDisplay.getUserId());
		if(log.isDebugEnabled()){
			log.debug("-----------------------\n  From course: "+  group.getName());
			log.debug("  + to course: "+  newCourse.getTitle(Locale.getDefault()) +", GroupCreatedId: "+newCourse.getGroupCreatedId()+", GroupId: "+newCourse.getGroupId());
			
		}
		
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
		
		List<LearningActivity> activities = new ArrayList<LearningActivity>();
		HashMap<Long, Long> pending = null;
		List<Long> evaluations = new ArrayList<Long>(); 
		LearningActivity newLearnActivity=null;
		LearningActivity nuevaLarn = null;
		Module newModule=null;
		for(Module module:modules){
			

			try {
				newModule = new ModuleImpl();
				if(module.getPrecedence()!=0){
					modulesDependencesList.put(module.getModuleId(),module.getPrecedence());
				}
				newModule.setTitle(module.getTitle());
				newModule.setDescription(module.getDescription());
				newModule.setGroupId(newCourse.getGroupId());
				newModule.setCompanyId(newCourse.getCompanyId());
				newModule.setGroupId(newCourse.getGroupCreatedId());
				newModule.setUserId(newCourse.getUserId());
				
				newModule.setAllowedTime(module.getAllowedTime());
				newModule.setIcon(module.getIcon());
				newModule.setStartDate(startDate);
				newModule.setEndDate(endDate);
				newModule = ModuleLocalServiceUtil.addmodule(newModule);
				
				correlationModules.put(module.getModuleId(), newModule.getModuleId());
				newModule.setDescription(descriptionFilesClone(module.getDescription(),newCourse.getGroupCreatedId(), newModule.getModuleId(),themeDisplay.getUserId()));
				newModule.setOrdern(newModule.getModuleId());
				newModule.setUuid(module.getUuid());
				ModuleLocalServiceUtil.updateModule(newModule);
				if(log.isDebugEnabled()){
					log.debug("\n    Module : " + module.getTitle(Locale.getDefault()) +"("+module.getModuleId()+")");
					log.debug("    + Module : " + newModule.getTitle(Locale.getDefault()) +"("+newModule.getModuleId()+")" );
				}
				
				
			
				
			} catch (Exception e) {
				e.printStackTrace();
				error=true;
				statusMessage += e.getMessage() + "\n";
				continue;
			}
			
			activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(module.getModuleId());
			pending = new HashMap<Long, Long>();
			evaluations = new ArrayList<Long>(); 
			for(LearningActivity activity:activities){
				nuevaLarn = null;
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
					
			

					newLearnActivity.setDescription(descriptionFilesClone(activity.getDescription(),newModule.getGroupId(), newLearnActivity.getActId(),themeDisplay.getUserId()));
		
					nuevaLarn=LearningActivityLocalServiceUtil.addLearningActivity(newLearnActivity,serviceContext);
					if(log.isDebugEnabled()){
						log.debug("      Learning Activity : " + activity.getTitle(Locale.getDefault())+ " ("+activity.getActId()+", " + LanguageUtil.get(Locale.getDefault(),learningActivityTypeRegistry.getLearningActivityType(activity.getTypeId()).getName())+")");
						log.debug("      + Learning Activity : " + nuevaLarn.getTitle(Locale.getDefault())+ " ("+nuevaLarn.getActId()+", " + LanguageUtil.get(Locale.getDefault(),learningActivityTypeRegistry.getLearningActivityType(nuevaLarn.getTypeId()).getName())+")");
					}
					
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
					error=true;
					statusMessage += e.getMessage() + "\n";
					continue;
				}

				createTestQuestionsAndAnswers(activity, nuevaLarn, newModule, themeDisplay.getUserId());
				
				
			}
			LearningActivity la =null;
			Long other = null;
			Long idAsig = null;
			if(pending.size()>0){
				for(Long id : pending.keySet()){
					la = LearningActivityLocalServiceUtil.getLearningActivity(id);
					
					if(log.isDebugEnabled())log.debug(la);
					if(la!=null){
						idAsig = pending.get(id);
						if(log.isDebugEnabled())log.debug(idAsig);
						if(idAsig!=null){
							other = correlationActivities.get(idAsig);
							if(log.isDebugEnabled())log.debug(other);
							la.setPrecedence(other);
							LearningActivityLocalServiceUtil.updateLearningActivity(la);
						}
					}
				}
			}
			
			//Extra Content de las evaluaciones
			copyEvaluationExtraContent(evaluations, correlationActivities);
			
		}	
		
		
		//Dependencias de modulos
		log.debug("modulesDependencesList "+modulesDependencesList.keySet());
		Long moduleToBePrecededNew = null;
		Long modulePredecesorIdOld = null;
		Long modulePredecesorIdNew = null;
		for(Long id : modulesDependencesList.keySet()){
			//id del modulo actual
			moduleToBePrecededNew = correlationModules.get(id);
			modulePredecesorIdOld =  modulesDependencesList.get(id);
			modulePredecesorIdNew = correlationModules.get(modulePredecesorIdOld);
			Module moduleNew = ModuleLocalServiceUtil.getModule(moduleToBePrecededNew);
			moduleNew.setPrecedence(modulePredecesorIdNew);
			ModuleLocalServiceUtil.updateModule(moduleNew);
		}
		
		if(this.cloneForum){
			//-------------------------------------------
			//Categorias y subcategorias del foro
			//-------------------------------------
			List<MBCategory> listCategories = MBCategoryLocalServiceUtil.getCategories(groupId);
			//Si existen las categorias se clonan
			if(listCategories!=null && listCategories.size()>0){
				if(log.isDebugEnabled()){
					log.debug("------------------------Foro:: listCategories.size:: " + listCategories.size());
				}
				long newCourseGroupId = newCourse.getGroupCreatedId();//Para asociar las categorias creadas con el nuevo curso
				
				boolean resultCloneForo = cloneForo(newCourseGroupId, listCategories);
				if(log.isDebugEnabled()){
					log.debug("----------------------- Foro: CloneCat:: " + resultCloneForo);
				}
			}
			
			
			//---------------------------------------------------------------------
			
		}
		if(log.isDebugEnabled()){
			log.debug(" ENDS!");
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		dateFormat.setTimeZone(themeDisplay.getTimeZone());
		
		String[] args = {newCourse.getTitle(themeDisplay.getLocale()), dateFormat.format(startDate), dateFormat.format(endDate)};
		
		sendNotification(LanguageUtil.get(themeDisplay.getLocale(),"courseadmin.clone.confirmation.title"), LanguageUtil.format(themeDisplay.getLocale(),"courseadmin.clone.confirmation.message", args), themeDisplay.getPortalURL()+"/web/"+newGroup.getFriendlyURL(), "Avisos", 1);
		Date endDate = new Date();
		if(!error){
			AsynchronousProcessAuditLocalServiceUtil.updateProcessStatus(process, endDate, LmsConstant.STATUS_FINISH, "asynchronous-proccess-audit.status-ok");
		}else{
			AsynchronousProcessAuditLocalServiceUtil.updateProcessStatus(process, endDate, LmsConstant.STATUS_ERROR, statusMessage);
		}
	
	}
	
	public String descriptionFilesClone(String description, long groupId, long actId, long userId){

		String newDescription = description;
		
		try {
			
			Document document = SAXReaderUtil.read(description.replace("&lt;","<").replace("&nbsp;",""));
			
			Element rootElement = document.getRootElement();
			String []srcInfo =null;
			String fileUuid = null, fileGroupId = null;
			String src = null;
			String uuid = null;
			String []uuidInfo = null;
			FileEntry file =null;
			String href = null;
			ServiceContext serviceContext = null;
			FileEntry newFile = null;
			String []hrefInfo = null;
			if(rootElement.elements("Description").size()!=0){
				for (Element entryElement : rootElement.elements("Description")) {
					for (Element entryElementP : entryElement.elements("p")) {
						//Para las imagenes
						for (Element entryElementImg : entryElementP.elements("img")) {
							
							src = entryElementImg.attributeValue("src");
							
							srcInfo = src.split("/");
							fileUuid = "";
							fileGroupId ="";
							
							if(srcInfo.length >= 6  && srcInfo[1].compareTo("documents") == 0){
								fileUuid = srcInfo[srcInfo.length-1];
								fileGroupId = srcInfo[2];
								
								uuidInfo = fileUuid.split("\\?");
								uuid="";
								if(srcInfo.length > 0){
									uuid=uuidInfo[0];
								}
								
								
								try {
									file = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, Long.parseLong(fileGroupId));
									
									serviceContext = new ServiceContext();
									serviceContext.setScopeGroupId(groupId);
									serviceContext.setUserId(userId);
									serviceContext.setCompanyId(file.getCompanyId());
									serviceContext.setAddGroupPermissions(true);
									
									newFile = CourseCopyUtil.cloneFileDescription(file, actId, file.getUserId(), serviceContext);
									
									newDescription = descriptionCloneFile(newDescription, file, newFile);
									if(log.isDebugEnabled()){
										log.debug("     + Description file image : " + file.getTitle() +" ("+file.getMimeType()+")");
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									//e.printStackTrace();
									log.error("* ERROR! Description file image : " + e.getMessage());
								}
							}
						}
						
						//Para los enlaces
						for (Element entryElementLink : entryElementP.elements("a")) {
							
							href = entryElementLink.attributeValue("href");
							
							hrefInfo = href.split("/");
							fileUuid = "";
							fileGroupId ="";
							
							if(hrefInfo.length >= 6 && hrefInfo[1].compareTo("documents") == 0){
								fileUuid = hrefInfo[hrefInfo.length-1];
								fileGroupId = hrefInfo[2];
								
								uuidInfo = fileUuid.split("\\?");
								uuid="";
								if(hrefInfo.length > 0){
									uuid=uuidInfo[0];
								}
								
								try {
									file = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, Long.parseLong(fileGroupId));
																			
									serviceContext = new ServiceContext();
									serviceContext.setScopeGroupId(groupId);
									serviceContext.setUserId(userId);
									serviceContext.setCompanyId(file.getCompanyId());
									serviceContext.setAddGroupPermissions(true);
									
									newFile = CourseCopyUtil.cloneFileDescription(file, actId, file.getUserId(), serviceContext);
									
									newDescription = descriptionCloneFile(newDescription, file, newFile);
									
									log.debug("   + Description file pdf : " + file.getTitle() +" "+file.getFileEntryId() );
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									//e.printStackTrace();
									log.error("* ERROR! Description file pdf : " + e.getMessage());
								}
							}
							
							//Si en los enlaces tienen una imagen para hacer click.
							/*for (Element entryElementLinkImage : entryElementLink.elements("img")) {
								;//parseImage(entryElementLinkImage, element, context, moduleId);
							}*/
							
						}
					}
				}
			}else{
				if (rootElement.getQName().getName().equals("p")) {
					
					//Para las imagenes
					for (Element entryElementImg : rootElement.elements("img")) {
						
						src = entryElementImg.attributeValue("src");
						
						srcInfo = src.split("/");
						fileUuid = "";
						fileGroupId ="";
						
						if(srcInfo.length >= 6  && srcInfo[1].compareTo("documents") == 0){
							fileUuid = srcInfo[srcInfo.length-1];
							fileGroupId = srcInfo[2];
							
							uuidInfo = fileUuid.split("\\?");
							uuid="";
							if(srcInfo.length > 0){
								uuid=uuidInfo[0];
							}
							
							try{
								file = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, Long.parseLong(fileGroupId));
								
								serviceContext = new ServiceContext();
								serviceContext.setScopeGroupId(groupId);
								serviceContext.setUserId(userId);
								serviceContext.setCompanyId(file.getCompanyId());
								serviceContext.setAddGroupPermissions(true);
								
								newFile = CourseCopyUtil.cloneFileDescription(file, actId, file.getUserId(), serviceContext);
								
								newDescription = descriptionCloneFile(newDescription, file, newFile);
								if(log.isDebugEnabled()){
									log.debug("     + Description file image : " + file.getTitle() +" ("+file.getMimeType()+")");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
								log.error("* ERROR! Description file image : " + e.getMessage());
							}
						}
					}
					
					//Para los enlaces
					for (Element entryElementLink : rootElement.elements("a")) {
						
						href = entryElementLink.attributeValue("href");
						
						hrefInfo = href.split("/");
						fileUuid = "";
						fileGroupId ="";
						
						if(hrefInfo.length >= 6 && hrefInfo[1].compareTo("documents") == 0){
							fileUuid = hrefInfo[hrefInfo.length-1];
							fileGroupId = hrefInfo[2];
							
							uuidInfo = fileUuid.split("\\?");
							uuid="";
							if(hrefInfo.length > 0){
								uuid=uuidInfo[0];
							}
							
							try {
								file = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, Long.parseLong(fileGroupId));
								serviceContext = new ServiceContext();
								serviceContext.setScopeGroupId(groupId);
								serviceContext.setUserId(userId);
								serviceContext.setCompanyId(file.getCompanyId());
								serviceContext.setAddGroupPermissions(true);
								
								newFile = CourseCopyUtil.cloneFileDescription(file, actId, file.getUserId(), serviceContext);
								newDescription = descriptionCloneFile(newDescription, file, newFile);
								
								if(log.isDebugEnabled()){
									log.debug("   + Description file pdf : " + file.getTitle() +" "+file.getFileEntryId() );
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
								log.error("* ERROR! Description file pdf : " + e.getMessage());
							}
						}
						
						//Si en los enlaces tienen una imagen para hacer click.
						/*for (Element entryElementLinkImage : entryElementLink.elements("img")) {
							;//parseImage(entryElementLinkImage, element, context, moduleId);
						}*/
						
					}
				}
			}
			
			
			
		} catch (DocumentException de) {
			if(log.isDebugEnabled()){
				de.printStackTrace();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("* ERROR! Document Exception : " + e.getMessage());
		}

		return newDescription;
		
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
	
	/**
	 * Clona las categorias/subcategorias/subsubcategorias... del foro
	 * @param newCourseGroupId: groupId del curso clonado
	 * @param listCategories: lista de categorias del foro del curso
	 * 
	 * @return: boolean = true -> Se realiza correctamente
	 * 			boolean = false -> Se ha producido algun error durante el proceso
	 */
	private boolean cloneForo(long newCourseGroupId, List<MBCategory> listCategories){
		
		boolean resultCloneForo = true;
		
		List<MBCategory> listParentCat = new ArrayList<MBCategory>();
		List<MBCategory> listSubCat = new ArrayList<MBCategory>();
		
		long parentCategoryId = 0;
		
		for(MBCategory category:listCategories){
			if(0 == category.getParentCategoryId()) listParentCat.add(category);
			else listSubCat.add(category);
		}
		
		resultCloneForo = subCategories(parentCategoryId, newCourseGroupId, listParentCat, listSubCat);
		
		return resultCloneForo;
	}
	
	/**
	 * 
	 * Funcion recursiva que clona las categorias del foro y busca si cada categoria tiene una subcategoria para
	 * volver a realizar la misma operacion
	 * 
	 * @param parentCategoryId: Id de la categoria padre (la clonada)
	 * @param newCourseGroupId: Id del curso clonado
	 * @param listParentCat
	 * @param listSubCat
	 * @return: boolean = true -> Si se realiza el proceso correctamente
	 * 			boolean = false -> Si se produce algun error durante el proceso
	 */
	private boolean subCategories(long parentCategoryId, long newCourseGroupId, List<MBCategory> listParentCat, List<MBCategory> listSubCat){
		
		if(log.isDebugEnabled()){
			log.debug("-----------Clone Foro: listParentCat.size:: [" + listParentCat.size() + "], listSubCat.size :: [" + listSubCat.size() + "]");
		}
		boolean result = true;
		long newParentCategoryId;
		MBCategory newCourseCategory = null;
		List<MBCategory> listSubSubCat= new ArrayList<MBCategory>();
		List<MBCategory> listParentSubCat= new ArrayList<MBCategory>();
		
		for(MBCategory category:listParentCat){
			
			newCourseCategory = createNewCategory(newCourseGroupId, category, parentCategoryId);
			if (newCourseCategory==null) return false;
			
			newParentCategoryId = newCourseCategory.getCategoryId();
			
			if(listSubCat.size()>0) {
				
				listParentSubCat = new ArrayList<MBCategory>();
				listSubSubCat = new ArrayList<MBCategory>();
				
				for(MBCategory subCategory:listSubCat) {
					
					if(category.getCategoryId() == subCategory.getParentCategoryId()) listParentSubCat.add(subCategory);
					else listSubSubCat.add(subCategory);
		
				}
				
				if(listParentSubCat.size()>0){//Si encuentro subcategorias de esta categoria vuelvo a llamar a esta misma funcion
					result = subCategories(newParentCategoryId, newCourseGroupId, listParentSubCat, listSubSubCat);
					if(!result) return result;
				}
			}
		}
		
		return result;
		
	}
	
	/**
	 * 
	 * Crea una categoria del foro
	 * 
	 * @param newCourseGroupId: Id del curso clonado
	 * @param category: Datos de la categoria que se quiere clonar
	 * @param parentCategoryId: Id de la categoria de la que depende esta categoria (parentCategoryId=0 si no depende de ninguna categoria, y si tiene 
	 * 			dependencia, parentCategoryId = categoryId de la categoria de la que depende)
	 * @return 	null -> en caso de que se produzca algun error
	 * 			Objeto MBCategory creado en caso de que la operacion se realice correctamente
	 */
	private MBCategory createNewCategory(long newCourseGroupId, MBCategory category, long parentCategoryId) {
		
		log.debug("-----------Clone Foro: createNewCategory:: newCourseGroupId:: [" + newCourseGroupId + "], category:: [" + category.getName() + "], parentCatId:: [" + parentCategoryId + "]");
		MBCategory newCourseCategory = null;
		
		try {
			newCourseCategory = MBCategoryLocalServiceUtil.createMBCategory(CounterLocalServiceUtil.increment(MBCategory.class.getName()));
			newCourseCategory.setGroupId(newCourseGroupId);
			newCourseCategory.setCompanyId(category.getCompanyId());
			newCourseCategory.setName(category.getName());
			newCourseCategory.setDescription(category.getDescription());
			newCourseCategory.setCreateDate(Calendar.getInstance().getTime());
			newCourseCategory.setModifiedDate(Calendar.getInstance().getTime());
			newCourseCategory.setDisplayStyle(category.getDisplayStyle());
			newCourseCategory.setUserId(themeDisplay.getUserId());
			newCourseCategory.setUserName(themeDisplay.getUser().getFullName());
			newCourseCategory.setParentCategoryId(parentCategoryId);
			newCourseCategory = MBCategoryLocalServiceUtil.addMBCategory(newCourseCategory);
			
			// Copiar permisos de la categoria antigua en la nueva
			List<ResourcePermission> resourcePermissionList = new ArrayList<ResourcePermission>();
			ResourcePermission rpNew = null;
			long resourcePermissionId = 0;
    		int [] scopeIds = ResourceConstants.SCOPES;
    		for(int scope : scopeIds) {
    			resourcePermissionList = ResourcePermissionLocalServiceUtil.getResourcePermissions(category.getCompanyId(), MBCategory.class.getName(), scope, String.valueOf(category.getPrimaryKey()));
        		for(ResourcePermission resourcePermission : resourcePermissionList) {
        			resourcePermissionId = CounterLocalServiceUtil.increment(ResourcePermission.class.getName());
        			rpNew = ResourcePermissionLocalServiceUtil.createResourcePermission(resourcePermissionId);
        			rpNew.setActionIds(resourcePermission.getActionIds());
        			rpNew.setCompanyId(resourcePermission.getCompanyId());
        			rpNew.setName(resourcePermission.getName());
        			rpNew.setRoleId(resourcePermission.getRoleId());
        			rpNew.setScope(resourcePermission.getScope());
        			rpNew.setPrimKey(String.valueOf(newCourseCategory.getCategoryId()));
        			rpNew.setOwnerId(resourcePermission.getOwnerId());
        			rpNew = ResourcePermissionLocalServiceUtil.updateResourcePermission(rpNew);
        		}
    		}
			
			return newCourseCategory;
			
		} catch (SystemException e) {
			log.error(e.getMessage());
			return null;
		}
	}

}
