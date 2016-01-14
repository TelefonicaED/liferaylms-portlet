<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.QueryUtil"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.kernel.dao.orm.CustomSQLParam"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.util.comparator.UserLastNameComparator"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.lms.LearningTypesProperties"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@ include file="/init.jsp" %>
<%
long registered=CourseLocalServiceUtil.getStudentsFromCourse(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId()).size();
long moduleId=ParamUtil.getLong(request,"moduleId",0);
Module theModule=ModuleLocalServiceUtil.getModule(moduleId);

long   teamId		  	 = ParamUtil.getLong (request, "teamId", 0);
OrderByComparator obc	 = new UserLastNameComparator(true);
LinkedHashMap userParams = new LinkedHashMap();
List<User> usersList	 = new ArrayList<User>();
LmsPrefs prefs			 = LmsPrefsLocalServiceUtil
								.getLmsPrefs(themeDisplay.getCompanyId());

Course curso 	 = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());

if(teamId != 0) {
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
	userParams.put("usersTeams", teamId);

	//Usuarios del equipo
	usersList = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), StringPool.BLANK, 0, 
											userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);
	
	//Usuarios registrados en el curso
	List<User> registeredUsers = CourseLocalServiceUtil
									.getStudentsFromCourse(	themeDisplay.getCompanyId(), 
															themeDisplay.getScopeGroupId());
	registered = 0;
	for (User usuario: usersList){	
		//Comprobamos si está registrado en el curso
		if (registeredUsers.contains(usuario))
			registered += 1;
	}
}
%>

<liferay-portlet:resourceURL var="exportURL" >
	<portlet:param name="action" value="exportModule"/>
	<portlet:param name="moduleId" value="<%=Long.toString(moduleId) %>"/>
	<portlet:param name="teamId" value="<%=Long.toString(teamId) %>"/>
</liferay-portlet:resourceURL>
<liferay-ui:icon cssClass='bt_importexport' label="<%= true %>" message="coursestats.csv.export" method="get" url="<%=exportURL%>" />
<portlet:renderURL var="cancelURL" />

<liferay-ui:header title="<%=theModule.getTitle(themeDisplay.getLocale())%>" backURL="<%=cancelURL.toString() %>"></liferay-ui:header>
	
<div class="registered"><liferay-ui:message key="coursestats.modulestats.inscritos" arguments="<%=new Object[]{registered} %>"></liferay-ui:message></div>
<% 
long started = 0;
long finished = 0;

if (teamId == 0) {
	started = ModuleResultLocalServiceUtil.countByModuleOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), theModule.getModuleId());
	finished = ModuleResultLocalServiceUtil.countByModulePassedOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),theModule.getModuleId(),true);
}else if (usersList.size() > 0){
	started = ModuleResultLocalServiceUtil.countByModuleOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), theModule.getModuleId(), usersList);
	finished = ModuleResultLocalServiceUtil.countByModulePassedOnlyStudents(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),theModule.getModuleId(),true, usersList);
}

	%>
	<p><liferay-ui:message key="coursestats.modulestats.iniciaron" arguments="<%=new Object[]{started} %>">></liferay-ui:message><br />
	<%if(theModule.getStartDate()!=null)
			{
			%>
		<%=dateFormatDate.format(theModule.getStartDate()) %>
		<%
		}
		%>
		
			
			<%if(theModule.getEndDate()!=null)
			{
			%>
			<%=dateFormatDate.format(theModule.getEndDate()) %>
			<%
			}
			%>
			
<%
AssetRendererFactory arf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
Map<Long,String> classTypes=arf.getClassTypes(new long[]{themeDisplay.getScopeGroupId()}, themeDisplay.getLocale());
request.setAttribute("types", classTypes);

PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("jspPage","/html/coursestats/viewmodule.jsp");
portletURL.setParameter("moduleId", String.valueOf(moduleId)); 
%>			
<liferay-ui:search-container iteratorURL="<%=portletURL%>" deltaConfigurable="true" emptyResultsMessage="activities-empty-results-message" >
	<%if (usersList.size() > 0 || teamId == 0){ %>
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
		
		List<LearningActivity> tempResults = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(moduleId);
		results = ListUtil.subList(tempResults, containerStart, containerEnd);
		total = tempResults.size();
	
		pageContext.setAttribute("results", results);
		pageContext.setAttribute("total", total);
	
		request.setAttribute("containerStart",String.valueOf(containerStart));
		request.setAttribute("containerEnd",String.valueOf(containerEnd));
		%>
	
		</liferay-ui:search-container-results>
		<liferay-ui:search-container-row className="com.liferay.lms.model.LearningActivity"
			keyProperty="actId"
			modelVar="activity"
		>
		<%
		
		DecimalFormat df = new DecimalFormat("#.#");
		
		long astarted = 0;
		long afinished = 0;
		long notpassed = 0;
		
		if (teamId == 0) {
			astarted=LearningActivityResultLocalServiceUtil.countStartedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId());
			afinished=LearningActivityResultLocalServiceUtil.countPassedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(),true);
			notpassed=LearningActivityResultLocalServiceUtil.countNotPassedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId());
		}else if (usersList.size() > 0){
			astarted=LearningActivityResultLocalServiceUtil.countStartedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), usersList);
			afinished=LearningActivityResultLocalServiceUtil.countPassedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(),true, usersList);
			notpassed=LearningActivityResultLocalServiceUtil.countNotPassedOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), usersList);
		}
		
		double avgResult=0;
		if(afinished+notpassed>0)
		{
			if (teamId == 0) {
				avgResult=LearningActivityResultLocalServiceUtil.avgResultOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId());	
			}else if (usersList.size() > 0){
				avgResult=LearningActivityResultLocalServiceUtil.avgResultOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), usersList);
			}
			
		}
		double triesPerUser = 0;
		if (teamId == 0) {
			triesPerUser=LearningActivityResultLocalServiceUtil.triesPerUserOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId());
		}else if (usersList.size() > 0){
			triesPerUser=LearningActivityResultLocalServiceUtil.triesPerUserOnlyStudents(activity.getActId(), activity.getCompanyId(), activity.getGroupId(), usersList);
		}
		
		String title=activity.getTitle(themeDisplay.getLocale());
		int maxNameLength=GetterUtil.getInteger(LanguageUtil.get(pageContext, "coursestats.modulestats.large.name.length"),20);
		
		if(title.length()>maxNameLength) {
			title="<span title='"+title+"'>"+LanguageUtil.format(pageContext, "coursestats.modulestats.large.name", new Object[]{title.subSequence(0, maxNameLength+1)},false)+"</span>";
		}
		
		boolean hasPrecedence = false;
		if(activity.getPrecedence() > 0)
			hasPrecedence = true;
			%>
		<liferay-ui:search-container-column-text name="coursestats.modulestats.activity"><%=title %></liferay-ui:search-container-column-text>
		<% if (activity.getStartdate()==null) { %>
			<liferay-ui:search-container-column-text cssClass="date-column" name="coursestats.modulestats.activity.start">-</liferay-ui:search-container-column-text>
		<% } else { %>
			<liferay-ui:search-container-column-text cssClass="date-column" name="coursestats.modulestats.activity.start"><%=dateFormatDateTime.format(activity.getStartdate()) %></liferay-ui:search-container-column-text>
		<% } %>
		
		
		<% if (activity.getEnddate()==null) { %>
			<liferay-ui:search-container-column-text cssClass="date-column" name="coursestats.modulestats.activity.end">-</liferay-ui:search-container-column-text>
		<% } else { %>
			<liferay-ui:search-container-column-text cssClass="date-column" name="coursestats.modulestats.activity.end"><%=dateFormatDateTime.format(activity.getEnddate()) %></liferay-ui:search-container-column-text>
		<% } %>
		
		
		<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.modulestats.init"><%=astarted %></liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.modulestats.passed"><%=afinished %></liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.modulestats.failed"><%=notpassed %></liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.modulestats.trials.average"><%=df.format(triesPerUser) %></liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.modulestats.marks.average"><%=df.format(avgResult) %></liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.modulestats.pass.mark"><%=df.format(activity.getPasspuntuation()) %></liferay-ui:search-container-column-text>	
		<liferay-ui:search-container-column-text cssClass="number-column"  name="coursestats.modulestats.trials.numbers"><%=numberFormat.format(activity.getTries()) %></liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="coursestats.modulestats.dependencies"><%=LanguageUtil.get(pageContext, "dependencies."+String.valueOf(hasPrecedence)) %></liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="coursestats.modulestats.type"><%=LanguageUtil.get(pageContext,classTypes.get((long)activity.getTypeId())) %></liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="coursestats.modulestats.obligatory"><%=activity.getWeightinmodule() == 1 ? LanguageUtil.get(locale, "yes"):LanguageUtil.get(locale, "no") %></liferay-ui:search-container-column-text>
		<% //if (activity.getTypeId()!=8 && activity.getTypeId()!=9 && activity.getTypeId()!=7){ %>
		<%// liferay-ui:search-container-column-jsp name=" " align="right" path="/html/coursestats/viewextras.jsp" /%>
		<% //} else {%>
		<% //liferay-ui:search-container-column-text>&nbsp</liferay-ui:search-container-column-text%>
		
		<% //} %>
		</liferay-ui:search-container-row>
	<% } %>
	<liferay-ui:search-iterator />
</liferay-ui:search-container>