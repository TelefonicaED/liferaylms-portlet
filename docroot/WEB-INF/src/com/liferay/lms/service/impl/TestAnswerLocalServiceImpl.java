/**
 * 2012 TELEFONICA LEARNING SERVICES. All rights reserved.
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

package com.liferay.lms.service.impl;

import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.lms.service.base.TestAnswerLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the test answer local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.TestAnswerLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.TestAnswerLocalServiceUtil} to access the test answer local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.TestAnswerLocalServiceBaseImpl
 * @see com.liferay.lms.service.TestAnswerLocalServiceUtil
 */
public class TestAnswerLocalServiceImpl extends TestAnswerLocalServiceBaseImpl {
	public java.util.List<TestAnswer> getTestAnswersByQuestionId(long questionId) throws SystemException
	{
		return testAnswerPersistence.findByq(questionId);
	}
	public TestAnswer addTestAnswer(long questionId, String answer,String feedbackCorrect,String feedbacknocorrect, boolean correct) throws SystemException, PortalException
	{
		long primKey=counterLocalService.increment(TestAnswer.class.getName());
		TestAnswer testanswer=testAnswerPersistence.create(primKey);
		testanswer.setQuestionId(questionId);
		TestQuestion question=TestQuestionLocalServiceUtil.getTestQuestion(questionId);
		testanswer.setActId(question.getActId());
		testanswer.setAnswer(answer);
		testanswer.setIsCorrect(correct);
		testanswer.setFeedbackCorrect(feedbackCorrect);
		testanswer.setFeedbacknocorrect(feedbacknocorrect);
		testAnswerPersistence.update(testanswer, true);
		return testanswer;
	}
}