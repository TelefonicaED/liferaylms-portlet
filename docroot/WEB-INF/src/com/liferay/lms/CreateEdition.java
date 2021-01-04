package com.liferay.lms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.course.diploma.CourseDiploma;
import com.liferay.lms.course.diploma.CourseDiplomaRegistry;
import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.AsynchronousProcessAudit;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.impl.ModuleImpl;
import com.liferay.lms.service.AsynchronousProcessAuditLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
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
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.util.CourseCopyUtil;


public class CreateEdition extends CourseCopyUtil implements MessageListener {
	private static Log log = LogFactoryUtil.getLog(CreateEdition.class);

	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads";
	private String newEditionName;
	private ThemeDisplay themeDisplay;
	private ServiceContext serviceContext;
	private Date startDate;
	private Date endDate;
	private boolean isLinked;
	private long parentCourseId;
	private String editionFriendlyURL;
	private long editionLayoutId;
	private Date startExecutionDate;
	private Date endExecutionDate;
	
	AsynchronousProcessAudit process = null;
	String statusMessage ="";
	boolean error= false;
	
	public CreateEdition(long groupId, String newEditionName, ThemeDisplay themeDisplay, Date startDate, Date endDate, long parentCourseId, ServiceContext serviceContext) {
		super();
		this.newEditionName = newEditionName;
		this.parentCourseId = parentCourseId;
		this.themeDisplay = themeDisplay;
		this.startDate = startDate;
		this.endDate = endDate;
		this.serviceContext = serviceContext;
	}
	

	public CreateEdition() {
	}
	
	
	
	@Override
	public void receive(Message message) throws MessageListenerException {
		
		try {
			long processId = message.getLong("asynchronousProcessAuditId");
			
			process = AsynchronousProcessAuditLocalServiceUtil.fetchAsynchronousProcessAudit(processId);
			process = AsynchronousProcessAuditLocalServiceUtil.updateProcessStatus(process, null, LmsConstant.STATUS_IN_PROGRESS, "");
			statusMessage ="";
			error = false;
			
			this.newEditionName = message.getString("newEditionName");
			this.startDate 	= (Date)message.get("startDate");
			this.endDate 	= (Date)message.get("endDate");
			this.serviceContext = (ServiceContext)message.get("serviceContext");
			this.themeDisplay = (ThemeDisplay)message.get("themeDisplay");
			this.parentCourseId = (Long)message.get("parentCourseId");
			this.isLinked = (Boolean)message.get("isLinked");
			this.startExecutionDate = (Date) message.get("startExecutionDate");
			this.endExecutionDate = (Date) message.get("endExecutionDate");
			this.editionFriendlyURL = (String)message.get("editionFriendlyURL");
			this.editionLayoutId = (Long)message.get("editionLayoutId");
			log.debug("Parent Course Id: "+parentCourseId);
			Role adminRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(),"Administrator");
			List<User> adminUsers = UserLocalServiceUtil.getRoleUsers(adminRole.getRoleId());
			 
			PrincipalThreadLocal.setName(adminUsers.get(0).getUserId());
			PermissionChecker permissionChecker =PermissionCheckerFactoryUtil.create(adminUsers.get(0));
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		
			doCreateEdition();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doCreateEdition() throws Exception {
		
		Course course = CourseLocalServiceUtil.fetchCourse(parentCourseId);
	
		Group group = GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());

		if(log.isDebugEnabled()){
			log.debug(" Course to create edition\n........................." + parentCourseId);
			log.debug(" New edition name\n........................." + newEditionName);
			log.debug("  + Parent course: "+course.getTitle(themeDisplay.getLocale()));
		 
		}
	
		Date today=new Date(System.currentTimeMillis());

		//Plantilla
		
		if(editionLayoutId<=0){
			editionLayoutId = group.getPublicLayoutSet().getLayoutSetPrototypeId();
		}
		if(log.isDebugEnabled()){
			log.debug("  + layoutSetPrototypeId: "+editionLayoutId);
		}

		//Tags y categorias
		try{
			AssetEntryLocalServiceUtil.validate(course.getGroupCreatedId(), Course.class.getName(), serviceContext.getAssetCategoryIds(), serviceContext.getAssetTagNames());
			serviceContext.setAssetCategoryIds(AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId()).getCategoryIds());
			serviceContext.setAssetTagNames(AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId()).getTagNames());
			
