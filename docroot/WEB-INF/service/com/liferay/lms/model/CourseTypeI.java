package com.liferay.lms.model;

import java.util.HashMap;

import com.liferay.portal.service.ServiceContext;

public interface CourseTypeI {
	
	public void copyCourse(Course oldCourse, ServiceContext serviceContext);
	
}
