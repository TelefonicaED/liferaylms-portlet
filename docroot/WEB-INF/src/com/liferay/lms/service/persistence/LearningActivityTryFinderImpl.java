package com.liferay.lms.service.persistence;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.impl.LearningActivityTryImpl;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

public class LearningActivityTryFinderImpl extends BasePersistenceImpl<LearningActivityTry> implements LearningActivityTryFinder{
	
	Log log = LogFactoryUtil.getLog(LearningActivityTryFinderImpl.class);
	
	public static final String FIND_USERS_BY_ACT_ID =
		    LearningActivityTryFinder.class.getName() +
		        ".findUsersByActId";
	public static final String FIND_LAST_LEARNING_ACTIVITY_TRY_CREATE_BY_USER_AND_ACT =
			LearningActivityTryFinder.class.getName() +
		        ".findLastLearningActivityTryCreateByUsersAndActId";
	public static final String FIND_LAST_LEARNING_ACTIVITY_TRY_FINISHED_BY_USER_AND_ACT =
			LearningActivityTryFinder.class.getName() +
		        ".findLastLearningActivityTryFinishedByUserAndActId";
	public static final String FIND_LAST_LEARNING_ACTIVITY_TRY_NOT_FINISHED_BY_USER_AND_ACT =
			LearningActivityTryFinder.class.getName() +
		        ".findLastLearningActivityTryNotFinishedByUsersAndActId";
	public static final String FIND_LEARNING_ACTIVITY_TRY_NOT_FINISHED_BY_USER_AND_ACT =
			LearningActivityTryFinder.class.getName() +
		        ".findLearningActivityTryNotFinishedByUsersAndActId";
	
	public long triesPerUserOnlyStudents(long actId, long companyId, long courseGropupCreatedId, List<User> _students, long teamId) throws SystemException {
		Session session = null;
		try{
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.fetchLmsPrefs(companyId);			
			long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
			long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
			
			String sql="SELECT count(1) FROM lms_learningactivitytry r " +
					" INNER JOIN users_groups ug ON r.userId = ug.userId " +
					" AND ug.groupId ="+courseGropupCreatedId;
			if(teamId>0){
				sql+=" INNER JOIN users_teams ut ON r.userId = ut.userId AND ut.teamId = "+teamId;
			}
			sql+= " WHERE actId="+actId;
			
			// Se prepara el metodo para recibir un Listado de estudiantes especificos,, por ejemplo que pertenezcan a alguna organizacion. Sino, se trabaja con todos los estudiantes del curso.
			if(Validator.isNotNull(_students) && _students.size() > 0){
				sql += " AND r.userId in (-1";
				for(User user:_students){
					sql+=","+user.getUserId();
				}
				sql+=") ";
			}
			
			sql+=" AND r.userId not in ( SELECT userId FROM usergrouprole WHERE usergrouprole.groupId = " +courseGropupCreatedId+
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

	
	public List<User> getUsersByActId(long actId){
		Session session = null;
		List<User> distinctUsers = new ArrayList<User>();
		
		try{
			
			session = openSessionLiferay();
			
			String sql = CustomSQLUtil.get(FIND_USERS_BY_ACT_ID);
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
			}
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("User_",PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portal.model.impl.UserImpl"));
			
			QueryPos qPos = QueryPos.getInstance(q);			
			qPos.add(actId);				
			distinctUsers = (List<User>)q.list();
			
			log.debug("Distinct Users: " + distinctUsers.size());
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSessionLiferay(session);
	    }
	
		return distinctUsers;
	}

	
	public LearningActivityTry findLastLearningActivityTryCreateByUsersAndActId(long actId, long userId){
		LearningActivityTry learningActivityTry = null;
		Session session = null;
		try{
			
			String sql = CustomSQLUtil.get(FIND_LAST_LEARNING_ACTIVITY_TRY_CREATE_BY_USER_AND_ACT);
			
			session = openSession();			
			
			log.debug("sql: " + sql);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("lat", LearningActivityTryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(actId);
			qPos.add(userId);
			
			Iterator<LearningActivityTry> itr = q.iterate();

			if (itr.hasNext()) {
				learningActivityTry = itr.next();

				if (learningActivityTry != null) {
					return learningActivityTry;
				}
			}
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return learningActivityTry;
	}
	
	
	
	
	
	public List<LearningActivityTry> findLearningActivityTryNotFinishedByUsersAndActId(long actId, long userId){
		List<LearningActivityTry> latList = new ArrayList<LearningActivityTry>();
		Session session = null;
		try{
			
			String sql = CustomSQLUtil.get(FIND_LEARNING_ACTIVITY_TRY_NOT_FINISHED_BY_USER_AND_ACT);
			
			session = openSession();			
			
			log.debug("sql: " + sql);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("lat", LearningActivityTryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(actId);
			qPos.add(userId);
			
			latList = (List<LearningActivityTry>) q.list();
			
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return latList;
	}
	
	
	
	
	public LearningActivityTry findLastLearningActivityTryNotFinishedByUsersAndActId(long actId, long userId){
		LearningActivityTry learningActivityTry = null;
		Session session = null;
		try{
			
			String sql = CustomSQLUtil.get(FIND_LAST_LEARNING_ACTIVITY_TRY_NOT_FINISHED_BY_USER_AND_ACT);
			
			session = openSession();			
			
			log.debug("sql: " + sql);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("lms_learningactivitytry", LearningActivityTryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(actId);
			qPos.add(userId);
			
			Iterator<LearningActivityTry> itr = q.iterate();

			if (itr.hasNext()) {
				learningActivityTry = itr.next();

				if (learningActivityTry != null) {
					return learningActivityTry;
				}
			}
			
			
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return learningActivityTry;
	}
	
	
	
	public LearningActivityTry findLastLearningActivityTryFinishedByUserAndActId(long actId, long userId){
		LearningActivityTry learningActivityTry = null;
		Session session = null;
		try{
			
			String sql = CustomSQLUtil.get(FIND_LAST_LEARNING_ACTIVITY_TRY_FINISHED_BY_USER_AND_ACT);
			
			session = openSession();			
			
			log.debug("sql: " + sql);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("lat", LearningActivityTryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(actId);
			qPos.add(userId);
			
			Iterator<LearningActivityTry> itr = q.iterate();

			if (itr.hasNext()) {
				learningActivityTry = itr.next();

				if (learningActivityTry != null) {
					return learningActivityTry;
				}
			}
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return learningActivityTry;
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
