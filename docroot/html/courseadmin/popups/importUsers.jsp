<%@page import="java.io.DataInputStream"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.File"%>
<%@page import="com.liferay.portal.service.permission.PortalPermissionUtil"%>
<%@page import="com.liferay.lms.util.LmsConstant"%>
<%@page import="com.liferay.lms.util.LmsPrefsPropsValues"%>
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


<portlet:actionURL name="importUsersCourse" var="importUsersCourseURL" >
	<portlet:param name="javax.portlet.action" value="importUsersCourseReport" /> 
	<portlet:param name="courseId" value="<%=ParamUtil.getString(renderRequest, \"courseId\") %>" /> 
	<portlet:param name="roleId" value="<%=ParamUtil.getString(request, \"roleId\") %>" /> 
</portlet:actionURL>

<div class="generic-pop-up aui-helper-hidden" id="${renderResponse.getNamespace()}divImportUsersCourse">
 	<div class="pop-up-content">
 	
		<span class="close" ></span>
		<p>
			<liferay-ui:header title="courseadmin.importuserrole"></liferay-ui:header>
			
			<liferay-ui:panel id="importuserrole_help" title="help" extended="closed">
				<%String columns = "";
				
				String[] arguments = new String[1];
				if (CompanyConstants.AUTH_TYPE_SN.equalsIgnoreCase(authType)) {
					columns = "\"" + LanguageUtil.get(themeDisplay.getLocale(), "screen-name") + "\"";	
				}else if(CompanyConstants.AUTH_TYPE_EA.equalsIgnoreCase(authType)){
					columns = "\"" + LanguageUtil.get(themeDisplay.getLocale(), "email-address") + "\"";
				}else{
					columns = "\"" + LanguageUtil.get(themeDisplay.getLocale(), "user-id") + "\"";
				}
				
				if(!LmsPrefsPropsValues.getUsersExtendedData(themeDisplay.getCompanyId()) || PortalPermissionUtil.contains(
			    		themeDisplay.getPermissionChecker(), LmsConstant.ACTION_VIEW_USER_EXTENDED)){
					columns += ";\"" + LanguageUtil.get(themeDisplay.getLocale(), "first-name") + "\";\"" + 
							LanguageUtil.get(themeDisplay.getLocale(), "last-name") + "\"";
				}
				arguments[0] = columns;
				%>		
				<%=LanguageUtil.format(themeDisplay.getLocale(),"courseadmin.importuserrole.help",arguments) %>
			</liferay-ui:panel>
		</p>
		
		<span>
			<%=LanguageUtil.get(pageContext,"courseadmin.importuserrole.download") +" "+ urlExample%>
		</span>
		<aui:form name="fm" action="<%=importUsersCourseURL%>"  method="post" enctype="multipart/form-data" >

	    	<aui:input inlineLabel="left" inlineField="true" name="fileName" label="file" id="fileName" type="file" value="" >
	    		<aui:validator name="required"/>
				<aui:validator errorMessage="error-csv-input-file" name="acceptFiles">'csv'</aui:validator>
		    </aui:input>
			
			<aui:button-row>
				<aui:button type="submit" value="import" />
			</aui:button-row>
		</aui:form>
		
		
		<div id="${renderResponse.getNamespace()}divImportUsersCourseProgress" class="aui-helper-hidden">
			<progress id="${renderResponse.getNamespace()}importUsersCourseProgressBar" value="0" max="100">
				<liferay-ui:message key="generating"/>
			</progress>
		</div>	
		<div id="${renderResponse.getNamespace()}importUsersCourseResultsReport" class="aui-helper-hidden"></div>
		<div id="${renderResponse.getNamespace()}errors" class="aui-helper-hidden"></div>
		
	</div>
</div>



