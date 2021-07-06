<%@ include file="/init.jsp" %>


	<liferay-portlet:resourceURL var="exportURL" >
		<portlet:param name="action" value="export"/>
		<portlet:param name="courseId" value="${course.courseId}"/>
	</liferay-portlet:resourceURL>

	<liferay-portlet:renderURL var="reloadTeamURL"/>
	<liferay-ui:icon cssClass='bt_importexport' label="<%= true %>" message="coursestats.csv.export" method="get" url="<%=exportURL%>" />

	<c:if test="${existTeams}">
		<aui:form name="reloadTeamFm" action="${reloadTeamURL}" method="post" role="form">
			<aui:fieldset>
				<aui:column>
					<aui:select name="teamId" label="team" onChange="submit();">
						<aui:option label="--" value="0"></aui:option>
						<c:forEach items="${teams}" var="team">
							<aui:option  value="${team.teamId}" label="${team.name}" selected="${team.teamId == teamId}"></aui:option>
						</c:forEach>
					</aui:select>	
				</aui:column>
			</aui:fieldset>
		</aui:form>
	</c:if>
	<aui:fieldset>
		<h2><liferay-ui:message key="coursestats.course"></liferay-ui:message> </h2>
	</aui:fieldset>
	
	<liferay-ui:search-container
			id="courseSearchContainer"
			searchContainer="${searchContainer}" 
			iteratorURL="${searchContainer.iteratorURL}">
			<liferay-ui:search-container-results 
				total="${searchContainer.total }" 
				results="${searchContainer.results }"/>
			
			<liferay-ui:search-container-row modelVar="courseStats" keyProperty="courseId" className="com.liferay.lms.views.CourseStatsView" >
				<liferay-ui:search-container-column-text name="title"  title="title" orderable="false">
				 	${courseStats.courseTitle}
				</liferay-ui:search-container-column-text>
				
				<c:if test="${renderRequest.preferences.getValue('showCourseStartDate', 'false') }">
					<liferay-ui:search-container-column-text name="coursestats.start.date"  title="coursestats.start.date" orderable="false">
					 	${courseStats.startDateStr}
					</liferay-ui:search-container-column-text>
				</c:if>

				<c:if test="${renderRequest.preferences.getValue('showCourseEndDate', 'false') }">
					<liferay-ui:search-container-column-text name="coursestats.end.date"  title="coursestats.end.date" orderable="false">
					 	${courseStats.endDateStr}
					</liferay-ui:search-container-column-text>
				</c:if>

				<c:if test="${renderRequest.preferences.getValue('showCourseNumActity', 'false') }">
					<liferay-ui:search-container-column-text name="coursestats.activitiescounter"  title="coursestats.activitiescounter" orderable="false">
					 	${courseStats.activities}
					</liferay-ui:search-container-column-text>	
				</c:if>
										
				<liferay-ui:search-container-column-text name="coursestats.registered"  title="coursestats.registered" orderable="false">
				 	${courseStats.registered}
				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="coursestats.start.student"  title="coursestats.starts.course" orderable="false">
				 	${courseStats.started}
				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="coursestats.end.student"  title="coursestats.finished" orderable="false">
				 	${courseStats.finished}
				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="coursestats.passed"  title="evaluationAvg.passed" orderable="false">
				 	${courseStats.passed}
				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="coursestats.failed"  title="coursestats.modulestats.notpass" orderable="false">
				 	${courseStats.failed}
				</liferay-ui:search-container-column-text>
				
				<c:if test="${renderRequest.preferences.getValue('showCourseNumMale', 'false') }">
					<liferay-ui:search-container-column-text name="coursestats.male"  title="coursestats.male" orderable="false">
					 	${courseStats.male}
					</liferay-ui:search-container-column-text>
				</c:if>
				
				<c:if test="${renderRequest.preferences.getValue('showCourseNumFemale', 'false') }">
					<liferay-ui:search-container-column-text name="coursestats.female"  title="coursestats.female" orderable="false">
					 	${courseStats.female}
					</liferay-ui:search-container-column-text>
				</c:if>
				
				<c:if test="${renderRequest.preferences.getValue('showCourseAvgValoration', 'false') }">
					<liferay-ui:search-container-column-text name="coursestats.avgvaloration"  title="coursestats.avgvaloration" orderable="false">
					 	${courseStats.avgValoration}
					</liferay-ui:search-container-column-text>
				</c:if>
				
			</liferay-ui:search-container-row>		
			<liferay-ui:search-iterator />
	</liferay-ui:search-container>

	<aui:fieldset>
		<h2><liferay-ui:message key="coursestats.module-details"></liferay-ui:message> </h2>
	</aui:fieldset>
	
	<liferay-ui:search-container
				id="moduleSearchContainer"
				searchContainer="${moduleSearchContainer}" 
				iteratorURL="${moduleSearchContainer.iteratorURL}">
		<liferay-ui:search-container-results 
			total="${moduleSearchContainer.total }" 
			results="${moduleSearchContainer.results }"/>
					
			<liferay-ui:search-container-row  className="com.liferay.lms.views.ModuleStatsView"
				keyProperty="moduleId"
				modelVar="moduleStats"
			>
				<liferay-portlet:renderURL var="viewModuleURL">
					<liferay-portlet:param name="view" value="viewModule"></liferay-portlet:param>
					<liferay-portlet:param name="moduleId" value="${moduleStats.moduleId}"></liferay-portlet:param>
					<liferay-portlet:param name="teamId" value="${teamId}"></liferay-portlet:param>
				</liferay-portlet:renderURL>
				<liferay-ui:search-container-column-text name="module">
					<a href="${viewModuleURL}">
						${moduleStats.moduleTitle}
					</a>
				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text  cssClass="date-column" name="coursestats.start.date">
					${moduleStats.startDateString}
				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text cssClass="date-column" name="coursestats.end.date">
					${moduleStats.endDateString}
				</liferay-ui:search-container-column-text>
				
				<liferay-ui:search-container-column-text cssClass="number-column" name="total.activity">
					${moduleStats.activityNumber}
				</liferay-ui:search-container-column-text>
			
				<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.start.student">
					${moduleStats.started}
				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.end.student">
					${moduleStats.finished}
				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="coursestats.modulestats.dependencies">
					${moduleStats.precedence}
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>	
			<liferay-ui:search-iterator />
	</liferay-ui:search-container>
