package com.liferay.lms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.learningactivity.ResourceExternalLearningActivityType;
import com.liferay.lms.learningactivity.SurveyLearningActivityType;
import com.liferay.lms.learningactivity.TestLearningActivityType;
import com.liferay.lms.learningactivity.questiontype.QuestionType;
import com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry;
import com.liferay.lms.learningactivity.questiontype.SurveyHorizontalOptionsQuestionType;
import com.liferay.lms.learningactivity.questiontype.SurveyOptionsQuestionType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class QuestionsAdmin extends MVCPortlet{
	private static Log log = LogFactoryUtil.getLog(QuestionsAdmin.class);

	public static final Pattern DOCUMENT_EXCEPTION_MATCHER = Pattern.compile("Error on line (\\d+) of document ([^ ]+) : (.*)");
	public static final String TIMES_NEW_ROMAN = "Times New Roman";
	public static final int COLUMN_INDEX_QUESTION_TITLE = 0;
	public static final int COLUMN_INDEX_QUESTION_TYPE = 1;
	public static final int COLUMN_INDEX_QUESTION_PENALIZE = 2;
	public static final int COLUMN_INDEX_ANSWER_TITLE = 3;
	public static final int COLUMN_INDEX_ANSWER_IS_CORRECT = 4;
	public static final int COLUMN_INDEX_ANSWER_FEEDBACK_CORRECT = 5;
	public static final int COLUMN_INDEX_ANSWER_FEEDBACK_INCORRECT = 6;


	HashMap<Long, TestAnswer> answersMap = new HashMap<Long, TestAnswer>(); 


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
		actionResponse.setRenderParameter("jsp", "/html/questions/admin/orderQuestions.jsp");
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

	public void editQuestion(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

		long questionId = ParamUtil.getLong(actionRequest, "questionId", 0);
		long actid = ParamUtil.getLong(actionRequest, "resId");
		long questionType = ParamUtil.getLong(actionRequest, "typeId", -1);
		String questionText = ParamUtil.get(actionRequest, "text", "");

		boolean penalize = false;
		String partialCorrection = StringPool.BLANK;

		penalize = ParamUtil.getBoolean(actionRequest, "penalize");
		log.debug("***penalize:"+penalize);
		partialCorrection = ParamUtil.getString(actionRequest, "partialcorrection", "false");
		if(Boolean.parseBoolean(partialCorrection)){
			penalize = false;
		}


		log.debug("***questionId:"+questionId);
		log.debug("***penalize:"+penalize);

		String backUrl = ParamUtil.get(actionRequest, "backUrl", "");
		String formatType = ParamUtil.getString(actionRequest, "formattype", PropsUtil.get("lms.question.formattype.normal")); 
		Document document = null;
		Element rootElement = null;

		log.debug("questionType: " + questionType);
		if(questionType == 6 && PropsUtil.get("lms.question.formattype.horizontal").equals(formatType)){
			SurveyHorizontalOptionsQuestionType horizontalType = new SurveyHorizontalOptionsQuestionType();
			questionType = horizontalType.getTypeId();
		}else if (questionType == 7 && !PropsUtil.get("lms.question.formattype.horizontal").equals(formatType)){
			SurveyOptionsQuestionType verticalType = new SurveyOptionsQuestionType();
			questionType = verticalType.getTypeId();
		}
		log.debug("questionType: " + questionType);

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
				rootElement.add(elem);
				question.setExtracontent(document.formattedString());
				TestQuestionLocalServiceUtil.addTestQuestion(question);

			}else{//Pregunta existente
				question = TestQuestionLocalServiceUtil.getTestQuestion(questionId);
				log.debug("questionType: " + questionType);
				log.debug("question.getQuestionType(): " + question.getQuestionType());
				if(question.getQuestionType() != questionType){
					question.setQuestionType(questionType);
					question = TestQuestionLocalServiceUtil.updateTestQuestion(question);
					log.debug("question.getQuestionType(): " + question.getQuestionType());
				}

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
					question = TestQuestionLocalServiceUtil.updateTestQuestion(question);
					log.debug("question.getQuestionType(): " + question.getQuestionType());
				}
			}
			if(question!=null){
				questionId = question.getQuestionId();
				log.debug("question.getQuestionType() " + question.getQuestionType());
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
							}else if(question.getQuestionType()==5 ){
								correct = true;
							}else if(question.getQuestionType()==3){
								correct = true;
							}else if(question.getQuestionType() == 6 || question.getQuestionType() == 7){
								correct = true;
							}else{
								correct = ParamUtil.getBoolean(actionRequest, "correct_new");
								if(ParamUtil.getInteger(actionRequest, "correct_new") == counter){
									correct = true;
								}else{
									correct = false;
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

		log.debug("questionType: " + questionType);

		if(SessionErrors.size(actionRequest)==0) SessionMessages.add(actionRequest, "question-modified-successfully");
		actionResponse.getRenderParameterMap().putAll(actionRequest.getParameterMap());
		actionResponse.setRenderParameter("questionId", Long.toString(questionId));
		actionResponse.setRenderParameter("actionEditingDetails", StringPool.TRUE);
		actionResponse.setRenderParameter("resId", Long.toString(actid));
		actionResponse.setRenderParameter("questionTypeId", Long.toString(questionType));
		actionResponse.setRenderParameter("backUrl", backUrl);
		actionResponse.setRenderParameter("jspPage", "/html/questions/admin/editQuestion.jsp");
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
								try{
									TestQuestion q = TestQuestionLocalServiceUtil.getTestQuestion(Long.valueOf(question.attributeValue("id")));	        		

									if(q.getQuestionType() == 0){

										for(Element answerElement:question.elements("answer")){
											//Guardamos el id de la respuesta para posteriormente obtener su texto.
											if(Validator.isNumber(answerElement.attributeValue("id"))){
												answersIds.add(Long.valueOf(answerElement.attributeValue("id")));
											}
										}
									}
								}catch(NoSuchTestQuestionException e){
									if(log.isErrorEnabled()) log.error("En la actividad de tipo test "+activity.getActId()+" no se puede exportar la respuesta del usuario "+user.getUserId()+" para la pregunta "+question.attributeValue("id")+" porque ésta fue eliminada.");
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
			}else if(action.equals("exportExcel")){
				ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
				String name = LanguageUtil.get(themeDisplay.getLocale(), "questions")+".xls";
				File file = exportExcelQuestions(themeDisplay, actId);
				response.setContentType( ContentTypes.APPLICATION_VND_MS_EXCEL);
				response.setContentLength((int)file.length());
				response.addProperty(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
				ServletResponseUtil.sendFile(PortalUtil.getHttpServletRequest(request),
						PortalUtil.getHttpServletResponse(response),
						name,
						FileUtil.getBytes(file), 
						ContentTypes.APPLICATION_VND_MS_EXCEL);




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

	public void importQuestions(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

		UploadPortletRequest request = PortalUtil.getUploadPortletRequest(actionRequest);

		long actId = ParamUtil.getLong(actionRequest, "resId");
		String fileName = request.getFileName("fileName");
		if(fileName==null || StringPool.BLANK.equals(fileName)){
			SessionErrors.add(actionRequest, "execativity.editquestions.importquestions.xml.fileRequired");
			actionResponse.setRenderParameter("jspPage", "/html/questions/admin/importquestions.jsp");
		}
		else{ 
			String contentType = request.getContentType("fileName");	
			if (!ContentTypes.TEXT_XML.equals(contentType) && !ContentTypes.TEXT_XML_UTF8.equals(contentType) ) {
				SessionErrors.add(actionRequest, "execativity.editquestions.importquestions.xml.badFormat");	
				actionResponse.setRenderParameter("jspPage", "/html/questions/admin/importquestions.jsp");
			}
			else {
				try {
					Document document = SAXReaderUtil.read(request.getFile("fileName"));
					if (TestQuestionLocalServiceUtil.isTypeAllowed(actId, document)){
						TestQuestionLocalServiceUtil.importXML(actId, document);
						SessionMessages.add(actionRequest, "questions-added-successfully");
						actionResponse.setRenderParameter("jspPage", "/html/questions/admin/editquestions.jsp");
					}else{
						SessionErrors.add(actionRequest, "execativity.editquestions.importquestions.xml.not.allowed");
						actionResponse.setRenderParameter("jspPage", "/html/questions/admin/importquestions.jsp");
					}
				} catch (DocumentException e) {
					e.printStackTrace();
					Matcher matcher = DOCUMENT_EXCEPTION_MATCHER.matcher(e.getMessage());

					if(matcher.matches()) {
						SessionErrors.add(actionRequest, "execativity.editquestions.importquestions.xml.parseXMLLine", matcher.group(1));
					}
					else{
						SessionErrors.add(actionRequest, "execativity.editquestions.importquestions.xml.parseXML");					
					}
					actionResponse.setRenderParameter("jspPage", "/html/questions/admin/importquestions.jsp");
				} catch (Exception e) {
					e.printStackTrace();
					SessionErrors.add(actionRequest, "execativity.editquestions.importquestions.xml.generic");
					actionResponse.setRenderParameter("jspPage", "/html/questions/admin/importquestions.jsp");
				}
			}

		}

		actionResponse.setRenderParameter("actionEditingDetails", StringPool.TRUE);
		actionResponse.setRenderParameter("resId", Long.toString(actId));	
	}


	public void importExcelQuestions(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException, IOException {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		long actId = ParamUtil.getLong(actionRequest, "resId",0);

		String fileName = uploadRequest.getFileName("fileName");
		InputStream excelFile = uploadRequest.getFileAsStream("fileName");
		if(fileName==null || StringPool.BLANK.equals(fileName)){
			SessionErrors.add(actionRequest, "surveyactivity.csverror.fileRequired");
			actionResponse.setRenderParameter("jspPage", "/html/questions/admin/importQuestionsExcel.jsp");
		}else{ 
			String contentType = uploadRequest.getContentType("fileName");	
			if (!ContentTypes.APPLICATION_VND_MS_EXCEL.equals(contentType)) {
				SessionErrors.add(actionRequest, "surveyactivity.csvError.bad-format");	
				actionResponse.setRenderParameter("jspPage", "/html/questions/admin/importQuestionsExcel.jsp");
			}else{
				Workbook workbook = null;
				try{
					workbook = new HSSFWorkbook(excelFile);
				}catch(Exception e){//Excel 2007
					workbook = new XSSFWorkbook(excelFile);

				}			

				//Cogemos la primera hoja
				Sheet worksheet = workbook.getSheetAt(0);
				int fila = 0;
				String questionTitle, feedbackCorrect, feedbackIncorrect;
				int questionType;
				boolean allCorrect=true;
				boolean questionPenalize;
				String answerTitle;
				boolean answerIsCorrect;
				boolean firstLine = true;
				Row row = worksheet.getRow(fila);
				TestQuestion question =null;
				while(row != null){
					//La primera linea es la cabecera
					try{
						if(!firstLine){
							try{	
								questionTitle = row.getCell(COLUMN_INDEX_QUESTION_TITLE).getStringCellValue();
								answerTitle = row.getCell(COLUMN_INDEX_ANSWER_TITLE).getStringCellValue();
								answerIsCorrect = Boolean.parseBoolean(row.getCell(COLUMN_INDEX_ANSWER_IS_CORRECT).getStringCellValue());
								feedbackCorrect =  row.getCell(COLUMN_INDEX_ANSWER_FEEDBACK_CORRECT).getStringCellValue();
								feedbackIncorrect = row.getCell(COLUMN_INDEX_ANSWER_FEEDBACK_INCORRECT).getStringCellValue();
								if(questionTitle!=null && Validator.isNotNull(questionTitle.trim())){
									questionType = Integer.valueOf(row.getCell(COLUMN_INDEX_QUESTION_TYPE).getStringCellValue());
									questionPenalize = Boolean.parseBoolean(row.getCell(COLUMN_INDEX_QUESTION_PENALIZE).getStringCellValue());
									//Es pregunta
									if (log.isDebugEnabled()) log.debug("Line: " + fila + " ***********Es pregunta************");
									if (log.isDebugEnabled()) log.debug("Line: " + fila + " Titulo pregunta: " + questionTitle);					
									if (log.isDebugEnabled()) log.debug("Line: " + fila + " Tipo: " + questionType);
									if (log.isDebugEnabled()) log.debug("Line: " + fila + " Penalize: " + questionPenalize);

									//Creamos la pregunta.
									question =TestQuestionLocalServiceUtil.addQuestion(actId, questionTitle, questionType);
									question.setPenalize(questionPenalize);
									question = TestQuestionLocalServiceUtil.updateTestQuestion(question);
									if(answerTitle!=null && Validator.isNotNull(answerTitle.trim())){
										//Si tiene respuestas, creamos la respuesta 
										if (log.isDebugEnabled()) log.debug("Line: " + fila + " ***********Tiene respuesta************");
										if (log.isDebugEnabled()) log.debug("Line: " + fila + " Titulo respuesta: " + answerTitle);
										if (log.isDebugEnabled()) log.debug("Line: " + fila + " Es correcta: " + answerIsCorrect);
										TestAnswerLocalServiceUtil.addTestAnswer(question.getQuestionId(), answerTitle, feedbackCorrect, feedbackIncorrect, answerIsCorrect);
									}


								}else{	//Es solo respuesta
									if (log.isDebugEnabled()) log.debug("Line: " + fila + " ***********Es solo respuesta************");
									if (log.isDebugEnabled()) log.debug("Line: " + fila + " Titulo respuesta: " + answerTitle);
									if (log.isDebugEnabled()) log.debug("Line: " + fila + " Es correcta: " + answerIsCorrect);
									if(feedbackCorrect!=null && feedbackCorrect.length()>1000){
										feedbackCorrect = feedbackCorrect.substring(0, 999);
									}

									if(feedbackIncorrect!=null && feedbackIncorrect.length()>1000){
										feedbackIncorrect = feedbackIncorrect.substring(0, 999);
									}
									if(question!=null){
										TestAnswerLocalServiceUtil.addTestAnswer(question.getQuestionId(), answerTitle, feedbackCorrect, feedbackIncorrect, answerIsCorrect);
									}
								}
							}catch(Exception e){
								log.error(e.getMessage());
								log.debug(e);
								log.error("FILA "+fila);
								SessionErrors.add(actionRequest, "surveyactivity.csvError.bad-question",fila);
								actionResponse.setRenderParameter("jspPage", "/html/questions/admin/importQuestionsExcel.jsp");
								allCorrect=false;
							}
						}else{
							firstLine = false;
						}
						fila++;
						row = worksheet.getRow(fila);
					}catch(Exception e){
						e.printStackTrace();
						SessionErrors.add(actionRequest, "surveyactivity.csvError.bad-format-line",fila);
						actionResponse.setRenderParameter("jspPage", "/html/questions/admin/importQuestionsExcel.jsp");
						allCorrect=false;
						e.printStackTrace();	
					}

				}	

				if(allCorrect){
					log.debug("ALL CORRECT!!!");
					actionResponse.setRenderParameter("jspPage", "/html/questions/admin/editquestions.jsp");
					SessionMessages.add(actionRequest, "questions-added-successfully");
				}

			}

		}

		if (!SessionErrors.isEmpty(actionRequest)){
			actionResponse.setRenderParameter("jspPage", "/html/questions/admin/importQuestionsExcel.jsp");
		}

		actionResponse.setRenderParameter("actionEditingDetails", StringPool.TRUE);
		actionResponse.setRenderParameter("resId", Long.toString(actId));
	}

	private File exportExcelQuestions(ThemeDisplay themeDisplay, long actId) throws PortletException, IOException {

		log.debug("::ARRANCAMOS HILO USERS EXPORT EXCEL:::"); 
		int rowNumber = 1;
		// Presenta en pantalla informacion sobre este hilo en particular
		File file = FileUtil.createTempFile("xls");
		try {

			FileOutputStream bw = null;
			try {
				bw = new FileOutputStream(file);
			} 
			catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			String [] headers = {"title","type","penalize","answer","correct","feedbackCorrect","feedbackNoCorrect"} ;
			String [] headersTitle = new String [headers.length];
			int i = 0;
			for(String header : headers) {
				headersTitle[i++] = LanguageUtil.get(themeDisplay.getLocale(), header, header);
				log.debug("hearder: " + header);
			}

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(LanguageUtil.get(themeDisplay.getLocale(), "questions", "questions"));

			HSSFFont font = workbook.createFont();
			font.setFontName(TIMES_NEW_ROMAN);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle style = workbook.createCellStyle();
			style.setFont(font);

			exportExcelLine(headersTitle, sheet.createRow(0), style);

			font = workbook.createFont();
			font.setFontName(TIMES_NEW_ROMAN);
			style = workbook.createCellStyle();
			style.setFont(font);
			String[] questionLine = new String[headers.length];
			List<TestAnswer> testAnswers = null;

			for(TestQuestion question: TestQuestionLocalServiceUtil.getQuestions(actId)){
				questionLine[COLUMN_INDEX_QUESTION_TITLE]=question.getText();
				questionLine[COLUMN_INDEX_QUESTION_TYPE]=String.valueOf(question.getQuestionType());
				questionLine[COLUMN_INDEX_QUESTION_PENALIZE]= String.valueOf(question.getPenalize());
				i=0;
				testAnswers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
				
				if(testAnswers != null && testAnswers.size() > 0){
					for(TestAnswer answer: testAnswers){
						questionLine[COLUMN_INDEX_ANSWER_TITLE]=answer.getAnswer();
						questionLine[COLUMN_INDEX_ANSWER_IS_CORRECT]=String.valueOf(answer.isIsCorrect());
						questionLine[COLUMN_INDEX_ANSWER_FEEDBACK_CORRECT]=answer.getFeedbackCorrect();
						questionLine[COLUMN_INDEX_ANSWER_FEEDBACK_INCORRECT]=answer.getFeedbacknocorrect();
	
						exportExcelLine(questionLine, sheet.createRow(rowNumber++), style);
	
						if(questionLine[COLUMN_INDEX_QUESTION_TITLE]!="" || questionLine[COLUMN_INDEX_QUESTION_TYPE]!=""|| questionLine[COLUMN_INDEX_QUESTION_PENALIZE]!= ""){
							questionLine[COLUMN_INDEX_QUESTION_TITLE]="";
							questionLine[COLUMN_INDEX_QUESTION_TYPE]="";
							questionLine[COLUMN_INDEX_QUESTION_PENALIZE]= "";
						}
					}
				}else{
					questionLine[COLUMN_INDEX_ANSWER_TITLE]="";
					questionLine[COLUMN_INDEX_ANSWER_IS_CORRECT]="";
					questionLine[COLUMN_INDEX_ANSWER_FEEDBACK_CORRECT]="";
					questionLine[COLUMN_INDEX_ANSWER_FEEDBACK_INCORRECT]="";
					exportExcelLine(questionLine, sheet.createRow(rowNumber++), style);
				}

			}
			workbook.write(bw);

			try {
				bw.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}						

		}catch (Exception e) {
			e.printStackTrace();
		} 

		return file;

	}


	private void exportExcelLine(String[] line,HSSFRow row,HSSFCellStyle style){
		int columnNumer = 0;
		for (String column : line) {
			HSSFCell cell = row.createCell(columnNumer++, HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(style);
			cell.setCellValue(column);
		}
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

		if (learnact.getTypeId() == ResourceExternalLearningActivityType.TYPE_ID || learnact.getTypeId() == SurveyLearningActivityType.TYPE_ID || learnact.getTypeId() == TestLearningActivityType.TYPE_ID) {
			QuestionType qt =new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
			actionResponse.setRenderParameter("actionEditingDetails", StringPool.TRUE);
			actionResponse.setRenderParameter("resId", Long.toString(question.getActId()));
			actionResponse.setRenderParameter("jspPage", qt.getURLBack());
		
		}
	}

}
