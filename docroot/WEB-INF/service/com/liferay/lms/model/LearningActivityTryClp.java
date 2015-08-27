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

import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;

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
public class LearningActivityTryClp extends BaseModelImpl<LearningActivityTry>
	implements LearningActivityTry {
	public LearningActivityTryClp() {
	}

	public Class<?> getModelClass() {
		return LearningActivityTry.class;
	}

	public String getModelClassName() {
		return LearningActivityTry.class.getName();
	}

	public long getPrimaryKey() {
		return _latId;
	}

	public void setPrimaryKey(long primaryKey) {
		setLatId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_latId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("latId", getLatId());
		attributes.put("actId", getActId());
		attributes.put("userId", getUserId());
		attributes.put("startDate", getStartDate());
		attributes.put("result", getResult());
		attributes.put("endDate", getEndDate());
		attributes.put("tryData", getTryData());
		attributes.put("tryResultData", getTryResultData());
		attributes.put("comments", getComments());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long latId = (Long)attributes.get("latId");

		if (latId != null) {
			setLatId(latId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Long result = (Long)attributes.get("result");

		if (result != null) {
			setResult(result);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		String tryData = (String)attributes.get("tryData");

		if (tryData != null) {
			setTryData(tryData);
		}

		String tryResultData = (String)attributes.get("tryResultData");

		if (tryResultData != null) {
			setTryResultData(tryResultData);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLatId() {
		return _latId;
	}

	public void setLatId(long latId) {
		_latId = latId;
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

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public long getResult() {
		return _result;
	}

	public void setResult(long result) {
		_result = result;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public String getTryData() {
		return _tryData;
	}

	public void setTryData(String tryData) {
		_tryData = tryData;
	}

	public String getTryResultData() {
		return _tryResultData;
	}

	public void setTryResultData(String tryResultData) {
		_tryResultData = tryResultData;
	}

	public String getComments() {
		return _comments;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public BaseModel<?> getLearningActivityTryRemoteModel() {
		return _learningActivityTryRemoteModel;
	}

	public void setLearningActivityTryRemoteModel(
		BaseModel<?> learningActivityTryRemoteModel) {
		_learningActivityTryRemoteModel = learningActivityTryRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			LearningActivityTryLocalServiceUtil.addLearningActivityTry(this);
		}
		else {
			LearningActivityTryLocalServiceUtil.updateLearningActivityTry(this);
		}
	}

	@Override
	public LearningActivityTry toEscapedModel() {
		return (LearningActivityTry)Proxy.newProxyInstance(LearningActivityTry.class.getClassLoader(),
			new Class[] { LearningActivityTry.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		LearningActivityTryClp clone = new LearningActivityTryClp();

		clone.setUuid(getUuid());
		clone.setLatId(getLatId());
		clone.setActId(getActId());
		clone.setUserId(getUserId());
		clone.setStartDate(getStartDate());
		clone.setResult(getResult());
		clone.setEndDate(getEndDate());
		clone.setTryData(getTryData());
		clone.setTryResultData(getTryResultData());
		clone.setComments(getComments());

		return clone;
	}

	public int compareTo(LearningActivityTry learningActivityTry) {
		int value = 0;

		if (getLatId() < learningActivityTry.getLatId()) {
			value = -1;
		}
		else if (getLatId() > learningActivityTry.getLatId()) {
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

		LearningActivityTryClp learningActivityTry = null;

		try {
			learningActivityTry = (LearningActivityTryClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = learningActivityTry.getPrimaryKey();

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
		sb.append(", latId=");
		sb.append(getLatId());
		sb.append(", actId=");
		sb.append(getActId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", startDate=");
		sb.append(getStartDate());
		sb.append(", result=");
		sb.append(getResult());
		sb.append(", endDate=");
		sb.append(getEndDate());
		sb.append(", tryData=");
		sb.append(getTryData());
		sb.append(", tryResultData=");
		sb.append(getTryResultData());
		sb.append(", comments=");
		sb.append(getComments());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.LearningActivityTry");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>latId</column-name><column-value><![CDATA[");
		sb.append(getLatId());
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
			"<column><column-name>startDate</column-name><column-value><![CDATA[");
		sb.append(getStartDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>result</column-name><column-value><![CDATA[");
		sb.append(getResult());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>endDate</column-name><column-value><![CDATA[");
		sb.append(getEndDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>tryData</column-name><column-value><![CDATA[");
		sb.append(getTryData());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>tryResultData</column-name><column-value><![CDATA[");
		sb.append(getTryResultData());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>comments</column-name><column-value><![CDATA[");
		sb.append(getComments());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private long _latId;
	private long _actId;
	private long _userId;
	private String _userUuid;
	private Date _startDate;
	private long _result;
	private Date _endDate;
	private String _tryData;
	private String _tryResultData;
	private String _comments;
	private BaseModel<?> _learningActivityTryRemoteModel;
}