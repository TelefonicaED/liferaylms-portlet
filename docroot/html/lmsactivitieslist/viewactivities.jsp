<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.util.JavaScriptUtil"%> 

<% 
List<LearningActivity> activities = null;

if (moduleId == 0) {
	activities = LearningActivityServiceUtil.getLearningActivitiesOfGroup(scopeGroupId);
} else {
	
	if(!hasPermissionAddLact && moduleActuallyIsLocked && !accessLock && !hasPermissionAccessCourseFinished){
		activities=new ArrayList<LearningActivity>(); 
	}else{
		activities = LearningActivityServiceUtil.getLearningActivitiesOfModule(moduleId);
	}
}

String activityEnd = "desactivado";

if ((actionEditing && hasPermissionAddLact) ||
		(moduleId==0 
		&& permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.learningactivitymodel", themeDisplay.getScopeGroupId(), "ADD_ACTIVITY"))) {
	
	PortletURL urlCreateActivity = LmsActivitiesList.getURLCreateActivity(liferayPortletRequest, liferayPortletResponse, currentModule);

	if(Validator.isNotNull(urlCreateActivity)){
		
		%>
		<liferay-ui:icon image="add" message="activity.creation" label="true" cssClass="newactivity" url="<%=urlCreateActivity.toString()%>"/>
		<%
	}
}
%>
<liferay-ui:error></liferay-ui:error>

<ul id="myActivities">
	<%String status = null;
	long result = -1;
	long tries = -1;
	long userTried = -1;
	String type = null;
	String title = null;
	LearningActivityResult learningActivityResult = null;
	String editing=null;
	LearningActivityType learningActivityType = null;
	
	for (LearningActivity activity : activities) {
		title = activity.getTitle(themeDisplay.getLocale());				
		type= String.valueOf(activity.getTypeId());
		
		result = 0;
		status = "not-started";
		
		learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(activity.getActId(),themeDisplay.getUserId());
		
		if(learningActivityResult!=null){
			result = learningActivityResult.getResult();
			tries = activity.getTries();
			
			status="started";
			if(learningActivityResult.getEndDate()!=null){
				userTried = LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(activity.getActId(),themeDisplay.getUserId());
				if (learningActivityResult.isPassed()) {
					status = "passed";
				} else if ((userTried >= tries && tries > 0) && (!learningActivityResult.isPassed())) {
					status = "failed";
				}							
			}
		}				
		
		if (actId == activity.getActId()) {
				activityEnd = "activado";
		} else {
			activityEnd = "desactivado";
		}
		editing="";
		if(actionEditing) {
			editing="editing";
		}
		
		learningActivityType = learningActivityTypeRegistry.getLearningActivityType(activity.getTypeId());
		if (permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),	activity.getActId(), ActionKeys.VIEW)){

			if((Validator.isNotNull(learningActivityType))&&
				(accessLock || hasPermissionAccessCourseFinished || !activity.isLocked(themeDisplay.getUser(), themeDisplay.getPermissionChecker())  
					||(permissionChecker.hasPermission(activity.getGroupId(), LearningActivity.class.getName(), activity.getActId(), ActionKeys.UPDATE) && actionEditing))){%>

				<li class="learningActivity <%=activityEnd%> <%=editing %> <%=status%>"  
					<%=(status=="passed" || status=="failed")?"title =\""+
					LanguageUtil.format(pageContext, "activity.result",new Object[]{resultNumberFormat.format(result)})+"\"":StringPool.BLANK %> 
					id="<portlet:namespace/><%=activity.getActId()%>">
					<span class="type_<%=type%>"></span>
							
					<a href="<%=assetRendererFactory.getAssetRenderer(activity.getActId()).
							getURLView(liferayPortletResponse, WindowState.NORMAL).toString()%>"  ><%=title%></a>
					
			<%} else{ %>
				<li class="learningActivity <%=activityEnd%> <%=editing %> <%=status%> locked"  <%=(status=="passed"||status=="failed" )?"title =\""+LanguageUtil.format(pageContext, "activity.result",new Object[]{resultNumberFormat.format(result)})+"\"":StringPool.BLANK %> 
					id="<portlet:namespace/><%=activity.getActId()%>">
					<span class="type_<%=type%>"></span>
					<span><%=title%></span>
			<%}
		
			if(!actionEditing && activityStatus) { %>
			
				<span class="status"> <%=LanguageUtil.format(pageContext, status,new Object[]{})%></span>
				<%if(status=="passed"||status=="failed" ){%>	
					<span class="result"> <%=result%> %</span>
				<%}
			}
			if (actionEditing&&Validator.isNotNull(learningActivityType)){
				boolean hasPermissionUpdate = permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(), ActionKeys.UPDATE)
												|| permissionChecker.hasOwnerPermission(activity.getCompanyId(),LearningActivity.class.getName(),activity.getActId(),activity.getUserId(), ActionKeys.UPDATE);
				boolean hasPermissionDelete = permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(), ActionKeys.DELETE) 
												|| permissionChecker.hasOwnerPermission(activity.getCompanyId(),LearningActivity.class.getName(),activity.getActId(),activity.getUserId(), ActionKeys.DELETE);
				boolean hasPermissionPermissions = permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(),ActionKeys.PERMISSIONS);
				boolean hasPermissionSoftPermissions = permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(),"SOFT_PERMISSIONS");
				boolean hasPermissionViewResults = permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(), "VIEW_RESULTS");
				
				boolean hasPermissionChangeAllVisibility = permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(),"CHANGE_ALL_VISIBILITY");
				boolean hasPermissionChangeVisibility = permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(),"CHANGE_VISIBILITY");
				if(hasPermissionUpdate || hasPermissionDelete || hasPermissionPermissions || hasPermissionSoftPermissions || hasPermissionViewResults || hasPermissionChangeAllVisibility
						|| hasPermissionChangeVisibility) {
			%>
				<div class="iconsedit"><%@ include file="/html/lmsactivitieslist/admin_actions.jspf" %></div>
				<%}
			}%>
			</li>
		<%}
	}
	%>
</ul>