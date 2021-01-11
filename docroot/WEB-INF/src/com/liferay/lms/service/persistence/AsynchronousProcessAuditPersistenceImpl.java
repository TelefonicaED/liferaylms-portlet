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

import com.liferay.lms.NoSuchAsynchronousProcessAuditException;
import com.liferay.lms.model.AsynchronousProcessAudit;
import com.liferay.lms.model.impl.AsynchronousProcessAuditImpl;
import com.liferay.lms.model.impl.AsynchronousProcessAuditModelImpl;

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
 * The persistence implementation for the asynchronous process audit service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see AsynchronousProcessAuditPersistence
 * @see AsynchronousProcessAuditUtil
 * @generated
 */
public class AsynchronousProcessAuditPersistenceImpl extends BasePersistenceImpl<AsynchronousProcessAudit>
	implements AsynchronousProcessAuditPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AsynchronousProcessAuditUtil} to access the asynchronous process audit persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AsynchronousProcessAuditImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDCLASSPKSTATUS =
		new FinderPath(AsynchronousProcessAuditModelImpl.ENTITY_CACHE_ENABLED,
			AsynchronousProcessAuditModelImpl.FINDER_CACHE_ENABLED,
			AsynchronousProcessAuditImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyIdClassNameIdClassPKStatus",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDCLASSPKSTATUS =
		new FinderPath(AsynchronousProcessAuditModelImpl.ENTITY_CACHE_ENABLED,
			AsynchronousProcessAuditModelImpl.FINDER_CACHE_ENABLED,
			AsynchronousProcessAuditImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyIdClassNameIdClassPKStatus",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			AsynchronousProcessAuditModelImpl.COMPANYID_COLUMN_BITMASK |
			AsynchronousProcessAuditModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AsynchronousProcessAuditModelImpl.CLASSPK_COLUMN_BITMASK |
			AsynchronousProcessAuditModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYIDCLASSNAMEIDCLASSPKSTATUS =
		new FinderPath(AsynchronousProcessAuditModelImpl.ENTITY_CACHE_ENABLED,
			AsynchronousProcessAuditModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyIdClassNameIdClassPKStatus",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AsynchronousProcessAuditModelImpl.ENTITY_CACHE_ENABLED,
			AsynchronousProcessAuditModelImpl.FINDER_CACHE_ENABLED,
			AsynchronousProcessAuditImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AsynchronousProcessAuditModelImpl.ENTITY_CACHE_ENABLED,
			AsynchronousProcessAuditModelImpl.FINDER_CACHE_ENABLED,
			AsynchronousProcessAuditImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AsynchronousProcessAuditModelImpl.ENTITY_CACHE_ENABLED,
			AsynchronousProcessAuditModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the asynchronous process audit in the entity cache if it is enabled.
	 *
	 * @param asynchronousProcessAudit the asynchronous process audit
	 */
	public void cacheResult(AsynchronousProcessAudit asynchronousProcessAudit) {
		EntityCacheUtil.putResult(AsynchronousProcessAuditModelImpl.ENTITY_CACHE_ENABLED,
			AsynchronousProcessAuditImpl.class,
			asynchronousProcessAudit.getPrimaryKey(), asynchronousProcessAudit);

		asynchronousProcessAudit.resetOriginalValues();
	}

	/**
	 * Caches the asynchronous process audits in the entity cache if it is enabled.
	 *
	 * @param asynchronousProcessAudits the asynchronous process audits
	 */
	public void cacheResult(
		List<AsynchronousProcessAudit> asynchronousProcessAudits) {
		for (AsynchronousProcessAudit asynchronousProcessAudit : asynchronousProcessAudits) {
			if (EntityCacheUtil.getResult(
						AsynchronousProcessAuditModelImpl.ENTITY_CACHE_ENABLED,
						AsynchronousProcessAuditImpl.class,
						asynchronousProcessAudit.getPrimaryKey()) == null) {
				cacheResult(asynchronousProcessAudit);
			}
			else {
				asynchronousProcessAudit.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asynchronous process audits.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AsynchronousProcessAuditImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AsynchronousProcessAuditImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asynchronous process audit.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AsynchronousProcessAudit asynchronousProcessAudit) {
		EntityCacheUtil.removeResult(AsynchronousProcessAuditModelImpl.ENTITY_CACHE_ENABLED,
			AsynchronousProcessAuditImpl.class,
			asynchronousProcessAudit.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<AsynchronousProcessAudit> asynchronousProcessAudits) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AsynchronousProcessAudit asynchronousProcessAudit : asynchronousProcessAudits) {
			EntityCacheUtil.removeResult(AsynchronousProcessAuditModelImpl.ENTITY_CACHE_ENABLED,
				AsynchronousProcessAuditImpl.class,
				asynchronousProcessAudit.getPrimaryKey());
		}
	}

	/**
	 * Creates a new asynchronous process audit with the primary key. Does not add the asynchronous process audit to the database.
	 *
	 * @param asynchronousProcessAuditId the primary key for the new asynchronous process audit
	 * @return the new asynchronous process audit
	 */
	public AsynchronousProcessAudit create(long asynchronousProcessAuditId) {
		AsynchronousProcessAudit asynchronousProcessAudit = new AsynchronousProcessAuditImpl();

		asynchronousProcessAudit.setNew(true);
		asynchronousProcessAudit.setPrimaryKey(asynchronousProcessAuditId);

		return asynchronousProcessAudit;
	}

	/**
	 * Removes the asynchronous process audit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param asynchronousProcessAuditId the primary key of the asynchronous process audit
	 * @return the asynchronous process audit that was removed
	 * @throws com.liferay.lms.NoSuchAsynchronousProcessAuditException if a asynchronous process audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AsynchronousProcessAudit remove(long asynchronousProcessAuditId)
		throws NoSuchAsynchronousProcessAuditException, SystemException {
		return remove(Long.valueOf(asynchronousProcessAuditId));
	}

	/**
	 * Removes the asynchronous process audit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asynchronous process audit
	 * @return the asynchronous process audit that was removed
	 * @throws com.liferay.lms.NoSuchAsynchronousProcessAuditException if a asynchronous process audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AsynchronousProcessAudit remove(Serializable primaryKey)
		throws NoSuchAsynchronousProcessAuditException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AsynchronousProcessAudit asynchronousProcessAudit = (AsynchronousProcessAudit)session.get(AsynchronousProcessAuditImpl.class,
					primaryKey);

			if (asynchronousProcessAudit == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAsynchronousProcessAuditException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(asynchronousProcessAudit);
		}
		catch (NoSuchAsynchronousProcessAuditException nsee) {
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
	protected AsynchronousProcessAudit removeImpl(
		AsynchronousProcessAudit asynchronousProcessAudit)
		throws SystemException {
		asynchronousProcessAudit = toUnwrappedModel(asynchronousProcessAudit);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, asynchronousProcessAudit);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(asynchronousProcessAudit);

		return asynchronousProcessAudit;
	}

	@Override
	public AsynchronousProcessAudit updateImpl(
		com.liferay.lms.model.AsynchronousProcessAudit asynchronousProcessAudit,
		boolean merge) throws SystemException {
		asynchronousProcessAudit = toUnwrappedModel(asynchronousProcessAudit);

		boolean isNew = asynchronousProcessAudit.isNew();

		AsynchronousProcessAuditModelImpl asynchronousProcessAuditModelImpl = (AsynchronousProcessAuditModelImpl)asynchronousProcessAudit;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, asynchronousProcessAudit, merge);

			asynchronousProcessAudit.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AsynchronousProcessAuditModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((asynchronousProcessAuditModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDCLASSPKSTATUS.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(asynchronousProcessAuditModelImpl.getOriginalCompanyId()),
						Long.valueOf(asynchronousProcessAuditModelImpl.getOriginalClassNameId()),
						Long.valueOf(asynchronousProcessAuditModelImpl.getOriginalClassPK()),
						Integer.valueOf(asynchronousProcessAuditModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYIDCLASSNAMEIDCLASSPKSTATUS,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDCLASSPKSTATUS,
					args);

				args = new Object[] {
						Long.valueOf(asynchronousProcessAuditModelImpl.getCompanyId()),
						Long.valueOf(asynchronousProcessAuditModelImpl.getClassNameId()),
						Long.valueOf(asynchronousProcessAuditModelImpl.getClassPK()),
						Integer.valueOf(asynchronousProcessAuditModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYIDCLASSNAMEIDCLASSPKSTATUS,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDCLASSPKSTATUS,
					args);
			}
		}

		EntityCacheUtil.putResult(AsynchronousProcessAuditModelImpl.ENTITY_CACHE_ENABLED,
			AsynchronousProcessAuditImpl.class,
			asynchronousProcessAudit.getPrimaryKey(), asynchronousProcessAudit);

		return asynchronousProcessAudit;
	}

	protected AsynchronousProcessAudit toUnwrappedModel(
		AsynchronousProcessAudit asynchronousProcessAudit) {
		if (asynchronousProcessAudit instanceof AsynchronousProcessAuditImpl) {
			return asynchronousProcessAudit;
		}

		AsynchronousProcessAuditImpl asynchronousProcessAuditImpl = new AsynchronousProcessAuditImpl();

		asynchronousProcessAuditImpl.setNew(asynchronousProcessAudit.isNew());
		asynchronousProcessAuditImpl.setPrimaryKey(asynchronousProcessAudit.getPrimaryKey());

		asynchronousProcessAuditImpl.setAsynchronousProcessAuditId(asynchronousProcessAudit.getAsynchronousProcessAuditId());
		asynchronousProcessAuditImpl.setCompanyId(asynchronousProcessAudit.getCompanyId());
		asynchronousProcessAuditImpl.setType(asynchronousProcessAudit.getType());
		asynchronousProcessAuditImpl.setClassNameId(asynchronousProcessAudit.getClassNameId());
		asynchronousProcessAuditImpl.setClassPK(asynchronousProcessAudit.getClassPK());
		asynchronousProcessAuditImpl.setUserId(asynchronousProcessAudit.getUserId());
		asynchronousProcessAuditImpl.setCreateDate(asynchronousProcessAudit.getCreateDate());
		asynchronousProcessAuditImpl.setEndDate(asynchronousProcessAudit.getEndDate());
		asynchronousProcessAuditImpl.setStatus(asynchronousProcessAudit.getStatus());
		asynchronousProcessAuditImpl.setStatusMessage(asynchronousProcessAudit.getStatusMessage());
		asynchronousProcessAuditImpl.setExtraContent(asynchronousProcessAudit.getExtraContent());

		return asynchronousProcessAuditImpl;
	}

	/**
	 * Returns the asynchronous process audit with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asynchronous process audit
	 * @return the asynchronous process audit
	 * @throws com.liferay.portal.NoSuchModelException if a asynchronous process audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AsynchronousProcessAudit findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asynchronous process audit with the primary key or throws a {@link com.liferay.lms.NoSuchAsynchronousProcessAuditException} if it could not be found.
	 *
	 * @param asynchronousProcessAuditId the primary key of the asynchronous process audit
	 * @return the asynchronous process audit
	 * @throws com.liferay.lms.NoSuchAsynchronousProcessAuditException if a asynchronous process audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AsynchronousProcessAudit findByPrimaryKey(
		long asynchronousProcessAuditId)
		throws NoSuchAsynchronousProcessAuditException, SystemException {
		AsynchronousProcessAudit asynchronousProcessAudit = fetchByPrimaryKey(asynchronousProcessAuditId);

		if (asynchronousProcessAudit == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					asynchronousProcessAuditId);
			}

			throw new NoSuchAsynchronousProcessAuditException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				asynchronousProcessAuditId);
		}

		return asynchronousProcessAudit;
	}

	/**
	 * Returns the asynchronous process audit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asynchronous process audit
	 * @return the asynchronous process audit, or <code>null</code> if a asynchronous process audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AsynchronousProcessAudit fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asynchronous process audit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param asynchronousProcessAuditId the primary key of the asynchronous process audit
	 * @return the asynchronous process audit, or <code>null</code> if a asynchronous process audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AsynchronousProcessAudit fetchByPrimaryKey(
		long asynchronousProcessAuditId) throws SystemException {
		AsynchronousProcessAudit asynchronousProcessAudit = (AsynchronousProcessAudit)EntityCacheUtil.getResult(AsynchronousProcessAuditModelImpl.ENTITY_CACHE_ENABLED,
				AsynchronousProcessAuditImpl.class, asynchronousProcessAuditId);

		if (asynchronousProcessAudit == _nullAsynchronousProcessAudit) {
			return null;
		}

		if (asynchronousProcessAudit == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				asynchronousProcessAudit = (AsynchronousProcessAudit)session.get(AsynchronousProcessAuditImpl.class,
						Long.valueOf(asynchronousProcessAuditId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (asynchronousProcessAudit != null) {
					cacheResult(asynchronousProcessAudit);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(AsynchronousProcessAuditModelImpl.ENTITY_CACHE_ENABLED,
						AsynchronousProcessAuditImpl.class,
						asynchronousProcessAuditId,
						_nullAsynchronousProcessAudit);
				}

				closeSession(session);
			}
		}

		return asynchronousProcessAudit;
	}

	/**
	 * Returns all the asynchronous process audits where companyId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @return the matching asynchronous process audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<AsynchronousProcessAudit> findByCompanyIdClassNameIdClassPKStatus(
		long companyId, long classNameId, long classPK, int status)
		throws SystemException {
		return findByCompanyIdClassNameIdClassPKStatus(companyId, classNameId,
			classPK, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asynchronous process audits where companyId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param start the lower bound of the range of asynchronous process audits
	 * @param end the upper bound of the range of asynchronous process audits (not inclusive)
	 * @return the range of matching asynchronous process audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<AsynchronousProcessAudit> findByCompanyIdClassNameIdClassPKStatus(
		long companyId, long classNameId, long classPK, int status, int start,
		int end) throws SystemException {
		return findByCompanyIdClassNameIdClassPKStatus(companyId, classNameId,
			classPK, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asynchronous process audits where companyId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param start the lower bound of the range of asynchronous process audits
	 * @param end the upper bound of the range of asynchronous process audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asynchronous process audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<AsynchronousProcessAudit> findByCompanyIdClassNameIdClassPKStatus(
		long companyId, long classNameId, long classPK, int status, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDCLASSPKSTATUS;
			finderArgs = new Object[] { companyId, classNameId, classPK, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYIDCLASSNAMEIDCLASSPKSTATUS;
			finderArgs = new Object[] {
					companyId, classNameId, classPK, status,
					
					start, end, orderByComparator
				};
		}

		List<AsynchronousProcessAudit> list = (List<AsynchronousProcessAudit>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AsynchronousProcessAudit asynchronousProcessAudit : list) {
				if ((companyId != asynchronousProcessAudit.getCompanyId()) ||
						(classNameId != asynchronousProcessAudit.getClassNameId()) ||
						(classPK != asynchronousProcessAudit.getClassPK()) ||
						(status != asynchronousProcessAudit.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_ASYNCHRONOUSPROCESSAUDIT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_COMPANYID_2);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_CLASSPK_2);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_STATUS_2);

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

				qPos.add(classPK);

				qPos.add(status);

				list = (List<AsynchronousProcessAudit>)QueryUtil.list(q,
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
	 * Returns the first asynchronous process audit in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asynchronous process audit
	 * @throws com.liferay.lms.NoSuchAsynchronousProcessAuditException if a matching asynchronous process audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AsynchronousProcessAudit findByCompanyIdClassNameIdClassPKStatus_First(
		long companyId, long classNameId, long classPK, int status,
		OrderByComparator orderByComparator)
		throws NoSuchAsynchronousProcessAuditException, SystemException {
		AsynchronousProcessAudit asynchronousProcessAudit = fetchByCompanyIdClassNameIdClassPKStatus_First(companyId,
				classNameId, classPK, status, orderByComparator);

		if (asynchronousProcessAudit != null) {
			return asynchronousProcessAudit;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAsynchronousProcessAuditException(msg.toString());
	}

	/**
	 * Returns the first asynchronous process audit in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asynchronous process audit, or <code>null</code> if a matching asynchronous process audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AsynchronousProcessAudit fetchByCompanyIdClassNameIdClassPKStatus_First(
		long companyId, long classNameId, long classPK, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<AsynchronousProcessAudit> list = findByCompanyIdClassNameIdClassPKStatus(companyId,
				classNameId, classPK, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asynchronous process audit in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asynchronous process audit
	 * @throws com.liferay.lms.NoSuchAsynchronousProcessAuditException if a matching asynchronous process audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AsynchronousProcessAudit findByCompanyIdClassNameIdClassPKStatus_Last(
		long companyId, long classNameId, long classPK, int status,
		OrderByComparator orderByComparator)
		throws NoSuchAsynchronousProcessAuditException, SystemException {
		AsynchronousProcessAudit asynchronousProcessAudit = fetchByCompanyIdClassNameIdClassPKStatus_Last(companyId,
				classNameId, classPK, status, orderByComparator);

		if (asynchronousProcessAudit != null) {
			return asynchronousProcessAudit;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAsynchronousProcessAuditException(msg.toString());
	}

	/**
	 * Returns the last asynchronous process audit in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asynchronous process audit, or <code>null</code> if a matching asynchronous process audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AsynchronousProcessAudit fetchByCompanyIdClassNameIdClassPKStatus_Last(
		long companyId, long classNameId, long classPK, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyIdClassNameIdClassPKStatus(companyId,
				classNameId, classPK, status);

		List<AsynchronousProcessAudit> list = findByCompanyIdClassNameIdClassPKStatus(companyId,
				classNameId, classPK, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asynchronous process audits before and after the current asynchronous process audit in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param asynchronousProcessAuditId the primary key of the current asynchronous process audit
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asynchronous process audit
	 * @throws com.liferay.lms.NoSuchAsynchronousProcessAuditException if a asynchronous process audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AsynchronousProcessAudit[] findByCompanyIdClassNameIdClassPKStatus_PrevAndNext(
		long asynchronousProcessAuditId, long companyId, long classNameId,
		long classPK, int status, OrderByComparator orderByComparator)
		throws NoSuchAsynchronousProcessAuditException, SystemException {
		AsynchronousProcessAudit asynchronousProcessAudit = findByPrimaryKey(asynchronousProcessAuditId);

		Session session = null;

		try {
			session = openSession();

			AsynchronousProcessAudit[] array = new AsynchronousProcessAuditImpl[3];

			array[0] = getByCompanyIdClassNameIdClassPKStatus_PrevAndNext(session,
					asynchronousProcessAudit, companyId, classNameId, classPK,
					status, orderByComparator, true);

			array[1] = asynchronousProcessAudit;

			array[2] = getByCompanyIdClassNameIdClassPKStatus_PrevAndNext(session,
					asynchronousProcessAudit, companyId, classNameId, classPK,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AsynchronousProcessAudit getByCompanyIdClassNameIdClassPKStatus_PrevAndNext(
		Session session, AsynchronousProcessAudit asynchronousProcessAudit,
		long companyId, long classNameId, long classPK, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASYNCHRONOUSPROCESSAUDIT_WHERE);

		query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_COMPANYID_2);

		query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_CLASSPK_2);

		query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_STATUS_2);

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

		qPos.add(classPK);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(asynchronousProcessAudit);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AsynchronousProcessAudit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asynchronous process audits.
	 *
	 * @return the asynchronous process audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<AsynchronousProcessAudit> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asynchronous process audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asynchronous process audits
	 * @param end the upper bound of the range of asynchronous process audits (not inclusive)
	 * @return the range of asynchronous process audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<AsynchronousProcessAudit> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asynchronous process audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asynchronous process audits
	 * @param end the upper bound of the range of asynchronous process audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asynchronous process audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<AsynchronousProcessAudit> findAll(int start, int end,
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

		List<AsynchronousProcessAudit> list = (List<AsynchronousProcessAudit>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ASYNCHRONOUSPROCESSAUDIT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASYNCHRONOUSPROCESSAUDIT;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AsynchronousProcessAudit>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AsynchronousProcessAudit>)QueryUtil.list(q,
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
	 * Removes all the asynchronous process audits where companyId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyIdClassNameIdClassPKStatus(long companyId,
		long classNameId, long classPK, int status) throws SystemException {
		for (AsynchronousProcessAudit asynchronousProcessAudit : findByCompanyIdClassNameIdClassPKStatus(
				companyId, classNameId, classPK, status)) {
			remove(asynchronousProcessAudit);
		}
	}

	/**
	 * Removes all the asynchronous process audits from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (AsynchronousProcessAudit asynchronousProcessAudit : findAll()) {
			remove(asynchronousProcessAudit);
		}
	}

	/**
	 * Returns the number of asynchronous process audits where companyId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param status the status
	 * @return the number of matching asynchronous process audits
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyIdClassNameIdClassPKStatus(long companyId,
		long classNameId, long classPK, int status) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, classNameId, classPK, status
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYIDCLASSNAMEIDCLASSPKSTATUS,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_ASYNCHRONOUSPROCESSAUDIT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_COMPANYID_2);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_CLASSPK_2);

			query.append(_FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYIDCLASSNAMEIDCLASSPKSTATUS,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asynchronous process audits.
	 *
	 * @return the number of asynchronous process audits
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASYNCHRONOUSPROCESSAUDIT);

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
	 * Initializes the asynchronous process audit persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.AsynchronousProcessAudit")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AsynchronousProcessAudit>> listenersList = new ArrayList<ModelListener<AsynchronousProcessAudit>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AsynchronousProcessAudit>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(AsynchronousProcessAuditImpl.class.getName());
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
	private static final String _SQL_SELECT_ASYNCHRONOUSPROCESSAUDIT = "SELECT asynchronousProcessAudit FROM AsynchronousProcessAudit asynchronousProcessAudit";
	private static final String _SQL_SELECT_ASYNCHRONOUSPROCESSAUDIT_WHERE = "SELECT asynchronousProcessAudit FROM AsynchronousProcessAudit asynchronousProcessAudit WHERE ";
	private static final String _SQL_COUNT_ASYNCHRONOUSPROCESSAUDIT = "SELECT COUNT(asynchronousProcessAudit) FROM AsynchronousProcessAudit asynchronousProcessAudit";
	private static final String _SQL_COUNT_ASYNCHRONOUSPROCESSAUDIT_WHERE = "SELECT COUNT(asynchronousProcessAudit) FROM AsynchronousProcessAudit asynchronousProcessAudit WHERE ";
	private static final String _FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_COMPANYID_2 =
		"asynchronousProcessAudit.companyId = ? AND ";
	private static final String _FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_CLASSNAMEID_2 =
		"asynchronousProcessAudit.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_CLASSPK_2 =
		"asynchronousProcessAudit.classPK = ? AND ";
	private static final String _FINDER_COLUMN_COMPANYIDCLASSNAMEIDCLASSPKSTATUS_STATUS_2 =
		"asynchronousProcessAudit.status = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "asynchronousProcessAudit.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AsynchronousProcessAudit exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AsynchronousProcessAudit exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(AsynchronousProcessAuditPersistenceImpl.class);
	private static AsynchronousProcessAudit _nullAsynchronousProcessAudit = new AsynchronousProcessAuditImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AsynchronousProcessAudit> toCacheModel() {
				return _nullAsynchronousProcessAuditCacheModel;
			}
		};

	private static CacheModel<AsynchronousProcessAudit> _nullAsynchronousProcessAuditCacheModel =
		new CacheModel<AsynchronousProcessAudit>() {
			public AsynchronousProcessAudit toEntityModel() {
				return _nullAsynchronousProcessAudit;
			}
		};
}