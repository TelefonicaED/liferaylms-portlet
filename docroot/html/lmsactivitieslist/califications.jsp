<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>

<%@ include file="/init.jsp"%>

<%
	long actId = ParamUtil.getLong(request, "resId", 0);
	LearningActivity learnActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
	
	LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
	LearningActivityType learningActivityType = learningActivityTypeRegistry.getLearningActivityType(learnActivity.getTypeId());
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	dateFormat.setTimeZone(timeZone);
	
	PortletURL portletURL = renderResponse.createRenderURL();
	portletURL.setParameter("jspPage","/html/lmsactivitieslist/califications.jsp");
	portletURL.setParameter("resId",Long.toString(actId));
	

	boolean delete = PrefsPropsUtil.getBoolean("learningactivity.show.califications.delete");
%>

<h2 class="table_title"><%=learnActivity.getTitle(themeDisplay.getLocale()) %></h2>

<portlet:renderURL var="calificacionesURL">
	<portlet:param name="jspPage" value="/html/lmsactivitieslist/califications.jsp"></portlet:param>
</portlet:renderURL>

<portlet:actionURL name="deleteAllURL" var="deleteAllURL">
	<portlet:param name="resId" value="<%=String.valueOf(actId)%>"/>
</portlet:actionURL>

<portlet:actionURL name="deleteURL" var="deleteURL">
	<portlet:param name="resId" value="<%=String.valueOf(actId)%>"/>
</portlet:actionURL>

<c:if test="<%=delete&&learningActivityType.hasDeleteTries() %>">
	<liferay-ui:icon image="close" label="true" message="com.liferay.manager.CleanLearningActivityTriesNotPassed" url="<%= deleteAllURL %>"  />
</c:if>

<%

int num = CourseLocalServiceUtil.getStudentsFromCourse(CourseLocalServiceUtil.getCourseByGroupCreatedId(learnActivity.getGroupId())).size();

if(num<=100){
%>
	<liferay-ui:search-container iteratorURL="<%=portletURL%>" deltaConfigurable="true" emptyResultsMessage="there-are-no-results" delta="10">
	
	   	<liferay-ui:search-container-results>
			<%
				List<User> users = CourseLocalServiceUtil.getStudentsFromCourse(CourseLocalServiceUtil.getCourseByGroupCreatedId(learnActivity.getGroupId()));
	
				pageContext.setAttribute("results", users.subList(searchContainer.getStart(),users.size()<searchContainer.getEnd()?users.size():searchContainer.getEnd()));
			    pageContext.setAttribute("total", num);
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="result">
			<%
				LearningActivityResult lar = null;
				try{
					lar = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, result.getUserId());
				}catch(Exception e){}
				
				if(lar==null){

					String res = LanguageUtil.get(themeDisplay.getLocale(),"activity.showcalifications.notsubmitted");
					
			%>
				<liferay-ui:search-container-column-text name="user"><%=result.getFullName() %> </liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="activity.showcalifications.puntuation"> <%=res %> </liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="activity.showcalifications.result">		<%=res %> </liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="activity.showcalifications.startdate"> <%=res %> </liferay-ui:search-container-column-text>
			
				<c:if test="<%=delete&&learningActivityType.hasDeleteTries() %>">
					<liferay-ui:search-container-column-text name="actions"><liferay-ui:icon image="close" label="true" message="com.liferay.manager.CleanLearningActivityTries" url='<%= deleteURL+"&userId="+result.getUserId() %>'  /></liferay-ui:search-container-column-text>
				</c:if>
			<%
				}else{
					
					String puntuation = String.valueOf(lar.getResult());
					String res = lar.getPassed()?LanguageUtil.get(themeDisplay.getLocale(),"passed"):LanguageUtil.get(themeDisplay.getLocale(),"not-passed");
					String startdate = (lar.getStartDate()!=null)?dateFormat.format(lar.getStartDate()):"";
					
					//Si es de tipo test y no se ha entregado
					if(learnActivity.getTypeId() == 0 && lar.getEndDate() == null){
						puntuation = LanguageUtil.get(themeDisplay.getLocale(),"activity.showcalifications.notsubmitted");
						res = LanguageUtil.get(themeDisplay.getLocale(),"activity.showcalifications.notsubmitted");
						startdate = LanguageUtil.get(themeDisplay.getLocale(),"activity.showcalifications.notsubmitted");
					}
			%>
				<liferay-ui:search-container-column-text name="user"><%=result.getFullName() %> </liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="activity.showcalifications.puntuation">	<%=puntuation %> </liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="activity.showcalifications.result">		<%=res %> </liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="activity.showcalifications.startdate">	<%=startdate %> </liferay-ui:search-container-column-text>
			
				<c:if test="<%=delete&&learningActivityType.hasDeleteTries() %>">
					<liferay-ui:search-container-column-text name="actions"><liferay-ui:icon image="close" label="true" message="com.liferay.manager.CleanLearningActivityTries" url='<%= deleteURL+"&userId="+result.getUserId() %>'  /></liferay-ui:search-container-column-text>
				</c:if>
			<%
				}
			%>
		</liferay-ui:search-container-row>
	 	<liferay-ui:search-iterator />
	</liferay-ui:search-container>
	
