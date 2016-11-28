<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>

<%@ include file="/init.jsp" %>
<%
	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
	request.setAttribute("activity", learningActivity);
	PortletURL backUrl = renderResponse.createRenderURL();
	backUrl.setParameter("resId", String.valueOf(learningActivity.getActId()));
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
	    		required: Liferay.Language.get("execactivity.editquestions.newquestion.error.text.required")
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

<portlet:actionURL var="addquestionURL" name="addquestion" />

<aui:form name="qfm" action="<%=addquestionURL%>"  method="post">
	<aui:input name="resId" type="hidden" value="<%= learningActivity.getActId()%>"></aui:input>
	
	
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
	
	
	
		<aui:input name="isFree" label="texto_libre" type="checkbox" value="true"></aui:input>
	
	
		<liferay-ui:input-editor name="text" width="80%" onChangeMethod="onChangeText" />
		<script type="text/javascript">
	        function <portlet:namespace />initEditor() 
	        { 
	        	
	            return ""; 
	        }
	    </script>
	</aui:field-wrapper>
	
	<div id="<portlet:namespace />textError" class="<%=(SessionErrors.contains(renderRequest, "surveyactivity.editquestions.newquestion.error.test.required"))?
   														"portlet-msg-error":StringPool.BLANK %>">
   	<%=(SessionErrors.contains(renderRequest, "surveyactivity.editquestions.newquestion.error.test.required"))?
   			LanguageUtil.get(pageContext,"surveyactivity.editquestions.newquestion.error.test.required"):StringPool.BLANK %>
	</div>

	<aui:button-row>
		<aui:button type="submit"/>
		<liferay-util:include page="/html/surveyactivity/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
	</aui:button-row>
</aui:form>