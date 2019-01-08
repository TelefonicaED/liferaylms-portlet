package com.liferay.lms.learningactivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.lms.asset.ResourceExternalAssetRenderer;
import com.liferay.lms.learningactivity.questiontype.QuestionType;
import com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

public class ResourceExternalLearningActivityType extends BaseLearningActivityType 
{
	private static final long serialVersionUID = 346346367722124L;

	private static Log log = LogFactoryUtil.getLog(ResourceExternalLearningActivityType.class);
	
	public final static int DEFAULT_FILENUMBER = 5;
	
	public final static long TYPE_ID = 2;
	
	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads";
	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"resourceExternalActivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());
	
	public final static int CORRECT_VIDEO = 1;
	public final static int CORRECT_QUESTIONS = 2;
	
	public final static long DEFAULT_SCORE = 0;
	
	@Override
	public boolean gradebook() {
		return false;
	}


	@Override
	public long getDefaultScore() {
		return DEFAULT_SCORE;
	}
	
	//Se modifica para añadirlo en la parte de específica y poder controlar los literales y la posición
	@Override
	public boolean isScoreConfigurable() {
		return false;
	}

	@Override
	public String getName() {
		
		return "learningactivity.external";
	}
	
	@Override
	public String getClassName(){
		return getClass().getName();
	}


	@Override
	public AssetRenderer getAssetRenderer(LearningActivity learningactivity) throws SystemException, PortalException {
		
		return new ResourceExternalAssetRenderer(learningactivity,this);
	}


	@Override
	public long getTypeId() {
		return TYPE_ID;
	}
	
	@Override
	public String getExpecificContentPage() {
		return "/html/resourceExternalActivity/admin/edit.jsp";
	}
	
