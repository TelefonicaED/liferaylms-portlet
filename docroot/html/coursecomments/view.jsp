<%@page import="com.liferay.portlet.expando.model.ExpandoValue"%>
<%@page import="com.liferay.portlet.expando.model.ExpandoTableConstants"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp" %>

<%


if (themeDisplay.isSignedIn())

{
		
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());


if(themeDisplay.isSignedIn() &&   course!=null && permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW))
{
	
%>



<portlet:actionURL name="invokeTaglibDiscussion" var="discussionURL" >	
</portlet:actionURL>
<liferay-ui:discussion className="<%= Course.class.getName() %>"
	classPK="<%= course.getPrimaryKey() %>"
	formAction="<%= discussionURL %>" 
	formName="fm_1"
	ratingsEnabled="false" 
	redirect="<%= currentURL %>"
	subject="<%= course.getTitle() %>" 
	userId="<%= course.getUserId() %>" /> 



<% 
}
} // no signed 
else
{
%>
	<liferay-ui:message key="course.comments.nologado"></liferay-ui:message><br/>	
<% 
}
%>
