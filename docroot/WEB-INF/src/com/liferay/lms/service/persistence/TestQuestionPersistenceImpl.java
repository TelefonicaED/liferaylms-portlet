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

import com.liferay.lms.NoSuchTestQuestionException;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.model.impl.TestQuestionImpl;
import com.liferay.lms.model.impl.TestQuestionModelImpl;

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
 * The persistence implementation for the test question service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see TestQuestionPersistence
 * @see TestQuestionUtil
 * @generated
 */
public class TestQuestionPersistenceImpl extends BasePersistenceImpl<TestQuestion>
	implements TestQuestionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link TestQuestionUtil} to access the test question persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = TestQuestionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
			TestQuestionModelImpl.FINDER_CACHE_ENABLED, TestQuestionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
			TestQuestionModelImpl.FINDER_CACHE_ENABLED, TestQuestionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			TestQuestionModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
			TestQuestionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_AC = new FinderPath(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
			TestQuestionModelImpl.FINDER_CACHE_ENABLED, TestQuestionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByac",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AC = new FinderPath(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
			TestQuestionModelImpl.FINDER_CACHE_ENABLED, TestQuestionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByac",
			new String[] { Long.class.getName() },
			TestQuestionModelImpl.ACTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_AC = new FinderPath(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
			TestQuestionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByac",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
			TestQuestionModelImpl.FINDER_CACHE_ENABLED, TestQuestionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
			TestQuestionModelImpl.FINDER_CACHE_ENABLED, TestQuestionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
			TestQuestionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the test question in the entity cache if it is enabled.
	 *
	 * @param testQuestion the test question
	 */
	public void cacheResult(TestQuestion testQuestion) {
		EntityCacheUtil.putResult(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
			TestQuestionImpl.class, testQuestion.getPrimaryKey(), testQuestion);

		testQuestion.resetOriginalValues();
	}

	/**
	 * Caches the test questions in the entity cache if it is enabled.
	 *
	 * @param testQuestions the test questions
	 */
	public void cacheResult(List<TestQuestion> testQuestions) {
		for (TestQuestion testQuestion : testQuestions) {
			if (EntityCacheUtil.getResult(
						TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
						TestQuestionImpl.class, testQuestion.getPrimaryKey()) == null) {
				cacheResult(testQuestion);
			}
			else {
				testQuestion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all test questions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(TestQuestionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(TestQuestionImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the test question.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TestQuestion testQuestion) {
		EntityCacheUtil.removeResult(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
			TestQuestionImpl.class, testQuestion.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<TestQuestion> testQuestions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TestQuestion testQuestion : testQuestions) {
			EntityCacheUtil.removeResult(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
				TestQuestionImpl.class, testQuestion.getPrimaryKey());
		}
	}

	/**
	 * Creates a new test question with the primary key. Does not add the test question to the database.
	 *
	 * @param questionId the primary key for the new test question
	 * @return the new test question
	 */
	public TestQuestion create(long questionId) {
		TestQuestion testQuestion = new TestQuestionImpl();

		testQuestion.setNew(true);
		testQuestion.setPrimaryKey(questionId);

		String uuid = PortalUUIDUtil.generate();

		testQuestion.setUuid(uuid);

		return testQuestion;
	}

	/**
	 * Removes the test question with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param questionId the primary key of the test question
	 * @return the test question that was removed
	 * @throws com.liferay.lms.NoSuchTestQuestionException if a test question with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestQuestion remove(long questionId)
		throws NoSuchTestQuestionException, SystemException {
		return remove(Long.valueOf(questionId));
	}

	/**
	 * Removes the test question with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the test question
	 * @return the test question that was removed
	 * @throws com.liferay.lms.NoSuchTestQuestionException if a test question with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TestQuestion remove(Serializable primaryKey)
		throws NoSuchTestQuestionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TestQuestion testQuestion = (TestQuestion)session.get(TestQuestionImpl.class,
					primaryKey);

			if (testQuestion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTestQuestionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(testQuestion);
		}
		catch (NoSuchTestQuestionException nsee) {
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
	protected TestQuestion removeImpl(TestQuestion testQuestion)
		throws SystemException {
		testQuestion = toUnwrappedModel(testQuestion);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, testQuestion);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(testQuestion);

		return testQuestion;
	}

	@Override
	public TestQuestion updateImpl(
		com.liferay.lms.model.TestQuestion testQuestion, boolean merge)
		throws SystemException {
		testQuestion = toUnwrappedModel(testQuestion);

		boolean isNew = testQuestion.isNew();

		TestQuestionModelImpl testQuestionModelImpl = (TestQuestionModelImpl)testQuestion;

		if (Validator.isNull(testQuestion.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			testQuestion.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, testQuestion, merge);

			testQuestion.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !TestQuestionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((testQuestionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						testQuestionModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { testQuestionModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((testQuestionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AC.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(testQuestionModelImpl.getOriginalActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AC, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AC,
					args);

				args = new Object[] {
						Long.valueOf(testQuestionModelImpl.getActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AC, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AC,
					args);
			}
		}

		EntityCacheUtil.putResult(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
			TestQuestionImpl.class, testQuestion.getPrimaryKey(), testQuestion);

		return testQuestion;
	}

	protected TestQuestion toUnwrappedModel(TestQuestion testQuestion) {
		if (testQuestion instanceof TestQuestionImpl) {
			return testQuestion;
		}

		TestQuestionImpl testQuestionImpl = new TestQuestionImpl();

		testQuestionImpl.setNew(testQuestion.isNew());
		testQuestionImpl.setPrimaryKey(testQuestion.getPrimaryKey());

		testQuestionImpl.setUuid(testQuestion.getUuid());
		testQuestionImpl.setQuestionId(testQuestion.getQuestionId());
		testQuestionImpl.setActId(testQuestion.getActId());
		testQuestionImpl.setText(testQuestion.getText());
		testQuestionImpl.setQuestionType(testQuestion.getQuestionType());
		testQuestionImpl.setWeight(testQuestion.getWeight());

		return testQuestionImpl;
	}

	/**
	 * Returns the test question with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the test question
	 * @return the test question
	 * @throws com.liferay.portal.NoSuchModelException if a test question with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TestQuestion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the test question with the primary key or throws a {@link com.liferay.lms.NoSuchTestQuestionException} if it could not be found.
	 *
	 * @param questionId the primary key of the test question
	 * @return the test question
	 * @throws com.liferay.lms.NoSuchTestQuestionException if a test question with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestQuestion findByPrimaryKey(long questionId)
		throws NoSuchTestQuestionException, SystemException {
		TestQuestion testQuestion = fetchByPrimaryKey(questionId);

		if (testQuestion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + questionId);
			}

			throw new NoSuchTestQuestionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				questionId);
		}

		return testQuestion;
	}

	/**
	 * Returns the test question with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the test question
	 * @return the test question, or <code>null</code> if a test question with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TestQuestion fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the test question with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param questionId the primary key of the test question
	 * @return the test question, or <code>null</code> if a test question with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestQuestion fetchByPrimaryKey(long questionId)
		throws SystemException {
		TestQuestion testQuestion = (TestQuestion)EntityCacheUtil.getResult(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
				TestQuestionImpl.class, questionId);

		if (testQuestion == _nullTestQuestion) {
			return null;
		}

		if (testQuestion == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				testQuestion = (TestQuestion)session.get(TestQuestionImpl.class,
						Long.valueOf(questionId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (testQuestion != null) {
					cacheResult(testQuestion);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(TestQuestionModelImpl.ENTITY_CACHE_ENABLED,
						TestQuestionImpl.class, questionId, _nullTestQuestion);
				}

				closeSession(session);
			}
		}

		return testQuestion;
	}

	/**
	 * Returns all the test questions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching test questions
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestQuestion> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the test questions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of test questions
	 * @param end the upper bound of the range of test questions (not inclusive)
	 * @return the range of matching test questions
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestQuestion> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the test questions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of test questions
	 * @param end the upper bound of the range of test questions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching test questions
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestQuestion> findByUuid(String uuid, int start, int end,
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

		List<TestQuestion> list = (List<TestQuestion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (TestQuestion testQuestion : list) {
				if (!Validator.equals(uuid, testQuestion.getUuid())) {
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

			query.append(_SQL_SELECT_TESTQUESTION_WHERE);

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
				query.append(TestQuestionModelImpl.ORDER_BY_JPQL);
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

				list = (List<TestQuestion>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first test question in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching test question
	 * @throws com.liferay.lms.NoSuchTestQuestionException if a matching test question could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestQuestion findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTestQuestionException, SystemException {
		TestQuestion testQuestion = fetchByUuid_First(uuid, orderByComparator);

		if (testQuestion != null) {
			return testQuestion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTestQuestionException(msg.toString());
	}

	/**
	 * Returns the first test question in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching test question, or <code>null</code> if a matching test question could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestQuestion fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<TestQuestion> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last test question in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching test question
	 * @throws com.liferay.lms.NoSuchTestQuestionException if a matching test question could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestQuestion findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTestQuestionException, SystemException {
		TestQuestion testQuestion = fetchByUuid_Last(uuid, orderByComparator);

		if (testQuestion != null) {
			return testQuestion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTestQuestionException(msg.toString());
	}

	/**
	 * Returns the last test question in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching test question, or <code>null</code> if a matching test question could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestQuestion fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<TestQuestion> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the test questions before and after the current test question in the ordered set where uuid = &#63;.
	 *
	 * @param questionId the primary key of the current test question
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next test question
	 * @throws com.liferay.lms.NoSuchTestQuestionException if a test question with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestQuestion[] findByUuid_PrevAndNext(long questionId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchTestQuestionException, SystemException {
		TestQuestion testQuestion = findByPrimaryKey(questionId);

		Session session = null;

		try {
			session = openSession();

			TestQuestion[] array = new TestQuestionImpl[3];

			array[0] = getByUuid_PrevAndNext(session, testQuestion, uuid,
					orderByComparator, true);

			array[1] = testQuestion;

			array[2] = getByUuid_PrevAndNext(session, testQuestion, uuid,
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

	protected TestQuestion getByUuid_PrevAndNext(Session session,
		TestQuestion testQuestion, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TESTQUESTION_WHERE);

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
			query.append(TestQuestionModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(testQuestion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<TestQuestion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the test questions where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the matching test questions
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestQuestion> findByac(long actId) throws SystemException {
		return findByac(actId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the test questions where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of test questions
	 * @param end the upper bound of the range of test questions (not inclusive)
	 * @return the range of matching test questions
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestQuestion> findByac(long actId, int start, int end)
		throws SystemException {
		return findByac(actId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the test questions where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of test questions
	 * @param end the upper bound of the range of test questions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching test questions
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestQuestion> findByac(long actId, int start, int end,
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

		List<TestQuestion> list = (List<TestQuestion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (TestQuestion testQuestion : list) {
				if ((actId != testQuestion.getActId())) {
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

			query.append(_SQL_SELECT_TESTQUESTION_WHERE);

			query.append(_FINDER_COLUMN_AC_ACTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(TestQuestionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				list = (List<TestQuestion>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first test question in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching test question
	 * @throws com.liferay.lms.NoSuchTestQuestionException if a matching test question could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestQuestion findByac_First(long actId,
		OrderByComparator orderByComparator)
		throws NoSuchTestQuestionException, SystemException {
		TestQuestion testQuestion = fetchByac_First(actId, orderByComparator);

		if (testQuestion != null) {
			return testQuestion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTestQuestionException(msg.toString());
	}

	/**
	 * Returns the first test question in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching test question, or <code>null</code> if a matching test question could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestQuestion fetchByac_First(long actId,
		OrderByComparator orderByComparator) throws SystemException {
		List<TestQuestion> list = findByac(actId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last test question in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching test question
	 * @throws com.liferay.lms.NoSuchTestQuestionException if a matching test question could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestQuestion findByac_Last(long actId,
		OrderByComparator orderByComparator)
		throws NoSuchTestQuestionException, SystemException {
		TestQuestion testQuestion = fetchByac_Last(actId, orderByComparator);

		if (testQuestion != null) {
			return testQuestion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actId=");
		msg.append(actId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTestQuestionException(msg.toString());
	}

	/**
	 * Returns the last test question in the ordered set where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching test question, or <code>null</code> if a matching test question could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestQuestion fetchByac_Last(long actId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByac(actId);

		List<TestQuestion> list = findByac(actId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the test questions before and after the current test question in the ordered set where actId = &#63;.
	 *
	 * @param questionId the primary key of the current test question
	 * @param actId the act ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next test question
	 * @throws com.liferay.lms.NoSuchTestQuestionException if a test question with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public TestQuestion[] findByac_PrevAndNext(long questionId, long actId,
		OrderByComparator orderByComparator)
		throws NoSuchTestQuestionException, SystemException {
		TestQuestion testQuestion = findByPrimaryKey(questionId);

		Session session = null;

		try {
			session = openSession();

			TestQuestion[] array = new TestQuestionImpl[3];

			array[0] = getByac_PrevAndNext(session, testQuestion, actId,
					orderByComparator, true);

			array[1] = testQuestion;

			array[2] = getByac_PrevAndNext(session, testQuestion, actId,
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

	protected TestQuestion getByac_PrevAndNext(Session session,
		TestQuestion testQuestion, long actId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TESTQUESTION_WHERE);

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
			query.append(TestQuestionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(actId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(testQuestion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<TestQuestion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the test questions.
	 *
	 * @return the test questions
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestQuestion> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the test questions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of test questions
	 * @param end the upper bound of the range of test questions (not inclusive)
	 * @return the range of test questions
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestQuestion> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the test questions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of test questions
	 * @param end the upper bound of the range of test questions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of test questions
	 * @throws SystemException if a system exception occurred
	 */
	public List<TestQuestion> findAll(int start, int end,
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

		List<TestQuestion> list = (List<TestQuestion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_TESTQUESTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_TESTQUESTION.concat(TestQuestionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<TestQuestion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<TestQuestion>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the test questions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (TestQuestion testQuestion : findByUuid(uuid)) {
			remove(testQuestion);
		}
	}

	/**
	 * Removes all the test questions where actId = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByac(long actId) throws SystemException {
		for (TestQuestion testQuestion : findByac(actId)) {
			remove(testQuestion);
		}
	}

	/**
	 * Removes all the test questions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (TestQuestion testQuestion : findAll()) {
			remove(testQuestion);
		}
	}

	/**
	 * Returns the number of test questions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching test questions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TESTQUESTION_WHERE);

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
	 * Returns the number of test questions where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the number of matching test questions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByac(long actId) throws SystemException {
		Object[] finderArgs = new Object[] { actId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_AC,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TESTQUESTION_WHERE);

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
	 * Returns the number of test questions.
	 *
	 * @return the number of test questions
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_TESTQUESTION);

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
	 * Initializes the test question persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.model.TestQuestion")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<TestQuestion>> listenersList = new ArrayList<ModelListener<TestQuestion>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<TestQuestion>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(TestQuestionImpl.class.getName());
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
	private static final String _SQL_SELECT_TESTQUESTION = "SELECT testQuestion FROM TestQuestion testQuestion";
	private static final String _SQL_SELECT_TESTQUESTION_WHERE = "SELECT testQuestion FROM TestQuestion testQuestion WHERE ";
	private static final String _SQL_COUNT_TESTQUESTION = "SELECT COUNT(testQuestion) FROM TestQuestion testQuestion";
	private static final String _SQL_COUNT_TESTQUESTION_WHERE = "SELECT COUNT(testQuestion) FROM TestQuestion testQuestion WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "testQuestion.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "testQuestion.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(testQuestion.uuid IS NULL OR testQuestion.uuid = ?)";
	private static final String _FINDER_COLUMN_AC_ACTID_2 = "testQuestion.actId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "testQuestion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No TestQuestion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No TestQuestion exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(TestQuestionPersistenceImpl.class);
	private static TestQuestion _nullTestQuestion = new TestQuestionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<TestQuestion> toCacheModel() {
				return _nullTestQuestionCacheModel;
			}
		};

	private static CacheModel<TestQuestion> _nullTestQuestionCacheModel = new CacheModel<TestQuestion>() {
			public TestQuestion toEntityModel() {
				return _nullTestQuestion;
			}
		};
}