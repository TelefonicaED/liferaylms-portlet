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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.InvokableLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * The interface for the p2p activity local service.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see P2pActivityLocalServiceUtil
 * @see com.liferay.lms.service.base.P2pActivityLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.P2pActivityLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface P2pActivityLocalService extends BaseLocalService,
	InvokableLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link P2pActivityLocalServiceUtil} to access the p2p activity local service. Add custom service methods to {@link com.liferay.lms.service.impl.P2pActivityLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the p2p activity to the database. Also notifies the appropriate model listeners.
	*
	* @param p2pActivity the p2p activity
	* @return the p2p activity that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity addP2pActivity(
		com.liferay.lms.model.P2pActivity p2pActivity)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new p2p activity with the primary key. Does not add the p2p activity to the database.
	*
	* @param p2pActivityId the primary key for the new p2p activity
	* @return the new p2p activity
	*/
	public com.liferay.lms.model.P2pActivity createP2pActivity(
		long p2pActivityId);

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
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the p2p activity from the database. Also notifies the appropriate model listeners.
	*
	* @param p2pActivity the p2p activity
	* @return the p2p activity that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity deleteP2pActivity(
		com.liferay.lms.model.P2pActivity p2pActivity)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

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
		throws com.liferay.portal.kernel.exception.SystemException;

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
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.lms.model.P2pActivity fetchP2pActivity(
		long p2pActivityId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the p2p activity with the primary key.
	*
	* @param p2pActivityId the primary key of the p2p activity
	* @return the p2p activity
	* @throws PortalException if a p2p activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.lms.model.P2pActivity getP2pActivity(long p2pActivityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.lms.model.P2pActivity> getP2pActivities(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of p2p activities.
	*
	* @return the number of p2p activities
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getP2pActivitiesCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the p2p activity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param p2pActivity the p2p activity
	* @return the p2p activity that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.P2pActivity updateP2pActivity(
		com.liferay.lms.model.P2pActivity p2pActivity)
		throws com.liferay.portal.kernel.exception.SystemException;

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
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable;

	public com.liferay.lms.model.P2pActivity findByActIdAndUserId(long actId,
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	public boolean existP2pAct(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.lms.model.P2pActivity> findByActId(
		long actId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.lms.model.P2pActivity> findByActId(
		long actId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.lms.model.P2pActivity> findByActIdOrderByP2pId(
		long actId) throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.User> getUsersToCorrectP2P(
		long actId, long userId, int numUsers, java.util.Calendar calendar)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.lms.model.P2pActivity> getP2pActivitiesToCorrect(
		long actId, long p2pActivityId, int numValidaciones)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.lms.model.P2pActivity> getP2PActivitiesInDay(
		java.util.Calendar calendar)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getNumCorrectsByDayP2P(long actId, java.util.Calendar calendar)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}