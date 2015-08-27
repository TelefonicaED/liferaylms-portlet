<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<%
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
if (!themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(),
		"com.liferay.lms.model", themeDisplay.getScopeGroupId(), "ADD_MODULE")) {
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}else{
	boolean actionEditing=ParamUtil.getBoolean(request,"actionEditing",false);
	String text="enable-edition";
	if(actionEditing) text="disable-edition";
	%>

	<liferay-portlet:renderURL var="changeEditingMode">
		<liferay-portlet:param name="actionEditing" value="<%=Boolean.toString(!actionEditing)%>"/>
	</liferay-portlet:renderURL>
	
	<% String redirect="self.location='"+changeEditingMode.toString()+"'"; %>
	<aui:button-row>
		<aui:button type="submit"  value="<%=text %>" onClick="<%=redirect %>"/>
	</aui:button-row>
<%}%>