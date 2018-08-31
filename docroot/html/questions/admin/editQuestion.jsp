<%@page import="com.liferay.lms.learningactivity.SurveyLearningActivityType"%>
<%@page import="com.liferay.lms.SurveyActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.xml.DocumentException"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.util.portlet.PortletProps"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
<%@ include file="/init.jsp" %>

<%
	long questionId = ParamUtil.getLong(request,"questionId", 0);
	long typeId = ParamUtil.getLong(request,"questionTypeId", -1);
	long actId = ParamUtil.getLong(request,"resId", 0);
	String backUrl = ParamUtil.getString(request, "backUrl", currentURL);
	String formatType = PropsUtil.get("lms.question.formattype.normal");
	
	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
	request.setAttribute("activity", learningActivity);

	request.setAttribute("backUrl", backUrl.toString());
	
	
	TestQuestion question = null;
	if(questionId != 0){
		question = TestQuestionLocalServiceUtil.getTestQuestion(ParamUtil.getLong(request,"questionId"));
		
		String[] allowedTypes = PropsUtil.getArray("lms.questions.allowed.for."+learningActivity.getTypeId());
	 	List<String> allowedTypesList = new ArrayList<String>();
		if (allowedTypes != null) {
			allowedTypesList = ListUtil.fromArray(allowedTypes);
		}
		
		if (!allowedTypesList.isEmpty() && !allowedTypesList.contains(String.valueOf(question.getQuestionType())) && question.getQuestionType() != 7) {
			typeId = Long.valueOf(allowedTypes[0]);
			question.setQuestionType(typeId);
			question = TestQuestionLocalServiceUtil.updateTestQuestion(question);
			
		}
	}
	
%>
<liferay-util:include page="/html/questions/admin/editHeader.jsp" servletContext="<%=this.getServletContext() %>" />

