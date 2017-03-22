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
		_methodName148 = "getBeanIdentifier";

		_methodParameterTypes148 = new String[] {  };

		_methodName149 = "setBeanIdentifier";

		_methodParameterTypes149 = new String[] { "java.lang.String" };

		_methodName154 = "getCompetencesOfGroup";

		_methodParameterTypes154 = new String[] { "long" };

		_methodName155 = "getCompetencesOfGroup";

		_methodParameterTypes155 = new String[] { "long", "int", "int" };

		_methodName156 = "getCountCompetencesOfGroup";

		_methodParameterTypes156 = new String[] { "long" };

		_methodName157 = "getCompetencesOfGroups";

		_methodParameterTypes157 = new String[] { "long[][]" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName148.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes148, parameterTypes)) {
			return CompetenceServiceUtil.getBeanIdentifier();
		}

		if (_methodName149.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes149, parameterTypes)) {
			CompetenceServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);
		}

		if (_methodName154.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes154, parameterTypes)) {
			return CompetenceServiceUtil.getCompetencesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName155.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes155, parameterTypes)) {
			return CompetenceServiceUtil.getCompetencesOfGroup(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName156.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes156, parameterTypes)) {
			return CompetenceServiceUtil.getCountCompetencesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName157.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes157, parameterTypes)) {
			return CompetenceServiceUtil.getCompetencesOfGroups((long[])arguments[0]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName148;
	private String[] _methodParameterTypes148;
	private String _methodName149;
	private String[] _methodParameterTypes149;
	private String _methodName154;
	private String[] _methodParameterTypes154;
	private String _methodName155;
	private String[] _methodParameterTypes155;
	private String _methodName156;
	private String[] _methodParameterTypes156;
	private String _methodName157;
	private String[] _methodParameterTypes157;
}