<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.exception.NestableException"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="sun.nio.cs.UnicodeEncoder"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>
<%@page import="com.liferay.portal.kernel.exception.PortalException"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ include file="/init.jsp" %>
<% 
	long assetId=ParamUtil.getLong(request, "assertId");
	boolean openWindow = false, editDetails = false, improve = true; 
	String assetTitle=StringPool.BLANK;
	String sco=ParamUtil.getString(request, "sco","");
	long typeId=ParamUtil.getLong(request, "type");
	boolean completedAsPassed =ParamUtil.getBoolean(request, "completedAsPassed",false);

 LearningActivity learningActivity=null;
	if(assetId!=0){
		try{
			AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(assetId);
			assetId=entry.getEntryId();
			assetTitle=entry.getTitle(renderRequest.getLocale());
			editDetails = GetterUtil.getBoolean(PropsUtil.get("lms.scorm.editable."+entry.getClassName()),false);	
			
		}
		catch(NestableException e)
		{
			assetId=0;
		}
	}
	String windowWith="1024";
	String height="768";
	if(request.getAttribute("activity")!=null) {	
		learningActivity=(LearningActivity)request.getAttribute("activity");
		typeId=learningActivity.getTypeId();
	  
		if ((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().trim().length()!=0)) {
			try{
				if(assetId==0){
					AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(
						GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"assetEntry")));
					assetId=entry.getEntryId();
					assetTitle=entry.getTitle(renderRequest.getLocale());
					editDetails = GetterUtil.getBoolean(PropsUtil.get("lms.scorm.editable."+entry.getClassName()),false);
				}	
				openWindow = GetterUtil.getBoolean(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(), "openWindow"));
				sco=LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"sco","");
				String improveString = LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"improve");
				 windowWith=LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"windowWith","1024");
				 height=LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"height","768");
					completedAsPassed= GetterUtil.getBoolean(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(), "completedAsPassed"), false);

				if (improveString != null && !"".equals(improveString)) {
					improve = GetterUtil.getBoolean(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"improve"), true);
				}
			}
			catch(NestableException e)
			{
			}
			
		}	
	}
	
%>

<liferay-portlet:renderURL var="selectResource" windowState="<%=LiferayWindowState.POP_UP.toString() %>">
	<liferay-portlet:param name="jspPage" value="/html/scormactivity/admin/searchresource.jsp"/>
</liferay-portlet:renderURL>

<% 

String editResourceUnicode = StringPool.BLANK;
if(learningActivity!=null){  %>
<liferay-util:buffer var="editResource">
	<liferay-portlet:renderURL var="editResourceURL" windowState="<%=LiferayWindowState.POP_UP.toString() %>">
		<liferay-portlet:param name="mvcPath" value="/html/scormactivity/admin/editDetails.jsp"/>
		<liferay-portlet:param name="resId" value="<%=Long.toString(learningActivity.getActId()) %>"/>
	</liferay-portlet:renderURL>
	<% 
	   String editResourceClick="(function(source){ "+
				"if ((!!window.postMessage)&&(window.parent != window)) { "+
				"	AUI().use('json-stringify', function(A){ "+
				"		parent.postMessage(JSON.stringify({name:'resizeWidthActivity', "+
		        "							   width:'1250px'}), window.location.origin); "+
				"	}); "+
				"} "+
		        "})(this);";	
	%>
</liferay-util:buffer>
<% 
	editResourceUnicode = UnicodeFormatter.toString(editResource);
}
	//Permiso de editar los campos del extra content.
	boolean edit = LearningActivityLocalServiceUtil.canBeEdited(learningActivity, user.getUserId());
%>

<script type="text/javascript">

	AUI().ready(function(A) {
		var inputScormValue = A.one('#<portlet:namespace/>sco').get('value');
		A.one("label[for='<portlet:namespace/>sco']").hide();
		A.one('#<portlet:namespace/>sco').hide();
		if(inputScormValue.length > 0 ) {
			A.one("label[for='<portlet:namespace/>sco']").show();
			A.one('#<portlet:namespace/>sco').show();
		}
	}); 
