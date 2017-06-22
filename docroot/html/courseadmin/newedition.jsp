<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@ include file="/init.jsp" %>	
 

<liferay-ui:error key="course-admin.error.duplicate-name" message="course-admin.error.duplicate-name" />
<liferay-ui:error key="course-admin.error.date-interval" message="course-admin.error.date-interval" />

<liferay-portlet:renderURL var="backURL"></liferay-portlet:renderURL>
<liferay-ui:header title="${editionTitle}" backURL="${backURL}"></liferay-ui:header>

<portlet:actionURL name="createEdition" var="createEditionURL">
	<portlet:param name="groupId" value="${group.groupId}" />
</portlet:actionURL>
	
<aui:form name="fm" action="${createEditionURL}" method="post">
	<aui:input name="parentCourseId" value="${courseId}" type="hidden"/>
	<aui:input type="text" name="newCourseName" value="${newCourseName}" label="course-admin.new-edition-name" size="75">
		<aui:validator name="required" errorMessage="field.required"></aui:validator>
	</aui:input>
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
				
	<aui:button-row>
		<aui:button type="submit" value="course-admin.create-edition" />
	</aui:button-row>
</aui:form>