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

import com.liferay.lms.NoSuchScheduleException;
import com.liferay.lms.model.Schedule;
import com.liferay.lms.model.impl.ScheduleImpl;
import com.liferay.lms.model.impl.ScheduleModelImpl;

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
 * The persistence implementation for the schedule service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see SchedulePersistence
 * @see ScheduleUtil
 * @generated
 */
public class SchedulePersistenceImpl extends BasePersistenceImpl<Schedule>
	implements SchedulePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ScheduleUtil} to access the schedule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ScheduleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_TEAMID = new FinderPath(ScheduleModelImpl.ENTITY_CACHE_ENABLED,
			ScheduleModelImpl.FINDER_CACHE_ENABLED, ScheduleImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByTeamId",
			new String[] { Long.class.getName() },
			ScheduleModelImpl.TEAMID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TEAMID = new FinderPath(ScheduleModelImpl.ENTITY_CACHE_ENABLED,
			ScheduleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTeamId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ScheduleModelImpl.ENTITY_CACHE_ENABLED,
			ScheduleModelImpl.FINDER_CACHE_ENABLED, ScheduleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ScheduleModelImpl.ENTITY_CACHE_ENABLED,
			ScheduleModelImpl.FINDER_CACHE_ENABLED, ScheduleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ScheduleModelImpl.ENTITY_CACHE_ENABLED,
			ScheduleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the schedule in the entity cache if it is enabled.
	 *
	 * @param schedule the schedule
	 */
	public void cacheResult(Schedule schedule) {
		EntityCacheUtil.putResult(ScheduleModelImpl.ENTITY_CACHE_ENABLED,
			ScheduleImpl.class, schedule.getPrimaryKey(), schedule);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_TEAMID,
			new Object[] { Long.valueOf(schedule.getTeamId()) }, schedule);

		schedule.resetOriginalValues();
	}

	/**
	 * Caches the schedules in the entity cache if it is enabled.
	 *
	 * @param schedules the schedules
	 */
	public void cacheResult(List<Schedule> schedules) {
		for (Schedule schedule : schedules) {
			if (EntityCacheUtil.getResult(
						ScheduleModelImpl.ENTITY_CACHE_ENABLED,
						ScheduleImpl.class, schedule.getPrimaryKey()) == null) {
				cacheResult(schedule);
			}
			else {
				schedule.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all schedules.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ScheduleImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ScheduleImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the schedule.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Schedule schedule) {
		EntityCacheUtil.removeResult(ScheduleModelImpl.ENTITY_CACHE_ENABLED,
			ScheduleImpl.class, schedule.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(schedule);
	}

	@Override
	public void clearCache(List<Schedule> schedules) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Schedule schedule : schedules) {
			EntityCacheUtil.removeResult(ScheduleModelImpl.ENTITY_CACHE_ENABLED,
				ScheduleImpl.class, schedule.getPrimaryKey());

			clearUniqueFindersCache(schedule);
		}
	}

	protected void clearUniqueFindersCache(Schedule schedule) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_TEAMID,
			new Object[] { Long.valueOf(schedule.getTeamId()) });
	}

	/**
	 * Creates a new schedule with the primary key. Does not add the schedule to the database.
	 *
	 * @param secheduleId the primary key for the new schedule
	 * @return the new schedule
	 */
	public Schedule create(long secheduleId) {
		Schedule schedule = new ScheduleImpl();

		schedule.setNew(true);
		schedule.setPrimaryKey(secheduleId);

		return schedule;
	}

	/**
	 * Removes the schedule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param secheduleId the primary key of the schedule
	 * @return the schedule that was removed
	 * @throws com.liferay.lms.NoSuchScheduleException if a schedule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Schedule remove(long secheduleId)
		throws NoSuchScheduleException, SystemException {
		return remove(Long.valueOf(secheduleId));
	}

	/**
	 * Removes the schedule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the schedule
	 * @return the schedule that was removed
	 * @throws com.liferay.lms.NoSuchScheduleException if a schedule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Schedule remove(Serializable primaryKey)
		throws NoSuchScheduleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Schedule schedule = (Schedule)session.get(ScheduleImpl.class,
					primaryKey);

			if (schedule == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchScheduleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(schedule);
		}
		catch (NoSuchScheduleException nsee) {
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
	protected Schedule removeImpl(Schedule schedule) throws SystemException {
		schedule = toUnwrappedModel(schedule);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, schedule);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(schedule);

		return schedule;
	}

	@Override
	public Schedule updateImpl(com.liferay.lms.model.Schedule schedule,
		boolean merge) throws SystemException {
		schedule = toUnwrappedModel(schedule);

		boolean isNew = schedule.isNew();

		ScheduleModelImpl scheduleModelImpl = (ScheduleModelImpl)schedule;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, schedule, merge);

			schedule.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ScheduleModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(ScheduleModelImpl.ENTITY_CACHE_ENABLED,
			ScheduleImpl.class, schedule.getPrimaryKey(), schedule);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_TEAMID,
				new Object[] { Long.valueOf(schedule.getTeamId()) }, schedule);
		}
		else {
			if ((scheduleModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_TEAMID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scheduleModelImpl.getOriginalTeamId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TEAMID, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_TEAMID, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_TEAMID,
					new Object[] { Long.valueOf(schedule.getTeamId()) },
					schedule);
			}
		}

		return schedule;
	}

	protected Schedule toUnwrappedModel(Schedule schedule) {
		if (schedule instanceof ScheduleImpl) {
			return schedule;
		}

		ScheduleImpl scheduleImpl = new ScheduleImpl();

		scheduleImpl.setNew(schedule.isNew());
		scheduleImpl.setPrimaryKey(schedule.getPrimaryKey());

		scheduleImpl.setSecheduleId(schedule.getSecheduleId());
		scheduleImpl.setTeamId(schedule.getTeamId());
		scheduleImpl.setStartDate(schedule.getStartDate());
		scheduleImpl.setEndDate(schedule.getEndDate());

		return scheduleImpl;
	}

	/**
	 * Returns the schedule with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the schedule
	 * @return the schedule
	 * @throws com.liferay.portal.NoSuchModelException if a schedule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Schedule findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the schedule with the primary key or throws a {@link com.liferay.lms.NoSuchScheduleException} if it could not be found.
	 *
	 * @param secheduleId the primary key of the schedule
	 * @return the schedule
	 * @throws com.liferay.lms.NoSuchScheduleException if a schedule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Schedule findByPrimaryKey(long secheduleId)
		throws NoSuchScheduleException, SystemException {
		Schedule schedule = fetchByPrimaryKey(secheduleId);

		if (schedule == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + secheduleId);
			}

			throw new NoSuchScheduleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				secheduleId);
		}

		return schedule;
	}

	/**
	 * Returns the schedule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the schedule
	 * @return the schedule, or <code>null</code> if a schedule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Schedule fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the schedule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param secheduleId the primary key of the schedule
	 * @return the schedule, or <code>null</code> if a schedule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Schedule fetchByPrimaryKey(long secheduleId)
		throws SystemException {
		Schedule schedule = (Schedule)EntityCacheUtil.getResult(ScheduleModelImpl.ENTITY_CACHE_ENABLED,
				ScheduleImpl.class, secheduleId);

		if (schedule == _nullSchedule) {
			return null;
		}

		if (schedule == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				schedule = (Schedule)session.get(ScheduleImpl.class,
						Long.valueOf(secheduleId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (schedule != null) {
					cacheResult(schedule);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ScheduleModelImpl.ENTITY_CACHE_ENABLED,
						ScheduleImpl.class, secheduleId, _nullSchedule);
				}

				closeSession(session);
			}
		}

		return schedule;
	}

	/**
	 * Returns the schedule where teamId = &#63; or throws a {@link com.liferay.lms.NoSuchScheduleException} if it could not be found.
	 *
	 * @param teamId the team ID
	 * @return the matching schedule
	 * @throws com.liferay.lms.NoSuchScheduleException if a matching schedule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Schedule findByTeamId(long teamId)
		throws NoSuchScheduleException, SystemException {
		Schedule schedule = fetchByTeamId(teamId);

		if (schedule == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("teamId=");
			msg.append(teamId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchScheduleException(msg.toString());
		}

		return schedule;
	}

	/**
	 * Returns the schedule where teamId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param teamId the team ID
	 * @return the matching schedule, or <code>null</code> if a matching schedule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Schedule fetchByTeamId(long teamId) throws SystemException {
		return fetchByTeamId(teamId, true);
	}

	/**
	 * Returns the schedule where teamId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param teamId the team ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching schedule, or <code>null</code> if a matching schedule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Schedule fetchByTeamId(long teamId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { teamId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_TEAMID,
					finderArgs, this);
		}

		if (result instanceof Schedule) {
			Schedule schedule = (Schedule)result;

			if ((teamId != schedule.getTeamId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_SCHEDULE_WHERE);

			query.append(_FINDER_COLUMN_TEAMID_TEAMID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(teamId);

				List<Schedule> list = q.list();

				result = list;

				Schedule schedule = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_TEAMID,
						finderArgs, list);
				}
				else {
					schedule = list.get(0);

					cacheResult(schedule);

					if ((schedule.getTeamId() != teamId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_TEAMID,
							finderArgs, schedule);
					}
				}

				return schedule;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_TEAMID,
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
				return (Schedule)result;
			}
		}
	}

	/**
	 * Returns all the schedules.
	 *
	 * @return the schedules
	 * @throws SystemException if a system exception occurred
	 */
	public List<Schedule> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the schedules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of schedules
	 * @param end the upper bound of the range of schedules (not inclusive)
	 * @return the range of schedules
	 * @throws SystemException if a system exception occurred
	 */
	public List<Schedule> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the schedules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of schedules
	 * @param end the upper bound of the range of schedules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of schedules
	 * @throws SystemException if a system exception occurred
	 */
	public List<Schedule> findAll(int start, int end,
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

		List<Schedule> list = (List<Schedule>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SCHEDULE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SCHEDULE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Schedule>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Schedule>)QueryUtil.list(q, getDialect(),
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
	 * Removes the schedule where teamId = &#63; from the database.
	 *
	 * @param teamId the team ID
	 * @return the schedule that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Schedule removeByTeamId(long teamId)
		throws NoSuchScheduleException, SystemException {
		Schedule schedule = findByTeamId(teamId);

		return remove(schedule);
	}

	/**
	 * Removes all the schedules from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Schedule schedule : findAll()) {
			remove(schedule);
		}
	}

	/**
	 * Returns the number of schedules where teamId = &#63;.
	 *
	 * @param teamId the team ID
	 * @return the number of matching schedules
	 * @throws SystemException if a system exception occurred
	 */
	public int countByTeamId(long teamId) throws SystemException {
		Object[] finderArgs = new Object[] { teamId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TEAMID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCHEDULE_WHERE);

			query.append(_FINDER_COLUMN_TEAMID_TEAMID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(teamId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TEAMID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of schedules.
	 *
	 * @return the number of schedules
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SCHEDULE);

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
	 * Initializes the schedule persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.Schedule")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Schedule>> listenersList = new ArrayList<ModelListener<Schedule>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Schedule>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(ScheduleImpl.class.getName());
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
	private static final String _SQL_SELECT_SCHEDULE = "SELECT schedule FROM Schedule schedule";
	private static final String _SQL_SELECT_SCHEDULE_WHERE = "SELECT schedule FROM Schedule schedule WHERE ";
	private static final String _SQL_COUNT_SCHEDULE = "SELECT COUNT(schedule) FROM Schedule schedule";
	private static final String _SQL_COUNT_SCHEDULE_WHERE = "SELECT COUNT(schedule) FROM Schedule schedule WHERE ";
	private static final String _FINDER_COLUMN_TEAMID_TEAMID_2 = "schedule.teamId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "schedule.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Schedule exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Schedule exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(SchedulePersistenceImpl.class);
	private static Schedule _nullSchedule = new ScheduleImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Schedule> toCacheModel() {
				return _nullScheduleCacheModel;
			}
		};

	private static CacheModel<Schedule> _nullScheduleCacheModel = new CacheModel<Schedule>() {
			public Schedule toEntityModel() {
				return _nullSchedule;
			}
		};
}