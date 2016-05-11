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
 * This class is a wrapper for {@link ModuleResult}.
 * </p>
 *
 * @author    TLS
 * @see       ModuleResult
 * @generated
 */
public class ModuleResultWrapper implements ModuleResult,
	ModelWrapper<ModuleResult> {
	public ModuleResultWrapper(ModuleResult moduleResult) {
		_moduleResult = moduleResult;
	}

	public Class<?> getModelClass() {
		return ModuleResult.class;
	}

	public String getModelClassName() {
		return ModuleResult.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("moduleId", getModuleId());
		attributes.put("result", getResult());
		attributes.put("comments", getComments());
		attributes.put("userId", getUserId());
		attributes.put("startDate", getStartDate());
		attributes.put("passed", getPassed());
		attributes.put("mrId", getMrId());
		attributes.put("passedDate", getPassedDate());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long moduleId = (Long)attributes.get("moduleId");

		if (moduleId != null) {
			setModuleId(moduleId);
		}

		Long result = (Long)attributes.get("result");

		if (result != null) {
			setResult(result);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Boolean passed = (Boolean)attributes.get("passed");

		if (passed != null) {
			setPassed(passed);
		}

		Long mrId = (Long)attributes.get("mrId");

		if (mrId != null) {
			setMrId(mrId);
		}

		Date passedDate = (Date)attributes.get("passedDate");

		if (passedDate != null) {
			setPassedDate(passedDate);
		}
	}

	/**
	* Returns the primary key of this module result.
	*
	* @return the primary key of this module result
	*/
	public long getPrimaryKey() {
		return _moduleResult.getPrimaryKey();
	}

	/**
	* Sets the primary key of this module result.
	*
	* @param primaryKey the primary key of this module result
	*/
	public void setPrimaryKey(long primaryKey) {
		_moduleResult.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the module ID of this module result.
	*
	* @return the module ID of this module result
	*/
	public long getModuleId() {
		return _moduleResult.getModuleId();
	}

	/**
	* Sets the module ID of this module result.
	*
	* @param moduleId the module ID of this module result
	*/
	public void setModuleId(long moduleId) {
		_moduleResult.setModuleId(moduleId);
	}

	/**
	* Returns the result of this module result.
	*
	* @return the result of this module result
	*/
	public long getResult() {
		return _moduleResult.getResult();
	}

	/**
	* Sets the result of this module result.
	*
	* @param result the result of this module result
	*/
	public void setResult(long result) {
		_moduleResult.setResult(result);
	}

	/**
	* Returns the comments of this module result.
	*
	* @return the comments of this module result
	*/
	public java.lang.String getComments() {
		return _moduleResult.getComments();
	}

	/**
	* Sets the comments of this module result.
	*
	* @param comments the comments of this module result
	*/
	public void setComments(java.lang.String comments) {
		_moduleResult.setComments(comments);
	}

	/**
	* Returns the user ID of this module result.
	*
	* @return the user ID of this module result
	*/
	public long getUserId() {
		return _moduleResult.getUserId();
	}

	/**
	* Sets the user ID of this module result.
	*
	* @param userId the user ID of this module result
	*/
	public void setUserId(long userId) {
		_moduleResult.setUserId(userId);
	}

	/**
	* Returns the user uuid of this module result.
	*
	* @return the user uuid of this module result
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moduleResult.getUserUuid();
	}

	/**
	* Sets the user uuid of this module result.
	*
	* @param userUuid the user uuid of this module result
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_moduleResult.setUserUuid(userUuid);
	}

	/**
	* Returns the start date of this module result.
	*
	* @return the start date of this module result
	*/
	public java.util.Date getStartDate() {
		return _moduleResult.getStartDate();
	}

	/**
	* Sets the start date of this module result.
	*
	* @param startDate the start date of this module result
	*/
	public void setStartDate(java.util.Date startDate) {
		_moduleResult.setStartDate(startDate);
	}

	/**
	* Returns the passed of this module result.
	*
	* @return the passed of this module result
	*/
	public boolean getPassed() {
		return _moduleResult.getPassed();
	}

	/**
	* Returns <code>true</code> if this module result is passed.
	*
	* @return <code>true</code> if this module result is passed; <code>false</code> otherwise
	*/
	public boolean isPassed() {
		return _moduleResult.isPassed();
	}

	/**
	* Sets whether this module result is passed.
	*
	* @param passed the passed of this module result
	*/
	public void setPassed(boolean passed) {
		_moduleResult.setPassed(passed);
	}

	/**
	* Returns the mr ID of this module result.
	*
	* @return the mr ID of this module result
	*/
	public long getMrId() {
		return _moduleResult.getMrId();
	}

	/**
	* Sets the mr ID of this module result.
	*
	* @param mrId the mr ID of this module result
	*/
	public void setMrId(long mrId) {
		_moduleResult.setMrId(mrId);
	}

	/**
	* Returns the passed date of this module result.
	*
	* @return the passed date of this module result
	*/
	public java.util.Date getPassedDate() {
		return _moduleResult.getPassedDate();
	}

	/**
	* Sets the passed date of this module result.
	*
	* @param passedDate the passed date of this module result
	*/
	public void setPassedDate(java.util.Date passedDate) {
		_moduleResult.setPassedDate(passedDate);
	}

	public boolean isNew() {
		return _moduleResult.isNew();
	}

	public void setNew(boolean n) {
		_moduleResult.setNew(n);
	}

	public boolean isCachedModel() {
		return _moduleResult.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_moduleResult.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _moduleResult.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _moduleResult.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_moduleResult.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _moduleResult.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_moduleResult.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ModuleResultWrapper((ModuleResult)_moduleResult.clone());
	}

	public int compareTo(com.liferay.lms.model.ModuleResult moduleResult) {
		return _moduleResult.compareTo(moduleResult);
	}

	@Override
	public int hashCode() {
		return _moduleResult.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.ModuleResult> toCacheModel() {
		return _moduleResult.toCacheModel();
	}

	public com.liferay.lms.model.ModuleResult toEscapedModel() {
		return new ModuleResultWrapper(_moduleResult.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _moduleResult.toString();
	}

	public java.lang.String toXmlString() {
		return _moduleResult.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_moduleResult.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public ModuleResult getWrappedModuleResult() {
		return _moduleResult;
	}

	public ModuleResult getWrappedModel() {
		return _moduleResult;
	}

	public void resetOriginalValues() {
		_moduleResult.resetOriginalValues();
	}

	private ModuleResult _moduleResult;
}