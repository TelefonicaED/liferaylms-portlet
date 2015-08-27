
<%@page import="com.liferay.portlet.expando.model.ExpandoTableConstants"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@include file="/init.jsp" %>
<%
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
if(course!=null && permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW))
{

PortletPreferences prefs = renderRequest.getPreferences();
String portletResource = ParamUtil.getString(request, "portletResource");
if (Validator.isNotNull(portletResource)) {
    prefs = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}
    String expandoColumn=prefs.getValue("expandoColumn","");
    String columnValue=ExpandoValueLocalServiceUtil.getData(themeDisplay.getCompanyId(),Course.class.getName(),ExpandoTableConstants.DEFAULT_TABLE_NAME,expandoColumn,course.getCourseId(),"");
    %>
   	<%=columnValue %>
    <%
	
}
else
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
    %>
