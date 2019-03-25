<%@page import="com.liferay.lms.service.UserCompetenceLocalServiceUtil"%>

<%@ include file="/init.jsp"%>


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

<liferay-portlet:renderURL var="searchUUID" />

<aui:form name="searchForm" action="<%=searchUUID %>" method="POST" role="search"> 
	<label><liferay-ui:message key="checkcertificate.view.title" /></label>
	<span class="checkcertificate-text"><liferay-ui:message key="checkcertificate.view.text" /></span>
	<input name="uuid" />
	<aui:button-row>
		<aui:button type="submit" value="checkcertificate.view.button"></aui:button>
	</aui:button-row>
</aui:form>