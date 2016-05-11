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
public class CheckP2pMailingSoap implements Serializable {
	public static CheckP2pMailingSoap toSoapModel(CheckP2pMailing model) {
		CheckP2pMailingSoap soapModel = new CheckP2pMailingSoap();

		soapModel.setCheckP2pId(model.getCheckP2pId());
		soapModel.setActId(model.getActId());
		soapModel.setDate(model.getDate());

		return soapModel;
	}

	public static CheckP2pMailingSoap[] toSoapModels(CheckP2pMailing[] models) {
		CheckP2pMailingSoap[] soapModels = new CheckP2pMailingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CheckP2pMailingSoap[][] toSoapModels(
		CheckP2pMailing[][] models) {
		CheckP2pMailingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CheckP2pMailingSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CheckP2pMailingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CheckP2pMailingSoap[] toSoapModels(
		List<CheckP2pMailing> models) {
		List<CheckP2pMailingSoap> soapModels = new ArrayList<CheckP2pMailingSoap>(models.size());

		for (CheckP2pMailing model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CheckP2pMailingSoap[soapModels.size()]);
	}

	public CheckP2pMailingSoap() {
	}

	public long getPrimaryKey() {
		return _checkP2pId;
	}

	public void setPrimaryKey(long pk) {
		setCheckP2pId(pk);
	}

	public long getCheckP2pId() {
		return _checkP2pId;
	}

	public void setCheckP2pId(long checkP2pId) {
		_checkP2pId = checkP2pId;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_actId = actId;
	}

	public Date getDate() {
		return _date;
	}

	public void setDate(Date date) {
		_date = date;
	}

	private long _checkP2pId;
	private long _actId;
	private Date _date;
}