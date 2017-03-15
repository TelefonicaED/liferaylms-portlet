package com.liferay.lms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import org.apache.commons.beanutils.BeanComparator;
import org.jsoup.Jsoup;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.learningactivity.questiontype.QuestionType;
import com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class ExecActivity
 */
public class ExecActivity extends MVCPortlet{
	
	private static Log log = LogFactoryUtil.getLog(ExecActivity.class);
	
	static final Pattern DOCUMENT_EXCEPTION_MATCHER = Pattern.compile("Error on line (\\d+) of document ([^ ]+) : (.*)");

	HashMap<Long, TestAnswer> answersMap = new HashMap<Long, TestAnswer>(); 

	public void correct	(ActionRequest actionRequest,ActionResponse actionResponse)	throws Exception {

		long actId=ParamUtil.getLong(actionRequest, "actId");
		long latId=ParamUtil.getLong(actionRequest,"latId" );
		boolean isTablet = ParamUtil.getBoolean(actionRequest,"isTablet" );
		String navigate = ParamUtil.getString(actionRequest, "navigate");
		boolean isPartial = false;
		if (Validator.isNotNull(navigate)) {
			if (Validator.equals(navigate, "backward") || Validator.equals(navigate, "forward")) {
				isPartial = true;
			}
		}

		LearningActivityTry larntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);

		//Comprobar que el usuario tenga intentos posibles.
		if (larntry.getEndDate() == null){

			long correctanswers=0,penalizedAnswers=0;
			Element resultadosXML=SAXReaderUtil.createElement("results");
			Document resultadosXMLDoc=SAXReaderUtil.createDocument(resultadosXML);

			long[] questionIds = ParamUtil.getLongValues(actionRequest, "question");


			for (long questionId : questionIds) {
				TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
				QuestionType qt = new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
				if(!isPartial){
					if(qt.correct(actionRequest, questionId)>0) {
						correctanswers += qt.correct(actionRequest, questionId) ;
					}else if(question.isPenalize()){
						penalizedAnswers++;
					}
				}
				resultadosXML.add(qt.getResults(actionRequest, questionId));								
			}

			long random = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(actId,"random"));
			long score=isPartial ? 0 : correctanswers/((random!=0 && random<questionIds.length)?random:questionIds.length);
			if(score < 0)score = 0;
			
			LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, PortalUtil.getUserId(actionRequest));
			long oldResult=-1;
			if(learningActivityResult!=null) oldResult=learningActivityResult.getResult();

			larntry.setTryResultData(resultadosXMLDoc.formattedString());
			if (!isPartial) {
				larntry.setResult(score);
				larntry.setEndDate(new java.util.Date(System.currentTimeMillis()));
			}

			LearningActivityTryLocalServiceUtil.updateLearningActivityTry(larntry);

			actionResponse.setRenderParameters(actionRequest.getParameterMap());

			if (isPartial) {
				actionResponse.setRenderParameter("improve", ParamUtil.getString(actionRequest, "improve", Boolean.FALSE.toString()));
				if(isTablet)actionResponse.setRenderParameter("isTablet", Boolean.toString(true));
				actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/view.jsp");
			} else {
				actionResponse.setRenderParameter("oldResult", Long.toString(oldResult));
				actionResponse.setRenderParameter("correction", Boolean.toString(true));
				if(isTablet)actionResponse.setRenderParameter("isTablet", Boolean.toString(true));
				actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/results.jsp");
			}
		}else{
			actionResponse.setRenderParameters(actionRequest.getParameterMap());
			actionRequest.setAttribute("actId", actId);
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/preview.jsp");
		}						

	}
	
	/**
	 * Corrección para cuando estamos en modo observador ya que no se tiene que guardar nada en learningactivitytry
	 * @param actionRequest
	 * @param actionResponse
	 * @throws SystemException 
	 * @throws Exception
	 */
	
	public void correctAccessFinished	(ActionRequest actionRequest,ActionResponse actionResponse) throws SystemException{

		long actId=ParamUtil.getLong(actionRequest, "actId");

		boolean isTablet = ParamUtil.getBoolean(actionRequest,"isTablet" );

		long correctanswers=0,penalizedAnswers=0;
		Element resultadosXML=SAXReaderUtil.createElement("results");
		Document resultadosXMLDoc=SAXReaderUtil.createDocument(resultadosXML);

		long[] questionIds = ParamUtil.getLongValues(actionRequest, "question");


		for (long questionId : questionIds) {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			QuestionType qt = new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
			if(qt.correct(actionRequest, questionId)>0) {
				correctanswers++;
			}else if(question.isPenalize()){
				penalizedAnswers++;
			}
			resultadosXML.add(qt.getResults(actionRequest, questionId));								
		}


		List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actId);
		long score = (correctanswers-penalizedAnswers)*100/questions.size();
		if(score < 0)score = 0;
		
		
		actionResponse.setRenderParameters(actionRequest.getParameterMap());

		actionResponse.setRenderParameter("correction", Boolean.toString(true));
		if(isTablet)actionResponse.setRenderParameter("isTablet", Boolean.toString(true));
		try {
			//actionResponse.setRenderParameter("tryResultData", resultadosXMLDoc.formattedString());
			actionResponse.setRenderParameter("tryResultData", resultadosXMLDoc.formattedString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		actionResponse.setRenderParameter("score", String.valueOf(score));
		actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/results.jsp");					

	}

	public void camposExtra(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

		long actId = ParamUtil.getLong(actionRequest, "actId", 0);
		long randomString=ParamUtil.getLong(actionRequest, "randomString",0);
		String passwordString=ParamUtil.getString(actionRequest, "passwordString",StringPool.BLANK);
		long hourDurationString=ParamUtil.getLong(actionRequest, "hourDurationString",0);
		long minuteDurationString=ParamUtil.getLong(actionRequest, "minuteDurationString",0);
		long secondDurationString=ParamUtil.getLong(actionRequest, "secondDurationString",0);
		long timeStamp = hourDurationString * 3600 + minuteDurationString * 60 + secondDurationString;

		String showCorrectAnswer=ParamUtil.getString(actionRequest, "showCorrectAnswer", "false");
		String showCorrectAnswerOnlyOnFinalTry=ParamUtil.getString(actionRequest, "showCorrectAnswerOnlyOnFinalTry", "false");
		String improve=ParamUtil.getString(actionRequest, "improve", "false");

		long questionsPerPage = ParamUtil.getInteger(actionRequest, "questionsPerPage", 1);
		
		String isMultiple = ParamUtil.getString(actionRequest, "banks-multipleselections", "false");
		String isBank = ParamUtil.getString(actionRequest, "is-bank", "false"); 
		String assetCategoryIds = ParamUtil.getString(actionRequest, "assetCategoryIds", StringPool.BLANK);
		
		if(randomString==0) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "random", StringPool.BLANK);
		}
		else {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "random", Long.toString(randomString));
		}

		LearningActivityLocalServiceUtil.setExtraContentValue(actId, "password", HtmlUtil.escape(passwordString.trim()));

		if(timeStamp==0) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "timeStamp", StringPool.BLANK);
		}
		else {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "timeStamp", Long.toString(timeStamp));
		}

		if(showCorrectAnswer.equals("true")) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "showCorrectAnswer", "true");
		}else if(showCorrectAnswer.equals("false")){
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "showCorrectAnswer", "false");
		}
		
		if(showCorrectAnswerOnlyOnFinalTry.equals("true")) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "showCorrectAnswerOnlyOnFinalTry", "true");
		}else if(showCorrectAnswerOnlyOnFinalTry.equals("false")){
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "showCorrectAnswerOnlyOnFinalTry", "false");
		}

		if(improve.equals("true")) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "improve", "true");
		}else if(improve.equals("false")) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "improve", "false");
		}

		if(questionsPerPage == 0) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "questionsPerPage", StringPool.BLANK);
		}
		else {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "questionsPerPage", Long.toString(questionsPerPage));
		}
		
		if(isBank.equals("true")){
			LearningActivityLocalServiceUtil.setExtraContentValue(actId,"isBank", "true");
		}else if(isBank.equals("false")) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "isBank", "false");
		}
		
		if(isMultiple.equals("true")){
			LearningActivityLocalServiceUtil.setExtraContentValue(actId,"isMultiple", "true");
		}else if(isMultiple.equals("false")) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "isMultiple", "false");
		}
		
		if(!StringPool.BLANK.equals(assetCategoryIds)){
			LearningActivityLocalServiceUtil.setExtraContentValue(actId,"categoriesId", assetCategoryIds);
		}else{
			LearningActivityLocalServiceUtil.setExtraContentValue(actId,"categoriesId", StringPool.BLANK);
		}
		

		SessionMessages.add(actionRequest, "activity-saved-successfully");
		actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/edit.jsp");

	}

	public void importQuestions(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

		UploadPortletRequest request = PortalUtil.getUploadPortletRequest(actionRequest);

		long actId = ParamUtil.getLong(actionRequest, "resId");
		String fileName = request.getFileName("fileName");
		if(fileName==null || StringPool.BLANK.equals(fileName)){
			SessionErrors.add(actionRequest, "execativity.editquestions.importquestions.xml.fileRequired");
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/importquestions.jsp");
		}
		else{ 
			String contentType = request.getContentType("fileName");	
			if (!ContentTypes.TEXT_XML.equals(contentType) && !ContentTypes.TEXT_XML_UTF8.equals(contentType) ) {
				SessionErrors.add(actionRequest, "execativity.editquestions.importquestions.xml.badFormat");	
				actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/importquestions.jsp");
			}
			else {
				try {
					Document document = SAXReaderUtil.read(request.getFile("fileName"));
					if (TestQuestionLocalServiceUtil.isTypeAllowed(actId, document)){
						TestQuestionLocalServiceUtil.importXML(actId, document);
						SessionMessages.add(actionRequest, "questions-added-successfully");
						actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/editquestions.jsp");
					}else{
						SessionErrors.add(actionRequest, "execativity.editquestions.importquestions.xml.not.allowed");
						actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/importquestions.jsp");
					}
				} catch (DocumentException e) {
					Matcher matcher = DOCUMENT_EXCEPTION_MATCHER.matcher(e.getMessage());

					if(matcher.matches()) {
						SessionErrors.add(actionRequest, "execativity.editquestions.importquestions.xml.parseXMLLine", matcher.group(1));
					}
					else{
						SessionErrors.add(actionRequest, "execativity.editquestions.importquestions.xml.parseXML");					
					}
					actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/importquestions.jsp");
				} catch (Exception e) {
					SessionErrors.add(actionRequest, "execativity.editquestions.importquestions.xml.generic");
					actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/importquestions.jsp");
				}
			}

		}

		actionResponse.setRenderParameter("actionEditingDetails", StringPool.TRUE);
		actionResponse.setRenderParameter("resId", Long.toString(actId));	
	}

	public void editQuestion(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		
		long questionId = ParamUtil.getLong(actionRequest, "questionId", 0);
		long actid = ParamUtil.getLong(actionRequest, "resId");
		long questionType = ParamUtil.getLong(actionRequest, "typeId", -1);
		String questionText = ParamUtil.get(actionRequest, "text", "");
		boolean penalize = ParamUtil.getBoolean(actionRequest, "penalize");
		
		log.debug("***questionId:"+questionId);
		log.debug("***penalize:"+penalize);
		
		String backUrl = ParamUtil.get(actionRequest, "backUrl", "");
		String formatType = ParamUtil.getString(actionRequest, "formattype", PropsUtil.get("lms.question.formattype.normal")); 
		String partialCorrection = ParamUtil.getString(actionRequest, "partialcorrection", "false");
		Document document = null;
		Element rootElement = null;
		
		LearningActivityLocalServiceUtil.setExtraContentValue(actid, "isBank", "false");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		if(Validator.isNotNull(questionText)){//porque no se permite vacio ya que eliminar pregunta va por otro lado
			TestQuestion question = null;
			if(questionId == 0){//Nueva pregunta
				question = TestQuestionLocalServiceUtil.createTestQuestion(CounterLocalServiceUtil.increment(TestQuestion.class.getName()));
				question.setText(questionText);
				question.setPenalize(penalize);
				question.setQuestionType(questionType);
				question.setActId(actid);
				question.setWeight(question.getQuestionId());
				
				document = SAXReaderUtil.createDocument();
				rootElement = document.addElement("question");
				Element elem = SAXReaderUtil.createElement("formattype");
				elem.setText(formatType);		
				rootElement.add(elem);				
				elem = SAXReaderUtil.createElement("partialcorrection");
				elem.setText(partialCorrection);
				question.setExtracontent(document.formattedString());
				TestQuestionLocalServiceUtil.addTestQuestion(question);
				
			}else{//Pregunta existente
				question = TestQuestionLocalServiceUtil.getTestQuestion(questionId);
				String typeOrderBefore = "false";
				String partialCorrectionBefore = "false";
				try{
					Document xml = SAXReaderUtil.read(question.getExtracontent());
					Element ele = xml.getRootElement();
					typeOrderBefore = (String) ele.element("formattype").getData();
					partialCorrectionBefore = (String) ele.element("partialcorrection").getData();
				}catch (DocumentException e){
					document = SAXReaderUtil.createDocument();
					rootElement = document.addElement("question");
					Element ele = SAXReaderUtil.createElement("formattype");
					ele.setText(formatType);		
					rootElement.add(ele);
					ele = SAXReaderUtil.createElement("correctiontype");
					ele.setText(partialCorrection);		
					rootElement.add(ele);
				}catch (NullPointerException e){
					document = SAXReaderUtil.createDocument();
					rootElement = document.addElement("question");
					Element ele = SAXReaderUtil.createElement("formattype");
					ele.setText(formatType);		
					rootElement.add(ele);
					ele = SAXReaderUtil.createElement("partialcorrection");
					ele.setText(partialCorrection);		
					rootElement.add(ele);
				}
				Element elemQuestion = null;
				//Edicion de pregunta o alineacion o correción
				if(!questionText.equals(question.getText())|| penalize != question.getPenalize() ||!formatType.equals(typeOrderBefore)||!partialCorrection.equals(partialCorrectionBefore)){

					question.setText(questionText);
					question.setPenalize(penalize);
					if((question.getExtracontent()==null)||(question.getExtracontent().trim().length()==0)){
						document = SAXReaderUtil.createDocument();
						rootElement = document.addElement("question");
					}else{
						document=SAXReaderUtil.read(question.getExtracontent());
						rootElement =document.getRootElement();
						elemQuestion = rootElement.element("formattype");
						if(elemQuestion!=null)
						{
							elemQuestion.detach();
							rootElement.remove(elemQuestion);
						}
						elemQuestion = rootElement.element("partialcorrection");
						if(elemQuestion!=null)
						{
							elemQuestion.detach();
							rootElement.remove(elemQuestion);
						}
					}
					elemQuestion = SAXReaderUtil.createElement("formattype");
					elemQuestion.setText(formatType);		
					rootElement.add(elemQuestion);
					elemQuestion = SAXReaderUtil.createElement("partialcorrection");
					elemQuestion.setText(partialCorrection);		
					rootElement.add(elemQuestion);
					question.setExtracontent(document.formattedString());
					TestQuestionLocalServiceUtil.updateTestQuestion(question);
				}
			}
			if(question!=null){
				questionId = question.getQuestionId();
				//Obtengo un array con los ids de las respuestas que ya contenia la pregunta
				List<TestAnswer> existingAnswers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
				List<Long> existingAnswersIds = new ArrayList<Long>();
				for(TestAnswer answer:existingAnswers){
					existingAnswersIds.add(answer.getAnswerId());
				}
				//Recorro todas las respuestas y las actualizo o las creo en funcion de si son nuevas o modificaciones y si son modificaciones guardo sus ids en un array para despues borrar las que no existan.
				String[] newAnswersIds = ParamUtil.getParameterValues(actionRequest, "answerId", null);
				List<Long> editingAnswersIds = new ArrayList<Long>();
				if(newAnswersIds != null){
					int counter = 1;
					int trueCounter = 0;
					if(question.getQuestionType()==2){
						trueCounter=1;
					}
					for(String newAnswerId:newAnswersIds){
						String answer = ParamUtil.get(actionRequest, "answer_"+newAnswerId, "");
						if(Validator.isNotNull(answer)){
							boolean correct = false;
							if(question.getQuestionType()==1 || question.getQuestionType()==4){
								if(newAnswerId.startsWith("new")){
									correct = ParamUtil.getBoolean(actionRequest, "correct_"+newAnswerId.substring(newAnswerId.indexOf("new")+3));
								}else{
									correct = ParamUtil.getBoolean(actionRequest, "correct_"+newAnswerId);
								}
							}else{
								if(question.getQuestionType()==5 ){
									correct = true;
								}else{
									
									if(question.getQuestionType()==3){
										correct = true;
									}else{
										correct = ParamUtil.getBoolean(actionRequest, "correct_new");
										if(ParamUtil.getInteger(actionRequest, "correct_new") == counter){
											correct = true;
										}else{
											correct = false;
										}
									}
								}
							}
							counter++;
							if(correct)trueCounter++;
							String feedbackCorrect = ParamUtil.getString(actionRequest, "feedbackCorrect_"+newAnswerId, "");
							if(feedbackCorrect.length()>600) feedbackCorrect = feedbackCorrect.substring(0, 600);
							String feedbackNoCorrect = ParamUtil.getString(actionRequest, "feedbackNoCorrect_"+newAnswerId, "");
							if(feedbackNoCorrect.length()>600) feedbackNoCorrect = feedbackNoCorrect.substring(0, 600);
							if("".equals(feedbackNoCorrect)) feedbackNoCorrect = feedbackCorrect;
							if(newAnswerId.startsWith("new")){
								//creo respuesta
								TestAnswerLocalServiceUtil.addTestAnswer(questionId, answer, feedbackCorrect, feedbackNoCorrect, correct);
							}else {
								editingAnswersIds.add(Long.parseLong(newAnswerId));//almaceno en array para posterior borrado de las que no esten
								//actualizo respuesta
								TestAnswer testanswer = TestAnswerLocalServiceUtil.getTestAnswer(Long.parseLong(newAnswerId));
								testanswer.setAnswer(answer);
								testanswer.setIsCorrect(correct);
								testanswer.setFeedbackCorrect(feedbackCorrect);
								testanswer.setFeedbacknocorrect(feedbackNoCorrect);
								TestAnswerLocalServiceUtil.updateTestAnswer(testanswer);
							}
						}else if(Validator.isNotNull(ParamUtil.getString(actionRequest, "feedbackCorrect_"+newAnswerId, "")) ||
								Validator.isNotNull(ParamUtil.getString(actionRequest, "feedbackNoCorrect_"+newAnswerId, "")) ||
								ParamUtil.getBoolean(actionRequest, "correct_"+newAnswerId)==true)
							SessionErrors.add(actionRequest, "answer-test-required");
					}
					if(trueCounter==0){
						SessionErrors.add(actionRequest, "execativity.test.error");
						actionResponse.setRenderParameter("message", LanguageUtil.get(themeDisplay.getLocale(), "execactivity.editquestions.newquestion"));
					}
				}

				//Recorro los ids de respuestas que ya contenia y compruebo si siguen estando, si no, elimino dichas respuestas.
				for(Long existingAnswerId:existingAnswersIds){
					if(editingAnswersIds != null && editingAnswersIds.size()>0){
						if(!editingAnswersIds.contains(existingAnswerId)){
							TestAnswerLocalServiceUtil.deleteTestAnswer(existingAnswerId);
						}
					}else TestAnswerLocalServiceUtil.deleteTestAnswer(existingAnswerId);
				}
				actionResponse.setRenderParameter("message", LanguageUtil.get(themeDisplay.getLocale(), "execativity.editquestions.editquestion"));
			}else {
				SessionErrors.add(actionRequest, "execativity.test.error");
				actionResponse.setRenderParameter("message", LanguageUtil.get(themeDisplay.getLocale(), "execactivity.editquestions.newquestion"));
			}
		}else {
			SessionErrors.add(actionRequest, "execactivity.editquestions.newquestion.error.text.required");
			actionResponse.setRenderParameter("message", LanguageUtil.get(themeDisplay.getLocale(), "execactivity.editquestions.newquestion"));
		}

		if(SessionErrors.size(actionRequest)==0) SessionMessages.add(actionRequest, "question-modified-successfully");
		actionResponse.getRenderParameterMap().putAll(actionRequest.getParameterMap());
		actionResponse.setRenderParameter("questionId", Long.toString(questionId));
		actionResponse.setRenderParameter("actionEditingDetails", StringPool.TRUE);
		actionResponse.setRenderParameter("resId", Long.toString(actid));
		actionResponse.setRenderParameter("typeId", Long.toString(questionType));
		actionResponse.setRenderParameter("backUrl", backUrl);
		actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/editQuestion.jsp");
	}


	public void edit(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortalException, SystemException, Exception {

		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		if(ParamUtil.getLong(actionRequest, "actId", 0)==0)// TODO Auto-generated method stub
		{
			actionResponse.setRenderParameter("jspPage", "/html/lmsactivitieslist/view.jsp");
		}
	}
	public void editactivity(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortalException, SystemException, Exception {
		long actId = ParamUtil.getInteger(actionRequest, "actId");
		// LearningActivity learnact =
		// com.liferay.lms.service.LearningActivityServiceUtil.getLearningActivity(actId);
		AssetRendererFactory laf = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
		if (laf != null) {
			AssetRenderer assetRenderer = laf.getAssetRenderer(actId, 0);

			String urlEdit = assetRenderer.getURLEdit((LiferayPortletRequest) actionRequest, (LiferayPortletResponse) actionResponse).toString();
			actionResponse.sendRedirect(urlEdit);
		}
		SessionMessages.add(actionRequest, "asset-renderer-not-defined");
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException {
		long actId=0;
		boolean actionEditingDetails = ParamUtil.getBoolean(renderRequest, "actionEditingDetails", false);

		if(actionEditingDetails){

			actId=ParamUtil.getLong(renderRequest, "resId", 0);
			renderResponse.setProperty("clear-request-parameters",Boolean.TRUE.toString());
		}
		else{
			actId=ParamUtil.getLong(renderRequest, "actId", 0);

		}
		renderResponse.setProperty("clear-request-parameters",Boolean.TRUE.toString());

		if(actId==0)// TODO Auto-generated method stub
		{
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
		else
		{
			LearningActivity activity;
			try {

				//auditing
				ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
				activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
				long typeId = activity.getTypeId();

				if(typeId==0 || (typeId == 4 && actionEditingDetails))
				{
					super.render(renderRequest, renderResponse);
				}
				else
				{
					renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
				}
			} catch (PortalException e) {
			} catch (SystemException e) {
			}			
		}
	}


	public void  serveResource(ResourceRequest request, ResourceResponse response)throws PortletException, IOException {

		String action = ParamUtil.getString(request, "action");
		long actId = ParamUtil.getLong(request, "resId",0);
		response.setCharacterEncoding(StringPool.UTF8);
		try {
			if(action.equals("exportResultsCsv")){
				response.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=data.csv");
				response.setContentType(ContentTypes.TEXT_CSV_UTF8);
				byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
				response.getPortletOutputStream().write(b);
				CSVWriter writer = new CSVWriter(new OutputStreamWriter(response.getPortletOutputStream(),StringPool.UTF8),CharPool.SEMICOLON);

				//Crear la cabecera con las preguntas.
				List<TestQuestion> questiones=TestQuestionLocalServiceUtil.getQuestions(actId);
				List<TestQuestion> questions = ListUtil.copy(questiones);
				BeanComparator beanComparator = new BeanComparator("weight");
				Collections.sort(questions, beanComparator);
				List<TestQuestion> questionsTitle = new ArrayList<TestQuestion>();
				for(TestQuestion question:questions){
					if(question.getQuestionType() == 0) questionsTitle.add(question);
				}
				//Anadimos x columnas para mostrar otros datos que no sean las preguntas como nombre de usuario, fecha, etc.
				int numExtraCols = 3;
				String[] cabeceras = new String[questionsTitle.size()+numExtraCols];

				//Guardamos el orden en que obtenemos las preguntas de la base de datos para poner las preguntas en el mismo orden.
				Long []questionOrder = new Long[questionsTitle.size()];

				//En las columnas extra ponemos la cabecera
				cabeceras[0]="User";
				cabeceras[1]="UserId";
				cabeceras[2]="Date";

				for(int i=numExtraCols;i<questionsTitle.size()+numExtraCols;i++){
					cabeceras[i]=formatString(questionsTitle.get(i-numExtraCols).getText())+" ("+questionsTitle.get(i-numExtraCols).getQuestionId()+")";
					questionOrder[i-numExtraCols]=questionsTitle.get(i-numExtraCols).getQuestionId();
				}
				writer.writeNext(cabeceras);

				//Partiremos del usuario para crear el csv para que sea mas facil ver los intentos.
				List<User> users = LearningActivityTryLocalServiceUtil.getUsersByLearningActivity(actId);

				for(User user:users){

					//Para cada usuario obtenemos los intentos para la learning activity.
					List<LearningActivityTry> activities = LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUser(actId, user.getUserId());
					List<Long> answersIds = new ArrayList<Long>();

					for(LearningActivityTry activity:activities){

						String xml = activity.getTryResultData();

						//Leemos el xml que contiene lo que ha respondido el estudiante.
						if(!xml.equals("")){

							Document document = SAXReaderUtil.read(xml);
							Element rootElement = document.getRootElement();

							//Obtenemos las respuestas que hay introducido.
							for(Element question:rootElement.elements("question")){

								TestQuestion q = TestQuestionLocalServiceUtil.getTestQuestion(Long.valueOf(question.attributeValue("id")));	        		

								if(q.getQuestionType() == 0){

									for(Element answerElement:question.elements("answer")){
										//Guardamos el id de la respuesta para posteriormente obtener su texto.
										if(Validator.isNumber(answerElement.attributeValue("id"))){
											answersIds.add(Long.valueOf(answerElement.attributeValue("id")));
										}
									}
								}

							}

							//Array con los resultados de los intentos.
							String[] resultados = new String[questionOrder.length+numExtraCols];

							//Introducimos los datos de las columnas extra
							resultados[0]=user.getFullName();
							resultados[1] = String.valueOf(user.getUserId());
							resultados[2] = String.valueOf(activity.getEndDate());

							for(int i=numExtraCols;i <questionOrder.length+numExtraCols ; i++){
								//Si no tenemos respuesta para la pregunta, guardamos ""
								resultados[i] = "-";

								for(int j=0;j <answersIds.size() ; j++){
									//Cuando la respuesta se corresponda con la pregunta que corresponde.
									if(Long.valueOf(getQuestionIdByAnswerId(answersIds.get(j))).compareTo(Long.valueOf(questionOrder[i-numExtraCols])) == 0){
										//Guardamos la respuesta en el array de resultados
										resultados[i]=getAnswerTextByAnswerId(answersIds.get(j));
									}
								}

							}
							//Escribimos las respuestas obtenidas para el intento en el csv.
							writer.writeNext(resultados);
						}
					}
				}

				writer.flush();
				writer.close();
					
			}else if(action.equals("exportXml")){
				response.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=data.xml");
				response.setContentType(ContentTypes.TEXT_XML_UTF8);
				PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(response.getPortletOutputStream(),StringPool.UTF8));
				Element quizXML=SAXReaderUtil.createElement("quiz");
				Document quizXMLDoc=SAXReaderUtil.createDocument(quizXML);
				
				List<TestQuestion> questiones=TestQuestionLocalServiceUtil.getQuestions(actId);
				List<TestQuestion> questions = ListUtil.copy(questiones);
				BeanComparator beanComparator = new BeanComparator("weight");
				Collections.sort(questions, beanComparator);
				
				if(questions!=null &&questions.size()>0){
					for(TestQuestion question:questions){
						QuestionType qt =new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
						quizXML.add(qt.exportXML(question.getQuestionId()));
					}
				}
				
				printWriter.write(quizXMLDoc.formattedString());
				printWriter.flush();
				printWriter.close();
			}
		
			response.getPortletOutputStream().flush();
			response.getPortletOutputStream().close();

		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			response.getPortletOutputStream().flush();
			response.getPortletOutputStream().close();
		}
	}

	private String formatString(String str) {

		String res = "";

		//Jsoup elimina todas la etiquetas html del string que se le pasa, devolviendo �nicamente el texto plano.
		res = Jsoup.parse(str).text();

		//Si el texto es muy largo, lo recortamos para que sea m�s legible.
		if(res.length() > 50){
			res = res.substring(0, 50);
		}

		return res;
	}

	private String getAnswerTextByAnswerId(Long answerId) throws PortalException, SystemException{
		//Buscamos la respuesta en el hashmap, si no lo tenemos, lo obtenemos de la bd y lo guardamos.
		if(!answersMap.containsKey(answerId))
		{
			TestAnswer answer = TestAnswerLocalServiceUtil.getTestAnswer(Long.valueOf(answerId));
			answersMap.put(answerId, answer);
		}

		return formatString(answersMap.get(answerId).getAnswer())+" ("+answersMap.get(answerId).getAnswerId()+")";
	}

	private Long getQuestionIdByAnswerId(Long answerId) throws PortalException, SystemException{
		//Buscamos la respuesta en el hashmap, si no lo tenemos, lo obtenemos y lo guardamos.
		if(!answersMap.containsKey(answerId))
		{
			TestAnswer answer = TestAnswerLocalServiceUtil.getTestAnswer(Long.valueOf(answerId));
			answersMap.put(answerId, answer);
		}

		return answersMap.get(answerId).getQuestionId();
	}

	public void deletequestion(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

		TestQuestion question = TestQuestionLocalServiceUtil.getTestQuestion(ParamUtil.getLong(actionRequest, "questionId"));
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(question.getActId());
		List<TestAnswer> lTestAnswer = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
		for ( TestAnswer testAnswer :  lTestAnswer ){
			TestAnswerLocalServiceUtil.deleteTestAnswer(testAnswer);
		}
		TestQuestionLocalServiceUtil.deleteTestQuestion(question.getQuestionId());
		SessionMessages.add(actionRequest, "question-deleted-successfully");

		if (learnact.getTypeId() == 0) {
			QuestionType qt =new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
			actionResponse.setRenderParameter("actionEditingDetails", StringPool.TRUE);
			actionResponse.setRenderParameter("resId", Long.toString(question.getActId()));
			actionResponse.setRenderParameter("jspPage", qt.getURLBack());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void moveQuestion(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
		
		long questionId = ParamUtil.getLong(actionRequest, "pageId"),
		     prevQuestionId = ParamUtil.getLong(actionRequest, "prevPageId"),
		     nextQuestionId = ParamUtil.getLong(actionRequest, "nextPageId");
		TestQuestion question = TestQuestionLocalServiceUtil.getTestQuestion(questionId);
		if(questionId>0){
			if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), LearningActivity.class.getName(), questionId, ActionKeys.UPDATE)){
				TestQuestionLocalServiceUtil.moveQuestion(questionId, prevQuestionId, nextQuestionId);
			}
		}
		
		String orderByCol = ParamUtil.getString(actionRequest, "orderByCol");
        if(orderByCol==null || orderByCol=="")
            orderByCol = "weight";
        actionRequest.setAttribute("orderByCol", orderByCol);
        //Create an instance of BeanComparator telling it wich is the order column
        //Get the type of ordering, asc or desc
        String orderByType = ParamUtil.getString(actionRequest, "orderByType");
        	if(orderByType==null || orderByType=="")
        		orderByType = "asc";
        	actionRequest.setAttribute("orderByType", orderByType);
        	TestQuestion questions = TestQuestionLocalServiceUtil.getTestQuestion(questionId);
        	List<TestQuestion> listaAux = TestQuestionLocalServiceUtil.getQuestions(questions.getActId());
        	List<TestQuestion> listaTotal = new LinkedList<TestQuestion>();
        	listaTotal = ListUtil.copy(listaAux);
        	//Sort
            BeanComparator beanComparator = new BeanComparator(orderByCol);
        	if(orderByType.equals("asc")){
        		Collections.sort(listaTotal, beanComparator);
			 } 
        	else {
        		Collections.sort(listaTotal, Collections.reverseOrder(beanComparator));
			 }
		//Return the orderer list
		actionRequest.setAttribute("total", listaTotal.size());
		actionRequest.setAttribute("listaAux", listaTotal);
		actionResponse.setRenderParameter("actionEditingDetails", StringPool.TRUE);
		actionResponse.setRenderParameter("resId", Long.toString(question.getActId()));
		actionResponse.setRenderParameter("jsp", "/html/execactivity/test/admin/orderQuestions.jsp");
	}
	
	public void upquestion(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {
	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
		
		long actId = ParamUtil.getLong(actionRequest, "actId",0);
		long testQuestionId = ParamUtil.getLong(actionRequest, "questionId");
		
		if(actId>0)
		{	
			LearningActivity larn = LearningActivityLocalServiceUtil.getLearningActivity(actId);
		
			if(permissionChecker.hasPermission(larn.getGroupId(), LearningActivity.class.getName(), larn.getActId(),
					ActionKeys.UPDATE)|| permissionChecker.hasOwnerPermission(larn.getCompanyId(), LearningActivity.class.getName(), larn.getActId(),larn.getUserId(),
							ActionKeys.UPDATE))
			{
			TestQuestionLocalServiceUtil.goUpTestQuestion(testQuestionId);
			}
		}
	}
	
	public void downquestion(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {
	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
		
		long actId = ParamUtil.getLong(actionRequest, "actId",0);
		long testQuestionId = ParamUtil.getLong(actionRequest, "questionId");
	
		if(actId>0)
		{
			LearningActivity larn = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			
			if(permissionChecker.hasPermission(larn.getGroupId(), LearningActivity.class.getName(), larn.getActId(),
					ActionKeys.UPDATE)|| permissionChecker.hasOwnerPermission(larn.getCompanyId(), LearningActivity.class.getName(), larn.getActId(),larn.getUserId(),
							ActionKeys.UPDATE))
			{
				TestQuestionLocalServiceUtil.goDownTestQuestion(testQuestionId);
			}
		}
	}
	
	public void setGrades(ActionRequest request, ActionResponse response) throws IOException, PortletException, SystemException {
		
		ThemeDisplay themeDisplay  =(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean correct=true;
		long actId = ParamUtil.getLong(request,"actId"); 
		Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());	
		long studentId = ParamUtil.getLong(request,"studentId");						
		String comments = ParamUtil.getString(request,"comments");
		
		log.debug("actId: "+actId);
		log.debug("studentId: "+studentId);
		log.debug("comments: "+comments);
		
		String firstName = ParamUtil.getString(request, "first-name");
		String lastName = ParamUtil.getString(request, "last-name");
		String screenName = ParamUtil.getString(request, "screen-name");
		String emailAddress = ParamUtil.getString(request, "email-address");
		
		log.debug("firstName: "+firstName);
		log.debug("lastName: "+lastName);
		log.debug("screenName: "+screenName);
		log.debug("emailAddress: "+emailAddress);
		
		response.setRenderParameter("first-name", firstName);
		response.setRenderParameter("last-name", lastName);	
		response.setRenderParameter("screen-name", screenName);
		response.setRenderParameter("email-address", emailAddress);	
		
		CalificationType ct = null;
		double result=0;
		try {	
			result = Double.valueOf(ParamUtil.getString(request,"result").replace(",", "."));
					
			ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());			
			
			log.debug("result: "+result);
			
			if(result<0 || result>100){
				correct=false;
				SessionErrors.add(request, "result-bad-format");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			correct=false;
			SessionErrors.add(request, "result-bad-format");
		} catch (Exception e) {
			e.printStackTrace();
			correct=false;
			SessionErrors.add(request, "grades.bad-updating");
		}
		
		if(correct) {
			try {
				LearningActivityTry  learningActivityTry =  LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, studentId);
				if(learningActivityTry==null){
					ServiceContext serviceContext = new ServiceContext();
					serviceContext.setUserId(studentId);
					learningActivityTry =  LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
				}
				learningActivityTry.setEndDate(new Date());
				learningActivityTry.setResult(ct.toBase100(course.getGroupCreatedId(),result));
				learningActivityTry.setComments(comments);
				updateLearningActivityTryAndResult(learningActivityTry);
				
				SessionMessages.add(request, "grades.updating");
			} catch (NestableException e) {
				SessionErrors.add(request, "grades.bad-updating");
			}
		}
		
		response.setRenderParameter("jspPage", "/html/execactivity/test/correction.jsp");
		response.setRenderParameter("actId", String.valueOf(actId));
		response.setRenderParameter("courseId", String.valueOf(course.getCourseId()));
	}
	
	public void setBankTest(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception{
		
		long actId = ParamUtil.getLong(actionRequest, "actId", 0);
		String redirect = actionRequest.getParameter("redirect");
		String isMultiple = ParamUtil.getString(actionRequest, "banks-multipleselections", "false");
		String isBank = ParamUtil.getString(actionRequest, "is-bank", "false"); 
		String assetCategoryIds = ParamUtil.getString(actionRequest, "assetCategoryIds", StringPool.BLANK);
		long[] longCategoryIds = GetterUtil.getLongValues(StringUtil.split(assetCategoryIds));
		
		AssetEntryQuery entryQuery = new AssetEntryQuery();
		entryQuery.setAllCategoryIds(longCategoryIds);
		
		if(!Validator.equals(AssetEntryLocalServiceUtil.getEntries(entryQuery).size(), 0)){
			LearningActivityLocalServiceUtil.setExtraContentValue(actId,"isBank", isBank);
			LearningActivityLocalServiceUtil.setExtraContentValue(actId,"isMultiple", isMultiple);
			if(!StringPool.BLANK.equals(assetCategoryIds)){
				LearningActivityLocalServiceUtil.setExtraContentValue(actId,"categoriesId", assetCategoryIds);
				SessionMessages.add(actionRequest,"data-exist-for-these-categories");
			}else{
				SessionErrors.add(actionRequest, "error-selector-categories-empty");
			}
		}else{
			SessionErrors.add(actionRequest, "error-not-results");
		}
		
		WindowState windowState = actionRequest.getWindowState();
		if (Validator.isNotNull(redirect)) {
			if (!windowState.equals(LiferayWindowState.POP_UP)) {
				actionResponse.sendRedirect(redirect);
			}
			else {
				redirect = PortalUtil.escapeRedirect(redirect);
				if (Validator.isNotNull(redirect)) {
					actionResponse.sendRedirect(redirect);
				}
			}
		}
		
	}
	
	
	private void updateLearningActivityTryAndResult(
			LearningActivityTry learningActivityTry) throws PortalException,
			SystemException {
		LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningActivityTry);
		
		LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivityTry.getActId(), learningActivityTry.getUserId());
		if(learningActivityResult.getResult() != learningActivityTry.getResult()) {
			LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(learningActivityTry.getActId());
			learningActivityResult.setResult(learningActivityTry.getResult());
			learningActivityResult.setPassed(learningActivityTry.getResult()>=learningActivity.getPasspuntuation());
			LearningActivityResultLocalServiceUtil.updateLearningActivityResult(learningActivityResult);
		}
	}
}
