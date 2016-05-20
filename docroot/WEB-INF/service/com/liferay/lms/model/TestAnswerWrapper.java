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
 * This class is a wrapper for {@link TestAnswer}.
 * </p>
 *
 * @author    TLS
 * @see       TestAnswer
 * @generated
 */
public class TestAnswerWrapper implements TestAnswer, ModelWrapper<TestAnswer> {
	public TestAnswerWrapper(TestAnswer testAnswer) {
		_testAnswer = testAnswer;
	}

	public Class<?> getModelClass() {
		return TestAnswer.class;
	}

	public String getModelClassName() {
		return TestAnswer.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("answerId", getAnswerId());
		attributes.put("questionId", getQuestionId());
		attributes.put("actId", getActId());
		attributes.put("precedence", getPrecedence());
		attributes.put("answer", getAnswer());
		attributes.put("isCorrect", getIsCorrect());
		attributes.put("feedbackCorrect", getFeedbackCorrect());
		attributes.put("feedbacknocorrect", getFeedbacknocorrect());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long answerId = (Long)attributes.get("answerId");

		if (answerId != null) {
			setAnswerId(answerId);
		}

		Long questionId = (Long)attributes.get("questionId");

		if (questionId != null) {
			setQuestionId(questionId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long precedence = (Long)attributes.get("precedence");

		if (precedence != null) {
			setPrecedence(precedence);
		}

		String answer = (String)attributes.get("answer");

		if (answer != null) {
			setAnswer(answer);
		}

		Boolean isCorrect = (Boolean)attributes.get("isCorrect");

		if (isCorrect != null) {
			setIsCorrect(isCorrect);
		}

		String feedbackCorrect = (String)attributes.get("feedbackCorrect");

		if (feedbackCorrect != null) {
			setFeedbackCorrect(feedbackCorrect);
		}

		String feedbacknocorrect = (String)attributes.get("feedbacknocorrect");

		if (feedbacknocorrect != null) {
			setFeedbacknocorrect(feedbacknocorrect);
		}
	}

	/**
	* Returns the primary key of this test answer.
	*
	* @return the primary key of this test answer
	*/
	public long getPrimaryKey() {
		return _testAnswer.getPrimaryKey();
	}

	/**
	* Sets the primary key of this test answer.
	*
	* @param primaryKey the primary key of this test answer
	*/
	public void setPrimaryKey(long primaryKey) {
		_testAnswer.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this test answer.
	*
	* @return the uuid of this test answer
	*/
	public java.lang.String getUuid() {
		return _testAnswer.getUuid();
	}

	/**
	* Sets the uuid of this test answer.
	*
	* @param uuid the uuid of this test answer
	*/
	public void setUuid(java.lang.String uuid) {
		_testAnswer.setUuid(uuid);
	}

	/**
	* Returns the answer ID of this test answer.
	*
	* @return the answer ID of this test answer
	*/
	public long getAnswerId() {
		return _testAnswer.getAnswerId();
	}

	/**
	* Sets the answer ID of this test answer.
	*
	* @param answerId the answer ID of this test answer
	*/
	public void setAnswerId(long answerId) {
		_testAnswer.setAnswerId(answerId);
	}

	/**
	* Returns the question ID of this test answer.
	*
	* @return the question ID of this test answer
	*/
	public long getQuestionId() {
		return _testAnswer.getQuestionId();
	}

	/**
	* Sets the question ID of this test answer.
	*
	* @param questionId the question ID of this test answer
	*/
	public void setQuestionId(long questionId) {
		_testAnswer.setQuestionId(questionId);
	}

	/**
	* Returns the act ID of this test answer.
	*
	* @return the act ID of this test answer
	*/
	public long getActId() {
		return _testAnswer.getActId();
	}

	/**
	* Sets the act ID of this test answer.
	*
	* @param actId the act ID of this test answer
	*/
	public void setActId(long actId) {
		_testAnswer.setActId(actId);
	}

	/**
	* Returns the precedence of this test answer.
	*
	* @return the precedence of this test answer
	*/
	public long getPrecedence() {
		return _testAnswer.getPrecedence();
	}

	/**
	* Sets the precedence of this test answer.
	*
	* @param precedence the precedence of this test answer
	*/
	public void setPrecedence(long precedence) {
		_testAnswer.setPrecedence(precedence);
	}

	/**
	* Returns the answer of this test answer.
	*
	* @return the answer of this test answer
	*/
	public java.lang.String getAnswer() {
		return _testAnswer.getAnswer();
	}

	/**
	* Sets the answer of this test answer.
	*
	* @param answer the answer of this test answer
	*/
	public void setAnswer(java.lang.String answer) {
		_testAnswer.setAnswer(answer);
	}

	/**
	* Returns the is correct of this test answer.
	*
	* @return the is correct of this test answer
	*/
	public boolean getIsCorrect() {
		return _testAnswer.getIsCorrect();
	}

	/**
	* Returns <code>true</code> if this test answer is is correct.
	*
	* @return <code>true</code> if this test answer is is correct; <code>false</code> otherwise
	*/
	public boolean isIsCorrect() {
		return _testAnswer.isIsCorrect();
	}

	/**
	* Sets whether this test answer is is correct.
	*
	* @param isCorrect the is correct of this test answer
	*/
	public void setIsCorrect(boolean isCorrect) {
		_testAnswer.setIsCorrect(isCorrect);
	}

	/**
	* Returns the feedback correct of this test answer.
	*
	* @return the feedback correct of this test answer
	*/
	public java.lang.String getFeedbackCorrect() {
		return _testAnswer.getFeedbackCorrect();
	}

	/**
	* Sets the feedback correct of this test answer.
	*
	* @param feedbackCorrect the feedback correct of this test answer
	*/
	public void setFeedbackCorrect(java.lang.String feedbackCorrect) {
		_testAnswer.setFeedbackCorrect(feedbackCorrect);
	}

	/**
	* Returns the feedbacknocorrect of this test answer.
	*
	* @return the feedbacknocorrect of this test answer
	*/
	public java.lang.String getFeedbacknocorrect() {
		return _testAnswer.getFeedbacknocorrect();
	}

	/**
	* Sets the feedbacknocorrect of this test answer.
	*
	* @param feedbacknocorrect the feedbacknocorrect of this test answer
	*/
	public void setFeedbacknocorrect(java.lang.String feedbacknocorrect) {
		_testAnswer.setFeedbacknocorrect(feedbacknocorrect);
	}

	public boolean isNew() {
		return _testAnswer.isNew();
	}

	public void setNew(boolean n) {
		_testAnswer.setNew(n);
	}

	public boolean isCachedModel() {
		return _testAnswer.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_testAnswer.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _testAnswer.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _testAnswer.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_testAnswer.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _testAnswer.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_testAnswer.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new TestAnswerWrapper((TestAnswer)_testAnswer.clone());
	}

	public int compareTo(com.liferay.lms.model.TestAnswer testAnswer) {
		return _testAnswer.compareTo(testAnswer);
	}

	@Override
	public int hashCode() {
		return _testAnswer.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.TestAnswer> toCacheModel() {
		return _testAnswer.toCacheModel();
	}

	public com.liferay.lms.model.TestAnswer toEscapedModel() {
		return new TestAnswerWrapper(_testAnswer.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _testAnswer.toString();
	}

	public java.lang.String toXmlString() {
		return _testAnswer.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_testAnswer.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public TestAnswer getWrappedTestAnswer() {
		return _testAnswer;
	}

	public TestAnswer getWrappedModel() {
		return _testAnswer;
	}

	public void resetOriginalValues() {
		_testAnswer.resetOriginalValues();
	}

	private TestAnswer _testAnswer;
}