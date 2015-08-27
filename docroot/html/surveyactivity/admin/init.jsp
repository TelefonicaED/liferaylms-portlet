<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@ include file="/init.jsp" %>
<%
	LearningActivity learnact=null;
	if(request.getAttribute("activity")!=null)
	{
		learnact=(LearningActivity)request.getAttribute("activity");
	}
	else
	{
		learnact=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
	}
	
	boolean isSurvey = learnact.getTypeId() == 4;
	
	if(isSurvey){
%>

<div class = "init">
	<liferay-portlet:renderURL var="backURL">
		<liferay-portlet:param name="resId" value="0"></liferay-portlet:param>
	</liferay-portlet:renderURL>
	<a href="<%=backURL.toString()%>"><%=LanguageUtil.get(pageContext,"cancel")%></a>
	<br>
	
	<liferay-ui:header title="<%=learnact.getTitle(themeDisplay.getLocale()) %>"></liferay-ui:header>

</div>
<%}%>