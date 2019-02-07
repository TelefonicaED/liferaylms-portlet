<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>

<%@ include file="/init.jsp" %>

<%
	PortletPreferences preferences = null;
	String portletResource = ParamUtil.getString(request, "portletResource");
	
	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}else{
		preferences = renderRequest.getPreferences();
	}
	boolean showExport = (preferences.getValue("showExport", "false")).compareTo("true") == 0;
%>

<liferay-portlet:actionURL var="saveConfigurationURL" portletConfiguration="true"/>
<aui:form action="<%=saveConfigurationURL %>" role="form" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input type="hidden" name="<%=Constants.CMD %>" value="<%=Constants.UPDATE %>" />
	<aui:input type="checkbox" name="preferences--showExport--" label="userprogress.config.showExport" value="<%=showExport %>" checked="<%=showExport %>"/>
	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>

<aui:script use="liferay-util-list-fields">
	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>