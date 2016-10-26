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

import com.liferay.lms.service.LearningActivityLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class LearningActivityLocalServiceClpInvoker {
	public LearningActivityLocalServiceClpInvoker() {
		_methodName0 = "addLearningActivity";

		_methodParameterTypes0 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName1 = "createLearningActivity";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteLearningActivity";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteLearningActivity";

		_methodParameterTypes3 = new String[] {
				"com.liferay.lms.model.LearningActivity"
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

		_methodName9 = "fetchLearningActivity";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getLearningActivity";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getLearningActivityByUuidAndGroupId";

		_methodParameterTypes12 = new String[] { "java.lang.String", "long" };

		_methodName13 = "getLearningActivities";

		_methodParameterTypes13 = new String[] { "int", "int" };

		_methodName14 = "getLearningActivitiesCount";

		_methodParameterTypes14 = new String[] {  };

		_methodName15 = "updateLearningActivity";

		_methodParameterTypes15 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName16 = "updateLearningActivity";

		_methodParameterTypes16 = new String[] {
				"com.liferay.lms.model.LearningActivity", "boolean"
			};

		_methodName187 = "getBeanIdentifier";

		_methodParameterTypes187 = new String[] {  };

		_methodName188 = "setBeanIdentifier";

		_methodParameterTypes188 = new String[] { "java.lang.String" };

		_methodName193 = "islocked";

		_methodParameterTypes193 = new String[] { "long", "long" };

		_methodName194 = "addLearningActivity";

		_methodParameterTypes194 = new String[] {
				"com.liferay.lms.model.LearningActivity",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName195 = "addLearningActivity";

		_methodParameterTypes195 = new String[] {
				"java.lang.String", "java.lang.String", "java.util.Date",
				"java.util.Date", "java.util.Date", "int", "long", "int", "long",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName196 = "addLearningActivity";

		_methodParameterTypes196 = new String[] {
				"long", "long", "int", "java.util.Map", "java.util.Map", "int",
				"java.util.Date", "java.util.Date", "long", "long", "int",
				"long", "java.lang.String", "java.lang.String",
				"java.lang.String", "long", "long",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName197 = "modLearningActivity";

		_methodParameterTypes197 = new String[] {
				"long", "java.lang.String", "java.lang.String", "java.util.Date",
				"java.util.Date", "java.util.Date", "int", "long", "int", "long",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName198 = "modLearningActivity";

		_methodParameterTypes198 = new String[] {
				"com.liferay.lms.model.LearningActivity",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName199 = "modLearningActivity";

		_methodParameterTypes199 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName200 = "getLearningActivitiesOfGroup";

		_methodParameterTypes200 = new String[] { "long" };

		_methodName201 = "getMandatoryLearningActivitiesOfGroup";

		_methodParameterTypes201 = new String[] { "long" };

		_methodName202 = "countLearningActivitiesOfGroup";

		_methodParameterTypes202 = new String[] { "long" };

		_methodName203 = "countLearningActivitiesOfModule";

		_methodParameterTypes203 = new String[] { "long" };

		_methodName204 = "getLearningActivitiesOfGroupAndType";

		_methodParameterTypes204 = new String[] { "long", "int" };

		_methodName205 = "getLearningActivitiesOfModule";

		_methodParameterTypes205 = new String[] { "long" };

		_methodName206 = "getLearningActivitiesOfModule";

		_methodParameterTypes206 = new String[] { "long", "int", "int" };

		_methodName207 = "getLearningActivityIdsOfModule";

		_methodParameterTypes207 = new String[] { "long" };

		_methodName208 = "deleteLearningactivity";

		_methodParameterTypes208 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName209 = "getPreviusLearningActivity";

		_methodParameterTypes209 = new String[] { "long" };

		_methodName210 = "getPreviusLearningActivity";

		_methodParameterTypes210 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName211 = "goUpLearningActivity";

		_methodParameterTypes211 = new String[] { "long", "long" };

		_methodName212 = "goDownLearningActivity";

		_methodParameterTypes212 = new String[] { "long", "long" };

		_methodName213 = "moveActivity";

		_methodParameterTypes213 = new String[] { "long", "long", "long", "long" };

		_methodName214 = "getNextLearningActivity";

		_methodParameterTypes214 = new String[] { "long" };

		_methodName215 = "getNextLearningActivity";

		_methodParameterTypes215 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName216 = "deleteLearningactivity";

		_methodParameterTypes216 = new String[] { "long" };

		_methodName217 = "getExtraContentValue";

		_methodParameterTypes217 = new String[] {
				"long", "java.lang.String", "java.lang.String"
			};

		_methodName218 = "getExtraContentValue";

		_methodParameterTypes218 = new String[] { "long", "java.lang.String" };

		_methodName219 = "getExtraContentValues";

		_methodParameterTypes219 = new String[] { "long", "java.lang.String" };

		_methodName220 = "setExtraContentValue";

		_methodParameterTypes220 = new String[] {
				"long", "java.lang.String", "java.lang.String"
			};

		_methodName221 = "convertXMLExtraContentToHashMap";

		_methodParameterTypes221 = new String[] { "long" };

		_methodName222 = "saveHashMapToXMLExtraContent";

		_methodParameterTypes222 = new String[] { "long", "java.util.HashMap" };

		_methodName223 = "isLearningActivityDeleteTries";

		_methodParameterTypes223 = new String[] { "long" };

		_methodName225 = "canBeView";

		_methodParameterTypes225 = new String[] {
				"com.liferay.lms.model.LearningActivity", "long"
			};

		_methodName226 = "canBeView";

		_methodParameterTypes226 = new String[] {
				"com.liferay.lms.model.LearningActivity",
				"com.liferay.portal.security.permission.PermissionChecker"
			};

		_methodName227 = "canBeEdited";

		_methodParameterTypes227 = new String[] {
				"com.liferay.lms.model.LearningActivity", "long"
			};

		_methodName228 = "canBeEdited";

		_methodParameterTypes228 = new String[] {
				"com.liferay.lms.model.LearningActivity",
				"com.liferay.portal.security.permission.PermissionChecker"
			};

		_methodName229 = "updateLearningActivity";

		_methodParameterTypes229 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName230 = "getMandatoryActivities";

		_methodParameterTypes230 = new String[] { "long" };

		_methodName231 = "getByPrecedence";

		_methodParameterTypes231 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return LearningActivityLocalServiceUtil.addLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return LearningActivityLocalServiceUtil.createLearningActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return LearningActivityLocalServiceUtil.deleteLearningActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return LearningActivityLocalServiceUtil.deleteLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return LearningActivityLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return LearningActivityLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return LearningActivityLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return LearningActivityLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return LearningActivityLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return LearningActivityLocalServiceUtil.fetchLearningActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getLearningActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getLearningActivityByUuidAndGroupId((java.lang.String)arguments[0],
				((Long)arguments[1]).longValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getLearningActivities(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getLearningActivitiesCount();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return LearningActivityLocalServiceUtil.updateLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName16.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes16, parameterTypes)) {
			return LearningActivityLocalServiceUtil.updateLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName187.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes187, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName188.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes188, parameterTypes)) {
			LearningActivityLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);
		}

		if (_methodName193.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes193, parameterTypes)) {
			return LearningActivityLocalServiceUtil.islocked(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName194.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes194, parameterTypes)) {
			return LearningActivityLocalServiceUtil.addLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0],
				(com.liferay.portal.service.ServiceContext)arguments[1]);
		}

		if (_methodName195.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes195, parameterTypes)) {
			return LearningActivityLocalServiceUtil.addLearningActivity((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.util.Date)arguments[2],
				(java.util.Date)arguments[3], (java.util.Date)arguments[4],
				((Integer)arguments[5]).intValue(),
				((Long)arguments[6]).longValue(),
				((Integer)arguments[7]).intValue(),
				((Long)arguments[8]).longValue(),
				(java.lang.String)arguments[9],
				(java.lang.String)arguments[10],
				(java.lang.String)arguments[11],
				(com.liferay.portal.service.ServiceContext)arguments[12]);
		}

		if (_methodName196.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes196, parameterTypes)) {
			return LearningActivityLocalServiceUtil.addLearningActivity(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Integer)arguments[2]).intValue(),
				(java.util.Map<java.util.Locale, java.lang.String>)arguments[3],
				(java.util.Map<java.util.Locale, java.lang.String>)arguments[4],
				((Integer)arguments[5]).intValue(),
				(java.util.Date)arguments[6], (java.util.Date)arguments[7],
				((Long)arguments[8]).longValue(),
				((Long)arguments[9]).longValue(),
				((Integer)arguments[10]).intValue(),
				((Long)arguments[11]).longValue(),
				(java.lang.String)arguments[12],
				(java.lang.String)arguments[13],
				(java.lang.String)arguments[14],
				((Long)arguments[15]).longValue(),
				((Long)arguments[16]).longValue(),
				(com.liferay.portal.service.ServiceContext)arguments[17]);
		}

		if (_methodName197.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes197, parameterTypes)) {
			return LearningActivityLocalServiceUtil.modLearningActivity(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.util.Date)arguments[3], (java.util.Date)arguments[4],
				(java.util.Date)arguments[5],
				((Integer)arguments[6]).intValue(),
				((Long)arguments[7]).longValue(),
				((Integer)arguments[8]).intValue(),
				((Long)arguments[9]).longValue(),
				(java.lang.String)arguments[10],
				(java.lang.String)arguments[11],
				(java.lang.String)arguments[12],
				(com.liferay.portal.service.ServiceContext)arguments[13]);
		}

		if (_methodName198.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes198, parameterTypes)) {
			return LearningActivityLocalServiceUtil.modLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0],
				(com.liferay.portal.service.ServiceContext)arguments[1]);
		}

		if (_methodName199.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes199, parameterTypes)) {
			return LearningActivityLocalServiceUtil.modLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName200.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes200, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getLearningActivitiesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName201.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes201, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getMandatoryLearningActivitiesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName202.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes202, parameterTypes)) {
			return LearningActivityLocalServiceUtil.countLearningActivitiesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName203.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes203, parameterTypes)) {
			return LearningActivityLocalServiceUtil.countLearningActivitiesOfModule(((Long)arguments[0]).longValue());
		}

		if (_methodName204.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes204, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getLearningActivitiesOfGroupAndType(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName205.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes205, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(((Long)arguments[0]).longValue());
		}

		if (_methodName206.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes206, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName207.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes207, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getLearningActivityIdsOfModule(((Long)arguments[0]).longValue());
		}

		if (_methodName208.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes208, parameterTypes)) {
			LearningActivityLocalServiceUtil.deleteLearningactivity((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName209.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes209, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getPreviusLearningActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName210.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes210, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getPreviusLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName211.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes211, parameterTypes)) {
			LearningActivityLocalServiceUtil.goUpLearningActivity(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName212.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes212, parameterTypes)) {
			LearningActivityLocalServiceUtil.goDownLearningActivity(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName213.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes213, parameterTypes)) {
			LearningActivityLocalServiceUtil.moveActivity(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue(),
				((Long)arguments[3]).longValue());
		}

		if (_methodName214.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes214, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getNextLearningActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName215.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes215, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getNextLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName216.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes216, parameterTypes)) {
			LearningActivityLocalServiceUtil.deleteLearningactivity(((Long)arguments[0]).longValue());
		}

		if (_methodName217.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes217, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getExtraContentValue(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2]);
		}

		if (_methodName218.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes218, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getExtraContentValue(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName219.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes219, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getExtraContentValues(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName220.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes220, parameterTypes)) {
			LearningActivityLocalServiceUtil.setExtraContentValue(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2]);
		}

		if (_methodName221.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes221, parameterTypes)) {
			return LearningActivityLocalServiceUtil.convertXMLExtraContentToHashMap(((Long)arguments[0]).longValue());
		}

		if (_methodName222.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes222, parameterTypes)) {
			LearningActivityLocalServiceUtil.saveHashMapToXMLExtraContent(((Long)arguments[0]).longValue(),
				(java.util.HashMap<java.lang.String, java.lang.String>)arguments[1]);
		}

		if (_methodName223.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes223, parameterTypes)) {
			return LearningActivityLocalServiceUtil.isLearningActivityDeleteTries(((Long)arguments[0]).longValue());
		}

		if (_methodName225.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes225, parameterTypes)) {
			return LearningActivityLocalServiceUtil.canBeView((com.liferay.lms.model.LearningActivity)arguments[0],
				((Long)arguments[1]).longValue());
		}

		if (_methodName226.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes226, parameterTypes)) {
			return LearningActivityLocalServiceUtil.canBeView((com.liferay.lms.model.LearningActivity)arguments[0],
				(com.liferay.portal.security.permission.PermissionChecker)arguments[1]);
		}

		if (_methodName227.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes227, parameterTypes)) {
			return LearningActivityLocalServiceUtil.canBeEdited((com.liferay.lms.model.LearningActivity)arguments[0],
				((Long)arguments[1]).longValue());
		}

		if (_methodName228.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes228, parameterTypes)) {
			return LearningActivityLocalServiceUtil.canBeEdited((com.liferay.lms.model.LearningActivity)arguments[0],
				(com.liferay.portal.security.permission.PermissionChecker)arguments[1]);
		}

		if (_methodName229.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes229, parameterTypes)) {
			return LearningActivityLocalServiceUtil.updateLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName230.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes230, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getMandatoryActivities(((Long)arguments[0]).longValue());
		}

		if (_methodName231.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes231, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getByPrecedence(((Long)arguments[0]).longValue());
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
	private String _methodName16;
	private String[] _methodParameterTypes16;
	private String _methodName187;
	private String[] _methodParameterTypes187;
	private String _methodName188;
	private String[] _methodParameterTypes188;
	private String _methodName193;
	private String[] _methodParameterTypes193;
	private String _methodName194;
	private String[] _methodParameterTypes194;
	private String _methodName195;
	private String[] _methodParameterTypes195;
	private String _methodName196;
	private String[] _methodParameterTypes196;
	private String _methodName197;
	private String[] _methodParameterTypes197;
	private String _methodName198;
	private String[] _methodParameterTypes198;
	private String _methodName199;
	private String[] _methodParameterTypes199;
	private String _methodName200;
	private String[] _methodParameterTypes200;
	private String _methodName201;
	private String[] _methodParameterTypes201;
	private String _methodName202;
	private String[] _methodParameterTypes202;
	private String _methodName203;
	private String[] _methodParameterTypes203;
	private String _methodName204;
	private String[] _methodParameterTypes204;
	private String _methodName205;
	private String[] _methodParameterTypes205;
	private String _methodName206;
	private String[] _methodParameterTypes206;
	private String _methodName207;
	private String[] _methodParameterTypes207;
	private String _methodName208;
	private String[] _methodParameterTypes208;
	private String _methodName209;
	private String[] _methodParameterTypes209;
	private String _methodName210;
	private String[] _methodParameterTypes210;
	private String _methodName211;
	private String[] _methodParameterTypes211;
	private String _methodName212;
	private String[] _methodParameterTypes212;
	private String _methodName213;
	private String[] _methodParameterTypes213;
	private String _methodName214;
	private String[] _methodParameterTypes214;
	private String _methodName215;
	private String[] _methodParameterTypes215;
	private String _methodName216;
	private String[] _methodParameterTypes216;
	private String _methodName217;
	private String[] _methodParameterTypes217;
	private String _methodName218;
	private String[] _methodParameterTypes218;
	private String _methodName219;
	private String[] _methodParameterTypes219;
	private String _methodName220;
	private String[] _methodParameterTypes220;
	private String _methodName221;
	private String[] _methodParameterTypes221;
	private String _methodName222;
	private String[] _methodParameterTypes222;
	private String _methodName223;
	private String[] _methodParameterTypes223;
	private String _methodName225;
	private String[] _methodParameterTypes225;
	private String _methodName226;
	private String[] _methodParameterTypes226;
	private String _methodName227;
	private String[] _methodParameterTypes227;
	private String _methodName228;
	private String[] _methodParameterTypes228;
	private String _methodName229;
	private String[] _methodParameterTypes229;
	private String _methodName230;
	private String[] _methodParameterTypes230;
	private String _methodName231;
	private String[] _methodParameterTypes231;
}