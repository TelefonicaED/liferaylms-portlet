<%@page import="com.liferay.lms.learningactivity.SurveyLearningActivityType"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.liferay.lms.model.SurveyResult"%>
<%@page import="com.liferay.lms.service.SurveyResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>


<%@ include file="/init.jsp"%>

<%
	if (ParamUtil.getLong(request, "actId", 0) == 0) 
	{
		renderRequest.setAttribute( WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	} 
	else
	{
		long actId = ParamUtil.getLong(request, "actId",0);
		LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
	%>
		<liferay-portlet:resourceURL var="stadisticsReportURL" >
			<portlet:param name="action" value="stadisticsReport"/>
			<portlet:param name="resId" value="<%=Long.toString(learningActivity.getActId()) %>"/>
		</liferay-portlet:resourceURL>
		<liferay-ui:icon image="export" label="<%= true %>" message="download-content" method="get" url="<%=stadisticsReportURL%>" />
		
		<div class="surveyactivity stadistics">
		
			<h2><%=LearningActivityLocalServiceUtil.getLearningActivity(actId).getTitle(themeDisplay.getLocale()) %></h2>
			<%--<h3 class="description-h3"><liferay-ui:message key="description" /></h3> --%>
			<div class="description"><%=LearningActivityLocalServiceUtil.getLearningActivity(actId).getDescription(themeDisplay.getLocale()) %></div>
		
			
			
			<portlet:renderURL var="backToQuestionsURL">
				<portlet:param name="jspPage" value="/html/surveyactivity/view.jsp"></portlet:param>
			</portlet:renderURL>
		
			<%
			List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
			List<SurveyResult> listaResultadosEncuesta = SurveyResultLocalServiceUtil.getSurveyResultByActId(actId);
			long total;
			long totalAnswer;
			String textoAux;
			String percent;
			DecimalFormat df;
			String texto;
			for(TestQuestion question:questions)
			{
			%>
			<div class="question">
				<div  class="questiontext">
					<p><%=question.getText() %></p>
				</div>
				<span class="total color_tercero"><liferay-ui:message key="surveyactivity.stadistics.total" />: <%=SurveyResultLocalServiceUtil.getTotalAnswersByQuestionId(question.getQuestionId()) %></span>		
				<%
				
				
				List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
				
				if(testAnswers!=null && testAnswers.size()>0){
					total = SurveyResultLocalServiceUtil.getTotalAnswersByQuestionId(question.getQuestionId());
					for(TestAnswer answer:testAnswers)
					{
						textoAux = HtmlUtil.extractText(answer.getAnswer());
						texto = textoAux.length() > 50 ? textoAux.substring(0,50)+"..." : textoAux;
						df = new DecimalFormat("###.##");
						percent = df.format(SurveyResultLocalServiceUtil.getPercentageByQuestionIdAndAnswerId(question.getQuestionId(), answer.getAnswerId(), total));
						totalAnswer = SurveyResultLocalServiceUtil.getCountByQuestionIdAndAnswerId(question.getQuestionId(), answer.getAnswerId()); 
					%>
						<div class="answer">
							<%=texto %>
							<span class="porcentaje negrita"><liferay-ui:message key="surveyactivity.stadistics.percent" /> <%=totalAnswer %> (<%=percent %>%)</span>
						</div>
					<%
					}
				}else{
					if(listaResultadosEncuesta!=null && listaResultadosEncuesta.size()>0){
					%>
						<div class="answer">
									<p><liferay-ui:message key="surveyactivity.stadistics.freeanswer" /></p>
								</div>
					<% }
				}
				
				
				
				
				%>
			</div>
			<%
			} 
			%>
			<a href="<%=backToQuestionsURL.toString() %>" ><liferay-ui:message key="back" /></a>
		</div>
		
	<%	
	} 
	%>
	
