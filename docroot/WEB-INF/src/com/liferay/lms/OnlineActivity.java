package com.liferay.lms;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.asset.LearningActivityAssetRendererFactory;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;


/**
 * Portlet implementation class SurveyActivity
 */
public class OnlineActivity extends MVCPortlet {
	
	private static Log log = LogFactoryUtil.getLog(OnlineActivity.class);
	
	public static final String NOT_TEACHER_SQL = "WHERE User_.userId NOT IN "+
			 "( SELECT Usergrouprole.userId "+
			 "    FROM Usergrouprole "+ 
			 "   INNER JOIN Resourcepermission ON Usergrouprole.roleId = Resourcepermission.roleId "+
			 "   INNER JOIN Resourceaction ON Resourcepermission.name = Resourceaction.name "+
			 "	   					      AND (BITAND(CAST_LONG(ResourcePermission.actionIds), CAST_LONG(ResourceAction.bitwiseValue)) != 0)"+
			 "   WHERE Resourcepermission.scope="+ResourceConstants.SCOPE_GROUP_TEMPLATE+
			 "     AND Resourceaction.actionId = 'VIEW_RESULTS' "+
			 "     AND Resourceaction.name='com.liferay.lms.model' "+
			 "     AND Usergrouprole.groupid=? ) ";

	public static final String ACTIVITY_TRY_SQL = "WHERE (EXISTS (SELECT 1 FROM lms_learningactivitytry " +
			"WHERE User_.userId = lms_learningactivitytry.userId AND lms_learningactivitytry.actId = ? ))"; 

	public static final String ACTIVITY_RESULT_PASSED_SQL = "WHERE (EXISTS (SELECT 1 FROM lms_learningactivityresult " +
			"WHERE User_.userId = lms_learningactivityresult.userId " +
			" AND lms_learningactivityresult.passed > 0 AND lms_learningactivityresult.actId = ? ))"; 

	public static final String ACTIVITY_RESULT_FAIL_SQL = "WHERE (EXISTS (SELECT 1 FROM lms_learningactivityresult " +
			"WHERE User_.userId = lms_learningactivityresult.userId " +
			" AND lms_learningactivityresult.passed = 0 AND lms_learningactivityresult.actId = ? AND lms_learningactivityresult.endDate IS NOT NULL ))"; 

	public static final String ACTIVITY_RESULT_NO_CALIFICATION_SQL = "WHERE (NOT EXISTS (SELECT 1 FROM lms_learningactivityresult " +
			"WHERE User_.userId = lms_learningactivityresult.userId AND lms_learningactivityresult.actId = ? AND lms_learningactivityresult.endDate IS NOT NULL ))"; 


	//public static final String ACTIVITY_RESULT_NO_CALIFICATION_SQL = "WHERE (EXISTS (SELECT 1 FROM lms_learningactivityresult " +
	//		"WHERE User_.userId = lms_learningactivityresult.userId AND lms_learningactivityresult.endDate =lms_learningactivityresult.startDate AND lms_learningactivityresult.actId = ? ))"; 


	public static final String TEXT_XML= "text";
	public static final String RICH_TEXT_XML = "richText";
	public static final String FILE_XML = "file";	

