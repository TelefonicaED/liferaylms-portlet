<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.lms.service.UserCompetenceLocalServiceUtil"%>
<%@ include file="/init.jsp"%>
<liferay-portlet:renderURL var="searchUUID" >
</liferay-portlet:renderURL>
<%
String uuid=ParamUtil.getString(request, "uuid","").trim();
if(!"".equals(uuid))
{
	String certificateURL=UserCompetenceLocalServiceUtil.getCertificateURL(liferayPortletResponse, uuid);
	%>
	<script>
	window.open('<%=certificateURL%>');
	</script>
	<%
}
%>
<aui:form name="searchForm" action="<%=searchUUID %>" method="POST">
<aui:input name="uuid" label="UUID" inlineLabel="false"></aui:input>
<aui:button-row>
	<aui:button type="submit" value="search"></aui:button>
</aui:button-row>
</aui:form>
