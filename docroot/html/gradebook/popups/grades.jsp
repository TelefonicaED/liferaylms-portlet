<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@ include file="/init.jsp" %>
<%

LearningActivityResult result = null;

String actId2 = renderRequest.getParameter("actId");
String studentId2 = renderRequest.getParameter("studentId");

if((actId2!=null)&&(studentId2!=null)){
	Long lactId2 = ParamUtil.getLong(renderRequest,"actId");
	Long lstudentId2 = ParamUtil.getLong(renderRequest,"studentId");
	result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(lactId2, lstudentId2);
}

CalificationType ct = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId()).getCalificationType());

%>

<portlet:actionURL name="setGrades" var="updateGradesURL" windowState="<%= LiferayWindowState.NORMAL.toString() %>" />

<aui:form  name="fn_grades" method="post" action="${updateGradesURL}" role="form">
	<aui:fieldset>
		<aui:input type="hidden" name="studentId" value='<%=studentId2%>' />
		<aui:input type="hidden" name="actId" value='<%=actId2%>' />
	    <aui:input type="text" name="result" label="offlinetaskactivity.grades" value='<%=result!=null?ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(),result.getResult()):"" %>'>
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
		<aui:input type="textarea" cols="40" rows="2" name="comments" label="offlinetaskactivity.comments" value='<%=((result!=null)&&(result.getComments()!=null))?result.getComments():"" %>'/>
	</aui:fieldset>
	<aui:button-row>
		<aui:button name="Save" value="save" type="submit"/>
		<aui:button name="Close" value="cancel" onclick="${renderResponse.getNamespace()}doClosePopupGrades();" type="button" />
	</aui:button-row>	
</aui:form>