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

import com.liferay.lms.service.ActivityTriesDeletedLocalService;
import com.liferay.lms.service.AuditEntryLocalService;
import com.liferay.lms.service.AuditLoggerLocalService;
import com.liferay.lms.service.AuditLoggerService;
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
import com.liferay.lms.service.ScheduleLocalService;
import com.liferay.lms.service.SurveyResultLocalService;
import com.liferay.lms.service.TestAnswerLocalService;
import com.liferay.lms.service.TestAnswerService;
import com.liferay.lms.service.TestQuestionLocalService;
import com.liferay.lms.service.TestQuestionService;
import com.liferay.lms.service.UserCertificateDownloadLocalService;
import com.liferay.lms.service.UserCompetenceLocalService;
import com.liferay.lms.service.UserCompetenceService;
import com.liferay.lms.service.persistence.ActivityTriesDeletedPersistence;
import com.liferay.lms.service.persistence.AuditEntryPersistence;
import com.liferay.lms.service.persistence.CheckP2pMailingPersistence;
import com.liferay.lms.service.persistence.CompetencePersistence;
import com.liferay.lms.service.persistence.CourseCompetencePersistence;
import com.liferay.lms.service.persistence.CourseFinder;
import com.liferay.lms.service.persistence.CoursePersistence;
import com.liferay.lms.service.persistence.CourseResultFinder;
import com.liferay.lms.service.persistence.CourseResultPersistence;
import com.liferay.lms.service.persistence.LearningActivityFinder;
import com.liferay.lms.service.persistence.LearningActivityPersistence;
import com.liferay.lms.service.persistence.LearningActivityResultFinder;
import com.liferay.lms.service.persistence.LearningActivityResultPersistence;
import com.liferay.lms.service.persistence.LearningActivityTryFinder;
import com.liferay.lms.service.persistence.LearningActivityTryPersistence;
import com.liferay.lms.service.persistence.LmsPrefsPersistence;
import com.liferay.lms.service.persistence.ModulePersistence;
import com.liferay.lms.service.persistence.ModuleResultFinder;
import com.liferay.lms.service.persistence.ModuleResultPersistence;
import com.liferay.lms.service.persistence.P2pActivityCorrectionsPersistence;
import com.liferay.lms.service.persistence.P2pActivityPersistence;
import com.liferay.lms.service.persistence.SchedulePersistence;
import com.liferay.lms.service.persistence.SurveyResultFinder;
import com.liferay.lms.service.persistence.SurveyResultPersistence;
import com.liferay.lms.service.persistence.TestAnswerPersistence;
import com.liferay.lms.service.persistence.TestQuestionPersistence;
import com.liferay.lms.service.persistence.UserCertificateDownloadPersistence;
import com.liferay.lms.service.persistence.UserCompetencePersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;

import javax.sql.DataSource;

/**
 * The base implementation of the audit logger local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.lms.service.impl.AuditLoggerLocalServiceImpl}.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.impl.AuditLoggerLocalServiceImpl
 * @see com.liferay.lms.service.AuditLoggerLocalServiceUtil
 * @generated
 */
