<%@page import="com.liferay.lms.service.ScheduleLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ScheduleLocalService"%>
<%@page import="com.liferay.lms.model.Schedule"%>
<%@page import="com.liferay.portal.model.Team"%>
<%@page import="com.liferay.portal.service.TeamLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.CourseResult"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Competence"%>
<%@page import="com.liferay.lms.service.CompetenceLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.UserCompetence"%>
<%@page import="com.liferay.lms.model.CourseCompetence"%>
<%@page import="com.liferay.lms.service.CourseCompetenceLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.UserCompetenceLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.MembershipRequest"%>
<%@page import="com.liferay.portal.model.MembershipRequestConstants"%>
<%@page import="com.liferay.portal.service.MembershipRequestLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@include file="/init.jsp" %>
<%@page import="java.net.URLEncoder"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.util.LiferayViewUtil"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>

<div id="caja_inscripcion">
	
	<%
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
	
	if(course!=null && permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW)){
		
		if(themeDisplay.isSignedIn()){	%>
		
			<liferay-ui:error message="inscription-error-syte-restricted" key="inscription-error-syte-restricted"></liferay-ui:error>
			<liferay-ui:error message="inscription-error-max-users" key="inscription-error-max-users"></liferay-ui:error>
			<liferay-ui:error message="inscription-error-already-enrolled" key="inscription-error-already-enrolled"></liferay-ui:error>
			<liferay-ui:error message="inscription-error-already-disenrolled" key="inscription-error-already-disenrolled"></liferay-ui:error>
			
			
			
			
			
			
			<%if(GroupLocalServiceUtil.hasUserGroup(themeDisplay.getUserId(),themeDisplay.getScopeGroupId())){ 
				Date now = new Date();
				if(course.getStartDate().after(now)||course.getEndDate().before(now)||!permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),"REGISTER")){%>
					<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.inscrito" /></div>
				<%} else {
					CourseResult courseResult = CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), themeDisplay.getUserId()); 
					
					if(courseResult != null && courseResult.getPassedDate() != null){%>
						<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.inscrito" /></div>	
					<%}else{ %>
					
						<portlet:actionURL name="desinscribir"  var="desinscribirURL" windowState="NORMAL"/>
						<script type="text/javascript">
							function <portlet:namespace />enviar() {
								if(confirm('<%=JavaScriptUtil.markupToStringLiteral(LanguageUtil.get(pageContext, "inscripcion.desinscribete.seguro")) %>')) {
									window.location.href = "<%=desinscribirURL %>";
								}
							}
						</script>
						<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.inscrito" /></div>	
						
						<div class="boton_inscibirse ">
							<a href="#" onclick="javascript:<portlet:namespace />enviar();"><liferay-ui:message key="inscripcion.desinscribete" /></a>
						</div>	
								
					<%}
				}
			} else {
				Group groupC = GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
				List<CourseCompetence> courseCompetences = CourseCompetenceLocalServiceUtil.findBycourseId(course.getCourseId(), true);
					
				boolean pass=true;
				
				if(courseCompetences!=null&&courseCompetences.size()>0){%>
				
					<div><liferay-ui:message key="competences.necessary" />:</div>
					<ul>
						<%for(CourseCompetence courseCompetence : courseCompetences){
							UserCompetence uc = UserCompetenceLocalServiceUtil.findByUserIdCompetenceId(themeDisplay.getUserId(), courseCompetence.getCompetenceId());
							if(uc!=null) {
								Competence compet =CompetenceLocalServiceUtil.getCompetence(uc.getCompetenceId());
								if(compet!=null) {%>
									<li><liferay-ui:icon image="checked"/><%=compet.getTitle(themeDisplay.getLocale())%></li>
								<%}
							}else{
								pass=false;
								Competence compet =CompetenceLocalServiceUtil.getCompetence(courseCompetence.getCompetenceId());
								if(compet!=null){%>
									<li><liferay-ui:icon image="unchecked"/><%=compet.getTitle(themeDisplay.getLocale())%></li>
								<%}
							}
						}%>
					</ul>
				<%}
				
				Date now=new Date(System.currentTimeMillis());
						
						
						
				if((course.getStartDate().before(now)&&course.getEndDate().after(now))&&permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),"REGISTER")){
				
					if((course.getMaxusers()<=0||UserLocalServiceUtil.getGroupUsersCount(course.getGroupCreatedId())<course.getMaxusers())&&groupC.getType()!=GroupConstants.TYPE_SITE_PRIVATE){
						if(groupC.getType()==GroupConstants.TYPE_SITE_OPEN){%>
						
						<%if(pass){ 		
							List<Team> teams = TeamLocalServiceUtil.getGroupTeams(course.getGroupCreatedId());
							boolean existTeam = false;
							boolean scheduleOpen = false;
							for(Team team : teams){
								Schedule sch = ScheduleLocalServiceUtil.getScheduleByTeamId(team.getTeamId());	
								if(sch!=null){
									existTeam = true;
									if(sch.getStartDate().before(now)&&sch.getEndDate().after(now)){
										scheduleOpen = true;
										%>
										
										<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.noinscrito" /></div>
										<aui:fieldset>
											<aui:column>
												<%= team.getName() %>
											</aui:column>
											<aui:column>
												<%= sch.getStartDate() %>
											</aui:column>
											<aui:column>
												<%= sch.getEndDate() %>
											</aui:column>
											<aui:column>
												<div class="boton_inscibirse ">
													<a href="javascript:${renderResponse.getNamespace()}inscribir('<%=team.getTeamId()%>');"><liferay-ui:message key="inscripcion.inscribete" /></a>
												</div>
											</aui:column>
									</aui:fieldset>
								  <%}									
								}
								
							}
							if(!existTeam){
							%>
							
							<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.noinscrito" /></div>
								<div class="boton_inscibirse ">
									<a href="javascript:${renderResponse.getNamespace()}inscribir('0');"><liferay-ui:message key="inscripcion.inscribete" /></a>
								</div>
						<%
							}else{
								if(!scheduleOpen){
									%>
									<div class="mensaje_marcado">
										<liferay-ui:message key="inscripcion.no-schedule-open"/>
									</div>
									
									<%
								}
							}
						}else{ %>
							
						
								<div class="boton_inscibirse ">
									<liferay-ui:message key="competence.block" />
								</div>
							<%} %>
							
						<%}else if(groupC.getType()==GroupConstants.TYPE_SITE_RESTRICTED){
							List<MembershipRequest> pending = MembershipRequestLocalServiceUtil.getMembershipRequests(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), MembershipRequestConstants.STATUS_PENDING);
							
							if(pending.size()>0){%>
								<div class="mensaje_marcado"><liferay-ui:message key="course.pending" /></div>
							<%}else{
								List<MembershipRequest> denied = MembershipRequestLocalServiceUtil.getMembershipRequests(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), MembershipRequestConstants.STATUS_DENIED);
								
								if(denied.size()>0){%>
									<div class="mensaje_marcado"><liferay-ui:message key="course.denied" /></div><%
								}else{%>
									<%if(pass){ 
										List<Team> teams = TeamLocalServiceUtil.getGroupTeams(course.getGroupCreatedId());
										boolean existTeam = false;
										boolean scheduleOpen = false;
										for(Team team : teams){
											Schedule sch = ScheduleLocalServiceUtil.getScheduleByTeamId(team.getTeamId());	
											if(sch!=null){
												existTeam = true;
												if(sch.getStartDate().before(now)&&sch.getEndDate().after(now)){
													scheduleOpen = true;%>
										
										
										<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.surveillance" /></div>
										<aui:fieldset>
											<aui:column>
												<% team.getName();  %>
											</aui:column>
											<aui:column>
												<% sch.getStartDate(); %>
											</aui:column>
											<aui:column>
												<% sch.getEndDate(); %>
											</aui:column>
											<aui:column>
												<div class="boton_inscibirse ">
													<a href="javascript:${renderResponse.getNamespace()}member('<%=team.getTeamId()%>');"><liferay-ui:message key="inscripcion.request" /></a>
												</div>
											</aui:column>
										</aui:fieldset>
										<% 	
									}									
								}
								
							}
							if(!existTeam){
							%>
							
							<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.surveillance" /></div>
								<div class="boton_inscibirse ">
									<a href="javascript:${renderResponse.getNamespace()}member('0');"><liferay-ui:message key="inscripcion.request" /></a>
								</div>
						<%
							}else{
								if(!scheduleOpen){
									%>
									<div class="mensaje_marcado">
										<liferay-ui:message key="inscripcion.no-schedule-open"/>
									</div>
									
									<%
								}
							}	
						}else{ %>
										<div class="mensaje_marcado ">
											<liferay-ui:message key="competence.block" />
										</div>
									<%} %>
								<%}
							}
						}
					}else{
						if(groupC.getType()==GroupConstants.TYPE_SITE_PRIVATE){
							renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
						}%>
						
						<div class="mensaje_marcado"><liferay-ui:message key="course.full" /></div>
					<%}
					
				}else{
					if(!permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),"REGISTER")){%>
						<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.permission" /></div><%
					}else if(course.getStartDate().after(now)){%>
						<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.date" /></div><%
					}else{%>
						<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.date.pass" /></div><%
					}
						
				}
			}
		} else {
			String urlRedirect= themeDisplay.getURLCurrent();%>
			<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.nologado" /></div>
			<div class="boton_inscibirse ">
				<a href="/c/portal/login?redirect=<%=urlRedirect%>"><liferay-ui:message key="inscripcion.registrate" /></a>
			</div>
		<%}
	}else{
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}%>
</div>

<portlet:actionURL name="member"  var="memberURL" windowState="NORMAL"/>
<portlet:actionURL name="inscribir"  var="inscribirURL" windowState="NORMAL"/>
						
<aui:form name="inscribirFm" action="${inscribirURL}">
	<aui:input name="teamId" value="" type="hidden"/>
</aui:form>


<aui:form name="memberFm" action="${memberURL}">
	<aui:input name="teamId" value="" type="hidden"/>
</aui:form>



<script type="text/javascript">

	function <portlet:namespace />inscribir(teamId){
		$('#<portlet:namespace />teamId').val(teamId);
		$('#<portlet:namespace />inscribirFm').submit();
	}


	function <portlet:namespace />member(teamId){
		$('#<portlet:namespace />teamId').val(teamId);
		$('#<portlet:namespace />memberFm').submit();
	}

</script>

