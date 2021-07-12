package com.liferay.lms;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

import org.apache.commons.lang.StringEscapeUtils;

import com.liferay.lms.course.adminaction.AdminActionTypeRegistry;
import com.liferay.lms.model.AsynchronousProcessAudit;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.AsynchronousProcessAuditLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseServiceUtil;
import com.liferay.lms.service.CourseTypeLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.threads.ImportCsvAssignUsersThread;
import com.liferay.lms.threads.ImportCsvThread;
import com.liferay.lms.threads.ImportCsvThreadMapper;
import com.liferay.lms.threads.ImportCsvUnassignUsersThread;
import com.liferay.lms.threads.ImportEditionsThread;
import com.liferay.lms.threads.ImportUsersCourseThread;
import com.liferay.lms.threads.ImportUsersCourseThreadMapper;
import com.liferay.lms.threads.ReportThreadMapper;
import com.liferay.lms.util.CourseParams;
import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.util.EditionsImportExport;
import com.liferay.util.UsersImportExport;
import com.tls.lms.util.CourseOrderByCreationDate;
import com.tls.lms.util.CourseOrderByDate;
import com.tls.lms.util.CourseOrderByTitle;
import com.tls.lms.util.LiferaylmsUtil;

/**
 * Portlet implementation class CourseAdmin
 */
public class CourseAdmin extends BaseCourseAdminPortlet {
	
