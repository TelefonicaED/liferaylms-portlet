package com.liferay.lms.views;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.portal.theme.ThemeDisplay;

public class CourseResultView {
	
	public static final int STATUS_NOT_ATTEMPED = 0;
	public static final int STATUS_INCOMPLETE = 1;
	public static final int STATUS_FAILED = 2;
	public static final int STATUS_PASSED = 3;

	private CourseView course;
	private boolean passed;
	private long result;
	private int statusUser;
	
	public CourseResultView(Course course, CourseResult courseResult, ThemeDisplay themeDisplay){
		setCourse(new CourseView(course, themeDisplay));
		if(courseResult != null){
			setPassed(courseResult.isPassed());
			setResult(courseResult.getResult());
		}else{
			setPassed(false);
			setResult(0);
		}
	}
	
	public CourseResultView(CourseView courseView, long result, int statusUser){
		setCourse(courseView);
		setResult(result);
		setStatusUser(statusUser);
		setPassed(statusUser == STATUS_PASSED);
	}
	
	public CourseView getCourse() {
		return course;
	}
	
	public void setCourse(CourseView course) {
		this.course = course;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public long getResult() {
		return result;
	}

	public void setResult(long result) {
		this.result = result;
	}

	public int getStatusUser() {
		return statusUser;
	}

	public void setStatusUser(int statusUser) {
		this.statusUser = statusUser;
	}
}
