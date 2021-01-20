<%@page import="com.liferay.lms.asset.ModuleAssetRendererFactory"%>
<%@page import="com.liferay.lms.LmsActivitiesList"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@include file="../init.jsp" %>

<%@ page import="com.liferay.lms.model.Module" %>
<%@ page import="com.liferay.portlet.PortalPreferences" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.util.JavaScriptUtil"%>
<%@ page import="com.liferay.portal.model.PortletConstants"%>


	<jsp:useBean id="addmoduleURL" class="java.lang.String" scope="request" />
	<jsp:useBean id="moduleFilterURL" class="java.lang.String" scope="request" />
	<jsp:useBean id="moduleFilter" class="java.lang.String" scope="request" />
	<%
	
	PortletPreferences preferences = null;
	String portletResource = ParamUtil.getString(request, "portletResource");
	if (Validator.isNotNull(portletResource)) 
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	else
		preferences = renderRequest.getPreferences();
	
	boolean viewAlways = (preferences.getValue("viewAlways", "false")).compareTo("true") == 0;
	
	long moduleId=ParamUtil.getLong(request,"moduleId",0);
	boolean actionEditing=(ParamUtil.getBoolean(request,"actionEditing",false));
	if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ADD_MODULE") && (viewAlways || actionEditing))
	{
	
	%>
	<script>
	Liferay.provide(
	        window,
	        'closePopup',
	        function(popupIdToClose) {
	            var A = AUI();
	
	            A.DialogManager.closeByChild('#' + popupIdToClose);
	        },
	        ['aui-base','aui-dialog','aui-dialog-iframe']
	    );
	Liferay.provide(
	        window,
	        'refreshPortlet',
	        function() {
	
	            <%-- refreshing the portlet [Liferay.Util.getOpener().] --%>
	            var curPortletBoundaryId = '#p_p_id<portlet:namespace />';
	            Liferay.Portlet.refresh(curPortletBoundaryId);
	        },
	        ['aui-dialog','aui-dialog-iframe']
	    );
	
	
	AUI().ready('event', 'node','aui-base','aui-dialog','aui-dialog-iframe','anim','json',function(A) {
		
		A.one(window).on('message', 
			function(event){

				var html5Event=event._event;

				if(A.Lang.isString(html5Event.data)){
					html5Event={data:JSON.parse(html5Event.data)};
				}
		
				if(html5Event.data.name=='reloadModule'){
					<% if(moduleId!=0){ %>
					if(html5Event.data.moduleId==<%=Long.toString(moduleId)%>)
					<% } %>
					{
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
					}

					Liferay.Portlet.refresh(A.one('#p_p_id<portlet:namespace />'));	
	  
				}
				else if(html5Event.data.name=='closeModule'){
					A.DialogManager.closeByChild('#editModule');
				}

			});

	});
	
	</script>
	
	<%
	ModuleAssetRendererFactory moduleAssetRendererFactory = new ModuleAssetRendererFactory();
	PortletURL urlCreateModule = moduleAssetRendererFactory.getURLCreateModule(liferayPortletRequest, liferayPortletResponse, themeDisplay);
	%>
	
	<div class="<%=(themeDisplay.getLayout().getFriendlyURL().equals("/reto"))?"newitem":"newitem2" %>">
		<liferay-ui:icon image="add" message="module-add" label="true" url="<%=urlCreateModule.toString()%>"/>
	</div>
	
	<% 
	} else {
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}

%>
