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

import com.liferay.lms.NoSuchLearningActivityTryException;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.impl.LearningActivityTryImpl;
import com.liferay.lms.model.impl.LearningActivityTryModelImpl;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
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
 * The persistence implementation for the learning activity try service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LearningActivityTryPersistence
 * @see LearningActivityTryUtil
 * @generated
 */
public class LearningActivityTryPersistenceImpl extends BasePersistenceImpl<LearningActivityTry>
	implements LearningActivityTryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LearningActivityTryUtil} to access the learning activity try persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LearningActivityTryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			LearningActivityTryModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ACT = new FinderPath(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByact",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT = new FinderPath(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByact",
			new String[] { Long.class.getName() },
			LearningActivityTryModelImpl.ACTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACT = new FinderPath(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByact",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ACT_U = new FinderPath(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByact_u",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT_U = new FinderPath(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByact_u",
			new String[] { Long.class.getName(), Long.class.getName() },
			LearningActivityTryModelImpl.ACTID_COLUMN_BITMASK |
			LearningActivityTryModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACT_U = new FinderPath(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByact_u",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the learning activity try in the entity cache if it is enabled.
	 *
	 * @param learningActivityTry the learning activity try
	 */
	public void cacheResult(LearningActivityTry learningActivityTry) {
		EntityCacheUtil.putResult(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryImpl.class, learningActivityTry.getPrimaryKey(),
			learningActivityTry);

		learningActivityTry.resetOriginalValues();
	}

	/**
	 * Caches the learning activity tries in the entity cache if it is enabled.
	 *
	 * @param learningActivityTries the learning activity tries
	 */
	public void cacheResult(List<LearningActivityTry> learningActivityTries) {
		for (LearningActivityTry learningActivityTry : learningActivityTries) {
			if (EntityCacheUtil.getResult(
						LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
						LearningActivityTryImpl.class,
						learningActivityTry.getPrimaryKey()) == null) {
				cacheResult(learningActivityTry);
			}
			else {
				learningActivityTry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all learning activity tries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LearningActivityTryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LearningActivityTryImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the learning activity try.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LearningActivityTry learningActivityTry) {
		EntityCacheUtil.removeResult(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryImpl.class, learningActivityTry.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<LearningActivityTry> learningActivityTries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LearningActivityTry learningActivityTry : learningActivityTries) {
			EntityCacheUtil.removeResult(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
				LearningActivityTryImpl.class,
				learningActivityTry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new learning activity try with the primary key. Does not add the learning activity try to the database.
	 *
	 * @param latId the primary key for the new learning activity try
	 * @return the new learning activity try
	 */
	public LearningActivityTry create(long latId) {
		LearningActivityTry learningActivityTry = new LearningActivityTryImpl();

		learningActivityTry.setNew(true);
		learningActivityTry.setPrimaryKey(latId);

		String uuid = PortalUUIDUtil.generate();

		learningActivityTry.setUuid(uuid);

		return learningActivityTry;
	}

	/**
	 * Removes the learning activity try with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param latId the primary key of the learning activity try
	 * @return the learning activity try that was removed
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry remove(long latId)
		throws NoSuchLearningActivityTryException, SystemException {
		return remove(Long.valueOf(latId));
	}

	/**
	 * Removes the learning activity try with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the learning activity try
	 * @return the learning activity try that was removed
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivityTry remove(Serializable primaryKey)
		throws NoSuchLearningActivityTryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LearningActivityTry learningActivityTry = (LearningActivityTry)session.get(LearningActivityTryImpl.class,
					primaryKey);

			if (learningActivityTry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLearningActivityTryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(learningActivityTry);
		}
		catch (NoSuchLearningActivityTryException nsee) {
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
	protected LearningActivityTry removeImpl(
		LearningActivityTry learningActivityTry) throws SystemException {
		learningActivityTry = toUnwrappedModel(learningActivityTry);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, learningActivityTry);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(learningActivityTry);

		return learningActivityTry;
	}

	@Override
	public LearningActivityTry updateImpl(
		com.liferay.lms.model.LearningActivityTry learningActivityTry,
		boolean merge) throws SystemException {
		learningActivityTry = toUnwrappedModel(learningActivityTry);

		boolean isNew = learningActivityTry.isNew();

		LearningActivityTryModelImpl learningActivityTryModelImpl = (LearningActivityTryModelImpl)learningActivityTry;

		if (Validator.isNull(learningActivityTry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			learningActivityTry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, learningActivityTry, merge);

			learningActivityTry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !LearningActivityTryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((learningActivityTryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						learningActivityTryModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { learningActivityTryModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((learningActivityTryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityTryModelImpl.getOriginalActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACT, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT,
					args);

				args = new Object[] {
						Long.valueOf(learningActivityTryModelImpl.getActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACT, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT,
					args);
			}

			if ((learningActivityTryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityTryModelImpl.getOriginalActId()),
						Long.valueOf(learningActivityTryModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACT_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT_U,
					args);

				args = new Object[] {
						Long.valueOf(learningActivityTryModelImpl.getActId()),
						Long.valueOf(learningActivityTryModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACT_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT_U,
					args);
			}
		}

		EntityCacheUtil.putResult(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryImpl.class, learningActivityTry.getPrimaryKey(),
			learningActivityTry);

		return learningActivityTry;
	}

	protected LearningActivityTry toUnwrappedModel(
		LearningActivityTry learningActivityTry) {
		if (learningActivityTry instanceof LearningActivityTryImpl) {
			return learningActivityTry;
		}

		LearningActivityTryImpl learningActivityTryImpl = new LearningActivityTryImpl();

		learningActivityTryImpl.setNew(learningActivityTry.isNew());
		learningActivityTryImpl.setPrimaryKey(learningActivityTry.getPrimaryKey());

		learningActivityTryImpl.setUuid(learningActivityTry.getUuid());
		learningActivityTryImpl.setLatId(learningActivityTry.getLatId());
		learningActivityTryImpl.setActId(learningActivityTry.getActId());
		learningActivityTryImpl.setUserId(learningActivityTry.getUserId());
		learningActivityTryImpl.setStartDate(learningActivityTry.getStartDate());
		learningActivityTryImpl.setResult(learningActivityTry.getResult());
		learningActivityTryImpl.setEndDate(learningActivityTry.getEndDate());
		learningActivityTryImpl.setTryData(learningActivityTry.getTryData());
		learningActivityTryImpl.setTryResultData(learningActivityTry.getTryResultData());
		learningActivityTryImpl.setComments(learningActivityTry.getComments());

		return learningActivityTryImpl;
	}

	/**
	 * Returns the learning activity try with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the learning activity try
	 * @return the learning activity try
	 * @throws com.liferay.portal.NoSuchModelException if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivityTry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the learning activity try with the primary key or throws a {@link com.liferay.lms.NoSuchLearningActivityTryException} if it could not be found.
	 *
	 * @param latId the primary key of the learning activity try
	 * @return the learning activity try
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry findByPrimaryKey(long latId)
		throws NoSuchLearningActivityTryException, SystemException {
		LearningActivityTry learningActivityTry = fetchByPrimaryKey(latId);

		if (learningActivityTry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + latId);
			}

			throw new NoSuchLearningActivityTryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				latId);
		}

		return learningActivityTry;
	}

	/**
	 * Returns the learning activity try with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the learning activity try
	 * @return the learning activity try, or <code>null</code> if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivityTry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the learning activity try with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param latId the primary key of the learning activity try
	 * @return the learning activity try, or <code>null</code> if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry fetchByPrimaryKey(long latId)
		throws SystemException {
		LearningActivityTry learningActivityTry = (LearningActivityTry)EntityCacheUtil.getResult(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
				LearningActivityTryImpl.class, latId);

		if (learningActivityTry == _nullLearningActivityTry) {
			return null;
		}

		if (learningActivityTry == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				learningActivityTry = (LearningActivityTry)session.get(LearningActivityTryImpl.class,
						Long.valueOf(latId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (learningActivityTry != null) {
					cacheResult(learningActivityTry);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
						LearningActivityTryImpl.class, latId,
						_nullLearningActivityTry);
				}

				closeSession(session);
			}
		}

		return learningActivityTry;
	}

	/**
	 * Returns all the learning activity tries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTry> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activity tries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of learning activity tries
	 * @param end the upper bound of the range of learning activity tries (not inclusive)
	 * @return the range of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity tries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of learning activity tries
	 * @param end the upper bound of the range of learning activity tries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTry> findByUuid(String uuid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<LearningActivityTry> list = (List<LearningActivityTry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivityTry learningActivityTry : list) {
				if (!Validator.equals(uuid, learningActivityTry.getUuid())) {
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

			query.append(_SQL_SELECT_LEARNINGACTIVITYTRY_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LearningActivityTryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<LearningActivityTry>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first learning activity try in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity try
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryException, SystemException {
		LearningActivityTry learningActivityTry = fetchByUuid_First(uuid,
				orderByComparator);

		if (learningActivityTry != null) {
			return learningActivityTry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityTryException(msg.toString());
	}

	/**
	 * Returns the first learning activity try in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivityTry> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity try in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity try
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryException, SystemException {
		LearningActivityTry learningActivityTry = fetchByUuid_Last(uuid,
				orderByComparator);

		if (learningActivityTry != null) {
			return learningActivityTry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityTryException(msg.toString());
	}

	/**
	 * Returns the last learning activity try in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<LearningActivityTry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activity tries before and after the current learning activity try in the ordered set where uuid = &#63;.
	 *
	 * @param latId the primary key of the current learning activity try
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity try
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry[] findByUuid_PrevAndNext(long latId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryException, SystemException {
		LearningActivityTry learningActivityTry = findByPrimaryKey(latId);

		Session session = null;

		try {
			session = openSession();

			LearningActivityTry[] array = new LearningActivityTryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, learningActivityTry,
					uuid, orderByComparator, true);

			array[1] = learningActivityTry;

			array[2] = getByUuid_PrevAndNext(session, learningActivityTry,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LearningActivityTry getByUuid_PrevAndNext(Session session,
		LearningActivityTry learningActivityTry, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITYTRY_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

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
			query.append(LearningActivityTryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivityTry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivityTry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activity tries where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTry> findByact(long actId)
		throws SystemException {
		return findByact(actId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activity tries where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of learning activity tries
	 * @param end the upper bound of the range of learning activity tries (not inclusive)
	 * @return the range of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTry> findByact(long actId, int start, int end)
		throws SystemException {
		return findByact(actId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity tries where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of learning activity tries
	 * @param end the upper bound of the range of learning activity tries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTry> findByact(long actId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT;
			finderArgs = new Object[] { actId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ACT;
			finderArgs = new Object[] { actId, start, end, orderByComparator };
		}

		List<LearningActivityTry> list = (List<LearningActivityTry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivityTry learningActivityTry : list) {
				if ((actId != learningActivityTry.getActId())) {
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

			query.append(_SQL_SELECT_LEARNINGACTIVITYTRY_WHERE);

			query.append(_FINDER_COLUMN_ACT_ACTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LearningActivityTryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				list = (List<LearningActivityTry>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first learning activity try in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity try
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry findByact_First(long actId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryException, SystemException {
		LearningActivityTry learningActivityTry = fetchByact_First(actId,
				orderByComparator);

		if (learningActivityTry != null) {
			return learningActivityTry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityTryException(msg.toString());
	}

	/**
	 * Returns the first learning activity try in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry fetchByact_First(long actId,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivityTry> list = findByact(actId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity try in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity try
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry findByact_Last(long actId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryException, SystemException {
		LearningActivityTry learningActivityTry = fetchByact_Last(actId,
				orderByComparator);

		if (learningActivityTry != null) {
			return learningActivityTry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityTryException(msg.toString());
	}

	/**
	 * Returns the last learning activity try in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry fetchByact_Last(long actId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByact(actId);

		List<LearningActivityTry> list = findByact(actId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activity tries before and after the current learning activity try in the ordered set where actId = &#63;.
	 *
	 * @param latId the primary key of the current learning activity try
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity try
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry[] findByact_PrevAndNext(long latId, long actId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryException, SystemException {
		LearningActivityTry learningActivityTry = findByPrimaryKey(latId);

		Session session = null;

		try {
			session = openSession();

			LearningActivityTry[] array = new LearningActivityTryImpl[3];

			array[0] = getByact_PrevAndNext(session, learningActivityTry,
					actId, orderByComparator, true);

			array[1] = learningActivityTry;

			array[2] = getByact_PrevAndNext(session, learningActivityTry,
					actId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LearningActivityTry getByact_PrevAndNext(Session session,
		LearningActivityTry learningActivityTry, long actId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITYTRY_WHERE);

		query.append(_FINDER_COLUMN_ACT_ACTID_2);

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
			query.append(LearningActivityTryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(actId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivityTry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivityTry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activity tries where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @return the matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTry> findByact_u(long actId, long userId)
		throws SystemException {
		return findByact_u(actId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the learning activity tries where actId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of learning activity tries
	 * @param end the upper bound of the range of learning activity tries (not inclusive)
	 * @return the range of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTry> findByact_u(long actId, long userId,
		int start, int end) throws SystemException {
		return findByact_u(actId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity tries where actId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of learning activity tries
	 * @param end the upper bound of the range of learning activity tries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTry> findByact_u(long actId, long userId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT_U;
			finderArgs = new Object[] { actId, userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ACT_U;
			finderArgs = new Object[] {
					actId, userId,
					
					start, end, orderByComparator
				};
		}

		List<LearningActivityTry> list = (List<LearningActivityTry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivityTry learningActivityTry : list) {
				if ((actId != learningActivityTry.getActId()) ||
						(userId != learningActivityTry.getUserId())) {
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

			query.append(_SQL_SELECT_LEARNINGACTIVITYTRY_WHERE);

			query.append(_FINDER_COLUMN_ACT_U_ACTID_2);

			query.append(_FINDER_COLUMN_ACT_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LearningActivityTryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				qPos.add(userId);

				list = (List<LearningActivityTry>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first learning activity try in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity try
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry findByact_u_First(long actId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryException, SystemException {
		LearningActivityTry learningActivityTry = fetchByact_u_First(actId,
				userId, orderByComparator);

		if (learningActivityTry != null) {
			return learningActivityTry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityTryException(msg.toString());
	}

	/**
	 * Returns the first learning activity try in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry fetchByact_u_First(long actId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivityTry> list = findByact_u(actId, userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity try in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity try
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a matching learning activity try could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry findByact_u_Last(long actId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryException, SystemException {
		LearningActivityTry learningActivityTry = fetchByact_u_Last(actId,
				userId, orderByComparator);

		if (learningActivityTry != null) {
			return learningActivityTry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityTryException(msg.toString());
	}

	/**
	 * Returns the last learning activity try in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity try, or <code>null</code> if a matching learning activity try could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry fetchByact_u_Last(long actId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByact_u(actId, userId);

		List<LearningActivityTry> list = findByact_u(actId, userId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activity tries before and after the current learning activity try in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param latId the primary key of the current learning activity try
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity try
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry[] findByact_u_PrevAndNext(long latId,
		long actId, long userId, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryException, SystemException {
		LearningActivityTry learningActivityTry = findByPrimaryKey(latId);

		Session session = null;

		try {
			session = openSession();

			LearningActivityTry[] array = new LearningActivityTryImpl[3];

			array[0] = getByact_u_PrevAndNext(session, learningActivityTry,
					actId, userId, orderByComparator, true);

			array[1] = learningActivityTry;

			array[2] = getByact_u_PrevAndNext(session, learningActivityTry,
					actId, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LearningActivityTry getByact_u_PrevAndNext(Session session,
		LearningActivityTry learningActivityTry, long actId, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITYTRY_WHERE);

		query.append(_FINDER_COLUMN_ACT_U_ACTID_2);

		query.append(_FINDER_COLUMN_ACT_U_USERID_2);

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
			query.append(LearningActivityTryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(actId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivityTry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivityTry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activity tries.
	 *
	 * @return the learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activity tries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of learning activity tries
	 * @param end the upper bound of the range of learning activity tries (not inclusive)
	 * @return the range of learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity tries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of learning activity tries
	 * @param end the upper bound of the range of learning activity tries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTry> findAll(int start, int end,
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

		List<LearningActivityTry> list = (List<LearningActivityTry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LEARNINGACTIVITYTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LEARNINGACTIVITYTRY.concat(LearningActivityTryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<LearningActivityTry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LearningActivityTry>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the learning activity tries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (LearningActivityTry learningActivityTry : findByUuid(uuid)) {
			remove(learningActivityTry);
		}
	}

	/**
	 * Removes all the learning activity tries where actId = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByact(long actId) throws SystemException {
		for (LearningActivityTry learningActivityTry : findByact(actId)) {
			remove(learningActivityTry);
		}
	}

	/**
	 * Removes all the learning activity tries where actId = &#63; and userId = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByact_u(long actId, long userId)
		throws SystemException {
		for (LearningActivityTry learningActivityTry : findByact_u(actId, userId)) {
			remove(learningActivityTry);
		}
	}

	/**
	 * Removes all the learning activity tries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (LearningActivityTry learningActivityTry : findAll()) {
			remove(learningActivityTry);
		}
	}

	/**
	 * Returns the number of learning activity tries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LEARNINGACTIVITYTRY_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of learning activity tries where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the number of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByact(long actId) throws SystemException {
		Object[] finderArgs = new Object[] { actId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACT,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LEARNINGACTIVITYTRY_WHERE);

			query.append(_FINDER_COLUMN_ACT_ACTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACT, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of learning activity tries where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @return the number of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByact_u(long actId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { actId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACT_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LEARNINGACTIVITYTRY_WHERE);

			query.append(_FINDER_COLUMN_ACT_U_ACTID_2);

			query.append(_FINDER_COLUMN_ACT_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACT_U,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of learning activity tries.
	 *
	 * @return the number of learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LEARNINGACTIVITYTRY);

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
	 * Initializes the learning activity try persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.LearningActivityTry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LearningActivityTry>> listenersList = new ArrayList<ModelListener<LearningActivityTry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LearningActivityTry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(LearningActivityTryImpl.class.getName());
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
	private static final String _SQL_SELECT_LEARNINGACTIVITYTRY = "SELECT learningActivityTry FROM LearningActivityTry learningActivityTry";
	private static final String _SQL_SELECT_LEARNINGACTIVITYTRY_WHERE = "SELECT learningActivityTry FROM LearningActivityTry learningActivityTry WHERE ";
	private static final String _SQL_COUNT_LEARNINGACTIVITYTRY = "SELECT COUNT(learningActivityTry) FROM LearningActivityTry learningActivityTry";
	private static final String _SQL_COUNT_LEARNINGACTIVITYTRY_WHERE = "SELECT COUNT(learningActivityTry) FROM LearningActivityTry learningActivityTry WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "learningActivityTry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "learningActivityTry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(learningActivityTry.uuid IS NULL OR learningActivityTry.uuid = ?)";
	private static final String _FINDER_COLUMN_ACT_ACTID_2 = "learningActivityTry.actId = ?";
	private static final String _FINDER_COLUMN_ACT_U_ACTID_2 = "learningActivityTry.actId = ? AND ";
	private static final String _FINDER_COLUMN_ACT_U_USERID_2 = "learningActivityTry.userId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "learningActivityTry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LearningActivityTry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LearningActivityTry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(LearningActivityTryPersistenceImpl.class);
	private static LearningActivityTry _nullLearningActivityTry = new LearningActivityTryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LearningActivityTry> toCacheModel() {
				return _nullLearningActivityTryCacheModel;
			}
		};

	private static CacheModel<LearningActivityTry> _nullLearningActivityTryCacheModel =
		new CacheModel<LearningActivityTry>() {
			public LearningActivityTry toEntityModel() {
				return _nullLearningActivityTry;
			}
		};
}