			if(log.isDebugEnabled()){
				log.debug("  + AssetCategoryIds: "+AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId()).getCategoryIds().toString());
				log.debug("  + AssetTagNames: "+AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId()).getTagNames());
				log.debug("  + AssetTagNames Service Context: "+serviceContext.getAssetTagNames());
				
			}
		}catch(Exception e){
			serviceContext.setAssetCategoryIds(new long[]{});
		}
		//Tipo del grupo
		int typeSite = group.getType();
		
		//Creamos el nuevo curso para la ediciÃ³n 
		Course newCourse = null;  
		String summary = null;
		long courseTypeId = 0;
		
		try{
			AssetEntry entry = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId());
			summary = entry.getSummary(themeDisplay.getLocale());
			courseTypeId = entry.getClassTypeId();			
			newCourse = CourseLocalServiceUtil.addCourse(newEditionName, course.getDescription(themeDisplay.getLocale()),
					summary, editionFriendlyURL, themeDisplay.getLocale(), today, startDate, endDate, editionLayoutId, typeSite, serviceContext, 
					course.getCalificationType(), (int)course.getMaxusers(),true);
			
			newCourse.setGroupId(themeDisplay.getScopeGroupId());
			newCourse.setTitle(newEditionName, themeDisplay.getLocale());
			newCourse.setWelcome(course.getWelcome());
			newCourse.setWelcomeMsg(course.getWelcomeMsg());
			newCourse.setWelcomeSubject(course.getWelcomeSubject());
			newCourse.setDeniedInscription(course.isDeniedInscription());
			newCourse.setDeniedInscriptionSubject(course.getDeniedInscriptionSubject());
			newCourse.setDeniedInscriptionMsg(course.getDeniedInscriptionMsg());
			newCourse.setGoodbye(course.getGoodbye());
			newCourse.setExecutionStartDate(startExecutionDate);
			newCourse.setExecutionEndDate(endExecutionDate);
			newCourse.setGoodbyeMsg(course.getGoodbyeMsg());
			newCourse.setGoodbyeSubject(course.getGoodbyeSubject());
			newCourse.setCourseEvalId(course.getCourseEvalId());
			newCourse.setIsLinked(isLinked);
			
			StringBuilder extraContent = new StringBuilder();
			extraContent.append(LanguageUtil.get(themeDisplay.getLocale(), "course-admin.parent-course"))
                .append(StringPool.COLON).append(StringPool.SPACE)
                .append(course.getTitle(themeDisplay.getLocale()));		
            
            extraContent.append("<br>").append(LanguageUtil.get(themeDisplay.getLocale(), "courseadmin.edition"))
                .append(StringPool.COLON).append(StringPool.SPACE)
                .append(newCourse.getTitle(themeDisplay.getLocale()));
            
            
            JSONObject json =JSONFactoryUtil.createJSONObject();            
            json.put("data", extraContent.toString());
            
            process.setExtraContent(json.toString());
			process.setClassPK(newCourse.getCourseId());
			process = AsynchronousProcessAuditLocalServiceUtil.updateAsynchronousProcessAudit(process);
			
		} catch(DuplicateGroupException e){
			e.printStackTrace();
			process = AsynchronousProcessAuditLocalServiceUtil.updateProcessStatus(process, new Date(), LmsConstant.STATUS_ERROR, e.getMessage());
			throw new DuplicateGroupException();
		}
		newCourse.setExpandoBridgeAttributes(serviceContext);
		newCourse.getExpandoBridge().setAttributes(course.getExpandoBridge().getAttributes());
		newCourse.setParentCourseId(parentCourseId);
		newCourse.setUserId(themeDisplay.getUserId());
	
		Group newGroup = GroupLocalServiceUtil.getGroup(newCourse.getGroupCreatedId());
		serviceContext.setScopeGroupId(newCourse.getGroupCreatedId());
		      
		Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
		newCourse.setIcon(course.getIcon());
		
		try{
			newCourse = CourseLocalServiceUtil.modCourse(newCourse, courseTypeId, serviceContext);
			AssetEntry newEntry = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),newCourse.getCourseId());
			newEntry.setVisible(false);
			newEntry.setSummary(summary);
			newEntry.setClassTypeId(courseTypeId);
			AssetEntryLocalServiceUtil.updateAssetEntry(newEntry);
			newGroup.setType(typeSite); 
			GroupLocalServiceUtil.updateGroup(newGroup);
		}catch(Exception e){
			e.printStackTrace();
			error=true;
			statusMessage += e.getMessage() + "\n";
		}
		
		//Update especific content of diploma (if exists)
		CourseDiplomaRegistry cdr = new CourseDiplomaRegistry();
		if(cdr!=null){
			CourseDiploma courseDiploma = cdr.getCourseDiploma();
			if(courseDiploma!=null){
				
				String courseDiplomaError = courseDiploma.copyCourseDiploma(course.getCourseId(), newCourse.getCourseId());
				log.debug("****CourseDiplomaError:"+courseDiplomaError);
				
				if(Validator.isNotNull(courseDiplomaError)){
					statusMessage += courseDiplomaError + "\n";
					error = true;
				}
			}
		}

		if(log.isDebugEnabled()){
			log.debug("-----------------------\n  Creating edition from: "+  group.getName());
			log.debug("  + editionName : "+  newCourse.getTitle(Locale.getDefault()) +", GroupCreatedId: "+newCourse.getGroupCreatedId()+", GroupId: "+newCourse.getGroupId());
		}
		
		/*********************************************************/
		
		//Create modules and activities
		createModulesAndActivities(newCourse, siteMemberRole, group.getGroupId());
		Date endDate = new Date();
		if(!error){
			AsynchronousProcessAuditLocalServiceUtil.updateProcessStatus(process, endDate, LmsConstant.STATUS_FINISH, "asynchronous-proccess-audit.status-ok");
		}else{
			AsynchronousProcessAuditLocalServiceUtil.updateProcessStatus(process, endDate, LmsConstant.STATUS_ERROR, statusMessage);
		}
		
		//Create Tags and Categories
		
		log.debug("Enviando mensaje liferay/lms/createEditionPostAction con editionCourseId "+newCourse.getCourseId() + " y parentCourseId "+course.getCourseId());
		Message postActionMessage=new Message();
		postActionMessage.put("parentCourseId", course.getCourseId());
		postActionMessage.put("editionCourseId", newCourse.getCourseId());
		MessageBusUtil.sendMessage("liferay/lms/createEditionPostAction", postActionMessage);					
		log.debug(" ENDS!");
	}
	
	
	private void createModulesAndActivities(Course newCourse, Role siteMemberRole, long groupId) throws SystemException{
		
		LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
		List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(groupId);
		
		HashMap<Long,Long> correlationModules = new HashMap<Long, Long>();
		HashMap<Long,Long> modulesDependencesList = new  HashMap<Long, Long>();
		Module newModule=null;
		HashMap<Long, Long> pending = new HashMap<Long, Long>();
		HashMap<Long,Long> correlationActivities = new HashMap<Long, Long>();
		List<LearningActivity> activities =  new ArrayList<LearningActivity>();
		LearningActivity newLearnActivity=null;
		LearningActivity nuevaLarn = null;
		List<Long> evaluations = new ArrayList<Long>(); 
		
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
				if(module.getStartDate() != null)
					newModule.setStartDate(startExecutionDate);
				if(module.getEndDate() != null)
					newModule.setEndDate(endExecutionDate);
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
				
				//Copiar la clasificaciÃ³n de los mÃ³dulos
				AssetEntry entryModule = AssetEntryLocalServiceUtil.fetchEntry(Module.class.getName(), module.getModuleId());
				if(Validator.isNotNull(entryModule))
					AssetEntryLocalServiceUtil.updateEntry(newModule.getUserId(), newModule.getGroupId(), Module.class.getName(), 
						newModule.getModuleId(), entryModule.getCategoryIds(), entryModule.getTagNames());
				
				createLearningActivities(module, newModule, siteMemberRole, learningActivityTypeRegistry, pending, correlationActivities, activities, newLearnActivity, nuevaLarn, evaluations);
				
				
			} catch (Exception e) {
				e.printStackTrace();
				error=true;
				statusMessage += e.getMessage() + "\n";
				continue;
			}
			
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
			Module moduleNew = ModuleLocalServiceUtil.fetchModule(moduleToBePrecededNew);
			if(moduleNew!=null){
				moduleNew.setPrecedence(modulePredecesorIdNew);
				ModuleLocalServiceUtil.updateModule(moduleNew);	
			}
		}
	}
	
	
	private void createLearningActivities(Module parentModule, Module newModule, Role siteMemberRole, LearningActivityTypeRegistry learningActivityTypeRegistry,
			HashMap<Long, Long> pending, HashMap<Long,Long> correlationActivities, List<LearningActivity> activities, LearningActivity newLearnActivity, LearningActivity nuevaLarn, List<Long> evaluations) throws SystemException, PortalException{
		
		boolean canBeLinked = false;
		activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(parentModule.getModuleId());
		for(LearningActivity activity:activities){
			try {				
				canBeLinked = learningActivityTypeRegistry.getLearningActivityType(activity.getTypeId()).canBeLinked();
				//Fill common columns
				newLearnActivity = LearningActivityLocalServiceUtil.createLearningActivity(CounterLocalServiceUtil.increment(LearningActivity.class.getName()));
				newLearnActivity.setUuid(activity.getUuid());
				newLearnActivity.setTypeId(activity.getTypeId());
				newLearnActivity.setFeedbackCorrect(activity.getFeedbackCorrect());
				newLearnActivity.setFeedbackNoCorrect(activity.getFeedbackNoCorrect());
				newLearnActivity.setPriority(newLearnActivity.getActId());
				newLearnActivity.setWeightinmodule(activity.getWeightinmodule());
				newLearnActivity.setGroupId(newModule.getGroupId());
				newLearnActivity.setModuleId(newModule.getModuleId());
				if(activity.getStartdate() != null)
					newLearnActivity.setStartdate(startExecutionDate);
				if(activity.getEnddate() != null)
					newLearnActivity.setEnddate(endExecutionDate);
				boolean actPending = false;
				if(activity.getPrecedence()>0){
					if(correlationActivities.get(activity.getPrecedence())==null){
						actPending = true;
					}else{
						newLearnActivity.setPrecedence(correlationActivities.get(activity.getPrecedence()));
					}
				}
				
			
				//TODO Cuando estÃ© preparado la parte de linkar no habrÃ¡ que copiar todo
				//else{
					newLearnActivity.setExtracontent(activity.getExtracontent());
					newLearnActivity.setTitle(activity.getTitle());
					newLearnActivity.setDescription(activity.getDescription());
					newLearnActivity.setTries(activity.getTries());
					newLearnActivity.setPasspuntuation(activity.getPasspuntuation());
					newLearnActivity.setDescription(descriptionFilesClone(activity.getDescription(),newModule.getGroupId(), newLearnActivity.getActId(),themeDisplay.getUserId()));
				//}
				
				ServiceContext larnServiceContext = serviceContext;
	


				AssetEntry entryActivity = AssetEntryLocalServiceUtil.fetchEntry(LearningActivity.class.getName(), activity.getActId());
				if(Validator.isNotNull(entryActivity)){
					
					larnServiceContext.setAssetCategoryIds(entryActivity.getCategoryIds());
					larnServiceContext.setAssetTagNames(entryActivity.getTagNames());
					larnServiceContext.setExpandoBridgeAttributes(activity.getExpandoBridge().getAttributes());
			
				}
				
				nuevaLarn=LearningActivityLocalServiceUtil.addLearningActivity(newLearnActivity,larnServiceContext);
				nuevaLarn.setExpandoBridgeAttributes(larnServiceContext);
				nuevaLarn.getExpandoBridge().setAttributes(activity.getExpandoBridge().getAttributes());
				
				if(canBeLinked){
					nuevaLarn.setLinkedActivityId(activity.getActId());
				}
				nuevaLarn.setUuid(activity.getUuid());
				LearningActivityLocalServiceUtil.updateLearningActivity(nuevaLarn);
				
				log.debug("ACTIVITY EXTRA CONTENT BEFORE "+ newLearnActivity.getExtracontent());
				
				log.debug("Learning Activity : " + activity.getTitle(Locale.getDefault())+ " ("+activity.getActId()+", " + LanguageUtil.get(Locale.getDefault(),learningActivityTypeRegistry.getLearningActivityType(activity.getTypeId()).getName())+")");
				log.debug("+Learning Activity : " + nuevaLarn.getTitle(Locale.getDefault())+ " ("+nuevaLarn.getActId()+", " + LanguageUtil.get(Locale.getDefault(),learningActivityTypeRegistry.getLearningActivityType(nuevaLarn.getTypeId()).getName())+") Can Be Linked: "+canBeLinked);
				
				

				cloneActivityFile(activity, nuevaLarn, themeDisplay.getUserId(), serviceContext);
				
				
				long actId = nuevaLarn.getActId();
				correlationActivities.put(activity.getActId(), actId);
				boolean visibleParent = ResourcePermissionLocalServiceUtil.hasResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(activity.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW);			
				
				if(visibleParent){
					ResourcePermissionLocalServiceUtil.setResourcePermissions(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
							ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),siteMemberRole.getRoleId(), new String[] {ActionKeys.VIEW});
					
				}else{
					ResourcePermissionLocalServiceUtil.removeResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
							ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),siteMemberRole.getRoleId(), ActionKeys.VIEW);
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

			
			//TODO Descomentar cuando estÃ© implementado las actividades linkadas.
			//if(!canBeLinked){
			createTestQuestionsAndAnswers(activity, nuevaLarn, newModule, themeDisplay.getUserId());
		
		
			LearningActivityType lat = new LearningActivityTypeRegistry().getLearningActivityType(activity.getTypeId());
			lat.copyActivity(activity, nuevaLarn, serviceContext);
		}
		
	
		 
		
		//Set the precedences
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
		copyEvaluationExtraContent(evaluations, correlationActivities);
	}	
}
