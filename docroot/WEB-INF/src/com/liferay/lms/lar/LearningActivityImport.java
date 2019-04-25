package com.liferay.lms.lar;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.learningactivity.questiontype.QuestionType;
import com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.lms.util.DLFolderUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

public class LearningActivityImport {

	private static Log log = LogFactoryUtil.getLog(LearningActivityImport.class);
	
	/**
	 * Función para importar las actividades del módulo
	 * @param context 
	 * @param entryElement elemento módulo
	 * @param serviceContext
	 * @param userId
	 * @param newModule módulo nuevo al que pertenecerá
	 * @param actElement elemento actividad
	 * @throws SystemException 
	 * @throws PortalException 
	 */
	public static long importLearningActivity(PortletDataContext context, Element entryElement, ServiceContext serviceContext, long userId, Module newModule, Element actElement, LearningActivity larn) throws SystemException, PortalException {
	
		if(larn != null){
			
			log.info("*********************ACTIVIDAD ID ANTIGUO: " + larn.getActId() + "**************************");
			log.info("***************TIPO DE ACTIVIDAD: " + larn.getTypeId() + "**************************");
			
			serviceContext.setAssetCategoryIds(context.getAssetCategoryIds(LearningActivity.class, larn.getActId()));
			serviceContext.setAssetTagNames(context.getAssetTagNames(LearningActivity.class, larn.getActId()));
			serviceContext.setUserId(userId);
			serviceContext.setCompanyId(context.getCompanyId());
			serviceContext.setScopeGroupId(context.getScopeGroupId());
			
			larn.setGroupId(newModule.getGroupId());
			larn.setModuleId(newModule.getModuleId());
	
			//Comprobamos si la actividad ya existe para actualizarla o para crearla nueva
			LearningActivity newLarn = null;
			
			try{
				newLarn = LearningActivityLocalServiceUtil.getLearningActivityByUuidAndGroupId(larn.getUuid(), newModule.getGroupId());
			}catch(PortalException | SystemException e){
				log.debug("La actividad no existe, la creamos");
			}
			
			if(newLarn == null){
				newLarn=LearningActivityLocalServiceUtil.addLearningActivity(larn,serviceContext);
			}else{
				newLarn=LearningActivityLocalServiceUtil.updateLearningActivity(newLarn, larn,serviceContext);
			}

			serviceContext.setScopeGroupId(newLarn.getGroupId());
			
			//Seteo de contenido propio de la actividad
			log.debug("***IMPORT EXTRA CONTENT****");
			LearningActivityType learningActivityType=new LearningActivityTypeRegistry().getLearningActivityType(larn.getTypeId());
			
			String importExtraContentResult = null;
			try {
				importExtraContentResult = learningActivityType.importExtraContent(newLarn, userId, context, serviceContext, actElement);
			} catch (Exception e) {
				e.printStackTrace();
			} 

			log.debug("++++ importExtraContentResult:"+importExtraContentResult);
			
			log.debug("***FIN IMPORT EXTRA CONTENT****");
			
			importDLFileEntries(newLarn, userId, context, serviceContext, actElement);
			
			importDescriptionFileActivities(newLarn, userId, context, serviceContext, actElement);

			importQuestions(newLarn, userId, context, serviceContext, actElement);

			return newLarn.getActId();
		}else{
		
			return 0;
		
		}
	}


	private static void importQuestions(LearningActivity newLarn, long userId, PortletDataContext context, ServiceContext serviceContext, Element actElement) throws SystemException, PortalException {
		//Guardamos los identificadores en un hashmap para actualizar el orden de las preguntas
		HashMap<Long, Long> questionIdsMap = new HashMap<Long, Long>();
		
		for(Element qElement:actElement.elements("question")){
			String pathq = qElement.attributeValue("path");
				
			TestQuestion question=(TestQuestion)context.getZipEntryAsObject(pathq);
			question.setActId(newLarn.getActId());
			TestQuestion nuevaQuestion=TestQuestionLocalServiceUtil.addQuestion(question.getActId(), question.getText(), question.getQuestionType());
			
			if(question.getWeight() != question.getQuestionId()){
				nuevaQuestion.setWeight(question.getWeight());
				nuevaQuestion = TestQuestionLocalServiceUtil.updateTestQuestion(nuevaQuestion);
				questionIdsMap.put(question.getQuestionId(), nuevaQuestion.getQuestionId());
			}
			
			questionIdsMap.put(question.getQuestionId(), nuevaQuestion.getQuestionId());
			log.info("      Test Question: " + nuevaQuestion.getQuestionId() /*Jsoup.parse(nuevaQuestion.getText()).text()*/);
			
			//Si tenemos ficheros en las descripciones de las preguntas.
			for (Element actElementFile : qElement.elements("descriptionfile")) {
										
				String description = importDescriptionFile(nuevaQuestion.getText(), actElementFile, userId, context, serviceContext);

				//log.info("   description : " + description );
				nuevaQuestion.setText(description);
				nuevaQuestion = TestQuestionLocalServiceUtil.updateTestQuestion(nuevaQuestion);
				
			}
			
			QuestionType qt =new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
			qt.importQuestionAnswers(context, qElement, nuevaQuestion.getQuestionId(), userId, serviceContext);
		}
		
		List<TestQuestion> questions = TestQuestionLocalServiceUtil.getQuestions(newLarn.getActId());
		for(TestQuestion question: questions){
			if(question.getWeight() > 0 && questionIdsMap.containsKey(question.getWeight())){
				log.debug("nuevo orden: " + questionIdsMap.get(question.getWeight()));
				question.setWeight(questionIdsMap.get(question.getWeight()));
				TestQuestionLocalServiceUtil.updateTestQuestion(question);
			}
		}
	}

