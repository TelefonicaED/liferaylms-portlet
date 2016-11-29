<liferay-portlet:actionURL name="moveActivity" var="moveActivityURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString()%>" />	
	
<script type="text/javascript">

	Liferay.provide(
	        window,
	        '<portlet:namespace />refreshPortlet',
	        function() {
			     <%-- refreshing the portlet [Liferay.Util.getOpener().] --%>
	            var curPortletBoundaryId = '#p_p_id<portlet:namespace />';
	
	            Liferay.Portlet.refresh(curPortletBoundaryId);
	        },
	        ['aui-dialog','aui-dialog-iframe']
	    );
	    
	    
	var ismobile=navigator.userAgent.match(/(iPad)|(iPhone)|(iPod)|(android)|(webOS)/i);
	
	if(!ismobile){
	AUI().ready('node','aui-io-request','aui-parse-content','aui-sortable',function(A) {
	
		new A.Sortable(
			{
				container: A.one('#myActivities'),
			    nodes: 'li',
	            after: {   
	            	'drag:end': function(event){ 
	            		
					    var node = event.target.get('node'),
				            prev = node.previous(),
				            next = node.next(),
	                        movedPageId = parseInt(node.get('id').substr(<%=renderResponse.getNamespace().length() %>),0),
			            	prevPageId = 0,
			            	nextPageId = 0;
					    
				        if(prev){
				          prevPageId = parseInt(prev.get('id').substr(<%=renderResponse.getNamespace().length() %>),0);
					    }
	
				        if(next){
				          nextPageId = parseInt(next.get('id').substr(<%=renderResponse.getNamespace().length() %>),0);
					    }
	
						A.io.request('${moveActivityURL}', {  
							data: {
					            <portlet:namespace />pageId: movedPageId,
					            <portlet:namespace />prevPageId: prevPageId,
					            <portlet:namespace />nextPageId: nextPageId
					        },
						    dataType : 'html', 
						  on: {  
					  		success: function() {  
								 Liferay.Portlet.refresh(A.one('#p_p_id<portlet:namespace />'),{'p_t_lifecycle':0,'<%=renderResponse.getNamespace()+WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>':'<%=StringPool.TRUE %>'});
					        }  
						   }  
						});    
	            	}              
	            }
			}
		); 
	  });
	}
</script>
		
<liferay-portlet:resourceURL var="baseChangeURL"/>

<script>

	function <portlet:namespace />downActivity(actId){
		$.ajax({
			dataType: 'json',
			url:'${baseChangeURL}',
		    cache:false,
			data: {
				actId: actId,
				action: 'down'
			},
			success: function(data){
				if(data){	
					$item =  $("#<portlet:namespace />" + actId);
					$after = $item.next();
					if($after.prop("tagName").toUpperCase()=="LI"){
						$item.hide().insertAfter($after).fadeIn("slow");
					}			    
				}
			},
			error: function(){
	
			}
		});  
	 }
	
	
	function <portlet:namespace />upActivity(actId){
		$.ajax({
			dataType: 'json',
			url:'${baseChangeURL}',
		    cache:false,
			data: {
				actId: actId,
				action: 'up'
			},
			success: function(data){
				if(data){	
					$item =  $("#<portlet:namespace />" + actId);
					$before = $item.prev();
					if($before.prop("tagName").toUpperCase()=="LI"){
						$item.hide().insertBefore($before).fadeIn("slow");
					}			    
				}
			},
			error: function(){
	
			}
		});  
	 }


</script>
	