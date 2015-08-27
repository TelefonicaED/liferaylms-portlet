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

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the test answer service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see TestAnswerPersistenceImpl
 * @see TestAnswerUtil
 * @generated
 */
public interface TestAnswerPersistence extends BasePersistence<TestAnswer> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TestAnswerUtil} to access the test answer persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the test answer in the entity cache if it is enabled.
	*
	* @param testAnswer the test answer
	*/
	public void cacheResult(com.liferay.lms.model.TestAnswer testAnswer);

	/**
	* Caches the test answers in the entity cache if it is enabled.
	*
	* @param testAnswers the test answers
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.TestAnswer> testAnswers);

	/**
	* Creates a new test answer with the primary key. Does not add the test answer to the database.
	*
	* @param answerId the primary key for the new test answer
	* @return the new test answer
	*/
	public com.liferay.lms.model.TestAnswer create(long answerId);

	/**
	* Removes the test answer with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param answerId the primary key of the test answer
	* @return the test answer that was removed
	* @throws com.liferay.lms.NoSuchTestAnswerException if a test answer with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer remove(long answerId)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.TestAnswer updateImpl(
		com.liferay.lms.model.TestAnswer testAnswer, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the test answer with the primary key or throws a {@link com.liferay.lms.NoSuchTestAnswerException} if it could not be found.
	*
	* @param answerId the primary key of the test answer
	* @return the test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a test answer with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer findByPrimaryKey(long answerId)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the test answer with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param answerId the primary key of the test answer
	* @return the test answer, or <code>null</code> if a test answer with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer fetchByPrimaryKey(long answerId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the test answers where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.TestAnswer> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.TestAnswer> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.TestAnswer> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first test answer in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first test answer in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test answer, or <code>null</code> if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last test answer in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last test answer in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test answer, or <code>null</code> if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.TestAnswer[] findByUuid_PrevAndNext(
		long answerId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the test answers where actId = &#63;.
	*
	* @param actId the act ID
	* @return the matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.TestAnswer> findByac(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.TestAnswer> findByac(
		long actId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.TestAnswer> findByac(
		long actId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first test answer in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer findByac_First(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first test answer in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test answer, or <code>null</code> if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer fetchByac_First(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last test answer in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer findByac_Last(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last test answer in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test answer, or <code>null</code> if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer fetchByac_Last(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.TestAnswer[] findByac_PrevAndNext(
		long answerId, long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the test answers where questionId = &#63;.
	*
	* @param questionId the question ID
	* @return the matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.TestAnswer> findByq(
		long questionId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.TestAnswer> findByq(
		long questionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.TestAnswer> findByq(
		long questionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first test answer in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer findByq_First(long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first test answer in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching test answer, or <code>null</code> if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer fetchByq_First(long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last test answer in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test answer
	* @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer findByq_Last(long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last test answer in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching test answer, or <code>null</code> if a matching test answer could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.TestAnswer fetchByq_Last(long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.TestAnswer[] findByq_PrevAndNext(
		long answerId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchTestAnswerException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the test answers.
	*
	* @return the test answers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.TestAnswer> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.TestAnswer> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.TestAnswer> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the test answers where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the test answers where actId = &#63; from the database.
	*
	* @param actId the act ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByac(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the test answers where questionId = &#63; from the database.
	*
	* @param questionId the question ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByq(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the test answers from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of test answers where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of test answers where actId = &#63;.
	*
	* @param actId the act ID
	* @return the number of matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public int countByac(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of test answers where questionId = &#63;.
	*
	* @param questionId the question ID
	* @return the number of matching test answers
	* @throws SystemException if a system exception occurred
	*/
	public int countByq(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of test answers.
	*
	* @return the number of test answers
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}