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

import com.liferay.lms.model.TestQuestion;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the test question service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see TestQuestionPersistenceImpl
 * @see TestQuestionUtil
 * @generated
 */
public interface TestQuestionPersistence extends BasePersistence<TestQuestion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TestQuestionUtil} to access the test question persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the test question in the entity cache if it is enabled.
	*
	* @param testQuestion the test question
	*/
	public void cacheResult(com.liferay.lms.model.TestQuestion testQuestion);

	/**
	* Caches the test questions in the entity cache if it is enabled.
	*
	* @param testQuestions the test questions
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.TestQuestion> testQuestions);

	/**
	* Creates a new test question with the primary key. Does not add the test question to the database.
	*
	* @param questionId the primary key for the new test question
	* @return the new test question
	*/
	public com.liferay.lms.model.TestQuestion create(long questionId);

	/**
	* Removes the test question with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param questionId the primary key of the test question
	* @return the test question that was removed
	* @throws com.liferay.lms.NoSuchTestQuestionException if a test question with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestQuestion remove(long questionId)
		throws com.liferay.lms.NoSuchTestQuestionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.TestQuestion updateImpl(
		com.liferay.lms.model.TestQuestion testQuestion, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the test question with the primary key or throws a {@link com.liferay.lms.NoSuchTestQuestionException} if it could not be found.
	*
	* @param questionId the primary key of the test question
	* @return the test question
	* @throws com.liferay.lms.NoSuchTestQuestionException if a test question with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestQuestion findByPrimaryKey(long questionId)
		throws com.liferay.lms.NoSuchTestQuestionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the test question with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param questionId the primary key of the test question
	* @return the test question, or <code>null</code> if a test question with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestQuestion fetchByPrimaryKey(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the test questions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching test questions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.TestQuestion> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the test questions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of test questions
	* @param end the upper bound of the range of test questions (not inclusive)
	* @return the range of matching test questions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.TestQuestion> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the test questions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of test questions
	* @param end the upper bound of the range of test questions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching test questions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.TestQuestion> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first test question in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test question
	* @throws com.liferay.lms.NoSuchTestQuestionException if a matching test question could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestQuestion findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestQuestionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first test question in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test question, or <code>null</code> if a matching test question could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestQuestion fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last test question in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test question
	* @throws com.liferay.lms.NoSuchTestQuestionException if a matching test question could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestQuestion findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestQuestionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last test question in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test question, or <code>null</code> if a matching test question could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestQuestion fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the test questions before and after the current test question in the ordered set where uuid = &#63;.
	*
	* @param questionId the primary key of the current test question
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next test question
	* @throws com.liferay.lms.NoSuchTestQuestionException if a test question with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestQuestion[] findByUuid_PrevAndNext(
		long questionId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestQuestionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the test questions where actId = &#63;.
	*
	* @param actId the act ID
	* @return the matching test questions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.TestQuestion> findByac(
		long actId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the test questions where actId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param start the lower bound of the range of test questions
	* @param end the upper bound of the range of test questions (not inclusive)
	* @return the range of matching test questions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.TestQuestion> findByac(
		long actId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the test questions where actId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param start the lower bound of the range of test questions
	* @param end the upper bound of the range of test questions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching test questions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.TestQuestion> findByac(
		long actId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first test question in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test question
	* @throws com.liferay.lms.NoSuchTestQuestionException if a matching test question could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestQuestion findByac_First(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestQuestionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first test question in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test question, or <code>null</code> if a matching test question could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestQuestion fetchByac_First(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last test question in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test question
	* @throws com.liferay.lms.NoSuchTestQuestionException if a matching test question could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestQuestion findByac_Last(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestQuestionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last test question in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test question, or <code>null</code> if a matching test question could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestQuestion fetchByac_Last(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the test questions before and after the current test question in the ordered set where actId = &#63;.
	*
	* @param questionId the primary key of the current test question
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next test question
	* @throws com.liferay.lms.NoSuchTestQuestionException if a test question with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestQuestion[] findByac_PrevAndNext(
		long questionId, long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestQuestionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the test questions.
	*
	* @return the test questions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.TestQuestion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the test questions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of test questions
	* @param end the upper bound of the range of test questions (not inclusive)
	* @return the range of test questions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.TestQuestion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the test questions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of test questions
	* @param end the upper bound of the range of test questions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of test questions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.TestQuestion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the test questions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the test questions where actId = &#63; from the database.
	*
	* @param actId the act ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByac(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the test questions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of test questions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching test questions
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of test questions where actId = &#63;.
	*
	* @param actId the act ID
	* @return the number of matching test questions
	* @throws SystemException if a system exception occurred
	*/
	public int countByac(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of test questions.
	*
	* @return the number of test questions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}