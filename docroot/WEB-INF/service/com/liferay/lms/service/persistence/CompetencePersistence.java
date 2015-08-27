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

import com.liferay.lms.model.Competence;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the competence service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CompetencePersistenceImpl
 * @see CompetenceUtil
 * @generated
 */
public interface CompetencePersistence extends BasePersistence<Competence> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CompetenceUtil} to access the competence persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the competence in the entity cache if it is enabled.
	*
	* @param competence the competence
	*/
	public void cacheResult(com.liferay.lms.model.Competence competence);

	/**
	* Caches the competences in the entity cache if it is enabled.
	*
	* @param competences the competences
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.Competence> competences);

	/**
	* Creates a new competence with the primary key. Does not add the competence to the database.
	*
	* @param competenceId the primary key for the new competence
	* @return the new competence
	*/
	public com.liferay.lms.model.Competence create(long competenceId);

	/**
	* Removes the competence with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param competenceId the primary key of the competence
	* @return the competence that was removed
	* @throws com.liferay.lms.NoSuchCompetenceException if a competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence remove(long competenceId)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.Competence updateImpl(
		com.liferay.lms.model.Competence competence, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the competence with the primary key or throws a {@link com.liferay.lms.NoSuchCompetenceException} if it could not be found.
	*
	* @param competenceId the primary key of the competence
	* @return the competence
	* @throws com.liferay.lms.NoSuchCompetenceException if a competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence findByPrimaryKey(long competenceId)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the competence with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param competenceId the primary key of the competence
	* @return the competence, or <code>null</code> if a competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence fetchByPrimaryKey(long competenceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the competences where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the competences where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of competences
	* @param end the upper bound of the range of competences (not inclusive)
	* @return the range of matching competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the competences where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of competences
	* @param end the upper bound of the range of competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching competence
	* @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching competence, or <code>null</code> if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching competence
	* @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching competence, or <code>null</code> if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the competences before and after the current competence in the ordered set where uuid = &#63;.
	*
	* @param competenceId the primary key of the current competence
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next competence
	* @throws com.liferay.lms.NoSuchCompetenceException if a competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence[] findByUuid_PrevAndNext(
		long competenceId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the competence where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lms.NoSuchCompetenceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching competence
	* @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the competence where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching competence, or <code>null</code> if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the competence where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching competence, or <code>null</code> if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the competences where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the competences where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of competences
	* @param end the upper bound of the range of competences (not inclusive)
	* @return the range of matching competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the competences where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of competences
	* @param end the upper bound of the range of competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first competence in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching competence
	* @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first competence in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching competence, or <code>null</code> if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last competence in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching competence
	* @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last competence in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching competence, or <code>null</code> if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the competences before and after the current competence in the ordered set where groupId = &#63;.
	*
	* @param competenceId the primary key of the current competence
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next competence
	* @throws com.liferay.lms.NoSuchCompetenceException if a competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence[] findByGroupId_PrevAndNext(
		long competenceId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the competences that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching competences that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the competences that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of competences
	* @param end the upper bound of the range of competences (not inclusive)
	* @return the range of matching competences that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the competences that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of competences
	* @param end the upper bound of the range of competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching competences that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the competences before and after the current competence in the ordered set of competences that the user has permission to view where groupId = &#63;.
	*
	* @param competenceId the primary key of the current competence
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next competence
	* @throws com.liferay.lms.NoSuchCompetenceException if a competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence[] filterFindByGroupId_PrevAndNext(
		long competenceId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the competences where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the competences where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of competences
	* @param end the upper bound of the range of competences (not inclusive)
	* @return the range of matching competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the competences where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of competences
	* @param end the upper bound of the range of competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first competence in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching competence
	* @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first competence in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching competence, or <code>null</code> if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last competence in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching competence
	* @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last competence in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching competence, or <code>null</code> if a matching competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the competences before and after the current competence in the ordered set where companyId = &#63;.
	*
	* @param competenceId the primary key of the current competence
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next competence
	* @throws com.liferay.lms.NoSuchCompetenceException if a competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence[] findByCompanyId_PrevAndNext(
		long competenceId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the competences.
	*
	* @return the competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the competences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of competences
	* @param end the upper bound of the range of competences (not inclusive)
	* @return the range of competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the competences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of competences
	* @param end the upper bound of the range of competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Competence> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the competences where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the competence where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the competence that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.NoSuchCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the competences where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the competences where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the competences from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of competences where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching competences
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of competences where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching competences
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of competences where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching competences
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of competences that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching competences that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of competences where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching competences
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of competences.
	*
	* @return the number of competences
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}