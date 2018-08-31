<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.model.impl.LearningActivityImpl"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.model.ModelHintsUtil"%>
<%@page import="java.util.Locale"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.lms.asset.LearningActivityBaseAssetRenderer"%>
<%@page import="com.liferay.portal.service.ResourcePermissionServiceUtil"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.service.ResourceActionLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.ResourceConstants"%>
<%@page import="com.liferay.portal.service.ResourcePermissionLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.PortletConstants"%>
<%@page import="com.liferay.portal.service.permission.PortletPermissionUtil"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.TeamLocalServiceUtil"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.lms.learningactivity.BaseLearningActivityType"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.portlet.PortletQNameUtil"%>
<%@page import="com.liferay.portal.model.PublicRenderParameter"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistry"%>
<%@page import="com.liferay.lms.asset.LearningActivityAssetRendererFactory"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="java.util.Set"%>
<%@page import="com.liferay.lms.LearningTypesProperties"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<liferay-ui:success key="activity-saved-successfully" message="activity-saved-successfully" />
<liferay-ui:error key="learningactivity.connect.error.timepassg" message="learningactivity.connect.error.timepassg"></liferay-ui:error>
<liferay-ui:error key="learningactivity.connect.error.timepass.nan" message="learningactivity.connect.error.timepass.nan"></liferay-ui:error>
<liferay-ui:error key="learningactivity.connect.error.time" message="learningactivity.connect.error.time"></liferay-ui:error>
<liferay-ui:error key="learningactivity.connect.error.time.nan" message="learningactivity.connect.error.time.nan"></liferay-ui:error>
<liferay-ui:error key="execactivity.editActivity.questionsPerPage.number" message="execActivity.options.error.questionsPerPage"></liferay-ui:error>
<liferay-ui:error key="execactivity.editActivity.random.number" message="execActivity.options.error.random"></liferay-ui:error>
<liferay-ui:error key="general.error" message="edit.activity.general.error"></liferay-ui:error>
<liferay-ui:error key="error-p2pActivity-inProgress" message="p2ptaskactivity.error.extraContentInProgress" />

<%
renderResponse.setProperty("clear-request-parameters", Boolean.TRUE.toString());

long moduleId=ParamUtil.getLong(request,"resModuleId",0);
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");
long typeId=ParamUtil.getLong(request, "type");
AssetRendererFactory arf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
Map<Long,String> classTypes=arf.getClassTypes(new long[]{themeDisplay.getScopeGroupId()}, themeDisplay.getLocale());
long fileMaxSize = GetterUtil.getLong(PrefsPropsUtil.getString(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));
String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");
long actId=ParamUtil.getLong(request, "resId",0);
LearningActivity learnact=null;
if(request.getAttribute("activity")!=null){
	learnact=(LearningActivity)request.getAttribute("activity");
	typeId=learnact.getTypeId();
	moduleId=learnact.getModuleId();
}else{
	if(actId>0)	{
		learnact=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		typeId=learnact.getTypeId();
		moduleId=learnact.getModuleId();
	}
}


%>
<portlet:actionURL var="saveactivityURL" name="saveActivity" >
	<portlet:param name="editing" value="<%=StringPool.TRUE %>"/>
	<portlet:param name="type" value="<%=String.valueOf(typeId) %>"/>
	<portlet:param name="resId" value="<%=String.valueOf(actId) %>"/>
</portlet:actionURL>


<% 

//Reload LearningActivity
if(learnact!=null&&learnact.getActId()>0&&learnact.isNullStartDate()){
	learnact=LearningActivityLocalServiceUtil.getLearningActivity(learnact.getActId());
}

Module module = null;
try{
	module = ModuleLocalServiceUtil.getModule(moduleId);
}catch(Exception e){}

boolean disabled = true;
if(LearningActivityLocalServiceUtil.canBeEdited(learnact, user.getUserId())){
	disabled = false;
}

String typeName=classTypes.get(typeId);
LearningActivityType larntype=new LearningActivityTypeRegistry().getLearningActivityType(typeId);

String description="", startCalendarClass="", endCalendarClass="";
SimpleDateFormat formatDay    = new SimpleDateFormat("dd");
formatDay.setTimeZone(timeZone);
SimpleDateFormat formatMonth    = new SimpleDateFormat("MM");
formatMonth.setTimeZone(timeZone);
SimpleDateFormat formatYear    = new SimpleDateFormat("yyyy");
formatYear.setTimeZone(timeZone);
SimpleDateFormat formatHour   = new SimpleDateFormat("HH");
formatHour.setTimeZone(timeZone);
SimpleDateFormat formatMin = new SimpleDateFormat("mm");
formatMin.setTimeZone(timeZone);
Date today=new Date(System.currentTimeMillis());
int startDay=Integer.parseInt(formatDay.format(today));
int startMonth=Integer.parseInt(formatMonth.format(today))-1;
int startYear=Integer.parseInt(formatYear.format(today));
int startHour=Integer.parseInt(formatHour.format(today));
int startMin=Integer.parseInt(formatMin.format(today));
int endDay=Integer.parseInt(formatDay.format(today));
int endMonth=Integer.parseInt(formatMonth.format(today))-1;
int endYear=Integer.parseInt(formatYear.format(today))+1;
int endHour=Integer.parseInt(formatHour.format(today));
int endMin=Integer.parseInt(formatMin.format(today));

Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
boolean isCourse = false;
if (course!=null){
	isCourse = true;
}

//title = ParamUtil.getString(request, "title", null);
description = ParamUtil.getString(request, "description", "");
String title = ParamUtil.getString(request, "title", "");
title = title.replace("%23", "#").replace("%26","&").replace("%25", "%");
%>

