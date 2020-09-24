<%@page import="com.liferay.portal.service.PortletPreferencesLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.PortletPreferences"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.kernel.util.StringBundler"%>
<%@include file="/init.jsp"%>


<%
String path = renderRequest.getParameter("path");
String portletId = renderRequest.getParameter("portletId");
String courseId = renderRequest.getParameter("courseId");
String cur = renderRequest.getParameter("cur");
String delta = renderRequest.getParameter("delta");
Course selectedCourse = CourseLocalServiceUtil.fetchCourse(Long.parseLong(courseId));
com.liferay.portal.model.PortletPreferences modelPreferences = PortletPreferencesLocalServiceUtil.getPortletPreferences(0, PortletKeys.PREFS_OWNER_TYPE_LAYOUT, themeDisplay.getPlid(),  (String) request.getAttribute(WebKeys.PORTLET_ID));
long portletPreferencesId = 0;
String portletResource = ParamUtil.getString(request, "portletResource");

if(modelPreferences!=null){
	portletPreferencesId = modelPreferences.getPrimaryKey();
}

StringBundler sb = new StringBundler();
sb.append("<portlet-preferences >");
sb.append("<preference>");
sb.append("<name>");
sb.append("parentPortletPreferenceId");
sb.append("</name>");
sb.append("<value>");
sb.append(String.valueOf(portletPreferencesId));
sb.append("</value>");
sb.append("</preference>");
sb.append("<preference>");
sb.append("<name>");
sb.append("portletSetupShowBorders");
sb.append("</name>");
sb.append("<value>");
sb.append("false");
sb.append("</value>");
sb.append("</preference>");
sb.append("</portlet-preferences>");

String queryString = "courseId=" + courseId + "&coursesCur=" + cur + "&coursesDelta=" + delta + "&classNameId=" + PortalUtil.getClassNameId(Course.class) + "&classPK=" + courseId
+ "&backURL=" + renderRequest.getParameter("backURL");

%>

<liferay-portlet:runtime portletName="<%=portletId%>" 
	queryString="<%=queryString %>"
	defaultPreferences="<%=sb.toString() %>"/>