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

package com.liferay.lms.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AuditEntry}.
 * </p>
 *
 * @author    TLS
 * @see       AuditEntry
 * @generated
 */
public class AuditEntryWrapper implements AuditEntry, ModelWrapper<AuditEntry> {
	public AuditEntryWrapper(AuditEntry auditEntry) {
		_auditEntry = auditEntry;
	}

	public Class<?> getModelClass() {
		return AuditEntry.class;
	}

	public String getModelClassName() {
		return AuditEntry.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("auditId", getAuditId());
		attributes.put("auditDate", getAuditDate());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("classname", getClassname());
		attributes.put("action", getAction());
		attributes.put("extradata", getExtradata());
		attributes.put("classPK", getClassPK());
		attributes.put("associationClassPK", getAssociationClassPK());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long auditId = (Long)attributes.get("auditId");

		if (auditId != null) {
			setAuditId(auditId);
		}

		Date auditDate = (Date)attributes.get("auditDate");

		if (auditDate != null) {
			setAuditDate(auditDate);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String classname = (String)attributes.get("classname");

		if (classname != null) {
			setClassname(classname);
		}

		String action = (String)attributes.get("action");

		if (action != null) {
			setAction(action);
		}

		String extradata = (String)attributes.get("extradata");

		if (extradata != null) {
			setExtradata(extradata);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long associationClassPK = (Long)attributes.get("associationClassPK");

		if (associationClassPK != null) {
			setAssociationClassPK(associationClassPK);
		}
	}

	/**
	* Returns the primary key of this audit entry.
	*
	* @return the primary key of this audit entry
	*/
	public long getPrimaryKey() {
		return _auditEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this audit entry.
	*
	* @param primaryKey the primary key of this audit entry
	*/
	public void setPrimaryKey(long primaryKey) {
		_auditEntry.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the audit ID of this audit entry.
	*
	* @return the audit ID of this audit entry
	*/
	public long getAuditId() {
		return _auditEntry.getAuditId();
	}

	/**
	* Sets the audit ID of this audit entry.
	*
	* @param auditId the audit ID of this audit entry
	*/
	public void setAuditId(long auditId) {
		_auditEntry.setAuditId(auditId);
	}

	/**
	* Returns the audit date of this audit entry.
	*
	* @return the audit date of this audit entry
	*/
	public java.util.Date getAuditDate() {
		return _auditEntry.getAuditDate();
	}

	/**
	* Sets the audit date of this audit entry.
	*
	* @param auditDate the audit date of this audit entry
	*/
	public void setAuditDate(java.util.Date auditDate) {
		_auditEntry.setAuditDate(auditDate);
	}

	/**
	* Returns the company ID of this audit entry.
	*
	* @return the company ID of this audit entry
	*/
	public long getCompanyId() {
		return _auditEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this audit entry.
	*
	* @param companyId the company ID of this audit entry
	*/
	public void setCompanyId(long companyId) {
		_auditEntry.setCompanyId(companyId);
	}

	/**
	* Returns the group ID of this audit entry.
	*
	* @return the group ID of this audit entry
	*/
	public long getGroupId() {
		return _auditEntry.getGroupId();
	}

	/**
	* Sets the group ID of this audit entry.
	*
	* @param groupId the group ID of this audit entry
	*/
	public void setGroupId(long groupId) {
		_auditEntry.setGroupId(groupId);
	}

	/**
	* Returns the user ID of this audit entry.
	*
	* @return the user ID of this audit entry
	*/
	public long getUserId() {
		return _auditEntry.getUserId();
	}

	/**
	* Sets the user ID of this audit entry.
	*
	* @param userId the user ID of this audit entry
	*/
	public void setUserId(long userId) {
		_auditEntry.setUserId(userId);
	}

	/**
	* Returns the user uuid of this audit entry.
	*
	* @return the user uuid of this audit entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _auditEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this audit entry.
	*
	* @param userUuid the user uuid of this audit entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_auditEntry.setUserUuid(userUuid);
	}

	/**
	* Returns the classname of this audit entry.
	*
	* @return the classname of this audit entry
	*/
	public java.lang.String getClassname() {
		return _auditEntry.getClassname();
	}

	/**
	* Sets the classname of this audit entry.
	*
	* @param classname the classname of this audit entry
	*/
	public void setClassname(java.lang.String classname) {
		_auditEntry.setClassname(classname);
	}

	/**
	* Returns the action of this audit entry.
	*
	* @return the action of this audit entry
	*/
	public java.lang.String getAction() {
		return _auditEntry.getAction();
	}

	/**
	* Sets the action of this audit entry.
	*
	* @param action the action of this audit entry
	*/
	public void setAction(java.lang.String action) {
		_auditEntry.setAction(action);
	}

	/**
	* Returns the extradata of this audit entry.
	*
	* @return the extradata of this audit entry
	*/
	public java.lang.String getExtradata() {
		return _auditEntry.getExtradata();
	}

	/**
	* Sets the extradata of this audit entry.
	*
	* @param extradata the extradata of this audit entry
	*/
	public void setExtradata(java.lang.String extradata) {
		_auditEntry.setExtradata(extradata);
	}

	/**
	* Returns the class p k of this audit entry.
	*
	* @return the class p k of this audit entry
	*/
	public long getClassPK() {
		return _auditEntry.getClassPK();
	}

	/**
	* Sets the class p k of this audit entry.
	*
	* @param classPK the class p k of this audit entry
	*/
	public void setClassPK(long classPK) {
		_auditEntry.setClassPK(classPK);
	}

	/**
	* Returns the association class p k of this audit entry.
	*
	* @return the association class p k of this audit entry
	*/
	public long getAssociationClassPK() {
		return _auditEntry.getAssociationClassPK();
	}

	/**
	* Sets the association class p k of this audit entry.
	*
	* @param associationClassPK the association class p k of this audit entry
	*/
	public void setAssociationClassPK(long associationClassPK) {
		_auditEntry.setAssociationClassPK(associationClassPK);
	}

	public boolean isNew() {
		return _auditEntry.isNew();
	}

	public void setNew(boolean n) {
		_auditEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _auditEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_auditEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _auditEntry.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _auditEntry.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_auditEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _auditEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_auditEntry.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new AuditEntryWrapper((AuditEntry)_auditEntry.clone());
	}

	public int compareTo(com.liferay.lms.model.AuditEntry auditEntry) {
		return _auditEntry.compareTo(auditEntry);
	}

	@Override
	public int hashCode() {
		return _auditEntry.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.AuditEntry> toCacheModel() {
		return _auditEntry.toCacheModel();
	}

	public com.liferay.lms.model.AuditEntry toEscapedModel() {
		return new AuditEntryWrapper(_auditEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _auditEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _auditEntry.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_auditEntry.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public AuditEntry getWrappedAuditEntry() {
		return _auditEntry;
	}

	public AuditEntry getWrappedModel() {
		return _auditEntry;
	}

	public void resetOriginalValues() {
		_auditEntry.resetOriginalValues();
	}

	private AuditEntry _auditEntry;
}