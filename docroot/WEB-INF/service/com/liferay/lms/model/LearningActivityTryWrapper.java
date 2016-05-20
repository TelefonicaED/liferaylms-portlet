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
 * This class is a wrapper for {@link LearningActivityTry}.
 * </p>
 *
 * @author    TLS
 * @see       LearningActivityTry
 * @generated
 */
public class LearningActivityTryWrapper implements LearningActivityTry,
	ModelWrapper<LearningActivityTry> {
	public LearningActivityTryWrapper(LearningActivityTry learningActivityTry) {
		_learningActivityTry = learningActivityTry;
	}

	public Class<?> getModelClass() {
		return LearningActivityTry.class;
	}

	public String getModelClassName() {
		return LearningActivityTry.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("latId", getLatId());
		attributes.put("actId", getActId());
		attributes.put("userId", getUserId());
		attributes.put("startDate", getStartDate());
		attributes.put("result", getResult());
		attributes.put("endDate", getEndDate());
		attributes.put("tryData", getTryData());
		attributes.put("tryResultData", getTryResultData());
		attributes.put("comments", getComments());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long latId = (Long)attributes.get("latId");

		if (latId != null) {
			setLatId(latId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Long result = (Long)attributes.get("result");

		if (result != null) {
			setResult(result);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		String tryData = (String)attributes.get("tryData");

		if (tryData != null) {
			setTryData(tryData);
		}

		String tryResultData = (String)attributes.get("tryResultData");

		if (tryResultData != null) {
			setTryResultData(tryResultData);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}
	}

	/**
	* Returns the primary key of this learning activity try.
	*
	* @return the primary key of this learning activity try
	*/
	public long getPrimaryKey() {
		return _learningActivityTry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this learning activity try.
	*
	* @param primaryKey the primary key of this learning activity try
	*/
	public void setPrimaryKey(long primaryKey) {
		_learningActivityTry.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this learning activity try.
	*
	* @return the uuid of this learning activity try
	*/
	public java.lang.String getUuid() {
		return _learningActivityTry.getUuid();
	}

	/**
	* Sets the uuid of this learning activity try.
	*
	* @param uuid the uuid of this learning activity try
	*/
	public void setUuid(java.lang.String uuid) {
		_learningActivityTry.setUuid(uuid);
	}

	/**
	* Returns the lat ID of this learning activity try.
	*
	* @return the lat ID of this learning activity try
	*/
	public long getLatId() {
		return _learningActivityTry.getLatId();
	}

	/**
	* Sets the lat ID of this learning activity try.
	*
	* @param latId the lat ID of this learning activity try
	*/
	public void setLatId(long latId) {
		_learningActivityTry.setLatId(latId);
	}

	/**
	* Returns the act ID of this learning activity try.
	*
	* @return the act ID of this learning activity try
	*/
	public long getActId() {
		return _learningActivityTry.getActId();
	}

	/**
	* Sets the act ID of this learning activity try.
	*
	* @param actId the act ID of this learning activity try
	*/
	public void setActId(long actId) {
		_learningActivityTry.setActId(actId);
	}

	/**
	* Returns the user ID of this learning activity try.
	*
	* @return the user ID of this learning activity try
	*/
	public long getUserId() {
		return _learningActivityTry.getUserId();
	}

	/**
	* Sets the user ID of this learning activity try.
	*
	* @param userId the user ID of this learning activity try
	*/
	public void setUserId(long userId) {
		_learningActivityTry.setUserId(userId);
	}

	/**
	* Returns the user uuid of this learning activity try.
	*
	* @return the user uuid of this learning activity try
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTry.getUserUuid();
	}

	/**
	* Sets the user uuid of this learning activity try.
	*
	* @param userUuid the user uuid of this learning activity try
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_learningActivityTry.setUserUuid(userUuid);
	}

	/**
	* Returns the start date of this learning activity try.
	*
	* @return the start date of this learning activity try
	*/
	public java.util.Date getStartDate() {
		return _learningActivityTry.getStartDate();
	}

	/**
	* Sets the start date of this learning activity try.
	*
	* @param startDate the start date of this learning activity try
	*/
	public void setStartDate(java.util.Date startDate) {
		_learningActivityTry.setStartDate(startDate);
	}

	/**
	* Returns the result of this learning activity try.
	*
	* @return the result of this learning activity try
	*/
	public long getResult() {
		return _learningActivityTry.getResult();
	}

	/**
	* Sets the result of this learning activity try.
	*
	* @param result the result of this learning activity try
	*/
	public void setResult(long result) {
		_learningActivityTry.setResult(result);
	}

	/**
	* Returns the end date of this learning activity try.
	*
	* @return the end date of this learning activity try
	*/
	public java.util.Date getEndDate() {
		return _learningActivityTry.getEndDate();
	}

	/**
	* Sets the end date of this learning activity try.
	*
	* @param endDate the end date of this learning activity try
	*/
	public void setEndDate(java.util.Date endDate) {
		_learningActivityTry.setEndDate(endDate);
	}

	/**
	* Returns the try data of this learning activity try.
	*
	* @return the try data of this learning activity try
	*/
	public java.lang.String getTryData() {
		return _learningActivityTry.getTryData();
	}

	/**
	* Sets the try data of this learning activity try.
	*
	* @param tryData the try data of this learning activity try
	*/
	public void setTryData(java.lang.String tryData) {
		_learningActivityTry.setTryData(tryData);
	}

	/**
	* Returns the try result data of this learning activity try.
	*
	* @return the try result data of this learning activity try
	*/
	public java.lang.String getTryResultData() {
		return _learningActivityTry.getTryResultData();
	}

	/**
	* Sets the try result data of this learning activity try.
	*
	* @param tryResultData the try result data of this learning activity try
	*/
	public void setTryResultData(java.lang.String tryResultData) {
		_learningActivityTry.setTryResultData(tryResultData);
	}

	/**
	* Returns the comments of this learning activity try.
	*
	* @return the comments of this learning activity try
	*/
	public java.lang.String getComments() {
		return _learningActivityTry.getComments();
	}

	/**
	* Sets the comments of this learning activity try.
	*
	* @param comments the comments of this learning activity try
	*/
	public void setComments(java.lang.String comments) {
		_learningActivityTry.setComments(comments);
	}

	public boolean isNew() {
		return _learningActivityTry.isNew();
	}

	public void setNew(boolean n) {
		_learningActivityTry.setNew(n);
	}

	public boolean isCachedModel() {
		return _learningActivityTry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_learningActivityTry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _learningActivityTry.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _learningActivityTry.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_learningActivityTry.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _learningActivityTry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_learningActivityTry.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new LearningActivityTryWrapper((LearningActivityTry)_learningActivityTry.clone());
	}

	public int compareTo(
		com.liferay.lms.model.LearningActivityTry learningActivityTry) {
		return _learningActivityTry.compareTo(learningActivityTry);
	}

	@Override
	public int hashCode() {
		return _learningActivityTry.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.LearningActivityTry> toCacheModel() {
		return _learningActivityTry.toCacheModel();
	}

	public com.liferay.lms.model.LearningActivityTry toEscapedModel() {
		return new LearningActivityTryWrapper(_learningActivityTry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _learningActivityTry.toString();
	}

	public java.lang.String toXmlString() {
		return _learningActivityTry.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_learningActivityTry.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public LearningActivityTry getWrappedLearningActivityTry() {
		return _learningActivityTry;
	}

	public LearningActivityTry getWrappedModel() {
		return _learningActivityTry;
	}

	public void resetOriginalValues() {
		_learningActivityTry.resetOriginalValues();
	}

	private LearningActivityTry _learningActivityTry;
}