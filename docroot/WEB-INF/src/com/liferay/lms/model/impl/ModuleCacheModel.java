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

import com.liferay.lms.model.Module;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing Module in entity cache.
 *
 * @author TLS
 * @see Module
 * @generated
 */
public class ModuleCacheModel implements CacheModel<Module>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", moduleId=");
		sb.append(moduleId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", ordern=");
		sb.append(ordern);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", icon=");
		sb.append(icon);
		sb.append(", precedence=");
		sb.append(precedence);
		sb.append("}");

		return sb.toString();
	}

	public Module toEntityModel() {
		ModuleImpl moduleImpl = new ModuleImpl();

		if (uuid == null) {
			moduleImpl.setUuid(StringPool.BLANK);
		}
		else {
			moduleImpl.setUuid(uuid);
		}

		moduleImpl.setModuleId(moduleId);
		moduleImpl.setCompanyId(companyId);
		moduleImpl.setGroupId(groupId);
		moduleImpl.setUserId(userId);

		if (userName == null) {
			moduleImpl.setUserName(StringPool.BLANK);
		}
		else {
			moduleImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			moduleImpl.setCreateDate(null);
		}
		else {
			moduleImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			moduleImpl.setModifiedDate(null);
		}
		else {
			moduleImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (title == null) {
			moduleImpl.setTitle(StringPool.BLANK);
		}
		else {
			moduleImpl.setTitle(title);
		}

		if (description == null) {
			moduleImpl.setDescription(StringPool.BLANK);
		}
		else {
			moduleImpl.setDescription(description);
		}

		moduleImpl.setOrdern(ordern);

		if (startDate == Long.MIN_VALUE) {
			moduleImpl.setStartDate(null);
		}
		else {
			moduleImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			moduleImpl.setEndDate(null);
		}
		else {
			moduleImpl.setEndDate(new Date(endDate));
		}

		moduleImpl.setIcon(icon);
		moduleImpl.setPrecedence(precedence);

		moduleImpl.resetOriginalValues();

		return moduleImpl;
	}

	public String uuid;
	public long moduleId;
	public long companyId;
	public long groupId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String title;
	public String description;
	public long ordern;
	public long startDate;
	public long endDate;
	public long icon;
	public long precedence;
}