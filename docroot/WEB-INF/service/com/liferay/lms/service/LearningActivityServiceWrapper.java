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
 * This class is a wrapper for {@link LearningActivityService}.
 * </p>
 *
 * @author    TLS
 * @see       LearningActivityService
 * @generated
 */
public class LearningActivityServiceWrapper implements LearningActivityService,
	ServiceWrapper<LearningActivityService> {
	public LearningActivityServiceWrapper(
		LearningActivityService learningActivityService) {
		_learningActivityService = learningActivityService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _learningActivityService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_learningActivityService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _learningActivityService.invokeMethod(name, parameterTypes,
			arguments);
	}

	public java.util.List<com.liferay.lms.model.LearningActivity> getLearningActivitiesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityService.getLearningActivitiesOfGroup(groupId);
	}

	public java.util.List<com.liferay.lms.model.LearningActivity> getLearningActivitiesOfModule(
		long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityService.getLearningActivitiesOfModule(moduleId);
	}

	public void deleteLearningactivity(
		com.liferay.lms.model.LearningActivity lernact)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_learningActivityService.deleteLearningactivity(lernact);
	}

	public void deleteLearningactivity(long actId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_learningActivityService.deleteLearningactivity(actId);
	}

	public com.liferay.lms.model.LearningActivity getLearningActivity(
		long actId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityService.getLearningActivity(actId);
	}

	public com.liferay.lms.model.LearningActivity addLearningActivity(
		java.lang.String title, java.lang.String description,
		java.util.Date createDate, java.util.Date startDate,
		java.util.Date endDate, int typeId, long tries, int passpuntuation,
		long moduleId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityService.addLearningActivity(title, description,
			createDate, startDate, endDate, typeId, tries, passpuntuation,
			moduleId, serviceContext);
	}

	public com.liferay.lms.model.LearningActivity modLearningActivity(
		com.liferay.lms.model.LearningActivity lernact,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityService.modLearningActivity(lernact,
			serviceContext);
	}

	public com.liferay.lms.model.LearningActivity modLearningActivity(
		com.liferay.lms.model.LearningActivity lernact)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityService.modLearningActivity(lernact);
	}

	public com.liferay.lms.model.LearningActivity modLearningActivity(
		long actId, java.lang.String title, java.lang.String description,
		java.util.Date createDate, java.util.Date startDate,
		java.util.Date endDate, int typeId, long tries, int passpuntuation,
		long moduleId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityService.modLearningActivity(actId, title,
			description, createDate, startDate, endDate, typeId, tries,
			passpuntuation, moduleId, serviceContext);
	}

	public boolean isLocked(long actId) throws java.lang.Exception {
		return _learningActivityService.isLocked(actId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public LearningActivityService getWrappedLearningActivityService() {
		return _learningActivityService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedLearningActivityService(
		LearningActivityService learningActivityService) {
		_learningActivityService = learningActivityService;
	}

	public LearningActivityService getWrappedService() {
		return _learningActivityService;
	}

	public void setWrappedService(
		LearningActivityService learningActivityService) {
		_learningActivityService = learningActivityService;
	}

	private LearningActivityService _learningActivityService;
}