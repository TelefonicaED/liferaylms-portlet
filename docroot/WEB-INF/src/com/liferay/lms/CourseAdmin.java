package com.liferay.lms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
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
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseCompetence;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.CourseCompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.CourseService;
import com.liferay.lms.service.CourseServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.base.CourseServiceBaseImpl;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CourseAdmin
 */
public class CourseAdmin extends MVCPortlet {
	Log log = LogFactoryUtil.getLog(CourseAdmin.class);

	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads"; 
	
	public static String IMAGEGALLERY_MAINFOLDER = "icons";
	public static String IMAGEGALLERY_PORTLETFOLDER = "course";
	public static String IMAGEGALLERY_MAINFOLDER_DESCRIPTION = "Course Image Uploads";
	public static String IMAGEGALLERY_PORTLETFOLDER_DESCRIPTION = StringPool.BLANK;	
	
	private static Log _log = LogFactoryUtil.getLog(CourseAdmin.class);
	
	public void deleteCourse(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Course.class.getName(), actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		String redirect = ParamUtil.getString(actionRequest, "redirect");

		User user = themeDisplay.getUser();
		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		if (courseId > 0) {

			//auditing
			AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), Course.class.getName(), courseId, serviceContext.getUserId(), AuditConstants.CLOSE, null);
			
