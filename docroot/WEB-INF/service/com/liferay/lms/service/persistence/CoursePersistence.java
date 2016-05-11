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

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the course service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CoursePersistenceImpl
 * @see CourseUtil
 * @generated
 */
public interface CoursePersistence extends BasePersistence<Course> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CourseUtil} to access the course persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the course in the entity cache if it is enabled.
	*
	* @param course the course
	*/
	public void cacheResult(com.liferay.lms.model.Course course);

	/**
	* Caches the courses in the entity cache if it is enabled.
	*
	* @param courses the courses
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.Course> courses);

	/**
	* Creates a new course with the primary key. Does not add the course to the database.
	*
	* @param courseId the primary key for the new course
	* @return the new course
	*/
	public com.liferay.lms.model.Course create(long courseId);

	/**
	* Removes the course with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param courseId the primary key of the course
	* @return the course that was removed
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course remove(long courseId)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.Course updateImpl(
		com.liferay.lms.model.Course course, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course with the primary key or throws a {@link com.liferay.lms.NoSuchCourseException} if it could not be found.
	*
	* @param courseId the primary key of the course
	* @return the course
	* @throws com.liferay.lms.NoSuchCourseException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course findByPrimaryKey(long courseId)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param courseId the primary key of the course
	* @return the course, or <code>null</code> if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByPrimaryKey(long courseId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the courses where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Course> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first course in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first course in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last course in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last course in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course[] findByUuid_PrevAndNext(
		long courseId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lms.NoSuchCourseException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the courses where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Course> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first course in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first course in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last course in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last course in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course[] findByGroupId_PrevAndNext(
		long courseId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the courses that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Course> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course[] filterFindByGroupId_PrevAndNext(
		long courseId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the courses where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Course> findByGroupIdClosed(
		long groupId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByGroupIdClosed(
		long groupId, boolean closed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByGroupIdClosed(
		long groupId, boolean closed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course findByGroupIdClosed_First(
		long groupId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first course in the ordered set where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByGroupIdClosed_First(
		long groupId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course findByGroupIdClosed_Last(long groupId,
		boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last course in the ordered set where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByGroupIdClosed_Last(
		long groupId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course[] findByGroupIdClosed_PrevAndNext(
		long courseId, long groupId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the courses that the user has permission to view where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @return the matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Course> filterFindByGroupIdClosed(
		long groupId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> filterFindByGroupIdClosed(
		long groupId, boolean closed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> filterFindByGroupIdClosed(
		long groupId, boolean closed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course[] filterFindByGroupIdClosed_PrevAndNext(
		long courseId, long groupId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the courses where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Course> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first course in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first course in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last course in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last course in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course[] findByCompanyId_PrevAndNext(
		long courseId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the courses where companyId = &#63; and closed = &#63;.
	*
	* @param companyId the company ID
	* @param closed the closed
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Course> findByCompanyIdClosed(
		long companyId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByCompanyIdClosed(
		long companyId, boolean closed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByCompanyIdClosed(
		long companyId, boolean closed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course findByCompanyIdClosed_First(
		long companyId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first course in the ordered set where companyId = &#63; and closed = &#63;.
	*
	* @param companyId the company ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByCompanyIdClosed_First(
		long companyId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course findByCompanyIdClosed_Last(
		long companyId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last course in the ordered set where companyId = &#63; and closed = &#63;.
	*
	* @param companyId the company ID
	* @param closed the closed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByCompanyIdClosed_Last(
		long companyId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course[] findByCompanyIdClosed_PrevAndNext(
		long courseId, long companyId, boolean closed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course where groupCreatedId = &#63; or throws a {@link com.liferay.lms.NoSuchCourseException} if it could not be found.
	*
	* @param groupCreatedId the group created ID
	* @return the matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course findByGroupCreatedId(
		long groupCreatedId)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course where groupCreatedId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupCreatedId the group created ID
	* @return the matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByGroupCreatedId(
		long groupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course where groupCreatedId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupCreatedId the group created ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByGroupCreatedId(
		long groupCreatedId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the courses where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Course> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first course in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first course in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last course in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last course in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course[] findByUserId_PrevAndNext(
		long courseId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the courses where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the matching courses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Course> findByUserIdGroupId(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByUserIdGroupId(
		long userId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findByUserIdGroupId(
		long userId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course findByUserIdGroupId_First(long userId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first course in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByUserIdGroupId_First(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course findByUserIdGroupId_Last(long userId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last course in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByUserIdGroupId_Last(long userId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course[] findByUserIdGroupId_PrevAndNext(
		long courseId, long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the courses that the user has permission to view where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Course> filterFindByUserIdGroupId(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> filterFindByUserIdGroupId(
		long userId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> filterFindByUserIdGroupId(
		long userId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.Course[] filterFindByUserIdGroupId_PrevAndNext(
		long courseId, long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course where companyId = &#63; and friendlyURL = &#63; or throws a {@link com.liferay.lms.NoSuchCourseException} if it could not be found.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the matching course
	* @throws com.liferay.lms.NoSuchCourseException if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course findByFriendlyURL(long companyId,
		java.lang.String friendlyURL)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByFriendlyURL(long companyId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching course, or <code>null</code> if a matching course could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course fetchByFriendlyURL(long companyId,
		java.lang.String friendlyURL, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the courses.
	*
	* @return the courses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Course> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.Course> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the courses where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the course where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the course that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the courses where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the courses where groupId = &#63; and closed = &#63; from the database.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupIdClosed(long groupId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the courses where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the courses where companyId = &#63; and closed = &#63; from the database.
	*
	* @param companyId the company ID
	* @param closed the closed
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyIdClosed(long companyId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the course where groupCreatedId = &#63; from the database.
	*
	* @param groupCreatedId the group created ID
	* @return the course that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course removeByGroupCreatedId(
		long groupCreatedId)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the courses where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the courses where userId = &#63; and groupId = &#63; from the database.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserIdGroupId(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the course where companyId = &#63; and friendlyURL = &#63; from the database.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the course that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course removeByFriendlyURL(long companyId,
		java.lang.String friendlyURL)
		throws com.liferay.lms.NoSuchCourseException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the courses from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupIdClosed(long groupId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses that the user has permission to view where groupId = &#63; and closed = &#63;.
	*
	* @param groupId the group ID
	* @param closed the closed
	* @return the number of matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupIdClosed(long groupId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses where companyId = &#63; and closed = &#63;.
	*
	* @param companyId the company ID
	* @param closed the closed
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyIdClosed(long companyId, boolean closed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses where groupCreatedId = &#63;.
	*
	* @param groupCreatedId the group created ID
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupCreatedId(long groupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserIdGroupId(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses that the user has permission to view where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the number of matching courses that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByUserIdGroupId(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses where companyId = &#63; and friendlyURL = &#63;.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the number of matching courses
	* @throws SystemException if a system exception occurred
	*/
	public int countByFriendlyURL(long companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of courses.
	*
	* @return the number of courses
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}