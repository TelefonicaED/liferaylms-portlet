<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="java.util.Collections"%>
<%@page import="org.apache.commons.beanutils.BeanComparator"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="javax.portlet.RenderResponse"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<style type="text/css">
.horizontalquestion {
  overflow: auto;
}

.horizontalquestion>div {
  float: left;
  width: 99%;
}

.horizontalquestion>div>div {
  float: left;
  width: 98%;
}

.horizontalquestion>div>div>div {
  float: left;
  width: 20%;
}

.textolibre {
  overflow: auto;
  width: 1024px;
  height: 20px;
}

textarea {
    resize: none;
    width : 75%;
    min-width: 75%;
}

</style>
<div class="container-activity">
<%
	long actId = ParamUtil.getLong(request,"actId",0);
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
	if(actId==0)
	{
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
	else
	{
		
		LearningActivity activity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		long typeId=activity.getTypeId();
		boolean isSurvey = activity.getTypeId() == 4;
		boolean isEvaluationSurvey = activity.getTitle(themeDisplay.getLocale())
					.equalsIgnoreCase(LanguageUtil.get(pageContext, "surveyactivity.evaluation.title"));
		
		if(typeId==4&&(!LearningActivityLocalServiceUtil.islocked(actId,themeDisplay.getUserId())||
				permissionChecker.hasPermission(activity.getGroupId(), LearningActivity.class.getName(), actId, ActionKeys.UPDATE)||
				permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK")))
		{

	
		%>
          
			<div class="surveyactivity view">
				<h2><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
				<%--<h3 class="description-h3"><liferay-ui:message key="description" /></h3> --%>
				<div class="description"><%=activity.getDescription(themeDisplay.getLocale()) %></div>
				
				<%if(permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(), activity.getActId(), ActionKeys.UPDATE)){ %>
					<portlet:renderURL var="stadisticsURL">
						<portlet:param name="jspPage" value="/html/surveyactivity/stadistics.jsp"></portlet:param>
						<portlet:param name="actId" value="<%=String.valueOf(actId) %>" />
					</portlet:renderURL>
					<liferay-ui:icon image="view" message="surveyactivity.stadistics" label="true" url="<%=stadisticsURL.toString() %>" />
				<%
				}
				
				
					if(LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId()))
					{
					%>
						<h2 class="description-title"><liferay-ui:message key="surveyactivity.survey.done" /></h2>
					<%
					}
					else
					{
						ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivityTry.class.getName(), renderRequest);
						
						LearningActivityTry learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
						List<TestQuestion> questiones=TestQuestionLocalServiceUtil.getQuestions(actId);
						List<TestQuestion> questions = ListUtil.copy(questiones);
						BeanComparator beanComparator = new BeanComparator("weight");
						Collections.sort(questions, beanComparator);
						
					%>
						<portlet:actionURL name="correct" var="correctURL">
							<portlet:param name="actId" value="<%=Long.toString(actId) %>"></portlet:param>
							<portlet:param name="latId" value="<%=Long.toString(learningTry.getLatId()) %>"></portlet:param>
						</portlet:actionURL>
																	
						<script type="text/javascript">
						<!--
						Liferay.provide(
						        window,
						        '<portlet:namespace />questionValidation',
						        function(question) {
									var A = AUI();
									var questionValidators = {
										questiontype_options : function(question) {
											return (question.all('div.answer input[type="radio"]:checked').size() > 0);
										}
									};
									
									var clases = question.getAttribute('class').split(" ");
									var questiontypevalidator = '';
									for ( var i = 0; i < clases.length; i++) {
										var clase = clases[i];
										if (clase.indexOf('questiontype_') == 0) {
											questiontypevalidator = clase;
											break;
										}
									}
									if (questionValidators[questiontypevalidator] != null) {
										var resultado = questionValidators[questiontypevalidator](question);
										return resultado;
									} else {
										return true;
									}
									
						        },
						        ['node', 'aui-dialog', 'event', 'node-event-simulate']
						        );
						
						Liferay.provide(
						        window,
						        '<portlet:namespace />popConfirm',
						        function(content, boton) {
									var A = AUI();
								
									window.<portlet:namespace />confirmDialog = new A.Dialog(
									    {
									        title: '<liferay-ui:message key="execativity.test.questions.without.response"/>',
									        bodyContent: content,
									        buttons: [
									                  {
									                	  label: '<liferay-ui:message key="ok"/>',
									                	  handler: function() {
									                		  A.one('#<portlet:namespace/>formulario').detach('submit');
									                		  if (!<%=isEvaluationSurvey%>)
									                			 document.getElementById('<portlet:namespace/>formulario').submit();
									                		  <portlet:namespace />confirmDialog.close();
									                	  }
									                  },
									                  {
									                	  label: '<liferay-ui:message key="cancel"/>',
									                	  handler: function() {
									                		  <portlet:namespace />confirmDialog.close();
									                	  }
									                  }
									                  ],
									        width: 'auto',
									        height: 'auto',
									        resizable: false,
									        draggable: false,
									        close: true,
									        destroyOnClose: true,
									        centered: true,
									        modal: true
									    }
									).render();
									
						        },
						        ['node', 'aui-dialog', 'event', 'node-event-simulate']
						    );
						
						Liferay.provide(
						        window,
						        '<portlet:namespace/>formValidation',
						function(e) {
							var returnValue = true;
							
							var A = AUI();
						    var questions = A.all('#<portlet:namespace/>formulario div.question');
						    for (var i = 0; i < questions.size(); i++) {
						    	var question = questions.item(i);
						    	var validQuestion = <portlet:namespace />questionValidation(question);
						    	if (typeof validQuestion == 'undefined') {
						    		validQuestion = <portlet:namespace />questionValidation(question);
						    	}
						    	if (!validQuestion) {
						    		returnValue = false;
						    		break;
						    	}
							}
							
						    if (!returnValue) {
						    	if (e.target) {
						    		targ = e.target.blur();
						    	} else if (e.srcElement) {
						    		targ = e.srcElement.blur();
						    	}
						    	<%= renderResponse.getNamespace() %>popConfirm('<%=JavaScriptUtil.markupToStringLiteral(LanguageUtil.get(pageContext, "surveyactivity.survey.without.response.text")) %>', e.srcElement);
				    		}
						    
							if (!returnValue && e.preventDefault) {
								e.preventDefault();
							}
							return returnValue;
						},
						['node', 'aui-dialog', 'event', 'node-event-simulate']
					);
						//-->
						</script>
						
						<aui:form name="formulario" action="<%=correctURL %>" method="POST">
						<%
						for(TestQuestion question:questions)
						{
							if( question.getQuestionType() != 7 && question.getQuestionType() != 2 ){
						%>
								<div class="question questiontype_options">
								<div class="questiontext"><%=question.getText() %></div>
								<%
								List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
								for(TestAnswer answer:testAnswers)
								{
								%>
									<div class="answer"><aui:input id='<%="question_"+question.getQuestionId()%>' type="radio" name='<%="question_"+question.getQuestionId()%>' value="<%=answer.getAnswerId() %>" label="<%=answer.getAnswer() %>"/>
									</div>
								<%
								}
								%>
								</div>
								<%
							} else if( question.getQuestionType() == 7) { %>
								<div class="horizontalquestion">
									<div class="question questiontype_options">
										<div class="questiontext"><%=question.getText() %>
											<%
											List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
											for(TestAnswer answer:testAnswers)
											{
											%>
											<div class="answer"><aui:input id='<%="question_"+question.getQuestionId()%>' type="radio" name='<%="question_"+question.getQuestionId()%>'  value='<%=answer.getAnswerId() %>'  label="<%=answer.getAnswer() %>"/>
											</div>
											<%
											}
										%>
										</div>
									</div>
								</div>
							<% } else { // En este caso es texto libre %>
								<div>
 									<div class="questiontext"><%=question.getText() %></div> 
									<div class="answer"><textarea rows="10" cols="100" maxlength="2000" name='<%="question_"+question.getQuestionId()%>'></textarea></div>
 									<%--<div class="answer"><input type="text" name='<%="question_"+question.getQuestionId()%>'></input></div> --%>
								</div>
							<% }
							
						}
						
						if(questions.size() > 0)
						{
						%>
						<aui:button-row>
						<aui:button type="submit"  onClick='<%= "return  "+renderResponse.getNamespace() + "formValidation(event);" %>' ></aui:button>
						</aui:button-row>
						<%}%>
						</aui:form>
						<%
						}
					%></div> <%
					}	
				}
			%>


</div>