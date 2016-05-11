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

package com.liferay.lms.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link AuditEntryLocalService}.
 * </p>
 *
 * @author    TLS
 * @see       AuditEntryLocalService
 * @generated
 */
public class AuditEntryLocalServiceWrapper implements AuditEntryLocalService,
	ServiceWrapper<AuditEntryLocalService> {
	public AuditEntryLocalServiceWrapper(
		AuditEntryLocalService auditEntryLocalService) {
		_auditEntryLocalService = auditEntryLocalService;
	}

	/**
	* Adds the audit entry to the database. Also notifies the appropriate model listeners.
	*
	* @param auditEntry the audit entry
	* @return the audit entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry addAuditEntry(
		com.liferay.lms.model.AuditEntry auditEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.addAuditEntry(auditEntry);
	}

	/**
	* Creates a new audit entry with the primary key. Does not add the audit entry to the database.
	*
	* @param auditId the primary key for the new audit entry
	* @return the new audit entry
	*/
	public com.liferay.lms.model.AuditEntry createAuditEntry(long auditId) {
		return _auditEntryLocalService.createAuditEntry(auditId);
	}

	/**
	* Deletes the audit entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param auditId the primary key of the audit entry
	* @return the audit entry that was removed
	* @throws PortalException if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry deleteAuditEntry(long auditId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.deleteAuditEntry(auditId);
	}

	/**
	* Deletes the audit entry from the database. Also notifies the appropriate model listeners.
	*
	* @param auditEntry the audit entry
	* @return the audit entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry deleteAuditEntry(
		com.liferay.lms.model.AuditEntry auditEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.deleteAuditEntry(auditEntry);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _auditEntryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.lms.model.AuditEntry fetchAuditEntry(long auditId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.fetchAuditEntry(auditId);
	}

	/**
	* Returns the audit entry with the primary key.
	*
	* @param auditId the primary key of the audit entry
	* @return the audit entry
	* @throws PortalException if a audit entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry getAuditEntry(long auditId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.getAuditEntry(auditId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.getPersistedModel(primaryKeyObj);
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
	public java.util.List<com.liferay.lms.model.AuditEntry> getAuditEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.getAuditEntries(start, end);
	}

	/**
	* Returns the number of audit entries.
	*
	* @return the number of audit entries
	* @throws SystemException if a system exception occurred
	*/
	public int getAuditEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.getAuditEntriesCount();
	}

	/**
	* Updates the audit entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param auditEntry the audit entry
	* @return the audit entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry updateAuditEntry(
		com.liferay.lms.model.AuditEntry auditEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.updateAuditEntry(auditEntry);
	}

	/**
	* Updates the audit entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param auditEntry the audit entry
	* @param merge whether to merge the audit entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the audit entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.AuditEntry updateAuditEntry(
		com.liferay.lms.model.AuditEntry auditEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.updateAuditEntry(auditEntry, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _auditEntryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_auditEntryLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _auditEntryLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	public void addAuditEntry(long companyId, long groupId,
		java.lang.String className, long classPK, long associationClassPK,
		long userId, java.lang.String action, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		_auditEntryLocalService.addAuditEntry(companyId, groupId, className,
			classPK, associationClassPK, userId, action, extraData);
	}

	public void addAuditEntry(long companyId, long groupId,
		java.lang.String className, long classPK, long userId,
		java.lang.String action, java.lang.String extraData)
		throws com.liferay.portal.kernel.exception.SystemException {
		_auditEntryLocalService.addAuditEntry(companyId, groupId, className,
			classPK, userId, action, extraData);
	}

	public java.util.List<com.liferay.lms.model.AuditEntry> search(
		long companyId, long groupId, java.lang.String className, long classPK,
		long userId, java.util.Date startDate, java.util.Date endDate,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.search(companyId, groupId, className,
			classPK, userId, startDate, endDate, start, end);
	}

	public long searchCount(long companyId, long groupId,
		java.lang.String className, long classPK, long userId,
		java.util.Date startDate, java.util.Date endDate, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntryLocalService.searchCount(companyId, groupId,
			className, classPK, userId, startDate, endDate, start, end);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public AuditEntryLocalService getWrappedAuditEntryLocalService() {
		return _auditEntryLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedAuditEntryLocalService(
		AuditEntryLocalService auditEntryLocalService) {
		_auditEntryLocalService = auditEntryLocalService;
	}

	public AuditEntryLocalService getWrappedService() {
		return _auditEntryLocalService;
	}

	public void setWrappedService(AuditEntryLocalService auditEntryLocalService) {
		_auditEntryLocalService = auditEntryLocalService;
	}

	private AuditEntryLocalService _auditEntryLocalService;
}