	/**
	 * Importación de ficheros incluidos en la descripción de las actividades
	 * @param newLarn
	 * @param userId
	 * @param context
	 * @param serviceContext
	 * @param actElement
	 * @throws SystemException
	 */
	private static void importDescriptionFileActivities(LearningActivity newLarn, long userId, PortletDataContext context, ServiceContext serviceContext, Element actElement) throws SystemException {
		//Si tenemos ficheros en las descripciones de las actividades
		for (Element actElementFile : actElement.elements("descriptionfile")) {
			
			String description = importDescriptionFile(newLarn.getDescription(), actElementFile, userId, context, serviceContext);

			newLarn.setDescription(description);
			LearningActivityLocalServiceUtil.updateLearningActivity(newLarn);
			
		}
	}
	
	private static String importDescriptionFile(String descriptionLearningActivity, Element actElementFile, long userId, PortletDataContext context, ServiceContext serviceContext) throws SystemException {
		FileEntry oldFile = (FileEntry)context.getZipEntryAsObject(actElementFile.attributeValue("path"));
		
		log.info("*  Description File: " + oldFile.getTitle()); 
								
		FileEntry newFile;
		long folderId=0;
		String description = "";
		
		try {
			
			InputStream input = context.getZipEntryAsInputStream(actElementFile.attributeValue("file"));
			
			long repositoryId = DLFolderConstants.getDataRepositoryId(context.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			folderId=DLFolderUtil.createDLFoldersForLearningActivity(userId,repositoryId,serviceContext).getFolderId();	
			
			log.info("*   getMimeType getMimeType: " + oldFile.getMimeType()); 
			log.info("*   getExtension getExtension: " + oldFile.getExtension()); 
			
			String titleFile=oldFile.getTitle();
			if(!oldFile.getTitle().endsWith(oldFile.getExtension())){
				titleFile=oldFile.getTitle()+"."+oldFile.getExtension();
			} 
			
			log.info("*   titleFile titleFile: " + titleFile); 
			newFile = DLAppLocalServiceUtil.addFileEntry(userId, repositoryId , folderId , titleFile, oldFile.getMimeType(), oldFile.getTitle(), StringPool.BLANK, StringPool.BLANK, IOUtils.toByteArray(input), serviceContext );
								
			description = ImportUtil.descriptionFileParserLarToDescription(descriptionLearningActivity, oldFile, newFile);
			
		} catch(DuplicateFileException dfl){
			
			try{
							
				
				FileEntry existingFile = DLAppLocalServiceUtil.getFileEntry(context.getScopeGroupId(), folderId, oldFile.getTitle());
				description = ImportUtil.descriptionFileParserLarToDescription(descriptionLearningActivity, oldFile, existingFile);
			}catch(Exception e){
				log.info("ERROR! descriptionfile descriptionFileParserLarToDescription : " +e.getMessage());
				description = descriptionLearningActivity;
			}
		} catch (PortletDataException e1){
			log.info("ERROR! descriptionfile: ");
			
		} catch (Exception e) {

			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.info("ERROR! descriptionfile: " + actElementFile.attributeValue("file") +"\n        "+e.getMessage());
		}
		
		return description;
	}

	/**
	 * Función para la importación de fileentry de las actividades
	 * @param newLarn
	 * @param userId
	 * @param context
	 * @param serviceContext
	 * @param actElement
	 * @throws SystemException 
	 * @throws PortalException 
	 */
	private static void importDLFileEntries(LearningActivity newLarn, long userId, PortletDataContext context, ServiceContext serviceContext, Element actElement) throws PortalException, SystemException, FileExtensionException, FileSizeException {
		//Importar las imagenes de los resources.
		Iterator<Element> it = actElement.elementIterator("dlfileentry");
		
		int countDocument = -1;
		
		while(it.hasNext()){
			Element theElement = it.next();
			log.info("element: " + theElement.toString());
			
			log.info("   dlfileentry path: "+theElement.attributeValue("path"));

			FileEntry newFile = ImportUtil.importDLFileEntry(context, theElement, serviceContext, userId);
			
			AssetEntry asset = AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), newFile.getPrimaryKey());
			
			//Ponemos a la actividad el fichero que hemos recuperado.
			log.info("    Extracontent : \n"+newLarn.getExtracontent());
			if(newLarn.getTypeId() == 2){
				log.info("TIPO EXTERNO");
				if(countDocument < 0){
					LearningActivityLocalServiceUtil.setExtraContentValue(newLarn.getActId(), "document", String.valueOf(asset.getEntryId()));
				}else{
					LearningActivityLocalServiceUtil.setExtraContentValue(newLarn.getActId(), "document" + countDocument, String.valueOf(asset.getEntryId()));
				}
				countDocument++;
			}else if(newLarn.getTypeId() == 7){
				LearningActivityLocalServiceUtil.setExtraContentValue(newLarn.getActId(), "assetEntry", String.valueOf(asset.getEntryId()));
			}
			
			Long newActId = newLarn.getActId();
			newLarn = LearningActivityLocalServiceUtil.getLearningActivity(newActId);

		}	
	}


}
