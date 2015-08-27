<%@include file="../init.jsp" %>

<%@ page import="com.liferay.lms.model.Module"%>
<%@ page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>

<%
String containerStart = (String) request.getAttribute("containerStart");
String containerEnd =  (String) request.getAttribute("containerEnd");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
Module module = (Module)row.getObject();

long groupId = module.getGroupId();
String name = Module.class.getName();
String primKey = String.valueOf(module.getPrimaryKey());

%>
<c:if test="<%= permissionChecker.hasPermission(groupId, name, primKey, ActionKeys.UPDATE) %>">
    <portlet:actionURL name="eventmodule" var="eventmoduleURL">
        <portlet:param name="resourcePrimKey" value="<%=primKey %>" />
        <portlet:param name="containerStart" value="<%= containerStart %>" />
        <portlet:param name="containerEnd" value="<%= containerEnd %>" />
    </portlet:actionURL>
<%
	if (primKey.equalsIgnoreCase((String)request.getAttribute("highlightRowWithKey"))) {
%>
    <liferay-ui:icon image="forward" url="<%=eventmoduleURL.toString() %>" />
<%
	} else {
%>    
    <liferay-ui:icon image="view" url="<%=eventmoduleURL.toString() %>" />
<%
	}
%>     
</c:if>
