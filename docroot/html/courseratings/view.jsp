<%@page import="com.liferay.portlet.expando.model.ExpandoValue"%>
<%@page import="com.liferay.portlet.expando.model.ExpandoTableConstants"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp" %>

<%
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());

if(course!=null && permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW))
{

%>


<liferay-ui:ratings classPK="<%=course.getCourseId() %>"  className="<%=Course.class.getName() %>" />

<% 
} 
%>
