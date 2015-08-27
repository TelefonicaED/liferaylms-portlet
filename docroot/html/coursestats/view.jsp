<%@page import="java.util.Locale"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<%
long registered=UserLocalServiceUtil.getGroupUsersCount(themeDisplay.getScopeGroupId(),0);
Course curso = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
long finalizados = CourseResultLocalServiceUtil.countByCourseId(curso.getCourseId(), true);
long iniciados = CourseResultLocalServiceUtil.countByCourseId(curso.getCourseId(), false) + finalizados;
%>

<liferay-portlet:resourceURL var="exportURL" >
	<portlet:param name="action" value="export"/>
	<portlet:param name="courseId" value="<%=Long.toString(curso.getCourseId()) %>"/>
</liferay-portlet:resourceURL>

<liferay-ui:icon cssClass='bt_importexport' label="<%= true %>" message="coursestats.csv.export" method="get" url="<%=exportURL%>" />
<div class="registered"><liferay-ui:message key="coursestats.hay" /> <%=registered %> <liferay-ui:message key="coursestats.inscription.users" /></div>

<div class="coursestart"><liferay-ui:message key="coursestats.start.course" /> <%= iniciados %> <liferay-ui:message key="coursestats.end.course" /> <%= finalizados %>.</div>
<liferay-ui:search-container  deltaConfigurable="true" emptyResultsMessage="module-empty-results-message" delta="20" >
	<liferay-ui:search-container-results>
	<%
	int containerStart;
	int containerEnd;
	try {
		containerStart = ParamUtil.getInteger(request, "containerStart");
		containerEnd = ParamUtil.getInteger(request, "containerEnd");
	} catch (Exception e) {
		containerStart = searchContainer.getStart();
		containerEnd = searchContainer.getEnd();
	}
	if (containerStart <=0) {
		containerStart = searchContainer.getStart();
		containerEnd = searchContainer.getEnd();
	}
	
	List<Module> tempResults = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
	results = ListUtil.subList(tempResults, containerStart, containerEnd);
	total = tempResults.size();

	pageContext.setAttribute("results", results);
	pageContext.setAttribute("total", total);

	request.setAttribute("containerStart",String.valueOf(containerStart));
	request.setAttribute("containerEnd",String.valueOf(containerEnd));
	%>

	</liferay-ui:search-container-results>
	<liferay-ui:search-container-row className="com.liferay.lms.model.Module"
		keyProperty="moduleId"
		modelVar="module"
	>
	<%
	long started=ModuleResultLocalServiceUtil.countByModule(module.getModuleId());
	long finished=ModuleResultLocalServiceUtil.countByModulePassed(module.getModuleId(),true);
	
		%>
	<liferay-portlet:renderURL var="viewModuleURL">
	<liferay-portlet:param name="jspPage" value="/html/coursestats/viewmodule.jsp"></liferay-portlet:param>
	<liferay-portlet:param name="moduleId" value="<%= Long.toString(module.getModuleId())%>"></liferay-portlet:param>
	</liferay-portlet:renderURL>
	<liferay-ui:search-container-column-text name="module"><a href="<%=viewModuleURL%>"><%=module.getTitle(themeDisplay.getLocale()) %></a></liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  cssClass="date-column" name="coursestats.start.date">
		<%if(module.getStartDate()!=null)
			{
			%>
		<%=dateFormatDate.format(module.getStartDate()) %>
		<%
		}
		%>
		</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text cssClass="date-column" name="coursestats.end.date">
			<%if(module.getEndDate()!=null)
			{
			%>
			<%=dateFormatDate.format(module.getEndDate()) %>
			<%
			}
			%>
			</liferay-ui:search-container-column-text>
	
	<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.start.student"><%=started %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text cssClass="number-column" name="coursestats.end.student"><%=finished %></liferay-ui:search-container-column-text>
		<%  
	
	int totalActivity=0;
	List<LearningActivity> tempResults = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(module.getModuleId());
	totalActivity = tempResults.size();
	%>
	<liferay-ui:search-container-column-text cssClass="number-column" name="total.activity"><%=totalActivity %></liferay-ui:search-container-column-text>
	
		
	<% if(module.getPrecedence() != 0) {
		
		Module modulePredence = ModuleLocalServiceUtil.getModule(module.getPrecedence());
	%>
	<liferay-ui:search-container-column-text name="coursestats.modulestats.dependencies"><%=modulePredence.getTitle(themeDisplay.getLocale()) %></liferay-ui:search-container-column-text>
	<%}else{ %>
	<liferay-ui:search-container-column-text name="coursestats.modulestats.dependencies"><%=LanguageUtil.get(locale, "no")%></liferay-ui:search-container-column-text>
	<%} %>
	
	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator />
	</liferay-ui:search-container>