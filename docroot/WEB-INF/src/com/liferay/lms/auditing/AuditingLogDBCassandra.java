package com.liferay.lms.auditing;

import java.text.ParseException;
import java.util.Date;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.cassandra.ExtConexionCassandra;
import com.liferay.lms.model.AuditEntry;
import com.liferay.lms.service.AuditEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
 


public class AuditingLogDBCassandra implements AuditingLog {
	
	Session  sesion =  ExtConexionCassandra.session;
    // INSERT  
	  
	
	
	PreparedStatement insertStatement = sesion.prepare(
		      "INSERT INTO liferay.auditentry " +
		      "(auditid,action,association, auditdate,classname,classpk, companyid,extradata, groupid, userid) " +
		      "VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?);");			
	@Override
	public void audit(long companyId, long groupId, String className,long classPK,long associationClassPK, 
			long userId, String action, String extraData) throws SystemException 
	{	
		

		AuditEntry auditEntry= AuditEntryLocalServiceUtil.createAuditEntry(CounterLocalServiceUtil.increment(AuditEntry.class.getName()))  ;
		BoundStatement boundStatement = new BoundStatement(insertStatement);
		try{
			sesion.execute(boundStatement.bind(
					auditEntry.getAuditId(),
					action,
					associationClassPK,
					new Date( System.currentTimeMillis()),
					className,
					classPK,
					companyId,
					extraData,
					groupId,
					userId
					));				
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	//	AuditEntryLocalServiceUtil.addAuditEntry(companyId, groupId, className, classPK, associationClassPK, userId, action, extraData);
	}

}
