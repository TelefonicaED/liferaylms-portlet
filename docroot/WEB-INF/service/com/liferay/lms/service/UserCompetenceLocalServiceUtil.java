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
 * The utility for the user competence local service. This utility wraps {@link com.liferay.lms.service.impl.UserCompetenceLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see UserCompetenceLocalService
 * @see com.liferay.lms.service.base.UserCompetenceLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.UserCompetenceLocalServiceImpl
 * @generated
 */
public class UserCompetenceLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.UserCompetenceLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the user competence to the database. Also notifies the appropriate model listeners.
	*
	* @param userCompetence the user competence
	* @return the user competence that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence addUserCompetence(
		com.liferay.lms.model.UserCompetence userCompetence)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addUserCompetence(userCompetence);
	}

	/**
	* Creates a new user competence with the primary key. Does not add the user competence to the database.
	*
	* @param usercompId the primary key for the new user competence
	* @return the new user competence
	*/
	public static com.liferay.lms.model.UserCompetence createUserCompetence(
		long usercompId) {
		return getService().createUserCompetence(usercompId);
	}

	/**
	* Deletes the user competence with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param usercompId the primary key of the user competence
	* @return the user competence that was removed
	* @throws PortalException if a user competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence deleteUserCompetence(
		long usercompId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteUserCompetence(usercompId);
	}

	/**
	* Deletes the user competence from the database. Also notifies the appropriate model listeners.
	*
	* @param userCompetence the user competence
	* @return the user competence that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence deleteUserCompetence(
		com.liferay.lms.model.UserCompetence userCompetence)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteUserCompetence(userCompetence);
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

	public static com.liferay.lms.model.UserCompetence fetchUserCompetence(
		long usercompId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchUserCompetence(usercompId);
	}

	/**
	* Returns the user competence with the primary key.
	*
	* @param usercompId the primary key of the user competence
	* @return the user competence
	* @throws PortalException if a user competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence getUserCompetence(
		long usercompId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserCompetence(usercompId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the user competences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @return the range of user competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.UserCompetence> getUserCompetences(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserCompetences(start, end);
	}

	/**
	* Returns the number of user competences.
	*
	* @return the number of user competences
	* @throws SystemException if a system exception occurred
	*/
	public static int getUserCompetencesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserCompetencesCount();
	}

	/**
	* Updates the user competence in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param userCompetence the user competence
	* @return the user competence that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence updateUserCompetence(
		com.liferay.lms.model.UserCompetence userCompetence)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUserCompetence(userCompetence);
	}

	/**
	* Updates the user competence in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param userCompetence the user competence
	* @param merge whether to merge the user competence with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the user competence that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.UserCompetence updateUserCompetence(
		com.liferay.lms.model.UserCompetence userCompetence, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUserCompetence(userCompetence, merge);
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

	public static com.liferay.lms.model.UserCompetence findByUserIdCompetenceId(
		long userId, long competenceId) {
		return getService().findByUserIdCompetenceId(userId, competenceId);
	}

	public static java.util.List<com.liferay.lms.model.UserCompetence> findBuUserId(
		long userId) {
		return getService().findBuUserId(userId);
	}

	public static java.util.List<com.liferay.lms.model.UserCompetence> findBuUserId(
		long userId, int start, int end) {
		return getService().findBuUserId(userId, start, end);
	}

	public static int countByUserId(long userId) {
		return getService().countByUserId(userId);
	}

	public static void clearService() {
		_service = null;
	}

	public static UserCompetenceLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					UserCompetenceLocalService.class.getName());

			if (invokableLocalService instanceof UserCompetenceLocalService) {
				_service = (UserCompetenceLocalService)invokableLocalService;
			}
			else {
				_service = new UserCompetenceLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(UserCompetenceLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(UserCompetenceLocalService service) {
	}

	private static UserCompetenceLocalService _service;
}