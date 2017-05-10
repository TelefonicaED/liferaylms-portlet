package com.liferay.lms.lar;

import java.io.InputStream;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.SCORMContent;
import com.liferay.lms.service.SCORMContentLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

public class ExportUtil {
	
	private static Log log = LogFactoryUtil.getLog(ExportUtil.class);

	public static void descriptionFileParserDescriptionToLar(String description, long oldGroupId, long moduleId, PortletDataContext context, Element element){

		if(description != null && !description.equals("")){
			try {
				
				Document document = SAXReaderUtil.read(description.replace("&lt;","<").replace("&nbsp;",""));
				
				Element rootElement = document.getRootElement();
				
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
				
									String pathqu = getEntryPath(context, file);
									String pathFile = getDescriptionModulePath(context, moduleId); 
											
									context.addZipEntry(pathqu, file);
									context.addZipEntry(pathFile + file.getTitle(), file.getContentStream());
	
									Element entryElementLoc= element.addElement("descriptionfile");
									entryElementLoc.addAttribute("path", pathqu);
									entryElementLoc.addAttribute("file", pathFile + file.getTitle());
									
									log.info("   + Description file image : " + file.getTitle() +" ("+file.getMimeType()+")");
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									log.info("* ERROR! Description file image : " + e.getMessage());
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
									
									String pathqu = getEntryPath(context, file);
									String pathFile = getDescriptionModulePath(context, moduleId); 
											
									context.addZipEntry(pathqu, file);
									context.addZipEntry(pathFile + file.getTitle(), file.getContentStream());
	
									String titleFile=changeSpecialCharacter(file.getTitle());
									if(!file.getTitle().endsWith(file.getExtension())){
										titleFile=file.getTitle()+"."+file.getExtension();
									} 
									
									
									
									Element entryElementLoc= element.addElement("descriptionfile");
									entryElementLoc.addAttribute("path", pathqu);
									entryElementLoc.addAttribute("file", pathFile + titleFile);	
										
								} catch (Exception e) {
									// TODO Auto-generated catch block
									//e.printStackTrace();
									log.info("* ERROR! Description file pdf : " + e.getMessage());
								}
							}
							
							//Si en los enlaces tienen una imagen para hacer click.
							for (Element entryElementLinkImage : entryElementLink.elements("img")) {
								parseImage(entryElementLinkImage, element, context, moduleId);
							}
							
						}
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.info("* ERROR! Document Exception : " + e.getMessage());
			}
		}

	}
	
	private static void parseImage(Element imgElement, Element element, PortletDataContext context, Long moduleId){
		
		String src = imgElement.attributeValue("src");
		
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

				String pathqu = getEntryPath(context, file);
				String pathFile = getDescriptionModulePath(context, moduleId); 
						
				context.addZipEntry(pathqu, file);
				context.addZipEntry(pathFile + file.getTitle(), file.getContentStream());

				Element entryElementLoc= element.addElement("descriptionfile");
				entryElementLoc.addAttribute("path", pathqu);
				entryElementLoc.addAttribute("file", pathFile + file.getTitle());
				
				log.info("   + Description file image : " + file.getTitle() +" ("+file.getMimeType()+")");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.info("* ERROR! Description file image : " + e.getMessage());
			}
		}
		
	
	}
	
	private static String getDescriptionModulePath(PortletDataContext context, long moduleId) {
		
		StringBundler sb = new StringBundler(4);	
		sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
		sb.append("/moduleentries/module_description_files/"+String.valueOf(moduleId)+"/");
		return sb.toString();
	}
	
	private static String getEntryPath(PortletDataContext context, FileEntry file) {
		
		StringBundler sb = new StringBundler(4);
		sb.append(context.getPortletPath("resourceactivity_WAR_liferaylmsportlet"));
		sb.append("/moduleentries/");
		sb.append(file.getFileEntryId());
		sb.append(".xml");
		return sb.toString();
	}

	public static void addZipEntry(LearningActivity actividad, long assetEntryId, PortletDataContext context, Element entryElementLoc) throws PortalException, SystemException {
		AssetEntry docAsset= AssetEntryLocalServiceUtil.getAssetEntry(assetEntryId);
		log.info("docAsset: " + docAsset.getClassPK());
		if(actividad.getTypeId() != 9){
			
			log.info("mimeType: " + docAsset.getMimeType());
			DLFileEntry docfile=DLFileEntryLocalServiceUtil.getDLFileEntry(docAsset.getClassPK());
			
			log.info("docFile: " + docfile.getFileEntryId());
			String extension = "";
			if(!docfile.getTitle().endsWith(docfile.getExtension()) && docfile.getExtension().equals("")){
				if(docfile.getMimeType().equals("image/jpeg")){
					extension= ".jpg";
				}else if(docfile.getMimeType().equals("image/png")){
					extension= ".png";
				}else if(docfile.getMimeType().equals("video/mpeg")){
					extension= ".mpeg";
				}else if(docfile.getMimeType().equals("application/pdf")){
					extension= ".pdf";
				}else{
					String ext[] = extension.split("/");
					if(ext.length>1){
						extension = ext[1];
					}
				}
			}else if(!docfile.getTitle().endsWith(docfile.getExtension()) && !docfile.getExtension().equals("")){
				extension="."+docfile.getExtension();
			}

			log.info("file Title: " + docfile.getTitle());
			String title = changeSpecialCharacter(docfile.getTitle());
			title += extension;
			log.info("title: " + title);
			
			String pathqu = getEntryPath(context, docfile);
			String pathFile = getFilePath(context, docfile,actividad.getActId());
			Element entryElementfe= entryElementLoc.addElement("dlfileentry");
			entryElementfe.addAttribute("path", pathqu);
			entryElementfe.addAttribute("file", pathFile+title);
			context.addZipEntry(pathqu, docfile);
			
			log.info("pathqu: " + pathqu);
			log.info("pathFile: " + pathFile);

			//Guardar el fichero en el zip.
			InputStream input = DLFileEntryLocalServiceUtil.getFileAsStream(docfile.getUserId(), docfile.getFileEntryId(), docfile.getVersion());

			context.addZipEntry(getFilePath(context, docfile,actividad.getActId())+title, input);
			
			String txt = (actividad.getTypeId() == 2) ? "external":"internal";
			log.info("    - Resource "+ txt + ": " + title);

		}else{
			if(actividad.getTypeId() == 9){
					
				log.info("***************************************************************************************");
					
				log.info("PASO POR AQUI PARA "+actividad.getTitle());

					
				if(docAsset.getClassName().equals(SCORMContent.class.getName())){
					try{
						ScormDataHandlerImpl scormHandler = new ScormDataHandlerImpl();
						SCORMContent scocontent = SCORMContentLocalServiceUtil.getSCORMContent(docAsset.getClassPK());
						scormHandler.exportEntry(context, entryElementLoc, scocontent);
					}catch(Exception e){
						e.printStackTrace();
						actividad.setExtracontent("");
					}
				} else{
					log.info("MPC CONTENT");
					actividad.setExtracontent("");
				}
					
			}
		}
	}
	
	public static String changeSpecialCharacter(String str) {
		String fullName = "";
		if(str.lastIndexOf(".")>0){
			String name = str.substring(0, str.lastIndexOf("."));
		    String extension = str.substring(str.lastIndexOf("."));
		    fullName =   name.replaceAll("[^a-zA-Z0-9]", "") + extension;
			
		}else{
			fullName = str.replaceAll("[^a-zA-Z0-9]", "");
		}		
		return fullName;
		
	}
	
	private static String getEntryPath(PortletDataContext context, DLFileEntry file) {
		
		StringBundler sb = new StringBundler(4);
		sb.append(context.getPortletPath("resourceactivity_WAR_liferaylmsportlet"));
		sb.append("/moduleentries/");
		sb.append(file.getFileEntryId());
		sb.append(".xml");
		return sb.toString();
	}
	
	private static String getFilePath(PortletDataContext context,DLFileEntry file, long actId) {
		
		StringBundler sb = new StringBundler(4);
		sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
		sb.append("/moduleentries/activities/"+String.valueOf(actId)+"/");
		return sb.toString();
	}
}
