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
 * This class is a wrapper for {@link P2pActivityCorrections}.
 * </p>
 *
 * @author    TLS
 * @see       P2pActivityCorrections
 * @generated
 */
public class P2pActivityCorrectionsWrapper implements P2pActivityCorrections,
	ModelWrapper<P2pActivityCorrections> {
	public P2pActivityCorrectionsWrapper(
		P2pActivityCorrections p2pActivityCorrections) {
		_p2pActivityCorrections = p2pActivityCorrections;
	}

	public Class<?> getModelClass() {
		return P2pActivityCorrections.class;
	}

	public String getModelClassName() {
		return P2pActivityCorrections.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("p2pActivityCorrectionsId", getP2pActivityCorrectionsId());
		attributes.put("p2pActivityId", getP2pActivityId());
		attributes.put("userId", getUserId());
		attributes.put("actId", getActId());
		attributes.put("description", getDescription());
		attributes.put("date", getDate());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("result", getResult());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long p2pActivityCorrectionsId = (Long)attributes.get(
				"p2pActivityCorrectionsId");

		if (p2pActivityCorrectionsId != null) {
			setP2pActivityCorrectionsId(p2pActivityCorrectionsId);
		}

		Long p2pActivityId = (Long)attributes.get("p2pActivityId");

		if (p2pActivityId != null) {
			setP2pActivityId(p2pActivityId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date date = (Date)attributes.get("date");

		if (date != null) {
			setDate(date);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Long result = (Long)attributes.get("result");

		if (result != null) {
			setResult(result);
		}
	}

	/**
	* Returns the primary key of this p2p activity corrections.
	*
	* @return the primary key of this p2p activity corrections
	*/
	public long getPrimaryKey() {
		return _p2pActivityCorrections.getPrimaryKey();
	}

	/**
	* Sets the primary key of this p2p activity corrections.
	*
	* @param primaryKey the primary key of this p2p activity corrections
	*/
	public void setPrimaryKey(long primaryKey) {
		_p2pActivityCorrections.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this p2p activity corrections.
	*
	* @return the uuid of this p2p activity corrections
	*/
	public java.lang.String getUuid() {
		return _p2pActivityCorrections.getUuid();
	}

	/**
	* Sets the uuid of this p2p activity corrections.
	*
	* @param uuid the uuid of this p2p activity corrections
	*/
	public void setUuid(java.lang.String uuid) {
		_p2pActivityCorrections.setUuid(uuid);
	}

	/**
	* Returns the p2p activity corrections ID of this p2p activity corrections.
	*
	* @return the p2p activity corrections ID of this p2p activity corrections
	*/
	public long getP2pActivityCorrectionsId() {
		return _p2pActivityCorrections.getP2pActivityCorrectionsId();
	}

	/**
	* Sets the p2p activity corrections ID of this p2p activity corrections.
	*
	* @param p2pActivityCorrectionsId the p2p activity corrections ID of this p2p activity corrections
	*/
	public void setP2pActivityCorrectionsId(long p2pActivityCorrectionsId) {
		_p2pActivityCorrections.setP2pActivityCorrectionsId(p2pActivityCorrectionsId);
	}

	/**
	* Returns the p2p activity ID of this p2p activity corrections.
	*
	* @return the p2p activity ID of this p2p activity corrections
	*/
	public long getP2pActivityId() {
		return _p2pActivityCorrections.getP2pActivityId();
	}

	/**
	* Sets the p2p activity ID of this p2p activity corrections.
	*
	* @param p2pActivityId the p2p activity ID of this p2p activity corrections
	*/
	public void setP2pActivityId(long p2pActivityId) {
		_p2pActivityCorrections.setP2pActivityId(p2pActivityId);
	}

	/**
	* Returns the user ID of this p2p activity corrections.
	*
	* @return the user ID of this p2p activity corrections
	*/
	public long getUserId() {
		return _p2pActivityCorrections.getUserId();
	}

	/**
	* Sets the user ID of this p2p activity corrections.
	*
	* @param userId the user ID of this p2p activity corrections
	*/
	public void setUserId(long userId) {
		_p2pActivityCorrections.setUserId(userId);
	}

	/**
	* Returns the user uuid of this p2p activity corrections.
	*
	* @return the user uuid of this p2p activity corrections
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityCorrections.getUserUuid();
	}

	/**
	* Sets the user uuid of this p2p activity corrections.
	*
	* @param userUuid the user uuid of this p2p activity corrections
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_p2pActivityCorrections.setUserUuid(userUuid);
	}

	/**
	* Returns the act ID of this p2p activity corrections.
	*
	* @return the act ID of this p2p activity corrections
	*/
	public long getActId() {
		return _p2pActivityCorrections.getActId();
	}

	/**
	* Sets the act ID of this p2p activity corrections.
	*
	* @param actId the act ID of this p2p activity corrections
	*/
	public void setActId(long actId) {
		_p2pActivityCorrections.setActId(actId);
	}

	/**
	* Returns the description of this p2p activity corrections.
	*
	* @return the description of this p2p activity corrections
	*/
	public java.lang.String getDescription() {
		return _p2pActivityCorrections.getDescription();
	}

	/**
	* Sets the description of this p2p activity corrections.
	*
	* @param description the description of this p2p activity corrections
	*/
	public void setDescription(java.lang.String description) {
		_p2pActivityCorrections.setDescription(description);
	}

	/**
	* Returns the date of this p2p activity corrections.
	*
	* @return the date of this p2p activity corrections
	*/
	public java.util.Date getDate() {
		return _p2pActivityCorrections.getDate();
	}

	/**
	* Sets the date of this p2p activity corrections.
	*
	* @param date the date of this p2p activity corrections
	*/
	public void setDate(java.util.Date date) {
		_p2pActivityCorrections.setDate(date);
	}

	/**
	* Returns the file entry ID of this p2p activity corrections.
	*
	* @return the file entry ID of this p2p activity corrections
	*/
	public long getFileEntryId() {
		return _p2pActivityCorrections.getFileEntryId();
	}

	/**
	* Sets the file entry ID of this p2p activity corrections.
	*
	* @param fileEntryId the file entry ID of this p2p activity corrections
	*/
	public void setFileEntryId(long fileEntryId) {
		_p2pActivityCorrections.setFileEntryId(fileEntryId);
	}

	/**
	* Returns the result of this p2p activity corrections.
	*
	* @return the result of this p2p activity corrections
	*/
	public long getResult() {
		return _p2pActivityCorrections.getResult();
	}

	/**
	* Sets the result of this p2p activity corrections.
	*
	* @param result the result of this p2p activity corrections
	*/
	public void setResult(long result) {
		_p2pActivityCorrections.setResult(result);
	}

	public boolean isNew() {
		return _p2pActivityCorrections.isNew();
	}

	public void setNew(boolean n) {
		_p2pActivityCorrections.setNew(n);
	}

	public boolean isCachedModel() {
		return _p2pActivityCorrections.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_p2pActivityCorrections.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _p2pActivityCorrections.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _p2pActivityCorrections.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_p2pActivityCorrections.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _p2pActivityCorrections.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_p2pActivityCorrections.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new P2pActivityCorrectionsWrapper((P2pActivityCorrections)_p2pActivityCorrections.clone());
	}

	public int compareTo(
		com.liferay.lms.model.P2pActivityCorrections p2pActivityCorrections) {
		return _p2pActivityCorrections.compareTo(p2pActivityCorrections);
	}

	@Override
	public int hashCode() {
		return _p2pActivityCorrections.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.P2pActivityCorrections> toCacheModel() {
		return _p2pActivityCorrections.toCacheModel();
	}

	public com.liferay.lms.model.P2pActivityCorrections toEscapedModel() {
		return new P2pActivityCorrectionsWrapper(_p2pActivityCorrections.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _p2pActivityCorrections.toString();
	}

	public java.lang.String toXmlString() {
		return _p2pActivityCorrections.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_p2pActivityCorrections.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public P2pActivityCorrections getWrappedP2pActivityCorrections() {
		return _p2pActivityCorrections;
	}

	public P2pActivityCorrections getWrappedModel() {
		return _p2pActivityCorrections;
	}

	public void resetOriginalValues() {
		_p2pActivityCorrections.resetOriginalValues();
	}

	private P2pActivityCorrections _p2pActivityCorrections;
}