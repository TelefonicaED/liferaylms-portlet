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

package com.liferay.lms.service.impl;

import java.util.Date;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.model.Schedule;
import com.liferay.lms.service.ScheduleLocalServiceUtil;
import com.liferay.lms.service.base.ScheduleLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.ScheduleUtil;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the schedule local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.ScheduleLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.ScheduleLocalServiceBaseImpl
 * @see com.liferay.lms.service.ScheduleLocalServiceUtil
 */
public class ScheduleLocalServiceImpl extends ScheduleLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.ScheduleLocalServiceUtil} to access the schedule local service.
	 */
	
	public Schedule addSchedule (long teamId, Date startDate, Date endDate){
		Schedule scheduledTeam = null;
		try {
			scheduledTeam = ScheduleUtil.create(CounterLocalServiceUtil.increment(Schedule.class.getName()));
			scheduledTeam.setTeamId(teamId);
			scheduledTeam.setEndDate(endDate);
			scheduledTeam.setStartDate(startDate);
			ScheduleLocalServiceUtil.addSchedule(scheduledTeam);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		return scheduledTeam;
		
	}
	
	
	public Schedule getScheduleByTeamId(long teamId) throws SystemException
	{
		Schedule schedule = ScheduleUtil.fetchByTeamId(teamId);
		return schedule;
		
	}
}