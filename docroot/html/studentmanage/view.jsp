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
<%@include file="/init.jsp" %>

<%
	String criteria = request.getParameter("criteria");
	long teamId=ParamUtil.getLong(request, "teamId",0);

	if (criteria == null) criteria = "";	
	
	PortletURL portletURL = renderResponse.createRenderURL();
	portletURL.setParameter("jspPage","/html/studentmanage/view.jsp");
	portletURL.setParameter("criteria", criteria); 
	portletURL.setParameter("teamId", Long.toString(teamId)); 
	
	Course course=CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
	long courseId=course.getCourseId();
	java.util.List<Team> userTeams=TeamLocalServiceUtil.getUserTeams(themeDisplay.getUserId(), themeDisplay.getScopeGroupId());
	
	Team theTeam=null;
	boolean hasNullTeam=false;
	if(teamId>0 && (TeamLocalServiceUtil.hasUserTeam(themeDisplay.getUserId(), teamId)||userTeams.size()==0))
	{		
		theTeam=TeamLocalServiceUtil.fetchTeam(teamId);	
	}
	else
	{
		if(userTeams!=null&& userTeams.size()>0)
		{
			theTeam=userTeams.get(0);	
			teamId=theTeam.getTeamId();
		}
	}
	if(userTeams.size()==0)
	{
		userTeams=TeamLocalServiceUtil.getGroupTeams(themeDisplay.getScopeGroupId());
		hasNullTeam=true;
	}
	
	
	PortletPreferences preferences = null;
	
	String portletResource = ParamUtil.getString(request, "portletResource");
	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}else{
		preferences = renderRequest.getPreferences();
	}
	boolean showActionSocial = GetterUtil.getBoolean(preferences.getValue("showActionSocial", StringPool.FALSE),true);
	boolean showActionAudit = GetterUtil.getBoolean(preferences.getValue("showActionAudit", StringPool.FALSE),true);
	
	
	LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());

	long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
	long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
	
	
%>

<liferay-portlet:renderURL var="returnurl">
	<liferay-portlet:param name="jspPage" value="/html/studentmanage/view.jsp"></liferay-portlet:param>
</liferay-portlet:renderURL>

<div class="student_search"> 

	<portlet:renderURL var="buscarURL">
		<portlet:param name="jspPage" value="/html/studentmanage/view.jsp"></portlet:param>
	</portlet:renderURL>
