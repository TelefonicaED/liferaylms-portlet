package com.liferay.lms.auditing;

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
		      "(auditid, auditdate, companyid, groupid, classname, userid, action, extradata, classpk, association) " +
		      "VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?);");			
	  

	@Override
	public void audit(long companyId, long groupId, String className,long classPK,long associationClassPK, 
			long userId, String action, String extraData) throws SystemException 
	{	
		

		AuditEntry auditEntry= AuditEntryLocalServiceUtil.createAuditEntry(CounterLocalServiceUtil.increment(AuditEntry.class.getName()))  ;
		BoundStatement boundStatement = new BoundStatement(insertStatement);
		sesion.execute(boundStatement.bind(
				auditEntry.getAuditId(),
				new java.util.Date(System.currentTimeMillis()),
				companyId,
				groupId,
				className,
				userId,
				action,
				extraData,
				classPK,
				associationClassPK
				));		
		
		
	//	AuditEntryLocalServiceUtil.addAuditEntry(companyId, groupId, className, classPK, associationClassPK, userId, action, extraData);
	}

}
