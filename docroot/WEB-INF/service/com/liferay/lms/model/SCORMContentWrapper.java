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
 * This class is a wrapper for {@link SCORMContent}.
 * </p>
 *
 * @author    TLS
 * @see       SCORMContent
 * @generated
 */
public class SCORMContentWrapper implements SCORMContent,
	ModelWrapper<SCORMContent> {
	public SCORMContentWrapper(SCORMContent scormContent) {
		_scormContent = scormContent;
	}

	public Class<?> getModelClass() {
		return SCORMContent.class;
	}

	public String getModelClassName() {
		return SCORMContent.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("scormId", getScormId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("index", getIndex());
		attributes.put("ciphered", getCiphered());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long scormId = (Long)attributes.get("scormId");

		if (scormId != null) {
			setScormId(scormId);
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

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String index = (String)attributes.get("index");

		if (index != null) {
			setIndex(index);
		}

		Boolean ciphered = (Boolean)attributes.get("ciphered");

		if (ciphered != null) {
			setCiphered(ciphered);
		}
	}

	/**
	* Returns the primary key of this s c o r m content.
	*
	* @return the primary key of this s c o r m content
	*/
	public long getPrimaryKey() {
		return _scormContent.getPrimaryKey();
	}

	/**
	* Sets the primary key of this s c o r m content.
	*
	* @param primaryKey the primary key of this s c o r m content
	*/
	public void setPrimaryKey(long primaryKey) {
		_scormContent.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this s c o r m content.
	*
	* @return the uuid of this s c o r m content
	*/
	public java.lang.String getUuid() {
		return _scormContent.getUuid();
	}

	/**
	* Sets the uuid of this s c o r m content.
	*
	* @param uuid the uuid of this s c o r m content
	*/
	public void setUuid(java.lang.String uuid) {
		_scormContent.setUuid(uuid);
	}

	/**
	* Returns the scorm ID of this s c o r m content.
	*
	* @return the scorm ID of this s c o r m content
	*/
	public long getScormId() {
		return _scormContent.getScormId();
	}

	/**
	* Sets the scorm ID of this s c o r m content.
	*
	* @param scormId the scorm ID of this s c o r m content
	*/
	public void setScormId(long scormId) {
		_scormContent.setScormId(scormId);
	}

	/**
	* Returns the company ID of this s c o r m content.
	*
	* @return the company ID of this s c o r m content
	*/
	public long getCompanyId() {
		return _scormContent.getCompanyId();
	}

	/**
	* Sets the company ID of this s c o r m content.
	*
	* @param companyId the company ID of this s c o r m content
	*/
	public void setCompanyId(long companyId) {
		_scormContent.setCompanyId(companyId);
	}

	/**
	* Returns the group ID of this s c o r m content.
	*
	* @return the group ID of this s c o r m content
	*/
	public long getGroupId() {
		return _scormContent.getGroupId();
	}

	/**
	* Sets the group ID of this s c o r m content.
	*
	* @param groupId the group ID of this s c o r m content
	*/
	public void setGroupId(long groupId) {
		_scormContent.setGroupId(groupId);
	}

	/**
	* Returns the user ID of this s c o r m content.
	*
	* @return the user ID of this s c o r m content
	*/
	public long getUserId() {
		return _scormContent.getUserId();
	}

	/**
	* Sets the user ID of this s c o r m content.
	*
	* @param userId the user ID of this s c o r m content
	*/
	public void setUserId(long userId) {
		_scormContent.setUserId(userId);
	}

	/**
	* Returns the user uuid of this s c o r m content.
	*
	* @return the user uuid of this s c o r m content
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scormContent.getUserUuid();
	}

	/**
	* Sets the user uuid of this s c o r m content.
	*
	* @param userUuid the user uuid of this s c o r m content
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_scormContent.setUserUuid(userUuid);
	}

	/**
	* Returns the status of this s c o r m content.
	*
	* @return the status of this s c o r m content
	*/
	public int getStatus() {
		return _scormContent.getStatus();
	}

	/**
	* Sets the status of this s c o r m content.
	*
	* @param status the status of this s c o r m content
	*/
	public void setStatus(int status) {
		_scormContent.setStatus(status);
	}

	/**
	* Returns the status by user ID of this s c o r m content.
	*
	* @return the status by user ID of this s c o r m content
	*/
	public long getStatusByUserId() {
		return _scormContent.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this s c o r m content.
	*
	* @param statusByUserId the status by user ID of this s c o r m content
	*/
	public void setStatusByUserId(long statusByUserId) {
		_scormContent.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this s c o r m content.
	*
	* @return the status by user uuid of this s c o r m content
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scormContent.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this s c o r m content.
	*
	* @param statusByUserUuid the status by user uuid of this s c o r m content
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_scormContent.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this s c o r m content.
	*
	* @return the status by user name of this s c o r m content
	*/
	public java.lang.String getStatusByUserName() {
		return _scormContent.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this s c o r m content.
	*
	* @param statusByUserName the status by user name of this s c o r m content
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_scormContent.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this s c o r m content.
	*
	* @return the status date of this s c o r m content
	*/
	public java.util.Date getStatusDate() {
		return _scormContent.getStatusDate();
	}

	/**
	* Sets the status date of this s c o r m content.
	*
	* @param statusDate the status date of this s c o r m content
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_scormContent.setStatusDate(statusDate);
	}

	/**
	* Returns the title of this s c o r m content.
	*
	* @return the title of this s c o r m content
	*/
	public java.lang.String getTitle() {
		return _scormContent.getTitle();
	}

	/**
	* Sets the title of this s c o r m content.
	*
	* @param title the title of this s c o r m content
	*/
	public void setTitle(java.lang.String title) {
		_scormContent.setTitle(title);
	}

	/**
	* Returns the description of this s c o r m content.
	*
	* @return the description of this s c o r m content
	*/
	public java.lang.String getDescription() {
		return _scormContent.getDescription();
	}

	/**
	* Sets the description of this s c o r m content.
	*
	* @param description the description of this s c o r m content
	*/
	public void setDescription(java.lang.String description) {
		_scormContent.setDescription(description);
	}

	/**
	* Returns the index of this s c o r m content.
	*
	* @return the index of this s c o r m content
	*/
	public java.lang.String getIndex() {
		return _scormContent.getIndex();
	}

	/**
	* Sets the index of this s c o r m content.
	*
	* @param index the index of this s c o r m content
	*/
	public void setIndex(java.lang.String index) {
		_scormContent.setIndex(index);
	}

	/**
	* Returns the ciphered of this s c o r m content.
	*
	* @return the ciphered of this s c o r m content
	*/
	public boolean getCiphered() {
		return _scormContent.getCiphered();
	}

	/**
	* Returns <code>true</code> if this s c o r m content is ciphered.
	*
	* @return <code>true</code> if this s c o r m content is ciphered; <code>false</code> otherwise
	*/
	public boolean isCiphered() {
		return _scormContent.isCiphered();
	}

	/**
	* Sets whether this s c o r m content is ciphered.
	*
	* @param ciphered the ciphered of this s c o r m content
	*/
	public void setCiphered(boolean ciphered) {
		_scormContent.setCiphered(ciphered);
	}

	/**
	* @deprecated Renamed to {@link #isApproved()}
	*/
	public boolean getApproved() {
		return _scormContent.getApproved();
	}

	/**
	* Returns <code>true</code> if this s c o r m content is approved.
	*
	* @return <code>true</code> if this s c o r m content is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _scormContent.isApproved();
	}

	/**
	* Returns <code>true</code> if this s c o r m content is denied.
	*
	* @return <code>true</code> if this s c o r m content is denied; <code>false</code> otherwise
	*/
	public boolean isDenied() {
		return _scormContent.isDenied();
	}

	/**
	* Returns <code>true</code> if this s c o r m content is a draft.
	*
	* @return <code>true</code> if this s c o r m content is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _scormContent.isDraft();
	}

	/**
	* Returns <code>true</code> if this s c o r m content is expired.
	*
	* @return <code>true</code> if this s c o r m content is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _scormContent.isExpired();
	}

	/**
	* Returns <code>true</code> if this s c o r m content is inactive.
	*
	* @return <code>true</code> if this s c o r m content is inactive; <code>false</code> otherwise
	*/
	public boolean isInactive() {
		return _scormContent.isInactive();
	}

	/**
	* Returns <code>true</code> if this s c o r m content is incomplete.
	*
	* @return <code>true</code> if this s c o r m content is incomplete; <code>false</code> otherwise
	*/
	public boolean isIncomplete() {
		return _scormContent.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this s c o r m content is pending.
	*
	* @return <code>true</code> if this s c o r m content is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _scormContent.isPending();
	}

	/**
	* Returns <code>true</code> if this s c o r m content is scheduled.
	*
	* @return <code>true</code> if this s c o r m content is scheduled; <code>false</code> otherwise
	*/
	public boolean isScheduled() {
		return _scormContent.isScheduled();
	}

	public boolean isNew() {
		return _scormContent.isNew();
	}

	public void setNew(boolean n) {
		_scormContent.setNew(n);
	}

	public boolean isCachedModel() {
		return _scormContent.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_scormContent.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _scormContent.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _scormContent.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_scormContent.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _scormContent.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_scormContent.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SCORMContentWrapper((SCORMContent)_scormContent.clone());
	}

	public int compareTo(com.liferay.lms.model.SCORMContent scormContent) {
		return _scormContent.compareTo(scormContent);
	}

	@Override
	public int hashCode() {
		return _scormContent.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.SCORMContent> toCacheModel() {
		return _scormContent.toCacheModel();
	}

	public com.liferay.lms.model.SCORMContent toEscapedModel() {
		return new SCORMContentWrapper(_scormContent.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _scormContent.toString();
	}

	public java.lang.String toXmlString() {
		return _scormContent.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_scormContent.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public SCORMContent getWrappedSCORMContent() {
		return _scormContent;
	}

	public SCORMContent getWrappedModel() {
		return _scormContent;
	}

	public void resetOriginalValues() {
		_scormContent.resetOriginalValues();
	}

	private SCORMContent _scormContent;
}