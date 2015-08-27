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
 * This class is a wrapper for {@link LearningActivityResultService}.
 * </p>
 *
 * @author    TLS
 * @see       LearningActivityResultService
 * @generated
 */
public class LearningActivityResultServiceWrapper
	implements LearningActivityResultService,
		ServiceWrapper<LearningActivityResultService> {
	public LearningActivityResultServiceWrapper(
		LearningActivityResultService learningActivityResultService) {
		_learningActivityResultService = learningActivityResultService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _learningActivityResultService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_learningActivityResultService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _learningActivityResultService.invokeMethod(name,
			parameterTypes, arguments);
	}

	public com.liferay.lms.model.LearningActivityResult getByActId(long actId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultService.getByActId(actId);
	}

	public com.liferay.lms.model.LearningActivityResult getByActIdAndUser(
		long actId, java.lang.String login)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultService.getByActIdAndUser(actId, login);
	}

	public boolean userPassed(long actId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultService.userPassed(actId);
	}

	public boolean userLoginPassed(long actId, java.lang.String login)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultService.userLoginPassed(actId, login);
	}

	public com.liferay.lms.model.LearningActivityResult update(long latId,
		long result, java.lang.String tryResultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultService.update(latId, result,
			tryResultData);
	}

	public com.liferay.lms.model.LearningActivityResult update(long latId,
		java.lang.String tryResultData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultService.update(latId, tryResultData);
	}

	public com.liferay.lms.model.LearningActivityResult update(long latId,
		java.lang.String tryResultData, java.lang.String imsmanifest)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultService.update(latId, tryResultData,
			imsmanifest);
	}

	public com.liferay.lms.model.LearningActivityResult updateFinishTry(
		long latId, java.lang.String tryResultData, java.lang.String imsmanifest)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultService.updateFinishTry(latId,
			tryResultData, imsmanifest);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public LearningActivityResultService getWrappedLearningActivityResultService() {
		return _learningActivityResultService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedLearningActivityResultService(
		LearningActivityResultService learningActivityResultService) {
		_learningActivityResultService = learningActivityResultService;
	}

	public LearningActivityResultService getWrappedService() {
		return _learningActivityResultService;
	}

	public void setWrappedService(
		LearningActivityResultService learningActivityResultService) {
		_learningActivityResultService = learningActivityResultService;
	}

	private LearningActivityResultService _learningActivityResultService;
}