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

import com.liferay.lms.NoSuchAuditEntryException;
import com.liferay.lms.model.AuditEntry;
import com.liferay.lms.model.impl.AuditEntryImpl;
import com.liferay.lms.model.impl.AuditEntryModelImpl;

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
 * The persistence implementation for the audit entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see AuditEntryPersistence
 * @see AuditEntryUtil
 * @generated
 */
public class AuditEntryPersistenceImpl extends BasePersistenceImpl<AuditEntry>
	implements AuditEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AuditEntryUtil} to access the audit entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AuditEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			AuditEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			AuditEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			AuditEntryModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_G = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_G",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_G",
			new String[] { Long.class.getName(), Long.class.getName() },
			AuditEntryModelImpl.USERID_COLUMN_BITMASK |
			AuditEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_G = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_G",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_G_C = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_G_c",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G_C = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_G_c",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			AuditEntryModelImpl.USERID_COLUMN_BITMASK |
			AuditEntryModelImpl.GROUPID_COLUMN_BITMASK |
			AuditEntryModelImpl.CLASSNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_G_C = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_G_c",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CN_CP = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCN_CP",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CP = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCN_CP",
			new String[] { String.class.getName(), Long.class.getName() },
			AuditEntryModelImpl.CLASSNAME_COLUMN_BITMASK |
			AuditEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CN_CP = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCN_CP",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, AuditEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the audit entry in the entity cache if it is enabled.
	 *
	 * @param auditEntry the audit entry
	 */
	public void cacheResult(AuditEntry auditEntry) {
		EntityCacheUtil.putResult(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryImpl.class, auditEntry.getPrimaryKey(), auditEntry);

		auditEntry.resetOriginalValues();
	}

	/**
	 * Caches the audit entries in the entity cache if it is enabled.
	 *
	 * @param auditEntries the audit entries
	 */
	public void cacheResult(List<AuditEntry> auditEntries) {
		for (AuditEntry auditEntry : auditEntries) {
			if (EntityCacheUtil.getResult(
						AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
						AuditEntryImpl.class, auditEntry.getPrimaryKey()) == null) {
				cacheResult(auditEntry);
			}
			else {
				auditEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all audit entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AuditEntryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AuditEntryImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the audit entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AuditEntry auditEntry) {
		EntityCacheUtil.removeResult(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryImpl.class, auditEntry.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<AuditEntry> auditEntries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AuditEntry auditEntry : auditEntries) {
			EntityCacheUtil.removeResult(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
				AuditEntryImpl.class, auditEntry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new audit entry with the primary key. Does not add the audit entry to the database.
	 *
	 * @param auditId the primary key for the new audit entry
	 * @return the new audit entry
	 */
	public AuditEntry create(long auditId) {
		AuditEntry auditEntry = new AuditEntryImpl();

		auditEntry.setNew(true);
		auditEntry.setPrimaryKey(auditId);

		return auditEntry;
	}

	/**
	 * Removes the audit entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param auditId the primary key of the audit entry
	 * @return the audit entry that was removed
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry remove(long auditId)
		throws NoSuchAuditEntryException, SystemException {
		return remove(Long.valueOf(auditId));
	}

	/**
	 * Removes the audit entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the audit entry
	 * @return the audit entry that was removed
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AuditEntry remove(Serializable primaryKey)
		throws NoSuchAuditEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AuditEntry auditEntry = (AuditEntry)session.get(AuditEntryImpl.class,
					primaryKey);

			if (auditEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAuditEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(auditEntry);
		}
		catch (NoSuchAuditEntryException nsee) {
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
	protected AuditEntry removeImpl(AuditEntry auditEntry)
		throws SystemException {
		auditEntry = toUnwrappedModel(auditEntry);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, auditEntry);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(auditEntry);

		return auditEntry;
	}

	@Override
	public AuditEntry updateImpl(com.liferay.lms.model.AuditEntry auditEntry,
		boolean merge) throws SystemException {
		auditEntry = toUnwrappedModel(auditEntry);

		boolean isNew = auditEntry.isNew();

		AuditEntryModelImpl auditEntryModelImpl = (AuditEntryModelImpl)auditEntry;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, auditEntry, merge);

			auditEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AuditEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((auditEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(auditEntryModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(auditEntryModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((auditEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(auditEntryModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(auditEntryModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((auditEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(auditEntryModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(auditEntryModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((auditEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(auditEntryModelImpl.getOriginalUserId()),
						Long.valueOf(auditEntryModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_G, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G,
					args);

				args = new Object[] {
						Long.valueOf(auditEntryModelImpl.getUserId()),
						Long.valueOf(auditEntryModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_G, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G,
					args);
			}

			if ((auditEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(auditEntryModelImpl.getOriginalUserId()),
						Long.valueOf(auditEntryModelImpl.getOriginalGroupId()),
						
						auditEntryModelImpl.getOriginalClassname()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G_C,
					args);

				args = new Object[] {
						Long.valueOf(auditEntryModelImpl.getUserId()),
						Long.valueOf(auditEntryModelImpl.getGroupId()),
						
						auditEntryModelImpl.getClassname()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G_C,
					args);
			}

			if ((auditEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CP.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						auditEntryModelImpl.getOriginalClassname(),
						Long.valueOf(auditEntryModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CN_CP, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CP,
					args);

				args = new Object[] {
						auditEntryModelImpl.getClassname(),
						Long.valueOf(auditEntryModelImpl.getClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CN_CP, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CP,
					args);
			}
		}

		EntityCacheUtil.putResult(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
			AuditEntryImpl.class, auditEntry.getPrimaryKey(), auditEntry);

		return auditEntry;
	}

	protected AuditEntry toUnwrappedModel(AuditEntry auditEntry) {
		if (auditEntry instanceof AuditEntryImpl) {
			return auditEntry;
		}

		AuditEntryImpl auditEntryImpl = new AuditEntryImpl();

		auditEntryImpl.setNew(auditEntry.isNew());
		auditEntryImpl.setPrimaryKey(auditEntry.getPrimaryKey());

		auditEntryImpl.setAuditId(auditEntry.getAuditId());
		auditEntryImpl.setAuditDate(auditEntry.getAuditDate());
		auditEntryImpl.setCompanyId(auditEntry.getCompanyId());
		auditEntryImpl.setGroupId(auditEntry.getGroupId());
		auditEntryImpl.setUserId(auditEntry.getUserId());
		auditEntryImpl.setClassname(auditEntry.getClassname());
		auditEntryImpl.setAction(auditEntry.getAction());
		auditEntryImpl.setExtradata(auditEntry.getExtradata());
		auditEntryImpl.setClassPK(auditEntry.getClassPK());
		auditEntryImpl.setAssociationClassPK(auditEntry.getAssociationClassPK());

		return auditEntryImpl;
	}

	/**
	 * Returns the audit entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the audit entry
	 * @return the audit entry
	 * @throws com.liferay.portal.NoSuchModelException if a audit entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AuditEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the audit entry with the primary key or throws a {@link com.liferay.lms.NoSuchAuditEntryException} if it could not be found.
	 *
	 * @param auditId the primary key of the audit entry
	 * @return the audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry findByPrimaryKey(long auditId)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = fetchByPrimaryKey(auditId);

		if (auditEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + auditId);
			}

			throw new NoSuchAuditEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				auditId);
		}

		return auditEntry;
	}

	/**
	 * Returns the audit entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the audit entry
	 * @return the audit entry, or <code>null</code> if a audit entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AuditEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the audit entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param auditId the primary key of the audit entry
	 * @return the audit entry, or <code>null</code> if a audit entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry fetchByPrimaryKey(long auditId) throws SystemException {
		AuditEntry auditEntry = (AuditEntry)EntityCacheUtil.getResult(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
				AuditEntryImpl.class, auditId);

		if (auditEntry == _nullAuditEntry) {
			return null;
		}

		if (auditEntry == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				auditEntry = (AuditEntry)session.get(AuditEntryImpl.class,
						Long.valueOf(auditId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (auditEntry != null) {
					cacheResult(auditEntry);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(AuditEntryModelImpl.ENTITY_CACHE_ENABLED,
						AuditEntryImpl.class, auditId, _nullAuditEntry);
				}

				closeSession(session);
			}
		}

		return auditEntry;
	}

	/**
	 * Returns all the audit entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the audit entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @return the range of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the audit entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByGroupId(long groupId, int start, int end,
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

		List<AuditEntry> list = (List<AuditEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AuditEntry auditEntry : list) {
				if ((groupId != auditEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_AUDITENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AuditEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<AuditEntry>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first audit entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = fetchByGroupId_First(groupId, orderByComparator);

		if (auditEntry != null) {
			return auditEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAuditEntryException(msg.toString());
	}

	/**
	 * Returns the first audit entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AuditEntry> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last audit entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = fetchByGroupId_Last(groupId, orderByComparator);

		if (auditEntry != null) {
			return auditEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAuditEntryException(msg.toString());
	}

	/**
	 * Returns the last audit entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<AuditEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the audit entries before and after the current audit entry in the ordered set where groupId = &#63;.
	 *
	 * @param auditId the primary key of the current audit entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry[] findByGroupId_PrevAndNext(long auditId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = findByPrimaryKey(auditId);

		Session session = null;

		try {
			session = openSession();

			AuditEntry[] array = new AuditEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, auditEntry, groupId,
					orderByComparator, true);

			array[1] = auditEntry;

			array[2] = getByGroupId_PrevAndNext(session, auditEntry, groupId,
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

	protected AuditEntry getByGroupId_PrevAndNext(Session session,
		AuditEntry auditEntry, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_AUDITENTRY_WHERE);

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

		else {
			query.append(AuditEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(auditEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AuditEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the audit entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the audit entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @return the range of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the audit entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByCompanyId(long companyId, int start, int end,
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

		List<AuditEntry> list = (List<AuditEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AuditEntry auditEntry : list) {
				if ((companyId != auditEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_AUDITENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AuditEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<AuditEntry>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first audit entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (auditEntry != null) {
			return auditEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAuditEntryException(msg.toString());
	}

	/**
	 * Returns the first audit entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AuditEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last audit entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (auditEntry != null) {
			return auditEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAuditEntryException(msg.toString());
	}

	/**
	 * Returns the last audit entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<AuditEntry> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the audit entries before and after the current audit entry in the ordered set where companyId = &#63;.
	 *
	 * @param auditId the primary key of the current audit entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry[] findByCompanyId_PrevAndNext(long auditId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = findByPrimaryKey(auditId);

		Session session = null;

		try {
			session = openSession();

			AuditEntry[] array = new AuditEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, auditEntry,
					companyId, orderByComparator, true);

			array[1] = auditEntry;

			array[2] = getByCompanyId_PrevAndNext(session, auditEntry,
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

	protected AuditEntry getByCompanyId_PrevAndNext(Session session,
		AuditEntry auditEntry, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_AUDITENTRY_WHERE);

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

		else {
			query.append(AuditEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(auditEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AuditEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the audit entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByUserId(long userId) throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the audit entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @return the range of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the audit entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByUserId(long userId, int start, int end,
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

		List<AuditEntry> list = (List<AuditEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AuditEntry auditEntry : list) {
				if ((userId != auditEntry.getUserId())) {
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

			query.append(_SQL_SELECT_AUDITENTRY_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AuditEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<AuditEntry>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first audit entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = fetchByUserId_First(userId, orderByComparator);

		if (auditEntry != null) {
			return auditEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAuditEntryException(msg.toString());
	}

	/**
	 * Returns the first audit entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AuditEntry> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last audit entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = fetchByUserId_Last(userId, orderByComparator);

		if (auditEntry != null) {
			return auditEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAuditEntryException(msg.toString());
	}

	/**
	 * Returns the last audit entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<AuditEntry> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the audit entries before and after the current audit entry in the ordered set where userId = &#63;.
	 *
	 * @param auditId the primary key of the current audit entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry[] findByUserId_PrevAndNext(long auditId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = findByPrimaryKey(auditId);

		Session session = null;

		try {
			session = openSession();

			AuditEntry[] array = new AuditEntryImpl[3];

			array[0] = getByUserId_PrevAndNext(session, auditEntry, userId,
					orderByComparator, true);

			array[1] = auditEntry;

			array[2] = getByUserId_PrevAndNext(session, auditEntry, userId,
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

	protected AuditEntry getByUserId_PrevAndNext(Session session,
		AuditEntry auditEntry, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_AUDITENTRY_WHERE);

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

		else {
			query.append(AuditEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(auditEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AuditEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the audit entries where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByU_G(long userId, long groupId)
		throws SystemException {
		return findByU_G(userId, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the audit entries where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @return the range of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByU_G(long userId, long groupId, int start,
		int end) throws SystemException {
		return findByU_G(userId, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the audit entries where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByU_G(long userId, long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G;
			finderArgs = new Object[] { userId, groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_G;
			finderArgs = new Object[] {
					userId, groupId,
					
					start, end, orderByComparator
				};
		}

		List<AuditEntry> list = (List<AuditEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AuditEntry auditEntry : list) {
				if ((userId != auditEntry.getUserId()) ||
						(groupId != auditEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_AUDITENTRY_WHERE);

			query.append(_FINDER_COLUMN_U_G_USERID_2);

			query.append(_FINDER_COLUMN_U_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AuditEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(groupId);

				list = (List<AuditEntry>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first audit entry in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry findByU_G_First(long userId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = fetchByU_G_First(userId, groupId,
				orderByComparator);

		if (auditEntry != null) {
			return auditEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAuditEntryException(msg.toString());
	}

	/**
	 * Returns the first audit entry in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry fetchByU_G_First(long userId, long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AuditEntry> list = findByU_G(userId, groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last audit entry in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry findByU_G_Last(long userId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = fetchByU_G_Last(userId, groupId,
				orderByComparator);

		if (auditEntry != null) {
			return auditEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAuditEntryException(msg.toString());
	}

	/**
	 * Returns the last audit entry in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry fetchByU_G_Last(long userId, long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByU_G(userId, groupId);

		List<AuditEntry> list = findByU_G(userId, groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the audit entries before and after the current audit entry in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param auditId the primary key of the current audit entry
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry[] findByU_G_PrevAndNext(long auditId, long userId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = findByPrimaryKey(auditId);

		Session session = null;

		try {
			session = openSession();

			AuditEntry[] array = new AuditEntryImpl[3];

			array[0] = getByU_G_PrevAndNext(session, auditEntry, userId,
					groupId, orderByComparator, true);

			array[1] = auditEntry;

			array[2] = getByU_G_PrevAndNext(session, auditEntry, userId,
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

	protected AuditEntry getByU_G_PrevAndNext(Session session,
		AuditEntry auditEntry, long userId, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_AUDITENTRY_WHERE);

		query.append(_FINDER_COLUMN_U_G_USERID_2);

		query.append(_FINDER_COLUMN_U_G_GROUPID_2);

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
			query.append(AuditEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(auditEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AuditEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the audit entries where userId = &#63; and groupId = &#63; and classname = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param classname the classname
	 * @return the matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByU_G_c(long userId, long groupId,
		String classname) throws SystemException {
		return findByU_G_c(userId, groupId, classname, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the audit entries where userId = &#63; and groupId = &#63; and classname = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param classname the classname
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @return the range of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByU_G_c(long userId, long groupId,
		String classname, int start, int end) throws SystemException {
		return findByU_G_c(userId, groupId, classname, start, end, null);
	}

	/**
	 * Returns an ordered range of all the audit entries where userId = &#63; and groupId = &#63; and classname = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param classname the classname
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByU_G_c(long userId, long groupId,
		String classname, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_G_C;
			finderArgs = new Object[] { userId, groupId, classname };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_G_C;
			finderArgs = new Object[] {
					userId, groupId, classname,
					
					start, end, orderByComparator
				};
		}

		List<AuditEntry> list = (List<AuditEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AuditEntry auditEntry : list) {
				if ((userId != auditEntry.getUserId()) ||
						(groupId != auditEntry.getGroupId()) ||
						!Validator.equals(classname, auditEntry.getClassname())) {
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

			query.append(_SQL_SELECT_AUDITENTRY_WHERE);

			query.append(_FINDER_COLUMN_U_G_C_USERID_2);

			query.append(_FINDER_COLUMN_U_G_C_GROUPID_2);

			if (classname == null) {
				query.append(_FINDER_COLUMN_U_G_C_CLASSNAME_1);
			}
			else {
				if (classname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_G_C_CLASSNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_G_C_CLASSNAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AuditEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(groupId);

				if (classname != null) {
					qPos.add(classname);
				}

				list = (List<AuditEntry>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first audit entry in the ordered set where userId = &#63; and groupId = &#63; and classname = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param classname the classname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry findByU_G_c_First(long userId, long groupId,
		String classname, OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = fetchByU_G_c_First(userId, groupId, classname,
				orderByComparator);

		if (auditEntry != null) {
			return auditEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(", classname=");
		msg.append(classname);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAuditEntryException(msg.toString());
	}

	/**
	 * Returns the first audit entry in the ordered set where userId = &#63; and groupId = &#63; and classname = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param classname the classname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry fetchByU_G_c_First(long userId, long groupId,
		String classname, OrderByComparator orderByComparator)
		throws SystemException {
		List<AuditEntry> list = findByU_G_c(userId, groupId, classname, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last audit entry in the ordered set where userId = &#63; and groupId = &#63; and classname = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param classname the classname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry findByU_G_c_Last(long userId, long groupId,
		String classname, OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = fetchByU_G_c_Last(userId, groupId, classname,
				orderByComparator);

		if (auditEntry != null) {
			return auditEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(", classname=");
		msg.append(classname);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAuditEntryException(msg.toString());
	}

	/**
	 * Returns the last audit entry in the ordered set where userId = &#63; and groupId = &#63; and classname = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param classname the classname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry fetchByU_G_c_Last(long userId, long groupId,
		String classname, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByU_G_c(userId, groupId, classname);

		List<AuditEntry> list = findByU_G_c(userId, groupId, classname,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the audit entries before and after the current audit entry in the ordered set where userId = &#63; and groupId = &#63; and classname = &#63;.
	 *
	 * @param auditId the primary key of the current audit entry
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param classname the classname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry[] findByU_G_c_PrevAndNext(long auditId, long userId,
		long groupId, String classname, OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = findByPrimaryKey(auditId);

		Session session = null;

		try {
			session = openSession();

			AuditEntry[] array = new AuditEntryImpl[3];

			array[0] = getByU_G_c_PrevAndNext(session, auditEntry, userId,
					groupId, classname, orderByComparator, true);

			array[1] = auditEntry;

			array[2] = getByU_G_c_PrevAndNext(session, auditEntry, userId,
					groupId, classname, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AuditEntry getByU_G_c_PrevAndNext(Session session,
		AuditEntry auditEntry, long userId, long groupId, String classname,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_AUDITENTRY_WHERE);

		query.append(_FINDER_COLUMN_U_G_C_USERID_2);

		query.append(_FINDER_COLUMN_U_G_C_GROUPID_2);

		if (classname == null) {
			query.append(_FINDER_COLUMN_U_G_C_CLASSNAME_1);
		}
		else {
			if (classname.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_U_G_C_CLASSNAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_U_G_C_CLASSNAME_2);
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
			query.append(AuditEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(groupId);

		if (classname != null) {
			qPos.add(classname);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(auditEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AuditEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the audit entries where classname = &#63; and classPK = &#63;.
	 *
	 * @param classname the classname
	 * @param classPK the class p k
	 * @return the matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByCN_CP(String classname, long classPK)
		throws SystemException {
		return findByCN_CP(classname, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the audit entries where classname = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classname the classname
	 * @param classPK the class p k
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @return the range of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByCN_CP(String classname, long classPK,
		int start, int end) throws SystemException {
		return findByCN_CP(classname, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the audit entries where classname = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classname the classname
	 * @param classPK the class p k
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findByCN_CP(String classname, long classPK,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CP;
			finderArgs = new Object[] { classname, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CN_CP;
			finderArgs = new Object[] {
					classname, classPK,
					
					start, end, orderByComparator
				};
		}

		List<AuditEntry> list = (List<AuditEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AuditEntry auditEntry : list) {
				if (!Validator.equals(classname, auditEntry.getClassname()) ||
						(classPK != auditEntry.getClassPK())) {
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

			query.append(_SQL_SELECT_AUDITENTRY_WHERE);

			if (classname == null) {
				query.append(_FINDER_COLUMN_CN_CP_CLASSNAME_1);
			}
			else {
				if (classname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_CN_CP_CLASSNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_CN_CP_CLASSNAME_2);
				}
			}

			query.append(_FINDER_COLUMN_CN_CP_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AuditEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (classname != null) {
					qPos.add(classname);
				}

				qPos.add(classPK);

				list = (List<AuditEntry>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first audit entry in the ordered set where classname = &#63; and classPK = &#63;.
	 *
	 * @param classname the classname
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry findByCN_CP_First(String classname, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = fetchByCN_CP_First(classname, classPK,
				orderByComparator);

		if (auditEntry != null) {
			return auditEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classname=");
		msg.append(classname);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAuditEntryException(msg.toString());
	}

	/**
	 * Returns the first audit entry in the ordered set where classname = &#63; and classPK = &#63;.
	 *
	 * @param classname the classname
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit entry, or <code>null</code> if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry fetchByCN_CP_First(String classname, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		List<AuditEntry> list = findByCN_CP(classname, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last audit entry in the ordered set where classname = &#63; and classPK = &#63;.
	 *
	 * @param classname the classname
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry findByCN_CP_Last(String classname, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = fetchByCN_CP_Last(classname, classPK,
				orderByComparator);

		if (auditEntry != null) {
			return auditEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classname=");
		msg.append(classname);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAuditEntryException(msg.toString());
	}

	/**
	 * Returns the last audit entry in the ordered set where classname = &#63; and classPK = &#63;.
	 *
	 * @param classname the classname
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit entry, or <code>null</code> if a matching audit entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry fetchByCN_CP_Last(String classname, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCN_CP(classname, classPK);

		List<AuditEntry> list = findByCN_CP(classname, classPK, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the audit entries before and after the current audit entry in the ordered set where classname = &#63; and classPK = &#63;.
	 *
	 * @param auditId the primary key of the current audit entry
	 * @param classname the classname
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next audit entry
	 * @throws com.liferay.lms.NoSuchAuditEntryException if a audit entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AuditEntry[] findByCN_CP_PrevAndNext(long auditId, String classname,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchAuditEntryException, SystemException {
		AuditEntry auditEntry = findByPrimaryKey(auditId);

		Session session = null;

		try {
			session = openSession();

			AuditEntry[] array = new AuditEntryImpl[3];

			array[0] = getByCN_CP_PrevAndNext(session, auditEntry, classname,
					classPK, orderByComparator, true);

			array[1] = auditEntry;

			array[2] = getByCN_CP_PrevAndNext(session, auditEntry, classname,
					classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AuditEntry getByCN_CP_PrevAndNext(Session session,
		AuditEntry auditEntry, String classname, long classPK,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_AUDITENTRY_WHERE);

		if (classname == null) {
			query.append(_FINDER_COLUMN_CN_CP_CLASSNAME_1);
		}
		else {
			if (classname.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CN_CP_CLASSNAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_CN_CP_CLASSNAME_2);
			}
		}

		query.append(_FINDER_COLUMN_CN_CP_CLASSPK_2);

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
			query.append(AuditEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (classname != null) {
			qPos.add(classname);
		}

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(auditEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AuditEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the audit entries.
	 *
	 * @return the audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the audit entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @return the range of audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the audit entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of audit entries
	 * @param end the upper bound of the range of audit entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AuditEntry> findAll(int start, int end,
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

		List<AuditEntry> list = (List<AuditEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_AUDITENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_AUDITENTRY.concat(AuditEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AuditEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AuditEntry>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the audit entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (AuditEntry auditEntry : findByGroupId(groupId)) {
			remove(auditEntry);
		}
	}

	/**
	 * Removes all the audit entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (AuditEntry auditEntry : findByCompanyId(companyId)) {
			remove(auditEntry);
		}
	}

	/**
	 * Removes all the audit entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (AuditEntry auditEntry : findByUserId(userId)) {
			remove(auditEntry);
		}
	}

	/**
	 * Removes all the audit entries where userId = &#63; and groupId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_G(long userId, long groupId)
		throws SystemException {
		for (AuditEntry auditEntry : findByU_G(userId, groupId)) {
			remove(auditEntry);
		}
	}

	/**
	 * Removes all the audit entries where userId = &#63; and groupId = &#63; and classname = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param classname the classname
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_G_c(long userId, long groupId, String classname)
		throws SystemException {
		for (AuditEntry auditEntry : findByU_G_c(userId, groupId, classname)) {
			remove(auditEntry);
		}
	}

	/**
	 * Removes all the audit entries where classname = &#63; and classPK = &#63; from the database.
	 *
	 * @param classname the classname
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCN_CP(String classname, long classPK)
		throws SystemException {
		for (AuditEntry auditEntry : findByCN_CP(classname, classPK)) {
			remove(auditEntry);
		}
	}

	/**
	 * Removes all the audit entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (AuditEntry auditEntry : findAll()) {
			remove(auditEntry);
		}
	}

	/**
	 * Returns the number of audit entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_AUDITENTRY_WHERE);

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
	 * Returns the number of audit entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_AUDITENTRY_WHERE);

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
	 * Returns the number of audit entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_AUDITENTRY_WHERE);

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
	 * Returns the number of audit entries where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the number of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_G(long userId, long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { userId, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_AUDITENTRY_WHERE);

			query.append(_FINDER_COLUMN_U_G_USERID_2);

			query.append(_FINDER_COLUMN_U_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_G, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of audit entries where userId = &#63; and groupId = &#63; and classname = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param classname the classname
	 * @return the number of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_G_c(long userId, long groupId, String classname)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, groupId, classname };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_G_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_AUDITENTRY_WHERE);

			query.append(_FINDER_COLUMN_U_G_C_USERID_2);

			query.append(_FINDER_COLUMN_U_G_C_GROUPID_2);

			if (classname == null) {
				query.append(_FINDER_COLUMN_U_G_C_CLASSNAME_1);
			}
			else {
				if (classname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_G_C_CLASSNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_G_C_CLASSNAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(groupId);

				if (classname != null) {
					qPos.add(classname);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_G_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of audit entries where classname = &#63; and classPK = &#63;.
	 *
	 * @param classname the classname
	 * @param classPK the class p k
	 * @return the number of matching audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCN_CP(String classname, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { classname, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CN_CP,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_AUDITENTRY_WHERE);

			if (classname == null) {
				query.append(_FINDER_COLUMN_CN_CP_CLASSNAME_1);
			}
			else {
				if (classname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_CN_CP_CLASSNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_CN_CP_CLASSNAME_2);
				}
			}

			query.append(_FINDER_COLUMN_CN_CP_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (classname != null) {
					qPos.add(classname);
				}

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CN_CP,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of audit entries.
	 *
	 * @return the number of audit entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_AUDITENTRY);

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
	 * Initializes the audit entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.AuditEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AuditEntry>> listenersList = new ArrayList<ModelListener<AuditEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AuditEntry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(AuditEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_AUDITENTRY = "SELECT auditEntry FROM AuditEntry auditEntry";
	private static final String _SQL_SELECT_AUDITENTRY_WHERE = "SELECT auditEntry FROM AuditEntry auditEntry WHERE ";
	private static final String _SQL_COUNT_AUDITENTRY = "SELECT COUNT(auditEntry) FROM AuditEntry auditEntry";
	private static final String _SQL_COUNT_AUDITENTRY_WHERE = "SELECT COUNT(auditEntry) FROM AuditEntry auditEntry WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "auditEntry.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "auditEntry.companyId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "auditEntry.userId = ?";
	private static final String _FINDER_COLUMN_U_G_USERID_2 = "auditEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_G_GROUPID_2 = "auditEntry.groupId = ?";
	private static final String _FINDER_COLUMN_U_G_C_USERID_2 = "auditEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_G_C_GROUPID_2 = "auditEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_U_G_C_CLASSNAME_1 = "auditEntry.classname IS NULL";
	private static final String _FINDER_COLUMN_U_G_C_CLASSNAME_2 = "auditEntry.classname = ?";
	private static final String _FINDER_COLUMN_U_G_C_CLASSNAME_3 = "(auditEntry.classname IS NULL OR auditEntry.classname = ?)";
	private static final String _FINDER_COLUMN_CN_CP_CLASSNAME_1 = "auditEntry.classname IS NULL AND ";
	private static final String _FINDER_COLUMN_CN_CP_CLASSNAME_2 = "auditEntry.classname = ? AND ";
	private static final String _FINDER_COLUMN_CN_CP_CLASSNAME_3 = "(auditEntry.classname IS NULL OR auditEntry.classname = ?) AND ";
	private static final String _FINDER_COLUMN_CN_CP_CLASSPK_2 = "auditEntry.classPK = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "auditEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AuditEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AuditEntry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(AuditEntryPersistenceImpl.class);
	private static AuditEntry _nullAuditEntry = new AuditEntryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AuditEntry> toCacheModel() {
				return _nullAuditEntryCacheModel;
			}
		};

	private static CacheModel<AuditEntry> _nullAuditEntryCacheModel = new CacheModel<AuditEntry>() {
			public AuditEntry toEntityModel() {
				return _nullAuditEntry;
			}
		};
}