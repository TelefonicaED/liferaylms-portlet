package com.liferay.util;

import java.io.InputStream;
import java.net.URLEncoder;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.tls.lms.util.DLFolderUtil;

public class CourseCopyUtil {
	private static Log log = LogFactoryUtil.getLog(CourseCopyUtil.class);
	
	public static String descriptionFilesClone(String description, long groupId, long actId, long userId){

		String newDescription = description;
		
		try {
			
			Document document = SAXReaderUtil.read(description.replace("&lt;","<").replace("&nbsp;",""));
			
			Element rootElement = document.getRootElement();
			
			if(rootElement.elements("Description").size()!=0){
				for (Element entryElement : rootElement.elements("Description")) {
					for (Element entryElementP : entryElement.elements("p")) {
						
						//Para las imagenes
						for (Element entryElementImg : entryElementP.elements("img")) {
							
							String src = entryElementImg.attributeValue("src");
							
							String []srcInfo = src.split("/");
							String fileUuid = "", fileGroupId ="";
							
							if(srcInfo.length >= 6  && srcInfo[1].compareTo("documents") == 0){
								fileUuid = srcInfo[srcInfo.length-1];
								fileGroupId = srcInfo[2];
								
								String []uuidInfo = fileUuid.split("\\?");
								String uuid="";
								if(srcInfo.length > 0){
									uuid=uuidInfo[0];
								}
								
								FileEntry file;
								try {
									file = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, Long.parseLong(fileGroupId));
									
									ServiceContext serviceContext = new ServiceContext();
									serviceContext.setScopeGroupId(groupId);
									serviceContext.setUserId(userId);
									serviceContext.setCompanyId(file.getCompanyId());
									serviceContext.setAddGroupPermissions(true);
									
									FileEntry newFile = cloneFileDescription(file, actId, file.getUserId(), serviceContext);
									
									newDescription = descriptionCloneFile(newDescription, file, newFile);
									
									log.debug("     + Description file image : " + file.getTitle() +" ("+file.getMimeType()+")");
									
								} catch (Exception e) {
									log.error("* ERROR! Description file image : " + e.getMessage());
									if(log.isDebugEnabled()){
										e.printStackTrace();
									}
								}
							}
						}
						
						//Para los enlaces
						for (Element entryElementLink : entryElementP.elements("a")) {
							
							String href = entryElementLink.attributeValue("href");
							
							String []hrefInfo = href.split("/");
							String fileUuid = "", fileGroupId ="";
							
							if(hrefInfo.length >= 6 && hrefInfo[1].compareTo("documents") == 0){
								fileUuid = hrefInfo[hrefInfo.length-1];
								fileGroupId = hrefInfo[2];
								
								String []uuidInfo = fileUuid.split("\\?");
								String uuid="";
								if(hrefInfo.length > 0){
									uuid=uuidInfo[0];
								}
								
								FileEntry file;
								try {
									file = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, Long.parseLong(fileGroupId));
																			
									ServiceContext serviceContext = new ServiceContext();
									serviceContext.setScopeGroupId(groupId);
									serviceContext.setUserId(userId);
									serviceContext.setCompanyId(file.getCompanyId());
									serviceContext.setAddGroupPermissions(true);
									
									FileEntry newFile = cloneFileDescription(file, actId, file.getUserId(), serviceContext);
									
									newDescription = descriptionCloneFile(newDescription, file, newFile);
									
									log.debug("   + Description file pdf : " + file.getTitle() +" "+file.getFileEntryId() );
									
								} catch (Exception e) {
									log.error("* ERROR! Description file pdf : " + e.getMessage());
									if(log.isDebugEnabled()){
										e.printStackTrace();
									}
								}
							}
							
							//Si en los enlaces tienen una imagen para hacer click.
							/*for (Element entryElementLinkImage : entryElementLink.elements("img")) {
								;//parseImage(entryElementLinkImage, element, context, moduleId);
							}*/
							
						}
					}
				}
			}else{
				if (rootElement.getQName().getName().equals("p")) {
					
					//Para las imagenes
					for (Element entryElementImg : rootElement.elements("img")) {
						
						String src = entryElementImg.attributeValue("src");
						
						String []srcInfo = src.split("/");
						String fileUuid = "", fileGroupId ="";
						
						if(srcInfo.length >= 6  && srcInfo[1].compareTo("documents") == 0){
							fileUuid = srcInfo[srcInfo.length-1];
							fileGroupId = srcInfo[2];
							
							String []uuidInfo = fileUuid.split("\\?");
							String uuid="";
							if(srcInfo.length > 0){
								uuid=uuidInfo[0];
							}
							
							FileEntry file;
							try {
								file = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, Long.parseLong(fileGroupId));
								
								ServiceContext serviceContext = new ServiceContext();
								serviceContext.setScopeGroupId(groupId);
								serviceContext.setUserId(userId);
								serviceContext.setCompanyId(file.getCompanyId());
								serviceContext.setAddGroupPermissions(true);
								
								FileEntry newFile = cloneFileDescription(file, actId, file.getUserId(), serviceContext);
								
								newDescription = descriptionCloneFile(newDescription, file, newFile);
								
								log.debug("     + Description file image : " + file.getTitle() +" ("+file.getMimeType()+")");
								
							} catch (Exception e) {
								log.error("* ERROR! Description file image : " + e.getMessage());
								if(log.isDebugEnabled()){
									e.printStackTrace();
								}
							}
						}
					}
					
					//Para los enlaces
					for (Element entryElementLink : rootElement.elements("a")) {
						
						String href = entryElementLink.attributeValue("href");
						
						String []hrefInfo = href.split("/");
						String fileUuid = "", fileGroupId ="";
						
						if(hrefInfo.length >= 6 && hrefInfo[1].compareTo("documents") == 0){
							fileUuid = hrefInfo[hrefInfo.length-1];
							fileGroupId = hrefInfo[2];
							
							String []uuidInfo = fileUuid.split("\\?");
							String uuid="";
							if(hrefInfo.length > 0){
								uuid=uuidInfo[0];
							}
							
							FileEntry file;
							try {
								file = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, Long.parseLong(fileGroupId));
																		
								ServiceContext serviceContext = new ServiceContext();
								serviceContext.setScopeGroupId(groupId);
								serviceContext.setUserId(userId);
								serviceContext.setCompanyId(file.getCompanyId());
								serviceContext.setAddGroupPermissions(true);
								
								FileEntry newFile = cloneFileDescription(file, actId, file.getUserId(), serviceContext);
								
								newDescription = descriptionCloneFile(newDescription, file, newFile);
								
								log.debug("   + Description file pdf : " + file.getTitle() +" "+file.getFileEntryId() );
								
							} catch (Exception e) {
								log.error("* ERROR! Description file pdf : " + e.getMessage());
								if(log.isDebugEnabled()){
									e.printStackTrace();
								}
							}
						}
						
						//Si en los enlaces tienen una imagen para hacer click.
						/*for (Element entryElementLinkImage : entryElementLink.elements("img")) {
							;//parseImage(entryElementLinkImage, element, context, moduleId);
						}*/
						
					}
				}
			}
			
			
			
		} catch (DocumentException de) {
			
		} catch (Exception e) {
			log.error("* ERROR! Document Exception : " + e.getMessage());
			if(log.isDebugEnabled()){
				e.printStackTrace();
			}
		}

		return newDescription;
		
	}
	
	public static String descriptionCloneFile(String description, FileEntry oldFile, FileEntry newFile){
		String res = description;
		
		//Precondicion
		if(oldFile == null || newFile == null){
			return res;
		}
		
		//<img src="/documents/10808/0/GibbonIndexer.jpg/b24c4a8f-e65c-434a-ba36-3b3e10b21a8d?t=1376472516221"
		//<a  href="/documents/10808/10884/documento.pdf/32c193ed-16b3-4a83-93da-630501b72ee4">Documento</a></p>
		
		String target 		= "/documents/"+oldFile.getRepositoryId()+"/"+oldFile.getFolderId()+"/"+URLEncoder.encode(oldFile.getTitle())+"/"+oldFile.getUuid();
		String replacement 	= "/documents/"+newFile.getRepositoryId()+"/"+newFile.getFolderId()+"/"+URLEncoder.encode(newFile.getTitle())+"/"+newFile.getUuid();

		res = description.replace(target, replacement);
		
		if(log.isDebugEnabled()){
			if(res.equals(description)){
				log.debug("   :: description         : " + description );
				log.debug("   :: target      : " + target );	
				log.debug("   :: replacement : " + replacement );
			}
		}
		
				
		String changed = (!res.equals(description))?" changed":" not changed";
		
		log.debug("   + Description file : " + newFile.getTitle() +" (" + newFile.getMimeType() + ")" + changed);
		
		return res;
	}
	
	public static FileEntry cloneFileDescription(FileEntry file, long actId, long userId, ServiceContext serviceContext){
		
		long folderId = 0;
		
		try {
			
			InputStream is = DLFileEntryLocalServiceUtil.getFileAsStream(userId, file.getFileEntryId(), file.getVersion());
			
			//Crear el folder
			DLFolder dlFolder = DLFolderUtil.createDLFoldersForLearningActivity(userId, serviceContext.getScopeGroupId(), serviceContext);
			folderId = dlFolder.getFolderId();
			
			//long repId = DLFolderConstants.getDataRepositoryId(file.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			long repositoryId = DLFolderConstants.getDataRepositoryId(serviceContext.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			
			String ficheroStr = file.getTitle();	
			if(!file.getTitle().endsWith(file.getExtension())){
				ficheroStr = ficheroStr +"."+ file.getExtension();
			}
			return  DLAppLocalServiceUtil.addFileEntry(
					serviceContext.getUserId(), repositoryId , folderId , ficheroStr, file.getMimeType(), 
					file.getTitle(), StringPool.BLANK, StringPool.BLANK, is, file.getSize() , serviceContext ) ;
			
			
		}catch(DuplicateFileException dfl){
			try{
				
				return DLAppLocalServiceUtil.getFileEntry(serviceContext.getScopeGroupId(), folderId, file.getTitle());
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
}
