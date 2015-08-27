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

package com.liferay.lms.service.base;

import com.liferay.lms.service.LearningActivityServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class LearningActivityServiceClpInvoker {
	public LearningActivityServiceClpInvoker() {
		_methodName150 = "getBeanIdentifier";

		_methodParameterTypes150 = new String[] {  };

		_methodName151 = "setBeanIdentifier";

		_methodParameterTypes151 = new String[] { "java.lang.String" };

		_methodName156 = "getLearningActivitiesOfGroup";

		_methodParameterTypes156 = new String[] { "long" };

		_methodName157 = "getLearningActivitiesOfModule";

		_methodParameterTypes157 = new String[] { "long" };

		_methodName158 = "deleteLearningactivity";

		_methodParameterTypes158 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName159 = "deleteLearningactivity";

		_methodParameterTypes159 = new String[] { "long" };

		_methodName160 = "getLearningActivity";

		_methodParameterTypes160 = new String[] { "long" };

		_methodName161 = "addLearningActivity";

		_methodParameterTypes161 = new String[] {
				"java.lang.String", "java.lang.String", "java.util.Date",
				"java.util.Date", "java.util.Date", "int", "long", "int", "long",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName162 = "modLearningActivity";

		_methodParameterTypes162 = new String[] {
				"com.liferay.lms.model.LearningActivity",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName163 = "modLearningActivity";

		_methodParameterTypes163 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName164 = "modLearningActivity";

		_methodParameterTypes164 = new String[] {
				"long", "java.lang.String", "java.lang.String", "java.util.Date",
				"java.util.Date", "java.util.Date", "int", "long", "int", "long",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName165 = "isLocked";

		_methodParameterTypes165 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName150.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes150, parameterTypes)) {
			return LearningActivityServiceUtil.getBeanIdentifier();
		}

		if (_methodName151.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes151, parameterTypes)) {
			LearningActivityServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName156.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes156, parameterTypes)) {
			return LearningActivityServiceUtil.getLearningActivitiesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName157.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes157, parameterTypes)) {
			return LearningActivityServiceUtil.getLearningActivitiesOfModule(((Long)arguments[0]).longValue());
		}

		if (_methodName158.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes158, parameterTypes)) {
			LearningActivityServiceUtil.deleteLearningactivity((com.liferay.lms.model.LearningActivity)arguments[0]);

			return null;
		}

		if (_methodName159.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes159, parameterTypes)) {
			LearningActivityServiceUtil.deleteLearningactivity(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName160.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes160, parameterTypes)) {
			return LearningActivityServiceUtil.getLearningActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName161.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes161, parameterTypes)) {
			return LearningActivityServiceUtil.addLearningActivity((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.util.Date)arguments[2],
				(java.util.Date)arguments[3], (java.util.Date)arguments[4],
				((Integer)arguments[5]).intValue(),
				((Long)arguments[6]).longValue(),
				((Integer)arguments[7]).intValue(),
				((Long)arguments[8]).longValue(),
				(com.liferay.portal.service.ServiceContext)arguments[9]);
		}

		if (_methodName162.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes162, parameterTypes)) {
			return LearningActivityServiceUtil.modLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0],
				(com.liferay.portal.service.ServiceContext)arguments[1]);
		}

		if (_methodName163.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes163, parameterTypes)) {
			return LearningActivityServiceUtil.modLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName164.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes164, parameterTypes)) {
			return LearningActivityServiceUtil.modLearningActivity(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.util.Date)arguments[3], (java.util.Date)arguments[4],
				(java.util.Date)arguments[5],
				((Integer)arguments[6]).intValue(),
				((Long)arguments[7]).longValue(),
				((Integer)arguments[8]).intValue(),
				((Long)arguments[9]).longValue(),
				(com.liferay.portal.service.ServiceContext)arguments[10]);
		}

		if (_methodName165.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes165, parameterTypes)) {
			return LearningActivityServiceUtil.isLocked(((Long)arguments[0]).longValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName150;
	private String[] _methodParameterTypes150;
	private String _methodName151;
	private String[] _methodParameterTypes151;
	private String _methodName156;
	private String[] _methodParameterTypes156;
	private String _methodName157;
	private String[] _methodParameterTypes157;
	private String _methodName158;
	private String[] _methodParameterTypes158;
	private String _methodName159;
	private String[] _methodParameterTypes159;
	private String _methodName160;
	private String[] _methodParameterTypes160;
	private String _methodName161;
	private String[] _methodParameterTypes161;
	private String _methodName162;
	private String[] _methodParameterTypes162;
	private String _methodName163;
	private String[] _methodParameterTypes163;
	private String _methodName164;
	private String[] _methodParameterTypes164;
	private String _methodName165;
	private String[] _methodParameterTypes165;
}