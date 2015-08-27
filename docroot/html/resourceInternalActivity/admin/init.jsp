
<%@ include file="/init.jsp" %>

<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>

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

%>
<liferay-portlet:renderURL var="backURL">
</liferay-portlet:renderURL>
<liferay-ui:header title="<%=learnact.getTitle(themeDisplay.getLocale()) %>"></liferay-ui:header>