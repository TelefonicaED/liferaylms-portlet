<%@page import="com.liferay.lms.learningactivity.questiontype.SurveyHorizontalOptionsQuestionType"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="com.liferay.lms.SurveyActivity"%>
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

	long questionId = ParamUtil.getLong(request,"questionId",0);
	long typeId = ParamUtil.getLong(request,"questionTypeId", -1);
	TestQuestion question = null;
	LearningActivity learningActivity = null;
	boolean isNewQuestion = false;
	boolean isHorizontal = false;
	long horizontalQuestionType = new SurveyHorizontalOptionsQuestionType().getTypeId();
	
	if (questionId != 0){
		question 		 = TestQuestionLocalServiceUtil.getTestQuestion(ParamUtil.getLong(request,"questionId"));
		learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(question.getActId());
		isHorizontal 	 = question.getQuestionType() == horizontalQuestionType;
	}else{
		isNewQuestion = true;
		learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
	}
	
	request.setAttribute("activity", learningActivity);
	PortletURL backUrl = renderResponse.createRenderURL();
	backUrl.setParameter("resId", String.valueOf(learningActivity.getActId()));
	backUrl.setParameter("qtype", String.valueOf(typeId));
	backUrl.setParameter("jspPage", "/html/surveyactivity/admin/editquestions.jsp");
	backUrl.setParameter("actionEditingDetails",StringPool.TRUE);
	request.setAttribute("backUrl", backUrl.toString());
%>
<liferay-util:include page="/html/surveyactivity/admin/editHeader.jsp" servletContext="<%=this.getServletContext() %>" />

<script type="text/javascript">
<!--

AUI().ready('node-base' ,'aui-form-validator', 'aui-overlay-context-panel', function(A) {

	window.<portlet:namespace />validateQuestion = new A.FormValidator({
		boundingBox: '#<portlet:namespace />qfm',
		validateOnBlur: true,
		validateOnInput: true,
		selectText: true,
		showMessages: false,
		containerErrorClass: '',
		errorClass: '',
		rules: {
            <portlet:namespace />text: {
	    		required: true
	        }
		},
        fieldStrings: {
            <portlet:namespace />text: {
	    		required: Liferay.Language.get("surveyactivity.editquestions.editquestion.error.text.required")
	        }
	    },
		on: {		
            errorField: function(event) {
            	var instance = this;
				var field = event.validator.field;
				var divError = A.one('#'+field.get('name')+'Error');
				if(divError) {
					divError.addClass('portlet-msg-error');
					divError.setContent(instance.getFieldErrorMessage(field,event.validator.errors[0]));
				}
            },		
            validField: function(event) {
				var divError = A.one('#'+event.validator.field.get('name')+'Error');
				if(divError) {
					divError.removeClass('portlet-msg-error');
					divError.setContent('');
				}
            }
		}
	});

	window.<portlet:namespace />validateAnswer = new A.FormValidator({
		boundingBox: '#<portlet:namespace />afm',
		validateOnBlur: true,
		validateOnInput: true,
		selectText: true,
		showMessages: false,
		containerErrorClass: '',
		errorClass: '',
		rules: {
            <portlet:namespace />answer: {
	    		required: true
	        }
		},
        fieldStrings: {
            <portlet:namespace />answer: {
	    		required: Liferay.Language.get("answer-test-required")
	        }
	    },
		on: {		
            errorField: function(event) {
            	var instance = this;
				var field = event.validator.field;
				var divError = A.one('#'+field.get('name')+'Error');
				if(divError) {
					divError.addClass('portlet-msg-error');
					divError.setContent(instance.getFieldErrorMessage(field,event.validator.errors[0]));
				}
            },		
            validField: function(event) {
				var divError = A.one('#'+event.validator.field.get('name')+'Error');
				if(divError) {
					divError.removeClass('portlet-msg-error');
					divError.setContent('');
				}
            }
		}
	});
	A.one("#<portlet:namespace/>backbutton").scrollIntoView();
});

