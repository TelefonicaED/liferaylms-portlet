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

import com.liferay.lms.model.CourseCompetence;
import com.liferay.lms.service.base.CourseCompetenceLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;

/**
 * The implementation of the course competence local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseCompetenceLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CourseCompetenceLocalServiceBaseImpl
 * @see com.liferay.lms.service.CourseCompetenceLocalServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.MANUAL)
public class CourseCompetenceLocalServiceImpl extends CourseCompetenceLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseCompetenceLocalServiceUtil} to access the course competence local service.
	 */
	
	public List<CourseCompetence> findBycourseId(long courseId,boolean condition) throws SystemException{
		return courseCompetencePersistence.findBycourseId(courseId, condition);
	}
	
	public CourseCompetence fetchByCourseCompetenceCondition(long courseId,long competenceId,boolean condition) throws SystemException{
		return courseCompetencePersistence.fetchByCourseCompetenceCondition(courseId, competenceId, condition);
	}
}