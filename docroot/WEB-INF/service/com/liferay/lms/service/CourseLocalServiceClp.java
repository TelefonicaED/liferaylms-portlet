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

package com.liferay.lms.service;

import com.liferay.portal.service.InvokableLocalService;

/**
 * @author TLS
 */
public class CourseLocalServiceClp implements CourseLocalService {
	public CourseLocalServiceClp(InvokableLocalService invokableLocalService) {
		_invokableLocalService = invokableLocalService;

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

		_methodName17 = "getBeanIdentifier";

		_methodParameterTypes17 = new String[] {  };

		_methodName18 = "setBeanIdentifier";

		_methodParameterTypes18 = new String[] { "java.lang.String" };

		_methodName20 = "getCoursesOfGroup";

		_methodParameterTypes20 = new String[] { "long" };

		_methodName21 = "getOpenCoursesOfGroup";

		_methodParameterTypes21 = new String[] { "long" };

		_methodName22 = "getCourses";

		_methodParameterTypes22 = new String[] { "long" };

		_methodName23 = "countByGroupId";

		_methodParameterTypes23 = new String[] { "long" };

		_methodName24 = "fetchByGroupCreatedId";

		_methodParameterTypes24 = new String[] { "long" };

		_methodName25 = "addCourse";

		_methodParameterTypes25 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.util.Locale", "java.util.Date",
				"java.util.Date", "java.util.Date", "long", "int",
				"com.liferay.portal.service.ServiceContext", "long", "int",
				"boolean"
			};

		_methodName26 = "getUserCourses";

		_methodParameterTypes26 = new String[] { "long" };

		_methodName27 = "getOpenedUserCourses";

		_methodParameterTypes27 = new String[] { "long" };

		_methodName28 = "getOpenedUserCourses";

		_methodParameterTypes28 = new String[] { "long", "int", "int" };

		_methodName29 = "getPublicCoursesByCompanyId";

		_methodParameterTypes29 = new String[] { "java.lang.Long" };

		_methodName30 = "getPublicCoursesByCompanyId";

		_methodParameterTypes30 = new String[] { "java.lang.Long", "int", "int" };

		_methodName31 = "addCourse";

