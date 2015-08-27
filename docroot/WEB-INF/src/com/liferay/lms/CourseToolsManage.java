package com.liferay.lms;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class CourseToolsManage extends MVCPortlet 
{
	public void changeLayout(ActionRequest request, ActionResponse response) throws Exception
	{

			try {
				ThemeDisplay themeDisplay = (ThemeDisplay) request
						.getAttribute(WebKeys.THEME_DISPLAY);
				if (!themeDisplay.isSignedIn()) {
					return;
				}
				String layoutid = ParamUtil.getString(request, "layoutid");
				Layout ellayout = LayoutLocalServiceUtil.getLayout(Long
						.parseLong(layoutid));

				Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
				Role userRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.USER);
				boolean visible = ResourcePermissionLocalServiceUtil.hasResourcePermission(siteMemberRole.getCompanyId(), Layout.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL, layoutid, new long[] {siteMemberRole.getRoleId(), userRole.getRoleId()}, ActionKeys.VIEW);
				//if (ellayout.getHidden()) {
				if(!visible){	
					//ellayout.setHidden(false);
					
					ResourcePermissionLocalServiceUtil.setResourcePermissions(
							themeDisplay.getCompanyId(),
							Layout.class.getName(),
							ResourceConstants.SCOPE_INDIVIDUAL,
							layoutid,
							siteMemberRole.getRoleId(),
							new String[] { ActionKeys.VIEW });
					ResourcePermissionLocalServiceUtil.setResourcePermissions(
							themeDisplay.getCompanyId(),
							Layout.class.getName(),
							ResourceConstants.SCOPE_INDIVIDUAL,
							layoutid,
							userRole.getRoleId(),
							new String[] { ActionKeys.VIEW });
				} else {
					//ellayout.setHidden(true);
					ResourcePermissionLocalServiceUtil.removeResourcePermission(
							themeDisplay.getCompanyId(),
							Layout.class.getName(),
							ResourceConstants.SCOPE_INDIVIDUAL,
							layoutid,
							siteMemberRole.getRoleId(), ActionKeys.VIEW);
					ResourcePermissionLocalServiceUtil.removeResourcePermission(
							themeDisplay.getCompanyId(),
							Layout.class.getName(),
							ResourceConstants.SCOPE_INDIVIDUAL,
							layoutid,
							userRole.getRoleId(), ActionKeys.VIEW);
				}
				//LayoutLocalServiceUtil.updateLayout(ellayout);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//response.setRenderParameters(request.getParameterMap());			
			response.setRenderParameter("jspPage", "/html/coursetoolsmanage/reload.jsp");		
	} 

}
