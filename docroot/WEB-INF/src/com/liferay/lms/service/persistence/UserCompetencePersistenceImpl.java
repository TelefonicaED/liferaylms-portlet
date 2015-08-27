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

import com.liferay.lms.NoSuchUserCompetenceException;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.model.impl.UserCompetenceImpl;
import com.liferay.lms.model.impl.UserCompetenceModelImpl;

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
 * The persistence implementation for the user competence service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see UserCompetencePersistence
 * @see UserCompetenceUtil
 * @generated
 */
public class UserCompetencePersistenceImpl extends BasePersistenceImpl<UserCompetence>
	implements UserCompetencePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link UserCompetenceUtil} to access the user competence persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = UserCompetenceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceModelImpl.FINDER_CACHE_ENABLED,
			UserCompetenceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceModelImpl.FINDER_CACHE_ENABLED,
			UserCompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			UserCompetenceModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceModelImpl.FINDER_CACHE_ENABLED,
			UserCompetenceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceModelImpl.FINDER_CACHE_ENABLED,
			UserCompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			UserCompetenceModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_USERIDCOMPETENCEID = new FinderPath(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceModelImpl.FINDER_CACHE_ENABLED,
			UserCompetenceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUserIdCompetenceId",
			new String[] { Long.class.getName(), Long.class.getName() },
			UserCompetenceModelImpl.USERID_COLUMN_BITMASK |
			UserCompetenceModelImpl.COMPETENCEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERIDCOMPETENCEID = new FinderPath(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserIdCompetenceId",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceModelImpl.FINDER_CACHE_ENABLED,
			UserCompetenceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceModelImpl.FINDER_CACHE_ENABLED,
			UserCompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the user competence in the entity cache if it is enabled.
	 *
	 * @param userCompetence the user competence
	 */
	public void cacheResult(UserCompetence userCompetence) {
		EntityCacheUtil.putResult(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceImpl.class, userCompetence.getPrimaryKey(),
			userCompetence);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERIDCOMPETENCEID,
			new Object[] {
				Long.valueOf(userCompetence.getUserId()),
				Long.valueOf(userCompetence.getCompetenceId())
			}, userCompetence);

		userCompetence.resetOriginalValues();
	}

	/**
	 * Caches the user competences in the entity cache if it is enabled.
	 *
	 * @param userCompetences the user competences
	 */
	public void cacheResult(List<UserCompetence> userCompetences) {
		for (UserCompetence userCompetence : userCompetences) {
			if (EntityCacheUtil.getResult(
						UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
						UserCompetenceImpl.class, userCompetence.getPrimaryKey()) == null) {
				cacheResult(userCompetence);
			}
			else {
				userCompetence.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all user competences.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(UserCompetenceImpl.class.getName());
		}

		EntityCacheUtil.clearCache(UserCompetenceImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user competence.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserCompetence userCompetence) {
		EntityCacheUtil.removeResult(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceImpl.class, userCompetence.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(userCompetence);
	}

	@Override
	public void clearCache(List<UserCompetence> userCompetences) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UserCompetence userCompetence : userCompetences) {
			EntityCacheUtil.removeResult(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
				UserCompetenceImpl.class, userCompetence.getPrimaryKey());

			clearUniqueFindersCache(userCompetence);
		}
	}

	protected void clearUniqueFindersCache(UserCompetence userCompetence) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERIDCOMPETENCEID,
			new Object[] {
				Long.valueOf(userCompetence.getUserId()),
				Long.valueOf(userCompetence.getCompetenceId())
			});
	}

	/**
	 * Creates a new user competence with the primary key. Does not add the user competence to the database.
	 *
	 * @param usercompId the primary key for the new user competence
	 * @return the new user competence
	 */
	public UserCompetence create(long usercompId) {
		UserCompetence userCompetence = new UserCompetenceImpl();

		userCompetence.setNew(true);
		userCompetence.setPrimaryKey(usercompId);

		String uuid = PortalUUIDUtil.generate();

		userCompetence.setUuid(uuid);

		return userCompetence;
	}

	/**
	 * Removes the user competence with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param usercompId the primary key of the user competence
	 * @return the user competence that was removed
	 * @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence remove(long usercompId)
		throws NoSuchUserCompetenceException, SystemException {
		return remove(Long.valueOf(usercompId));
	}

	/**
	 * Removes the user competence with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user competence
	 * @return the user competence that was removed
	 * @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserCompetence remove(Serializable primaryKey)
		throws NoSuchUserCompetenceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserCompetence userCompetence = (UserCompetence)session.get(UserCompetenceImpl.class,
					primaryKey);

			if (userCompetence == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserCompetenceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(userCompetence);
		}
		catch (NoSuchUserCompetenceException nsee) {
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
	protected UserCompetence removeImpl(UserCompetence userCompetence)
		throws SystemException {
		userCompetence = toUnwrappedModel(userCompetence);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, userCompetence);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(userCompetence);

		return userCompetence;
	}

	@Override
	public UserCompetence updateImpl(
		com.liferay.lms.model.UserCompetence userCompetence, boolean merge)
		throws SystemException {
		userCompetence = toUnwrappedModel(userCompetence);

		boolean isNew = userCompetence.isNew();

		UserCompetenceModelImpl userCompetenceModelImpl = (UserCompetenceModelImpl)userCompetence;

		if (Validator.isNull(userCompetence.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			userCompetence.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, userCompetence, merge);

			userCompetence.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !UserCompetenceModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((userCompetenceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userCompetenceModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { userCompetenceModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((userCompetenceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(userCompetenceModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(userCompetenceModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}
		}

		EntityCacheUtil.putResult(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
			UserCompetenceImpl.class, userCompetence.getPrimaryKey(),
			userCompetence);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERIDCOMPETENCEID,
				new Object[] {
					Long.valueOf(userCompetence.getUserId()),
					Long.valueOf(userCompetence.getCompetenceId())
				}, userCompetence);
		}
		else {
			if ((userCompetenceModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_USERIDCOMPETENCEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(userCompetenceModelImpl.getOriginalUserId()),
						Long.valueOf(userCompetenceModelImpl.getOriginalCompetenceId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERIDCOMPETENCEID,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERIDCOMPETENCEID,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERIDCOMPETENCEID,
					new Object[] {
						Long.valueOf(userCompetence.getUserId()),
						Long.valueOf(userCompetence.getCompetenceId())
					}, userCompetence);
			}
		}

		return userCompetence;
	}

	protected UserCompetence toUnwrappedModel(UserCompetence userCompetence) {
		if (userCompetence instanceof UserCompetenceImpl) {
			return userCompetence;
		}

		UserCompetenceImpl userCompetenceImpl = new UserCompetenceImpl();

		userCompetenceImpl.setNew(userCompetence.isNew());
		userCompetenceImpl.setPrimaryKey(userCompetence.getPrimaryKey());

		userCompetenceImpl.setUuid(userCompetence.getUuid());
		userCompetenceImpl.setUsercompId(userCompetence.getUsercompId());
		userCompetenceImpl.setUserId(userCompetence.getUserId());
		userCompetenceImpl.setCompetenceId(userCompetence.getCompetenceId());
		userCompetenceImpl.setCompDate(userCompetence.getCompDate());
		userCompetenceImpl.setCourseId(userCompetence.getCourseId());

		return userCompetenceImpl;
	}

	/**
	 * Returns the user competence with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the user competence
	 * @return the user competence
	 * @throws com.liferay.portal.NoSuchModelException if a user competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserCompetence findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the user competence with the primary key or throws a {@link com.liferay.lms.NoSuchUserCompetenceException} if it could not be found.
	 *
	 * @param usercompId the primary key of the user competence
	 * @return the user competence
	 * @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence findByPrimaryKey(long usercompId)
		throws NoSuchUserCompetenceException, SystemException {
		UserCompetence userCompetence = fetchByPrimaryKey(usercompId);

		if (userCompetence == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + usercompId);
			}

			throw new NoSuchUserCompetenceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				usercompId);
		}

		return userCompetence;
	}

	/**
	 * Returns the user competence with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user competence
	 * @return the user competence, or <code>null</code> if a user competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserCompetence fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the user competence with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param usercompId the primary key of the user competence
	 * @return the user competence, or <code>null</code> if a user competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence fetchByPrimaryKey(long usercompId)
		throws SystemException {
		UserCompetence userCompetence = (UserCompetence)EntityCacheUtil.getResult(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
				UserCompetenceImpl.class, usercompId);

		if (userCompetence == _nullUserCompetence) {
			return null;
		}

		if (userCompetence == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				userCompetence = (UserCompetence)session.get(UserCompetenceImpl.class,
						Long.valueOf(usercompId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (userCompetence != null) {
					cacheResult(userCompetence);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(UserCompetenceModelImpl.ENTITY_CACHE_ENABLED,
						UserCompetenceImpl.class, usercompId,
						_nullUserCompetence);
				}

				closeSession(session);
			}
		}

		return userCompetence;
	}

	/**
	 * Returns all the user competences where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching user competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserCompetence> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user competences where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of user competences
	 * @param end the upper bound of the range of user competences (not inclusive)
	 * @return the range of matching user competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserCompetence> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user competences where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of user competences
	 * @param end the upper bound of the range of user competences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserCompetence> findByUuid(String uuid, int start, int end,
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

		List<UserCompetence> list = (List<UserCompetence>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserCompetence userCompetence : list) {
				if (!Validator.equals(uuid, userCompetence.getUuid())) {
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

			query.append(_SQL_SELECT_USERCOMPETENCE_WHERE);

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

				list = (List<UserCompetence>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first user competence in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user competence
	 * @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchUserCompetenceException, SystemException {
		UserCompetence userCompetence = fetchByUuid_First(uuid,
				orderByComparator);

		if (userCompetence != null) {
			return userCompetence;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserCompetenceException(msg.toString());
	}

	/**
	 * Returns the first user competence in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user competence, or <code>null</code> if a matching user competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<UserCompetence> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user competence in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user competence
	 * @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchUserCompetenceException, SystemException {
		UserCompetence userCompetence = fetchByUuid_Last(uuid, orderByComparator);

		if (userCompetence != null) {
			return userCompetence;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserCompetenceException(msg.toString());
	}

	/**
	 * Returns the last user competence in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user competence, or <code>null</code> if a matching user competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<UserCompetence> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user competences before and after the current user competence in the ordered set where uuid = &#63;.
	 *
	 * @param usercompId the primary key of the current user competence
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user competence
	 * @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence[] findByUuid_PrevAndNext(long usercompId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchUserCompetenceException, SystemException {
		UserCompetence userCompetence = findByPrimaryKey(usercompId);

		Session session = null;

		try {
			session = openSession();

			UserCompetence[] array = new UserCompetenceImpl[3];

			array[0] = getByUuid_PrevAndNext(session, userCompetence, uuid,
					orderByComparator, true);

			array[1] = userCompetence;

			array[2] = getByUuid_PrevAndNext(session, userCompetence, uuid,
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

	protected UserCompetence getByUuid_PrevAndNext(Session session,
		UserCompetence userCompetence, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERCOMPETENCE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(userCompetence);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserCompetence> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user competences where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching user competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserCompetence> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user competences where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user competences
	 * @param end the upper bound of the range of user competences (not inclusive)
	 * @return the range of matching user competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserCompetence> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user competences where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user competences
	 * @param end the upper bound of the range of user competences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserCompetence> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<UserCompetence> list = (List<UserCompetence>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserCompetence userCompetence : list) {
				if ((userId != userCompetence.getUserId())) {
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

			query.append(_SQL_SELECT_USERCOMPETENCE_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

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

				qPos.add(userId);

				list = (List<UserCompetence>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first user competence in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user competence
	 * @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchUserCompetenceException, SystemException {
		UserCompetence userCompetence = fetchByUserId_First(userId,
				orderByComparator);

		if (userCompetence != null) {
			return userCompetence;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserCompetenceException(msg.toString());
	}

	/**
	 * Returns the first user competence in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user competence, or <code>null</code> if a matching user competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<UserCompetence> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user competence in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user competence
	 * @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchUserCompetenceException, SystemException {
		UserCompetence userCompetence = fetchByUserId_Last(userId,
				orderByComparator);

		if (userCompetence != null) {
			return userCompetence;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserCompetenceException(msg.toString());
	}

	/**
	 * Returns the last user competence in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user competence, or <code>null</code> if a matching user competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<UserCompetence> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user competences before and after the current user competence in the ordered set where userId = &#63;.
	 *
	 * @param usercompId the primary key of the current user competence
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user competence
	 * @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence[] findByUserId_PrevAndNext(long usercompId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchUserCompetenceException, SystemException {
		UserCompetence userCompetence = findByPrimaryKey(usercompId);

		Session session = null;

		try {
			session = openSession();

			UserCompetence[] array = new UserCompetenceImpl[3];

			array[0] = getByUserId_PrevAndNext(session, userCompetence, userId,
					orderByComparator, true);

			array[1] = userCompetence;

			array[2] = getByUserId_PrevAndNext(session, userCompetence, userId,
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

	protected UserCompetence getByUserId_PrevAndNext(Session session,
		UserCompetence userCompetence, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERCOMPETENCE_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userCompetence);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserCompetence> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the user competence where userId = &#63; and competenceId = &#63; or throws a {@link com.liferay.lms.NoSuchUserCompetenceException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param competenceId the competence ID
	 * @return the matching user competence
	 * @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence findByUserIdCompetenceId(long userId,
		long competenceId)
		throws NoSuchUserCompetenceException, SystemException {
		UserCompetence userCompetence = fetchByUserIdCompetenceId(userId,
				competenceId);

		if (userCompetence == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", competenceId=");
			msg.append(competenceId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserCompetenceException(msg.toString());
		}

		return userCompetence;
	}

	/**
	 * Returns the user competence where userId = &#63; and competenceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param competenceId the competence ID
	 * @return the matching user competence, or <code>null</code> if a matching user competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence fetchByUserIdCompetenceId(long userId,
		long competenceId) throws SystemException {
		return fetchByUserIdCompetenceId(userId, competenceId, true);
	}

	/**
	 * Returns the user competence where userId = &#63; and competenceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param competenceId the competence ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user competence, or <code>null</code> if a matching user competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence fetchByUserIdCompetenceId(long userId,
		long competenceId, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { userId, competenceId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_USERIDCOMPETENCEID,
					finderArgs, this);
		}

		if (result instanceof UserCompetence) {
			UserCompetence userCompetence = (UserCompetence)result;

			if ((userId != userCompetence.getUserId()) ||
					(competenceId != userCompetence.getCompetenceId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_USERCOMPETENCE_WHERE);

			query.append(_FINDER_COLUMN_USERIDCOMPETENCEID_USERID_2);

			query.append(_FINDER_COLUMN_USERIDCOMPETENCEID_COMPETENCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(competenceId);

				List<UserCompetence> list = q.list();

				result = list;

				UserCompetence userCompetence = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERIDCOMPETENCEID,
						finderArgs, list);
				}
				else {
					userCompetence = list.get(0);

					cacheResult(userCompetence);

					if ((userCompetence.getUserId() != userId) ||
							(userCompetence.getCompetenceId() != competenceId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERIDCOMPETENCEID,
							finderArgs, userCompetence);
					}
				}

				return userCompetence;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERIDCOMPETENCEID,
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
				return (UserCompetence)result;
			}
		}
	}

	/**
	 * Returns all the user competences.
	 *
	 * @return the user competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserCompetence> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user competences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user competences
	 * @param end the upper bound of the range of user competences (not inclusive)
	 * @return the range of user competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserCompetence> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user competences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user competences
	 * @param end the upper bound of the range of user competences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserCompetence> findAll(int start, int end,
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

		List<UserCompetence> list = (List<UserCompetence>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_USERCOMPETENCE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USERCOMPETENCE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<UserCompetence>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserCompetence>)QueryUtil.list(q,
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
	 * Removes all the user competences where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (UserCompetence userCompetence : findByUuid(uuid)) {
			remove(userCompetence);
		}
	}

	/**
	 * Removes all the user competences where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (UserCompetence userCompetence : findByUserId(userId)) {
			remove(userCompetence);
		}
	}

	/**
	 * Removes the user competence where userId = &#63; and competenceId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param competenceId the competence ID
	 * @return the user competence that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public UserCompetence removeByUserIdCompetenceId(long userId,
		long competenceId)
		throws NoSuchUserCompetenceException, SystemException {
		UserCompetence userCompetence = findByUserIdCompetenceId(userId,
				competenceId);

		return remove(userCompetence);
	}

	/**
	 * Removes all the user competences from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (UserCompetence userCompetence : findAll()) {
			remove(userCompetence);
		}
	}

	/**
	 * Returns the number of user competences where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching user competences
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERCOMPETENCE_WHERE);

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
	 * Returns the number of user competences where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching user competences
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERCOMPETENCE_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of user competences where userId = &#63; and competenceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param competenceId the competence ID
	 * @return the number of matching user competences
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserIdCompetenceId(long userId, long competenceId)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, competenceId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERIDCOMPETENCEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERCOMPETENCE_WHERE);

			query.append(_FINDER_COLUMN_USERIDCOMPETENCEID_USERID_2);

			query.append(_FINDER_COLUMN_USERIDCOMPETENCEID_COMPETENCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(competenceId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERIDCOMPETENCEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of user competences.
	 *
	 * @return the number of user competences
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USERCOMPETENCE);

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
	 * Initializes the user competence persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.UserCompetence")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserCompetence>> listenersList = new ArrayList<ModelListener<UserCompetence>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserCompetence>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(UserCompetenceImpl.class.getName());
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
	private static final String _SQL_SELECT_USERCOMPETENCE = "SELECT userCompetence FROM UserCompetence userCompetence";
	private static final String _SQL_SELECT_USERCOMPETENCE_WHERE = "SELECT userCompetence FROM UserCompetence userCompetence WHERE ";
	private static final String _SQL_COUNT_USERCOMPETENCE = "SELECT COUNT(userCompetence) FROM UserCompetence userCompetence";
	private static final String _SQL_COUNT_USERCOMPETENCE_WHERE = "SELECT COUNT(userCompetence) FROM UserCompetence userCompetence WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "userCompetence.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "userCompetence.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(userCompetence.uuid IS NULL OR userCompetence.uuid = ?)";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "userCompetence.userId = ?";
	private static final String _FINDER_COLUMN_USERIDCOMPETENCEID_USERID_2 = "userCompetence.userId = ? AND ";
	private static final String _FINDER_COLUMN_USERIDCOMPETENCEID_COMPETENCEID_2 =
		"userCompetence.competenceId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userCompetence.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserCompetence exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserCompetence exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(UserCompetencePersistenceImpl.class);
	private static UserCompetence _nullUserCompetence = new UserCompetenceImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<UserCompetence> toCacheModel() {
				return _nullUserCompetenceCacheModel;
			}
		};

	private static CacheModel<UserCompetence> _nullUserCompetenceCacheModel = new CacheModel<UserCompetence>() {
			public UserCompetence toEntityModel() {
				return _nullUserCompetence;
			}
		};
}