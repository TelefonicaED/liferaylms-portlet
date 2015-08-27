<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@include file="/init.jsp" %>
<%
	PortletPreferences prefs = renderRequest.getPreferences();
	String portletResource = ParamUtil.getString(request, "portletResource");
	if (Validator.isNotNull(portletResource)) {
	    prefs = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}
    String myCoursesOrder = prefs.getValue("myCoursesOrder","");
    int orderField = 0;
    if (!myCoursesOrder.equals("")) orderField = Integer.parseInt(myCoursesOrder);
    
    String labelOrder = LanguageUtil.get(pageContext,"mycourses.conf.order");
    String labelNothing = LanguageUtil.get(pageContext,"mycourses.conf.order.nothing");
    String labelNameAsc = LanguageUtil.get(pageContext,"mycourses.conf.order.nameAsc");
    String labelNameDesc = LanguageUtil.get(pageContext,"mycourses.conf.order.nameDesc");
    String labelInitDate = LanguageUtil.get(pageContext,"mycourses.conf.order.initDate");
    String labelEndDate = LanguageUtil.get(pageContext,"mycourses.conf.order.endDate");
    String messageSuccess = LanguageUtil.get(pageContext,"module-updated-successfully");
%>
    
<liferay-portlet:actionURL var="updateConfURL" portletConfiguration="true"></liferay-portlet:actionURL>

<liferay-ui:success key="success" message="<%=messageSuccess %>"/>

<form name="<portlet:namespace />fm" action="<%=updateConfURL%>" method="post">
	
	<aui:select name="myCoursesOrder" label="<%=labelOrder %>">
        <aui:option value="0" label="<%=labelNothing %>" selected="<%=orderField == 0 %>"/>
        <aui:option value="1" label="<%=labelNameAsc %>"  selected="<%=orderField == 1 %>"/>
        <aui:option value="2" label="<%=labelNameDesc %>"  selected="<%=orderField == 2 %>"/>
        <aui:option value="3" label="<%=labelInitDate %>"  selected="<%=orderField == 3 %>"/>
        <aui:option value="4" label="<%=labelEndDate %>"  selected="<%=orderField == 4 %>"/>
    </aui:select>
	<br/>
	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</form>