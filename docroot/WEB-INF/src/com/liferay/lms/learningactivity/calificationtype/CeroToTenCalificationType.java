package com.liferay.lms.learningactivity.calificationtype;

import java.text.DecimalFormat;
import java.util.Locale;

public class CeroToTenCalificationType extends BaseCalificationType{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public long getTypeId() {
		return 1;
	}

	@Override
	public String getName() {
		return "cerototen_ct";
	}

	@Override
	public String getTitle(Locale locale) {
		return "cerototen_ct.title";
	}

	@Override
	public String getDescription(Locale locale) {
		return "cerototen_ct.description";
	}

	
	@Override
	public String getSuffix() {
		return "/10";
	}
	
	@Override
	public String translate(double result) {
		DecimalFormat df = new DecimalFormat("##.#");
		return df.format(result/10);
	}

}
