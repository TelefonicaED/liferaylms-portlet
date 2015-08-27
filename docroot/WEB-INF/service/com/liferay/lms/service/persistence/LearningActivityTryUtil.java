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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the learning activity try service. This utility wraps {@link LearningActivityTryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LearningActivityTryPersistence
 * @see LearningActivityTryPersistenceImpl
 * @generated
 */
public class LearningActivityTryUtil {
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
	public static void clearCache(LearningActivityTry learningActivityTry) {
		getPersistence().clearCache(learningActivityTry);
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
	public static List<LearningActivityTry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LearningActivityTry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LearningActivityTry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static LearningActivityTry update(
		LearningActivityTry learningActivityTry, boolean merge)
		throws SystemException {
		return getPersistence().update(learningActivityTry, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static LearningActivityTry update(
		LearningActivityTry learningActivityTry, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(learningActivityTry, merge, serviceContext);
	}

	/**
	* Caches the learning activity try in the entity cache if it is enabled.
	*
	* @param learningActivityTry the learning activity try
	*/
	public static void cacheResult(
		com.liferay.lms.model.LearningActivityTry learningActivityTry) {
		getPersistence().cacheResult(learningActivityTry);
	}

	/**
	* Caches the learning activity tries in the entity cache if it is enabled.
	*
	* @param learningActivityTries the learning activity tries
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.LearningActivityTry> learningActivityTries) {
		getPersistence().cacheResult(learningActivityTries);
	}

	/**
	* Creates a new learning activity try with the primary key. Does not add the learning activity try to the database.
	*
	* @param latId the primary key for the new learning activity try
	* @return the new learning activity try
	*/
	public static com.liferay.lms.model.LearningActivityTry create(long latId) {
		return getPersistence().create(latId);
	}

	/**
	* Removes the learning activity try with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param latId the primary key of the learning activity try
	* @return the learning activity try that was removed
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityTry remove(long latId)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(latId);
	}

	public static com.liferay.lms.model.LearningActivityTry updateImpl(
		com.liferay.lms.model.LearningActivityTry learningActivityTry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(learningActivityTry, merge);
	}

	/**
	* Returns the learning activity try with the primary key or throws a {@link com.liferay.lms.NoSuchLearningActivityTryException} if it could not be found.
	*
	* @param latId the primary key of the learning activity try
	* @return the learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityTry findByPrimaryKey(
		long latId)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(latId);
	}

	/**
	* Returns the learning activity try with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param latId the primary key of the learning activity try
	* @return the learning activity try, or <code>null</code> if a learning activity try with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityTry fetchByPrimaryKey(
		long latId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(latId);
	}

	/**
	* Returns all the learning activity tries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivityTry> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityTry> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityTry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first learning activity try in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityTry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first learning activity try in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityTry fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last learning activity try in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityTry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last learning activity try in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityTry fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityTry[] findByUuid_PrevAndNext(
		long latId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(latId, uuid, orderByComparator);
	}

	/**
	* Returns all the learning activity tries where actId = &#63;.
	*
	* @param actId the act ID
	* @return the matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivityTry> findByact(
		long actId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByact(actId);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityTry> findByact(
		long actId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByact(actId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityTry> findByact(
		long actId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByact(actId, start, end, orderByComparator);
	}

	/**
	* Returns the first learning activity try in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityTry findByact_First(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByact_First(actId, orderByComparator);
	}

	/**
	* Returns the first learning activity try in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityTry fetchByact_First(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByact_First(actId, orderByComparator);
	}

	/**
	* Returns the last learning activity try in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try
	* @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityTry findByact_Last(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByact_Last(actId, orderByComparator);
	}

	/**
	* Returns the last learning activity try in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityTry fetchByact_Last(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByact_Last(actId, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityTry[] findByact_PrevAndNext(
		long latId, long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByact_PrevAndNext(latId, actId, orderByComparator);
	}

	/**
	* Returns all the learning activity tries where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivityTry> findByact_u(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByact_u(actId, userId);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityTry> findByact_u(
		long actId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByact_u(actId, userId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityTry> findByact_u(
		long actId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByact_u(actId, userId, start, end, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityTry findByact_u_First(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByact_u_First(actId, userId, orderByComparator);
	}

	/**
	* Returns the first learning activity try in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityTry fetchByact_u_First(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByact_u_First(actId, userId, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityTry findByact_u_Last(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByact_u_Last(actId, userId, orderByComparator);
	}

	/**
	* Returns the last learning activity try in the ordered set where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityTry fetchByact_u_Last(
		long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByact_u_Last(actId, userId, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityTry[] findByact_u_PrevAndNext(
		long latId, long actId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityTryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByact_u_PrevAndNext(latId, actId, userId,
			orderByComparator);
	}

	/**
	* Returns all the learning activity tries.
	*
	* @return the learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivityTry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityTry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityTry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the learning activity tries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes all the learning activity tries where actId = &#63; from the database.
	*
	* @param actId the act ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByact(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByact(actId);
	}

	/**
	* Removes all the learning activity tries where actId = &#63; and userId = &#63; from the database.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByact_u(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByact_u(actId, userId);
	}

	/**
	* Removes all the learning activity tries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of learning activity tries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of learning activity tries where actId = &#63;.
	*
	* @param actId the act ID
	* @return the number of matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByact(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByact(actId);
	}

	/**
	* Returns the number of learning activity tries where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the number of matching learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByact_u(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByact_u(actId, userId);
	}

	/**
	* Returns the number of learning activity tries.
	*
	* @return the number of learning activity tries
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LearningActivityTryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LearningActivityTryPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					LearningActivityTryPersistence.class.getName());

			ReferenceRegistry.registerReference(LearningActivityTryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(LearningActivityTryPersistence persistence) {
	}

	private static LearningActivityTryPersistence _persistence;
}