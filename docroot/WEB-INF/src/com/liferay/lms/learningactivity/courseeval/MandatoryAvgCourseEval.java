package com.liferay.lms.learningactivity.courseeval;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

public class MandatoryAvgCourseEval extends BaseCourseEval {

	@Override
	public void updateCourse(Course course, ModuleResult mresult) throws SystemException 
	{				
		updateCourse(course, mresult.getUserId());				
	}
	
	@Override
	public boolean updateCourse(Course course, long userId) throws SystemException {
		
			CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), userId);
			//.fetchByuc(mresult.getUserId(), course.getCourseId());
			if(courseResult==null)
			{
				courseResult=CourseResultLocalServiceUtil.create(course.getCourseId(), userId);

				//auditing
				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), CourseResult.class.getName(), courseResult.getPrimaryKey(), userId, AuditConstants.CREATE, null);
			}
			
			List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId());
			boolean passed=true;
			long result=0;
			List<LearningActivity> learningActivities=LearningActivityLocalServiceUtil.getMandatoryLearningActivitiesOfGroup(course.getGroupCreatedId());
			boolean isFired=false;
			for(LearningActivity activity:learningActivities)
			{
				if(LearningActivityResultLocalServiceUtil.existsLearningActivityResult(activity.getActId(), userId))
				{
					LearningActivityResult learningActivityResult=LearningActivityResultLocalServiceUtil.getByActIdAndUserId(activity.getActId(), userId);
					if(learningActivityResult.getEndDate()!=null&&!learningActivityResult.isPassed())
					{
						isFired=true;
					}
					else
					{
						if(learningActivityResult.getEndDate()==null)
						{
							passed=false;
						}
					}
					result+=learningActivityResult.getResult();
				}
				else
				{
					passed=false;
				}
			}
			if(learningActivities.size()>0)
			{
				result=result/learningActivities.size();
			}
				if(isFired)
				{
					if(courseResult.getPassedDate()==null)
					{
						courseResult.setPassed(false);
						courseResult.setPassedDate(new Date());
					}
					courseResult.setResult(result);
					CourseResultLocalServiceUtil.update(courseResult);
					return true;
				}
				else
				{
					if(passed) 
					{
						if(courseResult.getPassedDate()==null)
						{
							courseResult.setPassedDate(new Date());
							courseResult.setPassed(passed);
						}
						else
						{
							if(!courseResult.getPassed())
							{
								courseResult.setPassedDate(new Date());
								courseResult.setPassed(passed);
							}
						}
					
					}
					else
					{
						courseResult.setPassedDate(null);
						courseResult.setPassed(false);
					}
					courseResult.setResult(result);
					CourseResultLocalServiceUtil.update(courseResult);
					return true;
				}
	}

	@Override
	public boolean updateCourse(Course course) throws SystemException {
		try {
			for(User userOfCourse:UserLocalServiceUtil.getGroupUsers(course.getGroupCreatedId())){
				if(!PermissionCheckerFactoryUtil.create(userOfCourse).hasPermission(course.getGroupCreatedId(), "com.liferay.lms.model",course.getGroupCreatedId(), "VIEW_RESULTS")){
					updateCourse(course,  userOfCourse.getUserId());	
				}				
			}

			return true;
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public String getName() {
		return "mandatoryavg."+getTypeId()+".name";
	}
	
	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, "mandatoryavg."+getTypeId()+".name");
	}

	@Override
	public long getTypeId() {
		return 2;
	}

	@Override
	public boolean getNeedPassAllModules() {
		return true;
	}

	@Override
	public boolean getFailOnCourseCloseAndNotQualificated() {
		return true;
	}

	@Override
	public boolean getNeedPassPuntuation() {
		return false;
	}
	
	@Override
	public long getPassPuntuation(Course course) throws DocumentException {
		throw new RuntimeException();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onOpenCourse(Course course) throws SystemException {
		for(CourseResult courseResult:
			(List<CourseResult>)CourseResultLocalServiceUtil.dynamicQuery(
				CourseResultLocalServiceUtil.dynamicQuery().
					add(PropertyFactoryUtil.forName("courseId").eq(course.getCourseId())).
					add(PropertyFactoryUtil.forName("passed").eq(false)))){
			courseResult.setPassedDate(null);
			CourseResultLocalServiceUtil.update(courseResult);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onCloseCourse(Course course) throws SystemException {
		for(CourseResult courseResult:
			(List<CourseResult>)CourseResultLocalServiceUtil.dynamicQuery(
				CourseResultLocalServiceUtil.dynamicQuery().
					add(PropertyFactoryUtil.forName("courseId").eq(course.getCourseId())).
					add(PropertyFactoryUtil.forName("passedDate").isNull()))){
			courseResult.setPassedDate(course.getModifiedDate());
			CourseResultLocalServiceUtil.update(courseResult);
		}
	}
	
	@Override
	public JSONObject getEvaluationModel(Course course) throws PortalException,
			SystemException, DocumentException, IOException {
		return JSONFactoryUtil.createJSONObject();
	}

	@Override
	public void setEvaluationModel(Course course, JSONObject model)
			throws PortalException, SystemException, DocumentException,
			IOException {	
		Document document = SAXReaderUtil.createDocument();
		Element rootElement = document.addElement("eval");
		rootElement.addElement("courseEval").setText(CompleteModulesCourseEval.class.getName());		
		course.setCourseExtraData(document.formattedString());
	}


}
