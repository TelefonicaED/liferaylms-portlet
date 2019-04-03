<%@page import="com.liferay.lms.learningactivity.courseeval.PonderatedCourseEval"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<%
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
if (course==null || !themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(),
		"com.liferay.lms.model", themeDisplay.getScopeGroupId(), "ADD_MODULE")
		||course.getCourseEvalId()!=3) 
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{
	java.util.List<Long> required=PonderatedCourseEval.getRequiredActivities(course);
	java.util.Map<Long,Long> weights=PonderatedCourseEval.getActivitiesWeight(course);
	long score=PonderatedCourseEval.getScore(course);
%>
	<portlet:actionURL var="savePonderationURL" name="savePonderation" />
	
<portlet:renderURL var="cancel" />
<aui:form name="fm" action="<%=savePonderationURL%>"  role="form" method="post">
	<aui:input size="5" name="passpuntuation" label="passpuntuation" type="text" value="<%=Long.toString(score) %>" >
		<aui:validator name="digits"></aui:validator>
	</aui:input>
	<liferay-ui:panel-container >
		<%
		java.util.List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
		for(Module theModule:modules){
		%>
			<liferay-ui:panel id="<%=Long.toString(theModule.getModuleId()) %>" title="<%=theModule.getTitle(themeDisplay.getLocale()) %>" collapsible="true" extended="true" defaultState="collapsed">
				<liferay-ui:search-container  emptyResultsMessage="there-are-no-results" delta="500" deltaConfigurable="false">
				<liferay-ui:search-container-results>
			<% 
				List<LearningActivity> allActivities=LearningActivityServiceUtil.getLearningActivitiesOfModule(theModule.getModuleId());
				List<LearningActivity> activities=new java.util.ArrayList<LearningActivity>();
				for(LearningActivity act:allActivities)
				{
					if(act.getWeightinmodule()>0)
					{
						activities.add(act);
					}
				}
				pageContext.setAttribute("results", activities);
			    pageContext.setAttribute("total", activities.size());
			    
			%>
			</liferay-ui:search-container-results>
			<liferay-ui:search-container-row className="com.liferay.lms.model.LearningActivity" keyProperty="actId" modelVar="learningActivity">
				<%
					String requiredname="required_"+learningActivity.getActId();
					String weightname="weight_"+learningActivity.getActId();
					String weight="";
					boolean requiredChecked=false;
					if(weights.containsKey(learningActivity.getActId()))
					{
						weight=weights.get(learningActivity.getActId()).toString();
					}
					if(required.contains(learningActivity.getActId()))
					{
						requiredChecked=true;
					}
				%>
				<liferay-ui:search-container-column-text cssClass="number-column" name = "activity">
					<%=learningActivity.getTitle(themeDisplay.getLocale()) %>
				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text cssClass="number-column" name = "ponderated.weight">
					<aui:input size="5" name="<%=weightname %>" value="<%=weight %>" label="">
						<aui:validator name="digits"></aui:validator>
					</aui:input>
				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text cssClass="number-column" name = "ponderated.mustpassed">
					<aui:input type="checkbox" name="<%=requiredname %>" label="" value="<%=requiredChecked%>" />
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>
			<liferay-ui:search-iterator></liferay-ui:search-iterator>
		</liferay-ui:search-container>
		
		</liferay-ui:panel>
		<%
		}%>
	</liferay-ui:panel-container>
	<aui:button-row>
		<aui:button type="submit"></aui:button>							
	</aui:button-row>
</aui:form>
	<%
	
}
%>