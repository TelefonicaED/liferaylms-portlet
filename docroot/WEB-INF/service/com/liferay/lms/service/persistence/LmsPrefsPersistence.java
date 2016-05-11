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

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the lms prefs service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LmsPrefsPersistenceImpl
 * @see LmsPrefsUtil
 * @generated
 */
public interface LmsPrefsPersistence extends BasePersistence<LmsPrefs> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LmsPrefsUtil} to access the lms prefs persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the lms prefs in the entity cache if it is enabled.
	*
	* @param lmsPrefs the lms prefs
	*/
	public void cacheResult(com.liferay.lms.model.LmsPrefs lmsPrefs);

	/**
	* Caches the lms prefses in the entity cache if it is enabled.
	*
	* @param lmsPrefses the lms prefses
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.LmsPrefs> lmsPrefses);

	/**
	* Creates a new lms prefs with the primary key. Does not add the lms prefs to the database.
	*
	* @param companyId the primary key for the new lms prefs
	* @return the new lms prefs
	*/
	public com.liferay.lms.model.LmsPrefs create(long companyId);

	/**
	* Removes the lms prefs with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param companyId the primary key of the lms prefs
	* @return the lms prefs that was removed
	* @throws com.liferay.lms.NoSuchPrefsException if a lms prefs with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LmsPrefs remove(long companyId)
		throws com.liferay.lms.NoSuchPrefsException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.LmsPrefs updateImpl(
		com.liferay.lms.model.LmsPrefs lmsPrefs, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the lms prefs with the primary key or throws a {@link com.liferay.lms.NoSuchPrefsException} if it could not be found.
	*
	* @param companyId the primary key of the lms prefs
	* @return the lms prefs
	* @throws com.liferay.lms.NoSuchPrefsException if a lms prefs with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LmsPrefs findByPrimaryKey(long companyId)
		throws com.liferay.lms.NoSuchPrefsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the lms prefs with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param companyId the primary key of the lms prefs
	* @return the lms prefs, or <code>null</code> if a lms prefs with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LmsPrefs fetchByPrimaryKey(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the lms prefses.
	*
	* @return the lms prefses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LmsPrefs> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.LmsPrefs> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.LmsPrefs> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the lms prefses from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of lms prefses.
	*
	* @return the number of lms prefses
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}