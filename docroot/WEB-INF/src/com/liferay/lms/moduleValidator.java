
package com.liferay.lms;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.portlet.PortletRequest;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

public class moduleValidator {

	public static ArrayList<String> validatemodule(Module module, PortletRequest request) throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		Properties props = new Properties();
		ClassLoader classLoader = moduleValidator.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream("regexp.properties");
		props.load(is);
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		//Field title
		if (Validator.isNull(module.getTitle(request.getLocale(), true))) {
			errors.add("module-title-required");
		}

		//Field order
			
		//Date validation
		if (module.getStartDate().after(module.getEndDate())){
			errors.add("module-startDate-before-endDate");			
		}
		
		//File size validation
		if (!validateFileSize(props, module.getIcon())){
			errors.add("error-file-size");			
		}
		
		
		try {
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
			//moduleStartDate vs courseExecutionStartDate
			if(module.getStartDate().before(course.getExecutionStartDate())){
				errors.add("module-startDate-before-course-startDate");
			}
			
			//moduleEndDate vs courseExecutionEndDate
			if(module.getEndDate().after(course.getExecutionEndDate())){
				errors.add("module-endDate-after-course-endDate");
			}
			
		} catch (SystemException e) {
			e.printStackTrace();
		}
		

		return errors;
	}

	public static boolean validateEditmodule(
		String rowsPerPage, String dateFormat, String datetimeFormat, List errors) {
		boolean valid = true;
		if (Validator.isNull(rowsPerPage)) {
			errors.add("module-rows-per-page-required");
			valid = false;
		} else if (!Validator.isNumber(rowsPerPage)) {
			errors.add("module-rows-per-page-invalid");
			valid = false;
		} else if (Validator.isNull(dateFormat)) {
			errors.add("module-date-format-required");
			valid = false;
		} else if (Validator.isNull(datetimeFormat)) {
			errors.add("module-datetime_format.required");
			valid = false;
		}
		return valid;
	}

	//Field moduleId
	private static boolean validateModuleId(Properties props,String field) {
		boolean valid = true;
		try {
			Double.parseDouble(field);
		} catch (NumberFormatException nfe) {
		    valid = false;
		}
		return valid;
	}
	//Field title
	private static boolean validateTitle(Properties props,String field) {
		boolean valid = true;
		return valid;
	}
	//Field description
	private static boolean validateDescription(Properties props,String field) {
		boolean valid = true;
		return valid;
	}
	//Field order
	private static boolean validateOrder(Properties props,String field) {
		boolean valid = true;
		try {
			Double.parseDouble(field);
		} catch (NumberFormatException nfe) {
		    valid = false;
		}
		return valid;
	}
	//Field file size
	private static boolean validateFileSize(Properties props,File file) {
		boolean valid = true;

		//Comprobar que el tamano del fichero no supere los 5mb
		long size = 5 * 1024 * 1024;
				
		if(file.length() > size){
			valid = false;
		}
		
		return valid;
	}
	//Field file size
	private static boolean validateFileSize(Properties props, long imageId) {
		boolean valid = true;
				
		if(imageId == 0){
			return true;
		}
		
		try {
			
			FileEntry img = DLAppLocalServiceUtil.getFileEntry(imageId);
			
			//Comprobar que el tamano del fichero no supere los 5mb
			//long size = 1024;
			
			long fileMaxSize = Long.parseLong(PrefsPropsUtil.getString(PropsKeys.DL_FILE_MAX_SIZE));
			if(img.getSize() > fileMaxSize){
				valid = false;
			}
			
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return valid;
	}
}
