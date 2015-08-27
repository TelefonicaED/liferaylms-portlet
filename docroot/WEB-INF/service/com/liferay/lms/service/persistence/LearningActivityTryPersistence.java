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

import com.liferay.lms.model.LearningActivityTry;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the learning activity try service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LearningActivityTryPersistenceImpl
 * @see LearningActivityTryUtil
 * @generated
 */
public interface LearningActivityTryPersistence extends BasePersistence<LearningActivityTry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LearningActivityTryUtil} to access the learning activity try persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the learning activity try in the entity cache if it is enabled.
	*
	* @param learningActivityTry the learning activity try
	*/
	public void cacheResult(
		com.liferay.lms.model.LearningActivityTry learningActivityTry);

	/**
	* Caches the learning activity tries in the entity cache if it is enabled.
	*
	* @param learningActivityTries the learning activity tries
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.LearningActivityTry> learningActivityTries);

	/**
	* Creates a new learning activity try with the primary key. Does not add the learning activity try to the database.
	*
	* @param latId the primary key for the new learning activity try
	* @return the new learning activity try
	*/
	public com.liferay.lms.model.LearningActivityTry create(long latId);

	/**
	* Removes the learning activity try with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param latId the primary key of the learning activity try
	* @return the learning activity try that was removed
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry remove(long latId)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.LearningActivityTry updateImpl(
		com.liferay.lms.model.LearningActivityTry learningActivityTry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity try with the primary key or throws a {@link com.liferay.lms.NoSuchLearningActivityTryException} if it could not be found.
	*
	* @param latId the primary key of the learning activity try
	* @return the learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry findByPrimaryKey(
		long latId)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity try with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param latId the primary key of the learning activity try
	* @return the learning activity try, or <code>null</code> if a learning activity try with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry fetchByPrimaryKey(
		long latId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the learning activity tries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityTry> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the learning activity tries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of learning activity tries
	* @param end the upper bound of the range of learning activity tries (not inclusive)
	* @return the range of matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityTry> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the learning activity tries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of learning activity tries
	* @param end the upper bound of the range of learning activity tries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityTry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity try in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity try in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity try in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity try in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity tries before and after the current learning activity try in the ordered set where uuid = &#63;.
	*
	* @param latId the primary key of the current learning activity try
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry[] findByUuid_PrevAndNext(
		long latId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the learning activity tries where actId = &#63;.
	*
	* @param actId the act ID
	* @return the matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityTry> findByact(
		long actId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the learning activity tries where actId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param start the lower bound of the range of learning activity tries
	* @param end the upper bound of the range of learning activity tries (not inclusive)
	* @return the range of matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityTry> findByact(
		long actId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the learning activity tries where actId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param start the lower bound of the range of learning activity tries
	* @param end the upper bound of the range of learning activity tries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityTry> findByact(
		long actId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity try in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry findByact_First(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity try in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry fetchByact_First(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity try in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry findByact_Last(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity try in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry fetchByact_Last(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity tries before and after the current learning activity try in the ordered set where actId = &#63;.
	*
	* @param latId the primary key of the current learning activity try
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry[] findByact_PrevAndNext(
		long latId, long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the learning activity tries where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityTry> findByact_u(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the learning activity tries where actId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param start the lower bound of the range of learning activity tries
	* @param end the upper bound of the range of learning activity tries (not inclusive)
	* @return the range of matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityTry> findByact_u(
		long actId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the learning activity tries where actId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param start the lower bound of the range of learning activity tries
	* @param end the upper bound of the range of learning activity tries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityTry> findByact_u(
		long actId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity try in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry findByact_u_First(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity try in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry fetchByact_u_First(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity try in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry findByact_u_Last(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity try in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry fetchByact_u_Last(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity tries before and after the current learning activity try in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param latId the primary key of the current learning activity try
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivityTry[] findByact_u_PrevAndNext(
		long latId, long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the learning activity tries.
	*
	* @return the learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityTry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.LearningActivityTry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the learning activity tries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of learning activity tries
	* @param end the upper bound of the range of learning activity tries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivityTry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the learning activity tries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the learning activity tries where actId = &#63; from the database.
	*
	* @param actId the act ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByact(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the learning activity tries where actId = &#63; and userId = &#63; from the database.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByact_u(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the learning activity tries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity tries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity tries where actId = &#63;.
	*
	* @param actId the act ID
	* @return the number of matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public int countByact(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity tries where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the number of matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public int countByact_u(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity tries.
	*
	* @return the number of learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}