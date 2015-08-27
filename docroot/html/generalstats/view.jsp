<%@page import="com.liferay.lms.model.CourseResult"%>
<%@page import="com.liferay.lms.service.CourseResultService"%>
<%@page import="com.liferay.lms.service.CourseResultLocalService"%>
<%@page import="com.liferay.portal.service.UserGroupRoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.UserGroupRole"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalService"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
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
<%	
DecimalFormat df = new DecimalFormat("#.#");
Locale loc = response.getLocale();
 %>
<div class="portlet-toolbar search-form">
<%@ include file="/html/courseadmin/coursesearchform.jsp" %>
<liferay-ui:success key="courseadmin.clone.confirmation.success" message="courseadmin.clone.confirmation.success" />
<liferay-ui:error ></liferay-ui:error>
<%
if(courses!=null&&courses.size()>0)
{

String scourseIds=ListUtil.toString(courses,"courseId");
%>
<liferay-portlet:resourceURL var="exportURL" >
				<portlet:param name="action" value="export"/>
				<portlet:param name="courseIds" value="<%=scourseIds %>"/>
			</liferay-portlet:resourceURL>
			<liferay-ui:icon image="export" label="<%= true %>" message="offlinetaskactivity.csv.export" method="get" url="<%=exportURL%>" />
<%
}
%>

<liferay-ui:search-container  deltaConfigurable="true" emptyResultsMessage="there-are-no-courses" delta="10">
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
		long registered=CourseLocalServiceUtil.getStudentsFromCourse(course.getCompanyId(), course.getGroupCreatedId()).size();
		long iniciados =  CourseResultLocalServiceUtil.countStudentsByCourseId(course);
		long finalizados = CourseResultLocalServiceUtil.countStudentsByCourseId(course, true);
		double avgResult=0;
		if(finalizados>0){
			avgResult=CourseResultLocalServiceUtil.avgStudentsResult(course, true);
		}
		long activitiesCount=LearningActivityLocalServiceUtil.countLearningActivitiesOfGroup(course.getGroupCreatedId());
		long modulesCount=ModuleLocalServiceUtil.countByGroupId(course.getGroupCreatedId());
	%>

		<liferay-ui:search-container-column-text name="coursestats.name">
		<c:choose>
			<c:when test="<%= !course.isClosed() && UserLocalServiceUtil.hasGroupUser(course.getGroupCreatedId(), themeDisplay.getUserId()) %>">
				<a href='<%=themeDisplay.getPortalURL() +"/"+ loc.getLanguage() +"/web/"+ groupsel.getFriendlyURL()%>'><%=course.getTitle(themeDisplay.getLocale()) %></a>
			</c:when>
			<c:otherwise>
				<%=course.getTitle(themeDisplay.getLocale()) %>
			</c:otherwise>
		</c:choose>
		
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text valign="right" name="coursestats.registered">
		<%=registered %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="coursestats.starts.course">
		<%=iniciados %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="coursestats.ends.course">
		<%=finalizados %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="closed">
		<%=course.getClosed()?LanguageUtil.get(pageContext,"yes"):LanguageUtil.get(pageContext,"no") %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="coursestats.modulestats.marks.average">
		<%=df.format(avgResult) %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="coursestats.modulecounter">
		<%=modulesCount %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="coursestats.activitiescounter">
		<%=activitiesCount %>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator  />

</liferay-ui:search-container>

</div>