<%
if(learnact!=null)
{
	actId=learnact.getActId();
	if(Validator.isNull(description)){
		description=learnact.getDescription(themeDisplay.getLocale());	
	}
	
	if(!learnact.isNullStartDate()){
		Date startDate = learnact.getStartdate();
		startDay=Integer.parseInt(formatDay.format(startDate));
		startMonth=Integer.parseInt(formatMonth.format(startDate))-1;
		startYear=Integer.parseInt(formatYear.format(startDate));
		startHour=Integer.parseInt(formatHour.format(startDate));
		startMin=Integer.parseInt(formatMin.format(startDate));
	}
	
	if(!learnact.isNullEndDate()){
		Date endDate = learnact.getEnddate();
		endDay=Integer.parseInt(formatDay.format(endDate));
		endMonth=Integer.parseInt(formatMonth.format(endDate))-1;
		endYear=Integer.parseInt(formatYear.format(endDate));
		endHour=Integer.parseInt(formatHour.format(endDate));
		endMin=Integer.parseInt(formatMin.format(endDate));
	}
	%>
	
	<portlet:actionURL name="deleteMyTries" var="deleteMyTriesURL">
		<portlet:param name="editing" value="<%=StringPool.TRUE %>"/>
		<portlet:param name="resId" value="<%=Long.toString(learnact.getActId()) %>" />
		<portlet:param name="redirect" value="<%=redirect %>" />
		<portlet:param name="backURL" value="<%=backURL%>" />
	</portlet:actionURL>

	
	<portlet:actionURL name="deleteActivityBank" var="deleteActivityBankURL">
		<portlet:param name="resId" value="<%=Long.toString(learnact.getActId()) %>" />
		<portlet:param name="redirect" value="<%=redirect %>" />
	</portlet:actionURL>
	
<div class="acticons">
	<%
		if(larntype.hasEditDetails() && !disabled){
			AssetRenderer  assetRenderer=larntype.getAssetRenderer(learnact);
			if(assetRenderer!=null) {
				liferayPortletRequest.setAttribute(LearningActivityBaseAssetRenderer.EDIT_DETAILS, true);
				PortletURL urlEditDetails = assetRenderer.getURLEdit(liferayPortletRequest, liferayPortletResponse);

				if(Validator.isNotNull(urlEditDetails)){
					if(learnact.getModuleId()>0){
						urlEditDetails.setWindowState(LiferayWindowState.NORMAL);	
					}else{
						urlEditDetails.setWindowState(LiferayWindowState.POP_UP);
					}
					String urlEdit = urlEditDetails.toString();
					Portlet urlEditPortlet = PortletLocalServiceUtil.getPortletById(HttpUtil.getParameter(urlEdit, "p_p_id",false));
					
					PublicRenderParameter actIdPublicParameter = urlEditPortlet.getPublicRenderParameter("actId");
					if(Validator.isNotNull(actIdPublicParameter)) {
						urlEdit=HttpUtil.removeParameter(urlEdit,PortletQNameUtil.getPublicRenderParameterName(actIdPublicParameter.getQName()));
					}
					
					urlEdit=HttpUtil.removeParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resId");
					urlEdit=HttpUtil.addParameter   (urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resId", Long.toString(learnact.getActId()));
					urlEdit=HttpUtil.removeParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resModuleId");
					urlEdit=HttpUtil.addParameter   (urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resModuleId", Long.toString(learnact.getModuleId()) );
					urlEdit=HttpUtil.removeParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"actionEditingDetails");
					urlEdit=HttpUtil.addParameter   (urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"actionEditingDetails", true);
					%>
					
					<portlet:actionURL name="editDetailsURL" var="editDetailsURL">
						<portlet:param name="redirectURL" value="<%=urlEdit %>" />
					</portlet:actionURL>
					
					<liferay-ui:icon image="edit" message="<%=larntype.getMesageEditDetails()%>" label="true" 
									 url="<%=editDetailsURL%>" />
					<% 
				}
			}
		}
		else{
			if(larntype.hasEditDetails()){
			%>
			<liferay-ui:icon image="edit" message="<%=larntype.getMesageEditDetails()%>" label="true" />
			<% 
			}
		}
		if(larntype.hasDeleteTries() && isCourse) {
			%>
			<liferay-ui:icon-delete label="true" url="<%=deleteMyTriesURL.toString()%>" message="delete-mi-tries" />	
			<%		
		}
		if(larntype.allowsDeleteBank() && !isCourse){
			%>
			<liferay-ui:icon-delete label="true" url="<%=deleteActivityBankURL.toString()%>" message="delete" />	
			<%
		}
	%>
</div>
	<% 
	 if(Validator.isNotNull(title)){
		 learnact.setTitle(title, themeDisplay.getLocale());
	 }
	
	%>
	<aui:model-context bean="<%= learnact %>" model="<%= LearningActivity.class %>" />

<%
}else{
	learnact = new LearningActivityImpl();
	learnact.setTitle(title, themeDisplay.getLocale());
	Long defaultScore = larntype.getDefaultScore();
	learnact.setPasspuntuation(defaultScore.intValue());
	learnact.setTries(larntype.getDefaultTries());
	%>
	<aui:model-context  bean="<%= learnact %>"  model="<%= LearningActivity.class %>" />
	<%
}
%>
<aui:script use="node, event, node-event-simulate">
<!-- 
	var enabledStart = document.getElementById('<%=renderResponse.getNamespace() %>startdate-enabledCheckbox').checked;
	A.all("#startDate").one('div.aui-datepicker').simulate("click");
	if(enabledStart){
		A.all("#startDate").one(".aui-datepicker-button-wrapper").show();
		A.all("#startDate").one("#startDateSpan").removeClass('aui-helper-hidden');
	}else  A.all("#startDate").one(".aui-datepicker-button-wrapper").hide();

	var enabledEnd = document.getElementById('<%=renderResponse.getNamespace() %>stopdate-enabledCheckbox').checked;
	A.all("#endDate").one('div.aui-datepicker').simulate("click");
	if(enabledEnd){
		A.all("#endDate").one(".aui-datepicker-button-wrapper").show();
		A.all("#endDate").one("#endDateSpan").removeClass('aui-helper-hidden');
	}else A.all("#endDate").one(".aui-datepicker-button-wrapper").hide();
	//-->
</aui:script>
<script type="text/javascript">
<!--

AUI().ready('node-base' ,'aui-form-validator', 'aui-overlay-context-panel', 'widget-locale', 'event', 'node-event-simulate', function(A) {
	
	if ((!!window.postMessage)&&(window.parent != window)) {
		if (!window.location.origin){
			window.location.origin = window.location.protocol+"//"+window.location.host;
		}
		
		if(AUI().UA.ie==0) {
			parent.postMessage({name:'setTitleActivity',
				                moduleId:<%=Long.toString(moduleId)%>,
				                actId:<%=Long.toString(actId)%>,
				                title:'<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, actId==0?"activity.creation":"activity.edition"))+" "+ 
				                	UnicodeFormatter.toString(LanguageUtil.get(pageContext, new LearningActivityTypeRegistry().getLearningActivityType(typeId).getName()))%>'}, window.location.origin);
		}
		else {
			parent.postMessage(JSON.stringify({name:'setTitleActivity',
                							   moduleId:<%=Long.toString(moduleId)%>,
                							   actId:<%=Long.toString(actId)%>,
                							   title:'<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext,actId==0?"activity.creation":"activity.edition"))+" "+ 
                								   UnicodeFormatter.toString(LanguageUtil.get(pageContext, new LearningActivityTypeRegistry().getLearningActivityType(typeId).getName()))%>'}), window.location.origin);
		}
	}

	try{
		<% if(larntype.hasEditDetails()){ %>
			A.one('.taglib-icon').focus();
		<% } %>
	}catch (err){
		
	}
	
	

	var rules = {			
			<portlet:namespace />title_<%=renderRequest.getLocale().toString()%>: {
				required: true,
				maxLength: 75
			},
        	<portlet:namespace />description: {
        		required: false
            }
			<% if(larntype.isTriesConfigurable()) { %>
			,<portlet:namespace />tries: {
				number: true,
				range: [0,100],
				required: true
			}
			<% } if(larntype.isScoreConfigurable()) { %>
			,<portlet:namespace />passpuntuation: {
				required: true,
				number: true,
				range: [0,100]
			}
			<% } %>
		};

	var fieldStrings = {			
        	<portlet:namespace />title_<%=renderRequest.getLocale().toString()%>: {
        		required: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "activity-title-required")) %>'
            },
        	<portlet:namespace />description: {
        		required: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "description-required")) %>'
            }
			<% if(larntype.isTriesConfigurable()) { %>
        	,<portlet:namespace />tries: {
        		required: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "editActivity.tries.required")) %>',
        		number: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "editActivity.tries.number")) %>',
        		range: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "editActivity.tries.range")) %>',       		
            }
			<% } if(larntype.isScoreConfigurable()) { %>
        	,<portlet:namespace />passpuntuation: {
        		required: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "editActivity.passpuntuation.required")) %>',
        		number: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "editActivity.passpuntuation.number")) %>',
        		range: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "editActivity.passpuntuation.range")) %>',       		
            }
			<% } %>
			
			<% if(larntype.getTypeId() == 120) { %>
        	,<portlet:namespace />time: {
        		required: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "learningactivity.connect.error.time")) %>',
        		number: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "learningactivity.connect.error.time.nan")) %>',
            }
        	,<portlet:namespace />timepass: {
        		required: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "learningactivity.connect.error.timepass")) %>',
        		number: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "learningactivity.connect.error.timepass.nan")) %>',
            }
        	<% } %>
		};
	
	A.each(A.Object.keys(window), function(fieldName){
		var field=window[fieldName];
	    if((fieldName.indexOf('<portlet:namespace />validate_')==0) &&
	       (A.Lang.isObject(field))&&
	       (A.Object.hasKey(field,'rules'))&&
	       (A.Object.hasKey(field,'fieldStrings'))) {
			A.mix(rules,field.rules,true);
			A.mix(fieldStrings,field.fieldStrings,true);
		}
	});

	
	window.<portlet:namespace />validateActivity = new A.FormValidator({
		boundingBox: '#<portlet:namespace />fm',
		validateOnBlur: true,
		validateOnInput: true,
		selectText: true,
		showMessages: false,
		containerErrorClass: '',
		errorClass: '',
		rules: rules,
        fieldStrings: fieldStrings,
		
		on: {		
            errorField: function(event) {
            	var instance = this;
				var field = event.validator.field;
				var divError = A.one('#'+field.get('name')+'Error');
				if(divError) {
					divError.addClass('portlet-msg-error');
					divError.setContent(instance.getFieldErrorMessage(field,event.validator.errors[0]));
				}
            },		
            validField: function(event) {
				var divError = A.one('#'+event.validator.field.get('name')+'Error');
				if(divError) {
					divError.removeClass('portlet-msg-error');
					divError.setContent('');
				}
            }
		}
	});
	if(A.one('#<portlet:namespace/>resModuleId')){
		A.one('#<portlet:namespace/>resModuleId').scrollIntoView();
	};
});



	function getElementByNameStart(str){
		var x=document.getElementsByTagName('input');
		var a=[];
		for(var i=0;i<x.length;i++){
	  		if(x[i].id.indexOf(str)==0){
	   			a.push(x[i]);
	  		}
		}
		return a;
	}

	function <portlet:namespace/>validate(){
		
		var startInput = document.getElementById('<portlet:namespace />startdate-enabledCheckbox');
		var start = startInput != null ? startInput.checked : true;
		var stopInput = document.getElementById('<portlet:namespace />stopdate-enabledCheckbox');
		var stop = stopInput != null ? stopInput.checked : true;
		var i = 0;
		var maxSize = document.getElementById('<portlet:namespace />maxSize').value;
		var file_inputs =getElementByNameStart("<portlet:namespace />additionalFile");
		for(i=0; i<file_inputs.length; i++){
			if(file_inputs[i].files!=null){
				if(file_inputs[i].files[0]!=null){
					if (typeof file_inputs[i].files[0] != 'undefined'){
						if(maxSize<file_inputs[i].files[0].size){
							alert("<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "server-file-size-upload-exceeded")) %>");	
							return false;
						}	
					}				
				}
				
			}
		}
		
		
		
		if(start&&stop){	
					
			var start = getStartDate();
			var end = getEndDate();
					
			if(start.getTime()>=end.getTime()){
				alert("<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "please-enter-a-start-date-that-comes-before-the-end-date")) %>");
				return false;
			}
// 			else{
// 				if(document.getElementById('<portlet:namespace />uploadDay')!=null){
// 					var upload = getUpdateDate();
// 					if(start.getTime()>upload.getTime()||upload.getTime()>end.getTime()){
// 						alert("<liferay-ui:message key="please-enter-a-correct-update-date" />");
// 						return;
// 					}
// 				}
// 			}
// 		}else if(start){
// 			if(document.getElementById('<portlet:namespace />uploadDay')!=null){
// 				var start = getStartDate();
// 				var upload = getUpdateDate();
				
// 				if(start.getTime()>upload.getTime()){
// 					alert("<liferay-ui:message key="please-enter-a-correct-update-date" />");
// 					return;
// 				}
// 			}
// 		}else if(stop){
// 			if(document.getElementById('<portlet:namespace />uploadDay')!=null){
// 				var end = getEndDate();
// 				var upload = getUpdateDate();

// 				if(upload.getTime()>end.getTime()){
// 					alert("<liferay-ui:message key="please-enter-a-correct-update-date" />");
// 					return;
// 				}
// 			}
// 		}else{
// 			if(document.getElementById('<portlet:namespace />uploadDay')!=null){
<%-- 				var startm = new Date(<%= module.getStartDate().getTime() %>); --%>
<%-- 				var endm = new Date(<%= module.getEndDate().getTime() %>); --%>
// 				var upload = getUpdateDate();

// 				if(startm.getTime()>upload.getTime()||upload.getTime()>endm.getTime()){
// 					alert("<liferay-ui:message key="please-enter-a-correct-update-date" />");
// 					return;
// 				}
// 			}
 		}


		var form = document.getElementById('<portlet:namespace />fm');
		var inputsform = form.getElementsByTagName("input");
		var selector = document.getElementById('dpcqlanguageSelector');
		if(selector){
			var parents = selector.getElementsByClassName("lfr-form-row");
			for (var i=0; i < parents.length; i++){
				if(!parents[i].className.match(/.*hidden.*/)){
					var inputs = parents[i].getElementsByTagName("input");
					for (var j=0; j < inputs.length; j++){
						var input = document.createElement('input');
					    input.type = 'hidden';
					    input.name = inputs[j].name;
					    input.id = inputs[j].id;
					    input.value = inputs[j].value;
					    form.appendChild(input);
					}
				}
			}
		}
		return true;
	}

	function getStartDate(){
		var startDateDia = document.getElementById('<portlet:namespace />startDay').value;
		var startDateMes = document.getElementById('<portlet:namespace />startMon').value;
		var startDateAno = document.getElementById('<portlet:namespace />startYear').value;
		var startDateHora = document.querySelectorAll('[name="<portlet:namespace />startHour"]')[0].value;
		var startDateMinuto = document.querySelectorAll('[name="<portlet:namespace />startMin"]')[0].value;

		var start = new Date(startDateAno,startDateMes,startDateDia,startDateHora,startDateMinuto);
		return start;
	}
	
	function getEndDate(){
		var endDateDia = document.getElementById('<portlet:namespace />stopDay').value;
		var endDateMes = document.getElementById('<portlet:namespace />stopMon').value;
		var endDateAno = document.getElementById('<portlet:namespace />stopYear').value;
		var endDateHora = document.querySelectorAll('[name="<portlet:namespace />stopHour"]')[0].value;
		var endDateMinuto = document.querySelectorAll('[name="<portlet:namespace />stopMin"]')[0].value;

		var end = new Date(endDateAno,endDateMes,endDateDia,endDateHora,endDateMinuto);
		return end;
	}
	
	function getUpdateDate(){
		var uploadDateDia = document.getElementById('<portlet:namespace />uploadDay').value;
		var uploadDateMes = document.getElementById('<portlet:namespace />uploadMon').value;
		var uploadDateAno = document.getElementById('<portlet:namespace />uploadYear').value;
		var uploadDateHora = document.querySelectorAll('[name="<portlet:namespace />uploadHour"]')[0].value;
		var uploadDateMinuto = document.querySelectorAll('[name="<portlet:namespace />uploadMin"]')[0].value;

		var upload = new Date(uploadDateAno,uploadDateMes,uploadDateDia,uploadDateHora,uploadDateMinuto);
		return upload;
	}
	
	Liferay.provide(
			window,
			'<portlet:namespace/>initializeActivityDates',
			function() {
				var A = AUI(); 
				A.one('select[name="<portlet:namespace />startDay"]').ancestor('div.aui-datepicker').simulate("click");
				A.one('select[name="<portlet:namespace />stopDay"]').ancestor('div.aui-datepicker').simulate("click");
			},
			['node', 'event', 'node-event-simulate']
	);
