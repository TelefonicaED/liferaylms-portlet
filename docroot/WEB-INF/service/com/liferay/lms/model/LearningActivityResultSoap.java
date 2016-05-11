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
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.lms.service.http.LearningActivityResultServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lms.service.http.LearningActivityResultServiceSoap
 * @generated
 */
public class LearningActivityResultSoap implements Serializable {
	public static LearningActivityResultSoap toSoapModel(
		LearningActivityResult model) {
		LearningActivityResultSoap soapModel = new LearningActivityResultSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setLarId(model.getLarId());
		soapModel.setActId(model.getActId());
		soapModel.setUserId(model.getUserId());
		soapModel.setResult(model.getResult());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setEndDate(model.getEndDate());
		soapModel.setLatId(model.getLatId());
		soapModel.setComments(model.getComments());
		soapModel.setPassed(model.getPassed());

		return soapModel;
	}

	public static LearningActivityResultSoap[] toSoapModels(
		LearningActivityResult[] models) {
		LearningActivityResultSoap[] soapModels = new LearningActivityResultSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LearningActivityResultSoap[][] toSoapModels(
		LearningActivityResult[][] models) {
		LearningActivityResultSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LearningActivityResultSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LearningActivityResultSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LearningActivityResultSoap[] toSoapModels(
		List<LearningActivityResult> models) {
		List<LearningActivityResultSoap> soapModels = new ArrayList<LearningActivityResultSoap>(models.size());

		for (LearningActivityResult model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LearningActivityResultSoap[soapModels.size()]);
	}

	public LearningActivityResultSoap() {
	}

	public long getPrimaryKey() {
		return _larId;
	}

	public void setPrimaryKey(long pk) {
		setLarId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLarId() {
		return _larId;
	}

	public void setLarId(long larId) {
		_larId = larId;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_actId = actId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getResult() {
		return _result;
	}

	public void setResult(long result) {
		_result = result;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public long getLatId() {
		return _latId;
	}

	public void setLatId(long latId) {
		_latId = latId;
	}

	public String getComments() {
		return _comments;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public boolean getPassed() {
		return _passed;
	}

	public boolean isPassed() {
		return _passed;
	}

	public void setPassed(boolean passed) {
		_passed = passed;
	}

	private String _uuid;
	private long _larId;
	private long _actId;
	private long _userId;
	private long _result;
	private Date _startDate;
	private Date _endDate;
	private long _latId;
	private String _comments;
	private boolean _passed;
}