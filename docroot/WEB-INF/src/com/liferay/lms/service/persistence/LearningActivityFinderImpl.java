package com.liferay.lms.service.persistence;


import java.util.List;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.impl.LearningActivityImpl;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

public class LearningActivityFinderImpl extends BasePersistenceImpl<LearningActivity> implements LearningActivityFinder{
	
	Log log = LogFactoryUtil.getLog(LearningActivityFinderImpl.class);
	 
	public static final String FIND_BY_PRIORITY =
		    LearningActivityFinder.class.getName() +
		        ".findByPriority";
	
	public LearningActivity findByPriority(int position, long moduleId, long companyId){
		Session session = null;
		
		try{
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(FIND_BY_PRIORITY);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Lms_LearningActivity", LearningActivityImpl.class);
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(moduleId);
			qPos.add(companyId);
			qPos.add(position);
			
			List<LearningActivity> learningActivities = q.list();

			if (!learningActivities.isEmpty()) {
				return learningActivities.get(0);
			}
			
			return null;
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return null;
	}
	
	private final Class<?> getPortalClass(String className) {
		ClassLoader portalCl = PortalClassLoaderUtil.getClassLoader();

		try {
			return portalCl.loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	private SessionFactory getPortalSessionFactory() {
		String sessionFactory = "liferaySessionFactory";

		SessionFactory sf = (SessionFactory) PortalBeanLocatorUtil
				.getBeanLocator().locate(sessionFactory);

		return sf;
	}

	public void closeSessionLiferay(Session session) {
		getPortalSessionFactory().closeSession(session);
	}

	public Session openSessionLiferay() throws ORMException {
		return getPortalSessionFactory().openSession();
	}
}
