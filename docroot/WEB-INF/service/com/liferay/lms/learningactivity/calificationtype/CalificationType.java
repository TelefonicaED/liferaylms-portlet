package com.liferay.lms.learningactivity.calificationtype;

import java.util.Locale;

import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.ModuleResult;

public interface CalificationType 
{
	public long getTypeId();
	public String getName();
	public String getTitle(Locale locale);
	public String getDescription(Locale locale);
	public String getSuffix();
	public String translate(Locale locale, double result);
	public String translate(Locale locale, CourseResult result);
	public String translate(Locale locale, ModuleResult result);
	public String translate(Locale locale, LearningActivityResult result);
	public String translate(Locale locale,long companyId, double result);
}
