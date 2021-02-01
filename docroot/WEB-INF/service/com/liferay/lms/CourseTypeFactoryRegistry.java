package com.liferay.lms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.liferay.lms.model.BaseCourseTypeFactory;
import com.liferay.lms.model.CourseTypeFactory;
import com.liferay.lms.model.CourseTypeFactoryClp;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

public class CourseTypeFactoryRegistry {
	
	private static Log log = LogFactoryUtil.getLog(CourseTypeFactoryRegistry.class);
	private static HashMap<Long,CourseTypeFactory> _migrationTypeFactories = null;
		
	private static HashMap<Long,CourseTypeFactory> _getCourseTypeFactories(){
		Properties properties = PropsUtil.getProperties("lms.course.type.class.name", true);
		HashMap<Long, CourseTypeFactory> migrationTypeFactories = new HashMap<Long, CourseTypeFactory>();
		String className = null;
		CourseTypeFactory migrationTypeFactory = null;
		
		for (Object key:properties.keySet()) {
			log.debug("classname: " + key);
			className=properties.getProperty(key.toString());
			log.debug("className: " + className);
			try {	
				migrationTypeFactory = (CourseTypeFactory)getPortletClassLoaderWorkflow().loadClass(className).newInstance();
				if(migrationTypeFactory!=null){
					log.debug("---classnameid "+migrationTypeFactory.getClassNameId());
					migrationTypeFactories.put(migrationTypeFactory.getClassNameId(), migrationTypeFactory);
				}
			} catch (ClassNotFoundException e) {
				try {
					String [] context = ((String) key).split("\\.");
					if (Validator.isNotNull(context) && context.length == 2) {
						context[1] = BaseCourseTypeFactory.COURSE_TYPE_PORTLET_ID;
					}
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(className, true, 
						PortletClassLoaderUtil.getClassLoader(context[1])).newInstance(), className, 
						PortletClassLoaderUtil.getClassLoader(context[1]));
					migrationTypeFactory = new CourseTypeFactoryClp(classLoaderProxy);
					if(migrationTypeFactory!=null){
						log.debug("---classnameid "+migrationTypeFactory.getClassNameId());
						migrationTypeFactories.put(migrationTypeFactory.getClassNameId(), migrationTypeFactory);
					}
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
			} catch (ClassCastException e) {
				try {
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(className, true, 
						getPortletClassLoader()).newInstance(), className, 
						getPortletClassLoader());
					migrationTypeFactory = new CourseTypeFactoryClp(classLoaderProxy);
					if(migrationTypeFactory!=null){
						log.debug("---classnameid "+migrationTypeFactory.getClassNameId());
						migrationTypeFactories.put(migrationTypeFactory.getClassNameId(), migrationTypeFactory);
					}
				} catch (Throwable throwable) {
				}
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}
		
		return migrationTypeFactories;
	}
	
	private static ClassLoader _portletClassLoader;
	
	
	private static ClassLoader getPortletClassLoaderWorkflow() {
		if(_portletClassLoader==null) {
			_portletClassLoader=PortletClassLoaderUtil.getClassLoader(BaseCourseTypeFactory.COURSE_TYPE_PORTLET_ID);
		}
		return _portletClassLoader;
	}
	
	private static ClassLoader getPortletClassLoader() {
		if(_portletClassLoader==null) {
			ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
			if(currentClassLoader.equals(PortalClassLoaderUtil.getClassLoader())) {
				_portletClassLoader=PortletClassLoaderUtil.getClassLoader(BaseCourseTypeFactory.COURSE_TYPE_PORTLET_ID);
			}
			else {
				_portletClassLoader=currentClassLoader;
			}
		}
		return _portletClassLoader;
	}
	
	public CourseTypeFactoryRegistry() {
		log.debug("entramos: " + _migrationTypeFactoryThreadLocal.get());
		_migrationTypeFactories =  _migrationTypeFactoryThreadLocal.get();
		if((Validator.isNull(_migrationTypeFactories))|| (_migrationTypeFactories.isEmpty())) {
				_migrationTypeFactoryThreadLocal.set(_getCourseTypeFactories());
		}
		_migrationTypeFactories =  _migrationTypeFactoryThreadLocal.get();
	}
	
	public List<CourseTypeFactory> getCourseTypeFactoriesList(){
		List<CourseTypeFactory> migrationEventTypeFactories = new ArrayList<CourseTypeFactory>();
		HashMap<Long,CourseTypeFactory> hashMap = getCourseTypeFactories();
		log.debug("hashMap: " + hashMap.size());
		if(hashMap != null && hashMap.size() > 0){
			Iterator<CourseTypeFactory> iterator = hashMap.values().iterator();
			CourseTypeFactory migrationTypeFactory = null;
			while(iterator.hasNext()){
				migrationTypeFactory = iterator.next();
				migrationEventTypeFactories.add(migrationTypeFactory);
			}
		}
		return migrationEventTypeFactories;
	}

	public HashMap<Long,CourseTypeFactory> getCourseTypeFactories() {
		return _migrationTypeFactories;
	}
	
	public CourseTypeFactory getCourseTypeFactory(long classNameId){
		return _migrationTypeFactories.get(classNameId);
	}
	
	public CourseTypeFactory getCourseTypeFactoryByClassName(String className){
		return _migrationTypeFactories.get(PortalUtil.getClassNameId(className));
	}
	
	private static ThreadLocal<HashMap<Long,CourseTypeFactory>>
		_migrationTypeFactoryThreadLocal =
			new AutoResetThreadLocal<HashMap<Long,CourseTypeFactory>>(
					CourseTypeFactoryRegistry.class + "._migrationTypeFactoryThreadLocal");
}
