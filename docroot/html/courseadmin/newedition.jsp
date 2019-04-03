<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@ include file="/init.jsp" %>	
 

<liferay-ui:error key="duplicate-name" message="course-admin.error.duplicate-name" />

<liferay-ui:error key="duplicate-friendly-url" message="course-admin.error.duplicate-friendly-url" />

<liferay-ui:error key="date-interval" message="course-admin.error.date-interval" />

<liferay-portlet:renderURL var="backURL">
	<liferay-portlet:param name="view" value="editions"/>
	<liferay-portlet:param name="courseId" value="${course.courseId}"/>
</liferay-portlet:renderURL>

<liferay-ui:header title="${editionTitle}" backURL="${backURL}"></liferay-ui:header>

<portlet:actionURL name="createEdition" var="createEditionURL">
	<portlet:param name="groupId" value="${group.groupId}" />
</portlet:actionURL>
	
<aui:form name="fm" action="${createEditionURL}" method="post" role="form">
	<aui:input name="parentCourseId" value="${courseId}" type="hidden"/>
	<aui:input type="text" name="newCourseName" value="${newCourseName}" label="course-admin.new-edition-name" size="75">
		<aui:validator name="required" errorMessage="field.required"></aui:validator>
	</aui:input>
	<aui:input name="editionFriendlyURL" label="courseadmin.friendly-url" value="${editionFriendlyURL}" />
    <div id="datesbox">				
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
	<!-- DE MOMENTO COMENTADO HASTA QUE SE EMPIECE A APLICAR  
	<aui:fieldset>
		<aui:input name="linkedCourse" label="course-admin.linked-course" helpMessage="course-admin.linked-course-help" type="checkbox" value="false" checked="false"/>
	</aui:fieldset>
	-->	
	
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
	
	
	<aui:button-row>
		<aui:button type="submit" value="course-admin.create-edition" />
		<aui:button type="cancel" value="cancel" onClick="${backURL}" />
	</aui:button-row>
</aui:form>