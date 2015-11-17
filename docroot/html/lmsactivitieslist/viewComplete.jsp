<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="java.net.URL"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
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
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>

<%@ include file="/init.jsp"%>

<%
PortletPreferences preferences = null;
String portletResource = ParamUtil.getString(request, "portletResource");
if (Validator.isNotNull(portletResource)) 
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
else
	preferences = renderRequest.getPreferences();

boolean numerateModules = (preferences.getValue("numerateModules", "false")).compareTo("true") == 0;
%>

<%if (SessionErrors.contains(renderRequest, "activities-in-module")) { %>
	<script type="text/javascript">
		<!--
			AUI().ready(function(A) {
				alert('<%=LanguageUtil.get(pageContext," module.delete.module.with.activity")%>');
			});
		//-->
	</script>
<% } %>
<%
long moduleId = ParamUtil.getLong(request, "moduleId", 0);
boolean actionEditing = ParamUtil.getBoolean(request, "actionEditing", false);
long actId = ParamUtil.getLong(request, "actId", 0);
if (moduleId == 0){
	java.util.List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
	if (modules.size() > 0) {
		Module theModule = modules.get(0);
		moduleId = theModule.getModuleId();
	}
}

String currentActivityPortletId =  null;
if(actId!=0) {				
	LearningActivity currentLeaningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
	LearningActivityType currentLearningActivityType = new LearningActivityTypeRegistry().getLearningActivityType(currentLeaningActivity.getTypeId());
	if(Validator.isNotNull(currentLearningActivityType)) {
		currentActivityPortletId = currentLearningActivityType.getPortletId();
	}
}

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
		boolean registrado=UserLocalServiceUtil.hasGroupUser(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		if(course!=null && permissionChecker.hasPermission(course.getGroupCreatedId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW) && !course.isClosed()){
			java.util.List<Module> theModules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
			Date today=new java.util.Date(System.currentTimeMillis());
			long currentThemeId=0;
			for(Module theModule:theModules){
				currentThemeId++;
				if(moduleId == theModule.getModuleId()) 
					break;
			}
			
			long modulesCount=theModules.size();
			long themeId=0;
			for(Module theModule:theModules){
				themeId++;
				%>
				
				<%--li class='<%= moduleId == theModule.getModuleId() ? "option-less" : "option-more"%>' --%>
				<li class='option-none  <%=theModule.getModuleId() == moduleId ? "option-less":"" %>' id="<portlet:namespace/><%=theModule.getModuleId()%>">
					<%
					if(theModule.getModuleId() == moduleId)
					{
						%>
						<span class="desplegar"></span>
						<%
					}
					%>
										
					<%
					ModuleResult moduleResult=ModuleResultLocalServiceUtil.getByModuleAndUser(theModule.getModuleId(),themeDisplay.getUserId());
					long done=0;
					if(moduleResult!=null){
						done=moduleResult.getResult();
					}
					java.util.Date now=new java.util.Date(System.currentTimeMillis());
					if(!ModuleLocalServiceUtil.isLocked(theModule.getModuleId(),themeDisplay.getUserId())||actionEditing||
							permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model", themeDisplay.getScopeGroupId() , "ACCESSLOCK")){
						LiferayPortletURL  gotoModuleURL = (LiferayPortletURL)renderResponse.createActionURL();	
						gotoModuleURL.setParameter(ActionRequest.ACTION_NAME, "goToModule");
					    gotoModuleURL.removePublicRenderParameter("actId");
					    gotoModuleURL.setWindowState(WindowState.NORMAL);
					    gotoModuleURL.setParameter("moduleId", Long.toString(theModule.getModuleId()));
					    gotoModuleURL.setParameter("themeId", Long.toString(themeId));
					    gotoModuleURL.setPlid(themeDisplay.getPlid());
					    URL goToModuleRelativeURL = new URL(gotoModuleURL.toString());
						%>
						<a href="<%="?"+goToModuleRelativeURL.getQuery()%>" >
						<%
					if(theModule.getModuleId() != moduleId)
					{
						if(ModuleLocalServiceUtil.isLocked(theModule.getModuleId(),themeDisplay.getUserId()))
						{
							%>
							<span class="locked"></span>
							<%
						}
						else
						{
							
						
						%>
						<span class="desplegar"></span>
						<%
						}
					}
					%>
						<%= (numerateModules)?
								LanguageUtil.format(pageContext, "moduleTitle.chapter", new Object[]{themeId,theModule.getTitle(themeDisplay.getLocale())}):
									theModule.getTitle(themeDisplay.getLocale()) %></a>
						<%if(actionEditing){%>
							<div class="iconsedit"><%@ include file="/JSPs/module/edit_actions.jspf" %></div>
						<%}
					}else
					{%>
						<span class="locked"></span><%=(numerateModules)?
															LanguageUtil.format(pageContext, "moduleTitle.chapter", new Object[]{themeId,theModule.getTitle(themeDisplay.getLocale())}):
																theModule.getTitle(themeDisplay.getLocale())  %> <span class="module-percent"><%=done %>%</span>
						<%if(actionEditing){}
					}
					if((theModule.getModuleId()==moduleId)&&(ParamUtil.getBoolean(renderRequest, "viewCurrentModule",true))){%>
						<div class="lms-desplegable" style="overflow: hidden;" >
							<jsp:include page="/html/lmsactivitieslist/viewactivities.jsp"></jsp:include>
						</div>
					<%}	%>		
				</li>
			<%}%>
			</ul>
			</div>
		<%}else{
			if(course==null){%>
				<jsp:include page="/html/lmsactivitieslist/viewactivities.jsp"></jsp:include>
			<%}else{
				renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
			}
		}%>
