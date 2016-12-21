package com.liferay.lms.service.persistence;


import java.util.Iterator;
import org.apache.commons.lang.ArrayUtils;

import com.liferay.lms.model.CourseResult;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

public class CourseResultFinderImpl extends BasePersistenceImpl<CourseResult> implements CourseResultFinder{
	
	Log log = LogFactoryUtil.getLog(CourseResultFinderImpl.class);
	
	public static final String AVG_RESULT_COURSE_ID =
			CourseResultFinder.class.getName() +
		        ".avgResultCourseId";
	public static final String WHERE_BY_USER_EXCLUEDED_IDS =
			CourseResultFinder.class.getName() +
		        ".whereByUserExcludedIds";
	public static final String WHERE_BY_USER_IDS =
			CourseResultFinder.class.getName() +
		        ".whereByUserIds";
	
	public double avgResultByCourseId(long courseId, boolean passed, long[] userIds, long[] userExcludedIds){
		Session session = null;
		try{
			
			if(log.isDebugEnabled()){
				log.debug("courseId:"+courseId);
			}
						
			session = openSession();
			
			String sql = CustomSQLUtil.get(AVG_RESULT_COURSE_ID);
			
			if(userIds != null && userIds.length > 0){
				String userIdsString = ArrayUtils.toString(userIds);
				userIdsString = userIdsString.substring(1, userIdsString.length()-1);
				String sqlUserIds = CustomSQLUtil.get(WHERE_BY_USER_IDS);
				sqlUserIds = sqlUserIds.replace("[$USERIDS$]", userIdsString);
				sql += sqlUserIds;
			}
			
			if(userExcludedIds != null && userExcludedIds.length > 0){
				String userExcludedIdsString = ArrayUtils.toString(userExcludedIds);
				userExcludedIdsString = userExcludedIdsString.substring(1, userExcludedIdsString.length()-1);
				String sqlUserExcludedIds = CustomSQLUtil.get(WHERE_BY_USER_EXCLUEDED_IDS);
				sqlUserExcludedIds = sqlUserExcludedIds.replace("[$USEREXCLUDEDIDS$]", userExcludedIdsString);
				sql += sqlUserExcludedIds;
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("avgResult", Type.DOUBLE);
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(courseId);
			qPos.add(passed);
			
			Iterator<Double> itr = q.iterate();

			if (itr.hasNext()) {
				Double avgResult = itr.next();

				if (avgResult != null) {
					return avgResult.doubleValue();
				}
			}
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	    	closeSession(session);
	    }
	
	    return 0;
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
