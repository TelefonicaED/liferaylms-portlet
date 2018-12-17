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

package com.liferay.lms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.liferay.lms.NoSuchCourseTypeException;
import com.liferay.lms.model.CourseType;
import com.liferay.lms.service.base.CourseTypeLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.CourseTypeFinderUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * The implementation of the course type local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseTypeLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TED
 * @see com.liferay.lms.service.base.CourseTypeLocalServiceBaseImpl
 * @see com.liferay.lms.service.CourseTypeLocalServiceUtil
 */
public class CourseTypeLocalServiceImpl extends CourseTypeLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseTypeLocalServiceUtil} to access the course type local service.
	 */
	
	Log log = LogFactoryUtil.getLog(CourseTypeLocalServiceImpl.class);
	
	public boolean hasCourses(long courseTypeId){
		return CourseTypeFinderUtil.countCourses(courseTypeId)>0;
	}
	
	public List<CourseType> getByCompanyId(long companyId) throws SystemException{
		return courseTypePersistence.findByCompanyId(companyId);
	}
	
	public CourseType getByCourseTypeId(long courseTypeId) throws NoSuchCourseTypeException, SystemException{
		return courseTypePersistence.findByCourseTypeId(courseTypeId);
	}
	
	public CourseType addCourseType(long companyId, long userId, long groupId, Map<Locale, String> nameMap, Map<Locale, String> descriptionMap, long[] templateIds,
			long[] courseEvalTypeIds, long[] learningActivityTypeIds, long[] inscriptionTypeIds, long[] calificationTypeIds, long iconImageId) throws SystemException {
		
		if(log.isDebugEnabled()){
			log.debug("::addCourseType:: companyId :: " + companyId);
			log.debug("::addCourseType:: userId :: " + userId);
			log.debug("::addCourseType:: groupId :: " + groupId);
			log.debug("::addCourseType:: nameMap :: " + nameMap);
			log.debug("::addCourseType:: descriptionMap :: " + descriptionMap);
			log.debug("::addCourseType:: templateIds :: " + templateIds.length);
			log.debug("::addCourseType:: courseEvalTypeIds :: " + courseEvalTypeIds.length);
			log.debug("::addCourseType:: learningActivityTypeIds :: " + learningActivityTypeIds.length);
			log.debug("::addCourseType:: inscriptionTypeIds :: " + inscriptionTypeIds.length);
			log.debug("::addCourseType:: calificationTypeIds :: " + calificationTypeIds.length);
			log.debug("::addCourseType:: iconImageId :: " + iconImageId);
		}
		
		//PK Field	
		CourseType courseType = courseTypePersistence.create(counterLocalService.increment(CourseType.class.getName()));
		//Audit fields
		courseType.setCompanyId(companyId);
		courseType.setUserId(userId);
		courseType.setGroupId(groupId);
		courseType.setUserName((Validator.isNotNull(UserLocalServiceUtil.fetchUser(userId)))?UserLocalServiceUtil.fetchUser(userId).getScreenName():StringPool.BLANK);
		courseType.setCreateDate(new Date());
		courseType.setModifiedDate(new Date());
		//Description fields
		courseType.setNameMap(nameMap);
		courseType.setDescriptionMap(descriptionMap);
		
		//El icono se setea sólo si se sube imagen (no es un campo obligatorio)
		if(Validator.isNotNull(iconImageId) && iconImageId > 0)
			courseType.setIconId(iconImageId);
		
		courseTypePersistence.update(courseType, Boolean.TRUE);
		
		//Añadir plantillas de site
		if(Validator.isNotNull(templateIds)  && templateIds.length>0)
			courseTypeTemplateLocalService.addListCourseTypeTemplates(courseType.getCourseTypeId(), templateIds);
		//Añadir métodos de evaluación
		if(Validator.isNotNull(courseEvalTypeIds)  && courseEvalTypeIds.length>0)
			courseTypeCourseEvalLocalService.addListCourseTypeCourseEvals(courseType.getCourseTypeId(), courseEvalTypeIds);
		//Añadir tipos de actividad
		if(Validator.isNotNull(learningActivityTypeIds)  && learningActivityTypeIds.length>0)
			courseTypeLearningActivityLocalService.addListCourseTypeLearningActivities(courseType.getCourseTypeId(), learningActivityTypeIds);
		//Añadir tipos de inscripción
		if(Validator.isNotNull(inscriptionTypeIds)  && inscriptionTypeIds.length>0)
			courseTypeInscriptionTypeLocalService.addListCourseTypeInscriptionTypes(courseType.getCourseTypeId(), inscriptionTypeIds);
		//Añadir stmas de calificación
		if(Validator.isNotNull(calificationTypeIds)  && calificationTypeIds.length>0)
			courseTypeCalificationTypeLocalService.addListCourseTypeCalificationTypes(courseType.getCourseTypeId(), calificationTypeIds);
		return courseType;
	}
	
	public CourseType updateCourseType(long courseTypeId, Map<Locale, String> nameMap, Map<Locale, String> descriptionMap, long[] templateIds,
			long[] courseEvalTypeIds, long[] learningActivityTypeIds, long[] inscriptionTypeIds, long[] calificationTypeIds, long iconImageId,
			boolean deleteIcon) throws SystemException{
		
		if(log.isDebugEnabled()){
			log.debug("::updateCourseType:: courseTypeId :: " + courseTypeId);
			log.debug("::updateCourseType:: nameMap :: " + nameMap);
			log.debug("::updateCourseType:: descriptionMap :: " + descriptionMap);
			log.debug("::updateCourseType:: nameMap :: " + nameMap);
			log.debug("::updateCourseType:: templateIds :: " + templateIds.length);
			log.debug("::updateCourseType:: courseEvalTypeIds :: " + courseEvalTypeIds.length);
			log.debug("::updateCourseType:: learningActivityTypeIds :: " + learningActivityTypeIds.length);
			log.debug("::updateCourseType:: inscriptionTypeIds :: " + inscriptionTypeIds.length);
			log.debug("::updateCourseType:: calificationTypeIds :: " + calificationTypeIds.length);
			log.debug("::updateCourseType:: iconImageId :: " + iconImageId);
			log.debug("::updateCourseType:: deleteIcon :: " + deleteIcon);
		}
	
		CourseType courseType = courseTypePersistence.fetchByCourseTypeId(courseTypeId);
		
		if(Validator.isNotNull(courseType)){
			
			//Audit field
			courseType.setModifiedDate(new Date());
			
			//Description fields
			courseType.setNameMap(nameMap);
			courseType.setDescriptionMap(descriptionMap);
			
			//Se comprueba si se quiere eliminar el icono
			if(deleteIcon)
				courseType.setIconId(0);
			//Si no se quiere eliminar el icono se setea un nuevo icono sólo si se sube imagen (no es un campo obligatorio)
			else if(Validator.isNotNull(iconImageId) && iconImageId > 0)
				courseType.setIconId(iconImageId);
			
			//Las plantillas, métodos de evaluación, tipos de actividad, de inscripción y stmas de calificación
			//existentes no se borran, se pueden añadir nuevos pero los que tenga no se van a borrar
			
			//Plantillas de site
			if(Validator.isNotNull(templateIds) && templateIds.length>0){
				List<Long> listTemplatesIdsOfCourseType = courseType.getCourseTemplateIds();
				for(int i=0; i<templateIds.length ; i++){
					if(!listTemplatesIdsOfCourseType.contains(templateIds[i]))
						courseTypeTemplateLocalService.addCourseTypeTemplate(courseType.getCourseTypeId(), templateIds[i]);
				}
			}
				
			//Métodos de evaluación
			if(Validator.isNotNull(courseEvalTypeIds)  && courseEvalTypeIds.length>0){
				List<Long> listEvalTypesIdsOfCourseType = courseType.getCourseEvalTypeIds();
				for(int i=0; i<courseEvalTypeIds.length; i++){
					if(!listEvalTypesIdsOfCourseType.contains(courseEvalTypeIds[i]))
						courseTypeCourseEvalLocalService.addCourseTypeCourseEval(courseType.getCourseTypeId(), courseEvalTypeIds[i]);
				}
			}
				
			//Tipos de actividad
			if(Validator.isNotNull(learningActivityTypeIds)  && learningActivityTypeIds.length>0){
				List<Long> listLearningActivityTypeIdsOfCourseType = courseType.getLearningActivityTypeIds();
				for(int i=0; i<learningActivityTypeIds.length; i++){
					if(!listLearningActivityTypeIdsOfCourseType.contains(learningActivityTypeIds[i]))
						courseTypeLearningActivityLocalService.addCourseTypeLearningActivity(courseType.getCourseTypeId(), learningActivityTypeIds[i]);
				}
			}
				
			//Tipos de inscripción
			if(Validator.isNotNull(inscriptionTypeIds)  && inscriptionTypeIds.length>0){
				List<Long> listInscriptionTypesIdsOfCourseType = courseType.getInscriptionTypeIds();
				for(int i=0; i<inscriptionTypeIds.length; i++){
					if(!listInscriptionTypesIdsOfCourseType.contains(inscriptionTypeIds[i]))
						courseTypeInscriptionTypeLocalService.addCourseTypeInscriptionType(courseType.getCourseTypeId(), inscriptionTypeIds[i]);
				}
			}
				
			//Stmas de calificación
			if(Validator.isNotNull(calificationTypeIds)  && calificationTypeIds.length>0){
				List<Long> listCalificationTypesIdsOfCourseType = courseType.getCalificationTypeIds();
				for(int i=0; i<calificationTypeIds.length; i++){
					if(!listCalificationTypesIdsOfCourseType.contains(calificationTypeIds[i]))
						courseTypeCalificationTypeLocalService.addCourseTypeCalificationType(courseType.getCourseTypeId(), calificationTypeIds[i]);
				}
			}
				
			courseTypePersistence.update(courseType, Boolean.TRUE);
		}
		return courseType;
	}
	
	public CourseType deleteCourseType(long courseTypeId) throws SystemException, NoSuchCourseTypeException {
		if(log.isDebugEnabled())
			log.debug("::deleteCourseType:: courseTypeId :: " + courseTypeId);
		
		CourseType courseType = courseTypePersistence.fetchByCourseTypeId(courseTypeId);
		if(Validator.isNotNull(courseType)){
			//Borrar plantillas de site asociadas
			courseTypeTemplatePersistence.removeByCourseTypeId(courseTypeId);
			//Borrar métodos de evaluación asociados
			courseTypeCourseEvalPersistence.removeByCourseTypeId(courseTypeId);
			//Borrar tipos de actividades asociadas
			courseTypeLearningActivityPersistence.removeByCourseTypeId(courseTypeId);
			//Borrar tipos de inscripción asociados
			courseTypeInscriptionTypePersistence.removeByCourseTypeId(courseTypeId);
			//Borrar stmas de calificación asociados
			courseTypeCalificationTypePersistence.removeByCourseTypeId(courseTypeId);
			//Despues de eliminar todo se elimina el tipo de curso
			courseType = courseTypePersistence.remove(courseTypeId);
		}
		return courseType;
	}
	
}