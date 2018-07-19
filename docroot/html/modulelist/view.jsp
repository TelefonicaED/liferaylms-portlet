<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.portal.model.Team"%>
<%@page import="com.liferay.portal.service.TeamLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ScheduleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Schedule"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.lms.service.ClpSerializer"%>
<%@page import="com.liferay.portal.model.PortletConstants"%>
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="com.liferay.lms.model.ModuleResult"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.ImageLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@ include file="/init.jsp"%>

<%
	PortletPreferences preferences = null;
	String portletResource = ParamUtil.getString(request, "portletResource");
	if (Validator.isNotNull(portletResource)){ 
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}else{
		preferences = renderRequest.getPreferences();
	}
	boolean showLockedModulesIcon = (preferences.getValue("showLockedModulesIcon", "false")).compareTo("true") == 0;
	boolean showModuleIcon = (preferences.getValue("showModuleIcon", "true")).compareTo("true") == 0;
	boolean numerateModules = (preferences.getValue("numerateModules", "false")).compareTo("true") == 0;
	boolean moduleTitleLinkable = (preferences.getValue("moduleTitleLinkable", "false")).compareTo("true") == 0;
	boolean showPercentDone = (preferences.getValue("showPercentDone", "true")).compareTo("true") == 0;
	boolean showModuleStartDate = (preferences.getValue("showModuleStartDate", "true")).compareTo("true") == 0;
	boolean showModuleEndDate = (preferences.getValue("showModuleEndDate", "true")).compareTo("true") == 0;
	boolean allowEditionMode = (preferences.getValue("allowEditionMode", "false")).compareTo("true") == 0;
	boolean allowAccessWhenFinishedButNotClosed = (preferences.getValue("allowAccessWhenFinishedButNotClosed", "false")).compareTo("true") == 0;
	boolean showActivities = Boolean.parseBoolean(preferences.getValue("showActivities", "false"));
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
	
	Date startSchDate = null;
	Date endSchDate = null;
	boolean existSchedule = false;
	if(showModuleStartDate || showModuleEndDate){
		List<Team> teams = TeamLocalServiceUtil.getGroupTeams(themeDisplay.getScopeGroupId());
		for(Team team : teams){		
			Schedule sch = ScheduleLocalServiceUtil.getScheduleByTeamId(team.getTeamId());
			if(sch!=null){
			  showModuleStartDate = false;
			  showModuleEndDate = false;
			  break;
		  }
		}	
	}	
	
	List<Team> teams = null;
			
	if(course!=null){
		teams = TeamLocalServiceUtil.getUserTeams(themeDisplay.getUserId(), course.getGroupCreatedId());
	}
	if(teams!=null && teams.size()>0){
		for(Team team : teams){
			Schedule schedule = ScheduleLocalServiceUtil.getScheduleByTeamId(team.getTeamId());
			if(schedule!=null){
				existSchedule = true;
				startSchDate=schedule.getStartDate();
				endSchDate = schedule.getEndDate();
				break;
			}
		}			
	}
	
	
%>
<liferay-portlet:actionURL name="moveModule" var="moveModuleURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString()%>" />



<c:if test="${renderRequest.preferences.getValue('dragAndDrop', 'true')}">

	<script>
		var ismobile=navigator.userAgent.match(/(iPad)|(iPhone)|(iPod)|(android)|(webOS)/i);
		
		if(!ismobile){
			AUI().ready('node','aui-io-request','aui-parse-content','aui-sortable',function(A) {
		
				new A.Sortable(
					{
						container: A.one('#myContainer'),
					    nodes: 'table#myModule > tbody > tr',
			            after: {   
			            	'drag:end': function(event){ 
			            		
							    var node = event.target.get('node'),
						            prev = node.previous(),
						            next = node.next(),
			                        movedPageId = parseInt(node.get('id').substr(<%=renderResponse.getNamespace().length() %>),0),
					            	prevPageId = 0,
					            	nextPageId = 0;
							    
						        if(prev){
						          prevPageId = parseInt(prev.get('id').substr(<%=renderResponse.getNamespace().length() %>),0);
							    }
		
						        if(next){
						          nextPageId = parseInt(next.get('id').substr(<%=renderResponse.getNamespace().length() %>),0);
							    }
		
								A.io.request('<%=moveModuleURL %>', {  
									data: {
							            <portlet:namespace />pageId: movedPageId,
							            <portlet:namespace />prevPageId: prevPageId,
							            <portlet:namespace />nextPageId: nextPageId
							        },
								    dataType : 'html', 
								  on: {  
							  		success: function() {  
										 Liferay.Portlet.refresh(A.one('#p_p_id<portlet:namespace />'),{'p_t_lifecycle':0,'<%=renderResponse.getNamespace()+WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>':'<%=StringPool.TRUE %>'});
							        }  
								   }  
								});    
			            	}              
			            }
					}
				);
			  });	
		}
	</script>
