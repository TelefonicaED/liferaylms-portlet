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
 * This class is used by SOAP remote services.
 *
 * @author    TLS
 * @generated
 */
public class AuditEntrySoap implements Serializable {
	public static AuditEntrySoap toSoapModel(AuditEntry model) {
		AuditEntrySoap soapModel = new AuditEntrySoap();

		soapModel.setAuditId(model.getAuditId());
		soapModel.setAuditDate(model.getAuditDate());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setClassname(model.getClassname());
		soapModel.setAction(model.getAction());
		soapModel.setExtradata(model.getExtradata());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setAssociationClassPK(model.getAssociationClassPK());

		return soapModel;
	}

	public static AuditEntrySoap[] toSoapModels(AuditEntry[] models) {
		AuditEntrySoap[] soapModels = new AuditEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AuditEntrySoap[][] toSoapModels(AuditEntry[][] models) {
		AuditEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AuditEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new AuditEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AuditEntrySoap[] toSoapModels(List<AuditEntry> models) {
		List<AuditEntrySoap> soapModels = new ArrayList<AuditEntrySoap>(models.size());

		for (AuditEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AuditEntrySoap[soapModels.size()]);
	}

	public AuditEntrySoap() {
	}

	public long getPrimaryKey() {
		return _auditId;
	}

	public void setPrimaryKey(long pk) {
		setAuditId(pk);
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

	private long _auditId;
	private Date _auditDate;
	private long _companyId;
	private long _groupId;
	private long _userId;
	private String _classname;
	private String _action;
	private String _extradata;
	private long _classPK;
	private long _associationClassPK;
}