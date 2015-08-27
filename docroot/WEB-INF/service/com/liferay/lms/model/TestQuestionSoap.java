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
 * This class is used by SOAP remote services, specifically {@link com.liferay.lms.service.http.TestQuestionServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lms.service.http.TestQuestionServiceSoap
 * @generated
 */
public class TestQuestionSoap implements Serializable {
	public static TestQuestionSoap toSoapModel(TestQuestion model) {
		TestQuestionSoap soapModel = new TestQuestionSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setQuestionId(model.getQuestionId());
		soapModel.setActId(model.getActId());
		soapModel.setText(model.getText());
		soapModel.setQuestionType(model.getQuestionType());
		soapModel.setWeight(model.getWeight());

		return soapModel;
	}

	public static TestQuestionSoap[] toSoapModels(TestQuestion[] models) {
		TestQuestionSoap[] soapModels = new TestQuestionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TestQuestionSoap[][] toSoapModels(TestQuestion[][] models) {
		TestQuestionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new TestQuestionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new TestQuestionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TestQuestionSoap[] toSoapModels(List<TestQuestion> models) {
		List<TestQuestionSoap> soapModels = new ArrayList<TestQuestionSoap>(models.size());

		for (TestQuestion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TestQuestionSoap[soapModels.size()]);
	}

	public TestQuestionSoap() {
	}

	public long getPrimaryKey() {
		return _questionId;
	}

	public void setPrimaryKey(long pk) {
		setQuestionId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
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

	public String getText() {
		return _text;
	}

	public void setText(String text) {
		_text = text;
	}

	public long getQuestionType() {
		return _questionType;
	}

	public void setQuestionType(long questionType) {
		_questionType = questionType;
	}

	public long getWeight() {
		return _weight;
	}

	public void setWeight(long weight) {
		_weight = weight;
	}

	private String _uuid;
	private long _questionId;
	private long _actId;
	private String _text;
	private long _questionType;
	private long _weight;
}