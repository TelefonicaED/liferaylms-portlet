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

import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;

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
public class LearningActivityResultClp extends BaseModelImpl<LearningActivityResult>
	implements LearningActivityResult {
	public LearningActivityResultClp() {
	}

	public Class<?> getModelClass() {
		return LearningActivityResult.class;
	}

	public String getModelClassName() {
		return LearningActivityResult.class.getName();
	}

	public long getPrimaryKey() {
		return _larId;
	}

	public void setPrimaryKey(long primaryKey) {
		setLarId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_larId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("larId", getLarId());
		attributes.put("actId", getActId());
		attributes.put("userId", getUserId());
		attributes.put("result", getResult());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("latId", getLatId());
		attributes.put("comments", getComments());
		attributes.put("passed", getPassed());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long larId = (Long)attributes.get("larId");

		if (larId != null) {
			setLarId(larId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long result = (Long)attributes.get("result");

		if (result != null) {
			setResult(result);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		Long latId = (Long)attributes.get("latId");

		if (latId != null) {
			setLatId(latId);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}

		Boolean passed = (Boolean)attributes.get("passed");

		if (passed != null) {
			setPassed(passed);
		}
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLarId() {
		return _larId;
	}

	public void setLarId(long larId) {
		_larId = larId;
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

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getResult() {
		return _result;
	}

	public void setResult(long result) {
		_result = result;
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

	public long getLatId() {
		return _latId;
	}

	public void setLatId(long latId) {
		_latId = latId;
	}

	public String getComments() {
		return _comments;
	}

	public void setComments(String comments) {
		_comments = comments;
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

	public BaseModel<?> getLearningActivityResultRemoteModel() {
		return _learningActivityResultRemoteModel;
	}

	public void setLearningActivityResultRemoteModel(
		BaseModel<?> learningActivityResultRemoteModel) {
		_learningActivityResultRemoteModel = learningActivityResultRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			LearningActivityResultLocalServiceUtil.addLearningActivityResult(this);
		}
		else {
			LearningActivityResultLocalServiceUtil.updateLearningActivityResult(this);
		}
	}

	@Override
	public LearningActivityResult toEscapedModel() {
		return (LearningActivityResult)Proxy.newProxyInstance(LearningActivityResult.class.getClassLoader(),
			new Class[] { LearningActivityResult.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		LearningActivityResultClp clone = new LearningActivityResultClp();

		clone.setUuid(getUuid());
		clone.setLarId(getLarId());
		clone.setActId(getActId());
		clone.setUserId(getUserId());
		clone.setResult(getResult());
		clone.setStartDate(getStartDate());
		clone.setEndDate(getEndDate());
		clone.setLatId(getLatId());
		clone.setComments(getComments());
		clone.setPassed(getPassed());

		return clone;
	}

	public int compareTo(LearningActivityResult learningActivityResult) {
		int value = 0;

		if (getLarId() < learningActivityResult.getLarId()) {
			value = -1;
		}
		else if (getLarId() > learningActivityResult.getLarId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		LearningActivityResultClp learningActivityResult = null;

		try {
			learningActivityResult = (LearningActivityResultClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = learningActivityResult.getPrimaryKey();

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

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", larId=");
		sb.append(getLarId());
		sb.append(", actId=");
		sb.append(getActId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", result=");
		sb.append(getResult());
		sb.append(", startDate=");
		sb.append(getStartDate());
		sb.append(", endDate=");
		sb.append(getEndDate());
		sb.append(", latId=");
		sb.append(getLatId());
		sb.append(", comments=");
		sb.append(getComments());
		sb.append(", passed=");
		sb.append(getPassed());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.LearningActivityResult");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>larId</column-name><column-value><![CDATA[");
		sb.append(getLarId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actId</column-name><column-value><![CDATA[");
		sb.append(getActId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>result</column-name><column-value><![CDATA[");
		sb.append(getResult());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>startDate</column-name><column-value><![CDATA[");
		sb.append(getStartDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>endDate</column-name><column-value><![CDATA[");
		sb.append(getEndDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>latId</column-name><column-value><![CDATA[");
		sb.append(getLatId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>comments</column-name><column-value><![CDATA[");
		sb.append(getComments());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>passed</column-name><column-value><![CDATA[");
		sb.append(getPassed());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private long _larId;
	private long _actId;
	private long _userId;
	private String _userUuid;
	private long _result;
	private Date _startDate;
	private Date _endDate;
	private long _latId;
	private String _comments;
	private boolean _passed;
	private BaseModel<?> _learningActivityResultRemoteModel;
}