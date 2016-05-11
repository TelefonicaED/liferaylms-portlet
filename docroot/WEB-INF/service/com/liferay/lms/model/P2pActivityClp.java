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

import com.liferay.lms.service.P2pActivityLocalServiceUtil;

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
public class P2pActivityClp extends BaseModelImpl<P2pActivity>
	implements P2pActivity {
	public P2pActivityClp() {
	}

	public Class<?> getModelClass() {
		return P2pActivity.class;
	}

	public String getModelClassName() {
		return P2pActivity.class.getName();
	}

	public long getPrimaryKey() {
		return _p2pActivityId;
	}

	public void setPrimaryKey(long primaryKey) {
		setP2pActivityId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_p2pActivityId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("p2pActivityId", getP2pActivityId());
		attributes.put("actId", getActId());
		attributes.put("userId", getUserId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("countCorrections", getCountCorrections());
		attributes.put("description", getDescription());
		attributes.put("date", getDate());
		attributes.put("asignationsCompleted", getAsignationsCompleted());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long p2pActivityId = (Long)attributes.get("p2pActivityId");

		if (p2pActivityId != null) {
			setP2pActivityId(p2pActivityId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Long countCorrections = (Long)attributes.get("countCorrections");

		if (countCorrections != null) {
			setCountCorrections(countCorrections);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date date = (Date)attributes.get("date");

		if (date != null) {
			setDate(date);
		}

		Boolean asignationsCompleted = (Boolean)attributes.get(
				"asignationsCompleted");

		if (asignationsCompleted != null) {
			setAsignationsCompleted(asignationsCompleted);
		}
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getP2pActivityId() {
		return _p2pActivityId;
	}

	public void setP2pActivityId(long p2pActivityId) {
		_p2pActivityId = p2pActivityId;
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

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public long getCountCorrections() {
		return _countCorrections;
	}

	public void setCountCorrections(long countCorrections) {
		_countCorrections = countCorrections;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public Date getDate() {
		return _date;
	}

	public void setDate(Date date) {
		_date = date;
	}

	public boolean getAsignationsCompleted() {
		return _asignationsCompleted;
	}

	public boolean isAsignationsCompleted() {
		return _asignationsCompleted;
	}

	public void setAsignationsCompleted(boolean asignationsCompleted) {
		_asignationsCompleted = asignationsCompleted;
	}

	public BaseModel<?> getP2pActivityRemoteModel() {
		return _p2pActivityRemoteModel;
	}

	public void setP2pActivityRemoteModel(BaseModel<?> p2pActivityRemoteModel) {
		_p2pActivityRemoteModel = p2pActivityRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			P2pActivityLocalServiceUtil.addP2pActivity(this);
		}
		else {
			P2pActivityLocalServiceUtil.updateP2pActivity(this);
		}
	}

	@Override
	public P2pActivity toEscapedModel() {
		return (P2pActivity)Proxy.newProxyInstance(P2pActivity.class.getClassLoader(),
			new Class[] { P2pActivity.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		P2pActivityClp clone = new P2pActivityClp();

		clone.setUuid(getUuid());
		clone.setP2pActivityId(getP2pActivityId());
		clone.setActId(getActId());
		clone.setUserId(getUserId());
		clone.setFileEntryId(getFileEntryId());
		clone.setCountCorrections(getCountCorrections());
		clone.setDescription(getDescription());
		clone.setDate(getDate());
		clone.setAsignationsCompleted(getAsignationsCompleted());

		return clone;
	}

	public int compareTo(P2pActivity p2pActivity) {
		int value = 0;

		if (getCountCorrections() < p2pActivity.getCountCorrections()) {
			value = -1;
		}
		else if (getCountCorrections() > p2pActivity.getCountCorrections()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (getP2pActivityId() < p2pActivity.getP2pActivityId()) {
			value = -1;
		}
		else if (getP2pActivityId() > p2pActivity.getP2pActivityId()) {
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

		P2pActivityClp p2pActivity = null;

		try {
			p2pActivity = (P2pActivityClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = p2pActivity.getPrimaryKey();

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
		StringBundler sb = new StringBundler(19);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", p2pActivityId=");
		sb.append(getP2pActivityId());
		sb.append(", actId=");
		sb.append(getActId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", fileEntryId=");
		sb.append(getFileEntryId());
		sb.append(", countCorrections=");
		sb.append(getCountCorrections());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", date=");
		sb.append(getDate());
		sb.append(", asignationsCompleted=");
		sb.append(getAsignationsCompleted());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.P2pActivity");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>p2pActivityId</column-name><column-value><![CDATA[");
		sb.append(getP2pActivityId());
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
			"<column><column-name>fileEntryId</column-name><column-value><![CDATA[");
		sb.append(getFileEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>countCorrections</column-name><column-value><![CDATA[");
		sb.append(getCountCorrections());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>date</column-name><column-value><![CDATA[");
		sb.append(getDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>asignationsCompleted</column-name><column-value><![CDATA[");
		sb.append(getAsignationsCompleted());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private long _p2pActivityId;
	private long _actId;
	private long _userId;
	private String _userUuid;
	private long _fileEntryId;
	private long _countCorrections;
	private String _description;
	private Date _date;
	private boolean _asignationsCompleted;
	private BaseModel<?> _p2pActivityRemoteModel;
}