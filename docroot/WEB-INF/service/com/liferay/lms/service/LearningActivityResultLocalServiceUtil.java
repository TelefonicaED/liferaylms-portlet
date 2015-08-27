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
import com.liferay.portal.service.InvokableLocalService;

/**
 * The utility for the learning activity result local service. This utility wraps {@link com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see LearningActivityResultLocalService
 * @see com.liferay.lms.service.base.LearningActivityResultLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl
 * @generated
 */
public class LearningActivityResultLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the learning activity result to the database. Also notifies the appropriate model listeners.
	*
	* @param learningActivityResult the learning activity result
	* @return the learning activity result that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult addLearningActivityResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addLearningActivityResult(learningActivityResult);
	}

	/**
	* Creates a new learning activity result with the primary key. Does not add the learning activity result to the database.
	*
	* @param larId the primary key for the new learning activity result
	* @return the new learning activity result
	*/
	public static com.liferay.lms.model.LearningActivityResult createLearningActivityResult(
		long larId) {
		return getService().createLearningActivityResult(larId);
	}

	/**
	* Deletes the learning activity result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param larId the primary key of the learning activity result
	* @return the learning activity result that was removed
	* @throws PortalException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult deleteLearningActivityResult(
		long larId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteLearningActivityResult(larId);
	}

	/**
	* Deletes the learning activity result from the database. Also notifies the appropriate model listeners.
	*
	* @param learningActivityResult the learning activity result
	* @return the learning activity result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult deleteLearningActivityResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteLearningActivityResult(learningActivityResult);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.lms.model.LearningActivityResult fetchLearningActivityResult(
		long larId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchLearningActivityResult(larId);
	}

	/**
	* Returns the learning activity result with the primary key.
	*
	* @param larId the primary key of the learning activity result
	* @return the learning activity result
	* @throws PortalException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult getLearningActivityResult(
		long larId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLearningActivityResult(larId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> getLearningActivityResults(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLearningActivityResults(start, end);
	}

	/**
	* Returns the number of learning activity results.
	*
	* @return the number of learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static int getLearningActivityResultsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLearningActivityResultsCount();
	}

	/**
	* Updates the learning activity result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param learningActivityResult the learning activity result
	* @return the learning activity result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult updateLearningActivityResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLearningActivityResult(learningActivityResult);
	}

	/**
	* Updates the learning activity result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param learningActivityResult the learning activity result
	* @param merge whether to merge the learning activity result with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the learning activity result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult updateLearningActivityResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLearningActivityResult(learningActivityResult, merge);
	}

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

	public static com.liferay.lms.model.LearningActivityResult update(
		com.liferay.lms.model.LearningActivityTry learningActivityTry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().update(learningActivityTry);
	}

	public static com.liferay.lms.model.LearningActivityResult update(
		long latId, long result, java.lang.String tryResultData, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().update(latId, result, tryResultData, userId);
	}

	public static com.liferay.lms.model.LearningActivityResult update(
		long latId, java.lang.String tryResultData, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().update(latId, tryResultData, userId);
	}

	public static com.liferay.lms.model.LearningActivityResult update(
		long latId, java.lang.String tryResultData,
		java.lang.String imsmanifest, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().update(latId, tryResultData, imsmanifest, userId);
	}

	public static boolean existsLearningActivityResult(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().existsLearningActivityResult(actId, userId);
	}

	public static boolean userPassed(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().userPassed(actId, userId);
	}

	public static long countPassed(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countPassed(actId);
	}

	public static long countPassedOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countPassedOnlyStudents(actId, companyId,
			courseGropupCreatedId, passed);
	}

	public static long countNotPassed(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countNotPassed(actId);
	}

	public static long countNotPassedOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countNotPassedOnlyStudents(actId, companyId,
			courseGropupCreatedId);
	}

	public static java.lang.Double avgResult(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().avgResult(actId);
	}

	public static java.lang.Double avgResultOnlyStudents(long actId,
		long companyId, long courseGropupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .avgResultOnlyStudents(actId, companyId,
			courseGropupCreatedId);
	}

	public static long countStarted(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countStarted(actId);
	}

	public static long countStartedOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStartedOnlyStudents(actId, companyId,
			courseGropupCreatedId);
	}

	public static double triesPerUser(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().triesPerUser(actId);
	}

	public static double triesPerUserOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .triesPerUserOnlyStudents(actId, companyId,
			courseGropupCreatedId);
	}

	public static com.liferay.lms.model.LearningActivityResult getByActIdAndUserId(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getByActIdAndUserId(actId, userId);
	}

	public static java.util.Date getLastEndDateByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLastEndDateByUserId(userId);
	}

	public static java.util.List<com.liferay.lms.model.LearningActivityResult> getByActId(
		long actId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getByActId(actId);
	}

	public static java.lang.String translateResult(java.util.Locale locale,
		double result, long groupId) {
		return getService().translateResult(locale, result, groupId);
	}

	public static void clearService() {
		_service = null;
	}

	public static LearningActivityResultLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					LearningActivityResultLocalService.class.getName());

			if (invokableLocalService instanceof LearningActivityResultLocalService) {
				_service = (LearningActivityResultLocalService)invokableLocalService;
			}
			else {
				_service = new LearningActivityResultLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(LearningActivityResultLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(LearningActivityResultLocalService service) {
	}

	private static LearningActivityResultLocalService _service;
}