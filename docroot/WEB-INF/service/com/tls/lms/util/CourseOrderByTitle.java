package com.tls.lms.util;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class CourseOrderByTitle  extends OrderByComparator {
	
	public static String ORDER_BY_FIELD = "title";
	
	private ThemeDisplay themeDisplay;

	public CourseOrderByTitle(ThemeDisplay themeDisplay){
		this(themeDisplay, false);
	}

	public CourseOrderByTitle(ThemeDisplay themeDisplay, boolean asc){
		this.themeDisplay = themeDisplay;
		_asc = asc;
	}

	@Override
	public int compare(Object o1,Object o2) {

		String lo1 = "";
		String lo2 = "";

		if(o1!=null) lo1 = (String)o1;
		if(o2!=null) lo2 = (String)o2;

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
	public String toString(){
		String orderBy = " IF (ExtractValue(lms_Course.title, '//Title[@language-id=\"[$LANGUAGE$]\"]' )='', ExtractValue(lms_Course.title,  '//root[@default-locale]//Title' ), ExtractValue(lms_Course.title, '//Title[@language-id=\"[$LANGUAGE$]\"]' )) ";
		orderBy += getOrderBy();
		orderBy = StringUtil.replace(orderBy, "[$LANGUAGE$]", themeDisplay.getLanguageId());
		return orderBy;
	}

	private boolean _asc;
}
