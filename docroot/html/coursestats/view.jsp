
<%@page import="com.liferay.portal.kernel.dao.orm.QueryUtil"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.CustomSQLParam"%>
<%@page import="com.liferay.portal.util.comparator.UserLastNameComparator"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.lms.model.CourseResult"%>
<%@page import="java.util.Collection"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.portal.service.TeamLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Team"%>
<%@page import="java.util.Locale"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<%
long   teamId		  	 = ParamUtil.getLong (request, "teamId", 0);
OrderByComparator obc	 = new UserLastNameComparator(true);
LinkedHashMap userParams = new LinkedHashMap();
List<User> usersList	 = new ArrayList<User>();
List<Team> userTeams  	 = TeamLocalServiceUtil
								.getUserTeams(	themeDisplay.getUserId(), 
												themeDisplay.getScopeGroupId());
//Colección con los id's de Usuario
Collection<Object> usersCollection = new ArrayList<Object>();

LmsPrefs prefs			 = LmsPrefsLocalServiceUtil
								.getLmsPrefs(themeDisplay.getCompanyId());

Course curso 	 = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
long registered	 = CourseLocalServiceUtil.getStudentsFromCourse(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId()).size();
long finalizados = CourseResultLocalServiceUtil.countStudentsByCourseId(curso, true);
long iniciados 	 = CourseResultLocalServiceUtil.countStudentsByCourseId(curso, false) + finalizados;

Team theTeam = null;
boolean hasNullTeam = false;

if (teamId > 0 && 
	(TeamLocalServiceUtil.hasUserTeam(themeDisplay.getUserId(), teamId) ||
	 userTeams.size() == 0))
{		
	theTeam = TeamLocalServiceUtil.fetchTeam(teamId);	
}
else
{
	if(userTeams != null && 
	   userTeams.size() > 0)
	{
		theTeam = userTeams.get(0);	
		teamId = theTeam.getTeamId();
	}
}
if(userTeams.size() == 0)
{
	userTeams = TeamLocalServiceUtil.getGroupTeams(themeDisplay.getScopeGroupId());
	hasNullTeam = true;
}

if(theTeam != null) {
	
	userParams.put("notInCourseRoleTeach", new CustomSQLParam("WHERE User_.userId NOT IN "
	        + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
	        + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
	        curso.getGroupCreatedId(),
	        RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId() }));
	userParams.put("notInCourseRoleEdit", new CustomSQLParam("WHERE User_.userId NOT IN "
	        + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
	        + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))", new Long[] {
	        curso.getGroupCreatedId(),
	        RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId() }));
	userParams.put("usersGroups", new Long(themeDisplay.getScopeGroupId()));
	userParams.put("usersTeams", theTeam.getTeamId());
	
	//Usuarios del equipo
	usersList = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), StringPool.BLANK, 0, 
											userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);
	
	//Usuarios registrados en el curso
	List<User> registeredUsers = CourseLocalServiceUtil
									.getStudentsFromCourse(	themeDisplay.getCompanyId(), 
															themeDisplay.getScopeGroupId());
	registered = 0;
	for (User usuario: usersList){
		//Añadimos el id a la colección de usuarios
		usersCollection.add(usuario.getUserId());
		
		//Comprobamos si está registrado en el curso
		if (registeredUsers.contains(usuario))
			registered += 1;
	}
	
	DynamicQuery courseResultsQueryF = DynamicQueryFactoryUtil.forClass(CourseResult.class)
										.add(PropertyFactoryUtil.forName("courseId").eq(new Long(curso.getCourseId())))
										.add(PropertyFactoryUtil.forName("passed").eq(new Boolean(true)))
										.add(PropertyFactoryUtil.forName("userId")
											.in(usersCollection));
	
	DynamicQuery courseResultsQueryI = DynamicQueryFactoryUtil.forClass(CourseResult.class)
										.add(PropertyFactoryUtil.forName("courseId").eq(new Long(curso.getCourseId())))
										.add(PropertyFactoryUtil.forName("passed").eq(new Boolean(false)))
										.add(PropertyFactoryUtil.forName("userId")
											.in(usersCollection));
	if (usersCollection.size() > 0){
		finalizados  = CourseResultLocalServiceUtil.dynamicQuery(courseResultsQueryF).size();
		iniciados 	 = CourseResultLocalServiceUtil.dynamicQuery(courseResultsQueryI).size() + finalizados;
	}else{
		registered	 = 0;
		finalizados  = 0;
		iniciados 	 = 0;
	}
}

%>

<liferay-portlet:resourceURL var="exportURL" >
	<portlet:param name="action" value="export"/>
	<portlet:param name="courseId" value="<%=Long.toString(curso.getCourseId()) %>"/>
</liferay-portlet:resourceURL>

<liferay-portlet:renderURL var="recargarTeam">
	<liferay-portlet:param name="jspPage" value="/html/coursestats/view.jsp" />
</liferay-portlet:renderURL>

<%if (usersCollection.size() > 0 || teamId == 0){ %>
	<liferay-ui:icon cssClass='bt_importexport' label="<%= true %>" message="coursestats.csv.export" method="get" url="<%=exportURL%>" />
<%}%>

