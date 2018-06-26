<%@ include file="/init.jsp"%>

<liferay-ui:search-container searchContainer="${searchContainer}" iteratorURL="${searchContainer.iteratorURL}" >
	<liferay-ui:search-container-results total="${searchContainer.total }" results="${searchContainer.results }" />
	
	<liferay-ui:search-container-row className="com.liferay.lms.views.CourseResultView" keyProperty="course.courseId" modelVar="courseResult">
		<liferay-ui:search-container-column-text title="course">
			<div class="course">
				<c:if test="${not empty courseResult.course.logoURL}">
					<img src="${courseResult.course.logoURL }" alt="${courseResult.course.title}">
				</c:if>
				<c:choose>
					<c:when test="${prefs.viewCoursesFinished && !courseResult.course.closed}">
						<a href="${courseResult.course.url}">${courseResult.course.title}</a>
					</c:when>
					<c:otherwise>
						${courseResult.course.title}
					</c:otherwise>
				</c:choose>
			</div>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text align="right">
			${courseResult.result}
		 	<c:if test="${courseResult.passed}">
				<liferay-ui:icon image="checked" alt="passed"/>
			</c:if>
		
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
 
</liferay-ui:search-container>

