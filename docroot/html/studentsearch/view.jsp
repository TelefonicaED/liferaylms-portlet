<%@page import="com.liferay.lms.StudentSearch"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.util.comparator.*"%>
<%@include file="/init.jsp" %>

<c:set var="showScreenName" value="<%=StudentSearch.VIEW_TYPE_SCREEN_NAME %>"/>
<c:set var="showFullName" value="<%=StudentSearch.VIEW_TYPE_FULL_NAME %>"/>
<c:set var="showEmailAddress" value="<%=StudentSearch.VIEW_TYPE_EMAIL_ADDRESS %>"/>

	<c:if test="${showSearcher}">
		<div class="col-sm-12">
			<aui:form name="searchFm" method="post" action="${renderURL }" role="search">
				<%@ include file="/html/search/userSearch.jsp" %>
				<div class="search-user last">
					<aui:button value="search" type="submit"/>
				</div>
			</aui:form>
		</div>
	</c:if>
<div class="student_search">	
	<liferay-ui:search-container
			searchContainer="${searchContainer}" 
			iteratorURL="${searchContainer.iteratorURL}">
			<liferay-ui:search-container-results 
				total="${searchContainer.total }" 
				results="${searchContainer.results }"/>
			
			<liferay-ui:search-container-row modelVar="student" keyProperty="userId" className="com.liferay.portal.model.User" >
				<liferay-ui:search-container-column-text name="students"  title="students" orderable="false">
				 	<c:choose>
				 		<c:when test="${showFullName eq  showViewResults}">
				 			<liferay-ui:user-display userId="${student.userId}" />
				 		</c:when>
				 		<c:otherwise>
				 			<c:choose>
				 				<c:when test="${showScreenName eq showViewResults}">
				 					<liferay-ui:user-display userName="${student.screenName}" userId="${student.userId}"/>
				 				</c:when>
				 				<c:otherwise>
				 					<liferay-ui:user-display userName="${student.emailAddress}" userId="${student.userId}"/>
				 				</c:otherwise>
				 			</c:choose>
				 		</c:otherwise>
				 	</c:choose>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>		
			<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</div>
