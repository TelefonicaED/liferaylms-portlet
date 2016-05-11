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

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link CourseService}.
 * </p>
 *
 * @author    TLS
 * @see       CourseService
 * @generated
 */
public class CourseServiceWrapper implements CourseService,
	ServiceWrapper<CourseService> {
	public CourseServiceWrapper(CourseService courseService) {
		_courseService = courseService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _courseService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_courseService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _courseService.invokeMethod(name, parameterTypes, arguments);
	}

	public java.util.List<com.liferay.lms.model.Course> getCoursesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseService.getCoursesOfGroup(groupId);
	}

	public com.liferay.lms.model.Course createCourse(long groupId,
		java.lang.String title, java.lang.String description,
		boolean published, java.lang.String summary, int evaluationmethod,
		int calificationType, int template, int registermethod, int maxusers,
		java.util.Date startregistrationdate, java.util.Date endregistrationdate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseService.createCourse(groupId, title, description,
			published, summary, evaluationmethod, calificationType, template,
			registermethod, maxusers, startregistrationdate, endregistrationdate);
	}

	public com.liferay.lms.model.Course createCourse(java.lang.String title,
		java.lang.String description, boolean published,
		java.lang.String summary, int evaluationmethod, int calificationType,
		int template, int registermethod, int maxusers,
		java.util.Date startregistrationdate, java.util.Date endregistrationdate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseService.createCourse(title, description, published,
			summary, evaluationmethod, calificationType, template,
			registermethod, maxusers, startregistrationdate, endregistrationdate);
	}

	public java.util.List<com.liferay.lms.model.Course> getCourses()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseService.getCourses();
	}

	public java.util.List<java.lang.String> getCourseStudents(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseService.getCourseStudents(courseId);
	}

	public java.util.List<java.lang.String> getCourseTeachers(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseService.getCourseTeachers(courseId);
	}

	public java.util.List<java.lang.String> getCourseEditors(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseService.getCourseEditors(courseId);
	}

	public void addStudentToCourse(long courseId, java.lang.String login)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseService.addStudentToCourse(courseId, login);
	}

	public void addStudentToCourseWithDates(long courseId,
		java.lang.String login, java.util.Date allowStartDate,
		java.util.Date allowFinishDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseService.addStudentToCourseWithDates(courseId, login,
			allowStartDate, allowFinishDate);
	}

	public void editUserInscriptionDates(long courseId, java.lang.String login,
		java.util.Date allowStartDate, java.util.Date allowFinishDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseService.editUserInscriptionDates(courseId, login,
			allowStartDate, allowFinishDate);
	}

	public void editUserInscriptionDates(long courseId, long userId,
		java.util.Date allowStartDate, java.util.Date allowFinishDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseService.editUserInscriptionDates(courseId, userId,
			allowStartDate, allowFinishDate);
	}

	public void addTeacherToCourse(long courseId, java.lang.String login)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseService.addTeacherToCourse(courseId, login);
	}

	public void addEditorToCourse(long courseId, java.lang.String login)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseService.addEditorToCourse(courseId, login);
	}

	public void removeStudentFromCourse(long courseId, java.lang.String login)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.security.auth.PrincipalException {
		_courseService.removeStudentFromCourse(courseId, login);
	}

	public void removeTeacherFromCourse(long courseId, java.lang.String login)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseService.removeTeacherFromCourse(courseId, login);
	}

	public void removeEditorFromCourse(long courseId, java.lang.String login)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseService.removeEditorFromCourse(courseId, login);
	}

	public long getUserResult(long courseId, java.lang.String login)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseService.getUserResult(courseId, login);
	}

	public java.util.List<com.liferay.lms.model.Course> myCourses()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseService.myCourses();
	}

	public void addUser(java.lang.String login, java.lang.String firstName,
		java.lang.String lastName, java.lang.String email)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseService.addUser(login, firstName, lastName, email);
	}

	public void updateUser(java.lang.String login, java.lang.String firstName,
		java.lang.String lastName, java.lang.String email)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseService.updateUser(login, firstName, lastName, email);
	}

	public boolean existsCourseName(java.lang.Long companyId,
		java.lang.Long groupId, java.lang.String groupName) {
		return _courseService.existsCourseName(companyId, groupId, groupName);
	}

	public java.lang.String getLogoUrl(java.lang.Long courseId) {
		return _courseService.getLogoUrl(courseId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public CourseService getWrappedCourseService() {
		return _courseService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedCourseService(CourseService courseService) {
		_courseService = courseService;
	}

	public CourseService getWrappedService() {
		return _courseService;
	}

	public void setWrappedService(CourseService courseService) {
		_courseService = courseService;
	}

	private CourseService _courseService;
}