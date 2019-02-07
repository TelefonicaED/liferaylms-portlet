<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@ include file="/init.jsp" %>


<%

LearningActivityResult result = null;
LearningActivity activity = null;
String actId2 = renderRequest.getParameter("actId");
String studentId2 = renderRequest.getParameter("studentId");
if((actId2!=null)&&(studentId2!=null)){
	Long lactId2 = ParamUtil.getLong(renderRequest,"actId");
	activity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(renderRequest,"actId"));
	Long lstudentId2 = ParamUtil.getLong(renderRequest,"studentId");
	result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(lactId2, lstudentId2);
}


String firstName = ParamUtil.getString(renderRequest, "first-name");
String lastName = ParamUtil.getString(renderRequest, "last-name");
String screenName = ParamUtil.getString(renderRequest, "screen-name");
String emailAddress = ParamUtil.getString(renderRequest, "email-address");


CalificationType ct = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId()).getCalificationType());

%>

<portlet:actionURL var="updateGradesURL" name="setGrades" windowState="<%= LiferayWindowState.NORMAL.toString() %>"> 
	<portlet:param name="first-name" value="<%=firstName %>" />
	<portlet:param name="last-name" value="<%=lastName %>" />
	<portlet:param name="screen-name" value="<%=screenName %>" />
	<portlet:param name="email-address" value="<%=emailAddress %>" />    
</portlet:actionURL>

<aui:form action="<%=updateGradesURL %>" name="fn_grades" method="post" role="form">
	<aui:fieldset>
		<aui:input type="hidden" name="studentId" value='<%=studentId2%>' />
		<aui:input type="hidden" name="actId" value='<%=actId2%>' />
	    <aui:input type="text" name="result" label="offlinetaskactivity.grades" helpMessage="<%=LanguageUtil.format(pageContext, \"offlinetaskactivity.grades.resultMessage\", new Object[]{ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), activity.getPasspuntuation())})%>" value='<%=result!=null?ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), result.getResult()):"" %>' >
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
		<aui:input type="textarea" cols="40" rows="2" name="comments" helpMessage="<%=LanguageUtil.get(pageContext, \"onlinetaskactivity.grades.commentsMessage\")%>" maxLength="350" label="offlinetaskactivity.comments" value='<%=((result!=null)&&(result.getComments()!=null))?result.getComments():"" %>'/>
	</aui:fieldset>
	
	<aui:button-row>
		<aui:button type="submit" name="saveGrade" value="save"></aui:button>
		<aui:button name="Close" value="cancel" onclick="${renderResponse.getNamespace()}doClosePopupGrades();" type="button" />
	</aui:button-row>

</aui:form>