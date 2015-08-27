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

	public java.util.List<com.liferay.lms.model.Course> getPublicCoursesByCompanyId(
		java.lang.Long companyId) {
		return _courseLocalService.getPublicCoursesByCompanyId(companyId);
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

	public java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		com.liferay.lms.model.Course course) {
		return _courseLocalService.getStudentsFromCourse(course);
	}

	public java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		long companyId, long courseGropupCreatedId) {
		return _courseLocalService.getStudentsFromCourse(companyId,
			courseGropupCreatedId);
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