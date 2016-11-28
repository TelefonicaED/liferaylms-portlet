package com.liferay.lms.views;


public class CourseStatsView {
	

	private long courseId;
	private String courseTitle;
	private long registered;
	private long started;
	private long finished;
	private long passed;
	private long failed;
	
	

	public CourseStatsView(long courseId, String courseTitle) {
		super();
		this.courseId = courseId;
		this.courseTitle = courseTitle;
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

	

}
