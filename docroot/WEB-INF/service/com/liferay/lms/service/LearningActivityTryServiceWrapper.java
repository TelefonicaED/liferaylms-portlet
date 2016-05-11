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
 * This class is a wrapper for {@link LearningActivityTryService}.
 * </p>
 *
 * @author    TLS
 * @see       LearningActivityTryService
 * @generated
 */
public class LearningActivityTryServiceWrapper
	implements LearningActivityTryService,
		ServiceWrapper<LearningActivityTryService> {
	public LearningActivityTryServiceWrapper(
		LearningActivityTryService learningActivityTryService) {
		_learningActivityTryService = learningActivityTryService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _learningActivityTryService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_learningActivityTryService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _learningActivityTryService.invokeMethod(name, parameterTypes,
			arguments);
	}

	public com.liferay.lms.model.LearningActivityTry createLearningActivityTry(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryService.createLearningActivityTry(actId,
			userId);
	}

	public java.util.List<com.liferay.lms.model.LearningActivityTry> getLearningActivityTries(
		long actId, java.lang.String login)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryService.getLearningActivityTries(actId, login);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public LearningActivityTryService getWrappedLearningActivityTryService() {
		return _learningActivityTryService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedLearningActivityTryService(
		LearningActivityTryService learningActivityTryService) {
		_learningActivityTryService = learningActivityTryService;
	}

	public LearningActivityTryService getWrappedService() {
		return _learningActivityTryService;
	}

	public void setWrappedService(
		LearningActivityTryService learningActivityTryService) {
		_learningActivityTryService = learningActivityTryService;
	}

	private LearningActivityTryService _learningActivityTryService;
}