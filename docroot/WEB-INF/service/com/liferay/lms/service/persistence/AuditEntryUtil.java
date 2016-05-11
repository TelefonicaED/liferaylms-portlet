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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the audit entry service. This utility wraps {@link AuditEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see AuditEntryPersistence
 * @see AuditEntryPersistenceImpl
 * @generated
 */
public class AuditEntryUtil {
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
	public static void clearCache(AuditEntry auditEntry) {
		getPersistence().clearCache(auditEntry);
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
	public static List<AuditEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AuditEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AuditEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static AuditEntry update(AuditEntry auditEntry, boolean merge)
		throws SystemException {
		return getPersistence().update(auditEntry, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static AuditEntry update(AuditEntry auditEntry, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(auditEntry, merge, serviceContext);
	}

	/**
	* Caches the audit entry in the entity cache if it is enabled.
	*
	* @param auditEntry the audit entry
	*/
	public static void cacheResult(com.liferay.lms.model.AuditEntry auditEntry) {
		getPersistence().cacheResult(auditEntry);
	}

	/**
	* Caches the audit entries in the entity cache if it is enabled.
	*
	* @param auditEntries the audit entries
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.AuditEntry> auditEntries) {
		getPersistence().cacheResult(auditEntries);
	}

	/**
	* Creates a new audit entry with the primary key. Does not add the audit entry to the database.
	*
	* @param auditId the primary key for the new audit entry
	* @return the new audit entry
	*/
	public static com.liferay.lms.model.AuditEntry create(long auditId) {
		return getPersistence().create(auditId);
	}

	/**
	* Removes the audit entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param auditId the primary key of the audit entry
	* @return the audit entry that was removed
	* @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry remove(long auditId)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(auditId);
	}

	public static com.liferay.lms.model.AuditEntry updateImpl(
		com.liferay.lms.model.AuditEntry auditEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(auditEntry, merge);
	}

	/**
	* Returns the audit entry with the primary key or throws a {@link com.liferay.lms.NoSuchAuditEntryException} if it could not be found.
	*
	* @param auditId the primary key of the audit entry
	* @return the audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry findByPrimaryKey(
		long auditId)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(auditId);
	}

	/**
	* Returns the audit entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param auditId the primary key of the audit entry
	* @return the audit entry, or <code>null</code> if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry fetchByPrimaryKey(
		long auditId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(auditId);
	}

	/**
	* Returns all the audit entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first audit entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first audit entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last audit entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last audit entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry[] findByGroupId_PrevAndNext(
		long auditId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(auditId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the audit entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first audit entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first audit entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last audit entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last audit entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry[] findByCompanyId_PrevAndNext(
		long auditId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(auditId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the audit entries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first audit entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first audit entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last audit entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry
	* @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last audit entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry[] findByUserId_PrevAndNext(
		long auditId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(auditId, userId, orderByComparator);
	}

	/**
	* Returns all the audit entries where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByU_G(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_G(userId, groupId);
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByU_G(
		long userId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_G(userId, groupId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByU_G(
		long userId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_G(userId, groupId, start, end, orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry findByU_G_First(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_G_First(userId, groupId, orderByComparator);
	}

	/**
	* Returns the first audit entry in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry fetchByU_G_First(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_G_First(userId, groupId, orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry findByU_G_Last(long userId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_G_Last(userId, groupId, orderByComparator);
	}

	/**
	* Returns the last audit entry in the ordered set where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry fetchByU_G_Last(
		long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_G_Last(userId, groupId, orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry[] findByU_G_PrevAndNext(
		long auditId, long userId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_G_PrevAndNext(auditId, userId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the audit entries where userId = &#63; and groupId = &#63; and classname = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param classname the classname
	* @return the matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByU_G_c(
		long userId, long groupId, java.lang.String classname)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_G_c(userId, groupId, classname);
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByU_G_c(
		long userId, long groupId, java.lang.String classname, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_G_c(userId, groupId, classname, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByU_G_c(
		long userId, long groupId, java.lang.String classname, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_G_c(userId, groupId, classname, start, end,
			orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry findByU_G_c_First(
		long userId, long groupId, java.lang.String classname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_G_c_First(userId, groupId, classname,
			orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry fetchByU_G_c_First(
		long userId, long groupId, java.lang.String classname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_G_c_First(userId, groupId, classname,
			orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry findByU_G_c_Last(
		long userId, long groupId, java.lang.String classname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_G_c_Last(userId, groupId, classname,
			orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry fetchByU_G_c_Last(
		long userId, long groupId, java.lang.String classname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_G_c_Last(userId, groupId, classname,
			orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry[] findByU_G_c_PrevAndNext(
		long auditId, long userId, long groupId, java.lang.String classname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_G_c_PrevAndNext(auditId, userId, groupId,
			classname, orderByComparator);
	}

	/**
	* Returns all the audit entries where classname = &#63; and classPK = &#63;.
	*
	* @param classname the classname
	* @param classPK the class p k
	* @return the matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByCN_CP(
		java.lang.String classname, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCN_CP(classname, classPK);
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByCN_CP(
		java.lang.String classname, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCN_CP(classname, classPK, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findByCN_CP(
		java.lang.String classname, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCN_CP(classname, classPK, start, end,
			orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry findByCN_CP_First(
		java.lang.String classname, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCN_CP_First(classname, classPK, orderByComparator);
	}

	/**
	* Returns the first audit entry in the ordered set where classname = &#63; and classPK = &#63;.
	*
	* @param classname the classname
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry fetchByCN_CP_First(
		java.lang.String classname, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCN_CP_First(classname, classPK, orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry findByCN_CP_Last(
		java.lang.String classname, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCN_CP_Last(classname, classPK, orderByComparator);
	}

	/**
	* Returns the last audit entry in the ordered set where classname = &#63; and classPK = &#63;.
	*
	* @param classname the classname
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.AuditEntry fetchByCN_CP_Last(
		java.lang.String classname, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCN_CP_Last(classname, classPK, orderByComparator);
	}

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
	public static com.liferay.lms.model.AuditEntry[] findByCN_CP_PrevAndNext(
		long auditId, java.lang.String classname, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchAuditEntryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCN_CP_PrevAndNext(auditId, classname, classPK,
			orderByComparator);
	}

	/**
	* Returns all the audit entries.
	*
	* @return the audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.AuditEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.AuditEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the audit entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the audit entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Removes all the audit entries where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes all the audit entries where userId = &#63; and groupId = &#63; from the database.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByU_G(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByU_G(userId, groupId);
	}

	/**
	* Removes all the audit entries where userId = &#63; and groupId = &#63; and classname = &#63; from the database.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param classname the classname
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByU_G_c(long userId, long groupId,
		java.lang.String classname)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByU_G_c(userId, groupId, classname);
	}

	/**
	* Removes all the audit entries where classname = &#63; and classPK = &#63; from the database.
	*
	* @param classname the classname
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCN_CP(java.lang.String classname, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCN_CP(classname, classPK);
	}

	/**
	* Removes all the audit entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of audit entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of audit entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of audit entries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the number of audit entries where userId = &#63; and groupId = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @return the number of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByU_G(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_G(userId, groupId);
	}

	/**
	* Returns the number of audit entries where userId = &#63; and groupId = &#63; and classname = &#63;.
	*
	* @param userId the user ID
	* @param groupId the group ID
	* @param classname the classname
	* @return the number of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByU_G_c(long userId, long groupId,
		java.lang.String classname)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_G_c(userId, groupId, classname);
	}

	/**
	* Returns the number of audit entries where classname = &#63; and classPK = &#63;.
	*
	* @param classname the classname
	* @param classPK the class p k
	* @return the number of matching audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCN_CP(java.lang.String classname, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCN_CP(classname, classPK);
	}

	/**
	* Returns the number of audit entries.
	*
	* @return the number of audit entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static AuditEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AuditEntryPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					AuditEntryPersistence.class.getName());

			ReferenceRegistry.registerReference(AuditEntryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(AuditEntryPersistence persistence) {
	}

	private static AuditEntryPersistence _persistence;
}