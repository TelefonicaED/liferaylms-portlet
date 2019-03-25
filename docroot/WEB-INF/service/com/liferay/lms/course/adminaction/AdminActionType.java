package com.liferay.lms.course.adminaction;

import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletResponse;

import com.liferay.lms.model.Course;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.service.ServiceContext;

public interface AdminActionType 
{
	public long getTypeId();
	public String getName(Locale locale);
	public String getHelpMessage(Locale locale);
	public String getPortletId();
	public boolean hasPermission(long userId);
	public String getIcon();
}
