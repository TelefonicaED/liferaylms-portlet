<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalService"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseCompetenceLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.lms.model.CourseCompetence"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.lms.service.ClpSerializer"%>
<%@page import="com.liferay.portal.kernel.bean.PortletBeanLocatorUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.lms.service.CompetenceServiceUtil"%>
<%@page import="com.liferay.lms.model.Competence"%>
<%@page import="com.liferay.lms.service.CompetenceLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<liferay-ui:error key="competence.courseCompetence-in-use" message="competence.courseCompetence-in-use" />
<liferay-ui:error key="competence.userCompetence-in-use" message="competence.userCompetence-in-use" />


<portlet:renderURL var="newcompetenceURL">
<portlet:param name="jspPage" value="/html/competencesadmin/editcompetence.jsp"></portlet:param>
</portlet:renderURL>
<portlet:renderURL var="editcompimageURL">
<portlet:param name="jspPage" value="/html/competencesadmin/editimage.jsp"></portlet:param>
</portlet:renderURL>
<portlet:renderURL var="editbackgroundURL">
<portlet:param name="jspPage" value="/html/competencesadmin/editbackground.jsp"></portlet:param>
</portlet:renderURL>
<portlet:renderURL var="editpageURL">
<portlet:param name="jspPage" value="/html/competencesadmin/editpage.jsp"></portlet:param>
</portlet:renderURL>
<div class="newitem2">

<%
	if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Competence.class.getName(),0L,ActionKeys.UPDATE))
	{
%>
	<liferay-ui:icon-list>
		<liferay-ui:icon image="add" label="<%= true %>"
		message="new-competence"
		url='<%= newcompetenceURL %>' />
		<% if(permissionChecker.isCompanyAdmin() || permissionChecker.isOmniadmin()) { %>
			<liferay-ui:icon image="edit" label="<%= true %>"
			message="edit-image"
			url='<%= editcompimageURL %>' />
		<%} %>
		<liferay-ui:icon image="edit" label="<%= true %>"
		message="competence.edit-background"
		url='<%= editbackgroundURL %>' />
		<% if(permissionChecker.isCompanyAdmin() || permissionChecker.isOmniadmin()) { %>
			<liferay-ui:icon image="edit" label="<%= true %>"
			message="configure-pages"
			url='<%= editpageURL %>' />
		<%} %>
	</liferay-ui:icon-list>
<%
	}
%>

</div>
<liferay-ui:search-container emptyResultsMessage="there-are-no-competences" delta="10">
	<liferay-ui:search-container-results>
	<%
	long groupId=themeDisplay.getScopeGroupId();
	results=CompetenceServiceUtil.getCompetencesOfGroup(groupId,searchContainer.getStart(), searchContainer.getEnd());
	total=CompetenceServiceUtil.getCountCompetencesOfGroup(groupId);
	pageContext.setAttribute("results", results);
	pageContext.setAttribute("total", total);
	%>
	</liferay-ui:search-container-results>
		<liferay-ui:search-container-row className="com.liferay.lms.model.Competence" keyProperty="competenceId" modelVar="competence">
			<%
			ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),"portletClassLoader");
			DynamicQuery dq = DynamicQueryFactoryUtil.forClass(CourseCompetence.class,classLoader)
					.add(PropertyFactoryUtil.forName("competenceId").eq(competence.getCompetenceId()));
			List<CourseCompetence> courseCompetences = CourseCompetenceLocalServiceUtil.dynamicQuery(dq);
			%>
			<liferay-ui:search-container-column-text name="competence.title">
				<%=competence.getTitle(themeDisplay.getLocale()) %>
			</liferay-ui:search-container-column-text>
			
			<liferay-ui:search-container-column-text name="competence.courses">
				<%
					if(courseCompetences != null && courseCompetences.size()>0) {
						for(CourseCompetence cc: courseCompetences) {
							try{
								Course course=CourseLocalServiceUtil.getCourse(cc.getCourseId());
								%>
								<%=course.getTitle(locale) %>
							<%
							}catch(Exception e){
								
							}
							
						}
					}
				%>
			</liferay-ui:search-container-column-text>
			
			<liferay-ui:search-container-column-jsp path="/html/competencesadmin/actions.jsp" align="right" />
		</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />

</liferay-ui:search-container>