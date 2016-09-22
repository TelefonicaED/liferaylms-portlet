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

import com.liferay.lms.model.ActivityTriesDeleted;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing ActivityTriesDeleted in entity cache.
 *
 * @author TLS
 * @see ActivityTriesDeleted
 * @generated
 */
public class ActivityTriesDeletedCacheModel implements CacheModel<ActivityTriesDeleted>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{activityTriesDeletedId=");
		sb.append(activityTriesDeletedId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", actId=");
		sb.append(actId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	public ActivityTriesDeleted toEntityModel() {
		ActivityTriesDeletedImpl activityTriesDeletedImpl = new ActivityTriesDeletedImpl();

		activityTriesDeletedImpl.setActivityTriesDeletedId(activityTriesDeletedId);
		activityTriesDeletedImpl.setGroupId(groupId);
		activityTriesDeletedImpl.setActId(actId);
		activityTriesDeletedImpl.setUserId(userId);

		if (startDate == Long.MIN_VALUE) {
			activityTriesDeletedImpl.setStartDate(null);
		}
		else {
			activityTriesDeletedImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			activityTriesDeletedImpl.setEndDate(null);
		}
		else {
			activityTriesDeletedImpl.setEndDate(new Date(endDate));
		}

		activityTriesDeletedImpl.setStatus(status);

		activityTriesDeletedImpl.resetOriginalValues();

		return activityTriesDeletedImpl;
	}

	public long activityTriesDeletedId;
	public long groupId;
	public long actId;
	public long userId;
	public long startDate;
	public long endDate;
	public int status;
}