<%@ include file="/init.jsp" %>

<c:if test="${finishModule }">
	<div id="modulegreetings">
		<liferay-ui:message key="module-finissed-greetings-simple" />
	</div>		
</c:if>

<c:if test="${not empty startURL }">
	<div id="startactivity"><a href="${startURL}"><liferay-ui:message key="activityNavigator.start" /></a></div>
</c:if>

<c:if test="${not empty previusURL}">
	<div id="previusactivity"><a href="${previusURL}"><liferay-ui:message key="activityNavigator.prev" /></a></div>
</c:if>
<c:if test="${not empty nextURL}">
	<div id="nextactivity"><a href="${nextURL}"><liferay-ui:message key="activityNavigator.next" /></a></div>
</c:if>


<script type="text/javascript">
	function updateActivityNavigation(){
		Liferay.Portlet.refresh('#p_p_id<portlet:namespace />');
	}
</script>