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

import com.liferay.lms.service.ModuleLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class ModuleLocalServiceClpInvoker {
	public ModuleLocalServiceClpInvoker() {
		_methodName0 = "addModule";

		_methodParameterTypes0 = new String[] { "com.liferay.lms.model.Module" };

		_methodName1 = "createModule";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteModule";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteModule";

		_methodParameterTypes3 = new String[] { "com.liferay.lms.model.Module" };

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

		_methodName9 = "fetchModule";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getModule";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getModuleByUuidAndGroupId";

		_methodParameterTypes12 = new String[] { "java.lang.String", "long" };

		_methodName13 = "getModules";

		_methodParameterTypes13 = new String[] { "int", "int" };

		_methodName14 = "getModulesCount";

		_methodParameterTypes14 = new String[] {  };

		_methodName15 = "updateModule";

		_methodParameterTypes15 = new String[] { "com.liferay.lms.model.Module" };

		_methodName16 = "updateModule";

		_methodParameterTypes16 = new String[] {
				"com.liferay.lms.model.Module", "boolean"
			};

		_methodName135 = "getBeanIdentifier";

		_methodParameterTypes135 = new String[] {  };

		_methodName136 = "setBeanIdentifier";

		_methodParameterTypes136 = new String[] { "java.lang.String" };

		_methodName141 = "findAllInUser";

		_methodParameterTypes141 = new String[] { "long" };

		_methodName142 = "findAllInUser";

		_methodParameterTypes142 = new String[] {
				"long", "com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName143 = "findAllInGroup";

		_methodParameterTypes143 = new String[] { "long" };

		_methodName144 = "findAllInGroup";

		_methodParameterTypes144 = new String[] {
				"long", "com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName145 = "findFirstInGroup";

		_methodParameterTypes145 = new String[] { "long" };

		_methodName146 = "findFirstInGroup";

		_methodParameterTypes146 = new String[] {
				"long", "com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName147 = "countInGroup";

		_methodParameterTypes147 = new String[] { "long" };

		_methodName148 = "findAllInUserAndGroup";

		_methodParameterTypes148 = new String[] { "long", "long" };

		_methodName149 = "findAllInUserAndGroup";

		_methodParameterTypes149 = new String[] {
				"long", "long",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName150 = "getPreviusModule";

		_methodParameterTypes150 = new String[] { "long" };

		_methodName151 = "getPreviusModule";

		_methodParameterTypes151 = new String[] { "com.liferay.lms.model.Module" };

		_methodName152 = "getNextModule";

		_methodParameterTypes152 = new String[] { "long" };

		_methodName153 = "getNextModule";

		_methodParameterTypes153 = new String[] { "com.liferay.lms.model.Module" };

		_methodName154 = "goUpModule";

		_methodParameterTypes154 = new String[] { "long" };

		_methodName155 = "goDownModule";

		_methodParameterTypes155 = new String[] { "long" };

		_methodName156 = "moveModule";

		_methodParameterTypes156 = new String[] { "long", "long", "long" };

		_methodName157 = "addmodule";

		_methodParameterTypes157 = new String[] { "com.liferay.lms.model.Module" };

		_methodName158 = "addModule";

		_methodParameterTypes158 = new String[] {
				"java.lang.Long", "java.lang.Long", "java.lang.Long",
				"java.lang.String", "java.lang.String", "java.util.Date",
				"java.util.Date", "java.lang.Long"
			};

		_methodName159 = "remove";

		_methodParameterTypes159 = new String[] { "com.liferay.lms.model.Module" };

		_methodName160 = "updateModule";

		_methodParameterTypes160 = new String[] { "com.liferay.lms.model.Module" };

		_methodName161 = "updateModule";

		_methodParameterTypes161 = new String[] {
				"com.liferay.lms.model.Module", "boolean"
			};

		_methodName162 = "isUserPassed";

		_methodParameterTypes162 = new String[] { "long", "long" };

		_methodName163 = "isLocked";

		_methodParameterTypes163 = new String[] { "long", "long" };

		_methodName164 = "countByGroupId";

		_methodParameterTypes164 = new String[] { "long" };

		_methodName165 = "usersStarted";

		_methodParameterTypes165 = new String[] { "long" };

		_methodName166 = "modulesUserPassed";

		_methodParameterTypes166 = new String[] { "long", "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return ModuleLocalServiceUtil.addModule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return ModuleLocalServiceUtil.createModule(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return ModuleLocalServiceUtil.deleteModule(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return ModuleLocalServiceUtil.deleteModule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return ModuleLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return ModuleLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return ModuleLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return ModuleLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return ModuleLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return ModuleLocalServiceUtil.fetchModule(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return ModuleLocalServiceUtil.getModule(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return ModuleLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return ModuleLocalServiceUtil.getModuleByUuidAndGroupId((java.lang.String)arguments[0],
				((Long)arguments[1]).longValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return ModuleLocalServiceUtil.getModules(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return ModuleLocalServiceUtil.getModulesCount();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return ModuleLocalServiceUtil.updateModule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName16.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes16, parameterTypes)) {
			return ModuleLocalServiceUtil.updateModule((com.liferay.lms.model.Module)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName135.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes135, parameterTypes)) {
			return ModuleLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName136.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes136, parameterTypes)) {
			ModuleLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName141.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes141, parameterTypes)) {
			return ModuleLocalServiceUtil.findAllInUser(((Long)arguments[0]).longValue());
		}

		if (_methodName142.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes142, parameterTypes)) {
			return ModuleLocalServiceUtil.findAllInUser(((Long)arguments[0]).longValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[1]);
		}

		if (_methodName143.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes143, parameterTypes)) {
			return ModuleLocalServiceUtil.findAllInGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName144.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes144, parameterTypes)) {
			return ModuleLocalServiceUtil.findAllInGroup(((Long)arguments[0]).longValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[1]);
		}

		if (_methodName145.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes145, parameterTypes)) {
			return ModuleLocalServiceUtil.findFirstInGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName146.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes146, parameterTypes)) {
			return ModuleLocalServiceUtil.findFirstInGroup(((Long)arguments[0]).longValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[1]);
		}

		if (_methodName147.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes147, parameterTypes)) {
			return ModuleLocalServiceUtil.countInGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName148.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes148, parameterTypes)) {
			return ModuleLocalServiceUtil.findAllInUserAndGroup(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName149.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes149, parameterTypes)) {
			return ModuleLocalServiceUtil.findAllInUserAndGroup(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[2]);
		}

		if (_methodName150.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes150, parameterTypes)) {
			return ModuleLocalServiceUtil.getPreviusModule(((Long)arguments[0]).longValue());
		}

		if (_methodName151.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes151, parameterTypes)) {
			return ModuleLocalServiceUtil.getPreviusModule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName152.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes152, parameterTypes)) {
			return ModuleLocalServiceUtil.getNextModule(((Long)arguments[0]).longValue());
		}

		if (_methodName153.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes153, parameterTypes)) {
			return ModuleLocalServiceUtil.getNextModule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName154.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes154, parameterTypes)) {
			ModuleLocalServiceUtil.goUpModule(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName155.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes155, parameterTypes)) {
			ModuleLocalServiceUtil.goDownModule(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName156.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes156, parameterTypes)) {
			ModuleLocalServiceUtil.moveModule(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue());

			return null;
		}

		if (_methodName157.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes157, parameterTypes)) {
			return ModuleLocalServiceUtil.addmodule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName158.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes158, parameterTypes)) {
			return ModuleLocalServiceUtil.addModule((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1], (java.lang.Long)arguments[2],
				(java.lang.String)arguments[3], (java.lang.String)arguments[4],
				(java.util.Date)arguments[5], (java.util.Date)arguments[6],
				(java.lang.Long)arguments[7]);
		}

		if (_methodName159.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes159, parameterTypes)) {
			ModuleLocalServiceUtil.remove((com.liferay.lms.model.Module)arguments[0]);

			return null;
		}

		if (_methodName160.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes160, parameterTypes)) {
			return ModuleLocalServiceUtil.updateModule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName161.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes161, parameterTypes)) {
			return ModuleLocalServiceUtil.updateModule((com.liferay.lms.model.Module)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName162.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes162, parameterTypes)) {
			return ModuleLocalServiceUtil.isUserPassed(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName163.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes163, parameterTypes)) {
			return ModuleLocalServiceUtil.isLocked(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName164.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes164, parameterTypes)) {
			return ModuleLocalServiceUtil.countByGroupId(((Long)arguments[0]).longValue());
		}

		if (_methodName165.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes165, parameterTypes)) {
			return ModuleLocalServiceUtil.usersStarted(((Long)arguments[0]).longValue());
		}

		if (_methodName166.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes166, parameterTypes)) {
			return ModuleLocalServiceUtil.modulesUserPassed(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
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
	private String _methodName135;
	private String[] _methodParameterTypes135;
	private String _methodName136;
	private String[] _methodParameterTypes136;
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
}