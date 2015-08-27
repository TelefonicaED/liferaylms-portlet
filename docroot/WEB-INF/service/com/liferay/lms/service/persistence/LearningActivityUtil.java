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

import com.liferay.lms.model.LearningActivity;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the learning activity service. This utility wraps {@link LearningActivityPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LearningActivityPersistence
 * @see LearningActivityPersistenceImpl
 * @generated
 */
public class LearningActivityUtil {
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
	public static void clearCache(LearningActivity learningActivity) {
		getPersistence().clearCache(learningActivity);
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
	public static List<LearningActivity> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LearningActivity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LearningActivity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static LearningActivity update(LearningActivity learningActivity,
		boolean merge) throws SystemException {
		return getPersistence().update(learningActivity, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static LearningActivity update(LearningActivity learningActivity,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(learningActivity, merge, serviceContext);
	}

	/**
	* Caches the learning activity in the entity cache if it is enabled.
	*
	* @param learningActivity the learning activity
	*/
	public static void cacheResult(
		com.liferay.lms.model.LearningActivity learningActivity) {
		getPersistence().cacheResult(learningActivity);
	}

	/**
	* Caches the learning activities in the entity cache if it is enabled.
	*
	* @param learningActivities the learning activities
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.LearningActivity> learningActivities) {
		getPersistence().cacheResult(learningActivities);
	}

	/**
	* Creates a new learning activity with the primary key. Does not add the learning activity to the database.
	*
	* @param actId the primary key for the new learning activity
	* @return the new learning activity
	*/
	public static com.liferay.lms.model.LearningActivity create(long actId) {
		return getPersistence().create(actId);
	}

	/**
	* Removes the learning activity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param actId the primary key of the learning activity
	* @return the learning activity that was removed
	* @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity remove(long actId)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(actId);
	}

	public static com.liferay.lms.model.LearningActivity updateImpl(
		com.liferay.lms.model.LearningActivity learningActivity, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(learningActivity, merge);
	}

	/**
	* Returns the learning activity with the primary key or throws a {@link com.liferay.lms.NoSuchLearningActivityException} if it could not be found.
	*
	* @param actId the primary key of the learning activity
	* @return the learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity findByPrimaryKey(
		long actId)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(actId);
	}

	/**
	* Returns the learning activity with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param actId the primary key of the learning activity
	* @return the learning activity, or <code>null</code> if a learning activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity fetchByPrimaryKey(
		long actId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(actId);
	}

	/**
	* Returns all the learning activities where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the learning activities where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @return the range of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the learning activities where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first learning activity in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first learning activity in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity, or <code>null</code> if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last learning activity in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last learning activity in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity, or <code>null</code> if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the learning activities before and after the current learning activity in the ordered set where uuid = &#63;.
	*
	* @param actId the primary key of the current learning activity
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity[] findByUuid_PrevAndNext(
		long actId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(actId, uuid, orderByComparator);
	}

	/**
	* Returns the learning activity where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lms.NoSuchLearningActivityException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the learning activity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching learning activity, or <code>null</code> if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the learning activity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching learning activity, or <code>null</code> if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Returns all the learning activities where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findByg(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByg(groupId);
	}

	/**
	* Returns a range of all the learning activities where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @return the range of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findByg(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByg(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the learning activities where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findByg(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByg(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first learning activity in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity findByg_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByg_First(groupId, orderByComparator);
	}

	/**
	* Returns the first learning activity in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity, or <code>null</code> if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity fetchByg_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByg_First(groupId, orderByComparator);
	}

	/**
	* Returns the last learning activity in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity findByg_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByg_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last learning activity in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity, or <code>null</code> if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity fetchByg_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByg_Last(groupId, orderByComparator);
	}

	/**
	* Returns the learning activities before and after the current learning activity in the ordered set where groupId = &#63;.
	*
	* @param actId the primary key of the current learning activity
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity[] findByg_PrevAndNext(
		long actId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByg_PrevAndNext(actId, groupId, orderByComparator);
	}

	/**
	* Returns all the learning activities that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching learning activities that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> filterFindByg(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByg(groupId);
	}

	/**
	* Returns a range of all the learning activities that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @return the range of matching learning activities that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> filterFindByg(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByg(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the learning activities that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activities that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> filterFindByg(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByg(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the learning activities before and after the current learning activity in the ordered set of learning activities that the user has permission to view where groupId = &#63;.
	*
	* @param actId the primary key of the current learning activity
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity[] filterFindByg_PrevAndNext(
		long actId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByg_PrevAndNext(actId, groupId, orderByComparator);
	}

	/**
	* Returns all the learning activities where groupId = &#63; and typeId = &#63;.
	*
	* @param groupId the group ID
	* @param typeId the type ID
	* @return the matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findByg_t(
		long groupId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByg_t(groupId, typeId);
	}

	/**
	* Returns a range of all the learning activities where groupId = &#63; and typeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param typeId the type ID
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @return the range of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findByg_t(
		long groupId, int typeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByg_t(groupId, typeId, start, end);
	}

	/**
	* Returns an ordered range of all the learning activities where groupId = &#63; and typeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param typeId the type ID
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findByg_t(
		long groupId, int typeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByg_t(groupId, typeId, start, end, orderByComparator);
	}

	/**
	* Returns the first learning activity in the ordered set where groupId = &#63; and typeId = &#63;.
	*
	* @param groupId the group ID
	* @param typeId the type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity findByg_t_First(
		long groupId, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByg_t_First(groupId, typeId, orderByComparator);
	}

	/**
	* Returns the first learning activity in the ordered set where groupId = &#63; and typeId = &#63;.
	*
	* @param groupId the group ID
	* @param typeId the type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity, or <code>null</code> if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity fetchByg_t_First(
		long groupId, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByg_t_First(groupId, typeId, orderByComparator);
	}

	/**
	* Returns the last learning activity in the ordered set where groupId = &#63; and typeId = &#63;.
	*
	* @param groupId the group ID
	* @param typeId the type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity findByg_t_Last(
		long groupId, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByg_t_Last(groupId, typeId, orderByComparator);
	}

	/**
	* Returns the last learning activity in the ordered set where groupId = &#63; and typeId = &#63;.
	*
	* @param groupId the group ID
	* @param typeId the type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity, or <code>null</code> if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity fetchByg_t_Last(
		long groupId, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByg_t_Last(groupId, typeId, orderByComparator);
	}

	/**
	* Returns the learning activities before and after the current learning activity in the ordered set where groupId = &#63; and typeId = &#63;.
	*
	* @param actId the primary key of the current learning activity
	* @param groupId the group ID
	* @param typeId the type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity[] findByg_t_PrevAndNext(
		long actId, long groupId, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByg_t_PrevAndNext(actId, groupId, typeId,
			orderByComparator);
	}

	/**
	* Returns all the learning activities that the user has permission to view where groupId = &#63; and typeId = &#63;.
	*
	* @param groupId the group ID
	* @param typeId the type ID
	* @return the matching learning activities that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> filterFindByg_t(
		long groupId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByg_t(groupId, typeId);
	}

	/**
	* Returns a range of all the learning activities that the user has permission to view where groupId = &#63; and typeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param typeId the type ID
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @return the range of matching learning activities that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> filterFindByg_t(
		long groupId, int typeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByg_t(groupId, typeId, start, end);
	}

	/**
	* Returns an ordered range of all the learning activities that the user has permissions to view where groupId = &#63; and typeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param typeId the type ID
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activities that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> filterFindByg_t(
		long groupId, int typeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByg_t(groupId, typeId, start, end,
			orderByComparator);
	}

	/**
	* Returns the learning activities before and after the current learning activity in the ordered set of learning activities that the user has permission to view where groupId = &#63; and typeId = &#63;.
	*
	* @param actId the primary key of the current learning activity
	* @param groupId the group ID
	* @param typeId the type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity[] filterFindByg_t_PrevAndNext(
		long actId, long groupId, int typeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByg_t_PrevAndNext(actId, groupId, typeId,
			orderByComparator);
	}

	/**
	* Returns all the learning activities where groupId = &#63; and weightinmodule = &#63;.
	*
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @return the matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findByg_m(
		long groupId, long weightinmodule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByg_m(groupId, weightinmodule);
	}

	/**
	* Returns a range of all the learning activities where groupId = &#63; and weightinmodule = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @return the range of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findByg_m(
		long groupId, long weightinmodule, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByg_m(groupId, weightinmodule, start, end);
	}

	/**
	* Returns an ordered range of all the learning activities where groupId = &#63; and weightinmodule = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findByg_m(
		long groupId, long weightinmodule, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByg_m(groupId, weightinmodule, start, end,
			orderByComparator);
	}

	/**
	* Returns the first learning activity in the ordered set where groupId = &#63; and weightinmodule = &#63;.
	*
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity findByg_m_First(
		long groupId, long weightinmodule,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByg_m_First(groupId, weightinmodule, orderByComparator);
	}

	/**
	* Returns the first learning activity in the ordered set where groupId = &#63; and weightinmodule = &#63;.
	*
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity, or <code>null</code> if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity fetchByg_m_First(
		long groupId, long weightinmodule,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByg_m_First(groupId, weightinmodule, orderByComparator);
	}

	/**
	* Returns the last learning activity in the ordered set where groupId = &#63; and weightinmodule = &#63;.
	*
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity findByg_m_Last(
		long groupId, long weightinmodule,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByg_m_Last(groupId, weightinmodule, orderByComparator);
	}

	/**
	* Returns the last learning activity in the ordered set where groupId = &#63; and weightinmodule = &#63;.
	*
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity, or <code>null</code> if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity fetchByg_m_Last(
		long groupId, long weightinmodule,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByg_m_Last(groupId, weightinmodule, orderByComparator);
	}

	/**
	* Returns the learning activities before and after the current learning activity in the ordered set where groupId = &#63; and weightinmodule = &#63;.
	*
	* @param actId the primary key of the current learning activity
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity[] findByg_m_PrevAndNext(
		long actId, long groupId, long weightinmodule,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByg_m_PrevAndNext(actId, groupId, weightinmodule,
			orderByComparator);
	}

	/**
	* Returns all the learning activities that the user has permission to view where groupId = &#63; and weightinmodule = &#63;.
	*
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @return the matching learning activities that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> filterFindByg_m(
		long groupId, long weightinmodule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByg_m(groupId, weightinmodule);
	}

	/**
	* Returns a range of all the learning activities that the user has permission to view where groupId = &#63; and weightinmodule = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @return the range of matching learning activities that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> filterFindByg_m(
		long groupId, long weightinmodule, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByg_m(groupId, weightinmodule, start, end);
	}

	/**
	* Returns an ordered range of all the learning activities that the user has permissions to view where groupId = &#63; and weightinmodule = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activities that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> filterFindByg_m(
		long groupId, long weightinmodule, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByg_m(groupId, weightinmodule, start, end,
			orderByComparator);
	}

	/**
	* Returns the learning activities before and after the current learning activity in the ordered set of learning activities that the user has permission to view where groupId = &#63; and weightinmodule = &#63;.
	*
	* @param actId the primary key of the current learning activity
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity[] filterFindByg_m_PrevAndNext(
		long actId, long groupId, long weightinmodule,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByg_m_PrevAndNext(actId, groupId, weightinmodule,
			orderByComparator);
	}

	/**
	* Returns all the learning activities where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @return the matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findBym(
		long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBym(moduleId);
	}

	/**
	* Returns a range of all the learning activities where moduleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param moduleId the module ID
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @return the range of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findBym(
		long moduleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBym(moduleId, start, end);
	}

	/**
	* Returns an ordered range of all the learning activities where moduleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param moduleId the module ID
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findBym(
		long moduleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBym(moduleId, start, end, orderByComparator);
	}

	/**
	* Returns the first learning activity in the ordered set where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity findBym_First(
		long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBym_First(moduleId, orderByComparator);
	}

	/**
	* Returns the first learning activity in the ordered set where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity, or <code>null</code> if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity fetchBym_First(
		long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchBym_First(moduleId, orderByComparator);
	}

	/**
	* Returns the last learning activity in the ordered set where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity findBym_Last(
		long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBym_Last(moduleId, orderByComparator);
	}

	/**
	* Returns the last learning activity in the ordered set where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity, or <code>null</code> if a matching learning activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity fetchBym_Last(
		long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchBym_Last(moduleId, orderByComparator);
	}

	/**
	* Returns the learning activities before and after the current learning activity in the ordered set where moduleId = &#63;.
	*
	* @param actId the primary key of the current learning activity
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity
	* @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity[] findBym_PrevAndNext(
		long actId, long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBym_PrevAndNext(actId, moduleId, orderByComparator);
	}

	/**
	* Returns all the learning activities.
	*
	* @return the learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the learning activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @return the range of learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the learning activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LearningActivity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the learning activities where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the learning activity where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the learning activity that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivity removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.NoSuchLearningActivityException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the learning activities where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByg(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByg(groupId);
	}

	/**
	* Removes all the learning activities where groupId = &#63; and typeId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param typeId the type ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByg_t(long groupId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByg_t(groupId, typeId);
	}

	/**
	* Removes all the learning activities where groupId = &#63; and weightinmodule = &#63; from the database.
	*
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByg_m(long groupId, long weightinmodule)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByg_m(groupId, weightinmodule);
	}

	/**
	* Removes all the learning activities where moduleId = &#63; from the database.
	*
	* @param moduleId the module ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeBym(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeBym(moduleId);
	}

	/**
	* Removes all the learning activities from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of learning activities where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of learning activities where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of learning activities where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static int countByg(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByg(groupId);
	}

	/**
	* Returns the number of learning activities that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching learning activities that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByg(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByg(groupId);
	}

	/**
	* Returns the number of learning activities where groupId = &#63; and typeId = &#63;.
	*
	* @param groupId the group ID
	* @param typeId the type ID
	* @return the number of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static int countByg_t(long groupId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByg_t(groupId, typeId);
	}

	/**
	* Returns the number of learning activities that the user has permission to view where groupId = &#63; and typeId = &#63;.
	*
	* @param groupId the group ID
	* @param typeId the type ID
	* @return the number of matching learning activities that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByg_t(long groupId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByg_t(groupId, typeId);
	}

	/**
	* Returns the number of learning activities where groupId = &#63; and weightinmodule = &#63;.
	*
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @return the number of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static int countByg_m(long groupId, long weightinmodule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByg_m(groupId, weightinmodule);
	}

	/**
	* Returns the number of learning activities that the user has permission to view where groupId = &#63; and weightinmodule = &#63;.
	*
	* @param groupId the group ID
	* @param weightinmodule the weightinmodule
	* @return the number of matching learning activities that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByg_m(long groupId, long weightinmodule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByg_m(groupId, weightinmodule);
	}

	/**
	* Returns the number of learning activities where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @return the number of matching learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static int countBym(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBym(moduleId);
	}

	/**
	* Returns the number of learning activities.
	*
	* @return the number of learning activities
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LearningActivityPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LearningActivityPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					LearningActivityPersistence.class.getName());

			ReferenceRegistry.registerReference(LearningActivityUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(LearningActivityPersistence persistence) {
	}

	private static LearningActivityPersistence _persistence;
}