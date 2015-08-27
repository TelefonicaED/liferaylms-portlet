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

package com.liferay.lms.service.persistence;

import com.liferay.lms.model.CheckP2pMailing;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the check p2p mailing service. This utility wraps {@link CheckP2pMailingPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CheckP2pMailingPersistence
 * @see CheckP2pMailingPersistenceImpl
 * @generated
 */
public class CheckP2pMailingUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(CheckP2pMailing checkP2pMailing) {
		getPersistence().clearCache(checkP2pMailing);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CheckP2pMailing> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CheckP2pMailing> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CheckP2pMailing> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static CheckP2pMailing update(CheckP2pMailing checkP2pMailing,
		boolean merge) throws SystemException {
		return getPersistence().update(checkP2pMailing, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static CheckP2pMailing update(CheckP2pMailing checkP2pMailing,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(checkP2pMailing, merge, serviceContext);
	}

	/**
	* Caches the check p2p mailing in the entity cache if it is enabled.
	*
	* @param checkP2pMailing the check p2p mailing
	*/
	public static void cacheResult(
		com.liferay.lms.model.CheckP2pMailing checkP2pMailing) {
		getPersistence().cacheResult(checkP2pMailing);
	}

	/**
	* Caches the check p2p mailings in the entity cache if it is enabled.
	*
	* @param checkP2pMailings the check p2p mailings
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.CheckP2pMailing> checkP2pMailings) {
		getPersistence().cacheResult(checkP2pMailings);
	}

	/**
	* Creates a new check p2p mailing with the primary key. Does not add the check p2p mailing to the database.
	*
	* @param checkP2pId the primary key for the new check p2p mailing
	* @return the new check p2p mailing
	*/
	public static com.liferay.lms.model.CheckP2pMailing create(long checkP2pId) {
		return getPersistence().create(checkP2pId);
	}

	/**
	* Removes the check p2p mailing with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param checkP2pId the primary key of the check p2p mailing
	* @return the check p2p mailing that was removed
	* @throws com.liferay.lms.NoSuchCheckP2pMailingException if a check p2p mailing with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CheckP2pMailing remove(long checkP2pId)
		throws com.liferay.lms.NoSuchCheckP2pMailingException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(checkP2pId);
	}

	public static com.liferay.lms.model.CheckP2pMailing updateImpl(
		com.liferay.lms.model.CheckP2pMailing checkP2pMailing, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(checkP2pMailing, merge);
	}

	/**
	* Returns the check p2p mailing with the primary key or throws a {@link com.liferay.lms.NoSuchCheckP2pMailingException} if it could not be found.
	*
	* @param checkP2pId the primary key of the check p2p mailing
	* @return the check p2p mailing
	* @throws com.liferay.lms.NoSuchCheckP2pMailingException if a check p2p mailing with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CheckP2pMailing findByPrimaryKey(
		long checkP2pId)
		throws com.liferay.lms.NoSuchCheckP2pMailingException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(checkP2pId);
	}

	/**
	* Returns the check p2p mailing with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param checkP2pId the primary key of the check p2p mailing
	* @return the check p2p mailing, or <code>null</code> if a check p2p mailing with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CheckP2pMailing fetchByPrimaryKey(
		long checkP2pId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(checkP2pId);
	}

	/**
	* Returns the check p2p mailing where actId = &#63; or throws a {@link com.liferay.lms.NoSuchCheckP2pMailingException} if it could not be found.
	*
	* @param actId the act ID
	* @return the matching check p2p mailing
	* @throws com.liferay.lms.NoSuchCheckP2pMailingException if a matching check p2p mailing could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CheckP2pMailing findByactId(long actId)
		throws com.liferay.lms.NoSuchCheckP2pMailingException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByactId(actId);
	}

	/**
	* Returns the check p2p mailing where actId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param actId the act ID
	* @return the matching check p2p mailing, or <code>null</code> if a matching check p2p mailing could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CheckP2pMailing fetchByactId(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByactId(actId);
	}

	/**
	* Returns the check p2p mailing where actId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param actId the act ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching check p2p mailing, or <code>null</code> if a matching check p2p mailing could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CheckP2pMailing fetchByactId(
		long actId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByactId(actId, retrieveFromCache);
	}

	/**
	* Returns all the check p2p mailings.
	*
	* @return the check p2p mailings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CheckP2pMailing> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the check p2p mailings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of check p2p mailings
	* @param end the upper bound of the range of check p2p mailings (not inclusive)
	* @return the range of check p2p mailings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CheckP2pMailing> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the check p2p mailings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of check p2p mailings
	* @param end the upper bound of the range of check p2p mailings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of check p2p mailings
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CheckP2pMailing> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes the check p2p mailing where actId = &#63; from the database.
	*
	* @param actId the act ID
	* @return the check p2p mailing that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CheckP2pMailing removeByactId(
		long actId)
		throws com.liferay.lms.NoSuchCheckP2pMailingException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByactId(actId);
	}

	/**
	* Removes all the check p2p mailings from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of check p2p mailings where actId = &#63;.
	*
	* @param actId the act ID
	* @return the number of matching check p2p mailings
	* @throws SystemException if a system exception occurred
	*/
	public static int countByactId(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByactId(actId);
	}

	/**
	* Returns the number of check p2p mailings.
	*
	* @return the number of check p2p mailings
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static CheckP2pMailingPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CheckP2pMailingPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					CheckP2pMailingPersistence.class.getName());

			ReferenceRegistry.registerReference(CheckP2pMailingUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(CheckP2pMailingPersistence persistence) {
	}

	private static CheckP2pMailingPersistence _persistence;
}