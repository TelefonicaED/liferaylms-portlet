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

import com.liferay.lms.NoSuchCourseTypeLearningActivityException;
import com.liferay.lms.model.CourseTypeLearningActivity;
import com.liferay.lms.model.impl.CourseTypeLearningActivityImpl;
import com.liferay.lms.model.impl.CourseTypeLearningActivityModelImpl;

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
 * The persistence implementation for the course type learning activity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CourseTypeLearningActivityPersistence
 * @see CourseTypeLearningActivityUtil
 * @generated
 */
public class CourseTypeLearningActivityPersistenceImpl
	extends BasePersistenceImpl<CourseTypeLearningActivity>
	implements CourseTypeLearningActivityPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CourseTypeLearningActivityUtil} to access the course type learning activity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CourseTypeLearningActivityImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_COURSETYPELEARNINGACTIVITYID =
		new FinderPath(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeLearningActivityModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeLearningActivityImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCourseTypeLearningActivityId",
			new String[] { Long.class.getName() },
			CourseTypeLearningActivityModelImpl.COURSETYPELEARNINGACTIVITYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSETYPELEARNINGACTIVITYID =
		new FinderPath(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeLearningActivityModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCourseTypeLearningActivityId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COURSETYPEID =
		new FinderPath(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeLearningActivityModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeLearningActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCourseTypeId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID =
		new FinderPath(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeLearningActivityModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeLearningActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCourseTypeId",
			new String[] { Long.class.getName() },
			CourseTypeLearningActivityModelImpl.COURSETYPEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSETYPEID = new FinderPath(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeLearningActivityModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCourseTypeId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeLearningActivityModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeLearningActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeLearningActivityModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeLearningActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeLearningActivityModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

	/**
	 * Caches the course type learning activity in the entity cache if it is enabled.
	 *
	 * @param courseTypeLearningActivity the course type learning activity
	 */
	public void cacheResult(
		CourseTypeLearningActivity courseTypeLearningActivity) {
		EntityCacheUtil.putResult(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeLearningActivityImpl.class,
			courseTypeLearningActivity.getPrimaryKey(),
			courseTypeLearningActivity);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPELEARNINGACTIVITYID,
			new Object[] {
				Long.valueOf(
					courseTypeLearningActivity.getCourseTypeLearningActivityId())
			}, courseTypeLearningActivity);

		courseTypeLearningActivity.resetOriginalValues();
	}

	/**
	 * Caches the course type learning activities in the entity cache if it is enabled.
	 *
	 * @param courseTypeLearningActivities the course type learning activities
	 */
	public void cacheResult(
		List<CourseTypeLearningActivity> courseTypeLearningActivities) {
		for (CourseTypeLearningActivity courseTypeLearningActivity : courseTypeLearningActivities) {
			if (EntityCacheUtil.getResult(
						CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeLearningActivityImpl.class,
						courseTypeLearningActivity.getPrimaryKey()) == null) {
				cacheResult(courseTypeLearningActivity);
			}
			else {
				courseTypeLearningActivity.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all course type learning activities.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CourseTypeLearningActivityImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CourseTypeLearningActivityImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the course type learning activity.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CourseTypeLearningActivity courseTypeLearningActivity) {
		EntityCacheUtil.removeResult(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeLearningActivityImpl.class,
			courseTypeLearningActivity.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(courseTypeLearningActivity);
	}

	@Override
	public void clearCache(
		List<CourseTypeLearningActivity> courseTypeLearningActivities) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CourseTypeLearningActivity courseTypeLearningActivity : courseTypeLearningActivities) {
			EntityCacheUtil.removeResult(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeLearningActivityImpl.class,
				courseTypeLearningActivity.getPrimaryKey());

			clearUniqueFindersCache(courseTypeLearningActivity);
		}
	}

	protected void clearUniqueFindersCache(
		CourseTypeLearningActivity courseTypeLearningActivity) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPELEARNINGACTIVITYID,
			new Object[] {
				Long.valueOf(
					courseTypeLearningActivity.getCourseTypeLearningActivityId())
			});
	}

	/**
	 * Creates a new course type learning activity with the primary key. Does not add the course type learning activity to the database.
	 *
	 * @param courseTypeLearningActivityId the primary key for the new course type learning activity
	 * @return the new course type learning activity
	 */
	public CourseTypeLearningActivity create(long courseTypeLearningActivityId) {
		CourseTypeLearningActivity courseTypeLearningActivity = new CourseTypeLearningActivityImpl();

		courseTypeLearningActivity.setNew(true);
		courseTypeLearningActivity.setPrimaryKey(courseTypeLearningActivityId);

		return courseTypeLearningActivity;
	}

	/**
	 * Removes the course type learning activity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param courseTypeLearningActivityId the primary key of the course type learning activity
	 * @return the course type learning activity that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeLearningActivityException if a course type learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeLearningActivity remove(long courseTypeLearningActivityId)
		throws NoSuchCourseTypeLearningActivityException, SystemException {
		return remove(Long.valueOf(courseTypeLearningActivityId));
	}

	/**
	 * Removes the course type learning activity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the course type learning activity
	 * @return the course type learning activity that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeLearningActivityException if a course type learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeLearningActivity remove(Serializable primaryKey)
		throws NoSuchCourseTypeLearningActivityException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CourseTypeLearningActivity courseTypeLearningActivity = (CourseTypeLearningActivity)session.get(CourseTypeLearningActivityImpl.class,
					primaryKey);

			if (courseTypeLearningActivity == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCourseTypeLearningActivityException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(courseTypeLearningActivity);
		}
		catch (NoSuchCourseTypeLearningActivityException nsee) {
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
	protected CourseTypeLearningActivity removeImpl(
		CourseTypeLearningActivity courseTypeLearningActivity)
		throws SystemException {
		courseTypeLearningActivity = toUnwrappedModel(courseTypeLearningActivity);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, courseTypeLearningActivity);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(courseTypeLearningActivity);

		return courseTypeLearningActivity;
	}

	@Override
	public CourseTypeLearningActivity updateImpl(
		com.liferay.lms.model.CourseTypeLearningActivity courseTypeLearningActivity,
		boolean merge) throws SystemException {
		courseTypeLearningActivity = toUnwrappedModel(courseTypeLearningActivity);

		boolean isNew = courseTypeLearningActivity.isNew();

		CourseTypeLearningActivityModelImpl courseTypeLearningActivityModelImpl = (CourseTypeLearningActivityModelImpl)courseTypeLearningActivity;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, courseTypeLearningActivity, merge);

			courseTypeLearningActivity.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew ||
				!CourseTypeLearningActivityModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((courseTypeLearningActivityModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeLearningActivityModelImpl.getOriginalCourseTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID,
					args);

				args = new Object[] {
						Long.valueOf(courseTypeLearningActivityModelImpl.getCourseTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID,
					args);
			}
		}

		EntityCacheUtil.putResult(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeLearningActivityImpl.class,
			courseTypeLearningActivity.getPrimaryKey(),
			courseTypeLearningActivity);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPELEARNINGACTIVITYID,
				new Object[] {
					Long.valueOf(
						courseTypeLearningActivity.getCourseTypeLearningActivityId())
				}, courseTypeLearningActivity);
		}
		else {
			if ((courseTypeLearningActivityModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_COURSETYPELEARNINGACTIVITYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeLearningActivityModelImpl.getOriginalCourseTypeLearningActivityId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPELEARNINGACTIVITYID,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPELEARNINGACTIVITYID,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPELEARNINGACTIVITYID,
					new Object[] {
						Long.valueOf(
							courseTypeLearningActivity.getCourseTypeLearningActivityId())
					}, courseTypeLearningActivity);
			}
		}

		return courseTypeLearningActivity;
	}

	protected CourseTypeLearningActivity toUnwrappedModel(
		CourseTypeLearningActivity courseTypeLearningActivity) {
		if (courseTypeLearningActivity instanceof CourseTypeLearningActivityImpl) {
			return courseTypeLearningActivity;
		}

		CourseTypeLearningActivityImpl courseTypeLearningActivityImpl = new CourseTypeLearningActivityImpl();

		courseTypeLearningActivityImpl.setNew(courseTypeLearningActivity.isNew());
		courseTypeLearningActivityImpl.setPrimaryKey(courseTypeLearningActivity.getPrimaryKey());

		courseTypeLearningActivityImpl.setCourseTypeLearningActivityId(courseTypeLearningActivity.getCourseTypeLearningActivityId());
		courseTypeLearningActivityImpl.setCourseTypeId(courseTypeLearningActivity.getCourseTypeId());
		courseTypeLearningActivityImpl.setLearningActivityTypeId(courseTypeLearningActivity.getLearningActivityTypeId());

		return courseTypeLearningActivityImpl;
	}

	/**
	 * Returns the course type learning activity with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type learning activity
	 * @return the course type learning activity
	 * @throws com.liferay.portal.NoSuchModelException if a course type learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeLearningActivity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type learning activity with the primary key or throws a {@link com.liferay.lms.NoSuchCourseTypeLearningActivityException} if it could not be found.
	 *
	 * @param courseTypeLearningActivityId the primary key of the course type learning activity
	 * @return the course type learning activity
	 * @throws com.liferay.lms.NoSuchCourseTypeLearningActivityException if a course type learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeLearningActivity findByPrimaryKey(
		long courseTypeLearningActivityId)
		throws NoSuchCourseTypeLearningActivityException, SystemException {
		CourseTypeLearningActivity courseTypeLearningActivity = fetchByPrimaryKey(courseTypeLearningActivityId);

		if (courseTypeLearningActivity == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					courseTypeLearningActivityId);
			}

			throw new NoSuchCourseTypeLearningActivityException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				courseTypeLearningActivityId);
		}

		return courseTypeLearningActivity;
	}

	/**
	 * Returns the course type learning activity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type learning activity
	 * @return the course type learning activity, or <code>null</code> if a course type learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeLearningActivity fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type learning activity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param courseTypeLearningActivityId the primary key of the course type learning activity
	 * @return the course type learning activity, or <code>null</code> if a course type learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeLearningActivity fetchByPrimaryKey(
		long courseTypeLearningActivityId) throws SystemException {
		CourseTypeLearningActivity courseTypeLearningActivity = (CourseTypeLearningActivity)EntityCacheUtil.getResult(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeLearningActivityImpl.class,
				courseTypeLearningActivityId);

		if (courseTypeLearningActivity == _nullCourseTypeLearningActivity) {
			return null;
		}

		if (courseTypeLearningActivity == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				courseTypeLearningActivity = (CourseTypeLearningActivity)session.get(CourseTypeLearningActivityImpl.class,
						Long.valueOf(courseTypeLearningActivityId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (courseTypeLearningActivity != null) {
					cacheResult(courseTypeLearningActivity);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(CourseTypeLearningActivityModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeLearningActivityImpl.class,
						courseTypeLearningActivityId,
						_nullCourseTypeLearningActivity);
				}

				closeSession(session);
			}
		}

		return courseTypeLearningActivity;
	}

	/**
	 * Returns the course type learning activity where courseTypeLearningActivityId = &#63; or throws a {@link com.liferay.lms.NoSuchCourseTypeLearningActivityException} if it could not be found.
	 *
	 * @param courseTypeLearningActivityId the course type learning activity ID
	 * @return the matching course type learning activity
	 * @throws com.liferay.lms.NoSuchCourseTypeLearningActivityException if a matching course type learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeLearningActivity findByCourseTypeLearningActivityId(
		long courseTypeLearningActivityId)
		throws NoSuchCourseTypeLearningActivityException, SystemException {
		CourseTypeLearningActivity courseTypeLearningActivity = fetchByCourseTypeLearningActivityId(courseTypeLearningActivityId);

		if (courseTypeLearningActivity == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("courseTypeLearningActivityId=");
			msg.append(courseTypeLearningActivityId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCourseTypeLearningActivityException(msg.toString());
		}

		return courseTypeLearningActivity;
	}

	/**
	 * Returns the course type learning activity where courseTypeLearningActivityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param courseTypeLearningActivityId the course type learning activity ID
	 * @return the matching course type learning activity, or <code>null</code> if a matching course type learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeLearningActivity fetchByCourseTypeLearningActivityId(
		long courseTypeLearningActivityId) throws SystemException {
		return fetchByCourseTypeLearningActivityId(courseTypeLearningActivityId,
			true);
	}

	/**
	 * Returns the course type learning activity where courseTypeLearningActivityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param courseTypeLearningActivityId the course type learning activity ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching course type learning activity, or <code>null</code> if a matching course type learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeLearningActivity fetchByCourseTypeLearningActivityId(
		long courseTypeLearningActivityId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeLearningActivityId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_COURSETYPELEARNINGACTIVITYID,
					finderArgs, this);
		}

		if (result instanceof CourseTypeLearningActivity) {
			CourseTypeLearningActivity courseTypeLearningActivity = (CourseTypeLearningActivity)result;

			if ((courseTypeLearningActivityId != courseTypeLearningActivity.getCourseTypeLearningActivityId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_COURSETYPELEARNINGACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPELEARNINGACTIVITYID_COURSETYPELEARNINGACTIVITYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeLearningActivityId);

				List<CourseTypeLearningActivity> list = q.list();

				result = list;

				CourseTypeLearningActivity courseTypeLearningActivity = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPELEARNINGACTIVITYID,
						finderArgs, list);
				}
				else {
					courseTypeLearningActivity = list.get(0);

					cacheResult(courseTypeLearningActivity);

					if ((courseTypeLearningActivity.getCourseTypeLearningActivityId() != courseTypeLearningActivityId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPELEARNINGACTIVITYID,
							finderArgs, courseTypeLearningActivity);
					}
				}

				return courseTypeLearningActivity;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPELEARNINGACTIVITYID,
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
				return (CourseTypeLearningActivity)result;
			}
		}
	}

	/**
	 * Returns all the course type learning activities where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @return the matching course type learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeLearningActivity> findByCourseTypeId(
		long courseTypeId) throws SystemException {
		return findByCourseTypeId(courseTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course type learning activities where courseTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param start the lower bound of the range of course type learning activities
	 * @param end the upper bound of the range of course type learning activities (not inclusive)
	 * @return the range of matching course type learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeLearningActivity> findByCourseTypeId(
		long courseTypeId, int start, int end) throws SystemException {
		return findByCourseTypeId(courseTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the course type learning activities where courseTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param start the lower bound of the range of course type learning activities
	 * @param end the upper bound of the range of course type learning activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching course type learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeLearningActivity> findByCourseTypeId(
		long courseTypeId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID;
			finderArgs = new Object[] { courseTypeId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COURSETYPEID;
			finderArgs = new Object[] {
					courseTypeId,
					
					start, end, orderByComparator
				};
		}

		List<CourseTypeLearningActivity> list = (List<CourseTypeLearningActivity>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CourseTypeLearningActivity courseTypeLearningActivity : list) {
				if ((courseTypeId != courseTypeLearningActivity.getCourseTypeId())) {
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

			query.append(_SQL_SELECT_COURSETYPELEARNINGACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPEID_COURSETYPEID_2);

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

				qPos.add(courseTypeId);

				list = (List<CourseTypeLearningActivity>)QueryUtil.list(q,
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
	 * Returns the first course type learning activity in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type learning activity
	 * @throws com.liferay.lms.NoSuchCourseTypeLearningActivityException if a matching course type learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeLearningActivity findByCourseTypeId_First(
		long courseTypeId, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeLearningActivityException, SystemException {
		CourseTypeLearningActivity courseTypeLearningActivity = fetchByCourseTypeId_First(courseTypeId,
				orderByComparator);

		if (courseTypeLearningActivity != null) {
			return courseTypeLearningActivity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeLearningActivityException(msg.toString());
	}

	/**
	 * Returns the first course type learning activity in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type learning activity, or <code>null</code> if a matching course type learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeLearningActivity fetchByCourseTypeId_First(
		long courseTypeId, OrderByComparator orderByComparator)
		throws SystemException {
		List<CourseTypeLearningActivity> list = findByCourseTypeId(courseTypeId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last course type learning activity in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type learning activity
	 * @throws com.liferay.lms.NoSuchCourseTypeLearningActivityException if a matching course type learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeLearningActivity findByCourseTypeId_Last(
		long courseTypeId, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeLearningActivityException, SystemException {
		CourseTypeLearningActivity courseTypeLearningActivity = fetchByCourseTypeId_Last(courseTypeId,
				orderByComparator);

		if (courseTypeLearningActivity != null) {
			return courseTypeLearningActivity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeLearningActivityException(msg.toString());
	}

	/**
	 * Returns the last course type learning activity in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type learning activity, or <code>null</code> if a matching course type learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeLearningActivity fetchByCourseTypeId_Last(
		long courseTypeId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByCourseTypeId(courseTypeId);

		List<CourseTypeLearningActivity> list = findByCourseTypeId(courseTypeId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the course type learning activities before and after the current course type learning activity in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeLearningActivityId the primary key of the current course type learning activity
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next course type learning activity
	 * @throws com.liferay.lms.NoSuchCourseTypeLearningActivityException if a course type learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeLearningActivity[] findByCourseTypeId_PrevAndNext(
		long courseTypeLearningActivityId, long courseTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeLearningActivityException, SystemException {
		CourseTypeLearningActivity courseTypeLearningActivity = findByPrimaryKey(courseTypeLearningActivityId);

		Session session = null;

		try {
			session = openSession();

			CourseTypeLearningActivity[] array = new CourseTypeLearningActivityImpl[3];

			array[0] = getByCourseTypeId_PrevAndNext(session,
					courseTypeLearningActivity, courseTypeId,
					orderByComparator, true);

			array[1] = courseTypeLearningActivity;

			array[2] = getByCourseTypeId_PrevAndNext(session,
					courseTypeLearningActivity, courseTypeId,
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

	protected CourseTypeLearningActivity getByCourseTypeId_PrevAndNext(
		Session session, CourseTypeLearningActivity courseTypeLearningActivity,
		long courseTypeId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COURSETYPELEARNINGACTIVITY_WHERE);

		query.append(_FINDER_COLUMN_COURSETYPEID_COURSETYPEID_2);

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

		qPos.add(courseTypeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(courseTypeLearningActivity);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CourseTypeLearningActivity> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the course type learning activities.
	 *
	 * @return the course type learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeLearningActivity> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course type learning activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course type learning activities
	 * @param end the upper bound of the range of course type learning activities (not inclusive)
	 * @return the range of course type learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeLearningActivity> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the course type learning activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course type learning activities
	 * @param end the upper bound of the range of course type learning activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of course type learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeLearningActivity> findAll(int start, int end,
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

		List<CourseTypeLearningActivity> list = (List<CourseTypeLearningActivity>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_COURSETYPELEARNINGACTIVITY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COURSETYPELEARNINGACTIVITY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<CourseTypeLearningActivity>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<CourseTypeLearningActivity>)QueryUtil.list(q,
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
	 * Removes the course type learning activity where courseTypeLearningActivityId = &#63; from the database.
	 *
	 * @param courseTypeLearningActivityId the course type learning activity ID
	 * @return the course type learning activity that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeLearningActivity removeByCourseTypeLearningActivityId(
		long courseTypeLearningActivityId)
		throws NoSuchCourseTypeLearningActivityException, SystemException {
		CourseTypeLearningActivity courseTypeLearningActivity = findByCourseTypeLearningActivityId(courseTypeLearningActivityId);

		return remove(courseTypeLearningActivity);
	}

	/**
	 * Removes all the course type learning activities where courseTypeId = &#63; from the database.
	 *
	 * @param courseTypeId the course type ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCourseTypeId(long courseTypeId)
		throws SystemException {
		for (CourseTypeLearningActivity courseTypeLearningActivity : findByCourseTypeId(
				courseTypeId)) {
			remove(courseTypeLearningActivity);
		}
	}

	/**
	 * Removes all the course type learning activities from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (CourseTypeLearningActivity courseTypeLearningActivity : findAll()) {
			remove(courseTypeLearningActivity);
		}
	}

	/**
	 * Returns the number of course type learning activities where courseTypeLearningActivityId = &#63;.
	 *
	 * @param courseTypeLearningActivityId the course type learning activity ID
	 * @return the number of matching course type learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseTypeLearningActivityId(
		long courseTypeLearningActivityId) throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeLearningActivityId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSETYPELEARNINGACTIVITYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COURSETYPELEARNINGACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPELEARNINGACTIVITYID_COURSETYPELEARNINGACTIVITYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeLearningActivityId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COURSETYPELEARNINGACTIVITYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course type learning activities where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @return the number of matching course type learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseTypeId(long courseTypeId) throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COURSETYPELEARNINGACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPEID_COURSETYPEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course type learning activities.
	 *
	 * @return the number of course type learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COURSETYPELEARNINGACTIVITY);

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
	 * Initializes the course type learning activity persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.CourseTypeLearningActivity")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CourseTypeLearningActivity>> listenersList = new ArrayList<ModelListener<CourseTypeLearningActivity>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CourseTypeLearningActivity>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(CourseTypeLearningActivityImpl.class.getName());
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
	@BeanReference(type = CourseTypeCalificationTypePersistence.class)
	protected CourseTypeCalificationTypePersistence courseTypeCalificationTypePersistence;
	@BeanReference(type = CourseTypeCourseEvalPersistence.class)
	protected CourseTypeCourseEvalPersistence courseTypeCourseEvalPersistence;
	@BeanReference(type = CourseTypeInscriptionTypePersistence.class)
	protected CourseTypeInscriptionTypePersistence courseTypeInscriptionTypePersistence;
	@BeanReference(type = CourseTypeLearningActivityPersistence.class)
	protected CourseTypeLearningActivityPersistence courseTypeLearningActivityPersistence;
	@BeanReference(type = CourseTypeTemplatePersistence.class)
	protected CourseTypeTemplatePersistence courseTypeTemplatePersistence;
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
	private static final String _SQL_SELECT_COURSETYPELEARNINGACTIVITY = "SELECT courseTypeLearningActivity FROM CourseTypeLearningActivity courseTypeLearningActivity";
	private static final String _SQL_SELECT_COURSETYPELEARNINGACTIVITY_WHERE = "SELECT courseTypeLearningActivity FROM CourseTypeLearningActivity courseTypeLearningActivity WHERE ";
	private static final String _SQL_COUNT_COURSETYPELEARNINGACTIVITY = "SELECT COUNT(courseTypeLearningActivity) FROM CourseTypeLearningActivity courseTypeLearningActivity";
	private static final String _SQL_COUNT_COURSETYPELEARNINGACTIVITY_WHERE = "SELECT COUNT(courseTypeLearningActivity) FROM CourseTypeLearningActivity courseTypeLearningActivity WHERE ";
	private static final String _FINDER_COLUMN_COURSETYPELEARNINGACTIVITYID_COURSETYPELEARNINGACTIVITYID_2 =
		"courseTypeLearningActivity.courseTypeLearningActivityId = ?";
	private static final String _FINDER_COLUMN_COURSETYPEID_COURSETYPEID_2 = "courseTypeLearningActivity.courseTypeId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "courseTypeLearningActivity.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CourseTypeLearningActivity exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CourseTypeLearningActivity exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(CourseTypeLearningActivityPersistenceImpl.class);
	private static CourseTypeLearningActivity _nullCourseTypeLearningActivity = new CourseTypeLearningActivityImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<CourseTypeLearningActivity> toCacheModel() {
				return _nullCourseTypeLearningActivityCacheModel;
			}
		};

	private static CacheModel<CourseTypeLearningActivity> _nullCourseTypeLearningActivityCacheModel =
		new CacheModel<CourseTypeLearningActivity>() {
			public CourseTypeLearningActivity toEntityModel() {
				return _nullCourseTypeLearningActivity;
			}
		};
}