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
 * This class is a wrapper for {@link CourseResultLocalService}.
 * </p>
 *
 * @author    TLS
 * @see       CourseResultLocalService
 * @generated
 */
public class CourseResultLocalServiceWrapper implements CourseResultLocalService,
	ServiceWrapper<CourseResultLocalService> {
	public CourseResultLocalServiceWrapper(
		CourseResultLocalService courseResultLocalService) {
		_courseResultLocalService = courseResultLocalService;
	}

	/**
	* Adds the course result to the database. Also notifies the appropriate model listeners.
	*
	* @param courseResult the course result
	* @return the course result that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult addCourseResult(
		com.liferay.lms.model.CourseResult courseResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.addCourseResult(courseResult);
	}

	/**
	* Creates a new course result with the primary key. Does not add the course result to the database.
	*
	* @param crId the primary key for the new course result
	* @return the new course result
	*/
	public com.liferay.lms.model.CourseResult createCourseResult(long crId) {
		return _courseResultLocalService.createCourseResult(crId);
	}

	/**
	* Deletes the course result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param crId the primary key of the course result
	* @return the course result that was removed
	* @throws PortalException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult deleteCourseResult(long crId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.deleteCourseResult(crId);
	}

	/**
	* Deletes the course result from the database. Also notifies the appropriate model listeners.
	*
	* @param courseResult the course result
	* @return the course result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult deleteCourseResult(
		com.liferay.lms.model.CourseResult courseResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.deleteCourseResult(courseResult);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _courseResultLocalService.dynamicQuery();
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
		return _courseResultLocalService.dynamicQuery(dynamicQuery);
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
		return _courseResultLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _courseResultLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _courseResultLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.lms.model.CourseResult fetchCourseResult(long crId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.fetchCourseResult(crId);
	}

	/**
	* Returns the course result with the primary key.
	*
	* @param crId the primary key of the course result
	* @return the course result
	* @throws PortalException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult getCourseResult(long crId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.getCourseResult(crId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the course results.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of course results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.CourseResult> getCourseResults(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.getCourseResults(start, end);
	}

	/**
	* Returns the number of course results.
	*
	* @return the number of course results
	* @throws SystemException if a system exception occurred
	*/
	public int getCourseResultsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.getCourseResultsCount();
	}

	/**
	* Updates the course result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param courseResult the course result
	* @return the course result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult updateCourseResult(
		com.liferay.lms.model.CourseResult courseResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.updateCourseResult(courseResult);
	}

	/**
	* Updates the course result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param courseResult the course result
	* @param merge whether to merge the course result with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the course result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult updateCourseResult(
		com.liferay.lms.model.CourseResult courseResult, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.updateCourseResult(courseResult, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _courseResultLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_courseResultLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _courseResultLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	public com.liferay.lms.model.CourseResult getByUserAndCourse(
		long courseId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.getByUserAndCourse(courseId, userId);
	}

	public long countByCourseId(long courseId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.countByCourseId(courseId, passed);
	}

	public long countStudentsByCourseId(com.liferay.lms.model.Course course,
		boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.countStudentsByCourseId(course, passed);
	}

	public long countStudentsByCourseId(com.liferay.lms.model.Course course)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.countStudentsByCourseId(course);
	}

	public java.lang.Double avgResult(long courseId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.avgResult(courseId, passed);
	}

	public java.lang.Double avgResult(long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.avgResult(courseId);
	}

	public java.lang.Double avgStudentsResult(
		com.liferay.lms.model.Course course, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.avgStudentsResult(course, passed);
	}

	public com.liferay.lms.model.CourseResult create(long courseId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.create(courseId, userId);
	}

	public com.liferay.lms.model.CourseResult create(long courseId,
		long userId, java.util.Date allowStartDate,
		java.util.Date allowFinishDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.create(courseId, userId,
			allowStartDate, allowFinishDate);
	}

	public void update(com.liferay.lms.model.CourseResult cresult)
		throws com.liferay.portal.kernel.exception.SystemException {
		_courseResultLocalService.update(cresult);
	}

	public void update(com.liferay.lms.model.ModuleResult mresult)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_courseResultLocalService.update(mresult);
	}

	public com.liferay.lms.model.CourseResult getCourseResultByCourseAndUser(
		long courseId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _courseResultLocalService.getCourseResultByCourseAndUser(courseId,
			userId);
	}

	public java.lang.String translateResult(java.util.Locale locale,
		double result, long groupId) {
		return _courseResultLocalService.translateResult(locale, result, groupId);
	}

	public void softInitializeByGroupIdAndUserId(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_courseResultLocalService.softInitializeByGroupIdAndUserId(groupId,
			userId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public CourseResultLocalService getWrappedCourseResultLocalService() {
		return _courseResultLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedCourseResultLocalService(
		CourseResultLocalService courseResultLocalService) {
		_courseResultLocalService = courseResultLocalService;
	}

	public CourseResultLocalService getWrappedService() {
		return _courseResultLocalService;
	}

	public void setWrappedService(
		CourseResultLocalService courseResultLocalService) {
		_courseResultLocalService = courseResultLocalService;
	}

	private CourseResultLocalService _courseResultLocalService;
}