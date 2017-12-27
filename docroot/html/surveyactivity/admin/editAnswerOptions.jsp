<%@page import="com.liferay.lms.service.SurveyResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
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
	int i=0;
		List<TestAnswer> res = new ArrayList<TestAnswer>();
		if(question!=null){
			res = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
		}
		for(TestAnswer testanswer:res){
			String titleAnswer = HtmlUtil.extractText(testanswer.getAnswer());
			titleAnswer = titleAnswer.replaceAll("&lt;.+?&gt;|<.+?>","");
			titleAnswer = titleAnswer.replaceAll("&nbsp;"," ");
			if(titleAnswer.length() > 50) titleAnswer = titleAnswer.substring(0, 50) + " ...";
			i++;
%>
			<div id="testAnswer_<%=testanswer.getAnswerId() %>" >
				<div id="<portlet:namespace />answerError_<%=testanswer.getAnswerId() %>" class="aui-helper-hidden portlet-msg-error">
					<liferay-ui:message key="answer-test-required"/>
				</div>
				<div id="<portlet:namespace />answer_<%=testanswer.getAnswerId()%>Error"></div>
				<liferay-ui:panel id="<%=\"panel_\"+testanswer.getAnswerId() %>" title="<%=i+\") \"+titleAnswer%>" collapsible="true" extended="true" defaultState="close" persistState="false">
					<div class="leftSideAnswer">
						<aui:input  type="hidden" name="answerId" value="<%=testanswer.getAnswerId() %>"></aui:input>
						<aui:input  type="hidden" name="iterator" value="<%=i%>"></aui:input>
						<aui:input type="hidden" name="<%=\"correct_\"+testanswer.getAnswerId() %>" value="false"></aui:input>
						<script type="text/javascript">
							function <portlet:namespace />onChangeTextAnswer_<%=testanswer.getAnswerId() %>(val) {
					        	var A = AUI();
								A.one('#<portlet:namespace />answer_<%=testanswer.getAnswerId() %>').set('value',val);
					        }
						</script>
						<aui:field-wrapper label="">
							<liferay-ui:input-editor toolbarSet="slimmer" height="15"  name="<%=\"answer_\"+testanswer.getAnswerId() %>" width="80%" onChangeMethod="<%=\"onChangeTextAnswer_\"+testanswer.getAnswerId() %>" initMethod="<%=\"initEditorAnswer_\"+testanswer.getAnswerId() %>" />
							<script type="text/javascript">
						        function <portlet:namespace />initEditorAnswer_<%=testanswer.getAnswerId() %>() { 
						            return "<%=JavaScriptUtil.markupToStringLiteral(testanswer.getAnswer())%>";
						        }
						    </script>
					    </aui:field-wrapper>
					</div>
					<div class="rightSideAnswer">
						<span class="newitem2"><a href="#" class="newitem2" onclick="<portlet:namespace />deleteNode('testAnswer_<%=testanswer.getAnswerId() %>');"><liferay-ui:message key="delete"/></a></span>
					</div>
				</liferay-ui:panel>
			</div>
<%		}
%>
</span>