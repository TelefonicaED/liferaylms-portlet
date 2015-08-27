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

import com.liferay.lms.model.ModuleResult;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the module result service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see ModuleResultPersistenceImpl
 * @see ModuleResultUtil
 * @generated
 */
public interface ModuleResultPersistence extends BasePersistence<ModuleResult> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ModuleResultUtil} to access the module result persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the module result in the entity cache if it is enabled.
	*
	* @param moduleResult the module result
	*/
	public void cacheResult(com.liferay.lms.model.ModuleResult moduleResult);

	/**
	* Caches the module results in the entity cache if it is enabled.
	*
	* @param moduleResults the module results
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.ModuleResult> moduleResults);

	/**
	* Creates a new module result with the primary key. Does not add the module result to the database.
	*
	* @param mrId the primary key for the new module result
	* @return the new module result
	*/
	public com.liferay.lms.model.ModuleResult create(long mrId);

	/**
	* Removes the module result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param mrId the primary key of the module result
	* @return the module result that was removed
	* @throws com.liferay.lms.NoSuchModuleResultException if a module result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult remove(long mrId)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.ModuleResult updateImpl(
		com.liferay.lms.model.ModuleResult moduleResult, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the module result with the primary key or throws a {@link com.liferay.lms.NoSuchModuleResultException} if it could not be found.
	*
	* @param mrId the primary key of the module result
	* @return the module result
	* @throws com.liferay.lms.NoSuchModuleResultException if a module result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult findByPrimaryKey(long mrId)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the module result with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param mrId the primary key of the module result
	* @return the module result, or <code>null</code> if a module result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult fetchByPrimaryKey(long mrId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the module result where userId = &#63; and moduleId = &#63; or throws a {@link com.liferay.lms.NoSuchModuleResultException} if it could not be found.
	*
	* @param userId the user ID
	* @param moduleId the module ID
	* @return the matching module result
	* @throws com.liferay.lms.NoSuchModuleResultException if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult findBymu(long userId,
		long moduleId)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the module result where userId = &#63; and moduleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param moduleId the module ID
	* @return the matching module result, or <code>null</code> if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult fetchBymu(long userId,
		long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the module result where userId = &#63; and moduleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param moduleId the module ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching module result, or <code>null</code> if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult fetchBymu(long userId,
		long moduleId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the module results where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @return the matching module results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.ModuleResult> findBym(
		long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the module results where moduleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param moduleId the module ID
	* @param start the lower bound of the range of module results
	* @param end the upper bound of the range of module results (not inclusive)
	* @return the range of matching module results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.ModuleResult> findBym(
		long moduleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the module results where moduleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param moduleId the module ID
	* @param start the lower bound of the range of module results
	* @param end the upper bound of the range of module results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching module results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.ModuleResult> findBym(
		long moduleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first module result in the ordered set where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching module result
	* @throws com.liferay.lms.NoSuchModuleResultException if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult findBym_First(long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first module result in the ordered set where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching module result, or <code>null</code> if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult fetchBym_First(long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last module result in the ordered set where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching module result
	* @throws com.liferay.lms.NoSuchModuleResultException if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult findBym_Last(long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last module result in the ordered set where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching module result, or <code>null</code> if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult fetchBym_Last(long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the module results before and after the current module result in the ordered set where moduleId = &#63;.
	*
	* @param mrId the primary key of the current module result
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next module result
	* @throws com.liferay.lms.NoSuchModuleResultException if a module result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult[] findBym_PrevAndNext(long mrId,
		long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the module results where moduleId = &#63; and passed = &#63;.
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @return the matching module results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.ModuleResult> findBymp(
		long moduleId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the module results where moduleId = &#63; and passed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @param start the lower bound of the range of module results
	* @param end the upper bound of the range of module results (not inclusive)
	* @return the range of matching module results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.ModuleResult> findBymp(
		long moduleId, boolean passed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the module results where moduleId = &#63; and passed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @param start the lower bound of the range of module results
	* @param end the upper bound of the range of module results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching module results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.ModuleResult> findBymp(
		long moduleId, boolean passed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first module result in the ordered set where moduleId = &#63; and passed = &#63;.
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching module result
	* @throws com.liferay.lms.NoSuchModuleResultException if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult findBymp_First(long moduleId,
		boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first module result in the ordered set where moduleId = &#63; and passed = &#63;.
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching module result, or <code>null</code> if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult fetchBymp_First(long moduleId,
		boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last module result in the ordered set where moduleId = &#63; and passed = &#63;.
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching module result
	* @throws com.liferay.lms.NoSuchModuleResultException if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult findBymp_Last(long moduleId,
		boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last module result in the ordered set where moduleId = &#63; and passed = &#63;.
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching module result, or <code>null</code> if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult fetchBymp_Last(long moduleId,
		boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the module results before and after the current module result in the ordered set where moduleId = &#63; and passed = &#63;.
	*
	* @param mrId the primary key of the current module result
	* @param moduleId the module ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next module result
	* @throws com.liferay.lms.NoSuchModuleResultException if a module result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult[] findBymp_PrevAndNext(
		long mrId, long moduleId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the module results.
	*
	* @return the module results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.ModuleResult> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the module results.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of module results
	* @param end the upper bound of the range of module results (not inclusive)
	* @return the range of module results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.ModuleResult> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the module results.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of module results
	* @param end the upper bound of the range of module results (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of module results
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.ModuleResult> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the module result where userId = &#63; and moduleId = &#63; from the database.
	*
	* @param userId the user ID
	* @param moduleId the module ID
	* @return the module result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.ModuleResult removeBymu(long userId,
		long moduleId)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the module results where moduleId = &#63; from the database.
	*
	* @param moduleId the module ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeBym(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the module results where moduleId = &#63; and passed = &#63; from the database.
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @throws SystemException if a system exception occurred
	*/
	public void removeBymp(long moduleId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the module results from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of module results where userId = &#63; and moduleId = &#63;.
	*
	* @param userId the user ID
	* @param moduleId the module ID
	* @return the number of matching module results
	* @throws SystemException if a system exception occurred
	*/
	public int countBymu(long userId, long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of module results where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @return the number of matching module results
	* @throws SystemException if a system exception occurred
	*/
	public int countBym(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of module results where moduleId = &#63; and passed = &#63;.
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @return the number of matching module results
	* @throws SystemException if a system exception occurred
	*/
	public int countBymp(long moduleId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of module results.
	*
	* @return the number of module results
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}