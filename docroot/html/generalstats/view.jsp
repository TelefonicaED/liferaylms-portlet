
<%@ include file="/init.jsp" %>

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
				 	${courseStats.courseTitle}
				</liferay-ui:search-container-column-text>
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

