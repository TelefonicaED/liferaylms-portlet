package com.liferay.lms.learningactivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.util.PortalUtil;

public class LearningActivityTypeRegistry {
	
	private static Log log = LogFactoryUtil.getLog(LearningActivityTypeRegistry.class);
	protected static final String LMS_ACTIVITIES_LIST_PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());

	private static LearningActivityType[] _getLearningActivityTypes(){
		Properties properties = PropsUtil.getProperties("lms.learningactivity.type", true);
		LearningActivityType[] learningActivityTypes = new LearningActivityType[properties.size()];
		log.debug("properties size: " + properties.size());
		int currentLearningActivityType = 0;
		for (Object key:properties.keySet()) {
			log.debug("key: " + key.toString());
			String type=properties.getProperty(key.toString());
			log.debug("type: " + type);
			try {	
				LearningActivityType learningActivityType = (LearningActivityType)getPortletClassLoader().loadClass(type).newInstance();
				learningActivityTypes[currentLearningActivityType++]=learningActivityType;
			} catch (ClassNotFoundException e) {
				log.debug(e);
				try {
					String [] context = ((String) key).split("\\.");
					if (Validator.isNotNull(context) && context.length == 2) {
						context[1] = LMS_ACTIVITIES_LIST_PORTLET_ID;
					}
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						PortletClassLoaderUtil.getClassLoader(context[1])).newInstance(), type, 
						PortletClassLoaderUtil.getClassLoader(context[1]));
					learningActivityTypes[currentLearningActivityType++]=new LearningActivityTypeClp(classLoaderProxy);
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
			} catch (ClassCastException e) {
				log.debug(e);
				try {
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						getPortletClassLoader()).newInstance(), type, 
						getPortletClassLoader());
					learningActivityTypes[currentLearningActivityType++]=new LearningActivityTypeClp(classLoaderProxy);
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}
		
		if(properties.size()==currentLearningActivityType) {
			return learningActivityTypes;
		}
		else {
			return Arrays.copyOf(learningActivityTypes,currentLearningActivityType);
		}
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
	
	public LearningActivityTypeRegistry() {
			if((Validator.isNull(_learningActivityTypes))||
				(_learningActivityTypes.isEmpty())||
				(!(_learningActivityTypes.get(0) instanceof LearningActivityType))) {
					LearningActivityType[] learningActivityTypes = _getLearningActivityTypes();
					int orderedIdsSize = learningActivityTypes.length; 
					try{
						long[] orderedIds = StringUtil.split(LmsPrefsLocalServiceUtil.getStrictLmsPrefsIni(CompanyThreadLocal.getCompanyId()).getActivities(), 
																StringPool.COMMA, GetterUtil.DEFAULT_LONG);
						orderedIdsSize = orderedIds.length; 
						for (int currentPosition = 0; currentPosition < orderedIds.length; currentPosition++) {
							for(int currentLearningActivityType=currentPosition+1;currentLearningActivityType<learningActivityTypes.length;currentLearningActivityType++){
								if(learningActivityTypes[currentLearningActivityType].getTypeId()==orderedIds[currentPosition]){
									LearningActivityType learningActivityType=learningActivityTypes[currentLearningActivityType];
									learningActivityTypes[currentLearningActivityType]=learningActivityTypes[currentPosition];
									learningActivityTypes[currentPosition]=learningActivityType;
								}
							}
						}
					} catch(NestableException e){}
				_learningActivityTypes=new UnmodifiableList<LearningActivityType>(Arrays.asList(learningActivityTypes));
				_learningActivityTypesForCreating = new UnmodifiableList<LearningActivityType>(Arrays.asList(Arrays.copyOf(learningActivityTypes, orderedIdsSize)));
		
			}

	}
		
	public LearningActivityType getLearningActivityType(long typeId) {
		for (LearningActivityType learningActivityType : _learningActivityTypes) {
			if(learningActivityType.getTypeId()==typeId){
				return learningActivityType;
			}
		}	
		return null;
	}
	
	public HashMap<Integer, LearningActivityType> getLearningActivityTypesForCreatingHash(){
		HashMap<Integer, LearningActivityType> hashLearningActivityType = new HashMap<Integer, LearningActivityType>();
		for(LearningActivityType lat: _learningActivityTypesForCreating){
			hashLearningActivityType.put((int)lat.getTypeId(), lat);
		}
		return hashLearningActivityType;
	}
	

	public static List<LearningActivityType> getLearningActivityTypes() {
		return _learningActivityTypes;
	}
	
	public static List<LearningActivityType> getLearningActivityTypesForCreating() {
		return _learningActivityTypesForCreating;
	}
	
	public static void resetLearningActivityTypes(){
		
		_learningActivityTypes = null;
	}
	
	private static  List<LearningActivityType> _learningActivityTypes = null;
	

	
	private static  List<LearningActivityType> _learningActivityTypesForCreating = null;
	

}
