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
	
	boolean showSearchTagsGeneralStats = 		preferences.getValue("showSearchTagsGeneralStats", "false").equals("true");
	boolean showSearchCategoriesGeneralStats = 	preferences.getValue("showSearchCategoriesGeneralStats", "true").equals("true");
	
	boolean showRegistered = 	preferences.getValue("showRegistered", "true").equals("true");
	boolean showInit = 	preferences.getValue("showInit", "true").equals("true");
	boolean showFinished = 	preferences.getValue("showFinished", "true").equals("true");
	boolean showAvgResult = 	preferences.getValue("showAvgResult", "true").equals("true");
	boolean showModules = 	preferences.getValue("showModules", "true").equals("true");
	boolean showActivities = 	preferences.getValue("showActivities", "true").equals("true");
	boolean showPassed = 	preferences.getValue("showPassed", "false").equals("true");
	boolean showFailed = 	preferences.getValue("showFailed", "false").equals("true");
	boolean showCourseClosed = preferences.getValue("showCourseClosed", "true").equals("true");
	
%>

<liferay-portlet:actionURL var="saveConfigurationURL" portletConfiguration="true"/>
<aui:form action="<%=saveConfigurationURL %>" role="form">
	<aui:input type="hidden" name="<%=Constants.CMD %>" value="<%=Constants.UPDATE %>" />
	<aui:field-wrapper label="generalstats.config.showSearch" >
		<aui:input type="checkbox" name="showSearchTagsGeneralStats" label="generalstats.config.showSearchTags" value="<%=showSearchTagsGeneralStats %>" checked="<%=showSearchTagsGeneralStats %>"/>
		<aui:input type="checkbox" name="showSearchCategoriesGeneralStats" label="generalstats.config.showSearchCategories" value="<%=showSearchCategoriesGeneralStats %>" checked="<%=showSearchCategoriesGeneralStats %>"/>
		<aui:input type="checkbox" name="showRegistered" label="generalstats.config.showRegistered" value="<%=showRegistered %>" checked="<%=showRegistered %>"/>
		<aui:input type="checkbox" name="showInit" label="generalstats.config.showInit" value="<%=showInit %>" checked="<%=showInit %>"/>
		<aui:input type="checkbox" name="showFinished" label="generalstats.config.showFinished" value="<%=showFinished %>" checked="<%=showFinished %>"/>
		<aui:input type="checkbox" name="showAvgResult" label="generalstats.config.showAvgResult" value="<%=showAvgResult %>" checked="<%=showAvgResult %>"/>
		<aui:input type="checkbox" name="showModules" label="generalstats.config.showModules" value="<%=showModules %>" checked="<%=showModules %>"/>
		<aui:input type="checkbox" name="showActivities" label="generalstats.config.showActivities" value="<%=showActivities %>" checked="<%=showActivities %>"/>
		<aui:input type="checkbox" name="showPassed" label="generalstats.config.showPassed" value="<%=showPassed %>" checked="<%=showPassed %>"/>
		<aui:input type="checkbox" name="showFailed" label="generalstats.config.showFailed" value="<%=showFailed %>" checked="<%=showFailed %>"/>
		<aui:input type="checkbox" name="showCourseClosed" label="generalstats.config.showCourseClosed" value="<%=showCourseClosed %>" checked="<%=showCourseClosed %>"/>
	</aui:field-wrapper>
	<aui:button-row><aui:button type="submit" value="save" /></aui:button-row>
</aui:form>