	private String viewJSP = null;
	private String editCourseJSP = null;
	private String courseTypesJSP = null;
	private String exportJSP = null;
	private String importJSP = null;
	private String cloneJSP = null;
	private String configLmsPrefsJSP = null;
	private String editionsJSP = null;
	private String newEditionJSP = null;
	private String roleMembersJSP = null;
	private String copyParentToEditionsJSP = null;
	private static String PORTLET_DETAIL_NAME = "coursedetail";
	
	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
		editCourseJSP =  getInitParameter("edit-course-template");
		courseTypesJSP = getInitParameter("course-types-template");
		roleMembersTabJSP =  getInitParameter("role-members-tab-template");
		roleMembersJSP =  "/html/courseadmin/rolemembers.jsp";
		exportJSP =  getInitParameter("export-template");
		importJSP =  getInitParameter("import-template");
		cloneJSP =  getInitParameter("clone-template");
		competenceTabJSP =  getInitParameter("competence-tab-template");
		importUsersJSP = getInitParameter("import-users-template");
		usersResultsJSP = getInitParameter("users-results-template");
		competenceResultsJSP = getInitParameter("competence-results-template");
		configLmsPrefsJSP = getInitParameter("config-lms-prefs");
		editionsJSP = getInitParameter("editions-template");
		newEditionJSP = getInitParameter("new-edition-template");
		copyParentToEditionsJSP = getInitParameter("copy-parent-to-editions-template");
	}

	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads"; 
	
	public static String IMAGEGALLERY_MAINFOLDER = "icons";
	public static String IMAGEGALLERY_PORTLETFOLDER = "course";
	public static String IMAGEGALLERY_MAINFOLDER_DESCRIPTION = "Course Image Uploads";
	public static String IMAGEGALLERY_PORTLETFOLDER_DESCRIPTION = StringPool.BLANK;	
	
	private static Log log = LogFactoryUtil.getLog(CourseAdmin.class);
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		LmsPrefs lmsPrefs = null;
		try {
			lmsPrefs = LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId());
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		if(lmsPrefs != null){		
			String jsp = renderRequest.getParameter("view");
			if(log.isDebugEnabled())log.debug("VIEW "+jsp);
			try {
				if(jsp == null || "".equals(jsp)){
					if(themeDisplay.getPortletDisplay().getPortletName().equalsIgnoreCase(PORTLET_DETAIL_NAME)){
						showViewEditCourse(renderRequest, renderResponse);
					}else{
						showViewDefault(renderRequest, renderResponse);
					}
					
				}else if("course-types".equals(jsp)){
					showViewCourseTypes(renderRequest, renderResponse);
				}else if("edit-course".equals(jsp)){
					showViewEditCourse(renderRequest, renderResponse);
				}else if("role-members-tab".equals(jsp)){
					showViewRoleMembersTab(renderRequest, renderResponse);
				}else if("export".equals(jsp)){
					showViewExport(renderRequest, renderResponse);
				}else if("import".equals(jsp)){
					showViewImport(renderRequest, renderResponse);
				}else if("clone".equals(jsp)){
					showViewClone(renderRequest, renderResponse);
				}else if("competence-tab".equals(jsp)){
					showViewCompetenceTab(renderRequest, renderResponse);
				}else if("import-users".equals(jsp)){
					showViewImportUsers(renderRequest, renderResponse);
				}else if("users-results".equals(jsp)){
					showViewUsersResults(renderRequest, renderResponse);
				}else if("competence-results".equals(jsp)){
					showViewCompetenceResults(renderRequest, renderResponse);
				}else if("editions".equals(jsp)){
					showViewEditions(renderRequest, renderResponse);
				}else if("new-edition".equals(jsp)){
					showViewNewEdition(renderRequest, renderResponse);
				}else if("copy-parent-to-editions".equals(jsp)){
					showViewCopyParentToEditions(renderRequest, renderResponse, false);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				showViewDefault(renderRequest, renderResponse);
			} catch (PortletException e) {
				e.printStackTrace();
				showViewDefault(renderRequest, renderResponse);
			}
		}else{
			include(this.configLmsPrefsJSP, renderRequest, renderResponse);
		}
	}
	
	private void showViewDefault(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		searchCourses(renderRequest, renderResponse);
		
		AdminActionTypeRegistry registry =  new AdminActionTypeRegistry();
		renderRequest.setAttribute("adminActionTypes", registry.getAdminActionTypes());
		include(this.viewJSP, renderRequest, renderResponse);
	}
	
	private void showViewCourseTypes(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		PortletURL backURL = renderResponse.createRenderURL();
		renderRequest.setAttribute("backURL", backURL);
		
		List<CourseType> listCourseTypes = new ArrayList<CourseType>();
		try {
			listCourseTypes = CourseTypeLocalServiceUtil.getByCompanyId(themeDisplay.getCompanyId());
		} catch (SystemException e) {
			e.printStackTrace();
		}
		renderRequest.setAttribute("listCourseTypes", listCourseTypes);
		
		include(this.courseTypesJSP, renderRequest, renderResponse);
	}
	
	public void showViewEditCourse(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		if(themeDisplay.getPortletDisplay().getPortletName().equalsIgnoreCase(PORTLET_DETAIL_NAME)){
			long groupId = themeDisplay.getScopeGroupId();
			
			Course course = null;
			try {
				course = CourseLocalServiceUtil.fetchByGroupCreatedId(groupId);
			} catch (SystemException e) {
				log.error("No se ha encontrado ningún curso con el groupCreatedId=" + groupId, e);
			}
			
			if (Validator.isNotNull(course)) {
				AdminActionTypeRegistry registry =  new AdminActionTypeRegistry();
				renderRequest.setAttribute("adminActionTypes", registry.getAdminActionTypes());
				
				renderRequest.setAttribute("course", course);
			}
			
			
		}else{
			AdminActionTypeRegistry registry =  new AdminActionTypeRegistry();
			renderRequest.setAttribute("adminActionTypes", registry.getAdminActionTypes());
			
			PortletURL backURL = renderResponse.createRenderURL();
			renderRequest.setAttribute("backURL", backURL);
			
			
		}
		include(this.editCourseJSP, renderRequest, renderResponse);
	}
	
	private void showViewEditions(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		AdminActionTypeRegistry registry =  new AdminActionTypeRegistry();
		renderRequest.setAttribute("adminActionTypes", registry.getAdminActionTypes());
		
		searchCourses(renderRequest, renderResponse);
		
		long courseId = ParamUtil.getLong(renderRequest, "courseId");
		
		Course course;
		try {
			course = CourseLocalServiceUtil.fetchCourse(courseId);
			if(course!=null){
				String editionsTitle =LanguageUtil.format(themeDisplay.getLocale(), "course-admin.edition-title-x", course.getTitle(themeDisplay.getLocale()));
				
				Group groupCreated = GroupLocalServiceUtil.fetchGroup(course.getGroupCreatedId());
				if(Validator.isNotNull(groupCreated)){
					AssetEntry entry = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), courseId);
					CourseType courseType = Validator.isNotNull(entry) ? CourseTypeLocalServiceUtil.fetchCourseType(entry.getClassTypeId()) : null;
					if(Validator.isNotNull(courseType)){
						editionsTitle += StringPool.SPACE + StringPool.OPEN_PARENTHESIS + courseType.getName(themeDisplay.getLocale()) + StringPool.CLOSE_PARENTHESIS;
						renderRequest.setAttribute("courseTypeId", courseType.getCourseTypeId());
					}
				}
				
				renderRequest.setAttribute("editionsTitle", editionsTitle);
			}
			
			//--Listado de plantillas para las importaciones
			String[] layusprsel=null;
			if(renderRequest.getPreferences().getValue("courseTemplates", null)!=null&&renderRequest.getPreferences().getValue("courseTemplates", null).length()>0)
			{
					layusprsel=renderRequest.getPreferences().getValue("courseTemplates", "").split(",");
			}
			String[] lspList=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getLmsTemplates().split(",");
			if(layusprsel!=null && layusprsel.length>0)
				lspList=layusprsel;
			if(lspList.length>1){
				List<LayoutSetPrototype> prototypeList = new ArrayList<LayoutSetPrototype>();
				for(String lspId: lspList){
					prototypeList.add(LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.parseLong(lspId)));
					
				}
				
				long parentCourseLspId = GroupLocalServiceUtil.fetchGroup(course.getGroupCreatedId()).getPublicLayoutSet().getLayoutSetPrototypeId();
				renderRequest.setAttribute("lspList", prototypeList);
				renderRequest.setAttribute("parentCourseLspId", parentCourseLspId);
				renderRequest.setAttribute("viewTemplateSelector", true);
			}else{
				LayoutSetPrototype lsp=LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.parseLong(lspList[0]));
				renderRequest.setAttribute("lspId", lsp.getLayoutSetPrototypeId());
				renderRequest.setAttribute("viewTemplateSelector", false);
			}
			
		} catch (SystemException | PortalException e) {
			e.printStackTrace();
		}
		
		PortletURL backURL = renderResponse.createRenderURL();
		renderRequest.setAttribute("backURL", backURL);
		
		renderRequest.setAttribute("showInscriptionDate", Boolean.parseBoolean(renderRequest.getPreferences().getValue("inscriptionDateColumn", "true")));
		renderRequest.setAttribute("showExecutionDate", Boolean.parseBoolean(renderRequest.getPreferences().getValue("executionDateColumn", "true")));
		renderRequest.setAttribute("view", "editions");
		renderRequest.setAttribute("courseId", courseId);
		
		//--Importar/Exportar
		ResourceURL exportEditionsURL = renderResponse.createResourceURL();
		exportEditionsURL.setResourceID("exportEditions");
		renderRequest.setAttribute("exportEditionsURL", exportEditionsURL.toString());
		ResourceURL importEditionsExampleURL = renderResponse.createResourceURL();
		importEditionsExampleURL.setResourceID("importEditionsExample");
		renderRequest.setAttribute("importEditionsExampleURL", importEditionsExampleURL.toString());
		ResourceURL importEditionsResultsReportURL = renderResponse.createResourceURL();
		importEditionsResultsReportURL.setResourceID("importEditionsResultsReport");
		renderRequest.setAttribute("importEditionsResultsReportURL", importEditionsResultsReportURL.toString());
		PortletURL importEditionsURL = renderResponse.createActionURL();
		importEditionsURL.setParameter("javax.portlet.action", "importEditions");
		importEditionsURL.setParameter("courseId", String.valueOf(courseId));
		renderRequest.setAttribute("importEditionsURL", importEditionsURL.toString());
		
		String uuid = ParamUtil.getString(renderRequest, "UUID",null);
		renderRequest.setAttribute("UUID" ,uuid);
		if(log.isDebugEnabled())
			log.debug(" ::showViewEditions:: UUID :: " + uuid);
		
		if(ParamUtil.getBoolean(renderRequest, "importEditionsSuccess", Boolean.FALSE))
			SessionMessages.add(renderRequest, "course-admin.confirmation.new-edition-success");
			
		include(this.editionsJSP, renderRequest, renderResponse);
	}
	
	private void showViewNewEdition(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		TimeZone timeZone = themeDisplay.getTimeZone();
		
		long courseId = ParamUtil.getLong(renderRequest, "courseId", 0);
		long courseTypeId = ParamUtil.getLong(renderRequest, "courseTypeId", 0);
		
		if(log.isDebugEnabled()){
			log.debug("CourseId "+courseId);
			log.debug("::courseTypeId:: " + courseTypeId);
		}
		
		try{
			Course course = CourseLocalServiceUtil.fetchCourse(courseId);
			if(course!=null){
				Group group = GroupLocalServiceUtil.fetchGroup(course.getGroupCreatedId());
				renderRequest.setAttribute("courseGroup", group);
				log.debug("GroupId "+group);
				log.debug("CourseId "+course);
				renderRequest.setAttribute("course", course);
				
				String newCourseName;
				int newCourseEditionNumber = CourseLocalServiceUtil.countChildCourses(course.getCourseId())+1;
				newCourseName = LanguageUtil.get(themeDisplay.getLocale(), "courseadmin.edition")+" "+newCourseEditionNumber;
				String newCourseURL;				
			        String courseURL; 		
			        String editionURL;
			        courseURL = FriendlyURLNormalizerUtil.normalize(course.getFriendlyURL());	
			        editionURL = FriendlyURLNormalizerUtil.normalize(newCourseName);						      
				newCourseURL = FriendlyURLNormalizerUtil.normalize(course.getFriendlyURL()+" "+newCourseName);
				if(courseURL.length() + editionURL.length()  > 100){										
					newCourseURL = FriendlyURLNormalizerUtil.normalize(course.getFriendlyURL().substring(0,100-newCourseName.length())+" "+newCourseName);
				}
				Group groupURL = null;
				groupURL=GroupLocalServiceUtil.fetchFriendlyURLGroup(themeDisplay.getCompanyId(), newCourseURL);
				while(groupURL != null){					
                                newCourseName = LanguageUtil.get(themeDisplay.getLocale(), "courseadmin.edition")+" "+newCourseEditionNumber;
                                courseURL = FriendlyURLNormalizerUtil.normalize(course.getFriendlyURL());	
        			editionURL = FriendlyURLNormalizerUtil.normalize(newCourseName);	
                                newCourseURL = FriendlyURLNormalizerUtil.normalize(course.getFriendlyURL()+" "+newCourseName);
						if(courseURL.length() + editionURL.length()  > 100){										
							newCourseURL = FriendlyURLNormalizerUtil.normalize(course.getFriendlyURL().substring(0,100-newCourseName.length())+" "+newCourseName);
						}
						groupURL=GroupLocalServiceUtil.fetchFriendlyURLGroup(themeDisplay.getCompanyId(), newCourseURL);
						newCourseEditionNumber++;							
						
					}
									
					
				renderRequest.setAttribute("editionFriendlyURL", newCourseURL);
				
				renderRequest.setAttribute("newCourseName", newCourseName);
				renderRequest.setAttribute("courseId", courseId);
				
				String editionsTitle =LanguageUtil.format(themeDisplay.getLocale(), "course-admin.new-edition-x", course.getTitle(themeDisplay.getLocale()));
				
				List<LayoutSetPrototype> prototypeList = null;
				
				if(courseTypeId>0){
					CourseType courseType = CourseTypeLocalServiceUtil.fetchCourseType(courseTypeId);
					if(Validator.isNotNull(courseType)){
						editionsTitle += StringPool.SPACE + StringPool.OPEN_PARENTHESIS + courseType.getName(themeDisplay.getLocale()) + StringPool.CLOSE_PARENTHESIS;
						renderRequest.setAttribute("courseTypeId", courseTypeId);
						
						List<Long> editionTemplateIds = courseType.getEditionTemplateIds();
						LayoutSetPrototype template = null;
						if(editionTemplateIds != null && editionTemplateIds.size() > 0){
							prototypeList = new ArrayList<LayoutSetPrototype>();
							for(Long templateId:editionTemplateIds){
								template = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(templateId);
								
								prototypeList.add(template);
							}
						}
					}
				}
				
				if(prototypeList == null || prototypeList.size() == 0){
				
					String[] layusprsel=null;
					if(renderRequest.getPreferences().getValue("courseTemplates", null)!=null&&renderRequest.getPreferences().getValue("courseTemplates", null).length()>0)
					{
							layusprsel=renderRequest.getPreferences().getValue("courseTemplates", "").split(",");
					}
					String[] lspList=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getLmsTemplates().split(",");
					if(layusprsel!=null && layusprsel.length>0)
					{
						lspList=layusprsel;
	
					}
					
					prototypeList = new ArrayList<LayoutSetPrototype>();
					for(String lspId: lspList){
						prototypeList.add(LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.parseLong(lspId)));
						
					}
					
				}
				renderRequest.setAttribute("editionTitle", editionsTitle);
				
				//renderRequest.setAttribute("editionFriendlyURL", course.getFriendlyURL()+"-"+newCourseName.replace(" ", "-"));
				
				
				if(prototypeList.size()>1){
					long parentCourseLspId = GroupLocalServiceUtil.fetchGroup(course.getGroupCreatedId()).getPublicLayoutSet().getLayoutSetPrototypeId();
					renderRequest.setAttribute("lspList", prototypeList);
					renderRequest.setAttribute("parentCourseLspId", parentCourseLspId);
					renderRequest.setAttribute("viewTemplateSelector", true);
				}else{
					LayoutSetPrototype lsp=LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(prototypeList.get(0).getLayoutSetPrototypeId());
					renderRequest.setAttribute("lspId", lsp.getLayoutSetPrototypeId());
					renderRequest.setAttribute("viewTemplateSelector", false);
				}
			}
			
			
			SimpleDateFormat formatDay = new SimpleDateFormat("dd");
			formatDay.setTimeZone(timeZone);
			SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
			formatMonth.setTimeZone(timeZone);
			SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
			formatYear.setTimeZone(timeZone);
			SimpleDateFormat formatHour = new SimpleDateFormat("HH");
			formatHour.setTimeZone(timeZone);
			SimpleDateFormat formatMin = new SimpleDateFormat("mm");
			formatMin.setTimeZone(timeZone);
			Date today=course.getStartDate();
			if(Validator.isNull(today)){
				today = new Date();
			}
			int startDay=Integer.parseInt(formatDay.format(today));
			int startMonth=Integer.parseInt(formatMonth.format(today))-1;
			int startYear=Integer.parseInt(formatYear.format(today));
			int startHour=Integer.parseInt(formatHour.format(today));
			int startMin=Integer.parseInt(formatMin.format(today));
			
			today = course.getEndDate();
			if(Validator.isNull(today)){
				today = new Date();
			}
			
			int endDay=Integer.parseInt(formatDay.format(today));
			int endMonth=Integer.parseInt(formatMonth.format(today))-1;
			int endYear=Integer.parseInt(formatYear.format(today));
			int endHour=Integer.parseInt(formatHour.format(today));
			int endMin=Integer.parseInt(formatMin.format(today));
			//Inscription Date
			renderRequest.setAttribute("startDay", startDay);
			renderRequest.setAttribute("startMonth", startMonth);
			renderRequest.setAttribute("startYear", startYear);
			renderRequest.setAttribute("startHour", startHour);
			renderRequest.setAttribute("startMin", startMin);
			renderRequest.setAttribute("defaultStartYear", LiferaylmsUtil.defaultStartYear);
			renderRequest.setAttribute("defaultEndYear", LiferaylmsUtil.defaultEndYear);
			renderRequest.setAttribute("endDay", endDay);
			renderRequest.setAttribute("endMonth", endMonth);
			renderRequest.setAttribute("endYear", endYear);
			renderRequest.setAttribute("endHour", endHour);
			renderRequest.setAttribute("endMin", endMin);
			
			
			today = course.getExecutionStartDate();
			
			if(Validator.isNull(today)){
				today = new Date();
			}
			startDay=Integer.parseInt(formatDay.format(today));
			startMonth=Integer.parseInt(formatMonth.format(today))-1;
			startYear=Integer.parseInt(formatYear.format(today));
			startHour=Integer.parseInt(formatHour.format(today));
			startMin=Integer.parseInt(formatMin.format(today));
			
			today = course.getExecutionEndDate();
			
			if(Validator.isNull(today)){
				today = new Date();
			}
			endDay=Integer.parseInt(formatDay.format(today));
			endMonth=Integer.parseInt(formatMonth.format(today))-1;
			endYear=Integer.parseInt(formatYear.format(today));
			endHour=Integer.parseInt(formatHour.format(today));
			endMin=Integer.parseInt(formatMin.format(today));
			
			//Execution Date
			renderRequest.setAttribute("startExecutionDay", startDay);
			renderRequest.setAttribute("startExecutionMonth", startMonth);
			renderRequest.setAttribute("startExecutionYear", startYear);
			renderRequest.setAttribute("startExecutionHour", startHour);
			renderRequest.setAttribute("startExecutionMin", startMin);
			renderRequest.setAttribute("defaultStartYear", LiferaylmsUtil.defaultStartYear);
			renderRequest.setAttribute("defaultEndYear", LiferaylmsUtil.defaultEndYear);
			renderRequest.setAttribute("endExecutionDay", endDay);
			renderRequest.setAttribute("endExecutionMonth", endMonth);
			renderRequest.setAttribute("endExecutionYear", endYear);
			renderRequest.setAttribute("endExecutionHour", endHour);
			renderRequest.setAttribute("endExecutionMin", endMin);
			
			renderRequest.setAttribute("courseTypeId", courseTypeId);
			
			
			long parentLspId = 0;
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(course.getGroupCreatedId(), false);
			if(layoutSet != null){
				parentLspId = layoutSet.getLayoutSetPrototypeId();
			}
			
			renderRequest.setAttribute("parentLspId", parentLspId);
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		include(this.newEditionJSP, renderRequest, renderResponse);
	}
	
	private void showViewExport(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		include(this.exportJSP, renderRequest, renderResponse);
	}
	
	private void showViewImport(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
			
		include(this.importJSP, renderRequest, renderResponse);
		
	}
	
	protected void showViewImportUsers(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		//include(this.importUsersJSP, renderRequest, renderResponse);
		
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		if(log.isDebugEnabled())log.debug("---- showViewImportUsers ---");
		
		
				
		PortletURL backURL = renderResponse.createRenderURL();
		renderRequest.setAttribute("backURL", backURL);
		
		long courseId = ParamUtil.getLong(renderRequest, "courseId");
		String roleId = ParamUtil.getString(renderRequest, "roleId",null);
		log.debug("courseId: " + courseId);
		log.debug("roleId: " + roleId);
		
		renderRequest.setAttribute("view", "import-users");
		//renderRequest.setAttribute("view", "role-members-tab");
		renderRequest.setAttribute("courseId", courseId);
		renderRequest.setAttribute("roleId", roleId);
		
		ResourceURL importUsersCourseReportURL = renderResponse.createResourceURL();
		importUsersCourseReportURL.setResourceID("importUsersCourseReport");
		//renderRequest.setAttribute("action", "importUsersCourse");
		renderRequest.setAttribute("importUsersCourseReportURL", importUsersCourseReportURL.toString());
		
		String uuid = ParamUtil.getString(renderRequest, "UUID",null);
		renderRequest.setAttribute("UUID" ,uuid);
		if(log.isDebugEnabled())
			log.debug(" ::showViewImportUsers:: UUID :: " + uuid);
		
		showViewRoleMembersTab(renderRequest, renderResponse);
		
		//include(this.importUsersJSP, renderRequest, renderResponse);
		//include(this.roleMembersJSP, renderRequest, renderResponse);
		//include(this.roleMembersTabJSP, renderRequest, renderResponse);
	}
	
	private void showViewCopyParentToEditions(RenderRequest renderRequest,RenderResponse renderResponse, boolean ajax) throws IOException, PortletException {
		long courseId = ParamUtil.getLong(renderRequest, "courseId");
		
		try {
			Course course = CourseLocalServiceUtil.getCourse(courseId);
			
			renderRequest.setAttribute("course", course);
			
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
					.getAttribute(WebKeys.THEME_DISPLAY);
			
			PortletURL copyEditionsURL = renderResponse.createActionURL();
			copyEditionsURL.setParameter("javax.portlet.action", "copyEditions");
			copyEditionsURL.setParameter("courseId", String.valueOf(courseId));
			renderRequest.setAttribute("copyEditionsURL", copyEditionsURL);
			
			String copyEditions = ParamUtil.getString(renderRequest, "copyEditions","");
			String editionIds = ParamUtil.getString(renderRequest, "editionIds","");

			String action =  ParamUtil.getString(renderRequest, "action","");
			
			copyEditions = StringEscapeUtils.escapeJavaScript(copyEditions);
			
			if(log.isDebugEnabled()){
				log.debug("copyEditions "+copyEditions);
			}
			renderRequest.setAttribute("copyEditions", copyEditions);
			renderRequest.setAttribute("editionIds", editionIds);
			renderRequest.setAttribute("action", action);
			
			String freetext = ParamUtil.getString(renderRequest, "freetext","");
			
			int dateMonthStart = ParamUtil.getInteger(renderRequest, "dateMonthStart",Calendar.getInstance().get(Calendar.MONTH));
			int dateDayStart = ParamUtil.getInteger(renderRequest, "dateDayStart",Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
			int dateYearStart = ParamUtil.getInteger(renderRequest, "dateYearStart",Calendar.getInstance().get(Calendar.YEAR));
			int dateMonthEnd = ParamUtil.getInteger(renderRequest, "dateMonthEnd",Calendar.getInstance().get(Calendar.MONTH));
			int dateDayEnd = ParamUtil.getInteger(renderRequest, "dateDayEnd",Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
			int dateYearEnd = ParamUtil.getInteger(renderRequest, "dateYearEnd",Calendar.getInstance().get(Calendar.YEAR));
			boolean startDateFilter = ParamUtil.getBoolean(renderRequest, "startDateFilter",false);
			boolean endDateFilter = ParamUtil.getBoolean(renderRequest, "endDateFilter",false);

			Calendar startDate = Calendar.getInstance();
			if(startDateFilter){
				startDate.set(Calendar.YEAR, dateYearStart);
				startDate.set(Calendar.MONTH, dateMonthStart);
				startDate.set(Calendar.DAY_OF_MONTH, dateDayStart);
				startDate.set(Calendar.HOUR, 0);
				startDate.set(Calendar.MINUTE, 0);
				startDate.set(Calendar.SECOND, 0);
			}else{
				startDate = null;
			}

			Calendar endDate = Calendar.getInstance();
			if(endDateFilter){
				endDate.set(Calendar.YEAR, dateYearEnd);
				endDate.set(Calendar.MONTH, dateMonthEnd);
				endDate.set(Calendar.DAY_OF_MONTH, dateDayEnd);
				endDate.set(Calendar.HOUR, 23);
				endDate.set(Calendar.MINUTE, 59);
				endDate.set(Calendar.SECOND, 59);
			}else{
				endDate = null;
			}
			
			PortletURL backURL = renderResponse.createRenderURL();
			renderRequest.setAttribute("backURL", backURL);
			
			log.debug(":::show view users to send:::");		
			PortletURL iteratorURL = renderResponse.createRenderURL();
			iteratorURL.setParameter("ajaxAction", "searchEditions");
			iteratorURL.setParameter("view", "search-editions");
			iteratorURL.setParameter("editionIds", editionIds);
			iteratorURL.setParameter("copyEditions", copyEditions);
			iteratorURL.setParameter("freetext", freetext);
			iteratorURL.setParameter("yearRangeStart", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-10));
			iteratorURL.setParameter("yearRangeEnd", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)+10));
			iteratorURL.setParameter("dateMonthStart", String.valueOf(dateMonthStart));
			iteratorURL.setParameter("dateDayStart", String.valueOf(dateDayStart));
			iteratorURL.setParameter("dateYearStart", String.valueOf(dateYearStart));
			iteratorURL.setParameter("dateMonthEnd", String.valueOf(dateMonthEnd));
			iteratorURL.setParameter("dateDayEnd", String.valueOf(dateDayEnd));
			iteratorURL.setParameter("dateYearEnd", String.valueOf(dateYearEnd));
			iteratorURL.setParameter("startDateFilter", String.valueOf(startDateFilter));
			iteratorURL.setParameter("endDateFilter", String.valueOf(endDateFilter));
			iteratorURL.setParameter("courseId", String.valueOf(courseId));
			
			SearchContainer<Course> searchContainer = new SearchContainer<Course>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
					10, iteratorURL, null, null);
					
			searchContainer.setHover(false);
			searchContainer.setDeltaConfigurable(false);
			log.debug("****************DELTA 2 RENDER: "+searchContainer.getDelta());
			searchContainer.setDelta(10);
			
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			
			if (startDate != null){
				params.put(CourseParams.PARAM_EXECUTION_START_DATE, startDate.getTime());
			}
			
			if (endDate != null){
				params.put(CourseParams.PARAM_EXECUTION_END_DATE, endDate.getTime());
			}
			
			
			
			searchContainer.setResults(CourseLocalServiceUtil.searchCourses(themeDisplay.getCompanyId(), freetext, themeDisplay.getLanguageId(), WorkflowConstants.STATUS_APPROVED, courseId, 0, params, 
					searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()));
			searchContainer.setTotal(CourseLocalServiceUtil.countCourses(themeDisplay.getCompanyId(), freetext, themeDisplay.getLanguageId(), WorkflowConstants.STATUS_APPROVED, courseId, 0, params));
			
			System.out.println("results: " + searchContainer.getResults().size());
			System.out.println("total: " + searchContainer.getTotal());
			
			renderRequest.setAttribute("yearRangeStart", Calendar.getInstance().get(Calendar.YEAR)-10);
			renderRequest.setAttribute("yearRangeEnd", Calendar.getInstance().get(Calendar.YEAR)+10);
			renderRequest.setAttribute("dateMonthStart", dateMonthStart);
			renderRequest.setAttribute("dateDayStart", dateDayStart);
			renderRequest.setAttribute("dateYearStart", dateYearStart);
			renderRequest.setAttribute("dateMonthEnd", dateMonthEnd);
			renderRequest.setAttribute("dateDayEnd", dateDayEnd);
			renderRequest.setAttribute("dateYearEnd", dateYearEnd);
			
					
			renderRequest.setAttribute("searchContainer", searchContainer);
			
			PortletURL searchURL = renderResponse.createRenderURL();
			searchURL.setParameter("view", "");
			renderRequest.setAttribute("searchURL", searchURL.toString());
			
			ExpandoTable table = ExpandoTableLocalServiceUtil.getTable(themeDisplay.getCompanyId(), PortalUtil.getClassNameId(Course.class), ExpandoTableConstants.DEFAULT_TABLE_NAME);
			List<ExpandoColumn> columns = ExpandoColumnLocalServiceUtil.getColumns(table.getTableId());
			
			renderRequest.setAttribute("columns", columns);
			
			List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId());
			
			renderRequest.setAttribute("modules", modules);
			
			renderRequest.setAttribute("namespace", renderResponse.getNamespace());
			if(ajax){
				include("/html/courseadmin/searcheditions.jsp", renderRequest, renderResponse);
			}else{
				include(copyParentToEditionsJSP, renderRequest, renderResponse);
			}
		} catch (PortalException | SystemException e) {
			e.printStackTrace();
		}
	}
	
	private void showViewClone(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		TimeZone timeZone = themeDisplay.getTimeZone();
		
		long groupId = ParamUtil.getLong(renderRequest, "groupId", 0);
		
		try{
			Group groupObj = GroupLocalServiceUtil.getGroup(groupId);
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(groupId);
			
			boolean isCourseChild = false;
			String courseTitle = "course";
			if(course!=null){
				if(course.getParentCourseId()>0){
					isCourseChild=true;
				}
				
				courseTitle = course.getTitle(themeDisplay.getLocale());	
				String newCourseName = groupObj.getName()+"_"+Time.getShortTimestamp();
				renderRequest.setAttribute("isCourseChild", isCourseChild);
				renderRequest.setAttribute("courseTitle", courseTitle);
				renderRequest.setAttribute("groupId", groupId);
				renderRequest.setAttribute("newCourseName", newCourseName);
				
				String[] layusprsel=null;
				if(renderRequest.getPreferences().getValue("courseTemplates", null)!=null&&renderRequest.getPreferences().getValue("courseTemplates", null).length()>0)
				{
						layusprsel=renderRequest.getPreferences().getValue("courseTemplates", "").split(",");
				}
				String[] lspList=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getLmsTemplates().split(",");
				if(layusprsel!=null && layusprsel.length>0)
				{
					lspList=layusprsel;
				}
								
				if(lspList.length>1){
					List<LayoutSetPrototype> prototypeList = new ArrayList<LayoutSetPrototype>();
					for(String lspId: lspList){
						prototypeList.add(LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.parseLong(lspId)));						
					}

					renderRequest.setAttribute("lspList", prototypeList);
					renderRequest.setAttribute("viewTemplateSelector", true);
				}else{
					LayoutSetPrototype lsp=LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.parseLong(lspList[0]));
					renderRequest.setAttribute("lspId", lsp.getLayoutSetPrototypeId());
					renderRequest.setAttribute("viewTemplateSelector", false);
				}
								
						
				SimpleDateFormat formatDay = new SimpleDateFormat("dd");
				formatDay.setTimeZone(timeZone);
				SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
				formatMonth.setTimeZone(timeZone);
				SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
				formatYear.setTimeZone(timeZone);
				SimpleDateFormat formatHour = new SimpleDateFormat("HH");
				formatHour.setTimeZone(timeZone);
				SimpleDateFormat formatMin = new SimpleDateFormat("mm");
				formatMin.setTimeZone(timeZone);
				Date today=course.getStartDate();
				if(Validator.isNull(today)){
					today = new Date();
				}
				int startDay=Integer.parseInt(formatDay.format(today));
				int startMonth=Integer.parseInt(formatMonth.format(today))-1;
				int startYear=Integer.parseInt(formatYear.format(today));
				int startHour=Integer.parseInt(formatHour.format(today));
				int startMin=Integer.parseInt(formatMin.format(today));
				
				today = course.getEndDate();
				if(Validator.isNull(today)){
					today = new Date();
				}
				
				int endDay=Integer.parseInt(formatDay.format(today));
				int endMonth=Integer.parseInt(formatMonth.format(today))-1;
				int endYear=Integer.parseInt(formatYear.format(today));
				int endHour=Integer.parseInt(formatHour.format(today));
				int endMin=Integer.parseInt(formatMin.format(today));
				//Inscription Date
				renderRequest.setAttribute("startDay", startDay);
				renderRequest.setAttribute("startMonth", startMonth);
				renderRequest.setAttribute("startYear", startYear);
				renderRequest.setAttribute("startHour", startHour);
				renderRequest.setAttribute("startMin", startMin);
				renderRequest.setAttribute("defaultStartYear", LiferaylmsUtil.defaultStartYear);
				renderRequest.setAttribute("defaultEndYear", LiferaylmsUtil.defaultEndYear);
				renderRequest.setAttribute("endDay", endDay);
				renderRequest.setAttribute("endMonth", endMonth);
				renderRequest.setAttribute("endYear", endYear);
				renderRequest.setAttribute("endHour", endHour);
				renderRequest.setAttribute("endMin", endMin);
				
				
				today = course.getExecutionStartDate();
				
				if(Validator.isNull(today)){
					today = new Date();
				}
				startDay=Integer.parseInt(formatDay.format(today));
				startMonth=Integer.parseInt(formatMonth.format(today))-1;
				startYear=Integer.parseInt(formatYear.format(today));
				startHour=Integer.parseInt(formatHour.format(today));
				startMin=Integer.parseInt(formatMin.format(today));
				
				today = course.getExecutionEndDate();
				
				if(Validator.isNull(today)){
					today = new Date();
				}
				endDay=Integer.parseInt(formatDay.format(today));
				endMonth=Integer.parseInt(formatMonth.format(today))-1;
				endYear=Integer.parseInt(formatYear.format(today));
				endHour=Integer.parseInt(formatHour.format(today));
				endMin=Integer.parseInt(formatMin.format(today));
				
				//Execution Date
				renderRequest.setAttribute("startExecutionDay", startDay);
				renderRequest.setAttribute("startExecutionMonth", startMonth);
				renderRequest.setAttribute("startExecutionYear", startYear);
				renderRequest.setAttribute("startExecutionHour", startHour);
				renderRequest.setAttribute("startExecutionMin", startMin);
				renderRequest.setAttribute("defaultStartYear", LiferaylmsUtil.defaultStartYear);
				renderRequest.setAttribute("defaultEndYear", LiferaylmsUtil.defaultEndYear);
				renderRequest.setAttribute("endExecutionDay", endDay);
				renderRequest.setAttribute("endExecutionMonth", endMonth);
				renderRequest.setAttribute("endExecutionYear", endYear);
				renderRequest.setAttribute("endExecutionHour", endHour);
				renderRequest.setAttribute("endExecutionMin", endMin);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		
		include(this.cloneJSP, renderRequest, renderResponse);
	}
	
	private void searchCourses(RenderRequest renderRequest,RenderResponse renderResponse){
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long courseId = ParamUtil.getLong(renderRequest, "courseId", 0);
		String search = ParamUtil.getString(renderRequest, "search","");
		String freetext = ParamUtil.getString(renderRequest, "freetext","");
		String tags = ParamUtil.getString(renderRequest, "tags","");
		int state = ParamUtil.getInteger(renderRequest, "state",WorkflowConstants.STATUS_APPROVED);
		long selectedGroupId = ParamUtil.get(renderRequest,"selectedGroupId",-1);
		long catId=ParamUtil.getLong(renderRequest, "categoryId",0);
		long columnId = ParamUtil.getLong(renderRequest, "columnId");
		String expandoValue = ParamUtil.getString(renderRequest, "expandoValue", "");
		
		int dateMonthStart = ParamUtil.getInteger(renderRequest, "dateMonthStart",Calendar.getInstance().get(Calendar.MONTH));
		int dateDayStart = ParamUtil.getInteger(renderRequest, "dateDayStart",Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		int dateYearStart = ParamUtil.getInteger(renderRequest, "dateYearStart",Calendar.getInstance().get(Calendar.YEAR));
		int dateMonthEnd = ParamUtil.getInteger(renderRequest, "dateMonthEnd",Calendar.getInstance().get(Calendar.MONTH));
		int dateDayEnd = ParamUtil.getInteger(renderRequest, "dateDayEnd",Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		int dateYearEnd = ParamUtil.getInteger(renderRequest, "dateYearEnd",Calendar.getInstance().get(Calendar.YEAR));
		boolean startDateFilter = ParamUtil.getBoolean(renderRequest, "startDateFilter",false);
		boolean endDateFilter = ParamUtil.getBoolean(renderRequest, "endDateFilter",false);

		Calendar startDate = Calendar.getInstance();
		if(startDateFilter){
			startDate.set(Calendar.YEAR, dateYearStart);
			startDate.set(Calendar.MONTH, dateMonthStart);
			startDate.set(Calendar.DAY_OF_MONTH, dateDayStart);
			startDate.set(Calendar.HOUR, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
		}else{
			startDate = null;
		}

		Calendar endDate = Calendar.getInstance();
		if(endDateFilter){
			endDate.set(Calendar.YEAR, dateYearEnd);
			endDate.set(Calendar.MONTH, dateMonthEnd);
			endDate.set(Calendar.DAY_OF_MONTH, dateDayEnd);
			endDate.set(Calendar.HOUR, 23);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
		}else{
			endDate = null;
		}
		
		
		//*****************************************Cogemos los tags************************************//
		String[] tagsSel = null;
		long[] tagsSelIds = null;
		try {
			ServiceContext sc = ServiceContextFactory.getInstance(renderRequest);
			tagsSel = sc.getAssetTagNames();

			if(tagsSel != null){
				long[] groups = new long[]{themeDisplay.getScopeGroupId()};
				tagsSelIds = AssetTagLocalServiceUtil.getTagIds(groups, tagsSel);
			}
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}

		//*****************************************Si se muestra el filtro de grupos, cogemos los grupos*****//
		if(Boolean.parseBoolean(renderRequest.getPreferences().getValue("showGroupFilter", "false"))){
			List<Group> courseGroups = CourseLocalServiceUtil.getDistinctCourseGroups(themeDisplay.getCompanyId());
			renderRequest.setAttribute("listGroups", courseGroups);
			selectedGroupId = selectedGroupId == -1 ? 0 : selectedGroupId;
			renderRequest.setAttribute("selectedGroupId", selectedGroupId);
		}
		
		//*****************************************Cogemos las categorias************************************//
		Enumeration<String> pnames =renderRequest.getParameterNames();
		ArrayList<String> tparams = new ArrayList<String>();
		ArrayList<Long> assetCategoryIds = new ArrayList<Long>();
		


		while(pnames.hasMoreElements()){
			String name = pnames.nextElement();
			if(name.length()>16&&name.substring(0,16).equals("assetCategoryIds")){
				tparams.add(name);
				String value = renderRequest.getParameter(name);
				String[] values = value.split(",");
				for(String valuet : values){
					try{
						assetCategoryIds.add(Long.parseLong(valuet));
					}catch(Exception e){
					}
				}
				
			}
		}
		
		//***************************Si estÃ¡s buscando te guarda los parÃ¡metros en la sesiÃ³n, si no estÃ¡s buscando te los coge de la sesiÃ³n****************************//

		PortletSession portletSession = renderRequest.getPortletSession();
		String prefix = "";
		if(courseId > 0){
			prefix = String.valueOf(courseId);
		}
		if(ParamUtil.getString(renderRequest, "search").equals("search")){
			portletSession.setAttribute(prefix+"freetext", freetext);
			portletSession.setAttribute(prefix+"state", state);
			portletSession.setAttribute(prefix+"assetCategoryIds", assetCategoryIds);
			portletSession.setAttribute(prefix+"assetTagIds", tagsSelIds);
			portletSession.setAttribute(prefix+"columnId", columnId);
			portletSession.setAttribute(prefix+"expandoValue",expandoValue);
			
			portletSession.setAttribute(prefix+"dateMonthStart", dateMonthStart);
			portletSession.setAttribute(prefix+"dateDayStart", dateDayStart);
			portletSession.setAttribute(prefix+"dateYearStart", dateYearStart);
			portletSession.setAttribute(prefix+"dateMonthEnd", dateMonthEnd);
			portletSession.setAttribute(prefix+"dateDayEnd", dateDayEnd);
			portletSession.setAttribute(prefix+"dateYearEnd", dateYearEnd);
			portletSession.setAttribute(prefix+"startDateFilter", startDateFilter);
			portletSession.setAttribute(prefix+"endDateFilter", endDateFilter);

		}else{
			try{
				String freetextTemp = (String)portletSession.getAttribute(prefix+"freetext");
				if(freetextTemp!=null){
					freetext = freetextTemp;
				}
			}catch(Exception e){
				log.debug(e);
			}
			try{
				ArrayList<Long> assetCategoryIdsTemp = (ArrayList<Long>)portletSession.getAttribute(prefix+"assetCategoryIds");
				if(assetCategoryIdsTemp!=null){
					assetCategoryIds = assetCategoryIdsTemp;
				}
			}catch(Exception e){
				log.debug(e);
			}
			try{
				Integer stateTemp = (Integer)portletSession.getAttribute(prefix+"state");
				if(stateTemp!=null){
					state = stateTemp;
				}
			}catch(Exception e){}
			try{
				Long columnIdTemp = (Long)portletSession.getAttribute(prefix+"columnId");
				if(columnIdTemp != null){
					columnId = columnIdTemp;
					String expandoValueTemp = (String)portletSession.getAttribute(prefix+"expandoValue");
					if(expandoValueTemp != null){
						expandoValue = expandoValueTemp;
					}
				}
			}catch(Exception e){}
		}
				
		long[] catIds=ParamUtil.getLongValues(renderRequest, "categoryIds");

		StringBuffer sb = new StringBuffer();
		for(long cateId : assetCategoryIds){
			sb.append(cateId);
			sb.append(",");
		}
		String catIdsText = sb.toString();

		if((catIds==null||catIds.length<=0)&&(assetCategoryIds!=null&&assetCategoryIds.size()>0)){
			catIds = new long[assetCategoryIds.size()];
			for(int i=0;i<assetCategoryIds.size();i++){
				catIds[i] = assetCategoryIds.get(i);
			}
		}
		
		
		//***********************  COGEMOS LAS PLANTILLAS ********************/
		
		// Templates
		String[] templates = getCourseTemplates(renderRequest.getPreferences(), themeDisplay.getCompanyId());
		
		PortletURL portletURL = renderResponse.createRenderURL();
		portletURL.setParameter("javax.portlet.action","doView");
		portletURL.setParameter("freetext",freetext);
		portletURL.setParameter("selectedGroupId", String.valueOf(selectedGroupId));
		portletURL.setParameter("state",String.valueOf(state));
		
		portletURL.setParameter(prefix+"dateMonthStart", String.valueOf(dateMonthStart));
		portletURL.setParameter(prefix+"dateDayStart", String.valueOf(dateDayStart));
		portletURL.setParameter(prefix+"dateYearStart", String.valueOf(dateYearStart));
		portletURL.setParameter(prefix+"dateMonthEnd", String.valueOf(dateMonthEnd));
		portletURL.setParameter(prefix+"dateDayEnd", String.valueOf(dateDayEnd));
		portletURL.setParameter(prefix+"dateYearEnd", String.valueOf(dateYearEnd));
		portletURL.setParameter(prefix+"startDateFilter", String.valueOf(startDateFilter));
		portletURL.setParameter(prefix+"endDateFilter", String.valueOf(endDateFilter));
	

		pnames =renderRequest.getParameterNames();
		while(pnames.hasMoreElements()){
			String name = pnames.nextElement();
			if(name.length()>16&&name.substring(0,16).equals("assetCategoryIds")){
				portletURL.setParameter(name,renderRequest.getParameter(name));
			}
		}
		for(String param : tparams){
			portletURL.setParameter(param,renderRequest.getParameter(param));
		}
		portletURL.setParameter("search","search");
		portletURL.setParameter("columnId", String.valueOf(columnId));
		portletURL.setParameter("expandoValue", expandoValue);
		if(courseId > 0){
			portletURL.setParameter("courseId", String.valueOf(courseId));
			portletURL.setParameter("view", "editions");
		}
		
		long[] categoryIds = ArrayUtil.toArray(assetCategoryIds.toArray(new Long[assetCategoryIds.size()]));
		
		int closed = -1;
		if(state!=WorkflowConstants.STATUS_ANY){
			if(state==WorkflowConstants.STATUS_APPROVED){
				closed = 0;
			}
			else if(state==WorkflowConstants.STATUS_INACTIVE){
				closed = 1;
			}
		}
		
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
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}

		long groupId = themeDisplay.getScopeGroupId();
		log.debug("SELECTED GROUP ID " + selectedGroupId);
		if(selectedGroupId>-1){
			groupId = selectedGroupId;
		}
		
		String emptyResultsMessage = "there-are-no-courses";
		if(courseId > 0){
			emptyResultsMessage = "there-are-no-editions";
		}
		
		SearchContainer<Course> searchContainer = new SearchContainer<Course>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
				SearchContainer.DEFAULT_DELTA, portletURL, 
				null, emptyResultsMessage);
		
		log.debug("freetext: " + freetext);
		log.debug("closed: " + closed);
		log.debug("categoryIds: " + categoryIds);
		log.debug("tagsSelIds: " + tagsSelIds);
		log.debug("templates: " + templates);
		log.debug("columnId: " + columnId);
		log.debug("expandoValue: " + expandoValue);
		log.debug("courseId: " + courseId);
		log.debug("themeDisplay.getCompanyId(): " + themeDisplay.getCompanyId());
		log.debug("groupId: " + groupId);
		log.debug("isAdmin: " + isAdmin);
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if(categoryIds != null && categoryIds.length > 0){
			params.put(CourseParams.PARAM_AND_CATEGORIES, categoryIds);
		}
		if(tagsSelIds != null && tagsSelIds.length > 0){
			params.put(CourseParams.PARAM_TAGS, tagsSelIds);
		}
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
		
		
		if (startDate != null){
			params.put(CourseParams.PARAM_EXECUTION_START_DATE, startDate.getTime());
		}
		
		if (endDate != null){
			params.put(CourseParams.PARAM_EXECUTION_END_DATE, endDate.getTime());
		}
		
		String orderByCol = ParamUtil.getString(renderRequest, "orderByCol");
		String orderByType = ParamUtil.getString(renderRequest, "orderByType");
		
		if (Validator.isNull(orderByCol) ||
			Validator.isNull(orderByType)){
			orderByCol = "title";
			orderByType = "asc";
		}
		
		OrderByComparator obc = null;
		if(Validator.isNotNull(orderByCol) && orderByCol.equals("title")){
			obc = new CourseOrderByTitle(themeDisplay, orderByType.equals("asc"));
		}else if(Validator.isNotNull(orderByCol) && orderByCol.equals("createDate")){
			obc = new CourseOrderByDate(orderByType.equals("asc"),"createDate");
		}else if(Validator.isNotNull(orderByCol) && orderByCol.equals("startDate")){
			obc = new CourseOrderByDate(orderByType.equals("asc"),"startDate");
		}else if(Validator.isNotNull(orderByCol) && orderByCol.equals("endDate")){
			obc = new CourseOrderByDate(orderByType.equals("asc"),"endDate");
		}else if(Validator.isNotNull(orderByCol) && orderByCol.equals("executionStartDate")){
			obc = new CourseOrderByDate(orderByType.equals("asc"),"executionStartDate");
		}else if(Validator.isNotNull(orderByCol) && orderByCol.equals("executionEndDate")){
			obc = new CourseOrderByDate(orderByType.equals("asc"),"executionEndDate");
		}
		
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);
		searchContainer.setOrderByComparator(obc);
		
		searchContainer.setResults(CourseLocalServiceUtil.searchCourses(themeDisplay.getCompanyId(), freetext, themeDisplay.getLanguageId(), state, courseId, groupId, params, 
				searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()));
		searchContainer.setTotal(CourseLocalServiceUtil.countCourses(themeDisplay.getCompanyId(), freetext, themeDisplay.getLanguageId(), state, courseId, groupId, params));
		
		renderRequest.setAttribute("searchContainer", searchContainer);
		renderRequest.setAttribute("catIds", catIds);
		renderRequest.setAttribute("noAssetCategoryIds", assetCategoryIds == null || assetCategoryIds.size() == 0);
		renderRequest.setAttribute("catId", catId);
		renderRequest.setAttribute("search", search);
		renderRequest.setAttribute("freetext", freetext);
		renderRequest.setAttribute("tags", tags);
		renderRequest.setAttribute("state", state);
		renderRequest.setAttribute("catIdsText", catIdsText);
		renderRequest.setAttribute("columnId", columnId);
		renderRequest.setAttribute("expandoValue", expandoValue);
		renderRequest.setAttribute("STATUS_APPROVED", WorkflowConstants.STATUS_APPROVED);
		renderRequest.setAttribute("STATUS_INACTIVE", WorkflowConstants.STATUS_INACTIVE);
		renderRequest.setAttribute("STATUS_ANY", WorkflowConstants.STATUS_ANY);
		
		renderRequest.setAttribute("yearRangeStart", Calendar.getInstance().get(Calendar.YEAR)-10);
		renderRequest.setAttribute("yearRangeEnd", Calendar.getInstance().get(Calendar.YEAR)+10);
		renderRequest.setAttribute("dateMonthStart", dateMonthStart);
		renderRequest.setAttribute("dateDayStart", dateDayStart);
		renderRequest.setAttribute("dateYearStart", dateYearStart);
		renderRequest.setAttribute("dateMonthEnd", dateMonthEnd);
		renderRequest.setAttribute("dateDayEnd", dateDayEnd);
		renderRequest.setAttribute("dateYearEnd", dateYearEnd);
		renderRequest.setAttribute("startDateFilter", startDateFilter);
		renderRequest.setAttribute("endDateFilter", endDateFilter);
		
		List<ExpandoColumn> listExpandos = null;
		try {
			listExpandos = ExpandoColumnLocalServiceUtil.getColumns(themeDisplay.getCompanyId(), Course.class.getName(), ExpandoTableConstants.DEFAULT_TABLE_NAME);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		if(Boolean.parseBoolean(renderRequest.getPreferences().getValue("showExpandos", "false")) || Boolean.parseBoolean(renderRequest.getPreferences().getValue("showExpandosEdition", "false"))){
			renderRequest.setAttribute("listExpandos", listExpandos);
		}
		try{
		//Creamos la lista para las columnas
		List<ExpandoColumn> expandoNames = new ArrayList<ExpandoColumn>();
		if(Validator.isNotNull(listExpandos) && listExpandos.size()>0) {
			String expandoName="";
			for (ExpandoColumn expandoCourse : listExpandos) {
				expandoName = expandoCourse.getName();
				if(((renderRequest.getPreferences().getValue("show" + expandoName, "false")).compareTo("true") == 0)) {
					expandoNames.add(expandoCourse);
				}
			}	
		}
		
		renderRequest.setAttribute("expandoNames", expandoNames);
		}catch(Exception e){
			e.printStackTrace();
		}
		//Ocultar o no las fechas de ejecuciÃ³n del curso (por defecto no se ocultan)
		renderRequest.setAttribute("hideExecutionDateCourseColumn", Boolean.parseBoolean(renderRequest.getPreferences().getValue("executionDateCourseColumn", "false")));
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
	
	public void copyEditions(ActionRequest actionRequest, ActionResponse actionResponse) {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);
			long courseId = ParamUtil.getLong(actionRequest, "courseId");
			
			boolean description = ParamUtil.getBoolean(actionRequest, "description");
			boolean summary = ParamUtil.getBoolean(actionRequest, "summary");
			boolean courseEvalId = ParamUtil.getBoolean(actionRequest, "courseEvalId");
			boolean calificationType = ParamUtil.getBoolean(actionRequest, "calificationType");
			boolean registrationType = ParamUtil.getBoolean(actionRequest, "registrationType");
			
			ExpandoTable table = ExpandoTableLocalServiceUtil.getTable(themeDisplay.getCompanyId(), PortalUtil.getClassNameId(Course.class), ExpandoTableConstants.DEFAULT_TABLE_NAME);
			List<ExpandoColumn> columns = ExpandoColumnLocalServiceUtil.getColumns(table.getTableId());
			
			List<Long> columnIds = new ArrayList<Long>();
			for(ExpandoColumn column: columns){
				if(ParamUtil.getBoolean(actionRequest, "expando_" + column.getColumnId())){
					columnIds.add(column.getColumnId());
				}
			}
			
			Course course = CourseLocalServiceUtil.getCourse(courseId);
			
			boolean activities = ParamUtil.getBoolean(actionRequest, "activities");
			
			long[] editionIds = StringUtil.split(ParamUtil.getString(actionRequest, "editionIds"),",",0L);
			
			CourseLocalServiceUtil.copyParentToEditions(themeDisplay.getUserId(),course, description, summary, courseEvalId, 
					calificationType, registrationType, columnIds, activities, editionIds, serviceContext);
		} catch (PortalException | SystemException e) {
			e.printStackTrace();
		}
	}
	
	public void importCourse(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		long groupId = ParamUtil.getLong(uploadRequest, "groupId");
		
		try {

			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			File file = uploadRequest.getFile("importFileName");
			if (!file.exists()) {
				//	log.debug("Import file does not exist");
				throw new LARFileException("Import file does not exist");
			}
			String portletId = (String) actionRequest.getAttribute(WebKeys.PORTLET_ID);
			

			Map<String,String[]> parameterMap = uploadRequest.getParameterMap();
			
			parameterMap.put(PortletDataHandlerKeys.PERMISSIONS, new String[]{"true"}); 
			parameterMap.put(PortletDataHandlerKeys.USER_PERMISSIONS,  new String[]{"true"});
			
			LayoutServiceUtil.importPortletInfo(themeDisplay.getLayout().getPlid(), groupId,portletId, parameterMap, file);
			
					
			addSuccessMessage(actionRequest, actionResponse);
			
			
			
			/* Si esta seleccionado el modo de borrar tenemos que progpagar borrado de icono de la clase*/
			
			if(uploadRequest.getParameter(PortletDataHandlerKeys.DELETE_PORTLET_DATA).equals("true")){
				Course c = CourseLocalServiceUtil.getCourseByGroupCreatedId(groupId);
				c.setIcon(0);
				CourseLocalServiceUtil.updateCourse(c);
			}
			
			SessionMessages.add(actionRequest, "import-course-ok");

		} catch (Exception e) {
			e.printStackTrace();
			if ((e instanceof LARFileException) || (e instanceof LARTypeException)) {

				SessionErrors.add(actionRequest, e.getClass().getName());

			} if(e.getMessage() != null && e.getMessage().indexOf(NoLearningActivityTypeActiveException.class.getName()) >= 0){
				e.printStackTrace();
				actionResponse.setRenderParameter("view", "import");
				actionResponse.setRenderParameter("groupId", String.valueOf(groupId));
				SessionErrors.add(actionRequest, "error-learning-activity-type");	
			}
			else {
				log.error(e, e);
				SessionErrors.add(actionRequest, LayoutImportException.class.getName());
			}
		}
	}

