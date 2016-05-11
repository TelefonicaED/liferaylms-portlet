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

import com.liferay.lms.model.LmsPrefs;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the lms prefs service. This utility wraps {@link LmsPrefsPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LmsPrefsPersistence
 * @see LmsPrefsPersistenceImpl
 * @generated
 */
public class LmsPrefsUtil {
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
	public static void clearCache(LmsPrefs lmsPrefs) {
		getPersistence().clearCache(lmsPrefs);
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
	public static List<LmsPrefs> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LmsPrefs> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LmsPrefs> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static LmsPrefs update(LmsPrefs lmsPrefs, boolean merge)
		throws SystemException {
		return getPersistence().update(lmsPrefs, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static LmsPrefs update(LmsPrefs lmsPrefs, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(lmsPrefs, merge, serviceContext);
	}

	/**
	* Caches the lms prefs in the entity cache if it is enabled.
	*
	* @param lmsPrefs the lms prefs
	*/
	public static void cacheResult(com.liferay.lms.model.LmsPrefs lmsPrefs) {
		getPersistence().cacheResult(lmsPrefs);
	}

	/**
	* Caches the lms prefses in the entity cache if it is enabled.
	*
	* @param lmsPrefses the lms prefses
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.LmsPrefs> lmsPrefses) {
		getPersistence().cacheResult(lmsPrefses);
	}

	/**
	* Creates a new lms prefs with the primary key. Does not add the lms prefs to the database.
	*
	* @param companyId the primary key for the new lms prefs
	* @return the new lms prefs
	*/
	public static com.liferay.lms.model.LmsPrefs create(long companyId) {
		return getPersistence().create(companyId);
	}

	/**
	* Removes the lms prefs with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param companyId the primary key of the lms prefs
	* @return the lms prefs that was removed
	* @throws com.liferay.lms.NoSuchPrefsException if a lms prefs with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LmsPrefs remove(long companyId)
		throws com.liferay.lms.NoSuchPrefsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(companyId);
	}

	public static com.liferay.lms.model.LmsPrefs updateImpl(
		com.liferay.lms.model.LmsPrefs lmsPrefs, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(lmsPrefs, merge);
	}

	/**
	* Returns the lms prefs with the primary key or throws a {@link com.liferay.lms.NoSuchPrefsException} if it could not be found.
	*
	* @param companyId the primary key of the lms prefs
	* @return the lms prefs
	* @throws com.liferay.lms.NoSuchPrefsException if a lms prefs with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LmsPrefs findByPrimaryKey(
		long companyId)
		throws com.liferay.lms.NoSuchPrefsException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(companyId);
	}

	/**
	* Returns the lms prefs with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param companyId the primary key of the lms prefs
	* @return the lms prefs, or <code>null</code> if a lms prefs with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LmsPrefs fetchByPrimaryKey(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(companyId);
	}

	/**
	* Returns all the lms prefses.
	*
	* @return the lms prefses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LmsPrefs> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the lms prefses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of lms prefses
	* @param end the upper bound of the range of lms prefses (not inclusive)
	* @return the range of lms prefses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LmsPrefs> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the lms prefses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of lms prefses
	* @param end the upper bound of the range of lms prefses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of lms prefses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.LmsPrefs> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the lms prefses from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of lms prefses.
	*
	* @return the number of lms prefses
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LmsPrefsPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LmsPrefsPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					LmsPrefsPersistence.class.getName());

			ReferenceRegistry.registerReference(LmsPrefsUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(LmsPrefsPersistence persistence) {
	}

	private static LmsPrefsPersistence _persistence;
}