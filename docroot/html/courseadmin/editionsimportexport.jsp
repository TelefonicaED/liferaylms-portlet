<div class="generic-pop-up aui-helper-hidden" id="${renderResponse.getNamespace()}importExportEditions">
 	<div class="pop-up-content">
		<span class="close" ></span>
		<p><liferay-ui:message key="course-admin.editions.import-export.help"/></p>
		<a href="${importEditionsExampleURL }" ><liferay-ui:message key="course-admin.editions.import-export.download-example-file"/></a>
		<aui:form name="importFm" action="${importEditionsURL }" role="form" method="POST" enctype="multipart/form-data"  >
			<aui:select name="courseTemplate" label="course-template">
				<c:forEach items="${lspList}" var="lsp">
					<aui:option value="${lsp.layoutSetPrototypeId}" selected="${lsp.layoutSetPrototypeId == parentLspId}">${lsp.getName(themeDisplay.getLocale())}</aui:option>
				</c:forEach>
			</aui:select>
	    	<aui:input inlineLabel="left" inlineField="true" name="fileName" label="file" id="fileName" type="file" value="" >
	    		<aui:validator name="required"/>
				<aui:validator errorMessage="error-csv-input-file" name="acceptFiles">'csv'</aui:validator>
		    </aui:input>
			<aui:button-row>
				<aui:button type="submit" value="import" />
			</aui:button-row>
		</aui:form>
		<div id="${renderResponse.getNamespace()}divImportEditionsProgress" class="aui-helper-hidden">
			<progress id="${renderResponse.getNamespace()}importEditionsProgressBar" value="0" max="100">
				<liferay-ui:message key="generating"/>
			</progress>
		</div>	
		<div id="${renderResponse.getNamespace()}importEditionsResultsReport" class="aui-helper-hidden"></div>
	</div>
</div>

<script>
	$('.close').on('click',function(){
		$('#<portlet:namespace />importExportEditions').addClass("aui-helper-hidden");
		<portlet:namespace />hideComponents();
	});
	function <portlet:namespace />importExportEditions(){
		$('#<portlet:namespace />importExportEditions').removeClass("aui-helper-hidden");
	}
	function <portlet:namespace />hideComponents(){
		if(!$('#<portlet:namespace />importEditionsResultsReport').hasClass("aui-helper-hidden")){
			$('#<portlet:namespace />importEditionsResultsReport').addClass("aui-helper-hidden");
		}
		if(!$('#<portlet:namespace />importEditionsProgressBar').hasClass("aui-helper-hidden")){
			$('#<portlet:namespace />importEditionsProgressBar').addClass("aui-helper-hidden");
		}	
	}
	function <portlet:namespace />downloadEditionsReport(url,countRegistered){
		location.href = url;
		<portlet:namespace />hideComponents();
		$('#<portlet:namespace />importExportEditions').addClass("aui-helper-hidden");
		if(countRegistered>0){
			Liferay.Portlet.refresh(AUI().one('#p_p_id<portlet:namespace />'),{'p_t_lifecycle':0, 'view':'editions','courseId':'${course.courseId }','importEditionsSuccess':true});
		}
	}
</script>	

<c:if test="${not empty UUID}">
	<script>	
		$('#<portlet:namespace />divImportEditionsProgress').removeClass("aui-helper-hidden");
		if($('#<portlet:namespace />importExportEditions').hasClass("aui-helper-hidden")){
			$('#<portlet:namespace />importExportEditions').removeClass("aui-helper-hidden");
		}
		$(document).ready(function(){
			<portlet:namespace />readThreadState();
		});
		function <portlet:namespace />readThreadState(){
			$.ajax({
				dataType: 'json',
				url: '${importEditionsResultsReportURL }',
			    cache:false,
				data: {
					uuid : '${UUID}',
				},
				success: function(data){
					if(data){						
				    	if(!data.finished){		
				    		$('#<portlet:namespace />importEditionsProgressBar').val(data.progress);
				    		setTimeout(<portlet:namespace />readThreadState,100);
				    	}else{	
				    		$('#<portlet:namespace />importEditionsProgressBar').val(100);
				    		$('#<portlet:namespace />importEditionsResultsReport').empty();
							$('#<portlet:namespace />importEditionsResultsReport').append('<div><a href="javascript:${renderResponse.getNamespace() }downloadEditionsReport(\'${importEditionsResultsReportURL }&file= '+data.filePath+'\', \''+data.registered+'\'); "><liferay-ui:message key="course-admin.editions.import-export.download-report-result"/></a></div>');
							$('#<portlet:namespace />importEditionsResultsReport').append('<div><liferay-ui:message key="course-admin.editions.import-export.imported-editions"/>: '+data.registered+'</div>');
							$('#<portlet:namespace />importEditionsResultsReport').append('<div><liferay-ui:message key="course-admin.editions.import-export.total-editions"/>: '+data.total+'</div>');
					   		if(data.result){
					   			$('#<portlet:namespace />importEditionsResultsReport').append('<div class="portlet-msg-success"><liferay-ui:message key="course-admin.editions.import-export.success"/></div>');	    			
					   		}else{
					   			$('#<portlet:namespace />importEditionsResultsReport').append('<div class="portlet-msg-error"><liferay-ui:message key="course-admin.editions.import-export.errors"/></div>');	    			
					   		}
							$('#<portlet:namespace />importEditionsResultsReport').removeClass("aui-helper-hidden");
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