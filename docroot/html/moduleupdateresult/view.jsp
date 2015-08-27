<%@page import="com.liferay.portal.util.comparator.UserFirstNameComparator"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.lms.service.moduleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.module"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ include file="/init.jsp" %>

<%
	String criteria = request.getParameter("criteria");

	if (criteria == null) criteria = "";	
	
	PortletURL portletURL = renderResponse.createRenderURL();
	portletURL.setParameter("jspPage","/html/moduleupdateresult/view.jsp");
	portletURL.setParameter("criteria", criteria); 
%>

<script type="text/javascript">
	function openLogs(ruta){
		window.open(ruta, "File" , "width=1000,height=600,fullscreen=yes,scrollbars=yes");
	}
</script>

	<portlet:actionURL name="updateResult" var="updateResultURL"/>

	<portlet:renderURL var="buscarURL">
		<portlet:param name="jspPage" value="/html/moduleupdateresult/view.jsp"></portlet:param>
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
			
				List<User> userListPage = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), criteria, true, params, searchContainer.getStart(), searchContainer.getEnd(), obc);
				int userCount = UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), criteria, true, params);
						
				pageContext.setAttribute("results", userListPage);
			    pageContext.setAttribute("total", userCount);
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="user">
			<liferay-ui:search-container-column-text name="studentsearch.user.firstName" title="studentsearch.user.firstName"><%=user.getFullName() %></liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="studentsearch.user.emailAddress" title="studentsearch.user.firstName"><a onClick="document.getElementById('<portlet:namespace />email').value='<%=user.getEmailAddress() %>';"  style="Cursor:pointer;"><%=user.getEmailAddress() %></a> </liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
		
	 	<liferay-ui:search-iterator />
	 	
	</liferay-ui:search-container>


	<aui:form name="form" action="<%=updateResultURL%>"  method="post">
	
		<aui:select label="Modulo" name="moduleId" >
		
		<%
		java.util.List<module> modules = moduleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
		for(module theModule:modules)
		{
			%>
				<aui:option value="<%=theModule.getModuleId() %>"><%=theModule.getTitle() %></aui:option>
			<% 
		}
		%>
	
		</aui:select>
		
		<aui:input name="email"></aui:input>
		<aui:input name="allusers" label="moduleupdateresult.allusers" type="checkbox" value="<%=false %>"></aui:input>
		<aui:button-row>
			<aui:button type="submit" name="acept"></aui:button>
		</aui:button-row>
	
	</aui:form>
	<div>
		<a onClick="openLogs('/custom_logs/ModuleUpdate.txt')" style="Cursor:pointer;">ModuleUpdate.txt</a>
	</div>