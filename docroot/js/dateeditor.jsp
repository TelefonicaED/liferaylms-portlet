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
			
			
			if($(this).hasClass('modall')){
				if(confirm(Liferay.Language.get("date-editor.confirm-act"))){
					if($(this).parent()){
						var parent = $(this).parent().parent().parent().parent().parent();
						if(parent){
							parent.find('.modallh').each(function(index) {
								var aparent =$(this);
								if(parent.parent()){
								    parent.parent().find('.castallh').each(function(index) {
								    	if($(this).val()!=aparent.val()){
								    		$(this).val(aparent.val());
								    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
								    		$(this).addClass("modif");
								    	}
								    });
								}
							});					
						}
					}
					if($(this).parent()){
						var parent = $(this).parent().parent().parent().parent().parent();
						if(parent){
							parent.find('.modallt').each(function(index) {
								var aparent =$(this);
								if(parent.parent()){
								    parent.parent().find('.castallt').each(function(index) {
								    	if($(this).val()!=aparent.val()){
								    		$(this).val(aparent.val());
								    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
								    		$(this).addClass("modif");
								    	}
								    });
								}
							});
						}
					}
				}
			}
			
			if($(this).hasClass('modalm')){
				if(confirm(Liferay.Language.get("date-editor.confirm-act"))){
					if($(this).parent()){
						var parent = $(this).parent().parent().parent().parent().parent();
						if(parent){
							parent.find('.modalmx').each(function(index) {
								var aparent =$(this);
								if(parent.parent()){
								    parent.parent().find('.castalmx').each(function(index) {
								    	if($(this).val()!=aparent.val()){
								    		$(this).val(aparent.val());
								    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
								    		$(this).addClass("modif");
								    	}
								    });
								}
							});						
						}
					}
					if($(this).parent()){
						var parent = $(this).parent().parent().parent().parent().parent();
						if(parent){
							parent.find('.modalmy').each(function(index) {
								var aparent =$(this);
								if(parent.parent()){
								    parent.parent().find('.castalmy').each(function(index) {
								    	if($(this).val()!=aparent.val()){
								    		$(this).val(aparent.val());
								    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
								    		$(this).addClass("modif");
								    	}
								    });
								}
							});
						}
					}
				}
			}
			
			/*Nuevo codigo para nuevos campos de fecha*/		
			if($(this).hasClass('coursel')){
				if(confirm(Liferay.Language.get("date-editor.confirm-all"))){				
					if($(this).hasClass('courselh')){
						var aparent =$(this);
						$('.castallh').each(function(index) {
					    	console.log("Se modifica castallh");
					    	if($(this).val()!=aparent.val()){
					    		$(this).val(aparent.val());
					    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
					    		$(this).addClass("modif");
					    	}
					    });
					    
					    $('.modallh').each(function(index) {
					    	console.log("Se modifica modallh");
					    	if($(this).val()!=aparent.val()){
					    		$(this).val(aparent.val());
					    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
					    		$(this).addClass("modif");
					    	}
					    });
					}
					if($(this).hasClass('courselt')){
						var aparent =$(this);
						$('.castallt').each(function(index) {
					    	console.log("Se modifica castallt");
					    	if($(this).val()!=aparent.val()){
					    		$(this).val(aparent.val());
					    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
					    		$(this).addClass("modif");
					    	}
					    });
					    
					    $('.modallt').each(function(index) {
					    	console.log("Se modifica modallt");
					    	if($(this).val()!=aparent.val()){
					    		$(this).val(aparent.val());
					    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
					    		$(this).addClass("modif");
					    	}
					    });
					}
				}
			}
			
			if($(this).hasClass('coursem')){
				if(confirm(Liferay.Language.get("date-editor.confirm-all"))){				
					if($(this).hasClass('coursemx')){
						var aparent =$(this);
						$('.castalmx').each(function(index) {
					    	console.log("Se modifica castalmx");
					    	if($(this).val()!=aparent.val()){
					    		$(this).val(aparent.val());
					    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
					    		$(this).addClass("modif");
					    	}
					    });
					    
					    $('.modalmx').each(function(index) {
					    	console.log("Se modifica modalmx");
					    	if($(this).val()!=aparent.val()){
					    		$(this).val(aparent.val());
					    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
					    		$(this).addClass("modif");
					    	}
					    });
					}
					if($(this).hasClass('coursemy')){
						var aparent =$(this);
						$('.castalmy').each(function(index) {
					    	console.log("Se modifica castalmy");
					    	if($(this).val()!=aparent.val()){
					    		$(this).val(aparent.val());
					    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
					    		$(this).addClass("modif");
					    	}
					    });
					    
					    $('.modalmy').each(function(index) {
					    	console.log("Se modifica modalmy");
					    	if($(this).val()!=aparent.val()){
					    		$(this).val(aparent.val());
					    		$(this).css("background","none repeat scroll 0 0 #BCF5A9");
					    		$(this).addClass("modif");
					    	}
					    });
					}
				}
			}	
		});
	}
</script>