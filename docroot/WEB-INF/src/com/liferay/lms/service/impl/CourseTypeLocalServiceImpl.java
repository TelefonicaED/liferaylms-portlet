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

import com.liferay.lms.NoSuchCourseTypeException;
import com.liferay.lms.model.CourseType;
import com.liferay.lms.service.base.CourseTypeLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * The implementation of the course type local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseTypeLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CourseTypeLocalServiceBaseImpl
 * @see com.liferay.lms.service.CourseTypeLocalServiceUtil
 */
public class CourseTypeLocalServiceImpl extends CourseTypeLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseTypeLocalServiceUtil} to access the course type local service.
	 */
	
	Log log = LogFactoryUtil.getLog(CourseTypeLocalServiceImpl.class);
	
	public List<CourseType> getByCompanyId(long companyId) throws SystemException{
		return courseTypePersistence.findByCompanyId(companyId);
	}
	
	public CourseType getByCourseTypeId(long courseTypeId) throws NoSuchCourseTypeException, SystemException{
		return courseTypePersistence.findByCourseTypeId(courseTypeId);
	}
	
}