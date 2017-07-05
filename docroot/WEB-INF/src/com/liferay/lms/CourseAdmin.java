package com.liferay.lms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;

/**
 * Portlet implementation class CourseAdmin
 */
public class CourseAdmin extends BaseCourseAdminPortlet {
	
	private String viewJSP = null;
	private String exportJSP = null;
	private String importJSP = null;
	private String cloneJSP = null;
	private String configLmsPrefsJSP = null;
	
	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
		editCourseJSP =  getInitParameter("edit-course-template");
		roleMembersTabJSP =  getInitParameter("role-members-tab-template");
		exportJSP =  getInitParameter("export-template");
		importJSP =  getInitParameter("import-template");
		cloneJSP =  getInitParameter("clone-template");
		competenceTabJSP =  getInitParameter("competence-tab-template");
		importUsersJSP = getInitParameter("import-users-template");
		usersResultsJSP = getInitParameter("users-results-template");
		competenceResultsJSP = getInitParameter("competence-results-template");
		configLmsPrefsJSP = getInitParameter("config-lms-prefs");
	}
	
	private static Log log = LogFactoryUtil.getLog(CourseAdmin.class);
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		LmsPrefs lmsPrefs = null;
		try {
			lmsPrefs = LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId());
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(lmsPrefs != null){		
			String jsp = renderRequest.getParameter("view");
			if(log.isDebugEnabled())log.debug("VIEW "+jsp);
			try {
				if(jsp == null || "".equals(jsp)){
					showViewDefault(renderRequest, renderResponse);
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
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				showViewDefault(renderRequest, renderResponse);
			} catch (PortletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				showViewDefault(renderRequest, renderResponse);
			}
		}else{
			include(this.configLmsPrefsJSP, renderRequest, renderResponse);
		}
	}
	
	private void showViewDefault(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String search = ParamUtil.getString(renderRequest, "search","");
		String freetext = ParamUtil.getString(renderRequest, "freetext","");
		String tags = ParamUtil.getString(renderRequest, "tags","");
		int state = ParamUtil.getInteger(renderRequest, "state",WorkflowConstants.STATUS_APPROVED);

		long catId=ParamUtil.getLong(renderRequest, "categoryId",0);
		
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		//*****************************************Cogemos las categorias************************************//
		Enumeration<String> pnames =renderRequest.getParameterNames();
		ArrayList<String> tparams = new ArrayList<String>();
		ArrayList<Long> assetCategoryIds = new ArrayList<Long>();
		ArrayList<Long> assetTagIds = new ArrayList<Long>();


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
		
		//***************************Si estás buscando te guarda los parámetros en la sesión, si no estás buscando te los coge de la sesión****************************//

		PortletSession portletSession = renderRequest.getPortletSession();
		if(ParamUtil.getString(renderRequest, "search").equals("search")){
			portletSession.setAttribute("freetext", freetext);
			portletSession.setAttribute("state", state);
			portletSession.setAttribute("assetCategoryIds", assetCategoryIds);
			portletSession.setAttribute("assetTagIds", tagsSelIds);

		}else{
			try{
				String freetextTemp = (String)portletSession.getAttribute("freetext");
				if(freetextTemp!=null){
					freetext = freetextTemp;
				}
			}catch(Exception e){}
			try{
				ArrayList<Long> assetCategoryIdsTemp = (ArrayList<Long>)portletSession.getAttribute("assetCategoryIds");
				if(assetCategoryIdsTemp!=null){
					assetCategoryIds = assetCategoryIdsTemp;
				}
			}catch(Exception e){}
			try{
				ArrayList<Long> assetTagIdsTemp = (ArrayList<Long>)portletSession.getAttribute("assetTagIds");
				if(assetTagIdsTemp!=null){
					assetTagIds = assetTagIdsTemp;
				}
			}catch(Exception e){}
			try{
				Integer stateTemp = (Integer)portletSession.getAttribute("state");
				if(stateTemp!=null){
					state = stateTemp;
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
		
		PortletURL portletURL = renderResponse.createRenderURL();
		portletURL.setParameter("javax.portlet.action","doView");
		portletURL.setParameter("freetext",freetext);
		portletURL.setParameter("state",String.valueOf(state));

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
			isAdmin = RoleLocalServiceUtil.hasUserRole(themeDisplay.getUserId(), RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.ADMINISTRATOR).getRoleId());
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SearchContainer<Course> searchContainer = new SearchContainer<Course>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
				SearchContainer.DEFAULT_DELTA, portletURL, 
				null, "there-are-no-courses");

		searchContainer.setResults(CourseLocalServiceUtil.getByTitleStatusCategoriesTags(freetext, closed, categoryIds, tagsSelIds, themeDisplay.getCompanyId(), 
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(), themeDisplay.getLanguageId(), isAdmin, true, searchContainer.getStart(), searchContainer.getEnd()));
		searchContainer.setTotal(CourseLocalServiceUtil.countByTitleStatusCategoriesTags(freetext, closed, categoryIds, tagsSelIds, themeDisplay.getCompanyId(), 
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(), themeDisplay.getLanguageId(), isAdmin, true));
		
		renderRequest.setAttribute("searchContainer", searchContainer);
		renderRequest.setAttribute("catIds", catIds);
		renderRequest.setAttribute("noAssetCategoryIds", assetCategoryIds == null || assetCategoryIds.size() == 0);
		renderRequest.setAttribute("catId", catId);
		renderRequest.setAttribute("search", search);
		renderRequest.setAttribute("freetext", freetext);
		renderRequest.setAttribute("tags", tags);
		renderRequest.setAttribute("state", state);
		renderRequest.setAttribute("catIdsText", catIdsText);
		renderRequest.setAttribute("STATUS_APPROVED", WorkflowConstants.STATUS_APPROVED);
		renderRequest.setAttribute("STATUS_INACTIVE", WorkflowConstants.STATUS_INACTIVE);
		renderRequest.setAttribute("STATUS_ANY", WorkflowConstants.STATUS_ANY);
		
		include(this.viewJSP, renderRequest, renderResponse);
	}
	
	private void showViewEditCourse(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		include(this.editCourseJSP, renderRequest, renderResponse);
	}
	
	private void showViewExport(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		include(this.exportJSP, renderRequest, renderResponse);
	}
	
	private void showViewImport(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		include(this.importJSP, renderRequest, renderResponse);
	}
	
	private void showViewClone(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		include(this.cloneJSP, renderRequest, renderResponse);
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
			LayoutServiceUtil.importPortletInfo(themeDisplay.getLayout().getPlid(), groupId,portletId, uploadRequest.getParameterMap(), file);
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
		boolean childCourse=ParamUtil.getBoolean(actionRequest, "childCourse",false);
		boolean cloneForum = ParamUtil.getBoolean(actionRequest, "cloneForum");
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
		
		boolean visible = ParamUtil.getBoolean(actionRequest, "visible", false);

		
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
			message.put("childCourse", childCourse);
			message.put("serviceContext",serviceContext);
			message.put("visible",visible);
			message.put("cloneForum", cloneForum);
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
		if(log.isDebugEnabled()){
			log.debug("groupId:"+groupId);
			log.debug("fileName:"+fileName);
		}
		
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
	
}