	@Override
	public String setExtraContent(UploadRequest uploadRequest, PortletResponse portletResponse, LearningActivity learningActivity) {
		/**
		 * 	Todo esto te viene a continuación te puede resultar un poco confuso, pero el desarrollo siguiente debe ser compatible 
		 *  con otras configuraciones de esta actividad.
		 */
		ThemeDisplay themeDisplay = (ThemeDisplay) uploadRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletRequest portletRequest = (PortletRequest)uploadRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		
		String youtubecode=ParamUtil.getString(uploadRequest,"youtubecode");
		boolean videoControlEnabled=ParamUtil.getBoolean(uploadRequest,"videoControl");
		String team = ParamUtil.getString(uploadRequest, "team","0");
		long teamId = 0;
		if(!team.equalsIgnoreCase("0")&&!team.isEmpty()){
			teamId = Long.parseLong(team);
		}
		
		Integer maxfile = DEFAULT_FILENUMBER;
		try{
			maxfile = Integer.valueOf(PropsUtil.get("lms.learningactivity.maxfile"));
		}catch(NumberFormatException nfe){
		}
		
		List<Integer> files = new ArrayList<Integer>();
		for(int i=0;i<=maxfile;i++){
			String param = "additionalFile";
			if(i > 0){
				param = param + (i-1);
			}
			
			String fileName = uploadRequest.getFileName(param);
			if(fileName!=null&&!"".equals(fileName)){
				files.add(i);
			}else{
				fileName = ParamUtil.getString(uploadRequest, param, null);
				if(fileName!=null&&!"".equals(fileName)){
					files.add(i);
				}
			}
		}
		
		if((!StringPool.BLANK.equals(youtubecode.trim())) || files.size()>0 || (!StringPool.BLANK.equals(team)) ){
			
			Document document = null;
			Element rootElement = null;
			if((learningActivity.getExtracontent()==null)||(learningActivity.getExtracontent().trim().length()==0)){
				document = SAXReaderUtil.createDocument();
				rootElement = document.addElement("multimediaentry");
			}else{
				try {
					document=SAXReaderUtil.read(learningActivity.getExtracontent());
				} catch (DocumentException e) {
					if(log.isDebugEnabled())e.printStackTrace();
					if(log.isErrorEnabled())log.error(e.getMessage());
				}
				rootElement =document.getRootElement();
			}
			
			Element video=rootElement.element("video");
			if(video!=null)
			{
				video.detach();
				rootElement.remove(video);
			}
			
			if(!StringPool.BLANK.equals(youtubecode.trim())){
				video = SAXReaderUtil.createElement("video");
				video.setText(youtubecode);		
				rootElement.add(video);
			}
			
			Element videoControl=rootElement.element("video-control");
			if(videoControl!=null)
			{
				videoControl.detach();
				rootElement.remove(videoControl);
			}
			
			videoControl = SAXReaderUtil.createElement("video-control");
			videoControl.setText(String.valueOf(videoControlEnabled));		
			rootElement.add(videoControl);				
			
			if(files.size()>0){
				boolean changes = false;
				boolean error = false;
				List<Element> elements = new ArrayList<Element>(); 
				List<Element> createelements = new ArrayList<Element>(); 
				
				long repositoryId = DLFolderConstants.getDataRepositoryId(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
				long folderId = 0;
				
				try {
					folderId = createDLFolders(themeDisplay.getUserId(),repositoryId, portletRequest,learningActivity.getActId());
				} catch (PortalException e) {
					if(log.isDebugEnabled())e.printStackTrace();
					if(log.isErrorEnabled())log.error(e.getMessage());
					return null;
				} catch (SystemException e) {
					if(log.isDebugEnabled())e.printStackTrace();
					if(log.isErrorEnabled())log.error(e.getMessage());
					return null;
				}
				
				
				ServiceContext serviceContext = null;
				try {
					serviceContext = ServiceContextFactory.getInstance( DLFileEntry.class.getName(), portletRequest);
				} catch (PortalException e) {
					if(log.isDebugEnabled())e.printStackTrace();
					if(log.isErrorEnabled())log.error(e.getMessage());
				} catch (SystemException e) {
					if(log.isDebugEnabled())e.printStackTrace();
					if(log.isErrorEnabled())log.error(e.getMessage());
				}
				
				Element additionalDocumentElement = null;
				int j = 0;
				do{
					String documentt = "document";
					if(j>0){
						documentt = documentt+(j-1);
					}
					additionalDocumentElement=rootElement.element(documentt);
					if(additionalDocumentElement!=null){
						elements.add(additionalDocumentElement);
					}
					j++;
				}while(additionalDocumentElement!=null);
									
				j = 0;
				for(Integer i:files){
					String param = "additionalFile";
					String documentt = "document";
					if(i > 0){
						param = param + (i-1);
					}
					if(j > 0){
						documentt = documentt + (j-1);
					}

					if(log.isDebugEnabled())log.debug("AddElement:"+documentt);
					
					String fileName = uploadRequest.getFileName(param);
					if(fileName!=null&&!"".equals(fileName)){
						FileEntry dlDocument = null;
						try {
							if(!changes)
								changes=true;
							serviceContext.setAddGroupPermissions(true);
							serviceContext.setAddGuestPermissions(true);
							dlDocument = DLAppLocalServiceUtil.addFileEntry(
							          themeDisplay.getUserId(), repositoryId , folderId , uploadRequest.getFileName(param), uploadRequest.getContentType(param), 
							          uploadRequest.getFileName(param), StringPool.BLANK, StringPool.BLANK, uploadRequest.getFile(param) , serviceContext );
							portletRequest.getPortletSession().setAttribute("extensionfile"+i, null);
							portletRequest.getPortletSession().setAttribute("sizefile"+i, null);
						} catch(FileExtensionException fee){
							if(log.isDebugEnabled())fee.printStackTrace();
							if(log.isErrorEnabled())log.error(fee.getMessage());
							portletRequest.getPortletSession().setAttribute("extensionfile"+i, uploadRequest.getFileName(param));
							if(!error)error = true;
							continue;
						} catch(FileSizeException fse){
							if(log.isDebugEnabled())fse.printStackTrace();
							if(log.isErrorEnabled())log.error(fse.getMessage());
							portletRequest.getPortletSession().setAttribute("sizefile"+i, uploadRequest.getFileName(param));
							if(!error)error = true;
							continue;
						}catch (PortalException e) {
							if(log.isDebugEnabled())e.printStackTrace();
							if(log.isErrorEnabled())log.error(e.getMessage());
							if(!error)error = true;
							continue;
						} catch (SystemException e) {
							if(log.isDebugEnabled())e.printStackTrace();
							if(log.isErrorEnabled())log.error(e.getMessage());
							if(!error)error = true;
							continue;
						}
						Element element=SAXReaderUtil.createElement(documentt);
						try {
							element.addAttribute("id",String.valueOf(AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), dlDocument.getPrimaryKey()).getEntryId()));
						} catch (PortalException e) {
							if(log.isDebugEnabled())e.printStackTrace();
							if(log.isErrorEnabled())log.error(e.getMessage());
							if(!error)error = true;
							continue;
						} catch (SystemException e) {
							if(log.isDebugEnabled())e.printStackTrace();
							if(log.isErrorEnabled())log.error(e.getMessage());
							if(!error)error = true;
							continue;
						}
						createelements.add(element);
					}else{
						fileName = ParamUtil.getString(uploadRequest, param, null);
						if(fileName!=null&&!"".equals(fileName)){
							Element element=SAXReaderUtil.createElement(documentt);
							element.addAttribute("id",fileName);
							createelements.add(element);
						}
					}
					
					j++;
				}
				
				for(Element element : elements){
					boolean find = false;
					for(Element celement : createelements){
						if(element.attribute("id").getStringValue().equals(celement.attribute("id").getStringValue())){
							find = true;
						}
					}
					if(!find){
						AssetEntry videoAsset;
						try {
							videoAsset = AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(element.attributeValue("id")));
							FileEntry videofile=DLAppLocalServiceUtil.getFileEntry(videoAsset.getClassPK());
							DLAppLocalServiceUtil.deleteFileEntry(videofile.getFileEntryId());
							if(!changes)
								changes=true;
						} catch (NumberFormatException e) {
							if(!error)error = true;
							if(log.isDebugEnabled())e.printStackTrace();
							if(log.isErrorEnabled())log.error(e.getMessage());
						} catch (PortalException e) {
							if(!error)error = true;
							if(log.isDebugEnabled())e.printStackTrace();
							if(log.isErrorEnabled())log.error(e.getMessage());
						} catch (SystemException e) {
							if(!error)error = true;
							if(log.isDebugEnabled())e.printStackTrace();
							if(log.isErrorEnabled())log.error(e.getMessage());
						}
					}
					element.detach();
					rootElement.remove(element);
				}

				for(Element element : createelements){
					if(log.isDebugEnabled())log.debug("AddElement:"+element.getName());
					rootElement.add(element);
				}
				
				if(changes){
					portletRequest.getPortletSession().setAttribute("preferencesOpen", "preferencesOpen");
				}
				
				if(error){
					portletRequest.getPortletSession().setAttribute("error", "error");
				}
			}else{
				//Delete all
				for(int i=0;i<maxfile;i++){
					String documentt = "document";
					if(i > 0){
						documentt = documentt + (i-1);
					}
					Element element = rootElement.element(documentt);
					if(element!=null){
						try {
							AssetEntry videoAsset = AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(element.attributeValue("id")));
							FileEntry videofile=DLAppLocalServiceUtil.getFileEntry(videoAsset.getClassPK());
							DLAppLocalServiceUtil.deleteFileEntry(videofile.getFileEntryId());
						} catch (NumberFormatException e) {
							if(log.isDebugEnabled())e.printStackTrace();
							if(log.isErrorEnabled())log.error(e.getMessage());
						} catch (PortalException e) {
							if(log.isDebugEnabled())e.printStackTrace();
							if(log.isErrorEnabled())log.error(e.getMessage());
						} catch (SystemException e) {
							if(log.isDebugEnabled())e.printStackTrace();
							if(log.isErrorEnabled())log.error(e.getMessage());
						}
						
						element.detach();
						rootElement.remove(element);
					}
				}
			}
			
			if(!StringPool.BLANK.equals(team)){
				Element teamElement=rootElement.element("team");
				if(teamElement!=null){
					teamElement.detach();
					rootElement.remove(teamElement);
				}
				
				if(teamId!=0){
					teamElement = SAXReaderUtil.createElement("team");
					teamElement.setText(Long.toString(teamId));
					rootElement.add(teamElement);
				}
			}	
			
			//Guardamos los segundos de las preguntas
			List<TestQuestion> listQuestions = null;
			try {
				listQuestions = TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(listQuestions != null && listQuestions.size() > 0){
				int second = 0;
				Element questionElement = null;
				for(TestQuestion question: listQuestions){
					second = ParamUtil.getInteger(uploadRequest, "second_" + question.getQuestionId(), 0);

					questionElement = rootElement.element("question_" + question.getQuestionId());
					if(questionElement != null){
						questionElement.detach();
						rootElement.remove(questionElement);
					}
					if(second > 0){
						questionElement = SAXReaderUtil.createElement("question_" + question.getQuestionId());
						questionElement.setText(String.valueOf(second));
						rootElement.add(questionElement);
					}
				}
			}
			//CORRECT MODE
			int correctMode = ParamUtil.getInteger(uploadRequest, "correctMode", CORRECT_VIDEO);
			Element correctModeElement=rootElement.element("correctMode");
			if(correctModeElement!=null){
				correctModeElement.detach();
				rootElement.remove(correctModeElement);
			}
			
			correctModeElement = SAXReaderUtil.createElement("correctMode");
			correctModeElement.setText(String.valueOf(correctMode));
			rootElement.add(correctModeElement);
			
			//FINAL FEEDBACK
			boolean finalFeedback = ParamUtil.getBoolean(uploadRequest, "finalFeedback", false);
			Element finalFeedbackElement=rootElement.element("finalFeedback");
			if(finalFeedbackElement!=null){
				finalFeedbackElement.detach();
				rootElement.remove(finalFeedbackElement);
			}
			finalFeedbackElement = SAXReaderUtil.createElement("finalFeedback");
			finalFeedbackElement.setText(String.valueOf(finalFeedback));
			rootElement.add(finalFeedbackElement);
						
			//QUESTION FEEDBACK
			boolean questionFeedback = ParamUtil.getBoolean(uploadRequest, "questionFeedback", false);
			Element questionFeedbackElement=rootElement.element("questionFeedback");
			if(questionFeedbackElement!=null){
				questionFeedbackElement.detach();
				rootElement.remove(questionFeedbackElement);
			}
			questionFeedbackElement = SAXReaderUtil.createElement("questionFeedback");
			questionFeedbackElement.setText(String.valueOf(questionFeedback));
			rootElement.add(questionFeedbackElement);
			
			try {
				learningActivity.setExtracontent(document.formattedString());
			} catch (IOException e) {
				if(log.isDebugEnabled())e.printStackTrace();
				if(log.isErrorEnabled())log.error(e.getMessage());
			}
		}
		
		return null;
	}
	
	private long createDLFolders(Long userId,Long repositoryId,PortletRequest portletRequest,long actId) throws PortalException, SystemException{
		//Variables for folder ids
		Long dlMainFolderId = 0L;
		//Search for folder in Document Library
        boolean dlMainFolderFound = false;
        //Get main folder
        try {
        	//Get main folder
        	Folder dlFolderMain = DLAppLocalServiceUtil.getFolder(repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,DOCUMENTLIBRARY_MAINFOLDER+actId);
        	dlMainFolderId = dlFolderMain.getFolderId();
        	dlMainFolderFound = true;
        	//Get portlet folder
        } catch (Exception ex){
        }
        
		ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFolder.class.getName(), portletRequest);
		//Damos permisos al archivo para usuarios de comunidad.
		serviceContext.setAddGroupPermissions(true);
        
        //Create main folder if not exist
        if(!dlMainFolderFound){
        	Folder newDocumentMainFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, DOCUMENTLIBRARY_MAINFOLDER+actId, DOCUMENTLIBRARY_MAINFOLDER+actId, serviceContext);
        	dlMainFolderFound = true;
        	dlMainFolderId = newDocumentMainFolder.getFolderId();
        }
        //Create portlet folder if not exist
        return dlMainFolderId;
	}
	
	@Override
	public String getDescription() {
		return "learningactivity.external.helpmessage";
	}
	
	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean hasDeleteTries() {
		return true;
	}
	
	@Override
	public boolean canBeLinked(){
		return false;
	}
	
	
	/**
	 * Si tiene preguntas se calcula el 50% por ver el video y el 50% por responder correctamente a las preguntas
	 * Si no necesita porcentaje del video para aprobarlo, se da como aprobado con cualquier porcentaje del video
	 * @param activity actividad
	 * @param latId
	 * @param score int en este caso es el porcentaje del video visualizado
	 * @return score final
	 */
	@Override
	public long calculateResult(LearningActivity activity, LearningActivityTry lat){
		long score = 0;
		
		try {
			
			Document documentActivity = SAXReaderUtil.read(activity.getExtracontent());
			Element rootActivity=documentActivity.getRootElement();
				
			Document documentTry = SAXReaderUtil.read(lat.getTryResultData());
			Element rootTry = documentTry.getRootElement();
			
			score = rootTry.element("score") != null ? Long.parseLong(rootTry.element("score").getText()) :  lat.getResult();
			log.debug("score del try: " + score);
			
			//Comprobamos el tipo de corrección
			Element correctModeElement = rootActivity.element("correctMode");
			int correctMode = CORRECT_VIDEO;
			if(correctModeElement != null){
				correctMode = Integer.parseInt(correctModeElement.getText());
			}
			
			//Comprobamos si es necesario ver un porcetanje del video para aprobar
			boolean isDefaultScore = (activity.getPasspuntuation() == 0);
			
			log.debug("isDefaultScore: " + isDefaultScore);
			
			if(correctMode == CORRECT_QUESTIONS){
			
				Element video=rootActivity.element("video");
				if(video!=null){
					
					List<TestQuestion> listQuestions = TestQuestionLocalServiceUtil.getQuestions(activity.getActId());
					
					if(listQuestions != null && listQuestions.size() > 0){
						log.debug("tiene preguntas: " + listQuestions.size());
						Element element = null;
						int correctAnswers = 0;
						int penalizedAnswers = 0;
						int numQuestions = 0;
						
						Iterator<Element> questionIterator = null;
						boolean finded = false;
						
						long correct = 0;
						
						Element questionSecond = null;
						
						for(TestQuestion question: listQuestions){
							
							try{
								
								questionSecond = rootActivity.element("question_" + question.getQuestionId());

								if(questionSecond != null){
									log.debug("esta pregunta tiene segundo: " + questionSecond.getText());
									numQuestions++;
									
									element = null;
									questionIterator = rootTry != null ? rootTry.elementIterator("question") : null;
									
									if(questionIterator != null){
										
										finded = false;
										while(!finded && questionIterator.hasNext()){
											element = questionIterator.next();
											if(Long.parseLong(element.attributeValue("id")) == question.getQuestionId()){
												finded = true;
											}else{
												element = null;
											}
										}
										
										if(element != null){
											QuestionType qt = new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
											correct = qt.correct(element, question.getQuestionId());
											log.debug("correct: " + correct);
											if(correct > 0) {
												correctAnswers += correct ;
											}else if(question.isPenalize()){
												penalizedAnswers++;
											}
										}else{
											log.debug("no ha respondido a la pregunta: " + question.getQuestionId());
										}
									
									}else{
										log.debug("no ha respondido a ninguna pregunta " );
									}
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						
						log.debug("numQuestions: " + numQuestions);
						log.debug("correctAnswers: " + correctAnswers);
						log.debug("penalizedAnswers: " + penalizedAnswers);
						
						if(numQuestions > 0){
							score = (correctAnswers - penalizedAnswers)/numQuestions;
							log.debug("scoreQuestions: " + score);
						}
						
					}
				}
			}else if(correctMode == CORRECT_VIDEO && isDefaultScore){
				score = 100;
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		log.debug("score: " + score);
		return score;
	}
	
	@Override
	public String getMesageEditDetails() {
		return "resource-external-activity.editquestions";
	}
	
}
