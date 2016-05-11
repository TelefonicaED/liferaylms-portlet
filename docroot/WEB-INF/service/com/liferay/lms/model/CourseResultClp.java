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

import com.liferay.lms.service.CourseResultLocalServiceUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TLS
 */
public class CourseResultClp extends BaseModelImpl<CourseResult>
	implements CourseResult {
	public CourseResultClp() {
	}

	public Class<?> getModelClass() {
		return CourseResult.class;
	}

	public String getModelClassName() {
		return CourseResult.class.getName();
	}

	public long getPrimaryKey() {
		return _crId;
	}

	public void setPrimaryKey(long primaryKey) {
		setCrId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_crId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("crId", getCrId());
		attributes.put("courseId", getCourseId());
		attributes.put("result", getResult());
		attributes.put("comments", getComments());
		attributes.put("userId", getUserId());
		attributes.put("passed", getPassed());
		attributes.put("startDate", getStartDate());
		attributes.put("passedDate", getPassedDate());
		attributes.put("allowStartDate", getAllowStartDate());
		attributes.put("allowFinishDate", getAllowFinishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long crId = (Long)attributes.get("crId");

		if (crId != null) {
			setCrId(crId);
		}

		Long courseId = (Long)attributes.get("courseId");

		if (courseId != null) {
			setCourseId(courseId);
		}

		Long result = (Long)attributes.get("result");

		if (result != null) {
			setResult(result);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Boolean passed = (Boolean)attributes.get("passed");

		if (passed != null) {
			setPassed(passed);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date passedDate = (Date)attributes.get("passedDate");

		if (passedDate != null) {
			setPassedDate(passedDate);
		}

		Date allowStartDate = (Date)attributes.get("allowStartDate");

		if (allowStartDate != null) {
			setAllowStartDate(allowStartDate);
		}

		Date allowFinishDate = (Date)attributes.get("allowFinishDate");

		if (allowFinishDate != null) {
			setAllowFinishDate(allowFinishDate);
		}
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

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
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

	public BaseModel<?> getCourseResultRemoteModel() {
		return _courseResultRemoteModel;
	}

	public void setCourseResultRemoteModel(BaseModel<?> courseResultRemoteModel) {
		_courseResultRemoteModel = courseResultRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			CourseResultLocalServiceUtil.addCourseResult(this);
		}
		else {
			CourseResultLocalServiceUtil.updateCourseResult(this);
		}
	}

	@Override
	public CourseResult toEscapedModel() {
		return (CourseResult)Proxy.newProxyInstance(CourseResult.class.getClassLoader(),
			new Class[] { CourseResult.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		CourseResultClp clone = new CourseResultClp();

		clone.setCrId(getCrId());
		clone.setCourseId(getCourseId());
		clone.setResult(getResult());
		clone.setComments(getComments());
		clone.setUserId(getUserId());
		clone.setPassed(getPassed());
		clone.setStartDate(getStartDate());
		clone.setPassedDate(getPassedDate());
		clone.setAllowStartDate(getAllowStartDate());
		clone.setAllowFinishDate(getAllowFinishDate());

		return clone;
	}

	public int compareTo(CourseResult courseResult) {
		long primaryKey = courseResult.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		CourseResultClp courseResult = null;

		try {
			courseResult = (CourseResultClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = courseResult.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{crId=");
		sb.append(getCrId());
		sb.append(", courseId=");
		sb.append(getCourseId());
		sb.append(", result=");
		sb.append(getResult());
		sb.append(", comments=");
		sb.append(getComments());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", passed=");
		sb.append(getPassed());
		sb.append(", startDate=");
		sb.append(getStartDate());
		sb.append(", passedDate=");
		sb.append(getPassedDate());
		sb.append(", allowStartDate=");
		sb.append(getAllowStartDate());
		sb.append(", allowFinishDate=");
		sb.append(getAllowFinishDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.CourseResult");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>crId</column-name><column-value><![CDATA[");
		sb.append(getCrId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>courseId</column-name><column-value><![CDATA[");
		sb.append(getCourseId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>result</column-name><column-value><![CDATA[");
		sb.append(getResult());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>comments</column-name><column-value><![CDATA[");
		sb.append(getComments());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>passed</column-name><column-value><![CDATA[");
		sb.append(getPassed());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>startDate</column-name><column-value><![CDATA[");
		sb.append(getStartDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>passedDate</column-name><column-value><![CDATA[");
		sb.append(getPassedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>allowStartDate</column-name><column-value><![CDATA[");
		sb.append(getAllowStartDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>allowFinishDate</column-name><column-value><![CDATA[");
		sb.append(getAllowFinishDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _crId;
	private long _courseId;
	private long _result;
	private String _comments;
	private long _userId;
	private String _userUuid;
	private boolean _passed;
	private Date _startDate;
	private Date _passedDate;
	private Date _allowStartDate;
	private Date _allowFinishDate;
	private BaseModel<?> _courseResultRemoteModel;
}