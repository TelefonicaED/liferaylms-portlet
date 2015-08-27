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

import com.liferay.lms.NoSuchTestAnswerException;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.impl.TestAnswerImpl;
import com.liferay.lms.model.impl.TestAnswerModelImpl;

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
 * The persistence implementation for the test answer service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see TestAnswerPersistence
 * @see TestAnswerUtil
 * @generated
 */
public class TestAnswerPersistenceImpl extends BasePersistenceImpl<TestAnswer>
	implements TestAnswerPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link TestAnswerUtil} to access the test answer persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = TestAnswerImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerModelImpl.FINDER_CACHE_ENABLED, TestAnswerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerModelImpl.FINDER_CACHE_ENABLED, TestAnswerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			TestAnswerModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_AC = new FinderPath(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerModelImpl.FINDER_CACHE_ENABLED, TestAnswerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByac",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AC = new FinderPath(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerModelImpl.FINDER_CACHE_ENABLED, TestAnswerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByac",
			new String[] { Long.class.getName() },
			TestAnswerModelImpl.ACTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_AC = new FinderPath(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByac",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_Q = new FinderPath(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerModelImpl.FINDER_CACHE_ENABLED, TestAnswerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByq",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_Q = new FinderPath(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerModelImpl.FINDER_CACHE_ENABLED, TestAnswerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByq",
			new String[] { Long.class.getName() },
			TestAnswerModelImpl.QUESTIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_Q = new FinderPath(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByq",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerModelImpl.FINDER_CACHE_ENABLED, TestAnswerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerModelImpl.FINDER_CACHE_ENABLED, TestAnswerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the test answer in the entity cache if it is enabled.
	 *
	 * @param testAnswer the test answer
	 */
	public void cacheResult(TestAnswer testAnswer) {
		EntityCacheUtil.putResult(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerImpl.class, testAnswer.getPrimaryKey(), testAnswer);

		testAnswer.resetOriginalValues();
	}

	/**
	 * Caches the test answers in the entity cache if it is enabled.
	 *
	 * @param testAnswers the test answers
	 */
	public void cacheResult(List<TestAnswer> testAnswers) {
		for (TestAnswer testAnswer : testAnswers) {
			if (EntityCacheUtil.getResult(
						TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
						TestAnswerImpl.class, testAnswer.getPrimaryKey()) == null) {
				cacheResult(testAnswer);
			}
			else {
				testAnswer.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all test answers.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(TestAnswerImpl.class.getName());
		}

		EntityCacheUtil.clearCache(TestAnswerImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the test answer.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestAnswer testAnswer) {
		EntityCacheUtil.removeResult(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerImpl.class, testAnswer.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<TestAnswer> testAnswers) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestAnswer testAnswer : testAnswers) {
			EntityCacheUtil.removeResult(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
				TestAnswerImpl.class, testAnswer.getPrimaryKey());
		}
	}

	/**
	 * Creates a new test answer with the primary key. Does not add the test answer to the database.
	 *
	 * @param answerId the primary key for the new test answer
	 * @return the new test answer
	 */
	public TestAnswer create(long answerId) {
		TestAnswer testAnswer = new TestAnswerImpl();

		testAnswer.setNew(true);
		testAnswer.setPrimaryKey(answerId);

		String uuid = PortalUUIDUtil.generate();

		testAnswer.setUuid(uuid);

		return testAnswer;
	}

	/**
	 * Removes the test answer with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param answerId the primary key of the test answer
	 * @return the test answer that was removed
	 * @throws com.liferay.lms.NoSuchTestAnswerException if a test answer with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer remove(long answerId)
		throws NoSuchTestAnswerException, SystemException {
		return remove(Long.valueOf(answerId));
	}

	/**
	 * Removes the test answer with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the test answer
	 * @return the test answer that was removed
	 * @throws com.liferay.lms.NoSuchTestAnswerException if a test answer with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TestAnswer remove(Serializable primaryKey)
		throws NoSuchTestAnswerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TestAnswer testAnswer = (TestAnswer)session.get(TestAnswerImpl.class,
					primaryKey);

			if (testAnswer == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestAnswerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(testAnswer);
		}
		catch (NoSuchTestAnswerException nsee) {
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
	protected TestAnswer removeImpl(TestAnswer testAnswer)
		throws SystemException {
		testAnswer = toUnwrappedModel(testAnswer);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, testAnswer);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(testAnswer);

		return testAnswer;
	}

	@Override
	public TestAnswer updateImpl(com.liferay.lms.model.TestAnswer testAnswer,
		boolean merge) throws SystemException {
		testAnswer = toUnwrappedModel(testAnswer);

		boolean isNew = testAnswer.isNew();

		TestAnswerModelImpl testAnswerModelImpl = (TestAnswerModelImpl)testAnswer;

		if (Validator.isNull(testAnswer.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			testAnswer.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, testAnswer, merge);

			testAnswer.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !TestAnswerModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((testAnswerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						testAnswerModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { testAnswerModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((testAnswerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AC.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(testAnswerModelImpl.getOriginalActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AC, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AC,
					args);

				args = new Object[] { Long.valueOf(testAnswerModelImpl.getActId()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AC, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AC,
					args);
			}

			if ((testAnswerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_Q.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(testAnswerModelImpl.getOriginalQuestionId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_Q, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_Q,
					args);

				args = new Object[] {
						Long.valueOf(testAnswerModelImpl.getQuestionId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_Q, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_Q,
					args);
			}
		}

		EntityCacheUtil.putResult(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
			TestAnswerImpl.class, testAnswer.getPrimaryKey(), testAnswer);

		return testAnswer;
	}

	protected TestAnswer toUnwrappedModel(TestAnswer testAnswer) {
		if (testAnswer instanceof TestAnswerImpl) {
			return testAnswer;
		}

		TestAnswerImpl testAnswerImpl = new TestAnswerImpl();

		testAnswerImpl.setNew(testAnswer.isNew());
		testAnswerImpl.setPrimaryKey(testAnswer.getPrimaryKey());

		testAnswerImpl.setUuid(testAnswer.getUuid());
		testAnswerImpl.setAnswerId(testAnswer.getAnswerId());
		testAnswerImpl.setQuestionId(testAnswer.getQuestionId());
		testAnswerImpl.setActId(testAnswer.getActId());
		testAnswerImpl.setPrecedence(testAnswer.getPrecedence());
		testAnswerImpl.setAnswer(testAnswer.getAnswer());
		testAnswerImpl.setIsCorrect(testAnswer.isIsCorrect());
		testAnswerImpl.setFeedbackCorrect(testAnswer.getFeedbackCorrect());
		testAnswerImpl.setFeedbacknocorrect(testAnswer.getFeedbacknocorrect());

		return testAnswerImpl;
	}

	/**
	 * Returns the test answer with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the test answer
	 * @return the test answer
	 * @throws com.liferay.portal.NoSuchModelException if a test answer with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TestAnswer findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the test answer with the primary key or throws a {@link com.liferay.lms.NoSuchTestAnswerException} if it could not be found.
	 *
	 * @param answerId the primary key of the test answer
	 * @return the test answer
	 * @throws com.liferay.lms.NoSuchTestAnswerException if a test answer with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer findByPrimaryKey(long answerId)
		throws NoSuchTestAnswerException, SystemException {
		TestAnswer testAnswer = fetchByPrimaryKey(answerId);

		if (testAnswer == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + answerId);
			}

			throw new NoSuchTestAnswerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				answerId);
		}

		return testAnswer;
	}

	/**
	 * Returns the test answer with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the test answer
	 * @return the test answer, or <code>null</code> if a test answer with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TestAnswer fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the test answer with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param answerId the primary key of the test answer
	 * @return the test answer, or <code>null</code> if a test answer with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer fetchByPrimaryKey(long answerId)
		throws SystemException {
		TestAnswer testAnswer = (TestAnswer)EntityCacheUtil.getResult(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
				TestAnswerImpl.class, answerId);

		if (testAnswer == _nullTestAnswer) {
			return null;
		}

		if (testAnswer == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				testAnswer = (TestAnswer)session.get(TestAnswerImpl.class,
						Long.valueOf(answerId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (testAnswer != null) {
					cacheResult(testAnswer);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(TestAnswerModelImpl.ENTITY_CACHE_ENABLED,
						TestAnswerImpl.class, answerId, _nullTestAnswer);
				}

				closeSession(session);
			}
		}

		return testAnswer;
	}

	/**
	 * Returns all the test answers where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching test answers
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestAnswer> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the test answers where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of test answers
	 * @param end the upper bound of the range of test answers (not inclusive)
	 * @return the range of matching test answers
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestAnswer> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the test answers where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of test answers
	 * @param end the upper bound of the range of test answers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching test answers
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestAnswer> findByUuid(String uuid, int start, int end,
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

		List<TestAnswer> list = (List<TestAnswer>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (TestAnswer testAnswer : list) {
				if (!Validator.equals(uuid, testAnswer.getUuid())) {
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

			query.append(_SQL_SELECT_TESTANSWER_WHERE);

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
				query.append(TestAnswerModelImpl.ORDER_BY_JPQL);
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

				list = (List<TestAnswer>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first test answer in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching test answer
	 * @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTestAnswerException, SystemException {
		TestAnswer testAnswer = fetchByUuid_First(uuid, orderByComparator);

		if (testAnswer != null) {
			return testAnswer;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTestAnswerException(msg.toString());
	}

	/**
	 * Returns the first test answer in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching test answer, or <code>null</code> if a matching test answer could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<TestAnswer> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last test answer in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching test answer
	 * @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTestAnswerException, SystemException {
		TestAnswer testAnswer = fetchByUuid_Last(uuid, orderByComparator);

		if (testAnswer != null) {
			return testAnswer;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTestAnswerException(msg.toString());
	}

	/**
	 * Returns the last test answer in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching test answer, or <code>null</code> if a matching test answer could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<TestAnswer> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the test answers before and after the current test answer in the ordered set where uuid = &#63;.
	 *
	 * @param answerId the primary key of the current test answer
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next test answer
	 * @throws com.liferay.lms.NoSuchTestAnswerException if a test answer with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer[] findByUuid_PrevAndNext(long answerId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTestAnswerException, SystemException {
		TestAnswer testAnswer = findByPrimaryKey(answerId);

		Session session = null;

		try {
			session = openSession();

			TestAnswer[] array = new TestAnswerImpl[3];

			array[0] = getByUuid_PrevAndNext(session, testAnswer, uuid,
					orderByComparator, true);

			array[1] = testAnswer;

			array[2] = getByUuid_PrevAndNext(session, testAnswer, uuid,
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

	protected TestAnswer getByUuid_PrevAndNext(Session session,
		TestAnswer testAnswer, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TESTANSWER_WHERE);

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
			query.append(TestAnswerModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(testAnswer);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<TestAnswer> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the test answers where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the matching test answers
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestAnswer> findByac(long actId) throws SystemException {
		return findByac(actId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the test answers where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of test answers
	 * @param end the upper bound of the range of test answers (not inclusive)
	 * @return the range of matching test answers
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestAnswer> findByac(long actId, int start, int end)
		throws SystemException {
		return findByac(actId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the test answers where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of test answers
	 * @param end the upper bound of the range of test answers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching test answers
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestAnswer> findByac(long actId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<TestAnswer> list = (List<TestAnswer>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (TestAnswer testAnswer : list) {
				if ((actId != testAnswer.getActId())) {
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

			query.append(_SQL_SELECT_TESTANSWER_WHERE);

			query.append(_FINDER_COLUMN_AC_ACTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(TestAnswerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				list = (List<TestAnswer>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first test answer in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching test answer
	 * @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer findByac_First(long actId,
		OrderByComparator orderByComparator)
		throws NoSuchTestAnswerException, SystemException {
		TestAnswer testAnswer = fetchByac_First(actId, orderByComparator);

		if (testAnswer != null) {
			return testAnswer;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTestAnswerException(msg.toString());
	}

	/**
	 * Returns the first test answer in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching test answer, or <code>null</code> if a matching test answer could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer fetchByac_First(long actId,
		OrderByComparator orderByComparator) throws SystemException {
		List<TestAnswer> list = findByac(actId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last test answer in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching test answer
	 * @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer findByac_Last(long actId,
		OrderByComparator orderByComparator)
		throws NoSuchTestAnswerException, SystemException {
		TestAnswer testAnswer = fetchByac_Last(actId, orderByComparator);

		if (testAnswer != null) {
			return testAnswer;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTestAnswerException(msg.toString());
	}

	/**
	 * Returns the last test answer in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching test answer, or <code>null</code> if a matching test answer could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer fetchByac_Last(long actId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByac(actId);

		List<TestAnswer> list = findByac(actId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the test answers before and after the current test answer in the ordered set where actId = &#63;.
	 *
	 * @param answerId the primary key of the current test answer
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next test answer
	 * @throws com.liferay.lms.NoSuchTestAnswerException if a test answer with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer[] findByac_PrevAndNext(long answerId, long actId,
		OrderByComparator orderByComparator)
		throws NoSuchTestAnswerException, SystemException {
		TestAnswer testAnswer = findByPrimaryKey(answerId);

		Session session = null;

		try {
			session = openSession();

			TestAnswer[] array = new TestAnswerImpl[3];

			array[0] = getByac_PrevAndNext(session, testAnswer, actId,
					orderByComparator, true);

			array[1] = testAnswer;

			array[2] = getByac_PrevAndNext(session, testAnswer, actId,
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

	protected TestAnswer getByac_PrevAndNext(Session session,
		TestAnswer testAnswer, long actId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TESTANSWER_WHERE);

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
			query.append(TestAnswerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(actId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(testAnswer);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<TestAnswer> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the test answers where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @return the matching test answers
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestAnswer> findByq(long questionId) throws SystemException {
		return findByq(questionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the test answers where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of test answers
	 * @param end the upper bound of the range of test answers (not inclusive)
	 * @return the range of matching test answers
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestAnswer> findByq(long questionId, int start, int end)
		throws SystemException {
		return findByq(questionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the test answers where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of test answers
	 * @param end the upper bound of the range of test answers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching test answers
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestAnswer> findByq(long questionId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_Q;
			finderArgs = new Object[] { questionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_Q;
			finderArgs = new Object[] { questionId, start, end, orderByComparator };
		}

		List<TestAnswer> list = (List<TestAnswer>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (TestAnswer testAnswer : list) {
				if ((questionId != testAnswer.getQuestionId())) {
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

			query.append(_SQL_SELECT_TESTANSWER_WHERE);

			query.append(_FINDER_COLUMN_Q_QUESTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(TestAnswerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				list = (List<TestAnswer>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first test answer in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching test answer
	 * @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer findByq_First(long questionId,
		OrderByComparator orderByComparator)
		throws NoSuchTestAnswerException, SystemException {
		TestAnswer testAnswer = fetchByq_First(questionId, orderByComparator);

		if (testAnswer != null) {
			return testAnswer;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("questionId=");
		msg.append(questionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTestAnswerException(msg.toString());
	}

	/**
	 * Returns the first test answer in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching test answer, or <code>null</code> if a matching test answer could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer fetchByq_First(long questionId,
		OrderByComparator orderByComparator) throws SystemException {
		List<TestAnswer> list = findByq(questionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last test answer in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching test answer
	 * @throws com.liferay.lms.NoSuchTestAnswerException if a matching test answer could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer findByq_Last(long questionId,
		OrderByComparator orderByComparator)
		throws NoSuchTestAnswerException, SystemException {
		TestAnswer testAnswer = fetchByq_Last(questionId, orderByComparator);

		if (testAnswer != null) {
			return testAnswer;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("questionId=");
		msg.append(questionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTestAnswerException(msg.toString());
	}

	/**
	 * Returns the last test answer in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching test answer, or <code>null</code> if a matching test answer could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer fetchByq_Last(long questionId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByq(questionId);

		List<TestAnswer> list = findByq(questionId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the test answers before and after the current test answer in the ordered set where questionId = &#63;.
	 *
	 * @param answerId the primary key of the current test answer
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next test answer
	 * @throws com.liferay.lms.NoSuchTestAnswerException if a test answer with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestAnswer[] findByq_PrevAndNext(long answerId, long questionId,
		OrderByComparator orderByComparator)
		throws NoSuchTestAnswerException, SystemException {
		TestAnswer testAnswer = findByPrimaryKey(answerId);

		Session session = null;

		try {
			session = openSession();

			TestAnswer[] array = new TestAnswerImpl[3];

			array[0] = getByq_PrevAndNext(session, testAnswer, questionId,
					orderByComparator, true);

			array[1] = testAnswer;

			array[2] = getByq_PrevAndNext(session, testAnswer, questionId,
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

	protected TestAnswer getByq_PrevAndNext(Session session,
		TestAnswer testAnswer, long questionId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TESTANSWER_WHERE);

		query.append(_FINDER_COLUMN_Q_QUESTIONID_2);

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
			query.append(TestAnswerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(questionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(testAnswer);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<TestAnswer> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the test answers.
	 *
	 * @return the test answers
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestAnswer> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the test answers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of test answers
	 * @param end the upper bound of the range of test answers (not inclusive)
	 * @return the range of test answers
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestAnswer> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the test answers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of test answers
	 * @param end the upper bound of the range of test answers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of test answers
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestAnswer> findAll(int start, int end,
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

		List<TestAnswer> list = (List<TestAnswer>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_TESTANSWER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_TESTANSWER.concat(TestAnswerModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<TestAnswer>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<TestAnswer>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the test answers where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (TestAnswer testAnswer : findByUuid(uuid)) {
			remove(testAnswer);
		}
	}

	/**
	 * Removes all the test answers where actId = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByac(long actId) throws SystemException {
		for (TestAnswer testAnswer : findByac(actId)) {
			remove(testAnswer);
		}
	}

	/**
	 * Removes all the test answers where questionId = &#63; from the database.
	 *
	 * @param questionId the question ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByq(long questionId) throws SystemException {
		for (TestAnswer testAnswer : findByq(questionId)) {
			remove(testAnswer);
		}
	}

	/**
	 * Removes all the test answers from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (TestAnswer testAnswer : findAll()) {
			remove(testAnswer);
		}
	}

	/**
	 * Returns the number of test answers where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching test answers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TESTANSWER_WHERE);

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
	 * Returns the number of test answers where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the number of matching test answers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByac(long actId) throws SystemException {
		Object[] finderArgs = new Object[] { actId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_AC,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TESTANSWER_WHERE);

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
	 * Returns the number of test answers where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @return the number of matching test answers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByq(long questionId) throws SystemException {
		Object[] finderArgs = new Object[] { questionId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_Q,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TESTANSWER_WHERE);

			query.append(_FINDER_COLUMN_Q_QUESTIONID_2);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_Q, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of test answers.
	 *
	 * @return the number of test answers
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_TESTANSWER);

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
	 * Initializes the test answer persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.TestAnswer")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<TestAnswer>> listenersList = new ArrayList<ModelListener<TestAnswer>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<TestAnswer>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(TestAnswerImpl.class.getName());
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
	private static final String _SQL_SELECT_TESTANSWER = "SELECT testAnswer FROM TestAnswer testAnswer";
	private static final String _SQL_SELECT_TESTANSWER_WHERE = "SELECT testAnswer FROM TestAnswer testAnswer WHERE ";
	private static final String _SQL_COUNT_TESTANSWER = "SELECT COUNT(testAnswer) FROM TestAnswer testAnswer";
	private static final String _SQL_COUNT_TESTANSWER_WHERE = "SELECT COUNT(testAnswer) FROM TestAnswer testAnswer WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "testAnswer.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "testAnswer.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(testAnswer.uuid IS NULL OR testAnswer.uuid = ?)";
	private static final String _FINDER_COLUMN_AC_ACTID_2 = "testAnswer.actId = ?";
	private static final String _FINDER_COLUMN_Q_QUESTIONID_2 = "testAnswer.questionId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "testAnswer.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No TestAnswer exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No TestAnswer exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(TestAnswerPersistenceImpl.class);
	private static TestAnswer _nullTestAnswer = new TestAnswerImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<TestAnswer> toCacheModel() {
				return _nullTestAnswerCacheModel;
			}
		};

	private static CacheModel<TestAnswer> _nullTestAnswerCacheModel = new CacheModel<TestAnswer>() {
			public TestAnswer toEntityModel() {
				return _nullTestAnswer;
			}
		};
}