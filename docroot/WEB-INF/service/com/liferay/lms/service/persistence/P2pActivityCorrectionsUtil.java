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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the p2p activity corrections service. This utility wraps {@link P2pActivityCorrectionsPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see P2pActivityCorrectionsPersistence
 * @see P2pActivityCorrectionsPersistenceImpl
 * @generated
 */
public class P2pActivityCorrectionsUtil {
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
	public static void clearCache(P2pActivityCorrections p2pActivityCorrections) {
		getPersistence().clearCache(p2pActivityCorrections);
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
	public static List<P2pActivityCorrections> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<P2pActivityCorrections> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<P2pActivityCorrections> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static P2pActivityCorrections update(
		P2pActivityCorrections p2pActivityCorrections, boolean merge)
		throws SystemException {
		return getPersistence().update(p2pActivityCorrections, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static P2pActivityCorrections update(
		P2pActivityCorrections p2pActivityCorrections, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(p2pActivityCorrections, merge, serviceContext);
	}

	/**
	* Caches the p2p activity corrections in the entity cache if it is enabled.
	*
	* @param p2pActivityCorrections the p2p activity corrections
	*/
	public static void cacheResult(
		com.liferay.lms.model.P2pActivityCorrections p2pActivityCorrections) {
		getPersistence().cacheResult(p2pActivityCorrections);
	}

	/**
	* Caches the p2p activity correctionses in the entity cache if it is enabled.
	*
	* @param p2pActivityCorrectionses the p2p activity correctionses
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.P2pActivityCorrections> p2pActivityCorrectionses) {
		getPersistence().cacheResult(p2pActivityCorrectionses);
	}

	/**
	* Creates a new p2p activity corrections with the primary key. Does not add the p2p activity corrections to the database.
	*
	* @param p2pActivityCorrectionsId the primary key for the new p2p activity corrections
	* @return the new p2p activity corrections
	*/
	public static com.liferay.lms.model.P2pActivityCorrections create(
		long p2pActivityCorrectionsId) {
		return getPersistence().create(p2pActivityCorrectionsId);
	}

	/**
	* Removes the p2p activity corrections with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param p2pActivityCorrectionsId the primary key of the p2p activity corrections
	* @return the p2p activity corrections that was removed
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections remove(
		long p2pActivityCorrectionsId)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(p2pActivityCorrectionsId);
	}

	public static com.liferay.lms.model.P2pActivityCorrections updateImpl(
		com.liferay.lms.model.P2pActivityCorrections p2pActivityCorrections,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(p2pActivityCorrections, merge);
	}

	/**
	* Returns the p2p activity corrections with the primary key or throws a {@link com.liferay.lms.NoSuchP2pActivityCorrectionsException} if it could not be found.
	*
	* @param p2pActivityCorrectionsId the primary key of the p2p activity corrections
	* @return the p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections findByPrimaryKey(
		long p2pActivityCorrectionsId)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(p2pActivityCorrectionsId);
	}

	/**
	* Returns the p2p activity corrections with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param p2pActivityCorrectionsId the primary key of the p2p activity corrections
	* @return the p2p activity corrections, or <code>null</code> if a p2p activity corrections with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections fetchByPrimaryKey(
		long p2pActivityCorrectionsId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(p2pActivityCorrectionsId);
	}

	/**
	* Returns all the p2p activity correctionses where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

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
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first p2p activity corrections in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first p2p activity corrections in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last p2p activity corrections in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last p2p activity corrections in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

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
	public static com.liferay.lms.model.P2pActivityCorrections[] findByUuid_PrevAndNext(
		long p2pActivityCorrectionsId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(p2pActivityCorrectionsId, uuid,
			orderByComparator);
	}

	/**
	* Returns all the p2p activity correctionses where p2pActivityId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @return the matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByP2pActivityId(
		long p2pActivityId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP2pActivityId(p2pActivityId);
	}

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
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByP2pActivityId(
		long p2pActivityId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP2pActivityId(p2pActivityId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByP2pActivityId(
		long p2pActivityId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP2pActivityId(p2pActivityId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections findByP2pActivityId_First(
		long p2pActivityId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP2pActivityId_First(p2pActivityId, orderByComparator);
	}

	/**
	* Returns the first p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections fetchByP2pActivityId_First(
		long p2pActivityId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByP2pActivityId_First(p2pActivityId, orderByComparator);
	}

	/**
	* Returns the last p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections
	* @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections findByP2pActivityId_Last(
		long p2pActivityId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP2pActivityId_Last(p2pActivityId, orderByComparator);
	}

	/**
	* Returns the last p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections fetchByP2pActivityId_Last(
		long p2pActivityId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByP2pActivityId_Last(p2pActivityId, orderByComparator);
	}

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
	public static com.liferay.lms.model.P2pActivityCorrections[] findByP2pActivityId_PrevAndNext(
		long p2pActivityCorrectionsId, long p2pActivityId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP2pActivityId_PrevAndNext(p2pActivityCorrectionsId,
			p2pActivityId, orderByComparator);
	}

	/**
	* Returns all the p2p activity correctionses where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByActIdAndUserId(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActIdAndUserId(actId, userId);
	}

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
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByActIdAndUserId(
		long actId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActIdAndUserId(actId, userId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByActIdAndUserId(
		long actId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByActIdAndUserId(actId, userId, start, end,
			orderByComparator);
	}

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
	public static com.liferay.lms.model.P2pActivityCorrections findByActIdAndUserId_First(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByActIdAndUserId_First(actId, userId, orderByComparator);
	}

	/**
	* Returns the first p2p activity corrections in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections fetchByActIdAndUserId_First(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByActIdAndUserId_First(actId, userId, orderByComparator);
	}

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
	public static com.liferay.lms.model.P2pActivityCorrections findByActIdAndUserId_Last(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByActIdAndUserId_Last(actId, userId, orderByComparator);
	}

	/**
	* Returns the last p2p activity corrections in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections fetchByActIdAndUserId_Last(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByActIdAndUserId_Last(actId, userId, orderByComparator);
	}

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
	public static com.liferay.lms.model.P2pActivityCorrections[] findByActIdAndUserId_PrevAndNext(
		long p2pActivityCorrectionsId, long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByActIdAndUserId_PrevAndNext(p2pActivityCorrectionsId,
			actId, userId, orderByComparator);
	}

	/**
	* Returns all the p2p activity correctionses where p2pActivityId = &#63; and userId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @return the matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByP2pActivityIdAndUserId(
		long p2pActivityId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP2pActivityIdAndUserId(p2pActivityId, userId);
	}

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
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByP2pActivityIdAndUserId(
		long p2pActivityId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP2pActivityIdAndUserId(p2pActivityId, userId, start,
			end);
	}

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
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findByP2pActivityIdAndUserId(
		long p2pActivityId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP2pActivityIdAndUserId(p2pActivityId, userId, start,
			end, orderByComparator);
	}

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
	public static com.liferay.lms.model.P2pActivityCorrections findByP2pActivityIdAndUserId_First(
		long p2pActivityId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP2pActivityIdAndUserId_First(p2pActivityId, userId,
			orderByComparator);
	}

	/**
	* Returns the first p2p activity corrections in the ordered set where p2pActivityId = &#63; and userId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections fetchByP2pActivityIdAndUserId_First(
		long p2pActivityId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByP2pActivityIdAndUserId_First(p2pActivityId, userId,
			orderByComparator);
	}

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
	public static com.liferay.lms.model.P2pActivityCorrections findByP2pActivityIdAndUserId_Last(
		long p2pActivityId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP2pActivityIdAndUserId_Last(p2pActivityId, userId,
			orderByComparator);
	}

	/**
	* Returns the last p2p activity corrections in the ordered set where p2pActivityId = &#63; and userId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.P2pActivityCorrections fetchByP2pActivityIdAndUserId_Last(
		long p2pActivityId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByP2pActivityIdAndUserId_Last(p2pActivityId, userId,
			orderByComparator);
	}

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
	public static com.liferay.lms.model.P2pActivityCorrections[] findByP2pActivityIdAndUserId_PrevAndNext(
		long p2pActivityCorrectionsId, long p2pActivityId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchP2pActivityCorrectionsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP2pActivityIdAndUserId_PrevAndNext(p2pActivityCorrectionsId,
			p2pActivityId, userId, orderByComparator);
	}

	/**
	* Returns all the p2p activity correctionses.
	*
	* @return the p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.P2pActivityCorrections> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the p2p activity correctionses where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes all the p2p activity correctionses where p2pActivityId = &#63; from the database.
	*
	* @param p2pActivityId the p2p activity ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByP2pActivityId(long p2pActivityId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByP2pActivityId(p2pActivityId);
	}

	/**
	* Removes all the p2p activity correctionses where actId = &#63; and userId = &#63; from the database.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByActIdAndUserId(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByActIdAndUserId(actId, userId);
	}

	/**
	* Removes all the p2p activity correctionses where p2pActivityId = &#63; and userId = &#63; from the database.
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByP2pActivityIdAndUserId(long p2pActivityId,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByP2pActivityIdAndUserId(p2pActivityId, userId);
	}

	/**
	* Removes all the p2p activity correctionses from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of p2p activity correctionses where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of p2p activity correctionses where p2pActivityId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @return the number of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByP2pActivityId(long p2pActivityId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByP2pActivityId(p2pActivityId);
	}

	/**
	* Returns the number of p2p activity correctionses where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the number of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByActIdAndUserId(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByActIdAndUserId(actId, userId);
	}

	/**
	* Returns the number of p2p activity correctionses where p2pActivityId = &#63; and userId = &#63;.
	*
	* @param p2pActivityId the p2p activity ID
	* @param userId the user ID
	* @return the number of matching p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByP2pActivityIdAndUserId(long p2pActivityId,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByP2pActivityIdAndUserId(p2pActivityId, userId);
	}

	/**
	* Returns the number of p2p activity correctionses.
	*
	* @return the number of p2p activity correctionses
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static P2pActivityCorrectionsPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (P2pActivityCorrectionsPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					P2pActivityCorrectionsPersistence.class.getName());

			ReferenceRegistry.registerReference(P2pActivityCorrectionsUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(P2pActivityCorrectionsPersistence persistence) {
	}

	private static P2pActivityCorrectionsPersistence _persistence;
}