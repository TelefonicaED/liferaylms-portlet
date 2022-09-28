package com.liferay.lms.util;

import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;

public class LmsConstant {
	
	public final static int STATUS_NOT_STARTED = 0;
	public final static int STATUS_IN_PROGRESS = 1;
	public final static int STATUS_FINISH = 2;
	public final static int STATUS_ERROR = 3;
	public final static String ADDITIONAL_INFORMATION_COURSE_EXPANDO = "course-course-index";
	public final static String P2P_TEAM_ASSIGNATIONS_PROPERTY = "com.liferay.lms.p2p.teams";
	
	public static String SEPARATOR = "_";
	public final static String IMAGEGALLERY_MAINFOLDER = "icons";
	public final static String IMAGEGALLERY_PORTLETFOLDER = "module";
	public final static String IMAGEGALLERY_MAINFOLDER_DESCRIPTION = "Module Image Uploads";
	public final static String IMAGEGALLERY_PORTLETFOLDER_DESCRIPTION = "";
	
	public static String DOCUMENTLIBRARY_MAINFOLDER = "PortletUploads";
	public static String DOCUMENTLIBRARY_PORTLETFOLDER = "module";
	public static String DOCUMENTLIBRARY_MAINFOLDER_DESCRIPTION = "Portlet Document Uploads";
	public static String DOCUMENTLIBRARY_PORTLETFOLDER_DESCRIPTION = "";
	
	public static String DOCUMENTLIBRARY_PORTLET_KEY = "lms.document.library.viewer.portlet";
	public static String DOCUMENTLIBRARY_PAGE_KEY = "lms.document.library.viewer.page";
	
	public static String SHOW_COMPLETED_OPEN_COURSES_INPROGRESS = "lms.course.showCompletedOpenCoursesInProgress";
	public static String ALLOW_WEIGHTLESS_MANDATORY_ACTIVITIES = "lms.course.allowWeightlessMandatoryActivities";
	public static String RESOURCE_INTERNAL_DOCUMENT_LINKED = "lms.resource.internal.linked";
	public static String SHOW_MODULE_CLASSIFICATION = "showModuleClassification";
	public static String SHOW_ACTIVITY_CLASSIFICATION = "showActivityClassification";
	public static String CHECK_EXECUTION_DATE = "checkExecutionDate";
	public static String SEND_MAIL_TO_EDITORS = "sendMailToEditors";
	public static String SEND_MAIL_TO_TUTORS = "sendMailToTutors";
	public static String SHOW_BUTTON_INSCRIPTION_ALL = "showButtonInscriptionAll";
	public static String SHOW_BUTTON_UNSUBSCRIBE_ALL = "showButtonUnsubscribeAll";
	public static String EDITION_TEMPLATE_IDS = "lms.editions.template-ids";
	public static String PREFS_SHOW_OPTION_TEST = "lms.test.show-option-finished";
	
	public final static String COURSETYPE_ICON_MAINFOLDER = "icons";
	public final static String COURSETYPE_ICON_MAINFOLDER_DESCRIPTION = "Course Type Icons";
	public final static String COURSETYPE_ICON_PORTLETFOLDER = "coursetype";
	public final static String COURSETYPE_ICON_PORTLETFOLDER_DESCRIPTION = "";
	
	public static final long DEFAULT_PARENT_COURSE_ID = 0;
	
	public final static String PREFS_VIEW_COURSE_FINISHED_TYPE = "viewCourseFinishedType";
	public final static int VIEW_COURSE_FINISHED_TYPE_ALL = 0;
	public final static int VIEW_COURSE_FINISHED_TYPE_PASSED = 1;
	public final static int VIEW_COURSE_FINISHED_TYPE_DEFAULT = VIEW_COURSE_FINISHED_TYPE_ALL;
	
	public final static String PREFS_ACCESS_COURSE_EXECUTION_DATES = "lms.course.access.execution.dates";
	public final static String PREFS_ACCESS_QUALITY_SURVEY_EXECUTION_DATES = "lms.qualitysurvey.access.execution.dates";
	public final static String PREFS_SHOW_INSPECTOR_ROLE = "lms.show.inspector.role";
	
	public final static String PREFS_USERS_EXTENDED_DATA = "users.view.extended.data";
	public final static boolean PREFS_USERS_EXTENDED_DATA_DEFAULT = false;
	public final static String ACTION_VIEW_USER_EXTENDED = "VIEW_USER_EXTENDED";

	public static final String ACTIVITY_VIEWER_PORTLET_ID =  PortalUtil.getJsSafePortletId("activityViewer"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	public static final String LMS_EDITACTIVITY_PORTLET_ID =  PortalUtil.getJsSafePortletId("editactivity"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	public static final String LMS_EDITMODULE_PORTLET_ID =  PortalUtil.getJsSafePortletId("editmodule"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	public static final String TEACHERS_PORTLET_ID = PortalUtil.getJsSafePortletId("courseteachers"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	

}
