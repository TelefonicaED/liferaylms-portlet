
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.impl.TestQuestionLocalServiceImpl"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.FreetextQuestionType"%>
<%@ include file="/init.jsp" %>

<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>

<%

Long actId = (Long)request.getAttribute("actId");
LearningActivity learningActivity = (LearningActivity)request.getAttribute("learningActivity");
LearningActivityTry learnTry = (LearningActivityTry)request.getAttribute("larntry");
long oldResults= ParamUtil.get(request, "oldResult", -1);
boolean hasFreeQuestion = (Boolean) request.getAttribute("hasFreeQuestion");
LearningActivityResult learnResult = 
	LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId,themeDisplay.getUserId());
String actFeedback=learningActivity.getFeedbackNoCorrect();
String title = "";
%>
<style type="text/css">
	#simplemodal-container{width:500px;height:280px;}
</style>
 
<%
boolean isP2P=false;
if(learnResult != null){
if(learnResult.getPassed())
{
	actFeedback=learningActivity.getFeedbackCorrect();
	if(learningActivity.getTypeId()==3){
		title = LanguageUtil.get(pageContext,"p2ptask-superada");
		isP2P=true;
	}else{
		title = LanguageUtil.get(pageContext,"test-superado");
	}

%>
<div id="activityResult" style="display:none">
<h1><%=title %></h1>
<div id="actfeedback"><%=actFeedback %></div>
<div id="score" style='<%=(learningActivity.getTypeId()!=0)? "display:none":""%>'>
	<b><liferay-ui:message key="shared-you-guess" /> <%=learnResult.translateResult(themeDisplay.getLocale())%> <liferay-ui:message key="shared-in-tarea" /></b>
</div>
<%--div class="negrita color_principal"><liferay-ui:message key="shared-guess-badget" /></div--%>
<!-- <a class="button">Ver revisi&oacute;n</a>-->
<div style='<%=(true)? "display:none":""%>'>
	<aui:button-row>
		<aui:button name="cancelButton" type="button" value="acept" onclick="$('#activityResult').dialog('close')"/>
	</aui:button-row>
</div>
</div>
<script type="text/javascript">
<!--
$('#activityResult').dialog();
//-->
</script>
<%
}
else
{
	actFeedback=learningActivity.getFeedbackNoCorrect();
	if(learningActivity.getTypeId()==3){
		title = LanguageUtil.get(pageContext,"p2ptask-nosuperada");
	}else{		
		if(hasFreeQuestion){
			title = LanguageUtil.get(pageContext,"freetext.result.title");
		}else{
			title = LanguageUtil.get(pageContext,"test-nosuperado");

		}
	}

%>
<div id="activityResult" style="display:none">
<h1><%=title %></h1>

<%
		if(hasFreeQuestion){%>
			<b><liferay-ui:message key="freetext.result.body" /></b>
			<%}else{%>
			
	<div id="actfeedback"><%=actFeedback %></div>
	<div id="score" style='<%=(learningActivity.getTypeId()!=0)? "display:none":""%>'>
	<b><liferay-ui:message key="shared-you-guess" /> <%=learnTry.translateResult(themeDisplay.getLocale())%> <liferay-ui:message key="shared-in-tarea" /></b>
	
	
			<%
			
			CalificationType clasificationType = new CalificationTypeRegistry().getCalificationType(CourseLocalServiceUtil.fetchByGroupCreatedId(learningActivity.getGroupId()).getCalificationType());
			
			String arguments = clasificationType.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), oldResults);
			if(!hasFreeQuestion && oldResults>0){
					if(oldResults<learnTry.getResult()){
		%>
				<p><b><liferay-ui:message key="execActivity.improve.result" arguments="<%=new Object[]{arguments} %>" /></b></p>
<%		
			}else{
%>
				<p><b><liferay-ui:message key="execActivity.not.improve.result" arguments="<%=new Object[]{arguments} %>" /></b></p>
<%			
			}
		}	
	
		}%>

	
</div>
<!--  <a class="button">Ver revisi&oacute;n</a>-->
<div style='<%=(true)? "display:none":""%>'>
	<aui:button-row>
		<aui:button name="cancelButton" type="button" value="acept" onclick="$('#activityResult').dialog('close')"/>
	</aui:button-row>
</div>
</div>
<script type="text/javascript">
<!--
$('#activityResult').dialog();
//-->
</script>
<%
}
}
 %>