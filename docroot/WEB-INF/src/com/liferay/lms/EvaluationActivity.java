package com.liferay.lms;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.learningactivity.TaskEvaluationLearningActivityType;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;


/**
 * Portlet implementation class EvaluationActivity
 */
public class EvaluationActivity extends MVCPortlet implements MessageListener{
	
	private static Log log = LogFactoryUtil.getLog(EvaluationActivity.class);
	
	private static DateFormat _dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:sszzz",Locale.US);
	
	public static final String LEARNING_ACTIVITY_RESULT_PASSED_SQL = "WHERE (EXISTS (SELECT 1 FROM lms_learningactivityresult " +
			"WHERE User_.userId = lms_learningactivityresult.userId " +
			" AND lms_learningactivityresult.endData IS NOT NULL AND lms_learningactivityresult.passed > 0 AND lms_learningactivityresult.actId = ? ))"; 

	public static final String LEARNING_ACTIVITY_RESULT_FAIL_SQL = "WHERE (EXISTS (SELECT 1 FROM lms_learningactivityresult " +
			"WHERE User_.userId = lms_learningactivityresult.userId " +
			" AND lms_learningactivityresult.endDate IS NOT NULL AND lms_learningactivityresult.passed = 0 AND lms_learningactivityresult.actId = ? ))"; 

	public static final String LEARNING_ACTIVITY_RESULT_NO_CALIFICATION_SQL = "WHERE (NOT EXISTS (SELECT 1 FROM lms_learningactivityresult " +
			"WHERE User_.userId = lms_learningactivityresult.userId AND lms_learningactivityresult.actId = ? AND lms_learningactivityresult.endDate IS NOT NULL))"; 
	
	@Override
	public void receive(Message message) throws MessageListenerException {
		long actId = message.getLong("actId");
		if(actId!=0){
			try {
				Element publishdDateElement = null;
				try{
					LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
					Document document=SAXReaderUtil.read(learningActivity.getExtracontent());
					Element rootElement =document.getRootElement();
					publishdDateElement = rootElement.element("publishDate");
				}catch(Exception e){
					e.printStackTrace();
				}
				if(publishdDateElement == null){
					evaluate(actId, false);
				}
			} catch (Exception e) {
				log.error("Error during evaluation: "+actId, e);
			}
		}
		else{
			// Scheduler trigger this execution. We must evaluate all activities.
			log.debug("## Running EvaluationActivity cron ##");
			try {
				
				List<LearningActivity> learningActivities = LearningActivityLocalServiceUtil.getLearningActivitiesByType((int)TaskEvaluationLearningActivityType.TYPE_ID);
				Element publishdDateElement = null;
				for (LearningActivity learningActivity : learningActivities){
					try {
						try{
							Document document=SAXReaderUtil.read(learningActivity.getExtracontent());
							Element rootElement =document.getRootElement();
							publishdDateElement = rootElement.element("publishDate");
						}catch(Exception e){
							e.printStackTrace();
						}
						if(publishdDateElement == null){
							evaluate(learningActivity.getActId(), false);
						}
					} catch (Exception e) {
						log.error("Error during evaluation: "+actId, e);
						e.printStackTrace();
					}					
				}
			} catch (SystemException e) {
				log.error("Error during evaluation job ");
				e.printStackTrace();
			}

		}
	}	
	
	public void calcualteLearningActivitiyResult(ActionRequest actionRequest,ActionResponse actionResponse) throws PortalException, SystemException, DocumentException{

    	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
    	
    	String gradeFilter = ParamUtil.getString(actionRequest, "gradeFilter");
		String criteria = ParamUtil.getString(actionRequest, "criteria");

		log.debug("gradeFilter: "+gradeFilter);
		log.debug("criteria: "+criteria);
		
		actionResponse.setRenderParameter("gradeFilter", gradeFilter);
		actionResponse.setRenderParameter("criteria", criteria);	
		
		long userId = ParamUtil.getLong(actionRequest, "userId");
		long actId = ParamUtil.getLong(actionRequest, "actId");
		
		if(userId==0){
			SessionErrors.add(actionRequest, "evaluationtaskactivity.reCalculate.userId");			
		}
		else{
			LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			evaluateUser(actId, userId, getLearningActivities(learningActivity), false);		
			SessionMessages.add(actionRequest, "evaluationtaskactivity.reCalculate.ok");
		}
	}
	
