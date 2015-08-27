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

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the course result service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CourseResultPersistenceImpl
 * @see CourseResultUtil
 * @generated
 */
public interface CourseResultPersistence extends BasePersistence<CourseResult> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CourseResultUtil} to access the course result persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the course result in the entity cache if it is enabled.
	*
	* @param courseResult the course result
	*/
	public void cacheResult(com.liferay.lms.model.CourseResult courseResult);

	/**
	* Caches the course results in the entity cache if it is enabled.
	*
	* @param courseResults the course results
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.CourseResult> courseResults);

	/**
	* Creates a new course result with the primary key. Does not add the course result to the database.
	*
	* @param crId the primary key for the new course result
	* @return the new course result
	*/
	public com.liferay.lms.model.CourseResult create(long crId);

	/**
	* Removes the course result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param crId the primary key of the course result
	* @return the course result that was removed
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult remove(long crId)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.CourseResult updateImpl(
		com.liferay.lms.model.CourseResult courseResult, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course result with the primary key or throws a {@link com.liferay.lms.NoSuchCourseResultException} if it could not be found.
	*
	* @param crId the primary key of the course result
	* @return the course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult findByPrimaryKey(long crId)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course result with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param crId the primary key of the course result
	* @return the course result, or <code>null</code> if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult fetchByPrimaryKey(long crId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course result where userId = &#63; and courseId = &#63; or throws a {@link com.liferay.lms.NoSuchCourseResultException} if it could not be found.
	*
	* @param userId the user ID
	* @param courseId the course ID
	* @return the matching course result
	* @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult findByuc(long userId,
		long courseId)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course result where userId = &#63; and courseId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param courseId the course ID
	* @return the matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult fetchByuc(long userId,
		long courseId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course result where userId = &#63; and courseId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param courseId the course ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult fetchByuc(long userId,
		long courseId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the course results where courseId = &#63; and passed = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @return the matching course results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.CourseResult> findByc(
		long courseId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.CourseResult> findByc(
		long courseId, boolean passed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.CourseResult> findByc(
		long courseId, boolean passed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.CourseResult findByc_First(long courseId,
		boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first course result in the ordered set where courseId = &#63; and passed = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult fetchByc_First(long courseId,
		boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.CourseResult findByc_Last(long courseId,
		boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last course result in the ordered set where courseId = &#63; and passed = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course result, or <code>null</code> if a matching course result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult fetchByc_Last(long courseId,
		boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.lms.model.CourseResult[] findByc_PrevAndNext(long crId,
		long courseId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the course results.
	*
	* @return the course results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.CourseResult> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.CourseResult> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.CourseResult> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the course result where userId = &#63; and courseId = &#63; from the database.
	*
	* @param userId the user ID
	* @param courseId the course ID
	* @return the course result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult removeByuc(long userId,
		long courseId)
		throws com.liferay.lms.NoSuchCourseResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the course results where courseId = &#63; and passed = &#63; from the database.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @throws SystemException if a system exception occurred
	*/
	public void removeByc(long courseId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the course results from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of course results where userId = &#63; and courseId = &#63;.
	*
	* @param userId the user ID
	* @param courseId the course ID
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public int countByuc(long userId, long courseId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of course results where courseId = &#63; and passed = &#63;.
	*
	* @param courseId the course ID
	* @param passed the passed
	* @return the number of matching course results
	* @throws SystemException if a system exception occurred
	*/
	public int countByc(long courseId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of course results.
	*
	* @return the number of course results
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}