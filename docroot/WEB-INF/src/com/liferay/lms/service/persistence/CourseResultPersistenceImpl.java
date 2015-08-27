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

import com.liferay.lms.NoSuchCourseResultException;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.impl.CourseResultImpl;
import com.liferay.lms.model.impl.CourseResultModelImpl;

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
 * The persistence implementation for the course result service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CourseResultPersistence
 * @see CourseResultUtil
 * @generated
 */
public class CourseResultPersistenceImpl extends BasePersistenceImpl<CourseResult>
	implements CourseResultPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CourseResultUtil} to access the course result persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CourseResultImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_UC = new FinderPath(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
			CourseResultModelImpl.FINDER_CACHE_ENABLED, CourseResultImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByuc",
			new String[] { Long.class.getName(), Long.class.getName() },
			CourseResultModelImpl.USERID_COLUMN_BITMASK |
			CourseResultModelImpl.COURSEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UC = new FinderPath(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
			CourseResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByuc",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C = new FinderPath(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
			CourseResultModelImpl.FINDER_CACHE_ENABLED, CourseResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByc",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C = new FinderPath(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
			CourseResultModelImpl.FINDER_CACHE_ENABLED, CourseResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByc",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			CourseResultModelImpl.COURSEID_COLUMN_BITMASK |
			CourseResultModelImpl.PASSED_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C = new FinderPath(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
			CourseResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByc",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
			CourseResultModelImpl.FINDER_CACHE_ENABLED, CourseResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
			CourseResultModelImpl.FINDER_CACHE_ENABLED, CourseResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
			CourseResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the course result in the entity cache if it is enabled.
	 *
	 * @param courseResult the course result
	 */
	public void cacheResult(CourseResult courseResult) {
		EntityCacheUtil.putResult(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
			CourseResultImpl.class, courseResult.getPrimaryKey(), courseResult);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UC,
			new Object[] {
				Long.valueOf(courseResult.getUserId()),
				Long.valueOf(courseResult.getCourseId())
			}, courseResult);

		courseResult.resetOriginalValues();
	}

	/**
	 * Caches the course results in the entity cache if it is enabled.
	 *
	 * @param courseResults the course results
	 */
	public void cacheResult(List<CourseResult> courseResults) {
		for (CourseResult courseResult : courseResults) {
			if (EntityCacheUtil.getResult(
						CourseResultModelImpl.ENTITY_CACHE_ENABLED,
						CourseResultImpl.class, courseResult.getPrimaryKey()) == null) {
				cacheResult(courseResult);
			}
			else {
				courseResult.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all course results.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CourseResultImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CourseResultImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the course result.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CourseResult courseResult) {
		EntityCacheUtil.removeResult(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
			CourseResultImpl.class, courseResult.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(courseResult);
	}

	@Override
	public void clearCache(List<CourseResult> courseResults) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CourseResult courseResult : courseResults) {
			EntityCacheUtil.removeResult(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
				CourseResultImpl.class, courseResult.getPrimaryKey());

			clearUniqueFindersCache(courseResult);
		}
	}

	protected void clearUniqueFindersCache(CourseResult courseResult) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UC,
			new Object[] {
				Long.valueOf(courseResult.getUserId()),
				Long.valueOf(courseResult.getCourseId())
			});
	}

	/**
	 * Creates a new course result with the primary key. Does not add the course result to the database.
	 *
	 * @param crId the primary key for the new course result
	 * @return the new course result
	 */
	public CourseResult create(long crId) {
		CourseResult courseResult = new CourseResultImpl();

		courseResult.setNew(true);
		courseResult.setPrimaryKey(crId);

		return courseResult;
	}

	/**
	 * Removes the course result with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param crId the primary key of the course result
	 * @return the course result that was removed
	 * @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseResult remove(long crId)
		throws NoSuchCourseResultException, SystemException {
		return remove(Long.valueOf(crId));
	}

	/**
	 * Removes the course result with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the course result
	 * @return the course result that was removed
	 * @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseResult remove(Serializable primaryKey)
		throws NoSuchCourseResultException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CourseResult courseResult = (CourseResult)session.get(CourseResultImpl.class,
					primaryKey);

			if (courseResult == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCourseResultException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(courseResult);
		}
		catch (NoSuchCourseResultException nsee) {
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
	protected CourseResult removeImpl(CourseResult courseResult)
		throws SystemException {
		courseResult = toUnwrappedModel(courseResult);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, courseResult);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(courseResult);

		return courseResult;
	}

	@Override
	public CourseResult updateImpl(
		com.liferay.lms.model.CourseResult courseResult, boolean merge)
		throws SystemException {
		courseResult = toUnwrappedModel(courseResult);

		boolean isNew = courseResult.isNew();

		CourseResultModelImpl courseResultModelImpl = (CourseResultModelImpl)courseResult;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, courseResult, merge);

			courseResult.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !CourseResultModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((courseResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseResultModelImpl.getOriginalCourseId()),
						Boolean.valueOf(courseResultModelImpl.getOriginalPassed())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C,
					args);

				args = new Object[] {
						Long.valueOf(courseResultModelImpl.getCourseId()),
						Boolean.valueOf(courseResultModelImpl.getPassed())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C,
					args);
			}
		}

		EntityCacheUtil.putResult(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
			CourseResultImpl.class, courseResult.getPrimaryKey(), courseResult);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UC,
				new Object[] {
					Long.valueOf(courseResult.getUserId()),
					Long.valueOf(courseResult.getCourseId())
				}, courseResult);
		}
		else {
			if ((courseResultModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UC.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseResultModelImpl.getOriginalUserId()),
						Long.valueOf(courseResultModelImpl.getOriginalCourseId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UC, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UC, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UC,
					new Object[] {
						Long.valueOf(courseResult.getUserId()),
						Long.valueOf(courseResult.getCourseId())
					}, courseResult);
			}
		}

		return courseResult;
	}

	protected CourseResult toUnwrappedModel(CourseResult courseResult) {
		if (courseResult instanceof CourseResultImpl) {
			return courseResult;
		}

		CourseResultImpl courseResultImpl = new CourseResultImpl();

		courseResultImpl.setNew(courseResult.isNew());
		courseResultImpl.setPrimaryKey(courseResult.getPrimaryKey());

		courseResultImpl.setCrId(courseResult.getCrId());
		courseResultImpl.setCourseId(courseResult.getCourseId());
		courseResultImpl.setResult(courseResult.getResult());
		courseResultImpl.setComments(courseResult.getComments());
		courseResultImpl.setUserId(courseResult.getUserId());
		courseResultImpl.setPassed(courseResult.isPassed());
		courseResultImpl.setStartDate(courseResult.getStartDate());
		courseResultImpl.setPassedDate(courseResult.getPassedDate());
		courseResultImpl.setAllowStartDate(courseResult.getAllowStartDate());
		courseResultImpl.setAllowFinishDate(courseResult.getAllowFinishDate());

		return courseResultImpl;
	}

	/**
	 * Returns the course result with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the course result
	 * @return the course result
	 * @throws com.liferay.portal.NoSuchModelException if a course result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseResult findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course result with the primary key or throws a {@link com.liferay.lms.NoSuchCourseResultException} if it could not be found.
	 *
	 * @param crId the primary key of the course result
	 * @return the course result
	 * @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseResult findByPrimaryKey(long crId)
		throws NoSuchCourseResultException, SystemException {
		CourseResult courseResult = fetchByPrimaryKey(crId);

		if (courseResult == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + crId);
			}

			throw new NoSuchCourseResultException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				crId);
		}

		return courseResult;
	}

	/**
	 * Returns the course result with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the course result
	 * @return the course result, or <code>null</code> if a course result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseResult fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course result with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param crId the primary key of the course result
	 * @return the course result, or <code>null</code> if a course result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseResult fetchByPrimaryKey(long crId) throws SystemException {
		CourseResult courseResult = (CourseResult)EntityCacheUtil.getResult(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
				CourseResultImpl.class, crId);

		if (courseResult == _nullCourseResult) {
			return null;
		}

		if (courseResult == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				courseResult = (CourseResult)session.get(CourseResultImpl.class,
						Long.valueOf(crId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (courseResult != null) {
					cacheResult(courseResult);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(CourseResultModelImpl.ENTITY_CACHE_ENABLED,
						CourseResultImpl.class, crId, _nullCourseResult);
				}

				closeSession(session);
			}
		}

		return courseResult;
	}

	/**
	 * Returns the course result where userId = &#63; and courseId = &#63; or throws a {@link com.liferay.lms.NoSuchCourseResultException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param courseId the course ID
	 * @return the matching course result
	 * @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseResult findByuc(long userId, long courseId)
		throws NoSuchCourseResultException, SystemException {
		CourseResult courseResult = fetchByuc(userId, courseId);

		if (courseResult == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", courseId=");
			msg.append(courseId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCourseResultException(msg.toString());
		}

		return courseResult;
	}

	/**
	 * Returns the course result where userId = &#63; and courseId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param courseId the course ID
	 * @return the matching course result, or <code>null</code> if a matching course result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseResult fetchByuc(long userId, long courseId)
		throws SystemException {
		return fetchByuc(userId, courseId, true);
	}

	/**
	 * Returns the course result where userId = &#63; and courseId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param courseId the course ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching course result, or <code>null</code> if a matching course result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseResult fetchByuc(long userId, long courseId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { userId, courseId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UC,
					finderArgs, this);
		}

		if (result instanceof CourseResult) {
			CourseResult courseResult = (CourseResult)result;

			if ((userId != courseResult.getUserId()) ||
					(courseId != courseResult.getCourseId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_COURSERESULT_WHERE);

			query.append(_FINDER_COLUMN_UC_USERID_2);

			query.append(_FINDER_COLUMN_UC_COURSEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(courseId);

				List<CourseResult> list = q.list();

				result = list;

				CourseResult courseResult = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UC,
						finderArgs, list);
				}
				else {
					courseResult = list.get(0);

					cacheResult(courseResult);

					if ((courseResult.getUserId() != userId) ||
							(courseResult.getCourseId() != courseId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UC,
							finderArgs, courseResult);
					}
				}

				return courseResult;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UC,
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
				return (CourseResult)result;
			}
		}
	}

	/**
	 * Returns all the course results where courseId = &#63; and passed = &#63;.
	 *
	 * @param courseId the course ID
	 * @param passed the passed
	 * @return the matching course results
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseResult> findByc(long courseId, boolean passed)
		throws SystemException {
		return findByc(courseId, passed, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the course results where courseId = &#63; and passed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseId the course ID
	 * @param passed the passed
	 * @param start the lower bound of the range of course results
	 * @param end the upper bound of the range of course results (not inclusive)
	 * @return the range of matching course results
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseResult> findByc(long courseId, boolean passed, int start,
		int end) throws SystemException {
		return findByc(courseId, passed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the course results where courseId = &#63; and passed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseId the course ID
	 * @param passed the passed
	 * @param start the lower bound of the range of course results
	 * @param end the upper bound of the range of course results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching course results
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseResult> findByc(long courseId, boolean passed, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C;
			finderArgs = new Object[] { courseId, passed };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C;
			finderArgs = new Object[] {
					courseId, passed,
					
					start, end, orderByComparator
				};
		}

		List<CourseResult> list = (List<CourseResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CourseResult courseResult : list) {
				if ((courseId != courseResult.getCourseId()) ||
						(passed != courseResult.getPassed())) {
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

			query.append(_SQL_SELECT_COURSERESULT_WHERE);

			query.append(_FINDER_COLUMN_C_COURSEID_2);

			query.append(_FINDER_COLUMN_C_PASSED_2);

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

				qPos.add(courseId);

				qPos.add(passed);

				list = (List<CourseResult>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first course result in the ordered set where courseId = &#63; and passed = &#63;.
	 *
	 * @param courseId the course ID
	 * @param passed the passed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course result
	 * @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseResult findByc_First(long courseId, boolean passed,
		OrderByComparator orderByComparator)
		throws NoSuchCourseResultException, SystemException {
		CourseResult courseResult = fetchByc_First(courseId, passed,
				orderByComparator);

		if (courseResult != null) {
			return courseResult;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseId=");
		msg.append(courseId);

		msg.append(", passed=");
		msg.append(passed);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseResultException(msg.toString());
	}

	/**
	 * Returns the first course result in the ordered set where courseId = &#63; and passed = &#63;.
	 *
	 * @param courseId the course ID
	 * @param passed the passed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course result, or <code>null</code> if a matching course result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseResult fetchByc_First(long courseId, boolean passed,
		OrderByComparator orderByComparator) throws SystemException {
		List<CourseResult> list = findByc(courseId, passed, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last course result in the ordered set where courseId = &#63; and passed = &#63;.
	 *
	 * @param courseId the course ID
	 * @param passed the passed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course result
	 * @throws com.liferay.lms.NoSuchCourseResultException if a matching course result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseResult findByc_Last(long courseId, boolean passed,
		OrderByComparator orderByComparator)
		throws NoSuchCourseResultException, SystemException {
		CourseResult courseResult = fetchByc_Last(courseId, passed,
				orderByComparator);

		if (courseResult != null) {
			return courseResult;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseId=");
		msg.append(courseId);

		msg.append(", passed=");
		msg.append(passed);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseResultException(msg.toString());
	}

	/**
	 * Returns the last course result in the ordered set where courseId = &#63; and passed = &#63;.
	 *
	 * @param courseId the course ID
	 * @param passed the passed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course result, or <code>null</code> if a matching course result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseResult fetchByc_Last(long courseId, boolean passed,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByc(courseId, passed);

		List<CourseResult> list = findByc(courseId, passed, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the course results before and after the current course result in the ordered set where courseId = &#63; and passed = &#63;.
	 *
	 * @param crId the primary key of the current course result
	 * @param courseId the course ID
	 * @param passed the passed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next course result
	 * @throws com.liferay.lms.NoSuchCourseResultException if a course result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseResult[] findByc_PrevAndNext(long crId, long courseId,
		boolean passed, OrderByComparator orderByComparator)
		throws NoSuchCourseResultException, SystemException {
		CourseResult courseResult = findByPrimaryKey(crId);

		Session session = null;

		try {
			session = openSession();

			CourseResult[] array = new CourseResultImpl[3];

			array[0] = getByc_PrevAndNext(session, courseResult, courseId,
					passed, orderByComparator, true);

			array[1] = courseResult;

			array[2] = getByc_PrevAndNext(session, courseResult, courseId,
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

	protected CourseResult getByc_PrevAndNext(Session session,
		CourseResult courseResult, long courseId, boolean passed,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COURSERESULT_WHERE);

		query.append(_FINDER_COLUMN_C_COURSEID_2);

		query.append(_FINDER_COLUMN_C_PASSED_2);

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

		qPos.add(courseId);

		qPos.add(passed);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(courseResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CourseResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the course results.
	 *
	 * @return the course results
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseResult> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course results.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course results
	 * @param end the upper bound of the range of course results (not inclusive)
	 * @return the range of course results
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseResult> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the course results.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course results
	 * @param end the upper bound of the range of course results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of course results
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseResult> findAll(int start, int end,
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

		List<CourseResult> list = (List<CourseResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_COURSERESULT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COURSERESULT;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<CourseResult>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<CourseResult>)QueryUtil.list(q, getDialect(),
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
	 * Removes the course result where userId = &#63; and courseId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param courseId the course ID
	 * @return the course result that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public CourseResult removeByuc(long userId, long courseId)
		throws NoSuchCourseResultException, SystemException {
		CourseResult courseResult = findByuc(userId, courseId);

		return remove(courseResult);
	}

	/**
	 * Removes all the course results where courseId = &#63; and passed = &#63; from the database.
	 *
	 * @param courseId the course ID
	 * @param passed the passed
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByc(long courseId, boolean passed)
		throws SystemException {
		for (CourseResult courseResult : findByc(courseId, passed)) {
			remove(courseResult);
		}
	}

	/**
	 * Removes all the course results from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (CourseResult courseResult : findAll()) {
			remove(courseResult);
		}
	}

	/**
	 * Returns the number of course results where userId = &#63; and courseId = &#63;.
	 *
	 * @param userId the user ID
	 * @param courseId the course ID
	 * @return the number of matching course results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByuc(long userId, long courseId) throws SystemException {
		Object[] finderArgs = new Object[] { userId, courseId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UC,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COURSERESULT_WHERE);

			query.append(_FINDER_COLUMN_UC_USERID_2);

			query.append(_FINDER_COLUMN_UC_COURSEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(courseId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UC, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course results where courseId = &#63; and passed = &#63;.
	 *
	 * @param courseId the course ID
	 * @param passed the passed
	 * @return the number of matching course results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByc(long courseId, boolean passed)
		throws SystemException {
		Object[] finderArgs = new Object[] { courseId, passed };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COURSERESULT_WHERE);

			query.append(_FINDER_COLUMN_C_COURSEID_2);

			query.append(_FINDER_COLUMN_C_PASSED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course results.
	 *
	 * @return the number of course results
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COURSERESULT);

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
	 * Initializes the course result persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.CourseResult")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CourseResult>> listenersList = new ArrayList<ModelListener<CourseResult>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CourseResult>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(CourseResultImpl.class.getName());
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
	private static final String _SQL_SELECT_COURSERESULT = "SELECT courseResult FROM CourseResult courseResult";
	private static final String _SQL_SELECT_COURSERESULT_WHERE = "SELECT courseResult FROM CourseResult courseResult WHERE ";
	private static final String _SQL_COUNT_COURSERESULT = "SELECT COUNT(courseResult) FROM CourseResult courseResult";
	private static final String _SQL_COUNT_COURSERESULT_WHERE = "SELECT COUNT(courseResult) FROM CourseResult courseResult WHERE ";
	private static final String _FINDER_COLUMN_UC_USERID_2 = "courseResult.userId = ? AND ";
	private static final String _FINDER_COLUMN_UC_COURSEID_2 = "courseResult.courseId = ?";
	private static final String _FINDER_COLUMN_C_COURSEID_2 = "courseResult.courseId = ? AND ";
	private static final String _FINDER_COLUMN_C_PASSED_2 = "courseResult.passed = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "courseResult.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CourseResult exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CourseResult exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(CourseResultPersistenceImpl.class);
	private static CourseResult _nullCourseResult = new CourseResultImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<CourseResult> toCacheModel() {
				return _nullCourseResultCacheModel;
			}
		};

	private static CacheModel<CourseResult> _nullCourseResultCacheModel = new CacheModel<CourseResult>() {
			public CourseResult toEntityModel() {
				return _nullCourseResult;
			}
		};
}