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
 * This class is a wrapper for {@link SCORMContentService}.
 * </p>
 *
 * @author    TLS
 * @see       SCORMContentService
 * @generated
 */
public class SCORMContentServiceWrapper implements SCORMContentService,
	ServiceWrapper<SCORMContentService> {
	public SCORMContentServiceWrapper(SCORMContentService scormContentService) {
		_scormContentService = scormContentService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _scormContentService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_scormContentService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _scormContentService.invokeMethod(name, parameterTypes, arguments);
	}

	public java.util.List<com.liferay.lms.model.SCORMContent> getSCORMContentOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scormContentService.getSCORMContentOfGroup(groupId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SCORMContentService getWrappedSCORMContentService() {
		return _scormContentService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSCORMContentService(
		SCORMContentService scormContentService) {
		_scormContentService = scormContentService;
	}

	public SCORMContentService getWrappedService() {
		return _scormContentService;
	}

	public void setWrappedService(SCORMContentService scormContentService) {
		_scormContentService = scormContentService;
	}

	private SCORMContentService _scormContentService;
}