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

import com.liferay.lms.model.CourseTypeTemplate;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

/**
 * The cache model class for representing CourseTypeTemplate in entity cache.
 *
 * @author TLS
 * @see CourseTypeTemplate
 * @generated
 */
public class CourseTypeTemplateCacheModel implements CacheModel<CourseTypeTemplate>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{courseTypeTemplateId=");
		sb.append(courseTypeTemplateId);
		sb.append(", courseTypeId=");
		sb.append(courseTypeId);
		sb.append(", templateId=");
		sb.append(templateId);
		sb.append("}");

		return sb.toString();
	}

	public CourseTypeTemplate toEntityModel() {
		CourseTypeTemplateImpl courseTypeTemplateImpl = new CourseTypeTemplateImpl();

		courseTypeTemplateImpl.setCourseTypeTemplateId(courseTypeTemplateId);
		courseTypeTemplateImpl.setCourseTypeId(courseTypeId);
		courseTypeTemplateImpl.setTemplateId(templateId);

		courseTypeTemplateImpl.resetOriginalValues();

		return courseTypeTemplateImpl;
	}

	public long courseTypeTemplateId;
	public long courseTypeId;
	public long templateId;
}