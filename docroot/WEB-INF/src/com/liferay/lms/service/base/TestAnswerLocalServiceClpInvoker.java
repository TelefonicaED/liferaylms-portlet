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

import com.liferay.lms.service.TestAnswerLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class TestAnswerLocalServiceClpInvoker {
	public TestAnswerLocalServiceClpInvoker() {
		_methodName0 = "addTestAnswer";

		_methodParameterTypes0 = new String[] { "com.liferay.lms.model.TestAnswer" };

		_methodName1 = "createTestAnswer";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteTestAnswer";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteTestAnswer";

		_methodParameterTypes3 = new String[] { "com.liferay.lms.model.TestAnswer" };

		_methodName4 = "dynamicQuery";

		_methodParameterTypes4 = new String[] {  };

		_methodName5 = "dynamicQuery";

		_methodParameterTypes5 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName6 = "dynamicQuery";

		_methodParameterTypes6 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int"
			};

		_methodName7 = "dynamicQuery";

		_methodParameterTypes7 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName8 = "dynamicQueryCount";

		_methodParameterTypes8 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName9 = "fetchTestAnswer";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getTestAnswer";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getTestAnswers";

		_methodParameterTypes12 = new String[] { "int", "int" };

		_methodName13 = "getTestAnswersCount";

		_methodParameterTypes13 = new String[] {  };

		_methodName14 = "updateTestAnswer";

		_methodParameterTypes14 = new String[] {
				"com.liferay.lms.model.TestAnswer"
			};

		_methodName15 = "updateTestAnswer";

		_methodParameterTypes15 = new String[] {
				"com.liferay.lms.model.TestAnswer", "boolean"
			};

		_methodName134 = "getBeanIdentifier";

		_methodParameterTypes134 = new String[] {  };

		_methodName135 = "setBeanIdentifier";

		_methodParameterTypes135 = new String[] { "java.lang.String" };

		_methodName140 = "getTestAnswersByQuestionId";

		_methodParameterTypes140 = new String[] { "long" };

		_methodName141 = "addTestAnswer";

		_methodParameterTypes141 = new String[] {
				"long", "java.lang.String", "java.lang.String",
				"java.lang.String", "boolean"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return TestAnswerLocalServiceUtil.addTestAnswer((com.liferay.lms.model.TestAnswer)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return TestAnswerLocalServiceUtil.createTestAnswer(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return TestAnswerLocalServiceUtil.deleteTestAnswer(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return TestAnswerLocalServiceUtil.deleteTestAnswer((com.liferay.lms.model.TestAnswer)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return TestAnswerLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return TestAnswerLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return TestAnswerLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return TestAnswerLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return TestAnswerLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return TestAnswerLocalServiceUtil.fetchTestAnswer(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return TestAnswerLocalServiceUtil.getTestAnswer(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return TestAnswerLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return TestAnswerLocalServiceUtil.getTestAnswers(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return TestAnswerLocalServiceUtil.getTestAnswersCount();
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return TestAnswerLocalServiceUtil.updateTestAnswer((com.liferay.lms.model.TestAnswer)arguments[0]);
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return TestAnswerLocalServiceUtil.updateTestAnswer((com.liferay.lms.model.TestAnswer)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName134.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes134, parameterTypes)) {
			return TestAnswerLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName135.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes135, parameterTypes)) {
			TestAnswerLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName140.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes140, parameterTypes)) {
			return TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(((Long)arguments[0]).longValue());
		}

		if (_methodName141.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes141, parameterTypes)) {
			return TestAnswerLocalServiceUtil.addTestAnswer(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3],
				((Boolean)arguments[4]).booleanValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName0;
	private String[] _methodParameterTypes0;
	private String _methodName1;
	private String[] _methodParameterTypes1;
	private String _methodName2;
	private String[] _methodParameterTypes2;
	private String _methodName3;
	private String[] _methodParameterTypes3;
	private String _methodName4;
	private String[] _methodParameterTypes4;
	private String _methodName5;
	private String[] _methodParameterTypes5;
	private String _methodName6;
	private String[] _methodParameterTypes6;
	private String _methodName7;
	private String[] _methodParameterTypes7;
	private String _methodName8;
	private String[] _methodParameterTypes8;
	private String _methodName9;
	private String[] _methodParameterTypes9;
	private String _methodName10;
	private String[] _methodParameterTypes10;
	private String _methodName11;
	private String[] _methodParameterTypes11;
	private String _methodName12;
	private String[] _methodParameterTypes12;
	private String _methodName13;
	private String[] _methodParameterTypes13;
	private String _methodName14;
	private String[] _methodParameterTypes14;
	private String _methodName15;
	private String[] _methodParameterTypes15;
	private String _methodName134;
	private String[] _methodParameterTypes134;
	private String _methodName135;
	private String[] _methodParameterTypes135;
	private String _methodName140;
	private String[] _methodParameterTypes140;
	private String _methodName141;
	private String[] _methodParameterTypes141;
}