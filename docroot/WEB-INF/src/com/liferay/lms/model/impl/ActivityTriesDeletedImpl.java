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

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * The extended model implementation for the ActivityTriesDeleted service. Represents a row in the &quot;Lms_ActivityTriesDeleted&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.model.ActivityTriesDeleted} interface.
 * </p>
 *
 * @author TLS
 */
public class ActivityTriesDeletedImpl extends ActivityTriesDeletedBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a activity tries deleted model instance should use the {@link com.liferay.lms.model.ActivityTriesDeleted} interface instead.
	 */
	
	
	public ActivityTriesDeletedImpl() {
	}
	
	public LearningActivity getLearningActivity(){
		try {
			return LearningActivityLocalServiceUtil.getLearningActivity(getActId());
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public User getUser(){
		try {
			return UserLocalServiceUtil.getUser(getUserId());
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getStatusProperties(){
		if(LmsConstant.STATUS_NOT_STARTED == getStatus()){
			return "activity-tries-deleted.not-started";
		}else if(LmsConstant.STATUS_IN_PROGRESS == getStatus()){
			return "activity-tries-deleted.in-progress";
		}else if(LmsConstant.STATUS_FINISH == getStatus()){
			return "activity-tries-deleted.finish";
		}else{
			return "-";
		}
	}
}