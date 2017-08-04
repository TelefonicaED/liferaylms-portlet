package com.liferay.lms.learningactivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.liferay.lms.learningactivity.courseeval.CourseEvalClp;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.util.PortalUtil;

public class LearningActivityTypeRegistry {
	
	protected static final String LMS_ACTIVITIES_LIST_PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	
	private static LearningActivityType[] _getLearningActivityTypes(){
		Properties properties = PropsUtil.getProperties("lms.learningactivity.type", true);
		LearningActivityType[] learningActivityTypes = new LearningActivityType[properties.size()];
		int currentLearningActivityType = 0;
		for (Object key:properties.keySet()) {
			String type=properties.getProperty(key.toString());
			try {	
				LearningActivityType learningActivityType = (LearningActivityType)getPortletClassLoader().loadClass(type).newInstance();
				learningActivityTypes[currentLearningActivityType++]=learningActivityType;
			} catch (ClassNotFoundException e) {
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
				}
			} catch (ClassCastException e) {
				try {
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						getPortletClassLoader()).newInstance(), type, 
						getPortletClassLoader());
					learningActivityTypes[currentLearningActivityType++]=new LearningActivityTypeClp(classLoaderProxy);
				} catch (Throwable throwable) {
				}
			} catch (Throwable throwable) {
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
		_learningActivityTypes =  _learningActivityTypeThreadLocal.get();
		_learningActivityTypesForCreating = _learningActivityTypeForCreatingThreadLocal.get();
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
			_learningActivityTypeThreadLocal.set(_learningActivityTypes);
			_learningActivityTypesForCreating = new UnmodifiableList<LearningActivityType>(Arrays.asList(Arrays.copyOf(learningActivityTypes, orderedIdsSize)));
			_learningActivityTypeForCreatingThreadLocal.set(_learningActivityTypesForCreating);
			
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
	

	public List<LearningActivityType> getLearningActivityTypes() {
		return _learningActivityTypes;
	}
	
	public List<LearningActivityType> getLearningActivityTypesForCreating() {
		return _learningActivityTypesForCreating;
	}
	
	private List<LearningActivityType> _learningActivityTypes;
	
	private static ThreadLocal<List<LearningActivityType>>
		_learningActivityTypeThreadLocal =
			new AutoResetThreadLocal<List<LearningActivityType>>(
				LearningActivityTypeRegistry.class + "._learningActivityTypeThreadLocal");
	
	private List<LearningActivityType> _learningActivityTypesForCreating;
	
	private static ThreadLocal<List<LearningActivityType>>
		_learningActivityTypeForCreatingThreadLocal =
			new AutoResetThreadLocal<List<LearningActivityType>>(
				LearningActivityTypeRegistry.class + "._learningActivityTypeForCreatingThreadLocal");
}
