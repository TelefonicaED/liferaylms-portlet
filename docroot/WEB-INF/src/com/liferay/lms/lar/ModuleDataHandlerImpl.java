package com.liferay.lms.lar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;

import org.apache.commons.io.IOUtils;

import com.liferay.lms.ResourceExternalActivity;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.learningactivity.ResourceExternalLearningActivityType;
import com.liferay.lms.learningactivity.questiontype.QuestionType;
import com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;


public class ModuleDataHandlerImpl extends BasePortletDataHandler {

	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads";
	
	public PortletDataHandlerControl[] getExportControls() {
return new PortletDataHandlerControl[] {
		_entries, _categories, _comments, _ratings, _tags
	};
}

@Override
public PortletDataHandlerControl[] getImportControls() {
return new PortletDataHandlerControl[] {
		_entries, _categories, _comments, _ratings, _tags
	};
}
private static final String _NAMESPACE = "module";

private static PortletDataHandlerBoolean _categories =
	new PortletDataHandlerBoolean(_NAMESPACE, "categories");

private static PortletDataHandlerBoolean _comments =
	new PortletDataHandlerBoolean(_NAMESPACE, "comments");

private static PortletDataHandlerBoolean _entries =
	new PortletDataHandlerBoolean(_NAMESPACE, "entries", true, true);

private static PortletDataHandlerBoolean _ratings =
	new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

private static PortletDataHandlerBoolean _tags =
	new PortletDataHandlerBoolean(_NAMESPACE, "tags");

@SuppressWarnings("unchecked")
@Override
protected PortletPreferences doDeleteData(PortletDataContext context,
		String portletId, PortletPreferences preferences) throws Exception {
	
	System.out.println("\n-----------------------------\ndoDeleteData STARTS");
	
	try {
		String groupIdStr = String.valueOf(context.getScopeGroupId());
		
		Group group = GroupLocalServiceUtil.getGroup(context.getScopeGroupId());
		
		long groupId = 0;
		
		if(Validator.isNumber(groupIdStr)){
			groupId = Long.parseLong(groupIdStr);
		}
		long repositoryId = DLFolderConstants.getDataRepositoryId(group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		
		System.out.println("  Course: "+ group.getName()+ ", groupId: " + repositoryId);
		
		LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
		List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(groupId);

		for(Module module:modules){
			
			System.out.println("    Module : " + module.getTitle(Locale.getDefault()) );
			
			List<LearningActivity> activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(module.getModuleId());
			
			for(LearningActivity activity:activities){
				
				System.out.println("      Learning Activity : " + activity.getTitle(Locale.getDefault())+ " (" + LanguageUtil.get(Locale.getDefault(),learningActivityTypeRegistry.getLearningActivityType(activity.getTypeId()).getName())+")");
				
				LearningActivityLocalServiceUtil.deleteLearningactivity(activity);
				
			}
			
			//Borrar todos los ficheros 
			DLAppLocalServiceUtil.deleteAll(repositoryId);
			
			ModuleLocalServiceUtil.deleteModule(module);
			
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	System.out.println("doDeleteData ENDS\n-----------------------------\n");
	
	return super.doDeleteData(context, portletId, preferences);
}

@Override
protected String doExportData(PortletDataContext context, String portletId, PortletPreferences preferences) throws Exception {

	System.out.println("\n-----------------------------\ndoExportData STARTS, groupId : " + context.getScopeGroupId() );
	
	Group group = GroupLocalServiceUtil.getGroup(context.getScopeGroupId());
	System.out.println(" Course: "+ group.getName());
	
	
	context.addPermissions("com.liferay.lms.model.module", context.getScopeGroupId());
	
	Document document = SAXReaderUtil.createDocument();

	Element rootElement = document.addElement("moduledata");

	rootElement.addAttribute("group-id", String.valueOf(context.getScopeGroupId()));
	
	List<Module> entries = ModuleLocalServiceUtil.findAllInGroup(context.getScopeGroupId());
	long entryOld=0L;
	for (Module entry : entries) {
		System.out.println("Moduleeeeeeeeeeeeeeeeeeeeeeeeeeeeee "+entry.getModuleId());
		System.out.println("Moduleeeeeeeeeeeeeeeeeeeeeeeeeeeeee entryOld "+entryOld);
		if(entryOld!=entry.getModuleId())
		{
			entryOld=entry.getModuleId();
			exportEntry(context, rootElement, entry);
		} else{
			System.out.println("repetidooooo el modulo "+entry.getModuleId());

		}
	}

	System.out.println("doExportData ENDS, modules:" + entries.size() + "\n-----------------------------\n"  );
	
	return document.formattedString();
}

private void exportEntry(PortletDataContext context, Element root, Module entry) throws PortalException, SystemException {
	
	String path = getEntryPath(context, entry);
	
	System.out.println("\n  Module: " + entry.getModuleId() +" "+ entry.getTitle(Locale.getDefault()) );
	
	
	if (!context.isPathNotProcessed(path)) {
		return;
	}

	Element entryElement = root.addElement("moduleentry");

	entryElement.addAttribute("path", path);

	context.addPermissions(Module.class, entry.getModuleId());
	
	entry.setUserUuid(entry.getUserUuid());
	context.addZipEntry(path, entry);
	
	if(entry.getIcon() > 0){
		
		String pathFileModule = getFileToIS(context, entry.getIcon(), entry.getModuleId());
		entryElement.addAttribute("file", pathFileModule);
	}
	
	//Exportar los ficheros que tenga la descripci�n del modulo.
	descriptionFileParserDescriptionToLar(entry.getDescription(), entry.getGroupId(), entry.getModuleId(), context, entryElement);		
	
	LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
		List<LearningActivity> actividades=LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(entry.getModuleId());
	for(LearningActivity actividad:actividades)
	{
		
		System.out.println("    Learning Activity: " + actividad.getTitle(Locale.getDefault()) + " (" + LanguageUtil.get(Locale.getDefault(),learningActivityTypeRegistry.getLearningActivityType(actividad.getTypeId()).getName())+")" );
		
		String pathlo = getEntryPath(context, actividad);
		Element entryElementLoc= entryElement.addElement("learningactivity");
		entryElementLoc.addAttribute("path", pathlo);
		context.addPermissions(Module.class, entry.getModuleId());
		
		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			context.addAssetCategories(LearningActivity.class, actividad.getActId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.addComments(LearningActivity.class, actividad.getActId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.addRatingsEntries(LearningActivity.class, actividad.getActId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			context.addAssetTags(LearningActivity.class, actividad.getActId());
		}
		
		
		//Exportar los ficheros que tenga la descripci�n de la actividad.
		descriptionFileParserDescriptionToLar(actividad.getDescription(), actividad.getGroupId(), actividad.getModuleId(), context, entryElementLoc);		

	
		//Exportar las imagenes de los resources.
		if(actividad.getTypeId() == 2 || actividad.getTypeId() == 7 ){
			
			List<String> img = new LinkedList<String>();
			if(actividad.getTypeId() == 2){
				//LearningActivityLocalServiceUtil.
				//List<String> documents = LearningActivityLocalServiceUtil.getExtraContentValues(actividad.getActId(), "document");
				img = LearningActivityLocalServiceUtil.getExtraContentValues(actividad.getActId(), "document");
			}else if(actividad.getTypeId() == 7){
				img.add(LearningActivityLocalServiceUtil.getExtraContentValue(actividad.getActId(), "assetEntry"));
			}
			
				try {
					System.out.println("actividad.getTypeId() "+actividad.getTypeId());

					for(int i=0;i<img.size();i++){
						System.out.println("img "+img);
						if(!img.get(i).startsWith("<")){
							AssetEntry docAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.valueOf(img.get(i)));
							DLFileEntry docfile=DLFileEntryLocalServiceUtil.getDLFileEntry(docAsset.getClassPK());
							
								
							String extension = "";
							if(!docfile.getTitle().contains(".") && docfile.getExtension().equals("")){
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
							}else if(!docfile.getTitle().contains(".") && !docfile.getExtension().equals("")){
								extension="."+docfile.getExtension();
							}

							String pathqu = getEntryPath(context, docfile);
							String pathFile = getFilePath(context, docfile,actividad.getActId());
							Element entryElementfe= entryElementLoc.addElement("dlfileentry");
							entryElementfe.addAttribute("path", pathqu);
							entryElementfe.addAttribute("file", pathFile+containsCharUpper(docfile.getTitle()+extension));
							context.addZipEntry(pathqu, docfile);

							//Guardar el fichero en el zip.
							InputStream input = DLFileEntryLocalServiceUtil.getFileAsStream(docfile.getUserId(), docfile.getFileEntryId(), docfile.getVersion());

							context.addZipEntry(getFilePath(context, docfile,actividad.getActId())+containsCharUpper(docfile.getTitle()+extension), input);
							
							String txt = (actividad.getTypeId() == 2) ? "external":"internal";
							System.out.println("    - Resource "+ txt + ": " + containsCharUpper(docfile.getTitle()+extension));
						}
						
					}
					

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("* ERROR! resource file: " + e.getMessage());
				}
			
		}
		
		context.addZipEntry(pathlo, actividad);
		
		List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actividad.getActId());
		
		for(TestQuestion question:questions)
		{

			String pathqu = getEntryPath(context, question);
			Element entryElementq= entryElementLoc.addElement("question");
			entryElementq.addAttribute("path", pathqu);
			context.addZipEntry(pathqu, question);
			
			System.out.println("      Test Question: " + question.getQuestionId() /*Jsoup.parse(nuevaQuestion.getText()).text()*/);
			
			System.out.println("question.getText():\n" + question.getText());
			//Exportar los ficheros que tiene la descripcion de la pregunta
			descriptionFileParserDescriptionToLar("<root><Description>"+question.getText()+"</Description></root>", actividad.getGroupId(), actividad.getModuleId(), context, entryElementq);	
			
			QuestionType qt =new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
			qt.exportQuestionAnswers(context, entryElementq, question.getQuestionId(), actividad);

		}
		
	}
	
}

private String getFileToIS(PortletDataContext context, long entryId, long moduleId){
	
	try {

		FileEntry image=DLAppLocalServiceUtil.getFileEntry(entryId);
		
		String pathqu = getEntryPath(context, image);
		String pathFile = getImgModulePath(context, moduleId); 
				
		context.addZipEntry(pathqu, image);
		
		context.addZipEntry(pathFile + containsCharUpper(image.getTitle()), image.getContentStream());
		
		return pathFile + containsCharUpper(image.getTitle());

	} catch (Exception e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
		System.out.println("* ERROR! getFileToIS: " + e.getMessage());
	}	
	
	return "";
}

private static String getDescriptionModulePath(PortletDataContext context, long moduleId) {
	
	StringBundler sb = new StringBundler(4);	
	sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/module_description_files/"+String.valueOf(moduleId)+"/");
	return sb.toString();
}

private String getImgModulePath(PortletDataContext context, long moduleId) {
	
	StringBundler sb = new StringBundler(4);	
	sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/module_img/"+String.valueOf(moduleId)+"/");
	return sb.toString();
}

private String getEntryPath(PortletDataContext context, DLFileEntry file) {

	StringBundler sb = new StringBundler(4);
	sb.append(context.getPortletPath("resourceactivity_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/");
	sb.append(file.getFileEntryId());
	sb.append(".xml");
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

private String getEntryPath(PortletDataContext context, Image img) {

	StringBundler sb = new StringBundler(4);
	sb.append(context.getPortletPath("resourceactivity_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/");
	if(img != null){
		sb.append(img.getImageId());
	}else{
		sb.append("image");
	}
	sb.append(".xml");
	return sb.toString();
}

private String getFilePath(PortletDataContext context,DLFileEntry file, long actId) {
	
	StringBundler sb = new StringBundler(4);
	sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/activities/"+String.valueOf(actId)+"/");
	return sb.toString();
}

private String getEntryPath(PortletDataContext context, TestQuestion question) {

	StringBundler sb = new StringBundler(4);
	sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/activities/questions/");
	sb.append(question.getQuestionId());
	sb.append(".xml");
	return sb.toString();
}

private String getEntryPath(PortletDataContext context, LearningActivity actividad) {
	
	StringBundler sb = new StringBundler(4);
	
	sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/activities/");
	sb.append(actividad.getActId());
	sb.append(".xml");
	return sb.toString();
}

protected String getEntryPath(PortletDataContext context, Module entry) {

	StringBundler sb = new StringBundler(4);	
	sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/");
	sb.append(entry.getModuleId());
	sb.append(".xml");
	return sb.toString();
}

@Override
protected PortletPreferences doImportData(PortletDataContext context, String portletId, PortletPreferences preferences, String data) throws Exception {
	
	context.importPermissions("com.liferay.lms.model.module", context.getSourceGroupId(),context.getScopeGroupId());
	
	System.out.println("\n-----------------------------\ndoImport1Data STARTS12");
	
	
	Document document = SAXReaderUtil.read(data);

	Element rootElement = document.getRootElement();
	String entryOld="";
	for (Element entryElement : rootElement.elements("moduleentry")) {
		String path = entryElement.attributeValue("path");
		Group group = GroupLocalServiceUtil.getGroup(context.getScopeGroupId());
		

		if (!context.isPathNotProcessed(path)) {
			continue;
		}
		Module entry = (Module)context.getZipEntryAsObject(path);

		System.out.println("\n  Module: " + entry.getTitle(Locale.getDefault()) );
		System.out.println("\n  getModuleId: " + entry.getModuleId() );
		
		
		if(!entryOld.equalsIgnoreCase(String.valueOf(entry.getModuleId())))
		{
		
			System.out.println("entraaaaa el modulo "+entry.getModuleId());
			System.out.println("entraaaaa el entryOld "+entryOld);
			entryOld=String.valueOf(entry.getModuleId());
			importEntry(context,entryElement, entry);
		} else{
			System.out.println("repetidooooo el modulo "+entry.getModuleId());

		}
		
	}
	
	System.out.println("doImportData ENDS" + "\n-----------------------------\n"  );
	
	return null;
}

private void importEntry(PortletDataContext context, Element entryElement, Module entry) throws SystemException, PortalException {
	
	long userId = context.getUserId(entry.getUserUuid());
	
	ServiceContext serviceContext=new ServiceContext();
	serviceContext.setUserId(userId);
	serviceContext.setCompanyId(context.getCompanyId());
	serviceContext.setScopeGroupId(context.getScopeGroupId());
	
	entry.setGroupId(context.getScopeGroupId());
	entry.setUserId(userId);
	entry.setCompanyId(context.getCompanyId());
		
	//Para convertir < correctamente.
	entry.setDescription(entry.getDescription().replace("&amp;lt;", "&lt;"));
	//entry.setDescription(parseFilesFromDescription(entry.getDescription().replace("&amp;lt;", "&lt;"), entryElement.elements("descriptionfile"), userId, context, serviceContext));
	
	System.out.println("entry.setGroupId" +entry.getGroupId()  );
	System.out.println("entry.setGroupId" +entry.getUserId()  );
	System.out.println("entry.setGroupId" +entry.getModuleId()  );
	System.out.println("entry.setGroupId" +entry.getTitle()  );
	
	
	Module theModule=ModuleLocalServiceUtil.addmodule(entry);
	
	//Importar imagenes del modulo.
	if(entryElement.attributeValue("file") != null){
	
		String name[] = entryElement.attributeValue("file").split("/");
		String imageName = "module.jpg";
		String imageExtension ="jpg";
		long folderId=0;
		
		try {
			
			if(name.length > 0){
				imageName = name[name.length-1];
			}
			
			String extension[] = imageName.split(".");
			if(extension.length > 0){
				imageExtension = "."+extension[extension.length-1];
			}
			
			InputStream input = context.getZipEntryAsInputStream(entryElement.attributeValue("file"));
			
			if(input != null){
			
				long repositoryId = DLFolderConstants.getDataRepositoryId(context.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
				folderId=createDLFolders(userId,repositoryId, serviceContext);
											
				FileEntry image = DLAppLocalServiceUtil.addFileEntry(userId, repositoryId , folderId , imageName, "image/jpeg", imageName, StringPool.BLANK, StringPool.BLANK, IOUtils.toByteArray(input), serviceContext ) ;
		
				theModule.setIcon(image.getFileEntryId());	
				
				ModuleLocalServiceUtil.updateModule(theModule);
				
			}
			
		} catch(DuplicateFileException dfl){
			
			FileEntry image = DLAppLocalServiceUtil.getFileEntry(context.getScopeGroupId(), folderId, imageName);
			theModule.setIcon(image.getFileEntryId());	
			
			ModuleLocalServiceUtil.updateModule(theModule);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("* ERROR! module file: " + e.getCause().toString());
		}
		
	}
	
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
			folderId=createDLFolders(userId,repositoryId,serviceContext);
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
				
			description = descriptionFileParserLarToDescription(theModule.getDescription(), oldFile, newFile);
			
		} catch(DuplicateFileException dfl){
			try{
				String titleFile=oldFile.getTitle();
				
				//si el archivo le han cambiado el titulo y no tiene extension
				if(!oldFile.getTitle().endsWith(oldFile.getExtension())){
					titleFile=oldFile.getTitle();
							
				}
				
				FileEntry existingFile = DLAppLocalServiceUtil.getFileEntry(context.getScopeGroupId(), folderId, titleFile);
				description = descriptionFileParserLarToDescription(theModule.getDescription(), oldFile, existingFile);
				
				
			} catch (Exception e) {
				description = theModule.getDescription();
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
			System.out.println("*2 ERROR! descriptionfile: " + e.getMessage());
		}

		theModule.setDescription(description);
		
		ModuleLocalServiceUtil.updateModule(theModule);

	}
	
	LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
	
	for (Element actElement : entryElement.elements("learningactivity")) {
		
		//System.out.println("  Element : " + actElement.getPath() );
		
		String path = actElement.attributeValue("path");

		LearningActivity larn=(LearningActivity)context.getZipEntryAsObject(path);
		
		if(larn == null){
			System.out.println("    ERROR! LearningActivity, path: " + path);
			continue;
		}
		
		System.out.println("    Learning Activity: " + larn.getTitle(Locale.getDefault()) + " (" + LanguageUtil.get(Locale.getDefault(),learningActivityTypeRegistry.getLearningActivityType(larn.getTypeId()).getName())+")" );
		
		serviceContext.setAssetCategoryIds(context.getAssetCategoryIds(LearningActivity.class, larn.getActId()));
		serviceContext.setAssetTagNames(context.getAssetTagNames(LearningActivity.class, larn.getActId()));
		serviceContext.setUserId(userId);
		serviceContext.setCompanyId(context.getCompanyId());
		serviceContext.setScopeGroupId(context.getScopeGroupId());
		
		larn.setGroupId(theModule.getGroupId());
		larn.setModuleId(theModule.getModuleId());
		
	

		LearningActivity nuevaLarn=LearningActivityLocalServiceUtil.addLearningActivity(larn,serviceContext);
		serviceContext.setScopeGroupId(nuevaLarn.getGroupId());
		//Cambios Miguel para importar bien los ficheros asociados a recursos externos.
		HashMap<String, String> map = LearningActivityLocalServiceUtil.convertXMLExtraContentToHashMap(nuevaLarn.getActId());
		Iterator <String> keysString =  map.keySet().iterator();
		int index = 0;
		while (keysString.hasNext()){
			String key = keysString.next();
			System.out.println("KEY: "+key);
			
			if(!key.equals("video") && key.indexOf("document")!=-1){
				
				System.out.println("PASADO POR AQUI "+index);
				index++;
				long assetEntryIdOld =  Long.parseLong(map.get(key));
				
				AssetEntry docAssetOLD= AssetEntryLocalServiceUtil.getAssetEntry(assetEntryIdOld);
				DLFileEntry oldFile=DLFileEntryLocalServiceUtil.getDLFileEntry(docAssetOLD.getClassPK());
				
				InputStream inputStream = DLFileEntryLocalServiceUtil.getFileAsStream(userId, oldFile.getFileVersion().getFileEntryId(), oldFile.getFileVersion().getVersion());
				byte[] byteArray = null;
				try {
					byteArray = IOUtils.toByteArray(inputStream);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
					long repositoryId = DLFolderConstants.getDataRepositoryId(theModule.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
					
					long dlMainFolderId = 0;
					 boolean dlMainFolderFound = false;
				        //Get main folder
				        try {
				        	//Get main folder
				        	Folder dlFolderMain = DLAppLocalServiceUtil.getFolder(repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,DOCUMENTLIBRARY_MAINFOLDER+nuevaLarn.getActId());
				        	dlMainFolderId = dlFolderMain.getFolderId();
				        	dlMainFolderFound = true;
				        	//Get portlet folder
				        } catch (Exception ex){
				        }
				        
						//Damos permisos al archivo para usuarios de comunidad.
						serviceContext.setAddGroupPermissions(true);
				        
				        //Create main folder if not exist
				        if(!dlMainFolderFound){
				        	Folder newDocumentMainFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, DOCUMENTLIBRARY_MAINFOLDER+nuevaLarn.getActId(), DOCUMENTLIBRARY_MAINFOLDER+nuevaLarn.getActId(), serviceContext);
				        	dlMainFolderFound = true;
				        	dlMainFolderId = newDocumentMainFolder.getFolderId();
				        }
					


					
					String ficheroExtStr = "";
					String extension[] = oldFile.getTitle().split("\\.");
					if(extension.length > 0){
						ficheroExtStr = "."+extension[extension.length-1];
					}
				
					FileEntry newFile = DLAppLocalServiceUtil.addFileEntry(
							userId, repositoryId , dlMainFolderId , oldFile.getTitle()+ficheroExtStr, oldFile.getMimeType(), 
						oldFile.getTitle(), StringPool.BLANK, StringPool.BLANK, byteArray , serviceContext ) ;

				
					//AssetEntry asset  = AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), newFile.getPrimaryKey());
					//System.out.println("      DLFileEntry newFile: "+newFile.getTitle()+", newFile PrimaryKey: "+newFile.getPrimaryKey()+", EntryId: "+asset.getEntryId());
					
					map.put(key, String.valueOf(AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), newFile.getPrimaryKey()).getEntryId()));
					
				
					
					Role siteMemberRole = RoleLocalServiceUtil.getRole(context.getCompanyId(), RoleConstants.SITE_MEMBER);
					ResourcePermissionLocalServiceUtil.setResourcePermissions(context.getCompanyId(), LearningActivity.class.getName(), 
							ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(nuevaLarn.getActId()),siteMemberRole.getRoleId(), new String[] {ActionKeys.VIEW});
				
				
			
			}
					
				
					//Ponemos a la actividad el fichero que hemos recuperado.
					
					if(larn.getTypeId() == 2){
					
					LearningActivityLocalServiceUtil.saveHashMapToXMLExtraContent(nuevaLarn.getActId(), map);
					//LearningActivityLocalServiceUtil.updateLearningActivity(nuevaLarn);
					
					
					
					//System.out.println("AL FINAL QUEDA ASI: "+nuevaLarn.getExtracontent());
				
					}else if(larn.getTypeId() == 7){
						//LearningActivityLocalServiceUtil.setExtraContentValue(nuevaLarn.getActId(), "assetEntry", String.valueOf(asset.getEntryId()));
					}
				
					
				
				
			}
		
		// FIN CAMBIO MIGUEL
		//Importar las imagenes de los resources.
		
		Iterator<Element> it = actElement.elementIterator("dlfileentry");
		
		while(it.hasNext()){
			
		Element theElement = it.next();
		
		if(theElement.element("dlfileentry") != null){
			
			AssetEntry asset = null;
			FileEntry newFile = null;
			DLFolder dlFolder = null;
			DLFileEntry oldFile = null;
			
			String messageException = "";
			try {
				//System.out.println("   dlfileentry path: "+actElement.element("dlfileentry").attributeValue("path"));
				
				//Crear el folder
				dlFolder = createDLFoldersForLearningActivity(userId, context.getScopeGroupId(), nuevaLarn.getActId(), nuevaLarn.getTitle(Locale.getDefault()), serviceContext);
				//System.out.println("    DLFolder dlFolder: "+dlFolder.getFolderId()+", title: "+dlFolder.getName());
				
				//Recuperar el fichero del xml.
				//InputStream is = context.getZipEntryAsInputStream(actElement.element("dlfileentry").attributeValue("file"));
				//System.out.println("    InputStream file: "+is.toString());
				byte [] byteArray = context.getZipEntryAsByteArray(theElement.element("dlfileentry").attributeValue("file"));
					
				//Obtener los datos del dlfileentry del .lar para poner sus campos igual. 
				oldFile = (DLFileEntry) context.getZipEntryAsObject(theElement.element("dlfileentry").attributeValue("path"));
				System.out.println("    DLFileEntry file: "+oldFile.getTitle()+",getFileEntryId "+oldFile.getFileEntryId()+",getFolderId "+oldFile.getFolderId()+",getGroupId "+oldFile.getGroupId());
				
				messageException = "\n      - oldFile title: "+oldFile.getTitle()+ ", extension: "+oldFile.getExtension()+ ", mimetype: "+oldFile.getMimeType()+ ", size: "+oldFile.getSize()+" - ";		

				String ficheroStr = theElement.element("dlfileentry").attributeValue("file");	
				String ficheroExtStr = "";
				String extension[] = ficheroStr.split("\\.");
				if(extension.length > 0){
					ficheroExtStr = "."+extension[extension.length-1];
				}
				
				long repositoryId = DLFolderConstants.getDataRepositoryId(larn.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
				newFile = DLAppLocalServiceUtil.addFileEntry(
						serviceContext.getUserId(), repositoryId , dlFolder.getFolderId() , oldFile.getTitle()+ficheroExtStr, oldFile.getMimeType(), 
						oldFile.getTitle(), StringPool.BLANK, StringPool.BLANK, byteArray , serviceContext ) ;

				messageException += "\n      - newFile title: "+newFile.getTitle()+ ", extension: "+newFile.getExtension()+ ", extension: "+newFile.getFolderId()+ ", size: "+newFile.getSize()+" - ";
				
				asset = AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), newFile.getPrimaryKey());
				System.out.println("      DLFileEntry newFile: "+newFile.getTitle()+", newFile PrimaryKey: "+newFile.getPrimaryKey()+", EntryId: "+asset.getEntryId());
				
				
				//Ponemos a la actividad el fichero que hemos recuperado.
				System.out.println("    Extracontent : \n"+nuevaLarn.getExtracontent());
				if(larn.getTypeId() == 2){
					System.out.println("TIPO EXTERNO");
					LearningActivityLocalServiceUtil.setExtraContentValue(nuevaLarn.getActId(), "document", String.valueOf(asset.getEntryId()));
				}else if(larn.getTypeId() == 7){
					LearningActivityLocalServiceUtil.setExtraContentValue(nuevaLarn.getActId(), "assetEntry", String.valueOf(asset.getEntryId()));
				}
				
				Long newActId = nuevaLarn.getActId();
				nuevaLarn = LearningActivityLocalServiceUtil.getLearningActivity(newActId);
				
			}catch(FileExtensionException fee){
				fee.printStackTrace();
				System.out.println("*ERROR! dlfileentry path FileExtensionException:" + actElement.element("dlfileentry").attributeValue("path")+", "+messageException +", message: "+fee.getMessage());
			}catch(FileSizeException fse){
				System.out.println("*ERROR! dlfileentry path FileSizeException:" + actElement.element("dlfileentry").attributeValue("path")+messageException +", message: "+ fse.getMessage());
			} catch(DuplicateFileException dfl){
				newFile = DLAppLocalServiceUtil.getFileEntry(context.getScopeGroupId(), dlFolder.getFolderId(), oldFile.getTitle());
				System.out.println("*ERROR! dlfileentry path DuplicateFileException:" + actElement.element("dlfileentry").attributeValue("path")+messageException +", message: "+ dfl.getMessage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("*ERROR! dlfileentry path: " + actElement.element("dlfileentry").attributeValue("path")+messageException +", message: "+e.getMessage());
			}

		}
	}
		
		//Si tenemos ficheros en las descripciones de las actividades
		for (Element actElementFile : actElement.elements("descriptionfile")) {
			
			FileEntry oldFile = (FileEntry)context.getZipEntryAsObject(actElementFile.attributeValue("path"));
			
			System.out.println("*  Description File: " + oldFile.getTitle()); 
									
			FileEntry newFile;
			long folderId=0;
			String description = "";
			
			try {
				
				InputStream input = context.getZipEntryAsInputStream(actElementFile.attributeValue("file"));
				
				long repositoryId = DLFolderConstants.getDataRepositoryId(context.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
				folderId=createDLFolders(userId,repositoryId,serviceContext);
				
				String ficheroStr = actElementFile.attributeValue("file");	
				String ficheroExtStr = "";
				String extension[] = ficheroStr.split("\\.");
				if(extension.length > 0){
					ficheroExtStr = "."+extension[extension.length-1];
				}
				
				System.out.println("*   getMimeType getMimeType: " + oldFile.getMimeType()); 
				System.out.println("*   getExtension getExtension: " + oldFile.getExtension()); 
				
			
				
				String titleFile=oldFile.getTitle();
				if(!oldFile.getTitle().endsWith(oldFile.getExtension())){
					titleFile=oldFile.getTitle()+"."+oldFile.getExtension();
				} 
				
				System.out.println("*   titleFile titleFile: " + titleFile); 
				newFile = DLAppLocalServiceUtil.addFileEntry(userId, repositoryId , folderId , titleFile, oldFile.getMimeType(), oldFile.getTitle(), StringPool.BLANK, StringPool.BLANK, IOUtils.toByteArray(input), serviceContext );
									
				description = descriptionFileParserLarToDescription(nuevaLarn.getDescription(), oldFile, newFile);
				
			} catch(DuplicateFileException dfl){
				
				try{
								
					
					FileEntry existingFile = DLAppLocalServiceUtil.getFileEntry(context.getScopeGroupId(), folderId, oldFile.getTitle());
					description = descriptionFileParserLarToDescription(nuevaLarn.getDescription(), oldFile, existingFile);
				}catch(Exception e){
					System.out.println("ERROR! descriptionfile descriptionFileParserLarToDescription : " +e.getMessage());
					description = nuevaLarn.getDescription();
				}
			} catch (PortletDataException e1){
				System.out.println("ERROR! descriptionfile: ");
				
			} catch (Exception e) {

				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("ERROR! descriptionfile: " + actElementFile.attributeValue("file") +"\n        "+e.getMessage());
			}

			nuevaLarn.setDescription(description);
			LearningActivityLocalServiceUtil.updateLearningActivity(nuevaLarn);
			
		}
	
		for(Element qElement:actElement.elements("question"))
		{
			String pathq = qElement.attributeValue("path");
				
			TestQuestion question=(TestQuestion)context.getZipEntryAsObject(pathq);
			question.setActId(nuevaLarn.getActId());
			TestQuestion nuevaQuestion=TestQuestionLocalServiceUtil.addQuestion(question.getActId(), question.getText(), question.getQuestionType());
			
			System.out.println("      Test Question: " + nuevaQuestion.getQuestionId() /*Jsoup.parse(nuevaQuestion.getText()).text()*/);
			
			//Si tenemos ficheros en las descripciones de las preguntas.
			for (Element actElementFile : qElement.elements("descriptionfile")) {
				
				FileEntry oldFile = (FileEntry)context.getZipEntryAsObject(actElementFile.attributeValue("path"));
										
				FileEntry newFile;
				long folderId=0;
				String description = "";
				
				try {
					
					InputStream input = context.getZipEntryAsInputStream(actElementFile.attributeValue("file"));
					
					long repositoryId = DLFolderConstants.getDataRepositoryId(context.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
					folderId=createDLFolders(userId,repositoryId,serviceContext);
					
					String ficheroStr = actElementFile.attributeValue("file");	
					String ficheroExtStr = "";
					String extension[] = ficheroStr.split("\\.");
					if(extension.length > 0){
						ficheroExtStr = "."+extension[extension.length-1];
					}
					
					newFile = DLAppLocalServiceUtil.addFileEntry(userId, repositoryId , folderId , oldFile.getTitle()+ficheroExtStr, oldFile.getMimeType(), oldFile.getTitle(), StringPool.BLANK, StringPool.BLANK, IOUtils.toByteArray(input), serviceContext );
					
					description = descriptionFileParserLarToDescription(nuevaQuestion.getText(), oldFile, newFile);
					
				} catch(DuplicateFileException dfl){
					
					FileEntry existingFile = DLAppLocalServiceUtil.getFileEntry(context.getScopeGroupId(), folderId, oldFile.getTitle());
					description = descriptionFileParserLarToDescription(nuevaQuestion.getText(), oldFile, existingFile);
					
				} catch (Exception e) {

					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println("* ERROR! Question descriptionfile: " + e.getMessage());
				}
				//System.out.println("   description : " + description );
				nuevaQuestion.setText(description);
				TestQuestionLocalServiceUtil.updateTestQuestion(nuevaQuestion);
				
			}
			
			QuestionType qt =new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
			qt.importQuestionAnswers(context, qElement, nuevaQuestion.getQuestionId(), userId, serviceContext);
		}
	}
	
}

	private DLFolder getMainDLFolder(Long userId, Long groupId, ServiceContext serviceContext) throws PortalException, SystemException{

		DLFolder mainFolder = null;

		try {

			mainFolder = DLFolderLocalServiceUtil.getFolder(groupId,0,"ResourceUploads");

        } catch (Exception ex){

        	long repositoryId = DLFolderConstants.getDataRepositoryId(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
        	//mountPoint -> Si es carpeta ra�z.
        	mainFolder = DLFolderLocalServiceUtil.addFolder(userId, groupId, repositoryId, true, 0, "ResourceUploads", "ResourceUploads", serviceContext);
        }
  
        return mainFolder;
	}
	
	/**
	 * Primero se busca si ya existe, si existe se devuelve y sino se crea uno nuevo.
	 */
	private DLFolder createDLFoldersForLearningActivity(Long userId, Long groupId, Long actId, String title, ServiceContext serviceContext) throws PortalException, SystemException{
		
		DLFolder newDLFolder = null;
		
		try {

			DLFolder dlMainFolder = getMainDLFolder(userId, groupId, serviceContext);
			
			//A partir de ahora, guardamos los ficheros en el "Resource Uploads".
			return dlMainFolder;
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("* ERROR! createDLFoldersForLearningActivity: " + e.getMessage());
		}
		
    	return newDLFolder;
	}
	
	public long createDLFolders(Long userId,Long repositoryId,ServiceContext serviceContext) throws PortalException, SystemException{
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
        } catch (Exception e){
        	//e.printStackTrace()
        	dlMainFolderFound = false;
        }
        
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

	
	public void descriptionFileParserDescriptionToLar(String description, long oldGroupId, long moduleId, PortletDataContext context, Element element){

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
								
								System.out.println("   + Description file image : " + file.getTitle() +" ("+file.getMimeType()+")");
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
								System.out.println("* ERROR! Description file image : " + e.getMessage());
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

								String titleFile=file.getTitle();
								if(!file.getTitle().endsWith(file.getExtension())){
									titleFile=file.getTitle()+"."+file.getExtension();
								} 
								
								
								
								Element entryElementLoc= element.addElement("descriptionfile");
								entryElementLoc.addAttribute("path", pathqu);
								entryElementLoc.addAttribute("file", pathFile + file.getTitle());
								
								//System.out.println("titleFile =============="+titleFile);
								//System.out.println("getMimeType =============="+file.getMimeType());
								//System.out.println("file.getExtension =============="+file.getExtension());
							
								
									
							} catch (Exception e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
								System.out.println("* ERROR! Description file pdf : " + e.getMessage());
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
			//e.printStackTrace();
			System.out.println("* ERROR! Document Exception : " + e.getMessage());
		}

	}
	
	private void parseImage(Element imgElement, Element element, PortletDataContext context, Long moduleId){
	
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
				
				System.out.println("   + Description file image : " + file.getTitle() +" ("+file.getMimeType()+")");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("* ERROR! Description file image : " + e.getMessage());
			}
		}
		
	
	}
	
	public String descriptionFileParserLarToDescription(String description, FileEntry oldFile, FileEntry newFile){
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
		
		//System.out.println("   res         : " + res );
		if(res.equals(description)){
			System.out.println("   :: description         : " + description );
			//System.out.println("   :: target      : " + target );	
			//System.out.println("   :: replacement : " + replacement );
		}
				
		String changed = (!res.equals(description))?" changed":" not changed";
		
		System.out.println("   + Description file : " + newFile.getTitle() +" (" + newFile.getMimeType() + ")" + changed);
		
		return res;
	}

	public String containsCharUpper(String str) {
	    
	    String characters = "�����";

	    for (int i=0; i<characters.length(); i++) {
	    	if(str.contains(String.valueOf(characters.charAt(i)))){
	    		return str.toLowerCase();
	    	}
	    }
	    return str;
	}
	/*
	private String parseFilesFromDescription(String description, List<Element> elements, long userId, PortletDataContext context, ServiceContext serviceContext){
		
		FileEntry newFile;
		long folderId=0;
		String res = description;
		
		//Si tenemos ficheros en las descripciones de las actividades
		for (Element actElementFile : elements) {
			
			FileEntry oldFile = (FileEntry)context.getZipEntryAsObject(actElementFile.attributeValue("path"));
			
			//System.out.println("      Description File: " + oldFile.getTitle()); 

			try {
				
				InputStream input = context.getZipEntryAsInputStream(actElementFile.attributeValue("file"));
				
				long repositoryId = DLFolderConstants.getDataRepositoryId(context.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
				folderId=createDLFolders(userId,repositoryId,serviceContext);
				
				newFile = DLAppLocalServiceUtil.addFileEntry(userId, repositoryId , folderId , oldFile.getTitle(), "contentType", oldFile.getTitle(), StringPool.BLANK, StringPool.BLANK, IOUtils.toByteArray(input), serviceContext );
									
				res = descriptionFileParserLarToDescription(description, oldFile, newFile);
				
			} catch(DuplicateFileException dfl){
				
				try{
					FileEntry existingFile = DLAppLocalServiceUtil.getFileEntry(context.getScopeGroupId(), folderId, oldFile.getTitle());
					res = descriptionFileParserLarToDescription(description, oldFile, existingFile);
				}catch(Exception e){
					System.out.println("ERROR! descriptionfile descriptionFileParserLarToDescription : " +e.getMessage());
				}
				
			} catch (Exception e) {
				//e.printStackTrace();
			}

		}
		return res;
	}*/
	
	
}
