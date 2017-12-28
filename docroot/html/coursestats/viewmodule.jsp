<%@ include file="/init.jsp" %>


<liferay-portlet:resourceURL var="exportURL" >
	<portlet:param name="action" value="exportModule"/>
	<portlet:param name="moduleId" value="${moduleId}"/>
	<portlet:param name="teamId" value="${teamId}"/>
</liferay-portlet:resourceURL>

<portlet:renderURL var="cancelURL">
	<portlet:param name="teamId" value="${teamId}"/>
</portlet:renderURL>

<liferay-ui:header title="${module.getTitle(themeDisplay.locale)}" 
				   backURL="${cancelURL}"/>

<liferay-ui:icon cssClass='bt_importexport' label="${true}" message="coursestats.csv.export" method="get" url="${exportURL}" />

	<aui:fieldset>
		<h2><liferay-ui:message key="coursestats.activities"></liferay-ui:message> </h2>
	</aui:fieldset>

	<liferay-ui:search-container
			id="activitySearchContainer"
			searchContainer="${searchContainer}" 
			iteratorURL="${searchContainer.iteratorURL}">
			<liferay-ui:search-container-results 
				total="${searchContainer.total }" 
				results="${searchContainer.results }"/>
			
			<liferay-ui:search-container-row modelVar="activityStats" keyProperty="actId" className="com.liferay.lms.views.ActivityStatsView" >
			<liferay-ui:search-container-column-text name="coursestats.modulestats.activity">
				${activityStats.actTitle}
			</liferay-ui:search-container-column-text>
			<c:if test="${renderRequest.preferences.getValue('showActivityStartDate', 'true') }">
				<liferay-ui:search-container-column-text cssClass="date-column" name="coursestats.modulestats.activity.start">
					${activityStats.startDateString}
				</liferay-ui:search-container-column-text>
			</c:if>
			<c:if test="${renderRequest.preferences.getValue('showActivityEndDate', 'true') }">
				<liferay-ui:search-container-column-text cssClass="date-column" name="coursestats.modulestats.activity.end">
					${activityStats.endDateString}
				</liferay-ui:search-container-column-text>
			</c:if>
			<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.modulestats.init">
				${activityStats.started}
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.finished">
				${activityStats.finished}
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.modulestats.passed">
				${activityStats.passed}
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.modulestats.failed">
				${activityStats.failed}
			</liferay-ui:search-container-column-text>
			<c:if test="${renderRequest.preferences.getValue('showActivityTrialsAverage', 'true') }">
				<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.modulestats.trials.average">
					${activityStats.triesPerUserString}
				</liferay-ui:search-container-column-text>
			</c:if>
			<c:if test="${renderRequest.preferences.getValue('showActivityMarksAverage', 'true') }">
				<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.modulestats.marks.average">
					${activityStats.avgResult}
				</liferay-ui:search-container-column-text>
			</c:if>
			<c:if test="${renderRequest.preferences.getValue('showActivityPassMark', 'true') }">
				<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.modulestats.pass.mark">
					${activityStats.passPuntuation}
				</liferay-ui:search-container-column-text>	
			</c:if>
			<c:if test="${renderRequest.preferences.getValue('showActivityTrialsNumber', 'true') }">
				<liferay-ui:search-container-column-text cssClass="number-column"  name="coursestats.modulestats.trials.numbers">
					${activityStats.tries}
				</liferay-ui:search-container-column-text>
			</c:if>
			<c:if test="${renderRequest.preferences.getValue('showActivityPrecedence', 'true') }">
				<liferay-ui:search-container-column-text name="coursestats.modulestats.act-precedence">
					${activityStats.dependency}
				</liferay-ui:search-container-column-text>
			</c:if>
			<c:if test="${renderRequest.preferences.getValue('showActivityType', 'true') }">
				<liferay-ui:search-container-column-text name="coursestats.modulestats.type">
					${activityStats.type}
				</liferay-ui:search-container-column-text>
			</c:if>
			<c:if test="${renderRequest.preferences.getValue('showActivityObligatory', 'true') }">
				<liferay-ui:search-container-column-text name="coursestats.modulestats.obligatory">
					${activityStats.mandatory}
				</liferay-ui:search-container-column-text>
			</c:if>
		</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />	
	</liferay-ui:search-container>