<%@page import="java.util.Locale"%>
<%@page import="com.liferay.portal.kernel.util.LocalizationUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.portal.security.permission.PermissionCheckerFactoryUtil"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.portal.model.PublicRenderParameter"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="java.net.URL"%>
<%@page import="com.liferay.portlet.PortletQNameUtil"%>
<%@page import="javax.portlet.RenderResponse"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@page import="com.liferay.lms.LmsActivitiesList"%>
<%@page import="com.liferay.lms.asset.LearningActivityAssetRendererFactory"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.liferay.portlet.asset.model.AssetCategory"%>
<%@page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil"%>
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
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>

<%@ include file="/init.jsp"%>


<%
long moduleId = ParamUtil.getLong(request, "moduleId", 0);
boolean actionEditing = ParamUtil.getBoolean(request, "actionEditing", false);
long actId = ParamUtil.getLong(request, "actId", 0);

NumberFormat resultNumberFormat = NumberFormat.getInstance(locale);
resultNumberFormat.setMinimumIntegerDigits(1);

LearningActivity currentLeaningActivity = null;

if(actId!=0) {
	currentLeaningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
}

Course coursetmp = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());

java.util.List<LearningActivity> activities = null;
if (moduleId == 0) {
	java.util.List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
	if (modules.size() > 0) {
		Module theModule = modules.get(0);
		moduleId = theModule.getModuleId();
	}
}
if (moduleId == 0) {
	activities = LearningActivityServiceUtil.getLearningActivitiesOfGroup(scopeGroupId);
} else {
	Module theModule =ModuleLocalServiceUtil.getModule(moduleId);
	if(!permissionChecker.hasPermission(
			themeDisplay.getScopeGroupId(),
			Module.class.getName(), moduleId,
			"ADD_LACT")&& ModuleLocalServiceUtil.isLocked(theModule.getPrimaryKey(),themeDisplay.getUserId()) &&
			!permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model", themeDisplay.getScopeGroupId() , "ACCESSLOCK")){
		//renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		activities=new ArrayList<LearningActivity>(); 
	}else{
		activities = LearningActivityServiceUtil.getLearningActivitiesOfModule(moduleId);
	}
}


