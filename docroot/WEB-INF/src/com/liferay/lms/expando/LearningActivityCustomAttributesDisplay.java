package com.liferay.lms.expando;

import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.expando.model.BaseCustomAttributesDisplay;

public class LearningActivityCustomAttributesDisplay extends BaseCustomAttributesDisplay {

	public static final String CLASS_NAME = LearningActivity.class.getName();
	
	public String getClassName() {

		return CLASS_NAME;
	}

}
