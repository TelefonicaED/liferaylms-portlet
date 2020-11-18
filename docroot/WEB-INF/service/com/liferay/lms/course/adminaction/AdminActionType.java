package com.liferay.lms.course.adminaction;

import java.util.Locale;

import com.liferay.lms.model.Course;

public interface AdminActionType 
{
	public long getTypeId();
	public String getName(Locale locale);
	public String getHelpMessage(Locale locale);
	public String getPortletId();
	public boolean hasPermission(long userId);
	public String getIcon();
	public boolean showInCourse(Course course, long userId);
	public boolean showInEdition(Course course, long userId);
}