<portlet:actionURL var="editQuestionURL" name="editQuestion" />
<aui:form name="qfm" action="<%=editQuestionURL %>" method="post" onSubmit="javascript:return false;">

	<%
		QuestionType qt = 	new QuestionTypeRegistry().getQuestionType(typeId);
		String questionTypeName = (qt!=null)?qt.getTitle(themeDisplay.getLocale()):"";
		int maxAnswersNo = (qt!=null)?qt.getMaxAnswers():0;
		int defaultAnswersNo = (qt!=null)?qt.getDefaultAnswersNo():0;
	%>
	<aui:input type="hidden" id="typeId" name="typeId" value="<%=typeId%>" />
	<aui:input type="hidden" id="backUrl" name="backUrl" value="<%=backUrl%>" />

	<aui:input name="resId" type="hidden" value="<%=learningActivity.getActId() %>"></aui:input>
	<%if(question != null){ %>
		<aui:input name="questionId" type="hidden" value="<%=question.getQuestionId() %>"></aui:input>
	<%} %>
	
	<script type="text/javascript">
		function <portlet:namespace />onChangeText(val) {
        	var A = AUI();
			A.one('#<portlet:namespace />text').set('value',val);
        }
	</script>
    
    <%	
		boolean enableOrder = StringPool.TRUE.equals(PropsUtil.get("lms.learningactivity.testoption.editformat"));
		if(qt.isInline()){
			try{
				Document document = SAXReaderUtil.read(question.getExtracontent());
				Element rootElement = document.getRootElement();
				formatType = (String) rootElement.element("formattype").getData();
			}catch(NullPointerException e){
				formatType = PropsUtil.get("lms.question.formattype.normal");
			}catch(DocumentException e){
				formatType = PropsUtil.get("lms.question.formattype.normal");
			}
			if (enableOrder){
	%>
				<aui:select name="formattype" label="exectactivity.editquestions.formattype" helpMessage="exectactivity.editquestions.formattype.helpMessage" inlineLabel="true" > 
					<aui:option selected="<%=formatType.equals(PropsUtil.get(\"lms.question.formattype.normal\")) %>" value="<%=PropsUtil.get(\"lms.question.formattype.normal\")%>">
						<liferay-ui:message key="exectactivity.editquestions.formattype.vertical" />
					</aui:option>
					<aui:option selected="<%=formatType.equals(PropsUtil.get(\"lms.question.formattype.horizontal\")) || typeId == 7%>" value="<%=PropsUtil.get(\"lms.question.formattype.horizontal\") %>">
						<liferay-ui:message key="exectactivity.editquestions.formattype.horizontal" />
					</aui:option>
					<aui:option selected="<%=formatType.equals(PropsUtil.get(\"lms.question.formattype.combo\")) %>" value="<%=PropsUtil.get(\"lms.question.formattype.combo\") %>">
						<liferay-ui:message key="exectactivity.editquestions.formattype.combo" />
					</aui:option>
				</aui:select>
	<%
			}else{
	%>
				<aui:input type="hidden" name="formattype" value="<%=formatType %>" ignoreRequestValue="true" />
	<%
			}
		}
	%>

	<aui:field-wrapper label="">
		<div id="<portlet:namespace />questionError" class="aui-helper-hidden portlet-msg-error">
			<liferay-ui:message key="execactivity.editquestions.newquestion.error.text.required"/>
		</div>
		
		<%
			String qId = "question_"+((question!=null)?question.getQuestionId():"");
			boolean extended = true;
		%>
		
		<liferay-ui:success key="question-modified-successfully" message="question-modified-successfully" />
		<liferay-ui:error key="execactivity.editquestions.newquestion.error.text.required" message="execactivity.editquestions.newquestion.error.text.required"/>
		<liferay-ui:error key="execativity.test.error" message="execativity.test.error"/>
		
		<aui:field-wrapper label="execativity.editquestions.editquestion.enunciation">
			<liferay-ui:input-editor toolbarSet="actslimmer" name="text" width="80%" onChangeMethod="onChangeText" initMethod="initEditor"/>
			<%
			String qText = "";
			if(question != null) qText = question.getText();	
			%>
				<script type="text/javascript">
			        function <portlet:namespace />initEditor() { 
			            return "<%=JavaScriptUtil.markupToStringLiteral(qText)%>";
			        }
			    </script>
	    </aui:field-wrapper>
	    
	    <%
	    boolean partialCorrection = false;
		if(qt.isPartialCorrectAvailable()){
			
			try{
				Document document = SAXReaderUtil.read(question.getExtracontent());
				Element rootElement = document.getRootElement();
				partialCorrection = StringPool.TRUE.equals(rootElement.element("partialcorrection").getData());
			}catch(NullPointerException e){
				partialCorrection = false;
			}catch(DocumentException e){
				partialCorrection = false;
			}
		%>
				<aui:input type="checkbox" label="execactivity.editquestions.partialcorrection" helpMessage="execactivity.editquestions.partialcorrection.helpmessage" name="partialcorrection" 
						checked="<%=partialCorrection %>" last="true" inlineLabel="true" inlineField="true" onChange="javascript:${renderResponse.getNamespace()}changePartialCorrection(this.checked)"/>
						
			   <script type="text/javascript">
					function <portlet:namespace/>changePartialCorrection(value){
						if($('#<portlet:namespace/>penalizeCheckbox')!=null){
							if(value){
								$('#<portlet:namespace/>penalizeCheckbox').prop('checked', false); 
								$('#<portlet:namespace/>penalizeCheckbox').prop('disabled', true);
									
							}else{
								$('#<portlet:namespace/>penalizeCheckbox').prop('disabled', false);
							}
						}
					}
				</script>
				
		<%
			}
		
		if(qt.isPartialCorrectAvailable() && question!=null && !(question.isPenalize()&&!partialCorrection)){		%>
			<script type="text/javascript">
				AUI().ready('aui-base',
				   	function() {
						$('#<portlet:namespace/>penalizeCheckbox').prop('checked', false); 
						$('#<portlet:namespace/>penalizeCheckbox').prop('disabled', true);
				   	}
				);
			</script>
			
		<%
		}
			
			
		%>
	    
	    <c:if test="<%=qt.getPenalize() && (learningActivity.getTypeId() != SurveyLearningActivityType.TYPE_ID)%>">
		    	<aui:input name="penalize" label="question.penalize" type="checkbox" disabled="<%=partialCorrection%>" checked="<%=question!=null?(question.isPenalize()&&!partialCorrection):false%>"/>
	    </c:if>
	</aui:field-wrapper>
	
	<portlet:renderURL var="viewAnswerURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">   
		<portlet:param name="<%=WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>" value="<%=StringPool.TRUE %>"/>     
	    <portlet:param name="jspPage" value="<%=qt.getURLNew() %>" />    
	    <portlet:param name="actionEditingDetails" value="true"/>
	    <portlet:param name="resId" value="<%=ParamUtil.getString(request,\"resId\", \"0\") %>"/>
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
	 					data:{iterator:iter,typeId:<%=typeId%>}
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
		 					data:{iterator:iter,typeId:<%=typeId%>},
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
		    		var typeId=<%=typeId%>;
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
			    		var trueCounter = 0;
			    		var index = 1;
			    		list.each(function() {
			    			var id = this.get('id');
			    			id=id.replace('testAnswer_','');
			    			if(typeId==1 || typeId==4)id=id.replace('new','');

			    			feedbackCorrect = A.one('input[name=<portlet:namespace />feedbackCorrect_'+id+']');
			    			feedbackNoCorrect = A.one('input[name=<portlet:namespace />feedbackNoCorrect_'+id+']');
			    			
			    			switch(typeId){
			    				
				    			case 0:
				    				var radioChecked = (A.one('input[name=<portlet:namespace/>correct_new]:checked'));
		    						if(radioChecked==null){
		    							valid = false;
		    							correctVal = false;
		    						}	else{
		    							trueCounter++;
		    							correctVal = true;
		    						}
				    			break;
			    			
			    				case 1:
			    				
			    					correct = A.one('input[name=<portlet:namespace />correct_'+id+'Checkbox]');
			    					correctVal = (correct != null && correct._node.checked);
				    				if(correctVal==true)trueCounter++;
				    				if (correct == null) {
				    					correct =document.getElementById('input[name=<portlet:namespace />correct_'+id+']');
				    					correctVal = (correct != null && correct.val() === 'true');
				    				}
			    				break;
			    				
			    				case 2:
			    					valid = true;
		    						correctVal = false;
		    						trueCounter++;
			    				break;
			    				
			    				case 3:
			    					valid = true;
		    						correctVal = true;
		    						trueCounter++;
			    				break;
			    				case 5:
			    					valid = true;
		    						correctVal = false;
		    						trueCounter++;
			    				break;
			    				
			    				case 6:
			    					correct = A.one('input[name=<portlet:namespace />correct_'+id+'Checkbox]');
					    			correctVal = (correct != null && correct._node.checked);
					    			if (correct == null) {
					    				correct = A.one('input[name=<portlet:namespace />correct_'+id+']');
					    				correctVal = (correct != null && correct.val() === 'true');
					    			}
					    			trueCounter++;
			    				case 7:
			    					correct = A.one('input[name=<portlet:namespace />correct_'+id+'Checkbox]');
					    			correctVal = (correct != null && correct._node.checked);
					    			if (correct == null) {
					    				correct = A.one('input[name=<portlet:namespace />correct_'+id+']');
					    				correctVal = (correct != null && correct.val() === 'true');
					    			}
					    			trueCounter++;
			    				default:
			    					correct = A.one('input[name=<portlet:namespace />correct_'+id+'Checkbox]');
			    				correctVal = (correct != null && correct._node.checked);
			    				if(correctVal==true)trueCounter++;
			    				if (correct == null) {
			    					correct =document.getElementById('input[name=<portlet:namespace />correct_'+id+']');
			    					correctVal = (correct != null && correct.val() === 'true');
			    				}
		    				
		    					break;
			    			
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
		    			if(trueCounter==0)valid = false;
			    		
			    		//Ningun feedback > 300 caracteres
			    		if(valid && typeId!=5){
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
	</script>

	<%
	if(learningActivity.getTypeId()!=4){ %>
	
		<aui:field-wrapper label="answers" helpMessage="<%=qt.getDescription(themeDisplay.getLocale()) %>" /><%

	 } %>
	<liferay-ui:error key="answer-test-required" message="answer-test-required"/>
	<jsp:include page="<%=(qt!=null)?qt.getURLEdit():\"\" %>"/>
    <aui:button-row>
    	<div id="addAnswerButton">
    		<span class="newitem2">
				<a href="#" class="newitem2" onclick="<portlet:namespace />addNode();"><liferay-ui:message key="add-answer"/></a>
			</span>
		</div>
		<div class="buttons_content">
		<aui:button type="submit" onClick='<%= "return validateFields(event);" %>'/>
		<liferay-util:include page="/html/questions/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
		</div>
	</aui:button-row>
</aui:form>
<script >
	AUI().ready('aui-base',
	   	function() {
	    	<portlet:namespace />checkAddAnswerButtonVisibility();
	    	<portlet:namespace />checkMinAnswersNo();
	   	}
	);
</script>
