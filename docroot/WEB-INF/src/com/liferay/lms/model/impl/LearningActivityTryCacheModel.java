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

import com.liferay.lms.model.LearningActivityTry;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing LearningActivityTry in entity cache.
 *
 * @author TLS
 * @see LearningActivityTry
 * @generated
 */
public class LearningActivityTryCacheModel implements CacheModel<LearningActivityTry>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", latId=");
		sb.append(latId);
		sb.append(", actId=");
		sb.append(actId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", result=");
		sb.append(result);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", tryData=");
		sb.append(tryData);
		sb.append(", tryResultData=");
		sb.append(tryResultData);
		sb.append(", comments=");
		sb.append(comments);
		sb.append("}");

		return sb.toString();
	}

	public LearningActivityTry toEntityModel() {
		LearningActivityTryImpl learningActivityTryImpl = new LearningActivityTryImpl();

		if (uuid == null) {
			learningActivityTryImpl.setUuid(StringPool.BLANK);
		}
		else {
			learningActivityTryImpl.setUuid(uuid);
		}

		learningActivityTryImpl.setLatId(latId);
		learningActivityTryImpl.setActId(actId);
		learningActivityTryImpl.setUserId(userId);

		if (startDate == Long.MIN_VALUE) {
			learningActivityTryImpl.setStartDate(null);
		}
		else {
			learningActivityTryImpl.setStartDate(new Date(startDate));
		}

		learningActivityTryImpl.setResult(result);

		if (endDate == Long.MIN_VALUE) {
			learningActivityTryImpl.setEndDate(null);
		}
		else {
			learningActivityTryImpl.setEndDate(new Date(endDate));
		}

		if (tryData == null) {
			learningActivityTryImpl.setTryData(StringPool.BLANK);
		}
		else {
			learningActivityTryImpl.setTryData(tryData);
		}

		if (tryResultData == null) {
			learningActivityTryImpl.setTryResultData(StringPool.BLANK);
		}
		else {
			learningActivityTryImpl.setTryResultData(tryResultData);
		}

		if (comments == null) {
			learningActivityTryImpl.setComments(StringPool.BLANK);
		}
		else {
			learningActivityTryImpl.setComments(comments);
		}

		learningActivityTryImpl.resetOriginalValues();

		return learningActivityTryImpl;
	}

	public String uuid;
	public long latId;
	public long actId;
	public long userId;
	public long startDate;
	public long result;
	public long endDate;
	public String tryData;
	public String tryResultData;
	public String comments;
}