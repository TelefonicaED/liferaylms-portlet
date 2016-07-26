
<%@page import="com.liferay.lms.views.ModuleView"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="java.util.Calendar"%>

<%@ include file="/init.jsp"%>

<portlet:actionURL name="submitChangeDate" var="submitChangeDate" />
<liferay-ui:success key="dates-updated-correctly"
	message="date-editor.dates-updated-correctly" />
<liferay-ui:error key="error-updating-dates"
	message="date-editor.error-updating-dates" />

<aui:form name="dateEditorFm" method="POST" action="${submitChangeDate}">

	<div id="maindateeditor" class="dateeditorcont">
		<c:forEach var="module" items="${listModules}">
			<h2>${module.moduleName}</h2>
			<aui:fieldset column="first">
				<liferay-ui:message key="start-date" />
				<aui:field-wrapper>
					<liferay-ui:input-date
						yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>"
						yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"
						yearValue="${module.startYear}" monthValue="${module.startMonth}"
						dayValue="${module.startDay}"
						yearParam="modInitYear_${module.moduleId}"
						monthParam="modInitMonth_${module.moduleId}"
						dayParam="modInitDay_${module.moduleId}" />
					<liferay-ui:input-time
						minuteParam="modInitMinute_${module.moduleId}"
						amPmParam="startAMPM" hourParam="modInitHour_${module.moduleId}"
						hourValue="${module.startHour}"
						minuteValue="${module.startMinute}" />
				</aui:field-wrapper>

			</aui:fieldset>
			<aui:fieldset column="last">
				<liferay-ui:message key="end-date" />
				<aui:field-wrapper>
					<liferay-ui:input-date
						yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>"
						yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"
						yearValue="${module.endYear}" monthValue="${module.endMonth}"
						dayValue="${module.endDay}"
						yearParam="modEndYear_${module.moduleId}"
						monthParam="modEndMonth_${module.moduleId}"
						dayParam="modEndDay_${module.moduleId}" />
					<liferay-ui:input-time
						minuteParam="modEndMinute_${module.moduleId}"
						amPmParam="startAMPM" hourParam="modEndHour_${module.moduleId}"
						hourValue="${module.endHour}" minuteValue="${module.endMinute}" />
				</aui:field-wrapper>
			</aui:fieldset>
			<c:forEach var="act" items="${module.activities}">
				<h6>${act.title}</h6>
				<!-- Fecha inicio Actividad  -->
				<aui:fieldset column="first">
					<liferay-ui:message key="start-date" />
					<aui:field-wrapper>
						<liferay-ui:input-date
							yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>"
							yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"
							yearValue="${act.startYear}" monthValue="${act.startMonth}"
							dayValue="${act.startDay}" yearParam="actInitYear_${act.actId}"
							monthParam="actInitMonth_${act.actId}"
							dayParam="actInitDay_${act.actId}" />
						<liferay-ui:input-time minuteParam="actInitMinute_${act.actId}"
							amPmParam="startAMPM" hourParam="actInitHour_${act.actId}"
							hourValue="${act.startHour}" minuteValue="${act.startMinute}" />
					</aui:field-wrapper>
				</aui:fieldset>
				<aui:fieldset column="last">
					<!-- Fecha fin Actividad -->
					<liferay-ui:message key="end-date" />
					<aui:field-wrapper>
						<liferay-ui:input-date
							yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>"
							yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"
							yearValue="${act.endYear}" monthValue="${act.endMonth}"
							dayValue="${act.endDay}" yearParam="actEndYear_${act.actId}"
							monthParam="actEndMonth_${act.actId}"
							dayParam="actEndDay_${act.actId}" />
						<liferay-ui:input-time minuteParam="actEndMinute_${act.actId}"
							amPmParam="startAMPM" hourParam="actEndHour_${act.actId}"
							hourValue="${act.endHour}" minuteValue="${act.endMinute}" />
					</aui:field-wrapper>
				</aui:fieldset>
				<c:if test="${act.p2pActivity}">
					<aui:fieldset>
						<!-- Actividad P2P -->
						<liferay-ui:message key="p2ptaskactivity.edit.dateUpload" />
						<aui:field-wrapper>
							<liferay-ui:input-date
								yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>"
								yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"
								yearValue="${act.uploadYear}" monthValue="${act.uploadMonth}"
								dayValue="${act.uploadDay}"
								yearParam="actUpdateYear_${act.actId}"
								monthParam="actUpdateMonth_${act.actId}"
								dayParam="actUpdateDay_${act.actId}" />
							<liferay-ui:input-time minuteParam="actUpdateMinute_${act.actId}"
								amPmParam="startAMPM" hourParam="actUpdateHour_${act.actId}"
								hourValue="${act.uploadHour}" minuteValue="${act.uploadMinute}" />
						</aui:field-wrapper>
					</aui:fieldset>
				</c:if>
			</c:forEach>
		</c:forEach>
	</div>

	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>

</aui:form>
