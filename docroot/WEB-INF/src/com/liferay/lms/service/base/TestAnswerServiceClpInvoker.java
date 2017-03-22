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

import com.liferay.lms.service.TestAnswerServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class TestAnswerServiceClpInvoker {
	public TestAnswerServiceClpInvoker() {
		_methodName148 = "getBeanIdentifier";

		_methodParameterTypes148 = new String[] {  };

		_methodName149 = "setBeanIdentifier";

		_methodParameterTypes149 = new String[] { "java.lang.String" };

		_methodName154 = "getTestAnswersByQuestionId";

		_methodParameterTypes154 = new String[] { "long" };

		_methodName155 = "getTestAnswer";

		_methodParameterTypes155 = new String[] { "long" };

		_methodName156 = "modTestAnswer";

		_methodParameterTypes156 = new String[] {
				"com.liferay.lms.model.TestAnswer"
			};

		_methodName157 = "addTestAnswer";

		_methodParameterTypes157 = new String[] {
				"long", "java.lang.String", "java.lang.String",
				"java.lang.String", "boolean"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName148.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes148, parameterTypes)) {
			return TestAnswerServiceUtil.getBeanIdentifier();
		}

		if (_methodName149.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes149, parameterTypes)) {
			TestAnswerServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);
		}

		if (_methodName154.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes154, parameterTypes)) {
			return TestAnswerServiceUtil.getTestAnswersByQuestionId(((Long)arguments[0]).longValue());
		}

		if (_methodName155.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes155, parameterTypes)) {
			return TestAnswerServiceUtil.getTestAnswer(((Long)arguments[0]).longValue());
		}

		if (_methodName156.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes156, parameterTypes)) {
			return TestAnswerServiceUtil.modTestAnswer((com.liferay.lms.model.TestAnswer)arguments[0]);
		}

		if (_methodName157.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes157, parameterTypes)) {
			return TestAnswerServiceUtil.addTestAnswer(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3],
				((Boolean)arguments[4]).booleanValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName148;
	private String[] _methodParameterTypes148;
	private String _methodName149;
	private String[] _methodParameterTypes149;
	private String _methodName154;
	private String[] _methodParameterTypes154;
	private String _methodName155;
	private String[] _methodParameterTypes155;
	private String _methodName156;
	private String[] _methodParameterTypes156;
	private String _methodName157;
	private String[] _methodParameterTypes157;
}