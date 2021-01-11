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

import com.liferay.lms.model.CourseTypeRelation;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

/**
 * The cache model class for representing CourseTypeRelation in entity cache.
 *
 * @author TLS
 * @see CourseTypeRelation
 * @generated
 */
public class CourseTypeRelationCacheModel implements CacheModel<CourseTypeRelation>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{courseTypeRelationId=");
		sb.append(courseTypeRelationId);
		sb.append(", courseTypeId=");
		sb.append(courseTypeId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	public CourseTypeRelation toEntityModel() {
		CourseTypeRelationImpl courseTypeRelationImpl = new CourseTypeRelationImpl();

		courseTypeRelationImpl.setCourseTypeRelationId(courseTypeRelationId);
		courseTypeRelationImpl.setCourseTypeId(courseTypeId);
		courseTypeRelationImpl.setClassNameId(classNameId);
		courseTypeRelationImpl.setClassPK(classPK);

		courseTypeRelationImpl.resetOriginalValues();

		return courseTypeRelationImpl;
	}

	public long courseTypeRelationId;
	public long courseTypeId;
	public long classNameId;
	public long classPK;
}