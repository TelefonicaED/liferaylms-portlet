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

import com.liferay.lms.model.CourseTypeLearningActivity;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

/**
 * The cache model class for representing CourseTypeLearningActivity in entity cache.
 *
 * @author TLS
 * @see CourseTypeLearningActivity
 * @generated
 */
public class CourseTypeLearningActivityCacheModel implements CacheModel<CourseTypeLearningActivity>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{courseTypeLearningActivityId=");
		sb.append(courseTypeLearningActivityId);
		sb.append(", courseTypeId=");
		sb.append(courseTypeId);
		sb.append(", learningActivityTypeId=");
		sb.append(learningActivityTypeId);
		sb.append("}");

		return sb.toString();
	}

	public CourseTypeLearningActivity toEntityModel() {
		CourseTypeLearningActivityImpl courseTypeLearningActivityImpl = new CourseTypeLearningActivityImpl();

		courseTypeLearningActivityImpl.setCourseTypeLearningActivityId(courseTypeLearningActivityId);
		courseTypeLearningActivityImpl.setCourseTypeId(courseTypeId);
		courseTypeLearningActivityImpl.setLearningActivityTypeId(learningActivityTypeId);

		courseTypeLearningActivityImpl.resetOriginalValues();

		return courseTypeLearningActivityImpl;
	}

	public long courseTypeLearningActivityId;
	public long courseTypeId;
	public long learningActivityTypeId;
}