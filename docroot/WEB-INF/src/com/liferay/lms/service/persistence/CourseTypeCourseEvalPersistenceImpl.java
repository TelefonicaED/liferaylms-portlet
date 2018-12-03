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

import com.liferay.lms.NoSuchCourseTypeCourseEvalException;
import com.liferay.lms.model.CourseTypeCourseEval;
import com.liferay.lms.model.impl.CourseTypeCourseEvalImpl;
import com.liferay.lms.model.impl.CourseTypeCourseEvalModelImpl;

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
 * The persistence implementation for the course type course eval service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CourseTypeCourseEvalPersistence
 * @see CourseTypeCourseEvalUtil
 * @generated
 */
public class CourseTypeCourseEvalPersistenceImpl extends BasePersistenceImpl<CourseTypeCourseEval>
	implements CourseTypeCourseEvalPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CourseTypeCourseEvalUtil} to access the course type course eval persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CourseTypeCourseEvalImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_COURSETYPECOURSEEVALID = new FinderPath(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCourseEvalModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeCourseEvalImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCourseTypeCourseEvalId",
			new String[] { Long.class.getName() },
			CourseTypeCourseEvalModelImpl.COURSETYPEEVALUTATIONTYPEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSETYPECOURSEEVALID = new FinderPath(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCourseEvalModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCourseTypeCourseEvalId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COURSETYPEID =
		new FinderPath(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCourseEvalModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeCourseEvalImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCourseTypeId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID =
		new FinderPath(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCourseEvalModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeCourseEvalImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCourseTypeId",
			new String[] { Long.class.getName() },
			CourseTypeCourseEvalModelImpl.COURSETYPEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSETYPEID = new FinderPath(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCourseEvalModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCourseTypeId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCourseEvalModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeCourseEvalImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCourseEvalModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeCourseEvalImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCourseEvalModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the course type course eval in the entity cache if it is enabled.
	 *
	 * @param courseTypeCourseEval the course type course eval
	 */
	public void cacheResult(CourseTypeCourseEval courseTypeCourseEval) {
		EntityCacheUtil.putResult(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCourseEvalImpl.class,
			courseTypeCourseEval.getPrimaryKey(), courseTypeCourseEval);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPECOURSEEVALID,
			new Object[] {
				Long.valueOf(
					courseTypeCourseEval.getCourseTypeEvalutationTypeId())
			}, courseTypeCourseEval);

		courseTypeCourseEval.resetOriginalValues();
	}

	/**
	 * Caches the course type course evals in the entity cache if it is enabled.
	 *
	 * @param courseTypeCourseEvals the course type course evals
	 */
	public void cacheResult(List<CourseTypeCourseEval> courseTypeCourseEvals) {
		for (CourseTypeCourseEval courseTypeCourseEval : courseTypeCourseEvals) {
			if (EntityCacheUtil.getResult(
						CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeCourseEvalImpl.class,
						courseTypeCourseEval.getPrimaryKey()) == null) {
				cacheResult(courseTypeCourseEval);
			}
			else {
				courseTypeCourseEval.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all course type course evals.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CourseTypeCourseEvalImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CourseTypeCourseEvalImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the course type course eval.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CourseTypeCourseEval courseTypeCourseEval) {
		EntityCacheUtil.removeResult(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCourseEvalImpl.class, courseTypeCourseEval.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(courseTypeCourseEval);
	}

	@Override
	public void clearCache(List<CourseTypeCourseEval> courseTypeCourseEvals) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CourseTypeCourseEval courseTypeCourseEval : courseTypeCourseEvals) {
			EntityCacheUtil.removeResult(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeCourseEvalImpl.class,
				courseTypeCourseEval.getPrimaryKey());

			clearUniqueFindersCache(courseTypeCourseEval);
		}
	}

	protected void clearUniqueFindersCache(
		CourseTypeCourseEval courseTypeCourseEval) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPECOURSEEVALID,
			new Object[] {
				Long.valueOf(
					courseTypeCourseEval.getCourseTypeEvalutationTypeId())
			});
	}

	/**
	 * Creates a new course type course eval with the primary key. Does not add the course type course eval to the database.
	 *
	 * @param courseTypeEvalutationTypeId the primary key for the new course type course eval
	 * @return the new course type course eval
	 */
	public CourseTypeCourseEval create(long courseTypeEvalutationTypeId) {
		CourseTypeCourseEval courseTypeCourseEval = new CourseTypeCourseEvalImpl();

		courseTypeCourseEval.setNew(true);
		courseTypeCourseEval.setPrimaryKey(courseTypeEvalutationTypeId);

		return courseTypeCourseEval;
	}

	/**
	 * Removes the course type course eval with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param courseTypeEvalutationTypeId the primary key of the course type course eval
	 * @return the course type course eval that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeCourseEvalException if a course type course eval with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCourseEval remove(long courseTypeEvalutationTypeId)
		throws NoSuchCourseTypeCourseEvalException, SystemException {
		return remove(Long.valueOf(courseTypeEvalutationTypeId));
	}

	/**
	 * Removes the course type course eval with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the course type course eval
	 * @return the course type course eval that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeCourseEvalException if a course type course eval with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeCourseEval remove(Serializable primaryKey)
		throws NoSuchCourseTypeCourseEvalException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CourseTypeCourseEval courseTypeCourseEval = (CourseTypeCourseEval)session.get(CourseTypeCourseEvalImpl.class,
					primaryKey);

			if (courseTypeCourseEval == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCourseTypeCourseEvalException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(courseTypeCourseEval);
		}
		catch (NoSuchCourseTypeCourseEvalException nsee) {
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
	protected CourseTypeCourseEval removeImpl(
		CourseTypeCourseEval courseTypeCourseEval) throws SystemException {
		courseTypeCourseEval = toUnwrappedModel(courseTypeCourseEval);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, courseTypeCourseEval);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(courseTypeCourseEval);

		return courseTypeCourseEval;
	}

	@Override
	public CourseTypeCourseEval updateImpl(
		com.liferay.lms.model.CourseTypeCourseEval courseTypeCourseEval,
		boolean merge) throws SystemException {
		courseTypeCourseEval = toUnwrappedModel(courseTypeCourseEval);

		boolean isNew = courseTypeCourseEval.isNew();

		CourseTypeCourseEvalModelImpl courseTypeCourseEvalModelImpl = (CourseTypeCourseEvalModelImpl)courseTypeCourseEval;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, courseTypeCourseEval, merge);

			courseTypeCourseEval.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !CourseTypeCourseEvalModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((courseTypeCourseEvalModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeCourseEvalModelImpl.getOriginalCourseTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID,
					args);

				args = new Object[] {
						Long.valueOf(courseTypeCourseEvalModelImpl.getCourseTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID,
					args);
			}
		}

		EntityCacheUtil.putResult(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCourseEvalImpl.class,
			courseTypeCourseEval.getPrimaryKey(), courseTypeCourseEval);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPECOURSEEVALID,
				new Object[] {
					Long.valueOf(
						courseTypeCourseEval.getCourseTypeEvalutationTypeId())
				}, courseTypeCourseEval);
		}
		else {
			if ((courseTypeCourseEvalModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_COURSETYPECOURSEEVALID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeCourseEvalModelImpl.getOriginalCourseTypeEvalutationTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPECOURSEEVALID,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPECOURSEEVALID,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPECOURSEEVALID,
					new Object[] {
						Long.valueOf(
							courseTypeCourseEval.getCourseTypeEvalutationTypeId())
					}, courseTypeCourseEval);
			}
		}

		return courseTypeCourseEval;
	}

	protected CourseTypeCourseEval toUnwrappedModel(
		CourseTypeCourseEval courseTypeCourseEval) {
		if (courseTypeCourseEval instanceof CourseTypeCourseEvalImpl) {
			return courseTypeCourseEval;
		}

		CourseTypeCourseEvalImpl courseTypeCourseEvalImpl = new CourseTypeCourseEvalImpl();

		courseTypeCourseEvalImpl.setNew(courseTypeCourseEval.isNew());
		courseTypeCourseEvalImpl.setPrimaryKey(courseTypeCourseEval.getPrimaryKey());

		courseTypeCourseEvalImpl.setCourseTypeEvalutationTypeId(courseTypeCourseEval.getCourseTypeEvalutationTypeId());
		courseTypeCourseEvalImpl.setCourseTypeId(courseTypeCourseEval.getCourseTypeId());
		courseTypeCourseEvalImpl.setCourseEvalId(courseTypeCourseEval.getCourseEvalId());

		return courseTypeCourseEvalImpl;
	}

	/**
	 * Returns the course type course eval with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type course eval
	 * @return the course type course eval
	 * @throws com.liferay.portal.NoSuchModelException if a course type course eval with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeCourseEval findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type course eval with the primary key or throws a {@link com.liferay.lms.NoSuchCourseTypeCourseEvalException} if it could not be found.
	 *
	 * @param courseTypeEvalutationTypeId the primary key of the course type course eval
	 * @return the course type course eval
	 * @throws com.liferay.lms.NoSuchCourseTypeCourseEvalException if a course type course eval with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCourseEval findByPrimaryKey(
		long courseTypeEvalutationTypeId)
		throws NoSuchCourseTypeCourseEvalException, SystemException {
		CourseTypeCourseEval courseTypeCourseEval = fetchByPrimaryKey(courseTypeEvalutationTypeId);

		if (courseTypeCourseEval == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					courseTypeEvalutationTypeId);
			}

			throw new NoSuchCourseTypeCourseEvalException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				courseTypeEvalutationTypeId);
		}

		return courseTypeCourseEval;
	}

	/**
	 * Returns the course type course eval with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type course eval
	 * @return the course type course eval, or <code>null</code> if a course type course eval with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeCourseEval fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type course eval with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param courseTypeEvalutationTypeId the primary key of the course type course eval
	 * @return the course type course eval, or <code>null</code> if a course type course eval with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCourseEval fetchByPrimaryKey(
		long courseTypeEvalutationTypeId) throws SystemException {
		CourseTypeCourseEval courseTypeCourseEval = (CourseTypeCourseEval)EntityCacheUtil.getResult(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeCourseEvalImpl.class, courseTypeEvalutationTypeId);

		if (courseTypeCourseEval == _nullCourseTypeCourseEval) {
			return null;
		}

		if (courseTypeCourseEval == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				courseTypeCourseEval = (CourseTypeCourseEval)session.get(CourseTypeCourseEvalImpl.class,
						Long.valueOf(courseTypeEvalutationTypeId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (courseTypeCourseEval != null) {
					cacheResult(courseTypeCourseEval);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(CourseTypeCourseEvalModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeCourseEvalImpl.class,
						courseTypeEvalutationTypeId, _nullCourseTypeCourseEval);
				}

				closeSession(session);
			}
		}

		return courseTypeCourseEval;
	}

	/**
	 * Returns the course type course eval where courseTypeEvalutationTypeId = &#63; or throws a {@link com.liferay.lms.NoSuchCourseTypeCourseEvalException} if it could not be found.
	 *
	 * @param courseTypeEvalutationTypeId the course type evalutation type ID
	 * @return the matching course type course eval
	 * @throws com.liferay.lms.NoSuchCourseTypeCourseEvalException if a matching course type course eval could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCourseEval findByCourseTypeCourseEvalId(
		long courseTypeEvalutationTypeId)
		throws NoSuchCourseTypeCourseEvalException, SystemException {
		CourseTypeCourseEval courseTypeCourseEval = fetchByCourseTypeCourseEvalId(courseTypeEvalutationTypeId);

		if (courseTypeCourseEval == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("courseTypeEvalutationTypeId=");
			msg.append(courseTypeEvalutationTypeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCourseTypeCourseEvalException(msg.toString());
		}

		return courseTypeCourseEval;
	}

	/**
	 * Returns the course type course eval where courseTypeEvalutationTypeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param courseTypeEvalutationTypeId the course type evalutation type ID
	 * @return the matching course type course eval, or <code>null</code> if a matching course type course eval could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCourseEval fetchByCourseTypeCourseEvalId(
		long courseTypeEvalutationTypeId) throws SystemException {
		return fetchByCourseTypeCourseEvalId(courseTypeEvalutationTypeId, true);
	}

	/**
	 * Returns the course type course eval where courseTypeEvalutationTypeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param courseTypeEvalutationTypeId the course type evalutation type ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching course type course eval, or <code>null</code> if a matching course type course eval could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCourseEval fetchByCourseTypeCourseEvalId(
		long courseTypeEvalutationTypeId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeEvalutationTypeId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_COURSETYPECOURSEEVALID,
					finderArgs, this);
		}

		if (result instanceof CourseTypeCourseEval) {
			CourseTypeCourseEval courseTypeCourseEval = (CourseTypeCourseEval)result;

			if ((courseTypeEvalutationTypeId != courseTypeCourseEval.getCourseTypeEvalutationTypeId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_COURSETYPECOURSEEVAL_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPECOURSEEVALID_COURSETYPEEVALUTATIONTYPEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeEvalutationTypeId);

				List<CourseTypeCourseEval> list = q.list();

				result = list;

				CourseTypeCourseEval courseTypeCourseEval = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPECOURSEEVALID,
						finderArgs, list);
				}
				else {
					courseTypeCourseEval = list.get(0);

					cacheResult(courseTypeCourseEval);

					if ((courseTypeCourseEval.getCourseTypeEvalutationTypeId() != courseTypeEvalutationTypeId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPECOURSEEVALID,
							finderArgs, courseTypeCourseEval);
					}
				}

				return courseTypeCourseEval;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPECOURSEEVALID,
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
				return (CourseTypeCourseEval)result;
			}
		}
	}

	/**
	 * Returns all the course type course evals where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @return the matching course type course evals
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeCourseEval> findByCourseTypeId(long courseTypeId)
		throws SystemException {
		return findByCourseTypeId(courseTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course type course evals where courseTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param start the lower bound of the range of course type course evals
	 * @param end the upper bound of the range of course type course evals (not inclusive)
	 * @return the range of matching course type course evals
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeCourseEval> findByCourseTypeId(long courseTypeId,
		int start, int end) throws SystemException {
		return findByCourseTypeId(courseTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the course type course evals where courseTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param start the lower bound of the range of course type course evals
	 * @param end the upper bound of the range of course type course evals (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching course type course evals
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeCourseEval> findByCourseTypeId(long courseTypeId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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

		List<CourseTypeCourseEval> list = (List<CourseTypeCourseEval>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CourseTypeCourseEval courseTypeCourseEval : list) {
				if ((courseTypeId != courseTypeCourseEval.getCourseTypeId())) {
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

			query.append(_SQL_SELECT_COURSETYPECOURSEEVAL_WHERE);

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

				list = (List<CourseTypeCourseEval>)QueryUtil.list(q,
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
	 * Returns the first course type course eval in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type course eval
	 * @throws com.liferay.lms.NoSuchCourseTypeCourseEvalException if a matching course type course eval could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCourseEval findByCourseTypeId_First(long courseTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeCourseEvalException, SystemException {
		CourseTypeCourseEval courseTypeCourseEval = fetchByCourseTypeId_First(courseTypeId,
				orderByComparator);

		if (courseTypeCourseEval != null) {
			return courseTypeCourseEval;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeCourseEvalException(msg.toString());
	}

	/**
	 * Returns the first course type course eval in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type course eval, or <code>null</code> if a matching course type course eval could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCourseEval fetchByCourseTypeId_First(long courseTypeId,
		OrderByComparator orderByComparator) throws SystemException {
		List<CourseTypeCourseEval> list = findByCourseTypeId(courseTypeId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last course type course eval in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type course eval
	 * @throws com.liferay.lms.NoSuchCourseTypeCourseEvalException if a matching course type course eval could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCourseEval findByCourseTypeId_Last(long courseTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeCourseEvalException, SystemException {
		CourseTypeCourseEval courseTypeCourseEval = fetchByCourseTypeId_Last(courseTypeId,
				orderByComparator);

		if (courseTypeCourseEval != null) {
			return courseTypeCourseEval;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeCourseEvalException(msg.toString());
	}

	/**
	 * Returns the last course type course eval in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type course eval, or <code>null</code> if a matching course type course eval could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCourseEval fetchByCourseTypeId_Last(long courseTypeId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCourseTypeId(courseTypeId);

		List<CourseTypeCourseEval> list = findByCourseTypeId(courseTypeId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the course type course evals before and after the current course type course eval in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeEvalutationTypeId the primary key of the current course type course eval
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next course type course eval
	 * @throws com.liferay.lms.NoSuchCourseTypeCourseEvalException if a course type course eval with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCourseEval[] findByCourseTypeId_PrevAndNext(
		long courseTypeEvalutationTypeId, long courseTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeCourseEvalException, SystemException {
		CourseTypeCourseEval courseTypeCourseEval = findByPrimaryKey(courseTypeEvalutationTypeId);

		Session session = null;

		try {
			session = openSession();

			CourseTypeCourseEval[] array = new CourseTypeCourseEvalImpl[3];

			array[0] = getByCourseTypeId_PrevAndNext(session,
					courseTypeCourseEval, courseTypeId, orderByComparator, true);

			array[1] = courseTypeCourseEval;

			array[2] = getByCourseTypeId_PrevAndNext(session,
					courseTypeCourseEval, courseTypeId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CourseTypeCourseEval getByCourseTypeId_PrevAndNext(
		Session session, CourseTypeCourseEval courseTypeCourseEval,
		long courseTypeId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COURSETYPECOURSEEVAL_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(courseTypeCourseEval);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CourseTypeCourseEval> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the course type course evals.
	 *
	 * @return the course type course evals
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeCourseEval> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course type course evals.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course type course evals
	 * @param end the upper bound of the range of course type course evals (not inclusive)
	 * @return the range of course type course evals
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeCourseEval> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the course type course evals.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course type course evals
	 * @param end the upper bound of the range of course type course evals (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of course type course evals
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeCourseEval> findAll(int start, int end,
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

		List<CourseTypeCourseEval> list = (List<CourseTypeCourseEval>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_COURSETYPECOURSEEVAL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COURSETYPECOURSEEVAL;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<CourseTypeCourseEval>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<CourseTypeCourseEval>)QueryUtil.list(q,
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
	 * Removes the course type course eval where courseTypeEvalutationTypeId = &#63; from the database.
	 *
	 * @param courseTypeEvalutationTypeId the course type evalutation type ID
	 * @return the course type course eval that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCourseEval removeByCourseTypeCourseEvalId(
		long courseTypeEvalutationTypeId)
		throws NoSuchCourseTypeCourseEvalException, SystemException {
		CourseTypeCourseEval courseTypeCourseEval = findByCourseTypeCourseEvalId(courseTypeEvalutationTypeId);

		return remove(courseTypeCourseEval);
	}

	/**
	 * Removes all the course type course evals where courseTypeId = &#63; from the database.
	 *
	 * @param courseTypeId the course type ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCourseTypeId(long courseTypeId)
		throws SystemException {
		for (CourseTypeCourseEval courseTypeCourseEval : findByCourseTypeId(
				courseTypeId)) {
			remove(courseTypeCourseEval);
		}
	}

	/**
	 * Removes all the course type course evals from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (CourseTypeCourseEval courseTypeCourseEval : findAll()) {
			remove(courseTypeCourseEval);
		}
	}

	/**
	 * Returns the number of course type course evals where courseTypeEvalutationTypeId = &#63;.
	 *
	 * @param courseTypeEvalutationTypeId the course type evalutation type ID
	 * @return the number of matching course type course evals
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseTypeCourseEvalId(long courseTypeEvalutationTypeId)
		throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeEvalutationTypeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSETYPECOURSEEVALID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COURSETYPECOURSEEVAL_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPECOURSEEVALID_COURSETYPEEVALUTATIONTYPEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeEvalutationTypeId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COURSETYPECOURSEEVALID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course type course evals where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @return the number of matching course type course evals
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseTypeId(long courseTypeId) throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COURSETYPECOURSEEVAL_WHERE);

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
	 * Returns the number of course type course evals.
	 *
	 * @return the number of course type course evals
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COURSETYPECOURSEEVAL);

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
	 * Initializes the course type course eval persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.CourseTypeCourseEval")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CourseTypeCourseEval>> listenersList = new ArrayList<ModelListener<CourseTypeCourseEval>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CourseTypeCourseEval>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(CourseTypeCourseEvalImpl.class.getName());
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
	private static final String _SQL_SELECT_COURSETYPECOURSEEVAL = "SELECT courseTypeCourseEval FROM CourseTypeCourseEval courseTypeCourseEval";
	private static final String _SQL_SELECT_COURSETYPECOURSEEVAL_WHERE = "SELECT courseTypeCourseEval FROM CourseTypeCourseEval courseTypeCourseEval WHERE ";
	private static final String _SQL_COUNT_COURSETYPECOURSEEVAL = "SELECT COUNT(courseTypeCourseEval) FROM CourseTypeCourseEval courseTypeCourseEval";
	private static final String _SQL_COUNT_COURSETYPECOURSEEVAL_WHERE = "SELECT COUNT(courseTypeCourseEval) FROM CourseTypeCourseEval courseTypeCourseEval WHERE ";
	private static final String _FINDER_COLUMN_COURSETYPECOURSEEVALID_COURSETYPEEVALUTATIONTYPEID_2 =
		"courseTypeCourseEval.courseTypeEvalutationTypeId = ?";
	private static final String _FINDER_COLUMN_COURSETYPEID_COURSETYPEID_2 = "courseTypeCourseEval.courseTypeId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "courseTypeCourseEval.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CourseTypeCourseEval exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CourseTypeCourseEval exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(CourseTypeCourseEvalPersistenceImpl.class);
	private static CourseTypeCourseEval _nullCourseTypeCourseEval = new CourseTypeCourseEvalImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<CourseTypeCourseEval> toCacheModel() {
				return _nullCourseTypeCourseEvalCacheModel;
			}
		};

	private static CacheModel<CourseTypeCourseEval> _nullCourseTypeCourseEvalCacheModel =
		new CacheModel<CourseTypeCourseEval>() {
			public CourseTypeCourseEval toEntityModel() {
				return _nullCourseTypeCourseEval;
			}
		};
}