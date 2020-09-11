package com.liferay.lms.scheduler;

import java.util.LinkedHashMap;
import java.util.List;

import com.liferay.lms.learningactivity.TestLearningActivityType;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.util.CourseParams;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;

public class TestActivityFinished implements MessageListener {
	@Override
	public void receive(Message message) {
		//Obtenemos los cursos finalizados en el último día
		try {
			List<Company> companies = CompanyLocalServiceUtil.getCompanies();
			
			List<Course> courses = null;
			
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			params.put(CourseParams.PARAM_SEARCH_PARENT_AND_CHILD_COURSES, true);
			
			List<LearningActivity> activities = null;
			List<LearningActivityResult> activityResults = null;
			LearningActivityTry learningActivityTry = null;
			
			for(Company company: companies){
				try {
					courses = CourseLocalServiceUtil.searchCourses(company.getCompanyId(), null, company.getLocale().getLanguage(), WorkflowConstants.STATUS_ANY, 
							-1, 0, params, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
					
					for(Course course: courses){
						activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfGroupAndType(course.getGroupCreatedId(),(int) TestLearningActivityType.TYPE_ID);
						for(LearningActivity activity: activities){
							if(activity.isImprove()){
								//Pedimos los aprobados sin fecha fin para cerrarlo
								activityResults = LearningActivityResultLocalServiceUtil.getByActIdPassedEndDateNull(activity.getActId(), true);
								for(LearningActivityResult activityResult :activityResults){
									learningActivityTry = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(activityResult.getActId(), activityResult.getUserId());
									if(learningActivityTry != null){
										activityResult.setEndDate(learningActivityTry.getEndDate());
										LearningActivityResultLocalServiceUtil.updateLearningActivityResult(activityResult);
										ModuleResultLocalServiceUtil.update(activityResult);
									}
								}
							}
						}
					}
				} catch (PortalException | SystemException e) {
					e.printStackTrace();
				}
			}
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
	}
}
