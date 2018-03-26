<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.util.comparator.*"%>
<%@include file="/init.jsp" %>


	<c:if test="${showSearcher}">
		<div class="col-sm-12">
			<aui:form name="searchFm" method="post" action="${renderURL }">
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
				 	<liferay-ui:user-display userId="${student.userId}" />
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>		
			<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</div>
