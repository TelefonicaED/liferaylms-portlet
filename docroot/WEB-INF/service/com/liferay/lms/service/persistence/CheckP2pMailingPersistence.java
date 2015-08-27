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

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the check p2p mailing service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CheckP2pMailingPersistenceImpl
 * @see CheckP2pMailingUtil
 * @generated
 */
public interface CheckP2pMailingPersistence extends BasePersistence<CheckP2pMailing> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CheckP2pMailingUtil} to access the check p2p mailing persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the check p2p mailing in the entity cache if it is enabled.
	*
	* @param checkP2pMailing the check p2p mailing
	*/
	public void cacheResult(
		com.liferay.lms.model.CheckP2pMailing checkP2pMailing);

	/**
	* Caches the check p2p mailings in the entity cache if it is enabled.
	*
	* @param checkP2pMailings the check p2p mailings
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.CheckP2pMailing> checkP2pMailings);

	/**
	* Creates a new check p2p mailing with the primary key. Does not add the check p2p mailing to the database.
	*
	* @param checkP2pId the primary key for the new check p2p mailing
	* @return the new check p2p mailing
	*/
	public com.liferay.lms.model.CheckP2pMailing create(long checkP2pId);

	/**
	* Removes the check p2p mailing with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param checkP2pId the primary key of the check p2p mailing
	* @return the check p2p mailing that was removed
	* @throws com.liferay.lms.NoSuchCheckP2pMailingException if a check p2p mailing with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CheckP2pMailing remove(long checkP2pId)
		throws com.liferay.lms.NoSuchCheckP2pMailingException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.CheckP2pMailing updateImpl(
		com.liferay.lms.model.CheckP2pMailing checkP2pMailing, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the check p2p mailing with the primary key or throws a {@link com.liferay.lms.NoSuchCheckP2pMailingException} if it could not be found.
	*
	* @param checkP2pId the primary key of the check p2p mailing
	* @return the check p2p mailing
	* @throws com.liferay.lms.NoSuchCheckP2pMailingException if a check p2p mailing with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CheckP2pMailing findByPrimaryKey(
		long checkP2pId)
		throws com.liferay.lms.NoSuchCheckP2pMailingException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the check p2p mailing with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param checkP2pId the primary key of the check p2p mailing
	* @return the check p2p mailing, or <code>null</code> if a check p2p mailing with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CheckP2pMailing fetchByPrimaryKey(
		long checkP2pId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the check p2p mailing where actId = &#63; or throws a {@link com.liferay.lms.NoSuchCheckP2pMailingException} if it could not be found.
	*
	* @param actId the act ID
	* @return the matching check p2p mailing
	* @throws com.liferay.lms.NoSuchCheckP2pMailingException if a matching check p2p mailing could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CheckP2pMailing findByactId(long actId)
		throws com.liferay.lms.NoSuchCheckP2pMailingException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the check p2p mailing where actId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param actId the act ID
	* @return the matching check p2p mailing, or <code>null</code> if a matching check p2p mailing could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CheckP2pMailing fetchByactId(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the check p2p mailing where actId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param actId the act ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching check p2p mailing, or <code>null</code> if a matching check p2p mailing could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CheckP2pMailing fetchByactId(long actId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the check p2p mailings.
	*
	* @return the check p2p mailings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.CheckP2pMailing> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.CheckP2pMailing> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.model.CheckP2pMailing> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the check p2p mailing where actId = &#63; from the database.
	*
	* @param actId the act ID
	* @return the check p2p mailing that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CheckP2pMailing removeByactId(long actId)
		throws com.liferay.lms.NoSuchCheckP2pMailingException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the check p2p mailings from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of check p2p mailings where actId = &#63;.
	*
	* @param actId the act ID
	* @return the number of matching check p2p mailings
	* @throws SystemException if a system exception occurred
	*/
	public int countByactId(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of check p2p mailings.
	*
	* @return the number of check p2p mailings
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}