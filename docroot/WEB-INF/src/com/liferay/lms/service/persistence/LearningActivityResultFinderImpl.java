package com.liferay.lms.service.persistence;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

public class LearningActivityResultFinderImpl extends BasePersistenceImpl<LearningActivityResult> implements LearningActivityResultFinder{
	
	Log log = LogFactoryUtil.getLog(LearningActivityResultFinderImpl.class);
	
	public static final String AVG_RESULT_ACT_ID =
			LearningActivityResultFinder.class.getName() +
		        ".avgResultActId";
	public static final String WHERE_BY_USER_EXCLUEDED_IDS =
			LearningActivityResultFinder.class.getName() +
		        ".whereByUserExcludedIds";
	public static final String WHERE_BY_USER_IDS =
			LearningActivityResultFinder.class.getName() +
		        ".whereByUserIds";
	
	public double avgResultByActId(long actId, long[] userIds, long[] userExcludedIds){
		Session session = null;
		try{
			
			if(log.isDebugEnabled()){
				log.debug("actId:"+actId);
			}
						
			session = openSession();
			
			String sql = CustomSQLUtil.get(AVG_RESULT_ACT_ID);
			
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
			qPos.add(actId);
			
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
	 	
	public long countStartedOnlyStudents(long actId, long companyId, long courseGropupCreatedId, List<User> _students){
		Session session = null;
		try{
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.fetchLmsPrefs(companyId);			
			long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
			long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
			
			String sql="SELECT * FROM lms_learningactivityresult r INNER JOIN users_groups ug ON r.userId = ug.userId AND ug.groupId ="+courseGropupCreatedId+
					" WHERE actId="+actId;
			
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

	
	public long countFinishedOnlyStudents(long actId, long companyId, long courseGropupCreatedId, List<User> _students){
		Session session = null;
		try{
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.fetchLmsPrefs(companyId);			
			long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
			long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
			
			String sql="SELECT count(1) FROM lms_learningactivityresult r INNER JOIN users_groups ug ON r.userId = ug.userId AND ug.groupId ="+courseGropupCreatedId+
					" WHERE actId="+actId+" AND r.endDate IS NOT NULL ";
			
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






















