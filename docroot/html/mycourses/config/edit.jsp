<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@include file="/init.jsp" %>
<%
	PortletPreferences prefs = renderRequest.getPreferences();
	String portletResource = ParamUtil.getString(request, "portletResource");
	if (Validator.isNotNull(portletResource)) {
	    prefs = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}

    int orderField = GetterUtil.getInteger(prefs.getValue("myCoursesOrder",""), 0);
%>
    
<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form name="fm" action="<%=configurationURL%>" method="post">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	
	<aui:select name="preferences--myCoursesOrder--" label="mycourses.conf.order">
        <aui:option value="0" label="mycourses.conf.order.nothing" selected="<%=orderField == 0 %>"/>
        <aui:option value="1" label="mycourses.conf.order.nameAsc"  selected="<%=orderField == 1 %>"/>
        <aui:option value="2" label="mycourses.conf.order.nameDesc"  selected="<%=orderField == 2 %>"/>
        <aui:option value="3" label="mycourses.conf.order.initDate"  selected="<%=orderField == 3 %>"/>
        <aui:option value="4" label="mycourses.conf.order.endDate"  selected="<%=orderField == 4 %>"/>
        <aui:option value="5" label="mycourses.conf.order.inscription-date.asc"  selected="<%=orderField == 5 %>"/>
        <aui:option value="6" label="mycourses.conf.order.inscription-date.desc"  selected="<%=orderField == 6 %>"/>
    </aui:select>

	<aui:button type="submit" />
</aui:form>