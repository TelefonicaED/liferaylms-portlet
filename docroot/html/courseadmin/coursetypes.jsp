<%@ include file="/init.jsp" %>
<jsp:useBean id="LanguageUtil" class="com.liferay.portal.kernel.language.LanguageUtil"/>

<liferay-ui:header title="courseadmin.coursetype.course-types" backURL="${backURL }"></liferay-ui:header>

<ul class="activity-list">
	<portlet:renderURL var="newactivityURL">
		<portlet:param name="view" value="edit-course"></portlet:param>
		<portlet:param name="redirect" value="<%= currentURL %>"></portlet:param>
		<portlet:param name="courseTypeId" value="0"></portlet:param>
	</portlet:renderURL>
	<li>
		<a href="${newactivityURL }">
			<liferay-ui:message key="courseadmin.coursetypes.empty-course"/>
		</a>
	</li>
	<c:forEach items="${listCourseTypes }" var="courseType">
		<portlet:renderURL var="newCourseURL">
			<portlet:param name="view" value="edit-course"></portlet:param>
			<portlet:param name="redirect" value="<%= currentURL %>"></portlet:param>
			<portlet:param name="courseTypeId" value="${courseType.courseTypeId }"></portlet:param>
		</portlet:renderURL>
		<li>
			<liferay-ui:icon-help message="${courseType.getInfo(themeDisplay.locale) }"  />
			<a href="${newCourseURL }">
				${courseType.getName(themeDisplay.locale) }
			</a>
			<c:if test="${not empty courseType.getIconCourseTypeURL(themeDisplay) }">
				<img src="${courseType.getIconCourseTypeURL(themeDisplay) }"/>
			</c:if>
		</li>
	</c:forEach>
</ul>