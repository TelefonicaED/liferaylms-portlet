package com.liferay.lms.course.inscriptiontype;

import java.util.Arrays;
import java.util.Comparator;
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

public class InscriptionTypeRegistry {
	
	protected static final String LMS_ACTIVITIES_LIST_PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	
	private static InscriptionType[] _getInscriptionTypes(){
		Properties properties = PropsUtil.getProperties("lms.inscription.type", true);
		InscriptionType[] inscriptionTypes = new InscriptionType[properties.size()];
		int currentInscriptionType = 0;
		for (Object key:properties.keySet()) {
			String type=properties.getProperty(key.toString());
			try {	
				InscriptionType inscriptionType = (InscriptionType)getPortletClassLoader().loadClass(type).newInstance();
				inscriptionTypes[currentInscriptionType++]=inscriptionType;
			} catch (ClassNotFoundException e) {
				try {
					String [] context = ((String) key).split("\\.");
					if (Validator.isNotNull(context) && context.length == 2) {
						context[1] = LMS_ACTIVITIES_LIST_PORTLET_ID;
					}
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						PortletClassLoaderUtil.getClassLoader(context[1])).newInstance(), type, 
						PortletClassLoaderUtil.getClassLoader(context[1]));
					inscriptionTypes[currentInscriptionType++]=new InscriptionTypeClp(classLoaderProxy);
				} catch (Throwable throwable) {
				}
			} catch (ClassCastException e) {
				try {
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						getPortletClassLoader()).newInstance(), type, 
						getPortletClassLoader());
					inscriptionTypes[currentInscriptionType++]=new InscriptionTypeClp(classLoaderProxy);
				} catch (Throwable throwable) {
				}
			} catch (Throwable throwable) {
			}
		}
		
		InscriptionType[] inscriptions = null;
		
		if(properties.size()==currentInscriptionType) {
			inscriptions = inscriptionTypes;
		}
		else {
			inscriptions = Arrays.copyOf(inscriptionTypes,currentInscriptionType);
		}
		
		Arrays.sort(inscriptions, new Comparator<InscriptionType>() {
		    @Override
		    public int compare(InscriptionType o1, InscriptionType o2) {
		        return ((Long) o2.getTypeId()).compareTo(o1.getTypeId());
		    }
		});
		
		return inscriptions;
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
	
	public InscriptionTypeRegistry() {
		_inscriptionTypes =  _inscriptionTypeThreadLocal.get();
		if(Validator.isNull(_inscriptionTypes)|| _inscriptionTypes.isEmpty()|| !(_inscriptionTypes.get(0) instanceof InscriptionType)) {
			InscriptionType[] inscriptionTypes = _getInscriptionTypes();
			_inscriptionTypes=new UnmodifiableList<InscriptionType>(Arrays.asList(inscriptionTypes));
			_inscriptionTypeThreadLocal.set(_inscriptionTypes);
			
		}
	}
		
	public InscriptionType getInscriptionType(long typeId) {
		for (InscriptionType inscriptionType : _inscriptionTypes) {
			if(inscriptionType.getTypeId()==typeId){
				return inscriptionType;
			}
		}	
		return null;
	}

	public List<InscriptionType> getInscriptionTypes() {
		return _inscriptionTypes;
	}
	
	private List<InscriptionType> _inscriptionTypes;
	
	private static ThreadLocal<List<InscriptionType>>
		_inscriptionTypeThreadLocal =
			new AutoResetThreadLocal<List<InscriptionType>>(
					InscriptionTypeRegistry.class + "._inscriptionTypeThreadLocal");
}
