<%@page import="com.liferay.portal.model.UserGroupRole"%>
<%@page import="com.liferay.portal.service.UserGroupRoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.service.TeamLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Team"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.portal.security.permission.PermissionCheckerFactoryUtil"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.lms.asset.LearningActivityAssetRendererFactory"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse"%>
<%@page import="java.util.Arrays"%>
<%@ include file="/init.jsp" %>
<liferay-ui:panel-container >
<%
long teamId=ParamUtil.getLong(request, "teamId",0);
PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("jspPage","/html/gradebook/view.jsp");
portletURL.setParameter("teamId", Long.toString(teamId)); 

java.util.List<Team> userTeams=TeamLocalServiceUtil.getUserTeams(themeDisplay.getUserId(), themeDisplay.getScopeGroupId());

Team theTeam=null;
boolean hasNullTeam=false;
if(teamId>0 && (TeamLocalServiceUtil.hasUserTeam(themeDisplay.getUserId(), teamId)||userTeams.size()==0))
{		
	theTeam=TeamLocalServiceUtil.fetchTeam(teamId);	
}
else
{
	if(userTeams!=null&& userTeams.size()>0)
	{
		theTeam=userTeams.get(0);	
		teamId=theTeam.getTeamId();
	}
}
if(userTeams.size()==0)
{
	hasNullTeam=true;
	userTeams=TeamLocalServiceUtil.getGroupTeams(themeDisplay.getScopeGroupId());
}

if(userTeams!=null&& userTeams.size()>0)
	{
	%>
	
<portlet:renderURL var="buscarURL">
		<portlet:param name="jspPage" value="/html/gradebook/view.jsp"></portlet:param>
	</portlet:renderURL>
<aui:form name="studentsearch" action="<%=buscarURL %>" method="post">
<aui:fieldset>
	<aui:column>
		<aui:select name="teamId" id="teamIdSelect" label="team">
		<%
		if(hasNullTeam)
		{
			if(teamId==0)
			{
			%>
			<aui:option label="--" value="0" selected="true"></aui:option>
			<%
			}
			else
			{
				%>
				<aui:option label="--" value="0"></aui:option>
				<%
			}
		}
		for(Team team:userTeams)
		{
			if(teamId==team.getTeamId())
			{
			%>
			<aui:option label="<%=team.getName() %>" value="<%=team.getTeamId() %>" selected="true"></aui:option>
			<%
			}
			else
			{
			%>
			<aui:option label="<%=team.getName() %>" value="<%=team.getTeamId() %>"></aui:option>
			<%
			}
		}
		%>
		</aui:select>
	</aui:column>	
	<aui:button-row>
		<aui:button name="searchUsers" value="select" type="submit" />
	</aui:button-row>
</aui:fieldset>
</aui:form>
<%
	}

