package com.liferay.lms.lar;

import java.io.InputStream;

import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
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

		log.debug("*** addZipEntry Implementacion de cada actividad****: typeId:"+actividad.getTypeId());
		LearningActivityType learningActivityType=new LearningActivityTypeRegistry().getLearningActivityType(actividad.getTypeId());
		
		String addZipEntryResult = null;
		try {
			addZipEntryResult = learningActivityType.addZipEntry(actividad, assetEntryId, context, entryElementLoc);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		log.debug("++++ addZipEntryResult:"+addZipEntryResult);
	}
	
	public static String changeSpecialCharacter(String str) {
	    
		str = str.replaceAll("[^a-zA-Z0-9]", "");
	    return str;
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
