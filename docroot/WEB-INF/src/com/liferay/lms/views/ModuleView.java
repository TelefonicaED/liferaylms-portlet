package com.liferay.lms.views;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;

public class ModuleView {
	private Module module;
	private Locale locale;
	private List<LearningActivityView> activities;
	
	//Start Date
	private int startYear;
	private int startMonth;
	private int startDay;
	private int startHour;
	private int startMinute;

	//End Date
	private int endYear;
	private int endMonth;
	private int endDay;
	private int endHour;
	private int endMinute;
	
	public ModuleView(Module module, List<LearningActivity> activities,Locale locale,TimeZone timeZone) {
		super();
		this.module = module;
		this.activities = new ArrayList<LearningActivityView>();
		for(LearningActivity la : activities){
			this.activities.add(new LearningActivityView(la,locale,timeZone));
		}
		this.locale = locale;
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(module.getStartDate());
		startDate.setTimeZone(timeZone);
		
		this.startYear = startDate.get(Calendar.YEAR);
		this.startMonth = startDate.get(Calendar.MONTH);
		this.startDay = startDate.get(Calendar.DAY_OF_MONTH);
		this.startHour = startDate.get(Calendar.HOUR_OF_DAY);
		this.startMinute = startDate.get(Calendar.MINUTE);
		
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(module.getEndDate());
		endDate.setTimeZone(timeZone);
		this.endYear = endDate.get(Calendar.YEAR);
		this.endMonth = endDate.get(Calendar.MONTH);
		this.endDay = endDate.get(Calendar.DAY_OF_MONTH);
		this.endHour = endDate.get(Calendar.HOUR_OF_DAY);
		this.endMinute = endDate.get(Calendar.MINUTE);
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public List<LearningActivityView> getActivities() {
		return activities;
	}

	public void setActivities(List<LearningActivityView> activities) {
		this.activities = activities;
	}
	
	public long getModuleId(){
		return module.getModuleId();
	}
	
	public String getModuleName(){
		String name = module.getTitle(locale);
		if(name.length()>59){
			name = name.substring(0,56)+"...";
		}
		return name;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public int getStartDay() {
		return startDay;
	}

	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}

	public int getEndDay() {
		return endDay;
	}

	public void setEndDay(int endDay) {
		this.endDay = endDay;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public int getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}
	
	
}
