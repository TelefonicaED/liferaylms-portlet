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
 * This class is a wrapper for {@link CourseLocalService}.
 * </p>
 *
 * @author    TLS
 * @see       CourseLocalService
 * @generated
 */
public class CourseLocalServiceWrapper implements CourseLocalService,
	ServiceWrapper<CourseLocalService> {
	public CourseLocalServiceWrapper(CourseLocalService courseLocalService) {
		_courseLocalService = courseLocalService;
	}

	/**
	* Adds the course to the database. Also notifies the appropriate model listeners.
	*
	* @param course the course
	* @return the course that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course addCourse(
		com.liferay.lms.model.Course course)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.addCourse(course);
	}

	/**
	* Creates a new course with the primary key. Does not add the course to the database.
	*
	* @param courseId the primary key for the new course
	* @return the new course
	*/
	public com.liferay.lms.model.Course createCourse(long courseId) {
		return _courseLocalService.createCourse(courseId);
	}

	/**
	* Deletes the course with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param courseId the primary key of the course
	* @return the course that was removed
	* @throws PortalException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course deleteCourse(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.deleteCourse(courseId);
	}

	/**
	* Deletes the course from the database. Also notifies the appropriate model listeners.
	*
	* @param course the course
	* @return the course that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course deleteCourse(
		com.liferay.lms.model.Course course)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.deleteCourse(course);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _courseLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.lms.model.Course fetchCourse(long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.fetchCourse(courseId);
	}

	/**
	* Returns the course with the primary key.
	*
	* @param courseId the primary key of the course
	* @return the course
	* @throws PortalException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course getCourse(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getCourse(courseId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the course with the UUID in the group.
	*
	* @param uuid the UUID of course
	* @param groupId the group id of the course
	* @return the course
	* @throws PortalException if a course with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course getCourseByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getCourseByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the courses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of courses
	* @param end the upper bound of the range of courses (not inclusive)
	* @return the range of courses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.Course> getCourses(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getCourses(start, end);
	}

	/**
	* Returns the number of courses.
	*
	* @return the number of courses
	* @throws SystemException if a system exception occurred
	*/
	public int getCoursesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getCoursesCount();
	}

	/**
	* Updates the course in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param course the course
	* @return the course that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course updateCourse(
		com.liferay.lms.model.Course course)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.updateCourse(course);
	}

	/**
	* Updates the course in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param course the course
	* @param merge whether to merge the course with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the course that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Course updateCourse(
		com.liferay.lms.model.Course course, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.updateCourse(course, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _courseLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_courseLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _courseLocalService.invokeMethod(name, parameterTypes, arguments);
	}

	public java.util.List<com.liferay.lms.model.Course> getCoursesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getCoursesOfGroup(groupId);
	}

	public java.util.List<com.liferay.lms.model.Course> getOpenCoursesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getOpenCoursesOfGroup(groupId);
	}

	public java.util.List<com.liferay.lms.model.Course> getCourses(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getCourses(companyId);
	}

	public long countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.countByGroupId(groupId);
	}

	public com.liferay.lms.model.Course fetchByGroupCreatedId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.fetchByGroupCreatedId(groupId);
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
		return _courseLocalService.addCourse(title, description, summary,
			friendlyURL, locale, createDate, startDate, endDate,
			layoutSetPrototypeId, typesite, serviceContext, calificationType,
			maxUsers, isFromClone);
	}

	public java.util.List<com.liferay.lms.model.Course> getUserCourses(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getUserCourses(userId);
	}

	public java.util.List<com.liferay.lms.model.Course> getOpenedUserCourses(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getOpenedUserCourses(userId);
	}

	public java.util.List<com.liferay.lms.model.Course> getOpenedUserCourses(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getOpenedUserCourses(userId, start, end);
	}

	public java.util.List<com.liferay.lms.model.Course> getPublicCoursesByCompanyId(
		java.lang.Long companyId) {
		return _courseLocalService.getPublicCoursesByCompanyId(companyId);
	}

	public java.util.List<com.liferay.lms.model.Course> getPublicCoursesByCompanyId(
		java.lang.Long companyId, int start, int end) {
		return _courseLocalService.getPublicCoursesByCompanyId(companyId,
			start, end);
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
		return _courseLocalService.addCourse(title, description, summary,
			friendlyURL, locale, createDate, startDate, endDate,
			layoutSetPrototypeId, typesite, CourseEvalId, calificationType,
			maxUsers, serviceContext, isfromClone);
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
		return _courseLocalService.addCourse(title, description, summary,
			friendlyURL, locale, createDate, startDate, endDate,
			executionStartDate, executionEndDate, layoutSetPrototypeId,
			typesite, CourseEvalId, calificationType, maxUsers, serviceContext,
			isfromClone);
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
		return _courseLocalService.addCourse(titleMap, description, summary,
			friendlyURL, locale, createDate, startDate, endDate,
			executionStartDate, executionEndDate, layoutSetPrototypeId,
			typesite, CourseEvalId, calificationType, maxUsers, serviceContext,
			isfromClone);
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
		return _courseLocalService.addCourse(title, description, summary,
			friendlyURL, locale, createDate, startDate, endDate,
			serviceContext, calificationType);
	}

	public com.liferay.lms.model.Course addCourse(java.lang.String title,
		java.lang.String description, java.lang.String friendlyURL,
		java.util.Locale locale, java.util.Date createDate,
		java.util.Date startDate, java.util.Date endDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		long calificationType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.addCourse(title, description, friendlyURL,
			locale, createDate, startDate, endDate, serviceContext,
			calificationType);
	}

	public void setVisible(long courseId, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseLocalService.setVisible(courseId, visible);
	}

	public com.liferay.lms.model.Course modCourse(
		com.liferay.lms.model.Course course, java.lang.String summary,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.modCourse(course, summary, serviceContext);
	}

	public com.liferay.lms.model.Course modCourse(
		com.liferay.lms.model.Course course,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.modCourse(course, serviceContext);
	}

	public com.liferay.lms.model.Course modCourse(
		com.liferay.lms.model.Course course, java.lang.String summary,
		com.liferay.portal.service.ServiceContext serviceContext,
		boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.modCourse(course, summary, serviceContext,
			visible);
	}

	public com.liferay.lms.model.Course modCourse(
		com.liferay.lms.model.Course course, java.lang.String summary,
		com.liferay.portal.service.ServiceContext serviceContext,
		boolean visible, boolean allowDuplicateName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.modCourse(course, summary, serviceContext,
			visible, allowDuplicateName);
	}

	public com.liferay.lms.model.Course closeCourse(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.closeCourse(courseId);
	}

	public com.liferay.lms.model.Course openCourse(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.openCourse(courseId);
	}

	public boolean existsCourseName(long companyId, long classNameId,
		long liveGroupId, java.lang.String name) {
		return _courseLocalService.existsCourseName(companyId, classNameId,
			liveGroupId, name);
	}

	public com.liferay.lms.model.Course getCourseByGroupCreatedId(
		long groupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getCourseByGroupCreatedId(groupCreatedId);
	}

	public boolean existsCourseName(java.lang.Long companyId,
		java.lang.Long courseId, java.lang.String groupName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.existsCourseName(companyId, courseId,
			groupName);
	}

	public java.util.List<com.liferay.lms.model.Course> findByCompanyId(
		java.lang.Long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.findByCompanyId(companyId);
	}

	public int getStudentsFromCourseCount(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getStudentsFromCourseCount(courseId);
	}

	public int getStudentsFromCourseCount(long courseId, long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getStudentsFromCourseCount(courseId, teamId);
	}

	public int getStudentsFromCourseCount(long courseId, long teamId,
		java.lang.String firstName, java.lang.String lastName,
		java.lang.String screeName, java.lang.String emailAddress,
		boolean andComparator)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getStudentsFromCourseCount(courseId, teamId,
			firstName, lastName, screeName, emailAddress, andComparator);
	}

	public java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		com.liferay.lms.model.Course course) {
		return _courseLocalService.getStudentsFromCourse(course);
	}

	public java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		long companyId, long courseGroupCreatedId) {
		return _courseLocalService.getStudentsFromCourse(companyId,
			courseGroupCreatedId);
	}

	public java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		long companyId, long courseGroupCreatedId, long teamId) {
		return _courseLocalService.getStudentsFromCourse(companyId,
			courseGroupCreatedId, teamId);
	}

	public java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		long companyId, long courseGroupCreatedId, int start, int end,
		long teamId, java.lang.String firstName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		boolean andOperator) {
		return _courseLocalService.getStudentsFromCourse(companyId,
			courseGroupCreatedId, start, end, teamId, firstName, lastName,
			screenName, emailAddress, andOperator);
	}

	public java.util.List<com.liferay.portal.model.User> getTeachersFromCourse(
		long courseId) {
		return _courseLocalService.getTeachersFromCourse(courseId);
	}

	public java.util.List<com.liferay.portal.model.User> getTeachersFromCourse(
		com.liferay.lms.model.Course course, long teacherRoleId) {
		return _courseLocalService.getTeachersFromCourse(course, teacherRoleId);
	}

	public long[] getTeachersAndEditorsIdsFromCourse(
		com.liferay.lms.model.Course course) {
		return _courseLocalService.getTeachersAndEditorsIdsFromCourse(course);
	}

	/**
	* Devuelve los profesores de un curso teniendo en cuenta si el usuario pertenece a algÃƒÂºn equipo, si pertenece
	* a algÃƒÂºn equipo, devuelve los profesores de ese equipo. Si no los equipos a los que pertenece no tienen ningÃƒÂºn
	* profesor o no pertenece a ningÃƒÂºn equipo devuelve todos los profesores del curso
	*
	* @param course
	* @param teacherRoleId
	* @param userId
	* @return List<User> Lista de usuarios profesores
	*/
	public java.util.List<com.liferay.portal.model.User> getTeachersFromCourseTeams(
		com.liferay.lms.model.Course course, long teacherRoleId, long userId) {
		return _courseLocalService.getTeachersFromCourseTeams(course,
			teacherRoleId, userId);
	}

	public java.util.List<com.liferay.lms.model.Course> getByTitleStatusCategoriesTags(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language,
		boolean isAdmin, boolean andOperator, int start, int end) {
		return _courseLocalService.getByTitleStatusCategoriesTags(freeText,
			status, categories, tags, companyId, groupId, userId, language,
			isAdmin, andOperator, start, end);
	}

	public int countByTitleStatusCategoriesTags(java.lang.String freeText,
		int status, long[] categories, long[] tags, long companyId,
		long groupId, long userId, java.lang.String language, boolean isAdmin,
		boolean andOperator) {
		return _courseLocalService.countByTitleStatusCategoriesTags(freeText,
			status, categories, tags, companyId, groupId, userId, language,
			isAdmin, andOperator);
	}

	public java.util.List<com.liferay.lms.model.Course> getParentCoursesByTitleStatusCategoriesTags(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language,
		boolean isAdmin, boolean andOperator, int start, int end) {
		return _courseLocalService.getParentCoursesByTitleStatusCategoriesTags(freeText,
			status, categories, tags, companyId, groupId, userId, language,
			isAdmin, andOperator, start, end);
	}

	public int countParentCoursesByTitleStatusCategoriesTags(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language,
		boolean isAdmin, boolean andOperator) {
		return _courseLocalService.countParentCoursesByTitleStatusCategoriesTags(freeText,
			status, categories, tags, companyId, groupId, userId, language,
			isAdmin, andOperator);
	}

	public java.util.List<com.liferay.lms.model.Course> getParentCoursesByTitleStatusCategoriesTagsTemplates(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		java.lang.String templates, long companyId, long groupId, long userId,
		java.lang.String language, boolean isAdmin, boolean andOperator,
		int start, int end) {
		return _courseLocalService.getParentCoursesByTitleStatusCategoriesTagsTemplates(freeText,
			status, categories, tags, templates, companyId, groupId, userId,
			language, isAdmin, andOperator, start, end);
	}

	public int countParentCoursesByTitleStatusCategoriesTagsTemplates(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		java.lang.String templates, long companyId, long groupId, long userId,
		java.lang.String language, boolean isAdmin, boolean andOperator) {
		return _courseLocalService.countParentCoursesByTitleStatusCategoriesTagsTemplates(freeText,
			status, categories, tags, templates, companyId, groupId, userId,
			language, isAdmin, andOperator);
	}

	public java.util.List<com.liferay.lms.model.Course> getChildCoursesByTitle(
		java.lang.String freeText, long parentCourseId, int status,
		long companyId, long groupId, long userId, java.lang.String language,
		boolean isAdmin, boolean andOperator, int start, int end) {
		return _courseLocalService.getChildCoursesByTitle(freeText,
			parentCourseId, status, companyId, groupId, userId, language,
			isAdmin, andOperator, start, end);
	}

	public int countChildCoursesByTitle(java.lang.String freeText,
		long parentCourseId, int status, long companyId, long groupId,
		long userId, java.lang.String language, boolean isAdmin,
		boolean andOperator) {
		return _courseLocalService.countChildCoursesByTitle(freeText,
			parentCourseId, status, companyId, groupId, userId, language,
			isAdmin, andOperator);
	}

	public java.util.List<com.liferay.portal.model.User> getStudents(
		long courseId, long companyId, java.lang.String screenName,
		java.lang.String firstName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator comparator) {
		return _courseLocalService.getStudents(courseId, companyId, screenName,
			firstName, lastName, emailAddress, andOperator, start, end,
			comparator);
	}

	public int countStudents(long courseId, long companyId,
		java.lang.String screenName, java.lang.String firstName,
		java.lang.String lastName, java.lang.String emailAddress,
		boolean andOperator) {
		return _courseLocalService.countStudents(courseId, companyId,
			screenName, firstName, lastName, emailAddress, andOperator);
	}

	public int countStudentsStatus(long courseId, long companyId,
		java.lang.String screenName, java.lang.String firstName,
		java.lang.String lastName, java.lang.String emailAddress, int status,
		boolean andOperator) {
		return _courseLocalService.countStudentsStatus(courseId, companyId,
			screenName, firstName, lastName, emailAddress, status, andOperator);
	}

	public java.util.List<com.liferay.portal.model.User> getStudents(
		long courseId, long companyId, java.lang.String screenName,
		java.lang.String firstName, java.lang.String lastName,
		java.lang.String emailAddress, int status, long teamId,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator comparator) {
		return _courseLocalService.getStudents(courseId, companyId, screenName,
			firstName, lastName, emailAddress, status, teamId, andOperator,
			start, end, comparator);
	}

	public int countStudents(long courseId, long companyId,
		java.lang.String screenName, java.lang.String firstName,
		java.lang.String lastName, java.lang.String emailAddress, int status,
		long teamId, boolean andOperator) {
		return _courseLocalService.countStudents(courseId, companyId,
			screenName, firstName, lastName, emailAddress, status, teamId,
			andOperator);
	}

	public java.util.List<com.liferay.lms.model.Course> getCoursesCatalogByTitleCategoriesTags(
		java.lang.String freeText, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language,
		int start, int end) {
		return _courseLocalService.getCoursesCatalogByTitleCategoriesTags(freeText,
			categories, tags, companyId, groupId, userId, language, start, end);
	}

	public int countCoursesCatalogByTitleCategoriesTags(
		java.lang.String freeText, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language) {
		return _courseLocalService.countCoursesCatalogByTitleCategoriesTags(freeText,
			categories, tags, companyId, groupId, userId, language);
	}

	public java.util.List<java.lang.Long> getCatalogCoursesAssetTags(
		java.lang.String freeText, long[] categories, long companyId,
		long groupId, long userId, java.lang.String language) {
		return _courseLocalService.getCatalogCoursesAssetTags(freeText,
			categories, companyId, groupId, userId, language);
	}

	public java.util.HashMap<java.lang.Long, java.lang.Long> countCategoryCourses(
		java.lang.String freeText, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language) {
		return _courseLocalService.countCategoryCourses(freeText, categories,
			tags, companyId, groupId, userId, language);
	}

	public java.util.HashMap<java.lang.Long, java.lang.Long> countTagCourses(
		java.lang.String freeText, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language) {
		return _courseLocalService.countTagCourses(freeText, categories, tags,
			companyId, groupId, userId, language);
	}

	public java.util.List<com.liferay.lms.views.CourseResultView> getMyCourses(
		long groupId, long userId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		java.lang.String orderByColumn, java.lang.String orderByType,
		int start, int end) {
		return _courseLocalService.getMyCourses(groupId, userId, themeDisplay,
			orderByColumn, orderByType, start, end);
	}

	public int countMyCourses(long groupId, long userId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _courseLocalService.countMyCourses(groupId, userId, themeDisplay);
	}

	public boolean hasUserTries(long courseId, long userId) {
		return _courseLocalService.hasUserTries(courseId, userId);
	}

	public java.util.List<com.liferay.lms.model.Course> getPublicCoursesByCompanyId(
		java.lang.Long companyId, int limit) {
		return _courseLocalService.getPublicCoursesByCompanyId(companyId, limit);
	}

	public java.util.List<com.liferay.lms.model.Course> getChildCourses(
		long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getChildCourses(courseId);
	}

	public java.util.List<com.liferay.lms.model.Course> getChildCourses(
		long courseId, int start, int end) {
		return _courseLocalService.getChildCourses(courseId, start, end);
	}

	public java.util.List<com.liferay.lms.model.Course> getOpenOrRestrictedChildCourses(
		long courseId) {
		return _courseLocalService.getOpenOrRestrictedChildCourses(courseId);
	}

	public int countChildCourses(long courseId) {
		return _courseLocalService.countChildCourses(courseId);
	}

	public int countOpenOrRestrictedChildCourses(long courseId) {
		return _courseLocalService.countOpenOrRestrictedChildCourses(courseId);
	}

	public java.util.List<com.liferay.lms.model.Course> getCoursesParents(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.getCoursesParents(groupId);
	}

	public void addStudentToCourseWithDates(long courseId, long userId,
		java.util.Date allowStartDate, java.util.Date allowFinishDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseLocalService.addStudentToCourseWithDates(courseId, userId,
			allowStartDate, allowFinishDate);
	}

	public void editUserInscriptionDates(long courseId, long userId,
		java.util.Date allowStartDate, java.util.Date allowFinishDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseLocalService.editUserInscriptionDates(courseId, userId,
			allowStartDate, allowFinishDate);
	}

	/**
	* Se van a realizar las siguientes comprobaciones:
	* - Curso cerrado
	* - Que pertenezcas a la comunidad
	* - Que tengas permiso para acceder al curso
	* - Comprobar que tenga una convocatoria en fecha
	* - Que el usuario tenga fechas propias para realizarlo y estÃƒÂ©n en fecha
	*
	* @param courseId Id el curso
	* @param user Usuario
	* @return true si el curso estÃƒÂ¡ bloqueado, false en caso contrario
	*/
	public boolean isLocked(com.liferay.lms.model.Course course,
		com.liferay.portal.model.User user) {
		return _courseLocalService.isLocked(course, user);
	}

	/**
	* Comprueba si un usuario puede acceder a los cursos/modulos/actividades bloqueadas
	*
	* @param groupCreatedId id del grupo creado para el curso
	* @param user usuario
	* @return true en caso de que pueda acceder a bloqueados
	*/
	public boolean canAccessLock(long groupCreatedId,
		com.liferay.portal.model.User user) {
		return _courseLocalService.canAccessLock(groupCreatedId, user);
	}

	/**
	* Returns the last module date in course, because the course end date is for enrollments.
	*
	* @param courseId Course Identifier
	* @return Course last module date.
	*/
	public java.util.Date getLastModuleDateInCourse(long courseId) {
		return _courseLocalService.getLastModuleDateInCourse(courseId);
	}

	/**
	* Returns the first module date in course, because the course end date is for enrollments.
	*
	* @param courseId Course Identifier
	* @return Course last module date.
	*/
	public java.util.Date getFirstModuleDateInCourse(long courseId) {
		return _courseLocalService.getFirstModuleDateInCourse(courseId);
	}

	public java.lang.String getImageURL(com.liferay.lms.model.Course course,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _courseLocalService.getImageURL(course, themeDisplay);
	}

	/**
	* Service that validates the course inscription as it is validated in web.
	*
	* @param courseId
	* @param userId
	* @return ok or error and the error description.
	* @throws PortalException
	* @throws SystemException
	*/
	public java.lang.String addStudentToCourseByUserId(long courseId,
		long userId, long teamId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.addStudentToCourseByUserId(courseId, userId,
			teamId, serviceContext);
	}

	/**
	* @param groupId
	* @param userId
	* @param teamId
	* @return
	* @throws PortalException
	* @throws SystemException
	*/
	public boolean validateAddUserToCourse(long groupId, long userId,
		long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseLocalService.validateAddUserToCourse(groupId, userId,
			teamId);
	}

	public java.util.List<com.liferay.portal.model.Group> getDistinctCourseGroups(
		long companyId) {
		return _courseLocalService.getDistinctCourseGroups(companyId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public CourseLocalService getWrappedCourseLocalService() {
		return _courseLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedCourseLocalService(
		CourseLocalService courseLocalService) {
		_courseLocalService = courseLocalService;
	}

	public CourseLocalService getWrappedService() {
		return _courseLocalService;
	}

	public void setWrappedService(CourseLocalService courseLocalService) {
		_courseLocalService = courseLocalService;
	}

	private CourseLocalService _courseLocalService;
}