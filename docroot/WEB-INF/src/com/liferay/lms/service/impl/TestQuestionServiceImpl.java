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
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.lms.service.base.TestQuestionServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;

/**
 * The implementation of the test question remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.TestQuestionService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.TestQuestionServiceUtil} to access the test question remote service.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.TestQuestionServiceBaseImpl
 * @see com.liferay.lms.service.TestQuestionServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.MANUAL)
public class TestQuestionServiceImpl extends TestQuestionServiceBaseImpl 
{
	public TestQuestion addQuestion(long actId,String text,long questionType) throws SystemException,PortalException
	{
		 LearningActivity lernact=LearningActivityLocalServiceUtil.getLearningActivity(actId);
			if( getPermissionChecker().hasPermission(lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(),
					ActionKeys.UPDATE))
			{
				return TestQuestionLocalServiceUtil.addQuestion(actId, text, questionType);
			}
	
	else
	{
		return null;
	}
	}
	public java.util.List<TestQuestion> getQuestions(long actid) throws SystemException, PortalException
	{
		LearningActivity lernact=LearningActivityLocalServiceUtil.getLearningActivity(actid);
		if( getPermissionChecker().hasPermission(lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(),
				ActionKeys.UPDATE))
		{
	 return testQuestionPersistence.findByac(actid);
		}
		
		else
		{
			return null;
		}
	}
public TestQuestion getQuestion(long questionId) throws PortalException, SystemException 
{
TestQuestion testQuestion= TestQuestionLocalServiceUtil.getTestQuestion(questionId);
	LearningActivity lernact=LearningActivityLocalServiceUtil.getLearningActivity(testQuestion.getActId());
	if( getPermissionChecker().hasPermission(lernact.getGroupId(), LearningActivity.class.getName(), lernact.getActId(),
			ActionKeys.UPDATE))
	{
 return testQuestion;
	}

else
{
	return null;
}
}
}