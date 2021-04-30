<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>

<%--  --%>

<%
	if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model.Course", themeDisplay.getScopeGroupId(), "ASSIGN_MEMBERS")){
%>
		<portlet:resourceURL var="importUsersFromCsvExampleURL" id="importUsersFromCsvExample"/>
		<portlet:resourceURL var="importUsersFromCsvURL" id="importUsersFromCsv"/>
		<portlet:actionURL var="readImportFromCsvURL" name="readImportFromCsv"/>
<%		
		LmsPrefs prefs = LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
		long editorRoleId  = prefs.getEditorRole();
		long teacherRoleId = prefs.getTeacherRole();
		Role editorRole = RoleLocalServiceUtil.fetchRole(editorRoleId);
		Role teacherRole = RoleLocalServiceUtil.fetchRole(teacherRoleId);
		Role studentRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER) ;
%>	
	<liferay-ui:icon-menu message="courseadmin.import">
		<liferay-ui:icon 
 			image="assign"
 			label="<%=true %>"
 			message="courseadmin.import.csv.assign-users"
 			url="javascript:${renderResponse.getNamespace() }openImportUsersCsvPopUp('assignUsers');"
 		/>
		
		<liferay-ui:icon 
 			image="unassign_user"
 			label="<%=true %>"
 			message="courseadmin.import.csv.unassign-users"
 			url="javascript:${renderResponse.getNamespace() }openImportUsersCsvPopUp('unassignUsers');"
 		/> 
 	</liferay-ui:icon-menu>
		
		<div id="${renderResponse.getNamespace() }importUsersDivId" class="generic-pop-up aui-helper-hidden">
			
			<div class="pop-up-content">
				
				<span class="close" onClick="javascript:${renderResponse.getNamespace() }closeImportUsersCsvPopUp();"></span>
				
				<h1 id="${renderResponse.getNamespace() }importCsvTitle"></h1>
				
				<liferay-ui:error key="import.csv.users.role-required" message="courseadmin.import.csv.users.role-required"></liferay-ui:error>
				<liferay-ui:error key="import.csv.users.bad-format.size" message="courseadmin.importuserrole.csv.badFormat.size"></liferay-ui:error>
				<liferay-ui:error key="import.csv.users.bad-format" message="courseadmin.importuserrole.csv.badFormat"></liferay-ui:error>
				<liferay-ui:error key="import.csv.users.file-required" message="courseadmin.importuserrole.csv.fileRequired"></liferay-ui:error>
				<liferay-ui:error key="import.csv.users.error" message="courseadmin.import.csv.users.error"></liferay-ui:error>
				
				<p><liferay-ui:message key="courseadmin.import.csv.help"/></p>
				
				<p><liferay-ui:message key="courseadmin.import.csv.example.download"/>
					<a href='${importUsersFromCsvExampleURL }'  >
						<liferay-ui:message key="example"/>
					</a>
				</p>
				
				<div id="${renderResponse.getNamespace() }importUsersFromCsvDivId">
				
					<aui:form name="importUsersFromCsvForm" action="${readImportFromCsvURL }" method="POST" enctype="multipart/form-data" 
							onSubmit="javascript:${renderResponse.getNamespace() }submitImportUsersCsv();">
						
						<aui:input type="hidden" name="importType" value="" />
						
						<aui:select name="importAssignUsersRole" label="courseadmin.import.csv.users-role">
							<aui:option value="-1"></aui:option>
							<aui:option value="<%=studentRole.getRoleId() %>"><%=LanguageUtil.get(themeDisplay.getLocale(), "courseadmin.adminactions.students") %></aui:option>
							<aui:option value="<%=editorRole.getRoleId() %>"><%=editorRole.getTitle(themeDisplay.getLocale()) %></aui:option>
							<aui:option value="<%=teacherRole.getRoleId() %>"><%=teacherRole.getTitle(themeDisplay.getLocale()) %></aui:option>
						</aui:select>
						
						<aui:input inlineLabel="left" inlineField="true" name="fileName" label="file" id="fileName" type="file" value="" >
				    		<aui:validator name="required"/>
							<aui:validator errorMessage="error-csv-input-file" name="acceptFiles">'csv'</aui:validator>
					    </aui:input>
					    
				    	<aui:button-row>
							<aui:button type="submit" value="import"/>
						</aui:button-row>
					
					</aui:form>
				</div>
				
				<div id="${renderResponse.getNamespace()}progressBarImportUsersCsvDiv" class='aui-helper-hidden'>
					<liferay-ui:message key="courseadmin.import.csv.importing-data"/>
					<progress id="${renderResponse.getNamespace()}progressBarImportUsersCsv" value="0" max="100"></progress>
				</div>
				<div id="${renderResponse.getNamespace() }downloadReportImportUsersCsvDiv" class="aui-helper-hidden"></div>
				
			</div>
		</div>

		<script>
			function <portlet:namespace />openImportUsersCsvPopUp(importType){
				if(importType === 'assignUsers' || importType === 'unassignUsers'){
					var divTitle = $('#<portlet:namespace />importCsvTitle');
					if(importType === 'assignUsers'){
						divTitle.text('<liferay-ui:message key="courseadmin.import.csv.assign-users"/>');
					} else {
						divTitle.text('<liferay-ui:message key="courseadmin.import.csv.unassign-users"/>');
					}
					$('form[name="<portlet:namespace />importUsersFromCsvForm"] input[name="<portlet:namespace />importType"]').val(importType);
					$('#<portlet:namespace />importUsersDivId').removeClass('aui-helper-hidden');
				} else {
					console.log(":::ERROR:::: Tipo de importación?? asignar usuarios/eliminar usuarios?? ");
				}
			}
			
			function <portlet:namespace />closeImportUsersCsvPopUp(){
				$('#<portlet:namespace />importUsersDivId').addClass('aui-helper-hidden');
			}
			
			function <portlet:namespace />downloadImportCsvReport(filePath){
				$('#<portlet:namespace />downloadReportImportUsersCsvDiv').addClass('aui-helper-hidden');
				location.href = filePath;	
			}
			
			function <portlet:namespace />submitImportUsersCsv(){
				var roleValue = $('form[name="<portlet:namespace />importUsersFromCsvForm"] select[name="<portlet:namespace />importAssignUsersRole"]').val();
				if(roleValue!=-1){
					$('form[name="<portlet:namespace />importUsersFromCsvForm"]').submit();
				} else {
					alert(Liferay.Language.get("courseadmin.import.csv.mandatory-select-role"));
				}
			}
			
		</script>
		
		<%
			String UUID = ParamUtil.getString(request, "UUID");
			String importType = ParamUtil.getString(request, "importType");
			if(UUID!=null && importType!=null && importType!=""){
		%>
			<script>
				$(document).ready(function(){
					<portlet:namespace />readThreadStateImportUsers('<%=UUID%>', '<%=importType%>');
				});
				function <portlet:namespace />readThreadStateImportUsers(uuid, importType){
					console.log("::Read thread state:: " + uuid);
					$.ajax({
						dataType: 'json',
						url: '${importUsersFromCsvURL }',
						cache: false,
						data: {
							uuid: uuid,
							importType: importType
						},
						
						success: function(data){
							if(data){
								
								var formDiv = $("#<portlet:namespace />importUsersFromCsvDivId");
								var progressBarDiv = $('#<portlet:namespace />progressBarImportUsersCsvDiv');
								var downloadReportDiv = $('#<portlet:namespace />downloadReportImportUsersCsvDiv');
								
								if(!data.finished){
									
									formDiv.addClass("aui-helper-hidden");
									progressBarDiv.removeClass("aui-helper-hidden");
									progressBarDiv.val(data.progress);
						    		setTimeout(<portlet:namespace />readThreadStateImportUsers(data.UUID, data.importType),1000);
								
								} else {
									
									progressBarDiv.val(100);
									progressBarDiv.addClass("aui-helper-hidden");
									formDiv.removeClass("aui-helper-hidden");
									downloadReportDiv.append('<div><a style="cursor:pointer;" onClick="javascript:<portlet:namespace />downloadImportCsvReport(\'${importUsersFromCsvURL}&file=' + data.filePath + '&importType= ' + data.importType + '\'); "><liferay-ui:message key="courseadmin.import.csv.result-import-report"/></a></div>');
									downloadReportDiv.removeClass('aui-helper-hidden');
								}
								
								<portlet:namespace />openImportUsersCsvPopUp(importType);
								
							} else {
								console.log("ERROR IMPORT CSV USERS -- No data response")
							}
						},
						
						error: function(){
							console.log("ERROR IMPORT CSV USERS");
						}
					});
				}
			</script>
<%		} 
	}
%>
