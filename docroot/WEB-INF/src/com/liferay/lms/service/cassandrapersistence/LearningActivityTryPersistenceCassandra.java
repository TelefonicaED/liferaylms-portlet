package com.liferay.lms.service.cassandrapersistence;


/*
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
*/

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.NoSuchLearningActivityTryException;
import com.liferay.lms.model.AuditEntry;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.impl.LearningActivityTryImpl;
import com.liferay.lms.model.impl.LearningActivityTryModelImpl;
import com.liferay.lms.service.AuditEntryLocalServiceUtil;
import com.liferay.lms.service.persistence.LearningActivityTryPersistenceImpl;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
/*import com.liferay.portal.kernel.dao.orm.Session; */
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.lms.cassandra.ExtConexionCassandra;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class LearningActivityTryPersistenceCassandra extends LearningActivityTryPersistenceImpl {
	
	
	Session  session =  ExtConexionCassandra.session;
    // INSERT  
	  PreparedStatement insertStatement = session.prepare(
		      "INSERT INTO liferay.auditentry " +
		      "(auditid, auditdate, companyid, groupid, classname, userid, action, extradata, classpk, association) " +
		      "VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?);");			
	
	

	@Override
	public LearningActivityTry create(long latId) {
		// TODO Auto-generated method stub
		return super.create(latId);
	}


	/**
	 * Returns the learning activity try with the primary key or throws a {@link com.liferay.lms.NoSuchLearningActivityTryException} if it could not be found.
	 *
	 * @param latId the primary key of the learning activity try
	 * @return the learning activity try
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry findByPrimaryKey(long latId)
		throws NoSuchLearningActivityTryException, SystemException {

		
		//Search LearningActivityTry
		LearningActivityTry learningActivityTry = null;
		BoundStatement boundStatement = new BoundStatement( ExtConexionCassandra.searchBylat_Statement);
		ResultSet results=session.execute(boundStatement.bind(latId));
		if(results!=null )		
		{
			List<Row> rowlist=results.all();
			System.out.println(rowlist.size());
			if(rowlist.size()>0){
				Row row=rowlist.get(0);
				learningActivityTry =getLearningActivityTryFromRow(row);

			}else{
				if (learningActivityTry == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + latId);
					}

					throw new NoSuchLearningActivityTryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						latId);
				}
			}
				
			}
		return learningActivityTry;
	}
	
	/**
	 * Removes the learning activity try with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param latId the primary key of the learning activity try
	 * @return the learning activity try that was removed
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTry remove(long latId)
		throws NoSuchLearningActivityTryException, SystemException {
		return remove(Long.valueOf(latId));
	}	
	
	/**
	 * Removes the learning activity try with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the learning activity try
	 * @return the learning activity try that was removed
	 * @throws com.liferay.lms.NoSuchLearningActivityTryException if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivityTry remove(Serializable primaryKey)
		throws NoSuchLearningActivityTryException, SystemException {
		

		
		//Search LearningActivityTry
		LearningActivityTry learningActivityTry = null;
		BoundStatement boundStatement = new BoundStatement( ExtConexionCassandra.searchBylat_Statement);
		ResultSet results=session.execute(boundStatement.bind(primaryKey));
		if(results!=null )		
		{
			List<Row> rowlist=results.all();
			System.out.println(rowlist.size());
			if(rowlist.size()>0){
				Row row=rowlist.get(0);
				learningActivityTry =getLearningActivityTryFromRow(row);

			}
		}	
		
		
		boundStatement = new BoundStatement(ExtConexionCassandra.remove_uStatement);
		session.execute(boundStatement.bind(primaryKey));
		
		return learningActivityTry;
		
		
		
/*		
		Session session = null;

		try {
			session = openSession();

			LearningActivityTry learningActivityTry = (LearningActivityTry)session.get(LearningActivityTryImpl.class,
					primaryKey);

			if (learningActivityTry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLearningActivityTryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(learningActivityTry);
		}
		catch (NoSuchLearningActivityTryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
		
*/		
	}
	
	
	
	
	
	@Override
	public LearningActivityTry updateImpl(
		com.liferay.lms.model.LearningActivityTry learningActivityTry,
		boolean merge) throws SystemException {
		
		

		
	    BoundStatement boundStatement = new BoundStatement(ExtConexionCassandra.updateLearningActivityTry_Statement);
			ResultSet results=session.execute(boundStatement.bind(
					learningActivityTry.getLatId(),
					learningActivityTry.getActId(),
					learningActivityTry.getComments(),
					learningActivityTry.getEndDate(),
					learningActivityTry.getResult(),     
					learningActivityTry.getStartDate(),
					learningActivityTry.getTryData(),
					learningActivityTry.getTryResultData(),
					learningActivityTry.getUserId(),
					learningActivityTry.getUuid())
					);
			

			return learningActivityTry; 		
		
/*		
		learningActivityTry = toUnwrappedModel(learningActivityTry);

		boolean isNew = learningActivityTry.isNew();

		LearningActivityTryModelImpl learningActivityTryModelImpl = (LearningActivityTryModelImpl)learningActivityTry;

		if (Validator.isNull(learningActivityTry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			learningActivityTry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, learningActivityTry, merge);

			learningActivityTry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !LearningActivityTryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((learningActivityTryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						learningActivityTryModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { learningActivityTryModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((learningActivityTryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityTryModelImpl.getOriginalActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACT, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT,
					args);

				args = new Object[] {
						Long.valueOf(learningActivityTryModelImpl.getActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACT, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT,
					args);
			}

			if ((learningActivityTryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityTryModelImpl.getOriginalActId()),
						Long.valueOf(learningActivityTryModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACT_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT_U,
					args);

				args = new Object[] {
						Long.valueOf(learningActivityTryModelImpl.getActId()),
						Long.valueOf(learningActivityTryModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACT_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT_U,
					args);
			}
		}

		EntityCacheUtil.putResult(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryImpl.class, learningActivityTry.getPrimaryKey(),
			learningActivityTry);

		return learningActivityTry;
*/		
	}
	
	
	public LearningActivityTry update(LearningActivityTry learningActivityTry, Boolean merge) throws SystemException{
		
		
	    BoundStatement boundStatement = new BoundStatement(ExtConexionCassandra.updateLearningActivityTry_Statement);
			ResultSet results=session.execute(boundStatement.bind(
					learningActivityTry.getLatId(),
					learningActivityTry.getUuid(),
					learningActivityTry.getLatId(),
					learningActivityTry.getComments(),
					learningActivityTry.getEndDate(),
					learningActivityTry.getResult(),     
					learningActivityTry.getStartDate(),
					learningActivityTry. getTryData(),
					learningActivityTry. getTryData())
					);
			

			return learningActivityTry; 		
		
	}
	
	/**
	 * Returns the number of learning activity tries where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the number of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByact(long actId) throws SystemException {
	//	Object[] finderArgs = new Object[] { actId };

		
	    BoundStatement boundStatement = new BoundStatement(ExtConexionCassandra.countByact_Statement);
		ResultSet results=session.execute(boundStatement.bind(actId));
		List<Row> rows=results.all();
		int cont =0;
		cont = (int) rows.get(0).getLong(0);
		return cont;   

		
	}
	
	
	/**
	 * Returns the learning activity try with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the learning activity try
	 * @return the learning activity try, or <code>null</code> if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivityTry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the learning activity try with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param latId the primary key of the learning activity try
	 * @return the learning activity try, or <code>null</code> if a learning activity try with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivityTry fetchByPrimaryKey(long latId)
		throws SystemException {
		
		
/*		
		LearningActivityTry learningActivityTry = (LearningActivityTry)EntityCacheUtil.getResult(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
				LearningActivityTryImpl.class, latId);

		if (learningActivityTry == _nullLearningActivityTry) {
			return null;
		}

		if (learningActivityTry == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				learningActivityTry = (LearningActivityTry)session.get(LearningActivityTryImpl.class,
						Long.valueOf(latId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (learningActivityTry != null) {
					cacheResult(learningActivityTry);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(LearningActivityTryModelImpl.ENTITY_CACHE_ENABLED,
						LearningActivityTryImpl.class, latId,
						_nullLearningActivityTry);
				}

				closeSession(session);
			}
		}

		return learningActivityTry;
*/
		
		
		//Search LearningActivityTry
		LearningActivityTry learningActivityTry = null;
		BoundStatement boundStatement = new BoundStatement( ExtConexionCassandra.searchBylat_Statement);
		ResultSet results=session.execute(boundStatement.bind(latId));
		if(results!=null )		
		{
			List<Row> rowlist=results.all();
			System.out.println(rowlist.size());
			if(rowlist.size()>0){
				Row row=rowlist.get(0);
				learningActivityTry =getLearningActivityTryFromRow(row);

			}
		}
		
		if (learningActivityTry == null) {
			return null;
		}else{
			return learningActivityTry;
		}
		
		
		
	}

	/**
	 * Returns a range of all the learning activity tries where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of learning activity tries
	 * @param end the upper bound of the range of learning activity tries (not inclusive)
	 * @return the range of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LearningActivityTry> findByact(long actId, int start, int end)
		throws SystemException {
		return findByact(actId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity tries where actId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param start the lower bound of the range of learning activity tries
	 * @param end the upper bound of the range of learning activity tries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LearningActivityTry> findByact(long actId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;
		
		
   		List<LearningActivityTry> groupActivityTries=new java.util.ArrayList<LearningActivityTry>();
   		BoundStatement boundStatement = new BoundStatement(ExtConexionCassandra.searchByact_Statement);
		boundStatement.setFetchSize(end);		
		ResultSet results = null;
		results = session.execute(boundStatement.bind(actId));
   				List<Row> rows=results.all();
   				if(rows.size()>0&&rows.size()>=start){
   					for(int j=start;j<rows.size();j++){
   						Row row=rows.get(j);
   						LearningActivityTry learningActivityTry = getLearningActivityTryFromRow(row);
   						groupActivityTries.add(learningActivityTry);
   					}
   		}			
   		
   		return groupActivityTries;	
/*		
		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACT;
			finderArgs = new Object[] { actId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ACT;
			finderArgs = new Object[] { actId, start, end, orderByComparator };
		}

		List<LearningActivityTry> list = (List<LearningActivityTry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivityTry learningActivityTry : list) {
				if ((actId != learningActivityTry.getActId())) {
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

			query.append(_SQL_SELECT_LEARNINGACTIVITYTRY_WHERE);

			query.append(_FINDER_COLUMN_ACT_ACTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(LearningActivityTryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				list = (List<LearningActivityTry>)QueryUtil.list(q,
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
*/		
	}
	
	
	
	/**
	 * Returns all the learning activity tries where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @return the matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LearningActivityTry> findByact_u(long actId, long userId)
		throws SystemException {
		return findByact_u(actId, userId, 0, QueryUtil.ALL_POS,
			null);
	}
	
	
	
	/**
	 * Returns an ordered range of all the learning activity tries where actId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of learning activity tries
	 * @param end the upper bound of the range of learning activity tries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LearningActivityTry> findByact_u(long actId, long userId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {

		

   		List<LearningActivityTry> groupActivityTries=new java.util.ArrayList<LearningActivityTry>();
   		BoundStatement boundStatement = new BoundStatement(ExtConexionCassandra.findByact_uStatement);
   		if (!(end==-1)){
   			boundStatement.setFetchSize(end);
   		}
		ResultSet results = null;
		results = session.execute(boundStatement.bind(actId,userId));
   				List<Row> rows=results.all();
   				if(rows.size()>0&&rows.size()>=start){
   					for(int j=start;j<rows.size();j++){
   						Row row=rows.get(j);
   						LearningActivityTry learningActivityTry = getLearningActivityTryFromRow(row);
   						groupActivityTries.add(learningActivityTry);
   					}
   		}			
   		
   		return groupActivityTries;		
	}

	/*
	public List<LearningActivityTry> getLearningActivityTryNotFinishedByActUser(long actId, long userId)
			throws SystemException {
			FinderPath finderPath = null;
			Object[] finderArgs = null;

	   		List<LearningActivityTry> groupActivityTries=new java.util.ArrayList<LearningActivityTry>();
	   		BoundStatement boundStatement = new BoundStatement(ExtConexionCassandra.findByactByUserByEnDate_uStatement);

			ResultSet results = null;
			results = session.execute(boundStatement.bind(actId,userId));
	   				List<Row> rows=results.all();
	   				if(rows.size()>0){
	   					for(int j=0;j<rows.size();j++){
	   						Row row=rows.get(j);
	   						LearningActivityTry learningActivityTry = getLearningActivityTryFromRow(row);
	   						groupActivityTries.add(learningActivityTry);
	   					}
	   		}			
	   		
	   		return groupActivityTries;		
		}
	*/
	
	

	/**
	 * Returns the number of learning activity tries where actId = &#63; and userId = &#63;.
	 *
	 * @param actId the act ID
	 * @param userId the user ID
	 * @return the number of matching learning activity tries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByact_u(long actId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { actId, userId };

/*		
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACT_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LEARNINGACTIVITYTRY_WHERE);

			query.append(_FINDER_COLUMN_ACT_U_ACTID_2);

			query.append(_FINDER_COLUMN_ACT_U_USERID_2);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACT_U,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
*/
		
		BoundStatement boundStatement = new BoundStatement(ExtConexionCassandra.countByact_uStatement);
		ResultSet results=session.execute(boundStatement.bind(actId,userId));
		List<Row> rows=results.all();
		int cont =0;
		cont = (int) rows.get(0).getLong(0);
		return cont;         
  

	}

	

	@Override
	public LearningActivityTry fetchByUuid_First(String uuid,
			OrderByComparator orderByComparator) throws SystemException {
		// TODO Auto-generated method stub
		return super.fetchByUuid_First(uuid, orderByComparator);
	}

	@Override
	public LearningActivityTry fetchByUuid_Last(String uuid,
			OrderByComparator orderByComparator) throws SystemException {
		// TODO Auto-generated method stub
		return super.fetchByUuid_Last(uuid, orderByComparator);
	}

	@Override
	public LearningActivityTry fetchByact_First(long actId,
			OrderByComparator orderByComparator) throws SystemException {
		// TODO Auto-generated method stub
		return super.fetchByact_First(actId, orderByComparator);
	}

	@Override
	public LearningActivityTry fetchByact_Last(long actId,
			OrderByComparator orderByComparator) throws SystemException {
		// TODO Auto-generated method stub
		return super.fetchByact_Last(actId, orderByComparator);
	}

	@Override
	public LearningActivityTry fetchByact_u_First(long actId, long userId,
			OrderByComparator orderByComparator) throws SystemException {
		// TODO Auto-generated method stub
		return super.fetchByact_u_First(actId, userId, orderByComparator);
	}

	@Override
	public LearningActivityTry fetchByact_u_Last(long actId, long userId,
			OrderByComparator orderByComparator) throws SystemException {
		// TODO Auto-generated method stub
		return super.fetchByact_u_Last(actId, userId, orderByComparator);
	}

	@Override
	public List<LearningActivityTry> findAll() throws SystemException {
		// TODO Auto-generated method stub
		return super.findAll();
	}
	
	private LearningActivityTry getLearningActivityTryFromRow(Row row)
			throws SystemException {
		LearningActivityTry learningActivityTry= this.create(row.getLong("latId"));
		learningActivityTry.setUuid(row.getString("uuid_"));
		learningActivityTry.setLatId(row.getLong("latid"));
		learningActivityTry.setComments(row.getString("comments"));
	    learningActivityTry.setEndDate( row.getTimestamp("endDate"));
		learningActivityTry.setResult(row.getLong("Result"));     
		learningActivityTry.setStartDate(row.getTimestamp("startDate"));
		learningActivityTry. setTryData(row.getString("trydata"));
		learningActivityTry. setTryData(row.getString("tryresultdata"));
		return learningActivityTry;
	}	
	
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LearningActivityTry exists with the primary key ";
	private static Log _log = LogFactoryUtil.getLog(LearningActivityTryPersistenceCassandra.class);

}
