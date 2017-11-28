package com.liferay.lms.views;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class ActivityStatsView {
	

	private long actId;
	private String actTitle;
	private Date startDate;
	private Date endDate;
	private long started;
	private long finished;
	private long failed;
	private long passed;
	private Double triesPerUser;
	private String avgResult;
	private String passPuntuation;
	private String avgResultWithSuffix;
	private String passPuntuationWithSuffix;
	private long tries;
	private String dependency;
	private String precedence;
	private String type;
	private String mandatory;
	private SimpleDateFormat sdf;
	private DecimalFormat numberFormat;

	public ActivityStatsView(long actId, String actTitle, TimeZone timeZone) {
		super();
		this.actId = actId;
		this.actTitle = actTitle;
		this.sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.sdf.setTimeZone(timeZone);
		this.numberFormat = new DecimalFormat("#.#");
		
	}

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public String getActTitle() {
		return actTitle;
	}

	public void setActTitle(String actTitle) {
		this.actTitle = actTitle;
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

	public Date getEndDate() {
		return endDate;
	}

	public String getEndDateString() {
		return sdf.format(getEndDate());
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public long getFailed() {
		return failed;
	}

	public void setFailed(long failed) {
		this.failed = failed;
	}

	public long getPassed() {
		return passed;
	}

	public void setPassed(long passed) {
		this.passed = passed;
	}

	public String getTriesPerUserString() {
		return numberFormat.format(triesPerUser);
	}

	public void setTriesPerUser(Double triesPerUser) {
		this.triesPerUser = triesPerUser;
	}

	public void setAvgResult(String avgResult) {
		this.avgResult = avgResult;
	}

	public String getAvgResult() {
		return avgResult;
	}
	
	public String getPassPuntuation() {
		return passPuntuation;
	}

	public void setPassPuntuation(String passPuntuation) {
		this.passPuntuation = passPuntuation;
	}

	public long getTries() {
		return tries;
	}

	public void setTries(long tries) {
		this.tries = tries;
	}

	public String getDependency() {
		return dependency;
	}

	public void setDependency(String dependency) {
		this.dependency = dependency;
	}

	public String getPrecedence() {
		return precedence;
	}

	public void setPrecedence(String precedence) {
		this.precedence = precedence;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getPassPuntuationWithSuffix() {
		return passPuntuationWithSuffix;
	}

	public void setPassPuntuationWithSuffix(String passPuntuationWithSuffix) {
		this.passPuntuationWithSuffix = passPuntuationWithSuffix;
	}

	public String getAvgResultWithSuffix() {
		return avgResultWithSuffix;
	}

	public void setAvgResultWithSuffix(String avgResultWithSuffix) {
		this.avgResultWithSuffix = avgResultWithSuffix;
	}
	
	public final static int COLUMN_START_DATE = 1;
	public final static int COLUMN_END_DATE = 2;
	public final static int COLUMN_AVG_ATTEMPT = 3;
	public final static int COLUMN_AVG_SCORE = 4;
	public final static int COLUMN_PASS_SCORE = 5;
	public final static int COLUMN_NUM_TRIES = 6;
	public final static int COLUMN_PRECEDENCE = 7;
	public final static int COLUMN_TYPE = 8;
	public final static int COLUMN_MANDATORY = 9;
	
	public final static int[] COLUMNS_CONFIG = {COLUMN_START_DATE,COLUMN_END_DATE,COLUMN_AVG_ATTEMPT,COLUMN_AVG_SCORE,COLUMN_PASS_SCORE,
		COLUMN_NUM_TRIES,COLUMN_PRECEDENCE,COLUMN_TYPE,COLUMN_MANDATORY};


}

