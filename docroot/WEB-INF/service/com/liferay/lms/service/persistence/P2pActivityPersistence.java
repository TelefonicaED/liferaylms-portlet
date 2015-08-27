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

import com.liferay.lms.model.P2pActivity;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the p2p activity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see P2pActivityPersistenceImpl
 * @see P2pActivityUtil
 * @generated
 */
public interface P2pActivityPersistence extends BasePersistence<P2pActivity> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link P2pActivityUtil} to access the p2p activity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the p2p activity in the entity cache if it is enabled.
	*
	* @param p2pActivity the p2p activity
	*/
	public void cacheResult(com.liferay.lms.model.P2pActivity p2pActivity);

	/**
	* Caches the p2p activities in the entity cache if it is enabled.
	*
	* @param p2pActivities the p2p activities
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.P2pActivity> p2pActivities);

	/**
	* Creates a new p2p activity with the primary key. Does not add the p2p activity to the database.
	*
	* @param p2pActivityId the primary key for the new p2p activity
	* @return the new p2p activity
	*/
	public com.liferay.lms.model.P2pActivity create(long p2pActivityId);

	/**
	* Removes the p2p activity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param p2pActivityId the primary key of the p2p activity
	* @return the p2p activity that was removed
	* @throws com.liferay.lms.NoSuchP2pActivityException if a p2p activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity remove(long p2pActivityId)
		throws com.liferay.lms.NoSuchP2pActivityException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.P2pActivity updateImpl(
		com.liferay.lms.model.P2pActivity p2pActivity, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the p2p activity with the primary key or throws a {@link com.liferay.lms.NoSuchP2pActivityException} if it could not be found.
	*
	* @param p2pActivityId the primary key of the p2p activity
	* @return the p2p activity
	* @throws com.liferay.lms.NoSuchP2pActivityException if a p2p activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity findByPrimaryKey(
		long p2pActivityId)
		throws com.liferay.lms.NoSuchP2pActivityException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the p2p activity with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param p2pActivityId the primary key of the p2p activity
	* @return the p2p activity, or <code>null</code> if a p2p activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity fetchByPrimaryKey(
		long p2pActivityId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the p2p activities where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivity> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the p2p activities where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of p2p activities
	* @param end the upper bound of the range of p2p activities (not inclusive)
	* @return the range of matching p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivity> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the p2p activities where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of p2p activities
	* @param end the upper bound of the range of p2p activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivity> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity
	* @throws com.liferay.lms.NoSuchP2pActivityException if a matching p2p activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity, or <code>null</code> if a matching p2p activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity
	* @throws com.liferay.lms.NoSuchP2pActivityException if a matching p2p activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity, or <code>null</code> if a matching p2p activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the p2p activities before and after the current p2p activity in the ordered set where uuid = &#63;.
	*
	* @param p2pActivityId the primary key of the current p2p activity
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next p2p activity
	* @throws com.liferay.lms.NoSuchP2pActivityException if a p2p activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity[] findByUuid_PrevAndNext(
		long p2pActivityId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the p2p activities where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the matching p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivity> findByActIdAndUserId(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the p2p activities where actId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param start the lower bound of the range of p2p activities
	* @param end the upper bound of the range of p2p activities (not inclusive)
	* @return the range of matching p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivity> findByActIdAndUserId(
		long actId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the p2p activities where actId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param start the lower bound of the range of p2p activities
	* @param end the upper bound of the range of p2p activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivity> findByActIdAndUserId(
		long actId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity
	* @throws com.liferay.lms.NoSuchP2pActivityException if a matching p2p activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity findByActIdAndUserId_First(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity, or <code>null</code> if a matching p2p activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity fetchByActIdAndUserId_First(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity
	* @throws com.liferay.lms.NoSuchP2pActivityException if a matching p2p activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity findByActIdAndUserId_Last(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity, or <code>null</code> if a matching p2p activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity fetchByActIdAndUserId_Last(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the p2p activities before and after the current p2p activity in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param p2pActivityId the primary key of the current p2p activity
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next p2p activity
	* @throws com.liferay.lms.NoSuchP2pActivityException if a p2p activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity[] findByActIdAndUserId_PrevAndNext(
		long p2pActivityId, long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the p2p activities where actId = &#63;.
	*
	* @param actId the act ID
	* @return the matching p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivity> findByActId(
		long actId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the p2p activities where actId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param start the lower bound of the range of p2p activities
	* @param end the upper bound of the range of p2p activities (not inclusive)
	* @return the range of matching p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivity> findByActId(
		long actId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the p2p activities where actId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param start the lower bound of the range of p2p activities
	* @param end the upper bound of the range of p2p activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivity> findByActId(
		long actId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity
	* @throws com.liferay.lms.NoSuchP2pActivityException if a matching p2p activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity findByActId_First(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity, or <code>null</code> if a matching p2p activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity fetchByActId_First(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity
	* @throws com.liferay.lms.NoSuchP2pActivityException if a matching p2p activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity findByActId_Last(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity, or <code>null</code> if a matching p2p activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity fetchByActId_Last(long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the p2p activities before and after the current p2p activity in the ordered set where actId = &#63;.
	*
	* @param p2pActivityId the primary key of the current p2p activity
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next p2p activity
	* @throws com.liferay.lms.NoSuchP2pActivityException if a p2p activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity[] findByActId_PrevAndNext(
		long p2pActivityId, long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the p2p activities.
	*
	* @return the p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivity> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the p2p activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of p2p activities
	* @param end the upper bound of the range of p2p activities (not inclusive)
	* @return the range of p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivity> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the p2p activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of p2p activities
	* @param end the upper bound of the range of p2p activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the p2p activities where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the p2p activities where actId = &#63; and userId = &#63; from the database.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByActIdAndUserId(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the p2p activities where actId = &#63; from the database.
	*
	* @param actId the act ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByActId(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the p2p activities from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of p2p activities where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of p2p activities where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the number of matching p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByActIdAndUserId(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of p2p activities where actId = &#63;.
	*
	* @param actId the act ID
	* @return the number of matching p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByActId(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of p2p activities.
	*
	* @return the number of p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}