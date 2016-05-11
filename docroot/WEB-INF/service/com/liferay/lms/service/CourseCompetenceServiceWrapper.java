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

package com.liferay.lms.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link CourseCompetenceService}.
 * </p>
 *
 * @author    TLS
 * @see       CourseCompetenceService
 * @generated
 */
public class CourseCompetenceServiceWrapper implements CourseCompetenceService,
	ServiceWrapper<CourseCompetenceService> {
	public CourseCompetenceServiceWrapper(
		CourseCompetenceService courseCompetenceService) {
		_courseCompetenceService = courseCompetenceService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _courseCompetenceService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_courseCompetenceService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _courseCompetenceService.invokeMethod(name, parameterTypes,
			arguments);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public CourseCompetenceService getWrappedCourseCompetenceService() {
		return _courseCompetenceService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedCourseCompetenceService(
		CourseCompetenceService courseCompetenceService) {
		_courseCompetenceService = courseCompetenceService;
	}

	public CourseCompetenceService getWrappedService() {
		return _courseCompetenceService;
	}

	public void setWrappedService(
		CourseCompetenceService courseCompetenceService) {
		_courseCompetenceService = courseCompetenceService;
	}

	private CourseCompetenceService _courseCompetenceService;
}