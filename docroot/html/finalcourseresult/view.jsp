<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseResultLocalService"%>
<%@page import="com.liferay.lms.model.CourseResult"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ include file="/init.jsp" %>


<%

PortletPreferences prefs = null;
String portletResource = ParamUtil.getString(request, "portletResource");
if (Validator.isNotNull(portletResource)) {
    prefs = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}else{
	prefs = renderRequest.getPreferences();
}
//Boolean showAllways= Boolean.parseBoolean(prefs.getValue("showAllways","false"));
//Boolean showOnlyWhenFinishDate= Boolean.parseBoolean(prefs.getValue("showOnlyWhenFinishDate","false"));
String modeSelected = prefs.getValue("showCalificationMode", "");

Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), themeDisplay.getUserId());

if(modeSelected.equals("showAllways")){
%>
	<liferay-ui:message key="courseresult.mode.result"  arguments="<%=new Object[]{courseResult.getResult()}%>"/>

<%
}else{
	if(courseResult.getAllowFinishDate()!=null){
	%>
	<liferay-ui:message key="courseresult.mode.result"  arguments="<%=new Object[]{courseResult.getResult()}%>"/>

	<%
	}
}


%>



