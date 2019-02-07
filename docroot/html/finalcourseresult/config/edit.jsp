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
	
	
	String modeSelected = preferences.getValue("showCalificationMode", "");
	

	boolean showAllways = modeSelected.equals("showAllways");
	boolean showOnlyWhenFinishDate =  modeSelected.equals("showOnlyWhenFinishDate");

	
%>

<liferay-portlet:actionURL var="saveConfigurationURL" portletConfiguration="true"/>
<aui:form action="<%=saveConfigurationURL %>" role="form">
	<aui:input type="hidden" name="<%=Constants.CMD %>" value="<%=Constants.UPDATE %>" />
	<aui:field-wrapper label="courseresult.mode.view" >
		<aui:input type="radio" name="showCalificationMode" label="courseresult.mode.showAllways" value="showAllways" checked="<%=showAllways %>"/>
		<aui:input type="radio" name="showCalificationMode" label="courseresult.mode.showOnlyWhenFinishDate" value="showOnlyWhenFinishDate" checked="<%=showOnlyWhenFinishDate %>"/>
	</aui:field-wrapper>
	<aui:button-row><aui:button type="submit" value="save" /></aui:button-row>
</aui:form>