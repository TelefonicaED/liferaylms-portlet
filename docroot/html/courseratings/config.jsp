<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@ include file="/init.jsp" %>

<%   
	PortletPreferences prefs = renderRequest.getPreferences();
	String portletResource = ParamUtil.getString(request, "portletResource");	
	if (Validator.isNotNull(portletResource)){
		prefs = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}
	boolean rateParentCourse = Boolean.valueOf(prefs.getValue("rateParentCourse", "false"));
%>


<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="${configurationURL.toString() }" method="post" name="fm" >
    
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<aui:input type="checkbox" name="preferences--rateParentCourse--" label="course-ratings.rate-parent-course" value="<%=rateParentCourse %>"/>

	<aui:button-row>
       <aui:button type="submit" />
    </aui:button-row>

</aui:form>	