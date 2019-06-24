<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<%
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());

boolean actionEditing=ParamUtil.getBoolean(request,"actionEditing",false);
String text="enable-edition";
if(actionEditing) text="disable-edition";
%>

<liferay-portlet:renderURL var="changeEditingMode">
	<liferay-portlet:param name="actionEditing" value="<%=Boolean.toString(!actionEditing)%>"/>
	<%if(actionEditing){ %>
		<liferay-portlet:param name="actionEditingActivity" value="<%=StringPool.FALSE%>"/>
		<liferay-portlet:param name="actionEditingDetails" value="<%=StringPool.FALSE%>"/>
		<liferay-portlet:param name="actionEditingModule" value="<%=StringPool.FALSE%>"/>
		<liferay-portlet:param name="actionCalifications" value="<%=StringPool.FALSE%>"/>
	<%}%>
</liferay-portlet:renderURL>

<% String redirect="self.location='"+changeEditingMode.toString()+"'"; %>
<aui:button-row>
	<img src='/liferaylms-portlet/icons/icon_editingmode.png' >
	<aui:button type="submit"  value="<%=text %>" alt="<%=LanguageUtil.get(themeDisplay.getLocale(), text) %>" onClick="<%=redirect %>"/>
</aui:button-row>
