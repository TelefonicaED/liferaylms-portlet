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

import com.liferay.lms.model.AsynchronousProcessAudit;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing AsynchronousProcessAudit in entity cache.
 *
 * @author TLS
 * @see AsynchronousProcessAudit
 * @generated
 */
public class AsynchronousProcessAuditCacheModel implements CacheModel<AsynchronousProcessAudit>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{asynchronousProcessAuditId=");
		sb.append(asynchronousProcessAuditId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", type=");
		sb.append(type);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusMessage=");
		sb.append(statusMessage);
		sb.append(", extraContent=");
		sb.append(extraContent);
		sb.append("}");

		return sb.toString();
	}

	public AsynchronousProcessAudit toEntityModel() {
		AsynchronousProcessAuditImpl asynchronousProcessAuditImpl = new AsynchronousProcessAuditImpl();

		asynchronousProcessAuditImpl.setAsynchronousProcessAuditId(asynchronousProcessAuditId);
		asynchronousProcessAuditImpl.setCompanyId(companyId);

		if (type == null) {
			asynchronousProcessAuditImpl.setType(StringPool.BLANK);
		}
		else {
			asynchronousProcessAuditImpl.setType(type);
		}

		asynchronousProcessAuditImpl.setClassNameId(classNameId);
		asynchronousProcessAuditImpl.setClassPK(classPK);
		asynchronousProcessAuditImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			asynchronousProcessAuditImpl.setCreateDate(null);
		}
		else {
			asynchronousProcessAuditImpl.setCreateDate(new Date(createDate));
		}

		if (endDate == Long.MIN_VALUE) {
			asynchronousProcessAuditImpl.setEndDate(null);
		}
		else {
			asynchronousProcessAuditImpl.setEndDate(new Date(endDate));
		}

		asynchronousProcessAuditImpl.setStatus(status);

		if (statusMessage == null) {
			asynchronousProcessAuditImpl.setStatusMessage(StringPool.BLANK);
		}
		else {
			asynchronousProcessAuditImpl.setStatusMessage(statusMessage);
		}

		if (extraContent == null) {
			asynchronousProcessAuditImpl.setExtraContent(StringPool.BLANK);
		}
		else {
			asynchronousProcessAuditImpl.setExtraContent(extraContent);
		}

		asynchronousProcessAuditImpl.resetOriginalValues();

		return asynchronousProcessAuditImpl;
	}

	public long asynchronousProcessAuditId;
	public long companyId;
	public String type;
	public long classNameId;
	public long classPK;
	public long userId;
	public long createDate;
	public long endDate;
	public int status;
	public String statusMessage;
	public String extraContent;
}