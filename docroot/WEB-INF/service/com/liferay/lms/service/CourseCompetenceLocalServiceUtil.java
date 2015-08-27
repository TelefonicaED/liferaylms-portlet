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
 * The utility for the course competence local service. This utility wraps {@link com.liferay.lms.service.impl.CourseCompetenceLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see CourseCompetenceLocalService
 * @see com.liferay.lms.service.base.CourseCompetenceLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.CourseCompetenceLocalServiceImpl
 * @generated
 */
public class CourseCompetenceLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.CourseCompetenceLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the course competence to the database. Also notifies the appropriate model listeners.
	*
	* @param courseCompetence the course competence
	* @return the course competence that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence addCourseCompetence(
		com.liferay.lms.model.CourseCompetence courseCompetence)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addCourseCompetence(courseCompetence);
	}

	/**
	* Creates a new course competence with the primary key. Does not add the course competence to the database.
	*
	* @param CourcompetenceId the primary key for the new course competence
	* @return the new course competence
	*/
	public static com.liferay.lms.model.CourseCompetence createCourseCompetence(
		long CourcompetenceId) {
		return getService().createCourseCompetence(CourcompetenceId);
	}

	/**
	* Deletes the course competence with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CourcompetenceId the primary key of the course competence
	* @return the course competence that was removed
	* @throws PortalException if a course competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence deleteCourseCompetence(
		long CourcompetenceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteCourseCompetence(CourcompetenceId);
	}

	/**
	* Deletes the course competence from the database. Also notifies the appropriate model listeners.
	*
	* @param courseCompetence the course competence
	* @return the course competence that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence deleteCourseCompetence(
		com.liferay.lms.model.CourseCompetence courseCompetence)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteCourseCompetence(courseCompetence);
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

	public static com.liferay.lms.model.CourseCompetence fetchCourseCompetence(
		long CourcompetenceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchCourseCompetence(CourcompetenceId);
	}

	/**
	* Returns the course competence with the primary key.
	*
	* @param CourcompetenceId the primary key of the course competence
	* @return the course competence
	* @throws PortalException if a course competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence getCourseCompetence(
		long CourcompetenceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCourseCompetence(CourcompetenceId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the course competences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of course competences
	* @param end the upper bound of the range of course competences (not inclusive)
	* @return the range of course competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseCompetence> getCourseCompetences(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCourseCompetences(start, end);
	}

	/**
	* Returns the number of course competences.
	*
	* @return the number of course competences
	* @throws SystemException if a system exception occurred
	*/
	public static int getCourseCompetencesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCourseCompetencesCount();
	}

	/**
	* Updates the course competence in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param courseCompetence the course competence
	* @return the course competence that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence updateCourseCompetence(
		com.liferay.lms.model.CourseCompetence courseCompetence)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCourseCompetence(courseCompetence);
	}

	/**
	* Updates the course competence in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param courseCompetence the course competence
	* @param merge whether to merge the course competence with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the course competence that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseCompetence updateCourseCompetence(
		com.liferay.lms.model.CourseCompetence courseCompetence, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCourseCompetence(courseCompetence, merge);
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

	public static java.util.List<com.liferay.lms.model.CourseCompetence> findBycourseId(
		long courseId, boolean condition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findBycourseId(courseId, condition);
	}

	public static com.liferay.lms.model.CourseCompetence fetchByCourseCompetenceCondition(
		long courseId, long competenceId, boolean condition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .fetchByCourseCompetenceCondition(courseId, competenceId,
			condition);
	}

	public static void clearService() {
		_service = null;
	}

	public static CourseCompetenceLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					CourseCompetenceLocalService.class.getName());

			if (invokableLocalService instanceof CourseCompetenceLocalService) {
				_service = (CourseCompetenceLocalService)invokableLocalService;
			}
			else {
				_service = new CourseCompetenceLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(CourseCompetenceLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(CourseCompetenceLocalService service) {
	}

	private static CourseCompetenceLocalService _service;
}