<aui:form name="recargarTeam" action="<%=recargarTeam%>" method="post">
	<aui:fieldset>
		<aui:column>
			<aui:select name="teamId" id="teamIdSelect" label="team" onChange="submit();">
				<%
				if (hasNullTeam) {
					if (teamId == 0) {
				%>
					<aui:option label="--" value="0" selected="true"></aui:option>
				<%
					} else {
				%>
					<aui:option label="--" value="0"></aui:option>
				<%
					}
				}
				for (Team team : userTeams) {
					if (teamId == team.getTeamId()) {
				%>
					<aui:option label="<%=team.getName()%>" value="<%=team.getTeamId()%>" selected="true"></aui:option>
				<%
					} else {
				%>
					<aui:option label="<%=team.getName()%>" value="<%=team.getTeamId()%>"></aui:option>
				<%
					}
				}
				%>
			</aui:select>
		</aui:column>
	</aui:fieldset>
</aui:form>

<div class="registered"><liferay-ui:message key="coursestats.hay" /> <%=registered %> <liferay-ui:message key="coursestats.inscription.users" /></div>
<div class="coursestart"><liferay-ui:message key="coursestats.start.course" /> <%= iniciados %> <liferay-ui:message key="coursestats.end.course" /> <%= finalizados %>.</div>

<liferay-ui:search-container  deltaConfigurable="true" emptyResultsMessage="module-empty-results-message" delta="20" >
	<%if (usersCollection.size() > 0 || teamId == 0){ %>
		<liferay-ui:search-container-results>
			<%
			int containerStart;
			int containerEnd;
			try {
				containerStart = ParamUtil.getInteger(request, "containerStart");
				containerEnd = ParamUtil.getInteger(request, "containerEnd");
			} catch (Exception e) {
				containerStart = searchContainer.getStart();
				containerEnd = searchContainer.getEnd();
			}
			if (containerStart <=0) {
				containerStart = searchContainer.getStart();
				containerEnd = searchContainer.getEnd();
			}
			
			List<Module> tempResults = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
			results = ListUtil.subList(tempResults, containerStart, containerEnd);
			total = tempResults.size();
		
			pageContext.setAttribute("results", results);
			pageContext.setAttribute("total", total);
		
			request.setAttribute("containerStart",String.valueOf(containerStart));
			request.setAttribute("containerEnd",String.valueOf(containerEnd));
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.lms.model.Module"
			keyProperty="moduleId"
			modelVar="module"
		>
			<%
			long started = 0;
			long finished = 0;

			if (teamId == 0) {
				started = ModuleResultLocalServiceUtil.countByModuleOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), module.getModuleId());
				finished = ModuleResultLocalServiceUtil.countByModulePassedOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),module.getModuleId(),true);
			}else{
				started = ModuleResultLocalServiceUtil.countByModuleOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), module.getModuleId(), usersList);
				finished = ModuleResultLocalServiceUtil.countByModulePassedOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),module.getModuleId(),true, usersList);
			}
			%>
			<liferay-portlet:renderURL var="viewModuleURL">
			<liferay-portlet:param name="jspPage" value="/html/coursestats/viewmodule.jsp"></liferay-portlet:param>
			<liferay-portlet:param name="moduleId" value="<%= Long.toString(module.getModuleId())%>"></liferay-portlet:param>
			<liferay-portlet:param name="teamId" value="<%= Long.toString(teamId)%>"></liferay-portlet:param>
			</liferay-portlet:renderURL>
			<liferay-ui:search-container-column-text name="module"><a href="<%=viewModuleURL%>"><%=module.getTitle(themeDisplay.getLocale()) %></a></liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text  cssClass="date-column" name="coursestats.start.date">
				<%if(module.getStartDate()!=null)
					{
					%>
				<%=dateFormatDate.format(module.getStartDate()) %>
				<%
				}
				%>
				</liferay-ui:search-container-column-text>
					<liferay-ui:search-container-column-text cssClass="date-column" name="coursestats.end.date">
					<%if(module.getEndDate()!=null)
					{
					%>
					<%=dateFormatDate.format(module.getEndDate()) %>
					<%
					}
					%>
					</liferay-ui:search-container-column-text>
			
			<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.start.student"><%=started %></liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.end.student"><%=finished %></liferay-ui:search-container-column-text>
				<%  
			
			int totalActivity=0;
			
			List<LearningActivity> tempResults = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(module.getModuleId());
			totalActivity = tempResults.size();
			
			%>
			<liferay-ui:search-container-column-text cssClass="number-column" name="total.activity"><%=totalActivity %></liferay-ui:search-container-column-text>
			
				
			<% if(module.getPrecedence() != 0) {
				
				Module modulePredence = ModuleLocalServiceUtil.getModule(module.getPrecedence());
			%>
			<liferay-ui:search-container-column-text name="coursestats.modulestats.dependencies"><%=modulePredence.getTitle(themeDisplay.getLocale()) %></liferay-ui:search-container-column-text>
			<%}else{ %>
			<liferay-ui:search-container-column-text name="coursestats.modulestats.dependencies"><%=LanguageUtil.get(locale, "no")%></liferay-ui:search-container-column-text>
			<%} %>
		</liferay-ui:search-container-row>
	<%} %>
	
	<liferay-ui:search-iterator />
</liferay-ui:search-container>