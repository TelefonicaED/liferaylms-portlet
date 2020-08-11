<style>
.module-date-editor{
	background-color: #eaeff2;
}
input:disabled {
	opacity: 0.5;
}
</style>

<script>
	window.onload=function(){
		
		$(".itime" ).datepicker();
		
		$(".itime, .ihour").change(function(event) {
			
			$(this).css("background","none repeat scroll 0 0 #F5A9A9");
			
			if($(this).hasClass('ihour')){
				var hour = /^([0-9]{1,2}):([0-9]{1,2})$/; 			
				var validacion = $(this).val();
				var n=validacion.split(":");
				if(($(this).val().match(hour)) && ((parseInt(0) <= parseInt(n[0]))&& (parseInt(n[0]) <= parseInt(23))) && ((parseInt(0) <= parseInt(n[0]))&& (parseInt(n[1]) <= parseInt(59)))){
			    	$(this).css("background","none repeat scroll 0 0 #BCF5A9");
			    	$(this).addClass("modif");
				}else{
					confirm(Liferay.Language.get("date-editor.format-error"));
					$(this).css("background","none repeat scroll 0 0 #F5A9A9");
					return;
				}
			}else if($(this).hasClass('itime')){
				var time = /^([0-9]{1,2})\/([0-9]{1,2})\/([0-9]{4})$/;
				if(time.test($(this).val())){
		    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
		    		$(this).addClass("modif");
				}else{
					$(this).css("background","none repeat scroll 0 0 #F5A9A9");
					return;
				}
			}
			
			/* Fecha de fin de módulo */
			if($(this).hasClass('modall')){
				var checkDate = false;
				var moduleId = $(this).prop("id").substring(4);
				$("input[id^='<portlet:namespace />endDateActivityEnabled_" + moduleId + "_'][id$='_']").each(
						function(){
							checkDate = checkDate || $(this).val() == "true";
						}
				);
				console.log("checkDate: " + checkDate);
				
				if(checkDate == true){ 
					if(confirm(Liferay.Language.get("date-editor.confirm-act"))){
						<portlet:namespace />changeActivities($(this), false);
					}else{
						<portlet:namespace />changeActivities($(this), true);
					}
				}else{
					<portlet:namespace />changeActivities($(this), false);
				}
			}
			
			/* Fecha de inicio de módulo */
			if($(this).hasClass('modalm')){
				var checkDate = false;
				var moduleId = $(this).prop("id").substring(4);
				$("input[id^='<portlet:namespace />startDateActivityEnabled_" + moduleId + "_'][id$='_']").each(function(){checkDate = checkDate || $(this).val() == "true";});
				console.log("checkDate: " + checkDate);
				
				if(checkDate == true){ 
					if(confirm(Liferay.Language.get("date-editor.confirm-act"))){
						<portlet:namespace />changeActivities($(this), false);
					}else{
						<portlet:namespace />changeActivities($(this), true);
					}
				}else{
					<portlet:namespace />changeActivities($(this), false);
				}
			}
			
			var aparent =$(this);
			
			/* Preguntamos si se quieren cambiar las fechas de por debajo cuando se cambia la fecha de fin de ejecución del curso */		
			if($(this).hasClass('coursel')){
				console.log("cambiamos las fechas de fin de modulos y actividades");
				var checkDate = false;
				$("input[id^='<portlet:namespace />endDateEnabled'][id$='_']").each(function(){checkDate = checkDate || $(this).val() == "true";});
				$("input[id^='<portlet:namespace />endDateActivityEnabled_'][id$='_']").each(function(){checkDate = checkDate || $(this).val() == "true";});
				
				console.log("checkDate: " + checkDate);
				if(checkDate == true){ 
					if(confirm(Liferay.Language.get("date-editor.confirm-all"))){				
						if($(this).hasClass('courselh')){
							<portlet:namespace />changeDate(aparent, false, "modallh", "castallh", false, false, true, false);
						}
						if($(this).hasClass('courselt')){
							<portlet:namespace />changeDate(aparent, false, "modallt", "castallt", false, false, false, true);
						}
					}else{
						if($(this).hasClass('courselh')){
							<portlet:namespace />changeDate(aparent, true, "modallh", "castallh", false, false, true, false);
						}
						if($(this).hasClass('courselt')){
							<portlet:namespace />changeDate(aparent, true, "modallt", "castallt", false, false, false, true);
						}
					}
				}else{
					//No hay ninguno que esté chequeado, cambiamos todos
					if($(this).hasClass('courselh')){
						<portlet:namespace />changeDate(aparent, false, "modallh", "castallh", false, false, true, false);
					}
					if($(this).hasClass('courselt')){
						<portlet:namespace />changeDate(aparent, false, "modallt", "castallt", false, false, false, true);
					}
				}
			}
			
			/* Preguntamos si se quieren cambiar las fechas de por debajo cuando se cambia la fecha de inicio de ejecución del curso */
			if($(this).hasClass('coursem')){
				console.log("cambiamos las fechas de inicio de modulos y actividades");
				var checkDate = false;
				$("input[id^='<portlet:namespace />startDateEnabled'][id$='_']").each(function(){checkDate = checkDate || $(this).val() == "true";});
				$("input[id^='<portlet:namespace />startDateActivityEnabled_'][id$='_']").each(function(){checkDate = checkDate || $(this).val() == "true";});
				console.log("checkDate: " + checkDate);
				if(checkDate == true){ 
					if(confirm(Liferay.Language.get("date-editor.confirm-all"))){				
						if($(this).hasClass('coursemx')){
							<portlet:namespace />changeDate(aparent, false, "modalmx", "castalmx", true, false, false, false);
						}
						if($(this).hasClass('coursemy')){
							<portlet:namespace />changeDate(aparent, false, "modalmy", "castalmy", false, true, false, false);
						}
					}else{
						if($(this).hasClass('coursemx')){
							<portlet:namespace />changeDate(aparent, true, "modalmx", "castalmx", true, false, false, false);
						}
						if($(this).hasClass('coursemy')){
							<portlet:namespace />changeDate(aparent, true, "modalmy", "castalmy", false, true, false, false);
						}
					}
				}else{
					//No hay ninguno que esté chequeado, cambiamos todos
					if($(this).hasClass('coursemx')){
						<portlet:namespace />changeDate(aparent, false, "modalmx", "castalmx", true, false, false, false);
					}
					if($(this).hasClass('coursemy')){
						<portlet:namespace />changeDate(aparent, false, "modalmy", "castalmy", false, true, false, false);
					}
				}
			}	
		});
	}
	
	function <portlet:namespace />changeDate(aparent, onlyChecked, moduleClass, activityClass, isStartDate, isStartHour, isEndDate, isEndHour){
		
		console.log("onlyChecked: " + onlyChecked);
		console.log("moduleClass: " + moduleClass);
		console.log("activityClass: " + activityClass);
		console.log("isStartDate: " + isStartDate);
		console.log("isStartHour: " + isStartHour);
		console.log("isEndDate: " + isEndDate);
		console.log("isEndHour: " + isEndHour);
		
		var nameCheck = "";
		var nameCheckActivity = "";
		
		if(isStartDate){
			nameCheck = "startDateEnabled";
			nameCheckActivity = "startDateActivityEnabled";
		}else if(isEndDate){
			nameCheck = "endDateEnabled";
			nameCheckActivity = "endDateActivityEnabled";
		}else if(isStartHour){
			nameCheck = "startDateEnabled";
			nameCheckActivity = "startDateActivityEnabled";
		}else if(isEndHour){
			nameCheck = "endDateEnabled";
			nameCheckActivity = "endDateActivityEnabled";
		}
	    
		console.log("nameCheck: " + nameCheck);
		
	    $('.' + moduleClass).each(function() {
	    	console.log("Se modifica modalmx");
	    	var moduleId =  $(this).prop("id").substring(4); 
	    	   	
	    	if(!onlyChecked || $('#<portlet:namespace />' + nameCheck + moduleId + '_').val() == "false"){
	    		console.log("modificamos la fecha del modulo");
	    		
	    		if($(this).val()!=aparent.val()){
		    		$(this).val(aparent.val());
		    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
		    		$(this).addClass("modif");
		    	}
	    		
	    		$('.' + activityClass).each(function() {
	    	    	console.log("Se modifica castalmx");
	    	    	
	    	    	var actId =  $(this).prop("id").substring(4); 
	    	    	
	    	    	if(!onlyChecked || $('#<portlet:namespace />' + nameCheckActivity + "_" + moduleId + "_" + actId + '_').val() == "false"){
	    	    	
	    		    	 if($(this).val()!=aparent.val()){
	    		    		$(this).val(aparent.val());
	    		    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
	    		    		$(this).addClass("modif");
	    		    	}
	    	    	}
	    	    });
	    	}
	    });
	}
	
	function <portlet:namespace />changeActivities(node, onlyChecked){
		if(node.parent()){
			var moduleId = node.prop("id").substring(4);
			var parent = node.parent().parent().parent().parent().parent();
			if(parent){
				if(node.hasClass("modalmx")) {
					var aparent = node;
					parent.parent().parent().parent().parent().find('.castalmx').each(function(index) {
				    	var actId = $(this).prop("id").substring(4);
				    	console.log("actId: " + actId);
						if(!onlyChecked || $('#<portlet:namespace />startDateActivityEnabled_' + moduleId + "_" + actId + '_').val() == "false"){
							if($(this).val()!=aparent.val()){
								$(this).val(aparent.val());
								$(this).css("background","none repeat scroll 0 0 #BCF5A9");
								$(this).addClass("modif");
							}
						}
				    });
				}					
				if(node.hasClass("modalmy")) {
					var aparent =node;
					parent.parent().parent().parent().parent().find('.castalmy').each(function(index) {
				    	var actId = $(this).prop("id").substring(4);
				    	console.log("actId: " + actId);
						if(!onlyChecked || $('#<portlet:namespace />startDateActivityEnabled_' + moduleId + "_" + actId + '_').val() == "false"){
							if($(this).val()!=aparent.val()){
								$(this).val(aparent.val());
								$(this).css("background","none repeat scroll 0 0 #BCF5A9");
								$(this).addClass("modif");
							}
						}
				    });
				}
				if(node.hasClass("modallh")) {
					var aparent =node;
					parent.parent().parent().parent().parent().find('.castallh').each(function(index) {
						var actId = $(this).prop("id").substring(4);
						console.log("actId: " + actId);
						if(!onlyChecked || $('#<portlet:namespace />endDateActivityEnabled_' + moduleId + "_" + actId + '_').val() == "false"){
							if($(this).val()!=aparent.val()){
								$(this).val(aparent.val());
								$(this).css("background","none repeat scroll 0 0 #BCF5A9");
								$(this).addClass("modif");
							}
						}
					});
				}					
				if(node.hasClass("modallt")) {
					var aparent =node;
					parent.parent().parent().parent().parent().find('.castallt').each(function(index) {
						var actId = $(this).prop("id").substring(4);
						console.log("actId: " + actId);
						if(!onlyChecked || $('#<portlet:namespace />endDateActivityEnabled_' + moduleId + "_" + actId + '_').val() == "false"){
							if($(this).val()!=aparent.val()){
								$(this).val(aparent.val());
								$(this).css("background","none repeat scroll 0 0 #BCF5A9");
								$(this).addClass("modif");
							}
						}
					});
				}
			}
		}
	}
	
	function <portlet:namespace />setStartDateState(moduleId){
	   	var enabled = document.getElementById('<portlet:namespace />startDateEnabled' + moduleId + '_Checkbox').checked; 
  		if(enabled) {
  			$("#fmin" + moduleId).prop("disabled", false);
  			$("#tmin" + moduleId).prop("disabled", false);
  		}else {
  			$("#fmin" + moduleId).prop("disabled", true);
  			$("#tmin" + moduleId).prop("disabled", true);
  		}
	}
	function <portlet:namespace />setEndDateState(moduleId){
	   	var enabled = document.getElementById('<portlet:namespace />endDateEnabled' + moduleId + '_Checkbox').checked; 
  		if(enabled) {
  			$("#fmou" + moduleId).prop("disabled", false);
  			$("#tmou" + moduleId).prop("disabled", false);
  		}else {
  			$("#fmou" + moduleId).prop("disabled", true);
  			$("#tmou" + moduleId).prop("disabled", true);
  		}
	}
	
	function <portlet:namespace />setStartDateActState(moduleId,actId){
	   	var enabled = document.getElementById('<portlet:namespace />startDateActivityEnabled_' + moduleId + "_" + actId + '_Checkbox').checked; 
  		if(enabled) {
  			$("#fain" + actId).prop("disabled", false);
  			$("#tain" + actId).prop("disabled", false);
  		}else {
  			$("#fain" + actId).prop("disabled", true);
  			$("#tain" + actId).prop("disabled", true);
  		}
	}
	function <portlet:namespace />setEndDateActState(moduleId, actId){
	   	var enabled = document.getElementById('<portlet:namespace />endDateActivityEnabled_' + moduleId + "_" + actId + '_Checkbox').checked; 
  		if(enabled) {
  			$("#faou" + actId).prop("disabled", false);
  			$("#taou" + actId).prop("disabled", false);
  		}else {
  			$("#faou" + actId).prop("disabled", true);
  			$("#taou" + actId).prop("disabled", true);
  		}
	}
</script>