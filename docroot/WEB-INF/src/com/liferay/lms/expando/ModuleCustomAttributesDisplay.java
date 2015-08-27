package com.liferay.lms.expando;

import com.liferay.lms.model.Module;
import com.liferay.portlet.expando.model.BaseCustomAttributesDisplay;

public class ModuleCustomAttributesDisplay extends BaseCustomAttributesDisplay {

	public static final String CLASS_NAME = Module.class.getName();
	
	public String getClassName() {

		return CLASS_NAME;
	}

}
