<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.CourseResult"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.ModuleResult"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<%
String returnurl=ParamUtil.getString(request,"returnurl","");
String title = null;
long userId=ParamUtil.getLong(request,"userId",0);
if(userId==0){
	userId=themeDisplay.getUserId();
}else{
	User usuario=UserLocalServiceUtil.getUser(userId);
	title = LanguageUtil.get(pageContext,"results") +" "+ usuario.getFullName();
}

boolean showExport = (renderRequest.getPreferences().getValue("showExport", "false")).compareTo("true") == 0;
Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());

CourseResult courseResult = CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(course.getCourseId(), userId);

String status = "not-started";
if(courseResult != null && courseResult.getStartDate() != null && courseResult.getPassedDate() == null){
	status = "started";
}else if(courseResult != null && courseResult.getPassedDate() != null && !courseResult.isPassed()){
	status = "not-passed";
}else if(courseResult != null && courseResult.getPassedDate() != null && courseResult.isPassed()){
	status = "passed";
}

status = LanguageUtil.get(themeDisplay.getLocale(), status);

String result = "result.pending";
if(courseResult != null && courseResult.getPassedDate() != null){
	CalificationTypeRegistry cr = new CalificationTypeRegistry();
	
	CalificationType calificationType = cr.getCalificationType(course.getCalificationType());
	
	if(calificationType!=null){
		result = calificationType.translate(themeDisplay.getLocale(), courseResult);
	}else{
		result = String.valueOf(courseResult.getResult());
	}
	
}else{
	result = LanguageUtil.get(themeDisplay.getLocale(), result);
}
%>

<c:if test="<%=title != null %>">
	<liferay-ui:header title="<%= title %>" backURL="<%=returnurl %>"></liferay-ui:header>
</c:if>
<%-- Exportar --%>
<c:if test="<%=showExport %>">
	<div class="aui-tab-back">
		<liferay-ui:icon-menu align="right" cssClass='lfr-toolbar-button add-button' direction="down" extended="<%= false %>"  message="export" showWhenSingleIcon="<%= false %>">
			<liferay-portlet:resourceURL var="exportCourseStatus" />
			<liferay-ui:icon image="export" url='<%=exportCourseStatus%>' label="dates"/>
		</liferay-ui:icon-menu>
	</div>
</c:if>

<div class="wrap-text">
    <span><strong><liferay-ui:message key="user-progress.calification"/></strong></span>
    <span><%=result %></span>
</div>
<div class="wrap-text">
    <span><strong><liferay-ui:message key="user-progress.status"/></strong></span>
    <span><%=status %></span>
</div>

<liferay-ui:panel-container >
	<%
	List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
	int fila = 0;
	for(Module theModule:modules){
		if(permissionChecker.hasPermission(theModule.getGroupId(),Module.class.getName(),theModule.getModuleId(), ActionKeys.VIEW)){ %>
			<liferay-ui:panel id="<%=Long.toString(theModule.getModuleId()) %>" 
				title="<%=theModule.getTitle(themeDisplay.getLocale()) %>" collapsible="true" extended="true" defaultState="<%=(fila==0)?\"open\":\"collapsed\" %>">
				<liferay-ui:search-container  emptyResultsMessage="there-are-no-results" delta="50" deltaConfigurable="false">
					<liferay-ui:search-container-results>
						<% 
						List<LearningActivity> activities=LearningActivityServiceUtil.getLearningActivitiesOfModule(theModule.getModuleId());
						List<LearningActivity> activitiesFiltered = new LinkedList<LearningActivity>();
						for(LearningActivity act : activities){
							if (permissionChecker.hasPermission(act.getGroupId(),LearningActivity.class.getName(),	act.getActId(), ActionKeys.VIEW))
								activitiesFiltered.add(act);
						}
						
						pageContext.setAttribute("results", activitiesFiltered);
					    pageContext.setAttribute("total", activitiesFiltered.size());
						%>
					</liferay-ui:search-container-results>
					<liferay-ui:search-container-row className="com.liferay.lms.model.LearningActivity" keyProperty="actId" modelVar="learningActivity">
						<%
						String score= "-";
						status="not-started";	
						String comments =" ";
						
						String divisor ="";
						if(LearningActivityResultLocalServiceUtil.existsLearningActivityResult(learningActivity.getActId(), userId)){
							status="started";
							LearningActivityResult learningActivityResult=LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), userId);
							score=(learningActivityResult!=null)?LearningActivityResultLocalServiceUtil.translateResult(themeDisplay.getLocale(), learningActivityResult.getResult(), learningActivity.getGroupId()):"";
							if(learningActivityResult!=null){
								divisor = LearningActivityResultLocalServiceUtil.getCalificationTypeSuffix(themeDisplay.getLocale(), learningActivityResult.getResult(), learningActivity.getGroupId());
							}
							comments=HtmlUtil.stripHtml(learningActivityResult.getComments());
							if(learningActivityResult.getEndDate()!=null){
									status="not-passed"	;
							}
							if(learningActivityResult.isPassed()){
								status="passed"	;
							}
						}
						%>
						<liferay-ui:search-container-column-text cssClass="number-column" name = "activity">
							<%=learningActivity.getTitle(themeDisplay.getLocale()) %>
						</liferay-ui:search-container-column-text>
						<liferay-ui:search-container-column-text cssClass="number-column" name = "result" align="center">
							<%=(score.trim().equalsIgnoreCase("-")) ? score:  score + divisor%>
						</liferay-ui:search-container-column-text>
						<liferay-ui:search-container-column-text cssClass="number-column" name = "status" align="center">
				
							<%if(status.equals("passed")){%>
								<liferay-ui:icon image="checked" message="passed"></liferay-ui:icon>
							<%}
							if(status.equals("not-passed")){%>
								<liferay-ui:icon image="close" message="not-passed"></liferay-ui:icon>
							<%}
							if(status.equals("started")){%>
								<liferay-ui:icon image="unchecked" message="unchecked"></liferay-ui:icon>
							<%}%>
						</liferay-ui:search-container-column-text>
						<liferay-ui:search-container-column-text cssClass="number-column" name = "comments">
							<%=HtmlUtil.escape(comments) %>
						</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>
					<liferay-ui:search-iterator></liferay-ui:search-iterator>
				</liferay-ui:search-container>
			</liferay-ui:panel>
			<%fila++;
		}
	}%>
</liferay-ui:panel-container>
