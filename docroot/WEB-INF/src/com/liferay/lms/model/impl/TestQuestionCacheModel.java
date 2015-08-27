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

import com.liferay.lms.model.TestQuestion;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

/**
 * The cache model class for representing TestQuestion in entity cache.
 *
 * @author TLS
 * @see TestQuestion
 * @generated
 */
public class TestQuestionCacheModel implements CacheModel<TestQuestion>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", questionId=");
		sb.append(questionId);
		sb.append(", actId=");
		sb.append(actId);
		sb.append(", text=");
		sb.append(text);
		sb.append(", questionType=");
		sb.append(questionType);
		sb.append(", weight=");
		sb.append(weight);
		sb.append("}");

		return sb.toString();
	}

	public TestQuestion toEntityModel() {
		TestQuestionImpl testQuestionImpl = new TestQuestionImpl();

		if (uuid == null) {
			testQuestionImpl.setUuid(StringPool.BLANK);
		}
		else {
			testQuestionImpl.setUuid(uuid);
		}

		testQuestionImpl.setQuestionId(questionId);
		testQuestionImpl.setActId(actId);

		if (text == null) {
			testQuestionImpl.setText(StringPool.BLANK);
		}
		else {
			testQuestionImpl.setText(text);
		}

		testQuestionImpl.setQuestionType(questionType);
		testQuestionImpl.setWeight(weight);

		testQuestionImpl.resetOriginalValues();

		return testQuestionImpl;
	}

	public String uuid;
	public long questionId;
	public long actId;
	public String text;
	public long questionType;
	public long weight;
}