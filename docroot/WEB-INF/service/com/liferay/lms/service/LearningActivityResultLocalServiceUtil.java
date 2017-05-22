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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * The utility for the learning activity result local service. This utility wraps {@link com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see LearningActivityResultLocalService
 * @see com.liferay.lms.service.base.LearningActivityResultLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl
 * @generated
 */
public class LearningActivityResultLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the learning activity result to the database. Also notifies the appropriate model listeners.
	*
	* @param learningActivityResult the learning activity result
	* @return the learning activity result that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult addLearningActivityResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addLearningActivityResult(learningActivityResult);
	}

	/**
	* Creates a new learning activity result with the primary key. Does not add the learning activity result to the database.
	*
	* @param larId the primary key for the new learning activity result
	* @return the new learning activity result
	*/
	public static com.liferay.lms.model.LearningActivityResult createLearningActivityResult(
		long larId) {
		return getService().createLearningActivityResult(larId);
	}

	/**
	* Deletes the learning activity result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param larId the primary key of the learning activity result
	* @return the learning activity result that was removed
	* @throws PortalException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult deleteLearningActivityResult(
		long larId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteLearningActivityResult(larId);
	}

	/**
	* Deletes the learning activity result from the database. Also notifies the appropriate model listeners.
	*
	* @param learningActivityResult the learning activity result
	* @return the learning activity result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult deleteLearningActivityResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteLearningActivityResult(learningActivityResult);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.lms.model.LearningActivityResult fetchLearningActivityResult(
		long larId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchLearningActivityResult(larId);
	}

	/**
	* Returns the learning activity result with the primary key.
	*
	* @param larId the primary key of the learning activity result
	* @return the learning activity result
	* @throws PortalException if a learning activity result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult getLearningActivityResult(
		long larId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLearningActivityResult(larId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static java.util.List<com.liferay.lms.model.LearningActivityResult> getLearningActivityResults(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLearningActivityResults(start, end);
	}

	/**
	* Returns the number of learning activity results.
	*
	* @return the number of learning activity results
	* @throws SystemException if a system exception occurred
	*/
	public static int getLearningActivityResultsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLearningActivityResultsCount();
	}

	/**
	* Updates the learning activity result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param learningActivityResult the learning activity result
	* @return the learning activity result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult updateLearningActivityResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLearningActivityResult(learningActivityResult);
	}

	/**
	* Updates the learning activity result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param learningActivityResult the learning activity result
	* @param merge whether to merge the learning activity result with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the learning activity result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.LearningActivityResult updateLearningActivityResult(
		com.liferay.lms.model.LearningActivityResult learningActivityResult,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLearningActivityResult(learningActivityResult, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static com.liferay.lms.model.LearningActivityResult update(
		com.liferay.lms.model.LearningActivityTry learningActivityTry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().update(learningActivityTry);
	}

	public static com.liferay.lms.model.LearningActivityResult update(
		long latId, long result, java.lang.String tryResultData, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().update(latId, result, tryResultData, userId);
	}

	public static boolean existsLearningActivityResult(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().existsLearningActivityResult(actId, userId);
	}

	public static boolean userPassed(long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().userPassed(actId, userId);
	}

	public static long countPassed(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countPassed(actId);
	}

	public static long countByActId(long actId) {
		return getService().countByActId(actId);
	}

	/**
	* Devuelve el nÃƒÂºmero de estudiantes que han pasado una actividad (en caso de passed = true) o lo que han suspendido o la tienen en curos (en caso de passed = false)
	*
	* @param actId id de la actividad
	* @param companyId id de la company de la actividad
	* @param courseGroupCreatedId id del group del curso
	* @param passed si se quieren los aprobados o no
	* @return nÃƒÂºmero de estudiantes que han pasado la actividad o los que han suspendido + los que la tienen no la han finalizado
	* @throws SystemException
	*/
	public static long countPassedOnlyStudents(long actId, long companyId,
		long courseGroupCreatedId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countPassedOnlyStudents(actId, companyId,
			courseGroupCreatedId, passed);
	}

	/**
	* @deprecated SE RECOMIENDA NO USAR ESTE MÃƒâ€°TODO, SE RECOMIENDA USAR: countPassedOnlyStudents(long actId, long companyId, long courseGroupCreatedId, boolean passed)
	o countPassedOnlyStudents(long actId, boolean passed, long[] userExcludedIds)
	* @param actId id de la actividad
	* @param companyId id de la company de la actividad
	* @param courseGroupCreatedId id del group del curso
	* @param passed si se quieren los aprobados o no
	* @param lista de estudiantes, si viene vacÃƒÂ­a se calculan dentro
	* @return nÃƒÂºmero de estudiantes que han pasado la actividad o los que han suspendido + los que la tienen no la han finalizado
	*/
	public static long countPassedOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId, boolean passed,
		java.util.List<com.liferay.portal.model.User> _students)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countPassedOnlyStudents(actId, companyId,
			courseGropupCreatedId, passed, _students);
	}

	/**
	* Devuelve el nÃƒÂºmero de usuarios que han suspendido una actividad
	*
	* @param actId id de la actividad
	* @return nÃƒÂºmero de usuarios que han suspendido la actividad
	*/
	public static long countNotPassed(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countNotPassed(actId);
	}

	/**
	* Devuelve el nÃƒÂºmero de estudiantes que han suspendido la actividad
	*
	* @param actId id de la actividad
	* @param companyId id de la company de la actividad
	* @param courseGroupCreatedId id del group de la actividad
	*/
	public static long countNotPassedOnlyStudents(long actId, long companyId,
		long courseGroupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countNotPassedOnlyStudents(actId, companyId,
			courseGroupCreatedId);
	}

	/**
	* @deprecated SE RECOMIENDA NO USAR ESTE MÃƒâ€°TODO, SE RECOMIENDA USAR: countNotPassedOnlyStudents(long actId, long companyId, long courseGroupCreatedId)
	o countStudentsByActIdUserExcludedIdsFailed(actId, userExcludedIds)
	* @param actId id de la actividad
	* @param companyId id de la company de la actividad
	* @param courseGroupCreatedId id del group del curso
	* @param lista de estudiantes, si viene vacÃƒÂ­a se calculan dentro
	* @return nÃƒÂºmero de estudiantes que han suspendido la actividad
	*/
	public static long countNotPassedOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId,
		java.util.List<com.liferay.portal.model.User> _students)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countNotPassedOnlyStudents(actId, companyId,
			courseGropupCreatedId, _students);
	}

	public static java.lang.Double avgResult(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().avgResult(actId);
	}

	/**
	* Devuelve la media de resultado de usuarios para una actividad, si ya se tiene la lista de usuarios excluidos (profesores y editores)
	* llamar al mÃƒÂ©todo avgResultByActIdUserExcludedIds directamente
	*
	* @param actId id de la actividad
	* @param companyId id de la company de la actividad
	* @param courseGroupCreatedId id del group del curso
	* @return media de resultado de usuarios para una actividad
	*/
	public static java.lang.Double avgResultOnlyStudents(long actId,
		long companyId, long courseGroupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .avgResultOnlyStudents(actId, companyId, courseGroupCreatedId);
	}

	/**
	* @deprecated SE RECOMIENDA NO USAR ESTE MÃƒâ€°TODO, SE RECOMIENDA USAR: avgResultOnlyStudents(long actId, long companyId, long courseGroupCreatedId)
	o avgResultByActIdUserExcludedIds(long actId, long[] userExcludedIds)
	* @param actId id de la actividad
	* @param companyId id de la company de la actividad
	* @param courseGroupCreatedId id del group del curso
	* @param _students lista de estudiantes, si viene vacÃƒÂ­a se calculan dentro
	* @return media de resultado de usuarios para una actividad
	*/
	public static java.lang.Double avgResultOnlyStudents(long actId,
		long companyId, long courseGropupCreatedId,
		java.util.List<com.liferay.portal.model.User> _students)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .avgResultOnlyStudents(actId, companyId,
			courseGropupCreatedId, _students);
	}

	/**
	* Devuelve la media de resultado de usuarios para una actividad
	*
	* @param actId id de la actividad
	* @param userExcludedIds id de la company de la actividad
	* @return media de resultado de usuarios para una actividad
	*/
	public static double avgResultByActIdUserExcludedIds(long actId,
		long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .avgResultByActIdUserExcludedIds(actId, userExcludedIds);
	}

	/**
	* Devuelve la media de resultado de usuarios para una actividad, esta funciÃƒÂ³n estÃƒÂ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar avgTriesByActIdUserExcludedIds
	*
	* @param actId id de la actividad
	* @param userIds ids de los usuarios filtrados
	* @return media de resultado de usuarios para una actividad
	* @throws SystemException
	*/
	public static double avgResultByActIdUserIds(long actId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().avgResultByActIdUserIds(actId, userIds);
	}

	public static long countStarted(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countStarted(actId);
	}

	/**
	* Devuelve el nÃ¯Â¿Â½mero de estudiantes que han comenzado una actividad, si ya se tiene la lista de usuarios excluidos (profesores y editores)
	* llamar al mÃ¯Â¿Â½todo countStudentsByActIdUserExcludedIdsStarted directamente
	*
	* @param actId id de la actividad
	* @param companyId id de la company de la actividad
	* @param courseGroupCreatedId id del group del curso
	* @return nÃ¯Â¿Â½mero de estudiantes que han comenzado una actividad
	*/
	public static long countStartedOnlyStudents(long actId, long companyId,
		long courseGroupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStartedOnlyStudents(actId, companyId,
			courseGroupCreatedId);
	}

	/**
	* @deprecated SE RECOMIENDA NO USAR ESTE MÃ¯Â¿Â½TODO, SE RECOMIENDA USAR: countStartedOnlyStudents(long actId, long companyId, long courseGroupCreatedId)
	o countStudentsByActIdUserExcludedIdsStarted(actId, userExcludedIds)
	* @param actId id de la actividad
	* @param companyId id de la company de la actividad
	* @param courseGroupCreatedId id del group del curso
	* @param _students lista de estudiantes, si viene vacÃ¯Â¿Â½a se calculan dentro
	* @return nÃ¯Â¿Â½mero de estudiantes que han comenzado la actividad
	*/
	public static long countStartedOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId,
		java.util.List<com.liferay.portal.model.User> _students)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStartedOnlyStudents(actId, companyId,
			courseGropupCreatedId, _students);
	}

	/**
	* Devuelve el nÃ¯Â¿Â½mero de estudiantes que han finalizado una actividad, si ya se tiene la lista de usuarios excluidos (profesores y editores)
	* llamar al mÃ¯Â¿Â½todo countStudentsByActIdUserExcludedIdsFinished directamente
	*
	* @param actId id de la actividad
	* @param companyId id de la company de la actividad
	* @param courseGroupCreatedId id del group del curso
	* @return nÃ¯Â¿Â½mero de estudiantes que han finalizado una actividad
	*/
	public static long countFinishedOnlyStudents(long actId, long companyId,
		long courseGroupCreatedId) {
		return getService()
				   .countFinishedOnlyStudents(actId, companyId,
			courseGroupCreatedId);
	}

	/**
	* @deprecated SE RECOMIENDA NO USAR ESTE MÃ¯Â¿Â½TODO, SE RECOMIENDA USAR: countFinishedOnlyStudents(long actId, long companyId, long courseGroupCreatedId)
	o countStudentsByActIdUserExcludedIdsFinished(actId, userExcludedIds)
	* @param actId id de la actividad
	* @param companyId id de la company de la actividad
	* @param courseGroupCreatedId id del group del curso
	* @param _students lista de estudiantes, si viene vacÃ¯Â¿Â½a se calculan dentro
	* @return nÃ¯Â¿Â½mero de estudiantes que han finalizado la actividad
	*/
	public static long countFinishedOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId,
		java.util.List<com.liferay.portal.model.User> _students) {
		return getService()
				   .countFinishedOnlyStudents(actId, companyId,
			courseGropupCreatedId, _students);
	}

	public static double triesPerUser(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().triesPerUser(actId);
	}

	/**
	* Devuelve la media de intentos por usuario para una actividad, si ya se tiene la lista de usuarios excluidos (profesores y editores)
	* llamar al mÃƒÂ©todo avgTriesByActIdUserExcludedIds directamente
	*
	* @param actId id de la actividad
	* @param companyId id de la company de la actividad
	* @param courseGroupCreatedId id del group del curso
	* @return media de intentos por usuario para una actividad
	*/
	public static double triesPerUserOnlyStudents(long actId, long companyId,
		long courseGroupCreatedId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .triesPerUserOnlyStudents(actId, companyId,
			courseGroupCreatedId);
	}

	/**
	* @deprecated SE RECOMIENDA NO USAR ESTE MÃƒâ€°TODO, SE RECOMIENDA USAR: triesPerUserOnlyStudents(long actId, long companyId, long courseGroupCreatedId)
	o avgTriesByActIdUserExcludedIds(long actId, long[] userExcludedIds)
	* @param actId id de la actividad
	* @param companyId id de la company de la actividad
	* @param courseGroupCreatedId id del group del curso
	* @param _students lista de estudiantes, si viene vacÃƒÂ­a se calculan dentro
	* @return media de intentos por usuario para una actividad
	*/
	public static double triesPerUserOnlyStudents(long actId, long companyId,
		long courseGropupCreatedId,
		java.util.List<com.liferay.portal.model.User> _students)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .triesPerUserOnlyStudents(actId, companyId,
			courseGropupCreatedId, _students);
	}

	/**
	* Devuelve la media de intentos por usuario para una actividad
	*
	* @param actId id de la actividad
	* @param userExcludedIds id de la company de la actividad
	* @return media de intentos por usuario para una actividad
	*/
	public static double avgTriesByActIdUserExcludedIds(long actId,
		long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .avgTriesByActIdUserExcludedIds(actId, userExcludedIds);
	}

	/**
	* Devuelve la media de intentos por usuario para una actividad, esta funciÃƒÂ³n estÃƒÂ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar avgTriesByActIdUserExcludedIds
	*
	* @param actId id de la actividad
	* @param userIds ids de los usuarios filtrados
	* @return media de intentos por usuario para una actividad
	* @throws SystemException
	*/
	public static double avgTriesByActIdUserIds(long actId, long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().avgTriesByActIdUserIds(actId, userIds);
	}

	public static com.liferay.lms.model.LearningActivityResult getByActIdAndUserId(
		long actId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getByActIdAndUserId(actId, userId);
	}

	public static java.util.Date getLastEndDateByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLastEndDateByUserId(userId);
	}

	public static java.util.List<com.liferay.lms.model.LearningActivityResult> getByActId(
		long actId) {
		return getService().getByActId(actId);
	}

	public static java.util.List<com.liferay.lms.model.LearningActivityResult> getByGroupIdUserId(
		long groupId, long userId) {
		return getService().getByGroupIdUserId(groupId, userId);
	}

	public static java.util.List<com.liferay.lms.model.LearningActivityResult> getMandatoryByGroupIdUserId(
		long groupId, long userId) {
		return getService().getMandatoryByGroupIdUserId(groupId, userId);
	}

	public static java.util.List<com.liferay.lms.model.LearningActivityResult> getByModuleIdUserId(
		long moduleId, long userId) {
		return getService().getByModuleIdUserId(moduleId, userId);
	}

	public static java.util.List<com.liferay.lms.model.LearningActivityResult> getByModuleIdUserIdPassed(
		long moduleId, long userId) {
		return getService().getByModuleIdUserIdPassed(moduleId, userId);
	}

	public static java.util.List<com.liferay.lms.model.LearningActivityResult> getMandatoryByModuleIdUserIdPassed(
		long moduleId, long userId) {
		return getService().getMandatoryByModuleIdUserIdPassed(moduleId, userId);
	}

	public static java.util.List<com.liferay.lms.model.LearningActivityResult> getByUserId(
		long userId) {
		return getService().getByUserId(userId);
	}

	public static int countMandatoryByModuleIdUserIdPassed(long moduleId,
		long userId) {
		return getService()
				   .countMandatoryByModuleIdUserIdPassed(moduleId, userId);
	}

	public static java.lang.String translateResult(java.util.Locale locale,
		double result, long groupId) {
		return getService().translateResult(locale, result, groupId);
	}

	public static java.lang.String getCalificationTypeSuffix(
		java.util.Locale locale, double result, long groupId) {
		return getService().getCalificationTypeSuffix(locale, result, groupId);
	}

	/**
	* Cuenta los estudiantes que han iniciado la actividad: solo llamar si se tiene la lista de usuarios excluidos
	*
	* @param actId id de la actividad
	* @param userExcludedIds ids de usuarios excluidos (profesores y editores)
	* @return nÃƒÂºmero de estudiantes que han comenzado la actividad
	* @throws SystemException
	*/
	public static int countStudentsByActIdUserExcludedIdsStarted(long actId,
		long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStudentsByActIdUserExcludedIdsStarted(actId,
			userExcludedIds);
	}

	/**
	* Cuenta los estudiantes que han finalizado la actividad: solo llamar si se tiene la lista de usuarios excluidos
	*
	* @param actId id de la actividad
	* @param userExcludedIds ids de usuarios excluidos (profesores y editores)
	* @return nÃƒÂºmero de estudiantes que han finalizado la actividad
	* @throws SystemException
	*/
	public static int countStudentsByActIdUserExcludedIdsFinished(long actId,
		long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStudentsByActIdUserExcludedIdsFinished(actId,
			userExcludedIds);
	}

	/**
	* Cuenta los estudiantes que han finalizado la actividad y la han aprobado
	*
	* @param actId id de la actividad
	* @param userExcludedIds ids de usuarios excluidos (profesores y editores)
	* @return nÃƒÂºmero de estudiantes que han finalizado y aprobado la actividad
	* @throws SystemException
	*/
	public static int countStudentsByActIdUserExcludedIdsPassed(long actId,
		long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStudentsByActIdUserExcludedIdsPassed(actId,
			userExcludedIds);
	}

	/**
	* Cuenta los estudiantes que han finalizado la actividad y la han suspendido
	*
	* @param actId id de la actividad
	* @param userExcludedIds ids de usuarios excluidos (profesores y editores)
	* @return nÃƒÂºmero de estudiantes que han finalizado y suspendido la actividad
	* @throws SystemException
	*/
	public static int countStudentsByActIdUserExcludedIdsFailed(long actId,
		long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStudentsByActIdUserExcludedIdsFailed(actId,
			userExcludedIds);
	}

	/**
	* Cuenta los estudiantes que han iniciado la actividad, esta funciÃƒÂ³n estÃƒÂ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByActIdUserExcludedIdsStarted
	*
	* @param actId id de la actividad
	* @param userIds ids de los usuarios filtrados
	* @return nÃƒÂºmero de estudiantes que han comenzado la actividad
	* @throws SystemException
	*/
	public static int countStudentsByActIdUserIdsStarted(long actId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countStudentsByActIdUserIdsStarted(actId, userIds);
	}

	/**
	* Cuenta los estudiantes que han finalizado la actividad, esta funciÃƒÂ³n estÃƒÂ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByActIdUserExcludedIdsFinished
	*
	* @param actId id de la actividad
	* @param userIds ids de los usuarios filtrados
	* @return nÃƒÂºmero de estudiantes que han finalizado la actividad
	* @throws SystemException
	*/
	public static int countStudentsByActIdUserIdsFinished(long actId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countStudentsByActIdUserIdsFinished(actId, userIds);
	}

	/**
	* Cuenta los estudiantes que han finalizado la actividad y la han aprobado, esta funciÃƒÂ³n estÃƒÂ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByActIdUserExcludedIdsPassed
	*
	* @param actId id de la actividad
	* @param userIds ids de los usuarios filtrados
	* @return nÃƒÂºmero de estudiantes que han finalizado y aprobado la actividad
	* @throws SystemException
	*/
	public static int countStudentsByActIdUserIdsPassed(long actId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countStudentsByActIdUserIdsPassed(actId, userIds);
	}

	/**
	* Cuenta los estudiantes que han finalizado la actividad y la han suspendido, esta funciÃƒÂ³n estÃƒÂ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByActIdUserExcludedIdsFailed
	*
	* @param actId id de la actividad
	* @param userIds ids de los usuarios filtrados
	* @return nÃƒÂºmero de estudiantes que han finalizado y suspendido la actividad
	* @throws SystemException
	*/
	public static int countStudentsByActIdUserIdsFailed(long actId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countStudentsByActIdUserIdsFailed(actId, userIds);
	}

	public static java.util.Date getLastEndDateByUserIdCourseId(long userId,
		long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLastEndDateByUserIdCourseId(userId, courseId);
	}

	public static void clearService() {
		_service = null;
	}

	public static LearningActivityResultLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					LearningActivityResultLocalService.class.getName());

			if (invokableLocalService instanceof LearningActivityResultLocalService) {
				_service = (LearningActivityResultLocalService)invokableLocalService;
			}
			else {
				_service = new LearningActivityResultLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(LearningActivityResultLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(LearningActivityResultLocalService service) {
	}

	private static LearningActivityResultLocalService _service;
}