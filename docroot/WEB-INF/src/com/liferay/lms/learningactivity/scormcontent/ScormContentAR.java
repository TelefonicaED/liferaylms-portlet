package com.liferay.lms.learningactivity.scormcontent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.portlet.RenderRequest;

import com.liferay.lms.model.SCORMContent;
import com.liferay.lms.service.SCORMContentLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetEntry;

public class ScormContentAR extends BaseScormAssetRenderer {

	
	@Override
	public boolean acceptsSelectSCO() throws DocumentException, IOException,
			PortalException, SystemException {
		return true;
	}

	@Override
	public String getManifestURL(RenderRequest renderRequest, AssetEntry asset)
			throws DocumentException, IOException, PortalException,
			SystemException {
		SCORMContent _scorm=SCORMContentLocalServiceUtil.getSCORMContent(asset.getClassPK());
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		String urlIndex=themeDisplay.getPortalURL()+renderRequest.getContextPath()+
				"/scorm/"+Long.toString(_scorm.getCompanyId())+"/"+Long.toString(_scorm.getGroupId())+"/"+_scorm.getUuid()+"/imsmanifest.xml";
        return urlIndex;
        
	}

	@Override
	public String getManifestURL(String scoshow, RenderRequest renderRequest,
			AssetEntry asset) throws DocumentException, IOException,
			PortalException, SystemException {
		
		SCORMContent _scorm=SCORMContentLocalServiceUtil.getSCORMContent(asset.getClassPK());

		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		String urlIndex=themeDisplay.getPortalURL()+renderRequest.getContextPath()+
				"/scorm/"+Long.toString(_scorm.getCompanyId())+"/"+Long.toString(_scorm.getGroupId())+"/"+_scorm.getUuid()+"/imsmanifest.xml?scoshow="+scoshow;
		return urlIndex;
	}

	@Override
	public Document getManifest(RenderRequest renderRequest, AssetEntry asset)
			throws DocumentException, IOException, PortalException,
			SystemException {
		SCORMContent _scorm=SCORMContentLocalServiceUtil.getSCORMContent(asset.getClassPK());

		String rutaDatos = SCORMContentLocalServiceUtil.getBaseDir();
		
		String urlIndex=rutaDatos+"/"+Long.toString(_scorm.getCompanyId())+"/"+Long.toString(_scorm.getGroupId())+"/"+_scorm.getUuid()+"/imsmanifest.xml";

		FileInputStream fis;
		try {
			fis = new FileInputStream(urlIndex);
		} catch (FileNotFoundException e) {
			
			throw new IOException( e);
		}

		Document manifest=SAXReaderUtil.read(fis);
		return manifest;
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return SCORMContent.class.getName();
	}

}