public void cloneCourse(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);	
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), actionRequest);
		
		long groupId  = ParamUtil.getLong(actionRequest, "groupId", 0);
	
		String newCourseName  = ParamUtil.getString(actionRequest, "newCourseName", "New course cloned");
		boolean cloneForum = ParamUtil.getBoolean(actionRequest, "cloneForum");
		boolean cloneDocuments = ParamUtil.getBoolean(actionRequest, "cloneDocuments");
		boolean cloneModuleClassification = ParamUtil.getBoolean(actionRequest, "cloneModuleClassification");
		boolean cloneActivityClassificationTypes = ParamUtil.getBoolean(actionRequest, "cloneActivityClassificationTypes");
		
		/*
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
		*/
		
		//Inscription Date
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
		
		startMonth = 	ParamUtil.getInteger(actionRequest, "startExecutionMon");
		startYear = 	ParamUtil.getInteger(actionRequest, "startExecutionYear");
		startDay = 		ParamUtil.getInteger(actionRequest, "startExecutionDay");
		startHour = 	ParamUtil.getInteger(actionRequest, "startExecutionHour");
		startMinute = 	ParamUtil.getInteger(actionRequest, "startExecutionMin");
		startAMPM = 	ParamUtil.getInteger(actionRequest, "startExecutionAMPM");
		if(startAMPM > 0) {
			startHour += 12;
		}
		Date startExecutionDate = PortalUtil.getDate(startMonth, startDay, startYear, startHour, startMinute, themeDisplay.getTimeZone(), EntryDisplayDateException.class);
		
		stopMonth = 	ParamUtil.getInteger(actionRequest, "stopExecutionMon");
		stopYear = 		ParamUtil.getInteger(actionRequest, "stopExecutionYear");
		stopDay = 		ParamUtil.getInteger(actionRequest, "stopExecutionDay");
		stopHour = 		ParamUtil.getInteger(actionRequest, "stopExecutionHour");
		stopMinute = 	ParamUtil.getInteger(actionRequest, "stopExecutionMin");
		stopAMPM = 		ParamUtil.getInteger(actionRequest, "stopExecutionAMPM");
		if (stopAMPM > 0) {
			stopHour += 12;
		}
		Date endExecutionDate = PortalUtil.getDate(stopMonth, stopDay, stopYear, stopHour, stopMinute, themeDisplay.getTimeZone(), EntryDisplayDateException.class);
		
		
		
		//CloneCourseThread cloneThread = new CloneCourseThread(groupId, newCourseName, themeDisplay, startDate, endDate, serviceContext);
		//Thread thread = new Thread(cloneThread);
		//thread.start();
		
		// Comprobaciones antes del proceso
		boolean errors = false;
		if(endDate.before(startDate)){
			SessionErrors.add(actionRequest, "courseadmin.clone.error.dateinterval");
			errors = true;
		}
		
		boolean visible = ParamUtil.getBoolean(actionRequest, "visible", false);

		
		Group group = null;
		try{
			group = GroupLocalServiceUtil.getGroup(themeDisplay.getCompanyId(), newCourseName);
		}catch(NoSuchGroupException e){
			group = null;
			if(log.isDebugEnabled())
				e.printStackTrace();
		}
		if(!errors){
			if(group != null) {
				SessionErrors.add(actionRequest, "courseadmin.clone.error.duplicateName");
				errors = true;
			} else {
				
				
				AsynchronousProcessAudit process = AsynchronousProcessAuditLocalServiceUtil.addAsynchronousProcessAudit(themeDisplay.getCompanyId(), themeDisplay.getUserId(), Course.class.getName(), "liferay/lms/courseClone");
				
				Message message=new Message();
				message.put("asynchronousProcessAuditId", process.getAsynchronousProcessAuditId());
				message.put("groupId",groupId);
				message.put("newCourseName",newCourseName);
				message.put("themeDisplay",themeDisplay);
				/*message.put("startDate",startDate);
				message.put("endDate",endDate);*/
				message.put("startDate",startDate);
				message.put("endDate",endDate);
				message.put("startExecutionDate",startExecutionDate);
				message.put("endExecutionDate",endExecutionDate);
				message.put("serviceContext",serviceContext);
				message.put("visible",visible);
				message.put("cloneForum", cloneForum);
				message.put("cloneDocuments", cloneDocuments);
				message.put("cloneModuleClassification", cloneModuleClassification);
				message.put("cloneActivityClassificationTypes", cloneActivityClassificationTypes);
				MessageBusUtil.sendMessage("liferay/lms/courseClone", message);
				SessionMessages.add(actionRequest, "courseadmin.clone.confirmation.success");
			}
		}
		if(errors){
			actionResponse.sendRedirect(ParamUtil.getString(actionRequest, "redirect"));
		}else{
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(groupId);
			if(course!=null){
				if(course.getParentCourseId()>0){
					actionResponse.setRenderParameter("view", "editions");
					actionResponse.setRenderParameter("courseId", String.valueOf(course.getParentCourseId()));
				}
			}
		}

	}
	
	public void createEdition(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);	
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), actionRequest);
		boolean isLinked = ParamUtil.getBoolean(actionRequest, "linkedCourse", false);
		long parentCourseId = ParamUtil.getLong(actionRequest, "parentCourseId",0);
		String friendlyURL = ParamUtil.getString(actionRequest, "editionFriendlyURL");
		String newEditionName  = ParamUtil.getString(actionRequest, "newCourseName", "New edition");
		long editionLayoutId = ParamUtil.getLong(actionRequest, "courseTemplate");

		//Inscription Date
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
		
		
		
		startMonth = 	ParamUtil.getInteger(actionRequest, "startExecutionMon");
		startYear = 	ParamUtil.getInteger(actionRequest, "startExecutionYear");
		startDay = 		ParamUtil.getInteger(actionRequest, "startExecutionDay");
		startHour = 	ParamUtil.getInteger(actionRequest, "startExecutionHour");
		startMinute = 	ParamUtil.getInteger(actionRequest, "startExecutionMin");
		startAMPM = 	ParamUtil.getInteger(actionRequest, "startExecutionAMPM");
		if(startAMPM > 0) {
			startHour += 12;
		}
		Date startExecutionDate = PortalUtil.getDate(startMonth, startDay, startYear, startHour, startMinute, themeDisplay.getTimeZone(), EntryDisplayDateException.class);
		
		stopMonth = 	ParamUtil.getInteger(actionRequest, "stopExecutionMon");
		stopYear = 		ParamUtil.getInteger(actionRequest, "stopExecutionYear");
		stopDay = 		ParamUtil.getInteger(actionRequest, "stopExecutionDay");
		stopHour = 		ParamUtil.getInteger(actionRequest, "stopExecutionHour");
		stopMinute = 	ParamUtil.getInteger(actionRequest, "stopExecutionMin");
		stopAMPM = 		ParamUtil.getInteger(actionRequest, "stopExecutionAMPM");
		if (stopAMPM > 0) {
			stopHour += 12;
		}
		Date endExecutionDate = PortalUtil.getDate(stopMonth, stopDay, stopYear, stopHour, stopMinute, themeDisplay.getTimeZone(), EntryDisplayDateException.class);
		
		
		// Comprobaciones antes del proceso
		boolean errors = false;
		if(endDate.before(startDate)){
			SessionErrors.add(actionRequest, "date-interval");
			errors = true;
		}
		
		Group group = null;
		try{
			group = GroupLocalServiceUtil.getGroup(themeDisplay.getCompanyId(), newEditionName);
		}catch(NoSuchGroupException e){
			group = null;
			if(log.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		if(!errors){
			if(group != null) {
				SessionErrors.add(actionRequest, "duplicate-name");
				errors = true;
			} else {
				group=GroupLocalServiceUtil.fetchFriendlyURLGroup(themeDisplay.getCompanyId(), friendlyURL);
				if(group!=null){
					SessionErrors.add(actionRequest, "duplicate-friendly-url");
					errors = true;
				}else{
					
					AsynchronousProcessAudit process = AsynchronousProcessAuditLocalServiceUtil.addAsynchronousProcessAudit(themeDisplay.getCompanyId(), themeDisplay.getUserId(), Course.class.getName(), "liferay/lms/createEdition");
					
					Message message=new Message();
					message.put("asynchronousProcessAuditId", process.getAsynchronousProcessAuditId());
					message.put("parentCourseId", parentCourseId);
					message.put("newEditionName",newEditionName);
					message.put("themeDisplay",themeDisplay);
					message.put("startDate",startDate);
					message.put("endDate",endDate);
					message.put("startExecutionDate",startExecutionDate);
					message.put("endExecutionDate",endExecutionDate);
					message.put("editionFriendlyURL",friendlyURL);
					message.put("isLinked",isLinked);
					message.put("serviceContext",serviceContext);
					message.put("editionLayoutId", editionLayoutId);
					MessageBusUtil.sendMessage("liferay/lms/createEdition", message);
			
				}
						
			}
		}
		
		if(errors){
			actionResponse.setRenderParameter("view", "new-edition");
			actionResponse.setRenderParameter("courseId", String.valueOf(parentCourseId));
			actionResponse.setRenderParameter("courseTypeId", ParamUtil.getString(actionRequest, "courseTypeId"));
		}else{
			SessionMessages.add(actionRequest, "course-admin.confirmation.new-edition-success");
			actionResponse.setRenderParameter("courseId", String.valueOf(parentCourseId));
			actionResponse.setRenderParameter("view", "editions");
		}
	}
	
	
	public void exportCourse(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);	
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), actionRequest);
		
		long groupId  = ParamUtil.getLong(actionRequest, "groupId", 0);
		String fileName  = ParamUtil.getString(actionRequest, "exportFileName", "New course exported");
		if(log.isDebugEnabled()){
			log.debug("groupId:"+groupId);
			log.debug("fileName:"+fileName);
		}

		AsynchronousProcessAudit process = AsynchronousProcessAuditLocalServiceUtil.addAsynchronousProcessAudit(themeDisplay.getCompanyId(), themeDisplay.getUserId(), Course.class.getName(), "liferay/lms/courseExport");
		
		Message message=new Message();
		message.put("asynchronousProcessAuditId", process.getAsynchronousProcessAuditId());
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
	
	//--ImportaciÃ³n ediciones
	public void importEditions(ActionRequest request, ActionResponse response) throws PortalException, SystemException  {
		if(log.isDebugEnabled())log.debug(" ::importEditions:: ");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), request);
		PortletSession session = request.getPortletSession(Boolean.TRUE);
		long previousFormDate = GetterUtil.getLong(session.getAttribute("formDate"));
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		
		String fileName = uploadPortletRequest.getFileName("fileName");
		File file = uploadPortletRequest.getFile("fileName");
		long editionLayoutId = ParamUtil.getLong(uploadPortletRequest, "courseTemplate", 0);
		long parentCourseId = ParamUtil.getLong(uploadPortletRequest, "courseId", 0);
		long formDate = GetterUtil.getLong(uploadPortletRequest.getParameter("formDate"));
		if(log.isDebugEnabled()) {
			log.debug(" ::importEditions:: serviceContext OK :: "+ Validator.isNotNull(serviceContext));
			log.debug(" ::importEditions:: fileName :: "+ fileName);
			log.debug(" ::importEditions:: editionLayoutId :: "+ editionLayoutId);
			log.debug(" ::importEditions:: parentCourseId :: "+ parentCourseId);
			log.debug(" ::importEditions:: formDate :: "+ formDate);
			log.debug(" ::importEditions:: previousFormDate :: "+ previousFormDate);
		}
		
		if(!Validator.equals(formDate, previousFormDate)){
			//Guardar el formDate en session
			session.setAttribute("formDate", String.valueOf(formDate));
			
			if(Validator.isNotNull(fileName)){
				try {
					String idThread = UUID.randomUUID().toString();
					if(log.isDebugEnabled()) log.debug("idThread: " + idThread);		
					ImportEditionsThread thread = new ImportEditionsThread(themeDisplay, serviceContext,  idThread, file, editionLayoutId, parentCourseId);
					ReportThreadMapper.addThread(idThread, thread);
					response.setRenderParameter("UUID", idThread);
				} catch (PortalException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		}
		response.setRenderParameter("courseId", String.valueOf(parentCourseId));
		response.setRenderParameter("view", "editions");
	}
	
	
	
	public void importUsersCourse(ActionRequest request, ActionResponse response)throws Exception {
		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), request);
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		log.debug("importUsersCourse 1");
		
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);
		
		long courseId = ParamUtil.getLong(uploadRequest, "courseId", 0);
		long roleId = ParamUtil.getLong(uploadRequest, "roleId", 0);
		
		try{
			
			String fileName = uploadRequest.getFileName("fileName");
				
			log.debug("importUsersCourse 2 - fileName: " + fileName);		
			
			String idHilo = UUID.randomUUID().toString();
			log.debug("idHilo: " + idHilo);				
			InputStream csvFile = uploadRequest.getFileAsStream("fileName");
			File file =uploadRequest.getFile("fileName");
			
			PortletPreferences preferences;
			String portletResource = ParamUtil.getString(request, "portletResource");
			if (Validator.isNotNull(portletResource)) {
				preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
			}else{
				preferences = request.getPreferences();
			}
			
			ImportUsersCourseThread hilo = new ImportUsersCourseThread(themeDisplay, idHilo, getPortletConfig(), fileName, file, serviceContext, csvFile, preferences, request);
			ImportUsersCourseThreadMapper.addThread(idHilo, hilo);
			response.setRenderParameter("UUID", idHilo);				
			response.setRenderParameter("courseId", String.valueOf(courseId));
			response.setRenderParameter("roleId", String.valueOf(roleId));
			response.setRenderParameter("view", "import-users");
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	
	//Importacion de usuarios en csv
	public void readImportFromCsv(ActionRequest actionRequest, ActionResponse actionResponse){
		
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		log.debug("::Import Users From Csv::");
		
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		
		String fileName = uploadPortletRequest.getFileName("fileName");
		String importType = ParamUtil.getString(uploadPortletRequest, "importType", StringPool.BLANK);
		long roleId = ParamUtil.getLong(uploadPortletRequest, "importAssignUsersRole", -1);
		String authType = PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_TYPE);
		
		if(log.isDebugEnabled()){
			log.debug("::readFile::fileName: "+ fileName);
			log.debug("::readFile::importType: "+ importType);
			log.debug("::readFile::roleId: "+ roleId);
			log.debug("::readFile::authType: "+ authType);
		}
		
		if(roleId==-1){
			
			SessionErrors.add(actionRequest, "import.csv.users.role-required");
			log.error("::Role required::");
			
		} else if(Validator.isNull(fileName)) {
			
			SessionErrors.add(actionRequest, "import.csv.users.file-required");
			log.error("::File required::");
			
		} else if(Validator.isNull(importType) || StringPool.BLANK.equals(importType)){
				
				SessionErrors.add(actionRequest,  "import.csv.users.error");
				log.error(":: ImportType ?? ::");
		
		} else {
				
			String idThread = UUID.randomUUID().toString();
			log.debug("idThread: " + idThread);		
			
			InputStream file = null;
			try {
				file = uploadPortletRequest.getFileAsStream("fileName");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(uploadPortletRequest.getFile("fileName").length()> 2 * 1024 * 1024){
				
				SessionErrors.add(actionRequest, "import.csv.users.bad-format.size");
			
			} else 	if (!fileName.endsWith(".csv")) { 
				
				SessionErrors.add(actionRequest, "import.csv.users.bad-format");	
			
			} else {
			
				ImportCsvThread thread = null;
				
				switch (importType) {
				
					case "assignUsers":
						thread = new ImportCsvAssignUsersThread(roleId, authType, file, idThread, themeDisplay);
						break;
					
					case "unassignUsers":
						thread = new ImportCsvUnassignUsersThread(roleId, authType, file, idThread, themeDisplay);
						break;
		
					default:
						break;
				}
				
				ImportCsvThreadMapper.addThread(idThread, thread);
				actionResponse.setRenderParameter("UUID", idThread);
				actionResponse.setRenderParameter("importType", importType);
			}
		}
	}
	
	
	//---Resource
	@Override
	public void serveResource(ResourceRequest request, ResourceResponse response) throws IOException, PortletException {

		log.debug("serveResource");
		if(request.getResourceID() != null){
			log.debug("request.getResourceID(): " + request.getResourceID());
		}
		
		String action = ParamUtil.getString(request, "action");
		String uuid = ParamUtil.getString(request, "UUID");
		log.debug("action: "+action);
		log.debug("uuid "+uuid);
		
		String fileReport = ParamUtil.getString(request, "fileReport", null);
		if (fileReport != null){
			File file = new File(fileReport);
			String exportFileReport = "report-importUsersCourse.csv";	
			int length   = 0;			 
			response.setContentType(ParamUtil.getString(request, "contentType"));
			response.setContentLength((int)file.length());
			response.addProperty(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + exportFileReport);
			
			OutputStream out = response.getPortletOutputStream();
			
			byte[] byteBuffer = new byte[4096];
	        DataInputStream in = new DataInputStream(new FileInputStream(file));
	        
	        // reads the file's bytes and writes them to the response stream
	        while ((in != null) && ((length = in.read(byteBuffer)) != -1)){
	        	out.write(byteBuffer,0,length);
	        }		
			
			out.flush();
			out.close();
			in.close();
		}else{
		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			
			if(Validator.isNotNull(request.getResourceID()) && request.getResourceID().equals("importUsersFromCsvExample")){
				log.debug("::Import Users From CSV Example::");
				UsersImportExport.importUsersFromCsvExample(request, response);
			} else if(Validator.isNotNull(request.getResourceID()) && request.getResourceID().equals("importUsersFromCsv")){
				log.debug("::Import Users From CSV::");
				UsersImportExport.importUsersFromCsv(request, response);
			} else if(request.getResourceID() != null && request.getResourceID().equals("importEditionsResultsReport")){
				EditionsImportExport.generateImportReport(request, response);
			}else if(request.getResourceID() != null && request.getResourceID().equals("exportEditions")){
				EditionsImportExport.generateReportEditions(request, response, themeDisplay);
			} else if (request.getResourceID() != null && request.getResourceID().equals("importEditionsExample")){
				EditionsImportExport.generateEditionsExampleFile(request, response, themeDisplay);
			}else if (request.getResourceID() != null && request.getResourceID().equals("importUsersCourseReport")){
				
				log.debug("serveResource - else - action: " + action);
				
				if(Validator.isNotNull(uuid) && action!=null && action.equals("importUsersCourseReport")){
					
					String courseId = ParamUtil.getString(request, "courseId");
					String roleId = ParamUtil.getString(request, "roleId");
					log.debug("courseId: "+courseId);
					log.debug("roleId: "+roleId);
					
					
					response.setContentType("application/json");
					JSONObject oreturned = JSONFactoryUtil.createJSONObject();
					
					log.debug(":::importUsersCourseReport:::uuid::"+uuid);
					if(uuid!=null){
						boolean finished = ImportUsersCourseThreadMapper.threadFinished(uuid);
						oreturned.put("finished", finished);
						log.debug(":::importUsersCourseReport:::not finished");
						if(finished){
							log.debug(":::importUsersCourseReport:::FINISHED["+uuid+"]");
							oreturned.put("fileReport", ImportUsersCourseThreadMapper.getThreadFileReport(uuid));
							oreturned.put("contentType", "application/csv");
							oreturned.put("action", action);
							oreturned.put("UUID", uuid);
							oreturned.put("courseId", courseId);
							oreturned.put("roleId", roleId);
							
							String result = "";
							result += "<BR>Líneas procesadas: " + ImportUsersCourseThreadMapper.getThreadLines(uuid) +"<BR>";
							result += "Usuarios inscritos: " + ImportUsersCourseThreadMapper.getUsersInscripted(uuid) +"<BR>";	
							oreturned.put("result", result);
							
							String sErrors = "";
							List<String> lErrors = ImportUsersCourseThreadMapper.getThreadErrors(uuid);
							if (lErrors != null && lErrors.size() > 0){
								for (String sError : lErrors){
									sErrors += sError +"<BR>";
								}
							}					
							oreturned.put("errors", sErrors);
							
							ImportUsersCourseThreadMapper.unlinkThread(uuid);
						}else{
							oreturned.put("progress", ImportUsersCourseThreadMapper.getProgress(uuid));
						}
					}
					try {
						PrintWriter out = response.getWriter();
						out.print(oreturned.toString());
						out.flush();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else if(action.equals("searchEditions")){
				log.debug(":::serve resource search users");
				
				long courseId = ParamUtil.getLong(request, "courseId");
				
				String freetext = ParamUtil.getString(request, "freetext","");
				
				int dateMonthStart = ParamUtil.getInteger(request, "dateMonthStart",Calendar.getInstance().get(Calendar.MONTH));
				int dateDayStart = ParamUtil.getInteger(request, "dateDayStart",Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
				int dateYearStart = ParamUtil.getInteger(request, "dateYearStart",Calendar.getInstance().get(Calendar.YEAR));
				int dateMonthEnd = ParamUtil.getInteger(request, "dateMonthEnd",Calendar.getInstance().get(Calendar.MONTH));
				int dateDayEnd = ParamUtil.getInteger(request, "dateDayEnd",Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
				int dateYearEnd = ParamUtil.getInteger(request, "dateYearEnd",Calendar.getInstance().get(Calendar.YEAR));
				boolean startDateFilter = ParamUtil.getBoolean(request, "startDateFilter",false);
				boolean endDateFilter = ParamUtil.getBoolean(request, "endDateFilter",false);

				Calendar startDate = Calendar.getInstance();
				if(startDateFilter){
					startDate.set(Calendar.YEAR, dateYearStart);
					startDate.set(Calendar.MONTH, dateMonthStart);
					startDate.set(Calendar.DAY_OF_MONTH, dateDayStart);
					startDate.set(Calendar.HOUR, 0);
					startDate.set(Calendar.MINUTE, 0);
					startDate.set(Calendar.SECOND, 0);
				}else{
					startDate = null;
				}

				Calendar endDate = Calendar.getInstance();
				if(endDateFilter){
					endDate.set(Calendar.YEAR, dateYearEnd);
					endDate.set(Calendar.MONTH, dateMonthEnd);
					endDate.set(Calendar.DAY_OF_MONTH, dateDayEnd);
					endDate.set(Calendar.HOUR, 23);
					endDate.set(Calendar.MINUTE, 59);
					endDate.set(Calendar.SECOND, 59);
				}else{
					endDate = null;
				}
				
				
				log.debug(":::show view users to send:::");		
				PortletURL iteratorURL = response.createRenderURL();
				iteratorURL.setParameter("ajaxAction", "searchEditions");
				iteratorURL.setParameter("view", "searchEditions");
				iteratorURL.setParameter("freetext", freetext);
				iteratorURL.setParameter("yearRangeStart", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-10));
				iteratorURL.setParameter("yearRangeEnd", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)+10));
				iteratorURL.setParameter("dateMonthStart", String.valueOf(dateMonthStart));
				iteratorURL.setParameter("dateDayStart", String.valueOf(dateDayStart));
				iteratorURL.setParameter("dateYearStart", String.valueOf(dateYearStart));
				iteratorURL.setParameter("dateMonthEnd", String.valueOf(dateMonthEnd));
				iteratorURL.setParameter("dateDayEnd", String.valueOf(dateDayEnd));
				iteratorURL.setParameter("dateYearEnd", String.valueOf(dateYearEnd));
				iteratorURL.setParameter("courseId", String.valueOf(courseId));
				
				request.setAttribute("yearRangeStart", Calendar.getInstance().get(Calendar.YEAR)-10);
				request.setAttribute("yearRangeEnd", Calendar.getInstance().get(Calendar.YEAR)+10);
				request.setAttribute("dateMonthStart", dateMonthStart);
				request.setAttribute("dateDayStart", dateDayStart);
				request.setAttribute("dateYearStart", dateYearStart);
				request.setAttribute("dateMonthEnd", dateMonthEnd);
				request.setAttribute("dateDayEnd", dateDayEnd);
				request.setAttribute("dateYearEnd", dateYearEnd);
				request.setAttribute("freetext", freetext);
				request.setAttribute("startDateFilter", startDateFilter);
				request.setAttribute("endDateFilter", endDateFilter);
				
				
				SearchContainer<Course> searchContainer = new SearchContainer<Course>(request, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
						10, iteratorURL, null, null);
						
				searchContainer.setHover(false);
				searchContainer.setDeltaConfigurable(false);
				log.debug("****************DELTA 2 RENDER: "+searchContainer.getDelta());
				searchContainer.setDelta(10);
				
				LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
				
				if (startDate != null){
					params.put(CourseParams.PARAM_EXECUTION_START_DATE, startDate.getTime());
				}
				
				if (endDate != null){
					params.put(CourseParams.PARAM_EXECUTION_END_DATE, endDate.getTime());
				}
				
				searchContainer.setResults(CourseLocalServiceUtil.searchCourses(themeDisplay.getCompanyId(), freetext, themeDisplay.getLanguageId(), 
						WorkflowConstants.STATUS_APPROVED, courseId, 0, params, 
						searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()));
				searchContainer.setTotal(CourseLocalServiceUtil.countCourses(themeDisplay.getCompanyId(), freetext, themeDisplay.getLanguageId(), 
						WorkflowConstants.STATUS_APPROVED, courseId, 0, params));

				request.setAttribute("searchContainer", searchContainer);
				
				log.info("Total:"+searchContainer.getTotal());
				
				PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher( "/html/courseadmin/searcheditions.jsp" );
				
				request.setAttribute("namespace", response.getNamespace());
				dispatcher.include( request, response );
			}else if(action.equals("addAllEditions")){
				
				System.out.println("addAllEditions");
				
				long courseId = ParamUtil.getLong(request, "courseId");
				
				String freetext = ParamUtil.getString(request, "freetext","");
				
				int dateMonthStart = ParamUtil.getInteger(request, "dateMonthStart",Calendar.getInstance().get(Calendar.MONTH));
				int dateDayStart = ParamUtil.getInteger(request, "dateDayStart",Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
				int dateYearStart = ParamUtil.getInteger(request, "dateYearStart",Calendar.getInstance().get(Calendar.YEAR));
				int dateMonthEnd = ParamUtil.getInteger(request, "dateMonthEnd",Calendar.getInstance().get(Calendar.MONTH));
				int dateDayEnd = ParamUtil.getInteger(request, "dateDayEnd",Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
				int dateYearEnd = ParamUtil.getInteger(request, "dateYearEnd",Calendar.getInstance().get(Calendar.YEAR));
				boolean startDateFilter = ParamUtil.getBoolean(request, "startDateFilter",false);
				boolean endDateFilter = ParamUtil.getBoolean(request, "endDateFilter",false);

				Calendar startDate = Calendar.getInstance();
				if(startDateFilter){
					startDate.set(Calendar.YEAR, dateYearStart);
					startDate.set(Calendar.MONTH, dateMonthStart);
					startDate.set(Calendar.DAY_OF_MONTH, dateDayStart);
					startDate.set(Calendar.HOUR, 0);
					startDate.set(Calendar.MINUTE, 0);
					startDate.set(Calendar.SECOND, 0);
				}else{
					startDate = null;
				}

				Calendar endDate = Calendar.getInstance();
				if(endDateFilter){
					endDate.set(Calendar.YEAR, dateYearEnd);
					endDate.set(Calendar.MONTH, dateMonthEnd);
					endDate.set(Calendar.DAY_OF_MONTH, dateDayEnd);
					endDate.set(Calendar.HOUR, 23);
					endDate.set(Calendar.MINUTE, 59);
					endDate.set(Calendar.SECOND, 59);
				}else{
					endDate = null;
				}
				
				LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
				
				if (startDate != null){
					params.put(CourseParams.PARAM_EXECUTION_START_DATE, startDate.getTime());
				}
				
				if (endDate != null){
					params.put(CourseParams.PARAM_EXECUTION_END_DATE, endDate.getTime());
				}
				
				List<Course> courses = CourseLocalServiceUtil.searchCourses(themeDisplay.getCompanyId(), freetext, themeDisplay.getLanguageId(), 
						WorkflowConstants.STATUS_APPROVED, courseId, 0, params, 
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
				
				response.setContentType("application/json");
				JSONObject oreturned = JSONFactoryUtil.createJSONObject();
				
				JSONArray editions = JSONFactoryUtil.createJSONArray();
				JSONObject editionJSON = null;
				
				for(Course course: courses){
					editionJSON = JSONFactoryUtil.createJSONObject();
					editionJSON.put("title", course.getTitle(themeDisplay.getLocale()));
					editionJSON.put("editionId", course.getCourseId());
					
					editions.put(editionJSON);
				}
				
				System.out.println("editions: " + editions);
				
				oreturned.put("editions", editions);
				
				try {
					PrintWriter out = response.getWriter();
					out.print(oreturned.toString());
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			super.serveResource(request, response);
		}
	}
	
	protected void doDispatch(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		log.debug("PARAM "+ParamUtil.getString(renderRequest, "ajaxAction"));
		if("searchEditions".equals(ParamUtil.getString(renderRequest, "ajaxAction"))){
				showViewCopyParentToEditions(renderRequest,renderResponse,true);
		}
		super.doDispatch(renderRequest, renderResponse);
	}

}

