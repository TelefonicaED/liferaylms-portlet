<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="javax.portlet.PortletSession"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sun.xml.internal.ws.developer.MemberSubmissionEndpointReference.Elements"%>
<%@page import="com.liferay.lms.learningactivity.ResourceExternalLearningActivityType"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.Criterion"%>
<%@page import="com.liferay.lms.service.ClpSerializer"%>
<%@page import="com.liferay.portal.kernel.bean.PortletBeanLocatorUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileVersion"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>

<% 
	boolean defaultValueCheckBox=false;
	Integer maxfile = ResourceExternalLearningActivityType.DEFAULT_FILENUMBER;
	PortletSession psession= renderRequest.getPortletSession();

	try{
		maxfile = Integer.valueOf(PropsUtil.get("lms.learningactivity.maxfile"));
	}catch(NumberFormatException nfe){
	}
	
	for(int i=0;i<=maxfile;i++){
		String paramExt = "extensionfile";
		String paramSize = "sizefile";
		if(i>0){
			paramExt = paramExt +(i-1);
			paramSize = paramSize +(i-1);
		}
		
		try{
			String valueExt = (String)request.getAttribute(paramExt);
			String valueSize = (String)request.getAttribute(paramSize);
			
			if(valueExt!=null){
				%>	<div class="portlet-msg-error"><%=LanguageUtil.format(themeDisplay.getLocale(),"error-file-ext",new Object[]{valueExt}) %></div><%
			}
			if(valueSize!=null){
				%>	<div class="portlet-msg-error"><%=LanguageUtil.format(themeDisplay.getLocale(),"error-file-size",new Object[]{valueSize}) %></div><%
			}
		}catch(Exception e){}
	}
%>
 
<%
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyy",themeDisplay.getLocale());
	sdf.setTimeZone(themeDisplay.getTimeZone());
	DLFileVersion previusaditionalfile=null;
	String youtubecode=StringPool.BLANK;
	LearningActivity learningActivity=null;
	boolean readonly = true;
	int correctMode = ResourceExternalLearningActivityType.CORRECT_VIDEO;
	boolean finalFeedback = false;
	boolean questionFeedback = false;
	List<AssetEntry> elements = new ArrayList<AssetEntry>(); 
		
	if(request.getAttribute("activity")!=null) {
		learningActivity=(LearningActivity)request.getAttribute("activity");
		
		if((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().trim().length()!=0))	{
			Document document = SAXReaderUtil.read(learningActivity.getExtracontent());
			Element root=document.getRootElement();
			Element video=root.element("video");
			
			if(video!=null) youtubecode=video.getText();
			
			Element videoControlEnabled = root.element("video-control");
			if(videoControlEnabled!=null) defaultValueCheckBox= Boolean.parseBoolean(videoControlEnabled.getText());

			Element documento=null;
			int i = 0;
			do{
				String documentt = "document";
				if(i>0){
					documentt = documentt+(i-1);
				}
				documento=root.element(documentt);
				if(documento!=null){
					try{
						AssetEntry docAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(documento.attributeValue("id")));
						elements.add(docAsset);
					}catch(Exception e){}
				}
				i++;
			}while(documento!=null);
			Element correctModeElement = root.element("correctMode");
			if(correctModeElement != null){
				correctMode = Integer.parseInt(correctModeElement.getText());
			}
			
			Element finalFeedbackElement = root.element("finalFeedback");
			if(finalFeedbackElement != null){
				finalFeedback = Boolean.parseBoolean(finalFeedbackElement.getText());
			}
			
			Element questionFeedbackElement = root.element("questionFeedback");
			if(questionFeedbackElement != null){
				questionFeedback = Boolean.parseBoolean(questionFeedbackElement.getText());
			}
		}
	}
	
	long score = Long.parseLong(ParamUtil.getString(request, "passpuntuation","0"));
	if(score==0){
		score=Long.valueOf(ResourceExternalLearningActivityType.DEFAULT_SCORE);
		if(learningActivity!=null){
			score=learningActivity.getPasspuntuation();
		}
	}
	
	if(LearningActivityLocalServiceUtil.canBeEdited(learningActivity, user.getUserId())) readonly=false;
	List<TestQuestion> listQuestions = null;
	if(learningActivity != null && learningActivity.getActId() > 0 ){
		listQuestions = TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
	}
	
	String passpuntuationLabelProperty = "resourceexternalactivity.passpuntuation";
	String passpunctuationHelpProperty= "resourceexternalactivity.passpuntuation.help";
	
	if(listQuestions != null && listQuestions.size() > 0){
		passpuntuationLabelProperty = "resourceexternalactivity.questions-video.passpuntuation";
		passpunctuationHelpProperty= "resourceexternalactivity.questions-video.passpuntuation.help";
	}
