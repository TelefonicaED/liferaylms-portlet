<%@include file="/init.jsp" %>
<%
long courseId=ParamUtil.getLong(request, "courseId",0);
long roleId=ParamUtil.getLong(request, "roleId",0);
%>

<liferay-portlet:renderURL var="buscarURL">
 <liferay-portlet:param name="jspPage" value="/html/courseadmin/usersresults.jsp" />
<portlet:param name="courseId" value="<%=Long.toString(courseId) %>" />
<portlet:param name="roleId" value="<%=Long.toString(roleId) %>" />
</liferay-portlet:renderURL>

<div class="npa_search_user"> 
<aui:form name="busqusu" action="<%=buscarURL %>" method="post">
	<aui:fieldset>
		
		<aui:input name="searchForm" type="hidden" value="true" />	
					
		<aui:column>
			<aui:input label="misc.user.firstName" name="firstName" size="20" value="" />
			<aui:input label="misc.user.screenName" name="screenName" size="20" value="" />	
		</aui:column>	
					
		<aui:column>			
			<aui:input label="misc.user.lastName" name="lastName" size="20" value="" />				
			<aui:input label="misc.user.emailAddress" name="emailAddress" size="20" value="" />
		</aui:column>
	
		<aui:column>	
			<aui:select label="misc.search.allFields" name="andSearch">
				<aui:option label="misc.search.all" selected="true" value="true" />
				<aui:option label="misc.search.any" value="false" />
			</aui:select>		
		</aui:column>
		
		<aui:button-row>
			<aui:button name="searchUsers" value="search" type="submit" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>
	
</div>