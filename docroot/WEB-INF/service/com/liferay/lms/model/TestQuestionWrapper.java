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
 * This class is a wrapper for {@link TestQuestion}.
 * </p>
 *
 * @author    TLS
 * @see       TestQuestion
 * @generated
 */
public class TestQuestionWrapper implements TestQuestion,
	ModelWrapper<TestQuestion> {
	public TestQuestionWrapper(TestQuestion testQuestion) {
		_testQuestion = testQuestion;
	}

	public Class<?> getModelClass() {
		return TestQuestion.class;
	}

	public String getModelClassName() {
		return TestQuestion.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("questionId", getQuestionId());
		attributes.put("actId", getActId());
		attributes.put("text", getText());
		attributes.put("questionType", getQuestionType());
		attributes.put("weight", getWeight());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long questionId = (Long)attributes.get("questionId");

		if (questionId != null) {
			setQuestionId(questionId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		String text = (String)attributes.get("text");

		if (text != null) {
			setText(text);
		}

		Long questionType = (Long)attributes.get("questionType");

		if (questionType != null) {
			setQuestionType(questionType);
		}

		Long weight = (Long)attributes.get("weight");

		if (weight != null) {
			setWeight(weight);
		}
	}

	/**
	* Returns the primary key of this test question.
	*
	* @return the primary key of this test question
	*/
	public long getPrimaryKey() {
		return _testQuestion.getPrimaryKey();
	}

	/**
	* Sets the primary key of this test question.
	*
	* @param primaryKey the primary key of this test question
	*/
	public void setPrimaryKey(long primaryKey) {
		_testQuestion.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this test question.
	*
	* @return the uuid of this test question
	*/
	public java.lang.String getUuid() {
		return _testQuestion.getUuid();
	}

	/**
	* Sets the uuid of this test question.
	*
	* @param uuid the uuid of this test question
	*/
	public void setUuid(java.lang.String uuid) {
		_testQuestion.setUuid(uuid);
	}

	/**
	* Returns the question ID of this test question.
	*
	* @return the question ID of this test question
	*/
	public long getQuestionId() {
		return _testQuestion.getQuestionId();
	}

	/**
	* Sets the question ID of this test question.
	*
	* @param questionId the question ID of this test question
	*/
	public void setQuestionId(long questionId) {
		_testQuestion.setQuestionId(questionId);
	}

	/**
	* Returns the act ID of this test question.
	*
	* @return the act ID of this test question
	*/
	public long getActId() {
		return _testQuestion.getActId();
	}

	/**
	* Sets the act ID of this test question.
	*
	* @param actId the act ID of this test question
	*/
	public void setActId(long actId) {
		_testQuestion.setActId(actId);
	}

	/**
	* Returns the text of this test question.
	*
	* @return the text of this test question
	*/
	public java.lang.String getText() {
		return _testQuestion.getText();
	}

	/**
	* Sets the text of this test question.
	*
	* @param text the text of this test question
	*/
	public void setText(java.lang.String text) {
		_testQuestion.setText(text);
	}

	/**
	* Returns the question type of this test question.
	*
	* @return the question type of this test question
	*/
	public long getQuestionType() {
		return _testQuestion.getQuestionType();
	}

	/**
	* Sets the question type of this test question.
	*
	* @param questionType the question type of this test question
	*/
	public void setQuestionType(long questionType) {
		_testQuestion.setQuestionType(questionType);
	}

	/**
	* Returns the weight of this test question.
	*
	* @return the weight of this test question
	*/
	public long getWeight() {
		return _testQuestion.getWeight();
	}

	/**
	* Sets the weight of this test question.
	*
	* @param weight the weight of this test question
	*/
	public void setWeight(long weight) {
		_testQuestion.setWeight(weight);
	}

	public boolean isNew() {
		return _testQuestion.isNew();
	}

	public void setNew(boolean n) {
		_testQuestion.setNew(n);
	}

	public boolean isCachedModel() {
		return _testQuestion.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_testQuestion.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _testQuestion.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _testQuestion.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_testQuestion.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _testQuestion.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_testQuestion.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new TestQuestionWrapper((TestQuestion)_testQuestion.clone());
	}

	public int compareTo(com.liferay.lms.model.TestQuestion testQuestion) {
		return _testQuestion.compareTo(testQuestion);
	}

	@Override
	public int hashCode() {
		return _testQuestion.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.TestQuestion> toCacheModel() {
		return _testQuestion.toCacheModel();
	}

	public com.liferay.lms.model.TestQuestion toEscapedModel() {
		return new TestQuestionWrapper(_testQuestion.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _testQuestion.toString();
	}

	public java.lang.String toXmlString() {
		return _testQuestion.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_testQuestion.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public TestQuestion getWrappedTestQuestion() {
		return _testQuestion;
	}

	public TestQuestion getWrappedModel() {
		return _testQuestion;
	}

	public void resetOriginalValues() {
		_testQuestion.resetOriginalValues();
	}

	private TestQuestion _testQuestion;
}