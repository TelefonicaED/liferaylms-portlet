<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@ include file="/init.jsp" %>
<c:if test="<%=Validator.isNull(ParamUtil.getLong(renderRequest, \"courseId\")) %>">
<% 
	long defaultEvaluations =  GetterUtil.getLong(PropsUtil.get("lms.course.default.evaluations"));
	boolean numOfEvaluationsRequired = SessionErrors.contains(renderRequest, "num-of-evaluations-required");
	boolean numOfEvaluationsNumber = SessionErrors.contains(renderRequest, "num-of-evaluations-number");
%>
	<aui:input name="numOfEvaluations" label="num-of-evaluations" 
			type="text" 
			value="<%=defaultEvaluations %>" helpMessage="<%=LanguageUtil.get(pageContext,\"num-of-evaluations-help\")%>" >
	</aui:input>

	<c:if test="<%=numOfEvaluationsRequired||numOfEvaluationsNumber %>">
		<script type="text/javascript">
		<!--
		AUI().ready(function(A){
			var lastMessageError = A.one('#p_p_id<portlet:namespace /> * .portlet-msg-error:last-of-type');
			<c:if test="<%=numOfEvaluationsNumber %>">
				lastMessageError.placeAfter('<div class=\'portlet-msg-error\' ><%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "num-of-evaluations-required")) %></div>'); 
			</c:if>
			<c:if test="<%=numOfEvaluationsRequired %>">
				lastMessageError.placeAfter('<div class=\'portlet-msg-error\' ><%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "num-of-evaluations-number")) %></div>'); 
			</c:if>
		});
		//-->
		</script>
	</c:if>
	
	<aui:script use="liferay-form">
		Liferay.Form.register(
			{
				id: '<portlet:namespace />fm',
				fieldRules: [
					{
						fieldName: '<portlet:namespace />numOfEvaluations',
						validatorName: 'required',
						errorMessage: '<liferay-ui:message key="num-of-evaluations-required" />'
					},
					{
						fieldName: '<portlet:namespace />numOfEvaluations',
						validatorName: 'number',
						errorMessage: '<liferay-ui:message key="num-of-evaluations-number" />'
					},
				]
			}
		);

	</aui:script>
	
</c:if>
