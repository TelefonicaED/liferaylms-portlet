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

import com.liferay.lms.NoSuchCourseTypeRelationException;
import com.liferay.lms.model.CourseTypeRelation;
import com.liferay.lms.model.impl.CourseTypeRelationImpl;
import com.liferay.lms.model.impl.CourseTypeRelationModelImpl;

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
 * The persistence implementation for the course type relation service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CourseTypeRelationPersistence
 * @see CourseTypeRelationUtil
 * @generated
 */
public class CourseTypeRelationPersistenceImpl extends BasePersistenceImpl<CourseTypeRelation>
	implements CourseTypeRelationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CourseTypeRelationUtil} to access the course type relation persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CourseTypeRelationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COURSETYPEID =
		new FinderPath(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCourseTypeId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID =
		new FinderPath(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCourseTypeId",
			new String[] { Long.class.getName() },
			CourseTypeRelationModelImpl.COURSETYPEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSETYPEID = new FinderPath(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCourseTypeId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COURSETYPEIDCLASSNAMEID =
		new FinderPath(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCourseTypeIdClassNameId",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEIDCLASSNAMEID =
		new FinderPath(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCourseTypeIdClassNameId",
			new String[] { Long.class.getName(), Long.class.getName() },
			CourseTypeRelationModelImpl.COURSETYPEID_COLUMN_BITMASK |
			CourseTypeRelationModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSETYPEIDCLASSNAMEID = new FinderPath(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCourseTypeIdClassNameId",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_COURSETYPEIDCLASSNAMEIDCLASSPK =
		new FinderPath(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeRelationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCourseTypeIdClassNameIdClassPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			CourseTypeRelationModelImpl.COURSETYPEID_COLUMN_BITMASK |
			CourseTypeRelationModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CourseTypeRelationModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSETYPEIDCLASSNAMEIDCLASSPK =
		new FinderPath(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCourseTypeIdClassNameIdClassPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeRelationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the course type relation in the entity cache if it is enabled.
	 *
	 * @param courseTypeRelation the course type relation
	 */
	public void cacheResult(CourseTypeRelation courseTypeRelation) {
		EntityCacheUtil.putResult(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationImpl.class, courseTypeRelation.getPrimaryKey(),
			courseTypeRelation);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPEIDCLASSNAMEIDCLASSPK,
			new Object[] {
				Long.valueOf(courseTypeRelation.getCourseTypeId()),
				Long.valueOf(courseTypeRelation.getClassNameId()),
				Long.valueOf(courseTypeRelation.getClassPK())
			}, courseTypeRelation);

		courseTypeRelation.resetOriginalValues();
	}

	/**
	 * Caches the course type relations in the entity cache if it is enabled.
	 *
	 * @param courseTypeRelations the course type relations
	 */
	public void cacheResult(List<CourseTypeRelation> courseTypeRelations) {
		for (CourseTypeRelation courseTypeRelation : courseTypeRelations) {
			if (EntityCacheUtil.getResult(
						CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeRelationImpl.class,
						courseTypeRelation.getPrimaryKey()) == null) {
				cacheResult(courseTypeRelation);
			}
			else {
				courseTypeRelation.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all course type relations.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CourseTypeRelationImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CourseTypeRelationImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the course type relation.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CourseTypeRelation courseTypeRelation) {
		EntityCacheUtil.removeResult(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationImpl.class, courseTypeRelation.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(courseTypeRelation);
	}

	@Override
	public void clearCache(List<CourseTypeRelation> courseTypeRelations) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CourseTypeRelation courseTypeRelation : courseTypeRelations) {
			EntityCacheUtil.removeResult(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeRelationImpl.class, courseTypeRelation.getPrimaryKey());

			clearUniqueFindersCache(courseTypeRelation);
		}
	}

	protected void clearUniqueFindersCache(
		CourseTypeRelation courseTypeRelation) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPEIDCLASSNAMEIDCLASSPK,
			new Object[] {
				Long.valueOf(courseTypeRelation.getCourseTypeId()),
				Long.valueOf(courseTypeRelation.getClassNameId()),
				Long.valueOf(courseTypeRelation.getClassPK())
			});
	}

	/**
	 * Creates a new course type relation with the primary key. Does not add the course type relation to the database.
	 *
	 * @param courseTypeRelationId the primary key for the new course type relation
	 * @return the new course type relation
	 */
	public CourseTypeRelation create(long courseTypeRelationId) {
		CourseTypeRelation courseTypeRelation = new CourseTypeRelationImpl();

		courseTypeRelation.setNew(true);
		courseTypeRelation.setPrimaryKey(courseTypeRelationId);

		return courseTypeRelation;
	}

	/**
	 * Removes the course type relation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param courseTypeRelationId the primary key of the course type relation
	 * @return the course type relation that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeRelationException if a course type relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation remove(long courseTypeRelationId)
		throws NoSuchCourseTypeRelationException, SystemException {
		return remove(Long.valueOf(courseTypeRelationId));
	}

	/**
	 * Removes the course type relation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the course type relation
	 * @return the course type relation that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeRelationException if a course type relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeRelation remove(Serializable primaryKey)
		throws NoSuchCourseTypeRelationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CourseTypeRelation courseTypeRelation = (CourseTypeRelation)session.get(CourseTypeRelationImpl.class,
					primaryKey);

			if (courseTypeRelation == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCourseTypeRelationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(courseTypeRelation);
		}
		catch (NoSuchCourseTypeRelationException nsee) {
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
	protected CourseTypeRelation removeImpl(
		CourseTypeRelation courseTypeRelation) throws SystemException {
		courseTypeRelation = toUnwrappedModel(courseTypeRelation);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, courseTypeRelation);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(courseTypeRelation);

		return courseTypeRelation;
	}

	@Override
	public CourseTypeRelation updateImpl(
		com.liferay.lms.model.CourseTypeRelation courseTypeRelation,
		boolean merge) throws SystemException {
		courseTypeRelation = toUnwrappedModel(courseTypeRelation);

		boolean isNew = courseTypeRelation.isNew();

		CourseTypeRelationModelImpl courseTypeRelationModelImpl = (CourseTypeRelationModelImpl)courseTypeRelation;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, courseTypeRelation, merge);

			courseTypeRelation.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !CourseTypeRelationModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((courseTypeRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeRelationModelImpl.getOriginalCourseTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID,
					args);

				args = new Object[] {
						Long.valueOf(courseTypeRelationModelImpl.getCourseTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID,
					args);
			}

			if ((courseTypeRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEIDCLASSNAMEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeRelationModelImpl.getOriginalCourseTypeId()),
						Long.valueOf(courseTypeRelationModelImpl.getOriginalClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEIDCLASSNAMEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEIDCLASSNAMEID,
					args);

				args = new Object[] {
						Long.valueOf(courseTypeRelationModelImpl.getCourseTypeId()),
						Long.valueOf(courseTypeRelationModelImpl.getClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEIDCLASSNAMEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEIDCLASSNAMEID,
					args);
			}
		}

		EntityCacheUtil.putResult(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeRelationImpl.class, courseTypeRelation.getPrimaryKey(),
			courseTypeRelation);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPEIDCLASSNAMEIDCLASSPK,
				new Object[] {
					Long.valueOf(courseTypeRelation.getCourseTypeId()),
					Long.valueOf(courseTypeRelation.getClassNameId()),
					Long.valueOf(courseTypeRelation.getClassPK())
				}, courseTypeRelation);
		}
		else {
			if ((courseTypeRelationModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_COURSETYPEIDCLASSNAMEIDCLASSPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeRelationModelImpl.getOriginalCourseTypeId()),
						Long.valueOf(courseTypeRelationModelImpl.getOriginalClassNameId()),
						Long.valueOf(courseTypeRelationModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEIDCLASSNAMEIDCLASSPK,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPEIDCLASSNAMEIDCLASSPK,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPEIDCLASSNAMEIDCLASSPK,
					new Object[] {
						Long.valueOf(courseTypeRelation.getCourseTypeId()),
						Long.valueOf(courseTypeRelation.getClassNameId()),
						Long.valueOf(courseTypeRelation.getClassPK())
					}, courseTypeRelation);
			}
		}

		return courseTypeRelation;
	}

	protected CourseTypeRelation toUnwrappedModel(
		CourseTypeRelation courseTypeRelation) {
		if (courseTypeRelation instanceof CourseTypeRelationImpl) {
			return courseTypeRelation;
		}

		CourseTypeRelationImpl courseTypeRelationImpl = new CourseTypeRelationImpl();

		courseTypeRelationImpl.setNew(courseTypeRelation.isNew());
		courseTypeRelationImpl.setPrimaryKey(courseTypeRelation.getPrimaryKey());

		courseTypeRelationImpl.setCourseTypeRelationId(courseTypeRelation.getCourseTypeRelationId());
		courseTypeRelationImpl.setCourseTypeId(courseTypeRelation.getCourseTypeId());
		courseTypeRelationImpl.setClassNameId(courseTypeRelation.getClassNameId());
		courseTypeRelationImpl.setClassPK(courseTypeRelation.getClassPK());

		return courseTypeRelationImpl;
	}

	/**
	 * Returns the course type relation with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type relation
	 * @return the course type relation
	 * @throws com.liferay.portal.NoSuchModelException if a course type relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeRelation findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type relation with the primary key or throws a {@link com.liferay.lms.NoSuchCourseTypeRelationException} if it could not be found.
	 *
	 * @param courseTypeRelationId the primary key of the course type relation
	 * @return the course type relation
	 * @throws com.liferay.lms.NoSuchCourseTypeRelationException if a course type relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation findByPrimaryKey(long courseTypeRelationId)
		throws NoSuchCourseTypeRelationException, SystemException {
		CourseTypeRelation courseTypeRelation = fetchByPrimaryKey(courseTypeRelationId);

		if (courseTypeRelation == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					courseTypeRelationId);
			}

			throw new NoSuchCourseTypeRelationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				courseTypeRelationId);
		}

		return courseTypeRelation;
	}

	/**
	 * Returns the course type relation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type relation
	 * @return the course type relation, or <code>null</code> if a course type relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeRelation fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type relation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param courseTypeRelationId the primary key of the course type relation
	 * @return the course type relation, or <code>null</code> if a course type relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation fetchByPrimaryKey(long courseTypeRelationId)
		throws SystemException {
		CourseTypeRelation courseTypeRelation = (CourseTypeRelation)EntityCacheUtil.getResult(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeRelationImpl.class, courseTypeRelationId);

		if (courseTypeRelation == _nullCourseTypeRelation) {
			return null;
		}

		if (courseTypeRelation == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				courseTypeRelation = (CourseTypeRelation)session.get(CourseTypeRelationImpl.class,
						Long.valueOf(courseTypeRelationId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (courseTypeRelation != null) {
					cacheResult(courseTypeRelation);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(CourseTypeRelationModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeRelationImpl.class, courseTypeRelationId,
						_nullCourseTypeRelation);
				}

				closeSession(session);
			}
		}

		return courseTypeRelation;
	}

	/**
	 * Returns all the course type relations where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @return the matching course type relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeRelation> findByCourseTypeId(long courseTypeId)
		throws SystemException {
		return findByCourseTypeId(courseTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course type relations where courseTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param start the lower bound of the range of course type relations
	 * @param end the upper bound of the range of course type relations (not inclusive)
	 * @return the range of matching course type relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeRelation> findByCourseTypeId(long courseTypeId,
		int start, int end) throws SystemException {
		return findByCourseTypeId(courseTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the course type relations where courseTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param start the lower bound of the range of course type relations
	 * @param end the upper bound of the range of course type relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching course type relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeRelation> findByCourseTypeId(long courseTypeId,
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

		List<CourseTypeRelation> list = (List<CourseTypeRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CourseTypeRelation courseTypeRelation : list) {
				if ((courseTypeId != courseTypeRelation.getCourseTypeId())) {
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

			query.append(_SQL_SELECT_COURSETYPERELATION_WHERE);

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

				list = (List<CourseTypeRelation>)QueryUtil.list(q,
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
	 * Returns the first course type relation in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type relation
	 * @throws com.liferay.lms.NoSuchCourseTypeRelationException if a matching course type relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation findByCourseTypeId_First(long courseTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeRelationException, SystemException {
		CourseTypeRelation courseTypeRelation = fetchByCourseTypeId_First(courseTypeId,
				orderByComparator);

		if (courseTypeRelation != null) {
			return courseTypeRelation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeRelationException(msg.toString());
	}

	/**
	 * Returns the first course type relation in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type relation, or <code>null</code> if a matching course type relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation fetchByCourseTypeId_First(long courseTypeId,
		OrderByComparator orderByComparator) throws SystemException {
		List<CourseTypeRelation> list = findByCourseTypeId(courseTypeId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last course type relation in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type relation
	 * @throws com.liferay.lms.NoSuchCourseTypeRelationException if a matching course type relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation findByCourseTypeId_Last(long courseTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeRelationException, SystemException {
		CourseTypeRelation courseTypeRelation = fetchByCourseTypeId_Last(courseTypeId,
				orderByComparator);

		if (courseTypeRelation != null) {
			return courseTypeRelation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeRelationException(msg.toString());
	}

	/**
	 * Returns the last course type relation in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type relation, or <code>null</code> if a matching course type relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation fetchByCourseTypeId_Last(long courseTypeId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCourseTypeId(courseTypeId);

		List<CourseTypeRelation> list = findByCourseTypeId(courseTypeId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the course type relations before and after the current course type relation in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeRelationId the primary key of the current course type relation
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next course type relation
	 * @throws com.liferay.lms.NoSuchCourseTypeRelationException if a course type relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation[] findByCourseTypeId_PrevAndNext(
		long courseTypeRelationId, long courseTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeRelationException, SystemException {
		CourseTypeRelation courseTypeRelation = findByPrimaryKey(courseTypeRelationId);

		Session session = null;

		try {
			session = openSession();

			CourseTypeRelation[] array = new CourseTypeRelationImpl[3];

			array[0] = getByCourseTypeId_PrevAndNext(session,
					courseTypeRelation, courseTypeId, orderByComparator, true);

			array[1] = courseTypeRelation;

			array[2] = getByCourseTypeId_PrevAndNext(session,
					courseTypeRelation, courseTypeId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CourseTypeRelation getByCourseTypeId_PrevAndNext(
		Session session, CourseTypeRelation courseTypeRelation,
		long courseTypeId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COURSETYPERELATION_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(courseTypeRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CourseTypeRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the course type relations where courseTypeId = &#63; and classNameId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @return the matching course type relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeRelation> findByCourseTypeIdClassNameId(
		long courseTypeId, long classNameId) throws SystemException {
		return findByCourseTypeIdClassNameId(courseTypeId, classNameId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course type relations where courseTypeId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of course type relations
	 * @param end the upper bound of the range of course type relations (not inclusive)
	 * @return the range of matching course type relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeRelation> findByCourseTypeIdClassNameId(
		long courseTypeId, long classNameId, int start, int end)
		throws SystemException {
		return findByCourseTypeIdClassNameId(courseTypeId, classNameId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the course type relations where courseTypeId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of course type relations
	 * @param end the upper bound of the range of course type relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching course type relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeRelation> findByCourseTypeIdClassNameId(
		long courseTypeId, long classNameId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEIDCLASSNAMEID;
			finderArgs = new Object[] { courseTypeId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COURSETYPEIDCLASSNAMEID;
			finderArgs = new Object[] {
					courseTypeId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<CourseTypeRelation> list = (List<CourseTypeRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CourseTypeRelation courseTypeRelation : list) {
				if ((courseTypeId != courseTypeRelation.getCourseTypeId()) ||
						(classNameId != courseTypeRelation.getClassNameId())) {
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

			query.append(_SQL_SELECT_COURSETYPERELATION_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPEIDCLASSNAMEID_COURSETYPEID_2);

			query.append(_FINDER_COLUMN_COURSETYPEIDCLASSNAMEID_CLASSNAMEID_2);

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

				qPos.add(classNameId);

				list = (List<CourseTypeRelation>)QueryUtil.list(q,
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
	 * Returns the first course type relation in the ordered set where courseTypeId = &#63; and classNameId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type relation
	 * @throws com.liferay.lms.NoSuchCourseTypeRelationException if a matching course type relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation findByCourseTypeIdClassNameId_First(
		long courseTypeId, long classNameId, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeRelationException, SystemException {
		CourseTypeRelation courseTypeRelation = fetchByCourseTypeIdClassNameId_First(courseTypeId,
				classNameId, orderByComparator);

		if (courseTypeRelation != null) {
			return courseTypeRelation;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeRelationException(msg.toString());
	}

	/**
	 * Returns the first course type relation in the ordered set where courseTypeId = &#63; and classNameId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type relation, or <code>null</code> if a matching course type relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation fetchByCourseTypeIdClassNameId_First(
		long courseTypeId, long classNameId, OrderByComparator orderByComparator)
		throws SystemException {
		List<CourseTypeRelation> list = findByCourseTypeIdClassNameId(courseTypeId,
				classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last course type relation in the ordered set where courseTypeId = &#63; and classNameId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type relation
	 * @throws com.liferay.lms.NoSuchCourseTypeRelationException if a matching course type relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation findByCourseTypeIdClassNameId_Last(
		long courseTypeId, long classNameId, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeRelationException, SystemException {
		CourseTypeRelation courseTypeRelation = fetchByCourseTypeIdClassNameId_Last(courseTypeId,
				classNameId, orderByComparator);

		if (courseTypeRelation != null) {
			return courseTypeRelation;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeRelationException(msg.toString());
	}

	/**
	 * Returns the last course type relation in the ordered set where courseTypeId = &#63; and classNameId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type relation, or <code>null</code> if a matching course type relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation fetchByCourseTypeIdClassNameId_Last(
		long courseTypeId, long classNameId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByCourseTypeIdClassNameId(courseTypeId, classNameId);

		List<CourseTypeRelation> list = findByCourseTypeIdClassNameId(courseTypeId,
				classNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the course type relations before and after the current course type relation in the ordered set where courseTypeId = &#63; and classNameId = &#63;.
	 *
	 * @param courseTypeRelationId the primary key of the current course type relation
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next course type relation
	 * @throws com.liferay.lms.NoSuchCourseTypeRelationException if a course type relation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation[] findByCourseTypeIdClassNameId_PrevAndNext(
		long courseTypeRelationId, long courseTypeId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeRelationException, SystemException {
		CourseTypeRelation courseTypeRelation = findByPrimaryKey(courseTypeRelationId);

		Session session = null;

		try {
			session = openSession();

			CourseTypeRelation[] array = new CourseTypeRelationImpl[3];

			array[0] = getByCourseTypeIdClassNameId_PrevAndNext(session,
					courseTypeRelation, courseTypeId, classNameId,
					orderByComparator, true);

			array[1] = courseTypeRelation;

			array[2] = getByCourseTypeIdClassNameId_PrevAndNext(session,
					courseTypeRelation, courseTypeId, classNameId,
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

	protected CourseTypeRelation getByCourseTypeIdClassNameId_PrevAndNext(
		Session session, CourseTypeRelation courseTypeRelation,
		long courseTypeId, long classNameId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COURSETYPERELATION_WHERE);

		query.append(_FINDER_COLUMN_COURSETYPEIDCLASSNAMEID_COURSETYPEID_2);

		query.append(_FINDER_COLUMN_COURSETYPEIDCLASSNAMEID_CLASSNAMEID_2);

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

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(courseTypeRelation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CourseTypeRelation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the course type relation where courseTypeId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.lms.NoSuchCourseTypeRelationException} if it could not be found.
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching course type relation
	 * @throws com.liferay.lms.NoSuchCourseTypeRelationException if a matching course type relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation findByCourseTypeIdClassNameIdClassPK(
		long courseTypeId, long classNameId, long classPK)
		throws NoSuchCourseTypeRelationException, SystemException {
		CourseTypeRelation courseTypeRelation = fetchByCourseTypeIdClassNameIdClassPK(courseTypeId,
				classNameId, classPK);

		if (courseTypeRelation == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("courseTypeId=");
			msg.append(courseTypeId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCourseTypeRelationException(msg.toString());
		}

		return courseTypeRelation;
	}

	/**
	 * Returns the course type relation where courseTypeId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching course type relation, or <code>null</code> if a matching course type relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation fetchByCourseTypeIdClassNameIdClassPK(
		long courseTypeId, long classNameId, long classPK)
		throws SystemException {
		return fetchByCourseTypeIdClassNameIdClassPK(courseTypeId, classNameId,
			classPK, true);
	}

	/**
	 * Returns the course type relation where courseTypeId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching course type relation, or <code>null</code> if a matching course type relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation fetchByCourseTypeIdClassNameIdClassPK(
		long courseTypeId, long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeId, classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_COURSETYPEIDCLASSNAMEIDCLASSPK,
					finderArgs, this);
		}

		if (result instanceof CourseTypeRelation) {
			CourseTypeRelation courseTypeRelation = (CourseTypeRelation)result;

			if ((courseTypeId != courseTypeRelation.getCourseTypeId()) ||
					(classNameId != courseTypeRelation.getClassNameId()) ||
					(classPK != courseTypeRelation.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COURSETYPERELATION_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPEIDCLASSNAMEIDCLASSPK_COURSETYPEID_2);

			query.append(_FINDER_COLUMN_COURSETYPEIDCLASSNAMEIDCLASSPK_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_COURSETYPEIDCLASSNAMEIDCLASSPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<CourseTypeRelation> list = q.list();

				result = list;

				CourseTypeRelation courseTypeRelation = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPEIDCLASSNAMEIDCLASSPK,
						finderArgs, list);
				}
				else {
					courseTypeRelation = list.get(0);

					cacheResult(courseTypeRelation);

					if ((courseTypeRelation.getCourseTypeId() != courseTypeId) ||
							(courseTypeRelation.getClassNameId() != classNameId) ||
							(courseTypeRelation.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPEIDCLASSNAMEIDCLASSPK,
							finderArgs, courseTypeRelation);
					}
				}

				return courseTypeRelation;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPEIDCLASSNAMEIDCLASSPK,
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
				return (CourseTypeRelation)result;
			}
		}
	}

	/**
	 * Returns all the course type relations.
	 *
	 * @return the course type relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeRelation> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course type relations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course type relations
	 * @param end the upper bound of the range of course type relations (not inclusive)
	 * @return the range of course type relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeRelation> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the course type relations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course type relations
	 * @param end the upper bound of the range of course type relations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of course type relations
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeRelation> findAll(int start, int end,
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

		List<CourseTypeRelation> list = (List<CourseTypeRelation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_COURSETYPERELATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COURSETYPERELATION;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<CourseTypeRelation>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<CourseTypeRelation>)QueryUtil.list(q,
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
	 * Removes all the course type relations where courseTypeId = &#63; from the database.
	 *
	 * @param courseTypeId the course type ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCourseTypeId(long courseTypeId)
		throws SystemException {
		for (CourseTypeRelation courseTypeRelation : findByCourseTypeId(
				courseTypeId)) {
			remove(courseTypeRelation);
		}
	}

	/**
	 * Removes all the course type relations where courseTypeId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCourseTypeIdClassNameId(long courseTypeId,
		long classNameId) throws SystemException {
		for (CourseTypeRelation courseTypeRelation : findByCourseTypeIdClassNameId(
				courseTypeId, classNameId)) {
			remove(courseTypeRelation);
		}
	}

	/**
	 * Removes the course type relation where courseTypeId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the course type relation that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeRelation removeByCourseTypeIdClassNameIdClassPK(
		long courseTypeId, long classNameId, long classPK)
		throws NoSuchCourseTypeRelationException, SystemException {
		CourseTypeRelation courseTypeRelation = findByCourseTypeIdClassNameIdClassPK(courseTypeId,
				classNameId, classPK);

		return remove(courseTypeRelation);
	}

	/**
	 * Removes all the course type relations from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (CourseTypeRelation courseTypeRelation : findAll()) {
			remove(courseTypeRelation);
		}
	}

	/**
	 * Returns the number of course type relations where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @return the number of matching course type relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseTypeId(long courseTypeId) throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COURSETYPERELATION_WHERE);

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
	 * Returns the number of course type relations where courseTypeId = &#63; and classNameId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @return the number of matching course type relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseTypeIdClassNameId(long courseTypeId,
		long classNameId) throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSETYPEIDCLASSNAMEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COURSETYPERELATION_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPEIDCLASSNAMEID_COURSETYPEID_2);

			query.append(_FINDER_COLUMN_COURSETYPEIDCLASSNAMEID_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COURSETYPEIDCLASSNAMEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course type relations where courseTypeId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching course type relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseTypeIdClassNameIdClassPK(long courseTypeId,
		long classNameId, long classPK) throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeId, classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSETYPEIDCLASSNAMEIDCLASSPK,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_COURSETYPERELATION_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPEIDCLASSNAMEIDCLASSPK_COURSETYPEID_2);

			query.append(_FINDER_COLUMN_COURSETYPEIDCLASSNAMEIDCLASSPK_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_COURSETYPEIDCLASSNAMEIDCLASSPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeId);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COURSETYPEIDCLASSNAMEIDCLASSPK,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course type relations.
	 *
	 * @return the number of course type relations
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COURSETYPERELATION);

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
	 * Initializes the course type relation persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.CourseTypeRelation")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CourseTypeRelation>> listenersList = new ArrayList<ModelListener<CourseTypeRelation>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CourseTypeRelation>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(CourseTypeRelationImpl.class.getName());
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
	private static final String _SQL_SELECT_COURSETYPERELATION = "SELECT courseTypeRelation FROM CourseTypeRelation courseTypeRelation";
	private static final String _SQL_SELECT_COURSETYPERELATION_WHERE = "SELECT courseTypeRelation FROM CourseTypeRelation courseTypeRelation WHERE ";
	private static final String _SQL_COUNT_COURSETYPERELATION = "SELECT COUNT(courseTypeRelation) FROM CourseTypeRelation courseTypeRelation";
	private static final String _SQL_COUNT_COURSETYPERELATION_WHERE = "SELECT COUNT(courseTypeRelation) FROM CourseTypeRelation courseTypeRelation WHERE ";
	private static final String _FINDER_COLUMN_COURSETYPEID_COURSETYPEID_2 = "courseTypeRelation.courseTypeId = ?";
	private static final String _FINDER_COLUMN_COURSETYPEIDCLASSNAMEID_COURSETYPEID_2 =
		"courseTypeRelation.courseTypeId = ? AND ";
	private static final String _FINDER_COLUMN_COURSETYPEIDCLASSNAMEID_CLASSNAMEID_2 =
		"courseTypeRelation.classNameId = ?";
	private static final String _FINDER_COLUMN_COURSETYPEIDCLASSNAMEIDCLASSPK_COURSETYPEID_2 =
		"courseTypeRelation.courseTypeId = ? AND ";
	private static final String _FINDER_COLUMN_COURSETYPEIDCLASSNAMEIDCLASSPK_CLASSNAMEID_2 =
		"courseTypeRelation.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_COURSETYPEIDCLASSNAMEIDCLASSPK_CLASSPK_2 =
		"courseTypeRelation.classPK = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "courseTypeRelation.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CourseTypeRelation exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CourseTypeRelation exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(CourseTypeRelationPersistenceImpl.class);
	private static CourseTypeRelation _nullCourseTypeRelation = new CourseTypeRelationImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<CourseTypeRelation> toCacheModel() {
				return _nullCourseTypeRelationCacheModel;
			}
		};

	private static CacheModel<CourseTypeRelation> _nullCourseTypeRelationCacheModel =
		new CacheModel<CourseTypeRelation>() {
			public CourseTypeRelation toEntityModel() {
				return _nullCourseTypeRelation;
			}
		};
}