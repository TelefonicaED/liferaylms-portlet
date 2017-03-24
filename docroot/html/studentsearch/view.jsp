<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.util.comparator.*"%>
<%@include file="/init.jsp" %>

<div class="student_search">
	<liferay-portlet:renderURL var="renderURL" />
	<c:if test="${showSearcher}">
		<aui:form action="${renderURL}" name="searchFm">
			<liferay-ui:search-form page="/html/shared/usersSearchform.jsp"
				searchContainer="${searchContainer}"
				servletContext="<%= this.getServletConfig().getServletContext() %>">
			</liferay-ui:search-form>
		</aui:form>
	</c:if>
	
	<liferay-ui:search-container
			searchContainer="${searchContainer}" 
			iteratorURL="${searchContainer.iteratorURL}">
			<liferay-ui:search-container-results 
				total="${searchContainer.total }" 
				results="${searchContainer.results }"/>
			
			<liferay-ui:search-container-row modelVar="student" keyProperty="userId" className="com.liferay.portal.model.User" >
				<liferay-ui:search-container-column-text name="students"  title="students" orderable="false">
				 	<liferay-ui:user-display userId="${student.userId}" />
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>		
			<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</div>
