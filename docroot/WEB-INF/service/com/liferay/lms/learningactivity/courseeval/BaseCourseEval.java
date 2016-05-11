package com.liferay.lms.learningactivity.courseeval;

import javax.portlet.PortletResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

public abstract class BaseCourseEval implements CourseEval {

	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"courseadmin" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());

	@Override
	public String getExpecificContentPage() {
		return StringPool.BLANK;
	}

	@Override
	public void setExtraContent(Course course, String actionId, ServiceContext serviceContext)
			throws PortalException, SystemException {
	}

	@Override
	public boolean especificValidations(UploadRequest uploadRequest,
			PortletResponse portletResponse) {
		return true;
	}

	@Override
	public String getPortletId(){
		return PORTLET_ID;
	}
	
	@Override
	public void onOpenCourse(Course course) throws SystemException {
	}
	
	@Override
	public void onCloseCourse(Course course) throws SystemException {
	}

}
