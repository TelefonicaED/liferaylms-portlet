package com.liferay.lms.learningactivity.questiontype;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class DraganddropQuestionType extends BaseQuestionType {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactoryUtil.getLog(DraganddropQuestionType.class);
	public long getTypeId(){
		return 4;
	}

	public String getName() {
		return "draganddrop";
	}

	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "draganddrop.title");
	}

	public String getDescription(Locale locale) {
		return LanguageUtil.get(locale, "draganddrop.description");
	}

	public String getURLEdit(){
		return "/html/questions/admin/editAnswerOptions.jsp";
	}
	
	public String getURLNew(){
		return "/html/questions/admin/popups/options.jsp";
	}

	public long correct(PortletRequest portletRequest, long questionId){
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers.addAll(TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId));
		} catch (SystemException e) {
			e.printStackTrace();
		}

		//me quedo solo con un array con la solucion
		for(java.util.ListIterator<TestAnswer> itr = testAnswers.listIterator(); itr.hasNext();){
			TestAnswer tanswer = itr.next();
			if(!tanswer.isIsCorrect()) itr.remove();
		}

		List<Long> answersId = new ArrayList<Long>();
		for(int i=0;i<testAnswers.size();i++){
			answersId.add(ParamUtil.getLong(portletRequest, "question_"+questionId+"_"+i+"hidden"));
		}

		if(!isCorrect(answersId, testAnswers)){
			return INCORRECT;
		}else{
			return CORRECT;
		}
	}
	
	@Override
	public long correct(Element element, long questionId){
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers.addAll(TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId));
		} catch (SystemException e) {
			e.printStackTrace();
		}

		//me quedo solo con un array con la solucion
		for(ListIterator<TestAnswer> itr = testAnswers.listIterator(); itr.hasNext();){
			TestAnswer tanswer = itr.next();
			if(!tanswer.isIsCorrect()) itr.remove();
		}

		List<Long> answersId = new ArrayList<Long>();
		for(int i=0;i<testAnswers.size();i++){
			log.debug("TEST ANSWER ID "+testAnswers.get(i).getAnswerId());
			Iterator<Element> itElements = element.elements("answer").iterator();
			boolean idExist = false;
			while(!idExist && itElements.hasNext()){
				Element questionElement = itElements.next();
				idExist = questionElement.attributeValue("id").equals(String.valueOf(testAnswers.get(i).getAnswerId()));
				log.debug("ID ELEMENT "+questionElement.attributeValue("id"));
				log.debug("ID ANSWER "+testAnswers.get(i).getAnswerId());
				log.debug("ID EXIST "+idExist);
				if(idExist){
					answersId.add(testAnswers.get(i).getAnswerId());
				}
			}
		}
		
		if(!isCorrect(answersId, testAnswers)){
			return INCORRECT;
		}else{
			return CORRECT;
		}
	}

	protected boolean isCorrect(List<Long> answersId, List<TestAnswer> testAnswers){
		boolean result = Boolean.TRUE;
		if (testAnswers.size() == answersId.size()) {
			// El numero de respuestas ha de coincidir
			for(TestAnswer testAnswer : testAnswers) {
				if (!answersId.contains(testAnswer.getAnswerId())) {
					result = Boolean.FALSE;
				}
			}
		} else {
			return Boolean.FALSE;
		}
		return result;
	}

	public String getHtmlView(long questionId, ThemeDisplay themeDisplay, Document document){
		return getHtml(document, questionId, false, themeDisplay);
	}

	public Element getResults(PortletRequest portletRequest, long questionId){
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers.addAll(TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId));
		} catch (SystemException e) {
			e.printStackTrace();
		}

		//me quedo solo con un array con la solucion
		for(java.util.ListIterator<TestAnswer> itr = testAnswers.listIterator(); itr.hasNext();){
			TestAnswer tanswer = itr.next();
			if(!tanswer.isIsCorrect()) itr.remove();
		}

		List<Long> answersId = new ArrayList<Long>();
		for(int i=0;i<testAnswers.size();i++){
			answersId.add(ParamUtil.getLong(portletRequest, "question_"+questionId+"_"+i+"hidden"));
		}

		Element questionXML=SAXReaderUtil.createElement("question");
		questionXML.addAttribute("id", Long.toString(questionId));
		
		long currentQuestionId = ParamUtil.getLong(portletRequest, "currentQuestionId");
		if (currentQuestionId == questionId) {
			questionXML.addAttribute("current", "true");
		}

		for(long answer:answersId){
			Element answerXML=SAXReaderUtil.createElement("answer");
			answerXML.addAttribute("id", Long.toString(answer));
			questionXML.add(answerXML);
		}
		return questionXML;
	}

	private String getHtml(Document document, long questionId, boolean feedback, ThemeDisplay themeDisplay){
		String html = "", leftCol="", rightCol = "", feedMessage="", feedsol="", showCorrectAnswer="false";
		String namespace = themeDisplay != null ? themeDisplay.getPortletDisplay().getNamespace() : "";
		try {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			//String feedMessage = LanguageUtil.get(themeDisplay.getLocale(),"answer-in-blank") ;
			List<TestAnswer> answersSelected=getAnswersSelected(document, questionId);
			List<TestAnswer> tA= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			List<Long>answersSelectedIds = new ArrayList<Long>();
			List<TestAnswer> sols = new ArrayList<TestAnswer>();

			//array con todas las respuestas posibles desordenadas
			ArrayList<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
			testAnswers.addAll(tA);
			Collections.shuffle(testAnswers);

			//la lista tA la reutilizo como lista con la solucion
			for(TestAnswer an:tA){
				if(an.isIsCorrect()) sols.add(an);
			}

			//Si el alumno ha pasado por la pregunta alguna vez, eliminamos de testAnswers las que el alumno puso en answersSelected
			if(answersSelected != null && answersSelected.size() == sols.size()){ 
				for(int k=0; k<answersSelected.size(); k++){
					if(answersSelected.get(k) != null){
						testAnswers.remove(answersSelected.get(k));
						answersSelectedIds.add(answersSelected.get(k).getAnswerId());
					}else
						answersSelectedIds.add(new Long(-1));
				}
				//sino, creamos el array de respuestas con el tamano que tiene q tener para pintar las cajas grises vacias.
			}else{
				for(int k=0; k<sols.size(); k++) answersSelectedIds.add(new Long(-1));
			}
			String correctionClass = "";
			if(feedback){
				feedMessage = LanguageUtil.get(locale,"answer-in-blank");
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
				if(isCorrect(answersSelectedIds, sols)) correctionClass = " correct";
				else correctionClass = " incorrect";
			}

			html += "<div id=\"id"+questionId+"\" class=\"question draganddrop"+correctionClass + " questiontype_" + getName() + " questiontype_" + getTypeId() +"\">"+
						"<input type=\"hidden\" name=\""+namespace+"question\" value=\"" + question.getQuestionId() + "\"/>"+
						"<div class=\"questiontext\">" + question.getText() + "</div>";

			//en la columna de la izq el contenido de testAnswers, con las que el estudiante dejo sin arrastrar
			leftCol +=	"<div class=\"items\" style=\"border:1px solid #fff; min-height:"+20*testAnswers.size()+"px; min-width=300px;\">";
			for(TestAnswer answer:testAnswers){
				leftCol += "<div id=\""+answer.getAnswerId()+"\" class=\"ui-corner-all\">"+answer.getAnswer()+"</div>";
			}
			leftCol +=	"</div>";

			//en la columna de la derecha el contenido de answersSelected, con las respuestas que dio el estudiante
			rightCol +=	"<div class=\"drop\">";
			for(int i=0;i<answersSelectedIds.size();i++){
				int aux = i+1;
				long value = -1;
				String text = LanguageUtil.format(locale, "drop", aux);
				if(answersSelectedIds.get(i)!= -1 && answersSelected.get(i) != null){
					value = answersSelected.get(i).getAnswerId();
					text = answersSelected.get(i).getAnswer();
				}
				
				if(feedback){
					if(answersSelectedIds.get(i) == sols.get(i).getAnswerId()) {
						//feedMessage = (!LanguageUtil.get(themeDisplay.getLocale(),"answer-in-blank").equals(feedMessage))?feedMessage+"<div class=\"questionFeedback\">"+sols.get(i).getFeedbackCorrect()+"</div>":"<div class=\"questionFeedback\">"+sols.get(i).getFeedbackCorrect()+"</div>";
						feedMessage = "<div class=\"questionFeedback\">"+sols.get(i).getFeedbackCorrect()+"</div>";
					}
					else {
						//feedMessage = (!LanguageUtil.get(themeDisplay.getLocale(),"answer-in-blank").equals(feedMessage))?feedMessage+"<div class=\"questionFeedback\">"+sols.get(i).getFeedbacknocorrect()+"</div>":"<div class=\"questionFeedback\">"+sols.get(i).getFeedbacknocorrect()+"</div>";
						String feedAux = new String();
						Iterator<TestAnswer> it = tA.iterator();
						TestAnswer answerAux = null;
						while (it.hasNext()){ 
							answerAux = it.next();
							if(answersSelectedIds.get(i) == answerAux.getAnswerId()){
								feedAux = answerAux.getFeedbacknocorrect();
								break;
							}
						}
						feedMessage = "<div class=\"questionFeedback\">"+feedAux+"</div>";
					}
					if("true".equals(showCorrectAnswer)) {
						feedsol = "<div class=\" font_14 color_cuarto negrita\">" + sols.get(i).getAnswer() + "</div>";
					}
					if (!"<div class=\"questionFeedback\"></div>".equals(feedMessage)) {
						feedsol += feedMessage;
					}
				}
				
				rightCol +=	"<input type=\"hidden\" name=\""+namespace+"question_" + question.getQuestionId() + "_" + i +"hidden\"  value=\""+value+"\"/>" +
						"<div name=\""+namespace+"question_" + question.getQuestionId() + "_" + i +"\" id=\"Drop"+aux +"\" class=\"drop-containers ui-corner-all background "+(value == -1 ? "base" : "occupied")+"\">"+
						(value == -1 ? "" : "<div id=\""+value+"\" class=\"ui-corner-all ui-draggable\">") +
						text +
						(value == -1 ? "" : "</div>") +
						"</div>"
						+ feedsol;
			}
			rightCol += "</div>";

			if(feedback) {
				html += "<div class=\"content_answer\">" + leftCol + rightCol + "</div>";
			}
			else html += leftCol + rightCol;
			html+=	"</div>";

		} catch (SystemException e) {
			e.printStackTrace();
		}


		return html;
	}

	@Override
	public String getHtmlFeedback(Document document, long questionId, long actId, ThemeDisplay themeDisplay){
		return getHtml(document, questionId, true, themeDisplay);
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
								long id = Long.valueOf(elementElement.attributeValue("id"));
								if(id != -1)
									answerSelected.add(TestAnswerLocalServiceUtil.getTestAnswer(id));
								else answerSelected.add(null);
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
		XMLType = "draganddrop";
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
		return GetterUtil.getInteger(PropsUtil.get("lms.maxAnswers.dragAndDrop"), 100);
	}
	public int getDefaultAnswersNo(){
		return GetterUtil.getInteger(PropsUtil.get("lms.defaultAnswersNo.dragAndDrop"), 2);
	}

}
