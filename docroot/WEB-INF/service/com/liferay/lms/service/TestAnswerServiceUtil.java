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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableService;

/**
 * The utility for the test answer remote service. This utility wraps {@link com.liferay.lms.service.impl.TestAnswerServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author TLS
 * @see TestAnswerService
 * @see com.liferay.lms.service.base.TestAnswerServiceBaseImpl
 * @see com.liferay.lms.service.impl.TestAnswerServiceImpl
 * @generated
 */
public class TestAnswerServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.TestAnswerServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static java.util.List<com.liferay.lms.model.TestAnswer> getTestAnswersByQuestionId(
		long questionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTestAnswersByQuestionId(questionId);
	}

	public static com.liferay.lms.model.TestAnswer getTestAnswer(long answerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTestAnswer(answerId);
	}

	public static com.liferay.lms.model.TestAnswer modTestAnswer(
		com.liferay.lms.model.TestAnswer testAnswer)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().modTestAnswer(testAnswer);
	}

	public static com.liferay.lms.model.TestAnswer addTestAnswer(
		long questionId, java.lang.String answer,
		java.lang.String feedbackCorrect, java.lang.String feedbacknocorrect,
		boolean correct)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addTestAnswer(questionId, answer, feedbackCorrect,
			feedbacknocorrect, correct);
	}

	public static void clearService() {
		_service = null;
	}

	public static TestAnswerService getService() {
		if (_service == null) {
			InvokableService invokableService = (InvokableService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					TestAnswerService.class.getName());

			if (invokableService instanceof TestAnswerService) {
				_service = (TestAnswerService)invokableService;
			}
			else {
				_service = new TestAnswerServiceClp(invokableService);
			}

			ReferenceRegistry.registerReference(TestAnswerServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(TestAnswerService service) {
	}

	private static TestAnswerService _service;
}