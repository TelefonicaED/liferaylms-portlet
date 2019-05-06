
<%@page import="com.liferay.lms.course.adminaction.AdminActionTypeRegistry"%>
<%@page import="com.liferay.lms.course.adminaction.AdminActionType"%>

<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.lms.service.CompetenceServiceUtil"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@ include file="/init.jsp" %>
<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
SearchContainer<Course> searchContainer = (SearchContainer<Course>)request.getAttribute("liferay-ui:search:searchContainer"); 
Course myCourse = (Course)row.getObject();
LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
String name = Course.class.getName();
String primKey = String.valueOf(myCourse.getCourseId());

long count = 0;
long countGroup = CompetenceServiceUtil.getCountCompetencesOfGroup(myCourse.getGroupCreatedId());
long countParentGroup = CompetenceServiceUtil.getCountCompetencesOfGroup(myCourse.getGroupId());
long countStudents = CourseLocalServiceUtil.getStudentsFromCourseCount(myCourse.getCourseId());
long countChildCourses = CourseLocalServiceUtil.countChildCourses(myCourse.getCourseId());

count = countGroup + countParentGroup;

PortletPreferences preferences = null;
String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}else{
	preferences = renderRequest.getPreferences();
}

boolean showClose 	= preferences.getValue("showClose",  "true").equals("true");
boolean showDelete 	= preferences.getValue("showDelete", "true").equals("true");
boolean showMembers = preferences.getValue("showMembers","true").equals("true");
boolean showExport 	= preferences.getValue("showExport", "true").equals("true");
boolean showImport	= preferences.getValue("showImport", "true").equals("true");
boolean showClone 	= preferences.getValue("showClone",  "true").equals("true");
boolean showGo 		= preferences.getValue("showGo", 	 "true").equals("true");
boolean showPermission = preferences.getValue("showPermission", "true").equals("true");
%>

<portlet:actionURL name="closeCourse" var="closeURL">
	<portlet:param name="courseId" value="<%= primKey %>" />
</portlet:actionURL>

<liferay-ui:icon-menu>
<portlet:renderURL var="editURL">
	<portlet:param name="courseId" value="<%=primKey %>" />
	<portlet:param name="view" value="edit-course" />
	<portlet:param name="redirect" value='<%= ParamUtil.getString(request, "redirect", currentURL) %>'/>
</portlet:renderURL>

<%-- Editar --%>
<%

boolean editionsWithoutRestrictions = GetterUtil.getBoolean(renderRequest.getPreferences().getValue("showEditionsWithoutRestrictions", StringPool.FALSE),false);

if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,ActionKeys.UPDATE)&& ! myCourse.isClosed()){%>
	<liferay-ui:icon image="edit" message="edit" url="<%=editURL.toString() %>" />

<%}%>

<%-- Asignar miembros --%>
<portlet:renderURL var="memebersURL">
	<portlet:param name="courseId" value="<%=primKey %>" />
	<portlet:param name="backToEdit" value="<%=StringPool.FALSE %>" />
	<portlet:param name="view" value="role-members-tab" />
</portlet:renderURL>
<%
if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,ActionKeys.ASSIGN_MEMBERS)&& ! myCourse.isClosed() && showMembers && (editionsWithoutRestrictions || countChildCourses <=0))
{
%>

<liferay-ui:icon image="group" message="assign-member" url="<%=memebersURL.toString() %>" />

<%
}
%>

<%-- Competencias --%>
<c:if test="<%=count>0 && permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,ActionKeys.UPDATE) && ! myCourse.isClosed()  && myCourse.getParentCourseId()<=0%>">
	<portlet:renderURL var="competenceURL">
		<portlet:param name="groupId" value="<%=String.valueOf(myCourse.getGroupCreatedId()) %>" />
		<portlet:param name="courseId" value="<%=String.valueOf(myCourse.getCourseId()) %>" />
		<portlet:param name="view" value="competence-tab" />
	</portlet:renderURL>
	<liferay-ui:icon image="tag" message="competence.label" url="<%=competenceURL %>" />
</c:if>

<%-- Exportar curso --%>
	<%if(showExport && permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,ActionKeys.UPDATE)){%>	
	<portlet:renderURL var="exportURL">
		<portlet:param name="groupId" value="<%=String.valueOf(myCourse.getGroupCreatedId()) %>" />
		<portlet:param name="view" value="export" />
	</portlet:renderURL>
	<liferay-ui:icon image="download" message="courseadmin.adminactions.export" url="<%=exportURL %>" />			
	<%}%>
	<%if(showImport && permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,ActionKeys.UPDATE)){%>	
	<portlet:renderURL var="importURL">
		<portlet:param name="groupId" value="<%=String.valueOf(myCourse.getGroupCreatedId()) %>" />
		<portlet:param name="view" value="import" />
	</portlet:renderURL>
	<liferay-ui:icon image="post" message="courseadmin.adminactions.import" url="<%=importURL %>" />			
	<%}%>
	
