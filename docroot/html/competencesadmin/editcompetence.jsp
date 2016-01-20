<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.lms.service.CompetenceLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Competence"%>
<%@ include file="/init.jsp" %>

<portlet:actionURL var="savecompetenceURL" name="saveCompetence" />
<portlet:renderURL var="cancel" />
<liferay-ui:error key="title-required" message="title-required" />
<liferay-ui:error key="title-empty" message="title-empty" />
<liferay-ui:error key="title-repeated" message="title-repeated" />
<%

	PortletPreferences preferences = null;
	
	String portletResource = ParamUtil.getString(request, "portletResource");
	
	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}
	else{
		preferences = renderRequest.getPreferences();
	}
	
	String[] pages = null;
	
	if(preferences!=null&&preferences.getValue("pages",null)!=null){
		String pagesb = preferences.getValue("pages",null);
		if(pagesb!=null){
			pages = pagesb.split(StringPool.COMMA);
		}
	}

	if(pages==null){
		pages = PrefsPropsUtil.getString("lms.competences.pages", "A4").split(StringPool.COMMA);
	}

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");
long competenceId=ParamUtil.getLong(request, "competenceId",0);
Competence competence=null;
if(request.getAttribute("competence")!=null)
{
	competence=(Competence)request.getAttribute("competence");
}
else
{
	if(competenceId>0)
	{
		competence=CompetenceLocalServiceUtil.getCompetence(competenceId);
	}
}	
String description="";
String template="";
if(competence!=null)
{
	description=competence.getDescription(themeDisplay.getLocale(),true);
	template=competence.getDiplomaTemplate(themeDisplay.getLocale(),true);
	
	%>
	<aui:model-context bean="<%= competence %>" model="<%= Competence.class %>" />
	<%
}
else
{
	%>
	<aui:model-context  model="<%= Competence.class %>" />
	<%
}
%>

<script type="text/javascript">
	function generateCertificate(){
		var divTemplate = document.getElementById("<portlet:namespace />templateContainer");
		
		if(divTemplate.style.display==='none'){
			divTemplate.style.display='block';
		}else{
			divTemplate.style.display='none';
		}
	}
</script>

<aui:form name="fm" action="<%=savecompetenceURL%>"  method="post" >

	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="competenceId" type="hidden" value="<%=competenceId %>"/>
	<aui:input name="title" label="title">
		<aui:validator name="required" errorMessage="field.required"></aui:validator>
	</aui:input>
	<aui:field-wrapper label="description">
			<liferay-ui:input-editor name="description" width="100%" initMethod="initEditorDescription" />
			<aui:input name="description" type="hidden" />
				<script type="text/javascript">
        			function <portlet:namespace />initEditorDescription() { return "<%= UnicodeFormatter.toString(description) %>"; }
   				</script>
	</aui:field-wrapper>
	<aui:input type="checkbox" value="<%= competence!=null?competence.getGenerateCertificate():StringPool.BLANK %>" name="generateCertificate" label="competence.generatecertificate" onChange="generateCertificate()">
	</aui:input>
	<div id="<portlet:namespace />templateContainer" <c:if test="<%= competence!=null?!competence.getGenerateCertificate():true %>">style="display:none"</c:if> >
		<aui:select name="page">
			<%for(String pagei : pages){ %>
				<aui:option value="<%=pagei%>" label="<%=pagei %>" selected="<%= competence==null?false:(pagei.equals(competence.getPage())) %>" ></aui:option>
			<%} %>
		</aui:select>
		<aui:field-wrapper label="competence.diplomaTemplate">
				<liferay-ui:input-editor name="template" width="100%" initMethod="initEditorTemplate" />
				<aui:input name="/>template" type="hidden" />
					<script type="text/javascript">
	        			function <portlet:namespace />initEditorTemplate() { return "<%= UnicodeFormatter.toString(template) %>"; }
	   				</script>
		</aui:field-wrapper>
		<div>
			<liferay-ui:message key="competence.helpcertificate" />
		</div>
	</div>
		<liferay-ui:panel-container>
		<liferay-ui:panel title="categorization" extended="false">
	<liferay-ui:custom-attributes-available className="<%= Competence.class.getName() %>">
		<liferay-ui:custom-attribute-list 
			className="<%=com.liferay.lms.model.Competence.class.getName()%>" classPK="<%=competenceId %>" editable="true" label="true"></liferay-ui:custom-attribute-list>
	</liferay-ui:custom-attributes-available>
	<aui:input name="tags" type="assetTags" />
	<aui:input name="categories" type="assetCategories" />
	</liferay-ui:panel>
	</liferay-ui:panel-container>
	<aui:button-row>
		<aui:button type="submit"></aui:button>	
		<aui:button onClick="<%=cancel %>" type="cancel" />
	</aui:button-row>
</aui:form>