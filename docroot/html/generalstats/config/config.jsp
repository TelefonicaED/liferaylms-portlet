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
%>

<liferay-portlet:actionURL var="saveConfigurationURL" portletConfiguration="true"/>
<aui:form action="<%=saveConfigurationURL %>" role="form">
	<aui:input type="hidden" name="<%=Constants.CMD %>" value="<%=Constants.UPDATE %>" />
	<aui:field-wrapper label="generalstats.config.showSearch" >
		<aui:input type="checkbox" name="showSearchTagsGeneralStats" label="generalstats.config.showSearchTags" value="<%=showSearchTagsGeneralStats %>" checked="<%=showSearchTagsGeneralStats %>"/>
		<aui:input type="checkbox" name="showSearchCategoriesGeneralStats" label="generalstats.config.showSearchCategories" value="<%=showSearchCategoriesGeneralStats %>" checked="<%=showSearchCategoriesGeneralStats %>"/>
	</aui:field-wrapper>
	<aui:button-row><aui:button type="submit" value="save" /></aui:button-row>
</aui:form>