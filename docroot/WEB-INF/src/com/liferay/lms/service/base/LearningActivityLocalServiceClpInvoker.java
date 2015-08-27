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

		_methodName167 = "getBeanIdentifier";

		_methodParameterTypes167 = new String[] {  };

		_methodName168 = "setBeanIdentifier";

		_methodParameterTypes168 = new String[] { "java.lang.String" };

		_methodName173 = "islocked";

		_methodParameterTypes173 = new String[] { "long", "long" };

		_methodName174 = "addLearningActivity";

		_methodParameterTypes174 = new String[] {
				"com.liferay.lms.model.LearningActivity",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName175 = "addLearningActivity";

		_methodParameterTypes175 = new String[] {
				"java.lang.String", "java.lang.String", "java.util.Date",
				"java.util.Date", "java.util.Date", "int", "long", "int", "long",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName176 = "addLearningActivity";

		_methodParameterTypes176 = new String[] {
				"long", "long", "int", "java.util.Map", "java.util.Map", "int",
				"java.util.Date", "java.util.Date", "long", "long", "int",
				"long", "java.lang.String", "java.lang.String",
				"java.lang.String", "long", "long",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName177 = "modLearningActivity";

		_methodParameterTypes177 = new String[] {
				"long", "java.lang.String", "java.lang.String", "java.util.Date",
				"java.util.Date", "java.util.Date", "int", "long", "int", "long",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName178 = "modLearningActivity";

		_methodParameterTypes178 = new String[] {
				"com.liferay.lms.model.LearningActivity",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName179 = "modLearningActivity";

		_methodParameterTypes179 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName180 = "getLearningActivitiesOfGroup";

		_methodParameterTypes180 = new String[] { "long" };

		_methodName181 = "getMandatoryLearningActivitiesOfGroup";

		_methodParameterTypes181 = new String[] { "long" };

		_methodName182 = "countLearningActivitiesOfGroup";

		_methodParameterTypes182 = new String[] { "long" };

		_methodName183 = "getLearningActivitiesOfGroupAndType";

		_methodParameterTypes183 = new String[] { "long", "int" };

		_methodName184 = "getLearningActivitiesOfModule";

		_methodParameterTypes184 = new String[] { "long" };

		_methodName185 = "getLearningActivityIdsOfModule";

		_methodParameterTypes185 = new String[] { "long" };

		_methodName186 = "deleteLearningactivity";

		_methodParameterTypes186 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName187 = "getPreviusLearningActivity";

		_methodParameterTypes187 = new String[] { "long" };

		_methodName188 = "getPreviusLearningActivity";

		_methodParameterTypes188 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName189 = "goUpLearningActivity";

		_methodParameterTypes189 = new String[] { "long" };

		_methodName190 = "goDownLearningActivity";

		_methodParameterTypes190 = new String[] { "long" };

		_methodName191 = "moveActivity";

		_methodParameterTypes191 = new String[] { "long", "long", "long" };

		_methodName192 = "getNextLearningActivity";

		_methodParameterTypes192 = new String[] { "long" };

		_methodName193 = "getNextLearningActivity";

		_methodParameterTypes193 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName194 = "deleteLearningactivity";

		_methodParameterTypes194 = new String[] { "long" };

		_methodName195 = "getExtraContentValue";

		_methodParameterTypes195 = new String[] {
				"long", "java.lang.String", "java.lang.String"
			};

		_methodName196 = "getExtraContentValue";

		_methodParameterTypes196 = new String[] { "long", "java.lang.String" };

		_methodName197 = "getExtraContentValues";

		_methodParameterTypes197 = new String[] { "long", "java.lang.String" };

		_methodName198 = "setExtraContentValue";

		_methodParameterTypes198 = new String[] {
				"long", "java.lang.String", "java.lang.String"
			};

		_methodName199 = "convertXMLExtraContentToHashMap";

		_methodParameterTypes199 = new String[] { "long" };

		_methodName200 = "saveHashMapToXMLExtraContent";

		_methodParameterTypes200 = new String[] { "long", "java.util.HashMap" };

		_methodName201 = "isLearningActivityDeleteTries";

		_methodParameterTypes201 = new String[] { "long" };

		_methodName203 = "canBeView";

		_methodParameterTypes203 = new String[] {
				"com.liferay.lms.model.LearningActivity", "long"
			};

		_methodName204 = "canBeView";

		_methodParameterTypes204 = new String[] {
				"com.liferay.lms.model.LearningActivity",
				"com.liferay.portal.security.permission.PermissionChecker"
			};

		_methodName205 = "canBeEdited";

		_methodParameterTypes205 = new String[] {
				"com.liferay.lms.model.LearningActivity", "long"
			};

		_methodName206 = "canBeEdited";

		_methodParameterTypes206 = new String[] {
				"com.liferay.lms.model.LearningActivity",
				"com.liferay.portal.security.permission.PermissionChecker"
			};

		_methodName207 = "updateLearningActivity";

		_methodParameterTypes207 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};
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

		if (_methodName167.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes167, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName168.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes168, parameterTypes)) {
			LearningActivityLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName173.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes173, parameterTypes)) {
			return LearningActivityLocalServiceUtil.islocked(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName174.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes174, parameterTypes)) {
			return LearningActivityLocalServiceUtil.addLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0],
				(com.liferay.portal.service.ServiceContext)arguments[1]);
		}

		if (_methodName175.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes175, parameterTypes)) {
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

		if (_methodName176.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes176, parameterTypes)) {
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

		if (_methodName177.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes177, parameterTypes)) {
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

		if (_methodName178.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes178, parameterTypes)) {
			return LearningActivityLocalServiceUtil.modLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0],
				(com.liferay.portal.service.ServiceContext)arguments[1]);
		}

		if (_methodName179.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes179, parameterTypes)) {
			return LearningActivityLocalServiceUtil.modLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName180.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes180, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getLearningActivitiesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName181.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes181, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getMandatoryLearningActivitiesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName182.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes182, parameterTypes)) {
			return LearningActivityLocalServiceUtil.countLearningActivitiesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName183.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes183, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getLearningActivitiesOfGroupAndType(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName184.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes184, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(((Long)arguments[0]).longValue());
		}

		if (_methodName185.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes185, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getLearningActivityIdsOfModule(((Long)arguments[0]).longValue());
		}

		if (_methodName186.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes186, parameterTypes)) {
			LearningActivityLocalServiceUtil.deleteLearningactivity((com.liferay.lms.model.LearningActivity)arguments[0]);

			return null;
		}

		if (_methodName187.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes187, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getPreviusLearningActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName188.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes188, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getPreviusLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName189.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes189, parameterTypes)) {
			LearningActivityLocalServiceUtil.goUpLearningActivity(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName190.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes190, parameterTypes)) {
			LearningActivityLocalServiceUtil.goDownLearningActivity(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName191.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes191, parameterTypes)) {
			LearningActivityLocalServiceUtil.moveActivity(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue());

			return null;
		}

		if (_methodName192.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes192, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getNextLearningActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName193.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes193, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getNextLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName194.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes194, parameterTypes)) {
			LearningActivityLocalServiceUtil.deleteLearningactivity(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName195.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes195, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getExtraContentValue(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2]);
		}

		if (_methodName196.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes196, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getExtraContentValue(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName197.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes197, parameterTypes)) {
			return LearningActivityLocalServiceUtil.getExtraContentValues(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName198.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes198, parameterTypes)) {
			LearningActivityLocalServiceUtil.setExtraContentValue(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2]);

			return null;
		}

		if (_methodName199.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes199, parameterTypes)) {
			return LearningActivityLocalServiceUtil.convertXMLExtraContentToHashMap(((Long)arguments[0]).longValue());
		}

		if (_methodName200.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes200, parameterTypes)) {
			LearningActivityLocalServiceUtil.saveHashMapToXMLExtraContent(((Long)arguments[0]).longValue(),
				(java.util.HashMap<java.lang.String, java.lang.String>)arguments[1]);

			return null;
		}

		if (_methodName201.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes201, parameterTypes)) {
			return LearningActivityLocalServiceUtil.isLearningActivityDeleteTries(((Long)arguments[0]).longValue());
		}

		if (_methodName203.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes203, parameterTypes)) {
			return LearningActivityLocalServiceUtil.canBeView((com.liferay.lms.model.LearningActivity)arguments[0],
				((Long)arguments[1]).longValue());
		}

		if (_methodName204.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes204, parameterTypes)) {
			return LearningActivityLocalServiceUtil.canBeView((com.liferay.lms.model.LearningActivity)arguments[0],
				(com.liferay.portal.security.permission.PermissionChecker)arguments[1]);
		}

		if (_methodName205.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes205, parameterTypes)) {
			return LearningActivityLocalServiceUtil.canBeEdited((com.liferay.lms.model.LearningActivity)arguments[0],
				((Long)arguments[1]).longValue());
		}

		if (_methodName206.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes206, parameterTypes)) {
			return LearningActivityLocalServiceUtil.canBeEdited((com.liferay.lms.model.LearningActivity)arguments[0],
				(com.liferay.portal.security.permission.PermissionChecker)arguments[1]);
		}

		if (_methodName207.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes207, parameterTypes)) {
			return LearningActivityLocalServiceUtil.updateLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0]);
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
	private String _methodName167;
	private String[] _methodParameterTypes167;
	private String _methodName168;
	private String[] _methodParameterTypes168;
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
	private String _methodName188;
	private String[] _methodParameterTypes188;
	private String _methodName189;
	private String[] _methodParameterTypes189;
	private String _methodName190;
	private String[] _methodParameterTypes190;
	private String _methodName191;
	private String[] _methodParameterTypes191;
	private String _methodName192;
	private String[] _methodParameterTypes192;
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
}