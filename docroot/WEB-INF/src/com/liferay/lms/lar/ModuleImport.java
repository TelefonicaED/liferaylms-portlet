package com.liferay.lms.lar;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.liferay.lms.model.Module;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.util.DLFolderUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

public class ModuleImport {
	
	private static Log log = LogFactoryUtil.getLog(ModuleImport.class);
	
	/**
	 * FUnción para importar las imágenes asociadas a los módulos
	 * @param context
	 * @param entryElement
	 * @param serviceContext
	 * @param userId
	 * @param newModule
	 * @throws PortalException
	 * @throws SystemException
	 */
	public  static void importImageModule(PortletDataContext context, Element entryElement, ServiceContext serviceContext, long userId, Module newModule) throws PortalException, SystemException {
		//Importar imagenes del modulo.
		if(entryElement.attributeValue("file") != null){
			
			log.info("entryElement value file-->"+entryElement.attributeValue("file"));
		
			long repositoryId = DLFolderConstants.getDataRepositoryId(context.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			long folderId=DLFolderUtil.createDLFolderImageModule(userId,repositoryId, serviceContext);
			
			log.info("repositoryId: " + repositoryId);
			log.info("folderId: " + folderId);
			
			String name[] = entryElement.attributeValue("file").split("/");
	
			if(name.length > 0){
				String imageName = name[name.length-1];
				InputStream input = context.getZipEntryAsInputStream(entryElement.attributeValue("file"));
			
				
				if(input != null){
					String mimeType = MimeTypesUtil.getContentType(imageName);
					log.info("mimeType: " + mimeType);
					try {								
						FileEntry image = DLAppLocalServiceUtil.addFileEntry(userId, repositoryId , folderId , imageName, mimeType, imageName, StringPool.BLANK, StringPool.BLANK, IOUtils.toByteArray(input), serviceContext ) ;
						if(image != null)log.info("image: " + image.getFileEntryId());
						newModule.setIcon(image.getFileEntryId());	
						
						ModuleLocalServiceUtil.updateModule(newModule);
					} catch(DuplicateFileException dfl){
						dfl.printStackTrace();
						//Si da un error de duplicado de imagen, le ponemos delante el id del modulo
						FileEntry image;
						try {
							image = DLAppLocalServiceUtil.getFileEntry(context.getScopeGroupId(), folderId, newModule.getModuleId() + "module.jpg");
							newModule.setIcon(image.getFileEntryId());	
							
							ModuleLocalServiceUtil.updateModule(newModule);
						} catch (SystemException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (PortalException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					} catch (Exception e) {
						e.printStackTrace();
						log.info("* ERROR! module file: " + e.getCause().toString());
					}	
				}
			}
			
		}
	}
	
	/**
	 * Función para importar las imágenes que tienen los módulos en la descripción
	 * @param context
	 * @param entryElement
	 * @param serviceContext
	 * @param userId
	 * @param newModule
	 */
	public static void importDescriptionFile(PortletDataContext context,Element entryElement, ServiceContext serviceContext, long userId, Module newModule) {
		
		for (Element actElement : entryElement.elements("descriptionfile")) {
			
			FileEntry oldFile = (FileEntry)context.getZipEntryAsObject(actElement.attributeValue("path"));
			FileEntry newFile;
			long folderId=0;
			String description = "";
			
			try {
				String titleFile=oldFile.getTitle();
				//si el archivo le han cambiado el titulo y no tiene extension
				if(!oldFile.getTitle().endsWith(oldFile.getExtension())){
					titleFile=oldFile.getTitle()+"."+oldFile.getExtension();
				}
	
				InputStream input = context.getZipEntryAsInputStream(actElement.attributeValue("file"));
				
				long repositoryId = DLFolderConstants.getDataRepositoryId(context.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
				folderId=DLFolderUtil.createDLFoldersForLearningActivity(userId,repositoryId,serviceContext).getFolderId();
				String ficheroStr = actElement.attributeValue("file");	
				String ficheroExtStr = "";
				String extension[] = ficheroStr.split("\\.");
				
				if(extension.length > 0){
					ficheroExtStr = "."+extension[extension.length-1];
				}
				
				if(!ficheroExtStr.endsWith(oldFile.getExtension())){
					ficheroExtStr="."+oldFile.getExtension();
					ficheroStr=ficheroStr+"."+oldFile.getExtension();
				}
							
				newFile = DLAppLocalServiceUtil.addFileEntry(userId, repositoryId , folderId , ficheroExtStr, oldFile.getMimeType(), titleFile, StringPool.BLANK, StringPool.BLANK, IOUtils.toByteArray(input), serviceContext );
					
				description = ImportUtil.descriptionFileParserLarToDescription(newModule.getDescription(), oldFile, newFile);
				
			} catch(DuplicateFileException dfl){
				try{
					String titleFile=oldFile.getTitle();
					
					//si el archivo le han cambiado el titulo y no tiene extension
					if(!oldFile.getTitle().endsWith(oldFile.getExtension())){
						titleFile=oldFile.getTitle();
								
					}
					
					FileEntry existingFile = DLAppLocalServiceUtil.getFileEntry(context.getScopeGroupId(), folderId, titleFile);
					description = ImportUtil.descriptionFileParserLarToDescription(newModule.getDescription(), oldFile, existingFile);
					
					
				} catch (Exception e) {
					description = newModule.getDescription();
				}
				
				
			} catch (Exception e) {
			//	e.printStackTrace();
				log.info("*2 ERROR! descriptionfile: " + e.getMessage());
			}
	
			newModule.setDescription(description);
			try {
				ModuleLocalServiceUtil.updateModule(newModule);
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
