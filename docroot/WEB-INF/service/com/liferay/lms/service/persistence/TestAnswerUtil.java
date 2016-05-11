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

package com.liferay.lms.service.persistence;

import com.liferay.lms.model.TestAnswer;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the test answer service. This utility wraps {@link TestAnswerPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see TestAnswerPersistence
 * @see TestAnswerPersistenceImpl
 * @generated
 */
public class TestAnswerUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(TestAnswer testAnswer) {
		getPersistence().clearCache(testAnswer);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<TestAnswer> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<TestAnswer> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<TestAnswer> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static TestAnswer update(TestAnswer testAnswer, boolean merge)
		throws SystemException {
		return getPersistence().update(testAnswer, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static TestAnswer update(TestAnswer testAnswer, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(testAnswer, merge, serviceContext);
	}

	/**
	* Caches the test answer in the entity cache if it is enabled.
	*
	* @param testAnswer the test answer
	*/
	public static void cacheResult(com.liferay.lms.model.TestAnswer testAnswer) {
		getPersistence().cacheResult(testAnswer);
	}

	/**
	* Caches the test answers in the entity cache if it is enabled.
	*
	* @param testAnswers the test answers
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.TestAnswer> testAnswers) {
		getPersistence().cacheResult(testAnswers);
	}

	/**
	* Creates a new test answer with the primary key. Does not add the test answer to the database.
	*
	* @param answerId the primary key for the new test answer
	* @return the new test answer
	*/
	public static com.liferay.lms.model.TestAnswer create(long answerId) {
		return getPersistence().create(answerId);
	}

	/**
	* Removes the test answer with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param answerId the primary key of the test answer
	* @return the test answer that was removed
	* @throws com.liferay.lms.NoSuchTestAnswerException if a test answer with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer remove(long answerId)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(answerId);
	}

	public static com.liferay.lms.model.TestAnswer updateImpl(
		com.liferay.lms.model.TestAnswer testAnswer, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(testAnswer, merge);
	}

	/**
	* Returns the test answer with the primary key or throws a {@link com.liferay.lms.NoSuchTestAnswerException} if it could not be found.
	*
	* @param answerId the primary key of the test answer
	* @return the test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a test answer with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer findByPrimaryKey(
		long answerId)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(answerId);
	}

	/**
	* Returns the test answer with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param answerId the primary key of the test answer
	* @return the test answer, or <code>null</code> if a test answer with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer fetchByPrimaryKey(
		long answerId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(answerId);
	}

	/**
	* Returns all the test answers where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.TestAnswer> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the test answers where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of test answers
	* @param end the upper bound of the range of test answers (not inclusive)
	* @return the range of matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.TestAnswer> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the test answers where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of test answers
	* @param end the upper bound of the range of test answers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.TestAnswer> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first test answer in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first test answer in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test answer, or <code>null</code> if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last test answer in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last test answer in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test answer, or <code>null</code> if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the test answers before and after the current test answer in the ordered set where uuid = &#63;.
	*
	* @param answerId the primary key of the current test answer
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a test answer with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer[] findByUuid_PrevAndNext(
		long answerId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(answerId, uuid, orderByComparator);
	}

	/**
	* Returns all the test answers where actId = &#63;.
	*
	* @param actId the act ID
	* @return the matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.TestAnswer> findByac(
		long actId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByac(actId);
	}

	/**
	* Returns a range of all the test answers where actId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param start the lower bound of the range of test answers
	* @param end the upper bound of the range of test answers (not inclusive)
	* @return the range of matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.TestAnswer> findByac(
		long actId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByac(actId, start, end);
	}

	/**
	* Returns an ordered range of all the test answers where actId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param start the lower bound of the range of test answers
	* @param end the upper bound of the range of test answers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.TestAnswer> findByac(
		long actId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByac(actId, start, end, orderByComparator);
	}

	/**
	* Returns the first test answer in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer findByac_First(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByac_First(actId, orderByComparator);
	}

	/**
	* Returns the first test answer in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test answer, or <code>null</code> if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer fetchByac_First(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByac_First(actId, orderByComparator);
	}

	/**
	* Returns the last test answer in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer findByac_Last(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByac_Last(actId, orderByComparator);
	}

	/**
	* Returns the last test answer in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test answer, or <code>null</code> if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer fetchByac_Last(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByac_Last(actId, orderByComparator);
	}

	/**
	* Returns the test answers before and after the current test answer in the ordered set where actId = &#63;.
	*
	* @param answerId the primary key of the current test answer
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a test answer with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer[] findByac_PrevAndNext(
		long answerId, long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByac_PrevAndNext(answerId, actId, orderByComparator);
	}

	/**
	* Returns all the test answers where questionId = &#63;.
	*
	* @param questionId the question ID
	* @return the matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.TestAnswer> findByq(
		long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByq(questionId);
	}

	/**
	* Returns a range of all the test answers where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param questionId the question ID
	* @param start the lower bound of the range of test answers
	* @param end the upper bound of the range of test answers (not inclusive)
	* @return the range of matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.TestAnswer> findByq(
		long questionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByq(questionId, start, end);
	}

	/**
	* Returns an ordered range of all the test answers where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param questionId the question ID
	* @param start the lower bound of the range of test answers
	* @param end the upper bound of the range of test answers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.TestAnswer> findByq(
		long questionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByq(questionId, start, end, orderByComparator);
	}

	/**
	* Returns the first test answer in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer findByq_First(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByq_First(questionId, orderByComparator);
	}

	/**
	* Returns the first test answer in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test answer, or <code>null</code> if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer fetchByq_First(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByq_First(questionId, orderByComparator);
	}

	/**
	* Returns the last test answer in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer findByq_Last(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByq_Last(questionId, orderByComparator);
	}

	/**
	* Returns the last test answer in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test answer, or <code>null</code> if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer fetchByq_Last(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByq_Last(questionId, orderByComparator);
	}

	/**
	* Returns the test answers before and after the current test answer in the ordered set where questionId = &#63;.
	*
	* @param answerId the primary key of the current test answer
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a test answer with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.TestAnswer[] findByq_PrevAndNext(
		long answerId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByq_PrevAndNext(answerId, questionId, orderByComparator);
	}

	/**
	* Returns all the test answers.
	*
	* @return the test answers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.TestAnswer> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
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
	public static java.util.List<com.liferay.lms.model.TestAnswer> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the test answers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of test answers
	* @param end the upper bound of the range of test answers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of test answers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.TestAnswer> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the test answers where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes all the test answers where actId = &#63; from the database.
	*
	* @param actId the act ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByac(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByac(actId);
	}

	/**
	* Removes all the test answers where questionId = &#63; from the database.
	*
	* @param questionId the question ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByq(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByq(questionId);
	}

	/**
	* Removes all the test answers from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of test answers where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of test answers where actId = &#63;.
	*
	* @param actId the act ID
	* @return the number of matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public static int countByac(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByac(actId);
	}

	/**
	* Returns the number of test answers where questionId = &#63;.
	*
	* @param questionId the question ID
	* @return the number of matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public static int countByq(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByq(questionId);
	}

	/**
	* Returns the number of test answers.
	*
	* @return the number of test answers
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static TestAnswerPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (TestAnswerPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					TestAnswerPersistence.class.getName());

			ReferenceRegistry.registerReference(TestAnswerUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(TestAnswerPersistence persistence) {
	}

	private static TestAnswerPersistence _persistence;
}