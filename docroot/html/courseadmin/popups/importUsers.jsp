<%@page import="java.util.Iterator"%>
<%@page import="com.liferay.portal.model.CompanyConstants"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.lms.service.ClpSerializer"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionMessages"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.io.FileNotFoundException"%>

<%@ include file="/init.jsp" %>

<%
	PortletPreferences preferences = null;
	String portletResource = ParamUtil.getString(request, "portletResource");
	
	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}
	else{
		preferences = renderRequest.getPreferences();
	}
	
	String authType = PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_TYPE);
	try {
		if (Validator.isNotNull(company)) {
			authType = company.getAuthType();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}

	String urlExample = "<a href=\"/"+ ClpSerializer.getServletContextName();
	
	if (CompanyConstants.AUTH_TYPE_SN.equalsIgnoreCase(authType)) {
		urlExample += "/html/courseadmin/examples/ImportCourseUsersByName.csv\">"+LanguageUtil.get(themeDisplay.getLocale(),"example")+"</a>";
	}else if(CompanyConstants.AUTH_TYPE_EA.equalsIgnoreCase(authType)){
		urlExample += "/html/courseadmin/examples/ImportCourseUsersByEmailAddress.csv\">"+LanguageUtil.get(themeDisplay.getLocale(),"example")+"</a>";
	}else{
		urlExample += "/html/courseadmin/examples/ImportCourseUsersByUserId.csv\">"+LanguageUtil.get(themeDisplay.getLocale(),"example")+"</a>";
	}

%>

<portlet:renderURL var="importUsersURL"  windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="ajaxAction" value="importUserRole" /> 
	<portlet:param name="courseId" value="<%=ParamUtil.getString(renderRequest, \"courseId\") %>" /> 
	<portlet:param name="roleId" value="<%=ParamUtil.getString(renderRequest, \"roleId\") %>" /> 
	<portlet:param name="view" value="import-users" />  
</portlet:renderURL>

<liferay-ui:header title="courseadmin.importuserrole"></liferay-ui:header>



<c:if test='<%= SessionMessages.contains(renderRequest, "courseadmin.importuserrole.csv.saved") %>'>
	<div class="portlet-msg-success"> 
		<liferay-ui:message key="hello" />
	</div>
</c:if>
<liferay-ui:panel id="importuserrole_help" title="help" extended="closed">
	<%
	String[] arguments = new String[1];
	if (CompanyConstants.AUTH_TYPE_SN.equalsIgnoreCase(authType)) {
		arguments[0] = LanguageUtil.get(themeDisplay.getLocale(), "screen-name");	
	}else if(CompanyConstants.AUTH_TYPE_EA.equalsIgnoreCase(authType)){
		arguments[0] = LanguageUtil.get(themeDisplay.getLocale(), "email-address");
	}else{
		arguments[0] = LanguageUtil.get(themeDisplay.getLocale(), "user-id");
	}%>		
	<%=LanguageUtil.format(themeDisplay.getLocale(),"courseadmin.importuserrole.help",arguments) %>
</liferay-ui:panel>

<span>
	<%=LanguageUtil.get(pageContext,"courseadmin.importuserrole.download") +" "+ urlExample%>
</span>

<% if ((!SessionMessages.contains(renderRequest, "courseadmin.importuserrole.csv.saved"))&&(SessionErrors.isEmpty(renderRequest))) { %>
<iframe name="<portlet:namespace />import_frame" src="" id="<portlet:namespace />import_frame" style="display:none;" onload="<portlet:namespace />doImportUsers();" ></iframe>
<aui:form name="fm" action="<%=importUsersURL%>"  method="post" enctype="multipart/form-data" target='<%=renderResponse.getNamespace() +"import_frame" %>' >
	<aui:fieldset>
		<aui:field-wrapper label="courseadmin.importuserrole.file" helpMessage="courseadmin.importuserrole.file.help" >
			<div class="container-file">
		    	<aui:input inlineLabel="left" inlineField="true" name="fileName" label="" id="fileName" type="file" value="" >
		    		<aui:validator name="acceptFiles">'csv'</aui:validator>
		    	</aui:input>
	    	</div>
		</aui:field-wrapper>
	</aui:fieldset> 
	<aui:button-row>
		<button name="Save" value="save" onclick="AUI().use(function(A) {
	    												A.one('#<portlet:namespace />fm').submit();
	    											  });" type="button">
		<liferay-ui:message key="courseadmin.importuserrole.save" />
		</button>
		<button name="Close" value="close" onclick="AUI().use('aui-dialog', function(A) {
		    												A.DialogManager.closeByChild('#<portlet:namespace />showPopupImportUsers');
		    											  });" type="button">
			<liferay-ui:message key="courseadmin.importuserrole.cancel" />
		</button>
	</aui:button-row>
</aui:form>
<% } %>
	<div id="<portlet:namespace />uploadMessages" >
		<liferay-ui:error key="courseadmin.importuserrole.csv.fileRequired" message="courseadmin.importuserrole.csv.fileRequired" />
		<liferay-ui:error key="courseadmin.importuserrole.csv.badFormat" message="courseadmin.importuserrole.csv.badFormat" />
		<liferay-ui:error key="courseadmin.importuserrole.csv.badFormat.size" message="courseadmin.importuserrole.csv.badFormat.size" />
		<liferay-ui:success key="courseadmin.importuserrole.csv.saved" message="courseadmin.importuserrole.csv.saved"/>
		<% 
			SessionMessages.contains(renderRequest, "courseadmin.importuserrole.csv.saved");
		
		if(SessionErrors.contains(renderRequest, "courseadmin.importuserrole.csvErrors")) {%>
		<div class="portlet-msg-error">
			<% List<String> errors = (List<String>)SessionErrors.get(renderRequest, "courseadmin.importuserrole.csvErrors");
			   if(errors.size()==1) {
				  %><%=errors.get(0) %><%
			   }	
			   else {
			%>
				<ul>
				<% for(String error : errors){ %>
				 	<li><%=error %></li>
				<% } %>
				</ul>
			<% } %>
		</div>
		<% } %>
	</div>
