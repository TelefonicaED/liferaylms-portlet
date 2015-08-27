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

import com.liferay.lms.model.SurveyResult;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

/**
 * The cache model class for representing SurveyResult in entity cache.
 *
 * @author TLS
 * @see SurveyResult
 * @generated
 */
public class SurveyResultCacheModel implements CacheModel<SurveyResult>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", surveyResultId=");
		sb.append(surveyResultId);
		sb.append(", actId=");
		sb.append(actId);
		sb.append(", latId=");
		sb.append(latId);
		sb.append(", questionId=");
		sb.append(questionId);
		sb.append(", answerId=");
		sb.append(answerId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", freeAnswer=");
		sb.append(freeAnswer);
		sb.append("}");

		return sb.toString();
	}

	public SurveyResult toEntityModel() {
		SurveyResultImpl surveyResultImpl = new SurveyResultImpl();

		if (uuid == null) {
			surveyResultImpl.setUuid(StringPool.BLANK);
		}
		else {
			surveyResultImpl.setUuid(uuid);
		}

		surveyResultImpl.setSurveyResultId(surveyResultId);
		surveyResultImpl.setActId(actId);
		surveyResultImpl.setLatId(latId);
		surveyResultImpl.setQuestionId(questionId);
		surveyResultImpl.setAnswerId(answerId);
		surveyResultImpl.setUserId(userId);

		if (freeAnswer == null) {
			surveyResultImpl.setFreeAnswer(StringPool.BLANK);
		}
		else {
			surveyResultImpl.setFreeAnswer(freeAnswer);
		}

		surveyResultImpl.resetOriginalValues();

		return surveyResultImpl;
	}

	public String uuid;
	public long surveyResultId;
	public long actId;
	public long latId;
	public long questionId;
	public long answerId;
	public long userId;
	public String freeAnswer;
}