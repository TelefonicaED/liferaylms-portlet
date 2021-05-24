<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.portal.kernel.util.Time"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.portal.LayoutImportException" %>
<%@page import="com.liferay.portal.model.LayoutSetPrototype"%>
<%@page import="com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>

<%@ include file="/init.jsp" %>	
 
<%
	String groupId = request.getParameter("groupId");
%>
<liferay-ui:error key="courseadmin.clone.error.duplicateName" message="courseadmin.clone.error.duplicateName" />
<liferay-ui:error key="courseadmin.clone.error.dateinterval" message="courseadmin.clone.error.dateinterval" />

<liferay-portlet:renderURL var="backURL"/>

<c:choose>
		<c:when test="${isCourseChild}">
			<liferay-portlet:renderURL var="backURL">
				<liferay-portlet:param name="view" value="editions"/>
				<liferay-portlet:param name="courseId" value="${course.parentCourseId}"/>
			</liferay-portlet:renderURL>
		</c:when>
		<c:otherwise>
			<liferay-portlet:renderURL var="backURL"/>
		</c:otherwise>
	</c:choose>

<%--<liferay-ui:header title="<%= course != null ? course.getTitle(themeDisplay.getLocale()) : \"course\" %>" backURL="<%=backURL %>"></liferay-ui:header>--%>
<liferay-ui:header title="${courseTitle}" backURL="${backURL}"></liferay-ui:header>

<portlet:actionURL name="cloneCourse" var="cloneCourseURL">
	<portlet:param name="groupId" value="${groupId}" />
	<portlet:param name="redirect" value='<%= ParamUtil.getString(request, "redirect", currentURL) %>'/>
</portlet:actionURL>
	
