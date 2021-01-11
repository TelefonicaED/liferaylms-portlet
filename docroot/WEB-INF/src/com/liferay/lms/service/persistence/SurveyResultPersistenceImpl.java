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

import com.liferay.lms.NoSuchSurveyResultException;
import com.liferay.lms.model.SurveyResult;
import com.liferay.lms.model.impl.SurveyResultImpl;
import com.liferay.lms.model.impl.SurveyResultModelImpl;

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
 * The persistence implementation for the survey result service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see SurveyResultPersistence
 * @see SurveyResultUtil
 * @generated
 */
public class SurveyResultPersistenceImpl extends BasePersistenceImpl<SurveyResult>
	implements SurveyResultPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SurveyResultUtil} to access the survey result persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SurveyResultImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			SurveyResultModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			SurveyResultModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTID = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByActId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTID = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByActId",
			new String[] { Long.class.getName() },
			SurveyResultModelImpl.ACTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTID = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByActId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_QUESTIONIDACTID =
		new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByQuestionIdActId",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONIDACTID =
		new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByQuestionIdActId",
			new String[] { Long.class.getName(), Long.class.getName() },
			SurveyResultModelImpl.QUESTIONID_COLUMN_BITMASK |
			SurveyResultModelImpl.ACTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_QUESTIONIDACTID = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByQuestionIdActId",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_QUESTIONID =
		new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByQuestionId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONID =
		new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByQuestionId",
			new String[] { Long.class.getName() },
			SurveyResultModelImpl.QUESTIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_QUESTIONID = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByQuestionId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ANSWERIDQUESTIONID =
		new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAnswerIdQuestionId",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ANSWERIDQUESTIONID =
		new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByAnswerIdQuestionId",
			new String[] { Long.class.getName(), Long.class.getName() },
			SurveyResultModelImpl.ANSWERID_COLUMN_BITMASK |
			SurveyResultModelImpl.QUESTIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ANSWERIDQUESTIONID = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAnswerIdQuestionId",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_QUESTIONIDACTIDLATID = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByQuestionIdActIdLatId",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			SurveyResultModelImpl.QUESTIONID_COLUMN_BITMASK |
			SurveyResultModelImpl.ACTID_COLUMN_BITMASK |
			SurveyResultModelImpl.LATID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_QUESTIONIDACTIDLATID = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByQuestionIdActIdLatId",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, SurveyResultImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the survey result in the entity cache if it is enabled.
	 *
	 * @param surveyResult the survey result
	 */
	public void cacheResult(SurveyResult surveyResult) {
		EntityCacheUtil.putResult(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultImpl.class, surveyResult.getPrimaryKey(), surveyResult);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_QUESTIONIDACTIDLATID,
			new Object[] {
				Long.valueOf(surveyResult.getQuestionId()),
				Long.valueOf(surveyResult.getActId()),
				Long.valueOf(surveyResult.getLatId())
			}, surveyResult);

		surveyResult.resetOriginalValues();
	}

	/**
	 * Caches the survey results in the entity cache if it is enabled.
	 *
	 * @param surveyResults the survey results
	 */
	public void cacheResult(List<SurveyResult> surveyResults) {
		for (SurveyResult surveyResult : surveyResults) {
			if (EntityCacheUtil.getResult(
						SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
						SurveyResultImpl.class, surveyResult.getPrimaryKey()) == null) {
				cacheResult(surveyResult);
			}
			else {
				surveyResult.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all survey results.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SurveyResultImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SurveyResultImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the survey result.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SurveyResult surveyResult) {
		EntityCacheUtil.removeResult(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultImpl.class, surveyResult.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(surveyResult);
	}

	@Override
	public void clearCache(List<SurveyResult> surveyResults) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SurveyResult surveyResult : surveyResults) {
			EntityCacheUtil.removeResult(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
				SurveyResultImpl.class, surveyResult.getPrimaryKey());

			clearUniqueFindersCache(surveyResult);
		}
	}

	protected void clearUniqueFindersCache(SurveyResult surveyResult) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_QUESTIONIDACTIDLATID,
			new Object[] {
				Long.valueOf(surveyResult.getQuestionId()),
				Long.valueOf(surveyResult.getActId()),
				Long.valueOf(surveyResult.getLatId())
			});
	}

	/**
	 * Creates a new survey result with the primary key. Does not add the survey result to the database.
	 *
	 * @param surveyResultId the primary key for the new survey result
	 * @return the new survey result
	 */
	public SurveyResult create(long surveyResultId) {
		SurveyResult surveyResult = new SurveyResultImpl();

		surveyResult.setNew(true);
		surveyResult.setPrimaryKey(surveyResultId);

		String uuid = PortalUUIDUtil.generate();

		surveyResult.setUuid(uuid);

		return surveyResult;
	}

	/**
	 * Removes the survey result with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param surveyResultId the primary key of the survey result
	 * @return the survey result that was removed
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult remove(long surveyResultId)
		throws NoSuchSurveyResultException, SystemException {
		return remove(Long.valueOf(surveyResultId));
	}

	/**
	 * Removes the survey result with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the survey result
	 * @return the survey result that was removed
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SurveyResult remove(Serializable primaryKey)
		throws NoSuchSurveyResultException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SurveyResult surveyResult = (SurveyResult)session.get(SurveyResultImpl.class,
					primaryKey);

			if (surveyResult == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSurveyResultException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(surveyResult);
		}
		catch (NoSuchSurveyResultException nsee) {
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
	protected SurveyResult removeImpl(SurveyResult surveyResult)
		throws SystemException {
		surveyResult = toUnwrappedModel(surveyResult);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, surveyResult);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(surveyResult);

		return surveyResult;
	}

	@Override
	public SurveyResult updateImpl(
		com.liferay.lms.model.SurveyResult surveyResult, boolean merge)
		throws SystemException {
		surveyResult = toUnwrappedModel(surveyResult);

		boolean isNew = surveyResult.isNew();

		SurveyResultModelImpl surveyResultModelImpl = (SurveyResultModelImpl)surveyResult;

		if (Validator.isNull(surveyResult.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			surveyResult.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, surveyResult, merge);

			surveyResult.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SurveyResultModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((surveyResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						surveyResultModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { surveyResultModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((surveyResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(surveyResultModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(surveyResultModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((surveyResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(surveyResultModelImpl.getOriginalActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTID,
					args);

				args = new Object[] {
						Long.valueOf(surveyResultModelImpl.getActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTID,
					args);
			}

			if ((surveyResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONIDACTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(surveyResultModelImpl.getOriginalQuestionId()),
						Long.valueOf(surveyResultModelImpl.getOriginalActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_QUESTIONIDACTID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONIDACTID,
					args);

				args = new Object[] {
						Long.valueOf(surveyResultModelImpl.getQuestionId()),
						Long.valueOf(surveyResultModelImpl.getActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_QUESTIONIDACTID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONIDACTID,
					args);
			}

			if ((surveyResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(surveyResultModelImpl.getOriginalQuestionId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_QUESTIONID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONID,
					args);

				args = new Object[] {
						Long.valueOf(surveyResultModelImpl.getQuestionId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_QUESTIONID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONID,
					args);
			}

			if ((surveyResultModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ANSWERIDQUESTIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(surveyResultModelImpl.getOriginalAnswerId()),
						Long.valueOf(surveyResultModelImpl.getOriginalQuestionId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ANSWERIDQUESTIONID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ANSWERIDQUESTIONID,
					args);

				args = new Object[] {
						Long.valueOf(surveyResultModelImpl.getAnswerId()),
						Long.valueOf(surveyResultModelImpl.getQuestionId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ANSWERIDQUESTIONID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ANSWERIDQUESTIONID,
					args);
			}
		}

		EntityCacheUtil.putResult(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
			SurveyResultImpl.class, surveyResult.getPrimaryKey(), surveyResult);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_QUESTIONIDACTIDLATID,
				new Object[] {
					Long.valueOf(surveyResult.getQuestionId()),
					Long.valueOf(surveyResult.getActId()),
					Long.valueOf(surveyResult.getLatId())
				}, surveyResult);
		}
		else {
			if ((surveyResultModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_QUESTIONIDACTIDLATID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(surveyResultModelImpl.getOriginalQuestionId()),
						Long.valueOf(surveyResultModelImpl.getOriginalActId()),
						Long.valueOf(surveyResultModelImpl.getOriginalLatId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_QUESTIONIDACTIDLATID,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_QUESTIONIDACTIDLATID,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_QUESTIONIDACTIDLATID,
					new Object[] {
						Long.valueOf(surveyResult.getQuestionId()),
						Long.valueOf(surveyResult.getActId()),
						Long.valueOf(surveyResult.getLatId())
					}, surveyResult);
			}
		}

		return surveyResult;
	}

	protected SurveyResult toUnwrappedModel(SurveyResult surveyResult) {
		if (surveyResult instanceof SurveyResultImpl) {
			return surveyResult;
		}

		SurveyResultImpl surveyResultImpl = new SurveyResultImpl();

		surveyResultImpl.setNew(surveyResult.isNew());
		surveyResultImpl.setPrimaryKey(surveyResult.getPrimaryKey());

		surveyResultImpl.setUuid(surveyResult.getUuid());
		surveyResultImpl.setSurveyResultId(surveyResult.getSurveyResultId());
		surveyResultImpl.setActId(surveyResult.getActId());
		surveyResultImpl.setLatId(surveyResult.getLatId());
		surveyResultImpl.setQuestionId(surveyResult.getQuestionId());
		surveyResultImpl.setAnswerId(surveyResult.getAnswerId());
		surveyResultImpl.setUserId(surveyResult.getUserId());
		surveyResultImpl.setFreeAnswer(surveyResult.getFreeAnswer());

		return surveyResultImpl;
	}

	/**
	 * Returns the survey result with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the survey result
	 * @return the survey result
	 * @throws com.liferay.portal.NoSuchModelException if a survey result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SurveyResult findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the survey result with the primary key or throws a {@link com.liferay.lms.NoSuchSurveyResultException} if it could not be found.
	 *
	 * @param surveyResultId the primary key of the survey result
	 * @return the survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByPrimaryKey(long surveyResultId)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByPrimaryKey(surveyResultId);

		if (surveyResult == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + surveyResultId);
			}

			throw new NoSuchSurveyResultException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				surveyResultId);
		}

		return surveyResult;
	}

	/**
	 * Returns the survey result with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the survey result
	 * @return the survey result, or <code>null</code> if a survey result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SurveyResult fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the survey result with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param surveyResultId the primary key of the survey result
	 * @return the survey result, or <code>null</code> if a survey result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByPrimaryKey(long surveyResultId)
		throws SystemException {
		SurveyResult surveyResult = (SurveyResult)EntityCacheUtil.getResult(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
				SurveyResultImpl.class, surveyResultId);

		if (surveyResult == _nullSurveyResult) {
			return null;
		}

		if (surveyResult == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				surveyResult = (SurveyResult)session.get(SurveyResultImpl.class,
						Long.valueOf(surveyResultId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (surveyResult != null) {
					cacheResult(surveyResult);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SurveyResultModelImpl.ENTITY_CACHE_ENABLED,
						SurveyResultImpl.class, surveyResultId,
						_nullSurveyResult);
				}

				closeSession(session);
			}
		}

		return surveyResult;
	}

	/**
	 * Returns all the survey results where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the survey results where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @return the range of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the survey results where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByUuid(String uuid, int start, int end,
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

		List<SurveyResult> list = (List<SurveyResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SurveyResult surveyResult : list) {
				if (!Validator.equals(uuid, surveyResult.getUuid())) {
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

			query.append(_SQL_SELECT_SURVEYRESULT_WHERE);

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

				list = (List<SurveyResult>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first survey result in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByUuid_First(uuid, orderByComparator);

		if (surveyResult != null) {
			return surveyResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSurveyResultException(msg.toString());
	}

	/**
	 * Returns the first survey result in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<SurveyResult> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last survey result in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByUuid_Last(uuid, orderByComparator);

		if (surveyResult != null) {
			return surveyResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSurveyResultException(msg.toString());
	}

	/**
	 * Returns the last survey result in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<SurveyResult> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the survey results before and after the current survey result in the ordered set where uuid = &#63;.
	 *
	 * @param surveyResultId the primary key of the current survey result
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult[] findByUuid_PrevAndNext(long surveyResultId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = findByPrimaryKey(surveyResultId);

		Session session = null;

		try {
			session = openSession();

			SurveyResult[] array = new SurveyResultImpl[3];

			array[0] = getByUuid_PrevAndNext(session, surveyResult, uuid,
					orderByComparator, true);

			array[1] = surveyResult;

			array[2] = getByUuid_PrevAndNext(session, surveyResult, uuid,
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

	protected SurveyResult getByUuid_PrevAndNext(Session session,
		SurveyResult surveyResult, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SURVEYRESULT_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(surveyResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SurveyResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the survey results where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the survey results where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @return the range of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the survey results where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByUserId(long userId, int start, int end,
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

		List<SurveyResult> list = (List<SurveyResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SurveyResult surveyResult : list) {
				if ((userId != surveyResult.getUserId())) {
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

			query.append(_SQL_SELECT_SURVEYRESULT_WHERE);

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

				list = (List<SurveyResult>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first survey result in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByUserId_First(userId,
				orderByComparator);

		if (surveyResult != null) {
			return surveyResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSurveyResultException(msg.toString());
	}

	/**
	 * Returns the first survey result in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SurveyResult> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last survey result in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByUserId_Last(userId, orderByComparator);

		if (surveyResult != null) {
			return surveyResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSurveyResultException(msg.toString());
	}

	/**
	 * Returns the last survey result in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<SurveyResult> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the survey results before and after the current survey result in the ordered set where userId = &#63;.
	 *
	 * @param surveyResultId the primary key of the current survey result
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult[] findByUserId_PrevAndNext(long surveyResultId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = findByPrimaryKey(surveyResultId);

		Session session = null;

		try {
			session = openSession();

			SurveyResult[] array = new SurveyResultImpl[3];

			array[0] = getByUserId_PrevAndNext(session, surveyResult, userId,
					orderByComparator, true);

			array[1] = surveyResult;

			array[2] = getByUserId_PrevAndNext(session, surveyResult, userId,
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

	protected SurveyResult getByUserId_PrevAndNext(Session session,
		SurveyResult surveyResult, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SURVEYRESULT_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(surveyResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SurveyResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the survey results where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByActId(long actId) throws SystemException {
		return findByActId(actId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the survey results where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @return the range of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByActId(long actId, int start, int end)
		throws SystemException {
		return findByActId(actId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the survey results where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByActId(long actId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTID;
			finderArgs = new Object[] { actId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTID;
			finderArgs = new Object[] { actId, start, end, orderByComparator };
		}

		List<SurveyResult> list = (List<SurveyResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SurveyResult surveyResult : list) {
				if ((actId != surveyResult.getActId())) {
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

			query.append(_SQL_SELECT_SURVEYRESULT_WHERE);

			query.append(_FINDER_COLUMN_ACTID_ACTID_2);

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

				qPos.add(actId);

				list = (List<SurveyResult>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first survey result in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByActId_First(long actId,
		OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByActId_First(actId, orderByComparator);

		if (surveyResult != null) {
			return surveyResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSurveyResultException(msg.toString());
	}

	/**
	 * Returns the first survey result in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByActId_First(long actId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SurveyResult> list = findByActId(actId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last survey result in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByActId_Last(long actId,
		OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByActId_Last(actId, orderByComparator);

		if (surveyResult != null) {
			return surveyResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSurveyResultException(msg.toString());
	}

	/**
	 * Returns the last survey result in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByActId_Last(long actId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByActId(actId);

		List<SurveyResult> list = findByActId(actId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the survey results before and after the current survey result in the ordered set where actId = &#63;.
	 *
	 * @param surveyResultId the primary key of the current survey result
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult[] findByActId_PrevAndNext(long surveyResultId,
		long actId, OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = findByPrimaryKey(surveyResultId);

		Session session = null;

		try {
			session = openSession();

			SurveyResult[] array = new SurveyResultImpl[3];

			array[0] = getByActId_PrevAndNext(session, surveyResult, actId,
					orderByComparator, true);

			array[1] = surveyResult;

			array[2] = getByActId_PrevAndNext(session, surveyResult, actId,
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

	protected SurveyResult getByActId_PrevAndNext(Session session,
		SurveyResult surveyResult, long actId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SURVEYRESULT_WHERE);

		query.append(_FINDER_COLUMN_ACTID_ACTID_2);

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

		qPos.add(actId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(surveyResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SurveyResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the survey results where questionId = &#63; and actId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @return the matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByQuestionIdActId(long questionId, long actId)
		throws SystemException {
		return findByQuestionIdActId(questionId, actId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the survey results where questionId = &#63; and actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @return the range of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByQuestionIdActId(long questionId,
		long actId, int start, int end) throws SystemException {
		return findByQuestionIdActId(questionId, actId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the survey results where questionId = &#63; and actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByQuestionIdActId(long questionId,
		long actId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONIDACTID;
			finderArgs = new Object[] { questionId, actId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_QUESTIONIDACTID;
			finderArgs = new Object[] {
					questionId, actId,
					
					start, end, orderByComparator
				};
		}

		List<SurveyResult> list = (List<SurveyResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SurveyResult surveyResult : list) {
				if ((questionId != surveyResult.getQuestionId()) ||
						(actId != surveyResult.getActId())) {
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

			query.append(_SQL_SELECT_SURVEYRESULT_WHERE);

			query.append(_FINDER_COLUMN_QUESTIONIDACTID_QUESTIONID_2);

			query.append(_FINDER_COLUMN_QUESTIONIDACTID_ACTID_2);

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

				qPos.add(questionId);

				qPos.add(actId);

				list = (List<SurveyResult>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first survey result in the ordered set where questionId = &#63; and actId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByQuestionIdActId_First(long questionId,
		long actId, OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByQuestionIdActId_First(questionId,
				actId, orderByComparator);

		if (surveyResult != null) {
			return surveyResult;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("questionId=");
		msg.append(questionId);

		msg.append(", actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSurveyResultException(msg.toString());
	}

	/**
	 * Returns the first survey result in the ordered set where questionId = &#63; and actId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByQuestionIdActId_First(long questionId,
		long actId, OrderByComparator orderByComparator)
		throws SystemException {
		List<SurveyResult> list = findByQuestionIdActId(questionId, actId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last survey result in the ordered set where questionId = &#63; and actId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByQuestionIdActId_Last(long questionId, long actId,
		OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByQuestionIdActId_Last(questionId,
				actId, orderByComparator);

		if (surveyResult != null) {
			return surveyResult;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("questionId=");
		msg.append(questionId);

		msg.append(", actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSurveyResultException(msg.toString());
	}

	/**
	 * Returns the last survey result in the ordered set where questionId = &#63; and actId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByQuestionIdActId_Last(long questionId,
		long actId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByQuestionIdActId(questionId, actId);

		List<SurveyResult> list = findByQuestionIdActId(questionId, actId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the survey results before and after the current survey result in the ordered set where questionId = &#63; and actId = &#63;.
	 *
	 * @param surveyResultId the primary key of the current survey result
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult[] findByQuestionIdActId_PrevAndNext(
		long surveyResultId, long questionId, long actId,
		OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = findByPrimaryKey(surveyResultId);

		Session session = null;

		try {
			session = openSession();

			SurveyResult[] array = new SurveyResultImpl[3];

			array[0] = getByQuestionIdActId_PrevAndNext(session, surveyResult,
					questionId, actId, orderByComparator, true);

			array[1] = surveyResult;

			array[2] = getByQuestionIdActId_PrevAndNext(session, surveyResult,
					questionId, actId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SurveyResult getByQuestionIdActId_PrevAndNext(Session session,
		SurveyResult surveyResult, long questionId, long actId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SURVEYRESULT_WHERE);

		query.append(_FINDER_COLUMN_QUESTIONIDACTID_QUESTIONID_2);

		query.append(_FINDER_COLUMN_QUESTIONIDACTID_ACTID_2);

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

		qPos.add(questionId);

		qPos.add(actId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(surveyResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SurveyResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the survey results where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @return the matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByQuestionId(long questionId)
		throws SystemException {
		return findByQuestionId(questionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the survey results where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @return the range of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByQuestionId(long questionId, int start,
		int end) throws SystemException {
		return findByQuestionId(questionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the survey results where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByQuestionId(long questionId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONID;
			finderArgs = new Object[] { questionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_QUESTIONID;
			finderArgs = new Object[] { questionId, start, end, orderByComparator };
		}

		List<SurveyResult> list = (List<SurveyResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SurveyResult surveyResult : list) {
				if ((questionId != surveyResult.getQuestionId())) {
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

			query.append(_SQL_SELECT_SURVEYRESULT_WHERE);

			query.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

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

				qPos.add(questionId);

				list = (List<SurveyResult>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first survey result in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByQuestionId_First(long questionId,
		OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByQuestionId_First(questionId,
				orderByComparator);

		if (surveyResult != null) {
			return surveyResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("questionId=");
		msg.append(questionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSurveyResultException(msg.toString());
	}

	/**
	 * Returns the first survey result in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByQuestionId_First(long questionId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SurveyResult> list = findByQuestionId(questionId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last survey result in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByQuestionId_Last(long questionId,
		OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByQuestionId_Last(questionId,
				orderByComparator);

		if (surveyResult != null) {
			return surveyResult;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("questionId=");
		msg.append(questionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSurveyResultException(msg.toString());
	}

	/**
	 * Returns the last survey result in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByQuestionId_Last(long questionId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByQuestionId(questionId);

		List<SurveyResult> list = findByQuestionId(questionId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the survey results before and after the current survey result in the ordered set where questionId = &#63;.
	 *
	 * @param surveyResultId the primary key of the current survey result
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult[] findByQuestionId_PrevAndNext(long surveyResultId,
		long questionId, OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = findByPrimaryKey(surveyResultId);

		Session session = null;

		try {
			session = openSession();

			SurveyResult[] array = new SurveyResultImpl[3];

			array[0] = getByQuestionId_PrevAndNext(session, surveyResult,
					questionId, orderByComparator, true);

			array[1] = surveyResult;

			array[2] = getByQuestionId_PrevAndNext(session, surveyResult,
					questionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SurveyResult getByQuestionId_PrevAndNext(Session session,
		SurveyResult surveyResult, long questionId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SURVEYRESULT_WHERE);

		query.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

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

		qPos.add(questionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(surveyResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SurveyResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the survey results where answerId = &#63; and questionId = &#63;.
	 *
	 * @param answerId the answer ID
	 * @param questionId the question ID
	 * @return the matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByAnswerIdQuestionId(long answerId,
		long questionId) throws SystemException {
		return findByAnswerIdQuestionId(answerId, questionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the survey results where answerId = &#63; and questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param answerId the answer ID
	 * @param questionId the question ID
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @return the range of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByAnswerIdQuestionId(long answerId,
		long questionId, int start, int end) throws SystemException {
		return findByAnswerIdQuestionId(answerId, questionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the survey results where answerId = &#63; and questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param answerId the answer ID
	 * @param questionId the question ID
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findByAnswerIdQuestionId(long answerId,
		long questionId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ANSWERIDQUESTIONID;
			finderArgs = new Object[] { answerId, questionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ANSWERIDQUESTIONID;
			finderArgs = new Object[] {
					answerId, questionId,
					
					start, end, orderByComparator
				};
		}

		List<SurveyResult> list = (List<SurveyResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SurveyResult surveyResult : list) {
				if ((answerId != surveyResult.getAnswerId()) ||
						(questionId != surveyResult.getQuestionId())) {
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

			query.append(_SQL_SELECT_SURVEYRESULT_WHERE);

			query.append(_FINDER_COLUMN_ANSWERIDQUESTIONID_ANSWERID_2);

			query.append(_FINDER_COLUMN_ANSWERIDQUESTIONID_QUESTIONID_2);

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

				qPos.add(answerId);

				qPos.add(questionId);

				list = (List<SurveyResult>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first survey result in the ordered set where answerId = &#63; and questionId = &#63;.
	 *
	 * @param answerId the answer ID
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByAnswerIdQuestionId_First(long answerId,
		long questionId, OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByAnswerIdQuestionId_First(answerId,
				questionId, orderByComparator);

		if (surveyResult != null) {
			return surveyResult;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("answerId=");
		msg.append(answerId);

		msg.append(", questionId=");
		msg.append(questionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSurveyResultException(msg.toString());
	}

	/**
	 * Returns the first survey result in the ordered set where answerId = &#63; and questionId = &#63;.
	 *
	 * @param answerId the answer ID
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByAnswerIdQuestionId_First(long answerId,
		long questionId, OrderByComparator orderByComparator)
		throws SystemException {
		List<SurveyResult> list = findByAnswerIdQuestionId(answerId,
				questionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last survey result in the ordered set where answerId = &#63; and questionId = &#63;.
	 *
	 * @param answerId the answer ID
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByAnswerIdQuestionId_Last(long answerId,
		long questionId, OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByAnswerIdQuestionId_Last(answerId,
				questionId, orderByComparator);

		if (surveyResult != null) {
			return surveyResult;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("answerId=");
		msg.append(answerId);

		msg.append(", questionId=");
		msg.append(questionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSurveyResultException(msg.toString());
	}

	/**
	 * Returns the last survey result in the ordered set where answerId = &#63; and questionId = &#63;.
	 *
	 * @param answerId the answer ID
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByAnswerIdQuestionId_Last(long answerId,
		long questionId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByAnswerIdQuestionId(answerId, questionId);

		List<SurveyResult> list = findByAnswerIdQuestionId(answerId,
				questionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the survey results before and after the current survey result in the ordered set where answerId = &#63; and questionId = &#63;.
	 *
	 * @param surveyResultId the primary key of the current survey result
	 * @param answerId the answer ID
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a survey result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult[] findByAnswerIdQuestionId_PrevAndNext(
		long surveyResultId, long answerId, long questionId,
		OrderByComparator orderByComparator)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = findByPrimaryKey(surveyResultId);

		Session session = null;

		try {
			session = openSession();

			SurveyResult[] array = new SurveyResultImpl[3];

			array[0] = getByAnswerIdQuestionId_PrevAndNext(session,
					surveyResult, answerId, questionId, orderByComparator, true);

			array[1] = surveyResult;

			array[2] = getByAnswerIdQuestionId_PrevAndNext(session,
					surveyResult, answerId, questionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SurveyResult getByAnswerIdQuestionId_PrevAndNext(
		Session session, SurveyResult surveyResult, long answerId,
		long questionId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SURVEYRESULT_WHERE);

		query.append(_FINDER_COLUMN_ANSWERIDQUESTIONID_ANSWERID_2);

		query.append(_FINDER_COLUMN_ANSWERIDQUESTIONID_QUESTIONID_2);

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

		qPos.add(answerId);

		qPos.add(questionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(surveyResult);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SurveyResult> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the survey result where questionId = &#63; and actId = &#63; and latId = &#63; or throws a {@link com.liferay.lms.NoSuchSurveyResultException} if it could not be found.
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @param latId the lat ID
	 * @return the matching survey result
	 * @throws com.liferay.lms.NoSuchSurveyResultException if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult findByQuestionIdActIdLatId(long questionId, long actId,
		long latId) throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = fetchByQuestionIdActIdLatId(questionId,
				actId, latId);

		if (surveyResult == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("questionId=");
			msg.append(questionId);

			msg.append(", actId=");
			msg.append(actId);

			msg.append(", latId=");
			msg.append(latId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchSurveyResultException(msg.toString());
		}

		return surveyResult;
	}

	/**
	 * Returns the survey result where questionId = &#63; and actId = &#63; and latId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @param latId the lat ID
	 * @return the matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByQuestionIdActIdLatId(long questionId,
		long actId, long latId) throws SystemException {
		return fetchByQuestionIdActIdLatId(questionId, actId, latId, true);
	}

	/**
	 * Returns the survey result where questionId = &#63; and actId = &#63; and latId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @param latId the lat ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching survey result, or <code>null</code> if a matching survey result could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult fetchByQuestionIdActIdLatId(long questionId,
		long actId, long latId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { questionId, actId, latId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_QUESTIONIDACTIDLATID,
					finderArgs, this);
		}

		if (result instanceof SurveyResult) {
			SurveyResult surveyResult = (SurveyResult)result;

			if ((questionId != surveyResult.getQuestionId()) ||
					(actId != surveyResult.getActId()) ||
					(latId != surveyResult.getLatId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SURVEYRESULT_WHERE);

			query.append(_FINDER_COLUMN_QUESTIONIDACTIDLATID_QUESTIONID_2);

			query.append(_FINDER_COLUMN_QUESTIONIDACTIDLATID_ACTID_2);

			query.append(_FINDER_COLUMN_QUESTIONIDACTIDLATID_LATID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				qPos.add(actId);

				qPos.add(latId);

				List<SurveyResult> list = q.list();

				result = list;

				SurveyResult surveyResult = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_QUESTIONIDACTIDLATID,
						finderArgs, list);
				}
				else {
					surveyResult = list.get(0);

					cacheResult(surveyResult);

					if ((surveyResult.getQuestionId() != questionId) ||
							(surveyResult.getActId() != actId) ||
							(surveyResult.getLatId() != latId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_QUESTIONIDACTIDLATID,
							finderArgs, surveyResult);
					}
				}

				return surveyResult;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_QUESTIONIDACTIDLATID,
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
				return (SurveyResult)result;
			}
		}
	}

	/**
	 * Returns all the survey results.
	 *
	 * @return the survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the survey results.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @return the range of survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the survey results.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of survey results
	 * @param end the upper bound of the range of survey results (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of survey results
	 * @throws SystemException if a system exception occurred
	 */
	public List<SurveyResult> findAll(int start, int end,
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

		List<SurveyResult> list = (List<SurveyResult>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SURVEYRESULT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SURVEYRESULT;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SurveyResult>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SurveyResult>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the survey results where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (SurveyResult surveyResult : findByUuid(uuid)) {
			remove(surveyResult);
		}
	}

	/**
	 * Removes all the survey results where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (SurveyResult surveyResult : findByUserId(userId)) {
			remove(surveyResult);
		}
	}

	/**
	 * Removes all the survey results where actId = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByActId(long actId) throws SystemException {
		for (SurveyResult surveyResult : findByActId(actId)) {
			remove(surveyResult);
		}
	}

	/**
	 * Removes all the survey results where questionId = &#63; and actId = &#63; from the database.
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByQuestionIdActId(long questionId, long actId)
		throws SystemException {
		for (SurveyResult surveyResult : findByQuestionIdActId(questionId, actId)) {
			remove(surveyResult);
		}
	}

	/**
	 * Removes all the survey results where questionId = &#63; from the database.
	 *
	 * @param questionId the question ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByQuestionId(long questionId) throws SystemException {
		for (SurveyResult surveyResult : findByQuestionId(questionId)) {
			remove(surveyResult);
		}
	}

	/**
	 * Removes all the survey results where answerId = &#63; and questionId = &#63; from the database.
	 *
	 * @param answerId the answer ID
	 * @param questionId the question ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByAnswerIdQuestionId(long answerId, long questionId)
		throws SystemException {
		for (SurveyResult surveyResult : findByAnswerIdQuestionId(answerId,
				questionId)) {
			remove(surveyResult);
		}
	}

	/**
	 * Removes the survey result where questionId = &#63; and actId = &#63; and latId = &#63; from the database.
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @param latId the lat ID
	 * @return the survey result that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SurveyResult removeByQuestionIdActIdLatId(long questionId,
		long actId, long latId)
		throws NoSuchSurveyResultException, SystemException {
		SurveyResult surveyResult = findByQuestionIdActIdLatId(questionId,
				actId, latId);

		return remove(surveyResult);
	}

	/**
	 * Removes all the survey results from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SurveyResult surveyResult : findAll()) {
			remove(surveyResult);
		}
	}

	/**
	 * Returns the number of survey results where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SURVEYRESULT_WHERE);

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
	 * Returns the number of survey results where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SURVEYRESULT_WHERE);

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
	 * Returns the number of survey results where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the number of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByActId(long actId) throws SystemException {
		Object[] finderArgs = new Object[] { actId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACTID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SURVEYRESULT_WHERE);

			query.append(_FINDER_COLUMN_ACTID_ACTID_2);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACTID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of survey results where questionId = &#63; and actId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @return the number of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByQuestionIdActId(long questionId, long actId)
		throws SystemException {
		Object[] finderArgs = new Object[] { questionId, actId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_QUESTIONIDACTID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SURVEYRESULT_WHERE);

			query.append(_FINDER_COLUMN_QUESTIONIDACTID_QUESTIONID_2);

			query.append(_FINDER_COLUMN_QUESTIONIDACTID_ACTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_QUESTIONIDACTID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of survey results where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @return the number of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByQuestionId(long questionId) throws SystemException {
		Object[] finderArgs = new Object[] { questionId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_QUESTIONID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SURVEYRESULT_WHERE);

			query.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_QUESTIONID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of survey results where answerId = &#63; and questionId = &#63;.
	 *
	 * @param answerId the answer ID
	 * @param questionId the question ID
	 * @return the number of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByAnswerIdQuestionId(long answerId, long questionId)
		throws SystemException {
		Object[] finderArgs = new Object[] { answerId, questionId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ANSWERIDQUESTIONID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SURVEYRESULT_WHERE);

			query.append(_FINDER_COLUMN_ANSWERIDQUESTIONID_ANSWERID_2);

			query.append(_FINDER_COLUMN_ANSWERIDQUESTIONID_QUESTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(answerId);

				qPos.add(questionId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ANSWERIDQUESTIONID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of survey results where questionId = &#63; and actId = &#63; and latId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param actId the act ID
	 * @param latId the lat ID
	 * @return the number of matching survey results
	 * @throws SystemException if a system exception occurred
	 */
	public int countByQuestionIdActIdLatId(long questionId, long actId,
		long latId) throws SystemException {
		Object[] finderArgs = new Object[] { questionId, actId, latId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_QUESTIONIDACTIDLATID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SURVEYRESULT_WHERE);

			query.append(_FINDER_COLUMN_QUESTIONIDACTIDLATID_QUESTIONID_2);

			query.append(_FINDER_COLUMN_QUESTIONIDACTIDLATID_ACTID_2);

			query.append(_FINDER_COLUMN_QUESTIONIDACTIDLATID_LATID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				qPos.add(actId);

				qPos.add(latId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_QUESTIONIDACTIDLATID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of survey results.
	 *
	 * @return the number of survey results
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SURVEYRESULT);

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
	 * Initializes the survey result persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.SurveyResult")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SurveyResult>> listenersList = new ArrayList<ModelListener<SurveyResult>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SurveyResult>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SurveyResultImpl.class.getName());
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
	private static final String _SQL_SELECT_SURVEYRESULT = "SELECT surveyResult FROM SurveyResult surveyResult";
	private static final String _SQL_SELECT_SURVEYRESULT_WHERE = "SELECT surveyResult FROM SurveyResult surveyResult WHERE ";
	private static final String _SQL_COUNT_SURVEYRESULT = "SELECT COUNT(surveyResult) FROM SurveyResult surveyResult";
	private static final String _SQL_COUNT_SURVEYRESULT_WHERE = "SELECT COUNT(surveyResult) FROM SurveyResult surveyResult WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "surveyResult.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "surveyResult.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(surveyResult.uuid IS NULL OR surveyResult.uuid = ?)";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "surveyResult.userId = ?";
	private static final String _FINDER_COLUMN_ACTID_ACTID_2 = "surveyResult.actId = ?";
	private static final String _FINDER_COLUMN_QUESTIONIDACTID_QUESTIONID_2 = "surveyResult.questionId = ? AND ";
	private static final String _FINDER_COLUMN_QUESTIONIDACTID_ACTID_2 = "surveyResult.actId = ?";
	private static final String _FINDER_COLUMN_QUESTIONID_QUESTIONID_2 = "surveyResult.questionId = ?";
	private static final String _FINDER_COLUMN_ANSWERIDQUESTIONID_ANSWERID_2 = "surveyResult.answerId = ? AND ";
	private static final String _FINDER_COLUMN_ANSWERIDQUESTIONID_QUESTIONID_2 = "surveyResult.questionId = ?";
	private static final String _FINDER_COLUMN_QUESTIONIDACTIDLATID_QUESTIONID_2 =
		"surveyResult.questionId = ? AND ";
	private static final String _FINDER_COLUMN_QUESTIONIDACTIDLATID_ACTID_2 = "surveyResult.actId = ? AND ";
	private static final String _FINDER_COLUMN_QUESTIONIDACTIDLATID_LATID_2 = "surveyResult.latId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "surveyResult.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SurveyResult exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SurveyResult exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(SurveyResultPersistenceImpl.class);
	private static SurveyResult _nullSurveyResult = new SurveyResultImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SurveyResult> toCacheModel() {
				return _nullSurveyResultCacheModel;
			}
		};

	private static CacheModel<SurveyResult> _nullSurveyResultCacheModel = new CacheModel<SurveyResult>() {
			public SurveyResult toEntityModel() {
				return _nullSurveyResult;
			}
		};
}