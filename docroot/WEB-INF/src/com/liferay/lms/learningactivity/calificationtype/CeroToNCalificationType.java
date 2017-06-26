package com.liferay.lms.learningactivity.calificationtype;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;

public class CeroToNCalificationType extends BaseCalificationType{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactoryUtil.getLog(CeroToNCalificationType.class);

	@Override
	public long getTypeId() {
		return 3;
	}

	@Override
	public String getName() {
		return "cerototen_ct";
	}

	@Override
	public String getTitle(Locale locale) {
		return "ceroton_ct.title";
	}

	@Override
	public String getDescription(Locale locale) {
		return "ceroton_ct.description";
	}


	@Override
	public String getSuffix() {
		return "";
	}

	@Override
	public String getSuffix(long groupId) {
		try {
			long cerotonvalue = PrefsPropsUtil.getLong(groupId, "ceroton-value",-1);
			if(cerotonvalue>0){
				return "/" + cerotonvalue;
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	private String translate(Locale locale, double result, long N){
		if(N>0){
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
			otherSymbols.setDecimalSeparator('.');
			DecimalFormat df = new DecimalFormat("##.#",otherSymbols);				
			return df.format(result*N/100);
		}else{
			return translate(locale, result);
		}
	}
	
	@Override
	public String translate(Locale locale, double result) {		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
		otherSymbols.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("##.#",otherSymbols);				
		return df.format(result);
	}

	@Override
	public String translate(Locale locale, CourseResult result) {
		long cerotonvalue = -1;
		try {
			cerotonvalue = PrefsPropsUtil.getLong(CourseLocalServiceUtil.fetchCourse(result.getCourseId()).getGroupCreatedId() , "ceroton-value",-1);						
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return translate(locale, (double)result.getResult(), cerotonvalue);
	}

	@Override
	public String translate(Locale locale, ModuleResult result) {		
		long cerotonvalue = -1;
		try {
			cerotonvalue = PrefsPropsUtil.getLong(ModuleLocalServiceUtil.fetchModule(result.getModuleId()).getGroupId() , "ceroton-value",-1);						
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return translate(locale, (double)result.getResult(), cerotonvalue);
	}

	@Override
	public String translate(Locale locale, LearningActivityResult result) {
		long cerotonvalue = -1;
		try {
			cerotonvalue = PrefsPropsUtil.getLong(LearningActivityLocalServiceUtil.fetchLearningActivity(result.getActId()).getGroupId() , "ceroton-value",-1);						
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return translate(locale, (double)result.getResult(), cerotonvalue);
	}

	@Override
	public String translate(Locale locale, long groupId, double result) {
		
		long cerotonvalue = -1;
		try {
			cerotonvalue = PrefsPropsUtil.getLong(groupId, "ceroton-value",-1);						
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return translate(locale, result, cerotonvalue);
		
	}

	@Override
	public long toBase100(double result) {
		return (long) (result);
	}

	@Override
	public long toBase100(long groupId,double result) {
		
		try {
			long cerotonvalue = PrefsPropsUtil.getLong(groupId, "ceroton-value",-1);
			if(cerotonvalue>0){
				return (long) (result * 100 /cerotonvalue);
			}			
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return (long) (result);
		
	}
	
	@Override
	public long getMinValue(long groupId) {
		return 0;
	}

	@Override
	public long getMaxValue(long groupId) {
		try {
			return PrefsPropsUtil.getLong(groupId, "ceroton-value",0);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public String getExpecificContentPage() {
		return "/html/courseadmin/calificationtype/ceroton.jsp";
	}

	@Override
	public String setExtraContent(UploadRequest uploadRequest,PortletResponse portletResponse, Course course) {
		log.debug("*******setExtraContent**********");
		try {
			String value=ParamUtil.getString(uploadRequest, "ceroton-value", "");
			log.debug("****ceroton-value:"+value);
			
			if(Validator.isNull(value)){
				return "ceroton_ct.n-value-required";
			}
			
			try {
				if(Integer.parseInt(value)<= 0){
					return "ceroton_ct.n-value-positive";
				}
			} catch (NumberFormatException nfe){
				return "ceroton_ct.n-value-number";
			}
			
			PortletPreferences prefs= PortalPreferencesLocalServiceUtil.getPreferences(course.getCompanyId(), course.getGroupCreatedId(), PortletKeys.PREFS_OWNER_TYPE_COMPANY);

			if(!prefs.isReadOnly("ceroton-value")){

				prefs.setValue("ceroton-value", value);
				prefs.store();

			}
			

		} catch (Exception e) {
			e.printStackTrace();
			return "ceroton_ct.extra-content-error";
		}
		
		return null;
	}

}
