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

import com.liferay.lms.model.CourseCompetence;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

/**
 * The cache model class for representing CourseCompetence in entity cache.
 *
 * @author TLS
 * @see CourseCompetence
 * @generated
 */
public class CourseCompetenceCacheModel implements CacheModel<CourseCompetence>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CourcompetenceId=");
		sb.append(CourcompetenceId);
		sb.append(", courseId=");
		sb.append(courseId);
		sb.append(", competenceId=");
		sb.append(competenceId);
		sb.append(", condition=");
		sb.append(condition);
		sb.append("}");

		return sb.toString();
	}

	public CourseCompetence toEntityModel() {
		CourseCompetenceImpl courseCompetenceImpl = new CourseCompetenceImpl();

		if (uuid == null) {
			courseCompetenceImpl.setUuid(StringPool.BLANK);
		}
		else {
			courseCompetenceImpl.setUuid(uuid);
		}

		courseCompetenceImpl.setCourcompetenceId(CourcompetenceId);
		courseCompetenceImpl.setCourseId(courseId);
		courseCompetenceImpl.setCompetenceId(competenceId);
		courseCompetenceImpl.setCondition(condition);

		courseCompetenceImpl.resetOriginalValues();

		return courseCompetenceImpl;
	}

	public String uuid;
	public long CourcompetenceId;
	public long courseId;
	public long competenceId;
	public boolean condition;
}