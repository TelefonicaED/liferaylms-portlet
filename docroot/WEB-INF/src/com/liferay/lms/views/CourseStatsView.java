package com.liferay.lms.views;

import java.text.DecimalFormat;
import java.util.Locale;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;


public class CourseStatsView {
	

	private long courseId;
	private String courseTitle;
	private long registered;
	private long started;
	private long finished;
	private long passed;
	private long failed;
	private Course course;
	private double avgResult = 0.0;
	private long modules;
	private long activities;
	private String closed;
	private DecimalFormat df = new DecimalFormat("#.#");
	
	

	



	public CourseStatsView(long courseId, Locale locale, long teamId, long[] userExcludedIds, long[] userIds, boolean generalStats) {
		super();
		this.courseId = courseId;
		
		try {
			this.course = CourseLocalServiceUtil.fetchCourse(courseId);
			this.courseTitle = course.getTitle(locale);
			if(teamId > 0){
				this.registered  =userIds.length;
				this.started = CourseResultLocalServiceUtil.countStudentsByCourseIdUserIdsStarted(course.getCourseId(), userIds);
				this.started = CourseResultLocalServiceUtil.countStudentsByCourseIdUserIdsFinished(course.getCourseId(), userIds);
				this.passed = CourseResultLocalServiceUtil.countStudentsByCourseIdUserIdsPassed(course.getCourseId(), userIds);
				this.failed = CourseResultLocalServiceUtil.countStudentsByCourseIdUserIdsFailed(course.getCourseId(), userIds);		
			}else{
				this.registered  = CourseLocalServiceUtil.countStudentsStatus(course.getCourseId(), course.getCompanyId(), null, null, null, null, WorkflowConstants.STATUS_ANY, false);
				this.started = CourseResultLocalServiceUtil.countStudentsByCourseIdUserExcludedIdsStarted(course.getCourseId(), userExcludedIds);
				this.finished = CourseResultLocalServiceUtil.countStudentsByCourseIdUserExcludedIdsFinished(course.getCourseId(), userExcludedIds);
				this.passed = CourseResultLocalServiceUtil.countStudentsByCourseIdUserExcludedIdsPassed(course.getCourseId(), userExcludedIds);
				this.failed = CourseResultLocalServiceUtil.countStudentsByCourseIdUserExcludedIdsFailed(course.getCourseId(), userExcludedIds);
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(generalStats){
			try {
				this.modules = ModuleLocalServiceUtil.countByGroupId(course.getGroupCreatedId());
				this.activities = LearningActivityLocalServiceUtil.countLearningActivitiesOfGroup(course.getGroupCreatedId());
				if(this.finished>0){
					this.avgResult=CourseResultLocalServiceUtil.avgResultByCourseIdUserExcludedIds(course.getCourseId(), true, userExcludedIds);
				}
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(course.getClosed()){
				this.closed = LanguageUtil.get(locale, "yes");
			}else{
				this.closed = LanguageUtil.get(locale,"no");
			}
			
		}
	}



	public long getCourseId() {
		return courseId;
	}



	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}



	public String getCourseTitle() {
		return courseTitle;
	}



	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}



	public long getRegistered() {
		return registered;
	}

	public String getCssClosed(){
		String cssClosed ="";
		if(course.getClosed()){
			cssClosed="course-closed";
		}
		return cssClosed;
	}

	public void setRegistered(long registered) {
		this.registered = registered;
	}



	public long getStarted() {
		return started;
	}



	public void setStarted(long started) {
		this.started = started;
	}



	public long getFinished() {
		return finished;
	}



	public void setFinished(long finished) {
		this.finished = finished;
	}



	public long getPassed() {
		return passed;
	}



	public void setPassed(long passed) {
		this.passed = passed;
	}



	public long getFailed() {
		return failed;
	}



	public void setFailed(long failed) {
		this.failed = failed;
	}

	public Course getCourse() {
		return course;
	}



	public void setCourse(Course course) {
		this.course = course;
	}



	public String getAvgResult() {
		return df.format(avgResult);
	}



	public void setAvgResult(double avgResult) {
		this.avgResult = avgResult;
	}



	public long getModules() {
		return modules;
	}



	public void setModules(long modules) {
		this.modules = modules;
	}



	public long getActivities() {
		return activities;
	}



	public void setActivities(long activities) {
		this.activities = activities;
	}



	public String getClosed() {
		return closed;
	}



	public void setClosed(String closed) {
		this.closed = closed;
	}

}
