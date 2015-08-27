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

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.liferay.lms.NoSuchUserCompetenceException;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseCompetence;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.base.CourseResultLocalServiceBaseImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;

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
	
	public CourseResult getByUserAndCourse(long courseId,long userId) throws SystemException
	{
		return courseResultPersistence.fetchByuc(userId, courseId);
	}
	
	public long countByCourseId(long courseId, boolean passed) throws SystemException
	{
		return courseResultPersistence.countByc(courseId, passed);
	}
	
	public long countStudentsByCourseId(Course course, boolean passed) throws SystemException
	{
		
		long res = 0;
		List<User> students = CourseLocalServiceUtil.getStudentsFromCourse(course.getCompanyId(), course.getGroupCreatedId());
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(CourseResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("courseId").eq(course.getCourseId()));
		
		if(Validator.isNotNull(students) && students.size() > 0) {
			Criterion criterion = null;
			for (int i = 0; i < students.size(); i++) {
				if(i==0) {
					criterion = RestrictionsFactoryUtil.like("userId", students.get(i).getUserId());
				} else {
					criterion = RestrictionsFactoryUtil.or(criterion, RestrictionsFactoryUtil.like("userId", students.get(i).getUserId()));
				}
			}
			if(Validator.isNotNull(criterion)) {
				criterion=RestrictionsFactoryUtil.and(criterion,
						RestrictionsFactoryUtil.eq("passed",passed));
				
				consulta.add(criterion);
				
				List<CourseResult> results = (List<CourseResult>)courseResultPersistence.findWithDynamicQuery(consulta);
				if(results!=null && !results.isEmpty()) {
					res = results.size();
				}
			}
		}
		
		return res;
	}
	
	public long countStudentsByCourseId(Course course) throws SystemException
	{
		
		long res = 0;
		List<User> students = CourseLocalServiceUtil.getStudentsFromCourse(course.getCompanyId(), course.getGroupCreatedId());
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(CourseResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("courseId").eq(course.getCourseId()));
		
		if(Validator.isNotNull(students) && students.size() > 0) {
			Criterion criterion = null;
			for (int i = 0; i < students.size(); i++) {
				if(i==0) {
					criterion = RestrictionsFactoryUtil.like("userId", students.get(i).getUserId());
				} else {
					criterion = RestrictionsFactoryUtil.or(criterion, RestrictionsFactoryUtil.like("userId", students.get(i).getUserId()));
				}
			}
			if(Validator.isNotNull(criterion)) {
				consulta.add(criterion);
				List<CourseResult> results = (List<CourseResult>)courseResultPersistence.findWithDynamicQuery(consulta);
				if(results!=null && !results.isEmpty()) {
					res = results.size();
				}
			}
		}
		
		return res;
	}
	
	
	public Double avgResult(long courseId, boolean passed) throws SystemException
	{
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(CourseResult.class, classLoader);
		Criterion criterion=PropertyFactoryUtil.forName("courseId").eq(courseId);
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("passed").eq(passed);
		dq.add(criterion);
		dq.setProjection(ProjectionFactoryUtil.avg("result"));
		return (Double)(learningActivityResultPersistence.findWithDynamicQuery(dq).get(0));
	}
	
	public Double avgResult(long courseId) throws SystemException
	{
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(CourseResult.class, classLoader);
		Criterion criterion=PropertyFactoryUtil.forName("courseId").eq(courseId);
		dq.add(criterion);
		dq.setProjection(ProjectionFactoryUtil.avg("result"));
		return (Double)(learningActivityResultPersistence.findWithDynamicQuery(dq).get(0));
	}
	
	public Double avgStudentsResult(Course course, boolean passed) throws SystemException
	{
		List<User> students = CourseLocalServiceUtil.getStudentsFromCourse(course.getCompanyId(), course.getGroupCreatedId());
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery consulta=DynamicQueryFactoryUtil.forClass(CourseResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("courseId").eq(course.getCourseId()));
		
		if(Validator.isNotNull(students) && students.size() > 0) {
			Criterion criterion = null;
			for (int i = 0; i < students.size(); i++) {
				if(i==0) {
					criterion = RestrictionsFactoryUtil.like("userId", students.get(i).getUserId());
				} else {
					criterion = RestrictionsFactoryUtil.or(criterion, RestrictionsFactoryUtil.like("userId", students.get(i).getUserId()));
				}
			}
			if(Validator.isNotNull(criterion)) {
				criterion=RestrictionsFactoryUtil.and(criterion,
						RestrictionsFactoryUtil.eq("passed",passed));
				consulta.add(criterion);
			}
		}
		consulta.setProjection(ProjectionFactoryUtil.avg("result"));
		return (Double)(learningActivityResultPersistence.findWithDynamicQuery(consulta).get(0));
	}
	
	public CourseResult create(long courseId, long userId) throws SystemException
	{

		CourseResult courseResult=courseResultPersistence.create(counterLocalService.increment(CourseResult.class.getName()));
		courseResult.setUserId(userId);
		courseResult.setCourseId(courseId);
		courseResult.setResult(0);
		courseResult.setPassed(false);
		courseResult.setPassedDate(null);
		courseResultPersistence.update(courseResult, false);

		return courseResult;
	}
	public CourseResult create(long courseId, long userId,Date allowStartDate,Date allowFinishDate) throws SystemException
	{

		CourseResult courseResult=courseResultPersistence.create(counterLocalService.increment(CourseResult.class.getName()));
		courseResult.setUserId(userId);
		courseResult.setCourseId(courseId);
		courseResult.setResult(0);
		courseResult.setPassed(false);
		courseResult.setPassedDate(null);
		courseResult.setAllowStartDate(allowStartDate);
		courseResult.setAllowFinishDate(allowFinishDate);
		courseResultPersistence.update(courseResult, false);

		return courseResult;
	}
	public void update(CourseResult cresult) throws SystemException
	{
		if(cresult.getPassed()){
			List<CourseCompetence> competences = courseCompetencePersistence.findBycourseId(cresult.getCourseId(), false);
		
			for(CourseCompetence cc: competences){
				UserCompetence uc = null;
				try {
					uc = userCompetencePersistence.findByUserIdCompetenceId(cresult.getUserId(), cc.getCompetenceId());
				} catch (NoSuchUserCompetenceException e) {
					uc = null;
				}
				
				if(uc==null){
					UserCompetence userCompetence=userCompetencePersistence.create(counterLocalService.increment(UserCompetence.class.getName()));
					userCompetence.setUserId(cresult.getUserId());
					userCompetence.setCompetenceId(cc.getCompetenceId());
					userCompetence.setCompDate(new Date());
					userCompetence.setCourseId(cc.getCourseId());
					userCompetencePersistence.update(userCompetence, false);
				}
			}	
		}
		
		courseResultPersistence.update(cresult, false);
	}
	
	public void update(ModuleResult mresult) throws PortalException, SystemException
	{
		Module theModule=moduleLocalService.getModule(mresult.getModuleId());
		Course course=coursePersistence.fetchByGroupCreatedId(theModule.getGroupId());
		if(course!=null)
		{
			CourseEvalRegistry cer=new CourseEvalRegistry();
			long courseEvalTypeId=course.getCourseEvalId();
			CourseEval ceval=cer.getCourseEval(courseEvalTypeId);
			CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), mresult.getUserId());
			//.fetchByuc(mresult.getUserId(), course.getCourseId());
			if(courseResult==null)
			{
				courseResult=CourseResultLocalServiceUtil.create(course.getCourseId(),  mresult.getUserId());
				courseResult.setStartDate(mresult.getStartDate());
				CourseResultLocalServiceUtil.update(courseResult);
			}
			else
			{
				courseResult=CourseResultLocalServiceUtil.getByUserAndCourse( course.getCourseId(),  mresult.getUserId());
				if(courseResult.getStartDate()==null)
				{
					courseResult.setStartDate(mresult.getStartDate());
					CourseResultLocalServiceUtil.update(courseResult);
				}
			}
			ceval.updateCourse(course, mresult);
		}
	}
	
	public CourseResult getCourseResultByCourseAndUser(long courseId,long userId) throws SystemException{

		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(CourseResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("courseId").eq(courseId))
				.add(PropertyFactoryUtil.forName("userId").eq(userId));
	
		List<CourseResult> list = (List<CourseResult>)courseResultPersistence.findWithDynamicQuery(consulta);
		
		if(list!=null){
			if(list.size() > 0){
				return list.get(0);
			}
		}
		
		return null;
		
		//return courseResultPersistence.fetchByuc(userId, courseId);
	}
	
	public String translateResult(Locale locale, double result, long groupId){
		String translatedResult = "";
		try {
			Course curso = courseLocalService.getCourseByGroupCreatedId(groupId);
			if(curso != null){
				CalificationType ct = new CalificationTypeRegistry().getCalificationType(curso.getCalificationType());
				translatedResult = ct.translate(locale, result);
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return translatedResult;
	}
	
	public void softInitializeByGroupIdAndUserId(long groupId, long userId) throws SystemException {
		Course course = courseLocalService.getCourseByGroupCreatedId(groupId);
		if (course != null && courseResultLocalService.getByUserAndCourse(course.getCourseId(), userId) == null) {
			courseResultLocalService.create(course.getCourseId(), userId);
		}
	}
}