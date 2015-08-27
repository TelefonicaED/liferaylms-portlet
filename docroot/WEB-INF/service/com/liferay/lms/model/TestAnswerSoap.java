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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.lms.service.http.TestAnswerServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lms.service.http.TestAnswerServiceSoap
 * @generated
 */
public class TestAnswerSoap implements Serializable {
	public static TestAnswerSoap toSoapModel(TestAnswer model) {
		TestAnswerSoap soapModel = new TestAnswerSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setAnswerId(model.getAnswerId());
		soapModel.setQuestionId(model.getQuestionId());
		soapModel.setActId(model.getActId());
		soapModel.setPrecedence(model.getPrecedence());
		soapModel.setAnswer(model.getAnswer());
		soapModel.setIsCorrect(model.getIsCorrect());
		soapModel.setFeedbackCorrect(model.getFeedbackCorrect());
		soapModel.setFeedbacknocorrect(model.getFeedbacknocorrect());

		return soapModel;
	}

	public static TestAnswerSoap[] toSoapModels(TestAnswer[] models) {
		TestAnswerSoap[] soapModels = new TestAnswerSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TestAnswerSoap[][] toSoapModels(TestAnswer[][] models) {
		TestAnswerSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new TestAnswerSoap[models.length][models[0].length];
		}
		else {
			soapModels = new TestAnswerSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TestAnswerSoap[] toSoapModels(List<TestAnswer> models) {
		List<TestAnswerSoap> soapModels = new ArrayList<TestAnswerSoap>(models.size());

		for (TestAnswer model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TestAnswerSoap[soapModels.size()]);
	}

	public TestAnswerSoap() {
	}

	public long getPrimaryKey() {
		return _answerId;
	}

	public void setPrimaryKey(long pk) {
		setAnswerId(pk);
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

	private String _uuid;
	private long _answerId;
	private long _questionId;
	private long _actId;
	private long _precedence;
	private String _answer;
	private boolean _isCorrect;
	private String _feedbackCorrect;
	private String _feedbacknocorrect;
}