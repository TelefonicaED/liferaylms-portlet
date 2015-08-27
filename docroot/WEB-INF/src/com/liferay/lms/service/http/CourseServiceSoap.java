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

package com.liferay.lms.service.http;

import com.liferay.lms.service.CourseServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.lms.service.CourseServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.lms.model.CourseSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.lms.model.Course}, that is translated to a
 * {@link com.liferay.lms.model.CourseSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/api/secure/axis. Set the property
 * <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    TLS
 * @see       CourseServiceHttp
 * @see       com.liferay.lms.model.CourseSoap
 * @see       com.liferay.lms.service.CourseServiceUtil
 * @generated
 */
public class CourseServiceSoap {
	public static com.liferay.lms.model.CourseSoap[] getCoursesOfGroup(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.lms.model.Course> returnValue = CourseServiceUtil.getCoursesOfGroup(groupId);

			return com.liferay.lms.model.CourseSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.CourseSoap createCourse(long groupId,
		java.lang.String title, java.lang.String description,
		boolean published, java.lang.String summary, int evaluationmethod,
		int calificationType, int template, int registermethod, int maxusers,
		java.util.Date startregistrationdate, java.util.Date endregistrationdate)
		throws RemoteException {
		try {
			com.liferay.lms.model.Course returnValue = CourseServiceUtil.createCourse(groupId,
					title, description, published, summary, evaluationmethod,
					calificationType, template, registermethod, maxusers,
					startregistrationdate, endregistrationdate);

			return com.liferay.lms.model.CourseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.CourseSoap createCourse(
		java.lang.String title, java.lang.String description,
		boolean published, java.lang.String summary, int evaluationmethod,
		int calificationType, int template, int registermethod, int maxusers,
		java.util.Date startregistrationdate, java.util.Date endregistrationdate)
		throws RemoteException {
		try {
			com.liferay.lms.model.Course returnValue = CourseServiceUtil.createCourse(title,
					description, published, summary, evaluationmethod,
					calificationType, template, registermethod, maxusers,
					startregistrationdate, endregistrationdate);

			return com.liferay.lms.model.CourseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.CourseSoap[] getCourses()
		throws RemoteException {
		try {
			java.util.List<com.liferay.lms.model.Course> returnValue = CourseServiceUtil.getCourses();

			return com.liferay.lms.model.CourseSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String[] getCourseStudents(long courseId)
		throws RemoteException {
		try {
			java.util.List<java.lang.String> returnValue = CourseServiceUtil.getCourseStudents(courseId);

			return returnValue.toArray(new java.lang.String[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String[] getCourseTeachers(long courseId)
		throws RemoteException {
		try {
			java.util.List<java.lang.String> returnValue = CourseServiceUtil.getCourseTeachers(courseId);

			return returnValue.toArray(new java.lang.String[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String[] getCourseEditors(long courseId)
		throws RemoteException {
		try {
			java.util.List<java.lang.String> returnValue = CourseServiceUtil.getCourseEditors(courseId);

			return returnValue.toArray(new java.lang.String[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void addStudentToCourse(long courseId, java.lang.String login)
		throws RemoteException {
		try {
			CourseServiceUtil.addStudentToCourse(courseId, login);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void addStudentToCourseWithDates(long courseId,
		java.lang.String login, java.util.Date allowStartDate,
		java.util.Date allowFinishDate) throws RemoteException {
		try {
			CourseServiceUtil.addStudentToCourseWithDates(courseId, login,
				allowStartDate, allowFinishDate);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void editUserInscriptionDates(long courseId,
		java.lang.String login, java.util.Date allowStartDate,
		java.util.Date allowFinishDate) throws RemoteException {
		try {
			CourseServiceUtil.editUserInscriptionDates(courseId, login,
				allowStartDate, allowFinishDate);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void editUserInscriptionDates(long courseId, long userId,
		java.util.Date allowStartDate, java.util.Date allowFinishDate)
		throws RemoteException {
		try {
			CourseServiceUtil.editUserInscriptionDates(courseId, userId,
				allowStartDate, allowFinishDate);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void addTeacherToCourse(long courseId, java.lang.String login)
		throws RemoteException {
		try {
			CourseServiceUtil.addTeacherToCourse(courseId, login);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void addEditorToCourse(long courseId, java.lang.String login)
		throws RemoteException {
		try {
			CourseServiceUtil.addEditorToCourse(courseId, login);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void removeStudentFromCourse(long courseId,
		java.lang.String login) throws RemoteException {
		try {
			CourseServiceUtil.removeStudentFromCourse(courseId, login);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void removeTeacherFromCourse(long courseId,
		java.lang.String login) throws RemoteException {
		try {
			CourseServiceUtil.removeTeacherFromCourse(courseId, login);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void removeEditorFromCourse(long courseId,
		java.lang.String login) throws RemoteException {
		try {
			CourseServiceUtil.removeEditorFromCourse(courseId, login);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long getUserResult(long courseId, java.lang.String login)
		throws RemoteException {
		try {
			long returnValue = CourseServiceUtil.getUserResult(courseId, login);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.lms.model.CourseSoap[] myCourses()
		throws RemoteException {
		try {
			java.util.List<com.liferay.lms.model.Course> returnValue = CourseServiceUtil.myCourses();

			return com.liferay.lms.model.CourseSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void addUser(java.lang.String login,
		java.lang.String firstName, java.lang.String lastName,
		java.lang.String email) throws RemoteException {
		try {
			CourseServiceUtil.addUser(login, firstName, lastName, email);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateUser(java.lang.String login,
		java.lang.String firstName, java.lang.String lastName,
		java.lang.String email) throws RemoteException {
		try {
			CourseServiceUtil.updateUser(login, firstName, lastName, email);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean existsCourseName(java.lang.Long companyId,
		java.lang.Long groupId, java.lang.String groupName)
		throws RemoteException {
		try {
			boolean returnValue = CourseServiceUtil.existsCourseName(companyId,
					groupId, groupName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String getLogoUrl(java.lang.Long courseId)
		throws RemoteException {
		try {
			java.lang.String returnValue = CourseServiceUtil.getLogoUrl(courseId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CourseServiceSoap.class);
}