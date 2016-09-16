package com.liferay.lms.views;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.portal.theme.ThemeDisplay;

public class CourseResultView {

	private CourseView course;
	private boolean passed;
	private long result;
	
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
}
