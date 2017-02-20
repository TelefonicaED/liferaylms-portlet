package com.liferay.lms.lar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.learningactivity.questiontype.QuestionType;
import com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.SCORMContent;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.lms.service.persistence.SCORMContentPersistence;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.tls.lms.util.DLFolderUtil;

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
	
			LearningActivity newLarn=LearningActivityLocalServiceUtil.addLearningActivity(larn,serviceContext);
			serviceContext.setScopeGroupId(newLarn.getGroupId());
			
			//Para actividad de recurso externo
			if(larn.getTypeId() == 2){
				//changeExtraContentDocumentIds(newLarn, newModule, userId, context, serviceContext);
			}
			
			//Comprobamos si es un Recurso SCORM (Type=9) para guardarlo
			if(larn.getTypeId() == 9){
				importSCORM(newLarn, userId, context, serviceContext, actElement);
			}
			
			importDLFileEntries(newLarn, userId, context, serviceContext, actElement);
			
			importDescriptionFileActivities(newLarn, userId, context, serviceContext, actElement);

			importQuestions(newLarn, userId, context, serviceContext, actElement);

			return newLarn.getActId();
		}else{
		
			return 0;
		
		}
	}


	private static void importQuestions(LearningActivity newLarn, long userId, PortletDataContext context, ServiceContext serviceContext, Element actElement) throws SystemException, PortalException {
		for(Element qElement:actElement.elements("question")){
			String pathq = qElement.attributeValue("path");
				
			TestQuestion question=(TestQuestion)context.getZipEntryAsObject(pathq);
			question.setActId(newLarn.getActId());
			TestQuestion nuevaQuestion=TestQuestionLocalServiceUtil.addQuestion(question.getActId(), question.getText(), question.getQuestionType());
			
			log.info("      Test Question: " + nuevaQuestion.getQuestionId() /*Jsoup.parse(nuevaQuestion.getText()).text()*/);
			
			//Si tenemos ficheros en las descripciones de las preguntas.
			for (Element actElementFile : qElement.elements("descriptionfile")) {
				
				FileEntry oldFile = (FileEntry)context.getZipEntryAsObject(actElementFile.attributeValue("path"));
										
				FileEntry newFile;
				long folderId=0;
				String description = "";
				
				try {
					
					newFile = ImportUtil.importDLFileEntry(context, actElementFile, serviceContext, userId);
					
					description = ImportUtil.descriptionFileParserLarToDescription(nuevaQuestion.getText(), oldFile, newFile);
					
				} catch(DuplicateFileException dfl){
					
					FileEntry existingFile = DLAppLocalServiceUtil.getFileEntry(context.getScopeGroupId(), folderId, oldFile.getTitle());
					description = ImportUtil.descriptionFileParserLarToDescription(nuevaQuestion.getText(), oldFile, existingFile);
					
				} catch (Exception e) {

					// TODO Auto-generated catch block
					//e.printStackTrace();
					log.info("* ERROR! Question descriptionfile: " + e.getMessage());
				}
				//log.info("   description : " + description );
				nuevaQuestion.setText(description);
				TestQuestionLocalServiceUtil.updateTestQuestion(nuevaQuestion);
				
			}
			
			QuestionType qt =new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
			qt.importQuestionAnswers(context, qElement, nuevaQuestion.getQuestionId(), userId, serviceContext);
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
			
			FileEntry oldFile = (FileEntry)context.getZipEntryAsObject(actElementFile.attributeValue("path"));
			
			log.info("*  Description File: " + oldFile.getTitle()); 
									
			FileEntry newFile;
			long folderId=0;
			String description = "";
			
			try {
				
				InputStream input = context.getZipEntryAsInputStream(actElementFile.attributeValue("file"));
				
				long repositoryId = DLFolderConstants.getDataRepositoryId(context.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
				folderId=DLFolderUtil.createDLFoldersForLearningActivity(userId,repositoryId,serviceContext).getFolderId();
				
				String ficheroStr = actElementFile.attributeValue("file");	
				String ficheroExtStr = "";
				String extension[] = ficheroStr.split("\\.");
				if(extension.length > 0){
					ficheroExtStr = "."+extension[extension.length-1];
				}
				
				log.info("*   getMimeType getMimeType: " + oldFile.getMimeType()); 
				log.info("*   getExtension getExtension: " + oldFile.getExtension()); 
				
			
				
				String titleFile=oldFile.getTitle();
				if(!oldFile.getTitle().endsWith(oldFile.getExtension())){
					titleFile=oldFile.getTitle()+"."+oldFile.getExtension();
				} 
				
				log.info("*   titleFile titleFile: " + titleFile); 
				newFile = DLAppLocalServiceUtil.addFileEntry(userId, repositoryId , folderId , titleFile, oldFile.getMimeType(), oldFile.getTitle(), StringPool.BLANK, StringPool.BLANK, IOUtils.toByteArray(input), serviceContext );
									
				description = ImportUtil.descriptionFileParserLarToDescription(newLarn.getDescription(), oldFile, newFile);
				
			} catch(DuplicateFileException dfl){
				
				try{
								
					
					FileEntry existingFile = DLAppLocalServiceUtil.getFileEntry(context.getScopeGroupId(), folderId, oldFile.getTitle());
					description = ImportUtil.descriptionFileParserLarToDescription(newLarn.getDescription(), oldFile, existingFile);
				}catch(Exception e){
					log.info("ERROR! descriptionfile descriptionFileParserLarToDescription : " +e.getMessage());
					description = newLarn.getDescription();
				}
			} catch (PortletDataException e1){
				log.info("ERROR! descriptionfile: ");
				
			} catch (Exception e) {

				// TODO Auto-generated catch block
				//e.printStackTrace();
				log.info("ERROR! descriptionfile: " + actElementFile.attributeValue("file") +"\n        "+e.getMessage());
			}

			newLarn.setDescription(description);
			LearningActivityLocalServiceUtil.updateLearningActivity(newLarn);
			
		}
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
	private static void importDLFileEntries(LearningActivity newLarn, long userId, PortletDataContext context, ServiceContext serviceContext, Element actElement) throws PortalException, SystemException {
		//Importar las imagenes de los resources.
		Iterator<Element> it = actElement.elementIterator("dlfileentry");
		
		int countDocument = -1;
		
		while(it.hasNext()){
			Element theElement = it.next();
			log.info("element: " + theElement.toString());
			
			String messageException = "";
			try {
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
				
			}catch(FileExtensionException fee){
				fee.printStackTrace();
				log.info("*ERROR! dlfileentry path FileExtensionException:" + theElement.attributeValue("path")+", "+messageException +", message: "+fee.getMessage());
			}catch(FileSizeException fse){
				log.info("*ERROR! dlfileentry path FileSizeException:" + theElement.attributeValue("path")+messageException +", message: "+ fse.getMessage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.info("*ERROR! dlfileentry path: " + theElement.attributeValue("path")+messageException +", message: "+e.getMessage());
			}

	}	
	}

	/**
	 * Función para importar los datos de las actividades tipo SCORM
	 * @param newLarn
	 * @param userId
	 * @param context
	 * @param serviceContext
	 * @param actElement
	 * @throws PortalException
	 * @throws SystemException
	 */
	private static void importSCORM(LearningActivity newLarn, long userId, PortletDataContext context, ServiceContext serviceContext, Element actElement) throws PortalException, SystemException {
		context.importPermissions("com.liferay.lms.model.SCORMContent",  context.getSourceGroupId(),    context.getScopeGroupId());
		
		ScormDataHandlerImpl scormHandler = new ScormDataHandlerImpl();
		Element scormEntry = actElement.element("scormentry");
		
		if(scormEntry!=null){
			String scormPath = scormEntry.attributeValue("path");
			
			SCORMContent scocontentOld = (SCORMContent) context.getZipEntryAsObject(scormPath);
			
			
			InputStream is = context.getZipEntryAsInputStream(scormEntry.attributeValue("file"));
			try {
				
				
				
				byte[] dataFileScorm = IOUtils.toByteArray(is);
				File scormfile = new File(System.getProperty("java.io.tmpdir")+ "/scorms/" + scocontentOld.getUuid() + ".zip");
				FileUtils.writeByteArrayToFile(scormfile, dataFileScorm);
				
				log.info("AQUI");
				
				//SCORMContentPersistenceImpl scormContentPersistence = new SCORMContentPersistenceImpl();
				SCORMContentPersistence _persistence = (SCORMContentPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
						SCORMContentPersistence.class.getName());
				
				SCORMContent scocontent = _persistence.create(CounterLocalServiceUtil.increment( SCORMContent.class.getName()));
					scocontent.setCompanyId(serviceContext.getCompanyId());
					String uuid = serviceContext.getUuid();
					if (Validator.isNotNull(uuid)) {
						scocontent.setUuid(uuid);
					}
					scocontent.setGroupId(serviceContext.getScopeGroupId());
					scocontent.setUserId(userId);
					scocontent.setDescription(scocontentOld.getDescription());
					scocontent.setTitle(scocontentOld.getTitle());
					scocontent.setCiphered(scocontentOld.getCiphered());
					scocontent.setStatus(WorkflowConstants.STATUS_APPROVED);
					scocontent.setExpandoBridgeAttributes(serviceContext);
					_persistence.update(scocontent, true);
					String dirScorm=getDirScormPath(scocontent);
					File dir=new File(dirScorm);
					String dirScormZip=getDirScormzipPath(scocontent);
					File dirZip=new File(dirScormZip);
					
					FileUtils.forceMkdir(dir);
					FileUtils.forceMkdir(dirZip);
					File scormFileZip=new File(dirZip.getAbsolutePath()+"/"+scocontent.getUuid()+".zip");
					FileUtils.copyFile(scormfile, scormFileZip);
					try {
						ZipFile zipFile= new ZipFile(scormfile);
						zipFile.extractAll(dir.getCanonicalPath());
						File manifestfile=new File(dir.getCanonicalPath()+"/imsmanifest.xml");
						try {
							Document imsdocument=SAXReaderUtil.read(manifestfile);
							Element item=imsdocument.getRootElement().element("organizations").elements("organization").get(0).elements("item").get(0);
							String resourceid=item.attributeValue("identifierref");
							java.util.List<Element> resources=imsdocument.getRootElement().element("resources").elements("resource");
							for(Element resource:resources)
							{
								if(resource.attributeValue("identifier").equals(resourceid))
								{
									scocontent.setIndex(resource.attributeValue("href"));
									_persistence.update(scocontent, true);
								}
							}
						} catch (DocumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (ZipException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
						if (serviceContext.isAddGroupPermissions() ||
								serviceContext.isAddGuestPermissions()) {
							ResourceLocalServiceUtil.addResources(
									serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), userId,
									SCORMContent.class.getName(), scocontent.getPrimaryKey(), false,
								serviceContext.isAddGroupPermissions(),
								serviceContext.isAddGuestPermissions());			
							}
							else {
								
								ResourceLocalServiceUtil.addModelResources(
										serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), userId,
										SCORMContent.class.getName(), scocontent.getPrimaryKey(),
								serviceContext.getGroupPermissions(),
								serviceContext.getGuestPermissions());
								
								
							}
					
					AssetEntry assentry = AssetEntryLocalServiceUtil.updateEntry(
							userId, scocontent.getGroupId(), SCORMContent.class.getName(),
							scocontent.getScormId(), scocontent.getUuid(),0, serviceContext.getAssetCategoryIds(),
							serviceContext.getAssetTagNames(), true, null, null,
							new java.util.Date(System.currentTimeMillis()), null,
							ContentTypes.TEXT_HTML, scocontent.getTitle(),null,  HtmlUtil.extractText(scocontent.getDescription()), 
							getUrlManifest(scocontent), 
							null, 0, 0, null, false);
					
					
					log.info("Assentry "+assentry);
							
					Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(SCORMContent.class);

					indexer.reindex(scocontent);
					LearningActivityLocalServiceUtil.setExtraContentValue(newLarn.getActId(), "assetEntry", assentry.getEntryId()+"");
					
			} catch (IOException e) {
				e.printStackTrace();
			} catch(Exception e){
				e.printStackTrace();
			}finally {
				try {
					if (is != null) {
						is.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private static String getDirScormPath(SCORMContent scocon){
		String baseDir=PropsUtil.get("liferay.lms.scormdir");
		if(baseDir==null ||baseDir.equals("")){
			baseDir=PropsUtil.get("liferay.home")+"/data/scorms";
		}
		return baseDir+"/"+Long.toString(scocon.getCompanyId())+"/"+Long.toString(scocon.getGroupId())+"/"+scocon.getUuid();
	}
	
	private static String getDirScormzipPath(SCORMContent scocon){
		String baseDir=PropsUtil.get("liferay.lms.scormzipsdir");
		if(baseDir==null ||baseDir.equals(""))
		{
			baseDir=PropsUtil.get("liferay.home")+"/data/scormszip";
		}
		return baseDir+"/"+Long.toString(scocon.getCompanyId())+"/"+Long.toString(scocon.getGroupId())+"/"+scocon.getUuid();
	}
	
	private static String getUrlManifest(SCORMContent scocontent) {
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		return PortalUtil.getPortalURL(serviceContext.getRequest())+"/"+
						ClpSerializer.getServletContextName()+
						"/scorm/"+
						Long.toString(scocontent.getCompanyId())+"/"+
						Long.toString(scocontent.getGroupId())+"/"+
						scocontent.getUuid()+"/imsmanifest.xml";
	}
}
