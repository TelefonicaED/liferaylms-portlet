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

import com.liferay.lms.model.CourseType;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing CourseType in entity cache.
 *
 * @author TLS
 * @see CourseType
 * @generated
 */
public class CourseTypeCacheModel implements CacheModel<CourseType>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{courseTypeId=");
		sb.append(courseTypeId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", iconId=");
		sb.append(iconId);
		sb.append("}");

		return sb.toString();
	}

	public CourseType toEntityModel() {
		CourseTypeImpl courseTypeImpl = new CourseTypeImpl();

		courseTypeImpl.setCourseTypeId(courseTypeId);
		courseTypeImpl.setCompanyId(companyId);
		courseTypeImpl.setUserId(userId);
		courseTypeImpl.setGroupId(groupId);

		if (userName == null) {
			courseTypeImpl.setUserName(StringPool.BLANK);
		}
		else {
			courseTypeImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			courseTypeImpl.setCreateDate(null);
		}
		else {
			courseTypeImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			courseTypeImpl.setModifiedDate(null);
		}
		else {
			courseTypeImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			courseTypeImpl.setName(StringPool.BLANK);
		}
		else {
			courseTypeImpl.setName(name);
		}

		if (description == null) {
			courseTypeImpl.setDescription(StringPool.BLANK);
		}
		else {
			courseTypeImpl.setDescription(description);
		}

		courseTypeImpl.setIconId(iconId);

		courseTypeImpl.resetOriginalValues();

		return courseTypeImpl;
	}

	public long courseTypeId;
	public long companyId;
	public long userId;
	public long groupId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public long iconId;
}