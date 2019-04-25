package com.liferay.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.liferay.counter.model.Counter;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.lms.util.DLFolderUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

public class CourseCopyUtil {
	private static Log log = LogFactoryUtil.getLog(CourseCopyUtil.class);

	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads";
	
	public String descriptionFilesClone(String description, long groupId, long actId, long userId){

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
		
	public void createTestQuestionsAndAnswers(LearningActivity oldActivity, LearningActivity newActivity, Module newModule, long userId) throws SystemException{
		List<TestQuestion> questions = TestQuestionLocalServiceUtil.getQuestions(oldActivity.getActId());
		TestQuestion newTestQuestion= null;
		List<TestAnswer> answers = new ArrayList<TestAnswer>();
		TestAnswer newTestAnswer = null;
		HashMap<Long, Long> questionIdsMap = new HashMap<Long, Long>();
		
		for(TestQuestion question:questions){
			try {
				newTestQuestion = TestQuestionLocalServiceUtil.addQuestion(newActivity.getActId(), question.getText(), question.getQuestionType());
				String newTestDescription = descriptionFilesClone(question.getText(),newModule.getGroupId(), newTestQuestion.getActId(),userId);
				newTestQuestion.setText(newTestDescription);
				if(question.getWeight() != question.getQuestionId()){
					newTestQuestion.setWeight(question.getWeight());
					questionIdsMap.put(question.getQuestionId(), newTestQuestion.getQuestionId());
				}
				newTestQuestion = TestQuestionLocalServiceUtil.updateTestQuestion(newTestQuestion, true);
				
				if(log.isDebugEnabled()){
					log.debug("      Test question : " + question.getQuestionId() );
					log.debug("      + Test question : " + newTestQuestion.getQuestionId() );
					log.debug("      + Test question TEXT : " + newTestDescription );
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			answers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			for(TestAnswer answer:answers){
				try {
					newTestAnswer = TestAnswerLocalServiceUtil.addTestAnswer(question.getQuestionId(), answer.getAnswer(), answer.getFeedbackCorrect(), answer.getFeedbacknocorrect(), answer.isIsCorrect());
					newTestAnswer.setActId(newActivity.getActId());
					newTestAnswer.setQuestionId(newTestQuestion.getQuestionId());
					newTestAnswer.setAnswer(descriptionFilesClone(answer.getAnswer(),newModule.getGroupId(), newTestAnswer.getActId(),userId));
					
					TestAnswerLocalServiceUtil.updateTestAnswer(newTestAnswer, true);
					if(log.isDebugEnabled()){
						log.debug("        Test answer : " + answer.getAnswerId());
						log.debug("        + Test answer : " + newTestAnswer.getAnswerId());	
					}
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		questions = TestQuestionLocalServiceUtil.getQuestions(newActivity.getActId());
		
		for(TestQuestion question:questions){
			log.debug("orden anterior de la pregunta: " + question.getWeight());
			if(question.getWeight() > 0 && questionIdsMap.containsKey(question.getWeight())){
				log.debug("nuevo orden: " + questionIdsMap.get(question.getWeight()));
				question.setWeight(questionIdsMap.get(question.getWeight()));
				TestQuestionLocalServiceUtil.updateTestQuestion(question);
			}
		}
	}
		
	public void copyEvaluationExtraContent(List<Long> evaluations, HashMap<Long,Long> correlationActivities) throws PortalException, SystemException{
		
		//Extra Content de las evaluaciones
		LearningActivity evaluationActivity=null;
		Document document = null;
		Element evaluationXML = null;
		Iterator<Element> activitiesElementItr = null;
		Element activity = null;
		long newValue;
		for(Long evaluation : evaluations){
			evaluationActivity = LearningActivityLocalServiceUtil.getLearningActivity(evaluation);
			try{
				if((evaluationActivity.getExtracontent()!=null)&&(evaluationActivity.getExtracontent().length()!=0)) {	
					//Element activitiesElement = SAXReaderUtil.read(evaluationActivity.getExtracontent()).getRootElement().element("activities");
					document =SAXReaderUtil.read(evaluationActivity.getExtracontent());
					if(log.isDebugEnabled())log.debug(" --- OLD EXTRA CONTENT "+document.formattedString());
					evaluationXML = document.getRootElement();
					if(log.isDebugEnabled())log.debug("--- OLD Evaluation Element "+evaluationXML.asXML());
					Element activitiesElement = evaluationXML.element("activities");
					if(log.isDebugEnabled())log.debug("--- OLD Activities Element "+activitiesElement.asXML());
					if(activitiesElement!=null){
						activitiesElementItr = activitiesElement.elementIterator();
						while(activitiesElementItr.hasNext()) {
							activity =activitiesElementItr.next();
							if(log.isDebugEnabled())log.debug("-- Activity "+ activity);
							if(("activity".equals(activity.getName()))&&(activity.attribute("id")!=null)&&(activity.attribute("id").getValue().length()!=0)){
								try{
									if(log.isDebugEnabled())log.debug("Old Value "+Long.parseLong(activity.attribute("id").getValue()));
									newValue = correlationActivities.get(Long.parseLong(activity.attribute("id").getValue()));
									if(log.isDebugEnabled())log.debug("New Value "+ newValue);
									activity.attribute("id").setValue(String.valueOf(newValue));
								}
								catch(NumberFormatException e){
									if(log.isDebugEnabled())e.printStackTrace();
								}
							}
							if(log.isDebugEnabled())log.debug("-- Activity Changed "+ activity.asXML());
						}		
						if(log.isDebugEnabled())log.debug("--- NEW Activities Element "+activitiesElement.asXML());
					}
	
					if(log.isDebugEnabled()){
						log.debug("--- NEW Evaluation Element "+evaluationXML.asXML());
						log.debug(" --- NEW EXTRA CONTENT "+document.formattedString());
					}
					evaluationActivity.setExtracontent(document.formattedString());
					LearningActivityLocalServiceUtil.updateLearningActivity(evaluationActivity);
					
					LearningActivityType learningActivityType=new LearningActivityTypeRegistry().getLearningActivityType(evaluationActivity.getTypeId());
					learningActivityType.importExtraContent(evaluationActivity, 0L, null, null, null);
					   
				}
			}catch(Exception e){
				e.printStackTrace();
			}		
		}
		
	
	}
	
	public void cloneActivityFile(LearningActivity actOld, LearningActivity actNew, long userId, ServiceContext serviceContext){
		
		try {
			if(log.isDebugEnabled()){
				log.debug("cloneActivityFile");
			}
	
			String entryIdStr = "";
			if(actOld.getTypeId() == 2){
				entryIdStr = LearningActivityLocalServiceUtil.getExtraContentValue(actOld.getActId(), "document");
			}else if(actOld.getTypeId() == 7){
				entryIdStr = LearningActivityLocalServiceUtil.getExtraContentValue(actOld.getActId(), "assetEntry");
			}
			if(!entryIdStr.equals("")){
				
				AssetEntry docAsset = AssetEntryLocalServiceUtil.getAssetEntry(Long.valueOf(entryIdStr));
				long entryId = 0;
				if(docAsset.getUrl()!=null && docAsset.getUrl().trim().length()>0){
					entryId = Long.valueOf(entryIdStr);
				}else{
					
					if(actNew.getTypeId() == 2){
						try{
							
							HashMap<String, String> map = LearningActivityLocalServiceUtil.convertXMLExtraContentToHashMap(actNew.getActId());
							Iterator <String> keysString =  map.keySet().iterator();
							String key = null;
							AssetEntry docAssetOLD = null;
							DLFileEntry oldFile = null;
							InputStream inputStream = null;
							byte[] byteArray = null;
							//int index = 0;
							Folder dlFolderMain = null;
							long repositoryId = 0;
							long dlMainFolderId = 0;
							boolean dlMainFolderFound = false;
							Folder newDocumentMainFolder =null;
							String ficheroExtStr= null;
							String extension[] = null;
							FileEntry newFile = null;
							Role siteMemberRole = null;
							while (keysString.hasNext()){
								key = keysString.next();
								if(!key.equals("video") && key.indexOf("document")!=-1){
									//index++;
									long assetEntryIdOld =  Long.parseLong(map.get(key));
									docAssetOLD= AssetEntryLocalServiceUtil.getAssetEntry(assetEntryIdOld);
									oldFile=DLFileEntryLocalServiceUtil.getDLFileEntry(docAssetOLD.getClassPK());
									inputStream = DLFileEntryLocalServiceUtil.getFileAsStream(userId, oldFile.getFileVersion().getFileEntryId(), oldFile.getFileVersion().getVersion());
									try {
										byteArray = IOUtils.toByteArray(inputStream);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									repositoryId = DLFolderConstants.getDataRepositoryId(actNew.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
									dlMainFolderId = 0;
									dlMainFolderFound = false;
									//Get main folder
									 try {
										 //Get main folder
									     dlFolderMain = DLAppLocalServiceUtil.getFolder(repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,DOCUMENTLIBRARY_MAINFOLDER+actNew.getActId());
									     dlMainFolderId = dlFolderMain.getFolderId();
									     dlMainFolderFound = true;
									     //Get portlet folder
									  } catch (Exception ex){
										  if(log.isDebugEnabled()){
											  ex.printStackTrace();
										  }
									  }
									        
									//Damos permisos al archivo para usuarios de comunidad.
									serviceContext.setAddGroupPermissions(true);
									        
									 //Create main folder if not exist
									 if(!dlMainFolderFound){
										 newDocumentMainFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, DOCUMENTLIBRARY_MAINFOLDER+actNew.getActId(), DOCUMENTLIBRARY_MAINFOLDER+actNew.getActId(), serviceContext);
									     dlMainFolderFound = true;
									     dlMainFolderId = newDocumentMainFolder.getFolderId();
									 }
										
									 ficheroExtStr = "";
									extension = oldFile.getTitle().split("\\.");
									if(extension.length > 0){
											ficheroExtStr = "."+extension[extension.length-1];
										}
									
										newFile = DLAppLocalServiceUtil.addFileEntry(
												userId, repositoryId , dlMainFolderId , oldFile.getTitle()+ficheroExtStr, oldFile.getMimeType(), 
											oldFile.getTitle(), StringPool.BLANK, StringPool.BLANK, byteArray , serviceContext ) ;

																		
										map.put(key, String.valueOf(AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), newFile.getPrimaryKey()).getEntryId()));
										
									
										
										siteMemberRole = RoleLocalServiceUtil.getRole(serviceContext.getCompanyId(), RoleConstants.SITE_MEMBER);
										ResourcePermissionLocalServiceUtil.setResourcePermissions(serviceContext.getCompanyId(), LearningActivity.class.getName(), 
												ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actNew.getActId()),siteMemberRole.getRoleId(), new String[] {ActionKeys.VIEW});

								}

								LearningActivityLocalServiceUtil.saveHashMapToXMLExtraContent(actNew.getActId(), map);
							
						}
						
						
					} catch (NoSuchEntryException nsee) {
						log.error(" asset not exits ");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						serviceContext.setAddGroupPermissions(serviceContext.isAddGroupPermissions());
					}
				}else{
					entryId = cloneFile(Long.valueOf(entryIdStr), actNew, userId, serviceContext);
				}
					
					

				}
				
				if(actNew.getTypeId() == 7){
					LearningActivityLocalServiceUtil.setExtraContentValue(actNew.getActId(), "assetEntry", String.valueOf(entryId));
				}else if(actNew.getTypeId() == 9){
					AssetEntry entry =  AssetEntryLocalServiceUtil.getAssetEntry(entryId);
					AssetEntry newEntry = AssetEntryLocalServiceUtil.createAssetEntry(CounterLocalServiceUtil.increment(Counter.class.getName()));
					long newEntryId = newEntry.getEntryId();
					newEntry = (AssetEntry)entry.clone();
					newEntry.setGroupId(actNew.getGroupId());
					AssetEntryLocalServiceUtil.updateAssetEntry(newEntry);
					LearningActivityLocalServiceUtil.setExtraContentValue(actNew.getActId(), "assetEntry", String.valueOf(newEntryId));
					
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private long cloneFile(long entryId, LearningActivity actNew, long userId, ServiceContext serviceContext){
		
		long assetEntryId = 0;
		boolean addGroupPermissions = serviceContext.isAddGroupPermissions();
		
		try {
			if(log.isDebugEnabled()){log.debug("EntryId: "+entryId);}
			AssetEntry docAsset = AssetEntryLocalServiceUtil.getAssetEntry(entryId);
			//docAsset.getUrl()!=""
			//DLFileEntryLocalServiceUtil.getDLFileEntry(fileEntryId)
			if(log.isDebugEnabled()){log.debug(docAsset.getClassPK());}
			DLFileEntry docfile = DLFileEntryLocalServiceUtil.getDLFileEntry(docAsset.getClassPK());
			InputStream is = DLFileEntryLocalServiceUtil.getFileAsStream(userId, docfile.getFileEntryId(), docfile.getVersion());
			
			//Crear el folder
			DLFolder dlFolder = DLFolderUtil.createDLFoldersForLearningActivity(userId, serviceContext.getScopeGroupId(), serviceContext);

			long repositoryId = DLFolderConstants.getDataRepositoryId(actNew.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			
			String ficheroStr = docfile.getTitle();	
			if(!docfile.getTitle().endsWith(docfile.getExtension())){
				ficheroStr = ficheroStr +"."+ docfile.getExtension();
			}
			
			serviceContext.setAddGroupPermissions(true);
			FileEntry newFile = DLAppLocalServiceUtil.addFileEntry(
					serviceContext.getUserId(), repositoryId , dlFolder.getFolderId() , ficheroStr, docfile.getMimeType(), 
					docfile.getTitle(), StringPool.BLANK, StringPool.BLANK, is, docfile.getSize() , serviceContext ) ;
			
			
			AssetEntry asset = AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), newFile.getPrimaryKey());
			
			if(log.isDebugEnabled()){log.debug(" asset : " + asset.getEntryId());};
			
			assetEntryId = asset.getEntryId();
			
		} catch (NoSuchEntryException nsee) {
			log.error(" asset not exits ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			serviceContext.setAddGroupPermissions(addGroupPermissions);
		}
		
		return assetEntryId;
	}
	
	
	/**
	 * Clonar documentos del curso de forma recursiva
	 */
	
	public boolean duplicateFoldersAndFileEntriesInsideFolder(boolean cloneDocumentsOk, long userId, long groupId, long companyId, long parentFolderId, long repositoryId, long newGroupId, long newParentFolderId, long newRepositoryId, ServiceContext serviceContext){
		if(cloneDocumentsOk){
			//Listado de archivos del curso que se va a duplicar
			List<FileEntry> listFiles = null;
			//Listado de carpetas del curso que se va a duplicar
			List<Folder> listFolders = null;
			try {
				listFiles = DLAppServiceUtil.getFileEntries(repositoryId, parentFolderId);
				listFolders = DLAppServiceUtil.getFolders(repositoryId, parentFolderId);
			} catch (PortalException | SystemException e) {
				e.printStackTrace();
			}
			if(log.isDebugEnabled()){
				log.debug("::cloneDocuments:: listFiles :: " + Validator.isNotNull(listFiles));
				log.debug("::cloneDocuments:: listFolders :: " + Validator.isNotNull(listFolders));
				if(Validator.isNotNull(listFiles))
					log.debug("::cloneDocuments:: listFiles.size :: " + listFiles.size());
				if(Validator.isNotNull(listFolders))
					log.debug("::cloneDocuments:: listFolders.size :: " + listFolders.size());
			}
			if(Validator.isNotNull(listFiles) && listFiles.size()>0){
				//Clonar archivos
				if(log.isDebugEnabled())
					log.debug("::cloneDocuments:: clone file entries:::");
				InputStream is = null;
				FileEntry newFileEntry = null;
				for(FileEntry fileEntry:listFiles){
					newFileEntry = null;
					//Comprobar si el archivo ya existe
					try {
						newFileEntry = DLAppLocalServiceUtil.getFileEntry(newGroupId, newParentFolderId, fileEntry.getTitle());
					} catch (PortalException | SystemException e) {
						if(log.isDebugEnabled())
							log.debug("::cloneDocuments:: clone file entries::: newFileEntry does not exist :: " + e.getMessage());
					}
					if(Validator.isNull(newFileEntry)){
						try {
							//Añadir archivo si no existía
							is = DLFileEntryLocalServiceUtil.getFileAsStream(userId, fileEntry.getFileEntryId(), fileEntry.getVersion());
							newFileEntry = DLAppLocalServiceUtil.addFileEntry(userId, newRepositoryId, newParentFolderId, fileEntry.getTitle(), fileEntry.getMimeType(), fileEntry.getTitle(), fileEntry.getDescription(), null, is, fileEntry.getSize(), serviceContext);
						} catch (PortalException | SystemException e) {
							cloneDocumentsOk = Boolean.FALSE;
							e.printStackTrace();
						}
					}
					if(Validator.isNotNull(newFileEntry)){
						//Copiar permisos del curso actual al curso clonado
						copyPermissionsEntries(String.valueOf(newFileEntry.getFileEntryId()), String.valueOf(fileEntry.getFileEntryId()), DLFileEntry.class.getName(), newGroupId, companyId);
					}
				}
			}
			if(Validator.isNotNull(listFolders) && listFolders.size()>0){
				//Clonar carpetas
				if(log.isDebugEnabled())
					log.debug(":::clone folders:::");
				Folder newFolder = null;
				for(Folder folder:listFolders){
					newFolder = null;
					//Comprobar si la carpeta ya existe
					try {
						newFolder = DLAppLocalServiceUtil.getFolder(newRepositoryId, newParentFolderId, folder.getName());
					} catch (PortalException | SystemException e) {
						if(log.isDebugEnabled())
							log.debug("::cloneDocuments:: clone file entries::: newFolder does not exist :: " + e.getMessage());
					}
					if(Validator.isNull(newFolder)){
						try {
							//Añadir carpeta
							newFolder = DLAppLocalServiceUtil.addFolder(userId, newRepositoryId, newParentFolderId, folder.getName(), folder.getDescription(), serviceContext);
						} catch (PortalException | SystemException e) {
							cloneDocumentsOk = Boolean.FALSE;
							e.printStackTrace();
						}
					}
					if(Validator.isNotNull(newFolder)){
						//Copiar permisos del curso actual al curso clonado
						copyPermissionsEntries(String.valueOf(newFolder.getFolderId()), String.valueOf(folder.getFolderId()), DLFolder.class.getName(), newGroupId, companyId);
						//Se vuelve a llamar a la misma función para que duplique los archivos y carpetas que haya dentro de la carpeta que se acaba de clonar
						cloneDocumentsOk = duplicateFoldersAndFileEntriesInsideFolder(cloneDocumentsOk, userId, groupId, companyId, folder.getFolderId(), repositoryId, newGroupId, newFolder.getFolderId(), newRepositoryId, serviceContext);
					}
				}
			}
		} else {
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:: ERROR IN PREVIOUS ITERATION :: ");
		}
		return cloneDocumentsOk;
	}
	
	/**
	 * Copiar permisos desde entryId a newEntryId (ya sea una carpeta o un fileEntry)
	 * @param newEntryId
	 * @param entryId
	 * @param className
	 * @param newGroupId
	 * @param companyId
	 */
	private static void copyPermissionsEntries(String newEntryId, String entryId, String className, long newGroupId,  long companyId){
		if(log.isDebugEnabled()){
			log.debug("::cloneDocuments:: copyPermissions:: newEntryId :: " + newEntryId);
			log.debug("::cloneDocuments:: copyPermissions:: entryId :: " + entryId);
			log.debug("::cloneDocuments:: copyPermissions:: className :: " + className);
			log.debug("::cloneDocuments:: copyPermissions:: newGroupId :: " + newGroupId);
			log.debug("::cloneDocuments:: copyPermissions:: companyId :: " + companyId);
		}
		try {
			List<ResourcePermission> listResourcePermission = ResourcePermissionLocalServiceUtil.getResourcePermissions(companyId, className, ResourceConstants.SCOPE_INDIVIDUAL, entryId);
			if(log.isDebugEnabled())
				log.debug("::copyPermissions:: listResourcePermission OK :: " + Validator.isNotNull(listResourcePermission));
			if(Validator.isNotNull(listResourcePermission)){
				String[] actionIds = null;
				for(ResourcePermission resourcePermission: listResourcePermission){
					if(log.isDebugEnabled())
						log.debug("rol: " + resourcePermission.getRoleId());
					//Se distingue entre folder y fileEntry porque no tienen los mismos permisos
					if(className.equals(DLFolder.class.getName())){
						actionIds = checkFolderPermission(entryId, resourcePermission, companyId);
					}else if(className.equals(DLFileEntry.class.getName())){
						actionIds = checkFileEntryPermission(entryId, resourcePermission, companyId);
					}
					setPermission(companyId, newGroupId, className, resourcePermission.getRoleId(), actionIds, newEntryId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static String[] checkFolderPermission(String entryId, ResourcePermission resourcePermission, long companyId) throws PortalException, SystemException{
		String[] actionIds = new String[8];
		int i = 0;
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFolder.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.ACCESS)){
			actionIds[i++] = ActionKeys.ACCESS;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.ACCESS);
		}
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFolder.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.ADD_DOCUMENT)){
			actionIds[i++] = ActionKeys.ADD_DOCUMENT;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.ADD_DOCUMENT);
		}
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFolder.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.ADD_SHORTCUT)){
			actionIds[i++] = ActionKeys.ADD_SHORTCUT;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.ADD_SHORTCUT);
		}
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFolder.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.ADD_SUBFOLDER)){
			actionIds[i++] = ActionKeys.ADD_SUBFOLDER;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.ADD_SUBFOLDER);
		}
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFolder.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.DELETE)){
			actionIds[i++] = ActionKeys.DELETE;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.DELETE);
		}
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFolder.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.PERMISSIONS)){
			actionIds[i++] = ActionKeys.PERMISSIONS;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.PERMISSIONS);
		}
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFolder.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.UPDATE)){
			actionIds[i++] = ActionKeys.UPDATE;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.UPDATE);
		}
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFolder.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.VIEW)){
			actionIds[i++] = ActionKeys.VIEW;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.VIEW);
		}
		return actionIds;
	}
	private static String[] checkFileEntryPermission(String entryId, ResourcePermission resourcePermission, long companyId) throws PortalException, SystemException{
		String[] actionIds = new String[7];
		int i = 0;
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFileEntry.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.ADD_DISCUSSION)){
			actionIds[i++] = ActionKeys.ADD_DISCUSSION;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.ADD_DISCUSSION);
		}
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFileEntry.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.DELETE)){
			actionIds[i++] = ActionKeys.DELETE;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.DELETE);
		}
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFileEntry.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.DELETE_DISCUSSION)){
			actionIds[i++] = ActionKeys.DELETE_DISCUSSION;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.DELETE_DISCUSSION);
		}
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFileEntry.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.PERMISSIONS)){
			actionIds[i++] = ActionKeys.PERMISSIONS;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.PERMISSIONS);
		}
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFileEntry.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.UPDATE)){
			actionIds[i++] = ActionKeys.UPDATE;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.UPDATE);
		}
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFileEntry.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.UPDATE_DISCUSSION)){
			actionIds[i++] = ActionKeys.UPDATE_DISCUSSION;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.UPDATE_DISCUSSION);
		}
		if(ResourcePermissionLocalServiceUtil.hasResourcePermission(companyId, DLFileEntry.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, entryId, resourcePermission.getRoleId(), ActionKeys.VIEW)){
			actionIds[i++] = ActionKeys.VIEW;
			if(log.isDebugEnabled())
				log.debug("::cloneDocuments:copyPermissions:: actionId[" + i + "] " + ActionKeys.VIEW);
		}
		return actionIds;
	}
	private static void setPermission(long companyId, long groupId, String classname, long roleId, String[] actionIds, String primaryKey) throws Exception{
		Resource resource =  ResourceLocalServiceUtil.getResource(companyId, classname, ResourceConstants.SCOPE_INDIVIDUAL, primaryKey);
		if(ResourceBlockLocalServiceUtil.isSupported(classname)){
			Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();
			roleIdsToActionIds.put(roleId, actionIds);
			ResourceBlockLocalServiceUtil.setIndividualScopePermissions(
					companyId, groupId, resource.getName(),
					Long.parseLong(primaryKey), roleIdsToActionIds);
		}else{
			Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();
			roleIdsToActionIds.put(roleId, actionIds);
			ResourcePermissionLocalServiceUtil.setResourcePermissions(
					companyId, resource.getName(), ResourceConstants.SCOPE_INDIVIDUAL,
					primaryKey, roleIdsToActionIds);
		}
	}
}
