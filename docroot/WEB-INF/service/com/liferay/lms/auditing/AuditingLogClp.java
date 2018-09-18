package com.liferay.lms.auditing;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

public class AuditingLogClp  implements AuditingLog{
	private ClassLoaderProxy clp;
	
	 public AuditingLogClp(ClassLoaderProxy clp) {
		this.clp=clp;
	}
	@Override
	public void audit(long companyId, long groupId, String className,
			long classPK, long associationClassPK, long userId, String action,
			String extraData) throws SystemException {
		MethodKey methodKey=new MethodKey(clp.getClassName(), "audit", Long.TYPE,Long.TYPE,String.class,
				Long.TYPE, Long.TYPE,Long.TYPE,String.class, String.class);
		try {
			clp.invoke(new MethodHandler(methodKey, companyId,  groupId,  className,
				 classPK,  associationClassPK,  userId,  action, extraData));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
