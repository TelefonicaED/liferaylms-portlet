<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.util.comparator.*"%>
<%@include file="/init.jsp" %>

<%
	String criteria = request.getParameter("criteria");

	if (criteria == null) criteria = "";	
	
	PortletURL portletURL = renderResponse.createRenderURL();
	portletURL.setParameter("jspPage","/html/studentsearch/view.jsp");
	portletURL.setParameter("criteria", criteria); 
%>


<div class="student_search"> 

	<portlet:renderURL var="buscarURL">
		<portlet:param name="jspPage" value="/html/studentsearch/view.jsp"></portlet:param>
	</portlet:renderURL>

	<aui:form name="studentsearch" action="<%=buscarURL %>" method="post">
		<aui:fieldset>
			<aui:column>
				<aui:input label="studentsearch.criteria" name="criteria" size="20" value="<%=criteria %>" />	
			</aui:column>	
			<aui:column cssClass="search_lms_button">
				<aui:button-row>
					<aui:button name="searchUsers" value="search" type="submit" />
				</aui:button-row>
			</aui:column>	
		</aui:fieldset>
	</aui:form>
	
	<liferay-ui:search-container iteratorURL="<%=portletURL%>" emptyResultsMessage="there-are-no-results" delta="5">

	   	<liferay-ui:search-container-results>
			<%
				String middleName = null;
		
				LinkedHashMap<String,Object> params=new LinkedHashMap<String, Object>();			
				params.put("usersGroups", new Long(themeDisplay.getScopeGroupId()));			
				
				OrderByComparator obc = new UserFirstNameComparator(true);
			
				List<User> userListPage = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), criteria, WorkflowConstants.STATUS_APPROVED, params, searchContainer.getStart(), searchContainer.getEnd(), obc);
				int userCount = UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), criteria, WorkflowConstants.STATUS_APPROVED, params);
						
				pageContext.setAttribute("results", userListPage);
			    pageContext.setAttribute("total", userCount);
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="user">
			<liferay-ui:user-display userId="<%=user.getUserId() %>"></liferay-ui:user-display>
		</liferay-ui:search-container-row>
		
	 	<liferay-ui:search-iterator />
	 	
	</liferay-ui:search-container>
	
</div>
