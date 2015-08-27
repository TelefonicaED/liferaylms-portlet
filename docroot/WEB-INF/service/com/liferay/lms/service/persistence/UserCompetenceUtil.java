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

import com.liferay.lms.model.UserCompetence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the user competence service. This utility wraps {@link UserCompetencePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see UserCompetencePersistence
 * @see UserCompetencePersistenceImpl
 * @generated
 */
public class UserCompetenceUtil {
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
	public static void clearCache(UserCompetence userCompetence) {
		getPersistence().clearCache(userCompetence);
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
	public static List<UserCompetence> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UserCompetence> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<UserCompetence> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static UserCompetence update(UserCompetence userCompetence,
		boolean merge) throws SystemException {
		return getPersistence().update(userCompetence, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static UserCompetence update(UserCompetence userCompetence,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(userCompetence, merge, serviceContext);
	}

	/**
	* Caches the user competence in the entity cache if it is enabled.
	*
	* @param userCompetence the user competence
	*/
	public static void cacheResult(
		com.liferay.lms.model.UserCompetence userCompetence) {
		getPersistence().cacheResult(userCompetence);
	}

	/**
	* Caches the user competences in the entity cache if it is enabled.
	*
	* @param userCompetences the user competences
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.UserCompetence> userCompetences) {
		getPersistence().cacheResult(userCompetences);
	}

	/**
	* Creates a new user competence with the primary key. Does not add the user competence to the database.
	*
	* @param usercompId the primary key for the new user competence
	* @return the new user competence
	*/
	public static com.liferay.lms.model.UserCompetence create(long usercompId) {
		return getPersistence().create(usercompId);
	}

	/**
	* Removes the user competence with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param usercompId the primary key of the user competence
	* @return the user competence that was removed
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence remove(long usercompId)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(usercompId);
	}

	public static com.liferay.lms.model.UserCompetence updateImpl(
		com.liferay.lms.model.UserCompetence userCompetence, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(userCompetence, merge);
	}

	/**
	* Returns the user competence with the primary key or throws a {@link com.liferay.lms.NoSuchUserCompetenceException} if it could not be found.
	*
	* @param usercompId the primary key of the user competence
	* @return the user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence findByPrimaryKey(
		long usercompId)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(usercompId);
	}

	/**
	* Returns the user competence with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param usercompId the primary key of the user competence
	* @return the user competence, or <code>null</code> if a user competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence fetchByPrimaryKey(
		long usercompId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(usercompId);
	}

	/**
	* Returns all the user competences where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.UserCompetence> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the user competences where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @return the range of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.UserCompetence> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the user competences where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.UserCompetence> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first user competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first user competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user competence, or <code>null</code> if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last user competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last user competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user competence, or <code>null</code> if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the user competences before and after the current user competence in the ordered set where uuid = &#63;.
	*
	* @param usercompId the primary key of the current user competence
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence[] findByUuid_PrevAndNext(
		long usercompId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(usercompId, uuid, orderByComparator);
	}

	/**
	* Returns all the user competences where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.UserCompetence> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the user competences where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @return the range of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.UserCompetence> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the user competences where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.UserCompetence> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first user competence in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first user competence in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user competence, or <code>null</code> if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last user competence in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last user competence in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user competence, or <code>null</code> if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the user competences before and after the current user competence in the ordered set where userId = &#63;.
	*
	* @param usercompId the primary key of the current user competence
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence[] findByUserId_PrevAndNext(
		long usercompId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(usercompId, userId,
			orderByComparator);
	}

	/**
	* Returns the user competence where userId = &#63; and competenceId = &#63; or throws a {@link com.liferay.lms.NoSuchUserCompetenceException} if it could not be found.
	*
	* @param userId the user ID
	* @param competenceId the competence ID
	* @return the matching user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence findByUserIdCompetenceId(
		long userId, long competenceId)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserIdCompetenceId(userId, competenceId);
	}

	/**
	* Returns the user competence where userId = &#63; and competenceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param competenceId the competence ID
	* @return the matching user competence, or <code>null</code> if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence fetchByUserIdCompetenceId(
		long userId, long competenceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserIdCompetenceId(userId, competenceId);
	}

	/**
	* Returns the user competence where userId = &#63; and competenceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param competenceId the competence ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user competence, or <code>null</code> if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence fetchByUserIdCompetenceId(
		long userId, long competenceId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUserIdCompetenceId(userId, competenceId,
			retrieveFromCache);
	}

	/**
	* Returns all the user competences.
	*
	* @return the user competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.UserCompetence> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the user competences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @return the range of user competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.UserCompetence> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the user competences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.UserCompetence> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the user competences where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes all the user competences where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes the user competence where userId = &#63; and competenceId = &#63; from the database.
	*
	* @param userId the user ID
	* @param competenceId the competence ID
	* @return the user competence that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence removeByUserIdCompetenceId(
		long userId, long competenceId)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByUserIdCompetenceId(userId, competenceId);
	}

	/**
	* Removes all the user competences from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of user competences where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of user competences where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the number of user competences where userId = &#63; and competenceId = &#63;.
	*
	* @param userId the user ID
	* @param competenceId the competence ID
	* @return the number of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserIdCompetenceId(long userId, long competenceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserIdCompetenceId(userId, competenceId);
	}

	/**
	* Returns the number of user competences.
	*
	* @return the number of user competences
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static UserCompetencePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserCompetencePersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					UserCompetencePersistence.class.getName());

			ReferenceRegistry.registerReference(UserCompetenceUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(UserCompetencePersistence persistence) {
	}

	private static UserCompetencePersistence _persistence;
}