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

package com.liferay.lms.model.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import com.liferay.lms.CourseTypeFactoryRegistry;
import com.liferay.lms.course.inscriptiontype.InscriptionType;
import com.liferay.lms.course.inscriptiontype.InscriptionTypeRegistry;
import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseTypeFactory;
import com.liferay.lms.model.CourseTypeRelation;
import com.liferay.lms.service.CourseTypeLocalServiceUtil;
import com.liferay.lms.service.CourseTypeRelationLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

/**
 * The extended model implementation for the CourseType service. Represents a row in the &quot;Lms_CourseType&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.model.CourseType} interface.
 * </p>
 *
 * @author TLS
 */
public class CourseTypeImpl extends CourseTypeBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a course type model instance should use the {@link com.liferay.lms.model.CourseType} interface instead.
	 */
	
	private static Log log = LogFactoryUtil.getLog(CourseTypeImpl.class);
	private CourseTypeFactory courseTypeFactory;
	
	public CourseTypeImpl() {
	}
	
	public boolean hasCourses(){
		return CourseTypeLocalServiceUtil.hasCourses(getCourseTypeId());
	}
	
	public CourseTypeFactory getCourseTypeFactory(){
		if(courseTypeFactory == null && getClassNameId() > 0){
			CourseTypeFactoryRegistry courseTypeFactoryRegistry = new CourseTypeFactoryRegistry();
			courseTypeFactory = courseTypeFactoryRegistry.getCourseTypeFactory(getClassNameId());
		}
		return courseTypeFactory;
	}
	
	public String getInfo(Locale locale) throws SystemException, PortalException{
		String courseTypeInfo = "<ul>";
		courseTypeInfo += "<li>";
		courseTypeInfo += "<strong>" + LanguageUtil.get(locale, "courseadmin.coursetype.course-templates") + "</strong>";
		courseTypeInfo += "<p>";
		for(LayoutSetPrototype template:getTemplates())
			courseTypeInfo += template.getName(locale) + ", ";
		courseTypeInfo = courseTypeInfo.substring(0,courseTypeInfo.length()-2);
		courseTypeInfo += "</p>";
		courseTypeInfo += "</li>";
		courseTypeInfo += "<li>";
		courseTypeInfo += "<strong>" + LanguageUtil.get(locale, "courseadmin.coursetype.course-correction-methods") +  "</strong>";
		courseTypeInfo += "<p>";
		for(CourseEval courseEval:getCourseEvalTypes())
			courseTypeInfo += LanguageUtil.get(locale, courseEval.getName()) + ", ";
		courseTypeInfo = courseTypeInfo.substring(0,courseTypeInfo.length()-2);
		courseTypeInfo += "</p>";
		courseTypeInfo += "</li>";
		courseTypeInfo += "<li>";
		courseTypeInfo += "<strong>" + LanguageUtil.get(locale, "courseadmin.coursetype.lms-activities") +  "</strong>";
		courseTypeInfo += "<p>";
		for(LearningActivityType leaningActivityType:getLearningActivityTypes()){
			courseTypeInfo += LanguageUtil.get(locale, leaningActivityType.getName()) + ", ";
		}
			
		courseTypeInfo = courseTypeInfo.substring(0,courseTypeInfo.length()-2);
		courseTypeInfo += "</p>";
		courseTypeInfo += "</li>";
		courseTypeInfo += "<li>";
		courseTypeInfo += "<strong>" + LanguageUtil.get(locale, "courseadmin.coursetype.inscription-types") + "</strong>";
		courseTypeInfo += "<p>";
		for(InscriptionType inscriptionType:getInscriptionTypes())
			courseTypeInfo += inscriptionType.getTitle(locale) + ", ";
		courseTypeInfo = courseTypeInfo.substring(0,courseTypeInfo.length()-2);
		courseTypeInfo += "</p>";
		courseTypeInfo += "</li>";
		courseTypeInfo += "<li>";
		courseTypeInfo += "<strong>" +  LanguageUtil.get(locale, "courseadmin.coursetype.calification-types") + "</strong>";
		courseTypeInfo += "<p>";
		for(CalificationType calificationType:getCalificationTypes())
			courseTypeInfo += LanguageUtil.get(locale, calificationType.getTitle(locale)) + ", ";
		courseTypeInfo = courseTypeInfo.substring(0,courseTypeInfo.length()-2);
		courseTypeInfo += "</p>";
		courseTypeInfo += "</li>";
		courseTypeInfo+="</ul>";
		return courseTypeInfo;
	}
	
	public String getIconCourseTypeURL(ThemeDisplay themeDisplay) throws PortalException, SystemException{
		String iconCourseTypeURL = StringPool.BLANK;
		long iconId = getIconId();
		if(log.isDebugEnabled())
			log.debug("::getIconCourseTypeURL:: iconId :: " + iconId);
		if(Validator.isNotNull(iconId) && iconId>0){
			FileEntry iconCourseType = DLAppLocalServiceUtil.getFileEntry(getIconId());
			iconCourseTypeURL = Validator.isNotNull(iconCourseType) ? DLUtil.getPreviewURL(iconCourseType, iconCourseType.getFileVersion(), themeDisplay, StringPool.BLANK) : StringPool.BLANK;
		}
		return iconCourseTypeURL;
	}
	
	public List<Long> getCourseTemplateIds() throws SystemException{
		List<Long> courseTemplateIds = null;
		if(getClassNameId() == 0){
			courseTemplateIds = new ArrayList<Long>();
			List<CourseTypeRelation> courseTypeRelations = CourseTypeRelationLocalServiceUtil.getCourseTypeRelations(getCourseTypeId(), PortalUtil.getClassNameId(LayoutSetPrototype.class));
			for (CourseTypeRelation courseTypeTemplate:courseTypeRelations)
				courseTemplateIds.add(courseTypeTemplate.getClassPK());
		}else{
			CourseTypeFactory courseTypeFactory = getCourseTypeFactory();
			if(courseTypeFactory.getTemplates() == null){
				courseTemplateIds = new ArrayList<Long>();
				List<CourseTypeRelation> courseTypeRelations = CourseTypeRelationLocalServiceUtil.getCourseTypeRelations(getCourseTypeId(), PortalUtil.getClassNameId(LayoutSetPrototype.class));
				for (CourseTypeRelation courseTypeTemplate:courseTypeRelations)
					courseTemplateIds.add(courseTypeTemplate.getClassPK());
			}else{
				courseTemplateIds = Arrays.stream(courseTypeFactory.getTemplates()).boxed().collect(Collectors.toList());
			}
		}
		return courseTemplateIds;
	}
	
	public List<Long> getEditionTemplateIds() throws SystemException{
		List<Long> editionTemplateIds = null;
		if(getClassNameId() == 0){
			editionTemplateIds = new ArrayList<Long>();
			List<CourseTypeRelation> courseTypeRelations = CourseTypeRelationLocalServiceUtil.getCourseTypeRelations(getCourseTypeId(), PortalUtil.getClassNameId(Course.class));
			for (CourseTypeRelation editionTemplate:courseTypeRelations){
				editionTemplateIds.add(editionTemplate.getClassPK());
			}
		}else{
			CourseTypeFactory courseTypeFactory = getCourseTypeFactory();
			if(courseTypeFactory.getEditionTemplates() == null){
				editionTemplateIds = new ArrayList<Long>();
				List<CourseTypeRelation> courseTypeRelations = CourseTypeRelationLocalServiceUtil.getCourseTypeRelations(getCourseTypeId(), PortalUtil.getClassNameId(Course.class));
				for (CourseTypeRelation editionTemplate:courseTypeRelations){
					editionTemplateIds.add(editionTemplate.getClassPK());
				}
			}else{
				editionTemplateIds = Arrays.stream(courseTypeFactory.getEditionTemplates()).boxed().collect(Collectors.toList());
			}
		}
		return editionTemplateIds;
	}
	
	public List<LayoutSetPrototype> getTemplates() throws SystemException, PortalException{
		List<LayoutSetPrototype> listTemplates = new ArrayList<LayoutSetPrototype>();
		for (long templateId:getCourseTemplateIds())
			listTemplates.add(LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(templateId));
		return listTemplates;
	}
	
	public List<Long> getCourseEvalTypeIds() throws SystemException{
		List<Long> courseEvalIds = null;
		if(getClassNameId() == 0){
			courseEvalIds = new ArrayList<Long>();
			List<CourseTypeRelation> courseTypeRelations = CourseTypeRelationLocalServiceUtil.getCourseTypeRelations(getCourseTypeId(), PortalUtil.getClassNameId(CourseEval.class));
			for (CourseTypeRelation courseTypeTemplate:courseTypeRelations)
				courseEvalIds.add(Long.valueOf(courseTypeTemplate.getClassPK()));
		}else{
			CourseTypeFactory courseTypeFactory = getCourseTypeFactory();
			if(courseTypeFactory.getCourseEvals() == null){
				courseEvalIds = new ArrayList<Long>();
				List<CourseTypeRelation> courseTypeRelations = CourseTypeRelationLocalServiceUtil.getCourseTypeRelations(getCourseTypeId(), PortalUtil.getClassNameId(CourseEval.class));
				for (CourseTypeRelation courseTypeTemplate:courseTypeRelations)
					courseEvalIds.add(Long.valueOf(courseTypeTemplate.getClassPK()));
			}else{
				courseEvalIds = Arrays.stream(courseTypeFactory.getCourseEvals()).boxed().collect(Collectors.toList());
			}
		}
		return courseEvalIds;
	}
	
	public List<CourseEval> getCourseEvalTypes() throws SystemException{
		List<CourseEval> listCourseEvals = new ArrayList<CourseEval>();
		CourseEvalRegistry cer=new CourseEvalRegistry();
		for(long courseEvalId:getCourseEvalTypeIds())
			listCourseEvals.add(cer.getCourseEval(courseEvalId));
		return listCourseEvals;
	}
	
	public List<Long> getLearningActivityTypeIds() throws SystemException{
		List<Long> learningActivityTypeIds = null;
		if(getClassNameId() == 0){
			learningActivityTypeIds = new ArrayList<Long>();
			List<CourseTypeRelation> courseTypeRelations = CourseTypeRelationLocalServiceUtil.getCourseTypeRelations(getCourseTypeId(), PortalUtil.getClassNameId(LearningActivityType.class));
			for(CourseTypeRelation courseTypeLearningActivity:courseTypeRelations)
				learningActivityTypeIds.add(courseTypeLearningActivity.getClassPK());
		}else{
			CourseTypeFactory courseTypeFactory = getCourseTypeFactory();
			if(courseTypeFactory.getLearningActivities() == null){
				learningActivityTypeIds = new ArrayList<Long>();
				List<CourseTypeRelation> courseTypeRelations = CourseTypeRelationLocalServiceUtil.getCourseTypeRelations(getCourseTypeId(), PortalUtil.getClassNameId(LearningActivityType.class));
				for(CourseTypeRelation courseTypeLearningActivity:courseTypeRelations)
					learningActivityTypeIds.add(courseTypeLearningActivity.getClassPK());
			}else{
				learningActivityTypeIds = Arrays.stream(courseTypeFactory.getLearningActivities()).boxed().collect(Collectors.toList());
			}
		}
		return learningActivityTypeIds;
	}
	
	public List<LearningActivityType> getLearningActivityTypes() throws SystemException{
		List<LearningActivityType> listLearningActivityTypes = new ArrayList<LearningActivityType>();
		LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
		for(long learningActivityTypeId:getLearningActivityTypeIds()){
			if(learningActivityTypeRegistry.getLearningActivityType(learningActivityTypeId)!=null){
				listLearningActivityTypes.add(learningActivityTypeRegistry.getLearningActivityType(learningActivityTypeId));
			}
		}		
		return listLearningActivityTypes;
	}
	
	public List<Long> getInscriptionTypeIds() throws SystemException{
		List<Long> inscriptionTypeIds = null;
		if(getClassNameId() == 0){
			inscriptionTypeIds = new ArrayList<Long>();
			List<CourseTypeRelation> courseTypeRelations = CourseTypeRelationLocalServiceUtil.getCourseTypeRelations(getCourseTypeId(), PortalUtil.getClassNameId(InscriptionType.class));
			for(CourseTypeRelation courseTypeInscriptionType:courseTypeRelations)
				inscriptionTypeIds.add(courseTypeInscriptionType.getClassPK());
		}else{
			CourseTypeFactory courseTypeFactory = getCourseTypeFactory();
			if(courseTypeFactory.getInscriptionTypes() == null){
				inscriptionTypeIds = new ArrayList<Long>();
				List<CourseTypeRelation> courseTypeRelations = CourseTypeRelationLocalServiceUtil.getCourseTypeRelations(getCourseTypeId(), PortalUtil.getClassNameId(InscriptionType.class));
				for(CourseTypeRelation courseTypeInscriptionType:courseTypeRelations)
					inscriptionTypeIds.add(courseTypeInscriptionType.getClassPK());
			}else{
				inscriptionTypeIds = Arrays.stream(courseTypeFactory.getInscriptionTypes()).boxed().collect(Collectors.toList());
			}
		}
		return inscriptionTypeIds;
	}
	
	public List<InscriptionType> getInscriptionTypes() throws SystemException{
		List<InscriptionType> listInscriptionTypes = new ArrayList<InscriptionType>();
		InscriptionTypeRegistry inscription = new InscriptionTypeRegistry();
		for(long inscriptionTypeId:getInscriptionTypeIds())
			listInscriptionTypes.add(inscription.getInscriptionType(inscriptionTypeId));
		return listInscriptionTypes;
	}
	
	public List<Long> getCalificationTypeIds() throws SystemException{
		List<Long> calificationTypeIds = null;
		if(getClassNameId() == 0){
			calificationTypeIds = new ArrayList<Long>();
			List<CourseTypeRelation> courseTypeRelations = CourseTypeRelationLocalServiceUtil.getCourseTypeRelations(getCourseTypeId(), PortalUtil.getClassNameId(CalificationType.class));
			for(CourseTypeRelation courseTypeCalificationType:courseTypeRelations)
				calificationTypeIds.add(courseTypeCalificationType.getClassPK());
		}else{
			CourseTypeFactory courseTypeFactory = getCourseTypeFactory();
			if(courseTypeFactory.getCalificationTypes() == null){
				calificationTypeIds = new ArrayList<Long>();
				List<CourseTypeRelation> courseTypeRelations = CourseTypeRelationLocalServiceUtil.getCourseTypeRelations(getCourseTypeId(), PortalUtil.getClassNameId(CalificationType.class));
				for(CourseTypeRelation courseTypeCalificationType:courseTypeRelations)
					calificationTypeIds.add(courseTypeCalificationType.getClassPK());
			}else{
				calificationTypeIds = Arrays.stream(courseTypeFactory.getCalificationTypes()).boxed().collect(Collectors.toList());
			}
		}
		return calificationTypeIds;
	}
	
	public List<CalificationType> getCalificationTypes() throws SystemException{
		List<CalificationType> listCalificationTypes = new ArrayList<CalificationType>();
		CalificationTypeRegistry calificationType = new CalificationTypeRegistry();
		for(long calificationTypeId:getCalificationTypeIds())
			listCalificationTypes.add(calificationType.getCalificationType(calificationTypeId));
		return listCalificationTypes;
	}

}