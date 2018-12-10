package com.liferay.lms.service.persistence;

import java.math.BigInteger;
import com.liferay.lms.model.Course;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

public class CourseTypeFinderImpl extends BasePersistenceImpl<Course> implements CourseTypeFinder{
	
	Log log = LogFactoryUtil.getLog(CourseTypeFinderImpl.class);
	 
	public static final String COUNT_COURSES =
		    CourseTypeFinder.class.getName() +
		        ".countCourses";
	
	public int countCourses(long courseTypeId){
		Session session = null;
		int numCourses = 0;
		try{
			if(log.isDebugEnabled())
				log.debug(":: countCourses :: courseTypeId :: " + courseTypeId);
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(COUNT_COURSES);
			if(log.isDebugEnabled())
					log.debug(":: countCourses :: sql :: " + sql);
			
			SQLQuery q = session.createSQLQuery(sql);
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(courseTypeId);
			
			Object count = q.list().get(0);
			if(log.isDebugEnabled()) log.debug(":: countCourses :: count ok :: " + Validator.isNotNull(count));
			if (count != null) {
				if(count instanceof Long){
					numCourses = ((Long)count).intValue();
				}else if(count instanceof BigInteger){
					numCourses = ((BigInteger)count).intValue();
				}else if(count instanceof Integer){
					numCourses = (Integer)count;
				}
			}
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	    	closeSession(session);
	    }
		if(log.isDebugEnabled()) log.debug(":: countCourses :: numCourses :: " + numCourses);
	    return numCourses;
	}
	
}
