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

import com.liferay.lms.NoSuchActivityTriesDeletedException;
import com.liferay.lms.model.ActivityTriesDeleted;
import com.liferay.lms.model.impl.ActivityTriesDeletedImpl;
import com.liferay.lms.model.impl.ActivityTriesDeletedModelImpl;

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
 * The persistence implementation for the activity tries deleted service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see ActivityTriesDeletedPersistence
 * @see ActivityTriesDeletedUtil
 * @generated
 */
public class ActivityTriesDeletedPersistenceImpl extends BasePersistenceImpl<ActivityTriesDeleted>
	implements ActivityTriesDeletedPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ActivityTriesDeletedUtil} to access the activity tries deleted persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ActivityTriesDeletedImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
			ActivityTriesDeletedModelImpl.FINDER_CACHE_ENABLED,
			ActivityTriesDeletedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
			ActivityTriesDeletedModelImpl.FINDER_CACHE_ENABLED,
			ActivityTriesDeletedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			ActivityTriesDeletedModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
			ActivityTriesDeletedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTIDSTATUS =
		new FinderPath(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
			ActivityTriesDeletedModelImpl.FINDER_CACHE_ENABLED,
			ActivityTriesDeletedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByActIdStatus",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDSTATUS =
		new FinderPath(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
			ActivityTriesDeletedModelImpl.FINDER_CACHE_ENABLED,
			ActivityTriesDeletedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByActIdStatus",
			new String[] { Long.class.getName(), Integer.class.getName() },
			ActivityTriesDeletedModelImpl.ACTID_COLUMN_BITMASK |
			ActivityTriesDeletedModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTIDSTATUS = new FinderPath(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
			ActivityTriesDeletedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByActIdStatus",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
			ActivityTriesDeletedModelImpl.FINDER_CACHE_ENABLED,
			ActivityTriesDeletedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
			ActivityTriesDeletedModelImpl.FINDER_CACHE_ENABLED,
			ActivityTriesDeletedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
			ActivityTriesDeletedModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the activity tries deleted in the entity cache if it is enabled.
	 *
	 * @param activityTriesDeleted the activity tries deleted
	 */
	public void cacheResult(ActivityTriesDeleted activityTriesDeleted) {
		EntityCacheUtil.putResult(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
			ActivityTriesDeletedImpl.class,
			activityTriesDeleted.getPrimaryKey(), activityTriesDeleted);

		activityTriesDeleted.resetOriginalValues();
	}

	/**
	 * Caches the activity tries deleteds in the entity cache if it is enabled.
	 *
	 * @param activityTriesDeleteds the activity tries deleteds
	 */
	public void cacheResult(List<ActivityTriesDeleted> activityTriesDeleteds) {
		for (ActivityTriesDeleted activityTriesDeleted : activityTriesDeleteds) {
			if (EntityCacheUtil.getResult(
						ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
						ActivityTriesDeletedImpl.class,
						activityTriesDeleted.getPrimaryKey()) == null) {
				cacheResult(activityTriesDeleted);
			}
			else {
				activityTriesDeleted.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all activity tries deleteds.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ActivityTriesDeletedImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ActivityTriesDeletedImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the activity tries deleted.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ActivityTriesDeleted activityTriesDeleted) {
		EntityCacheUtil.removeResult(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
			ActivityTriesDeletedImpl.class, activityTriesDeleted.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<ActivityTriesDeleted> activityTriesDeleteds) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ActivityTriesDeleted activityTriesDeleted : activityTriesDeleteds) {
			EntityCacheUtil.removeResult(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
				ActivityTriesDeletedImpl.class,
				activityTriesDeleted.getPrimaryKey());
		}
	}

	/**
	 * Creates a new activity tries deleted with the primary key. Does not add the activity tries deleted to the database.
	 *
	 * @param activityTriesDeletedId the primary key for the new activity tries deleted
	 * @return the new activity tries deleted
	 */
	public ActivityTriesDeleted create(long activityTriesDeletedId) {
		ActivityTriesDeleted activityTriesDeleted = new ActivityTriesDeletedImpl();

		activityTriesDeleted.setNew(true);
		activityTriesDeleted.setPrimaryKey(activityTriesDeletedId);

		return activityTriesDeleted;
	}

	/**
	 * Removes the activity tries deleted with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param activityTriesDeletedId the primary key of the activity tries deleted
	 * @return the activity tries deleted that was removed
	 * @throws com.liferay.lms.NoSuchActivityTriesDeletedException if a activity tries deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActivityTriesDeleted remove(long activityTriesDeletedId)
		throws NoSuchActivityTriesDeletedException, SystemException {
		return remove(Long.valueOf(activityTriesDeletedId));
	}

	/**
	 * Removes the activity tries deleted with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the activity tries deleted
	 * @return the activity tries deleted that was removed
	 * @throws com.liferay.lms.NoSuchActivityTriesDeletedException if a activity tries deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ActivityTriesDeleted remove(Serializable primaryKey)
		throws NoSuchActivityTriesDeletedException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ActivityTriesDeleted activityTriesDeleted = (ActivityTriesDeleted)session.get(ActivityTriesDeletedImpl.class,
					primaryKey);

			if (activityTriesDeleted == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActivityTriesDeletedException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(activityTriesDeleted);
		}
		catch (NoSuchActivityTriesDeletedException nsee) {
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
	protected ActivityTriesDeleted removeImpl(
		ActivityTriesDeleted activityTriesDeleted) throws SystemException {
		activityTriesDeleted = toUnwrappedModel(activityTriesDeleted);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, activityTriesDeleted);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(activityTriesDeleted);

		return activityTriesDeleted;
	}

	@Override
	public ActivityTriesDeleted updateImpl(
		com.liferay.lms.model.ActivityTriesDeleted activityTriesDeleted,
		boolean merge) throws SystemException {
		activityTriesDeleted = toUnwrappedModel(activityTriesDeleted);

		boolean isNew = activityTriesDeleted.isNew();

		ActivityTriesDeletedModelImpl activityTriesDeletedModelImpl = (ActivityTriesDeletedModelImpl)activityTriesDeleted;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, activityTriesDeleted, merge);

			activityTriesDeleted.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ActivityTriesDeletedModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((activityTriesDeletedModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(activityTriesDeletedModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(activityTriesDeletedModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((activityTriesDeletedModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDSTATUS.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(activityTriesDeletedModelImpl.getOriginalActId()),
						Integer.valueOf(activityTriesDeletedModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTIDSTATUS,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDSTATUS,
					args);

				args = new Object[] {
						Long.valueOf(activityTriesDeletedModelImpl.getActId()),
						Integer.valueOf(activityTriesDeletedModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTIDSTATUS,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDSTATUS,
					args);
			}
		}

		EntityCacheUtil.putResult(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
			ActivityTriesDeletedImpl.class,
			activityTriesDeleted.getPrimaryKey(), activityTriesDeleted);

		return activityTriesDeleted;
	}

	protected ActivityTriesDeleted toUnwrappedModel(
		ActivityTriesDeleted activityTriesDeleted) {
		if (activityTriesDeleted instanceof ActivityTriesDeletedImpl) {
			return activityTriesDeleted;
		}

		ActivityTriesDeletedImpl activityTriesDeletedImpl = new ActivityTriesDeletedImpl();

		activityTriesDeletedImpl.setNew(activityTriesDeleted.isNew());
		activityTriesDeletedImpl.setPrimaryKey(activityTriesDeleted.getPrimaryKey());

		activityTriesDeletedImpl.setActivityTriesDeletedId(activityTriesDeleted.getActivityTriesDeletedId());
		activityTriesDeletedImpl.setGroupId(activityTriesDeleted.getGroupId());
		activityTriesDeletedImpl.setActId(activityTriesDeleted.getActId());
		activityTriesDeletedImpl.setUserId(activityTriesDeleted.getUserId());
		activityTriesDeletedImpl.setStartDate(activityTriesDeleted.getStartDate());
		activityTriesDeletedImpl.setEndDate(activityTriesDeleted.getEndDate());
		activityTriesDeletedImpl.setStatus(activityTriesDeleted.getStatus());

		return activityTriesDeletedImpl;
	}

	/**
	 * Returns the activity tries deleted with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the activity tries deleted
	 * @return the activity tries deleted
	 * @throws com.liferay.portal.NoSuchModelException if a activity tries deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ActivityTriesDeleted findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the activity tries deleted with the primary key or throws a {@link com.liferay.lms.NoSuchActivityTriesDeletedException} if it could not be found.
	 *
	 * @param activityTriesDeletedId the primary key of the activity tries deleted
	 * @return the activity tries deleted
	 * @throws com.liferay.lms.NoSuchActivityTriesDeletedException if a activity tries deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActivityTriesDeleted findByPrimaryKey(long activityTriesDeletedId)
		throws NoSuchActivityTriesDeletedException, SystemException {
		ActivityTriesDeleted activityTriesDeleted = fetchByPrimaryKey(activityTriesDeletedId);

		if (activityTriesDeleted == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					activityTriesDeletedId);
			}

			throw new NoSuchActivityTriesDeletedException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				activityTriesDeletedId);
		}

		return activityTriesDeleted;
	}

	/**
	 * Returns the activity tries deleted with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the activity tries deleted
	 * @return the activity tries deleted, or <code>null</code> if a activity tries deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ActivityTriesDeleted fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the activity tries deleted with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param activityTriesDeletedId the primary key of the activity tries deleted
	 * @return the activity tries deleted, or <code>null</code> if a activity tries deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActivityTriesDeleted fetchByPrimaryKey(long activityTriesDeletedId)
		throws SystemException {
		ActivityTriesDeleted activityTriesDeleted = (ActivityTriesDeleted)EntityCacheUtil.getResult(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
				ActivityTriesDeletedImpl.class, activityTriesDeletedId);

		if (activityTriesDeleted == _nullActivityTriesDeleted) {
			return null;
		}

		if (activityTriesDeleted == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				activityTriesDeleted = (ActivityTriesDeleted)session.get(ActivityTriesDeletedImpl.class,
						Long.valueOf(activityTriesDeletedId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (activityTriesDeleted != null) {
					cacheResult(activityTriesDeleted);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ActivityTriesDeletedModelImpl.ENTITY_CACHE_ENABLED,
						ActivityTriesDeletedImpl.class, activityTriesDeletedId,
						_nullActivityTriesDeleted);
				}

				closeSession(session);
			}
		}

		return activityTriesDeleted;
	}

	/**
	 * Returns all the activity tries deleteds where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching activity tries deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActivityTriesDeleted> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the activity tries deleteds where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of activity tries deleteds
	 * @param end the upper bound of the range of activity tries deleteds (not inclusive)
	 * @return the range of matching activity tries deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActivityTriesDeleted> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the activity tries deleteds where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of activity tries deleteds
	 * @param end the upper bound of the range of activity tries deleteds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching activity tries deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActivityTriesDeleted> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<ActivityTriesDeleted> list = (List<ActivityTriesDeleted>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ActivityTriesDeleted activityTriesDeleted : list) {
				if ((groupId != activityTriesDeleted.getGroupId())) {
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

			query.append(_SQL_SELECT_ACTIVITYTRIESDELETED_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

				qPos.add(groupId);

				list = (List<ActivityTriesDeleted>)QueryUtil.list(q,
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
	 * Returns the first activity tries deleted in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching activity tries deleted
	 * @throws com.liferay.lms.NoSuchActivityTriesDeletedException if a matching activity tries deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActivityTriesDeleted findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityTriesDeletedException, SystemException {
		ActivityTriesDeleted activityTriesDeleted = fetchByGroupId_First(groupId,
				orderByComparator);

		if (activityTriesDeleted != null) {
			return activityTriesDeleted;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityTriesDeletedException(msg.toString());
	}

	/**
	 * Returns the first activity tries deleted in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching activity tries deleted, or <code>null</code> if a matching activity tries deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActivityTriesDeleted fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<ActivityTriesDeleted> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last activity tries deleted in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching activity tries deleted
	 * @throws com.liferay.lms.NoSuchActivityTriesDeletedException if a matching activity tries deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActivityTriesDeleted findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityTriesDeletedException, SystemException {
		ActivityTriesDeleted activityTriesDeleted = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (activityTriesDeleted != null) {
			return activityTriesDeleted;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityTriesDeletedException(msg.toString());
	}

	/**
	 * Returns the last activity tries deleted in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching activity tries deleted, or <code>null</code> if a matching activity tries deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActivityTriesDeleted fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<ActivityTriesDeleted> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the activity tries deleteds before and after the current activity tries deleted in the ordered set where groupId = &#63;.
	 *
	 * @param activityTriesDeletedId the primary key of the current activity tries deleted
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next activity tries deleted
	 * @throws com.liferay.lms.NoSuchActivityTriesDeletedException if a activity tries deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActivityTriesDeleted[] findByGroupId_PrevAndNext(
		long activityTriesDeletedId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityTriesDeletedException, SystemException {
		ActivityTriesDeleted activityTriesDeleted = findByPrimaryKey(activityTriesDeletedId);

		Session session = null;

		try {
			session = openSession();

			ActivityTriesDeleted[] array = new ActivityTriesDeletedImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, activityTriesDeleted,
					groupId, orderByComparator, true);

			array[1] = activityTriesDeleted;

			array[2] = getByGroupId_PrevAndNext(session, activityTriesDeleted,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ActivityTriesDeleted getByGroupId_PrevAndNext(Session session,
		ActivityTriesDeleted activityTriesDeleted, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ACTIVITYTRIESDELETED_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(activityTriesDeleted);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ActivityTriesDeleted> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the activity tries deleteds where actId = &#63; and status = &#63;.
	 *
	 * @param actId the act ID
	 * @param status the status
	 * @return the matching activity tries deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActivityTriesDeleted> findByActIdStatus(long actId, int status)
		throws SystemException {
		return findByActIdStatus(actId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the activity tries deleteds where actId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param status the status
	 * @param start the lower bound of the range of activity tries deleteds
	 * @param end the upper bound of the range of activity tries deleteds (not inclusive)
	 * @return the range of matching activity tries deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActivityTriesDeleted> findByActIdStatus(long actId, int status,
		int start, int end) throws SystemException {
		return findByActIdStatus(actId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the activity tries deleteds where actId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param status the status
	 * @param start the lower bound of the range of activity tries deleteds
	 * @param end the upper bound of the range of activity tries deleteds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching activity tries deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActivityTriesDeleted> findByActIdStatus(long actId, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTIDSTATUS;
			finderArgs = new Object[] { actId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTIDSTATUS;
			finderArgs = new Object[] {
					actId, status,
					
					start, end, orderByComparator
				};
		}

		List<ActivityTriesDeleted> list = (List<ActivityTriesDeleted>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ActivityTriesDeleted activityTriesDeleted : list) {
				if ((actId != activityTriesDeleted.getActId()) ||
						(status != activityTriesDeleted.getStatus())) {
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

			query.append(_SQL_SELECT_ACTIVITYTRIESDELETED_WHERE);

			query.append(_FINDER_COLUMN_ACTIDSTATUS_ACTID_2);

			query.append(_FINDER_COLUMN_ACTIDSTATUS_STATUS_2);

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

				qPos.add(status);

				list = (List<ActivityTriesDeleted>)QueryUtil.list(q,
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
	 * Returns the first activity tries deleted in the ordered set where actId = &#63; and status = &#63;.
	 *
	 * @param actId the act ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching activity tries deleted
	 * @throws com.liferay.lms.NoSuchActivityTriesDeletedException if a matching activity tries deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActivityTriesDeleted findByActIdStatus_First(long actId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchActivityTriesDeletedException, SystemException {
		ActivityTriesDeleted activityTriesDeleted = fetchByActIdStatus_First(actId,
				status, orderByComparator);

		if (activityTriesDeleted != null) {
			return activityTriesDeleted;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityTriesDeletedException(msg.toString());
	}

	/**
	 * Returns the first activity tries deleted in the ordered set where actId = &#63; and status = &#63;.
	 *
	 * @param actId the act ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching activity tries deleted, or <code>null</code> if a matching activity tries deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActivityTriesDeleted fetchByActIdStatus_First(long actId,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		List<ActivityTriesDeleted> list = findByActIdStatus(actId, status, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last activity tries deleted in the ordered set where actId = &#63; and status = &#63;.
	 *
	 * @param actId the act ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching activity tries deleted
	 * @throws com.liferay.lms.NoSuchActivityTriesDeletedException if a matching activity tries deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActivityTriesDeleted findByActIdStatus_Last(long actId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchActivityTriesDeletedException, SystemException {
		ActivityTriesDeleted activityTriesDeleted = fetchByActIdStatus_Last(actId,
				status, orderByComparator);

		if (activityTriesDeleted != null) {
			return activityTriesDeleted;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityTriesDeletedException(msg.toString());
	}

	/**
	 * Returns the last activity tries deleted in the ordered set where actId = &#63; and status = &#63;.
	 *
	 * @param actId the act ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching activity tries deleted, or <code>null</code> if a matching activity tries deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActivityTriesDeleted fetchByActIdStatus_Last(long actId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByActIdStatus(actId, status);

		List<ActivityTriesDeleted> list = findByActIdStatus(actId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the activity tries deleteds before and after the current activity tries deleted in the ordered set where actId = &#63; and status = &#63;.
	 *
	 * @param activityTriesDeletedId the primary key of the current activity tries deleted
	 * @param actId the act ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next activity tries deleted
	 * @throws com.liferay.lms.NoSuchActivityTriesDeletedException if a activity tries deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActivityTriesDeleted[] findByActIdStatus_PrevAndNext(
		long activityTriesDeletedId, long actId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchActivityTriesDeletedException, SystemException {
		ActivityTriesDeleted activityTriesDeleted = findByPrimaryKey(activityTriesDeletedId);

		Session session = null;

		try {
			session = openSession();

			ActivityTriesDeleted[] array = new ActivityTriesDeletedImpl[3];

			array[0] = getByActIdStatus_PrevAndNext(session,
					activityTriesDeleted, actId, status, orderByComparator, true);

			array[1] = activityTriesDeleted;

			array[2] = getByActIdStatus_PrevAndNext(session,
					activityTriesDeleted, actId, status, orderByComparator,
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

	protected ActivityTriesDeleted getByActIdStatus_PrevAndNext(
		Session session, ActivityTriesDeleted activityTriesDeleted, long actId,
		int status, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ACTIVITYTRIESDELETED_WHERE);

		query.append(_FINDER_COLUMN_ACTIDSTATUS_ACTID_2);

		query.append(_FINDER_COLUMN_ACTIDSTATUS_STATUS_2);

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

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(activityTriesDeleted);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ActivityTriesDeleted> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the activity tries deleteds.
	 *
	 * @return the activity tries deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActivityTriesDeleted> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the activity tries deleteds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of activity tries deleteds
	 * @param end the upper bound of the range of activity tries deleteds (not inclusive)
	 * @return the range of activity tries deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActivityTriesDeleted> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the activity tries deleteds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of activity tries deleteds
	 * @param end the upper bound of the range of activity tries deleteds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of activity tries deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActivityTriesDeleted> findAll(int start, int end,
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

		List<ActivityTriesDeleted> list = (List<ActivityTriesDeleted>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ACTIVITYTRIESDELETED);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ACTIVITYTRIESDELETED;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ActivityTriesDeleted>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ActivityTriesDeleted>)QueryUtil.list(q,
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
	 * Removes all the activity tries deleteds where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (ActivityTriesDeleted activityTriesDeleted : findByGroupId(groupId)) {
			remove(activityTriesDeleted);
		}
	}

	/**
	 * Removes all the activity tries deleteds where actId = &#63; and status = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByActIdStatus(long actId, int status)
		throws SystemException {
		for (ActivityTriesDeleted activityTriesDeleted : findByActIdStatus(
				actId, status)) {
			remove(activityTriesDeleted);
		}
	}

	/**
	 * Removes all the activity tries deleteds from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ActivityTriesDeleted activityTriesDeleted : findAll()) {
			remove(activityTriesDeleted);
		}
	}

	/**
	 * Returns the number of activity tries deleteds where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching activity tries deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ACTIVITYTRIESDELETED_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of activity tries deleteds where actId = &#63; and status = &#63;.
	 *
	 * @param actId the act ID
	 * @param status the status
	 * @return the number of matching activity tries deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public int countByActIdStatus(long actId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { actId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACTIDSTATUS,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ACTIVITYTRIESDELETED_WHERE);

			query.append(_FINDER_COLUMN_ACTIDSTATUS_ACTID_2);

			query.append(_FINDER_COLUMN_ACTIDSTATUS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACTIDSTATUS,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of activity tries deleteds.
	 *
	 * @return the number of activity tries deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ACTIVITYTRIESDELETED);

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
	 * Initializes the activity tries deleted persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.ActivityTriesDeleted")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ActivityTriesDeleted>> listenersList = new ArrayList<ModelListener<ActivityTriesDeleted>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ActivityTriesDeleted>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(ActivityTriesDeletedImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = ActivityTriesDeletedPersistence.class)
	protected ActivityTriesDeletedPersistence activityTriesDeletedPersistence;
	@BeanReference(type = AsynchronousProcessAuditPersistence.class)
	protected AsynchronousProcessAuditPersistence asynchronousProcessAuditPersistence;
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
	@BeanReference(type = CourseTypePersistence.class)
	protected CourseTypePersistence courseTypePersistence;
	@BeanReference(type = CourseTypeRelationPersistence.class)
	protected CourseTypeRelationPersistence courseTypeRelationPersistence;
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
	@BeanReference(type = SchedulePersistence.class)
	protected SchedulePersistence schedulePersistence;
	@BeanReference(type = SurveyResultPersistence.class)
	protected SurveyResultPersistence surveyResultPersistence;
	@BeanReference(type = TestAnswerPersistence.class)
	protected TestAnswerPersistence testAnswerPersistence;
	@BeanReference(type = TestQuestionPersistence.class)
	protected TestQuestionPersistence testQuestionPersistence;
	@BeanReference(type = UserCertificateDownloadPersistence.class)
	protected UserCertificateDownloadPersistence userCertificateDownloadPersistence;
	@BeanReference(type = UserCompetencePersistence.class)
	protected UserCompetencePersistence userCompetencePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_ACTIVITYTRIESDELETED = "SELECT activityTriesDeleted FROM ActivityTriesDeleted activityTriesDeleted";
	private static final String _SQL_SELECT_ACTIVITYTRIESDELETED_WHERE = "SELECT activityTriesDeleted FROM ActivityTriesDeleted activityTriesDeleted WHERE ";
	private static final String _SQL_COUNT_ACTIVITYTRIESDELETED = "SELECT COUNT(activityTriesDeleted) FROM ActivityTriesDeleted activityTriesDeleted";
	private static final String _SQL_COUNT_ACTIVITYTRIESDELETED_WHERE = "SELECT COUNT(activityTriesDeleted) FROM ActivityTriesDeleted activityTriesDeleted WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "activityTriesDeleted.groupId = ?";
	private static final String _FINDER_COLUMN_ACTIDSTATUS_ACTID_2 = "activityTriesDeleted.actId = ? AND ";
	private static final String _FINDER_COLUMN_ACTIDSTATUS_STATUS_2 = "activityTriesDeleted.status = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "activityTriesDeleted.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ActivityTriesDeleted exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ActivityTriesDeleted exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(ActivityTriesDeletedPersistenceImpl.class);
	private static ActivityTriesDeleted _nullActivityTriesDeleted = new ActivityTriesDeletedImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ActivityTriesDeleted> toCacheModel() {
				return _nullActivityTriesDeletedCacheModel;
			}
		};

	private static CacheModel<ActivityTriesDeleted> _nullActivityTriesDeletedCacheModel =
		new CacheModel<ActivityTriesDeleted>() {
			public ActivityTriesDeleted toEntityModel() {
				return _nullActivityTriesDeleted;
			}
		};
}