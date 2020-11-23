package com.liferay.lms.course.adminaction;

import java.io.Serializable;
import java.util.Locale;

import com.liferay.lms.model.Course;

public class BaseAdminActionType implements AdminActionType, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8299999140131518845L;
	public static final long TYPE = 0;
		
	public long getTypeId(){
		return TYPE;
	}
	
	public String getName(Locale locale){
		return "";
	}
	
	public String getHelpMessage(Locale locale){
		return "";
	}
	
	public String getPortletId(){
		return null;
	}
	
	public boolean hasPermission(long userId){
		return true;
	}
	
	public String getIcon(){
		return "";
	}

	@Override
	public boolean showInCourse(Course course, long userId) {
		return true;
	}

	@Override
	public boolean showInEdition(Course course, long userId) {
		return false;
	}
}
