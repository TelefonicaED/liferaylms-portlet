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

import com.liferay.lms.service.CourseResultLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class CourseResultLocalServiceClpInvoker {
	public CourseResultLocalServiceClpInvoker() {
		_methodName0 = "addCourseResult";

		_methodParameterTypes0 = new String[] {
				"com.liferay.lms.model.CourseResult"
			};

		_methodName1 = "createCourseResult";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteCourseResult";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteCourseResult";

		_methodParameterTypes3 = new String[] {
				"com.liferay.lms.model.CourseResult"
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

		_methodName9 = "fetchCourseResult";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getCourseResult";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getCourseResults";

		_methodParameterTypes12 = new String[] { "int", "int" };

		_methodName13 = "getCourseResultsCount";

		_methodParameterTypes13 = new String[] {  };

		_methodName14 = "updateCourseResult";

		_methodParameterTypes14 = new String[] {
				"com.liferay.lms.model.CourseResult"
			};

		_methodName15 = "updateCourseResult";

		_methodParameterTypes15 = new String[] {
				"com.liferay.lms.model.CourseResult", "boolean"
			};

		_methodName134 = "getBeanIdentifier";

		_methodParameterTypes134 = new String[] {  };

		_methodName135 = "setBeanIdentifier";

		_methodParameterTypes135 = new String[] { "java.lang.String" };

		_methodName140 = "getByUserAndCourse";

		_methodParameterTypes140 = new String[] { "long", "long" };

		_methodName141 = "countByCourseId";

		_methodParameterTypes141 = new String[] { "long", "boolean" };

		_methodName142 = "countStudentsByCourseId";

		_methodParameterTypes142 = new String[] {
				"com.liferay.lms.model.Course", "boolean"
			};

		_methodName143 = "countStudentsByCourseId";

		_methodParameterTypes143 = new String[] { "com.liferay.lms.model.Course" };

		_methodName144 = "avgResult";

		_methodParameterTypes144 = new String[] { "long", "boolean" };

		_methodName145 = "avgResult";

		_methodParameterTypes145 = new String[] { "long" };

		_methodName146 = "avgStudentsResult";

		_methodParameterTypes146 = new String[] {
				"com.liferay.lms.model.Course", "boolean"
			};

		_methodName147 = "create";

		_methodParameterTypes147 = new String[] { "long", "long" };

		_methodName148 = "create";

		_methodParameterTypes148 = new String[] {
				"long", "long", "java.util.Date", "java.util.Date"
			};

		_methodName149 = "update";

		_methodParameterTypes149 = new String[] {
				"com.liferay.lms.model.CourseResult"
			};

		_methodName150 = "update";

		_methodParameterTypes150 = new String[] {
				"com.liferay.lms.model.ModuleResult"
			};

		_methodName151 = "getCourseResultByCourseAndUser";

		_methodParameterTypes151 = new String[] { "long", "long" };

		_methodName152 = "translateResult";

		_methodParameterTypes152 = new String[] {
				"java.util.Locale", "double", "long"
			};

		_methodName153 = "softInitializeByGroupIdAndUserId";

		_methodParameterTypes153 = new String[] { "long", "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return CourseResultLocalServiceUtil.addCourseResult((com.liferay.lms.model.CourseResult)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return CourseResultLocalServiceUtil.createCourseResult(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return CourseResultLocalServiceUtil.deleteCourseResult(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return CourseResultLocalServiceUtil.deleteCourseResult((com.liferay.lms.model.CourseResult)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return CourseResultLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return CourseResultLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return CourseResultLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return CourseResultLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return CourseResultLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return CourseResultLocalServiceUtil.fetchCourseResult(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return CourseResultLocalServiceUtil.getCourseResult(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return CourseResultLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return CourseResultLocalServiceUtil.getCourseResults(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return CourseResultLocalServiceUtil.getCourseResultsCount();
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return CourseResultLocalServiceUtil.updateCourseResult((com.liferay.lms.model.CourseResult)arguments[0]);
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return CourseResultLocalServiceUtil.updateCourseResult((com.liferay.lms.model.CourseResult)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName134.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes134, parameterTypes)) {
			return CourseResultLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName135.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes135, parameterTypes)) {
			CourseResultLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName140.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes140, parameterTypes)) {
			return CourseResultLocalServiceUtil.getByUserAndCourse(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName141.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes141, parameterTypes)) {
			return CourseResultLocalServiceUtil.countByCourseId(((Long)arguments[0]).longValue(),
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName142.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes142, parameterTypes)) {
			return CourseResultLocalServiceUtil.countStudentsByCourseId((com.liferay.lms.model.Course)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName143.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes143, parameterTypes)) {
			return CourseResultLocalServiceUtil.countStudentsByCourseId((com.liferay.lms.model.Course)arguments[0]);
		}

		if (_methodName144.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes144, parameterTypes)) {
			return CourseResultLocalServiceUtil.avgResult(((Long)arguments[0]).longValue(),
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName145.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes145, parameterTypes)) {
			return CourseResultLocalServiceUtil.avgResult(((Long)arguments[0]).longValue());
		}

		if (_methodName146.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes146, parameterTypes)) {
			return CourseResultLocalServiceUtil.avgStudentsResult((com.liferay.lms.model.Course)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName147.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes147, parameterTypes)) {
			return CourseResultLocalServiceUtil.create(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName148.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes148, parameterTypes)) {
			return CourseResultLocalServiceUtil.create(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(), (java.util.Date)arguments[2],
				(java.util.Date)arguments[3]);
		}

		if (_methodName149.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes149, parameterTypes)) {
			CourseResultLocalServiceUtil.update((com.liferay.lms.model.CourseResult)arguments[0]);

			return null;
		}

		if (_methodName150.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes150, parameterTypes)) {
			CourseResultLocalServiceUtil.update((com.liferay.lms.model.ModuleResult)arguments[0]);

			return null;
		}

		if (_methodName151.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes151, parameterTypes)) {
			return CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName152.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes152, parameterTypes)) {
			return CourseResultLocalServiceUtil.translateResult((java.util.Locale)arguments[0],
				((Double)arguments[1]).doubleValue(),
				((Long)arguments[2]).longValue());
		}

		if (_methodName153.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes153, parameterTypes)) {
			CourseResultLocalServiceUtil.softInitializeByGroupIdAndUserId(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());

			return null;
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
}