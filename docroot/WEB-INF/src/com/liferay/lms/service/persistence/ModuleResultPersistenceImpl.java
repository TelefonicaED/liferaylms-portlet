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

import com.liferay.lms.NoSuchModuleResultException;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.model.impl.ModuleResultImpl;
import com.liferay.lms.model.impl.ModuleResultModelImpl;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the module result service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see ModuleResultPersistence
 * @see ModuleResultUtil
 * @generated
 */
public class ModuleResultPersistenceImpl extends BasePersistenceImpl<ModuleResult>
	implements ModuleResultPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ModuleResultUtil} to access the module result persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ModuleResultImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_MU = new FinderPath(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultModelImpl.FINDER_CACHE_ENABLED, ModuleResultImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchBymu",
			new String[] { Long.class.getName(), Long.class.getName() },
			ModuleResultModelImpl.USERID_COLUMN_BITMASK |
			ModuleResultModelImpl.MODULEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_MU = new FinderPath(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBymu",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_M = new FinderPath(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultModelImpl.FINDER_CACHE_ENABLED, ModuleResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBym",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_M = new FinderPath(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultModelImpl.FINDER_CACHE_ENABLED, ModuleResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBym",
			new String[] { Long.class.getName() },
			ModuleResultModelImpl.MODULEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_M = new FinderPath(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBym",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_MP = new FinderPath(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultModelImpl.FINDER_CACHE_ENABLED, ModuleResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBymp",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MP = new FinderPath(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultModelImpl.FINDER_CACHE_ENABLED, ModuleResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBymp",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			ModuleResultModelImpl.MODULEID_COLUMN_BITMASK |
			ModuleResultModelImpl.PASSED_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_MP = new FinderPath(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBymp",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultModelImpl.FINDER_CACHE_ENABLED, ModuleResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultModelImpl.FINDER_CACHE_ENABLED, ModuleResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the module result in the entity cache if it is enabled.
	 *
	 * @param moduleResult the module result
	 */
	public void cacheResult(ModuleResult moduleResult) {
		EntityCacheUtil.putResult(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultImpl.class, moduleResult.getPrimaryKey(), moduleResult);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MU,
			new Object[] {
				Long.valueOf(moduleResult.getUserId()),
				Long.valueOf(moduleResult.getModuleId())
			}, moduleResult);

		moduleResult.resetOriginalValues();
	}

	/**
	 * Caches the module results in the entity cache if it is enabled.
	 *
	 * @param moduleResults the module results
	 */
	public void cacheResult(List<ModuleResult> moduleResults) {
		for (ModuleResult moduleResult : moduleResults) {
			if (EntityCacheUtil.getResult(
						ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
						ModuleResultImpl.class, moduleResult.getPrimaryKey()) == null) {
				cacheResult(moduleResult);
			}
			else {
				moduleResult.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all module results.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ModuleResultImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ModuleResultImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the module result.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ModuleResult moduleResult) {
		EntityCacheUtil.removeResult(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultImpl.class, moduleResult.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(moduleResult);
	}

	@Override
	public void clearCache(List<ModuleResult> moduleResults) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ModuleResult moduleResult : moduleResults) {
			EntityCacheUtil.removeResult(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
				ModuleResultImpl.class, moduleResult.getPrimaryKey());

			clearUniqueFindersCache(moduleResult);
		}
	}

	protected void clearUniqueFindersCache(ModuleResult moduleResult) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MU,
			new Object[] {
				Long.valueOf(moduleResult.getUserId()),
				Long.valueOf(moduleResult.getModuleId())
			});
	}

	/**
	 * Creates a new module result with the primary key. Does not add the module result to the database.
	 *
	 * @param mrId the primary key for the new module result
	 * @return the new module result
	 */
	public ModuleResult create(long mrId) {
		ModuleResult moduleResult = new ModuleResultImpl();

		moduleResult.setNew(true);
		moduleResult.setPrimaryKey(mrId);

		return moduleResult;
	}

	/**
	 * Removes the module result with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mrId the primary key of the module result
	 * @return the module result that was removed
	 * @throws com.liferay.lms.NoSuchModuleResultException if a module result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ModuleResult remove(long mrId)
		throws NoSuchModuleResultException, SystemException {
		return remove(Long.valueOf(mrId));
	}

	/**
	 * Removes the module result with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the module result
	 * @return the module result that was removed
	 * @throws com.liferay.lms.NoSuchModuleResultException if a module result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ModuleResult remove(Serializable primaryKey)
		throws NoSuchModuleResultException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ModuleResult moduleResult = (ModuleResult)session.get(ModuleResultImpl.class,
					primaryKey);

			if (moduleResult == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchModuleResultException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(moduleResult);
		}
		catch (NoSuchModuleResultException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ModuleResult removeImpl(ModuleResult moduleResult)
		throws SystemException {
		moduleResult = toUnwrappedModel(moduleResult);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, moduleResult);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(moduleResult);

		return moduleResult;
	}

	@Override
	public ModuleResult updateImpl(
		com.liferay.lms.model.ModuleResult moduleResult, boolean merge)
		throws SystemException {
		moduleResult = toUnwrappedModel(moduleResult);

		boolean isNew = moduleResult.isNew();

		ModuleResultModelImpl moduleResultModelImpl = (ModuleResultModelImpl)moduleResult;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, moduleResult, merge);

			moduleResult.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ModuleResultModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((moduleResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_M.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(moduleResultModelImpl.getOriginalModuleId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_M, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_M,
					args);

				args = new Object[] {
						Long.valueOf(moduleResultModelImpl.getModuleId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_M, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_M,
					args);
			}

			if ((moduleResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MP.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(moduleResultModelImpl.getOriginalModuleId()),
						Boolean.valueOf(moduleResultModelImpl.getOriginalPassed())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_MP, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MP,
					args);

				args = new Object[] {
						Long.valueOf(moduleResultModelImpl.getModuleId()),
						Boolean.valueOf(moduleResultModelImpl.getPassed())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_MP, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MP,
					args);
			}
		}

		EntityCacheUtil.putResult(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
			ModuleResultImpl.class, moduleResult.getPrimaryKey(), moduleResult);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MU,
				new Object[] {
					Long.valueOf(moduleResult.getUserId()),
					Long.valueOf(moduleResult.getModuleId())
				}, moduleResult);
		}
		else {
			if ((moduleResultModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_MU.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(moduleResultModelImpl.getOriginalUserId()),
						Long.valueOf(moduleResultModelImpl.getOriginalModuleId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_MU, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MU, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MU,
					new Object[] {
						Long.valueOf(moduleResult.getUserId()),
						Long.valueOf(moduleResult.getModuleId())
					}, moduleResult);
			}
		}

		return moduleResult;
	}

	protected ModuleResult toUnwrappedModel(ModuleResult moduleResult) {
		if (moduleResult instanceof ModuleResultImpl) {
			return moduleResult;
		}

		ModuleResultImpl moduleResultImpl = new ModuleResultImpl();

		moduleResultImpl.setNew(moduleResult.isNew());
		moduleResultImpl.setPrimaryKey(moduleResult.getPrimaryKey());

		moduleResultImpl.setModuleId(moduleResult.getModuleId());
		moduleResultImpl.setResult(moduleResult.getResult());
		moduleResultImpl.setComments(moduleResult.getComments());
		moduleResultImpl.setUserId(moduleResult.getUserId());
		moduleResultImpl.setStartDate(moduleResult.getStartDate());
		moduleResultImpl.setPassed(moduleResult.isPassed());
		moduleResultImpl.setMrId(moduleResult.getMrId());
		moduleResultImpl.setPassedDate(moduleResult.getPassedDate());

		return moduleResultImpl;
	}

	/**
	 * Returns the module result with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the module result
	 * @return the module result
	 * @throws com.liferay.portal.NoSuchModelException if a module result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ModuleResult findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the module result with the primary key or throws a {@link com.liferay.lms.NoSuchModuleResultException} if it could not be found.
	 *
	 * @param mrId the primary key of the module result
	 * @return the module result
	 * @throws com.liferay.lms.NoSuchModuleResultException if a module result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ModuleResult findByPrimaryKey(long mrId)
		throws NoSuchModuleResultException, SystemException {
		ModuleResult moduleResult = fetchByPrimaryKey(mrId);

		if (moduleResult == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + mrId);
			}

			throw new NoSuchModuleResultException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				mrId);
		}

		return moduleResult;
	}

	/**
	 * Returns the module result with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the module result
	 * @return the module result, or <code>null</code> if a module result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ModuleResult fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the module result with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mrId the primary key of the module result
	 * @return the module result, or <code>null</code> if a module result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ModuleResult fetchByPrimaryKey(long mrId) throws SystemException {
		ModuleResult moduleResult = (ModuleResult)EntityCacheUtil.getResult(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
				ModuleResultImpl.class, mrId);

		if (moduleResult == _nullModuleResult) {
			return null;
		}

		if (moduleResult == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				moduleResult = (ModuleResult)session.get(ModuleResultImpl.class,
						Long.valueOf(mrId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (moduleResult != null) {
					cacheResult(moduleResult);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ModuleResultModelImpl.ENTITY_CACHE_ENABLED,
						ModuleResultImpl.class, mrId, _nullModuleResult);
				}

				closeSession(session);
			}
		}

		return moduleResult;
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
	public ModuleResult findBymu(long userId, long moduleId)
		throws NoSuchModuleResultException, SystemException {
		ModuleResult moduleResult = fetchBymu(userId, moduleId);

		if (moduleResult == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", moduleId=");
			msg.append(moduleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchModuleResultException(msg.toString());
		}

		return moduleResult;
	}

	/**
	 * Returns the module result where userId = &#63; and moduleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param moduleId the module ID
	 * @return the matching module result, or <code>null</code> if a matching module result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ModuleResult fetchBymu(long userId, long moduleId)
		throws SystemException {
		return fetchBymu(userId, moduleId, true);
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
	public ModuleResult fetchBymu(long userId, long moduleId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { userId, moduleId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_MU,
					finderArgs, this);
		}

		if (result instanceof ModuleResult) {
			ModuleResult moduleResult = (ModuleResult)result;

			if ((userId != moduleResult.getUserId()) ||
					(moduleId != moduleResult.getModuleId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MODULERESULT_WHERE);

			query.append(_FINDER_COLUMN_MU_USERID_2);

			query.append(_FINDER_COLUMN_MU_MODULEID_2);

			query.append(ModuleResultModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(moduleId);

				List<ModuleResult> list = q.list();

				result = list;

				ModuleResult moduleResult = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MU,
						finderArgs, list);
				}
				else {
					moduleResult = list.get(0);

					cacheResult(moduleResult);

					if ((moduleResult.getUserId() != userId) ||
							(moduleResult.getModuleId() != moduleId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MU,
							finderArgs, moduleResult);
					}
				}

				return moduleResult;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MU,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ModuleResult)result;
			}
		}
	}

	/**
	 * Returns all the module results where moduleId = &#63;.
	 *
	 * @param moduleId the module ID
	 * @return the matching module results
	 * @throws SystemException if a system exception occurred
	 */
	public List<ModuleResult> findBym(long moduleId) throws SystemException {
		return findBym(moduleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	public List<ModuleResult> findBym(long moduleId, int start, int end)
		throws SystemException {
		return findBym(moduleId, start, end, null);
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
	public List<ModuleResult> findBym(long moduleId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_M;
			finderArgs = new Object[] { moduleId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_M;
			finderArgs = new Object[] { moduleId, start, end, orderByComparator };
		}

		List<ModuleResult> list = (List<ModuleResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ModuleResult moduleResult : list) {
				if ((moduleId != moduleResult.getModuleId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_MODULERESULT_WHERE);

			query.append(_FINDER_COLUMN_M_MODULEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ModuleResultModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(moduleId);

				list = (List<ModuleResult>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
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
	public ModuleResult findBym_First(long moduleId,
		OrderByComparator orderByComparator)
		throws NoSuchModuleResultException, SystemException {
		ModuleResult moduleResult = fetchBym_First(moduleId, orderByComparator);

		if (moduleResult != null) {
			return moduleResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("moduleId=");
		msg.append(moduleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchModuleResultException(msg.toString());
	}

	/**
	 * Returns the first module result in the ordered set where moduleId = &#63;.
	 *
	 * @param moduleId the module ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching module result, or <code>null</code> if a matching module result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ModuleResult fetchBym_First(long moduleId,
		OrderByComparator orderByComparator) throws SystemException {
		List<ModuleResult> list = findBym(moduleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	public ModuleResult findBym_Last(long moduleId,
		OrderByComparator orderByComparator)
		throws NoSuchModuleResultException, SystemException {
		ModuleResult moduleResult = fetchBym_Last(moduleId, orderByComparator);

		if (moduleResult != null) {
			return moduleResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("moduleId=");
		msg.append(moduleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchModuleResultException(msg.toString());
	}

	/**
	 * Returns the last module result in the ordered set where moduleId = &#63;.
	 *
	 * @param moduleId the module ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching module result, or <code>null</code> if a matching module result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ModuleResult fetchBym_Last(long moduleId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countBym(moduleId);

		List<ModuleResult> list = findBym(moduleId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	public ModuleResult[] findBym_PrevAndNext(long mrId, long moduleId,
		OrderByComparator orderByComparator)
		throws NoSuchModuleResultException, SystemException {
		ModuleResult moduleResult = findByPrimaryKey(mrId);

		Session session = null;

		try {
			session = openSession();

			ModuleResult[] array = new ModuleResultImpl[3];

			array[0] = getBym_PrevAndNext(session, moduleResult, moduleId,
					orderByComparator, true);

			array[1] = moduleResult;

			array[2] = getBym_PrevAndNext(session, moduleResult, moduleId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ModuleResult getBym_PrevAndNext(Session session,
		ModuleResult moduleResult, long moduleId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MODULERESULT_WHERE);

		query.append(_FINDER_COLUMN_M_MODULEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(ModuleResultModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(moduleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(moduleResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ModuleResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the module results where moduleId = &#63; and passed = &#63;.
	 *
	 * @param moduleId the module ID
	 * @param passed the passed
	 * @return the matching module results
	 * @throws SystemException if a system exception occurred
	 */
	public List<ModuleResult> findBymp(long moduleId, boolean passed)
		throws SystemException {
		return findBymp(moduleId, passed, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
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
	public List<ModuleResult> findBymp(long moduleId, boolean passed,
		int start, int end) throws SystemException {
		return findBymp(moduleId, passed, start, end, null);
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
	public List<ModuleResult> findBymp(long moduleId, boolean passed,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MP;
			finderArgs = new Object[] { moduleId, passed };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_MP;
			finderArgs = new Object[] {
					moduleId, passed,
					
					start, end, orderByComparator
				};
		}

		List<ModuleResult> list = (List<ModuleResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ModuleResult moduleResult : list) {
				if ((moduleId != moduleResult.getModuleId()) ||
						(passed != moduleResult.getPassed())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_MODULERESULT_WHERE);

			query.append(_FINDER_COLUMN_MP_MODULEID_2);

			query.append(_FINDER_COLUMN_MP_PASSED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ModuleResultModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(moduleId);

				qPos.add(passed);

				list = (List<ModuleResult>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
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
	public ModuleResult findBymp_First(long moduleId, boolean passed,
		OrderByComparator orderByComparator)
		throws NoSuchModuleResultException, SystemException {
		ModuleResult moduleResult = fetchBymp_First(moduleId, passed,
				orderByComparator);

		if (moduleResult != null) {
			return moduleResult;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("moduleId=");
		msg.append(moduleId);

		msg.append(", passed=");
		msg.append(passed);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchModuleResultException(msg.toString());
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
	public ModuleResult fetchBymp_First(long moduleId, boolean passed,
		OrderByComparator orderByComparator) throws SystemException {
		List<ModuleResult> list = findBymp(moduleId, passed, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	public ModuleResult findBymp_Last(long moduleId, boolean passed,
		OrderByComparator orderByComparator)
		throws NoSuchModuleResultException, SystemException {
		ModuleResult moduleResult = fetchBymp_Last(moduleId, passed,
				orderByComparator);

		if (moduleResult != null) {
			return moduleResult;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("moduleId=");
		msg.append(moduleId);

		msg.append(", passed=");
		msg.append(passed);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchModuleResultException(msg.toString());
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
	public ModuleResult fetchBymp_Last(long moduleId, boolean passed,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countBymp(moduleId, passed);

		List<ModuleResult> list = findBymp(moduleId, passed, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	public ModuleResult[] findBymp_PrevAndNext(long mrId, long moduleId,
		boolean passed, OrderByComparator orderByComparator)
		throws NoSuchModuleResultException, SystemException {
		ModuleResult moduleResult = findByPrimaryKey(mrId);

		Session session = null;

		try {
			session = openSession();

			ModuleResult[] array = new ModuleResultImpl[3];

			array[0] = getBymp_PrevAndNext(session, moduleResult, moduleId,
					passed, orderByComparator, true);

			array[1] = moduleResult;

			array[2] = getBymp_PrevAndNext(session, moduleResult, moduleId,
					passed, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ModuleResult getBymp_PrevAndNext(Session session,
		ModuleResult moduleResult, long moduleId, boolean passed,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MODULERESULT_WHERE);

		query.append(_FINDER_COLUMN_MP_MODULEID_2);

		query.append(_FINDER_COLUMN_MP_PASSED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(ModuleResultModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(moduleId);

		qPos.add(passed);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(moduleResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ModuleResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the module results.
	 *
	 * @return the module results
	 * @throws SystemException if a system exception occurred
	 */
	public List<ModuleResult> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	public List<ModuleResult> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
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
	public List<ModuleResult> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<ModuleResult> list = (List<ModuleResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MODULERESULT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MODULERESULT.concat(ModuleResultModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ModuleResult>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ModuleResult>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes the module result where userId = &#63; and moduleId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param moduleId the module ID
	 * @return the module result that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public ModuleResult removeBymu(long userId, long moduleId)
		throws NoSuchModuleResultException, SystemException {
		ModuleResult moduleResult = findBymu(userId, moduleId);

		return remove(moduleResult);
	}

	/**
	 * Removes all the module results where moduleId = &#63; from the database.
	 *
	 * @param moduleId the module ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeBym(long moduleId) throws SystemException {
		for (ModuleResult moduleResult : findBym(moduleId)) {
			remove(moduleResult);
		}
	}

	/**
	 * Removes all the module results where moduleId = &#63; and passed = &#63; from the database.
	 *
	 * @param moduleId the module ID
	 * @param passed the passed
	 * @throws SystemException if a system exception occurred
	 */
	public void removeBymp(long moduleId, boolean passed)
		throws SystemException {
		for (ModuleResult moduleResult : findBymp(moduleId, passed)) {
			remove(moduleResult);
		}
	}

	/**
	 * Removes all the module results from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ModuleResult moduleResult : findAll()) {
			remove(moduleResult);
		}
	}

	/**
	 * Returns the number of module results where userId = &#63; and moduleId = &#63;.
	 *
	 * @param userId the user ID
	 * @param moduleId the module ID
	 * @return the number of matching module results
	 * @throws SystemException if a system exception occurred
	 */
	public int countBymu(long userId, long moduleId) throws SystemException {
		Object[] finderArgs = new Object[] { userId, moduleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_MU,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MODULERESULT_WHERE);

			query.append(_FINDER_COLUMN_MU_USERID_2);

			query.append(_FINDER_COLUMN_MU_MODULEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(moduleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_MU, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of module results where moduleId = &#63;.
	 *
	 * @param moduleId the module ID
	 * @return the number of matching module results
	 * @throws SystemException if a system exception occurred
	 */
	public int countBym(long moduleId) throws SystemException {
		Object[] finderArgs = new Object[] { moduleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_M,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MODULERESULT_WHERE);

			query.append(_FINDER_COLUMN_M_MODULEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(moduleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_M, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of module results where moduleId = &#63; and passed = &#63;.
	 *
	 * @param moduleId the module ID
	 * @param passed the passed
	 * @return the number of matching module results
	 * @throws SystemException if a system exception occurred
	 */
	public int countBymp(long moduleId, boolean passed)
		throws SystemException {
		Object[] finderArgs = new Object[] { moduleId, passed };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_MP,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MODULERESULT_WHERE);

			query.append(_FINDER_COLUMN_MP_MODULEID_2);

			query.append(_FINDER_COLUMN_MP_PASSED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(moduleId);

				qPos.add(passed);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_MP, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of module results.
	 *
	 * @return the number of module results
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MODULERESULT);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the module result persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.ModuleResult")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ModuleResult>> listenersList = new ArrayList<ModelListener<ModuleResult>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ModuleResult>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ModuleResultImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AuditEntryPersistence.class)
	protected AuditEntryPersistence auditEntryPersistence;
	@BeanReference(type = CheckP2pMailingPersistence.class)
	protected CheckP2pMailingPersistence checkP2pMailingPersistence;
	@BeanReference(type = CompetencePersistence.class)
	protected CompetencePersistence competencePersistence;
	@BeanReference(type = CoursePersistence.class)
	protected CoursePersistence coursePersistence;
	@BeanReference(type = CourseCompetencePersistence.class)
	protected CourseCompetencePersistence courseCompetencePersistence;
	@BeanReference(type = CourseResultPersistence.class)
	protected CourseResultPersistence courseResultPersistence;
	@BeanReference(type = LearningActivityPersistence.class)
	protected LearningActivityPersistence learningActivityPersistence;
	@BeanReference(type = LearningActivityResultPersistence.class)
	protected LearningActivityResultPersistence learningActivityResultPersistence;
	@BeanReference(type = LearningActivityTryPersistence.class)
	protected LearningActivityTryPersistence learningActivityTryPersistence;
	@BeanReference(type = LmsPrefsPersistence.class)
	protected LmsPrefsPersistence lmsPrefsPersistence;
	@BeanReference(type = ModulePersistence.class)
	protected ModulePersistence modulePersistence;
	@BeanReference(type = ModuleResultPersistence.class)
	protected ModuleResultPersistence moduleResultPersistence;
	@BeanReference(type = P2pActivityPersistence.class)
	protected P2pActivityPersistence p2pActivityPersistence;
	@BeanReference(type = P2pActivityCorrectionsPersistence.class)
	protected P2pActivityCorrectionsPersistence p2pActivityCorrectionsPersistence;
	@BeanReference(type = SCORMContentPersistence.class)
	protected SCORMContentPersistence scormContentPersistence;
	@BeanReference(type = SurveyResultPersistence.class)
	protected SurveyResultPersistence surveyResultPersistence;
	@BeanReference(type = TestAnswerPersistence.class)
	protected TestAnswerPersistence testAnswerPersistence;
	@BeanReference(type = TestQuestionPersistence.class)
	protected TestQuestionPersistence testQuestionPersistence;
	@BeanReference(type = UserCompetencePersistence.class)
	protected UserCompetencePersistence userCompetencePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_MODULERESULT = "SELECT moduleResult FROM ModuleResult moduleResult";
	private static final String _SQL_SELECT_MODULERESULT_WHERE = "SELECT moduleResult FROM ModuleResult moduleResult WHERE ";
	private static final String _SQL_COUNT_MODULERESULT = "SELECT COUNT(moduleResult) FROM ModuleResult moduleResult";
	private static final String _SQL_COUNT_MODULERESULT_WHERE = "SELECT COUNT(moduleResult) FROM ModuleResult moduleResult WHERE ";
	private static final String _FINDER_COLUMN_MU_USERID_2 = "moduleResult.userId = ? AND ";
	private static final String _FINDER_COLUMN_MU_MODULEID_2 = "moduleResult.moduleId = ?";
	private static final String _FINDER_COLUMN_M_MODULEID_2 = "moduleResult.moduleId = ?";
	private static final String _FINDER_COLUMN_MP_MODULEID_2 = "moduleResult.moduleId = ? AND ";
	private static final String _FINDER_COLUMN_MP_PASSED_2 = "moduleResult.passed = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "moduleResult.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ModuleResult exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ModuleResult exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(ModuleResultPersistenceImpl.class);
	private static ModuleResult _nullModuleResult = new ModuleResultImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ModuleResult> toCacheModel() {
				return _nullModuleResultCacheModel;
			}
		};

	private static CacheModel<ModuleResult> _nullModuleResultCacheModel = new CacheModel<ModuleResult>() {
			public ModuleResult toEntityModel() {
				return _nullModuleResult;
			}
		};
}