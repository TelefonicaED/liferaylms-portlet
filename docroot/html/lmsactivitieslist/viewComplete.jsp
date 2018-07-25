<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@page import="java.net.URL"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="com.liferay.lms.model.ModuleResult"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>

<%

if (SessionErrors.contains(renderRequest, "activities-in-module")) { %>
	<script type="text/javascript">
		<!--
			AUI().ready(function(A) {
				alert('<%=LanguageUtil.get(pageContext," module.delete.module.with.activity")%>');
			});
		//-->
	</script>
<% }


String idModuleUl = "idModuleUl";
boolean moduleEditing = permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Module.class.getName(), themeDisplay.getScopeGroupId(), ActionKeys.UPDATE);
if(moduleEditing) idModuleUl = "myModule";
%>

<liferay-portlet:actionURL name="moveModule" var="moveModuleURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString()%>" />

<script type="text/javascript">
	<!-- 
	var ismobile=navigator.userAgent.match(/(iPad)|(iPhone)|(iPod)|(android)|(webOS)/i);
	
	if(!ismobile){
	AUI().ready('node','aui-io-request','aui-parse-content','aui-sortable',function(A) {
	
		new A.Sortable(
			{
				container: A.one('#myContainer'),
			    nodes: 'ul#myModule > li',
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
	
	//-->
</script>

<div id="myContainer" class="modulo portlet-toolbar search-form lms-tree">

	<ul id="<%=idModuleUl%>">
		<%
		
		renderRequest.setAttribute("moduleId", Long.toString(moduleId));
		
		if(course!=null && permissionChecker.hasPermission(course.getGroupCreatedId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW) && !course.isClosed()){
			
			List<Module> theModules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
			Date now=new Date();
			
			long currentThemeId=0;
			for(Module theModule:theModules){
				currentThemeId++;
				if(moduleId == theModule.getModuleId()) 
					break;
			}
			
			//actionEditing = actionEditing && moduleEditing;
			
			boolean hasPermissionAccessCourseFinished = LiferaylmsUtil.hasPermissionAccessCourseFinished(themeDisplay.getCompanyId(), course.getGroupCreatedId(), course.getCourseId(), themeDisplay.getUserId());
			
			long modulesCount=theModules.size();
			long themeId=0;
			moduleActuallyIsLocked = true;
			
			boolean numerateModules = (preferences.getValue("numerateModules", "false")).compareTo("true") == 0;
			
			String currentActivityPortletId =  null;
			
			if(actId!=0) {				
				LearningActivity currentLeaningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
				LearningActivityType currentLearningActivityType = new LearningActivityTypeRegistry().getLearningActivityType(currentLeaningActivity.getTypeId());
				if(Validator.isNotNull(currentLearningActivityType)) {
					currentActivityPortletId = currentLearningActivityType.getPortletId();
				}
			}
			
			boolean courseIsLocked = course.isLocked(themeDisplay.getUser(), themeDisplay.getPermissionChecker());
			boolean accessLock = CourseLocalServiceUtil.canAccessLock(themeDisplay.getScopeGroupId(), themeDisplay.getUser());
			
			//NECESARIO PARA LA LISTA DE ACTIVIDADES
	
			NumberFormat resultNumberFormat = NumberFormat.getInstance(locale);
			resultNumberFormat.setMinimumIntegerDigits(1);
			
			LearningActivity currentLeaningActivity = null;

			if(actId!=0) {
				currentLeaningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			}
			
			LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
			AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
		
			//FIN DE NECESARIO PARA LISTA DE ACTIVIDADES
			long numActivities = 0;
			for(Module theModule:theModules){
				themeId++;
				numActivities = LearningActivityLocalServiceUtil.countLearningActivitiesOfModule(theModule.getModuleId());
				moduleActuallyIsLocked = theModule.isLocked(themeDisplay.getUserId());
				if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Module.class.getName(), theModule.getModuleId(), ActionKeys.VIEW) ||
						permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), Module.class.getName(), theModule.getModuleId(), ActionKeys.ACCESS)){
				%>
				
				<li class='option-none  <%=theModule.getModuleId() == moduleId ? "option-less selected":"" %> <%=numActivities > 0 ? " module-with-activities ":" module-without-activities "  %><%= (!hasPermissionAccessCourseFinished && moduleActuallyIsLocked) ? " locked ":""%>' id="<portlet:namespace/><%=theModule.getModuleId()%>">
					
					
					<%
					if(!hasPermissionAccessCourseFinished && moduleActuallyIsLocked){%>
						<span class="locked"></span>
					<%} else {%>
						<span class="desplegar"></span>
					<%}
					
					
					if(accessLock || (!courseIsLocked && !moduleActuallyIsLocked) || actionEditing || hasPermissionAccessCourseFinished){
						
						LiferayPortletURL  gotoModuleURL = (LiferayPortletURL)renderResponse.createActionURL();	
						gotoModuleURL.setParameter(ActionRequest.ACTION_NAME, "goToModule"); 
					    gotoModuleURL.removePublicRenderParameter("actId");
					    gotoModuleURL.setWindowState(WindowState.NORMAL);
					    gotoModuleURL.setParameter("moduleId", Long.toString(theModule.getModuleId()));
					    gotoModuleURL.setParameter("themeId", Long.toString(themeId));
					    gotoModuleURL.setPlid(themeDisplay.getPlid());
					    URL goToModuleRelativeURL = new URL(gotoModuleURL.toString());%>
					    
						<a href="<%="?"+goToModuleRelativeURL.getQuery()%>" >
						
							
							
							<%= (numerateModules)?
									LanguageUtil.format(pageContext, "moduleTitle.chapter", new Object[]{themeId,theModule.getTitle(themeDisplay.getLocale())}):
										theModule.getTitle(themeDisplay.getLocale()) %>
						</a>
						
						<%if(actionEditing && moduleEditing){%>
							<div class="iconsedit"><%@ include file="/JSPs/module/edit_actions.jspf" %></div>
						<%}
						if((theModule.getModuleId()==moduleId)&&(ParamUtil.getBoolean(renderRequest, "viewCurrentModule",true))){%>
							<div class="lms-desplegable" style="overflow: hidden;" >
								<%@ include file="/html/lmsactivitieslist/viewactivities.jsp"%>
							</div>
						<%}		
					} else {
						
						ModuleResult moduleResult=ModuleResultLocalServiceUtil.getByModuleAndUser(theModule.getModuleId(),themeDisplay.getUserId());
						long done=0;
						if(moduleResult!=null){
							done=moduleResult.getResult();
						}%>
						
						<span><%=(numerateModules)?LanguageUtil.format(pageContext, "moduleTitle.chapter", new Object[]{themeId,theModule.getTitle(themeDisplay.getLocale())}):
																theModule.getTitle(themeDisplay.getLocale())  %></span> <span class="module-percent"><%=done %>%</span>
					<%}%>
					
				</li>
			<% }
			}%>
		<%}else{
			if(course==null){
				
				NumberFormat resultNumberFormat = NumberFormat.getInstance(locale);
				resultNumberFormat.setMinimumIntegerDigits(1);
				
				LearningActivity currentLeaningActivity = null;
		
				if(actId!=0) {
					currentLeaningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
				}
				
				LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
				AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
		
				boolean hasPermissionAccessCourseFinished = false;
				boolean accessLock = CourseLocalServiceUtil.canAccessLock(themeDisplay.getScopeGroupId(), themeDisplay.getUser());
				Module theModule = currentModule;
				%>
				<%@ include file="/html/lmsactivitieslist/viewactivities.jsp"%>
			<%}else{
				renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
			}
		}%>
	</ul>
</div>