package com.liferay.lms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.PortletRequest;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

public class DLFolderUtil {
	
	private static Log log = LogFactoryUtil.getLog(DLFolderUtil.class);
	
	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads";
	

	/**
	 * Método para crear las carpetas de las imágenes de los módulos dentro de un curso
	 * @param userId
	 * @param groupId
	 * @param serviceContext
	 * @return id de la carpeta donde se subirán las imágenes
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static long createDLFolderImageModule(long userId, long groupId, ServiceContext serviceContext) throws PortalException, SystemException {
		
		DLFolder dlFolderMain = null;
		DLFolder dlFolderModule = null;
		
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		
		//Buscamos la carpeta por defecto
		try {
			dlFolderMain = DLFolderLocalServiceUtil.getFolder(groupId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,LmsConstant.IMAGEGALLERY_MAINFOLDER);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
		} catch (SystemException e) {
			// TODO Auto-generated catch block
		}
		
		if(dlFolderMain == null){
			dlFolderMain = DLFolderLocalServiceUtil.addFolder(userId, groupId, groupId, false, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, LmsConstant.IMAGEGALLERY_MAINFOLDER, LmsConstant.IMAGEGALLERY_MAINFOLDER_DESCRIPTION, serviceContext);
		}
		
		try {
			dlFolderModule = DLFolderLocalServiceUtil.getFolder(groupId,dlFolderMain.getFolderId(),LmsConstant.IMAGEGALLERY_PORTLETFOLDER);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
		} catch (SystemException e) {
			// TODO Auto-generated catch block
		}
		
		if(dlFolderModule == null){
			dlFolderModule = DLFolderLocalServiceUtil.addFolder(userId, groupId, groupId, false, dlFolderMain.getFolderId(), LmsConstant.IMAGEGALLERY_PORTLETFOLDER, LmsConstant.IMAGEGALLERY_PORTLETFOLDER_DESCRIPTION, serviceContext);
		}
		

    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    	Date date = new Date();
    	String igRecordFolderName=dateFormat.format(date)+LmsConstant.SEPARATOR+userId;
    	DLFolder dlFolderImage = null;
    	try{
    		dlFolderImage = DLFolderLocalServiceUtil.addFolder(userId,groupId, groupId, false, dlFolderModule.getFolderId(),igRecordFolderName, igRecordFolderName, serviceContext);
    	}catch (DuplicateFolderNameException e){
    		dlFolderImage = DLFolderLocalServiceUtil.getFolder(groupId, dlFolderModule.getFolderId(), igRecordFolderName);
    	}
		
		return dlFolderImage.getFolderId();
	}
	
	/**
	 * Creamos la carpeta para los documentos adjuntos de las actividades.Primero se busca si ya existe, si existe se devuelve y sino se crea uno nuevo.
	 * @param userId
	 * @param groupId
	 * @param actId
	 * @param serviceContext
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static DLFolder createDLFoldersForLearningActivity(long userId, long groupId, ServiceContext serviceContext) throws PortalException, SystemException{
		
		DLFolder mainFolder = null;

		try {
			mainFolder = DLFolderLocalServiceUtil.getFolder(groupId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,DOCUMENTLIBRARY_MAINFOLDER);
		} catch (PortalException e) {
			// No mostramos el error porque solo preguntamos por una carpeta que no tiene por qué existir
		} catch (SystemException e) {
			// TODO Auto-generated catch block
		}
		
		//Damos permisos al archivo para usuarios de comunidad.
		serviceContext.setAddGroupPermissions(true);

		if(mainFolder == null){
	    	long repositoryId = DLFolderConstants.getDataRepositoryId(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	    	//mountPoint -> Si es carpeta raíz.
	    	mainFolder = DLFolderLocalServiceUtil.addFolder(userId, groupId, repositoryId, true, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, DOCUMENTLIBRARY_MAINFOLDER, DOCUMENTLIBRARY_MAINFOLDER, serviceContext);
		}
  
        return mainFolder;
	}
	
	public static long createDLFoldersP2P(long userId,long repositoryId,PortletRequest portletRequest) throws PortalException, SystemException{

		long dlRecordFolderId = 0;
		
		long dlPortletFolderId = createDLFolderModule(userId,repositoryId,portletRequest);
        //Create this record folder
        if(dlPortletFolderId > 0){
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        	Date date = new Date();
        	String dlRecordFolderName = dateFormat.format(date)+LmsConstant.SEPARATOR+userId;
        	ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFolder.class.getName(), portletRequest);
        	Folder newDocumentRecordFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, dlPortletFolderId, dlRecordFolderName, dlRecordFolderName, serviceContext);
        	dlRecordFolderId = newDocumentRecordFolder.getFolderId();
        }
        return dlRecordFolderId;
	}
	
	public static long createDLFolderModule(long userId,long repositoryId,PortletRequest portletRequest) throws PortalException, SystemException{
		//Variables for folder ids
		long dlMainFolderId = 0;
		long dlPortletFolderId = 0;
		
		//Search for folder in Document Library
        boolean dlMainFolderFound = false;
        boolean dlPortletFolderFound = false;
        
        Folder dlFolderMain = null;
        //Get main folder
        try {
        	//Get main folder
        	dlFolderMain = DLAppLocalServiceUtil.getFolder(repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,LmsConstant.DOCUMENTLIBRARY_MAINFOLDER);
        	dlMainFolderId = dlFolderMain.getFolderId();
        	dlMainFolderFound = true;
        	//Get portlet folder
        	Folder dlFolderPortlet = DLAppLocalServiceUtil.getFolder(repositoryId,dlMainFolderId,LmsConstant.DOCUMENTLIBRARY_PORTLETFOLDER);
        	dlPortletFolderId = dlFolderPortlet.getFolderId();
        	dlPortletFolderFound = true;
        } catch (Exception ex){
        	//Not found Main Folder
        }
        
		ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFolder.class.getName(), portletRequest);
		//Damos permisos al archivo para usuarios de comunidad.
		serviceContext.setAddGroupPermissions(true);
        
        //Create main folder if not exist
        if(!dlMainFolderFound || dlFolderMain==null){
        	Folder newDocumentMainFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, LmsConstant.DOCUMENTLIBRARY_MAINFOLDER, LmsConstant.DOCUMENTLIBRARY_MAINFOLDER_DESCRIPTION, serviceContext);
        	dlMainFolderId = newDocumentMainFolder.getFolderId();
        	dlMainFolderFound = true;
        }
        //Create portlet folder if not exist
        if(dlMainFolderFound && !dlPortletFolderFound){
        	Folder newDocumentPortletFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, dlMainFolderId , LmsConstant.DOCUMENTLIBRARY_PORTLETFOLDER, LmsConstant.DOCUMENTLIBRARY_PORTLETFOLDER_DESCRIPTION, serviceContext);
        	dlPortletFolderFound = true;
            dlPortletFolderId = newDocumentPortletFolder.getFolderId();
        }
        
        return dlPortletFolderId;
	}
	
	/**
	 * Método para crear las carpetas de los iconos de los tipos de curso
	 * @param userId
	 * @param groupId
	 * @param serviceContext
	 * @return id de la carpeta donde se subirán las imágenes
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static long createDLFolderIconImageCourseType(long userId, long groupId, long repositoryId, ServiceContext serviceContext) throws PortalException, SystemException {
		
		DLFolder dlFolderMain = null;
		DLFolder dlFolderCourseType= null;
		
		//Se busca carpeta por defecto
		try {
			dlFolderMain = DLFolderLocalServiceUtil.getFolder(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, LmsConstant.COURSETYPE_ICON_MAINFOLDER);
		} catch (PortalException | SystemException e) {
			log.debug("Default folder not found:: " + e.getMessage());
		}
		
		//Si no se encuentra carpeta por defecto se crea
		if(Validator.isNull(dlFolderMain))
			dlFolderMain = DLFolderLocalServiceUtil.addFolder(userId, groupId, repositoryId, false, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, LmsConstant.COURSETYPE_ICON_MAINFOLDER, LmsConstant.COURSETYPE_ICON_MAINFOLDER_DESCRIPTION, serviceContext);
		
		try {
			dlFolderCourseType = DLFolderLocalServiceUtil.getFolder(groupId, dlFolderMain.getFolderId(), LmsConstant.COURSETYPE_ICON_PORTLETFOLDER);
		} catch (PortalException | SystemException e) {
			log.debug("Default folder not found:: " + e.getMessage());
		} 
		
		if(Validator.isNull(dlFolderCourseType))
			dlFolderCourseType = DLFolderLocalServiceUtil.addFolder(userId, groupId, repositoryId, false, dlFolderMain.getFolderId(), LmsConstant.COURSETYPE_ICON_PORTLETFOLDER, LmsConstant.COURSETYPE_ICON_PORTLETFOLDER_DESCRIPTION, serviceContext);

    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    	Date date = new Date();
    	String igRecordFolderName = dateFormat.format(date) + StringPool.UNDERLINE + userId;
    	DLFolder dlFolderImage = null;
    	try{
    		dlFolderImage = DLFolderLocalServiceUtil.addFolder(userId, groupId, repositoryId, false, dlFolderCourseType.getFolderId(), igRecordFolderName, igRecordFolderName, serviceContext);
    	}catch (DuplicateFolderNameException e){
    		dlFolderImage = DLFolderLocalServiceUtil.getFolder(groupId, dlFolderCourseType.getFolderId(), igRecordFolderName);
    	}
		
		return dlFolderImage.getFolderId();
	}

}
