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

package com.liferay.lms.service.messaging;

import com.liferay.lms.service.AuditEntryLocalServiceUtil;
import com.liferay.lms.service.CheckP2pMailingLocalServiceUtil;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CompetenceLocalServiceUtil;
import com.liferay.lms.service.CompetenceServiceUtil;
import com.liferay.lms.service.CourseCompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseCompetenceServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.CourseResultServiceUtil;
import com.liferay.lms.service.CourseServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.ModuleResultServiceUtil;
import com.liferay.lms.service.ModuleServiceUtil;
import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.lms.service.SCORMContentLocalServiceUtil;
import com.liferay.lms.service.SCORMContentServiceUtil;
import com.liferay.lms.service.SurveyResultLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestAnswerServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.lms.service.TestQuestionServiceUtil;
import com.liferay.lms.service.UserCompetenceLocalServiceUtil;
import com.liferay.lms.service.UserCompetenceServiceUtil;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Brian Wing Shun Chan
 */
public class ClpMessageListener extends BaseMessageListener {
	public static String getServletContextName() {
		return ClpSerializer.getServletContextName();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		String command = message.getString("command");
		String servletContextName = message.getString("servletContextName");

		if (command.equals("undeploy") &&
				servletContextName.equals(getServletContextName())) {
			AuditEntryLocalServiceUtil.clearService();

			CheckP2pMailingLocalServiceUtil.clearService();

			CompetenceLocalServiceUtil.clearService();

			CompetenceServiceUtil.clearService();
			CourseLocalServiceUtil.clearService();

			CourseServiceUtil.clearService();
			CourseCompetenceLocalServiceUtil.clearService();

			CourseCompetenceServiceUtil.clearService();
			CourseResultLocalServiceUtil.clearService();

			CourseResultServiceUtil.clearService();
			LearningActivityLocalServiceUtil.clearService();

			LearningActivityServiceUtil.clearService();
			LearningActivityResultLocalServiceUtil.clearService();

			LearningActivityResultServiceUtil.clearService();
			LearningActivityTryLocalServiceUtil.clearService();

			LearningActivityTryServiceUtil.clearService();
			LmsPrefsLocalServiceUtil.clearService();

			ModuleLocalServiceUtil.clearService();

			ModuleServiceUtil.clearService();
			ModuleResultLocalServiceUtil.clearService();

			ModuleResultServiceUtil.clearService();
			P2pActivityLocalServiceUtil.clearService();

			P2pActivityCorrectionsLocalServiceUtil.clearService();

			SCORMContentLocalServiceUtil.clearService();

			SCORMContentServiceUtil.clearService();
			SurveyResultLocalServiceUtil.clearService();

			TestAnswerLocalServiceUtil.clearService();

			TestAnswerServiceUtil.clearService();
			TestQuestionLocalServiceUtil.clearService();

			TestQuestionServiceUtil.clearService();
			UserCompetenceLocalServiceUtil.clearService();

			UserCompetenceServiceUtil.clearService();
		}
	}
}