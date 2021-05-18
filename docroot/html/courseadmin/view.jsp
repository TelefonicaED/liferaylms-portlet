<%@page import="java.util.Locale"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetCategory"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseTypeLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<div class="portlet-toolbar search-form">
	<%@ include file="/html/courseadmin/coursesearchform.jsp" %>
		
	<%
	if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.coursemodel",themeDisplay.getScopeGroupId(),"ADD_COURSE")){
		String viewParam = "edit-course";
		if(CourseTypeLocalServiceUtil.countByCompanyIdActive(themeDisplay.getCompanyId())>0)
			viewParam = "course-types"; 
		%>
		
		<portlet:renderURL var="newactivityURL">
			<portlet:param name="view" value="<%= viewParam %>"></portlet:param>
			<portlet:param name="redirect" value="<%= currentURL %>"></portlet:param>
		</portlet:renderURL>
		<div class="newitem2">
			<liferay-ui:icon
				image="add"
				label="true"
				message="new-course"
				url='${newactivityURL }'
			/>
		</div>
		<%
	}
	
	if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model.Course",themeDisplay.getScopeGroupId(),"ASSIGN_MEMBERS")){
	%>		
		<div><%@ include file="/html/courseadmin/import_actions.jsp" %></div>
	<%
	}
	%>
	
	<liferay-ui:success key="courseadmin.clone.confirmation.success" message="courseadmin.clone.confirmation.success" />
	<liferay-ui:success key="import-course-ok" message="courseadmin.import-course-ok" />
	<liferay-ui:error></liferay-ui:error>

	<%
	boolean editionsWithoutRestrictions = GetterUtil.getBoolean(renderRequest.getPreferences().getValue("showEditionsWithoutRestrictions", StringPool.FALSE),false);
	%>
	
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
			long countStudents = CourseLocalServiceUtil.getStudentsFromCourseCount(course.getCourseId());
			String primKey = String.valueOf(course.getCourseId());
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
			
			<c:if test="${!hideExecutionDateCourseColumn }">
				<liferay-ui:search-container-column-text name="course-admin.start-execution-date" orderable="true" orderableProperty="executionStartDate">
					<c:choose>
						<c:when test="<%=CourseLocalServiceUtil.countChildCourses(course.getCourseId())<1 %>">
							<%= (course.getExecutionStartDate()!=null)?dateFormatDateTime.format(course.getExecutionStartDate()):"-"%>
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="course-admin.end-execution-date" orderable="true" orderableProperty="executionEndDate">
					<c:choose>
						<c:when test="<%=CourseLocalServiceUtil.countChildCourses(course.getCourseId())<1 %>">
							<%= (course.getExecutionEndDate()!=null)?dateFormatDateTime.format(course.getExecutionEndDate()):"-"%>
						</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>
			</c:if>
			
			<c:if test="${not empty expandoNames}">
				<c:forEach items="${expandoNames}" var="expName">
					<liferay-ui:search-container-column-text name="${expName.getDisplayName(themeDisplay.locale)}">
						<liferay-ui:custom-attribute classPK="${course.courseId}" name="${expName.name}" 
									className="<%= Course.class.getName() %>" editable="false" label="false" >
						</liferay-ui:custom-attribute>
					</liferay-ui:search-container-column-text>
				</c:forEach>
			</c:if>
			
			<c:if test="${renderRequest.preferences.getValue('showFriendlyUrlColumn', 'false')}">
				<liferay-ui:search-container-column-text name="course-admin.friendlyurl">
					<%=course.getFriendlyURL() %>
				</liferay-ui:search-container-column-text>
			</c:if>
			
			<c:if test="${renderRequest.preferences.getValue('showCourseIdColumn', 'false')}">
				<liferay-ui:search-container-column-text name="course-admin.course-id">
					<%=course.getCourseId() %>
				</liferay-ui:search-container-column-text>
			</c:if>
				
			<liferay-ui:search-container-column-text name="course.editions-number">
			<c:choose>
				<c:when test="<%=permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,ActionKeys.UPDATE) && ! course.isClosed() && course.getGroupCreatedId() != themeDisplay.getScopeGroupId() && course.getParentCourseId()<=0 && (countStudents<=0 || editionsWithoutRestrictions)%>">
					<liferay-portlet:renderURL var="goToEditionsURL">
						<liferay-portlet:param name="courseId" value="<%=String.valueOf(course.getCourseId()) %>"/>
						<liferay-portlet:param name="view" value="editions"/>
					</liferay-portlet:renderURL>
					<a href="${goToEditionsURL}"><%=CourseLocalServiceUtil.countChildCourses(course.getCourseId()) %></a>
				</c:when>
				<c:otherwise>
					<p><%=CourseLocalServiceUtil.countChildCourses(course.getCourseId()) %></p>
				</c:otherwise>
			</c:choose>
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