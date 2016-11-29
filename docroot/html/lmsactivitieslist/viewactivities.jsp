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
		
		String newactivitypopup = "javascript:AUI().use('aui-dialog','aui-dialog-iframe', "+
			"	function(A){ "+
			"	new A.Dialog( "+
			"		{ "+
			"    		id: 'editlesson', "+ 
			"			title: '"+LanguageUtil.get(pageContext,"activity.creation")+"', "+
		    "			destroyOnClose: true, "+
		    "			width: 750, "+
		    "			modal:true, "+
		    "			x:50, "+
		    "			y:50, "+
		    "			on: { "+
			"    			close: function(evt){ "+
			"					Liferay.Portlet.refresh(A.one('#p_p_id"+renderResponse.getNamespace()+"')); "+		
			"				} "+
			"			} "+
			"		} "+
			"	).plug( "+
			"		A.Plugin.DialogIframe, "+
			"		{ "+
			"			uri: '" + JavaScriptUtil.markupToStringLiteral(newactivityURL.toString()) + "', "+
			"			on: { "+
			"    			load: function(evt){ "+
			"					var instance = evt.target; "+
			"					var iframe = instance.node; "+
			"					var	iframeDocument = iframe.get('contentWindow.document') || iframe.get('contentDocument'); "+
			"					var	iframeBody = iframeDocument.one('body'); "+	
			"					iframeBody.delegate( "+	
			"						'click', "+	
			"						function() { "+	
			"							iframeDocument.purge(true); "+	
			"							instance.get('host').close(); "+	
			"						}, "+	
			"						'.aui-button-input-cancel' "+	
			"					); "+	
			"				} "+
			"			} "+
			"		} "+
			"	).render().show(); "+
			"});";
		%>


		
		<liferay-ui:icon image="add" label="<%=true%>" message="activity.creation" url="#" cssClass="newactivity" onClick="<%=newactivitypopup %>"/>	
	<%
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

				<li class="learningActivity <%=activityEnd%> <%=editing %> <%=status%>"  <%=(status=="passed" || status=="failed")?"title =\""+LanguageUtil.format(pageContext, "activity.result",new Object[]{resultNumberFormat.format(result)})+"\"":StringPool.BLANK %> 
					id="<portlet:namespace/><%=activity.getActId()%>">
					<span class="type_<%=type%>"></span>
					<a href="<%=assetRendererFactory.getAssetRenderer(activity.getActId()).
							getURLView(liferayPortletResponse, WindowState.NORMAL) %>"  ><%=title%></a>
					
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
				if(hasPermissionUpdate || hasPermissionDelete || hasPermissionPermissions || hasPermissionSoftPermissions) {
			%>
				<div class="iconsedit"><%@ include file="/html/lmsactivitieslist/admin_actions.jspf" %></div>
				<%}
			}%>
			</li>
		<%}
	}
	%>
</ul>