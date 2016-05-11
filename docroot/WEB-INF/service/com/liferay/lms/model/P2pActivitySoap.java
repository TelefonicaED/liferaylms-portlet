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
 * This class is used by SOAP remote services.
 *
 * @author    TLS
 * @generated
 */
public class P2pActivitySoap implements Serializable {
	public static P2pActivitySoap toSoapModel(P2pActivity model) {
		P2pActivitySoap soapModel = new P2pActivitySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setP2pActivityId(model.getP2pActivityId());
		soapModel.setActId(model.getActId());
		soapModel.setUserId(model.getUserId());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setCountCorrections(model.getCountCorrections());
		soapModel.setDescription(model.getDescription());
		soapModel.setDate(model.getDate());
		soapModel.setAsignationsCompleted(model.getAsignationsCompleted());

		return soapModel;
	}

	public static P2pActivitySoap[] toSoapModels(P2pActivity[] models) {
		P2pActivitySoap[] soapModels = new P2pActivitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static P2pActivitySoap[][] toSoapModels(P2pActivity[][] models) {
		P2pActivitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new P2pActivitySoap[models.length][models[0].length];
		}
		else {
			soapModels = new P2pActivitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static P2pActivitySoap[] toSoapModels(List<P2pActivity> models) {
		List<P2pActivitySoap> soapModels = new ArrayList<P2pActivitySoap>(models.size());

		for (P2pActivity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new P2pActivitySoap[soapModels.size()]);
	}

	public P2pActivitySoap() {
	}

	public long getPrimaryKey() {
		return _p2pActivityId;
	}

	public void setPrimaryKey(long pk) {
		setP2pActivityId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getP2pActivityId() {
		return _p2pActivityId;
	}

	public void setP2pActivityId(long p2pActivityId) {
		_p2pActivityId = p2pActivityId;
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

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public long getCountCorrections() {
		return _countCorrections;
	}

	public void setCountCorrections(long countCorrections) {
		_countCorrections = countCorrections;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public Date getDate() {
		return _date;
	}

	public void setDate(Date date) {
		_date = date;
	}

	public boolean getAsignationsCompleted() {
		return _asignationsCompleted;
	}

	public boolean isAsignationsCompleted() {
		return _asignationsCompleted;
	}

	public void setAsignationsCompleted(boolean asignationsCompleted) {
		_asignationsCompleted = asignationsCompleted;
	}

	private String _uuid;
	private long _p2pActivityId;
	private long _actId;
	private long _userId;
	private long _fileEntryId;
	private long _countCorrections;
	private String _description;
	private Date _date;
	private boolean _asignationsCompleted;
}