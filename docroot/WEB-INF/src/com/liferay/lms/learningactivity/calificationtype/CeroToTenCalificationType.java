package com.liferay.lms.learningactivity.calificationtype;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.portlet.PortletResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.ModuleResult;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.UploadRequest;

public class CeroToTenCalificationType extends BaseCalificationType{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactoryUtil.getLog(CeroToTenCalificationType.class);
	
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
	public String translate(Locale locale, double result) {		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
		otherSymbols.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("##.#",otherSymbols);				
		return df.format(result/10);
	}

	@Override
	public String translate(Locale locale, CourseResult result) {
		return translate(locale, result.getResult());
	}

	@Override
	public String translate(Locale locale, ModuleResult result) {
		return translate(locale, result.getResult());
	}

	@Override
	public String translate(Locale locale, LearningActivityResult result) {
		return translate(locale, result.getResult());
	}

	@Override
	public String translate(Locale locale, long groupId, double result) {
		return translate(locale, result);
	}

	@Override
	public long toBase100(double result) {
		return (long) (result*10);
	}

	@Override
	public long getMinValue(long groupId) {
		return 0;
	}

	@Override
	public long getMaxValue(long groupId) {
		return 10;
	}

	@Override
	public String getExpecificContentPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setExtraContent(UploadRequest uploadRequest,
			PortletResponse portletResponse, Course course) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