	public void publishLearningActivitiyResult(ActionRequest actionRequest,ActionResponse actionResponse) throws PortalException, SystemException, DocumentException{

   	
    	String gradeFilter = ParamUtil.getString(actionRequest, "gradeFilter");
		String criteria = ParamUtil.getString(actionRequest, "criteria");

		log.debug("gradeFilter: "+gradeFilter);
		log.debug("criteria: "+criteria);
		
		actionResponse.setRenderParameter("gradeFilter", gradeFilter);
		actionResponse.setRenderParameter("criteria", criteria);	
		
		long userId = ParamUtil.getLong(actionRequest, "userId");
		long actId = ParamUtil.getLong(actionRequest, "actId");
		
		LearningActivityTry learningActivityTry = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, userId);
		learningActivityTry.setEndDate(new Date());
		LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningActivityTry);
		LearningActivityResultLocalServiceUtil.update(learningActivityTry, true);
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
	
	private void updateLearningActivityTryAndResult(LearningActivityTry learningActivityTry, boolean finishScore) throws PortalException,
			SystemException {
		LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningActivityTry);
		
		LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivityTry.getActId(), learningActivityTry.getUserId());
		
		LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(learningActivityTry.getActId());
		learningActivityResult.setResult(learningActivityTry.getResult());
		LearningActivityTypeRegistry registry = new LearningActivityTypeRegistry();
		LearningActivityType learningActivityType = registry.getLearningActivityType(learningActivity.getTypeId());
		learningActivityResult.setPassed(learningActivityType.isPassed(learningActivity, learningActivityTry));
		learningActivityResult.setComments(learningActivityTry.getComments());
		learningActivityResult = LearningActivityResultLocalServiceUtil.updateLearningActivityResult(learningActivityResult);
		
		if(finishScore){
			ModuleResultLocalServiceUtil.update(learningActivityResult);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<Long, Long> getLearningActivities(
			LearningActivity learningActivity) throws DocumentException, SystemException {
		
		Map<Long,Long> activities= new HashMap<Long,Long>();
		if((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().length()!=0)) {
			Element activitiesElement = SAXReaderUtil.read(learningActivity.getExtracontent()).getRootElement().element("activities");
			
			if(activitiesElement!=null){
				Iterator<Element> activitiesElementItr = activitiesElement.elementIterator();
				while(activitiesElementItr.hasNext()) {
					Element activity =activitiesElementItr.next();
					if(("activity".equals(activity.getName()))&&(activity.attribute("id")!=null)&&(activity.attribute("id").getValue().length()!=0)){
						try{
							activities.put(Long.valueOf(activity.attribute("id").getValue()),Long.valueOf(activity.getText()));
						}
						catch(NumberFormatException e){
							e.printStackTrace();
						}
					}
				}				
			}
	
			List<Long> actIdsInDatabase = 
					LearningActivityLocalServiceUtil.dynamicQuery(
					DynamicQueryFactoryUtil.forClass(LearningActivity.class)
					.add(PropertyFactoryUtil.forName("typeId").ne(8))
					.add(PropertyFactoryUtil.forName("actId").in((Collection<Object>)(Collection<?>)activities.keySet()))
					.setProjection(ProjectionFactoryUtil.property("actId")));
			
			Iterator<Map.Entry<Long,Long>> activitiesIterator = activities.entrySet().iterator();
			while (activitiesIterator.hasNext()) {
				if(!actIdsInDatabase.contains(activitiesIterator.next().getKey())){
					activitiesIterator.remove();
			    }
			}
		}
		
		
		return activities;
	}
		
	private void evaluate(long actId, boolean finishScore)
			throws Exception {
		
		LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);

		try{

			Map<Long, Long> activities = getLearningActivities(learningActivity);
		
			if(activities.size()!=0){
				for(User user:UserLocalServiceUtil.getGroupUsers(learningActivity.getGroupId())) {
					if(!PermissionCheckerFactoryUtil.create(user).hasPermission(learningActivity.getGroupId(), "com.liferay.lms.model",learningActivity.getGroupId(), "VIEW_RESULTS")){
						evaluateUser(actId, user.getUserId(), activities, finishScore);	
					}
				}
			}

		}catch(DocumentException e){
			e.printStackTrace();
		}	
	}

	private void evaluateUser(long actId, long userId,Map<Long, Long> activities, boolean finishScore) throws SystemException {
		{
			double[] values = new double[activities.size()];
			double[] weights = new double[activities.size()];
			
			int i=0;
			for(Map.Entry<Long, Long> evalAct:activities.entrySet()){
				LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(evalAct.getKey(), userId);
				if(learningActivityResult==null){
					values[i]=0;
				}
				else{
					values[i]=learningActivityResult.getResult();
				}
				weights[i]=evalAct.getValue();		
				i++;
			}

			try {
				LearningActivityTry  learningActivityTry =  LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, userId);
				if(learningActivityTry==null){
					ServiceContext serviceContext = new ServiceContext();
					serviceContext.setUserId(userId);
					learningActivityTry =  LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
				}
				if(finishScore){
					learningActivityTry.setEndDate(new Date());
				}
				learningActivityTry.setResult((long)calculateMean(values, weights));
				learningActivityTry.setComments(StringPool.BLANK);
				updateLearningActivityTryAndResult(learningActivityTry, finishScore);
				
			} catch (NestableException e) {
				log.error("Error updating evaluation: "+actId+" result of user: "+userId, e);
			}						
		
		}
	}
	
    
    @SuppressWarnings({ "unchecked" })
	public void saveEvalModel(ActionRequest actionRequest,ActionResponse actionResponse){

    	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
    	try {
        	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(actionRequest, "actId"));
        	if(learningActivity==null){
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationtaskactivity.error.noLearningActivityFound")}); 
        		return;
        	}
        	
    		JSONObject jsonObjectModel=null;
			try {
				jsonObjectModel = JSONFactoryUtil.createJSONObject(actionRequest.getParameter("model"));
			} catch (JSONException e) {
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationtaskactivity.error.courseModel")});
        		e.printStackTrace();
        		return;
			}

			if((jsonObjectModel.has("passPuntuation"))&&(!Validator.isNumber(jsonObjectModel.getString("passPuntuation")))) {
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.PassPuntuationNumber")}); 
        		return;
			}

    		if(!jsonObjectModel.has("activities")){
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationtaskactivity.error.noActivities")}); 
        		return;
    		}
			
			JSONArray activities = jsonObjectModel.getJSONArray("activities");

			List<String> errors = new ArrayList<String>();
			Map<Long, Long> activitiesMap = new HashMap<Long, Long>();
			
			for (int i = 0; i < activities.length(); i++) {
				JSONObject activity = activities.getJSONObject(i);
				String id = activity.getString("id");
				String weight = activity.getString("weight");
				if(!Validator.isNumber(id)) {
					errors.add(LanguageUtil.format(getPortletConfig(), themeDisplay.getLocale(), "evaluationtaskactivity.error.idNumber",new Object[]{id},false));
				}
				
				if(!Validator.isNumber(weight)) {
					errors.add(LanguageUtil.format(getPortletConfig(), themeDisplay.getLocale(), "evaluationtaskactivity.error.weightNumber",new Object[]{weight},false));
				}
				
				Long idLong = GetterUtil.getLong(id);
				
				if(activitiesMap.containsKey(idLong)){
					errors.add(LanguageUtil.format(getPortletConfig(), themeDisplay.getLocale(), "evaluationtaskactivity.error.idNumberRepeated",new Object[]{idLong},false));
				}
				else{
					activitiesMap.put(idLong, GetterUtil.getLong(weight));
				}
			}
			
			if(!errors.isEmpty()){
	    		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    
	    		return;
			}

			if(!activitiesMap.isEmpty()) {

				List<Long> actIdsInDatabase = 
						LearningActivityLocalServiceUtil.dynamicQuery(
						DynamicQueryFactoryUtil.forClass(LearningActivity.class)
						.add(PropertyFactoryUtil.forName("typeId").ne(8))
						.add(PropertyFactoryUtil.forName("actId").in((Collection<Object>)(Collection<?>)activitiesMap.keySet()))
						.setProjection(ProjectionFactoryUtil.property("actId")));

				Iterator<Map.Entry<Long,Long>> evaluationMapIterator = activitiesMap.entrySet().iterator();
				while (evaluationMapIterator.hasNext()) {
					Map.Entry<Long,Long> evaluationEntry = evaluationMapIterator.next();
					if(!actIdsInDatabase.contains(evaluationEntry.getKey())){
						errors.add(LanguageUtil.format(getPortletConfig(), themeDisplay.getLocale(), "evaluationtaskactivity.error.idNumberNotInDatabase",new Object[]{evaluationEntry.getKey()},false));
						evaluationMapIterator.remove();
				    }
				}

			}

			if(activitiesMap.isEmpty()){
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationtaskactivity.error.noActivities")}); 
        		return;				
			}
					
            if(errors.isEmpty()) {
        		Document document = null;
        		Element rootElement = null;
        		Date firedDate = null;
        		Date publishDate = null;
            	
        		if((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().trim().length()!=0)){
        			try {
        				document=SAXReaderUtil.read(learningActivity.getExtracontent());
        				rootElement =document.getRootElement();
        				Element firedDateElement = rootElement.element("firedDate");
        				if(firedDateElement!=null){
        					firedDate =(Date)_dateFormat.parseObject(firedDateElement.getTextTrim());
        				}
        				
        				Element publishdDateElement = rootElement.element("publishDate");
        				if(publishdDateElement!=null){
        					publishDate =(Date)_dateFormat.parseObject(publishdDateElement.getTextTrim());
        				}
        			} catch (Throwable e) {
        				e.printStackTrace();
        			}	
        		}
        		
        		document = SAXReaderUtil.createDocument();
        		rootElement = document.addElement("evaluation");
        		
        		if(firedDate!=null){
        			rootElement.addElement("firedDate").setText(_dateFormat.format(firedDate));
        		}
        		
        		if(publishDate!=null){
        			rootElement.addElement("publishDate").setText(_dateFormat.format(publishDate));
        		}
        		
        		
    			Element activitiesElement = rootElement.addElement("activities");
    			
    			for (Map.Entry<Long,Long> activity : activitiesMap.entrySet()) {
					Element activityElement = activitiesElement.addElement("activity");
					activityElement.addAttribute("id", Long.toString(activity.getKey()));
					activityElement.setText(Long.toString(activity.getValue()));
				}
    			
    			if(jsonObjectModel.has("passPuntuation")) {
    				learningActivity.setPasspuntuation(jsonObjectModel.getInt("passPuntuation"));
    			}
    			
    			learningActivity.setExtracontent(document.formattedString());
				LearningActivityLocalServiceUtil.updateLearningActivity(learningActivity);
	    		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[49]); //1 
	    		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationtaskactivity.updating")}); 
	    		SessionMessages.add(actionRequest, "evaluationtaskactivity.updating");
            }
            else{
	    		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0   
	    		actionResponse.setRenderParameter("message", errors.toArray(new String[errors.size()]));
            }
    		
    	} catch (Exception e) {	
    		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
    		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationtaskactivity.error.systemError")});
    		e.printStackTrace();
    	} finally{
    		String returnToFullPageURL = actionRequest.getParameter("returnToFullPageURL");
    		if(Validator.isNotNull(returnToFullPageURL)) {
    			actionResponse.setRenderParameter("returnToFullPageURL", returnToFullPageURL);
    		}

	    	actionResponse.setRenderParameter("jspPage","/html/evaluationtaskactivity/popups/activitiesResult.jsp");   	
    	}
    }
    
	public void update(ActionRequest actionRequest,ActionResponse actionResponse) throws Exception{
		
		LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(actionRequest, "actId"));
		
		Document document = SAXReaderUtil.read(learningActivity.getExtracontent());
		Element rootElement = document.getRootElement();
		
		rootElement.addElement("firedDate").setText(_dateFormat.format(new Date()));
		learningActivity.setExtracontent(document.formattedString());
		LearningActivityLocalServiceUtil.updateLearningActivity(learningActivity);

		evaluate(learningActivity.getActId(), false);
		
		PortletURL viewPortletURL = ((LiferayPortletResponse)actionResponse).createRenderURL();
		viewPortletURL.setParameter("jspPage","/html/evaluationtaskactivity/view.jsp"); 
		
		String returnToFullPageURL = actionRequest.getParameter("returnToFullPageURL");
		if(Validator.isNotNull(returnToFullPageURL)) {
			viewPortletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
		}
		
    	actionResponse.sendRedirect(viewPortletURL.toString());
	}
	
	public void publish(ActionRequest actionRequest,ActionResponse actionResponse) throws Exception{
		
		LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(actionRequest, "actId"));
		
		Document document = SAXReaderUtil.read(learningActivity.getExtracontent());
		Element rootElement = document.getRootElement();
		
		Element publishDate = rootElement.element("publishDate");
		if(publishDate==null){
			rootElement.addElement("publishDate").setText(_dateFormat.format(new Date()));
		}
		learningActivity.setExtracontent(document.formattedString());
		LearningActivityLocalServiceUtil.updateLearningActivity(learningActivity);
		
		List<LearningActivityTry> learningActivityTries = LearningActivityTryLocalServiceUtil.getLearningActivityTriesByActId(learningActivity.getActId());
		for(LearningActivityTry learningActivityTry: learningActivityTries){
			learningActivityTry.setEndDate(new Date());
			LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningActivityTry);
			LearningActivityResultLocalServiceUtil.update(learningActivityTry, true);
		}
		
		PortletURL viewPortletURL = ((LiferayPortletResponse)actionResponse).createRenderURL();
		viewPortletURL.setParameter("jspPage","/html/evaluationtaskactivity/view.jsp");
		
		String returnToFullPageURL = actionRequest.getParameter("returnToFullPageURL");
		if(Validator.isNotNull(returnToFullPageURL)) {
			viewPortletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
		}
		
    	actionResponse.sendRedirect(viewPortletURL.toString());
	}
	
	public void reCalculate(ActionRequest actionRequest,ActionResponse actionResponse) throws Exception{

		long userId = ParamUtil.getLong(actionRequest, "userId");
		
		if(userId==0){
			SessionErrors.add(actionRequest, "evaluationtaskactivity.reCalculate.userId");			
		}
		else{
			LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(actionRequest, "actId"));
			evaluateUser(learningActivity.getActId(), userId, getLearningActivities(learningActivity), false);		
			SessionMessages.add(actionRequest, "evaluationtaskactivity.reCalculate.ok");
		}

		PortletURL viewPortletURL = ((LiferayPortletResponse)actionResponse).createRenderURL();
		viewPortletURL.setParameter("jspPage","/html/evaluationtaskactivity/view.jsp");
		
		String returnToFullPageURL = actionRequest.getParameter("returnToFullPageURL");
		if(Validator.isNotNull(returnToFullPageURL)) {
			viewPortletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
		}
		
    	actionResponse.sendRedirect(viewPortletURL.toString());
	}
	
	
	
	
	public void setGrades(ActionRequest request, ActionResponse response){
		
		ThemeDisplay themeDisplay  =(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean correct=true;
		long actId = ParamUtil.getLong(request,"actId"); 
		long studentId = ParamUtil.getLong(request,"studentId");		
		String comments = ParamUtil.getString(request,"comments");
		
		log.debug("actId: "+actId);
		log.debug("studentId: "+studentId);
		log.debug("comments: "+comments);		
		
		String gradeFilter = ParamUtil.getString(request, "gradeFilter");
		String criteria = ParamUtil.getString(request, "criteria");

		log.debug("gradeFilter: "+gradeFilter);
		log.debug("criteria: "+criteria);
		
		response.setRenderParameter("gradeFilter", gradeFilter);
		response.setRenderParameter("criteria", criteria);		
		
		CalificationType ct = null;
		double result=0;
		try {
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());			
			ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());			
			result= Double.valueOf(ParamUtil.getString(request,"result").replace(",", "."));
			log.debug("result: "+result);
			if(result<ct.getMinValue(course.getGroupCreatedId()) || result>ct.getMaxValue(course.getGroupCreatedId())){
				correct=false;
				log.error("Result fuera de rango");
				SessionErrors.add(request, "result-bad-format");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			correct=false;
			SessionErrors.add(request, "result-bad-format");
		} catch (Exception e) {
			e.printStackTrace();
			correct=false;
			SessionErrors.add(request, "grades.bad-updating");
		}
		
		if(correct) {
			try {
				LearningActivityTry  learningActivityTry =  LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, studentId);
				if(learningActivityTry==null){
					ServiceContext serviceContext = new ServiceContext();
					serviceContext.setUserId(studentId);
					learningActivityTry =  LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
				}
				
				learningActivityTry.setResult(ct.toBase100(themeDisplay.getScopeGroupId(),result));
				learningActivityTry.setComments(comments);
				
				
				Element publishdDateElement = null;
				try{
					LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
					Document document=SAXReaderUtil.read(learningActivity.getExtracontent());
					Element rootElement =document.getRootElement();
					publishdDateElement = rootElement.element("publishDate");
				}catch(Exception e){
					e.printStackTrace();
				}

				
				updateLearningActivityTryAndResult(learningActivityTry, publishdDateElement != null);
				
				SessionMessages.add(request, "grades.updating");
			} catch (NestableException e) {
				SessionErrors.add(request, "grades.bad-updating");
			}
		}
	}
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {
		long actId=0;
		
		if(ParamUtil.getBoolean(renderRequest, "actionEditingDetails", false)){
			
			actId=ParamUtil.getLong(renderRequest, "resId", 0);
			renderResponse.setProperty("clear-request-parameters",Boolean.TRUE.toString());
		}
		else{
			actId=ParamUtil.getLong(renderRequest, "actId", 0);
		}
					
		if(actId==0)// TODO Auto-generated method stub
		{
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
		else
		{
				LearningActivity activity;
				try {
					activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);

					//auditing
					
					long typeId=activity.getTypeId();
					
					if(typeId==8)
					{
						if(WindowState.MAXIMIZED.equals(renderRequest.getWindowState())){
							renderRequest.setAttribute(WebKeys.PORTLET_DECORATE, Boolean.TRUE);
						}

						super.render(renderRequest, renderResponse);
					}
					else
					{
						renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
					}
				} catch (PortalException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				}			
		}
	}

}