<%if(theTeam==null&&(userTeams==null||userTeams.size()==0))
{
	%>
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
	<%
}
else	
{
%>
	<aui:form name="studentsearch" action="<%=buscarURL %>" method="post">
		<aui:fieldset>
			<aui:column>
				<aui:input label="studentsearch.criteria" name="criteria" size="20" value="<%=criteria %>" />	
				<aui:select name="teamId" id="teamIdSelect" label="team">
				<%
				if(hasNullTeam)
				{
					if(teamId==0)
					{
					%>
					<aui:option label="--" value="0" selected="true"></aui:option>
					<%
					}
					else
					{
						%>
						<aui:option label="--" value="0"></aui:option>
						<%
					}
				}
				for(Team team:userTeams)
				{
					if(teamId==team.getTeamId())
					{
					%>
					<aui:option label="<%=team.getName() %>" value="<%=team.getTeamId() %>" selected="true"></aui:option>
					<%
					}
					else
					{
					%>
					<aui:option label="<%=team.getName() %>" value="<%=team.getTeamId() %>"></aui:option>
					<%
					}
				}
				%>
				</aui:select>
			</aui:column>	
			<aui:button-row>
				<aui:button name="searchUsers" value="select" type="submit" />
			</aui:button-row>
		</aui:fieldset>
	</aui:form>
	<%if(theTeam!=null)
	{ %>
	<liferay-ui:header title="<%=theTeam.getName() %>" showBackURL="false"></liferay-ui:header>
	
<%
	}
}
	%>
	<liferay-ui:search-container iteratorURL="<%=portletURL%>" emptyResultsMessage="there-are-no-results" delta="10" deltaConfigurable="true">

	   	<liferay-ui:search-container-results>
			<%
			List<User> userListPage  = null;
			String middleName = null;
			LinkedHashMap userParams = new LinkedHashMap();

			if(theTeam==null){
				if(criteria.trim().length()==0){
					userParams.put("usersGroups", new Long(themeDisplay.getScopeGroupId()));
					userListPage = UserLocalServiceUtil.getGroupUsers(themeDisplay.getScopeGroupId());
				}else{
					userParams.put("usersGroups", new Long(themeDisplay.getScopeGroupId()));
					OrderByComparator obc = null;
					userListPage  = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), criteria, 0, userParams, searchContainer.getStart(), searchContainer.getEnd(), obc);
				}
			}else{
				userParams.put("usersGroups", theTeam.getGroupId());
				userParams.put("usersTeams", theTeam.getTeamId());
				OrderByComparator obc = null;
				userListPage  = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), criteria, 0, userParams, searchContainer.getStart(), searchContainer.getEnd(), obc);
			}
			
				List<User> finalUserList = new LinkedList<User>();
				
				Iterator<User> ituserlistpage = userListPage.iterator();
				
				while(ituserlistpage.hasNext()){
					User u = ituserlistpage.next();
					
					boolean isStudent = (!(PermissionCheckerFactoryUtil.create(u).hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model", themeDisplay.getScopeGroupId(), "VIEW_RESULTS"))
								&&
								!UserGroupRoleLocalServiceUtil.hasUserGroupRole(u.getUserId(), themeDisplay.getScopeGroupId(), teacherRoleId)
								&&
								!UserGroupRoleLocalServiceUtil.hasUserGroupRole(u.getUserId(), themeDisplay.getScopeGroupId(), editorRoleId));
							//System.out.println("User "+u.getFullName()+" isStudent "+ isStudent);
					
					if(isStudent)finalUserList.add(u);
				}
				
				//int userCount = UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), criteria, 0, params);
				int userCount = finalUserList.size();
						
				//pageContext.setAttribute("results", userListPage);
				pageContext.setAttribute("results", finalUserList);
			    	pageContext.setAttribute("total", userCount);
			
			
			    	
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="user">
		<liferay-ui:search-container-column-text name="name" >
			<liferay-ui:user-display userId="<%=user.getUserId() %>"></liferay-ui:user-display>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="result">
			<%			
			CourseResult courseResult=CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(courseId, user.getUserId());
			String result="-";
			String status="not-started";
			if(courseResult!=null)
			{
				status="started";
				result=Long.toString(courseResult.getResult());
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
							 	<liferay-ui:icon image="checked" alt="passed"></liferay-ui:icon>
							<%} else if(status.equals("not-passed")){%>
							 	<liferay-ui:icon image="close" alt="not-passed"></liferay-ui:icon>
							<%} else if(status.equals("started")){%>
						 		<liferay-ui:icon image="unchecked" alt="unchecked"></liferay-ui:icon>
						 	<%}%>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="actions" >
			<liferay-portlet:renderURL var="viewGradeURL">
			<liferay-portlet:param name="jspPage" value="/html/gradebook/userdetails.jsp"></liferay-portlet:param>
			<liferay-portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"></liferay-portlet:param>
			<liferay-portlet:param name="returnurl" value="<%=returnurl %>"></liferay-portlet:param>
			</liferay-portlet:renderURL>
			
			<liferay-portlet:renderURL var="viewactivityURL">
			<liferay-portlet:param name="jspPage" value="/html/studentmanage/lastsocialactivity.jsp"></liferay-portlet:param>
			<liferay-portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"></liferay-portlet:param>
			<liferay-portlet:param name="returnurl" value="<%=returnurl %>"></liferay-portlet:param>	
			</liferay-portlet:renderURL>
			
			<liferay-portlet:renderURL var="viewtriesURL">
			<liferay-portlet:param name="jspPage" value="/html/studentmanage/lastactivitytries.jsp"></liferay-portlet:param>
			<liferay-portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"></liferay-portlet:param>
			<liferay-portlet:param name="returnurl" value="<%=returnurl %>"></liferay-portlet:param>	
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
	
</div>
