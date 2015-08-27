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

import com.liferay.lms.service.AuditEntryLocalServiceUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.DateUtil;
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
public class AuditEntryClp extends BaseModelImpl<AuditEntry>
	implements AuditEntry {
	public AuditEntryClp() {
	}

	public Class<?> getModelClass() {
		return AuditEntry.class;
	}

	public String getModelClassName() {
		return AuditEntry.class.getName();
	}

	public long getPrimaryKey() {
		return _auditId;
	}

	public void setPrimaryKey(long primaryKey) {
		setAuditId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_auditId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("auditId", getAuditId());
		attributes.put("auditDate", getAuditDate());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("classname", getClassname());
		attributes.put("action", getAction());
		attributes.put("extradata", getExtradata());
		attributes.put("classPK", getClassPK());
		attributes.put("associationClassPK", getAssociationClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long auditId = (Long)attributes.get("auditId");

		if (auditId != null) {
			setAuditId(auditId);
		}

		Date auditDate = (Date)attributes.get("auditDate");

		if (auditDate != null) {
			setAuditDate(auditDate);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String classname = (String)attributes.get("classname");

		if (classname != null) {
			setClassname(classname);
		}

		String action = (String)attributes.get("action");

		if (action != null) {
			setAction(action);
		}

		String extradata = (String)attributes.get("extradata");

		if (extradata != null) {
			setExtradata(extradata);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long associationClassPK = (Long)attributes.get("associationClassPK");

		if (associationClassPK != null) {
			setAssociationClassPK(associationClassPK);
		}
	}

	public long getAuditId() {
		return _auditId;
	}

	public void setAuditId(long auditId) {
		_auditId = auditId;
	}

	public Date getAuditDate() {
		return _auditDate;
	}

	public void setAuditDate(Date auditDate) {
		_auditDate = auditDate;
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

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public String getClassname() {
		return _classname;
	}

	public void setClassname(String classname) {
		_classname = classname;
	}

	public String getAction() {
		return _action;
	}

	public void setAction(String action) {
		_action = action;
	}

	public String getExtradata() {
		return _extradata;
	}

	public void setExtradata(String extradata) {
		_extradata = extradata;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getAssociationClassPK() {
		return _associationClassPK;
	}

	public void setAssociationClassPK(long associationClassPK) {
		_associationClassPK = associationClassPK;
	}

	public BaseModel<?> getAuditEntryRemoteModel() {
		return _auditEntryRemoteModel;
	}

	public void setAuditEntryRemoteModel(BaseModel<?> auditEntryRemoteModel) {
		_auditEntryRemoteModel = auditEntryRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			AuditEntryLocalServiceUtil.addAuditEntry(this);
		}
		else {
			AuditEntryLocalServiceUtil.updateAuditEntry(this);
		}
	}

	@Override
	public AuditEntry toEscapedModel() {
		return (AuditEntry)Proxy.newProxyInstance(AuditEntry.class.getClassLoader(),
			new Class[] { AuditEntry.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		AuditEntryClp clone = new AuditEntryClp();

		clone.setAuditId(getAuditId());
		clone.setAuditDate(getAuditDate());
		clone.setCompanyId(getCompanyId());
		clone.setGroupId(getGroupId());
		clone.setUserId(getUserId());
		clone.setClassname(getClassname());
		clone.setAction(getAction());
		clone.setExtradata(getExtradata());
		clone.setClassPK(getClassPK());
		clone.setAssociationClassPK(getAssociationClassPK());

		return clone;
	}

	public int compareTo(AuditEntry auditEntry) {
		int value = 0;

		value = DateUtil.compareTo(getAuditDate(), auditEntry.getAuditDate());

		value = value * -1;

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

		AuditEntryClp auditEntry = null;

		try {
			auditEntry = (AuditEntryClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = auditEntry.getPrimaryKey();

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

		sb.append("{auditId=");
		sb.append(getAuditId());
		sb.append(", auditDate=");
		sb.append(getAuditDate());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", classname=");
		sb.append(getClassname());
		sb.append(", action=");
		sb.append(getAction());
		sb.append(", extradata=");
		sb.append(getExtradata());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", associationClassPK=");
		sb.append(getAssociationClassPK());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.AuditEntry");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>auditId</column-name><column-value><![CDATA[");
		sb.append(getAuditId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>auditDate</column-name><column-value><![CDATA[");
		sb.append(getAuditDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classname</column-name><column-value><![CDATA[");
		sb.append(getClassname());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>action</column-name><column-value><![CDATA[");
		sb.append(getAction());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>extradata</column-name><column-value><![CDATA[");
		sb.append(getExtradata());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>associationClassPK</column-name><column-value><![CDATA[");
		sb.append(getAssociationClassPK());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _auditId;
	private Date _auditDate;
	private long _companyId;
	private long _groupId;
	private long _userId;
	private String _userUuid;
	private String _classname;
	private String _action;
	private String _extradata;
	private long _classPK;
	private long _associationClassPK;
	private BaseModel<?> _auditEntryRemoteModel;
}