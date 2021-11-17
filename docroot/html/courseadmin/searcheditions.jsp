<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@include file="/init-min.jsp" %>
	
<liferay-portlet:resourceURL var="searchURL">
	<liferay-portlet:param name="action" value="searchEditions"/>
</liferay-portlet:resourceURL>
<liferay-portlet:resourceURL var="addAllEditionsURL">
	<liferay-portlet:param name="action" value="addAllEditions"/>
</liferay-portlet:resourceURL>
	
<aui:form action="javascript:${namespace}searchEditions();" name="searchFm" method="post" role="search">	
		
	<div class="row">
		<div class="col-md-4">
			<aui:input inlineField="true" name="freetext" type="text" value="${freetext}" >
					<aui:validator name="maxLength">150</aui:validator>
			</aui:input>
		</div>
		<div class="startDatePnl col-md-4">
			<aui:input helpMessage="help.course-admin.start-execution-date" name="startDateFilter" label="course-admin.start-execution-date" type="checkbox" 
				onChange="javascript:showStartDate('startDateFilterCheckbox');"/>
			<div id="startDateFilterDiv" class="aui-helper-hidden">
				<aui:field-wrapper>
					<liferay-ui:input-date
				           monthParam="dateMonthStart"
				           monthNullable="<%=Boolean.FALSE%>"
				           monthValue="${dateMonthStart}"
				           dayNullable="<%=Boolean.FALSE%>"
				           dayParam="dateDayStart"
				           dayValue="${dateDayStart}"
				           yearParam="dateYearStart"
				           yearNullable="<%=Boolean.FALSE%>"
				           yearValue="${dateYearStart}"
				           yearRangeStart="${yearRangeStart}"
				           yearRangeEnd="${yearRangeEnd}"
				         	cssClass="date-input"
				         	firstDayOfWeek="${firstDayOfWeek}"
				      	/>	
				</aui:field-wrapper>
			</div>
		</div>
		
		<div class="endDatePnl col-md-4">
			<aui:input helpMessage="help.course-admin.end-execution-date" name="endDateFilter" label="course-admin.end-execution-date" type="checkbox" 
				onChange="javascript:showEndDate('endDateFilterCheckbox');"/>	
		    <div id="endDateFilterDiv" class="aui-helper-hidden">
		     	<aui:field-wrapper>			
					<liferay-ui:input-date
			           monthParam="dateMonthEnd"
			           monthNullable="<%=Boolean.FALSE%>"
			           monthValue="${dateMonthEnd}"
			           dayNullable="<%=Boolean.FALSE%>"
			           dayParam="dateDayEnd"
			           dayValue="${dateDayEnd}"
			           yearParam="dateYearEnd"
			           yearNullable="<%=Boolean.FALSE%>"
			           yearValue="${dateYearEnd}"
			           yearRangeStart="${yearRangeStart}"
			           yearRangeEnd="${yearRangeEnd}"
			         	cssClass="date-input"
			         	firstDayOfWeek="${firstDayOfWeek}"
			      	/>	
				</aui:field-wrapper>
			</div>
		</div>	
					
	</div>

	<aui:button-row>
		<aui:button value="search" type="button" onClick="javascript:${namespace}searchEditions();"/>
		<aui:button value="course-admin.select-all-editions" type="button" onClick="javascript:${namespace}addAllEditions();"/>
	</aui:button-row>
		
	<liferay-ui:search-container searchContainer="${searchContainer}">
		<liferay-ui:search-container-results results="${searchContainer.results}" total="${searchContainer.total}"/>
		<div id="<portlet:namespace/>myContainer">
			<liferay-ui:search-container-row  className="com.liferay.lms.model.Course"
		     		keyProperty="courseId" modelVar="edition">
				<liferay-ui:search-container-column-text name="edition">
					${edition.getTitle(themeDisplay.locale)}
				</liferay-ui:search-container-column-text> 		
				<liferay-ui:search-container-column-text>
					<a id="<portlet:namespace />addEdition_${edition.courseId}" class="add-user" style="cursor:pointer;" 
							onClick="${namespace}addEditionCopy('<%=HtmlUtil.escapeJS(edition.getTitle(themeDisplay.getLocale())) %>','${edition.courseId}')">
						<liferay-ui:message key="select" />
						<span class="sr-only"><liferay-ui:message key="select" /> ${edition.getTitle(themeDisplay.locale)}</span>
					</a>
					<a id="<portlet:namespace />deleteEdition_${edition.courseId}" class="remove-user aui-helper-hidden" style="cursor:pointer;" 
							onClick="${namespace}deleteEditionCopy('<%=HtmlUtil.escapeJS(edition.getTitle(themeDisplay.getLocale())) %>','${edition.courseId}')" >
						<liferay-ui:message key="discard" />
						<span class="sr-only"><liferay-ui:message key="discard" /> ${edition.getTitle(themeDisplay.locale)}</span>
					</a>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>
			<liferay-ui:search-iterator />	
		</div>
	</liferay-ui:search-container>
</aui:form>	 
      		