<script>
	$('.close').on('click',function(){
		$('#<portlet:namespace />divImportUsersCourse').addClass("aui-helper-hidden");
		<portlet:namespace />hideComponents();
	});
	function <portlet:namespace />showImportUsersCourse(){
		$('#<portlet:namespace />divImportUsersCourse').removeClass("aui-helper-hidden");
	}
	function <portlet:namespace />hideComponents(){
		if(!$('#<portlet:namespace />importUsersCourseResultsReport').hasClass("aui-helper-hidden")){
			$('#<portlet:namespace />importUsersCourseResultsReport').addClass("aui-helper-hidden");
		}
		if(!$('#<portlet:namespace />importUsersCourseProgressBar').hasClass("aui-helper-hidden")){
			$('#<portlet:namespace />importUsersCourseProgressBar').addClass("aui-helper-hidden");
		}	
	}
	function <portlet:namespace />downloadReport(url){
		location.href = url;
		//<portlet:namespace />showImportUsersCourse();
		Liferay.Portlet.refresh(AUI().one('#p_p_id<portlet:namespace />'),{'p_t_lifecycle':0, 'view':'role-members-tab','courseId':'${course.courseId }'});
	}
</script>	

<c:if test="${not empty UUID}">
	<script>	
		$('#<portlet:namespace />divImportUsersCourseProgress').removeClass("aui-helper-hidden");
		if($('#<portlet:namespace />divImportUsersCourse').hasClass("aui-helper-hidden")){
			$('#<portlet:namespace />divImportUsersCourse').removeClass("aui-helper-hidden");
		}
		$(document).ready(function(){
			<portlet:namespace />readThreadState();
		});
		function <portlet:namespace />readThreadState(){
			console.log("readThreadState 1");
			$.ajax({
				dataType: 'json',
				url: '${importUsersCourseReportURL }',
			    cache:false,
				data: {
					uuid : '${UUID}',
					action : 'importUsersCourseReport',
				},
				success: function(data){
					console.log("readThreadState - data: " + data);
					if(data){						
				    	if(!data.finished){		
				    		console.log("readThreadState 1 - data: " + data.progress);
				    		$('#<portlet:namespace />importUsersCourseProgressBar').val(data.progress);
				    		setTimeout(<portlet:namespace />readThreadState,100);
				    	}else{	
				    		console.log("readThreadState 2 - data.fileReport: " + data.fileReport);
				    		
					   		if (data.errors){
					   			$('#<portlet:namespace />errors').append('<div class="portlet-msg-error">'+data.errors+'</div>');
					    		$('#<portlet:namespace />errors').removeClass("aui-helper-hidden");
					    		$('#<portlet:namespace />divImportUsersCourseProgress').addClass("aui-helper-hidden");
					   		}else{
					   			if(data.result){
					   				$('#<portlet:namespace />importUsersCourseProgressBar').val(100);
						    		$('#<portlet:namespace />importUsersCourseResultsReport').empty();
									$('#<portlet:namespace />importUsersCourseResultsReport').append('<div><a onClick="javascript:<portlet:namespace />downloadReport(\'${importUsersCourseReportURL}&fileReport=' + data.fileReport + '&contentType=' + data.contentType + '&UUID=' + data.UUID + '&action='+data.action+'\'); "><liferay-ui:message key="courseadmin.importuserrole.download-report-result"/></a></div>');
									$('#<portlet:namespace />result').append('<b><liferay-ui:message key="courseadmin.importuserrole.result"/></b> '+data.result);
						   			$('#<portlet:namespace />importUsersCourseResultsReport').append('<div class="portlet-msg-success"><liferay-ui:message key="courseadmin.importuserrole.csv.saved"/></div>');
						   			

						   			$("#<portlet:namespace />divImportUsersCourse span.close").on( "click", function() {
						   			  $("#<portlet:namespace />fm").submit();
						   			});
						   			
						   			
						   		}
					   		}
					   	// Cerramos el panel con la ayuda para importar los usuarios
					   		$('#importuserrole_help').addClass('lfr-collapsed'); 
							$('#<portlet:namespace />importUsersCourseResultsReport').removeClass("aui-helper-hidden");
				    	}
					}else{					
						<portlet:namespace />hideComponents();
					}
				},
				error: function(){
					<portlet:namespace />hideComponents();	
				}
			});
		}
		
	</script>
</c:if>	