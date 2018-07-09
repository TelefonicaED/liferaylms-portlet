package com.liferay.lms.auditing;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

public class AuditingLogFactory 
{
	static Log log = LogFactoryUtil.getLog(AuditingLogFactory.class);
	
	static AuditingLog auditLog;
	public static AuditingLog getAuditLog() throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		if(auditLog==null)
		{
			Class<?> clase= null;
			if(PropsUtil.get("audit.implementation")!=null){
				clase=Class.forName(PropsUtil.get("audit.implementation"));
			}else{
				clase=Class.forName("com.liferay.lms.auditing.NullAuditingLog");
			}
			auditLog=(AuditingLog)clase.newInstance();
		}
		return auditLog;
	}
	
	public static void audit(long companyId, long groupId, String className,long classPK,
			long userId, String action, String extraData)
			throws SystemException {
		audit(companyId, groupId, className, classPK, GetterUtil.DEFAULT_LONG, 
				userId, action, extraData);
	}
	
	public static void audit(long companyId, long groupId, String className,long classPK,long associationClassPK, 
			long userId, String action, String extraData)
			throws SystemException {
		try{
			if(log.isDebugEnabled())log.debug("audit:"+className+"::"+classPK);
			getAuditLog().audit(companyId, groupId, className, classPK, associationClassPK, userId, action, extraData);
		}catch(ClassNotFoundException cnfe){
			if(log.isDebugEnabled())cnfe.printStackTrace();
		}catch(IllegalAccessException iae){
			if(log.isDebugEnabled())iae.printStackTrace();
		}catch(InstantiationException ie){
			if(log.isDebugEnabled())ie.printStackTrace();
		}
	}
}
