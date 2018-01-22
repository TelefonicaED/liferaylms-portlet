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

<span class="solution">
	<%
	long questionId = ParamUtil.getLong(request,"questionId", 0);
	TestQuestion question = null;
	if(questionId != 0){
		question = TestQuestionLocalServiceUtil.getTestQuestion(ParamUtil.getLong(request,"questionId"));
	}
	int totalAnswer=(question!=null)?(int)TestAnswerLocalServiceUtil.dynamicQueryCount( DynamicQueryFactoryUtil.forClass(TestAnswer.class).
			add(PropertyFactoryUtil.forName("questionId").eq(question.getQuestionId()))):0;
	int i=0;
	if(totalAnswer>0){
		List<TestAnswer> testAnswers = TestAnswerLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(TestAnswer.class).
				add(PropertyFactoryUtil.forName("questionId").eq(question.getQuestionId())));
		for(TestAnswer testanswer:testAnswers){
		i++;	
	%>
		<div id="testAnswer_<%=testanswer.getAnswerId() %>">
			<div id="<portlet:namespace />answerError_<%=testanswer.getAnswerId() %>" class="aui-helper-hidden portlet-msg-error">
				<liferay-ui:message key="answer-test-required"/>
			</div>
			<liferay-ui:panel id="<%=\"panel_\"+testanswer.getAnswerId() %>" title="answer" collapsible="true" extended="true" defaultState="open" persistState="false">
				<div class="leftSideAnswer">
					<aui:input  type="hidden" name="answerId" value="<%=testanswer.getAnswerId() %>"></aui:input>
					<aui:input  type="hidden" name="iterator" value="<%=i%>"></aui:input>
					<aui:input  type="hidden" name="<%=\"correct_\"+testanswer.getAnswerId() %>" label="correct" value="true"/>					
					<script type="text/javascript">
						function <portlet:namespace />onChangeTextAnswer_<%=testanswer.getAnswerId() %>(val) {
				        	var A = AUI();
							A.one('#<portlet:namespace />answer_<%=testanswer.getAnswerId() %>').set('value',val);
				        }
					</script>
					<aui:field-wrapper label="">
						<liferay-ui:input-editor toolbarSet="actslimmer" name="<%=\"answer_\"+testanswer.getAnswerId() %>" width="80%" onChangeMethod="<%=\"onChangeTextAnswer_\"+testanswer.getAnswerId() %>" initMethod="<%=\"initEditorAnswer\"+testanswer.getAnswerId() %>" />
						<script type="text/javascript">
					        function <portlet:namespace />initEditorAnswer<%=testanswer.getAnswerId() %>() { 
					            return "<%=JavaScriptUtil.markupToStringLiteral(testanswer.getAnswer())%>";
					        }
					    </script>
					</aui:field-wrapper>
					<div id="<portlet:namespace />feedBackError_<%=testanswer.getAnswerId()%>" class="aui-helper-hidden portlet-msg-error">
						<liferay-ui:message key="feedback-maxlength"/>
					</div>
					<aui:input  name="<%=\"feedbackCorrect_\"+testanswer.getAnswerId() %>" label="feedbackCorrect" value="<%=testanswer.getFeedbackCorrect() %>" size="60"/>
					<aui:input  name="<%=\"feedbackNoCorrect_\"+testanswer.getAnswerId() %>" label="feedbackNoCorrect" value="<%=testanswer.getFeedbacknocorrect() %>" size="60"/>
				</div>
				<div class="rightSideAnswer">
					<span class="newitem2"><a href="#" class="newitem2" onclick="<portlet:namespace />deleteNode('testAnswer_<%=testanswer.getAnswerId() %>');"><liferay-ui:message key="delete"/></a></span>
				</div>
			</liferay-ui:panel>
		</div>
	<%
		}
	}
	%>
</span>