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
public class LmsPrefsSoap implements Serializable {
	public static LmsPrefsSoap toSoapModel(LmsPrefs model) {
		LmsPrefsSoap soapModel = new LmsPrefsSoap();

		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setTeacherRole(model.getTeacherRole());
		soapModel.setEditorRole(model.getEditorRole());
		soapModel.setLmsTemplates(model.getLmsTemplates());
		soapModel.setActivities(model.getActivities());
		soapModel.setCourseevals(model.getCourseevals());
		soapModel.setScoretranslators(model.getScoretranslators());
		soapModel.setUsersResults(model.getUsersResults());

		return soapModel;
	}

	public static LmsPrefsSoap[] toSoapModels(LmsPrefs[] models) {
		LmsPrefsSoap[] soapModels = new LmsPrefsSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LmsPrefsSoap[][] toSoapModels(LmsPrefs[][] models) {
		LmsPrefsSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LmsPrefsSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LmsPrefsSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LmsPrefsSoap[] toSoapModels(List<LmsPrefs> models) {
		List<LmsPrefsSoap> soapModels = new ArrayList<LmsPrefsSoap>(models.size());

		for (LmsPrefs model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LmsPrefsSoap[soapModels.size()]);
	}

	public LmsPrefsSoap() {
	}

	public long getPrimaryKey() {
		return _companyId;
	}

	public void setPrimaryKey(long pk) {
		setCompanyId(pk);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getTeacherRole() {
		return _teacherRole;
	}

	public void setTeacherRole(long teacherRole) {
		_teacherRole = teacherRole;
	}

	public long getEditorRole() {
		return _editorRole;
	}

	public void setEditorRole(long editorRole) {
		_editorRole = editorRole;
	}

	public String getLmsTemplates() {
		return _lmsTemplates;
	}

	public void setLmsTemplates(String lmsTemplates) {
		_lmsTemplates = lmsTemplates;
	}

	public String getActivities() {
		return _activities;
	}

	public void setActivities(String activities) {
		_activities = activities;
	}

	public String getCourseevals() {
		return _courseevals;
	}

	public void setCourseevals(String courseevals) {
		_courseevals = courseevals;
	}

	public String getScoretranslators() {
		return _scoretranslators;
	}

	public void setScoretranslators(String scoretranslators) {
		_scoretranslators = scoretranslators;
	}

	public long getUsersResults() {
		return _usersResults;
	}

	public void setUsersResults(long usersResults) {
		_usersResults = usersResults;
	}

	private long _companyId;
	private long _teacherRole;
	private long _editorRole;
	private String _lmsTemplates;
	private String _activities;
	private String _courseevals;
	private String _scoretranslators;
	private long _usersResults;
}