<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@include file="/init.jsp" %>
<%
PortletPreferences prefs = renderRequest.getPreferences();
String portletResource = ParamUtil.getString(request, "portletResource");
if (Validator.isNotNull(portletResource)) {
    prefs = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}
    String expandoColumn=prefs.getValue("expandoColumn","");
    %>
    <liferay-portlet:actionURL var="updateConfURL" portletConfiguration="true">
</liferay-portlet:actionURL>
<form name="<portlet:namespace />fm" action="<%=updateConfURL%>" method="post">
<aui:input  label="expandoColumn" name="expandoColumn" value="<%=expandoColumn %>"></aui:input>
	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</form>
<%

%>