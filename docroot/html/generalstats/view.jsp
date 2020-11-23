<%@ include file="/init.jsp" %>

<c:if test="${course.courseId > 0 }">
	<liferay-ui:header
			backURL="${backURL}"
			title="${course.getTitle(themeDisplay.locale)}"/>
</c:if>

<div class="portlet-toolbar search-form">
	<%@ include file="/html/courseadmin/coursesearchform.jsp" %>
	
	<liferay-ui:icon image="export" label="<%= true %>" message="offlinetaskactivity.csv.export" url="javascript:${renderResponse.getNamespace()}submitExport();" />
	<aui:form name="fmExport" action="${exportReportURL}" method="POST" role="form">
	</aui:form>
	<script>
	function <portlet:namespace />submitExport(){
		$('form[name="<portlet:namespace />fmExport"]').submit();
	}
	</script>
	
	<liferay-ui:search-container
			id="courseSearchContainer"
			searchContainer="${searchContainer}" 
			iteratorURL="${searchContainer.iteratorURL}">
			<liferay-ui:search-container-results 
				total="${searchContainer.total }" 
				results="${searchContainer.results }"/>
			<liferay-ui:search-container-row modelVar="courseStats"  keyProperty="courseId" className="com.liferay.lms.views.CourseStatsView" >
				<liferay-ui:search-container-column-text name="title" cssClass="${courseStats.cssClosed}"  title="title" orderable="false">					
					<a href='${themeDisplay.portalURL}/${themeDisplay.locale.language}/web${courseStats.course.friendlyURL}'>${courseStats.courseTitle}</a>
					<c:if test="${courseStats.numEditions > 0}">
						<liferay-portlet:renderURL var="goToEditionsURL">
							<liferay-portlet:param name="courseId" value="${courseStats.courseId }"/>
							<liferay-portlet:param name="view" value="editions"/>
						</liferay-portlet:renderURL>
					 	(<a href="${goToEditionsURL}"> <liferay-ui:message key="coursestats.view-editions"/></a>)
				 	</c:if>					 		
				</liferay-ui:search-container-column-text>
				<c:if test="${empty course && renderRequest.preferences.getValue('showEditions', 'true') }">
					<liferay-ui:search-container-column-text name="course.editions-number"  title="course.editions-number" orderable="false" >
					 	${courseStats.numEditions}
					</liferay-ui:search-container-column-text>
				</c:if>
				<c:if test="${renderRequest.preferences.getValue('showRegistered', 'true') }">
					<liferay-ui:search-container-column-text name="coursestats.registered"  title="coursestats.registered" orderable="false">
					 	${courseStats.registered}
					</liferay-ui:search-container-column-text>
				</c:if>
				<c:if test="${renderRequest.preferences.getValue('showInit', 'true') }">
					<liferay-ui:search-container-column-text name="coursestats.start.student"  title="coursestats.starts.course" orderable="false">
					 	${courseStats.started}
					</liferay-ui:search-container-column-text>
				</c:if>
				<c:if test="${renderRequest.preferences.getValue('showFinished', 'true') }">
					<liferay-ui:search-container-column-text name="coursestats.end.student"  title="coursestats.finished" orderable="false">
					 	${courseStats.finished}
					</liferay-ui:search-container-column-text>
				</c:if>
				<c:if test="${renderRequest.preferences.getValue('showPassed', 'false') }">
					<liferay-ui:search-container-column-text name="coursestats.passed"  title="evaluationAvg.passed" orderable="false">
					 	${courseStats.passed}
					</liferay-ui:search-container-column-text>
				</c:if>
				<c:if test="${renderRequest.preferences.getValue('showFailed', 'false') }">
					<liferay-ui:search-container-column-text name="coursestats.failed"  title="coursestats.modulestats.notpass" orderable="false">
					 	${courseStats.failed}
					</liferay-ui:search-container-column-text>
				</c:if>
				<c:if test="${renderRequest.preferences.getValue('showCourseClosed', 'true') }">
					<liferay-ui:search-container-column-text  valign="right" name="closed">
						${courseStats.closed}
					</liferay-ui:search-container-column-text>
				</c:if>
				<c:if test="${renderRequest.preferences.getValue('showModules', 'true') }">
					<liferay-ui:search-container-column-text  valign="right" name="coursestats.modulecounter">
					 	${courseStats.modules}
					</liferay-ui:search-container-column-text>
				</c:if>
				<c:if test="${renderRequest.preferences.getValue('showActivities', 'true') }">
					<liferay-ui:search-container-column-text  valign="right" name="coursestats.activitiescounter">
					 	${courseStats.activities}
					</liferay-ui:search-container-column-text>
				</c:if>
			</liferay-ui:search-container-row>		
			<liferay-ui:search-iterator />
	</liferay-ui:search-container>

</div>

