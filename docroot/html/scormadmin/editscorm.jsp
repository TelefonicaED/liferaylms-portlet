<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.model.ResourcePermission"%>
<%@page import="com.liferay.portal.model.ResourceConstants"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.model.Resource"%>
<%@page import="com.liferay.portal.service.ResourceActionLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.ResourcePermissionLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.ResourceLocalServiceUtil"%>
<%@page import="com.liferay.portlet.social.service.SocialRelationLocalServiceUtil"%>
<%@page import="com.liferay.portlet.social.model.SocialRelationConstants"%>
<%@ include file="/init.jsp" %>
<%@page import="com.liferay.lms.service.SCORMContentLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<portlet:actionURL var="savescormURL" name="saveSCORM" />

<script type="text/javascript" >
<!--
setTimeout(resizeThisFrame, 500);

function resizeThisFrame() {
	if ((!!window.postMessage)&&(window.parent != window)) {
		if (!window.location.origin){
			var nada = '';
			window.location.origin = window.location.protocol+"/"+nada+"/"+window.location.host;
		}
		var bodyHeight = document.getElementsByTagName('body')[0].offsetHeight;
		parent.postMessage({name:'resizeIframe', height : bodyHeight}, window.location.origin);
	}
	setTimeout(resizeThisFrame, 500);
}
//-->
</script>
<%
String redirect = ParamUtil.getString(request, "redirect","");
String backURL = ParamUtil.getString(request, "backURL");
String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");
long scormId=ParamUtil.getLong(request, "scormId",0);
String description=ParamUtil.getString(request, "description", "");
String title=ParamUtil.getString(request, "title", "");
String[] tags = ParamUtil.getParameterValues(request, "tags", new String[0]);
String[] categories = ParamUtil.getParameterValues(request, "categories", new String[0]);
SCORMContent scorm=null;
if(scormId>0)
{
	scorm=SCORMContentLocalServiceUtil.getSCORMContent(scormId);
	%>
	<aui:model-context bean="<%= scorm %>" model="<%= SCORMContent.class %>" />
	
	<%
	title = scorm.getTitle();
	description = scorm.getDescription();
}
else
{
	%>
	<aui:model-context  model="<%= SCORMContent.class %>" />
	
	<%
}

%>
<liferay-ui:error key="maximum-file-size" message="maximum-file-size"/>
<liferay-ui:error key="scormadmin.error.notitle" message="scormadmin.error.notitle" />
<liferay-ui:error key="scormadmin.error.nodescription" message="scormadmin.error.nodescription"/>
<liferay-ui:error key="scormadmin.error.nozip" message="scormadmin.error.nozip"/>
<liferay-ui:error key="scormadmin.error.nomanifest" message="scormadmin.error.nomanifest"/>
<liferay-ui:error key="scormadmin.error.nomanifestincorrect" message="scormadmin.error.nomanifestincorrect"/>
<liferay-ui:error key="scormadmin.error.requiredcategories" message="scormadmin.error.requiredcategories"/>

<div class="portlet-msg-error"><%= LanguageUtil.format (themeDisplay.getLocale(), "upload-documents-no-larger-than-x-k", new String[]{ PrefsPropsUtil.getString("dl.file.max.size") }) %></div>

<aui:form name="fm" action="<%=savescormURL%>"  method="post" enctype="multipart/form-data" >

	<aui:input type="hidden" name="scormId" value="<%=scormId %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="scormId" type="hidden" value="<%=scormId %>"/>
	<aui:input name="title" label="title" value="<%= title %>">
		<aui:validator name="required"></aui:validator>
	</aui:input>

	<aui:field-wrapper label="description" required="true">
			<liferay-ui:input-editor cssClass="aui-field-element" name="description" width="100%" onChangeMethod="onChangeDescription"/>
				<script type="text/javascript">
        function <portlet:namespace />initEditor() { return "<%= UnicodeFormatter.toString(description) %>"; }
    </script>
    <br />
    </aui:field-wrapper>
    <c:if test="<%=scormId==0%>">

		<aui:field-wrapper label="zipfile">
		<aui:input inlineLabel="left" inlineField="true"
		  	name="fileName" label="" id="fileName" type="file" value="" >
		  	<aui:validator name="required"></aui:validator>
		  	<aui:validator name="acceptFiles">'zip'</aui:validator>
		  	</aui:input>
		  	<br />