<!--

<% if(editDetails){ %>
	AUI().ready('node',function(A) {
		A.one('.acticons').html('<%=editResourceUnicode%>');
		var editResource=A.one('#<portlet:namespace/>editResource');
		editResource.set('href',editResource.get('href')+'&assertId='+
				encodeURIComponent(<%=assetId %>));
	});
<% } %>

function <portlet:namespace />search() {
	AUI().use('node',function(A) {
		var backbutton = A.one('#<portlet:namespace/>backButton').one('span').clone();
		backbutton.setAttribute('id','<portlet:namespace/>backbutton');
		A.one('.portlet-body').prepend(backbutton);
		A.one('#<portlet:namespace/>backbutton').scrollIntoView();
		var iframe = A.Node.create('<iframe id="<portlet:namespace/>finder" src="javascript:false;" onload="<portlet:namespace />load(this)" frameBorder="0" scrolling="no" width="'+A.getBody().width()+'" height="0"></iframe>');
		A.one('.portlet-body').append(iframe);
		A.all('.acticons').each(function(icon){ icon.hide(); });
		A.one('#<portlet:namespace/>fm').hide();
		A.one('#<portlet:namespace/>backbutton').scrollIntoView();

		iframe.plug(A.Plugin.ResizeIframe);
		iframe.setAttribute('src','<%=selectResource %>');
	});
}
	
function <portlet:namespace />load(source) {

	AUI().use('node','querystring-parse',function(A) {
		
		var params=A.QueryString.parse(source.contentWindow.location.search.replace('?',''));
	
		if((params['<portlet:namespace />jspPage']=='/html/scormactivity/admin/result.jsp')&&
           (A.Lang.isNumber(params['<portlet:namespace />assertId'])) &&
           (A.Lang.isString(params['<portlet:namespace />assertTitle'])) &&
           (A.Lang.isString(params['<portlet:namespace />assertEditable'])) &&
           (A.Lang.isString(params['<portlet:namespace />assertWindowable']))) {
			A.one('#<portlet:namespace/>backbutton').remove();
			A.one('#<portlet:namespace/>finder').remove();
			A.all('.acticons').each(function(icon){ icon.show(); });
			A.one('#<portlet:namespace/>fm').show();
			A.one('#<portlet:namespace/>assetEntryId').set('value',params['<portlet:namespace />assertId']);		
			A.one('#<portlet:namespace/>assetEntryName').set('value',params['<portlet:namespace />assertTitle']);
			if(A.Lang.isString(params['<portlet:namespace />sco']))
			{
				A.one('#<portlet:namespace/>sco').set('value',params['<portlet:namespace />sco']);
				A.one('#<portlet:namespace/>sco').show();
				A.one("label[for='_lmsactivitieslist_WAR_liferaylmsportlet_sco']").show();
			}
			else
			{
				A.one('#<portlet:namespace/>sco').set('value','');
				A.one('#<portlet:namespace/>sco').hide();
				A.one("label[for='_lmsactivitieslist_WAR_liferaylmsportlet_sco']").hide();
			}
			
			if(params['<portlet:namespace />assertWindowable']=='true') {
				document.getElementById("<portlet:namespace/>openWindow").value = "true";
				document.getElementById("<portlet:namespace/>openWindowCheckbox").checked = true;
			} else {
				document.getElementById("<portlet:namespace/>openWindow").value = "false";
				document.getElementById("<portlet:namespace/>openWindowCheckbox").checked = false;
			}
			
			<% if(learningActivity!=null){  %>
				if(params['<portlet:namespace />assertEditable']=='true') {
					A.one('.acticons').html('<%=editResourceUnicode%>');
					var editResource=A.one('#<portlet:namespace/>editResource');
					editResource.set('href',editResource.get('href')+'&assertId='+
							encodeURIComponent(params['<portlet:namespace />assertId']));
				}
				else{
					A.one('.acticons').html('');
				}
				
			<% } %>

			window.messageHandler.detach();
			window.messageHandler=null;
		}
		else {
		    if (source.Document && source.Document.body.scrollHeight) 
		        source.height = source.contentWindow.document.body.scrollHeight;
		    else if (source.contentDocument && source.contentDocument.body.scrollHeight) 
		        source.height = source.contentDocument.body.scrollHeight + 35;
		    else if (source.contentDocument && source.contentDocument.body.offsetHeight) 
		        source.height = source.contentDocument.body.offsetHeight + 35;
		}
	
	});
}

