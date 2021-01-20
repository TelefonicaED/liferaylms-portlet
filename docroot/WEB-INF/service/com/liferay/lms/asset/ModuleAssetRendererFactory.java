package com.liferay.lms.asset;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import com.liferay.lms.model.Module;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

public class ModuleAssetRendererFactory extends BaseAssetRendererFactory {

	public static final String CLASS_NAME = Module.class.getName();
	public static final String TYPE = "module";
	
	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public AssetRenderer getAssetRenderer(long classPK, int type)
			throws PortalException, SystemException {
		Module module = ModuleLocalServiceUtil.getModule(classPK);
		return new ModuleAssetRenderer(module);
	}
	
	@Override
	public AssetRenderer getAssetRenderer(long classPK)
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
	
	public PortletURL getURLCreateModule(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, ThemeDisplay themeDisplay) throws Exception {
		
		Layout layout = getLayoutActivityViewer(themeDisplay.getLayout(), themeDisplay.getScopeGroupId());			
		
		if (layout == null) {
			throw new NoSuchLayoutException();
		}
		
		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(layout.getPlid(), LmsConstant.LMS_EDITMODULE_PORTLET_ID, PortletRequest.RENDER_PHASE);
		portletURL.setWindowState(WindowState.NORMAL);
		portletURL.setParameter("actionEditingModule", StringPool.TRUE);
		portletURL.setParameter("actionEditingActivity", StringPool.FALSE);
		portletURL.setParameter("actionEditingDetails", StringPool.FALSE);
		portletURL.setParameter("actionCalifications", StringPool.FALSE);
		portletURL.setParameter("view", "editmodule");
		portletURL.setParameter("moduleId",Long.toString(0));
		portletURL.setParameter("actId","");
		portletURL.setParameter("resourcePrimKey",Long.toString(0));		
		portletURL.setParameter("editType","add");		
		portletURL.setParameter("p_o_p_id",LmsConstant.ACTIVITY_VIEWER_PORTLET_ID);
		
		//log.debug(" getURLCreateModule: "+portletURL);
		
		return portletURL;		
	}
	
	public static Layout getLayoutActivityViewer(Layout actualLayout, long groupId) throws PortalException, SystemException{
		Layout layoutActivityViewer = null;
		
		if(groupId == actualLayout.getGroupId() && actualLayout.getTypeSettings().contains(LmsConstant.ACTIVITY_VIEWER_PORTLET_ID)){
			layoutActivityViewer = actualLayout;
		}
		
		if(layoutActivityViewer == null){
			long plid = CourseServiceUtil.getPlidActivityViewer(groupId);
			if(plid > 0){
				layoutActivityViewer = LayoutLocalServiceUtil.getLayout(plid);
			}
		}
		
		return layoutActivityViewer;
	}

}
