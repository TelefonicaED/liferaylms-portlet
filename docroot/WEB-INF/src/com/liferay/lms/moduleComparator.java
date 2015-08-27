package com.liferay.lms;

import com.liferay.portal.kernel.util.OrderByComparator;


public class moduleComparator {

	public static String ORDER_BY_ASC =  " ASC";
	public static String ORDER_BY_DESC = " DESC";

	public static OrderByComparator getmoduleOrderByComparator(String orderByCol,String orderByType) {

		boolean orderByAsc = false;
		if(orderByType==null) {
			orderByAsc = true;
		} else if (orderByType.equalsIgnoreCase(ORDER_BY_ASC.trim())){
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;
			if(orderByCol==null) {
			orderByComparator = new OrderBymoduleModuleId(orderByAsc);
			} else if(orderByCol.equals("moduleId")){
			orderByComparator = new OrderBymoduleModuleId(orderByAsc);
			} else if(orderByCol.equals("title")){
			orderByComparator = new OrderBymoduleTitle(orderByAsc);
				} else if(orderByCol.equals("order")){
			orderByComparator = new OrderBymoduleOrder(orderByAsc);
	    }
	    return orderByComparator;
	}
}

class OrderBymoduleModuleId extends OrderByComparator {
	public static String ORDER_BY_FIELD = "moduleId";

	public OrderBymoduleModuleId(){
		this(false);
	}

	public OrderBymoduleModuleId(boolean asc){
		_asc = asc;
	}

	@Override
	public int compare(Object o1,Object o2) {

		Long lo1 = 0L;
		Long lo2 = 0L;

		if(o1!=null) lo1 = (Long)o1;
		if(o2!=null) lo2 = (Long)o2;

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
		if(_asc) return moduleComparator.ORDER_BY_ASC;
		else return moduleComparator.ORDER_BY_DESC;
	}

	private boolean _asc;
}

class OrderBymoduleTitle extends OrderByComparator {
	public static String ORDER_BY_FIELD = "title";

	public OrderBymoduleTitle(){
		this(false);
	}

	public OrderBymoduleTitle(boolean asc){
		_asc = asc;
	}

	@Override
	public int compare(Object o1,Object o2) {

		String str1 = "";
		String str2 = "";

		if(str1!=null) str1 = (String)o1;
		if(str2!=null) str2 = (String)o1;

		return str1.compareTo(str2);
	}

	@Override
	public String[] getOrderByFields() {
		String[] orderByFields = new String[1];
		orderByFields[0] = ORDER_BY_FIELD;
		return orderByFields;
	}

	@Override
	public String getOrderBy() {
		if(_asc) return moduleComparator.ORDER_BY_ASC;
		else return moduleComparator.ORDER_BY_DESC;
	}

	private boolean _asc;
}
class OrderBymoduleOrder extends OrderByComparator {
	public static String ORDER_BY_FIELD = "order";

	public OrderBymoduleOrder(){
		this(false);
	}

	public OrderBymoduleOrder(boolean asc){
		_asc = asc;
	}

	@Override
	public int compare(Object o1,Object o2) {

		Long lo1 = 0L;
		Long lo2 = 0L;

		if(o1!=null) lo1 = (Long)o1;
		if(o2!=null) lo2 = (Long)o2;

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
		if(_asc) return moduleComparator.ORDER_BY_ASC;
		else return moduleComparator.ORDER_BY_DESC;
	}

	private boolean _asc;
}




