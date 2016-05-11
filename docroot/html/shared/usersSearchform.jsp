<%@include file="/init.jsp" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="${searchContainer.displayTerms }"
	id="toggle_id_users_admin_user_search"
>
	<aui:fieldset>
		<aui:input name="${searchContainer.displayTerms.getFIRST_NAME()}" size="20" value="${searchContainer.displayTerms.getFirstName() }" />

		<aui:input name="${searchContainer.displayTerms.getLAST_NAME()}" size="20" value="${searchContainer.displayTerms.getLastName() }" />

		<aui:input name="${searchContainer.displayTerms.getSCREEN_NAME()}" size="20" value="${searchContainer.displayTerms.getScreenName() }" />

		<aui:input name="${searchContainer.displayTerms.getEMAIL_ADDRESS()}" size="20" value="${searchContainer.displayTerms.getEmailAddress() }" />

		<c:if test="${not empty theTeam or not empty userTeams}">
			<aui:select name="${searchContainer.displayTerms.getTEAM()}" label="team">
				<c:if test="${hasNullTeam}">
					<aui:option label="--" value="0"/>
				</c:if>				
				<c:forEach items="${userTeams}" var="team">
					<aui:option label="${team.name}" value="${team.teamId}"  selected="${searchContainer.displayTerms.getTeamId() == team.teamId}"/>
				</c:forEach>
			</aui:select>
		</c:if>
	</aui:fieldset>
</liferay-ui:search-toggle>