String activityEnd = "desactivado";
Hashtable<AssetCategory, java.util.List<LearningActivity>> catler = new Hashtable<AssetCategory, java.util.List<LearningActivity>>();
for (LearningActivity activity : activities) {
	java.util.List<AssetCategory> categorias = AssetCategoryLocalServiceUtil.getCategories(LearningActivity.class.getName(), activity.getActId());
	for (AssetCategory categoria : categorias) {
		if (!catler.containsKey(categoria)) {
			catler.put(categoria, new ArrayList());
		}
		catler.get(categoria).add(activity);
	}
}
if ((actionEditing
		&& permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),Module.class.getName(), moduleId,	"ADD_LACT"))||
		(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.learningactivitymodel", themeDisplay.getScopeGroupId(), "ADD_ACTIVITY")&&
				moduleId==0)) {
	%>


<liferay-portlet:actionURL name="moveActivity" var="moveActivityURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString()%>" />

<script type="text/javascript">

Liferay.provide(
        window,
        '<portlet:namespace />refreshPortlet',
        function() {
		     <%-- refreshing the portlet [Liferay.Util.getOpener().] --%>
            var curPortletBoundaryId = '#p_p_id<portlet:namespace />';

            Liferay.Portlet.refresh(curPortletBoundaryId);
        },
        ['aui-dialog','aui-dialog-iframe']
    );
    
    
var ismobile=navigator.userAgent.match(/(iPad)|(iPhone)|(iPod)|(android)|(webOS)/i);

if(!ismobile){
AUI().ready('node','aui-io-request','aui-parse-content','aui-sortable',function(A) {

	new A.Sortable(
		{
			container: A.one('#myActivities'),
		    nodes: 'li',
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

					A.io.request('<%=moveActivityURL %>', {  
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

<liferay-portlet:renderURL var="newactivityURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<liferay-portlet:param name="jspPage" value="/html/lmsactivitieslist/newactivity.jsp"/>
	<liferay-portlet:param name="resModuleId" value="<%=Long.toString(moduleId) %>" />
</liferay-portlet:renderURL>
	<%

	String portletnamespace = renderResponse.getNamespace();
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
			"			uri: '" + JavaScriptUtil.markupToStringLiteral(newactivityURL) + "', "+
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
	<liferay-ui:icon image="add" label="<%=true%>" message="activity.creation"
		url="#" cssClass="newactivity" onClick="<%=newactivitypopup %>"/>
	
<%
}
%>
<liferay-ui:error></liferay-ui:error>
<ul id="myActivities">
			<%
			for (LearningActivity activity : activities) {
				String title = activity.getTitle(themeDisplay.getLocale());				
				String moduletitle = "";
				if (activity.getModuleId() != 0) {
					Module theModule = ModuleLocalServiceUtil
							.getModule(activity.getModuleId());
					moduletitle = theModule.getTitle();
				}
				long result = 0;
				String status = "not-started";
				if (LearningActivityResultLocalServiceUtil
						.existsLearningActivityResult(activity.getActId(),
								themeDisplay.getUserId())) {
					status = "started";

					LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil
							.getByActIdAndUserId(activity.getActId(),
									themeDisplay.getUserId());
					result = learningActivityResult.getResult();
					long tries = activity.getTries();
					long userTried = Long.valueOf(LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(activity.getActId(),themeDisplay.getUserId()));
					if (!actionEditing) {
						if (learningActivityResult.isPassed()) {
							status = "passed";
						}
						else if ((userTried >= tries && tries > 0) && (!learningActivityResult.isPassed())) {
							status = "failed";
						}
					}
				}
				if (actId == activity.getActId()) {
						activityEnd = "activado";
				} else {
					activityEnd = "desactivado";
				}
				String editing="";
				if(actionEditing)
				{
					editing="editing";
				}
				
				LearningActivityType learningActivityType = learningActivityTypeRegistry.getLearningActivityType(activity.getTypeId());
				
				if (permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),	activity.getActId(), ActionKeys.VIEW)){
					
					if((Validator.isNotNull(learningActivityType))&&
						(!LearningActivityLocalServiceUtil.islocked(activity.getActId(),themeDisplay.getUserId())
							|| permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model", themeDisplay.getScopeGroupId() , "ACCESSLOCK") 
							||(permissionChecker.hasPermission(activity.getGroupId(), LearningActivity.class.getName(), activity.getActId(), ActionKeys.UPDATE) && actionEditing)))
					{
						%>
						<portlet:actionURL var="goToActivity" windowState="<%= WindowState.NORMAL.toString()%>" >
							<portlet:param name="actId" value="<%=Long.toString(activity.getActId()) %>" />
						</portlet:actionURL>

						<li class="learningActivity <%=activityEnd%> <%=editing %> <%=status%>"  <%=(status=="passed")?"title =\""+LanguageUtil.format(pageContext, "activity.result",new Object[]{resultNumberFormat.format(result)})+"\"":(status=="failed")?"title =\""+LanguageUtil.format(pageContext, "activity.result",new Object[]{resultNumberFormat.format(result)})+"\"":StringPool.BLANK %> 
							id="<portlet:namespace/><%=activity.getActId()%>">
							<a href="<%=assetRendererFactory.getAssetRenderer(activity.getActId()).
									getURLView(liferayPortletResponse, WindowState.NORMAL) %>"  ><%=title%></a>
							
					<%
					}
					else
					{
					%>
						<li class="learningActivity <%=activityEnd%> <%=editing %> <%=status%> locked"  <%=(status=="passed")?"title =\""+LanguageUtil.format(pageContext, "activity.result",new Object[]{resultNumberFormat.format(result)})+"\"":(status=="failed")?"title =\""+LanguageUtil.format(pageContext, "activity.result",new Object[]{resultNumberFormat.format(result)})+"\"":StringPool.BLANK %> 
							id="<portlet:namespace/><%=activity.getActId()%>">
							<span><%=title%></span>
					<%
					}
				if ((actionEditing)&&(Validator.isNotNull(learningActivityType))
					&& (permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(), ActionKeys.UPDATE)
						|| permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(), ActionKeys.DELETE) 
						|| permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(),ActionKeys.PERMISSIONS)
						|| permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),activity.getActId(),"SOFT_PERMISSIONS")
						|| permissionChecker.hasOwnerPermission(activity.getCompanyId(),LearningActivity.class.getName(),activity.getActId(),activity.getUserId(), ActionKeys.UPDATE)
						|| permissionChecker.hasOwnerPermission(activity.getCompanyId(),LearningActivity.class.getName(),activity.getActId(),activity.getUserId(), ActionKeys.DELETE))) {
				%>
				<div class="iconsedit"><%@ include file="/html/lmsactivitieslist/admin_actions.jspf" %></div>
				
				<%
				}
				%>
				</li>
			<%
			}}
			%>
</ul>