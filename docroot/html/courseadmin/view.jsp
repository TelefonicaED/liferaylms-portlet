<%@page import="java.util.Locale"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetCategory"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@ include file="/init.jsp" %>

<div class="portlet-toolbar search-form">
	<%@ include file="/html/courseadmin/coursesearchform.jsp" %>
		
	<%
	if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.coursemodel",themeDisplay.getScopeGroupId(),"ADD_COURSE")){
		%>
		<portlet:renderURL var="newactivityURL">
			<portlet:param name="view" value="edit-course"></portlet:param>
			<portlet:param name="redirect" value="<%= currentURL %>"></portlet:param>
		</portlet:renderURL>
		<div class="newitem2">
			<liferay-ui:icon
				image="add"
				label="true"
				message="new-course"
				url='${newactivityURL}'
			/>
		</div>
		
		<%
	}
	%>
	<liferay-ui:success key="courseadmin.clone.confirmation.success" message="courseadmin.clone.confirmation.success" />
	<liferay-ui:success key="import-course-ok" message="courseadmin.import-course-ok" />
	<liferay-ui:error></liferay-ui:error>
	
	<liferay-ui:search-container 
		searchContainer="${searchContainer}"
		iteratorURL="${searchContainer.iteratorURL}">
		
		<liferay-ui:search-container-results 
			total="${searchContainer.total }" 
			results="${searchContainer.results }"
		/>
	
		<liferay-ui:search-container-row className="com.liferay.lms.model.Course" keyProperty="courseId" modelVar="course">
			<%
			
			Group groupsel= GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
			Layout initCourseLayout = LayoutLocalServiceUtil.fetchFirstLayout(course.getGroupCreatedId(), false, 0);
			%>
			<liferay-ui:search-container-column-text name="course" orderable="true" orderableProperty="title">
				<c:choose>
					<c:when test="<%= !course.isClosed()  && permissionChecker.hasPermission(course.getGroupCreatedId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW) %>">
						<a href='<%=themeDisplay.getPortalURL() +"/"+ themeDisplay.getLocale().getLanguage() +"/web"+ groupsel.getFriendlyURL()%>'><%=course.getTitle(themeDisplay.getLocale()) %></a>
					</c:when>
					<c:otherwise>
						<span class="cclosed"><%=course.getTitle(themeDisplay.getLocale()) %></span>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
			
			<c:if test="${not empty expandoNames}">
				<c:forEach items="${expandoNames}" var="expName">
					<liferay-ui:search-container-column-text name="${expName.getDisplayName(themeDisplay.locale)}">
						<liferay-ui:custom-attribute classPK="${course.courseId}" name="${expName.name}" 
									className="<%= Course.class.getName() %>" editable="false" label="false" >
						</liferay-ui:custom-attribute>
					</liferay-ui:search-container-column-text>
				</c:forEach>
			</c:if>
				
			<liferay-ui:search-container-column-text name="course.editions-number">
				<%=CourseLocalServiceUtil.countChildCourses(course.getCourseId()) %>
			</liferay-ui:search-container-column-text>
			
			<c:if test="${renderRequest.preferences.getValue('showRegistrationType', 'false')}">		
				<liferay-ui:search-container-column-text name="registration-type">
				    <c:if test="<%=groupsel.getType() == GroupConstants.TYPE_SITE_OPEN  %>">
						<liferay-ui:message key="public" />
					</c:if>
				    <c:if test="<%=groupsel.getType() == GroupConstants.TYPE_SITE_PRIVATE  %>">
						<liferay-ui:message key="private" />
					</c:if>
				    <c:if test="<%=groupsel.getType() == GroupConstants.TYPE_SITE_RESTRICTED  %>">
						<liferay-ui:message key="restricted" />
					</c:if>     			
				</liferay-ui:search-container-column-text>
			</c:if>
			<c:if test="${renderRequest.preferences.getValue('createDateColumn', 'false')}">
				<liferay-ui:search-container-column-text name="create-date" orderable="true" orderableProperty="createDate">
					<%=dateFormatDateTime.format(course.getCreateDate()) %>
				</liferay-ui:search-container-column-text>
			</c:if>
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