<%@include file="/init.jsp"%>

<fmt:setLocale value="${themeDisplay.languageId }"/>

<liferay-ui:search-container searchContainer="${searchContainer}" iteratorURL="${searchContainer.iteratorURL}" >
	<liferay-ui:search-container-results total="${searchContainer.total }" results="${searchContainer.results }" />
		
		<liferay-ui:search-container-row modelVar="activityTriesDeleted" className="com.liferay.lms.model.ActivityTriesDeleted" >
		
			<liferay-ui:search-container-column-text name="view-activity-tries-deleted.activity-tries-deleted-id" property="activityTriesDeletedId" />
			
			<liferay-ui:search-container-column-text name="module" >
				${activityTriesDeleted.learningActivity.module.getTitle(themeDisplay.locale) }
			</liferay-ui:search-container-column-text>
			
			<liferay-ui:search-container-column-text name="activity" >
				${activityTriesDeleted.learningActivity.getTitle(themeDisplay.locale) }
			</liferay-ui:search-container-column-text>
			
			<liferay-ui:search-container-column-text name="user" property="user.screenName" />
			
			<liferay-ui:search-container-column-text name="start-date" >
				<fmt:formatDate type="both" value="${activityTriesDeleted.startDate}" />
			</liferay-ui:search-container-column-text>
			
			<liferay-ui:search-container-column-text name="end-date" >
				<fmt:formatDate type="both" value="${activityTriesDeleted.endDate}" />
			</liferay-ui:search-container-column-text>
			
			<liferay-ui:search-container-column-text name="status" >
				<liferay-ui:message key="${activityTriesDeleted.statusProperties}" />
			</liferay-ui:search-container-column-text>
		
		</liferay-ui:search-container-row>
		
		<liferay-ui:search-iterator />
	
	</liferay-ui:search-container>
