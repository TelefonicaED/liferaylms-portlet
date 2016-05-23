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
 * This class is a wrapper for {@link LearningActivityResult}.
 * </p>
 *
 * @author    TLS
 * @see       LearningActivityResult
 * @generated
 */
public class LearningActivityResultWrapper implements LearningActivityResult,
	ModelWrapper<LearningActivityResult> {
	public LearningActivityResultWrapper(
		LearningActivityResult learningActivityResult) {
		_learningActivityResult = learningActivityResult;
	}

	public Class<?> getModelClass() {
		return LearningActivityResult.class;
	}

	public String getModelClassName() {
		return LearningActivityResult.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("larId", getLarId());
		attributes.put("actId", getActId());
		attributes.put("userId", getUserId());
		attributes.put("result", getResult());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("latId", getLatId());
		attributes.put("comments", getComments());
		attributes.put("passed", getPassed());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long larId = (Long)attributes.get("larId");

		if (larId != null) {
			setLarId(larId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long result = (Long)attributes.get("result");

		if (result != null) {
			setResult(result);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		Long latId = (Long)attributes.get("latId");

		if (latId != null) {
			setLatId(latId);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}

		Boolean passed = (Boolean)attributes.get("passed");

		if (passed != null) {
			setPassed(passed);
		}
	}

	/**
	* Returns the primary key of this learning activity result.
	*
	* @return the primary key of this learning activity result
	*/
	public long getPrimaryKey() {
		return _learningActivityResult.getPrimaryKey();
	}

	/**
	* Sets the primary key of this learning activity result.
	*
	* @param primaryKey the primary key of this learning activity result
	*/
	public void setPrimaryKey(long primaryKey) {
		_learningActivityResult.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this learning activity result.
	*
	* @return the uuid of this learning activity result
	*/
	public java.lang.String getUuid() {
		return _learningActivityResult.getUuid();
	}

	/**
	* Sets the uuid of this learning activity result.
	*
	* @param uuid the uuid of this learning activity result
	*/
	public void setUuid(java.lang.String uuid) {
		_learningActivityResult.setUuid(uuid);
	}

	/**
	* Returns the lar ID of this learning activity result.
	*
	* @return the lar ID of this learning activity result
	*/
	public long getLarId() {
		return _learningActivityResult.getLarId();
	}

	/**
	* Sets the lar ID of this learning activity result.
	*
	* @param larId the lar ID of this learning activity result
	*/
	public void setLarId(long larId) {
		_learningActivityResult.setLarId(larId);
	}

	/**
	* Returns the act ID of this learning activity result.
	*
	* @return the act ID of this learning activity result
	*/
	public long getActId() {
		return _learningActivityResult.getActId();
	}

	/**
	* Sets the act ID of this learning activity result.
	*
	* @param actId the act ID of this learning activity result
	*/
	public void setActId(long actId) {
		_learningActivityResult.setActId(actId);
	}

	/**
	* Returns the user ID of this learning activity result.
	*
	* @return the user ID of this learning activity result
	*/
	public long getUserId() {
		return _learningActivityResult.getUserId();
	}

	/**
	* Sets the user ID of this learning activity result.
	*
	* @param userId the user ID of this learning activity result
	*/
	public void setUserId(long userId) {
		_learningActivityResult.setUserId(userId);
	}

	/**
	* Returns the user uuid of this learning activity result.
	*
	* @return the user uuid of this learning activity result
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityResult.getUserUuid();
	}

	/**
	* Sets the user uuid of this learning activity result.
	*
	* @param userUuid the user uuid of this learning activity result
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_learningActivityResult.setUserUuid(userUuid);
	}

	/**
	* Returns the result of this learning activity result.
	*
	* @return the result of this learning activity result
	*/
	public long getResult() {
		return _learningActivityResult.getResult();
	}

	/**
	* Sets the result of this learning activity result.
	*
	* @param result the result of this learning activity result
	*/
	public void setResult(long result) {
		_learningActivityResult.setResult(result);
	}

	/**
	* Returns the start date of this learning activity result.
	*
	* @return the start date of this learning activity result
	*/
	public java.util.Date getStartDate() {
		return _learningActivityResult.getStartDate();
	}

	/**
	* Sets the start date of this learning activity result.
	*
	* @param startDate the start date of this learning activity result
	*/
	public void setStartDate(java.util.Date startDate) {
		_learningActivityResult.setStartDate(startDate);
	}

	/**
	* Returns the end date of this learning activity result.
	*
	* @return the end date of this learning activity result
	*/
	public java.util.Date getEndDate() {
		return _learningActivityResult.getEndDate();
	}

	/**
	* Sets the end date of this learning activity result.
	*
	* @param endDate the end date of this learning activity result
	*/
	public void setEndDate(java.util.Date endDate) {
		_learningActivityResult.setEndDate(endDate);
	}

	/**
	* Returns the lat ID of this learning activity result.
	*
	* @return the lat ID of this learning activity result
	*/
	public long getLatId() {
		return _learningActivityResult.getLatId();
	}

	/**
	* Sets the lat ID of this learning activity result.
	*
	* @param latId the lat ID of this learning activity result
	*/
	public void setLatId(long latId) {
		_learningActivityResult.setLatId(latId);
	}

	/**
	* Returns the comments of this learning activity result.
	*
	* @return the comments of this learning activity result
	*/
	public java.lang.String getComments() {
		return _learningActivityResult.getComments();
	}

	/**
	* Sets the comments of this learning activity result.
	*
	* @param comments the comments of this learning activity result
	*/
	public void setComments(java.lang.String comments) {
		_learningActivityResult.setComments(comments);
	}

	/**
	* Returns the passed of this learning activity result.
	*
	* @return the passed of this learning activity result
	*/
	public boolean getPassed() {
		return _learningActivityResult.getPassed();
	}

	/**
	* Returns <code>true</code> if this learning activity result is passed.
	*
	* @return <code>true</code> if this learning activity result is passed; <code>false</code> otherwise
	*/
	public boolean isPassed() {
		return _learningActivityResult.isPassed();
	}

	/**
	* Sets whether this learning activity result is passed.
	*
	* @param passed the passed of this learning activity result
	*/
	public void setPassed(boolean passed) {
		_learningActivityResult.setPassed(passed);
	}

	public boolean isNew() {
		return _learningActivityResult.isNew();
	}

	public void setNew(boolean n) {
		_learningActivityResult.setNew(n);
	}

	public boolean isCachedModel() {
		return _learningActivityResult.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_learningActivityResult.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _learningActivityResult.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _learningActivityResult.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_learningActivityResult.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _learningActivityResult.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_learningActivityResult.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new LearningActivityResultWrapper((LearningActivityResult)_learningActivityResult.clone());
	}

	public int compareTo(
		com.liferay.lms.model.LearningActivityResult learningActivityResult) {
		return _learningActivityResult.compareTo(learningActivityResult);
	}

	@Override
	public int hashCode() {
		return _learningActivityResult.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.LearningActivityResult> toCacheModel() {
		return _learningActivityResult.toCacheModel();
	}

	public com.liferay.lms.model.LearningActivityResult toEscapedModel() {
		return new LearningActivityResultWrapper(_learningActivityResult.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _learningActivityResult.toString();
	}

	public java.lang.String toXmlString() {
		return _learningActivityResult.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_learningActivityResult.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public LearningActivityResult getWrappedLearningActivityResult() {
		return _learningActivityResult;
	}

	public LearningActivityResult getWrappedModel() {
		return _learningActivityResult;
	}

	public void resetOriginalValues() {
		_learningActivityResult.resetOriginalValues();
	}

	private LearningActivityResult _learningActivityResult;
}