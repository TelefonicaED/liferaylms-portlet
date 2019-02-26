<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@ include file="/init.jsp" %>
<%

LearningActivityResult result = null;
LearningActivity activity = null;
int curValue = ParamUtil.get(renderRequest, "curValue", 0);

if((renderRequest.getParameter("actId")!=null)&&(renderRequest.getParameter("studentId")!=null))
{
	result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(ParamUtil.getLong(renderRequest,"actId"), ParamUtil.getLong(renderRequest,"studentId"));
	activity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(renderRequest,"actId"));
}

String gradeFilter = ParamUtil.getString(renderRequest, "gradeFilter");
String criteria = ParamUtil.getString(renderRequest, "criteria");

CalificationType ct = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId()).getCalificationType());

%>

<portlet:actionURL name="setGrades" var="updateGradesURL" windowState="<%= LiferayWindowState.NORMAL.toString() %>">
	<portlet:param name="gradeFilter" value="<%=gradeFilter %>" />
	<portlet:param name="criteria" value="<%=criteria %>" />
	<portlet:param name="curValue" value="<%=String.valueOf(curValue) %>" />
</portlet:actionURL>

<aui:form  name="fn_grades" method="post" action="${updateGradesURL}"  role="form">
	<aui:fieldset>
		<aui:input type="hidden" name="studentId" value='<%=renderRequest.getParameter("studentId") %>' />
		
	    <aui:input type="text" name="result" helpMessage="<%=LanguageUtil.format(pageContext, \"offlinetaskactivity.grades.resultMessage\", new Object[]{ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), activity.getPasspuntuation())})%>" label="offlinetaskactivity.grades" value='<%=(result!=null && result.getEndDate()!=null)?ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), result.getResult()):"" %>'>
	    
	    	<aui:validator name="number"></aui:validator>
	    	<aui:validator  name="custom"  errorMessage="<%=LanguageUtil.format(themeDisplay.getLocale(), \"result.must-be-between\", new Object[]{ct.getMinValue(themeDisplay.getScopeGroupId()),ct.getMaxValue(themeDisplay.getScopeGroupId())})%>"  >
				function (val, fieldNode, ruleValue) {
					var result = false;
					if (val >= <%=ct.getMinValue(themeDisplay.getScopeGroupId()) %> && val <= <%= ct.getMaxValue(themeDisplay.getScopeGroupId()) %>) {
						result = true;
					}
					return result;					
				}
			</aui:validator>
	    
	    </aui:input>

		<aui:input type="textarea"  helpMessage="<%=LanguageUtil.get(pageContext, \"offlinetaskactivity.grades.commentsMessage\")%>"  maxLength="1000" rows="3" name="comments" label="offlinetaskactivity.comments" value='<%=((result!=null)&&(result.getComments()!=null))?result.getComments():"" %>'>
			<aui:validator name="range">[0, 1000]</aui:validator>
		</aui:input>
	</aui:fieldset>
	
	
	<aui:button-row>
		<aui:button name="Save" value="save" type="submit"/>
		<aui:button name="Close" value="cancel" onclick="${renderResponse.getNamespace()}doClosePopupGrades();" type="button" />
	</aui:button-row>	

</aui:form>
