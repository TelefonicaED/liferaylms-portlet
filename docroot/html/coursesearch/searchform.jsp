<%@ include file="/init.jsp" %>
<liferay-portlet:renderURL var="searchURL" windowState="MAXIMIZED">
<liferay-portlet:param name="jspPage" value="/html/coursesearch/searchresults.jsp"></liferay-portlet:param>
</liferay-portlet:renderURL>
<%
String text=ParamUtil.getString(request, "text","").trim();

%>
<div class="buscadorcursos">
<aui:form name="searchForm" action="<%=searchURL %>" method="POST" role="search">
<aui:input name="text" label="" inlineLabel="false" value="<%=text %>"></aui:input>
<aui:button-row>
	<aui:button type="submit" value="search"></aui:button>
</aui:button-row>
</aui:form>
</div>