		_methodParameterTypes31 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.util.Locale", "java.util.Date",
				"java.util.Date", "java.util.Date", "long", "int", "long",
				"long", "int", "com.liferay.portal.service.ServiceContext",
				"boolean"
			};

		_methodName32 = "addCourse";

		_methodParameterTypes32 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.util.Locale", "java.util.Date",
				"java.util.Date", "java.util.Date", "java.util.Date",
				"java.util.Date", "long", "int", "long", "long", "int",
				"com.liferay.portal.service.ServiceContext", "boolean"
			};

		_methodName33 = "addCourse";

		_methodParameterTypes33 = new String[] {
				"java.util.Map", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.util.Locale", "java.util.Date",
				"java.util.Date", "java.util.Date", "java.util.Date",
				"java.util.Date", "long", "int", "long", "long", "int",
				"com.liferay.portal.service.ServiceContext", "boolean"
			};

		_methodName34 = "addCourse";

		_methodParameterTypes34 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.util.Locale", "java.util.Date",
				"java.util.Date", "java.util.Date",
				"com.liferay.portal.service.ServiceContext", "long"
			};

		_methodName35 = "addCourse";

		_methodParameterTypes35 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.util.Locale", "java.util.Date", "java.util.Date",
				"java.util.Date", "com.liferay.portal.service.ServiceContext",
				"long"
			};

		_methodName36 = "setVisible";

		_methodParameterTypes36 = new String[] { "long", "boolean" };

		_methodName37 = "modCourse";

		_methodParameterTypes37 = new String[] {
				"com.liferay.lms.model.Course", "java.lang.String",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName38 = "modCourse";

		_methodParameterTypes38 = new String[] {
				"com.liferay.lms.model.Course",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName39 = "modCourse";

		_methodParameterTypes39 = new String[] {
				"com.liferay.lms.model.Course", "java.lang.String",
				"com.liferay.portal.service.ServiceContext", "boolean"
			};

		_methodName40 = "modCourse";

		_methodParameterTypes40 = new String[] {
				"com.liferay.lms.model.Course", "java.lang.String",
				"com.liferay.portal.service.ServiceContext", "boolean",
				"boolean"
			};

		_methodName41 = "closeCourse";

		_methodParameterTypes41 = new String[] { "long" };

		_methodName42 = "openCourse";

		_methodParameterTypes42 = new String[] { "long" };

		_methodName43 = "existsCourseName";

		_methodParameterTypes43 = new String[] {
				"long", "long", "long", "java.lang.String"
			};

		_methodName44 = "getCourseByGroupCreatedId";

		_methodParameterTypes44 = new String[] { "long" };

		_methodName45 = "existsCourseName";

		_methodParameterTypes45 = new String[] {
				"java.lang.Long", "java.lang.Long", "java.lang.String"
			};

		_methodName46 = "findByCompanyId";

		_methodParameterTypes46 = new String[] { "java.lang.Long" };

		_methodName47 = "getStudentsFromCourseCount";

		_methodParameterTypes47 = new String[] { "long" };

		_methodName48 = "getStudentsFromCourseCount";

		_methodParameterTypes48 = new String[] { "long", "long" };

		_methodName49 = "getStudentsFromCourseCount";

		_methodParameterTypes49 = new String[] {
				"long", "long", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String", "boolean"
			};

		_methodName50 = "getStudentsFromCourse";

		_methodParameterTypes50 = new String[] { "com.liferay.lms.model.Course" };

		_methodName51 = "getStudentsFromCourse";

		_methodParameterTypes51 = new String[] { "long", "long" };

		_methodName52 = "getStudentsFromCourse";

		_methodParameterTypes52 = new String[] { "long", "long", "long" };

		_methodName53 = "getStudentsFromCourse";

		_methodParameterTypes53 = new String[] {
				"long", "long", "int", "int", "long", "java.lang.String",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"boolean"
			};

		_methodName54 = "getTeachersFromCourse";

		_methodParameterTypes54 = new String[] { "long" };

		_methodName55 = "getTeachersFromCourse";

		_methodParameterTypes55 = new String[] {
				"com.liferay.lms.model.Course", "long"
			};

		_methodName56 = "getTeachersAndEditorsIdsFromCourse";

		_methodParameterTypes56 = new String[] { "com.liferay.lms.model.Course" };

		_methodName57 = "getTeachersFromCourseTeams";

		_methodParameterTypes57 = new String[] {
				"com.liferay.lms.model.Course", "long", "long"
			};

		_methodName58 = "getByTitleStatusCategoriesTags";

		_methodParameterTypes58 = new String[] {
				"java.lang.String", "int", "long[][]", "long[][]", "long",
				"long", "long", "java.lang.String", "boolean", "boolean", "int",
				"int"
			};

		_methodName59 = "countByTitleStatusCategoriesTags";

		_methodParameterTypes59 = new String[] {
				"java.lang.String", "int", "long[][]", "long[][]", "long",
				"long", "long", "java.lang.String", "boolean", "boolean"
			};

		_methodName60 = "getParentCoursesByTitleStatusCategoriesTags";

		_methodParameterTypes60 = new String[] {
				"java.lang.String", "int", "long[][]", "long[][]", "long",
				"long", "long", "java.lang.String", "boolean", "boolean", "int",
				"int"
			};

		_methodName61 = "countParentCoursesByTitleStatusCategoriesTags";

		_methodParameterTypes61 = new String[] {
				"java.lang.String", "int", "long[][]", "long[][]", "long",
				"long", "long", "java.lang.String", "boolean", "boolean"
			};

		_methodName62 = "getParentCoursesByTitleStatusCategoriesTagsTemplates";

		_methodParameterTypes62 = new String[] {
				"java.lang.String", "int", "long[][]", "long[][]",
				"java.lang.String", "long", "long", "long", "java.lang.String",
				"boolean", "boolean", "int", "int"
			};

		_methodName63 = "countParentCoursesByTitleStatusCategoriesTagsTemplates";

		_methodParameterTypes63 = new String[] {
				"java.lang.String", "int", "long[][]", "long[][]",
				"java.lang.String", "long", "long", "long", "java.lang.String",
				"boolean", "boolean"
			};

		_methodName64 = "getChildCoursesByTitle";

		_methodParameterTypes64 = new String[] {
				"java.lang.String", "long", "int", "long", "long", "long",
				"java.lang.String", "boolean", "boolean", "int", "int"
			};

		_methodName65 = "countChildCoursesByTitle";

		_methodParameterTypes65 = new String[] {
				"java.lang.String", "long", "int", "long", "long", "long",
				"java.lang.String", "boolean", "boolean"
			};

		_methodName66 = "getStudents";

		_methodParameterTypes66 = new String[] {
				"long", "long", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String", "boolean", "int", "int",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName67 = "countStudents";

		_methodParameterTypes67 = new String[] {
				"long", "long", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String", "boolean"
			};

		_methodName68 = "countStudentsStatus";

		_methodParameterTypes68 = new String[] {
				"long", "long", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String", "int", "boolean"
			};

		_methodName69 = "getStudents";

		_methodParameterTypes69 = new String[] {
				"long", "long", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String", "int", "long", "boolean",
				"int", "int", "com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName70 = "countStudents";

		_methodParameterTypes70 = new String[] {
				"long", "long", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String", "int", "long", "boolean"
			};

		_methodName71 = "getCoursesCatalogByTitleCategoriesTags";

		_methodParameterTypes71 = new String[] {
				"java.lang.String", "long[][]", "long[][]", "long", "long",
				"long", "java.lang.String", "int", "int"
			};

		_methodName72 = "countCoursesCatalogByTitleCategoriesTags";

		_methodParameterTypes72 = new String[] {
				"java.lang.String", "long[][]", "long[][]", "long", "long",
				"long", "java.lang.String"
			};

		_methodName73 = "getMyCourses";

		_methodParameterTypes73 = new String[] {
				"long", "long", "com.liferay.portal.theme.ThemeDisplay",
				"java.lang.String", "java.lang.String", "int", "int"
			};

		_methodName74 = "countMyCourses";

		_methodParameterTypes74 = new String[] {
				"long", "long", "com.liferay.portal.theme.ThemeDisplay"
			};

		_methodName75 = "hasUserTries";

		_methodParameterTypes75 = new String[] { "long", "long" };

		_methodName76 = "getPublicCoursesByCompanyId";

		_methodParameterTypes76 = new String[] { "java.lang.Long", "int" };

		_methodName77 = "getChildCourses";

		_methodParameterTypes77 = new String[] { "long" };

		_methodName78 = "getChildCourses";

		_methodParameterTypes78 = new String[] { "long", "int", "int" };

		_methodName79 = "getOpenOrRestrictedChildCourses";

		_methodParameterTypes79 = new String[] { "long" };

		_methodName80 = "countChildCourses";

		_methodParameterTypes80 = new String[] { "long" };

		_methodName81 = "countOpenOrRestrictedChildCourses";

		_methodParameterTypes81 = new String[] { "long" };

		_methodName82 = "getCoursesParents";

		_methodParameterTypes82 = new String[] { "long" };

		_methodName83 = "addStudentToCourseWithDates";

		_methodParameterTypes83 = new String[] {
				"long", "long", "java.util.Date", "java.util.Date"
			};

		_methodName84 = "editUserInscriptionDates";

		_methodParameterTypes84 = new String[] {
				"long", "long", "java.util.Date", "java.util.Date"
			};

		_methodName85 = "isLocked";

		_methodParameterTypes85 = new String[] {
				"com.liferay.lms.model.Course", "com.liferay.portal.model.User"
			};

		_methodName86 = "canAccessLock";

		_methodParameterTypes86 = new String[] {
				"long", "com.liferay.portal.model.User"
			};

		_methodName87 = "getLastModuleDateInCourse";

		_methodParameterTypes87 = new String[] { "long" };

		_methodName88 = "getFirstModuleDateInCourse";

		_methodParameterTypes88 = new String[] { "long" };

		_methodName89 = "getImageURL";

		_methodParameterTypes89 = new String[] {
				"com.liferay.lms.model.Course",
				"com.liferay.portal.theme.ThemeDisplay"
			};

		_methodName90 = "addStudentToCourseByUserId";

		_methodParameterTypes90 = new String[] {
				"long", "long", "long",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName91 = "validateAddUserToCourse";

		_methodParameterTypes91 = new String[] { "long", "long", "long" };

		_methodName92 = "getDistinctCourseGroups";

		_methodParameterTypes92 = new String[] { "long" };
	}

	public com.liferay.lms.model.Course addCourse(
		com.liferay.lms.model.Course course)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName0,
					_methodParameterTypes0,
					new Object[] { ClpSerializer.translateInput(course) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course createCourse(long courseId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName1,
					_methodParameterTypes1, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course deleteCourse(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName2,
					_methodParameterTypes2, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course deleteCourse(
		com.liferay.lms.model.Course course)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName3,
					_methodParameterTypes3,
					new Object[] { ClpSerializer.translateInput(course) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName4,
					_methodParameterTypes4, new Object[] {  });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.portal.kernel.dao.orm.DynamicQuery)ClpSerializer.translateOutput(returnObj);
	}

	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName5,
					_methodParameterTypes5,
					new Object[] { ClpSerializer.translateInput(dynamicQuery) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List)ClpSerializer.translateOutput(returnObj);
	}

	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName6,
					_methodParameterTypes6,
					new Object[] {
						ClpSerializer.translateInput(dynamicQuery),
						
					start,
						
					end
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List)ClpSerializer.translateOutput(returnObj);
	}

	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName7,
					_methodParameterTypes7,
					new Object[] {
						ClpSerializer.translateInput(dynamicQuery),
						
					start,
						
					end,
						
					ClpSerializer.translateInput(orderByComparator)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List)ClpSerializer.translateOutput(returnObj);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName8,
					_methodParameterTypes8,
					new Object[] { ClpSerializer.translateInput(dynamicQuery) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Long)returnObj).longValue();
	}

	public com.liferay.lms.model.Course fetchCourse(long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName9,
					_methodParameterTypes9, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course getCourse(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName10,
					_methodParameterTypes10, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName11,
					_methodParameterTypes11,
					new Object[] { ClpSerializer.translateInput(primaryKeyObj) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.portal.model.PersistedModel)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course getCourseByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName12,
					_methodParameterTypes12,
					new Object[] { ClpSerializer.translateInput(uuid), groupId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.lms.model.Course> getCourses(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName13,
					_methodParameterTypes13, new Object[] { start, end });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public int getCoursesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName14,
					_methodParameterTypes14, new Object[] {  });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public com.liferay.lms.model.Course updateCourse(
		com.liferay.lms.model.Course course)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName15,
					_methodParameterTypes15,
					new Object[] { ClpSerializer.translateInput(course) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course updateCourse(
		com.liferay.lms.model.Course course, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName16,
					_methodParameterTypes16,
					new Object[] { ClpSerializer.translateInput(course), merge });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public java.lang.String getBeanIdentifier() {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName17,
					_methodParameterTypes17, new Object[] {  });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		try {
			_invokableLocalService.invokeMethod(_methodName18,
				_methodParameterTypes18,
				new Object[] { ClpSerializer.translateInput(beanIdentifier) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		throw new UnsupportedOperationException();
	}

	public java.util.List<com.liferay.lms.model.Course> getCoursesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName20,
					_methodParameterTypes20, new Object[] { groupId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.lms.model.Course> getOpenCoursesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName21,
					_methodParameterTypes21, new Object[] { groupId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.lms.model.Course> getCourses(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName22,
					_methodParameterTypes22, new Object[] { companyId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public long countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName23,
					_methodParameterTypes23, new Object[] { groupId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Long)returnObj).longValue();
	}

	public com.liferay.lms.model.Course fetchByGroupCreatedId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName24,
					_methodParameterTypes24, new Object[] { groupId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course addCourse(java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String friendlyURL, java.util.Locale locale,
		java.util.Date createDate, java.util.Date startDate,
		java.util.Date endDate, long layoutSetPrototypeId, int typesite,
		com.liferay.portal.service.ServiceContext serviceContext,
		long calificationType, int maxUsers, boolean isFromClone)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName25,
					_methodParameterTypes25,
					new Object[] {
						ClpSerializer.translateInput(title),
						
					ClpSerializer.translateInput(description),
						
					ClpSerializer.translateInput(summary),
						
					ClpSerializer.translateInput(friendlyURL),
						
					ClpSerializer.translateInput(locale),
						
					ClpSerializer.translateInput(createDate),
						
					ClpSerializer.translateInput(startDate),
						
					ClpSerializer.translateInput(endDate),
						
					layoutSetPrototypeId,
						
					typesite,
						
					ClpSerializer.translateInput(serviceContext),
						
					calificationType,
						
					maxUsers,
						
					isFromClone
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.lms.model.Course> getUserCourses(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName26,
					_methodParameterTypes26, new Object[] { userId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.lms.model.Course> getOpenedUserCourses(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName27,
					_methodParameterTypes27, new Object[] { userId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.lms.model.Course> getOpenedUserCourses(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName28,
					_methodParameterTypes28, new Object[] { userId, start, end });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.lms.model.Course> getPublicCoursesByCompanyId(
		java.lang.Long companyId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName29,
					_methodParameterTypes29,
					new Object[] { ClpSerializer.translateInput(companyId) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.lms.model.Course> getPublicCoursesByCompanyId(
		java.lang.Long companyId, int start, int end) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName30,
					_methodParameterTypes30,
					new Object[] {
						ClpSerializer.translateInput(companyId),
						
					start,
						
					end
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course addCourse(java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String friendlyURL, java.util.Locale locale,
		java.util.Date createDate, java.util.Date startDate,
		java.util.Date endDate, long layoutSetPrototypeId, int typesite,
		long CourseEvalId, long calificationType, int maxUsers,
		com.liferay.portal.service.ServiceContext serviceContext,
		boolean isfromClone)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName31,
					_methodParameterTypes31,
					new Object[] {
						ClpSerializer.translateInput(title),
						
					ClpSerializer.translateInput(description),
						
					ClpSerializer.translateInput(summary),
						
					ClpSerializer.translateInput(friendlyURL),
						
					ClpSerializer.translateInput(locale),
						
					ClpSerializer.translateInput(createDate),
						
					ClpSerializer.translateInput(startDate),
						
					ClpSerializer.translateInput(endDate),
						
					layoutSetPrototypeId,
						
					typesite,
						
					CourseEvalId,
						
					calificationType,
						
					maxUsers,
						
					ClpSerializer.translateInput(serviceContext),
						
					isfromClone
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course addCourse(java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String friendlyURL, java.util.Locale locale,
		java.util.Date createDate, java.util.Date startDate,
		java.util.Date endDate, java.util.Date executionStartDate,
		java.util.Date executionEndDate, long layoutSetPrototypeId,
		int typesite, long CourseEvalId, long calificationType, int maxUsers,
		com.liferay.portal.service.ServiceContext serviceContext,
		boolean isfromClone)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName32,
					_methodParameterTypes32,
					new Object[] {
						ClpSerializer.translateInput(title),
						
					ClpSerializer.translateInput(description),
						
					ClpSerializer.translateInput(summary),
						
					ClpSerializer.translateInput(friendlyURL),
						
					ClpSerializer.translateInput(locale),
						
					ClpSerializer.translateInput(createDate),
						
					ClpSerializer.translateInput(startDate),
						
					ClpSerializer.translateInput(endDate),
						
					ClpSerializer.translateInput(executionStartDate),
						
					ClpSerializer.translateInput(executionEndDate),
						
					layoutSetPrototypeId,
						
					typesite,
						
					CourseEvalId,
						
					calificationType,
						
					maxUsers,
						
					ClpSerializer.translateInput(serviceContext),
						
					isfromClone
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course addCourse(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.lang.String description, java.lang.String summary,
		java.lang.String friendlyURL, java.util.Locale locale,
		java.util.Date createDate, java.util.Date startDate,
		java.util.Date endDate, java.util.Date executionStartDate,
		java.util.Date executionEndDate, long layoutSetPrototypeId,
		int typesite, long CourseEvalId, long calificationType, int maxUsers,
		com.liferay.portal.service.ServiceContext serviceContext,
		boolean isfromClone)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName33,
					_methodParameterTypes33,
					new Object[] {
						ClpSerializer.translateInput(titleMap),
						
					ClpSerializer.translateInput(description),
						
					ClpSerializer.translateInput(summary),
						
					ClpSerializer.translateInput(friendlyURL),
						
					ClpSerializer.translateInput(locale),
						
					ClpSerializer.translateInput(createDate),
						
					ClpSerializer.translateInput(startDate),
						
					ClpSerializer.translateInput(endDate),
						
					ClpSerializer.translateInput(executionStartDate),
						
					ClpSerializer.translateInput(executionEndDate),
						
					layoutSetPrototypeId,
						
					typesite,
						
					CourseEvalId,
						
					calificationType,
						
					maxUsers,
						
					ClpSerializer.translateInput(serviceContext),
						
					isfromClone
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course addCourse(java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String friendlyURL, java.util.Locale locale,
		java.util.Date createDate, java.util.Date startDate,
		java.util.Date endDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		long calificationType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName34,
					_methodParameterTypes34,
					new Object[] {
						ClpSerializer.translateInput(title),
						
					ClpSerializer.translateInput(description),
						
					ClpSerializer.translateInput(summary),
						
					ClpSerializer.translateInput(friendlyURL),
						
					ClpSerializer.translateInput(locale),
						
					ClpSerializer.translateInput(createDate),
						
					ClpSerializer.translateInput(startDate),
						
					ClpSerializer.translateInput(endDate),
						
					ClpSerializer.translateInput(serviceContext),
						
					calificationType
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course addCourse(java.lang.String title,
		java.lang.String description, java.lang.String friendlyURL,
		java.util.Locale locale, java.util.Date createDate,
		java.util.Date startDate, java.util.Date endDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		long calificationType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName35,
					_methodParameterTypes35,
					new Object[] {
						ClpSerializer.translateInput(title),
						
					ClpSerializer.translateInput(description),
						
					ClpSerializer.translateInput(friendlyURL),
						
					ClpSerializer.translateInput(locale),
						
					ClpSerializer.translateInput(createDate),
						
					ClpSerializer.translateInput(startDate),
						
					ClpSerializer.translateInput(endDate),
						
					ClpSerializer.translateInput(serviceContext),
						
					calificationType
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public void setVisible(long courseId, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			_invokableLocalService.invokeMethod(_methodName36,
				_methodParameterTypes36, new Object[] { courseId, visible });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
	}

	public com.liferay.lms.model.Course modCourse(
		com.liferay.lms.model.Course course, java.lang.String summary,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName37,
					_methodParameterTypes37,
					new Object[] {
						ClpSerializer.translateInput(course),
						
					ClpSerializer.translateInput(summary),
						
					ClpSerializer.translateInput(serviceContext)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course modCourse(
		com.liferay.lms.model.Course course,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName38,
					_methodParameterTypes38,
					new Object[] {
						ClpSerializer.translateInput(course),
						
					ClpSerializer.translateInput(serviceContext)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course modCourse(
		com.liferay.lms.model.Course course, java.lang.String summary,
		com.liferay.portal.service.ServiceContext serviceContext,
		boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName39,
					_methodParameterTypes39,
					new Object[] {
						ClpSerializer.translateInput(course),
						
					ClpSerializer.translateInput(summary),
						
					ClpSerializer.translateInput(serviceContext),
						
					visible
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course modCourse(
		com.liferay.lms.model.Course course, java.lang.String summary,
		com.liferay.portal.service.ServiceContext serviceContext,
		boolean visible, boolean allowDuplicateName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName40,
					_methodParameterTypes40,
					new Object[] {
						ClpSerializer.translateInput(course),
						
					ClpSerializer.translateInput(summary),
						
					ClpSerializer.translateInput(serviceContext),
						
					visible,
						
					allowDuplicateName
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course closeCourse(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName41,
					_methodParameterTypes41, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public com.liferay.lms.model.Course openCourse(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName42,
					_methodParameterTypes42, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public boolean existsCourseName(long companyId, long classNameId,
		long liveGroupId, java.lang.String name) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName43,
					_methodParameterTypes43,
					new Object[] {
						companyId,
						
					classNameId,
						
					liveGroupId,
						
					ClpSerializer.translateInput(name)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Boolean)returnObj).booleanValue();
	}

	public com.liferay.lms.model.Course getCourseByGroupCreatedId(
		long groupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName44,
					_methodParameterTypes44, new Object[] { groupCreatedId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.lms.model.Course)ClpSerializer.translateOutput(returnObj);
	}

	public boolean existsCourseName(java.lang.Long companyId,
		java.lang.Long courseId, java.lang.String groupName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName45,
					_methodParameterTypes45,
					new Object[] {
						ClpSerializer.translateInput(companyId),
						
					ClpSerializer.translateInput(courseId),
						
					ClpSerializer.translateInput(groupName)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Boolean)returnObj).booleanValue();
	}

	public java.util.List<com.liferay.lms.model.Course> findByCompanyId(
		java.lang.Long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName46,
					_methodParameterTypes46,
					new Object[] { ClpSerializer.translateInput(companyId) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public int getStudentsFromCourseCount(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName47,
					_methodParameterTypes47, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public int getStudentsFromCourseCount(long courseId, long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName48,
					_methodParameterTypes48, new Object[] { courseId, teamId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public int getStudentsFromCourseCount(long courseId, long teamId,
		java.lang.String firstName, java.lang.String lastName,
		java.lang.String screeName, java.lang.String emailAddress,
		boolean andComparator)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName49,
					_methodParameterTypes49,
					new Object[] {
						courseId,
						
					teamId,
						
					ClpSerializer.translateInput(firstName),
						
					ClpSerializer.translateInput(lastName),
						
					ClpSerializer.translateInput(screeName),
						
					ClpSerializer.translateInput(emailAddress),
						
					andComparator
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		com.liferay.lms.model.Course course) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName50,
					_methodParameterTypes50,
					new Object[] { ClpSerializer.translateInput(course) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.portal.model.User>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		long companyId, long courseGroupCreatedId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName51,
					_methodParameterTypes51,
					new Object[] { companyId, courseGroupCreatedId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.portal.model.User>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		long companyId, long courseGroupCreatedId, long teamId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName52,
					_methodParameterTypes52,
					new Object[] { companyId, courseGroupCreatedId, teamId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.portal.model.User>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		long companyId, long courseGroupCreatedId, int start, int end,
		long teamId, java.lang.String firstName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		boolean andOperator) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName53,
					_methodParameterTypes53,
					new Object[] {
						companyId,
						
					courseGroupCreatedId,
						
					start,
						
					end,
						
					teamId,
						
					ClpSerializer.translateInput(firstName),
						
					ClpSerializer.translateInput(lastName),
						
					ClpSerializer.translateInput(screenName),
						
					ClpSerializer.translateInput(emailAddress),
						
					andOperator
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.portal.model.User>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.portal.model.User> getTeachersFromCourse(
		long courseId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName54,
					_methodParameterTypes54, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.portal.model.User>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.portal.model.User> getTeachersFromCourse(
		com.liferay.lms.model.Course course, long teacherRoleId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName55,
					_methodParameterTypes55,
					new Object[] {
						ClpSerializer.translateInput(course),
						
					teacherRoleId
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.portal.model.User>)ClpSerializer.translateOutput(returnObj);
	}

	public long[] getTeachersAndEditorsIdsFromCourse(
		com.liferay.lms.model.Course course) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName56,
					_methodParameterTypes56,
					new Object[] { ClpSerializer.translateInput(course) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (long[])ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.portal.model.User> getTeachersFromCourseTeams(
		com.liferay.lms.model.Course course, long teacherRoleId, long userId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName57,
					_methodParameterTypes57,
					new Object[] {
						ClpSerializer.translateInput(course),
						
					teacherRoleId,
						
					userId
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.portal.model.User>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.lms.model.Course> getByTitleStatusCategoriesTags(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language,
		boolean isAdmin, boolean andOperator, int start, int end) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName58,
					_methodParameterTypes58,
					new Object[] {
						ClpSerializer.translateInput(freeText),
						
					status,
						
					ClpSerializer.translateInput(categories),
						
					ClpSerializer.translateInput(tags),
						
					companyId,
						
					groupId,
						
					userId,
						
					ClpSerializer.translateInput(language),
						
					isAdmin,
						
					andOperator,
						
					start,
						
					end
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public int countByTitleStatusCategoriesTags(java.lang.String freeText,
		int status, long[] categories, long[] tags, long companyId,
		long groupId, long userId, java.lang.String language, boolean isAdmin,
		boolean andOperator) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName59,
					_methodParameterTypes59,
					new Object[] {
						ClpSerializer.translateInput(freeText),
						
					status,
						
					ClpSerializer.translateInput(categories),
						
					ClpSerializer.translateInput(tags),
						
					companyId,
						
					groupId,
						
					userId,
						
					ClpSerializer.translateInput(language),
						
					isAdmin,
						
					andOperator
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public java.util.List<com.liferay.lms.model.Course> getParentCoursesByTitleStatusCategoriesTags(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language,
		boolean isAdmin, boolean andOperator, int start, int end) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName60,
					_methodParameterTypes60,
					new Object[] {
						ClpSerializer.translateInput(freeText),
						
					status,
						
					ClpSerializer.translateInput(categories),
						
					ClpSerializer.translateInput(tags),
						
					companyId,
						
					groupId,
						
					userId,
						
					ClpSerializer.translateInput(language),
						
					isAdmin,
						
					andOperator,
						
					start,
						
					end
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public int countParentCoursesByTitleStatusCategoriesTags(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language,
		boolean isAdmin, boolean andOperator) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName61,
					_methodParameterTypes61,
					new Object[] {
						ClpSerializer.translateInput(freeText),
						
					status,
						
					ClpSerializer.translateInput(categories),
						
					ClpSerializer.translateInput(tags),
						
					companyId,
						
					groupId,
						
					userId,
						
					ClpSerializer.translateInput(language),
						
					isAdmin,
						
					andOperator
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public java.util.List<com.liferay.lms.model.Course> getParentCoursesByTitleStatusCategoriesTagsTemplates(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		java.lang.String templates, long companyId, long groupId, long userId,
		java.lang.String language, boolean isAdmin, boolean andOperator,
		int start, int end) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName62,
					_methodParameterTypes62,
					new Object[] {
						ClpSerializer.translateInput(freeText),
						
					status,
						
					ClpSerializer.translateInput(categories),
						
					ClpSerializer.translateInput(tags),
						
					ClpSerializer.translateInput(templates),
						
					companyId,
						
					groupId,
						
					userId,
						
					ClpSerializer.translateInput(language),
						
					isAdmin,
						
					andOperator,
						
					start,
						
					end
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public int countParentCoursesByTitleStatusCategoriesTagsTemplates(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		java.lang.String templates, long companyId, long groupId, long userId,
		java.lang.String language, boolean isAdmin, boolean andOperator) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName63,
					_methodParameterTypes63,
					new Object[] {
						ClpSerializer.translateInput(freeText),
						
					status,
						
					ClpSerializer.translateInput(categories),
						
					ClpSerializer.translateInput(tags),
						
					ClpSerializer.translateInput(templates),
						
					companyId,
						
					groupId,
						
					userId,
						
					ClpSerializer.translateInput(language),
						
					isAdmin,
						
					andOperator
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public java.util.List<com.liferay.lms.model.Course> getChildCoursesByTitle(
		java.lang.String freeText, long parentCourseId, int status,
		long companyId, long groupId, long userId, java.lang.String language,
		boolean isAdmin, boolean andOperator, int start, int end) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName64,
					_methodParameterTypes64,
					new Object[] {
						ClpSerializer.translateInput(freeText),
						
					parentCourseId,
						
					status,
						
					companyId,
						
					groupId,
						
					userId,
						
					ClpSerializer.translateInput(language),
						
					isAdmin,
						
					andOperator,
						
					start,
						
					end
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public int countChildCoursesByTitle(java.lang.String freeText,
		long parentCourseId, int status, long companyId, long groupId,
		long userId, java.lang.String language, boolean isAdmin,
		boolean andOperator) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName65,
					_methodParameterTypes65,
					new Object[] {
						ClpSerializer.translateInput(freeText),
						
					parentCourseId,
						
					status,
						
					companyId,
						
					groupId,
						
					userId,
						
					ClpSerializer.translateInput(language),
						
					isAdmin,
						
					andOperator
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public java.util.List<com.liferay.portal.model.User> getStudents(
		long courseId, long companyId, java.lang.String screenName,
		java.lang.String firstName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator comparator) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName66,
					_methodParameterTypes66,
					new Object[] {
						courseId,
						
					companyId,
						
					ClpSerializer.translateInput(screenName),
						
					ClpSerializer.translateInput(firstName),
						
					ClpSerializer.translateInput(lastName),
						
					ClpSerializer.translateInput(emailAddress),
						
					andOperator,
						
					start,
						
					end,
						
					ClpSerializer.translateInput(comparator)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.portal.model.User>)ClpSerializer.translateOutput(returnObj);
	}

	public int countStudents(long courseId, long companyId,
		java.lang.String screenName, java.lang.String firstName,
		java.lang.String lastName, java.lang.String emailAddress,
		boolean andOperator) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName67,
					_methodParameterTypes67,
					new Object[] {
						courseId,
						
					companyId,
						
					ClpSerializer.translateInput(screenName),
						
					ClpSerializer.translateInput(firstName),
						
					ClpSerializer.translateInput(lastName),
						
					ClpSerializer.translateInput(emailAddress),
						
					andOperator
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public int countStudentsStatus(long courseId, long companyId,
		java.lang.String screenName, java.lang.String firstName,
		java.lang.String lastName, java.lang.String emailAddress, int status,
		boolean andOperator) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName68,
					_methodParameterTypes68,
					new Object[] {
						courseId,
						
					companyId,
						
					ClpSerializer.translateInput(screenName),
						
					ClpSerializer.translateInput(firstName),
						
					ClpSerializer.translateInput(lastName),
						
					ClpSerializer.translateInput(emailAddress),
						
					status,
						
					andOperator
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public java.util.List<com.liferay.portal.model.User> getStudents(
		long courseId, long companyId, java.lang.String screenName,
		java.lang.String firstName, java.lang.String lastName,
		java.lang.String emailAddress, int status, long teamId,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator comparator) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName69,
					_methodParameterTypes69,
					new Object[] {
						courseId,
						
					companyId,
						
					ClpSerializer.translateInput(screenName),
						
					ClpSerializer.translateInput(firstName),
						
					ClpSerializer.translateInput(lastName),
						
					ClpSerializer.translateInput(emailAddress),
						
					status,
						
					teamId,
						
					andOperator,
						
					start,
						
					end,
						
					ClpSerializer.translateInput(comparator)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.portal.model.User>)ClpSerializer.translateOutput(returnObj);
	}

	public int countStudents(long courseId, long companyId,
		java.lang.String screenName, java.lang.String firstName,
		java.lang.String lastName, java.lang.String emailAddress, int status,
		long teamId, boolean andOperator) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName70,
					_methodParameterTypes70,
					new Object[] {
						courseId,
						
					companyId,
						
					ClpSerializer.translateInput(screenName),
						
					ClpSerializer.translateInput(firstName),
						
					ClpSerializer.translateInput(lastName),
						
					ClpSerializer.translateInput(emailAddress),
						
					status,
						
					teamId,
						
					andOperator
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public java.util.List<com.liferay.lms.model.Course> getCoursesCatalogByTitleCategoriesTags(
		java.lang.String freeText, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language,
		int start, int end) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName71,
					_methodParameterTypes71,
					new Object[] {
						ClpSerializer.translateInput(freeText),
						
					ClpSerializer.translateInput(categories),
						
					ClpSerializer.translateInput(tags),
						
					companyId,
						
					groupId,
						
					userId,
						
					ClpSerializer.translateInput(language),
						
					start,
						
					end
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public int countCoursesCatalogByTitleCategoriesTags(
		java.lang.String freeText, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName72,
					_methodParameterTypes72,
					new Object[] {
						ClpSerializer.translateInput(freeText),
						
					ClpSerializer.translateInput(categories),
						
					ClpSerializer.translateInput(tags),
						
					companyId,
						
					groupId,
						
					userId,
						
					ClpSerializer.translateInput(language)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public java.util.List<com.liferay.lms.views.CourseResultView> getMyCourses(
		long groupId, long userId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		java.lang.String orderByColumn, java.lang.String orderByType,
		int start, int end) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName73,
					_methodParameterTypes73,
					new Object[] {
						groupId,
						
					userId,
						
					ClpSerializer.translateInput(themeDisplay),
						
					ClpSerializer.translateInput(orderByColumn),
						
					ClpSerializer.translateInput(orderByType),
						
					start,
						
					end
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.views.CourseResultView>)ClpSerializer.translateOutput(returnObj);
	}

	public int countMyCourses(long groupId, long userId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName74,
					_methodParameterTypes74,
					new Object[] {
						groupId,
						
					userId,
						
					ClpSerializer.translateInput(themeDisplay)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public boolean hasUserTries(long courseId, long userId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName75,
					_methodParameterTypes75, new Object[] { courseId, userId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Boolean)returnObj).booleanValue();
	}

	public java.util.List<com.liferay.lms.model.Course> getPublicCoursesByCompanyId(
		java.lang.Long companyId, int limit) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName76,
					_methodParameterTypes76,
					new Object[] { ClpSerializer.translateInput(companyId), limit });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.lms.model.Course> getChildCourses(
		long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName77,
					_methodParameterTypes77, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.lms.model.Course> getChildCourses(
		long courseId, int start, int end) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName78,
					_methodParameterTypes78,
					new Object[] { courseId, start, end });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.List<com.liferay.lms.model.Course> getOpenOrRestrictedChildCourses(
		long courseId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName79,
					_methodParameterTypes79, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public int countChildCourses(long courseId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName80,
					_methodParameterTypes80, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public int countOpenOrRestrictedChildCourses(long courseId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName81,
					_methodParameterTypes81, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Integer)returnObj).intValue();
	}

	public java.util.List<com.liferay.lms.model.Course> getCoursesParents(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName82,
					_methodParameterTypes82, new Object[] { groupId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.lms.model.Course>)ClpSerializer.translateOutput(returnObj);
	}

	public void addStudentToCourseWithDates(long courseId, long userId,
		java.util.Date allowStartDate, java.util.Date allowFinishDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			_invokableLocalService.invokeMethod(_methodName83,
				_methodParameterTypes83,
				new Object[] {
					courseId,
					
				userId,
					
				ClpSerializer.translateInput(allowStartDate),
					
				ClpSerializer.translateInput(allowFinishDate)
				});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
	}

	public void editUserInscriptionDates(long courseId, long userId,
		java.util.Date allowStartDate, java.util.Date allowFinishDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			_invokableLocalService.invokeMethod(_methodName84,
				_methodParameterTypes84,
				new Object[] {
					courseId,
					
				userId,
					
				ClpSerializer.translateInput(allowStartDate),
					
				ClpSerializer.translateInput(allowFinishDate)
				});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
	}

	public boolean isLocked(com.liferay.lms.model.Course course,
		com.liferay.portal.model.User user) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName85,
					_methodParameterTypes85,
					new Object[] {
						ClpSerializer.translateInput(course),
						
					ClpSerializer.translateInput(user)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Boolean)returnObj).booleanValue();
	}

	public boolean canAccessLock(long groupCreatedId,
		com.liferay.portal.model.User user) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName86,
					_methodParameterTypes86,
					new Object[] {
						groupCreatedId,
						
					ClpSerializer.translateInput(user)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Boolean)returnObj).booleanValue();
	}

	public java.util.Date getLastModuleDateInCourse(long courseId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName87,
					_methodParameterTypes87, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.Date)ClpSerializer.translateOutput(returnObj);
	}

	public java.util.Date getFirstModuleDateInCourse(long courseId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName88,
					_methodParameterTypes88, new Object[] { courseId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.Date)ClpSerializer.translateOutput(returnObj);
	}

	public java.lang.String getImageURL(com.liferay.lms.model.Course course,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName89,
					_methodParameterTypes89,
					new Object[] {
						ClpSerializer.translateInput(course),
						
					ClpSerializer.translateInput(themeDisplay)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	public java.lang.String addStudentToCourseByUserId(long courseId,
		long userId, long teamId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName90,
					_methodParameterTypes90,
					new Object[] {
						courseId,
						
					userId,
						
					teamId,
						
					ClpSerializer.translateInput(serviceContext)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	public boolean validateAddUserToCourse(long groupId, long userId,
		long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName91,
					_methodParameterTypes91,
					new Object[] { groupId, userId, teamId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Boolean)returnObj).booleanValue();
	}

	public java.util.List<com.liferay.portal.model.Group> getDistinctCourseGroups(
		long companyId) {
		Object returnObj = null;

		try {
			returnObj = _invokableLocalService.invokeMethod(_methodName92,
					_methodParameterTypes92, new Object[] { companyId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.portal.model.Group>)ClpSerializer.translateOutput(returnObj);
	}

	private InvokableLocalService _invokableLocalService;
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
	private String _methodName17;
	private String[] _methodParameterTypes17;
	private String _methodName18;
	private String[] _methodParameterTypes18;
	private String _methodName20;
	private String[] _methodParameterTypes20;
	private String _methodName21;
	private String[] _methodParameterTypes21;
	private String _methodName22;
	private String[] _methodParameterTypes22;
	private String _methodName23;
	private String[] _methodParameterTypes23;
	private String _methodName24;
	private String[] _methodParameterTypes24;
	private String _methodName25;
	private String[] _methodParameterTypes25;
	private String _methodName26;
	private String[] _methodParameterTypes26;
	private String _methodName27;
	private String[] _methodParameterTypes27;
	private String _methodName28;
	private String[] _methodParameterTypes28;
	private String _methodName29;
	private String[] _methodParameterTypes29;
	private String _methodName30;
	private String[] _methodParameterTypes30;
	private String _methodName31;
	private String[] _methodParameterTypes31;
	private String _methodName32;
	private String[] _methodParameterTypes32;
	private String _methodName33;
	private String[] _methodParameterTypes33;
	private String _methodName34;
	private String[] _methodParameterTypes34;
	private String _methodName35;
	private String[] _methodParameterTypes35;
	private String _methodName36;
	private String[] _methodParameterTypes36;
	private String _methodName37;
	private String[] _methodParameterTypes37;
	private String _methodName38;
	private String[] _methodParameterTypes38;
	private String _methodName39;
	private String[] _methodParameterTypes39;
	private String _methodName40;
	private String[] _methodParameterTypes40;
	private String _methodName41;
	private String[] _methodParameterTypes41;
	private String _methodName42;
	private String[] _methodParameterTypes42;
	private String _methodName43;
	private String[] _methodParameterTypes43;
	private String _methodName44;
	private String[] _methodParameterTypes44;
	private String _methodName45;
	private String[] _methodParameterTypes45;
	private String _methodName46;
	private String[] _methodParameterTypes46;
	private String _methodName47;
	private String[] _methodParameterTypes47;
	private String _methodName48;
	private String[] _methodParameterTypes48;
	private String _methodName49;
	private String[] _methodParameterTypes49;
	private String _methodName50;
	private String[] _methodParameterTypes50;
	private String _methodName51;
	private String[] _methodParameterTypes51;
	private String _methodName52;
	private String[] _methodParameterTypes52;
	private String _methodName53;
	private String[] _methodParameterTypes53;
	private String _methodName54;
	private String[] _methodParameterTypes54;
	private String _methodName55;
	private String[] _methodParameterTypes55;
	private String _methodName56;
	private String[] _methodParameterTypes56;
	private String _methodName57;
	private String[] _methodParameterTypes57;
	private String _methodName58;
	private String[] _methodParameterTypes58;
	private String _methodName59;
	private String[] _methodParameterTypes59;
	private String _methodName60;
	private String[] _methodParameterTypes60;
	private String _methodName61;
	private String[] _methodParameterTypes61;
	private String _methodName62;
	private String[] _methodParameterTypes62;
	private String _methodName63;
	private String[] _methodParameterTypes63;
	private String _methodName64;
	private String[] _methodParameterTypes64;
	private String _methodName65;
	private String[] _methodParameterTypes65;
	private String _methodName66;
	private String[] _methodParameterTypes66;
	private String _methodName67;
	private String[] _methodParameterTypes67;
	private String _methodName68;
	private String[] _methodParameterTypes68;
	private String _methodName69;
	private String[] _methodParameterTypes69;
	private String _methodName70;
	private String[] _methodParameterTypes70;
	private String _methodName71;
	private String[] _methodParameterTypes71;
	private String _methodName72;
	private String[] _methodParameterTypes72;
	private String _methodName73;
	private String[] _methodParameterTypes73;
	private String _methodName74;
	private String[] _methodParameterTypes74;
	private String _methodName75;
	private String[] _methodParameterTypes75;
	private String _methodName76;
	private String[] _methodParameterTypes76;
	private String _methodName77;
	private String[] _methodParameterTypes77;
	private String _methodName78;
	private String[] _methodParameterTypes78;
	private String _methodName79;
	private String[] _methodParameterTypes79;
	private String _methodName80;
	private String[] _methodParameterTypes80;
	private String _methodName81;
	private String[] _methodParameterTypes81;
	private String _methodName82;
	private String[] _methodParameterTypes82;
	private String _methodName83;
	private String[] _methodParameterTypes83;
	private String _methodName84;
	private String[] _methodParameterTypes84;
	private String _methodName85;
	private String[] _methodParameterTypes85;
	private String _methodName86;
	private String[] _methodParameterTypes86;
	private String _methodName87;
	private String[] _methodParameterTypes87;
	private String _methodName88;
	private String[] _methodParameterTypes88;
	private String _methodName89;
	private String[] _methodParameterTypes89;
	private String _methodName90;
	private String[] _methodParameterTypes90;
	private String _methodName91;
	private String[] _methodParameterTypes91;
	private String _methodName92;
	private String[] _methodParameterTypes92;
}