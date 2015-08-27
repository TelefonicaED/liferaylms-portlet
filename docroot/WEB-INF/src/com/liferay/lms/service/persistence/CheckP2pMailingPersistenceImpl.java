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

import com.liferay.lms.NoSuchCheckP2pMailingException;
import com.liferay.lms.model.CheckP2pMailing;
import com.liferay.lms.model.impl.CheckP2pMailingImpl;
import com.liferay.lms.model.impl.CheckP2pMailingModelImpl;

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
 * The persistence implementation for the check p2p mailing service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CheckP2pMailingPersistence
 * @see CheckP2pMailingUtil
 * @generated
 */
public class CheckP2pMailingPersistenceImpl extends BasePersistenceImpl<CheckP2pMailing>
	implements CheckP2pMailingPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CheckP2pMailingUtil} to access the check p2p mailing persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CheckP2pMailingImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_ACTID = new FinderPath(CheckP2pMailingModelImpl.ENTITY_CACHE_ENABLED,
			CheckP2pMailingModelImpl.FINDER_CACHE_ENABLED,
			CheckP2pMailingImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByactId", new String[] { Long.class.getName() },
			CheckP2pMailingModelImpl.ACTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTID = new FinderPath(CheckP2pMailingModelImpl.ENTITY_CACHE_ENABLED,
			CheckP2pMailingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByactId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CheckP2pMailingModelImpl.ENTITY_CACHE_ENABLED,
			CheckP2pMailingModelImpl.FINDER_CACHE_ENABLED,
			CheckP2pMailingImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CheckP2pMailingModelImpl.ENTITY_CACHE_ENABLED,
			CheckP2pMailingModelImpl.FINDER_CACHE_ENABLED,
			CheckP2pMailingImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CheckP2pMailingModelImpl.ENTITY_CACHE_ENABLED,
			CheckP2pMailingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the check p2p mailing in the entity cache if it is enabled.
	 *
	 * @param checkP2pMailing the check p2p mailing
	 */
	public void cacheResult(CheckP2pMailing checkP2pMailing) {
		EntityCacheUtil.putResult(CheckP2pMailingModelImpl.ENTITY_CACHE_ENABLED,
			CheckP2pMailingImpl.class, checkP2pMailing.getPrimaryKey(),
			checkP2pMailing);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACTID,
			new Object[] { Long.valueOf(checkP2pMailing.getActId()) },
			checkP2pMailing);

		checkP2pMailing.resetOriginalValues();
	}

	/**
	 * Caches the check p2p mailings in the entity cache if it is enabled.
	 *
	 * @param checkP2pMailings the check p2p mailings
	 */
	public void cacheResult(List<CheckP2pMailing> checkP2pMailings) {
		for (CheckP2pMailing checkP2pMailing : checkP2pMailings) {
			if (EntityCacheUtil.getResult(
						CheckP2pMailingModelImpl.ENTITY_CACHE_ENABLED,
						CheckP2pMailingImpl.class,
						checkP2pMailing.getPrimaryKey()) == null) {
				cacheResult(checkP2pMailing);
			}
			else {
				checkP2pMailing.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all check p2p mailings.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CheckP2pMailingImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CheckP2pMailingImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the check p2p mailing.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CheckP2pMailing checkP2pMailing) {
		EntityCacheUtil.removeResult(CheckP2pMailingModelImpl.ENTITY_CACHE_ENABLED,
			CheckP2pMailingImpl.class, checkP2pMailing.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(checkP2pMailing);
	}

	@Override
	public void clearCache(List<CheckP2pMailing> checkP2pMailings) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CheckP2pMailing checkP2pMailing : checkP2pMailings) {
			EntityCacheUtil.removeResult(CheckP2pMailingModelImpl.ENTITY_CACHE_ENABLED,
				CheckP2pMailingImpl.class, checkP2pMailing.getPrimaryKey());

			clearUniqueFindersCache(checkP2pMailing);
		}
	}

	protected void clearUniqueFindersCache(CheckP2pMailing checkP2pMailing) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ACTID,
			new Object[] { Long.valueOf(checkP2pMailing.getActId()) });
	}

	/**
	 * Creates a new check p2p mailing with the primary key. Does not add the check p2p mailing to the database.
	 *
	 * @param checkP2pId the primary key for the new check p2p mailing
	 * @return the new check p2p mailing
	 */
	public CheckP2pMailing create(long checkP2pId) {
		CheckP2pMailing checkP2pMailing = new CheckP2pMailingImpl();

		checkP2pMailing.setNew(true);
		checkP2pMailing.setPrimaryKey(checkP2pId);

		return checkP2pMailing;
	}

	/**
	 * Removes the check p2p mailing with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param checkP2pId the primary key of the check p2p mailing
	 * @return the check p2p mailing that was removed
	 * @throws com.liferay.lms.NoSuchCheckP2pMailingException if a check p2p mailing with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CheckP2pMailing remove(long checkP2pId)
		throws NoSuchCheckP2pMailingException, SystemException {
		return remove(Long.valueOf(checkP2pId));
	}

	/**
	 * Removes the check p2p mailing with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the check p2p mailing
	 * @return the check p2p mailing that was removed
	 * @throws com.liferay.lms.NoSuchCheckP2pMailingException if a check p2p mailing with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CheckP2pMailing remove(Serializable primaryKey)
		throws NoSuchCheckP2pMailingException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CheckP2pMailing checkP2pMailing = (CheckP2pMailing)session.get(CheckP2pMailingImpl.class,
					primaryKey);

			if (checkP2pMailing == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCheckP2pMailingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(checkP2pMailing);
		}
		catch (NoSuchCheckP2pMailingException nsee) {
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
	protected CheckP2pMailing removeImpl(CheckP2pMailing checkP2pMailing)
		throws SystemException {
		checkP2pMailing = toUnwrappedModel(checkP2pMailing);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, checkP2pMailing);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(checkP2pMailing);

		return checkP2pMailing;
	}

	@Override
	public CheckP2pMailing updateImpl(
		com.liferay.lms.model.CheckP2pMailing checkP2pMailing, boolean merge)
		throws SystemException {
		checkP2pMailing = toUnwrappedModel(checkP2pMailing);

		boolean isNew = checkP2pMailing.isNew();

		CheckP2pMailingModelImpl checkP2pMailingModelImpl = (CheckP2pMailingModelImpl)checkP2pMailing;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, checkP2pMailing, merge);

			checkP2pMailing.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !CheckP2pMailingModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(CheckP2pMailingModelImpl.ENTITY_CACHE_ENABLED,
			CheckP2pMailingImpl.class, checkP2pMailing.getPrimaryKey(),
			checkP2pMailing);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACTID,
				new Object[] { Long.valueOf(checkP2pMailing.getActId()) },
				checkP2pMailing);
		}
		else {
			if ((checkP2pMailingModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_ACTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(checkP2pMailingModelImpl.getOriginalActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTID, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ACTID, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACTID,
					new Object[] { Long.valueOf(checkP2pMailing.getActId()) },
					checkP2pMailing);
			}
		}

		return checkP2pMailing;
	}

	protected CheckP2pMailing toUnwrappedModel(CheckP2pMailing checkP2pMailing) {
		if (checkP2pMailing instanceof CheckP2pMailingImpl) {
			return checkP2pMailing;
		}

		CheckP2pMailingImpl checkP2pMailingImpl = new CheckP2pMailingImpl();

		checkP2pMailingImpl.setNew(checkP2pMailing.isNew());
		checkP2pMailingImpl.setPrimaryKey(checkP2pMailing.getPrimaryKey());

		checkP2pMailingImpl.setCheckP2pId(checkP2pMailing.getCheckP2pId());
		checkP2pMailingImpl.setActId(checkP2pMailing.getActId());
		checkP2pMailingImpl.setDate(checkP2pMailing.getDate());

		return checkP2pMailingImpl;
	}

	/**
	 * Returns the check p2p mailing with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the check p2p mailing
	 * @return the check p2p mailing
	 * @throws com.liferay.portal.NoSuchModelException if a check p2p mailing with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CheckP2pMailing findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the check p2p mailing with the primary key or throws a {@link com.liferay.lms.NoSuchCheckP2pMailingException} if it could not be found.
	 *
	 * @param checkP2pId the primary key of the check p2p mailing
	 * @return the check p2p mailing
	 * @throws com.liferay.lms.NoSuchCheckP2pMailingException if a check p2p mailing with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CheckP2pMailing findByPrimaryKey(long checkP2pId)
		throws NoSuchCheckP2pMailingException, SystemException {
		CheckP2pMailing checkP2pMailing = fetchByPrimaryKey(checkP2pId);

		if (checkP2pMailing == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + checkP2pId);
			}

			throw new NoSuchCheckP2pMailingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				checkP2pId);
		}

		return checkP2pMailing;
	}

	/**
	 * Returns the check p2p mailing with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the check p2p mailing
	 * @return the check p2p mailing, or <code>null</code> if a check p2p mailing with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CheckP2pMailing fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the check p2p mailing with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param checkP2pId the primary key of the check p2p mailing
	 * @return the check p2p mailing, or <code>null</code> if a check p2p mailing with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CheckP2pMailing fetchByPrimaryKey(long checkP2pId)
		throws SystemException {
		CheckP2pMailing checkP2pMailing = (CheckP2pMailing)EntityCacheUtil.getResult(CheckP2pMailingModelImpl.ENTITY_CACHE_ENABLED,
				CheckP2pMailingImpl.class, checkP2pId);

		if (checkP2pMailing == _nullCheckP2pMailing) {
			return null;
		}

		if (checkP2pMailing == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				checkP2pMailing = (CheckP2pMailing)session.get(CheckP2pMailingImpl.class,
						Long.valueOf(checkP2pId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (checkP2pMailing != null) {
					cacheResult(checkP2pMailing);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(CheckP2pMailingModelImpl.ENTITY_CACHE_ENABLED,
						CheckP2pMailingImpl.class, checkP2pId,
						_nullCheckP2pMailing);
				}

				closeSession(session);
			}
		}

		return checkP2pMailing;
	}

	/**
	 * Returns the check p2p mailing where actId = &#63; or throws a {@link com.liferay.lms.NoSuchCheckP2pMailingException} if it could not be found.
	 *
	 * @param actId the act ID
	 * @return the matching check p2p mailing
	 * @throws com.liferay.lms.NoSuchCheckP2pMailingException if a matching check p2p mailing could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CheckP2pMailing findByactId(long actId)
		throws NoSuchCheckP2pMailingException, SystemException {
		CheckP2pMailing checkP2pMailing = fetchByactId(actId);

		if (checkP2pMailing == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("actId=");
			msg.append(actId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCheckP2pMailingException(msg.toString());
		}

		return checkP2pMailing;
	}

	/**
	 * Returns the check p2p mailing where actId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param actId the act ID
	 * @return the matching check p2p mailing, or <code>null</code> if a matching check p2p mailing could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CheckP2pMailing fetchByactId(long actId) throws SystemException {
		return fetchByactId(actId, true);
	}

	/**
	 * Returns the check p2p mailing where actId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param actId the act ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching check p2p mailing, or <code>null</code> if a matching check p2p mailing could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CheckP2pMailing fetchByactId(long actId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { actId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_ACTID,
					finderArgs, this);
		}

		if (result instanceof CheckP2pMailing) {
			CheckP2pMailing checkP2pMailing = (CheckP2pMailing)result;

			if ((actId != checkP2pMailing.getActId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_CHECKP2PMAILING_WHERE);

			query.append(_FINDER_COLUMN_ACTID_ACTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				List<CheckP2pMailing> list = q.list();

				result = list;

				CheckP2pMailing checkP2pMailing = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACTID,
						finderArgs, list);
				}
				else {
					checkP2pMailing = list.get(0);

					cacheResult(checkP2pMailing);

					if ((checkP2pMailing.getActId() != actId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACTID,
							finderArgs, checkP2pMailing);
					}
				}

				return checkP2pMailing;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ACTID,
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
				return (CheckP2pMailing)result;
			}
		}
	}

	/**
	 * Returns all the check p2p mailings.
	 *
	 * @return the check p2p mailings
	 * @throws SystemException if a system exception occurred
	 */
	public List<CheckP2pMailing> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	public List<CheckP2pMailing> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
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
	public List<CheckP2pMailing> findAll(int start, int end,
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

		List<CheckP2pMailing> list = (List<CheckP2pMailing>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_CHECKP2PMAILING);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CHECKP2PMAILING;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<CheckP2pMailing>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<CheckP2pMailing>)QueryUtil.list(q,
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
	 * Removes the check p2p mailing where actId = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @return the check p2p mailing that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public CheckP2pMailing removeByactId(long actId)
		throws NoSuchCheckP2pMailingException, SystemException {
		CheckP2pMailing checkP2pMailing = findByactId(actId);

		return remove(checkP2pMailing);
	}

	/**
	 * Removes all the check p2p mailings from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (CheckP2pMailing checkP2pMailing : findAll()) {
			remove(checkP2pMailing);
		}
	}

	/**
	 * Returns the number of check p2p mailings where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the number of matching check p2p mailings
	 * @throws SystemException if a system exception occurred
	 */
	public int countByactId(long actId) throws SystemException {
		Object[] finderArgs = new Object[] { actId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACTID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CHECKP2PMAILING_WHERE);

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
	 * Returns the number of check p2p mailings.
	 *
	 * @return the number of check p2p mailings
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CHECKP2PMAILING);

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
	 * Initializes the check p2p mailing persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.CheckP2pMailing")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CheckP2pMailing>> listenersList = new ArrayList<ModelListener<CheckP2pMailing>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CheckP2pMailing>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(CheckP2pMailingImpl.class.getName());
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
	private static final String _SQL_SELECT_CHECKP2PMAILING = "SELECT checkP2pMailing FROM CheckP2pMailing checkP2pMailing";
	private static final String _SQL_SELECT_CHECKP2PMAILING_WHERE = "SELECT checkP2pMailing FROM CheckP2pMailing checkP2pMailing WHERE ";
	private static final String _SQL_COUNT_CHECKP2PMAILING = "SELECT COUNT(checkP2pMailing) FROM CheckP2pMailing checkP2pMailing";
	private static final String _SQL_COUNT_CHECKP2PMAILING_WHERE = "SELECT COUNT(checkP2pMailing) FROM CheckP2pMailing checkP2pMailing WHERE ";
	private static final String _FINDER_COLUMN_ACTID_ACTID_2 = "checkP2pMailing.actId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "checkP2pMailing.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CheckP2pMailing exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CheckP2pMailing exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(CheckP2pMailingPersistenceImpl.class);
	private static CheckP2pMailing _nullCheckP2pMailing = new CheckP2pMailingImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<CheckP2pMailing> toCacheModel() {
				return _nullCheckP2pMailingCacheModel;
			}
		};

	private static CacheModel<CheckP2pMailing> _nullCheckP2pMailingCacheModel = new CacheModel<CheckP2pMailing>() {
			public CheckP2pMailing toEntityModel() {
				return _nullCheckP2pMailing;
			}
		};
}