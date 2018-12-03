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

import java.util.List;

import com.liferay.lms.NoSuchCourseTypeLearningActivityException;
import com.liferay.lms.model.CourseTypeLearningActivity;
import com.liferay.lms.service.base.CourseTypeLearningActivityLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * The implementation of the course type learning activity local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseTypeLearningActivityLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TED
 * @see com.liferay.lms.service.base.CourseTypeLearningActivityLocalServiceBaseImpl
 * @see com.liferay.lms.service.CourseTypeLearningActivityLocalServiceUtil
 */
public class CourseTypeLearningActivityLocalServiceImpl
	extends CourseTypeLearningActivityLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseTypeLearningActivityLocalServiceUtil} to access the course type learning activity local service.
	 */
	
	Log log = LogFactoryUtil.getLog(CourseTypeLearningActivityLocalServiceImpl.class);
	
	public CourseTypeLearningActivity getByCourseTypeLearningActivityId(long courseTypeLearningActivityId) throws NoSuchCourseTypeLearningActivityException, SystemException{
		return courseTypeLearningActivityPersistence.findByCourseTypeLearningActivityId(courseTypeLearningActivityId);
	}
	
	public List<CourseTypeLearningActivity> getByCourseTypeId(long courseTypeId) throws SystemException{
		return courseTypeLearningActivityPersistence.findByCourseTypeId(courseTypeId);
	}
	
	public CourseTypeLearningActivity addCourseTypeLearningActivity(long courseTypeId, long learningActivityTypeId) throws SystemException{
		if(log.isDebugEnabled()){
			log.debug("::addCourseTypeLearningActivity:: courseTypeId :: " + courseTypeId);
			log.debug("::addCourseTypeLearningActivity:: learningActivityTypeId :: " + learningActivityTypeId);
		}
		//PK Field
		CourseTypeLearningActivity courseTypeLearningActivity = courseTypeLearningActivityPersistence.create(counterLocalService.increment(CourseTypeLearningActivity.class.getName()));
		//Description fields
		courseTypeLearningActivity.setCourseTypeId(courseTypeId);
		courseTypeLearningActivity.setLearningActivityTypeId(learningActivityTypeId);
		courseTypeLearningActivityPersistence.update(courseTypeLearningActivity, Boolean.TRUE);
		return courseTypeLearningActivity;
	}
	
	public void addListCourseTypeLearningActivities(long courseTypeId, long[] listLearningActivityTypeIds) throws SystemException{
		for(long learningActivityTypeId:listLearningActivityTypeIds)
			courseTypeLearningActivityLocalService.addCourseTypeLearningActivity(courseTypeId, learningActivityTypeId);
	}
}