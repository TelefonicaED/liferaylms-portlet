<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="com.liferay.portal.kernel.servlet.BrowserSnifferUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.liferay.portal.service.persistence.PortletUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.P2pActivity"%>
<%@page import="com.liferay.lms.service.P2pActivityLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>

<%@include file="/init.jsp" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<portlet:actionURL name="addP2PActivity" var="addP2PActivity">
</portlet:actionURL> 

<liferay-ui:error key="campos-necesarios-vacios" message="campos-necesarios-vacios" />
<liferay-ui:error key="error-subir-p2p" message="error-subir-p2p" />
<liferay-ui:error key="p2ptaskactivity-error-file-type" message="p2ptaskactivity.error.file.type" />
<liferay-ui:error key="p2ptaskactivity-error-file-name" message="p2ptaskactivity-error-file-name" />
<liferay-ui:error key="p2ptaskactivity-error-file" message="p2ptaskactivity-error-file" />
<%
Boolean isLinkTabletP2PUpload = ParamUtil.getBoolean(request, "isTablet", false);
String cssLinkTabletClassP2PUpload="";
if(isLinkTabletP2PUpload){
	cssLinkTabletClassP2PUpload="tablet-link";
}
long actId = ParamUtil.getLong(request, "actId",0);
String textCorrection = LanguageUtil.get(pageContext,"p2ptask-text-upload");
String uploadCorrect = ParamUtil.getString(request, "uploadCorrect", "false");
Long userId = themeDisplay.getUserId();
P2pActivity myP2PActivity = P2pActivityLocalServiceUtil.findByActIdAndUserId(actId, userId);
long p2pActivityId = 0;
boolean isDateExpired = false; 
String dateUpload = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"dateupload");
Date date = new Date();
if(!dateUpload.equals("")){
	
	Date now = new Date();
	SimpleDateFormat dateFormatZone = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	dateFormatZone.setTimeZone(themeDisplay.getTimeZone());
	
	date = dateFormat.parse(dateUpload);
	dateUpload = dateFormatZone.format(date);
	if(now.after(date)){
		isDateExpired = true;
	}
}
%>

<script type="text/javascript">
<!--
Liferay.provide(
        window,
        '<portlet:namespace />clearText',
        function() {
			var A = AUI();
			
			if (window.CKEDITOR)
				var idDesc = CKEDITOR.instances['<portlet:namespace />description'].document.getBody().getText();
			else
				var idDesc = A.one("#<portlet:namespace/>description").val();
			
			var textReplace = "<%=StringEscapeUtils.unescapeHtml(textCorrection)%>";
			if (idDesc == textReplace) {
				if (window.CKEDITOR){
					CKEDITOR.instances.<portlet:namespace />description.setData("");
					CKEDITOR.instances.<portlet:namespace />description.focus();
				}else{
					A.one("#<portlet:namespace />description").set('value',"");
					A.one("#<portlet:namespace />description").focus();
				}
				
			}
        }
    );
    
Liferay.provide(
        window,
        '<portlet:namespace />onChangeDescription',
        function(val) {
        	var A = AUI();
			A.one('#<portlet:namespace />description').set('value',val);
        },
        ['node']
    );
    
Liferay.provide(
        window,
        '<portlet:namespace />commitForm',
        function() {
			var A = AUI();
			
			var idForm = "#<portlet:namespace />fm_1";
			A.one("#buttonSendP2P").attr("disabled", "disabled");
			A.one(idForm).submit(); // TODO check this
        }
    );
	
