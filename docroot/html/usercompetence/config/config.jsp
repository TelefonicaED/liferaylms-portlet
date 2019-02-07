<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@include file="/init.jsp"%>
<%

PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

boolean showCompetence = GetterUtil.getBoolean(preferences.getValue("showCompetence", StringPool.TRUE));
boolean showCourse = GetterUtil.getBoolean(preferences.getValue("showCourse", StringPool.FALSE));

%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />
<aui:form action="<%= configurationURL %>" method="post" name="fm" role="form" >
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<h4>
		<liferay-ui:message key="general" />
	</h4>
	
	<aui:fieldset>
		<aui:input name="preferences--showCompetence--" type="checkbox" value="<%= showCompetence %>" label="show-competence"/>	
	</aui:fieldset>	
	
	<aui:fieldset>
		<aui:input name="preferences--showCourse--" type="checkbox" value="<%= showCourse %>" label="show-course"/>
	</aui:fieldset>
	
	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