%>


	<%if(learningActivity != null && learningActivity.getActId() > 0 && listQuestions != null && listQuestions.size() > 0){%>
		<aui:field-wrapper label="course-correction-method">
			<%if(readonly) {%>
				<aui:input type="radio" readonly="<%=readonly %>" name="correctMode" value="<%=ResourceExternalLearningActivityType.CORRECT_VIDEO %>" label="resource-external-activity.correct-video" checked="<%=ResourceExternalLearningActivityType.CORRECT_VIDEO == correctMode %>"/>
				<aui:input type="radio" readonly="<%=readonly %>" name="correctMode" value="<%=ResourceExternalLearningActivityType.CORRECT_QUESTIONS %>" label="resource-external-activity.correct-questions" checked="<%=ResourceExternalLearningActivityType.CORRECT_QUESTIONS == correctMode %>"/>
			<%} else{ %>
				<aui:input type="radio" name="correctMode" value="<%=ResourceExternalLearningActivityType.CORRECT_VIDEO %>" label="resource-external-activity.correct-video" checked="<%=ResourceExternalLearningActivityType.CORRECT_VIDEO == correctMode %>"/>
				<aui:input type="radio" name="correctMode" value="<%=ResourceExternalLearningActivityType.CORRECT_QUESTIONS %>" label="resource-external-activity.correct-questions" checked="<%=ResourceExternalLearningActivityType.CORRECT_QUESTIONS == correctMode %>"/>
			<%} %>
		</aui:field-wrapper>
	<%} %>
	
	<aui:input size="5" name="passpuntuation" label="<%=passpuntuationLabelProperty %>" type="number" value="<%=score %>" disabled="<%=readonly %>" helpMessage="<%=LanguageUtil.get(pageContext, passpunctuationHelpProperty)%>">
		<aui:validator name="min" errorMessage="editActivity.passpuntuation.range">-1</aui:validator>
		<aui:validator name="max" errorMessage="editActivity.passpuntuation.range">101</aui:validator>
	</aui:input>
	<% if (readonly) { %>
		<input name="<portlet:namespace />passpuntuation" type="hidden" value="<%=score %>" />
	<% } %>
	
	<div id="<portlet:namespace />passpuntuationError" class="<%=((SessionErrors.contains(renderRequest, "editActivity.passpuntuation.required"))||
														      (SessionErrors.contains(renderRequest, "editActivity.passpuntuation.number"))||
														      (SessionErrors.contains(renderRequest, "editActivity.passpuntuation.range")))?
	  														      "portlet-msg-error":StringPool.BLANK %>">
	  	<%=(SessionErrors.contains(renderRequest, "editActivity.passpuntuation.required"))?
	  			LanguageUtil.get(pageContext,"editActivity.passpuntuation.required"):
				   (SessionErrors.contains(renderRequest, "editActivity.passpuntuation.number"))?
			    		LanguageUtil.get(pageContext,"editActivity.passpuntuation.number"):
				   (SessionErrors.contains(renderRequest, "editActivity.passpuntuation.range"))?
			    		LanguageUtil.get(pageContext,"editActivity.passpuntuation.range"):StringPool.BLANK %>
	</div>
