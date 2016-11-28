<%@include file="/init.jsp" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="${searchContainer.displayTerms}"
	id="${renderResponse.getNamespace()}toggle_register_search"
>
	<aui:fieldset>
		<aui:input name="${searchContainer.displayTerms.getFIRST_NAME()}" size="20" value="${searchContainer.displayTerms.getFirstName() }" />

		<aui:input name="${searchContainer.displayTerms.getLAST_NAME()}" size="20" value="${searchContainer.displayTerms.getLastName() }" />
		<c:if test="${showScreenName}">
			<aui:input name="${searchContainer.displayTerms.getSCREEN_NAME()}" size="20" value="${searchContainer.displayTerms.getScreenName() }" />
		</c:if>
		
		<c:if test="${showEmail}">
			<aui:input name="${searchContainer.displayTerms.getEMAIL_ADDRESS()}" size="20" value="${searchContainer.displayTerms.getEmailAddress() }" />
		</c:if>
		<c:if test="${not empty teams}">
			<aui:select name="${searchContainer.displayTerms.getTEAM()}" label="team">
				<aui:option label="--" value="0"/>
				<c:forEach items="${teams}" var="team">
					<aui:option label="${team.name}" value="${team.teamId}"  selected="${searchContainer.displayTerms.getTeamId() == team.teamId}"/>
				</c:forEach>
			</aui:select>
		</c:if>
	</aui:fieldset>
</liferay-ui:search-toggle>
