package com.liferay.lms.course.diploma;

import java.util.Iterator;
import java.util.Properties;

import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;


public class CourseDiplomaRegistry {
	
	protected static final String LMS_ACTIVITIES_LIST_PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	private static ClassLoader _portletClassLoader;
	
	public CourseDiploma getCourseDiploma() {
		
		CourseDiploma courseDiploma = null;
		Properties properties = PropsUtil.getProperties("lms.course.diploma.integration", true);
		Iterator<Object> keys = properties.keySet().iterator();
		while(courseDiploma==null && keys.hasNext()){
			Object key = keys.next();
			String classname=properties.getProperty(key.toString());
			try {	
				courseDiploma = (CourseDiploma)getPortletClassLoader().loadClass(classname).newInstance();
			} catch (ClassNotFoundException e) {
				String [] context = ((String) key).split("\\.");
				String portlet;
				if (Validator.isNotNull(context) && context.length == 2) {
					portlet = LMS_ACTIVITIES_LIST_PORTLET_ID;
				}else{
					portlet = context[1];
				}
				try {
					
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(classname, true, 
						PortletClassLoaderUtil.getClassLoader(portlet)).newInstance(), classname, 
						PortletClassLoaderUtil.getClassLoader(portlet));
					courseDiploma = new CourseDiplomaClp(classLoaderProxy);
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
			} catch (ClassCastException e) {
				try {
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(classname, true, 
						getPortletClassLoader()).newInstance(), classname, 
						getPortletClassLoader());
					courseDiploma = new CourseDiplomaClp(classLoaderProxy);
				} catch (Throwable throwable) {
				}
			}catch(Exception e){
				e.printStackTrace();
			}	
				
		}
		
		return courseDiploma;
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
