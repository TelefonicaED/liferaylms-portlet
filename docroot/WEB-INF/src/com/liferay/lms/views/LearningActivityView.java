package com.liferay.lms.views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;

public class LearningActivityView {
	

	private SimpleDateFormat sdfP2p = null;
	
	LearningActivity la = null;
	Locale locale = null;
	TimeZone timeZone = null;
	
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
	boolean p2pActivity = false;
	
	//Date Upload
	private int uploadYear;
	private int uploadMonth;
	private int uploadDay;
	private int uploadHour;
	private int uploadMinute;

	public LearningActivityView(LearningActivity la,Locale locale,TimeZone timeZone) {
		super();
		this.la = la;
		this.locale = locale;
		this.timeZone = timeZone;
		sdfP2p =  new SimpleDateFormat("yyyy-MM-dd HH:mm",locale);
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(la.getStartdate());
		startDate.setTimeZone(timeZone);
		this.startYear = startDate.get(Calendar.YEAR);
		this.startMonth = startDate.get(Calendar.MONTH);
		this.startDay = startDate.get(Calendar.DAY_OF_MONTH);
		this.startHour = startDate.get(Calendar.HOUR_OF_DAY);
		this.startMinute = startDate.get(Calendar.MINUTE);
		
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(la.getEnddate());
		endDate.setTimeZone(timeZone);
		this.endYear = endDate.get(Calendar.YEAR);
		this.endMonth = endDate.get(Calendar.MONTH);
		this.endDay = endDate.get(Calendar.DAY_OF_MONTH);
		this.endHour = endDate.get(Calendar.HOUR_OF_DAY);
		this.endMinute = endDate.get(Calendar.MINUTE);
		
		if(la.getTypeId()==3){
			this.p2pActivity=true;
			try {
				Calendar uploadDate = Calendar.getInstance();
				String sdate = LearningActivityLocalServiceUtil.getExtraContentValue(la.getActId(), "dateupload");
				Date date =  sdfP2p.parse(sdate);
				uploadDate.setTime(date);
				uploadDate.setTimeZone(timeZone);
				this.uploadYear = uploadDate.get(Calendar.YEAR);
				this.uploadMonth = uploadDate.get(Calendar.MONTH);
				this.uploadDay = uploadDate.get(Calendar.DAY_OF_MONTH);
				this.uploadHour = uploadDate.get(Calendar.HOUR_OF_DAY);
				this.uploadMinute = uploadDate.get(Calendar.MINUTE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public LearningActivity getLa() {
		return la;
	}

	public void setLa(LearningActivity la) {
		this.la = la;
	}
	
	public String getTitle(){
		String title = la.getTitle(locale);
		if(title.length()>63){
			title = title.substring(0,60)+"...";
		}
		return title;
	}

	public long getActId(){
		return la.getActId();
	}
	
	public boolean getMandatory(){
		if(la.getWeightinmodule()==1){
			return true;
		}else{
			return false;
		}
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

	public boolean isP2pActivity() {
		return p2pActivity;
	}

	public void setP2pActivity(boolean p2pActivity) {
		this.p2pActivity = p2pActivity;
	}

	public int getUploadYear() {
		return uploadYear;
	}

	public void setUploadYear(int uploadYear) {
		this.uploadYear = uploadYear;
	}

	public int getUploadMonth() {
		return uploadMonth;
	}

	public void setUploadMonth(int uploadMonth) {
		this.uploadMonth = uploadMonth;
	}

	public int getUploadDay() {
		return uploadDay;
	}

	public void setUploadDay(int uploadDay) {
		this.uploadDay = uploadDay;
	}

	public int getUploadHour() {
		return uploadHour;
	}

	public void setUploadHour(int uploadHour) {
		this.uploadHour = uploadHour;
	}

	public int getUploadMinute() {
		return uploadMinute;
	}

	public void setUploadMinute(int uploadMinute) {
		this.uploadMinute = uploadMinute;
	}
	
	
	

	


}