</c:if>
<%
	String idModuleTable = "idModuleTable";
	boolean moduleEditing = permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Module.class.getName(), themeDisplay.getScopeGroupId(), ActionKeys.UPDATE);
	if(moduleEditing){ 
		idModuleTable = "myModule";
	}
	
	if(course!=null && permissionChecker.hasPermission(course.getGroupCreatedId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW)){
%>
	<div id="myContainer">
	<script type="text/javascript">
		function <portlet:namespace/>changeRowStyle(id, cssClass){
			var trId = '#'+id;
			$(id).addClass(cssClass);
		}
	</script>
	<table class="coursemodule <%="course-status-".concat(String.valueOf(course.getStatus()))%>" id="<%=idModuleTable%>">
<%
		Date today=new java.util.Date(System.currentTimeMillis());
		
			java.util.List<Module> theModules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
			long currentThemeId=0, moduleId=0;
			String currentActivityPortletId =  null;
			long modulesCount=theModules.size();
			int themeId=0;
			boolean moduleIsLocked = false;
			boolean courseIsLocked = course.isLocked(themeDisplay.getUser(), permissionChecker);
			boolean canAccessLock = permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model", themeDisplay.getScopeGroupId() , "ACCESSLOCK");
			boolean courseEditing = (permissionChecker.hasPermission(course.getGroupCreatedId(), Course.class.getName(), course.getCourseId() , ActionKeys.UPDATE))?true:false;
			boolean hasPermissionAccessCourseFinished = LiferaylmsUtil.hasPermissionAccessCourseFinished(themeDisplay.getCompanyId(), course.getGroupCreatedId(), course.getCourseId(), themeDisplay.getUserId());
			int numTd;
			
			for(Module theModule:theModules){
				numTd = 2;
				Date startDate;
				Date endDate;
				if(existSchedule){
					startDate = startSchDate;
					endDate = endSchDate;
				}else{
					startDate = theModule.getStartDate();
					endDate = theModule.getEndDate();
				}
				
				themeId++;
				moduleIsLocked = theModule.isLocked(themeDisplay.getUserId());
				
				if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Module.class.getName(), theModule.getModuleId(), ActionKeys.VIEW) ||
						permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Module.class.getName(), theModule.getModuleId(), ActionKeys.ACCESS)){
				
%>
				<script type="text/javascript">
				  AUI().ready('event', 'node','aui-base','aui-dialog','aui-dialog-iframe','anim','json',function(A) {
						
						A.one(window).on('message', 
							function(event){
								var html5Event=event._event;
								if(A.Lang.isString(html5Event.data)){
									html5Event={data:JSON.parse(html5Event.data)};
								}
						
								if(html5Event.data.name=='reloadModule'){
									<% if(theModule.getModuleId()!=0){ %>
										if(html5Event.data.moduleId==<%=Long.toString(theModule.getModuleId())%>){
									<% } %>
									
										var moduleTitlePortlet=A.one('#p_p_id<%=StringPool.UNDERLINE+PortalUtil.getJsSafePortletId("ModuleTitle"+
												PortletConstants.WAR_SEPARATOR+portletConfig.getPortletContext().getPortletContextName())+StringPool.UNDERLINE %>');
										if(moduleTitlePortlet!=null) {
											Liferay.Portlet.refresh(moduleTitlePortlet);
										}
										var moduleDescriptionPortlet=A.one('#p_p_id<%=PortalUtil.getJsSafePortletId(StringPool.UNDERLINE+"moduleDescription"+
												PortletConstants.WAR_SEPARATOR+portletConfig.getPortletContext().getPortletContextName())+StringPool.UNDERLINE %>');
										if(moduleDescriptionPortlet!=null) {
											Liferay.Portlet.refresh(moduleDescriptionPortlet);
										}
										var activityNavigatorPortlet=A.one('#p_p_id<%=PortalUtil.getJsSafePortletId(StringPool.UNDERLINE+"activityNavigator"+
												PortletConstants.WAR_SEPARATOR+portletConfig.getPortletContext().getPortletContextName())+StringPool.UNDERLINE %>');
										if(activityNavigatorPortlet!=null) {
											Liferay.Portlet.refresh(activityNavigatorPortlet);
										}
									<% if(theModule.getModuleId()!=0){ %>
										}
									<%}%>
									Liferay.Portlet.refresh(A.one('#p_p_id<portlet:namespace />'));	
					  
								}
								else if(html5Event.data.name=='closeModule'){
									A.DialogManager.closeByChild('#editModule');
								}
							});
					});
				</script>
				<tr id="<portlet:namespace/><%=theModule.getModuleId()%>">
<% 					
					if(showLockedModulesIcon){
						boolean showLock = (courseEditing || moduleEditing || canAccessLock)?true:false;
						if(showLock){ 
							numTd++;
%>
							<td class="icon">
<%
								if(startDate!=null &&today.before(startDate)){
%>
									<liferay-ui:icon image="lock" alt="starting-soon" />
<%
								}else if(courseIsLocked || moduleIsLocked){
%>
									<liferay-ui:icon image="lock" alt="closed"/>
<%
								}
%>
							</td>
<%
						}
					}
					if(showModuleIcon){
						numTd++;
%>
						<td class="icon">
<% 
                            long entryId=theModule.getIcon();
                            if(entryId!=0){
                                try{
                                    FileEntry image=DLAppLocalServiceUtil.getFileEntry(entryId);
                                    if(image!=null){
    %>
                                        <img src="<%= DLUtil.getPreviewURL(image, image.getFileVersion(), themeDisplay, "&imageThumbnail=1") %>" alt="<%=theModule.getTitle(themeDisplay.getLocale())%>"/>
    <%
                                    }    
                                }catch(Exception e){
                                }
                            }
%>
                        </td>
					
<%
					}
					
					Group grupo=themeDisplay.getScopeGroup();
					long retoplid=themeDisplay.getPlid();
					for(Layout theLayout:LayoutLocalServiceUtil.getLayouts(grupo.getGroupId(),false)){
						if(theLayout.getFriendlyURL().equals("/reto")){
							retoplid=theLayout.getPlid();
						}
					}
						
					LiferayPortletURL  gotoModuleURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(renderRequest),
								PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName()), retoplid, PortletRequest.ACTION_PHASE);		
					gotoModuleURL.setParameter(ActionRequest.ACTION_NAME, "goToModule");
					gotoModuleURL.removePublicRenderParameter("actId");
					gotoModuleURL.setWindowState(WindowState.NORMAL);
					gotoModuleURL.setParameter("moduleId", Long.toString(theModule.getModuleId()));
					gotoModuleURL.setParameter("themeId", Long.toString(themeId));
					gotoModuleURL.setParameter("actionEditing", Boolean.toString(moduleEditing));
					gotoModuleURL.setPlid(retoplid);
					gotoModuleURL.setPortletId("lmsactivitieslist_WAR_liferaylmsportlet");
					Object[] arg =  new Object[2];
					arg[0] = themeId;
					arg[1] = theModule.getTitle(themeDisplay.getLocale());
					
					boolean canAccess = (startDate != null )?(!today.before(startDate)&&!courseIsLocked):true;
