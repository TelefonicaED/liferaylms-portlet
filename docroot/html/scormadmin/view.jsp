<%@page import="com.liferay.lms.model.SCORMContent"%>
<%@page import="com.liferay.lms.service.SCORMContentLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<portlet:renderURL var="newactivityURL">
<portlet:param name="jspPage" value="/html/scormadmin/editscorm.jsp"></portlet:param>
<portlet:param name="redirect" value="<%= currentURL %>"></portlet:param>
</portlet:renderURL>
<div class="newitem">
<%
if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.scormmodel",themeDisplay.getScopeGroupId(),"ADD_SCORM"))
{
	
%>
<liferay-ui:icon
image="add" cssClass="newitem2"
label="<%= true %>"
message="new"
url='<%= newactivityURL %>'
/>
<%
}
%>
</div>
<% List<SCORMContent> resultados = SCORMContentLocalServiceUtil.getSCORMContentOfGroup(themeDisplay.getScopeGroupId()); %>
<liferay-ui:search-container 
 delta="10">
<liferay-ui:search-container-results>
<%
 // TODO better pagination
pageContext.setAttribute("total", resultados.size());
pageContext.setAttribute("results", resultados.subList(searchContainer.getStart() >= resultados.size() ? 0 : searchContainer.getStart(), resultados.size() <= searchContainer.getEnd() ? resultados.size() : searchContainer.getEnd()));

%>
</liferay-ui:search-container-results>
<liferay-ui:search-container-row
className="com.liferay.lms.model.SCORMContent"
keyProperty="scormId"
modelVar="scorm">
<liferay-ui:search-container-column-text>
<liferay-portlet:renderURL var="viewSCORMURL">
<liferay-portlet:param name="jspPage" value="/html/scormadmin/scormdetail.jsp"/>
<liferay-portlet:param name="scormId" value="<%=Long.toString(scorm.getScormId()) %>"/>

</liferay-portlet:renderURL>
<a href="<%=viewSCORMURL%>"><%=scorm.getTitle() %></a>
</liferay-ui:search-container-column-text>
<liferay-ui:search-container-column-jsp path="/html/scormadmin/admin_actions.jsp">
</liferay-ui:search-container-column-jsp>
</liferay-ui:search-container-row>
<liferay-ui:search-iterator />
</liferay-ui:search-container>