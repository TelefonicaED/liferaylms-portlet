<aui:form name="fm" action="<%=setActivity%>"  method="post" enctype="multipart/form-data" cssClass='<%=(result!=null)?((result.getEndDate()!= null)?"aui-helper-hidden":""):""%>' >
	<aui:fieldset>
	<% if(isSetTextoEnr){ %>
		<aui:input type="hidden" name="text" value='<%=text %>'/>
		<aui:field-wrapper label="text" name="DescripcionRichTxt" >
			<div id="<portlet:namespace/>DescripcionRichTxt" ></div>
		</aui:field-wrapper>
		<liferay-ui:input-editor toolbarSet="actliferay" name="DescripcionRichTxt" initMethod="initEditor"  />
	<% }  else { %>
		<aui:field-wrapper label="text" name="text" >
			<aui:input type="textarea" cols="100" rows="5" name="text" label="" value='<%=text %>'/>
		</aui:field-wrapper>
	<% }
	   if(isSetFichero){ %>
		<aui:field-wrapper label="courseadmin.importuserrole.file" name="fileName" >
	  		<aui:input inlineLabel="left" inlineField="true"
			  	name="fileName" label="" id="fileName" type="file" value="" />
			<%if(urlFile!=null) {%>
				<aui:a href="<%=urlFile %>" ><%=titleFile %></aui:a>
			<%} %>
		</aui:field-wrapper>
		<liferay-ui:error exception="<%= FileSizeException.class %>">
			<%
			long fileMaxSize = GetterUtil.getLong(PrefsPropsUtil.getString(PropsKeys.DL_FILE_MAX_SIZE));
	
			if (fileMaxSize == 0) {
				fileMaxSize = GetterUtil.getLong(PrefsPropsUtil.getString(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));
			}
	
			fileMaxSize /= 1024;
			%>
	
			<liferay-ui:message arguments="<%= fileMaxSize %>" key="onlineActivity.enter.valid.file" />
		</liferay-ui:error>
		<liferay-ui:error exception="<%= com.liferay.portlet.documentlibrary.FileExtensionException.class %>">
			<liferay-ui:message key="onlineActivity.not.allowed.file.type"/>
		</liferay-ui:error>
		<liferay-ui:error key="onlineActivity.mandatory.file" message="onlineActivity.mandatory.file" />
		<liferay-ui:error key="onlineActivity-error-file-type" message="onlineActivity.not.allowed.file.type" />
	<% } %>
	<liferay-ui:error key="onlineActivity.max-tries" message="onlineActivity.max-tries" />
	</aui:fieldset>
	<aui:button-row>
		<aui:button type="submit" value="onlinetaskactivity.save" onClick='<%=(isSetTextoEnr)?(renderResponse.getNamespace() + "extractCodeFromEditor()"):""%>'></aui:button>
	</aui:button-row>
</aui:form>

<script>
	function <portlet:namespace />initEditor() {
		return "<%=text %>";
	}
    function <portlet:namespace />extractCodeFromEditor(){
		document.<portlet:namespace />fm['<portlet:namespace />text'].value = window['<portlet:namespace />DescripcionRichTxt'].getHTML();
    }
</script>