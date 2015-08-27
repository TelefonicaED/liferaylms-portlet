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

package com.liferay.lms.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link LearningActivityLocalService}.
 * </p>
 *
 * @author    TLS
 * @see       LearningActivityLocalService
 * @generated
 */
public class LearningActivityLocalServiceWrapper
	implements LearningActivityLocalService,
		ServiceWrapper<LearningActivityLocalService> {
	public LearningActivityLocalServiceWrapper(
		LearningActivityLocalService learningActivityLocalService) {
		_learningActivityLocalService = learningActivityLocalService;
	}

	/**
	* Adds the learning activity to the database. Also notifies the appropriate model listeners.
	*
	* @param learningActivity the learning activity
	* @return the learning activity that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivity addLearningActivity(
		com.liferay.lms.model.LearningActivity learningActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.addLearningActivity(learningActivity);
	}

	/**
	* Creates a new learning activity with the primary key. Does not add the learning activity to the database.
	*
	* @param actId the primary key for the new learning activity
	* @return the new learning activity
	*/
	public com.liferay.lms.model.LearningActivity createLearningActivity(
		long actId) {
		return _learningActivityLocalService.createLearningActivity(actId);
	}

	/**
	* Deletes the learning activity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param actId the primary key of the learning activity
	* @return the learning activity that was removed
	* @throws PortalException if a learning activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivity deleteLearningActivity(
		long actId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.deleteLearningActivity(actId);
	}

	/**
	* Deletes the learning activity from the database. Also notifies the appropriate model listeners.
	*
	* @param learningActivity the learning activity
	* @return the learning activity that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivity deleteLearningActivity(
		com.liferay.lms.model.LearningActivity learningActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.deleteLearningActivity(learningActivity);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _learningActivityLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.dynamicQuery(dynamicQuery);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.dynamicQuery(dynamicQuery, start,
			end);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.lms.model.LearningActivity fetchLearningActivity(
		long actId) throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.fetchLearningActivity(actId);
	}

	/**
	* Returns the learning activity with the primary key.
	*
	* @param actId the primary key of the learning activity
	* @return the learning activity
	* @throws PortalException if a learning activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivity getLearningActivity(
		long actId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getLearningActivity(actId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the learning activity with the UUID in the group.
	*
	* @param uuid the UUID of learning activity
	* @param groupId the group id of the learning activity
	* @return the learning activity
	* @throws PortalException if a learning activity with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivity getLearningActivityByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getLearningActivityByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the learning activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of learning activities
	* @param end the upper bound of the range of learning activities (not inclusive)
	* @return the range of learning activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.LearningActivity> getLearningActivities(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getLearningActivities(start, end);
	}

	/**
	* Returns the number of learning activities.
	*
	* @return the number of learning activities
	* @throws SystemException if a system exception occurred
	*/
	public int getLearningActivitiesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getLearningActivitiesCount();
	}

	/**
	* Updates the learning activity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param learningActivity the learning activity
	* @return the learning activity that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivity updateLearningActivity(
		com.liferay.lms.model.LearningActivity learningActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.updateLearningActivity(learningActivity);
	}

	/**
	* Updates the learning activity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param learningActivity the learning activity
	* @param merge whether to merge the learning activity with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the learning activity that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.LearningActivity updateLearningActivity(
		com.liferay.lms.model.LearningActivity learningActivity, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.updateLearningActivity(learningActivity,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _learningActivityLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_learningActivityLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _learningActivityLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	public boolean islocked(long actId, long userId) throws java.lang.Exception {
		return _learningActivityLocalService.islocked(actId, userId);
	}

	public com.liferay.lms.model.LearningActivity addLearningActivity(
		com.liferay.lms.model.LearningActivity learningActivity,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.addLearningActivity(learningActivity,
			serviceContext);
	}

	public com.liferay.lms.model.LearningActivity addLearningActivity(
		java.lang.String title, java.lang.String description,
		java.util.Date createDate, java.util.Date startDate,
		java.util.Date endDate, int typeId, long tries, int passpuntuation,
		long moduleId, java.lang.String extracontent,
		java.lang.String feedbackCorrect, java.lang.String feedbackNoCorrect,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.addLearningActivity(title,
			description, createDate, startDate, endDate, typeId, tries,
			passpuntuation, moduleId, extracontent, feedbackCorrect,
			feedbackNoCorrect, serviceContext);
	}

	public com.liferay.lms.model.LearningActivity addLearningActivity(
		long userId, long groupId, int status,
		java.util.Map<java.util.Locale, java.lang.String> title,
		java.util.Map<java.util.Locale, java.lang.String> description,
		int typeId, java.util.Date startdate, java.util.Date enddate,
		long precedence, long tries, int passpuntuation, long moduleId,
		java.lang.String extracontent, java.lang.String feedbackCorrect,
		java.lang.String feedbackNoCorrect, long weightinmodule, long teamId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.addLearningActivity(userId,
			groupId, status, title, description, typeId, startdate, enddate,
			precedence, tries, passpuntuation, moduleId, extracontent,
			feedbackCorrect, feedbackNoCorrect, weightinmodule, teamId,
			serviceContext);
	}

	public com.liferay.lms.model.LearningActivity modLearningActivity(
		long actId, java.lang.String title, java.lang.String description,
		java.util.Date createDate, java.util.Date startDate,
		java.util.Date endDate, int typeId, long tries, int passpuntuation,
		long moduleId, java.lang.String extracontent,
		java.lang.String feedbackCorrect, java.lang.String feedbackNoCorrect,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.modLearningActivity(actId, title,
			description, createDate, startDate, endDate, typeId, tries,
			passpuntuation, moduleId, extracontent, feedbackCorrect,
			feedbackNoCorrect, serviceContext);
	}

	public com.liferay.lms.model.LearningActivity modLearningActivity(
		com.liferay.lms.model.LearningActivity larn,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.modLearningActivity(larn,
			serviceContext);
	}

	public com.liferay.lms.model.LearningActivity modLearningActivity(
		com.liferay.lms.model.LearningActivity larn)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.modLearningActivity(larn);
	}

	public java.util.List<com.liferay.lms.model.LearningActivity> getLearningActivitiesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getLearningActivitiesOfGroup(groupId);
	}

	public java.util.List<com.liferay.lms.model.LearningActivity> getMandatoryLearningActivitiesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getMandatoryLearningActivitiesOfGroup(groupId);
	}

	public long countLearningActivitiesOfGroup(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.countLearningActivitiesOfGroup(groupId);
	}

	public java.util.List<com.liferay.lms.model.LearningActivity> getLearningActivitiesOfGroupAndType(
		long groupId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getLearningActivitiesOfGroupAndType(groupId,
			typeId);
	}

	public java.util.List<com.liferay.lms.model.LearningActivity> getLearningActivitiesOfModule(
		long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getLearningActivitiesOfModule(moduleId);
	}

	public java.util.List<java.lang.Long> getLearningActivityIdsOfModule(
		long moduleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getLearningActivityIdsOfModule(moduleId);
	}

	public void deleteLearningactivity(
		com.liferay.lms.model.LearningActivity lernact)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_learningActivityLocalService.deleteLearningactivity(lernact);
	}

	public com.liferay.lms.model.LearningActivity getPreviusLearningActivity(
		long actId) throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getPreviusLearningActivity(actId);
	}

	public com.liferay.lms.model.LearningActivity getPreviusLearningActivity(
		com.liferay.lms.model.LearningActivity larn)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getPreviusLearningActivity(larn);
	}

	public void goUpLearningActivity(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_learningActivityLocalService.goUpLearningActivity(actId);
	}

	public void goDownLearningActivity(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_learningActivityLocalService.goDownLearningActivity(actId);
	}

	public void moveActivity(long actId, long previusAct, long nextAct)
		throws com.liferay.portal.kernel.exception.SystemException {
		_learningActivityLocalService.moveActivity(actId, previusAct, nextAct);
	}

	public com.liferay.lms.model.LearningActivity getNextLearningActivity(
		long actId) throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getNextLearningActivity(actId);
	}

	public com.liferay.lms.model.LearningActivity getNextLearningActivity(
		com.liferay.lms.model.LearningActivity larn)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getNextLearningActivity(larn);
	}

	public void deleteLearningactivity(long actId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_learningActivityLocalService.deleteLearningactivity(actId);
	}

	public java.lang.String getExtraContentValue(long actId,
		java.lang.String key, java.lang.String defaultValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getExtraContentValue(actId, key,
			defaultValue);
	}

	public java.lang.String getExtraContentValue(long actId,
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getExtraContentValue(actId, key);
	}

	public java.util.List<java.lang.String> getExtraContentValues(long actId,
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.getExtraContentValues(actId, key);
	}

	public void setExtraContentValue(long actId, java.lang.String name,
		java.lang.String val)
		throws com.liferay.portal.kernel.exception.SystemException {
		_learningActivityLocalService.setExtraContentValue(actId, name, val);
	}

	public java.util.HashMap<java.lang.String, java.lang.String> convertXMLExtraContentToHashMap(
		long actId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityLocalService.convertXMLExtraContentToHashMap(actId);
	}

	public void saveHashMapToXMLExtraContent(long actId,
		java.util.HashMap<java.lang.String, java.lang.String> map)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_learningActivityLocalService.saveHashMapToXMLExtraContent(actId, map);
	}

	public boolean isLearningActivityDeleteTries(long typeId) {
		return _learningActivityLocalService.isLearningActivityDeleteTries(typeId);
	}

	public boolean canBeView(com.liferay.lms.model.LearningActivity activity,
		long userId) throws java.lang.Exception {
		return _learningActivityLocalService.canBeView(activity, userId);
	}

	public boolean canBeView(com.liferay.lms.model.LearningActivity activity,
		com.liferay.portal.security.permission.PermissionChecker permissionChecker)
		throws java.lang.Exception {
		return _learningActivityLocalService.canBeView(activity,
			permissionChecker);
	}

	public boolean canBeEdited(
		com.liferay.lms.model.LearningActivity activity, long userId)
		throws java.lang.Exception {
		return _learningActivityLocalService.canBeEdited(activity, userId);
	}

	public boolean canBeEdited(
		com.liferay.lms.model.LearningActivity activity,
		com.liferay.portal.security.permission.PermissionChecker permissionChecker)
		throws java.lang.Exception {
		return _learningActivityLocalService.canBeEdited(activity,
			permissionChecker);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public LearningActivityLocalService getWrappedLearningActivityLocalService() {
		return _learningActivityLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedLearningActivityLocalService(
		LearningActivityLocalService learningActivityLocalService) {
		_learningActivityLocalService = learningActivityLocalService;
	}

	public LearningActivityLocalService getWrappedService() {
		return _learningActivityLocalService;
	}

	public void setWrappedService(
		LearningActivityLocalService learningActivityLocalService) {
		_learningActivityLocalService = learningActivityLocalService;
	}

	private LearningActivityLocalService _learningActivityLocalService;
}