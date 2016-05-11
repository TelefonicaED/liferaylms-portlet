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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the learning activity result service. This utility wraps {@link LearningActivityResultPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LearningActivityResultPersistence
 * @see LearningActivityResultPersistenceImpl
 * @generated
 */
public class LearningActivityResultUtil {
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
	public static void clearCache(LearningActivityResult learningActivityResult) {
		getPersistence().clearCache(learningActivityResult);
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
	public static List<LearningActivityResult> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LearningActivityResult> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LearningActivityResult> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static LearningActivityResult update(
		LearningActivityResult learningActivityResult, boolean merge)
		throws SystemException {
		return getPersistence().update(learningActivityResult, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static LearningActivityResult update(
		LearningActivityResult learningActivityResult, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(learningActivityResult, merge, serviceContext);
	}

	/**
	* Caches the learning activity result in the entity cache if it is enabled.
	*
	* @param learningActivityResult the learning activity result
	*/
	public static void cacheResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult) {
		getPersistence().cacheResult(learningActivityResult);
	}

	/**
	* Caches the learning activity results in the entity cache if it is enabled.
	*
	* @param learningActivityResults the learning activity results
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.LearningActivityResult> learningActivityResults) {
		getPersistence().cacheResult(learningActivityResults);
	}

	/**
	* Creates a new learning activity result with the primary key. Does not add the learning activity result to the database.
	*
	* @param larId the primary key for the new learning activity result
	* @return the new learning activity result
	*/
	public static com.liferay.lms.model.LearningActivityResult create(
		long larId) {
		return getPersistence().create(larId);
	}

	/**
	* Removes the learning activity result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param larId the primary key of the learning activity result
	* @return the learning activity result that was removed
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult remove(
		long larId)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(larId);
	}

	public static com.liferay.lms.model.LearningActivityResult updateImpl(
		com.liferay.lms.model.LearningActivityResult learningActivityResult,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(learningActivityResult, merge);
	}

	/**
	* Returns the learning activity result with the primary key or throws a {@link com.liferay.lms.NoSuchLearningActivityResultException} if it could not be found.
	*
	* @param larId the primary key of the learning activity result
	* @return the learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult findByPrimaryKey(
		long larId)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(larId);
	}

	/**
	* Returns the learning activity result with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param larId the primary key of the learning activity result
	* @return the learning activity result, or <code>null</code> if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult fetchByPrimaryKey(
		long larId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(larId);
	}

	/**
	* Returns all the learning activity results where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first learning activity result in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first learning activity result in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last learning activity result in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last learning activity result in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityResult[] findByUuid_PrevAndNext(
		long larId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(larId, uuid, orderByComparator);
	}

	/**
	* Returns the learning activity result where actId = &#63; and userId = &#63; or throws a {@link com.liferay.lms.NoSuchLearningActivityResultException} if it could not be found.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult findByact_user(
		long actId, long userId)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByact_user(actId, userId);
	}

	/**
	* Returns the learning activity result where actId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult fetchByact_user(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByact_user(actId, userId);
	}

	/**
	* Returns the learning activity result where actId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult fetchByact_user(
		long actId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByact_user(actId, userId, retrieveFromCache);
	}

	/**
	* Returns all the learning activity results where actId = &#63; and passed = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @return the matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByap(
		long actId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByap(actId, passed);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByap(
		long actId, boolean passed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByap(actId, passed, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByap(
		long actId, boolean passed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByap(actId, passed, start, end, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityResult findByap_First(
		long actId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByap_First(actId, passed, orderByComparator);
	}

	/**
	* Returns the first learning activity result in the ordered set where actId = &#63; and passed = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult fetchByap_First(
		long actId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByap_First(actId, passed, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityResult findByap_Last(
		long actId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByap_Last(actId, passed, orderByComparator);
	}

	/**
	* Returns the last learning activity result in the ordered set where actId = &#63; and passed = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult fetchByap_Last(
		long actId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByap_Last(actId, passed, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityResult[] findByap_PrevAndNext(
		long larId, long actId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByap_PrevAndNext(larId, actId, passed, orderByComparator);
	}

	/**
	* Returns all the learning activity results where actId = &#63; and passed = &#63; and endDate = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param endDate the end date
	* @return the matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByapd(
		long actId, boolean passed, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByapd(actId, passed, endDate);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByapd(
		long actId, boolean passed, java.util.Date endDate, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByapd(actId, passed, endDate, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByapd(
		long actId, boolean passed, java.util.Date endDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByapd(actId, passed, endDate, start, end,
			orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityResult findByapd_First(
		long actId, boolean passed, java.util.Date endDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByapd_First(actId, passed, endDate, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityResult fetchByapd_First(
		long actId, boolean passed, java.util.Date endDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByapd_First(actId, passed, endDate, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityResult findByapd_Last(
		long actId, boolean passed, java.util.Date endDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByapd_Last(actId, passed, endDate, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityResult fetchByapd_Last(
		long actId, boolean passed, java.util.Date endDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByapd_Last(actId, passed, endDate, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityResult[] findByapd_PrevAndNext(
		long larId, long actId, boolean passed, java.util.Date endDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByapd_PrevAndNext(larId, actId, passed, endDate,
			orderByComparator);
	}

	/**
	* Returns all the learning activity results where actId = &#63;.
	*
	* @param actId the act ID
	* @return the matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByac(
		long actId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByac(actId);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByac(
		long actId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByac(actId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByac(
		long actId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByac(actId, start, end, orderByComparator);
	}

	/**
	* Returns the first learning activity result in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult findByac_First(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByac_First(actId, orderByComparator);
	}

	/**
	* Returns the first learning activity result in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult fetchByac_First(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByac_First(actId, orderByComparator);
	}

	/**
	* Returns the last learning activity result in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult findByac_Last(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByac_Last(actId, orderByComparator);
	}

	/**
	* Returns the last learning activity result in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult fetchByac_Last(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByac_Last(actId, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityResult[] findByac_PrevAndNext(
		long larId, long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByac_PrevAndNext(larId, actId, orderByComparator);
	}

	/**
	* Returns all the learning activity results where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByuser(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByuser(userId);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByuser(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByuser(userId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findByuser(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByuser(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first learning activity result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult findByuser_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByuser_First(userId, orderByComparator);
	}

	/**
	* Returns the first learning activity result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult fetchByuser_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByuser_First(userId, orderByComparator);
	}

	/**
	* Returns the last learning activity result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result
	* @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult findByuser_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByuser_Last(userId, orderByComparator);
	}

	/**
	* Returns the last learning activity result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult fetchByuser_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByuser_Last(userId, orderByComparator);
	}

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
	public static com.liferay.lms.model.LearningActivityResult[] findByuser_PrevAndNext(
		long larId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByuser_PrevAndNext(larId, userId, orderByComparator);
	}

	/**
	* Returns all the learning activity results.
	*
	* @return the learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the learning activity results where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the learning activity result where actId = &#63; and userId = &#63; from the database.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the learning activity result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult removeByact_user(
		long actId, long userId)
		throws com.liferay.lms.NoSuchLearningActivityResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByact_user(actId, userId);
	}

	/**
	* Removes all the learning activity results where actId = &#63; and passed = &#63; from the database.
	*
	* @param actId the act ID
	* @param passed the passed
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByap(long actId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByap(actId, passed);
	}

	/**
	* Removes all the learning activity results where actId = &#63; and passed = &#63; and endDate = &#63; from the database.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param endDate the end date
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByapd(long actId, boolean passed,
		java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByapd(actId, passed, endDate);
	}

	/**
	* Removes all the learning activity results where actId = &#63; from the database.
	*
	* @param actId the act ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByac(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByac(actId);
	}

	/**
	* Removes all the learning activity results where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByuser(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByuser(userId);
	}

	/**
	* Removes all the learning activity results from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of learning activity results where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of learning activity results where actId = &#63; and userId = &#63;.
	*
	* @param actId the act ID
	* @param userId the user ID
	* @return the number of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByact_user(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByact_user(actId, userId);
	}

	/**
	* Returns the number of learning activity results where actId = &#63; and passed = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @return the number of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByap(long actId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByap(actId, passed);
	}

	/**
	* Returns the number of learning activity results where actId = &#63; and passed = &#63; and endDate = &#63;.
	*
	* @param actId the act ID
	* @param passed the passed
	* @param endDate the end date
	* @return the number of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByapd(long actId, boolean passed,
		java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByapd(actId, passed, endDate);
	}

	/**
	* Returns the number of learning activity results where actId = &#63;.
	*
	* @param actId the act ID
	* @return the number of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByac(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByac(actId);
	}

	/**
	* Returns the number of learning activity results where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByuser(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByuser(userId);
	}

	/**
	* Returns the number of learning activity results.
	*
	* @return the number of learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LearningActivityResultPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LearningActivityResultPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					LearningActivityResultPersistence.class.getName());

			ReferenceRegistry.registerReference(LearningActivityResultUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(LearningActivityResultPersistence persistence) {
	}

	private static LearningActivityResultPersistence _persistence;
}