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

package com.liferay.lms.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.service.AuditEntryLocalService;
import com.liferay.lms.service.CheckP2pMailingLocalService;
import com.liferay.lms.service.CompetenceLocalService;
import com.liferay.lms.service.CompetenceService;
import com.liferay.lms.service.CourseCompetenceLocalService;
import com.liferay.lms.service.CourseCompetenceService;
import com.liferay.lms.service.CourseLocalService;
import com.liferay.lms.service.CourseResultLocalService;
import com.liferay.lms.service.CourseResultService;
import com.liferay.lms.service.CourseService;
import com.liferay.lms.service.LearningActivityLocalService;
import com.liferay.lms.service.LearningActivityResultLocalService;
import com.liferay.lms.service.LearningActivityResultService;
import com.liferay.lms.service.LearningActivityService;
import com.liferay.lms.service.LearningActivityTryLocalService;
import com.liferay.lms.service.LearningActivityTryService;
import com.liferay.lms.service.LmsPrefsLocalService;
import com.liferay.lms.service.ModuleLocalService;
import com.liferay.lms.service.ModuleResultLocalService;
import com.liferay.lms.service.ModuleResultService;
import com.liferay.lms.service.ModuleService;
import com.liferay.lms.service.P2pActivityCorrectionsLocalService;
import com.liferay.lms.service.P2pActivityLocalService;
import com.liferay.lms.service.SCORMContentLocalService;
import com.liferay.lms.service.SCORMContentService;
import com.liferay.lms.service.SurveyResultLocalService;
import com.liferay.lms.service.TestAnswerLocalService;
import com.liferay.lms.service.TestAnswerService;
import com.liferay.lms.service.TestQuestionLocalService;
import com.liferay.lms.service.TestQuestionService;
import com.liferay.lms.service.UserCompetenceLocalService;
import com.liferay.lms.service.UserCompetenceService;
import com.liferay.lms.service.persistence.AuditEntryPersistence;
import com.liferay.lms.service.persistence.CheckP2pMailingPersistence;
import com.liferay.lms.service.persistence.CompetencePersistence;
import com.liferay.lms.service.persistence.CourseCompetencePersistence;
import com.liferay.lms.service.persistence.CoursePersistence;
import com.liferay.lms.service.persistence.CourseResultPersistence;
import com.liferay.lms.service.persistence.LearningActivityPersistence;
import com.liferay.lms.service.persistence.LearningActivityResultPersistence;
import com.liferay.lms.service.persistence.LearningActivityTryPersistence;
import com.liferay.lms.service.persistence.LmsPrefsPersistence;
import com.liferay.lms.service.persistence.ModulePersistence;
import com.liferay.lms.service.persistence.ModuleResultPersistence;
import com.liferay.lms.service.persistence.P2pActivityCorrectionsPersistence;
import com.liferay.lms.service.persistence.P2pActivityPersistence;
import com.liferay.lms.service.persistence.SCORMContentPersistence;
import com.liferay.lms.service.persistence.SurveyResultPersistence;
import com.liferay.lms.service.persistence.TestAnswerPersistence;
import com.liferay.lms.service.persistence.TestQuestionPersistence;
import com.liferay.lms.service.persistence.UserCompetencePersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * The base implementation of the learning activity result local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl}.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl
 * @see com.liferay.lms.service.LearningActivityResultLocalServiceUtil
 * @generated
 */
