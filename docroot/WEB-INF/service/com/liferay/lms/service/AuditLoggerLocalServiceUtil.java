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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * The utility for the audit logger local service. This utility wraps {@link com.liferay.lms.service.impl.AuditLoggerLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see AuditLoggerLocalService
 * @see com.liferay.lms.service.base.AuditLoggerLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.AuditLoggerLocalServiceImpl
 * @generated
 */
public class AuditLoggerLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.AuditLoggerLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static void audit(long companyId, long groupId,
		java.lang.String className, long classPK, long userId,
		java.lang.String action, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.audit(companyId, groupId, className, classPK, userId, action,
			extraData);
	}

	public static void audit(long companyId, long groupId,
		java.lang.String className, long classPK, long associationClassPK,
		long userId, java.lang.String action, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.audit(companyId, groupId, className, classPK, associationClassPK,
			userId, action, extraData);
	}

	public static void clearService() {
		_service = null;
	}

	public static AuditLoggerLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					AuditLoggerLocalService.class.getName());

			if (invokableLocalService instanceof AuditLoggerLocalService) {
				_service = (AuditLoggerLocalService)invokableLocalService;
			}
			else {
				_service = new AuditLoggerLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(AuditLoggerLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(AuditLoggerLocalService service) {
	}

	private static AuditLoggerLocalService _service;
}