package com.liferay.lms.model;

import java.util.Locale;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

public interface CourseTypeFactory {
	public CourseTypeI getCourseType(Course course) throws PortalException, SystemException;
	public long getClassNameId();
	public String getClassName();
	public String getTitle(Locale locale);
	public String getDescription(Locale locale);
	public String getPortletId();
	public long[] getCalificationTypes();
	public long[] getCourseEvals();
	public long[] getInscriptionTypes();
	public long[] getLearningActivities();
	public long[] getTemplates();
	public long[] getEditionTemplates();
}