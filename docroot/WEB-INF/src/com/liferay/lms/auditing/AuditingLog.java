package com.liferay.lms.auditing;

import com.liferay.portal.kernel.exception.SystemException;

public interface AuditingLog {
	public  void audit(long companyId,long groupId, String className, long classPK, long associationClassPK, long userId,String action,String extraData) throws SystemException;

}
