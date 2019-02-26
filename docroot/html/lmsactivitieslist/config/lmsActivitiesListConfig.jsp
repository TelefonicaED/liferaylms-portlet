<%@page import="com.liferay.portal.kernel.servlet.SessionMessages"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@include file="/init.jsp" %>
<%

	PortletPreferences preferences = null;
	String portletResource = ParamUtil.getString(request, "portletResource");
	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}else{
		preferences = renderRequest.getPreferences();
	}
	String viewMode = preferences.getValue("viewMode", "0");
	boolean numerateModules = (preferences.getValue("numerateModules", "false")).compareTo("true") == 0;
%>

<liferay-portlet:actionURL var="saveConfigurationURL"  portletConfiguration="true"/>
<aui:form action="<%=saveConfigurationURL %>" role="form">
	<aui:input type="hidden" name="<%=Constants.CMD %>" value="<%=Constants.UPDATE %>" />
	<aui:select label="viewMode"  name="viewMode">
		<% boolean selected = viewMode.compareTo("0") == 0; %>
		<aui:option value="0" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "lmsactivitieslist.view.complete") %></aui:option>
		<% selected = viewMode.compareTo("1") == 0; %>
		<aui:option value="1" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "lmsactivitieslist.view.actualModule") %></aui:option>
	</aui:select>
	<br/>
	<aui:input type="checkbox" name="numerateModules" label="modulelist.numerateModules" value="<%=numerateModules %>" checked="<%=numerateModules %>"/>
	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>
