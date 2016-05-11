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
 * This class is used by SOAP remote services, specifically {@link com.liferay.lms.service.http.CourseResultServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lms.service.http.CourseResultServiceSoap
 * @generated
 */
public class CourseResultSoap implements Serializable {
	public static CourseResultSoap toSoapModel(CourseResult model) {
		CourseResultSoap soapModel = new CourseResultSoap();

		soapModel.setCrId(model.getCrId());
		soapModel.setCourseId(model.getCourseId());
		soapModel.setResult(model.getResult());
		soapModel.setComments(model.getComments());
		soapModel.setUserId(model.getUserId());
		soapModel.setPassed(model.getPassed());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setPassedDate(model.getPassedDate());
		soapModel.setAllowStartDate(model.getAllowStartDate());
		soapModel.setAllowFinishDate(model.getAllowFinishDate());

		return soapModel;
	}

	public static CourseResultSoap[] toSoapModels(CourseResult[] models) {
		CourseResultSoap[] soapModels = new CourseResultSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CourseResultSoap[][] toSoapModels(CourseResult[][] models) {
		CourseResultSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CourseResultSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CourseResultSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CourseResultSoap[] toSoapModels(List<CourseResult> models) {
		List<CourseResultSoap> soapModels = new ArrayList<CourseResultSoap>(models.size());

		for (CourseResult model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CourseResultSoap[soapModels.size()]);
	}

	public CourseResultSoap() {
	}

	public long getPrimaryKey() {
		return _crId;
	}

	public void setPrimaryKey(long pk) {
		setCrId(pk);
	}

	public long getCrId() {
		return _crId;
	}

	public void setCrId(long crId) {
		_crId = crId;
	}

	public long getCourseId() {
		return _courseId;
	}

	public void setCourseId(long courseId) {
		_courseId = courseId;
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

	public boolean getPassed() {
		return _passed;
	}

	public boolean isPassed() {
		return _passed;
	}

	public void setPassed(boolean passed) {
		_passed = passed;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getPassedDate() {
		return _passedDate;
	}

	public void setPassedDate(Date passedDate) {
		_passedDate = passedDate;
	}

	public Date getAllowStartDate() {
		return _allowStartDate;
	}

	public void setAllowStartDate(Date allowStartDate) {
		_allowStartDate = allowStartDate;
	}

	public Date getAllowFinishDate() {
		return _allowFinishDate;
	}

	public void setAllowFinishDate(Date allowFinishDate) {
		_allowFinishDate = allowFinishDate;
	}

	private long _crId;
	private long _courseId;
	private long _result;
	private String _comments;
	private long _userId;
	private boolean _passed;
	private Date _startDate;
	private Date _passedDate;
	private Date _allowStartDate;
	private Date _allowFinishDate;
}