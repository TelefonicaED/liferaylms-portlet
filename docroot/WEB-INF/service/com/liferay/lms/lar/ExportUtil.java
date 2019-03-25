package com.liferay.lms.lar;

import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

public class ExportUtil {
	
	private static Log log = LogFactoryUtil.getLog(ExportUtil.class);

	public static void descriptionFileParserDescriptionToLar(String description, long oldGroupId, long moduleId, PortletDataContext context, Element element){
		if(description != null && !description.equals("")){
			descriptionFileReplaceDescriptionToLar(description, oldGroupId, moduleId, context, element);
		}
	}
	
	private static void descriptionFileReplaceDescriptionToLar(String description, long oldGroupId, long moduleId, PortletDataContext context, Element element) {
		int index = 0;
		int fromIndex = 0;
		try{
			index = description.indexOf("src=");
			String src = null;
			while(index > 0){
				src = description.substring(index+5, description.indexOf("\"", index+5));
				log.debug("src: " + src);
				saveImagen(src, moduleId, context, element);
				fromIndex = index + 5;
				index = description.indexOf("src=", fromIndex);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		try{
			index = description.indexOf("href=");
			fromIndex = 0;
			String href = null;
			while(index > 0){
				href = description.substring(index+6, description.indexOf("\"", index+6));
				log.debug("href: " + href);
				saveLink(href, moduleId, context, element);
				fromIndex = index + 5;
				index = description.indexOf("href=", fromIndex);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private static void saveLink(String href, long moduleId, PortletDataContext context, Element element) {
		//Para los enlaces
			
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
				
				String pathqu = getEntryPath(context, file.getFileEntryId());
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
					
			} catch (NoSuchFileEntryException e){
				log.info("* ERROR! Description file image : " + e.getMessage());
			} catch (RepositoryException e){
				log.info("* ERROR! Description file image : " + e.getMessage());
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.info("* ERROR! Description file pdf : " + e.getMessage());
			}
		}
	}

	private static void saveImagen(String src, long moduleId, PortletDataContext context, Element element) {
		
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
			
			try {
				FileEntry file = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, Long.parseLong(fileGroupId));

				String pathqu = getEntryPath(context, file.getFileEntryId());
				String pathFile = getDescriptionModulePath(context, moduleId); 
						
				context.addZipEntry(pathqu, file);
				context.addZipEntry(pathFile + file.getTitle(), file.getContentStream());

				Element entryElementLoc= element.addElement("descriptionfile");
				entryElementLoc.addAttribute("path", pathqu);
				entryElementLoc.addAttribute("file", pathFile + file.getTitle());
				
				log.info("   + Description file image : " + file.getTitle() +" ("+file.getMimeType()+")");
				
			} catch (RepositoryException e){
				log.info("* ERROR! Description file image : " + e.getMessage());
			} catch (NoSuchFileEntryException e){
				log.info("* ERROR! Description file image : " + e.getMessage());
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
	
	public static String getEntryPath(PortletDataContext context, long fileEntryId) {
		
		StringBundler sb = new StringBundler(4);
		sb.append(context.getPortletPath("resourceactivity_WAR_liferaylmsportlet"));
		sb.append("/moduleentries/");
		sb.append(fileEntryId);
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
		String fullName = "";
		if(str.lastIndexOf(".") > 0 && !str.endsWith(".")){
			String name = str.substring(0, str.lastIndexOf("."));
		    String extension = str.substring(str.lastIndexOf("."));
		    fullName =   name.replaceAll("[^a-zA-Z0-9]", "") + extension;
			
		}else{
			fullName = str.replaceAll("[^a-zA-Z0-9]", "");
		}		
		return fullName;
		
	}
}
