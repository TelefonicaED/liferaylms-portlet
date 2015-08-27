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

import com.liferay.lms.service.CompetenceServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class CompetenceServiceClpInvoker {
	public CompetenceServiceClpInvoker() {
		_methodName118 = "getBeanIdentifier";

		_methodParameterTypes118 = new String[] {  };

		_methodName119 = "setBeanIdentifier";

		_methodParameterTypes119 = new String[] { "java.lang.String" };

		_methodName124 = "getCompetencesOfGroup";

		_methodParameterTypes124 = new String[] { "long" };

		_methodName125 = "getCompetencesOfGroup";

		_methodParameterTypes125 = new String[] { "long", "int", "int" };

		_methodName126 = "getCountCompetencesOfGroup";

		_methodParameterTypes126 = new String[] { "long" };

		_methodName127 = "getCompetencesOfGroups";

		_methodParameterTypes127 = new String[] { "long[][]" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName118.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes118, parameterTypes)) {
			return CompetenceServiceUtil.getBeanIdentifier();
		}

		if (_methodName119.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes119, parameterTypes)) {
			CompetenceServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName124.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes124, parameterTypes)) {
			return CompetenceServiceUtil.getCompetencesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName125.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes125, parameterTypes)) {
			return CompetenceServiceUtil.getCompetencesOfGroup(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName126.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes126, parameterTypes)) {
			return CompetenceServiceUtil.getCountCompetencesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName127.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes127, parameterTypes)) {
			return CompetenceServiceUtil.getCompetencesOfGroups((long[])arguments[0]);
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
}