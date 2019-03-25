<%@page import="com.liferay.lms.StudentSearch"%>
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
int showResultsType = GetterUtil.getInteger(preferences.getValue("showResultsType", String.valueOf(StudentSearch.VIEW_TYPE_FULL_NAME)));

%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />
<aui:form action="<%= configurationURL %>" method="post" name="fm" role="form" >
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<h4><liferay-ui:message key="general"></liferay-ui:message> </h4>
	<aui:fieldset>
		<aui:input name="preferences--showSearcher--" type="checkbox" value="<%= showSearcher %>" label="show-searcher"/>	
	</aui:fieldset>	
	
	<h4><liferay-ui:message key="results"></liferay-ui:message> </h4>
	<aui:fieldset>
		<aui:input name="preferences--showResultsType--" type="radio" value="<%=StudentSearch.VIEW_TYPE_FULL_NAME %>" label="show-full-name" checked="<%=(StudentSearch.VIEW_TYPE_FULL_NAME == showResultsType)  %>"/>
		<aui:input name="preferences--showResultsType--" type="radio" value="<%=StudentSearch.VIEW_TYPE_EMAIL_ADDRESS %>" label="show-email" checked="<%=(StudentSearch.VIEW_TYPE_EMAIL_ADDRESS == showResultsType)%>"/>
		<aui:input name="preferences--showResultsType--" type="radio" value="<%=StudentSearch.VIEW_TYPE_SCREEN_NAME %>" label="show-screen-name" checked="<%=(StudentSearch.VIEW_TYPE_SCREEN_NAME == showResultsType)%>"/>
	</aui:fieldset>
	
	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

