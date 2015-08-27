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

import com.liferay.lms.model.CourseCompetence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the course competence service. This utility wraps {@link CourseCompetencePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CourseCompetencePersistence
 * @see CourseCompetencePersistenceImpl
 * @generated
 */
public class CourseCompetenceUtil {
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
	public static void clearCache(CourseCompetence courseCompetence) {
		getPersistence().clearCache(courseCompetence);
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
	public static List<CourseCompetence> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CourseCompetence> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CourseCompetence> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static CourseCompetence update(CourseCompetence courseCompetence,
		boolean merge) throws SystemException {
		return getPersistence().update(courseCompetence, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static CourseCompetence update(CourseCompetence courseCompetence,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(courseCompetence, merge, serviceContext);
	}

	/**
	* Caches the course competence in the entity cache if it is enabled.
	*
	* @param courseCompetence the course competence
	*/
	public static void cacheResult(
		com.liferay.lms.model.CourseCompetence courseCompetence) {
		getPersistence().cacheResult(courseCompetence);
	}

	/**
	* Caches the course competences in the entity cache if it is enabled.
	*
	* @param courseCompetences the course competences
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.CourseCompetence> courseCompetences) {
		getPersistence().cacheResult(courseCompetences);
	}

	/**
	* Creates a new course competence with the primary key. Does not add the course competence to the database.
	*
	* @param CourcompetenceId the primary key for the new course competence
	* @return the new course competence
	*/
	public static com.liferay.lms.model.CourseCompetence create(
		long CourcompetenceId) {
		return getPersistence().create(CourcompetenceId);
	}

	/**
	* Removes the course competence with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CourcompetenceId the primary key of the course competence
	* @return the course competence that was removed
	* @throws com.liferay.lms.NoSuchCourseCompetenceException if a course competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence remove(
		long CourcompetenceId)
		throws com.liferay.lms.NoSuchCourseCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(CourcompetenceId);
	}

	public static com.liferay.lms.model.CourseCompetence updateImpl(
		com.liferay.lms.model.CourseCompetence courseCompetence, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(courseCompetence, merge);
	}

	/**
	* Returns the course competence with the primary key or throws a {@link com.liferay.lms.NoSuchCourseCompetenceException} if it could not be found.
	*
	* @param CourcompetenceId the primary key of the course competence
	* @return the course competence
	* @throws com.liferay.lms.NoSuchCourseCompetenceException if a course competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence findByPrimaryKey(
		long CourcompetenceId)
		throws com.liferay.lms.NoSuchCourseCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(CourcompetenceId);
	}

	/**
	* Returns the course competence with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CourcompetenceId the primary key of the course competence
	* @return the course competence, or <code>null</code> if a course competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence fetchByPrimaryKey(
		long CourcompetenceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(CourcompetenceId);
	}

	/**
	* Returns all the course competences where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching course competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseCompetence> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the course competences where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of course competences
	* @param end the upper bound of the range of course competences (not inclusive)
	* @return the range of matching course competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseCompetence> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the course competences where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of course competences
	* @param end the upper bound of the range of course competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseCompetence> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first course competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course competence
	* @throws com.liferay.lms.NoSuchCourseCompetenceException if a matching course competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first course competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course competence, or <code>null</code> if a matching course competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last course competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course competence
	* @throws com.liferay.lms.NoSuchCourseCompetenceException if a matching course competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last course competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course competence, or <code>null</code> if a matching course competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the course competences before and after the current course competence in the ordered set where uuid = &#63;.
	*
	* @param CourcompetenceId the primary key of the current course competence
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course competence
	* @throws com.liferay.lms.NoSuchCourseCompetenceException if a course competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence[] findByUuid_PrevAndNext(
		long CourcompetenceId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CourcompetenceId, uuid,
			orderByComparator);
	}

	/**
	* Returns all the course competences where courseId = &#63; and condition = &#63;.
	*
	* @param courseId the course ID
	* @param condition the condition
	* @return the matching course competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseCompetence> findBycourseId(
		long courseId, boolean condition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBycourseId(courseId, condition);
	}

	/**
	* Returns a range of all the course competences where courseId = &#63; and condition = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param condition the condition
	* @param start the lower bound of the range of course competences
	* @param end the upper bound of the range of course competences (not inclusive)
	* @return the range of matching course competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseCompetence> findBycourseId(
		long courseId, boolean condition, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBycourseId(courseId, condition, start, end);
	}

	/**
	* Returns an ordered range of all the course competences where courseId = &#63; and condition = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param courseId the course ID
	* @param condition the condition
	* @param start the lower bound of the range of course competences
	* @param end the upper bound of the range of course competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching course competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseCompetence> findBycourseId(
		long courseId, boolean condition, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBycourseId(courseId, condition, start, end,
			orderByComparator);
	}

	/**
	* Returns the first course competence in the ordered set where courseId = &#63; and condition = &#63;.
	*
	* @param courseId the course ID
	* @param condition the condition
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course competence
	* @throws com.liferay.lms.NoSuchCourseCompetenceException if a matching course competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence findBycourseId_First(
		long courseId, boolean condition,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBycourseId_First(courseId, condition, orderByComparator);
	}

	/**
	* Returns the first course competence in the ordered set where courseId = &#63; and condition = &#63;.
	*
	* @param courseId the course ID
	* @param condition the condition
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching course competence, or <code>null</code> if a matching course competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence fetchBycourseId_First(
		long courseId, boolean condition,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchBycourseId_First(courseId, condition, orderByComparator);
	}

	/**
	* Returns the last course competence in the ordered set where courseId = &#63; and condition = &#63;.
	*
	* @param courseId the course ID
	* @param condition the condition
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course competence
	* @throws com.liferay.lms.NoSuchCourseCompetenceException if a matching course competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence findBycourseId_Last(
		long courseId, boolean condition,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBycourseId_Last(courseId, condition, orderByComparator);
	}

	/**
	* Returns the last course competence in the ordered set where courseId = &#63; and condition = &#63;.
	*
	* @param courseId the course ID
	* @param condition the condition
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching course competence, or <code>null</code> if a matching course competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence fetchBycourseId_Last(
		long courseId, boolean condition,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchBycourseId_Last(courseId, condition, orderByComparator);
	}

	/**
	* Returns the course competences before and after the current course competence in the ordered set where courseId = &#63; and condition = &#63;.
	*
	* @param CourcompetenceId the primary key of the current course competence
	* @param courseId the course ID
	* @param condition the condition
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next course competence
	* @throws com.liferay.lms.NoSuchCourseCompetenceException if a course competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence[] findBycourseId_PrevAndNext(
		long CourcompetenceId, long courseId, boolean condition,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCourseCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBycourseId_PrevAndNext(CourcompetenceId, courseId,
			condition, orderByComparator);
	}

	/**
	* Returns the course competence where courseId = &#63; and competenceId = &#63; and condition = &#63; or throws a {@link com.liferay.lms.NoSuchCourseCompetenceException} if it could not be found.
	*
	* @param courseId the course ID
	* @param competenceId the competence ID
	* @param condition the condition
	* @return the matching course competence
	* @throws com.liferay.lms.NoSuchCourseCompetenceException if a matching course competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence findByCourseCompetenceCondition(
		long courseId, long competenceId, boolean condition)
		throws com.liferay.lms.NoSuchCourseCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCourseCompetenceCondition(courseId, competenceId,
			condition);
	}

	/**
	* Returns the course competence where courseId = &#63; and competenceId = &#63; and condition = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param courseId the course ID
	* @param competenceId the competence ID
	* @param condition the condition
	* @return the matching course competence, or <code>null</code> if a matching course competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence fetchByCourseCompetenceCondition(
		long courseId, long competenceId, boolean condition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseCompetenceCondition(courseId, competenceId,
			condition);
	}

	/**
	* Returns the course competence where courseId = &#63; and competenceId = &#63; and condition = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param courseId the course ID
	* @param competenceId the competence ID
	* @param condition the condition
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching course competence, or <code>null</code> if a matching course competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence fetchByCourseCompetenceCondition(
		long courseId, long competenceId, boolean condition,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCourseCompetenceCondition(courseId, competenceId,
			condition, retrieveFromCache);
	}

	/**
	* Returns all the course competences.
	*
	* @return the course competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseCompetence> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the course competences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of course competences
	* @param end the upper bound of the range of course competences (not inclusive)
	* @return the range of course competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseCompetence> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the course competences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of course competences
	* @param end the upper bound of the range of course competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of course competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseCompetence> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the course competences where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes all the course competences where courseId = &#63; and condition = &#63; from the database.
	*
	* @param courseId the course ID
	* @param condition the condition
	* @throws SystemException if a system exception occurred
	*/
	public static void removeBycourseId(long courseId, boolean condition)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeBycourseId(courseId, condition);
	}

	/**
	* Removes the course competence where courseId = &#63; and competenceId = &#63; and condition = &#63; from the database.
	*
	* @param courseId the course ID
	* @param competenceId the competence ID
	* @param condition the condition
	* @return the course competence that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence removeByCourseCompetenceCondition(
		long courseId, long competenceId, boolean condition)
		throws com.liferay.lms.NoSuchCourseCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .removeByCourseCompetenceCondition(courseId, competenceId,
			condition);
	}

	/**
	* Removes all the course competences from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of course competences where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching course competences
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of course competences where courseId = &#63; and condition = &#63;.
	*
	* @param courseId the course ID
	* @param condition the condition
	* @return the number of matching course competences
	* @throws SystemException if a system exception occurred
	*/
	public static int countBycourseId(long courseId, boolean condition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBycourseId(courseId, condition);
	}

	/**
	* Returns the number of course competences where courseId = &#63; and competenceId = &#63; and condition = &#63;.
	*
	* @param courseId the course ID
	* @param competenceId the competence ID
	* @param condition the condition
	* @return the number of matching course competences
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCourseCompetenceCondition(long courseId,
		long competenceId, boolean condition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByCourseCompetenceCondition(courseId, competenceId,
			condition);
	}

	/**
	* Returns the number of course competences.
	*
	* @return the number of course competences
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static CourseCompetencePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CourseCompetencePersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					CourseCompetencePersistence.class.getName());

			ReferenceRegistry.registerReference(CourseCompetenceUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(CourseCompetencePersistence persistence) {
	}

	private static CourseCompetencePersistence _persistence;
}