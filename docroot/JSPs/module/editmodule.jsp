<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileVersion"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="java.util.Locale"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionMessages"%>
<%@page import="javax.portlet.WindowState"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@ page import="com.liferay.lms.model.Module" %>
<%@ page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.exception.SystemException"%>
<%@page import="com.liferay.lms.util.LmsConstant"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>

<%@include file="../init.jsp" %>

<jsp:useBean id="editmoduleURL" class="java.lang.String" scope="request" />
<jsp:useBean id="module" type="com.liferay.lms.model.Module" scope="request"/>
<jsp:useBean id="title" class="java.lang.String" scope="request" />
<jsp:useBean id="description" class="java.lang.String" scope="request" />
<jsp:useBean id="icon" class="java.lang.String" scope="request" />
<jsp:useBean id="startDateDia" class="java.lang.String" scope="request" />
<jsp:useBean id="startDateMes" class="java.lang.String" scope="request" />
<jsp:useBean id="startDateAno" class="java.lang.String" scope="request" />
<jsp:useBean id="startDateHora" class="java.lang.String" scope="request" />
<jsp:useBean id="startDateMinuto" class="java.lang.String" scope="request" />
<jsp:useBean id="endDateDia" class="java.lang.String" scope="request" />
<jsp:useBean id="endDateMes" class="java.lang.String" scope="request" />
<jsp:useBean id="endDateAno" class="java.lang.String" scope="request" />
<jsp:useBean id="endDateHora" class="java.lang.String" scope="request" />
<jsp:useBean id="endDateMinuto" class="java.lang.String" scope="request" />
<jsp:useBean id="allowedDateHora" class="java.lang.String" scope="request" />
<jsp:useBean id="allowedDateMinuto" class="java.lang.String" scope="request" />

