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
 * The utility for the test answer local service. This utility wraps {@link com.liferay.lms.service.impl.TestAnswerLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see TestAnswerLocalService
 * @see com.liferay.lms.service.base.TestAnswerLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.TestAnswerLocalServiceImpl
 * @generated
 */
public class TestAnswerLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.TestAnswerLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the test answer to the database. Also notifies the appropriate model listeners.
	*
	* @param testAnswer the test answer
	* @return the test answer that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer addTestAnswer(
		com.liferay.lms.model.TestAnswer testAnswer)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addTestAnswer(testAnswer);
	}

	/**
	* Creates a new test answer with the primary key. Does not add the test answer to the database.
	*
	* @param answerId the primary key for the new test answer
	* @return the new test answer
	*/
	public static com.liferay.lms.model.TestAnswer createTestAnswer(
		long answerId) {
		return getService().createTestAnswer(answerId);
	}

	/**
	* Deletes the test answer with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param answerId the primary key of the test answer
	* @return the test answer that was removed
	* @throws PortalException if a test answer with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer deleteTestAnswer(
		long answerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteTestAnswer(answerId);
	}

	/**
	* Deletes the test answer from the database. Also notifies the appropriate model listeners.
	*
	* @param testAnswer the test answer
	* @return the test answer that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer deleteTestAnswer(
		com.liferay.lms.model.TestAnswer testAnswer)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteTestAnswer(testAnswer);
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

	public static com.liferay.lms.model.TestAnswer fetchTestAnswer(
		long answerId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchTestAnswer(answerId);
	}

	/**
	* Returns the test answer with the primary key.
	*
	* @param answerId the primary key of the test answer
	* @return the test answer
	* @throws PortalException if a test answer with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer getTestAnswer(long answerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTestAnswer(answerId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the test answers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of test answers
	* @param end the upper bound of the range of test answers (not inclusive)
	* @return the range of test answers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.TestAnswer> getTestAnswers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTestAnswers(start, end);
	}

	/**
	* Returns the number of test answers.
	*
	* @return the number of test answers
	* @throws SystemException if a system exception occurred
	*/
	public static int getTestAnswersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTestAnswersCount();
	}

	/**
	* Updates the test answer in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param testAnswer the test answer
	* @return the test answer that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer updateTestAnswer(
		com.liferay.lms.model.TestAnswer testAnswer)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTestAnswer(testAnswer);
	}

	/**
	* Updates the test answer in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param testAnswer the test answer
	* @param merge whether to merge the test answer with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the test answer that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer updateTestAnswer(
		com.liferay.lms.model.TestAnswer testAnswer, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTestAnswer(testAnswer, merge);
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

	public static java.util.List<com.liferay.lms.model.TestAnswer> getTestAnswersByQuestionId(
		long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTestAnswersByQuestionId(questionId);
	}

	public static com.liferay.lms.model.TestAnswer addTestAnswer(
		long questionId, java.lang.String answer,
		java.lang.String feedbackCorrect, java.lang.String feedbacknocorrect,
		boolean correct)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addTestAnswer(questionId, answer, feedbackCorrect,
			feedbacknocorrect, correct);
	}

	public static void clearService() {
		_service = null;
	}

	public static TestAnswerLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					TestAnswerLocalService.class.getName());

			if (invokableLocalService instanceof TestAnswerLocalService) {
				_service = (TestAnswerLocalService)invokableLocalService;
			}
			else {
				_service = new TestAnswerLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(TestAnswerLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(TestAnswerLocalService service) {
	}

	private static TestAnswerLocalService _service;
}