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

import com.liferay.lms.model.Schedule;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing Schedule in entity cache.
 *
 * @author TLS
 * @see Schedule
 * @generated
 */
public class ScheduleCacheModel implements CacheModel<Schedule>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{secheduleId=");
		sb.append(secheduleId);
		sb.append(", teamId=");
		sb.append(teamId);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append("}");

		return sb.toString();
	}

	public Schedule toEntityModel() {
		ScheduleImpl scheduleImpl = new ScheduleImpl();

		scheduleImpl.setSecheduleId(secheduleId);
		scheduleImpl.setTeamId(teamId);

		if (startDate == Long.MIN_VALUE) {
			scheduleImpl.setStartDate(null);
		}
		else {
			scheduleImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			scheduleImpl.setEndDate(null);
		}
		else {
			scheduleImpl.setEndDate(new Date(endDate));
		}

		scheduleImpl.resetOriginalValues();

		return scheduleImpl;
	}

	public long secheduleId;
	public long teamId;
	public long startDate;
	public long endDate;
}