%>
					
					<td class="title">
<%				
						if((moduleTitleLinkable || (allowEditionMode && moduleEditing)) && canAccess && (canAccessLock || !moduleIsLocked)){				 
%>
							<a href="<%=gotoModuleURL.toString() %>"><%=(numerateModules)?
																			LanguageUtil.format(pageContext, "moduleTitle.chapter", arg):
																				theModule.getTitle(themeDisplay.getLocale()) %></a>
<%		
						}else{
%>
							<%=(numerateModules)?
											LanguageUtil.format(pageContext, "moduleTitle.chapter", arg):
												theModule.getTitle(themeDisplay.getLocale()) %>
<%
						}
%>				
					</td>
					
<%
					if(showPercentDone){
						numTd++;
%>
						<td class="percent">
<%
							ModuleResult moduleResult=ModuleResultLocalServiceUtil.getByModuleAndUser(theModule.getModuleId(),themeDisplay.getUserId());
							long done=0;
							if(moduleResult!=null){
								done=moduleResult.getResult();
%>
								<%=done %>%
<%
							}
%>
						</td>
<%
					}
					if(showModuleStartDate || showModuleEndDate ){
						numTd++;
%>
						<td class="date">
<%
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm",themeDisplay.getLocale());
							sdf.setTimeZone(themeDisplay.getTimeZone());
							if(startDate!=null &&today.before(startDate)){
								if(showModuleStartDate){
%>
									<liferay-ui:message key="fecha-inicio"/><br />
									<%=	sdf.format(startDate)%>
									<%--=	dateFormatDate.format(new Date(dateOffSet))--%>
<%
								}
							}else{
								if(endDate!=null&&today.before(endDate)){
									if(showModuleStartDate){
%>
										<liferay-ui:message key="fecha-inicio"/><br />
										<%=	sdf.format(startDate)%>
										<%--=	dateFormatDateTime.format(new Date(dateOffSet))--%><br />
<%
									}
									if(showModuleEndDate){
%>
										<liferay-ui:message key="fecha-fin"/><br />
										<%=	sdf.format(endDate)%>
										<%--=	dateFormatDateTime.format(new Date(dateOffSet))--%>
<%
									}
								}
							}
%>
						</td>
<%
					}
