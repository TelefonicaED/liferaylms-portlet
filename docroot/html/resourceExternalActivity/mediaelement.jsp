<div class="contentQuestionVideo">
	<div class="video">
		<video width="600" height="338" id="playervideo" ${controls } preload="none" src="${video}"  type="${mimeType }"></video>
	</div>
	
	<c:forEach items="${listQuestions }" var="question">
		<c:set var="questionType" value="${question.testQuestionType }" />
		<div class="aui-helper-hidden questionVideo" id="${renderResponse.namespace}question_${question.questionId}">	
			<aui:form name="questionform_${question.questionId}">
				<aui:input name="questionId" value="${question.questionId }" type="hidden"/>
				<aui:input name="latId" value="${latId}" type="hidden"/>
				${questionType.getHtmlView(question.questionId, themeDisplay, null) }
				<aui:button value="save" onClick="javascript:${renderResponse.namespace}answerQuestion(${question.questionId })" />
			</aui:form>
		</div>
	</c:forEach>
</div>

<%@ include file="/html/questions/validations.jsp" %>

 <!-- JS -->
 <script src="https://cdn.jsdelivr.net/npm/mediaelement@4.2.7/build/mediaelement-and-player.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/mediaelement@4.2.7/build/renderers/dailymotion.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/mediaelement@4.2.7/build/renderers/facebook.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/mediaelement@4.2.7/build/renderers/soundcloud.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/mediaelement@4.2.7/build/renderers/twitch.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/mediaelement@4.2.7/build/renderers/vimeo.min.js"></script>
 
 <portlet:resourceURL var="saveQuestionURL" id="saveQuestion"/>
 
 <script>
 	
 	var player;
 
     document.addEventListener('DOMContentLoaded', function() {
    	 
    	 
    	var answerQuestion = true;
  	 	var plays = 0;
  	 	var finished = false;

	 	player = new MediaElement("playervideo", {
	    	pluginPath: 'https://cdn.jsdelivr.net/npm/mediaelement@4.2.7/build/',
	        shimScriptAccess: 'always',
	        success: function (media, node) {

	        }
	    });
	
	    var currentTime = parseInt('${currentTime}');
		if(currentTime > 0){
			player.setCurrentTime(currentTime);
		}
		
		player.addEventListener('play', function () {
			finished = false;
			if(plays > 0){
				$('[id*^=<portlet:namespace/>question_]').addClass("aui-helper-hidden");
			}
			plays++;
		});	
			
		player.addEventListener('ended',function() {
			
			var duration = player.getDuration();
			
			<portlet:namespace/>finishTry(100,duration,plays);	

			// Process Success - A LearningActivityResult returned
			finished = true;	
			Liferay.Portlet.refresh('#p_p_id_activityNavigator_WAR_liferaylmsportlet_');
			Liferay.Portlet.refresh('#p_p_id_lmsactivitieslist_WAR_liferaylmsportlet_');
			player.setControls(true);
			if('${isVimeoIframe}' == 'true'){
				var src = 	document.getElementById("playervideo_vimeo_iframe").src;
				var index = src.indexOf("background");
				if(index > 0){
					src = src.substring(0,index-1);
					document.getElementById("playervideo_vimeo_iframe").src = src;
				}
			}								
		});
		
		//Creamos el array para las preguntas
		var questions = [];
		<c:forEach items="${timeQuestions }" var="question">
			var question = ["${question.key}","${question.value}"];
			if(parseInt("${question.value}") >= currentTime){
				questions.push(question);
			}
			
		</c:forEach>
		
		questions.sort(function(a, b){return a[1]-b[1]});
		
		if(questions.length > 0){
			var indexQuestion = 0;
			var maxQuestions = questions.length;
			var nextQuestion = questions[indexQuestion];
			
			player.addEventListener('timeupdate', function() {
				console.log("timeupdate");
				if(indexQuestion < maxQuestions && nextQuestion[1] < player.currentTime && (nextQuestion[1] > (player.currentTime - 2))){
					player.pause();
					$('#<portlet:namespace/>question_' + nextQuestion[0]).removeClass("aui-helper-hidden");
					indexQuestion++;
					if(indexQuestion < maxQuestions){
						nextQuestion = questions[indexQuestion];
					}
					answerQuestion = false;
				}else if(indexQuestion < maxQuestions && nextQuestion[1] < player.currentTime){
					indexQuestion++;
				}
			});
		}
			
		var unloadEvent = function (e) {
			console.log("unload event vimeo");  
			if(!finished){
				var duration = player.getDuration();
				currentTime = player.getCurrentTime();
					
				var isDefaultScore = '${isDefaultScore}' == 'true';
				var positionToSave = parseFloat('${videoPosition}');
				var oldScore = parseInt('${oldScore}');
				if (currentTime > positionToSave)
					positionToSave = currentTime;
				var score = 100;														
				if (!isDefaultScore) score = Math.round((currentTime/duration)*100);
				//debugger;
				<portlet:namespace/>finishTry(score, positionToSave,plays);													
			  
			}
		};
		
		window.addEventListener("beforeunload", unloadEvent);
		
	});
     
 	function <portlet:namespace/>answerQuestion(questionId){
 		//Cogemos la respuesta
 		console.log("guardamos respuesta");
 		var A = AUI();
 		var divQuestionId = $('.question',$('#<portlet:namespace />question_'+questionId)).attr("id");
 		var divQuestion = A.one('#' + divQuestionId);
 		var validationCorrect = <portlet:namespace/>questionValidation(divQuestion);
 		if (typeof validQuestion == 'undefined') {
 			validationCorrect = <portlet:namespace />questionValidation(divQuestion);
    	}
 		if(validationCorrect){
 			answerQuestion = true;
 			$.ajax({
 				dataType: 'json',
 				url: '${saveQuestionURL}',
 			    cache:false,
 				data: $("#<portlet:namespace />questionform_" + questionId).serialize(),
 				success: function(data){			
 					if(data){
 						if(data.correct){
 							$('#<portlet:namespace />question_'+questionId).remove();
							player.play();
 						}	
 					}
 				},
 				error: function(){
 					
 				}
 			});
 		}else{
 			//Mostramos los mensajes que sean
 		}
 	}

</script>	
	