//-->
</script>
<aui:form name="fm" action="<%=saveactivityURL%>"  method="post"   onSubmit="event.preventDefault();${renderResponse.getNamespace()}validateForm();" enctype="multipart/form-data">
	<aui:fieldset>
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="maxSize" type="hidden" value="<%= fileMaxSize %>" />
		<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
		<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	
<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />validateForm',
		function() {
			if(<portlet:namespace />validate()){
				submitForm(document.<portlet:namespace />fm);
			}
			
		},
		['liferay-util-list-fields']
	);
</aui:script>	
	
<script type="text/javascript">
<!--

Liferay.provide(
        window,
        '<portlet:namespace />reloadComboActivities',
        function(moduleId) {
        	var A = AUI();
			var renderUrl = Liferay.PortletURL.createRenderURL();							
			renderUrl.setWindowState('<%= LiferayWindowState.NORMAL.toString() %>');
			renderUrl.setPortletId('<%=portletDisplay.getId()%>');
			renderUrl.setParameter('jspPage','/html/editactivity/comboActivities.jsp');
			renderUrl.setParameter('resId','<%=Long.toString(actId) %>');
			renderUrl.setParameter('resModuleId',moduleId);
			renderUrl.setParameter('firstLoad',false);
			renderUrl.setParameter('precedence','<%=Long.toString((learnact!=null)?learnact.getPrecedence():0) %>');

			A.io.request(renderUrl.toString(),
			{  
			  dataType : 'html',	 
			  on: 
				{   
				  	success: function() { 
						A.one('#<portlet:namespace />precedence').
							replace(A.Node.create(this.get('responseData')).one('#<portlet:namespace />precedence'));
					}
				}
			}); 
			
        },
        ['liferay-portlet-url']
    );
    
