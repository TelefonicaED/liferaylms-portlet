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

import com.liferay.lms.NoSuchCourseTypeCalificationTypeException;
import com.liferay.lms.model.CourseTypeCalificationType;
import com.liferay.lms.model.impl.CourseTypeCalificationTypeImpl;
import com.liferay.lms.model.impl.CourseTypeCalificationTypeModelImpl;

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
 * The persistence implementation for the course type calification type service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CourseTypeCalificationTypePersistence
 * @see CourseTypeCalificationTypeUtil
 * @generated
 */
public class CourseTypeCalificationTypePersistenceImpl
	extends BasePersistenceImpl<CourseTypeCalificationType>
	implements CourseTypeCalificationTypePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CourseTypeCalificationTypeUtil} to access the course type calification type persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CourseTypeCalificationTypeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_COURSETYPECALIFICATIONTYPEID =
		new FinderPath(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCalificationTypeModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeCalificationTypeImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCourseTypeCalificationTypeId",
			new String[] { Long.class.getName() },
			CourseTypeCalificationTypeModelImpl.COURSETYPECALIFICATIONTYPEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSETYPECALIFICATIONTYPEID =
		new FinderPath(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCalificationTypeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCourseTypeCalificationTypeId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COURSETYPEID =
		new FinderPath(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCalificationTypeModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeCalificationTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCourseTypeId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID =
		new FinderPath(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCalificationTypeModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeCalificationTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCourseTypeId",
			new String[] { Long.class.getName() },
			CourseTypeCalificationTypeModelImpl.COURSETYPEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSETYPEID = new FinderPath(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCalificationTypeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCourseTypeId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCalificationTypeModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeCalificationTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCalificationTypeModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeCalificationTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCalificationTypeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

	/**
	 * Caches the course type calification type in the entity cache if it is enabled.
	 *
	 * @param courseTypeCalificationType the course type calification type
	 */
	public void cacheResult(
		CourseTypeCalificationType courseTypeCalificationType) {
		EntityCacheUtil.putResult(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCalificationTypeImpl.class,
			courseTypeCalificationType.getPrimaryKey(),
			courseTypeCalificationType);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPECALIFICATIONTYPEID,
			new Object[] {
				Long.valueOf(
					courseTypeCalificationType.getCourseTypeCalificationTypeId())
			}, courseTypeCalificationType);

		courseTypeCalificationType.resetOriginalValues();
	}

	/**
	 * Caches the course type calification types in the entity cache if it is enabled.
	 *
	 * @param courseTypeCalificationTypes the course type calification types
	 */
	public void cacheResult(
		List<CourseTypeCalificationType> courseTypeCalificationTypes) {
		for (CourseTypeCalificationType courseTypeCalificationType : courseTypeCalificationTypes) {
			if (EntityCacheUtil.getResult(
						CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeCalificationTypeImpl.class,
						courseTypeCalificationType.getPrimaryKey()) == null) {
				cacheResult(courseTypeCalificationType);
			}
			else {
				courseTypeCalificationType.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all course type calification types.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CourseTypeCalificationTypeImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CourseTypeCalificationTypeImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the course type calification type.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CourseTypeCalificationType courseTypeCalificationType) {
		EntityCacheUtil.removeResult(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCalificationTypeImpl.class,
			courseTypeCalificationType.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(courseTypeCalificationType);
	}

	@Override
	public void clearCache(
		List<CourseTypeCalificationType> courseTypeCalificationTypes) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CourseTypeCalificationType courseTypeCalificationType : courseTypeCalificationTypes) {
			EntityCacheUtil.removeResult(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeCalificationTypeImpl.class,
				courseTypeCalificationType.getPrimaryKey());

			clearUniqueFindersCache(courseTypeCalificationType);
		}
	}

	protected void clearUniqueFindersCache(
		CourseTypeCalificationType courseTypeCalificationType) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPECALIFICATIONTYPEID,
			new Object[] {
				Long.valueOf(
					courseTypeCalificationType.getCourseTypeCalificationTypeId())
			});
	}

	/**
	 * Creates a new course type calification type with the primary key. Does not add the course type calification type to the database.
	 *
	 * @param courseTypeCalificationTypeId the primary key for the new course type calification type
	 * @return the new course type calification type
	 */
	public CourseTypeCalificationType create(long courseTypeCalificationTypeId) {
		CourseTypeCalificationType courseTypeCalificationType = new CourseTypeCalificationTypeImpl();

		courseTypeCalificationType.setNew(true);
		courseTypeCalificationType.setPrimaryKey(courseTypeCalificationTypeId);

		return courseTypeCalificationType;
	}

	/**
	 * Removes the course type calification type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param courseTypeCalificationTypeId the primary key of the course type calification type
	 * @return the course type calification type that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeCalificationTypeException if a course type calification type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCalificationType remove(long courseTypeCalificationTypeId)
		throws NoSuchCourseTypeCalificationTypeException, SystemException {
		return remove(Long.valueOf(courseTypeCalificationTypeId));
	}

	/**
	 * Removes the course type calification type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the course type calification type
	 * @return the course type calification type that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeCalificationTypeException if a course type calification type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeCalificationType remove(Serializable primaryKey)
		throws NoSuchCourseTypeCalificationTypeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CourseTypeCalificationType courseTypeCalificationType = (CourseTypeCalificationType)session.get(CourseTypeCalificationTypeImpl.class,
					primaryKey);

			if (courseTypeCalificationType == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCourseTypeCalificationTypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(courseTypeCalificationType);
		}
		catch (NoSuchCourseTypeCalificationTypeException nsee) {
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
	protected CourseTypeCalificationType removeImpl(
		CourseTypeCalificationType courseTypeCalificationType)
		throws SystemException {
		courseTypeCalificationType = toUnwrappedModel(courseTypeCalificationType);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, courseTypeCalificationType);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(courseTypeCalificationType);

		return courseTypeCalificationType;
	}

	@Override
	public CourseTypeCalificationType updateImpl(
		com.liferay.lms.model.CourseTypeCalificationType courseTypeCalificationType,
		boolean merge) throws SystemException {
		courseTypeCalificationType = toUnwrappedModel(courseTypeCalificationType);

		boolean isNew = courseTypeCalificationType.isNew();

		CourseTypeCalificationTypeModelImpl courseTypeCalificationTypeModelImpl = (CourseTypeCalificationTypeModelImpl)courseTypeCalificationType;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, courseTypeCalificationType, merge);

			courseTypeCalificationType.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew ||
				!CourseTypeCalificationTypeModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((courseTypeCalificationTypeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeCalificationTypeModelImpl.getOriginalCourseTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID,
					args);

				args = new Object[] {
						Long.valueOf(courseTypeCalificationTypeModelImpl.getCourseTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID,
					args);
			}
		}

		EntityCacheUtil.putResult(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeCalificationTypeImpl.class,
			courseTypeCalificationType.getPrimaryKey(),
			courseTypeCalificationType);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPECALIFICATIONTYPEID,
				new Object[] {
					Long.valueOf(
						courseTypeCalificationType.getCourseTypeCalificationTypeId())
				}, courseTypeCalificationType);
		}
		else {
			if ((courseTypeCalificationTypeModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_COURSETYPECALIFICATIONTYPEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeCalificationTypeModelImpl.getOriginalCourseTypeCalificationTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPECALIFICATIONTYPEID,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPECALIFICATIONTYPEID,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPECALIFICATIONTYPEID,
					new Object[] {
						Long.valueOf(
							courseTypeCalificationType.getCourseTypeCalificationTypeId())
					}, courseTypeCalificationType);
			}
		}

		return courseTypeCalificationType;
	}

	protected CourseTypeCalificationType toUnwrappedModel(
		CourseTypeCalificationType courseTypeCalificationType) {
		if (courseTypeCalificationType instanceof CourseTypeCalificationTypeImpl) {
			return courseTypeCalificationType;
		}

		CourseTypeCalificationTypeImpl courseTypeCalificationTypeImpl = new CourseTypeCalificationTypeImpl();

		courseTypeCalificationTypeImpl.setNew(courseTypeCalificationType.isNew());
		courseTypeCalificationTypeImpl.setPrimaryKey(courseTypeCalificationType.getPrimaryKey());

		courseTypeCalificationTypeImpl.setCourseTypeCalificationTypeId(courseTypeCalificationType.getCourseTypeCalificationTypeId());
		courseTypeCalificationTypeImpl.setCourseTypeId(courseTypeCalificationType.getCourseTypeId());
		courseTypeCalificationTypeImpl.setCalificationType(courseTypeCalificationType.getCalificationType());

		return courseTypeCalificationTypeImpl;
	}

	/**
	 * Returns the course type calification type with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type calification type
	 * @return the course type calification type
	 * @throws com.liferay.portal.NoSuchModelException if a course type calification type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeCalificationType findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type calification type with the primary key or throws a {@link com.liferay.lms.NoSuchCourseTypeCalificationTypeException} if it could not be found.
	 *
	 * @param courseTypeCalificationTypeId the primary key of the course type calification type
	 * @return the course type calification type
	 * @throws com.liferay.lms.NoSuchCourseTypeCalificationTypeException if a course type calification type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCalificationType findByPrimaryKey(
		long courseTypeCalificationTypeId)
		throws NoSuchCourseTypeCalificationTypeException, SystemException {
		CourseTypeCalificationType courseTypeCalificationType = fetchByPrimaryKey(courseTypeCalificationTypeId);

		if (courseTypeCalificationType == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					courseTypeCalificationTypeId);
			}

			throw new NoSuchCourseTypeCalificationTypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				courseTypeCalificationTypeId);
		}

		return courseTypeCalificationType;
	}

	/**
	 * Returns the course type calification type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type calification type
	 * @return the course type calification type, or <code>null</code> if a course type calification type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeCalificationType fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type calification type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param courseTypeCalificationTypeId the primary key of the course type calification type
	 * @return the course type calification type, or <code>null</code> if a course type calification type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCalificationType fetchByPrimaryKey(
		long courseTypeCalificationTypeId) throws SystemException {
		CourseTypeCalificationType courseTypeCalificationType = (CourseTypeCalificationType)EntityCacheUtil.getResult(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeCalificationTypeImpl.class,
				courseTypeCalificationTypeId);

		if (courseTypeCalificationType == _nullCourseTypeCalificationType) {
			return null;
		}

		if (courseTypeCalificationType == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				courseTypeCalificationType = (CourseTypeCalificationType)session.get(CourseTypeCalificationTypeImpl.class,
						Long.valueOf(courseTypeCalificationTypeId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (courseTypeCalificationType != null) {
					cacheResult(courseTypeCalificationType);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(CourseTypeCalificationTypeModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeCalificationTypeImpl.class,
						courseTypeCalificationTypeId,
						_nullCourseTypeCalificationType);
				}

				closeSession(session);
			}
		}

		return courseTypeCalificationType;
	}

	/**
	 * Returns the course type calification type where courseTypeCalificationTypeId = &#63; or throws a {@link com.liferay.lms.NoSuchCourseTypeCalificationTypeException} if it could not be found.
	 *
	 * @param courseTypeCalificationTypeId the course type calification type ID
	 * @return the matching course type calification type
	 * @throws com.liferay.lms.NoSuchCourseTypeCalificationTypeException if a matching course type calification type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCalificationType findByCourseTypeCalificationTypeId(
		long courseTypeCalificationTypeId)
		throws NoSuchCourseTypeCalificationTypeException, SystemException {
		CourseTypeCalificationType courseTypeCalificationType = fetchByCourseTypeCalificationTypeId(courseTypeCalificationTypeId);

		if (courseTypeCalificationType == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("courseTypeCalificationTypeId=");
			msg.append(courseTypeCalificationTypeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCourseTypeCalificationTypeException(msg.toString());
		}

		return courseTypeCalificationType;
	}

	/**
	 * Returns the course type calification type where courseTypeCalificationTypeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param courseTypeCalificationTypeId the course type calification type ID
	 * @return the matching course type calification type, or <code>null</code> if a matching course type calification type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCalificationType fetchByCourseTypeCalificationTypeId(
		long courseTypeCalificationTypeId) throws SystemException {
		return fetchByCourseTypeCalificationTypeId(courseTypeCalificationTypeId,
			true);
	}

	/**
	 * Returns the course type calification type where courseTypeCalificationTypeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param courseTypeCalificationTypeId the course type calification type ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching course type calification type, or <code>null</code> if a matching course type calification type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCalificationType fetchByCourseTypeCalificationTypeId(
		long courseTypeCalificationTypeId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeCalificationTypeId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_COURSETYPECALIFICATIONTYPEID,
					finderArgs, this);
		}

		if (result instanceof CourseTypeCalificationType) {
			CourseTypeCalificationType courseTypeCalificationType = (CourseTypeCalificationType)result;

			if ((courseTypeCalificationTypeId != courseTypeCalificationType.getCourseTypeCalificationTypeId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_COURSETYPECALIFICATIONTYPE_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPECALIFICATIONTYPEID_COURSETYPECALIFICATIONTYPEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeCalificationTypeId);

				List<CourseTypeCalificationType> list = q.list();

				result = list;

				CourseTypeCalificationType courseTypeCalificationType = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPECALIFICATIONTYPEID,
						finderArgs, list);
				}
				else {
					courseTypeCalificationType = list.get(0);

					cacheResult(courseTypeCalificationType);

					if ((courseTypeCalificationType.getCourseTypeCalificationTypeId() != courseTypeCalificationTypeId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPECALIFICATIONTYPEID,
							finderArgs, courseTypeCalificationType);
					}
				}

				return courseTypeCalificationType;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPECALIFICATIONTYPEID,
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
				return (CourseTypeCalificationType)result;
			}
		}
	}

	/**
	 * Returns all the course type calification types where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @return the matching course type calification types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeCalificationType> findByCourseTypeId(
		long courseTypeId) throws SystemException {
		return findByCourseTypeId(courseTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course type calification types where courseTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param start the lower bound of the range of course type calification types
	 * @param end the upper bound of the range of course type calification types (not inclusive)
	 * @return the range of matching course type calification types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeCalificationType> findByCourseTypeId(
		long courseTypeId, int start, int end) throws SystemException {
		return findByCourseTypeId(courseTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the course type calification types where courseTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param start the lower bound of the range of course type calification types
	 * @param end the upper bound of the range of course type calification types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching course type calification types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeCalificationType> findByCourseTypeId(
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

		List<CourseTypeCalificationType> list = (List<CourseTypeCalificationType>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CourseTypeCalificationType courseTypeCalificationType : list) {
				if ((courseTypeId != courseTypeCalificationType.getCourseTypeId())) {
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

			query.append(_SQL_SELECT_COURSETYPECALIFICATIONTYPE_WHERE);

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

				list = (List<CourseTypeCalificationType>)QueryUtil.list(q,
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
	 * Returns the first course type calification type in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type calification type
	 * @throws com.liferay.lms.NoSuchCourseTypeCalificationTypeException if a matching course type calification type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCalificationType findByCourseTypeId_First(
		long courseTypeId, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeCalificationTypeException, SystemException {
		CourseTypeCalificationType courseTypeCalificationType = fetchByCourseTypeId_First(courseTypeId,
				orderByComparator);

		if (courseTypeCalificationType != null) {
			return courseTypeCalificationType;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeCalificationTypeException(msg.toString());
	}

	/**
	 * Returns the first course type calification type in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type calification type, or <code>null</code> if a matching course type calification type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCalificationType fetchByCourseTypeId_First(
		long courseTypeId, OrderByComparator orderByComparator)
		throws SystemException {
		List<CourseTypeCalificationType> list = findByCourseTypeId(courseTypeId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last course type calification type in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type calification type
	 * @throws com.liferay.lms.NoSuchCourseTypeCalificationTypeException if a matching course type calification type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCalificationType findByCourseTypeId_Last(
		long courseTypeId, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeCalificationTypeException, SystemException {
		CourseTypeCalificationType courseTypeCalificationType = fetchByCourseTypeId_Last(courseTypeId,
				orderByComparator);

		if (courseTypeCalificationType != null) {
			return courseTypeCalificationType;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeCalificationTypeException(msg.toString());
	}

	/**
	 * Returns the last course type calification type in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type calification type, or <code>null</code> if a matching course type calification type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCalificationType fetchByCourseTypeId_Last(
		long courseTypeId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByCourseTypeId(courseTypeId);

		List<CourseTypeCalificationType> list = findByCourseTypeId(courseTypeId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the course type calification types before and after the current course type calification type in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeCalificationTypeId the primary key of the current course type calification type
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next course type calification type
	 * @throws com.liferay.lms.NoSuchCourseTypeCalificationTypeException if a course type calification type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCalificationType[] findByCourseTypeId_PrevAndNext(
		long courseTypeCalificationTypeId, long courseTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeCalificationTypeException, SystemException {
		CourseTypeCalificationType courseTypeCalificationType = findByPrimaryKey(courseTypeCalificationTypeId);

		Session session = null;

		try {
			session = openSession();

			CourseTypeCalificationType[] array = new CourseTypeCalificationTypeImpl[3];

			array[0] = getByCourseTypeId_PrevAndNext(session,
					courseTypeCalificationType, courseTypeId,
					orderByComparator, true);

			array[1] = courseTypeCalificationType;

			array[2] = getByCourseTypeId_PrevAndNext(session,
					courseTypeCalificationType, courseTypeId,
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

	protected CourseTypeCalificationType getByCourseTypeId_PrevAndNext(
		Session session, CourseTypeCalificationType courseTypeCalificationType,
		long courseTypeId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COURSETYPECALIFICATIONTYPE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(courseTypeCalificationType);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CourseTypeCalificationType> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the course type calification types.
	 *
	 * @return the course type calification types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeCalificationType> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course type calification types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course type calification types
	 * @param end the upper bound of the range of course type calification types (not inclusive)
	 * @return the range of course type calification types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeCalificationType> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the course type calification types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course type calification types
	 * @param end the upper bound of the range of course type calification types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of course type calification types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeCalificationType> findAll(int start, int end,
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

		List<CourseTypeCalificationType> list = (List<CourseTypeCalificationType>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_COURSETYPECALIFICATIONTYPE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COURSETYPECALIFICATIONTYPE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<CourseTypeCalificationType>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<CourseTypeCalificationType>)QueryUtil.list(q,
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
	 * Removes the course type calification type where courseTypeCalificationTypeId = &#63; from the database.
	 *
	 * @param courseTypeCalificationTypeId the course type calification type ID
	 * @return the course type calification type that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeCalificationType removeByCourseTypeCalificationTypeId(
		long courseTypeCalificationTypeId)
		throws NoSuchCourseTypeCalificationTypeException, SystemException {
		CourseTypeCalificationType courseTypeCalificationType = findByCourseTypeCalificationTypeId(courseTypeCalificationTypeId);

		return remove(courseTypeCalificationType);
	}

	/**
	 * Removes all the course type calification types where courseTypeId = &#63; from the database.
	 *
	 * @param courseTypeId the course type ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCourseTypeId(long courseTypeId)
		throws SystemException {
		for (CourseTypeCalificationType courseTypeCalificationType : findByCourseTypeId(
				courseTypeId)) {
			remove(courseTypeCalificationType);
		}
	}

	/**
	 * Removes all the course type calification types from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (CourseTypeCalificationType courseTypeCalificationType : findAll()) {
			remove(courseTypeCalificationType);
		}
	}

	/**
	 * Returns the number of course type calification types where courseTypeCalificationTypeId = &#63;.
	 *
	 * @param courseTypeCalificationTypeId the course type calification type ID
	 * @return the number of matching course type calification types
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseTypeCalificationTypeId(
		long courseTypeCalificationTypeId) throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeCalificationTypeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSETYPECALIFICATIONTYPEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COURSETYPECALIFICATIONTYPE_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPECALIFICATIONTYPEID_COURSETYPECALIFICATIONTYPEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeCalificationTypeId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COURSETYPECALIFICATIONTYPEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course type calification types where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @return the number of matching course type calification types
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseTypeId(long courseTypeId) throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COURSETYPECALIFICATIONTYPE_WHERE);

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
	 * Returns the number of course type calification types.
	 *
	 * @return the number of course type calification types
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COURSETYPECALIFICATIONTYPE);

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
	 * Initializes the course type calification type persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.CourseTypeCalificationType")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CourseTypeCalificationType>> listenersList = new ArrayList<ModelListener<CourseTypeCalificationType>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CourseTypeCalificationType>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(CourseTypeCalificationTypeImpl.class.getName());
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
	private static final String _SQL_SELECT_COURSETYPECALIFICATIONTYPE = "SELECT courseTypeCalificationType FROM CourseTypeCalificationType courseTypeCalificationType";
	private static final String _SQL_SELECT_COURSETYPECALIFICATIONTYPE_WHERE = "SELECT courseTypeCalificationType FROM CourseTypeCalificationType courseTypeCalificationType WHERE ";
	private static final String _SQL_COUNT_COURSETYPECALIFICATIONTYPE = "SELECT COUNT(courseTypeCalificationType) FROM CourseTypeCalificationType courseTypeCalificationType";
	private static final String _SQL_COUNT_COURSETYPECALIFICATIONTYPE_WHERE = "SELECT COUNT(courseTypeCalificationType) FROM CourseTypeCalificationType courseTypeCalificationType WHERE ";
	private static final String _FINDER_COLUMN_COURSETYPECALIFICATIONTYPEID_COURSETYPECALIFICATIONTYPEID_2 =
		"courseTypeCalificationType.courseTypeCalificationTypeId = ?";
	private static final String _FINDER_COLUMN_COURSETYPEID_COURSETYPEID_2 = "courseTypeCalificationType.courseTypeId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "courseTypeCalificationType.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CourseTypeCalificationType exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CourseTypeCalificationType exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(CourseTypeCalificationTypePersistenceImpl.class);
	private static CourseTypeCalificationType _nullCourseTypeCalificationType = new CourseTypeCalificationTypeImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<CourseTypeCalificationType> toCacheModel() {
				return _nullCourseTypeCalificationTypeCacheModel;
			}
		};

	private static CacheModel<CourseTypeCalificationType> _nullCourseTypeCalificationTypeCacheModel =
		new CacheModel<CourseTypeCalificationType>() {
			public CourseTypeCalificationType toEntityModel() {
				return _nullCourseTypeCalificationType;
			}
		};
}