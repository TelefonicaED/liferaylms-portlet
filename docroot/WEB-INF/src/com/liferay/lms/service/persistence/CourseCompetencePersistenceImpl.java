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

import com.liferay.lms.NoSuchCourseCompetenceException;
import com.liferay.lms.model.CourseCompetence;
import com.liferay.lms.model.impl.CourseCompetenceImpl;
import com.liferay.lms.model.impl.CourseCompetenceModelImpl;

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
 * The persistence implementation for the course competence service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CourseCompetencePersistence
 * @see CourseCompetenceUtil
 * @generated
 */
public class CourseCompetencePersistenceImpl extends BasePersistenceImpl<CourseCompetence>
	implements CourseCompetencePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CourseCompetenceUtil} to access the course competence persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CourseCompetenceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceModelImpl.FINDER_CACHE_ENABLED,
			CourseCompetenceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceModelImpl.FINDER_CACHE_ENABLED,
			CourseCompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CourseCompetenceModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COURSEID = new FinderPath(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceModelImpl.FINDER_CACHE_ENABLED,
			CourseCompetenceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBycourseId",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSEID =
		new FinderPath(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceModelImpl.FINDER_CACHE_ENABLED,
			CourseCompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBycourseId",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			CourseCompetenceModelImpl.COURSEID_COLUMN_BITMASK |
			CourseCompetenceModelImpl.CONDITION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSEID = new FinderPath(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBycourseId",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_COURSECOMPETENCECONDITION =
		new FinderPath(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceModelImpl.FINDER_CACHE_ENABLED,
			CourseCompetenceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCourseCompetenceCondition",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			CourseCompetenceModelImpl.COURSEID_COLUMN_BITMASK |
			CourseCompetenceModelImpl.COMPETENCEID_COLUMN_BITMASK |
			CourseCompetenceModelImpl.CONDITION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSECOMPETENCECONDITION =
		new FinderPath(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCourseCompetenceCondition",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceModelImpl.FINDER_CACHE_ENABLED,
			CourseCompetenceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceModelImpl.FINDER_CACHE_ENABLED,
			CourseCompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the course competence in the entity cache if it is enabled.
	 *
	 * @param courseCompetence the course competence
	 */
	public void cacheResult(CourseCompetence courseCompetence) {
		EntityCacheUtil.putResult(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceImpl.class, courseCompetence.getPrimaryKey(),
			courseCompetence);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSECOMPETENCECONDITION,
			new Object[] {
				Long.valueOf(courseCompetence.getCourseId()),
				Long.valueOf(courseCompetence.getCompetenceId()),
				Boolean.valueOf(courseCompetence.getCondition())
			}, courseCompetence);

		courseCompetence.resetOriginalValues();
	}

	/**
	 * Caches the course competences in the entity cache if it is enabled.
	 *
	 * @param courseCompetences the course competences
	 */
	public void cacheResult(List<CourseCompetence> courseCompetences) {
		for (CourseCompetence courseCompetence : courseCompetences) {
			if (EntityCacheUtil.getResult(
						CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
						CourseCompetenceImpl.class,
						courseCompetence.getPrimaryKey()) == null) {
				cacheResult(courseCompetence);
			}
			else {
				courseCompetence.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all course competences.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CourseCompetenceImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CourseCompetenceImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the course competence.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CourseCompetence courseCompetence) {
		EntityCacheUtil.removeResult(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceImpl.class, courseCompetence.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(courseCompetence);
	}

	@Override
	public void clearCache(List<CourseCompetence> courseCompetences) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CourseCompetence courseCompetence : courseCompetences) {
			EntityCacheUtil.removeResult(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
				CourseCompetenceImpl.class, courseCompetence.getPrimaryKey());

			clearUniqueFindersCache(courseCompetence);
		}
	}

	protected void clearUniqueFindersCache(CourseCompetence courseCompetence) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSECOMPETENCECONDITION,
			new Object[] {
				Long.valueOf(courseCompetence.getCourseId()),
				Long.valueOf(courseCompetence.getCompetenceId()),
				Boolean.valueOf(courseCompetence.getCondition())
			});
	}

	/**
	 * Creates a new course competence with the primary key. Does not add the course competence to the database.
	 *
	 * @param CourcompetenceId the primary key for the new course competence
	 * @return the new course competence
	 */
	public CourseCompetence create(long CourcompetenceId) {
		CourseCompetence courseCompetence = new CourseCompetenceImpl();

		courseCompetence.setNew(true);
		courseCompetence.setPrimaryKey(CourcompetenceId);

		String uuid = PortalUUIDUtil.generate();

		courseCompetence.setUuid(uuid);

		return courseCompetence;
	}

	/**
	 * Removes the course competence with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CourcompetenceId the primary key of the course competence
	 * @return the course competence that was removed
	 * @throws com.liferay.lms.NoSuchCourseCompetenceException if a course competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence remove(long CourcompetenceId)
		throws NoSuchCourseCompetenceException, SystemException {
		return remove(Long.valueOf(CourcompetenceId));
	}

	/**
	 * Removes the course competence with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the course competence
	 * @return the course competence that was removed
	 * @throws com.liferay.lms.NoSuchCourseCompetenceException if a course competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseCompetence remove(Serializable primaryKey)
		throws NoSuchCourseCompetenceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CourseCompetence courseCompetence = (CourseCompetence)session.get(CourseCompetenceImpl.class,
					primaryKey);

			if (courseCompetence == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCourseCompetenceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(courseCompetence);
		}
		catch (NoSuchCourseCompetenceException nsee) {
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
	protected CourseCompetence removeImpl(CourseCompetence courseCompetence)
		throws SystemException {
		courseCompetence = toUnwrappedModel(courseCompetence);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, courseCompetence);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(courseCompetence);

		return courseCompetence;
	}

	@Override
	public CourseCompetence updateImpl(
		com.liferay.lms.model.CourseCompetence courseCompetence, boolean merge)
		throws SystemException {
		courseCompetence = toUnwrappedModel(courseCompetence);

		boolean isNew = courseCompetence.isNew();

		CourseCompetenceModelImpl courseCompetenceModelImpl = (CourseCompetenceModelImpl)courseCompetence;

		if (Validator.isNull(courseCompetence.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			courseCompetence.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, courseCompetence, merge);

			courseCompetence.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !CourseCompetenceModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((courseCompetenceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						courseCompetenceModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { courseCompetenceModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((courseCompetenceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseCompetenceModelImpl.getOriginalCourseId()),
						Boolean.valueOf(courseCompetenceModelImpl.getOriginalCondition())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSEID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSEID,
					args);

				args = new Object[] {
						Long.valueOf(courseCompetenceModelImpl.getCourseId()),
						Boolean.valueOf(courseCompetenceModelImpl.getCondition())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSEID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSEID,
					args);
			}
		}

		EntityCacheUtil.putResult(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CourseCompetenceImpl.class, courseCompetence.getPrimaryKey(),
			courseCompetence);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSECOMPETENCECONDITION,
				new Object[] {
					Long.valueOf(courseCompetence.getCourseId()),
					Long.valueOf(courseCompetence.getCompetenceId()),
					Boolean.valueOf(courseCompetence.getCondition())
				}, courseCompetence);
		}
		else {
			if ((courseCompetenceModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_COURSECOMPETENCECONDITION.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseCompetenceModelImpl.getOriginalCourseId()),
						Long.valueOf(courseCompetenceModelImpl.getOriginalCompetenceId()),
						Boolean.valueOf(courseCompetenceModelImpl.getOriginalCondition())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSECOMPETENCECONDITION,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSECOMPETENCECONDITION,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSECOMPETENCECONDITION,
					new Object[] {
						Long.valueOf(courseCompetence.getCourseId()),
						Long.valueOf(courseCompetence.getCompetenceId()),
						Boolean.valueOf(courseCompetence.getCondition())
					}, courseCompetence);
			}
		}

		return courseCompetence;
	}

	protected CourseCompetence toUnwrappedModel(
		CourseCompetence courseCompetence) {
		if (courseCompetence instanceof CourseCompetenceImpl) {
			return courseCompetence;
		}

		CourseCompetenceImpl courseCompetenceImpl = new CourseCompetenceImpl();

		courseCompetenceImpl.setNew(courseCompetence.isNew());
		courseCompetenceImpl.setPrimaryKey(courseCompetence.getPrimaryKey());

		courseCompetenceImpl.setUuid(courseCompetence.getUuid());
		courseCompetenceImpl.setCourcompetenceId(courseCompetence.getCourcompetenceId());
		courseCompetenceImpl.setCourseId(courseCompetence.getCourseId());
		courseCompetenceImpl.setCompetenceId(courseCompetence.getCompetenceId());
		courseCompetenceImpl.setCondition(courseCompetence.isCondition());

		return courseCompetenceImpl;
	}

	/**
	 * Returns the course competence with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the course competence
	 * @return the course competence
	 * @throws com.liferay.portal.NoSuchModelException if a course competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseCompetence findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course competence with the primary key or throws a {@link com.liferay.lms.NoSuchCourseCompetenceException} if it could not be found.
	 *
	 * @param CourcompetenceId the primary key of the course competence
	 * @return the course competence
	 * @throws com.liferay.lms.NoSuchCourseCompetenceException if a course competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence findByPrimaryKey(long CourcompetenceId)
		throws NoSuchCourseCompetenceException, SystemException {
		CourseCompetence courseCompetence = fetchByPrimaryKey(CourcompetenceId);

		if (courseCompetence == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + CourcompetenceId);
			}

			throw new NoSuchCourseCompetenceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				CourcompetenceId);
		}

		return courseCompetence;
	}

	/**
	 * Returns the course competence with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the course competence
	 * @return the course competence, or <code>null</code> if a course competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseCompetence fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course competence with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CourcompetenceId the primary key of the course competence
	 * @return the course competence, or <code>null</code> if a course competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence fetchByPrimaryKey(long CourcompetenceId)
		throws SystemException {
		CourseCompetence courseCompetence = (CourseCompetence)EntityCacheUtil.getResult(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
				CourseCompetenceImpl.class, CourcompetenceId);

		if (courseCompetence == _nullCourseCompetence) {
			return null;
		}

		if (courseCompetence == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				courseCompetence = (CourseCompetence)session.get(CourseCompetenceImpl.class,
						Long.valueOf(CourcompetenceId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (courseCompetence != null) {
					cacheResult(courseCompetence);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(CourseCompetenceModelImpl.ENTITY_CACHE_ENABLED,
						CourseCompetenceImpl.class, CourcompetenceId,
						_nullCourseCompetence);
				}

				closeSession(session);
			}
		}

		return courseCompetence;
	}

	/**
	 * Returns all the course competences where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching course competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseCompetence> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course competences where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of course competences
	 * @param end the upper bound of the range of course competences (not inclusive)
	 * @return the range of matching course competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseCompetence> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the course competences where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of course competences
	 * @param end the upper bound of the range of course competences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching course competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseCompetence> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<CourseCompetence> list = (List<CourseCompetence>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CourseCompetence courseCompetence : list) {
				if (!Validator.equals(uuid, courseCompetence.getUuid())) {
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

			query.append(_SQL_SELECT_COURSECOMPETENCE_WHERE);

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

				list = (List<CourseCompetence>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first course competence in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course competence
	 * @throws com.liferay.lms.NoSuchCourseCompetenceException if a matching course competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCourseCompetenceException, SystemException {
		CourseCompetence courseCompetence = fetchByUuid_First(uuid,
				orderByComparator);

		if (courseCompetence != null) {
			return courseCompetence;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseCompetenceException(msg.toString());
	}

	/**
	 * Returns the first course competence in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course competence, or <code>null</code> if a matching course competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<CourseCompetence> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last course competence in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course competence
	 * @throws com.liferay.lms.NoSuchCourseCompetenceException if a matching course competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCourseCompetenceException, SystemException {
		CourseCompetence courseCompetence = fetchByUuid_Last(uuid,
				orderByComparator);

		if (courseCompetence != null) {
			return courseCompetence;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseCompetenceException(msg.toString());
	}

	/**
	 * Returns the last course competence in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course competence, or <code>null</code> if a matching course competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<CourseCompetence> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the course competences before and after the current course competence in the ordered set where uuid = &#63;.
	 *
	 * @param CourcompetenceId the primary key of the current course competence
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next course competence
	 * @throws com.liferay.lms.NoSuchCourseCompetenceException if a course competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence[] findByUuid_PrevAndNext(long CourcompetenceId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchCourseCompetenceException, SystemException {
		CourseCompetence courseCompetence = findByPrimaryKey(CourcompetenceId);

		Session session = null;

		try {
			session = openSession();

			CourseCompetence[] array = new CourseCompetenceImpl[3];

			array[0] = getByUuid_PrevAndNext(session, courseCompetence, uuid,
					orderByComparator, true);

			array[1] = courseCompetence;

			array[2] = getByUuid_PrevAndNext(session, courseCompetence, uuid,
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

	protected CourseCompetence getByUuid_PrevAndNext(Session session,
		CourseCompetence courseCompetence, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COURSECOMPETENCE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(courseCompetence);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CourseCompetence> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the course competences where courseId = &#63; and condition = &#63;.
	 *
	 * @param courseId the course ID
	 * @param condition the condition
	 * @return the matching course competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseCompetence> findBycourseId(long courseId,
		boolean condition) throws SystemException {
		return findBycourseId(courseId, condition, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course competences where courseId = &#63; and condition = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseId the course ID
	 * @param condition the condition
	 * @param start the lower bound of the range of course competences
	 * @param end the upper bound of the range of course competences (not inclusive)
	 * @return the range of matching course competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseCompetence> findBycourseId(long courseId,
		boolean condition, int start, int end) throws SystemException {
		return findBycourseId(courseId, condition, start, end, null);
	}

	/**
	 * Returns an ordered range of all the course competences where courseId = &#63; and condition = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseId the course ID
	 * @param condition the condition
	 * @param start the lower bound of the range of course competences
	 * @param end the upper bound of the range of course competences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching course competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseCompetence> findBycourseId(long courseId,
		boolean condition, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSEID;
			finderArgs = new Object[] { courseId, condition };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COURSEID;
			finderArgs = new Object[] {
					courseId, condition,
					
					start, end, orderByComparator
				};
		}

		List<CourseCompetence> list = (List<CourseCompetence>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CourseCompetence courseCompetence : list) {
				if ((courseId != courseCompetence.getCourseId()) ||
						(condition != courseCompetence.getCondition())) {
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

			query.append(_SQL_SELECT_COURSECOMPETENCE_WHERE);

			query.append(_FINDER_COLUMN_COURSEID_COURSEID_2);

			query.append(_FINDER_COLUMN_COURSEID_CONDITION_2);

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

				qPos.add(condition);

				list = (List<CourseCompetence>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first course competence in the ordered set where courseId = &#63; and condition = &#63;.
	 *
	 * @param courseId the course ID
	 * @param condition the condition
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course competence
	 * @throws com.liferay.lms.NoSuchCourseCompetenceException if a matching course competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence findBycourseId_First(long courseId,
		boolean condition, OrderByComparator orderByComparator)
		throws NoSuchCourseCompetenceException, SystemException {
		CourseCompetence courseCompetence = fetchBycourseId_First(courseId,
				condition, orderByComparator);

		if (courseCompetence != null) {
			return courseCompetence;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseId=");
		msg.append(courseId);

		msg.append(", condition=");
		msg.append(condition);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseCompetenceException(msg.toString());
	}

	/**
	 * Returns the first course competence in the ordered set where courseId = &#63; and condition = &#63;.
	 *
	 * @param courseId the course ID
	 * @param condition the condition
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course competence, or <code>null</code> if a matching course competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence fetchBycourseId_First(long courseId,
		boolean condition, OrderByComparator orderByComparator)
		throws SystemException {
		List<CourseCompetence> list = findBycourseId(courseId, condition, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last course competence in the ordered set where courseId = &#63; and condition = &#63;.
	 *
	 * @param courseId the course ID
	 * @param condition the condition
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course competence
	 * @throws com.liferay.lms.NoSuchCourseCompetenceException if a matching course competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence findBycourseId_Last(long courseId,
		boolean condition, OrderByComparator orderByComparator)
		throws NoSuchCourseCompetenceException, SystemException {
		CourseCompetence courseCompetence = fetchBycourseId_Last(courseId,
				condition, orderByComparator);

		if (courseCompetence != null) {
			return courseCompetence;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseId=");
		msg.append(courseId);

		msg.append(", condition=");
		msg.append(condition);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseCompetenceException(msg.toString());
	}

	/**
	 * Returns the last course competence in the ordered set where courseId = &#63; and condition = &#63;.
	 *
	 * @param courseId the course ID
	 * @param condition the condition
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course competence, or <code>null</code> if a matching course competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence fetchBycourseId_Last(long courseId,
		boolean condition, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countBycourseId(courseId, condition);

		List<CourseCompetence> list = findBycourseId(courseId, condition,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the course competences before and after the current course competence in the ordered set where courseId = &#63; and condition = &#63;.
	 *
	 * @param CourcompetenceId the primary key of the current course competence
	 * @param courseId the course ID
	 * @param condition the condition
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next course competence
	 * @throws com.liferay.lms.NoSuchCourseCompetenceException if a course competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence[] findBycourseId_PrevAndNext(
		long CourcompetenceId, long courseId, boolean condition,
		OrderByComparator orderByComparator)
		throws NoSuchCourseCompetenceException, SystemException {
		CourseCompetence courseCompetence = findByPrimaryKey(CourcompetenceId);

		Session session = null;

		try {
			session = openSession();

			CourseCompetence[] array = new CourseCompetenceImpl[3];

			array[0] = getBycourseId_PrevAndNext(session, courseCompetence,
					courseId, condition, orderByComparator, true);

			array[1] = courseCompetence;

			array[2] = getBycourseId_PrevAndNext(session, courseCompetence,
					courseId, condition, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CourseCompetence getBycourseId_PrevAndNext(Session session,
		CourseCompetence courseCompetence, long courseId, boolean condition,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COURSECOMPETENCE_WHERE);

		query.append(_FINDER_COLUMN_COURSEID_COURSEID_2);

		query.append(_FINDER_COLUMN_COURSEID_CONDITION_2);

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

		qPos.add(condition);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(courseCompetence);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CourseCompetence> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the course competence where courseId = &#63; and competenceId = &#63; and condition = &#63; or throws a {@link com.liferay.lms.NoSuchCourseCompetenceException} if it could not be found.
	 *
	 * @param courseId the course ID
	 * @param competenceId the competence ID
	 * @param condition the condition
	 * @return the matching course competence
	 * @throws com.liferay.lms.NoSuchCourseCompetenceException if a matching course competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence findByCourseCompetenceCondition(long courseId,
		long competenceId, boolean condition)
		throws NoSuchCourseCompetenceException, SystemException {
		CourseCompetence courseCompetence = fetchByCourseCompetenceCondition(courseId,
				competenceId, condition);

		if (courseCompetence == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("courseId=");
			msg.append(courseId);

			msg.append(", competenceId=");
			msg.append(competenceId);

			msg.append(", condition=");
			msg.append(condition);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCourseCompetenceException(msg.toString());
		}

		return courseCompetence;
	}

	/**
	 * Returns the course competence where courseId = &#63; and competenceId = &#63; and condition = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param courseId the course ID
	 * @param competenceId the competence ID
	 * @param condition the condition
	 * @return the matching course competence, or <code>null</code> if a matching course competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence fetchByCourseCompetenceCondition(long courseId,
		long competenceId, boolean condition) throws SystemException {
		return fetchByCourseCompetenceCondition(courseId, competenceId,
			condition, true);
	}

	/**
	 * Returns the course competence where courseId = &#63; and competenceId = &#63; and condition = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param courseId the course ID
	 * @param competenceId the competence ID
	 * @param condition the condition
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching course competence, or <code>null</code> if a matching course competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence fetchByCourseCompetenceCondition(long courseId,
		long competenceId, boolean condition, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { courseId, competenceId, condition };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_COURSECOMPETENCECONDITION,
					finderArgs, this);
		}

		if (result instanceof CourseCompetence) {
			CourseCompetence courseCompetence = (CourseCompetence)result;

			if ((courseId != courseCompetence.getCourseId()) ||
					(competenceId != courseCompetence.getCompetenceId()) ||
					(condition != courseCompetence.getCondition())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COURSECOMPETENCE_WHERE);

			query.append(_FINDER_COLUMN_COURSECOMPETENCECONDITION_COURSEID_2);

			query.append(_FINDER_COLUMN_COURSECOMPETENCECONDITION_COMPETENCEID_2);

			query.append(_FINDER_COLUMN_COURSECOMPETENCECONDITION_CONDITION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseId);

				qPos.add(competenceId);

				qPos.add(condition);

				List<CourseCompetence> list = q.list();

				result = list;

				CourseCompetence courseCompetence = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSECOMPETENCECONDITION,
						finderArgs, list);
				}
				else {
					courseCompetence = list.get(0);

					cacheResult(courseCompetence);

					if ((courseCompetence.getCourseId() != courseId) ||
							(courseCompetence.getCompetenceId() != competenceId) ||
							(courseCompetence.getCondition() != condition)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSECOMPETENCECONDITION,
							finderArgs, courseCompetence);
					}
				}

				return courseCompetence;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSECOMPETENCECONDITION,
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
				return (CourseCompetence)result;
			}
		}
	}

	/**
	 * Returns all the course competences.
	 *
	 * @return the course competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseCompetence> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course competences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course competences
	 * @param end the upper bound of the range of course competences (not inclusive)
	 * @return the range of course competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseCompetence> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the course competences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course competences
	 * @param end the upper bound of the range of course competences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of course competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseCompetence> findAll(int start, int end,
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

		List<CourseCompetence> list = (List<CourseCompetence>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_COURSECOMPETENCE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COURSECOMPETENCE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<CourseCompetence>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<CourseCompetence>)QueryUtil.list(q,
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
	 * Removes all the course competences where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (CourseCompetence courseCompetence : findByUuid(uuid)) {
			remove(courseCompetence);
		}
	}

	/**
	 * Removes all the course competences where courseId = &#63; and condition = &#63; from the database.
	 *
	 * @param courseId the course ID
	 * @param condition the condition
	 * @throws SystemException if a system exception occurred
	 */
	public void removeBycourseId(long courseId, boolean condition)
		throws SystemException {
		for (CourseCompetence courseCompetence : findBycourseId(courseId,
				condition)) {
			remove(courseCompetence);
		}
	}

	/**
	 * Removes the course competence where courseId = &#63; and competenceId = &#63; and condition = &#63; from the database.
	 *
	 * @param courseId the course ID
	 * @param competenceId the competence ID
	 * @param condition the condition
	 * @return the course competence that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public CourseCompetence removeByCourseCompetenceCondition(long courseId,
		long competenceId, boolean condition)
		throws NoSuchCourseCompetenceException, SystemException {
		CourseCompetence courseCompetence = findByCourseCompetenceCondition(courseId,
				competenceId, condition);

		return remove(courseCompetence);
	}

	/**
	 * Removes all the course competences from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (CourseCompetence courseCompetence : findAll()) {
			remove(courseCompetence);
		}
	}

	/**
	 * Returns the number of course competences where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching course competences
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COURSECOMPETENCE_WHERE);

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
	 * Returns the number of course competences where courseId = &#63; and condition = &#63;.
	 *
	 * @param courseId the course ID
	 * @param condition the condition
	 * @return the number of matching course competences
	 * @throws SystemException if a system exception occurred
	 */
	public int countBycourseId(long courseId, boolean condition)
		throws SystemException {
		Object[] finderArgs = new Object[] { courseId, condition };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COURSECOMPETENCE_WHERE);

			query.append(_FINDER_COLUMN_COURSEID_COURSEID_2);

			query.append(_FINDER_COLUMN_COURSEID_CONDITION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseId);

				qPos.add(condition);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COURSEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course competences where courseId = &#63; and competenceId = &#63; and condition = &#63;.
	 *
	 * @param courseId the course ID
	 * @param competenceId the competence ID
	 * @param condition the condition
	 * @return the number of matching course competences
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseCompetenceCondition(long courseId,
		long competenceId, boolean condition) throws SystemException {
		Object[] finderArgs = new Object[] { courseId, competenceId, condition };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSECOMPETENCECONDITION,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_COURSECOMPETENCE_WHERE);

			query.append(_FINDER_COLUMN_COURSECOMPETENCECONDITION_COURSEID_2);

			query.append(_FINDER_COLUMN_COURSECOMPETENCECONDITION_COMPETENCEID_2);

			query.append(_FINDER_COLUMN_COURSECOMPETENCECONDITION_CONDITION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseId);

				qPos.add(competenceId);

				qPos.add(condition);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COURSECOMPETENCECONDITION,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course competences.
	 *
	 * @return the number of course competences
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COURSECOMPETENCE);

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
	 * Initializes the course competence persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.CourseCompetence")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CourseCompetence>> listenersList = new ArrayList<ModelListener<CourseCompetence>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CourseCompetence>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(CourseCompetenceImpl.class.getName());
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
	private static final String _SQL_SELECT_COURSECOMPETENCE = "SELECT courseCompetence FROM CourseCompetence courseCompetence";
	private static final String _SQL_SELECT_COURSECOMPETENCE_WHERE = "SELECT courseCompetence FROM CourseCompetence courseCompetence WHERE ";
	private static final String _SQL_COUNT_COURSECOMPETENCE = "SELECT COUNT(courseCompetence) FROM CourseCompetence courseCompetence";
	private static final String _SQL_COUNT_COURSECOMPETENCE_WHERE = "SELECT COUNT(courseCompetence) FROM CourseCompetence courseCompetence WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "courseCompetence.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "courseCompetence.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(courseCompetence.uuid IS NULL OR courseCompetence.uuid = ?)";
	private static final String _FINDER_COLUMN_COURSEID_COURSEID_2 = "courseCompetence.courseId = ? AND ";
	private static final String _FINDER_COLUMN_COURSEID_CONDITION_2 = "courseCompetence.condition = ?";
	private static final String _FINDER_COLUMN_COURSECOMPETENCECONDITION_COURSEID_2 =
		"courseCompetence.courseId = ? AND ";
	private static final String _FINDER_COLUMN_COURSECOMPETENCECONDITION_COMPETENCEID_2 =
		"courseCompetence.competenceId = ? AND ";
	private static final String _FINDER_COLUMN_COURSECOMPETENCECONDITION_CONDITION_2 =
		"courseCompetence.condition = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "courseCompetence.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CourseCompetence exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CourseCompetence exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(CourseCompetencePersistenceImpl.class);
	private static CourseCompetence _nullCourseCompetence = new CourseCompetenceImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<CourseCompetence> toCacheModel() {
				return _nullCourseCompetenceCacheModel;
			}
		};

	private static CacheModel<CourseCompetence> _nullCourseCompetenceCacheModel = new CacheModel<CourseCompetence>() {
			public CourseCompetence toEntityModel() {
				return _nullCourseCompetence;
			}
		};
}