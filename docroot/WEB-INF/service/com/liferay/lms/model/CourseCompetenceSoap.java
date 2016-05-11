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
 * This class is used by SOAP remote services, specifically {@link com.liferay.lms.service.http.CourseCompetenceServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lms.service.http.CourseCompetenceServiceSoap
 * @generated
 */
public class CourseCompetenceSoap implements Serializable {
	public static CourseCompetenceSoap toSoapModel(CourseCompetence model) {
		CourseCompetenceSoap soapModel = new CourseCompetenceSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCourcompetenceId(model.getCourcompetenceId());
		soapModel.setCourseId(model.getCourseId());
		soapModel.setCompetenceId(model.getCompetenceId());
		soapModel.setCondition(model.getCondition());

		return soapModel;
	}

	public static CourseCompetenceSoap[] toSoapModels(CourseCompetence[] models) {
		CourseCompetenceSoap[] soapModels = new CourseCompetenceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CourseCompetenceSoap[][] toSoapModels(
		CourseCompetence[][] models) {
		CourseCompetenceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CourseCompetenceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CourseCompetenceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CourseCompetenceSoap[] toSoapModels(
		List<CourseCompetence> models) {
		List<CourseCompetenceSoap> soapModels = new ArrayList<CourseCompetenceSoap>(models.size());

		for (CourseCompetence model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CourseCompetenceSoap[soapModels.size()]);
	}

	public CourseCompetenceSoap() {
	}

	public long getPrimaryKey() {
		return _CourcompetenceId;
	}

	public void setPrimaryKey(long pk) {
		setCourcompetenceId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCourcompetenceId() {
		return _CourcompetenceId;
	}

	public void setCourcompetenceId(long CourcompetenceId) {
		_CourcompetenceId = CourcompetenceId;
	}

	public long getCourseId() {
		return _courseId;
	}

	public void setCourseId(long courseId) {
		_courseId = courseId;
	}

	public long getCompetenceId() {
		return _competenceId;
	}

	public void setCompetenceId(long competenceId) {
		_competenceId = competenceId;
	}

	public boolean getCondition() {
		return _condition;
	}

	public boolean isCondition() {
		return _condition;
	}

	public void setCondition(boolean condition) {
		_condition = condition;
	}

	private String _uuid;
	private long _CourcompetenceId;
	private long _courseId;
	private long _competenceId;
	private boolean _condition;
}