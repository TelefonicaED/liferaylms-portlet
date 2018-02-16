package com.liferay.lms.service.persistence;


import java.util.ArrayList;
import java.util.List;

import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.impl.P2pActivityImpl;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

public class P2pActivityFinderImpl extends BasePersistenceImpl<P2pActivity> implements P2pActivityFinder{
	
	Log log = LogFactoryUtil.getLog(P2pActivityFinderImpl.class);
	 	
	
	public static final String FIND_BY_TEAM =
			P2pActivityFinder.class.getName() +
		        ".findByTeam";
	
	public static final String FIND_BY_GROUP =
			P2pActivityFinder.class.getName() +
		        ".findByGroup";
	
	public static final String FIND_BY_USER_WITHOUT_TEAM_ACTIVITIES =
			P2pActivityFinder.class.getName() +
		        ".findByUserWithoutTeamActivities";
	
	
	
	/**
	 * Gest all user´s p2pActivities of the course. Used for the assignation. 
	 * 
	 * @param actId Id of Learning activity.
	 * @param p2pActId Id of current p2pActivity
	 * @param start Limit start
	 * @param end Limit end
	 * @return List of p2pActivities of the group
	 */
	public List<P2pActivity> findByGroup(long actId, long p2pActId, int start, int end){
		List<P2pActivity> p2pActivities = new ArrayList<P2pActivity>();
		Session session = null;
		try{
			
			session = openSession();
			String sql = CustomSQLUtil.get(FIND_BY_GROUP);
			sql = replaceLimit(sql, start, end);
		
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				//log.debug("userId: " + userId);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("lms_p2pactivity", P2pActivityImpl.class);
			QueryPos qPos = QueryPos.getInstance(q);			
			qPos.add(actId);
			qPos.add(p2pActId);
							
			p2pActivities = (List<P2pActivity>)q.list();
			
			
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
		return p2pActivities;
		
	}
	
	
	/**
	 * Gest all user´s p2pActivities of the team. Used for the assignation. 
	 * 
	 * @param actId Id of Learning activity.
	 * @param p2pActId Id of current p2pActivity
	 * @param start Limit start
	 * @param end Limit end
	 * @return List of p2pActivities of the group
	 */
	public List<P2pActivity> findByTeam(long actId, long p2pActId, List<Team> userTeams, int start, int end){
		List<P2pActivity> p2pActivities = new ArrayList<P2pActivity>();
		Session session = null;
		try{
			
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_TEAM);

			sql = replaceLimit(sql, start, end);
			String teams = "(";
			for (int i = 0; i < userTeams.size(); i++) {
				teams += userTeams.get(i).getTeamId();
				if (i < (userTeams.size() - 1)) {
					teams += ", ";
				}
			}
			teams += ")";
			if (log.isDebugEnabled()) {
				log.debug("TEAMS "+userTeams);
				log.debug("sql: " + sql);
				// log.debug("userId: " + userId);
			}
			sql = sql.replace("[$TEAMS$]",teams);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Lms_P2pActivity", P2pActivityImpl.class);
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(actId);
			qPos.add(p2pActId);
			p2pActivities = (List<P2pActivity>) q.list();
			
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
		return p2pActivities;
		
	}

	
	public List<P2pActivity> findByUserWithoutTeamActivities(long actId, long p2pActId, long groupId, int start, int end){
		List<P2pActivity> p2pActivities = new ArrayList<P2pActivity>();
		Session session = null;
		try{
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(FIND_BY_USER_WITHOUT_TEAM_ACTIVITIES);
			sql = replaceLimit(sql, start, end);
		
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				//log.debug("userId: " + userId);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Lms_P2pActivity", P2pActivityImpl.class);
			QueryPos qPos = QueryPos.getInstance(q);			
			qPos.add(groupId);
			qPos.add(groupId);
			qPos.add(actId);
			qPos.add(p2pActId);
							
			p2pActivities = (List<P2pActivity>)q.list();
			
			
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
		return p2pActivities;
		
	}
	

	
	private String replaceLimit(String sql, int start, int end){
		if(start < 0 && end < 0){
			sql = sql.replace("LIMIT [$START$], [$END$]", "");
		}else{
			sql = sql.replace("[$START$]", String.valueOf(start));
			sql = sql.replace("[$END$]", String.valueOf(start+end));
		}
		return sql;
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
