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
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;


public class LearningActivityResultImpl extends LearningActivityResultBaseImpl {
	
	public LearningActivityResultImpl() {
	
	}
	
	public String getResult(long groupId){
		String result ="";
		try {
			Course curso = CourseLocalServiceUtil.getCourseByGroupCreatedId(groupId);
			User user = UserLocalServiceUtil.fetchUser(this.getUserId());
			Locale locale = user.getLocale();
			if(curso != null){
				CalificationType ct = new CalificationTypeRegistry().getCalificationType(curso.getCalificationType());
				result = ct.translate(locale,groupId, this.getResult());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String translateResult(Locale locale){
		String translatedResult = "";
		try {
			LearningActivity activity = LearningActivityLocalServiceUtil.fetchLearningActivity(getActId());
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(activity.getGroupId());
			
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
			LearningActivity activity = LearningActivityLocalServiceUtil.fetchLearningActivity(getActId());
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(activity.getGroupId());
			
			CalificationType ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());
			translatedResult = ct.translate(locale,course.getGroupCreatedId(),getResult())+ct.getSuffix(activity.getGroupId());
			
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