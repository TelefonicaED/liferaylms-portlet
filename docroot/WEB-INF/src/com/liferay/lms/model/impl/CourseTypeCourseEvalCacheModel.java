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

import com.liferay.lms.model.CourseTypeCourseEval;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

/**
 * The cache model class for representing CourseTypeCourseEval in entity cache.
 *
 * @author TLS
 * @see CourseTypeCourseEval
 * @generated
 */
public class CourseTypeCourseEvalCacheModel implements CacheModel<CourseTypeCourseEval>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{courseTypeEvalutationTypeId=");
		sb.append(courseTypeEvalutationTypeId);
		sb.append(", courseTypeId=");
		sb.append(courseTypeId);
		sb.append(", courseEvalId=");
		sb.append(courseEvalId);
		sb.append("}");

		return sb.toString();
	}

	public CourseTypeCourseEval toEntityModel() {
		CourseTypeCourseEvalImpl courseTypeCourseEvalImpl = new CourseTypeCourseEvalImpl();

		courseTypeCourseEvalImpl.setCourseTypeEvalutationTypeId(courseTypeEvalutationTypeId);
		courseTypeCourseEvalImpl.setCourseTypeId(courseTypeId);

		if (courseEvalId == null) {
			courseTypeCourseEvalImpl.setCourseEvalId(StringPool.BLANK);
		}
		else {
			courseTypeCourseEvalImpl.setCourseEvalId(courseEvalId);
		}

		courseTypeCourseEvalImpl.resetOriginalValues();

		return courseTypeCourseEvalImpl;
	}

	public long courseTypeEvalutationTypeId;
	public long courseTypeId;
	public String courseEvalId;
}