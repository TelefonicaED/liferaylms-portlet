package com.liferay.lms.learningactivity.questiontype;

import java.util.Locale;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.theme.ThemeDisplay;

/**
 * Heredado todo de OptionsQuestionType (caso base de esta clase), salvo la creaci�n y la importaci�n, 
 * donde se limita si s�lo puede haber una respuesta correcta o varias.
 * 
 * @author je10396
 *
 */
public class MultioptionsQuestionType extends OptionsQuestionType {

	private static final long serialVersionUID = 1L;

	public long getTypeId(){
		return 1;
	}

	public String getName() {
		return "multioptions";
	}

	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "multioptions.title");
	}

	public String getDescription(Locale locale) {
		return LanguageUtil.get(locale, "multioptions.description");
	}
	
	
	protected boolean isQuestionCorrect(int correctAnswers, int correctAnswered, int incorrectAnswered){
		return correctAnswers==correctAnswered && incorrectAnswered==0;
	}
	
	public String getHtmlFeedback(Document document, long questionId, long actId, ThemeDisplay themeDisplay) {
		inputType="checkbox";
		return super.getHtmlFeedback(document, questionId, actId, themeDisplay);
	}
	
	public String getHtmlView(long questionId, ThemeDisplay themeDisplay, Document document) {
		inputType="checkbox";
		return super.getHtmlView(questionId, themeDisplay, document);
	}
	
	public Element exportXML(long questionId) {
		XMLSingle="false";
		return super.exportXML(questionId);
	}
	
	public int getMaxAnswers(){
		return GetterUtil.getInteger(PropsUtil.get("lms.maxAnswers.multioptions"), 100);
	}
	
	public int getDefaultAnswersNo(){
		return GetterUtil.getInteger(PropsUtil.get("lms.defaultAnswersNo.multioptions"), 2);
	}
	
	public boolean isPartialCorrectAvailable() {
		return true;
	}
}
