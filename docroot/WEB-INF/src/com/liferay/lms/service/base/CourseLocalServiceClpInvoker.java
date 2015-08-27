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

import com.liferay.lms.service.CourseLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class CourseLocalServiceClpInvoker {
	public CourseLocalServiceClpInvoker() {
		_methodName0 = "addCourse";

		_methodParameterTypes0 = new String[] { "com.liferay.lms.model.Course" };

		_methodName1 = "createCourse";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteCourse";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteCourse";

		_methodParameterTypes3 = new String[] { "com.liferay.lms.model.Course" };

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

		_methodName9 = "fetchCourse";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getCourse";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getCourseByUuidAndGroupId";

		_methodParameterTypes12 = new String[] { "java.lang.String", "long" };

		_methodName13 = "getCourses";

		_methodParameterTypes13 = new String[] { "int", "int" };

		_methodName14 = "getCoursesCount";

		_methodParameterTypes14 = new String[] {  };

		_methodName15 = "updateCourse";

		_methodParameterTypes15 = new String[] { "com.liferay.lms.model.Course" };

		_methodName16 = "updateCourse";

		_methodParameterTypes16 = new String[] {
				"com.liferay.lms.model.Course", "boolean"
			};

		_methodName179 = "getBeanIdentifier";

		_methodParameterTypes179 = new String[] {  };

		_methodName180 = "setBeanIdentifier";

		_methodParameterTypes180 = new String[] { "java.lang.String" };

		_methodName185 = "getCoursesOfGroup";

		_methodParameterTypes185 = new String[] { "long" };

		_methodName186 = "getOpenCoursesOfGroup";

		_methodParameterTypes186 = new String[] { "long" };

		_methodName187 = "getCourses";

		_methodParameterTypes187 = new String[] { "long" };

		_methodName188 = "countByGroupId";

		_methodParameterTypes188 = new String[] { "long" };

		_methodName189 = "fetchByGroupCreatedId";

		_methodParameterTypes189 = new String[] { "long" };

		_methodName190 = "addCourse";

		_methodParameterTypes190 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.util.Locale", "java.util.Date",
				"java.util.Date", "java.util.Date", "long", "int",
				"com.liferay.portal.service.ServiceContext", "long", "int",
				"boolean"
			};

		_methodName191 = "getUserCourses";

		_methodParameterTypes191 = new String[] { "long" };

		_methodName192 = "getPublicCoursesByCompanyId";

		_methodParameterTypes192 = new String[] { "java.lang.Long" };

		_methodName193 = "addCourse";

		_methodParameterTypes193 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.util.Locale", "java.util.Date",
				"java.util.Date", "java.util.Date", "long", "int", "long",
				"long", "int", "com.liferay.portal.service.ServiceContext",
				"boolean"
			};

		_methodName194 = "addCourse";

		_methodParameterTypes194 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.util.Locale", "java.util.Date",
				"java.util.Date", "java.util.Date",
				"com.liferay.portal.service.ServiceContext", "long"
			};

		_methodName195 = "addCourse";

		_methodParameterTypes195 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.util.Locale", "java.util.Date", "java.util.Date",
				"java.util.Date", "com.liferay.portal.service.ServiceContext",
				"long"
			};

		_methodName200 = "setVisible";

		_methodParameterTypes200 = new String[] { "long", "boolean" };

		_methodName201 = "modCourse";

		_methodParameterTypes201 = new String[] {
				"com.liferay.lms.model.Course", "java.lang.String",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName202 = "modCourse";

		_methodParameterTypes202 = new String[] {
				"com.liferay.lms.model.Course",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName203 = "closeCourse";

		_methodParameterTypes203 = new String[] { "long" };

		_methodName204 = "openCourse";

		_methodParameterTypes204 = new String[] { "long" };

		_methodName205 = "deleteCourse";

		_methodParameterTypes205 = new String[] { "com.liferay.lms.model.Course" };

		_methodName206 = "deleteCourse";

		_methodParameterTypes206 = new String[] { "long" };

		_methodName207 = "existsCourseName";

		_methodParameterTypes207 = new String[] {
				"long", "long", "long", "java.lang.String"
			};

		_methodName208 = "getCourseByGroupCreatedId";

		_methodParameterTypes208 = new String[] { "long" };

		_methodName209 = "existsCourseName";

		_methodParameterTypes209 = new String[] {
				"java.lang.Long", "java.lang.Long", "java.lang.String"
			};

		_methodName210 = "findByCompanyId";

		_methodParameterTypes210 = new String[] { "java.lang.Long" };

		_methodName211 = "getStudentsFromCourse";

		_methodParameterTypes211 = new String[] { "com.liferay.lms.model.Course" };

		_methodName212 = "getStudentsFromCourse";

		_methodParameterTypes212 = new String[] { "long", "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return CourseLocalServiceUtil.addCourse((com.liferay.lms.model.Course)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return CourseLocalServiceUtil.createCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return CourseLocalServiceUtil.deleteCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return CourseLocalServiceUtil.deleteCourse((com.liferay.lms.model.Course)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return CourseLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return CourseLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return CourseLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return CourseLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return CourseLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return CourseLocalServiceUtil.fetchCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return CourseLocalServiceUtil.getCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return CourseLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return CourseLocalServiceUtil.getCourseByUuidAndGroupId((java.lang.String)arguments[0],
				((Long)arguments[1]).longValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return CourseLocalServiceUtil.getCourses(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return CourseLocalServiceUtil.getCoursesCount();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return CourseLocalServiceUtil.updateCourse((com.liferay.lms.model.Course)arguments[0]);
		}

		if (_methodName16.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes16, parameterTypes)) {
			return CourseLocalServiceUtil.updateCourse((com.liferay.lms.model.Course)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName179.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes179, parameterTypes)) {
			return CourseLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName180.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes180, parameterTypes)) {
			CourseLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName185.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes185, parameterTypes)) {
			return CourseLocalServiceUtil.getCoursesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName186.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes186, parameterTypes)) {
			return CourseLocalServiceUtil.getOpenCoursesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName187.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes187, parameterTypes)) {
			return CourseLocalServiceUtil.getCourses(((Long)arguments[0]).longValue());
		}

		if (_methodName188.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes188, parameterTypes)) {
			return CourseLocalServiceUtil.countByGroupId(((Long)arguments[0]).longValue());
		}

		if (_methodName189.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes189, parameterTypes)) {
			return CourseLocalServiceUtil.fetchByGroupCreatedId(((Long)arguments[0]).longValue());
		}

		if (_methodName190.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes190, parameterTypes)) {
			return CourseLocalServiceUtil.addCourse((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3], (java.util.Locale)arguments[4],
				(java.util.Date)arguments[5], (java.util.Date)arguments[6],
				(java.util.Date)arguments[7], ((Long)arguments[8]).longValue(),
				((Integer)arguments[9]).intValue(),
				(com.liferay.portal.service.ServiceContext)arguments[10],
				((Long)arguments[11]).longValue(),
				((Integer)arguments[12]).intValue(),
				((Boolean)arguments[13]).booleanValue());
		}

		if (_methodName191.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes191, parameterTypes)) {
			return CourseLocalServiceUtil.getUserCourses(((Long)arguments[0]).longValue());
		}

		if (_methodName192.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes192, parameterTypes)) {
			return CourseLocalServiceUtil.getPublicCoursesByCompanyId((java.lang.Long)arguments[0]);
		}

		if (_methodName193.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes193, parameterTypes)) {
			return CourseLocalServiceUtil.addCourse((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3], (java.util.Locale)arguments[4],
				(java.util.Date)arguments[5], (java.util.Date)arguments[6],
				(java.util.Date)arguments[7], ((Long)arguments[8]).longValue(),
				((Integer)arguments[9]).intValue(),
				((Long)arguments[10]).longValue(),
				((Long)arguments[11]).longValue(),
				((Integer)arguments[12]).intValue(),
				(com.liferay.portal.service.ServiceContext)arguments[13],
				((Boolean)arguments[14]).booleanValue());
		}

		if (_methodName194.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes194, parameterTypes)) {
			return CourseLocalServiceUtil.addCourse((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3], (java.util.Locale)arguments[4],
				(java.util.Date)arguments[5], (java.util.Date)arguments[6],
				(java.util.Date)arguments[7],
				(com.liferay.portal.service.ServiceContext)arguments[8],
				((Long)arguments[9]).longValue());
		}

		if (_methodName195.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes195, parameterTypes)) {
			return CourseLocalServiceUtil.addCourse((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.util.Locale)arguments[3], (java.util.Date)arguments[4],
				(java.util.Date)arguments[5], (java.util.Date)arguments[6],
				(com.liferay.portal.service.ServiceContext)arguments[7],
				((Long)arguments[8]).longValue());
		}

		if (_methodName200.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes200, parameterTypes)) {
			CourseLocalServiceUtil.setVisible(((Long)arguments[0]).longValue(),
				((Boolean)arguments[1]).booleanValue());

			return null;
		}

		if (_methodName201.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes201, parameterTypes)) {
			return CourseLocalServiceUtil.modCourse((com.liferay.lms.model.Course)arguments[0],
				(java.lang.String)arguments[1],
				(com.liferay.portal.service.ServiceContext)arguments[2]);
		}

		if (_methodName202.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes202, parameterTypes)) {
			return CourseLocalServiceUtil.modCourse((com.liferay.lms.model.Course)arguments[0],
				(com.liferay.portal.service.ServiceContext)arguments[1]);
		}

		if (_methodName203.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes203, parameterTypes)) {
			return CourseLocalServiceUtil.closeCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName204.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes204, parameterTypes)) {
			return CourseLocalServiceUtil.openCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName205.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes205, parameterTypes)) {
			return CourseLocalServiceUtil.deleteCourse((com.liferay.lms.model.Course)arguments[0]);
		}

		if (_methodName206.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes206, parameterTypes)) {
			return CourseLocalServiceUtil.deleteCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName207.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes207, parameterTypes)) {
			return CourseLocalServiceUtil.existsCourseName(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue(), (java.lang.String)arguments[3]);
		}

		if (_methodName208.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes208, parameterTypes)) {
			return CourseLocalServiceUtil.getCourseByGroupCreatedId(((Long)arguments[0]).longValue());
		}

		if (_methodName209.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes209, parameterTypes)) {
			return CourseLocalServiceUtil.existsCourseName((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1], (java.lang.String)arguments[2]);
		}

		if (_methodName210.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes210, parameterTypes)) {
			return CourseLocalServiceUtil.findByCompanyId((java.lang.Long)arguments[0]);
		}

		if (_methodName211.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes211, parameterTypes)) {
			return CourseLocalServiceUtil.getStudentsFromCourse((com.liferay.lms.model.Course)arguments[0]);
		}

		if (_methodName212.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes212, parameterTypes)) {
			return CourseLocalServiceUtil.getStudentsFromCourse(((Long)arguments[0]).longValue(),
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
	private String _methodName179;
	private String[] _methodParameterTypes179;
	private String _methodName180;
	private String[] _methodParameterTypes180;
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
}