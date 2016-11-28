package com.liferay.lms.views;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class ModuleStatsView {
	

	private long moduleId;
	private String moduleTitle;
	private long activityNumber;
	private Date startDate;
	private Date endDate;
	private long started;
	private long finished;
	private String precedence;
	private SimpleDateFormat sdf;
	

	public ModuleStatsView(long moduleId, String moduleTitle, TimeZone timeZone) {
		super();
		this.moduleId = moduleId;
		this.moduleTitle = moduleTitle;
		this.sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.sdf.setTimeZone(timeZone);
		this.precedence = "-";
	}



	public long getModuleId() {
		return moduleId;
	}



	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}



	public String getModuleTitle() {
		return moduleTitle;
	}



	public void setModuleTitle(String moduleTitle) {
		this.moduleTitle = moduleTitle;
	}



	public long getActivityNumber() {
		return activityNumber;
	}



	public void setActivityNumber(long activityNumber) {
		this.activityNumber = activityNumber;
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



	public Date getStartDate() {
		return startDate;
	}


	public String getStartDateString() {
		return sdf.format(getStartDate());
	}
	

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	
	public String getEndDateString() {
		return sdf.format(getEndDate());
	}
	
	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public String getPrecedence() {
		return precedence;
	}



	public void setPrecedence(String precedence) {
		this.precedence = precedence;
	}



	

}
