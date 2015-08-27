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

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceWrapper;
import com.liferay.lms.service.base.TestAnswerServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.security.permission.ActionKeys;

/**
 * The implementation of the test answer remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.TestAnswerService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.TestAnswerServiceUtil} to access the test answer remote service.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.TestAnswerServiceBaseImpl
 * @see com.liferay.lms.service.TestAnswerServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.MANUAL)
public class TestAnswerServiceImpl extends TestAnswerServiceBaseImpl
{
	public java.util.List<TestAnswer> getTestAnswersByQuestionId(long questionId) throws SystemException,PortalException
	{
		 TestQuestion question=TestQuestionLocalServiceUtil.getTestQuestion(questionId);
		    LearningActivity lernact=LearningActivityLocalServiceUtil.getLearningActivity(question.getActId());
			if( getPermissionChecker().hasPermission(lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(),
					ActionKeys.UPDATE))
			{
				return testAnswerPersistence.findByq(questionId);
			}
	else
	{
		return null;
	}
	}
	public TestAnswer getTestAnswer(long answerId) throws SystemException,PortalException
	{
		TestAnswer testAnswer=TestAnswerLocalServiceUtil.getTestAnswer(answerId);
		TestQuestion question=TestQuestionLocalServiceUtil.getTestQuestion(testAnswer.getQuestionId());
	    LearningActivity lernact=LearningActivityLocalServiceUtil.getLearningActivity(question.getActId());
		if( getPermissionChecker().hasPermission(lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(),
				ActionKeys.UPDATE))
		{
			return testAnswer;
		}
		else
		{
			return null;
		}
	}
	public TestAnswer modTestAnswer(TestAnswer testAnswer) throws SystemException,PortalException
	{
		TestQuestion question=TestQuestionLocalServiceUtil.getTestQuestion(testAnswer.getQuestionId());
	    LearningActivity lernact=LearningActivityLocalServiceUtil.getLearningActivity(question.getActId());
		if( getPermissionChecker().hasPermission(lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(),
				ActionKeys.UPDATE))
		{
		return TestAnswerLocalServiceUtil.updateTestAnswer(testAnswer);
		}
		else
		{
			return null;
		}
	}
	public TestAnswer addTestAnswer(long questionId, String answer,String feedbackCorrect,String feedbacknocorrect, boolean correct) throws SystemException, PortalException
	{
		
	    TestQuestion question=TestQuestionLocalServiceUtil.getTestQuestion(questionId);
	    LearningActivity lernact=LearningActivityLocalServiceUtil.getLearningActivity(question.getActId());
		if( getPermissionChecker().hasPermission(lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(),
				ActionKeys.UPDATE))
		{
		return TestAnswerLocalServiceUtil.addTestAnswer(questionId, answer, feedbackCorrect, feedbacknocorrect, correct);
		}
		else
		{
			return null;
		}
	}
}