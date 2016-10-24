<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.service.PortletPreferencesLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.LayoutSetPrototype"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.KeyValuePairComparator"%>
<%@page import="com.liferay.portal.kernel.util.KeyValuePair"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.portal.kernel.util.SetUtil"%>
<%@page import="java.util.Set"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.liferay.portal.model.UserGroup"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.UserGroupLocalServiceUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Arrays"%>
<%@include file="/init.jsp"%>
<%

PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

boolean showSearcher = GetterUtil.getBoolean(preferences.getValue("showSearcher", StringPool.TRUE));
boolean showScreenName = GetterUtil.getBoolean(preferences.getValue("showScreenName", StringPool.TRUE));
boolean showEmail = GetterUtil.getBoolean(preferences.getValue("showEmail", StringPool.FALSE));

%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />
<aui:form action="<%= configurationURL %>" method="post" name="fm"  >
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<h4><liferay-ui:message key="general"></liferay-ui:message> </h4>
	<aui:fieldset>
		<aui:input name="preferences--showSearcher--" type="checkbox" value="<%= showSearcher %>" label="show-searcher"/>	
	</aui:fieldset>	
	
	<h4><liferay-ui:message key="searcher"></liferay-ui:message> </h4>
	<aui:fieldset>
		<aui:input name="preferences--showScreenName--" type="checkbox" value="<%= showScreenName %>" label="show-screen-name"/>
		<aui:input name="preferences--showEmail--" type="checkbox" value="<%= showEmail %>" label="show-email"/>
	</aui:fieldset>
	
	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

