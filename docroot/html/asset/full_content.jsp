<%@ include file="/init.jsp" %>
<%@page import="java.util.List"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%
LearningActivity larn=(LearningActivity)request.getAttribute("learningactivity");
%>
<p><%=larn.getDescription(themeDisplay.getLocale()) %></p>