<aui:form name="form" action="<%=cloneCourseURL%>" role="form" method="post">

	<aui:input type="text" name="newCourseName" value="${newCourseName}" label="courseadmin.clone.newcoursename" size="50" helpMessage="courseadmin.clone.newcoursename.help">
		<aui:validator name="required" errorMessage="field.required"></aui:validator>
	</aui:input>
    <aui:input type="checkbox"  name="cloneForum" label="courseadmin.clone.clone-forum" helpMessage="courseadmin.clone.clone-forum.help" />
    <aui:input type="checkbox"  name="cloneDocuments" label="courseadmin.clone.clone-documents" helpMessage="courseadmin.clone.clone-documents.help" />
    <aui:input type="checkbox"  name="cloneModuleClassification" label="courseadmin.clone.clone-module-classification" helpMessage="courseadmin.clone.clone-module-classification.help" />
    <aui:input type="checkbox"  name="cloneActivityClassificationTypes" label="courseadmin.clone.clone-activity-classification-types" helpMessage="courseadmin.clone.clone-activity-classification-types.help" />
        
	<div id="datesbox" >				
		<aui:field-wrapper label="course-admin.start-inscription-date">
			<liferay-ui:input-date yearRangeEnd="${defaultEndYear}" yearRangeStart="${defaultStartYear}"  dayParam="startDay" monthParam="startMon"
					 yearParam="startYear"  yearNullable="false" dayNullable="false" monthNullable="false" yearValue="${startYear}" monthValue="${startMonth}" dayValue="${startDay}"></liferay-ui:input-date>
			<liferay-ui:input-time minuteParam="startMin" amPmParam="startAMPM" hourParam="startHour" hourValue="${startHour}" minuteValue="${startMin}"></liferay-ui:input-time>
		</aui:field-wrapper>
		<aui:field-wrapper label="course-admin.end-inscription-date">
			<liferay-ui:input-date yearRangeEnd="${defaultEndYear}" yearRangeStart="${defaultStartYear}" dayParam="stopDay" monthParam="stopMon"
					 yearParam="stopYear"  yearNullable="false" dayNullable="false" monthNullable="false"  yearValue="${endYear}" monthValue="${endMonth}" dayValue="${endDay}"></liferay-ui:input-date>
			 <liferay-ui:input-time minuteParam="stopMin" amPmParam="stopAMPM" hourParam="stopHour"  hourValue="${endHour}" minuteValue="${endMin}"></liferay-ui:input-time></br>
		</aui:field-wrapper>
	</div>	
	<div id="datesExecutionbox">				
		<aui:field-wrapper label="start-execution-date">
			<liferay-ui:input-date yearRangeEnd="${defaultEndYear}" yearRangeStart="${defaultStartYear}"  dayParam="startExecutionDay" monthParam="startExecutionMon"
					 yearParam="startExecutionYear"  yearNullable="false" dayNullable="false" monthNullable="false" yearValue="${startExecutionYear}" monthValue="${startExecutionMonth}" dayValue="${startExecutionDay}"></liferay-ui:input-date>
			<liferay-ui:input-time minuteParam="startExecutionMin" amPmParam="startExecutionAMPM" hourParam="startExecutionHour" hourValue="${startExecutionHour}" minuteValue="${startExecutionMin}"></liferay-ui:input-time>
		</aui:field-wrapper>
		<aui:field-wrapper label="end-execution-date">
			<liferay-ui:input-date yearRangeEnd="${defaultEndYear}" yearRangeStart="${defaultStartYear}" dayParam="stopExecutionDay" monthParam="stopExecutionMon"
					 yearParam="stopExecutionYear"  yearNullable="false" dayNullable="false" monthNullable="false"  yearValue="${endExecutionYear}" monthValue="${endExecutionMonth}" dayValue="${endExecutionDay}"></liferay-ui:input-date>
			 <liferay-ui:input-time minuteParam="stopExecutionMin" amPmParam="stopExecutionAMPM" hourParam="stopExecutionHour"  hourValue="${endExecutionHour}" minuteValue="${endExecutionMin}"></liferay-ui:input-time></br>
		</aui:field-wrapper>
	</div>
	
	<!-- Selector de plantilla -->
	<c:choose>
		<c:when test="${viewTemplateSelector }">
			<aui:select name="courseTemplate" label="course-template" onChange="showAlert(this);">
				<c:forEach items="${lspList}" var="lsp">
					<aui:option value="${lsp.layoutSetPrototypeId}" selected="${lsp.layoutSetPrototypeId == parentLspId}">${lsp.getName(themeDisplay.getLocale())}</aui:option>
				</c:forEach>
			</aui:select>
		</c:when>
		<c:otherwise>
			<aui:input name="courseTemplate" value="${lspId}" type="hidden"/>
		</c:otherwise>
	</c:choose>
		
		
<script type="text/javascript">
	function showAlert(ele){
		document.getElementById("datesbox").style.visibility = "visible";			
		if(<%=GroupLocalServiceUtil.getGroup(Long.parseLong(groupId)).getPublicLayoutSet().getLayoutSetPrototypeId()%> != ele.options[ele.selectedIndex].value){
			alert("<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "template-not-equals")) %>");
		}
		var course = ele.value.split("&");

		if( course[0].indexOf("Curso Simple") == 0 ){
			document.getElementById("datesbox").style.visibility = "hidden";			
		} else {
			document.getElementById("datesbox").style.visibility = "visible";						
		}
	}
</script>

	<c:choose>
		<c:when test="<%=GetterUtil.getBoolean(renderRequest.getPreferences().getValues(\"showcatalog\", new String[]{StringPool.TRUE})[0],true) %>">
       			<aui:input type="hidden" name="visible"  value="true" />	
		</c:when>
		<c:otherwise>
        		<aui:field-wrapper label="published-in-catalog">
				<aui:input type="checkbox" name="visible" label="published-in-catalog" value="false" />	
			</aui:field-wrapper>	
		</c:otherwise>
	</c:choose>
				
	<aui:button-row>
		<aui:button type="submit" value="clone" />
	</aui:button-row>
</aui:form>