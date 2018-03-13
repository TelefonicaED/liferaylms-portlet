<aui:fieldset>
	<aui:input name="${displayTerms.getFIRST_NAME() }" label="first-name" size="20" value="${displayTerms.firstName}" />
	<aui:input name="${displayTerms.getLAST_NAME() }" label="last-name" size="20" value="${displayTerms.lastName}" />
	<aui:input name="${displayTerms.getSCREEN_NAME() }" label="screen-name" size="20" value="${displayTerms.screenName }" />
	<aui:input name="${displayTerms.getEMAIL_ADDRESS() }" label="email-address" size="20" value="${displayTerms.emailAddress }" />

	<c:if test="${not empty displayTerms.userTeams }">
		<aui:select name="${displayTerms.getTEAM() }" label="team">
			<c:if test="${displayTerms.hasNullTeam }">
				<aui:option label="" value="0"/>
			</c:if>
			<c:forEach items="${displayTerms.userTeams}" var="team">
				<aui:option  value="${displayTerms.team.teamId}" label="${displayTerms.team.name}" selected="${displayTerms.team.teamId == displayTerms.teamId}"></aui:option>
			</c:forEach>
		</aui:select>
	</c:if>
	
</aui:fieldset>