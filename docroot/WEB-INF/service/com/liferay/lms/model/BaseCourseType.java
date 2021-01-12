package com.liferay.lms.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

public abstract class BaseCourseType implements CourseTypeI{

	protected Course course;
	
	public BaseCourseType(Course course) throws PortalException, SystemException{
		this.course = course;
	}
}
