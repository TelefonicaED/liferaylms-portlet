<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.ClpSerializer"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@ include file="/init.jsp" %>
<%
	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
	request.setAttribute("activity", learningActivity);
	PortletURL backUrl = renderResponse.createRenderURL();
	backUrl.setParameter("resId", String.valueOf(learningActivity.getActId()));
	backUrl.setParameter("jspPage", "/html/surveyactivity/admin/editquestions.jsp");
	backUrl.setParameter("actionEditingDetails",StringPool.TRUE);
	request.setAttribute("backUrl", backUrl.toString());
	String urlExample = "<a href=\"/"+ClpSerializer.getServletContextName()+"/html/surveyactivity/examples/document.csv\">"+LanguageUtil.get(themeDisplay.getLocale(),"example")+"</a>";

%>
<liferay-util:include page="/html/surveyactivity/admin/editHeader.jsp" servletContext="<%=this.getServletContext() %>" />
<%
LearningActivity learnact=null;
if(request.getAttribute("activity")!=null)
{
	
 learnact=(LearningActivity)request.getAttribute("activity");
}
else
{
	learnact=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
}
%>
<portlet:actionURL name="importSurveyQuestions" var="importQuestionsURL">
	<portlet:param name="resId" value="<%=String.valueOf(learnact.getActId()) %>" />
	<portlet:param name="actionEditingDetails" value="<%=StringPool.TRUE %>" />	
	<portlet:param name="jspPage" value="/html/surveyactivity/admin/editquestions.jsp"></portlet:param>
</portlet:actionURL>

<span>
	<%=LanguageUtil.get(pageContext,"surveyactivity.editquestions.importquestions.fileHelp") +"<br>"+ urlExample%>
</span>

<aui:form name="fm" action="<%=importQuestionsURL%>"  method="post" enctype="multipart/form-data">
	<aui:fieldset>
		<aui:field-wrapper label="file" helpMessage="surveyactivity.editquestions.importquestions.fileHelp">
	    			<aui:input inlineLabel="left" inlineField="true"
					  	name="fileName" label="" id="fileName" type="file" value="" />
				</aui:field-wrapper>
	</aui:fieldset>
	<aui:button-row>
		<aui:button type="submit" value="upload"></aui:button>
		<liferay-util:include page="/html/surveyactivity/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
	</aui:button-row>
</aui:form>

<liferay-ui:error key="surveyactivity.csvError.empty-file" message="surveyactivity.csvError.empty-file" />
<liferay-ui:error key="surveyactivity.csvError.bad-format" message="surveyactivity.csvError.bad-format" />
<!--
<liferay-ui:error key="surveyactivity.csvError.bad-question" message="surveyactivity.csvError.bad-question" />
<liferay-ui:error key="surveyactivity.csvError.bad-answer" message="surveyactivity.csvError.bad-answer" />
-->
<% if(SessionErrors.contains(renderRequest, "surveyactivity.csvError.bad-format-line")) { %>
<div class="portlet-msg-error">
	<%=LanguageUtil.format(pageContext, "surveyactivity.csvError.bad-format-line", SessionErrors.get(renderRequest, "surveyactivity.csvError.bad-format-line"),false) %>
</div>
<% } %>
<% if(SessionErrors.contains(renderRequest, "surveyactivity.csvError.bad-answer")) { %>
<div class="portlet-msg-error">
	<%=LanguageUtil.format(pageContext, "surveyactivity.csvError.bad-answer", SessionErrors.get(renderRequest, "surveyactivity.csvError.bad-answer"),false) %>
</div>
<% } %>
<% if(SessionErrors.contains(renderRequest, "surveyactivity.csvError.bad-question")) { %>
<div class="portlet-msg-error">
	<%=LanguageUtil.format(pageContext, "surveyactivity.csvError.bad-question", SessionErrors.get(renderRequest, "surveyactivity.csvError.bad-question"),false) %>
</div>
<% } %>

