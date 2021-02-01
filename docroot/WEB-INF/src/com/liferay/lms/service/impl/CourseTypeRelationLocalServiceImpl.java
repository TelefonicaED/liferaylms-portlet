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

import com.liferay.lms.model.CourseTypeRelation;
import com.liferay.lms.service.base.CourseTypeRelationLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the course type relation local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseTypeRelationLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CourseTypeRelationLocalServiceBaseImpl
 * @see com.liferay.lms.service.CourseTypeRelationLocalServiceUtil
 */
public class CourseTypeRelationLocalServiceImpl
	extends CourseTypeRelationLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseTypeRelationLocalServiceUtil} to access the course type relation local service.
	 */
	
	public CourseTypeRelation addCourseTypeRelation(long courseTypeId, long classNameId, long classPK) throws SystemException{
		CourseTypeRelation courseTypeRelation = courseTypeRelationPersistence.create(counterLocalService.increment(CourseTypeRelation.class.getName()));
		
		courseTypeRelation.setCourseTypeId(courseTypeId);
		courseTypeRelation.setClassNameId(classNameId);
		courseTypeRelation.setClassPK(classPK);
		
		courseTypeRelation = courseTypeRelationPersistence.update(courseTypeRelation, false);
		
		return courseTypeRelation;
	}
	
	public void addCourseTypeRelations(long courseTypeId, long classNameId, long[] classPKs) throws SystemException{
		CourseTypeRelation courseTypeRelation = null;
		
		for(long classPK: classPKs){
			courseTypeRelation = courseTypeRelationPersistence.create(counterLocalService.increment(CourseTypeRelation.class.getName()));
			
			courseTypeRelation.setCourseTypeId(courseTypeId);
			courseTypeRelation.setClassNameId(classNameId);
			courseTypeRelation.setClassPK(classPK);
			
			courseTypeRelation = courseTypeRelationPersistence.update(courseTypeRelation, false);
		}
	}
	
	public List<CourseTypeRelation> getCourseTypeRelations(long courseTypeId, long classNameId) throws SystemException{
		return courseTypeRelationPersistence.findByCourseTypeIdClassNameId(courseTypeId, classNameId);
	}
	
	public void removeCourseTypeRelations(long courseTypeId) throws SystemException{
		courseTypeRelationPersistence.removeByCourseTypeId(courseTypeId);
	}
}