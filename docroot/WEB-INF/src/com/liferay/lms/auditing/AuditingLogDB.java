package com.liferay.lms.auditing;

import com.liferay.lms.service.AuditEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;

public class AuditingLogDB implements AuditingLog {

	@Override
	public void audit(long companyId, long groupId, String className,long classPK,long associationClassPK, 
			long userId, String action, String extraData) throws SystemException 
	{
		
		AuditEntryLocalServiceUtil.addAuditEntry(companyId, groupId, className, classPK, associationClassPK, userId, action, extraData);
	}

}
