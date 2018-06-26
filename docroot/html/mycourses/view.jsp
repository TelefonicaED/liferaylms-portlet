<%@ include file="/init.jsp"%>
<jsp:useBean id="moduleService" class="com.liferay.lms.service.ModuleLocalServiceUtil" />
<jsp:useBean id="moduleResultService" class="com.liferay.lms.service.ModuleResultLocalServiceUtil" />
<jsp:useBean id="layoutService" class="com.liferay.portal.service.LayoutLocalServiceUtil" />

<c:forEach items="${listMyCourses }" var="courseResult">
	<c:set var="listModules" value="${moduleService.findAllInGroup(courseResult.course.groupId) }"/>
	<c:if test='${not empty listModules || permissionChecker.hasPermission(courseResult.course.groupId, "com.liferay.lms.model",themeDisplay.scopeGroupId,"ADD_MODULE") }'>
		<div class="course option-more">
			<c:set var="url" value="${courseResult.course.url }"/>
			<c:choose>
				<c:when test="${not empty courseResult.course.logoURL }">
					<a href="${url }" class="course-title">
						<img src="${courseResult.course.logoURL }" alt="${courseResult.course.title }">
						${courseResult.course.title }
					</a>
				</c:when>
				<c:otherwise>
					<a class="course-no-image" href='${url }'>${courseResult.course.title }</a>
				</c:otherwise>
			</c:choose>
			<span class="challenges">${moduleService.modulesUserPassed(courseResult.course.groupId,themeDisplay.userId)}/${moduleService.countByGroupId(courseResult.course.groupId) }<span class="ch-text"><liferay-ui:message key="finished.modules" /></span></span>
			<span class="ico-desplegable"></span>
				<div class="collapsable"  style="display:none;">
					<table class="moduleList">
						<c:forEach items="${listModules }" var="module">
							<c:set var="moduleResult" value="${moduleResultService.getByModuleAndUser(module.moduleId,themeDisplay.userId)}"/>
							<c:set var="done" value="0"/>
							<c:if test="${not empty moduleResult }">
								<c:set var="done" value="${moduleResult.result }"/>
							</c:if>
							<tr>
								<td class="title">
									<c:set var="retoplid" value="${themeDisplay.plid }"/>
									<c:forEach items="${courseResult.course.layouts}" var="theLayout">
										<c:if test="${theLayout.friendlyURL == '/reto' }">
											<c:set var="retoplid" value="${theLayout.plid }"/>
										</c:if>
									</c:forEach>
									<liferay-portlet:renderURL plid="${retoplid}" portletName="lmsactivitieslist_WAR_liferaylmsportlet" var="gotoModuleURL">
										<liferay-portlet:param name="moduleId" value="${module.moduleId }"></liferay-portlet:param>
									</liferay-portlet:renderURL>
									
									<c:choose>
										<c:when test="${!module.isLocked(themeDisplay.userId) }">
											<a href="${gotoModuleURL }">${module.getTitle(themeDisplay.locale)}</a>
										</c:when>
										<c:otherwise>
											<a>${module.getTitle(themeDisplay.locale)}</a>
										</c:otherwise>
									</c:choose>								
								</td>
								<td class="result">
									${done}% <liferay-ui:message key="test.done" />
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
	</c:if>

</c:forEach>

<c:if test="${empty listMyCourses }">
	<liferay-ui:message key="there-are-no-courses" />
</c:if>
	
