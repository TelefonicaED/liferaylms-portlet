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
		_methodName118 = "getBeanIdentifier";

		_methodParameterTypes118 = new String[] {  };

		_methodName119 = "setBeanIdentifier";

		_methodParameterTypes119 = new String[] { "java.lang.String" };

		_methodName124 = "getTestAnswersByQuestionId";

		_methodParameterTypes124 = new String[] { "long" };

		_methodName125 = "getTestAnswer";

		_methodParameterTypes125 = new String[] { "long" };

		_methodName126 = "modTestAnswer";

		_methodParameterTypes126 = new String[] {
				"com.liferay.lms.model.TestAnswer"
			};

		_methodName127 = "addTestAnswer";

		_methodParameterTypes127 = new String[] {
				"long", "java.lang.String", "java.lang.String",
				"java.lang.String", "boolean"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName118.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes118, parameterTypes)) {
			return TestAnswerServiceUtil.getBeanIdentifier();
		}

		if (_methodName119.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes119, parameterTypes)) {
			TestAnswerServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName124.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes124, parameterTypes)) {
			return TestAnswerServiceUtil.getTestAnswersByQuestionId(((Long)arguments[0]).longValue());
		}

		if (_methodName125.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes125, parameterTypes)) {
			return TestAnswerServiceUtil.getTestAnswer(((Long)arguments[0]).longValue());
		}

		if (_methodName126.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes126, parameterTypes)) {
			return TestAnswerServiceUtil.modTestAnswer((com.liferay.lms.model.TestAnswer)arguments[0]);
		}

		if (_methodName127.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes127, parameterTypes)) {
			return TestAnswerServiceUtil.addTestAnswer(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3],
				((Boolean)arguments[4]).booleanValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName118;
	private String[] _methodParameterTypes118;
	private String _methodName119;
	private String[] _methodParameterTypes119;
	private String _methodName124;
	private String[] _methodParameterTypes124;
	private String _methodName125;
	private String[] _methodParameterTypes125;
	private String _methodName126;
	private String[] _methodParameterTypes126;
	private String _methodName127;
	private String[] _methodParameterTypes127;
}