function <portlet:namespace />back() {
	AUI().use('node',function(A) {
		A.one('#<portlet:namespace/>backbutton').remove();
		A.one('#<portlet:namespace/>finder').remove();
		A.all('.acticons').each(function(icon){ icon.show(); });
		A.one('#<portlet:namespace/>fm').show();

		window.messageHandler.detach();
		window.messageHandler=null;
	});
}

//-->
</script>

<%
	boolean disabled = true;
	if(LearningActivityLocalServiceUtil.canBeEdited(learningActivity, user.getUserId())){
		disabled = false;
	}
%>
<liferay-ui:error key="scormactivity.error.invalid-asset" message="scormactivity.error.invalid-asset"></liferay-ui:error>
<aui:input type="hidden" name="assetEntryId" ignoreRequestValue="true" value="<%=Long.toString(assetId) %>">
	<aui:validator name="required"></aui:validator>
</aui:input>
<aui:field-wrapper name="activity.edit.asserts" cssClass="search-button-container">
	<aui:input type="text" name="assetEntryName" ignoreRequestValue="true" value="<%=assetTitle %>" label="" inlineField="true" disabled="true"  size="50" >
		<aui:validator name="required"></aui:validator>
	</aui:input>
	<c:if test="<%=!disabled %>" >
		<button type="button" id="<portlet:namespace/>searchEntry" onclick="<portlet:namespace/>search();" >
		    <span class="aui-buttonitem-icon aui-icon aui-icon-search"></span>
		    <span class="aui-buttonitem-label"><%= LanguageUtil.get(pageContext, "search") %></span>
		</button>
		<aui:input  name="sco" value="<%=sco %>" label="Sco"/>
	</c:if>
			
</aui:field-wrapper>
<aui:field-wrapper name="activity.edit.openwindow.options">
	<aui:input type="checkbox" name="openWindow" label="activity.edit.openwindow" value="<%= String.valueOf(openWindow) %>" />
	<aui:input name="windowWith" label="width" value="<%= windowWith %>" >
		<aui:validator name="number"></aui:validator>
		<aui:validator name="max">10000</aui:validator>
	</aui:input>
	<aui:input name="height" label="height" value="<%= height %>">
		<aui:validator name="number"></aui:validator>
		<aui:validator name="max">5000</aui:validator>
	</aui:input>
</aui:field-wrapper>
<aui:input type="checkbox" name="improve" label="scormactivity.edit.improve" checked="<%=improve %>" disabled="<%=!edit %>" 
		ignoreRequestValue="true" helpMessage="scormactivity.edit.improve.helpMessage"></aui:input>
<aui:input type="checkbox" name="completedAsPassed" label="scormactivity.edit.completedAsPassed" checked="<%=completedAsPassed %>" disabled="<%=!edit %>" 
		 helpMessage="scormactivity.edit.completedAsPassed.helpMessage"></aui:input>
		
<div id="<portlet:namespace/>backButton" style="display:none;">
	<liferay-ui:icon image="back" message="back" url="<%=\"javascript:\"+renderResponse.getNamespace()+\"back();\" %>" label="true"  />
</div>