if(theTeam!=null)
{ %>
<liferay-ui:header title="<%=theTeam.getName() %>" showBackURL="false"></liferay-ui:header>

<%
}
	java.util.List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
	if(modules != null){
		int fila = 0;
	for(Module theModule:modules){
%>
		<liferay-ui:panel id="<%=Long.toString(theModule.getModuleId()) %>" title="<%=theModule.getTitle(themeDisplay.getLocale()) %>" collapsible="true" extended="true" defaultState="<%=(fila==0)?\"open\":\"collapsed\" %>">
			<liferay-portlet:resourceURL var="exportURL" >
				<portlet:param name="action" value="export"/>
				<portlet:param name="moduleId" value="<%=Long.toString(theModule.getModuleId()) %>"/>
				<portlet:param name="teamId" value="<%=Long.toString(teamId)%>" />
				<portlet:param name="random" value="<%=Long.toString(System.currentTimeMillis())%>" />
			</liferay-portlet:resourceURL>
			<liferay-ui:icon image="export" label="<%= true %>" message="offlinetaskactivity.csv.export" method="get" url="<%=exportURL%>" />

			<liferay-portlet:renderURL var="returnurl">
			<liferay-portlet:param name="jspPage" value="/html/gradebook/view.jsp" />
			</liferay-portlet:renderURL>
			<liferay-ui:search-container  emptyResultsMessage="there-are-no-results" delta="40" deltaConfigurable="true">
				<liferay-ui:search-container-results>
					<%
						List<User> onlyStudents=new ArrayList<User>();
						if((PermissionCheckerFactoryUtil.create(themeDisplay.getUser())).hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model", themeDisplay.getScopeGroupId(), "VIEW_RESULTS")){
							List<User> usus=null;
							if(theTeam==null) {
								usus = UserLocalServiceUtil.getGroupUsers(themeDisplay.getScopeGroupId());
							}
							else {
								LinkedHashMap userParams = new LinkedHashMap();
								userParams.put("usersGroups", theTeam.getGroupId());
								userParams.put("usersTeams", theTeam.getTeamId());
								OrderByComparator obc = null;
								usus  = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), "", 0, userParams, searchContainer.getStart(), searchContainer.getEnd(), obc);	
							}
							
							for(User usu:usus){
								boolean shouldPass = true;
								List<UserGroupRole> listRolesOfUser = UserGroupRoleLocalServiceUtil.getUserGroupRoles(usu.getUserId(), themeDisplay.getScopeGroupId());

								//System.out.println("Usuario "+usu.getFullName());
								for(int i=0; i<listRolesOfUser.size();i++){
									//System.out.println("    -->ROL: "+listRolesOfUser.get(i).getRole().getName());
									if(listRolesOfUser.get(i).getRole().getName().equals("courseTeacher")){
										shouldPass=false;
										break;
									}
								}
								
								if(!(PermissionCheckerFactoryUtil.create(usu)).hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model", themeDisplay.getScopeGroupId(), "VIEW_RESULTS") 
										&& shouldPass){
									onlyStudents.add(usu);
								}
							}
								
						}else{
							onlyStudents.add(themeDisplay.getUser());	
						}
							
						
						List<User> orderedUsers = new ArrayList<User>();
				        orderedUsers.addAll(onlyStudents);
				        Collections.sort(orderedUsers, new Comparator<User>() {
				            @Override
				            public int compare(final User object1, final User object2) {
				                return object1.getFullName().toLowerCase().compareTo(object2.getFullName().toLowerCase());
				            }
				        } );
					
						pageContext.setAttribute("results", ListUtil.subList(orderedUsers, searchContainer.getStart(), searchContainer.getEnd()));
					    pageContext.setAttribute("total", onlyStudents.size());
					%>
				</liferay-ui:search-container-results>
				<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="usuario">
					<liferay-portlet:renderURL var="userDetailsURL">
						<liferay-portlet:param name="jspPage" value="/html/gradebook/userdetails.jsp"></liferay-portlet:param>
						<liferay-portlet:param name="userId" value="<%=Long.toString(usuario.getUserId()) %>"></liferay-portlet:param>
					</liferay-portlet:renderURL>
					<liferay-ui:search-container-column-text name="student-name">
						<liferay-ui:user-display userId="<%=usuario.getUserId() %>" url = "<%=userDetailsURL%>"/>
					</liferay-ui:search-container-column-text>
					<% 	List<LearningActivity> activities = LearningActivityServiceUtil.getLearningActivitiesOfModule(theModule.getModuleId());
					activities = LiferaylmsUtil.getVisibleActivities(themeDisplay, activities, permissionChecker);
					if(activities != null){
					for(LearningActivity learningActivity: activities){
						String result= "-";
						String status="not-started";
						if(LearningActivityResultLocalServiceUtil.existsLearningActivityResult(learningActivity.getActId(), usuario.getUserId())){
							status="started";
							LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), usuario.getUserId());
							if(learningActivity.getTypeId() == 8){
								result= (learningActivityResult!=null)?LearningActivityResultLocalServiceUtil.translateResult(themeDisplay.getLocale(), learningActivityResult.getResult(), learningActivity.getGroupId()):"";
							}else{
								result = ""+learningActivityResult.getResult();
							}
							if(learningActivityResult.getEndDate()!=null){
								status="not-passed"	;
							}
							if(learningActivityResult.isPassed()){
								status="passed"	;
							}
						}%>
						<portlet:renderURL var="viewUrlPopGrades" windowState="<%= LiferayWindowState.POP_UP.toString() %>">   
							<portlet:param name="actId" value="<%=String.valueOf(learningActivity.getActId()) %>" />      
						    <portlet:param name="jspPage" value="/html/gradebook/popups/grades.jsp" />           
						</portlet:renderURL>
						<portlet:renderURL var="setGradesURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">   
							<portlet:param name="ajaxAction" value="setGrades" />      
						   	<portlet:param name="jspPage" value="/html/gradebook/popups/grades.jsp" />           
						</portlet:renderURL>
						<script type="text/javascript">
						function <portlet:namespace />showPopupActivity(studentId, actId, actType) {
							
							var url = '/html/gradebook/popups/activity.jsp';
							AUI().use('aui-dialog','liferay-portlet-url', function(A){
								var renderUrl = Liferay.PortletURL.createRenderURL();							
								renderUrl.setWindowState('<%= LiferayWindowState.POP_UP.toString() %>');
								renderUrl.setPortletId('<%=portletDisplay.getId()%>');
								renderUrl.setParameter('actId', actId);
								renderUrl.setParameter('studentId', studentId);
								renderUrl.setParameter('actType', actType);
								renderUrl.setParameter('jspPage', url);
			
								window.<portlet:namespace />popupActivity = new A.Dialog({
									id:'<portlet:namespace />showPopupActivity',
						            title: '<liferay-ui:message key="coursestats.modulestats.activity" />',
						            centered: true,
						            modal: true,
						            width: 700,
						            height: 800,
						            after: {   
							          	close: function(event){ 
							          		document.location.reload();
						            	}
						            }
						        }).plug(A.Plugin.IO, {
						            uri: renderUrl.toString()
						        }).render();
								window.<portlet:namespace />popupActivity.show();   
							});
					    }
						
						function <portlet:namespace />doClosePopupActivity(){
						    AUI().use('aui-dialog', function(A) {
						    	window.<portlet:namespace />popupActivity.close();
						    });
						}
						
						function <portlet:namespace />showPopupGrades(studentId, actId) {
			
							AUI().use('aui-dialog','liferay-portlet-url', function(A){
								var renderUrl = Liferay.PortletURL.createRenderURL();							
								renderUrl.setWindowState('<%= LiferayWindowState.POP_UP.toString() %>');
								renderUrl.setPortletId('<%=portletDisplay.getId()%>');
								renderUrl.setParameter('actId', actId);
								renderUrl.setParameter('studentId', studentId);
								renderUrl.setParameter('jspPage', '/html/gradebook/popups/grades.jsp');
			
								window.<portlet:namespace />popupGrades = new A.Dialog({
									id:'<portlet:namespace />showPopupGrades',
						            title: '<liferay-ui:message key="offlinetaskactivity.set.grades" />',
						            centered: true,
						            modal: true,
						            width: 370,
						            height: 300
						        }).plug(A.Plugin.IO, {
						            uri: renderUrl.toString()
						        }).render();
								window.<portlet:namespace />popupGrades.show();   
							});
					    }
					    
						function <portlet:namespace />doClosePopupGrades(){
						    AUI().use('aui-dialog', function(A) {
						    	window.<portlet:namespace />popupGrades.close();
						    });
						}
						
						function <portlet:namespace />doSaveGrades(studentId, actId) {
					        AUI().use('aui-io-request','io-form', function(A) {
					            A.io.request('<%= setGradesURL %>', { 
					                method : 'POST', 
					                form: {
					                    id: '<portlet:namespace />fn_grades'
					                },
					                dataType : 'html', 
					                headers:{
					                	actId: actId,
					                	studentId: studentId
					                },
					                on : {
					                	success : function() { 
					                    	A.one('.aui-dialog-bd').set('innerHTML',this.get('responseData'));	
					                    	window.<portlet:namespace />popupGrades.close();
					                    	// No se muy bien por qué se realizaba una recarga de la página. 
					                    	// Parece que no afecta al funcionamiento del portlet a peor, sino a mejor.
					                    	// document.location.reload();
					                    },
						                cancel : function() { 
					                    	window.<portlet:namespace />popupGrades.close();
					                    } 
					                } 
					            });
					        });
					    }
						</script>
						<liferay-ui:search-container-column-text cssClass="number-column" name = "<%=learningActivity.getTitle(themeDisplay.getLocale()) %>" align="center">
							<%=(result.trim().equalsIgnoreCase("-")) ? result:  result + "/100" %>
							<% if(status.equals("passed")){%>
							 	<liferay-ui:icon image="checked" message="passed"></liferay-ui:icon>
							<%} else if(status.equals("not-passed")){%>
							 	<liferay-ui:icon image="close" message="not-passed"></liferay-ui:icon>
							<%} else if(status.equals("started")){%>
						 		<liferay-ui:icon image="unchecked" message="unchecked"></liferay-ui:icon>
						 	<%}
							
				 			if(status.equals("passed") || status.equals("not-passed")){
				 				if((PermissionCheckerFactoryUtil.create(themeDisplay.getUser())).hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model", themeDisplay.getScopeGroupId(), "VIEW_RESULTS")){%>
						 			<liferay-ui:icon image="edit" url='<%="javascript:"+renderResponse.getNamespace() + "showPopupGrades("+Long.toString(usuario.getUserId())+","+String.valueOf(learningActivity.getActId())+");" %>' />
							 		<% String typesThatCanBeSeen = "0,3,6";
							 		if(typesThatCanBeSeen.contains(String.valueOf(learningActivity.getTypeId()))){
							 			%>
							 			<liferay-ui:icon image="view" url='<%="javascript:"+renderResponse.getNamespace() + "showPopupActivity("+Long.toString(usuario.getUserId())+","+String.valueOf(learningActivity.getActId())+","+String.valueOf(learningActivity.getTypeId())+");" %>'/>
							 		<%}
						  		}
				 			}%>
						</liferay-ui:search-container-column-text>
					<%} 
					}%>
				</liferay-ui:search-container-row>
			 	<liferay-ui:search-iterator />
			</liferay-ui:search-container>

		</liferay-ui:panel>
	<%fila++;
	}
	}%>
</liferay-ui:panel-container>
