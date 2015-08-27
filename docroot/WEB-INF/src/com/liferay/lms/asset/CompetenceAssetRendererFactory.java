package com.liferay.lms.asset;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;

import com.liferay.lms.model.Competence;
import com.liferay.lms.service.CompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

public class CompetenceAssetRendererFactory extends BaseAssetRendererFactory
 {

	public static final String CLASS_NAME = Competence.class.getName();
	public static final String TYPE = "competence";

	@Override
	public String getClassName() {
	
		return CLASS_NAME;
	}

	public AssetRenderer getAssetRenderer(long classPK, int type)
	throws PortalException, SystemException {
	Competence competence = CompetenceLocalServiceUtil.getCompetence(classPK);
	
		return new CompetenceAssetRenderer(competence);
	}
	public PortletURL getURLAdd(
            LiferayPortletRequest liferayPortletRequest,
            LiferayPortletResponse liferayPortletResponse)
        {
		
        return null;
    }
	@Override
	public boolean hasPermission(PermissionChecker permissionChecker,
			long classPK, String actionId) throws Exception {
	
		return true;
	}

	@Override
	public String getType() {
		return TYPE;
	}


	



}
