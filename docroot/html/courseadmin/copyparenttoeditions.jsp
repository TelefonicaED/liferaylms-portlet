<%@include file="/init-min.jsp" %>

<liferay-ui:header title="${course.getTitle(themeDisplay.locale) }" backURL="${backURL}" localizeTitle="false"/>

<liferay-ui:panel-container extended="false"  persistState="false">
   	
   	<aui:form action="${copyEditionsURL }" name="copyEditionsFm" method="post" >	
	   	
	   	<liferay-ui:panel title="course-admin.copy-parent.record" collapsible="true" defaultState="closed" >
	   		<aui:input name="description" type="checkbox" label="description"/>
	   		<aui:input name="summary" type="checkbox" label="summary"/>
	   		<aui:input name="courseEvalId" type="checkbox" label="course-correction-method"/>
	   		<aui:input name="calificationType" type="checkbox" label="calificationType"/>
	   		<aui:input name="registrationType" type="checkbox" label="registration-type"/>
	   		<c:forEach items="${columns }" var="column">
	   			<aui:input name="expando_${column.columnId }" type="checkbox" label="${column.name }"/>
	   		</c:forEach>
		</liferay-ui:panel>
		<liferay-ui:panel title="course-admin.copy-parent.activities" collapsible="true" defaultState="closed" >
	   	  	<aui:input name="activities" type="checkbox" label="activities"/>
	   	  	<c:forEach items="${modules }" var="module">
	   	  		<h4>${module.getTitle(themeDisplay.locale) }</h4>
	   	  		<ul>
	   	  			<c:set var="activities" value="${module.listLearningActivities }" />
	   	  			<c:forEach items="${activities }" var="activity">
	   	  				<li>${activity.getTitle(themeDisplay.locale) }</li>
	   	  			</c:forEach>
	   	  		</ul>
	   	  	</c:forEach>
		</liferay-ui:panel>
		
		<aui:input type="hidden" name="editionsTitle" id="editionsTitle" value="" />
		<aui:input type="hidden" name="editionIds" id="editionIds" value="" />
	</aui:form>
	<liferay-ui:panel title="course-admin.copy-parent.editions" collapsible="true" defaultState="closed" >
		<aui:fieldset id="searchFieldset"> 
			<%@include file="/html/courseadmin/searcheditions.jsp" %>
		</aui:fieldset>
		
		<div class="to">
			<liferay-ui:message key="courseadmin.copy-editions-selected" />
			<div class="aui-helper-clearfix aui-textboxlistentry-holder" id="<portlet:namespace />selected_editions_copy" ><liferay-ui:message key="there-are-no-editions"/></div>
		</div>	   	  
	</liferay-ui:panel>
</liferay-ui:panel-container>
<script>
	var <portlet:namespace />selectedEditionsTitle =[];
	var <portlet:namespace />editionIds = [];
	var recievedTitle = '${copyEditions}'.split(";");
	var recievedCourseIds = '${editionIds}'.split(",");
</script>
	
<aui:button-row>
	<aui:button type="button" name="copyParentToEditions" value="copy" onClick="javascript:${renderResponse.namespace}copyEditions()"/>
