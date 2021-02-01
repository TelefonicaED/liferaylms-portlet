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

import com.liferay.lms.NoSuchCourseTypeException;
import com.liferay.lms.model.CourseType;
import com.liferay.lms.model.impl.CourseTypeImpl;
import com.liferay.lms.model.impl.CourseTypeModelImpl;

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
 * The persistence implementation for the course type service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CourseTypePersistence
 * @see CourseTypeUtil
 * @generated
 */
public class CourseTypePersistenceImpl extends BasePersistenceImpl<CourseType>
	implements CourseTypePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CourseTypeUtil} to access the course type persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CourseTypeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeModelImpl.FINDER_CACHE_ENABLED, CourseTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeModelImpl.FINDER_CACHE_ENABLED, CourseTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CourseTypeModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYIDACTIVE =
		new FinderPath(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeModelImpl.FINDER_CACHE_ENABLED, CourseTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyIdActive",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDACTIVE =
		new FinderPath(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeModelImpl.FINDER_CACHE_ENABLED, CourseTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyIdActive",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			CourseTypeModelImpl.COMPANYID_COLUMN_BITMASK |
			CourseTypeModelImpl.ACTIVE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYIDACTIVE = new FinderPath(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyIdActive",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDACTIVE =
		new FinderPath(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeModelImpl.FINDER_CACHE_ENABLED, CourseTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyIdClassNameIdActive",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDACTIVE =
		new FinderPath(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeModelImpl.FINDER_CACHE_ENABLED, CourseTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyIdClassNameIdActive",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			CourseTypeModelImpl.COMPANYID_COLUMN_BITMASK |
			CourseTypeModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CourseTypeModelImpl.ACTIVE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYIDCLASSNAMEIDACTIVE =
		new FinderPath(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyIdClassNameIdActive",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeModelImpl.FINDER_CACHE_ENABLED, CourseTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeModelImpl.FINDER_CACHE_ENABLED, CourseTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the course type in the entity cache if it is enabled.
	 *
	 * @param courseType the course type
	 */
	public void cacheResult(CourseType courseType) {
		EntityCacheUtil.putResult(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeImpl.class, courseType.getPrimaryKey(), courseType);

		courseType.resetOriginalValues();
	}

	/**
	 * Caches the course types in the entity cache if it is enabled.
	 *
	 * @param courseTypes the course types
	 */
	public void cacheResult(List<CourseType> courseTypes) {
		for (CourseType courseType : courseTypes) {
			if (EntityCacheUtil.getResult(
						CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeImpl.class, courseType.getPrimaryKey()) == null) {
				cacheResult(courseType);
			}
			else {
				courseType.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all course types.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CourseTypeImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CourseTypeImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the course type.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CourseType courseType) {
		EntityCacheUtil.removeResult(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeImpl.class, courseType.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CourseType> courseTypes) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CourseType courseType : courseTypes) {
			EntityCacheUtil.removeResult(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeImpl.class, courseType.getPrimaryKey());
		}
	}

	/**
	 * Creates a new course type with the primary key. Does not add the course type to the database.
	 *
	 * @param courseTypeId the primary key for the new course type
	 * @return the new course type
	 */
	public CourseType create(long courseTypeId) {
		CourseType courseType = new CourseTypeImpl();

		courseType.setNew(true);
		courseType.setPrimaryKey(courseTypeId);

		return courseType;
	}

	/**
	 * Removes the course type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param courseTypeId the primary key of the course type
	 * @return the course type that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeException if a course type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType remove(long courseTypeId)
		throws NoSuchCourseTypeException, SystemException {
		return remove(Long.valueOf(courseTypeId));
	}

	/**
	 * Removes the course type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the course type
	 * @return the course type that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeException if a course type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseType remove(Serializable primaryKey)
		throws NoSuchCourseTypeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CourseType courseType = (CourseType)session.get(CourseTypeImpl.class,
					primaryKey);

			if (courseType == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCourseTypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(courseType);
		}
		catch (NoSuchCourseTypeException nsee) {
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
	protected CourseType removeImpl(CourseType courseType)
		throws SystemException {
		courseType = toUnwrappedModel(courseType);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, courseType);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(courseType);

		return courseType;
	}

	@Override
	public CourseType updateImpl(com.liferay.lms.model.CourseType courseType,
		boolean merge) throws SystemException {
		courseType = toUnwrappedModel(courseType);

		boolean isNew = courseType.isNew();

		CourseTypeModelImpl courseTypeModelImpl = (CourseTypeModelImpl)courseType;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, courseType, merge);

			courseType.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !CourseTypeModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((courseTypeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(courseTypeModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((courseTypeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDACTIVE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeModelImpl.getOriginalCompanyId()),
						Boolean.valueOf(courseTypeModelImpl.getOriginalActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYIDACTIVE,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDACTIVE,
					args);

				args = new Object[] {
						Long.valueOf(courseTypeModelImpl.getCompanyId()),
						Boolean.valueOf(courseTypeModelImpl.getActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYIDACTIVE,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDACTIVE,
					args);
			}

			if ((courseTypeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDACTIVE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeModelImpl.getOriginalCompanyId()),
						Long.valueOf(courseTypeModelImpl.getOriginalClassNameId()),
						Boolean.valueOf(courseTypeModelImpl.getOriginalActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYIDCLASSNAMEIDACTIVE,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDACTIVE,
					args);

				args = new Object[] {
						Long.valueOf(courseTypeModelImpl.getCompanyId()),
						Long.valueOf(courseTypeModelImpl.getClassNameId()),
						Boolean.valueOf(courseTypeModelImpl.getActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYIDCLASSNAMEIDACTIVE,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDACTIVE,
					args);
			}
		}

		EntityCacheUtil.putResult(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeImpl.class, courseType.getPrimaryKey(), courseType);

		return courseType;
	}

	protected CourseType toUnwrappedModel(CourseType courseType) {
		if (courseType instanceof CourseTypeImpl) {
			return courseType;
		}

		CourseTypeImpl courseTypeImpl = new CourseTypeImpl();

		courseTypeImpl.setNew(courseType.isNew());
		courseTypeImpl.setPrimaryKey(courseType.getPrimaryKey());

		courseTypeImpl.setCourseTypeId(courseType.getCourseTypeId());
		courseTypeImpl.setCompanyId(courseType.getCompanyId());
		courseTypeImpl.setUserId(courseType.getUserId());
		courseTypeImpl.setGroupId(courseType.getGroupId());
		courseTypeImpl.setUserName(courseType.getUserName());
		courseTypeImpl.setCreateDate(courseType.getCreateDate());
		courseTypeImpl.setModifiedDate(courseType.getModifiedDate());
		courseTypeImpl.setName(courseType.getName());
		courseTypeImpl.setDescription(courseType.getDescription());
		courseTypeImpl.setIconId(courseType.getIconId());
		courseTypeImpl.setClassNameId(courseType.getClassNameId());
		courseTypeImpl.setActive(courseType.isActive());

		return courseTypeImpl;
	}

	/**
	 * Returns the course type with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type
	 * @return the course type
	 * @throws com.liferay.portal.NoSuchModelException if a course type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseType findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type with the primary key or throws a {@link com.liferay.lms.NoSuchCourseTypeException} if it could not be found.
	 *
	 * @param courseTypeId the primary key of the course type
	 * @return the course type
	 * @throws com.liferay.lms.NoSuchCourseTypeException if a course type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType findByPrimaryKey(long courseTypeId)
		throws NoSuchCourseTypeException, SystemException {
		CourseType courseType = fetchByPrimaryKey(courseTypeId);

		if (courseType == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + courseTypeId);
			}

			throw new NoSuchCourseTypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				courseTypeId);
		}

		return courseType;
	}

	/**
	 * Returns the course type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type
	 * @return the course type, or <code>null</code> if a course type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseType fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param courseTypeId the primary key of the course type
	 * @return the course type, or <code>null</code> if a course type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType fetchByPrimaryKey(long courseTypeId)
		throws SystemException {
		CourseType courseType = (CourseType)EntityCacheUtil.getResult(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeImpl.class, courseTypeId);

		if (courseType == _nullCourseType) {
			return null;
		}

		if (courseType == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				courseType = (CourseType)session.get(CourseTypeImpl.class,
						Long.valueOf(courseTypeId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (courseType != null) {
					cacheResult(courseType);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(CourseTypeModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeImpl.class, courseTypeId, _nullCourseType);
				}

				closeSession(session);
			}
		}

		return courseType;
	}

	/**
	 * Returns all the course types where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching course types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseType> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the course types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of course types
	 * @param end the upper bound of the range of course types (not inclusive)
	 * @return the range of matching course types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseType> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the course types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of course types
	 * @param end the upper bound of the range of course types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching course types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseType> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<CourseType> list = (List<CourseType>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CourseType courseType : list) {
				if ((companyId != courseType.getCompanyId())) {
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

			query.append(_SQL_SELECT_COURSETYPE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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

				qPos.add(companyId);

				list = (List<CourseType>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first course type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type
	 * @throws com.liferay.lms.NoSuchCourseTypeException if a matching course type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeException, SystemException {
		CourseType courseType = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (courseType != null) {
			return courseType;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeException(msg.toString());
	}

	/**
	 * Returns the first course type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type, or <code>null</code> if a matching course type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<CourseType> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last course type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type
	 * @throws com.liferay.lms.NoSuchCourseTypeException if a matching course type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeException, SystemException {
		CourseType courseType = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (courseType != null) {
			return courseType;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeException(msg.toString());
	}

	/**
	 * Returns the last course type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type, or <code>null</code> if a matching course type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<CourseType> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the course types before and after the current course type in the ordered set where companyId = &#63;.
	 *
	 * @param courseTypeId the primary key of the current course type
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next course type
	 * @throws com.liferay.lms.NoSuchCourseTypeException if a course type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType[] findByCompanyId_PrevAndNext(long courseTypeId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeException, SystemException {
		CourseType courseType = findByPrimaryKey(courseTypeId);

		Session session = null;

		try {
			session = openSession();

			CourseType[] array = new CourseTypeImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, courseType,
					companyId, orderByComparator, true);

			array[1] = courseType;

			array[2] = getByCompanyId_PrevAndNext(session, courseType,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CourseType getByCompanyId_PrevAndNext(Session session,
		CourseType courseType, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COURSETYPE_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(courseType);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CourseType> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the course types where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching course types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseType> findByCompanyIdActive(long companyId, boolean active)
		throws SystemException {
		return findByCompanyIdActive(companyId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course types where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of course types
	 * @param end the upper bound of the range of course types (not inclusive)
	 * @return the range of matching course types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseType> findByCompanyIdActive(long companyId,
		boolean active, int start, int end) throws SystemException {
		return findByCompanyIdActive(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the course types where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of course types
	 * @param end the upper bound of the range of course types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching course types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseType> findByCompanyIdActive(long companyId,
		boolean active, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDACTIVE;
			finderArgs = new Object[] { companyId, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYIDACTIVE;
			finderArgs = new Object[] {
					companyId, active,
					
					start, end, orderByComparator
				};
		}

		List<CourseType> list = (List<CourseType>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CourseType courseType : list) {
				if ((companyId != courseType.getCompanyId()) ||
						(active != courseType.getActive())) {
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

			query.append(_SQL_SELECT_COURSETYPE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYIDACTIVE_COMPANYID_2);

			query.append(_FINDER_COLUMN_COMPANYIDACTIVE_ACTIVE_2);

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

				qPos.add(companyId);

				qPos.add(active);

				list = (List<CourseType>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first course type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type
	 * @throws com.liferay.lms.NoSuchCourseTypeException if a matching course type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType findByCompanyIdActive_First(long companyId,
		boolean active, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeException, SystemException {
		CourseType courseType = fetchByCompanyIdActive_First(companyId, active,
				orderByComparator);

		if (courseType != null) {
			return courseType;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeException(msg.toString());
	}

	/**
	 * Returns the first course type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type, or <code>null</code> if a matching course type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType fetchByCompanyIdActive_First(long companyId,
		boolean active, OrderByComparator orderByComparator)
		throws SystemException {
		List<CourseType> list = findByCompanyIdActive(companyId, active, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last course type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type
	 * @throws com.liferay.lms.NoSuchCourseTypeException if a matching course type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType findByCompanyIdActive_Last(long companyId,
		boolean active, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeException, SystemException {
		CourseType courseType = fetchByCompanyIdActive_Last(companyId, active,
				orderByComparator);

		if (courseType != null) {
			return courseType;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeException(msg.toString());
	}

	/**
	 * Returns the last course type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type, or <code>null</code> if a matching course type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType fetchByCompanyIdActive_Last(long companyId,
		boolean active, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByCompanyIdActive(companyId, active);

		List<CourseType> list = findByCompanyIdActive(companyId, active,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the course types before and after the current course type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param courseTypeId the primary key of the current course type
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next course type
	 * @throws com.liferay.lms.NoSuchCourseTypeException if a course type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType[] findByCompanyIdActive_PrevAndNext(long courseTypeId,
		long companyId, boolean active, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeException, SystemException {
		CourseType courseType = findByPrimaryKey(courseTypeId);

		Session session = null;

		try {
			session = openSession();

			CourseType[] array = new CourseTypeImpl[3];

			array[0] = getByCompanyIdActive_PrevAndNext(session, courseType,
					companyId, active, orderByComparator, true);

			array[1] = courseType;

			array[2] = getByCompanyIdActive_PrevAndNext(session, courseType,
					companyId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CourseType getByCompanyIdActive_PrevAndNext(Session session,
		CourseType courseType, long companyId, boolean active,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COURSETYPE_WHERE);

		query.append(_FINDER_COLUMN_COMPANYIDACTIVE_COMPANYID_2);

		query.append(_FINDER_COLUMN_COMPANYIDACTIVE_ACTIVE_2);

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

		qPos.add(companyId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(courseType);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CourseType> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the course types where companyId = &#63; and classNameId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param active the active
	 * @return the matching course types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseType> findByCompanyIdClassNameIdActive(long companyId,
		long classNameId, boolean active) throws SystemException {
		return findByCompanyIdClassNameIdActive(companyId, classNameId, active,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course types where companyId = &#63; and classNameId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param active the active
	 * @param start the lower bound of the range of course types
	 * @param end the upper bound of the range of course types (not inclusive)
	 * @return the range of matching course types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseType> findByCompanyIdClassNameIdActive(long companyId,
		long classNameId, boolean active, int start, int end)
		throws SystemException {
		return findByCompanyIdClassNameIdActive(companyId, classNameId, active,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the course types where companyId = &#63; and classNameId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param active the active
	 * @param start the lower bound of the range of course types
	 * @param end the upper bound of the range of course types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching course types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseType> findByCompanyIdClassNameIdActive(long companyId,
		long classNameId, boolean active, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDACTIVE;
			finderArgs = new Object[] { companyId, classNameId, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDACTIVE;
			finderArgs = new Object[] {
					companyId, classNameId, active,
					
					start, end, orderByComparator
				};
		}

		List<CourseType> list = (List<CourseType>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CourseType courseType : list) {
				if ((companyId != courseType.getCompanyId()) ||
						(classNameId != courseType.getClassNameId()) ||
						(active != courseType.getActive())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_COURSETYPE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDACTIVE_COMPANYID_2);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDACTIVE_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDACTIVE_ACTIVE_2);

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

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(active);

				list = (List<CourseType>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first course type in the ordered set where companyId = &#63; and classNameId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type
	 * @throws com.liferay.lms.NoSuchCourseTypeException if a matching course type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType findByCompanyIdClassNameIdActive_First(long companyId,
		long classNameId, boolean active, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeException, SystemException {
		CourseType courseType = fetchByCompanyIdClassNameIdActive_First(companyId,
				classNameId, active, orderByComparator);

		if (courseType != null) {
			return courseType;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeException(msg.toString());
	}

	/**
	 * Returns the first course type in the ordered set where companyId = &#63; and classNameId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type, or <code>null</code> if a matching course type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType fetchByCompanyIdClassNameIdActive_First(long companyId,
		long classNameId, boolean active, OrderByComparator orderByComparator)
		throws SystemException {
		List<CourseType> list = findByCompanyIdClassNameIdActive(companyId,
				classNameId, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last course type in the ordered set where companyId = &#63; and classNameId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type
	 * @throws com.liferay.lms.NoSuchCourseTypeException if a matching course type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType findByCompanyIdClassNameIdActive_Last(long companyId,
		long classNameId, boolean active, OrderByComparator orderByComparator)
		throws NoSuchCourseTypeException, SystemException {
		CourseType courseType = fetchByCompanyIdClassNameIdActive_Last(companyId,
				classNameId, active, orderByComparator);

		if (courseType != null) {
			return courseType;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeException(msg.toString());
	}

	/**
	 * Returns the last course type in the ordered set where companyId = &#63; and classNameId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type, or <code>null</code> if a matching course type could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType fetchByCompanyIdClassNameIdActive_Last(long companyId,
		long classNameId, boolean active, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByCompanyIdClassNameIdActive(companyId, classNameId,
				active);

		List<CourseType> list = findByCompanyIdClassNameIdActive(companyId,
				classNameId, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the course types before and after the current course type in the ordered set where companyId = &#63; and classNameId = &#63; and active = &#63;.
	 *
	 * @param courseTypeId the primary key of the current course type
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next course type
	 * @throws com.liferay.lms.NoSuchCourseTypeException if a course type with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseType[] findByCompanyIdClassNameIdActive_PrevAndNext(
		long courseTypeId, long companyId, long classNameId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeException, SystemException {
		CourseType courseType = findByPrimaryKey(courseTypeId);

		Session session = null;

		try {
			session = openSession();

			CourseType[] array = new CourseTypeImpl[3];

			array[0] = getByCompanyIdClassNameIdActive_PrevAndNext(session,
					courseType, companyId, classNameId, active,
					orderByComparator, true);

			array[1] = courseType;

			array[2] = getByCompanyIdClassNameIdActive_PrevAndNext(session,
					courseType, companyId, classNameId, active,
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

	protected CourseType getByCompanyIdClassNameIdActive_PrevAndNext(
		Session session, CourseType courseType, long companyId,
		long classNameId, boolean active, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COURSETYPE_WHERE);

		query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDACTIVE_COMPANYID_2);

		query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDACTIVE_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDACTIVE_ACTIVE_2);

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

		qPos.add(companyId);

		qPos.add(classNameId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(courseType);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CourseType> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the course types.
	 *
	 * @return the course types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseType> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course types
	 * @param end the upper bound of the range of course types (not inclusive)
	 * @return the range of course types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseType> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the course types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course types
	 * @param end the upper bound of the range of course types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of course types
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseType> findAll(int start, int end,
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

		List<CourseType> list = (List<CourseType>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_COURSETYPE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COURSETYPE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<CourseType>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<CourseType>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the course types where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (CourseType courseType : findByCompanyId(companyId)) {
			remove(courseType);
		}
	}

	/**
	 * Removes all the course types where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyIdActive(long companyId, boolean active)
		throws SystemException {
		for (CourseType courseType : findByCompanyIdActive(companyId, active)) {
			remove(courseType);
		}
	}

	/**
	 * Removes all the course types where companyId = &#63; and classNameId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param active the active
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyIdClassNameIdActive(long companyId,
		long classNameId, boolean active) throws SystemException {
		for (CourseType courseType : findByCompanyIdClassNameIdActive(
				companyId, classNameId, active)) {
			remove(courseType);
		}
	}

	/**
	 * Removes all the course types from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (CourseType courseType : findAll()) {
			remove(courseType);
		}
	}

	/**
	 * Returns the number of course types where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching course types
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COURSETYPE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course types where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching course types
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyIdActive(long companyId, boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, active };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYIDACTIVE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COURSETYPE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYIDACTIVE_COMPANYID_2);

			query.append(_FINDER_COLUMN_COMPANYIDACTIVE_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(active);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYIDACTIVE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course types where companyId = &#63; and classNameId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param active the active
	 * @return the number of matching course types
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyIdClassNameIdActive(long companyId,
		long classNameId, boolean active) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, classNameId, active };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYIDCLASSNAMEIDACTIVE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_COURSETYPE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDACTIVE_COMPANYID_2);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDACTIVE_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDACTIVE_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(active);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYIDCLASSNAMEIDACTIVE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course types.
	 *
	 * @return the number of course types
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COURSETYPE);

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
	 * Initializes the course type persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.CourseType")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CourseType>> listenersList = new ArrayList<ModelListener<CourseType>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CourseType>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(CourseTypeImpl.class.getName());
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
	private static final String _SQL_SELECT_COURSETYPE = "SELECT courseType FROM CourseType courseType";
	private static final String _SQL_SELECT_COURSETYPE_WHERE = "SELECT courseType FROM CourseType courseType WHERE ";
	private static final String _SQL_COUNT_COURSETYPE = "SELECT COUNT(courseType) FROM CourseType courseType";
	private static final String _SQL_COUNT_COURSETYPE_WHERE = "SELECT COUNT(courseType) FROM CourseType courseType WHERE ";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "courseType.companyId = ?";
	private static final String _FINDER_COLUMN_COMPANYIDACTIVE_COMPANYID_2 = "courseType.companyId = ? AND ";
	private static final String _FINDER_COLUMN_COMPANYIDACTIVE_ACTIVE_2 = "courseType.active = ?";
	private static final String _FINDER_COLUMN_COMPANYIDCLASSNAMEIDACTIVE_COMPANYID_2 =
		"courseType.companyId = ? AND ";
	private static final String _FINDER_COLUMN_COMPANYIDCLASSNAMEIDACTIVE_CLASSNAMEID_2 =
		"courseType.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_COMPANYIDCLASSNAMEIDACTIVE_ACTIVE_2 =
		"courseType.active = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "courseType.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CourseType exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CourseType exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(CourseTypePersistenceImpl.class);
	private static CourseType _nullCourseType = new CourseTypeImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<CourseType> toCacheModel() {
				return _nullCourseTypeCacheModel;
			}
		};

	private static CacheModel<CourseType> _nullCourseTypeCacheModel = new CacheModel<CourseType>() {
			public CourseType toEntityModel() {
				return _nullCourseType;
			}
		};
}