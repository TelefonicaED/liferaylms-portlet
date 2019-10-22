package com.liferay.lms.learningactivity.calificationtype;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;

public class CalificationTypeRegistry {
	
	protected static final String LMS_ACTIVITIES_LIST_PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	
	private static CalificationType[] _getCalificationTypes(){
		Properties properties = PropsUtil.getProperties("lms.calification.type", true);
		CalificationType[] calificationTypes = new CalificationType[properties.size()];
		int currentCalificationType = 0;
		for (Object key:properties.keySet()) {
			String type=properties.getProperty(key.toString());
			try {	
				CalificationType calificationType = (CalificationType)getPortletClassLoaderLMS().loadClass(type).newInstance();
				calificationTypes[currentCalificationType++]=calificationType;
			} catch (ClassNotFoundException e) {
				try {
					String [] context = ((String) key).split("\\.");
					if (Validator.isNotNull(context) && context.length == 2) {
						context[1] = LMS_ACTIVITIES_LIST_PORTLET_ID;
					}
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						PortletClassLoaderUtil.getClassLoader(context[1])).newInstance(), type, 
						PortletClassLoaderUtil.getClassLoader(context[1]));
					calificationTypes[currentCalificationType++]=new CalificationTypeClp(classLoaderProxy);
				} catch (Throwable throwable) {
				}
			} catch (ClassCastException e) {
				try {
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						getPortletClassLoader()).newInstance(), type, 
						getPortletClassLoader());
					calificationTypes[currentCalificationType++]=new CalificationTypeClp(classLoaderProxy);
				} catch (Throwable throwable) {
				}
			} catch (Throwable throwable) {
			}
		}
		
		if(properties.size()==currentCalificationType) {
			return calificationTypes;
		}
		else {
			return Arrays.copyOf(calificationTypes,currentCalificationType);
		}
	}
	
	private static ClassLoader _portletClassLoader;
	
	
	private static ClassLoader getPortletClassLoaderLMS() {
		if(_portletClassLoader==null) {
			_portletClassLoader=PortletClassLoaderUtil.getClassLoader(LMS_ACTIVITIES_LIST_PORTLET_ID);
		}
		return _portletClassLoader;
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
	
	public CalificationTypeRegistry() {
		_calificationTypes =  _calificationTypeThreadLocal.get();
		if((Validator.isNull(_calificationTypes))||
			(_calificationTypes.isEmpty())||
			(!(_calificationTypes.get(0) instanceof CalificationType))) {
				CalificationType[] calificationTypes = _getCalificationTypes();
			_calificationTypes=new UnmodifiableList<CalificationType>(Arrays.asList(calificationTypes));
			_calificationTypeThreadLocal.set(_calificationTypes);
			
		}
	}
		
	public CalificationType getCalificationType(long typeId) {
		for (CalificationType calificationType : _calificationTypes) {
			if(calificationType.getTypeId()==typeId){
				return calificationType;
			}
		}	
		return null;
	}

	public List<CalificationType> getCalificationTypes() {
		return _calificationTypes;
	}
	
	private List<CalificationType> _calificationTypes;
	
	private static ThreadLocal<List<CalificationType>>
		_calificationTypeThreadLocal =
			new AutoResetThreadLocal<List<CalificationType>>(
					CalificationTypeRegistry.class + "._calificationTypeThreadLocal");
}
