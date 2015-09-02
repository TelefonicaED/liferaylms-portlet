package com.liferay.lms.learningactivity.scormcontent;

import java.io.IOException;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portlet.asset.model.BaseAssetRenderer;

public abstract class BaseScormAssetRenderer implements ScormContentAsset 
{

	@Override
	public boolean acceptsSelectSCO() throws DocumentException, IOException,
			PortalException, SystemException {
		return false;
	}

}