<%-- Duplicar curso --%>
	<%if(showClone && permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,ActionKeys.UPDATE)){%>	
	<portlet:renderURL var="cloneURL">
		<portlet:param name="groupId" value="<%=String.valueOf(myCourse.getGroupCreatedId()) %>" />
		<portlet:param name="view" value="clone" />
	</portlet:renderURL>
	<liferay-ui:icon image="copy" message="<%=(myCourse.getParentCourseId()>0) ? \"courseadmin.adminactions.clone-edition\" :  \"courseadmin.adminactions.clone\"  %>" url="<%=cloneURL%>" />	
	<%}%>
	
	<%-- Cerrar Curso --%>
	<%
if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,ActionKeys.UPDATE)&& ! myCourse.isClosed() && showClose)
{
%>
	<liferay-ui:icon image="close" message="close" url="<%= 
			\"javascript:AUI().use(function(A){ \"+
			\"   msg = '\" + LanguageUtil.get(pageContext, \"courseadmin.confirm.close\") + \"';\" +
			\"   if(confirm(msg)) { \" +
			\"       window.location.href = '\" + closeURL.toString() + \"';\" +
			\"   } else { \" +
			\"       return false; \" +
			\"   } \" +
			\"}) && undefined\"%>" />
			
			
	<%-- Abrir Curso --%>
<%
}else if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,ActionKeys.UPDATE)&& myCourse.isClosed()){
%>
	<portlet:actionURL name="openCourse" var="openURL">
		<portlet:param name="courseId" value="<%= primKey %>" />
	</portlet:actionURL>
	<liferay-ui:icon src="<%= themeDisplay.getPathThemeImages() + \"/dock/my_places_private.png\" %>" message="open-course" url="<%=openURL.toString() %>" />
<%} %>


<%-- Eliminar Curso --%>
<%
if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,ActionKeys.DELETE)&& 
		! myCourse.isClosed() && 
		showDelete && 
		CourseResultLocalServiceUtil.countFinishedOnlyStudents(myCourse.getCourseId(), myCourse.getCompanyId(), myCourse.getGroupCreatedId(), null, 0)<=0
){
%>
<portlet:actionURL name="deleteCourse" var="deleteURL">
	<portlet:param name="courseId" value="<%= primKey %>" />
</portlet:actionURL>
<liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />
<%
}
%>

	
<c:if test="<%= permissionChecker.hasPermission(myCourse.getGroupId(), Course.class.getName(), myCourse.getCourseId(), ActionKeys.PERMISSIONS)&& ! myCourse.isClosed() %>">

	<%-- Permisos --%>
	<%if(showPermission){%>
		<liferay-security:permissionsURL
			modelResource="<%=Course.class.getName() %>"
			modelResourceDescription="<%= myCourse.getTitle(themeDisplay.getLocale()) %>"
			resourcePrimKey="<%= String.valueOf(myCourse.getCourseId()) %>"
			var="permissionsURL"
		/>
	<liferay-ui:icon image="permissions" message="courseadmin.adminactions.permissions" url="<%=permissionsURL %>" />
	<%}%>
</c:if>

<c:if test="<%=permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,ActionKeys.UPDATE) && ! myCourse.isClosed() && myCourse.getParentCourseId()<=0 && (countStudents<=0 || editionsWithoutRestrictions)%>">
		<liferay-portlet:renderURL var="editionsURL">
			<liferay-portlet:param name="courseId" value="<%=String.valueOf(myCourse.getCourseId()) %>"/>
			<liferay-portlet:param name="view" value="editions"/>
		</liferay-portlet:renderURL>
		<liferay-ui:icon image="tag" message="course-admin.editions" url="<%=editionsURL %>" />
	</c:if>



	<c:forEach var="action" items="${adminActionTypes}">
	
		 <c:if test="${action.hasPermission(themeDisplay.getUserId())}">
		
			<portlet:renderURL var="specificURL" >
				<portlet:param name="jspPage" value="/html/courseadmin/inc/specific_action.jsp" />
				<portlet:param name="courseId" value="<%=String.valueOf(myCourse.getCourseId()) %>" />
				<portlet:param name="cur" value="<%=  String.valueOf(searchContainer.getCur()) %>" />
				<portlet:param name="delta" value="<%=  String.valueOf(searchContainer.getDelta()) %>" />
				<portlet:param name="portletId" value="${action.getPortletId()}" />
			</portlet:renderURL>
			<liferay-ui:icon image="${action.getIcon()}" message="${action.getName(locale)}"  label="true"
				url="${specificURL}" />
		 </c:if>
	</c:forEach>

</liferay-ui:icon-menu>