package com.liferay.lms.service.persistence;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.impl.CourseImpl;
import com.liferay.lms.model.impl.CourseResultImpl;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

public class CourseResultFinderImpl extends BasePersistenceImpl<CourseResult> implements CourseResultFinder{
	
	Log log = LogFactoryUtil.getLog(CourseResultFinderImpl.class);
	

	public static final String AVG_RESULT_COURSE_ID =
			CourseResultFinder.class.getName() +
		        ".avgResultCourseId";
	public static final String WHERE_COURSE_ID = 
			CourseResultFinder.class.getName() + ".whereCourseId";
	public static final String WHERE_EDITIONS = 
			CourseResultFinder.class.getName() + ".whereEditions";	
	public static final String FIND_BY_PARENT_COURSE_ID =
			CourseResultFinder.class.getName() +
		        ".findCourseResultByParentCourseId";
	
	public double avgResultByCourseIdStudents(long courseId, boolean includeEditions){
		Session session = null;
		try{
			
			if(log.isDebugEnabled()){
				log.debug("courseId:"+courseId);
			}
						
			session = openSession();
			
			String sql = CustomSQLUtil.get(AVG_RESULT_COURSE_ID);
			sql = replaceCourseOrEditions(sql, includeEditions);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("avgResult", Type.DOUBLE);
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(courseId);
			if(includeEditions){
				qPos.add(courseId);
			}
			
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
	
	public List<CourseResult> findCourseResultByParentCourseId(long courseParentId, long userId){
		Session session = null;
		List<CourseResult> courseResults = null;
		
		try{
			
			if(log.isDebugEnabled()){
				log.debug("courseParentId:"+courseParentId);
				log.debug("userId:"+userId);
			}
						
			session = openSession();
			
			String sql = CustomSQLUtil.get(FIND_BY_PARENT_COURSE_ID);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Lms_CourseResult", CourseResultImpl.class);
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(courseParentId);
			qPos.add(userId);
			
			courseResults = (List<CourseResult>) QueryUtil.list(
					q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	    	closeSession(session);
	    }
	
	    return courseResults;
	}
	
	private String replaceCourseOrEditions(String sql, boolean includeEditions){
		String sqlCourse = null;
		if(!includeEditions){
			sqlCourse = CustomSQLUtil.get(WHERE_COURSE_ID);
		}else{
			sqlCourse = CustomSQLUtil.get(WHERE_EDITIONS);
		}
		sql = StringUtil.replace(sql, "[$WHERECOURSEOREDITIONS$]", sqlCourse);
		
		return sql;
	}

	public long countFinishedOnlyStudents(long courseId, long companyId, long courseGropupCreatedId, List<User> _students, long teamId){
		Session session = null;
		try{
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.fetchLmsPrefs(companyId);			
			long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
			long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
			
			String sql="SELECT count(1) " +
					" FROM lms_courseresult cr " +
					" INNER JOIN users_groups ug ON cr.userId = ug.userId " +
					" AND ug.groupId ="+courseGropupCreatedId;
			if(teamId>0){
				sql+=" INNER JOIN users_teams ut ON cr.userId = ut.userId AND ut.teamId = "+teamId;
			}
			sql+=" WHERE courseId="+courseId+" AND cr.passedDate IS NOT NULL ";
			
			// Se prepara el metodo para recibir un Listado de estudiantes especificos,, por ejemplo que pertenezcan a alguna organizacion. Sino, se trabaja con todos los estudiantes del curso.
			if(Validator.isNotNull(_students) && _students.size() > 0){
				sql += " AND cr.userId in (-1";
				for(User user:_students){
					sql+=","+user.getUserId();
				}
				sql+=") ";
			}
			
			sql+=" AND cr.userId not in ( SELECT userId FROM usergrouprole WHERE usergrouprole.groupId = " +courseGropupCreatedId+
				 " AND usergrouprole.roleId in ("+teacherRoleId+","+editorRoleId+"))";	
			
			session = openSession();			
			
			log.debug("sql: " + sql);			
			
			SQLQuery q = session.createSQLQuery(sql);
			
			return ((List<BigInteger>) q.list()).get(0).longValue();
			
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
