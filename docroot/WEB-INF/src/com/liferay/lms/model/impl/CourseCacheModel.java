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

import com.liferay.lms.model.Course;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing Course in entity cache.
 *
 * @author TLS
 * @see Course
 * @generated
 */
public class CourseCacheModel implements CacheModel<Course>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(55);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", courseId=");
		sb.append(courseId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", groupCreatedId=");
		sb.append(groupCreatedId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", friendlyURL=");
		sb.append(friendlyURL);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", icon=");
		sb.append(icon);
		sb.append(", CourseEvalId=");
		sb.append(CourseEvalId);
		sb.append(", CourseExtraData=");
		sb.append(CourseExtraData);
		sb.append(", closed=");
		sb.append(closed);
		sb.append(", maxusers=");
		sb.append(maxusers);
		sb.append(", calificationType=");
		sb.append(calificationType);
		sb.append(", welcome=");
		sb.append(welcome);
		sb.append(", welcomeMsg=");
		sb.append(welcomeMsg);
		sb.append(", welcomeSubject=");
		sb.append(welcomeSubject);
		sb.append("}");

		return sb.toString();
	}

	public Course toEntityModel() {
		CourseImpl courseImpl = new CourseImpl();

		if (uuid == null) {
			courseImpl.setUuid(StringPool.BLANK);
		}
		else {
			courseImpl.setUuid(uuid);
		}

		courseImpl.setCourseId(courseId);
		courseImpl.setCompanyId(companyId);
		courseImpl.setGroupId(groupId);
		courseImpl.setUserId(userId);

		if (userName == null) {
			courseImpl.setUserName(StringPool.BLANK);
		}
		else {
			courseImpl.setUserName(userName);
		}

		courseImpl.setGroupCreatedId(groupCreatedId);

		if (createDate == Long.MIN_VALUE) {
			courseImpl.setCreateDate(null);
		}
		else {
			courseImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			courseImpl.setModifiedDate(null);
		}
		else {
			courseImpl.setModifiedDate(new Date(modifiedDate));
		}

		courseImpl.setStatus(status);
		courseImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			courseImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			courseImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			courseImpl.setStatusDate(null);
		}
		else {
			courseImpl.setStatusDate(new Date(statusDate));
		}

		if (title == null) {
			courseImpl.setTitle(StringPool.BLANK);
		}
		else {
			courseImpl.setTitle(title);
		}

		if (description == null) {
			courseImpl.setDescription(StringPool.BLANK);
		}
		else {
			courseImpl.setDescription(description);
		}

		if (friendlyURL == null) {
			courseImpl.setFriendlyURL(StringPool.BLANK);
		}
		else {
			courseImpl.setFriendlyURL(friendlyURL);
		}

		if (startDate == Long.MIN_VALUE) {
			courseImpl.setStartDate(null);
		}
		else {
			courseImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			courseImpl.setEndDate(null);
		}
		else {
			courseImpl.setEndDate(new Date(endDate));
		}

		courseImpl.setIcon(icon);
		courseImpl.setCourseEvalId(CourseEvalId);

		if (CourseExtraData == null) {
			courseImpl.setCourseExtraData(StringPool.BLANK);
		}
		else {
			courseImpl.setCourseExtraData(CourseExtraData);
		}

		courseImpl.setClosed(closed);
		courseImpl.setMaxusers(maxusers);
		courseImpl.setCalificationType(calificationType);
		courseImpl.setWelcome(welcome);

		if (welcomeMsg == null) {
			courseImpl.setWelcomeMsg(StringPool.BLANK);
		}
		else {
			courseImpl.setWelcomeMsg(welcomeMsg);
		}

		if (welcomeSubject == null) {
			courseImpl.setWelcomeSubject(StringPool.BLANK);
		}
		else {
			courseImpl.setWelcomeSubject(welcomeSubject);
		}

		courseImpl.resetOriginalValues();

		return courseImpl;
	}

	public String uuid;
	public long courseId;
	public long companyId;
	public long groupId;
	public long userId;
	public String userName;
	public long groupCreatedId;
	public long createDate;
	public long modifiedDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public String title;
	public String description;
	public String friendlyURL;
	public long startDate;
	public long endDate;
	public long icon;
	public long CourseEvalId;
	public String CourseExtraData;
	public boolean closed;
	public long maxusers;
	public long calificationType;
	public boolean welcome;
	public String welcomeMsg;
	public String welcomeSubject;
}