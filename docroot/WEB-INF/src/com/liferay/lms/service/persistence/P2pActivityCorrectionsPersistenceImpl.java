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

import com.liferay.lms.NoSuchP2pActivityCorrectionsException;
import com.liferay.lms.model.P2pActivityCorrections;
import com.liferay.lms.model.impl.P2pActivityCorrectionsImpl;
import com.liferay.lms.model.impl.P2pActivityCorrectionsModelImpl;

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
 * The persistence implementation for the p2p activity corrections service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see P2pActivityCorrectionsPersistence
 * @see P2pActivityCorrectionsUtil
 * @generated
 */
public class P2pActivityCorrectionsPersistenceImpl extends BasePersistenceImpl<P2pActivityCorrections>
	implements P2pActivityCorrectionsPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link P2pActivityCorrectionsUtil} to access the p2p activity corrections persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = P2pActivityCorrectionsImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED,
			P2pActivityCorrectionsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED,
			P2pActivityCorrectionsImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			P2pActivityCorrectionsModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_P2PACTIVITYID =
		new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED,
			P2pActivityCorrectionsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP2pActivityId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P2PACTIVITYID =
		new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED,
			P2pActivityCorrectionsImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP2pActivityId",
			new String[] { Long.class.getName() },
			P2pActivityCorrectionsModelImpl.P2PACTIVITYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_P2PACTIVITYID = new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP2pActivityId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTIDANDUSERID =
		new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED,
			P2pActivityCorrectionsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByActIdAndUserId",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDANDUSERID =
		new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED,
			P2pActivityCorrectionsImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByActIdAndUserId",
			new String[] { Long.class.getName(), Long.class.getName() },
			P2pActivityCorrectionsModelImpl.ACTID_COLUMN_BITMASK |
			P2pActivityCorrectionsModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTIDANDUSERID = new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByActIdAndUserId",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_P2PACTIVITYIDANDUSERID =
		new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED,
			P2pActivityCorrectionsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByP2pActivityIdAndUserId",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P2PACTIVITYIDANDUSERID =
		new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED,
			P2pActivityCorrectionsImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByP2pActivityIdAndUserId",
			new String[] { Long.class.getName(), Long.class.getName() },
			P2pActivityCorrectionsModelImpl.P2PACTIVITYID_COLUMN_BITMASK |
			P2pActivityCorrectionsModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_P2PACTIVITYIDANDUSERID = new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByP2pActivityIdAndUserId",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED,
			P2pActivityCorrectionsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED,
			P2pActivityCorrectionsImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the p2p activity corrections in the entity cache if it is enabled.
	 *
	 * @param p2pActivityCorrections the p2p activity corrections
	 */
	public void cacheResult(P2pActivityCorrections p2pActivityCorrections) {
		EntityCacheUtil.putResult(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsImpl.class,
			p2pActivityCorrections.getPrimaryKey(), p2pActivityCorrections);

		p2pActivityCorrections.resetOriginalValues();
	}

	/**
	 * Caches the p2p activity correctionses in the entity cache if it is enabled.
	 *
	 * @param p2pActivityCorrectionses the p2p activity correctionses
	 */
	public void cacheResult(
		List<P2pActivityCorrections> p2pActivityCorrectionses) {
		for (P2pActivityCorrections p2pActivityCorrections : p2pActivityCorrectionses) {
			if (EntityCacheUtil.getResult(
						P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
						P2pActivityCorrectionsImpl.class,
						p2pActivityCorrections.getPrimaryKey()) == null) {
				cacheResult(p2pActivityCorrections);
			}
			else {
				p2pActivityCorrections.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all p2p activity correctionses.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(P2pActivityCorrectionsImpl.class.getName());
		}

		EntityCacheUtil.clearCache(P2pActivityCorrectionsImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the p2p activity corrections.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(P2pActivityCorrections p2pActivityCorrections) {
		EntityCacheUtil.removeResult(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsImpl.class,
			p2pActivityCorrections.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<P2pActivityCorrections> p2pActivityCorrectionses) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (P2pActivityCorrections p2pActivityCorrections : p2pActivityCorrectionses) {
			EntityCacheUtil.removeResult(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
				P2pActivityCorrectionsImpl.class,
				p2pActivityCorrections.getPrimaryKey());
		}
	}

	/**
	 * Creates a new p2p activity corrections with the primary key. Does not add the p2p activity corrections to the database.
	 *
	 * @param p2pActivityCorrectionsId the primary key for the new p2p activity corrections
	 * @return the new p2p activity corrections
	 */
	public P2pActivityCorrections create(long p2pActivityCorrectionsId) {
		P2pActivityCorrections p2pActivityCorrections = new P2pActivityCorrectionsImpl();

		p2pActivityCorrections.setNew(true);
		p2pActivityCorrections.setPrimaryKey(p2pActivityCorrectionsId);

		String uuid = PortalUUIDUtil.generate();

		p2pActivityCorrections.setUuid(uuid);

		return p2pActivityCorrections;
	}

	/**
	 * Removes the p2p activity corrections with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param p2pActivityCorrectionsId the primary key of the p2p activity corrections
	 * @return the p2p activity corrections that was removed
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections remove(long p2pActivityCorrectionsId)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		return remove(Long.valueOf(p2pActivityCorrectionsId));
	}

	/**
	 * Removes the p2p activity corrections with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the p2p activity corrections
	 * @return the p2p activity corrections that was removed
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public P2pActivityCorrections remove(Serializable primaryKey)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		Session session = null;

		try {
			session = openSession();

			P2pActivityCorrections p2pActivityCorrections = (P2pActivityCorrections)session.get(P2pActivityCorrectionsImpl.class,
					primaryKey);

			if (p2pActivityCorrections == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchP2pActivityCorrectionsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(p2pActivityCorrections);
		}
		catch (NoSuchP2pActivityCorrectionsException nsee) {
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
	protected P2pActivityCorrections removeImpl(
		P2pActivityCorrections p2pActivityCorrections)
		throws SystemException {
		p2pActivityCorrections = toUnwrappedModel(p2pActivityCorrections);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, p2pActivityCorrections);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(p2pActivityCorrections);

		return p2pActivityCorrections;
	}

	@Override
	public P2pActivityCorrections updateImpl(
		com.liferay.lms.model.P2pActivityCorrections p2pActivityCorrections,
		boolean merge) throws SystemException {
		p2pActivityCorrections = toUnwrappedModel(p2pActivityCorrections);

		boolean isNew = p2pActivityCorrections.isNew();

		P2pActivityCorrectionsModelImpl p2pActivityCorrectionsModelImpl = (P2pActivityCorrectionsModelImpl)p2pActivityCorrections;

		if (Validator.isNull(p2pActivityCorrections.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			p2pActivityCorrections.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, p2pActivityCorrections, merge);

			p2pActivityCorrections.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !P2pActivityCorrectionsModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((p2pActivityCorrectionsModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						p2pActivityCorrectionsModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { p2pActivityCorrectionsModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((p2pActivityCorrectionsModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P2PACTIVITYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(p2pActivityCorrectionsModelImpl.getOriginalP2pActivityId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P2PACTIVITYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P2PACTIVITYID,
					args);

				args = new Object[] {
						Long.valueOf(p2pActivityCorrectionsModelImpl.getP2pActivityId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P2PACTIVITYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P2PACTIVITYID,
					args);
			}

			if ((p2pActivityCorrectionsModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDANDUSERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(p2pActivityCorrectionsModelImpl.getOriginalActId()),
						Long.valueOf(p2pActivityCorrectionsModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTIDANDUSERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDANDUSERID,
					args);

				args = new Object[] {
						Long.valueOf(p2pActivityCorrectionsModelImpl.getActId()),
						Long.valueOf(p2pActivityCorrectionsModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTIDANDUSERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDANDUSERID,
					args);
			}

			if ((p2pActivityCorrectionsModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P2PACTIVITYIDANDUSERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(p2pActivityCorrectionsModelImpl.getOriginalP2pActivityId()),
						Long.valueOf(p2pActivityCorrectionsModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P2PACTIVITYIDANDUSERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P2PACTIVITYIDANDUSERID,
					args);

				args = new Object[] {
						Long.valueOf(p2pActivityCorrectionsModelImpl.getP2pActivityId()),
						Long.valueOf(p2pActivityCorrectionsModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P2PACTIVITYIDANDUSERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P2PACTIVITYIDANDUSERID,
					args);
			}
		}

		EntityCacheUtil.putResult(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
			P2pActivityCorrectionsImpl.class,
			p2pActivityCorrections.getPrimaryKey(), p2pActivityCorrections);

		return p2pActivityCorrections;
	}

	protected P2pActivityCorrections toUnwrappedModel(
		P2pActivityCorrections p2pActivityCorrections) {
		if (p2pActivityCorrections instanceof P2pActivityCorrectionsImpl) {
			return p2pActivityCorrections;
		}

		P2pActivityCorrectionsImpl p2pActivityCorrectionsImpl = new P2pActivityCorrectionsImpl();

		p2pActivityCorrectionsImpl.setNew(p2pActivityCorrections.isNew());
		p2pActivityCorrectionsImpl.setPrimaryKey(p2pActivityCorrections.getPrimaryKey());

		p2pActivityCorrectionsImpl.setUuid(p2pActivityCorrections.getUuid());
		p2pActivityCorrectionsImpl.setP2pActivityCorrectionsId(p2pActivityCorrections.getP2pActivityCorrectionsId());
		p2pActivityCorrectionsImpl.setP2pActivityId(p2pActivityCorrections.getP2pActivityId());
		p2pActivityCorrectionsImpl.setUserId(p2pActivityCorrections.getUserId());
		p2pActivityCorrectionsImpl.setActId(p2pActivityCorrections.getActId());
		p2pActivityCorrectionsImpl.setDescription(p2pActivityCorrections.getDescription());
		p2pActivityCorrectionsImpl.setDate(p2pActivityCorrections.getDate());
		p2pActivityCorrectionsImpl.setFileEntryId(p2pActivityCorrections.getFileEntryId());
		p2pActivityCorrectionsImpl.setResult(p2pActivityCorrections.getResult());

		return p2pActivityCorrectionsImpl;
	}

	/**
	 * Returns the p2p activity corrections with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the p2p activity corrections
	 * @return the p2p activity corrections
	 * @throws com.liferay.portal.NoSuchModelException if a p2p activity corrections with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public P2pActivityCorrections findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the p2p activity corrections with the primary key or throws a {@link com.liferay.lms.NoSuchP2pActivityCorrectionsException} if it could not be found.
	 *
	 * @param p2pActivityCorrectionsId the primary key of the p2p activity corrections
	 * @return the p2p activity corrections
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections findByPrimaryKey(
		long p2pActivityCorrectionsId)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		P2pActivityCorrections p2pActivityCorrections = fetchByPrimaryKey(p2pActivityCorrectionsId);

		if (p2pActivityCorrections == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					p2pActivityCorrectionsId);
			}

			throw new NoSuchP2pActivityCorrectionsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				p2pActivityCorrectionsId);
		}

		return p2pActivityCorrections;
	}

	/**
	 * Returns the p2p activity corrections with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the p2p activity corrections
	 * @return the p2p activity corrections, or <code>null</code> if a p2p activity corrections with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public P2pActivityCorrections fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the p2p activity corrections with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param p2pActivityCorrectionsId the primary key of the p2p activity corrections
	 * @return the p2p activity corrections, or <code>null</code> if a p2p activity corrections with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections fetchByPrimaryKey(
		long p2pActivityCorrectionsId) throws SystemException {
		P2pActivityCorrections p2pActivityCorrections = (P2pActivityCorrections)EntityCacheUtil.getResult(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
				P2pActivityCorrectionsImpl.class, p2pActivityCorrectionsId);

		if (p2pActivityCorrections == _nullP2pActivityCorrections) {
			return null;
		}

		if (p2pActivityCorrections == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				p2pActivityCorrections = (P2pActivityCorrections)session.get(P2pActivityCorrectionsImpl.class,
						Long.valueOf(p2pActivityCorrectionsId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (p2pActivityCorrections != null) {
					cacheResult(p2pActivityCorrections);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(P2pActivityCorrectionsModelImpl.ENTITY_CACHE_ENABLED,
						P2pActivityCorrectionsImpl.class,
						p2pActivityCorrectionsId, _nullP2pActivityCorrections);
				}

				closeSession(session);
			}
		}

		return p2pActivityCorrections;
	}

	/**
	 * Returns all the p2p activity correctionses where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the p2p activity correctionses where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of p2p activity correctionses
	 * @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	 * @return the range of matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findByUuid(String uuid, int start,
		int end) throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the p2p activity correctionses where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of p2p activity correctionses
	 * @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findByUuid(String uuid, int start,
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

		List<P2pActivityCorrections> list = (List<P2pActivityCorrections>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (P2pActivityCorrections p2pActivityCorrections : list) {
				if (!Validator.equals(uuid, p2pActivityCorrections.getUuid())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_P2PACTIVITYCORRECTIONS_WHERE);

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

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<P2pActivityCorrections>)QueryUtil.list(q,
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
	 * Returns the first p2p activity corrections in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity corrections
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		P2pActivityCorrections p2pActivityCorrections = fetchByUuid_First(uuid,
				orderByComparator);

		if (p2pActivityCorrections != null) {
			return p2pActivityCorrections;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityCorrectionsException(msg.toString());
	}

	/**
	 * Returns the first p2p activity corrections in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<P2pActivityCorrections> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last p2p activity corrections in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity corrections
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		P2pActivityCorrections p2pActivityCorrections = fetchByUuid_Last(uuid,
				orderByComparator);

		if (p2pActivityCorrections != null) {
			return p2pActivityCorrections;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityCorrectionsException(msg.toString());
	}

	/**
	 * Returns the last p2p activity corrections in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<P2pActivityCorrections> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the p2p activity correctionses before and after the current p2p activity corrections in the ordered set where uuid = &#63;.
	 *
	 * @param p2pActivityCorrectionsId the primary key of the current p2p activity corrections
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next p2p activity corrections
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections[] findByUuid_PrevAndNext(
		long p2pActivityCorrectionsId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		P2pActivityCorrections p2pActivityCorrections = findByPrimaryKey(p2pActivityCorrectionsId);

		Session session = null;

		try {
			session = openSession();

			P2pActivityCorrections[] array = new P2pActivityCorrectionsImpl[3];

			array[0] = getByUuid_PrevAndNext(session, p2pActivityCorrections,
					uuid, orderByComparator, true);

			array[1] = p2pActivityCorrections;

			array[2] = getByUuid_PrevAndNext(session, p2pActivityCorrections,
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

	protected P2pActivityCorrections getByUuid_PrevAndNext(Session session,
		P2pActivityCorrections p2pActivityCorrections, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_P2PACTIVITYCORRECTIONS_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(p2pActivityCorrections);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<P2pActivityCorrections> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the p2p activity correctionses where p2pActivityId = &#63;.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @return the matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findByP2pActivityId(long p2pActivityId)
		throws SystemException {
		return findByP2pActivityId(p2pActivityId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the p2p activity correctionses where p2pActivityId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param start the lower bound of the range of p2p activity correctionses
	 * @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	 * @return the range of matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findByP2pActivityId(
		long p2pActivityId, int start, int end) throws SystemException {
		return findByP2pActivityId(p2pActivityId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the p2p activity correctionses where p2pActivityId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param start the lower bound of the range of p2p activity correctionses
	 * @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findByP2pActivityId(
		long p2pActivityId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P2PACTIVITYID;
			finderArgs = new Object[] { p2pActivityId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_P2PACTIVITYID;
			finderArgs = new Object[] {
					p2pActivityId,
					
					start, end, orderByComparator
				};
		}

		List<P2pActivityCorrections> list = (List<P2pActivityCorrections>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (P2pActivityCorrections p2pActivityCorrections : list) {
				if ((p2pActivityId != p2pActivityCorrections.getP2pActivityId())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_P2PACTIVITYCORRECTIONS_WHERE);

			query.append(_FINDER_COLUMN_P2PACTIVITYID_P2PACTIVITYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(p2pActivityId);

				list = (List<P2pActivityCorrections>)QueryUtil.list(q,
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
	 * Returns the first p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity corrections
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections findByP2pActivityId_First(
		long p2pActivityId, OrderByComparator orderByComparator)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		P2pActivityCorrections p2pActivityCorrections = fetchByP2pActivityId_First(p2pActivityId,
				orderByComparator);

		if (p2pActivityCorrections != null) {
			return p2pActivityCorrections;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("p2pActivityId=");
		msg.append(p2pActivityId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityCorrectionsException(msg.toString());
	}

	/**
	 * Returns the first p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections fetchByP2pActivityId_First(
		long p2pActivityId, OrderByComparator orderByComparator)
		throws SystemException {
		List<P2pActivityCorrections> list = findByP2pActivityId(p2pActivityId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity corrections
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections findByP2pActivityId_Last(long p2pActivityId,
		OrderByComparator orderByComparator)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		P2pActivityCorrections p2pActivityCorrections = fetchByP2pActivityId_Last(p2pActivityId,
				orderByComparator);

		if (p2pActivityCorrections != null) {
			return p2pActivityCorrections;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("p2pActivityId=");
		msg.append(p2pActivityId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityCorrectionsException(msg.toString());
	}

	/**
	 * Returns the last p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections fetchByP2pActivityId_Last(
		long p2pActivityId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByP2pActivityId(p2pActivityId);

		List<P2pActivityCorrections> list = findByP2pActivityId(p2pActivityId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the p2p activity correctionses before and after the current p2p activity corrections in the ordered set where p2pActivityId = &#63;.
	 *
	 * @param p2pActivityCorrectionsId the primary key of the current p2p activity corrections
	 * @param p2pActivityId the p2p activity ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next p2p activity corrections
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections[] findByP2pActivityId_PrevAndNext(
		long p2pActivityCorrectionsId, long p2pActivityId,
		OrderByComparator orderByComparator)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		P2pActivityCorrections p2pActivityCorrections = findByPrimaryKey(p2pActivityCorrectionsId);

		Session session = null;

		try {
			session = openSession();

			P2pActivityCorrections[] array = new P2pActivityCorrectionsImpl[3];

			array[0] = getByP2pActivityId_PrevAndNext(session,
					p2pActivityCorrections, p2pActivityId, orderByComparator,
					true);

			array[1] = p2pActivityCorrections;

			array[2] = getByP2pActivityId_PrevAndNext(session,
					p2pActivityCorrections, p2pActivityId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected P2pActivityCorrections getByP2pActivityId_PrevAndNext(
		Session session, P2pActivityCorrections p2pActivityCorrections,
		long p2pActivityId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_P2PACTIVITYCORRECTIONS_WHERE);

		query.append(_FINDER_COLUMN_P2PACTIVITYID_P2PACTIVITYID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(p2pActivityId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(p2pActivityCorrections);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<P2pActivityCorrections> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the p2p activity correctionses where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @return the matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findByActIdAndUserId(long actId,
		long userId) throws SystemException {
		return findByActIdAndUserId(actId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the p2p activity correctionses where actId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of p2p activity correctionses
	 * @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	 * @return the range of matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findByActIdAndUserId(long actId,
		long userId, int start, int end) throws SystemException {
		return findByActIdAndUserId(actId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the p2p activity correctionses where actId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of p2p activity correctionses
	 * @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findByActIdAndUserId(long actId,
		long userId, int start, int end, OrderByComparator orderByComparator)
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

		List<P2pActivityCorrections> list = (List<P2pActivityCorrections>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (P2pActivityCorrections p2pActivityCorrections : list) {
				if ((actId != p2pActivityCorrections.getActId()) ||
						(userId != p2pActivityCorrections.getUserId())) {
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_P2PACTIVITYCORRECTIONS_WHERE);

			query.append(_FINDER_COLUMN_ACTIDANDUSERID_ACTID_2);

			query.append(_FINDER_COLUMN_ACTIDANDUSERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				qPos.add(userId);

				list = (List<P2pActivityCorrections>)QueryUtil.list(q,
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
	 * Returns the first p2p activity corrections in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity corrections
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections findByActIdAndUserId_First(long actId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		P2pActivityCorrections p2pActivityCorrections = fetchByActIdAndUserId_First(actId,
				userId, orderByComparator);

		if (p2pActivityCorrections != null) {
			return p2pActivityCorrections;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityCorrectionsException(msg.toString());
	}

	/**
	 * Returns the first p2p activity corrections in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections fetchByActIdAndUserId_First(long actId,
		long userId, OrderByComparator orderByComparator)
		throws SystemException {
		List<P2pActivityCorrections> list = findByActIdAndUserId(actId, userId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last p2p activity corrections in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity corrections
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections findByActIdAndUserId_Last(long actId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		P2pActivityCorrections p2pActivityCorrections = fetchByActIdAndUserId_Last(actId,
				userId, orderByComparator);

		if (p2pActivityCorrections != null) {
			return p2pActivityCorrections;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityCorrectionsException(msg.toString());
	}

	/**
	 * Returns the last p2p activity corrections in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections fetchByActIdAndUserId_Last(long actId,
		long userId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByActIdAndUserId(actId, userId);

		List<P2pActivityCorrections> list = findByActIdAndUserId(actId, userId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the p2p activity correctionses before and after the current p2p activity corrections in the ordered set where actId = &#63; and userId = &#63;.
	 *
	 * @param p2pActivityCorrectionsId the primary key of the current p2p activity corrections
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next p2p activity corrections
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections[] findByActIdAndUserId_PrevAndNext(
		long p2pActivityCorrectionsId, long actId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		P2pActivityCorrections p2pActivityCorrections = findByPrimaryKey(p2pActivityCorrectionsId);

		Session session = null;

		try {
			session = openSession();

			P2pActivityCorrections[] array = new P2pActivityCorrectionsImpl[3];

			array[0] = getByActIdAndUserId_PrevAndNext(session,
					p2pActivityCorrections, actId, userId, orderByComparator,
					true);

			array[1] = p2pActivityCorrections;

			array[2] = getByActIdAndUserId_PrevAndNext(session,
					p2pActivityCorrections, actId, userId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected P2pActivityCorrections getByActIdAndUserId_PrevAndNext(
		Session session, P2pActivityCorrections p2pActivityCorrections,
		long actId, long userId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_P2PACTIVITYCORRECTIONS_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(actId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(p2pActivityCorrections);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<P2pActivityCorrections> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the p2p activity correctionses where p2pActivityId = &#63; and userId = &#63;.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param userId the user ID
	 * @return the matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findByP2pActivityIdAndUserId(
		long p2pActivityId, long userId) throws SystemException {
		return findByP2pActivityIdAndUserId(p2pActivityId, userId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the p2p activity correctionses where p2pActivityId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of p2p activity correctionses
	 * @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	 * @return the range of matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findByP2pActivityIdAndUserId(
		long p2pActivityId, long userId, int start, int end)
		throws SystemException {
		return findByP2pActivityIdAndUserId(p2pActivityId, userId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the p2p activity correctionses where p2pActivityId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of p2p activity correctionses
	 * @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findByP2pActivityIdAndUserId(
		long p2pActivityId, long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P2PACTIVITYIDANDUSERID;
			finderArgs = new Object[] { p2pActivityId, userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_P2PACTIVITYIDANDUSERID;
			finderArgs = new Object[] {
					p2pActivityId, userId,
					
					start, end, orderByComparator
				};
		}

		List<P2pActivityCorrections> list = (List<P2pActivityCorrections>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (P2pActivityCorrections p2pActivityCorrections : list) {
				if ((p2pActivityId != p2pActivityCorrections.getP2pActivityId()) ||
						(userId != p2pActivityCorrections.getUserId())) {
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_P2PACTIVITYCORRECTIONS_WHERE);

			query.append(_FINDER_COLUMN_P2PACTIVITYIDANDUSERID_P2PACTIVITYID_2);

			query.append(_FINDER_COLUMN_P2PACTIVITYIDANDUSERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(p2pActivityId);

				qPos.add(userId);

				list = (List<P2pActivityCorrections>)QueryUtil.list(q,
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
	 * Returns the first p2p activity corrections in the ordered set where p2pActivityId = &#63; and userId = &#63;.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity corrections
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections findByP2pActivityIdAndUserId_First(
		long p2pActivityId, long userId, OrderByComparator orderByComparator)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		P2pActivityCorrections p2pActivityCorrections = fetchByP2pActivityIdAndUserId_First(p2pActivityId,
				userId, orderByComparator);

		if (p2pActivityCorrections != null) {
			return p2pActivityCorrections;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("p2pActivityId=");
		msg.append(p2pActivityId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityCorrectionsException(msg.toString());
	}

	/**
	 * Returns the first p2p activity corrections in the ordered set where p2pActivityId = &#63; and userId = &#63;.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections fetchByP2pActivityIdAndUserId_First(
		long p2pActivityId, long userId, OrderByComparator orderByComparator)
		throws SystemException {
		List<P2pActivityCorrections> list = findByP2pActivityIdAndUserId(p2pActivityId,
				userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last p2p activity corrections in the ordered set where p2pActivityId = &#63; and userId = &#63;.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity corrections
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections findByP2pActivityIdAndUserId_Last(
		long p2pActivityId, long userId, OrderByComparator orderByComparator)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		P2pActivityCorrections p2pActivityCorrections = fetchByP2pActivityIdAndUserId_Last(p2pActivityId,
				userId, orderByComparator);

		if (p2pActivityCorrections != null) {
			return p2pActivityCorrections;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("p2pActivityId=");
		msg.append(p2pActivityId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchP2pActivityCorrectionsException(msg.toString());
	}

	/**
	 * Returns the last p2p activity corrections in the ordered set where p2pActivityId = &#63; and userId = &#63;.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching p2p activity corrections, or <code>null</code> if a matching p2p activity corrections could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections fetchByP2pActivityIdAndUserId_Last(
		long p2pActivityId, long userId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByP2pActivityIdAndUserId(p2pActivityId, userId);

		List<P2pActivityCorrections> list = findByP2pActivityIdAndUserId(p2pActivityId,
				userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the p2p activity correctionses before and after the current p2p activity corrections in the ordered set where p2pActivityId = &#63; and userId = &#63;.
	 *
	 * @param p2pActivityCorrectionsId the primary key of the current p2p activity corrections
	 * @param p2pActivityId the p2p activity ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next p2p activity corrections
	 * @throws com.liferay.lms.NoSuchP2pActivityCorrectionsException if a p2p activity corrections with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public P2pActivityCorrections[] findByP2pActivityIdAndUserId_PrevAndNext(
		long p2pActivityCorrectionsId, long p2pActivityId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchP2pActivityCorrectionsException, SystemException {
		P2pActivityCorrections p2pActivityCorrections = findByPrimaryKey(p2pActivityCorrectionsId);

		Session session = null;

		try {
			session = openSession();

			P2pActivityCorrections[] array = new P2pActivityCorrectionsImpl[3];

			array[0] = getByP2pActivityIdAndUserId_PrevAndNext(session,
					p2pActivityCorrections, p2pActivityId, userId,
					orderByComparator, true);

			array[1] = p2pActivityCorrections;

			array[2] = getByP2pActivityIdAndUserId_PrevAndNext(session,
					p2pActivityCorrections, p2pActivityId, userId,
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

	protected P2pActivityCorrections getByP2pActivityIdAndUserId_PrevAndNext(
		Session session, P2pActivityCorrections p2pActivityCorrections,
		long p2pActivityId, long userId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_P2PACTIVITYCORRECTIONS_WHERE);

		query.append(_FINDER_COLUMN_P2PACTIVITYIDANDUSERID_P2PACTIVITYID_2);

		query.append(_FINDER_COLUMN_P2PACTIVITYIDANDUSERID_USERID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(p2pActivityId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(p2pActivityCorrections);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<P2pActivityCorrections> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the p2p activity correctionses.
	 *
	 * @return the p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the p2p activity correctionses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of p2p activity correctionses
	 * @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	 * @return the range of p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the p2p activity correctionses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of p2p activity correctionses
	 * @param end the upper bound of the range of p2p activity correctionses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public List<P2pActivityCorrections> findAll(int start, int end,
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

		List<P2pActivityCorrections> list = (List<P2pActivityCorrections>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_P2PACTIVITYCORRECTIONS);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_P2PACTIVITYCORRECTIONS;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<P2pActivityCorrections>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<P2pActivityCorrections>)QueryUtil.list(q,
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
	 * Removes all the p2p activity correctionses where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (P2pActivityCorrections p2pActivityCorrections : findByUuid(uuid)) {
			remove(p2pActivityCorrections);
		}
	}

	/**
	 * Removes all the p2p activity correctionses where p2pActivityId = &#63; from the database.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByP2pActivityId(long p2pActivityId)
		throws SystemException {
		for (P2pActivityCorrections p2pActivityCorrections : findByP2pActivityId(
				p2pActivityId)) {
			remove(p2pActivityCorrections);
		}
	}

	/**
	 * Removes all the p2p activity correctionses where actId = &#63; and userId = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByActIdAndUserId(long actId, long userId)
		throws SystemException {
		for (P2pActivityCorrections p2pActivityCorrections : findByActIdAndUserId(
				actId, userId)) {
			remove(p2pActivityCorrections);
		}
	}

	/**
	 * Removes all the p2p activity correctionses where p2pActivityId = &#63; and userId = &#63; from the database.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByP2pActivityIdAndUserId(long p2pActivityId, long userId)
		throws SystemException {
		for (P2pActivityCorrections p2pActivityCorrections : findByP2pActivityIdAndUserId(
				p2pActivityId, userId)) {
			remove(p2pActivityCorrections);
		}
	}

	/**
	 * Removes all the p2p activity correctionses from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (P2pActivityCorrections p2pActivityCorrections : findAll()) {
			remove(p2pActivityCorrections);
		}
	}

	/**
	 * Returns the number of p2p activity correctionses where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_P2PACTIVITYCORRECTIONS_WHERE);

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
	 * Returns the number of p2p activity correctionses where p2pActivityId = &#63;.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @return the number of matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public int countByP2pActivityId(long p2pActivityId)
		throws SystemException {
		Object[] finderArgs = new Object[] { p2pActivityId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P2PACTIVITYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_P2PACTIVITYCORRECTIONS_WHERE);

			query.append(_FINDER_COLUMN_P2PACTIVITYID_P2PACTIVITYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(p2pActivityId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P2PACTIVITYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of p2p activity correctionses where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @return the number of matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public int countByActIdAndUserId(long actId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { actId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACTIDANDUSERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_P2PACTIVITYCORRECTIONS_WHERE);

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
	 * Returns the number of p2p activity correctionses where p2pActivityId = &#63; and userId = &#63;.
	 *
	 * @param p2pActivityId the p2p activity ID
	 * @param userId the user ID
	 * @return the number of matching p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public int countByP2pActivityIdAndUserId(long p2pActivityId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { p2pActivityId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P2PACTIVITYIDANDUSERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_P2PACTIVITYCORRECTIONS_WHERE);

			query.append(_FINDER_COLUMN_P2PACTIVITYIDANDUSERID_P2PACTIVITYID_2);

			query.append(_FINDER_COLUMN_P2PACTIVITYIDANDUSERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(p2pActivityId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P2PACTIVITYIDANDUSERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of p2p activity correctionses.
	 *
	 * @return the number of p2p activity correctionses
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_P2PACTIVITYCORRECTIONS);

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
	 * Initializes the p2p activity corrections persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.P2pActivityCorrections")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<P2pActivityCorrections>> listenersList = new ArrayList<ModelListener<P2pActivityCorrections>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<P2pActivityCorrections>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(P2pActivityCorrectionsImpl.class.getName());
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
	private static final String _SQL_SELECT_P2PACTIVITYCORRECTIONS = "SELECT p2pActivityCorrections FROM P2pActivityCorrections p2pActivityCorrections";
	private static final String _SQL_SELECT_P2PACTIVITYCORRECTIONS_WHERE = "SELECT p2pActivityCorrections FROM P2pActivityCorrections p2pActivityCorrections WHERE ";
	private static final String _SQL_COUNT_P2PACTIVITYCORRECTIONS = "SELECT COUNT(p2pActivityCorrections) FROM P2pActivityCorrections p2pActivityCorrections";
	private static final String _SQL_COUNT_P2PACTIVITYCORRECTIONS_WHERE = "SELECT COUNT(p2pActivityCorrections) FROM P2pActivityCorrections p2pActivityCorrections WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "p2pActivityCorrections.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "p2pActivityCorrections.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(p2pActivityCorrections.uuid IS NULL OR p2pActivityCorrections.uuid = ?)";
	private static final String _FINDER_COLUMN_P2PACTIVITYID_P2PACTIVITYID_2 = "p2pActivityCorrections.p2pActivityId = ?";
	private static final String _FINDER_COLUMN_ACTIDANDUSERID_ACTID_2 = "p2pActivityCorrections.actId = ? AND ";
	private static final String _FINDER_COLUMN_ACTIDANDUSERID_USERID_2 = "p2pActivityCorrections.userId = ?";
	private static final String _FINDER_COLUMN_P2PACTIVITYIDANDUSERID_P2PACTIVITYID_2 =
		"p2pActivityCorrections.p2pActivityId = ? AND ";
	private static final String _FINDER_COLUMN_P2PACTIVITYIDANDUSERID_USERID_2 = "p2pActivityCorrections.userId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "p2pActivityCorrections.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No P2pActivityCorrections exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No P2pActivityCorrections exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(P2pActivityCorrectionsPersistenceImpl.class);
	private static P2pActivityCorrections _nullP2pActivityCorrections = new P2pActivityCorrectionsImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<P2pActivityCorrections> toCacheModel() {
				return _nullP2pActivityCorrectionsCacheModel;
			}
		};

	private static CacheModel<P2pActivityCorrections> _nullP2pActivityCorrectionsCacheModel =
		new CacheModel<P2pActivityCorrections>() {
			public P2pActivityCorrections toEntityModel() {
				return _nullP2pActivityCorrections;
			}
		};
}