</aui:field-wrapper>	
<aui:field-wrapper name="permissions">
		<liferay-ui:input-permissions modelName="<%= SCORMContent.class.getName() %>">
		</liferay-ui:input-permissions>
	</aui:field-wrapper>
	
	</c:if>
	
	<% String cipheredMode = GetterUtil.getString(PropsUtil.get("lms.scorm.cipheredmode"), "ask"); 
	if ("always".equals(cipheredMode) || "never".equals(cipheredMode)) { %>
		<aui:input name="ciphered" id="ciphered" type="hidden" value='<%= "always".equals(cipheredMode) %>' ignoreRequestValue="true"/>
	<% } else { %>
	<aui:field-wrapper label="ciphered.options">
		<aui:input inlineLabel="right" inlineField="true" name="ciphered" label="ciphered" id="ciphered" type="checkbox" value="<%= scormId == 0 ? false : scorm.getCiphered() %>"/>
	</aui:field-wrapper>
	<% } %>
		<liferay-ui:custom-attributes-available className="<%= SCORMContent.class.getName() %>">
		<liferay-ui:custom-attribute-list 
			className="<%=com.liferay.lms.model.SCORMContent.class.getName()%>" classPK="<%=scormId %>" editable="true" label="true"></liferay-ui:custom-attribute-list>
	</liferay-ui:custom-attributes-available>
	<aui:input name="tags" type="assetTags" ignoreRequestValue="true" value='<%= Validator.isNull(tags) ? "" : StringUtil.merge(tags, ",") %>' />
	<aui:input name="categories" type="assetCategories" ignoreRequestValue="true"  value='<%= Validator.isNull(categories) ? "" : StringUtil.merge(categories, ",") %>'/>
	<aui:button-row>
	<%
		String extractCodeFromEditor = renderResponse.getNamespace() + "extractCodeFromEditor()";
	%>									
		<aui:button type="submit"></aui:button>
			<%if(scormId>0) 
	{
	%>
	

<c:if test="<%= permissionChecker.hasPermission(scorm.getGroupId(), SCORMContent.class.getName(), scorm.getScormId(),
ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="<%=SCORMContent.class.getName() %>"
					modelResourceDescription="<%= scorm.getTitle() %>"
					resourcePrimKey="<%= String.valueOf(scorm.getScormId()) %>"
					var="permissionsURL"
				/>
				
				<aui:button href="<%=permissionsURL.toString() %>" value="courseadmin.adminactions.permissions" />	
			</c:if>

<%
	}
%>
<portlet:actionURL name="deleteSCORM" var="deleteURL">
<portlet:param name="scormId" value="<%= Long.toString(scormId )%>" />
<portlet:param name="redirect" value="<%= redirect%>" />

</portlet:actionURL>
<%
if(scormId > 0 && permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  SCORMContent.class.getName(),scormId,ActionKeys.DELETE))
{
%>
<aui:button href="<%=deleteURL.toString() %>" value="delete" />
<%
}
%>
		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
		
		
	</aui:form>
<script type="text/javascript">
<!--
Liferay.provide(
        window,
        '<portlet:namespace />onChangeDescription',
        function(val) {
        	var A = AUI();
			A.one('#<portlet:namespace />description').set('value',val);
        },
        ['node']
    );
-->
</script>
<aui:script use="liferay-form">
	Liferay.Form.register(
	     {
	        id: '<portlet:namespace />fm',
	        
	        fieldRules: [
	        	{
	        		fieldName: '<portlet:namespace />description',
	        		validatorName: 'required'
	        	}
	        ]
	    }
	);
</aui:script>