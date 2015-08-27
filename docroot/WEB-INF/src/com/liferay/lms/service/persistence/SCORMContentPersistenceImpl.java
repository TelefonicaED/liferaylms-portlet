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

import com.liferay.lms.NoSuchSCORMContentException;
import com.liferay.lms.model.SCORMContent;
import com.liferay.lms.model.impl.SCORMContentImpl;
import com.liferay.lms.model.impl.SCORMContentModelImpl;

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

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the s c o r m content service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see SCORMContentPersistence
 * @see SCORMContentUtil
 * @generated
 */
public class SCORMContentPersistenceImpl extends BasePersistenceImpl<SCORMContent>
	implements SCORMContentPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SCORMContentUtil} to access the s c o r m content persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SCORMContentImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			SCORMContentModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			SCORMContentModelImpl.UUID_COLUMN_BITMASK |
			SCORMContentModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SCORMID = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByscormId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SCORMID =
		new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByscormId",
			new String[] { Long.class.getName() },
			SCORMContentModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SCORMID = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByscormId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			SCORMContentModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			SCORMContentModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			SCORMContentModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERIDGROUPID =
		new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserIdGroupId",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERIDGROUPID =
		new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserIdGroupId",
			new String[] { Long.class.getName(), Long.class.getName() },
			SCORMContentModelImpl.USERID_COLUMN_BITMASK |
			SCORMContentModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERIDGROUPID = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserIdGroupId",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, SCORMContentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the s c o r m content in the entity cache if it is enabled.
	 *
	 * @param scormContent the s c o r m content
	 */
	public void cacheResult(SCORMContent scormContent) {
		EntityCacheUtil.putResult(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentImpl.class, scormContent.getPrimaryKey(), scormContent);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				scormContent.getUuid(), Long.valueOf(scormContent.getGroupId())
			}, scormContent);

		scormContent.resetOriginalValues();
	}

	/**
	 * Caches the s c o r m contents in the entity cache if it is enabled.
	 *
	 * @param scormContents the s c o r m contents
	 */
	public void cacheResult(List<SCORMContent> scormContents) {
		for (SCORMContent scormContent : scormContents) {
			if (EntityCacheUtil.getResult(
						SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
						SCORMContentImpl.class, scormContent.getPrimaryKey()) == null) {
				cacheResult(scormContent);
			}
			else {
				scormContent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all s c o r m contents.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SCORMContentImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SCORMContentImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the s c o r m content.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SCORMContent scormContent) {
		EntityCacheUtil.removeResult(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentImpl.class, scormContent.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(scormContent);
	}

	@Override
	public void clearCache(List<SCORMContent> scormContents) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SCORMContent scormContent : scormContents) {
			EntityCacheUtil.removeResult(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
				SCORMContentImpl.class, scormContent.getPrimaryKey());

			clearUniqueFindersCache(scormContent);
		}
	}

	protected void clearUniqueFindersCache(SCORMContent scormContent) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				scormContent.getUuid(), Long.valueOf(scormContent.getGroupId())
			});
	}

	/**
	 * Creates a new s c o r m content with the primary key. Does not add the s c o r m content to the database.
	 *
	 * @param scormId the primary key for the new s c o r m content
	 * @return the new s c o r m content
	 */
	public SCORMContent create(long scormId) {
		SCORMContent scormContent = new SCORMContentImpl();

		scormContent.setNew(true);
		scormContent.setPrimaryKey(scormId);

		String uuid = PortalUUIDUtil.generate();

		scormContent.setUuid(uuid);

		return scormContent;
	}

	/**
	 * Removes the s c o r m content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param scormId the primary key of the s c o r m content
	 * @return the s c o r m content that was removed
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent remove(long scormId)
		throws NoSuchSCORMContentException, SystemException {
		return remove(Long.valueOf(scormId));
	}

	/**
	 * Removes the s c o r m content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the s c o r m content
	 * @return the s c o r m content that was removed
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCORMContent remove(Serializable primaryKey)
		throws NoSuchSCORMContentException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCORMContent scormContent = (SCORMContent)session.get(SCORMContentImpl.class,
					primaryKey);

			if (scormContent == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSCORMContentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(scormContent);
		}
		catch (NoSuchSCORMContentException nsee) {
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
	protected SCORMContent removeImpl(SCORMContent scormContent)
		throws SystemException {
		scormContent = toUnwrappedModel(scormContent);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, scormContent);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(scormContent);

		return scormContent;
	}

	@Override
	public SCORMContent updateImpl(
		com.liferay.lms.model.SCORMContent scormContent, boolean merge)
		throws SystemException {
		scormContent = toUnwrappedModel(scormContent);

		boolean isNew = scormContent.isNew();

		SCORMContentModelImpl scormContentModelImpl = (SCORMContentModelImpl)scormContent;

		if (Validator.isNull(scormContent.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			scormContent.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, scormContent, merge);

			scormContent.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SCORMContentModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((scormContentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						scormContentModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { scormContentModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((scormContentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SCORMID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scormContentModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SCORMID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SCORMID,
					args);

				args = new Object[] {
						Long.valueOf(scormContentModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SCORMID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SCORMID,
					args);
			}

			if ((scormContentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scormContentModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(scormContentModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((scormContentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scormContentModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(scormContentModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((scormContentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scormContentModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(scormContentModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((scormContentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERIDGROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scormContentModelImpl.getOriginalUserId()),
						Long.valueOf(scormContentModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERIDGROUPID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERIDGROUPID,
					args);

				args = new Object[] {
						Long.valueOf(scormContentModelImpl.getUserId()),
						Long.valueOf(scormContentModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERIDGROUPID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERIDGROUPID,
					args);
			}
		}

		EntityCacheUtil.putResult(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
			SCORMContentImpl.class, scormContent.getPrimaryKey(), scormContent);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					scormContent.getUuid(),
					Long.valueOf(scormContent.getGroupId())
				}, scormContent);
		}
		else {
			if ((scormContentModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						scormContentModelImpl.getOriginalUuid(),
						Long.valueOf(scormContentModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						scormContent.getUuid(),
						Long.valueOf(scormContent.getGroupId())
					}, scormContent);
			}
		}

		return scormContent;
	}

	protected SCORMContent toUnwrappedModel(SCORMContent scormContent) {
		if (scormContent instanceof SCORMContentImpl) {
			return scormContent;
		}

		SCORMContentImpl scormContentImpl = new SCORMContentImpl();

		scormContentImpl.setNew(scormContent.isNew());
		scormContentImpl.setPrimaryKey(scormContent.getPrimaryKey());

		scormContentImpl.setUuid(scormContent.getUuid());
		scormContentImpl.setScormId(scormContent.getScormId());
		scormContentImpl.setCompanyId(scormContent.getCompanyId());
		scormContentImpl.setGroupId(scormContent.getGroupId());
		scormContentImpl.setUserId(scormContent.getUserId());
		scormContentImpl.setStatus(scormContent.getStatus());
		scormContentImpl.setStatusByUserId(scormContent.getStatusByUserId());
		scormContentImpl.setStatusByUserName(scormContent.getStatusByUserName());
		scormContentImpl.setStatusDate(scormContent.getStatusDate());
		scormContentImpl.setTitle(scormContent.getTitle());
		scormContentImpl.setDescription(scormContent.getDescription());
		scormContentImpl.setIndex(scormContent.getIndex());
		scormContentImpl.setCiphered(scormContent.isCiphered());

		return scormContentImpl;
	}

	/**
	 * Returns the s c o r m content with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c o r m content
	 * @return the s c o r m content
	 * @throws com.liferay.portal.NoSuchModelException if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCORMContent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the s c o r m content with the primary key or throws a {@link com.liferay.lms.NoSuchSCORMContentException} if it could not be found.
	 *
	 * @param scormId the primary key of the s c o r m content
	 * @return the s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByPrimaryKey(long scormId)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByPrimaryKey(scormId);

		if (scormContent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + scormId);
			}

			throw new NoSuchSCORMContentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				scormId);
		}

		return scormContent;
	}

	/**
	 * Returns the s c o r m content with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c o r m content
	 * @return the s c o r m content, or <code>null</code> if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCORMContent fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the s c o r m content with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param scormId the primary key of the s c o r m content
	 * @return the s c o r m content, or <code>null</code> if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByPrimaryKey(long scormId)
		throws SystemException {
		SCORMContent scormContent = (SCORMContent)EntityCacheUtil.getResult(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
				SCORMContentImpl.class, scormId);

		if (scormContent == _nullSCORMContent) {
			return null;
		}

		if (scormContent == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				scormContent = (SCORMContent)session.get(SCORMContentImpl.class,
						Long.valueOf(scormId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (scormContent != null) {
					cacheResult(scormContent);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SCORMContentModelImpl.ENTITY_CACHE_ENABLED,
						SCORMContentImpl.class, scormId, _nullSCORMContent);
				}

				closeSession(session);
			}
		}

		return scormContent;
	}

	/**
	 * Returns all the s c o r m contents where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c o r m contents where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @return the range of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c o r m contents where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByUuid(String uuid, int start, int end,
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

		List<SCORMContent> list = (List<SCORMContent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCORMContent scormContent : list) {
				if (!Validator.equals(uuid, scormContent.getUuid())) {
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

			query.append(_SQL_SELECT_SCORMCONTENT_WHERE);

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
				query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
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

				list = (List<SCORMContent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s c o r m content in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByUuid_First(uuid, orderByComparator);

		if (scormContent != null) {
			return scormContent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSCORMContentException(msg.toString());
	}

	/**
	 * Returns the first s c o r m content in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCORMContent> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c o r m content in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByUuid_Last(uuid, orderByComparator);

		if (scormContent != null) {
			return scormContent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSCORMContentException(msg.toString());
	}

	/**
	 * Returns the last s c o r m content in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<SCORMContent> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c o r m contents before and after the current s c o r m content in the ordered set where uuid = &#63;.
	 *
	 * @param scormId the primary key of the current s c o r m content
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent[] findByUuid_PrevAndNext(long scormId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = findByPrimaryKey(scormId);

		Session session = null;

		try {
			session = openSession();

			SCORMContent[] array = new SCORMContentImpl[3];

			array[0] = getByUuid_PrevAndNext(session, scormContent, uuid,
					orderByComparator, true);

			array[1] = scormContent;

			array[2] = getByUuid_PrevAndNext(session, scormContent, uuid,
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

	protected SCORMContent getByUuid_PrevAndNext(Session session,
		SCORMContent scormContent, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCORMCONTENT_WHERE);

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
			query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(scormContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCORMContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the s c o r m content where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lms.NoSuchSCORMContentException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByUUID_G(String uuid, long groupId)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByUUID_G(uuid, groupId);

		if (scormContent == null) {
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

			throw new NoSuchSCORMContentException(msg.toString());
		}

		return scormContent;
	}

	/**
	 * Returns the s c o r m content where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the s c o r m content where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof SCORMContent) {
			SCORMContent scormContent = (SCORMContent)result;

			if (!Validator.equals(uuid, scormContent.getUuid()) ||
					(groupId != scormContent.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SCORMCONTENT_WHERE);

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

			query.append(SCORMContentModelImpl.ORDER_BY_JPQL);

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

				List<SCORMContent> list = q.list();

				result = list;

				SCORMContent scormContent = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					scormContent = list.get(0);

					cacheResult(scormContent);

					if ((scormContent.getUuid() == null) ||
							!scormContent.getUuid().equals(uuid) ||
							(scormContent.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, scormContent);
					}
				}

				return scormContent;
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
				return (SCORMContent)result;
			}
		}
	}

	/**
	 * Returns all the s c o r m contents where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByscormId(long groupId)
		throws SystemException {
		return findByscormId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c o r m contents where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @return the range of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByscormId(long groupId, int start, int end)
		throws SystemException {
		return findByscormId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c o r m contents where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByscormId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SCORMID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SCORMID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<SCORMContent> list = (List<SCORMContent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCORMContent scormContent : list) {
				if ((groupId != scormContent.getGroupId())) {
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

			query.append(_SQL_SELECT_SCORMCONTENT_WHERE);

			query.append(_FINDER_COLUMN_SCORMID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<SCORMContent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s c o r m content in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByscormId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByscormId_First(groupId,
				orderByComparator);

		if (scormContent != null) {
			return scormContent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSCORMContentException(msg.toString());
	}

	/**
	 * Returns the first s c o r m content in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByscormId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCORMContent> list = findByscormId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c o r m content in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByscormId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByscormId_Last(groupId,
				orderByComparator);

		if (scormContent != null) {
			return scormContent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSCORMContentException(msg.toString());
	}

	/**
	 * Returns the last s c o r m content in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByscormId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByscormId(groupId);

		List<SCORMContent> list = findByscormId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c o r m contents before and after the current s c o r m content in the ordered set where groupId = &#63;.
	 *
	 * @param scormId the primary key of the current s c o r m content
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent[] findByscormId_PrevAndNext(long scormId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = findByPrimaryKey(scormId);

		Session session = null;

		try {
			session = openSession();

			SCORMContent[] array = new SCORMContentImpl[3];

			array[0] = getByscormId_PrevAndNext(session, scormContent, groupId,
					orderByComparator, true);

			array[1] = scormContent;

			array[2] = getByscormId_PrevAndNext(session, scormContent, groupId,
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

	protected SCORMContent getByscormId_PrevAndNext(Session session,
		SCORMContent scormContent, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCORMCONTENT_WHERE);

		query.append(_FINDER_COLUMN_SCORMID_GROUPID_2);

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
			query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scormContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCORMContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c o r m contents that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching s c o r m contents that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> filterFindByscormId(long groupId)
		throws SystemException {
		return filterFindByscormId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c o r m contents that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @return the range of matching s c o r m contents that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> filterFindByscormId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByscormId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c o r m contents that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c o r m contents that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> filterFindByscormId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByscormId(groupId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_SCORMID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCORMContentModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCORMContent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SCORMContentImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SCORMContentImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<SCORMContent>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s c o r m contents before and after the current s c o r m content in the ordered set of s c o r m contents that the user has permission to view where groupId = &#63;.
	 *
	 * @param scormId the primary key of the current s c o r m content
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent[] filterFindByscormId_PrevAndNext(long scormId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByscormId_PrevAndNext(scormId, groupId, orderByComparator);
		}

		SCORMContent scormContent = findByPrimaryKey(scormId);

		Session session = null;

		try {
			session = openSession();

			SCORMContent[] array = new SCORMContentImpl[3];

			array[0] = filterGetByscormId_PrevAndNext(session, scormContent,
					groupId, orderByComparator, true);

			array[1] = scormContent;

			array[2] = filterGetByscormId_PrevAndNext(session, scormContent,
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

	protected SCORMContent filterGetByscormId_PrevAndNext(Session session,
		SCORMContent scormContent, long groupId,
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
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_SCORMID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCORMContentModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCORMContent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SCORMContentImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SCORMContentImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scormContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCORMContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c o r m contents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the s c o r m contents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @return the range of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c o r m contents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<SCORMContent> list = (List<SCORMContent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCORMContent scormContent : list) {
				if ((companyId != scormContent.getCompanyId())) {
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

			query.append(_SQL_SELECT_SCORMCONTENT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<SCORMContent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s c o r m content in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (scormContent != null) {
			return scormContent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSCORMContentException(msg.toString());
	}

	/**
	 * Returns the first s c o r m content in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCORMContent> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c o r m content in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (scormContent != null) {
			return scormContent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSCORMContentException(msg.toString());
	}

	/**
	 * Returns the last s c o r m content in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<SCORMContent> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c o r m contents before and after the current s c o r m content in the ordered set where companyId = &#63;.
	 *
	 * @param scormId the primary key of the current s c o r m content
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent[] findByCompanyId_PrevAndNext(long scormId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = findByPrimaryKey(scormId);

		Session session = null;

		try {
			session = openSession();

			SCORMContent[] array = new SCORMContentImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, scormContent,
					companyId, orderByComparator, true);

			array[1] = scormContent;

			array[2] = getByCompanyId_PrevAndNext(session, scormContent,
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

	protected SCORMContent getByCompanyId_PrevAndNext(Session session,
		SCORMContent scormContent, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCORMCONTENT_WHERE);

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
			query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scormContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCORMContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c o r m contents where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c o r m contents where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @return the range of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c o r m contents where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByUserId(long userId, int start, int end,
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

		List<SCORMContent> list = (List<SCORMContent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCORMContent scormContent : list) {
				if ((userId != scormContent.getUserId())) {
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

			query.append(_SQL_SELECT_SCORMCONTENT_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<SCORMContent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s c o r m content in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByUserId_First(userId,
				orderByComparator);

		if (scormContent != null) {
			return scormContent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSCORMContentException(msg.toString());
	}

	/**
	 * Returns the first s c o r m content in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCORMContent> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c o r m content in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByUserId_Last(userId, orderByComparator);

		if (scormContent != null) {
			return scormContent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSCORMContentException(msg.toString());
	}

	/**
	 * Returns the last s c o r m content in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<SCORMContent> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c o r m contents before and after the current s c o r m content in the ordered set where userId = &#63;.
	 *
	 * @param scormId the primary key of the current s c o r m content
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent[] findByUserId_PrevAndNext(long scormId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = findByPrimaryKey(scormId);

		Session session = null;

		try {
			session = openSession();

			SCORMContent[] array = new SCORMContentImpl[3];

			array[0] = getByUserId_PrevAndNext(session, scormContent, userId,
					orderByComparator, true);

			array[1] = scormContent;

			array[2] = getByUserId_PrevAndNext(session, scormContent, userId,
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

	protected SCORMContent getByUserId_PrevAndNext(Session session,
		SCORMContent scormContent, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCORMCONTENT_WHERE);

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
			query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scormContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCORMContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c o r m contents where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c o r m contents where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @return the range of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c o r m contents where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByGroupId(long groupId, int start, int end,
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

		List<SCORMContent> list = (List<SCORMContent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCORMContent scormContent : list) {
				if ((groupId != scormContent.getGroupId())) {
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

			query.append(_SQL_SELECT_SCORMCONTENT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<SCORMContent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s c o r m content in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByGroupId_First(groupId,
				orderByComparator);

		if (scormContent != null) {
			return scormContent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSCORMContentException(msg.toString());
	}

	/**
	 * Returns the first s c o r m content in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCORMContent> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c o r m content in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (scormContent != null) {
			return scormContent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSCORMContentException(msg.toString());
	}

	/**
	 * Returns the last s c o r m content in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<SCORMContent> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c o r m contents before and after the current s c o r m content in the ordered set where groupId = &#63;.
	 *
	 * @param scormId the primary key of the current s c o r m content
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent[] findByGroupId_PrevAndNext(long scormId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = findByPrimaryKey(scormId);

		Session session = null;

		try {
			session = openSession();

			SCORMContent[] array = new SCORMContentImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, scormContent, groupId,
					orderByComparator, true);

			array[1] = scormContent;

			array[2] = getByGroupId_PrevAndNext(session, scormContent, groupId,
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

	protected SCORMContent getByGroupId_PrevAndNext(Session session,
		SCORMContent scormContent, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCORMCONTENT_WHERE);

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
			query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scormContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCORMContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c o r m contents that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching s c o r m contents that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c o r m contents that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @return the range of matching s c o r m contents that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c o r m contents that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c o r m contents that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> filterFindByGroupId(long groupId, int start,
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
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCORMContentModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCORMContent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SCORMContentImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SCORMContentImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<SCORMContent>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s c o r m contents before and after the current s c o r m content in the ordered set of s c o r m contents that the user has permission to view where groupId = &#63;.
	 *
	 * @param scormId the primary key of the current s c o r m content
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent[] filterFindByGroupId_PrevAndNext(long scormId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(scormId, groupId, orderByComparator);
		}

		SCORMContent scormContent = findByPrimaryKey(scormId);

		Session session = null;

		try {
			session = openSession();

			SCORMContent[] array = new SCORMContentImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, scormContent,
					groupId, orderByComparator, true);

			array[1] = scormContent;

			array[2] = filterGetByGroupId_PrevAndNext(session, scormContent,
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

	protected SCORMContent filterGetByGroupId_PrevAndNext(Session session,
		SCORMContent scormContent, long groupId,
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
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCORMContentModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCORMContent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SCORMContentImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SCORMContentImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scormContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCORMContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c o r m contents where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByUserIdGroupId(long userId, long groupId)
		throws SystemException {
		return findByUserIdGroupId(userId, groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c o r m contents where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @return the range of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByUserIdGroupId(long userId, long groupId,
		int start, int end) throws SystemException {
		return findByUserIdGroupId(userId, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c o r m contents where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findByUserIdGroupId(long userId, long groupId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERIDGROUPID;
			finderArgs = new Object[] { userId, groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERIDGROUPID;
			finderArgs = new Object[] {
					userId, groupId,
					
					start, end, orderByComparator
				};
		}

		List<SCORMContent> list = (List<SCORMContent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCORMContent scormContent : list) {
				if ((userId != scormContent.getUserId()) ||
						(groupId != scormContent.getGroupId())) {
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

			query.append(_SQL_SELECT_SCORMCONTENT_WHERE);

			query.append(_FINDER_COLUMN_USERIDGROUPID_USERID_2);

			query.append(_FINDER_COLUMN_USERIDGROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(groupId);

				list = (List<SCORMContent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s c o r m content in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByUserIdGroupId_First(long userId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByUserIdGroupId_First(userId, groupId,
				orderByComparator);

		if (scormContent != null) {
			return scormContent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSCORMContentException(msg.toString());
	}

	/**
	 * Returns the first s c o r m content in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByUserIdGroupId_First(long userId, long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCORMContent> list = findByUserIdGroupId(userId, groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c o r m content in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent findByUserIdGroupId_Last(long userId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = fetchByUserIdGroupId_Last(userId, groupId,
				orderByComparator);

		if (scormContent != null) {
			return scormContent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSCORMContentException(msg.toString());
	}

	/**
	 * Returns the last s c o r m content in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c o r m content, or <code>null</code> if a matching s c o r m content could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent fetchByUserIdGroupId_Last(long userId, long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserIdGroupId(userId, groupId);

		List<SCORMContent> list = findByUserIdGroupId(userId, groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c o r m contents before and after the current s c o r m content in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param scormId the primary key of the current s c o r m content
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent[] findByUserIdGroupId_PrevAndNext(long scormId,
		long userId, long groupId, OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = findByPrimaryKey(scormId);

		Session session = null;

		try {
			session = openSession();

			SCORMContent[] array = new SCORMContentImpl[3];

			array[0] = getByUserIdGroupId_PrevAndNext(session, scormContent,
					userId, groupId, orderByComparator, true);

			array[1] = scormContent;

			array[2] = getByUserIdGroupId_PrevAndNext(session, scormContent,
					userId, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCORMContent getByUserIdGroupId_PrevAndNext(Session session,
		SCORMContent scormContent, long userId, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCORMCONTENT_WHERE);

		query.append(_FINDER_COLUMN_USERIDGROUPID_USERID_2);

		query.append(_FINDER_COLUMN_USERIDGROUPID_GROUPID_2);

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
			query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scormContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCORMContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c o r m contents that the user has permission to view where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the matching s c o r m contents that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> filterFindByUserIdGroupId(long userId,
		long groupId) throws SystemException {
		return filterFindByUserIdGroupId(userId, groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c o r m contents that the user has permission to view where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @return the range of matching s c o r m contents that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> filterFindByUserIdGroupId(long userId,
		long groupId, int start, int end) throws SystemException {
		return filterFindByUserIdGroupId(userId, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c o r m contents that the user has permissions to view where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c o r m contents that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> filterFindByUserIdGroupId(long userId,
		long groupId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByUserIdGroupId(userId, groupId, start, end,
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
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_USERIDGROUPID_USERID_2);

		query.append(_FINDER_COLUMN_USERIDGROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCORMContentModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCORMContent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SCORMContentImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SCORMContentImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			qPos.add(groupId);

			return (List<SCORMContent>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s c o r m contents before and after the current s c o r m content in the ordered set of s c o r m contents that the user has permission to view where userId = &#63; and groupId = &#63;.
	 *
	 * @param scormId the primary key of the current s c o r m content
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c o r m content
	 * @throws com.liferay.lms.NoSuchSCORMContentException if a s c o r m content with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent[] filterFindByUserIdGroupId_PrevAndNext(long scormId,
		long userId, long groupId, OrderByComparator orderByComparator)
		throws NoSuchSCORMContentException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByUserIdGroupId_PrevAndNext(scormId, userId, groupId,
				orderByComparator);
		}

		SCORMContent scormContent = findByPrimaryKey(scormId);

		Session session = null;

		try {
			session = openSession();

			SCORMContent[] array = new SCORMContentImpl[3];

			array[0] = filterGetByUserIdGroupId_PrevAndNext(session,
					scormContent, userId, groupId, orderByComparator, true);

			array[1] = scormContent;

			array[2] = filterGetByUserIdGroupId_PrevAndNext(session,
					scormContent, userId, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCORMContent filterGetByUserIdGroupId_PrevAndNext(
		Session session, SCORMContent scormContent, long userId, long groupId,
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
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_USERIDGROUPID_USERID_2);

		query.append(_FINDER_COLUMN_USERIDGROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SCORMContentModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCORMContentModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCORMContent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SCORMContentImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SCORMContentImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scormContent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCORMContent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c o r m contents.
	 *
	 * @return the s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c o r m contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @return the range of s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c o r m contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c o r m contents
	 * @param end the upper bound of the range of s c o r m contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCORMContent> findAll(int start, int end,
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

		List<SCORMContent> list = (List<SCORMContent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SCORMCONTENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SCORMCONTENT.concat(SCORMContentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SCORMContent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SCORMContent>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the s c o r m contents where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (SCORMContent scormContent : findByUuid(uuid)) {
			remove(scormContent);
		}
	}

	/**
	 * Removes the s c o r m content where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the s c o r m content that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SCORMContent removeByUUID_G(String uuid, long groupId)
		throws NoSuchSCORMContentException, SystemException {
		SCORMContent scormContent = findByUUID_G(uuid, groupId);

		return remove(scormContent);
	}

	/**
	 * Removes all the s c o r m contents where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByscormId(long groupId) throws SystemException {
		for (SCORMContent scormContent : findByscormId(groupId)) {
			remove(scormContent);
		}
	}

	/**
	 * Removes all the s c o r m contents where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (SCORMContent scormContent : findByCompanyId(companyId)) {
			remove(scormContent);
		}
	}

	/**
	 * Removes all the s c o r m contents where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (SCORMContent scormContent : findByUserId(userId)) {
			remove(scormContent);
		}
	}

	/**
	 * Removes all the s c o r m contents where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (SCORMContent scormContent : findByGroupId(groupId)) {
			remove(scormContent);
		}
	}

	/**
	 * Removes all the s c o r m contents where userId = &#63; and groupId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserIdGroupId(long userId, long groupId)
		throws SystemException {
		for (SCORMContent scormContent : findByUserIdGroupId(userId, groupId)) {
			remove(scormContent);
		}
	}

	/**
	 * Removes all the s c o r m contents from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SCORMContent scormContent : findAll()) {
			remove(scormContent);
		}
	}

	/**
	 * Returns the number of s c o r m contents where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCORMCONTENT_WHERE);

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
	 * Returns the number of s c o r m contents where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCORMCONTENT_WHERE);

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
	 * Returns the number of s c o r m contents where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public int countByscormId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SCORMID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCORMCONTENT_WHERE);

			query.append(_FINDER_COLUMN_SCORMID_GROUPID_2);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SCORMID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of s c o r m contents that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching s c o r m contents that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByscormId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByscormId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SCORMCONTENT_WHERE);

		query.append(_FINDER_COLUMN_SCORMID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCORMContent.class.getName(),
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
	 * Returns the number of s c o r m contents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCORMCONTENT_WHERE);

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
	 * Returns the number of s c o r m contents where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCORMCONTENT_WHERE);

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
	 * Returns the number of s c o r m contents where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCORMCONTENT_WHERE);

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
	 * Returns the number of s c o r m contents that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching s c o r m contents that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SCORMCONTENT_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCORMContent.class.getName(),
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
	 * Returns the number of s c o r m contents where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the number of matching s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserIdGroupId(long userId, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERIDGROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCORMCONTENT_WHERE);

			query.append(_FINDER_COLUMN_USERIDGROUPID_USERID_2);

			query.append(_FINDER_COLUMN_USERIDGROUPID_GROUPID_2);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERIDGROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of s c o r m contents that the user has permission to view where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the number of matching s c o r m contents that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByUserIdGroupId(long userId, long groupId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByUserIdGroupId(userId, groupId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SCORMCONTENT_WHERE);

		query.append(_FINDER_COLUMN_USERIDGROUPID_USERID_2);

		query.append(_FINDER_COLUMN_USERIDGROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCORMContent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

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
	 * Returns the number of s c o r m contents.
	 *
	 * @return the number of s c o r m contents
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SCORMCONTENT);

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
	 * Initializes the s c o r m content persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.SCORMContent")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCORMContent>> listenersList = new ArrayList<ModelListener<SCORMContent>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCORMContent>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SCORMContentImpl.class.getName());
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
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
	private static final String _SQL_SELECT_SCORMCONTENT = "SELECT scormContent FROM SCORMContent scormContent";
	private static final String _SQL_SELECT_SCORMCONTENT_WHERE = "SELECT scormContent FROM SCORMContent scormContent WHERE ";
	private static final String _SQL_COUNT_SCORMCONTENT = "SELECT COUNT(scormContent) FROM SCORMContent scormContent";
	private static final String _SQL_COUNT_SCORMCONTENT_WHERE = "SELECT COUNT(scormContent) FROM SCORMContent scormContent WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "scormContent.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "scormContent.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(scormContent.uuid IS NULL OR scormContent.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "scormContent.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "scormContent.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(scormContent.uuid IS NULL OR scormContent.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "scormContent.groupId = ?";
	private static final String _FINDER_COLUMN_SCORMID_GROUPID_2 = "scormContent.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "scormContent.companyId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "scormContent.userId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "scormContent.groupId = ?";
	private static final String _FINDER_COLUMN_USERIDGROUPID_USERID_2 = "scormContent.userId = ? AND ";
	private static final String _FINDER_COLUMN_USERIDGROUPID_GROUPID_2 = "scormContent.groupId = ?";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "scormContent.scormId";
	private static final String _FILTER_SQL_SELECT_SCORMCONTENT_WHERE = "SELECT DISTINCT {scormContent.*} FROM Lms_SCORMContent scormContent WHERE ";
	private static final String _FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {Lms_SCORMContent.*} FROM (SELECT DISTINCT scormContent.scormId FROM Lms_SCORMContent scormContent WHERE ";
	private static final String _FILTER_SQL_SELECT_SCORMCONTENT_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN Lms_SCORMContent ON TEMP_TABLE.scormId = Lms_SCORMContent.scormId";
	private static final String _FILTER_SQL_COUNT_SCORMCONTENT_WHERE = "SELECT COUNT(DISTINCT scormContent.scormId) AS COUNT_VALUE FROM Lms_SCORMContent scormContent WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "scormContent";
	private static final String _FILTER_ENTITY_TABLE = "Lms_SCORMContent";
	private static final String _ORDER_BY_ENTITY_ALIAS = "scormContent.";
	private static final String _ORDER_BY_ENTITY_TABLE = "Lms_SCORMContent.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SCORMContent exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SCORMContent exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(SCORMContentPersistenceImpl.class);
	private static SCORMContent _nullSCORMContent = new SCORMContentImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SCORMContent> toCacheModel() {
				return _nullSCORMContentCacheModel;
			}
		};

	private static CacheModel<SCORMContent> _nullSCORMContentCacheModel = new CacheModel<SCORMContent>() {
			public SCORMContent toEntityModel() {
				return _nullSCORMContent;
			}
		};
}