package com.liferay.lms.learningactivity.calificationtype;

import java.util.Locale;

import javax.portlet.PortletResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.ModuleResult;
import com.liferay.portal.kernel.upload.UploadRequest;

public interface CalificationType 
{
	public long getTypeId();
	public String getName();
	public String getTitle(Locale locale);
	public String getDescription(Locale locale);
	public String getSuffix();
	public String getSuffix(long groupId);
	public String translate(Locale locale, double result);
	public String translate(Locale locale, CourseResult result);
	public String translate(Locale locale, ModuleResult result);
	public String translate(Locale locale, LearningActivityResult result);
	public String translate(Locale locale,long groupId, double result);
	public long toBase100(double result);
	public long toBase100(long groupId,double result);
	public long getMinValue(long groupId);
	public long getMaxValue(long groupId);
	public String getExpecificContentPage();
	public String setExtraContent(UploadRequest uploadRequest,PortletResponse portletResponse,Course course);
}
