package com.liferay.lms.expando;

import com.liferay.lms.model.Module;
import com.liferay.portal.model.Group;
import com.liferay.portlet.expando.model.BaseCustomAttributesDisplay;

public class GroupCustomAttributesDisplay extends BaseCustomAttributesDisplay {

	public static final String CLASS_NAME = Group.class.getName();
	
	public String getClassName() {

		return CLASS_NAME;
	}

}
