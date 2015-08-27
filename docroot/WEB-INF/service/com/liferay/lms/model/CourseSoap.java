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
 * This class is used by SOAP remote services, specifically {@link com.liferay.lms.service.http.CourseServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lms.service.http.CourseServiceSoap
 * @generated
 */
public class CourseSoap implements Serializable {
	public static CourseSoap toSoapModel(Course model) {
		CourseSoap soapModel = new CourseSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCourseId(model.getCourseId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setGroupCreatedId(model.getGroupCreatedId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setFriendlyURL(model.getFriendlyURL());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setEndDate(model.getEndDate());
		soapModel.setIcon(model.getIcon());
		soapModel.setCourseEvalId(model.getCourseEvalId());
		soapModel.setCourseExtraData(model.getCourseExtraData());
		soapModel.setClosed(model.getClosed());
		soapModel.setMaxusers(model.getMaxusers());
		soapModel.setCalificationType(model.getCalificationType());
		soapModel.setWelcome(model.getWelcome());
		soapModel.setWelcomeMsg(model.getWelcomeMsg());
		soapModel.setWelcomeSubject(model.getWelcomeSubject());

		return soapModel;
	}

	public static CourseSoap[] toSoapModels(Course[] models) {
		CourseSoap[] soapModels = new CourseSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CourseSoap[][] toSoapModels(Course[][] models) {
		CourseSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CourseSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CourseSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CourseSoap[] toSoapModels(List<Course> models) {
		List<CourseSoap> soapModels = new ArrayList<CourseSoap>(models.size());

		for (Course model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CourseSoap[soapModels.size()]);
	}

	public CourseSoap() {
	}

	public long getPrimaryKey() {
		return _courseId;
	}

	public void setPrimaryKey(long pk) {
		setCourseId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCourseId() {
		return _courseId;
	}

	public void setCourseId(long courseId) {
		_courseId = courseId;
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

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public long getGroupCreatedId() {
		return _groupCreatedId;
	}

	public void setGroupCreatedId(long groupCreatedId) {
		_groupCreatedId = groupCreatedId;
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

	public String getFriendlyURL() {
		return _friendlyURL;
	}

	public void setFriendlyURL(String friendlyURL) {
		_friendlyURL = friendlyURL;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public long getIcon() {
		return _icon;
	}

	public void setIcon(long icon) {
		_icon = icon;
	}

	public long getCourseEvalId() {
		return _CourseEvalId;
	}

	public void setCourseEvalId(long CourseEvalId) {
		_CourseEvalId = CourseEvalId;
	}

	public String getCourseExtraData() {
		return _CourseExtraData;
	}

	public void setCourseExtraData(String CourseExtraData) {
		_CourseExtraData = CourseExtraData;
	}

	public boolean getClosed() {
		return _closed;
	}

	public boolean isClosed() {
		return _closed;
	}

	public void setClosed(boolean closed) {
		_closed = closed;
	}

	public long getMaxusers() {
		return _maxusers;
	}

	public void setMaxusers(long maxusers) {
		_maxusers = maxusers;
	}

	public long getCalificationType() {
		return _calificationType;
	}

	public void setCalificationType(long calificationType) {
		_calificationType = calificationType;
	}

	public boolean getWelcome() {
		return _welcome;
	}

	public boolean isWelcome() {
		return _welcome;
	}

	public void setWelcome(boolean welcome) {
		_welcome = welcome;
	}

	public String getWelcomeMsg() {
		return _welcomeMsg;
	}

	public void setWelcomeMsg(String welcomeMsg) {
		_welcomeMsg = welcomeMsg;
	}

	public String getWelcomeSubject() {
		return _welcomeSubject;
	}

	public void setWelcomeSubject(String welcomeSubject) {
		_welcomeSubject = welcomeSubject;
	}

	private String _uuid;
	private long _courseId;
	private long _companyId;
	private long _groupId;
	private long _userId;
	private String _userName;
	private long _groupCreatedId;
	private Date _createDate;
	private Date _modifiedDate;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
	private String _title;
	private String _description;
	private String _friendlyURL;
	private Date _startDate;
	private Date _endDate;
	private long _icon;
	private long _CourseEvalId;
	private String _CourseExtraData;
	private boolean _closed;
	private long _maxusers;
	private long _calificationType;
	private boolean _welcome;
	private String _welcomeMsg;
	private String _welcomeSubject;
}