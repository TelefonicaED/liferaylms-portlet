<%@page import="java.util.Locale"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.model.ClassName"%>
<%@page import="com.liferay.portal.service.ClassNameLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.PortalClassLoaderUtil"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.liferay.portal.kernel.search.ParseException"%>
<%@page import="com.liferay.portal.kernel.search.BooleanClauseFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.search.BooleanClauseOccur"%>
<%@page import="com.liferay.portal.kernel.search.BooleanClause"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.portal.kernel.search.Field"%>
<%@page import="com.liferay.portal.kernel.search.Document"%>
<%@page import="com.liferay.portal.kernel.search.Hits"%>
<%@page import="com.liferay.portal.kernel.search.IndexerRegistryUtil"%>
<%@page import="com.liferay.portal.kernel.search.Indexer"%>
<%@page import="com.liferay.portal.kernel.search.BooleanQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.search.BooleanQuery"%>
<%@page import="com.liferay.portal.kernel.search.SearchContext"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portlet.asset.service.persistence.AssetEntryQuery"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetCategory"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@ include file="/init.jsp" %>

<div class="portlet-toolbar search-form">
<%@ include file="/html/courseadmin/coursesearchform.jsp" %>
	
<%
	if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.coursemodel",themeDisplay.getScopeGroupId(),"ADD_COURSE"))
	{
%>
<portlet:renderURL var="newactivityURL">
<portlet:param name="jspPage" value="/html/courseadmin/editcourse.jsp"></portlet:param>
<portlet:param name="redirect" value="<%= currentURL %>"></portlet:param>
</portlet:renderURL>
<div class="newitem2">
<liferay-ui:icon
image="add"
label="<%= true %>"
message="new-course"
url='<%= newactivityURL %>'
/>
</div>

<%
}

PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("jspPage","/html/courseadmin/view.jsp");
portletURL.setParameter("freetext",freetext);
portletURL.setParameter("state",String.valueOf(state));

pnames =request.getParameterNames();
while(pnames.hasMoreElements()){
	String name = pnames.nextElement();
	if(name.length()>16&&name.substring(0,16).equals("assetCategoryIds")){
		portletURL.setParameter(name,request.getParameter(name));
	}
}
for(String param : tparams){
	portletURL.setParameter(param,request.getParameter(param));
}
portletURL.setParameter("search","search");
Locale loc = response.getLocale();
%>
<liferay-ui:success key="courseadmin.clone.confirmation.success" message="courseadmin.clone.confirmation.success" />
<liferay-ui:error></liferay-ui:error>
<liferay-ui:search-container iteratorURL="<%=portletURL %>" emptyResultsMessage="there-are-no-courses" delta="10">
	<liferay-ui:search-container-results>
	<%
	
		List<Course> orderedCourses = new ArrayList<Course>();
		orderedCourses.addAll(courses);
	    Collections.sort(orderedCourses, new Comparator<Course>() {
	        @Override
	        public int compare(final Course object1, final Course object2) {
	            return object1.getTitle().toLowerCase().compareTo(object2.getTitle().toLowerCase());
	        }
	    } );
	
		results = ListUtil.subList(orderedCourses, searchContainer.getStart(), searchContainer.getEnd());
		total = courses.size();
		pageContext.setAttribute("results", results);
		pageContext.setAttribute("total", total);
		
	%>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row className="com.liferay.lms.model.Course" keyProperty="courseId" modelVar="course">
	<%
		
		Group groupsel= GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
		Layout initCourseLayout = LayoutLocalServiceUtil.fetchFirstLayout(course.getGroupCreatedId(), false, 0);
	%>
		<liferay-ui:search-container-column-text>

		<c:choose>
		
			<c:when test="<%= !course.isClosed()  && permissionChecker.hasPermission(course.getGroupCreatedId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW) %>">
				<a href='<%=themeDisplay.getPortalURL() +"/"+ loc.getLanguage() +"/web/"+ groupsel.getFriendlyURL()%>'><%=course.getTitle(themeDisplay.getLocale()) %></a>
			</c:when>
			<c:otherwise>
				<span class="cclosed"><%=course.getTitle(themeDisplay.getLocale()) %></span>
			</c:otherwise>
		</c:choose>
		
		</liferay-ui:search-container-column-text>
		
	<%
	if( permissionChecker.hasPermission(course.getGroupId(), Course.class.getName(), course.getCourseId(), ActionKeys.UPDATE)
		||permissionChecker.hasPermission(course.getGroupId(), Course.class.getName(), course.getCourseId(), ActionKeys.DELETE)
		||permissionChecker.hasPermission(course.getGroupId(), Course.class.getName(), course.getCourseId(), ActionKeys.PERMISSIONS)
		||permissionChecker.hasPermission(course.getGroupId(), Course.class.getName(), course.getCourseId(), ActionKeys.ASSIGN_MEMBERS)){
	%>		
		<liferay-ui:search-container-column-jsp path="/html/courseadmin/admin_actions.jsp" align="right" />
	<%
	}
	%>
	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator />

</liferay-ui:search-container>
</div>