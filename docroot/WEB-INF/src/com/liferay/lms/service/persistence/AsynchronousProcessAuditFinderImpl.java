package com.liferay.lms.service.persistence;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.liferay.lms.model.AsynchronousProcessAudit;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.impl.AsynchronousProcessAuditImpl;
import com.liferay.lms.model.impl.CourseImpl;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

public class AsynchronousProcessAuditFinderImpl extends BasePersistenceImpl<CourseResult> implements AsynchronousProcessAuditFinder{
	
	Log log = LogFactoryUtil.getLog(AsynchronousProcessAuditFinderImpl.class);
	

	public static final String GET_DISTINCT_TYPES =
			AsynchronousProcessAuditFinder.class.getName() +
		        ".getDistinctTypes";
	public static final String GET_BY_COMPANY_CLASSNAME_CREATE_DATE =
			AsynchronousProcessAuditFinder.class.getName() +
		        ".getByCompanyIdClassNameAndDate";
	public static final String COUNT_BY_COMPANY_CLASSNAME_CREATE_DATE =
			AsynchronousProcessAuditFinder.class.getName() +
		        ".countByCompanyIdClassNameAndDate";
	
	
	public List<String> getDistinctTypes(long companyId){
		Session session = null;
		List<String> classnames = new ArrayList<String>();
		try{
			session = openSession();
			String sql = CustomSQLUtil.get(GET_DISTINCT_TYPES);
			SQLQuery q = session.createSQLQuery(sql);
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(companyId);
			classnames = (List<String>)q.list();
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	    	closeSession(session);
	    }
	
	    return classnames;
	}

 	
	public List<AsynchronousProcessAudit> getByCompanyIdClassNameIdCreateDate(long companyId, String type,long userId, Date startDate, Date endDate, int start, int end){
		Session session = null;
		List<AsynchronousProcessAudit> asynchronousProcessAudits = new ArrayList<AsynchronousProcessAudit>();
		try{
			session = openSession();
			String sql = CustomSQLUtil.get(GET_BY_COMPANY_CLASSNAME_CREATE_DATE);
			//Logica para incluir select multiples en la consulta de procesos asincronos
			if(Validator.isNull(type)){
				sql = sql.replace("AND asy.type_ IN (?)", "");
			}else{
				if(type.indexOf(",")>=0){
					String bffcopy="";
					for (String s : type.split(",")) { bffcopy = bffcopy + ",?"; }
					String sqlReplace = "AND asy.type_ IN ("+bffcopy.replaceFirst(",","")+")";
					sql = sql.replace("AND asy.type_ IN (?)", sqlReplace);
				}
			}
			if(Validator.equals(userId, 0L)){
				sql = sql.replace("AND asy.userId = ?", "");
			}
			
			if(start < 0 && end < 0){
				sql = sql.replace("LIMIT [$START$], [$END$]", "");
			}else{
				sql = sql.replace("[$START$]", String.valueOf(start));
				sql = sql.replace("[$END$]", String.valueOf(end-start));
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			
			if(Validator.isNotNull(startDate)){
				sql = StringUtil.replace(sql, "[$STARTDATE$]",sdf.format(startDate));
			}else{
				sql = StringUtil.replace(sql, "AND asy.createDate > '[$STARTDATE$]'", "");
			}
			
			if(Validator.isNotNull(endDate)){
				sql = StringUtil.replace(sql, "[$ENDDATE$]",sdf.format(endDate));
			}else{
				sql = StringUtil.replace(sql, "AND asy.createDate <= '[$ENDDATE$]'", "");
			}
			
			
			log.debug("SQL "+sql);
			
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Lms_AsynchronousProcessAudit", AsynchronousProcessAuditImpl.class);
			if(log.isDebugEnabled()){
				log.debug("--CompanyId: "+companyId);
				if(Validator.isNotNull(type)){
					log.debug("--TypeId: "+type);
				}
				log.debug("--userId "+userId);
				log.debug("--Start Date: "+startDate);
				log.debug("--EndDate: "+endDate);
			}
			QueryPos qPos = QueryPos.getInstance(q);
			
			qPos.add(companyId);
			if(Validator.isNotNull(type)){
				if(type.indexOf(",")>=0){
					String[] split = type.split(",");
					for (String string : split) {
						qPos.add(string);
					}
				}
				else{
					qPos.add(type);
				}
			}
			if(!Validator.equals(userId, 0L)){
				qPos.add(userId);	
			}
			
			
			asynchronousProcessAudits = (List<AsynchronousProcessAudit>)q.list();
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	    	closeSession(session);
	    }
	
	    return asynchronousProcessAudits;
	}

	
	
	public int countByCompanyIdClassNameIdCreateDate(long companyId, String type,long userId, Date startDate, Date endDate){
		Session session = null;
		int asynchronousProcessAudits = 0;
		try{
			session = openSession();
			String sql = CustomSQLUtil.get(COUNT_BY_COMPANY_CLASSNAME_CREATE_DATE);
			if(Validator.isNull(type)){
				sql = sql.replace("AND asy.type_ IN (?)", "");
			}else{
				if(type.indexOf(",")>=0){
					String bffcopy="";
					for (String s : type.split(",")) { bffcopy = bffcopy + ",?"; }
					String sqlReplace = "("+bffcopy.replaceFirst(",","")+")";
					sql = sql.replace("(?)", sqlReplace);
				}
			}
			if(Validator.equals(userId, 0L)){
				sql = sql.replace("AND asy.userId = ?", "");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(Validator.isNotNull(startDate)){
				sql = StringUtil.replace(sql, "[$STARTDATE$]",sdf.format(startDate));
			}else{
				sql = StringUtil.replace(sql, "AND asy.createDate > '[$STARTDATE$]'", "");
			} 
			
			if(Validator.isNotNull(endDate)){
				sql = StringUtil.replace(sql, "[$ENDDATE$]",sdf.format(endDate));
			}else{
				sql = StringUtil.replace(sql, "AND asy.createDate <= '[$ENDDATE$]'", "");
			}
			
			log.debug("SQL: "+sql);
			if(log.isDebugEnabled()){
				log.debug("--CompanyId: "+companyId);
				if(Validator.isNotNull(type)){
					log.debug("--Type: "+type);
				}
				log.debug("--Start Date: "+startDate);
				log.debug("--EndDate: "+endDate);
			}
			
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("COUNT_VALUE", Type.LONG);
			QueryPos qPos = QueryPos.getInstance(q);
			
			qPos.add(companyId);
			if(Validator.isNotNull(type)){
				if(type.indexOf(",")>=0){
					String[] split = type.split(",");
					for (String string : split) {
						qPos.add(string);
					}
				}
				else{
					qPos.add(type);
				}
			}
			if(!Validator.equals(userId, 0L)){
				qPos.add(userId);	
			}
			
			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					asynchronousProcessAudits = count.intValue();
				}
			}
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	    	closeSession(session);
	    }
	
	    return asynchronousProcessAudits;
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
