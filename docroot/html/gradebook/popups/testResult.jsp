<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalService"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>


<%@ include file="/init.jsp" %>

<liferay-ui:panel-container >
<%
long actId = ParamUtil.getLong(request,"actId",0 );

if(actId == 0){
	

} else {
	
	long userId = ParamUtil.getLong(request,"studentId",0 );

	LearningActivity learningActivity=LearningActivityLocalServiceUtil.getLearningActivity(actId);	
	User owner = UserLocalServiceUtil.getUser(userId);
	%>
	<h2 class="description-title"><%=learningActivity.getTitle(themeDisplay.getLocale()) %></h2>
	<h3 class="description-h3"><liferay-ui:message key="description" /></h3>
	<div class="description"><%=learningActivity.getDescription(themeDisplay.getLocale()) %></div>
	<p class="sub-title"><liferay-ui:message key="p2ptask-done-by" /> <%=owner.getFullName()%></p>
	<%
	
	List<LearningActivityTry> triesList = LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUser(actId, userId);
	for(LearningActivityTry larntry:triesList){
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(timeZone);
		String fecha ="";
		if(larntry.getEndDate()!=null){
			fecha = sdf.format(larntry.getEndDate());
		}
		else{ //just in case of the learningActivity hasn't got end date
			fecha = sdf.format(ModuleLocalServiceUtil.getModule(LearningActivityLocalServiceUtil.getLearningActivity(larntry.getActId()).getModuleId()).getEndDate());
		}
		String title = learningActivity.getTitle(themeDisplay.getLocale())  + " (" + fecha + ")";
		%>
		<liferay-ui:panel id="<%=Long.toString(larntry.getLatId()) %>" title="<%=title %>" collapsible="true" extended="true" defaultState="collapsed">
		
		<%
		long tries = learningActivity.getTries();
		long userTries = Long.valueOf(LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(learningActivity.getActId(), userId));
		
		LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), userId);
		
		boolean userPassed=false;
		long oldResult=-1;
		if(result != null)
			userPassed = result.getPassed();
		else{
			userPassed=learningActivity.getPasspuntuation()<=larntry.getResult();
			oldResult=(Long)request.getAttribute("oldResult");
		%>
		<jsp:include page="/html/shared/popResult.jsp" />
		<h2><%=learningActivity.getTitle(themeDisplay.getLocale()) %></h2>
		<%} %>
		<p><liferay-ui:message key="test-done" /></p>
		<liferay-util:include page="/html/execactivity/test/timeout.jsp" servletContext="<%=this.getServletContext() %>">
			<liferay-util:param value="<%=Long.toString(learningActivity.getActId()) %>" name="actId"/>
		</liferay-util:include> 
		<p><liferay-ui:message key="your-result" arguments="<%=new Object[]{larntry.getResult()} %>" /></p>
		
		<% 	if(oldResult>0){
				if(oldResult<larntry.getResult()){
		%>
					<p><liferay-ui:message key="execActivity.improve.result" arguments="<%=new Object[]{oldResult} %>" /></p>
		<%		
				}else {
		%>
					<p><liferay-ui:message key="execActivity.not.improve.result" arguments="<%=new Object[]{oldResult} %>" /></p>
		<%		}
			}%>
		
		<%if(userPassed){%>
			<p class="color_tercero negrita"><liferay-ui:message key="your-result-pass" /></p>
		<%}else{%>	
			<p class="color_tercero negrita"><liferay-ui:message key="your-result-dont-pass"  arguments="<%=new Object[]{learningActivity.getPasspuntuation()} %>" /></p>
		<% }
		if(tries>0 && userTries >= tries )	{
		%><p class="color_tercero"><liferay-ui:message key="your-result-no-more-tries" /></p><%
		}%>
		<p class="negrita"><liferay-ui:message key="your-answers" /></p>
	
		<% List<TestQuestion> questions=null;
		
		if (GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"random"))==0){
			questions=TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
		}
		else{
			questions= new ArrayList<TestQuestion>();
			Iterator<Element> nodeItr = SAXReaderUtil.read(larntry.getTryResultData()).getRootElement().elementIterator();
			while(nodeItr.hasNext()) {
				Element element = nodeItr.next();
		         if("question".equals(element.getName())) {
						questions.add(TestQuestionLocalServiceUtil.getTestQuestion(Long.valueOf(element.attributeValue("id"))));
		         }
		    }	
		}
		
		for(TestQuestion question:questions){
			QuestionType qt = new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
			qt.setLocale(themeDisplay.getLocale());
			if(Validator.isNotNull(larntry.getTryResultData())){
			%><%=qt.getHtmlFeedback(SAXReaderUtil.read(larntry.getTryResultData()), question.getQuestionId(), themeDisplay)%><%
			}
		}
		
		if(tries==0 || userTries < tries ||permissionChecker.hasPermission(learningActivity.getGroupId(),LearningActivity.class.getName(),learningActivity.getActId(), ActionKeys.UPDATE)) {
			if(LearningActivityResultLocalServiceUtil.userPassed(learningActivity.getActId(),userId)){
				if(result.getResult()<100){
					if(tries>0){%>
						<p class="negrita"><liferay-ui:message key="execativity.test.try.count" arguments="<%=new Object[]{userTries,tries} %>" /></p>
					<% }	
				}
			}
		}%>
		</liferay-ui:panel>
	<%}

}%>
</liferay-ui:panel-container>

