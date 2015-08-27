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

import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class LearningActivityResultLocalServiceClpInvoker {
	public LearningActivityResultLocalServiceClpInvoker() {
		_methodName0 = "addLearningActivityResult";

		_methodParameterTypes0 = new String[] {
				"com.liferay.lms.model.LearningActivityResult"
			};

		_methodName1 = "createLearningActivityResult";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteLearningActivityResult";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteLearningActivityResult";

		_methodParameterTypes3 = new String[] {
				"com.liferay.lms.model.LearningActivityResult"
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

		_methodName9 = "fetchLearningActivityResult";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getLearningActivityResult";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getLearningActivityResults";

		_methodParameterTypes12 = new String[] { "int", "int" };

		_methodName13 = "getLearningActivityResultsCount";

		_methodParameterTypes13 = new String[] {  };

		_methodName14 = "updateLearningActivityResult";

		_methodParameterTypes14 = new String[] {
				"com.liferay.lms.model.LearningActivityResult"
			};

		_methodName15 = "updateLearningActivityResult";

		_methodParameterTypes15 = new String[] {
				"com.liferay.lms.model.LearningActivityResult", "boolean"
			};

		_methodName134 = "getBeanIdentifier";

		_methodParameterTypes134 = new String[] {  };

		_methodName135 = "setBeanIdentifier";

		_methodParameterTypes135 = new String[] { "java.lang.String" };

		_methodName140 = "update";

		_methodParameterTypes140 = new String[] {
				"com.liferay.lms.model.LearningActivityTry"
			};

		_methodName141 = "update";

		_methodParameterTypes141 = new String[] {
				"long", "long", "java.lang.String", "long"
			};

		_methodName142 = "update";

		_methodParameterTypes142 = new String[] {
				"long", "java.lang.String", "long"
			};

		_methodName143 = "update";

		_methodParameterTypes143 = new String[] {
				"long", "java.lang.String", "java.lang.String", "long"
			};

		_methodName144 = "existsLearningActivityResult";

		_methodParameterTypes144 = new String[] { "long", "long" };

		_methodName145 = "userPassed";

		_methodParameterTypes145 = new String[] { "long", "long" };

		_methodName146 = "countPassed";

		_methodParameterTypes146 = new String[] { "long" };

		_methodName147 = "countPassedOnlyStudents";

		_methodParameterTypes147 = new String[] {
				"long", "long", "long", "boolean"
			};

		_methodName148 = "countNotPassed";

		_methodParameterTypes148 = new String[] { "long" };

		_methodName149 = "countNotPassedOnlyStudents";

		_methodParameterTypes149 = new String[] { "long", "long", "long" };

		_methodName150 = "avgResult";

		_methodParameterTypes150 = new String[] { "long" };

		_methodName151 = "avgResultOnlyStudents";

		_methodParameterTypes151 = new String[] { "long", "long", "long" };

		_methodName152 = "countStarted";

		_methodParameterTypes152 = new String[] { "long" };

		_methodName153 = "countStartedOnlyStudents";

		_methodParameterTypes153 = new String[] { "long", "long", "long" };

		_methodName154 = "triesPerUser";

		_methodParameterTypes154 = new String[] { "long" };

		_methodName155 = "triesPerUserOnlyStudents";

		_methodParameterTypes155 = new String[] { "long", "long", "long" };

		_methodName156 = "getByActIdAndUserId";

		_methodParameterTypes156 = new String[] { "long", "long" };

		_methodName157 = "getLastEndDateByUserId";

		_methodParameterTypes157 = new String[] { "long" };

		_methodName158 = "getByActId";

		_methodParameterTypes158 = new String[] { "long" };

		_methodName159 = "translateResult";

		_methodParameterTypes159 = new String[] {
				"java.util.Locale", "double", "long"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.addLearningActivityResult((com.liferay.lms.model.LearningActivityResult)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.createLearningActivityResult(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.deleteLearningActivityResult(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.deleteLearningActivityResult((com.liferay.lms.model.LearningActivityResult)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.fetchLearningActivityResult(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.getLearningActivityResult(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.getLearningActivityResults(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.getLearningActivityResultsCount();
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.updateLearningActivityResult((com.liferay.lms.model.LearningActivityResult)arguments[0]);
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.updateLearningActivityResult((com.liferay.lms.model.LearningActivityResult)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName134.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes134, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName135.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes135, parameterTypes)) {
			LearningActivityResultLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName140.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes140, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.update((com.liferay.lms.model.LearningActivityTry)arguments[0]);
		}

		if (_methodName141.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes141, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.update(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.lang.String)arguments[2], ((Long)arguments[3]).longValue());
		}

		if (_methodName142.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes142, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.update(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], ((Long)arguments[2]).longValue());
		}

		if (_methodName143.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes143, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.update(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				((Long)arguments[3]).longValue());
		}

		if (_methodName144.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes144, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.existsLearningActivityResult(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName145.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes145, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.userPassed(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName146.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes146, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.countPassed(((Long)arguments[0]).longValue());
		}

		if (_methodName147.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes147, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.countPassedOnlyStudents(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue(),
				((Boolean)arguments[3]).booleanValue());
		}

		if (_methodName148.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes148, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.countNotPassed(((Long)arguments[0]).longValue());
		}

		if (_methodName149.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes149, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.countNotPassedOnlyStudents(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue());
		}

		if (_methodName150.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes150, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.avgResult(((Long)arguments[0]).longValue());
		}

		if (_methodName151.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes151, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.avgResultOnlyStudents(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue());
		}

		if (_methodName152.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes152, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.countStarted(((Long)arguments[0]).longValue());
		}

		if (_methodName153.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes153, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.countStartedOnlyStudents(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue());
		}

		if (_methodName154.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes154, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.triesPerUser(((Long)arguments[0]).longValue());
		}

		if (_methodName155.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes155, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.triesPerUserOnlyStudents(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue());
		}

		if (_methodName156.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes156, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.getByActIdAndUserId(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName157.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes157, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.getLastEndDateByUserId(((Long)arguments[0]).longValue());
		}

		if (_methodName158.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes158, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.getByActId(((Long)arguments[0]).longValue());
		}

		if (_methodName159.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes159, parameterTypes)) {
			return LearningActivityResultLocalServiceUtil.translateResult((java.util.Locale)arguments[0],
				((Double)arguments[1]).doubleValue(),
				((Long)arguments[2]).longValue());
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
	private String _methodName142;
	private String[] _methodParameterTypes142;
	private String _methodName143;
	private String[] _methodParameterTypes143;
	private String _methodName144;
	private String[] _methodParameterTypes144;
	private String _methodName145;
	private String[] _methodParameterTypes145;
	private String _methodName146;
	private String[] _methodParameterTypes146;
	private String _methodName147;
	private String[] _methodParameterTypes147;
	private String _methodName148;
	private String[] _methodParameterTypes148;
	private String _methodName149;
	private String[] _methodParameterTypes149;
	private String _methodName150;
	private String[] _methodParameterTypes150;
	private String _methodName151;
	private String[] _methodParameterTypes151;
	private String _methodName152;
	private String[] _methodParameterTypes152;
	private String _methodName153;
	private String[] _methodParameterTypes153;
	private String _methodName154;
	private String[] _methodParameterTypes154;
	private String _methodName155;
	private String[] _methodParameterTypes155;
	private String _methodName156;
	private String[] _methodParameterTypes156;
	private String _methodName157;
	private String[] _methodParameterTypes157;
	private String _methodName158;
	private String[] _methodParameterTypes158;
	private String _methodName159;
	private String[] _methodParameterTypes159;
}