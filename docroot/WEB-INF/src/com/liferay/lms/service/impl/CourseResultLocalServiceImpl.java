/**
 * 2012 TELEFONICA LEARNING SERVICES. All rights reserved.
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

package com.liferay.lms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.course.diploma.CourseDiploma;
import com.liferay.lms.course.diploma.CourseDiplomaRegistry;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseCompetence;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.base.CourseResultLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.CourseResultFinderUtil;
import com.liferay.lms.util.CourseParams;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;

/**
 * The implementation of the course result local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseResultLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseResultLocalServiceUtil} to access the course result local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CourseResultLocalServiceBaseImpl
 * @see com.liferay.lms.service.CourseResultLocalServiceUtil
 */
public class CourseResultLocalServiceImpl
	extends CourseResultLocalServiceBaseImpl {
	
	/**
	 * Crear un courseresult para el alumno cuando se inscribe
	 * @param userId identificador del usuario que está inscribiendo
	 * @param courseId identificador del curso
	 * @param studentId usuario que se inscribe en el curso
	 * @param serviceContext
	 * @return
	 * @throws SystemException 
	 * @throws PortalException 
	 */
	@Override
	public CourseResult addCourseResult(long userModifiedId, long courseId, long studentId) throws SystemException {
		return addCourseResult(userModifiedId, courseId, studentId, null, null);
	}
	
	/**
	 * Crear un courseresult para el alumno cuando se inscribe
	 * @param userId identificador del usuario que está inscribiendo
	 * @param courseId identificador del curso
	 * @param studentId usuario que se inscribe en el curso
	 * @param serviceContext
	 * @return
	 * @throws SystemException 
	 * @throws PortalException 
	 */
	@Override
	public CourseResult addCourseResult(long userModifiedId, long courseId, long studentId, Date allowStartDate, Date allowEndDate) throws SystemException {
		CourseResult courseResult = null;
		
		courseResult = courseResultPersistence.create(counterLocalService.increment(CourseResult.class.getName()));
		courseResult.setCourseId(courseId);
		courseResult.setUserId(studentId);
		courseResult.setResult(0);
		courseResult.setRegistrationDate(new Date());
		courseResult.setUnRegistrationDate(null);
		courseResult.setPassed(false);
		courseResult.setAllowStartDate(allowStartDate);
		courseResult.setAllowFinishDate(allowEndDate);
		try {
			Course course = courseLocalService.getCourse(courseId);
			courseResult.setCompanyId(course.getCompanyId());
		} catch (PortalException e) {
			e.printStackTrace();
		}
		courseResult.setUserModifiedId(userModifiedId);
		
		courseResultPersistence.update(courseResult, true);
		
		return courseResult;
	}
	
	private void associateCompetencesToUser(List<CourseCompetence> competences, long userId) throws SystemException{
		for(CourseCompetence cc: competences){
			UserCompetence uc = userCompetencePersistence.fetchByUserIdCompetenceId(userId, cc.getCompetenceId());
							
			if(uc==null){
				UserCompetence userCompetence=userCompetencePersistence.create(counterLocalService.increment(UserCompetence.class.getName()));
				userCompetence.setUserId(userId);
				userCompetence.setCompetenceId(cc.getCompetenceId());
				userCompetence.setCompDate(new Date());
				userCompetence.setCourseId(cc.getCourseId());
				userCompetencePersistence.update(userCompetence, false);
			}
			
		}	
	}
	
	public Double avgResult(long courseId) throws SystemException {
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(CourseResult.class, classLoader);
		Criterion criterion=PropertyFactoryUtil.forName("courseId").eq(courseId);
		dq.add(criterion);
		dq.setProjection(ProjectionFactoryUtil.avg("result"));
		return (Double)(learningActivityResultPersistence.findWithDynamicQuery(dq).get(0));
	}
	
	public Double avgResult(long courseId, boolean passed) throws SystemException{
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(CourseResult.class, classLoader);
		Criterion criterion=PropertyFactoryUtil.forName("courseId").eq(courseId);
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("passed").eq(passed);
		dq.add(criterion);
		dq.setProjection(ProjectionFactoryUtil.avg("result"));
		return (Double)(learningActivityResultPersistence.findWithDynamicQuery(dq).get(0));
	}
	
	/**
	 * Devuelve la media de resultado de usuarios para un curso
	 * @param courseId id del curso
	 * @param passed si queremos los aprobados o suspensos
	 * @param userExcludedIds id de la company de la actividad
	 * @return media de resultado de usuarios para una actividad
	 * @deprecated
	 */
	@Deprecated
	public double avgResultByCourseIdUserExcludedIds(long courseId, boolean passed, long[] userExcludedIds) throws SystemException{
		return CourseResultFinderUtil.avgResultByCourseIdStudents(courseId, passed);
	}
	
	/**
	 * Devuelve la media de resultado de usuarios para un curso
	 * @param courseId id del curso
	 * @param includeEditions si contar las notas de las ediciones también
	 * @param userExcludedIds id de la company de la actividad
	 * @return media de resultado de usuarios para una actividad
	 */
	public double avgResultByCourseIdStudents(long courseId, boolean includeEditions) throws SystemException{
		return CourseResultFinderUtil.avgResultByCourseIdStudents(courseId, includeEditions);
	}
	
	/**
	 * Devuelve la media de resultado de usuarios para un curso, si ya se tiene la lista de usuarios excluidos (profesores y editores)
	 * llamar al método avgResultByCourseIdUserExcludedIds directamente
	 * @param course curso
	 * @param passed si queremos de los aprobados o suspendos
	 * @return media de resultado de usuarios para una actividad
	 */
	public Double avgStudentsResult(Course course, boolean passed) throws SystemException{	 
		return CourseResultFinderUtil.avgResultByCourseIdStudents(course.getCourseId(), passed);
	}
	
	public long countByCourseId(long courseId) throws SystemException{
		return courseResultPersistence.countByCourseId(courseId);
	}
	
	public long countByCourseId(long courseId, boolean passed) throws SystemException{
		return courseResultPersistence.countByc(courseId, passed);
	}
	
	public long countByUserId(long userId) throws SystemException{
		return courseResultPersistence.countByUserId(userId);
	}
	
	public long countFinishedOnlyStudents(long courseId, long companyId, long courseGropupCreatedId, List<User> _students, long teamId){
		return CourseResultFinderUtil.countFinishedOnlyStudents(courseId, companyId, courseGropupCreatedId, _students, teamId);
	}
	
	/**
	 * Pide la lista de profesores y editores para obtener los usuarios excluidos y llama a countStudentsByCourseIdUserIds
	 * @param course curso del que quiero los usuarios
	 * @param passed si quiero los aprobados
	 * @return numero de usuarios que han pasado el curso si passed es a true
	 * @throws SystemException
	 */
	
	public long countStudentsByCourseId(Course course, boolean passed) throws SystemException{
		long[] userExcludedIds = CourseLocalServiceUtil.getTeachersAndEditorsIdsFromCourse(course);
		return countStudentsByCourseIdUserExcludedIds(course.getCourseId(), userExcludedIds, passed);
	}
	

	/**
	 * Obtiene los estudiantes de un curso en función del passed
	 * Solo usar si ya tengo la lista de usuarios dada
	 * @param courseId id del curso del que quiero los usuarios
	 * @param userExcludedIds ids de los usuarios que se excluyen para no contarlos (profesores y editores)
	 * @param passed si quiero los aprobados
	 * @return numero de usuarios que han pasado el curso si passed es a true
	 * @throws SystemException
	 */
	
	public int countStudentsByCourseIdUserExcludedIds(long courseId, long[] userExcludedIds, boolean passed) throws SystemException{

		if(userExcludedIds != null && userExcludedIds.length > 0){
		
			return courseResultPersistence.countByCourseIdPassedNotMultipleUserId(courseId, passed, userExcludedIds);

		}else{
			return courseResultPersistence.countByc(courseId, passed);
		}
	}
	
	/**
	 * Devuelve el número de estudiantes que han comenzado un curso
	 * @param courseId id del curso del que quiero contar estudiantes
	 * @param userExcludedIds ids de los usuarios excluidos (profesores y editores)
	 * @return número de usuarios que han iniciado el curso
	 * @throws SystemException
	 */
	public int countStudentsByCourseIdUserExcludedIdsStarted(long courseId, long[] userExcludedIds) throws SystemException{
		
		if(userExcludedIds != null && userExcludedIds.length > 0){
			return courseResultPersistence.countByCourseIdNotMultipleUserIdStarted(courseId, userExcludedIds);
		}else{
			return courseResultPersistence.countByCourseIdStarted(courseId);
		}
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado el curso
	 * @param courseId id del curso del que quiero contar los estudiantes
	 * @param userExcludedIds de los usuarios excluidos (profesores y editores)
	 * @return número de usuarios que han finalizado el curso
	 * @throws SystemException
	 */
	
	public int countStudentsByCourseIdUserExcludedIdsFinished(long courseId, long[] userExcludedIds) throws SystemException{
		
		if(userExcludedIds != null && userExcludedIds.length > 0){
			return courseResultPersistence.countByCourseIdNotMultipleUserIdFinished(courseId, null, userExcludedIds);

		}else{ 
			return courseResultPersistence.countByCourseIdFinished(courseId, null);
		}
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado el curso y lo hayan aprobado
	 * @param courseId id del curso del que quiero contar los estudiantes
	 * @param userExcludedIds de losusuarios excluidos(profesores y editores)
	 * @return número de estudiantes que han aprobado el curso
	 * @throws SystemException
	 */
	
	public int countStudentsByCourseIdUserExcludedIdsPassed(long courseId, long[] userExcludedIds) throws SystemException{
		
		if(userExcludedIds != null && userExcludedIds.length > 0){
			return courseResultPersistence.countByCourseIdPassedNotMultipleUserIdFinished(courseId, true, null, userExcludedIds);

		}else{
			return courseResultPersistence.countByCourseIdPassedFinished(courseId, true, null);
		}
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado el curso y lo hayan suspendido
	 * @param courseId id del curso del que quiero contar los estudiantes
	 * @param userExcludedIds de los usuarios excluidos (profesores y editores)
	 * @return número de estudiantes que han finalizado el curso y lo han suspendido
	 * @throws SystemException
	 */
	
	public int countStudentsByCourseIdUserExcludedIdsFailed(long courseId, long[] userExcludedIds) throws SystemException{
		
		if(userExcludedIds != null && userExcludedIds.length > 0){
			return courseResultPersistence.countByCourseIdPassedNotMultipleUserIdFinished(courseId, false, null, userExcludedIds);

		}else{
			return courseResultPersistence.countByCourseIdPassedFinished(courseId, false, null);
		}
	}
	
	/**
	 * Devuelve el número de estudiantes que han comenzado un curso, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsStarted
	 * @param courseId id del curso del que quiero contar estudiantes
	 * @param userIds ids de los usuarios filtrados
	 * @return número de estudiantes que han iniciado el curso
	 * @throws SystemException
	 */
	public int countStudentsByCourseIdUserIdsStarted(long courseId, long[] userIds) throws SystemException{
		
		if(userIds != null && userIds.length > 0){
			return courseResultPersistence.countByCourseIdMultipleUserIdStarted(courseId, userIds);
		}else{
			return 0;
		}
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado el curso, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsFinished
	 * @param courseId id del curso del que quiero contar los estudiantes
	 * @param userIds ids de los usuarios filtrados
	 * @return número de usuarios que han finalizado el curso
	 * @throws SystemException
	 */
	
	public int countStudentsByCourseIdUserIdsFinished(long courseId, long[] userIds) throws SystemException{
		
		if(userIds != null && userIds.length > 0){
			return courseResultPersistence.countByCourseIdMultipleUserIdFinished(courseId, null, userIds);

		}else{
			return 0;
		}
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado el curso y lo hayan aprobado, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsPassed
	 * @param courseId id del curso del que quiero contar los estudiantes
	 * @param userIds ids de los usuarios filtrados
	 * @return número de estudiantes que han aprobado el curso
	 * @throws SystemException
	 */
	
	public int countStudentsByCourseIdUserIdsPassed(long courseId, long[] userIds) throws SystemException{
		
		if(userIds != null && userIds.length > 0){
			return courseResultPersistence.countByCourseIdPassedMultipleUserIdFinished(courseId, true, null, userIds);
		}else{
			return 0;
		}
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado el curso y lo hayan suspendido, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsFailed
	 * @param courseId id del curso del que quiero contar los estudiantes
	 * @param userIds ids de los usuarios filtrados
	 * @return número de estudiantes que han finalizado el curso y lo han suspendido
	 * @throws SystemException
	 */
	
	public int countStudentsByCourseIdUserIdsFailed(long courseId, long[] userIds) throws SystemException{
		
		if(userIds != null && userIds.length > 0){
			return courseResultPersistence.countByCourseIdPassedMultipleUserIdFinished(courseId, false, null, userIds);
		}else{
			return 0;
		}
	}
	
	/**
	 * Devuelve el número de estudiantes que han comenzado un curso, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsStarted
	 * @param courseId id del curso del que quiero contar estudiantes
	 * @param teamId id del equipo
	 * @return número de estudiantes que han iniciado el curso
	 * @throws SystemException
	 */
	public int countStudentsByCourseIdTeamIdStarted(long courseId, long companyId, long[] teamIds, boolean includeEditions) throws SystemException{
		return courseLocalService.countStudentsFromCourse(courseId, companyId, null, null, null, null, WorkflowConstants.STATUS_ANY, teamIds, 
				false, includeEditions, CourseParams.STUDENTS_TYPE_STARTED);
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado el curso, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsFinished
	 * @param courseId id del curso del que quiero contar los estudiantes
	 * @param teamId id del equipo
	 * @return número de usuarios que han finalizado el curso
	 * @throws SystemException
	 */
	
	public int countStudentsByCourseIdTeamIdFinished(long courseId, long companyId, long[] teamIds, boolean includeEditions) throws SystemException{
		return courseLocalService.countStudentsFromCourse(courseId, companyId, null, null, null, null, WorkflowConstants.STATUS_ANY, teamIds, 
				false, includeEditions, CourseParams.STUDENTS_TYPE_FINISHED);
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado el curso y lo hayan aprobado, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsPassed
	 * @param courseId id del curso del que quiero contar los estudiantes
	 * @param teamId id del equipo
	 * @return número de estudiantes que han aprobado el curso
	 * @throws SystemException
	 */
	
	public int countStudentsByCourseIdTeamIdPassed(long courseId, long companyId, long[] teamIds, boolean includeEditions) throws SystemException{
		return courseLocalService.countStudentsFromCourse(courseId, companyId, null, null, null, null, WorkflowConstants.STATUS_ANY, teamIds, 
				false, includeEditions, CourseParams.STUDENTS_TYPE_PASSED);
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado el curso y lo hayan suspendido, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByCourseIdUserExcludedIdsFailed
	 * @param courseId id del curso del que quiero contar los estudiantes
	 * @param teamId id del equipo
	 * @return número de estudiantes que han finalizado el curso y lo han suspendido
	 * @throws SystemException
	 */
	
	public int countStudentsByCourseIdTeamIdFailed(long courseId, long companyId, long[] teamIds, boolean includeEditions) throws SystemException{
		return courseLocalService.countStudentsFromCourse(courseId, companyId, null, null, null, null, WorkflowConstants.STATUS_ANY, teamIds, 
				false, includeEditions, CourseParams.STUDENTS_TYPE_FAILED);
	}
	
	public List<CourseResult> getByUserId(long userId){
		List<CourseResult> results = new ArrayList<CourseResult>();
		try {
			results = courseResultPersistence.findByUserId(userId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public CourseResult getCourseResultByCourseAndUser(long courseId,long userId) throws SystemException{

		return courseResultPersistence.fetchByuc(userId, courseId);
	}
	
	public List<CourseResult> getCourseResultByCourseId(long courseId, int start, int end, OrderByComparator orderByComparator){
		try {
			return courseResultPersistence.findByCourseId(courseId, start, end, orderByComparator);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Inicializa el courseresult cuando un usuario lo ha comenzado
	 * @param courseResult
	 * @return
	 * @throws SystemException
	 */
	@Override
	public CourseResult initializeCourseResult(CourseResult courseResult) throws SystemException{
		courseResult.setStartDate(new Date());
		courseResult = courseResultPersistence.update(courseResult, false);
		
		return courseResult;
	}
	
	public void update(CourseResult cresult) throws SystemException{
		if(cresult.getPassedDate()!=null)
		{
			CourseResult previousCR=courseResultLocalService.getCourseResultByCourseAndUser(cresult.getCourseId(), cresult.getUserId());
			if(previousCR==null || previousCR.getPassedDate()==null)
			{
				Course course;
				try {
					course = courseLocalService.getCourse(cresult.getCourseId());


				String action=AuditConstants.FAILED;
				if(cresult.getPassed())
				{
					action=AuditConstants.PASSED;
				}
				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
						cresult.getCourseId(), cresult.getUserId(), action, Long.toString(cresult.getResult()));
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					throw new SystemException(e);
				}
			}
		
		}
		if(cresult.getPassed()){
			Course course = courseLocalService.fetchCourse(cresult.getCourseId());
			associateCompetencesToUser(courseCompetencePersistence.findBycourseId(cresult.getCourseId(), false), cresult.getUserId());
			
			//Si tiene curso padre, asignamos las del curso padre tambi�n.
			if(course.getParentCourseId()>0){
				associateCompetencesToUser(courseCompetencePersistence.findBycourseId(course.getParentCourseId(), false), cresult.getUserId());
			}
			
			
		}		
		courseResultPersistence.update(cresult, false);
		
		//Actualizamos los diplomas externos (si los hay)
		CourseDiplomaRegistry cdr=new CourseDiplomaRegistry();
		if(cdr!=null){
			CourseDiploma courseDiploma = cdr.getCourseDiploma();
			if(courseDiploma!=null){
				courseDiploma.updateUserDiploma(cresult.getCrId());
			}
		}
	}
	
	public void update(ModuleResult mresult) throws PortalException, SystemException{
		Module theModule=moduleLocalService.getModule(mresult.getModuleId());
		Course course=coursePersistence.fetchByGroupCreatedId(theModule.getGroupId());
		if(course!=null){
			CourseEvalRegistry cer=new CourseEvalRegistry();
			long courseEvalTypeId=course.getCourseEvalId();
			CourseEval ceval=cer.getCourseEval(courseEvalTypeId);
			ceval.updateCourse(course, mresult);
		}
	}
	
	public void resetUser(long userId, long groupCreatedId) throws SystemException, PortalException {
		Course course = courseLocalService.getCourseByGroupCreatedId(groupCreatedId);
		CourseResult courseResult = courseResultPersistence.fetchByuc(userId, course.getCourseId());
		if(courseResult!=null){
			courseResult.setResult(0);
			courseResult.setPassed(false);
			courseResult.setStartDate(null);
			courseResult.setPassedDate(null);
			courseResultPersistence.update(courseResult, true);
			auditEntryLocalService.addAuditEntry(course.getCompanyId(), groupCreatedId, Course.class.getName(), course.getPrimaryKey(), userId, "RESET", null);
			List<Module> modules = moduleLocalService.findAllInGroup(course.getGroupCreatedId());
			for (Module module : modules) {
				List<ModuleResult> moduleResults = moduleResultLocalService.getListModuleResultByModuleAndUser(module.getModuleId(), userId);
				for (ModuleResult moduleResult : moduleResults) {
					moduleResultLocalService.deleteModuleResult(moduleResult.getMrId());
					auditEntryLocalService.addAuditEntry(module.getCompanyId(), module.getGroupId(), ModuleResult.class.getName(), moduleResult.getPrimaryKey(), userId, ActionKeys.DELETE, null);
				}
				List<LearningActivity> learnAct = learningActivityLocalService.getLearningActivitiesOfModule(module.getModuleId());
				for (LearningActivity learningActivity : learnAct) {
					long actId = learningActivity.getActId();
					learningActivityTryLocalService.deleteUserTries(actId, userId);
					auditEntryLocalService.addAuditEntry(learningActivity.getCompanyId(), learningActivity.getGroupId(), LearningActivityTry.class.getName(), learningActivity.getPrimaryKey(), userId, ActionKeys.DELETE, null);
					LearningActivityResult learActResult = learningActivityResultLocalService.getByActIdAndUserId(actId, userId);
					if(learActResult!=null){
						learningActivityResultLocalService.deleteLearningActivityResult(learActResult.getLarId());
						auditEntryLocalService.addAuditEntry(learningActivity.getCompanyId(), learningActivity.getGroupId(), LearningActivityResult.class.getName(), learActResult.getPrimaryKey(), userId, ActionKeys.DELETE, null);
					}
				}
			}
		}
	}
	
	public List<CourseResult> getCourseResultByCourseParentId(long courseParentId, long userId){
		return courseResultFinder.findCourseResultByParentCourseId(courseParentId, userId);
	}
	
	public void softInitializeByGroupIdAndUserId(long groupId, long userId) throws SystemException {
		Course course = courseLocalService.getCourseByGroupCreatedId(groupId);
		if (course != null){
			CourseResult result = courseResultLocalService.getCourseResultByCourseAndUser(course.getCourseId(), userId);
			if(result==null) {
				result = courseResultLocalService.addCourseResult(userId,course.getCourseId(), userId);
			}
			
			if(result!=null && result.getStartDate()==null){
				result.setStartDate(new Date());
				courseResultLocalService.update(result);
			}
		}
	}
	
	public String translateResult(Locale locale, double result, long groupId){
		String translatedResult = "";
		try {
			Course curso = courseLocalService.getCourseByGroupCreatedId(groupId);
			if(curso != null){
				CalificationType ct = new CalificationTypeRegistry().getCalificationType(curso.getCalificationType());
				translatedResult = ct.translate(locale,groupId,result);
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return translatedResult;
	}
	
	/**
	 * @deprecated Renamed to {@link #avgPassedStudentsResult} 
	 */
	/**
	 * @deprecated SE RECOMIENDA NO USAR ESTE MÉTODO, SE RECOMIENDA USAR: avgStudentsResult(Course course, boolean passed)
	 * o avgResultByCourseIdUserExcludedIds(long courseId, passed, long[] userExcludedIds)
	 * @param course curso
	 * @param _students lista de estudiantes, si viene vacía se calculan dentro
	 * @param passed si queremos de los aprobados o suspendos
	 * @return media de resultado de usuarios para un curso
	 */
	@Deprecated
	public Double avgStudentsResult(Course course, List<User> _students, boolean passed) throws SystemException{
		return CourseResultFinderUtil.avgResultByCourseIdStudents(course.getCourseId(), passed);
	}
	
	/**
	 * Cuenta los estudiantes que han iniciado el curso, si no se tiene ya la lista de usuarios excluidos (profesores y editores) llamar
	 * a este método, si se tiene la lista llamar a countStudentsByCourseIdUserIdsStarted
	 * @param course curso del que quiero los usuarios
	 * @return numero de usuarios que han iniciado el curso
	 * @throws SystemException
	 * @deprecated Renamed to {@link #countStartedOnlyStudents} 
	 */
	public long countStudentsByCourseId(Course course) throws SystemException
	{
		long[] userExcludedIds = CourseLocalServiceUtil.getTeachersAndEditorsIdsFromCourse(course);
		return countStudentsByCourseIdUserExcludedIdsStarted(course.getCourseId(), userExcludedIds);
	}
	
	/**
	 * @deprecated ESTE LO VAMOS A DEPRECAR, HABR�A QUE LLAMAR AL MÉTODO countStudentsByCourseId o countStudentsByCourseIdUserIdsStarted 
	 * Cuenta los estudiantes que han iniciado el curso
	 * @param course curso del que quiero los usuarios
	 * @param students lista de estudiantes, si se pasa a null se obtienen dentro de la función
	 * @return numero de usuarios que han iniciado el curso
	 * @throws SystemException
	 */
	@Deprecated
	public long countStudentsByCourseId(Course course, List<User> students) throws SystemException{
		
		if(Validator.isNull(students) || !(students.size()>0))
			students = CourseLocalServiceUtil.getStudentsFromCourse(course.getCompanyId(), course.getGroupCreatedId());
		
		if(students != null && students.size() > 0){
			long[] userIds = new long[students.size()];
			for(int i = 0; i < students.size(); i++){
				userIds[i] = students.get(i).getUserId();
			}
			return courseResultPersistence.countByCourseIdMultipleUserIdStarted(course.getCourseId(), userIds);
		}else{
			return 0;
		}
	}
	
	/**
	 * @deprecated ESTE SE VA A DEPRECAR PORQUE NO ES RECOMENDABLE USAR UNA LISTA GRANDE USUARIOS, ES MEJOR PASAR LA DE LOS EDITORES Y PROFESORES 
	 * POR LO QUE SE RECOMIENDA LLAMAR A countStudentsByCourseId o countStudentsByCourseIdUserIds
	 * @param course curso del que quiero los usuarios
	 * @param students lista de estudiantes del curso, si no se pasa la lista se obtiene en la función
	 * @param passed si quiero los aprobados
	 * @return numero de usuarios que han pasado el curso si passed es a true
	 * @throws SystemException
	 */
	@Deprecated
	public long countStudentsByCourseId(Course course, List<User> students, boolean passed) throws SystemException {
		
		if(Validator.isNull(students) || !(students.size()>0))
			students = CourseLocalServiceUtil.getStudentsFromCourse(course.getCompanyId(), course.getGroupCreatedId());
	
		if(students != null && students.size() > 0){
			long[] userIds = new long[students.size()];
			for(int i = 0; i < students.size(); i++){
				userIds[i] = students.get(i).getUserId();
			}
				
			return courseResultPersistence.countByCourseIdPassedMultipleUserId(course.getCourseId(), passed, userIds);
		}else{
			return 0;
		}
	}
	
	/**
	 * @deprecated Se reemplaza por addCourseResult y initializeCourseResult
	 * Te crea el courseresult inicializando startDate
	 * @param courseId id del curso
	 * @param userId id del estudiante
	 */
	@Deprecated
	public CourseResult create(long courseId, long userId) throws SystemException {
		CourseResult courseResult = addCourseResult(0, courseId, userId);
		courseResult.setStartDate(new Date());
		courseResult = courseResultPersistence.update(courseResult, false);
		
		return courseResult;
	}
	
	/**
	 * @deprecated Se reemplaza por addCourseResult y initializeCourseResult
	 * Te crea el courseresult inicializando startDate
	 * @param courseId id del curso
	 * @param userId id del estudiante
	 * @param allowStartDate fecha de inicio para el alumno permitida
	 * @param allowFinishDate fecha de fin para el alumno permitida
	 */
	@Deprecated
	public CourseResult create(long courseId, long userId,Date allowStartDate,Date allowFinishDate) throws SystemException{

		CourseResult courseResult = addCourseResult(0, courseId, userId, allowStartDate, allowFinishDate);
		courseResult.setStartDate(new Date());
		courseResult = courseResultPersistence.update(courseResult, false);

		return courseResult;
	}
	
	/**
	 * @deprecated El nombre era confuso ya que no concordaba con las variables
	 */
	@Deprecated
	public CourseResult getByUserAndCourse(long courseId,long userId) throws SystemException{
		return courseResultPersistence.fetchByuc(userId, courseId);
	}
	
}