			CourseLocalServiceUtil.deleteCourse(courseId);
		}
	}
	public void closeCourse(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {


		Indexer indexer=IndexerRegistryUtil.getIndexer(Course.class);
		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Course.class.getName(), actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		String redirect = ParamUtil.getString(actionRequest, "redirect");

		User user = themeDisplay.getUser();
		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		if (courseId > 0) {

			//auditing
			//AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), Course.class.getName(), courseId, serviceContext.getUserId(), AuditConstants.CLOSE, null);
			
			CourseLocalServiceUtil.closeCourse(courseId);
		}
	}
	

	public void openCourse(ActionRequest actionRequest,ActionResponse actionResponse) throws Exception {

		//ServiceContext serviceContext = ServiceContextFactory.getInstance(
		//		Course.class.getName(), actionRequest);

		//ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
		//		.getAttribute(WebKeys.THEME_DISPLAY);
		//String redirect = ParamUtil.getString(actionRequest, "redirect");

		//User user = themeDisplay.getUser();
		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		if (courseId > 0) {
			//auditing
			//AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), 
			//					Course.class.getName(), courseId, serviceContext.getUserId(), AuditConstants.UPDATE, null);
			
			CourseLocalServiceUtil.openCourse(courseId);
		}
	}
	
	public void saveCourse(ActionRequest actionRequest, ActionResponse actionResponse) {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), uploadRequest);
		} catch (PortalException e1) {
			
		} catch (SystemException e1) {
			
		}

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		String redirect = ParamUtil.getString(uploadRequest, "redirect");

		User user = themeDisplay.getUser();
		Enumeration<String> parNam = uploadRequest.getParameterNames();
		String title = StringPool.BLANK;
		while (parNam.hasMoreElements()) {
			String paramName = parNam.nextElement();
			if (paramName.startsWith("title_") && paramName.length() > 6) {
				if (title.equals(StringPool.BLANK)) {
					title = uploadRequest.getParameter(paramName);
				}
			}
		}

		String description = uploadRequest.getParameter("description");
		long icon = ParamUtil.getLong(uploadRequest, "icon", 0);
		//Cambiar la imagen de la comunidad
		
		String fileName = uploadRequest.getFileName("fileName");
		long courseId = ParamUtil.getLong(uploadRequest, "courseId", 0);
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
		String summary = ParamUtil.getString(uploadRequest, "summary", StringPool.BLANK);
		boolean visible = ParamUtil.getBoolean(uploadRequest, "visible", false);
		boolean welcome = ParamUtil.getBoolean(uploadRequest, "welcome", false);
		String welcomeSubject = ParamUtil.getString(uploadRequest, "welcomeSubject",StringPool.BLANK);
		String welcomeMsg = ParamUtil.getString(uploadRequest, "welcomeMsg",StringPool.BLANK);
		
		int type = ParamUtil.getInteger(uploadRequest, "type", GroupConstants.TYPE_SITE_OPEN);
		int maxusers = ParamUtil.getInteger(uploadRequest, "maxUsers");
		
		long courseEvalId=ParamUtil.getLong(uploadRequest, "courseEvalId", 0);
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

		if (friendlyURL.equals(StringPool.BLANK) && !title.equals(StringPool.BLANK)) {
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

		java.util.Date ahora = new java.util.Date(System.currentTimeMillis());
		// Validations
		boolean noTitle = true;
		Enumeration<String> parNames = uploadRequest.getParameterNames();
		while (parNames.hasMoreElements()) {
			String paramName = parNames.nextElement();
			if (paramName.startsWith("title_")
					&& paramName.length() > 6
					&& ParamUtil.getString(uploadRequest, paramName, StringPool.BLANK)
					.length() > 0) {
				noTitle = false;
			}
		}
		if (noTitle) {
			SessionErrors.add(actionRequest, "title-required");
			actionResponse.setRenderParameter("courseId", String.valueOf(courseId));
			actionResponse.setRenderParameter("redirect", redirect);
			actionResponse.setRenderParameter("jspPage",
					"/html/courseadmin/editcourse.jsp");
			return;
		}
		
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


		Course course = null;
		if (courseId == 0) {
			try{
				course = com.liferay.lms.service.CourseLocalServiceUtil.addCourse(
						title, description, summary, friendlyURL,
						themeDisplay.getLocale(), ahora, startDate, stopDate,courseTemplateId,type,courseEvalId,
						courseCalificationType,maxusers,serviceContext,false);
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
				course.setTitle(StringPool.BLANK);
				
				parNam = uploadRequest.getParameterNames();
				while (parNam.hasMoreElements()) {
					String paramName = parNam.nextElement();
					if (paramName.startsWith("title_") && paramName.length() > 6) {
						  String language=paramName.substring(6);
						  Locale locale=LocaleUtil.fromLanguageId(language);
						  course.setTitle(uploadRequest.getParameter(paramName),locale);
					}
				}
				
				course.setDescription( description,themeDisplay.getLocale());
				course.setStartDate(startDate); 
				course.setEndDate(stopDate);
				course.setCalificationType(courseCalificationType);
				course.setMaxusers(maxusers);
				serviceContext.setAttribute("type", String.valueOf(type));
				com.liferay.lms.service.CourseLocalServiceUtil.modCourse(course,
						summary, serviceContext);
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
						//System.out.println("---\n fileMaxSize 0 : " + fileMaxSize+", "+ file.length());
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

			boolean oneTitleNotEmpty = false;
			parNames = uploadRequest.getParameterNames();
			while (parNames.hasMoreElements()) {
				String paramName = parNames.nextElement();
				if (paramName.startsWith("title_") && paramName.length() > 6) {
					String language = paramName.substring(6);
					Locale locale = LocaleUtil.fromLanguageId(language);
					course.setTitle(uploadRequest.getParameter(paramName),locale);

					if (!uploadRequest.getParameter(paramName).equals(StringPool.BLANK)) {
						oneTitleNotEmpty = true;
					}
				}
			}

			if (!oneTitleNotEmpty) {
				SessionErrors.add(actionRequest, "title-empty");
				actionResponse.setRenderParameter("jspPage",
						"/html/courseadmin/editcourse.jsp");
				return;
			}
			course.setCourseEvalId(courseEvalId);
			course.setWelcome(welcome);
			course.setWelcomeSubject(welcomeSubject);
			course.setWelcomeMsg(welcomeMsg);
		
			try {
				try{
					serviceContext.setAttribute("type", String.valueOf(type));
					CourseLocalServiceUtil.modCourse(course,summary,serviceContext);
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
				
				PermissionChecker permissionChecker = PermissionCheckerFactoryUtil
						.getPermissionCheckerFactory().create(user);

				if (permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),
						Course.class.getName(), 0, "PUBLISH")) {

					com.liferay.lms.service.CourseLocalServiceUtil.setVisible(
							course.getCourseId(), visible);
				}
				
				SessionMessages.add(actionRequest, "course-saved-successfully");
				
				actionResponse.setRenderParameter("courseId", String.valueOf(course.getCourseId()));
				actionResponse.setRenderParameter("jspPage","/html/courseadmin/editcourse.jsp");
				
				/*
				WindowState windowState = actionRequest.getWindowState();
				if (redirect != null && !StringPool.BLANK.equals(redirect)) {
					if (!windowState.equals(LiferayWindowState.POP_UP)) {
						actionResponse.sendRedirect(redirect);
					} else {
						redirect = PortalUtil.escapeRedirect(redirect);

						if (Validator.isNotNull(redirect)) {
							actionResponse.sendRedirect(redirect);
						}
					}
				}*/
				
			} catch (Exception e) {
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
	
	public void removeUserRole(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Course.class.getName(), actionRequest);

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
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
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
		for (long userId : to) {
			if (!GroupLocalServiceUtil.hasUserGroup(userId, course.getGroupCreatedId())) {
				GroupLocalServiceUtil.addUserGroups(userId,	new long[] { course.getGroupCreatedId() });
			//The application only send one mail at listener
			//User user = UserLocalServiceUtil.getUser(userId);
			//sendEmail(user, course);
			}
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { userId }, course.getGroupCreatedId(), roleId);
		}		
		
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
	}
	

	public void editInscriptionDates(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception 
	{

		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		long userId = ParamUtil.getLong(actionRequest, "userId", 0);
		User user = UserLocalServiceUtil.getUser(userId);
		Course course = CourseLocalServiceUtil.getCourse(courseId);
		int startMonth = ParamUtil.getInteger(actionRequest, "startMon");
		int startYear = ParamUtil.getInteger(actionRequest, "startYear");
		int startDay = ParamUtil.getInteger(actionRequest, "startDay");
		int stopMonth = ParamUtil.getInteger(actionRequest, "stopMon");
		int stopYear = ParamUtil.getInteger(actionRequest, "stopYear");
		int stopDay = ParamUtil.getInteger(actionRequest, "stopDay");
		boolean startDateEnabled=ParamUtil.getBoolean(actionRequest,"startdate-enabled",false);
		boolean stopDateEnabled=ParamUtil.getBoolean(actionRequest,"stopdate-enabled",false);
		Date allowStartDate = PortalUtil.getDate(startMonth, startDay, startYear,
				0, 0, user.getTimeZone(),
				new EntryDisplayDateException());
		
		if(!startDateEnabled)
	    {
			allowStartDate=null;
	    }
		
		Date allowFinishDate = PortalUtil.getDate(stopMonth, stopDay, stopYear,
				0, 0, user.getTimeZone(),
				new EntryDisplayDateException());
		if(!stopDateEnabled)
	    {
			allowFinishDate=null;
	    }
		CourseServiceUtil.editUserInscriptionDates(courseId,userId,allowStartDate,allowFinishDate);
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
	}
	public void removeAll(ActionRequest actionRequest,ActionResponse actionResponse) throws Exception {
		if(log.isDebugEnabled())log.debug("removeAll");
		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		long roleId = ParamUtil.getLong(actionRequest, "roleId", 0);
		if(log.isDebugEnabled())log.debug("removeAll"+courseId+"--"+roleId);

		Course course = CourseLocalServiceUtil.getCourse(courseId);
		
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
			}
			
			actionResponse.setRenderParameters(actionRequest.getParameterMap());
		}else{
			long[] users = UserLocalServiceUtil.getGroupUserIds(course.getGroupCreatedId());
			
			for(long user : users){
				List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(user, course.getGroupCreatedId());
				//List<Role> roles = RoleLocalServiceUtil.getUserGroupGroupRoles(user, course.getGroupCreatedId());
				
				if(log.isDebugEnabled())log.debug("User::"+user);
				if((userGroupRoles.size()==0)||(userGroupRoles.size()==1&&userGroupRoles.get(0).getRoleId()==roleId)){
					if(log.isDebugEnabled())log.debug("deleted!");
					GroupLocalServiceUtil.unsetUserGroups(user,new long[] { course.getGroupCreatedId() });
				}
				/*for(UserGroupRole userGroupRole:userGroupRoles){
					if(log.isDebugEnabled())log.debug("Role::"+userGroupRole.getRoleId());
				}*/
			}
			//GroupLocalServiceUtil.unsetUserGroups(userGroupRole.getUserId(), new long[] { course.getGroupCreatedId() });
		}

		actionResponse.setRenderParameters(actionRequest.getParameterMap());
	}


	public void importUserRole(PortletRequest portletRequest,
			PortletResponse portletResponse) throws NestableException, IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest
				.getAttribute(WebKeys.THEME_DISPLAY);

		UploadPortletRequest request = PortalUtil.getUploadPortletRequest(portletRequest);
		long courseId = ParamUtil.getLong(portletRequest, "courseId", 0);
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
			System.out.println(" contentType : " + contentType );
			System.out.println(" fileName : " + fileName );
			if (!fileName.endsWith(".csv")) { 
				SessionErrors.add(portletRequest, "courseadmin.importuserrole.csv.badFormat");	
			}
			else {
				CSVReader reader = null; 
				try {
					File file = request.getFile("fileName");
					System.out.println("----------------------------\n  Import users ::"+roleId);
					reader = new CSVReader(new InputStreamReader(new FileInputStream(file), StringPool.UTF8), CharPool.SEMICOLON);

					String[] currLine;
					int line = 0;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar cal = Calendar.getInstance();
					Date allowStartDate;
					Date allowFinishDate;

					while ((currLine = reader.readNext()) != null) {

						if(currLine.length > 0 && (line++ > 0 || (Validator.isNotNull(currLine[0]) && Validator.isNumber(currLine[0])))) {
							String userIdStr = currLine[0];
						
							if (!userIdStr.equals(StringPool.BLANK)){
	
								long userId=0;
								System.out.println("    userId : " + userIdStr.trim() );
								try {
									
									userId = Long.parseLong(userIdStr.trim());
									
									User user = UserLocalServiceUtil.getUser(userId);
									
									if(user != null){
										System.out.println("      User Name : " + user.getFullName() );
										if(!GroupLocalServiceUtil.hasUserGroup(userId, course.getGroupCreatedId())){
											GroupLocalServiceUtil.addUserGroups(userId, new long[] { course.getGroupCreatedId() });
											//sendEmail(user, course);
										}
	
										users.add(userId);
										/**MIGUEL**/
										
										UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { userId }, course.getGroupCreatedId(), roleId);
										String allowStartDateStr = currLine[2];
										String allowEndDateStr = currLine[3];
										//System.out.println(allowStartDateStr);
										//System.out.println(allowEndDateStr);
										
										if(allowStartDateStr.trim().length() >0){
											cal.setTime(sdf.parse(allowStartDateStr));
											int startMonth = cal.get(Calendar.MONTH);
											int startYear = cal.get(Calendar.YEAR);
											int startDay = cal.get(Calendar.DATE);
											allowStartDate = PortalUtil.getDate(startMonth, startDay, startYear,0, 0, user.getTimeZone(),new EntryDisplayDateException());
										}else{
											allowStartDate=null;
										}
										if(allowEndDateStr.trim().length() >0){
											cal.setTime(sdf.parse(allowEndDateStr));
											int stopMonth = cal.get(Calendar.MONTH);
											int stopYear = cal.get(Calendar.YEAR);
											int stopDay = cal.get(Calendar.DATE);
											 allowFinishDate = PortalUtil.getDate(stopMonth, stopDay, stopYear,0, 0, user.getTimeZone(),new EntryDisplayDateException());
										
										}else{
											allowFinishDate=null;
										}
										CourseServiceUtil.editUserInscriptionDates(courseId,userId,allowStartDate,allowFinishDate);
										/**************/
										

										
									}else{
										System.out.println("      User not exits (userId:"+userId+").");
									}
									
									
								} catch (NumberFormatException e) {
									errors.add(LanguageUtil.format(getPortletConfig(),themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.user-id-bad-format", new Object[] { userId }, false));
								} catch (PortalException e) {
									errors.add(LanguageUtil.format(getPortletConfig(),themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.user-id-not-found",	new Object[] { userId,userId }, false));
								} catch (Exception e){
									errors.add(LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(),"courseadmin.importuserrole.csvError"));
								}
							}
						}else{
							if((!Validator.isNotNull(currLine[0]))){
								errors.add(LanguageUtil.format(getPortletConfig(),themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.user-id-bad-format", new Object[] { currLine[0] }, false));

							}
							
							if(!Validator.isNumber(currLine[0])){
								errors.add(LanguageUtil.format(getPortletConfig(),themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.user-id-bad-format", new Object[] { currLine[0] }, false));

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
				}
				else {
					SessionErrors.add(portletRequest, "courseadmin.importuserrole.csvErrors",errors);
				}	
			}	
		}
	}

	public void importCourse(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		try {
			UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);

			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(uploadRequest, "groupId");
			File file = uploadRequest.getFile("importFileName");
			if (!file.exists()) {
				//	System.out.println("Import file does not exist");
				throw new LARFileException("Import file does not exist");
			}
			String portletId = (String) actionRequest.getAttribute(WebKeys.PORTLET_ID);
			LayoutServiceUtil.importPortletInfo(themeDisplay.getLayout().getPlid(), groupId,portletId, uploadRequest.getParameterMap(), file);
			addSuccessMessage(actionRequest, actionResponse);

		}
		catch (Exception e) {
			//System.out.println("Error importando lar.");

			if ((e instanceof LARFileException) || (e instanceof LARTypeException)) {

				SessionErrors.add(actionRequest, e.getClass().getName());

			}
			else {
				_log.error(e, e);
				SessionErrors.add(actionRequest, LayoutImportException.class.getName());
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

	public void cloneCourse(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);	
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), actionRequest);
		
		long groupId  = ParamUtil.getLong(actionRequest, "groupId", 0);
	
		String newCourseName  = ParamUtil.getString(actionRequest, "newCourseName", "New course cloned");
		
		int startMonth = 	ParamUtil.getInteger(actionRequest, "startMon");
		int startYear = 	ParamUtil.getInteger(actionRequest, "startYear");
		int startDay = 		ParamUtil.getInteger(actionRequest, "startDay");
		int startHour = 	ParamUtil.getInteger(actionRequest, "startHour");
		int startMinute = 	ParamUtil.getInteger(actionRequest, "startMin");
		int startAMPM = 	ParamUtil.getInteger(actionRequest, "startAMPM");
		if (startAMPM > 0) {
			startHour += 12;
		}
		Date startDate = PortalUtil.getDate(startMonth, startDay, startYear, startHour, startMinute, themeDisplay.getTimeZone(), EntryDisplayDateException.class);
		
		int stopMonth = 	ParamUtil.getInteger(actionRequest, "stopMon");
		int stopYear = 		ParamUtil.getInteger(actionRequest, "stopYear");
		int stopDay = 		ParamUtil.getInteger(actionRequest, "stopDay");
		int stopHour = 		ParamUtil.getInteger(actionRequest, "stopHour");
		int stopMinute = 	ParamUtil.getInteger(actionRequest, "stopMin");
		int stopAMPM = 		ParamUtil.getInteger(actionRequest, "stopAMPM");
		if (stopAMPM > 0) {
			stopHour += 12;
		}
		Date endDate = PortalUtil.getDate(stopMonth, stopDay, stopYear, stopHour, stopMinute, themeDisplay.getTimeZone(), EntryDisplayDateException.class);
		
		//CloneCourseThread cloneThread = new CloneCourseThread(groupId, newCourseName, themeDisplay, startDate, endDate, serviceContext);
		//Thread thread = new Thread(cloneThread);
		//thread.start();
		
		// Comprobaciones antes del proceso
		boolean errors = false;
		if(endDate.before(startDate)){
			SessionErrors.add(actionRequest, "courseadmin.clone.error.dateinterval");
			errors = true;
		}
		
		Group group = null;
		try{
			group = GroupLocalServiceUtil.getGroup(themeDisplay.getCompanyId(), newCourseName);
		}catch(NoSuchGroupException e){
			group = null;
			if(log.isDebugEnabled())
				e.printStackTrace();
		}
		if(group != null) {
			SessionErrors.add(actionRequest, "courseadmin.clone.error.duplicateName");
			errors = true;
		} else {
			Message message=new Message();
			message.put("groupId",groupId);
			message.put("newCourseName",newCourseName);
			message.put("themeDisplay",themeDisplay);
			message.put("startDate",startDate);
			message.put("endDate",endDate);
			message.put("serviceContext",serviceContext);
			MessageBusUtil.sendMessage("liferay/lms/courseClone", message);
			SessionMessages.add(actionRequest, "courseadmin.clone.confirmation.success");
		}
		
		if(errors)
			actionResponse.sendRedirect(ParamUtil.getString(actionRequest, "redirect"));

	}
	
	public void exportCourse(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);	
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), actionRequest);
		
		long groupId  = ParamUtil.getLong(actionRequest, "groupId", 0);
	
		String fileName  = ParamUtil.getString(actionRequest, "exportFileName", "New course exported");

		Message message = new Message();
		message.put("groupId", groupId);
		message.put("fileName", fileName);
		message.put("themeDisplay", themeDisplay);
		message.put("serviceContext", serviceContext);
		MessageBusUtil.sendMessage("liferay/lms/courseExport", message);
		
		SessionMessages.add(actionRequest, "courseadmin.export.confirmation.success");

	}
	
	public void deleteExportedCourse(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId  = ParamUtil.getLong(actionRequest, "groupId", 0);
		String fileName = ParamUtil.getString(actionRequest, "fileName", StringPool.BLANK);
		String redirect = ParamUtil.getString(actionRequest, "redirect", StringPool.BLANK);
		File f = new File(PropsUtil.get("liferay.home")+"/data/lms_exports/courses/"+themeDisplay.getCompanyId()+"/"+groupId+"/"+fileName);
		if (themeDisplay.getPermissionChecker().hasPermission(groupId, Course.class.getName(), groupId, ActionKeys.DELETE) && f != null && f.isFile()) {
			FileUtil.delete(f);
			SessionMessages.add(actionRequest, "courseadmin.delete.exported.confirmation.success");
		} else {
			SessionMessages.add(actionRequest, "courseadmin.delete.exported.confirmation.error");
		}
		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
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
								Message message = new Message();
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
				//System.out.println(" Error: "+e.getMessage());
				e.printStackTrace();
				jsonObject.put("error", e.getMessage());
			} finally {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=UTF-8");
				
				PrintWriter writer = response.getWriter();
				writer.write(jsonObject.toString());
			}

		} 
		else if(action.equals("export")){
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
			
			
			long groupId = ParamUtil.getLong(request, "groupId",0);
			long roleId = ParamUtil.getLong(request, "roleId",0);
			
			List<User> users = new ArrayList<User>();
			
			if(roleId!=commmanager.getRoleId())
			{
				List<UserGroupRole> ugrs = null;
				try {
					ugrs = UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroupAndRole(groupId, roleId);
				} catch (SystemException e) {
					if(log.isDebugEnabled()){
						e.printStackTrace();
					}
				}

				users=new java.util.ArrayList<User>();
				
				if(ugrs!=null){
					for(UserGroupRole ugr:ugrs)
					{
						try {
							users.add(ugr.getUser());
						} catch (PortalException e) {
							if(log.isDebugEnabled()){
								e.printStackTrace();
							}
						} catch (SystemException e) {
							if(log.isDebugEnabled()){
								e.printStackTrace();
							}
						}
					}
				}
			}else{
				java.util.List<User> userst = null;
				try {
					userst = UserLocalServiceUtil.getGroupUsers(groupId);
				} catch (SystemException e) {
					if(log.isDebugEnabled()){
						e.printStackTrace();
					}
				}
				
				if(userst!=null){
					for(User usert:userst){
						List<UserGroupRole> userGroupRoles = null;
						try {
							userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(usert.getUserId(),groupId);
						} catch (SystemException e) {
							if(log.isDebugEnabled()){
								e.printStackTrace();
							}
						}
						boolean remove =false;
						if(userGroupRoles!=null){
							for(UserGroupRole ugr:userGroupRoles){
								if(ugr.getRoleId()==prefs.getEditorRole()||ugr.getRoleId()==prefs.getTeacherRole()){
									remove = true;
									break;
								}
							}
							if(!remove){
								users.add(usert);
							}
						}
					}
				}
			}
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType(ContentTypes.TEXT_CSV_UTF8);
			response.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=users.csv");
			
			byte b[] = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };

			response.getPortletOutputStream().write(b);

			
			CSVWriter writer = new CSVWriter(new OutputStreamWriter(
					response.getPortletOutputStream(), StringPool.UTF8),CharPool.SEMICOLON);
			
			String[] cabecera = {"Id.Usuario","Nombre de usuario","Fecha Inicio" ,"Fecha Fin"};
			writer.writeNext(cabecera);
			
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		   
		    Long courseId = ParamUtil.getLong(request, "courseId");
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
	
				String[] resultados = {String.valueOf(user.getUserId()),user.getFullName(),fechaIni ,fechaFin};
				writer.writeNext(resultados);
			}

			writer.flush();
			writer.close();
			response.getPortletOutputStream().flush();
			response.getPortletOutputStream().close();
		}
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
			AuditingLogFactory.audit(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), Course.class.getName(), courseId, themeDisplay.getUserId(), AuditConstants.CLOSE, null);
			
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
}

