<%@ include file="/init-min.jsp" %>

<div class="teachers">
	<c:forEach items="${listTeachers }" var="teacher">	
		<div class="teacher">
			<liferay-ui:user-display displayStyle="2"  userId="${teacher.userId}"/>
		</div>
	</c:forEach>
</div>