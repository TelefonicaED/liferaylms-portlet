<%@include file="/init.jsp"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.service.PortletPreferencesLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>


<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}
boolean unsubscribeIfFinished = Boolean.parseBoolean(preferences.getValue("unsubscribeIfFinished",StringPool.TRUE));
boolean modeMd = Boolean.parseBoolean(preferences.getValue("modeMd",StringPool.FALSE));
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" >
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	
	<aui:input type="checkbox" name="preferences--unsubscribeIfFinished--" label="unsubscribe.ifFinished" value="<%=unsubscribeIfFinished %>"/>
	<aui:input type="checkbox" name="preferences--modeMd--" label="inscription.config.mode-md" value="<%=modeMd %>"/>
	
	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row> 
</aui:form>