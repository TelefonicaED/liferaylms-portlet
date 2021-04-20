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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.liferay.lms.NoSuchCourseTypeException;
import com.liferay.lms.course.inscriptiontype.InscriptionType;
import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseType;
import com.liferay.lms.model.CourseTypeRelation;
import com.liferay.lms.service.base.CourseTypeLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

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
		return courseTypeFinder.countCourses(courseTypeId)>0;
	}
	
	public List<CourseType> getByCompanyId(long companyId) throws SystemException{
		return courseTypePersistence.findByCompanyId(companyId);
	}
	
	public List<CourseType> getByCompanyIdActive(long companyId) throws SystemException{
		return courseTypePersistence.findByCompanyIdActive(companyId, true);
	}
	
	public List<CourseType> getByCompanyId(long companyId, int start, int end) throws SystemException{
		return courseTypePersistence.findByCompanyId(companyId, start, end);
	}
	
	public int countByCompanyId(long companyId) throws SystemException{
		return courseTypePersistence.countByCompanyId(companyId);
	}
	
	public int countByCompanyIdActive(long companyId) throws SystemException{
		return courseTypePersistence.countByCompanyIdActive(companyId,true);
	}
	
	public CourseType getByCourseTypeId(long courseTypeId) throws NoSuchCourseTypeException, SystemException{
		return courseTypePersistence.findByPrimaryKey(courseTypeId);
	}
	
	
	public CourseType addCourseType(long companyId, long userId, long groupId, Map<Locale, String> nameMap, Map<Locale, String> descriptionMap, long[] templateIds,
			long[] editionTemplateIds, long[] courseEvalTypeIds, long[] learningActivityTypeIds, long[] inscriptionTypeIds, long[] calificationTypeIds, 
			long iconImageId) throws SystemException {
		return addCourseType(companyId, userId, groupId, nameMap, descriptionMap, templateIds, editionTemplateIds, courseEvalTypeIds, learningActivityTypeIds, inscriptionTypeIds, calificationTypeIds, iconImageId,0);
	}
	
	public CourseType addCourseType(long companyId, long userId, long groupId, Map<Locale, String> nameMap, Map<Locale, String> descriptionMap, long[] templateIds,
			long[] editionTemplateIds, long[] courseEvalTypeIds, long[] learningActivityTypeIds, long[] inscriptionTypeIds, long[] calificationTypeIds, 
			long iconImageId, long classNameId) throws SystemException {
		
		if(log.isDebugEnabled()){
			log.debug("::addCourseType:: companyId :: " + companyId);
			log.debug("::addCourseType:: userId :: " + userId);
			log.debug("::addCourseType:: groupId :: " + groupId);
			log.debug("::addCourseType:: nameMap :: " + nameMap);
			log.debug("::addCourseType:: descriptionMap :: " + descriptionMap);
			log.debug("::addCourseType:: templateIds :: " + templateIds.length);
			log.debug("::addCourseType:: editionTemplateIds :: " + editionTemplateIds.length);
			log.debug("::addCourseType:: courseEvalTypeIds :: " + courseEvalTypeIds.length);
			log.debug("::addCourseType:: learningActivityTypeIds :: " + learningActivityTypeIds.length);
			log.debug("::addCourseType:: inscriptionTypeIds :: " + inscriptionTypeIds.length);
			log.debug("::addCourseType:: calificationTypeIds :: " + calificationTypeIds.length);
			log.debug("::addCourseType:: iconImageId :: " + iconImageId);
		}
		
		//PK Field
		long courseTypeId = counterLocalService.increment(CourseType.class.getName());
		CourseType courseType = courseTypePersistence.create(courseTypeId);
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
		courseType.setActive(true);
		courseType.setClassNameId(classNameId);
		//El icono se setea sólo si se sube imagen (no es un campo obligatorio)
		if(Validator.isNotNull(iconImageId) && iconImageId > 0)
			courseType.setIconId(iconImageId);
		
		courseTypePersistence.update(courseType, Boolean.TRUE);
		
		//Añadir plantillas de site
		if(Validator.isNotNull(templateIds)  && templateIds.length>0)
			courseTypeRelationLocalService.addCourseTypeRelations(courseType.getCourseTypeId(), PortalUtil.getClassNameId(LayoutSetPrototype.class),templateIds);
		if(Validator.isNotNull(editionTemplateIds)  && editionTemplateIds.length>0){
			courseTypeRelationLocalService.addCourseTypeRelations(courseType.getCourseTypeId(), PortalUtil.getClassNameId(Course.class),editionTemplateIds);
		}
		//Añadir métodos de evaluación
		if(Validator.isNotNull(courseEvalTypeIds)  && courseEvalTypeIds.length>0)
			courseTypeRelationLocalService.addCourseTypeRelations(courseType.getCourseTypeId(), PortalUtil.getClassNameId(CourseEval.class), courseEvalTypeIds);
		//Añadir tipos de actividad
		if(Validator.isNotNull(learningActivityTypeIds)  && learningActivityTypeIds.length>0)
			courseTypeRelationLocalService.addCourseTypeRelations(courseType.getCourseTypeId(), PortalUtil.getClassNameId(LearningActivityType.class), learningActivityTypeIds);
		//Añadir tipos de inscripción
		if(Validator.isNotNull(inscriptionTypeIds)  && inscriptionTypeIds.length>0)
			courseTypeRelationLocalService.addCourseTypeRelations(courseType.getCourseTypeId(), PortalUtil.getClassNameId(InscriptionType.class), inscriptionTypeIds);
		//Añadir stmas de calificación
		if(Validator.isNotNull(calificationTypeIds)  && calificationTypeIds.length>0)
			courseTypeRelationLocalService.addCourseTypeRelations(courseType.getCourseTypeId(), PortalUtil.getClassNameId(CalificationType.class), calificationTypeIds);
		return courseType;
	}
	
	public CourseType updateCourseType(long courseTypeId, Map<Locale, String> nameMap, Map<Locale, String> descriptionMap, long[] templateIds,
			long[] editionTemplateIds, long[] courseEvalTypeIds, long[] learningActivityTypeIds, long[] inscriptionTypeIds, long[] calificationTypeIds, 
			long iconImageId, boolean deleteIcon) throws SystemException{
			return updateCourseType(courseTypeId, nameMap, descriptionMap, templateIds, editionTemplateIds, courseEvalTypeIds, learningActivityTypeIds, inscriptionTypeIds, calificationTypeIds, iconImageId, deleteIcon);
	}
	
	
	public CourseType updateCourseType(long courseTypeId, Map<Locale, String> nameMap, Map<Locale, String> descriptionMap, long[] templateIds,
			long[] editionTemplateIds, long[] courseEvalTypeIds, long[] learningActivityTypeIds, long[] inscriptionTypeIds, long[] calificationTypeIds, 
			long iconImageId, boolean deleteIcon, long classNameId) throws SystemException{
		
		if(log.isDebugEnabled()){
			log.debug("::updateCourseType:: courseTypeId :: " + courseTypeId);
			log.debug("::updateCourseType:: nameMap :: " + nameMap);
			log.debug("::updateCourseType:: descriptionMap :: " + descriptionMap);
			log.debug("::updateCourseType:: nameMap :: " + nameMap);
			log.debug("::updateCourseType:: templateIds :: " + templateIds.length);
			log.debug("::updateCourseType:: editionTemplateIds :: " + editionTemplateIds.length);
			log.debug("::updateCourseType:: courseEvalTypeIds :: " + courseEvalTypeIds.length);
			log.debug("::updateCourseType:: learningActivityTypeIds :: " + learningActivityTypeIds.length);
			log.debug("::updateCourseType:: inscriptionTypeIds :: " + inscriptionTypeIds.length);
			log.debug("::updateCourseType:: calificationTypeIds :: " + calificationTypeIds.length);
			log.debug("::updateCourseType:: iconImageId :: " + iconImageId);
			log.debug("::updateCourseType:: deleteIcon :: " + deleteIcon);
		}
	
		CourseType courseType = courseTypePersistence.fetchByPrimaryKey(courseTypeId);
		
		if(Validator.isNotNull(courseType)){
			
			//Audit field
			courseType.setModifiedDate(new Date());
			
				
			//Se borran todas las relaciones anteriores
			List<CourseTypeRelation> oldRelations = new ArrayList<CourseTypeRelation>();
				
			//Plantillas
			 oldRelations.addAll(courseTypeRelationLocalService.getCourseTypeRelations(courseType.getCourseTypeId(), PortalUtil.getClassNameId(LayoutSetPrototype.class)));
			
			//Plantillas de edicion
			 oldRelations.addAll(courseTypeRelationLocalService.getCourseTypeRelations(courseType.getCourseTypeId(), PortalUtil.getClassNameId(Course.class)));
			     
			 //Metodos de evaluacion
			 oldRelations.addAll(courseTypeRelationLocalService.getCourseTypeRelations(courseType.getCourseTypeId(), PortalUtil.getClassNameId(CourseEval.class)));
					
			 //Inscripciones
			 oldRelations.addAll(courseTypeRelationLocalService.getCourseTypeRelations(courseType.getCourseTypeId(), PortalUtil.getClassNameId(InscriptionType.class)));
					
			 //Actividades
			 oldRelations.addAll(courseTypeRelationLocalService.getCourseTypeRelations(courseType.getCourseTypeId(), PortalUtil.getClassNameId(LearningActivityType.class)));
					
			  //Calificacion
			 oldRelations.addAll(courseTypeRelationLocalService.getCourseTypeRelations(courseType.getCourseTypeId(), PortalUtil.getClassNameId(CalificationType.class)));
					
			 
			 
			 for(CourseTypeRelation oldRelation: oldRelations){
				 courseTypeRelationLocalService.deleteCourseTypeRelation(oldRelation);
			 }
			 
			//Plantillas de site
			if(Validator.isNotNull(templateIds) && templateIds.length>0){
				for(int i=0; i<templateIds.length ; i++){
					courseTypeRelationLocalService.addCourseTypeRelation(courseType.getCourseTypeId(), PortalUtil.getClassNameId(LayoutSetPrototype.class), templateIds[i]);
				}
			}
			
			if(Validator.isNotNull(editionTemplateIds)  && editionTemplateIds.length>0){
				for(int i=0; i<editionTemplateIds.length ; i++){
					courseTypeRelationLocalService.addCourseTypeRelation(courseType.getCourseTypeId(), PortalUtil.getClassNameId(Course.class), editionTemplateIds[i]);
				}
			}
			
			//Métodos de evaluación
			if(Validator.isNotNull(courseEvalTypeIds)  && courseEvalTypeIds.length>0){
				for(int i=0; i<courseEvalTypeIds.length; i++){
					courseTypeRelationLocalService.addCourseTypeRelation(courseType.getCourseTypeId(), PortalUtil.getClassNameId(CourseEval.class), courseEvalTypeIds[i]);
				}
			}
				
			//Tipos de actividad
			if(Validator.isNotNull(learningActivityTypeIds)  && learningActivityTypeIds.length>0){
				for(int i=0; i<learningActivityTypeIds.length; i++){
					courseTypeRelationLocalService.addCourseTypeRelation(courseType.getCourseTypeId(), PortalUtil.getClassNameId(LearningActivityType.class), learningActivityTypeIds[i]);
				}
			}
				
			//Tipos de inscripción
			if(Validator.isNotNull(inscriptionTypeIds)  && inscriptionTypeIds.length>0){
				for(int i=0; i<inscriptionTypeIds.length; i++){
					courseTypeRelationLocalService.addCourseTypeRelation(courseType.getCourseTypeId(), PortalUtil.getClassNameId(InscriptionType.class), inscriptionTypeIds[i]);
				}
			}
				
			//Stmas de calificación
			if(Validator.isNotNull(calificationTypeIds)  && calificationTypeIds.length>0){
				for(int i=0; i<calificationTypeIds.length; i++){
					courseTypeRelationLocalService.addCourseTypeRelation(courseType.getCourseTypeId(), PortalUtil.getClassNameId(CalificationType.class), calificationTypeIds[i]);
				}
			}
		}
		
	
			courseType.setClassNameId(classNameId);
			//Description fields
			courseType.setNameMap(nameMap);
			courseType.setDescriptionMap(descriptionMap);
			
			//Se comprueba si se quiere eliminar el icono
			if(deleteIcon)
				courseType.setIconId(0);
			//Si no se quiere eliminar el icono se setea un nuevo icono sólo si se sube imagen (no es un campo obligatorio)
			else if(Validator.isNotNull(iconImageId) && iconImageId > 0)
				courseType.setIconId(iconImageId);
				
			courseTypePersistence.update(courseType, Boolean.TRUE);
		
		return courseType;
	}
	
	public CourseType updateActive(long courseTypeId, boolean active) throws SystemException{
		CourseType courseType = courseTypePersistence.fetchByPrimaryKey(courseTypeId);
		
		courseType.setModifiedDate(new Date());
		courseType.setActive(active);
		
		return courseTypePersistence.update(courseType, Boolean.TRUE);
	}
	
	public CourseType deleteCourseType(long courseTypeId) throws SystemException, NoSuchCourseTypeException {
		if(log.isDebugEnabled())
			log.debug("::deleteCourseType:: courseTypeId :: " + courseTypeId);
		
		CourseType courseType = courseTypePersistence.fetchByPrimaryKey(courseTypeId);
		if(Validator.isNotNull(courseType)){
			//Borrar plantillas de site asociadas
			courseTypeRelationLocalService.removeCourseTypeRelations(courseTypeId);
			//Despues de eliminar todo se elimina el tipo de curso
			courseType = courseTypePersistence.remove(courseTypeId);
		}
		return courseType;
	}
	
	public List<CourseType> getCourseTypeByClassNameId(long companyId, long classNameId, boolean active) throws SystemException{
		return courseTypePersistence.findByCompanyIdClassNameIdActive(companyId, classNameId, active);
	}
}