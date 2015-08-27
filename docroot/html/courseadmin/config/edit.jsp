<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.LayoutSetPrototype"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionMessages"%>
<%@page import="com.liferay.portal.kernel.portlet.PortletBagPool"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@include file="/init.jsp" %>
<%
	PortletPreferences preferences = null;
	
	String portletResource = ParamUtil.getString(request, "portletResource");
	
	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}
	else{
		preferences = renderRequest.getPreferences();
	}
	LmsPrefs lmsPrefs=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId());
	String[] layusprsel=new String[0];
	if(preferences.getValue("courseTemplates","")!=null&&preferences.getValue("courseTemplates","").length()>0)
	{	
		layusprsel=preferences.getValue("courseTemplates", "").split(",");
	}
	
	boolean showClose 	= preferences.getValue("showClose",  "true").equals("true");
	boolean showDelete 	= preferences.getValue("showDelete", "true").equals("true");
	boolean showMembers = preferences.getValue("showMembers","true").equals("true");
	boolean showExport 	= preferences.getValue("showExport", "true").equals("true");
	boolean showImport	= preferences.getValue("showImport", "true").equals("true");
	boolean showClone 	= preferences.getValue("showClone",  "true").equals("true");
	boolean showGo 		= preferences.getValue("showGo", 	 "true").equals("true");
	boolean showPermission = preferences.getValue("showPermission", "true").equals("true");
	boolean showSearchTags = preferences.getValue("showSearchTags", "false").equals("true");
	boolean showWelcomeMsg = preferences.getValue("showWelcomeMsg", "true").equals("true");
	boolean showOnlyOrganizationUsers = preferences.getValue("showOnlyOrganizationUsers", "false").equals("true");
%>

<liferay-portlet:actionURL var="saveConfigurationURL"  portletConfiguration="true"/>
<aui:form action="<%=saveConfigurationURL %>" >
	<aui:input type="hidden" name="<%=Constants.CMD %>" value="<%=Constants.UPDATE %>" />
		
	<aui:field-wrapper label="courseadmin.config.showSearch" >
		<aui:input type="checkbox" name="showSearchTags" label="courseadmin.config.showSearchTags" value="<%=showSearchTags %>" checked="<%=showSearchTags %>"/>
		<aui:input type="checkbox" helpMessage="help-available-categories" label="available-categories" name="categories" value="<%=preferences.getValue(\"categories\", StringPool.TRUE) %>" ignoreRequestValue="true"/>
	</aui:field-wrapper>
	
	<aui:field-wrapper label="course-fields" >
		<aui:input type="checkbox" label="registration-type" name="showRegistrationType" value="<%=preferences.getValue(\"showRegistrationType\", StringPool.TRUE) %>" ignoreRequestValue="true"/>
		<aui:input type="checkbox" label="num-of-users" name="showMaxUsers" value="<%=preferences.getValue(\"showMaxUsers\", StringPool.TRUE) %>" ignoreRequestValue="true"/>
		<aui:input type="checkbox" helpMessage="help-inscription-date" label="inscription-date" name="inscriptionDate" value="<%=preferences.getValue(\"showInscriptionDate\", StringPool.TRUE) %>" ignoreRequestValue="true"/>
		<aui:input type="checkbox" helpMessage="help-published-in-catalog" label="published-in-catalog" name="showcatalog" value="<%=preferences.getValue(\"showcatalog\", StringPool.TRUE) %>" ignoreRequestValue="true"/>
	</aui:field-wrapper>
	
	<aui:field-wrapper label="courseadmin.config.courseactions" >
		<aui:input type="checkbox" name="showClose" label="courseadmin.config.showClose" 	value="<%=showClose %>" checked="<%=showClose %>"/>
		<aui:input type="checkbox" name="showDelete" label="courseadmin.config.showDelete" 	value="<%=showDelete %>" checked="<%=showDelete %>"/>
		<aui:input type="checkbox" name="showMembers" label="courseadmin.config.showMembers" value="<%=showMembers %>" checked="<%=showMembers %>"/>
		<aui:input type="checkbox" name="showExport" label="courseadmin.config.showExport"	value="<%=showExport %>" checked="<%=showExport %>"/>
		<aui:input type="checkbox" name="showImport" label="courseadmin.config.showImport" 	value="<%=showImport %>" checked="<%=showImport %>"/>
		<aui:input type="checkbox" name="showClone" label="courseadmin.config.showClone" 	value="<%=showClone %>" checked="<%=showClone %>"/>
		<aui:input type="checkbox" name="showGo" label="courseadmin.config.showGo" 			value="<%=showGo %>" checked="<%=showGo %>"/>
		<aui:input type="checkbox" name="showPermission" label="courseadmin.config.showPermission" 			value="<%=showPermission %>" checked="<%=showPermission %>"/>
		<aui:input type="checkbox" name="showWelcomeMsg" label="courseadmin.config.showWelcomeMsg" 			value="<%=showWelcomeMsg %>" checked="<%=showWelcomeMsg %>"/>
	</aui:field-wrapper>

<%
String[] lspist=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getLmsTemplates().split(",");
		%>
		<aui:field-wrapper  label="allowed-site-templates" >
		<%
		for(String lspis:lspist)
		{
			String checked="";
			if(ArrayUtils.contains(layusprsel, lspis))
			{
				checked="checked=\"true\"";
			}
			LayoutSetPrototype layoutsetproto=LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.parseLong(lspis));
			%>
				<input type="checkbox" name="<portlet:namespace/>courseTemplates" 
	<%=checked %> value="<%=layoutsetproto.getLayoutSetPrototypeId()%>"/> <label><%=layoutsetproto.getName(themeDisplay.getLocale())%></label><br/>
			
			<%
		}
		%>
		</aui:field-wrapper>

	<aui:field-wrapper  label="modulenavigation.Mode" >
		<aui:input type="checkbox" name="showOnlyOrganizationUsers" label="modulenavigation.organizationMode" value="<%=showOnlyOrganizationUsers %>" checked="<%=showOnlyOrganizationUsers %>"/>
	</aui:field-wrapper>

	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
	
</aui:form>