Liferay.provide(
        window,
        '<portlet:namespace />checkDataform',
        function() {
			var A = AUI();
			
			if(window.CKEDITOR)
				var descrip = CKEDITOR.instances.<portlet:namespace />description.getData();
			else
				var descrip = A.one("#<portlet:namespace/>description").val();
			
			var idFile = "#<portlet:namespace />fileName";
			
			if(descrip == "" || descrip == "<%=StringEscapeUtils.unescapeHtml(textCorrection)%>"){
				alert(Liferay.Language.get("p2ptask-empty-desc"));
				return false;
			}
			if(A.one(idFile).val() == "" && <%=!StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "fileoptional")) %> ){
				alert(Liferay.Language.get("p2ptask-empty-file"));
				return false;
			}
			<portlet:namespace />openPopUp();
        }
    );
    
	Liferay.provide(
        window,
        '<portlet:namespace />openPopUp',
        function() {
			var A = AUI();
			
			var fileName = A.one("#<portlet:namespace />fileName").val();
			
			if(window.CKEDITOR)
				var descrip = CKEDITOR.instances['<portlet:namespace />description'].document.getBody().getText();
			else
				var descrip = A.one("#<portlet:namespace />description").val();
			
			var pos = fileName.lastIndexOf("\\");
			if (pos > 0) {
				fileName = fileName.substring(pos+1);
			}
			
			
			//Start opening popUp			
			if(A.one('#<portlet:namespace />p2pconfirmation')) {
				window.<portlet:namespace />p2pconfirmationBody = A.one('#<portlet:namespace />p2pconfirmation').html();
				A.one('#<portlet:namespace />p2pconfirmation').remove();
			}
			window.<portlet:namespace />p2pconfirmation = new A.Dialog({
				id:'<portlet:namespace />showp2pconfirmation',
				title: Liferay.Language.get('p2ptask-upload-confirmation'),
	            bodyContent: window.<portlet:namespace />p2pconfirmationBody,
	            centered: true,
	            modal: true,
	            width: "auto",
	            destroyOnClose: true,
	            height: "auto"
	        }).render().show();
			A.one("#contentFile").html(fileName);
			A.one("#contentDescription").html(descrip);
        },
        ['aui-dialog']
    );
	
	Liferay.provide(
	        window,
	        '<portlet:namespace />openConfirmation',
	        function() {
				var A = AUI();
				
				if(A.one('#<portlet:namespace />p2puploaded')) {
					window.<portlet:namespace />p2puploadedTitle = A.one('#<portlet:namespace />p2puploaded h1').html();
					window.<portlet:namespace />p2puploadedBody = A.one('#<portlet:namespace />p2puploaded').html();
				}
				
				window.<portlet:namespace />p2puploaded = new A.Dialog({
					id:'<portlet:namespace />showp2puploaded',
		            title: window.<portlet:namespace />p2puploadedTitle,
		            bodyContent: window.<portlet:namespace />p2puploadedBody, //Esto no esta bien
		            centered: true,
		            modal: true,
		            width: "auto",
		            height: "auto"
		        }).render().show();
				
				
	        },
	        ['aui-dialog']
	    );
	
	Liferay.provide(
	        window,
	        '<portlet:namespace />closeConfirmation',
	        function() {
				var A = AUI();
				A.DialogManager.closeByChild('#<portlet:namespace />showp2pconfirmation');
				A.one('#<portlet:namespace />showp2pconfirmation').remove();
	        },
	        ['aui-dialog']
	    );
	
	Liferay.provide(
	        window,
	        '<portlet:namespace />closeForm',
	        function() {
				var A = AUI();
				
				A.DialogManager.closeByChild('#<portlet:namespace />showp2puploaded');
	        },
	        ['aui-dialog']
	    );
-->
</script>

<!-- Start PopUp confirmation -->
<div id="<%= renderResponse.getNamespace() %>p2pconfirmation" style="display:none;">
	<div class="contDesc">
	<p><span class="label"><liferay-ui:message key="p2ptask-file-name" />:</span></p> <p><span id="contentFile"></span></p>
	</div>
	<br />
	<div class="contDesc">
		<p><span class="label"><liferay-ui:message key="p2ptask-description-task" />:</span></p> <p><span id="contentDescription"></span></p>
	</div>
	<br /><br />
	<p><liferay-ui:message key="p2ptask-description-task-confirmation-message" /></p>
	<div class="buttons">
		<input type="button" onclick="<%= renderResponse.getNamespace() %>closeConfirmation()" class="button simplemodal-close" value="<liferay-ui:message key="cancel" />" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" class="button" id="buttonSendP2P" value="<liferay-ui:message key="p2ptask-send" />" onclick="<%= renderResponse.getNamespace() %>commitForm()" />
	</div>
</div>
<!-- End PopUp confrimation -->

<div id="<%= renderResponse.getNamespace() %>p2puploaded" style="display:none">
	<h1><liferay-ui:message key="p2ptaskactivity.inc.p2puploaded.title" /></h1>
	<div class="desc color_tercero"><liferay-ui:message key="p2ptaskactivity.inc.p2puploaded.subtitle" /></div>
	<br />
	<div class="contDesc bg-icon-check">
		<p><liferay-ui:message key="p2ptaskactivity.inc.p2puploaded.message" /></p>
	</div>
	<div class="buttons">
		<input type="button" class="button simplemodal-close" id="buttonClose" value="<liferay-ui:message key="close" />" onclick="<portlet:namespace />closeForm()" />
	</div>
</div>

<div>

