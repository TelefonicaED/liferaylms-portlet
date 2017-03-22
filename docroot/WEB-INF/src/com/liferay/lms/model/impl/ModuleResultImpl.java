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
import com.liferay.lms.model.Module;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;


public class ModuleResultImpl extends ModuleResultBaseImpl {

	public ModuleResultImpl() {
	}
	
	public String translateResult(Locale locale){
		String translatedResult = "";
		try {
			Module module = ModuleLocalServiceUtil.fetchModule(getModuleId());
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(module.getGroupId());
			
			CalificationType ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());
			translatedResult = ct.translate(locale,course.getGroupCreatedId(),getResult());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return translatedResult;
	}
	
	public String translateResultWithSuffix(Locale locale){
		String translatedResult = "";
		try {
			Module module = ModuleLocalServiceUtil.fetchModule(getModuleId());
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(module.getGroupId());
			
			CalificationType ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());
			translatedResult = ct.translate(locale,course.getGroupCreatedId(),getResult())+ct.getSuffix(module.getGroupId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return translatedResult;
	}
	
	public String translateResult(Locale locale,long groupId,CalificationType ct){
		String translatedResult = "";
		if(ct != null){
			translatedResult = ct.translate(locale,groupId,getResult());
		}		
		return translatedResult;
	}
	
	public String translateResultWithSuffix(Locale locale,long groupId,CalificationType ct){
		String translatedResult = "";
		if(ct != null){
			translatedResult = ct.translate(locale,groupId,getResult())+ct.getSuffix(groupId);
		}		
		return translatedResult;
	}
}