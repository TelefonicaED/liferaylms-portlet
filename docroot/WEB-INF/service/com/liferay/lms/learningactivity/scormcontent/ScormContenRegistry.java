package com.liferay.lms.learningactivity.scormcontent;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;

public class ScormContenRegistry {
	private static ClassLoader _portletClassLoader;
	protected static final String LMS_ACTIVITIES_LIST_PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());	
	static Map<String,ScormContentAsset> scormContentAssets;
	private static Map<String,ScormContentAsset> _getScormCourseAssets(){
		if(true)
		{
			Properties properties = PropsUtil.getProperties("lms.learningactivity.scormasset", true);
			scormContentAssets = new HashMap<String, ScormContentAsset>();
			for (Object key:properties.keySet()) {
				String type=properties.getProperty(key.toString());
				try {	
					ScormContentAsset scormCourseAsset = (ScormContentAsset)getPortletClassLoader().loadClass(type).newInstance();
					scormContentAssets.put(scormCourseAsset.getClassName(), scormCourseAsset);
				} catch (ClassNotFoundException e) {
					try {
						String [] context = ((String) key).split("\\.");
						if (Validator.isNotNull(context) && context.length == 1) {
							context[1] = LMS_ACTIVITIES_LIST_PORTLET_ID;
						}
						ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
							PortletClassLoaderUtil.getClassLoader(context[1])).newInstance(), type, 
							PortletClassLoaderUtil.getClassLoader(context[1]));
						ScormContentAsset scormCourseAsset=new ScormContentAssetClp(classLoaderProxy);
						scormContentAssets.put(scormCourseAsset.getClassName(), scormCourseAsset);
					} catch (Throwable throwable) {
					}
				} catch (ClassCastException e) {
					try {
						ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
							getPortletClassLoader()).newInstance(), type, 
							getPortletClassLoader());
						ScormContentAsset scormCourseAsset=new ScormContentAssetClp(classLoaderProxy);
						scormContentAssets.put(scormCourseAsset.getClassName(), scormCourseAsset);
					} catch (Throwable throwable) {
					}
				} catch (Throwable throwable) {
				}
			}
		}
		return scormContentAssets;
		
	}
	public static ScormContentAsset getScormContentAsset(String type) {
		return _getScormCourseAssets().get(type);
		}	
		
	private static ClassLoader getPortletClassLoader() {
		if(_portletClassLoader==null) {
			ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
			if(currentClassLoader.equals(PortalClassLoaderUtil.getClassLoader())) {
				_portletClassLoader=PortletClassLoaderUtil.getClassLoader(LMS_ACTIVITIES_LIST_PORTLET_ID);
			}
			else {
				_portletClassLoader=currentClassLoader;
			}
		}
		return _portletClassLoader;
	}

}
