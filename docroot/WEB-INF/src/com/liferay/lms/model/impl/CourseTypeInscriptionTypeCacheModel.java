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

import com.liferay.lms.model.CourseTypeInscriptionType;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

/**
 * The cache model class for representing CourseTypeInscriptionType in entity cache.
 *
 * @author TLS
 * @see CourseTypeInscriptionType
 * @generated
 */
public class CourseTypeInscriptionTypeCacheModel implements CacheModel<CourseTypeInscriptionType>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{courseTypeInscriptionTypeId=");
		sb.append(courseTypeInscriptionTypeId);
		sb.append(", courseTypeId=");
		sb.append(courseTypeId);
		sb.append(", inscriptionType=");
		sb.append(inscriptionType);
		sb.append("}");

		return sb.toString();
	}

	public CourseTypeInscriptionType toEntityModel() {
		CourseTypeInscriptionTypeImpl courseTypeInscriptionTypeImpl = new CourseTypeInscriptionTypeImpl();

		courseTypeInscriptionTypeImpl.setCourseTypeInscriptionTypeId(courseTypeInscriptionTypeId);
		courseTypeInscriptionTypeImpl.setCourseTypeId(courseTypeId);
		courseTypeInscriptionTypeImpl.setInscriptionType(inscriptionType);

		courseTypeInscriptionTypeImpl.resetOriginalValues();

		return courseTypeInscriptionTypeImpl;
	}

	public long courseTypeInscriptionTypeId;
	public long courseTypeId;
	public long inscriptionType;
}