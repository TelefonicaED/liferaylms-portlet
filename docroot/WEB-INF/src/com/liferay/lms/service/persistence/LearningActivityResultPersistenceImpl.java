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

import com.liferay.lms.NoSuchLearningActivityResultException;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.impl.LearningActivityResultImpl;
import com.liferay.lms.model.impl.LearningActivityResultModelImpl;

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
import com.liferay.portal.kernel.util.CalendarUtil;
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
import java.util.Date;
import java.util.List;

/**
 * The persistence implementation for the learning activity result service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LearningActivityResultPersistence
 * @see LearningActivityResultUtil
 * @generated
 */
public class LearningActivityResultPersistenceImpl extends BasePersistenceImpl<LearningActivityResult>
	implements LearningActivityResultPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LearningActivityResultUtil} to access the learning activity result persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LearningActivityResultImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			LearningActivityResultModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_ACT_USER = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityResultImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByact_user",
			new String[] { Long.class.getName(), Long.class.getName() },
			LearningActivityResultModelImpl.ACTID_COLUMN_BITMASK |
			LearningActivityResultModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACT_USER = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByact_user",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_AP = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByap",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AP = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByap",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			LearningActivityResultModelImpl.ACTID_COLUMN_BITMASK |
			LearningActivityResultModelImpl.PASSED_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_AP = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByap",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_APD = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByapd",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_APD = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByapd",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Date.class.getName()
			},
			LearningActivityResultModelImpl.ACTID_COLUMN_BITMASK |
			LearningActivityResultModelImpl.PASSED_COLUMN_BITMASK |
			LearningActivityResultModelImpl.ENDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_APD = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByapd",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Date.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_AC = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByac",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AC = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByac",
			new String[] { Long.class.getName() },
			LearningActivityResultModelImpl.ACTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_AC = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByac",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USER = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByuser",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USER = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByuser",
			new String[] { Long.class.getName() },
			LearningActivityResultModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USER = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByuser",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the learning activity result in the entity cache if it is enabled.
	 *
	 * @param learningActivityResult the learning activity result
	 */
	public void cacheResult(LearningActivityResult learningActivityResult) {
		EntityCacheUtil.putResult(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			learningActivityResult.getPrimaryKey(), learningActivityResult);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACT_USER,
			new Object[] {
				Long.valueOf(learningActivityResult.getActId()),
				Long.valueOf(learningActivityResult.getUserId())
			}, learningActivityResult);

		learningActivityResult.resetOriginalValues();
	}

	/**
	 * Caches the learning activity results in the entity cache if it is enabled.
	 *
	 * @param learningActivityResults the learning activity results
	 */
	public void cacheResult(
		List<LearningActivityResult> learningActivityResults) {
		for (LearningActivityResult learningActivityResult : learningActivityResults) {
			if (EntityCacheUtil.getResult(
						LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
						LearningActivityResultImpl.class,
						learningActivityResult.getPrimaryKey()) == null) {
				cacheResult(learningActivityResult);
			}
			else {
				learningActivityResult.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all learning activity results.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LearningActivityResultImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LearningActivityResultImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the learning activity result.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LearningActivityResult learningActivityResult) {
		EntityCacheUtil.removeResult(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			learningActivityResult.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(learningActivityResult);
	}

	@Override
	public void clearCache(List<LearningActivityResult> learningActivityResults) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LearningActivityResult learningActivityResult : learningActivityResults) {
			EntityCacheUtil.removeResult(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
				LearningActivityResultImpl.class,
				learningActivityResult.getPrimaryKey());

			clearUniqueFindersCache(learningActivityResult);
		}
	}

	protected void clearUniqueFindersCache(
		LearningActivityResult learningActivityResult) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ACT_USER,
			new Object[] {
				Long.valueOf(learningActivityResult.getActId()),
				Long.valueOf(learningActivityResult.getUserId())
			});
	}

	/**
	 * Creates a new learning activity result with the primary key. Does not add the learning activity result to the database.
	 *
	 * @param larId the primary key for the new learning activity result
	 * @return the new learning activity result
	 */
	public LearningActivityResult create(long larId) {
		LearningActivityResult learningActivityResult = new LearningActivityResultImpl();

		learningActivityResult.setNew(true);
		learningActivityResult.setPrimaryKey(larId);

		String uuid = PortalUUIDUtil.generate();

		learningActivityResult.setUuid(uuid);

		return learningActivityResult;
	}

	/**
	 * Removes the learning activity result with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param larId the primary key of the learning activity result
	 * @return the learning activity result that was removed
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult remove(long larId)
		throws NoSuchLearningActivityResultException, SystemException {
		return remove(Long.valueOf(larId));
	}

	/**
	 * Removes the learning activity result with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the learning activity result
	 * @return the learning activity result that was removed
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivityResult remove(Serializable primaryKey)
		throws NoSuchLearningActivityResultException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LearningActivityResult learningActivityResult = (LearningActivityResult)session.get(LearningActivityResultImpl.class,
					primaryKey);

			if (learningActivityResult == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLearningActivityResultException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(learningActivityResult);
		}
		catch (NoSuchLearningActivityResultException nsee) {
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
	protected LearningActivityResult removeImpl(
		LearningActivityResult learningActivityResult)
		throws SystemException {
		learningActivityResult = toUnwrappedModel(learningActivityResult);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, learningActivityResult);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(learningActivityResult);

		return learningActivityResult;
	}

	@Override
	public LearningActivityResult updateImpl(
		com.liferay.lms.model.LearningActivityResult learningActivityResult,
		boolean merge) throws SystemException {
		learningActivityResult = toUnwrappedModel(learningActivityResult);

		boolean isNew = learningActivityResult.isNew();

		LearningActivityResultModelImpl learningActivityResultModelImpl = (LearningActivityResultModelImpl)learningActivityResult;

		if (Validator.isNull(learningActivityResult.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			learningActivityResult.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, learningActivityResult, merge);

			learningActivityResult.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !LearningActivityResultModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((learningActivityResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						learningActivityResultModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { learningActivityResultModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((learningActivityResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AP.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityResultModelImpl.getOriginalActId()),
						Boolean.valueOf(learningActivityResultModelImpl.getOriginalPassed())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AP, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AP,
					args);

				args = new Object[] {
						Long.valueOf(learningActivityResultModelImpl.getActId()),
						Boolean.valueOf(learningActivityResultModelImpl.getPassed())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AP, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AP,
					args);
			}

			if ((learningActivityResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_APD.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityResultModelImpl.getOriginalActId()),
						Boolean.valueOf(learningActivityResultModelImpl.getOriginalPassed()),
						
						learningActivityResultModelImpl.getOriginalEndDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_APD, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_APD,
					args);

				args = new Object[] {
						Long.valueOf(learningActivityResultModelImpl.getActId()),
						Boolean.valueOf(learningActivityResultModelImpl.getPassed()),
						
						learningActivityResultModelImpl.getEndDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_APD, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_APD,
					args);
			}

			if ((learningActivityResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AC.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityResultModelImpl.getOriginalActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AC, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AC,
					args);

				args = new Object[] {
						Long.valueOf(learningActivityResultModelImpl.getActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AC, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AC,
					args);
			}

			if ((learningActivityResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USER.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityResultModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USER, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USER,
					args);

				args = new Object[] {
						Long.valueOf(learningActivityResultModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USER, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USER,
					args);
			}
		}

		EntityCacheUtil.putResult(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityResultImpl.class,
			learningActivityResult.getPrimaryKey(), learningActivityResult);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACT_USER,
				new Object[] {
					Long.valueOf(learningActivityResult.getActId()),
					Long.valueOf(learningActivityResult.getUserId())
				}, learningActivityResult);
		}
		else {
			if ((learningActivityResultModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_ACT_USER.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityResultModelImpl.getOriginalActId()),
						Long.valueOf(learningActivityResultModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACT_USER, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ACT_USER, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACT_USER,
					new Object[] {
						Long.valueOf(learningActivityResult.getActId()),
						Long.valueOf(learningActivityResult.getUserId())
					}, learningActivityResult);
			}
		}

		return learningActivityResult;
	}

	protected LearningActivityResult toUnwrappedModel(
		LearningActivityResult learningActivityResult) {
		if (learningActivityResult instanceof LearningActivityResultImpl) {
			return learningActivityResult;
		}

		LearningActivityResultImpl learningActivityResultImpl = new LearningActivityResultImpl();

		learningActivityResultImpl.setNew(learningActivityResult.isNew());
		learningActivityResultImpl.setPrimaryKey(learningActivityResult.getPrimaryKey());

		learningActivityResultImpl.setUuid(learningActivityResult.getUuid());
		learningActivityResultImpl.setLarId(learningActivityResult.getLarId());
		learningActivityResultImpl.setActId(learningActivityResult.getActId());
		learningActivityResultImpl.setUserId(learningActivityResult.getUserId());
		learningActivityResultImpl.setResult(learningActivityResult.getResult());
		learningActivityResultImpl.setStartDate(learningActivityResult.getStartDate());
		learningActivityResultImpl.setEndDate(learningActivityResult.getEndDate());
		learningActivityResultImpl.setLatId(learningActivityResult.getLatId());
		learningActivityResultImpl.setComments(learningActivityResult.getComments());
		learningActivityResultImpl.setPassed(learningActivityResult.isPassed());

		return learningActivityResultImpl;
	}

	/**
	 * Returns the learning activity result with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the learning activity result
	 * @return the learning activity result
	 * @throws com.liferay.portal.NoSuchModelException if a learning activity result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivityResult findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the learning activity result with the primary key or throws a {@link com.liferay.lms.NoSuchLearningActivityResultException} if it could not be found.
	 *
	 * @param larId the primary key of the learning activity result
	 * @return the learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult findByPrimaryKey(long larId)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = fetchByPrimaryKey(larId);

		if (learningActivityResult == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + larId);
			}

			throw new NoSuchLearningActivityResultException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				larId);
		}

		return learningActivityResult;
	}

	/**
	 * Returns the learning activity result with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the learning activity result
	 * @return the learning activity result, or <code>null</code> if a learning activity result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivityResult fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the learning activity result with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param larId the primary key of the learning activity result
	 * @return the learning activity result, or <code>null</code> if a learning activity result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult fetchByPrimaryKey(long larId)
		throws SystemException {
		LearningActivityResult learningActivityResult = (LearningActivityResult)EntityCacheUtil.getResult(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
				LearningActivityResultImpl.class, larId);

		if (learningActivityResult == _nullLearningActivityResult) {
			return null;
		}

		if (learningActivityResult == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				learningActivityResult = (LearningActivityResult)session.get(LearningActivityResultImpl.class,
						Long.valueOf(larId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (learningActivityResult != null) {
					cacheResult(learningActivityResult);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(LearningActivityResultModelImpl.ENTITY_CACHE_ENABLED,
						LearningActivityResultImpl.class, larId,
						_nullLearningActivityResult);
				}

				closeSession(session);
			}
		}

		return learningActivityResult;
	}

	/**
	 * Returns all the learning activity results where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activity results where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of learning activity results
	 * @param end the upper bound of the range of learning activity results (not inclusive)
	 * @return the range of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByUuid(String uuid, int start,
		int end) throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity results where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of learning activity results
	 * @param end the upper bound of the range of learning activity results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByUuid(String uuid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<LearningActivityResult> list = (List<LearningActivityResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivityResult learningActivityResult : list) {
				if (!Validator.equals(uuid, learningActivityResult.getUuid())) {
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LEARNINGACTIVITYRESULT_WHERE);

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

			else {
				query.append(LearningActivityResultModelImpl.ORDER_BY_JPQL);
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

				list = (List<LearningActivityResult>)QueryUtil.list(q,
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
	 * Returns the first learning activity result in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = fetchByUuid_First(uuid,
				orderByComparator);

		if (learningActivityResult != null) {
			return learningActivityResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityResultException(msg.toString());
	}

	/**
	 * Returns the first learning activity result in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivityResult> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity result in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = fetchByUuid_Last(uuid,
				orderByComparator);

		if (learningActivityResult != null) {
			return learningActivityResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityResultException(msg.toString());
	}

	/**
	 * Returns the last learning activity result in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<LearningActivityResult> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activity results before and after the current learning activity result in the ordered set where uuid = &#63;.
	 *
	 * @param larId the primary key of the current learning activity result
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult[] findByUuid_PrevAndNext(long larId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = findByPrimaryKey(larId);

		Session session = null;

		try {
			session = openSession();

			LearningActivityResult[] array = new LearningActivityResultImpl[3];

			array[0] = getByUuid_PrevAndNext(session, learningActivityResult,
					uuid, orderByComparator, true);

			array[1] = learningActivityResult;

			array[2] = getByUuid_PrevAndNext(session, learningActivityResult,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LearningActivityResult getByUuid_PrevAndNext(Session session,
		LearningActivityResult learningActivityResult, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITYRESULT_WHERE);

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

		else {
			query.append(LearningActivityResultModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivityResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivityResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the learning activity result where actId = &#63; and userId = &#63; or throws a {@link com.liferay.lms.NoSuchLearningActivityResultException} if it could not be found.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @return the matching learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult findByact_user(long actId, long userId)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = fetchByact_user(actId,
				userId);

		if (learningActivityResult == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("actId=");
			msg.append(actId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLearningActivityResultException(msg.toString());
		}

		return learningActivityResult;
	}

	/**
	 * Returns the learning activity result where actId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @return the matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult fetchByact_user(long actId, long userId)
		throws SystemException {
		return fetchByact_user(actId, userId, true);
	}

	/**
	 * Returns the learning activity result where actId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult fetchByact_user(long actId, long userId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { actId, userId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_ACT_USER,
					finderArgs, this);
		}

		if (result instanceof LearningActivityResult) {
			LearningActivityResult learningActivityResult = (LearningActivityResult)result;

			if ((actId != learningActivityResult.getActId()) ||
					(userId != learningActivityResult.getUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LEARNINGACTIVITYRESULT_WHERE);

			query.append(_FINDER_COLUMN_ACT_USER_ACTID_2);

			query.append(_FINDER_COLUMN_ACT_USER_USERID_2);

			query.append(LearningActivityResultModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				qPos.add(userId);

				List<LearningActivityResult> list = q.list();

				result = list;

				LearningActivityResult learningActivityResult = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACT_USER,
						finderArgs, list);
				}
				else {
					learningActivityResult = list.get(0);

					cacheResult(learningActivityResult);

					if ((learningActivityResult.getActId() != actId) ||
							(learningActivityResult.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACT_USER,
							finderArgs, learningActivityResult);
					}
				}

				return learningActivityResult;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ACT_USER,
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
				return (LearningActivityResult)result;
			}
		}
	}

	/**
	 * Returns all the learning activity results where actId = &#63; and passed = &#63;.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @return the matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByap(long actId, boolean passed)
		throws SystemException {
		return findByap(actId, passed, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the learning activity results where actId = &#63; and passed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param start the lower bound of the range of learning activity results
	 * @param end the upper bound of the range of learning activity results (not inclusive)
	 * @return the range of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByap(long actId, boolean passed,
		int start, int end) throws SystemException {
		return findByap(actId, passed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity results where actId = &#63; and passed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param start the lower bound of the range of learning activity results
	 * @param end the upper bound of the range of learning activity results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByap(long actId, boolean passed,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AP;
			finderArgs = new Object[] { actId, passed };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_AP;
			finderArgs = new Object[] {
					actId, passed,
					
					start, end, orderByComparator
				};
		}

		List<LearningActivityResult> list = (List<LearningActivityResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivityResult learningActivityResult : list) {
				if ((actId != learningActivityResult.getActId()) ||
						(passed != learningActivityResult.getPassed())) {
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
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LEARNINGACTIVITYRESULT_WHERE);

			query.append(_FINDER_COLUMN_AP_ACTID_2);

			query.append(_FINDER_COLUMN_AP_PASSED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LearningActivityResultModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				qPos.add(passed);

				list = (List<LearningActivityResult>)QueryUtil.list(q,
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
	 * Returns the first learning activity result in the ordered set where actId = &#63; and passed = &#63;.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult findByap_First(long actId, boolean passed,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = fetchByap_First(actId,
				passed, orderByComparator);

		if (learningActivityResult != null) {
			return learningActivityResult;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(", passed=");
		msg.append(passed);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityResultException(msg.toString());
	}

	/**
	 * Returns the first learning activity result in the ordered set where actId = &#63; and passed = &#63;.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult fetchByap_First(long actId, boolean passed,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivityResult> list = findByap(actId, passed, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity result in the ordered set where actId = &#63; and passed = &#63;.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult findByap_Last(long actId, boolean passed,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = fetchByap_Last(actId,
				passed, orderByComparator);

		if (learningActivityResult != null) {
			return learningActivityResult;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(", passed=");
		msg.append(passed);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityResultException(msg.toString());
	}

	/**
	 * Returns the last learning activity result in the ordered set where actId = &#63; and passed = &#63;.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult fetchByap_Last(long actId, boolean passed,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByap(actId, passed);

		List<LearningActivityResult> list = findByap(actId, passed, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activity results before and after the current learning activity result in the ordered set where actId = &#63; and passed = &#63;.
	 *
	 * @param larId the primary key of the current learning activity result
	 * @param actId the act ID
	 * @param passed the passed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult[] findByap_PrevAndNext(long larId,
		long actId, boolean passed, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = findByPrimaryKey(larId);

		Session session = null;

		try {
			session = openSession();

			LearningActivityResult[] array = new LearningActivityResultImpl[3];

			array[0] = getByap_PrevAndNext(session, learningActivityResult,
					actId, passed, orderByComparator, true);

			array[1] = learningActivityResult;

			array[2] = getByap_PrevAndNext(session, learningActivityResult,
					actId, passed, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LearningActivityResult getByap_PrevAndNext(Session session,
		LearningActivityResult learningActivityResult, long actId,
		boolean passed, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITYRESULT_WHERE);

		query.append(_FINDER_COLUMN_AP_ACTID_2);

		query.append(_FINDER_COLUMN_AP_PASSED_2);

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

		else {
			query.append(LearningActivityResultModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(actId);

		qPos.add(passed);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivityResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivityResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activity results where actId = &#63; and passed = &#63; and endDate = &#63;.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param endDate the end date
	 * @return the matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByapd(long actId, boolean passed,
		Date endDate) throws SystemException {
		return findByapd(actId, passed, endDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activity results where actId = &#63; and passed = &#63; and endDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param endDate the end date
	 * @param start the lower bound of the range of learning activity results
	 * @param end the upper bound of the range of learning activity results (not inclusive)
	 * @return the range of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByapd(long actId, boolean passed,
		Date endDate, int start, int end) throws SystemException {
		return findByapd(actId, passed, endDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity results where actId = &#63; and passed = &#63; and endDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param endDate the end date
	 * @param start the lower bound of the range of learning activity results
	 * @param end the upper bound of the range of learning activity results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByapd(long actId, boolean passed,
		Date endDate, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_APD;
			finderArgs = new Object[] { actId, passed, endDate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_APD;
			finderArgs = new Object[] {
					actId, passed, endDate,
					
					start, end, orderByComparator
				};
		}

		List<LearningActivityResult> list = (List<LearningActivityResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivityResult learningActivityResult : list) {
				if ((actId != learningActivityResult.getActId()) ||
						(passed != learningActivityResult.getPassed()) ||
						!Validator.equals(endDate,
							learningActivityResult.getEndDate())) {
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
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LEARNINGACTIVITYRESULT_WHERE);

			query.append(_FINDER_COLUMN_APD_ACTID_2);

			query.append(_FINDER_COLUMN_APD_PASSED_2);

			if (endDate == null) {
				query.append(_FINDER_COLUMN_APD_ENDDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_APD_ENDDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LearningActivityResultModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				qPos.add(passed);

				if (endDate != null) {
					qPos.add(CalendarUtil.getTimestamp(endDate));
				}

				list = (List<LearningActivityResult>)QueryUtil.list(q,
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
	 * Returns the first learning activity result in the ordered set where actId = &#63; and passed = &#63; and endDate = &#63;.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param endDate the end date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult findByapd_First(long actId, boolean passed,
		Date endDate, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = fetchByapd_First(actId,
				passed, endDate, orderByComparator);

		if (learningActivityResult != null) {
			return learningActivityResult;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(", passed=");
		msg.append(passed);

		msg.append(", endDate=");
		msg.append(endDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityResultException(msg.toString());
	}

	/**
	 * Returns the first learning activity result in the ordered set where actId = &#63; and passed = &#63; and endDate = &#63;.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param endDate the end date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult fetchByapd_First(long actId, boolean passed,
		Date endDate, OrderByComparator orderByComparator)
		throws SystemException {
		List<LearningActivityResult> list = findByapd(actId, passed, endDate,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity result in the ordered set where actId = &#63; and passed = &#63; and endDate = &#63;.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param endDate the end date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult findByapd_Last(long actId, boolean passed,
		Date endDate, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = fetchByapd_Last(actId,
				passed, endDate, orderByComparator);

		if (learningActivityResult != null) {
			return learningActivityResult;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(", passed=");
		msg.append(passed);

		msg.append(", endDate=");
		msg.append(endDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityResultException(msg.toString());
	}

	/**
	 * Returns the last learning activity result in the ordered set where actId = &#63; and passed = &#63; and endDate = &#63;.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param endDate the end date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult fetchByapd_Last(long actId, boolean passed,
		Date endDate, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByapd(actId, passed, endDate);

		List<LearningActivityResult> list = findByapd(actId, passed, endDate,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activity results before and after the current learning activity result in the ordered set where actId = &#63; and passed = &#63; and endDate = &#63;.
	 *
	 * @param larId the primary key of the current learning activity result
	 * @param actId the act ID
	 * @param passed the passed
	 * @param endDate the end date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult[] findByapd_PrevAndNext(long larId,
		long actId, boolean passed, Date endDate,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = findByPrimaryKey(larId);

		Session session = null;

		try {
			session = openSession();

			LearningActivityResult[] array = new LearningActivityResultImpl[3];

			array[0] = getByapd_PrevAndNext(session, learningActivityResult,
					actId, passed, endDate, orderByComparator, true);

			array[1] = learningActivityResult;

			array[2] = getByapd_PrevAndNext(session, learningActivityResult,
					actId, passed, endDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LearningActivityResult getByapd_PrevAndNext(Session session,
		LearningActivityResult learningActivityResult, long actId,
		boolean passed, Date endDate, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITYRESULT_WHERE);

		query.append(_FINDER_COLUMN_APD_ACTID_2);

		query.append(_FINDER_COLUMN_APD_PASSED_2);

		if (endDate == null) {
			query.append(_FINDER_COLUMN_APD_ENDDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_APD_ENDDATE_2);
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

		else {
			query.append(LearningActivityResultModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(actId);

		qPos.add(passed);

		if (endDate != null) {
			qPos.add(CalendarUtil.getTimestamp(endDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivityResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivityResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activity results where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByac(long actId)
		throws SystemException {
		return findByac(actId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activity results where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of learning activity results
	 * @param end the upper bound of the range of learning activity results (not inclusive)
	 * @return the range of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByac(long actId, int start, int end)
		throws SystemException {
		return findByac(actId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity results where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of learning activity results
	 * @param end the upper bound of the range of learning activity results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByac(long actId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AC;
			finderArgs = new Object[] { actId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_AC;
			finderArgs = new Object[] { actId, start, end, orderByComparator };
		}

		List<LearningActivityResult> list = (List<LearningActivityResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivityResult learningActivityResult : list) {
				if ((actId != learningActivityResult.getActId())) {
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LEARNINGACTIVITYRESULT_WHERE);

			query.append(_FINDER_COLUMN_AC_ACTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LearningActivityResultModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				list = (List<LearningActivityResult>)QueryUtil.list(q,
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
	 * Returns the first learning activity result in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult findByac_First(long actId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = fetchByac_First(actId,
				orderByComparator);

		if (learningActivityResult != null) {
			return learningActivityResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityResultException(msg.toString());
	}

	/**
	 * Returns the first learning activity result in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult fetchByac_First(long actId,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivityResult> list = findByac(actId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity result in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult findByac_Last(long actId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = fetchByac_Last(actId,
				orderByComparator);

		if (learningActivityResult != null) {
			return learningActivityResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityResultException(msg.toString());
	}

	/**
	 * Returns the last learning activity result in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult fetchByac_Last(long actId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByac(actId);

		List<LearningActivityResult> list = findByac(actId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activity results before and after the current learning activity result in the ordered set where actId = &#63;.
	 *
	 * @param larId the primary key of the current learning activity result
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult[] findByac_PrevAndNext(long larId,
		long actId, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = findByPrimaryKey(larId);

		Session session = null;

		try {
			session = openSession();

			LearningActivityResult[] array = new LearningActivityResultImpl[3];

			array[0] = getByac_PrevAndNext(session, learningActivityResult,
					actId, orderByComparator, true);

			array[1] = learningActivityResult;

			array[2] = getByac_PrevAndNext(session, learningActivityResult,
					actId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LearningActivityResult getByac_PrevAndNext(Session session,
		LearningActivityResult learningActivityResult, long actId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITYRESULT_WHERE);

		query.append(_FINDER_COLUMN_AC_ACTID_2);

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

		else {
			query.append(LearningActivityResultModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(actId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivityResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivityResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activity results where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByuser(long userId)
		throws SystemException {
		return findByuser(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activity results where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of learning activity results
	 * @param end the upper bound of the range of learning activity results (not inclusive)
	 * @return the range of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByuser(long userId, int start,
		int end) throws SystemException {
		return findByuser(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity results where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of learning activity results
	 * @param end the upper bound of the range of learning activity results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findByuser(long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USER;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USER;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<LearningActivityResult> list = (List<LearningActivityResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivityResult learningActivityResult : list) {
				if ((userId != learningActivityResult.getUserId())) {
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LEARNINGACTIVITYRESULT_WHERE);

			query.append(_FINDER_COLUMN_USER_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LearningActivityResultModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<LearningActivityResult>)QueryUtil.list(q,
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
	 * Returns the first learning activity result in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult findByuser_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = fetchByuser_First(userId,
				orderByComparator);

		if (learningActivityResult != null) {
			return learningActivityResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityResultException(msg.toString());
	}

	/**
	 * Returns the first learning activity result in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult fetchByuser_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivityResult> list = findByuser(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity result in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult findByuser_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = fetchByuser_Last(userId,
				orderByComparator);

		if (learningActivityResult != null) {
			return learningActivityResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityResultException(msg.toString());
	}

	/**
	 * Returns the last learning activity result in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity result, or <code>null</code> if a matching learning activity result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult fetchByuser_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByuser(userId);

		List<LearningActivityResult> list = findByuser(userId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activity results before and after the current learning activity result in the ordered set where userId = &#63;.
	 *
	 * @param larId the primary key of the current learning activity result
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity result
	 * @throws com.liferay.lms.NoSuchLearningActivityResultException if a learning activity result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult[] findByuser_PrevAndNext(long larId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = findByPrimaryKey(larId);

		Session session = null;

		try {
			session = openSession();

			LearningActivityResult[] array = new LearningActivityResultImpl[3];

			array[0] = getByuser_PrevAndNext(session, learningActivityResult,
					userId, orderByComparator, true);

			array[1] = learningActivityResult;

			array[2] = getByuser_PrevAndNext(session, learningActivityResult,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LearningActivityResult getByuser_PrevAndNext(Session session,
		LearningActivityResult learningActivityResult, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITYRESULT_WHERE);

		query.append(_FINDER_COLUMN_USER_USERID_2);

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

		else {
			query.append(LearningActivityResultModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivityResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivityResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activity results.
	 *
	 * @return the learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activity results.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of learning activity results
	 * @param end the upper bound of the range of learning activity results (not inclusive)
	 * @return the range of learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity results.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of learning activity results
	 * @param end the upper bound of the range of learning activity results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> findAll(int start, int end,
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

		List<LearningActivityResult> list = (List<LearningActivityResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LEARNINGACTIVITYRESULT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LEARNINGACTIVITYRESULT.concat(LearningActivityResultModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<LearningActivityResult>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LearningActivityResult>)QueryUtil.list(q,
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
	 * Removes all the learning activity results where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (LearningActivityResult learningActivityResult : findByUuid(uuid)) {
			remove(learningActivityResult);
		}
	}

	/**
	 * Removes the learning activity result where actId = &#63; and userId = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @return the learning activity result that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult removeByact_user(long actId, long userId)
		throws NoSuchLearningActivityResultException, SystemException {
		LearningActivityResult learningActivityResult = findByact_user(actId,
				userId);

		return remove(learningActivityResult);
	}

	/**
	 * Removes all the learning activity results where actId = &#63; and passed = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByap(long actId, boolean passed)
		throws SystemException {
		for (LearningActivityResult learningActivityResult : findByap(actId,
				passed)) {
			remove(learningActivityResult);
		}
	}

	/**
	 * Removes all the learning activity results where actId = &#63; and passed = &#63; and endDate = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param endDate the end date
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByapd(long actId, boolean passed, Date endDate)
		throws SystemException {
		for (LearningActivityResult learningActivityResult : findByapd(actId,
				passed, endDate)) {
			remove(learningActivityResult);
		}
	}

	/**
	 * Removes all the learning activity results where actId = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByac(long actId) throws SystemException {
		for (LearningActivityResult learningActivityResult : findByac(actId)) {
			remove(learningActivityResult);
		}
	}

	/**
	 * Removes all the learning activity results where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByuser(long userId) throws SystemException {
		for (LearningActivityResult learningActivityResult : findByuser(userId)) {
			remove(learningActivityResult);
		}
	}

	/**
	 * Removes all the learning activity results from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (LearningActivityResult learningActivityResult : findAll()) {
			remove(learningActivityResult);
		}
	}

	/**
	 * Returns the number of learning activity results where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LEARNINGACTIVITYRESULT_WHERE);

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
	 * Returns the number of learning activity results where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @return the number of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByact_user(long actId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { actId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACT_USER,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LEARNINGACTIVITYRESULT_WHERE);

			query.append(_FINDER_COLUMN_ACT_USER_ACTID_2);

			query.append(_FINDER_COLUMN_ACT_USER_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACT_USER,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of learning activity results where actId = &#63; and passed = &#63;.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @return the number of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByap(long actId, boolean passed) throws SystemException {
		Object[] finderArgs = new Object[] { actId, passed };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_AP,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LEARNINGACTIVITYRESULT_WHERE);

			query.append(_FINDER_COLUMN_AP_ACTID_2);

			query.append(_FINDER_COLUMN_AP_PASSED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_AP, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of learning activity results where actId = &#63; and passed = &#63; and endDate = &#63;.
	 *
	 * @param actId the act ID
	 * @param passed the passed
	 * @param endDate the end date
	 * @return the number of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByapd(long actId, boolean passed, Date endDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { actId, passed, endDate };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_APD,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LEARNINGACTIVITYRESULT_WHERE);

			query.append(_FINDER_COLUMN_APD_ACTID_2);

			query.append(_FINDER_COLUMN_APD_PASSED_2);

			if (endDate == null) {
				query.append(_FINDER_COLUMN_APD_ENDDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_APD_ENDDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				qPos.add(passed);

				if (endDate != null) {
					qPos.add(CalendarUtil.getTimestamp(endDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_APD, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of learning activity results where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the number of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByac(long actId) throws SystemException {
		Object[] finderArgs = new Object[] { actId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_AC,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LEARNINGACTIVITYRESULT_WHERE);

			query.append(_FINDER_COLUMN_AC_ACTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_AC, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of learning activity results where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByuser(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USER,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LEARNINGACTIVITYRESULT_WHERE);

			query.append(_FINDER_COLUMN_USER_USERID_2);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USER,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of learning activity results.
	 *
	 * @return the number of learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LEARNINGACTIVITYRESULT);

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
	 * Initializes the learning activity result persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.LearningActivityResult")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LearningActivityResult>> listenersList = new ArrayList<ModelListener<LearningActivityResult>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LearningActivityResult>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(LearningActivityResultImpl.class.getName());
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
	private static final String _SQL_SELECT_LEARNINGACTIVITYRESULT = "SELECT learningActivityResult FROM LearningActivityResult learningActivityResult";
	private static final String _SQL_SELECT_LEARNINGACTIVITYRESULT_WHERE = "SELECT learningActivityResult FROM LearningActivityResult learningActivityResult WHERE ";
	private static final String _SQL_COUNT_LEARNINGACTIVITYRESULT = "SELECT COUNT(learningActivityResult) FROM LearningActivityResult learningActivityResult";
	private static final String _SQL_COUNT_LEARNINGACTIVITYRESULT_WHERE = "SELECT COUNT(learningActivityResult) FROM LearningActivityResult learningActivityResult WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "learningActivityResult.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "learningActivityResult.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(learningActivityResult.uuid IS NULL OR learningActivityResult.uuid = ?)";
	private static final String _FINDER_COLUMN_ACT_USER_ACTID_2 = "learningActivityResult.actId = ? AND ";
	private static final String _FINDER_COLUMN_ACT_USER_USERID_2 = "learningActivityResult.userId = ?";
	private static final String _FINDER_COLUMN_AP_ACTID_2 = "learningActivityResult.actId = ? AND ";
	private static final String _FINDER_COLUMN_AP_PASSED_2 = "learningActivityResult.passed = ?";
	private static final String _FINDER_COLUMN_APD_ACTID_2 = "learningActivityResult.actId = ? AND ";
	private static final String _FINDER_COLUMN_APD_PASSED_2 = "learningActivityResult.passed = ? AND ";
	private static final String _FINDER_COLUMN_APD_ENDDATE_1 = "learningActivityResult.endDate IS NULL";
	private static final String _FINDER_COLUMN_APD_ENDDATE_2 = "learningActivityResult.endDate = ?";
	private static final String _FINDER_COLUMN_AC_ACTID_2 = "learningActivityResult.actId = ?";
	private static final String _FINDER_COLUMN_USER_USERID_2 = "learningActivityResult.userId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "learningActivityResult.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LearningActivityResult exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LearningActivityResult exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(LearningActivityResultPersistenceImpl.class);
	private static LearningActivityResult _nullLearningActivityResult = new LearningActivityResultImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LearningActivityResult> toCacheModel() {
				return _nullLearningActivityResultCacheModel;
			}
		};

	private static CacheModel<LearningActivityResult> _nullLearningActivityResultCacheModel =
		new CacheModel<LearningActivityResult>() {
			public LearningActivityResult toEntityModel() {
				return _nullLearningActivityResult;
			}
		};
}