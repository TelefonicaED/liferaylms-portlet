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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SurveyResult}.
 * </p>
 *
 * @author    TLS
 * @see       SurveyResult
 * @generated
 */
public class SurveyResultWrapper implements SurveyResult,
	ModelWrapper<SurveyResult> {
	public SurveyResultWrapper(SurveyResult surveyResult) {
		_surveyResult = surveyResult;
	}

	public Class<?> getModelClass() {
		return SurveyResult.class;
	}

	public String getModelClassName() {
		return SurveyResult.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("surveyResultId", getSurveyResultId());
		attributes.put("actId", getActId());
		attributes.put("latId", getLatId());
		attributes.put("questionId", getQuestionId());
		attributes.put("answerId", getAnswerId());
		attributes.put("userId", getUserId());
		attributes.put("freeAnswer", getFreeAnswer());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long surveyResultId = (Long)attributes.get("surveyResultId");

		if (surveyResultId != null) {
			setSurveyResultId(surveyResultId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long latId = (Long)attributes.get("latId");

		if (latId != null) {
			setLatId(latId);
		}

		Long questionId = (Long)attributes.get("questionId");

		if (questionId != null) {
			setQuestionId(questionId);
		}

		Long answerId = (Long)attributes.get("answerId");

		if (answerId != null) {
			setAnswerId(answerId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String freeAnswer = (String)attributes.get("freeAnswer");

		if (freeAnswer != null) {
			setFreeAnswer(freeAnswer);
		}
	}

	/**
	* Returns the primary key of this survey result.
	*
	* @return the primary key of this survey result
	*/
	public long getPrimaryKey() {
		return _surveyResult.getPrimaryKey();
	}

	/**
	* Sets the primary key of this survey result.
	*
	* @param primaryKey the primary key of this survey result
	*/
	public void setPrimaryKey(long primaryKey) {
		_surveyResult.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this survey result.
	*
	* @return the uuid of this survey result
	*/
	public java.lang.String getUuid() {
		return _surveyResult.getUuid();
	}

	/**
	* Sets the uuid of this survey result.
	*
	* @param uuid the uuid of this survey result
	*/
	public void setUuid(java.lang.String uuid) {
		_surveyResult.setUuid(uuid);
	}

	/**
	* Returns the survey result ID of this survey result.
	*
	* @return the survey result ID of this survey result
	*/
	public long getSurveyResultId() {
		return _surveyResult.getSurveyResultId();
	}

	/**
	* Sets the survey result ID of this survey result.
	*
	* @param surveyResultId the survey result ID of this survey result
	*/
	public void setSurveyResultId(long surveyResultId) {
		_surveyResult.setSurveyResultId(surveyResultId);
	}

	/**
	* Returns the act ID of this survey result.
	*
	* @return the act ID of this survey result
	*/
	public long getActId() {
		return _surveyResult.getActId();
	}

	/**
	* Sets the act ID of this survey result.
	*
	* @param actId the act ID of this survey result
	*/
	public void setActId(long actId) {
		_surveyResult.setActId(actId);
	}

	/**
	* Returns the lat ID of this survey result.
	*
	* @return the lat ID of this survey result
	*/
	public long getLatId() {
		return _surveyResult.getLatId();
	}

	/**
	* Sets the lat ID of this survey result.
	*
	* @param latId the lat ID of this survey result
	*/
	public void setLatId(long latId) {
		_surveyResult.setLatId(latId);
	}

	/**
	* Returns the question ID of this survey result.
	*
	* @return the question ID of this survey result
	*/
	public long getQuestionId() {
		return _surveyResult.getQuestionId();
	}

	/**
	* Sets the question ID of this survey result.
	*
	* @param questionId the question ID of this survey result
	*/
	public void setQuestionId(long questionId) {
		_surveyResult.setQuestionId(questionId);
	}

	/**
	* Returns the answer ID of this survey result.
	*
	* @return the answer ID of this survey result
	*/
	public long getAnswerId() {
		return _surveyResult.getAnswerId();
	}

	/**
	* Sets the answer ID of this survey result.
	*
	* @param answerId the answer ID of this survey result
	*/
	public void setAnswerId(long answerId) {
		_surveyResult.setAnswerId(answerId);
	}

	/**
	* Returns the user ID of this survey result.
	*
	* @return the user ID of this survey result
	*/
	public long getUserId() {
		return _surveyResult.getUserId();
	}

	/**
	* Sets the user ID of this survey result.
	*
	* @param userId the user ID of this survey result
	*/
	public void setUserId(long userId) {
		_surveyResult.setUserId(userId);
	}

	/**
	* Returns the user uuid of this survey result.
	*
	* @return the user uuid of this survey result
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _surveyResult.getUserUuid();
	}

	/**
	* Sets the user uuid of this survey result.
	*
	* @param userUuid the user uuid of this survey result
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_surveyResult.setUserUuid(userUuid);
	}

	/**
	* Returns the free answer of this survey result.
	*
	* @return the free answer of this survey result
	*/
	public java.lang.String getFreeAnswer() {
		return _surveyResult.getFreeAnswer();
	}

	/**
	* Sets the free answer of this survey result.
	*
	* @param freeAnswer the free answer of this survey result
	*/
	public void setFreeAnswer(java.lang.String freeAnswer) {
		_surveyResult.setFreeAnswer(freeAnswer);
	}

	public boolean isNew() {
		return _surveyResult.isNew();
	}

	public void setNew(boolean n) {
		_surveyResult.setNew(n);
	}

	public boolean isCachedModel() {
		return _surveyResult.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_surveyResult.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _surveyResult.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _surveyResult.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_surveyResult.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _surveyResult.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_surveyResult.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SurveyResultWrapper((SurveyResult)_surveyResult.clone());
	}

	public int compareTo(com.liferay.lms.model.SurveyResult surveyResult) {
		return _surveyResult.compareTo(surveyResult);
	}

	@Override
	public int hashCode() {
		return _surveyResult.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.SurveyResult> toCacheModel() {
		return _surveyResult.toCacheModel();
	}

	public com.liferay.lms.model.SurveyResult toEscapedModel() {
		return new SurveyResultWrapper(_surveyResult.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _surveyResult.toString();
	}

	public java.lang.String toXmlString() {
		return _surveyResult.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_surveyResult.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public SurveyResult getWrappedSurveyResult() {
		return _surveyResult;
	}

	public SurveyResult getWrappedModel() {
		return _surveyResult;
	}

	public void resetOriginalValues() {
		_surveyResult.resetOriginalValues();
	}

	private SurveyResult _surveyResult;
}