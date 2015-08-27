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
 * This class is used by SOAP remote services, specifically {@link com.liferay.lms.service.http.ModuleResultServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lms.service.http.ModuleResultServiceSoap
 * @generated
 */
public class ModuleResultSoap implements Serializable {
	public static ModuleResultSoap toSoapModel(ModuleResult model) {
		ModuleResultSoap soapModel = new ModuleResultSoap();

		soapModel.setModuleId(model.getModuleId());
		soapModel.setResult(model.getResult());
		soapModel.setComments(model.getComments());
		soapModel.setUserId(model.getUserId());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setPassed(model.getPassed());
		soapModel.setMrId(model.getMrId());
		soapModel.setPassedDate(model.getPassedDate());

		return soapModel;
	}

	public static ModuleResultSoap[] toSoapModels(ModuleResult[] models) {
		ModuleResultSoap[] soapModels = new ModuleResultSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ModuleResultSoap[][] toSoapModels(ModuleResult[][] models) {
		ModuleResultSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ModuleResultSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ModuleResultSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ModuleResultSoap[] toSoapModels(List<ModuleResult> models) {
		List<ModuleResultSoap> soapModels = new ArrayList<ModuleResultSoap>(models.size());

		for (ModuleResult model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ModuleResultSoap[soapModels.size()]);
	}

	public ModuleResultSoap() {
	}

	public long getPrimaryKey() {
		return _mrId;
	}

	public void setPrimaryKey(long pk) {
		setMrId(pk);
	}

	public long getModuleId() {
		return _moduleId;
	}

	public void setModuleId(long moduleId) {
		_moduleId = moduleId;
	}

	public long getResult() {
		return _result;
	}

	public void setResult(long result) {
		_result = result;
	}

	public String getComments() {
		return _comments;
	}

	public void setComments(String comments) {
		_comments = comments;
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

	public boolean getPassed() {
		return _passed;
	}

	public boolean isPassed() {
		return _passed;
	}

	public void setPassed(boolean passed) {
		_passed = passed;
	}

	public long getMrId() {
		return _mrId;
	}

	public void setMrId(long mrId) {
		_mrId = mrId;
	}

	public Date getPassedDate() {
		return _passedDate;
	}

	public void setPassedDate(Date passedDate) {
		_passedDate = passedDate;
	}

	private long _moduleId;
	private long _result;
	private String _comments;
	private long _userId;
	private Date _startDate;
	private boolean _passed;
	private long _mrId;
	private Date _passedDate;
}