//-->
</script>

<portlet:actionURL var="editquestionURL" name="editquestion" />
<aui:form name="qfm" action="<%=editquestionURL %>" method="post" role="form">
	<aui:input name="resId" type="hidden" value="<%=learningActivity.getActId() %>"></aui:input>
	<c:if test="<%=typeId != 2 %>">
		<aui:input name="isHorizontal" label="learningactivity.survey.horizontal" type="checkbox" value="<%=isHorizontal %>" checked="<%=isHorizontal %>"></aui:input>
	</c:if>
	<aui:input name="qtype" type="hidden" value="<%=typeId %>"></aui:input>
	
	<%
	
	QuestionType qt = 	new QuestionTypeRegistry().getQuestionType(typeId);
	String questionTypeName = (qt!=null)?qt.getTitle(themeDisplay.getLocale()):"";
	int maxAnswersNo = (qt!=null)?qt.getMaxAnswers():0;
	int defaultAnswersNo = (qt!=null)?qt.getDefaultAnswersNo():0;
	if(!isNewQuestion){%>
		<aui:input name="questionId" type="hidden" value="<%=question.getQuestionId() %>"></aui:input>

	<%} %>
	<script type="text/javascript">
	<!--
		Liferay.provide(
	        window,
	        '<portlet:namespace />onChangeText',
	        function(val) {
	        	var A = AUI();
				A.one('#<portlet:namespace />text').set('value',val);
				if(window.<portlet:namespace />validateQuestion){
					window.<portlet:namespace />validateQuestion.validateField('<portlet:namespace />text');
				}
	        },
	        ['node']
	    );
	    
	//-->
	</script>
    
	<aui:field-wrapper label="surveyactivity.editquestions.editquestion.enunciation">
		<liferay-ui:input-editor name="text" width="80%" onChangeMethod="onChangeText" />
		
	<%String qText = "";
	if(question != null) qText = question.getText();%>
	<script type="text/javascript">
	        function <portlet:namespace />initEditor() 
	        { 
	            return "<%=JavaScriptUtil.markupToStringLiteral(qText)%>";
	        }
	</script>
		

	</aui:field-wrapper>
	
	<portlet:renderURL var="viewAnswerURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">   
		<portlet:param name="<%=WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>" value="<%=StringPool.TRUE %>"/>     
	    <portlet:param name="jspPage" value="<%=qt.getURLNew() %>" />    
	    <portlet:param name="actionEditingDetails" value="true"/>
	    <portlet:param name="resId" value="<%=ParamUtil.getString(request,\"resId\", \"0\") %>"/>
	    <portlet:param name="qtype" value="<%=ParamUtil.getString(request,\"typeId\", \"0\") %>"/>
	</portlet:renderURL>
	
	<script type="text/javascript">
		function <portlet:namespace />addNode(){
	 		AUI().use('aui-node', 'aui-io',
	 			function(A) {
	 				var parent = A.one('.solution');
	 				var list = A.all('.solution > div'),lastNode=null;
	 				var iter = 1;
	 				
	 				if (list.size()) {
						lastNode = list.item(list.size() - 1);
						iter = A.all('#'+lastNode.get('id') +' input[name="<portlet:namespace />iterator"]').val();
						iter = parseInt(iter) +1;
					}
	
	 				if(parent!=null) parent.append(A.Node.create('<div id="testAnswer_new'+iter+'"></div>').plug(A.Plugin.IO,{
	 					uri:'<%=viewAnswerURL%>',
	 					parseContent:true,
	 					data:{iterator:iter}
	 				}));
	  			}
	 		);
		}
		
		function <portlet:namespace />addNodes(itemList, iter){
	 		AUI().use('aui-node', 'aui-io',
	 			function(A) {
	 				var parent = A.one('.solution');
	
	 				if(itemList.length>0){
	 					if(parent!=null) parent.append(A.Node.create(itemList[0]).plug(A.Plugin.IO,{
		 					uri:'<%=viewAnswerURL%>',
		 					parseContent:true,
		 					data:{iterator:iter},
		 					on: {
		 						success: function() {
		 							<portlet:namespace />addNodes(itemList, iter+1);
		 						}
		 					}
	 					}));
	 					itemList.shift();
	 				}
	  			}
	 		);
		}
		
		function <portlet:namespace />deleteNode(id){
	 		AUI().use('aui-node', 
	 			function(A) {
	 				var validate = false;
	 				var elemId = id.replace('testAnswer_', '');
	 				var answer = A.one('textarea[name=<portlet:namespace />answer_'+elemId+']');
	 				feedbackCorrect = A.one('input[name=<portlet:namespace />feedbackCorrect_'+elemId+']');
	    			feedbackNoCorrect = A.one('input[name=<portlet:namespace />feedbackNoCorrect_'+elemId+']');
	    			correct = A.one('input[name=<portlet:namespace />correct_'+elemId+'Checkbox]');
	    			var somefieldWithValue = (answer != null && answer.val() !="") ||
	    										(feedbackCorrect != null && feedbackCorrect.val() !="") || 
												(feedbackNoCorrect != null && feedbackNoCorrect.val() != "") || 
												(correct != null && correct._node.checked);
	    			if(somefieldWithValue){
	    				if(confirm(Liferay.Language.get('deleteContentConfirmation'))) validate = true;
	    			}else validate = true;
	    			
	    			if(validate){
	 				 	A.one('#'+id).remove();
	 					<portlet:namespace />checkMinAnswersNo();
	 				}
	  			}
	 		);
		}
		
		function <portlet:namespace />checkAddAnswerButtonVisibility(){
	 		AUI().use('aui-node', 
	 			function(A) {
	 				var list = A.all('.solution > div');
	 				var numNodes = list.size();
	 				var maxNodes = parseInt(<%=maxAnswersNo%>);
	 				if(numNodes >= maxNodes) A.all('#addAnswerButton').addClass("aui-helper-hidden");
	 				else  A.all('#addAnswerButton').removeClass("aui-helper-hidden");
	  			}
	 		);
		}
		
		function <portlet:namespace />checkMinAnswersNo(){
	 		AUI().use('aui-node', 
	 			function(A) {
	 				var list = A.all('.solution > div');
	 				var numNodes = list.size();
	 				var defaultNodesNo = parseInt(<%=defaultAnswersNo%>);
	 				if(numNodes < defaultNodesNo){
	 					
		 				var list = A.all('.solution > div'),lastNode=null;
		 				var iter = 1;
		 				
		 				if (list.size()) {
							lastNode = list.item(list.size() - 1);
							iter = A.all('#'+lastNode.get('id') +' input[name="<portlet:namespace />iterator"]').val();
							iter = parseInt(iter) +1;
						}
		 				
		 				var itemList = [];
		 				var iterAux = iter;
		 				while(numNodes < defaultNodesNo){
		 					itemList.push('<div id="testAnswer_new'+iterAux+'"></div>');
		 					numNodes = numNodes + 1;
		 					iterAux = iterAux + 1;
		 				}
		 				
	 					<portlet:namespace />addNodes(itemList, iter);
	 					
	 				}
	 				<portlet:namespace />checkAddAnswerButtonVisibility();
	 			}
	 		);
		}
		
		 function validateFields(e){
		    	AUI().use('node',
		    		function(A) {	
			    		var valid = true;
			    		//todas las respuestas plegadas
			    		var panels = A.all('[id^=panel_]');
			    		panels.each(function() {
			    			this.addClass('lfr-collapsed');
			    		});
			    		
			    		//Pregunta no vacía
			    		var question = A.one('#<portlet:namespace />text');
			    		if(question.val()==""){
			    			A.one('#<portlet:namespace />questionError').removeClass('aui-helper-hidden');
			    			valid=false;
			    		}else{
			    			A.one('#<portlet:namespace />questionError').addClass('aui-helper-hidden');
			    		}			    		
			    		//Ninguna respuesta vacía
			    		var list = A.all('.solution > div');
			    		list.each(function() {
			    			var id = this.get('id');
			    			alert("--> "+id);
			    			id=id.replace('testAnswer_','');
			    			
			    			feedbackCorrect = A.one('input[name=<portlet:namespace />feedbackCorrect_'+id+']');
			    			feedbackNoCorrect = A.one('input[name=<portlet:namespace />feedbackNoCorrect_'+id+']');
			    			correct = A.one('input[name=<portlet:namespace />correct_'+id+'Checkbox]');
			    			correctVal = (correct != null && correct._node.checked);
			    			if (correct == null) {
			    				correct = A.one('input[name=<portlet:namespace />correct_'+id+']');
			    				correctVal = (correct != null && correct.val() === 'true');
			    			}

			    			var otherFieldsWithValue = (feedbackCorrect != null && feedbackCorrect.val() !="") || 
			    										(feedbackNoCorrect != null && feedbackNoCorrect.val() != "") || 
			    										(correctVal);
			    			if(otherFieldsWithValue){
			    				answer = A.one('textarea[name=<portlet:namespace />answer_'+id+']');
				    			if (answer != null && answer.val() == "") {
									A.one('#<portlet:namespace />answerError_'+id).removeClass('aui-helper-hidden');
				    				valid=false;
				    				A.one('#panel_'+id).removeClass('lfr-collapsed');
								}else{
									A.one('#<portlet:namespace />answerError_'+id).addClass('aui-helper-hidden');
								}
			    			}
			    		});
			    		
			    		//Ningun feedback > 300 caracteres
			    		if(valid){
				    		list.each(function() {
				    			var id = this.get('id');
				    			id=id.replace('testAnswer_','');
				    			
				    			feedbackCorrect = A.one('input[name=<portlet:namespace />feedbackCorrect_'+id+']');
				    			feedbackNoCorrect = A.one('input[name=<portlet:namespace />feedbackNoCorrect_'+id+']');
								
				    			if((feedbackCorrect != null && feedbackCorrect.val().length > 600) || 
										(feedbackNoCorrect != null && feedbackNoCorrect.val().length > 600)){

										A.one('#<portlet:namespace />feedBackError_'+id).removeClass('aui-helper-hidden');
					    				valid=false;
					    				A.one('#panel_'+id).removeClass('lfr-collapsed');
				    			}else{
									A.one('#<portlet:namespace />feedBackError_'+id).addClass('aui-helper-hidden');
								}
				    		});
			    		}
			    		
			    		if (!valid && e.preventDefault) {
							e.preventDefault();
						}
				    	return valid;
		    		}
		    	);
		    }
		
		AUI().ready('aui-base',
		   	function() {
		    	<portlet:namespace />checkAddAnswerButtonVisibility();
		    	<portlet:namespace />checkMinAnswersNo();
		   	}
		);
	</script>

	<%
	if(learningActivity.getTypeId()!=4){ %>
		<aui:field-wrapper label="answers" helpMessage="<%=qt.getDescription(themeDisplay.getLocale()) %>" /><%

	 } %>
	<liferay-ui:error key="answer-test-required" message="answer-test-required"/>
	<jsp:include page="<%=(qt!=null)?qt.getURLEdit():\"\" %>"/>
    <aui:button-row>

	<%	
	if(typeId!=2){%>

    	<div id="addAnswerButton">
    		<span class="newitem2">
				<a href="#" class="newitem2" onclick="<portlet:namespace />addNode();"><liferay-ui:message key="add-answer"/></a>
		</span>
	</div>
	<%}%>
	<div class="buttons_content">
		<aui:button type="submit" onClick='<%= "return validateFields(event);" %>'/>
		<liferay-util:include page="/html/surveyactivity/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
	</div>
	</aui:button-row>
	
	
	
</aui:form>