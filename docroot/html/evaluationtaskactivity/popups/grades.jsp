<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionMessages"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEval"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.CourseResult"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@ include file="/init.jsp" %>

<script type="text/javascript">

function <portlet:namespace />doClosePopupGrades()
{
    AUI().use('aui-dialog', function(A) {
    	A.DialogManager.closeByChild('#<portlet:namespace />showPopupGrades');
    });
}

</script>

<%
long actId = ParamUtil.getLong(request,"actId",0);
long userId = ParamUtil.getLong(renderRequest, "userId");
LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);

CalificationType ct = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId()).getCalificationType());

String resultHelpMessage=LanguageUtil.format(pageContext, "evaluationtaskactivity.grades.resultMessage", new Object[]{ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), result.getResult())+ct.getSuffix(themeDisplay.getScopeGroupId())});
String gradeFilter = ParamUtil.getString(renderRequest, "gradeFilter");
String criteria = ParamUtil.getString(renderRequest, "criteria");
%>

<portlet:actionURL name="setGrades" var="updateGradesURL" windowState="<%= LiferayWindowState.NORMAL.toString() %>">
	<portlet:param name="gradeFilter" value="<%=gradeFilter %>" />
	<portlet:param name="criteria" value="<%=criteria %>" />
</portlet:actionURL>

<aui:form  name="fn_grades" method="post"  action="${updateGradesURL}" role="form">
	<aui:fieldset>
		<h1><%=UserLocalServiceUtil.getUser(userId).getFullName() %></h1>
		<aui:input type="hidden" name="studentId" value='<%=userId %>' />
	    <aui:input type="text" name="result" size="3" helpMessage="<%=resultHelpMessage %>" label="evaluationtaskactivity.grades" value='<%=ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(),result.getResult()) %>'>
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

	    <liferay-ui:message key="evaluationtaskactivity.result.percent"  arguments="<%=new Object[]{ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(),learningActivity.getPasspuntuation())} %>" />

		<aui:input type="textarea"  helpMessage="<%=LanguageUtil.get(pageContext, \"evaluationtaskactivity.grades.commentsMessage\")%>"  maxLength="350" cols="70"  rows="3" name="comments" label="evaluationtaskactivity.comments" value='<%=(result.getComments()!=null)?result.getComments():"" %>'>
			<aui:validator name="range">[0, 350]</aui:validator>
		</aui:input>
		<liferay-ui:message key="evaluationtaskactivity.comments.maxLength" />
	</aui:fieldset>
	
	<aui:button-row>
		<aui:button name="Save" value="save" type="submit"/>
		<aui:button name="Close" value="cancel" onclick="${renderResponse.getNamespace()}doClosePopupGrades();" type="button" />
	</aui:button-row>	
</aui:form>

<div id="<portlet:namespace />evaluationResult" class="<%=(SessionErrors.contains(renderRequest, "evaluationtaskactivity.grades.bad-updating"))?
									   "portlet-msg-error":((SessionMessages.contains(renderRequest, "evaluationtaskactivity.grades.updating"))?
									   "portlet-msg-success":StringPool.BLANK) %>">
	<%=(SessionErrors.contains(renderRequest, "evaluationtaskactivity.grades.bad-updating"))?LanguageUtil.get(pageContext,"evaluationtaskactivity.grades.bad-updating"):
	    ((SessionMessages.contains(renderRequest, "evaluationtaskactivity.grades.updating"))?LanguageUtil.get(pageContext,"evaluationtaskactivity.grades.updating"):StringPool.BLANK) %>
</div>
