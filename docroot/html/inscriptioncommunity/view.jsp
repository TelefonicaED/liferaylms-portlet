<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test="${not empty inscriptionPortletName }">
		<liferay-portlet:runtime portletName="${inscriptionPortletName }" defaultPreferences="${defaultPreferences }"/>
	</c:when>
	<c:otherwise>
	
		<jsp:useBean id="userCompetenceLocalServiceUtil" class="com.liferay.lms.service.UserCompetenceLocalServiceUtil"/>
		<jsp:useBean id="competenceLocalServiceUtil" class="com.liferay.lms.service.CompetenceLocalServiceUtil"/>
		<jsp:useBean id="membershipRequestLocalServiceUtil" class="com.liferay.portal.service.MembershipRequestLocalServiceUtil"/>
	
		<liferay-ui:success message="inscription-ok" key="inscription-ok" />
		<liferay-ui:success message="lms.inscription.unsusbscribe.success" key="unsusbscribe" />
		<liferay-ui:error message="inscription-error-already-enrolled" key="inscription-error-already-enrolled" />
		<liferay-ui:error message="inscription-error-already-disenrolled" key="unsusbscribe" />
		<liferay-ui:error message="inscription-error-max-users" key="inscription-error-max-users"/>
		
		<div id="caja_inscripcion container" class="caja_inscripcion">
			<c:choose>
				<c:when test="${themeDisplay.isSignedIn() && registredUser}">
					<div class="mensaje_marcado">
						<liferay-ui:message key="inscripcion.inscrito" />
					</div>
					<c:if test="${not empty listChildCourses }">	
						<c:forEach items="${listChildCourses }" var="childCourse">
							<div class="mensaje_marcado">
								<span class="edition-name">${childCourse.getTitle(locale)}</span>
								<aui:button type="button" value="inscription.go-to-course" href='/web${childCourse.friendlyURL}'/>
								<c:if test="${childCourse.canUnsubscribe(themeDisplay.userId, themeDisplay.permissionChecker) }">
									<div class="boton_inscibirse ">
										<a href="#" onclick="javascript:<portlet:namespace/>unsubscribe(${childCourse.courseId })"><liferay-ui:message key="inscripcion.desinscribete" /></a>
									</div>
								</c:if>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${not empty course}">
						<c:if test="${course.canUnsubscribe(themeDisplay.userId, themeDisplay.permissionChecker)}">
							<div class="boton_inscibirse ">
								<a href="#" onclick="javascript:<portlet:namespace />unsubscribe(${course.courseId });"><liferay-ui:message key="inscripcion.desinscribete" /></a>
							</div>
						</c:if>
					</c:if>
					<aui:form action="${unsubscribeURL }" name="unsubscribeForm">
						<aui:input name="courseId" type="hidden" value=""/>
					</aui:form>
					<script>
						function <portlet:namespace />unsubscribe(courseId) {
							if(confirm(Liferay.Language.get('inscripcion.desinscribete.seguro'))){
								document.<portlet:namespace />unsubscribeForm.<portlet:namespace />courseId.value=courseId;
								document.<portlet:namespace />unsubscribeForm.submit();
							}
						}
					</script>
				</c:when>
				<c:when test="${themeDisplay.isSignedIn() && !registredUser}">
					<c:set var="competencesPassed" value="${true }"/>
					<c:if test="${not empty listCourseCompetences}">
						<div><liferay-ui:message key="competences.necessary" />:</div>
						<ul>
							<c:forEach items="${listCourseCompetences}" var="courseCompetence">
								<c:set var="userCompetence" value="${userCompetenceLocalServiceUtil.findByUserIdCompetenceId(themeDisplay.getUserId(), courseCompetence.getCompetenceId()) }" />
								<c:set var="competence" value="${competenceLocalServiceUtil.getCompetence(courseCompetence.competenceId) }" />
								<c:choose>
									<c:when test="${not empty userCompetence }">
										<c:if test="${not empty competence }">
											<li><liferay-ui:icon image="checked"/>${competence.getTitle(themeDisplay.getLocale())}</li>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${not empty competence }" >
											<c:set var="competencesPassed" value="false" />
											<li><liferay-ui:icon image="unchecked"/>${competence.getTitle(themeDisplay.getLocale())}</li>
										</c:if>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
					</c:if>
					<!-- Si tiene pasadas las competencias, entonces se continua con la inscripción -->
					<c:choose>
						<c:when test="${competencesPassed}">
							<c:choose>
								<c:when test="${not empty listChildCourses}">
									<h2><liferay-ui:message key="editions-available"/></h2>
									<p><liferay-ui:message key="editions-available.select-edition"/></p>
								<!-- Si tiene convocatorias, se buclea por los cursos hijos pero no se tiene en cuenta los equipos -->
									<c:set var="childAvailable" value="false" />
									<aui:form name="enrollEditionForm" action="${enrollURL}">
										<c:forEach items="${listChildCourses}" var="childCourse">
											<c:if test="${childCourse.isRegistrationOnDate() }">
												<c:catch var ="inscriptionException">
													<c:if test="${childCourse.canEnroll(themeDisplay.userId, false, themeDisplay.locale, themeDisplay.permissionChecker)}">
														<c:set var="childAvailable" value="true" />
													</c:if>
												</c:catch>
												
												<div class="row inscription-edition">
													<aui:input label="${childCourse.getTitle(themeDisplay.getLocale())}" type="radio" name="courseId" value="${childCourse.courseId}"
														disabled="${not empty inscriptionException }" showRequiredLabel="false">
														<aui:validator name="required" errorMessage="select-edition"/>
													</aui:input>
													<div class="edition-dates">
														<span><liferay-ui:message key="inscription-date"/>:</span> <span><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${childCourse.startDate }" /> - <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${childCourse.endDate }" /></span>
													</div>
													<div class="edition-dates">
														<span><liferay-ui:message key="execution-date"/>:</span> <span><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${childCourse.executionStartDate }" /> - <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${childCourse.executionEndDate }" /></span>
													</div>
													<c:if test="${not empty inscriptionException }">
														<div class="mensaje_marcado">${fn:substringAfter(inscriptionException.message, ':')}</div>
													</c:if>
												</div>
											</c:if>
										</c:forEach>
										<c:choose>	
											<c:when test="${childAvailable }">
												<div class="boton_inscibirse ">
													<aui:button type="submit" value="inscripcion.inscribete"/>
												</div>							
											</c:when>
											<c:otherwise>
												<liferay-ui:message key="inscripcion.no-schedule-open"/>
											</c:otherwise>
										</c:choose>
									</aui:form>
								</c:when>
								<c:otherwise>
									<!-- Si no tiene convocatorias, se hace igual que hasta ahora, teniendo en cuenta los equipos -->
									<c:catch var ="inscriptionException">
										<c:if test="${course.canEnroll(themeDisplay.userId, false, themeDisplay.locale, themeDisplay.permissionChecker)}">
											<aui:form name="enrollForm" action="${enrollURL}">
												<aui:input name="courseId" value="${course.courseId }" type="hidden"/>
												
												<c:if test="${not empty schedules }">
													<h2><liferay-ui:message key="teams-available"/></h2>
													<p><liferay-ui:message key="teams-available.select-team"/></p>
												</c:if>
												<c:forEach items="${schedules}" var="schedule">
												 	<div class="row inscription-schedule">
														<aui:input label="${schedule.team.name}" type="radio" name="teamId" value="${schedule.teamId}" showRequiredLabel="false">
															<aui:validator name="required" errorMessage="select-edition"/>
														</aui:input>
														<div class="edition-dates">
															<span><liferay-ui:message key="inscription-date"/>:</span> <span><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${schedule.startDate}" /> - <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${schedule.endDate }" /></span>
														</div>
														<c:if test="${not empty inscriptionException }">
															<div class="mensaje_marcado">${fn:substringAfter(inscriptionException.message, ':')}</div>
														</c:if>
													</div>
												 </c:forEach>
												 <c:choose>
												 	<c:when test="${!hasTeams}">
												 		<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.noinscrito" /></div>
														<div class="boton_inscibirse ">
															<c:choose>
																<c:when test="${course.group.type == TYPE_SITE_OPEN}">
																	<aui:button type="submit" value="inscripcion.inscribete"/>
																</c:when>
																<c:otherwise>
																	<aui:button type="submit" value="inscripcion.request"/>
																</c:otherwise>
															</c:choose>
														</div>
												 	</c:when>
												 	<c:when test="${not empty schedules }">
												 		<div class="boton_inscibirse ">
												 			<c:choose>
																<c:when test="${course.group.type == TYPE_SITE_OPEN}">
																	<aui:button type="submit" value="inscripcion.inscribete"/>
																</c:when>
																<c:otherwise>
																	<aui:button type="submit" value="inscripcion.request"/>
																</c:otherwise>
															</c:choose>
														</div>
												 	</c:when>
												 	<c:otherwise>
											 			<div class="mensaje_marcado">
															<liferay-ui:message key="inscripcion.no-schedule-open"/>
														</div>
												 	</c:otherwise>
												 </c:choose>			
											</aui:form>
										</c:if>
									</c:catch>
									<c:if test="${not empty inscriptionException }">
										<div class="mensaje_marcado">${fn:substringAfter(inscriptionException.message, ':')}</div> 
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<div class="boton_inscibirse ">
								<liferay-ui:message key="competence.block" />
							</div>	
						</c:otherwise>
					</c:choose> 
				</c:when>
				<c:otherwise>
					<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.nologado" /></div>
					<div class="boton_inscibirse ">
						<a href="/c/portal/login?p_l_id=${themeDisplay.layout.plid }"><liferay-ui:message key="inscripcion.registrate" /></a>
					</div>		
				</c:otherwise>
			</c:choose>
		</div>
	</c:otherwise>
</c:choose>
