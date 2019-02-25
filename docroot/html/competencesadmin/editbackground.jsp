<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntryConstants"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.portal.model.ResourceConstants"%>
<%@page import="com.liferay.portal.service.ResourcePermissionLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.NoSuchFolderException"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.Folder"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFolderConstants"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CompetenceLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<%
	String competenceImageURL=CompetenceLocalServiceUtil.getBGImageURL(themeDisplay.getScopeGroupId(), request);
	long companyId = themeDisplay.getCompanyId();
	
	Role siteMemberRole = RoleLocalServiceUtil.getRole(companyId, RoleConstants.SITE_MEMBER);
	Role guestRole = RoleLocalServiceUtil.getRole(companyId, RoleConstants.GUEST);

	ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFolderConstants.getClassName(),renderRequest);
	long repositoryId = DLFolderConstants.getDataRepositoryId(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	String path = PropsUtil.get("com.liferay.lms.diploma.background.path");
	String [] folders = path.split(StringPool.SLASH);
	Folder folder = null;
	if (Validator.isNotNull(folders)) {
		for(int i = 0; i < folders.length; i++) {
			String folderName = folders[i].trim();
			long parentFolderId = Validator.isNull(folder)?DLFolderConstants.DEFAULT_PARENT_FOLDER_ID:folder.getFolderId();
			
			if ((i == 0 && Validator.isNull(folder)) || (i > 0 && Validator.isNotNull(folder))) {
				try {
					folder = DLAppServiceUtil.getFolder(repositoryId, parentFolderId, folderName);
					
				} catch (NoSuchFolderException e) {
					try {
						folder = DLAppServiceUtil.addFolder(repositoryId, parentFolderId, folderName, StringPool.BLANK, serviceContext);
						
						if (Validator.isNotNull(folder)) {
							String [] siteMemberActionIds = { ActionKeys.VIEW, ActionKeys.ADD_DOCUMENT };
							String [] guestActionIds = { ActionKeys.VIEW };
							ResourcePermissionLocalServiceUtil.setResourcePermissions(companyId, DLFolderConstants.getClassName(), ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(folder.getFolderId()), siteMemberRole.getRoleId(), siteMemberActionIds);
							ResourcePermissionLocalServiceUtil.setResourcePermissions(companyId, DLFolderConstants.getClassName(), ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(folder.getFolderId()), guestRole.getRoleId(), guestActionIds);
						}
					} catch (Exception e1) {
						
					}
				}
			}
		}
	}
	
	List<FileEntry> fileEntryList = new ArrayList<FileEntry>();
	int cur = ParamUtil.get(renderRequest, "cur", SearchContainer.DEFAULT_CUR);
	int delta = ParamUtil.get(renderRequest, "delta", SearchContainer.DEFAULT_DELTA);
	int fondosTotal = 0;
	if (Validator.isNotNull(folder)) {
		
		fondosTotal = DLAppServiceUtil.getFileEntriesCount(repositoryId, folder.getFolderId());

		int start = (cur - 1) * delta;
		int end = start + delta;
		
		fileEntryList = DLAppServiceUtil.getFileEntries(repositoryId, folder.getFolderId(), start, end);
	}
	
	PortletURL fondosSearchContainerURL = renderResponse.createRenderURL();
	fondosSearchContainerURL.setParameter("jspPage", "/html/competencesadmin/editbackground.jsp");
	
%>

<portlet:renderURL var="cancelURL" />
<portlet:actionURL var="saveBackgroundURL" name="saveBackground" />

<liferay-ui:header backURL="<%=cancelURL%>" title="competence.edit-background"  />

<liferay-ui:success key="add-diploma-background-success" message="competence.add-diploma-background-success" />
<liferay-ui:error key="add-diploma-background-error" message="competence.add-diploma-background-error" />
<liferay-ui:error key="duplicate-diploma-background-error" message="competence.duplicate-diploma-background-error" />

<liferay-ui:search-container iteratorURL="<%=fondosSearchContainerURL%>">
	<liferay-ui:search-container-results total="<%=fondosTotal%>" results="<%=fileEntryList%>" />
	<liferay-ui:search-container-row modelVar="fileEntry" className="com.liferay.portal.kernel.repository.model.FileEntry" rowIdProperty="fileEntryId" keyProperty="fileEntryId" >
	
		<c:set var="imageURL" value="/c/document_library/get_file?uuid=${fileEntry.uuid}&groupId=${fileEntry.groupId}"/>
		<liferay-ui:search-container-column-text name="" align="center" >
			<a href="${imageURL}" title="${fileEntry.title}" target="_BLANK" >
				<img src="${imageURL}" alt="${fileEntry.title}" style="max-height: 50px;" />
			</a>
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text name="competence.file-name">
			<a href="${imageURL}" title="${fileEntry.title}" target="_BLANK" >
				${fileEntry.title}
			</a>
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text>
			<%
				boolean showDeleteButton = permissionChecker.hasPermission(scopeGroupId, DLFileEntryConstants.getClassName(), scopeGroupId, ActionKeys.DELETE);
			%>
			<c:if test="<%=showDeleteButton%>">
				<portlet:actionURL var="deleteBackgroundURL" name="deleteBackground">
					<portlet:param name="fileEntryId" value="${fileEntry.fileEntryId}" />
				</portlet:actionURL>
				<liferay-ui:icon-delete url="${deleteBackgroundURL.toString()}"/>
			</c:if>
		</liferay-ui:search-container-column-text>
		
	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator searchContainer="${searchContainer}" paginate="<%=true%>" />
</liferay-ui:search-container>
			
<aui:form name="fm" action="<%=saveBackgroundURL%>" role="form" method="post" enctype="multipart/form-data">
	<aui:input type="hidden" name="groupId" value="<%=themeDisplay.getScopeGroupId() %>"/>
	<aui:input type="hidden" name="folderId" value="<%=folder.getFolderId()%>" />
	<aui:input name="fileName" label="image" id="fileName" type="file" value="" >
		<aui:validator name="acceptFiles">'jpg, jpeg, png, gif'</aui:validator>
	</aui:input>
	<aui:button-row>
		<aui:button type="submit"></aui:button>							
		<aui:button onClick="<%=cancelURL %>" type="cancel" />
	</aui:button-row>
</aui:form>