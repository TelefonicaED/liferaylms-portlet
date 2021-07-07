package com.liferay.lms.views;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.util.CourseParams;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;


public class CourseStatsView {

	private long courseId;
	private String courseTitle;
	private Date startDate;
	private Date endDate;
	private long registered;
	private long started;
	private long finished;
	private long passed;
	private long failed;
	private long male =0; 
	private long female = 0;
	private Course course;
	private double avgResult = 0.0;
	private long modules;
	private long activities;
	private double avgValoration = 0.0;
	private String closed;
	private DecimalFormat df = new DecimalFormat("#.#");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private int numEditions;
	

	public CourseStatsView(long companyId, long courseId, Locale locale, long teamId, boolean generalStats) {
		this.courseId = courseId;
		
		try 
		{
		this.course = CourseLocalServiceUtil.fetchCourse(courseId);
		this.courseTitle = course.getTitle(locale);
		
		long[] teamIds = null;
		if(teamId > 0){
			teamIds = new long[1];
			teamIds[0] = teamId;
		}
		
		this.registered  = CourseLocalServiceUtil.countStudentsFromCourse(course.getCourseId(), course.getCompanyId(), null, null, null, null, WorkflowConstants.STATUS_ANY, teamIds, false, generalStats, CourseParams.STUDENTS_TYPE_ALL);
		this.started = CourseResultLocalServiceUtil.countStudentsByCourseIdTeamIdStarted(course.getCourseId(), companyId, teamIds, generalStats);
		this.finished = CourseResultLocalServiceUtil.countStudentsByCourseIdTeamIdFinished(course.getCourseId(), companyId, teamIds, generalStats);
		this.passed = CourseResultLocalServiceUtil.countStudentsByCourseIdTeamIdPassed(course.getCourseId(), companyId, teamIds, generalStats);
		this.failed = CourseResultLocalServiceUtil.countStudentsByCourseIdTeamIdFailed(course.getCourseId(), companyId, teamIds, generalStats);
		this.startDate = course.getExecutionStartDate();
		this.endDate = course.getExecutionEndDate();
		
		this.avgValoration = CourseLocalServiceUtil.avgValorationFromCourse( course.getCourseId() , course.getParentCourseId() );
	    this.activities = LearningActivityLocalServiceUtil.countLearningActivitiesOfGroup(course.getGroupCreatedId());
	    
	    
	    this.male = CourseLocalServiceUtil.countStudentsFromCourse(course.getCourseId(), course.getCompanyId(), null, null, null, null, WorkflowConstants.STATUS_ANY, teamIds, false, generalStats, CourseParams.STUDENTS_TYPE_ALL,CourseParams.STUDENTS_GENERE_MALE);
	    this.female = CourseLocalServiceUtil.countStudentsFromCourse(course.getCourseId(), course.getCompanyId(), null, null, null, null, WorkflowConstants.STATUS_ANY, teamIds, false, generalStats, CourseParams.STUDENTS_TYPE_ALL,CourseParams.STUDENTS_GENERE_FEMALE); 

		}catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		
		if(generalStats){
			try {
				this.setNumEditions(CourseLocalServiceUtil.countChildCourses(course.getCourseId()));
				this.modules = ModuleLocalServiceUtil.countByGroupId(course.getGroupCreatedId());
				//this.activities = LearningActivityLocalServiceUtil.countLearningActivitiesOfGroup(course.getGroupCreatedId());
				if(this.finished>0){
					this.avgResult=CourseResultLocalServiceUtil.avgResultByCourseIdStudents(course.getCourseId(), generalStats);
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

	
	public long getMale ( ) {
		return this.male;
	}
	
	public void setMale ( long male) {
		this.male = male;
		
	}

	public long getFemale ( ) {
		return this.female;
	}
	
	public void setFemale ( long female) {
		this.female = female;
		
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public String getStartDateStr() {
		return sdf.format (startDate);
	}
	public void setStartDate(Date startDate) {
		this.startDate= startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getEndDateStr() {
		return sdf.format(endDate);
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	
	public String getAvgValoration() {
		return df.format(avgValoration);
	}

	public void setAvgValoration(double avgValue) {
		this.avgValoration = avgValue;
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

	public int getNumEditions() {
		return numEditions;
	}

	public void setNumEditions(int numEditions) {
		this.numEditions = numEditions;
	}

}
