<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
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
<%
	Boolean isTablet = ParamUtil.getBoolean(renderRequest, "isTablet", false);

	if(ParamUtil.getLong(request,"actId",0 ) == 0){
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
	else{
%>
	<div class="container-activity isFeedback">
<%	
		Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		boolean hasFreeQuestion = false;
		boolean hasPermissionAccessCourseFinished = LiferaylmsUtil.hasPermissionAccessCourseFinished(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), course.getCourseId(), themeDisplay.getUserId());

		LearningActivity learningActivity=(LearningActivity)request.getAttribute("learningActivity");
		if(learningActivity==null){
			learningActivity=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"actId" ));	
		}
		LearningActivity bankActivity = learningActivity;
		boolean useBank = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(), "isBank"));
		LearningActivityTry larntry=(LearningActivityTry)request.getAttribute("larntry");
		if(!hasPermissionAccessCourseFinished){
			if(larntry==null) larntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(ParamUtil.getLong(request,"latId" ));
			
			if(larntry.getActId() == learningActivity.getActId()){
				request.setAttribute("larntry",larntry);
			}else{
				larntry=LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(ParamUtil.getLong(request,"actId",0 ), learningActivity.getUserId());
				request.setAttribute("larntry",larntry);
			}
			
			if( useBank && Validator.isNotNull(larntry) && Validator.isXml(larntry.getTryResultData()) ){
				String tryResultData = larntry.getTryResultData();
				Document docQuestions = SAXReaderUtil.read(tryResultData);
				List<Element> xmlQuestions = docQuestions.getRootElement().elements("question");
				String questionIdString = xmlQuestions.get(0).attributeValue("id");
				Long questionId = Long.valueOf(questionIdString);
				TestQuestion testQuestion = TestQuestionLocalServiceUtil.getTestQuestion(questionId);
				bankActivity = LearningActivityLocalServiceUtil.getLearningActivity(testQuestion.getActId());
			}
		}
%>
		<h2 class="description-title"><%=bankActivity.getTitle(themeDisplay.getLocale()) %></h2>
		<div class="description">
			<%=bankActivity.getDescriptionFiltered(themeDisplay.getLocale(),true) %>
		</div>
<%
		request.setAttribute("actId",learningActivity.getActId());
		request.setAttribute("learningActivity",learningActivity);
		
		List<TestQuestion> questionList = TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
		
		Iterator<TestQuestion> questionListIt = questionList.iterator();
		while(questionListIt.hasNext()){
			TestQuestion q = questionListIt.next();
			if(q.getQuestionType() == 2){
				hasFreeQuestion = true;
				break;
			}
		}
		
		boolean isTeacher=permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(), "VIEW_RESULTS");
	
		
		CalificationType ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());
		
	
	
		
		long tries = 0;
		long userTries = 0;
		long score = 0;
		long scoreTry = 0;
		String tryResultData = null;
		
		if(!hasPermissionAccessCourseFinished){
			request.setAttribute("hasFreeQuestion", hasFreeQuestion);	
			tries = learningActivity.getTries();
			userTries = Long.valueOf(LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(learningActivity.getActId(),themeDisplay.getUserId()));
		
			LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), themeDisplay.getUserId());
			score = result.getResult();
			scoreTry = larntry.getResult();
			tryResultData = larntry.getTryResultData();
		}else{
			score = ParamUtil.getLong(request, "score", 0);
			scoreTry = score;
			tryResultData = ParamUtil.getString(request, "tryResultData", "");
		}
		boolean userPassed=false;
		boolean hideFeedback = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"hideFeedback"));
		
		boolean comesFromCorrection = ParamUtil.get(request, "correction", false);
		long oldResult= ParamUtil.get(request, "oldResult", -1);
		if(!comesFromCorrection) {
			userPassed=LearningActivityResultLocalServiceUtil.userPassed(learningActivity.getActId(),themeDisplay.getUserId());
		}else if(!hasPermissionAccessCourseFinished){
			userPassed=learningActivity.getPasspuntuation()<=scoreTry;
			//Cuando estamos mejorando la nota no mostramos el popup.
			//if(oldResult <= 0){
			if(!hideFeedback)
			{
				%>
				<jsp:include page="/html/shared/popResult.jsp" />
				<%
			}				//}
				%>
							<h2><%=learningActivity.getTitle(themeDisplay.getLocale()) %></h2>
				<% 
		}
		%>
		
		<p><liferay-ui:message key="test-done" /></p>
		<liferay-util:include page="/html/execactivity/test/timeout.jsp" servletContext="<%=this.getServletContext() %>">
			<liferay-util:param value="<%=Long.toString(learningActivity.getActId()) %>" name="actId"/>
		</liferay-util:include> 
		
		<%
		if(hasFreeQuestion){%>
			<!-- <p>Respuesta libre</p> -->
			
		<% }else{%>
			<p><liferay-ui:message key="your-result" arguments="<%=new Object[]{ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), scoreTry)} %>" /></p>
			
		<%}%>
	
