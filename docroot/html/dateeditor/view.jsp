<%@ include file="/init-min.jsp" %>
<%@ include file="/js/dateeditor.jsp" %>

<c:choose>
	<c:when test="${themeDisplay.locale.language.equals('ca')}">
		<script src="/liferaylms-portlet/js/datepicker/datepicker-ca.js"></script>
	</c:when>
	<c:when test="${themeDisplay.locale.language.equals('de')}">
		<script src="/liferaylms-portlet/js/datepicker/datepicker-de.js"></script>
	</c:when>
	<c:when test="${themeDisplay.locale.language.equals('es')}">
		<script src="/liferaylms-portlet/js/datepicker/datepicker-es.js"></script>
	</c:when>
	<c:when test="${themeDisplay.locale.language.equals('eu')}">
		<script src="/liferaylms-portlet/js/datepicker/datepicker-eu.js"></script>
	</c:when>
	<c:when test="${themeDisplay.locale.language.equals('gl')}">
		<script src="/liferaylms-portlet/js/datepicker/datepicker-gl.js"></script>
	</c:when>
	<c:when test="${themeDisplay.locale.language.equals('it')}">
		<script src="/liferaylms-portlet/js/datepicker/datepicker-it.js"></script>
	</c:when>
	<c:when test="${themeDisplay.locale.language.equals('pt')}">
		<script src="/liferaylms-portlet/js/datepicker/datepicker-pt.js"></script>
	</c:when>
	<c:when test="${themeDisplay.locale.language.equals('tr')}">
		<script src="/liferaylms-portlet/js/datepicker/datepicker-tr.js"></script>
	</c:when>
	<c:when test="${themeDisplay.locale.language.equals('zh')}">
		<script src="/liferaylms-portlet/js/datepicker/datepicker-zh.js"></script>
	</c:when>
</c:choose>

