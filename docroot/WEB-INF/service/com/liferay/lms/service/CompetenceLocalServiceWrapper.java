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
 * This class is a wrapper for {@link CompetenceLocalService}.
 * </p>
 *
 * @author    TLS
 * @see       CompetenceLocalService
 * @generated
 */
public class CompetenceLocalServiceWrapper implements CompetenceLocalService,
	ServiceWrapper<CompetenceLocalService> {
	public CompetenceLocalServiceWrapper(
		CompetenceLocalService competenceLocalService) {
		_competenceLocalService = competenceLocalService;
	}

	/**
	* Adds the competence to the database. Also notifies the appropriate model listeners.
	*
	* @param competence the competence
	* @return the competence that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence addCompetence(
		com.liferay.lms.model.Competence competence)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.addCompetence(competence);
	}

	/**
	* Creates a new competence with the primary key. Does not add the competence to the database.
	*
	* @param competenceId the primary key for the new competence
	* @return the new competence
	*/
	public com.liferay.lms.model.Competence createCompetence(long competenceId) {
		return _competenceLocalService.createCompetence(competenceId);
	}

	/**
	* Deletes the competence with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param competenceId the primary key of the competence
	* @return the competence that was removed
	* @throws PortalException if a competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence deleteCompetence(long competenceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.deleteCompetence(competenceId);
	}

	/**
	* Deletes the competence from the database. Also notifies the appropriate model listeners.
	*
	* @param competence the competence
	* @return the competence that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence deleteCompetence(
		com.liferay.lms.model.Competence competence)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.deleteCompetence(competence);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _competenceLocalService.dynamicQuery();
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
		return _competenceLocalService.dynamicQuery(dynamicQuery);
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
		return _competenceLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _competenceLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _competenceLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.lms.model.Competence fetchCompetence(long competenceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.fetchCompetence(competenceId);
	}

	/**
	* Returns the competence with the primary key.
	*
	* @param competenceId the primary key of the competence
	* @return the competence
	* @throws PortalException if a competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence getCompetence(long competenceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.getCompetence(competenceId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.getPersistedModel(primaryKeyObj);
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
	public com.liferay.lms.model.Competence getCompetenceByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.getCompetenceByUuidAndGroupId(uuid,
			groupId);
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
	public java.util.List<com.liferay.lms.model.Competence> getCompetences(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.getCompetences(start, end);
	}

	/**
	* Returns the number of competences.
	*
	* @return the number of competences
	* @throws SystemException if a system exception occurred
	*/
	public int getCompetencesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.getCompetencesCount();
	}

	/**
	* Updates the competence in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param competence the competence
	* @return the competence that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence updateCompetence(
		com.liferay.lms.model.Competence competence)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.updateCompetence(competence);
	}

	/**
	* Updates the competence in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param competence the competence
	* @param merge whether to merge the competence with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the competence that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.Competence updateCompetence(
		com.liferay.lms.model.Competence competence, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.updateCompetence(competence, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _competenceLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_competenceLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _competenceLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	public com.liferay.lms.model.Competence addCompetence(
		java.lang.String title, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.addCompetence(title, description,
			serviceContext);
	}

	public com.liferay.lms.model.Competence addCompetence(
		java.lang.String title, java.lang.String description,
		boolean generateCertificate,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.addCompetence(title, description,
			generateCertificate, serviceContext);
	}

	public com.liferay.lms.model.Competence updateCompetence(
		com.liferay.lms.model.Competence competence,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.updateCompetence(competence,
			serviceContext);
	}

	public com.liferay.lms.model.Competence modCompetence(
		com.liferay.lms.model.Competence competence,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.modCompetence(competence, serviceContext);
	}

	public java.lang.String getBGImageURL(long groupId,
		javax.servlet.http.HttpServletRequest request) {
		return _competenceLocalService.getBGImageURL(groupId, request);
	}

	public java.lang.String getBGImageURL(
		com.liferay.lms.model.Competence competence,
		javax.servlet.http.HttpServletRequest request) {
		return _competenceLocalService.getBGImageURL(competence, request);
	}

	public void setBGImage(byte[] data, long groupId, java.lang.String name)
		throws java.io.IOException {
		_competenceLocalService.setBGImage(data, groupId, name);
	}

	public long countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.countAll();
	}

	public java.util.List findByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competenceLocalService.findByCompanyId(companyId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public CompetenceLocalService getWrappedCompetenceLocalService() {
		return _competenceLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedCompetenceLocalService(
		CompetenceLocalService competenceLocalService) {
		_competenceLocalService = competenceLocalService;
	}

	public CompetenceLocalService getWrappedService() {
		return _competenceLocalService;
	}

	public void setWrappedService(CompetenceLocalService competenceLocalService) {
		_competenceLocalService = competenceLocalService;
	}

	private CompetenceLocalService _competenceLocalService;
}