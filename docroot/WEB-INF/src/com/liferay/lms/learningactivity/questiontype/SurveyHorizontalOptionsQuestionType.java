package com.liferay.lms.learningactivity.questiontype;

import java.util.Locale;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * Heredado casi todo de OptionsQuestionType (caso base de esta clase)
 * 
 * @author je10396
 *
 */
public class SurveyHorizontalOptionsQuestionType extends OptionsQuestionType {

	private static final long serialVersionUID = 1L;

	public long getTypeId(){
		return 7;
	}

	public String getName() {
		return "surveyoptionshorizontal";
	}

	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "surveyoptionshorizontal.title");
	}

	public String getDescription(Locale locale) {
		return LanguageUtil.get(locale, "surveyoptionshorizontal.description");
	}
	
	public String getURLEdit(){
		return "/html/questions/admin/editSurveyAnswerOptions.jsp";
	}
	
	public String getURLNew(){
		return "/html/questions/admin/popups/surveyoptions.jsp";
	}
	
	protected boolean isQuestionCorrect(int correctAnswers, int correctAnswered, int incorrectAnswered){
		return true;
	}
	
	public int getMaxAnswers(){
		return GetterUtil.getInteger(PropsUtil.get("lms.maxAnswers.surveyoptions"), 100);
	}
	
	public int getDefaultAnswersNo(){
		return GetterUtil.getInteger(PropsUtil.get("lms.defaultAnswersNo.surveyoptions"), 2);
	}
	
	public boolean getPenalize(){
		return false;
	}
}
