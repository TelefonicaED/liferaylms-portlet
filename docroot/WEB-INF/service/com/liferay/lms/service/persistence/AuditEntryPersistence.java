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

import com.liferay.lms.model.AuditEntry;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the audit entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see AuditEntryPersistenceImpl
 * @see AuditEntryUtil
 * @generated
 */
public interface AuditEntryPersistence extends BasePersistence<AuditEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AuditEntryUtil} to access the audit entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the audit entry in the entity cache if it is enabled.
	*
	* @param auditEntry the audit entry
	*/
	public void cacheResult(com.liferay.lms.model.AuditEntry auditEntry);

	/**
	* Caches the audit entries in the entity cache if it is enabled.
	*
	* @param auditEntries the audit entries
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.AuditEntry> auditEntries);

	/**
	* Creates a new audit entry with the primary key. Does not add the audit entry to the database.
	*
	* @param auditId the primary key for the new audit entry
	* @return the new audit entry
	*/
	public com.liferay.lms.model.AuditEntry create(long auditId);

	/**
	* Removes the audit entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param auditId the primary key of the audit entry
	* @return the audit entry that was removed
	* @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry remove(long auditId)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.AuditEntry updateImpl(
		com.liferay.lms.model.AuditEntry auditEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the audit entry with the primary key or throws a {@link com.liferay.lms.NoSuchAuditEntryException} if it could not be found.
	*
	* @param auditId the primary key of the audit entry
	* @return the audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry findByPrimaryKey(long auditId)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the audit entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param auditId the primary key of the audit entry
	* @return the audit entry, or <code>null</code> if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry fetchByPrimaryKey(long auditId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the audit entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the audit entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @return the range of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the audit entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first audit entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first audit entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last audit entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last audit entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the audit entries before and after the current audit entry in the ordered set where groupId = &#63;.
	*
	* @param auditId the primary key of the current audit entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry[] findByGroupId_PrevAndNext(
		long auditId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the audit entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the audit entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @return the range of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the audit entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first audit entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first audit entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last audit entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last audit entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the audit entries before and after the current audit entry in the ordered set where companyId = &#63;.
	*
	* @param auditId the primary key of the current audit entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry[] findByCompanyId_PrevAndNext(
		long auditId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the audit entries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the audit entries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @return the range of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the audit entries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first audit entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first audit entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last audit entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last audit entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the audit entries before and after the current audit entry in the ordered set where userId = &#63;.
	*
	* @param auditId the primary key of the current audit entry
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry[] findByUserId_PrevAndNext(
		long auditId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the audit entries where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByU_G(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the audit entries where userId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @return the range of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByU_G(
		long userId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the audit entries where userId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByU_G(
		long userId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first audit entry in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry findByU_G_First(long userId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first audit entry in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry fetchByU_G_First(long userId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last audit entry in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry findByU_G_Last(long userId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last audit entry in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry fetchByU_G_Last(long userId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the audit entries before and after the current audit entry in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param auditId the primary key of the current audit entry
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry[] findByU_G_PrevAndNext(
		long auditId, long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the audit entries where userId = &#63; and groupId = &#63; and classname = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param classname the classname
	* @return the matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByU_G_c(
		long userId, long groupId, java.lang.String classname)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the audit entries where userId = &#63; and groupId = &#63; and classname = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param classname the classname
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @return the range of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByU_G_c(
		long userId, long groupId, java.lang.String classname, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the audit entries where userId = &#63; and groupId = &#63; and classname = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param classname the classname
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByU_G_c(
		long userId, long groupId, java.lang.String classname, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first audit entry in the ordered set where userId = &#63; and groupId = &#63; and classname = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param classname the classname
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry findByU_G_c_First(long userId,
		long groupId, java.lang.String classname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first audit entry in the ordered set where userId = &#63; and groupId = &#63; and classname = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param classname the classname
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry fetchByU_G_c_First(long userId,
		long groupId, java.lang.String classname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last audit entry in the ordered set where userId = &#63; and groupId = &#63; and classname = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param classname the classname
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry findByU_G_c_Last(long userId,
		long groupId, java.lang.String classname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last audit entry in the ordered set where userId = &#63; and groupId = &#63; and classname = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param classname the classname
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry fetchByU_G_c_Last(long userId,
		long groupId, java.lang.String classname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the audit entries before and after the current audit entry in the ordered set where userId = &#63; and groupId = &#63; and classname = &#63;.
	*
	* @param auditId the primary key of the current audit entry
	* @param userId the user ID
	* @param groupId the group ID
	* @param classname the classname
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry[] findByU_G_c_PrevAndNext(
		long auditId, long userId, long groupId, java.lang.String classname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the audit entries where classname = &#63; and classPK = &#63;.
	*
	* @param classname the classname
	* @param classPK the class p k
	* @return the matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByCN_CP(
		java.lang.String classname, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the audit entries where classname = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classname the classname
	* @param classPK the class p k
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @return the range of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByCN_CP(
		java.lang.String classname, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the audit entries where classname = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classname the classname
	* @param classPK the class p k
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findByCN_CP(
		java.lang.String classname, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first audit entry in the ordered set where classname = &#63; and classPK = &#63;.
	*
	* @param classname the classname
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry findByCN_CP_First(
		java.lang.String classname, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first audit entry in the ordered set where classname = &#63; and classPK = &#63;.
	*
	* @param classname the classname
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry fetchByCN_CP_First(
		java.lang.String classname, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last audit entry in the ordered set where classname = &#63; and classPK = &#63;.
	*
	* @param classname the classname
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry findByCN_CP_Last(
		java.lang.String classname, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last audit entry in the ordered set where classname = &#63; and classPK = &#63;.
	*
	* @param classname the classname
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry fetchByCN_CP_Last(
		java.lang.String classname, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the audit entries before and after the current audit entry in the ordered set where classname = &#63; and classPK = &#63;.
	*
	* @param auditId the primary key of the current audit entry
	* @param classname the classname
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry[] findByCN_CP_PrevAndNext(
		long auditId, java.lang.String classname, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the audit entries.
	*
	* @return the audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the audit entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @return the range of audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the audit entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of audit entries
	* @param end the upper bound of the range of audit entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of audit entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.AuditEntry> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the audit entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the audit entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the audit entries where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the audit entries where userId = &#63; and groupId = &#63; from the database.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByU_G(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the audit entries where userId = &#63; and groupId = &#63; and classname = &#63; from the database.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param classname the classname
	* @throws SystemException if a system exception occurred
	*/
	public void removeByU_G_c(long userId, long groupId,
		java.lang.String classname)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the audit entries where classname = &#63; and classPK = &#63; from the database.
	*
	* @param classname the classname
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCN_CP(java.lang.String classname, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the audit entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of audit entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of audit entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of audit entries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of audit entries where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the number of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByU_G(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of audit entries where userId = &#63; and groupId = &#63; and classname = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param classname the classname
	* @return the number of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByU_G_c(long userId, long groupId,
		java.lang.String classname)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of audit entries where classname = &#63; and classPK = &#63;.
	*
	* @param classname the classname
	* @param classPK the class p k
	* @return the number of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByCN_CP(java.lang.String classname, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of audit entries.
	*
	* @return the number of audit entries
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}