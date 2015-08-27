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
 * This class is used by SOAP remote services.
 *
 * @author    TLS
 * @generated
 */
public class SurveyResultSoap implements Serializable {
	public static SurveyResultSoap toSoapModel(SurveyResult model) {
		SurveyResultSoap soapModel = new SurveyResultSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setSurveyResultId(model.getSurveyResultId());
		soapModel.setActId(model.getActId());
		soapModel.setLatId(model.getLatId());
		soapModel.setQuestionId(model.getQuestionId());
		soapModel.setAnswerId(model.getAnswerId());
		soapModel.setUserId(model.getUserId());
		soapModel.setFreeAnswer(model.getFreeAnswer());

		return soapModel;
	}

	public static SurveyResultSoap[] toSoapModels(SurveyResult[] models) {
		SurveyResultSoap[] soapModels = new SurveyResultSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SurveyResultSoap[][] toSoapModels(SurveyResult[][] models) {
		SurveyResultSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SurveyResultSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SurveyResultSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SurveyResultSoap[] toSoapModels(List<SurveyResult> models) {
		List<SurveyResultSoap> soapModels = new ArrayList<SurveyResultSoap>(models.size());

		for (SurveyResult model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SurveyResultSoap[soapModels.size()]);
	}

	public SurveyResultSoap() {
	}

	public long getPrimaryKey() {
		return _surveyResultId;
	}

	public void setPrimaryKey(long pk) {
		setSurveyResultId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getSurveyResultId() {
		return _surveyResultId;
	}

	public void setSurveyResultId(long surveyResultId) {
		_surveyResultId = surveyResultId;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_actId = actId;
	}

	public long getLatId() {
		return _latId;
	}

	public void setLatId(long latId) {
		_latId = latId;
	}

	public long getQuestionId() {
		return _questionId;
	}

	public void setQuestionId(long questionId) {
		_questionId = questionId;
	}

	public long getAnswerId() {
		return _answerId;
	}

	public void setAnswerId(long answerId) {
		_answerId = answerId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getFreeAnswer() {
		return _freeAnswer;
	}

	public void setFreeAnswer(String freeAnswer) {
		_freeAnswer = freeAnswer;
	}

	private String _uuid;
	private long _surveyResultId;
	private long _actId;
	private long _latId;
	private long _questionId;
	private long _answerId;
	private long _userId;
	private String _freeAnswer;
}