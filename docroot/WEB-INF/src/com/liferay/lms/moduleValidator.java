
package com.liferay.lms;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.portlet.PortletRequest;

import com.liferay.lms.model.Module;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

public class moduleValidator {

	public static ArrayList<String> validatemodule(Module module, PortletRequest request) throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		Properties props = new Properties();
		ClassLoader classLoader = moduleValidator.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream("regexp.properties");
		props.load(is);


		//Field title
		if (Validator.isNull(module.getTitle(request.getLocale(), true))) {
			errors.add("module-title-required");
		}
	
		//Field description
	
		/* La descripcion se hace opcional */
		/*
		if(!validateDescription(props, ParamUtil.getString(request, "description"))){
		    errors.add("error");
		}
		
		if (Validator.isNull(module.getDescription(request.getLocale(), true))) {
			errors.add("module-description-required");
		}
		*/
		//Field order
			
		//Date validation
		if (module.getStartDate().after(module.getEndDate())){
			errors.add("module-startDate-before-endDate");			
		}
		
		//File size validation
		if (!validateFileSize(props, module.getIcon())){
			errors.add("error-file-size");			
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
			//System.out.println(" fileMaxSize 1 : " + fileMaxSize+", "+ img.getSize() );
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
