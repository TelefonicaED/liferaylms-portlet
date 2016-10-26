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

import com.liferay.lms.service.TestQuestionLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class TestQuestionLocalServiceClpInvoker {
	public TestQuestionLocalServiceClpInvoker() {
		_methodName0 = "addTestQuestion";

		_methodParameterTypes0 = new String[] {
				"com.liferay.lms.model.TestQuestion"
			};

		_methodName1 = "createTestQuestion";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteTestQuestion";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteTestQuestion";

		_methodParameterTypes3 = new String[] {
				"com.liferay.lms.model.TestQuestion"
			};

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

		_methodName9 = "fetchTestQuestion";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getTestQuestion";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getTestQuestions";

		_methodParameterTypes12 = new String[] { "int", "int" };

		_methodName13 = "getTestQuestionsCount";

		_methodParameterTypes13 = new String[] {  };

		_methodName14 = "updateTestQuestion";

		_methodParameterTypes14 = new String[] {
				"com.liferay.lms.model.TestQuestion"
			};

		_methodName15 = "updateTestQuestion";

		_methodParameterTypes15 = new String[] {
				"com.liferay.lms.model.TestQuestion", "boolean"
			};

		_methodName154 = "getBeanIdentifier";

		_methodParameterTypes154 = new String[] {  };

		_methodName155 = "setBeanIdentifier";

		_methodParameterTypes155 = new String[] { "java.lang.String" };

		_methodName160 = "importXML";

		_methodParameterTypes160 = new String[] {
				"long", "com.liferay.portal.kernel.xml.Document"
			};

		_methodName161 = "importXMLQuestion";

		_methodParameterTypes161 = new String[] {
				"long", "com.liferay.portal.kernel.xml.Element"
			};

		_methodName162 = "getQuestionType";

		_methodParameterTypes162 = new String[] {
				"com.liferay.portal.kernel.xml.Element"
			};

		_methodName163 = "isTypeAllowed";

		_methodParameterTypes163 = new String[] {
				"long", "com.liferay.portal.kernel.xml.Document"
			};

		_methodName164 = "addQuestion";

		_methodParameterTypes164 = new String[] {
				"long", "java.lang.String", "long"
			};

		_methodName165 = "getQuestions";

		_methodParameterTypes165 = new String[] { "long" };

		_methodName166 = "initializeQuestionType";

		_methodParameterTypes166 = new String[] { "long" };

		_methodName167 = "updateQuestionWithoutWeight";

		_methodParameterTypes167 = new String[] { "long" };

		_methodName168 = "getPreviusTestQuestion";

		_methodParameterTypes168 = new String[] { "long" };

		_methodName169 = "getPreviusTestQuestion";

		_methodParameterTypes169 = new String[] {
				"com.liferay.lms.model.TestQuestion"
			};

		_methodName170 = "getNextTestQuestion";

		_methodParameterTypes170 = new String[] { "long" };

		_methodName171 = "getNextTestQuestion";

		_methodParameterTypes171 = new String[] {
				"com.liferay.lms.model.TestQuestion"
			};

		_methodName172 = "goUpTestQuestion";

		_methodParameterTypes172 = new String[] { "long" };

		_methodName173 = "goDownTestQuestion";

		_methodParameterTypes173 = new String[] { "long" };

		_methodName174 = "moveQuestion";

		_methodParameterTypes174 = new String[] { "long", "long", "long" };

		_methodName175 = "checkWeights";

		_methodParameterTypes175 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return TestQuestionLocalServiceUtil.addTestQuestion((com.liferay.lms.model.TestQuestion)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return TestQuestionLocalServiceUtil.createTestQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return TestQuestionLocalServiceUtil.deleteTestQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return TestQuestionLocalServiceUtil.deleteTestQuestion((com.liferay.lms.model.TestQuestion)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return TestQuestionLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return TestQuestionLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return TestQuestionLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return TestQuestionLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return TestQuestionLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return TestQuestionLocalServiceUtil.fetchTestQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getTestQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getTestQuestions(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getTestQuestionsCount();
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return TestQuestionLocalServiceUtil.updateTestQuestion((com.liferay.lms.model.TestQuestion)arguments[0]);
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return TestQuestionLocalServiceUtil.updateTestQuestion((com.liferay.lms.model.TestQuestion)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName154.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes154, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName155.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes155, parameterTypes)) {
			TestQuestionLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);
		}

		if (_methodName160.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes160, parameterTypes)) {
			TestQuestionLocalServiceUtil.importXML(((Long)arguments[0]).longValue(),
				(com.liferay.portal.kernel.xml.Document)arguments[1]);
		}

		if (_methodName161.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes161, parameterTypes)) {
			TestQuestionLocalServiceUtil.importXMLQuestion(((Long)arguments[0]).longValue(),
				(com.liferay.portal.kernel.xml.Element)arguments[1]);
		}

		if (_methodName162.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes162, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getQuestionType((com.liferay.portal.kernel.xml.Element)arguments[0]);
		}

		if (_methodName163.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes163, parameterTypes)) {
			return TestQuestionLocalServiceUtil.isTypeAllowed(((Long)arguments[0]).longValue(),
				(com.liferay.portal.kernel.xml.Document)arguments[1]);
		}

		if (_methodName164.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes164, parameterTypes)) {
			return TestQuestionLocalServiceUtil.addQuestion(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], ((Long)arguments[2]).longValue());
		}

		if (_methodName165.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes165, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getQuestions(((Long)arguments[0]).longValue());
		}

		if (_methodName166.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes166, parameterTypes)) {
			return TestQuestionLocalServiceUtil.initializeQuestionType(((Long)arguments[0]).longValue());
		}

		if (_methodName167.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes167, parameterTypes)) {
			return TestQuestionLocalServiceUtil.updateQuestionWithoutWeight(((Long)arguments[0]).longValue());
		}

		if (_methodName168.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes168, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getPreviusTestQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName169.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes169, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getPreviusTestQuestion((com.liferay.lms.model.TestQuestion)arguments[0]);
		}

		if (_methodName170.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes170, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getNextTestQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName171.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes171, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getNextTestQuestion((com.liferay.lms.model.TestQuestion)arguments[0]);
		}

		if (_methodName172.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes172, parameterTypes)) {
			TestQuestionLocalServiceUtil.goUpTestQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName173.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes173, parameterTypes)) {
			TestQuestionLocalServiceUtil.goDownTestQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName174.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes174, parameterTypes)) {
			TestQuestionLocalServiceUtil.moveQuestion(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue());
		}

		if (_methodName175.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes175, parameterTypes)) {
			TestQuestionLocalServiceUtil.checkWeights(((Long)arguments[0]).longValue());
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
	private String _methodName154;
	private String[] _methodParameterTypes154;
	private String _methodName155;
	private String[] _methodParameterTypes155;
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
	private String _methodName166;
	private String[] _methodParameterTypes166;
	private String _methodName167;
	private String[] _methodParameterTypes167;
	private String _methodName168;
	private String[] _methodParameterTypes168;
	private String _methodName169;
	private String[] _methodParameterTypes169;
	private String _methodName170;
	private String[] _methodParameterTypes170;
	private String _methodName171;
	private String[] _methodParameterTypes171;
	private String _methodName172;
	private String[] _methodParameterTypes172;
	private String _methodName173;
	private String[] _methodParameterTypes173;
	private String _methodName174;
	private String[] _methodParameterTypes174;
	private String _methodName175;
	private String[] _methodParameterTypes175;
}