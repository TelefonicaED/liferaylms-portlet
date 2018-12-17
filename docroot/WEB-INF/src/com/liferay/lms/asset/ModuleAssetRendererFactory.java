package com.liferay.lms.asset;

import com.liferay.lms.model.Module;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

public class ModuleAssetRendererFactory extends BaseAssetRendererFactory
 {

	public static final String CLASS_NAME = Module.class.getName();
	public static final String TYPE = "module";
	
	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	public AssetRenderer getAssetRenderer(long classPK, int type)
	throws PortalException, SystemException {
		Module module = ModuleLocalServiceUtil.getModule(classPK);
		return new ModuleAssetRenderer(module);
	}
	
	@Override
	public boolean hasPermission(PermissionChecker permissionChecker,
			long classPK, String actionId) throws Exception {
		Module module = ModuleLocalServiceUtil.getModule(classPK);
		return permissionChecker.hasPermission(module.getGroupId(), Module.class.getName(), classPK, actionId);
	}

	@Override
	public boolean isLinkable() {
		return true;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

}
