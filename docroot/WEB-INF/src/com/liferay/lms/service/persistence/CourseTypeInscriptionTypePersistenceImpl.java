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

import com.liferay.lms.NoSuchCourseTypeInscriptionTypeException;
import com.liferay.lms.model.CourseTypeInscriptionType;
import com.liferay.lms.model.impl.CourseTypeInscriptionTypeImpl;
import com.liferay.lms.model.impl.CourseTypeInscriptionTypeModelImpl;

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
 * The persistence implementation for the course type inscription type service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CourseTypeInscriptionTypePersistence
 * @see CourseTypeInscriptionTypeUtil
 * @generated
 */
public class CourseTypeInscriptionTypePersistenceImpl
	extends BasePersistenceImpl<CourseTypeInscriptionType>
	implements CourseTypeInscriptionTypePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CourseTypeInscriptionTypeUtil} to access the course type inscription type persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CourseTypeInscriptionTypeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_COURSETYPEINSCRIPTIONTYPEID =
		new FinderPath(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeInscriptionTypeModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeInscriptionTypeImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCourseTypeInscriptionTypeId",
			new String[] { Long.class.getName() },
			CourseTypeInscriptionTypeModelImpl.COURSETYPEINSCRIPTIONTYPEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSETYPEINSCRIPTIONTYPEID =
		new FinderPath(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeInscriptionTypeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCourseTypeInscriptionTypeId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COURSETYPEID =
		new FinderPath(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeInscriptionTypeModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeInscriptionTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCourseTypeId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID =
		new FinderPath(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeInscriptionTypeModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeInscriptionTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCourseTypeId",
			new String[] { Long.class.getName() },
			CourseTypeInscriptionTypeModelImpl.COURSETYPEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSETYPEID = new FinderPath(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeInscriptionTypeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCourseTypeId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeInscriptionTypeModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeInscriptionTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeInscriptionTypeModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeInscriptionTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeInscriptionTypeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

	/**
	 * Caches the course type inscription type in the entity cache if it is enabled.
	 *
	 * @param courseTypeInscriptionType the course type inscription type
	 */
	public void cacheResult(CourseTypeInscriptionType courseTypeInscriptionType) {
		EntityCacheUtil.putResult(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeInscriptionTypeImpl.class,
			courseTypeInscriptionType.getPrimaryKey(), courseTypeInscriptionType);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPEINSCRIPTIONTYPEID,
			new Object[] {
				Long.valueOf(
					courseTypeInscriptionType.getCourseTypeInscriptionTypeId())
			}, courseTypeInscriptionType);

		courseTypeInscriptionType.resetOriginalValues();
	}

	/**
	 * Caches the course type inscription types in the entity cache if it is enabled.
	 *
	 * @param courseTypeInscriptionTypes the course type inscription types
	 */
	public void cacheResult(
		List<CourseTypeInscriptionType> courseTypeInscriptionTypes) {
		for (CourseTypeInscriptionType courseTypeInscriptionType : courseTypeInscriptionTypes) {
			if (EntityCacheUtil.getResult(
						CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeInscriptionTypeImpl.class,
						courseTypeInscriptionType.getPrimaryKey()) == null) {
				cacheResult(courseTypeInscriptionType);
			}
			else {
				courseTypeInscriptionType.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all course type inscription types.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CourseTypeInscriptionTypeImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CourseTypeInscriptionTypeImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the course type inscription type.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CourseTypeInscriptionType courseTypeInscriptionType) {
		EntityCacheUtil.removeResult(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeInscriptionTypeImpl.class,
			courseTypeInscriptionType.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(courseTypeInscriptionType);
	}

	@Override
	public void clearCache(
		List<CourseTypeInscriptionType> courseTypeInscriptionTypes) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CourseTypeInscriptionType courseTypeInscriptionType : courseTypeInscriptionTypes) {
			EntityCacheUtil.removeResult(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeInscriptionTypeImpl.class,
				courseTypeInscriptionType.getPrimaryKey());

			clearUniqueFindersCache(courseTypeInscriptionType);
		}
	}

	protected void clearUniqueFindersCache(
		CourseTypeInscriptionType courseTypeInscriptionType) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPEINSCRIPTIONTYPEID,
			new Object[] {
				Long.valueOf(
					courseTypeInscriptionType.getCourseTypeInscriptionTypeId())
			});
	}

	/**
	 * Creates a new course type inscription type with the primary key. Does not add the course type inscription type to the database.
	 *
	 * @param courseTypeInscriptionTypeId the primary key for the new course type inscription type
	 * @return the new course type inscription type
	 */
	public CourseTypeInscriptionType create(long courseTypeInscriptionTypeId) {
		CourseTypeInscriptionType courseTypeInscriptionType = new CourseTypeInscriptionTypeImpl();

		courseTypeInscriptionType.setNew(true);
		courseTypeInscriptionType.setPrimaryKey(courseTypeInscriptionTypeId);

		return courseTypeInscriptionType;
	}

	/**
	 * Removes the course type inscription type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param courseTypeInscriptionTypeId the primary key of the course type inscription type
	 * @return the course type inscription type that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeInscriptionTypeException if a course type inscription type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeInscriptionType remove(long courseTypeInscriptionTypeId)
		throws NoSuchCourseTypeInscriptionTypeException, SystemException {
		return remove(Long.valueOf(courseTypeInscriptionTypeId));
	}

	/**
	 * Removes the course type inscription type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the course type inscription type
	 * @return the course type inscription type that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeInscriptionTypeException if a course type inscription type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeInscriptionType remove(Serializable primaryKey)
		throws NoSuchCourseTypeInscriptionTypeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CourseTypeInscriptionType courseTypeInscriptionType = (CourseTypeInscriptionType)session.get(CourseTypeInscriptionTypeImpl.class,
					primaryKey);

			if (courseTypeInscriptionType == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCourseTypeInscriptionTypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(courseTypeInscriptionType);
		}
		catch (NoSuchCourseTypeInscriptionTypeException nsee) {
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
	protected CourseTypeInscriptionType removeImpl(
		CourseTypeInscriptionType courseTypeInscriptionType)
		throws SystemException {
		courseTypeInscriptionType = toUnwrappedModel(courseTypeInscriptionType);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, courseTypeInscriptionType);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(courseTypeInscriptionType);

		return courseTypeInscriptionType;
	}

	@Override
	public CourseTypeInscriptionType updateImpl(
		com.liferay.lms.model.CourseTypeInscriptionType courseTypeInscriptionType,
		boolean merge) throws SystemException {
		courseTypeInscriptionType = toUnwrappedModel(courseTypeInscriptionType);

		boolean isNew = courseTypeInscriptionType.isNew();

		CourseTypeInscriptionTypeModelImpl courseTypeInscriptionTypeModelImpl = (CourseTypeInscriptionTypeModelImpl)courseTypeInscriptionType;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, courseTypeInscriptionType, merge);

			courseTypeInscriptionType.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew ||
				!CourseTypeInscriptionTypeModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((courseTypeInscriptionTypeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeInscriptionTypeModelImpl.getOriginalCourseTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID,
					args);

				args = new Object[] {
						Long.valueOf(courseTypeInscriptionTypeModelImpl.getCourseTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID,
					args);
			}
		}

		EntityCacheUtil.putResult(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeInscriptionTypeImpl.class,
			courseTypeInscriptionType.getPrimaryKey(), courseTypeInscriptionType);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPEINSCRIPTIONTYPEID,
				new Object[] {
					Long.valueOf(
						courseTypeInscriptionType.getCourseTypeInscriptionTypeId())
				}, courseTypeInscriptionType);
		}
		else {
			if ((courseTypeInscriptionTypeModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_COURSETYPEINSCRIPTIONTYPEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeInscriptionTypeModelImpl.getOriginalCourseTypeInscriptionTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEINSCRIPTIONTYPEID,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPEINSCRIPTIONTYPEID,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPEINSCRIPTIONTYPEID,
					new Object[] {
						Long.valueOf(
							courseTypeInscriptionType.getCourseTypeInscriptionTypeId())
					}, courseTypeInscriptionType);
			}
		}

		return courseTypeInscriptionType;
	}

	protected CourseTypeInscriptionType toUnwrappedModel(
		CourseTypeInscriptionType courseTypeInscriptionType) {
		if (courseTypeInscriptionType instanceof CourseTypeInscriptionTypeImpl) {
			return courseTypeInscriptionType;
		}

		CourseTypeInscriptionTypeImpl courseTypeInscriptionTypeImpl = new CourseTypeInscriptionTypeImpl();

		courseTypeInscriptionTypeImpl.setNew(courseTypeInscriptionType.isNew());
		courseTypeInscriptionTypeImpl.setPrimaryKey(courseTypeInscriptionType.getPrimaryKey());

		courseTypeInscriptionTypeImpl.setCourseTypeInscriptionTypeId(courseTypeInscriptionType.getCourseTypeInscriptionTypeId());
		courseTypeInscriptionTypeImpl.setCourseTypeId(courseTypeInscriptionType.getCourseTypeId());
		courseTypeInscriptionTypeImpl.setInscriptionType(courseTypeInscriptionType.getInscriptionType());

		return courseTypeInscriptionTypeImpl;
	}

	/**
	 * Returns the course type inscription type with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type inscription type
	 * @return the course type inscription type
	 * @throws com.liferay.portal.NoSuchModelException if a course type inscription type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeInscriptionType findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type inscription type with the primary key or throws a {@link com.liferay.lms.NoSuchCourseTypeInscriptionTypeException} if it could not be found.
	 *
	 * @param courseTypeInscriptionTypeId the primary key of the course type inscription type
	 * @return the course type inscription type
	 * @throws com.liferay.lms.NoSuchCourseTypeInscriptionTypeException if a course type inscription type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeInscriptionType findByPrimaryKey(
		long courseTypeInscriptionTypeId)
		throws NoSuchCourseTypeInscriptionTypeException, SystemException {
		CourseTypeInscriptionType courseTypeInscriptionType = fetchByPrimaryKey(courseTypeInscriptionTypeId);

		if (courseTypeInscriptionType == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					courseTypeInscriptionTypeId);
			}

			throw new NoSuchCourseTypeInscriptionTypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				courseTypeInscriptionTypeId);
		}

		return courseTypeInscriptionType;
	}

	/**
	 * Returns the course type inscription type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type inscription type
	 * @return the course type inscription type, or <code>null</code> if a course type inscription type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeInscriptionType fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type inscription type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param courseTypeInscriptionTypeId the primary key of the course type inscription type
	 * @return the course type inscription type, or <code>null</code> if a course type inscription type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeInscriptionType fetchByPrimaryKey(
		long courseTypeInscriptionTypeId) throws SystemException {
		CourseTypeInscriptionType courseTypeInscriptionType = (CourseTypeInscriptionType)EntityCacheUtil.getResult(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeInscriptionTypeImpl.class, courseTypeInscriptionTypeId);

		if (courseTypeInscriptionType == _nullCourseTypeInscriptionType) {
			return null;
		}

		if (courseTypeInscriptionType == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				courseTypeInscriptionType = (CourseTypeInscriptionType)session.get(CourseTypeInscriptionTypeImpl.class,
						Long.valueOf(courseTypeInscriptionTypeId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (courseTypeInscriptionType != null) {
					cacheResult(courseTypeInscriptionType);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(CourseTypeInscriptionTypeModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeInscriptionTypeImpl.class,
						courseTypeInscriptionTypeId,
						_nullCourseTypeInscriptionType);
				}

				closeSession(session);
			}
		}

		return courseTypeInscriptionType;
	}

	/**
	 * Returns the course type inscription type where courseTypeInscriptionTypeId = &#63; or throws a {@link com.liferay.lms.NoSuchCourseTypeInscriptionTypeException} if it could not be found.
	 *
	 * @param courseTypeInscriptionTypeId the course type inscription type ID
	 * @return the matching course type inscription type
	 * @throws com.liferay.lms.NoSuchCourseTypeInscriptionTypeException if a matching course type inscription type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeInscriptionType findByCourseTypeInscriptionTypeId(
		long courseTypeInscriptionTypeId)
		throws NoSuchCourseTypeInscriptionTypeException, SystemException {
		CourseTypeInscriptionType courseTypeInscriptionType = fetchByCourseTypeInscriptionTypeId(courseTypeInscriptionTypeId);

		if (courseTypeInscriptionType == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("courseTypeInscriptionTypeId=");
			msg.append(courseTypeInscriptionTypeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCourseTypeInscriptionTypeException(msg.toString());
		}

		return courseTypeInscriptionType;
	}

	/**
	 * Returns the course type inscription type where courseTypeInscriptionTypeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param courseTypeInscriptionTypeId the course type inscription type ID
	 * @return the matching course type inscription type, or <code>null</code> if a matching course type inscription type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeInscriptionType fetchByCourseTypeInscriptionTypeId(
		long courseTypeInscriptionTypeId) throws SystemException {
		return fetchByCourseTypeInscriptionTypeId(courseTypeInscriptionTypeId,
			true);
	}

	/**
	 * Returns the course type inscription type where courseTypeInscriptionTypeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param courseTypeInscriptionTypeId the course type inscription type ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching course type inscription type, or <code>null</code> if a matching course type inscription type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeInscriptionType fetchByCourseTypeInscriptionTypeId(
		long courseTypeInscriptionTypeId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeInscriptionTypeId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_COURSETYPEINSCRIPTIONTYPEID,
					finderArgs, this);
		}

		if (result instanceof CourseTypeInscriptionType) {
			CourseTypeInscriptionType courseTypeInscriptionType = (CourseTypeInscriptionType)result;

			if ((courseTypeInscriptionTypeId != courseTypeInscriptionType.getCourseTypeInscriptionTypeId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_COURSETYPEINSCRIPTIONTYPE_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPEINSCRIPTIONTYPEID_COURSETYPEINSCRIPTIONTYPEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeInscriptionTypeId);

				List<CourseTypeInscriptionType> list = q.list();

				result = list;

				CourseTypeInscriptionType courseTypeInscriptionType = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPEINSCRIPTIONTYPEID,
						finderArgs, list);
				}
				else {
					courseTypeInscriptionType = list.get(0);

					cacheResult(courseTypeInscriptionType);

					if ((courseTypeInscriptionType.getCourseTypeInscriptionTypeId() != courseTypeInscriptionTypeId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPEINSCRIPTIONTYPEID,
							finderArgs, courseTypeInscriptionType);
					}
				}

				return courseTypeInscriptionType;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPEINSCRIPTIONTYPEID,
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
				return (CourseTypeInscriptionType)result;
			}
		}
	}

	/**
	 * Returns all the course type inscription types where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @return the matching course type inscription types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeInscriptionType> findByCourseTypeId(long courseTypeId)
		throws SystemException {
		return findByCourseTypeId(courseTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course type inscription types where courseTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param start the lower bound of the range of course type inscription types
	 * @param end the upper bound of the range of course type inscription types (not inclusive)
	 * @return the range of matching course type inscription types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeInscriptionType> findByCourseTypeId(
		long courseTypeId, int start, int end) throws SystemException {
		return findByCourseTypeId(courseTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the course type inscription types where courseTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param start the lower bound of the range of course type inscription types
	 * @param end the upper bound of the range of course type inscription types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching course type inscription types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeInscriptionType> findByCourseTypeId(
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

		List<CourseTypeInscriptionType> list = (List<CourseTypeInscriptionType>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CourseTypeInscriptionType courseTypeInscriptionType : list) {
				if ((courseTypeId != courseTypeInscriptionType.getCourseTypeId())) {
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

			query.append(_SQL_SELECT_COURSETYPEINSCRIPTIONTYPE_WHERE);

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

				list = (List<CourseTypeInscriptionType>)QueryUtil.list(q,
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
	 * Returns the first course type inscription type in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type inscription type
	 * @throws com.liferay.lms.NoSuchCourseTypeInscriptionTypeException if a matching course type inscription type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeInscriptionType findByCourseTypeId_First(
		long courseTypeId, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeInscriptionTypeException, SystemException {
		CourseTypeInscriptionType courseTypeInscriptionType = fetchByCourseTypeId_First(courseTypeId,
				orderByComparator);

		if (courseTypeInscriptionType != null) {
			return courseTypeInscriptionType;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeInscriptionTypeException(msg.toString());
	}

	/**
	 * Returns the first course type inscription type in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type inscription type, or <code>null</code> if a matching course type inscription type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeInscriptionType fetchByCourseTypeId_First(
		long courseTypeId, OrderByComparator orderByComparator)
		throws SystemException {
		List<CourseTypeInscriptionType> list = findByCourseTypeId(courseTypeId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last course type inscription type in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type inscription type
	 * @throws com.liferay.lms.NoSuchCourseTypeInscriptionTypeException if a matching course type inscription type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeInscriptionType findByCourseTypeId_Last(
		long courseTypeId, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeInscriptionTypeException, SystemException {
		CourseTypeInscriptionType courseTypeInscriptionType = fetchByCourseTypeId_Last(courseTypeId,
				orderByComparator);

		if (courseTypeInscriptionType != null) {
			return courseTypeInscriptionType;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeInscriptionTypeException(msg.toString());
	}

	/**
	 * Returns the last course type inscription type in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type inscription type, or <code>null</code> if a matching course type inscription type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeInscriptionType fetchByCourseTypeId_Last(
		long courseTypeId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByCourseTypeId(courseTypeId);

		List<CourseTypeInscriptionType> list = findByCourseTypeId(courseTypeId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the course type inscription types before and after the current course type inscription type in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeInscriptionTypeId the primary key of the current course type inscription type
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next course type inscription type
	 * @throws com.liferay.lms.NoSuchCourseTypeInscriptionTypeException if a course type inscription type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeInscriptionType[] findByCourseTypeId_PrevAndNext(
		long courseTypeInscriptionTypeId, long courseTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeInscriptionTypeException, SystemException {
		CourseTypeInscriptionType courseTypeInscriptionType = findByPrimaryKey(courseTypeInscriptionTypeId);

		Session session = null;

		try {
			session = openSession();

			CourseTypeInscriptionType[] array = new CourseTypeInscriptionTypeImpl[3];

			array[0] = getByCourseTypeId_PrevAndNext(session,
					courseTypeInscriptionType, courseTypeId, orderByComparator,
					true);

			array[1] = courseTypeInscriptionType;

			array[2] = getByCourseTypeId_PrevAndNext(session,
					courseTypeInscriptionType, courseTypeId, orderByComparator,
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

	protected CourseTypeInscriptionType getByCourseTypeId_PrevAndNext(
		Session session, CourseTypeInscriptionType courseTypeInscriptionType,
		long courseTypeId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COURSETYPEINSCRIPTIONTYPE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(courseTypeInscriptionType);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CourseTypeInscriptionType> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the course type inscription types.
	 *
	 * @return the course type inscription types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeInscriptionType> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course type inscription types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course type inscription types
	 * @param end the upper bound of the range of course type inscription types (not inclusive)
	 * @return the range of course type inscription types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeInscriptionType> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the course type inscription types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course type inscription types
	 * @param end the upper bound of the range of course type inscription types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of course type inscription types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeInscriptionType> findAll(int start, int end,
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

		List<CourseTypeInscriptionType> list = (List<CourseTypeInscriptionType>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_COURSETYPEINSCRIPTIONTYPE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COURSETYPEINSCRIPTIONTYPE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<CourseTypeInscriptionType>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<CourseTypeInscriptionType>)QueryUtil.list(q,
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
	 * Removes the course type inscription type where courseTypeInscriptionTypeId = &#63; from the database.
	 *
	 * @param courseTypeInscriptionTypeId the course type inscription type ID
	 * @return the course type inscription type that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeInscriptionType removeByCourseTypeInscriptionTypeId(
		long courseTypeInscriptionTypeId)
		throws NoSuchCourseTypeInscriptionTypeException, SystemException {
		CourseTypeInscriptionType courseTypeInscriptionType = findByCourseTypeInscriptionTypeId(courseTypeInscriptionTypeId);

		return remove(courseTypeInscriptionType);
	}

	/**
	 * Removes all the course type inscription types where courseTypeId = &#63; from the database.
	 *
	 * @param courseTypeId the course type ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCourseTypeId(long courseTypeId)
		throws SystemException {
		for (CourseTypeInscriptionType courseTypeInscriptionType : findByCourseTypeId(
				courseTypeId)) {
			remove(courseTypeInscriptionType);
		}
	}

	/**
	 * Removes all the course type inscription types from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (CourseTypeInscriptionType courseTypeInscriptionType : findAll()) {
			remove(courseTypeInscriptionType);
		}
	}

	/**
	 * Returns the number of course type inscription types where courseTypeInscriptionTypeId = &#63;.
	 *
	 * @param courseTypeInscriptionTypeId the course type inscription type ID
	 * @return the number of matching course type inscription types
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseTypeInscriptionTypeId(
		long courseTypeInscriptionTypeId) throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeInscriptionTypeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSETYPEINSCRIPTIONTYPEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COURSETYPEINSCRIPTIONTYPE_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPEINSCRIPTIONTYPEID_COURSETYPEINSCRIPTIONTYPEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeInscriptionTypeId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COURSETYPEINSCRIPTIONTYPEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course type inscription types where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @return the number of matching course type inscription types
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseTypeId(long courseTypeId) throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COURSETYPEINSCRIPTIONTYPE_WHERE);

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
	 * Returns the number of course type inscription types.
	 *
	 * @return the number of course type inscription types
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COURSETYPEINSCRIPTIONTYPE);

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
	 * Initializes the course type inscription type persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.CourseTypeInscriptionType")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CourseTypeInscriptionType>> listenersList = new ArrayList<ModelListener<CourseTypeInscriptionType>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CourseTypeInscriptionType>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(CourseTypeInscriptionTypeImpl.class.getName());
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
	private static final String _SQL_SELECT_COURSETYPEINSCRIPTIONTYPE = "SELECT courseTypeInscriptionType FROM CourseTypeInscriptionType courseTypeInscriptionType";
	private static final String _SQL_SELECT_COURSETYPEINSCRIPTIONTYPE_WHERE = "SELECT courseTypeInscriptionType FROM CourseTypeInscriptionType courseTypeInscriptionType WHERE ";
	private static final String _SQL_COUNT_COURSETYPEINSCRIPTIONTYPE = "SELECT COUNT(courseTypeInscriptionType) FROM CourseTypeInscriptionType courseTypeInscriptionType";
	private static final String _SQL_COUNT_COURSETYPEINSCRIPTIONTYPE_WHERE = "SELECT COUNT(courseTypeInscriptionType) FROM CourseTypeInscriptionType courseTypeInscriptionType WHERE ";
	private static final String _FINDER_COLUMN_COURSETYPEINSCRIPTIONTYPEID_COURSETYPEINSCRIPTIONTYPEID_2 =
		"courseTypeInscriptionType.courseTypeInscriptionTypeId = ?";
	private static final String _FINDER_COLUMN_COURSETYPEID_COURSETYPEID_2 = "courseTypeInscriptionType.courseTypeId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "courseTypeInscriptionType.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CourseTypeInscriptionType exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CourseTypeInscriptionType exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(CourseTypeInscriptionTypePersistenceImpl.class);
	private static CourseTypeInscriptionType _nullCourseTypeInscriptionType = new CourseTypeInscriptionTypeImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<CourseTypeInscriptionType> toCacheModel() {
				return _nullCourseTypeInscriptionTypeCacheModel;
			}
		};

	private static CacheModel<CourseTypeInscriptionType> _nullCourseTypeInscriptionTypeCacheModel =
		new CacheModel<CourseTypeInscriptionType>() {
			public CourseTypeInscriptionType toEntityModel() {
				return _nullCourseTypeInscriptionType;
			}
		};
}