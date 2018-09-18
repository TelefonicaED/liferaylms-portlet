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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.xml.sax.InputSource;

import com.liferay.lms.NoSuchLearningActivityResultException;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.base.LearningActivityResultLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.LearningActivityResultFinderUtil;
import com.liferay.lms.service.persistence.LearningActivityResultUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;


public class LearningActivityResultLocalServiceImpl	extends LearningActivityResultLocalServiceBaseImpl {

	private Log log = LogFactoryUtil.getLog(LearningActivityResultLocalServiceImpl.class);


	public LearningActivityResult update(LearningActivityTry learningActivityTry) throws SystemException, PortalException{

		long actId=learningActivityTry.getActId();
		long userId=learningActivityTry.getUserId();
		LearningActivityResult learningActivityResult=getByActIdAndUserId(actId, userId);
		LearningActivity learningActivity=learningActivityLocalService.getLearningActivity(actId);
		boolean recalculateActivity = false;
		log.debug("****LAR "+learningActivityResult);
		if(learningActivityResult==null){	
			learningActivityResult=
					learningActivityResultPersistence.create(counterLocalService.increment(
							LearningActivityResult.class.getName()));
			learningActivityResult.setStartDate(learningActivityTry.getStartDate());
			learningActivityResult.setActId(actId);
			learningActivityResult.setUserId(userId);
			learningActivityResult.setPassed(false);
			recalculateActivity = true;
		}
		log.debug("****END DATE "+learningActivityTry.getEndDate());
		if(learningActivityTry.getEndDate()!=null){
			
			long cuantosTryLlevo=LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(actId, userId);
			log.debug("****Cuantos try llevo "+cuantosTryLlevo);
			if(learningActivity.getTries()>0&&cuantosTryLlevo>=learningActivity.getTries()){
				learningActivityResult.setEndDate(learningActivityTry.getEndDate());
				recalculateActivity= true;
				log.debug("****Recalculamos 1");
			}

			if(learningActivityTry.getResult()>learningActivityResult.getResult()){			
				learningActivityResult.setResult(learningActivityTry.getResult());
				recalculateActivity= true;
				log.debug("****Recalculamos 2");
			}

			if(!learningActivityResult.getPassed()){
				if(learningActivityTry.getResult()>=learningActivity.getPasspuntuation()){
					learningActivityResult.setEndDate(learningActivityTry.getEndDate());
					learningActivityResult.setPassed(true);	
					recalculateActivity= true;	
					log.debug("****Recalculamos 3");
				}
			}	
			if(Validator.isNotNull(learningActivityTry.getComments())&&!learningActivityTry.getComments().equals(learningActivityResult.getComments())){
				learningActivityResult.setComments(learningActivityTry.getComments());
				recalculateActivity= true;
				log.debug("****Recalculamos 4");
			}
		}
		
		log.debug("****Recalculate "+recalculateActivity);
		if(recalculateActivity){
			learningActivityResultPersistence.update(learningActivityResult, false);
			moduleResultLocalService.update(learningActivityResult);
			
			//auditing
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
			if(serviceContext!=null){
				AuditingLogFactory.audit(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), LearningActivityResult.class.getName(), 
						learningActivityResult.getPrimaryKey(), learningActivityTry.getUserId(), AuditConstants.UPDATE, null);
			}else{
				LearningActivity la = learningActivityPersistence.fetchByPrimaryKey(actId);
				if(la!=null){
					AuditingLogFactory.audit(la.getCompanyId(), la.getGroupId(), LearningActivityResult.class.getName(), 
							learningActivityResult.getPrimaryKey(), learningActivityTry.getUserId(), AuditConstants.UPDATE, null);
				}
			}
		}
		return learningActivityResult;

	}
	public LearningActivityResult update(long latId, long result, String tryResultData, long userId) throws SystemException, PortalException {
		LearningActivityTry learningActivityTry = LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);
		if (userId != learningActivityTry.getUserId()) {
			throw new PortalException();
		}
		if (result >= 0) {
			learningActivityTry.setResult(result);

			Date endDate = new Date(System.currentTimeMillis());
			learningActivityTry.setEndDate(endDate);
		}
		learningActivityTry.setTryResultData(tryResultData);
		LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningActivityTry);

		return update(learningActivityTry);
	}
	
	
	
	
	public boolean existsLearningActivityResult(long actId,long userId) throws SystemException{
		return learningActivityResultPersistence.countByact_user(actId, userId)>0;
	}

	public boolean userPassed(long actId,long userId) throws SystemException{
		LearningActivityResult learningActivityResult = getByActIdAndUserId(actId, userId);

		return learningActivityResult != null && learningActivityResult.isPassed();
	}
	
	public long countPassed(long actId) throws SystemException{
		return LearningActivityResultUtil.countByap(actId, true);
	}

	
	public long countByActId(long actId){
		long result = 0;
		try {
			result = LearningActivityResultUtil.countByac(actId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return result;	
		
	}
	
	/**
	 * Devuelve el número de estudiantes que han pasado una actividad (en caso de passed = true) o lo que han suspendido o la tienen en curos (en caso de passed = false)
	 * @param actId id de la actividad
	 * @param companyId id de la company de la actividad
	 * @param courseGroupCreatedId id del group del curso
	 * @param passed si se quieren los aprobados o no
	 * @return número de estudiantes que han pasado la actividad o los que han suspendido + los que la tienen no la han finalizado
	 * @throws SystemException
	 */
	public long countPassedOnlyStudents(long actId, long companyId, long courseGroupCreatedId, boolean passed) throws SystemException{
		Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(courseGroupCreatedId);
		long[] userExcludedIds = CourseLocalServiceUtil.getTeachersAndEditorsIdsFromCourse(course);
		
		if(passed)
			return countStudentsByActIdUserExcludedIdsPassed(actId, userExcludedIds);
		else
			return countStudentsByActIdUserExcludedIdsFailed(actId, userExcludedIds);
	}

	/**
	 * @deprecated SE RECOMIENDA NO USAR ESTE MÉTODO, SE RECOMIENDA USAR: countPassedOnlyStudents(long actId, long companyId, long courseGroupCreatedId, boolean passed)
	 * o countPassedOnlyStudents(long actId, boolean passed, long[] userExcludedIds)
	 * @param actId id de la actividad
	 * @param companyId id de la company de la actividad
	 * @param courseGroupCreatedId id del group del curso
	 * @param passed si se quieren los aprobados o no
	 * @param lista de estudiantes, si viene vacía se calculan dentro
	 * @return número de estudiantes que han pasado la actividad o los que han suspendido + los que la tienen no la han finalizado
	 */
	public long countPassedOnlyStudents(long actId, long companyId, long courseGropupCreatedId, boolean passed, List<User> _students) throws SystemException{
		List<User> students = null;
		// Se prepara el metodo para recibir un Listado de estudiantes especificos,, por ejemplo que pertenezcan a alguna organizacion. Sino, se trabaja con todos los estudiantes del curso.
		if(Validator.isNotNull(_students) && _students.size() > 0)
			students = _students;
		else
			students = CourseLocalServiceUtil.getStudentsFromCourse(companyId, courseGropupCreatedId);
		
		if(students != null && students.size() > 0){
			long[] userIds = new long[students.size()];
			for(int i = 0; i < students.size(); i++){
				userIds[i] = students.get(i).getUserId();
			}
			return learningActivityResultPersistence.countByActIdPassedMultipleUserId(actId, passed, userIds);
		}

		return 0;
	}

	/**
	 * Devuelve el número de usuarios que han suspendido una actividad
	 * @param actId id de la actividad
	 * @return número de usuarios que han suspendido la actividad
	 */
	public long countNotPassed(long actId) throws SystemException{	
		return learningActivityResultPersistence.countByActIdPassedFinished(actId, false);
	}

	/**
	 * Devuelve el número de estudiantes que han suspendido la actividad
	 * @param actId id de la actividad
	 * @param companyId id de la company de la actividad
	 * @param courseGroupCreatedId id del group de la actividad
	 */
	public long countNotPassedOnlyStudents(long actId, long companyId, long courseGroupCreatedId) throws SystemException{
		Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(courseGroupCreatedId);
		long[] userExcludedIds = CourseLocalServiceUtil.getTeachersAndEditorsIdsFromCourse(course);
		
		return countStudentsByActIdUserExcludedIdsFailed(actId, userExcludedIds); 
	}

	/**
	 * @deprecated SE RECOMIENDA NO USAR ESTE MÉTODO, SE RECOMIENDA USAR: countNotPassedOnlyStudents(long actId, long companyId, long courseGroupCreatedId)
	 * o countStudentsByActIdUserExcludedIdsFailed(actId, userExcludedIds)
	 * @param actId id de la actividad
	 * @param companyId id de la company de la actividad
	 * @param courseGroupCreatedId id del group del curso
	 * @param lista de estudiantes, si viene vacía se calculan dentro
	 * @return número de estudiantes que han suspendido la actividad
	 */
	public long countNotPassedOnlyStudents(long actId, long companyId, long courseGropupCreatedId, List<User> _students) throws SystemException
	{
		List<User> students = null;
		// Se prepara el metodo para recibir un Listado de estudiantes especificos,, por ejemplo que pertenezcan a alguna organizacion. Sino, se trabaja con todos los estudiantes del curso.
		if(Validator.isNotNull(_students) && _students.size() > 0)
			students = _students;
		else
			students = CourseLocalServiceUtil.getStudentsFromCourse(companyId, courseGropupCreatedId);
		
		if(students != null && students.size() > 0){
			long[] userIds = new long[students.size()];
			for(int i = 0; i < students.size(); i++){
				userIds[i] = students.get(i).getUserId();
			}
			return learningActivityResultPersistence.countByActIdPassedMultipleUserIdFinished(actId, false, userIds);
		}

		return 0;
	}

	public Double avgResult(long actId) throws SystemException
	{
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivityResult.class, classLoader);
		Criterion criterion=PropertyFactoryUtil.forName("actId").eq(actId);
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("endDate").isNotNull();
		dq.add(criterion);
		dq.setProjection(ProjectionFactoryUtil.avg("result"));
		return (Double)(learningActivityResultPersistence.findWithDynamicQuery(dq).get(0));
	}
	
	/**
	 * Devuelve la media de resultado de usuarios para una actividad, si ya se tiene la lista de usuarios excluidos (profesores y editores)
	 * llamar al método avgResultByActIdUserExcludedIds directamente
	 * @param actId id de la actividad
	 * @param companyId id de la company de la actividad
	 * @param courseGroupCreatedId id del group del curso
	 * @return media de resultado de usuarios para una actividad
	 */
	public Double avgResultOnlyStudents(long actId, long companyId, long courseGroupCreatedId) throws SystemException {
		Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(courseGroupCreatedId);
		long[] userExcludedIds = CourseLocalServiceUtil.getTeachersAndEditorsIdsFromCourse(course);
		
		return LearningActivityResultFinderUtil.avgResultByActId(actId, null, userExcludedIds);
	}

	/**
	 * @deprecated SE RECOMIENDA NO USAR ESTE MÉTODO, SE RECOMIENDA USAR: avgResultOnlyStudents(long actId, long companyId, long courseGroupCreatedId)
	 * o avgResultByActIdUserExcludedIds(long actId, long[] userExcludedIds)
	 * @param actId id de la actividad
	 * @param companyId id de la company de la actividad
	 * @param courseGroupCreatedId id del group del curso
	 * @param _students lista de estudiantes, si viene vacía se calculan dentro
	 * @return media de resultado de usuarios para una actividad
	 */
	@Deprecated
	public Double avgResultOnlyStudents(long actId, long companyId, long courseGropupCreatedId, List<User> _students) throws SystemException{

		List<User> students = null;
		// Se prepara el metodo para recibir un Listado de estudiantes especificos,, por ejemplo que pertenezcan a alguna organizacion. Sino, se trabaja con todos los estudiantes del curso.
		if(Validator.isNotNull(_students) && _students.size() > 0)
			students = _students;
		else
			students = CourseLocalServiceUtil.getStudentsFromCourse(companyId, courseGropupCreatedId);
		
		if(students != null && students.size() > 0){
			long[] userIds = new long[students.size()];
			for(int i = 0; i < students.size(); i++){
				userIds[i] = students.get(i).getUserId();
			}
			return LearningActivityResultFinderUtil.avgResultByActId(actId, userIds, null);
		}
		return new Double(0);
	}
	
	/**
	 * Devuelve la media de resultado de usuarios para una actividad
	 * @param actId id de la actividad
	 * @param userExcludedIds id de la company de la actividad
	 * @return media de resultado de usuarios para una actividad
	 */
	public double avgResultByActIdUserExcludedIds(long actId, long[] userExcludedIds) throws SystemException{
		return LearningActivityResultFinderUtil.avgResultByActId(actId, null, userExcludedIds);
	}
	
	/**
	 * Devuelve la media de resultado de usuarios para una actividad, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar avgTriesByActIdUserExcludedIds
	 * @param actId id de la actividad
	 * @param userIds ids de los usuarios filtrados
	 * @return media de resultado de usuarios para una actividad
	 * @throws SystemException
	 */
	public double avgResultByActIdUserIds(long actId, long[] userIds) throws SystemException{
		if(userIds != null && userIds.length > 0){
			return LearningActivityResultFinderUtil.avgResultByActId(actId, userIds, null);
		}else{
			return 0;
		}
	}

	public long countStarted(long actId) throws SystemException{
		return learningActivityResultPersistence.countByActIdStarted(actId);
	}

	/**
	 * Devuelve el n�mero de estudiantes que han comenzado una actividad, si ya se tiene la lista de usuarios excluidos (profesores y editores)
	 * llamar al m�todo countStudentsByActIdUserExcludedIdsStarted directamente
	 * @param actId id de la actividad
	 * @param companyId id de la company de la actividad
	 * @param courseGroupCreatedId id del group del curso
	 * @return n�mero de estudiantes que han comenzado una actividad
	 */
	public long countStartedOnlyStudents(long actId, long companyId, long courseGroupCreatedId) throws SystemException{
		Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(courseGroupCreatedId);
		long[] userExcludedIds = CourseLocalServiceUtil.getTeachersAndEditorsIdsFromCourse(course);
		
		return countStudentsByActIdUserExcludedIdsStarted(actId, userExcludedIds);
	}

	/**
	 * @deprecated SE RECOMIENDA NO USAR ESTE M�TODO, SE RECOMIENDA USAR: countStartedOnlyStudents(long actId, long companyId, long courseGroupCreatedId)
	 * o countStudentsByActIdUserExcludedIdsStarted(actId, userExcludedIds)
	 * @param actId id de la actividad
	 * @param companyId id de la company de la actividad
	 * @param courseGroupCreatedId id del group del curso
	 * @param _students lista de estudiantes, si viene vac�a se calculan dentro
	 * @return n�mero de estudiantes que han comenzado la actividad
	 */
	@Deprecated
	public long countStartedOnlyStudents(long actId, long companyId, long courseGropupCreatedId, List<User> _students) throws SystemException{
		
		return LearningActivityResultFinderUtil.countStartedOnlyStudents(actId, companyId, courseGropupCreatedId, _students);
	}

	/**
	 * Devuelve el n�mero de estudiantes que han finalizado una actividad, si ya se tiene la lista de usuarios excluidos (profesores y editores)
	 * llamar al m�todo countStudentsByActIdUserExcludedIdsFinished directamente
	 * @param actId id de la actividad
	 * @param companyId id de la company de la actividad
	 * @param courseGroupCreatedId id del group del curso
	 * @return n�mero de estudiantes que han finalizado una actividad
	 */
	public long countFinishedOnlyStudents(long actId, long companyId, long courseGroupCreatedId){
		try {
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(courseGroupCreatedId);
			long[] userExcludedIds = CourseLocalServiceUtil.getTeachersAndEditorsIdsFromCourse(course);
			
			return countStudentsByActIdUserExcludedIdsFinished(actId, userExcludedIds);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}
	
	/**
	 * @deprecated SE RECOMIENDA NO USAR ESTE M�TODO, SE RECOMIENDA USAR: countFinishedOnlyStudents(long actId, long companyId, long courseGroupCreatedId)
	 * o countStudentsByActIdUserExcludedIdsFinished(actId, userExcludedIds)
	 * @param actId id de la actividad
	 * @param companyId id de la company de la actividad
	 * @param courseGroupCreatedId id del group del curso
	 * @param _students lista de estudiantes, si viene vac�a se calculan dentro
	 * @return n�mero de estudiantes que han finalizado la actividad
	 */
	@Deprecated
	public long countFinishedOnlyStudents(long actId, long companyId, long courseGropupCreatedId, List<User> _students){
		return LearningActivityResultFinderUtil.countFinishedOnlyStudents(actId, companyId, courseGropupCreatedId, _students);
	}
	
	
	public double triesPerUser(long actId) throws SystemException
	{
		long tries=learningActivityTryPersistence.countByact(actId);
		long started=countStarted(actId);
		if(started==0)
		{
			return 0;
		}
		return ((double) tries)/((double) started);
	}

	/**
	 * Devuelve la media de intentos por usuario para una actividad, si ya se tiene la lista de usuarios excluidos (profesores y editores)
	 * llamar al método avgTriesByActIdUserExcludedIds directamente
	 * @param actId id de la actividad
	 * @param companyId id de la company de la actividad
	 * @param courseGroupCreatedId id del group del curso
	 * @return media de intentos por usuario para una actividad
	 */
	public double triesPerUserOnlyStudents(long actId, long companyId, long courseGroupCreatedId) throws SystemException {
		Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(courseGroupCreatedId);
		long[] userExcludedIds = CourseLocalServiceUtil.getTeachersAndEditorsIdsFromCourse(course);
		
		return avgTriesByActIdUserExcludedIds(actId, userExcludedIds);
	}

	/**
	 * @deprecated SE RECOMIENDA NO USAR ESTE MÉTODO, SE RECOMIENDA USAR: triesPerUserOnlyStudents(long actId, long companyId, long courseGroupCreatedId)
	 * o avgTriesByActIdUserExcludedIds(long actId, long[] userExcludedIds)
	 * @param actId id de la actividad
	 * @param companyId id de la company de la actividad
	 * @param courseGroupCreatedId id del group del curso
	 * @param _students lista de estudiantes, si viene vacía se calculan dentro
	 * @return media de intentos por usuario para una actividad
	 */
	@Deprecated
	public double triesPerUserOnlyStudents(long actId, long companyId, long courseGropupCreatedId, List<User> _students) throws SystemException
	{
		long tries=0;
		List<User> students = null;
		// Se prepara el metodo para recibir un Listado de estudiantes especificos, por ejemplo que pertenezcan a alguna organizacion. Sino, se trabaja con todos los estudiantes del curso.
		if(Validator.isNotNull(_students) && _students.size() > 0)
			students = _students;
		else
			students = CourseLocalServiceUtil.getStudentsFromCourse(companyId, courseGropupCreatedId);

		if(students != null && students.size() > 0){
			long[] userIds = new long[students.size()];
			for(int i = 0; i < students.size(); i++){
				userIds[i] = students.get(i).getUserId();
			}
			
			tries = LearningActivityTryLocalServiceUtil.countTriesByActIdUserIdsStarted(actId, userIds);
	
			long started = countStudentsByActIdUserIdsStarted(actId, userIds);
			if(started==0){
				return 0;
			}
			return ((double) tries)/((double) started);
		}else{
			return 0;
		}
	}
	
	/**
	 * Devuelve la media de intentos por usuario para una actividad
	 * @param actId id de la actividad
	 * @param userExcludedIds id de la company de la actividad
	 * @return media de intentos por usuario para una actividad
	 */
	public double avgTriesByActIdUserExcludedIds(long actId, long[] userExcludedIds) throws SystemException{
		int tries = LearningActivityTryLocalServiceUtil.countTriesByActIdUserExcludedIdsStarted(actId, userExcludedIds);
		int started = countStudentsByActIdUserExcludedIdsStarted(actId, userExcludedIds);
		if(started == 0){
			return 0;
		}
		return (double) tries / (double) started; 
	}
	
	/**
	 * Devuelve la media de intentos por usuario para una actividad, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar avgTriesByActIdUserExcludedIds
	 * @param actId id de la actividad
	 * @param userIds ids de los usuarios filtrados
	 * @return media de intentos por usuario para una actividad
	 * @throws SystemException
	 */
	public double avgTriesByActIdUserIds(long actId, long[] userIds) throws SystemException{
		int tries = LearningActivityTryLocalServiceUtil.countTriesByActIdUserIdsStarted(actId, userIds);
		int started = countStudentsByActIdUserIdsStarted(actId, userIds);
		if(started == 0){
		}
		return (double) tries / (double) started; 
	}

	public LearningActivityResult getByActIdAndUserId(long actId,long userId) throws SystemException{
		return learningActivityResultPersistence.fetchByact_user(actId, userId);
	}

	public Date getLastEndDateByUserId(long userId) throws SystemException{
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivityResult.class, classLoader);
		Criterion criterion=PropertyFactoryUtil.forName("userId").eq(userId);
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("endDate").isNotNull();
		dq.add(criterion);
		dq.setProjection(ProjectionFactoryUtil.max("endDate"));
		return (Date)(learningActivityResultPersistence.findWithDynamicQuery(dq).get(0));
	}

	public List<LearningActivityResult> getByActId(long actId){
		List<LearningActivityResult> results = new ArrayList<LearningActivityResult>();
		try {
			results = learningActivityResultPersistence.findByac(actId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return results;			
	}

	public List<LearningActivityResult> getByGroupIdUserId(long groupId,long userId){
		List<LearningActivityResult> results = new ArrayList<LearningActivityResult>();

		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq2 = DynamicQueryFactoryUtil.forClass(LearningActivity.class, classLoader)
				.add(PropertyFactoryUtil.forName("groupId").eq(groupId))
				.setProjection(ProjectionFactoryUtil.property("actId"));

		DynamicQuery dq = DynamicQueryFactoryUtil.forClass(LearningActivityResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.add(PropertyFactoryUtil.forName("actId").in(dq2));		

		try {
			results = (List<LearningActivityResult>)learningActivityResultPersistence.findWithDynamicQuery(dq);
		} catch (SystemException e) {
			e.printStackTrace();
		}

		log.debug("::getByModuleId:"+results.size());
		return results;			
	}

	public List<LearningActivityResult> getMandatoryByGroupIdUserId(long groupId,long userId){
		List<LearningActivityResult> results = new ArrayList<LearningActivityResult>();

		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq2 = DynamicQueryFactoryUtil.forClass(LearningActivity.class, classLoader)
				.add(PropertyFactoryUtil.forName("groupId").eq(groupId))
				.add(PropertyFactoryUtil.forName("weightinmodule").gt(0L))
				.setProjection(ProjectionFactoryUtil.property("actId"));

		DynamicQuery dq = DynamicQueryFactoryUtil.forClass(LearningActivityResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.add(PropertyFactoryUtil.forName("actId").in(dq2));

		try {
			results = (List<LearningActivityResult>)learningActivityResultPersistence.findWithDynamicQuery(dq);
		} catch (SystemException e) {
			e.printStackTrace();
		}

		log.debug("::getByModuleIdPassed:"+results.size());
		return results;			
	}

	public List<LearningActivityResult> getByModuleIdUserId(long moduleId,long userId){
		List<LearningActivityResult> results = new ArrayList<LearningActivityResult>();

		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq2 = DynamicQueryFactoryUtil.forClass(LearningActivity.class, classLoader)
				.add(PropertyFactoryUtil.forName("moduleId").eq(moduleId))
				.setProjection(ProjectionFactoryUtil.property("actId"));

		DynamicQuery dq = DynamicQueryFactoryUtil.forClass(LearningActivityResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.add(PropertyFactoryUtil.forName("actId").in(dq2));		

		try {
			results = (List<LearningActivityResult>)learningActivityResultPersistence.findWithDynamicQuery(dq);
		} catch (SystemException e) {
			e.printStackTrace();
		}

		log.debug("::getByModuleId:"+results.size());
		return results;			
	}

	public List<LearningActivityResult> getByModuleIdUserIdPassed(long moduleId,long userId){
		List<LearningActivityResult> results = new ArrayList<LearningActivityResult>();

		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq2 = DynamicQueryFactoryUtil.forClass(LearningActivity.class, classLoader)
				.add(PropertyFactoryUtil.forName("moduleId").eq(moduleId))
				.setProjection(ProjectionFactoryUtil.property("actId"));

		DynamicQuery dq = DynamicQueryFactoryUtil.forClass(LearningActivityResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.add(PropertyFactoryUtil.forName("passed").eq(true))
				.add(PropertyFactoryUtil.forName("actId").in(dq2));


		try {
			results = (List<LearningActivityResult>)learningActivityResultPersistence.findWithDynamicQuery(dq);
		} catch (SystemException e) {
			e.printStackTrace();
		}

		log.debug("::getByModuleIdPassed:"+results.size());
		return results;			
	}

	public List<LearningActivityResult> getMandatoryByModuleIdUserIdPassed(long moduleId,long userId){
		List<LearningActivityResult> results = new ArrayList<LearningActivityResult>();

		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq2 = DynamicQueryFactoryUtil.forClass(LearningActivity.class, classLoader)
				.add(PropertyFactoryUtil.forName("moduleId").eq(moduleId))
				.add(PropertyFactoryUtil.forName("weightinmodule").gt(0L))
				.setProjection(ProjectionFactoryUtil.property("actId"));

		DynamicQuery dq = DynamicQueryFactoryUtil.forClass(LearningActivityResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.add(PropertyFactoryUtil.forName("passed").eq(true))
				.add(PropertyFactoryUtil.forName("actId").in(dq2));


		try {
			results = (List<LearningActivityResult>)learningActivityResultPersistence.findWithDynamicQuery(dq);
		} catch (SystemException e) {
			e.printStackTrace();
		}

		log.debug("::getByModuleIdPassed:"+results.size());
		return results;			
	}

	public List<LearningActivityResult> getByUserId(long userId){
		List<LearningActivityResult> results = new ArrayList<LearningActivityResult>();
		try {
			results = learningActivityResultPersistence.findByuser(userId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return results;			
	}
	
	public int countMandatoryByModuleIdUserIdPassed(long moduleId,long userId){

		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq2 = DynamicQueryFactoryUtil.forClass(LearningActivity.class, classLoader)
				.add(PropertyFactoryUtil.forName("moduleId").eq(moduleId))
				.add(PropertyFactoryUtil.forName("weightinmodule").gt(0L))
				.setProjection(ProjectionFactoryUtil.property("actId"));

		DynamicQuery dq = DynamicQueryFactoryUtil.forClass(LearningActivityResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.add(PropertyFactoryUtil.forName("passed").eq(true))
				.add(PropertyFactoryUtil.forName("actId").in(dq2));


		try {
			return (int) LearningActivityResultLocalServiceUtil.dynamicQueryCount(dq);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return 0;			
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
	
	
	public String getCalificationTypeSuffix(Locale locale, double result, long groupId){
		String suffix = "";
		try {
			Course curso = courseLocalService.getCourseByGroupCreatedId(groupId);
			if(curso != null){
				CalificationType ct = new CalificationTypeRegistry().getCalificationType(curso.getCalificationType());
				suffix = ct.getSuffix(groupId);
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return suffix;
	}
	
	
	@Override
	public LearningActivityResult deleteLearningActivityResult(LearningActivityResult lar) throws SystemException{
		try {
			LearningActivityResultUtil.remove(lar.getLarId());
			
			
			
			long moduleId = LearningActivityLocalServiceUtil.fetchLearningActivity(lar.getActId()).getModuleId();
			//Si se ha borrado correctamente llamamos al update de moduleresult para qeu recalcule, o borramos el moduleResult si no hay mas lar.
			if(getByModuleIdUserId(moduleId,lar.getUserId()).size()>0){
				ModuleResultLocalServiceUtil.update(lar);
			}else{
				ModuleResult moduleResult = ModuleResultLocalServiceUtil.getByModuleAndUser(moduleId, lar.getUserId());
				ModuleResultLocalServiceUtil.deleteModuleResult(moduleResult);
			}
			
			return lar;
		} catch (NoSuchLearningActivityResultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Cuenta los estudiantes que han iniciado la actividad: solo llamar si se tiene la lista de usuarios excluidos
	 * @param actId id de la actividad
	 * @param userExcludedIds ids de usuarios excluidos (profesores y editores)
	 * @return número de estudiantes que han comenzado la actividad
	 * @throws SystemException
	 */
	public int countStudentsByActIdUserExcludedIdsStarted(long actId, long[] userExcludedIds) throws SystemException{
		
		if(userExcludedIds != null && userExcludedIds.length > 0){
			return learningActivityResultPersistence.countByActIdNotMultipleUserIdStarted(actId, userExcludedIds);

		}else{
			return learningActivityResultPersistence.countByActIdStarted(actId);
		}
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado la actividad: solo llamar si se tiene la lista de usuarios excluidos
	 * @param actId id de la actividad
	 * @param userExcludedIds ids de usuarios excluidos (profesores y editores)
	 * @return número de estudiantes que han finalizado la actividad
	 * @throws SystemException
	 */
	
	public int countStudentsByActIdUserExcludedIdsFinished(long actId, long[] userExcludedIds) throws SystemException{
		
		if(userExcludedIds != null && userExcludedIds.length > 0){
			return learningActivityResultPersistence.countByActIdNotMultipleUserIdFinished(actId, userExcludedIds);
		}else{
			return learningActivityResultPersistence.countByActIdFinished(actId);
		}
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado la actividad y la han aprobado
	 * @param actId id de la actividad
	 * @param userExcludedIds ids de usuarios excluidos (profesores y editores)
	 * @return número de estudiantes que han finalizado y aprobado la actividad
	 * @throws SystemException
	 */
	
	public int countStudentsByActIdUserExcludedIdsPassed(long actId, long[] userExcludedIds) throws SystemException{
		
		if(userExcludedIds != null && userExcludedIds.length > 0){
			return learningActivityResultPersistence.countByActIdPassedNotMultipleUserIdFinished(actId, true, userExcludedIds);
		}else{
			return learningActivityResultPersistence.countByActIdPassedFinished(actId, true);
		}
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado la actividad y la han suspendido
	 * @param actId id de la actividad
	 * @param userExcludedIds ids de usuarios excluidos (profesores y editores)
	 * @return número de estudiantes que han finalizado y suspendido la actividad
	 * @throws SystemException
	 */
	
	public int countStudentsByActIdUserExcludedIdsFailed(long actId, long[] userExcludedIds) throws SystemException{
		
		if(userExcludedIds != null && userExcludedIds.length > 0){
			return learningActivityResultPersistence.countByActIdPassedNotMultipleUserIdFinished(actId, false, userExcludedIds);

		}else{
			return learningActivityResultPersistence.countByActIdPassedFinished(actId, false);
		}
	}
	
	/**
	 * Cuenta los estudiantes que han iniciado la actividad, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByActIdUserExcludedIdsStarted
	 * @param actId id de la actividad
	 * @param userIds ids de los usuarios filtrados
	 * @return número de estudiantes que han comenzado la actividad
	 * @throws SystemException
	 */
	public int countStudentsByActIdUserIdsStarted(long actId, long[] userIds) throws SystemException{
		
		if(userIds != null && userIds.length > 0){
			return learningActivityResultPersistence.countByActIdMultipleUserIdStarted(actId, userIds);

		}else{
			return 0;
		}
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado la actividad, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByActIdUserExcludedIdsFinished
	 * @param actId id de la actividad
	 * @param userIds ids de los usuarios filtrados
	 * @return número de estudiantes que han finalizado la actividad
	 * @throws SystemException
	 */
	
	public int countStudentsByActIdUserIdsFinished(long actId, long[] userIds) throws SystemException{
		
		if(userIds != null && userIds.length > 0){
			return learningActivityResultPersistence.countByActIdMultipleUserIdFinished(actId, userIds);
		}else{
			return 0;
		}
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado la actividad y la han aprobado, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByActIdUserExcludedIdsPassed
	 * @param actId id de la actividad
	 * @param userIds ids de los usuarios filtrados
	 * @return número de estudiantes que han finalizado y aprobado la actividad
	 * @throws SystemException
	 */
	
	public int countStudentsByActIdUserIdsPassed(long actId, long[] userIds) throws SystemException{
		
		if(userIds != null && userIds.length > 0){
			return learningActivityResultPersistence.countByActIdPassedMultipleUserIdFinished(actId, true, userIds);
		}else{
			return 0;
		}
	}
	
	/**
	 * Cuenta los estudiantes que han finalizado la actividad y la han suspendido, esta función está pensada para pasar una lista de estudiantes filtrada
	 * (por ejemplo para los equipos) para pedir de todos los estudiantes usar countStudentsByActIdUserExcludedIdsFailed
	 * @param actId id de la actividad
	 * @param userIds ids de los usuarios filtrados
	 * @return número de estudiantes que han finalizado y suspendido la actividad
	 * @throws SystemException
	 */
	
	public int countStudentsByActIdUserIdsFailed(long actId, long[] userIds) throws SystemException{
		
		if(userIds != null && userIds.length > 0){
			return learningActivityResultPersistence.countByActIdPassedMultipleUserIdFinished(actId, false, userIds);

		}else{
			return 0;
		}
	}

	public Date getLastEndDateByUserIdCourseId(long userId, long courseId) throws SystemException
	{
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		
		DynamicQuery dqCourse=DynamicQueryFactoryUtil.forClass(Course.class, classLoader);
		Criterion criterion=PropertyFactoryUtil.forName("courseId").eq(courseId);
		dqCourse.add(criterion);
		dqCourse.setProjection(ProjectionFactoryUtil.property("groupCreatedId"));
		
		DynamicQuery dqModule=DynamicQueryFactoryUtil.forClass(Module.class, classLoader);
		dqModule.add(PropertyFactoryUtil.forName("groupId").in(dqCourse));
		dqModule.setProjection(ProjectionFactoryUtil.property("moduleId"));
				
		DynamicQuery dqActivity=DynamicQueryFactoryUtil.forClass(LearningActivity.class, classLoader);
		dqActivity.add(PropertyFactoryUtil.forName("moduleId").in(dqModule));
		dqActivity.setProjection(ProjectionFactoryUtil.property("actId"));
		
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivityResult.class, classLoader);
		criterion=PropertyFactoryUtil.forName("userId").eq(userId);
		dq.add(criterion);
		dq.add(PropertyFactoryUtil.forName("actId").in(dqActivity));
		criterion=PropertyFactoryUtil.forName("endDate").isNotNull();
		dq.add(criterion);
		dq.setProjection(ProjectionFactoryUtil.max("endDate"));
		
		return (Date)(learningActivityResultPersistence.findWithDynamicQuery(dq).get(0));
	}
}