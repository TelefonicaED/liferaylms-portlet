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

import com.liferay.lms.model.UserCompetence;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing UserCompetence in entity cache.
 *
 * @author TLS
 * @see UserCompetence
 * @generated
 */
public class UserCompetenceCacheModel implements CacheModel<UserCompetence>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", usercompId=");
		sb.append(usercompId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", competenceId=");
		sb.append(competenceId);
		sb.append(", compDate=");
		sb.append(compDate);
		sb.append(", courseId=");
		sb.append(courseId);
		sb.append("}");

		return sb.toString();
	}

	public UserCompetence toEntityModel() {
		UserCompetenceImpl userCompetenceImpl = new UserCompetenceImpl();

		if (uuid == null) {
			userCompetenceImpl.setUuid(StringPool.BLANK);
		}
		else {
			userCompetenceImpl.setUuid(uuid);
		}

		userCompetenceImpl.setUsercompId(usercompId);
		userCompetenceImpl.setUserId(userId);
		userCompetenceImpl.setCompetenceId(competenceId);

		if (compDate == Long.MIN_VALUE) {
			userCompetenceImpl.setCompDate(null);
		}
		else {
			userCompetenceImpl.setCompDate(new Date(compDate));
		}

		userCompetenceImpl.setCourseId(courseId);

		userCompetenceImpl.resetOriginalValues();

		return userCompetenceImpl;
	}

	public String uuid;
	public long usercompId;
	public long userId;
	public long competenceId;
	public long compDate;
	public long courseId;
}