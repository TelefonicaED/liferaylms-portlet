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

import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;

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
public class P2pActivityCorrectionsClp extends BaseModelImpl<P2pActivityCorrections>
	implements P2pActivityCorrections {
	public P2pActivityCorrectionsClp() {
	}

	public Class<?> getModelClass() {
		return P2pActivityCorrections.class;
	}

	public String getModelClassName() {
		return P2pActivityCorrections.class.getName();
	}

	public long getPrimaryKey() {
		return _p2pActivityCorrectionsId;
	}

	public void setPrimaryKey(long primaryKey) {
		setP2pActivityCorrectionsId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_p2pActivityCorrectionsId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("p2pActivityCorrectionsId", getP2pActivityCorrectionsId());
		attributes.put("p2pActivityId", getP2pActivityId());
		attributes.put("userId", getUserId());
		attributes.put("actId", getActId());
		attributes.put("description", getDescription());
		attributes.put("date", getDate());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("result", getResult());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long p2pActivityCorrectionsId = (Long)attributes.get(
				"p2pActivityCorrectionsId");

		if (p2pActivityCorrectionsId != null) {
			setP2pActivityCorrectionsId(p2pActivityCorrectionsId);
		}

		Long p2pActivityId = (Long)attributes.get("p2pActivityId");

		if (p2pActivityId != null) {
			setP2pActivityId(p2pActivityId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date date = (Date)attributes.get("date");

		if (date != null) {
			setDate(date);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Long result = (Long)attributes.get("result");

		if (result != null) {
			setResult(result);
		}
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getP2pActivityCorrectionsId() {
		return _p2pActivityCorrectionsId;
	}

	public void setP2pActivityCorrectionsId(long p2pActivityCorrectionsId) {
		_p2pActivityCorrectionsId = p2pActivityCorrectionsId;
	}

	public long getP2pActivityId() {
		return _p2pActivityId;
	}

	public void setP2pActivityId(long p2pActivityId) {
		_p2pActivityId = p2pActivityId;
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

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_actId = actId;
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

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public long getResult() {
		return _result;
	}

	public void setResult(long result) {
		_result = result;
	}

	public BaseModel<?> getP2pActivityCorrectionsRemoteModel() {
		return _p2pActivityCorrectionsRemoteModel;
	}

	public void setP2pActivityCorrectionsRemoteModel(
		BaseModel<?> p2pActivityCorrectionsRemoteModel) {
		_p2pActivityCorrectionsRemoteModel = p2pActivityCorrectionsRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			P2pActivityCorrectionsLocalServiceUtil.addP2pActivityCorrections(this);
		}
		else {
			P2pActivityCorrectionsLocalServiceUtil.updateP2pActivityCorrections(this);
		}
	}

	@Override
	public P2pActivityCorrections toEscapedModel() {
		return (P2pActivityCorrections)Proxy.newProxyInstance(P2pActivityCorrections.class.getClassLoader(),
			new Class[] { P2pActivityCorrections.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		P2pActivityCorrectionsClp clone = new P2pActivityCorrectionsClp();

		clone.setUuid(getUuid());
		clone.setP2pActivityCorrectionsId(getP2pActivityCorrectionsId());
		clone.setP2pActivityId(getP2pActivityId());
		clone.setUserId(getUserId());
		clone.setActId(getActId());
		clone.setDescription(getDescription());
		clone.setDate(getDate());
		clone.setFileEntryId(getFileEntryId());
		clone.setResult(getResult());

		return clone;
	}

	public int compareTo(P2pActivityCorrections p2pActivityCorrections) {
		long primaryKey = p2pActivityCorrections.getPrimaryKey();

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

		P2pActivityCorrectionsClp p2pActivityCorrections = null;

		try {
			p2pActivityCorrections = (P2pActivityCorrectionsClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = p2pActivityCorrections.getPrimaryKey();

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
		sb.append(", p2pActivityCorrectionsId=");
		sb.append(getP2pActivityCorrectionsId());
		sb.append(", p2pActivityId=");
		sb.append(getP2pActivityId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", actId=");
		sb.append(getActId());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", date=");
		sb.append(getDate());
		sb.append(", fileEntryId=");
		sb.append(getFileEntryId());
		sb.append(", result=");
		sb.append(getResult());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.P2pActivityCorrections");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>p2pActivityCorrectionsId</column-name><column-value><![CDATA[");
		sb.append(getP2pActivityCorrectionsId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>p2pActivityId</column-name><column-value><![CDATA[");
		sb.append(getP2pActivityId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actId</column-name><column-value><![CDATA[");
		sb.append(getActId());
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
			"<column><column-name>fileEntryId</column-name><column-value><![CDATA[");
		sb.append(getFileEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>result</column-name><column-value><![CDATA[");
		sb.append(getResult());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private long _p2pActivityCorrectionsId;
	private long _p2pActivityId;
	private long _userId;
	private String _userUuid;
	private long _actId;
	private String _description;
	private Date _date;
	private long _fileEntryId;
	private long _result;
	private BaseModel<?> _p2pActivityCorrectionsRemoteModel;
}