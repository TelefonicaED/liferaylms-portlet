<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@ include file="/init.jsp" %>
<%

LearningActivityResult result = null;
LearningActivity activity = null;

if((renderRequest.getParameter("actId")!=null)&&(renderRequest.getParameter("studentId")!=null))
{
	result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(ParamUtil.getLong(renderRequest,"actId"), ParamUtil.getLong(renderRequest,"studentId"));
	activity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(renderRequest,"actId"));
}


%>

<aui:form  name="fn_grades" method="post" >
	<aui:fieldset>
		<aui:input type="hidden" name="studentId" value='<%=renderRequest.getParameter("studentId") %>' />
	    <aui:input type="text" name="result" helpMessage="<%=LanguageUtil.format(pageContext, \"offlinetaskactivity.grades.resultMessage\", new Object[]{activity.getPasspuntuation()})%>" label="offlinetaskactivity.grades" value='<%=((result!=null)&&(result.getEndDate()!=null))?Long.toString(result.getResult()):"" %>' />
	    <div id="<portlet:namespace />resultError" class="<%=(SessionErrors.contains(renderRequest, "offlinetaskactivity.grades.result-bad-format"))?
	    														"portlet-msg-error":StringPool.BLANK %>">
	    	<%=(SessionErrors.contains(renderRequest, "offlinetaskactivity.grades.result-bad-format"))?
	    			LanguageUtil.get(pageContext,"offlinetaskactivity.grades.result-bad-format"):StringPool.BLANK %>
	    </div>

		<aui:input type="textarea"  helpMessage="<%=LanguageUtil.get(pageContext, \"offlinetaskactivity.grades.commentsMessage\")%>"  maxLength="350" rows="3" name="comments" label="offlinetaskactivity.comments" value='<%=((result!=null)&&(result.getComments()!=null))?result.getComments():"" %>'>
			<aui:validator name="range">[0, 350]</aui:validator>
		</aui:input>
	</aui:fieldset>
	<aui:button-row>
	<button name="Save" value="save" onclick="<portlet:namespace />doSaveGrades();" type="button">
		<liferay-ui:message key="offlinetaskactivity.save" />
	</button>
	<button name="Close" value="close" onclick="<portlet:namespace />doClosePopupGrades();" type="button">
		<liferay-ui:message key="offlinetaskactivity.cancel" />
	</button>
	</aui:button-row>
	<liferay-ui:success key="offlinetaskactivity.grades.updating" message="offlinetaskactivity.correct.saved" />
	<liferay-ui:error key="offlinetaskactivity.grades.bad-updating" message="offlinetaskactivity.grades.bad-updating" />
</aui:form>
<script type="text/javascript">
<!--
function createValidator(){

	AUI().use('node-base' ,'aui-form-validator', function(A) {
	
		A.mix(
				YUI.AUI.defaults.FormValidator.STRINGS,
				{
					required:'<%=JavaScriptUtil.markupToStringLiteral(LanguageUtil.get(pageContext, "offlinetaskactivity.grades.result-bad-format")) %>',
					resultRule: '<%=JavaScriptUtil.markupToStringLiteral(LanguageUtil.get(pageContext, "offlinetaskactivity.grades.result-bad-format")) %>'
				},
				true
			);
		
		A.mix(
			YUI.AUI.defaults.FormValidator.RULES,
			{
				resultRule: function(val, fieldNode, ruleValue) {
					return YUI.AUI.defaults.FormValidator.REGEX.digits.test(val) && val<=100;
				}		
			},
			true
		);
	
	
		window.<portlet:namespace />validator = new A.FormValidator({
			boundingBox: '#<portlet:namespace />fn_grades',
			validateOnBlur: true,
			validateOnInput: true,
			selectText: true,
			showMessages: false,
			containerErrorClass: '',
			errorClass: '',
			rules: {			
				<portlet:namespace />result: {
					required: true,
					resultRule: true
				}
			},
	
	        fieldStrings: {
	        	<portlet:namespace />result: {
	        		required:'<%=JavaScriptUtil.markupToStringLiteral(LanguageUtil.get(pageContext, "offlinetaskactivity.grades.result-bad-format")) %>',
	        		resultRule: '<%=JavaScriptUtil.markupToStringLiteral(LanguageUtil.get(pageContext, "offlinetaskactivity.grades.result-bad-format")) %>'
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
		
	});

}

createValidator();
//-->
</script>