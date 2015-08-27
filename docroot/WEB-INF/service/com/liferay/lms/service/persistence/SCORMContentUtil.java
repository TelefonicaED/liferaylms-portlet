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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the s c o r m content service. This utility wraps {@link SCORMContentPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see SCORMContentPersistence
 * @see SCORMContentPersistenceImpl
 * @generated
 */
public class SCORMContentUtil {
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
	public static void clearCache(SCORMContent scormContent) {
		getPersistence().clearCache(scormContent);
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
	public static List<SCORMContent> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SCORMContent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SCORMContent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SCORMContent update(SCORMContent scormContent, boolean merge)
		throws SystemException {
		return getPersistence().update(scormContent, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static SCORMContent update(SCORMContent scormContent, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(scormContent, merge, serviceContext);
	}

	/**
	* Caches the s c o r m content in the entity cache if it is enabled.
	*
	* @param scormContent the s c o r m content
	*/
	public static void cacheResult(
		com.liferay.lms.model.SCORMContent scormContent) {
		getPersistence().cacheResult(scormContent);
	}

	/**
	* Caches the s c o r m contents in the entity cache if it is enabled.
	*
	* @param scormContents the s c o r m contents
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.SCORMContent> scormContents) {
		getPersistence().cacheResult(scormContents);
	}

	/**
	* Creates a new s c o r m content with the primary key. Does not add the s c o r m content to the database.
	*
	* @param scormId the primary key for the new s c o r m content
	* @return the new s c o r m content
	*/
	public static com.liferay.lms.model.SCORMContent create(long scormId) {
		return getPersistence().create(scormId);
	}

	/**
	* Removes the s c o r m content with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param scormId the primary key of the s c o r m content
	* @return the s c o r m content that was removed
	* @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent remove(long scormId)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(scormId);
	}

	public static com.liferay.lms.model.SCORMContent updateImpl(
		com.liferay.lms.model.SCORMContent scormContent, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(scormContent, merge);
	}

	/**
	* Returns the s c o r m content with the primary key or throws a {@link com.liferay.lms.NoSuchSCORMContentException} if it could not be found.
	*
	* @param scormId the primary key of the s c o r m content
	* @return the s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent findByPrimaryKey(
		long scormId)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(scormId);
	}

	/**
	* Returns the s c o r m content with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param scormId the primary key of the s c o r m content
	* @return the s c o r m content, or <code>null</code> if a s c o r m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByPrimaryKey(
		long scormId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(scormId);
	}

	/**
	* Returns all the s c o r m contents where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first s c o r m content in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first s c o r m content in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last s c o r m content in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last s c o r m content in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

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
	public static com.liferay.lms.model.SCORMContent[] findByUuid_PrevAndNext(
		long scormId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(scormId, uuid, orderByComparator);
	}

	/**
	* Returns the s c o r m content where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lms.NoSuchSCORMContentException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the s c o r m content where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the s c o r m content where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Returns all the s c o r m contents where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByscormId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByscormId(groupId);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByscormId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByscormId(groupId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByscormId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByscormId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent findByscormId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByscormId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByscormId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByscormId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent findByscormId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByscormId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByscormId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByscormId_Last(groupId, orderByComparator);
	}

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
	public static com.liferay.lms.model.SCORMContent[] findByscormId_PrevAndNext(
		long scormId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByscormId_PrevAndNext(scormId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the s c o r m contents that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SCORMContent> filterFindByscormId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByscormId(groupId);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> filterFindByscormId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByscormId(groupId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> filterFindByscormId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByscormId(groupId, start, end, orderByComparator);
	}

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
	public static com.liferay.lms.model.SCORMContent[] filterFindByscormId_PrevAndNext(
		long scormId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByscormId_PrevAndNext(scormId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the s c o r m contents where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first s c o r m content in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first s c o r m content in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last s c o r m content in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last s c o r m content in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

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
	public static com.liferay.lms.model.SCORMContent[] findByCompanyId_PrevAndNext(
		long scormId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(scormId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the s c o r m contents where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first s c o r m content in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first s c o r m content in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last s c o r m content in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last s c o r m content in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

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
	public static com.liferay.lms.model.SCORMContent[] findByUserId_PrevAndNext(
		long scormId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(scormId, userId, orderByComparator);
	}

	/**
	* Returns all the s c o r m contents where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content
	* @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last s c o r m content in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

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
	public static com.liferay.lms.model.SCORMContent[] findByGroupId_PrevAndNext(
		long scormId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(scormId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the s c o r m contents that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SCORMContent> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

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
	public static com.liferay.lms.model.SCORMContent[] filterFindByGroupId_PrevAndNext(
		long scormId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(scormId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the s c o r m contents where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByUserIdGroupId(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserIdGroupId(userId, groupId);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByUserIdGroupId(
		long userId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserIdGroupId(userId, groupId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findByUserIdGroupId(
		long userId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserIdGroupId(userId, groupId, start, end,
			orderByComparator);
	}

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
	public static com.liferay.lms.model.SCORMContent findByUserIdGroupId_First(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserIdGroupId_First(userId, groupId, orderByComparator);
	}

	/**
	* Returns the first s c o r m content in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByUserIdGroupId_First(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUserIdGroupId_First(userId, groupId,
			orderByComparator);
	}

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
	public static com.liferay.lms.model.SCORMContent findByUserIdGroupId_Last(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserIdGroupId_Last(userId, groupId, orderByComparator);
	}

	/**
	* Returns the last s c o r m content in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent fetchByUserIdGroupId_Last(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUserIdGroupId_Last(userId, groupId, orderByComparator);
	}

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
	public static com.liferay.lms.model.SCORMContent[] findByUserIdGroupId_PrevAndNext(
		long scormId, long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserIdGroupId_PrevAndNext(scormId, userId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the s c o r m contents that the user has permission to view where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SCORMContent> filterFindByUserIdGroupId(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByUserIdGroupId(userId, groupId);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> filterFindByUserIdGroupId(
		long userId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByUserIdGroupId(userId, groupId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> filterFindByUserIdGroupId(
		long userId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByUserIdGroupId(userId, groupId, start, end,
			orderByComparator);
	}

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
	public static com.liferay.lms.model.SCORMContent[] filterFindByUserIdGroupId_PrevAndNext(
		long scormId, long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByUserIdGroupId_PrevAndNext(scormId, userId,
			groupId, orderByComparator);
	}

	/**
	* Returns all the s c o r m contents.
	*
	* @return the s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.SCORMContent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.SCORMContent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the s c o r m contents where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the s c o r m content where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the s c o r m content that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.SCORMContent removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.NoSuchSCORMContentException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the s c o r m contents where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByscormId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByscormId(groupId);
	}

	/**
	* Removes all the s c o r m contents where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Removes all the s c o r m contents where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes all the s c o r m contents where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the s c o r m contents where userId = &#63; and groupId = &#63; from the database.
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
	* Removes all the s c o r m contents from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of s c o r m contents where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of s c o r m contents where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of s c o r m contents where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static int countByscormId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByscormId(groupId);
	}

	/**
	* Returns the number of s c o r m contents that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByscormId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByscormId(groupId);
	}

	/**
	* Returns the number of s c o r m contents where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of s c o r m contents where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the number of s c o r m contents where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of s c o r m contents that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the number of s c o r m contents where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the number of matching s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserIdGroupId(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserIdGroupId(userId, groupId);
	}

	/**
	* Returns the number of s c o r m contents that the user has permission to view where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the number of matching s c o r m contents that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByUserIdGroupId(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByUserIdGroupId(userId, groupId);
	}

	/**
	* Returns the number of s c o r m contents.
	*
	* @return the number of s c o r m contents
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SCORMContentPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SCORMContentPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					SCORMContentPersistence.class.getName());

			ReferenceRegistry.registerReference(SCORMContentUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(SCORMContentPersistence persistence) {
	}

	private static SCORMContentPersistence _persistence;
}