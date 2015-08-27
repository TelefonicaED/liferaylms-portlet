<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>

<%
ResultRow row =
(ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
TestQuestion question = (TestQuestion)row.getObject();
long groupId = themeDisplay.getLayout().getGroupId();
String name = TestQuestion.class.getName();
String primKey = String.valueOf(question.getQuestionId());
String actId = String.valueOf(question.getActId());

QuestionType qt =new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
%>

<liferay-portlet:renderURL var="editURL" portletName="surveyactivity_WAR_liferaylmsportlet">
	<liferay-portlet:param name="jspPage" value="/html/surveyactivity/admin/editquestion.jsp"/>
	<liferay-portlet:param name="questionId" value="<%=primKey %>" />
	<liferay-portlet:param name="resId" value="<%= actId %>" />
	<liferay-portlet:param name="actionEditingDetails" value="<%= StringPool.TRUE %>" />
	<liferay-portlet:param name="message" value="<%= LanguageUtil.get(themeDisplay.getLocale(), \"execativity.editquestions.editquestion\")%>" />
	<liferay-portlet:param name="typeId" value="<%=String.valueOf(qt.getTypeId()) %>" />
	<liferay-portlet:param name="backUrl" value="<%= currentURL %>"/>
</liferay-portlet:renderURL>
<liferay-ui:icon image="edit" message="edit" url="<%=editURL.toString() %>" />
<liferay-portlet:actionURL name="deletequestion" var="deleteURL" portletName="surveyactivity_WAR_liferaylmsportlet">
	<liferay-portlet:param name="questionId" value="<%= primKey %>" />
	<liferay-portlet:param name="resId" value="<%= actId %>" />
	<liferay-portlet:param name="backUrl" value="<%= currentURL %>"/>
</liferay-portlet:actionURL>
<liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />
