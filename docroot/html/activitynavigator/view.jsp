<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.ResourceConstants"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.service.ResourcePermissionLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>

<%@ include file="/init.jsp" %>

<%
	long moduleId			= ParamUtil.getLong(request,"moduleId",0);
	long actId				= ParamUtil.getLong(request,"actId",0);
	boolean actionEditing	= ParamUtil.getBoolean(request,"actionEditing",false);
	
	Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());

	java.util.List<LearningActivity> activities = new java.util.ArrayList<LearningActivity>();
	
	if(moduleId == 0){
		
		if(actId != 0){
			LearningActivity larn = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			moduleId = larn.getModuleId();
		}
	}
	
	if(moduleId != 0)
	{
		Module theModule=ModuleLocalServiceUtil.getModule(moduleId);
	
		if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),	Module.class.getName(), moduleId, "ADD_LACT")
				|| !ModuleLocalServiceUtil.isLocked(theModule.getPrimaryKey(),themeDisplay.getUserId())){
			
			activities = LearningActivityServiceUtil.getLearningActivitiesOfModule(moduleId);
		}
	}
	
	if(ModuleLocalServiceUtil.isUserPassed(moduleId,themeDisplay.getUserId()))
	{%>
		<div id="modulegreetings">
	<%
			String titleAct = ModuleLocalServiceUtil.getModule(moduleId).getTitle(themeDisplay.getLocale());
			if (titleAct.length() > 13){
				titleAct= StringUtil.shorten(titleAct,13);
			}
			titleAct="\""+titleAct+"\"";
	%>
			<liferay-ui:message key="module-finissed-greetings-simple" />
		</div>		
	<%
	}

	LearningActivity prevActivity=null;
	boolean writeNext=false;
	
	Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
	
	for(LearningActivity activity:activities){
		
		boolean visible = ResourcePermissionLocalServiceUtil.hasResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
				ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(activity.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW);
		
		boolean isLocked = LearningActivityLocalServiceUtil.islocked(activity.getActId(),themeDisplay.getUserId());
		boolean accessLock = permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK");
		//boolean update = permissionChecker.hasPermission(activity.getGroupId(), LearningActivity.class.getName(), activity.getActId(), ActionKeys.UPDATE);
		//boolean view = permissionChecker.hasPermission(activity.getGroupId(), LearningActivity.class.getName(), activity.getActId(), ActionKeys.VIEW);
		
		boolean showActivity = ( !isLocked || accessLock ) && visible ;
		
		
					
		//Cuando entramos en la primera actividad desde el módulo y se puede ver.
		if(actId == 0 && showActivity){
			%>
				<portlet:actionURL name="viewactivity" var="viewstartURL">
					<portlet:param name="actId" value="<%=Long.toString(activity.getActId()) %>" />
				</portlet:actionURL>
				<div id="startactivity"><a href="<%=viewstartURL.toString()%>"><liferay-ui:message key="activityNavigator.start" /></a></div>
			<%
			break;
		}
		
		if(writeNext){

			if(showActivity){
				%>
					<portlet:actionURL name="viewactivity" var="viewnextURL">
						<portlet:param name="actId" value="<%=Long.toString(activity.getActId()) %>" />
					</portlet:actionURL>
					<div id="nextactivity"><a href="<%=viewnextURL.toString()%>"><liferay-ui:message key="activityNavigator.next" /></a></div>
				<%
				//Tenemos la anterior y la siguiente, no necesitamos buscar más.
				break;
			}
			
		}
		else
		{
			//Si la actividad actual es la primera, activamos calculo de la siguiente.
			if(activity.getActId() == actId){
				
				writeNext = true;
				
				//Tenemos una anterior mostrable.
				if(prevActivity != null){
					%>
						<portlet:actionURL name="viewactivity" var="viewURL">
							<portlet:param name="actId" value="<%=Long.toString(prevActivity.getActId()) %>" />
						</portlet:actionURL>
						<div id="previusactivity"><a href="<%=viewURL.toString()%>"><liferay-ui:message key="activityNavigator.prev" /></a></div>
					<%
				}			
			}
		}
		
		//Si se puede ver, la ponemos como la anterior.
		if(showActivity){
			prevActivity = activity;
		}
		
	}// fin for activities

%>
<script type="text/javascript">
function updateActivityNavigation()
{
	Liferay.Portlet.refresh('#p_p_id<portlet:namespace />');
}
</script>