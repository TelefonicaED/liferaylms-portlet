<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.SCORMContentLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.SCORMContentServiceUtil"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<liferay-ui:icon-menu>
<%
SearchContainer<SCORMContent> searchContainer = (SearchContainer<SCORMContent>)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row =
	(ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	 
SCORMContent myScorm = (SCORMContent)row.getObject();
String name = SCORMContent.class.getName();
String primKey = String.valueOf(myScorm.getScormId());

%>
<portlet:actionURL name="deleteSCORM" var="deleteURL">
<portlet:param name="scormId" value="<%= primKey %>" />
<portlet:param name="redirect" value="<%= redirect %>"/>
</portlet:actionURL>
<%
if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  SCORMContent.class.getName(),primKey,ActionKeys.DELETE))
{
%>
<liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />
<%
}
%>

<c:if test="<%= permissionChecker.hasPermission(myScorm.getGroupId(), SCORMContent.class.getName(), myScorm.getScormId(),
ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="<%=SCORMContent.class.getName() %>"
					modelResourceDescription="<%= myScorm.getTitle() %>"
					resourcePrimKey="<%= String.valueOf(myScorm.getScormId()) %>"
					var="permissionsURL"
				/>
				<liferay-ui:icon image="permissions" message="courseadmin.adminactions.permissions" url="<%=permissionsURL %>" />			
			</c:if>
<%
if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  SCORMContent.class.getName(),primKey,ActionKeys.UPDATE)) {
	%>
	<portlet:actionURL name="forceSCORM" var="force12URL">
		<portlet:param name="scormId" value="<%= primKey %>" />
		<portlet:param name="version" value="1.2" />
		<portlet:param name="redirect" value="<%= redirect %>"/>
</portlet:actionURL>

<liferay-ui:icon image="edit" message="scormadmin.force.12" url="<%=force12URL %>" />	

<portlet:actionURL name="forceSCORM" var="force13URL">
		<portlet:param name="scormId" value="<%= primKey %>" />
		<portlet:param name="version" value="1.3" />
		<portlet:param name="redirect" value="<%= redirect %>"/>
</portlet:actionURL>

<liferay-ui:icon image="edit" message="scormadmin.force.13" url="<%=force13URL %>" />	
	<%
}
%>

</liferay-ui:icon-menu>
