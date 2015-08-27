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
 * This class is a wrapper for {@link LearningActivityTryLocalService}.
 * </p>
 *
 * @author    TLS
 * @see       LearningActivityTryLocalService
 * @generated
 */
public class LearningActivityTryLocalServiceWrapper
	implements LearningActivityTryLocalService,
		ServiceWrapper<LearningActivityTryLocalService> {
	public LearningActivityTryLocalServiceWrapper(
		LearningActivityTryLocalService learningActivityTryLocalService) {
		_learningActivityTryLocalService = learningActivityTryLocalService;
	}

	/**
	* Adds the learning activity try to the database. Also notifies the appropriate model listeners.
	*
	* @param learningActivityTry the learning activity try
	* @return the learning activity try that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry addLearningActivityTry(
		com.liferay.lms.model.LearningActivityTry learningActivityTry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.addLearningActivityTry(learningActivityTry);
	}

	/**
	* Creates a new learning activity try with the primary key. Does not add the learning activity try to the database.
	*
	* @param latId the primary key for the new learning activity try
	* @return the new learning activity try
	*/
	public com.liferay.lms.model.LearningActivityTry createLearningActivityTry(
		long latId) {
		return _learningActivityTryLocalService.createLearningActivityTry(latId);
	}

	/**
	* Deletes the learning activity try with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param latId the primary key of the learning activity try
	* @return the learning activity try that was removed
	* @throws PortalException if a learning activity try with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry deleteLearningActivityTry(
		long latId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.deleteLearningActivityTry(latId);
	}

	/**
	* Deletes the learning activity try from the database. Also notifies the appropriate model listeners.
	*
	* @param learningActivityTry the learning activity try
	* @return the learning activity try that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry deleteLearningActivityTry(
		com.liferay.lms.model.LearningActivityTry learningActivityTry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.deleteLearningActivityTry(learningActivityTry);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _learningActivityTryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.lms.model.LearningActivityTry fetchLearningActivityTry(
		long latId) throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.fetchLearningActivityTry(latId);
	}

	/**
	* Returns the learning activity try with the primary key.
	*
	* @param latId the primary key of the learning activity try
	* @return the learning activity try
	* @throws PortalException if a learning activity try with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry getLearningActivityTry(
		long latId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.getLearningActivityTry(latId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the learning activity tries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of learning activity tries
	* @param end the upper bound of the range of learning activity tries (not inclusive)
	* @return the range of learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityTry> getLearningActivityTries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.getLearningActivityTries(start,
			end);
	}

	/**
	* Returns the number of learning activity tries.
	*
	* @return the number of learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public int getLearningActivityTriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.getLearningActivityTriesCount();
	}

	/**
	* Updates the learning activity try in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param learningActivityTry the learning activity try
	* @return the learning activity try that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry updateLearningActivityTry(
		com.liferay.lms.model.LearningActivityTry learningActivityTry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.updateLearningActivityTry(learningActivityTry);
	}

	/**
	* Updates the learning activity try in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param learningActivityTry the learning activity try
	* @param merge whether to merge the learning activity try with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the learning activity try that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry updateLearningActivityTry(
		com.liferay.lms.model.LearningActivityTry learningActivityTry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.updateLearningActivityTry(learningActivityTry,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _learningActivityTryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_learningActivityTryLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _learningActivityTryLocalService.invokeMethod(name,
			parameterTypes, arguments);
	}

	public long getLearningActivityTryByActUserCount(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.getLearningActivityTryByActUserCount(actId,
			userId);
	}

	public void deleteUserTries(long actId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_learningActivityTryLocalService.deleteUserTries(actId, userId);
	}

	public java.util.List<com.liferay.lms.model.LearningActivityTry> getLearningActivityTryByActUser(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.getLearningActivityTryByActUser(actId,
			userId);
	}

	public com.liferay.lms.model.LearningActivityTry createLearningActivityTry(
		long actId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.createLearningActivityTry(actId,
			serviceContext);
	}

	public java.util.List<com.liferay.portal.model.User> getUsersByLearningActivity(
		long actId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.getUsersByLearningActivity(actId);
	}

	public com.liferay.lms.model.LearningActivityTry getLastLearningActivityTryByActivityAndUser(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.getLastLearningActivityTryByActivityAndUser(actId,
			userId);
	}

	public com.liferay.lms.model.LearningActivityTry createOrDuplicateLast(
		long actId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.createOrDuplicateLast(actId,
			serviceContext);
	}

	public com.liferay.lms.model.LearningActivityTry getLearningActivityTryNotFinishedByActUser(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.getLearningActivityTryNotFinishedByActUser(actId,
			userId);
	}

	public int getTriesCountByActivityAndUser(long actId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.getTriesCountByActivityAndUser(actId,
			userId);
	}

	public int getTriesCountByActivity(long actId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.getTriesCountByActivity(actId);
	}

	public java.util.HashMap<java.lang.Long, java.lang.Long> getMapTryResultData(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryLocalService.getMapTryResultData(actId,
			userId);
	}

	public boolean canUserDoANewTry(long actId, long userId)
		throws java.lang.Exception {
		return _learningActivityTryLocalService.canUserDoANewTry(actId, userId);
	}

	public boolean areThereTriesNotFromEditors(
		com.liferay.lms.model.LearningActivity activity)
		throws java.lang.Exception {
		return _learningActivityTryLocalService.areThereTriesNotFromEditors(activity);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public LearningActivityTryLocalService getWrappedLearningActivityTryLocalService() {
		return _learningActivityTryLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedLearningActivityTryLocalService(
		LearningActivityTryLocalService learningActivityTryLocalService) {
		_learningActivityTryLocalService = learningActivityTryLocalService;
	}

	public LearningActivityTryLocalService getWrappedService() {
		return _learningActivityTryLocalService;
	}

	public void setWrappedService(
		LearningActivityTryLocalService learningActivityTryLocalService) {
		_learningActivityTryLocalService = learningActivityTryLocalService;
	}

	private LearningActivityTryLocalService _learningActivityTryLocalService;
}