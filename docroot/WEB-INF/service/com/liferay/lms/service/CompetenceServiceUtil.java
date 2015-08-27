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
 * The utility for the competence remote service. This utility wraps {@link com.liferay.lms.service.impl.CompetenceServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author TLS
 * @see CompetenceService
 * @see com.liferay.lms.service.base.CompetenceServiceBaseImpl
 * @see com.liferay.lms.service.impl.CompetenceServiceImpl
 * @generated
 */
public class CompetenceServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.CompetenceServiceImpl} and rerun ServiceBuilder to regenerate this class.
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

	public static java.util.List<com.liferay.lms.model.Competence> getCompetencesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompetencesOfGroup(groupId);
	}

	public static java.util.List<com.liferay.lms.model.Competence> getCompetencesOfGroup(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompetencesOfGroup(groupId, start, end);
	}

	public static int getCountCompetencesOfGroup(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCountCompetencesOfGroup(groupId);
	}

	public static java.util.HashMap<java.lang.Long, com.liferay.lms.model.Competence> getCompetencesOfGroups(
		long[] groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompetencesOfGroups(groups);
	}

	public static void clearService() {
		_service = null;
	}

	public static CompetenceService getService() {
		if (_service == null) {
			InvokableService invokableService = (InvokableService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					CompetenceService.class.getName());

			if (invokableService instanceof CompetenceService) {
				_service = (CompetenceService)invokableService;
			}
			else {
				_service = new CompetenceServiceClp(invokableService);
			}

			ReferenceRegistry.registerReference(CompetenceServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(CompetenceService service) {
	}

	private static CompetenceService _service;
}