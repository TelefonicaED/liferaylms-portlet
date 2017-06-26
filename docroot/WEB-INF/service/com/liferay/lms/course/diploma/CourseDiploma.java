package com.liferay.lms.course.diploma;

import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.service.InvokableService;


public interface CourseDiploma
{
	public String getSpecificDiplomaContent();
	public String saveDiploma(UploadRequest uploadRequest,Long courseId) throws Exception;
	public String getPortletId();
	public String updateUserDiploma(Long courseResultId);
}
