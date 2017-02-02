/**
 * Copyright (c)2013 Telefonica Learning Services. All rights reserved.
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

import java.util.Locale;

import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;


public class CourseResultImpl extends CourseResultBaseImpl {

	public CourseResultImpl() {
	}
	
	public String translateResult(Locale locale){
		String translatedResult = "";
		try {
			Course course = CourseLocalServiceUtil.fetchCourse(getCourseId());
			
			CalificationType ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());
			translatedResult = ct.translate(locale,getResult());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return translatedResult;
	}
	
	public String translateResultWithSuffix(Locale locale){
		String translatedResult = "";
		try {
			Course course = CourseLocalServiceUtil.fetchCourse(getCourseId());
			
			CalificationType ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());
			translatedResult = ct.translate(locale,getResult())+ct.getSuffix();			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return translatedResult;
	}
	
	public String translateResult(Locale locale,CalificationType ct){
		String translatedResult = "";
		if(ct != null){
			translatedResult = ct.translate(locale,getResult());
		}		
		return translatedResult;
	}
	
	public String translateResultWithSuffix(Locale locale,CalificationType ct){
		String translatedResult = "";
		if(ct != null){
			translatedResult = ct.translate(locale,getResult())+ct.getSuffix();
		}		
		return translatedResult;
	}
}