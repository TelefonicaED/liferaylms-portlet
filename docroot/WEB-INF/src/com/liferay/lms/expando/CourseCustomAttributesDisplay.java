package com.liferay.lms.expando;

import com.liferay.lms.model.Course;
import com.liferay.portlet.expando.model.BaseCustomAttributesDisplay;

public class CourseCustomAttributesDisplay extends BaseCustomAttributesDisplay {

	public static final String CLASS_NAME = Course.class.getName();
	
	public String getClassName() {
		return CLASS_NAME;
	}

}
