

<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@ include file="/init.jsp" %>

<%
	PortletPreferences preferences = null;
	String pages = null;

	String portletResource = ParamUtil.getString(request, "portletResource");

	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}
	else{
		preferences = renderRequest.getPreferences();
	}
	
	if(preferences!=null&&preferences.getValue("pages",null)!=null){
		pages = preferences.getValue("pages",null);
	}
	
	if(pages==null){
		pages = PrefsPropsUtil.getString("lms.competences.pages", "A4");
	}
%>

<portlet:renderURL var="cancelURL">
			</portlet:renderURL>
<portlet:actionURL var="savePagesURL" name="savePages" />	
<liferay-ui:header backURL="<%=cancelURL%>" showBackURL="<%=Boolean.TRUE%>" title="" />
<aui:form name="fm" action="<%=savePagesURL%>"  method="post">
<aui:input name="pages" value="<%=pages %>"/>


	<aui:button-row>
		<aui:button type="submit"></aui:button>							
		<aui:button onClick="<%=cancelURL %>" type="cancel" />
	</aui:button-row>
</aui:form>