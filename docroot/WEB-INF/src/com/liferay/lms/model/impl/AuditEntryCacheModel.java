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

package com.liferay.lms.model.impl;

import com.liferay.lms.model.AuditEntry;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing AuditEntry in entity cache.
 *
 * @author TLS
 * @see AuditEntry
 * @generated
 */
public class AuditEntryCacheModel implements CacheModel<AuditEntry>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{auditId=");
		sb.append(auditId);
		sb.append(", auditDate=");
		sb.append(auditDate);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", classname=");
		sb.append(classname);
		sb.append(", action=");
		sb.append(action);
		sb.append(", extradata=");
		sb.append(extradata);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", associationClassPK=");
		sb.append(associationClassPK);
		sb.append("}");

		return sb.toString();
	}

	public AuditEntry toEntityModel() {
		AuditEntryImpl auditEntryImpl = new AuditEntryImpl();

		auditEntryImpl.setAuditId(auditId);

		if (auditDate == Long.MIN_VALUE) {
			auditEntryImpl.setAuditDate(null);
		}
		else {
			auditEntryImpl.setAuditDate(new Date(auditDate));
		}

		auditEntryImpl.setCompanyId(companyId);
		auditEntryImpl.setGroupId(groupId);
		auditEntryImpl.setUserId(userId);

		if (classname == null) {
			auditEntryImpl.setClassname(StringPool.BLANK);
		}
		else {
			auditEntryImpl.setClassname(classname);
		}

		if (action == null) {
			auditEntryImpl.setAction(StringPool.BLANK);
		}
		else {
			auditEntryImpl.setAction(action);
		}

		if (extradata == null) {
			auditEntryImpl.setExtradata(StringPool.BLANK);
		}
		else {
			auditEntryImpl.setExtradata(extradata);
		}

		auditEntryImpl.setClassPK(classPK);
		auditEntryImpl.setAssociationClassPK(associationClassPK);

		auditEntryImpl.resetOriginalValues();

		return auditEntryImpl;
	}

	public long auditId;
	public long auditDate;
	public long companyId;
	public long groupId;
	public long userId;
	public String classname;
	public String action;
	public String extradata;
	public long classPK;
	public long associationClassPK;
}