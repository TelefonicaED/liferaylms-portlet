<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>

<%@ include file="/init.jsp" %>

<%
	if(ParamUtil.getLong(request,"actId",0 )==0) renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	else{
		
		
		boolean hasFreeQuestion = false;

		LearningActivity learningActivity=(LearningActivity)request.getAttribute("learningActivity");
		if(learningActivity==null) learningActivity=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"actId" ));	
		request.setAttribute("actId",learningActivity.getActId());
		request.setAttribute("learningActivity",learningActivity);
		
		List<TestQuestion> questionList = TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
		Iterator<TestQuestion> questionListIt = questionList.iterator();
		while(questionListIt.hasNext()){
			TestQuestion q = questionListIt.next();
			if(q.getQuestionType() == 2){
				hasFreeQuestion = true;
				break;
			}
		}
		
		boolean isTeacher=permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(), "VIEW_RESULTS");
		Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
	
		if (isTeacher) {
			String popupcorrection = "javascript:" + renderResponse.getNamespace() + "showPopupGetReport();";
%>
<portlet:renderURL var="goToCorrection">
	<portlet:param name="jspPage"
		value="/html/execactivity/test/correction.jsp" />
	<portlet:param name="actId"
		value="<%=Long.toString(learningActivity.getActId())%>" />
	<portlet:param name="courseId"
		value="<%=Long.toString(course.getCourseId())%>" />
</portlet:renderURL>
<aui:button name="importButton" type="button" value="action.CORRECT"
	last="true" href="<%=goToCorrection.toString()%>"></aui:button>

<%}
	
		LearningActivityTry larntry=(LearningActivityTry)request.getAttribute("larntry");
		
		if(larntry==null) larntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(ParamUtil.getLong(request,"latId" ));
		
		if(larntry.getActId() == learningActivity.getActId()){
			request.setAttribute("larntry",larntry);
		}else{
			larntry=LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(ParamUtil.getLong(request,"actId",0 ), learningActivity.getUserId());
			request.setAttribute("larntry",larntry);
		}
				
		Long tries = learningActivity.getTries();
		Long userTries = Long.valueOf(LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(learningActivity.getActId(),themeDisplay.getUserId()));
	
		LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), themeDisplay.getUserId());
	
		boolean userPassed=false;
		boolean comesFromCorrection = ParamUtil.get(request, "correction", false);
		long oldResult= ParamUtil.get(request, "oldResult", -1);
		if(!comesFromCorrection) {
			userPassed=LearningActivityResultLocalServiceUtil.userPassed(learningActivity.getActId(),themeDisplay.getUserId());
		}else {
			userPassed=learningActivity.getPasspuntuation()<=larntry.getResult();
			//Cuando estamos mejorando la nota no mostramos el popup.
			//if(oldResult <= 0){
%>
<jsp:include page="/html/shared/popResult.jsp" />
<%
			//}
%>
			<h2><%=learningActivity.getTitle(themeDisplay.getLocale()) %></h2>
<% 
		}
%>
		
		<p><liferay-ui:message key="test-done" /></p>
		<liferay-util:include page="/html/execactivity/test/timeout.jsp" servletContext="<%=this.getServletContext() %>">
			<liferay-util:param value="<%=Long.toString(learningActivity.getActId()) %>" name="actId"/>
		</liferay-util:include> 
		
		<%
		if(hasFreeQuestion){%>
			<!-- <p>Respuesta libre</p> -->
			
			<% }else{%>
			<p><liferay-ui:message key="your-result" arguments="<%=new Object[]{larntry.getResult()} %>" /></p>
			
			<% }
		%>
	
<% 
		if(!hasFreeQuestion && oldResult>0){
			if(oldResult<larntry.getResult()){
%>
				<p><liferay-ui:message key="execActivity.improve.result" arguments="<%=new Object[]{oldResult} %>" /></p>
<%		
			}else{
%>
				<p><liferay-ui:message key="execActivity.not.improve.result" arguments="<%=new Object[]{oldResult} %>" /></p>
<%			
			}
		}	
	
		if(userPassed){
%>
			<p class="color_tercero negrita"><liferay-ui:message key="your-result-pass" /></p>
<%
		}else{
			if(!hasFreeQuestion){
				%>			
				<p class="color_tercero negrita"><liferay-ui:message key="your-result-dont-pass"  arguments="<%=new Object[]{learningActivity.getPasspuntuation()} %>" /></p>
	<% 
			}
		}
		
		if(tries>0 && userTries >= tries ){
%>
			<p class="color_tercero"><liferay-ui:message key="your-result-no-more-tries" /></p>
<%
		}
