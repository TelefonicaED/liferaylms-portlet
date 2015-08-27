<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@ include file="/init.jsp" %>
<%

LearningActivityResult result = null;

String actId2 = renderRequest.getParameter("actId");
String studentId2 = renderRequest.getParameter("studentId");

if((actId2!=null)&&(studentId2!=null)){
	//System.out.println("actId2: " + actId2);
	//System.out.println("studentId2: " + studentId2);
	Long lactId2 = ParamUtil.getLong(renderRequest,"actId");
	Long lstudentId2 = ParamUtil.getLong(renderRequest,"studentId");
	result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(lactId2, lstudentId2);
}


%>

<aui:form  name="fn_grades" method="post" >
	<aui:fieldset>
		<aui:input type="hidden" name="studentId" value='<%=studentId2%>' />
		<aui:input type="hidden" name="actId" value='<%=actId2%>' />
	    <aui:input type="text" name="result" label="offlinetaskactivity.grades" value='<%=((result!=null)&&(result.getResult()>0))?Long.toString(result.getResult()):"" %>' />
	    <liferay-ui:error key="offlinetaskactivity.grades.result-bad-format" message="offlinetaskactivity.grades.result-bad-format" />
		<aui:input type="textarea" cols="40" rows="2" name="comments" label="offlinetaskactivity.comments" value='<%=((result!=null)&&(result.getComments()!=null))?result.getComments():"" %>'/>
	</aui:fieldset>
	<aui:button-row>
	<button name="Save" value="save" onclick="<portlet:namespace />doSaveGrades(<%=studentId2%>,<%=actId2%>);" type="button">
		<liferay-ui:message key="offlinetaskactivity.save" />
	</button>
	<button name="Close" value="close" onclick="<portlet:namespace />doClosePopupGrades();" type="button">
		<liferay-ui:message key="offlinetaskactivity.cancel" />
	</button>
	</aui:button-row>
	<liferay-ui:success key="offlinetaskactivity.grades.updating" message="offlinetaskactivity.correct.saved" />
	<liferay-ui:error key="offlinetaskactivity.grades.bad-updating" message="offlinetaskactivity.grades.bad-updating" />
</aui:form>