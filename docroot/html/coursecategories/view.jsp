<%@page import="com.liferay.portlet.asset.model.AssetCategory"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp" %>
<%
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
if(course!=null && permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW))
{
AssetEntry asset=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),course.getCourseId());
java.util.List<AssetCategory> acs=asset.getCategories();
if(acs!=null&&acs.size()>0)
{
	%>
	<ul id="coursecategories">
	<%
	for(AssetCategory ac:acs)
	{
		%>
		<li><%=ac.getTitle(themeDisplay.getLocale()) %></li>
		<%
	}
	%>
	</ul>
	<%
}
}
else
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
%>