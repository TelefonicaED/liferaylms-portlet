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
 * This class is a wrapper for {@link P2pActivity}.
 * </p>
 *
 * @author    TLS
 * @see       P2pActivity
 * @generated
 */
public class P2pActivityWrapper implements P2pActivity,
	ModelWrapper<P2pActivity> {
	public P2pActivityWrapper(P2pActivity p2pActivity) {
		_p2pActivity = p2pActivity;
	}

	public Class<?> getModelClass() {
		return P2pActivity.class;
	}

	public String getModelClassName() {
		return P2pActivity.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("p2pActivityId", getP2pActivityId());
		attributes.put("actId", getActId());
		attributes.put("userId", getUserId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("countCorrections", getCountCorrections());
		attributes.put("description", getDescription());
		attributes.put("date", getDate());
		attributes.put("asignationsCompleted", getAsignationsCompleted());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long p2pActivityId = (Long)attributes.get("p2pActivityId");

		if (p2pActivityId != null) {
			setP2pActivityId(p2pActivityId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Long countCorrections = (Long)attributes.get("countCorrections");

		if (countCorrections != null) {
			setCountCorrections(countCorrections);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date date = (Date)attributes.get("date");

		if (date != null) {
			setDate(date);
		}

		Boolean asignationsCompleted = (Boolean)attributes.get(
				"asignationsCompleted");

		if (asignationsCompleted != null) {
			setAsignationsCompleted(asignationsCompleted);
		}
	}

	/**
	* Returns the primary key of this p2p activity.
	*
	* @return the primary key of this p2p activity
	*/
	public long getPrimaryKey() {
		return _p2pActivity.getPrimaryKey();
	}

	/**
	* Sets the primary key of this p2p activity.
	*
	* @param primaryKey the primary key of this p2p activity
	*/
	public void setPrimaryKey(long primaryKey) {
		_p2pActivity.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this p2p activity.
	*
	* @return the uuid of this p2p activity
	*/
	public java.lang.String getUuid() {
		return _p2pActivity.getUuid();
	}

	/**
	* Sets the uuid of this p2p activity.
	*
	* @param uuid the uuid of this p2p activity
	*/
	public void setUuid(java.lang.String uuid) {
		_p2pActivity.setUuid(uuid);
	}

	/**
	* Returns the p2p activity ID of this p2p activity.
	*
	* @return the p2p activity ID of this p2p activity
	*/
	public long getP2pActivityId() {
		return _p2pActivity.getP2pActivityId();
	}

	/**
	* Sets the p2p activity ID of this p2p activity.
	*
	* @param p2pActivityId the p2p activity ID of this p2p activity
	*/
	public void setP2pActivityId(long p2pActivityId) {
		_p2pActivity.setP2pActivityId(p2pActivityId);
	}

	/**
	* Returns the act ID of this p2p activity.
	*
	* @return the act ID of this p2p activity
	*/
	public long getActId() {
		return _p2pActivity.getActId();
	}

	/**
	* Sets the act ID of this p2p activity.
	*
	* @param actId the act ID of this p2p activity
	*/
	public void setActId(long actId) {
		_p2pActivity.setActId(actId);
	}

	/**
	* Returns the user ID of this p2p activity.
	*
	* @return the user ID of this p2p activity
	*/
	public long getUserId() {
		return _p2pActivity.getUserId();
	}

	/**
	* Sets the user ID of this p2p activity.
	*
	* @param userId the user ID of this p2p activity
	*/
	public void setUserId(long userId) {
		_p2pActivity.setUserId(userId);
	}

	/**
	* Returns the user uuid of this p2p activity.
	*
	* @return the user uuid of this p2p activity
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivity.getUserUuid();
	}

	/**
	* Sets the user uuid of this p2p activity.
	*
	* @param userUuid the user uuid of this p2p activity
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_p2pActivity.setUserUuid(userUuid);
	}

	/**
	* Returns the file entry ID of this p2p activity.
	*
	* @return the file entry ID of this p2p activity
	*/
	public long getFileEntryId() {
		return _p2pActivity.getFileEntryId();
	}

	/**
	* Sets the file entry ID of this p2p activity.
	*
	* @param fileEntryId the file entry ID of this p2p activity
	*/
	public void setFileEntryId(long fileEntryId) {
		_p2pActivity.setFileEntryId(fileEntryId);
	}

	/**
	* Returns the count corrections of this p2p activity.
	*
	* @return the count corrections of this p2p activity
	*/
	public long getCountCorrections() {
		return _p2pActivity.getCountCorrections();
	}

	/**
	* Sets the count corrections of this p2p activity.
	*
	* @param countCorrections the count corrections of this p2p activity
	*/
	public void setCountCorrections(long countCorrections) {
		_p2pActivity.setCountCorrections(countCorrections);
	}

	/**
	* Returns the description of this p2p activity.
	*
	* @return the description of this p2p activity
	*/
	public java.lang.String getDescription() {
		return _p2pActivity.getDescription();
	}

	/**
	* Sets the description of this p2p activity.
	*
	* @param description the description of this p2p activity
	*/
	public void setDescription(java.lang.String description) {
		_p2pActivity.setDescription(description);
	}

	/**
	* Returns the date of this p2p activity.
	*
	* @return the date of this p2p activity
	*/
	public java.util.Date getDate() {
		return _p2pActivity.getDate();
	}

	/**
	* Sets the date of this p2p activity.
	*
	* @param date the date of this p2p activity
	*/
	public void setDate(java.util.Date date) {
		_p2pActivity.setDate(date);
	}

	/**
	* Returns the asignations completed of this p2p activity.
	*
	* @return the asignations completed of this p2p activity
	*/
	public boolean getAsignationsCompleted() {
		return _p2pActivity.getAsignationsCompleted();
	}

	/**
	* Returns <code>true</code> if this p2p activity is asignations completed.
	*
	* @return <code>true</code> if this p2p activity is asignations completed; <code>false</code> otherwise
	*/
	public boolean isAsignationsCompleted() {
		return _p2pActivity.isAsignationsCompleted();
	}

	/**
	* Sets whether this p2p activity is asignations completed.
	*
	* @param asignationsCompleted the asignations completed of this p2p activity
	*/
	public void setAsignationsCompleted(boolean asignationsCompleted) {
		_p2pActivity.setAsignationsCompleted(asignationsCompleted);
	}

	public boolean isNew() {
		return _p2pActivity.isNew();
	}

	public void setNew(boolean n) {
		_p2pActivity.setNew(n);
	}

	public boolean isCachedModel() {
		return _p2pActivity.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_p2pActivity.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _p2pActivity.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _p2pActivity.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_p2pActivity.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _p2pActivity.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_p2pActivity.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new P2pActivityWrapper((P2pActivity)_p2pActivity.clone());
	}

	public int compareTo(com.liferay.lms.model.P2pActivity p2pActivity) {
		return _p2pActivity.compareTo(p2pActivity);
	}

	@Override
	public int hashCode() {
		return _p2pActivity.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.P2pActivity> toCacheModel() {
		return _p2pActivity.toCacheModel();
	}

	public com.liferay.lms.model.P2pActivity toEscapedModel() {
		return new P2pActivityWrapper(_p2pActivity.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _p2pActivity.toString();
	}

	public java.lang.String toXmlString() {
		return _p2pActivity.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_p2pActivity.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public P2pActivity getWrappedP2pActivity() {
		return _p2pActivity;
	}

	public P2pActivity getWrappedModel() {
		return _p2pActivity;
	}

	public void resetOriginalValues() {
		_p2pActivity.resetOriginalValues();
	}

	private P2pActivity _p2pActivity;
}