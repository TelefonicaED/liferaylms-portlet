package com.liferay.lms.asset;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.BaseAssetRenderer;

public abstract class LearningActivityBaseAssetRenderer extends BaseAssetRenderer {
	
	private static Log log = LogFactoryUtil.getLog(LearningActivityBaseAssetRenderer.class);
	
	public static final String ACTION_VIEW = "ACTION_VIEW";
	public static final String EDIT_DETAILS = "ACTIVITY_EDIT_DETAILS";
	public static final String TEMPLATE_JSP = "template_JSP";
	public static final String TEMPLATE_PORTLET_ID = "template_portlet_id";
	protected static final String LMS_ACTIVITIES_LIST_PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	protected static final String ACTIVITY_VIEWER_PORTLET_ID =  PortalUtil.getJsSafePortletId("activityViewer"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	public static final String LMS_EDITACTIVITY_PORTLET_ID =  PortalUtil.getJsSafePortletId("editactivity"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	
	
	private LearningActivity _learningactivity;
	private String _nameKey;
	private String _portletId;
	private Layout _layout;
	private boolean _editDetails = false;
	private boolean _isLmsExternalTemplates = false;
	private boolean _isRuntimePortlet = false;
	
	public LearningActivityBaseAssetRenderer (LearningActivity learningactivity, LearningActivityType learningActivityType, boolean isLmsExternalTemplates) throws SystemException,PortalException {
		_learningactivity = learningactivity;
		_nameKey = learningActivityType.getName();
		_portletId = learningActivityType.getPortletId();
		_editDetails = learningActivityType.hasEditDetails();
		_isLmsExternalTemplates = isLmsExternalTemplates;
		
		ThreadLocalCache<Layout> threadLocalCache =
				ThreadLocalCacheManager.getThreadLocalCache(
					Lifecycle.REQUEST, LearningActivityBaseAssetRenderer.class.getName());
		
		String layoutKey = _learningactivity.getCompanyId()+StringPool.SLASH+_learningactivity.getGroupId();
		_layout  = threadLocalCache.get(layoutKey);
		
		if(Validator.isNull(_layout)) {
			@SuppressWarnings("unchecked")
			List<Layout> layouts = LayoutLocalServiceUtil.dynamicQuery(LayoutLocalServiceUtil.dynamicQuery().
					add(PropertyFactoryUtil.forName("privateLayout").eq(false)).
					add(PropertyFactoryUtil.forName("type").eq(LayoutConstants.TYPE_PORTLET)).
					add(PropertyFactoryUtil.forName("companyId").eq(_learningactivity.getCompanyId())).
					add(PropertyFactoryUtil.forName("groupId").eq(_learningactivity.getGroupId())).
					add(PropertyFactoryUtil.forName("friendlyURL").eq("/reto")), 0, 1);
	
			if(layouts.isEmpty()) {
				throw new NoSuchLayoutException();			
			}
			
			_layout = layouts.get(0);
			threadLocalCache.put(layoutKey, _layout);
		}
		
		if(!((LayoutTypePortlet)_layout.getLayoutType()).getPortletIds().contains(_portletId)){
			_isRuntimePortlet = true;
		}
		
	}
	
	@Override
	public long getClassPK() {
		return _learningactivity.getActId();
	}

	@Override
	public long getGroupId() {
		return _learningactivity.getGroupId();
	}

	@Override
	public String getSummary(Locale locale){
		return _learningactivity.getDescription(locale);
	}

	@Override
	public String getTitle(Locale locale){
		return _learningactivity.getTitle(locale);
	}

	@Override
	public long getUserId() {
		return _learningactivity.getUserId();
	}
	
	@Override
	public String getUserName() {
		return _learningactivity.getUserName();
	}

	@Override
	public String getUuid() {
		return _learningactivity.getUuid();
	}

	protected String getTemplatePath(String template) {
		return "/html/asset/";
	}

	@Override
	public final String render(RenderRequest request, RenderResponse response, String template) throws Exception {

		if(TEMPLATE_FULL_CONTENT.equals(template)) {
			request.setAttribute("learningactivity", _learningactivity);
		}

	    String templateJSP =  getTemplatePath(template) + template + ".jsp";
	    if(_isLmsExternalTemplates) {
	    	request.setAttribute(TEMPLATE_PORTLET_ID, _portletId);
	    	request.setAttribute(TEMPLATE_JSP, templateJSP);
	    	return "/html/asset/externalTemplate.jsp";
	    }

		return templateJSP;
	}

	private void prepareRuntimePortlet(PortletURL portletURL)
			throws SystemException, PortalException {
		if(_isRuntimePortlet){

			portletURL.setParameter("p_o_p_id",ACTIVITY_VIEWER_PORTLET_ID);

			PortletPreferencesFactoryUtil.getLayoutPortletSetup(_layout, _portletId);
			String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(_layout.getPlid(), _portletId);
			String portletName = _portletId;

			int warSeparatorIndex = portletName.indexOf(PortletConstants.WAR_SEPARATOR);
			if (warSeparatorIndex != -1) {
				portletName = portletName.substring(0, warSeparatorIndex);
			}

			if ((ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
					_learningactivity.getCompanyId(), portletName,
					ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey) == 0)&&
				(ResourceActionLocalServiceUtil.fetchResourceAction(portletName, ACTION_VIEW)!=null)) {
	        	Role siteMember = RoleLocalServiceUtil.getRole(_learningactivity.getCompanyId(),RoleConstants.SITE_MEMBER);
	        	ResourcePermissionLocalServiceUtil.setResourcePermissions(_learningactivity.getCompanyId(), portletName, ResourceConstants.SCOPE_INDIVIDUAL, 
        				resourcePrimKey,siteMember.getRoleId(), new String[]{ACTION_VIEW});
			}

		}
	}
	
	protected PortletURL getURLEditDetails(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception{
		return null;
	}
	
	@Override
	public final PortletURL getURLEdit(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception {
		PortletURL portletURL = null;
		
		if((_editDetails)&&(GetterUtil.getBoolean(liferayPortletRequest.getAttribute(EDIT_DETAILS)))) {
			portletURL = getURLEditDetails(liferayPortletRequest, liferayPortletResponse);
			if(Validator.isNotNull(portletURL)) {
				prepareRuntimePortlet(portletURL);
				return portletURL;
			}
		}
		ThemeDisplay themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
		
		if(_learningactivity.getModuleId()==0){
			portletURL= 
	    			  PortletURLFactoryUtil.create(liferayPortletRequest,"lmsactivitieslist_WAR_liferaylmsportlet",getControlPanelPlid(themeDisplay), PortletRequest.RENDER_PHASE);
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}else{
			portletURL = PortletURLFactoryUtil.create(liferayPortletRequest,_portletId,getControlPanelPlid(themeDisplay),PortletRequest.RENDER_PHASE);
		}
		
		portletURL.setParameter("editing", StringPool.TRUE);
		portletURL.setParameter("resId",Long.toString( _learningactivity.getActId()));
		portletURL.setParameter("resModuleId",Long.toString( _learningactivity.getModuleId())); 
	    return portletURL;
	}

	
	protected String getMvcPathView(long userId, LiferayPortletResponse liferayPortletResponse, WindowState windowState) throws Exception {
		return StringPool.BLANK;
	}
	
	
	@Override
	public final PortletURL getURLView(LiferayPortletResponse liferayPortletResponse,
			WindowState windowState) throws Exception {
		
		String portletId;
		
		if(_isRuntimePortlet){
			portletId = ACTIVITY_VIEWER_PORTLET_ID;
		}else{
			portletId = _portletId;
		}
		
		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(_layout.getPlid(),portletId, PortletRequest.RENDER_PHASE);
		portletURL.setWindowState(windowState);
		portletURL.setParameter("actId",Long.toString( _learningactivity.getActId()));
		portletURL.setParameter("moduleId",Long.toString( _learningactivity.getModuleId()));
		portletURL.setParameter("actionEditingActivity", StringPool.FALSE);
		portletURL.setParameter("actionEditingDetails", StringPool.FALSE);
		portletURL.setParameter("actionEditingModule", StringPool.FALSE);
		portletURL.setParameter("actionCalifications", StringPool.FALSE);
		portletURL.setParameter("activityStarted", StringPool.TRUE);
		portletURL.setParameter("view", StringPool.BLANK);
		
		long userId = PrincipalThreadLocal.getUserId();
		
		if(Validator.isNotNull(userId)) {
			String mvcPath = getMvcPathView(userId,liferayPortletResponse,windowState);
			if(Validator.isNotNull(mvcPath)){
				portletURL.setParameter("mvcPath",mvcPath);
			}
		}
		
		prepareRuntimePortlet(portletURL);
		
		log.debug("portletURL::"+portletURL.toString());
		
		return portletURL;
	}
	
	
	@Override
	public final String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(_layout.getPlid(), _portletId, PortletRequest.RENDER_PHASE);
		portletURL.setParameter("actId",Long.toString( _learningactivity.getActId()));
		portletURL.setParameter("moduleId",Long.toString( _learningactivity.getModuleId()));
		portletURL.setParameter("actionEditingActivity", StringPool.FALSE);
		portletURL.setParameter("actionEditingDetails", StringPool.FALSE);
		portletURL.setParameter("actionEditingModule", StringPool.FALSE);
		portletURL.setParameter("actionCalifications", StringPool.FALSE);
		portletURL.setParameter("activityStarted", StringPool.TRUE);	
		portletURL.setParameter("view", StringPool.BLANK);
		
		String mvcPath = getMvcPathView(themeDisplay.getUserId(),liferayPortletResponse,liferayPortletRequest.getWindowState());
		if(Validator.isNotNull(mvcPath)){
			portletURL.setParameter("mvcPath",mvcPath);
		}
		
		prepareRuntimePortlet(portletURL);
		return portletURL.toString();
	}

	@Override
	public String getViewInContextMessage() {
		return _nameKey;
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) throws PortalException, SystemException {	
		try {
			return LearningActivityLocalServiceUtil.canBeEdited(_learningactivity, permissionChecker);
		} catch (PortalException e) {
			throw e;
		} catch (SystemException e) {
			throw e;
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}
	
	
	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
			throws PortalException, SystemException {
		try {
			return LearningActivityLocalServiceUtil.canBeView(_learningactivity, permissionChecker);
		} catch (PortalException e) {
			throw e;
		} catch (SystemException e) {
			throw e;
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * @return the _learningactivity
	 */
	protected final LearningActivity getLearningactivity() {
		return _learningactivity;
	}

	/**
	 * @return the _portletId
	 */
	protected final String getPortletId() {
		return _portletId;
	}

	/**
	 * @return the _layout
	 */
	protected final Layout getLayout() {
		return _layout;
	}
	
}
