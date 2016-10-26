package com.liferay.lms.service.persistence;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

public class CourseResultFinderImpl extends BasePersistenceImpl<CourseResult> implements CourseResultFinder{
	
	Log log = LogFactoryUtil.getLog(CourseResultFinderImpl.class);
	 	
	public long countStartedOnlyStudents(long courseId, long companyId, long courseGropupCreatedId, List<User> _students, long teamId){
		Session session = null;
		try{
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.fetchLmsPrefs(companyId);			
			long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
			long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
			
			String sql="SELECT count(1) " +
					"FROM lms_courseresult cr " +
					"INNER JOIN users_groups ug ON cr.userId = ug.userId " +
					"AND ug.groupId ="+courseGropupCreatedId;
			if(teamId>0){
				sql+=" INNER JOIN users_teams ut ON cr.userId = ut.userId AND ut.teamId = "+teamId;
			}
			sql+=" WHERE courseId="+courseId;
			
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
				sql+=" INNER JOIN users_teams ut ON mr.userId = ut.userId AND ut.teamId = "+teamId;
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
	
	
	
	public long countFinishedPassedOnlyStudents(long courseId, long companyId, long courseGropupCreatedId, List<User> _students, boolean passed, long teamId){
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
			sql+=" WHERE courseId="+courseId+" AND cr.passedDate IS NOT NULL AND cr.passed = "+passed;
			
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
		
	
	public double avgFinishedOnlyStudents(long courseId, long companyId, long courseGropupCreatedId, List<User> _students, boolean passed, long teamId){
		Session session = null;
		double result = 0;
		try{
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.fetchLmsPrefs(companyId);			
			long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
			long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
			
			String sql="SELECT avg(cr.result) " +
					" FROM lms_courseresult cr " +
					" INNER JOIN users_groups ug ON cr.userId = ug.userId " +
					" AND ug.groupId ="+courseGropupCreatedId;
			if(teamId>0){
				sql+=" INNER JOIN users_teams ut ON cr.userId = ut.userId AND ut.teamId = "+teamId;
			}
			sql+=" WHERE courseId="+courseId+" AND cr.passedDate IS NOT NULL AND cr.passed =" + passed;
			
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
			if((q.list()).get(0)!=null){
				result = ((List<BigDecimal>) q.list()).get(0).doubleValue();
			}		
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return result;
	}
	
		
	public long countStudentsByCourseId(Course course, List<User> students, boolean passed, long teamId) throws SystemException {
		Session session = null;
		long res = 0;
		try{
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.fetchLmsPrefs(course.getCompanyId());			
			long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
			long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
			
			String sql=	"SELECT count(1) FROM lms_courseresult cr " +
						" INNER JOIN users_groups ug ON cr.userId = ug.userId AND ug.groupId ="+course.getGroupCreatedId();
			if(teamId>0){
				sql+=" INNER JOIN users_teams ut ON cr.userId = ut.userId AND ut.teamId = "+teamId;
			}
			sql+=" WHERE courseId="+course.getCourseId()+" AND cr.passed = "+passed;
			
			// Se prepara el metodo para recibir un Listado de estudiantes especificos,, por ejemplo que pertenezcan a alguna organizacion. Sino, se trabaja con todos los estudiantes del curso.
			if(Validator.isNotNull(students) && students.size() > 0){
				sql += " AND cr.userId in (-1";
				for(User user: students){
					sql+=","+user.getUserId();
				}
				sql+=") ";
			}else{
				sql+=" AND cr.userId not in ( SELECT userId FROM usergrouprole WHERE usergrouprole.groupId = " +course.getGroupCreatedId() +
						 " AND usergrouprole.roleId in ("+teacherRoleId+","+editorRoleId+"))";	
			}
			
			
			session = openSession();			
			
			log.debug("sql: " + sql);			
			
			SQLQuery q = session.createSQLQuery(sql);
			
			res=((List<BigInteger>) q.list()).get(0).longValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		
		return res;
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
