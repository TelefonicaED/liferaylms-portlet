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

import com.liferay.lms.NoSuchCourseTypeTemplateException;
import com.liferay.lms.model.CourseTypeTemplate;
import com.liferay.lms.model.impl.CourseTypeTemplateImpl;
import com.liferay.lms.model.impl.CourseTypeTemplateModelImpl;

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
 * The persistence implementation for the course type template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CourseTypeTemplatePersistence
 * @see CourseTypeTemplateUtil
 * @generated
 */
public class CourseTypeTemplatePersistenceImpl extends BasePersistenceImpl<CourseTypeTemplate>
	implements CourseTypeTemplatePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CourseTypeTemplateUtil} to access the course type template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CourseTypeTemplateImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_COURSETYPETEMPLATEID = new FinderPath(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeTemplateModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeTemplateImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCourseTypeTemplateId",
			new String[] { Long.class.getName() },
			CourseTypeTemplateModelImpl.COURSETYPETEMPLATEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSETYPETEMPLATEID = new FinderPath(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCourseTypeTemplateId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COURSETYPEID =
		new FinderPath(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeTemplateModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCourseTypeId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID =
		new FinderPath(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeTemplateModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCourseTypeId",
			new String[] { Long.class.getName() },
			CourseTypeTemplateModelImpl.COURSETYPEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COURSETYPEID = new FinderPath(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCourseTypeId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeTemplateModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeTemplateModelImpl.FINDER_CACHE_ENABLED,
			CourseTypeTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeTemplateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the course type template in the entity cache if it is enabled.
	 *
	 * @param courseTypeTemplate the course type template
	 */
	public void cacheResult(CourseTypeTemplate courseTypeTemplate) {
		EntityCacheUtil.putResult(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeTemplateImpl.class, courseTypeTemplate.getPrimaryKey(),
			courseTypeTemplate);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPETEMPLATEID,
			new Object[] {
				Long.valueOf(courseTypeTemplate.getCourseTypeTemplateId())
			}, courseTypeTemplate);

		courseTypeTemplate.resetOriginalValues();
	}

	/**
	 * Caches the course type templates in the entity cache if it is enabled.
	 *
	 * @param courseTypeTemplates the course type templates
	 */
	public void cacheResult(List<CourseTypeTemplate> courseTypeTemplates) {
		for (CourseTypeTemplate courseTypeTemplate : courseTypeTemplates) {
			if (EntityCacheUtil.getResult(
						CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeTemplateImpl.class,
						courseTypeTemplate.getPrimaryKey()) == null) {
				cacheResult(courseTypeTemplate);
			}
			else {
				courseTypeTemplate.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all course type templates.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CourseTypeTemplateImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CourseTypeTemplateImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the course type template.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CourseTypeTemplate courseTypeTemplate) {
		EntityCacheUtil.removeResult(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeTemplateImpl.class, courseTypeTemplate.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(courseTypeTemplate);
	}

	@Override
	public void clearCache(List<CourseTypeTemplate> courseTypeTemplates) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CourseTypeTemplate courseTypeTemplate : courseTypeTemplates) {
			EntityCacheUtil.removeResult(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeTemplateImpl.class, courseTypeTemplate.getPrimaryKey());

			clearUniqueFindersCache(courseTypeTemplate);
		}
	}

	protected void clearUniqueFindersCache(
		CourseTypeTemplate courseTypeTemplate) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPETEMPLATEID,
			new Object[] {
				Long.valueOf(courseTypeTemplate.getCourseTypeTemplateId())
			});
	}

	/**
	 * Creates a new course type template with the primary key. Does not add the course type template to the database.
	 *
	 * @param courseTypeTemplateId the primary key for the new course type template
	 * @return the new course type template
	 */
	public CourseTypeTemplate create(long courseTypeTemplateId) {
		CourseTypeTemplate courseTypeTemplate = new CourseTypeTemplateImpl();

		courseTypeTemplate.setNew(true);
		courseTypeTemplate.setPrimaryKey(courseTypeTemplateId);

		return courseTypeTemplate;
	}

	/**
	 * Removes the course type template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param courseTypeTemplateId the primary key of the course type template
	 * @return the course type template that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeTemplateException if a course type template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeTemplate remove(long courseTypeTemplateId)
		throws NoSuchCourseTypeTemplateException, SystemException {
		return remove(Long.valueOf(courseTypeTemplateId));
	}

	/**
	 * Removes the course type template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the course type template
	 * @return the course type template that was removed
	 * @throws com.liferay.lms.NoSuchCourseTypeTemplateException if a course type template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeTemplate remove(Serializable primaryKey)
		throws NoSuchCourseTypeTemplateException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CourseTypeTemplate courseTypeTemplate = (CourseTypeTemplate)session.get(CourseTypeTemplateImpl.class,
					primaryKey);

			if (courseTypeTemplate == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCourseTypeTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(courseTypeTemplate);
		}
		catch (NoSuchCourseTypeTemplateException nsee) {
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
	protected CourseTypeTemplate removeImpl(
		CourseTypeTemplate courseTypeTemplate) throws SystemException {
		courseTypeTemplate = toUnwrappedModel(courseTypeTemplate);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, courseTypeTemplate);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(courseTypeTemplate);

		return courseTypeTemplate;
	}

	@Override
	public CourseTypeTemplate updateImpl(
		com.liferay.lms.model.CourseTypeTemplate courseTypeTemplate,
		boolean merge) throws SystemException {
		courseTypeTemplate = toUnwrappedModel(courseTypeTemplate);

		boolean isNew = courseTypeTemplate.isNew();

		CourseTypeTemplateModelImpl courseTypeTemplateModelImpl = (CourseTypeTemplateModelImpl)courseTypeTemplate;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, courseTypeTemplate, merge);

			courseTypeTemplate.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !CourseTypeTemplateModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((courseTypeTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeTemplateModelImpl.getOriginalCourseTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID,
					args);

				args = new Object[] {
						Long.valueOf(courseTypeTemplateModelImpl.getCourseTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COURSETYPEID,
					args);
			}
		}

		EntityCacheUtil.putResult(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
			CourseTypeTemplateImpl.class, courseTypeTemplate.getPrimaryKey(),
			courseTypeTemplate);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPETEMPLATEID,
				new Object[] {
					Long.valueOf(courseTypeTemplate.getCourseTypeTemplateId())
				}, courseTypeTemplate);
		}
		else {
			if ((courseTypeTemplateModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_COURSETYPETEMPLATEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(courseTypeTemplateModelImpl.getOriginalCourseTypeTemplateId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COURSETYPETEMPLATEID,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPETEMPLATEID,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPETEMPLATEID,
					new Object[] {
						Long.valueOf(
							courseTypeTemplate.getCourseTypeTemplateId())
					}, courseTypeTemplate);
			}
		}

		return courseTypeTemplate;
	}

	protected CourseTypeTemplate toUnwrappedModel(
		CourseTypeTemplate courseTypeTemplate) {
		if (courseTypeTemplate instanceof CourseTypeTemplateImpl) {
			return courseTypeTemplate;
		}

		CourseTypeTemplateImpl courseTypeTemplateImpl = new CourseTypeTemplateImpl();

		courseTypeTemplateImpl.setNew(courseTypeTemplate.isNew());
		courseTypeTemplateImpl.setPrimaryKey(courseTypeTemplate.getPrimaryKey());

		courseTypeTemplateImpl.setCourseTypeTemplateId(courseTypeTemplate.getCourseTypeTemplateId());
		courseTypeTemplateImpl.setCourseTypeId(courseTypeTemplate.getCourseTypeId());
		courseTypeTemplateImpl.setTemplateId(courseTypeTemplate.getTemplateId());

		return courseTypeTemplateImpl;
	}

	/**
	 * Returns the course type template with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type template
	 * @return the course type template
	 * @throws com.liferay.portal.NoSuchModelException if a course type template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeTemplate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type template with the primary key or throws a {@link com.liferay.lms.NoSuchCourseTypeTemplateException} if it could not be found.
	 *
	 * @param courseTypeTemplateId the primary key of the course type template
	 * @return the course type template
	 * @throws com.liferay.lms.NoSuchCourseTypeTemplateException if a course type template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeTemplate findByPrimaryKey(long courseTypeTemplateId)
		throws NoSuchCourseTypeTemplateException, SystemException {
		CourseTypeTemplate courseTypeTemplate = fetchByPrimaryKey(courseTypeTemplateId);

		if (courseTypeTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					courseTypeTemplateId);
			}

			throw new NoSuchCourseTypeTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				courseTypeTemplateId);
		}

		return courseTypeTemplate;
	}

	/**
	 * Returns the course type template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the course type template
	 * @return the course type template, or <code>null</code> if a course type template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CourseTypeTemplate fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the course type template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param courseTypeTemplateId the primary key of the course type template
	 * @return the course type template, or <code>null</code> if a course type template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeTemplate fetchByPrimaryKey(long courseTypeTemplateId)
		throws SystemException {
		CourseTypeTemplate courseTypeTemplate = (CourseTypeTemplate)EntityCacheUtil.getResult(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
				CourseTypeTemplateImpl.class, courseTypeTemplateId);

		if (courseTypeTemplate == _nullCourseTypeTemplate) {
			return null;
		}

		if (courseTypeTemplate == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				courseTypeTemplate = (CourseTypeTemplate)session.get(CourseTypeTemplateImpl.class,
						Long.valueOf(courseTypeTemplateId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (courseTypeTemplate != null) {
					cacheResult(courseTypeTemplate);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(CourseTypeTemplateModelImpl.ENTITY_CACHE_ENABLED,
						CourseTypeTemplateImpl.class, courseTypeTemplateId,
						_nullCourseTypeTemplate);
				}

				closeSession(session);
			}
		}

		return courseTypeTemplate;
	}

	/**
	 * Returns the course type template where courseTypeTemplateId = &#63; or throws a {@link com.liferay.lms.NoSuchCourseTypeTemplateException} if it could not be found.
	 *
	 * @param courseTypeTemplateId the course type template ID
	 * @return the matching course type template
	 * @throws com.liferay.lms.NoSuchCourseTypeTemplateException if a matching course type template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeTemplate findByCourseTypeTemplateId(
		long courseTypeTemplateId)
		throws NoSuchCourseTypeTemplateException, SystemException {
		CourseTypeTemplate courseTypeTemplate = fetchByCourseTypeTemplateId(courseTypeTemplateId);

		if (courseTypeTemplate == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("courseTypeTemplateId=");
			msg.append(courseTypeTemplateId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCourseTypeTemplateException(msg.toString());
		}

		return courseTypeTemplate;
	}

	/**
	 * Returns the course type template where courseTypeTemplateId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param courseTypeTemplateId the course type template ID
	 * @return the matching course type template, or <code>null</code> if a matching course type template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeTemplate fetchByCourseTypeTemplateId(
		long courseTypeTemplateId) throws SystemException {
		return fetchByCourseTypeTemplateId(courseTypeTemplateId, true);
	}

	/**
	 * Returns the course type template where courseTypeTemplateId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param courseTypeTemplateId the course type template ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching course type template, or <code>null</code> if a matching course type template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeTemplate fetchByCourseTypeTemplateId(
		long courseTypeTemplateId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeTemplateId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_COURSETYPETEMPLATEID,
					finderArgs, this);
		}

		if (result instanceof CourseTypeTemplate) {
			CourseTypeTemplate courseTypeTemplate = (CourseTypeTemplate)result;

			if ((courseTypeTemplateId != courseTypeTemplate.getCourseTypeTemplateId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_COURSETYPETEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPETEMPLATEID_COURSETYPETEMPLATEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeTemplateId);

				List<CourseTypeTemplate> list = q.list();

				result = list;

				CourseTypeTemplate courseTypeTemplate = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPETEMPLATEID,
						finderArgs, list);
				}
				else {
					courseTypeTemplate = list.get(0);

					cacheResult(courseTypeTemplate);

					if ((courseTypeTemplate.getCourseTypeTemplateId() != courseTypeTemplateId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_COURSETYPETEMPLATEID,
							finderArgs, courseTypeTemplate);
					}
				}

				return courseTypeTemplate;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_COURSETYPETEMPLATEID,
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
				return (CourseTypeTemplate)result;
			}
		}
	}

	/**
	 * Returns all the course type templates where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @return the matching course type templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeTemplate> findByCourseTypeId(long courseTypeId)
		throws SystemException {
		return findByCourseTypeId(courseTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course type templates where courseTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param start the lower bound of the range of course type templates
	 * @param end the upper bound of the range of course type templates (not inclusive)
	 * @return the range of matching course type templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeTemplate> findByCourseTypeId(long courseTypeId,
		int start, int end) throws SystemException {
		return findByCourseTypeId(courseTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the course type templates where courseTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param courseTypeId the course type ID
	 * @param start the lower bound of the range of course type templates
	 * @param end the upper bound of the range of course type templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching course type templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeTemplate> findByCourseTypeId(long courseTypeId,
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

		List<CourseTypeTemplate> list = (List<CourseTypeTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CourseTypeTemplate courseTypeTemplate : list) {
				if ((courseTypeId != courseTypeTemplate.getCourseTypeId())) {
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

			query.append(_SQL_SELECT_COURSETYPETEMPLATE_WHERE);

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

				list = (List<CourseTypeTemplate>)QueryUtil.list(q,
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
	 * Returns the first course type template in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type template
	 * @throws com.liferay.lms.NoSuchCourseTypeTemplateException if a matching course type template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeTemplate findByCourseTypeId_First(long courseTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeTemplateException, SystemException {
		CourseTypeTemplate courseTypeTemplate = fetchByCourseTypeId_First(courseTypeId,
				orderByComparator);

		if (courseTypeTemplate != null) {
			return courseTypeTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeTemplateException(msg.toString());
	}

	/**
	 * Returns the first course type template in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching course type template, or <code>null</code> if a matching course type template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeTemplate fetchByCourseTypeId_First(long courseTypeId,
		OrderByComparator orderByComparator) throws SystemException {
		List<CourseTypeTemplate> list = findByCourseTypeId(courseTypeId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last course type template in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type template
	 * @throws com.liferay.lms.NoSuchCourseTypeTemplateException if a matching course type template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeTemplate findByCourseTypeId_Last(long courseTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeTemplateException, SystemException {
		CourseTypeTemplate courseTypeTemplate = fetchByCourseTypeId_Last(courseTypeId,
				orderByComparator);

		if (courseTypeTemplate != null) {
			return courseTypeTemplate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("courseTypeId=");
		msg.append(courseTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCourseTypeTemplateException(msg.toString());
	}

	/**
	 * Returns the last course type template in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching course type template, or <code>null</code> if a matching course type template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeTemplate fetchByCourseTypeId_Last(long courseTypeId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCourseTypeId(courseTypeId);

		List<CourseTypeTemplate> list = findByCourseTypeId(courseTypeId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the course type templates before and after the current course type template in the ordered set where courseTypeId = &#63;.
	 *
	 * @param courseTypeTemplateId the primary key of the current course type template
	 * @param courseTypeId the course type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next course type template
	 * @throws com.liferay.lms.NoSuchCourseTypeTemplateException if a course type template with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeTemplate[] findByCourseTypeId_PrevAndNext(
		long courseTypeTemplateId, long courseTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchCourseTypeTemplateException, SystemException {
		CourseTypeTemplate courseTypeTemplate = findByPrimaryKey(courseTypeTemplateId);

		Session session = null;

		try {
			session = openSession();

			CourseTypeTemplate[] array = new CourseTypeTemplateImpl[3];

			array[0] = getByCourseTypeId_PrevAndNext(session,
					courseTypeTemplate, courseTypeId, orderByComparator, true);

			array[1] = courseTypeTemplate;

			array[2] = getByCourseTypeId_PrevAndNext(session,
					courseTypeTemplate, courseTypeId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CourseTypeTemplate getByCourseTypeId_PrevAndNext(
		Session session, CourseTypeTemplate courseTypeTemplate,
		long courseTypeId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COURSETYPETEMPLATE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(courseTypeTemplate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CourseTypeTemplate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the course type templates.
	 *
	 * @return the course type templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeTemplate> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the course type templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course type templates
	 * @param end the upper bound of the range of course type templates (not inclusive)
	 * @return the range of course type templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeTemplate> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the course type templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of course type templates
	 * @param end the upper bound of the range of course type templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of course type templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<CourseTypeTemplate> findAll(int start, int end,
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

		List<CourseTypeTemplate> list = (List<CourseTypeTemplate>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_COURSETYPETEMPLATE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COURSETYPETEMPLATE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<CourseTypeTemplate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<CourseTypeTemplate>)QueryUtil.list(q,
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
	 * Removes the course type template where courseTypeTemplateId = &#63; from the database.
	 *
	 * @param courseTypeTemplateId the course type template ID
	 * @return the course type template that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public CourseTypeTemplate removeByCourseTypeTemplateId(
		long courseTypeTemplateId)
		throws NoSuchCourseTypeTemplateException, SystemException {
		CourseTypeTemplate courseTypeTemplate = findByCourseTypeTemplateId(courseTypeTemplateId);

		return remove(courseTypeTemplate);
	}

	/**
	 * Removes all the course type templates where courseTypeId = &#63; from the database.
	 *
	 * @param courseTypeId the course type ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCourseTypeId(long courseTypeId)
		throws SystemException {
		for (CourseTypeTemplate courseTypeTemplate : findByCourseTypeId(
				courseTypeId)) {
			remove(courseTypeTemplate);
		}
	}

	/**
	 * Removes all the course type templates from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (CourseTypeTemplate courseTypeTemplate : findAll()) {
			remove(courseTypeTemplate);
		}
	}

	/**
	 * Returns the number of course type templates where courseTypeTemplateId = &#63;.
	 *
	 * @param courseTypeTemplateId the course type template ID
	 * @return the number of matching course type templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseTypeTemplateId(long courseTypeTemplateId)
		throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeTemplateId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSETYPETEMPLATEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COURSETYPETEMPLATE_WHERE);

			query.append(_FINDER_COLUMN_COURSETYPETEMPLATEID_COURSETYPETEMPLATEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(courseTypeTemplateId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COURSETYPETEMPLATEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of course type templates where courseTypeId = &#63;.
	 *
	 * @param courseTypeId the course type ID
	 * @return the number of matching course type templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCourseTypeId(long courseTypeId) throws SystemException {
		Object[] finderArgs = new Object[] { courseTypeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COURSETYPEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COURSETYPETEMPLATE_WHERE);

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
	 * Returns the number of course type templates.
	 *
	 * @return the number of course type templates
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COURSETYPETEMPLATE);

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
	 * Initializes the course type template persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.CourseTypeTemplate")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CourseTypeTemplate>> listenersList = new ArrayList<ModelListener<CourseTypeTemplate>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CourseTypeTemplate>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(CourseTypeTemplateImpl.class.getName());
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
	private static final String _SQL_SELECT_COURSETYPETEMPLATE = "SELECT courseTypeTemplate FROM CourseTypeTemplate courseTypeTemplate";
	private static final String _SQL_SELECT_COURSETYPETEMPLATE_WHERE = "SELECT courseTypeTemplate FROM CourseTypeTemplate courseTypeTemplate WHERE ";
	private static final String _SQL_COUNT_COURSETYPETEMPLATE = "SELECT COUNT(courseTypeTemplate) FROM CourseTypeTemplate courseTypeTemplate";
	private static final String _SQL_COUNT_COURSETYPETEMPLATE_WHERE = "SELECT COUNT(courseTypeTemplate) FROM CourseTypeTemplate courseTypeTemplate WHERE ";
	private static final String _FINDER_COLUMN_COURSETYPETEMPLATEID_COURSETYPETEMPLATEID_2 =
		"courseTypeTemplate.courseTypeTemplateId = ?";
	private static final String _FINDER_COLUMN_COURSETYPEID_COURSETYPEID_2 = "courseTypeTemplate.courseTypeId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "courseTypeTemplate.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CourseTypeTemplate exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CourseTypeTemplate exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(CourseTypeTemplatePersistenceImpl.class);
	private static CourseTypeTemplate _nullCourseTypeTemplate = new CourseTypeTemplateImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<CourseTypeTemplate> toCacheModel() {
				return _nullCourseTypeTemplateCacheModel;
			}
		};

	private static CacheModel<CourseTypeTemplate> _nullCourseTypeTemplateCacheModel =
		new CacheModel<CourseTypeTemplate>() {
			public CourseTypeTemplate toEntityModel() {
				return _nullCourseTypeTemplate;
			}
		};
}