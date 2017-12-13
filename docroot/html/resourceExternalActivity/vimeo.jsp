<script src="/liferaylms-portlet/js/froogaloop.min.js"></script>

<script>
	document.addEventListener('DOMContentLoaded', function() { 		
		var iframe = document.getElementById('player_1');
		var player = $f(iframe);
		var plays = parseInt('${plays}');
		var duration = 0;
		var currentTime = 0;
		var seekTo = parseInt('${seekTo}');
		var finished = false;
		console.log("plays: " + plays);
		
		player.addEvent('ready', function() {
			console.log("ready ");
			player.api('getDuration', function(dur) {
				duration = dur;
			});		
			
		    player.addEvent('pause', onPause);
			player.addEvent('finish', onFinish);
			player.addEvent('play', onPlay);
			if (seekTo > 0)
				player.api('seekTo', seekTo);
			
		});
		
		function onPause() {
			
		}
		   
		function onPlay() {
			finished = false;
			plays++;
		}	
		
		function onFinish() {	
			console.log("finish vimeo");
			
			var serviceParameterTypes = [
			     	'long',
			     	'int',
			     	'double',
			    	'int'
			];
			
			var message = Liferay.Service.Lms.LearningActivityTry.update(
				{
					latId: '${latId}',
					score: 100,
					position: duration,
					plays: plays,
					serviceParameterTypes: JSON.stringify(serviceParameterTypes)
				}
			);
			      	
			var exception = message.exception;
			            
			if (!exception) {
				// Process Success - A LearningActivityResult returned
				finished = true;	
				Liferay.Portlet.refresh('#p_p_id_activityNavigator_WAR_liferaylmsportlet_');
				Liferay.Portlet.refresh('#p_p_id_lmsactivitieslist_WAR_liferaylmsportlet_');
				var src = 	document.getElementById("player_1").src;
				var index = src.indexOf("background");
				if(index > 0){
					src = src.substring(0,index-1);
					document.getElementById("player_1").src = src;
				}
			}									
		}
		
		var unloadEvent = function (e) {
			console.log("unload event vimeo");  
			if(!finished){
				player.api('getCurrentTime', function(time) {
					currentTime = time;
						
					var isDefaultScore = '${isDefaultScore}' == 'true';
					var positionToSave = parseFloat('${videoPosition}');
					var oldScore = parseInt('${oldScore}');
					if (currentTime > positionToSave)
						positionToSave = currentTime;
					var score = 100;														
					if (!isDefaultScore) score = Math.round((currentTime/duration)*100);
					//debugger;
					var serviceParameterTypes = [
				     	'long',
				     	'int',
				     	'double',
				    	'int'
				    ];
					var message = Liferay.Service.Lms.LearningActivityTry.update(
						{
							latId: '${latId}',
							score: score,
							position: positionToSave,
							plays: plays,
							serviceParameterTypes: JSON.stringify(serviceParameterTypes)
						}
					);
					
					var exception = message.exception;
							
					if (!exception) {
						// Process Success - A LearningActivityResult returned
					}														
				});	
			  
			}
		};
		
		window.addEventListener("beforeunload", unloadEvent);										
	
	});

</script>					
							