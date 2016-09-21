<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.CookieKeys"%>
<%@page import="com.liferay.portal.kernel.util.Time"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="com.liferay.taglib.portlet.RenderURLTag"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="javax.portlet.RenderResponse"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>

<%@ include file="/init.jsp"%>
<div class="container-activity">

<%
	long actId	=ParamUtil.getLong(request,"actId",0);
	long userId = themeDisplay.getUserId();
	
	boolean isNewTry = false;

	String openWindow = GetterUtil.getString(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "openWindow"), "true");
	boolean improve   = GetterUtil.getBoolean(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "improve"), true);

	//Obtener si puede hacer un intento de mejorar el resultado.
	
	LearningActivity activity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
	if (actId==0 || activity.getTypeId() != 9) {
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	} else {
		
		boolean userLocked 		 = LearningActivityLocalServiceUtil.islocked(actId,userId);
		boolean permissionUpdate = permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(), actId, ActionKeys.UPDATE);
		boolean permissionAcLock = permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK");
		Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(activity.getGroupId());
		boolean hasPermissionAccessCourseFinished = LiferaylmsUtil.hasPermissionAccessCourseFinished(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), course.getCourseId(), themeDisplay.getUserId());
		request.setAttribute("hasPermissionAccessCourseFinished", hasPermissionAccessCourseFinished);
		
		if( !userLocked || permissionUpdate || permissionAcLock || hasPermissionAccessCourseFinished) {
			
			boolean improving = true;
			LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
			
			long typeId=activity.getTypeId();
			
			boolean userPassed		 = Validator.isNotNull(result) ? result.isPassed() : false;
			%>
	
			<h2 class="description-title"><%=activity.getTitle(themeDisplay.getLocale())%></h2>
			<div class="description"><%=activity.getDescription(themeDisplay.getLocale())%></div>

			<%
			if(!improve && userPassed && !hasPermissionAccessCourseFinished) {
				
				request.setAttribute("learningActivity",activity);
				request.setAttribute("larntry",LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, userId));
				
				Object[] arguments = new Object[] { result.getResult() };
				Object[] arg = new Object[] { activity.getPasspuntuation() };
				%>
				
				<p><liferay-ui:message key="activity-done" /></p>
				<p><liferay-ui:message key="activity.your-result" arguments="<%=arguments%>" /></p>
				<p class="color_tercero negrita">
					<liferay-ui:message key="activity.your-result-pass" arguments="<%=arg%>" />
				</p>
				
				<% 
			} else {
			%>
				<div id="scormstatus">
					<%
					Object[] args1 = new Object[] { activity.getPasspuntuation() };
					if(userPassed) {
						request.setAttribute("learningActivity",activity);
						request.setAttribute("larntry",LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, userId));
						Object[] arguments = new Object[] { result.getResult() };
						%>
						<p><liferay-ui:message key="activity-done" /></p>
						<p><liferay-ui:message key="activity.your-result" arguments="<%=arguments%>" /></p>
						<p class="color_tercero negrita">
							<liferay-ui:message key="activity.your-result-pass" arguments="<%=args1%>" />
						</p>
						<% 
					}	
					Object[] argumentsfake = new Object[] {" __SCORE__ "};
					%>
				
				</div>
				<script>
					function updateScormStatus(lar){
						gradetext='<p><liferay-ui:message key="activity.your-result" arguments="<%=argumentsfake%>" /></p>';
						gradetext=gradetext.replace(" __SCORE__ ",lar.result);
						document.getElementById("scormstatus").innerHTML='<p><liferay-ui:message key="activity-done" /></p>'+gradetext+
						'<p class="color_tercero negrita"><liferay-ui:message key="activity.your-result-pass" arguments="<%=args1%>" /></p>';
					}
				</script>
				<%
					ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivityTry.class.getName(), renderRequest);
					
					long activityTimestamp=0;
					long timestamp		  =0;
					LearningActivityTry learningTry = null;
						
					if(!hasPermissionAccessCourseFinished){
						learningTry = LearningActivityTryLocalServiceUtil.createOrDuplicateLast(actId, serviceContext);
					
						activityTimestamp = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(),"timeStamp"));
						timestamp 		  = activityTimestamp * 1000 - (new Date().getTime() - learningTry.getStartDate().getTime());
					}
					
					if((activityTimestamp != 0) && (timestamp < 0))
					{
						request.setAttribute("learningActivity",activity);
						if(!hasPermissionAccessCourseFinished){
							request.setAttribute("larntry",learningTry);
						}
				%>
						<liferay-util:include page="/html/execactivity/test/expired.jsp" servletContext="<%=this.getServletContext()%>">
							<liferay-util:param value="<%=Long.toString(activity.getActId())%>" name="actId" />
						</liferay-util:include>
				<%
					} else {
						Object [] arg =  new Object [] { activity.getPasspuntuation() };
						%>
			
						<!-- PARA QUE NO CADUQUE LA SESION CUANDO ESTAS HACIENDO EL SCORM AÑADIMOS EXTENDER LA SESION -->
						<% if ("true".equals(openWindow)) {
							String windowWith=LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(),"windowWith","1024");
							String height	 =LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(),"height"	,"768");
						%>
			
							<liferay-portlet:renderURL var="scormwindow" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
								<liferay-portlet:param name="hasPermissionAccessCourseFinished" value="<%= String.valueOf(hasPermissionAccessCourseFinished) %>"/>
								<liferay-portlet:param name="jspPage" value="/html/scormactivity/window.jsp"/>
								<c:if test="<%=!hasPermissionAccessCourseFinished %>">
									<liferay-portlet:param name="latId" value="<%= String.valueOf(learningTry.getLatId()) %>"/>
								</c:if>
							</liferay-portlet:renderURL>
							
							<script type="text/javascript">
								function _checkPopupBlocker(poppedWindow) {
									setTimeout(function() {
										if (_doCheckPopupBlocker(poppedWindow)) {
											var A = AUI();
											A.one('p.activity-message').setStyle('display', 'block');
											A.one('span.newitem2').setStyle('display', 'block');
										}
									}, 2000);
								}
							
								function _doCheckPopupBlocker(poppedWindow) {
								    var result = false;
								    try {
								    	// Safari with popup blocker... leaves the popup window handle undefined
								        if (typeof poppedWindow == 'undefined')
								            result = true;
								    	
								        // This happens if the user opens and closes the client window...
							            // Confusing because the handle is still available, but it's in a "closed" state.
							            // We're not saying that the window is not being blocked, we're just saying
							            // that the window has been closed before the test could be run.
								        else if (poppedWindow && poppedWindow.closed)
								            result = false;
								        
								        // This is the actual test. The client window should be fine.
								        else if (poppedWindow && (poppedWindow.outerHeight > 0 || typeof poppedWindow.outerHeight == 'undefined'))
								            result = false;
								        
								        // Else we'll assume the window is not OK
								        else 
								        	result = true;
					
								    } catch (err) { console.log(err); }
								    
								    return result;
								}
							
								Liferay.provide(
										window,
										'<portlet:namespace />abrirActividad',
										function (e) {
											var A = AUI();
											if (e != null && window.<portlet:namespace />ventana != null && !window.<portlet:namespace />ventana.closed) {
												window.<portlet:namespace />ventana.close();
											}
											window.<portlet:namespace />ventana = window.open('','scormactivity','height=<%=height%>,width=<%=windowWith%>,scrollbars=0,resizable=yes,menubar=no,toolbar=no,location=no,');
											if (window.<portlet:namespace />ventana != null) {
												window.<portlet:namespace />ventana.location = '<%= scormwindow %>';
												_checkPopupBlocker(window.<portlet:namespace />ventana);
											} else {
												A.one('p.activity-message').setStyle('display', 'block');
												A.one('span.newitem2').setStyle('display', 'block');
											}
										},
										['node']
								);
							</script>
						
							<% if(!userPassed) { %>
								<script type="text/javascript">
									AUI().ready(function() {					
										<portlet:namespace />abrirActividad(null);					
									});
								</script>
							
								<p style="display:none" class="activity-message"><liferay-ui:message key="activity.openwindow"></liferay-ui:message></p>
									
								<span style="display:none" class="newitem2">
									<a class="newitem2" onclick="<portlet:namespace />abrirActividad(event)" target="scormactivity" href="javascript:void(0)">
										<% if (isNewTry) { %>
											<liferay-ui:message key="activity.go"></liferay-ui:message>
										<% } else { %>
											<liferay-ui:message key="activity.continue"></liferay-ui:message>
										<% } %>
									</a>
								</span>
							<% } else { %>		
								<p class="activity-message"><liferay-ui:message key="activity.openwindow.passed"></liferay-ui:message></p>
										
								<span class="newitem2">
									<a class="newitem2" onclick="<portlet:namespace />abrirActividad(event)" target="scormactivity" href="javascript:void(0)">
										<liferay-ui:message key="activity.go"></liferay-ui:message>
									</a>
								</span>
							<% }
					} else {
						request.setAttribute("learningTry", learningTry);
					%>
						<script>
							var scormembededmode=true;
						</script>
								
						<liferay-util:include page="/html/scormactivity/window.jsp" servletContext="<%=this.getServletContext() %>">
							<liferay-portlet:param name="hasPermissionAccessCourseFinished" value="<%= String.valueOf(hasPermissionAccessCourseFinished) %>"/>
						</liferay-util:include>
					<%
					}
				}
			}%>
			
			<%@ include file="/html/scormactivity/session_timeout.jspf" %>
			<script>			
				window.setInterval("${renderResponse.getNamespace()}waitForFinishScorm()", '${sessionIntervale}');	
			</script>
		<%}
	}
%>
</div>