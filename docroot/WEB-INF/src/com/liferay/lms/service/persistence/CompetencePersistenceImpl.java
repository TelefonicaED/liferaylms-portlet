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

import com.liferay.lms.NoSuchCompetenceException;
import com.liferay.lms.model.Competence;
import com.liferay.lms.model.impl.CompetenceImpl;
import com.liferay.lms.model.impl.CompetenceModelImpl;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
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
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the competence service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see CompetencePersistence
 * @see CompetenceUtil
 * @generated
 */
public class CompetencePersistenceImpl extends BasePersistenceImpl<Competence>
	implements CompetencePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CompetenceUtil} to access the competence persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CompetenceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, CompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, CompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CompetenceModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, CompetenceImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CompetenceModelImpl.UUID_COLUMN_BITMASK |
			CompetenceModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, CompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, CompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CompetenceModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, CompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, CompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CompetenceModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, CompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, CompetenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the competence in the entity cache if it is enabled.
	 *
	 * @param competence the competence
	 */
	public void cacheResult(Competence competence) {
		EntityCacheUtil.putResult(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceImpl.class, competence.getPrimaryKey(), competence);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				competence.getUuid(), Long.valueOf(competence.getGroupId())
			}, competence);

		competence.resetOriginalValues();
	}

	/**
	 * Caches the competences in the entity cache if it is enabled.
	 *
	 * @param competences the competences
	 */
	public void cacheResult(List<Competence> competences) {
		for (Competence competence : competences) {
			if (EntityCacheUtil.getResult(
						CompetenceModelImpl.ENTITY_CACHE_ENABLED,
						CompetenceImpl.class, competence.getPrimaryKey()) == null) {
				cacheResult(competence);
			}
			else {
				competence.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all competences.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CompetenceImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CompetenceImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the competence.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Competence competence) {
		EntityCacheUtil.removeResult(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceImpl.class, competence.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(competence);
	}

	@Override
	public void clearCache(List<Competence> competences) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Competence competence : competences) {
			EntityCacheUtil.removeResult(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
				CompetenceImpl.class, competence.getPrimaryKey());

			clearUniqueFindersCache(competence);
		}
	}

	protected void clearUniqueFindersCache(Competence competence) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				competence.getUuid(), Long.valueOf(competence.getGroupId())
			});
	}

	/**
	 * Creates a new competence with the primary key. Does not add the competence to the database.
	 *
	 * @param competenceId the primary key for the new competence
	 * @return the new competence
	 */
	public Competence create(long competenceId) {
		Competence competence = new CompetenceImpl();

		competence.setNew(true);
		competence.setPrimaryKey(competenceId);

		String uuid = PortalUUIDUtil.generate();

		competence.setUuid(uuid);

		return competence;
	}

	/**
	 * Removes the competence with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param competenceId the primary key of the competence
	 * @return the competence that was removed
	 * @throws com.liferay.lms.NoSuchCompetenceException if a competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence remove(long competenceId)
		throws NoSuchCompetenceException, SystemException {
		return remove(Long.valueOf(competenceId));
	}

	/**
	 * Removes the competence with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the competence
	 * @return the competence that was removed
	 * @throws com.liferay.lms.NoSuchCompetenceException if a competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Competence remove(Serializable primaryKey)
		throws NoSuchCompetenceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Competence competence = (Competence)session.get(CompetenceImpl.class,
					primaryKey);

			if (competence == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCompetenceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(competence);
		}
		catch (NoSuchCompetenceException nsee) {
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
	protected Competence removeImpl(Competence competence)
		throws SystemException {
		competence = toUnwrappedModel(competence);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, competence);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(competence);

		return competence;
	}

	@Override
	public Competence updateImpl(com.liferay.lms.model.Competence competence,
		boolean merge) throws SystemException {
		competence = toUnwrappedModel(competence);

		boolean isNew = competence.isNew();

		CompetenceModelImpl competenceModelImpl = (CompetenceModelImpl)competence;

		if (Validator.isNull(competence.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			competence.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, competence, merge);

			competence.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !CompetenceModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((competenceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						competenceModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { competenceModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((competenceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(competenceModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(competenceModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((competenceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(competenceModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(competenceModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		EntityCacheUtil.putResult(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
			CompetenceImpl.class, competence.getPrimaryKey(), competence);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					competence.getUuid(), Long.valueOf(competence.getGroupId())
				}, competence);
		}
		else {
			if ((competenceModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						competenceModelImpl.getOriginalUuid(),
						Long.valueOf(competenceModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						competence.getUuid(),
						Long.valueOf(competence.getGroupId())
					}, competence);
			}
		}

		return competence;
	}

	protected Competence toUnwrappedModel(Competence competence) {
		if (competence instanceof CompetenceImpl) {
			return competence;
		}

		CompetenceImpl competenceImpl = new CompetenceImpl();

		competenceImpl.setNew(competence.isNew());
		competenceImpl.setPrimaryKey(competence.getPrimaryKey());

		competenceImpl.setUuid(competence.getUuid());
		competenceImpl.setCompetenceId(competence.getCompetenceId());
		competenceImpl.setCompanyId(competence.getCompanyId());
		competenceImpl.setGroupId(competence.getGroupId());
		competenceImpl.setUserId(competence.getUserId());
		competenceImpl.setStatus(competence.getStatus());
		competenceImpl.setStatusByUserId(competence.getStatusByUserId());
		competenceImpl.setStatusByUserName(competence.getStatusByUserName());
		competenceImpl.setStatusDate(competence.getStatusDate());
		competenceImpl.setTitle(competence.getTitle());
		competenceImpl.setDescription(competence.getDescription());
		competenceImpl.setPage(competence.getPage());
		competenceImpl.setGenerateCertificate(competence.isGenerateCertificate());
		competenceImpl.setDiplomaTemplate(competence.getDiplomaTemplate());

		return competenceImpl;
	}

	/**
	 * Returns the competence with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the competence
	 * @return the competence
	 * @throws com.liferay.portal.NoSuchModelException if a competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Competence findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the competence with the primary key or throws a {@link com.liferay.lms.NoSuchCompetenceException} if it could not be found.
	 *
	 * @param competenceId the primary key of the competence
	 * @return the competence
	 * @throws com.liferay.lms.NoSuchCompetenceException if a competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence findByPrimaryKey(long competenceId)
		throws NoSuchCompetenceException, SystemException {
		Competence competence = fetchByPrimaryKey(competenceId);

		if (competence == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + competenceId);
			}

			throw new NoSuchCompetenceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				competenceId);
		}

		return competence;
	}

	/**
	 * Returns the competence with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the competence
	 * @return the competence, or <code>null</code> if a competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Competence fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the competence with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param competenceId the primary key of the competence
	 * @return the competence, or <code>null</code> if a competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence fetchByPrimaryKey(long competenceId)
		throws SystemException {
		Competence competence = (Competence)EntityCacheUtil.getResult(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
				CompetenceImpl.class, competenceId);

		if (competence == _nullCompetence) {
			return null;
		}

		if (competence == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				competence = (Competence)session.get(CompetenceImpl.class,
						Long.valueOf(competenceId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (competence != null) {
					cacheResult(competence);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(CompetenceModelImpl.ENTITY_CACHE_ENABLED,
						CompetenceImpl.class, competenceId, _nullCompetence);
				}

				closeSession(session);
			}
		}

		return competence;
	}

	/**
	 * Returns all the competences where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the competences where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of competences
	 * @param end the upper bound of the range of competences (not inclusive)
	 * @return the range of matching competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the competences where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of competences
	 * @param end the upper bound of the range of competences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> findByUuid(String uuid, int start, int end,
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

		List<Competence> list = (List<Competence>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Competence competence : list) {
				if (!Validator.equals(uuid, competence.getUuid())) {
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

			query.append(_SQL_SELECT_COMPETENCE_WHERE);

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

				list = (List<Competence>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first competence in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching competence
	 * @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCompetenceException, SystemException {
		Competence competence = fetchByUuid_First(uuid, orderByComparator);

		if (competence != null) {
			return competence;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCompetenceException(msg.toString());
	}

	/**
	 * Returns the first competence in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching competence, or <code>null</code> if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<Competence> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last competence in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching competence
	 * @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCompetenceException, SystemException {
		Competence competence = fetchByUuid_Last(uuid, orderByComparator);

		if (competence != null) {
			return competence;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCompetenceException(msg.toString());
	}

	/**
	 * Returns the last competence in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching competence, or <code>null</code> if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<Competence> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the competences before and after the current competence in the ordered set where uuid = &#63;.
	 *
	 * @param competenceId the primary key of the current competence
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next competence
	 * @throws com.liferay.lms.NoSuchCompetenceException if a competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence[] findByUuid_PrevAndNext(long competenceId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCompetenceException, SystemException {
		Competence competence = findByPrimaryKey(competenceId);

		Session session = null;

		try {
			session = openSession();

			Competence[] array = new CompetenceImpl[3];

			array[0] = getByUuid_PrevAndNext(session, competence, uuid,
					orderByComparator, true);

			array[1] = competence;

			array[2] = getByUuid_PrevAndNext(session, competence, uuid,
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

	protected Competence getByUuid_PrevAndNext(Session session,
		Competence competence, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMPETENCE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(competence);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Competence> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the competence where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lms.NoSuchCompetenceException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching competence
	 * @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence findByUUID_G(String uuid, long groupId)
		throws NoSuchCompetenceException, SystemException {
		Competence competence = fetchByUUID_G(uuid, groupId);

		if (competence == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCompetenceException(msg.toString());
		}

		return competence;
	}

	/**
	 * Returns the competence where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching competence, or <code>null</code> if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the competence where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching competence, or <code>null</code> if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof Competence) {
			Competence competence = (Competence)result;

			if (!Validator.equals(uuid, competence.getUuid()) ||
					(groupId != competence.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_COMPETENCE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<Competence> list = q.list();

				result = list;

				Competence competence = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					competence = list.get(0);

					cacheResult(competence);

					if ((competence.getUuid() == null) ||
							!competence.getUuid().equals(uuid) ||
							(competence.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, competence);
					}
				}

				return competence;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
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
				return (Competence)result;
			}
		}
	}

	/**
	 * Returns all the competences where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the competences where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of competences
	 * @param end the upper bound of the range of competences (not inclusive)
	 * @return the range of matching competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the competences where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of competences
	 * @param end the upper bound of the range of competences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<Competence> list = (List<Competence>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Competence competence : list) {
				if ((groupId != competence.getGroupId())) {
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

			query.append(_SQL_SELECT_COMPETENCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

				qPos.add(groupId);

				list = (List<Competence>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first competence in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching competence
	 * @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchCompetenceException, SystemException {
		Competence competence = fetchByGroupId_First(groupId, orderByComparator);

		if (competence != null) {
			return competence;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCompetenceException(msg.toString());
	}

	/**
	 * Returns the first competence in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching competence, or <code>null</code> if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Competence> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last competence in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching competence
	 * @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchCompetenceException, SystemException {
		Competence competence = fetchByGroupId_Last(groupId, orderByComparator);

		if (competence != null) {
			return competence;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCompetenceException(msg.toString());
	}

	/**
	 * Returns the last competence in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching competence, or <code>null</code> if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<Competence> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the competences before and after the current competence in the ordered set where groupId = &#63;.
	 *
	 * @param competenceId the primary key of the current competence
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next competence
	 * @throws com.liferay.lms.NoSuchCompetenceException if a competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence[] findByGroupId_PrevAndNext(long competenceId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchCompetenceException, SystemException {
		Competence competence = findByPrimaryKey(competenceId);

		Session session = null;

		try {
			session = openSession();

			Competence[] array = new CompetenceImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, competence, groupId,
					orderByComparator, true);

			array[1] = competence;

			array[2] = getByGroupId_PrevAndNext(session, competence, groupId,
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

	protected Competence getByGroupId_PrevAndNext(Session session,
		Competence competence, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMPETENCE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(competence);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Competence> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the competences that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching competences that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the competences that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of competences
	 * @param end the upper bound of the range of competences (not inclusive)
	 * @return the range of matching competences that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> filterFindByGroupId(long groupId, int start, int end)
		throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the competences that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of competences
	 * @param end the upper bound of the range of competences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching competences that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(2);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMPETENCE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMPETENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMPETENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Competence.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CompetenceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CompetenceImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<Competence>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the competences before and after the current competence in the ordered set of competences that the user has permission to view where groupId = &#63;.
	 *
	 * @param competenceId the primary key of the current competence
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next competence
	 * @throws com.liferay.lms.NoSuchCompetenceException if a competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence[] filterFindByGroupId_PrevAndNext(long competenceId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchCompetenceException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(competenceId, groupId,
				orderByComparator);
		}

		Competence competence = findByPrimaryKey(competenceId);

		Session session = null;

		try {
			session = openSession();

			Competence[] array = new CompetenceImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, competence,
					groupId, orderByComparator, true);

			array[1] = competence;

			array[2] = filterGetByGroupId_PrevAndNext(session, competence,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Competence filterGetByGroupId_PrevAndNext(Session session,
		Competence competence, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMPETENCE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMPETENCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMPETENCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Competence.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CompetenceImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CompetenceImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(competence);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Competence> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the competences where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the competences where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of competences
	 * @param end the upper bound of the range of competences (not inclusive)
	 * @return the range of matching competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the competences where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of competences
	 * @param end the upper bound of the range of competences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> findByCompanyId(long companyId, int start, int end,
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

		List<Competence> list = (List<Competence>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Competence competence : list) {
				if ((companyId != competence.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMPETENCE_WHERE);

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

				list = (List<Competence>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first competence in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching competence
	 * @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCompetenceException, SystemException {
		Competence competence = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (competence != null) {
			return competence;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCompetenceException(msg.toString());
	}

	/**
	 * Returns the first competence in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching competence, or <code>null</code> if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Competence> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last competence in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching competence
	 * @throws com.liferay.lms.NoSuchCompetenceException if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCompetenceException, SystemException {
		Competence competence = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (competence != null) {
			return competence;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCompetenceException(msg.toString());
	}

	/**
	 * Returns the last competence in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching competence, or <code>null</code> if a matching competence could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<Competence> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the competences before and after the current competence in the ordered set where companyId = &#63;.
	 *
	 * @param competenceId the primary key of the current competence
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next competence
	 * @throws com.liferay.lms.NoSuchCompetenceException if a competence with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Competence[] findByCompanyId_PrevAndNext(long competenceId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchCompetenceException, SystemException {
		Competence competence = findByPrimaryKey(competenceId);

		Session session = null;

		try {
			session = openSession();

			Competence[] array = new CompetenceImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, competence,
					companyId, orderByComparator, true);

			array[1] = competence;

			array[2] = getByCompanyId_PrevAndNext(session, competence,
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

	protected Competence getByCompanyId_PrevAndNext(Session session,
		Competence competence, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMPETENCE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(competence);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Competence> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the competences.
	 *
	 * @return the competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the competences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of competences
	 * @param end the upper bound of the range of competences (not inclusive)
	 * @return the range of competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the competences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of competences
	 * @param end the upper bound of the range of competences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of competences
	 * @throws SystemException if a system exception occurred
	 */
	public List<Competence> findAll(int start, int end,
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

		List<Competence> list = (List<Competence>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_COMPETENCE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMPETENCE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Competence>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Competence>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the competences where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (Competence competence : findByUuid(uuid)) {
			remove(competence);
		}
	}

	/**
	 * Removes the competence where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the competence that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Competence removeByUUID_G(String uuid, long groupId)
		throws NoSuchCompetenceException, SystemException {
		Competence competence = findByUUID_G(uuid, groupId);

		return remove(competence);
	}

	/**
	 * Removes all the competences where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (Competence competence : findByGroupId(groupId)) {
			remove(competence);
		}
	}

	/**
	 * Removes all the competences where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (Competence competence : findByCompanyId(companyId)) {
			remove(competence);
		}
	}

	/**
	 * Removes all the competences from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Competence competence : findAll()) {
			remove(competence);
		}
	}

	/**
	 * Returns the number of competences where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching competences
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMPETENCE_WHERE);

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
	 * Returns the number of competences where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching competences
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMPETENCE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of competences where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching competences
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMPETENCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of competences that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching competences that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_COMPETENCE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Competence.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of competences where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching competences
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMPETENCE_WHERE);

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
	 * Returns the number of competences.
	 *
	 * @return the number of competences
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMPETENCE);

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
	 * Initializes the competence persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.Competence")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Competence>> listenersList = new ArrayList<ModelListener<Competence>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Competence>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(CompetenceImpl.class.getName());
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
	private static final String _SQL_SELECT_COMPETENCE = "SELECT competence FROM Competence competence";
	private static final String _SQL_SELECT_COMPETENCE_WHERE = "SELECT competence FROM Competence competence WHERE ";
	private static final String _SQL_COUNT_COMPETENCE = "SELECT COUNT(competence) FROM Competence competence";
	private static final String _SQL_COUNT_COMPETENCE_WHERE = "SELECT COUNT(competence) FROM Competence competence WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "competence.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "competence.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(competence.uuid IS NULL OR competence.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "competence.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "competence.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(competence.uuid IS NULL OR competence.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "competence.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "competence.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "competence.companyId = ?";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "competence.competenceId";
	private static final String _FILTER_SQL_SELECT_COMPETENCE_WHERE = "SELECT DISTINCT {competence.*} FROM Lms_Competence competence WHERE ";
	private static final String _FILTER_SQL_SELECT_COMPETENCE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {Lms_Competence.*} FROM (SELECT DISTINCT competence.competenceId FROM Lms_Competence competence WHERE ";
	private static final String _FILTER_SQL_SELECT_COMPETENCE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN Lms_Competence ON TEMP_TABLE.competenceId = Lms_Competence.competenceId";
	private static final String _FILTER_SQL_COUNT_COMPETENCE_WHERE = "SELECT COUNT(DISTINCT competence.competenceId) AS COUNT_VALUE FROM Lms_Competence competence WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "competence";
	private static final String _FILTER_ENTITY_TABLE = "Lms_Competence";
	private static final String _ORDER_BY_ENTITY_ALIAS = "competence.";
	private static final String _ORDER_BY_ENTITY_TABLE = "Lms_Competence.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Competence exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Competence exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(CompetencePersistenceImpl.class);
	private static Competence _nullCompetence = new CompetenceImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Competence> toCacheModel() {
				return _nullCompetenceCacheModel;
			}
		};

	private static CacheModel<Competence> _nullCompetenceCacheModel = new CacheModel<Competence>() {
			public Competence toEntityModel() {
				return _nullCompetence;
			}
		};
}