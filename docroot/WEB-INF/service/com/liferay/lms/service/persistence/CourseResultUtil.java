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

import com.liferay.lms.model.CourseResult;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the course result service. This utility wraps {@link CourseResultPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CourseResultPersistence
 * @see CourseResultPersistenceImpl
 * @generated
 */
public class CourseResultUtil {
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
	public static void clearCache(CourseResult courseResult) {
		getPersistence().clearCache(courseResult);
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
	public static List<CourseResult> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CourseResult> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CourseResult> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static CourseResult update(CourseResult courseResult, boolean merge)
		throws SystemException {
		return getPersistence().update(courseResult, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static CourseResult update(CourseResult courseResult, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(courseResult, merge, serviceContext);
	}

	/**
	* Caches the course result in the entity cache if it is enabled.
	*
	* @param courseResult the course result
	*/
	public static void cacheResult(
		com.liferay.lms.model.CourseResult courseResult) {
		getPersistence().cacheResult(courseResult);
	}

	/**
	* Caches the course results in the entity cache if it is enabled.
	*
	* @param courseResults the course results
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.CourseResult> courseResults) {
		getPersistence().cacheResult(courseResults);
	}

	/**
	* Creates a new course result with the primary key. Does not add the course result to the database.
	*
	* @param crId the primary key for the new course result
	* @return the new course result
	*/
	public static com.liferay.lms.model.CourseResult create(long crId) {
		return getPersistence().create(crId);
	}

	/**
	* Removes the course result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param crId the primary key of the course result
	* @return the course result that was removed
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult remove(long crId)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(crId);
	}

	public static com.liferay.lms.model.CourseResult updateImpl(
		com.liferay.lms.model.CourseResult courseResult, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(courseResult, merge);
	}

	/**
	* Returns the course result with the primary key or throws a {@link com.liferay.lms.NoSuchCourseResultException} if it could not be found.
	*
	* @param crId the primary key of the course result
	* @return the course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByPrimaryKey(long crId)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(crId);
	}

	/**
	* Returns the course result with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param crId the primary key of the course result
	* @return the course result, or <code>null</code> if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByPrimaryKey(
		long crId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(crId);
	}

	/**
	* Returns the course result where userId = &#63; and courseId = &#63; or throws a {@link com.liferay.lms.NoSuchCourseResultException} if it could not be found.
	*
	* @param userId the user ID
	* @param courseId the course ID
	* @return the matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByuc(long userId,
		long courseId)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByuc(userId, courseId);
	}

	/**
	* Returns the course result where userId = &#63; and courseId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param courseId the course ID
	* @return the matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByuc(long userId,
		long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByuc(userId, courseId);
	}

	/**
	* Returns the course result where userId = &#63; and courseId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param courseId the course ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByuc(long userId,
		long courseId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByuc(userId, courseId, retrieveFromCache);
	}

	/**
	* Returns all the course results where courseId = &#63; and passed = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByc(
		long courseId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByc(courseId, passed);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByc(
		long courseId, boolean passed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByc(courseId, passed, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByc(
		long courseId, boolean passed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByc(courseId, passed, start, end, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passed = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByc_First(
		long courseId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByc_First(courseId, passed, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passed = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByc_First(
		long courseId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByc_First(courseId, passed, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passed = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByc_Last(
		long courseId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByc_Last(courseId, passed, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passed = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByc_Last(
		long courseId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByc_Last(courseId, passed, orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where courseId = &#63; and passed = &#63;.
	*
	* @param crId the primary key of the current course result
	* @param courseId the course ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByc_PrevAndNext(
		long crId, long courseId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByc_PrevAndNext(crId, courseId, passed,
			orderByComparator);
	}

	/**
	* Returns all the course results where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the course results where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the course results where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where userId = &#63;.
	*
	* @param crId the primary key of the current course result
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByUserId_PrevAndNext(
		long crId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(crId, userId, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63;.
	*
	* @param courseId the course ID
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseId(
		long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCourseId(courseId);
	}

	/**
	* Returns a range of all the course results where courseId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseId(
		long courseId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCourseId(courseId, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseId(
		long courseId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseId(courseId, start, end, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63;.
	*
	* @param courseId the course ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseId_First(
		long courseId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCourseId_First(courseId, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63;.
	*
	* @param courseId the course ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseId_First(
		long courseId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseId_First(courseId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63;.
	*
	* @param courseId the course ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseId_Last(
		long courseId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCourseId_Last(courseId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63;.
	*
	* @param courseId the course ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseId_Last(
		long courseId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCourseId_Last(courseId, orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where courseId = &#63;.
	*
	* @param crId the primary key of the current course result
	* @param courseId the course ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByCourseId_PrevAndNext(
		long crId, long courseId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseId_PrevAndNext(crId, courseId, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passed = &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedMultipleUserId(
		long courseId, boolean passed, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserId(courseId, passed, userId);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passed = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedMultipleUserId(
		long courseId, boolean passed, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserId(courseId, passed,
			userId, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passed = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedMultipleUserId(
		long courseId, boolean passed, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserId(courseId, passed,
			userId, start, end, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passed = &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdPassedMultipleUserId_First(
		long courseId, boolean passed, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserId_First(courseId, passed,
			userId, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passed = &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdPassedMultipleUserId_First(
		long courseId, boolean passed, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdPassedMultipleUserId_First(courseId, passed,
			userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passed = &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdPassedMultipleUserId_Last(
		long courseId, boolean passed, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserId_Last(courseId, passed,
			userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passed = &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdPassedMultipleUserId_Last(
		long courseId, boolean passed, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdPassedMultipleUserId_Last(courseId, passed,
			userId, orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where courseId = &#63; and passed = &#63; and userId = &#63;.
	*
	* @param crId the primary key of the current course result
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByCourseIdPassedMultipleUserId_PrevAndNext(
		long crId, long courseId, boolean passed, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserId_PrevAndNext(crId,
			courseId, passed, userId, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passed = &#63; and userId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userIds the user IDs
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedMultipleUserId(
		long courseId, boolean passed, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserId(courseId, passed, userIds);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passed = &#63; and userId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedMultipleUserId(
		long courseId, boolean passed, long[] userIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserId(courseId, passed,
			userIds, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passed = &#63; and userId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedMultipleUserId(
		long courseId, boolean passed, long[] userIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserId(courseId, passed,
			userIds, start, end, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdMultipleUserIdStarted(
		long courseId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdStarted(courseId, userId);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdMultipleUserIdStarted(
		long courseId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdStarted(courseId, userId,
			start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdMultipleUserIdStarted(
		long courseId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdStarted(courseId, userId,
			start, end, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdMultipleUserIdStarted_First(
		long courseId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdStarted_First(courseId, userId,
			orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdMultipleUserIdStarted_First(
		long courseId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdMultipleUserIdStarted_First(courseId,
			userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdMultipleUserIdStarted_Last(
		long courseId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdStarted_Last(courseId, userId,
			orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdMultipleUserIdStarted_Last(
		long courseId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdMultipleUserIdStarted_Last(courseId, userId,
			orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where courseId = &#63; and userId = &#63;.
	*
	* @param crId the primary key of the current course result
	* @param courseId the course ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByCourseIdMultipleUserIdStarted_PrevAndNext(
		long crId, long courseId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdStarted_PrevAndNext(crId,
			courseId, userId, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and userId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param userIds the user IDs
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdMultipleUserIdStarted(
		long courseId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdStarted(courseId, userIds);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and userId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdMultipleUserIdStarted(
		long courseId, long[] userIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdStarted(courseId, userIds,
			start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and userId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdMultipleUserIdStarted(
		long courseId, long[] userIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdStarted(courseId, userIds,
			start, end, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdMultipleUserIdFinished(
		long courseId, java.util.Date passedDate, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdFinished(courseId, passedDate,
			userId);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdMultipleUserIdFinished(
		long courseId, java.util.Date passedDate, long userId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdFinished(courseId, passedDate,
			userId, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdMultipleUserIdFinished(
		long courseId, java.util.Date passedDate, long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdFinished(courseId, passedDate,
			userId, start, end, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdMultipleUserIdFinished_First(
		long courseId, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdFinished_First(courseId,
			passedDate, userId, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdMultipleUserIdFinished_First(
		long courseId, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdMultipleUserIdFinished_First(courseId,
			passedDate, userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdMultipleUserIdFinished_Last(
		long courseId, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdFinished_Last(courseId,
			passedDate, userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdMultipleUserIdFinished_Last(
		long courseId, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdMultipleUserIdFinished_Last(courseId,
			passedDate, userId, orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param crId the primary key of the current course result
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByCourseIdMultipleUserIdFinished_PrevAndNext(
		long crId, long courseId, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdFinished_PrevAndNext(crId,
			courseId, passedDate, userId, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdMultipleUserIdFinished(
		long courseId, java.util.Date passedDate, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdFinished(courseId, passedDate,
			userIds);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdMultipleUserIdFinished(
		long courseId, java.util.Date passedDate, long[] userIds, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdFinished(courseId, passedDate,
			userIds, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdMultipleUserIdFinished(
		long courseId, java.util.Date passedDate, long[] userIds, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdMultipleUserIdFinished(courseId, passedDate,
			userIds, start, end, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63;.
	*
	* @param courseId the course ID
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdStarted(
		long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCourseIdStarted(courseId);
	}

	/**
	* Returns a range of all the course results where courseId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdStarted(
		long courseId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCourseIdStarted(courseId, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdStarted(
		long courseId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdStarted(courseId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63;.
	*
	* @param courseId the course ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdStarted_First(
		long courseId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdStarted_First(courseId, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63;.
	*
	* @param courseId the course ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdStarted_First(
		long courseId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdStarted_First(courseId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63;.
	*
	* @param courseId the course ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdStarted_Last(
		long courseId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdStarted_Last(courseId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63;.
	*
	* @param courseId the course ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdStarted_Last(
		long courseId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdStarted_Last(courseId, orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where courseId = &#63;.
	*
	* @param crId the primary key of the current course result
	* @param courseId the course ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByCourseIdStarted_PrevAndNext(
		long crId, long courseId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdStarted_PrevAndNext(crId, courseId,
			orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passedDate IS NOT &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdFinished(
		long courseId, java.util.Date passedDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCourseIdFinished(courseId, passedDate);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passedDate IS NOT &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdFinished(
		long courseId, java.util.Date passedDate, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdFinished(courseId, passedDate, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passedDate IS NOT &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdFinished(
		long courseId, java.util.Date passedDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdFinished(courseId, passedDate, start, end,
			orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdFinished_First(
		long courseId, java.util.Date passedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdFinished_First(courseId, passedDate,
			orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdFinished_First(
		long courseId, java.util.Date passedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdFinished_First(courseId, passedDate,
			orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdFinished_Last(
		long courseId, java.util.Date passedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdFinished_Last(courseId, passedDate,
			orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdFinished_Last(
		long courseId, java.util.Date passedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdFinished_Last(courseId, passedDate,
			orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63;.
	*
	* @param crId the primary key of the current course result
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByCourseIdFinished_PrevAndNext(
		long crId, long courseId, java.util.Date passedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdFinished_PrevAndNext(crId, courseId,
			passedDate, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserIdFinished(courseId,
			passed, passedDate, userId);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long userId,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserIdFinished(courseId,
			passed, passedDate, userId, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserIdFinished(courseId,
			passed, passedDate, userId, start, end, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdPassedMultipleUserIdFinished_First(
		long courseId, boolean passed, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserIdFinished_First(courseId,
			passed, passedDate, userId, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdPassedMultipleUserIdFinished_First(
		long courseId, boolean passed, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdPassedMultipleUserIdFinished_First(courseId,
			passed, passedDate, userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdPassedMultipleUserIdFinished_Last(
		long courseId, boolean passed, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserIdFinished_Last(courseId,
			passed, passedDate, userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdPassedMultipleUserIdFinished_Last(
		long courseId, boolean passed, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdPassedMultipleUserIdFinished_Last(courseId,
			passed, passedDate, userId, orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param crId the primary key of the current course result
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByCourseIdPassedMultipleUserIdFinished_PrevAndNext(
		long crId, long courseId, boolean passed, java.util.Date passedDate,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserIdFinished_PrevAndNext(crId,
			courseId, passed, passedDate, userId, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserIdFinished(courseId,
			passed, passedDate, userIds);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate,
		long[] userIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserIdFinished(courseId,
			passed, passedDate, userIds, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate,
		long[] userIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedMultipleUserIdFinished(courseId,
			passed, passedDate, userIds, start, end, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedFinished(
		long courseId, boolean passed, java.util.Date passedDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedFinished(courseId, passed, passedDate);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedFinished(
		long courseId, boolean passed, java.util.Date passedDate, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedFinished(courseId, passed, passedDate,
			start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedFinished(
		long courseId, boolean passed, java.util.Date passedDate, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedFinished(courseId, passed, passedDate,
			start, end, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdPassedFinished_First(
		long courseId, boolean passed, java.util.Date passedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedFinished_First(courseId, passed,
			passedDate, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdPassedFinished_First(
		long courseId, boolean passed, java.util.Date passedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdPassedFinished_First(courseId, passed,
			passedDate, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdPassedFinished_Last(
		long courseId, boolean passed, java.util.Date passedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedFinished_Last(courseId, passed,
			passedDate, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdPassedFinished_Last(
		long courseId, boolean passed, java.util.Date passedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdPassedFinished_Last(courseId, passed,
			passedDate, orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63;.
	*
	* @param crId the primary key of the current course result
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByCourseIdPassedFinished_PrevAndNext(
		long crId, long courseId, boolean passed, java.util.Date passedDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedFinished_PrevAndNext(crId, courseId,
			passed, passedDate, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passed = &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedNotMultipleUserId(
		long courseId, boolean passed, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserId(courseId, passed,
			userId);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passed = &#63; and userId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedNotMultipleUserId(
		long courseId, boolean passed, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserId(courseId, passed,
			userId, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passed = &#63; and userId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedNotMultipleUserId(
		long courseId, boolean passed, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserId(courseId, passed,
			userId, start, end, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passed = &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdPassedNotMultipleUserId_First(
		long courseId, boolean passed, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserId_First(courseId,
			passed, userId, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passed = &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdPassedNotMultipleUserId_First(
		long courseId, boolean passed, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdPassedNotMultipleUserId_First(courseId,
			passed, userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passed = &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdPassedNotMultipleUserId_Last(
		long courseId, boolean passed, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserId_Last(courseId,
			passed, userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passed = &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdPassedNotMultipleUserId_Last(
		long courseId, boolean passed, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdPassedNotMultipleUserId_Last(courseId,
			passed, userId, orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where courseId = &#63; and passed = &#63; and userId &ne; &#63;.
	*
	* @param crId the primary key of the current course result
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByCourseIdPassedNotMultipleUserId_PrevAndNext(
		long crId, long courseId, boolean passed, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserId_PrevAndNext(crId,
			courseId, passed, userId, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passed = &#63; and userId &ne; all &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userIds the user IDs
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedNotMultipleUserId(
		long courseId, boolean passed, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserId(courseId, passed,
			userIds);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passed = &#63; and userId &ne; all &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedNotMultipleUserId(
		long courseId, boolean passed, long[] userIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserId(courseId, passed,
			userIds, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passed = &#63; and userId &ne; all &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedNotMultipleUserId(
		long courseId, boolean passed, long[] userIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserId(courseId, passed,
			userIds, start, end, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdNotMultipleUserIdStarted(
		long courseId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdStarted(courseId, userId);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and userId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdNotMultipleUserIdStarted(
		long courseId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdStarted(courseId, userId,
			start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and userId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdNotMultipleUserIdStarted(
		long courseId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdStarted(courseId, userId,
			start, end, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdNotMultipleUserIdStarted_First(
		long courseId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdStarted_First(courseId,
			userId, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdNotMultipleUserIdStarted_First(
		long courseId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdNotMultipleUserIdStarted_First(courseId,
			userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdNotMultipleUserIdStarted_Last(
		long courseId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdStarted_Last(courseId,
			userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdNotMultipleUserIdStarted_Last(
		long courseId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdNotMultipleUserIdStarted_Last(courseId,
			userId, orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where courseId = &#63; and userId &ne; &#63;.
	*
	* @param crId the primary key of the current course result
	* @param courseId the course ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByCourseIdNotMultipleUserIdStarted_PrevAndNext(
		long crId, long courseId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdStarted_PrevAndNext(crId,
			courseId, userId, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and userId &ne; all &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param userIds the user IDs
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdNotMultipleUserIdStarted(
		long courseId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdStarted(courseId, userIds);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and userId &ne; all &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdNotMultipleUserIdStarted(
		long courseId, long[] userIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdStarted(courseId, userIds,
			start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and userId &ne; all &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdNotMultipleUserIdStarted(
		long courseId, long[] userIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdStarted(courseId, userIds,
			start, end, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdNotMultipleUserIdFinished(
		long courseId, java.util.Date passedDate, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdFinished(courseId,
			passedDate, userId);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdNotMultipleUserIdFinished(
		long courseId, java.util.Date passedDate, long userId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdFinished(courseId,
			passedDate, userId, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdNotMultipleUserIdFinished(
		long courseId, java.util.Date passedDate, long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdFinished(courseId,
			passedDate, userId, start, end, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdNotMultipleUserIdFinished_First(
		long courseId, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdFinished_First(courseId,
			passedDate, userId, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdNotMultipleUserIdFinished_First(
		long courseId, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdNotMultipleUserIdFinished_First(courseId,
			passedDate, userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdNotMultipleUserIdFinished_Last(
		long courseId, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdFinished_Last(courseId,
			passedDate, userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdNotMultipleUserIdFinished_Last(
		long courseId, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdNotMultipleUserIdFinished_Last(courseId,
			passedDate, userId, orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param crId the primary key of the current course result
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByCourseIdNotMultipleUserIdFinished_PrevAndNext(
		long crId, long courseId, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdFinished_PrevAndNext(crId,
			courseId, passedDate, userId, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; all &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdNotMultipleUserIdFinished(
		long courseId, java.util.Date passedDate, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdFinished(courseId,
			passedDate, userIds);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; all &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdNotMultipleUserIdFinished(
		long courseId, java.util.Date passedDate, long[] userIds, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdFinished(courseId,
			passedDate, userIds, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; all &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdNotMultipleUserIdFinished(
		long courseId, java.util.Date passedDate, long[] userIds, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdNotMultipleUserIdFinished(courseId,
			passedDate, userIds, start, end, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedNotMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserIdFinished(courseId,
			passed, passedDate, userId);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedNotMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long userId,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserIdFinished(courseId,
			passed, passedDate, userId, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedNotMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserIdFinished(courseId,
			passed, passedDate, userId, start, end, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdPassedNotMultipleUserIdFinished_First(
		long courseId, boolean passed, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserIdFinished_First(courseId,
			passed, passedDate, userId, orderByComparator);
	}

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdPassedNotMultipleUserIdFinished_First(
		long courseId, boolean passed, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdPassedNotMultipleUserIdFinished_First(courseId,
			passed, passedDate, userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult findByCourseIdPassedNotMultipleUserIdFinished_Last(
		long courseId, boolean passed, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserIdFinished_Last(courseId,
			passed, passedDate, userId, orderByComparator);
	}

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult fetchByCourseIdPassedNotMultipleUserIdFinished_Last(
		long courseId, boolean passed, java.util.Date passedDate, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseIdPassedNotMultipleUserIdFinished_Last(courseId,
			passed, passedDate, userId, orderByComparator);
	}

	/**
	* Returns the course results before and after the current course result in the ordered set where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param crId the primary key of the current course result
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult[] findByCourseIdPassedNotMultipleUserIdFinished_PrevAndNext(
		long crId, long courseId, boolean passed, java.util.Date passedDate,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserIdFinished_PrevAndNext(crId,
			courseId, passed, passedDate, userId, orderByComparator);
	}

	/**
	* Returns all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; all &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedNotMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserIdFinished(courseId,
			passed, passedDate, userIds);
	}

	/**
	* Returns a range of all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; all &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedNotMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate,
		long[] userIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserIdFinished(courseId,
			passed, passedDate, userIds, start, end);
	}

	/**
	* Returns an ordered range of all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; all &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findByCourseIdPassedNotMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate,
		long[] userIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseIdPassedNotMultipleUserIdFinished(courseId,
			passed, passedDate, userIds, start, end, orderByComparator);
	}

	/**
	* Returns all the course results.
	*
	* @return the course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the course results.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the course results.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes the course result where userId = &#63; and courseId = &#63; from the database.
	*
	* @param userId the user ID
	* @param courseId the course ID
	* @return the course result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult removeByuc(long userId,
		long courseId)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByuc(userId, courseId);
	}

	/**
	* Removes all the course results where courseId = &#63; and passed = &#63; from the database.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByc(long courseId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByc(courseId, passed);
	}

	/**
	* Removes all the course results where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes all the course results where courseId = &#63; from the database.
	*
	* @param courseId the course ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCourseId(long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCourseId(courseId);
	}

	/**
	* Removes all the course results where courseId = &#63; and passed = &#63; and userId = &#63; from the database.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCourseIdPassedMultipleUserId(long courseId,
		boolean passed, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByCourseIdPassedMultipleUserId(courseId, passed, userId);
	}

	/**
	* Removes all the course results where courseId = &#63; and userId = &#63; from the database.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCourseIdMultipleUserIdStarted(long courseId,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCourseIdMultipleUserIdStarted(courseId, userId);
	}

	/**
	* Removes all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId = &#63; from the database.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCourseIdMultipleUserIdFinished(long courseId,
		java.util.Date passedDate, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByCourseIdMultipleUserIdFinished(courseId, passedDate, userId);
	}

	/**
	* Removes all the course results where courseId = &#63; from the database.
	*
	* @param courseId the course ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCourseIdStarted(long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCourseIdStarted(courseId);
	}

	/**
	* Removes all the course results where courseId = &#63; and passedDate IS NOT &#63; from the database.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCourseIdFinished(long courseId,
		java.util.Date passedDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCourseIdFinished(courseId, passedDate);
	}

	/**
	* Removes all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = &#63; from the database.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCourseIdPassedMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByCourseIdPassedMultipleUserIdFinished(courseId, passed,
			passedDate, userId);
	}

	/**
	* Removes all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; from the database.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCourseIdPassedFinished(long courseId,
		boolean passed, java.util.Date passedDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByCourseIdPassedFinished(courseId, passed, passedDate);
	}

	/**
	* Removes all the course results where courseId = &#63; and passed = &#63; and userId &ne; &#63; from the database.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCourseIdPassedNotMultipleUserId(long courseId,
		boolean passed, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByCourseIdPassedNotMultipleUserId(courseId, passed, userId);
	}

	/**
	* Removes all the course results where courseId = &#63; and userId &ne; &#63; from the database.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCourseIdNotMultipleUserIdStarted(long courseId,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByCourseIdNotMultipleUserIdStarted(courseId, userId);
	}

	/**
	* Removes all the course results where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; &#63; from the database.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCourseIdNotMultipleUserIdFinished(
		long courseId, java.util.Date passedDate, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByCourseIdNotMultipleUserIdFinished(courseId, passedDate,
			userId);
	}

	/**
	* Removes all the course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; &#63; from the database.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCourseIdPassedNotMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByCourseIdPassedNotMultipleUserIdFinished(courseId, passed,
			passedDate, userId);
	}

	/**
	* Removes all the course results from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of course results where userId = &#63; and courseId = &#63;.
	*
	* @param userId the user ID
	* @param courseId the course ID
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByuc(long userId, long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByuc(userId, courseId);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passed = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByc(long courseId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByc(courseId, passed);
	}

	/**
	* Returns the number of course results where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the number of course results where courseId = &#63;.
	*
	* @param courseId the course ID
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseId(long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCourseId(courseId);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passed = &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdPassedMultipleUserId(long courseId,
		boolean passed, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdPassedMultipleUserId(courseId, passed, userId);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passed = &#63; and userId = any &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userIds the user IDs
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdPassedMultipleUserId(long courseId,
		boolean passed, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdPassedMultipleUserId(courseId, passed,
			userIds);
	}

	/**
	* Returns the number of course results where courseId = &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdMultipleUserIdStarted(long courseId,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdMultipleUserIdStarted(courseId, userId);
	}

	/**
	* Returns the number of course results where courseId = &#63; and userId = any &#63;.
	*
	* @param courseId the course ID
	* @param userIds the user IDs
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdMultipleUserIdStarted(long courseId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdMultipleUserIdStarted(courseId, userIds);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdMultipleUserIdFinished(long courseId,
		java.util.Date passedDate, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdMultipleUserIdFinished(courseId, passedDate,
			userId);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passedDate IS NOT &#63; and userId = any &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdMultipleUserIdFinished(long courseId,
		java.util.Date passedDate, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdMultipleUserIdFinished(courseId, passedDate,
			userIds);
	}

	/**
	* Returns the number of course results where courseId = &#63;.
	*
	* @param courseId the course ID
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdStarted(long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCourseIdStarted(courseId);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passedDate IS NOT &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdFinished(long courseId,
		java.util.Date passedDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCourseIdFinished(courseId, passedDate);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdPassedMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdPassedMultipleUserIdFinished(courseId,
			passed, passedDate, userId);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId = any &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdPassedMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdPassedMultipleUserIdFinished(courseId,
			passed, passedDate, userIds);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdPassedFinished(long courseId,
		boolean passed, java.util.Date passedDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdPassedFinished(courseId, passed, passedDate);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passed = &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userId the user ID
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdPassedNotMultipleUserId(long courseId,
		boolean passed, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdPassedNotMultipleUserId(courseId, passed,
			userId);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passed = &#63; and userId &ne; all &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param userIds the user IDs
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdPassedNotMultipleUserId(long courseId,
		boolean passed, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdPassedNotMultipleUserId(courseId, passed,
			userIds);
	}

	/**
	* Returns the number of course results where courseId = &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param userId the user ID
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdNotMultipleUserIdStarted(long courseId,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdNotMultipleUserIdStarted(courseId, userId);
	}

	/**
	* Returns the number of course results where courseId = &#63; and userId &ne; all &#63;.
	*
	* @param courseId the course ID
	* @param userIds the user IDs
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdNotMultipleUserIdStarted(long courseId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdNotMultipleUserIdStarted(courseId, userIds);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userId the user ID
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdNotMultipleUserIdFinished(long courseId,
		java.util.Date passedDate, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdNotMultipleUserIdFinished(courseId,
			passedDate, userId);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passedDate IS NOT &#63; and userId &ne; all &#63;.
	*
	* @param courseId the course ID
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdNotMultipleUserIdFinished(long courseId,
		java.util.Date passedDate, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdNotMultipleUserIdFinished(courseId,
			passedDate, userIds);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userId the user ID
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdPassedNotMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdPassedNotMultipleUserIdFinished(courseId,
			passed, passedDate, userId);
	}

	/**
	* Returns the number of course results where courseId = &#63; and passed = &#63; and passedDate IS NOT &#63; and userId &ne; all &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param passedDate the passed date
	* @param userIds the user IDs
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseIdPassedNotMultipleUserIdFinished(
		long courseId, boolean passed, java.util.Date passedDate, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseIdPassedNotMultipleUserIdFinished(courseId,
			passed, passedDate, userIds);
	}

	/**
	* Returns the number of course results.
	*
	* @return the number of course results
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static CourseResultPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CourseResultPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					CourseResultPersistence.class.getName());

			ReferenceRegistry.registerReference(CourseResultUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(CourseResultPersistence persistence) {
	}

	private static CourseResultPersistence _persistence;
}