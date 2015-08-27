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

import com.liferay.lms.model.TestAnswer;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

/**
 * The cache model class for representing TestAnswer in entity cache.
 *
 * @author TLS
 * @see TestAnswer
 * @generated
 */
public class TestAnswerCacheModel implements CacheModel<TestAnswer>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", answerId=");
		sb.append(answerId);
		sb.append(", questionId=");
		sb.append(questionId);
		sb.append(", actId=");
		sb.append(actId);
		sb.append(", precedence=");
		sb.append(precedence);
		sb.append(", answer=");
		sb.append(answer);
		sb.append(", isCorrect=");
		sb.append(isCorrect);
		sb.append(", feedbackCorrect=");
		sb.append(feedbackCorrect);
		sb.append(", feedbacknocorrect=");
		sb.append(feedbacknocorrect);
		sb.append("}");

		return sb.toString();
	}

	public TestAnswer toEntityModel() {
		TestAnswerImpl testAnswerImpl = new TestAnswerImpl();

		if (uuid == null) {
			testAnswerImpl.setUuid(StringPool.BLANK);
		}
		else {
			testAnswerImpl.setUuid(uuid);
		}

		testAnswerImpl.setAnswerId(answerId);
		testAnswerImpl.setQuestionId(questionId);
		testAnswerImpl.setActId(actId);
		testAnswerImpl.setPrecedence(precedence);

		if (answer == null) {
			testAnswerImpl.setAnswer(StringPool.BLANK);
		}
		else {
			testAnswerImpl.setAnswer(answer);
		}

		testAnswerImpl.setIsCorrect(isCorrect);

		if (feedbackCorrect == null) {
			testAnswerImpl.setFeedbackCorrect(StringPool.BLANK);
		}
		else {
			testAnswerImpl.setFeedbackCorrect(feedbackCorrect);
		}

		if (feedbacknocorrect == null) {
			testAnswerImpl.setFeedbacknocorrect(StringPool.BLANK);
		}
		else {
			testAnswerImpl.setFeedbacknocorrect(feedbacknocorrect);
		}

		testAnswerImpl.resetOriginalValues();

		return testAnswerImpl;
	}

	public String uuid;
	public long answerId;
	public long questionId;
	public long actId;
	public long precedence;
	public String answer;
	public boolean isCorrect;
	public String feedbackCorrect;
	public String feedbacknocorrect;
}