<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.lms.service.AsynchronousProcessAuditLocalServiceUtil"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
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
	
	boolean onlyForUserOwner = (preferences.getValue("preferences--onlyForUserOwner--", "false")).compareTo("true") == 0; 
	boolean showExtraContent = (preferences.getValue("preferences--showExtraContent--", "false")).compareTo("true") == 0; 
	String classNamePrefsValues = preferences.getValue("className", "todos");
	if(classNamePrefsValues==null){ classNamePrefsValues = "todos"; }
	
	// Obtenemos todos los classname distintos para efectuar la lista;
	List<String> classNamesList = AsynchronousProcessAuditLocalServiceUtil.getDistinctTypes(themeDisplay.getCompanyId());
	
%>

<liferay-ui:error key="sucess" message="sucess" />


<liferay-portlet:actionURL var="saveEditPreferencesURL" portletConfiguration="true"/>
<aui:form action="<%=saveEditPreferencesURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>

	<aui:column columnWidth="80">
	
			<aui:select name="className" multiple="true" label="asynchronous.config.show-types" cssClass="type-selector" ignoreRequestValue="true">
				<aui:option label="todos" value="todos" selected="<%= classNamePrefsValues.contains(\"todos\") %>" />
				<%for(String classe : classNamesList){ boolean selected=classNamePrefsValues.indexOf(classe) >= 0 ;%>
					<aui:option value="<%= classe %>"  selected="<%=selected%>" ><%=selected%>-<%= classe %></aui:option>
				<%} %>
			</aui:select>
	
			<aui:input type="checkbox" name="preferences--onlyForUserOwner--" label="asynchronous.config.only-owner" value="<%=onlyForUserOwner %>" checked="<%=onlyForUserOwner %>"/>
			
			<aui:input type="checkbox" name="preferences--showExtraContent--" label="asynchronous.config.show-extra-content" value="<%=showExtraContent %>" checked="<%=showExtraContent %>"/>
	</aui:column>
	
	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>

<aui:script use="liferay-util-list-fields">
	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>