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
 * This class is a wrapper for {@link CheckP2pMailing}.
 * </p>
 *
 * @author    TLS
 * @see       CheckP2pMailing
 * @generated
 */
public class CheckP2pMailingWrapper implements CheckP2pMailing,
	ModelWrapper<CheckP2pMailing> {
	public CheckP2pMailingWrapper(CheckP2pMailing checkP2pMailing) {
		_checkP2pMailing = checkP2pMailing;
	}

	public Class<?> getModelClass() {
		return CheckP2pMailing.class;
	}

	public String getModelClassName() {
		return CheckP2pMailing.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("checkP2pId", getCheckP2pId());
		attributes.put("actId", getActId());
		attributes.put("date", getDate());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long checkP2pId = (Long)attributes.get("checkP2pId");

		if (checkP2pId != null) {
			setCheckP2pId(checkP2pId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Date date = (Date)attributes.get("date");

		if (date != null) {
			setDate(date);
		}
	}

	/**
	* Returns the primary key of this check p2p mailing.
	*
	* @return the primary key of this check p2p mailing
	*/
	public long getPrimaryKey() {
		return _checkP2pMailing.getPrimaryKey();
	}

	/**
	* Sets the primary key of this check p2p mailing.
	*
	* @param primaryKey the primary key of this check p2p mailing
	*/
	public void setPrimaryKey(long primaryKey) {
		_checkP2pMailing.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the check p2p ID of this check p2p mailing.
	*
	* @return the check p2p ID of this check p2p mailing
	*/
	public long getCheckP2pId() {
		return _checkP2pMailing.getCheckP2pId();
	}

	/**
	* Sets the check p2p ID of this check p2p mailing.
	*
	* @param checkP2pId the check p2p ID of this check p2p mailing
	*/
	public void setCheckP2pId(long checkP2pId) {
		_checkP2pMailing.setCheckP2pId(checkP2pId);
	}

	/**
	* Returns the act ID of this check p2p mailing.
	*
	* @return the act ID of this check p2p mailing
	*/
	public long getActId() {
		return _checkP2pMailing.getActId();
	}

	/**
	* Sets the act ID of this check p2p mailing.
	*
	* @param actId the act ID of this check p2p mailing
	*/
	public void setActId(long actId) {
		_checkP2pMailing.setActId(actId);
	}

	/**
	* Returns the date of this check p2p mailing.
	*
	* @return the date of this check p2p mailing
	*/
	public java.util.Date getDate() {
		return _checkP2pMailing.getDate();
	}

	/**
	* Sets the date of this check p2p mailing.
	*
	* @param date the date of this check p2p mailing
	*/
	public void setDate(java.util.Date date) {
		_checkP2pMailing.setDate(date);
	}

	public boolean isNew() {
		return _checkP2pMailing.isNew();
	}

	public void setNew(boolean n) {
		_checkP2pMailing.setNew(n);
	}

	public boolean isCachedModel() {
		return _checkP2pMailing.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_checkP2pMailing.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _checkP2pMailing.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _checkP2pMailing.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_checkP2pMailing.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _checkP2pMailing.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_checkP2pMailing.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new CheckP2pMailingWrapper((CheckP2pMailing)_checkP2pMailing.clone());
	}

	public int compareTo(com.liferay.lms.model.CheckP2pMailing checkP2pMailing) {
		return _checkP2pMailing.compareTo(checkP2pMailing);
	}

	@Override
	public int hashCode() {
		return _checkP2pMailing.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.CheckP2pMailing> toCacheModel() {
		return _checkP2pMailing.toCacheModel();
	}

	public com.liferay.lms.model.CheckP2pMailing toEscapedModel() {
		return new CheckP2pMailingWrapper(_checkP2pMailing.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _checkP2pMailing.toString();
	}

	public java.lang.String toXmlString() {
		return _checkP2pMailing.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_checkP2pMailing.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public CheckP2pMailing getWrappedCheckP2pMailing() {
		return _checkP2pMailing;
	}

	public CheckP2pMailing getWrappedModel() {
		return _checkP2pMailing;
	}

	public void resetOriginalValues() {
		_checkP2pMailing.resetOriginalValues();
	}

	private CheckP2pMailing _checkP2pMailing;
}