public abstract class LearningActivityResultLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements LearningActivityResultLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.lms.service.LearningActivityResultLocalServiceUtil} to access the learning activity result local service.
	 */

	/**
	 * Adds the learning activity result to the database. Also notifies the appropriate model listeners.
	 *
	 * @param learningActivityResult the learning activity result
	 * @return the learning activity result that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public LearningActivityResult addLearningActivityResult(
		LearningActivityResult learningActivityResult)
		throws SystemException {
		learningActivityResult.setNew(true);

		return learningActivityResultPersistence.update(learningActivityResult,
			false);
	}

	/**
	 * Creates a new learning activity result with the primary key. Does not add the learning activity result to the database.
	 *
	 * @param larId the primary key for the new learning activity result
	 * @return the new learning activity result
	 */
	public LearningActivityResult createLearningActivityResult(long larId) {
		return learningActivityResultPersistence.create(larId);
	}

	/**
	 * Deletes the learning activity result with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param larId the primary key of the learning activity result
	 * @return the learning activity result that was removed
	 * @throws PortalException if a learning activity result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	public LearningActivityResult deleteLearningActivityResult(long larId)
		throws PortalException, SystemException {
		return learningActivityResultPersistence.remove(larId);
	}

	/**
	 * Deletes the learning activity result from the database. Also notifies the appropriate model listeners.
	 *
	 * @param learningActivityResult the learning activity result
	 * @return the learning activity result that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	public LearningActivityResult deleteLearningActivityResult(
		LearningActivityResult learningActivityResult)
		throws SystemException {
		return learningActivityResultPersistence.remove(learningActivityResult);
	}

	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(LearningActivityResult.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return learningActivityResultPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return learningActivityResultPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return learningActivityResultPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return learningActivityResultPersistence.countWithDynamicQuery(dynamicQuery);
	}

	public LearningActivityResult fetchLearningActivityResult(long larId)
		throws SystemException {
		return learningActivityResultPersistence.fetchByPrimaryKey(larId);
	}

	/**
	 * Returns the learning activity result with the primary key.
	 *
	 * @param larId the primary key of the learning activity result
	 * @return the learning activity result
	 * @throws PortalException if a learning activity result with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityResult getLearningActivityResult(long larId)
		throws PortalException, SystemException {
		return learningActivityResultPersistence.findByPrimaryKey(larId);
	}

	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return learningActivityResultPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the learning activity results.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of learning activity results
	 * @param end the upper bound of the range of learning activity results (not inclusive)
	 * @return the range of learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityResult> getLearningActivityResults(int start,
		int end) throws SystemException {
		return learningActivityResultPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of learning activity results.
	 *
	 * @return the number of learning activity results
	 * @throws SystemException if a system exception occurred
	 */
	public int getLearningActivityResultsCount() throws SystemException {
		return learningActivityResultPersistence.countAll();
	}

	/**
	 * Updates the learning activity result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param learningActivityResult the learning activity result
	 * @return the learning activity result that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public LearningActivityResult updateLearningActivityResult(
		LearningActivityResult learningActivityResult)
		throws SystemException {
		return updateLearningActivityResult(learningActivityResult, true);
	}

	/**
	 * Updates the learning activity result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param learningActivityResult the learning activity result
	 * @param merge whether to merge the learning activity result with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	 * @return the learning activity result that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public LearningActivityResult updateLearningActivityResult(
		LearningActivityResult learningActivityResult, boolean merge)
		throws SystemException {
		learningActivityResult.setNew(false);

		return learningActivityResultPersistence.update(learningActivityResult,
			merge);
	}

	/**
	 * Returns the audit entry local service.
	 *
	 * @return the audit entry local service
	 */
	public AuditEntryLocalService getAuditEntryLocalService() {
		return auditEntryLocalService;
	}

	/**
	 * Sets the audit entry local service.
	 *
	 * @param auditEntryLocalService the audit entry local service
	 */
	public void setAuditEntryLocalService(
		AuditEntryLocalService auditEntryLocalService) {
		this.auditEntryLocalService = auditEntryLocalService;
	}

	/**
	 * Returns the audit entry persistence.
	 *
	 * @return the audit entry persistence
	 */
	public AuditEntryPersistence getAuditEntryPersistence() {
		return auditEntryPersistence;
	}

	/**
	 * Sets the audit entry persistence.
	 *
	 * @param auditEntryPersistence the audit entry persistence
	 */
	public void setAuditEntryPersistence(
		AuditEntryPersistence auditEntryPersistence) {
		this.auditEntryPersistence = auditEntryPersistence;
	}

	/**
	 * Returns the check p2p mailing local service.
	 *
	 * @return the check p2p mailing local service
	 */
	public CheckP2pMailingLocalService getCheckP2pMailingLocalService() {
		return checkP2pMailingLocalService;
	}

	/**
	 * Sets the check p2p mailing local service.
	 *
	 * @param checkP2pMailingLocalService the check p2p mailing local service
	 */
	public void setCheckP2pMailingLocalService(
		CheckP2pMailingLocalService checkP2pMailingLocalService) {
		this.checkP2pMailingLocalService = checkP2pMailingLocalService;
	}

	/**
	 * Returns the check p2p mailing persistence.
	 *
	 * @return the check p2p mailing persistence
	 */
	public CheckP2pMailingPersistence getCheckP2pMailingPersistence() {
		return checkP2pMailingPersistence;
	}

	/**
	 * Sets the check p2p mailing persistence.
	 *
	 * @param checkP2pMailingPersistence the check p2p mailing persistence
	 */
	public void setCheckP2pMailingPersistence(
		CheckP2pMailingPersistence checkP2pMailingPersistence) {
		this.checkP2pMailingPersistence = checkP2pMailingPersistence;
	}

	/**
	 * Returns the competence local service.
	 *
	 * @return the competence local service
	 */
	public CompetenceLocalService getCompetenceLocalService() {
		return competenceLocalService;
	}

	/**
	 * Sets the competence local service.
	 *
	 * @param competenceLocalService the competence local service
	 */
	public void setCompetenceLocalService(
		CompetenceLocalService competenceLocalService) {
		this.competenceLocalService = competenceLocalService;
	}

	/**
	 * Returns the competence remote service.
	 *
	 * @return the competence remote service
	 */
	public CompetenceService getCompetenceService() {
		return competenceService;
	}

	/**
	 * Sets the competence remote service.
	 *
	 * @param competenceService the competence remote service
	 */
	public void setCompetenceService(CompetenceService competenceService) {
		this.competenceService = competenceService;
	}

	/**
	 * Returns the competence persistence.
	 *
	 * @return the competence persistence
	 */
	public CompetencePersistence getCompetencePersistence() {
		return competencePersistence;
	}

	/**
	 * Sets the competence persistence.
	 *
	 * @param competencePersistence the competence persistence
	 */
	public void setCompetencePersistence(
		CompetencePersistence competencePersistence) {
		this.competencePersistence = competencePersistence;
	}

	/**
	 * Returns the course local service.
	 *
	 * @return the course local service
	 */
	public CourseLocalService getCourseLocalService() {
		return courseLocalService;
	}

	/**
	 * Sets the course local service.
	 *
	 * @param courseLocalService the course local service
	 */
	public void setCourseLocalService(CourseLocalService courseLocalService) {
		this.courseLocalService = courseLocalService;
	}

	/**
	 * Returns the course remote service.
	 *
	 * @return the course remote service
	 */
	public CourseService getCourseService() {
		return courseService;
	}

	/**
	 * Sets the course remote service.
	 *
	 * @param courseService the course remote service
	 */
	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	/**
	 * Returns the course persistence.
	 *
	 * @return the course persistence
	 */
	public CoursePersistence getCoursePersistence() {
		return coursePersistence;
	}

	/**
	 * Sets the course persistence.
	 *
	 * @param coursePersistence the course persistence
	 */
	public void setCoursePersistence(CoursePersistence coursePersistence) {
		this.coursePersistence = coursePersistence;
	}

	/**
	 * Returns the course competence local service.
	 *
	 * @return the course competence local service
	 */
	public CourseCompetenceLocalService getCourseCompetenceLocalService() {
		return courseCompetenceLocalService;
	}

	/**
	 * Sets the course competence local service.
	 *
	 * @param courseCompetenceLocalService the course competence local service
	 */
	public void setCourseCompetenceLocalService(
		CourseCompetenceLocalService courseCompetenceLocalService) {
		this.courseCompetenceLocalService = courseCompetenceLocalService;
	}

	/**
	 * Returns the course competence remote service.
	 *
	 * @return the course competence remote service
	 */
	public CourseCompetenceService getCourseCompetenceService() {
		return courseCompetenceService;
	}

	/**
	 * Sets the course competence remote service.
	 *
	 * @param courseCompetenceService the course competence remote service
	 */
	public void setCourseCompetenceService(
		CourseCompetenceService courseCompetenceService) {
		this.courseCompetenceService = courseCompetenceService;
	}

	/**
	 * Returns the course competence persistence.
	 *
	 * @return the course competence persistence
	 */
	public CourseCompetencePersistence getCourseCompetencePersistence() {
		return courseCompetencePersistence;
	}

	/**
	 * Sets the course competence persistence.
	 *
	 * @param courseCompetencePersistence the course competence persistence
	 */
	public void setCourseCompetencePersistence(
		CourseCompetencePersistence courseCompetencePersistence) {
		this.courseCompetencePersistence = courseCompetencePersistence;
	}

	/**
	 * Returns the course result local service.
	 *
	 * @return the course result local service
	 */
	public CourseResultLocalService getCourseResultLocalService() {
		return courseResultLocalService;
	}

	/**
	 * Sets the course result local service.
	 *
	 * @param courseResultLocalService the course result local service
	 */
	public void setCourseResultLocalService(
		CourseResultLocalService courseResultLocalService) {
		this.courseResultLocalService = courseResultLocalService;
	}

	/**
	 * Returns the course result remote service.
	 *
	 * @return the course result remote service
	 */
	public CourseResultService getCourseResultService() {
		return courseResultService;
	}

	/**
	 * Sets the course result remote service.
	 *
	 * @param courseResultService the course result remote service
	 */
	public void setCourseResultService(CourseResultService courseResultService) {
		this.courseResultService = courseResultService;
	}

	/**
	 * Returns the course result persistence.
	 *
	 * @return the course result persistence
	 */
	public CourseResultPersistence getCourseResultPersistence() {
		return courseResultPersistence;
	}

	/**
	 * Sets the course result persistence.
	 *
	 * @param courseResultPersistence the course result persistence
	 */
	public void setCourseResultPersistence(
		CourseResultPersistence courseResultPersistence) {
		this.courseResultPersistence = courseResultPersistence;
	}

	/**
	 * Returns the learning activity local service.
	 *
	 * @return the learning activity local service
	 */
	public LearningActivityLocalService getLearningActivityLocalService() {
		return learningActivityLocalService;
	}

	/**
	 * Sets the learning activity local service.
	 *
	 * @param learningActivityLocalService the learning activity local service
	 */
	public void setLearningActivityLocalService(
		LearningActivityLocalService learningActivityLocalService) {
		this.learningActivityLocalService = learningActivityLocalService;
	}

	/**
	 * Returns the learning activity remote service.
	 *
	 * @return the learning activity remote service
	 */
	public LearningActivityService getLearningActivityService() {
		return learningActivityService;
	}

	/**
	 * Sets the learning activity remote service.
	 *
	 * @param learningActivityService the learning activity remote service
	 */
	public void setLearningActivityService(
		LearningActivityService learningActivityService) {
		this.learningActivityService = learningActivityService;
	}

	/**
	 * Returns the learning activity persistence.
	 *
	 * @return the learning activity persistence
	 */
	public LearningActivityPersistence getLearningActivityPersistence() {
		return learningActivityPersistence;
	}

	/**
	 * Sets the learning activity persistence.
	 *
	 * @param learningActivityPersistence the learning activity persistence
	 */
	public void setLearningActivityPersistence(
		LearningActivityPersistence learningActivityPersistence) {
		this.learningActivityPersistence = learningActivityPersistence;
	}

	/**
	 * Returns the learning activity result local service.
	 *
	 * @return the learning activity result local service
	 */
	public LearningActivityResultLocalService getLearningActivityResultLocalService() {
		return learningActivityResultLocalService;
	}

	/**
	 * Sets the learning activity result local service.
	 *
	 * @param learningActivityResultLocalService the learning activity result local service
	 */
	public void setLearningActivityResultLocalService(
		LearningActivityResultLocalService learningActivityResultLocalService) {
		this.learningActivityResultLocalService = learningActivityResultLocalService;
	}

	/**
	 * Returns the learning activity result remote service.
	 *
	 * @return the learning activity result remote service
	 */
	public LearningActivityResultService getLearningActivityResultService() {
		return learningActivityResultService;
	}

	/**
	 * Sets the learning activity result remote service.
	 *
	 * @param learningActivityResultService the learning activity result remote service
	 */
	public void setLearningActivityResultService(
		LearningActivityResultService learningActivityResultService) {
		this.learningActivityResultService = learningActivityResultService;
	}

	/**
	 * Returns the learning activity result persistence.
	 *
	 * @return the learning activity result persistence
	 */
	public LearningActivityResultPersistence getLearningActivityResultPersistence() {
		return learningActivityResultPersistence;
	}

	/**
	 * Sets the learning activity result persistence.
	 *
	 * @param learningActivityResultPersistence the learning activity result persistence
	 */
	public void setLearningActivityResultPersistence(
		LearningActivityResultPersistence learningActivityResultPersistence) {
		this.learningActivityResultPersistence = learningActivityResultPersistence;
	}

	/**
	 * Returns the learning activity try local service.
	 *
	 * @return the learning activity try local service
	 */
	public LearningActivityTryLocalService getLearningActivityTryLocalService() {
		return learningActivityTryLocalService;
	}

	/**
	 * Sets the learning activity try local service.
	 *
	 * @param learningActivityTryLocalService the learning activity try local service
	 */
	public void setLearningActivityTryLocalService(
		LearningActivityTryLocalService learningActivityTryLocalService) {
		this.learningActivityTryLocalService = learningActivityTryLocalService;
	}

	/**
	 * Returns the learning activity try remote service.
	 *
	 * @return the learning activity try remote service
	 */
	public LearningActivityTryService getLearningActivityTryService() {
		return learningActivityTryService;
	}

	/**
	 * Sets the learning activity try remote service.
	 *
	 * @param learningActivityTryService the learning activity try remote service
	 */
	public void setLearningActivityTryService(
		LearningActivityTryService learningActivityTryService) {
		this.learningActivityTryService = learningActivityTryService;
	}

	/**
	 * Returns the learning activity try persistence.
	 *
	 * @return the learning activity try persistence
	 */
	public LearningActivityTryPersistence getLearningActivityTryPersistence() {
		return learningActivityTryPersistence;
	}

	/**
	 * Sets the learning activity try persistence.
	 *
	 * @param learningActivityTryPersistence the learning activity try persistence
	 */
	public void setLearningActivityTryPersistence(
		LearningActivityTryPersistence learningActivityTryPersistence) {
		this.learningActivityTryPersistence = learningActivityTryPersistence;
	}

	/**
	 * Returns the lms prefs local service.
	 *
	 * @return the lms prefs local service
	 */
	public LmsPrefsLocalService getLmsPrefsLocalService() {
		return lmsPrefsLocalService;
	}

	/**
	 * Sets the lms prefs local service.
	 *
	 * @param lmsPrefsLocalService the lms prefs local service
	 */
	public void setLmsPrefsLocalService(
		LmsPrefsLocalService lmsPrefsLocalService) {
		this.lmsPrefsLocalService = lmsPrefsLocalService;
	}

	/**
	 * Returns the lms prefs persistence.
	 *
	 * @return the lms prefs persistence
	 */
	public LmsPrefsPersistence getLmsPrefsPersistence() {
		return lmsPrefsPersistence;
	}

	/**
	 * Sets the lms prefs persistence.
	 *
	 * @param lmsPrefsPersistence the lms prefs persistence
	 */
	public void setLmsPrefsPersistence(LmsPrefsPersistence lmsPrefsPersistence) {
		this.lmsPrefsPersistence = lmsPrefsPersistence;
	}

	/**
	 * Returns the module local service.
	 *
	 * @return the module local service
	 */
	public ModuleLocalService getModuleLocalService() {
		return moduleLocalService;
	}

	/**
	 * Sets the module local service.
	 *
	 * @param moduleLocalService the module local service
	 */
	public void setModuleLocalService(ModuleLocalService moduleLocalService) {
		this.moduleLocalService = moduleLocalService;
	}

	/**
	 * Returns the module remote service.
	 *
	 * @return the module remote service
	 */
	public ModuleService getModuleService() {
		return moduleService;
	}

	/**
	 * Sets the module remote service.
	 *
	 * @param moduleService the module remote service
	 */
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	/**
	 * Returns the module persistence.
	 *
	 * @return the module persistence
	 */
	public ModulePersistence getModulePersistence() {
		return modulePersistence;
	}

	/**
	 * Sets the module persistence.
	 *
	 * @param modulePersistence the module persistence
	 */
	public void setModulePersistence(ModulePersistence modulePersistence) {
		this.modulePersistence = modulePersistence;
	}

	/**
	 * Returns the module result local service.
	 *
	 * @return the module result local service
	 */
	public ModuleResultLocalService getModuleResultLocalService() {
		return moduleResultLocalService;
	}

	/**
	 * Sets the module result local service.
	 *
	 * @param moduleResultLocalService the module result local service
	 */
	public void setModuleResultLocalService(
		ModuleResultLocalService moduleResultLocalService) {
		this.moduleResultLocalService = moduleResultLocalService;
	}

	/**
	 * Returns the module result remote service.
	 *
	 * @return the module result remote service
	 */
	public ModuleResultService getModuleResultService() {
		return moduleResultService;
	}

	/**
	 * Sets the module result remote service.
	 *
	 * @param moduleResultService the module result remote service
	 */
	public void setModuleResultService(ModuleResultService moduleResultService) {
		this.moduleResultService = moduleResultService;
	}

	/**
	 * Returns the module result persistence.
	 *
	 * @return the module result persistence
	 */
	public ModuleResultPersistence getModuleResultPersistence() {
		return moduleResultPersistence;
	}

	/**
	 * Sets the module result persistence.
	 *
	 * @param moduleResultPersistence the module result persistence
	 */
	public void setModuleResultPersistence(
		ModuleResultPersistence moduleResultPersistence) {
		this.moduleResultPersistence = moduleResultPersistence;
	}

	/**
	 * Returns the p2p activity local service.
	 *
	 * @return the p2p activity local service
	 */
	public P2pActivityLocalService getP2pActivityLocalService() {
		return p2pActivityLocalService;
	}

	/**
	 * Sets the p2p activity local service.
	 *
	 * @param p2pActivityLocalService the p2p activity local service
	 */
	public void setP2pActivityLocalService(
		P2pActivityLocalService p2pActivityLocalService) {
		this.p2pActivityLocalService = p2pActivityLocalService;
	}

	/**
	 * Returns the p2p activity persistence.
	 *
	 * @return the p2p activity persistence
	 */
	public P2pActivityPersistence getP2pActivityPersistence() {
		return p2pActivityPersistence;
	}

	/**
	 * Sets the p2p activity persistence.
	 *
	 * @param p2pActivityPersistence the p2p activity persistence
	 */
	public void setP2pActivityPersistence(
		P2pActivityPersistence p2pActivityPersistence) {
		this.p2pActivityPersistence = p2pActivityPersistence;
	}

	/**
	 * Returns the p2p activity corrections local service.
	 *
	 * @return the p2p activity corrections local service
	 */
	public P2pActivityCorrectionsLocalService getP2pActivityCorrectionsLocalService() {
		return p2pActivityCorrectionsLocalService;
	}

	/**
	 * Sets the p2p activity corrections local service.
	 *
	 * @param p2pActivityCorrectionsLocalService the p2p activity corrections local service
	 */
	public void setP2pActivityCorrectionsLocalService(
		P2pActivityCorrectionsLocalService p2pActivityCorrectionsLocalService) {
		this.p2pActivityCorrectionsLocalService = p2pActivityCorrectionsLocalService;
	}

	/**
	 * Returns the p2p activity corrections persistence.
	 *
	 * @return the p2p activity corrections persistence
	 */
	public P2pActivityCorrectionsPersistence getP2pActivityCorrectionsPersistence() {
		return p2pActivityCorrectionsPersistence;
	}

	/**
	 * Sets the p2p activity corrections persistence.
	 *
	 * @param p2pActivityCorrectionsPersistence the p2p activity corrections persistence
	 */
	public void setP2pActivityCorrectionsPersistence(
		P2pActivityCorrectionsPersistence p2pActivityCorrectionsPersistence) {
		this.p2pActivityCorrectionsPersistence = p2pActivityCorrectionsPersistence;
	}

	/**
	 * Returns the s c o r m content local service.
	 *
	 * @return the s c o r m content local service
	 */
	public SCORMContentLocalService getSCORMContentLocalService() {
		return scormContentLocalService;
	}

	/**
	 * Sets the s c o r m content local service.
	 *
	 * @param scormContentLocalService the s c o r m content local service
	 */
	public void setSCORMContentLocalService(
		SCORMContentLocalService scormContentLocalService) {
		this.scormContentLocalService = scormContentLocalService;
	}

	/**
	 * Returns the s c o r m content remote service.
	 *
	 * @return the s c o r m content remote service
	 */
	public SCORMContentService getSCORMContentService() {
		return scormContentService;
	}

	/**
	 * Sets the s c o r m content remote service.
	 *
	 * @param scormContentService the s c o r m content remote service
	 */
	public void setSCORMContentService(SCORMContentService scormContentService) {
		this.scormContentService = scormContentService;
	}

	/**
	 * Returns the s c o r m content persistence.
	 *
	 * @return the s c o r m content persistence
	 */
	public SCORMContentPersistence getSCORMContentPersistence() {
		return scormContentPersistence;
	}

	/**
	 * Sets the s c o r m content persistence.
	 *
	 * @param scormContentPersistence the s c o r m content persistence
	 */
	public void setSCORMContentPersistence(
		SCORMContentPersistence scormContentPersistence) {
		this.scormContentPersistence = scormContentPersistence;
	}

	/**
	 * Returns the survey result local service.
	 *
	 * @return the survey result local service
	 */
	public SurveyResultLocalService getSurveyResultLocalService() {
		return surveyResultLocalService;
	}

	/**
	 * Sets the survey result local service.
	 *
	 * @param surveyResultLocalService the survey result local service
	 */
	public void setSurveyResultLocalService(
		SurveyResultLocalService surveyResultLocalService) {
		this.surveyResultLocalService = surveyResultLocalService;
	}

	/**
	 * Returns the survey result persistence.
	 *
	 * @return the survey result persistence
	 */
	public SurveyResultPersistence getSurveyResultPersistence() {
		return surveyResultPersistence;
	}

	/**
	 * Sets the survey result persistence.
	 *
	 * @param surveyResultPersistence the survey result persistence
	 */
	public void setSurveyResultPersistence(
		SurveyResultPersistence surveyResultPersistence) {
		this.surveyResultPersistence = surveyResultPersistence;
	}

	/**
	 * Returns the test answer local service.
	 *
	 * @return the test answer local service
	 */
	public TestAnswerLocalService getTestAnswerLocalService() {
		return testAnswerLocalService;
	}

	/**
	 * Sets the test answer local service.
	 *
	 * @param testAnswerLocalService the test answer local service
	 */
	public void setTestAnswerLocalService(
		TestAnswerLocalService testAnswerLocalService) {
		this.testAnswerLocalService = testAnswerLocalService;
	}

	/**
	 * Returns the test answer remote service.
	 *
	 * @return the test answer remote service
	 */
	public TestAnswerService getTestAnswerService() {
		return testAnswerService;
	}

	/**
	 * Sets the test answer remote service.
	 *
	 * @param testAnswerService the test answer remote service
	 */
	public void setTestAnswerService(TestAnswerService testAnswerService) {
		this.testAnswerService = testAnswerService;
	}

	/**
	 * Returns the test answer persistence.
	 *
	 * @return the test answer persistence
	 */
	public TestAnswerPersistence getTestAnswerPersistence() {
		return testAnswerPersistence;
	}

	/**
	 * Sets the test answer persistence.
	 *
	 * @param testAnswerPersistence the test answer persistence
	 */
	public void setTestAnswerPersistence(
		TestAnswerPersistence testAnswerPersistence) {
		this.testAnswerPersistence = testAnswerPersistence;
	}

	/**
	 * Returns the test question local service.
	 *
	 * @return the test question local service
	 */
	public TestQuestionLocalService getTestQuestionLocalService() {
		return testQuestionLocalService;
	}

	/**
	 * Sets the test question local service.
	 *
	 * @param testQuestionLocalService the test question local service
	 */
	public void setTestQuestionLocalService(
		TestQuestionLocalService testQuestionLocalService) {
		this.testQuestionLocalService = testQuestionLocalService;
	}

	/**
	 * Returns the test question remote service.
	 *
	 * @return the test question remote service
	 */
	public TestQuestionService getTestQuestionService() {
		return testQuestionService;
	}

	/**
	 * Sets the test question remote service.
	 *
	 * @param testQuestionService the test question remote service
	 */
	public void setTestQuestionService(TestQuestionService testQuestionService) {
		this.testQuestionService = testQuestionService;
	}

	/**
	 * Returns the test question persistence.
	 *
	 * @return the test question persistence
	 */
	public TestQuestionPersistence getTestQuestionPersistence() {
		return testQuestionPersistence;
	}

	/**
	 * Sets the test question persistence.
	 *
	 * @param testQuestionPersistence the test question persistence
	 */
	public void setTestQuestionPersistence(
		TestQuestionPersistence testQuestionPersistence) {
		this.testQuestionPersistence = testQuestionPersistence;
	}

	/**
	 * Returns the user competence local service.
	 *
	 * @return the user competence local service
	 */
	public UserCompetenceLocalService getUserCompetenceLocalService() {
		return userCompetenceLocalService;
	}

	/**
	 * Sets the user competence local service.
	 *
	 * @param userCompetenceLocalService the user competence local service
	 */
	public void setUserCompetenceLocalService(
		UserCompetenceLocalService userCompetenceLocalService) {
		this.userCompetenceLocalService = userCompetenceLocalService;
	}

	/**
	 * Returns the user competence remote service.
	 *
	 * @return the user competence remote service
	 */
	public UserCompetenceService getUserCompetenceService() {
		return userCompetenceService;
	}

	/**
	 * Sets the user competence remote service.
	 *
	 * @param userCompetenceService the user competence remote service
	 */
	public void setUserCompetenceService(
		UserCompetenceService userCompetenceService) {
		this.userCompetenceService = userCompetenceService;
	}

	/**
	 * Returns the user competence persistence.
	 *
	 * @return the user competence persistence
	 */
	public UserCompetencePersistence getUserCompetencePersistence() {
		return userCompetencePersistence;
	}

	/**
	 * Sets the user competence persistence.
	 *
	 * @param userCompetencePersistence the user competence persistence
	 */
	public void setUserCompetencePersistence(
		UserCompetencePersistence userCompetencePersistence) {
		this.userCompetencePersistence = userCompetencePersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the resource remote service.
	 *
	 * @return the resource remote service
	 */
	public ResourceService getResourceService() {
		return resourceService;
	}

	/**
	 * Sets the resource remote service.
	 *
	 * @param resourceService the resource remote service
	 */
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * Returns the resource persistence.
	 *
	 * @return the resource persistence
	 */
	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	/**
	 * Sets the resource persistence.
	 *
	 * @param resourcePersistence the resource persistence
	 */
	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		PersistedModelLocalServiceRegistryUtil.register("com.liferay.lms.model.LearningActivityResult",
			learningActivityResultLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"com.liferay.lms.model.LearningActivityResult");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
	}

	protected Class<?> getModelClass() {
		return LearningActivityResult.class;
	}

	protected String getModelClassName() {
		return LearningActivityResult.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = learningActivityResultPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = AuditEntryLocalService.class)
	protected AuditEntryLocalService auditEntryLocalService;
	@BeanReference(type = AuditEntryPersistence.class)
	protected AuditEntryPersistence auditEntryPersistence;
	@BeanReference(type = CheckP2pMailingLocalService.class)
	protected CheckP2pMailingLocalService checkP2pMailingLocalService;
	@BeanReference(type = CheckP2pMailingPersistence.class)
	protected CheckP2pMailingPersistence checkP2pMailingPersistence;
	@BeanReference(type = CompetenceLocalService.class)
	protected CompetenceLocalService competenceLocalService;
	@BeanReference(type = CompetenceService.class)
	protected CompetenceService competenceService;
	@BeanReference(type = CompetencePersistence.class)
	protected CompetencePersistence competencePersistence;
	@BeanReference(type = CourseLocalService.class)
	protected CourseLocalService courseLocalService;
	@BeanReference(type = CourseService.class)
	protected CourseService courseService;
	@BeanReference(type = CoursePersistence.class)
	protected CoursePersistence coursePersistence;
	@BeanReference(type = CourseCompetenceLocalService.class)
	protected CourseCompetenceLocalService courseCompetenceLocalService;
	@BeanReference(type = CourseCompetenceService.class)
	protected CourseCompetenceService courseCompetenceService;
	@BeanReference(type = CourseCompetencePersistence.class)
	protected CourseCompetencePersistence courseCompetencePersistence;
	@BeanReference(type = CourseResultLocalService.class)
	protected CourseResultLocalService courseResultLocalService;
	@BeanReference(type = CourseResultService.class)
	protected CourseResultService courseResultService;
	@BeanReference(type = CourseResultPersistence.class)
	protected CourseResultPersistence courseResultPersistence;
	@BeanReference(type = LearningActivityLocalService.class)
	protected LearningActivityLocalService learningActivityLocalService;
	@BeanReference(type = LearningActivityService.class)
	protected LearningActivityService learningActivityService;
	@BeanReference(type = LearningActivityPersistence.class)
	protected LearningActivityPersistence learningActivityPersistence;
	@BeanReference(type = LearningActivityResultLocalService.class)
	protected LearningActivityResultLocalService learningActivityResultLocalService;
	@BeanReference(type = LearningActivityResultService.class)
	protected LearningActivityResultService learningActivityResultService;
	@BeanReference(type = LearningActivityResultPersistence.class)
	protected LearningActivityResultPersistence learningActivityResultPersistence;
	@BeanReference(type = LearningActivityTryLocalService.class)
	protected LearningActivityTryLocalService learningActivityTryLocalService;
	@BeanReference(type = LearningActivityTryService.class)
	protected LearningActivityTryService learningActivityTryService;
	@BeanReference(type = LearningActivityTryPersistence.class)
	protected LearningActivityTryPersistence learningActivityTryPersistence;
	@BeanReference(type = LmsPrefsLocalService.class)
	protected LmsPrefsLocalService lmsPrefsLocalService;
	@BeanReference(type = LmsPrefsPersistence.class)
	protected LmsPrefsPersistence lmsPrefsPersistence;
	@BeanReference(type = ModuleLocalService.class)
	protected ModuleLocalService moduleLocalService;
	@BeanReference(type = ModuleService.class)
	protected ModuleService moduleService;
	@BeanReference(type = ModulePersistence.class)
	protected ModulePersistence modulePersistence;
	@BeanReference(type = ModuleResultLocalService.class)
	protected ModuleResultLocalService moduleResultLocalService;
	@BeanReference(type = ModuleResultService.class)
	protected ModuleResultService moduleResultService;
	@BeanReference(type = ModuleResultPersistence.class)
	protected ModuleResultPersistence moduleResultPersistence;
	@BeanReference(type = P2pActivityLocalService.class)
	protected P2pActivityLocalService p2pActivityLocalService;
	@BeanReference(type = P2pActivityPersistence.class)
	protected P2pActivityPersistence p2pActivityPersistence;
	@BeanReference(type = P2pActivityCorrectionsLocalService.class)
	protected P2pActivityCorrectionsLocalService p2pActivityCorrectionsLocalService;
	@BeanReference(type = P2pActivityCorrectionsPersistence.class)
	protected P2pActivityCorrectionsPersistence p2pActivityCorrectionsPersistence;
	@BeanReference(type = SCORMContentLocalService.class)
	protected SCORMContentLocalService scormContentLocalService;
	@BeanReference(type = SCORMContentService.class)
	protected SCORMContentService scormContentService;
	@BeanReference(type = SCORMContentPersistence.class)
	protected SCORMContentPersistence scormContentPersistence;
	@BeanReference(type = SurveyResultLocalService.class)
	protected SurveyResultLocalService surveyResultLocalService;
	@BeanReference(type = SurveyResultPersistence.class)
	protected SurveyResultPersistence surveyResultPersistence;
	@BeanReference(type = TestAnswerLocalService.class)
	protected TestAnswerLocalService testAnswerLocalService;
	@BeanReference(type = TestAnswerService.class)
	protected TestAnswerService testAnswerService;
	@BeanReference(type = TestAnswerPersistence.class)
	protected TestAnswerPersistence testAnswerPersistence;
	@BeanReference(type = TestQuestionLocalService.class)
	protected TestQuestionLocalService testQuestionLocalService;
	@BeanReference(type = TestQuestionService.class)
	protected TestQuestionService testQuestionService;
	@BeanReference(type = TestQuestionPersistence.class)
	protected TestQuestionPersistence testQuestionPersistence;
	@BeanReference(type = UserCompetenceLocalService.class)
	protected UserCompetenceLocalService userCompetenceLocalService;
	@BeanReference(type = UserCompetenceService.class)
	protected UserCompetenceService userCompetenceService;
	@BeanReference(type = UserCompetencePersistence.class)
	protected UserCompetencePersistence userCompetencePersistence;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = ResourceService.class)
	protected ResourceService resourceService;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private String _beanIdentifier;
	private LearningActivityResultLocalServiceClpInvoker _clpInvoker = new LearningActivityResultLocalServiceClpInvoker();
}