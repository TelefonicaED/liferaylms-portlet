
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
			<aui:input name="module_${module.moduleId}" type="text" label="" value="" cssClass="aui-helper-hidden">
				<aui:validator name="custom" errorMessage="please-enter-a-start-date-that-comes-before-the-end-date">
				function (val, fieldNode, ruleValue) {
			          	
			          	var moduleId = ${module.moduleId};
			          						          	
			          	var startDateYear = $('#<portlet:namespace />modInitYear_'+moduleId).val();
			          	var startDateMonth = $('#<portlet:namespace />modInitMonth_'+moduleId).val();
			          	var startDateDay = $('#<portlet:namespace />modInitDay_'+moduleId).val();
						var startDateHour = $('select[name=<portlet:namespace />modInitHour_'+moduleId+']').val();
						var startDateMinute = $('select[name=<portlet:namespace />modInitMinute_'+moduleId+']').val();
						
						
						var endDateYear = $('#<portlet:namespace />modEndYear_'+moduleId).val();
						var endDateMonth = $('#<portlet:namespace />modEndMonth_'+moduleId).val();
						var endDateDay = $('#<portlet:namespace />modEndDay_'+moduleId).val();
						var endDateHour = $('select[name=<portlet:namespace />modEndHour_'+moduleId+']').val();
						var endDateMinute = $('select[name=<portlet:namespace />modEndMinute_'+moduleId+']').val();
						
						
						var start = new Date(startDateYear,startDateMonth,startDateDay,startDateHour,startDateMinute);
						var end = new Date(endDateYear,endDateMonth,endDateDay,endDateHour,endDateMinute);
						
						if(start.getTime()>end.getTime()){
								$('#<portlet:namespace/>moduleErrorMessage_'+moduleId).removeClass('aui-helper-hidden');
								return false;
						}
						if(!$('#<portlet:namespace/>moduleErrorMessage_'+moduleId).hasClass('aui-helper-hidden')){
							$('#<portlet:namespace/>moduleErrorMessage_'+moduleId).addClass('aui-helper-hidden');
						}							
						
						return true;
			        }
			        </aui:validator>
			</aui:input>
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
						dayParam="modEndDay_${module.moduleId}">
					</liferay-ui:input-date>
					<liferay-ui:input-time
						minuteParam="modEndMinute_${module.moduleId}"
						amPmParam="startAMPM" hourParam="modEndHour_${module.moduleId}"
						hourValue="${module.endHour}" minuteValue="${module.endMinute}">
					</liferay-ui:input-time>	
				</aui:field-wrapper>
				<aui:fieldset id="moduleErrorMessage_${module.moduleId}" cssClass="aui-helper-hidden">
					<span class="aui-form-validator-error-container">
						<span class="aui-form-validator-stack-error">
							<liferay-ui:message key="please-enter-a-start-date-that-comes-before-the-end-date"></liferay-ui:message>
						</span>
					</span>
				</aui:fieldset>		
			</aui:fieldset>
			<c:forEach var="act" items="${module.activities}">
				<h6>${act.title}</h6>
				<!-- Fecha inicio Actividad  -->
				<aui:input name="act_${act.actId}" type="text" label="" value="" cssClass="aui-helper-hidden">
					<aui:validator name="custom" errorMessage="please-enter-a-start-date-that-comes-before-the-end-date">
					function (val, fieldNode, ruleValue) {
				          	
				          	var moduleId = ${module.moduleId};
				          	var actId= ${act.actId};
				          		          	
				          	var startDateYear = $('#<portlet:namespace />modInitYear_'+moduleId).val();
				          	var startDateMonth = $('#<portlet:namespace />modInitMonth_'+moduleId).val();
				          	var startDateDay = $('#<portlet:namespace />modInitDay_'+moduleId).val();
							var startDateHour = $('select[name=<portlet:namespace />modInitHour_'+moduleId+']').val();
							var startDateMinute = $('select[name=<portlet:namespace />modInitMinute_'+moduleId+']').val();
							
							
							var endDateYear = $('#<portlet:namespace />modEndYear_'+moduleId).val();
							var endDateMonth = $('#<portlet:namespace />modEndMonth_'+moduleId).val();
							var endDateDay = $('#<portlet:namespace />modEndDay_'+moduleId).val();
							var endDateHour = $('select[name=<portlet:namespace />modEndHour_'+moduleId+']').val();
							var endDateMinute = $('select[name=<portlet:namespace />modEndMinute_'+moduleId+']').val();
							
							
							
							var moduleStart = new Date(startDateYear,startDateMonth,startDateDay,startDateHour,startDateMinute);
							var moduleEnd = new Date(endDateYear,endDateMonth,endDateDay,endDateHour,endDateMinute);
							
							startDateYear = $('#<portlet:namespace />actInitYear_'+actId).val();
				          	startDateMonth = $('#<portlet:namespace />actInitMonth_'+actId).val();
				          	startDateDay = $('#<portlet:namespace />actInitDay_'+actId).val();
							startDateHour = $('select[name=<portlet:namespace />actInitHour_'+actId+']').val();
							startDateMinute = $('select[name=<portlet:namespace />actInitMinute_'+actId+']').val();
							
							endDateYear = $('#<portlet:namespace />actEndYear_'+actId).val();
							endDateMonth = $('#<portlet:namespace />actEndMonth_'+actId).val();
							endDateDay = $('#<portlet:namespace />actEndDay_'+actId).val();
							endDateHour = $('select[name=<portlet:namespace />actEndHour_'+actId+']').val();
							endDateMinute = $('select[name=<portlet:namespace />actEndMinute_'+actId+']').val();
							
										
							var start = new Date(startDateYear,startDateMonth,startDateDay,startDateHour,startDateMinute);
							var end = new Date(endDateYear,endDateMonth,endDateDay,endDateHour,endDateMinute);
									
							
							if(start.getTime()>end.getTime()){
									$('#<portlet:namespace/>activityErrorMessage_'+actId).removeClass('aui-helper-hidden');
									return false;
							}
							if(end.getTime() > moduleEnd.getTime() || start.getTime() < moduleStart.getTime()){
								$('#<portlet:namespace/>activityErrorModuleMessage_'+actId).removeClass('aui-helper-hidden');
								return false;
							}
							if(!$('#<portlet:namespace/>activityErrorMessage_'+actId).hasClass('aui-helper-hidden')){
								$('#<portlet:namespace/>activityErrorMessage_'+actId).addClass('aui-helper-hidden');
							}
							if(!$('#<portlet:namespace/>activityErrorModuleMessage_'+actId).hasClass('aui-helper-hidden')){
								$('#<portlet:namespace/>activityErrorModuleMessage_'+actId).addClass('aui-helper-hidden');
							}
							return true;
				        }
				        </aui:validator>
				</aui:input>
				
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
				<aui:fieldset id="activityErrorMessage_${act.actId}" cssClass="aui-helper-hidden">
					<span class="aui-form-validator-error-container">
						<span class="aui-form-validator-stack-error">
							<liferay-ui:message key="please-enter-a-start-date-that-comes-before-the-end-date"></liferay-ui:message>
						</span>
					</span>
				</aui:fieldset>	
				<aui:fieldset id="activityErrorModuleMessage_${act.actId}" cssClass="aui-helper-hidden">
					<span class="aui-form-validator-error-container">
						<span class="aui-form-validator-stack-error">
							<liferay-ui:message key="date-editor.please-enter-date-in-module-range"></liferay-ui:message>
						</span>
					</span>
				</aui:fieldset>	
				<c:if test="${act.p2pActivity}">
					<aui:fieldset>
						<!-- Actividad P2P -->
						<liferay-ui:message key="p2ptaskactivity.edit.dateUpload" />
						<aui:input name="act_${act.actId}" type="text" label="" value="" cssClass="aui-helper-hidden">
							<aui:validator name="custom" errorMessage="please-enter-a-start-date-that-comes-before-the-end-date">
							function (val, fieldNode, ruleValue) {
				          	
					           	var actId= ${act.actId};
					          		          	
					          	var startDateYear = $('#<portlet:namespace />actInitYear_'+actId).val();
					          	var startDateMonth = $('#<portlet:namespace />actInitMonth_'+actId).val();
					          	var startDateDay = $('#<portlet:namespace />actInitDay_'+actId).val();
								var startDateHour = $('select[name=<portlet:namespace />actInitHour_'+actId+']').val();
								var startDateMinute = $('select[name=<portlet:namespace />actInitMinute_'+actId+']').val();
								
								var updateDateYear = $('#<portlet:namespace />actUpdateYear_'+actId).val();
								var updateDateMonth = $('#<portlet:namespace />actUpdateMonth_'+actId).val();
								var updateDateDay = $('#<portlet:namespace />actUpdateDay_'+actId).val();
								var updateDateHour = $('select[name=<portlet:namespace />actUpdateHour_'+actId+']').val();
								var updateDateMinute = $('select[name=<portlet:namespace />actUpdateMinute_'+actId+']').val();
								
								var endDateYear = $('#<portlet:namespace />actEndYear_'+actId).val();
								var endDateMonth = $('#<portlet:namespace />actEndMonth_'+actId).val();
								var endDateDay = $('#<portlet:namespace />actEndDay_'+actId).val();
								var endDateHour = $('select[name=<portlet:namespace />actEndHour_'+actId+']').val();
								var endDateMinute = $('select[name=<portlet:namespace />actEndMinute_'+actId+']').val();
											
								var start = new Date(startDateYear,startDateMonth,startDateDay,startDateHour,startDateMinute);
								var update = new Date(updateDateYear,updateDateMonth,updateDateDay,updateDateHour,updateDateMinute);
								var end = new Date(endDateYear,endDateMonth,endDateDay,endDateHour,endDateMinute);
								
								if(start.getTime()>update.getTime() || end.getTime()< update.getTime()){
									$('#<portlet:namespace/>activityUpdateErrorMessage_'+actId).removeClass('aui-helper-hidden');
										return false;
								}
								if(!$('#<portlet:namespace/>activityUpdateErrorMessage_'+actId).hasClass('aui-helper-hidden')){
									$('#<portlet:namespace/>activityUpdateErrorMessage_'+actId).addClass('aui-helper-hidden');
								}
								return true;
					        }
				        </aui:validator>
					</aui:input>
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
					<aui:fieldset id="activityUpdateErrorMessage_${act.actId}" cssClass="aui-helper-hidden">
					<span class="aui-form-validator-error-container">
						<span class="aui-form-validator-stack-error">
							<liferay-ui:message key="date-editor.please-enter-an-update-date-in-range"></liferay-ui:message>
						</span>
					</span>
				</aui:fieldset>	
				</c:if>
			</c:forEach>
		</c:forEach>
	</div>

	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>

</aui:form>
