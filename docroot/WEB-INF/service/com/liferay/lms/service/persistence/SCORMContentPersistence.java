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

import com.liferay.lms.model.SCORMContent;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the s c o r m content service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see SCORMContentPersistenceImpl
 * @see SCORMContentUtil
 * @generated
 */
public interface SCORMContentPersistence extends BasePersistence<SCORMContent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SCORMContentUtil} to access the s c o r m content persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the s c o r m content in the entity cache if it is enabled.
	*
	* @param scormContent the s c o r m content
	*/
	public void cacheResult(com.liferay.lms.model.SCORMContent scormContent);

	/**
	* Caches the s c o r m contents in the entity cache if it is enabled.
	*
	* @param scormContents the s c o r m contents
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.SCORMContent> scormContents);

	/**
	* Creates a new s c o r m content with the primary key. Does not add the s c o r m content to the database.
	*
	* @param scormId the primary key for the new s c o r m content
	* @return the new s c o r m content
	*/
	public com.liferay.lms.model.SCORMContent create(long scormId);

	/**
	* Removes the s c o r m content with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param scormId the primary key of the s c o r m content
	* @return the s c o r m content that was removed
	* @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent remove(long scormId)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.SCORMContent updateImpl(
		com.liferay.lms.model.SCORMContent scormContent, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m content with the primary key or throws a {@link com.liferay.lms.NoSuchSCORMContentException} if it could not be found.
	*
	* @param scormId the primary key of the s c o r m content
	* @return the s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByPrimaryKey(long scormId)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m content with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param scormId the primary key of the s c o r m content
	* @return the s c o r m content, or <code>null</code> if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByPrimaryKey(long scormId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the s c o r m contents where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the s c o r m contents where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @return the range of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the s c o r m contents where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first s c o r m content in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first s c o r m content in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last s c o r m content in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last s c o r m content in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m contents before and after the current s c o r m content in the ordered set where uuid = &#63;.
	*
	* @param scormId the primary key of the current s c o r m content
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent[] findByUuid_PrevAndNext(
		long scormId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m content where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lms.NoSuchSCORMContentException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m content where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m content where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the s c o r m contents where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByscormId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the s c o r m contents where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @return the range of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByscormId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the s c o r m contents where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByscormId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByscormId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByscormId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByscormId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByscormId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m contents before and after the current s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param scormId the primary key of the current s c o r m content
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent[] findByscormId_PrevAndNext(
		long scormId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the s c o r m contents that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> filterFindByscormId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the s c o r m contents that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @return the range of matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> filterFindByscormId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the s c o r m contents that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> filterFindByscormId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m contents before and after the current s c o r m content in the ordered set of s c o r m contents that the user has permission to view where groupId = &#63;.
	*
	* @param scormId the primary key of the current s c o r m content
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent[] filterFindByscormId_PrevAndNext(
		long scormId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the s c o r m contents where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the s c o r m contents where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @return the range of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the s c o r m contents where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first s c o r m content in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first s c o r m content in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last s c o r m content in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last s c o r m content in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m contents before and after the current s c o r m content in the ordered set where companyId = &#63;.
	*
	* @param scormId the primary key of the current s c o r m content
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent[] findByCompanyId_PrevAndNext(
		long scormId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the s c o r m contents where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the s c o r m contents where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @return the range of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the s c o r m contents where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first s c o r m content in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first s c o r m content in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last s c o r m content in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last s c o r m content in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m contents before and after the current s c o r m content in the ordered set where userId = &#63;.
	*
	* @param scormId the primary key of the current s c o r m content
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent[] findByUserId_PrevAndNext(
		long scormId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the s c o r m contents where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the s c o r m contents where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @return the range of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the s c o r m contents where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m contents before and after the current s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param scormId the primary key of the current s c o r m content
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent[] findByGroupId_PrevAndNext(
		long scormId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the s c o r m contents that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the s c o r m contents that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @return the range of matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the s c o r m contents that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m contents before and after the current s c o r m content in the ordered set of s c o r m contents that the user has permission to view where groupId = &#63;.
	*
	* @param scormId the primary key of the current s c o r m content
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent[] filterFindByGroupId_PrevAndNext(
		long scormId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the s c o r m contents where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByUserIdGroupId(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the s c o r m contents where userId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @return the range of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByUserIdGroupId(
		long userId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the s c o r m contents where userId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findByUserIdGroupId(
		long userId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first s c o r m content in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByUserIdGroupId_First(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first s c o r m content in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByUserIdGroupId_First(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last s c o r m content in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent findByUserIdGroupId_Last(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last s c o r m content in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent fetchByUserIdGroupId_Last(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m contents before and after the current s c o r m content in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param scormId the primary key of the current s c o r m content
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent[] findByUserIdGroupId_PrevAndNext(
		long scormId, long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the s c o r m contents that the user has permission to view where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> filterFindByUserIdGroupId(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the s c o r m contents that the user has permission to view where userId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @return the range of matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> filterFindByUserIdGroupId(
		long userId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the s c o r m contents that the user has permissions to view where userId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> filterFindByUserIdGroupId(
		long userId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the s c o r m contents before and after the current s c o r m content in the ordered set of s c o r m contents that the user has permission to view where userId = &#63; and groupId = &#63;.
	*
	* @param scormId the primary key of the current s c o r m content
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent[] filterFindByUserIdGroupId_PrevAndNext(
		long scormId, long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the s c o r m contents.
	*
	* @return the s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the s c o r m contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @return the range of s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the s c o r m contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of s c o r m contents
	* @param end the upper bound of the range of s c o r m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SCORMContent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the s c o r m contents where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the s c o r m content where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the s c o r m content that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SCORMContent removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the s c o r m contents where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByscormId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the s c o r m contents where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the s c o r m contents where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the s c o r m contents where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the s c o r m contents where userId = &#63; and groupId = &#63; from the database.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserIdGroupId(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the s c o r m contents from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c o r m contents where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c o r m contents where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c o r m contents where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByscormId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c o r m contents that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByscormId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c o r m contents where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c o r m contents where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c o r m contents where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c o r m contents that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c o r m contents where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserIdGroupId(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c o r m contents that the user has permission to view where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the number of matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByUserIdGroupId(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of s c o r m contents.
	*
	* @return the number of s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}