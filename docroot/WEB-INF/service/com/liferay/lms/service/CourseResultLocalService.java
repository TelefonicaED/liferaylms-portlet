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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.InvokableLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * The interface for the course result local service.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see CourseResultLocalServiceUtil
 * @see com.liferay.lms.service.base.CourseResultLocalServiceBaseImpl
 * @see com.liferay.lms.service.impl.CourseResultLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CourseResultLocalService extends BaseLocalService,
	InvokableLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CourseResultLocalServiceUtil} to access the course result local service. Add custom service methods to {@link com.liferay.lms.service.impl.CourseResultLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the course result to the database. Also notifies the appropriate model listeners.
	*
	* @param courseResult the course result
	* @return the course result that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult addCourseResult(
		com.liferay.lms.model.CourseResult courseResult)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new course result with the primary key. Does not add the course result to the database.
	*
	* @param crId the primary key for the new course result
	* @return the new course result
	*/
	public com.liferay.lms.model.CourseResult createCourseResult(long crId);

	/**
	* Deletes the course result with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param crId the primary key of the course result
	* @return the course result that was removed
	* @throws PortalException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult deleteCourseResult(long crId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the course result from the database. Also notifies the appropriate model listeners.
	*
	* @param courseResult the course result
	* @return the course result that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult deleteCourseResult(
		com.liferay.lms.model.CourseResult courseResult)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

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
		throws com.liferay.portal.kernel.exception.SystemException;

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
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.lms.model.CourseResult fetchCourseResult(long crId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the course result with the primary key.
	*
	* @param crId the primary key of the course result
	* @return the course result
	* @throws PortalException if a course result with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.lms.model.CourseResult getCourseResult(long crId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.lms.model.CourseResult> getCourseResults(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of course results.
	*
	* @return the number of course results
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCourseResultsCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the course result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param courseResult the course result
	* @return the course result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult updateCourseResult(
		com.liferay.lms.model.CourseResult courseResult)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the course result in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param courseResult the course result
	* @param merge whether to merge the course result with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the course result that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.CourseResult updateCourseResult(
		com.liferay.lms.model.CourseResult courseResult, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.lms.model.CourseResult> getByUserId(
		long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.lms.model.CourseResult getByUserAndCourse(
		long courseId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public long countByCourseId(long courseId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	public long countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Pide la lista de profesores y editores para obtener los usuarios excluidos y llama a countStudentsByCourseIdUserIds
	*
	* @param course curso del que quiero los usuarios
	* @param passed si quiero los aprobados
	* @return numero de usuarios que han pasado el curso si passed es a true
	* @throws SystemException
	*/
	public long countStudentsByCourseId(com.liferay.lms.model.Course course,
		boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @deprecated ESTE SE VA A DEPRECAR PORQUE NO ES RECOMENDABLE USAR UNA LISTA GRANDE USUARIOS, ES MEJOR PASAR LA DE LOS EDITORES Y PROFESORES
	POR LO QUE SE RECOMIENDA LLAMAR A countStudentsByCourseId o countStudentsByCourseIdUserIds
	* @param course curso del que quiero los usuarios
	* @param students lista de estudiantes del curso, si no se pasa la lista se obtiene en la funciÃ³n
	* @param passed si quiero los aprobados
	* @return numero de usuarios que han pasado el curso si passed es a true
	* @throws SystemException
	*/
	public long countStudentsByCourseId(com.liferay.lms.model.Course course,
		java.util.List<com.liferay.portal.model.User> students, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Obtiene los estudiantes de un curso en funciÃ³n del passed
	* Solo usar si ya tengo la lista de usuarios dada
	*
	* @param courseId id del curso del que quiero los usuarios
	* @param userExcludedIds ids de los usuarios que se excluyen para no contarlos (profesores y editores)
	* @param passed si quiero los aprobados
	* @return numero de usuarios que han pasado el curso si passed es a true
	* @throws SystemException
	*/
	public int countStudentsByCourseIdUserExcludedIds(long courseId,
		long[] userExcludedIds, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Cuenta los estudiantes que han iniciado el curso, si no se tiene ya la lista de usuarios excluidos (profesores y editores) llamar
	* a este mÃ©todo, si se tiene la lista llamar a countStudentsByCourseIdUserIdsStarted
	*
	* @param course curso del que quiero los usuarios
	* @return numero de usuarios que han iniciado el curso
	* @throws SystemException
	*/
	public long countStudentsByCourseId(com.liferay.lms.model.Course course)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @deprecated ESTE LO VAMOS A DEPRECAR, HABRÃ�A QUE LLAMAR AL MÃ‰TODO countStudentsByCourseId o countStudentsByCourseIdUserIdsStarted
	Cuenta los estudiantes que han iniciado el curso
	* @param course curso del que quiero los usuarios
	* @param students lista de estudiantes, si se pasa a null se obtienen dentro de la funciÃ³n
	* @return numero de usuarios que han iniciado el curso
	* @throws SystemException
	*/
	public long countStudentsByCourseId(com.liferay.lms.model.Course course,
		java.util.List<com.liferay.portal.model.User> students)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Devuelve el nÃºmero de estudiantes que han comenzado un curso
	*
	* @param courseId id del curso del que quiero contar estudiantes
	* @param userExcludedIds ids de los usuarios excluidos (profesores y editores)
	* @return nÃºmero de usuarios que han iniciado el curso
	* @throws SystemException
	*/
	public int countStudentsByCourseIdUserExcludedIdsStarted(long courseId,
		long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Cuenta los estudiantes que han finalizado el curso
	*
	* @param courseId id del curso del que quiero contar los estudiantes
	* @param userExcludedIds de los usuarios excluidos (profesores y editores)
	* @return nÃºmero de usuarios que han finalizado el curso
	* @throws SystemException
	*/
	public int countStudentsByCourseIdUserExcludedIdsFinished(long courseId,
		long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Cuenta los estudiantes que han finalizado el curso y lo hayan aprobado
	*
	* @param courseId id del curso del que quiero contar los estudiantes
	* @param userExcludedIds de losusuarios excluidos(profesores y editores)
	* @return nÃºmero de estudiantes que han aprobado el curso
	* @throws SystemException
	*/
	public int countStudentsByCourseIdUserExcludedIdsPassed(long courseId,
		long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Cuenta los estudiantes que han finalizado el curso y lo hayan suspendido
	*
	* @param courseId id del curso del que quiero contar los estudiantes
	* @param userExcludedIds de los usuarios excluidos (profesores y editores)
	* @return nÃºmero de estudiantes que han finalizado el curso y lo han suspendido
	* @throws SystemException
	*/
	public int countStudentsByCourseIdUserExcludedIdsFailed(long courseId,
		long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Devuelve el nÃºmero de estudiantes que han comenzado un curso, esta funciÃ³n estÃ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsStarted
	*
	* @param courseId id del curso del que quiero contar estudiantes
	* @param userIds ids de los usuarios filtrados
	* @return nÃºmero de estudiantes que han iniciado el curso
	* @throws SystemException
	*/
	public int countStudentsByCourseIdUserIdsStarted(long courseId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Cuenta los estudiantes que han finalizado el curso, esta funciÃ³n estÃ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsFinished
	*
	* @param courseId id del curso del que quiero contar los estudiantes
	* @param userIds ids de los usuarios filtrados
	* @return nÃºmero de usuarios que han finalizado el curso
	* @throws SystemException
	*/
	public int countStudentsByCourseIdUserIdsFinished(long courseId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Cuenta los estudiantes que han finalizado el curso y lo hayan aprobado, esta funciÃ³n estÃ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsPassed
	*
	* @param courseId id del curso del que quiero contar los estudiantes
	* @param userIds ids de los usuarios filtrados
	* @return nÃºmero de estudiantes que han aprobado el curso
	* @throws SystemException
	*/
	public int countStudentsByCourseIdUserIdsPassed(long courseId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Cuenta los estudiantes que han finalizado el curso y lo hayan suspendido, esta funciÃ³n estÃ¡ pensada para pasar una lista de estudiantes filtrada
	* (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsFailed
	*
	* @param courseId id del curso del que quiero contar los estudiantes
	* @param userIds ids de los usuarios filtrados
	* @return nÃºmero de estudiantes que han finalizado el curso y lo han suspendido
	* @throws SystemException
	*/
	public int countStudentsByCourseIdUserIdsFailed(long courseId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.Double avgResult(long courseId, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.Double avgResult(long courseId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Devuelve la media de resultado de usuarios para un curso, si ya se tiene la lista de usuarios excluidos (profesores y editores)
	* llamar al mÃ©todo avgResultByCourseIdUserExcludedIds directamente
	*
	* @param course curso
	* @param passed si queremos de los aprobados o suspendos
	* @return media de resultado de usuarios para una actividad
	*/
	public java.lang.Double avgStudentsResult(
		com.liferay.lms.model.Course course, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* @deprecated SE RECOMIENDA NO USAR ESTE MÃ‰TODO, SE RECOMIENDA USAR: avgStudentsResult(Course course, boolean passed)
	o avgResultByCourseIdUserExcludedIds(long courseId, passed, long[] userExcludedIds)
	* @param course curso
	* @param _students lista de estudiantes, si viene vacÃ­a se calculan dentro
	* @param passed si queremos de los aprobados o suspendos
	* @return media de resultado de usuarios para un curso
	*/
	public java.lang.Double avgStudentsResult(
		com.liferay.lms.model.Course course,
		java.util.List<com.liferay.portal.model.User> _students, boolean passed)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Devuelve la media de resultado de usuarios para un curso
	*
	* @param courseId id del curso
	* @param passed si queremos los aprobados o suspensos
	* @param userExcludedIds id de la company de la actividad
	* @return media de resultado de usuarios para una actividad
	*/
	public double avgResultByCourseIdUserExcludedIds(long courseId,
		boolean passed, long[] userExcludedIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.CourseResult create(long courseId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.CourseResult create(long courseId,
		long userId, java.util.Date allowStartDate,
		java.util.Date allowFinishDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void update(com.liferay.lms.model.CourseResult cresult)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void update(com.liferay.lms.model.ModuleResult mresult)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.lms.model.CourseResult getCourseResultByCourseAndUser(
		long courseId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String translateResult(java.util.Locale locale,
		double result, long groupId);

	public void softInitializeByGroupIdAndUserId(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;
}