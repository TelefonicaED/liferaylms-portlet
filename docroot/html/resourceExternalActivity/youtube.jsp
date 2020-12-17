<script>
  // 2. This code loads the IFrame Player API code asynchronously.
  var plays = parseInt('${plays}');
  var tag = document.createElement('script');
  var finished = false;

  tag.src = "https://www.youtube.com/iframe_api";
  var firstScriptTag = document.getElementsByTagName('script')[0];
  firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

  // 3. This function creates an <iframe> (and YouTube player)
  //    after the API code downloads.
  var player = null;
  function onYouTubeIframeAPIReady() {
	player = new YT.Player('youtube-video', {
	  events: {
		'onReady': onPlayerReady,
		'onStateChange': onPlayerStateChange
	  }
	});
  }

  // 4. The API will call this function when the video player is ready.
  function onPlayerReady(event) {
  }

  // 5. The API calls this function when the player's state changes.
  //    The function indicates that when playing a video (state=1),
  //    the player should play for six seconds and then stop.
  var done = false;
  function onPlayerStateChange(event) {
	if (event.data == YT.PlayerState.PLAYING && !done) {
		plays ++;
		done=true;
		
	}
	if (event.data == YT.PlayerState.PAUSED){
		done=false;
	}
	if (event.data == YT.PlayerState.ENDED){
		console.log("youtube end");
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
	   			position: event.target.getDuration(),
	   			plays: plays,
	   			serviceParameterTypes: JSON.stringify(serviceParameterTypes)
	    	}
	    );
	      	
	    var exception = message.exception;
	            
		if (!exception) {
			// Process Success - A LearningActivityResult returned
			finished = true;
			setTimeout(function(){ Liferay.Portlet.refresh('#p_p_id_activityNavigator_WAR_liferaylmsportlet_'); }, 1000);
			setTimeout(function(){Liferay.Portlet.refresh('#p_p_id_lmsactivitieslist_WAR_liferaylmsportlet_'); }, 1000);
		}						
	}
  }
  
  //Añadimos los puntos donde se cargaran las preguntas
</script>

<c:forEach items="${timeQuestions }" var="timeQuestion">
	<script>
		
	</script>
</c:forEach>

<script >

  var unloadEvent = function (e) {
	  if(!finished){
		console.log("unloadEvent youtube");
		var isDefaultScore = '${isDefaultScore}' == 'true';
		var positionToSave = parseFloat('${videoPosition}');
		var oldScore = parseInt('${oldScore}');
		var score = 100;

		var currentTime = player.getCurrentTime();
		if (currentTime > positionToSave)
			positionToSave = currentTime;
		var duration = player.getDuration();	
		if (!isDefaultScore) score = Math.round((currentTime/duration)*100);

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
	  }	
  };
  window.addEventListener("beforeunload", unloadEvent);	
 </script>