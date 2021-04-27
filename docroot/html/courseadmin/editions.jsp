<%@page import="java.util.Locale"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetCategory"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@ include file="/init.jsp" %>

<div class="portlet-toolbar search-form">
<liferay-ui:success key="course-admin.confirmation.new-edition-success" message="course-admin.confirmation.new-edition-success" />
<liferay-ui:header
			backURL="${backURL}"
			title="${editionsTitle}"/>

<div class="container">			
<%
long parentCourseId=ParamUtil.getLong(request, "courseId",0);
Course parentCourse = CourseLocalServiceUtil.fetchCourse(parentCourseId);
if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), parentCourseId, ActionKeys.UPDATE)&& !parentCourse.isClosed()){%>
	
	<div class="col-md-2">
		<portlet:renderURL var="editParentCourseURL">
			<portlet:param name="view" value="edit-course" />
			<portlet:param name="courseId" value='<%=String.valueOf(parentCourseId) %>' />
			<portlet:param name="courseTypeId" value="${courseTypeId }"></portlet:param>
			<portlet:param name="redirect" value='<%=currentURL %>'/>
		</portlet:renderURL>
		
		<div class="newitem2">
			<liferay-ui:icon
				image="edit"
				label="true"
				message="course-admin.edit-parent-course"
				url='${editParentCourseURL}'
			/>
		</div>
	</div>
	
<%}%>

<%
if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.coursemodel",themeDisplay.getScopeGroupId(),"ADD_COURSE")){
	%>
		<div class="col-md-2">
		<portlet:renderURL var="newEditionURL">
			<portlet:param name="view" value="new-edition"></portlet:param>
			<portlet:param name="courseId" value="${courseId}"></portlet:param>
			<portlet:param name="courseTypeId" value="${courseTypeId }"></portlet:param>
			
		</portlet:renderURL>
		<div class="newitem2">
			<liferay-ui:icon
				image="add"
				label="true"
				message="course-admin.new-edition"
				url='${newEditionURL}'
			/>
		</div>
	</div>
</div>	
		<div class="float-r" id="${renderResponse.getNamespace()}importExportEditionsActionsDiv">
		<liferay-ui:icon-menu message="course-admin.editions.import-export" showExpanded="false" showWhenSingleIcon="true">
		 <c:set var="actionURL" value="${exportEditionsURL }" />
			 <liferay-ui:icon 
			 	message="export"
				image="download"
			 	url="${exportEditionsURL }" />
		<c:set var="actionURL" value="javascript:${renderResponse.getNamespace()}importExportEditions();" />
			<liferay-ui:icon
				message="import"
				image="add_instance"
				url="${actionURL  }" />
		</liferay-ui:icon-menu>
	</div>
	
	<%
}
%>

<%@ include file="/html/courseadmin/coursesearchform.jsp" %>

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
		<liferay-ui:search-container-column-text name="edition" orderable="true" orderableProperty="title">

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
		
		<c:if test="${renderRequest.preferences.getValue('showFriendlyUrlColumn', 'false')}">
			<liferay-ui:search-container-column-text name="course-admin.friendlyurl">
				<%=course.getFriendlyURL() %>
			</liferay-ui:search-container-column-text>
		</c:if>
		
		<c:if test="${renderRequest.preferences.getValue('showParentCourseIdColumn', 'false')}">
			<liferay-ui:search-container-column-text name="course-admin.parent-course-id">
				<%=course.getParentCourseId() %>
			</liferay-ui:search-container-column-text>
		</c:if>
		
		<c:if test="${renderRequest.preferences.getValue('showCourseIdColumn', 'false')}">
			<liferay-ui:search-container-column-text name="course-admin.course-id">
				<%=course.getCourseId() %>
			</liferay-ui:search-container-column-text>
		</c:if>

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
		<c:if test="${showInscriptionDate}">
			<liferay-ui:search-container-column-text name="course-admin.start-inscription-date" orderable="true" orderableProperty="startDate">
				<%=dateFormatDateTime.format(course.getStartDate()) %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="course-admin.end-inscription-date" orderable="true" orderableProperty="endDate">
				<%=dateFormatDateTime.format(course.getEndDate()) %>
			</liferay-ui:search-container-column-text>
		</c:if>
		<c:if test="${showExecutionDate}">
			<liferay-ui:search-container-column-text name="course-admin.start-execution-date" orderable="true" orderableProperty="executionStartDate">
				<% if(course.getExecutionStartDate()!=null){%>
						<%= dateFormatDateTime.format(course.getExecutionStartDate())%>
					<% }else{%>
						<%= "-"%>
					<%}%>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="course-admin.end-execution-date" orderable="true" orderableProperty="executionEndDate">
				<% if(course.getExecutionEndDate()!=null){%>
						<%= dateFormatDateTime.format(course.getExecutionEndDate())%>
					<% }else{%>
						<%= "-"%>
					<%}%>
			</liferay-ui:search-container-column-text>
		</c:if>
		<%--  DE MOMENTO COMENTADO HASTA QUE SE APLIQUE EL FUNCIONAMIENTO DE CURSO LINKADO -->  
		 <liferay-ui:search-container-column-text name="course-admin.linked">
			<c:choose>
				<c:when test="<%=course.getIsLinked() %>">
					<liferay-ui:message key="yes"/>
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="no"/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-column-text>
		 --%>
		<liferay-ui:search-container-column-text name="course-admin.number-of-members">
			<%=	CourseLocalServiceUtil.getStudentsFromCourseCount(course.getCourseId()) %>
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text name ="course-admin.is-published">		
<% 
			 AssetEntry entry=null;
			 boolean visibleencatalogo=false;
			 long assetEntryId=0;
			 
			entry = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),course.getCourseId());
			assetEntryId=entry.getEntryId();
			visibleencatalogo=entry.getVisible();		
			%>		
				<c:choose>
					<c:when test="<%= visibleencatalogo %>">
						<liferay-ui:message key="course-admin.is-published.yes"/>
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="course-admin.is-published.no"/>
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

<%@ include file="/html/courseadmin/editionsimportexport.jsp" %>