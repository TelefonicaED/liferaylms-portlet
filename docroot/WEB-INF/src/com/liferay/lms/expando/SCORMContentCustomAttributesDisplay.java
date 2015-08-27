package com.liferay.lms.expando;

import com.liferay.lms.model.SCORMContent;
import com.liferay.portlet.expando.model.BaseCustomAttributesDisplay;

public class SCORMContentCustomAttributesDisplay extends BaseCustomAttributesDisplay {

	public static final String CLASS_NAME = SCORMContent.class.getName();
	
	public String getClassName() {
		return CLASS_NAME;
	}

}
