<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.PortletConstants"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@ include file="/init.jsp"%>

<%
	long moduleId = ParamUtil.getLong(request, "moduleId", 0);

	PortletPreferences preferences = null;
	String portletResource = ParamUtil.getString(request, "portletResource");
	if (Validator.isNotNull(portletResource)) 
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	else
		preferences = renderRequest.getPreferences();
	String viewMode = preferences.getValue("viewMode", "0");
	
	boolean completeMode = viewMode.compareTo("0") == 0;
	boolean actualModuleMode = viewMode.compareTo("1") == 0;
	
	if(actualModuleMode && (moduleId>0 || ModuleLocalServiceUtil.countInGroup(themeDisplay.getScopeGroupId())!=0)){
		if(moduleId==0) {
			moduleId = ModuleLocalServiceUtil.findFirstInGroup(themeDisplay.getScopeGroupId()).getModuleId();
		}
%>
		<div class="lms-desplegable" style="overflow: hidden;" >
			<jsp:include page="/html/lmsactivitieslist/viewactivities.jsp"></jsp:include>
		</div>
<%
	}else{
%>
		<jsp:include page="/html/lmsactivitieslist/viewComplete.jsp"></jsp:include>
<%	} %>
	
<script type="text/javascript">

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
			else if(html5Event.data.name=='closeActivity'){
				A.DialogManager.closeByChild('#editlesson');
			}
			else if(html5Event.data.name=='setTitleActivity'){
				A.DialogManager.findByChild('#editlesson').set('title',html5Event.data.title);
			}
			else if((html5Event.data.name=='resizeWidthActivity')&&(!!html5Event.data.width)){
				var editLessonDiv = A.one('#editlesson');
				if((editLessonDiv!=null)&&(editLessonDiv.one('iframe.aui-resizeiframe-monitored-height')!=null)) {
					editLessonDiv.setStyle('width',html5Event.data.width);
				}
			}

		});

});
function updateActivityList()
{
	Liferay.Portlet.refresh('#p_p_id<portlet:namespace />');
}
</script>
