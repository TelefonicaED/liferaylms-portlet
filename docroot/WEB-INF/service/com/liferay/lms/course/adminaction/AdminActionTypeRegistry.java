package com.liferay.lms.course.adminaction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;

public class AdminActionTypeRegistry {
	
	protected static final String LMS_ACTIVITIES_LIST_PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	private static Log log = LogFactoryUtil.getLog(AdminActionTypeRegistry.class);
	
	private static AdminActionType[] _getAdminActionTypes(){
		Properties properties = PropsUtil.getProperties("lms.admin.action.type", true);
		AdminActionType[] adminActionTypes = new AdminActionType[properties.size()];
		int currentAdminActionType = 0;
		log.debug("PROPERTIES "+properties.size());
		for (Object key:properties.keySet()) {
			String type=properties.getProperty(key.toString());
			log.debug("----TYPE "+type);
			try {	
				AdminActionType actionType = (AdminActionType)getPortletClassLoader().loadClass(type).newInstance();
				adminActionTypes[currentAdminActionType++]=actionType;
			} catch (ClassNotFoundException e) {
				try {
					String [] context = ((String) key).split("\\.");
					if (Validator.isNotNull(context) && context.length == 2) {
						context[1] = LMS_ACTIVITIES_LIST_PORTLET_ID;
					}
					log.debug("**********************CONTEXTTT "+context[1]);
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						PortletClassLoaderUtil.getClassLoader(context[1])).newInstance(), type, 
						PortletClassLoaderUtil.getClassLoader(context[1]));
					adminActionTypes[currentAdminActionType++]=new AdminActionTypeClp(classLoaderProxy);
					
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
			} catch (ClassCastException e) {
				e.printStackTrace();
				try {
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						getPortletClassLoader()).newInstance(), type, 
						getPortletClassLoader());
					adminActionTypes[currentAdminActionType++]=new AdminActionTypeClp(classLoaderProxy);
				} catch (Throwable throwable) {
				}
			} catch (Throwable throwable) {
			}
		}
		
		AdminActionType[] actions = null;
		
		if(properties.size()==currentAdminActionType) {
			actions = adminActionTypes;
		}
		else {
			actions = Arrays.copyOf(adminActionTypes,currentAdminActionType);
		}
		
		Arrays.sort(actions, new Comparator<AdminActionType>() {
		    @Override
		    public int compare(AdminActionType o1, AdminActionType o2) {
		        return ((Long) o2.getTypeId()).compareTo(o1.getTypeId());
		    }
		});
		
		return actions;
	}
	
	private static ClassLoader _portletClassLoader;
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
	
	public AdminActionTypeRegistry() {
		_actionTypes =  _adminActionTypeThreadLocal.get();
		log.debug("ACTIONS "+_actionTypes);
		if(Validator.isNull(_actionTypes)|| _actionTypes.isEmpty()|| !(_actionTypes.get(0) instanceof AdminActionType)) {
			AdminActionType[] actionTypes = _getAdminActionTypes();
			_actionTypes=new UnmodifiableList<AdminActionType>(Arrays.asList(actionTypes));
			_adminActionTypeThreadLocal.set(_actionTypes);
			
		}
	}
	
	public void refrehTypes(){
		_adminActionTypeThreadLocal.set(null);
	}

		
	public AdminActionType getAdminActionType(long typeId) {
		for (AdminActionType actionType : _actionTypes) {
			if(actionType.getTypeId()==typeId){
				return actionType;
			}
		}	
		return null;
	}

	public List<AdminActionType> getAdminActionTypes() {
		return _actionTypes;
	}
	
	private List<AdminActionType> _actionTypes;
	
	private static ThreadLocal<List<AdminActionType>>
		_adminActionTypeThreadLocal =
			new AutoResetThreadLocal<List<AdminActionType>>(
					AdminActionTypeRegistry.class + "._adminActionTypeThreadLocal");
}
