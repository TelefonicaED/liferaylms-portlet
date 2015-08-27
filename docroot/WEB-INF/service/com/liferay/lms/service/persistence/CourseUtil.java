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

import com.liferay.lms.model.Course;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the course service. This utility wraps {@link CoursePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CoursePersistence
 * @see CoursePersistenceImpl
 * @generated
 */
public class CourseUtil {
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
	public static void clearCache(Course course) {
		getPersistence().clearCache(course);
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
	public static List<Course> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Course> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Course> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Course update(Course course, boolean merge)
		throws SystemException {
		return getPersistence().update(course, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Course update(Course course, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(course, merge, serviceContext);
	}

	/**
	* Caches the course in the entity cache if it is enabled.
	*
	* @param course the course
	*/
	public static void cacheResult(com.liferay.lms.model.Course course) {
		getPersistence().cacheResult(course);
	}

	/**
	* Caches the courses in the entity cache if it is enabled.
	*
	* @param courses the courses
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.Course> courses) {
		getPersistence().cacheResult(courses);
	}

	/**
	* Creates a new course with the primary key. Does not add the course to the database.
	*
	* @param courseId the primary key for the new course
	* @return the new course
	*/
	public static com.liferay.lms.model.Course create(long courseId) {
		return getPersistence().create(courseId);
	}

	/**
	* Removes the course with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param courseId the primary key of the course
	* @return the course that was removed
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course remove(long courseId)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(courseId);
	}

	public static com.liferay.lms.model.Course updateImpl(
		com.liferay.lms.model.Course course, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(course, merge);
	}

	/**
	* Returns the course with the primary key or throws a {@link com.liferay.lms.NoSuchCourseException} if it could not be found.
	*
	* @param courseId the primary key of the course
	* @return the course
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByPrimaryKey(long courseId)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(courseId);
	}

	/**
	* Returns the course with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param courseId the primary key of the course
	* @return the course, or <code>null</code> if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByPrimaryKey(long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(courseId);
	}

	/**
	* Returns all the courses where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the courses where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @return the range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the courses where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the courses before and after the current course in the ordered set where uuid = &#63;.
	*
	* @param courseId the primary key of the current course
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course[] findByUuid_PrevAndNext(
		long courseId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(courseId, uuid, orderByComparator);
	}

	/**
	* Returns the course where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lms.NoSuchCourseException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the course where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the course where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Returns all the courses where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the courses where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @return the range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the courses where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the courses before and after the current course in the ordered set where groupId = &#63;.
	*
	* @param courseId the primary key of the current course
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course[] findByGroupId_PrevAndNext(
		long courseId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(courseId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the courses that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the courses that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @return the range of matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the courses that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the courses before and after the current course in the ordered set of courses that the user has permission to view where groupId = &#63;.
	*
	* @param courseId the primary key of the current course
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course[] filterFindByGroupId_PrevAndNext(
		long courseId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(courseId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the courses where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByGroupIdClosed(
		long groupId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupIdClosed(groupId, closed);
	}

	/**
	* Returns a range of all the courses where groupId = &#63; and closed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param closed the closed
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @return the range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByGroupIdClosed(
		long groupId, boolean closed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupIdClosed(groupId, closed, start, end);
	}

	/**
	* Returns an ordered range of all the courses where groupId = &#63; and closed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param closed the closed
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByGroupIdClosed(
		long groupId, boolean closed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupIdClosed(groupId, closed, start, end,
			orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByGroupIdClosed_First(
		long groupId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupIdClosed_First(groupId, closed, orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByGroupIdClosed_First(
		long groupId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByGroupIdClosed_First(groupId, closed,
			orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByGroupIdClosed_Last(
		long groupId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupIdClosed_Last(groupId, closed, orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByGroupIdClosed_Last(
		long groupId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByGroupIdClosed_Last(groupId, closed, orderByComparator);
	}

	/**
	* Returns the courses before and after the current course in the ordered set where groupId = &#63; and closed = &#63;.
	*
	* @param courseId the primary key of the current course
	* @param groupId the group ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course[] findByGroupIdClosed_PrevAndNext(
		long courseId, long groupId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupIdClosed_PrevAndNext(courseId, groupId, closed,
			orderByComparator);
	}

	/**
	* Returns all the courses that the user has permission to view where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @return the matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> filterFindByGroupIdClosed(
		long groupId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupIdClosed(groupId, closed);
	}

	/**
	* Returns a range of all the courses that the user has permission to view where groupId = &#63; and closed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param closed the closed
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @return the range of matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> filterFindByGroupIdClosed(
		long groupId, boolean closed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupIdClosed(groupId, closed, start, end);
	}

	/**
	* Returns an ordered range of all the courses that the user has permissions to view where groupId = &#63; and closed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param closed the closed
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> filterFindByGroupIdClosed(
		long groupId, boolean closed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupIdClosed(groupId, closed, start, end,
			orderByComparator);
	}

	/**
	* Returns the courses before and after the current course in the ordered set of courses that the user has permission to view where groupId = &#63; and closed = &#63;.
	*
	* @param courseId the primary key of the current course
	* @param groupId the group ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course[] filterFindByGroupIdClosed_PrevAndNext(
		long courseId, long groupId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupIdClosed_PrevAndNext(courseId, groupId,
			closed, orderByComparator);
	}

	/**
	* Returns all the courses where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the courses where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @return the range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the courses where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the courses before and after the current course in the ordered set where companyId = &#63;.
	*
	* @param courseId the primary key of the current course
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course[] findByCompanyId_PrevAndNext(
		long courseId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(courseId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the courses where companyId = &#63; and closed = &#63;.
	*
	* @param companyId the company ID
	* @param closed the closed
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByCompanyIdClosed(
		long companyId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyIdClosed(companyId, closed);
	}

	/**
	* Returns a range of all the courses where companyId = &#63; and closed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param closed the closed
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @return the range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByCompanyIdClosed(
		long companyId, boolean closed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyIdClosed(companyId, closed, start, end);
	}

	/**
	* Returns an ordered range of all the courses where companyId = &#63; and closed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param closed the closed
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByCompanyIdClosed(
		long companyId, boolean closed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyIdClosed(companyId, closed, start, end,
			orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where companyId = &#63; and closed = &#63;.
	*
	* @param companyId the company ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByCompanyIdClosed_First(
		long companyId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyIdClosed_First(companyId, closed,
			orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where companyId = &#63; and closed = &#63;.
	*
	* @param companyId the company ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByCompanyIdClosed_First(
		long companyId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyIdClosed_First(companyId, closed,
			orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where companyId = &#63; and closed = &#63;.
	*
	* @param companyId the company ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByCompanyIdClosed_Last(
		long companyId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyIdClosed_Last(companyId, closed,
			orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where companyId = &#63; and closed = &#63;.
	*
	* @param companyId the company ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByCompanyIdClosed_Last(
		long companyId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyIdClosed_Last(companyId, closed,
			orderByComparator);
	}

	/**
	* Returns the courses before and after the current course in the ordered set where companyId = &#63; and closed = &#63;.
	*
	* @param courseId the primary key of the current course
	* @param companyId the company ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course[] findByCompanyIdClosed_PrevAndNext(
		long courseId, long companyId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyIdClosed_PrevAndNext(courseId, companyId,
			closed, orderByComparator);
	}

	/**
	* Returns the course where groupCreatedId = &#63; or throws a {@link com.liferay.lms.NoSuchCourseException} if it could not be found.
	*
	* @param groupCreatedId the group created ID
	* @return the matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByGroupCreatedId(
		long groupCreatedId)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupCreatedId(groupCreatedId);
	}

	/**
	* Returns the course where groupCreatedId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupCreatedId the group created ID
	* @return the matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByGroupCreatedId(
		long groupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupCreatedId(groupCreatedId);
	}

	/**
	* Returns the course where groupCreatedId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupCreatedId the group created ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByGroupCreatedId(
		long groupCreatedId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByGroupCreatedId(groupCreatedId, retrieveFromCache);
	}

	/**
	* Returns all the courses where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the courses where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @return the range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the courses where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the courses before and after the current course in the ordered set where userId = &#63;.
	*
	* @param courseId the primary key of the current course
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course[] findByUserId_PrevAndNext(
		long courseId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(courseId, userId, orderByComparator);
	}

	/**
	* Returns all the courses where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByUserIdGroupId(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserIdGroupId(userId, groupId);
	}

	/**
	* Returns a range of all the courses where userId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @return the range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByUserIdGroupId(
		long userId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserIdGroupId(userId, groupId, start, end);
	}

	/**
	* Returns an ordered range of all the courses where userId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findByUserIdGroupId(
		long userId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserIdGroupId(userId, groupId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByUserIdGroupId_First(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserIdGroupId_First(userId, groupId, orderByComparator);
	}

	/**
	* Returns the first course in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByUserIdGroupId_First(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUserIdGroupId_First(userId, groupId,
			orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByUserIdGroupId_Last(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserIdGroupId_Last(userId, groupId, orderByComparator);
	}

	/**
	* Returns the last course in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByUserIdGroupId_Last(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUserIdGroupId_Last(userId, groupId, orderByComparator);
	}

	/**
	* Returns the courses before and after the current course in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param courseId the primary key of the current course
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course[] findByUserIdGroupId_PrevAndNext(
		long courseId, long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserIdGroupId_PrevAndNext(courseId, userId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the courses that the user has permission to view where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> filterFindByUserIdGroupId(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByUserIdGroupId(userId, groupId);
	}

	/**
	* Returns a range of all the courses that the user has permission to view where userId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @return the range of matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> filterFindByUserIdGroupId(
		long userId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByUserIdGroupId(userId, groupId, start, end);
	}

	/**
	* Returns an ordered range of all the courses that the user has permissions to view where userId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> filterFindByUserIdGroupId(
		long userId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByUserIdGroupId(userId, groupId, start, end,
			orderByComparator);
	}

	/**
	* Returns the courses before and after the current course in the ordered set of courses that the user has permission to view where userId = &#63; and groupId = &#63;.
	*
	* @param courseId the primary key of the current course
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course[] filterFindByUserIdGroupId_PrevAndNext(
		long courseId, long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByUserIdGroupId_PrevAndNext(courseId, userId,
			groupId, orderByComparator);
	}

	/**
	* Returns the course where companyId = &#63; and friendlyURL = &#63; or throws a {@link com.liferay.lms.NoSuchCourseException} if it could not be found.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course findByFriendlyURL(
		long companyId, java.lang.String friendlyURL)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFriendlyURL(companyId, friendlyURL);
	}

	/**
	* Returns the course where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByFriendlyURL(
		long companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByFriendlyURL(companyId, friendlyURL);
	}

	/**
	* Returns the course where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course fetchByFriendlyURL(
		long companyId, java.lang.String friendlyURL, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByFriendlyURL(companyId, friendlyURL, retrieveFromCache);
	}

	/**
	* Returns all the courses.
	*
	* @return the courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the courses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @return the range of courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the courses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of courses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Course> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the courses where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the course where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the course that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the courses where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the courses where groupId = &#63; and closed = &#63; from the database.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupIdClosed(long groupId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupIdClosed(groupId, closed);
	}

	/**
	* Removes all the courses where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Removes all the courses where companyId = &#63; and closed = &#63; from the database.
	*
	* @param companyId the company ID
	* @param closed the closed
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyIdClosed(long companyId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyIdClosed(companyId, closed);
	}

	/**
	* Removes the course where groupCreatedId = &#63; from the database.
	*
	* @param groupCreatedId the group created ID
	* @return the course that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course removeByGroupCreatedId(
		long groupCreatedId)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByGroupCreatedId(groupCreatedId);
	}

	/**
	* Removes all the courses where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes all the courses where userId = &#63; and groupId = &#63; from the database.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserIdGroupId(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserIdGroupId(userId, groupId);
	}

	/**
	* Removes the course where companyId = &#63; and friendlyURL = &#63; from the database.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the course that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course removeByFriendlyURL(
		long companyId, java.lang.String friendlyURL)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByFriendlyURL(companyId, friendlyURL);
	}

	/**
	* Removes all the courses from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of courses where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of courses where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of courses where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of courses that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the number of courses where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupIdClosed(long groupId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupIdClosed(groupId, closed);
	}

	/**
	* Returns the number of courses that the user has permission to view where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @return the number of matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupIdClosed(long groupId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupIdClosed(groupId, closed);
	}

	/**
	* Returns the number of courses where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of courses where companyId = &#63; and closed = &#63;.
	*
	* @param companyId the company ID
	* @param closed the closed
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyIdClosed(long companyId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyIdClosed(companyId, closed);
	}

	/**
	* Returns the number of courses where groupCreatedId = &#63;.
	*
	* @param groupCreatedId the group created ID
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupCreatedId(long groupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupCreatedId(groupCreatedId);
	}

	/**
	* Returns the number of courses where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the number of courses where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserIdGroupId(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserIdGroupId(userId, groupId);
	}

	/**
	* Returns the number of courses that the user has permission to view where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the number of matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByUserIdGroupId(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByUserIdGroupId(userId, groupId);
	}

	/**
	* Returns the number of courses where companyId = &#63; and friendlyURL = &#63;.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByFriendlyURL(long companyId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByFriendlyURL(companyId, friendlyURL);
	}

	/**
	* Returns the number of courses.
	*
	* @return the number of courses
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static CoursePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CoursePersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					CoursePersistence.class.getName());

			ReferenceRegistry.registerReference(CourseUtil.class, "_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(CoursePersistence persistence) {
	}

	private static CoursePersistence _persistence;
}