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
 * This class is a wrapper for {@link UserCompetenceService}.
 * </p>
 *
 * @author    TLS
 * @see       UserCompetenceService
 * @generated
 */
public class UserCompetenceServiceWrapper implements UserCompetenceService,
	ServiceWrapper<UserCompetenceService> {
	public UserCompetenceServiceWrapper(
		UserCompetenceService userCompetenceService) {
		_userCompetenceService = userCompetenceService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _userCompetenceService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_userCompetenceService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _userCompetenceService.invokeMethod(name, parameterTypes,
			arguments);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public UserCompetenceService getWrappedUserCompetenceService() {
		return _userCompetenceService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedUserCompetenceService(
		UserCompetenceService userCompetenceService) {
		_userCompetenceService = userCompetenceService;
	}

	public UserCompetenceService getWrappedService() {
		return _userCompetenceService;
	}

	public void setWrappedService(UserCompetenceService userCompetenceService) {
		_userCompetenceService = userCompetenceService;
	}

	private UserCompetenceService _userCompetenceService;
}