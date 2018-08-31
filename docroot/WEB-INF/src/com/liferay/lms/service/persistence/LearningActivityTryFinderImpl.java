package com.liferay.lms.service.persistence;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
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
