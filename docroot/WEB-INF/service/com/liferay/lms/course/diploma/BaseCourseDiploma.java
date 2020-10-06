package com.liferay.lms.course.diploma;

import java.io.Serializable;

import com.liferay.portal.kernel.upload.UploadRequest;


public abstract class BaseCourseDiploma implements CourseDiploma, Serializable {

	@Override
	public String getSpecificDiplomaContent(){
		return "";
	}
	
	@Override
	public String saveDiploma(UploadRequest uploadRequest,Long courseId) throws Exception{
		return "";
	}
	
	@Override
	public String copyCourseDiploma(long oldCourseId, long newCourseId) throws Exception{
		return "";
	}
	
	@Override
	public String getPortletId(){
		return "";
	}
	
	@Override
	public String updateUserDiploma(Long courseResultId){
		return "";
	}
}
