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
 * The utility for the competence local service. This utility wraps {@link com.liferay.lms.service.impl.CompetenceLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see CompetenceLocalService
 * @see com.liferay.lms.service.base.CompetenceLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.CompetenceLocalServiceImpl
 * @generated
 */
public class CompetenceLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.CompetenceLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the competence to the database. Also notifies the appropriate model listeners.
	*
	* @param competence the competence
	* @return the competence that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Competence addCompetence(
		com.liferay.lms.model.Competence competence)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addCompetence(competence);
	}

	/**
	* Creates a new competence with the primary key. Does not add the competence to the database.
	*
	* @param competenceId the primary key for the new competence
	* @return the new competence
	*/
	public static com.liferay.lms.model.Competence createCompetence(
		long competenceId) {
		return getService().createCompetence(competenceId);
	}

	/**
	* Deletes the competence with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param competenceId the primary key of the competence
	* @return the competence that was removed
	* @throws PortalException if a competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Competence deleteCompetence(
		long competenceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteCompetence(competenceId);
	}

	/**
	* Deletes the competence from the database. Also notifies the appropriate model listeners.
	*
	* @param competence the competence
	* @return the competence that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Competence deleteCompetence(
		com.liferay.lms.model.Competence competence)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteCompetence(competence);
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

	public static com.liferay.lms.model.Competence fetchCompetence(
		long competenceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchCompetence(competenceId);
	}

	/**
	* Returns the competence with the primary key.
	*
	* @param competenceId the primary key of the competence
	* @return the competence
	* @throws PortalException if a competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Competence getCompetence(
		long competenceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompetence(competenceId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the competence with the UUID in the group.
	*
	* @param uuid the UUID of competence
	* @param groupId the group id of the competence
	* @return the competence
	* @throws PortalException if a competence with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Competence getCompetenceByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompetenceByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the competences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of competences
	* @param end the upper bound of the range of competences (not inclusive)
	* @return the range of competences
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.Competence> getCompetences(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompetences(start, end);
	}

	/**
	* Returns the number of competences.
	*
	* @return the number of competences
	* @throws SystemException if a system exception occurred
	*/
	public static int getCompetencesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompetencesCount();
	}

	/**
	* Updates the competence in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param competence the competence
	* @return the competence that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Competence updateCompetence(
		com.liferay.lms.model.Competence competence)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCompetence(competence);
	}

	/**
	* Updates the competence in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param competence the competence
	* @param merge whether to merge the competence with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the competence that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.Competence updateCompetence(
		com.liferay.lms.model.Competence competence, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCompetence(competence, merge);
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

	public static com.liferay.lms.model.Competence addCompetence(
		java.lang.String title, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addCompetence(title, description, serviceContext);
	}

	public static com.liferay.lms.model.Competence addCompetence(
		java.lang.String title, java.lang.String description,
		boolean generateCertificate,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addCompetence(title, description, generateCertificate,
			serviceContext);
	}

	public static com.liferay.lms.model.Competence updateCompetence(
		com.liferay.lms.model.Competence competence,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCompetence(competence, serviceContext);
	}

	public static com.liferay.lms.model.Competence modCompetence(
		com.liferay.lms.model.Competence competence,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().modCompetence(competence, serviceContext);
	}

	public static java.lang.String getBGImageURL(long groupId,
		javax.servlet.http.HttpServletRequest request) {
		return getService().getBGImageURL(groupId, request);
	}

	public static java.lang.String getBGImageURL(
		com.liferay.lms.model.Competence competence,
		javax.servlet.http.HttpServletRequest request) {
		return getService().getBGImageURL(competence, request);
	}

	public static void setBGImage(byte[] data, long groupId,
		java.lang.String name) throws java.io.IOException {
		getService().setBGImage(data, groupId, name);
	}

	public static long countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countAll();
	}

	public static java.util.List findByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().findByCompanyId(companyId);
	}

	public static void clearService() {
		_service = null;
	}

	public static CompetenceLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					CompetenceLocalService.class.getName());

			if (invokableLocalService instanceof CompetenceLocalService) {
				_service = (CompetenceLocalService)invokableLocalService;
			}
			else {
				_service = new CompetenceLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(CompetenceLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(CompetenceLocalService service) {
	}

	private static CompetenceLocalService _service;
}