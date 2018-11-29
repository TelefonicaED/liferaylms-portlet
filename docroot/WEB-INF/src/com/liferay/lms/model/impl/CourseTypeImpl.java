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

import java.util.ArrayList;
import java.util.List;

import com.liferay.lms.model.CourseTypeCalificationType;
import com.liferay.lms.model.CourseTypeCourseEval;
import com.liferay.lms.model.CourseTypeInscriptionType;
import com.liferay.lms.model.CourseTypeLearningActivity;
import com.liferay.lms.model.CourseTypeTemplate;
import com.liferay.lms.service.CourseTypeCalificationTypeLocalServiceUtil;
import com.liferay.lms.service.CourseTypeCourseEvalLocalServiceUtil;
import com.liferay.lms.service.CourseTypeInscriptionTypeLocalServiceUtil;
import com.liferay.lms.service.CourseTypeLearningActivityLocalServiceUtil;
import com.liferay.lms.service.CourseTypeTemplateLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * The extended model implementation for the CourseType service. Represents a row in the &quot;Lms_CourseType&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.model.CourseType} interface.
 * </p>
 *
 * @author TLS
 */
public class CourseTypeImpl extends CourseTypeBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a course type model instance should use the {@link com.liferay.lms.model.CourseType} interface instead.
	 */
	
	private static Log log = LogFactoryUtil.getLog(CourseTypeImpl.class);
	
	public CourseTypeImpl() {
	}
	
	public List<Long> getCourseTemplateIds() throws SystemException{
		List<Long> courseTemplateIds = new ArrayList<Long>();
		for (CourseTypeTemplate courseTypeTemplate:CourseTypeTemplateLocalServiceUtil.getByCourseTypeId(getCourseTypeId())){
			courseTemplateIds.add(courseTypeTemplate.getTemplateId());
		}
		return courseTemplateIds;
	}
	
	public List<Long> getCourseEvalTypeIds() throws SystemException{
		List<Long> courseEvalIds = new ArrayList<Long>();
		for(CourseTypeCourseEval courseTypeCourseEval:CourseTypeCourseEvalLocalServiceUtil.getByCourseTypeId(getCourseTypeId())){
			courseEvalIds.add(Long.valueOf(courseTypeCourseEval.getCourseEvalId()));
		}
		return courseEvalIds;
	}
	
	public List<Long> getLearningActivityTypeIds() throws SystemException{
		List<Long> learningActivityTypeIds = new ArrayList<Long>();
		for(CourseTypeLearningActivity courseTypeLearningActivity:CourseTypeLearningActivityLocalServiceUtil.getByCourseTypeId(getCourseTypeId())){
			learningActivityTypeIds.add(courseTypeLearningActivity.getLearningActivityTypeId());
		}
		return learningActivityTypeIds;
	}
	
	public List<Long> getInscriptionTypeIds() throws SystemException{
		List<Long> inscriptionTypeIds = new ArrayList<Long>();
		for(CourseTypeInscriptionType courseTypeInscriptionType:CourseTypeInscriptionTypeLocalServiceUtil.getByCourseTypeId(getCourseTypeId())){
			inscriptionTypeIds.add(courseTypeInscriptionType.getInscriptionType());
		}
		return inscriptionTypeIds;
	}
	
	public List<Long> getCalificationTypeIds() throws SystemException{
		List<Long> calificationTypeIds = new ArrayList<Long>();
		for(CourseTypeCalificationType courseTypeCalificationType:CourseTypeCalificationTypeLocalServiceUtil.getByCourseTypeId(getCourseTypeId())){
			calificationTypeIds.add(courseTypeCalificationType.getCalificationType());
		}
		return calificationTypeIds;
	}

}