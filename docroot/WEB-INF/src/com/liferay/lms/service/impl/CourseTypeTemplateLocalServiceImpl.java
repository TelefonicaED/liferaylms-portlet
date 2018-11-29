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

import com.liferay.lms.NoSuchCourseTypeTemplateException;
import com.liferay.lms.model.CourseTypeTemplate;
import com.liferay.lms.service.base.CourseTypeTemplateLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * The implementation of the course type template local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseTypeTemplateLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TED
 * @see com.liferay.lms.service.base.CourseTypeTemplateLocalServiceBaseImpl
 * @see com.liferay.lms.service.CourseTypeTemplateLocalServiceUtil
 */
public class CourseTypeTemplateLocalServiceImpl
	extends CourseTypeTemplateLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseTypeTemplateLocalServiceUtil} to access the course type template local service.
	 */
	
	Log log = LogFactoryUtil.getLog(CourseTypeTemplateLocalServiceImpl.class);
	
	public CourseTypeTemplate getByCourseTypeTemplateId(long courseTypeTemplateId) throws NoSuchCourseTypeTemplateException, SystemException{
		return courseTypeTemplatePersistence.findByCourseTypeTemplateId(courseTypeTemplateId);
	}
	
	public List<CourseTypeTemplate> getByCourseTypeId(long courseTypeId) throws SystemException{
		return courseTypeTemplatePersistence.findByCourseTypeId(courseTypeId);
	}
	
	public CourseTypeTemplate addCourseTypeTemplate(long courseTypeId, long templateId) throws SystemException{
		if(log.isDebugEnabled()){
			log.debug("::addCourseTypeTemplate:: courseTypeId :: " + courseTypeId);
			log.debug("::addCourseTypeTemplate:: templateId :: " + templateId);
		}
		//PK field
		CourseTypeTemplate courseTypeTemplate =  courseTypeTemplatePersistence.create(counterLocalService.increment(CourseTypeTemplate.class.getName()));
		//Description fields
		courseTypeTemplate.setCourseTypeId(courseTypeId);
		courseTypeTemplate.setTemplateId(templateId);
		courseTypeTemplatePersistence.update(courseTypeTemplate, Boolean.TRUE);
		return courseTypeTemplate;
	}
	
	public void addListCourseTypeTemplates(long courseTypeId, long[] listTemplateIds) throws SystemException{
		for(long templateId:listTemplateIds)
			courseTypeTemplateLocalService.addCourseTypeTemplate(courseTypeId, templateId);
	}
}