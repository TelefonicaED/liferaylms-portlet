<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<%
String necessary = LanguageUtil.get(pageContext,"competences.necessary");
String assigned = LanguageUtil.get(pageContext,"competences.assigned");

long primKey=ParamUtil.getLong(request, "courseId",0);
Course course=CourseLocalServiceUtil.getCourse(primKey);
String sPrimKey = String.valueOf(primKey);
String tab = ParamUtil.getString(request, "tabs1", necessary);

StringBuffer menu = new StringBuffer(necessary);
menu.append(",");
menu.append(assigned);
%>

<portlet:renderURL var="memebersURL">
	<portlet:param name="courseId" value="<%=String.valueOf(primKey) %>" />
	<portlet:param name="view" value="competence-tab" />
</portlet:renderURL>

<liferay-portlet:renderURL var="backURL"></liferay-portlet:renderURL>
<liferay-ui:header title="<%=course.getTitle(themeDisplay.getLocale()) %>" backURL="<%=backURL %>"></liferay-ui:header>

<liferay-ui:tabs names="<%=menu.toString() %>" url="<%=memebersURL %>" />
	<c:if test='<%= tab.equals(necessary) %>'>
	  <liferay-util:include page="/html/courseadmin/competence.jsp" servletContext="<%=this.getServletContext() %>">
		<liferay-util:param value="<%=sPrimKey %>" name="courseId"/>
		<liferay-util:param value="true" name="condition"/>
		<liferay-util:param value="1" name="tab"/>
	  </liferay-util:include>
	</c:if>
	<c:if test='<%= tab.equals(assigned) %>'>
	  <liferay-util:include page="/html/courseadmin/competence.jsp" servletContext="<%=this.getServletContext() %>">
		<liferay-util:param value="<%=sPrimKey %>" name="courseId"/>
		<liferay-util:param value="false" name="condition"/>
		<liferay-util:param value="2" name="tab"/>
	  </liferay-util:include>
	</c:if>