<%}else{ %>
	<liferay-ui:search-container iteratorURL="<%=portletURL%>" deltaConfigurable="true" emptyResultsMessage="there-are-no-results" delta="10">
	
	   	<liferay-ui:search-container-results>
			<%
				DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LearningActivityResult.class).add(PropertyFactoryUtil.forName("actId").eq(actId));
	
				pageContext.setAttribute("results", LearningActivityResultLocalServiceUtil.dynamicQuery(dynamicQuery,searchContainer.getStart(),searchContainer.getEnd()));
			    pageContext.setAttribute("total", (int)LearningActivityResultLocalServiceUtil.dynamicQueryCount(DynamicQueryFactoryUtil.forClass(LearningActivityResult.class).add(PropertyFactoryUtil.forName("actId").eq(actId))));
	
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.lms.model.LearningActivityResult" keyProperty="larId" modelVar="result">
			
			<%
				User usu = UserLocalServiceUtil.getUser(result.getUserId());
				
				String puntuation = String.valueOf(result.getResult());
				String res = result.getPassed()?LanguageUtil.get(themeDisplay.getLocale(),"passed"):LanguageUtil.get(themeDisplay.getLocale(),"not-passed");
				String startdate = (result.getStartDate()!=null)?dateFormat.format(result.getStartDate()):"";
				
				//Si es de tipo test y no se ha entregado
				if(learnActivity.getTypeId() == 0 && result.getEndDate() == null){
					puntuation = LanguageUtil.get(themeDisplay.getLocale(),"activity.showcalifications.notsubmitted");
					res = LanguageUtil.get(themeDisplay.getLocale(),"activity.showcalifications.notsubmitted");
					startdate = LanguageUtil.get(themeDisplay.getLocale(),"activity.showcalifications.notsubmitted");
				}
			%>
	
			<liferay-ui:search-container-column-text name="user"><%=usu.getFullName() %> </liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="activity.showcalifications.puntuation">	<%=puntuation %> </liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="activity.showcalifications.result">		<%=res %> </liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="activity.showcalifications.startdate">	<%=startdate %> </liferay-ui:search-container-column-text>
			
			<c:if test="<%=delete&&learningActivityType.hasDeleteTries() %>">
				<liferay-ui:search-container-column-text name="actions"><liferay-ui:icon image="close" label="true" message="com.liferay.manager.CleanLearningActivityTries" url='<%= deleteURL+"&userId="+usu.getUserId() %>'  /></liferay-ui:search-container-column-text>
			</c:if>
			
		</liferay-ui:search-container-row>
		
	 	<liferay-ui:search-iterator />
	 	
	</liferay-ui:search-container>
<%} %>
