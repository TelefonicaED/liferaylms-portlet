package com.tls.lms.util;

import java.util.Date;

import com.liferay.portal.kernel.util.OrderByComparator;

public class CourseOrderByCreationDate  extends OrderByComparator {
	
	public static String ORDER_BY_FIELD = "creationDate";

	public CourseOrderByCreationDate(){
		this(false);
	}

	public CourseOrderByCreationDate(boolean asc){
		_asc = asc;
	}

	@Override
	public int compare(Object o1,Object o2) {

		Date lo1 = null;
		Date lo2 = null;

		if(o1!=null) lo1 = (Date)o1;
		if(o2!=null) lo2 = (Date)o2;

		return lo1.compareTo(lo2);
	}

	@Override
	public String[] getOrderByFields() {
		String[] orderByFields = new String[1];
		orderByFields[0] = ORDER_BY_FIELD;
		return orderByFields;
	}

	@Override
	public String getOrderBy() {
		if(_asc) return " ASC ";
		else return " DESC ";
	}
	
	@Override
	public String toString() {
		return " Lms_Course.createDate " + getOrderBy();
	}

	private boolean _asc;
}
