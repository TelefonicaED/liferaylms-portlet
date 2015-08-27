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

import com.liferay.lms.service.TestAnswerLocalServiceUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TLS
 */
public class TestAnswerClp extends BaseModelImpl<TestAnswer>
	implements TestAnswer {
	public TestAnswerClp() {
	}

	public Class<?> getModelClass() {
		return TestAnswer.class;
	}

	public String getModelClassName() {
		return TestAnswer.class.getName();
	}

	public long getPrimaryKey() {
		return _answerId;
	}

	public void setPrimaryKey(long primaryKey) {
		setAnswerId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_answerId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
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

	@Override
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

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getAnswerId() {
		return _answerId;
	}

	public void setAnswerId(long answerId) {
		_answerId = answerId;
	}

	public long getQuestionId() {
		return _questionId;
	}

	public void setQuestionId(long questionId) {
		_questionId = questionId;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_actId = actId;
	}

	public long getPrecedence() {
		return _precedence;
	}

	public void setPrecedence(long precedence) {
		_precedence = precedence;
	}

	public String getAnswer() {
		return _answer;
	}

	public void setAnswer(String answer) {
		_answer = answer;
	}

	public boolean getIsCorrect() {
		return _isCorrect;
	}

	public boolean isIsCorrect() {
		return _isCorrect;
	}

	public void setIsCorrect(boolean isCorrect) {
		_isCorrect = isCorrect;
	}

	public String getFeedbackCorrect() {
		return _feedbackCorrect;
	}

	public void setFeedbackCorrect(String feedbackCorrect) {
		_feedbackCorrect = feedbackCorrect;
	}

	public String getFeedbacknocorrect() {
		return _feedbacknocorrect;
	}

	public void setFeedbacknocorrect(String feedbacknocorrect) {
		_feedbacknocorrect = feedbacknocorrect;
	}

	public BaseModel<?> getTestAnswerRemoteModel() {
		return _testAnswerRemoteModel;
	}

	public void setTestAnswerRemoteModel(BaseModel<?> testAnswerRemoteModel) {
		_testAnswerRemoteModel = testAnswerRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			TestAnswerLocalServiceUtil.addTestAnswer(this);
		}
		else {
			TestAnswerLocalServiceUtil.updateTestAnswer(this);
		}
	}

	@Override
	public TestAnswer toEscapedModel() {
		return (TestAnswer)Proxy.newProxyInstance(TestAnswer.class.getClassLoader(),
			new Class[] { TestAnswer.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		TestAnswerClp clone = new TestAnswerClp();

		clone.setUuid(getUuid());
		clone.setAnswerId(getAnswerId());
		clone.setQuestionId(getQuestionId());
		clone.setActId(getActId());
		clone.setPrecedence(getPrecedence());
		clone.setAnswer(getAnswer());
		clone.setIsCorrect(getIsCorrect());
		clone.setFeedbackCorrect(getFeedbackCorrect());
		clone.setFeedbacknocorrect(getFeedbacknocorrect());

		return clone;
	}

	public int compareTo(TestAnswer testAnswer) {
		int value = 0;

		if (getAnswerId() < testAnswer.getAnswerId()) {
			value = -1;
		}
		else if (getAnswerId() > testAnswer.getAnswerId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		TestAnswerClp testAnswer = null;

		try {
			testAnswer = (TestAnswerClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = testAnswer.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", answerId=");
		sb.append(getAnswerId());
		sb.append(", questionId=");
		sb.append(getQuestionId());
		sb.append(", actId=");
		sb.append(getActId());
		sb.append(", precedence=");
		sb.append(getPrecedence());
		sb.append(", answer=");
		sb.append(getAnswer());
		sb.append(", isCorrect=");
		sb.append(getIsCorrect());
		sb.append(", feedbackCorrect=");
		sb.append(getFeedbackCorrect());
		sb.append(", feedbacknocorrect=");
		sb.append(getFeedbacknocorrect());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.TestAnswer");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>answerId</column-name><column-value><![CDATA[");
		sb.append(getAnswerId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>questionId</column-name><column-value><![CDATA[");
		sb.append(getQuestionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actId</column-name><column-value><![CDATA[");
		sb.append(getActId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>precedence</column-name><column-value><![CDATA[");
		sb.append(getPrecedence());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>answer</column-name><column-value><![CDATA[");
		sb.append(getAnswer());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>isCorrect</column-name><column-value><![CDATA[");
		sb.append(getIsCorrect());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>feedbackCorrect</column-name><column-value><![CDATA[");
		sb.append(getFeedbackCorrect());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>feedbacknocorrect</column-name><column-value><![CDATA[");
		sb.append(getFeedbacknocorrect());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private long _answerId;
	private long _questionId;
	private long _actId;
	private long _precedence;
	private String _answer;
	private boolean _isCorrect;
	private String _feedbackCorrect;
	private String _feedbacknocorrect;
	private BaseModel<?> _testAnswerRemoteModel;
}