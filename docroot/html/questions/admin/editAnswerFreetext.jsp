<%@page import="com.liferay.lms.model.impl.LearningActivityModelImpl"%>
<%@page import="com.liferay.lms.model.LearningActivityModel"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.lms.SurveyActivity"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portal.kernel.util.JavaConstants"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@ include file="/init.jsp" %>



<% 
	long questionId = ParamUtil.getLong(request,"questionId", 0);
	TestQuestion question = null;
	if(questionId != 0){
		question = TestQuestionLocalServiceUtil.getTestQuestion(ParamUtil.getLong(request,"questionId"));
	}
	int totalAnswer=(question!=null)?(int)TestAnswerLocalServiceUtil.dynamicQueryCount( DynamicQueryFactoryUtil.forClass(TestAnswer.class).
													add(PropertyFactoryUtil.forName("questionId").eq(question.getQuestionId()))):0;
	long resId = ParamUtil.getLong(request,"resId", 0);
	LearningActivity la = LearningActivityLocalServiceUtil.getLearningActivity(resId);
	
	if(la.getTypeId()==4){
		totalAnswer = 0;%> 
		<span class="question" style="display:none">
			<aui:input type="radio" id="includeSolution" name="includeSolution" label="includeSolution.yes" value="y" onClick='<%= renderResponse.getNamespace() + "showHideSolution();" %>' checked='<%= totalAnswer > 0 %>'/>
			<aui:input type="radio" id="notIncludeSolution" name="includeSolution" label="includeSolution.no" value="n" onClick='<%= renderResponse.getNamespace() + "showHideSolution();" %>' checked='<%= totalAnswer == 0 %>'/>
		</span>
	 <%}else{%>
	<span class="question">
		<aui:input type="radio" id="includeSolution" name="includeSolution" label="includeSolution.yes" value="y" onClick='<%= renderResponse.getNamespace() + "showHideSolution();" %>' checked='<%= totalAnswer > 0 %>'/>
		<aui:input type="radio" id="notIncludeSolution" name="includeSolution" label="includeSolution.no" value="n" onClick='<%= renderResponse.getNamespace() + "showHideSolution();" %>' checked='<%= totalAnswer == 0 %>'/>
	</span> 
		 
	 <%} %> 
	


<script type="text/javascript">
function <portlet:namespace />showHideSolution(){
	var A = AUI();
	if(A.one('input[id=<portlet:namespace />includeSolution]').attr('checked')) {
		A.one('.solution').show();
		A.one('.noSolution').hide();
		A.all('.leftSideAnswer input[id^="<portlet:namespace />correct_"]').val('true').attr('value', 'true');
	}else if(A.one('input[id=<portlet:namespace />notIncludeSolution]').attr('checked')) {
		A.one('.solution').hide();
		A.one('.noSolution').show();
		A.all('.leftSideAnswer input[id^="<portlet:namespace />correct_"]').val('false').attr('value', 'false');
	}
}

AUI().ready('aui-base',
   	function() {
		<portlet:namespace />showHideSolution();
   	}
);

</script>

<span class="noSolution">
<%	if(question!=null){ %>
		<aui:input type="hidden" name="questionId" value="<%=question.getQuestionId() %>"></aui:input>
<%	} %>
</span>

<span class="solution">
	<liferay-ui:success key="answer-added-successfully" message="answer-added-successfully" />
	<%
	if(totalAnswer>0){
		List<TestAnswer> testAnswers = TestAnswerLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(TestAnswer.class).
				add(PropertyFactoryUtil.forName("questionId").eq(question.getQuestionId())));
		for(TestAnswer testanswer:testAnswers){
	%>
		<div id="testAnswer_<%=testanswer.getAnswerId() %>">
			<div id="<portlet:namespace />answerError_<%=testanswer.getAnswerId() %>" class="aui-helper-hidden portlet-msg-error">
				<liferay-ui:message key="answer-test-required"/>
			</div>
			<liferay-ui:panel id="<%=\"panel_\"+testanswer.getAnswerId() %>" title="answer" collapsible="true" extended="true" defaultState="open" persistState="false">
				<div class="leftSideAnswer">
					<aui:input  type="hidden" name="answerId" value="<%=testanswer.getAnswerId() %>"></aui:input>
					<aui:input  type="hidden" name="iterator" value="1"></aui:input>
					<aui:input  type="hidden" name="<%=\"correct_\"+testanswer.getAnswerId() %>" label="correct" value="<%= totalAnswer > 0 %>"/>
					<aui:field-wrapper label="">
						<div class="container-textarea">
							<%String name=renderResponse.getNamespace()+"answer_"+testanswer.getAnswerId(); %>
							<textarea rows="10" cols="88" name="<%=name%>"><%=testanswer.getAnswer()%></textarea>
						</div>
					</aui:field-wrapper>
					<div id="<portlet:namespace />feedBackError_<%=testanswer.getAnswerId()%>" class="aui-helper-hidden portlet-msg-error">
						<liferay-ui:message key="feedback-maxlength"/>
					</div>
					<aui:input  name="<%=\"feedbackCorrect_\"+testanswer.getAnswerId() %>" label="feedbackCorrect" value="<%=testanswer.getFeedbackCorrect() %>" size="60"/>
					<aui:input  name="<%=\"feedbackNoCorrect_\"+testanswer.getAnswerId() %>" label="feedbackNoCorrect" value="<%=testanswer.getFeedbacknocorrect() %>" size="60"/>
				</div>
			</liferay-ui:panel>
		</div>
	<%
		}
	}
	%>
</span>
