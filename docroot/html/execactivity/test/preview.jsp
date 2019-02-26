<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.OrderFactoryUtil"%>
<%@page
	import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page
	import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page
	import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page
	import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp"%>

<%
	long actId=ParamUtil.getLong(request,"actId",0);
	long userId = themeDisplay.getUserId();
	boolean improve =ParamUtil.getBoolean(request, "improve",false);

	Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());

	if(actId==0) renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	else if(LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId()) && !improve){	
		request.setAttribute("learningActivity",LearningActivityLocalServiceUtil.getLearningActivity(actId));
		request.setAttribute("larntry",(LearningActivityTry) LearningActivityTryLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(LearningActivityTry.class)
			.add(PropertyFactoryUtil.forName("actId").eq(actId))
			.add(PropertyFactoryUtil.forName("userId").eq(userId))
			.add(PropertyFactoryUtil.forName("endDate").isNotNull())
			.addOrder(OrderFactoryUtil.desc("result"))
			.addOrder(OrderFactoryUtil.asc("endDate")),0,1).get(0));
%>
<liferay-util:include page="/html/execactivity/test/results.jsp"
	servletContext="<%=this.getServletContext() %>">
	<liferay-util:param value="<%=Long.toString(actId) %>" name="actId" />
</liferay-util:include>
<% 
	}else{
%>
<div class="execactivity preview">
	<%
		LearningActivity activity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		long typeId=activity.getTypeId();
		
		if((typeId==0 && (LearningActivityTryLocalServiceUtil.canUserDoANewTry(actId, userId)) 
			|| permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),actId, ActionKeys.UPDATE)) || improve){
			
			boolean isTeacher=permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(), "VIEW_RESULTS");
			
				if (isTeacher) {
					String popupcorrection = "javascript:" + renderResponse.getNamespace()
							+ "showPopupGetReport();";
	%>
	<portlet:renderURL var="goToCorrection">
		<portlet:param name="jspPage"
			value="/html/execactivity/test/correction.jsp" />
		<portlet:param name="actId"
			value="<%=Long.toString(activity.getActId())%>" />
		<portlet:param name="courseId"
			value="<%=Long.toString(course.getCourseId())%>" />
	</portlet:renderURL>
	<aui:button name="importButton" type="button" value="action.CORRECT"
		last="true" href="<%=goToCorrection.toString()%>"></aui:button>

	<%}
				
				int tries = LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(actId, themeDisplay.getUserId());
				Object  [] arguments =  new Object[]{tries,activity.getTries()};
				Object  [] arguments2 =  new Object[]{activity.getPasspuntuation()};
%>
	<h2><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
	<p>
		<liferay-ui:message key="execativity.test.try.notification" />
	</p>
	<%
				if(activity.getTries()>0){
%>
	<p class="negrita">
		<liferay-ui:message key="execativity.test.try.count"
			arguments="<%=arguments %>" />
	</p>
	<%
				}
				
				if (activity.getPasspuntuation()>0){ 
%>
	<p>
		<liferay-ui:message key="execativity.test.try.pass.puntuation"
			arguments="<%=arguments2 %>" />
	</p>
	<% 
				} 
%>


	<portlet:renderURL var="correctURL">
		<portlet:param name="actId" value="<%=Long.toString(actId) %>"></portlet:param>
		<portlet:param name="improve" value="true"></portlet:param>
		<portlet:param name="jspPage" value="/html/execactivity/test/view.jsp" />
	</portlet:renderURL>
	<%
				List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actId);
	
	if (questions.size()>0 ){
		if(tries< activity.getTries() || activity.getTries() == 0){
%>

	<p class="color_tercero textcenter negrita">
		<liferay-ui:message key="execativity.test.try.confirmation" />
	</p>

	<aui:form class="buttons_container" name="formulario"
		action="<%=correctURL %>" method="POST" role="form">
		<aui:button-row>
			<aui:button class="floatr" type="submit"
				value="<%=LanguageUtil.get(pageContext,\"execativity.test.try.start\")%>" />
		</aui:button-row>
	</aui:form>
	<%}
	}else{
%>
	<p class="negrita">
		<liferay-ui:message key="execativity.test.no.question" />
	</p>
	<% 
				}
			}else if(typeId==0 && permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK")){	
%>
	<h2><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
	<liferay-util:include page="/html/execactivity/test/view.jsp"
		servletContext="<%=this.getServletContext() %>">
		<liferay-util:param value="<%=Long.toString(actId) %>" name="actId" />
	</liferay-util:include>
	<%
			}else{
				List<LearningActivityTry> larntry = LearningActivityTryLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(LearningActivityTry.class)
					.add(PropertyFactoryUtil.forName("actId").eq(actId))
					.add(PropertyFactoryUtil.forName("userId").eq(userId))
					.add(PropertyFactoryUtil.forName("endDate").isNotNull())
					.addOrder(OrderFactoryUtil.desc("result"))
					.addOrder(OrderFactoryUtil.asc("endDate")),0,1);
			
				if(!larntry.isEmpty()) 
					request.setAttribute("larntry",larntry.get(0));
				request.setAttribute("learningActivity",activity);
%>
	<liferay-util:include page="/html/execactivity/test/results.jsp"
		servletContext="<%=this.getServletContext() %>">
		<liferay-util:param value="<%=Long.toString(actId) %>" name="actId" />
	</liferay-util:include>
	<% 
			}
%>
</div>
<%
	}
%>
