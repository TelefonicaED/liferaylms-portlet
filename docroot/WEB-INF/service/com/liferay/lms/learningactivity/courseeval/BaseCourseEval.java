package com.liferay.lms.learningactivity.courseeval;

import java.util.HashMap;
import java.util.List;

import javax.portlet.PortletResponse;

import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
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
	public long calculateModuleResult(long moduleId,long userId) throws SystemException, PortalException
	{
		return 0;
	}
	public boolean hasModuleResultCalculator()
	{
		return false;
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
		List<LearningActivity> activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfGroup(course.getGroupCreatedId());
		LearningActivityTypeRegistry activityTypeRegistry = new LearningActivityTypeRegistry();
		LearningActivityType learningActivityType = null;
		for(LearningActivity activity: activities){
			learningActivityType = activityTypeRegistry.getLearningActivityType(activity.getTypeId());
			try {
				learningActivityType.onCloseCourse(activity);
			} catch (PortalException e) {
				e.printStackTrace();
			}
		}
	}
	
    @Override    
    public void cloneCourseEval(Course course, Course newCourse, HashMap<Long, Long> correlationModules,
        HashMap<Long, Long> correlationActivities) throws SystemException
    {
    }

    @Override    
    public void cloneCourseEval(Course course, Course newCourse) throws SystemException
    {
    }
}