//-->
</script>

	<c:if test="<%=!ParamUtil.getBoolean(renderRequest,\"noModule\",false) && isCourse %>">
		<aui:select id="resModuleId" label="module" name="resModuleId" onChange="<%=renderResponse.getNamespace()+\"reloadComboActivities(this.options[this.selectedIndex].value);\" %>">
		<%
			java.util.List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
			for(Module theModule:modules)
			{
				boolean selected=false;
				if(learnact!=null && learnact.getModuleId()==theModule.getModuleId())
				{
					selected=true; 
				}
				else
				{
					if(theModule.getModuleId()==moduleId)
					{
						selected=true;
					}
				}
				%>
					<aui:option value="<%=theModule.getModuleId() %>" selected="<%=selected %>"><%=theModule.getTitle(themeDisplay.getLocale()) %></aui:option>
				<% 
			}
		%>

		</aui:select>
	</c:if>

<%
	if(actId==0)
	{
	%>
	<aui:input type="hidden" name="type" value="<%=typeId %>"></aui:input>
	<% 
	}
	%>
		<aui:input name="title" label="title" defaultLanguageId="<%=renderRequest.getLocale().toString()%>" id="title">
		</aui:input>
		
		<div id="<portlet:namespace />title_<%=renderRequest.getLocale().toString()%>Error" class="<%=(SessionErrors.contains(renderRequest, "activity-title-required"))?
	    														"portlet-msg-error":StringPool.BLANK %>">
	    	<%=(SessionErrors.contains(renderRequest, "activity-title-required"))?
	    			LanguageUtil.get(pageContext,"activity-title-required"):StringPool.BLANK %>
	    </div>
	    
	       <script type="text/javascript">
		<!--
			Liferay.provide(
		        window,
		        '<portlet:namespace />onChangeDescription',
		        function(val) {
		        	var A = AUI();
					A.one('#<portlet:namespace />description').set('value',val);
					if(window.<portlet:namespace />validateActivity){
						window.<portlet:namespace />validateActivity.validateField('<portlet:namespace />description');
					}
		        },
		        ['node']
		    );
		    
		-->
		</script>
	    
		<aui:field-wrapper label="description" name="description">
			<liferay-ui:input-editor toolbarSet="actliferay" name="description" width="100%" onChangeMethod="onChangeDescription" />
			<script type="text/javascript">
		        function <portlet:namespace />initEditor() 
		        { 
					return decodeURI('<%= UnicodeFormatter.toString(description.replace("%26","&").replace("%23","#").replace("%","%25")) %>');		        }
		    </script>
		</aui:field-wrapper>
		<div id="<portlet:namespace />descriptionError" class="<%=(SessionErrors.contains(renderRequest, "description-required"))?
	    														"portlet-msg-error":StringPool.BLANK %>">
	    	<%=(SessionErrors.contains(renderRequest, "description-required"))?
	    			LanguageUtil.get(pageContext,"description-required"):StringPool.BLANK %>
	    </div>
	    
	    <%
			boolean optional=false;
			boolean mandatory = true;
			if(learnact!=null)
			{
				request.setAttribute("activity", learnact);
				request.setAttribute("activityId", learnact.getActId());
				optional=(learnact.getWeightinmodule()==0);
				mandatory = (learnact.getWeightinmodule() != 0);
			}
		%>
		<div id="<portlet:namespace />mandatorylabel" style="display:none">
			<aui:field-wrapper label="editactivity.mandatory" cssClass="editactivity-mandatory-field" name="mandatorylabel">
				<aui:input label="editactivity.mandatory.yes" type="radio" name="weightinmodule" value="1" checked="<%= mandatory %>" inlineField="true" />
				<aui:input label="editactivity.mandatory.no" type="radio" name="weightinmodule" value="0" checked="<%= !mandatory %>" inlineField="true" />
				<aui:input type="hidden" name="mandatorylabel" />
			</aui:field-wrapper>
		</div>
	 <liferay-ui:panel-container extended="false" persistState="false">
	 <%
	 boolean showSpecificPanel = larntype.isTriesConfigurable() || larntype.isScoreConfigurable() || larntype.isFeedbackCorrectConfigurable() || 
	 								larntype.isFeedbackNoCorrectConfigurable() || larntype.getExpecificContentPage()!=null;
	 if(showSpecificPanel){
	 
		 String defaultState="open";
		 if(actId>0&&renderRequest.getAttribute("preferencesOpen")==null)
		 {
			 defaultState="closed";
		 }
	 	%>
			<div id="<portlet:namespace />activity" style="display:none">
	 			<liferay-ui:panel title="activity-specifics" collapsible="true" defaultState="<%=defaultState %>">
	  
		<%
		if(larntype.isTriesConfigurable())
		{
			long tries=larntype.getDefaultTries();
			if(learnact!=null)
			{
				tries=learnact.getTries();
			}
		%>
		
		<aui:input size="5" name="tries" label="tries" value="<%=Long.toString(tries) %>" type="number"  disabled="<%=disabled%>">
			<aui:validator name="min" errorMessage="editActivity.tries.range">-1</aui:validator>
		</aui:input><%--liferay-ui:icon-help message="number-of-tries"></liferay-ui:icon-help--%>
  		<div id="<portlet:namespace />triesError" class="<%=((SessionErrors.contains(renderRequest, "editActivity.tries.required"))||
														      (SessionErrors.contains(renderRequest, "editActivity.tries.number"))||
														      (SessionErrors.contains(renderRequest, "editActivity.tries.range")))?
   														      "portlet-msg-error":StringPool.BLANK %>">
   			<%=(SessionErrors.contains(renderRequest, "editActivity.tries.required"))?
   			    	LanguageUtil.get(pageContext,"editActivity.tries.required"):
 			   (SessionErrors.contains(renderRequest, "editActivity.tries.number"))?
 		    		LanguageUtil.get(pageContext,"editActivity.tries.number"):
 			   (SessionErrors.contains(renderRequest, "editActivity.tries.range"))?
 		    		LanguageUtil.get(pageContext,"editActivity.tries.range"):StringPool.BLANK %>
	    </div>
		<%
		}
		else
		{
			%>
			<aui:input type="hidden" name="tries" value="<%=larntype.getDefaultTries() %>" />
			<% 
		}
				
		if(larntype.isScoreConfigurable())
		{
			
			long score = Long.parseLong(ParamUtil.getString(request, "passpuntuation","0"));
			if(score==0){
				score=Long.valueOf(larntype.getDefaultScore());
				if(learnact!=null)
				{
					score=learnact.getPasspuntuation();
				}
			}
			String passpuntuationLabelProperty = "passpuntuation";
			String passpunctuationHelpProperty= "editActivity.passpuntuation.help";
			%>
			<aui:input size="5" name="passpuntuation" label="<%=passpuntuationLabelProperty %>" type="number" value="<%=Long.toString(score) %>" disabled="<%=disabled %>" helpMessage="<%=LanguageUtil.get(pageContext, passpunctuationHelpProperty)%>">
				<aui:validator name="custom" errorMessage="editActivity.passpuntuation.range">
					function(val,fieldNode,ruleValue){
						var result = false;
						if(val >= 0 && val <= 100){
							result = true;
						}
						return result;
					}
				</aui:validator>
			</aui:input>
			<% if (disabled) { %>
				<input name="<portlet:namespace />passpuntuation" type="hidden" value="<%=Long.toString(score) %>" />
			<% } %>
	  		<div id="<portlet:namespace />passpuntuationError" class="<%=((SessionErrors.contains(renderRequest, "editActivity.passpuntuation.required"))||
																	      (SessionErrors.contains(renderRequest, "editActivity.passpuntuation.number"))||
																	      (SessionErrors.contains(renderRequest, "editActivity.passpuntuation.range")))?
		    														      "portlet-msg-error":StringPool.BLANK %>">
		    	<%=(SessionErrors.contains(renderRequest, "editActivity.passpuntuation.required"))?
		    			LanguageUtil.get(pageContext,"editActivity.passpuntuation.required"):
	   			   (SessionErrors.contains(renderRequest, "editActivity.passpuntuation.number"))?
	   		    		LanguageUtil.get(pageContext,"editActivity.passpuntuation.number"):
	   			   (SessionErrors.contains(renderRequest, "editActivity.passpuntuation.range"))?
	   		    		LanguageUtil.get(pageContext,"editActivity.passpuntuation.range"):StringPool.BLANK %>
		    </div>
		<%
		}
		%>
		
		
		
	
		<%
		if(larntype.isFeedbackCorrectConfigurable())
		{
			String  feedbacCorrect=larntype.getDefaultFeedbackCorrect();
			if(learnact!=null)
			{
				feedbacCorrect=learnact.getFeedbackCorrect();
			}
		%>	
		<aui:input name="feedbackCorrect" label="feedbackCorrect" value="<%=feedbacCorrect %>" helpMessage="feedbackCorrect.helpMessage" type="text" maxLength="<%=ModelHintsUtil.getHints(LearningActivity.class.getName(), \"feedbackCorrect\").get(\"max-length\") %>" ></aui:input>	
		<%
		}
		else
		{
			%>
			<aui:input type="hidden" name="feedbackCorrect" value="<%=larntype.getDefaultFeedbackCorrect() %>" helpMessage="feedbackCorrect.helpMessage"/>
			<% 
		}
		if(larntype.isFeedbackNoCorrectConfigurable())
		{
			String  feedbacNoCorrect=larntype.getDefaultFeedbackNoCorrect();
			if(learnact!=null)
			{
				feedbacNoCorrect=learnact.getFeedbackNoCorrect();
			}
		%>
		<aui:input name="feedbackNoCorrect" label="feedbackNoCorrect" value="<%=feedbacNoCorrect %>" helpMessage="feedbackNoCorrect.helpMessage" type="text"  maxLength="<%=ModelHintsUtil.getHints(LearningActivity.class.getName(), \"feedbackNoCorrect\").get(\"max-length\") %>" ></aui:input>	
		<%
		}
		else
		{
			%>
			<aui:input type="hidden" name="feedbackNoCorrect" value="<%=larntype.getDefaultFeedbackNoCorrect() %>" helpMessage="feedbackNoCorrect.helpMessage"/>
			<% 
		}
		
		%>

		
		<% if(larntype.getExpecificContentPage()!=null) { %>
			<liferay-util:include page="<%=larntype.getExpecificContentPage() %>" servletContext="<%=getServletContext() %>" portletId="<%= larntype.getPortletId() %>">
				<liferay-util:param name="resId" value="<%=Long.toString(actId) %>" />
				<liferay-util:param name="resModuleId" value="<%=Long.toString(moduleId) %>" />
			</liferay-util:include>	
		<% } %>
	</liferay-ui:panel>
	</div>
