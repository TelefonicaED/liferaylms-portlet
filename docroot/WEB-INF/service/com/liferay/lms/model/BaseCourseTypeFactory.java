package com.liferay.lms.model;

import java.util.Locale;

import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;

public abstract class BaseCourseTypeFactory implements CourseTypeFactory{
	
	public static final String COURSE_TYPE_PORTLET_ID = PortalUtil.getJsSafePortletId("course-type-admin"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	
	
	@Override
	final public String getTitle(Locale locale) {

		String key = getClassName();

		String value = LanguageUtil.get(locale, key, null);

		if (value == null) {
			value = getClassName();
		}

		return value;
	}
	
	@Override
	public String getDescription(Locale locale) {
		return "";
	}
	
	@Override
	public String getPortletId() {
		return COURSE_TYPE_PORTLET_ID;
	}
	
	@Override
	final public long getClassNameId(){
		return PortalUtil.getClassNameId(getClassName());
	}
	
	public long[] getCalificationTypes(){
		return null;
	}
	
	public long[] getCourseEvals(){
		return null;
	}
	public long[] getInscriptionTypes(){
		return null;
	}
	public long[] getLearningActivities(){
		return null;
	}
	public long[] getTemplates(){
		return null;
	}
	public long[] getEditionTemplates(){
		return null;
	}
}
