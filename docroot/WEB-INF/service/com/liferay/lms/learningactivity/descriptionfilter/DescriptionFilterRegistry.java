package com.liferay.lms.learningactivity.descriptionfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;

public class DescriptionFilterRegistry {
	private static ClassLoader _portletClassLoader;
	protected static final String LMS_ACTIVITIES_LIST_PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());	
	public static List<DescriptionFilter> descriptionFilters;
	public static List<DescriptionFilter> _getDescriptionFilters()
    {
    	if(descriptionFilters!=null)
    	{
    		return descriptionFilters;
    	}
    	Properties properties = PropsUtil.getProperties("lms.learningactivity.descriptionfilter", true);
    	descriptionFilters=new ArrayList<DescriptionFilter>();
    	for (Object key:properties.keySet()) 
		{
			String type=properties.getProperty(key.toString());
			try {	
				DescriptionFilter descriptionFilter = (DescriptionFilter)getPortletClassLoader().loadClass(type).newInstance();
				descriptionFilters.add( descriptionFilter);
			} catch (ClassNotFoundException e) {
				try {
					String [] context = ((String) key).split("\\.");
					if (Validator.isNotNull(context) && context.length == 1) {
						context[1] = LMS_ACTIVITIES_LIST_PORTLET_ID;
					}
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						PortletClassLoaderUtil.getClassLoader(context[1])).newInstance(), type, 
						PortletClassLoaderUtil.getClassLoader(context[1]));
					DescriptionFilter descriptionFilter=new DescriptionFilterClp(classLoaderProxy);
					descriptionFilters.add( descriptionFilter);
					
				} catch (Throwable throwable) {
				}
			} catch (ClassCastException e) {
				try {
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						getPortletClassLoader()).newInstance(), type, 
						getPortletClassLoader());
					DescriptionFilter descriptionFilter=new DescriptionFilterClp(classLoaderProxy);
					descriptionFilters.add( descriptionFilter);
					
				} catch (Throwable throwable) {
				}
			} catch (Throwable throwable) {
			}
		}
    	return descriptionFilters;
		
    }
    public static String filter(String description)
    {
    	String desc=description;
    	List<DescriptionFilter> descriptionFilters=_getDescriptionFilters();
    	for(DescriptionFilter descriptionFilter:descriptionFilters)
    	{
    		desc=descriptionFilter.filter(desc);
  
    	}
    	return desc;
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