<aui:field-wrapper label="video" >	
	<%if(readonly){%>
		<aui:input readonly="<%=readonly %>" name="youtubecode" type="textarea" rows="6" cols="45" label="youtube-code" value="<%=youtubecode %>" ignoreRequestValue="true" helpMessage="<%=LanguageUtil.get(pageContext,\"youtube-code-help\")%>"></aui:input>
	<% }else{ %>
		<aui:input name="youtubecode" type="textarea" rows="6" cols="45" label="youtube-code" value="<%=youtubecode %>" ignoreRequestValue="true" helpMessage="<%=LanguageUtil.get(pageContext,\"youtube-code-help\")%>"></aui:input>
	<%} %>
	
  	<aui:input label="resourceexternalactivity.videocontrol.disabled" name="videoControl" type="checkbox" value="<%= defaultValueCheckBox %>" />
  	
  	<%if(Validator.isNotNull(youtubecode) && learningActivity != null){
  		
  		if(listQuestions != null && listQuestions.size() > 0){
  			Element root = null;
  			if((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().trim().length()!=0))	{
				Document document = SAXReaderUtil.read(learningActivity.getExtracontent());
				root=document.getRootElement();
			
				
				//Tratamos el video si tiene
				Element video=root.element("video");
				if(video!=null){
					boolean isVimeoIframe = false;
					boolean isDLFileEntry = false;
					
					//Comprobamos si es vimeo o youtube
					if(video.attributeValue("id","").equals("")){
						String videoIframeCode= video.getText();
						isVimeoIframe = ((videoIframeCode.indexOf("iframe")>-1) &&  (videoIframeCode.indexOf("vimeo")>-1));
						
						boolean videoControlDisabled = false;
						Element videoControl=root.element("video-control");
						if(videoControl!=null){
							videoControlDisabled = Boolean.parseBoolean(videoControl.getText());
						}
						
						
						String videoCode= video.getText();
						
						if(videoCode.indexOf("src=") > 0){
							try{
								Matcher matcher = Pattern.compile("src=\"([^\"]+)\"").matcher(videoCode);
								matcher.find();
								videoCode = matcher.group(1);
							}catch (IllegalStateException e){
								Matcher matcher = Pattern.compile("src=\'([^\']+)\'").matcher(videoCode);
								matcher.find();
								videoCode = matcher.group(1);
							}
						}
						
						if(isVimeoIframe && videoCode.indexOf("?") >= 0){
							videoCode = videoCode.substring(0, videoCode.indexOf("?"));
							
						}
						
						
						
						String mimeType = "video/";
						if(videoCode.contains("vimeo.com")){
							videoCode += "?background=1&loop=0&mute=0&autoplay=1";	
							mimeType += "vimeo";
						}else if(videoCode.contains("youtu")){
							mimeType += "youtube";
						}else if(videoCode.contains(".mp4")){
							mimeType += "mp4";
						}else if(videoCode.contains(".wmv")){
							mimeType += "wmv";
						}else if(videoCode.contains(".ogv")){
							mimeType += "ogg";
						}else if(videoCode.contains(".webm")){
							mimeType += "webm";
						}else if(videoCode.contains(".flv")){
							mimeType += "flv";
						}else if(videoCode.contains(".mp4")){
							mimeType += "mp4";
						}
						
						
						//Ahora pasamos los tiempos de las preguntas
						Hashtable<Long, Integer> timeQuestions = new Hashtable<Long, Integer>();
						Element element = null;
						for(TestQuestion question: listQuestions){
							try{
								element = root.element("question_" + question.getQuestionId());
								if(element != null){
									timeQuestions.put(question.getQuestionId(), Integer.parseInt(element.getText()));
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						%>
						
						<c:set var="controls" value="controls"/>
						<c:set var="currentTime" value="0"/>
						<c:set var="mimeType" value="<%=mimeType %>"/>
						<c:set var="video" value="<%=videoCode %>"/>
						<c:set var="listQuestions" value="<%=listQuestions %>"/>
						<c:set var="timeQuestions" value="<%=timeQuestions %>"/>
						
						
						<%
				
					}
					
					renderRequest.setAttribute("isYoutubeIframe", true);
					renderRequest.setAttribute("isVimeoIframe", isVimeoIframe);
					renderRequest.setAttribute("isDLFileEntry", isDLFileEntry);
				}%>

<link rel="stylesheet" type="text/css" href="/liferaylms-portlet/js/mediaelement/mediaelementplayer.css">


	
	
<div class="contentQuestionVideo">
	<div class="video">
		<video  id="playervideo" width="600" height="338" autoplay="autoplay" src="${video}"  type="${mimeType }"></video>
	</div>
	
	<c:forEach items="${listQuestions }" var="question">
		<c:set var="questionType" value="${question.testQuestionType }" />
		<div class="aui-helper-hidden questionVideo" id="${renderResponse.namespace}question_video_${question.questionId}">	
			${questionType.getHtmlView(question.questionId, themeDisplay, null) }
			<aui:button value="continue" onClick="javascript:${renderResponse.namespace}continueQuestion(${question.questionId })" />
		</div>
		</c:forEach>
</div>
	
	
	

 <!-- JS -->
 <script src="https://cdn.jsdelivr.net/npm/mediaelement@4.2.9/build/mediaelement-and-player.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/mediaelement@4.2.9/build/renderers/dailymotion.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/mediaelement@4.2.9/build/renderers/facebook.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/mediaelement@4.2.9/build/renderers/soundcloud.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/mediaelement@4.2.9/build/renderers/twitch.min.js"></script>
 <script src="/liferaylms-portlet/js/mediaelement/renderers/vimeo.js"></script>
 
 <script src="/liferaylms-portlet/js/media-element-marker.js"></script>
<script>
 	
 	var player;
 	
 	document.addEventListener('DOMContentLoaded', function() {   

		//Creamos el array para las preguntas
		var questions = [];
		<c:forEach items="${timeQuestions }" var="question">
			var question = ["${question.key}","${question.value}"];
			questions.push(question);
		</c:forEach>
		
		questions.sort(function(a, b){return a[1]-b[1]});
 		
 		$('#playervideo').mediaelementplayer({
     	    features: ['playpause','current','progress','duration','markers','volume'], //Adding the feature 'markers' enables this plugin
     		pluginPath: 'https://cdn.jsdelivr.net/npm/mediaelement@4.2.9/build/',
     		markerColor: '#FCD730', // Optional : Specify the color of the marker
     		markers:questions, // Specify marker times in seconds 
     		markerCallback:function(media,time,currentMarker){ // Callback function invoked when a marker position is reached
     	
     		   
     		  media.pause();
				$('#<portlet:namespace/>question_video_' + currentMarker).removeClass("aui-helper-hidden");
     		
     		},
     	    success: function (media) {
     	    	player = media;
     	    }
     				
     				
     	}); 

     	     	
     	$(".mejs__playpause-button").remove();
     }); 
     
   
     
 	function <portlet:namespace/>continueQuestion(questionId){
 		//Cogemos la respuesta
 		//console.log("continuamos respuesta");
 		var A = AUI();
 		var divQuestionId = $('.question',$('#<portlet:namespace />question_video_'+questionId)).attr("id");
 		var divQuestion = A.one('#' + divQuestionId);
 		$('#<portlet:namespace />question_video_'+questionId).remove();
		player.play();
 		
 	}
    
 	function <portlet:namespace/>changeTime(questionId,second){
 		if(second>player.duration || second<0){
 			alert(Liferay.Language.get("resourceexternalactivity.error.seconds"));
 		}else{
 			//Borramos el marcador
 	 		$('#marker-'+questionId).remove();
 	 		//Creamos marcador nuevo
 	 		 $('.mejs__time-total').append('<span class="mejs-time-marker" id="marker-'+questionId+'"></span>');
 	 		//Maquetamos el marcador
 	 		if (Math.floor(second) <= player.getDuration() && Math.floor(second) >= 0) {
 	                    left = 100 * Math.floor(second) / player.getDuration();
 	                    $('#marker-'+questionId).css({
 	                        "width": "2px",
 	                        "height": "10px",
 	                        "left": left+"%",
 	                        "position": "absolute",
 	                        "background": "#FCD730"
 	                    });
 	                }
 	 		
 	 		for (i = 0; i < player.options.markers.length; ++i) {
 	          if(player.options.markers[i][0]==questionId){
 	        	  player.options.markers[i][1]=second;
 	          }
 	        }
 	 		
 	 		//Pausamos el video
 	 		player.pause();
 	 		//Posicionamos el video en el segundo.
 	 		player.setCurrentTime(second);
 	 		//console.log("finish");
 	 		
 	 			
 		}
 		
 		
 	}
</script>				
			
  			<%}
				%>
				
				
		
			
			
			
			
		
		<table class="taglib-search-container">
  				<thead>
  					<tr class="portlet-section-header results-header">
  						<th class="col-1 col-text first">
  							<liferay-ui:message key="question" />
  						</th>
  						<th class="col-2 col-second">
  							<liferay-ui:message key="second" />
  						</th>
  					</tr>
  				</thead>
  				<tbody>
  					<% Element second = null;
  					for(TestQuestion question: listQuestions){ 
  						if(root != null){
  							second = root.element("question_" + question.getQuestionId());
  						}%>
  						<tr class="portlet-section-body results-row">
  							<td class="align-left col-text"><span id="${renderResponse.getNamespace()}question_<%=question.getQuestionId()%>"><%=question.getText() %></td>
  							<td class="align-middle">
  								<c:set var="questionId" value="<%=question.getQuestionId()%>" />
  								<aui:input name="second_${questionId }" label="" value='<%=second != null ? second.getText() : "0" %>'  onChange="${renderResponse.getNamespace()}changeTime('${questionId}',this.value);">
  									<aui:validator name="number"/>
  									<aui:validator name="min">"0"</aui:validator>
  								</aui:input>
  							</td>
  						</tr>
  					<%} %>
  				</tbody>
  			</table>
  			<%if(readonly) {%>
    			<aui:input  readonly="<%=readonly %>"  label="resourceexternalactivity.question-feedback" name="questionFeedback" type="checkbox" value="<%= questionFeedback %>" />
			    <aui:input  readonly="<%=readonly %>"  label="resourceexternalactivity.final-feedback" name="finalFeedback" type="checkbox" value="<%= finalFeedback %>" />
			<%}else{ %>
				<aui:input  label="resourceexternalactivity.question-feedback" name="questionFeedback" type="checkbox" value="<%= questionFeedback %>" />
				<aui:input  label="resourceexternalactivity.final-feedback" name="finalFeedback" type="checkbox" value="<%= finalFeedback %>" />
			<%} 
  		}
  	} %>
  	
</aui:field-wrapper>
<script type="text/javascript">
	function deleteFile(id){
		var divfiles = document.getElementById("files");
		var file = document.getElementById("file"+id);
		var inputs = file.getElementsByTagName("input");
		if(inputs[0].value!=null&&inputs[0].value!=""){
			if(confirm(Liferay.Language.get("are-you-sure-you-want-to-delete-this"))){
				divfiles.removeChild(file);
			}	
		}else{
			divfiles.removeChild(file);
		}
		var files = divfiles.getElementsByTagName("input");
		if(files.length<<%=maxfile%>){
			document.getElementById("add_attachment").style.display = 'block';
		}
	}
	function addFileInput(){
		var divfiles = document.getElementById("files");
		var files = divfiles.getElementsByTagName("input");
		var append = 1;
		if(files.length+1>=<%=maxfile%>){
			document.getElementById("add_attachment").style.display = 'none';
		}
		for (var i=0; i<files.length; i++) {
			var div = document.getElementById("file"+i);
			if(div==null){
				append=i;
				break;
			}
			append=i;
		}
		var div = document.createElement("div");
		div.id = "file"+append;
		div.className = "row_file";
		var input = document.createElement("input");
		input.type = "file";
		input.name = "<portlet:namespace />additionalFile"+append;
		input.id = "<portlet:namespace />additionalFile"+append;
		div.appendChild(input);
		var a = document.createElement("a");
		a.onclick = function() { deleteFile(append); return false; };
		a.href="#";
		var img = document.createElement("img");
		img.className="icon";
		img.tittle="<%=LanguageUtil.get(pageContext,"delete")%>";
		img.alt="<%=LanguageUtil.get(pageContext,"delete") %>";
		img.src="<%= themeDisplay.getPathThemeImages() %>/common/delete.png";
		a.appendChild(img);

		div.appendChild(a);
		divfiles.appendChild(div);
	}
</script>
<c:if test="<%=GetterUtil.getBoolean(PropsUtil.get(\"learningactivity.resourceExternal.complementaryFile\"),true) %>">
<aui:field-wrapper label="complementary-file" helpMessage="<%=LanguageUtil.get(pageContext,\"additionalFile-help\")%>" >		  	
	<div id="files" class="container_files">
		<% for(int i=0;i<elements.size();i++){ 
			String append = "";
			if(i>0){
				append = append+(i-1);
			}
		%>
			<div id="file<%=append%>" class="row_file">
				<% 	AssetEntry aEntry = elements.get(i);
					FileEntry file=DLAppLocalServiceUtil.getFileEntry(aEntry.getClassPK());
					
					
					StringBuilder sb = new StringBuilder(themeDisplay.getPortalURL());
					sb.append(themeDisplay.getPathContext());
					sb.append("/documents/");
					sb.append(file.getGroupId());
					sb.append(StringPool.SLASH);
					sb.append(file.getFolderId());
					sb.append(StringPool.SLASH);
					sb.append(HttpUtil.encodeURL(HtmlUtil.unescape(file.getTitle())));	
					
					Double size = ((double)file.getSize())/1000;
				%>
				<input type="hidden" name="<portlet:namespace />additionalFile<%=append %>" id="<portlet:namespace />additionalFile<%=append %>" value="<%= aEntry.getEntryId() %>">
				<span class="upfile"><a target="_blank" href="<%=sb.toString()%>"><img class="dl-file-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= file.getIcon() %>.png" />
					<%= aEntry.getTitle(themeDisplay.getLocale()) %></a></span> <span class="ufilesize">(<%=String.format(themeDisplay.getLocale(), "%.2f", size) %> KB)</span>
				<a href="#" onclick="javascript: deleteFile('<%=append %>'); return false;">
					<img class="icon" title="<%=LanguageUtil.get(pageContext,"delete")%>" alt="<%=LanguageUtil.get(pageContext,"delete")%>" src="<%= themeDisplay.getPathThemeImages() %>/common/delete.png"></a>
					<span class="upuser"><%=aEntry.getUserName() %></span> <span class="udate"><%=sdf.format(aEntry.getCreateDate()) %></span>
			</div>
			<%}
			
			if(elements.size()==0){
				if(readonly) {%>
					<aui:input readonly="<%=readonly %>" inlineLabel="left" inlineField="true" name="additionalFile" label="" id="additionalFile" type="file" value="" />
				<%}else{ %>
					<aui:input inlineLabel="left" inlineField="true" name="additionalFile" label="" id="additionalFile" type="file" value="" />
				<%} 
			} %>
		</div>
	<div class="container-buttons">
		<a href="#" id="add_attachment" style="<%=(elements.size()>=maxfile)?"display:none":"" %>" class="add_attachment bt_new" onclick="addFileInput(); return false;"><liferay-ui:message key="resource.external.add.other" /></a>		
	</div>	
</aui:field-wrapper>
</c:if>
