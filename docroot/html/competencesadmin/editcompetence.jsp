<%@page import="com.liferay.portlet.expando.model.ExpandoColumnConstants"%>
<%@page import="com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil"%>
<%@page import="com.liferay.portlet.expando.model.ExpandoColumn"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.portal.kernel.exception.SystemException"%>
<%@page import="com.liferay.portlet.documentlibrary.NoSuchFolderException"%>
<%@page import="com.liferay.portal.kernel.exception.PortalException"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.Folder"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFolderConstants"%>
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
<portlet:resourceURL var="resourcePreviewURL" />

<liferay-ui:error key="title-required" message="title-required" />
<liferay-ui:error key="title-empty" message="title-empty" />
<liferay-ui:error key="title-repeated" message="title-repeated" />


<liferay-ui:header backURL="<%=cancel%>" showBackURL="<%=Boolean.TRUE%>" title="" />

<%

	String portletId = themeDisplay.getPortletDisplay().getId();

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

	// Recuperar fondos del directorio incluido en el fichero de propiedades
	String path = PropsUtil.get("com.liferay.lms.diploma.background.path");
	long repositoryId = DLFolderConstants.getDataRepositoryId(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	List<FileEntry> fileEntryList = new ArrayList<FileEntry>();
	if (Validator.isNotNull(path) && !path.isEmpty()) {
		StringTokenizer st = new StringTokenizer(path, "/");
		Folder folder = null;
		while (st.hasMoreTokens()) {
			String folderName = st.nextToken();
			long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			if (Validator.isNotNull(folder)) {
				parentFolderId = folder.getFolderId();
			}
			
			try {
				folder = DLAppServiceUtil.getFolder(repositoryId, parentFolderId, folderName);
			} catch (NoSuchFolderException e) {
				folder = null;
			} catch (PortalException e) {
				folder = null;
			} catch (SystemException e) {
				folder = null;
			}
		}
		
		if (Validator.isNotNull(folder)) {
			
			try {
				fileEntryList = DLAppServiceUtil.getFileEntries(repositoryId, folder.getFolderId());
			} catch (PortalException e) {
				
			} catch (SystemException e) {
				
			}
		}
		
	}
	
	// Se obtienen los campos personalizados asociados a Curso
	List<ExpandoColumn> courseExpandoColumnList = new ArrayList<ExpandoColumn>();
	try {
		courseExpandoColumnList = ExpandoColumnLocalServiceUtil.getDefaultTableColumns(themeDisplay.getCompanyId(), Course.class.getName());
	} catch (SystemException e) {
	}
	
	// Tipos de informacion a mostrar en la segunda pagina
	String [] diplomaAdditionalTypes = PropsUtil.get("com.liferay.lms.diploma.additional.type").split(StringPool.COMMA);

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

<aui:form name="fm" role="form" action="<%=savecompetenceURL%>"  method="post" >

	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="competenceId" type="hidden" value="<%=competenceId %>"/>
	<aui:input name="title" label="competence.title">
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
		<%-- Campo fondo de diploma --%>
		<aui:select name="diplomaBackground" label="competence.diplomaBackground">
			<aui:option label="" value="" />
			<c:forEach items="<%=fileEntryList%>" var="fondo" >
				<aui:option label="${fondo.title}" value="${fondo.fileEntryId}" selected="${fondo.fileEntryId == competence.diplomaBackground}" data-uuid="${fondo.uuid}" data-groupId="${fondo.groupId}" />
			</c:forEach>
		</aui:select>
		
		<br/>
		
		<a id='<portlet:namespace/>diplomaBackgroundLink' href="" target="_BLANK" title="view-image" class="aui-buttonitem-content yui3-widget aui-component aui-buttonitem aui-state-default aui-buttonitem-icon-label aui-toolbar-first aui-toolbar-item"><liferay-ui:message key="view"/></a>
		
		<aui:script>
			AUI().use('aui-base',function(A) {
			
				function showImage() {
			
					var diplomaBackgroundValue = A.one('#<portlet:namespace/>diplomaBackground').val();
					var diplomaBackgroundLink = A.one('#<portlet:namespace/>diplomaBackgroundLink');
					if (diplomaBackgroundValue != "" && diplomaBackgroundValue != "0") {
						var uuid = A.one('#<portlet:namespace/>diplomaBackground :checked').attr('data-uuid');
						var groupId = A.one('#<portlet:namespace/>diplomaBackground :checked').attr('data-groupId');
						
						var imageurl = '/c/document_library/get_file?uuid=' + uuid + '&groupId=' + groupId;
						diplomaBackgroundLink.attr('href', imageurl);
						
						diplomaBackgroundLink.show();
					} else {
						diplomaBackgroundLink.hide();
					}
					
				}
			
				A.one('#<portlet:namespace/>diplomaBackground').on('change', function() {
					showImage();
				});
				
				showImage();
			
			});
		</aui:script>
		
		<aui:field-wrapper label="competence.diplomaTemplate">
				<liferay-ui:input-editor name="template" width="100%" initMethod="initEditorTemplate" />
				<aui:input name="/>template" type="hidden" />
					<script type="text/javascript">
	        			function <portlet:namespace />initEditorTemplate() { return "<%= UnicodeFormatter.toString(template) %>"; }
	   				</script>
		</aui:field-wrapper>
		<div>
			<liferay-ui:message key="competence.helpcertificate" />
			<%-- Campos personalizados asociados al Curso --%>
			<c:set var="propertyHidden" value="<%=ExpandoColumnConstants.PROPERTY_HIDDEN%>" />
			<c:forEach items="<%=courseExpandoColumnList%>" var="courseExpandoColumn" >
				<c:choose>
					<c:when test="${courseExpandoColumn.getTypeSettingsProperties().get(propertyHidden) != null}">
						<c:if test='${"1".compareTo(courseExpandoColumn.getTypeSettingsProperties().get(propertyHidden)) != 0}'>
							<strong>&#36;${courseExpandoColumn.name}</strong>&nbsp;&#58;&nbsp;${courseExpandoColumn.getDisplayName(locale)}<br/>
						</c:if>
					</c:when>
					<c:otherwise>
						<strong>&#36;${courseExpandoColumn.name}</strong>&nbsp;&#58;&nbsp;${courseExpandoColumn.getDisplayName(locale)}<br/>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
		
		<aui:field-wrapper>
		
			<aui:select name="diplomaAdditional" label="competence.diplomaAdditional" >
				<aui:option label="" value="0" />
				<c:forEach items="<%=diplomaAdditionalTypes%>" var="type" >
					<aui:option label="competence.diplomaAdditional.${type}" value="${type}" selected="${type == competence.diplomaAdditional}" />
				</c:forEach>
			</aui:select>
		
		</aui:field-wrapper>
		
		<aui:button-row>
			<a id='<portlet:namespace/>viewPreviewLink' href="" target="_blank" class="aui-buttonitem-content yui3-widget aui-component aui-buttonitem aui-state-default aui-buttonitem-icon-label aui-toolbar-first aui-toolbar-item"><liferay-ui:message key="preview"/></a>

			<aui:script use="aui-base,liferay-util-window,liferay-portlet-url,aui-io-request">
			
			A.one('#<portlet:namespace/>viewPreviewLink').on('click', function() {
			
				var title = '';
				if (A.one('input#<portlet:namespace/>title_<%=themeDisplay.getLanguageId()%>') != null) {
					title = A.one('input#<portlet:namespace/>title_<%=themeDisplay.getLanguageId()%>').val();
				}
				var page = A.one('select#<portlet:namespace/>page').val();
				var template = window.<portlet:namespace/>template.getHTML();
				var background = A.one('#<portlet:namespace/>diplomaBackground').val();
				var additional = A.one('select#<portlet:namespace/>diplomaAdditional').val();
							
				var resourceURL = Liferay.PortletURL.createResourceURL();
				resourceURL.setPortletId('<%=portletId%>');
				resourceURL.setParameter('title', title);
				resourceURL.setParameter('page', page);
				resourceURL.setParameter('template', template);
				resourceURL.setParameter('background', background);
				resourceURL.setParameter('additional', additional);
				
				var viewPreviewLink = A.one('a#<portlet:namespace/>viewPreviewLink');
				viewPreviewLink.attr('href', resourceURL.toString());
				
			});
			
			</aui:script>
		</aui:button-row>
		
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