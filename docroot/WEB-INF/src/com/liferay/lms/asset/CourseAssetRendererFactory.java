package com.liferay.lms.asset;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

public class CourseAssetRendererFactory extends BaseAssetRendererFactory
 {

	public static final String CLASS_NAME = Course.class.getName();
	public static final String TYPE = "course";
	private static final String PORTLET_ID =  PortalUtil.getJsSafePortletId("courseadmin"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	
	@Override
	public String getClassName() {
	
		return CLASS_NAME;
	}

	public AssetRenderer getAssetRenderer(long classPK, int type)
	throws PortalException, SystemException {
		Course course = CourseLocalServiceUtil.getCourse(classPK);
		return new CourseAssetRenderer(course);
	}
	public PortletURL getURLAdd(LiferayPortletRequest liferayPortletRequest, LiferayPortletResponse liferayPortletResponse){
		ThemeDisplay themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		try {
			if(!themeDisplay.getPermissionChecker().
					hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.coursemodel",themeDisplay.getScopeGroupId(),"ADD_COURSE")){
				return null;
			}
			
	  	  	PortletURL portletURL = PortletURLFactoryUtil.create(liferayPortletRequest,PORTLET_ID,getControlPanelPlid(themeDisplay),PortletRequest.RENDER_PHASE);
	  	  	portletURL.setParameter("mvcPath", "/html/courseadmin/editcourse.jsp");
			return portletURL;
			
		}
		catch(Throwable t) {
			return null;
		}

    }
	@Override
	public boolean hasPermission(PermissionChecker permissionChecker,
			long classPK, String actionId) throws Exception {
		Course course=CourseLocalServiceUtil.getCourse(classPK);
		return permissionChecker.hasPermission(course.getGroupId(), Course.class.getName(), classPK,actionId);
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
