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

import com.liferay.lms.service.UserCompetenceLocalServiceUtil;

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
public class UserCompetenceClp extends BaseModelImpl<UserCompetence>
	implements UserCompetence {
	public UserCompetenceClp() {
	}

	public Class<?> getModelClass() {
		return UserCompetence.class;
	}

	public String getModelClassName() {
		return UserCompetence.class.getName();
	}

	public long getPrimaryKey() {
		return _usercompId;
	}

	public void setPrimaryKey(long primaryKey) {
		setUsercompId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_usercompId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("usercompId", getUsercompId());
		attributes.put("userId", getUserId());
		attributes.put("competenceId", getCompetenceId());
		attributes.put("compDate", getCompDate());
		attributes.put("courseId", getCourseId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long usercompId = (Long)attributes.get("usercompId");

		if (usercompId != null) {
			setUsercompId(usercompId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long competenceId = (Long)attributes.get("competenceId");

		if (competenceId != null) {
			setCompetenceId(competenceId);
		}

		Date compDate = (Date)attributes.get("compDate");

		if (compDate != null) {
			setCompDate(compDate);
		}

		Long courseId = (Long)attributes.get("courseId");

		if (courseId != null) {
			setCourseId(courseId);
		}
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getUsercompId() {
		return _usercompId;
	}

	public void setUsercompId(long usercompId) {
		_usercompId = usercompId;
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

	public long getCompetenceId() {
		return _competenceId;
	}

	public void setCompetenceId(long competenceId) {
		_competenceId = competenceId;
	}

	public Date getCompDate() {
		return _compDate;
	}

	public void setCompDate(Date compDate) {
		_compDate = compDate;
	}

	public long getCourseId() {
		return _courseId;
	}

	public void setCourseId(long courseId) {
		_courseId = courseId;
	}

	public BaseModel<?> getUserCompetenceRemoteModel() {
		return _userCompetenceRemoteModel;
	}

	public void setUserCompetenceRemoteModel(
		BaseModel<?> userCompetenceRemoteModel) {
		_userCompetenceRemoteModel = userCompetenceRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			UserCompetenceLocalServiceUtil.addUserCompetence(this);
		}
		else {
			UserCompetenceLocalServiceUtil.updateUserCompetence(this);
		}
	}

	@Override
	public UserCompetence toEscapedModel() {
		return (UserCompetence)Proxy.newProxyInstance(UserCompetence.class.getClassLoader(),
			new Class[] { UserCompetence.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		UserCompetenceClp clone = new UserCompetenceClp();

		clone.setUuid(getUuid());
		clone.setUsercompId(getUsercompId());
		clone.setUserId(getUserId());
		clone.setCompetenceId(getCompetenceId());
		clone.setCompDate(getCompDate());
		clone.setCourseId(getCourseId());

		return clone;
	}

	public int compareTo(UserCompetence userCompetence) {
		long primaryKey = userCompetence.getPrimaryKey();

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

		UserCompetenceClp userCompetence = null;

		try {
			userCompetence = (UserCompetenceClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = userCompetence.getPrimaryKey();

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
		StringBundler sb = new StringBundler(13);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", usercompId=");
		sb.append(getUsercompId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", competenceId=");
		sb.append(getCompetenceId());
		sb.append(", compDate=");
		sb.append(getCompDate());
		sb.append(", courseId=");
		sb.append(getCourseId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.UserCompetence");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>usercompId</column-name><column-value><![CDATA[");
		sb.append(getUsercompId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>competenceId</column-name><column-value><![CDATA[");
		sb.append(getCompetenceId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>compDate</column-name><column-value><![CDATA[");
		sb.append(getCompDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>courseId</column-name><column-value><![CDATA[");
		sb.append(getCourseId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private long _usercompId;
	private long _userId;
	private String _userUuid;
	private long _competenceId;
	private Date _compDate;
	private long _courseId;
	private BaseModel<?> _userCompetenceRemoteModel;
}