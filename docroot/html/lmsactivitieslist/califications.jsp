<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.liferay.lms.util.LmsConstant"%>
<%@page import="com.liferay.lms.model.ActivityTriesDeleted"%>
<%@page import="com.liferay.lms.service.ActivityTriesDeletedLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
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

<liferay-ui:success key="ok-deleting-tries" message="activity-tries-deleting-successfully" />

<%
	long actId = ParamUtil.getLong(request, "resId", 0);
	LearningActivity learnActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
	
	LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
	LearningActivityType learningActivityType = learningActivityTypeRegistry.getLearningActivityType(learnActivity.getTypeId());
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	dateFormat.setTimeZone(timeZone);
		

	boolean delete = PrefsPropsUtil.getBoolean("learningactivity.show.califications.delete") && permissionChecker.hasPermission(learnActivity.getGroupId(),LearningActivity.class.getName(),learnActivity.getActId(),"DELETE_TRIES");
	List<ActivityTriesDeleted> listActivityTriesDeleteds = ActivityTriesDeletedLocalServiceUtil.getByActIdStatus(actId, LmsConstant.STATUS_NOT_STARTED);
%>

<h2 class="table_title"><%=learnActivity.getTitle(themeDisplay.getLocale()) %></h2>

<portlet:renderURL var="calificacionesURL">
	<portlet:param name="jspPage" value="/html/lmsactivitieslist/califications.jsp"></portlet:param>
</portlet:renderURL>

<portlet:actionURL name="deleteAllURL" var="deleteAllURL">
	<portlet:param name="resId" value="<%=String.valueOf(actId)%>"/>
</portlet:actionURL>

<portlet:actionURL name="deleteAllTries" var="deleteAllTriesURL">
	<portlet:param name="resId" value="<%=String.valueOf(actId)%>"/>
</portlet:actionURL>

<portlet:actionURL name="deleteURL" var="deleteURL">
	<portlet:param name="resId" value="<%=String.valueOf(actId)%>"/>
</portlet:actionURL>

<c:if test="<%=delete&&learningActivityType.hasDeleteTries() && (listActivityTriesDeleteds == null || listActivityTriesDeleteds.size() == 0)%>">
	<aui:form name="deleteAllFm" action="<%=deleteAllURL %>" method="POST" role="form">
		<liferay-ui:icon image="close" label="true" message="com.liferay.manager.CleanLearningActivityTriesNotPassed" url="javascript:${renderResponse.getNamespace()}deleteAll();"  />
	</aui:form>
	<aui:form name="deleteAllTriesFm" action="<%=deleteAllTriesURL %>" method="POST" role="form">
		<liferay-ui:icon image="close" label="true" message="com.liferay.manager.CleanLearningActivityTries" url="javascript:${renderResponse.getNamespace()}deleteAllTries();"  />
	</aui:form>
</c:if>
<c:if test="<%=listActivityTriesDeleteds != null && listActivityTriesDeleteds.size() > 0 %>">
	<span class="portlet-msg-warning"><liferay-ui:message key="lms-activity-list.warning-activity-tries-deleted" /></span>
</c:if>

<script>
	function <portlet:namespace />deleteAll(){
		if(confirm(Liferay.Language.get("confirm.warning-delete-all-tries"))){
			document.<portlet:namespace />deleteAllFm.submit();
		}
	}
	function <portlet:namespace />deleteAllTries(){
		if(confirm(Liferay.Language.get("confirm.warning-delete-all-tries"))){
			document.<portlet:namespace />deleteAllTriesFm.submit();
		}
	}
</script>

<%

String firstName = ParamUtil.getString(request, "first-name","");
String lastName = ParamUtil.getString(request, "last-name","");
String screenName = ParamUtil.getString(request, "screen-name","");
String emailAddress = ParamUtil.getString(request, "email-address","");
Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(learnActivity.getGroupId()); 

PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("jspPage","/html/lmsactivitieslist/califications.jsp");
portletURL.setParameter("resId",Long.toString(actId));
portletURL.setParameter("first-name",firstName);
portletURL.setParameter("last-name",lastName);
portletURL.setParameter("screen-name",screenName);
portletURL.setParameter("email-address",emailAddress);

SearchContainer<User> userSearchContainer = new SearchContainer<User>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 
		ParamUtil.getInteger(renderRequest, SearchContainer.DEFAULT_DELTA_PARAM,SearchContainer.DEFAULT_DELTA), portletURL, 
		null, "there-are-no-results");

