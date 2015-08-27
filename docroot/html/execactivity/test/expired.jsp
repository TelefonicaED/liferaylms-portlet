<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page	import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page	import="com.liferay.lms.model.LearningActivity"%>
<%@page	import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page	import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@ include file="/init.jsp" %>
<% 

	LearningActivity learnact=null;
	if(request.getAttribute("learningActivity")!=null) learnact=(LearningActivity)request.getAttribute("learningActivity");
	else learnact=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"actId"));
		
	LearningActivityTry learnactTry=null;
	if(request.getAttribute("larntry")!=null) learnactTry=(LearningActivityTry)request.getAttribute("larntry");
	else learnactTry=LearningActivityTryLocalServiceUtil.getLearningActivityTryNotFinishedByActUser(learnact.getActId(),themeDisplay.getUserId());
	
	request.setAttribute("learningActivity",learnact);
	request.setAttribute("larntry",learnactTry);
	
	long activityTimestamp = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(learnact.getActId(),"timeStamp"));
	
	if(activityTimestamp !=0){ 
		long timestamp=0;
		if(learnactTry.getEndDate()!=null) timestamp=activityTimestamp*1000 - (learnactTry.getEndDate().getTime() - learnactTry.getStartDate().getTime());				
		else timestamp=activityTimestamp*1000 - (new Date().getTime() - learnactTry.getStartDate().getTime());			
				
		if(timestamp<0) { 
			long random = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(learnact.getActId(),"random"));
			if (random==0) learnactTry.setTryResultData(StringPool.BLANK);				
			else {
				List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(learnact.getActId());
				questions = new ArrayList<TestQuestion>(questions);
				Collections.shuffle(questions);	
				
				if(random>questions.size()) random=questions.size();
				
				Element resultadosXML=SAXReaderUtil.createElement("results");
				Document resultadosXMLDoc=SAXReaderUtil.createDocument(resultadosXML);
				
				for(int index=0; index<random; index++) {
					TestQuestion question = questions.get(index);
					Element questionXML=SAXReaderUtil.createElement("question");
					questionXML.addAttribute("id", Long.toString(question.getQuestionId()));
					resultadosXML.add(questionXML);	
				}
				learnactTry.setTryResultData(resultadosXMLDoc.formattedString());
			}
	  
			learnactTry.setEndDate(new Date(learnactTry.getStartDate().getTime()+activityTimestamp));
			learnactTry.setResult(0);
			LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learnactTry);		  
		}
	}
	
	request.setAttribute("resultados", new Hashtable<TestQuestion, TestAnswer>());
%>	    
	<liferay-util:include page="/html/execactivity/test/results.jsp" servletContext="<%=this.getServletContext() %>">
		<liferay-util:param value="<%=Long.toString(learnact.getActId()) %>" name="actId"/>
		<liferay-util:param value="<%=Long.toString(learnactTry.getLatId()) %>" name="latId"/>
	</liferay-util:include>       







