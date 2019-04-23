package com.liferay.lms.learningactivity.questiontype;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;

import org.jsoup.Jsoup;

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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class FillblankQuestionType extends BaseQuestionType {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactoryUtil.getLog(FillblankQuestionType.class);
	public long getTypeId(){
		return 3;
	}
	
	public String getName() {
		return "fillblank";
	}

	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "fillblank.title");
	}

	public String getDescription(Locale locale) {
		return LanguageUtil.get(locale, "fillblank.description");
	}
	
	public String getURLEdit(){
		return "/html/questions/admin/editAnswerFillblank.jsp";
	}
	
	public String getURLNew(){
		return "/html/questions/admin/popups/fillblank.jsp";
	}
	
	public long correct(PortletRequest portletRequest, long questionId){
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		TestAnswer solution = null;
		if(testAnswers!=null && testAnswers.size()>0)
			solution = testAnswers.get(0);
		
		if(solution!=null){
			int correctAnswers=0;
			
			List<String> sols = getQuestionSols(solution.getAnswer());
			int i=0;
			for(String sol:sols){
				String answer= ParamUtil.getString(portletRequest, "question_"+questionId+"_"+i, "").replace(",", "");
				if(isCorrect(sol, answer)){
					log.debug("CORRECT "+i);
					correctAnswers++;
				}
				i++;
			}
			if(sols.size()>0){
				double puntuation = correctAnswers*100.0/sols.size(); 
				log.debug("----PUNTUATION "+puntuation);
				return Math.round(puntuation);
			}
			
			if(correctAnswers==sols.size()){
				return CORRECT;
			}else{
				return INCORRECT;
			}
		}
	
		return INCORRECT;
	}
	
	@Override
	public long correct(Element element, long questionId){
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		TestAnswer solution = null;
		if(testAnswers!=null && testAnswers.size()>0)
			solution = testAnswers.get(0);
		
		if(solution!=null){
			int correctAnswers=0;
			
			List<String> sols = getQuestionSols(solution.getAnswer());
			String[] answers = element.element("answer").getText().split(",");
			
			for(int i = 0; i < sols.size(); i++){
				if(isCorrect(sols.get(i), answers[i])){
					log.debug("CORRECT "+i);
					correctAnswers++;
				}
			}
			if(sols.size()>0){
				double puntuation = correctAnswers*100.0/sols.size(); 
				log.debug("----PUNTUATION "+puntuation);
				return Math.round(puntuation);
			}
			
			if(correctAnswers==sols.size()){
				return CORRECT;
			}else{
				return INCORRECT;
			}
		}
	
		return INCORRECT;		
	}

	private List<String> getQuestionSols(String textAnswer) {
		List<String> sols = new ArrayList<String>();//array con las soluciones {...}
		String temp="";
		int start = textAnswer.indexOf("{"), end = 0;
		while (start != -1){
			end = textAnswer.indexOf("}");
			if(end != -1){
				if(end+1 == textAnswer.length()) temp = textAnswer.substring(start);
				else {
					if(textAnswer.charAt(end+1) == '}')
						if(end+2 == textAnswer.length()) temp = textAnswer.substring(start);
						else temp = textAnswer.substring(start, end+2);
					else temp = textAnswer.substring(start, end+1);
				}
				if(temp.startsWith("{{") || isMoodleAnswer(temp))sols.add(temp);				
				textAnswer = textAnswer.substring(0,start)+textAnswer.substring(start+temp.length());//textAnswer.replace(temp, "");
				
				start = textAnswer.indexOf("{");
			}
		}
		return sols;
	}
	
	protected boolean isCorrect(String solution, String answer){
		boolean correct = false;
		Collator c = Collator.getInstance();
		c.setStrength(Collator.PRIMARY);
		List<String> sols = getBlankSols(solution, true);
		for(String sol:sols){
			if(c.compare(answer,sol)==0) {
				correct = true;
				break;
			}
		}
		return correct;
	}

	private List<String> getBlankSols(String solution, boolean onlyCorrectOnes) {
		List<String> correctSols =new ArrayList<String>();
		if(solution.startsWith("{{")){
			solution = solution.replace("{{", "");
			if(solution.contains("}}")) solution = solution.replace("}}", "");
			solution = Jsoup.parse(solution).text();
			correctSols.add(solution);
		}else if(solution.startsWith("{")){
			boolean isNumerical = false;
			if(solution.contains(":NUMERICAL:") || solution.contains(":NM:")) isNumerical = true;
			String aux = solution.substring(solution.indexOf(":", solution.indexOf(":")+1)+1);
			if(aux.endsWith("}")) aux = aux.substring(0, aux.length()-1);
			String[] sols = aux.split("~");
			for(String sol:sols){
				if(!sol.startsWith("*#")){
					if(sol.startsWith("=")) sol = sol.replace("=", "");
					else if(sol.startsWith("%") && !sol.startsWith("%0%")) sol = sol.replace(sol.substring(sol.indexOf("%"), sol.lastIndexOf("%")+1), "");
					else {
						if(sol.startsWith("%0%")) sol = sol.replace(sol.substring(sol.indexOf("%"), sol.lastIndexOf("%")+1), "");
						if(onlyCorrectOnes) sol = "*#";//para que no incluya las q son falsas
					}
					if(!sol.startsWith("*#")){
						if(sol.contains("#")) sol=sol.substring(0,sol.indexOf("#"));
						if(isNumerical && sol.contains(":")) sol = sol.substring(0, sol.indexOf(":"));
						if(!correctSols.contains(sol)) correctSols.add(sol);	
					}
				}
			}
		}
		return correctSols;
	}
	
	public String getHtmlView(long questionId, ThemeDisplay themeDisplay, Document document){
		return getHtml(document, questionId, false, themeDisplay);
	}
	
	private boolean isMoodleAnswer(String temp) {
		if(temp.contains(":SHORTANSWER") || temp.contains(":SA") || temp.contains(":MW")
				|| temp.contains(":NUMERICAL:") || temp.contains(":NM:") || temp.contains("{{") 
				|| temp.contains(":MULTICHOICE_") || temp.contains(":MCV") || temp.contains(":MCH")
				|| temp.contains(":MULTICHOICE:") || temp.contains(":MC:")) return true;
		return false;
	}
	
	public Element getResults(PortletRequest portletRequest, long questionId){
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		TestAnswer solution = null;
		if(testAnswers!=null && testAnswers.size()>0)
			solution = testAnswers.get(0);
		
		String answer = "";
		
		if(solution!=null){
			int i = getQuestionSols(solution.getAnswer()).size();
			for(int k=0; k<i; k++){
				if(answer!="") answer+=",";
				answer+= ParamUtil.getString(portletRequest, "question_"+questionId+"_"+k, "").replace(",", ""); //Quito la , de la respuesta del usaurio
			}
		}
    	
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
		String html = "", answersFeedBack="", cssclass="", showCorrectAnswer = "false", feedMessage = "";
		String namespace = themeDisplay != null ? themeDisplay.getPortletDisplay().getNamespace() : "";
		try {
			
			//Cogemos las respuestas a los blancos (separadas por coma) de la pregunta a partir del xml de learningactivityresult
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			String answer = getAnswersSelected(document, questionId);	
			String[] answers = answer.split(",");
			//Cogemos el TestAnswer de la pregunta en formato Moodle para rellenar las respuestas dadas por el alumno
			List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			if(testAnswers!=null && testAnswers.size()>0){
				//Comprobamos si todos los blancos son acertados para ver si la pregunta resulta correcta o no
				TestAnswer solution = testAnswers.get(0);
				List<String> sols = getQuestionSols(solution.getAnswer());

				//String[] answers = answer.split(",");
				if (feedback){
					feedMessage = LanguageUtil.get(themeDisplay.getLocale(),"answer-in-blank") ;
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
					int correctAnswers=0,i=0;
					
					for(String sol:sols){
						String ans= (answers.length>i)?answers[i]:"";
						//String ans= answer;
						if(isCorrect(sol, ans)){
							correctAnswers++;
						}
						i++;
					}
					if(correctAnswers==sols.size()){
						feedMessage=solution.getFeedbackCorrect();
						cssclass=" correct";
					}else {
						feedMessage=solution.getFeedbacknocorrect();
						cssclass=" incorrect";
					}
				}
				
				//Obtain feedback
				String solok="";
				answersFeedBack = translateNewLines(solution.getAnswer());
				
				int i=0;
				for(String sol:sols){
					//String ans = (answers.length>i)?answers[i]:"";
					String ans= answer;
					String auxans = "";
					List<String> blankSols = getBlankSols(sol, true);
					
					if(sol.contains(":SHORTANSWER") || sol.contains(":SA") || sol.contains(":MW")
							|| sol.contains(":NUMERICAL:") || sol.contains(":NM:") || sol.contains("{{")) {
						
						ans= (answers.length>i)?answers[i]:"";
						String readonly = "";
						if (feedback) {
							readonly = "readonly";
						}
						auxans= "<label for=\""+namespace+"question_"+question.getQuestionId()+"_"+i+"\" > <input id=\""+namespace+"question_" + question.getQuestionId()+"_"+i+"\" name=\""+namespace+"question_" + question.getQuestionId()+"_"+i + "\" "+readonly+" type=\"text\" value=\""+ans+"\" /></label>";//input
						
						if("true".equals(showCorrectAnswer)) {
							for(String blankSol:blankSols){
								if(solok != "") solok += " | ";
								solok += blankSol;
							}
							auxans += "<div class=\" font_14 color_cuarto negrita\"> (" + solok + ") </div>";
						}
					}else if(sol.contains(":MULTICHOICE_") || sol.contains(":MCV") || sol.contains(":MCH")){
						String aux = "";
						auxans = "<br/><div class=\"multichoice\">";
						List<String> totalBlankSols = getBlankSols(sol, false);
						for(String blankSol:totalBlankSols){
							String checked = "", disabled = "", correct = "";
							if(blankSol.equals(ans)) checked="checked='checked'";
							if(feedback) disabled = "disabled='disabled'";
							if("true".equals(showCorrectAnswer) && blankSols.contains(blankSol)) correct = "font_14 color_cuarto negrita";
							aux = "<div class=\"answer " + correct + "\"> <label for=\""+namespace+"question_"+question.getQuestionId()+"_"+i+"\" > <input id=\""+namespace+"question_" + question.getQuestionId()+"_"+i + "\" name=\""+namespace+"question_" + question.getQuestionId()+"_"+i + "\" type=\"radio\"" + checked + "value=\"" + blankSol + "\" "+disabled+" /></label>" + blankSol + "</div>";//radiobuttons
							auxans += aux;
						}
						auxans += "</div>";
					}else if(sol.contains(":MULTICHOICE:") || sol.contains(":MC:")){
						String disabled = "";
						if(feedback) disabled = "disabled='disabled'";
						auxans+="<select "+disabled+" name=\""+namespace+"question_" + question.getQuestionId()+"_"+i + "\" >";
						auxans+="<option value=\"\" "+disabled+" label=\"\"/>";//primer valor vac�o
						List<String> totalBlankSols = getBlankSols(sol, false);
						for(String blankSol:totalBlankSols){
							String selected = "";
							if(ans.equals(blankSol)) selected ="selected";							
							auxans+="<option value=\""+ blankSol +"\" "+disabled+" label=\""+blankSol +"\" "+ selected +"/>";//dropdown
						}
						auxans+="</select>";
						if("true".equals(showCorrectAnswer)) {
							for(String blankSol:blankSols){
								if(solok != "") solok += " | ";
								solok += blankSol;
							}
							auxans += "<div class=\" font_14 color_cuarto negrita\"> (" + solok + ") </div>";
						}
					}else{
						auxans+=sol;
					}
					answersFeedBack = answersFeedBack.substring(0,answersFeedBack.indexOf(sol))+auxans+answersFeedBack.substring(answersFeedBack.indexOf(sol)+sol.length()); 
					//answersFeedBack.replace(sol, auxans);
					i++;solok="";
				}
				
				if(feedback) {
					answersFeedBack = "<div class=\"content_answer\">" + answersFeedBack + "</div>";
					if (!"".equals(feedMessage)) {
						answersFeedBack += "<div class=\"questionFeedback\">" + feedMessage + "</div>";
					}
				}
				
				html += "<div class=\"question" + cssclass + " questiontype_" + getName() + " questiontype_" + getTypeId() + "\">" + 
							"<input type=\"hidden\" name=\""+namespace+"question\" value=\"" + question.getQuestionId() + "\"/>"+
							"<div class=\"questiontext\">" + question.getText() + "</div><div class=\"answer-fillblank\">" +
							answersFeedBack +
						"</div></div>";
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return html;
	}
	
	@Override
	public String getHtmlFeedback(Document document, long questionId, long actId, ThemeDisplay themeDisplay){
		return getHtml(document, questionId, true, themeDisplay);
	}

	protected String getAnswersSelected(Document document, long questionId) {
		String answer = "";
		if(document != null){
			Iterator<Element> nodeItr = document.getRootElement().elementIterator();
			while(nodeItr.hasNext()) {
				Element element = nodeItr.next();
			     if("question".equals(element.getName()) && questionId == Long.valueOf(element.attributeValue("id"))){
			    	 Iterator<Element> elementItr = element.elementIterator();
			    	 if(elementItr.hasNext()) {
			    		 Element elementElement = elementItr.next();
			    		 if("answer".equals(elementElement.getName())) {
			    			 try {
								answer = elementElement.getText();
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
			    		 }
			    	 }
			     }
			}
		}
		return answer;
	}
	
	public Element exportXML(long questionId) {
		XMLType = "cloze";
		Element questionXML=SAXReaderUtil.createElement("question");
		questionXML.addAttribute("type", XMLType);
		
		Element name = SAXReaderUtil.createElement("name");
		Element text = SAXReaderUtil.createElement("text");
		try {
			TestQuestion question = TestQuestionLocalServiceUtil.getTestQuestion(questionId);
			text.addText(question.getText());
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}
		name.add(text);
		questionXML.add(name);
		
		Element questionText = SAXReaderUtil.createElement("questiontext");
		questionText.addAttribute("format", "html");
		Element textqt = SAXReaderUtil.createElement("text");
		String feedback="";
		try{
			List<TestAnswer> answers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
			for(TestAnswer answer:answers){
				textqt.addText(answer.getAnswer());
				feedback = answer.getFeedbackCorrect()+"//"+ answer.getFeedbacknocorrect();
				break;//Solo aceptamos una respuesta
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}	
		questionText.add(textqt);
		questionXML.add(questionText);
		
		Element feedbackE = SAXReaderUtil.createElement("generalfeedback");
		Element feedText = SAXReaderUtil.createElement("text");
		feedText.addText(feedback);
		feedbackE.add(feedText);
		questionXML.add(feedbackE);
		
		return questionXML;
	}
	
	public void importXML(long actId, Element question, TestAnswerLocalService testAnswerLocalService)throws SystemException, PortalException {
		//"cloze"
		Element name=question.element("name");
		String description=(name!=null)?name.elementText("text"):"";
		TestQuestion theQuestion=TestQuestionLocalServiceUtil.addQuestion(actId,description,getTypeId());
		Element questiontext=question.element("questiontext");
		String answer=questiontext.elementText("text");
		Element generalFeedback=question.element("generalfeedback");
		String feedback=generalFeedback.elementText("text");
		String feedbackCorrect = "", feedbackNoCorrect="";
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
		testAnswerLocalService.addTestAnswer(theQuestion.getQuestionId(), answer, feedbackCorrect, feedbackNoCorrect, true);
	}
	
	private String translateNewLines(String input){
		input = input.replace("\n", "<br/>");
		input = input.replace("\r", "<br/>");
		return input;
	}
	
	public int getMaxAnswers(){
		return 1;
	}
	public int getDefaultAnswersNo(){
		return 1;
	}
	
}
