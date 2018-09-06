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
	boolean showLockedModulesIcon = (preferences.getValue("showLockedModulesIcon", "false")).compareTo("true") == 0;
	boolean showModuleIcon = (preferences.getValue("showModuleIcon", "true")).compareTo("true") == 0;
	boolean numerateModules = (preferences.getValue("numerateModules", "false")).compareTo("true") == 0;
	boolean moduleTitleLinkable = (preferences.getValue("moduleTitleLinkable", "false")).compareTo("true") == 0;
	boolean showPercentDone = (preferences.getValue("showPercentDone", "true")).compareTo("true") == 0;
	boolean showModuleStartDate = (preferences.getValue("showModuleStartDate", "true")).compareTo("true") == 0;
	boolean showModuleEndDate = (preferences.getValue("showModuleEndDate", "true")).compareTo("true") == 0;
	boolean allowEditionMode = (preferences.getValue("allowEditionMode", "false")).compareTo("true") == 0;
	boolean allowAccessWhenFinishedButNotClosed = (preferences.getValue("allowAccessWhenFinishedButNotClosed", "false")).compareTo("true") == 0;
	boolean dragAndDrop = Boolean.parseBoolean(preferences.getValue("dragAndDrop", "true"));
	boolean showActivities = Boolean.parseBoolean(preferences.getValue("showActivities", "false"));
%>

<liferay-portlet:actionURL var="saveConfigurationURL"  portletConfiguration="true"/>
<aui:form action="<%=saveConfigurationURL %>" >
	<aui:input type="hidden" name="<%=Constants.CMD %>" value="<%=Constants.UPDATE %>" />
	<aui:input type="checkbox" name="showLockedModulesIcon" label="modulelist.showLockedModulesIcon" value="<%=showLockedModulesIcon %>" checked="<%=showLockedModulesIcon %>"/>
	<aui:input type="checkbox" name="showModuleIcon" label="modulelist.showModuleIcon" value="<%=showModuleIcon %>" checked="<%=showModuleIcon %>"/>
	<aui:input type="checkbox" name="numerateModules" label="modulelist.numerateModules" value="<%=numerateModules %>" checked="<%=numerateModules %>"/>
	<aui:input type="checkbox" name="moduleTitleLinkable" label="modulelist.moduleTitleLinkable" value="<%=moduleTitleLinkable %>" checked="<%=moduleTitleLinkable %>"/>
	<aui:input type="checkbox" name="showPercentDone" label="modulelist.showPercentDone" value="<%=showPercentDone %>" checked="<%=showPercentDone %>"/>
	<aui:input type="checkbox" name="showModuleStartDate" label="modulelist.showModuleStartDate" value="<%=showModuleStartDate %>" checked="<%=showModuleStartDate %>"/>
	<aui:input type="checkbox" name="showModuleEndDate" label="modulelist.showModuleEndDate" value="<%=showModuleEndDate %>" checked="<%=showModuleEndDate %>"/>
	<aui:input type="checkbox" name="allowEditionMode" label="modulelist.allowEditionMode" value="<%=allowEditionMode %>" checked="<%=allowEditionMode %>"/>
	<aui:input type="checkbox" name="allowAccessWhenFinishedButNotClosed" label="modulelist.allowAccessWhenFinishedButNotClosed" value="<%=allowAccessWhenFinishedButNotClosed %>" checked="<%=allowAccessWhenFinishedButNotClosed %>"/>
	<aui:input type="checkbox" name="dragAndDrop" label="modulelist.dragAndDrop" value="<%=dragAndDrop %>" checked="<%=dragAndDrop %>"/>
	<aui:input type="checkbox" name="showActivities" label="modulelist.showActivities" value="<%=showActivities %>" checked="<%=showActivities %>"/>
	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>