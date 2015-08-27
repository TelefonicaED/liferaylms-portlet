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

import com.liferay.lms.NoSuchPrefsException;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.impl.LmsPrefsImpl;
import com.liferay.lms.model.impl.LmsPrefsModelImpl;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
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
 * The persistence implementation for the lms prefs service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LmsPrefsPersistence
 * @see LmsPrefsUtil
 * @generated
 */
public class LmsPrefsPersistenceImpl extends BasePersistenceImpl<LmsPrefs>
	implements LmsPrefsPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LmsPrefsUtil} to access the lms prefs persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LmsPrefsImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LmsPrefsModelImpl.ENTITY_CACHE_ENABLED,
			LmsPrefsModelImpl.FINDER_CACHE_ENABLED, LmsPrefsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LmsPrefsModelImpl.ENTITY_CACHE_ENABLED,
			LmsPrefsModelImpl.FINDER_CACHE_ENABLED, LmsPrefsImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LmsPrefsModelImpl.ENTITY_CACHE_ENABLED,
			LmsPrefsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the lms prefs in the entity cache if it is enabled.
	 *
	 * @param lmsPrefs the lms prefs
	 */
	public void cacheResult(LmsPrefs lmsPrefs) {
		EntityCacheUtil.putResult(LmsPrefsModelImpl.ENTITY_CACHE_ENABLED,
			LmsPrefsImpl.class, lmsPrefs.getPrimaryKey(), lmsPrefs);

		lmsPrefs.resetOriginalValues();
	}

	/**
	 * Caches the lms prefses in the entity cache if it is enabled.
	 *
	 * @param lmsPrefses the lms prefses
	 */
	public void cacheResult(List<LmsPrefs> lmsPrefses) {
		for (LmsPrefs lmsPrefs : lmsPrefses) {
			if (EntityCacheUtil.getResult(
						LmsPrefsModelImpl.ENTITY_CACHE_ENABLED,
						LmsPrefsImpl.class, lmsPrefs.getPrimaryKey()) == null) {
				cacheResult(lmsPrefs);
			}
			else {
				lmsPrefs.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all lms prefses.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LmsPrefsImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LmsPrefsImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the lms prefs.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LmsPrefs lmsPrefs) {
		EntityCacheUtil.removeResult(LmsPrefsModelImpl.ENTITY_CACHE_ENABLED,
			LmsPrefsImpl.class, lmsPrefs.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<LmsPrefs> lmsPrefses) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LmsPrefs lmsPrefs : lmsPrefses) {
			EntityCacheUtil.removeResult(LmsPrefsModelImpl.ENTITY_CACHE_ENABLED,
				LmsPrefsImpl.class, lmsPrefs.getPrimaryKey());
		}
	}

	/**
	 * Creates a new lms prefs with the primary key. Does not add the lms prefs to the database.
	 *
	 * @param companyId the primary key for the new lms prefs
	 * @return the new lms prefs
	 */
	public LmsPrefs create(long companyId) {
		LmsPrefs lmsPrefs = new LmsPrefsImpl();

		lmsPrefs.setNew(true);
		lmsPrefs.setPrimaryKey(companyId);

		return lmsPrefs;
	}

	/**
	 * Removes the lms prefs with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param companyId the primary key of the lms prefs
	 * @return the lms prefs that was removed
	 * @throws com.liferay.lms.NoSuchPrefsException if a lms prefs with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LmsPrefs remove(long companyId)
		throws NoSuchPrefsException, SystemException {
		return remove(Long.valueOf(companyId));
	}

	/**
	 * Removes the lms prefs with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the lms prefs
	 * @return the lms prefs that was removed
	 * @throws com.liferay.lms.NoSuchPrefsException if a lms prefs with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LmsPrefs remove(Serializable primaryKey)
		throws NoSuchPrefsException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LmsPrefs lmsPrefs = (LmsPrefs)session.get(LmsPrefsImpl.class,
					primaryKey);

			if (lmsPrefs == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPrefsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(lmsPrefs);
		}
		catch (NoSuchPrefsException nsee) {
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
	protected LmsPrefs removeImpl(LmsPrefs lmsPrefs) throws SystemException {
		lmsPrefs = toUnwrappedModel(lmsPrefs);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, lmsPrefs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(lmsPrefs);

		return lmsPrefs;
	}

	@Override
	public LmsPrefs updateImpl(com.liferay.lms.model.LmsPrefs lmsPrefs,
		boolean merge) throws SystemException {
		lmsPrefs = toUnwrappedModel(lmsPrefs);

		boolean isNew = lmsPrefs.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, lmsPrefs, merge);

			lmsPrefs.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(LmsPrefsModelImpl.ENTITY_CACHE_ENABLED,
			LmsPrefsImpl.class, lmsPrefs.getPrimaryKey(), lmsPrefs);

		return lmsPrefs;
	}

	protected LmsPrefs toUnwrappedModel(LmsPrefs lmsPrefs) {
		if (lmsPrefs instanceof LmsPrefsImpl) {
			return lmsPrefs;
		}

		LmsPrefsImpl lmsPrefsImpl = new LmsPrefsImpl();

		lmsPrefsImpl.setNew(lmsPrefs.isNew());
		lmsPrefsImpl.setPrimaryKey(lmsPrefs.getPrimaryKey());

		lmsPrefsImpl.setCompanyId(lmsPrefs.getCompanyId());
		lmsPrefsImpl.setTeacherRole(lmsPrefs.getTeacherRole());
		lmsPrefsImpl.setEditorRole(lmsPrefs.getEditorRole());
		lmsPrefsImpl.setLmsTemplates(lmsPrefs.getLmsTemplates());
		lmsPrefsImpl.setActivities(lmsPrefs.getActivities());
		lmsPrefsImpl.setCourseevals(lmsPrefs.getCourseevals());
		lmsPrefsImpl.setScoretranslators(lmsPrefs.getScoretranslators());
		lmsPrefsImpl.setUsersResults(lmsPrefs.getUsersResults());

		return lmsPrefsImpl;
	}

	/**
	 * Returns the lms prefs with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the lms prefs
	 * @return the lms prefs
	 * @throws com.liferay.portal.NoSuchModelException if a lms prefs with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LmsPrefs findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the lms prefs with the primary key or throws a {@link com.liferay.lms.NoSuchPrefsException} if it could not be found.
	 *
	 * @param companyId the primary key of the lms prefs
	 * @return the lms prefs
	 * @throws com.liferay.lms.NoSuchPrefsException if a lms prefs with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LmsPrefs findByPrimaryKey(long companyId)
		throws NoSuchPrefsException, SystemException {
		LmsPrefs lmsPrefs = fetchByPrimaryKey(companyId);

		if (lmsPrefs == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + companyId);
			}

			throw new NoSuchPrefsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				companyId);
		}

		return lmsPrefs;
	}

	/**
	 * Returns the lms prefs with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the lms prefs
	 * @return the lms prefs, or <code>null</code> if a lms prefs with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LmsPrefs fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the lms prefs with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param companyId the primary key of the lms prefs
	 * @return the lms prefs, or <code>null</code> if a lms prefs with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LmsPrefs fetchByPrimaryKey(long companyId) throws SystemException {
		LmsPrefs lmsPrefs = (LmsPrefs)EntityCacheUtil.getResult(LmsPrefsModelImpl.ENTITY_CACHE_ENABLED,
				LmsPrefsImpl.class, companyId);

		if (lmsPrefs == _nullLmsPrefs) {
			return null;
		}

		if (lmsPrefs == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				lmsPrefs = (LmsPrefs)session.get(LmsPrefsImpl.class,
						Long.valueOf(companyId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (lmsPrefs != null) {
					cacheResult(lmsPrefs);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(LmsPrefsModelImpl.ENTITY_CACHE_ENABLED,
						LmsPrefsImpl.class, companyId, _nullLmsPrefs);
				}

				closeSession(session);
			}
		}

		return lmsPrefs;
	}

	/**
	 * Returns all the lms prefses.
	 *
	 * @return the lms prefses
	 * @throws SystemException if a system exception occurred
	 */
	public List<LmsPrefs> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lms prefses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of lms prefses
	 * @param end the upper bound of the range of lms prefses (not inclusive)
	 * @return the range of lms prefses
	 * @throws SystemException if a system exception occurred
	 */
	public List<LmsPrefs> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the lms prefses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of lms prefses
	 * @param end the upper bound of the range of lms prefses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lms prefses
	 * @throws SystemException if a system exception occurred
	 */
	public List<LmsPrefs> findAll(int start, int end,
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

		List<LmsPrefs> list = (List<LmsPrefs>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LMSPREFS);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LMSPREFS;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<LmsPrefs>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LmsPrefs>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the lms prefses from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (LmsPrefs lmsPrefs : findAll()) {
			remove(lmsPrefs);
		}
	}

	/**
	 * Returns the number of lms prefses.
	 *
	 * @return the number of lms prefses
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LMSPREFS);

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
	 * Initializes the lms prefs persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.LmsPrefs")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LmsPrefs>> listenersList = new ArrayList<ModelListener<LmsPrefs>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LmsPrefs>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(LmsPrefsImpl.class.getName());
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
	private static final String _SQL_SELECT_LMSPREFS = "SELECT lmsPrefs FROM LmsPrefs lmsPrefs";
	private static final String _SQL_COUNT_LMSPREFS = "SELECT COUNT(lmsPrefs) FROM LmsPrefs lmsPrefs";
	private static final String _ORDER_BY_ENTITY_ALIAS = "lmsPrefs.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LmsPrefs exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(LmsPrefsPersistenceImpl.class);
	private static LmsPrefs _nullLmsPrefs = new LmsPrefsImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LmsPrefs> toCacheModel() {
				return _nullLmsPrefsCacheModel;
			}
		};

	private static CacheModel<LmsPrefs> _nullLmsPrefsCacheModel = new CacheModel<LmsPrefs>() {
			public LmsPrefs toEntityModel() {
				return _nullLmsPrefs;
			}
		};
}