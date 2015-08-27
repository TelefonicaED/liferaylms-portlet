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

<%@ include file="/init.jsp"%>

<%
	if (ParamUtil.getLong(request, "actId", 0) == 0) {
		renderRequest.setAttribute( WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	} 
	else
	{
		long actId = ParamUtil.getLong(request, "actId",0);
		LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
	%>
		<div class="surveyactivity results">
			<portlet:renderURL var="backToQuestionsURL">
				<portlet:param name="jspPage" value="/html/surveyactivity/view.jsp"></portlet:param>
			</portlet:renderURL>
		
			<div class="message">
				<%=learningActivity.getFeedbackCorrect() %>
			</div>
			<a href="<%=backToQuestionsURL.toString() %>" ><liferay-ui:message key="back" /></a>
		</div>
		
	<%	
	} 
	%>
	
