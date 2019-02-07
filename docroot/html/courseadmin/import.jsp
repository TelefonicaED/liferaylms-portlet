<%@page import="com.liferay.portal.service.GroupServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@ page import="com.liferay.portal.LARFileException" %>
<%@ page import="com.liferay.portal.LARTypeException" %>
<%@ page import="com.liferay.portal.LayoutImportException" %>
<%@page import="com.liferay.portal.model.LayoutSetPrototype"%>
<%@page import="com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataException" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandler" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerBoolean" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerChoice" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerControl" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerKeys" %>
<%@ page import="com.liferay.portal.kernel.lar.UserIdStrategy" %>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>

<%@ include file="/init.jsp" %>	

<liferay-ui:error key="error-learning-activity-type" message="course-admin.error-learning-activity-type" />

<%
	String groupId = request.getParameter("groupId");
	Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(Long.parseLong(groupId));
%>
<liferay-portlet:renderURL var="backURL"></liferay-portlet:renderURL>
<liferay-ui:header title="<%= course != null ? course.getTitle(themeDisplay.getLocale()) : \"course\" %>" backURL="<%=backURL %>"></liferay-ui:header>

<portlet:actionURL name="importCourse" var="importURL">
	<portlet:param name="groupId" value="<%= groupId %>" />
</portlet:actionURL>
	
<aui:form name="form" action="<%=importURL%>" method="post" enctype="multipart/form-data" role="form">
	
	
	<liferay-ui:error exception="<%= LARFileException.class %>" message="please-specify-a-lar-file-to-import" />
	<liferay-ui:error exception="<%= LARTypeException.class %>" message="please-import-a-lar-file-of-the-correct-type" />
	<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />
	
	<aui:input label="import-a-lar-file-to-overwrite-the-selected-data" name="importFileName" size="50" type="file" >
		<aui:validator name="required" errorMessage="field.required"></aui:validator>
	</aui:input>
	
	<aui:input label="delete-portlet-data-before-importing" name="<%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>" type="checkbox" checked="<%= true %>"  />
	
	<div class="options" style="display:none;">
	
		<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= true %>" />
	
		<liferay-ui:message key="what-would-you-like-to-export" />
		
		<%
		String taglibOnChange = renderResponse.getNamespace() + "toggleChildren(this, '" + renderResponse.getNamespace() + "portletDataList');" ;
		%>
		
		<aui:input checked="<%= true %>" label="data" name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="checkbox" onchange="<%= taglibOnChange %>" />
		
	</div>
		
	<aui:button-row>
		<aui:button type="submit" value="import" />
	</aui:button-row>
</aui:form>