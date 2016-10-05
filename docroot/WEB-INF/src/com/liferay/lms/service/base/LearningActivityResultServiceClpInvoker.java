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

import com.liferay.lms.service.LearningActivityResultServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class LearningActivityResultServiceClpInvoker {
	public LearningActivityResultServiceClpInvoker() {
		_methodName132 = "getBeanIdentifier";

		_methodParameterTypes132 = new String[] {  };

		_methodName133 = "setBeanIdentifier";

		_methodParameterTypes133 = new String[] { "java.lang.String" };

		_methodName138 = "getByActId";

		_methodParameterTypes138 = new String[] { "long" };

		_methodName139 = "getByActIdAndUser";

		_methodParameterTypes139 = new String[] { "long", "java.lang.String" };

		_methodName140 = "userPassed";

		_methodParameterTypes140 = new String[] { "long" };

		_methodName141 = "userLoginPassed";

		_methodParameterTypes141 = new String[] { "long", "java.lang.String" };

		_methodName142 = "update";

		_methodParameterTypes142 = new String[] {
				"long", "long", "java.lang.String"
			};

		_methodName143 = "update";

		_methodParameterTypes143 = new String[] { "long", "java.lang.String" };

		_methodName144 = "update";

		_methodParameterTypes144 = new String[] {
				"long", "java.lang.String", "java.lang.String"
			};

		_methodName145 = "forceFinishTry";

		_methodParameterTypes145 = new String[] { "long" };

		_methodName146 = "updateFinishTry";

		_methodParameterTypes146 = new String[] {
				"long", "java.lang.String", "java.lang.String"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName132.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes132, parameterTypes)) {
			return LearningActivityResultServiceUtil.getBeanIdentifier();
		}

		if (_methodName133.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes133, parameterTypes)) {
			LearningActivityResultServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);
		}

		if (_methodName138.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes138, parameterTypes)) {
			return LearningActivityResultServiceUtil.getByActId(((Long)arguments[0]).longValue());
		}

		if (_methodName139.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes139, parameterTypes)) {
			return LearningActivityResultServiceUtil.getByActIdAndUser(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName140.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes140, parameterTypes)) {
			return LearningActivityResultServiceUtil.userPassed(((Long)arguments[0]).longValue());
		}

		if (_methodName141.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes141, parameterTypes)) {
			return LearningActivityResultServiceUtil.userLoginPassed(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName142.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes142, parameterTypes)) {
			return LearningActivityResultServiceUtil.update(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(), (java.lang.String)arguments[2]);
		}

		if (_methodName143.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes143, parameterTypes)) {
			return LearningActivityResultServiceUtil.update(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName144.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes144, parameterTypes)) {
			return LearningActivityResultServiceUtil.update(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2]);
		}

		if (_methodName145.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes145, parameterTypes)) {
			LearningActivityResultServiceUtil.forceFinishTry(((Long)arguments[0]).longValue());
		}

		if (_methodName146.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes146, parameterTypes)) {
			return LearningActivityResultServiceUtil.updateFinishTry(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName132;
	private String[] _methodParameterTypes132;
	private String _methodName133;
	private String[] _methodParameterTypes133;
	private String _methodName138;
	private String[] _methodParameterTypes138;
	private String _methodName139;
	private String[] _methodParameterTypes139;
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
}