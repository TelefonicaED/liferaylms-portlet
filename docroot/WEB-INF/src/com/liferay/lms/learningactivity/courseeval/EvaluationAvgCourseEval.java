package com.liferay.lms.learningactivity.courseeval;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

public class EvaluationAvgCourseEval extends BaseCourseEval {
	
	private static DateFormat _dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:sszzz",Locale.US);
		
	@SuppressWarnings("unchecked")
	private Map<Long,Long> getEvaluations(Course course, Element rootElement) throws SystemException,DocumentException{

		Map<Long,Long> evaluations = new HashMap<Long,Long>();
		
		for (Element evaluation : rootElement.elements("evaluation")) {
			long id = GetterUtil.getLong(evaluation.elementText("id"));
			long weight = GetterUtil.getLong(evaluation.elementText("weight"));
			if((id!=0)&&(weight!=0)){
				evaluations.put(id, weight);
			}
		}
		
		List<Long> actIdsInDatabase = 
				LearningActivityLocalServiceUtil.dynamicQuery(
				DynamicQueryFactoryUtil.forClass(LearningActivity.class)
				//.add(PropertyFactoryUtil.forName("typeId").eq(8))
				.add(PropertyFactoryUtil.forName("actId").in((Collection<Object>)(Collection<?>)evaluations.keySet()))
				.setProjection(ProjectionFactoryUtil.property("actId")));
		
		Iterator<Map.Entry<Long,Long>> evaluationsIterator = evaluations.entrySet().iterator();
		while (evaluationsIterator.hasNext()) {
			if(!actIdsInDatabase.contains(evaluationsIterator.next().getKey())){
				evaluationsIterator.remove();
		    }
		}
		
		return evaluations;
	}
	
	private double calculateMean(double[] values, double[] weights) {
		int i;
		double sumWeight=0;
		for (i = 0; i < weights.length; i++) {
			sumWeight+=weights[i];
		}
		
		double mean=0;
		for (i = 0; i < values.length; i++) {
			mean+=weights[i]*values[i];
		}
		mean/=sumWeight;
		
		//Correction factor
		double correction=0;
		for (i = 0; i < values.length; i++) {
			correction += weights[i] * (values[i] - mean);
		}
		
		return mean + (correction/sumWeight);
	}
	
