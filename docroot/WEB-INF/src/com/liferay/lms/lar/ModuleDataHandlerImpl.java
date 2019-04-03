package com.liferay.lms.lar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;

import org.apache.commons.lang.StringUtils;

import com.liferay.lms.NoLearningActivityTypeActiveException;
import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
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
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.util.LmsLocaleUtil;


public class ModuleDataHandlerImpl extends BasePortletDataHandler {
	
	private static Log log = LogFactoryUtil.getLog(ModuleDataHandlerImpl.class);

	
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
		
		@Override
		protected PortletPreferences doDeleteData(PortletDataContext context,
		String portletId, PortletPreferences preferences) throws Exception {
		
		log.info("\n-----------------------------\ndoDeleteData STARTS");
		
		try {
			String groupIdStr = String.valueOf(context.getScopeGroupId());
			
			Group group = GroupLocalServiceUtil.getGroup(context.getScopeGroupId());
			
			long groupId = 0;
			
			if(Validator.isNumber(groupIdStr)){
				groupId = Long.parseLong(groupIdStr);
			}
			long repositoryId = DLFolderConstants.getDataRepositoryId(group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			
			log.info("  Course: "+ group.getName()+ ", groupId: " + repositoryId);
			
			List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(groupId);
	
			for(Module module:modules){
				
				log.info("    Module : " + module.getTitle(Locale.getDefault()) );
				
				List<LearningActivity> activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(module.getModuleId());
				
				for(LearningActivity activity:activities){
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
	
		log.info("doDeleteData ENDS\n-----------------------------\n");
		
		return super.doDeleteData(context, portletId, preferences);
	}
	
	@Override
	protected String doExportData(PortletDataContext context, String portletId, PortletPreferences preferences) throws Exception {
		
		log.info("\n-----------------------------\ndoExportData STARTS, groupId : " + context.getScopeGroupId() );
		
		Group group = GroupLocalServiceUtil.getGroup(context.getScopeGroupId());
		log.info(" Course: "+ group.getName());
		
		
		context.addPermissions("com.liferay.lms.model.module", context.getScopeGroupId());
		
		Document document = SAXReaderUtil.createDocument();
	
		Element rootElement = document.addElement("moduledata");
	
		rootElement.addAttribute("group-id", String.valueOf(context.getScopeGroupId()));
		
		List<Module> entries = ModuleLocalServiceUtil.findAllInGroup(context.getScopeGroupId());
		long entryOld=0L;
		for (Module entry : entries) {
			log.info("++++++++++++++ModuleDataHandlerImpl::Module "+entry.getModuleId());
			log.info("++++++++++++++ModuleDataHandlerImpl::Module entryOld "+entryOld);
			
			if(entryOld!=entry.getModuleId()){
				entryOld=entry.getModuleId();
				exportEntry(context, rootElement, entry);
			} else{
				log.info("ModuleDataHandlerImpl::Repetido el modulo "+entry.getModuleId());
	
			}
		}
	
		log.info("\n-----------------------------\ndoExportData ENDS, modules:" + entries.size() + "\n-----------------------------\n"  );
		
		return document.formattedString();
	}
	
	private void exportEntry(PortletDataContext context, Element root, Module entry) throws PortalException, SystemException {
		
		String path = getEntryPath(context, entry);
		
		log.info("----------- Module: " + entry.getModuleId() +" "+ entry.getTitle(Locale.getDefault()) );
		
		
		if (!context.isPathNotProcessed(path)) {
			return;
		}
	
		Element entryElement = root.addElement("moduleentry");
	
		entryElement.addAttribute("path", path);
		
		log.debug("--------------moduleenttry path:"+path);
	
		context.addPermissions(Module.class, entry.getModuleId());
		
		entry.setUserUuid(entry.getUserUuid());
		context.addZipEntry(path, entry);
		
		if(entry.getIcon() > 0){
			
			String pathFileModule = getFileToIS(context, entry.getIcon(), entry.getModuleId());
			entryElement.addAttribute("file", pathFileModule);
		}
		
		//Exportar los ficheros que tenga la descripci�n del modulo.
		ExportUtil.descriptionFileParserDescriptionToLar(entry.getDescription(), entry.getGroupId(), entry.getModuleId(), context, entryElement);		
		
		LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
		List<LearningActivity> actividades=LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(entry.getModuleId());
		
		for(LearningActivity actividad:actividades){
			
			log.info(" **************    Learning Activity: " + actividad.getTitle(Locale.getDefault()) + " (" + LanguageUtil.get(Locale.getDefault(),learningActivityTypeRegistry.getLearningActivityType(actividad.getTypeId()).getName())+")" );
			
			log.debug("*******ActID:"+actividad.getActId());
			
			String pathlo = getEntryPath(context, actividad);
			Element entryElementLoc= entryElement.addElement("learningactivity");			
			entryElementLoc.addAttribute("path", pathlo);
			log.debug("--------------learningactivity path:"+pathlo);
			
			
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
			
			
			//Exportar los ficheros que tenga la descripcion de la actividad.
			ExportUtil.descriptionFileParserDescriptionToLar(actividad.getDescription(), actividad.getGroupId(), actividad.getModuleId(), context, entryElementLoc);		
		
			//Exportar las imagenes de los resources.
			if(actividad.getTypeId() == 2 || actividad.getTypeId() == 7|| actividad.getTypeId() == 9 ){
				List<String> img = new LinkedList<String>();
				if(actividad.getTypeId() == 2){
					img = LearningActivityLocalServiceUtil.getExtraContentValues(actividad.getActId(), "document");
				}else if(actividad.getTypeId() == 7 || actividad.getTypeId() == 9){
					log.info("ENTRO CON "+actividad.getTypeId());
					img.add(LearningActivityLocalServiceUtil.getExtraContentValue(actividad.getActId(), "assetEntry"));
				}
				
				try {
					log.info("actividad.getTypeId() "+actividad.getTypeId());

					for(int i=0;i<img.size();i++){
						log.info("img "+img);
						String content = img.get(i);
						//Comprobamos que sea un long
						if(StringUtils.isNumeric(content)){
							log.info("*** Es un id:"+content);
							ExportUtil.addZipEntry(actividad, Long.valueOf(img.get(i)), context, entryElementLoc);
						}
					}
						
	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("* ERROR! resource file: " + e.getMessage());
				}
				
			}
			
			context.addZipEntry(pathlo, actividad);
			
			List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actividad.getActId());
			
			for(TestQuestion question:questions){
	
				String pathqu = getEntryPath(context, question);
				Element entryElementq= entryElementLoc.addElement("question");
				entryElementq.addAttribute("path", pathqu);
				context.addZipEntry(pathqu, question);
				
				log.info("      Test Question: " + question.getQuestionId() );
				
				log.info("question.getText():\n" + question.getText());
				//Exportar los ficheros que tiene la descripcion de la pregunta
				ExportUtil.descriptionFileParserDescriptionToLar("<root><Description>"+question.getText()+"</Description></root>", actividad.getGroupId(), actividad.getModuleId(), context, entryElementq);	
				
				QuestionType qt =new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
				qt.exportQuestionAnswers(context, entryElementq, question.getQuestionId(), actividad);
	
			}
			
		}
		
	}
	
		
	@Override
	protected PortletPreferences doImportData(PortletDataContext context, String portletId, PortletPreferences preferences, String data) throws Exception {
		
		
		context.importPermissions("com.liferay.lms.model.module", context.getSourceGroupId(),context.getScopeGroupId());
		log.info("\n-----------------------------\ndoImport1Data STARTS12");
		
		
		Document document = SAXReaderUtil.read(data);
	
		Element rootElement = document.getRootElement();
		long entryOld=0;
		long newModuleId = 0;
		String path = null;
		Module entry = null;
		
		//Creamos un hashmap donde guardaremos la relación de los ids antiguos y los nuevos para poder cambiar las actividades precedentes
		HashMap<Long,Long> relationModule = new HashMap<Long, Long>();
		HashMap<Long,Long> relationActivities = new HashMap<Long, Long>();
		
		//Tipos de actividades permitidas en la plataforma:
		LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
		HashMap<Integer, LearningActivityType> hashLearningActivityType = learningActivityTypeRegistry.getLearningActivityTypesForCreatingHash();
		
		for (Element entryElement : rootElement.elements("moduleentry")) {
			path = entryElement.attributeValue("path");
			
			if (context.isPathNotProcessed(path)) {
				entry = (Module)context.getZipEntryAsObject(path);
				
				if(entryOld != entry.getModuleId()){ 
					entryOld=entry.getModuleId();
					newModuleId = importEntry(context,entryElement, entry, relationActivities, hashLearningActivityType);
					relationModule.put(entryOld, newModuleId);
				} else{
					log.info("repetidooooo el modulo "+entry.getModuleId());
				}	
			}
		}
		
		ImportUtil.updateModuleIds(context.getScopeGroupId(), relationModule);
		
		ImportUtil.updateActivityIds(context.getScopeGroupId(), relationActivities);

		
		log.info("doImportData ENDS" + "\n-----------------------------\n"  );
		
		return null;
	}
	
	/**
	 * Llamamos a esta función con el nodo moduleentry del archivo portlet-dat.xml dentro de \groups\xxxx\portlets\courseadmin_WAR_liferaylmsportlet
	 * @param context todo el war
	 * @param entryElement elemento moduleentry
	 * @param entry modulo cogido del war, de momento contiene los ids antiguos 
	 * @param hashLearningActivityType 
	 * @param relationActivities 
	 * @return 
	 * @throws SystemException
	 * @throws PortalException
	 * @throws DocumentException
	 */
	
	private long importEntry(PortletDataContext context, Element entryElement, Module entry, HashMap<Long, Long> relationActivities, HashMap<Integer, LearningActivityType> hashLearningActivityType ) throws SystemException, PortalException {
		
		long userId = context.getUserId(entry.getUserUuid());
		
		ServiceContext serviceContext=new ServiceContext();
		serviceContext.setUserId(userId);
		serviceContext.setCompanyId(context.getCompanyId());
		serviceContext.setScopeGroupId(context.getScopeGroupId());
		
		
		//Cargamos los datos del contexto del curso actual, sobre el que vamos a importar
		entry.setGroupId(context.getScopeGroupId());
		entry.setUserId(userId);
		entry.setCompanyId(context.getCompanyId());
			
		//Para convertir < correctamente.
		entry.setDescription(entry.getDescription().replace("&amp;lt;", "&lt;"));
		
		log.info("entry companyId: " + entry.getCompanyId());
		log.info("entry groupId: " +entry.getGroupId()  );
		log.info("entry userId: " +entry.getUserId()  );
		log.info("entry moduleId: " +entry.getModuleId()  );
		
		
		log.info("ENTRY ELEMENT-->"+entryElement);
		Module newModule = null;
		
		//Comprobamos si el módulo ya existe (por uuid) si ya existe lo actualizamos, no lo creamos de nuevo
		try{
			newModule = ModuleLocalServiceUtil.getModuleByUuidAndGroupId(entry.getUuid(), context.getScopeGroupId());
		}catch(PortalException | SystemException e){
			log.debug("Modulo no existe, lo creamos");
		}
		
		//Creamos el nuevo módulo en el curso
		if(newModule == null){
			newModule = ModuleLocalServiceUtil.addmodule(entry);
		}else{
			newModule = ModuleLocalServiceUtil.updateModule(newModule, entry);
		}
		
		//Importamos las imagenes de los módulos
		ModuleImport.importImageModule(context, entryElement, serviceContext, userId, newModule);
		
		//Importamos los archivos de la descripción
		ModuleImport.importDescriptionFile(context, entryElement, serviceContext, userId, newModule);
		
		//Importamos las actividades
		long newActId = 0;
		LearningActivity larn = null;
		String path = null;
		
		for (Element actElement : entryElement.elements("learningactivity")) {
			path = actElement.attributeValue("path");
			
			larn=(LearningActivity)context.getZipEntryAsObject(path);
			
			log.info("tipo a comprobar: " + larn.getTypeId());
			if(hashLearningActivityType.containsKey(larn.getTypeId())){
				log.info("actividad correcta: " + larn.getTypeId());
				newActId = LearningActivityImport.importLearningActivity(context, entryElement, serviceContext, userId, newModule, actElement, larn);
				//Comprobamos que podamos crear una actividad de ese tipo
				relationActivities.put(larn.getActId(), newActId);
			}else{
				log.info("actividad incorrecta: " + larn.getTypeId());
				throw new NoLearningActivityTypeActiveException("No existe el tipo de actividad: "  + larn.getTypeId());
			}
		}
		
		log.info("Fin importación de módulo: " + newModule.getModuleId());
		
		return newModule.getModuleId();
		
	}

	public String containsCharUpper(String str) {
	    return ExportUtil.changeSpecialCharacter(str);
	    /*
	     str = str.replaceAll("[^a-zA-Z0-9]", "");
	    return str;
	    */
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
			log.info("* ERROR! getFileToIS: " + e.getMessage());
		}	
		
		return "";
	}
	
	private String getImgModulePath(PortletDataContext context, long moduleId) {
		
		StringBundler sb = new StringBundler(4);	
		sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
		sb.append("/moduleentries/module_img/"+String.valueOf(moduleId)+"/");
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
	

	
}
