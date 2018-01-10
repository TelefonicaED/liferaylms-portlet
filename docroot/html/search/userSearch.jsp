<aui:fieldset>
	<aui:input name="${displayTerms.getFIRST_NAME() }" label="first-name" size="20" value="${displayTerms.firstName}" />
	<aui:input name="${displayTerms.getLAST_NAME() }" label="last-name" size="20" value="${displayTerms.lastName}" />
	<aui:input name="${displayTerms.getSCREEN_NAME() }" label="screen-name" size="20" value="${displayTerms.screenName }" />
	<aui:input name="${displayTerms.getEMAIL_ADDRESS() }" label="email-address" size="20" value="${displayTerms.emailAddress }" />

	<c:if test="${not empty teams }">
		<aui:select name="${displayTerms.getTEAM() }" label="team">
			<c:if test="${hasNullTeam }">
				<aui:option label="" value="0"/>
			</c:if>
			<c:forEach items="${teams}" var="team">
				<aui:option  value="${team.teamId}" label="${team.name}" selected="${team.teamId == displayTerms.teamId}"></aui:option>
			</c:forEach>
		</aui:select>
	</c:if>
	
</aui:fieldset>