package com.liferay.lms;

import java.io.IOException;
import java.text.Format;
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
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.ProcessEvent;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import com.liferay.lms.events.ThemeIdEvent;
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.NestableException;
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
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class EvaluationAvgPortlet extends MVCPortlet implements MessageListener{
	
	private static Format _dateFormat =
			FastDateFormatFactoryUtil.getSimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:sszzz",Locale.US);
	
	public static final String NOT_TEACHER_SQL = "WHERE User_.userId NOT IN "+
			 "( SELECT Usergrouprole.userId "+
			 "    FROM Usergrouprole "+ 
			 "   INNER JOIN Resourcepermission ON Usergrouprole.roleId = Resourcepermission.roleId "+
			 "   INNER JOIN Resourceaction ON Resourcepermission.name = Resourceaction.name "+
			 "	   					      AND (BITAND(CAST_LONG(ResourcePermission.actionIds), CAST_LONG(ResourceAction.bitwiseValue)) != 0)"+
			 "   WHERE Resourcepermission.scope="+ResourceConstants.SCOPE_GROUP_TEMPLATE+
			 "     AND Resourceaction.actionId = 'VIEW_RESULTS' "+
			 "     AND Resourceaction.name='com.liferay.lms.model' "+
			 "     AND Usergrouprole.groupid=? ) ";

	public static final String COURSE_RESULT_PASSED_SQL = "WHERE (EXISTS (SELECT 1 FROM lms_courseresult " +
			"WHERE User_.userId = lms_courseresult.userId " +
			" AND lms_courseresult.passed > 0 AND lms_courseresult.courseId = ? ))"; 

	public static final String COURSE_RESULT_FAIL_SQL = "WHERE (EXISTS (SELECT 1 FROM lms_courseresult " +
			"WHERE User_.userId = lms_courseresult.userId " +
			" AND lms_courseresult.passed = 0 AND lms_courseresult.courseId = ? ))"; 

	public static final String COURSE_RESULT_NO_CALIFICATION_SQL = "WHERE (NOT EXISTS (SELECT 1 FROM lms_courseresult " +
			"WHERE User_.userId = lms_courseresult.userId AND lms_courseresult.courseId = ? ))"; 
	
	private static Log _log = LogFactoryUtil.getLog(EvaluationAvgPortlet.class);
	
	@Override
	public void receive(Message message) throws MessageListenerException {
		long courseId = message.getLong("courseId");
		
		if(courseId!=0){
			try {
				Course course = CourseLocalServiceUtil.getCourse(courseId);
				CourseEval courseEval = new CourseEvalRegistry().getCourseEval(course.getCourseEvalId());
				if(!courseEval.updateCourse(course)){
					_log.error("Error during average evaluation: "+courseId);
				}
	
			} catch (NestableException e) {
				_log.error("Error during average evaluation: "+courseId, e);
				
			}
		}
		
		
		
	}
	
    @ProcessEvent(qname = "{http://www.wemooc.com/}themeId")
    public void handlethemeEvent(EventRequest eventRequest, EventResponse eventResponse) {
    	
        if (eventRequest.getEvent().getValue() instanceof ThemeIdEvent){
     	   ThemeIdEvent themeIdEvent = (ThemeIdEvent) eventRequest.getEvent().getValue();
     	   long moduleId=ParamUtil.getLong(eventRequest, "moduleId",0L);
     	   if(moduleId==themeIdEvent.getModuleId()){
     		   eventResponse.setRenderParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.FALSE);
     	   }    	
     	   else if((moduleId==0)&&(themeIdEvent.getModuleId()==ThemeIdEvent.EVALUATION_THEME_ID)){
     		   eventResponse.setRenderParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.TRUE);
     	   }
        }
    }
    
    @SuppressWarnings({ "unchecked", "unused" })
	public void saveEvalModel(ActionRequest actionRequest,ActionResponse actionResponse){

    	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
    	try {

        	Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
        	if(course==null){
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.noCourseFound")}); 
        		return;
        	}
        	
    		CourseEval courseEval = new CourseEvalRegistry().getCourseEval(course.getCourseEvalId());
        	if(course==null){
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.noCourseEvalFound")});  
        		return;
        	}
        	
    		JSONObject jsonObjectModel=null;
			try {
				jsonObjectModel = JSONFactoryUtil.createJSONObject(actionRequest.getParameter("model"));
			} catch (JSONException e) {
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.courseModel")});  
        		return;
			}
			
    		if(!jsonObjectModel.has("passPuntuation")){
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.noPassPuntuation")}); 
        		return;
    		}

			if(!Validator.isNumber(jsonObjectModel.getString("passPuntuation"))) {
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.PassPuntuationNumber")}); 
        		return;
			}
			
			long passPuntuation = jsonObjectModel.getLong("passPuntuation");
			if((passPuntuation<0)||(passPuntuation>100)) {
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.PassPuntuationBetween")}); 
        		return;
			}
			
			
    		if(!jsonObjectModel.has("evaluations")){
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.noEvaluations")}); 
        		return;
    		}
			
			JSONArray evaluations = jsonObjectModel.getJSONArray("evaluations");

			List<String> errors = new ArrayList<String>();
			Map<Long, Long> evaluationMap = new HashMap<Long, Long>();
			
			for (int i = 0; i < evaluations.length(); i++) {
				JSONObject evaluation = evaluations.getJSONObject(i);
				String id = evaluation.getString("id");
				String weight = evaluation.getString("weight");
				if(!Validator.isNumber(id)) {
					errors.add(LanguageUtil.format(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.idNumber",new Object[]{id},false));
				}
				
				if(!Validator.isNumber(weight)) {
					errors.add(LanguageUtil.format(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.weightNumber",new Object[]{weight},false));
				}
				
				Long idLong = GetterUtil.getLong(id);
				
				if(evaluationMap.containsKey(idLong)){
					errors.add(LanguageUtil.format(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.idNumberRepeated",new Object[]{weight},false));
				}
				else{
					evaluationMap.put(idLong, GetterUtil.getLong(weight));
				}
			}
			
			if(!errors.isEmpty()){
	    		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    
	    		return;
			}
			
			if(evaluationMap.isEmpty()){
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.noEvaluations")}); 
        		return;				
			}
			
			List<Long> actIdsInDatabase = 
				LearningActivityLocalServiceUtil.dynamicQuery(
				DynamicQueryFactoryUtil.forClass(LearningActivity.class)
				.add(PropertyFactoryUtil.forName("typeId").eq(8))
				.add(PropertyFactoryUtil.forName("actId").in((Collection<Object>)(Collection<?>)evaluationMap.keySet()))
				.setProjection(ProjectionFactoryUtil.property("actId")));
			
			Iterator<Map.Entry<Long,Long>> evaluationMapIterator = evaluationMap.entrySet().iterator();
			while (evaluationMapIterator.hasNext()) {
				Map.Entry<Long,Long> evaluationEntry = evaluationMapIterator.next();
				if(!actIdsInDatabase.contains(evaluationEntry.getKey())){
					errors.add(LanguageUtil.format(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.idNumberNotInDatabase",new Object[]{evaluationEntry.getKey()},false));
					evaluationMapIterator.remove();
			    }
			}
			
            if(errors.isEmpty()) {
				courseEval.setEvaluationModel(course, jsonObjectModel);
				CourseLocalServiceUtil.updateCourse(course);
	    		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[49]); //1 
	    		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.updating")});  
            }
            else{
	    		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0   
	    		actionResponse.setRenderParameter("message", errors.toArray(new String[errors.size()]));
            }
    		
    	} catch (Exception e) {	
    		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
    		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.systemError")});  
    	} finally{
    		String returnToFullPageURL = actionRequest.getParameter("returnToFullPageURL");
    		if(Validator.isNotNull(returnToFullPageURL)) {
    			actionResponse.setRenderParameter("returnToFullPageURL", returnToFullPageURL);
    		}
    		
	    	actionResponse.setRenderParameter("jspPage","/html/evaluationAvg/popups/evaluationsResult.jsp");   	
	    	actionResponse.setRenderParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.TRUE);
    	}
    }
    
	public void updateCourse(ActionRequest actionRequest,ActionResponse actionResponse) throws Exception{
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		
		Document document = SAXReaderUtil.read(course.getCourseExtraData());
		Element rootElement = document.getRootElement();
		
		Element firedDateElement = rootElement.element("firedDate");
		if(firedDateElement==null){
			rootElement.addElement("firedDate").setText(_dateFormat.format(new Date()));
			course.setCourseExtraData(document.formattedString());
			CourseLocalServiceUtil.updateCourse(course);
			/*
				Message message = new Message();
				message.put("courseId", course.getCourseId());
				MessageBusUtil.sendMessage("liferay/lms/evaluationAverage", message);
			*/
			CourseEval courseEval = new CourseEvalRegistry().getCourseEval(course.getCourseEvalId());
			if(!courseEval.updateCourse(course)){
				SessionErrors.add(actionRequest, "evaluationAvg.evaluation.error");
			}
		}
		
		PortletURL viewPortletURL = ((LiferayPortletResponse)actionResponse).createRenderURL();
		viewPortletURL.setParameter("jspPage","/html/evaluationAvg/view.jsp");   	
		viewPortletURL.setParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.TRUE);
		
		String returnToFullPageURL = actionRequest.getParameter("returnToFullPageURL");
		if(Validator.isNotNull(returnToFullPageURL)) {
			viewPortletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
		}
		
    	actionResponse.sendRedirect(viewPortletURL.toString());
	}
	
	public void reCalculate(ActionRequest actionRequest,ActionResponse actionResponse) throws Exception{
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		CourseEval courseEval = new CourseEvalRegistry().getCourseEval(course.getCourseEvalId());
		long userId = ParamUtil.getLong(actionRequest, "userId");
		
		if(userId==0){
			SessionErrors.add(actionRequest, "evaluationAvg.reCalculate.userId");			
		}
		else if(!courseEval.updateCourse(course, userId)){
			SessionErrors.add(actionRequest, "evaluationAvg.reCalculate.error");
		}
		else{
			SessionMessages.add(actionRequest, "evaluationAvg.reCalculate.ok");
		}
		
		PortletURL viewPortletURL = ((LiferayPortletResponse)actionResponse).createRenderURL();
		viewPortletURL.setParameter("jspPage","/html/evaluationAvg/view.jsp");   	
		viewPortletURL.setParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.TRUE);
		
		String returnToFullPageURL = actionRequest.getParameter("returnToFullPageURL");
		if(Validator.isNotNull(returnToFullPageURL)) {
			viewPortletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
		}
		
    	actionResponse.sendRedirect(viewPortletURL.toString());
	}
	
	
	public void setGrade(ActionRequest actionRequest,ActionResponse actionResponse) throws Exception{
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
    	try {
    		List<String> errors = new ArrayList<String>();
    		
    		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
    		CourseEval courseEval = null;
    		
        	if(course==null){
        		errors.add(LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.noCourseFound"));
        	}
        	else {  	
	    		courseEval = new CourseEvalRegistry().getCourseEval(course.getCourseEvalId());
	        	if(courseEval==null){
	        		errors.add(LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.noCourseEvalFound"));
	        	}
        	}
        	
        	long userId = ParamUtil.getLong(actionRequest, "userId");
        	if(userId==0){        		
        		errors.add(LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.noUserIdParam"));
        	}
        	
    		long result = ParamUtil.getLong(actionRequest, "result");
        	if((result<0)||(result>100)){
        		errors.add(LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.resultNumberRange"));
        	}
        	
    		String comments = ParamUtil.getString(actionRequest, "comments");
        	
        	if(errors.size()!=0){
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",errors.toArray(new String[errors.size()]));  
        		return;
        	}
        	
        	
        	CourseResult courseResult = CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), userId);
        	if(courseResult==null){
        		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
        		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.noCourseResultFound")});  
        		return;
        	}

    		courseResult.setComments(comments);
    		courseResult.setResult(result);
    		
    		boolean passed = courseResult.getPassed();
    		if(courseEval.getNeedPassPuntuation()){
    			courseResult.setPassed(courseEval.getPassPuntuation(course)<=result);
    		}
    		
			if((courseResult.getPassedDate()==null)||
				(courseResult.getPassed()!=passed)) {
				courseResult.setPassedDate(new Date());
			}
    		
    		CourseResultLocalServiceUtil.update(courseResult);
    		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[49]); //1    		
    		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.grade.updating")});  
    		
    	} catch (Exception e) {	
    		actionResponse.setRenderParameter("responseCode",StringPool.ASCII_TABLE[48]); //0    		
    		actionResponse.setRenderParameter("message",new String[]{LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "evaluationAvg.error.systemError")});  
    	} finally{
    		String returnToFullPageURL = actionRequest.getParameter("returnToFullPageURL");
    		if(Validator.isNotNull(returnToFullPageURL)) {
    			actionResponse.setRenderParameter("returnToFullPageURL", returnToFullPageURL);
    		}
    		
	    	actionResponse.setRenderParameter("jspPage","/html/evaluationAvg/popups/evaluationsResult.jsp");   	
	    	actionResponse.setRenderParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.TRUE);
    	}

	}

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		
		if(ParamUtil.getBoolean(renderRequest, WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,false)){
			if(WindowState.MAXIMIZED.equals(renderRequest.getWindowState())){
				renderRequest.setAttribute(WebKeys.PORTLET_DECORATE, Boolean.TRUE);
			}
			
			super.doView(renderRequest, renderResponse);
		}
		else {
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
	}

}

