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

public class PonderatedCourseEval extends BaseCourseEval {

	@Override
	public void updateCourse(Course course, ModuleResult mresult) throws SystemException 
	{				
		updateCourse(course, mresult.getUserId());				
	}
	public static long getScore(Course course) throws DocumentException, PortalException, SystemException
	{
		long score=0;
		String extraData=course.getCourseExtraData();
		if(extraData!=null &&extraData.startsWith("<?xml"))
		{
		Document document = SAXReaderUtil.read(extraData);
		Element rootElement=document.getRootElement();
		Element scoreElement=rootElement.element("score");
		if(scoreElement!=null)
		{
			score=Long.valueOf(scoreElement.attributeValue("value"));
		}
		}
		return score;
	}
	public static java.util.List<Long> getRequiredActivities(Course course) throws DocumentException, PortalException, SystemException
	{
		java.util.List<Long> result=new java.util.ArrayList<Long>();
		String extraData=course.getCourseExtraData();
		if(extraData!=null &&extraData.startsWith("<?xml"))
		{
		Document document = SAXReaderUtil.read(extraData);
		Element rootElement=document.getRootElement();
		java.util.List<Element> reqElements=rootElement.elements("required");
		for(Element reqElement:reqElements)
		{
			long actId=Long.parseLong(reqElement.attributeValue("actId"));
			LearningActivity larn=LearningActivityLocalServiceUtil.fetchLearningActivity(actId);
			if(larn!=null&& larn.getGroupId()==course.getGroupCreatedId()&& larn.getWeightinmodule()>0)
			{
				result.add(actId);
			}
		}
		}
		return result;
	}
	public static java.util.Map<Long, Long> getActivitiesWeight(Course course) throws PortalException, SystemException, DocumentException
	{
		java.util.Map<Long, Long> result =new java.util.HashMap<Long, Long>();
		String extraData=course.getCourseExtraData();
		if(extraData!=null &&extraData.startsWith("<?xml"))
		{
		Document document = SAXReaderUtil.read(extraData);
		Element rootElement=document.getRootElement();
		java.util.List<Element> reqElements=rootElement.elements("weight");
		for(Element reqElement:reqElements)
		{
			long actId=Long.parseLong(reqElement.attributeValue("actId"));
			long ponderation=Long.parseLong(reqElement.attributeValue("ponderation"));
			LearningActivity larn=LearningActivityLocalServiceUtil.fetchLearningActivity(actId);
			if(larn!=null&& larn.getGroupId()==course.getGroupCreatedId()&& larn.getWeightinmodule()>0)
			{
				result.put(actId,ponderation);
			}
		}
		}
		return result;
	}
	@Override
	public boolean updateCourse(Course course, long userId) throws SystemException
	{
	
		CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), userId);
		//.fetchByuc(mresult.getUserId(), course.getCourseId());
		if(courseResult==null)
		{
			courseResult=CourseResultLocalServiceUtil.create(course.getCourseId(), userId);

			//auditing
		}
		try 
		{
			java.util.List<Long> required=PonderatedCourseEval.getRequiredActivities(course);

			java.util.Map<Long,Long> weights=PonderatedCourseEval.getActivitiesWeight(course);
			long score=PonderatedCourseEval.getScore(course);
			
			boolean finished=true;
			boolean passed=true;
			long result=0;
			long weight=0;
			List<LearningActivity> learningActivities=LearningActivityLocalServiceUtil.getMandatoryLearningActivitiesOfGroup(course.getGroupCreatedId());
			boolean isFired=false;
			for(LearningActivity activity:learningActivities)
			{
				if(LearningActivityResultLocalServiceUtil.existsLearningActivityResult(activity.getActId(), userId))
				{
					LearningActivityResult learningActivityResult=LearningActivityResultLocalServiceUtil.getByActIdAndUserId(activity.getActId(), userId);
					if(learningActivityResult.getEndDate()!=null)
					{
						if(required.contains(activity.getActId()))
						{
							if(!learningActivityResult.isPassed())
							{
								passed=false;
								isFired=true;
							}
						}
						if(weights.containsKey(activity.getActId()))
						{
							result=result+(learningActivityResult.getResult()*weights.get(activity.getActId()));
							weight+=weights.get(activity.getActId());
						}
					}
					else
					{
						if(required.contains(activity.getActId()))
						{
							passed=false;
						}
						if(weights.containsKey(activity.getActId()))
						{
							weight+=weights.get(activity.getActId());
						}
						finished=false;

					}
				}
				else
				{
					if(weights.containsKey(activity.getActId()))
					{
						weight+=weights.get(activity.getActId());
					}
					passed=false;
					finished=false;
					
				}
			}
			if(learningActivities.size()>0)
			{
				if(result>0&&weight>0)
				{
					result=result/weight;
				}
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
					
					if(finished) 
					{
						if(result<score)
						{
							passed=false;
						}
							
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
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;	
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
		return "ponderated."+getTypeId()+".name";
	}
	
	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, "ponderated."+getTypeId()+".name");
	}

	@Override
	public long getTypeId() {
		return 3;
	}

	@Override
	public boolean getNeedPassAllModules() {
		return false;
	}

	@Override
	public boolean getFailOnCourseCloseAndNotQualificated() {
		return true;
	}

	@Override
	public boolean getNeedPassPuntuation() {
		return true;
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
		rootElement.addElement("courseEval").setText(PonderatedCourseEval.class.getName());		
		course.setCourseExtraData(document.formattedString());
	}


}
