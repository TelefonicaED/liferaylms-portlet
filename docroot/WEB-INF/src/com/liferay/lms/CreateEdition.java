package com.liferay.lms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.liferay.lms.course.diploma.CourseDiploma;
import com.liferay.lms.course.diploma.CourseDiplomaRegistry;
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry;
import com.liferay.lms.model.AsynchronousProcessAudit;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseType;
import com.liferay.lms.model.CourseTypeFactory;
import com.liferay.lms.model.CourseTypeI;
import com.liferay.lms.service.AsynchronousProcessAuditLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseTypeLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.management.jmx.GetAttributesAction;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
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
			log.error(e);
			//e.printStackTrace();
			process = AsynchronousProcessAuditLocalServiceUtil.updateProcessStatus(process, new Date(), LmsConstant.STATUS_ERROR, e.getMessage());
			throw new DuplicateGroupException();
		}
		
		copyExpandos(newCourse, course , serviceContext);
	
		newCourse.setParentCourseId(parentCourseId);
		newCourse.setUserId(themeDisplay.getUserId());
	
		Group newGroup = GroupLocalServiceUtil.getGroup(newCourse.getGroupCreatedId());
		serviceContext.setScopeGroupId(newCourse.getGroupCreatedId());
		      
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
		CourseLocalServiceUtil.copyModulesAndActivities(themeDisplay.getUserId(), course, newCourse, true, true, serviceContext);
		
		CourseTypeFactoryRegistry courseTypeFactoryRegistry = new CourseTypeFactoryRegistry();
		AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),newCourse.getCourseId());
		if(entry.getClassTypeId() > 0){
			CourseType courseType = CourseTypeLocalServiceUtil.getCourseType(entry.getClassTypeId());
			if(courseType.getClassNameId() > 0){
				CourseTypeFactory courseTypeFactory = courseTypeFactoryRegistry.getCourseTypeFactory(courseType.getClassNameId());
				if(courseTypeFactory != null){
					CourseTypeI courseTypeI = courseTypeFactory.getCourseType(newCourse);
					
					courseTypeI.copyCourse(course, serviceContext);
				}
			}
		}
		
		CourseEvalRegistry registry = new CourseEvalRegistry();
		CourseEval courseEval = registry.getCourseEval(course.getCourseEvalId());
		courseEval.cloneCourseEval(course, newCourse);
		
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
	
	
	
	
}