	private void setGrades(RenderRequest renderRequest,	RenderResponse renderResponse) throws IOException, PortletException {

		boolean correct=true;
		long actId = ParamUtil.getLong(renderRequest,"actId"); 
		long studentId = ParamUtil.getLong(renderRequest,"studentId");
		String comments = renderRequest.getParameter("comments");

		long result=0;
			try {
				result=Long.parseLong(renderRequest.getParameter("result"));
				if(result<0 || result>100){
					correct=false;
					SessionErrors.add(renderRequest, "onlinetaskactivity.grades.result-bad-format");
				}
			} catch (NumberFormatException e) {
				correct=false;
				SessionErrors.add(renderRequest, "onlinetaskactivity.grades.result-bad-format");
			}

		if(correct) {
			try {
				LearningActivityTry  learningActivityTry =  LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, studentId);
				learningActivityTry.setEndDate(new Date());
				learningActivityTry.setResult(result);
				learningActivityTry.setComments(comments);
				updateLearningActivityTryAndResult(learningActivityTry);

				SessionMessages.add(renderRequest, "onlinetaskactivity.grades.updating");
			} catch (NestableException e) {
				SessionErrors.add(renderRequest, "onlinetaskactivity.grades.bad-updating");
			}
		}
	}

	
	public void setGrades(ActionRequest request,	ActionResponse response){
		
		ThemeDisplay themeDisplay  =(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean correct=true;
		long actId = ParamUtil.getLong(request,"actId"); 
		long studentId = ParamUtil.getLong(request,"studentId");		
		String comments = ParamUtil.getString(request,"comments");
		
		log.debug("actId: "+actId);
		log.debug("studentId: "+studentId);
		log.debug("comments: "+comments);		
		
		String gradeFilter = ParamUtil.getString(request, "gradeFilter");
		String criteria = ParamUtil.getString(request, "criteria");

		log.debug("gradeFilter: "+gradeFilter);
		log.debug("criteria: "+criteria);
		
		response.setRenderParameter("gradeFilter", gradeFilter);
		response.setRenderParameter("criteria", criteria);
		
		CalificationType ct = null;
		double result=0;
		try {
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());			
			ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());			
			result= Double.valueOf(ParamUtil.getString(request,"result").replace(",", "."));
			log.debug("result: "+result);
			if(result<ct.getMinValue(course.getGroupCreatedId()) || result>ct.getMaxValue(course.getGroupCreatedId())){
				correct=false;
				log.error("Result fuera de rango");
				SessionErrors.add(request, "result-bad-format");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			correct=false;
			SessionErrors.add(request, "result-bad-format");
		} catch (Exception e) {
			e.printStackTrace();
			correct=false;
			SessionErrors.add(request, "grades.bad-updating");
		}
		
		if(correct) {
			try {
				LearningActivityTry  learningActivityTry =  LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, studentId);
				learningActivityTry.setEndDate(new Date());
				learningActivityTry.setResult(ct.toBase100(themeDisplay.getScopeGroupId(),result));
				learningActivityTry.setComments(comments);
				updateLearningActivityTryAndResult(learningActivityTry);
				
				SessionMessages.add(request, "grades.updating");
			} catch (NestableException e) {
				SessionErrors.add(request, "grades.bad-updating");
			}
		}
	}
	
	
	
	
	
	
	private void updateLearningActivityTryAndResult(
			LearningActivityTry learningActivityTry) throws PortalException,
			SystemException {
		LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningActivityTry);

		LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivityTry.getActId(), learningActivityTry.getUserId());
		if(learningActivityResult.getResult() != learningActivityTry.getResult()) {
			LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(learningActivityTry.getActId());
			learningActivityResult.setResult(learningActivityTry.getResult());
			learningActivityResult.setPassed(learningActivityTry.getResult()>=learningActivity.getPasspuntuation());
			LearningActivityResultLocalServiceUtil.updateLearningActivityResult(learningActivityResult);
		}
	}

	@Override
	protected void doDispatch(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		String ajaxAction = renderRequest.getParameter("ajaxAction");

		if(ajaxAction!=null) {
			if("setGrades".equals(ajaxAction)) {
				setGrades(renderRequest, renderResponse);
			} 
		}


		super.doDispatch(renderRequest, renderResponse);
	}


	public void setActivity(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, NestableException {
		long actId = ParamUtil.getLong(actionRequest, "actId");
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		String text = ParamUtil.getString(uploadRequest, "text");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		User user = UserLocalServiceUtil.getUser(themeDisplay.getUserId());
		boolean isSetTextoEnr =  StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(actId,"textoenr"));
		boolean isSetFichero =  StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(actId,"fichero"));

		LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
		LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(actId, user.getUserId());

		if((learningActivity.getTries()!=0)&&(learningActivity.getTries()<=LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(actId, user.getUserId()))) {
			//TODO
			SessionErrors.add(actionRequest, "onlineActivity.max-tries");	
		}
		else {

			//ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);

			Element resultadosXML=SAXReaderUtil.createElement("results");
			Document resultadosXMLDoc=SAXReaderUtil.createDocument(resultadosXML);

			if(isSetFichero) {
				String fileName = uploadRequest.getFileName("fileName");
				File file = uploadRequest.getFile("fileName");
				String mimeType = uploadRequest.getContentType("fileName");
				if (Validator.isNull(fileName)) {
					SessionErrors.add(actionRequest, "onlineActivity.mandatory.file");
					actionRequest.setAttribute("actId", actId);
					actionResponse.setRenderParameter("text", text);
					return;
				}
				if(	file.getName().endsWith(".bat") 
						|| file.getName().endsWith(".com")
						|| file.getName().endsWith(".exe")
						|| file.getName().endsWith(".msi") ){

					SessionErrors.add(actionRequest, "onlineActivity-error-file-type");
					actionResponse.setRenderParameter("text", text);
					actionRequest.setAttribute("actId", actId);
					return;
				}

				long repositoryId = DLFolderConstants.getDataRepositoryId(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
				long folderId = createDLFolders(user.getUserId(), repositoryId, actionRequest);

				//Subimos el Archivo en la Document Library
				ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFileEntry.class.getName(), actionRequest);
				//Damos permisos al archivo para usuarios de comunidad.
				//serviceContext.setAddGroupPermissions(true);
				FileEntry document = DLAppLocalServiceUtil.addFileEntry(
						themeDisplay.getUserId(), repositoryId , folderId , fileName, mimeType, fileName, StringPool.BLANK, StringPool.BLANK, file , serviceContext ) ;
				LmsPrefs prefs = LmsPrefsLocalServiceUtil.fetchLmsPrefs(themeDisplay.getCompanyId());
				try{
					ResourcePermissionLocalServiceUtil.setResourcePermissions(themeDisplay.getCompanyId(), DLFileEntry.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(document.getFileEntryId()) , prefs.getTeacherRole(), new String[]{"VIEW"});
					ResourcePermissionLocalServiceUtil.setResourcePermissions(themeDisplay.getCompanyId(), DLFileEntry.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(document.getFileEntryId()) , prefs.getEditorRole(), new String[]{"VIEW"});
				}catch(Exception e){
					e.printStackTrace();
				}
				//serviceContext.setGroupPermissions(groupPermissions);
				Element fileXML=SAXReaderUtil.createElement(FILE_XML);
				fileXML.addAttribute("id", Long.toString(document.getFileEntryId()));
				resultadosXML.add(fileXML);
			}

			if(isSetTextoEnr){
				Element richTextXML=SAXReaderUtil.createElement(RICH_TEXT_XML);
				richTextXML.setText(text);
				resultadosXML.add(richTextXML);				
			}
			else {
				Element textXML=SAXReaderUtil.createElement(TEXT_XML);
				textXML.setText(text);
				resultadosXML.add(textXML);				
			}

			LearningActivityTry learningActivityTry =  LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,ServiceContextFactory.getInstance(actionRequest));
			learningActivityTry.setTryResultData(resultadosXMLDoc.formattedString());	
			//learningActivityTry.setEndDate(new Date());
			//learningActivityTry.setResult(0);
			LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningActivityTry);
			SessionMessages.add(actionRequest, "onlinetaskactivity.updating");
		}

	}


	private long createDLFolders(Long userId,Long repositoryId,PortletRequest portletRequest) throws PortalException, SystemException{
		//Variables for folder ids
		Long dlMainFolderId = 0L;
		Long dlPortletFolderId = 0L;
		Long dlRecordFolderId = 0L;
		//Search for folder in Document Library
		boolean dlMainFolderFound = false;
		boolean dlPortletFolderFound = false;
		//Get main folder
		try {
			//Get main folder
			Folder folderMain = DLAppLocalServiceUtil.getFolder(repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,LmsConstant.DOCUMENTLIBRARY_MAINFOLDER);
			dlMainFolderId = folderMain.getFolderId();
			dlMainFolderFound = true;
			//Get portlet folder
			Folder dlFolderPortlet = DLAppLocalServiceUtil.getFolder(repositoryId,dlMainFolderId,LmsConstant.DOCUMENTLIBRARY_PORTLETFOLDER);
			dlPortletFolderId = dlFolderPortlet.getFolderId();
			dlPortletFolderFound = true;
		} catch (Exception ex){
		}

		ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFolder.class.getName(), portletRequest);
		//Damos permisos al archivo para usuarios de comunidad.
		serviceContext.setAddGroupPermissions(true);

		//Create main folder if not exist
		if(!dlMainFolderFound){
			Folder newDocumentMainFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, LmsConstant.DOCUMENTLIBRARY_MAINFOLDER, LmsConstant.DOCUMENTLIBRARY_MAINFOLDER_DESCRIPTION, serviceContext);
			//DLFolderLocalServiceUtil.addFolderResources(newDocumentMainFolder, true, false);
			dlMainFolderId = newDocumentMainFolder.getFolderId();
			dlMainFolderFound = true;
		}
		//Create portlet folder if not exist
		if(dlMainFolderFound && !dlPortletFolderFound){
			Folder newDocumentPortletFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, dlMainFolderId , LmsConstant.DOCUMENTLIBRARY_PORTLETFOLDER, LmsConstant.DOCUMENTLIBRARY_PORTLETFOLDER_DESCRIPTION, serviceContext);
			//DLFolderLocalServiceUtil.addFolderResources(newDocumentPortletFolder, true, false);
			dlPortletFolderFound = true;
			dlPortletFolderId = newDocumentPortletFolder.getFolderId();
		}

		//Create this record folder
		if(dlPortletFolderFound){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String dlRecordFolderName = dateFormat.format(date)+LmsConstant.SEPARATOR+userId;
			Folder newDocumentRecordFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, dlPortletFolderId, dlRecordFolderName, dlRecordFolderName, serviceContext);
			//DLFolderLocalServiceUtil.addFolderResources(newDocumentRecordFolder, true, false);
			dlRecordFolderId = newDocumentRecordFolder.getFolderId();
		}
		return dlRecordFolderId;
	}

	public void edit(ActionRequest actionRequest,ActionResponse actionResponse)throws Exception {

		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		if(ParamUtil.getLong(actionRequest, "actId", 0)==0)
		{
			actionResponse.setRenderParameter("jspPage", "/html/onlinetaskactivity/admin/edit.jsp");
		}
	}

	public void editactivity(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException, SystemException, Exception {
		long actId = ParamUtil.getInteger(actionRequest, "actId");
		LearningActivityAssetRendererFactory laf = new LearningActivityAssetRendererFactory();
		if (laf != null) {
			AssetRenderer assetRenderer = laf.getAssetRenderer(actId, 0);

			String urlEdit = assetRenderer.getURLEdit((LiferayPortletRequest) actionRequest, (LiferayPortletResponse) actionResponse).toString();
			actionResponse.sendRedirect(urlEdit);
		}
		SessionMessages.add(actionRequest, "asset-renderer-not-defined");
	}
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {
		long actId=0;
		
		if(ParamUtil.getBoolean(renderRequest, "actionEditingDetails", false)){
			
			actId=ParamUtil.getLong(renderRequest, "resId", 0);
			renderResponse.setProperty("clear-request-parameters",Boolean.TRUE.toString());
		}
		else{
			actId=ParamUtil.getLong(renderRequest, "actId", 0);
		}
					
		if(actId==0)// TODO Auto-generated method stub
		{
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
		else
		{
				LearningActivity activity;
				try {

					//auditing
					ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
					
					activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
					long typeId=activity.getTypeId();
					
					if(typeId==6)
					{
						super.render(renderRequest, renderResponse);
					}
					else
					{
						renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
					}
				} catch (PortalException e) {
				} catch (SystemException e) {
				}			
		}
	}

}
