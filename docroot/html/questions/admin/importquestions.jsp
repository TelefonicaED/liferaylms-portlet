<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@ include file="/init.jsp" %>
<%
	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
	request.setAttribute("activity", learningActivity);
	PortletURL backUrl = renderResponse.createRenderURL();
	backUrl.setParameter("resId", String.valueOf(learningActivity.getActId()));
	backUrl.setParameter("jspPage", "/html/questions/admin/editquestions.jsp");
	backUrl.setParameter("actionEditingDetails",StringPool.TRUE);
	request.setAttribute("backUrl", backUrl.toString());
%>
<liferay-util:include page="/html/questions/admin/editHeader.jsp" servletContext="<%=this.getServletContext() %>" />
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
<portlet:actionURL name="importQuestions" var="importQuestionsURL">
	<portlet:param name="resId" value="<%=String.valueOf(learnact.getActId()) %>" />
	<portlet:param name="actionEditingDetails" value="<%=StringPool.TRUE %>" />	
	<portlet:param name="jspPage" value="/html/questions/admin/editquestions.jsp"></portlet:param>
</portlet:actionURL>
<aui:form name="fm" action="<%=importQuestionsURL%>"  method="post" enctype="multipart/form-data">
	<aui:fieldset>
		<aui:field-wrapper label="file" helpMessage="execativity.editquestions.importquestions.fileHelp">
	    	<aui:input inlineLabel="left" inlineField="true"
					  	name="fileName" label="" id="fileName" type="file" value="" />
		</aui:field-wrapper>
	</aui:fieldset>
	<aui:button-row>
		<aui:button type="submit" value="upload"></aui:button>
		<liferay-util:include page="/html/questions/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
	</aui:button-row>
</aui:form>

<liferay-ui:error key="execativity.editquestions.importquestions.xml.fileRequired" message="execativity.editquestions.importquestions.xml.fileRequired" />
<liferay-ui:error key="execativity.editquestions.importquestions.xml.badFormat" message="execativity.editquestions.importquestions.xml.badFormat" />
<liferay-ui:error key="execativity.editquestions.importquestions.xml.parseXML" message="execativity.editquestions.importquestions.xml.parseXML" />
<liferay-ui:error key="execativity.editquestions.importquestions.xml.generic" message="execativity.editquestions.importquestions.xml.generic" />
<liferay-ui:error key="execativity.editquestions.importquestions.xml.not.allowed" message="execativity.editquestions.importquestions.xml.not.allowed" />
<% if(SessionErrors.contains(renderRequest, "execativity.editquestions.importquestions.xml.parseXMLLine")) { %>
<div class="portlet-msg-error">
	<%=LanguageUtil.format(pageContext, "execativity.editquestions.importquestions.xml.parseXMLLine", SessionErrors.get(renderRequest, "execativity.editquestions.importquestions.xml.parseXMLLine"),false) %>
</div>
<% } %>

