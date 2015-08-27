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
 * This class is a wrapper for {@link LearningActivityResultLocalService}.
 * </p>
 *
 * @author    TLS
 * @see       LearningActivityResultLocalService
 * @generated
 */
public class LearningActivityResultLocalServiceWrapper
	implements LearningActivityResultLocalService,
		ServiceWrapper<LearningActivityResultLocalService> {
	public LearningActivityResultLocalServiceWrapper(
		LearningActivityResultLocalService learningActivityResultLocalService) {
		_learningActivityResultLocalService = learningActivityResultLocalService;
	}

	/**
	* Adds the learning activity result to the database. Also notifies the appropriate model listeners.
	*
	* @param learningActivityResult the learning activity result
	* @return the learning activity result that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult addLearningActivityResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.addLearningActivityResult(learningActivityResult);
	}

	/**
	* Creates a new learning activity result with the primary key. Does not add the learning activity result to the database.
	*
	* @param larId the primary key for the new learning activity result
	* @return the new learning activity result
	*/
	public com.liferay.lms.model.LearningActivityResult createLearningActivityResult(
		long larId) {
		return _learningActivityResultLocalService.createLearningActivityResult(larId);
	}

	/**
	* Deletes the learning activity result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param larId the primary key of the learning activity result
	* @return the learning activity result that was removed
	* @throws PortalException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult deleteLearningActivityResult(
		long larId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.deleteLearningActivityResult(larId);
	}

	/**
	* Deletes the learning activity result from the database. Also notifies the appropriate model listeners.
	*
	* @param learningActivityResult the learning activity result
	* @return the learning activity result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult deleteLearningActivityResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.deleteLearningActivityResult(learningActivityResult);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _learningActivityResultLocalService.dynamicQuery();
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
		return _learningActivityResultLocalService.dynamicQuery(dynamicQuery);
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
		return _learningActivityResultLocalService.dynamicQuery(dynamicQuery,
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
		return _learningActivityResultLocalService.dynamicQuery(dynamicQuery,
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
		return _learningActivityResultLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.lms.model.LearningActivityResult fetchLearningActivityResult(
		long larId) throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.fetchLearningActivityResult(larId);
	}

	/**
	* Returns the learning activity result with the primary key.
	*
	* @param larId the primary key of the learning activity result
	* @return the learning activity result
	* @throws PortalException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult getLearningActivityResult(
		long larId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.getLearningActivityResult(larId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the learning activity results.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of learning activity results
	* @param end the upper bound of the range of learning activity results (not inclusive)
	* @return the range of learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> getLearningActivityResults(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.getLearningActivityResults(start,
			end);
	}

	/**
	* Returns the number of learning activity results.
	*
	* @return the number of learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public int getLearningActivityResultsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.getLearningActivityResultsCount();
	}

	/**
	* Updates the learning activity result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param learningActivityResult the learning activity result
	* @return the learning activity result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult updateLearningActivityResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.updateLearningActivityResult(learningActivityResult);
	}

	/**
	* Updates the learning activity result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param learningActivityResult the learning activity result
	* @param merge whether to merge the learning activity result with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the learning activity result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult updateLearningActivityResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.updateLearningActivityResult(learningActivityResult,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _learningActivityResultLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_learningActivityResultLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _learningActivityResultLocalService.invokeMethod(name,
			parameterTypes, arguments);
	}

	public com.liferay.lms.model.LearningActivityResult update(
		com.liferay.lms.model.LearningActivityTry learningActivityTry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.update(learningActivityTry);
	}

	public com.liferay.lms.model.LearningActivityResult update(long latId,
		long result, java.lang.String tryResultData, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.update(latId, result,
			tryResultData, userId);
	}

	public com.liferay.lms.model.LearningActivityResult update(long latId,
		java.lang.String tryResultData, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.update(latId, tryResultData,
			userId);
	}

	public com.liferay.lms.model.LearningActivityResult update(long latId,
		java.lang.String tryResultData, java.lang.String imsmanifest,
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.update(latId, tryResultData,
			imsmanifest, userId);
	}

	public boolean existsLearningActivityResult(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.existsLearningActivityResult(actId,
			userId);
	}

	public boolean userPassed(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.userPassed(actId, userId);
	}

	public long countPassed(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.countPassed(actId);
	}

	public long countPassedOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.countPassedOnlyStudents(actId,
			companyId, courseGropupCreatedId, passed);
	}

	public long countNotPassed(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.countNotPassed(actId);
	}

	public long countNotPassedOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.countNotPassedOnlyStudents(actId,
			companyId, courseGropupCreatedId);
	}

	public java.lang.Double avgResult(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.avgResult(actId);
	}

	public java.lang.Double avgResultOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.avgResultOnlyStudents(actId,
			companyId, courseGropupCreatedId);
	}

	public long countStarted(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.countStarted(actId);
	}

	public long countStartedOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.countStartedOnlyStudents(actId,
			companyId, courseGropupCreatedId);
	}

	public double triesPerUser(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.triesPerUser(actId);
	}

	public double triesPerUserOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.triesPerUserOnlyStudents(actId,
			companyId, courseGropupCreatedId);
	}

	public com.liferay.lms.model.LearningActivityResult getByActIdAndUserId(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.getByActIdAndUserId(actId,
			userId);
	}

	public java.util.Date getLastEndDateByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.getLastEndDateByUserId(userId);
	}

	public java.util.List<com.liferay.lms.model.LearningActivityResult> getByActId(
		long actId) throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResultLocalService.getByActId(actId);
	}

	public java.lang.String translateResult(java.util.Locale locale,
		double result, long groupId) {
		return _learningActivityResultLocalService.translateResult(locale,
			result, groupId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public LearningActivityResultLocalService getWrappedLearningActivityResultLocalService() {
		return _learningActivityResultLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedLearningActivityResultLocalService(
		LearningActivityResultLocalService learningActivityResultLocalService) {
		_learningActivityResultLocalService = learningActivityResultLocalService;
	}

	public LearningActivityResultLocalService getWrappedService() {
		return _learningActivityResultLocalService;
	}

	public void setWrappedService(
		LearningActivityResultLocalService learningActivityResultLocalService) {
		_learningActivityResultLocalService = learningActivityResultLocalService;
	}

	private LearningActivityResultLocalService _learningActivityResultLocalService;
}