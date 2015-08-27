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
 * This class is used by SOAP remote services, specifically {@link com.liferay.lms.service.http.SCORMContentServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lms.service.http.SCORMContentServiceSoap
 * @generated
 */
public class SCORMContentSoap implements Serializable {
	public static SCORMContentSoap toSoapModel(SCORMContent model) {
		SCORMContentSoap soapModel = new SCORMContentSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setScormId(model.getScormId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setIndex(model.getIndex());
		soapModel.setCiphered(model.getCiphered());

		return soapModel;
	}

	public static SCORMContentSoap[] toSoapModels(SCORMContent[] models) {
		SCORMContentSoap[] soapModels = new SCORMContentSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SCORMContentSoap[][] toSoapModels(SCORMContent[][] models) {
		SCORMContentSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SCORMContentSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SCORMContentSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SCORMContentSoap[] toSoapModels(List<SCORMContent> models) {
		List<SCORMContentSoap> soapModels = new ArrayList<SCORMContentSoap>(models.size());

		for (SCORMContent model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SCORMContentSoap[soapModels.size()]);
	}

	public SCORMContentSoap() {
	}

	public long getPrimaryKey() {
		return _scormId;
	}

	public void setPrimaryKey(long pk) {
		setScormId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getScormId() {
		return _scormId;
	}

	public void setScormId(long scormId) {
		_scormId = scormId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
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

	public String getIndex() {
		return _index;
	}

	public void setIndex(String index) {
		_index = index;
	}

	public boolean getCiphered() {
		return _ciphered;
	}

	public boolean isCiphered() {
		return _ciphered;
	}

	public void setCiphered(boolean ciphered) {
		_ciphered = ciphered;
	}

	private String _uuid;
	private long _scormId;
	private long _companyId;
	private long _groupId;
	private long _userId;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
	private String _title;
	private String _description;
	private String _index;
	private boolean _ciphered;
}