<%
	if(myP2PActivity != null){
		
		Date dateDel = myP2PActivity.getDate();
		textCorrection = myP2PActivity.getDescription();
		p2pActivityId = myP2PActivity.getP2pActivityId();
		
		SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		dFormat.setTimeZone(themeDisplay.getTimeZone());
		%>
		
		<div class="description">
			<span class="date-destacado"><liferay-ui:message key="p2ptaskactivity.datedelivery.message" arguments="<%=dFormat.format(dateDel)%>" /></span>
		</div>
		<br/>
		<label class="aui-field-label" style="font-size: 14px;"> <liferay-ui:message key="p2ptaskactivity.comment" /> </label>
		<br/>
		<div class="container-textarea">
			<label for="<portlet:namespace/>descreadonly" />
			<%=textCorrection %>
		</div>
		
		<%
			if(myP2PActivity.getFileEntryId() != 0){
				
				DLFileEntry dlfile = null;
				String urlFile = "";
				int size = 0;
				int sizeKb = 0;
				String title = "";
				
				try{
					dlfile = DLFileEntryLocalServiceUtil.getDLFileEntry(myP2PActivity.getFileEntryId());
					urlFile = themeDisplay.getPortalURL()+"/documents/"+dlfile.getGroupId()+"/"+dlfile.getUuid();
					size = Integer.parseInt(String.valueOf(dlfile.getSize()));
					sizeKb = size/1024; //Lo paso a Kilobytes
					title = dlfile.getTitle();
				}catch(Exception e){
					
				}
	
				if(dlfile != null){
				%>
					<div class="doc_descarga">
						<span><%=title%>&nbsp;(<%= sizeKb%> Kb)&nbsp;</span>
						<a href="<%=urlFile%>" class="verMas <%=cssLinkTabletClassP2PUpload %>" target="_blank"><liferay-ui:message key="p2ptask-donwload" /></a>
					</div>
				<%
				}
			}
	}
	//Si ha pasado la fecha de entrega.
	else if(isDateExpired){
	%>
		<h3 class="dateexpired"><liferay-ui:message key="p2ptaskactivity.dateexpired" /></h3>
		<div class="description">
			<p class="color_tercero"><liferay-ui:message key="p2ptaskactivity.dateexpired.message" arguments="<%=dateUpload%>" /></p>
		</div>
	<%			
	}
	else
	{
	%>
		<form name="<portlet:namespace />fm_1" id="<portlet:namespace />fm_1" action="<%=addP2PActivity%>" method="POST" enctype="multipart/form-data" >
			<aui:input name="actId" type="hidden" value="<%=actId %>" />
			<aui:input name="p2pActivityId" type="hidden" value="<%=p2pActivityId %>" />
			
			<c:if test="<%=!dateUpload.equals(\"\") %>">
				<div class="description">
					<span class="date-destacado"><liferay-ui:message key="p2ptaskactivity.dateexpire" arguments="<%=dateUpload%>" /></span>
				</div>
			</c:if>
			
			<aui:field-wrapper label="description" name="description">
				<liferay-ui:input-editor name="description" width="100%" onChangeMethod="onChangeDescription"/>
				<aui:input name="description" type="hidden"/>
				<script type="text/javascript">
	    		    function <portlet:namespace />initEditor() {
		    		    return "<%= UnicodeFormatter.toString(textCorrection) %>"; 
		    		};
		    		AUI().on('domready', function(){
	    		    	if(window.CKEDITOR)CKEDITOR.instances.<portlet:namespace />description.on('focus',<portlet:namespace />clearText);
	    		    	else AUI().one("#<portlet:namespace />description").on('focus',<portlet:namespace />clearText);
	    		    });
	    		</script>
			</aui:field-wrapper>
			
			<liferay-ui:error key="p2ptaskactivity-error-file-size" message="p2ptaskactivity.error.file.size" />
			<div class="container-file">
				<c:if test="<%=StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(actId, \"fileoptional\")) %>">
					<span class="optionalfile optional-yes"><liferay-ui:message key="p2ptaskactivity.optionalfile.yes" /></span>
				</c:if>
				<c:if test="<%=!StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(actId, \"fileoptional\")) %>">
					<span class="optionalfile optional-no"><liferay-ui:message key="p2ptaskactivity.optionalfile.no" /></span>
				</c:if>
				<aui:input inlineLabel="left" inlineField="true" name="fileName" id="fileName" type="file" value="" />
			</div>
			<div class="container-buttons">
				<input type="button" class="button floatr" value="<liferay-ui:message key="p2ptask-send" />" onclick="<%= renderResponse.getNamespace() %>checkDataform()" />
			</div>
		</form>
<%	}%>
</div>
<%
if(uploadCorrect.equals("true")){
	uploadCorrect="false";
	request.setAttribute("uploadCorrect", uploadCorrect);
	renderResponse.setProperty("clear-request-parameters", Boolean.TRUE.toString());
	%><script><portlet:namespace />openConfirmation();</script><%
}
%>