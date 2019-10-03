package com.liferay.lms.service.persistence;


import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.SurveyResult;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.views.CourseResultView;
import com.liferay.lms.views.CourseView;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

public class SurveyResultFinderImpl extends BasePersistenceImpl<SurveyResult> implements SurveyResultFinder{
	
	Log log = LogFactoryUtil.getLog(SurveyResultFinderImpl.class);
	public static final String FIND_TRIES_BY_ACT_ID =
			 SurveyResultFinder.class.getName() +
				".findTriesByActId";
	public long countStartedOnlyStudents(long actId, long companyId, long courseGropupCreatedId, List<User> _students){
		Session session = null;
		;
		try{
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.fetchLmsPrefs(companyId);			
			long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
			long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
			
			String sql="SELECT count(1) FROM lms_learningactivityresult r INNER JOIN users_groups ug ON r.userId = ug.userId AND ug.groupId ="+courseGropupCreatedId+
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

	
	public long countStudentsByQuestionIdAndAnswerId(long questionId, long answerId, long companyId, long courseGropupCreatedId){ 
		Session session = null;
		;
		try{
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.fetchLmsPrefs(companyId);			
			long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
			long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
			
			String sql="SELECT count(1) FROM lms_surveyresult r INNER JOIN users_groups ug ON r.userId = ug.userId AND ug.groupId ="+courseGropupCreatedId+
					" WHERE answerId="+answerId+" AND questionId="+questionId;
			
			
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
		
	public long countStudentsByQuestionId(long questionId, long companyId, long courseGropupCreatedId){ 
		Session session = null;
		;
		try{
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.fetchLmsPrefs(companyId);			
			long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
			long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
			
			String sql="SELECT count(1) FROM lms_surveyresult r INNER JOIN users_groups ug ON r.userId = ug.userId AND ug.groupId ="+courseGropupCreatedId+
					" WHERE questionId="+questionId;
			
			
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
	
	
	public List<Long> getTriesByActId(long actId){
		
		Session session = null;
		List<Long> tries = new ArrayList<Long>();
			
		try{
				
				session = openSession();
				
				String sql = CustomSQLUtil.get(FIND_TRIES_BY_ACT_ID);
				
				if(log.isDebugEnabled()){
					log.debug("sql: " + sql);
					log.debug("actId: " + actId);
				}
				
				SQLQuery q = session.createSQLQuery(sql);
				
				QueryPos qPos = QueryPos.getInstance(q);
				qPos.add(actId);
			
				
				Iterator<BigInteger> itr =  q.iterate();
								
				Long latId = null;
				
				while (itr.hasNext()) {
					latId = itr.next().longValue();
					log.debug("LAT ID "+latId);
					tries.add(latId);
				}
				
				
				
			} catch (Exception e) {
		       e.printStackTrace();
		    } finally {
		        closeSession(session);
		    }
	
		return tries;
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
