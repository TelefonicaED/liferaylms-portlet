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

import com.liferay.lms.model.LearningActivityResult;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the learning activity result service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LearningActivityResultPersistenceImpl
 * @see LearningActivityResultUtil
 * @generated
 */
public interface LearningActivityResultPersistence extends BasePersistence<LearningActivityResult> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LearningActivityResultUtil} to access the learning activity result persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the learning activity result in the entity cache if it is enabled.
	*
	* @param learningActivityResult the learning activity result
	*/
	public void cacheResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult);

	/**
	* Caches the learning activity results in the entity cache if it is enabled.
	*
	* @param learningActivityResults the learning activity results
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.LearningActivityResult> learningActivityResults);

	/**
	* Creates a new learning activity result with the primary key. Does not add the learning activity result to the database.
	*
	* @param larId the primary key for the new learning activity result
	* @return the new learning activity result
	*/
	public com.liferay.lms.model.LearningActivityResult create(long larId);

	/**
	* Removes the learning activity result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param larId the primary key of the learning activity result
	* @return the learning activity result that was removed
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult remove(long larId)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.LearningActivityResult updateImpl(
		com.liferay.lms.model.LearningActivityResult learningActivityResult,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity result with the primary key or throws a {@link com.liferay.lms.NoSuchLearningActivityResultException} if it could not be found.
	*
	* @param larId the primary key of the learning activity result
	* @return the learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult findByPrimaryKey(
		long larId)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity result with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param larId the primary key of the learning activity result
	* @return the learning activity result, or <code>null</code> if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult fetchByPrimaryKey(
		long larId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the learning activity results where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the learning activity results where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of learning activity results
	* @param end the upper bound of the range of learning activity results (not inclusive)
	* @return the range of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the learning activity results where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of learning activity results
	* @param end the upper bound of the range of learning activity results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity result in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity result in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity result in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity result in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity results before and after the current learning activity result in the ordered set where uuid = &#63;.
	*
	* @param larId the primary key of the current learning activity result
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult[] findByUuid_PrevAndNext(
		long larId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity result where actId = &#63; and userId = &#63; or throws a {@link com.liferay.lms.NoSuchLearningActivityResultException} if it could not be found.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult findByact_user(
		long actId, long userId)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity result where actId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult fetchByact_user(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity result where actId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult fetchByact_user(
		long actId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the learning activity results where actId = &#63; and passed = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @return the matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByap(
		long actId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the learning activity results where actId = &#63; and passed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param passed the passed
	* @param start the lower bound of the range of learning activity results
	* @param end the upper bound of the range of learning activity results (not inclusive)
	* @return the range of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByap(
		long actId, boolean passed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the learning activity results where actId = &#63; and passed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param passed the passed
	* @param start the lower bound of the range of learning activity results
	* @param end the upper bound of the range of learning activity results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByap(
		long actId, boolean passed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity result in the ordered set where actId = &#63; and passed = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult findByap_First(
		long actId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity result in the ordered set where actId = &#63; and passed = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult fetchByap_First(
		long actId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity result in the ordered set where actId = &#63; and passed = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult findByap_Last(
		long actId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity result in the ordered set where actId = &#63; and passed = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult fetchByap_Last(
		long actId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity results before and after the current learning activity result in the ordered set where actId = &#63; and passed = &#63;.
	*
	* @param larId the primary key of the current learning activity result
	* @param actId the act ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult[] findByap_PrevAndNext(
		long larId, long actId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the learning activity results where actId = &#63; and passed = &#63; and endDate = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param endDate the end date
	* @return the matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByapd(
		long actId, boolean passed, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the learning activity results where actId = &#63; and passed = &#63; and endDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param passed the passed
	* @param endDate the end date
	* @param start the lower bound of the range of learning activity results
	* @param end the upper bound of the range of learning activity results (not inclusive)
	* @return the range of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByapd(
		long actId, boolean passed, java.util.Date endDate, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the learning activity results where actId = &#63; and passed = &#63; and endDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param passed the passed
	* @param endDate the end date
	* @param start the lower bound of the range of learning activity results
	* @param end the upper bound of the range of learning activity results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByapd(
		long actId, boolean passed, java.util.Date endDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity result in the ordered set where actId = &#63; and passed = &#63; and endDate = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param endDate the end date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult findByapd_First(
		long actId, boolean passed, java.util.Date endDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity result in the ordered set where actId = &#63; and passed = &#63; and endDate = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param endDate the end date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult fetchByapd_First(
		long actId, boolean passed, java.util.Date endDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity result in the ordered set where actId = &#63; and passed = &#63; and endDate = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param endDate the end date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult findByapd_Last(
		long actId, boolean passed, java.util.Date endDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity result in the ordered set where actId = &#63; and passed = &#63; and endDate = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param endDate the end date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult fetchByapd_Last(
		long actId, boolean passed, java.util.Date endDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity results before and after the current learning activity result in the ordered set where actId = &#63; and passed = &#63; and endDate = &#63;.
	*
	* @param larId the primary key of the current learning activity result
	* @param actId the act ID
	* @param passed the passed
	* @param endDate the end date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult[] findByapd_PrevAndNext(
		long larId, long actId, boolean passed, java.util.Date endDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the learning activity results where actId = &#63;.
	*
	* @param actId the act ID
	* @return the matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByac(
		long actId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the learning activity results where actId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param start the lower bound of the range of learning activity results
	* @param end the upper bound of the range of learning activity results (not inclusive)
	* @return the range of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByac(
		long actId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the learning activity results where actId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param start the lower bound of the range of learning activity results
	* @param end the upper bound of the range of learning activity results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByac(
		long actId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity result in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult findByac_First(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity result in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult fetchByac_First(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity result in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult findByac_Last(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity result in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult fetchByac_Last(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity results before and after the current learning activity result in the ordered set where actId = &#63;.
	*
	* @param larId the primary key of the current learning activity result
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult[] findByac_PrevAndNext(
		long larId, long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the learning activity results where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByuser(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the learning activity results where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of learning activity results
	* @param end the upper bound of the range of learning activity results (not inclusive)
	* @return the range of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByuser(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the learning activity results where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of learning activity results
	* @param end the upper bound of the range of learning activity results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findByuser(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult findByuser_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult fetchByuser_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult findByuser_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult fetchByuser_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity results before and after the current learning activity result in the ordered set where userId = &#63;.
	*
	* @param larId the primary key of the current learning activity result
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult[] findByuser_PrevAndNext(
		long larId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the learning activity results.
	*
	* @return the learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the learning activity results.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of learning activity results
	* @param end the upper bound of the range of learning activity results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityResult> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the learning activity results where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the learning activity result where actId = &#63; and userId = &#63; from the database.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the learning activity result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityResult removeByact_user(
		long actId, long userId)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the learning activity results where actId = &#63; and passed = &#63; from the database.
	*
	* @param actId the act ID
	* @param passed the passed
	* @throws SystemException if a system exception occurred
	*/
	public void removeByap(long actId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the learning activity results where actId = &#63; and passed = &#63; and endDate = &#63; from the database.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param endDate the end date
	* @throws SystemException if a system exception occurred
	*/
	public void removeByapd(long actId, boolean passed, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the learning activity results where actId = &#63; from the database.
	*
	* @param actId the act ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByac(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the learning activity results where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByuser(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the learning activity results from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity results where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity results where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the number of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public int countByact_user(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity results where actId = &#63; and passed = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @return the number of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public int countByap(long actId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity results where actId = &#63; and passed = &#63; and endDate = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param endDate the end date
	* @return the number of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public int countByapd(long actId, boolean passed, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity results where actId = &#63;.
	*
	* @param actId the act ID
	* @return the number of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public int countByac(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity results where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public int countByuser(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity results.
	*
	* @return the number of learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}