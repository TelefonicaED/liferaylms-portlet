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

import com.liferay.lms.NoSuchUserCertificateDownloadException;
import com.liferay.lms.model.UserCertificateDownload;
import com.liferay.lms.model.impl.UserCertificateDownloadImpl;
import com.liferay.lms.model.impl.UserCertificateDownloadModelImpl;

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
 * The persistence implementation for the user certificate download service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see UserCertificateDownloadPersistence
 * @see UserCertificateDownloadUtil
 * @generated
 */
public class UserCertificateDownloadPersistenceImpl extends BasePersistenceImpl<UserCertificateDownload>
	implements UserCertificateDownloadPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link UserCertificateDownloadUtil} to access the user certificate download persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = UserCertificateDownloadImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(UserCertificateDownloadModelImpl.ENTITY_CACHE_ENABLED,
			UserCertificateDownloadModelImpl.FINDER_CACHE_ENABLED,
			UserCertificateDownloadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(UserCertificateDownloadModelImpl.ENTITY_CACHE_ENABLED,
			UserCertificateDownloadModelImpl.FINDER_CACHE_ENABLED,
			UserCertificateDownloadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserCertificateDownloadModelImpl.ENTITY_CACHE_ENABLED,
			UserCertificateDownloadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the user certificate download in the entity cache if it is enabled.
	 *
	 * @param userCertificateDownload the user certificate download
	 */
	public void cacheResult(UserCertificateDownload userCertificateDownload) {
		EntityCacheUtil.putResult(UserCertificateDownloadModelImpl.ENTITY_CACHE_ENABLED,
			UserCertificateDownloadImpl.class,
			userCertificateDownload.getPrimaryKey(), userCertificateDownload);

		userCertificateDownload.resetOriginalValues();
	}

	/**
	 * Caches the user certificate downloads in the entity cache if it is enabled.
	 *
	 * @param userCertificateDownloads the user certificate downloads
	 */
	public void cacheResult(
		List<UserCertificateDownload> userCertificateDownloads) {
		for (UserCertificateDownload userCertificateDownload : userCertificateDownloads) {
			if (EntityCacheUtil.getResult(
						UserCertificateDownloadModelImpl.ENTITY_CACHE_ENABLED,
						UserCertificateDownloadImpl.class,
						userCertificateDownload.getPrimaryKey()) == null) {
				cacheResult(userCertificateDownload);
			}
			else {
				userCertificateDownload.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all user certificate downloads.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(UserCertificateDownloadImpl.class.getName());
		}

		EntityCacheUtil.clearCache(UserCertificateDownloadImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user certificate download.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserCertificateDownload userCertificateDownload) {
		EntityCacheUtil.removeResult(UserCertificateDownloadModelImpl.ENTITY_CACHE_ENABLED,
			UserCertificateDownloadImpl.class,
			userCertificateDownload.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<UserCertificateDownload> userCertificateDownloads) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UserCertificateDownload userCertificateDownload : userCertificateDownloads) {
			EntityCacheUtil.removeResult(UserCertificateDownloadModelImpl.ENTITY_CACHE_ENABLED,
				UserCertificateDownloadImpl.class,
				userCertificateDownload.getPrimaryKey());
		}
	}

	/**
	 * Creates a new user certificate download with the primary key. Does not add the user certificate download to the database.
	 *
	 * @param userCertificateDownloadPK the primary key for the new user certificate download
	 * @return the new user certificate download
	 */
	public UserCertificateDownload create(
		UserCertificateDownloadPK userCertificateDownloadPK) {
		UserCertificateDownload userCertificateDownload = new UserCertificateDownloadImpl();

		userCertificateDownload.setNew(true);
		userCertificateDownload.setPrimaryKey(userCertificateDownloadPK);

		return userCertificateDownload;
	}

	/**
	 * Removes the user certificate download with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userCertificateDownloadPK the primary key of the user certificate download
	 * @return the user certificate download that was removed
	 * @throws com.liferay.lms.NoSuchUserCertificateDownloadException if a user certificate download with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCertificateDownload remove(
		UserCertificateDownloadPK userCertificateDownloadPK)
		throws NoSuchUserCertificateDownloadException, SystemException {
		return remove((Serializable)userCertificateDownloadPK);
	}

	/**
	 * Removes the user certificate download with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user certificate download
	 * @return the user certificate download that was removed
	 * @throws com.liferay.lms.NoSuchUserCertificateDownloadException if a user certificate download with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserCertificateDownload remove(Serializable primaryKey)
		throws NoSuchUserCertificateDownloadException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserCertificateDownload userCertificateDownload = (UserCertificateDownload)session.get(UserCertificateDownloadImpl.class,
					primaryKey);

			if (userCertificateDownload == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserCertificateDownloadException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(userCertificateDownload);
		}
		catch (NoSuchUserCertificateDownloadException nsee) {
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
	protected UserCertificateDownload removeImpl(
		UserCertificateDownload userCertificateDownload)
		throws SystemException {
		userCertificateDownload = toUnwrappedModel(userCertificateDownload);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, userCertificateDownload);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(userCertificateDownload);

		return userCertificateDownload;
	}

	@Override
	public UserCertificateDownload updateImpl(
		com.liferay.lms.model.UserCertificateDownload userCertificateDownload,
		boolean merge) throws SystemException {
		userCertificateDownload = toUnwrappedModel(userCertificateDownload);

		boolean isNew = userCertificateDownload.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, userCertificateDownload, merge);

			userCertificateDownload.setNew(false);
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

		EntityCacheUtil.putResult(UserCertificateDownloadModelImpl.ENTITY_CACHE_ENABLED,
			UserCertificateDownloadImpl.class,
			userCertificateDownload.getPrimaryKey(), userCertificateDownload);

		return userCertificateDownload;
	}

	protected UserCertificateDownload toUnwrappedModel(
		UserCertificateDownload userCertificateDownload) {
		if (userCertificateDownload instanceof UserCertificateDownloadImpl) {
			return userCertificateDownload;
		}

		UserCertificateDownloadImpl userCertificateDownloadImpl = new UserCertificateDownloadImpl();

		userCertificateDownloadImpl.setNew(userCertificateDownload.isNew());
		userCertificateDownloadImpl.setPrimaryKey(userCertificateDownload.getPrimaryKey());

		userCertificateDownloadImpl.setUserId(userCertificateDownload.getUserId());
		userCertificateDownloadImpl.setCourseId(userCertificateDownload.getCourseId());
		userCertificateDownloadImpl.setCompetenceId(userCertificateDownload.getCompetenceId());
		userCertificateDownloadImpl.setDownloadDate(userCertificateDownload.getDownloadDate());

		return userCertificateDownloadImpl;
	}

	/**
	 * Returns the user certificate download with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the user certificate download
	 * @return the user certificate download
	 * @throws com.liferay.portal.NoSuchModelException if a user certificate download with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserCertificateDownload findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey((UserCertificateDownloadPK)primaryKey);
	}

	/**
	 * Returns the user certificate download with the primary key or throws a {@link com.liferay.lms.NoSuchUserCertificateDownloadException} if it could not be found.
	 *
	 * @param userCertificateDownloadPK the primary key of the user certificate download
	 * @return the user certificate download
	 * @throws com.liferay.lms.NoSuchUserCertificateDownloadException if a user certificate download with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCertificateDownload findByPrimaryKey(
		UserCertificateDownloadPK userCertificateDownloadPK)
		throws NoSuchUserCertificateDownloadException, SystemException {
		UserCertificateDownload userCertificateDownload = fetchByPrimaryKey(userCertificateDownloadPK);

		if (userCertificateDownload == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					userCertificateDownloadPK);
			}

			throw new NoSuchUserCertificateDownloadException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				userCertificateDownloadPK);
		}

		return userCertificateDownload;
	}

	/**
	 * Returns the user certificate download with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user certificate download
	 * @return the user certificate download, or <code>null</code> if a user certificate download with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserCertificateDownload fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey((UserCertificateDownloadPK)primaryKey);
	}

	/**
	 * Returns the user certificate download with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userCertificateDownloadPK the primary key of the user certificate download
	 * @return the user certificate download, or <code>null</code> if a user certificate download with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserCertificateDownload fetchByPrimaryKey(
		UserCertificateDownloadPK userCertificateDownloadPK)
		throws SystemException {
		UserCertificateDownload userCertificateDownload = (UserCertificateDownload)EntityCacheUtil.getResult(UserCertificateDownloadModelImpl.ENTITY_CACHE_ENABLED,
				UserCertificateDownloadImpl.class, userCertificateDownloadPK);

		if (userCertificateDownload == _nullUserCertificateDownload) {
			return null;
		}

		if (userCertificateDownload == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				userCertificateDownload = (UserCertificateDownload)session.get(UserCertificateDownloadImpl.class,
						userCertificateDownloadPK);
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (userCertificateDownload != null) {
					cacheResult(userCertificateDownload);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(UserCertificateDownloadModelImpl.ENTITY_CACHE_ENABLED,
						UserCertificateDownloadImpl.class,
						userCertificateDownloadPK, _nullUserCertificateDownload);
				}

				closeSession(session);
			}
		}

		return userCertificateDownload;
	}

	/**
	 * Returns all the user certificate downloads.
	 *
	 * @return the user certificate downloads
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserCertificateDownload> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user certificate downloads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user certificate downloads
	 * @param end the upper bound of the range of user certificate downloads (not inclusive)
	 * @return the range of user certificate downloads
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserCertificateDownload> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user certificate downloads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user certificate downloads
	 * @param end the upper bound of the range of user certificate downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user certificate downloads
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserCertificateDownload> findAll(int start, int end,
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

		List<UserCertificateDownload> list = (List<UserCertificateDownload>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_USERCERTIFICATEDOWNLOAD);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USERCERTIFICATEDOWNLOAD;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<UserCertificateDownload>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserCertificateDownload>)QueryUtil.list(q,
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
	 * Removes all the user certificate downloads from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (UserCertificateDownload userCertificateDownload : findAll()) {
			remove(userCertificateDownload);
		}
	}

	/**
	 * Returns the number of user certificate downloads.
	 *
	 * @return the number of user certificate downloads
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USERCERTIFICATEDOWNLOAD);

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
	 * Initializes the user certificate download persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.UserCertificateDownload")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserCertificateDownload>> listenersList = new ArrayList<ModelListener<UserCertificateDownload>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserCertificateDownload>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(UserCertificateDownloadImpl.class.getName());
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
	private static final String _SQL_SELECT_USERCERTIFICATEDOWNLOAD = "SELECT userCertificateDownload FROM UserCertificateDownload userCertificateDownload";
	private static final String _SQL_COUNT_USERCERTIFICATEDOWNLOAD = "SELECT COUNT(userCertificateDownload) FROM UserCertificateDownload userCertificateDownload";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userCertificateDownload.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserCertificateDownload exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(UserCertificateDownloadPersistenceImpl.class);
	private static UserCertificateDownload _nullUserCertificateDownload = new UserCertificateDownloadImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<UserCertificateDownload> toCacheModel() {
				return _nullUserCertificateDownloadCacheModel;
			}
		};

	private static CacheModel<UserCertificateDownload> _nullUserCertificateDownloadCacheModel =
		new CacheModel<UserCertificateDownload>() {
			public UserCertificateDownload toEntityModel() {
				return _nullUserCertificateDownload;
			}
		};
}