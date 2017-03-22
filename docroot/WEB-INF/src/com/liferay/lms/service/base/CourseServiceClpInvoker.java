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

import com.liferay.lms.service.CourseServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class CourseServiceClpInvoker {
	public CourseServiceClpInvoker() {
		_methodName192 = "getBeanIdentifier";

		_methodParameterTypes192 = new String[] {  };

		_methodName193 = "setBeanIdentifier";

		_methodParameterTypes193 = new String[] { "java.lang.String" };

		_methodName198 = "getCoursesOfGroup";

		_methodParameterTypes198 = new String[] { "long" };

		_methodName199 = "createCourse";

		_methodParameterTypes199 = new String[] {
				"long", "java.lang.String", "java.lang.String", "boolean",
				"java.lang.String", "int", "int", "int", "int", "int",
				"java.util.Date", "java.util.Date"
			};

		_methodName200 = "createCourse";

		_methodParameterTypes200 = new String[] {
				"java.lang.String", "java.lang.String", "boolean",
				"java.lang.String", "int", "int", "int", "int", "int",
				"java.util.Date", "java.util.Date"
			};

		_methodName201 = "getCourses";

		_methodParameterTypes201 = new String[] {  };

		_methodName202 = "getCourseStudents";

		_methodParameterTypes202 = new String[] { "long" };

		_methodName203 = "getCourseTeachers";

		_methodParameterTypes203 = new String[] { "long" };

		_methodName204 = "getCourseEditors";

		_methodParameterTypes204 = new String[] { "long" };

		_methodName205 = "addStudentToCourse";

		_methodParameterTypes205 = new String[] { "long", "java.lang.String" };

		_methodName206 = "addStudentToCourseWithDates";

		_methodParameterTypes206 = new String[] {
				"long", "java.lang.String", "java.util.Date", "java.util.Date"
			};

		_methodName207 = "editUserInscriptionDates";

		_methodParameterTypes207 = new String[] {
				"long", "java.lang.String", "java.util.Date", "java.util.Date"
			};

		_methodName208 = "editUserInscriptionDates";

		_methodParameterTypes208 = new String[] {
				"long", "long", "java.util.Date", "java.util.Date"
			};

		_methodName209 = "addTeacherToCourse";

		_methodParameterTypes209 = new String[] { "long", "java.lang.String" };

		_methodName210 = "addEditorToCourse";

		_methodParameterTypes210 = new String[] { "long", "java.lang.String" };

		_methodName211 = "removeStudentFromCourse";

		_methodParameterTypes211 = new String[] { "long", "java.lang.String" };

		_methodName212 = "removeTeacherFromCourse";

		_methodParameterTypes212 = new String[] { "long", "java.lang.String" };

		_methodName213 = "removeEditorFromCourse";

		_methodParameterTypes213 = new String[] { "long", "java.lang.String" };

		_methodName214 = "getUserResult";

		_methodParameterTypes214 = new String[] { "long", "java.lang.String" };

		_methodName215 = "myCourses";

		_methodParameterTypes215 = new String[] {  };

		_methodName217 = "addUser";

		_methodParameterTypes217 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String"
			};

		_methodName218 = "updateUser";

		_methodParameterTypes218 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String"
			};

		_methodName219 = "existsCourseName";

		_methodParameterTypes219 = new String[] {
				"java.lang.Long", "java.lang.Long", "java.lang.String"
			};

		_methodName220 = "getLogoUrl";

		_methodParameterTypes220 = new String[] { "java.lang.Long" };

		_methodName221 = "getCoursesParents";

		_methodParameterTypes221 = new String[] { "long" };

		_methodName222 = "getChildCourses";

		_methodParameterTypes222 = new String[] { "long" };

		_methodName223 = "getStudentsFromCourseCount";

		_methodParameterTypes223 = new String[] { "long" };

		_methodName224 = "getPublicCoursesByCompanyId";

		_methodParameterTypes224 = new String[] { "java.lang.Long", "int", "int" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName192.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes192, parameterTypes)) {
			return CourseServiceUtil.getBeanIdentifier();
		}

		if (_methodName193.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes193, parameterTypes)) {
			CourseServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);
		}

		if (_methodName198.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes198, parameterTypes)) {
			return CourseServiceUtil.getCoursesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName199.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes199, parameterTypes)) {
			return CourseServiceUtil.createCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				((Boolean)arguments[3]).booleanValue(),
				(java.lang.String)arguments[4],
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue(),
				((Integer)arguments[7]).intValue(),
				((Integer)arguments[8]).intValue(),
				((Integer)arguments[9]).intValue(),
				(java.util.Date)arguments[10], (java.util.Date)arguments[11]);
		}

		if (_methodName200.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes200, parameterTypes)) {
			return CourseServiceUtil.createCourse((java.lang.String)arguments[0],
				(java.lang.String)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				(java.lang.String)arguments[3],
				((Integer)arguments[4]).intValue(),
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue(),
				((Integer)arguments[7]).intValue(),
				((Integer)arguments[8]).intValue(),
				(java.util.Date)arguments[9], (java.util.Date)arguments[10]);
		}

		if (_methodName201.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes201, parameterTypes)) {
			return CourseServiceUtil.getCourses();
		}

		if (_methodName202.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes202, parameterTypes)) {
			return CourseServiceUtil.getCourseStudents(((Long)arguments[0]).longValue());
		}

		if (_methodName203.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes203, parameterTypes)) {
			return CourseServiceUtil.getCourseTeachers(((Long)arguments[0]).longValue());
		}

		if (_methodName204.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes204, parameterTypes)) {
			return CourseServiceUtil.getCourseEditors(((Long)arguments[0]).longValue());
		}

		if (_methodName205.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes205, parameterTypes)) {
			CourseServiceUtil.addStudentToCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName206.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes206, parameterTypes)) {
			CourseServiceUtil.addStudentToCourseWithDates(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.util.Date)arguments[2],
				(java.util.Date)arguments[3]);
		}

		if (_methodName207.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes207, parameterTypes)) {
			CourseServiceUtil.editUserInscriptionDates(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.util.Date)arguments[2],
				(java.util.Date)arguments[3]);
		}

		if (_methodName208.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes208, parameterTypes)) {
			CourseServiceUtil.editUserInscriptionDates(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(), (java.util.Date)arguments[2],
				(java.util.Date)arguments[3]);
		}

		if (_methodName209.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes209, parameterTypes)) {
			CourseServiceUtil.addTeacherToCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName210.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes210, parameterTypes)) {
			CourseServiceUtil.addEditorToCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName211.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes211, parameterTypes)) {
			CourseServiceUtil.removeStudentFromCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName212.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes212, parameterTypes)) {
			CourseServiceUtil.removeTeacherFromCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName213.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes213, parameterTypes)) {
			CourseServiceUtil.removeEditorFromCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName214.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes214, parameterTypes)) {
			return CourseServiceUtil.getUserResult(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName215.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes215, parameterTypes)) {
			return CourseServiceUtil.myCourses();
		}

		if (_methodName217.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes217, parameterTypes)) {
			CourseServiceUtil.addUser((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3]);
		}

		if (_methodName218.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes218, parameterTypes)) {
			CourseServiceUtil.updateUser((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3]);
		}

		if (_methodName219.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes219, parameterTypes)) {
			return CourseServiceUtil.existsCourseName((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1], (java.lang.String)arguments[2]);
		}

		if (_methodName220.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes220, parameterTypes)) {
			return CourseServiceUtil.getLogoUrl((java.lang.Long)arguments[0]);
		}

		if (_methodName221.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes221, parameterTypes)) {
			return CourseServiceUtil.getCoursesParents(((Long)arguments[0]).longValue());
		}

		if (_methodName222.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes222, parameterTypes)) {
			return CourseServiceUtil.getChildCourses(((Long)arguments[0]).longValue());
		}

		if (_methodName223.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes223, parameterTypes)) {
			return CourseServiceUtil.getStudentsFromCourseCount(((Long)arguments[0]).longValue());
		}

		if (_methodName224.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes224, parameterTypes)) {
			return CourseServiceUtil.getPublicCoursesByCompanyId((java.lang.Long)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName192;
	private String[] _methodParameterTypes192;
	private String _methodName193;
	private String[] _methodParameterTypes193;
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
	private String _methodName224;
	private String[] _methodParameterTypes224;
}