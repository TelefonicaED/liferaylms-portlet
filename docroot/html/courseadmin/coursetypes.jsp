<%@ include file="/init.jsp" %>
<jsp:useBean id="LanguageUtil" class="com.liferay.portal.kernel.language.LanguageUtil"/>

<liferay-ui:header title="courseadmin.coursetype.course-types" backURL="${backURL }"></liferay-ui:header>

<ul class="course-type-list">
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
			<c:if test="${not empty courseType.getDescription(themeDisplay.locale) }">
				<liferay-ui:icon-help message="${courseType.getDescription(themeDisplay.locale) }"  />
			</c:if>
			<a href="${newCourseURL }" class="${empty courseType.getIconCourseTypeURL(themeDisplay) ? 'default-course-type-image' : ''}">
				${courseType.getName(themeDisplay.locale) }
				<c:if test="${not empty courseType.getIconCourseTypeURL(themeDisplay) }">
					<img src="${courseType.getIconCourseTypeURL(themeDisplay) }"/>
				</c:if>
			</a>
			<span class="tooltip-button"></span>
			<div class="tooltip-text" id="wkps">
				<liferay-ui:message key="${courseType.getInfo(themeDisplay.locale) }"/>
			</div>
		</li>
	</c:forEach>
</ul>

<script>
	$('.tooltip-text').slideToggle(0);
	$('.course-type-list li span.tooltip-button').on('click',function(){
		$(this).siblings('.tooltip-text').slideToggle("fast");
		$(this).toggleClass('open');
	});
</script>