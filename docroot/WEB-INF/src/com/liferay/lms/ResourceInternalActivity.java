package com.liferay.lms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.util.liferay.patch.PortalClassInvokerPatched;

/**
 * Portlet implementation class ResourceActivity
 */
public class ResourceInternalActivity extends MVCPortlet {
	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads";

	public void selectResource(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	
		String jspPage = ParamUtil.getString(actionRequest, "jspPage");
		long actId = ParamUtil.getLong(actionRequest, "actId", 0);
		long entryId = ParamUtil.getLong(actionRequest, "entryId", 0);
		ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivity.class.getName(), actionRequest);
	
		LearningActivity larn = LearningActivityServiceUtil.getLearningActivity(actId);
		larn.setExtracontent(Long.toString(entryId));
		LearningActivityServiceUtil.modLearningActivity(larn, serviceContext);
		SessionMessages.add(actionRequest, "activity-saved-successfully");
		actionResponse.setRenderParameter("jspPage", jspPage);
		actionResponse.setRenderParameter("actId", Long.toString(actId));
	}
	
	public void addfiles(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {

	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
	UploadPortletRequest request = PortalUtil.getUploadPortletRequest(actionRequest);
	
	String jspPage = ParamUtil.getString(actionRequest, "jspPage");
	long actId = ParamUtil.getLong(actionRequest, "actId", 0);
	ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivity.class.getName(), actionRequest);
	String fileName = request.getFileName("fileName");
	String mimeTypeFileName = request.getContentType("fileName");
	String fileName2 = request.getFileName("fileName2");
	String mimeTypeFileName2 = request.getContentType("fileName2");
	boolean deleteVideo=ParamUtil.getBoolean(request, "deletevideo",false);
	boolean deleteComplementary=ParamUtil.getBoolean(request, "deletecomplementary",false);
	String description = request.getParameter("description");
	String title = fileName;// + " uploaded by " + user.getFullName();
	AssetEntry entry=null;
	AssetEntry entry2=null;
	LearningActivity larn = LearningActivityServiceUtil.getLearningActivity(actId);
	String extraContent=larn.getExtracontent();
	Document document = SAXReaderUtil.createDocument();
	Element rootElement = document.addElement("multimediaentry");
	if(extraContent!=null &&!"".equals(extraContent)&&!Validator.isNumber(extraContent))
	{
		document=SAXReaderUtil.read(extraContent);
		rootElement =document.getRootElement();
	}
	if(deleteVideo)
	{
		Element video=rootElement.element("video");
		if(!video.attributeValue("id","").equals(""))
		{
			AssetEntry videoAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(video.attributeValue("id")));
			FileEntry videofile=DLAppLocalServiceUtil.getFileEntry(videoAsset.getClassPK());
			//TODO DLFileVersion videofileVersion = videofile.getFileVersion();
			DLAppLocalServiceUtil.deleteFileEntry(videofile.getFileEntryId());
		}
		video.detach();
		rootElement.remove(video);
		
		
	}
	if(deleteComplementary)
	{
		Element documento=rootElement.element("document");
		if(!documento.attributeValue("id","").equals(""))
		{
			AssetEntry videoAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(documento.attributeValue("id")));
			FileEntry videofile=DLAppLocalServiceUtil.getFileEntry(videoAsset.getClassPK());
			//TODO DLFileVersion videofileVersion = videofile.getFileVersion();
			DLAppLocalServiceUtil.deleteFileEntry(videofile.getFileEntryId());
		}
		documento.detach();
		rootElement.remove(documento);
		
	}
	
	long repositoryId = DLFolderConstants.getDataRepositoryId(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	
	if(fileName!=null && !fileName.equals(""))
	{
		File file = request.getFile("fileName");		
		Element video=rootElement.element("video");
		if(video!=null &&!video.attributeValue("id","").equals(""))
		{
			AssetEntry videoAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(video.attributeValue("id")));
			DLFileEntry videofile=DLFileEntryLocalServiceUtil.getDLFileEntry(videoAsset.getClassPK());
			DLFileEntryLocalServiceUtil.deleteFileEntry(videofile.getFileEntryId());
		}
		
		
		long folderId=createDLFolders(themeDisplay.getUserId(),repositoryId,actionRequest);
		
		ServiceContext serviceContextFile= ServiceContextFactory.getInstance( DLFileEntry.class.getName(), actionRequest);
		//Damos permisos al archivo para usuarios de comunidad.
		serviceContext.setAddGroupPermissions(true);
		FileEntry dlDocument = DLAppLocalServiceUtil.addFileEntry(
			                      themeDisplay.getUserId(), repositoryId , folderId , fileName, mimeTypeFileName, fileName, StringPool.BLANK, StringPool.BLANK, file , serviceContextFile ) ;
		entry=AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), dlDocument.getPrimaryKey());
		}

	if(fileName2!=null && !fileName2.equals(""))
	{
		File file2 = request.getFile("fileName2");
		Element documento=rootElement.element("document");
		if(documento!=null&&!documento.attributeValue("id","").equals(""))
		{
			AssetEntry videoAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(documento.attributeValue("id")));
			DLFileEntry videofile=DLFileEntryLocalServiceUtil.getDLFileEntry(videoAsset.getClassPK());
			DLFileEntryLocalServiceUtil.deleteFileEntry(videofile.getFileEntryId());
		}
		long folderId=createDLFolders(themeDisplay.getUserId(),repositoryId, actionRequest);
		ServiceContext serviceContextFile2= ServiceContextFactory.getInstance( DLFileEntry.class.getName(), actionRequest);
		//Damos permisos al archivo para usuarios de comunidad.
		serviceContext.setAddGroupPermissions(true);
		FileEntry dlDocument = DLAppLocalServiceUtil.addFileEntry(
			                      themeDisplay.getUserId(), repositoryId , folderId , fileName2, mimeTypeFileName2, fileName2, StringPool.BLANK, StringPool.BLANK, file2 , serviceContextFile2 ) ;
		entry2=AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), dlDocument.getPrimaryKey());

		if(entry2!=null)
		{
			if(documento!=null)
			{
				documento.detach();
				rootElement.remove(documento);
			}
			documento=SAXReaderUtil.createElement("document");
			documento.addAttribute("id", Long.toString(entry2.getEntryId()));
			rootElement.add(documento);
		}
	}
	
	if(entry!=null)
	{
		Element video=rootElement.element("video");
		if(video!=null)
		{
			video.detach();
			rootElement.remove(video);
		}
		video = SAXReaderUtil.createElement("video");
		video.addAttribute("id", Long.toString(entry.getEntryId()));
		video.setText("");
		rootElement.add(video);
		
		
		
	}
	larn.setExtracontent(document.formattedString());
	larn.setDescription( description,themeDisplay.getLocale());
	//LearningActivityServiceUtil.modLearningActivity(larn, serviceContext);
	LearningActivityServiceUtil.modLearningActivity(larn);
	//auditing
	AuditingLogFactory.audit(larn.getCompanyId(), larn.getGroupId(), LearningActivity.class.getName(), larn.getPrimaryKey(), themeDisplay.getUserId(), AuditConstants.UPDATE, null);
	
	SessionMessages.add(actionRequest, "activity-saved-successfully");
	actionResponse.setRenderParameter("jspPage", jspPage);
	actionResponse.setRenderParameter("actId", Long.toString(actId));
}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
	throws PortletException, IOException {

if(ParamUtil.getLong(renderRequest, "actId", 0)==0)// TODO Auto-generated method stub
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{
		LearningActivity activity;
		try {
			activity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(renderRequest, "actId", 0));
			
			//auditing
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
			long typeId=activity.getTypeId();
			
			if(typeId==7)
			{
				super.render(renderRequest, renderResponse);
			}
			else
			{
				renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
			}
		} catch (PortalException e) {
			// TODO Auto-generated catch block
		} catch (SystemException e) {
			// TODO Auto-generated catch block
		}			
}
}
	private long createDLFolders(Long userId,Long repositoryId,PortletRequest portletRequest) throws PortalException, SystemException{
		//Variables for folder ids
		Long dlMainFolderId = 0L;
		//Search for folder in Document Library
        boolean dlMainFolderFound = false;
        //Get main folder
        try {
        	//Get main folder
        	Folder dlFolderMain = DLAppLocalServiceUtil.getFolder(repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,DOCUMENTLIBRARY_MAINFOLDER);
        	dlMainFolderId = dlFolderMain.getFolderId();
        	dlMainFolderFound = true;
        	//Get portlet folder
        } catch (Exception ex){
        }
        
		ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFolder.class.getName(), portletRequest);
		//Damos permisos al archivo para usuarios de comunidad.
		serviceContext.setAddGroupPermissions(true);
        
        //Create main folder if not exist
        if(!dlMainFolderFound){
        	Folder newDocumentMainFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, DOCUMENTLIBRARY_MAINFOLDER, DOCUMENTLIBRARY_MAINFOLDER, serviceContext);
        	dlMainFolderFound = true;
        	dlMainFolderId = newDocumentMainFolder.getFolderId();
        }
        //Create portlet folder if not exist
     
  
        return dlMainFolderId;
	}
	
	public void invokeTaglibDiscussion(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
		
		//Se parchea porque da error al hacer comentarios con la clase por defecto del portal.
		
		PortletConfig portletConfig = getPortletConfig();
		
		PortalClassInvokerPatched.invoke(  // Notar el "Patched"
            true,
            "com.liferay.portlet.messageboards.action.EditDiscussionAction",
            "processAction",
            new String[] {
                    "org.apache.struts.action.ActionMapping",
                    "org.apache.struts.action.ActionForm",
                    PortletConfig.class.getName(), ActionRequest.class.getName(),
                    ActionResponse.class.getName()
            },
            null, null, portletConfig, actionRequest, actionResponse);
	}
}