List<User> users = CourseLocalServiceUtil.getStudents(course.getCourseId(), themeDisplay.getCompanyId(),screenName,firstName,lastName,emailAddress,true, userSearchContainer.getStart(), userSearchContainer.getEnd(),null);

int totalUsers = CourseLocalServiceUtil.countStudents(course.getCourseId(), themeDisplay.getCompanyId(), screenName,firstName,lastName,emailAddress,true);
userSearchContainer.setResults(users);
userSearchContainer.setTotal(totalUsers);

%>
	<aui:form name="searchFm" action="<%=renderResponse.createRenderURL() %>" method="POST" role="form">
		<div class="taglib-search-toggle">
			<div class="taglib-search-toggle-advanced">
				<aui:input type="hidden" name="resId" value="<%=actId %>"/>
				<aui:input type="hidden" name="jspPage" value="/html/lmsactivitieslist/califications.jsp"/>
				
				<aui:fieldset>
					<aui:input type="text" name="first-name" value="<%=firstName %>" inlineField="true"  />
					<aui:input type="text" name="last-name" value="<%=lastName %>" inlineField="true"  />
					<aui:input type="text" name="screen-name" value="<%=screenName %>" inlineField="true"  />
					<aui:input type="text" name="email-address" value="<%=lastName %>" inlineField="true"  />
				</aui:fieldset>
				
				<aui:button type="submit" value="search"/>
			</div>
		</div>
	</aui:form>

	<liferay-ui:search-container 
		searchContainer="<%=userSearchContainer %>" 
		iteratorURL="<%=userSearchContainer.getIteratorURL() %>"
		delta="<%=userSearchContainer.getDelta()%>"
		>
					
		<liferay-ui:search-container-results 
			total="<%=userSearchContainer.getTotal() %>" 
			results="<%=userSearchContainer.getResults() %>"
		/>
		
		<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="usuario">
			<%
				LearningActivityResult lar = null;
				try{
					lar = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, usuario.getUserId());
				}catch(Exception e){}
				
				if(lar==null){

					String res = LanguageUtil.get(themeDisplay.getLocale(),"activity.showcalifications.notsubmitted");
					
			%>
				<liferay-ui:search-container-column-text name="user"><%=usuario.getFullName() %> </liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="activity.showcalifications.puntuation"> <%=res %> </liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="activity.showcalifications.result">		<%=res %> </liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="activity.showcalifications.startdate"> <%=res %> </liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="actions"> - </liferay-ui:search-container-column-text>
			<%
				}else{
					CalificationType ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());
					
					
					String puntuation = ct.translate(themeDisplay.getLocale(), course.getGroupCreatedId(), lar.getResult());
					String res = "";
					if(lar.getPassed())
						res = LanguageUtil.get(themeDisplay.getLocale(),"passed");
					else if(!lar.getPassed() && lar.getEndDate() != null){
						res = LanguageUtil.get(themeDisplay.getLocale(),"not-passed");
					}else{
						res = LanguageUtil.get(themeDisplay.getLocale(),"started");
					}
					String startdate = (lar.getStartDate()!=null)?dateFormat.format(lar.getStartDate()):"";
					
					//Si es de tipo test y no se ha entregado
					if(learnActivity.getTypeId() == 0 && lar.getEndDate() == null){
						puntuation = LanguageUtil.get(themeDisplay.getLocale(),"activity.showcalifications.notsubmitted");
						res = puntuation;
						startdate = puntuation;
					}
			%>
				<liferay-ui:search-container-column-text name="user"><%=usuario.getFullName() %> </liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="activity.showcalifications.puntuation">	<%=puntuation %> </liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="activity.showcalifications.result">		<%=res %> </liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text name="activity.showcalifications.startdate">	<%=startdate %> </liferay-ui:search-container-column-text>
			
				<c:if test="<%=delete&&learningActivityType.hasDeleteTries()&& (listActivityTriesDeleteds == null || listActivityTriesDeleteds.size() == 0) %>">
					<liferay-ui:search-container-column-text name="actions"><liferay-ui:icon image="close" label="true" message="com.liferay.manager.CleanLearningActivityTries" url='<%= deleteURL+"&userId="+usuario.getUserId() %>'  /></liferay-ui:search-container-column-text>
				</c:if>
			<%
				}
			%>
		</liferay-ui:search-container-row>
	 	<liferay-ui:search-iterator />
	</liferay-ui:search-container>
	
