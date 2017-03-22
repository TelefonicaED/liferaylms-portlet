<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
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
	String sco=ParamUtil.getString(request, "sco",null);
	long typeId=ParamUtil.getLong(request, "type");
	boolean completedAsPassed =ParamUtil.getBoolean(request, "completedAsPassed",false);
	
	long resId = ParamUtil.getLong(request, "resId");
	long resModuleId = ParamUtil.getLong(request, "resModuleId");
	
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
				if(sco==null){
					sco=LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"sco","");
				}
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

<% if(editDetails){ %>
	AUI().ready('node',function(A) {
		A.one('.acticons').html('<%=editResourceUnicode%>');
		var editResource=A.one('#<portlet:namespace/>editResource');
		editResource.set('href',editResource.get('href')+'&assertId='+
				encodeURIComponent(<%=assetId %>));
	});
<% } %>




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
		
		<liferay-portlet:renderURL var="buscarRecurso" >
			<liferay-portlet:param name="mvcPath" value="/html/scormactivity/admin/searchresource.jsp" />
			<liferay-portlet:param name="resId" value="<%=String.valueOf(resId) %>" />
			<liferay-portlet:param name="resModuleId" value="<%=String.valueOf(resModuleId) %>" />
		</liferay-portlet:renderURL>
		
		<button type="button" id="<portlet:namespace/>searchEntry" onclick="javascript:${renderResponse.getNamespace()}goToSearchResource();" " >
		    <span class="aui-buttonitem-icon aui-icon aui-icon-search"></span>
		    <span class="aui-buttonitem-label"><%= LanguageUtil.get(pageContext, "search") %></span>
		</button>
				
		<aui:input  name="sco" value="<%=Validator.isNotNull(sco)?sco.trim():\"\" %>" label="SCO" type="<%= Validator.isNotNull(sco)?\"text\":\"hidden\" %>"/>
		
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
	<%
		LmsPrefs lmsPrefs = LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
		boolean isVisibleOptionDebug = lmsPrefs.getDebugScorm();
		if(isVisibleOptionDebug){
			boolean isDebugEnable = false;
			if(learningActivity!=null){
				isDebugEnable = Boolean.parseBoolean(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(), "scormDebug"));
			}else{
				isDebugEnable =ParamUtil.getBoolean(request, "debugScorm",false);
			}
				
			
		%>
			<aui:input type="checkbox" name="debugScorm" label="debugScorm" checked="<%=isDebugEnable %>"
					 helpMessage="debugScorm"></aui:input>
		<%}%> 
		 
		
<div id="<portlet:namespace/>backButton" style="display:none;">
	<liferay-ui:icon image="back" message="back" url="<%=\"javascript:\"+renderResponse.getNamespace()+\"back();\" %>" label="true"  />
</div>

<script type="text/javascript">

function <portlet:namespace />goToSearchResource(){
	var url =  '${buscarRecurso}';
	var languageId = '<%= themeDisplay.getLanguageId()%>';
	
	url +='&<portlet:namespace/>title='+$('#<portlet:namespace />title_'+languageId).val();
	url += '&<portlet:namespace/>description='+$('#<portlet:namespace />description').val();
	url += '&<portlet:namespace/>passpuntuation='+$('#<portlet:namespace />passpuntuation').val();	
	location.href=url;
		

}

</script>