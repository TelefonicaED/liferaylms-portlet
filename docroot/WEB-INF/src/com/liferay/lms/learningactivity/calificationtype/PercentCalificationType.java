package com.liferay.lms.learningactivity.calificationtype;


import java.text.DecimalFormat;
import java.util.Locale;

import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.ModuleResult;


public class PercentCalificationType extends BaseCalificationType {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public long getTypeId() {
		return 0;
	}
	
	@Override
	public String getName() {
		return "percent_ct";
	}
	
	@Override
	public String getTitle(Locale locale) {
		return "percent_ct.title";
	}
	
	@Override
	public String getDescription(Locale locale) {
		return "percent_ct.description";
	}
	
	@Override
	public String getSuffix() {
		return "/100";
	}

	@Override
	public String translate(Locale locale, double result) {
		DecimalFormat df = new DecimalFormat("##.#");
		return df.format(result);
	}
	
	@Override
	public String translate(Locale locale, CourseResult result) {
		return translate(locale, result.getResult());
	}

	@Override
	public String translate(Locale locale, ModuleResult result) {
		return translate(locale, result.getResult());
	}

	@Override
	public String translate(Locale locale, LearningActivityResult result) {
		return translate(locale, result.getResult());
	}
}
