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
 * This class is a wrapper for {@link SurveyResultLocalService}.
 * </p>
 *
 * @author    TLS
 * @see       SurveyResultLocalService
 * @generated
 */
public class SurveyResultLocalServiceWrapper implements SurveyResultLocalService,
	ServiceWrapper<SurveyResultLocalService> {
	public SurveyResultLocalServiceWrapper(
		SurveyResultLocalService surveyResultLocalService) {
		_surveyResultLocalService = surveyResultLocalService;
	}

	/**
	* Adds the survey result to the database. Also notifies the appropriate model listeners.
	*
	* @param surveyResult the survey result
	* @return the survey result that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SurveyResult addSurveyResult(
		com.liferay.lms.model.SurveyResult surveyResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _surveyResultLocalService.addSurveyResult(surveyResult);
	}

	/**
	* Creates a new survey result with the primary key. Does not add the survey result to the database.
	*
	* @param surveyResultId the primary key for the new survey result
	* @return the new survey result
	*/
	public com.liferay.lms.model.SurveyResult createSurveyResult(
		long surveyResultId) {
		return _surveyResultLocalService.createSurveyResult(surveyResultId);
	}

	/**
	* Deletes the survey result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param surveyResultId the primary key of the survey result
	* @return the survey result that was removed
	* @throws PortalException if a survey result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SurveyResult deleteSurveyResult(
		long surveyResultId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _surveyResultLocalService.deleteSurveyResult(surveyResultId);
	}

	/**
	* Deletes the survey result from the database. Also notifies the appropriate model listeners.
	*
	* @param surveyResult the survey result
	* @return the survey result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SurveyResult deleteSurveyResult(
		com.liferay.lms.model.SurveyResult surveyResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _surveyResultLocalService.deleteSurveyResult(surveyResult);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _surveyResultLocalService.dynamicQuery();
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
		return _surveyResultLocalService.dynamicQuery(dynamicQuery);
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
		return _surveyResultLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _surveyResultLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _surveyResultLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.lms.model.SurveyResult fetchSurveyResult(
		long surveyResultId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _surveyResultLocalService.fetchSurveyResult(surveyResultId);
	}

	/**
	* Returns the survey result with the primary key.
	*
	* @param surveyResultId the primary key of the survey result
	* @return the survey result
	* @throws PortalException if a survey result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SurveyResult getSurveyResult(
		long surveyResultId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _surveyResultLocalService.getSurveyResult(surveyResultId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _surveyResultLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the survey results.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of survey results
	* @param end the upper bound of the range of survey results (not inclusive)
	* @return the range of survey results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.SurveyResult> getSurveyResults(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _surveyResultLocalService.getSurveyResults(start, end);
	}

	/**
	* Returns the number of survey results.
	*
	* @return the number of survey results
	* @throws SystemException if a system exception occurred
	*/
	public int getSurveyResultsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _surveyResultLocalService.getSurveyResultsCount();
	}

	/**
	* Updates the survey result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param surveyResult the survey result
	* @return the survey result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SurveyResult updateSurveyResult(
		com.liferay.lms.model.SurveyResult surveyResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _surveyResultLocalService.updateSurveyResult(surveyResult);
	}

	/**
	* Updates the survey result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param surveyResult the survey result
	* @param merge whether to merge the survey result with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the survey result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.SurveyResult updateSurveyResult(
		com.liferay.lms.model.SurveyResult surveyResult, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _surveyResultLocalService.updateSurveyResult(surveyResult, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _surveyResultLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_surveyResultLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _surveyResultLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	public java.util.List<com.liferay.lms.model.SurveyResult> getSurveyResultByActId(
		long actId) throws com.liferay.portal.kernel.exception.SystemException {
		return _surveyResultLocalService.getSurveyResultByActId(actId);
	}

	public double getPercentageByQuestionIdAndAnswerId(long questionId,
		long answerId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _surveyResultLocalService.getPercentageByQuestionIdAndAnswerId(questionId,
			answerId);
	}

	public long getTotalAnswersByQuestionId(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _surveyResultLocalService.getTotalAnswersByQuestionId(questionId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SurveyResultLocalService getWrappedSurveyResultLocalService() {
		return _surveyResultLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSurveyResultLocalService(
		SurveyResultLocalService surveyResultLocalService) {
		_surveyResultLocalService = surveyResultLocalService;
	}

	public SurveyResultLocalService getWrappedService() {
		return _surveyResultLocalService;
	}

	public void setWrappedService(
		SurveyResultLocalService surveyResultLocalService) {
		_surveyResultLocalService = surveyResultLocalService;
	}

	private SurveyResultLocalService _surveyResultLocalService;
}