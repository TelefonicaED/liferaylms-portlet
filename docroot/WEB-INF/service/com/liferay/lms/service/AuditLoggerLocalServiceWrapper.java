/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lms.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link AuditLoggerLocalService}.
 * </p>
 *
 * @author    TLS
 * @see       AuditLoggerLocalService
 * @generated
 */
public class AuditLoggerLocalServiceWrapper implements AuditLoggerLocalService,
	ServiceWrapper<AuditLoggerLocalService> {
	public AuditLoggerLocalServiceWrapper(
		AuditLoggerLocalService auditLoggerLocalService) {
		_auditLoggerLocalService = auditLoggerLocalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _auditLoggerLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_auditLoggerLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _auditLoggerLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	public void audit(long companyId, long groupId, java.lang.String className,
		long classPK, long userId, java.lang.String action,
		java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		_auditLoggerLocalService.audit(companyId, groupId, className, classPK,
			userId, action, extraData);
	}

	public void audit(long companyId, long groupId, java.lang.String className,
		long classPK, long associationClassPK, long userId,
		java.lang.String action, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		_auditLoggerLocalService.audit(companyId, groupId, className, classPK,
			associationClassPK, userId, action, extraData);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public AuditLoggerLocalService getWrappedAuditLoggerLocalService() {
		return _auditLoggerLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedAuditLoggerLocalService(
		AuditLoggerLocalService auditLoggerLocalService) {
		_auditLoggerLocalService = auditLoggerLocalService;
	}

	public AuditLoggerLocalService getWrappedService() {
		return _auditLoggerLocalService;
	}

	public void setWrappedService(
		AuditLoggerLocalService auditLoggerLocalService) {
		_auditLoggerLocalService = auditLoggerLocalService;
	}

	private AuditLoggerLocalService _auditLoggerLocalService;
}