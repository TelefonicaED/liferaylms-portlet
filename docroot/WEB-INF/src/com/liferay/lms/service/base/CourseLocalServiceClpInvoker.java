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

		_methodName185 = "getBeanIdentifier";

		_methodParameterTypes185 = new String[] {  };

		_methodName186 = "setBeanIdentifier";

		_methodParameterTypes186 = new String[] { "java.lang.String" };

		_methodName191 = "getCoursesOfGroup";

		_methodParameterTypes191 = new String[] { "long" };

		_methodName192 = "getOpenCoursesOfGroup";

		_methodParameterTypes192 = new String[] { "long" };

		_methodName193 = "getCourses";

		_methodParameterTypes193 = new String[] { "long" };

		_methodName194 = "countByGroupId";

		_methodParameterTypes194 = new String[] { "long" };

		_methodName195 = "fetchByGroupCreatedId";

		_methodParameterTypes195 = new String[] { "long" };

		_methodName196 = "addCourse";

		_methodParameterTypes196 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.util.Locale", "java.util.Date",
				"java.util.Date", "java.util.Date", "long", "int",
				"com.liferay.portal.service.ServiceContext", "long", "int",
				"boolean"
			};

		_methodName197 = "getUserCourses";

		_methodParameterTypes197 = new String[] { "long" };

		_methodName198 = "getPublicCoursesByCompanyId";

		_methodParameterTypes198 = new String[] { "java.lang.Long" };

		_methodName199 = "getPublicCoursesByCompanyId";

		_methodParameterTypes199 = new String[] { "java.lang.Long", "int" };

		_methodName200 = "getPublicCoursesByCompanyId";

		_methodParameterTypes200 = new String[] { "java.lang.Long", "int", "int" };

		_methodName201 = "addCourse";

		_methodParameterTypes201 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.util.Locale", "java.util.Date",
				"java.util.Date", "java.util.Date", "long", "int", "long",
				"long", "int", "com.liferay.portal.service.ServiceContext",
				"boolean"
			};

		_methodName202 = "addCourse";

		_methodParameterTypes202 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.util.Locale", "java.util.Date",
				"java.util.Date", "java.util.Date",
				"com.liferay.portal.service.ServiceContext", "long"
			};

		_methodName203 = "addCourse";

		_methodParameterTypes203 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.util.Locale", "java.util.Date", "java.util.Date",
				"java.util.Date", "com.liferay.portal.service.ServiceContext",
				"long"
			};

		_methodName208 = "setVisible";

		_methodParameterTypes208 = new String[] { "long", "boolean" };

		_methodName209 = "modCourse";

		_methodParameterTypes209 = new String[] {
				"com.liferay.lms.model.Course", "java.lang.String",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName210 = "modCourse";

		_methodParameterTypes210 = new String[] {
				"com.liferay.lms.model.Course",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName211 = "closeCourse";

		_methodParameterTypes211 = new String[] { "long" };

		_methodName212 = "openCourse";

		_methodParameterTypes212 = new String[] { "long" };

		_methodName213 = "deleteCourse";

		_methodParameterTypes213 = new String[] { "com.liferay.lms.model.Course" };

		_methodName214 = "deleteCourse";

		_methodParameterTypes214 = new String[] { "long" };

		_methodName215 = "existsCourseName";

		_methodParameterTypes215 = new String[] {
				"long", "long", "long", "java.lang.String"
			};

		_methodName216 = "getCourseByGroupCreatedId";

		_methodParameterTypes216 = new String[] { "long" };

		_methodName217 = "existsCourseName";

		_methodParameterTypes217 = new String[] {
				"java.lang.Long", "java.lang.Long", "java.lang.String"
			};

		_methodName218 = "findByCompanyId";

		_methodParameterTypes218 = new String[] { "java.lang.Long" };

		_methodName219 = "getStudentsFromCourseCount";

		_methodParameterTypes219 = new String[] { "long" };

		_methodName220 = "getStudentsFromCourse";

		_methodParameterTypes220 = new String[] { "com.liferay.lms.model.Course" };

		_methodName221 = "getStudentsFromCourse";

		_methodParameterTypes221 = new String[] { "long", "long" };

		_methodName222 = "getByTitleStatusCategoriesTags";

		_methodParameterTypes222 = new String[] {
				"java.lang.String", "int", "long[][]", "long[][]", "long",
				"long", "long", "java.lang.String", "boolean", "boolean", "int",
				"int"
			};

		_methodName223 = "countByTitleStatusCategoriesTags";

		_methodParameterTypes223 = new String[] {
				"java.lang.String", "int", "long[][]", "long[][]", "long",
				"long", "long", "java.lang.String", "boolean", "boolean"
			};

		_methodName224 = "getStudents";

		_methodParameterTypes224 = new String[] {
				"long", "long", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String", "boolean", "int", "int",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName225 = "countStudents";

		_methodParameterTypes225 = new String[] {
				"long", "long", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String", "boolean"
			};

		_methodName226 = "getCoursesCatalogByTitleCategoriesTags";

		_methodParameterTypes226 = new String[] {
				"java.lang.String", "long[][]", "long[][]", "long", "long",
				"long", "java.lang.String", "int", "int"
			};

		_methodName227 = "countCoursesCatalogByTitleCategoriesTags";

		_methodParameterTypes227 = new String[] {
				"java.lang.String", "long[][]", "long[][]", "long", "long",
				"long", "java.lang.String"
			};

		_methodName228 = "getCatalogCoursesAssetTags";

		_methodParameterTypes228 = new String[] {
				"java.lang.String", "long[][]", "long", "long", "long",
				"java.lang.String"
			};

		_methodName229 = "countCategoryCourses";

		_methodParameterTypes229 = new String[] {
				"java.lang.String", "long[][]", "long[][]", "long", "long",
				"long", "java.lang.String"
			};

		_methodName230 = "countTagCourses";

		_methodParameterTypes230 = new String[] {
				"java.lang.String", "long[][]", "long[][]", "long", "long",
				"long", "java.lang.String"
			};

		_methodName231 = "getMyCourses";

		_methodParameterTypes231 = new String[] {
				"long", "long", "com.liferay.portal.theme.ThemeDisplay",
				"java.lang.String", "java.lang.String", "int", "int"
			};

		_methodName232 = "countMyCourses";

		_methodParameterTypes232 = new String[] {
				"long", "long", "com.liferay.portal.theme.ThemeDisplay"
			};

		_methodName233 = "hasUserTries";

		_methodParameterTypes233 = new String[] { "long", "long" };
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

		if (_methodName185.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes185, parameterTypes)) {
			return CourseLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName186.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes186, parameterTypes)) {
			CourseLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);
		}

		if (_methodName191.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes191, parameterTypes)) {
			return CourseLocalServiceUtil.getCoursesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName192.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes192, parameterTypes)) {
			return CourseLocalServiceUtil.getOpenCoursesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName193.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes193, parameterTypes)) {
			return CourseLocalServiceUtil.getCourses(((Long)arguments[0]).longValue());
		}

		if (_methodName194.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes194, parameterTypes)) {
			return CourseLocalServiceUtil.countByGroupId(((Long)arguments[0]).longValue());
		}

		if (_methodName195.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes195, parameterTypes)) {
			return CourseLocalServiceUtil.fetchByGroupCreatedId(((Long)arguments[0]).longValue());
		}

		if (_methodName196.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes196, parameterTypes)) {
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

		if (_methodName197.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes197, parameterTypes)) {
			return CourseLocalServiceUtil.getUserCourses(((Long)arguments[0]).longValue());
		}

		if (_methodName198.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes198, parameterTypes)) {
			return CourseLocalServiceUtil.getPublicCoursesByCompanyId((java.lang.Long)arguments[0]);
		}

		if (_methodName199.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes199, parameterTypes)) {
			return CourseLocalServiceUtil.getPublicCoursesByCompanyId((java.lang.Long)arguments[0],
				((Integer)arguments[1]).intValue());
		}

		if (_methodName200.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes200, parameterTypes)) {
			return CourseLocalServiceUtil.getPublicCoursesByCompanyId((java.lang.Long)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName201.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes201, parameterTypes)) {
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

		if (_methodName202.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes202, parameterTypes)) {
			return CourseLocalServiceUtil.addCourse((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3], (java.util.Locale)arguments[4],
				(java.util.Date)arguments[5], (java.util.Date)arguments[6],
				(java.util.Date)arguments[7],
				(com.liferay.portal.service.ServiceContext)arguments[8],
				((Long)arguments[9]).longValue());
		}

		if (_methodName203.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes203, parameterTypes)) {
			return CourseLocalServiceUtil.addCourse((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.util.Locale)arguments[3], (java.util.Date)arguments[4],
				(java.util.Date)arguments[5], (java.util.Date)arguments[6],
				(com.liferay.portal.service.ServiceContext)arguments[7],
				((Long)arguments[8]).longValue());
		}

		if (_methodName208.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes208, parameterTypes)) {
			CourseLocalServiceUtil.setVisible(((Long)arguments[0]).longValue(),
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName209.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes209, parameterTypes)) {
			return CourseLocalServiceUtil.modCourse((com.liferay.lms.model.Course)arguments[0],
				(java.lang.String)arguments[1],
				(com.liferay.portal.service.ServiceContext)arguments[2]);
		}

		if (_methodName210.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes210, parameterTypes)) {
			return CourseLocalServiceUtil.modCourse((com.liferay.lms.model.Course)arguments[0],
				(com.liferay.portal.service.ServiceContext)arguments[1]);
		}

		if (_methodName211.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes211, parameterTypes)) {
			return CourseLocalServiceUtil.closeCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName212.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes212, parameterTypes)) {
			return CourseLocalServiceUtil.openCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName213.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes213, parameterTypes)) {
			return CourseLocalServiceUtil.deleteCourse((com.liferay.lms.model.Course)arguments[0]);
		}

		if (_methodName214.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes214, parameterTypes)) {
			return CourseLocalServiceUtil.deleteCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName215.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes215, parameterTypes)) {
			return CourseLocalServiceUtil.existsCourseName(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue(), (java.lang.String)arguments[3]);
		}

		if (_methodName216.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes216, parameterTypes)) {
			return CourseLocalServiceUtil.getCourseByGroupCreatedId(((Long)arguments[0]).longValue());
		}

		if (_methodName217.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes217, parameterTypes)) {
			return CourseLocalServiceUtil.existsCourseName((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1], (java.lang.String)arguments[2]);
		}

		if (_methodName218.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes218, parameterTypes)) {
			return CourseLocalServiceUtil.findByCompanyId((java.lang.Long)arguments[0]);
		}

		if (_methodName219.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes219, parameterTypes)) {
			return CourseLocalServiceUtil.getStudentsFromCourseCount(((Long)arguments[0]).longValue());
		}

		if (_methodName220.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes220, parameterTypes)) {
			return CourseLocalServiceUtil.getStudentsFromCourse((com.liferay.lms.model.Course)arguments[0]);
		}

		if (_methodName221.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes221, parameterTypes)) {
			return CourseLocalServiceUtil.getStudentsFromCourse(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName222.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes222, parameterTypes)) {
			return CourseLocalServiceUtil.getByTitleStatusCategoriesTags((java.lang.String)arguments[0],
				((Integer)arguments[1]).intValue(), (long[])arguments[2],
				(long[])arguments[3], ((Long)arguments[4]).longValue(),
				((Long)arguments[5]).longValue(),
				((Long)arguments[6]).longValue(),
				(java.lang.String)arguments[7],
				((Boolean)arguments[8]).booleanValue(),
				((Boolean)arguments[9]).booleanValue(),
				((Integer)arguments[10]).intValue(),
				((Integer)arguments[11]).intValue());
		}

		if (_methodName223.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes223, parameterTypes)) {
			return CourseLocalServiceUtil.countByTitleStatusCategoriesTags((java.lang.String)arguments[0],
				((Integer)arguments[1]).intValue(), (long[])arguments[2],
				(long[])arguments[3], ((Long)arguments[4]).longValue(),
				((Long)arguments[5]).longValue(),
				((Long)arguments[6]).longValue(),
				(java.lang.String)arguments[7],
				((Boolean)arguments[8]).booleanValue(),
				((Boolean)arguments[9]).booleanValue());
		}

		if (_methodName224.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes224, parameterTypes)) {
			return CourseLocalServiceUtil.getStudents(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.lang.String)arguments[2], (java.lang.String)arguments[3],
				(java.lang.String)arguments[4], (java.lang.String)arguments[5],
				((Boolean)arguments[6]).booleanValue(),
				((Integer)arguments[7]).intValue(),
				((Integer)arguments[8]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[9]);
		}

		if (_methodName225.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes225, parameterTypes)) {
			return CourseLocalServiceUtil.countStudents(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.lang.String)arguments[2], (java.lang.String)arguments[3],
				(java.lang.String)arguments[4], (java.lang.String)arguments[5],
				((Boolean)arguments[6]).booleanValue());
		}

		if (_methodName226.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes226, parameterTypes)) {
			return CourseLocalServiceUtil.getCoursesCatalogByTitleCategoriesTags((java.lang.String)arguments[0],
				(long[])arguments[1], (long[])arguments[2],
				((Long)arguments[3]).longValue(),
				((Long)arguments[4]).longValue(),
				((Long)arguments[5]).longValue(),
				(java.lang.String)arguments[6],
				((Integer)arguments[7]).intValue(),
				((Integer)arguments[8]).intValue());
		}

		if (_methodName227.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes227, parameterTypes)) {
			return CourseLocalServiceUtil.countCoursesCatalogByTitleCategoriesTags((java.lang.String)arguments[0],
				(long[])arguments[1], (long[])arguments[2],
				((Long)arguments[3]).longValue(),
				((Long)arguments[4]).longValue(),
				((Long)arguments[5]).longValue(), (java.lang.String)arguments[6]);
		}

		if (_methodName228.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes228, parameterTypes)) {
			return CourseLocalServiceUtil.getCatalogCoursesAssetTags((java.lang.String)arguments[0],
				(long[])arguments[1], ((Long)arguments[2]).longValue(),
				((Long)arguments[3]).longValue(),
				((Long)arguments[4]).longValue(), (java.lang.String)arguments[5]);
		}

		if (_methodName229.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes229, parameterTypes)) {
			return CourseLocalServiceUtil.countCategoryCourses((java.lang.String)arguments[0],
				(long[])arguments[1], (long[])arguments[2],
				((Long)arguments[3]).longValue(),
				((Long)arguments[4]).longValue(),
				((Long)arguments[5]).longValue(), (java.lang.String)arguments[6]);
		}

		if (_methodName230.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes230, parameterTypes)) {
			return CourseLocalServiceUtil.countTagCourses((java.lang.String)arguments[0],
				(long[])arguments[1], (long[])arguments[2],
				((Long)arguments[3]).longValue(),
				((Long)arguments[4]).longValue(),
				((Long)arguments[5]).longValue(), (java.lang.String)arguments[6]);
		}

		if (_methodName231.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes231, parameterTypes)) {
			return CourseLocalServiceUtil.getMyCourses(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(com.liferay.portal.theme.ThemeDisplay)arguments[2],
				(java.lang.String)arguments[3], (java.lang.String)arguments[4],
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue());
		}

		if (_methodName232.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes232, parameterTypes)) {
			return CourseLocalServiceUtil.countMyCourses(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(com.liferay.portal.theme.ThemeDisplay)arguments[2]);
		}

		if (_methodName233.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes233, parameterTypes)) {
			return CourseLocalServiceUtil.hasUserTries(((Long)arguments[0]).longValue(),
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
	private String _methodName185;
	private String[] _methodParameterTypes185;
	private String _methodName186;
	private String[] _methodParameterTypes186;
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
	private String _methodName221;
	private String[] _methodParameterTypes221;
	private String _methodName222;
	private String[] _methodParameterTypes222;
	private String _methodName223;
	private String[] _methodParameterTypes223;
	private String _methodName224;
	private String[] _methodParameterTypes224;
	private String _methodName225;
	private String[] _methodParameterTypes225;
	private String _methodName226;
	private String[] _methodParameterTypes226;
	private String _methodName227;
	private String[] _methodParameterTypes227;
	private String _methodName228;
	private String[] _methodParameterTypes228;
	private String _methodName229;
	private String[] _methodParameterTypes229;
	private String _methodName230;
	private String[] _methodParameterTypes230;
	private String _methodName231;
	private String[] _methodParameterTypes231;
	private String _methodName232;
	private String[] _methodParameterTypes232;
	private String _methodName233;
	private String[] _methodParameterTypes233;
}