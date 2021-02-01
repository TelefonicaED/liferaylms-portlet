package com.liferay.lms.asset;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import com.liferay.lms.model.Module;
import com.liferay.lms.service.CourseServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
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

		ThemeDisplay themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
		            WebKeys.THEME_DISPLAY);
		Layout layoutActivityViewer = ModuleAssetRendererFactory.getLayoutActivityViewer(themeDisplay.getLayout(), _module.getGroupId());
			
		if (layoutActivityViewer == null) {
			throw new NoSuchLayoutException();
		}
		 
		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(layoutActivityViewer.getPlid(), 
				 LmsConstant.LMS_EDITMODULE_PORTLET_ID, PortletRequest.RENDER_PHASE);
		portletURL.setWindowState(WindowState.NORMAL);
		portletURL.setParameter("actionEditingModule", StringPool.TRUE);
		portletURL.setParameter("actionEditingActivity", StringPool.FALSE);
		portletURL.setParameter("actionEditingDetails", StringPool.FALSE);
		portletURL.setParameter("actionCalifications", StringPool.FALSE);
		portletURL.setParameter("view", "editmodule");
		portletURL.setParameter("moduleId",Long.toString(_module.getModuleId()));
		portletURL.setParameter("actId","");
		portletURL.setParameter("resourcePrimKey",Long.toString(_module.getModuleId()));		
		portletURL.setParameter("editType","edit");		
		portletURL.setParameter("p_o_p_id",LmsConstant.ACTIVITY_VIEWER_PORTLET_ID);
	
		return portletURL;
	}
	
	@Override
	public String getURLViewInContext(LiferayPortletRequest liferayPortletRequest,
				LiferayPortletResponse liferayPortletResponse, String noSuchEntryRedirect) throws Exception {
			
		ThemeDisplay themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
		
		Layout layoutActivityViewer = ModuleAssetRendererFactory.getLayoutActivityViewer(themeDisplay.getLayout(), _module.getGroupId());
		
		if (layoutActivityViewer == null) {
			throw new NoSuchLayoutException();
		}
		
		LiferayPortletURL  gotoModuleURL = PortletURLFactoryUtil.create(liferayPortletRequest,
				PortalUtil.getJsSafePortletId(LmsConstant.ACTIVITY_VIEWER_PORTLET_ID), 
				layoutActivityViewer.getPlid(), PortletRequest.RENDER_PHASE);	
		gotoModuleURL.removePublicRenderParameter("actionEditingActivity");
		gotoModuleURL.removePublicRenderParameter("actionEditingModule");
		gotoModuleURL.removePublicRenderParameter("actionCalifications");
		gotoModuleURL.removePublicRenderParameter("actionEditingDetails");
		gotoModuleURL.removePublicRenderParameter("actId");
		gotoModuleURL.setWindowState(WindowState.NORMAL);
		gotoModuleURL.setParameter("moduleId", Long.toString(_module.getModuleId()));
		gotoModuleURL.setPlid(layoutActivityViewer.getPlid());
		gotoModuleURL.setPortletId(LmsConstant.ACTIVITY_VIEWER_PORTLET_ID);
		
		return gotoModuleURL.toString();
	}
	
	
	public PortletURL getURLViewInContext(long plid, LiferayPortletRequest liferayPortletRequest,
				LiferayPortletResponse liferayPortletResponse) throws Exception {
			
		ThemeDisplay themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
		
		Layout layoutActivityViewer = null;

		if(plid > 0){
			layoutActivityViewer = LayoutLocalServiceUtil.getLayout(plid);
		}else{
			layoutActivityViewer = ModuleAssetRendererFactory.getLayoutActivityViewer(themeDisplay.getLayout(), _module.getGroupId());
		}
		
		if (layoutActivityViewer == null) {
			throw new NoSuchLayoutException();
		}
		
		LiferayPortletURL  gotoModuleURL = PortletURLFactoryUtil.create(liferayPortletRequest,
				PortalUtil.getJsSafePortletId(LmsConstant.ACTIVITY_VIEWER_PORTLET_ID), 
				layoutActivityViewer.getPlid(), PortletRequest.RENDER_PHASE);	
		gotoModuleURL.removePublicRenderParameter("actionEditingActivity");
		gotoModuleURL.removePublicRenderParameter("actionEditingModule");
		gotoModuleURL.removePublicRenderParameter("actionCalifications");
		gotoModuleURL.removePublicRenderParameter("actionEditingDetails");
		gotoModuleURL.removePublicRenderParameter("actId");
		gotoModuleURL.setWindowState(WindowState.NORMAL);
		gotoModuleURL.setParameter("moduleId", Long.toString(_module.getModuleId()));
		gotoModuleURL.setPlid(layoutActivityViewer.getPlid());
		gotoModuleURL.setPortletId(LmsConstant.ACTIVITY_VIEWER_PORTLET_ID);
		
		return gotoModuleURL;
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
