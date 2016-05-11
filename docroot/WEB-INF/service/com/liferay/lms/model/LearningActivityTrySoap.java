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
 * This class is used by SOAP remote services, specifically {@link com.liferay.lms.service.http.LearningActivityTryServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lms.service.http.LearningActivityTryServiceSoap
 * @generated
 */
public class LearningActivityTrySoap implements Serializable {
	public static LearningActivityTrySoap toSoapModel(LearningActivityTry model) {
		LearningActivityTrySoap soapModel = new LearningActivityTrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setLatId(model.getLatId());
		soapModel.setActId(model.getActId());
		soapModel.setUserId(model.getUserId());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setResult(model.getResult());
		soapModel.setEndDate(model.getEndDate());
		soapModel.setTryData(model.getTryData());
		soapModel.setTryResultData(model.getTryResultData());
		soapModel.setComments(model.getComments());

		return soapModel;
	}

	public static LearningActivityTrySoap[] toSoapModels(
		LearningActivityTry[] models) {
		LearningActivityTrySoap[] soapModels = new LearningActivityTrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LearningActivityTrySoap[][] toSoapModels(
		LearningActivityTry[][] models) {
		LearningActivityTrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LearningActivityTrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new LearningActivityTrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LearningActivityTrySoap[] toSoapModels(
		List<LearningActivityTry> models) {
		List<LearningActivityTrySoap> soapModels = new ArrayList<LearningActivityTrySoap>(models.size());

		for (LearningActivityTry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LearningActivityTrySoap[soapModels.size()]);
	}

	public LearningActivityTrySoap() {
	}

	public long getPrimaryKey() {
		return _latId;
	}

	public void setPrimaryKey(long pk) {
		setLatId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLatId() {
		return _latId;
	}

	public void setLatId(long latId) {
		_latId = latId;
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

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public long getResult() {
		return _result;
	}

	public void setResult(long result) {
		_result = result;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public String getTryData() {
		return _tryData;
	}

	public void setTryData(String tryData) {
		_tryData = tryData;
	}

	public String getTryResultData() {
		return _tryResultData;
	}

	public void setTryResultData(String tryResultData) {
		_tryResultData = tryResultData;
	}

	public String getComments() {
		return _comments;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	private String _uuid;
	private long _latId;
	private long _actId;
	private long _userId;
	private Date _startDate;
	private long _result;
	private Date _endDate;
	private String _tryData;
	private String _tryResultData;
	private String _comments;
}