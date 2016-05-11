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
 * This class is a wrapper for {@link CompetenceService}.
 * </p>
 *
 * @author    TLS
 * @see       CompetenceService
 * @generated
 */
public class CompetenceServiceWrapper implements CompetenceService,
	ServiceWrapper<CompetenceService> {
	public CompetenceServiceWrapper(CompetenceService competenceService) {
		_competenceService = competenceService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _competenceService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_competenceService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _competenceService.invokeMethod(name, parameterTypes, arguments);
	}

	public java.util.List<com.liferay.lms.model.Competence> getCompetencesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competenceService.getCompetencesOfGroup(groupId);
	}

	public java.util.List<com.liferay.lms.model.Competence> getCompetencesOfGroup(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competenceService.getCompetencesOfGroup(groupId, start, end);
	}

	public int getCountCompetencesOfGroup(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competenceService.getCountCompetencesOfGroup(groupId);
	}

	public java.util.HashMap<java.lang.Long, com.liferay.lms.model.Competence> getCompetencesOfGroups(
		long[] groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _competenceService.getCompetencesOfGroups(groups);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public CompetenceService getWrappedCompetenceService() {
		return _competenceService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedCompetenceService(CompetenceService competenceService) {
		_competenceService = competenceService;
	}

	public CompetenceService getWrappedService() {
		return _competenceService;
	}

	public void setWrappedService(CompetenceService competenceService) {
		_competenceService = competenceService;
	}

	private CompetenceService _competenceService;
}