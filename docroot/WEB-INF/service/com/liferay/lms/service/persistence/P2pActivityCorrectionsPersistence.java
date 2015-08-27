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

import com.liferay.lms.model.P2pActivityCorrections;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the p2p activity corrections service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see P2pActivityCorrectionsPersistenceImpl
 * @see P2pActivityCorrectionsUtil
 * @generated
 */
public interface P2pActivityCorrectionsPersistence extends BasePersistence<P2pActivityCorrections> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link P2pActivityCorrectionsUtil} to access the p2p activity corrections persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the p2p activity corrections in the entity cache if it is enabled.
	*
	* @param p2pActivityCorrections the p2p activity corrections
	*/
	public void cacheResult(
		com.liferay.lms.model.P2pActivityCorrections p2pActivityCorrections);

	/**
	* Caches the p2p activity correctionses in the entity cache if it is enabled.
	*
	* @param p2pActivityCorrectionses the p2p activity correctionses
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.P2pActivityCorrections> p2pActivityCorrectionses);

	/**
	* Creates a new p2p activity corrections with the primary key. Does not add the p2p activity corrections to the database.
	*
	* @param p2pActivityCorrectionsId the primary key for the new p2p activity corrections
	* @return the new p2p activity corrections
	*/
	public com.liferay.lms.model.P2pActivityCorrections create(
		long p2pActivityCorrectionsId);

	/**
	* Removes the p2p activity corrections with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param p2pActivityCorrectionsId the primary key of the p2p activity corrections
	* @return the p2p activity corrections that was removed
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections remove(
		long p2pActivityCorrectionsId)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.P2pActivityCorrections updateImpl(
		com.liferay.lms.model.P2pActivityCorrections p2pActivityCorrections,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the p2p activity corrections with the primary key or throws a {@link com.liferay.lms.NoSuchP2pActivityCorrectionsException} if it could not be found.
	*
	* @param p2pActivityCorrectionsId the primary key of the p2p activity corrections
	* @return the p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections findByPrimaryKey(
		long p2pActivityCorrectionsId)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the p2p activity corrections with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param p2pActivityCorrectionsId the primary key of the p2p activity corrections
	* @return the p2p activity corrections, or <code>null</code> if a p2p activity corrections with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections fetchByPrimaryKey(
		long p2pActivityCorrectionsId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the p2p activity correctionses where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the p2p activity correctionses where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of p2p activity correctionses
	* @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	* @return the range of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the p2p activity correctionses where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of p2p activity correctionses
	* @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity corrections in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity corrections in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity corrections in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity corrections in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the p2p activity correctionses before and after the current p2p activity corrections in the ordered set where uuid = &#63;.
	*
	* @param p2pActivityCorrectionsId the primary key of the current p2p activity corrections
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections[] findByUuid_PrevAndNext(
		long p2pActivityCorrectionsId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the p2p activity correctionses where p2pActivityId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @return the matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByP2pActivityId(
		long p2pActivityId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the p2p activity correctionses where p2pActivityId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param p2pActivityId the p2p activity ID
	* @param start the lower bound of the range of p2p activity correctionses
	* @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	* @return the range of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByP2pActivityId(
		long p2pActivityId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the p2p activity correctionses where p2pActivityId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param p2pActivityId the p2p activity ID
	* @param start the lower bound of the range of p2p activity correctionses
	* @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByP2pActivityId(
		long p2pActivityId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections findByP2pActivityId_First(
		long p2pActivityId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections fetchByP2pActivityId_First(
		long p2pActivityId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections findByP2pActivityId_Last(
		long p2pActivityId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections fetchByP2pActivityId_Last(
		long p2pActivityId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the p2p activity correctionses before and after the current p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	*
	* @param p2pActivityCorrectionsId the primary key of the current p2p activity corrections
	* @param p2pActivityId the p2p activity ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections[] findByP2pActivityId_PrevAndNext(
		long p2pActivityCorrectionsId, long p2pActivityId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the p2p activity correctionses where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByActIdAndUserId(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the p2p activity correctionses where actId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param start the lower bound of the range of p2p activity correctionses
	* @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	* @return the range of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByActIdAndUserId(
		long actId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the p2p activity correctionses where actId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param start the lower bound of the range of p2p activity correctionses
	* @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByActIdAndUserId(
		long actId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity corrections in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections findByActIdAndUserId_First(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity corrections in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections fetchByActIdAndUserId_First(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity corrections in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections findByActIdAndUserId_Last(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity corrections in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections fetchByActIdAndUserId_Last(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the p2p activity correctionses before and after the current p2p activity corrections in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param p2pActivityCorrectionsId the primary key of the current p2p activity corrections
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections[] findByActIdAndUserId_PrevAndNext(
		long p2pActivityCorrectionsId, long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the p2p activity correctionses where p2pActivityId = &#63; and userId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @return the matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByP2pActivityIdAndUserId(
		long p2pActivityId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the p2p activity correctionses where p2pActivityId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @param start the lower bound of the range of p2p activity correctionses
	* @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	* @return the range of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByP2pActivityIdAndUserId(
		long p2pActivityId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the p2p activity correctionses where p2pActivityId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @param start the lower bound of the range of p2p activity correctionses
	* @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByP2pActivityIdAndUserId(
		long p2pActivityId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity corrections in the ordered set where p2pActivityId = &#63; and userId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections findByP2pActivityIdAndUserId_First(
		long p2pActivityId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first p2p activity corrections in the ordered set where p2pActivityId = &#63; and userId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections fetchByP2pActivityIdAndUserId_First(
		long p2pActivityId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity corrections in the ordered set where p2pActivityId = &#63; and userId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections findByP2pActivityIdAndUserId_Last(
		long p2pActivityId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last p2p activity corrections in the ordered set where p2pActivityId = &#63; and userId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections fetchByP2pActivityIdAndUserId_Last(
		long p2pActivityId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the p2p activity correctionses before and after the current p2p activity corrections in the ordered set where p2pActivityId = &#63; and userId = &#63;.
	*
	* @param p2pActivityCorrectionsId the primary key of the current p2p activity corrections
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivityCorrections[] findByP2pActivityIdAndUserId_PrevAndNext(
		long p2pActivityCorrectionsId, long p2pActivityId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the p2p activity correctionses.
	*
	* @return the p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the p2p activity correctionses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of p2p activity correctionses
	* @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	* @return the range of p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the p2p activity correctionses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of p2p activity correctionses
	* @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivityCorrections> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the p2p activity correctionses where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the p2p activity correctionses where p2pActivityId = &#63; from the database.
	*
	* @param p2pActivityId the p2p activity ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByP2pActivityId(long p2pActivityId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the p2p activity correctionses where actId = &#63; and userId = &#63; from the database.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByActIdAndUserId(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the p2p activity correctionses where p2pActivityId = &#63; and userId = &#63; from the database.
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByP2pActivityIdAndUserId(long p2pActivityId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the p2p activity correctionses from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of p2p activity correctionses where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of p2p activity correctionses where p2pActivityId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @return the number of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public int countByP2pActivityId(long p2pActivityId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of p2p activity correctionses where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the number of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public int countByActIdAndUserId(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of p2p activity correctionses where p2pActivityId = &#63; and userId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @return the number of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public int countByP2pActivityIdAndUserId(long p2pActivityId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of p2p activity correctionses.
	*
	* @return the number of p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}