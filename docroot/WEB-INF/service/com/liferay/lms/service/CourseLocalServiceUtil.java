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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * The utility for the course local service. This utility wraps {@link com.liferay.lms.service.impl.CourseLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see CourseLocalService
 * @see com.liferay.lms.service.base.CourseLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.CourseLocalServiceImpl
 * @generated
 */
public class CourseLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.CourseLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the course to the database. Also notifies the appropriate model listeners.
	*
	* @param course the course
	* @return the course that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course addCourse(
		com.liferay.lms.model.Course course)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addCourse(course);
	}

	/**
	* Creates a new course with the primary key. Does not add the course to the database.
	*
	* @param courseId the primary key for the new course
	* @return the new course
	*/
	public static com.liferay.lms.model.Course createCourse(long courseId) {
		return getService().createCourse(courseId);
	}

	/**
	* Deletes the course with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param courseId the primary key of the course
	* @return the course that was removed
	* @throws PortalException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course deleteCourse(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteCourse(courseId);
	}

	/**
	* Deletes the course from the database. Also notifies the appropriate model listeners.
	*
	* @param course the course
	* @return the course that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course deleteCourse(
		com.liferay.lms.model.Course course)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteCourse(course);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.lms.model.Course fetchCourse(long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchCourse(courseId);
	}

	/**
	* Returns the course with the primary key.
	*
	* @param courseId the primary key of the course
	* @return the course
	* @throws PortalException if a course with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course getCourse(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCourse(courseId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static com.liferay.lms.model.Course getCourseByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCourseByUuidAndGroupId(uuid, groupId);
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
	public static java.util.List<com.liferay.lms.model.Course> getCourses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCourses(start, end);
	}

	/**
	* Returns the number of courses.
	*
	* @return the number of courses
	* @throws SystemException if a system exception occurred
	*/
	public static int getCoursesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCoursesCount();
	}

	/**
	* Updates the course in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param course the course
	* @return the course that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course updateCourse(
		com.liferay.lms.model.Course course)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCourse(course);
	}

	/**
	* Updates the course in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param course the course
	* @param merge whether to merge the course with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the course that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Course updateCourse(
		com.liferay.lms.model.Course course, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCourse(course, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static java.util.List<com.liferay.lms.model.Course> getCoursesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCoursesOfGroup(groupId);
	}

	public static java.util.List<com.liferay.lms.model.Course> getOpenCoursesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getOpenCoursesOfGroup(groupId);
	}

	public static java.util.List<com.liferay.lms.model.Course> getCourses(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCourses(companyId);
	}

	public static long countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countByGroupId(groupId);
	}

	public static com.liferay.lms.model.Course fetchByGroupCreatedId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchByGroupCreatedId(groupId);
	}

	public static com.liferay.lms.model.Course addCourse(
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String friendlyURL,
		java.util.Locale locale, java.util.Date createDate,
		java.util.Date startDate, java.util.Date endDate,
		long layoutSetPrototypeId, int typesite,
		com.liferay.portal.service.ServiceContext serviceContext,
		long calificationType, int maxUsers, boolean isFromClone)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addCourse(title, description, summary, friendlyURL, locale,
			createDate, startDate, endDate, layoutSetPrototypeId, typesite,
			serviceContext, calificationType, maxUsers, isFromClone);
	}

	public static java.util.List<com.liferay.lms.model.Course> getUserCourses(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserCourses(userId);
	}

	public static java.util.List<com.liferay.lms.model.Course> getOpenedUserCourses(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getOpenedUserCourses(userId);
	}

	public static java.util.List<com.liferay.lms.model.Course> getOpenedUserCourses(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getOpenedUserCourses(userId, start, end);
	}

	public static java.util.List<com.liferay.lms.model.Course> getPublicCoursesByCompanyId(
		java.lang.Long companyId) {
		return getService().getPublicCoursesByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.lms.model.Course> getPublicCoursesByCompanyId(
		java.lang.Long companyId, int start, int end) {
		return getService().getPublicCoursesByCompanyId(companyId, start, end);
	}

	public static com.liferay.lms.model.Course addCourse(
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String friendlyURL,
		java.util.Locale locale, java.util.Date createDate,
		java.util.Date startDate, java.util.Date endDate,
		long layoutSetPrototypeId, int typesite, long CourseEvalId,
		long calificationType, int maxUsers,
		com.liferay.portal.service.ServiceContext serviceContext,
		boolean isfromClone)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addCourse(title, description, summary, friendlyURL, locale,
			createDate, startDate, endDate, layoutSetPrototypeId, typesite,
			CourseEvalId, calificationType, maxUsers, serviceContext,
			isfromClone);
	}

	public static com.liferay.lms.model.Course addCourse(
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String friendlyURL,
		java.util.Locale locale, java.util.Date createDate,
		java.util.Date startDate, java.util.Date endDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		long calificationType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addCourse(title, description, summary, friendlyURL, locale,
			createDate, startDate, endDate, serviceContext, calificationType);
	}

	public static com.liferay.lms.model.Course addCourse(
		java.lang.String title, java.lang.String description,
		java.lang.String friendlyURL, java.util.Locale locale,
		java.util.Date createDate, java.util.Date startDate,
		java.util.Date endDate,
		com.liferay.portal.service.ServiceContext serviceContext,
		long calificationType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addCourse(title, description, friendlyURL, locale,
			createDate, startDate, endDate, serviceContext, calificationType);
	}

	public static void setVisible(long courseId, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setVisible(courseId, visible);
	}

	public static com.liferay.lms.model.Course modCourse(
		com.liferay.lms.model.Course course, java.lang.String summary,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().modCourse(course, summary, serviceContext);
	}

	public static com.liferay.lms.model.Course modCourse(
		com.liferay.lms.model.Course course,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().modCourse(course, serviceContext);
	}

	public static com.liferay.lms.model.Course modCourse(
		com.liferay.lms.model.Course course, java.lang.String summary,
		com.liferay.portal.service.ServiceContext serviceContext,
		boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().modCourse(course, summary, serviceContext, visible);
	}

	public static com.liferay.lms.model.Course closeCourse(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().closeCourse(courseId);
	}

	public static com.liferay.lms.model.Course openCourse(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().openCourse(courseId);
	}

	public static boolean existsCourseName(long companyId, long classNameId,
		long liveGroupId, java.lang.String name) {
		return getService()
				   .existsCourseName(companyId, classNameId, liveGroupId, name);
	}

	public static com.liferay.lms.model.Course getCourseByGroupCreatedId(
		long groupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCourseByGroupCreatedId(groupCreatedId);
	}

	public static boolean existsCourseName(java.lang.Long companyId,
		java.lang.Long courseId, java.lang.String groupName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().existsCourseName(companyId, courseId, groupName);
	}

	public static java.util.List<com.liferay.lms.model.Course> findByCompanyId(
		java.lang.Long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findByCompanyId(companyId);
	}

	public static int getStudentsFromCourseCount(long courseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getStudentsFromCourseCount(courseId);
	}

	public static int getStudentsFromCourseCount(long courseId, long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getStudentsFromCourseCount(courseId, teamId);
	}

	public static int getStudentsFromCourseCount(long courseId, long teamId,
		java.lang.String firstName, java.lang.String lastName,
		java.lang.String screeName, java.lang.String emailAddress,
		boolean andComparator)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getStudentsFromCourseCount(courseId, teamId, firstName,
			lastName, screeName, emailAddress, andComparator);
	}

	public static java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		com.liferay.lms.model.Course course) {
		return getService().getStudentsFromCourse(course);
	}

	public static java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		long companyId, long courseGroupCreatedId) {
		return getService()
				   .getStudentsFromCourse(companyId, courseGroupCreatedId);
	}

	public static java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		long companyId, long courseGroupCreatedId, long teamId) {
		return getService()
				   .getStudentsFromCourse(companyId, courseGroupCreatedId,
			teamId);
	}

	public static java.util.List<com.liferay.portal.model.User> getStudentsFromCourse(
		long companyId, long courseGroupCreatedId, int start, int end,
		long teamId, java.lang.String firstName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		boolean andOperator) {
		return getService()
				   .getStudentsFromCourse(companyId, courseGroupCreatedId,
			start, end, teamId, firstName, lastName, screenName, emailAddress,
			andOperator);
	}

	public static java.util.List<com.liferay.portal.model.User> getTeachersFromCourse(
		long courseId) {
		return getService().getTeachersFromCourse(courseId);
	}

	public static long[] getTeachersAndEditorsIdsFromCourse(
		com.liferay.lms.model.Course course) {
		return getService().getTeachersAndEditorsIdsFromCourse(course);
	}

	public static java.util.List<com.liferay.lms.model.Course> getByTitleStatusCategoriesTags(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language,
		boolean isAdmin, boolean andOperator, int start, int end) {
		return getService()
				   .getByTitleStatusCategoriesTags(freeText, status,
			categories, tags, companyId, groupId, userId, language, isAdmin,
			andOperator, start, end);
	}

	public static int countByTitleStatusCategoriesTags(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language,
		boolean isAdmin, boolean andOperator) {
		return getService()
				   .countByTitleStatusCategoriesTags(freeText, status,
			categories, tags, companyId, groupId, userId, language, isAdmin,
			andOperator);
	}

	public static java.util.List<com.liferay.lms.model.Course> getParentCoursesByTitleStatusCategoriesTags(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language,
		boolean isAdmin, boolean andOperator, int start, int end) {
		return getService()
				   .getParentCoursesByTitleStatusCategoriesTags(freeText,
			status, categories, tags, companyId, groupId, userId, language,
			isAdmin, andOperator, start, end);
	}

	public static int countParentCoursesByTitleStatusCategoriesTags(
		java.lang.String freeText, int status, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language,
		boolean isAdmin, boolean andOperator) {
		return getService()
				   .countParentCoursesByTitleStatusCategoriesTags(freeText,
			status, categories, tags, companyId, groupId, userId, language,
			isAdmin, andOperator);
	}

	public static java.util.List<com.liferay.portal.model.User> getStudents(
		long courseId, long companyId, java.lang.String screenName,
		java.lang.String firstName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator comparator) {
		return getService()
				   .getStudents(courseId, companyId, screenName, firstName,
			lastName, emailAddress, andOperator, start, end, comparator);
	}

	public static int countStudents(long courseId, long companyId,
		java.lang.String screenName, java.lang.String firstName,
		java.lang.String lastName, java.lang.String emailAddress,
		boolean andOperator) {
		return getService()
				   .countStudents(courseId, companyId, screenName, firstName,
			lastName, emailAddress, andOperator);
	}

	public static int countStudentsStatus(long courseId, long companyId,
		java.lang.String screenName, java.lang.String firstName,
		java.lang.String lastName, java.lang.String emailAddress, int status,
		boolean andOperator) {
		return getService()
				   .countStudentsStatus(courseId, companyId, screenName,
			firstName, lastName, emailAddress, status, andOperator);
	}

	public static java.util.List<com.liferay.lms.model.Course> getCoursesCatalogByTitleCategoriesTags(
		java.lang.String freeText, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language,
		int start, int end) {
		return getService()
				   .getCoursesCatalogByTitleCategoriesTags(freeText,
			categories, tags, companyId, groupId, userId, language, start, end);
	}

	public static int countCoursesCatalogByTitleCategoriesTags(
		java.lang.String freeText, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language) {
		return getService()
				   .countCoursesCatalogByTitleCategoriesTags(freeText,
			categories, tags, companyId, groupId, userId, language);
	}

	public static java.util.List<java.lang.Long> getCatalogCoursesAssetTags(
		java.lang.String freeText, long[] categories, long companyId,
		long groupId, long userId, java.lang.String language) {
		return getService()
				   .getCatalogCoursesAssetTags(freeText, categories, companyId,
			groupId, userId, language);
	}

	public static java.util.HashMap<java.lang.Long, java.lang.Long> countCategoryCourses(
		java.lang.String freeText, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language) {
		return getService()
				   .countCategoryCourses(freeText, categories, tags, companyId,
			groupId, userId, language);
	}

	public static java.util.HashMap<java.lang.Long, java.lang.Long> countTagCourses(
		java.lang.String freeText, long[] categories, long[] tags,
		long companyId, long groupId, long userId, java.lang.String language) {
		return getService()
				   .countTagCourses(freeText, categories, tags, companyId,
			groupId, userId, language);
	}

	public static java.util.List<com.liferay.lms.views.CourseResultView> getMyCourses(
		long groupId, long userId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		java.lang.String orderByColumn, java.lang.String orderByType,
		int start, int end) {
		return getService()
				   .getMyCourses(groupId, userId, themeDisplay, orderByColumn,
			orderByType, start, end);
	}

	public static int countMyCourses(long groupId, long userId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return getService().countMyCourses(groupId, userId, themeDisplay);
	}

	public static boolean hasUserTries(long courseId, long userId) {
		return getService().hasUserTries(courseId, userId);
	}

	public static java.util.List<com.liferay.lms.model.Course> getPublicCoursesByCompanyId(
		java.lang.Long companyId, int limit) {
		return getService().getPublicCoursesByCompanyId(companyId, limit);
	}

	public static java.util.List<com.liferay.lms.model.Course> getChildCourses(
		long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getChildCourses(courseId);
	}

	public static java.util.List<com.liferay.lms.model.Course> getChildCourses(
		long courseId, int start, int end) {
		return getService().getChildCourses(courseId, start, end);
	}

	public static int countChildCourses(long courseId) {
		return getService().countChildCourses(courseId);
	}

	public static java.util.List<com.liferay.lms.model.Course> getCoursesParents(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCoursesParents(groupId);
	}

	public static void addStudentToCourseWithDates(long courseId, long userId,
		java.util.Date allowStartDate, java.util.Date allowFinishDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addStudentToCourseWithDates(courseId, userId, allowStartDate,
			allowFinishDate);
	}

	public static void editUserInscriptionDates(long courseId, long userId,
		java.util.Date allowStartDate, java.util.Date allowFinishDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.editUserInscriptionDates(courseId, userId, allowStartDate,
			allowFinishDate);
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
	public static boolean isLocked(com.liferay.lms.model.Course course,
		com.liferay.portal.model.User user) {
		return getService().isLocked(course, user);
	}

	/**
	* Comprueba si un usuario puede acceder a los cursos/modulos/actividades bloqueadas
	*
	* @param groupCreatedId id del grupo creado para el curso
	* @param user usuario
	* @return true en caso de que pueda acceder a bloqueados
	*/
	public static boolean canAccessLock(long groupCreatedId,
		com.liferay.portal.model.User user) {
		return getService().canAccessLock(groupCreatedId, user);
	}

	/**
	* Returns the last module date in course, because the course end date is for enrollments.
	*
	* @param courseId Course Identifier
	* @return Course last module date.
	*/
	public static java.util.Date getLastModuleDateInCourse(long courseId) {
		return getService().getLastModuleDateInCourse(courseId);
	}

	/**
	* Returns the first module date in course, because the course end date is for enrollments.
	*
	* @param courseId Course Identifier
	* @return Course last module date.
	*/
	public static java.util.Date getFirstModuleDateInCourse(long courseId) {
		return getService().getFirstModuleDateInCourse(courseId);
	}

	public static java.lang.String getImageURL(
		com.liferay.lms.model.Course course,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return getService().getImageURL(course, themeDisplay);
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
	public static java.lang.String addStudentToCourseByUserId(long courseId,
		long userId, long teamId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addStudentToCourseByUserId(courseId, userId, teamId,
			serviceContext);
	}

	/**
	* @param groupId
	* @param userId
	* @param teamId
	* @return
	* @throws PortalException
	* @throws SystemException
	*/
	public static boolean validateAddUserToCourse(long groupId, long userId,
		long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().validateAddUserToCourse(groupId, userId, teamId);
	}

	public static void clearService() {
		_service = null;
	}

	public static CourseLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					CourseLocalService.class.getName());

			if (invokableLocalService instanceof CourseLocalService) {
				_service = (CourseLocalService)invokableLocalService;
			}
			else {
				_service = new CourseLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(CourseLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(CourseLocalService service) {
	}

	private static CourseLocalService _service;
}