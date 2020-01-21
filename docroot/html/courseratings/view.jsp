<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test="${empty courseId }">
		<div class="portlet-msg-info"><liferay-ui:message key="course-ratings.no-course-to-rate"/></div>
	</c:when>
	<c:otherwise>
		<liferay-ui:ratings classPK="${courseId }"  className="${courseClassName }" />
	</c:otherwise>
</c:choose>

