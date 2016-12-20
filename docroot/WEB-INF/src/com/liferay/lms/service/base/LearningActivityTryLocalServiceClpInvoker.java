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

import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class LearningActivityTryLocalServiceClpInvoker {
	public LearningActivityTryLocalServiceClpInvoker() {
		_methodName0 = "addLearningActivityTry";

		_methodParameterTypes0 = new String[] {
				"com.liferay.lms.model.LearningActivityTry"
			};

		_methodName1 = "createLearningActivityTry";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteLearningActivityTry";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteLearningActivityTry";

		_methodParameterTypes3 = new String[] {
				"com.liferay.lms.model.LearningActivityTry"
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

		_methodName9 = "fetchLearningActivityTry";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getLearningActivityTry";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getLearningActivityTries";

		_methodParameterTypes12 = new String[] { "int", "int" };

		_methodName13 = "getLearningActivityTriesCount";

		_methodParameterTypes13 = new String[] {  };

		_methodName14 = "updateLearningActivityTry";

		_methodParameterTypes14 = new String[] {
				"com.liferay.lms.model.LearningActivityTry"
			};

		_methodName15 = "updateLearningActivityTry";

		_methodParameterTypes15 = new String[] {
				"com.liferay.lms.model.LearningActivityTry", "boolean"
			};

		_methodName150 = "getBeanIdentifier";

		_methodParameterTypes150 = new String[] {  };

		_methodName151 = "setBeanIdentifier";

		_methodParameterTypes151 = new String[] { "java.lang.String" };

		_methodName156 = "softUpdateLearningActivityTry";

		_methodParameterTypes156 = new String[] {
				"com.liferay.lms.model.LearningActivityTry"
			};

		_methodName157 = "updateLearningActivityTry";

		_methodParameterTypes157 = new String[] {
				"com.liferay.lms.model.LearningActivityTry"
			};

		_methodName158 = "getLearningActivityTryByActUserCount";

		_methodParameterTypes158 = new String[] { "long", "long" };

		_methodName159 = "deleteUserTries";

		_methodParameterTypes159 = new String[] { "long", "long" };

		_methodName160 = "getLearningActivityTryByActUser";

		_methodParameterTypes160 = new String[] { "long", "long" };

		_methodName161 = "updateLearningActivityTry";

		_methodParameterTypes161 = new String[] {
				"com.liferay.lms.model.LearningActivityTry", "boolean"
			};

		_methodName162 = "updateLearningActivityTry";

		_methodParameterTypes162 = new String[] {
				"com.liferay.lms.model.LearningActivityTry", "java.lang.String",
				"java.lang.String"
			};

		_methodName163 = "createLearningActivityTry";

		_methodParameterTypes163 = new String[] {
				"long", "com.liferay.portal.service.ServiceContext"
			};

		_methodName164 = "getUsersByLearningActivity";

		_methodParameterTypes164 = new String[] { "long" };

		_methodName165 = "getLastLearningActivityTryByActivityAndUser";

		_methodParameterTypes165 = new String[] { "long", "long" };

		_methodName166 = "createOrDuplicateLast";

		_methodParameterTypes166 = new String[] {
				"long", "com.liferay.portal.service.ServiceContext"
			};

		_methodName167 = "getLearningActivityTryNotFinishedByActUser";

		_methodParameterTypes167 = new String[] { "long", "long" };

		_methodName168 = "getTriesCountByActivityAndUser";

		_methodParameterTypes168 = new String[] { "long", "long" };

		_methodName169 = "getTriesCountByActivity";

		_methodParameterTypes169 = new String[] { "long" };

		_methodName170 = "getMapTryResultData";

		_methodParameterTypes170 = new String[] { "long", "long" };

		_methodName171 = "canUserDoANewTry";

		_methodParameterTypes171 = new String[] { "long", "long" };

		_methodName172 = "areThereTriesNotFromEditors";

		_methodParameterTypes172 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName173 = "getByUserId";

		_methodParameterTypes173 = new String[] { "long" };

		_methodName174 = "countTriesByActIdUserIdsStarted";

		_methodParameterTypes174 = new String[] { "long", "long[][]" };

		_methodName175 = "countTriesByActIdUserExcludedIdsStarted";

		_methodParameterTypes175 = new String[] { "long", "long[][]" };

		_methodName176 = "countTriesByActIdStarted";

		_methodParameterTypes176 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.addLearningActivityTry((com.liferay.lms.model.LearningActivityTry)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.createLearningActivityTry(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.deleteLearningActivityTry(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.deleteLearningActivityTry((com.liferay.lms.model.LearningActivityTry)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.fetchLearningActivityTry(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getLearningActivityTry(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getLearningActivityTries(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getLearningActivityTriesCount();
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.updateLearningActivityTry((com.liferay.lms.model.LearningActivityTry)arguments[0]);
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.updateLearningActivityTry((com.liferay.lms.model.LearningActivityTry)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName150.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes150, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName151.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes151, parameterTypes)) {
			LearningActivityTryLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);
		}

		if (_methodName156.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes156, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.softUpdateLearningActivityTry((com.liferay.lms.model.LearningActivityTry)arguments[0]);
		}

		if (_methodName157.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes157, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.updateLearningActivityTry((com.liferay.lms.model.LearningActivityTry)arguments[0]);
		}

		if (_methodName158.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes158, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUserCount(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName159.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes159, parameterTypes)) {
			LearningActivityTryLocalServiceUtil.deleteUserTries(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName160.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes160, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUser(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName161.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes161, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.updateLearningActivityTry((com.liferay.lms.model.LearningActivityTry)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName162.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes162, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.updateLearningActivityTry((com.liferay.lms.model.LearningActivityTry)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2]);
		}

		if (_methodName163.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes163, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.createLearningActivityTry(((Long)arguments[0]).longValue(),
				(com.liferay.portal.service.ServiceContext)arguments[1]);
		}

		if (_methodName164.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes164, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getUsersByLearningActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName165.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes165, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName166.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes166, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.createOrDuplicateLast(((Long)arguments[0]).longValue(),
				(com.liferay.portal.service.ServiceContext)arguments[1]);
		}

		if (_methodName167.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes167, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getLearningActivityTryNotFinishedByActUser(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName168.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes168, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName169.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes169, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getTriesCountByActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName170.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes170, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getMapTryResultData(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName171.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes171, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.canUserDoANewTry(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName172.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes172, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.areThereTriesNotFromEditors((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName173.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes173, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.getByUserId(((Long)arguments[0]).longValue());
		}

		if (_methodName174.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes174, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.countTriesByActIdUserIdsStarted(((Long)arguments[0]).longValue(),
				(long[])arguments[1]);
		}

		if (_methodName175.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes175, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.countTriesByActIdUserExcludedIdsStarted(((Long)arguments[0]).longValue(),
				(long[])arguments[1]);
		}

		if (_methodName176.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes176, parameterTypes)) {
			return LearningActivityTryLocalServiceUtil.countTriesByActIdStarted(((Long)arguments[0]).longValue());
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
	private String _methodName176;
	private String[] _methodParameterTypes176;
}