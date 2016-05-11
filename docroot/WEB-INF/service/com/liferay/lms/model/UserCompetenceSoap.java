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
 * This class is used by SOAP remote services, specifically {@link com.liferay.lms.service.http.UserCompetenceServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lms.service.http.UserCompetenceServiceSoap
 * @generated
 */
public class UserCompetenceSoap implements Serializable {
	public static UserCompetenceSoap toSoapModel(UserCompetence model) {
		UserCompetenceSoap soapModel = new UserCompetenceSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setUsercompId(model.getUsercompId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCompetenceId(model.getCompetenceId());
		soapModel.setCompDate(model.getCompDate());
		soapModel.setCourseId(model.getCourseId());

		return soapModel;
	}

	public static UserCompetenceSoap[] toSoapModels(UserCompetence[] models) {
		UserCompetenceSoap[] soapModels = new UserCompetenceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static UserCompetenceSoap[][] toSoapModels(UserCompetence[][] models) {
		UserCompetenceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new UserCompetenceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new UserCompetenceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static UserCompetenceSoap[] toSoapModels(List<UserCompetence> models) {
		List<UserCompetenceSoap> soapModels = new ArrayList<UserCompetenceSoap>(models.size());

		for (UserCompetence model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new UserCompetenceSoap[soapModels.size()]);
	}

	public UserCompetenceSoap() {
	}

	public long getPrimaryKey() {
		return _usercompId;
	}

	public void setPrimaryKey(long pk) {
		setUsercompId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getUsercompId() {
		return _usercompId;
	}

	public void setUsercompId(long usercompId) {
		_usercompId = usercompId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getCompetenceId() {
		return _competenceId;
	}

	public void setCompetenceId(long competenceId) {
		_competenceId = competenceId;
	}

	public Date getCompDate() {
		return _compDate;
	}

	public void setCompDate(Date compDate) {
		_compDate = compDate;
	}

	public long getCourseId() {
		return _courseId;
	}

	public void setCourseId(long courseId) {
		_courseId = courseId;
	}

	private String _uuid;
	private long _usercompId;
	private long _userId;
	private long _competenceId;
	private Date _compDate;
	private long _courseId;
}