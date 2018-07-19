package com.liferay.lms.learningactivity;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.lms.asset.ResourceInternalAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.ImageProcessorUtil;
import com.tls.lms.util.LiferaylmsUtil;

public class ResourceInternalLearningActivityType extends BaseLearningActivityType 
{
	private static final long serialVersionUID = 4839623540644256683L;
	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"resourceInternalActivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());
	public final static long TYPE_ID = 7;
	
	@Override
	public boolean gradebook() {
		return false;
	}


	@Override
	public long getDefaultScore() {
		return 0;
	}


	@Override
	public String getName() {
		
		return "learningactivity.internal";
	}


	@Override
	public AssetRenderer getAssetRenderer(LearningActivity learningactivity) throws SystemException, PortalException {
		
		return new ResourceInternalAssetRenderer(learningactivity,this);
	}


	@Override
	public long getTypeId() {
		return 7;
	}
	
	@Override
	public String getExpecificContentPage() {
		return "/html/resourceInternalActivity/admin/edit.jsp";
	}
	
	@Override
	public boolean hasEditDetails() {
		return false;
	}

	
	@Override
	public String setExtraContent(UploadRequest uploadRequest,
			PortletResponse portletResponse, LearningActivity learningActivity)
			throws PortalException, SystemException, DocumentException,IOException {
		
		String assetId = ParamUtil.getString(uploadRequest,"assetEntryId","0");
		String team = ParamUtil.getString(uploadRequest, "team","0");
		long teamId = 0;
		ThemeDisplay themeDisplay = (ThemeDisplay) uploadRequest.getAttribute(WebKeys.THEME_DISPLAY);
		if(!team.equalsIgnoreCase("0")){
			teamId = Long.parseLong(team);
		}
		boolean linkResources = false;
		try {
			linkResources = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.RESOURCE_INTERNAL_DOCUMENT_LINKED);
		} catch (SystemException e) {
			e.printStackTrace();
		}	
		
		if(!linkResources){
			try{
				PortletRequest actionRequest = (PortletRequest)uploadRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
				ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivity.class.getName(), actionRequest);

				//Obtengo el assetEntry y compruebo si es un recurso de tipo Documento y Multimedia comprobando
				//si existe un elemento DLFileEntry con id=assetEntry.getClassPK
				AssetEntry docAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.valueOf(assetId));
				FileEntry docfile=DLAppLocalServiceUtil.getFileEntry(docAsset.getClassPK());
				if(docfile != null){
					//Creo un directorio propio de la actividad o lo recupero si ya existe.
					Folder folder = createFoldersForLearningActivity(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), learningActivity.getActId(), learningActivity.getTitle(themeDisplay.getLocale()), serviceContext);
					FileEntry newFile = null;
					boolean created = false;	
					int counter = 0;
					while(!created){
						try{
							if(counter==0){
								newFile = DLAppLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), folder.getFolderId(), docfile.getTitle()+StringPool.PERIOD+docfile.getExtension(), docfile.getMimeType(), docfile.getTitle(), docfile.getDescription(), "", docfile.getContentStream(), docfile.getSize(), serviceContext);
							}else{
								newFile = DLAppLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), folder.getFolderId(), docfile.getTitle()+counter+StringPool.PERIOD+docfile.getExtension(), docfile.getMimeType(), docfile.getTitle()+counter, docfile.getDescription(), "", docfile.getContentStream(), docfile.getSize(), serviceContext);
							}
							created = true;
						}catch (DuplicateFileException e){
							counter++;
						}
					}
									
					//Recupero el asset asociado al nuevo archivo local del curso.
					AssetEntry newAsset = AssetEntryLocalServiceUtil.getEntry(docAsset.getClassName(), newFile.getFileEntryId());
					//Actualizo el extraContentTmp para que apunte al recurso local y no al global
					assetId = Long.toString(newAsset.getEntryId());

					//Actualizo los permisos del recurso.
					Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
					String[] actIds = {ActionKeys.VIEW};
					try {
						LiferaylmsUtil.setPermission(themeDisplay, DLFolder.class.getName(), siteMemberRole, actIds, folder.getFolderId());
						LiferaylmsUtil.setPermission(themeDisplay, DLFileEntry.class.getName(), siteMemberRole, actIds, newFile.getFileEntryId());
					} catch (Exception e) {
						e.printStackTrace();
					}

					ImageProcessorUtil.generateImages(newFile.getLatestFileVersion());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		Document document = null;
		Element rootElement = null;
		if((learningActivity.getExtracontent()==null)||(learningActivity.getExtracontent().trim().length()==0)){
			document = SAXReaderUtil.createDocument();
			rootElement = document.addElement("resourceInternal");
		}
		else
		{
			document=SAXReaderUtil.read(learningActivity.getExtracontent());
			rootElement =document.getRootElement();
		}

		Element assetEntry=rootElement.element("assetEntry");
		if(assetEntry!=null)
		{
			assetEntry.detach();
			rootElement.remove(assetEntry);
		}
		assetEntry = SAXReaderUtil.createElement("assetEntry");
		assetEntry.setText(assetId);		
		rootElement.add(assetEntry);	
		
		if(!StringPool.BLANK.equals(team)){
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
		}
		
		learningActivity.setExtracontent(document.formattedString());
		
		return null;
	}
		


		/**
		 * Primero se busca si ya existe, si existe se devuelve y sino se crea uno nuevo.
		 */
		private Folder createFoldersForLearningActivity(Long userId, Long groupId, Long actId, String title, ServiceContext serviceContext) throws PortalException, SystemException{
			Folder newFolder = null;
			try {
				newFolder = DLAppLocalServiceUtil.getFolder(groupId, 0, String.valueOf(actId));
			} catch (Exception e) {
				newFolder = DLAppLocalServiceUtil.addFolder(userId, groupId,0, String.valueOf(actId), title, serviceContext);
			}
	    	return newFolder;
		}
		
	@Override
	public void deleteResources(ActionRequest actionRequest,
			ActionResponse actionResponse, LearningActivity larn)
			throws PortalException, SystemException, DocumentException,
			IOException {
		UploadRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		ThemeDisplay themeDisplay = (ThemeDisplay) uploadRequest.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			Folder folder = DLAppLocalServiceUtil.getFolder(themeDisplay.getScopeGroupId(), 0, String.valueOf(larn.getActId()));
			java.util.List<FileEntry> files = DLAppLocalServiceUtil.getFileEntries(themeDisplay.getScopeGroupId(), folder.getFolderId());
			for(FileEntry file:files){
				DLAppLocalServiceUtil.deleteFileEntry(file.getFileEntryId());
			}
			DLAppLocalServiceUtil.deleteFolder(folder.getFolderId());
		} catch (Exception e) {
			//No existe carpeta, por lo que no hay recursos asociados que eliminar.
		}
		super.deleteResources(actionRequest, actionResponse, larn);
	}
	
	@Override
	public String getDescription() {
		return "learningactivity.internal.helpmessage";
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
	public boolean canBeLinked(){
		return true;
	}
}
