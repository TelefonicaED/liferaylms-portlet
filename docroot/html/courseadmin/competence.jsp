<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.model.CourseCompetence"%>
<%@page import="com.liferay.lms.service.CourseCompetenceLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CompetenceLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Competence"%>
<%@page import="com.liferay.lms.service.CompetenceServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<%
	long primKey=ParamUtil.getLong(request, "courseId",0);
	String sPrimKey = String.valueOf(primKey);
	String condition = ParamUtil.getString(request, "condition", "true");
	String tab = ParamUtil.getString(request, "tab", "1");

	Course course = CourseLocalServiceUtil.getCourse(primKey);
	long[] groupsId = {course.getGroupId(),course.getGroupCreatedId()};
	
	HashMap<Long,Competence> competences = CompetenceServiceUtil.getCompetencesOfGroups(groupsId);
	HashMap<Long,Competence> has = new HashMap<Long,Competence>();
	Set<Long>competencesSet = competences.keySet();
	List<Competence>competencesList = new ArrayList<Competence>();
	List<CourseCompetence> courseCompetences = CourseCompetenceLocalServiceUtil.findBycourseId(primKey, Boolean.valueOf(condition));
	
	for(CourseCompetence courseCompetence: courseCompetences){
		Competence competence = competences.get(courseCompetence.getCompetenceId());
		if(competence!=null){
			competencesSet.remove(competence.getCompetenceId());
			competencesList.add(competence);
			has.put(competence.getCompetenceId(), competence);
		}
	}
	/*
	for(Long competenceId : competencesSet){
		competencesList.add(competences.get(competenceId));
	}
	*/
	
	PortletURL portletURL = renderResponse.createRenderURL();
	portletURL.setParameter("view","competence-tab");
	portletURL.setParameter("courseId",Long.toString(primKey));
	portletURL.setParameter("tabs1",tab);
%>

<portlet:renderURL var="adduserURL">
	<portlet:param name="view" value="competence-results" />
	<liferay-portlet:param name="courseId" value="<%=sPrimKey %>"></liferay-portlet:param>
	<liferay-portlet:param name="tabs1" value="<%=tab %>"></liferay-portlet:param>
	<liferay-portlet:param name="condition" value="<%=condition %>"></liferay-portlet:param>
</portlet:renderURL>

<liferay-ui:icon-menu align="left" cssClass='lfr-toolbar-button add-button' direction="down" extended="<%= false %>"  message="add" showWhenSingleIcon="<%= false %>">
	<liferay-ui:icon
	image="add" cssClass="newitem2"
	label="<%= true %>"
	message="add"
	url='<%= adduserURL %>' 
	/>
</liferay-ui:icon-menu>

<liferay-ui:search-container curParam="act" emptyResultsMessage="there-are-no-competences" delta="10" deltaConfigurable="true" iteratorURL="<%=portletURL%>"  >
	<liferay-ui:search-container-results>
		<%
		
				
		results = ListUtil.subList(competencesList, searchContainer.getStart(), searchContainer.getEnd());
		
		if(results.size()<=0){
			results = ListUtil.subList(competencesList,0,searchContainer.getDelta());
		}
		
		total = competencesList.size();
		pageContext.setAttribute("results", results);
		pageContext.setAttribute("total", total);
		%>
	</liferay-ui:search-container-results>
	<liferay-ui:search-container-row className="com.liferay.lms.model.Competence" keyProperty="competenceId" modelVar="cc">
		<liferay-ui:search-container-column-text name="competence.label" >
			<%=cc.getTitle(themeDisplay.getLocale()) %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="type">
			<c:choose>
				<c:when test="<%=cc.getGroupId()==course.getGroupCreatedId() %>">
					<liferay-ui:message key="competence.exclusive" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="competence.global" />
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  name="options">
			<c:choose>
				
				<c:when test="<%= has.get(cc.getCompetenceId())!=null %>">
					<portlet:actionURL name="deactivateCompetence" var="deactivateURL" >
						<portlet:param name="courseId" value="<%=sPrimKey %>" />
						<portlet:param name="competenceId" value="<%=String.valueOf(cc.getCompetenceId()) %>" />
						<portlet:param name="condition" value="<%=condition %>" />
						<portlet:param name="tab" value="<%=tab %>" />
					</portlet:actionURL>
					<liferay-ui:icon-menu>
						<liferay-ui:icon image="close" message="deactivate" url="<%=deactivateURL %>" />
					</liferay-ui:icon-menu>		
				</c:when>
				<c:otherwise>
					<portlet:actionURL name="activateCompetence" var="activateURL" >
						<portlet:param name="courseId" value="<%=sPrimKey %>" />
						<portlet:param name="competenceId" value="<%=String.valueOf(cc.getCompetenceId()) %>" />
						<portlet:param name="condition" value="<%=condition %>" />
						<portlet:param name="tab" value="<%=tab %>" />
					</portlet:actionURL>
					<liferay-ui:icon-menu>
						<liferay-ui:icon image="top" message="activate" url="<%=activateURL %>" />	
					</liferay-ui:icon-menu>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
</liferay-ui:search-container>