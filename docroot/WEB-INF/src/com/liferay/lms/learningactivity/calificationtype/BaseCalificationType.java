package com.liferay.lms.learningactivity.calificationtype;


import java.io.Serializable;
import java.util.Locale;


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
	
}
