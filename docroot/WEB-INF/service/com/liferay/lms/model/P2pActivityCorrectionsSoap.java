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
public class P2pActivityCorrectionsSoap implements Serializable {
	public static P2pActivityCorrectionsSoap toSoapModel(
		P2pActivityCorrections model) {
		P2pActivityCorrectionsSoap soapModel = new P2pActivityCorrectionsSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setP2pActivityCorrectionsId(model.getP2pActivityCorrectionsId());
		soapModel.setP2pActivityId(model.getP2pActivityId());
		soapModel.setUserId(model.getUserId());
		soapModel.setActId(model.getActId());
		soapModel.setDescription(model.getDescription());
		soapModel.setDate(model.getDate());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setResult(model.getResult());

		return soapModel;
	}

	public static P2pActivityCorrectionsSoap[] toSoapModels(
		P2pActivityCorrections[] models) {
		P2pActivityCorrectionsSoap[] soapModels = new P2pActivityCorrectionsSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static P2pActivityCorrectionsSoap[][] toSoapModels(
		P2pActivityCorrections[][] models) {
		P2pActivityCorrectionsSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new P2pActivityCorrectionsSoap[models.length][models[0].length];
		}
		else {
			soapModels = new P2pActivityCorrectionsSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static P2pActivityCorrectionsSoap[] toSoapModels(
		List<P2pActivityCorrections> models) {
		List<P2pActivityCorrectionsSoap> soapModels = new ArrayList<P2pActivityCorrectionsSoap>(models.size());

		for (P2pActivityCorrections model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new P2pActivityCorrectionsSoap[soapModels.size()]);
	}

	public P2pActivityCorrectionsSoap() {
	}

	public long getPrimaryKey() {
		return _p2pActivityCorrectionsId;
	}

	public void setPrimaryKey(long pk) {
		setP2pActivityCorrectionsId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getP2pActivityCorrectionsId() {
		return _p2pActivityCorrectionsId;
	}

	public void setP2pActivityCorrectionsId(long p2pActivityCorrectionsId) {
		_p2pActivityCorrectionsId = p2pActivityCorrectionsId;
	}

	public long getP2pActivityId() {
		return _p2pActivityId;
	}

	public void setP2pActivityId(long p2pActivityId) {
		_p2pActivityId = p2pActivityId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_actId = actId;
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

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public long getResult() {
		return _result;
	}

	public void setResult(long result) {
		_result = result;
	}

	private String _uuid;
	private long _p2pActivityCorrectionsId;
	private long _p2pActivityId;
	private long _userId;
	private long _actId;
	private String _description;
	private Date _date;
	private long _fileEntryId;
	private long _result;
}