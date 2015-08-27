<%@page import="com.liferay.portlet.expando.model.ExpandoValue"%>
<%@page import="com.liferay.portlet.expando.model.ExpandoTableConstants"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp" %>
<%
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
if(course!=null && permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW))
{
	java.util.List<ExpandoValue> values=ExpandoValueLocalServiceUtil.getRowValues(themeDisplay.getCompanyId(),Course.class.getName(),ExpandoTableConstants.DEFAULT_TABLE_NAME,course.getCourseId(),0,100);
	if(values!=null&&values.size()>0)
	{
		
%>
<liferay-ui:custom-attribute-list className="<%= Course.class.getName()%>" classPK="<%=course.getCourseId()%>" editable="false" label="true"></liferay-ui:custom-attribute-list>

<%
	}
}
else
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
%>