<portlet:defineObjects />
<script type="text/javascript">
<!--
AUI().ready('node-base' ,'aui-form-validator', 'aui-overlay-context-panel', 'widget-locale', function(A) {
	
	window.<portlet:namespace />validateActivity = new A.FormValidator({
		boundingBox: '#<portlet:namespace />fm',
		validateOnBlur: true,
		validateOnInput: true,
		selectText: true,
		showMessages: false,
		containerErrorClass: '',
		errorClass: '',
		rules: {			
			<portlet:namespace />title_<%=renderRequest.getLocale().toString()%>: {
				required: true
			},
        	<portlet:namespace />description: {
        		required: false
            }
		},
        fieldStrings: {			
        	<portlet:namespace />title_<%=renderRequest.getLocale().toString()%>: {
        		required: Liferay.Language.get("module-title-required")
            },
        	<portlet:namespace />description: {
        		required: Liferay.Language.get("module-description-required")
            }
		},
		
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
});

function <portlet:namespace />setStartDateState(){
  	AUI().use('node',function(A) {
   	var enabled = document.getElementById('<portlet:namespace />startdate-enabledCheckbox').checked; 
  		if(enabled) {
  			A.all("#<portlet:namespace />startDate").one(".aui-datepicker-button-wrapper").show();
  			A.all("#<portlet:namespace />startDate").one("#<portlet:namespace />startDateSpan").removeClass('aui-helper-hidden');
  		}else {
  			A.all("#<portlet:namespace />startDate").one(".aui-datepicker-button-wrapper").hide();
  			A.all("#<portlet:namespace />startDate").one("#<portlet:namespace />startDateSpan").addClass('aui-helper-hidden');
  		}
  	});
}
function <portlet:namespace />setEndDateState(){
  	AUI().use('node',function(A) {
   	var enabled = document.getElementById('<portlet:namespace />enddate-enabledCheckbox').checked; 
  		if(enabled) {
  			A.all("#<portlet:namespace />endDate").one(".aui-datepicker-button-wrapper").show();
  			A.all("#<portlet:namespace />endDate").one("#<portlet:namespace />endDateSpan").removeClass('aui-helper-hidden');
  		}else {
  			A.all("#<portlet:namespace />endDate").one(".aui-datepicker-button-wrapper").hide();
  			A.all("#<portlet:namespace />endDate").one("#<portlet:namespace />endDateSpan").addClass('aui-helper-hidden');
  		}
  	});
}

function validate(){
	var startDateDia = document.getElementById('<portlet:namespace />startDateDia').value;
	var startDateMes = document.getElementById('<portlet:namespace />startDateMes').value;
	var startDateAno = document.getElementById('<portlet:namespace />startDateAno').value;
	var startDateHora = document.querySelectorAll('[name="<portlet:namespace />startDateHora"]')[0].value;
	var startDateMinuto = document.querySelectorAll('[name="<portlet:namespace />startDateMinuto"]')[0].value;

	var endDateDia = document.getElementById('<portlet:namespace />endDateDia').value;
	var endDateMes = document.getElementById('<portlet:namespace />endDateMes').value;
	var endDateAno = document.getElementById('<portlet:namespace />endDateAno').value;
	var endDateHora = document.querySelectorAll('[name="<portlet:namespace />endDateHora"]')[0].value;
	var endDateMinuto = document.querySelectorAll('[name="<portlet:namespace />endDateMinuto"]')[0].value;
	
	
	var start = new Date(startDateAno,startDateMes,startDateDia,startDateHora,startDateMinuto);
	var end = new Date(endDateAno,endDateMes,endDateDia,endDateHora,endDateMinuto);
	
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
	 	
		
		document.getElementById('<portlet:namespace />fm').submit();
	}
}



//-->
</script>
<%
	long moduleId=0;

	if(module.getModuleId()!=0){
		moduleId=module.getModuleId();
	}

%>

<aui:model-context bean="module" model="<%= Module.class %>" />
<liferay-ui:success key="module-added-successfully" message="module-added-successfully" />
<liferay-ui:success key="module-updated-successfully" message="module-updated-successfully" />
<%if ((SessionMessages.contains(renderRequest, "module-added-successfully"))|| 
	  (SessionMessages.contains(renderRequest, "module-updated-successfully"))) { %>
<script type="text/javascript">
<!--
	AUI().ready(function(A) {
		if ((!!window.postMessage)&&(window.parent != window)) {
			if (!window.location.origin){
				window.location.origin = window.location.protocol+"//"+window.location.host;
			}
			
			if(AUI().UA.ie==0) {
				parent.postMessage({name:'reloadModule',moduleId:<%=Long.toString(moduleId)%>}, window.location.origin);
			}
			else {
				parent.postMessage(JSON.stringify({name:'reloadModule',moduleId:<%=Long.toString(moduleId)%>}), window.location.origin);
			}
		}
	});
//-->
</script>

<% } %>

<aui:form name="fm" action="<%=editmoduleURL %>" method="POST" enctype="multipart/form-data" role="form">
	<input type="hidden" name="resourcePrimKey" value="<%=module.getPrimaryKey() %>">

<%
	if(moduleId>0){
%>	<aui:model-context bean="<%= module %>" model="<%= Module.class %>"/>
	<aui:input type="hidden" name="ordern" value="<%=Long.toString(module.getOrdern()) %>" />
<% 
	}else {
		%>	<aui:model-context bean="<%= module %>" model="<%= Module.class %>"/>
	<% 
	}
%>
	<aui:input name="title" label="title" defaultLanguageId="<%=renderRequest.getLocale().toString() %>" id="title">
	</aui:input>
	<div id="<portlet:namespace />title_<%=renderRequest.getLocale().toString()%>Error" class="<%=(SessionErrors.contains(renderRequest, "module-title-required"))?
    														"portlet-msg-error":StringPool.BLANK %>">
    	<%=(SessionErrors.contains(renderRequest, "module-title-required"))?
    			LanguageUtil.get(pageContext,"module-title-required"):StringPool.BLANK %>
    </div>
    	    
    
	<aui:field-wrapper label="description">
		<liferay-ui:input-editor name="description" width="100%" onChangeMethod="onChangeDescription" />
		<script type="text/javascript">
	        function <portlet:namespace />initEditor() 
	        { 
	            return "<%= UnicodeFormatter.toString(description) %>"; 
	        }
	    </script>
	</aui:field-wrapper>
	<div id="<portlet:namespace />descriptionError" class="<%=(SessionErrors.contains(renderRequest, "module-description-required"))?
    														"portlet-msg-error":StringPool.BLANK %>">
    	<%=(SessionErrors.contains(renderRequest, "module-description-required"))?
    			LanguageUtil.get(pageContext,"module-description-required"):StringPool.BLANK %>
    </div>
    
    <div id="${renderResponse.getNamespace()}startDate">
   	 <aui:field-wrapper label="start-date">
    		<aui:input id="startdate-enabled" name="startdate-enabled" type="checkbox" checked="${!module.isNullStartDate() }" label="module.edit.start-date" onClick="${renderResponse.getNamespace()}setStartDateState();" helpMessage="module.edit.start-date.help"  ignoreRequestValue="true" />
	    	<div id="${renderResponse.getNamespace()}startDateSpan" class="${!module.isNullStartDate() ? '' : 'aui-helper-hidden'}">
				<liferay-ui:input-date  yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"
					dayParam="startDateDia" dayValue="${startDateDia }"
					monthParam="startDateMes" monthValue="${startDateMes-1 }"
					yearParam="startDateAno" yearValue="${startDateAno }"  yearNullable="false" 
						 dayNullable="false" monthNullable="false" ></liferay-ui:input-date>
				<liferay-ui:input-time minuteParam="startDateMinuto" amPmParam="startAMPM"  
					hourParam="startDateHora" hourValue="${startDateHora }" minuteValue="${startDateMinuto }"></liferay-ui:input-time>
			</div>
		</aui:field-wrapper>
	</div>
	
	${courseExecutionStartDateString }
	<liferay-ui:error key="module-startDate-required" message="module-startDate-required" />
	<liferay-ui:error key="module-startDate-before-course-startDate" message="module-startDate-before-course-startDate" />
	
	<div id="${renderResponse.getNamespace() }endDate">
		<aui:field-wrapper label="end-date">
			<aui:input id="enddate-enabled" name="enddate-enabled" type="checkbox" checked="${!module.isNullEndDate() }" label="module.edit.end-date" onClick="${renderResponse.getNamespace()}setEndDateState();" helpMessage="module.edit.end-date.help"  ignoreRequestValue="true" />
			<div id="${renderResponse.getNamespace()}endDateSpan" class="${!module.isNullEndDate() ? '' : 'aui-helper-hidden'}">
				<liferay-ui:input-date  yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>" 
					dayParam="endDateDia" dayValue="${endDateDia }" 
					monthParam="endDateMes" monthValue="${endDateMes-1 }" 
					yearParam="endDateAno" yearValue="${endDateAno }"  yearNullable="false" 
					 	dayNullable="false" monthNullable="false" ></liferay-ui:input-date>
				<liferay-ui:input-time minuteParam="endDateMinuto" amPmParam="endAMPM" 
					hourParam="endDateHora" hourValue="${endDateHora }" minuteValue="${endDateMinuto }"></liferay-ui:input-time>
			</div>
		</aui:field-wrapper>
	</div>

	${courseExecutionEndDateString }
	<liferay-ui:error key="module-endDate-required" message="module-endDate-required" />
	<liferay-ui:error key="module-startDate-before-endDate" message="module-startDate-before-endDate" />
	<liferay-ui:error key="module-endDate-after-course-endDate" message="module-endDate-after-course-endDate" />
	
	<aui:field-wrapper label="allowed-time">
		<liferay-ui:input-time minuteParam="allowedDateMinuto" amPmParam="allowedDateAMPM"	hourParam="allowedDateHora" hourValue="${allowedDateHora }" minuteValue="${allowedDateMinuto }"></liferay-ui:input-time>
	</aui:field-wrapper>
	
	<aui:input type="hidden" name="icon" />
	<br />
	 
	 <liferay-ui:error key="error-file-size" message="error-file-size" />
	 
	<c:if test="${showicon}">
		<div class="anti-column">
			<%if(module.getIcon()>0){
				try {
					FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(module.getIcon());
	        %>
			<aui:column>    
	        	<liferay-ui:message key="actual-image"  /><br/>
	            	<img class="courselogo" src="<%= DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, "&imageThumbnail=1") %>" alt="<%= module.getTitle(themeDisplay.getLocale()) %>">
	        </aui:column>    
				<%}catch(Exception e){}
	        }%>
	        <aui:column>
				<aui:input name="fileName" label="icon" id="fileName" type="file" value="" >
					<aui:validator name="acceptFiles">'jpg, jpeg, png, gif'</aui:validator>
				</aui:input>
				<c:if test="<%=module.getIcon()>0 %>">
					<aui:input type="checkbox" label="delete" name="deleteAdditionalFile" value="false" inlineLabel="left"/>
				</c:if>
			</aui:column>
		</div>
	</c:if>
	
	<liferay-ui:error key="module-icon-required" message="module-icon-required" />
	<liferay-ui:error key="error_number_format" message="error_number_format" />
	<br />

	<aui:select label="modulo-predecesor" name="precedence">
<%
	java.util.List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
%>
		<aui:option value="0" ><liferay-ui:message key="module-nothing" /></aui:option>
<%
	for(Module theModule2:modules){
		boolean selected=false;
		if(moduleId!= theModule2.getModuleId()){
			if(moduleId>0&& theModule2.getModuleId()==module.getPrecedence()){
				selected=true;
			}
%>
		<aui:option value="<%=theModule2.getModuleId() %>" selected="<%=selected %>"><%=theModule2.getTitle(themeDisplay.getLocale()) %></aui:option>
<% 
		}
	}
%>
	</aui:select>		
	<liferay-ui:panel-container extended="false" persistState="false">
		<liferay-ui:custom-attributes-available className="<%= Module.class.getName() %>" >
		  
		   <liferay-ui:panel title="custom-fields" collapsible="true" defaultState="closed" >
		    <liferay-ui:custom-attribute-list className="<%=Module.class.getName()%>" classPK="<%=moduleId %>" 
		     editable="true" label="true" />
		   </liferay-ui:panel>
		 </liferay-ui:custom-attributes-available>

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
		Liferay.provide(
		        window,
		        '<portlet:namespace />closeWindow',
		    	function (){
				if ((!!window.postMessage)&&(window.parent != window)) {
					if (!window.location.origin){
						window.location.origin = window.location.protocol+"//"+window.location.host;
					}
					
					if(AUI().UA.ie==0) {
						parent.postMessage({name:'closeModule',moduleId:<%=Long.toString(moduleId)%>}, window.location.origin);
					}
					else {
						parent.postMessage(JSON.stringify({name:'closeModule',moduleId:<%=Long.toString(moduleId)%>}), window.location.origin);
					}
				}
				else {
					window.location.href='<portlet:renderURL />';
				}
		    }
		);
	//-->
	</script>
<%
		boolean showModuleClassification = true;
		try {
			showModuleClassification = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.SHOW_MODULE_CLASSIFICATION);
		} catch (SystemException e) {
			e.printStackTrace();
		}
%>
		<c:if test="<%= showModuleClassification %>">
			<liferay-ui:panel title="categorization" collapsible="true" defaultState="closed">
				<aui:input name="tags" type="assetTags" />
				<aui:input name="categories" type="assetCategories" />
			</liferay-ui:panel>
		</c:if>
	</liferay-ui:panel-container>
	   
	<aui:button-row>
		<input type="button" value="<liferay-ui:message key="save" />" onclick="javascript:validate()" >
	</aui:button-row>
</aui:form>
