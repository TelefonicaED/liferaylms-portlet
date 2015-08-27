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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the module result service. This utility wraps {@link ModuleResultPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see ModuleResultPersistence
 * @see ModuleResultPersistenceImpl
 * @generated
 */
public class ModuleResultUtil {
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
	public static void clearCache(ModuleResult moduleResult) {
		getPersistence().clearCache(moduleResult);
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
	public static List<ModuleResult> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ModuleResult> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ModuleResult> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ModuleResult update(ModuleResult moduleResult, boolean merge)
		throws SystemException {
		return getPersistence().update(moduleResult, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static ModuleResult update(ModuleResult moduleResult, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(moduleResult, merge, serviceContext);
	}

	/**
	* Caches the module result in the entity cache if it is enabled.
	*
	* @param moduleResult the module result
	*/
	public static void cacheResult(
		com.liferay.lms.model.ModuleResult moduleResult) {
		getPersistence().cacheResult(moduleResult);
	}

	/**
	* Caches the module results in the entity cache if it is enabled.
	*
	* @param moduleResults the module results
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.model.ModuleResult> moduleResults) {
		getPersistence().cacheResult(moduleResults);
	}

	/**
	* Creates a new module result with the primary key. Does not add the module result to the database.
	*
	* @param mrId the primary key for the new module result
	* @return the new module result
	*/
	public static com.liferay.lms.model.ModuleResult create(long mrId) {
		return getPersistence().create(mrId);
	}

	/**
	* Removes the module result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param mrId the primary key of the module result
	* @return the module result that was removed
	* @throws com.liferay.lms.NoSuchModuleResultException if a module result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult remove(long mrId)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(mrId);
	}

	public static com.liferay.lms.model.ModuleResult updateImpl(
		com.liferay.lms.model.ModuleResult moduleResult, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(moduleResult, merge);
	}

	/**
	* Returns the module result with the primary key or throws a {@link com.liferay.lms.NoSuchModuleResultException} if it could not be found.
	*
	* @param mrId the primary key of the module result
	* @return the module result
	* @throws com.liferay.lms.NoSuchModuleResultException if a module result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult findByPrimaryKey(long mrId)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(mrId);
	}

	/**
	* Returns the module result with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param mrId the primary key of the module result
	* @return the module result, or <code>null</code> if a module result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult fetchByPrimaryKey(
		long mrId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(mrId);
	}

	/**
	* Returns the module result where userId = &#63; and moduleId = &#63; or throws a {@link com.liferay.lms.NoSuchModuleResultException} if it could not be found.
	*
	* @param userId the user ID
	* @param moduleId the module ID
	* @return the matching module result
	* @throws com.liferay.lms.NoSuchModuleResultException if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult findBymu(long userId,
		long moduleId)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBymu(userId, moduleId);
	}

	/**
	* Returns the module result where userId = &#63; and moduleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param moduleId the module ID
	* @return the matching module result, or <code>null</code> if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult fetchBymu(long userId,
		long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchBymu(userId, moduleId);
	}

	/**
	* Returns the module result where userId = &#63; and moduleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param moduleId the module ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching module result, or <code>null</code> if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult fetchBymu(long userId,
		long moduleId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchBymu(userId, moduleId, retrieveFromCache);
	}

	/**
	* Returns all the module results where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @return the matching module results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.ModuleResult> findBym(
		long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBym(moduleId);
	}

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
	public static java.util.List<com.liferay.lms.model.ModuleResult> findBym(
		long moduleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBym(moduleId, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.ModuleResult> findBym(
		long moduleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBym(moduleId, start, end, orderByComparator);
	}

	/**
	* Returns the first module result in the ordered set where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching module result
	* @throws com.liferay.lms.NoSuchModuleResultException if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult findBym_First(
		long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBym_First(moduleId, orderByComparator);
	}

	/**
	* Returns the first module result in the ordered set where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching module result, or <code>null</code> if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult fetchBym_First(
		long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchBym_First(moduleId, orderByComparator);
	}

	/**
	* Returns the last module result in the ordered set where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching module result
	* @throws com.liferay.lms.NoSuchModuleResultException if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult findBym_Last(
		long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBym_Last(moduleId, orderByComparator);
	}

	/**
	* Returns the last module result in the ordered set where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching module result, or <code>null</code> if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult fetchBym_Last(
		long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchBym_Last(moduleId, orderByComparator);
	}

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
	public static com.liferay.lms.model.ModuleResult[] findBym_PrevAndNext(
		long mrId, long moduleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBym_PrevAndNext(mrId, moduleId, orderByComparator);
	}

	/**
	* Returns all the module results where moduleId = &#63; and passed = &#63;.
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @return the matching module results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.ModuleResult> findBymp(
		long moduleId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBymp(moduleId, passed);
	}

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
	public static java.util.List<com.liferay.lms.model.ModuleResult> findBymp(
		long moduleId, boolean passed, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBymp(moduleId, passed, start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.ModuleResult> findBymp(
		long moduleId, boolean passed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBymp(moduleId, passed, start, end, orderByComparator);
	}

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
	public static com.liferay.lms.model.ModuleResult findBymp_First(
		long moduleId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBymp_First(moduleId, passed, orderByComparator);
	}

	/**
	* Returns the first module result in the ordered set where moduleId = &#63; and passed = &#63;.
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching module result, or <code>null</code> if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult fetchBymp_First(
		long moduleId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchBymp_First(moduleId, passed, orderByComparator);
	}

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
	public static com.liferay.lms.model.ModuleResult findBymp_Last(
		long moduleId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBymp_Last(moduleId, passed, orderByComparator);
	}

	/**
	* Returns the last module result in the ordered set where moduleId = &#63; and passed = &#63;.
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching module result, or <code>null</code> if a matching module result could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult fetchBymp_Last(
		long moduleId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchBymp_Last(moduleId, passed, orderByComparator);
	}

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
	public static com.liferay.lms.model.ModuleResult[] findBymp_PrevAndNext(
		long mrId, long moduleId, boolean passed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBymp_PrevAndNext(mrId, moduleId, passed,
			orderByComparator);
	}

	/**
	* Returns all the module results.
	*
	* @return the module results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.ModuleResult> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.lms.model.ModuleResult> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.lms.model.ModuleResult> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes the module result where userId = &#63; and moduleId = &#63; from the database.
	*
	* @param userId the user ID
	* @param moduleId the module ID
	* @return the module result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.ModuleResult removeBymu(long userId,
		long moduleId)
		throws com.liferay.lms.NoSuchModuleResultException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeBymu(userId, moduleId);
	}

	/**
	* Removes all the module results where moduleId = &#63; from the database.
	*
	* @param moduleId the module ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeBym(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeBym(moduleId);
	}

	/**
	* Removes all the module results where moduleId = &#63; and passed = &#63; from the database.
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @throws SystemException if a system exception occurred
	*/
	public static void removeBymp(long moduleId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeBymp(moduleId, passed);
	}

	/**
	* Removes all the module results from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of module results where userId = &#63; and moduleId = &#63;.
	*
	* @param userId the user ID
	* @param moduleId the module ID
	* @return the number of matching module results
	* @throws SystemException if a system exception occurred
	*/
	public static int countBymu(long userId, long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBymu(userId, moduleId);
	}

	/**
	* Returns the number of module results where moduleId = &#63;.
	*
	* @param moduleId the module ID
	* @return the number of matching module results
	* @throws SystemException if a system exception occurred
	*/
	public static int countBym(long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBym(moduleId);
	}

	/**
	* Returns the number of module results where moduleId = &#63; and passed = &#63;.
	*
	* @param moduleId the module ID
	* @param passed the passed
	* @return the number of matching module results
	* @throws SystemException if a system exception occurred
	*/
	public static int countBymp(long moduleId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBymp(moduleId, passed);
	}

	/**
	* Returns the number of module results.
	*
	* @return the number of module results
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ModuleResultPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ModuleResultPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.service.ClpSerializer.getServletContextName(),
					ModuleResultPersistence.class.getName());

			ReferenceRegistry.registerReference(ModuleResultUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(ModuleResultPersistence persistence) {
	}

	private static ModuleResultPersistence _persistence;
}