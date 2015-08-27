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

import com.liferay.lms.service.ModuleServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class ModuleServiceClpInvoker {
	public ModuleServiceClpInvoker() {
		_methodName118 = "getBeanIdentifier";

		_methodParameterTypes118 = new String[] {  };

		_methodName119 = "setBeanIdentifier";

		_methodParameterTypes119 = new String[] { "java.lang.String" };

		_methodName124 = "findAllInGroup";

		_methodParameterTypes124 = new String[] { "long" };

		_methodName125 = "findAllInCourse";

		_methodParameterTypes125 = new String[] { "long" };

		_methodName126 = "isLocked";

		_methodParameterTypes126 = new String[] { "long" };

		_methodName127 = "PassedByMe";

		_methodParameterTypes127 = new String[] { "long" };

		_methodName128 = "isUserPassed";

		_methodParameterTypes128 = new String[] { "long", "java.lang.String" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName118.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes118, parameterTypes)) {
			return ModuleServiceUtil.getBeanIdentifier();
		}

		if (_methodName119.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes119, parameterTypes)) {
			ModuleServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName124.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes124, parameterTypes)) {
			return ModuleServiceUtil.findAllInGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName125.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes125, parameterTypes)) {
			return ModuleServiceUtil.findAllInCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName126.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes126, parameterTypes)) {
			return ModuleServiceUtil.isLocked(((Long)arguments[0]).longValue());
		}

		if (_methodName127.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes127, parameterTypes)) {
			return ModuleServiceUtil.PassedByMe(((Long)arguments[0]).longValue());
		}

		if (_methodName128.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes128, parameterTypes)) {
			return ModuleServiceUtil.isUserPassed(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
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
}