package com.liferay.lms.asset;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.BaseAssetRenderer;

public class CourseAssetRenderer extends BaseAssetRenderer {
	
	public final String COURSE_VIEW_EVALUATION="COURSE_VIEW_EVALUATION";
	protected static final String COURSE_ADMIN_PORTLET_ID =  PortalUtil.getJsSafePortletId("courseadmin"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	protected static final String EVALUATION_AVG_PORTLET_ID =  PortalUtil.getJsSafePortletId("evaluationAvg"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	
	private Course _course;

	
	public CourseAssetRenderer (Course course) throws SystemException,PortalException {
		_course = course;		
	}
	
	@Override
	public long getClassPK() {
		return _course.getCourseId();
	}

	@Override
	public long getGroupId() {
		return _course.getGroupId();
	}

	@Override
	public String getSummary(Locale locale){
		return _course.getDescription(locale);
	}

	@Override
	public String getTitle(Locale locale){
		return _course.getTitle(locale);
	}

	@Override
	public long getUserId() {
		return _course.getUserId();
	}
	
	@Override
	public String getUserName() {
		try {
			return UserLocalServiceUtil.getUser(_course.getUserId()).
					getFullName();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getUuid() {
		return _course.getUuid();
	}

	@Override
	public final String render(RenderRequest request, RenderResponse response, String template) throws Exception {
		request.setAttribute("course", _course);
		return "/html/asset/course/" + template + ".jsp";

	}

	@Override
	public final PortletURL getURLEdit(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception {
		if(GetterUtil.getBoolean(liferayPortletRequest.getAttribute(COURSE_VIEW_EVALUATION),false)) {
			@SuppressWarnings("unchecked")
			List<Layout> layouts = LayoutLocalServiceUtil.dynamicQuery(LayoutLocalServiceUtil.dynamicQuery().
					add(PropertyFactoryUtil.forName("privateLayout").eq(false)).
					add(PropertyFactoryUtil.forName("type").eq(LayoutConstants.TYPE_PORTLET)).
					add(PropertyFactoryUtil.forName("companyId").eq(_course.getCompanyId())).
					add(PropertyFactoryUtil.forName("groupId").eq(_course.getGroupCreatedId())).
					add(PropertyFactoryUtil.forName("friendlyURL").eq("/reto")), 0, 1);

			if(layouts.isEmpty()) {
				throw new NoSuchLayoutException();
			}
			else {
				Layout layout = layouts.get(0);
				if(((LayoutTypePortlet)layout.getLayoutType()).getPortletIds().contains(EVALUATION_AVG_PORTLET_ID)){
					PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(layout.getPlid(), EVALUATION_AVG_PORTLET_ID, PortletRequest.RENDER_PHASE);
					portletURL.setParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.TRUE);
					return portletURL;
				}
			}
		}
		
		ThemeDisplay themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);
  	  	PortletURL portletURL = PortletURLFactoryUtil.create(liferayPortletRequest,COURSE_ADMIN_PORTLET_ID,getControlPanelPlid(themeDisplay),PortletRequest.RENDER_PHASE);
  	  	portletURL.setParameter("mvcPath", "/html/courseadmin/editcourse.jsp");
  	  	portletURL.setParameter("courseId",Long.toString(_course.getCourseId()));
		return portletURL;
	}
	
	@Override
	public final PortletURL getURLView(LiferayPortletResponse liferayPortletResponse,
			WindowState windowState) throws Exception {
		Group courseGroup= GroupLocalServiceUtil.getGroup(_course.getGroupCreatedId());
		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(courseGroup.getDefaultPublicPlid(), StringPool.BLANK, PortletRequest.RENDER_PHASE);
		return portletURL;
	}
	
	@Override
	public final String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Group courseGroup= GroupLocalServiceUtil.getGroup(_course.getGroupCreatedId());
		return PortalUtil.getGroupFriendlyURL(courseGroup, false, themeDisplay);
	}

	@Override
	public String getViewInContextMessage() {
		return "show-detail-course";
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) throws PortalException, SystemException {	
		return ((!_course.isClosed())&&
				(permissionChecker.hasPermission(_course.getGroupId(),  Course.class.getName(),_course.getCourseId(),ActionKeys.UPDATE)));

	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
			throws PortalException, SystemException {
		return ((!_course.isClosed())&&
				(permissionChecker.hasPermission(_course.getGroupId(),  Course.class.getName(),_course.getCourseId(),ActionKeys.VIEW)));
	}

}
