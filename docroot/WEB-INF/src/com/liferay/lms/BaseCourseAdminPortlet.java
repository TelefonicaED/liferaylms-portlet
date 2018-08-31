package com.liferay.lms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.course.diploma.CourseDiploma;
import com.liferay.lms.course.diploma.CourseDiplomaRegistry;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry;
import com.liferay.lms.model.AsynchronousProcessAudit;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseCompetence;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.AsynchronousProcessAuditLocalServiceUtil;
import com.liferay.lms.service.CourseCompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.util.CourseParams;
import com.liferay.lms.util.displayterms.UserDisplayTerms;
import com.liferay.lms.util.searchcontainer.UserSearchContainer;
import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.comparator.UserFirstNameComparator;
import com.liferay.portal.util.comparator.UserLastNameComparator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.util.LmsLocaleUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class BaseCourseAdminPortlet extends MVCPortlet {
	
	private static Log log = LogFactoryUtil.getLog(BaseCourseAdminPortlet.class);
	
	protected String roleMembersTabJSP = null;
	protected String competenceTabJSP = null;
	protected String editCourseJSP =  null;
	protected String usersResultsJSP = null;
	protected String competenceResultsJSP = null;
	protected String importUsersJSP = null;

	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads"; 
	
	public static String IMAGEGALLERY_MAINFOLDER = "icons";
	public static String IMAGEGALLERY_PORTLETFOLDER = "course";
	public static String IMAGEGALLERY_MAINFOLDER_DESCRIPTION = "Course Image Uploads";
	public static String IMAGEGALLERY_PORTLETFOLDER_DESCRIPTION = StringPool.BLANK;	

	protected void showViewRoleMembersTab(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long roleId=ParamUtil.getLong(renderRequest, "roleId",0);
		
		String students = LanguageUtil.get(themeDisplay.getLocale(),"courseadmin.adminactions.students");
		String tabs1 = ParamUtil.getString(renderRequest, "tabs1", students);
		
		long courseId=ParamUtil.getLong(renderRequest, "courseId",0);
		
		try {
			Course course = CourseLocalServiceUtil.getCourse(courseId);

			UserDisplayTerms displayTerms = new UserDisplayTerms(renderRequest, course.getGroupCreatedId());
			UserSearchContainer searchContainer = new UserSearchContainer(renderRequest, renderResponse.createRenderURL(), displayTerms);	
			
			String redirectOfEdit = ParamUtil.getString(renderRequest, "redirectOfEdit");
			
			List<User> users = null; 
			int total = 0;		
			
			LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
			String teacherName=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getTitle(themeDisplay.getLocale());
			String editorName=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getTitle(themeDisplay.getLocale());
			String tab=StringPool.BLANK;
			
			Role commmanager=RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
			
			
			if(roleId!=0){
				if(roleId==commmanager.getRoleId()){
					tabs1 = LanguageUtil.get(themeDisplay.getLocale(),"courseadmin.adminactions.students");
				}else if(roleId==prefs.getEditorRole()){
					tabs1 = editorName;
				}else{
					tabs1 = teacherName;
				}
			}
			if(tabs1.equals(students)){
				roleId=commmanager.getRoleId();
			}else if(tabs1.equals(editorName)){
				roleId=prefs.getEditorRole();
			}else if(tabs1.equals(teacherName)){
				roleId=prefs.getTeacherRole();
			}
			long createdGroupId=course.getGroupCreatedId();
			if(log.isDebugEnabled()){
				log.debug("START "+searchContainer.getStart());
				log.debug("END "+searchContainer.getEnd());
				log.debug("createdGroupId "+createdGroupId);
				log.debug("roleId "+roleId);
				log.debug("IS ADVANCED SEARCH "+displayTerms.isAdvancedSearch());
			}
			
			if(roleId==commmanager.getRoleId()){
				users = displayTerms.getStudents(courseId, searchContainer.getStart(), searchContainer.getEnd());
				total = displayTerms.countStudents(courseId);
			}else if(roleId == prefs.getTeacherRole()){
				users = displayTerms.getTeachers(courseId, searchContainer.getStart(), searchContainer.getEnd());
				total = displayTerms.countTeachers(courseId);
			}else if(roleId == prefs.getEditorRole()){
				users = displayTerms.getEditors(courseId, searchContainer.getStart(), searchContainer.getEnd());
				total = displayTerms.countEditors(courseId);
			}
			if(log.isDebugEnabled()){
				log.debug("Users "+users.size() );
				log.debug("TOTAL "+total);
			}
			searchContainer.setResults(users);
			searchContainer.setTotal(total);
			boolean commManagerRole = false;
			if(roleId==commmanager.getRoleId()){
				tab =  LanguageUtil.get(themeDisplay.getLocale(),"courseadmin.adminactions.students");
				commManagerRole=true;
			}else if(roleId==prefs.getEditorRole()){
				tab = editorName;
			}else{
				tab = teacherName;
			}
			PortletPreferences preferences = renderRequest.getPreferences();
			boolean showCalendar = GetterUtil.getBoolean(preferences.getValue("showCalendar", StringPool.FALSE));
			boolean backToEdit = ParamUtil.getBoolean(renderRequest, "backToEdit");
			searchContainer.getIteratorURL().setParameter("view", "role-members-tab");
			searchContainer.getIteratorURL().setParameter("courseId", String.valueOf(courseId));
			searchContainer.getIteratorURL().setParameter("tabs1", tabs1);
			
			renderRequest.setAttribute("searchContainer", searchContainer);
			renderRequest.setAttribute("roleId", roleId);
			renderRequest.setAttribute("courseId", courseId);
			renderRequest.setAttribute("commmanager", commmanager);
			renderRequest.setAttribute("course", course);
			renderRequest.setAttribute("tab", tab);
			renderRequest.setAttribute("commManagerRole", commManagerRole);
			renderRequest.setAttribute("showCalendar", showCalendar);
			renderRequest.setAttribute("backToEdit", backToEdit);
			renderRequest.setAttribute("redirectOfEdit", redirectOfEdit);
			renderRequest.setAttribute("createdGroupId", createdGroupId);
			renderRequest.setAttribute("displayTerms", displayTerms);
			
			PortletURL searchURL = renderResponse.createRenderURL();
			searchURL.setParameter("view", "role-members-tab");
			searchURL.setParameter("courseId", String.valueOf(courseId));
			searchURL.setParameter("tab", tab);
			searchURL.setParameter("tabs1", tabs1);
			renderRequest.setAttribute("searchURL", searchURL.toString());
			
			PortletURL returnURL = renderResponse.createRenderURL();
			returnURL.setParameter("view", "");
			renderRequest.setAttribute("returnURL", returnURL.toString());
		
		}catch(SystemException e){
			e.printStackTrace();
		}catch(PortalException e){
			e.printStackTrace();
		}
		
		include(this.roleMembersTabJSP, renderRequest, renderResponse);
	}
	
	protected void showViewCompetenceTab(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		include(this.competenceTabJSP, renderRequest, renderResponse);
	}
	
	protected void showViewImportUsers(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		include(this.importUsersJSP, renderRequest, renderResponse);
	}
	
	protected void showViewUsersResults(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		
		PortletPreferences preferences = null;
		String portletResource = ParamUtil.getString(renderRequest, "portletResource");
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		UserSearchContainer searchContainer = new UserSearchContainer(renderRequest, renderResponse.createRenderURL());	
		UserDisplayTerms displayTerms = (UserDisplayTerms) searchContainer.getDisplayTerms();
		
		try{
			if (Validator.isNotNull(portletResource)) {
				preferences = PortletPreferencesFactoryUtil.getPortletSetup(renderRequest, portletResource);
			}else{
				preferences = renderRequest.getPreferences();
			}
			
			long courseId=ParamUtil.getLong(renderRequest, "courseId",0);
			long roleId=ParamUtil.getLong(renderRequest, "roleId",0);
			Role role=RoleLocalServiceUtil.fetchRole(roleId);
			String tab="";
			
			Course course=CourseLocalServiceUtil.getCourse(courseId);
			boolean backToEdit = ParamUtil.getBoolean(renderRequest, "backToEdit");
			String redirectOfEdit = ParamUtil.getString(renderRequest, "redirectOfEdit");
			String firstName = ParamUtil.getString(renderRequest,"firstName");
			String lastName = ParamUtil.getString(renderRequest,"lastName");
			String screenName = ParamUtil.getString(renderRequest,"screenName");	
			String emailAddress = ParamUtil.getString(renderRequest,"emailAddress");
			String keywords = ParamUtil.getString(renderRequest, "keywords");
			boolean andSearch = ParamUtil.getBoolean(renderRequest,"andSearch",true);
			long team = ParamUtil.getLong(renderRequest, "team");

			LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
			Role commmanager=RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER) ;
			String teacherName=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getTitle(themeDisplay.getLocale());
			String editorName=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getTitle(themeDisplay.getLocale());
			
			if(roleId==commmanager.getRoleId()){
				tab =  LanguageUtil.get(themeDisplay.getLocale(),"courseadmin.adminactions.students");
			}else if(roleId==prefs.getEditorRole()){
				tab = editorName;
			}else{
				tab = teacherName;
			}
			
			log.debug("TAB "+tab);

			PortletURL portletURL = renderResponse.createRenderURL();
			portletURL.setParameter("view","users-results");
			//portletURL.setParameter("paginator","true");	
			portletURL.setParameter("firstName", firstName); 
			portletURL.setParameter("lastName", lastName);
			portletURL.setParameter("screenName", screenName);
			portletURL.setParameter("emailAddress", emailAddress);
			portletURL.setParameter("keywords", keywords);
			portletURL.setParameter("andSearch",Boolean.toString(andSearch));
			portletURL.setParameter("courseId",Long.toString(courseId));
			portletURL.setParameter("roleId",Long.toString(roleId));
			portletURL.setParameter("backToEdit",Boolean.toString(backToEdit));
			portletURL.setParameter("advancedSearch", String.valueOf(displayTerms.isAdvancedSearch()));
			if(backToEdit) {
				portletURL.setParameter("backToEdit",redirectOfEdit);
			}
			
			if(log.isDebugEnabled()){
				log.debug("START "+searchContainer.getStart());
				log.debug("END "+searchContainer.getEnd());
				log.debug("FIRST NAME "+firstName);
				log.debug("LAST NAME "+lastName);
				log.debug("SCREEN NAME "+screenName);
				log.debug("EMAIL ADDRESS "+emailAddress);
				log.debug("roleId "+roleId);
				log.debug("TEAM "+team);
				log.debug("IS ADVANCED SEARCH "+displayTerms.isAdvancedSearch());
			}
			
			
			
			
			/**RESULTS*/
			LinkedHashMap<String,Object> params=new LinkedHashMap<String,Object>();			
			
			OrderByComparator obc = null;
			PortletPreferences portletPreferences = PortalPreferencesLocalServiceUtil.getPreferences(themeDisplay.getCompanyId(), themeDisplay.getCompanyId(), 1);
			if(Boolean.parseBoolean(portletPreferences.getValue("users.first.last.name", "false"))){
				obc = new UserLastNameComparator(true);
			}else{
				obc = new UserFirstNameComparator(true);
			}
		
			
			
			if (!tab.equals(LanguageUtil.get(themeDisplay.getLocale(), "courseadmin.adminactions.students"))) {
				log.debug("NO ES ESTUDIANTE "+tab);
				params.put("notInCourseRoleStu", new CustomSQLParam("WHERE User_.userId NOT IN "
	              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
	              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
	              course.getGroupCreatedId(), commmanager.getRoleId() }));
	           }
		
			if(tab.equals(LanguageUtil.get(themeDisplay.getLocale(), "courseadmin.adminactions.students"))) {
				log.debug("ESTUDIANTE "+tab);
				 params.put("notInCourseRoleTeach", new CustomSQLParam("WHERE User_.userId NOT IN "
			              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
			              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
			              course.getGroupCreatedId(),
			              RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId() }));
				 
				 params.put("notInCourseRoleEdit", new CustomSQLParam("WHERE User_.userId NOT IN "
			              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
			              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
			              course.getGroupCreatedId(),
			              RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId() }));
			 
			 
			}
			
			params.put("notInCourseRole",new CustomSQLParam("WHERE User_.userId NOT IN "+
			                                 " (SELECT UserGroupRole.userId "+
			                                 "  FROM UserGroupRole "+
			                                 "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))",new Long[]{course.getGroupCreatedId(),roleId}));
			
			
			if(team>0){
				params.put("usersTeams",team);
			}
			
			
			boolean showOnlyOrganizationUsers = preferences.getValue("showOnlyOrganizationUsers", "false").equals("true");
			log.debug("MODO ORGANIZACION "+showOnlyOrganizationUsers);
			if (showOnlyOrganizationUsers) {
				Organization organization = null;
				if (themeDisplay.getScopeGroup()!=null && themeDisplay.getScopeGroup().isOrganization()) {
					organization = OrganizationLocalServiceUtil.getOrganization(themeDisplay.getScopeGroup().getClassPK());
				}
				if (organization != null) {
					params.put("usersOrgs", organization.getOrganizationId());
				} else {
					long[] organizationsOfUserList = themeDisplay.getUser().getOrganizationIds();
					String organizationIds = "";
					for(long organizationId: organizationsOfUserList){
						organizationIds += organizationId + ",";
					}
					if(organizationIds.length() > 0) organizationIds = organizationIds.substring(0, organizationIds.length()-1);
					if(organizationIds.length() == 0)
						organizationIds = "-1";
						
					params.put("multipleOrgs",new CustomSQLParam("WHERE User_.userId IN (SELECT users_orgs.userId FROM users_orgs WHERE users_orgs.organizationId IN (?)) ",organizationIds));
				}
	
			}
			
			int total=0;
			List<User> users = new ArrayList<User>();
			
			if(displayTerms.isAdvancedSearch()){
				users = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), firstName, null, lastName, screenName, emailAddress, WorkflowConstants.STATUS_APPROVED, params, andSearch, searchContainer.getStart(), searchContainer.getEnd(), obc);
				total =  UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), firstName, null, lastName, screenName, emailAddress, WorkflowConstants.STATUS_APPROVED, params, andSearch);
			}else{
				users = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), keywords, 0, params, searchContainer.getStart(), searchContainer.getEnd(), obc);
				total =  UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), keywords, WorkflowConstants.STATUS_APPROVED, params);
			}
			log.debug("SHOW SCREEN NAME "+displayTerms.isShowScreenName());
			log.debug("SHOW EMAIL ADDRESS "+displayTerms.isShowEmailAddress());
			log.debug("TOTAL "+total);
			searchContainer.setTotal(total);
			searchContainer.setResults(users);
			
			searchContainer.getIteratorURL().setParameter("courseId",Long.toString(courseId));
			searchContainer.getIteratorURL().setParameter("roleId",Long.toString(roleId));
			searchContainer.getIteratorURL().setParameter("backToEdit",Boolean.toString(backToEdit));
			searchContainer.getIteratorURL().setParameter("view","users-results");
			if(backToEdit) {
				searchContainer.getIteratorURL().setParameter("backToEdit",redirectOfEdit);
			}
			
		
		/***************/
			
			
			renderRequest.setAttribute("searchContainer", searchContainer);
			renderRequest.setAttribute("displayTerms", displayTerms);
			renderRequest.setAttribute("roleId", roleId);
			renderRequest.setAttribute("tab", tab);
			renderRequest.setAttribute("backToEdit", backToEdit);
			renderRequest.setAttribute("redirectOfEdit", redirectOfEdit);
			renderRequest.setAttribute("course", course);
			renderRequest.setAttribute("role", role);
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		
		include(this.usersResultsJSP, renderRequest, renderResponse);
	}
	
	protected void showViewCompetenceResults(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		include(this.competenceResultsJSP, renderRequest, renderResponse);
	}
	
	public void deleteCourse(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Course.class.getName(), actionRequest);

		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		if (courseId > 0) {

			//auditing
			List<Course> editions = CourseLocalServiceUtil.getChildCourses(courseId);
			for(Course edition : editions){
				CourseLocalServiceUtil.deleteCourse(edition.getCourseId());
				AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), Course.class.getName(), edition.getCourseId(), serviceContext.getUserId(), AuditConstants.DELETE, null);
			}
			
			CourseLocalServiceUtil.deleteCourse(courseId);
			AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), Course.class.getName(), courseId, serviceContext.getUserId(), AuditConstants.DELETE, null);
			
			
		}
	}
	public void closeCourse(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		log.debug("******CloseCourse**********");

		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), actionRequest);

		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		if (courseId > 0) {	
			List<Course> editions = CourseLocalServiceUtil.getChildCourses(courseId);
			for(Course edition : editions){
				CourseLocalServiceUtil.closeCourse(edition.getCourseId());
			}
			CourseLocalServiceUtil.closeCourse(courseId);
		}
	}
	

	public void openCourse(ActionRequest actionRequest,ActionResponse actionResponse) throws Exception {

		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		if (courseId > 0) {	
			CourseLocalServiceUtil.openCourse(courseId);
		}
	}
	
	public void saveCourse(ActionRequest actionRequest, ActionResponse actionResponse) {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), uploadRequest);
		} catch (PortalException e1) {
			if(log.isDebugEnabled())e1.printStackTrace();
			
		} catch (SystemException e1) {
			if(log.isDebugEnabled())e1.printStackTrace();
		}
		
		long courseId = ParamUtil.getLong(uploadRequest, "courseId", 0);

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		String redirect = ParamUtil.getString(uploadRequest, "redirect");

		User user = themeDisplay.getUser();
		
		Locale localeDefault = null;
		try {
			localeDefault = themeDisplay.getCompany().getLocale();
		} catch (PortalException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			localeDefault = LocaleUtil.getDefault();
		} catch (SystemException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			localeDefault = LocaleUtil.getDefault();
		}
		
		Map<Locale,String> titleMap = LmsLocaleUtil.getLocalizationMap(uploadRequest, "title");
		
		if(titleMap == null || Validator.isNull(titleMap.get(localeDefault))){
			SessionErrors.add(actionRequest, "title-required");
			actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
			actionResponse.setRenderParameter("redirect", redirect);
			actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp");
			return;
		}

		String description = uploadRequest.getParameter("description");
		long icon = ParamUtil.getLong(uploadRequest, "icon", 0);
		//Cambiar la imagen de la comunidad
		
		String fileName = uploadRequest.getFileName("fileName");
		long courseTemplateId=ParamUtil.getLong(uploadRequest,"courseTemplate",0);
		long courseCalificationType=ParamUtil.getLong(uploadRequest,"calificationType",0);
		String friendlyURL = ParamUtil.getString(uploadRequest, "friendlyURL",
				StringPool.BLANK);
		int startMonth = ParamUtil.getInteger(uploadRequest, "startMon");
		int startYear = ParamUtil.getInteger(uploadRequest, "startYear");
		int startDay = ParamUtil.getInteger(uploadRequest, "startDay");
		int startHour = ParamUtil.getInteger(uploadRequest, "startHour");
		int startMinute = ParamUtil.getInteger(uploadRequest, "startMin");
		int startAMPM = ParamUtil.getInteger(uploadRequest, "startAMPM");
		//Execution Date
		
		int startExecutionMonth = ParamUtil.getInteger(uploadRequest, "startExecutionMon");
		int startExecutionYear = ParamUtil.getInteger(uploadRequest, "startExecutionYear");
		int startExecutionDay = ParamUtil.getInteger(uploadRequest, "startExecutionDay");
		int startExecutionHour = ParamUtil.getInteger(uploadRequest, "startExecutionHour");
		int startExecutionMinute = ParamUtil.getInteger(uploadRequest, "startExecutionMin");
		int startExecutionAMPM = ParamUtil.getInteger(uploadRequest, "startExecutionAMPM");
		
		int stopExecutionMonth = ParamUtil.getInteger(uploadRequest, "stopExecutionMon");
		int stopExecutionYear = ParamUtil.getInteger(uploadRequest, "stopExecutionYear");
		int stopExecutionDay = ParamUtil.getInteger(uploadRequest, "stopExecutionDay");
		int stopExecutionHour = ParamUtil.getInteger(uploadRequest, "stopExecutionHour");
		int stopExecutionMinute = ParamUtil.getInteger(uploadRequest, "stopExecutionMin");
		int stopExecutionAMPM = ParamUtil.getInteger(uploadRequest, "stopExecutionAMPM");
		
		
		String summary = ParamUtil.getString(uploadRequest, "summary", StringPool.BLANK);
		boolean visible = ParamUtil.getBoolean(uploadRequest, "visible", false);
		boolean welcome = ParamUtil.getBoolean(uploadRequest, "welcome", false);
		String welcomeSubject = ParamUtil.getString(uploadRequest, "welcomeSubject",StringPool.BLANK);
		String welcomeMsg = ParamUtil.getString(uploadRequest, "welcomeMsg",StringPool.BLANK);
		boolean goodbye = ParamUtil.getBoolean(uploadRequest, "goodbye", false);
		String goodbyeSubject = ParamUtil.getString(uploadRequest, "goodbyeSubject",StringPool.BLANK);
		String goodbyeMsg = ParamUtil.getString(uploadRequest, "goodbyeMsg",StringPool.BLANK);

		int type = ParamUtil.getInteger(uploadRequest, "type", GroupConstants.TYPE_SITE_OPEN);
		int maxusers = ParamUtil.getInteger(uploadRequest, "maxUsers");
		
		Course course = null;
		long courseEvalId = 0;
		try{
			course = CourseLocalServiceUtil.fetchCourse(courseId);
			if( Validator.isNotNull(course) ){
				courseEvalId = course.getCourseEvalId();
			}
		}catch(SystemException e){
		}
		
		courseEvalId = ParamUtil.getLong(uploadRequest, "courseEvalId", courseEvalId);
		CourseEval courseEval = new CourseEvalRegistry().getCourseEval(courseEvalId);
		
		//course eval Validation
		if(Validator.isNull(courseEval)) {
			SessionErrors.add(actionRequest, "error-course-eval");
			actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
			actionResponse.setRenderParameter("redirect", redirect);
			actionResponse.setRenderParameter("jspPage",
					"/html/courseadmin/editcourse.jsp");
			return;			
		}

		if(!courseEval.especificValidations(uploadRequest, actionResponse)) {
			actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
			actionResponse.setRenderParameter("redirect", redirect);
			actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp");
			return;					
		}

		if (friendlyURL.equals(StringPool.BLANK)) {
			friendlyURL = StringPool.BLANK;
		}

		if (startAMPM > 0) {
			startHour += 12;
		}
		Date startDate = new Date();
		try {
			startDate = PortalUtil.getDate(startMonth, startDay, startYear,
					startHour, startMinute, user.getTimeZone(),
					new EntryDisplayDateException());
		} catch (PortalException e1) {
			e1.printStackTrace();
		}

		int stopMonth = ParamUtil.getInteger(uploadRequest, "stopMon");
		int stopYear = ParamUtil.getInteger(uploadRequest, "stopYear");
		int stopDay = ParamUtil.getInteger(uploadRequest, "stopDay");
		int stopHour = ParamUtil.getInteger(uploadRequest, "stopHour");
		int stopMinute = ParamUtil.getInteger(uploadRequest, "stopMin");
		int stopAMPM = ParamUtil.getInteger(uploadRequest, "stopAMPM");
		if (stopAMPM > 0) {
			stopHour += 12;
		}
		Date stopDate = new Date();
		try {
			stopDate = PortalUtil.getDate(stopMonth, stopDay, stopYear,
					stopHour, stopMinute, user.getTimeZone(),
					new EntryDisplayDateException());
		} catch (PortalException e1) {
			e1.printStackTrace();
		}
		
		if (stopDate.before(startDate)) {
			SessionErrors.add(actionRequest, "courseadmin.new.error.dateinterval");
			actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
			actionResponse.setRenderParameter("redirect", redirect);
			actionResponse.setRenderParameter("jspPage",
					"/html/courseadmin/editcourse.jsp");
			return;
		}

		
		
		//Execution Date Validation
		if (startExecutionAMPM > 0) {
			startExecutionHour += 12;
		}
		
		Calendar startExecutionDate = Calendar.getInstance(user.getTimeZone());
		startExecutionDate.set(Calendar.YEAR, startExecutionYear);
		startExecutionDate.set(Calendar.MONTH, startExecutionMonth);
		startExecutionDate.set(Calendar.DAY_OF_MONTH, startExecutionDay);
		startExecutionDate.set(Calendar.HOUR_OF_DAY, startExecutionHour);
		startExecutionDate.set(Calendar.MINUTE, startExecutionMinute);
		startExecutionDate.set(Calendar.SECOND, 0);
		startExecutionDate.set(Calendar.MILLISECOND,0);
			
		log.debug("START EXECUTION DATE "+startExecutionDate.getTime());
	
		if (stopExecutionAMPM > 0) {
			stopExecutionHour += 12;
		}
		Calendar stopExecutionDate = Calendar.getInstance(user.getTimeZone());
		stopExecutionDate.set(Calendar.YEAR, stopExecutionYear);
		stopExecutionDate.set(Calendar.MONTH, stopExecutionMonth);
		stopExecutionDate.set(Calendar.DAY_OF_MONTH, stopExecutionDay);
		stopExecutionDate.set(Calendar.HOUR_OF_DAY, stopExecutionHour);
		stopExecutionDate.set(Calendar.MINUTE, stopExecutionMinute);
		stopExecutionDate.set(Calendar.SECOND, 0);
		stopExecutionDate.set(Calendar.MILLISECOND,0);
		
		log.debug("STOP EXECUTION DATE "+stopExecutionDate.getTime());
		
		if (stopExecutionDate.before(startExecutionDate)) {
			SessionErrors.add(actionRequest, "courseadmin.new.error.dateinterval");
			actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
			actionResponse.setRenderParameter("redirect", redirect);
			actionResponse.setRenderParameter("jspPage",
					"/html/courseadmin/editcourse.jsp");
			return;
		}
		
		
		
		
		Date ahora = new Date(System.currentTimeMillis());
		
		boolean requiredCourseIcon = GetterUtil.getBoolean(PropsUtil.get("lms.course.icon.required"), false);

		if (requiredCourseIcon) {
			if (Validator.isNull(icon) && Validator.isNull(fileName)) {
				SessionErrors.add(actionRequest, "course-icon-required");
				actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
				actionResponse.setRenderParameter("redirect", redirect);
				actionResponse.setRenderParameter("jspPage",
						"/html/courseadmin/editcourse.jsp");
				return;
			}
		}
		
		//File size validation
		if (Validator.isNotNull(fileName) && !validateFileSize(uploadRequest.getFile("fileName"))){
			SessionErrors.add(actionRequest, "error-file-size");
			actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
			actionResponse.setRenderParameter("redirect", redirect);
			actionResponse.setRenderParameter("jspPage",
					"/html/courseadmin/editcourse.jsp");
			return;
		}
		
		try{
			AssetEntryLocalServiceUtil.validate(serviceContext.getScopeGroupId(), Course.class.getName(), serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames());
		}catch(Exception e){
			actionResponse.setRenderParameters(getParametersMapWithoutNulls(uploadRequest));
			List<String> errors = new ArrayList<String>();
			if (e instanceof AssetCategoryException) {
				AssetCategoryException ace = (AssetCategoryException)e;
				AssetVocabulary assetVocabulary = ace.getVocabulary();
				String vocabularyTitle = StringPool.BLANK;
				if (assetVocabulary != null) 
					vocabularyTitle = assetVocabulary.getTitle(themeDisplay.getLocale());

				if (ace.getType() == AssetCategoryException.AT_LEAST_ONE_CATEGORY) 
					errors.add(LanguageUtil.format(themeDisplay.getLocale(),"please-select-at-least-one-category-for-x", vocabularyTitle));
				else if (ace.getType() ==AssetCategoryException.TOO_MANY_CATEGORIES) 
					errors.add(LanguageUtil.format(themeDisplay.getLocale(), "you-cannot-select-more-than-one-category-for-x", vocabularyTitle));
			}else 
				errors.add(LanguageUtil.get(themeDisplay.getLocale(), "an-unexpected-error-occurred-while-saving"));

			SessionErrors.add(actionRequest, "newCourseErrors", errors);
			actionResponse.setRenderParameter("jspPage", "/html/courseadmin/editcourse.jsp");
			return;
		}

		if (courseId == 0) {
			try{
				course = CourseLocalServiceUtil.addCourse(
						titleMap, description, summary, friendlyURL,
						themeDisplay.getLocale(), ahora, startDate, stopDate, startExecutionDate.getTime(), stopExecutionDate.getTime() , courseTemplateId,type,courseEvalId,
						courseCalificationType,maxusers,serviceContext,false);
				try{
				LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(course.getCompanyId());
				//Añadimos como miembro del sitio web
				GroupLocalServiceUtil.addUserGroups(themeDisplay.getUserId(), new long[] {course.getGroupCreatedId()});
				
				//Añadimos el rol de editor del curso cuando lo crea
				Long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
				
				UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { themeDisplay.getUserId() }, course.getGroupCreatedId(), editorRoleId);

				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
						course.getCourseId(),themeDisplay.getUserId(), AuditConstants.REGISTER, "COURSE_EDITOR_ADD");
				} catch(Exception e){
					e.printStackTrace();
				}
			}catch(PortalException pe){
				if(log.isDebugEnabled())log.debug("Error:"+pe.getMessage());
				if(pe instanceof DuplicateGroupException){
					SessionErrors.add(actionRequest, "duplicate-course");
					actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
					actionResponse.setRenderParameter("jspPage", "/html/courseadmin/editcourse.jsp");
					return;
				}
				if((Validator.isNotNull(pe.getMessage()))&&(pe.getMessage().startsWith("maxUsers "))){					
					
					actionResponse.setRenderParameter("maxUsersError", String.valueOf(LanguageUtil.format(themeDisplay.getLocale(),"max-users-violated", pe.getMessage().replaceAll("maxUsers ", StringPool.BLANK))));
					actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
					actionResponse.setRenderParameter("jspPage", "/html/courseadmin/editcourse.jsp");
					return;
				}else{
					SessionErrors.add(actionRequest, "evaluationtaskactivity.error.systemError");
					actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
					actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp");
					return;
				}
			}catch(SystemException pe){
				List<String> errors = new ArrayList<String>();
				errors.add(LanguageUtil.format(themeDisplay.getLocale(),"max-users-violated", pe.getMessage().replaceAll("maxUsers ", StringPool.BLANK)));
				SessionErrors.add(actionRequest, "newCourseErrors", errors);
				actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
				actionResponse.setRenderParameter("jspPage", "/html/courseadmin/editcourse.jsp");
				return;
			}
			
		}
		// Estamos editando un curso existente.
		else {

			try{
				course = CourseLocalServiceUtil.getCourse(courseId);
				
				course.setTitleMap(titleMap);
				course.setDescription( description,themeDisplay.getLocale());
				course.setStartDate(startDate); 
				course.setEndDate(stopDate);
				course.setCalificationType(courseCalificationType);
				course.setMaxusers(maxusers);
				if(Validator.isNotNull(friendlyURL))course.setFriendlyURL(friendlyURL);
				serviceContext.setAttribute("type", String.valueOf(type));
				/*
				 * Se llama más abajo
				 * com.liferay.lms.service.CourseLocalServiceUtil.modCourse(course,
						summary, serviceContext);*/
			}catch(PortalException pe){ 
				if(pe.getMessage().startsWith("maxUsers ")){ 
					SessionErrors.add(actionRequest, "evaluationtaskactivity.error.systemError");
					actionResponse.setRenderParameter("maxUsersError", String.valueOf(LanguageUtil.format(themeDisplay.getLocale(),"max-users-violated", pe.getMessage().replaceAll("maxUsers ", StringPool.BLANK))));
					actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
					actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp");
					return;
				}else{
					SessionErrors.add(actionRequest, "evaluationtaskactivity.error.systemError");
					actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
					actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp");
					return;
				}
			}catch(SystemException se){
				SessionErrors.add(actionRequest, "evaluationtaskactivity.error.systemError");
				actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
				actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp");
				return;
			}
		}

		if(course!= null){
			//Cambiar la imagen de la comunidad
			if(fileName!=null && !fileName.equals(StringPool.BLANK))
			{
				File file = uploadRequest.getFile("fileName");
				try{
					//LayoutSetLocalServiceUtil.updateLogo(course.getGroupId(), true, true, file);
					//Saving image
					long fileMaxSize = 5 * 1024 * 1024;
					try {
						fileMaxSize = Long.parseLong(PrefsPropsUtil.getString(PropsKeys.DL_FILE_MAX_SIZE));
						//log.debug("---\n fileMaxSize 0 : " + fileMaxSize+", "+ file.length());
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if(file.length() <= fileMaxSize){
						String contentType = uploadRequest.getContentType("fileName");
						long repositoryId = DLFolderConstants.getDataRepositoryId(course.getGroupCreatedId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
						long igFolderId=createIGFolders(actionRequest, themeDisplay.getUserId(),repositoryId);
									
						//Subimos el Archivo en la Document Library
						ServiceContext serviceContextImg = new ServiceContext();
						serviceContextImg.setScopeGroupId(course.getGroupCreatedId());
						//Damos permisos al archivo para usuarios de comunidad.
						serviceContextImg.setAddGroupPermissions(true);
						serviceContextImg.setAddGuestPermissions(true);
						FileEntry image = DLAppLocalServiceUtil.addFileEntry(
							                      themeDisplay.getUserId(), repositoryId , igFolderId , fileName, contentType, fileName, StringPool.BLANK, StringPool.BLANK, file , serviceContextImg ) ;
								
						course.setIcon(image.getFileEntryId());
					} else if(file.length() > fileMaxSize){
						course.setIcon(0);
					}
				}catch(Exception e){
					SessionErrors.add(actionRequest, "evaluationtaskactivity.error.systemError");
					actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
					actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp");
					return;
				}
			} else if (ParamUtil.getBoolean(uploadRequest, "discardLogo", false) && !requiredCourseIcon) {
				course.setIcon(0);
			}

			//Miramos si hay imagen en WelcomeMsg y GoobyeMsg con dominio correcto
			String dominio = themeDisplay.getURLPortal();
			
			welcomeMsg = welcomeMsg.contains("img") ? 
						 welcomeMsg.replace("src=\"/", "src=\"" + dominio + StringPool.SLASH) :  
						 welcomeMsg;

			goodbyeMsg = goodbyeMsg.contains("img") ? 
						 goodbyeMsg.replace("src=\"/", "src=\"" + dominio + StringPool.SLASH) :  
						 goodbyeMsg;

			course.setCourseEvalId(courseEvalId);
			course.setWelcome(welcome);
			course.setWelcomeSubject(welcomeSubject);
			course.setWelcomeMsg(welcomeMsg);
			course.setGoodbye(goodbye);
			course.setGoodbyeSubject(goodbyeSubject);
			course.setGoodbyeMsg(goodbyeMsg);
			course.setExecutionStartDate(startExecutionDate.getTime());
			course.setExecutionEndDate(stopExecutionDate.getTime());
		
			try {
				
				//Update especific content of calificationType
				
				CalificationTypeRegistry cal = new CalificationTypeRegistry();
				CalificationType ctype = cal.getCalificationType(course.getCalificationType());
				String calificationTypeExtraContentError = ctype.setExtraContent(uploadRequest, actionResponse, course);
				log.debug("****calificationTypeExtraContentError:"+calificationTypeExtraContentError);
				
				if(calificationTypeExtraContentError != null){
					SessionErrors.add(actionRequest, "calificationTypeExtraContentError");
					actionResponse.setRenderParameter("calificationTypeExtraContentError", calificationTypeExtraContentError);
				}
				

				
				//Update especific content of diploma (if exists)
				CourseDiplomaRegistry cdr = new CourseDiplomaRegistry();
				if(cdr!=null){
					CourseDiploma courseDiploma = cdr.getCourseDiploma();
					if(courseDiploma!=null){
						String courseDiplomaError = courseDiploma.saveDiploma(uploadRequest, course.getCourseId());
						log.debug("****CourseDiplomaError:"+courseDiplomaError);
						
						if(Validator.isNotNull(courseDiplomaError)){
							SessionErrors.add(actionRequest, "courseDiplomaError");
							actionResponse.setRenderParameter("courseDiplomaError", courseDiplomaError);
						}
					}
				}
				
				try{
					serviceContext.setAttribute("type", String.valueOf(type));
					PermissionChecker permissionChecker = PermissionCheckerFactoryUtil
							.getPermissionCheckerFactory().create(user);
					log.debug("Updating the course");
					boolean allowDuplicateName =  actionRequest.getPreferences().getValue("allowDuplicateName", "false").equals("true");
					if (permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),
							Course.class.getName(), 0, "PUBLISH")) {
						log.debug("With publish permission, setting visible to "+visible);
						CourseLocalServiceUtil.modCourse(course,summary,serviceContext, visible, allowDuplicateName);
					}else{
						CourseLocalServiceUtil.modCourse(course,summary,serviceContext, true, allowDuplicateName);
					}
				}catch(PortalException pe){ 
					if(pe.getMessage().startsWith("maxUsers ")){
						SessionErrors.add(actionRequest, "evaluationtaskactivity.error.systemError");
						actionResponse.setRenderParameter("maxUsersError", String.valueOf(LanguageUtil.format(themeDisplay.getLocale(),"max-users-violated", pe.getMessage().replaceAll("maxUsers ", StringPool.BLANK))));
						actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
						actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp");
						return;
					}else if(pe.getMessage().startsWith("friendlyURL")){
						SessionErrors.add(actionRequest, "friendly-url-error");
						actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
						actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp");
						return;
					}else{
						SessionErrors.add(actionRequest, "evaluationtaskactivity.error.systemError");
						actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
						actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp"); 
						return;
					}
				}catch(SystemException se){
					SessionErrors.add(actionRequest, "evaluationtaskactivity.error.systemError");
					actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
					actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp");
					return;
				}
				 
				actionResponse.setRenderParameter("courseId", String.valueOf(course.getCourseId()));
				actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp");			
				SessionMessages.add(actionRequest, "course-saved-successfully");
				
			} catch (Exception e) {
				e.printStackTrace();
				SessionErrors.add(actionRequest, "evaluationtaskactivity.error.systemError");
				actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
				actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp");
				return;
			}
			
		}

	}
	
	private Map<String, String[]> getParametersMapWithoutNulls(UploadPortletRequest portletRequest) {
		Map<String, String[]> parametersMapWithoutNulls = portletRequest.getParameterMap();
		List<String> keysToRemove = new ArrayList<String>();
		
		java.util.Iterator<String> it = parametersMapWithoutNulls.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if (parametersMapWithoutNulls.get(key) == null) {
				keysToRemove.add(key);
			}
		}
		for (int i = 0; i < keysToRemove.size(); i++) {
			parametersMapWithoutNulls.remove(keysToRemove.get(i));
		}
		return parametersMapWithoutNulls;
	}

	private boolean validateFileSize(File file) {
		boolean valid = true;

		//Comprobar que el tamano del fichero no supere los 5mb
		long size = 5 * 1024 * 1024;
				
		if(file.length() > size){
			valid = false;
		}
		
		return valid;
	}
	@ProcessAction(name="removeUserRole")
	public void removeUserRole(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		
	
		if(log.isDebugEnabled())log.debug("DELETING USER ROLE....");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		Role siteMember = RoleLocalServiceUtil.getRole(
				themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER),
			 siteOwner = RoleLocalServiceUtil.getRole(
				themeDisplay.getCompanyId(), RoleConstants.SITE_OWNER);

		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		long roleId = ParamUtil.getLong(actionRequest, "roleId", 0);
		long userId = ParamUtil.getLong(actionRequest, "userId", 0);
		
		Course course = CourseLocalServiceUtil.getCourse(courseId);
		if (roleId != siteMember.getRoleId()) {
			UserGroupRoleLocalServiceUtil.deleteUserGroupRoles(
					userId,course.getGroupCreatedId(),new long[]{roleId,siteOwner.getRoleId()});
			List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(userId,course.getGroupCreatedId());
			if((userGroupRoles.isEmpty())||
				((userGroupRoles.size()==1)&&
				 (siteMember.getRoleId()==userGroupRoles.get(0).getRoleId()))){
				GroupLocalServiceUtil.unsetUserGroups(userId,
						new long[] { course.getGroupCreatedId() });
			}
		
			
		} else {
			GroupLocalServiceUtil.unsetUserGroups(userId,
					new long[] { course.getGroupCreatedId() });
		}
		LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(course.getCompanyId());
		Long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
		Long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
		
		if(roleId == teacherRoleId){
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
					course.getCourseId(),userId, AuditConstants.UNREGISTER, "COURSE_TUTOR_REMOVE");
		}
		if(roleId == editorRoleId){
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
					course.getCourseId(),userId, AuditConstants.UNREGISTER, "COURSE_EDITOR_REMOVE");
		}
		
	
		actionResponse.setRenderParameter("screenName", ParamUtil.getString(actionRequest, "screenName1"));
		actionResponse.setRenderParameter("firstName", ParamUtil.getString(actionRequest, "firstName1"));
		actionResponse.setRenderParameter("lastName", ParamUtil.getString(actionRequest, "lastName1"));
		actionResponse.setRenderParameter("emailAddress", ParamUtil.getString(actionRequest, "emailAddress1"));
		actionResponse.setRenderParameter("advancedSearch", ParamUtil.getString(actionRequest, "advancedSearch1"));
		actionResponse.setRenderParameter("keywords", ParamUtil.getString(actionRequest, "keywords1"));
		actionResponse.setRenderParameter("andOperator", ParamUtil.getString(actionRequest, "andOperator1"));
		actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
		actionResponse.setRenderParameter("userId", String.valueOf(userId));
		actionResponse.setRenderParameter("roleId", String.valueOf(roleId));
		
		actionResponse.setRenderParameter("tabs1", ParamUtil.getString(actionRequest, "tabs1"));
		
		boolean backToEdit = ParamUtil.getBoolean(actionRequest, "backToEdit", false);
		if(backToEdit){
			actionResponse.setRenderParameter("redirectOfEdit", ParamUtil.getString(actionRequest, "redirectOfEdit",""));
		}
		actionResponse.setRenderParameter("backToEdit", String.valueOf(backToEdit));
		
		actionResponse.setRenderParameter("view", "role-members-tab");
		//actionResponse.setRenderParameters(actionRequest.getParameterMap());
		
		
	}

	public void addUserRole(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception{

		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		long roleId = ParamUtil.getLong(actionRequest, "roleId", 0);
//		long userId = ParamUtil.getLong(actionRequest, "userId", 0);
		// Multiusuario
		long[] to = ParamUtil.getLongValues(actionRequest, "to");
//		long[] userIds=new long[1];
//		userIds[0]=ParamUtil.getLong(actionRequest, "userId");
		Course course = CourseLocalServiceUtil.getCourse(courseId);
		
		LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(course.getCompanyId());
		Long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
		Long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
		
		for (long userId : to) {
			if (!GroupLocalServiceUtil.hasUserGroup(userId, course.getGroupCreatedId())) {
				GroupLocalServiceUtil.addUserGroups(userId,	new long[] { course.getGroupCreatedId() });
			//The application only send one mail at listener
			//User user = UserLocalServiceUtil.getUser(userId);
			//sendEmail(user, course);
			}
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { userId }, course.getGroupCreatedId(), roleId);
			
			if(roleId == teacherRoleId){
				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
						course.getCourseId(),userId, AuditConstants.REGISTER, "COURSE_TUTOR_ADD");
			}
			if(roleId == editorRoleId){
				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
						course.getCourseId(),userId, AuditConstants.REGISTER, "COURSE_EDITOR_ADD");
			}
		}	
		
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
	}
	public void removeAll(ActionRequest actionRequest,ActionResponse actionResponse) throws Exception {
		if(log.isDebugEnabled())log.debug("removeAll");
		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		long roleId = ParamUtil.getLong(actionRequest, "roleId", 0);
		if(log.isDebugEnabled())log.debug("removeAll"+courseId+"--"+roleId);

		Course course = CourseLocalServiceUtil.getCourse(courseId);
		
		LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(course.getCompanyId());
		Long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
		Long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
		
		UserLocalServiceUtil.getRoleUserIds(roleId);
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Role commmanager = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);

		if(log.isDebugEnabled())log.debug("removeAllquery");
		if (roleId != commmanager.getRoleId()) {
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroupRole.class,PortalClassLoaderUtil.getClassLoader());
			dynamicQuery.add(PropertyFactoryUtil.forName("primaryKey.roleId").eq(roleId));
			dynamicQuery.add(PropertyFactoryUtil.forName("primaryKey.groupId").eq(course.getGroupCreatedId()));
			
			List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.dynamicQuery(dynamicQuery);
			
			if(log.isDebugEnabled())log.debug("removeAll"+userGroupRoles.size());
			
			for(UserGroupRole userGroupRole : userGroupRoles){
				if(log.isDebugEnabled())log.debug("removeAll::"+userGroupRole.getUserId());

				UserGroupRoleLocalServiceUtil.deleteUserGroupRoles(
						new long[] { userGroupRole.getUserId() }, course.getGroupCreatedId(), roleId);
				
				
				if(roleId == teacherRoleId){
					AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
							course.getCourseId(),userGroupRole.getUserId(), AuditConstants.UNREGISTER, "COURSE_TUTOR_REMOVE");
				}
				if(roleId == editorRoleId){
					AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
							course.getCourseId(),userGroupRole.getUserId(), AuditConstants.UNREGISTER, "COURSE_EDITOR_REMOVE");
				}
				
			}
			
		}
			long[] users = UserLocalServiceUtil.getGroupUserIds(course.getGroupCreatedId());
			
			for(long user : users){
				List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(user, course.getGroupCreatedId());
				//List<Role> roles = RoleLocalServiceUtil.getUserGroupGroupRoles(user, course.getGroupCreatedId());
				
				if(log.isDebugEnabled())log.debug("User::"+user);
				if((userGroupRoles.size()==0)||(userGroupRoles.size()==1&&userGroupRoles.get(0).getRoleId()==roleId)){
					if(log.isDebugEnabled())log.debug("deleted!");
					GroupLocalServiceUtil.unsetUserGroups(user,new long[] { course.getGroupCreatedId() });
				}
				
			}
			//GroupLocalServiceUtil.unsetUserGroups(userGroupRole.getUserId(), new long[] { course.getGroupCreatedId() });
		

		actionResponse.setRenderParameters(actionRequest.getParameterMap());
	}


	public void importUserRole(PortletRequest portletRequest,
			PortletResponse portletResponse) throws NestableException, IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest
				.getAttribute(WebKeys.THEME_DISPLAY);

		UploadPortletRequest request = PortalUtil.getUploadPortletRequest(portletRequest);
		long courseId = ParamUtil.getLong(portletRequest, "courseId", 0);
		long companyId= themeDisplay.getCompanyId();
		long roleId = ParamUtil.getLong(portletRequest, "roleId", 0);
		String fileName = request.getFileName("fileName");
		Course course = CourseLocalServiceUtil.getCourse(courseId);

		List<String> errors = new ArrayList<String>();
		List<Long> users = new ArrayList<Long>();

		if(fileName==null || StringPool.BLANK.equals(fileName)){
			SessionErrors.add(portletRequest, "courseadmin.importuserrole.csv.fileRequired");
		}
		//Comprobar que el size del fichero no sea mayor de 2mb.
		else if(request.getFile("fileName").length()> 2 * 1024 * 1024){
			SessionErrors.add(portletRequest, "courseadmin.importuserrole.csv.badFormat.size");
		}
		else{ 
			String contentType = request.getContentType("fileName");	
			if(log.isDebugEnabled()){
				log.debug(" contentType : " + contentType );
				log.debug(" fileName : " + fileName );
				log.debug(" Import users ::"+roleId);
			}
			if (!fileName.endsWith(".csv")) { 
				SessionErrors.add(portletRequest, "courseadmin.importuserrole.csv.badFormat");	
			}
			else {
				CSVReader reader = null; 
				try {
					File file = request.getFile("fileName");
					
					reader = new CSVReader(new InputStreamReader(new FileInputStream(file), StringPool.UTF8), CharPool.SEMICOLON);

					String[] currLine;
					int line = 0;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
					Calendar cal = Calendar.getInstance();
					Date allowStartDate;
					Date allowFinishDate;
					
					//Comprobamos el tipo de importaci�n
					String authType = PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_TYPE);
					try {
						Company company = CompanyLocalServiceUtil.getCompany(themeDisplay.getCompanyId());
						if (Validator.isNotNull(company)) {
							authType = company.getAuthType();
						}
					} catch (PortalException e) {
						log.error("Se ha producido un error al obtener el tipo de login de usuario (authType) para companyId=" + themeDisplay.getCompanyId(), e);
					} catch (SystemException e) {
						log.error("Se ha producido un error al obtener el tipo de login de usuario (authType) para companyId=" + themeDisplay.getCompanyId(), e);
					}
					
					
					while ((currLine = reader.readNext()) != null) {

						if(currLine.length > 0 && (line++ > 0)) {
							//Comprobamos errores
							if(Validator.isNull(currLine[0])){
								if (CompanyConstants.AUTH_TYPE_SN.equalsIgnoreCase(authType)) {
									errors.add(LanguageUtil.format(
											getPortletConfig(),
											themeDisplay.getLocale(),
											"courseadmin.importuserrole.csvError.user-name-bad-format", //Importaci�n por screenName
											new Object[] { line }, false));
								}else if(CompanyConstants.AUTH_TYPE_EA.equalsIgnoreCase(authType)){
									errors.add(LanguageUtil.format(
											getPortletConfig(),
											themeDisplay.getLocale(),
											"courseadmin.importuserrole.csvError.email-address-bad-format", //Importaci�n por screenName
											new Object[] { line }, false));
								}else{
									errors.add(LanguageUtil.format(
											getPortletConfig(),
											themeDisplay.getLocale(),
											"courseadmin.importuserrole.csvError.user-id-bad-format", //Importaci�n por screenName
											new Object[] { line }, false));
								}
							}else{
								
								String userIdStr = currLine[0];
								
								if (!userIdStr.equals(StringPool.BLANK)){
									
									try {
										User user = null;
										if(log.isDebugEnabled())log.debug("Identificador:: " + userIdStr);
										if (CompanyConstants.AUTH_TYPE_SN.equalsIgnoreCase(authType)) {
											user = UserLocalServiceUtil.getUserByScreenName(themeDisplay.getCompanyId(), userIdStr.trim());
										}else if(CompanyConstants.AUTH_TYPE_EA.equalsIgnoreCase(authType)){
											user = UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), userIdStr.trim());
										}else{
											user = UserLocalServiceUtil.getUser(Long.parseLong(userIdStr.trim()));
										}
										
										if(user != null){
											if(log.isDebugEnabled())log.debug("User Name:: " + user.getFullName() );
											if(!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())){
												GroupLocalServiceUtil.addUserGroups(user.getUserId(), new long[] { course.getGroupCreatedId() });
											}
		
											users.add(user.getUserId());
											
											UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user.getUserId() }, course.getGroupCreatedId(), roleId);
											String allowStartDateStr = currLine[3];
											String allowEndDateStr = currLine[4];
											
											if(allowStartDateStr.trim().length() >0){
												try{
													cal.setTime(sdf.parse(allowStartDateStr));
												}catch(ParseException e){
													cal.setTime(sdf2.parse(allowStartDateStr));
												}
												int startMonth = cal.get(Calendar.MONTH);
												int startYear = cal.get(Calendar.YEAR);
												int startDay = cal.get(Calendar.DATE);
												allowStartDate = PortalUtil.getDate(startMonth, startDay, startYear,0, 0, user.getTimeZone(),new EntryDisplayDateException());
											}else{
												allowStartDate=null;
											}
											if(allowEndDateStr.trim().length() >0){
												try{
													cal.setTime(sdf.parse(allowEndDateStr));
												}catch(ParseException e){
													cal.setTime(sdf2.parse(allowEndDateStr));
												}
												int stopMonth = cal.get(Calendar.MONTH);
												int stopYear = cal.get(Calendar.YEAR);
												int stopDay = cal.get(Calendar.DATE);
												 allowFinishDate = PortalUtil.getDate(stopMonth, stopDay, stopYear,0, 0, user.getTimeZone(),new EntryDisplayDateException());
											
											}else{
												allowFinishDate=null;
											}
											
											if(Validator.isNotNull(allowStartDate) && Validator.isNotNull(allowFinishDate) ){												
												
												CourseResult courseResult=CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(courseId, user.getUserId());
												if(courseResult==null){
													courseResult=CourseResultLocalServiceUtil.createCourseResult(CounterLocalServiceUtil.increment(CourseResult.class.getName()));
													courseResult.setUserId(user.getUserId());
													courseResult.setCourseId(courseId);
													courseResult.setResult(0);
													courseResult.setPassed(false);
													courseResult.setPassedDate(null);
													courseResult.setAllowStartDate(allowStartDate);
													courseResult.setAllowFinishDate(allowFinishDate);
													courseResult.setStartDate(allowStartDate);
													CourseResultLocalServiceUtil.addCourseResult(courseResult);
												}else{
													courseResult.setAllowStartDate(allowStartDate);
													courseResult.setAllowFinishDate(allowFinishDate);
													if(courseResult.getStartDate()==null){
														courseResult.setStartDate(allowStartDate);
													}
													CourseResultLocalServiceUtil.updateCourseResult(courseResult);
												}
																								
											}
											
										}else{
											errors.add(LanguageUtil.format
															(getPortletConfig(),
															 themeDisplay.getLocale(),
															 "courseadmin.importuserrole.csvError.user-id-not-found", 
															 new Object[] { userIdStr.trim()}, 
															 false));
										}
									} catch (NumberFormatException e) {
										errors.add(LanguageUtil.format(getPortletConfig(),themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.user-id-bad-format", new Object[] { line }, false));
									} catch (PortalException e) {
										errors.add(LanguageUtil.format(getPortletConfig(),themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.user-id-not-found",	new Object[] { userIdStr.trim() }, false));
									} catch (Exception e){
										e.printStackTrace();
										errors.add(LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(),"courseadmin.importuserrole.csvError"));
									}
								}
							}
						}
					}

				} catch (FileNotFoundException e) {
					errors.add(LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.empty-file"));
				}catch(Exception e){
					e.printStackTrace();
				} finally {
					if (reader != null) {
						reader.close();
					}
				}

				if(errors.isEmpty()){
					for (Long user : users) {
						UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user }, course.getGroupCreatedId(), roleId);
					}
					SessionMessages.add(portletRequest, "courseadmin.importuserrole.csv.saved");
					log.debug("correcto");
				}
				else {
					SessionErrors.add(portletRequest, "courseadmin.importuserrole.csvErrors",errors);
				}	
			}	
		}
	}


	@Override
	protected void doDispatch(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		if("importUserRole".equals(ParamUtil.getString(renderRequest, "ajaxAction"))){
			try {
				importUserRole(renderRequest,renderResponse);
			} catch (NestableException e) {
				throw new PortletException(e);
			}
		}
		super.doDispatch(renderRequest, renderResponse);
	}

	public void serveResource(ResourceRequest request, ResourceResponse response)throws PortletException, IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		String action = ParamUtil.getString(request, "action");
		
		if(action.equals("exportCourse")){
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
			try {	
				
				ServiceContext serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), request);				
				long groupId  = ParamUtil.getLong(request, "groupId", 0);
				
				
				if (themeDisplay.getPermissionChecker().hasPermission(groupId, Course.class.getName(), groupId, ActionKeys.UPDATE)) {
					String fileName  = ParamUtil.getString(request, "exportFileName", "New course exported");
					if(fileName.contains("/")){
						fileName=fileName.replaceAll("/", "-");
					}
					if(!(Validator.isNotNull(fileName)) || !(fileName.length()>0) || !(fileName.contains(".lar")) )
						jsonObject.put("error", LanguageUtil.get(themeDisplay.getLocale(), "course.export.badformat"));
					else{
						ClusterNode nodo = ClusterExecutorUtil.getLocalClusterNode();
						String clusterNodoId = nodo == null ? StringPool.DASH : nodo.getClusterNodeId();
						
						String key = ParamUtil.getString(request, "key", null);
						String newKey = clusterNodoId + StringPool.UNDERLINE + themeDisplay.getCompanyId() + StringPool.UNDERLINE + groupId;
						
						if (!StringPool.DASH.equals(clusterNodoId) && !ClusterExecutorUtil.isClusterNodeAlive(clusterNodoId)) {
							jsonObject.put("error", "deadnode");
						} else {
							if (Validator.isNull(key) && MultiVMPoolUtil.get("exportCourseCache", key) != null) { // Pide exportacion pero ya hay una en curso
								jsonObject.put("status", "generating");
								jsonObject.put("key", newKey);
							} else if (Validator.isNull(key) && MultiVMPoolUtil.get("exportCourseCache", key) == null) { // Pide exportacion y no hay ninguna en curso
								AsynchronousProcessAudit process = AsynchronousProcessAuditLocalServiceUtil.addAsynchronousProcessAudit(themeDisplay.getCompanyId(), themeDisplay.getUserId(), Course.class.getName(), "liferay/lms/courseExport");
								Message message=new Message();
								message.put("asynchronousProcessAuditId", process.getAsynchronousProcessAuditId());
								message.put("groupId", groupId);
								message.put("fileName", fileName);
								message.put("key", key);
								message.put("themeDisplay", themeDisplay);
								message.put("serviceContext", serviceContext);
								MessageBusUtil.sendMessage("liferay/lms/courseExport", message);
								jsonObject.put("status", "generating");
								jsonObject.put("key", newKey);
							} else if (Validator.isNotNull(key) && MultiVMPoolUtil.get("exportCourseCache", key) == null){ // Ha pedido exportacion y ya ha acabado
								SessionMessages.add(request, "courseadmin.export.confirmation.success");
								jsonObject.put("status", "ready");
								jsonObject.put("key", key);
							} else { // Ha pedido una exportacion y aun esta trabajando
								jsonObject.put("status", "generating");
								jsonObject.put("key", key);
							}
						}
					}
					
				} else {
					jsonObject.put("error", "bad-permission");
				}
			}catch(Exception e){
				//log.debug(" Error: "+e.getMessage());
				e.printStackTrace();
				jsonObject.put("error", e.getMessage());
			} finally {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=UTF-8");
				
				PrintWriter writer = response.getWriter();
				writer.write(jsonObject.toString());
			}

		} else if(action.equals("export")){
			
			Role commmanager = null;
			LmsPrefs prefs = null;
			try {
				commmanager = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
				prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
			} catch (PortalException e) {
				if(log.isDebugEnabled()){
					e.printStackTrace();
				}
			} catch (SystemException e) {
				if(log.isDebugEnabled()){
					e.printStackTrace();
				}
			}
			
			
			long courseId = ParamUtil.getLong(request, "courseId",0);
			long roleId = ParamUtil.getLong(request, "roleId",0);
			
			List<User> users = new ArrayList<User>();
			
			UserDisplayTerms displayTerms = new UserDisplayTerms(request);
			
			if(roleId==commmanager.getRoleId()){
				users = displayTerms.getStudents(courseId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			}else if(roleId == prefs.getTeacherRole()){
				users = displayTerms.getTeachers(courseId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			}else if(roleId == prefs.getEditorRole()){
				users = displayTerms.getEditors(courseId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			}
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType(ContentTypes.TEXT_CSV_UTF8);
			response.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=users.csv");
			
			byte b[] = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
			
			response.getPortletOutputStream().write(b);
			
			CSVWriter writer = new CSVWriter(new OutputStreamWriter(
					response.getPortletOutputStream(), StringPool.UTF8),CharPool.SEMICOLON);
			
			String authType = PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_TYPE);
			try {
				Company company = CompanyLocalServiceUtil.getCompany(themeDisplay.getCompanyId());
				if (Validator.isNotNull(company)) {
					authType = company.getAuthType();
				}
			} catch (PortalException e) {
				log.error("Se ha producido un error al obtener el tipo de login de usuario (authType) para companyId=" + themeDisplay.getCompanyId(), e);
			} catch (SystemException e) {
				log.error("Se ha producido un error al obtener el tipo de login de usuario (authType) para companyId=" + themeDisplay.getCompanyId(), e);
			}
			
			String[] header = new String[5];
			
			if (CompanyConstants.AUTH_TYPE_SN.equalsIgnoreCase(authType)) {
				header[0] = LanguageUtil.get(themeDisplay.getLocale(), "screen-name");
			}else if(CompanyConstants.AUTH_TYPE_EA.equalsIgnoreCase(authType)){
				header[0] = LanguageUtil.get(themeDisplay.getLocale(), "email-address");
			}else{
				header[0] = LanguageUtil.get(themeDisplay.getLocale(), "user-id");
			}
			
			header[1] = LanguageUtil.get(themeDisplay.getLocale(), "first-name");
			header[2] = LanguageUtil.get(themeDisplay.getLocale(), "last-name");
			header[3] = LanguageUtil.get(themeDisplay.getLocale(), "start-date");
			header[4] = LanguageUtil.get(themeDisplay.getLocale(), "end-date");
			
			writer.writeNext(header);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			CourseResult courseResult = null;
			String fechaIni,fechaFin = new String();
			
			for(User user:users){			
				try {
					courseResult=CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(courseId, user.getUserId());
				} catch (SystemException e) {
					if(log.isDebugEnabled())e.printStackTrace();
				}
				
				fechaIni = (courseResult!=null&&courseResult.getAllowStartDate() != null)?sdf.format(courseResult.getAllowStartDate()):StringPool.BLANK;
				fechaFin = (courseResult!=null&&courseResult.getAllowFinishDate() != null)?sdf.format(courseResult.getAllowFinishDate()):StringPool.BLANK;
			
				String[] result = new String[5];
				
				if (CompanyConstants.AUTH_TYPE_SN.equalsIgnoreCase(authType)) {
					result[0] = user.getScreenName();
				}else if(CompanyConstants.AUTH_TYPE_EA.equalsIgnoreCase(authType)){
					result[0] = user.getEmailAddress();
				}else{
					result[0] = String.valueOf(user.getUserId());
				}
				
				result[1] = user.getFirstName();
				result[2] = user.getLastName();
				result[3] = fechaIni;
				result[4] = fechaFin;
			
				writer.writeNext(result);
			}
			
			writer.flush();
			writer.close();
			response.getPortletOutputStream().flush();
			response.getPortletOutputStream().close();
		}else if(action.equals("getCourses")){
			JSONArray usersJSONArray = JSONFactoryUtil.createJSONArray();
			
			String courseTitle = ParamUtil.getString(request, "courseTitle");
			long courseId = ParamUtil.getLong(request, "courseId");
			long selectedGroupId = ParamUtil.get(request,"selectedGroupId",-1);
			int state = ParamUtil.getInteger(request, "state",WorkflowConstants.STATUS_APPROVED);
			long columnId = ParamUtil.getLong(request, "columnId");
			String expandoValue = ParamUtil.getString(request, "expandoValue");
			
			boolean isAdmin = false;
			try {
				isAdmin = RoleLocalServiceUtil.hasUserRole(themeDisplay.getUserId(), RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.ADMINISTRATOR).getRoleId())
						|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, ActionKeys.UPDATE)
						|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, ActionKeys.DELETE)
						|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, ActionKeys.PERMISSIONS)
						|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, "PUBLISH")
						|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, "COURSEEDITOR")
						|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, "ASSIGN_MEMBERS");
				
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			long groupId = themeDisplay.getScopeGroupId();
			if(selectedGroupId>-1){
				groupId = selectedGroupId;
			}
			
			int closed = -1;
			if(state!=WorkflowConstants.STATUS_ANY){
				if(state==WorkflowConstants.STATUS_APPROVED){
					closed = 0;
				}
				else if(state==WorkflowConstants.STATUS_INACTIVE){
					closed = 1;
				}
			}
			
			String[] templates = getCourseTemplates(request.getPreferences(), themeDisplay.getCompanyId());
			
			log.debug("courseTitle: " + courseTitle);
			log.debug("closed: " + closed);
			log.debug("templates: " + templates);
			log.debug("columnId: " + columnId);
			log.debug("expandoValue: " + expandoValue);
			log.debug("courseId: " + courseId);
			log.debug("themeDisplay.getCompanyId(): " + themeDisplay.getCompanyId());
			log.debug("groupId: " + groupId);
			log.debug("isAdmin: " + isAdmin);
			
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			if(templates != null && templates.length > 0){
				params.put(CourseParams.PARAM_TEMPLATES, templates);
			}
			if(columnId > 0){
				Object[] expandoValues = {columnId, expandoValue};
				params.put(CourseParams.PARAM_CUSTOM_ATTRIBUTE, expandoValues);
			}
			if(!isAdmin){
				params.put(CourseParams.PARAM_PERMISSIONS_ADMIN, themeDisplay.getUserId());
			}
			
			List<Course> listCourse = CourseLocalServiceUtil.searchCourses(themeDisplay.getCompanyId(), courseTitle, themeDisplay.getLanguageId(), state, courseId, groupId, params, -1, -1, null);
			
			log.debug("Listado de cursos obtenido");
			
			JSONObject userJSON = null;

			for (Course course : listCourse) {
				userJSON = JSONFactoryUtil.createJSONObject();
				userJSON.put("courseTitle", course.getTitle(themeDisplay.getLocale()));
				userJSON.put("courseDescription", course.getDescription(themeDisplay.getLocale()));
				usersJSONArray.put(userJSON);
			}

			PrintWriter out = response.getWriter();
			out.println(usersJSONArray.toString());
		}
	}
	
	
	protected String[] getCourseTemplates(PortletPreferences preferences, long companyId){

		// Templates
		String[] templates = null;
		boolean filterByTemplates = GetterUtil.getBoolean(preferences.getValue("filterByTemplates", StringPool.FALSE),false);
		log.debug("Filtrando por plantillas "+filterByTemplates);
		if(filterByTemplates){
			try {  
				String[] layusprsel=null;
				if(preferences.getValue("courseTemplates", null)!=null&&preferences.getValue("courseTemplates", null).length()>0){
					layusprsel=preferences.getValue("courseTemplates", "").split(",");
				}
			    
				if(layusprsel==null || layusprsel.length<=0){
					layusprsel = LmsPrefsLocalServiceUtil.getLmsPrefsIni(companyId).getLmsTemplates().split(",");
				}
				if(layusprsel!=null &&layusprsel.length>0){
					templates = new String[layusprsel.length];
					LayoutSetPrototype layoutSetPrototype = null;
					for (int i=0; i<layusprsel.length; i++) {
						layoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototype(Long.parseLong(layusprsel[i]));
						templates[i] = layoutSetPrototype.getUuid();
				    }
			   }
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return templates;
	}
	
	@ProcessAction(name="activateCompetence")
	public void activateCompetence(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		Long courseId = ParamUtil.getLong(actionRequest, "courseId");
		Long competenceId = ParamUtil.getLong(actionRequest, "competenceId");
		Boolean condition = ParamUtil.getBoolean(actionRequest, "condition");
		String tab = ParamUtil.getString(actionRequest, "tab");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		CourseCompetence cc = CourseCompetenceLocalServiceUtil.fetchByCourseCompetenceCondition(courseId, competenceId, condition);
		
		if(cc==null){
			long courseCompetenceId = CounterLocalServiceUtil.increment(CourseCompetence.class.getName());
			cc = CourseCompetenceLocalServiceUtil.createCourseCompetence(courseCompetenceId);
			cc.setCourseId(courseId);
			cc.setCompetenceId(competenceId);
			cc.setCachedModel(condition);
			cc.setCondition(condition);
			CourseCompetenceLocalServiceUtil.updateCourseCompetence(cc, true);
		}
		
		actionResponse.setRenderParameter("jspPage","/html/courseadmin/competencetab.jsp");
		actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
		actionResponse.setRenderParameter("competenceId", String.valueOf(competenceId));
		if(log.isDebugEnabled())log.debug("tab::"+tab);
		if(tab.equals("1")){
			actionResponse.setRenderParameter("tabs1", LanguageUtil.get(themeDisplay.getLocale(),"competences.necessary"));
		}else{
			actionResponse.setRenderParameter("tabs1", LanguageUtil.get(themeDisplay.getLocale(),"competences.assigned"));
		}
	}
	
	@ProcessAction(name="deactivateCompetence")
	public void deactivateCompetence(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		Long courseId = ParamUtil.getLong(actionRequest, "courseId");
		Long competenceId = ParamUtil.getLong(actionRequest, "competenceId");
		Boolean condition = ParamUtil.getBoolean(actionRequest, "condition");
		String tab = ParamUtil.getString(actionRequest, "tab");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		CourseCompetence cc = CourseCompetenceLocalServiceUtil.fetchByCourseCompetenceCondition(courseId, competenceId, condition);

		if(cc!=null){

			//auditing
			AuditingLogFactory.audit(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), CourseCompetence.class.getName(), cc.getPrimaryKey(), themeDisplay.getUserId(), AuditConstants.DELETE, "Compentence: "+String.valueOf(competenceId)+" Course: "+String.valueOf(courseId));
			
			try{
				CourseCompetenceLocalServiceUtil. deleteCourseCompetence(cc.getPrimaryKey());
			}catch(Exception e){
				if(log.isDebugEnabled())e.printStackTrace();
			}
		}
		
		actionResponse.setRenderParameter("jspPage","/html/courseadmin/competencetab.jsp");
		actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
		actionResponse.setRenderParameter("competenceId", String.valueOf(competenceId));
		
		if(tab.equals("1")){
			actionResponse.setRenderParameter("tabs1", LanguageUtil.get(themeDisplay.getLocale(),"competences.necessary"));
		}else{
			actionResponse.setRenderParameter("tabs1", LanguageUtil.get(themeDisplay.getLocale(),"competences.assigned"));
		}
	}
	
	private long createIGFolders(PortletRequest request,long userId,long repositoryId) throws PortalException, SystemException{
		//Variables for folder ids
		Long igMainFolderId = 0L;
		Long igPortletFolderId = 0L;
		Long igRecordFolderId = 0L;
	    //Search for folders
	    boolean igMainFolderFound = false;
	    boolean igPortletFolderFound = false;
	    try {
	    	//Get the main folder
	    	Folder igMainFolder = DLAppLocalServiceUtil.getFolder(repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,IMAGEGALLERY_MAINFOLDER);
	    	igMainFolderId = igMainFolder.getFolderId();
	    	igMainFolderFound = true;
	    	//Get the portlet folder
	    	DLFolder igPortletFolder = DLFolderLocalServiceUtil.getFolder(repositoryId,igMainFolderId,IMAGEGALLERY_PORTLETFOLDER);
	    	igPortletFolderId = igPortletFolder.getFolderId();
	    	igPortletFolderFound = true;
	    } catch (Exception ex) {
	    }
	    
		ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFolder.class.getName(), request);
		//Damos permisos al archivo para usuarios de comunidad.
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
	    //Create main folder if not exist
	    if(!igMainFolderFound) {
	    	Folder newImageMainFolder=DLAppLocalServiceUtil.addFolder(userId, repositoryId, 0, IMAGEGALLERY_MAINFOLDER, IMAGEGALLERY_MAINFOLDER_DESCRIPTION, serviceContext);
	    	igMainFolderId = newImageMainFolder.getFolderId();
	    	igMainFolderFound = true;
	    }
	    //Create portlet folder if not exist
	    if(igMainFolderFound && !igPortletFolderFound){
	    	Folder newImagePortletFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, igMainFolderId, IMAGEGALLERY_PORTLETFOLDER, IMAGEGALLERY_PORTLETFOLDER_DESCRIPTION, serviceContext);	    	
	    	igPortletFolderFound = true;
	    	igPortletFolderId = newImagePortletFolder.getFolderId();
	    }
	    //Create this record folder
	    if(igPortletFolderFound){
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	    	Date date = new Date();
	    	String igRecordFolderName=dateFormat.format(date)+StringPool.UNDERLINE+userId;
	    	Folder newImageRecordFolder = DLAppLocalServiceUtil.addFolder(userId,repositoryId, igPortletFolderId,igRecordFolderName, igRecordFolderName, serviceContext);
	    	igRecordFolderId = newImageRecordFolder.getFolderId();
	    }
	    return igRecordFolderId;
	  }
	
	public void addAllUsers(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException, SystemException{

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		long roleId = ParamUtil.getLong(actionRequest, "roleId", 0);
		
		Course course = CourseLocalServiceUtil.getCourse(courseId);
		
		LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(course.getCompanyId());
		long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
		long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
		long commmanagerId = RoleLocalServiceUtil.getRole(course.getCompanyId(), RoleConstants.SITE_MEMBER).getRoleId() ;
		
		String firstName = ParamUtil.getString(actionRequest, "addAllUsersFirstName");
		String lastName = ParamUtil.getString(actionRequest, "addAllUsersLastName");
		String screenName = ParamUtil.getString(actionRequest, "addAllUsersScreenName");
		String emailAddress = ParamUtil.getString(actionRequest, "addAllUsersEmailAddress");
		boolean andSearch = ParamUtil.getBoolean(actionRequest, "addAllUsersAndSearch");
		boolean advancedSearch = ParamUtil.getBoolean(actionRequest, "addAllUsersAdvancedSearch");
		String keywords = ParamUtil.getString(actionRequest, "addAllUsersKeywords");
		long team = ParamUtil.getLong(actionRequest, "addAllUsersTeam",0);
		
		
		if(log.isDebugEnabled()){
			log.debug("--First Name "+firstName);
			log.debug("--Last Name "+lastName);
			log.debug("--Screen Name "+screenName);
			log.debug("--Email Address "+emailAddress);
			log.debug("--AndSearch "+andSearch);
			log.debug("--AdvancedSearch "+advancedSearch);
			log.debug("--Keywords "+keywords);
			log.debug("--Team "+team);
		}
		
		LinkedHashMap<String,Object> params=new LinkedHashMap<String,Object>();			
			
		if (roleId != commmanagerId) {
	            params.put("notInCourseRoleStu", new CustomSQLParam("WHERE User_.userId NOT IN "
	              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
	              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
	              course.getGroupCreatedId(), commmanagerId }));
	    }else{
			 params.put("notInCourseRoleTeach", new CustomSQLParam("WHERE User_.userId NOT IN "
		              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
		              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
		              course.getGroupCreatedId(),
		              RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId() }));
			 
			 params.put("notInCourseRoleEdit", new CustomSQLParam("WHERE User_.userId NOT IN "
		              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
		              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
		              course.getGroupCreatedId(),
		              RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId() }));
		}
			
		params.put("notInCourseRole",new CustomSQLParam("WHERE User_.userId NOT IN "+
			                              " (SELECT UserGroupRole.userId "+
			                              "  FROM UserGroupRole "+
			                              "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))",new Long[]{course.getGroupCreatedId(),roleId}));

		if(team>0){
			params.put("usersTeams",team);
		}
		
		boolean showOnlyOrganizationUsers = actionRequest.getPreferences().getValue("showOnlyOrganizationUsers", "false").equals("true");
			
		if (showOnlyOrganizationUsers) {
			Group group = GroupLocalServiceUtil.getGroup(themeDisplay.getScopeGroupId());

			Organization organization = null;

			if (group.isOrganization()) {
				organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());
			}
			if (organization != null) {
				params.put("usersOrgs", organization.getOrganizationId());
			} else {
				
				long[] organizationsOfUserList = themeDisplay.getUser().getOrganizationIds();
				String organizationIds = "";
				for(long organizationId: organizationsOfUserList){
					organizationIds += organizationId + ",";
				}
				if(organizationIds.length() > 0) organizationIds = organizationIds.substring(0, organizationIds.length()-1);
				if(organizationIds.length() == 0)
					organizationIds = "-1";
					
				params.put("multipleOrgs",new CustomSQLParam("WHERE User_.userId IN (SELECT users_orgs.userId FROM users_orgs WHERE users_orgs.organizationId IN (?)) ",organizationIds));
			}

		}
		OrderByComparator obc = null;
		List <User> userList = new ArrayList<User>();  //UserLocalServiceUtil.search(themeDisplay.getCompanyId(), firstName, null, lastName, screenName, emailAddress, 0, params, andSearch, -1, -1, obc);
		if(advancedSearch){
			userList = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), firstName, null, lastName, screenName, emailAddress, WorkflowConstants.STATUS_APPROVED, params, andSearch, WorkflowConstants.STATUS_ANY, WorkflowConstants.STATUS_ANY, obc);
		}else{
			userList = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), keywords, 0, params, WorkflowConstants.STATUS_ANY, WorkflowConstants.STATUS_ANY, obc);
		}

		log.debug("USER LIST SIZE "+userList.size());
		
		for (User user : userList) {
			if (!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())) {
				GroupLocalServiceUtil.addUserGroups(user.getUserId(),	new long[] { course.getGroupCreatedId() });
			//The application only send one mail at listener
			//User user = UserLocalServiceUtil.getUser(userId);
			//sendEmail(user, course);
			}
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user.getUserId() }, course.getGroupCreatedId(), roleId);
			
			if(roleId == teacherRoleId){
				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
						course.getCourseId(),user.getUserId(), AuditConstants.REGISTER, "COURSE_TUTOR_ADD");
			}
			if(roleId == editorRoleId){
				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
						course.getCourseId(),user.getUserId(), AuditConstants.REGISTER, "COURSE_EDITOR_ADD");
			}
		}	
		
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
	}
	
	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			// do nothing
			// _log.error(path + " is not a valid include");
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}

}
