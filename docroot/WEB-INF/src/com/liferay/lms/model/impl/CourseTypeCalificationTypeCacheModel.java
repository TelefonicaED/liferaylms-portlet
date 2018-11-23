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

import com.liferay.lms.model.CourseTypeCalificationType;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

/**
 * The cache model class for representing CourseTypeCalificationType in entity cache.
 *
 * @author TLS
 * @see CourseTypeCalificationType
 * @generated
 */
public class CourseTypeCalificationTypeCacheModel implements CacheModel<CourseTypeCalificationType>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{courseTypeCalificationTypeId=");
		sb.append(courseTypeCalificationTypeId);
		sb.append(", courseTypeId=");
		sb.append(courseTypeId);
		sb.append(", calificationType=");
		sb.append(calificationType);
		sb.append("}");

		return sb.toString();
	}

	public CourseTypeCalificationType toEntityModel() {
		CourseTypeCalificationTypeImpl courseTypeCalificationTypeImpl = new CourseTypeCalificationTypeImpl();

		courseTypeCalificationTypeImpl.setCourseTypeCalificationTypeId(courseTypeCalificationTypeId);
		courseTypeCalificationTypeImpl.setCourseTypeId(courseTypeId);
		courseTypeCalificationTypeImpl.setCalificationType(calificationType);

		courseTypeCalificationTypeImpl.resetOriginalValues();

		return courseTypeCalificationTypeImpl;
	}

	public long courseTypeCalificationTypeId;
	public long courseTypeId;
	public long calificationType;
}