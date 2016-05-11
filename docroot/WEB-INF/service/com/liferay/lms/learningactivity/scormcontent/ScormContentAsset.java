package com.liferay.lms.learningactivity.scormcontent;

import java.io.IOException;

import javax.portlet.RenderRequest;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portlet.asset.model.AssetEntry;

public interface ScormContentAsset {
	public String getClassName();
	public boolean acceptsSelectSCO()  throws DocumentException,IOException, PortalException,SystemException;
	public String getManifestURL(RenderRequest renderRequest,AssetEntry asset)  throws DocumentException,IOException, PortalException,SystemException;
	public String getManifestURL(String scoshow,RenderRequest renderRequest,AssetEntry asset)  throws DocumentException,IOException, PortalException,SystemException;
	public Document getManifest(RenderRequest renderRequest,AssetEntry asset)  throws DocumentException,IOException, PortalException,SystemException;

}
