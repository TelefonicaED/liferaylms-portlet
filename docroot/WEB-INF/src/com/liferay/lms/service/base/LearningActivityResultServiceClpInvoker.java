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
		_methodName118 = "getBeanIdentifier";

		_methodParameterTypes118 = new String[] {  };

		_methodName119 = "setBeanIdentifier";

		_methodParameterTypes119 = new String[] { "java.lang.String" };

		_methodName124 = "getByActId";

		_methodParameterTypes124 = new String[] { "long" };

		_methodName125 = "getByActIdAndUser";

		_methodParameterTypes125 = new String[] { "long", "java.lang.String" };

		_methodName126 = "userPassed";

		_methodParameterTypes126 = new String[] { "long" };

		_methodName127 = "userLoginPassed";

		_methodParameterTypes127 = new String[] { "long", "java.lang.String" };

		_methodName128 = "update";

		_methodParameterTypes128 = new String[] {
				"long", "long", "java.lang.String"
			};

		_methodName129 = "update";

		_methodParameterTypes129 = new String[] { "long", "java.lang.String" };

		_methodName130 = "update";

		_methodParameterTypes130 = new String[] {
				"long", "java.lang.String", "java.lang.String"
			};

		_methodName131 = "updateFinishTry";

		_methodParameterTypes131 = new String[] {
				"long", "java.lang.String", "java.lang.String"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName118.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes118, parameterTypes)) {
			return LearningActivityResultServiceUtil.getBeanIdentifier();
		}

		if (_methodName119.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes119, parameterTypes)) {
			LearningActivityResultServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName124.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes124, parameterTypes)) {
			return LearningActivityResultServiceUtil.getByActId(((Long)arguments[0]).longValue());
		}

		if (_methodName125.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes125, parameterTypes)) {
			return LearningActivityResultServiceUtil.getByActIdAndUser(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName126.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes126, parameterTypes)) {
			return LearningActivityResultServiceUtil.userPassed(((Long)arguments[0]).longValue());
		}

		if (_methodName127.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes127, parameterTypes)) {
			return LearningActivityResultServiceUtil.userLoginPassed(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName128.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes128, parameterTypes)) {
			return LearningActivityResultServiceUtil.update(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(), (java.lang.String)arguments[2]);
		}

		if (_methodName129.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes129, parameterTypes)) {
			return LearningActivityResultServiceUtil.update(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName130.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes130, parameterTypes)) {
			return LearningActivityResultServiceUtil.update(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2]);
		}

		if (_methodName131.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes131, parameterTypes)) {
			return LearningActivityResultServiceUtil.updateFinishTry(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2]);
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
	private String _methodName128;
	private String[] _methodParameterTypes128;
	private String _methodName129;
	private String[] _methodParameterTypes129;
	private String _methodName130;
	private String[] _methodParameterTypes130;
	private String _methodName131;
	private String[] _methodParameterTypes131;
}