<div id="maineditsyllabus" class="syllabuscont row">	
	<div class="col-md-12">
		<div class="col-md-12">
			<h1>${course.getTitle(themeDisplay.locale)}</h1>
		</div>	
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4 aui-field-wrapper-content">
				<div class="aui-field-wrapper-content">
					<label class="aui-field-label"><liferay-ui:message key="course-admin.start-execution-date" /></label>
					<div class="aui-datepicker aui-datepicker-display">
						<div class="aui-datepicker-content aui-datepicker-display-content">
							<div class="aui-datepicker-select-wrapper">
								<input class="itime coursemx coursem" type="text" id="fcin${course.courseId}" name="fcin${course.courseId}" value="${dateFormat.format(course.executionStartDate)}" />
							</div>
						</div>
					</div>
					<div class="lfr-input-time">
						<input class="ihour coursemy coursem" type="text" id="tcin${course.courseId}" name="tcin${course.courseId}" value="${timeFormat.format(course.executionStartDate)}" />
					</div>
				</div>
			</div>
			<div class="col-md-4 aui-field-wrapper-content">
				<div class="aui-field-wrapper-content">
					<label class="aui-field-label"><liferay-ui:message key="course-admin.end-execution-date" /></label>
					<div class="aui-datepicker aui-datepicker-display">
						<div class="aui-datepicker-content aui-datepicker-display-content">
							<div class="aui-datepicker-select-wrapper">
								<input class="itime courselh coursel" type="text" id="fcou${course.courseId}" name="fcou${course.courseId}" value="${dateFormat.format(course.executionEndDate)}" />
							</div>
						</div>
					</div>
					<div class="lfr-input-time">
						<input class="ihour courselt coursel" type="text" id="tcou${course.courseId}" name="tcou${course.courseId}" value="${timeFormat.format(course.executionEndDate)}" />
					</div>
				</div>
			</div>
		</div>
		
		<c:forEach var="module" items="${listModules}">		
			<liferay-ui:panel-container extended="true" id="panel_container_${module.moduleId}">
				<div class="row">
					<div class="col-md-12">
						<liferay-ui:panel title="${module.getTitle(themeDisplay.locale)}" defaultState="closed" collapsible="true" id="panel_${module.moduleId}">					
							<div id="item${item.moduleId}" class="row">
								<div class="row">
									<div class="col-md-12">
										<h2>${module.getTitle(themeDisplay.locale)}</h2>
									</div>
								</div>
								<div class="row">
									<span class="hour col-md-12">
										<div class="col-md-4"></div>
										<div class="col-md-4  aui-field-wrapper-content">
											<div class="aui-field-wrapper-content">
												<label class="aui-field-label"><liferay-ui:message key="start-date" /></label>
												<div class="aui-datepicker aui-datepicker-display">
													<div class="aui-datepicker-content aui-datepicker-display-content">
														<div class="aui-datepicker-select-wrapper">
															<input class="itime modalmx modalm" type="text" id="fmin${module.moduleId}" name="fmin${module.moduleId}" value="${dateFormat.format(module.startDate)}" />
														</div>
													</div>
												</div>
												<div class="lfr-input-time">
													<input class="ihour modalmy modalm" type="text" id="tmin${module.moduleId}" name="tmin${module.moduleId}" value="${timeFormat.format(module.startDate)}" />
												</div>
											</div>
										</div>
										<div class="col-md-4  aui-field-wrapper-content">
											<div class="aui-field-wrapper-content">
												<label class="aui-field-label"><liferay-ui:message key="end-date" /></label>
												<div class="aui-datepicker aui-datepicker-display">
													<div class="aui-datepicker-content aui-datepicker-display-content">
														<div class="aui-datepicker-select-wrapper">
															<input class="itime modallh modall" type="text" id="fmou${module.moduleId}" name="fmou${module.moduleId}" value="${dateFormat.format(module.endDate)}" />
														</div>
													</div>
												</div>
												<div class="lfr-input-time">	
													<input class="ihour modallt modall" type="text" id="tmou${module.moduleId}" name="tmou${module.moduleId}" value="${timeFormat.format(module.endDate)}" />
												</div>
											</div>
										</div>
									</span>
								</div>
								
								<c:forEach var="act" items="${module.listLearningActivities}">
									<c:choose> 
										<c:when test="${act.weightinmodule == 1}">
											<div class="mandatory leyend"></div>
										</c:when>
										<c:otherwise>
											<div class="leyend"></div>
										</c:otherwise>
									</c:choose>
									<h3>${act.getTitle(themeDisplay.locale)}</h3>
									<div class="row">
										<span class="hour col-md-12">
											<div class="col-md-4  aui-field-wrapper-content">
												<c:if test="${act.typeId == typeP2P}">		
													<c:set var="dateupload" value='${act.getExtraContentValue("dateupload") }' />
													<c:if test="${not empty dateupload }">
														<c:set var="p2pDate" value="${dateFormat.format(p2pFormat.parse(dateupload))}" />
														<c:set var="p2pTime" value="${timeFormat.format(p2pFormat.parse(dateupload))}" />
													</c:if>
													<div class="aui-field-wrapper-content">
														<label class="aui-field-label"><liferay-ui:message key="p2ptaskactivity.edit.dateUpload" /></label>
														<div class="aui-datepicker aui-datepicker-display">
															<div class="aui-datepicker-content aui-datepicker-display-content">
																<div class="aui-datepicker-select-wrapper">
																	<input class="itime" type="text" id="fact${act.actId}" name="fact${act.actId}" value='${p2pDate}' />
																</div>
															</div>
														</div>
														<div class="lfr-input-time">	
															<input class="ihour" type="text" id="tact${act.actId}" name="tact${act.actId}" value="${p2pTime}" />
														</div>
													</div>	
												</c:if>
											</div>
											<div class="col-md-4  aui-field-wrapper-content">
												<div class="aui-field-wrapper-content">
													<label class="aui-field-label"><liferay-ui:message key="start-date" /></label>
													<div class="aui-datepicker aui-datepicker-display">
														<div class="aui-datepicker-content aui-datepicker-display-content">
															<div class="aui-datepicker-select-wrapper">
																<input class="itime castalmx" id="fain${act.actId}" name="fain${act.actId}" type="text" value="${not empty act.startdate ? dateFormat.format(act.startdate): ''}" />
															</div>
														</div>
													</div>
													<div class="lfr-input-time">	
														<input class="ihour castalmy" id="tain${act.actId}" name="tain${act.actId}" type="text" value="${not empty act.startdate ? timeFormat.format(act.startdate): ''}" />
													</div>
												</div>
											</div>
											<div class="col-md-4  aui-field-wrapper-content">
												<div class="aui-field-wrapper-content">
													<label class="aui-field-label"><liferay-ui:message key="end-date" /></label>
													<div class="aui-datepicker aui-datepicker-display">
														<div class="aui-datepicker-content aui-datepicker-display-content">
															<div class="aui-datepicker-select-wrapper">
																<input class="itime castallh" id="faou${act.actId}" name="faou${act.actId}" type="text" value="${not empty act.enddate ? dateFormat.format(act.enddate): ''}" />
															</div>
														</div>
													</div>
													<div class="lfr-input-time">	
														<input class="ihour castallt" id="taou${act.actId}" name="taou${act.actId}" type="text" value="${not empty act.enddate ? timeFormat.format(act.enddate): ''}" />
													</div>
												</div>
											</div>
										</span>	
									</div>			
								</c:forEach>				
							</div>
						</liferay-ui:panel>
					</div>
				</div>
			</liferay-ui:panel-container>
		</c:forEach>
	
		<aui:button onClick="javascript:${renderResponse.getNamespace()}submitFormDateEditor()" value="confirm" />
		
		<portlet:actionURL name="submit" var="submit" />
		<aui:form name="dform" action="${submit}" method="POST">
		</aui:form>
	</div>
</div>

<script>
	function <portlet:namespace />submitFormDateEditor(){
		if(confirm(Liferay.Language.get("date-editor.confirm-submit"))){
			$("#maineditsyllabus").find(".modif").each(function(index) {
				$("#<portlet:namespace />dform").append("<input type='hidden' name='"+$(this).attr('id')+"' id='"+$(this).attr('id')+"' value='"+$(this).val()+"'>");
			});
			$("#<portlet:namespace />dform").submit();
		}
		return false;
	}
</script>