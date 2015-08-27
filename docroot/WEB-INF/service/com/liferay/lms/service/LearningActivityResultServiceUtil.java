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
import com.liferay.portal.service.InvokableService;

/**
 * The utility for the learning activity result remote service. This utility wraps {@link com.liferay.lms.service.impl.LearningActivityResultServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author TLS
 * @see LearningActivityResultService
 * @see com.liferay.lms.service.base.LearningActivityResultServiceBaseImpl
 * @see com.liferay.lms.service.impl.LearningActivityResultServiceImpl
 * @generated
 */
public class LearningActivityResultServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.LearningActivityResultServiceImpl} and rerun ServiceBuilder to regenerate this class.
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

	public static com.liferay.lms.model.LearningActivityResult getByActId(
		long actId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getByActId(actId);
	}

	public static com.liferay.lms.model.LearningActivityResult getByActIdAndUser(
		long actId, java.lang.String login)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getByActIdAndUser(actId, login);
	}

	public static boolean userPassed(long actId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().userPassed(actId);
	}

	public static boolean userLoginPassed(long actId, java.lang.String login)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().userLoginPassed(actId, login);
	}

	public static com.liferay.lms.model.LearningActivityResult update(
		long latId, long result, java.lang.String tryResultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().update(latId, result, tryResultData);
	}

	public static com.liferay.lms.model.LearningActivityResult update(
		long latId, java.lang.String tryResultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().update(latId, tryResultData);
	}

	public static com.liferay.lms.model.LearningActivityResult update(
		long latId, java.lang.String tryResultData, java.lang.String imsmanifest)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().update(latId, tryResultData, imsmanifest);
	}

	public static com.liferay.lms.model.LearningActivityResult updateFinishTry(
		long latId, java.lang.String tryResultData, java.lang.String imsmanifest)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateFinishTry(latId, tryResultData, imsmanifest);
	}

	public static void clearService() {
		_service = null;
	}

	public static LearningActivityResultService getService() {
		if (_service == null) {
			InvokableService invokableService = (InvokableService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					LearningActivityResultService.class.getName());

			if (invokableService instanceof LearningActivityResultService) {
				_service = (LearningActivityResultService)invokableService;
			}
			else {
				_service = new LearningActivityResultServiceClp(invokableService);
			}

			ReferenceRegistry.registerReference(LearningActivityResultServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(LearningActivityResultService service) {
	}

	private static LearningActivityResultService _service;
}