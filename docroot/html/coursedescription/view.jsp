<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp" %>
<%
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
if(course!=null && (permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW) || permissionChecker.hasOwnerPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),themeDisplay.getUserId(),ActionKeys.VIEW)))
{
%>

	<div class="description"><%=HtmlUtil.unescape(course.getDescription(themeDisplay.getLocale())) %></div>
<%}
else
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
%>