package com.liferay.lms.learningactivity.questiontype;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;

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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class FreetextQuestionType extends BaseQuestionType {

	private static final long serialVersionUID = 1L;

	public void setLocale(Locale locale){
		super.setLocale(locale);
	}

	public long getTypeId(){
		return 2;
	}

	public String getName() {
		return "freetext";
	}

	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "freetext.title");
	}

	public String getDescription(Locale locale) {
		return LanguageUtil.get(locale, "freetext.description");
	}

	public String getURLEdit(){
		return "/html/questions/admin/editAnswerFreetext.jsp";
	}
	
	public String getURLNew(){
		return "/html/questions/admin/popups/freetext.jsp";
	}

	public long correct(PortletRequest portletRequest, long questionId){
		String answer= ParamUtil.getString(portletRequest, "question_"+questionId, "");
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
		} catch (SystemException e) {
			e.printStackTrace();
		}

		if(testAnswers!=null && testAnswers.size()>0){
			TestAnswer solution = testAnswers.get(0);
			if (isCorrect(solution, answer)){
				return CORRECT;
			}else{
				return INCORRECT;
			}
		}
		return INCORRECT;
	}
	
	@Override
	public long correct(Element element, long questionId){
		String answer= element.element("answer").getText();
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
		} catch (SystemException e) {
			e.printStackTrace();
		}

		if(testAnswers!=null && testAnswers.size()>0){
			TestAnswer solution = testAnswers.get(0);
			if (isCorrect(solution, answer)){
				return CORRECT;
			}else{
				return INCORRECT;
			}
		}
		return INCORRECT;
	}

	protected boolean isCorrect(TestAnswer solution, String answer){
		Collator c = Collator.getInstance();
		c.setStrength(Collator.PRIMARY);
		if(c.compare(solution.getAnswer(), answer) == 0){
			return true;
		}else{
			return false;
		}
	}

	public String getHtmlView(long questionId, ThemeDisplay themeDisplay, Document document){
		return getHtml(document, questionId, false, themeDisplay);
	}

	public Element getResults(PortletRequest portletRequest, long questionId){
		String answer= ParamUtil.getString(portletRequest, "question_"+questionId, "");

		Element questionXML=SAXReaderUtil.createElement("question");
		questionXML.addAttribute("id", Long.toString(questionId));

		long currentQuestionId = ParamUtil.getLong(portletRequest, "currentQuestionId");
		if (currentQuestionId == questionId) {
			questionXML.addAttribute("current", "true");
		}

		Element answerXML=SAXReaderUtil.createElement("answer");
		answerXML.addText(answer);
		questionXML.add(answerXML);

		return questionXML;
	}

	private String getHtml(Document document, long questionId, boolean feedback, ThemeDisplay themeDisplay){
		String feedBack = "", answersFeedBack= "", cssclass = "", showCorrectAnswer = "false";
		String namespace = themeDisplay != null ? themeDisplay.getPortletDisplay().getNamespace() : "";
		try {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			String feedMessage = LanguageUtil.get(themeDisplay.getLocale(),"answer-in-blank") ;
			String answer=getAnswersSelected(document, questionId);
	
			List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			if(testAnswers!=null && testAnswers.size()>0){//el profesor puso alguna soluci�n para correcci�n autom�tica
				TestAnswer solution = testAnswers.get(0);
				if(feedback){
					showCorrectAnswer = LearningActivityLocalServiceUtil.getExtraContentValue(question.getActId(), "showCorrectAnswer");
					String showCorrectAnswerOnlyOnFinalTryString = LearningActivityLocalServiceUtil.getExtraContentValue(question.getActId(), "showCorrectAnswerOnlyOnFinalTry");
					try {
						if ("true".equals(showCorrectAnswerOnlyOnFinalTryString)) {
							if(LearningActivityTryLocalServiceUtil.canUserDoANewTry(question.getActId(), themeDisplay.getUserId())){
								showCorrectAnswer = "false";
							}else{
								showCorrectAnswer = "true";
							}
						}
					} catch (Exception e) {}
					if(isCorrect(solution, answer)){
						feedMessage=solution.getFeedbackCorrect();
						cssclass=" correct";
					}else {
						feedMessage=solution.getFeedbacknocorrect();
						cssclass=" incorrect";
					}
				}

				answersFeedBack = answer;
				if("true".equals(showCorrectAnswer)) answersFeedBack += "<br/>" +"<div class=\"answer font_14 color_cuarto negrita\">" +
																						solution.getAnswer() +
																				"</div>";
			}else{//el profesor lo corregira manualmente
				answersFeedBack = answer;
				if(feedback) feedMessage = (locale!=null)?LanguageUtil.get(locale, "manually-correction"):"A evaluar manualmente por el profesor";
			}

			if(feedback) { 
				answersFeedBack = "<div class=\"content_answer\">" + answersFeedBack + "</div>";
				if (!"".equals(feedMessage)) {
					answersFeedBack += "<div class=\"questionFeedback\">" + feedMessage + "</div>";
				}
			}

			feedBack += "<div class=\"question" + cssclass + " questiontype_" + getName() + " questiontype_" + getTypeId() + "\">" + 
					"<input type=\"hidden\" name=\""+namespace+"question\" value=\"" + question.getQuestionId() + "\"/>"+
					"<div class=\"questiontext\">" + question.getText() + "</div>" +
					(!feedback ? "<div class=\"answer\"><label for=\""+namespace+"question_" + question.getQuestionId() + "\" /><textarea rows=\"4\" cols=\"60\" maxlength=\"1000\" id=\""+namespace+"question_" + question.getQuestionId() + "\" name=\""+namespace+"question_" + question.getQuestionId() + "\">"+answer+"</textarea></div>" : "") +
					answersFeedBack +
					"</div>";	
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return feedBack;
	}

	public String getHtmlFeedback(Document document,long questionId, long actId, ThemeDisplay themeDisplay){
		return getHtml(document, questionId, true, themeDisplay);
	}

	protected String getAnswersSelected(Document document,long questionId){
		String answerGiven = "";
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
								answerGiven = elementElement.getText();
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}	
		}
		return answerGiven;
	}
	
	public Element exportXML(long questionId) {
		try {
			List<TestAnswer> answers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
			if(answers==null || answers.size()==0){
				XMLType="essay";
			}else XMLType="numerical";
			Element questionXML = super.exportXML(questionId);
			if("numerical".equals(XMLType)){
				for(TestAnswer answer:answers){
					Element answerE = SAXReaderUtil.createElement("answer");
					answerE.addAttribute("fraction", "100");
					
					Element text = SAXReaderUtil.createElement("text");
					text.addText(answer.getAnswer());
					answerE.add(text);
					
					Element feedback = SAXReaderUtil.createElement("feedback");
					Element feedText = SAXReaderUtil.createElement("text");
					feedText.addText(answer.getFeedbackCorrect()+"//"+ answer.getFeedbacknocorrect());
					feedback.add(feedText);
					answerE.add(feedback);
					questionXML.add(answerE);
					break;
				}
			}else{
				Element answerE = SAXReaderUtil.createElement("answer");
				answerE.addAttribute("fraction", "0");
				
				Element text = SAXReaderUtil.createElement("text");
				answerE.add(text);
				
				questionXML.add(answerE);
			}
			return questionXML;
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return SAXReaderUtil.createElement("");
	}

	public void importXML(long actId, Element question, TestAnswerLocalService testAnswerLocalService)throws SystemException, PortalException {
		//"essay","numerical","shortanswer"
		Element questiontext=question.element("questiontext");
		String description=questiontext.elementText("text");
		TestQuestion theQuestion=TestQuestionLocalServiceUtil.addQuestion(actId,description,getTypeId());
		if(!"essay".equals(question.attributeValue("type"))){//los essay en moodle nunca tienen respuesta
			for(Element answerElement:question.elements("answer")){
				boolean correct=("100".equals(answerElement.attributeValue("fraction")))? true:false;
				String answer=answerElement.elementText("text");
				String feedback="", feedbackCorrect="", feedbackNoCorrect="";
				if(answerElement.element("feedback")!=null && answerElement.element("feedback").element("text")!=null)
					feedback=answerElement.element("feedback").element("text").getText();	 
				if(feedback.contains("//")){
					String[] split = feedback.split("//");
					if(split.length == 2){
						feedbackCorrect = split[0];
						feedbackNoCorrect = split[1];
					}else{
						feedbackCorrect = feedback;
						feedbackNoCorrect = feedback;
					}
				}else{
					feedbackCorrect = feedback;
					feedbackNoCorrect = feedback;
				}
				testAnswerLocalService.addTestAnswer(theQuestion.getQuestionId(), answer, feedbackCorrect, feedbackNoCorrect, correct);
				return;//porque inicialmente solo aceptamos una respuesta
			}
		}
	}
	
	public int getMaxAnswers(){
		return 1;
	}
	public int getDefaultAnswersNo(){
		return 1;
	}

}