<script type="text/javascript">
	function <portlet:namespace />addAllEditions(){
		var frm = $('#<portlet:namespace/>searchFm');
		$.ajax({
			dataType: 'json',
			url: '${addAllEditionsURL }',
		    cache:false,
		    data: frm.serialize(),
			success: function(data) {
				if(data.editions.length>0){
					$.each(data.editions, function() {		
						<portlet:namespace />addEditionCopy(this.title, this.editionId.toString());
					});		
				}
				
				onLoadActions();
			}
		});
	}

	function onLoadActions(){
		
		 AUI().use('aui-io-plugin', function(A) {
			var myContainer= A.one('#<portlet:namespace/>myContainer');
		    if (myContainer) {
		        var parent = myContainer.get('parentNode');
		
		        parent.plug(
		            A.Plugin.IO,
		            {
		                autoLoad: false
		            }
		        );
		
		        myContainer.all('a').on(
		            'click',
		            function(event) {
		            	if(event.currentTarget.get('href')!=null && event.currentTarget.get('href')!=''){
		            		var uri = event.currentTarget.get('href').replace(/p_p_state=normal/i,'p_p_state=exclusive');
		        	        parent.io.set('uri', uri);
		    	            parent.io.start();
			                event.preventDefault();
		
		            	}
		            }
		        );
		       
	
			 
				 //this applies for all select's on page, you should modify it to get only the desired ones
				 //remove default javascript action
				 
		 		myContainer.all("select").set("onchange", "");
		 
			 	
			 	myContainer.all("select").on("change", function(dropdown){
			 		var itemId = dropdown.currentTarget.get('id');
			 		var uri = "${ searchURL }";
		    	
			 		if(itemId.indexOf('itemsPerPage')>-1){
			 			uri += '&<portlet:namespace />delta='+dropdown.currentTarget.get('value');
			 		}else{
			 			uri += '&<portlet:namespace />cur='+dropdown.currentTarget.get('value');
			 		}
			 		parent.io.set('uri', uri);
		    		parent.io.start();
				}); 
		        
		    }
		 });
	
	}


	onLoadActions();
	$(<portlet:namespace />editionIds).each(function(value){
		if(<portlet:namespace />editionIds[value]!=-1){
			if(!$('#<portlet:namespace />addEdition_'+<portlet:namespace />editionIds[value]).hasClass('aui-helper-hidden')){
				$('#<portlet:namespace />addEdition_'+<portlet:namespace />editionIds[value]).addClass('aui-helper-hidden');  
			}
			if($('#<portlet:namespace />deleteEdition_'+<portlet:namespace />editionIds[value]).hasClass('aui-helper-hidden')){
				$('#<portlet:namespace />deleteEdition_'+<portlet:namespace />editionIds[value]).removeClass('aui-helper-hidden');
			}
		}else{
			$(".add-user").addClass('aui-helper-hidden');  
			$(".remove-user").removeClass('aui-helper-hidden');  
			$(".remove-user").css('pointer-events','none');
		}
	});
		
	function <portlet:namespace />searchEditions(){
		AUI( ).use( 'aui-io-request', function( A ) {
			var portletBody = A.one( '#p_p_id<portlet:namespace/> .portlet-content' );
			if( portletBody == null ) {
				portletBody = A.one( '#p_p_id<portlet:namespace/> .portlet-borderless-container' );
			}
			portletBody.setStyle( 'position', 'relative' );
			A.io.request( '${searchURL}', {
				method: 'post',
				form: { 
					id: '<portlet:namespace/>searchFm' 
				}, 
				on: { 
					success: function( ) {
						var data = this.get( 'responseData' );
						A.one( '#<portlet:namespace/>searchFieldset' ).html( data );
						
						$(<portlet:namespace />editionIds).each(function(value){
							if(<portlet:namespace />editionIds[value]!=-1){
								if(!$('#<portlet:namespace />addEdition_'+<portlet:namespace />editionIds[value]).hasClass('aui-helper-hidden')){
									$('#<portlet:namespace />addEdition_'+<portlet:namespace />editionIds[value]).addClass('aui-helper-hidden');  
								}
								if($('#<portlet:namespace />deleteEdition_'+<portlet:namespace />editionIds[value]).hasClass('aui-helper-hidden')){
									$('#<portlet:namespace />deleteEdition_'+<portlet:namespace />editionIds[value]).removeClass('aui-helper-hidden');
								}
								
							}else{
								$(".add-user").addClass('aui-helper-hidden');  
								$(".remove-user").removeClass('aui-helper-hidden');  
								$(".remove-user").css('pointer-events','none')
							}								
						});					
						
						onLoadActions();
					}
				}
			} );
		} );
	}
	
	function showStartDate(id){
		var namespace = "<portlet:namespace/>";
	 	var cb = document.getElementById(namespace+id);
		if( cb != null && cb.checked) {
	    	$('#startDateFilterDiv').removeClass('aui-helper-hidden');
		}else{
			$('#startDateFilterDiv').addClass('aui-helper-hidden');
		}
	}

	function showEndDate(id){
		var namespace = "<portlet:namespace/>";
	 	var cb = document.getElementById(namespace+id);
		if(cb != null && cb.checked) {
	    	$('#endDateFilterDiv').removeClass('aui-helper-hidden');
		}else{
			$('#endDateFilterDiv').addClass('aui-helper-hidden');
		}
	}

	showStartDate('startDateFilterCheckbox');
	showEndDate('endDateFilterCheckbox');
</script>
	