<%}
	String actCondefaultState="closed";
	if(larntype.hasMandatoryDates())
	{
		actCondefaultState="open";
	}
	%>
	 
	 <liferay-ui:panel title="activity-constraints" collapsible="true" defaultState="<%=actCondefaultState %>">
	   
	    <script type="text/javascript">


		    
		    function setStarDateState(){
		    	AUI().use('node',function(A) {
			    	var enabled = document.getElementById('<%=renderResponse.getNamespace() %>startdate-enabledCheckbox').checked; 
		    		var selector = 'form[name="<%=renderResponse.getNamespace() %>fm"]';
		    		
		    		if(enabled) {
		    			A.all("#startDate").one(".aui-datepicker-button-wrapper").show();
		    			A.all("#startDate").one("#startDateSpan").removeClass('aui-helper-hidden');
		    		}else {
		    			A.all("#startDate").one(".aui-datepicker-button-wrapper").hide();
		    			A.all("#startDate").one("#startDateSpan").addClass('aui-helper-hidden');
		    		}
		    	});
		    }
		    
		    function setStopDateState(){
		    	AUI().use('node',function(A) {
			    	var enabled = document.getElementById('<%=renderResponse.getNamespace() %>stopdate-enabledCheckbox').checked; 

		    		var selector = 'form[name="<%=renderResponse.getNamespace() %>fm"]';
		    		
		    		if(enabled) {
		    			A.all("#endDate").one(".aui-datepicker-button-wrapper").show();
		    			A.all("#endDate").one("#endDateSpan").removeClass('aui-helper-hidden');
		    		}else {
		    			A.all("#endDate").one(".aui-datepicker-button-wrapper").hide();
		    			A.all("#endDate").one("#endDateSpan").addClass('aui-helper-hidden');
		    		}
		    	});
		    }

	    </script>
		<div id="startDate">
			<aui:field-wrapper label="start-date">
				<% if (!larntype.hasMandatoryDates()) { %>
					<aui:input id="startdate-enabled" name="startdate-enabled" checked="<%=learnact != null && !learnact.isNullStartDate() %>" type="checkbox" label="editActivity.startdate.enabled" onClick="setStarDateState();" helpMessage="editActivity.startdate.enabled.help"  ignoreRequestValue="true" />
					<div id="startDateSpan" class="aui-helper-hidden">
						<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"  dayParam="startDay" monthParam="startMon"
						 yearParam="startYear"  yearNullable="false" dayNullable="false" monthNullable="false" yearValue="<%=startYear %>" monthValue="<%=startMonth %>" dayValue="<%=startDay %>"></liferay-ui:input-date>
						 <liferay-ui:input-time minuteParam="startMin" amPmParam="startAMPM" hourParam="startHour" hourValue="<%=startHour %>" minuteValue="<%=startMin %>"></liferay-ui:input-time>
					</div>
				<% } else { %>
				<aui:input id="startdate-enabled" name="startdate-enabled" checked="true" disabled="true"  type="checkbox" label="editActivity.startdate.enabled" onClick="setStarDateState();" helpMessage="editActivity.startdate.enabled.help"  ignoreRequestValue="true" />
					<div id="startDateSpan" class="aui-helper-hidden">
					<aui:input id="startdate-enabled" name="startdate-enabled" type="hidden" value="true" ignoreRequestValue="true" />
					<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"  dayParam="startDay" monthParam="startMon" 
					 yearParam="startYear"  yearNullable="false" dayNullable="false" monthNullable="false" yearValue="<%=startYear %>" monthValue="<%=startMonth %>" dayValue="<%=startDay %>"></liferay-ui:input-date>
					 <liferay-ui:input-time minuteParam="startMin" amPmParam="startAMPM" hourParam="startHour" hourValue="<%=startHour %>" minuteValue="<%=startMin %>"></liferay-ui:input-time>
					 </div>
				<% } %>
			</aui:field-wrapper>
		</div>
		
		<div id="endDate">
			<aui:field-wrapper label="end-date">
				<% if (!larntype.hasMandatoryDates()) { %>
					<aui:input id="stopdate-enabled" name="stopdate-enabled" checked="<%=learnact != null && !learnact.isNullEndDate() %>" type="checkbox" label="editActivity.stopdate.enabled" onClick="setStopDateState();" helpMessage="editActivity.stopdate.enabled.help"  ignoreRequestValue="true" />
					<div id="endDateSpan" class="aui-helper-hidden">
						<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>" dayParam="stopDay" monthParam="stopMon" 
						 yearParam="stopYear"  yearNullable="false" dayNullable="false" monthNullable="false"  yearValue="<%=endYear %>" monthValue="<%=endMonth %>" dayValue="<%=endDay %>"></liferay-ui:input-date>
						 <liferay-ui:input-time minuteParam="stopMin" amPmParam="stopAMPM" hourParam="stopHour"  hourValue="<%=endHour %>" minuteValue="<%=endMin %>"></liferay-ui:input-time>
					</div>
				<% } else { %>
				<aui:input id="stopdate-enabled" name="stopdate-enabled" checked="true" disabled="true" type="checkbox" label="editActivity.stopdate.enabled" onClick="setStopDateState();" helpMessage="editActivity.stopdate.enabled.help"  ignoreRequestValue="true" />
					<div id="endDateSpan" class="aui-helper-hidden">
					<aui:input id="stopdate-enabled" name="stopdate-enabled" type="hidden" value="true" ignoreRequestValue="true" />
					<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>" dayParam="stopDay" monthParam="stopMon"
					 yearParam="stopYear"  yearNullable="false" dayNullable="false" monthNullable="false"  yearValue="<%=endYear %>" monthValue="<%=endMonth %>" dayValue="<%=endDay %>"></liferay-ui:input-date>
					 <liferay-ui:input-time minuteParam="stopMin" amPmParam="stopAMPM" hourParam="stopHour"  hourValue="<%=endHour %>" minuteValue="<%=endMin %>"></liferay-ui:input-time>
					 </div>
				<% } %>
			</aui:field-wrapper>
		</div>
		
		<aui:field-wrapper label="bloquing-activity" helpMessage="<%=LanguageUtil.get(pageContext,\"helpmessage.precedence\")%>">
		
		
		<c:if test="<%=!ParamUtil.getBoolean(renderRequest,\"noModule\",false) %>">
		<aui:select id="resModuleId2" label="module"  name="resModuleId2" inlineLabel="true" onChange="<%=renderResponse.getNamespace()+\"reloadComboActivities(this.options[this.selectedIndex].value);\" %>">
		<%
			if(actId == 0 || learnact.getPrecedence()<= 0){%>
				<aui:option selected="true" value="0" ><%=LanguageUtil.get(pageContext,"none")%></aui:option>

			<%}
			java.util.List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
			for(Module theModule:modules){
				boolean selected = false;

				if (learnact != null && learnact.getPrecedence() != 0) {
					LearningActivity larnPrecedence = LearningActivityLocalServiceUtil.getLearningActivity(learnact.getPrecedence());
					long modulePrecedenceId = larnPrecedence.getModuleId();
					
					if (larnPrecedence != null && modulePrecedenceId == theModule.getModuleId()) {
						selected = true;
					} 
				} else {
					if (learnact != null && learnact.getModuleId() == theModule.getModuleId()) {
						selected = true;
					} else {
						if (theModule.getModuleId() == moduleId) {
							selected = true;
						}
					}
			}
				
				if(actId>0 && learnact.getPrecedence()>0){
		%>
					<aui:option value="<%=theModule.getModuleId() %>" selected="<%=selected %>"><%=theModule.getTitle(themeDisplay.getLocale()) %></aui:option>
				<% 
				}else{
					%>
					<aui:option value="<%=theModule.getModuleId() %>"><%=theModule.getTitle(themeDisplay.getLocale()) %></aui:option>
				<%
				}
			}
		%>

		</aui:select>
	</c:if>
		</aui:field-wrapper>
		<liferay-util:include page="/html/editactivity/comboActivities.jsp" servletContext="<%=getServletContext() %>">
			<liferay-util:param name="resId" value="<%=Long.toString(actId) %>" />
			<liferay-util:param name="resModuleId" value="<%=Long.toString(moduleId) %>" />
			<liferay-util:param name="precedence" value="<%=Long.toString((learnact!=null)?learnact.getPrecedence():0) %>" />
		</liferay-util:include>
		
		<%
			
		if(larntype.getTypeId()!=8 && !TeamLocalServiceUtil.getGroupTeams(themeDisplay.getScopeGroupId()).isEmpty()){ %>		
			<liferay-util:include page="/html/editactivity/comboTeams.jsp" servletContext="<%=getServletContext() %>">
				<liferay-util:param name="resId" value="<%=Long.toString(actId) %>" />
				<liferay-util:param name="teamId" value='<%=(learnact!=null)?LearningActivityLocalServiceUtil.getExtraContentValue(actId,"team"):Long.toString(0) %>' />
			</liferay-util:include>
		<%}
		%>
		</liferay-ui:panel>
		<%
		boolean showCategorization = ("false".equals(PropsUtil.get("activity.show.categorization")))?false:true;
		%>
		<c:if test="<%= showCategorization %>">
			<c:choose>
				<c:when test="<%=isCourse %>">
					<liferay-ui:panel title="categorization" collapsible="true" defaultState="closed">
						<aui:input name="tags" type="assetTags" />
						<aui:input name="categories" type="assetCategories" />
					</liferay-ui:panel>
				</c:when>
				<c:otherwise>
					<liferay-ui:panel title="categorization" collapsible="true" defaultState="open">
						<aui:input name="tags" type="assetTags" />
						<aui:input name="categories" type="assetCategories" />
					</liferay-ui:panel>
				</c:otherwise>
			</c:choose>
		</c:if>
	</liferay-ui:panel-container>
	</aui:fieldset>
	
	<aui:button-row>
		<aui:button type="submit" value="save"/>
		
		<%
		
		if(actId > 0 && moduleId>0){
			AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());

		%>
					
			<portlet:actionURL var="goToActivityURL" name="goToActivity" >
				<portlet:param name="redirectURL" value="<%=assetRendererFactory.getAssetRenderer(actId).
						getURLView(liferayPortletResponse, WindowState.NORMAL).toString() %>" />
			</portlet:actionURL>
	
			<aui:button type="button" value="go-to-activity" onclick="javascript:location.href='${goToActivityURL.toString()}'" />
		
		<%
		}
		%>
		
	</aui:button-row>
</aui:form>

<script type="text/javascript">
	
	AUI().ready(function(A) {
		
		var title = $("#portlet_editactivity_WAR_liferaylmsportlet").find(".portlet-title-text").text();
		title += " (<%= LanguageUtil.get(locale,typeName) %>)";
		$("#portlet_editactivity_WAR_liferaylmsportlet").find(".portlet-title-text").text(title);
	});

</script>

<%
	if (isCourse){
	%>
		<script type="text/javascript">
			AUI().ready(function(A) {
				if(A.one('#<portlet:namespace/>mandatorylabel')){
					A.one('#<portlet:namespace/>mandatorylabel').setStyle('display', 'block');
				}
				if(A.one('#<portlet:namespace/>resModuleId')){
					A.one('#<portlet:namespace/>resModuleId').show();
				}
				if(A.one('#<portlet:namespace/>restrictions')){
					A.one('#<portlet:namespace/>restrictions').setStyle('display', 'block');
				}
				if(A.one('#<portlet:namespace/>activity')){
					A.one('#<portlet:namespace/>activity').setStyle('display', 'block');
				}
			});
		</script>
	<%
	}
%>