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
import com.liferay.lms.service.persistence.CourseFinderUtil;
import com.liferay.lms.service.persistence.CourseResultFinder;
import com.liferay.lms.service.persistence.CourseResultFinderUtil;
import com.liferay.lms.service.persistence.CourseResultUtil;
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
	
	public List<CourseResult> getByUserId(long userId){
		List<CourseResult> results = new ArrayList<CourseResult>();
		try {
			results = CourseResultUtil.findByUserId(userId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public CourseResult getByUserAndCourse(long courseId,long userId) throws SystemException{
		return CourseResultUtil.fetchByuc(userId, courseId);
	}
	
	public long countByCourseId(long courseId, boolean passed) throws SystemException{
		return CourseResultUtil.countByc(courseId, passed);
	}
	
	public long countByUserId(long userId) throws SystemException{
		return CourseResultUtil.countByUserId(userId);
	}
	
	
	public long countStudentsStartedByCourseId(Course course, List<User> students, long teamId){
		return CourseResultFinderUtil.countStartedOnlyStudents(course.getCourseId(), course.getCompanyId(), course.getGroupCreatedId(), students, teamId);
	}
	
	public long countStudentsFinishedByCourseId(Course course, List<User> students, long teamId){
		return CourseResultFinderUtil.countFinishedOnlyStudents(course.getCourseId(), course.getCompanyId(), course.getGroupCreatedId(), students, teamId);
	}
	
	public long countStudentsPassedByCourseId(Course course, List<User> students, long teamId){
		return CourseResultFinderUtil.countFinishedPassedOnlyStudents(course.getCourseId(), course.getCompanyId(), course.getGroupCreatedId(), students, true, teamId);
	}
	
	public long countStudentsFailedByCourseId(Course course, List<User> students, long teamId){
		return CourseResultFinderUtil.countFinishedPassedOnlyStudents(course.getCourseId(), course.getCompanyId(), course.getGroupCreatedId(), students, false, teamId);
	}
	
	/**
	 * @deprecated Renamed to {@link #countStudentsPassedByCourseId} or  {@link #countStudentsFailedByCourseId}
	 */
	public long countStudentsByCourseId(Course course, boolean passed) throws SystemException{
		return countStudentsByCourseId(course, null, passed);
	}
	
	/**
	 * @deprecated Renamed to {@link #countStudentsPassedByCourseId} or  {@link #countStudentsFailedByCourseId}
	 */
	public long countStudentsByCourseId(Course course, List<User> students, boolean passed) throws SystemException {
		
		return CourseResultFinderUtil.countFinishedPassedOnlyStudents(course.getCourseId(), course.getCompanyId(), course.getGroupCreatedId(), students, passed, 0);
	}
	
	

	/**
	 * @deprecated Renamed to {@link #countStartedOnlyStudents} 
	 */
	public long countStudentsByCourseId(Course course) throws SystemException
	{
		return countStudentsByCourseId(course, null);
	}
	
	/**
	 * @deprecated Renamed to {@link #countStartedOnlyStudents} 
	 */
	public long countStudentsByCourseId(Course course, List<User> students) throws SystemException
	{
		return CourseResultFinderUtil.countStartedOnlyStudents(course.getCourseId(), course.getCompanyId(), course.getGroupCreatedId(), students, 0);
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
	
	/**
	 * @deprecated Renamed to {@link #avgPassedStudentsResult} 
	 */
	public Double avgStudentsResult(Course course, boolean passed) throws SystemException
	{
		return avgStudentsResult(course, null, passed);
	}

	/**
	 * @deprecated Renamed to {@link #avgPassedStudentsResult} 
	 */
	public Double avgStudentsResult(Course course, List<User> students, boolean passed) throws SystemException
	{
		return CourseResultFinderUtil.avgFinishedOnlyStudents(course.getCourseId(), course.getCompanyId(), course.getGroupCreatedId(), students, passed, 0);
	}
	
	public Double avgPassedStudentsResult(Course course, List<User> students, boolean passed, long teamId) throws SystemException
	{
		return CourseResultFinderUtil.avgFinishedOnlyStudents(course.getCourseId(), course.getCompanyId(), course.getGroupCreatedId(), students, passed, teamId);
	}
	
	public CourseResult create(long courseId, long userId) throws SystemException
	{

		CourseResult courseResult=courseResultPersistence.create(counterLocalService.increment(CourseResult.class.getName()));
		courseResult.setUserId(userId);
		courseResult.setCourseId(courseId);
		courseResult.setResult(0);
		courseResult.setPassed(false);
		courseResult.setPassedDate(null);
		courseResult.setStartDate(new Date());
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
		courseResult.setStartDate(new Date());
		courseResultPersistence.update(courseResult, false);

		return courseResult;
	}
	
	public void update(CourseResult cresult) throws SystemException{
		if(cresult.getPassed()){
			List<CourseCompetence> competences = courseCompetencePersistence.findBycourseId(cresult.getCourseId(), false);
		
			for(CourseCompetence cc: competences){
				UserCompetence uc = userCompetencePersistence.fetchByUserIdCompetenceId(cresult.getUserId(), cc.getCompetenceId());
								
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
	
	public CourseResult getCourseResultByCourseAndUser(long courseId,long userId) throws SystemException{

		return CourseResultUtil.fetchByuc(userId, courseId);
	}
	
	public String translateResult(Locale locale, double result, long groupId){
		String translatedResult = "";
		try {
			Course curso = courseLocalService.getCourseByGroupCreatedId(groupId);
			if(curso != null){
				CalificationType ct = new CalificationTypeRegistry().getCalificationType(curso.getCalificationType());
				translatedResult = ct.translate(locale,result);
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