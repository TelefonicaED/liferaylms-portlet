<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.PortletConstants"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil" %>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>

<%@ include file="/init.jsp"%>

<liferay-ui:success key="ok-deleteActivity" message="activity-deleted-successfully" />
<liferay-ui:success key="ok-delete-module" message="module-deleted-successfully" />
<liferay-ui:success key="ok-deleting-tries" message="activity-tries-deleting-successfully" />


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
	
	Module currentModule = null;
	
	if (moduleId == 0){
		currentModule = ModuleLocalServiceUtil.findFirstInGroup(themeDisplay.getScopeGroupId());
		if(currentModule != null){
			moduleId = currentModule.getModuleId();
		}
	}
	
	boolean moduleActuallyIsLocked = true;
	if(moduleId > 0){
		if(currentModule == null){
			currentModule = ModuleLocalServiceUtil.getModule(moduleId);
		}
		if(currentModule != null){
			moduleActuallyIsLocked = currentModule.isLocked(themeDisplay.getUserId());
		}
	}
	
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());

	boolean showChangeVisibility = LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId()).getShowHideActivity();
	boolean showChangeAllVisibility = PrefsPropsUtil.getBoolean("learningactivity.show.hideallactivity", false);
	boolean visibleCalifications = PrefsPropsUtil.getBoolean("learningactivity.show.califications", true);
	boolean activityStatus = PrefsPropsUtil.getBoolean("learningactivity.show.status", false);
	
	long actId = ParamUtil.getLong(request, "actId", 0);
	
	boolean actionEditing = ParamUtil.getBoolean(request, "actionEditing", false);
	boolean hasPermissionAddLact = permissionChecker.hasPermission( themeDisplay.getScopeGroupId(), Module.class.getName(), moduleId,"ADD_LACT");
	
	PortletURL newactivityURL = null;
	
	if ((actionEditing && hasPermissionAddLact) ||
		(moduleId==0 
		&& permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.learningactivitymodel", themeDisplay.getScopeGroupId(), "ADD_ACTIVITY"))) {
		
		newactivityURL = renderResponse.createRenderURL();
		newactivityURL.setWindowState(LiferayWindowState.POP_UP);
		newactivityURL.setParameter("jspPage", "/html/lmsactivitieslist/newactivity.jsp");
		newactivityURL.setParameter("resModuleId", Long.toString(moduleId));
		%>
 
		<%@ include file="/html/lmsactivitieslist/moveactivity.jsp"%>
		
	<%}%>
	
	<c:if test="<%=actualModuleMode %>">
	<%		
		NumberFormat resultNumberFormat = NumberFormat.getInstance(locale);
		resultNumberFormat.setMinimumIntegerDigits(1);
		
		LearningActivity currentLeaningActivity = null;

		if(actId!=0) {
			currentLeaningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
		}
		
		LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
		AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());

		boolean hasPermissionAccessCourseFinished = LiferaylmsUtil.hasPermissionAccessCourseFinished(themeDisplay.getCompanyId(), course.getGroupCreatedId(), course.getCourseId(), themeDisplay.getUserId());
		boolean accessLock = CourseLocalServiceUtil.canAccessLock(themeDisplay.getScopeGroupId(), themeDisplay.getUser());
		
		Module theModule = currentModule;
%>	
		<div class="lms-desplegable" style="overflow: hidden;" >	
			<%@ include file="/html/lmsactivitieslist/viewactivities.jsp"%>
		</div>
	</c:if>
	<c:if test="<%=completeMode %>"	>	
		<%@ include file="/html/lmsactivitieslist/viewComplete.jsp"%>
	</c:if>
	
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
