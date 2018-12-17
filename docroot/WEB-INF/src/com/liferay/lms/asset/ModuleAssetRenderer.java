package com.liferay.lms.asset;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import com.liferay.lms.model.Module;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.BaseAssetRenderer;

public class ModuleAssetRenderer extends BaseAssetRenderer {

	private Module _module;
	
	public ModuleAssetRenderer (Module module) {
		_module = module;
	}
	
	@Override
	public long getClassPK() {
		return _module.getModuleId();
	}
	
	@Override
	public long getGroupId() {
		return _module.getGroupId();
	}
	
	@Override
	public String getSummary(Locale locale){
		return _module.getDescription(locale);
	}
	
	@Override
	public String getTitle(Locale locale) { 
		return _module.getTitle(locale);
	} 

	@Override
	public long getUserId() {
		return _module.getUserId();
	}
	
	@Override
	public String getUserName() {
		try {
			return UserLocalServiceUtil.getUser(_module.getUserId()).getFullName();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public String getUuid() {
		return _module.getUuid();
	}
	
	@Override
	public String render(RenderRequest request, RenderResponse response,
			String template) throws Exception {
			return null;
	}
	
	@Override
	public PortletURL getURLEdit(LiferayPortletRequest liferayPortletRequest,
				LiferayPortletResponse liferayPortletResponse) throws Exception {
		
		HttpServletRequest request = liferayPortletRequest.getHttpServletRequest();

		 ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
		            WebKeys.THEME_DISPLAY);
	
		return null;
	}
	
	@Override
	public String getURLViewInContext(LiferayPortletRequest liferayPortletRequest,
				LiferayPortletResponse liferayPortletResponse, String noSuchEntryRedirect) throws Exception {
			
				return null;
	}
		
	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
			throws PortalException, SystemException {
		return permissionChecker.hasPermission(_module.getGroupId(),  Module.class.getName(),_module.getModuleId(),ActionKeys.VIEW);
	}
	
	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
			throws PortalException, SystemException {
		return permissionChecker.hasPermission(_module.getGroupId(), Module.class.getName(), _module.getModuleId(), ActionKeys.UPDATE);
	}

}
