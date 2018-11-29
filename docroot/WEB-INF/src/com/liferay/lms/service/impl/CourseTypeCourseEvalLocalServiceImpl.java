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

import com.liferay.lms.NoSuchCourseTypeCourseEvalException;
import com.liferay.lms.model.CourseTypeCourseEval;
import com.liferay.lms.service.base.CourseTypeCourseEvalLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * The implementation of the course type course eval local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseTypeCourseEvalLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TED
 * @see com.liferay.lms.service.base.CourseTypeCourseEvalLocalServiceBaseImpl
 * @see com.liferay.lms.service.CourseTypeCourseEvalLocalServiceUtil
 */
public class CourseTypeCourseEvalLocalServiceImpl
	extends CourseTypeCourseEvalLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseTypeCourseEvalLocalServiceUtil} to access the course type course eval local service.
	 */
	
	Log log = LogFactoryUtil.getLog(CourseTypeCourseEvalLocalServiceImpl.class);
	
	public CourseTypeCourseEval getByCourseTypeCourseEvalyationTypeId(long courseTypeEvalutationTypeId) throws NoSuchCourseTypeCourseEvalException, SystemException{
		return courseTypeCourseEvalPersistence.findByCourseTypeCourseEvalId(courseTypeEvalutationTypeId);
	}
	
	public List<CourseTypeCourseEval> getByCourseTypeId(long courseTypeId) throws SystemException{
		return courseTypeCourseEvalPersistence.findByCourseTypeId(courseTypeId);
	}
	
	public CourseTypeCourseEval addCourseTypeCourseEval(long courseTypeId, long courseEvalId) throws SystemException{
		if(log.isDebugEnabled()){
			log.debug("::addCourseTypeCourseEval:: courseTypeId :: " + courseTypeId);
			log.debug("::addCourseTypeCourseEval:: courseEvalId :: " + courseEvalId);
		}
		//PK Field
		CourseTypeCourseEval courseTypeCourseEval = courseTypeCourseEvalPersistence.create(counterLocalService.increment(CourseTypeCourseEval.class.getName()));
		//Description fields
		courseTypeCourseEval.setCourseTypeId(courseTypeId);
		courseTypeCourseEval.setCourseEvalId(String.valueOf(courseEvalId));
		courseTypeCourseEvalPersistence.update(courseTypeCourseEval, Boolean.TRUE);
		return courseTypeCourseEval;
	}
	
	public void addListCourseTypeCourseEvals(long courseTypeId, long[] listCourseEvalIds) throws SystemException{
		for(long courseEvalId:listCourseEvalIds)
			courseTypeCourseEvalLocalService.addCourseTypeCourseEval(courseTypeId, courseEvalId);
	}
}