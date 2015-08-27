package com.liferay.lms.auditing;

import com.liferay.portal.kernel.exception.SystemException;

public class NullAuditingLog implements AuditingLog {

	@Override
	public void audit(long companyId, long groupId, String className,
			long classPK, long associationClassPK, long userId, String action,
			String extraData) throws SystemException {
		// TODO Auto-generated method stub
		
	}

}
