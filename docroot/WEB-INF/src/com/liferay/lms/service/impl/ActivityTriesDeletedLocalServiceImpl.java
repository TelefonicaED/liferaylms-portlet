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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.model.ActivityTriesDeleted;
import com.liferay.lms.service.ActivityTriesDeletedLocalServiceUtil;
import com.liferay.lms.service.base.ActivityTriesDeletedLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.ActivityTriesDeletedUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the activity tries deleted local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.ActivityTriesDeletedLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.ActivityTriesDeletedLocalServiceBaseImpl
 * @see com.liferay.lms.service.ActivityTriesDeletedLocalServiceUtil
 */
public class ActivityTriesDeletedLocalServiceImpl
	extends ActivityTriesDeletedLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.ActivityTriesDeletedLocalServiceUtil} to access the activity tries deleted local service.
	 */
	
	public List<ActivityTriesDeleted> getByGroupId(long groupId, int start, int end){
		List<ActivityTriesDeleted> listActivityTriesDeleted = null;
		
		try {
			listActivityTriesDeleted = ActivityTriesDeletedUtil.findByGroupId(groupId, start, end);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			listActivityTriesDeleted = new ArrayList<ActivityTriesDeleted>();
		}
		
		return listActivityTriesDeleted;
	}
	
	public int countByGroupId(long groupId){
		try {
			return ActivityTriesDeletedUtil.countByGroupId(groupId);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public List<ActivityTriesDeleted> getByActIdStatus(long actId, int status){
		try {
			return ActivityTriesDeletedUtil.findByActIdStatus(actId, status);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<ActivityTriesDeleted>();
		}
	}
	
	public ActivityTriesDeleted addActivityTriesDeleted(long groupId, long actId, long userId){
		ActivityTriesDeleted activityTriesDeleted = null;
		
		try {
			activityTriesDeleted = ActivityTriesDeletedUtil.create(CounterLocalServiceUtil.increment(ActivityTriesDeleted.class.getName()));
			activityTriesDeleted.setActId(actId);
			activityTriesDeleted.setGroupId(groupId);
			activityTriesDeleted.setStatus(LmsConstant.STATUS_NOT_STARTED);
			activityTriesDeleted.setStartDate(new Date());
			activityTriesDeleted.setUserId(userId);
			ActivityTriesDeletedLocalServiceUtil.addActivityTriesDeleted(activityTriesDeleted);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return activityTriesDeleted;
	}
	
	public ActivityTriesDeleted updateFinish(ActivityTriesDeleted activityTriesDeleted){
		activityTriesDeleted.setStatus(LmsConstant.STATUS_FINISH);
		activityTriesDeleted.setEndDate(new Date());
		try {
			return ActivityTriesDeletedLocalServiceUtil.updateActivityTriesDeleted(activityTriesDeleted);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}