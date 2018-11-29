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

import com.liferay.lms.NoSuchCourseTypeCalificationTypeException;
import com.liferay.lms.model.CourseTypeCalificationType;
import com.liferay.lms.service.base.CourseTypeCalificationTypeLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * The implementation of the course type calification type local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseTypeCalificationTypeLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TED
 * @see com.liferay.lms.service.base.CourseTypeCalificationTypeLocalServiceBaseImpl
 * @see com.liferay.lms.service.CourseTypeCalificationTypeLocalServiceUtil
 */
public class CourseTypeCalificationTypeLocalServiceImpl
	extends CourseTypeCalificationTypeLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseTypeCalificationTypeLocalServiceUtil} to access the course type calification type local service.
	 */
	
	Log log = LogFactoryUtil.getLog(CourseTypeCalificationTypeLocalServiceImpl.class);
	
	public CourseTypeCalificationType getByCourseTypeCalificationTypeId(long courseTypeCalificationTypeId) throws NoSuchCourseTypeCalificationTypeException, SystemException{
		return courseTypeCalificationTypePersistence.findByCourseTypeCalificationTypeId(courseTypeCalificationTypeId);
	}
	
	public List<CourseTypeCalificationType> getByCourseTypeId(long courseTypeId) throws SystemException{
		return courseTypeCalificationTypePersistence.findByCourseTypeId(courseTypeId);
	}
	
	public CourseTypeCalificationType addCourseTypeCalificationType(long courseTypeId, long calificationTypeId) throws SystemException{
		if(log.isDebugEnabled()){
			log.debug("::addCourseTypeCalificationType:: courseTypeId :: " + courseTypeId);
			log.debug("::addCourseTypeCalificationType:: calificationTypeId :: " + calificationTypeId);
		}
		//PK Field
		CourseTypeCalificationType courseTypeCalificationType = courseTypeCalificationTypePersistence.create(counterLocalService.increment(CourseTypeCalificationType.class.getName()));
		//Description fields
		courseTypeCalificationType.setCourseTypeId(courseTypeId);
		courseTypeCalificationType.setCalificationType(calificationTypeId);
		courseTypeCalificationTypePersistence.update(courseTypeCalificationType, Boolean.TRUE);
		return courseTypeCalificationType;
	}
	
	public void addListCourseTypeCalificationTypes(long courseTypeId, long[] listCalificationTypeIds) throws SystemException{
		for(long calificationTypeId:listCalificationTypeIds)
			courseTypeCalificationTypeLocalService.addCourseTypeCalificationType(courseTypeId, calificationTypeId);
	}
}