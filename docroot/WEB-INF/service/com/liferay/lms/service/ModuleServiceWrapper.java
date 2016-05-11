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
 * This class is a wrapper for {@link ModuleService}.
 * </p>
 *
 * @author    TLS
 * @see       ModuleService
 * @generated
 */
public class ModuleServiceWrapper implements ModuleService,
	ServiceWrapper<ModuleService> {
	public ModuleServiceWrapper(ModuleService moduleService) {
		_moduleService = moduleService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _moduleService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_moduleService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _moduleService.invokeMethod(name, parameterTypes, arguments);
	}

	public java.util.List<com.liferay.lms.model.Module> findAllInGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _moduleService.findAllInGroup(groupId);
	}

	public java.util.List<com.liferay.lms.model.Module> findAllInCourse(
		long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _moduleService.findAllInCourse(courseId);
	}

	public boolean isLocked(long moduleId) throws java.lang.Exception {
		return _moduleService.isLocked(moduleId);
	}

	public boolean PassedByMe(long moduleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _moduleService.PassedByMe(moduleId);
	}

	public boolean isUserPassed(long moduleId, java.lang.String login)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _moduleService.isUserPassed(moduleId, login);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ModuleService getWrappedModuleService() {
		return _moduleService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedModuleService(ModuleService moduleService) {
		_moduleService = moduleService;
	}

	public ModuleService getWrappedService() {
		return _moduleService;
	}

	public void setWrappedService(ModuleService moduleService) {
		_moduleService = moduleService;
	}

	private ModuleService _moduleService;
}