<% 
		if(!hasFreeQuestion && oldResult>0){
			if(oldResult<scoreTry){
%>
				<p><liferay-ui:message key="execActivity.improve.result" arguments="<%=new Object[]{oldResult} %>" /></p>
<%		
			}else{
%>
				<p><liferay-ui:message key="execActivity.not.improve.result" arguments="<%=new Object[]{oldResult} %>" /></p>
<%			
			}
		}	
	
		if(userPassed){
%>
			<p class="color_tercero negrita"><liferay-ui:message key="your-result-pass" /></p>
<%
		}else{
			if(!hasFreeQuestion){
				%>			
				<p class="color_tercero negrita"><liferay-ui:message key="your-result-dont-pass"  arguments="<%=new Object[]{ct.translate(themeDisplay.getLocale(), themeDisplay.getScopeGroupId(), learningActivity.getPasspuntuation())} %>" /></p>
	<% 
			}
		}
		
		if(tries>0 && userTries >= tries ){
%>
			<p class="color_tercero"><liferay-ui:message key="your-result-no-more-tries" /></p>
<%
		}
		if(!hideFeedback)
		{
%>
			<p class="negrita"><liferay-ui:message key="your-answers" /></p>
<%
			long actId = learningActivity.getActId();
			long userId = themeDisplay.getUserId();
			List<TestQuestion> questions=null;
			if( StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "isBank")) ){
				questions = TestQuestionLocalServiceUtil.getQuestions(bankActivity.getActId());
			}else{
				if( GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(actId,"random"))==0 
						|| hasPermissionAccessCourseFinished )
					questions=TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
				else{
					questions= new ArrayList<TestQuestion>();
					Iterator<Element> nodeItr = SAXReaderUtil.read(tryResultData).getRootElement().elementIterator();
					TestQuestion question=null;
					while(nodeItr.hasNext()) {
						Element element = nodeItr.next();				
						 if("question".equals(element.getName())) {
							 question=TestQuestionLocalServiceUtil.fetchTestQuestion(Long.valueOf(element.attributeValue("id")));
							 if(question != null){
								 questions.add(question); 
							 }		        	 
						 }
					}	
				}
			}

			
			for(TestQuestion question:questions){
				QuestionType qt = new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
				qt.setLocale(themeDisplay.getLocale());
				%><%=qt.getHtmlFeedback(SAXReaderUtil.read(tryResultData), question.getQuestionId(), learningActivity.getActId(), themeDisplay)%><%
			}
		}
		if(tries==0 || userTries < tries ||permissionChecker.hasPermission(learningActivity.getGroupId(),LearningActivity.class.getName(),learningActivity.getActId(), ActionKeys.UPDATE)) {
			if(!LearningActivityResultLocalServiceUtil.userPassed(learningActivity.getActId(),themeDisplay.getUserId())){
				if(tries>0){	
%>
					<liferay-portlet:renderURL var="realizar">
						<liferay-portlet:param name="actId" value="<%=Long.toString(learningActivity.getActId()) %>"></liferay-portlet:param>
						<liferay-portlet:param name="jspPage" value="/html/execactivity/test/preview.jsp" />
					</liferay-portlet:renderURL>
<%
					String enlace="self.location='"+realizar.toString()+"'"; 
%>
					<aui:button name="repetir" value="<%=LanguageUtil.get(pageContext,\"execativity.test.try.again\")%>" onClick="<%=enlace %>"></aui:button>
<%
				}else{	
%>
					<liferay-portlet:renderURL var="realizardir">
						<liferay-portlet:param name="actId" value="<%=Long.toString(learningActivity.getActId()) %>"></liferay-portlet:param>
						<%if(isTablet){%>
							<liferay-portlet:param name="isTablet" value="<%=Boolean.toString(isTablet) %>"></liferay-portlet:param>

						<%} %>
						<liferay-portlet:param name="jspPage" value="/html/execactivity/test/view.jsp" />
					</liferay-portlet:renderURL>
<%	
					String enlace = "self.location='"+realizardir.toString()+"'"; 
					%>
					<aui:button name="repetir" value="<%=LanguageUtil.get(pageContext,\"execativity.test.try.again\")%>" onClick="<%=enlace %>"></aui:button>
<%
				}
			}else{
				if(score<100){
					String improveStr = LearningActivityLocalServiceUtil.getExtraContentValue(ParamUtil.getLong(request,"actId"), "improve");
					if(improveStr.equals("true")){
						if(tries>0){
%>
							<p class="negrita"><liferay-ui:message key="execativity.test.try.count" arguments="<%=new Object[]{userTries,tries} %>" /></p>
							<p class="color_tercero textcenter negrita"><liferay-ui:message key="execativity.test.try.confirmation.again" /></p>
<%
						}
%>
						<liferay-portlet:renderURL var="mejorardir">
							<liferay-portlet:param name="actId" value="<%=Long.toString(learningActivity.getActId()) %>"></liferay-portlet:param>
							<liferay-portlet:param name="improve" value="true"></liferay-portlet:param>
							<liferay-portlet:param name="jspPage" value="/html/execactivity/test/preview.jsp" />
						</liferay-portlet:renderURL>
<%	
						String enlace="self.location='"+mejorardir.toString()+"'"; 
%>
						<aui:button name="repetir" value="<%=LanguageUtil.get(pageContext,\"execativity.test.try.again.improve\")%>" onClick="<%=enlace %>"></aui:button>
<%
					}
				}
			}
		}
%>
	</div>
<%
	}
%>