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

import com.liferay.lms.NoSuchLearningActivityException;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.impl.LearningActivityImpl;
import com.liferay.lms.model.impl.LearningActivityModelImpl;

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
import com.liferay.portal.service.persistence.ResourcePermissionPersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.RolePersistence;
import com.liferay.portal.service.persistence.TeamPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the learning activity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LearningActivityPersistence
 * @see LearningActivityUtil
 * @generated
 */
public class LearningActivityPersistenceImpl extends BasePersistenceImpl<LearningActivity>
	implements LearningActivityPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LearningActivityUtil} to access the learning activity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LearningActivityImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			LearningActivityModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			LearningActivityModelImpl.UUID_COLUMN_BITMASK |
			LearningActivityModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByg",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByg",
			new String[] { Long.class.getName() },
			LearningActivityModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByg",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByg_t",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByg_t",
			new String[] { Long.class.getName(), Integer.class.getName() },
			LearningActivityModelImpl.GROUPID_COLUMN_BITMASK |
			LearningActivityModelImpl.TYPEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByg_t",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_M = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByg_m",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByg_m",
			new String[] { Long.class.getName(), Long.class.getName() },
			LearningActivityModelImpl.GROUPID_COLUMN_BITMASK |
			LearningActivityModelImpl.WEIGHTINMODULE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_M = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByg_m",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_M = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBym",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_M = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBym",
			new String[] { Long.class.getName() },
			LearningActivityModelImpl.MODULEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_M = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBym",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the learning activity in the entity cache if it is enabled.
	 *
	 * @param learningActivity the learning activity
	 */
	public void cacheResult(LearningActivity learningActivity) {
		EntityCacheUtil.putResult(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityImpl.class, learningActivity.getPrimaryKey(),
			learningActivity);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				learningActivity.getUuid(),
				Long.valueOf(learningActivity.getGroupId())
			}, learningActivity);

		learningActivity.resetOriginalValues();
	}

	/**
	 * Caches the learning activities in the entity cache if it is enabled.
	 *
	 * @param learningActivities the learning activities
	 */
	public void cacheResult(List<LearningActivity> learningActivities) {
		for (LearningActivity learningActivity : learningActivities) {
			if (EntityCacheUtil.getResult(
						LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
						LearningActivityImpl.class,
						learningActivity.getPrimaryKey()) == null) {
				cacheResult(learningActivity);
			}
			else {
				learningActivity.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all learning activities.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LearningActivityImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LearningActivityImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the learning activity.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LearningActivity learningActivity) {
		EntityCacheUtil.removeResult(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityImpl.class, learningActivity.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(learningActivity);
	}

	@Override
	public void clearCache(List<LearningActivity> learningActivities) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LearningActivity learningActivity : learningActivities) {
			EntityCacheUtil.removeResult(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
				LearningActivityImpl.class, learningActivity.getPrimaryKey());

			clearUniqueFindersCache(learningActivity);
		}
	}

	protected void clearUniqueFindersCache(LearningActivity learningActivity) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				learningActivity.getUuid(),
				Long.valueOf(learningActivity.getGroupId())
			});
	}

	/**
	 * Creates a new learning activity with the primary key. Does not add the learning activity to the database.
	 *
	 * @param actId the primary key for the new learning activity
	 * @return the new learning activity
	 */
	public LearningActivity create(long actId) {
		LearningActivity learningActivity = new LearningActivityImpl();

		learningActivity.setNew(true);
		learningActivity.setPrimaryKey(actId);

		String uuid = PortalUUIDUtil.generate();

		learningActivity.setUuid(uuid);

		return learningActivity;
	}

	/**
	 * Removes the learning activity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param actId the primary key of the learning activity
	 * @return the learning activity that was removed
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity remove(long actId)
		throws NoSuchLearningActivityException, SystemException {
		return remove(Long.valueOf(actId));
	}

	/**
	 * Removes the learning activity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the learning activity
	 * @return the learning activity that was removed
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivity remove(Serializable primaryKey)
		throws NoSuchLearningActivityException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LearningActivity learningActivity = (LearningActivity)session.get(LearningActivityImpl.class,
					primaryKey);

			if (learningActivity == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLearningActivityException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(learningActivity);
		}
		catch (NoSuchLearningActivityException nsee) {
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
	protected LearningActivity removeImpl(LearningActivity learningActivity)
		throws SystemException {
		learningActivity = toUnwrappedModel(learningActivity);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, learningActivity);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(learningActivity);

		return learningActivity;
	}

	@Override
	public LearningActivity updateImpl(
		com.liferay.lms.model.LearningActivity learningActivity, boolean merge)
		throws SystemException {
		learningActivity = toUnwrappedModel(learningActivity);

		boolean isNew = learningActivity.isNew();

		LearningActivityModelImpl learningActivityModelImpl = (LearningActivityModelImpl)learningActivity;

		if (Validator.isNull(learningActivity.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			learningActivity.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, learningActivity, merge);

			learningActivity.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !LearningActivityModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((learningActivityModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						learningActivityModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { learningActivityModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((learningActivityModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G,
					args);

				args = new Object[] {
						Long.valueOf(learningActivityModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G,
					args);
			}

			if ((learningActivityModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityModelImpl.getOriginalGroupId()),
						Integer.valueOf(learningActivityModelImpl.getOriginalTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);

				args = new Object[] {
						Long.valueOf(learningActivityModelImpl.getGroupId()),
						Integer.valueOf(learningActivityModelImpl.getTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);
			}

			if ((learningActivityModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityModelImpl.getOriginalGroupId()),
						Long.valueOf(learningActivityModelImpl.getOriginalWeightinmodule())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_M, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M,
					args);

				args = new Object[] {
						Long.valueOf(learningActivityModelImpl.getGroupId()),
						Long.valueOf(learningActivityModelImpl.getWeightinmodule())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_M, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M,
					args);
			}

			if ((learningActivityModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_M.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityModelImpl.getOriginalModuleId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_M, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_M,
					args);

				args = new Object[] {
						Long.valueOf(learningActivityModelImpl.getModuleId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_M, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_M,
					args);
			}
		}

		EntityCacheUtil.putResult(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityImpl.class, learningActivity.getPrimaryKey(),
			learningActivity);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					learningActivity.getUuid(),
					Long.valueOf(learningActivity.getGroupId())
				}, learningActivity);
		}
		else {
			if ((learningActivityModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						learningActivityModelImpl.getOriginalUuid(),
						Long.valueOf(learningActivityModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						learningActivity.getUuid(),
						Long.valueOf(learningActivity.getGroupId())
					}, learningActivity);
			}
		}

		return learningActivity;
	}

	protected LearningActivity toUnwrappedModel(
		LearningActivity learningActivity) {
		if (learningActivity instanceof LearningActivityImpl) {
			return learningActivity;
		}

		LearningActivityImpl learningActivityImpl = new LearningActivityImpl();

		learningActivityImpl.setNew(learningActivity.isNew());
		learningActivityImpl.setPrimaryKey(learningActivity.getPrimaryKey());

		learningActivityImpl.setUuid(learningActivity.getUuid());
		learningActivityImpl.setActId(learningActivity.getActId());
		learningActivityImpl.setCompanyId(learningActivity.getCompanyId());
		learningActivityImpl.setUserId(learningActivity.getUserId());
		learningActivityImpl.setGroupId(learningActivity.getGroupId());
		learningActivityImpl.setUserName(learningActivity.getUserName());
		learningActivityImpl.setCreateDate(learningActivity.getCreateDate());
		learningActivityImpl.setModifiedDate(learningActivity.getModifiedDate());
		learningActivityImpl.setStatus(learningActivity.getStatus());
		learningActivityImpl.setStatusByUserId(learningActivity.getStatusByUserId());
		learningActivityImpl.setStatusByUserName(learningActivity.getStatusByUserName());
		learningActivityImpl.setStatusDate(learningActivity.getStatusDate());
		learningActivityImpl.setTitle(learningActivity.getTitle());
		learningActivityImpl.setDescription(learningActivity.getDescription());
		learningActivityImpl.setTypeId(learningActivity.getTypeId());
		learningActivityImpl.setStartdate(learningActivity.getStartdate());
		learningActivityImpl.setEnddate(learningActivity.getEnddate());
		learningActivityImpl.setPrecedence(learningActivity.getPrecedence());
		learningActivityImpl.setTries(learningActivity.getTries());
		learningActivityImpl.setPasspuntuation(learningActivity.getPasspuntuation());
		learningActivityImpl.setPriority(learningActivity.getPriority());
		learningActivityImpl.setModuleId(learningActivity.getModuleId());
		learningActivityImpl.setExtracontent(learningActivity.getExtracontent());
		learningActivityImpl.setFeedbackCorrect(learningActivity.getFeedbackCorrect());
		learningActivityImpl.setFeedbackNoCorrect(learningActivity.getFeedbackNoCorrect());
		learningActivityImpl.setWeightinmodule(learningActivity.getWeightinmodule());

		return learningActivityImpl;
	}

	/**
	 * Returns the learning activity with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the learning activity
	 * @return the learning activity
	 * @throws com.liferay.portal.NoSuchModelException if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the learning activity with the primary key or throws a {@link com.liferay.lms.NoSuchLearningActivityException} if it could not be found.
	 *
	 * @param actId the primary key of the learning activity
	 * @return the learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity findByPrimaryKey(long actId)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = fetchByPrimaryKey(actId);

		if (learningActivity == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + actId);
			}

			throw new NoSuchLearningActivityException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				actId);
		}

		return learningActivity;
	}

	/**
	 * Returns the learning activity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the learning activity
	 * @return the learning activity, or <code>null</code> if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivity fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the learning activity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param actId the primary key of the learning activity
	 * @return the learning activity, or <code>null</code> if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity fetchByPrimaryKey(long actId)
		throws SystemException {
		LearningActivity learningActivity = (LearningActivity)EntityCacheUtil.getResult(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
				LearningActivityImpl.class, actId);

		if (learningActivity == _nullLearningActivity) {
			return null;
		}

		if (learningActivity == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				learningActivity = (LearningActivity)session.get(LearningActivityImpl.class,
						Long.valueOf(actId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (learningActivity != null) {
					cacheResult(learningActivity);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(LearningActivityModelImpl.ENTITY_CACHE_ENABLED,
						LearningActivityImpl.class, actId, _nullLearningActivity);
				}

				closeSession(session);
			}
		}

		return learningActivity;
	}

	/**
	 * Returns all the learning activities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @return the range of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findByUuid(String uuid, int start, int end,
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

		List<LearningActivity> list = (List<LearningActivity>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivity learningActivity : list) {
				if (!Validator.equals(uuid, learningActivity.getUuid())) {
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

			query.append(_SQL_SELECT_LEARNINGACTIVITY_WHERE);

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
				query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
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

				list = (List<LearningActivity>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first learning activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = fetchByUuid_First(uuid,
				orderByComparator);

		if (learningActivity != null) {
			return learningActivity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityException(msg.toString());
	}

	/**
	 * Returns the first learning activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity, or <code>null</code> if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivity> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = fetchByUuid_Last(uuid,
				orderByComparator);

		if (learningActivity != null) {
			return learningActivity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityException(msg.toString());
	}

	/**
	 * Returns the last learning activity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity, or <code>null</code> if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<LearningActivity> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activities before and after the current learning activity in the ordered set where uuid = &#63;.
	 *
	 * @param actId the primary key of the current learning activity
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity[] findByUuid_PrevAndNext(long actId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = findByPrimaryKey(actId);

		Session session = null;

		try {
			session = openSession();

			LearningActivity[] array = new LearningActivityImpl[3];

			array[0] = getByUuid_PrevAndNext(session, learningActivity, uuid,
					orderByComparator, true);

			array[1] = learningActivity;

			array[2] = getByUuid_PrevAndNext(session, learningActivity, uuid,
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

	protected LearningActivity getByUuid_PrevAndNext(Session session,
		LearningActivity learningActivity, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITY_WHERE);

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
			query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivity);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivity> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the learning activity where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lms.NoSuchLearningActivityException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity findByUUID_G(String uuid, long groupId)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = fetchByUUID_G(uuid, groupId);

		if (learningActivity == null) {
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

			throw new NoSuchLearningActivityException(msg.toString());
		}

		return learningActivity;
	}

	/**
	 * Returns the learning activity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching learning activity, or <code>null</code> if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the learning activity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching learning activity, or <code>null</code> if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof LearningActivity) {
			LearningActivity learningActivity = (LearningActivity)result;

			if (!Validator.equals(uuid, learningActivity.getUuid()) ||
					(groupId != learningActivity.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LEARNINGACTIVITY_WHERE);

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

			query.append(LearningActivityModelImpl.ORDER_BY_JPQL);

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

				List<LearningActivity> list = q.list();

				result = list;

				LearningActivity learningActivity = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					learningActivity = list.get(0);

					cacheResult(learningActivity);

					if ((learningActivity.getUuid() == null) ||
							!learningActivity.getUuid().equals(uuid) ||
							(learningActivity.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, learningActivity);
					}
				}

				return learningActivity;
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
				return (LearningActivity)result;
			}
		}
	}

	/**
	 * Returns all the learning activities where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findByg(long groupId)
		throws SystemException {
		return findByg(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activities where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @return the range of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findByg(long groupId, int start, int end)
		throws SystemException {
		return findByg(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activities where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findByg(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<LearningActivity> list = (List<LearningActivity>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivity learningActivity : list) {
				if ((groupId != learningActivity.getGroupId())) {
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

			query.append(_SQL_SELECT_LEARNINGACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<LearningActivity>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first learning activity in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity findByg_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = fetchByg_First(groupId,
				orderByComparator);

		if (learningActivity != null) {
			return learningActivity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityException(msg.toString());
	}

	/**
	 * Returns the first learning activity in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity, or <code>null</code> if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity fetchByg_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivity> list = findByg(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity findByg_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = fetchByg_Last(groupId,
				orderByComparator);

		if (learningActivity != null) {
			return learningActivity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityException(msg.toString());
	}

	/**
	 * Returns the last learning activity in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity, or <code>null</code> if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity fetchByg_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByg(groupId);

		List<LearningActivity> list = findByg(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activities before and after the current learning activity in the ordered set where groupId = &#63;.
	 *
	 * @param actId the primary key of the current learning activity
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity[] findByg_PrevAndNext(long actId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = findByPrimaryKey(actId);

		Session session = null;

		try {
			session = openSession();

			LearningActivity[] array = new LearningActivityImpl[3];

			array[0] = getByg_PrevAndNext(session, learningActivity, groupId,
					orderByComparator, true);

			array[1] = learningActivity;

			array[2] = getByg_PrevAndNext(session, learningActivity, groupId,
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

	protected LearningActivity getByg_PrevAndNext(Session session,
		LearningActivity learningActivity, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITY_WHERE);

		query.append(_FINDER_COLUMN_G_GROUPID_2);

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
			query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivity);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivity> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activities that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching learning activities that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> filterFindByg(long groupId)
		throws SystemException {
		return filterFindByg(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activities that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @return the range of matching learning activities that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> filterFindByg(long groupId, int start, int end)
		throws SystemException {
		return filterFindByg(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activities that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activities that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> filterFindByg(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByg(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LearningActivityModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LearningActivity.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LearningActivityImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LearningActivityImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<LearningActivity>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the learning activities before and after the current learning activity in the ordered set of learning activities that the user has permission to view where groupId = &#63;.
	 *
	 * @param actId the primary key of the current learning activity
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity[] filterFindByg_PrevAndNext(long actId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByg_PrevAndNext(actId, groupId, orderByComparator);
		}

		LearningActivity learningActivity = findByPrimaryKey(actId);

		Session session = null;

		try {
			session = openSession();

			LearningActivity[] array = new LearningActivityImpl[3];

			array[0] = filterGetByg_PrevAndNext(session, learningActivity,
					groupId, orderByComparator, true);

			array[1] = learningActivity;

			array[2] = filterGetByg_PrevAndNext(session, learningActivity,
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

	protected LearningActivity filterGetByg_PrevAndNext(Session session,
		LearningActivity learningActivity, long groupId,
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
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LearningActivityModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LearningActivity.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LearningActivityImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LearningActivityImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivity);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivity> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activities where groupId = &#63; and typeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @return the matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findByg_t(long groupId, int typeId)
		throws SystemException {
		return findByg_t(groupId, typeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the learning activities where groupId = &#63; and typeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @return the range of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findByg_t(long groupId, int typeId,
		int start, int end) throws SystemException {
		return findByg_t(groupId, typeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activities where groupId = &#63; and typeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findByg_t(long groupId, int typeId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] { groupId, typeId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] {
					groupId, typeId,
					
					start, end, orderByComparator
				};
		}

		List<LearningActivity> list = (List<LearningActivity>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivity learningActivity : list) {
				if ((groupId != learningActivity.getGroupId()) ||
						(typeId != learningActivity.getTypeId())) {
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

			query.append(_SQL_SELECT_LEARNINGACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(typeId);

				list = (List<LearningActivity>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first learning activity in the ordered set where groupId = &#63; and typeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity findByg_t_First(long groupId, int typeId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = fetchByg_t_First(groupId, typeId,
				orderByComparator);

		if (learningActivity != null) {
			return learningActivity;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", typeId=");
		msg.append(typeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityException(msg.toString());
	}

	/**
	 * Returns the first learning activity in the ordered set where groupId = &#63; and typeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity, or <code>null</code> if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity fetchByg_t_First(long groupId, int typeId,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivity> list = findByg_t(groupId, typeId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity in the ordered set where groupId = &#63; and typeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity findByg_t_Last(long groupId, int typeId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = fetchByg_t_Last(groupId, typeId,
				orderByComparator);

		if (learningActivity != null) {
			return learningActivity;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", typeId=");
		msg.append(typeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityException(msg.toString());
	}

	/**
	 * Returns the last learning activity in the ordered set where groupId = &#63; and typeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity, or <code>null</code> if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity fetchByg_t_Last(long groupId, int typeId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByg_t(groupId, typeId);

		List<LearningActivity> list = findByg_t(groupId, typeId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activities before and after the current learning activity in the ordered set where groupId = &#63; and typeId = &#63;.
	 *
	 * @param actId the primary key of the current learning activity
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity[] findByg_t_PrevAndNext(long actId, long groupId,
		int typeId, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = findByPrimaryKey(actId);

		Session session = null;

		try {
			session = openSession();

			LearningActivity[] array = new LearningActivityImpl[3];

			array[0] = getByg_t_PrevAndNext(session, learningActivity, groupId,
					typeId, orderByComparator, true);

			array[1] = learningActivity;

			array[2] = getByg_t_PrevAndNext(session, learningActivity, groupId,
					typeId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LearningActivity getByg_t_PrevAndNext(Session session,
		LearningActivity learningActivity, long groupId, int typeId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITY_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPEID_2);

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
			query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(typeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivity);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivity> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activities that the user has permission to view where groupId = &#63; and typeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @return the matching learning activities that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> filterFindByg_t(long groupId, int typeId)
		throws SystemException {
		return filterFindByg_t(groupId, typeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activities that the user has permission to view where groupId = &#63; and typeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @return the range of matching learning activities that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> filterFindByg_t(long groupId, int typeId,
		int start, int end) throws SystemException {
		return filterFindByg_t(groupId, typeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activities that the user has permissions to view where groupId = &#63; and typeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activities that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> filterFindByg_t(long groupId, int typeId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByg_t(groupId, typeId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LearningActivityModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LearningActivity.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LearningActivityImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LearningActivityImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(typeId);

			return (List<LearningActivity>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the learning activities before and after the current learning activity in the ordered set of learning activities that the user has permission to view where groupId = &#63; and typeId = &#63;.
	 *
	 * @param actId the primary key of the current learning activity
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity[] filterFindByg_t_PrevAndNext(long actId,
		long groupId, int typeId, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByg_t_PrevAndNext(actId, groupId, typeId,
				orderByComparator);
		}

		LearningActivity learningActivity = findByPrimaryKey(actId);

		Session session = null;

		try {
			session = openSession();

			LearningActivity[] array = new LearningActivityImpl[3];

			array[0] = filterGetByg_t_PrevAndNext(session, learningActivity,
					groupId, typeId, orderByComparator, true);

			array[1] = learningActivity;

			array[2] = filterGetByg_t_PrevAndNext(session, learningActivity,
					groupId, typeId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LearningActivity filterGetByg_t_PrevAndNext(Session session,
		LearningActivity learningActivity, long groupId, int typeId,
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
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LearningActivityModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LearningActivity.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LearningActivityImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LearningActivityImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(typeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivity);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivity> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activities where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @return the matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findByg_m(long groupId, long weightinmodule)
		throws SystemException {
		return findByg_m(groupId, weightinmodule, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activities where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @return the range of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findByg_m(long groupId, long weightinmodule,
		int start, int end) throws SystemException {
		return findByg_m(groupId, weightinmodule, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activities where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findByg_m(long groupId, long weightinmodule,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_M;
			finderArgs = new Object[] { groupId, weightinmodule };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_M;
			finderArgs = new Object[] {
					groupId, weightinmodule,
					
					start, end, orderByComparator
				};
		}

		List<LearningActivity> list = (List<LearningActivity>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivity learningActivity : list) {
				if ((groupId != learningActivity.getGroupId()) ||
						(weightinmodule != learningActivity.getWeightinmodule())) {
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

			query.append(_SQL_SELECT_LEARNINGACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_G_M_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_WEIGHTINMODULE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(weightinmodule);

				list = (List<LearningActivity>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first learning activity in the ordered set where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity findByg_m_First(long groupId, long weightinmodule,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = fetchByg_m_First(groupId,
				weightinmodule, orderByComparator);

		if (learningActivity != null) {
			return learningActivity;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", weightinmodule=");
		msg.append(weightinmodule);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityException(msg.toString());
	}

	/**
	 * Returns the first learning activity in the ordered set where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity, or <code>null</code> if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity fetchByg_m_First(long groupId, long weightinmodule,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivity> list = findByg_m(groupId, weightinmodule, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity in the ordered set where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity findByg_m_Last(long groupId, long weightinmodule,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = fetchByg_m_Last(groupId,
				weightinmodule, orderByComparator);

		if (learningActivity != null) {
			return learningActivity;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", weightinmodule=");
		msg.append(weightinmodule);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityException(msg.toString());
	}

	/**
	 * Returns the last learning activity in the ordered set where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity, or <code>null</code> if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity fetchByg_m_Last(long groupId, long weightinmodule,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByg_m(groupId, weightinmodule);

		List<LearningActivity> list = findByg_m(groupId, weightinmodule,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activities before and after the current learning activity in the ordered set where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * @param actId the primary key of the current learning activity
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity[] findByg_m_PrevAndNext(long actId, long groupId,
		long weightinmodule, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = findByPrimaryKey(actId);

		Session session = null;

		try {
			session = openSession();

			LearningActivity[] array = new LearningActivityImpl[3];

			array[0] = getByg_m_PrevAndNext(session, learningActivity, groupId,
					weightinmodule, orderByComparator, true);

			array[1] = learningActivity;

			array[2] = getByg_m_PrevAndNext(session, learningActivity, groupId,
					weightinmodule, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LearningActivity getByg_m_PrevAndNext(Session session,
		LearningActivity learningActivity, long groupId, long weightinmodule,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITY_WHERE);

		query.append(_FINDER_COLUMN_G_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_WEIGHTINMODULE_2);

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
			query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(weightinmodule);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivity);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivity> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activities that the user has permission to view where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @return the matching learning activities that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> filterFindByg_m(long groupId,
		long weightinmodule) throws SystemException {
		return filterFindByg_m(groupId, weightinmodule, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activities that the user has permission to view where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @return the range of matching learning activities that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> filterFindByg_m(long groupId,
		long weightinmodule, int start, int end) throws SystemException {
		return filterFindByg_m(groupId, weightinmodule, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activities that the user has permissions to view where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activities that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> filterFindByg_m(long groupId,
		long weightinmodule, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByg_m(groupId, weightinmodule, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_WEIGHTINMODULE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LearningActivityModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LearningActivity.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LearningActivityImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LearningActivityImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(weightinmodule);

			return (List<LearningActivity>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the learning activities before and after the current learning activity in the ordered set of learning activities that the user has permission to view where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * @param actId the primary key of the current learning activity
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity[] filterFindByg_m_PrevAndNext(long actId,
		long groupId, long weightinmodule, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByg_m_PrevAndNext(actId, groupId, weightinmodule,
				orderByComparator);
		}

		LearningActivity learningActivity = findByPrimaryKey(actId);

		Session session = null;

		try {
			session = openSession();

			LearningActivity[] array = new LearningActivityImpl[3];

			array[0] = filterGetByg_m_PrevAndNext(session, learningActivity,
					groupId, weightinmodule, orderByComparator, true);

			array[1] = learningActivity;

			array[2] = filterGetByg_m_PrevAndNext(session, learningActivity,
					groupId, weightinmodule, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LearningActivity filterGetByg_m_PrevAndNext(Session session,
		LearningActivity learningActivity, long groupId, long weightinmodule,
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
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_WEIGHTINMODULE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_2);
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

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LearningActivityModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LearningActivity.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LearningActivityImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LearningActivityImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(weightinmodule);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivity);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivity> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activities where moduleId = &#63;.
	 *
	 * @param moduleId the module ID
	 * @return the matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findBym(long moduleId)
		throws SystemException {
		return findBym(moduleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activities where moduleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param moduleId the module ID
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @return the range of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findBym(long moduleId, int start, int end)
		throws SystemException {
		return findBym(moduleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activities where moduleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param moduleId the module ID
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findBym(long moduleId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_M;
			finderArgs = new Object[] { moduleId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_M;
			finderArgs = new Object[] { moduleId, start, end, orderByComparator };
		}

		List<LearningActivity> list = (List<LearningActivity>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivity learningActivity : list) {
				if ((moduleId != learningActivity.getModuleId())) {
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

			query.append(_SQL_SELECT_LEARNINGACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_M_MODULEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(moduleId);

				list = (List<LearningActivity>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first learning activity in the ordered set where moduleId = &#63;.
	 *
	 * @param moduleId the module ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity findBym_First(long moduleId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = fetchBym_First(moduleId,
				orderByComparator);

		if (learningActivity != null) {
			return learningActivity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("moduleId=");
		msg.append(moduleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityException(msg.toString());
	}

	/**
	 * Returns the first learning activity in the ordered set where moduleId = &#63;.
	 *
	 * @param moduleId the module ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity, or <code>null</code> if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity fetchBym_First(long moduleId,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivity> list = findBym(moduleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity in the ordered set where moduleId = &#63;.
	 *
	 * @param moduleId the module ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity findBym_Last(long moduleId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = fetchBym_Last(moduleId,
				orderByComparator);

		if (learningActivity != null) {
			return learningActivity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("moduleId=");
		msg.append(moduleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityException(msg.toString());
	}

	/**
	 * Returns the last learning activity in the ordered set where moduleId = &#63;.
	 *
	 * @param moduleId the module ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity, or <code>null</code> if a matching learning activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity fetchBym_Last(long moduleId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countBym(moduleId);

		List<LearningActivity> list = findBym(moduleId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activities before and after the current learning activity in the ordered set where moduleId = &#63;.
	 *
	 * @param actId the primary key of the current learning activity
	 * @param moduleId the module ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity
	 * @throws com.liferay.lms.NoSuchLearningActivityException if a learning activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity[] findBym_PrevAndNext(long actId, long moduleId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = findByPrimaryKey(actId);

		Session session = null;

		try {
			session = openSession();

			LearningActivity[] array = new LearningActivityImpl[3];

			array[0] = getBym_PrevAndNext(session, learningActivity, moduleId,
					orderByComparator, true);

			array[1] = learningActivity;

			array[2] = getBym_PrevAndNext(session, learningActivity, moduleId,
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

	protected LearningActivity getBym_PrevAndNext(Session session,
		LearningActivity learningActivity, long moduleId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITY_WHERE);

		query.append(_FINDER_COLUMN_M_MODULEID_2);

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
			query.append(LearningActivityModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(moduleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivity);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivity> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activities.
	 *
	 * @return the learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @return the range of learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of learning activities
	 * @param end the upper bound of the range of learning activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivity> findAll(int start, int end,
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

		List<LearningActivity> list = (List<LearningActivity>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LEARNINGACTIVITY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LEARNINGACTIVITY.concat(LearningActivityModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<LearningActivity>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LearningActivity>)QueryUtil.list(q,
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
	 * Removes all the learning activities where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (LearningActivity learningActivity : findByUuid(uuid)) {
			remove(learningActivity);
		}
	}

	/**
	 * Removes the learning activity where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the learning activity that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivity removeByUUID_G(String uuid, long groupId)
		throws NoSuchLearningActivityException, SystemException {
		LearningActivity learningActivity = findByUUID_G(uuid, groupId);

		return remove(learningActivity);
	}

	/**
	 * Removes all the learning activities where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByg(long groupId) throws SystemException {
		for (LearningActivity learningActivity : findByg(groupId)) {
			remove(learningActivity);
		}
	}

	/**
	 * Removes all the learning activities where groupId = &#63; and typeId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByg_t(long groupId, int typeId) throws SystemException {
		for (LearningActivity learningActivity : findByg_t(groupId, typeId)) {
			remove(learningActivity);
		}
	}

	/**
	 * Removes all the learning activities where groupId = &#63; and weightinmodule = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByg_m(long groupId, long weightinmodule)
		throws SystemException {
		for (LearningActivity learningActivity : findByg_m(groupId,
				weightinmodule)) {
			remove(learningActivity);
		}
	}

	/**
	 * Removes all the learning activities where moduleId = &#63; from the database.
	 *
	 * @param moduleId the module ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeBym(long moduleId) throws SystemException {
		for (LearningActivity learningActivity : findBym(moduleId)) {
			remove(learningActivity);
		}
	}

	/**
	 * Removes all the learning activities from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (LearningActivity learningActivity : findAll()) {
			remove(learningActivity);
		}
	}

	/**
	 * Returns the number of learning activities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LEARNINGACTIVITY_WHERE);

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
	 * Returns the number of learning activities where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LEARNINGACTIVITY_WHERE);

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
	 * Returns the number of learning activities where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countByg(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LEARNINGACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_G_GROUPID_2);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of learning activities that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching learning activities that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByg(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByg(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_LEARNINGACTIVITY_WHERE);

		query.append(_FINDER_COLUMN_G_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LearningActivity.class.getName(),
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
	 * Returns the number of learning activities where groupId = &#63; and typeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @return the number of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countByg_t(long groupId, int typeId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, typeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LEARNINGACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(typeId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of learning activities that the user has permission to view where groupId = &#63; and typeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param typeId the type ID
	 * @return the number of matching learning activities that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByg_t(long groupId, int typeId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByg_t(groupId, typeId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LEARNINGACTIVITY_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPEID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LearningActivity.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(typeId);

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
	 * Returns the number of learning activities where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @return the number of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countByg_m(long groupId, long weightinmodule)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, weightinmodule };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_M,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LEARNINGACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_G_M_GROUPID_2);

			query.append(_FINDER_COLUMN_G_M_WEIGHTINMODULE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(weightinmodule);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_M, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of learning activities that the user has permission to view where groupId = &#63; and weightinmodule = &#63;.
	 *
	 * @param groupId the group ID
	 * @param weightinmodule the weightinmodule
	 * @return the number of matching learning activities that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByg_m(long groupId, long weightinmodule)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByg_m(groupId, weightinmodule);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LEARNINGACTIVITY_WHERE);

		query.append(_FINDER_COLUMN_G_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_M_WEIGHTINMODULE_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LearningActivity.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(weightinmodule);

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
	 * Returns the number of learning activities where moduleId = &#63;.
	 *
	 * @param moduleId the module ID
	 * @return the number of matching learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countBym(long moduleId) throws SystemException {
		Object[] finderArgs = new Object[] { moduleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_M,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LEARNINGACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_M_MODULEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(moduleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_M, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of learning activities.
	 *
	 * @return the number of learning activities
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LEARNINGACTIVITY);

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
	 * Initializes the learning activity persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.LearningActivity")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LearningActivity>> listenersList = new ArrayList<ModelListener<LearningActivity>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LearningActivity>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(LearningActivityImpl.class.getName());
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
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	private static final String _SQL_SELECT_LEARNINGACTIVITY = "SELECT learningActivity FROM LearningActivity learningActivity";
	private static final String _SQL_SELECT_LEARNINGACTIVITY_WHERE = "SELECT learningActivity FROM LearningActivity learningActivity WHERE ";
	private static final String _SQL_COUNT_LEARNINGACTIVITY = "SELECT COUNT(learningActivity) FROM LearningActivity learningActivity";
	private static final String _SQL_COUNT_LEARNINGACTIVITY_WHERE = "SELECT COUNT(learningActivity) FROM LearningActivity learningActivity WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "learningActivity.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "learningActivity.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(learningActivity.uuid IS NULL OR learningActivity.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "learningActivity.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "learningActivity.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(learningActivity.uuid IS NULL OR learningActivity.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "learningActivity.groupId = ?";
	private static final String _FINDER_COLUMN_G_GROUPID_2 = "learningActivity.groupId = ?";
	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "learningActivity.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TYPEID_2 = "learningActivity.typeId = ?";
	private static final String _FINDER_COLUMN_G_M_GROUPID_2 = "learningActivity.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_M_WEIGHTINMODULE_2 = "learningActivity.weightinmodule = ?";
	private static final String _FINDER_COLUMN_M_MODULEID_2 = "learningActivity.moduleId = ?";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "learningActivity.actId";
	private static final String _FILTER_SQL_SELECT_LEARNINGACTIVITY_WHERE = "SELECT DISTINCT {learningActivity.*} FROM Lms_LearningActivity learningActivity WHERE ";
	private static final String _FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {Lms_LearningActivity.*} FROM (SELECT DISTINCT learningActivity.actId FROM Lms_LearningActivity learningActivity WHERE ";
	private static final String _FILTER_SQL_SELECT_LEARNINGACTIVITY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN Lms_LearningActivity ON TEMP_TABLE.actId = Lms_LearningActivity.actId";
	private static final String _FILTER_SQL_COUNT_LEARNINGACTIVITY_WHERE = "SELECT COUNT(DISTINCT learningActivity.actId) AS COUNT_VALUE FROM Lms_LearningActivity learningActivity WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "learningActivity";
	private static final String _FILTER_ENTITY_TABLE = "Lms_LearningActivity";
	private static final String _ORDER_BY_ENTITY_ALIAS = "learningActivity.";
	private static final String _ORDER_BY_ENTITY_TABLE = "Lms_LearningActivity.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LearningActivity exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LearningActivity exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(LearningActivityPersistenceImpl.class);
	private static LearningActivity _nullLearningActivity = new LearningActivityImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LearningActivity> toCacheModel() {
				return _nullLearningActivityCacheModel;
			}
		};

	private static CacheModel<LearningActivity> _nullLearningActivityCacheModel = new CacheModel<LearningActivity>() {
			public LearningActivity toEntityModel() {
				return _nullLearningActivity;
			}
		};
}