</aui:button-row>

		 
<script type="text/javascript">
	
	function <portlet:namespace />copyEditions(){
		if($('#<portlet:namespace />editionIds').val() != ''){
			$('#<portlet:namespace />copyEditionsFm').submit();
		}else{
			alert(Liferay.Language.get("courseadmin.copy-editions.not-editions-selected"));
		}
	}
	
	$(recievedTitle).each(function(value){
		if(recievedTitle[value]!=null && recievedTitle[value]!=''){
			<portlet:namespace />addEditionCopy(recievedTitle[value],recievedCourseIds[value]);
		}		
	});
	
	function <portlet:namespace />addEditionCopy(titleEdition, courseId){
		
		var existingEdition=<portlet:namespace />editionIds.indexOf(courseId);
		if(existingEdition<0){
			<portlet:namespace />selectedEditionsTitle.push(titleEdition);
			<portlet:namespace />editionIds.push(courseId);
			var selectedEdition = '';
			var editionsTitle='';
			$(<portlet:namespace />selectedEditionsTitle).each(function(value){
				selectedEdition+='<li class="yui3-widget aui-component aui-textboxlistentry aui-textboxlistentry-focused" tabindex="0"><span class="aui-textboxlistentry-content"><span class="aui-textboxlistentry-text">';
				selectedEdition+=<portlet:namespace />selectedEditionsTitle[value];
				selectedEdition+= '<span class="aui-icon aui-icon-close aui-textboxlistentry-close" onClick="<portlet:namespace />deleteEditionCopy(\'';
				selectedEdition+=<portlet:namespace />selectedEditionsTitle[value] + '\',\''+<portlet:namespace />editionIds[value]+'\');" /></span></span></li>';
				editionsTitle += <portlet:namespace />selectedEditionsTitle[value]+';';
			});
			$('#<portlet:namespace />selected_editions_copy').html(selectedEdition);
			$('#<portlet:namespace />editionsTitle').val(editionsTitle.slice(0, -1));		
			$('#<portlet:namespace />editionIds').val(<portlet:namespace />editionIds.slice());
		
			
			if(courseId>-1){
				if(!$('#<portlet:namespace />addEdition_'+courseId).hasClass('aui-helper-hidden')){
					$('#<portlet:namespace />addEdition_'+courseId).addClass('aui-helper-hidden'); 
				}
				if($('#<portlet:namespace />deleteEdition_'+courseId).hasClass('aui-helper-hidden')){
					$('#<portlet:namespace />deleteEdition_'+courseId).removeClass('aui-helper-hidden'); 
				}	
			}else{
				$(".add-user").addClass('aui-helper-hidden');  
				$(".remove-user").removeClass('aui-helper-hidden');  
				$(".remove-user").css('pointer-events','none');
			}
			
		}
		 var action = document.getElementById("<portlet:namespace/>copyParentToEditions"); 
         if(<portlet:namespace/>selectedEditionsTitle.length>0){
			  action.removeAttribute("disabled");
			  $('#<portlet:namespace/>copyParentToEditions').parent().parent().removeClass('aui-button-disabled');
		 }else{
			  action.setAttribute("disabled", true);
			  $('#<portlet:namespace/>copyParentToEditions').parent().parent().addClass('aui-button-disabled');
		  }


	}
	
	function <portlet:namespace />deleteEditionCopy(titleEdition, courseId){
		
		var existingEdition=<portlet:namespace />editionIds.indexOf(courseId);
		if(existingEdition>-1){
			<portlet:namespace />selectedEditionsTitle.splice(existingEdition,1);
			<portlet:namespace />editionIds.splice(existingEdition,1);
			var selectedEdition = '';
			var editionsTitle='';
			$(<portlet:namespace />selectedEditionsTitle).each(function(value){
				selectedEdition+='<li class="yui3-widget aui-component aui-textboxlistentry aui-textboxlistentry-focused" tabindex="0"><span class="aui-textboxlistentry-content"><span class="aui-textboxlistentry-text">';
				selectedEdition+=<portlet:namespace />selectedEditionsTitle[value];
				selectedEdition+= '<span class="aui-icon aui-icon-close aui-textboxlistentry-close" onClick="<portlet:namespace />deleteEditionCopy(\'';
				selectedEdition+=<portlet:namespace />selectedEditionsTitle[value] + '\',\''+<portlet:namespace />editionIds[value]+'\');" /></span></span></li>';
				editionsTitle += <portlet:namespace />selectedEditionsTitle[value]+';';
			});
			$('#<portlet:namespace />selected_editions_copy').html(selectedEdition);
			$('#<portlet:namespace />editionsTitle').val(editionsTitle.slice(0, -1));		
			$('#<portlet:namespace />editionIds').val(<portlet:namespace />editionIds.slice());
			
			if(!$('#<portlet:namespace />deleteEdition_'+courseId).hasClass('aui-helper-hidden')){
				$('#<portlet:namespace />deleteEdition_'+courseId).addClass('aui-helper-hidden'); 
			}
			if($('#<portlet:namespace />addEdition_'+courseId).hasClass('aui-helper-hidden')){
				$('#<portlet:namespace />addEdition_'+courseId).removeClass('aui-helper-hidden'); 
			}	
			
			if(courseId==-1){
				$(".add-user").removeClass('aui-helper-hidden');  
				$(".remove-user").addClass('aui-helper-hidden');  
				$(".remove-user").removeAttr('style');
			}
		}
		
		 var action = document.getElementById("<portlet:namespace/>copyParentToEditions"); 
         if(<portlet:namespace/>selectedEditionsTitle.length>0){
			  action.removeAttribute("disabled");
			  $('#<portlet:namespace/>copyParentToEditions').parent().parent().removeClass('aui-button-disabled');
		 }else{
			  action.setAttribute("disabled", true);
			  $('#<portlet:namespace/>copyParentToEditions').parent().parent().addClass('aui-button-disabled');
		  }
	}

	
	
	
	Liferay.provide(
	        window,
	        '<portlet:namespace />closePopup',
	        function(popupIdToClose) {

	            var A = AUI();

	            A.DialogManager.closeByChild('#' + popupIdToClose);
	        },
	        ['aui-base','aui-dialog','aui-dialog-iframe']
	    );

	
</script>