package com.liferay.lms.learningactivity.questiontype;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;

import sun.security.util.PendingException;

import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalService;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class OptionsQuestionType extends BaseQuestionType {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactoryUtil.getLog(OptionsQuestionType.class);
	protected String inputType = "radio";
	protected String XMLSingle = "true";

	public long getTypeId(){
		return 0;
	}

	public String getName() {
		return "options";
	}

	@Override
	public boolean isInline() {
		return true;
	}

	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "options.title");
	}

	public String getDescription(Locale locale) {
		return LanguageUtil.get(locale, "options.description");
	}
	
	public String getURLEdit(){
		return "/html/questions/admin/editAnswerOptions.jsp";
	}
	
	public String getURLNew(){
		return "/html/questions/admin/popups/options.jsp";
	}

	public long correct(PortletRequest portletRequest, long questionId){
		long[] answersId= ParamUtil.getLongValues(portletRequest, "question_"+questionId);
		List<Long> arrayAnswersId = new ArrayList<Long>();
		for(long answerId:answersId) arrayAnswersId.add(answerId);

		return correct(questionId, arrayAnswersId);
		
	}
	
	@Override
	public long correct(Element element, long questionId){
		
		Iterator<Element> iteratorAnswers = element.elementIterator("answer");
		List<Long> arrayAnswersId = new ArrayList<Long>();
		
		Element elementAnswer = null;
		
		while(iteratorAnswers.hasNext()) {
			elementAnswer = iteratorAnswers.next();
			arrayAnswersId.add(Long.parseLong(elementAnswer.attributeValue("id")));
		}
		
		
		return correct(questionId, arrayAnswersId);

	}
	
	private long correct(long questionId, List<Long> arrayAnswersId){
		long retVal = 0L;
		
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		int correctAnswers=0, correctAnswered=0, incorrectAnswered=0;
		for(TestAnswer answer:testAnswers){
			log.debug("comprobamos la respuesta: " + answer.getAnswerId());
			if(isCorrect(answer)){
				log.debug("es la correcta");
				correctAnswers++;
				if(arrayAnswersId.contains(answer.getAnswerId())){ 
					correctAnswered++;
				}
			}else if(arrayAnswersId.contains(answer.getAnswerId())){ 
				incorrectAnswered++;
			}
		}
		boolean partialCorrection = false;
		try{
			String extraContent = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId).getExtracontent();
			if(log.isDebugEnabled())
				log.debug("Question " + questionId + " extracontent: " + extraContent);
				
			Document document = SAXReaderUtil.read(extraContent);
			Element rootElement = document.getRootElement();
			partialCorrection = StringPool.TRUE.equals(rootElement.element("partialcorrection").getData());
		}catch(NullPointerException e){
			partialCorrection = false;
		}catch(DocumentException e){
			partialCorrection = false;
		} catch (SystemException e) {
			partialCorrection = false;
		}
		if(partialCorrection){
			retVal = correctAnswered*100/correctAnswers;
		}else{
			log.debug("correctAnswers: " + correctAnswers);
			log.debug("correctAnswered: " + correctAnswered);
			log.debug("incorrectAnswered: " + incorrectAnswered);
			if(isQuestionCorrect(correctAnswers, correctAnswered, incorrectAnswered)){
				retVal = CORRECT;
			}
			else{
				retVal = getPenalize() ? -CORRECT : INCORRECT;
			}
		}
		
		return retVal;
	}
	
	
	protected boolean isQuestionCorrect(int correctAnswers, int correctAnswered, int incorrectAnswered){
		return correctAnswered>0 && incorrectAnswered==0;
	}

	protected boolean isCorrect(TestAnswer testAnswer){
		return (testAnswer!=null)?testAnswer.isIsCorrect():false;
	}

	public String getHtmlView(long questionId, ThemeDisplay themeDisplay, Document document){
		return getHtml(document, questionId, false, 0, themeDisplay);
	}

	public Element getResults(PortletRequest portletRequest, long questionId){
		long[] answersId= ParamUtil.getLongValues(portletRequest, "question_"+questionId);

		List<Long> arrayAnswersId = new ArrayList<Long>();
		for(long answerId:answersId) arrayAnswersId.add(answerId);

		Element questionXML=SAXReaderUtil.createElement("question");
		questionXML.addAttribute("id", Long.toString(questionId));

		long currentQuestionId = ParamUtil.getLong(portletRequest, "currentQuestionId");
		if (currentQuestionId == questionId) {
			questionXML.addAttribute("current", "true");
		}

		for(long answer:arrayAnswersId){
			if(answer >0){
				Element answerXML=SAXReaderUtil.createElement("answer");
				answerXML.addAttribute("id", Long.toString(answer));
				questionXML.add(answerXML);
			}
		}
		return questionXML;
	}

	private String getHtml(Document document, long questionId,boolean feedback, long actId, ThemeDisplay themeDisplay){
		String html = "", answersFeedBack="", feedMessage = "", cssclass="", selected="";
		String namespace = themeDisplay != null ? themeDisplay.getPortletDisplay().getNamespace() : "";
		String timestamp="";
		boolean isCombo = false;
		String onclick = "";
		String javascript = "";
		try {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			if( Validator.equals(actId, 0) ){
				actId = question.getActId();
			}
			String formatType = "0";
			boolean enableOrder = StringPool.TRUE.equals(PropsUtil.get("lms.learningactivity.testoption.editformat"));
			if(question.getExtracontent()!=null && !question.getExtracontent().trim().isEmpty()){
				try{
					Document xml = SAXReaderUtil.read(question.getExtracontent());
					Element ele = xml.getRootElement();
					formatType = (String) ele.element("formattype").getData();
					if ( enableOrder && formatType.equals(PropsUtil.get("lms.question.formattype.horizontal")) ){
						cssclass="in-line ";
					}else if ( enableOrder && formatType.equals(PropsUtil.get("lms.question.formattype.combo")) ){
						isCombo=true;
					}
				}catch(DocumentException e){
					e.printStackTrace();
				}
			}
			List<TestAnswer> answersSelected=getAnswersSelected(document, questionId);
			List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			int correctAnswers=0, correctAnswered=0, incorrectAnswered=0;
			if(feedback) feedMessage = "";
			boolean notAnswers = true;
			int numAnswer=0;
			String disabled = "";
			if (isCombo && !feedback){
				answersFeedBack+="<option class=\"selected\" value=\"\">"+LanguageUtil.get(themeDisplay.getLocale(),"select")+"</option>";
			}
			for(TestAnswer answer:testAnswers){
				if(inputType.equals("checkbox")){
					String maxNumberOfCheck = PropsUtil.get("lms.question.multiple.maxnumbercheck");
					if(StringPool.NULL.equals(maxNumberOfCheck)){
						maxNumberOfCheck = "0";
					}
					onclick = "onclick=\""+namespace+"checkMaxNumberOfChecks('"+question.getQuestionId()+"','"+numAnswer+"')\"";
					javascript += "<script type=\"text/javascript\"> var numberOfChecks = 0; "
							+ "function " + namespace + "checkMaxNumberOfChecks(idQ,idA){"
							+ "		var A = AUI();"
							+ "		if(A.one('#" + namespace + "question_'+idQ+'_'+idA+':checked')){"
							+ "			numberOfChecks++;"
							+ "			if(numberOfChecks==" + maxNumberOfCheck + "){ "
							+ "				A.all('div.answer input[type=\"checkbox\"]').setAttribute('disabled','disabled');"
							+ "				var inputs = A.all('div.answer input[type=\"checkbox\"]:checked');"
							+ "				inputs.each(function(input){"
							+ "					input.removeAttribute('disabled');"
							+ "				});"
							+ "			}"
							+ " 	}else{"
							+ "			if(numberOfChecks==" + maxNumberOfCheck + "){"
							+ "			A.all('div.answer input[type=\"checkbox\"]').removeAttribute('disabled');"
							+ "		}"
							+ "		numberOfChecks--;"
							+ "	}"
							+ "}"
							+ "</script>";
				}
				String correct="", checked="", showCorrectAnswer="false";
				disabled = "";
				if(feedback) {
					showCorrectAnswer = LearningActivityLocalServiceUtil.getExtraContentValue(actId, "showCorrectAnswer");
					String showCorrectAnswerOnlyOnFinalTryString = LearningActivityLocalServiceUtil.getExtraContentValue(actId, "showCorrectAnswerOnlyOnFinalTry");
					try {
						if ("true".equals(showCorrectAnswerOnlyOnFinalTryString)) {
							if(LearningActivityTryLocalServiceUtil.canUserDoANewTry(actId, themeDisplay.getUserId())){
								showCorrectAnswer = "false";
							}else{
								showCorrectAnswer = "true";
							}
						}
					} catch (Exception e) {}
					disabled = "disabled='disabled'";
					Date now = new Date();
					timestamp = String.valueOf(now.getTime());
				}
				if(isCorrect(answer)){
					correctAnswers++;
					if("true".equals(showCorrectAnswer)) correct="font_14 color_cuarto negrita";
					if(answersSelected.contains(answer)){
						correctAnswered++;
						checked="checked='checked'";
						notAnswers = false;
						if(Validator.isNotNull(answer.getFeedbackCorrect())){
							feedMessage=(!LanguageUtil.get(themeDisplay.getLocale(),"answer-in-blank").equals(feedMessage))?feedMessage+"<br/>"+answer.getFeedbackCorrect():answer.getFeedbackCorrect();
						}
					}
				}else if(answersSelected.contains(answer)){
					incorrectAnswered++;
					checked="checked='checked'";
					selected="selected";
					notAnswers = false;
					if(Validator.isNotNull(answer.getFeedbacknocorrect())){
						feedMessage=(!LanguageUtil.get(themeDisplay.getLocale(),"answer-in-blank").equals(feedMessage))?feedMessage+"<br/>"+answer.getFeedbacknocorrect():answer.getFeedbacknocorrect();
					}
				}

				if (isCombo && !feedback){
					answersFeedBack += 	"<option " + selected + " value= \"" + answer.getAnswerId() + "\" >" +
											answer.getAnswer() +			
										"</option>";
				}else{
					answersFeedBack += "<div class=\"answer " + cssclass + correct + "\">" +
											"<label for=\""+namespace+"question_"+question.getQuestionId()+"_"+numAnswer+"\" />"+
											"<input "+onclick+" id=\""+namespace+"question_"+question.getQuestionId()+"_"+numAnswer+"\" type=\"" + inputType 
												+ "\" name=\""+namespace+"question_" + question.getQuestionId() +timestamp+ "\" " + checked + " value=\"" 
													+ answer.getAnswerId() +"\" " + disabled + ">" +
											"<div class=\"answer-options\">" + answer.getAnswer() + 
											"</div>" + 
										"</div>";
				}
				
				numAnswer++;
			}

			if(feedback){
				
				if(notAnswers){
					feedMessage = LanguageUtil.get(themeDisplay.getLocale(),"answer-in-blank");
				}
				
				
				if(isQuestionCorrect(correctAnswers, correctAnswered, incorrectAnswered))	cssclass="correct ";
				else cssclass="incorrect ";
				
				answersFeedBack = "<div class=\"content_answer\">" + answersFeedBack + "</div>";
				if (!"".equals(feedMessage)) {
					answersFeedBack += "<div class=\"questionFeedback\">" + feedMessage + "</div>";
				}
			}
			
			if (isCombo && !feedback){
				html += "<div class=\"question " + cssclass + "questiontype_" + getName() + "_select questiontype_" + getTypeId() + "\">" +
							"<input type=\"hidden\" name=\""+namespace+"question\" value=\"" + question.getQuestionId() + "\"/>"+
							"<div class=\"questiontext select\">" + question.getText() + "</div>" +
							"<div class=\"answer select\">" +
								"<select "+ disabled + "class=\"answer select\" id=\""+namespace+"question_"+question.getQuestionId()+"_"+numAnswer+"\" name=\""+namespace+"question_"+question.getQuestionId()+"\" />"+
									answersFeedBack +
								"</select>" +
							"</div>" +
						"</div>";
			}else{
				html += "<div class=\"question " + cssclass + "questiontype_" + getName() + " questiontype_" + getTypeId() + "\">" +
						"<input type=\"hidden\" name=\""+namespace+"question\" value=\"" + question.getQuestionId() + "\"/>"+
						"<div class=\"questiontext " + cssclass + "\">" + question.getText() + "</div>" +
							answersFeedBack +
						"</div>";	
			}
			
			
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return html+javascript;
	}

	@Override
	public String getHtmlFeedback(Document document, long questionId, long actId, ThemeDisplay themeDisplay){
		return getHtml(document, questionId, true, actId, themeDisplay);
	}

	protected List<TestAnswer> getAnswersSelected(Document document,long questionId){
		List<TestAnswer> answerSelected = new ArrayList<TestAnswer>();
		if(document != null){
			Iterator<Element> nodeItr = document.getRootElement().elementIterator();
			while(nodeItr.hasNext()) {
				Element element = nodeItr.next();
				if("question".equals(element.getName()) && questionId == Long.valueOf(element.attributeValue("id"))){
					Iterator<Element> elementItr = element.elementIterator();
					while(elementItr.hasNext()) {
						Element elementElement = elementItr.next();
						if("answer".equals(elementElement.getName())) {
							try {
								answerSelected.add(TestAnswerLocalServiceUtil.getTestAnswer(Long.valueOf(elementElement.attributeValue("id"))));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							} catch (PortalException e) {
								e.printStackTrace();
							} catch (SystemException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}	
		}
		return answerSelected;
	}
	
	public Element exportXML(long questionId) {
		XMLType="multichoice";
		Element questionXML = super.exportXML(questionId);
		try {
			List<TestAnswer> answers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
			for(TestAnswer answer:answers){
				Element answerE = SAXReaderUtil.createElement("answer");
				answerE.addAttribute("fraction", (answer.isIsCorrect())?"100":"0");
				
				Element text = SAXReaderUtil.createElement("text");
				text.addText(answer.getAnswer());
				answerE.add(text);
				
				Element feedback = SAXReaderUtil.createElement("feedback");
				Element feedText = SAXReaderUtil.createElement("text");
				feedText.addText(answer.getFeedbackCorrect());
				feedback.add(feedText);
				answerE.add(feedback);
				questionXML.add(answerE);
			}
			Element single = SAXReaderUtil.createElement("single");
			single.addText(XMLSingle);
			questionXML.add(single);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return questionXML;
	}

	public void importXML(long actId, Element question, TestAnswerLocalService testAnswerLocalService)throws SystemException, PortalException {
		Element questiontext=question.element("questiontext");
		String description=questiontext.elementText("text");
		TestQuestion theQuestion=TestQuestionLocalServiceUtil.addQuestion(actId,description,getTypeId());
		for(Element answerElement:question.elements("answer")){
			boolean correct=(!"0".equals(answerElement.attributeValue("fraction")))? true:false;
			String answer=answerElement.elementText("text");
			String feedback="";
			if(answerElement.element("feedback")!=null && answerElement.element("feedback").element("text")!=null)
				feedback=answerElement.element("feedback").element("text").getText();
			testAnswerLocalService.addTestAnswer(theQuestion.getQuestionId(), answer, feedback, feedback, correct);
		}
	}

	public int getMaxAnswers(){
		return GetterUtil.getInteger(PropsUtil.get("lms.maxAnswers.options"), 100);
	}
	public int getDefaultAnswersNo(){
		return GetterUtil.getInteger(PropsUtil.get("lms.defaultAnswersNo.options"), 2);
	}

}
