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

import com.liferay.lms.NoSuchCourseTypeInscriptionTypeException;
import com.liferay.lms.model.CourseTypeInscriptionType;
import com.liferay.lms.service.base.CourseTypeInscriptionTypeLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * The implementation of the course type inscription type local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseTypeInscriptionTypeLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CourseTypeInscriptionTypeLocalServiceBaseImpl
 * @see com.liferay.lms.service.CourseTypeInscriptionTypeLocalServiceUtil
 */
public class CourseTypeInscriptionTypeLocalServiceImpl
	extends CourseTypeInscriptionTypeLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseTypeInscriptionTypeLocalServiceUtil} to access the course type inscription type local service.
	 */
	
	Log log = LogFactoryUtil.getLog(CourseTypeInscriptionTypeLocalServiceImpl.class);
	
	public CourseTypeInscriptionType getByCourseTypeInscriptionTypeId(long courseTypeInscriptionTypeId) throws NoSuchCourseTypeInscriptionTypeException, SystemException{
		return courseTypeInscriptionTypePersistence.findByCourseTypeInscriptionTypeId(courseTypeInscriptionTypeId);
	}
	
	public List<CourseTypeInscriptionType> getByCourseTypeId(long courseTypeId) throws SystemException{
		return courseTypeInscriptionTypePersistence.findByCourseTypeId(courseTypeId);
	}
	
	public CourseTypeInscriptionType addCourseTypeInscriptionType(long courseTypeId, long inscriptionTypeId) throws SystemException{
		if(log.isDebugEnabled()){
			log.debug("::addCourseTypeInscriptionType:: courseTypeId :: " + courseTypeId);
			log.debug("::addCourseTypeInscriptionType:: inscriptionTypeId :: " + inscriptionTypeId);
		}
		//PK Field
		CourseTypeInscriptionType courseTypeInscriptionType = courseTypeInscriptionTypePersistence.create(counterLocalService.increment(CourseTypeInscriptionType.class.getName()));
		//Description fields
		courseTypeInscriptionType.setCourseTypeId(courseTypeId);
		courseTypeInscriptionType.setInscriptionType(inscriptionTypeId);;
		courseTypeInscriptionTypePersistence.update(courseTypeInscriptionType, Boolean.TRUE);
		return courseTypeInscriptionType;
	}
	
	public void addListCourseTypeInscriptionTypes(long courseTypeId, long[] listInscriptionTypeIds) throws SystemException{
		for(long inscriptionTypeId:listInscriptionTypeIds)
			courseTypeInscriptionTypeLocalService.addCourseTypeInscriptionType(courseTypeId, inscriptionTypeId);
	}
}