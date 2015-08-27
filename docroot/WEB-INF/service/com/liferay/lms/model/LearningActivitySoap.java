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
 * This class is used by SOAP remote services, specifically {@link com.liferay.lms.service.http.LearningActivityServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lms.service.http.LearningActivityServiceSoap
 * @generated
 */
public class LearningActivitySoap implements Serializable {
	public static LearningActivitySoap toSoapModel(LearningActivity model) {
		LearningActivitySoap soapModel = new LearningActivitySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setActId(model.getActId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setTypeId(model.getTypeId());
		soapModel.setStartdate(model.getStartdate());
		soapModel.setEnddate(model.getEnddate());
		soapModel.setPrecedence(model.getPrecedence());
		soapModel.setTries(model.getTries());
		soapModel.setPasspuntuation(model.getPasspuntuation());
		soapModel.setPriority(model.getPriority());
		soapModel.setModuleId(model.getModuleId());
		soapModel.setExtracontent(model.getExtracontent());
		soapModel.setFeedbackCorrect(model.getFeedbackCorrect());
		soapModel.setFeedbackNoCorrect(model.getFeedbackNoCorrect());
		soapModel.setWeightinmodule(model.getWeightinmodule());

		return soapModel;
	}

	public static LearningActivitySoap[] toSoapModels(LearningActivity[] models) {
		LearningActivitySoap[] soapModels = new LearningActivitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LearningActivitySoap[][] toSoapModels(
		LearningActivity[][] models) {
		LearningActivitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LearningActivitySoap[models.length][models[0].length];
		}
		else {
			soapModels = new LearningActivitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LearningActivitySoap[] toSoapModels(
		List<LearningActivity> models) {
		List<LearningActivitySoap> soapModels = new ArrayList<LearningActivitySoap>(models.size());

		for (LearningActivity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LearningActivitySoap[soapModels.size()]);
	}

	public LearningActivitySoap() {
	}

	public long getPrimaryKey() {
		return _actId;
	}

	public void setPrimaryKey(long pk) {
		setActId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_actId = actId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserName() {
		return _statusByUserName;
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public int getTypeId() {
		return _typeId;
	}

	public void setTypeId(int typeId) {
		_typeId = typeId;
	}

	public Date getStartdate() {
		return _startdate;
	}

	public void setStartdate(Date startdate) {
		_startdate = startdate;
	}

	public Date getEnddate() {
		return _enddate;
	}

	public void setEnddate(Date enddate) {
		_enddate = enddate;
	}

	public long getPrecedence() {
		return _precedence;
	}

	public void setPrecedence(long precedence) {
		_precedence = precedence;
	}

	public long getTries() {
		return _tries;
	}

	public void setTries(long tries) {
		_tries = tries;
	}

	public int getPasspuntuation() {
		return _passpuntuation;
	}

	public void setPasspuntuation(int passpuntuation) {
		_passpuntuation = passpuntuation;
	}

	public long getPriority() {
		return _priority;
	}

	public void setPriority(long priority) {
		_priority = priority;
	}

	public long getModuleId() {
		return _moduleId;
	}

	public void setModuleId(long moduleId) {
		_moduleId = moduleId;
	}

	public String getExtracontent() {
		return _extracontent;
	}

	public void setExtracontent(String extracontent) {
		_extracontent = extracontent;
	}

	public String getFeedbackCorrect() {
		return _feedbackCorrect;
	}

	public void setFeedbackCorrect(String feedbackCorrect) {
		_feedbackCorrect = feedbackCorrect;
	}

	public String getFeedbackNoCorrect() {
		return _feedbackNoCorrect;
	}

	public void setFeedbackNoCorrect(String feedbackNoCorrect) {
		_feedbackNoCorrect = feedbackNoCorrect;
	}

	public long getWeightinmodule() {
		return _weightinmodule;
	}

	public void setWeightinmodule(long weightinmodule) {
		_weightinmodule = weightinmodule;
	}

	private String _uuid;
	private long _actId;
	private long _companyId;
	private long _userId;
	private long _groupId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
	private String _title;
	private String _description;
	private int _typeId;
	private Date _startdate;
	private Date _enddate;
	private long _precedence;
	private long _tries;
	private int _passpuntuation;
	private long _priority;
	private long _moduleId;
	private String _extracontent;
	private String _feedbackCorrect;
	private String _feedbackNoCorrect;
	private long _weightinmodule;
}