package com.liferay.lms.asset;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

public class LearningActivityAssetRendererFactory extends BaseAssetRendererFactory
 {
	private static final String LEARNING_ACTIVITY_EXEC_ACTIVITY = LearningActivityAssetRendererFactory.class.getName()+"_execActivity";
	public static final String CLASS_NAME = LearningActivity.class.getName();
	public static final String TYPE = "learningactivity";
	private static final String PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	private volatile Method getGroupIds;
	
	public LearningActivityAssetRendererFactory() throws SystemException {
		try {
 			Class<?> assetPublisherUtilClass = PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.assetpublisher.util.AssetPublisherUtil");
 			getGroupIds = assetPublisherUtilClass.getMethod("getGroupIds", PortletPreferences.class, Long.TYPE, Layout.class);
		} catch (Throwable e) {
			throw new SystemException(e);
		}
	}
	
	public long[] getGroupIds(
			PortletPreferences portletPreferences, long scopeGroupId,
			Layout layout) throws SystemException{
		try {
			return (long[]) getGroupIds.invoke(null, portletPreferences, scopeGroupId, layout);
		} catch (Throwable e) {
			throw new SystemException(e);
		} 
		
	}

	@Override
	public Map<Long, String> getClassTypes(long[] groupId, Locale locale)
			throws Exception {

		Map<Long, String> classTypes = new LinkedHashMap<Long, String>();
		if(CourseLocalServiceUtil.dynamicQueryCount(CourseLocalServiceUtil.dynamicQuery().
				add(PropertyFactoryUtil.forName("groupCreatedId").in(ArrayUtil.toArray(groupId))))>0){
			LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
			ResourceBundle resourceBundle = PortletBagPool.get(getPortletId()).getResourceBundle(locale);		
			long[] invisibleTypes = StringUtil.split(PropsUtil.get("lms.learningactivity.invisibles"), StringPool.COMMA,-1L);
			for(LearningActivityType learningActivityType:learningActivityTypeRegistry.getLearningActivityTypesForCreating()){
				if(learningActivityType != null && !ArrayUtil.contains(invisibleTypes, learningActivityType.getTypeId())){
					String learningActivityTypeName = learningActivityTypeRegistry.getLearningActivityType(learningActivityType.getTypeId()).getName();
					classTypes.put(learningActivityType.getTypeId(), (resourceBundle.containsKey(learningActivityTypeName)?
							resourceBundle.getString(learningActivityTypeName):learningActivityTypeName));
				}
			}	
		}
		return classTypes;
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}
	
	private long getPlid(long courseId,ThemeDisplay themeDisplay) throws SystemException{

		if((Validator.isNull(courseId))&&
				(themeDisplay.getLayout().getFriendlyURL().equals("/reto"))) {
			return themeDisplay.getLayout().getPlid();
		}
			
		Course course = null;

		if(Validator.isNotNull(courseId)) {
			course = CourseLocalServiceUtil.fetchCourse(courseId);
		}

		if(Validator.isNull(course)) {
			course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		}

		if(Validator.isNull(course)) {
			return GetterUtil.DEFAULT_LONG;
		}

		@SuppressWarnings("unchecked")
		List<Layout> layouts = LayoutLocalServiceUtil.dynamicQuery(LayoutLocalServiceUtil.dynamicQuery().
				add(PropertyFactoryUtil.forName("privateLayout").eq(false)).
				add(PropertyFactoryUtil.forName("companyId").eq(course.getCompanyId())).
				add(PropertyFactoryUtil.forName("groupId").eq(course.getGroupCreatedId())).
				add(PropertyFactoryUtil.forName("friendlyURL").eq("/reto")),0,1);

		if(layouts.isEmpty()) {
			return GetterUtil.DEFAULT_LONG;
		}
		else {
			return layouts.get(0).getPlid();
		}	
	}

	public AssetRenderer getAssetRenderer(long classPK, int type)throws PortalException, SystemException {
		LearningActivity learningactivity = LearningActivityLocalServiceUtil.getLearningActivity(classPK);
		LearningActivityType learningActivityType=new LearningActivityTypeRegistry().getLearningActivityType(learningactivity.getTypeId());
		return learningActivityType.getAssetRenderer(learningactivity);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PortletURL getURLAdd(LiferayPortletRequest liferayPortletRequest, LiferayPortletResponse liferayPortletResponse) {
		
		try {
			List<Course> courses = null;
			ThemeDisplay themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);
			Object typeAttribute = liferayPortletRequest.getAttribute(WebKeys.ASSET_RENDERER_FACTORY_CLASS_TYPE_ID);
			long type = GetterUtil.getLong(typeAttribute);
			long courseId = GetterUtil.getLong(liferayPortletRequest.getAttribute("courseId"));

			if((type==GetterUtil.DEFAULT_LONG)&&
			   (PortletKeys.ASSET_PUBLISHER.equals(themeDisplay.getPortletDisplay().getPortletName()))){
				if(GetterUtil.getBoolean(liferayPortletRequest.getAttribute(LEARNING_ACTIVITY_EXEC_ACTIVITY), false)){
					liferayPortletRequest.removeAttribute(LEARNING_ACTIVITY_EXEC_ACTIVITY);
				}
				else{
					typeAttribute=null;
					liferayPortletRequest.setAttribute(LEARNING_ACTIVITY_EXEC_ACTIVITY,StringPool.TRUE);
				}
			}

			if(Validator.isNull(courseId)) {
				courses = CourseLocalServiceUtil.dynamicQuery(
						CourseLocalServiceUtil.dynamicQuery().
							add(PropertyFactoryUtil.forName("groupCreatedId").
								in(ArrayUtil.toArray(
									getGroupIds(themeDisplay.getPortletDisplay().getPortletSetup(), 
												themeDisplay.getScopeGroupId(), themeDisplay.getLayout())))));
			}
			else {
				courses = Arrays.asList(CourseLocalServiceUtil.getCourse(courseId));
			}
			
			List<Course> filterCourses = new ArrayList<Course>(courses.size());
			for (Course course : courses) {
				if((themeDisplay.getPermissionChecker().hasPermission(course.getGroupCreatedId(),
						"com.liferay.lms.model", course.getGroupCreatedId(), "ADD_MODULE"))&& 
				   ((typeAttribute==null)||(ArrayUtil.contains(StringUtil.split(LmsPrefsLocalServiceUtil.getLmsPrefsIni(course.getCompanyId()).getActivities(), 
						StringPool.COMMA, GetterUtil.DEFAULT_LONG), type)))){
					filterCourses.add(course);
				}
			}
			
			if(filterCourses.isEmpty()) {
				if(themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(),
						"com.liferay.lms.learningactivitymodel", themeDisplay.getScopeGroupId(), "ADD_ACTIVITY"))
				{
				 PortletURL portletURL= 
		    			  PortletURLFactoryUtil.create(liferayPortletRequest,"lmsactivitieslist_WAR_liferaylmsportlet",getControlPanelPlid(themeDisplay), PortletRequest.RENDER_PHASE);
				 portletURL.setParameter("mvcPath", "/html/lmsactivitieslist/newactivity.jsp");
				 portletURL.setParameter("resModuleId", "0");
				 return portletURL;
				}
				return null;
			}
			else
			{
				long plid = getPlid(filterCourses.get(0).getCourseId(),themeDisplay);
				
				if(Validator.isNull(plid)) {
					return null;
				}			
	
				long resModuleId = GetterUtil.getLong(liferayPortletRequest.getAttribute("resModuleId"));
		  	  	PortletURL portletURL = PortletURLFactoryUtil.create(liferayPortletRequest,PORTLET_ID,plid,PortletRequest.RENDER_PHASE);
	
		  	  	if(filterCourses.size()>1) {
		  	  		portletURL.setParameter("mvcPath", "/html/lmsactivitieslist/selectCourse.jsp");
		  	  		portletURL.setParameter("assetRendererId",themeDisplay.getPortletDisplay().getId());
		  	  		portletURL.setParameter("assetRendererPlid",Long.toString(themeDisplay.getPlid()));
		  	  		if(Validator.isNotNull(typeAttribute)){
		  	  			portletURL.setParameter("type", Long.toString(type));
		  	  		}
		  	  	}
		  	  	else if(typeAttribute==null) {
					portletURL.setParameter("mvcPath", "/html/lmsactivitieslist/newactivity.jsp");
				}
				else {
					portletURL.setParameter("mvcPath", "/html/editactivity/editactivity.jsp");
					portletURL.setParameter("type", Long.toString(type));
				}
	
				if(Validator.isNotNull(resModuleId)) {
					portletURL.setParameter("resModuleId", Long.toString(resModuleId));
				}
	
				return portletURL;
			}
		}
		catch(Throwable t) {
			return null;
		}
    }
	
	@Override
	public boolean hasPermission(PermissionChecker permissionChecker,
			long classPK, String actionId) throws Exception {
		LearningActivity learningActivity = 
				LearningActivityLocalServiceUtil.getLearningActivity(classPK);
		return permissionChecker.
				hasPermission(learningActivity.getGroupId(),LearningActivity.class.getName(),classPK,actionId);

	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean isSelectable() {
		return true;
	}
	@Override
	public boolean isLinkable() {
		return true;
	}
	



}
