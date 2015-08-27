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

import com.liferay.lms.NoSuchP2pActivityException;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.impl.P2pActivityImpl;
import com.liferay.lms.model.impl.P2pActivityModelImpl;

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
 * The persistence implementation for the p2p activity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see P2pActivityPersistence
 * @see P2pActivityUtil
 * @generated
 */
public class P2pActivityPersistenceImpl extends BasePersistenceImpl<P2pActivity>
	implements P2pActivityPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link P2pActivityUtil} to access the p2p activity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = P2pActivityImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityModelImpl.FINDER_CACHE_ENABLED, P2pActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityModelImpl.FINDER_CACHE_ENABLED, P2pActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			P2pActivityModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTIDANDUSERID =
		new FinderPath(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityModelImpl.FINDER_CACHE_ENABLED, P2pActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByActIdAndUserId",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDANDUSERID =
		new FinderPath(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityModelImpl.FINDER_CACHE_ENABLED, P2pActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByActIdAndUserId",
			new String[] { Long.class.getName(), Long.class.getName() },
			P2pActivityModelImpl.ACTID_COLUMN_BITMASK |
			P2pActivityModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTIDANDUSERID = new FinderPath(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByActIdAndUserId",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTID = new FinderPath(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityModelImpl.FINDER_CACHE_ENABLED, P2pActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByActId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTID = new FinderPath(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityModelImpl.FINDER_CACHE_ENABLED, P2pActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByActId",
			new String[] { Long.class.getName() },
			P2pActivityModelImpl.ACTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTID = new FinderPath(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByActId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityModelImpl.FINDER_CACHE_ENABLED, P2pActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityModelImpl.FINDER_CACHE_ENABLED, P2pActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the p2p activity in the entity cache if it is enabled.
	 *
	 * @param p2pActivity the p2p activity
	 */
	public void cacheResult(P2pActivity p2pActivity) {
		EntityCacheUtil.putResult(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityImpl.class, p2pActivity.getPrimaryKey(), p2pActivity);

		p2pActivity.resetOriginalValues();
	}

	/**
	 * Caches the p2p activities in the entity cache if it is enabled.
	 *
	 * @param p2pActivities the p2p activities
	 */
	public void cacheResult(List<P2pActivity> p2pActivities) {
		for (P2pActivity p2pActivity : p2pActivities) {
			if (EntityCacheUtil.getResult(
						P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
						P2pActivityImpl.class, p2pActivity.getPrimaryKey()) == null) {
				cacheResult(p2pActivity);
			}
			else {
				p2pActivity.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all p2p activities.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(P2pActivityImpl.class.getName());
		}

		EntityCacheUtil.clearCache(P2pActivityImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the p2p activity.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(P2pActivity p2pActivity) {
		EntityCacheUtil.removeResult(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityImpl.class, p2pActivity.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<P2pActivity> p2pActivities) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (P2pActivity p2pActivity : p2pActivities) {
			EntityCacheUtil.removeResult(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
				P2pActivityImpl.class, p2pActivity.getPrimaryKey());
		}
	}

	/**
	 * Creates a new p2p activity with the primary key. Does not add the p2p activity to the database.
	 *
	 * @param p2pActivityId the primary key for the new p2p activity
	 * @return the new p2p activity
	 */
	public P2pActivity create(long p2pActivityId) {
		P2pActivity p2pActivity = new P2pActivityImpl();

		p2pActivity.setNew(true);
		p2pActivity.setPrimaryKey(p2pActivityId);

		String uuid = PortalUUIDUtil.generate();

		p2pActivity.setUuid(uuid);

		return p2pActivity;
	}

	/**
	 * Removes the p2p activity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param p2pActivityId the primary key of the p2p activity
	 * @return the p2p activity that was removed
	 * @throws com.liferay.lms.NoSuchP2pActivityException if a p2p activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity remove(long p2pActivityId)
		throws NoSuchP2pActivityException, SystemException {
		return remove(Long.valueOf(p2pActivityId));
	}

	/**
	 * Removes the p2p activity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the p2p activity
	 * @return the p2p activity that was removed
	 * @throws com.liferay.lms.NoSuchP2pActivityException if a p2p activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public P2pActivity remove(Serializable primaryKey)
		throws NoSuchP2pActivityException, SystemException {
		Session session = null;

		try {
			session = openSession();

			P2pActivity p2pActivity = (P2pActivity)session.get(P2pActivityImpl.class,
					primaryKey);

			if (p2pActivity == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchP2pActivityException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(p2pActivity);
		}
		catch (NoSuchP2pActivityException nsee) {
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
	protected P2pActivity removeImpl(P2pActivity p2pActivity)
		throws SystemException {
		p2pActivity = toUnwrappedModel(p2pActivity);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, p2pActivity);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(p2pActivity);

		return p2pActivity;
	}

	@Override
	public P2pActivity updateImpl(
		com.liferay.lms.model.P2pActivity p2pActivity, boolean merge)
		throws SystemException {
		p2pActivity = toUnwrappedModel(p2pActivity);

		boolean isNew = p2pActivity.isNew();

		P2pActivityModelImpl p2pActivityModelImpl = (P2pActivityModelImpl)p2pActivity;

		if (Validator.isNull(p2pActivity.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			p2pActivity.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, p2pActivity, merge);

			p2pActivity.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !P2pActivityModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((p2pActivityModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						p2pActivityModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { p2pActivityModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((p2pActivityModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDANDUSERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(p2pActivityModelImpl.getOriginalActId()),
						Long.valueOf(p2pActivityModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTIDANDUSERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDANDUSERID,
					args);

				args = new Object[] {
						Long.valueOf(p2pActivityModelImpl.getActId()),
						Long.valueOf(p2pActivityModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTIDANDUSERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDANDUSERID,
					args);
			}

			if ((p2pActivityModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(p2pActivityModelImpl.getOriginalActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTID,
					args);

				args = new Object[] {
						Long.valueOf(p2pActivityModelImpl.getActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTID,
					args);
			}
		}

		EntityCacheUtil.putResult(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityImpl.class, p2pActivity.getPrimaryKey(), p2pActivity);

		return p2pActivity;
	}

	protected P2pActivity toUnwrappedModel(P2pActivity p2pActivity) {
		if (p2pActivity instanceof P2pActivityImpl) {
			return p2pActivity;
		}

		P2pActivityImpl p2pActivityImpl = new P2pActivityImpl();

		p2pActivityImpl.setNew(p2pActivity.isNew());
		p2pActivityImpl.setPrimaryKey(p2pActivity.getPrimaryKey());

		p2pActivityImpl.setUuid(p2pActivity.getUuid());
		p2pActivityImpl.setP2pActivityId(p2pActivity.getP2pActivityId());
		p2pActivityImpl.setActId(p2pActivity.getActId());
		p2pActivityImpl.setUserId(p2pActivity.getUserId());
		p2pActivityImpl.setFileEntryId(p2pActivity.getFileEntryId());
		p2pActivityImpl.setCountCorrections(p2pActivity.getCountCorrections());
		p2pActivityImpl.setDescription(p2pActivity.getDescription());
		p2pActivityImpl.setDate(p2pActivity.getDate());
		p2pActivityImpl.setAsignationsCompleted(p2pActivity.isAsignationsCompleted());

		return p2pActivityImpl;
	}

	/**
	 * Returns the p2p activity with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the p2p activity
	 * @return the p2p activity
	 * @throws com.liferay.portal.NoSuchModelException if a p2p activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public P2pActivity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the p2p activity with the primary key or throws a {@link com.liferay.lms.NoSuchP2pActivityException} if it could not be found.
	 *
	 * @param p2pActivityId the primary key of the p2p activity
	 * @return the p2p activity
	 * @throws com.liferay.lms.NoSuchP2pActivityException if a p2p activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity findByPrimaryKey(long p2pActivityId)
		throws NoSuchP2pActivityException, SystemException {
		P2pActivity p2pActivity = fetchByPrimaryKey(p2pActivityId);

		if (p2pActivity == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + p2pActivityId);
			}

			throw new NoSuchP2pActivityException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				p2pActivityId);
		}

		return p2pActivity;
	}

	/**
	 * Returns the p2p activity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the p2p activity
	 * @return the p2p activity, or <code>null</code> if a p2p activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public P2pActivity fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the p2p activity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param p2pActivityId the primary key of the p2p activity
	 * @return the p2p activity, or <code>null</code> if a p2p activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity fetchByPrimaryKey(long p2pActivityId)
		throws SystemException {
		P2pActivity p2pActivity = (P2pActivity)EntityCacheUtil.getResult(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
				P2pActivityImpl.class, p2pActivityId);

		if (p2pActivity == _nullP2pActivity) {
			return null;
		}

		if (p2pActivity == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				p2pActivity = (P2pActivity)session.get(P2pActivityImpl.class,
						Long.valueOf(p2pActivityId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (p2pActivity != null) {
					cacheResult(p2pActivity);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(P2pActivityModelImpl.ENTITY_CACHE_ENABLED,
						P2pActivityImpl.class, p2pActivityId, _nullP2pActivity);
				}

				closeSession(session);
			}
		}

		return p2pActivity;
	}

	/**
	 * Returns all the p2p activities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivity> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the p2p activities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of p2p activities
	 * @param end the upper bound of the range of p2p activities (not inclusive)
	 * @return the range of matching p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivity> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the p2p activities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of p2p activities
	 * @param end the upper bound of the range of p2p activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivity> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<P2pActivity> list = (List<P2pActivity>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (P2pActivity p2pActivity : list) {
				if (!Validator.equals(uuid, p2pActivity.getUuid())) {
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

			query.append(_SQL_SELECT_P2PACTIVITY_WHERE);

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
				query.append(P2pActivityModelImpl.ORDER_BY_JPQL);
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

				list = (List<P2pActivity>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first p2p activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity
	 * @throws com.liferay.lms.NoSuchP2pActivityException if a matching p2p activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchP2pActivityException, SystemException {
		P2pActivity p2pActivity = fetchByUuid_First(uuid, orderByComparator);

		if (p2pActivity != null) {
			return p2pActivity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityException(msg.toString());
	}

	/**
	 * Returns the first p2p activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity, or <code>null</code> if a matching p2p activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<P2pActivity> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last p2p activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity
	 * @throws com.liferay.lms.NoSuchP2pActivityException if a matching p2p activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchP2pActivityException, SystemException {
		P2pActivity p2pActivity = fetchByUuid_Last(uuid, orderByComparator);

		if (p2pActivity != null) {
			return p2pActivity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityException(msg.toString());
	}

	/**
	 * Returns the last p2p activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity, or <code>null</code> if a matching p2p activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<P2pActivity> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the p2p activities before and after the current p2p activity in the ordered set where uuid = &#63;.
	 *
	 * @param p2pActivityId the primary key of the current p2p activity
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next p2p activity
	 * @throws com.liferay.lms.NoSuchP2pActivityException if a p2p activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity[] findByUuid_PrevAndNext(long p2pActivityId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchP2pActivityException, SystemException {
		P2pActivity p2pActivity = findByPrimaryKey(p2pActivityId);

		Session session = null;

		try {
			session = openSession();

			P2pActivity[] array = new P2pActivityImpl[3];

			array[0] = getByUuid_PrevAndNext(session, p2pActivity, uuid,
					orderByComparator, true);

			array[1] = p2pActivity;

			array[2] = getByUuid_PrevAndNext(session, p2pActivity, uuid,
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

	protected P2pActivity getByUuid_PrevAndNext(Session session,
		P2pActivity p2pActivity, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_P2PACTIVITY_WHERE);

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
			query.append(P2pActivityModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(p2pActivity);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<P2pActivity> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the p2p activities where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @return the matching p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivity> findByActIdAndUserId(long actId, long userId)
		throws SystemException {
		return findByActIdAndUserId(actId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the p2p activities where actId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of p2p activities
	 * @param end the upper bound of the range of p2p activities (not inclusive)
	 * @return the range of matching p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivity> findByActIdAndUserId(long actId, long userId,
		int start, int end) throws SystemException {
		return findByActIdAndUserId(actId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the p2p activities where actId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of p2p activities
	 * @param end the upper bound of the range of p2p activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivity> findByActIdAndUserId(long actId, long userId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDANDUSERID;
			finderArgs = new Object[] { actId, userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTIDANDUSERID;
			finderArgs = new Object[] {
					actId, userId,
					
					start, end, orderByComparator
				};
		}

		List<P2pActivity> list = (List<P2pActivity>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (P2pActivity p2pActivity : list) {
				if ((actId != p2pActivity.getActId()) ||
						(userId != p2pActivity.getUserId())) {
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

			query.append(_SQL_SELECT_P2PACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_ACTIDANDUSERID_ACTID_2);

			query.append(_FINDER_COLUMN_ACTIDANDUSERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(P2pActivityModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				qPos.add(userId);

				list = (List<P2pActivity>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first p2p activity in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity
	 * @throws com.liferay.lms.NoSuchP2pActivityException if a matching p2p activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity findByActIdAndUserId_First(long actId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchP2pActivityException, SystemException {
		P2pActivity p2pActivity = fetchByActIdAndUserId_First(actId, userId,
				orderByComparator);

		if (p2pActivity != null) {
			return p2pActivity;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityException(msg.toString());
	}

	/**
	 * Returns the first p2p activity in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity, or <code>null</code> if a matching p2p activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity fetchByActIdAndUserId_First(long actId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<P2pActivity> list = findByActIdAndUserId(actId, userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last p2p activity in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity
	 * @throws com.liferay.lms.NoSuchP2pActivityException if a matching p2p activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity findByActIdAndUserId_Last(long actId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchP2pActivityException, SystemException {
		P2pActivity p2pActivity = fetchByActIdAndUserId_Last(actId, userId,
				orderByComparator);

		if (p2pActivity != null) {
			return p2pActivity;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityException(msg.toString());
	}

	/**
	 * Returns the last p2p activity in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity, or <code>null</code> if a matching p2p activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity fetchByActIdAndUserId_Last(long actId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByActIdAndUserId(actId, userId);

		List<P2pActivity> list = findByActIdAndUserId(actId, userId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the p2p activities before and after the current p2p activity in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param p2pActivityId the primary key of the current p2p activity
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next p2p activity
	 * @throws com.liferay.lms.NoSuchP2pActivityException if a p2p activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity[] findByActIdAndUserId_PrevAndNext(long p2pActivityId,
		long actId, long userId, OrderByComparator orderByComparator)
		throws NoSuchP2pActivityException, SystemException {
		P2pActivity p2pActivity = findByPrimaryKey(p2pActivityId);

		Session session = null;

		try {
			session = openSession();

			P2pActivity[] array = new P2pActivityImpl[3];

			array[0] = getByActIdAndUserId_PrevAndNext(session, p2pActivity,
					actId, userId, orderByComparator, true);

			array[1] = p2pActivity;

			array[2] = getByActIdAndUserId_PrevAndNext(session, p2pActivity,
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

	protected P2pActivity getByActIdAndUserId_PrevAndNext(Session session,
		P2pActivity p2pActivity, long actId, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_P2PACTIVITY_WHERE);

		query.append(_FINDER_COLUMN_ACTIDANDUSERID_ACTID_2);

		query.append(_FINDER_COLUMN_ACTIDANDUSERID_USERID_2);

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
			query.append(P2pActivityModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(actId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(p2pActivity);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<P2pActivity> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the p2p activities where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the matching p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivity> findByActId(long actId) throws SystemException {
		return findByActId(actId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the p2p activities where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of p2p activities
	 * @param end the upper bound of the range of p2p activities (not inclusive)
	 * @return the range of matching p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivity> findByActId(long actId, int start, int end)
		throws SystemException {
		return findByActId(actId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the p2p activities where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of p2p activities
	 * @param end the upper bound of the range of p2p activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivity> findByActId(long actId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTID;
			finderArgs = new Object[] { actId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTID;
			finderArgs = new Object[] { actId, start, end, orderByComparator };
		}

		List<P2pActivity> list = (List<P2pActivity>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (P2pActivity p2pActivity : list) {
				if ((actId != p2pActivity.getActId())) {
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

			query.append(_SQL_SELECT_P2PACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_ACTID_ACTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(P2pActivityModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				list = (List<P2pActivity>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first p2p activity in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity
	 * @throws com.liferay.lms.NoSuchP2pActivityException if a matching p2p activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity findByActId_First(long actId,
		OrderByComparator orderByComparator)
		throws NoSuchP2pActivityException, SystemException {
		P2pActivity p2pActivity = fetchByActId_First(actId, orderByComparator);

		if (p2pActivity != null) {
			return p2pActivity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityException(msg.toString());
	}

	/**
	 * Returns the first p2p activity in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity, or <code>null</code> if a matching p2p activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity fetchByActId_First(long actId,
		OrderByComparator orderByComparator) throws SystemException {
		List<P2pActivity> list = findByActId(actId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last p2p activity in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity
	 * @throws com.liferay.lms.NoSuchP2pActivityException if a matching p2p activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity findByActId_Last(long actId,
		OrderByComparator orderByComparator)
		throws NoSuchP2pActivityException, SystemException {
		P2pActivity p2pActivity = fetchByActId_Last(actId, orderByComparator);

		if (p2pActivity != null) {
			return p2pActivity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityException(msg.toString());
	}

	/**
	 * Returns the last p2p activity in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity, or <code>null</code> if a matching p2p activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity fetchByActId_Last(long actId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByActId(actId);

		List<P2pActivity> list = findByActId(actId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the p2p activities before and after the current p2p activity in the ordered set where actId = &#63;.
	 *
	 * @param p2pActivityId the primary key of the current p2p activity
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next p2p activity
	 * @throws com.liferay.lms.NoSuchP2pActivityException if a p2p activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivity[] findByActId_PrevAndNext(long p2pActivityId,
		long actId, OrderByComparator orderByComparator)
		throws NoSuchP2pActivityException, SystemException {
		P2pActivity p2pActivity = findByPrimaryKey(p2pActivityId);

		Session session = null;

		try {
			session = openSession();

			P2pActivity[] array = new P2pActivityImpl[3];

			array[0] = getByActId_PrevAndNext(session, p2pActivity, actId,
					orderByComparator, true);

			array[1] = p2pActivity;

			array[2] = getByActId_PrevAndNext(session, p2pActivity, actId,
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

	protected P2pActivity getByActId_PrevAndNext(Session session,
		P2pActivity p2pActivity, long actId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_P2PACTIVITY_WHERE);

		query.append(_FINDER_COLUMN_ACTID_ACTID_2);

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
			query.append(P2pActivityModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(actId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(p2pActivity);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<P2pActivity> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the p2p activities.
	 *
	 * @return the p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivity> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	public List<P2pActivity> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the p2p activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of p2p activities
	 * @param end the upper bound of the range of p2p activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivity> findAll(int start, int end,
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

		List<P2pActivity> list = (List<P2pActivity>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_P2PACTIVITY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_P2PACTIVITY.concat(P2pActivityModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<P2pActivity>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<P2pActivity>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the p2p activities where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (P2pActivity p2pActivity : findByUuid(uuid)) {
			remove(p2pActivity);
		}
	}

	/**
	 * Removes all the p2p activities where actId = &#63; and userId = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByActIdAndUserId(long actId, long userId)
		throws SystemException {
		for (P2pActivity p2pActivity : findByActIdAndUserId(actId, userId)) {
			remove(p2pActivity);
		}
	}

	/**
	 * Removes all the p2p activities where actId = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByActId(long actId) throws SystemException {
		for (P2pActivity p2pActivity : findByActId(actId)) {
			remove(p2pActivity);
		}
	}

	/**
	 * Removes all the p2p activities from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (P2pActivity p2pActivity : findAll()) {
			remove(p2pActivity);
		}
	}

	/**
	 * Returns the number of p2p activities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_P2PACTIVITY_WHERE);

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
	 * Returns the number of p2p activities where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @return the number of matching p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countByActIdAndUserId(long actId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { actId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACTIDANDUSERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_P2PACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_ACTIDANDUSERID_ACTID_2);

			query.append(_FINDER_COLUMN_ACTIDANDUSERID_USERID_2);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACTIDANDUSERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of p2p activities where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the number of matching p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countByActId(long actId) throws SystemException {
		Object[] finderArgs = new Object[] { actId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACTID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_P2PACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_ACTID_ACTID_2);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACTID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of p2p activities.
	 *
	 * @return the number of p2p activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_P2PACTIVITY);

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
	 * Initializes the p2p activity persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.P2pActivity")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<P2pActivity>> listenersList = new ArrayList<ModelListener<P2pActivity>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<P2pActivity>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(P2pActivityImpl.class.getName());
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
	private static final String _SQL_SELECT_P2PACTIVITY = "SELECT p2pActivity FROM P2pActivity p2pActivity";
	private static final String _SQL_SELECT_P2PACTIVITY_WHERE = "SELECT p2pActivity FROM P2pActivity p2pActivity WHERE ";
	private static final String _SQL_COUNT_P2PACTIVITY = "SELECT COUNT(p2pActivity) FROM P2pActivity p2pActivity";
	private static final String _SQL_COUNT_P2PACTIVITY_WHERE = "SELECT COUNT(p2pActivity) FROM P2pActivity p2pActivity WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "p2pActivity.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "p2pActivity.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(p2pActivity.uuid IS NULL OR p2pActivity.uuid = ?)";
	private static final String _FINDER_COLUMN_ACTIDANDUSERID_ACTID_2 = "p2pActivity.actId = ? AND ";
	private static final String _FINDER_COLUMN_ACTIDANDUSERID_USERID_2 = "p2pActivity.userId = ?";
	private static final String _FINDER_COLUMN_ACTID_ACTID_2 = "p2pActivity.actId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "p2pActivity.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No P2pActivity exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No P2pActivity exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(P2pActivityPersistenceImpl.class);
	private static P2pActivity _nullP2pActivity = new P2pActivityImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<P2pActivity> toCacheModel() {
				return _nullP2pActivityCacheModel;
			}
		};

	private static CacheModel<P2pActivity> _nullP2pActivityCacheModel = new CacheModel<P2pActivity>() {
			public P2pActivity toEntityModel() {
				return _nullP2pActivity;
			}
		};
}