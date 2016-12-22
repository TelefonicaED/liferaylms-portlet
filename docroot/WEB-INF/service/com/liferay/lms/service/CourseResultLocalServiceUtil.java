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
 * The utility for the course result local service. This utility wraps {@link com.liferay.lms.service.impl.CourseResultLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see CourseResultLocalService
 * @see com.liferay.lms.service.base.CourseResultLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.CourseResultLocalServiceImpl
 * @generated
 */
public class CourseResultLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.CourseResultLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the course result to the database. Also notifies the appropriate model listeners.
	*
	* @param courseResult the course result
	* @return the course result that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult addCourseResult(
		com.liferay.lms.model.CourseResult courseResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addCourseResult(courseResult);
	}

	/**
	* Creates a new course result with the primary key. Does not add the course result to the database.
	*
	* @param crId the primary key for the new course result
	* @return the new course result
	*/
	public static com.liferay.lms.model.CourseResult createCourseResult(
		long crId) {
		return getService().createCourseResult(crId);
	}

	/**
	* Deletes the course result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param crId the primary key of the course result
	* @return the course result that was removed
	* @throws PortalException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult deleteCourseResult(
		long crId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteCourseResult(crId);
	}

	/**
	* Deletes the course result from the database. Also notifies the appropriate model listeners.
	*
	* @param courseResult the course result
	* @return the course result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult deleteCourseResult(
		com.liferay.lms.model.CourseResult courseResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteCourseResult(courseResult);
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

	public static com.liferay.lms.model.CourseResult fetchCourseResult(
		long crId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchCourseResult(crId);
	}

	/**
	* Returns the course result with the primary key.
	*
	* @param crId the primary key of the course result
	* @return the course result
	* @throws PortalException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult getCourseResult(long crId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCourseResult(crId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the course results.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of course results
	* @param end the upper bound of the range of course results (not inclusive)
	* @return the range of course results
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.model.CourseResult> getCourseResults(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCourseResults(start, end);
	}

	/**
	* Returns the number of course results.
	*
	* @return the number of course results
	* @throws SystemException if a system exception occurred
	*/
	public static int getCourseResultsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCourseResultsCount();
	}

	/**
	* Updates the course result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param courseResult the course result
	* @return the course result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult updateCourseResult(
		com.liferay.lms.model.CourseResult courseResult)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCourseResult(courseResult);
	}

	/**
	* Updates the course result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param courseResult the course result
	* @param merge whether to merge the course result with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the course result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.model.CourseResult updateCourseResult(
		com.liferay.lms.model.CourseResult courseResult, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCourseResult(courseResult, merge);
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

	public static java.util.List<com.liferay.lms.model.CourseResult> getByUserId(
		long userId) {
		return getService().getByUserId(userId);
	}

	public static com.liferay.lms.model.CourseResult getByUserAndCourse(
		long courseId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getByUserAndCourse(courseId, userId);
	}

	public static long countByCourseId(long courseId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countByCourseId(courseId, passed);
	}

	public static long countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countByUserId(userId);
	}

	/**
	* Pide la lista de profesores y editores para obtener los usuarios excluidos y llama a countStudentsByCourseIdUserIds
	*
	* @param course curso del que quiero los usuarios
	* @param passed si quiero los aprobados
	* @return numero de usuarios que han pasado el curso si passed es a true
	* @throws SystemException
	*/
	public static long countStudentsByCourseId(
		com.liferay.lms.model.Course course, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countStudentsByCourseId(course, passed);
	}

	/**
	* @deprecated ESTE SE VA A DEPRECAR PORQUE NO ES RECOMENDABLE USAR UNA LISTA GRANDE USUARIOS, ES MEJOR PASAR LA DE LOS EDITORES Y PROFESORES
	POR LO QUE SE RECOMIENDA LLAMAR A countStudentsByCourseId o countStudentsByCourseIdUserIds
	* @param course curso del que quiero los usuarios
	* @param students lista de estudiantes del curso, si no se pasa la lista se obtiene en la funciÃƒÂ³n
	* @param passed si quiero los aprobados
	* @return numero de usuarios que han pasado el curso si passed es a true
	* @throws SystemException
	*/
	public static long countStudentsByCourseId(
		com.liferay.lms.model.Course course,
		java.util.List<com.liferay.portal.model.User> students, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countStudentsByCourseId(course, students, passed);
	}

	/**
	* Obtiene los estudiantes de un curso en funciÃƒÂ³n del passed
	* Solo usar si ya tengo la lista de usuarios dada
	*
	* @param courseId id del curso del que quiero los usuarios
	* @param userExcludedIds ids de los usuarios que se excluyen para no contarlos (profesores y editores)
	* @param passed si quiero los aprobados
	* @return numero de usuarios que han pasado el curso si passed es a true
	* @throws SystemException
	*/
	public static int countStudentsByCourseIdUserExcludedIds(long courseId,
		long[] userExcludedIds, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStudentsByCourseIdUserExcludedIds(courseId,
			userExcludedIds, passed);
	}

	/**
	* Cuenta los estudiantes que han iniciado el curso, si no se tiene ya la lista de usuarios excluidos (profesores y editores) llamar
	* a este mÃƒÂ©todo, si se tiene la lista llamar a countStudentsByCourseIdUserIdsStarted
	*
	* @param course curso del que quiero los usuarios
	* @return numero de usuarios que han iniciado el curso
	* @throws SystemException
	*/
	public static long countStudentsByCourseId(
		com.liferay.lms.model.Course course)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countStudentsByCourseId(course);
	}

	/**
	* @deprecated ESTE LO VAMOS A DEPRECAR, HABRÃƒï¿½A QUE LLAMAR AL MÃƒâ€°TODO countStudentsByCourseId o countStudentsByCourseIdUserIdsStarted
	Cuenta los estudiantes que han iniciado el curso
	* @param course curso del que quiero los usuarios
	* @param students lista de estudiantes, si se pasa a null se obtienen dentro de la funciÃƒÂ³n
	* @return numero de usuarios que han iniciado el curso
	* @throws SystemException
	*/
	public static long countStudentsByCourseId(
		com.liferay.lms.model.Course course,
		java.util.List<com.liferay.portal.model.User> students)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().countStudentsByCourseId(course, students);
	}

	/**
	* Devuelve el nÃƒÂºmero de estudiantes que han comenzado un curso
	*
	* @param courseId id del curso del que quiero contar estudiantes
	* @param userExcludedIds ids de los usuarios excluidos (profesores y editores)
	* @return nÃƒÂºmero de usuarios que han iniciado el curso
	* @throws SystemException
	*/
	public static int countStudentsByCourseIdUserExcludedIdsStarted(
		long courseId, long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStudentsByCourseIdUserExcludedIdsStarted(courseId,
			userExcludedIds);
	}

	/**
	* Cuenta los estudiantes que han finalizado el curso
	*
	* @param courseId id del curso del que quiero contar los estudiantes
	* @param userExcludedIds de los usuarios excluidos (profesores y editores)
	* @return nÃƒÂºmero de usuarios que han finalizado el curso
	* @throws SystemException
	*/
	public static int countStudentsByCourseIdUserExcludedIdsFinished(
		long courseId, long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStudentsByCourseIdUserExcludedIdsFinished(courseId,
			userExcludedIds);
	}

	/**
	* Cuenta los estudiantes que han finalizado el curso y lo hayan aprobado
	*
	* @param courseId id del curso del que quiero contar los estudiantes
	* @param userExcludedIds de losusuarios excluidos(profesores y editores)
	* @return nÃƒÂºmero de estudiantes que han aprobado el curso
	* @throws SystemException
	*/
	public static int countStudentsByCourseIdUserExcludedIdsPassed(
		long courseId, long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStudentsByCourseIdUserExcludedIdsPassed(courseId,
			userExcludedIds);
	}

	/**
	* Cuenta los estudiantes que han finalizado el curso y lo hayan suspendido
	*
	* @param courseId id del curso del que quiero contar los estudiantes
	* @param userExcludedIds de los usuarios excluidos (profesores y editores)
	* @return nÃƒÂºmero de estudiantes que han finalizado el curso y lo han suspendido
	* @throws SystemException
	*/
	public static int countStudentsByCourseIdUserExcludedIdsFailed(
		long courseId, long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStudentsByCourseIdUserExcludedIdsFailed(courseId,
			userExcludedIds);
	}

	/**
	* Devuelve el nÃƒÂºmero de estudiantes que han comenzado un curso, esta funciÃƒÂ³n estÃƒÂ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsStarted
	*
	* @param courseId id del curso del que quiero contar estudiantes
	* @param userIds ids de los usuarios filtrados
	* @return nÃƒÂºmero de estudiantes que han iniciado el curso
	* @throws SystemException
	*/
	public static int countStudentsByCourseIdUserIdsStarted(long courseId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStudentsByCourseIdUserIdsStarted(courseId, userIds);
	}

	/**
	* Cuenta los estudiantes que han finalizado el curso, esta funciÃƒÂ³n estÃƒÂ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsFinished
	*
	* @param courseId id del curso del que quiero contar los estudiantes
	* @param userIds ids de los usuarios filtrados
	* @return nÃƒÂºmero de usuarios que han finalizado el curso
	* @throws SystemException
	*/
	public static int countStudentsByCourseIdUserIdsFinished(long courseId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStudentsByCourseIdUserIdsFinished(courseId, userIds);
	}

	/**
	* Cuenta los estudiantes que han finalizado el curso y lo hayan aprobado, esta funciÃƒÂ³n estÃƒÂ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsPassed
	*
	* @param courseId id del curso del que quiero contar los estudiantes
	* @param userIds ids de los usuarios filtrados
	* @return nÃƒÂºmero de estudiantes que han aprobado el curso
	* @throws SystemException
	*/
	public static int countStudentsByCourseIdUserIdsPassed(long courseId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStudentsByCourseIdUserIdsPassed(courseId, userIds);
	}

	/**
	* Cuenta los estudiantes que han finalizado el curso y lo hayan suspendido, esta funciÃƒÂ³n estÃƒÂ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsFailed
	*
	* @param courseId id del curso del que quiero contar los estudiantes
	* @param userIds ids de los usuarios filtrados
	* @return nÃƒÂºmero de estudiantes que han finalizado el curso y lo han suspendido
	* @throws SystemException
	*/
	public static int countStudentsByCourseIdUserIdsFailed(long courseId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .countStudentsByCourseIdUserIdsFailed(courseId, userIds);
	}

	public static java.lang.Double avgResult(long courseId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().avgResult(courseId, passed);
	}

	public static java.lang.Double avgResult(long courseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().avgResult(courseId);
	}

	/**
	* Devuelve la media de resultado de usuarios para un curso, si ya se tiene la lista de usuarios excluidos (profesores y editores)
	* llamar al mÃƒÂ©todo avgResultByCourseIdUserExcludedIds directamente
	*
	* @param course curso
	* @param passed si queremos de los aprobados o suspendos
	* @return media de resultado de usuarios para una actividad
	*/
	public static java.lang.Double avgStudentsResult(
		com.liferay.lms.model.Course course, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().avgStudentsResult(course, passed);
	}

	/**
	* @deprecated SE RECOMIENDA NO USAR ESTE MÃƒâ€°TODO, SE RECOMIENDA USAR: avgStudentsResult(Course course, boolean passed)
	o avgResultByCourseIdUserExcludedIds(long courseId, passed, long[] userExcludedIds)
	* @param course curso
	* @param _students lista de estudiantes, si viene vacÃƒÂ­a se calculan dentro
	* @param passed si queremos de los aprobados o suspendos
	* @return media de resultado de usuarios para un curso
	*/
	public static java.lang.Double avgStudentsResult(
		com.liferay.lms.model.Course course,
		java.util.List<com.liferay.portal.model.User> _students, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().avgStudentsResult(course, _students, passed);
	}

	/**
	* Devuelve la media de resultado de usuarios para un curso
	*
	* @param courseId id del curso
	* @param passed si queremos los aprobados o suspensos
	* @param userExcludedIds id de la company de la actividad
	* @return media de resultado de usuarios para una actividad
	*/
	public static double avgResultByCourseIdUserExcludedIds(long courseId,
		boolean passed, long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .avgResultByCourseIdUserExcludedIds(courseId, passed,
			userExcludedIds);
	}

	public static com.liferay.lms.model.CourseResult create(long courseId,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().create(courseId, userId);
	}

	public static com.liferay.lms.model.CourseResult create(long courseId,
		long userId, java.util.Date allowStartDate,
		java.util.Date allowFinishDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .create(courseId, userId, allowStartDate, allowFinishDate);
	}

	public static void update(com.liferay.lms.model.CourseResult cresult)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().update(cresult);
	}

	public static void update(com.liferay.lms.model.ModuleResult mresult)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().update(mresult);
	}

	public static com.liferay.lms.model.CourseResult getCourseResultByCourseAndUser(
		long courseId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCourseResultByCourseAndUser(courseId, userId);
	}

	public static java.lang.String translateResult(java.util.Locale locale,
		double result, long groupId) {
		return getService().translateResult(locale, result, groupId);
	}

	public static void softInitializeByGroupIdAndUserId(long groupId,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		getService().softInitializeByGroupIdAndUserId(groupId, userId);
	}

	public static void clearService() {
		_service = null;
	}

	public static CourseResultLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					CourseResultLocalService.class.getName());

			if (invokableLocalService instanceof CourseResultLocalService) {
				_service = (CourseResultLocalService)invokableLocalService;
			}
			else {
				_service = new CourseResultLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(CourseResultLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(CourseResultLocalService service) {
	}

	private static CourseResultLocalService _service;
}