<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@page import="com.liferay.lms.model.Competence"%>
<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Competence competence = (Competence)row.getObject();
String primKey = String.valueOf(competence.getCompetenceId());
%>
<liferay-ui:icon-menu>
	<portlet:renderURL var="editURL">
		<portlet:param name="competenceId" value="<%=primKey %>" />
		<portlet:param name="jspPage" value="/html/competencesadmin/editcompetence.jsp" />
	</portlet:renderURL>
	<%
		if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Competence.class.getName(),primKey,ActionKeys.UPDATE)){
	%>
		<liferay-ui:icon image="edit" message="edit" url="<%=editURL.toString() %>" />
	<%	}%>
	
	<portlet:actionURL name="deleteCompetence" var="deleteURL">
		<portlet:param name="competenceId" value="<%= primKey %>" />
	</portlet:actionURL>
	<%
		if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Competence.class.getName(),primKey,ActionKeys.DELETE)){
	%>
		<liferay-ui:icon image="delete" message="delete" url="<%=deleteURL.toString() %>" />
	<%	}%>
	
	
</liferay-ui:icon-menu>