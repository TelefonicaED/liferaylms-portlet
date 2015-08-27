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

package com.liferay.lms.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link TestQuestionService}.
 * </p>
 *
 * @author    TLS
 * @see       TestQuestionService
 * @generated
 */
public class TestQuestionServiceWrapper implements TestQuestionService,
	ServiceWrapper<TestQuestionService> {
	public TestQuestionServiceWrapper(TestQuestionService testQuestionService) {
		_testQuestionService = testQuestionService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _testQuestionService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_testQuestionService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _testQuestionService.invokeMethod(name, parameterTypes, arguments);
	}

	public com.liferay.lms.model.TestQuestion addQuestion(long actId,
		java.lang.String text, long questionType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _testQuestionService.addQuestion(actId, text, questionType);
	}

	public java.util.List<com.liferay.lms.model.TestQuestion> getQuestions(
		long actid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _testQuestionService.getQuestions(actid);
	}

	public com.liferay.lms.model.TestQuestion getQuestion(long questionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _testQuestionService.getQuestion(questionId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public TestQuestionService getWrappedTestQuestionService() {
		return _testQuestionService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedTestQuestionService(
		TestQuestionService testQuestionService) {
		_testQuestionService = testQuestionService;
	}

	public TestQuestionService getWrappedService() {
		return _testQuestionService;
	}

	public void setWrappedService(TestQuestionService testQuestionService) {
		_testQuestionService = testQuestionService;
	}

	private TestQuestionService _testQuestionService;
}