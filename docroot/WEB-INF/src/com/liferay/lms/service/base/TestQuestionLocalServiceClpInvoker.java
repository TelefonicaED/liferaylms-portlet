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

		_methodName164 = "getBeanIdentifier";

		_methodParameterTypes164 = new String[] {  };

		_methodName165 = "setBeanIdentifier";

		_methodParameterTypes165 = new String[] { "java.lang.String" };

		_methodName170 = "importXML";

		_methodParameterTypes170 = new String[] {
				"long", "com.liferay.portal.kernel.xml.Document"
			};

		_methodName171 = "importXMLQuestion";

		_methodParameterTypes171 = new String[] {
				"long", "com.liferay.portal.kernel.xml.Element"
			};

		_methodName172 = "getQuestionType";

		_methodParameterTypes172 = new String[] {
				"com.liferay.portal.kernel.xml.Element"
			};

		_methodName173 = "addQuestion";

		_methodParameterTypes173 = new String[] {
				"long", "java.lang.String", "long"
			};

		_methodName174 = "addQuestion";

		_methodParameterTypes174 = new String[] {
				"long", "java.lang.String", "long", "java.lang.String"
			};

		_methodName175 = "getQuestions";

		_methodParameterTypes175 = new String[] { "long" };

		_methodName176 = "initializeQuestionType";

		_methodParameterTypes176 = new String[] { "long" };

		_methodName177 = "updateQuestionWithoutWeight";

		_methodParameterTypes177 = new String[] { "long" };

		_methodName178 = "getPreviusTestQuestion";

		_methodParameterTypes178 = new String[] { "long" };

		_methodName179 = "getPreviusTestQuestion";

		_methodParameterTypes179 = new String[] {
				"com.liferay.lms.model.TestQuestion"
			};

		_methodName180 = "getNextTestQuestion";

		_methodParameterTypes180 = new String[] { "long" };

		_methodName181 = "getNextTestQuestion";

		_methodParameterTypes181 = new String[] {
				"com.liferay.lms.model.TestQuestion"
			};

		_methodName182 = "goUpTestQuestion";

		_methodParameterTypes182 = new String[] { "long" };

		_methodName183 = "goDownTestQuestion";

		_methodParameterTypes183 = new String[] { "long" };

		_methodName184 = "moveQuestion";

		_methodParameterTypes184 = new String[] { "long", "long", "long" };

		_methodName185 = "checkWeights";

		_methodParameterTypes185 = new String[] { "long" };

		_methodName186 = "generateAleatoryQuestions";

		_methodParameterTypes186 = new String[] { "long", "long" };

		_methodName187 = "isTypeAllowed";

		_methodParameterTypes187 = new String[] {
				"long", "com.liferay.portal.kernel.xml.Document"
			};
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

		if (_methodName164.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes164, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName165.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes165, parameterTypes)) {
			TestQuestionLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);
		}

		if (_methodName170.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes170, parameterTypes)) {
			TestQuestionLocalServiceUtil.importXML(((Long)arguments[0]).longValue(),
				(com.liferay.portal.kernel.xml.Document)arguments[1]);
		}

		if (_methodName171.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes171, parameterTypes)) {
			TestQuestionLocalServiceUtil.importXMLQuestion(((Long)arguments[0]).longValue(),
				(com.liferay.portal.kernel.xml.Element)arguments[1]);
		}

		if (_methodName172.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes172, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getQuestionType((com.liferay.portal.kernel.xml.Element)arguments[0]);
		}

		if (_methodName173.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes173, parameterTypes)) {
			return TestQuestionLocalServiceUtil.addQuestion(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], ((Long)arguments[2]).longValue());
		}

		if (_methodName174.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes174, parameterTypes)) {
			return TestQuestionLocalServiceUtil.addQuestion(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1],
				((Long)arguments[2]).longValue(), (java.lang.String)arguments[3]);
		}

		if (_methodName175.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes175, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getQuestions(((Long)arguments[0]).longValue());
		}

		if (_methodName176.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes176, parameterTypes)) {
			return TestQuestionLocalServiceUtil.initializeQuestionType(((Long)arguments[0]).longValue());
		}

		if (_methodName177.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes177, parameterTypes)) {
			return TestQuestionLocalServiceUtil.updateQuestionWithoutWeight(((Long)arguments[0]).longValue());
		}

		if (_methodName178.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes178, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getPreviusTestQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName179.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes179, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getPreviusTestQuestion((com.liferay.lms.model.TestQuestion)arguments[0]);
		}

		if (_methodName180.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes180, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getNextTestQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName181.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes181, parameterTypes)) {
			return TestQuestionLocalServiceUtil.getNextTestQuestion((com.liferay.lms.model.TestQuestion)arguments[0]);
		}

		if (_methodName182.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes182, parameterTypes)) {
			TestQuestionLocalServiceUtil.goUpTestQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName183.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes183, parameterTypes)) {
			TestQuestionLocalServiceUtil.goDownTestQuestion(((Long)arguments[0]).longValue());
		}

		if (_methodName184.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes184, parameterTypes)) {
			TestQuestionLocalServiceUtil.moveQuestion(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue());
		}

		if (_methodName185.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes185, parameterTypes)) {
			TestQuestionLocalServiceUtil.checkWeights(((Long)arguments[0]).longValue());
		}

		if (_methodName186.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes186, parameterTypes)) {
			return TestQuestionLocalServiceUtil.generateAleatoryQuestions(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName187.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes187, parameterTypes)) {
			return TestQuestionLocalServiceUtil.isTypeAllowed(((Long)arguments[0]).longValue(),
				(com.liferay.portal.kernel.xml.Document)arguments[1]);
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
	private String _methodName164;
	private String[] _methodParameterTypes164;
	private String _methodName165;
	private String[] _methodParameterTypes165;
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
	private String _methodName176;
	private String[] _methodParameterTypes176;
	private String _methodName177;
	private String[] _methodParameterTypes177;
	private String _methodName178;
	private String[] _methodParameterTypes178;
	private String _methodName179;
	private String[] _methodParameterTypes179;
	private String _methodName180;
	private String[] _methodParameterTypes180;
	private String _methodName181;
	private String[] _methodParameterTypes181;
	private String _methodName182;
	private String[] _methodParameterTypes182;
	private String _methodName183;
	private String[] _methodParameterTypes183;
	private String _methodName184;
	private String[] _methodParameterTypes184;
	private String _methodName185;
	private String[] _methodParameterTypes185;
	private String _methodName186;
	private String[] _methodParameterTypes186;
	private String _methodName187;
	private String[] _methodParameterTypes187;
}