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
 * This class is a wrapper for {@link P2pActivityLocalService}.
 * </p>
 *
 * @author    TLS
 * @see       P2pActivityLocalService
 * @generated
 */
public class P2pActivityLocalServiceWrapper implements P2pActivityLocalService,
	ServiceWrapper<P2pActivityLocalService> {
	public P2pActivityLocalServiceWrapper(
		P2pActivityLocalService p2pActivityLocalService) {
		_p2pActivityLocalService = p2pActivityLocalService;
	}

	/**
	* Adds the p2p activity to the database. Also notifies the appropriate model listeners.
	*
	* @param p2pActivity the p2p activity
	* @return the p2p activity that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity addP2pActivity(
		com.liferay.lms.model.P2pActivity p2pActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.addP2pActivity(p2pActivity);
	}

	/**
	* Creates a new p2p activity with the primary key. Does not add the p2p activity to the database.
	*
	* @param p2pActivityId the primary key for the new p2p activity
	* @return the new p2p activity
	*/
	public com.liferay.lms.model.P2pActivity createP2pActivity(
		long p2pActivityId) {
		return _p2pActivityLocalService.createP2pActivity(p2pActivityId);
	}

	/**
	* Deletes the p2p activity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param p2pActivityId the primary key of the p2p activity
	* @return the p2p activity that was removed
	* @throws PortalException if a p2p activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity deleteP2pActivity(
		long p2pActivityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.deleteP2pActivity(p2pActivityId);
	}

	/**
	* Deletes the p2p activity from the database. Also notifies the appropriate model listeners.
	*
	* @param p2pActivity the p2p activity
	* @return the p2p activity that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity deleteP2pActivity(
		com.liferay.lms.model.P2pActivity p2pActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.deleteP2pActivity(p2pActivity);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _p2pActivityLocalService.dynamicQuery();
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
		return _p2pActivityLocalService.dynamicQuery(dynamicQuery);
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
		return _p2pActivityLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _p2pActivityLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _p2pActivityLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.lms.model.P2pActivity fetchP2pActivity(
		long p2pActivityId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.fetchP2pActivity(p2pActivityId);
	}

	/**
	* Returns the p2p activity with the primary key.
	*
	* @param p2pActivityId the primary key of the p2p activity
	* @return the p2p activity
	* @throws PortalException if a p2p activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity getP2pActivity(long p2pActivityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.getP2pActivity(p2pActivityId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the p2p activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of p2p activities
	* @param end the upper bound of the range of p2p activities (not inclusive)
	* @return the range of p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.P2pActivity> getP2pActivities(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.getP2pActivities(start, end);
	}

	/**
	* Returns the number of p2p activities.
	*
	* @return the number of p2p activities
	* @throws SystemException if a system exception occurred
	*/
	public int getP2pActivitiesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.getP2pActivitiesCount();
	}

	/**
	* Updates the p2p activity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param p2pActivity the p2p activity
	* @return the p2p activity that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity updateP2pActivity(
		com.liferay.lms.model.P2pActivity p2pActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.updateP2pActivity(p2pActivity);
	}

	/**
	* Updates the p2p activity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param p2pActivity the p2p activity
	* @param merge whether to merge the p2p activity with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the p2p activity that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity updateP2pActivity(
		com.liferay.lms.model.P2pActivity p2pActivity, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.updateP2pActivity(p2pActivity, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _p2pActivityLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_p2pActivityLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _p2pActivityLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	public com.liferay.lms.model.P2pActivity findByActIdAndUserId(long actId,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.findByActIdAndUserId(actId, userId);
	}

	public boolean existP2pAct(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.existP2pAct(actId, userId);
	}

	public java.util.List<com.liferay.lms.model.P2pActivity> findByActId(
		long actId) throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.findByActId(actId);
	}

	public java.util.List<com.liferay.lms.model.P2pActivity> findByActId(
		long actId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.findByActId(actId, start, end);
	}

	public java.util.List<com.liferay.lms.model.P2pActivity> findByActIdOrderByP2pId(
		long actId) throws com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.findByActIdOrderByP2pId(actId);
	}

	public java.util.List<com.liferay.portal.model.User> getUsersToCorrectP2P(
		long actId, long userId, int numUsers, java.util.Calendar calendar)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.getUsersToCorrectP2P(actId, userId,
			numUsers, calendar);
	}

	public java.util.List<com.liferay.lms.model.P2pActivity> getP2pActivitiesToCorrect(
		long actId, long p2pActivityId, int numValidaciones)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.getP2pActivitiesToCorrect(actId,
			p2pActivityId, numValidaciones);
	}

	public java.util.List<com.liferay.lms.model.P2pActivity> getP2PActivitiesInDay(
		java.util.Calendar calendar)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.getP2PActivitiesInDay(calendar);
	}

	public int getNumCorrectsByDayP2P(long actId, java.util.Calendar calendar)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _p2pActivityLocalService.getNumCorrectsByDayP2P(actId, calendar);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public P2pActivityLocalService getWrappedP2pActivityLocalService() {
		return _p2pActivityLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedP2pActivityLocalService(
		P2pActivityLocalService p2pActivityLocalService) {
		_p2pActivityLocalService = p2pActivityLocalService;
	}

	public P2pActivityLocalService getWrappedService() {
		return _p2pActivityLocalService;
	}

	public void setWrappedService(
		P2pActivityLocalService p2pActivityLocalService) {
		_p2pActivityLocalService = p2pActivityLocalService;
	}

	private P2pActivityLocalService _p2pActivityLocalService;
}