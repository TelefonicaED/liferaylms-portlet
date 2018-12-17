package com.liferay.lms.learningactivity;

import java.io.File;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.lms.asset.TaskOnlineAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

public class TaskOnlineLearningActivityType extends BaseLearningActivityType {

	private static Log log = LogFactoryUtil.getLog(TaskOnlineLearningActivityType.class);
	
	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"onlinetaskactivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());
	
	public final static int DEFAULT_FILENUMBER = 5;
	
	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) throws SystemException, PortalException {
		return new TaskOnlineAssetRenderer(larn,this);
	}

	@Override
	public String getName() {	
		return "learningactivity.online";
	}
	
	@Override
	public String getClassName(){
		return getClass().getName();
	}
	
	@Override
	public boolean isScoreConfigurable() {
		return true;
	}

	@Override
	public long getTypeId() {
		return 6;
	}
	@Override
	public long getDefaultTries() {
		return 1;
	}	
	@Override
	public String getExpecificContentPage() {
		return "/html/onlinetaskactivity/admin/edit.jsp";
	}
	
	@Override
	public boolean hasEditDetails() {
		return false;
	}
	
	@Override
	public String setExtraContent(UploadRequest uploadRequest,
			PortletResponse portletResponse, LearningActivity learningActivity)
					throws NumberFormatException, Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay) uploadRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletRequest portletRequest = (PortletRequest)uploadRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);

		String fichero = ParamUtil.getString(uploadRequest, "fichero", StringPool.FALSE);
		String textoenr = ParamUtil.getString(uploadRequest, "textoenr", StringPool.FALSE);
		String team = ParamUtil.getString(uploadRequest, "team","0");
		String additionalFileName = GetterUtil.getString(uploadRequest.getFileName("additionalFile"), StringPool.BLANK);
		Boolean deleteAdditionalFile = ParamUtil.getBoolean(uploadRequest, "deleteAdditionalFile", Boolean.FALSE);
		if(log.isDebugEnabled()){
			log.debug(":::setExtraContent:: fichero :: " + fichero);
			log.debug(":::setExtraContent:: textoenr :: " + textoenr);
			log.debug(":::setExtraContent:: team :: " + team);
			log.debug(":::setExtraContent:: additionalFileName :: " + additionalFileName);
			log.debug(":::setExtraContent:: deleteAdditionalFile :: " + deleteAdditionalFile);
		}
		
		long teamId = 0;
		if(!team.equalsIgnoreCase("0")){
			teamId = Long.parseLong(team);
		}

		Document document = null;
		Element rootElement = null;
		if((learningActivity.getExtracontent()==null)||(learningActivity.getExtracontent().trim().length()==0)){
			document = SAXReaderUtil.createDocument();
			rootElement = document.addElement("online");
		}
		else
		{
			document=SAXReaderUtil.read(learningActivity.getExtracontent());
			rootElement =document.getRootElement();
		}

		Element ficheroXML=rootElement.element("fichero");
		if(ficheroXML!=null)
		{
			ficheroXML.detach();
			rootElement.remove(ficheroXML);
		}
		ficheroXML=SAXReaderUtil.createElement("fichero");
		ficheroXML.addText(fichero);
		rootElement.add(ficheroXML);

		Element textoenrXML=rootElement.element("textoenr");
		if(textoenrXML!=null)
		{
			textoenrXML.detach();
			rootElement.remove(textoenrXML);
		}
		textoenrXML=SAXReaderUtil.createElement("textoenr");
		textoenrXML.addText(textoenr);
		rootElement.add(textoenrXML);

		Element teamElement=rootElement.element("team");
		if(teamElement!=null)
		{
			teamElement.detach();
			rootElement.remove(teamElement);
		}
		if(teamId!=0){
			teamElement = SAXReaderUtil.createElement("team");
			teamElement.setText(Long.toString(teamId));
			rootElement.add(teamElement);
		}
		
		Element additionalFileElement = rootElement.element("additionalFile");
		if(additionalFileElement!=null){
			additionalFileElement.detach();
			rootElement.remove(additionalFileElement);
		}
		if(!deleteAdditionalFile && !StringPool.BLANK.equals(additionalFileName)){
			File additionalFile = uploadRequest.getFile("additionalFile");
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), portletRequest);
			serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			String contentType = uploadRequest.getContentType("additionalFile");
			long repositoryId = DLFolderConstants.getDataRepositoryId(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			DLFolder additionalFileFolder = null;
			try{
				additionalFileFolder = DLFolderLocalServiceUtil.getFolder(themeDisplay.getScopeGroupId(),DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "ResourceUploads");
			}catch (NoSuchFolderException e){
				if(log.isDebugEnabled())
					log.debug(e.getMessage());
			}
			if(Validator.isNull(additionalFileFolder))
				additionalFileFolder = DLFolderLocalServiceUtil.addFolder(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), repositoryId, true, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "ResourceUploads", "ResourceUploads", serviceContext);
			FileEntry additionalFileEntry = null;
			try{
				additionalFileEntry = DLAppLocalServiceUtil.getFileEntry(themeDisplay.getScopeGroupId(), additionalFileFolder.getFolderId(), additionalFileName);
			} catch (NoSuchFileEntryException e) {
				if(log.isDebugEnabled())
					log.debug(e.getMessage());
			}
			if(Validator.isNull(additionalFileEntry))
				additionalFileEntry = DLAppLocalServiceUtil.addFileEntry(themeDisplay.getUserId(),
					repositoryId, additionalFileFolder.getFolderId(), additionalFileName, contentType, additionalFileName, StringPool.BLANK, StringPool.BLANK, additionalFile, serviceContext);
			long additionalFileId = additionalFileEntry.getFileEntryId();
			additionalFileElement = SAXReaderUtil.createElement("additionalFile");
			additionalFileElement.addText(String.valueOf(additionalFileId));
			rootElement.add(additionalFileElement);
		}
		
		learningActivity.setExtracontent(document.formattedString());

		return null;
	}
	
	@Override
	public String getDescription() {
		return "learningactivity.online.helpmessage";
	}
	
	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}
	
	@Override
	public boolean hasDeleteTries() {
		return true;
	}
	
	@Override
	public boolean isAutoCorrect() {
		return false;
	}
	
	@Override
	public boolean canBeLinked(){
		return false;
	}
	
	@Override
	public boolean canBeSeenResults(){
		return true;
	}
	
}