	private void updateCourseResult(Course course, long passPuntuation,
			Map<Long, Long> evaluations, long userId) throws SystemException {
		double[] values=new double[evaluations.size()];
		double[] weights=new double[evaluations.size()];
		int i=0;
		for(Map.Entry<Long,Long> evaluation:evaluations.entrySet()){
			LearningActivityResult result=LearningActivityResultLocalServiceUtil.getByActIdAndUserId(evaluation.getKey(),userId);
			if(result!=null){
				values[i]=result.getResult();
			}

			weights[i++]=evaluation.getValue();
		}
		
		CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), userId);
		if(courseResult==null){
			courseResult = CourseResultLocalServiceUtil.addCourseResult(0, course.getCourseId(), userId);
		}
		
		if(courseResult.getStartDate() == null){
			courseResult = CourseResultLocalServiceUtil.initializeCourseResult(courseResult);
		}
		
		if(courseResult.getStartDate() == null){
			courseResult.setStartDate(new Date());
		}
		
		courseResult.setResult((long) calculateMean(values,weights));
		boolean passed = courseResult.getResult()>=passPuntuation;

		if((courseResult.getPassedDate()==null)||
				(courseResult.getPassed()!=passed)) {
			courseResult.setPassedDate(new Date());
		}
		courseResult.setPassed(passed);

		CourseResultLocalServiceUtil.update(courseResult);
	}


	@Override
	public void updateCourse(Course course, ModuleResult mresult) throws SystemException 
	{
	}
	
	@Override
	public boolean updateCourse(Course course, long userId) throws SystemException {
		try {
			if((course.getCourseExtraData()==null)&&(course.getCourseExtraData().trim().length()==0)) {
				return false;
			}
			
			Document document=SAXReaderUtil.read(course.getCourseExtraData());
			Element rootElement =document.getRootElement();
			
			long passPuntuation = GetterUtil.getLong(rootElement.elementText("passPuntuation"),-1);
			if(passPuntuation<0){
				return false;
			}
			
			Map<Long,Long> evaluations=getEvaluations(course,rootElement);
			if(evaluations.size()==0){
				return false;
			}
			
			updateCourseResult(course, passPuntuation, evaluations, userId);	
			return true;
		} catch (DocumentException e) {
			throw new SystemException(e);
		}
	}

	@Override
	public boolean updateCourse(Course course) throws SystemException {
		try {
			if((course.getCourseExtraData()==null)&&(course.getCourseExtraData().trim().length()==0)) {
				return false;
			}
			
			Document document=SAXReaderUtil.read(course.getCourseExtraData());
			Element rootElement =document.getRootElement();
			
			long passPuntuation = GetterUtil.getLong(rootElement.elementText("passPuntuation"),-1);
			if(passPuntuation<0){
				return false;
			}
			
			Map<Long,Long> evaluations=getEvaluations(course,rootElement);
			if(evaluations.size()==0){
				return false;
			}
			
			for(User userOfCourse:UserLocalServiceUtil.getGroupUsers(course.getGroupCreatedId())){
				if(!PermissionCheckerFactoryUtil.create(userOfCourse).hasPermission(course.getGroupCreatedId(), "com.liferay.lms.model",course.getGroupCreatedId(), "VIEW_RESULTS")){
					updateCourseResult(course, passPuntuation, evaluations, userOfCourse.getUserId());	
				}				
			}

			return true;
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public String getName() {
		return "courseeval."+getTypeId()+".name";
	}
	
	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, "courseeval."+getTypeId()+".name");
	}

	@Override
	public long getTypeId() {
		return 1;
	}

	@Override
	public boolean getNeedPassAllModules() {
		return false;
	}

	@Override
	public boolean getFailOnCourseCloseAndNotQualificated() {
		return false;
	}

	@Override
	public boolean getNeedPassPuntuation() {
		return true;
	}
	
	@Override
	public long getPassPuntuation(Course course) throws DocumentException {
		if((course.getCourseExtraData()==null)||
		   (course.getCourseExtraData().trim().length()==0))
			return 0;
		
		Element passPuntuation = SAXReaderUtil.read(course.getCourseExtraData()).getRootElement().element("passPuntuation");

		if(passPuntuation==null)
			return 0;
		
		return  GetterUtil.getLong(passPuntuation.getText());	
	}
	
	@Override
	public JSONObject getEvaluationModel(Course course) throws PortalException,
			SystemException, DocumentException, IOException {
		if(Validator.isNull(course.getCourseExtraData())) {
			return JSONFactoryUtil.createJSONObject();
		}
		
		Document document=SAXReaderUtil.read(course.getCourseExtraData());
		Element rootElement =document.getRootElement();
		JSONObject model = JSONFactoryUtil.createJSONObject();
			
		try {
			Element firedDateElement = rootElement.element("firedDate");
			if(firedDateElement!=null){
				Date firedDate =(Date)_dateFormat.parseObject(firedDateElement.getTextTrim());
				model.put("firedDate", firedDate);
			}
		} catch (Throwable e) {
		}	
			
		model.put("passPuntuation", Long.toString(GetterUtil.getLong(rootElement.elementText("passPuntuation"))));
		List<Element> evaluations = rootElement.elements("evaluation");
		if(!evaluations.isEmpty()){
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		    model.put("evaluations", jsonArray);
			for (Element evaluation : evaluations) {
				long id = GetterUtil.getLong(evaluation.elementText("id"));
				if(id!=0){
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					jsonArray.put(jsonObject);
					jsonObject.put("id", id);
					jsonObject.put("weight", GetterUtil.getLong(evaluation.elementText("weight")));
				}
			}
		}
		return model;
	}
	
	@Override
	public void setEvaluationModel(Course course, JSONObject model)
			throws PortalException, SystemException, DocumentException,
			IOException {
		
		Document document = null;
		Element rootElement = null;
		Date firedDate = null;
		if((course.getCourseExtraData()!=null)&&(course.getCourseExtraData().trim().length()!=0)){
			try {
				document=SAXReaderUtil.read(course.getCourseExtraData());
				rootElement =document.getRootElement();
				Element firedDateElement = rootElement.element("firedDate");
				if(firedDateElement!=null){
					firedDate =(Date)_dateFormat.parseObject(firedDateElement.getTextTrim());
				}
			} catch (Throwable e) {
			}	
		}
	
		document = SAXReaderUtil.createDocument();
		rootElement = document.addElement("eval");
		rootElement.addElement("courseEval").setText(EvaluationAvgCourseEval.class.getName());
		if(firedDate!=null){
			rootElement.addElement("firedDate").setText(_dateFormat.format(firedDate));
		}
		
		rootElement.addElement("passPuntuation").setText(Long.toString(model.getLong("passPuntuation")));
		JSONArray jsonArray = model.getJSONArray("evaluations");
		if(jsonArray!=null){		
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject evaluation = jsonArray.getJSONObject(i);
				long id = evaluation.getLong("id");
				if(id!=0){
					Element evaluationElement = rootElement.addElement("evaluation");
					evaluationElement.addElement("id").setText(Long.toString(id));
					evaluationElement.addElement("weight").setText(Long.toString(evaluation.getLong("weight")));
				}
			}
		}
		
		course.setCourseExtraData(document.formattedString());
		
	}

	@Override
	public boolean especificValidations(UploadRequest uploadRequest,
			PortletResponse portletResponse) {
		if(!Validator.isNumber(PropsUtil.get("lms.course.default.evaluations"))) {
			return true;
		}

		PortletRequest portletRequest = (PortletRequest)uploadRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		String numOfEvaluations = uploadRequest.getParameter("numOfEvaluations");
		Long courseId = ParamUtil.getLong(uploadRequest, "courseId");
		
		if (Validator.isNotNull(courseId)) {
			return true;
		}
		
		if(Validator.isNumber(numOfEvaluations)){
			_numOfEvaluations.set(GetterUtil.getLong(numOfEvaluations));
			return true;
		}

		if(Validator.isNull(numOfEvaluations)) {
			SessionErrors.add(portletRequest, "num-of-evaluations-required");
		}
		else {
			SessionErrors.add(portletRequest, "num-of-evaluations-number");
		}

		return false;
	}
	
	@Override
	public void setExtraContent(Course course, String actionId, ServiceContext serviceContext)
			throws PortalException, SystemException {
		if((Validator.isNumber(PropsUtil.get("lms.course.default.evaluations")))&&("ADD_COURSE".equals(actionId))) {
			ServiceContext evaluationServiceContext = ServiceContextFactory.getInstance(serviceContext.getRequest());
			long numOfEvaluations = _numOfEvaluations.get();
			Locale locale = LocaleThreadLocal.getThemeDisplayLocale();
			
			if(locale==null) {
				if(Validator.isNotNull(serviceContext.getLanguageId())){
					locale = LocaleUtil.fromLanguageId(serviceContext.getLanguageId());
				}
				else {
					locale = LocaleUtil.getDefault();
				}
			}
			long[] assetCategoryIds=new long[0];
			evaluationServiceContext.setAssetCategoryIds(assetCategoryIds);
			evaluationServiceContext.setAssetLinkEntryIds(assetCategoryIds);
			String[] emptyArray=new String[0];
			
			evaluationServiceContext.setAssetTagNames(emptyArray);
			evaluationServiceContext.setGroupPermissions(emptyArray);
			evaluationServiceContext.setGuestPermissions(emptyArray);
			evaluationServiceContext.setDeriveDefaultPermissions(true);
		
			
			for(int currentEvaluation=1;currentEvaluation<=numOfEvaluations;currentEvaluation++) {
				
				Map<Locale,String> evaluationTitle = new HashMap<Locale, String>(1);
				evaluationTitle.put(locale, LanguageUtil.format(locale, "evaluation.number", new Object[]{currentEvaluation}));
	
				LearningActivityLocalServiceUtil.addLearningActivity(course.getUserId(), course.getGroupCreatedId(), WorkflowConstants.STATUS_APPROVED, 
						evaluationTitle, evaluationTitle, 8 /* Evaluation */, null, null, 0, 0, 0, 0, null, null, null, 0, 0, evaluationServiceContext);
			}	
		}
	}
	
	@Override
	public String getExpecificContentPage() {
		return (Validator.isNumber(PropsUtil.get("lms.course.default.evaluations")))?
				PortalUtil.getPathContext()+"/html/evaluationAvg/courseeval/edit.jsp":StringPool.BLANK;
	}
	
	private static ThreadLocal<Long> _numOfEvaluations =
			new AutoResetThreadLocal<Long>(
				EvaluationAvgCourseEval.class + "._numOfEvaluations", GetterUtil.DEFAULT_LONG);

}