%>
					
					<td class="contain_actions">
<%
					if(themeDisplay.isSignedIn()){
						if(startDate!=null &&today.before(startDate) && !canAccessLock){
%>
							<script type="text/javascript">
								<portlet:namespace/>changeRowStyle(<portlet:namespace/><%=theModule.getModuleId()%>,'starting-soon');
							</script>

							<div class="access"><liferay-ui:message key="starting-soon"/><!-- En LMS esta etiqueta es vacía --></div>
<%
						}else if((moduleIsLocked || courseIsLocked) && !canAccessLock && !hasPermissionAccessCourseFinished){
							if(UserLocalServiceUtil.hasGroupUser(themeDisplay.getScopeGroupId(),themeDisplay.getUserId())){
%>								
								<script type="text/javascript">
									<portlet:namespace/>changeRowStyle(<portlet:namespace/><%=theModule.getModuleId()%>,'module-closed');
								</script>
								<div class="access"><liferay-ui:message key="module-closed"/><!-- En LMS esta etiqueta es vacía --></div>
<%
							}
						}else if((!moduleIsLocked && !courseIsLocked) || canAccessLock || hasPermissionAccessCourseFinished){
							if(allowEditionMode && moduleEditing){
								%>
								<script type="text/javascript">
									<portlet:namespace/>changeRowStyle(<portlet:namespace/><%=theModule.getModuleId()%>,'module-actions');
								</script>
								<div class="iconsedit"><%@ include file="/JSPs/module/edit_actions.jspf" %></div>
								<%							
							}else if(!allowAccessWhenFinishedButNotClosed && ModuleLocalServiceUtil.isUserPassed(theModule.getModuleId(),themeDisplay.getUserId())){
%>
								<script type="text/javascript">
									<portlet:namespace/>changeRowStyle(<portlet:namespace/><%=theModule.getModuleId()%>,'module-finished');
								</script>
								<div class="access"><a href="<%=gotoModuleURL.toString() %>"><liferay-ui:message key="module-finissed" /></a></div>
<%
							}else {
%>
								<script type="text/javascript">
									<portlet:namespace/>changeRowStyle(<portlet:namespace/><%=theModule.getModuleId()%>,'module-access');
								</script>
								<div class="access"><a class="module-list-button-access" href="<%=gotoModuleURL.toString() %>"><liferay-ui:message key="module-access" /></a></div>
<%
							} 
						}
					}
%>
					</td>
				</tr>
				
		<%if(showActivities){
			List<LearningActivity> moduleActivities = theModule.getListLearningActivities();
		%>
			
			<tr class="activity-list-row"><td colspan="<%=numTd%>">
				<liferay-ui:panel-container extended="false" id="<%=\"panel_container_\" + theModule.getModuleId()%>">
					<liferay-ui:panel title="modulelist.activity-list-title" id="<%=\"panel_\" + theModule.getModuleId()%>" defaultState="closed" collapsible="true">
						<%
						int i=1;
						for(LearningActivity activity:moduleActivities){%>
							<div class="activity-row">
								<span class="col-1"><%=i%></span>
								<span class="col-2"><%=activity.getTitle(locale) %></span>
							</div>
						<%i++;
						}
						%>
					</liferay-ui:panel>
				</liferay-ui:panel-container>
	
			</td></tr>
		<%}%>		
				
<%		}	
	}%>
  </table>
</div>
<script type="text/javascript">
function <portlet:namespace/>changeRowStyle(id, cssClass){
	$('#'+id).addClass(cssClass);
}
</script>
<%}else{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}%>