<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@include file="/init.jsp"%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="${searchContainer.displayTerms}"
	id="${renderResponse.getNamespace()}toggle_register_search"
>
	<aui:fieldset>
		<aui:input name="${searchContainer.displayTerms.getFIRST_NAME() }" label="first-name" size="20" value="${searchContainer.displayTerms.getFirstName() }" />
		<aui:input name="${searchContainer.displayTerms.getLAST_NAME() }" label="last-name" size="20" value="${searchContainer.displayTerms.getLastName() }" />
		<c:if test="${showScreenName}">
			<aui:input name="${searchContainer.displayTerms.getSCREEN_NAME() }" label="screen-name" size="20" value="${searchContainer.displayTerms.getScreenName() }" />
		</c:if>
		<c:if test="${showEmail}">
			<aui:input name="${searchContainer.displayTerms.getEMAIL_ADDRESS() }" label="email-address" size="20" value="${searchContainer.displayTerms.getEmailAddress() }" />
		</c:if>

		<c:if test="${existTeams}">
			<aui:select name="teamId" label="team">
				<aui:option label="" value="0"></aui:option>
				<c:forEach items="${teams}" var="team">
					<aui:option  value="${team.teamId}" label="${team.name}" selected="${team.teamId == teamId}"></aui:option>
				</c:forEach>
			</aui:select>
		</c:if>
		
	</aui:fieldset>
</liferay-ui:search-toggle>