public abstract class AuditLoggerLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements AuditLoggerLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.lms.service.AuditLoggerLocalServiceUtil} to access the audit logger local service.
	 */

	/**
	 * Returns the activity tries deleted local service.
	 *
	 * @return the activity tries deleted local service
	 */
	public ActivityTriesDeletedLocalService getActivityTriesDeletedLocalService() {
		return activityTriesDeletedLocalService;
	}

	/**
	 * Sets the activity tries deleted local service.
	 *
	 * @param activityTriesDeletedLocalService the activity tries deleted local service
	 */
	public void setActivityTriesDeletedLocalService(
		ActivityTriesDeletedLocalService activityTriesDeletedLocalService) {
		this.activityTriesDeletedLocalService = activityTriesDeletedLocalService;
	}

	/**
	 * Returns the activity tries deleted persistence.
	 *
	 * @return the activity tries deleted persistence
	 */
	public ActivityTriesDeletedPersistence getActivityTriesDeletedPersistence() {
		return activityTriesDeletedPersistence;
	}

	/**
	 * Sets the activity tries deleted persistence.
	 *
	 * @param activityTriesDeletedPersistence the activity tries deleted persistence
	 */
	public void setActivityTriesDeletedPersistence(
		ActivityTriesDeletedPersistence activityTriesDeletedPersistence) {
		this.activityTriesDeletedPersistence = activityTriesDeletedPersistence;
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
	 * Returns the audit logger local service.
	 *
	 * @return the audit logger local service
	 */
	public AuditLoggerLocalService getAuditLoggerLocalService() {
		return auditLoggerLocalService;
	}

	/**
	 * Sets the audit logger local service.
	 *
	 * @param auditLoggerLocalService the audit logger local service
	 */
	public void setAuditLoggerLocalService(
		AuditLoggerLocalService auditLoggerLocalService) {
		this.auditLoggerLocalService = auditLoggerLocalService;
	}

	/**
	 * Returns the audit logger remote service.
	 *
	 * @return the audit logger remote service
	 */
	public AuditLoggerService getAuditLoggerService() {
		return auditLoggerService;
	}

	/**
	 * Sets the audit logger remote service.
	 *
	 * @param auditLoggerService the audit logger remote service
	 */
	public void setAuditLoggerService(AuditLoggerService auditLoggerService) {
		this.auditLoggerService = auditLoggerService;
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
	 * Returns the course finder.
	 *
	 * @return the course finder
	 */
	public CourseFinder getCourseFinder() {
		return courseFinder;
	}

	/**
	 * Sets the course finder.
	 *
	 * @param courseFinder the course finder
	 */
	public void setCourseFinder(CourseFinder courseFinder) {
		this.courseFinder = courseFinder;
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
	 * Returns the course result finder.
	 *
	 * @return the course result finder
	 */
	public CourseResultFinder getCourseResultFinder() {
		return courseResultFinder;
	}

	/**
	 * Sets the course result finder.
	 *
	 * @param courseResultFinder the course result finder
	 */
	public void setCourseResultFinder(CourseResultFinder courseResultFinder) {
		this.courseResultFinder = courseResultFinder;
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
	 * Returns the learning activity finder.
	 *
	 * @return the learning activity finder
	 */
	public LearningActivityFinder getLearningActivityFinder() {
		return learningActivityFinder;
	}

	/**
	 * Sets the learning activity finder.
	 *
	 * @param learningActivityFinder the learning activity finder
	 */
	public void setLearningActivityFinder(
		LearningActivityFinder learningActivityFinder) {
		this.learningActivityFinder = learningActivityFinder;
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
	 * Returns the learning activity result finder.
	 *
	 * @return the learning activity result finder
	 */
	public LearningActivityResultFinder getLearningActivityResultFinder() {
		return learningActivityResultFinder;
	}

	/**
	 * Sets the learning activity result finder.
	 *
	 * @param learningActivityResultFinder the learning activity result finder
	 */
	public void setLearningActivityResultFinder(
		LearningActivityResultFinder learningActivityResultFinder) {
		this.learningActivityResultFinder = learningActivityResultFinder;
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
	 * Returns the learning activity try finder.
	 *
	 * @return the learning activity try finder
	 */
	public LearningActivityTryFinder getLearningActivityTryFinder() {
		return learningActivityTryFinder;
	}

	/**
	 * Sets the learning activity try finder.
	 *
	 * @param learningActivityTryFinder the learning activity try finder
	 */
	public void setLearningActivityTryFinder(
		LearningActivityTryFinder learningActivityTryFinder) {
		this.learningActivityTryFinder = learningActivityTryFinder;
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
	 * Returns the module result finder.
	 *
	 * @return the module result finder
	 */
	public ModuleResultFinder getModuleResultFinder() {
		return moduleResultFinder;
	}

	/**
	 * Sets the module result finder.
	 *
	 * @param moduleResultFinder the module result finder
	 */
	public void setModuleResultFinder(ModuleResultFinder moduleResultFinder) {
		this.moduleResultFinder = moduleResultFinder;
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
	 * Returns the schedule local service.
	 *
	 * @return the schedule local service
	 */
	public ScheduleLocalService getScheduleLocalService() {
		return scheduleLocalService;
	}

	/**
	 * Sets the schedule local service.
	 *
	 * @param scheduleLocalService the schedule local service
	 */
	public void setScheduleLocalService(
		ScheduleLocalService scheduleLocalService) {
		this.scheduleLocalService = scheduleLocalService;
	}

	/**
	 * Returns the schedule persistence.
	 *
	 * @return the schedule persistence
	 */
	public SchedulePersistence getSchedulePersistence() {
		return schedulePersistence;
	}

	/**
	 * Sets the schedule persistence.
	 *
	 * @param schedulePersistence the schedule persistence
	 */
	public void setSchedulePersistence(SchedulePersistence schedulePersistence) {
		this.schedulePersistence = schedulePersistence;
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
	 * Returns the survey result finder.
	 *
	 * @return the survey result finder
	 */
	public SurveyResultFinder getSurveyResultFinder() {
		return surveyResultFinder;
	}

	/**
	 * Sets the survey result finder.
	 *
	 * @param surveyResultFinder the survey result finder
	 */
	public void setSurveyResultFinder(SurveyResultFinder surveyResultFinder) {
		this.surveyResultFinder = surveyResultFinder;
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
	 * Returns the user certificate download local service.
	 *
	 * @return the user certificate download local service
	 */
	public UserCertificateDownloadLocalService getUserCertificateDownloadLocalService() {
		return userCertificateDownloadLocalService;
	}

	/**
	 * Sets the user certificate download local service.
	 *
	 * @param userCertificateDownloadLocalService the user certificate download local service
	 */
	public void setUserCertificateDownloadLocalService(
		UserCertificateDownloadLocalService userCertificateDownloadLocalService) {
		this.userCertificateDownloadLocalService = userCertificateDownloadLocalService;
	}

	/**
	 * Returns the user certificate download persistence.
	 *
	 * @return the user certificate download persistence
	 */
	public UserCertificateDownloadPersistence getUserCertificateDownloadPersistence() {
		return userCertificateDownloadPersistence;
	}

	/**
	 * Sets the user certificate download persistence.
	 *
	 * @param userCertificateDownloadPersistence the user certificate download persistence
	 */
	public void setUserCertificateDownloadPersistence(
		UserCertificateDownloadPersistence userCertificateDownloadPersistence) {
		this.userCertificateDownloadPersistence = userCertificateDownloadPersistence;
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
	}

	public void destroy() {
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

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = InfrastructureUtil.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = ActivityTriesDeletedLocalService.class)
	protected ActivityTriesDeletedLocalService activityTriesDeletedLocalService;
	@BeanReference(type = ActivityTriesDeletedPersistence.class)
	protected ActivityTriesDeletedPersistence activityTriesDeletedPersistence;
	@BeanReference(type = AuditEntryLocalService.class)
	protected AuditEntryLocalService auditEntryLocalService;
	@BeanReference(type = AuditEntryPersistence.class)
	protected AuditEntryPersistence auditEntryPersistence;
	@BeanReference(type = AuditLoggerLocalService.class)
	protected AuditLoggerLocalService auditLoggerLocalService;
	@BeanReference(type = AuditLoggerService.class)
	protected AuditLoggerService auditLoggerService;
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
	@BeanReference(type = CourseFinder.class)
	protected CourseFinder courseFinder;
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
	@BeanReference(type = CourseResultFinder.class)
	protected CourseResultFinder courseResultFinder;
	@BeanReference(type = LearningActivityLocalService.class)
	protected LearningActivityLocalService learningActivityLocalService;
	@BeanReference(type = LearningActivityService.class)
	protected LearningActivityService learningActivityService;
	@BeanReference(type = LearningActivityPersistence.class)
	protected LearningActivityPersistence learningActivityPersistence;
	@BeanReference(type = LearningActivityFinder.class)
	protected LearningActivityFinder learningActivityFinder;
	@BeanReference(type = LearningActivityResultLocalService.class)
	protected LearningActivityResultLocalService learningActivityResultLocalService;
	@BeanReference(type = LearningActivityResultService.class)
	protected LearningActivityResultService learningActivityResultService;
	@BeanReference(type = LearningActivityResultPersistence.class)
	protected LearningActivityResultPersistence learningActivityResultPersistence;
	@BeanReference(type = LearningActivityResultFinder.class)
	protected LearningActivityResultFinder learningActivityResultFinder;
	@BeanReference(type = LearningActivityTryLocalService.class)
	protected LearningActivityTryLocalService learningActivityTryLocalService;
	@BeanReference(type = LearningActivityTryService.class)
	protected LearningActivityTryService learningActivityTryService;
	@BeanReference(type = LearningActivityTryPersistence.class)
	protected LearningActivityTryPersistence learningActivityTryPersistence;
	@BeanReference(type = LearningActivityTryFinder.class)
	protected LearningActivityTryFinder learningActivityTryFinder;
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
	@BeanReference(type = ModuleResultFinder.class)
	protected ModuleResultFinder moduleResultFinder;
	@BeanReference(type = P2pActivityLocalService.class)
	protected P2pActivityLocalService p2pActivityLocalService;
	@BeanReference(type = P2pActivityPersistence.class)
	protected P2pActivityPersistence p2pActivityPersistence;
	@BeanReference(type = P2pActivityCorrectionsLocalService.class)
	protected P2pActivityCorrectionsLocalService p2pActivityCorrectionsLocalService;
	@BeanReference(type = P2pActivityCorrectionsPersistence.class)
	protected P2pActivityCorrectionsPersistence p2pActivityCorrectionsPersistence;
	@BeanReference(type = ScheduleLocalService.class)
	protected ScheduleLocalService scheduleLocalService;
	@BeanReference(type = SchedulePersistence.class)
	protected SchedulePersistence schedulePersistence;
	@BeanReference(type = SurveyResultLocalService.class)
	protected SurveyResultLocalService surveyResultLocalService;
	@BeanReference(type = SurveyResultPersistence.class)
	protected SurveyResultPersistence surveyResultPersistence;
	@BeanReference(type = SurveyResultFinder.class)
	protected SurveyResultFinder surveyResultFinder;
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
	@BeanReference(type = UserCertificateDownloadLocalService.class)
	protected UserCertificateDownloadLocalService userCertificateDownloadLocalService;
	@BeanReference(type = UserCertificateDownloadPersistence.class)
	protected UserCertificateDownloadPersistence userCertificateDownloadPersistence;
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
	private AuditLoggerLocalServiceClpInvoker _clpInvoker = new AuditLoggerLocalServiceClpInvoker();
}