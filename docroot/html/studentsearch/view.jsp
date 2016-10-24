<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.util.comparator.*"%>
<%@include file="/init.jsp" %>

<div class="student_search">
	<liferay-portlet:actionURL var="renderURL" />
	<c:if test="${showSearcher}">
		<aui:form action="${renderURL}" name="searchFm">
			<liferay-ui:search-form page="/html/search/userSearch.jsp"
				searchContainer="${searchContainer}"
				servletContext="<%= this.getServletConfig().getServletContext() %>">
			</liferay-ui:search-form>
		</aui:form>
	</c:if>
	<c:if test="${!showSearcher and existTeams}">
		<aui:form action="${renderURL}" name="searchTeamsFm">
			<aui:select name="teamId" label="team">
				<aui:option label="" value="0"></aui:option>
				<c:forEach items="${teams}" var="team">
					<aui:option  value="${team.teamId}" label="${team.name}" selected="${team.teamId == teamId}"></aui:option>
				</c:forEach>
			</aui:select>	
			<aui:button value="search"/>
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
