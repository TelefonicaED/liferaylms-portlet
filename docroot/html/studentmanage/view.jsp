<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.liferay.lms.service.impl.CourseResultLocalServiceImpl"%>
<%@page import="com.liferay.portal.util.comparator.UserFirstNameComparator"%>
<%@page import="com.liferay.portal.kernel.dao.orm.CustomSQLParam"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.service.UserGroupRoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.UserGroupRoleService"%>
<%@page import="com.liferay.portal.service.UserGroupRoleServiceUtil"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.security.permission.PermissionCheckerFactoryUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Team"%>
<%@page import="com.liferay.portal.service.TeamLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.model.CourseResult"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<jsp:useBean id="course" type="com.liferay.lms.model.Course" scope="request"/>
<%@include file="/init.jsp" %>

<%	
	
	PortletPreferences preferences = null;
	
	String portletResource = ParamUtil.getString(request, "portletResource");
	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}else{
		preferences = renderRequest.getPreferences();
	}
	boolean showActionSocial = GetterUtil.getBoolean(preferences.getValue("showActionSocial", StringPool.FALSE),true);
	boolean showActionAudit = GetterUtil.getBoolean(preferences.getValue("showActionAudit", StringPool.FALSE),true);
	
	CalificationType ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());
%>

<div class="student_search"> 
	
	<aui:form name="fm" action="${searchURL }" method="POST">
	
		<liferay-ui:search-container
					searchContainer="${searchContainer}"
					iteratorURL="${searchContainer.iteratorURL}" >
				
					<liferay-ui:search-form
						page="/html/search/usersSearchform.jsp"
						searchContainer="${searchContainer}"
			            servletContext="<%= this.getServletConfig().getServletContext() %>"
			        />
				
					<liferay-ui:search-container-results 
						total="${searchContainer.total }" 
						results="${searchContainer.results }"
					/>
			
			<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="usr">
			
			<liferay-ui:search-container-column-text name="name" >
				<liferay-ui:user-display userId="${usr.userId}"/>
			</liferay-ui:search-container-column-text>
			
			<liferay-ui:search-container-column-text name="result">
				<%			
				CourseResult courseResult=CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(course.getCourseId(), usr.getUserId());
				String result="-";
				String status="not-started";
				
				if(courseResult!=null){
					status="started";
					result=ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), courseResult.getResult())+ct.getSuffix(course.getGroupCreatedId());
					if(courseResult.getPassedDate()!=null){
						status="not-passed"	;
					}
					if(courseResult.isPassed()){
						status="passed"	;
					}
				}
				
				%>
				<%=result %>
				<% if(status.equals("passed")){%>
						<liferay-ui:icon image="checked" message="passed"></liferay-ui:icon>
				<%} else if(status.equals("not-passed")){%>
						<liferay-ui:icon image="close" message="not-passed"></liferay-ui:icon>
				<%} else if(status.equals("started")){%>
						<liferay-ui:icon image="unchecked" message="unchecked"></liferay-ui:icon>
				<%}%>
			</liferay-ui:search-container-column-text>
			
			<liferay-ui:search-container-column-text name="actions" >
				<liferay-portlet:renderURL var="viewGradeURL">
				<liferay-portlet:param name="jspPage" value="/html/gradebook/userdetails.jsp"></liferay-portlet:param>
				<liferay-portlet:param name="userId" value="${usr.userId}"></liferay-portlet:param>
				<liferay-portlet:param name="returnurl" value="${returnURL}"></liferay-portlet:param>
				</liferay-portlet:renderURL>
				
				<liferay-portlet:renderURL var="viewactivityURL">
				<liferay-portlet:param name="jspPage" value="/html/studentmanage/lastsocialactivity.jsp"></liferay-portlet:param>
				<liferay-portlet:param name="userId" value="${usr.userId}"></liferay-portlet:param>
				<liferay-portlet:param name="returnurl" value="${returnURL}"></liferay-portlet:param>	
				</liferay-portlet:renderURL>
				
				<liferay-portlet:renderURL var="viewtriesURL">
				<liferay-portlet:param name="jspPage" value="/html/studentmanage/lastactivitytries.jsp"></liferay-portlet:param>
				<liferay-portlet:param name="userId" value="${usr.userId}"></liferay-portlet:param>
				<liferay-portlet:param name="returnurl" value="${returnURL}"></liferay-portlet:param>	
				</liferay-portlet:renderURL>
				
				<liferay-ui:icon-menu>
					<liferay-ui:icon image="edit" message="searchresults.viewresults" url="<%=viewGradeURL%>" />
					<c:if test="<%= showActionSocial %>">
						<liferay-ui:icon image="group" message="studentsearch.result.actions.social" url="<%=viewactivityURL%>" />
					</c:if>
					<c:if test="<%= showActionAudit %>">
						<liferay-ui:icon image="group" message="studentsearch.result.actions.audit" url="<%=viewtriesURL%>" />
					</c:if>
				</liferay-ui:icon-menu>
			</liferay-ui:search-container-column-text>
			
			</liferay-ui:search-container-row>
			
		 	<liferay-ui:search-iterator />
		 	
		</liferay-ui:search-container>
	</aui:form>
</div>
