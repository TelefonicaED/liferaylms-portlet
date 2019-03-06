<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.kernel.util.StringBundler"%>
<%@include file="/init.jsp"%>


<%
String path = renderRequest.getParameter("path");
String portletId = renderRequest.getParameter("portletId");
String courseId = renderRequest.getParameter("courseId");
String cur = renderRequest.getParameter("cur");
String delta = renderRequest.getParameter("delta");
Course selectedCourse = CourseLocalServiceUtil.fetchCourse(Long.parseLong(courseId)); 

StringBundler sb = new StringBundler();
sb.append("<portlet-preferences >");
sb.append("<preference>");
sb.append("<name>");
sb.append("portletSetupShowBorders");
sb.append("</name>");
sb.append("<value>");
sb.append("false");
sb.append("</value>");
sb.append("</preference>");
sb.append("</portlet-preferences>");
%>

<portlet:renderURL portletMode="view" var="backURL" >
	<portlet:param name="cur" value="<%=cur%>"/>
	<portlet:param name="delta" value="<%=delta %>"/>
	<portlet:param name="keepSession" value="true"/>
</portlet:renderURL>

<c:set var="courseId" scope="request" value="<%=courseId%>"/>
<liferay-ui:header backURL="${backURL}" 
	title="<%=selectedCourse.getTitle(themeDisplay.getLocale())%>" />
<liferay-portlet:runtime portletName="<%=portletId%>" queryString="courseId=${courseId}" defaultPreferences="<%=sb.toString() %>"/>



