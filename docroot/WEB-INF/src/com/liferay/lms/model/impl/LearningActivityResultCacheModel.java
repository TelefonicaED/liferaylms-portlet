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

import com.liferay.lms.model.LearningActivityResult;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing LearningActivityResult in entity cache.
 *
 * @author TLS
 * @see LearningActivityResult
 * @generated
 */
public class LearningActivityResultCacheModel implements CacheModel<LearningActivityResult>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", larId=");
		sb.append(larId);
		sb.append(", actId=");
		sb.append(actId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", result=");
		sb.append(result);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", latId=");
		sb.append(latId);
		sb.append(", comments=");
		sb.append(comments);
		sb.append(", passed=");
		sb.append(passed);
		sb.append("}");

		return sb.toString();
	}

	public LearningActivityResult toEntityModel() {
		LearningActivityResultImpl learningActivityResultImpl = new LearningActivityResultImpl();

		if (uuid == null) {
			learningActivityResultImpl.setUuid(StringPool.BLANK);
		}
		else {
			learningActivityResultImpl.setUuid(uuid);
		}

		learningActivityResultImpl.setLarId(larId);
		learningActivityResultImpl.setActId(actId);
		learningActivityResultImpl.setUserId(userId);
		learningActivityResultImpl.setResult(result);

		if (startDate == Long.MIN_VALUE) {
			learningActivityResultImpl.setStartDate(null);
		}
		else {
			learningActivityResultImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			learningActivityResultImpl.setEndDate(null);
		}
		else {
			learningActivityResultImpl.setEndDate(new Date(endDate));
		}

		learningActivityResultImpl.setLatId(latId);

		if (comments == null) {
			learningActivityResultImpl.setComments(StringPool.BLANK);
		}
		else {
			learningActivityResultImpl.setComments(comments);
		}

		learningActivityResultImpl.setPassed(passed);

		learningActivityResultImpl.resetOriginalValues();

		return learningActivityResultImpl;
	}

	public String uuid;
	public long larId;
	public long actId;
	public long userId;
	public long result;
	public long startDate;
	public long endDate;
	public long latId;
	public String comments;
	public boolean passed;
}