%>
		<p class="negrita"><liferay-ui:message key="your-answers" /></p>
<%
		List<TestQuestion> questions=null;
		if (GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"random"))==0)
			questions=TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
		else{
			questions= new ArrayList<TestQuestion>();
			Iterator<Element> nodeItr = SAXReaderUtil.read(larntry.getTryResultData()).getRootElement().elementIterator();
			while(nodeItr.hasNext()) {
				Element element = nodeItr.next();
		         if("question".equals(element.getName())) questions.add(TestQuestionLocalServiceUtil.getTestQuestion(Long.valueOf(element.attributeValue("id"))));
		    }	
		}
		
		for(TestQuestion question:questions){
			QuestionType qt = new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
			qt.setLocale(themeDisplay.getLocale());
			%><%=qt.getHtmlFeedback(SAXReaderUtil.read(larntry.getTryResultData()), question.getQuestionId(), themeDisplay)%><%
		}
	
		if(tries==0 || userTries < tries ||permissionChecker.hasPermission(learningActivity.getGroupId(),LearningActivity.class.getName(),learningActivity.getActId(), ActionKeys.UPDATE)) {
			if(!LearningActivityResultLocalServiceUtil.userPassed(learningActivity.getActId(),themeDisplay.getUserId())){
				if(tries>0){	
%>
					<liferay-portlet:renderURL var="realizar">
						<liferay-portlet:param name="actId" value="<%=Long.toString(learningActivity.getActId()) %>"></liferay-portlet:param>
						<liferay-portlet:param name="jspPage" value="/html/execactivity/test/preview.jsp" />
					</liferay-portlet:renderURL>
<%
					String enlace="self.location='"+realizar.toString()+"'"; 
%>
					<aui:button name="repetir" value="<%=LanguageUtil.get(pageContext,\"execativity.test.try.again\")%>" onClick="<%=enlace %>"></aui:button>
<%
				}else{	
%>
					<liferay-portlet:renderURL var="realizardir">
						<liferay-portlet:param name="actId" value="<%=Long.toString(learningActivity.getActId()) %>"></liferay-portlet:param>
						<liferay-portlet:param name="jspPage" value="/html/execactivity/test/view.jsp" />
					</liferay-portlet:renderURL>
<%
					String enlace="self.location='"+realizardir.toString()+"'"; %>
					<aui:button name="repetir" value="<%=LanguageUtil.get(pageContext,\"execativity.test.try.again\")%>" onClick="<%=enlace %>"></aui:button>
<%
				}
			}else{
				if(result.getResult()<100){
					String improveStr = LearningActivityLocalServiceUtil.getExtraContentValue(ParamUtil.getLong(request,"actId"), "improve");
					if(improveStr.equals("true")){
						if(tries>0){
%>
							<p class="negrita"><liferay-ui:message key="execativity.test.try.count" arguments="<%=new Object[]{userTries,tries} %>" /></p>
							<p class="color_tercero textcenter negrita"><liferay-ui:message key="execativity.test.try.confirmation.again" /></p>
<%
						}
%>
						<liferay-portlet:renderURL var="mejorardir">
							<liferay-portlet:param name="actId" value="<%=Long.toString(learningActivity.getActId()) %>"></liferay-portlet:param>
							<liferay-portlet:param name="improve" value="true"></liferay-portlet:param>
							<liferay-portlet:param name="jspPage" value="/html/execactivity/test/preview.jsp" />
						</liferay-portlet:renderURL>
<%	
						String enlace="self.location='"+mejorardir.toString()+"'"; 
%>
						<aui:button name="repetir" value="<%=LanguageUtil.get(pageContext,\"execativity.test.try.again.improve\")%>" onClick="<%=enlace %>"></aui:button>
<%
					}
				}
			}
		}
	}
%>