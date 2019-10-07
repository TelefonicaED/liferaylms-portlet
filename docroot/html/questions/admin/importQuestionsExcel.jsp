<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.ClpSerializer"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@ include file="/init.jsp" %>
<%
	
	LearningActivity learnact=null;
	if(request.getAttribute("activity")!=null){
		
	 learnact=(LearningActivity)request.getAttribute("activity");
	}
	else{
		learnact=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
	}

	PortletURL backUrl = renderResponse.createRenderURL();
	backUrl.setParameter("resId", String.valueOf(learnact.getActId()));
	backUrl.setParameter("jspPage", "/html/questions/admin/editquestions.jsp");
	backUrl.setParameter("actionEditingDetails",StringPool.TRUE);
	
	String urlExample = "<a href=\"/"+ClpSerializer.getServletContextName()+"/html/questions/examples/ImportarPreguntas.xls\">"+LanguageUtil.get(themeDisplay.getLocale(),"example")+"</a>";

%>
<liferay-ui:header title="surveyactivity.editquestions.importquestions" backURL="<%=backUrl.toString() %>"></liferay-ui:header>
<%

%>
<portlet:actionURL name="importExcelQuestions" var="importQuestionsURL">
	<portlet:param name="resId" value="<%=String.valueOf(learnact.getActId()) %>" />
	<portlet:param name="actionEditingDetails" value="<%=StringPool.TRUE %>" />	
	<portlet:param name="jspPage" value="/html/questions/admin/editquestions.jsp"></portlet:param>
</portlet:actionURL>


<liferay-ui:panel id="importuserrole_help" title="help" extended="closed">
	<%=LanguageUtil.get(pageContext,"surveyactivity.editquestions.importquestions.fileHelp")%>
	
	<%
			List<QuestionType> qtypes = new QuestionTypeRegistry().getQuestionTypes();
			String[] allowedTypes = PropsUtil.getArray("lms.questions.allowed.for."+learnact.getTypeId());
		 	List<String> allowedTypesList = new ArrayList<String>();
			if (allowedTypes != null) {
				allowedTypesList = ListUtil.fromArray(allowedTypes);
			}
			%>
			<aui:fieldset>
			<liferay-ui:message key="surveyactivity.editquestions.importquestions.question-type"/>
			<ul>
			<%
			for(QuestionType qt:qtypes){
				if (allowedTypesList.contains(String.valueOf(qt.getTypeId()))) {
			%>
				<li>
				<%=qt.getTypeId() +": "+ qt.getTitle(themeDisplay.getLocale())+"<br/>" %>
				</li>
			<%	
				}
			}%>
			</ul>
			</aui:fieldset>
</liferay-ui:panel>

<span>
	<%=LanguageUtil.get(pageContext,"surveyactivity.editquestions.importquestions.download") +" "+ urlExample%>
</span>	
		

<aui:form name="fm" action="<%=importQuestionsURL%>"  method="post" enctype="multipart/form-data" role="form">
	<aui:fieldset>
		<aui:field-wrapper label="file" helpMessage="surveyactivity.editquestions.importquestions.fileHelp">
	    			<aui:input inlineLabel="left" inlineField="true"
					  	name="fileName" label="" id="fileName" type="file" value="">
					  	<aui:validator name="acceptFiles">'xls, xlsx'</aui:validator>
					 </aui:input>
				</aui:field-wrapper>
	</aui:fieldset>
	<aui:button-row>
		<aui:button type="submit" value="upload"></aui:button>
		<liferay-util:include page="/html/surveyactivity/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
	</aui:button-row>
</aui:form>

<liferay-ui:error key="surveyactivity.csvError.empty-file" message="surveyactivity.csvError.empty-file" />

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

