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
		_methodName188 = "getBeanIdentifier";

		_methodParameterTypes188 = new String[] {  };

		_methodName189 = "setBeanIdentifier";

		_methodParameterTypes189 = new String[] { "java.lang.String" };

		_methodName194 = "getCoursesOfGroup";

		_methodParameterTypes194 = new String[] { "long" };

		_methodName195 = "createCourse";

		_methodParameterTypes195 = new String[] {
				"long", "java.lang.String", "java.lang.String", "boolean",
				"java.lang.String", "int", "int", "int", "int", "int",
				"java.util.Date", "java.util.Date"
			};

		_methodName196 = "createCourse";

		_methodParameterTypes196 = new String[] {
				"java.lang.String", "java.lang.String", "boolean",
				"java.lang.String", "int", "int", "int", "int", "int",
				"java.util.Date", "java.util.Date"
			};

		_methodName197 = "getCourses";

		_methodParameterTypes197 = new String[] {  };

		_methodName198 = "getCourseStudents";

		_methodParameterTypes198 = new String[] { "long" };

		_methodName199 = "getCourseTeachers";

		_methodParameterTypes199 = new String[] { "long" };

		_methodName200 = "getCourseEditors";

		_methodParameterTypes200 = new String[] { "long" };

		_methodName201 = "addStudentToCourse";

		_methodParameterTypes201 = new String[] { "long", "java.lang.String" };

		_methodName202 = "addStudentToCourseWithDates";

		_methodParameterTypes202 = new String[] {
				"long", "java.lang.String", "java.util.Date", "java.util.Date"
			};

		_methodName203 = "editUserInscriptionDates";

		_methodParameterTypes203 = new String[] {
				"long", "java.lang.String", "java.util.Date", "java.util.Date"
			};

		_methodName204 = "editUserInscriptionDates";

		_methodParameterTypes204 = new String[] {
				"long", "long", "java.util.Date", "java.util.Date"
			};

		_methodName205 = "addTeacherToCourse";

		_methodParameterTypes205 = new String[] { "long", "java.lang.String" };

		_methodName206 = "addEditorToCourse";

		_methodParameterTypes206 = new String[] { "long", "java.lang.String" };

		_methodName207 = "removeStudentFromCourse";

		_methodParameterTypes207 = new String[] { "long", "java.lang.String" };

		_methodName208 = "removeTeacherFromCourse";

		_methodParameterTypes208 = new String[] { "long", "java.lang.String" };

		_methodName209 = "removeEditorFromCourse";

		_methodParameterTypes209 = new String[] { "long", "java.lang.String" };

		_methodName210 = "getUserResult";

		_methodParameterTypes210 = new String[] { "long", "java.lang.String" };

		_methodName211 = "myCourses";

		_methodParameterTypes211 = new String[] {  };

		_methodName213 = "addUser";

		_methodParameterTypes213 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String"
			};

		_methodName214 = "updateUser";

		_methodParameterTypes214 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String"
			};

		_methodName215 = "existsCourseName";

		_methodParameterTypes215 = new String[] {
				"java.lang.Long", "java.lang.Long", "java.lang.String"
			};

		_methodName216 = "getLogoUrl";

		_methodParameterTypes216 = new String[] { "java.lang.Long" };

		_methodName217 = "getCoursesParents";

		_methodParameterTypes217 = new String[] { "long" };

		_methodName218 = "getChildCourses";

		_methodParameterTypes218 = new String[] { "long" };

		_methodName219 = "getStudentsFromCourseCount";

		_methodParameterTypes219 = new String[] { "long" };

		_methodName220 = "getPublicCoursesByCompanyId";

		_methodParameterTypes220 = new String[] { "java.lang.Long", "int", "int" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName188.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes188, parameterTypes)) {
			return CourseServiceUtil.getBeanIdentifier();
		}

		if (_methodName189.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes189, parameterTypes)) {
			CourseServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);
		}

		if (_methodName194.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes194, parameterTypes)) {
			return CourseServiceUtil.getCoursesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName195.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes195, parameterTypes)) {
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

		if (_methodName196.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes196, parameterTypes)) {
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

		if (_methodName197.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes197, parameterTypes)) {
			return CourseServiceUtil.getCourses();
		}

		if (_methodName198.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes198, parameterTypes)) {
			return CourseServiceUtil.getCourseStudents(((Long)arguments[0]).longValue());
		}

		if (_methodName199.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes199, parameterTypes)) {
			return CourseServiceUtil.getCourseTeachers(((Long)arguments[0]).longValue());
		}

		if (_methodName200.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes200, parameterTypes)) {
			return CourseServiceUtil.getCourseEditors(((Long)arguments[0]).longValue());
		}

		if (_methodName201.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes201, parameterTypes)) {
			CourseServiceUtil.addStudentToCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName202.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes202, parameterTypes)) {
			CourseServiceUtil.addStudentToCourseWithDates(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.util.Date)arguments[2],
				(java.util.Date)arguments[3]);
		}

		if (_methodName203.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes203, parameterTypes)) {
			CourseServiceUtil.editUserInscriptionDates(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.util.Date)arguments[2],
				(java.util.Date)arguments[3]);
		}

		if (_methodName204.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes204, parameterTypes)) {
			CourseServiceUtil.editUserInscriptionDates(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(), (java.util.Date)arguments[2],
				(java.util.Date)arguments[3]);
		}

		if (_methodName205.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes205, parameterTypes)) {
			CourseServiceUtil.addTeacherToCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName206.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes206, parameterTypes)) {
			CourseServiceUtil.addEditorToCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName207.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes207, parameterTypes)) {
			CourseServiceUtil.removeStudentFromCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName208.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes208, parameterTypes)) {
			CourseServiceUtil.removeTeacherFromCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName209.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes209, parameterTypes)) {
			CourseServiceUtil.removeEditorFromCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName210.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes210, parameterTypes)) {
			return CourseServiceUtil.getUserResult(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName211.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes211, parameterTypes)) {
			return CourseServiceUtil.myCourses();
		}

		if (_methodName213.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes213, parameterTypes)) {
			CourseServiceUtil.addUser((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3]);
		}

		if (_methodName214.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes214, parameterTypes)) {
			CourseServiceUtil.updateUser((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3]);
		}

		if (_methodName215.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes215, parameterTypes)) {
			return CourseServiceUtil.existsCourseName((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1], (java.lang.String)arguments[2]);
		}

		if (_methodName216.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes216, parameterTypes)) {
			return CourseServiceUtil.getLogoUrl((java.lang.Long)arguments[0]);
		}

		if (_methodName217.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes217, parameterTypes)) {
			return CourseServiceUtil.getCoursesParents(((Long)arguments[0]).longValue());
		}

		if (_methodName218.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes218, parameterTypes)) {
			return CourseServiceUtil.getChildCourses(((Long)arguments[0]).longValue());
		}

		if (_methodName219.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes219, parameterTypes)) {
			return CourseServiceUtil.getStudentsFromCourseCount(((Long)arguments[0]).longValue());
		}

		if (_methodName220.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes220, parameterTypes)) {
			return CourseServiceUtil.getPublicCoursesByCompanyId((java.lang.Long)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName188;
	private String[] _methodParameterTypes188;
	private String _methodName189;
	private String[] _methodParameterTypes189;
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
	private String _methodName213;
	private String[] _methodParameterTypes213;
	private String _methodName214;
	private String[] _methodParameterTypes214;
	private String _methodName215;
	private String[] _methodParameterTypes215;
	private String _methodName216;
	private String[] _methodParameterTypes216;
	private String _methodName217;
	private String[] _methodParameterTypes217;
	private String _methodName218;
	private String[] _methodParameterTypes218;
	private String _methodName219;
	private String[] _methodParameterTypes219;
	private String _methodName220;
	private String[] _methodParameterTypes220;
}