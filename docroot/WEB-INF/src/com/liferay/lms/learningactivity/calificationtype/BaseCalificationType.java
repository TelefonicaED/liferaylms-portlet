package com.liferay.lms.learningactivity.calificationtype;


import java.io.Serializable;
import java.util.Locale;

import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.ModuleResult;


public abstract class BaseCalificationType implements CalificationType, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public long getTypeId() {
		return -1;
	}
	
	@Override
	public String getName() {
		return "";
	}
	
	@Override
	public String getTitle(Locale locale) {
		return "";
	}
	
	@Override
	public String getDescription(Locale locale) {
		return "";
	}

	@Override
	public String getSuffix() {
		return "";
	}	
	
	@Override
	public String translate(Locale locale, double result) {
		return "";
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
	
	@Override
	public String translate(Locale locale, long companyId, double result) {
		return translate(locale, result);
	}
	
	@Override
	public long toBase100(double result) {
		return (long)result;
	}

	@Override
	public long getMinValue() {
		return 0;
	}

	@Override
	public long getMaxValue() {
		return 0;
	}
}
