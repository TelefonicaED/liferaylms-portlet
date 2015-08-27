package com.liferay.lms.util;

import java.util.Comparator;
import java.util.Locale;

import com.liferay.lms.model.Course;

public class CourseComparator implements Comparator<Course>{
	Locale locale = null;
	
	public CourseComparator(){
		super();
	}
	
	public CourseComparator(Locale locale){
		super();
		this.locale = locale;
	}

	@Override
	public int compare(Course course0, Course course1) {
		if(locale!=null){
			return course0.getTitle(locale).compareTo(course1.getTitle(locale));
		}else{
			return course0.getTitle().compareTo(course1.getTitle());
		}
	}
	
}
