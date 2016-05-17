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

import com.liferay.lms.model.SurveyResult;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the survey result service. This utility wraps {@link SurveyResultPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see SurveyResultPersistence
 * @see SurveyResultPersistenceImpl
 * @generated
 */
public class SurveyResultUtil {
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
	public static void clearCache(SurveyResult surveyResult) {
		getPersistence().clearCache(surveyResult);
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
	public static List<SurveyResult> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SurveyResult> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SurveyResult> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SurveyResult update(SurveyResult surveyResult, boolean merge)
		throws SystemException {
		return getPersistence().update(surveyResult, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static SurveyResult update(SurveyResult surveyResult, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(surveyResult, merge, serviceContext);
	}

	/**
	* Caches the survey result in the entity cache if it is enabled.
	*
	* @param surveyResult the survey result
	*/
	public static void cacheResult(
		com.liferay.lms.model.SurveyResult surveyResult) {
		getPersistence().cacheResult(surveyResult);
	}

	/**
	* Caches the survey results in the entity cache if it is enabled.
	*
	* @param surveyResults the survey results
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.SurveyResult> surveyResults) {
		getPersistence().cacheResult(surveyResults);
	}

	/**
	* Creates a new survey result with the primary key. Does not add the survey result to the database.
	*
	* @param surveyResultId the primary key for the new survey result
	* @return the new survey result
	*/
	public static com.liferay.lms.model.SurveyResult create(long surveyResultId) {
		return getPersistence().create(surveyResultId);
	}

	/**
	* Removes the survey result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param surveyResultId the primary key of the survey result
	* @return the survey result that was removed
	* @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult remove(long surveyResultId)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(surveyResultId);
	}

	public static com.liferay.lms.model.SurveyResult updateImpl(
		com.liferay.lms.model.SurveyResult surveyResult, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(surveyResult, merge);
	}

	/**
	* Returns the survey result with the primary key or throws a {@link com.liferay.lms.NoSuchSurveyResultException} if it could not be found.
	*
	* @param surveyResultId the primary key of the survey result
	* @return the survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult findByPrimaryKey(
		long surveyResultId)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(surveyResultId);
	}

	/**
	* Returns the survey result with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param surveyResultId the primary key of the survey result
	* @return the survey result, or <code>null</code> if a survey result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult fetchByPrimaryKey(
		long surveyResultId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(surveyResultId);
	}

	/**
	* Returns all the survey results where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the survey results where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of survey results
	* @param end the upper bound of the range of survey results (not inclusive)
	* @return the range of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the survey results where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of survey results
	* @param end the upper bound of the range of survey results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first survey result in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first survey result in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching survey result, or <code>null</code> if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last survey result in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last survey result in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching survey result, or <code>null</code> if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the survey results before and after the current survey result in the ordered set where uuid = &#63;.
	*
	* @param surveyResultId the primary key of the current survey result
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult[] findByUuid_PrevAndNext(
		long surveyResultId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(surveyResultId, uuid,
			orderByComparator);
	}

	/**
	* Returns all the survey results where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the survey results where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of survey results
	* @param end the upper bound of the range of survey results (not inclusive)
	* @return the range of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the survey results where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of survey results
	* @param end the upper bound of the range of survey results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first survey result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first survey result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching survey result, or <code>null</code> if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last survey result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last survey result in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching survey result, or <code>null</code> if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the survey results before and after the current survey result in the ordered set where userId = &#63;.
	*
	* @param surveyResultId the primary key of the current survey result
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult[] findByUserId_PrevAndNext(
		long surveyResultId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(surveyResultId, userId,
			orderByComparator);
	}

	/**
	* Returns all the survey results where actId = &#63;.
	*
	* @param actId the act ID
	* @return the matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByActId(
		long actId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActId(actId);
	}

	/**
	* Returns a range of all the survey results where actId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param start the lower bound of the range of survey results
	* @param end the upper bound of the range of survey results (not inclusive)
	* @return the range of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByActId(
		long actId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActId(actId, start, end);
	}

	/**
	* Returns an ordered range of all the survey results where actId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actId the act ID
	* @param start the lower bound of the range of survey results
	* @param end the upper bound of the range of survey results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByActId(
		long actId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActId(actId, start, end, orderByComparator);
	}

	/**
	* Returns the first survey result in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult findByActId_First(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActId_First(actId, orderByComparator);
	}

	/**
	* Returns the first survey result in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching survey result, or <code>null</code> if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult fetchByActId_First(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByActId_First(actId, orderByComparator);
	}

	/**
	* Returns the last survey result in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult findByActId_Last(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActId_Last(actId, orderByComparator);
	}

	/**
	* Returns the last survey result in the ordered set where actId = &#63;.
	*
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching survey result, or <code>null</code> if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult fetchByActId_Last(
		long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByActId_Last(actId, orderByComparator);
	}

	/**
	* Returns the survey results before and after the current survey result in the ordered set where actId = &#63;.
	*
	* @param surveyResultId the primary key of the current survey result
	* @param actId the act ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult[] findByActId_PrevAndNext(
		long surveyResultId, long actId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByActId_PrevAndNext(surveyResultId, actId,
			orderByComparator);
	}

	/**
	* Returns all the survey results where questionId = &#63;.
	*
	* @param questionId the question ID
	* @return the matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByQuestionId(
		long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByQuestionId(questionId);
	}

	/**
	* Returns a range of all the survey results where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param questionId the question ID
	* @param start the lower bound of the range of survey results
	* @param end the upper bound of the range of survey results (not inclusive)
	* @return the range of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByQuestionId(
		long questionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByQuestionId(questionId, start, end);
	}

	/**
	* Returns an ordered range of all the survey results where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param questionId the question ID
	* @param start the lower bound of the range of survey results
	* @param end the upper bound of the range of survey results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByQuestionId(
		long questionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByQuestionId(questionId, start, end, orderByComparator);
	}

	/**
	* Returns the first survey result in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult findByQuestionId_First(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByQuestionId_First(questionId, orderByComparator);
	}

	/**
	* Returns the first survey result in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching survey result, or <code>null</code> if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult fetchByQuestionId_First(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByQuestionId_First(questionId, orderByComparator);
	}

	/**
	* Returns the last survey result in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult findByQuestionId_Last(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByQuestionId_Last(questionId, orderByComparator);
	}

	/**
	* Returns the last survey result in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching survey result, or <code>null</code> if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult fetchByQuestionId_Last(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByQuestionId_Last(questionId, orderByComparator);
	}

	/**
	* Returns the survey results before and after the current survey result in the ordered set where questionId = &#63;.
	*
	* @param surveyResultId the primary key of the current survey result
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult[] findByQuestionId_PrevAndNext(
		long surveyResultId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByQuestionId_PrevAndNext(surveyResultId, questionId,
			orderByComparator);
	}

	/**
	* Returns all the survey results where answerId = &#63; and questionId = &#63;.
	*
	* @param answerId the answer ID
	* @param questionId the question ID
	* @return the matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByAnswerIdQuestionId(
		long answerId, long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByAnswerIdQuestionId(answerId, questionId);
	}

	/**
	* Returns a range of all the survey results where answerId = &#63; and questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param answerId the answer ID
	* @param questionId the question ID
	* @param start the lower bound of the range of survey results
	* @param end the upper bound of the range of survey results (not inclusive)
	* @return the range of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByAnswerIdQuestionId(
		long answerId, long questionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAnswerIdQuestionId(answerId, questionId, start, end);
	}

	/**
	* Returns an ordered range of all the survey results where answerId = &#63; and questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param answerId the answer ID
	* @param questionId the question ID
	* @param start the lower bound of the range of survey results
	* @param end the upper bound of the range of survey results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findByAnswerIdQuestionId(
		long answerId, long questionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAnswerIdQuestionId(answerId, questionId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first survey result in the ordered set where answerId = &#63; and questionId = &#63;.
	*
	* @param answerId the answer ID
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult findByAnswerIdQuestionId_First(
		long answerId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAnswerIdQuestionId_First(answerId, questionId,
			orderByComparator);
	}

	/**
	* Returns the first survey result in the ordered set where answerId = &#63; and questionId = &#63;.
	*
	* @param answerId the answer ID
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching survey result, or <code>null</code> if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult fetchByAnswerIdQuestionId_First(
		long answerId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByAnswerIdQuestionId_First(answerId, questionId,
			orderByComparator);
	}

	/**
	* Returns the last survey result in the ordered set where answerId = &#63; and questionId = &#63;.
	*
	* @param answerId the answer ID
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult findByAnswerIdQuestionId_Last(
		long answerId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAnswerIdQuestionId_Last(answerId, questionId,
			orderByComparator);
	}

	/**
	* Returns the last survey result in the ordered set where answerId = &#63; and questionId = &#63;.
	*
	* @param answerId the answer ID
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching survey result, or <code>null</code> if a matching survey result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult fetchByAnswerIdQuestionId_Last(
		long answerId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByAnswerIdQuestionId_Last(answerId, questionId,
			orderByComparator);
	}

	/**
	* Returns the survey results before and after the current survey result in the ordered set where answerId = &#63; and questionId = &#63;.
	*
	* @param surveyResultId the primary key of the current survey result
	* @param answerId the answer ID
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next survey result
	* @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SurveyResult[] findByAnswerIdQuestionId_PrevAndNext(
		long surveyResultId, long answerId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSurveyResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAnswerIdQuestionId_PrevAndNext(surveyResultId,
			answerId, questionId, orderByComparator);
	}

	/**
	* Returns all the survey results.
	*
	* @return the survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the survey results.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of survey results
	* @param end the upper bound of the range of survey results (not inclusive)
	* @return the range of survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the survey results.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of survey results
	* @param end the upper bound of the range of survey results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of survey results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SurveyResult> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the survey results where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes all the survey results where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes all the survey results where actId = &#63; from the database.
	*
	* @param actId the act ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByActId(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByActId(actId);
	}

	/**
	* Removes all the survey results where questionId = &#63; from the database.
	*
	* @param questionId the question ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByQuestionId(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByQuestionId(questionId);
	}

	/**
	* Removes all the survey results where answerId = &#63; and questionId = &#63; from the database.
	*
	* @param answerId the answer ID
	* @param questionId the question ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByAnswerIdQuestionId(long answerId, long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByAnswerIdQuestionId(answerId, questionId);
	}

	/**
	* Removes all the survey results from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of survey results where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of survey results where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the number of survey results where actId = &#63;.
	*
	* @param actId the act ID
	* @return the number of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByActId(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByActId(actId);
	}

	/**
	* Returns the number of survey results where questionId = &#63;.
	*
	* @param questionId the question ID
	* @return the number of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByQuestionId(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByQuestionId(questionId);
	}

	/**
	* Returns the number of survey results where answerId = &#63; and questionId = &#63;.
	*
	* @param answerId the answer ID
	* @param questionId the question ID
	* @return the number of matching survey results
	* @throws SystemException if a system exception occurred
	*/
	public static int countByAnswerIdQuestionId(long answerId, long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByAnswerIdQuestionId(answerId, questionId);
	}

	/**
	* Returns the number of survey results.
	*
	* @return the number of survey results
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SurveyResultPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SurveyResultPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					SurveyResultPersistence.class.getName());

			ReferenceRegistry.registerReference(SurveyResultUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(SurveyResultPersistence persistence) {
	}

	private static SurveyResultPersistence _persistence;
}